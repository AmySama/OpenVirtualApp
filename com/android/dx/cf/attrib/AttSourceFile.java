package com.android.dx.cf.attrib;

import com.android.dx.rop.cst.CstString;

public final class AttSourceFile extends BaseAttribute {
  public static final String ATTRIBUTE_NAME = "SourceFile";
  
  private final CstString sourceFile;
  
  public AttSourceFile(CstString paramCstString) {
    super("SourceFile");
    if (paramCstString != null) {
      this.sourceFile = paramCstString;
      return;
    } 
    throw new NullPointerException("sourceFile == null");
  }
  
  public int byteLength() {
    return 8;
  }
  
  public CstString getSourceFile() {
    return this.sourceFile;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\attrib\AttSourceFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */