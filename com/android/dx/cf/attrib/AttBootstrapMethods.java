package com.android.dx.cf.attrib;

import com.android.dx.cf.code.BootstrapMethodsList;

public class AttBootstrapMethods extends BaseAttribute {
  private static final int ATTRIBUTE_HEADER_BYTES = 8;
  
  public static final String ATTRIBUTE_NAME = "BootstrapMethods";
  
  private static final int BOOTSTRAP_ARGUMENT_BYTES = 2;
  
  private static final int BOOTSTRAP_METHOD_BYTES = 4;
  
  private final BootstrapMethodsList bootstrapMethods;
  
  private final int byteLength;
  
  public AttBootstrapMethods(BootstrapMethodsList paramBootstrapMethodsList) {
    super("BootstrapMethods");
    this.bootstrapMethods = paramBootstrapMethodsList;
    int i = paramBootstrapMethodsList.size() * 4 + 8;
    for (byte b = 0; b < paramBootstrapMethodsList.size(); b++)
      i += paramBootstrapMethodsList.get(b).getBootstrapMethodArguments().size() * 2; 
    this.byteLength = i;
  }
  
  public int byteLength() {
    return this.byteLength;
  }
  
  public BootstrapMethodsList getBootstrapMethods() {
    return this.bootstrapMethods;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\attrib\AttBootstrapMethods.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */