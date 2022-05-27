package com.lody.virtual.helper.compat;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageParser;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Process;
import android.util.DisplayMetrics;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.os.VUserHandle;
import java.io.File;
import mirror.android.content.pm.PackageParser;
import mirror.android.content.pm.PackageParserJellyBean;
import mirror.android.content.pm.PackageParserJellyBean17;
import mirror.android.content.pm.PackageParserLollipop;
import mirror.android.content.pm.PackageParserLollipop22;
import mirror.android.content.pm.PackageParserMarshmallow;
import mirror.android.content.pm.PackageParserNougat;
import mirror.android.content.pm.PackageParserPie;
import mirror.android.content.pm.PackageUserState;

public class PackageParserCompat {
  static {
    API_LEVEL = Build.VERSION.SDK_INT;
    myUserId = VUserHandle.getUserId(Process.myUid());
    if (API_LEVEL >= 17) {
      object = PackageUserState.ctor.newInstance();
    } else {
      object = null;
    } 
    sUserState = object;
  }
  
  public static void collectCertificates(PackageParser paramPackageParser, PackageParser.Package paramPackage, int paramInt) throws Throwable {
    if (BuildCompat.isPie()) {
      PackageParserPie.collectCertificates.callWithException(new Object[] { paramPackage, Boolean.valueOf(true) });
    } else {
      int i = API_LEVEL;
      if (i >= 24) {
        PackageParserNougat.collectCertificates.callWithException(new Object[] { paramPackage, Integer.valueOf(paramInt) });
      } else if (i >= 23) {
        PackageParserMarshmallow.collectCertificates.callWithException(paramPackageParser, new Object[] { paramPackage, Integer.valueOf(paramInt) });
      } else if (i >= 22) {
        PackageParserLollipop22.collectCertificates.callWithException(paramPackageParser, new Object[] { paramPackage, Integer.valueOf(paramInt) });
      } else if (i >= 21) {
        PackageParserLollipop.collectCertificates.callWithException(paramPackageParser, new Object[] { paramPackage, Integer.valueOf(paramInt) });
      } else if (i >= 17) {
        PackageParserJellyBean17.collectCertificates.callWithException(paramPackageParser, new Object[] { paramPackage, Integer.valueOf(paramInt) });
      } else if (i >= 16) {
        PackageParserJellyBean.collectCertificates.callWithException(paramPackageParser, new Object[] { paramPackage, Integer.valueOf(paramInt) });
      } else {
        PackageParser.collectCertificates.call(paramPackageParser, new Object[] { paramPackage, Integer.valueOf(paramInt) });
      } 
    } 
  }
  
  public static PackageParser createParser(File paramFile) {
    int i = API_LEVEL;
    return (i >= 23) ? (PackageParser)PackageParserMarshmallow.ctor.newInstance() : ((i >= 22) ? (PackageParser)PackageParserLollipop22.ctor.newInstance() : ((i >= 21) ? (PackageParser)PackageParserLollipop.ctor.newInstance() : ((i >= 17) ? (PackageParser)PackageParserJellyBean17.ctor.newInstance(new Object[] { paramFile.getAbsolutePath() }) : ((i >= 16) ? (PackageParser)PackageParserJellyBean.ctor.newInstance(new Object[] { paramFile.getAbsolutePath() }) : (PackageParser)PackageParser.ctor.newInstance(new Object[] { paramFile.getAbsolutePath() })))));
  }
  
  public static ActivityInfo generateActivityInfo(PackageParser.Activity paramActivity, int paramInt) {
    int i = API_LEVEL;
    return (i >= 23) ? (ActivityInfo)PackageParserMarshmallow.generateActivityInfo.call(new Object[] { paramActivity, Integer.valueOf(paramInt), sUserState, Integer.valueOf(myUserId) }) : ((i >= 22) ? (ActivityInfo)PackageParserLollipop22.generateActivityInfo.call(new Object[] { paramActivity, Integer.valueOf(paramInt), sUserState, Integer.valueOf(myUserId) }) : ((i >= 21) ? (ActivityInfo)PackageParserLollipop.generateActivityInfo.call(new Object[] { paramActivity, Integer.valueOf(paramInt), sUserState, Integer.valueOf(myUserId) }) : ((i >= 17) ? (ActivityInfo)PackageParserJellyBean17.generateActivityInfo.call(new Object[] { paramActivity, Integer.valueOf(paramInt), sUserState, Integer.valueOf(myUserId) }) : ((i >= 16) ? (ActivityInfo)PackageParserJellyBean.generateActivityInfo.call(new Object[] { paramActivity, Integer.valueOf(paramInt), Boolean.valueOf(false), Integer.valueOf(1), Integer.valueOf(myUserId) }) : (ActivityInfo)PackageParser.generateActivityInfo.call(new Object[] { paramActivity, Integer.valueOf(paramInt) })))));
  }
  
  public static ApplicationInfo generateApplicationInfo(PackageParser.Package paramPackage, int paramInt) {
    int i = API_LEVEL;
    return (i >= 23) ? (ApplicationInfo)PackageParserMarshmallow.generateApplicationInfo.call(new Object[] { paramPackage, Integer.valueOf(paramInt), sUserState }) : ((i >= 22) ? (ApplicationInfo)PackageParserLollipop22.generateApplicationInfo.call(new Object[] { paramPackage, Integer.valueOf(paramInt), sUserState }) : ((i >= 21) ? (ApplicationInfo)PackageParserLollipop.generateApplicationInfo.call(new Object[] { paramPackage, Integer.valueOf(paramInt), sUserState }) : ((i >= 17) ? (ApplicationInfo)PackageParserJellyBean17.generateApplicationInfo.call(new Object[] { paramPackage, Integer.valueOf(paramInt), sUserState }) : ((i >= 16) ? (ApplicationInfo)PackageParserJellyBean.generateApplicationInfo.call(new Object[] { paramPackage, Integer.valueOf(paramInt), Boolean.valueOf(false), Integer.valueOf(1) }) : (ApplicationInfo)PackageParser.generateApplicationInfo.call(new Object[] { paramPackage, Integer.valueOf(paramInt) })))));
  }
  
  public static PackageInfo generatePackageInfo(PackageParser.Package paramPackage, int paramInt, long paramLong1, long paramLong2) {
    int i = API_LEVEL;
    return (i >= 23) ? (PackageInfo)PackageParserMarshmallow.generatePackageInfo.call(new Object[] { paramPackage, GIDS, Integer.valueOf(paramInt), Long.valueOf(paramLong1), Long.valueOf(paramLong2), null, sUserState }) : ((i >= 21) ? ((PackageParserLollipop22.generatePackageInfo != null) ? (PackageInfo)PackageParserLollipop22.generatePackageInfo.call(new Object[] { paramPackage, GIDS, Integer.valueOf(paramInt), Long.valueOf(paramLong1), Long.valueOf(paramLong2), null, sUserState }) : (PackageInfo)PackageParserLollipop.generatePackageInfo.call(new Object[] { paramPackage, GIDS, Integer.valueOf(paramInt), Long.valueOf(paramLong1), Long.valueOf(paramLong2), null, sUserState })) : ((i >= 17) ? (PackageInfo)PackageParserJellyBean17.generatePackageInfo.call(new Object[] { paramPackage, GIDS, Integer.valueOf(paramInt), Long.valueOf(paramLong1), Long.valueOf(paramLong2), null, sUserState }) : ((i >= 16) ? (PackageInfo)PackageParserJellyBean.generatePackageInfo.call(new Object[] { paramPackage, GIDS, Integer.valueOf(paramInt), Long.valueOf(paramLong1), Long.valueOf(paramLong2), null }) : (PackageInfo)PackageParser.generatePackageInfo.call(new Object[] { paramPackage, GIDS, Integer.valueOf(paramInt), Long.valueOf(paramLong1), Long.valueOf(paramLong2) }))));
  }
  
  public static ProviderInfo generateProviderInfo(PackageParser.Provider paramProvider, int paramInt) {
    int i = API_LEVEL;
    return (i >= 23) ? (ProviderInfo)PackageParserMarshmallow.generateProviderInfo.call(new Object[] { paramProvider, Integer.valueOf(paramInt), sUserState, Integer.valueOf(myUserId) }) : ((i >= 22) ? (ProviderInfo)PackageParserLollipop22.generateProviderInfo.call(new Object[] { paramProvider, Integer.valueOf(paramInt), sUserState, Integer.valueOf(myUserId) }) : ((i >= 21) ? (ProviderInfo)PackageParserLollipop.generateProviderInfo.call(new Object[] { paramProvider, Integer.valueOf(paramInt), sUserState, Integer.valueOf(myUserId) }) : ((i >= 17) ? (ProviderInfo)PackageParserJellyBean17.generateProviderInfo.call(new Object[] { paramProvider, Integer.valueOf(paramInt), sUserState, Integer.valueOf(myUserId) }) : ((i >= 16) ? (ProviderInfo)PackageParserJellyBean.generateProviderInfo.call(new Object[] { paramProvider, Integer.valueOf(paramInt), Boolean.valueOf(false), Integer.valueOf(1), Integer.valueOf(myUserId) }) : (ProviderInfo)PackageParser.generateProviderInfo.call(new Object[] { paramProvider, Integer.valueOf(paramInt) })))));
  }
  
  public static ServiceInfo generateServiceInfo(PackageParser.Service paramService, int paramInt) {
    int i = API_LEVEL;
    return (i >= 23) ? (ServiceInfo)PackageParserMarshmallow.generateServiceInfo.call(new Object[] { paramService, Integer.valueOf(paramInt), sUserState, Integer.valueOf(myUserId) }) : ((i >= 22) ? (ServiceInfo)PackageParserLollipop22.generateServiceInfo.call(new Object[] { paramService, Integer.valueOf(paramInt), sUserState, Integer.valueOf(myUserId) }) : ((i >= 21) ? (ServiceInfo)PackageParserLollipop.generateServiceInfo.call(new Object[] { paramService, Integer.valueOf(paramInt), sUserState, Integer.valueOf(myUserId) }) : ((i >= 17) ? (ServiceInfo)PackageParserJellyBean17.generateServiceInfo.call(new Object[] { paramService, Integer.valueOf(paramInt), sUserState, Integer.valueOf(myUserId) }) : ((i >= 16) ? (ServiceInfo)PackageParserJellyBean.generateServiceInfo.call(new Object[] { paramService, Integer.valueOf(paramInt), Boolean.valueOf(false), Integer.valueOf(1), Integer.valueOf(myUserId) }) : (ServiceInfo)PackageParser.generateServiceInfo.call(new Object[] { paramService, Integer.valueOf(paramInt) })))));
  }
  
  public static PackageParser.Package parsePackage(PackageParser paramPackageParser, File paramFile, int paramInt) throws Throwable {
    int i = API_LEVEL;
    return (i >= 23) ? (PackageParser.Package)PackageParserMarshmallow.parsePackage.callWithException(paramPackageParser, new Object[] { paramFile, Integer.valueOf(paramInt) }) : ((i >= 22) ? (PackageParser.Package)PackageParserLollipop22.parsePackage.callWithException(paramPackageParser, new Object[] { paramFile, Integer.valueOf(paramInt) }) : ((i >= 21) ? (PackageParser.Package)PackageParserLollipop.parsePackage.callWithException(paramPackageParser, new Object[] { paramFile, Integer.valueOf(paramInt) }) : ((i >= 17) ? (PackageParser.Package)PackageParserJellyBean17.parsePackage.callWithException(paramPackageParser, new Object[] { paramFile, null, new DisplayMetrics(), Integer.valueOf(paramInt) }) : ((i >= 16) ? (PackageParser.Package)PackageParserJellyBean.parsePackage.callWithException(paramPackageParser, new Object[] { paramFile, null, new DisplayMetrics(), Integer.valueOf(paramInt) }) : (PackageParser.Package)PackageParser.parsePackage.callWithException(paramPackageParser, new Object[] { paramFile, null, new DisplayMetrics(), Integer.valueOf(paramInt) })))));
  }
  
  static {
    Object object;
  }
  
  private static final int API_LEVEL;
  
  public static final int[] GIDS = VirtualCore.get().getGids();
  
  private static final int myUserId;
  
  private static final Object sUserState;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\PackageParserCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */