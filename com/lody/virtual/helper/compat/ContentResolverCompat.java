package com.lody.virtual.helper.compat;

public class ContentResolverCompat {
  public static final int SYNC_ERROR_AUTHENTICATION = 2;
  
  public static final int SYNC_ERROR_CONFLICT = 5;
  
  public static final int SYNC_ERROR_INTERNAL = 8;
  
  public static final int SYNC_ERROR_IO = 3;
  
  private static final String[] SYNC_ERROR_NAMES = new String[] { "already-in-progress", "authentication-error", "io-error", "parse-error", "conflict", "too-many-deletions", "too-many-retries", "internal-error" };
  
  public static final int SYNC_ERROR_PARSE = 4;
  
  public static final int SYNC_ERROR_SYNC_ALREADY_IN_PROGRESS = 1;
  
  public static final int SYNC_ERROR_TOO_MANY_DELETIONS = 6;
  
  public static final int SYNC_ERROR_TOO_MANY_RETRIES = 7;
  
  public static final String SYNC_EXTRAS_DISALLOW_METERED = "allow_metered";
  
  public static final String SYNC_EXTRAS_EXPECTED_DOWNLOAD = "expected_download";
  
  public static final String SYNC_EXTRAS_EXPECTED_UPLOAD = "expected_upload";
  
  public static final String SYNC_EXTRAS_PRIORITY = "sync_priority";
  
  public static final int SYNC_OBSERVER_TYPE_STATUS = 8;
  
  public static String syncErrorToString(int paramInt) {
    if (paramInt >= 1) {
      String[] arrayOfString = SYNC_ERROR_NAMES;
      if (paramInt <= arrayOfString.length)
        return arrayOfString[paramInt - 1]; 
    } 
    return String.valueOf(paramInt);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\ContentResolverCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */