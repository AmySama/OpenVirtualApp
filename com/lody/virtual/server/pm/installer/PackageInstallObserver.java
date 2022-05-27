package com.lody.virtual.server.pm.installer;

import android.content.Intent;
import android.content.pm.IPackageInstallObserver2;
import android.os.Bundle;

public class PackageInstallObserver {
  private final IPackageInstallObserver2.Stub mBinder = new IPackageInstallObserver2.Stub() {
      public void onPackageInstalled(String param1String1, int param1Int, String param1String2, Bundle param1Bundle) {
        PackageInstallObserver.this.onPackageInstalled(param1String1, param1Int, param1String2, param1Bundle);
      }
      
      public void onUserActionRequired(Intent param1Intent) {
        PackageInstallObserver.this.onUserActionRequired(param1Intent);
      }
    };
  
  public IPackageInstallObserver2 getBinder() {
    return (IPackageInstallObserver2)this.mBinder;
  }
  
  public void onPackageInstalled(String paramString1, int paramInt, String paramString2, Bundle paramBundle) {}
  
  public void onUserActionRequired(Intent paramIntent) {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\installer\PackageInstallObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */