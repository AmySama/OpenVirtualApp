package com.android.dx.command.annotool;

import com.android.dx.cf.attrib.BaseAnnotations;
import com.android.dx.cf.direct.AttributeFactory;
import com.android.dx.cf.direct.ClassPathOpener;
import com.android.dx.cf.direct.DirectClassFile;
import com.android.dx.cf.direct.StdAttributeFactory;
import com.android.dx.cf.iface.Attribute;
import com.android.dx.cf.iface.AttributeList;
import com.android.dx.rop.annotation.Annotation;
import com.android.dx.util.ByteArray;
import java.io.File;
import java.lang.annotation.ElementType;
import java.util.HashSet;
import java.util.Iterator;

class AnnotationLister {
  private static final String PACKAGE_INFO = "package-info";
  
  private final Main.Arguments args;
  
  HashSet<String> matchInnerClassesOf = new HashSet<String>();
  
  HashSet<String> matchPackages = new HashSet<String>();
  
  AnnotationLister(Main.Arguments paramArguments) {
    this.args = paramArguments;
  }
  
  private boolean isMatchingInnerClass(String paramString) {
    while (true) {
      int i = paramString.lastIndexOf('$');
      if (i > 0) {
        String str = paramString.substring(0, i);
        paramString = str;
        if (this.matchInnerClassesOf.contains(str))
          return true; 
        continue;
      } 
      return false;
    } 
  }
  
  private boolean isMatchingPackage(String paramString) {
    int i = paramString.lastIndexOf('/');
    if (i == -1) {
      paramString = "";
    } else {
      paramString = paramString.substring(0, i);
    } 
    return this.matchPackages.contains(paramString);
  }
  
  private void printMatch(DirectClassFile paramDirectClassFile) {
    for (Main.PrintType printType : this.args.printTypes) {
      int i = null.$SwitchMap$com$android$dx$command$annotool$Main$PrintType[printType.ordinal()];
      if (i != 1) {
        if (i != 2)
          continue; 
        this.matchInnerClassesOf.add(paramDirectClassFile.getThisClass().getClassType().getClassName());
        continue;
      } 
      String str = paramDirectClassFile.getThisClass().getClassType().getClassName().replace('/', '.');
      System.out.println(str);
    } 
  }
  
  private void printMatchPackage(String paramString) {
    for (Main.PrintType printType : this.args.printTypes) {
      int i = null.$SwitchMap$com$android$dx$command$annotool$Main$PrintType[printType.ordinal()];
      if (i != 1 && i != 2 && i != 3) {
        if (i != 4)
          continue; 
        System.out.println(paramString.replace('/', '.'));
        continue;
      } 
      this.matchPackages.add(paramString);
    } 
  }
  
  private void visitClassAnnotation(DirectClassFile paramDirectClassFile, BaseAnnotations paramBaseAnnotations) {
    if (!this.args.eTypes.contains(ElementType.TYPE))
      return; 
    Iterator<Annotation> iterator = paramBaseAnnotations.getAnnotations().getAnnotations().iterator();
    while (iterator.hasNext()) {
      String str = ((Annotation)iterator.next()).getType().getClassType().getClassName();
      if (this.args.aclass.equals(str))
        printMatch(paramDirectClassFile); 
    } 
  }
  
  private void visitPackageAnnotation(DirectClassFile paramDirectClassFile, BaseAnnotations paramBaseAnnotations) {
    if (!this.args.eTypes.contains(ElementType.PACKAGE))
      return; 
    String str = paramDirectClassFile.getThisClass().getClassType().getClassName();
    int i = str.lastIndexOf('/');
    if (i == -1) {
      str = "";
    } else {
      str = str.substring(0, i);
    } 
    Iterator<Annotation> iterator = paramBaseAnnotations.getAnnotations().getAnnotations().iterator();
    while (iterator.hasNext()) {
      String str1 = ((Annotation)iterator.next()).getType().getClassType().getClassName();
      if (this.args.aclass.equals(str1))
        printMatchPackage(str); 
    } 
  }
  
  void process() {
    String[] arrayOfString = this.args.files;
    int i = arrayOfString.length;
    for (byte b = 0; b < i; b++) {
      (new ClassPathOpener(arrayOfString[b], true, new ClassPathOpener.Consumer() {
            public void onException(Exception param1Exception) {
              throw new RuntimeException(param1Exception);
            }
            
            public void onProcessArchiveStart(File param1File) {}
            
            public boolean processFileBytes(String param1String, long param1Long, byte[] param1ArrayOfbyte) {
              Attribute attribute;
              if (!param1String.endsWith(".class"))
                return true; 
              DirectClassFile directClassFile = new DirectClassFile(new ByteArray(param1ArrayOfbyte), param1String, true);
              directClassFile.setAttributeFactory((AttributeFactory)StdAttributeFactory.THE_ONE);
              AttributeList attributeList = directClassFile.getAttributes();
              param1String = directClassFile.getThisClass().getClassType().getClassName();
              if (param1String.endsWith("package-info")) {
                for (attribute = attributeList.findFirst("RuntimeInvisibleAnnotations"); attribute != null; attribute = attributeList.findNext(attribute)) {
                  BaseAnnotations baseAnnotations = (BaseAnnotations)attribute;
                  AnnotationLister.this.visitPackageAnnotation(directClassFile, baseAnnotations);
                } 
                for (attribute = attributeList.findFirst("RuntimeVisibleAnnotations"); attribute != null; attribute = attributeList.findNext(attribute)) {
                  BaseAnnotations baseAnnotations = (BaseAnnotations)attribute;
                  AnnotationLister.this.visitPackageAnnotation(directClassFile, baseAnnotations);
                } 
              } else {
                if (AnnotationLister.this.isMatchingInnerClass((String)attribute) || AnnotationLister.this.isMatchingPackage((String)attribute)) {
                  AnnotationLister.this.printMatch(directClassFile);
                  return true;
                } 
                for (attribute = attributeList.findFirst("RuntimeInvisibleAnnotations"); attribute != null; attribute = attributeList.findNext(attribute)) {
                  BaseAnnotations baseAnnotations = (BaseAnnotations)attribute;
                  AnnotationLister.this.visitClassAnnotation(directClassFile, baseAnnotations);
                } 
                for (attribute = attributeList.findFirst("RuntimeVisibleAnnotations"); attribute != null; attribute = attributeList.findNext(attribute)) {
                  BaseAnnotations baseAnnotations = (BaseAnnotations)attribute;
                  AnnotationLister.this.visitClassAnnotation(directClassFile, baseAnnotations);
                } 
              } 
              return true;
            }
          })).process();
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\command\annotool\AnnotationLister.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */