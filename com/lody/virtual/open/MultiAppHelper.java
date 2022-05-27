package com.lody.virtual.open;

import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.os.VUserManager;
import com.lody.virtual.remote.InstalledAppInfo;

public class MultiAppHelper {
  public static int installExistedPackage(InstalledAppInfo paramInstalledAppInfo) throws IllegalStateException {
    if (paramInstalledAppInfo != null) {
      int j;
      int[] arrayOfInt = paramInstalledAppInfo.getInstalledUsers();
      int i = arrayOfInt.length;
      byte b = 0;
      while (true) {
        j = i;
        if (b < arrayOfInt.length) {
          if (arrayOfInt[b] != b) {
            j = b;
            break;
          } 
          b++;
          continue;
        } 
        break;
      } 
      if (VUserManager.get().getUserInfo(j) == null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Space ");
        stringBuilder.append(j + 1);
        String str = stringBuilder.toString();
        if (VUserManager.get().createUser(str, 2) == null)
          throw new IllegalStateException(); 
      } 
      if (VirtualCore.get().installPackageAsUser(j, paramInstalledAppInfo.packageName))
        return j; 
      throw new IllegalStateException("install fail");
    } 
    throw new IllegalStateException("pkg must be installed.");
  }
  
  public static int installExistedPackage(String paramString) throws IllegalStateException {
    return installExistedPackage(VirtualCore.get().getInstalledAppInfo(paramString, 0));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\open\MultiAppHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */