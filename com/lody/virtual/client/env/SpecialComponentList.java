package com.lody.virtual.client.env;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import com.lody.virtual.client.core.VirtualCore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import mirror.android.content.IntentFilter;

public final class SpecialComponentList {
  private static final List<String> ACTION_BLACK_LIST;
  
  private static final HashSet<String> BROADCAST_START_WHITE_LIST;
  
  private static final List<String> GMS_BLOCK_ACTION_LIST;
  
  private static final List<ComponentName> GMS_BLOCK_COMPONENT = Arrays.asList(new ComponentName[] { new ComponentName("com.google.android.gms", "com.google.android.gms.update.SystemUpdateService"), new ComponentName("com.google.android.gsf", "com.google.android.gsf.update.SystemUpdateService") });
  
  private static final HashSet<String> INSTRUMENTATION_CONFLICTING;
  
  private static final Set<String> PRE_INSTALL_PACKAGES;
  
  private static final Map<String, String> PROTECTED_ACTION_MAP;
  
  private static final String PROTECT_ACTION_PREFIX = "_VA_protected_";
  
  private static final HashSet<String> SPEC_SYSTEM_APP_LIST;
  
  public static final Set<String> SYSTEM_BROADCAST_ACTION;
  
  private static final HashSet<String> WHITE_PERMISSION;
  
  static {
    GMS_BLOCK_ACTION_LIST = Arrays.asList(new String[] { "com.google.android.gms.update.START_SERVICE" });
    ACTION_BLACK_LIST = new ArrayList<String>(2);
    PROTECTED_ACTION_MAP = new HashMap<String, String>(5);
    WHITE_PERMISSION = new HashSet<String>(3);
    BROADCAST_START_WHITE_LIST = new HashSet<String>();
    INSTRUMENTATION_CONFLICTING = new HashSet<String>(2);
    SPEC_SYSTEM_APP_LIST = new HashSet<String>(3);
    SYSTEM_BROADCAST_ACTION = new HashSet<String>(7);
    PRE_INSTALL_PACKAGES = new HashSet<String>(7);
    SYSTEM_BROADCAST_ACTION.add("android.intent.action.SCREEN_ON");
    SYSTEM_BROADCAST_ACTION.add("android.intent.action.SCREEN_OFF");
    SYSTEM_BROADCAST_ACTION.add("android.intent.action.NEW_OUTGOING_CALL");
    SYSTEM_BROADCAST_ACTION.add("android.intent.action.TIME_TICK");
    SYSTEM_BROADCAST_ACTION.add("android.intent.action.TIME_SET");
    SYSTEM_BROADCAST_ACTION.add("android.intent.action.TIMEZONE_CHANGED");
    SYSTEM_BROADCAST_ACTION.add("android.intent.action.BATTERY_CHANGED");
    SYSTEM_BROADCAST_ACTION.add("android.intent.action.BATTERY_LOW");
    SYSTEM_BROADCAST_ACTION.add("android.intent.action.BATTERY_OKAY");
    SYSTEM_BROADCAST_ACTION.add("android.intent.action.ACTION_POWER_CONNECTED");
    SYSTEM_BROADCAST_ACTION.add("android.intent.action.ACTION_POWER_DISCONNECTED");
    SYSTEM_BROADCAST_ACTION.add("android.intent.action.USER_PRESENT");
    SYSTEM_BROADCAST_ACTION.add("android.provider.Telephony.SMS_RECEIVED");
    SYSTEM_BROADCAST_ACTION.add("android.provider.Telephony.SMS_DELIVER");
    SYSTEM_BROADCAST_ACTION.add("android.net.wifi.STATE_CHANGE");
    SYSTEM_BROADCAST_ACTION.add("android.net.wifi.SCAN_RESULTS");
    SYSTEM_BROADCAST_ACTION.add("android.net.wifi.WIFI_STATE_CHANGED");
    SYSTEM_BROADCAST_ACTION.add("android.net.conn.CONNECTIVITY_CHANGE");
    SYSTEM_BROADCAST_ACTION.add("android.intent.action.ANY_DATA_STATE");
    SYSTEM_BROADCAST_ACTION.add("android.intent.action.SIM_STATE_CHANGED");
    SYSTEM_BROADCAST_ACTION.add("android.location.PROVIDERS_CHANGED");
    SYSTEM_BROADCAST_ACTION.add("android.location.MODE_CHANGED");
    SYSTEM_BROADCAST_ACTION.add("android.intent.action.HEADSET_PLUG");
    SYSTEM_BROADCAST_ACTION.add("android.media.VOLUME_CHANGED_ACTION");
    SYSTEM_BROADCAST_ACTION.add("android.intent.action.CONFIGURATION_CHANGED");
    SYSTEM_BROADCAST_ACTION.add("android.intent.action.DYNAMIC_SENSOR_CHANGED");
    SYSTEM_BROADCAST_ACTION.add("dynamic_sensor_change");
    ACTION_BLACK_LIST.add("android.appwidget.action.APPWIDGET_UPDATE");
    ACTION_BLACK_LIST.add("android.appwidget.action.APPWIDGET_CONFIGURE");
    WHITE_PERMISSION.add("com.google.android.gms.settings.SECURITY_SETTINGS");
    WHITE_PERMISSION.add("com.google.android.apps.plus.PRIVACY_SETTINGS");
    WHITE_PERMISSION.add("android.permission.ACCOUNT_MANAGER");
    PROTECTED_ACTION_MAP.put("android.intent.action.PACKAGE_ADDED", "virtual.android.intent.action.PACKAGE_ADDED");
    PROTECTED_ACTION_MAP.put("android.intent.action.PACKAGE_REMOVED", "virtual.android.intent.action.PACKAGE_REMOVED");
    PROTECTED_ACTION_MAP.put("android.intent.action.PACKAGE_CHANGED", "virtual.android.intent.action.PACKAGE_CHANGED");
    PROTECTED_ACTION_MAP.put("android.intent.action.USER_ADDED", "virtual.android.intent.action.USER_ADDED");
    PROTECTED_ACTION_MAP.put("android.intent.action.USER_REMOVED", "virtual.android.intent.action.USER_REMOVED");
    PROTECTED_ACTION_MAP.put("android.intent.action.MEDIA_SCANNER_SCAN_FILE", "android.intent.action.MEDIA_SCANNER_SCAN_FILE");
    INSTRUMENTATION_CONFLICTING.add("com.qihoo.magic");
    INSTRUMENTATION_CONFLICTING.add("com.qihoo.magic_mutiple");
    INSTRUMENTATION_CONFLICTING.add("com.facebook.katana");
    SPEC_SYSTEM_APP_LIST.add("android");
    SPEC_SYSTEM_APP_LIST.add("com.google.android.webview");
    SPEC_SYSTEM_APP_LIST.add("com.android.providers.downloads");
    SPEC_SYSTEM_APP_LIST.add("FelipeLeite.Sober.appicon");
    PRE_INSTALL_PACKAGES.add("com.huawei.hwid");
    PRE_INSTALL_PACKAGES.add("com.vivo.sdkplugin");
    PRE_INSTALL_PACKAGES.add("com.xiaomi.gamecenter.sdk.service");
  }
  
  public static void addBlackAction(String paramString) {
    ACTION_BLACK_LIST.add(paramString);
  }
  
  public static void addStaticBroadCastWhiteList(String paramString) {
    BROADCAST_START_WHITE_LIST.add(paramString);
  }
  
  public static boolean allowedStartFromBroadcast(String paramString) {
    return BROADCAST_START_WHITE_LIST.contains(paramString);
  }
  
  public static Set<String> getPreInstallPackages() {
    return PRE_INSTALL_PACKAGES;
  }
  
  public static String getProtectActionPrefix() {
    return "_VA_protected_";
  }
  
  public static boolean isActionInBlackList(String paramString) {
    return ACTION_BLACK_LIST.contains(paramString);
  }
  
  public static boolean isConflictingInstrumentation(String paramString) {
    return INSTRUMENTATION_CONFLICTING.contains(paramString);
  }
  
  public static boolean isSpecSystemPackage(String paramString) {
    return SPEC_SYSTEM_APP_LIST.contains(paramString);
  }
  
  public static boolean isWhitePermission(String paramString) {
    return WHITE_PERMISSION.contains(paramString);
  }
  
  public static String protectAction(String paramString) {
    if (paramString == null)
      return null; 
    if (VirtualCore.getConfig().isUnProtectAction(paramString))
      return paramString; 
    if (paramString.startsWith(getProtectActionPrefix()))
      return paramString; 
    String str1 = PROTECTED_ACTION_MAP.get(paramString);
    String str2 = str1;
    if (str1 == null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(getProtectActionPrefix());
      stringBuilder.append(paramString);
      str2 = stringBuilder.toString();
    } 
    return str2;
  }
  
  public static void protectIntent(Intent paramIntent) {
    String str = protectAction(paramIntent.getAction());
    if (str != null)
      paramIntent.setAction(str); 
  }
  
  public static void protectIntentFilter(IntentFilter paramIntentFilter) {
    if (paramIntentFilter != null) {
      ListIterator<String> listIterator = ((List)IntentFilter.mActions.get(paramIntentFilter)).listIterator();
      while (listIterator.hasNext()) {
        String str = listIterator.next();
        if (isActionInBlackList(str)) {
          listIterator.remove();
          continue;
        } 
        str = protectAction(str);
        if (str != null)
          listIterator.set(str); 
      } 
    } 
  }
  
  public static boolean shouldBlockIntent(Intent paramIntent) {
    ComponentName componentName = paramIntent.getComponent();
    if (componentName != null && GMS_BLOCK_COMPONENT.contains(componentName))
      return true; 
    String str = paramIntent.getAction();
    return (str != null && GMS_BLOCK_ACTION_LIST.contains(str));
  }
  
  public static String unprotectAction(String paramString) {
    if (paramString == null)
      return null; 
    if (paramString.startsWith(getProtectActionPrefix()))
      return paramString.substring(getProtectActionPrefix().length()); 
    for (Map.Entry<String, String> entry : PROTECTED_ACTION_MAP.entrySet()) {
      if (((String)entry.getValue()).equals(paramString))
        return (String)entry.getKey(); 
    } 
    return null;
  }
  
  public static Intent unprotectIntent(int paramInt, Intent paramIntent) {
    // Byte code:
    //   0: new com/lody/virtual/remote/BroadcastIntentData
    //   3: dup
    //   4: aload_1
    //   5: invokespecial <init> : (Landroid/content/Intent;)V
    //   8: astore_2
    //   9: aload_1
    //   10: astore_3
    //   11: aload_2
    //   12: getfield intent : Landroid/content/Intent;
    //   15: ifnull -> 63
    //   18: aload_2
    //   19: getfield userId : I
    //   22: iconst_m1
    //   23: if_icmpeq -> 36
    //   26: aload_1
    //   27: astore_3
    //   28: aload_2
    //   29: getfield userId : I
    //   32: iload_0
    //   33: if_icmpne -> 63
    //   36: aload_2
    //   37: getfield intent : Landroid/content/Intent;
    //   40: astore_1
    //   41: aload_1
    //   42: invokevirtual getAction : ()Ljava/lang/String;
    //   45: invokestatic unprotectAction : (Ljava/lang/String;)Ljava/lang/String;
    //   48: astore_2
    //   49: aload_1
    //   50: astore_3
    //   51: aload_2
    //   52: ifnull -> 63
    //   55: aload_1
    //   56: aload_2
    //   57: invokevirtual setAction : (Ljava/lang/String;)Landroid/content/Intent;
    //   60: pop
    //   61: aload_1
    //   62: astore_3
    //   63: aload_3
    //   64: areturn
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\env\SpecialComponentList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */