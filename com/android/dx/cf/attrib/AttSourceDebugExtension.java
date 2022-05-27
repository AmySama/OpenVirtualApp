package com.android.dx.cf.attrib;

import com.android.dx.rop.cst.CstString;

public final class AttSourceDebugExtension extends BaseAttribute {
  public static final String ATTRIBUTE_NAME = "SourceDebugExtension";
  
  private final CstString smapString;
  
  public AttSourceDebugExtension(CstString paramCstString) {
    super("SourceDebugExtension");
    if (paramCstString != null) {
      this.smapString = paramCstString;
      return;
    } 
    throw new NullPointerException("smapString == null");
  }
  
  public int byteLength() {
    return this.smapString.getUtf8Size() + 6;
  }
  
  public CstString getSmapString() {
    return this.smapString;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\attrib\AttSourceDebugExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */