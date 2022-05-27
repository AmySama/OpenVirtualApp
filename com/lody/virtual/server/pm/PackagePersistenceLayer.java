package com.lody.virtual.server.pm;

import android.net.Uri;
import android.os.Parcel;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.PersistenceLayer;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.remote.VAppInstallerParams;
import com.lody.virtual.server.pm.legacy.PackageSettingV1;
import com.lody.virtual.server.pm.legacy.PackageSettingV5;
import com.lody.virtual.server.pm.parser.VPackage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

class PackagePersistenceLayer extends PersistenceLayer {
  private static final int CURRENT_VERSION = 6;
  
  private static final char[] MAGIC = new char[] { 'v', 'p', 'k', 'g' };
  
  public boolean changed = false;
  
  private VAppManagerService mService;
  
  PackagePersistenceLayer(VAppManagerService paramVAppManagerService) {
    super(VEnvironment.getPackageListFile());
    this.mService = paramVAppManagerService;
  }
  
  public int getCurrentVersion() {
    return 6;
  }
  
  public void onPersistenceFileDamage() {}
  
  public void readPersistenceData(Parcel paramParcel, int paramInt) {
    Uri uri;
    if (paramInt != 6) {
      if (paramInt <= 5) {
        int j = paramParcel.readInt();
        ArrayList<PackageSettingV5> arrayList = new ArrayList(j);
        while (j > 0) {
          PackageSettingV5 packageSettingV5;
          if (paramInt < 5) {
            this.changed = true;
            PackageSettingV1 packageSettingV1 = new PackageSettingV1();
            packageSettingV1.readFromParcel(paramParcel, paramInt);
            packageSettingV5 = new PackageSettingV5();
            packageSettingV5.packageName = packageSettingV1.packageName;
            packageSettingV5.appMode = packageSettingV1.notCopyApk;
            packageSettingV5.appId = packageSettingV1.appId;
            packageSettingV5.flag = packageSettingV1.flag;
            packageSettingV5.userState = packageSettingV1.userState;
            packageSettingV5.firstInstallTime = System.currentTimeMillis();
            packageSettingV5.lastUpdateTime = packageSettingV5.firstInstallTime;
          } else {
            packageSettingV5 = new PackageSettingV5(paramInt, paramParcel);
          } 
          arrayList.add(packageSettingV5);
          j--;
        } 
        for (PackageSettingV5 packageSettingV5 : arrayList) {
          if (packageSettingV5.appMode == 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("package:");
            stringBuilder.append(packageSettingV5.packageName);
            uri = Uri.parse(stringBuilder.toString());
          } else {
            File file2 = VEnvironment.getPackageFile(packageSettingV5.packageName);
            File file1 = file2;
            if (!file2.exists())
              file1 = VEnvironment.getPackageFileExt(packageSettingV5.packageName); 
            if (file1.exists()) {
              uri = Uri.fromFile(file1);
            } else {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("package:");
              stringBuilder.append(packageSettingV5.packageName);
              uri = Uri.parse(stringBuilder.toString());
            } 
          } 
          if (uri != null) {
            VAppInstallerParams vAppInstallerParams = new VAppInstallerParams(26, 1);
            if ((VirtualCore.get().installPackage(uri, vAppInstallerParams)).status == 0) {
              (PackageCacheManager.getSetting(packageSettingV5.packageName)).userState = packageSettingV5.userState;
              continue;
            } 
            VLog.e("PackagePersistenceLayer", "update package info failed : install %s failed", new Object[] { packageSettingV5.packageName });
          } 
        } 
        save();
        this.changed = true;
      } else {
        onPersistenceFileDamage();
      } 
      return;
    } 
    for (int i = uri.readInt(); i > 0; i--) {
      PackageSetting packageSetting = new PackageSetting(paramInt, (Parcel)uri);
      if (!this.mService.loadPackage(packageSetting))
        this.changed = true; 
    } 
  }
  
  public boolean verifyMagic(Parcel paramParcel) {
    return Arrays.equals(paramParcel.createCharArray(), MAGIC);
  }
  
  public void writeMagic(Parcel paramParcel) {
    paramParcel.writeCharArray(MAGIC);
  }
  
  public void writePersistenceData(Parcel paramParcel) {
    synchronized (PackageCacheManager.PACKAGE_CACHE) {
      paramParcel.writeInt(PackageCacheManager.PACKAGE_CACHE.size());
      Iterator iterator = PackageCacheManager.PACKAGE_CACHE.values().iterator();
      while (iterator.hasNext())
        ((PackageSetting)((VPackage)iterator.next()).mExtras).writeToParcel(paramParcel, 0); 
      return;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\PackagePersistenceLayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */