package com.android.dx.rop.cst;

import com.android.dx.rop.annotation.Annotation;

public final class CstAnnotation extends Constant {
  private final Annotation annotation;
  
  public CstAnnotation(Annotation paramAnnotation) {
    if (paramAnnotation != null) {
      paramAnnotation.throwIfMutable();
      this.annotation = paramAnnotation;
      return;
    } 
    throw new NullPointerException("annotation == null");
  }
  
  protected int compareTo0(Constant paramConstant) {
    return this.annotation.compareTo(((CstAnnotation)paramConstant).annotation);
  }
  
  public boolean equals(Object paramObject) {
    return !(paramObject instanceof CstAnnotation) ? false : this.annotation.equals(((CstAnnotation)paramObject).annotation);
  }
  
  public Annotation getAnnotation() {
    return this.annotation;
  }
  
  public int hashCode() {
    return this.annotation.hashCode();
  }
  
  public boolean isCategory2() {
    return false;
  }
  
  public String toHuman() {
    return this.annotation.toString();
  }
  
  public String toString() {
    return this.annotation.toString();
  }
  
  public String typeName() {
    return "annotation";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */