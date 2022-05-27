package com.lody.virtual.client.stub;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.server.pm.VPackageManagerService;
import java.util.Locale;

public class StubManifest {
  public static final boolean BLOCK_GMS_CHIMERA = false;
  
  public static String EXT_PACKAGE_NAME;
  
  public static String EXT_PROXY_CP_AUTHORITY;
  
  public static String EXT_STUB_CP_AUTHORITY;
  
  public static String PACKAGE_NAME;
  
  public static String PROXY_CP_AUTHORITY;
  
  public static String RESOLVER_ACTIVITY;
  
  public static String STUB_ACTIVITY = ShadowActivity.class.getName();
  
  public static int STUB_COUNT;
  
  public static String STUB_CP;
  
  public static String STUB_CP_AUTHORITY;
  
  public static String STUB_DIALOG = ShadowDialogActivity.class.getName();
  
  public static String STUB_JOB;
  
  public static String STUB_SERVICE;
  
  static {
    STUB_CP = ShadowContentProvider.class.getName();
    STUB_JOB = ShadowJobService.class.getName();
    STUB_SERVICE = ShadowService.class.getName();
    RESOLVER_ACTIVITY = ResolverActivity.class.getName();
    STUB_CP_AUTHORITY = null;
    EXT_STUB_CP_AUTHORITY = null;
    PROXY_CP_AUTHORITY = null;
    EXT_PROXY_CP_AUTHORITY = null;
    STUB_COUNT = 100;
  }
  
  public static String getProxyAuthority(boolean paramBoolean) {
    String str;
    if (paramBoolean) {
      str = EXT_PROXY_CP_AUTHORITY;
    } else {
      str = PROXY_CP_AUTHORITY;
    } 
    return str;
  }
  
  public static String getStubActivityName(int paramInt, ActivityInfo paramActivityInfo) {
    try {
      ComponentName componentName = new ComponentName();
      this(paramActivityInfo.packageName, paramActivityInfo.name);
      ActivityInfo activityInfo = VPackageManagerService.get().getActivityInfo(componentName, 0, 0);
      boolean bool = isFixedOrientationLandscape(activityInfo);
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("getStubActivityName isFixedOrientationLandscape:");
      stringBuilder.append(bool);
      stringBuilder.append(",info:");
      stringBuilder.append(activityInfo);
      stringBuilder.append(",index:");
      stringBuilder.append(paramInt);
      VLog.d("VA", stringBuilder.toString(), new Object[0]);
      if (bool)
        return String.format(Locale.ENGLISH, "%s$P%d_Land", new Object[] { STUB_ACTIVITY, Integer.valueOf(paramInt) }); 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return String.format(Locale.ENGLISH, "%s$P%d", new Object[] { STUB_ACTIVITY, Integer.valueOf(paramInt) });
  }
  
  public static String getStubAuthority(int paramInt, boolean paramBoolean) {
    String str;
    Locale locale = Locale.ENGLISH;
    if (paramBoolean) {
      str = EXT_STUB_CP_AUTHORITY;
    } else {
      str = STUB_CP_AUTHORITY;
    } 
    return String.format(locale, "%s%d", new Object[] { str, Integer.valueOf(paramInt) });
  }
  
  public static String getStubContentProviderName(int paramInt) {
    return String.format(Locale.ENGLISH, "%s$P%d", new Object[] { STUB_CP, Integer.valueOf(paramInt) });
  }
  
  public static String getStubDialogName(int paramInt, ActivityInfo paramActivityInfo) {
    try {
      ComponentName componentName = new ComponentName();
      this(paramActivityInfo.packageName, paramActivityInfo.name);
      ActivityInfo activityInfo = VPackageManagerService.get().getActivityInfo(componentName, 0, 0);
      boolean bool = isFixedOrientationLandscape(activityInfo);
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("getStubDialogName isFixedOrientationLandscape:");
      stringBuilder.append(bool);
      stringBuilder.append(",info:");
      stringBuilder.append(activityInfo);
      stringBuilder.append(",index:");
      stringBuilder.append(paramInt);
      VLog.d("VA", stringBuilder.toString(), new Object[0]);
      if (bool)
        return String.format(Locale.ENGLISH, "%s$P%d_Land", new Object[] { STUB_DIALOG, Integer.valueOf(paramInt) }); 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return String.format(Locale.ENGLISH, "%s$P%d", new Object[] { STUB_DIALOG, Integer.valueOf(paramInt) });
  }
  
  public static String getStubPackageName(boolean paramBoolean) {
    String str;
    if (paramBoolean) {
      str = EXT_PACKAGE_NAME;
    } else {
      str = PACKAGE_NAME;
    } 
    return str;
  }
  
  public static String getStubServiceName(int paramInt) {
    return String.format(Locale.ENGLISH, "%s$P%d", new Object[] { STUB_SERVICE, Integer.valueOf(paramInt) });
  }
  
  public static boolean isFixedOrientationLandscape(ActivityInfo paramActivityInfo) {
    return (paramActivityInfo.screenOrientation == 0 || paramActivityInfo.screenOrientation == 6 || paramActivityInfo.screenOrientation == 8 || paramActivityInfo.screenOrientation == 11);
  }
  
  public static boolean isHostPackageName(String paramString) {
    return (PACKAGE_NAME.equals(paramString) || EXT_PACKAGE_NAME.equals(paramString));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\StubManifest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */