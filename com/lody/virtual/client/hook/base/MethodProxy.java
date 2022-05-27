package com.lody.virtual.client.hook.base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.SettingConfig;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.LocalPackageCache;
import com.lody.virtual.client.hook.annotations.LogInvocation;
import com.lody.virtual.client.ipc.VirtualLocationManager;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.remote.VDeviceConfig;
import java.lang.reflect.Method;

public abstract class MethodProxy {
  private boolean enable = true;
  
  private LogInvocation.Condition mInvocationLoggingCondition = LogInvocation.Condition.NEVER;
  
  public MethodProxy() {
    LogInvocation logInvocation = getClass().<LogInvocation>getAnnotation(LogInvocation.class);
    if (logInvocation != null)
      this.mInvocationLoggingCondition = logInvocation.value(); 
  }
  
  public static String getAppPkg() {
    return VClient.get().getCurrentPackage();
  }
  
  public static int getAppUserId() {
    return VUserHandle.getUserId(getVUid());
  }
  
  protected static int getBaseVUid() {
    return VClient.get().getBaseVUid();
  }
  
  protected static SettingConfig getConfig() {
    return VirtualCore.getConfig();
  }
  
  protected static VDeviceConfig getDeviceConfig() {
    return VClient.get().getDeviceConfig();
  }
  
  protected static Context getHostContext() {
    return VirtualCore.get().getContext();
  }
  
  public static String getHostPkg() {
    return VirtualCore.get().getHostPkg();
  }
  
  protected static int getRealUid() {
    return VirtualCore.get().myUid();
  }
  
  public static int getRealUserId() {
    return VUserHandle.realUserId();
  }
  
  protected static int getVUid() {
    return VClient.get().getVUid();
  }
  
  protected static boolean isAppProcess() {
    return VirtualCore.get().isVAppProcess();
  }
  
  protected static boolean isFakeLocationEnable() {
    boolean bool;
    if (VirtualLocationManager.get().getMode(VUserHandle.myUserId(), VClient.get().getCurrentPackage()) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isHostIntent(Intent paramIntent) {
    if (VirtualCore.getConfig().isHostIntent(paramIntent))
      return true; 
    ComponentName componentName = paramIntent.getComponent();
    return (componentName != null) ? isOutsidePackage(componentName.getPackageName()) : false;
  }
  
  public static boolean isInsidePackage(String paramString) {
    return VirtualCore.get().isAppInstalled(paramString);
  }
  
  protected static boolean isMainProcess() {
    return VirtualCore.get().isMainProcess();
  }
  
  public static boolean isOutsidePackage(String paramString) {
    return LocalPackageCache.isOutsideVisiblePackage(paramString);
  }
  
  protected static boolean isServerProcess() {
    return VirtualCore.get().isServerProcess();
  }
  
  public static void replaceFirstUserId(Object[] paramArrayOfObject) {
    if (getRealUserId() == 0)
      return; 
    for (byte b = 0; b < paramArrayOfObject.length; b++) {
      if (paramArrayOfObject[b] == Integer.valueOf(0)) {
        paramArrayOfObject[b] = Integer.valueOf(getRealUserId());
        return;
      } 
    } 
  }
  
  public static void replaceLastUserId(Object[] paramArrayOfObject) {
    if (getRealUserId() == 0)
      return; 
    byte b = -1;
    for (byte b1 = 0; b1 < paramArrayOfObject.length; b1++) {
      if (paramArrayOfObject[b1] == Integer.valueOf(0))
        b = b1; 
    } 
    if (b >= 0)
      paramArrayOfObject[b] = Integer.valueOf(getRealUserId()); 
  }
  
  public Object afterCall(Object paramObject1, Method paramMethod, Object[] paramArrayOfObject, Object paramObject2) throws Throwable {
    return paramObject2;
  }
  
  public boolean beforeCall(Object paramObject, Method paramMethod, Object... paramVarArgs) {
    return true;
  }
  
  public Object call(Object paramObject, Method paramMethod, Object... paramVarArgs) throws Throwable {
    return paramMethod.invoke(paramObject, paramVarArgs);
  }
  
  public LogInvocation.Condition getInvocationLoggingCondition() {
    return this.mInvocationLoggingCondition;
  }
  
  public abstract String getMethodName();
  
  protected PackageManager getPM() {
    return VirtualCore.getPM();
  }
  
  public boolean isAppPkg(String paramString) {
    return VirtualCore.get().isAppInstalled(paramString);
  }
  
  public boolean isEnable() {
    return this.enable;
  }
  
  public boolean isHostPkg(String paramString) {
    SettingConfig settingConfig = VirtualCore.getConfig();
    return (paramString.equals(getConfig().getMainPackageName()) || paramString.equals(settingConfig.getExtPackageName()));
  }
  
  public void setEnable(boolean paramBoolean) {
    this.enable = paramBoolean;
  }
  
  public void setInvocationloggingCondition(LogInvocation.Condition paramCondition) {
    this.mInvocationLoggingCondition = paramCondition;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Method : ");
    stringBuilder.append(getMethodName());
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\base\MethodProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */