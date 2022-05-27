package com.lody.virtual.client.hook.proxies.appops;

import android.app.SyncNotedAppOp;
import com.lody.virtual.GmsSupport;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import com.lody.virtual.helper.compat.BuildCompat;
import java.lang.reflect.Method;
import mirror.android.content.AttributionSource;

public class MethodProxies {
  public static Object checkAudioOperation(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    replaceUidAndPackage(paramArrayOfObject, 3);
    return paramMethod.invoke(paramObject, paramArrayOfObject);
  }
  
  public static Object checkOperation(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    replaceUidAndPackage(paramArrayOfObject, 2);
    return paramMethod.invoke(paramObject, paramArrayOfObject);
  }
  
  public static Object checkOperationRaw(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    replaceUidAndPackage(paramArrayOfObject, 2);
    return paramMethod.invoke(paramObject, paramArrayOfObject);
  }
  
  public static Object checkPackage(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    if (GmsSupport.isGoogleAppOrService((String)paramArrayOfObject[1]))
      return Integer.valueOf(0); 
    replaceUidAndPackage(paramArrayOfObject, 1);
    return paramMethod.invoke(paramObject, paramArrayOfObject);
  }
  
  public static Object extractAsyncOps(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    paramArrayOfObject[0] = VirtualCore.get().getHostPkg();
    return paramMethod.invoke(paramObject, paramArrayOfObject);
  }
  
  public static Object finishOperation(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    replaceUidAndPackage(paramArrayOfObject, 3);
    return paramMethod.invoke(paramObject, paramArrayOfObject);
  }
  
  public static Object getOpsForPackage(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    replaceUidAndPackage(paramArrayOfObject, 1);
    return paramMethod.invoke(paramObject, paramArrayOfObject);
  }
  
  public static Object getPackagesForOps(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    return paramMethod.invoke(paramObject, paramArrayOfObject);
  }
  
  public static Object noteOperation(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    replaceUidAndPackage(paramArrayOfObject, 2);
    return paramMethod.invoke(paramObject, paramArrayOfObject);
  }
  
  public static Object noteProxyOperation(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    if (BuildCompat.isS()) {
      int i = MethodParameterUtils.getIndex(paramArrayOfObject, AttributionSource.TYPE);
      if (i >= 0)
        return new SyncNotedAppOp(0, AttributionSource.getAttributionTag(paramArrayOfObject[i])); 
    } 
    return Integer.valueOf(0);
  }
  
  private static void replaceUidAndPackage(Object[] paramArrayOfObject, int paramInt) {
    paramArrayOfObject[paramInt] = VirtualCore.get().getHostPkg();
    if (paramArrayOfObject[--paramInt] instanceof Integer)
      paramArrayOfObject[paramInt] = Integer.valueOf(VirtualCore.get().myUid()); 
  }
  
  public static Object resetAllModes(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    paramArrayOfObject[0] = Integer.valueOf(0);
    paramArrayOfObject[1] = VirtualCore.get().getHostPkg();
    return paramMethod.invoke(paramObject, paramArrayOfObject);
  }
  
  public static Object startOperation(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    replaceUidAndPackage(paramArrayOfObject, 3);
    return paramMethod.invoke(paramObject, paramArrayOfObject);
  }
  
  public static Object startWatchingAsyncNoted(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    paramArrayOfObject[0] = VirtualCore.get().getHostPkg();
    return paramMethod.invoke(paramObject, paramArrayOfObject);
  }
  
  public static Object stopWatchingAsyncNoted(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
    paramArrayOfObject[0] = VirtualCore.get().getHostPkg();
    return paramMethod.invoke(paramObject, paramArrayOfObject);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\appops\MethodProxies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */