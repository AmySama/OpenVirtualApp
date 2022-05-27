package com.lody.virtual.helper.compat;

import android.content.Context;
import android.os.storage.StorageManager;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class StorageManagerCompat {
  public static String[] getAllPoints(Context paramContext) {
    StorageManager storageManager = (StorageManager)paramContext.getSystemService("storage");
    try {
      String[] arrayOfString = (String[])storageManager.getClass().getMethod("getVolumePaths", new Class[0]).invoke(storageManager, new Object[0]);
    } catch (Exception exception) {
      exception.printStackTrace();
      exception = null;
    } 
    return (String[])exception;
  }
  
  public static ArrayList<String> getMountedPoints(Context paramContext) {
    StorageManager storageManager = (StorageManager)paramContext.getSystemService("storage");
    ArrayList<String> arrayList = new ArrayList();
    try {
      String[] arrayOfString = (String[])storageManager.getClass().getMethod("getVolumePaths", new Class[0]).invoke(storageManager, new Object[0]);
      if (arrayOfString != null && arrayOfString.length > 0) {
        Method method = storageManager.getClass().getMethod("getVolumeState", new Class[] { String.class });
        int i = arrayOfString.length;
        for (byte b = 0; b < i; b++) {
          String str = arrayOfString[b];
          if ("mounted".equals(method.invoke(storageManager, new Object[] { str })))
            arrayList.add(str); 
        } 
        return arrayList;
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return null;
  }
  
  public static boolean isMounted(Context paramContext, String paramString) {
    if (paramString == null)
      return false; 
    StorageManager storageManager = (StorageManager)paramContext.getSystemService("storage");
    try {
      return "mounted".equals(storageManager.getClass().getMethod("getVolumeState", new Class[] { String.class }).invoke(storageManager, new Object[] { paramString }));
    } catch (Exception exception) {
      exception.printStackTrace();
      return false;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\StorageManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */