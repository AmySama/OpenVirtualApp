package com.lody.virtual.client.hook.proxies.content;

import android.content.pm.ProviderInfo;
import android.database.IContentObserver;
import android.net.Uri;
import android.os.Build;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.ipc.VContentManager;
import com.lody.virtual.client.ipc.VPackageManager;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.os.VUserHandle;
import java.lang.reflect.Method;

public class MethodProxies {
  private static boolean isAppUri(Uri paramUri) {
    VPackageManager vPackageManager = VPackageManager.get();
    String str = paramUri.getAuthority();
    int i = VUserHandle.myUserId();
    boolean bool1 = false;
    ProviderInfo providerInfo = vPackageManager.resolveContentProvider(str, 0, i);
    boolean bool2 = bool1;
    if (providerInfo != null) {
      bool2 = bool1;
      if (providerInfo.enabled)
        bool2 = true; 
    } 
    return bool2;
  }
  
  public static Object notifyChange(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    if (Build.VERSION.SDK_INT >= 24 && paramArrayOfObject.length >= 6)
      paramArrayOfObject[5] = Integer.valueOf(22); 
    boolean bool1 = true;
    IContentObserver iContentObserver = (IContentObserver)paramArrayOfObject[1];
    boolean bool2 = ((Boolean)paramArrayOfObject[2]).booleanValue();
    if (paramArrayOfObject[3] instanceof Integer) {
      if ((((Integer)paramArrayOfObject[3]).intValue() & 0x1) == 0)
        bool1 = false; 
    } else {
      bool1 = ((Boolean)paramArrayOfObject[3]).booleanValue();
    } 
    if (BuildCompat.isR()) {
      MethodProxy.replaceLastUserId(paramArrayOfObject);
      for (Uri uri1 : (Uri[])paramArrayOfObject[0]) {
        if (isAppUri(uri1)) {
          VContentManager.get().notifyChange(uri1, iContentObserver, bool2, bool1, VUserHandle.myUserId());
        } else {
          paramMethod.invoke(paramObject, paramArrayOfObject);
        } 
      } 
      return Integer.valueOf(0);
    } 
    Uri uri = (Uri)paramArrayOfObject[0];
    if (isAppUri(uri)) {
      VContentManager.get().notifyChange(uri, iContentObserver, bool2, bool1, VUserHandle.myUserId());
      return Integer.valueOf(0);
    } 
    MethodProxy.replaceLastUserId(paramArrayOfObject);
    return paramMethod.invoke(paramObject, paramArrayOfObject);
  }
  
  public static Object registerContentObserver(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    if (Build.VERSION.SDK_INT >= 24 && paramArrayOfObject.length >= 5)
      paramArrayOfObject[4] = Integer.valueOf(22); 
    Uri uri = (Uri)paramArrayOfObject[0];
    boolean bool = ((Boolean)paramArrayOfObject[1]).booleanValue();
    IContentObserver iContentObserver = (IContentObserver)paramArrayOfObject[2];
    if (isAppUri(uri)) {
      VContentManager.get().registerContentObserver(uri, bool, iContentObserver, VUserHandle.myUserId());
      return Integer.valueOf(0);
    } 
    MethodProxy.replaceFirstUserId(paramArrayOfObject);
    return paramMethod.invoke(paramObject, paramArrayOfObject);
  }
  
  public static Object unregisterContentObserver(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    IContentObserver iContentObserver = (IContentObserver)paramArrayOfObject[0];
    VContentManager.get().unregisterContentObserver(iContentObserver);
    return paramMethod.invoke(paramObject, paramArrayOfObject);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\content\MethodProxies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */