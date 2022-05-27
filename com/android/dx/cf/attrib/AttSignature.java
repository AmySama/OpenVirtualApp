package com.android.dx.cf.attrib;

import com.android.dx.rop.cst.CstString;

public final class AttSignature extends BaseAttribute {
  public static final String ATTRIBUTE_NAME = "Signature";
  
  private final CstString signature;
  
  public AttSignature(CstString paramCstString) {
    super("Signature");
    if (paramCstString != null) {
      this.signature = paramCstString;
      return;
    } 
    throw new NullPointerException("signature == null");
  }
  
  public int byteLength() {
    return 8;
  }
  
  public CstString getSignature() {
    return this.signature;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\attrib\AttSignature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */