package de.robv.android.xposed;

import de.robv.android.xposed.services.BaseService;
import de.robv.android.xposed.services.DirectAccessService;

public final class SELinuxHelper {
  private static boolean sIsSELinuxEnabled;
  
  private static BaseService sServiceAppDataFile = (BaseService)new DirectAccessService();
  
  public static BaseService getAppDataFileService() {
    BaseService baseService = sServiceAppDataFile;
    return (BaseService)((baseService != null) ? baseService : new DirectAccessService());
  }
  
  public static String getContext() {
    return null;
  }
  
  public static boolean isSELinuxEnabled() {
    return sIsSELinuxEnabled;
  }
  
  public static boolean isSELinuxEnforced() {
    return sIsSELinuxEnabled;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\SELinuxHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */