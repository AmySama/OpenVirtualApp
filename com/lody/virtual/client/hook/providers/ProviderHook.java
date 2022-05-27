package com.lody.virtual.client.hook.providers;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IInterface;
import com.lody.virtual.client.hook.base.MethodBox;
import com.lody.virtual.client.hook.secondary.ProxyBinder;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.VLog;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import mirror.android.content.IContentProvider;

public class ProviderHook implements InvocationHandler {
  private static final Map<String, HookFetcher> PROVIDER_MAP;
  
  public static final String QUERY_ARG_SQL_SELECTION = "android:query-arg-sql-selection";
  
  public static final String QUERY_ARG_SQL_SELECTION_ARGS = "android:query-arg-sql-selection-args";
  
  public static final String QUERY_ARG_SQL_SORT_ORDER = "android:query-arg-sql-sort-order";
  
  protected final IInterface mBase;
  
  protected IInterface mProxy;
  
  protected ProxyBinder mProxyBinder;
  
  static {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    PROVIDER_MAP = (Map)hashMap;
    hashMap.put("settings", new HookFetcher() {
          public ProviderHook fetch(boolean param1Boolean, IInterface param1IInterface) {
            return new SettingsProviderHook(param1IInterface);
          }
        });
    PROVIDER_MAP.put("downloads", new HookFetcher() {
          public ProviderHook fetch(boolean param1Boolean, IInterface param1IInterface) {
            return new DownloadProviderHook(param1IInterface);
          }
        });
    PROVIDER_MAP.put("com.android.badge", new HookFetcher() {
          public ProviderHook fetch(boolean param1Boolean, IInterface param1IInterface) {
            return new BadgeProviderHook(param1IInterface);
          }
        });
    PROVIDER_MAP.put("com.huawei.android.launcher.settings", new HookFetcher() {
          public ProviderHook fetch(boolean param1Boolean, IInterface param1IInterface) {
            return new BadgeProviderHook(param1IInterface);
          }
        });
  }
  
  public ProviderHook(IInterface paramIInterface) {
    this.mBase = paramIInterface;
    this.mProxy = (IInterface)Proxy.newProxyInstance(IContentProvider.TYPE.getClassLoader(), new Class[] { IContentProvider.TYPE }, this);
    this.mProxyBinder = new ProxyBinder(this.mBase.asBinder(), this.mProxy);
  }
  
  public static IInterface createProxy(boolean paramBoolean, String paramString, IInterface paramIInterface) {
    if (paramIInterface instanceof Proxy && Proxy.getInvocationHandler(paramIInterface) instanceof ProviderHook)
      return paramIInterface; 
    HookFetcher hookFetcher = fetchHook(paramString);
    IInterface iInterface = paramIInterface;
    if (hookFetcher != null) {
      IInterface iInterface1 = hookFetcher.fetch(paramBoolean, paramIInterface).getProxyInterface();
      iInterface = paramIInterface;
      if (iInterface1 != null)
        iInterface = iInterface1; 
    } 
    return iInterface;
  }
  
  private static HookFetcher fetchHook(String paramString) {
    HookFetcher hookFetcher2 = PROVIDER_MAP.get(paramString);
    HookFetcher hookFetcher1 = hookFetcher2;
    if (hookFetcher2 == null)
      hookFetcher1 = new HookFetcher() {
          public ProviderHook fetch(boolean param1Boolean, IInterface param1IInterface) {
            return (ProviderHook)(param1Boolean ? new ExternalProviderHook(param1IInterface) : new InternalProviderHook(param1IInterface));
          }
        }; 
    return hookFetcher1;
  }
  
  public Bundle call(MethodBox paramMethodBox, String paramString1, String paramString2, Bundle paramBundle) throws InvocationTargetException {
    Object[] arrayOfObject = paramMethodBox.args;
    int i = getCallIndex(paramMethodBox);
    arrayOfObject[i] = paramString1;
    arrayOfObject[i + 1] = paramString2;
    arrayOfObject[i + 2] = paramBundle;
    return (Bundle)paramMethodBox.call();
  }
  
  public int getCallIndex(MethodBox paramMethodBox) {
    return paramMethodBox.args.length - 3;
  }
  
  public IInterface getProxyInterface() {
    return this.mProxy;
  }
  
  public Uri insert(MethodBox paramMethodBox, Uri paramUri, ContentValues paramContentValues) throws InvocationTargetException {
    Object[] arrayOfObject = paramMethodBox.args;
    int i = MethodParameterUtils.getIndex(arrayOfObject, Uri.class);
    arrayOfObject[i] = paramUri;
    arrayOfObject[i + 1] = paramContentValues;
    return (Uri)paramMethodBox.call();
  }
  
  public Object invoke(Object paramObject, Method paramMethod, Object... paramVarArgs) throws Throwable {
    MethodBox methodBox;
    try {
      processArgs(paramMethod, paramVarArgs);
    } finally {
      paramObject = null;
    } 
    try {
      paramObject = paramMethod.getName();
      if ("call".equals(paramObject))
        return call(methodBox, (String)paramVarArgs[i], (String)paramVarArgs[i + 1], (Bundle)paramVarArgs[i + 2]); 
      return "asBinder".equals(paramObject) ? this.mProxyBinder : methodBox.call();
    } finally {
      VLog.w("ProviderHook", "call: %s (%s) with error", new Object[] { paramMethod.getName(), Arrays.toString(paramVarArgs) });
      if (paramObject instanceof InvocationTargetException)
        throw paramObject.getCause(); 
    } 
  }
  
  protected void processArgs(Method paramMethod, Object... paramVarArgs) {}
  
  public Cursor query(MethodBox paramMethodBox, Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2, Bundle paramBundle) throws InvocationTargetException {
    Object[] arrayOfObject = paramMethodBox.args;
    int i = MethodParameterUtils.getIndex(arrayOfObject, Uri.class);
    arrayOfObject[i] = paramUri;
    arrayOfObject[i + 1] = paramArrayOfString1;
    if (BuildCompat.isOreo()) {
      if (paramBundle != null) {
        paramBundle.putString("android:query-arg-sql-selection", paramString1);
        paramBundle.putStringArray("android:query-arg-sql-selection-args", paramArrayOfString2);
        paramBundle.putString("android:query-arg-sql-sort-order", paramString2);
      } 
    } else {
      arrayOfObject[i + 2] = paramString1;
      arrayOfObject[i + 3] = paramArrayOfString2;
      arrayOfObject[i + 4] = paramString2;
    } 
    return (Cursor)paramMethodBox.call();
  }
  
  public static interface HookFetcher {
    ProviderHook fetch(boolean param1Boolean, IInterface param1IInterface);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\providers\ProviderHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */