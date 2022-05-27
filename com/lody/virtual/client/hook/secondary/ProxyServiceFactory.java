package com.lody.virtual.client.hook.secondary;

import android.content.ComponentName;
import android.content.Context;
import android.os.IBinder;
import java.util.HashMap;
import java.util.Map;

public class ProxyServiceFactory {
  private static final String TAG = ProxyServiceFactory.class.getSimpleName();
  
  private static Map<String, ServiceFetcher> sHookSecondaryServiceMap = new HashMap<String, ServiceFetcher>();
  
  public static IBinder getProxyService(Context paramContext, ComponentName paramComponentName, IBinder paramIBinder) {
    if (paramContext != null && paramIBinder != null)
      try {
        String str = paramIBinder.getInterfaceDescriptor();
        ServiceFetcher serviceFetcher = sHookSecondaryServiceMap.get(str);
      } finally {
        paramContext = null;
      }  
    return null;
  }
  
  private static interface ServiceFetcher {
    IBinder getService(Context param1Context, ClassLoader param1ClassLoader, IBinder param1IBinder);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\secondary\ProxyServiceFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */