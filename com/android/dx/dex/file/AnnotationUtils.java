package com.android.dx.dex.file;

import com.android.dx.rop.annotation.Annotation;
import com.android.dx.rop.annotation.AnnotationVisibility;
import com.android.dx.rop.annotation.NameValuePair;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstAnnotation;
import com.android.dx.rop.cst.CstArray;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.rop.cst.CstKnownNull;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;
import java.util.ArrayList;

public final class AnnotationUtils {
  private static final CstString ACCESS_FLAGS_STRING;
  
  private static final CstType ANNOTATION_DEFAULT_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/AnnotationDefault;"));
  
  private static final CstType ENCLOSING_CLASS_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/EnclosingClass;"));
  
  private static final CstType ENCLOSING_METHOD_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/EnclosingMethod;"));
  
  private static final CstType INNER_CLASS_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/InnerClass;"));
  
  private static final CstType MEMBER_CLASSES_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/MemberClasses;"));
  
  private static final CstString NAME_STRING;
  
  private static final CstType SIGNATURE_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/Signature;"));
  
  private static final CstType SOURCE_DEBUG_EXTENSION_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/SourceDebugExtension;"));
  
  private static final CstType THROWS_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/Throws;"));
  
  private static final CstString VALUE_STRING;
  
  static {
    ACCESS_FLAGS_STRING = new CstString("accessFlags");
    NAME_STRING = new CstString("name");
    VALUE_STRING = new CstString("value");
  }
  
  public static Annotation makeAnnotationDefault(Annotation paramAnnotation) {
    Annotation annotation = new Annotation(ANNOTATION_DEFAULT_TYPE, AnnotationVisibility.SYSTEM);
    annotation.put(new NameValuePair(VALUE_STRING, (Constant)new CstAnnotation(paramAnnotation)));
    annotation.setImmutable();
    return annotation;
  }
  
  private static CstArray makeCstArray(TypeList paramTypeList) {
    int i = paramTypeList.size();
    CstArray.List list = new CstArray.List(i);
    for (byte b = 0; b < i; b++)
      list.set(b, (Constant)CstType.intern(paramTypeList.getType(b))); 
    list.setImmutable();
    return new CstArray(list);
  }
  
  public static Annotation makeEnclosingClass(CstType paramCstType) {
    Annotation annotation = new Annotation(ENCLOSING_CLASS_TYPE, AnnotationVisibility.SYSTEM);
    annotation.put(new NameValuePair(VALUE_STRING, (Constant)paramCstType));
    annotation.setImmutable();
    return annotation;
  }
  
  public static Annotation makeEnclosingMethod(CstMethodRef paramCstMethodRef) {
    Annotation annotation = new Annotation(ENCLOSING_METHOD_TYPE, AnnotationVisibility.SYSTEM);
    annotation.put(new NameValuePair(VALUE_STRING, (Constant)paramCstMethodRef));
    annotation.setImmutable();
    return annotation;
  }
  
  public static Annotation makeInnerClass(CstString paramCstString, int paramInt) {
    CstKnownNull cstKnownNull;
    Annotation annotation = new Annotation(INNER_CLASS_TYPE, AnnotationVisibility.SYSTEM);
    if (paramCstString == null)
      cstKnownNull = CstKnownNull.THE_ONE; 
    annotation.put(new NameValuePair(NAME_STRING, (Constant)cstKnownNull));
    annotation.put(new NameValuePair(ACCESS_FLAGS_STRING, (Constant)CstInteger.make(paramInt)));
    annotation.setImmutable();
    return annotation;
  }
  
  public static Annotation makeMemberClasses(TypeList paramTypeList) {
    CstArray cstArray = makeCstArray(paramTypeList);
    Annotation annotation = new Annotation(MEMBER_CLASSES_TYPE, AnnotationVisibility.SYSTEM);
    annotation.put(new NameValuePair(VALUE_STRING, (Constant)cstArray));
    annotation.setImmutable();
    return annotation;
  }
  
  public static Annotation makeSignature(CstString paramCstString) {
    Annotation annotation = new Annotation(SIGNATURE_TYPE, AnnotationVisibility.SYSTEM);
    String str = paramCstString.getString();
    int i = str.length();
    ArrayList<String> arrayList = new ArrayList(20);
    byte b1 = 0;
    int j;
    for (j = 0; j < i; j = m) {
      char c = str.charAt(j);
      int m = j + 1;
      int n = m;
      if (c == 'L') {
        n = m;
        while (true) {
          m = n;
          if (n < i) {
            m = str.charAt(n);
            if (m == 59) {
              m = n + 1;
              break;
            } 
            if (m == 60) {
              m = n;
              break;
            } 
            n++;
            continue;
          } 
          break;
        } 
      } else {
        while (true) {
          m = n;
          if (n < i) {
            if (str.charAt(n) == 'L') {
              m = n;
              break;
            } 
            n++;
            continue;
          } 
          break;
        } 
      } 
      arrayList.add(str.substring(j, m));
    } 
    int k = arrayList.size();
    CstArray.List list = new CstArray.List(k);
    for (byte b2 = b1; b2 < k; b2++)
      list.set(b2, (Constant)new CstString(arrayList.get(b2))); 
    list.setImmutable();
    annotation.put(new NameValuePair(VALUE_STRING, (Constant)new CstArray(list)));
    annotation.setImmutable();
    return annotation;
  }
  
  public static Annotation makeSourceDebugExtension(CstString paramCstString) {
    Annotation annotation = new Annotation(SOURCE_DEBUG_EXTENSION_TYPE, AnnotationVisibility.SYSTEM);
    annotation.put(new NameValuePair(VALUE_STRING, (Constant)paramCstString));
    annotation.setImmutable();
    return annotation;
  }
  
  public static Annotation makeThrows(TypeList paramTypeList) {
    CstArray cstArray = makeCstArray(paramTypeList);
    Annotation annotation = new Annotation(THROWS_TYPE, AnnotationVisibility.SYSTEM);
    annotation.put(new NameValuePair(VALUE_STRING, (Constant)cstArray));
    annotation.setImmutable();
    return annotation;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\AnnotationUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */