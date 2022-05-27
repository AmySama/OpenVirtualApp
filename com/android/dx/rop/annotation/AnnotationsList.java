package com.android.dx.rop.annotation;

import com.android.dx.util.FixedSizeList;

public final class AnnotationsList extends FixedSizeList {
  public static final AnnotationsList EMPTY = new AnnotationsList(0);
  
  public AnnotationsList(int paramInt) {
    super(paramInt);
  }
  
  public static AnnotationsList combine(AnnotationsList paramAnnotationsList1, AnnotationsList paramAnnotationsList2) {
    int i = paramAnnotationsList1.size();
    if (i == paramAnnotationsList2.size()) {
      AnnotationsList annotationsList = new AnnotationsList(i);
      for (byte b = 0; b < i; b++)
        annotationsList.set(b, Annotations.combine(paramAnnotationsList1.get(b), paramAnnotationsList2.get(b))); 
      annotationsList.setImmutable();
      return annotationsList;
    } 
    throw new IllegalArgumentException("list1.size() != list2.size()");
  }
  
  public Annotations get(int paramInt) {
    return (Annotations)get0(paramInt);
  }
  
  public void set(int paramInt, Annotations paramAnnotations) {
    paramAnnotations.throwIfMutable();
    set0(paramInt, paramAnnotations);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\annotation\AnnotationsList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */