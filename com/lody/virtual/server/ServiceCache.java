package com.lody.virtual.server;

import android.os.IBinder;
import com.lody.virtual.helper.collection.ArrayMap;
import java.util.Map;

public class ServiceCache {
  private static final Map<String, IBinder> sCache = (Map<String, IBinder>)new ArrayMap(5);
  
  public static void addService(String paramString, IBinder paramIBinder) {
    sCache.put(paramString, paramIBinder);
  }
  
  public static IBinder getService(String paramString) {
    return sCache.get(paramString);
  }
  
  public static IBinder removeService(String paramString) {
    return sCache.remove(paramString);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\ServiceCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */