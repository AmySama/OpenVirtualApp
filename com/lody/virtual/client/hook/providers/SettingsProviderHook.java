package com.lody.virtual.client.hook.providers;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IInterface;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.hook.base.MethodBox;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.remote.VDeviceConfig;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SettingsProviderHook extends ExternalProviderHook {
  private static final int METHOD_GET = 0;
  
  private static final int METHOD_PUT = 1;
  
  private static final Map<String, String> PRE_SET_VALUES;
  
  private static final String TAG = SettingsProviderHook.class.getSimpleName();
  
  static {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    PRE_SET_VALUES = (Map)hashMap;
    hashMap.put("user_setup_complete", "1");
    PRE_SET_VALUES.put("install_non_market_apps", "1");
  }
  
  public SettingsProviderHook(IInterface paramIInterface) {
    super(paramIInterface);
  }
  
  private static int getMethodType(String paramString) {
    return paramString.startsWith("GET_") ? 0 : (paramString.startsWith("PUT_") ? 1 : -1);
  }
  
  private static boolean isSecureMethod(String paramString) {
    return paramString.endsWith("secure");
  }
  
  private Bundle wrapBundle(String paramString1, String paramString2) {
    Bundle bundle = new Bundle();
    if (Build.VERSION.SDK_INT >= 24) {
      bundle.putString("name", paramString1);
      bundle.putString("value", paramString2);
    } else {
      bundle.putString(paramString1, paramString2);
    } 
    return bundle;
  }
  
  public Bundle call(MethodBox paramMethodBox, String paramString1, String paramString2, Bundle paramBundle) throws InvocationTargetException {
    if (!VClient.get().isProcessBound())
      return (Bundle)paramMethodBox.call(); 
    int i = getMethodType(paramString1);
    if (i == 0) {
      String str = PRE_SET_VALUES.get(paramString2);
      if (str != null)
        return wrapBundle(paramString2, str); 
      if ("android_id".equals(paramString2)) {
        VDeviceConfig vDeviceConfig = VClient.get().getDeviceConfig();
        if (vDeviceConfig.enable && vDeviceConfig.androidId != null)
          return wrapBundle("android_id", vDeviceConfig.androidId); 
      } 
    } 
    if (1 == i && isSecureMethod(paramString1))
      return null; 
    try {
      return (Bundle)paramMethodBox.call();
    } catch (InvocationTargetException invocationTargetException) {
      if (invocationTargetException.getCause() instanceof SecurityException)
        return null; 
      if (invocationTargetException.getCause() instanceof IllegalArgumentException)
        return null; 
      throw invocationTargetException;
    } 
  }
  
  public Object invoke(Object paramObject, Method paramMethod, Object... paramVarArgs) throws Throwable {
    String str = TAG;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("call ");
    stringBuilder.append(paramMethod.getName());
    stringBuilder.append(" -> ");
    stringBuilder.append(Arrays.toString(paramVarArgs));
    VLog.e(str, stringBuilder.toString());
    return super.invoke(paramObject, paramMethod, paramVarArgs);
  }
  
  protected void processArgs(Method paramMethod, Object... paramVarArgs) {
    super.processArgs(paramMethod, paramVarArgs);
  }
  
  public Cursor query(MethodBox paramMethodBox, Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2, Bundle paramBundle) throws InvocationTargetException {
    return paramUri.toString().equals("content://settings/config") ? null : super.query(paramMethodBox, paramUri, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2, paramBundle);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\providers\SettingsProviderHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */