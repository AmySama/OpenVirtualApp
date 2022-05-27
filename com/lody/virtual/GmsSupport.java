package com.lody.virtual;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.HostPackageManager;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.remote.VAppInstallerParams;
import com.lody.virtual.remote.VAppInstallerResult;
import java.io.File;
import java.util.HashSet;

public class GmsSupport {
  public static final String GMS_PKG = "com.google.android.gms";
  
  private static final HashSet<String> GOOGLE_APP;
  
  private static final HashSet<String> GOOGLE_SERVICE;
  
  public static final String GSF_PKG = "com.google.android.gsf";
  
  private static final String TAG = GmsSupport.class.getSimpleName();
  
  public static final String VENDING_PKG = "com.android.vending";
  
  static {
    GOOGLE_APP = new HashSet<String>();
    GOOGLE_SERVICE = new HashSet<String>();
    GOOGLE_APP.add("com.android.vending");
    GOOGLE_APP.add("com.google.android.play.games");
    GOOGLE_APP.add("com.google.android.wearable.app");
    GOOGLE_APP.add("com.google.android.wearable.app.cn");
    GOOGLE_SERVICE.add("com.google.android.gms");
    GOOGLE_SERVICE.add("com.google.android.gsf");
    GOOGLE_SERVICE.add("com.google.android.gsf.login");
    GOOGLE_SERVICE.add("com.google.android.backuptransport");
    GOOGLE_SERVICE.add("com.google.android.backup");
    GOOGLE_SERVICE.add("com.google.android.configupdater");
    GOOGLE_SERVICE.add("com.google.android.syncadapters.contacts");
    GOOGLE_SERVICE.add("com.google.android.feedback");
    GOOGLE_SERVICE.add("com.google.android.onetimeinitializer");
    GOOGLE_SERVICE.add("com.google.android.partnersetup");
    GOOGLE_SERVICE.add("com.google.android.setupwizard");
    GOOGLE_SERVICE.add("com.google.android.syncadapters.calendar");
  }
  
  public static void installDynamicGms(int paramInt) {
    VAppInstallerResult vAppInstallerResult;
    VirtualCore virtualCore = VirtualCore.get();
    if (paramInt == 0) {
      VAppInstallerParams vAppInstallerParams = new VAppInstallerParams(2);
      VAppInstallerResult vAppInstallerResult1 = virtualCore.installPackage(Uri.parse("package:com.google.android.gsf"), vAppInstallerParams);
      String str3 = TAG;
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append("install gsf result:");
      stringBuilder2.append(vAppInstallerResult1.status);
      VLog.w(str3, stringBuilder2.toString(), new Object[0]);
      VAppInstallerResult vAppInstallerResult2 = virtualCore.installPackage(Uri.parse("package:com.google.android.gms"), vAppInstallerParams);
      String str1 = TAG;
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append("install gms result:");
      stringBuilder2.append(vAppInstallerResult2.status);
      VLog.w(str1, stringBuilder2.toString(), new Object[0]);
      vAppInstallerResult = virtualCore.installPackage(Uri.parse("package:com.android.vending"), vAppInstallerParams);
      String str2 = TAG;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("install vending result:");
      stringBuilder1.append(vAppInstallerResult.status);
      VLog.w(str2, stringBuilder1.toString(), new Object[0]);
    } else {
      vAppInstallerResult.installPackageAsUser(paramInt, "com.google.android.gms");
      vAppInstallerResult.installPackageAsUser(paramInt, "com.google.android.gsf");
      vAppInstallerResult.installPackageAsUser(paramInt, "com.android.vending");
    } 
  }
  
  public static void installGApps(File paramFile, int paramInt) {
    installPackages(paramFile, paramInt);
  }
  
  private static void installPackages(File paramFile, int paramInt) {
    VirtualCore virtualCore = VirtualCore.get();
    File[] arrayOfFile = paramFile.listFiles();
    if (arrayOfFile != null) {
      int i = arrayOfFile.length;
      for (paramInt = 0; paramInt < i; paramInt++) {
        File file = arrayOfFile[paramInt];
        if (file.getName().endsWith(".apk")) {
          String str = file.getPath();
          VAppInstallerParams vAppInstallerParams = new VAppInstallerParams(2);
          VAppInstallerResult vAppInstallerResult = virtualCore.installPackage(Uri.fromFile(file), vAppInstallerParams);
          if (vAppInstallerResult.status == 0) {
            String str1 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("install gms pkg success:");
            stringBuilder.append(str);
            VLog.w(str1, stringBuilder.toString(), new Object[0]);
          } else {
            String str1 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("install gms pkg fail:");
            stringBuilder.append(str);
            stringBuilder.append(",error : ");
            stringBuilder.append(vAppInstallerResult.status);
            VLog.w(str1, stringBuilder.toString(), new Object[0]);
          } 
        } 
      } 
    } 
  }
  
  public static boolean isGoogleAppOrService(String paramString) {
    return (GOOGLE_APP.contains(paramString) || GOOGLE_SERVICE.contains(paramString));
  }
  
  public static boolean isGoogleFrameworkInstalled() {
    return VirtualCore.get().isAppInstalled("com.google.android.gms");
  }
  
  public static boolean isGoogleService(String paramString) {
    return GOOGLE_SERVICE.contains(paramString);
  }
  
  public static boolean isInstalledGoogleService() {
    return VirtualCore.get().isAppInstalled("com.google.android.gms");
  }
  
  public static boolean isOutsideGoogleFrameworkExist() {
    boolean bool;
    if (VirtualCore.get().isOutsideInstalled("com.google.android.gms") && VirtualCore.get().isOutsideInstalled("com.google.android.gsf")) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isOutsideSupportGms() {
    HostPackageManager hostPackageManager = VirtualCore.get().getHostPackageManager();
    PackageManager.NameNotFoundException nameNotFoundException1 = null;
    try {
      ApplicationInfo applicationInfo = hostPackageManager.getApplicationInfo("com.google.android.gms", 0);
    } catch (android.content.pm.PackageManager.NameNotFoundException null) {
      nameNotFoundException2.printStackTrace();
      nameNotFoundException2 = null;
    } 
    if (nameNotFoundException2 == null)
      return false; 
    try {
      ApplicationInfo applicationInfo = hostPackageManager.getApplicationInfo("com.google.android.gsf", 0);
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException2) {
      nameNotFoundException2.printStackTrace();
      nameNotFoundException2 = nameNotFoundException1;
    } 
    return !(nameNotFoundException2 == null);
  }
  
  public static void remove(String paramString) {
    GOOGLE_SERVICE.remove(paramString);
    GOOGLE_APP.remove(paramString);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\GmsSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */