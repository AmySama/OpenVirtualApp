package com.android.dex;

public final class DexFormat {
  public static final int API_CONST_METHOD_HANDLE = 28;
  
  public static final int API_CURRENT = 28;
  
  public static final int API_DEFINE_INTERFACE_METHODS = 24;
  
  public static final int API_INVOKE_INTERFACE_METHODS = 24;
  
  public static final int API_INVOKE_STATIC_INTERFACE_METHODS = 21;
  
  public static final int API_METHOD_HANDLES = 26;
  
  public static final int API_NO_EXTENDED_OPCODES = 13;
  
  public static final String DEX_IN_JAR_NAME = "classes.dex";
  
  public static final int ENDIAN_TAG = 305419896;
  
  public static final String MAGIC_PREFIX = "dex\n";
  
  public static final String MAGIC_SUFFIX = "\000";
  
  public static final int MAX_MEMBER_IDX = 65535;
  
  public static final int MAX_TYPE_IDX = 65535;
  
  public static final String VERSION_CURRENT = "039";
  
  public static final String VERSION_FOR_API_13 = "035";
  
  public static final String VERSION_FOR_API_24 = "037";
  
  public static final String VERSION_FOR_API_26 = "038";
  
  public static final String VERSION_FOR_API_28 = "039";
  
  public static String apiToMagic(int paramInt) {
    String str = "039";
    if (paramInt < 28 && paramInt < 28)
      if (paramInt >= 26) {
        str = "038";
      } else if (paramInt >= 24) {
        str = "037";
      } else {
        str = "035";
      }  
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("dex\n");
    stringBuilder.append(str);
    stringBuilder.append("\000");
    return stringBuilder.toString();
  }
  
  public static boolean isSupportedDexMagic(byte[] paramArrayOfbyte) {
    boolean bool;
    if (magicToApi(paramArrayOfbyte) > 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static int magicToApi(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte.length != 8)
      return -1; 
    if (paramArrayOfbyte[0] == 100 && paramArrayOfbyte[1] == 101 && paramArrayOfbyte[2] == 120 && paramArrayOfbyte[3] == 10 && paramArrayOfbyte[7] == 0) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("");
      stringBuilder.append((char)paramArrayOfbyte[4]);
      stringBuilder.append((char)paramArrayOfbyte[5]);
      stringBuilder.append((char)paramArrayOfbyte[6]);
      String str = stringBuilder.toString();
      if (str.equals("035"))
        return 13; 
      if (str.equals("037"))
        return 24; 
      if (str.equals("038"))
        return 26; 
      if (str.equals("039"))
        return 28; 
      if (str.equals("039"))
        return 28; 
    } 
    return -1;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dex\DexFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */