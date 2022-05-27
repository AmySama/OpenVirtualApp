package com.android.dx.cf.attrib;

import com.android.dx.rop.cst.Constant;

public final class AttAnnotationDefault extends BaseAttribute {
  public static final String ATTRIBUTE_NAME = "AnnotationDefault";
  
  private final int byteLength;
  
  private final Constant value;
  
  public AttAnnotationDefault(Constant paramConstant, int paramInt) {
    super("AnnotationDefault");
    if (paramConstant != null) {
      this.value = paramConstant;
      this.byteLength = paramInt;
      return;
    } 
    throw new NullPointerException("value == null");
  }
  
  public int byteLength() {
    return this.byteLength + 6;
  }
  
  public Constant getValue() {
    return this.value;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\attrib\AttAnnotationDefault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */