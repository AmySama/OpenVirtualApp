package com.android.dx.dex.cf;

import com.android.dx.cf.attrib.AttAnnotationDefault;
import com.android.dx.cf.attrib.AttEnclosingMethod;
import com.android.dx.cf.attrib.AttExceptions;
import com.android.dx.cf.attrib.AttInnerClasses;
import com.android.dx.cf.attrib.AttRuntimeInvisibleAnnotations;
import com.android.dx.cf.attrib.AttRuntimeInvisibleParameterAnnotations;
import com.android.dx.cf.attrib.AttRuntimeVisibleAnnotations;
import com.android.dx.cf.attrib.AttRuntimeVisibleParameterAnnotations;
import com.android.dx.cf.attrib.AttSignature;
import com.android.dx.cf.attrib.AttSourceDebugExtension;
import com.android.dx.cf.attrib.InnerClassList;
import com.android.dx.cf.direct.DirectClassFile;
import com.android.dx.cf.iface.AttributeList;
import com.android.dx.cf.iface.Method;
import com.android.dx.cf.iface.MethodList;
import com.android.dx.dex.file.AnnotationUtils;
import com.android.dx.rop.annotation.Annotation;
import com.android.dx.rop.annotation.AnnotationVisibility;
import com.android.dx.rop.annotation.Annotations;
import com.android.dx.rop.annotation.AnnotationsList;
import com.android.dx.rop.annotation.NameValuePair;
import com.android.dx.rop.code.AccessFlags;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;
import com.android.dx.util.Warning;
import java.io.PrintStream;
import java.util.ArrayList;

class AttributeTranslator {
  public static Annotations getAnnotations(AttributeList paramAttributeList) {
    Annotations annotations2 = getAnnotations0(paramAttributeList);
    Annotation annotation1 = getSignature(paramAttributeList);
    Annotation annotation2 = getSourceDebugExtension(paramAttributeList);
    Annotations annotations1 = annotations2;
    if (annotation1 != null)
      annotations1 = Annotations.combine(annotations2, annotation1); 
    annotations2 = annotations1;
    if (annotation2 != null)
      annotations2 = Annotations.combine(annotations1, annotation2); 
    return annotations2;
  }
  
  private static Annotations getAnnotations0(AttributeList paramAttributeList) {
    AttRuntimeVisibleAnnotations attRuntimeVisibleAnnotations = (AttRuntimeVisibleAnnotations)paramAttributeList.findFirst("RuntimeVisibleAnnotations");
    AttRuntimeInvisibleAnnotations attRuntimeInvisibleAnnotations = (AttRuntimeInvisibleAnnotations)paramAttributeList.findFirst("RuntimeInvisibleAnnotations");
    return (attRuntimeVisibleAnnotations == null) ? ((attRuntimeInvisibleAnnotations == null) ? Annotations.EMPTY : attRuntimeInvisibleAnnotations.getAnnotations()) : ((attRuntimeInvisibleAnnotations == null) ? attRuntimeVisibleAnnotations.getAnnotations() : Annotations.combine(attRuntimeVisibleAnnotations.getAnnotations(), attRuntimeInvisibleAnnotations.getAnnotations()));
  }
  
  public static Annotations getClassAnnotations(DirectClassFile paramDirectClassFile, CfOptions paramCfOptions) {
    boolean bool;
    CstType cstType = paramDirectClassFile.getThisClass();
    AttributeList attributeList = paramDirectClassFile.getAttributes();
    Annotations annotations3 = getAnnotations(attributeList);
    Annotation annotation = translateEnclosingMethod(attributeList);
    if (annotation == null) {
      bool = true;
    } else {
      bool = false;
    } 
    try {
      Annotations annotations = translateInnerClasses(cstType, attributeList, bool);
      annotations2 = annotations3;
      if (annotations != null)
        annotations2 = Annotations.combine(annotations3, annotations); 
    } catch (Warning warning) {
      PrintStream printStream = paramCfOptions.warn;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("warning: ");
      stringBuilder.append(warning.getMessage());
      printStream.println(stringBuilder.toString());
      annotations2 = annotations3;
    } 
    Annotations annotations1 = annotations2;
    if (annotation != null)
      annotations1 = Annotations.combine(annotations2, annotation); 
    Annotations annotations2 = annotations1;
    if (AccessFlags.isAnnotation(paramDirectClassFile.getAccessFlags())) {
      Annotation annotation1 = translateAnnotationDefaults(paramDirectClassFile);
      annotations2 = annotations1;
      if (annotation1 != null)
        annotations2 = Annotations.combine(annotations1, annotation1); 
    } 
    return annotations2;
  }
  
  public static TypeList getExceptions(Method paramMethod) {
    AttExceptions attExceptions = (AttExceptions)paramMethod.getAttributes().findFirst("Exceptions");
    return (TypeList)((attExceptions == null) ? StdTypeList.EMPTY : attExceptions.getExceptions());
  }
  
  public static Annotations getMethodAnnotations(Method paramMethod) {
    Annotations annotations2 = getAnnotations(paramMethod.getAttributes());
    TypeList typeList = getExceptions(paramMethod);
    Annotations annotations1 = annotations2;
    if (typeList.size() != 0)
      annotations1 = Annotations.combine(annotations2, AnnotationUtils.makeThrows(typeList)); 
    return annotations1;
  }
  
  public static AnnotationsList getParameterAnnotations(Method paramMethod) {
    AttributeList attributeList = paramMethod.getAttributes();
    AttRuntimeVisibleParameterAnnotations attRuntimeVisibleParameterAnnotations = (AttRuntimeVisibleParameterAnnotations)attributeList.findFirst("RuntimeVisibleParameterAnnotations");
    AttRuntimeInvisibleParameterAnnotations attRuntimeInvisibleParameterAnnotations = (AttRuntimeInvisibleParameterAnnotations)attributeList.findFirst("RuntimeInvisibleParameterAnnotations");
    return (attRuntimeVisibleParameterAnnotations == null) ? ((attRuntimeInvisibleParameterAnnotations == null) ? AnnotationsList.EMPTY : attRuntimeInvisibleParameterAnnotations.getParameterAnnotations()) : ((attRuntimeInvisibleParameterAnnotations == null) ? attRuntimeVisibleParameterAnnotations.getParameterAnnotations() : AnnotationsList.combine(attRuntimeVisibleParameterAnnotations.getParameterAnnotations(), attRuntimeInvisibleParameterAnnotations.getParameterAnnotations()));
  }
  
  private static Annotation getSignature(AttributeList paramAttributeList) {
    AttSignature attSignature = (AttSignature)paramAttributeList.findFirst("Signature");
    return (attSignature == null) ? null : AnnotationUtils.makeSignature(attSignature.getSignature());
  }
  
  private static Annotation getSourceDebugExtension(AttributeList paramAttributeList) {
    AttSourceDebugExtension attSourceDebugExtension = (AttSourceDebugExtension)paramAttributeList.findFirst("SourceDebugExtension");
    return (attSourceDebugExtension == null) ? null : AnnotationUtils.makeSourceDebugExtension(attSourceDebugExtension.getSmapString());
  }
  
  private static Annotation translateAnnotationDefaults(DirectClassFile paramDirectClassFile) {
    CstType cstType = paramDirectClassFile.getThisClass();
    MethodList methodList = paramDirectClassFile.getMethods();
    int i = methodList.size();
    Annotation annotation = new Annotation(cstType, AnnotationVisibility.EMBEDDED);
    byte b = 0;
    boolean bool = false;
    while (b < i) {
      Method method = methodList.get(b);
      AttAnnotationDefault attAnnotationDefault = (AttAnnotationDefault)method.getAttributes().findFirst("AnnotationDefault");
      if (attAnnotationDefault != null) {
        annotation.add(new NameValuePair(method.getNat().getName(), attAnnotationDefault.getValue()));
        bool = true;
      } 
      b++;
    } 
    if (!bool)
      return null; 
    annotation.setImmutable();
    return AnnotationUtils.makeAnnotationDefault(annotation);
  }
  
  private static Annotation translateEnclosingMethod(AttributeList paramAttributeList) {
    AttEnclosingMethod attEnclosingMethod = (AttEnclosingMethod)paramAttributeList.findFirst("EnclosingMethod");
    if (attEnclosingMethod == null)
      return null; 
    CstType cstType = attEnclosingMethod.getEnclosingClass();
    CstNat cstNat = attEnclosingMethod.getMethod();
    return (cstNat == null) ? AnnotationUtils.makeEnclosingClass(cstType) : AnnotationUtils.makeEnclosingMethod(new CstMethodRef(cstType, cstNat));
  }
  
  private static Annotations translateInnerClasses(CstType paramCstType, AttributeList paramAttributeList, boolean paramBoolean) {
    AttInnerClasses attInnerClasses = (AttInnerClasses)paramAttributeList.findFirst("InnerClasses");
    if (attInnerClasses == null)
      return null; 
    InnerClassList innerClassList = attInnerClasses.getInnerClasses();
    int i = innerClassList.size();
    ArrayList<Type> arrayList = new ArrayList();
    boolean bool = false;
    attInnerClasses = null;
    byte b = 0;
    while (b < i) {
      AttInnerClasses attInnerClasses1;
      InnerClassList.Item item = innerClassList.get(b);
      CstType cstType = item.getInnerClass();
      if (cstType.equals(paramCstType)) {
        InnerClassList.Item item1 = item;
      } else {
        attInnerClasses1 = attInnerClasses;
        if (paramCstType.equals(item.getOuterClass())) {
          arrayList.add(cstType.getClassType());
          attInnerClasses1 = attInnerClasses;
        } 
      } 
      b++;
      attInnerClasses = attInnerClasses1;
    } 
    i = arrayList.size();
    if (attInnerClasses == null && i == 0)
      return null; 
    Annotations annotations = new Annotations();
    if (attInnerClasses != null) {
      annotations.add(AnnotationUtils.makeInnerClass(attInnerClasses.getInnerName(), attInnerClasses.getAccessFlags()));
      if (paramBoolean)
        if (attInnerClasses.getOuterClass() != null) {
          annotations.add(AnnotationUtils.makeEnclosingClass(attInnerClasses.getOuterClass()));
        } else {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Ignoring InnerClasses attribute for an anonymous inner class\n(");
          stringBuilder.append(paramCstType.toHuman());
          stringBuilder.append(") that doesn't come with an\nassociated EnclosingMethod attribute. This class was probably produced by a\ncompiler that did not target the modern .class file format. The recommended\nsolution is to recompile the class from source, using an up-to-date compiler\nand without specifying any \"-target\" type options. The consequence of ignoring\nthis warning is that reflective operations on this class will incorrectly\nindicate that it is *not* an inner class.");
          throw new Warning(stringBuilder.toString());
        }  
    } 
    if (i != 0) {
      StdTypeList stdTypeList = new StdTypeList(i);
      for (b = bool; b < i; b++)
        stdTypeList.set(b, arrayList.get(b)); 
      stdTypeList.setImmutable();
      annotations.add(AnnotationUtils.makeMemberClasses((TypeList)stdTypeList));
    } 
    annotations.setImmutable();
    return annotations;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\cf\AttributeTranslator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */