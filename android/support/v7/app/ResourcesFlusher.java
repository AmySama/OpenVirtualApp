package android.support.v7.app;

import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.Map;

class ResourcesFlusher {
  private static final String TAG = "ResourcesFlusher";
  
  private static Field sDrawableCacheField;
  
  private static boolean sDrawableCacheFieldFetched;
  
  private static Field sResourcesImplField;
  
  private static boolean sResourcesImplFieldFetched;
  
  private static Class sThemedResourceCacheClazz;
  
  private static boolean sThemedResourceCacheClazzFetched;
  
  private static Field sThemedResourceCache_mUnthemedEntriesField;
  
  private static boolean sThemedResourceCache_mUnthemedEntriesFieldFetched;
  
  static boolean flush(Resources paramResources) {
    return (Build.VERSION.SDK_INT >= 24) ? flushNougats(paramResources) : ((Build.VERSION.SDK_INT >= 23) ? flushMarshmallows(paramResources) : ((Build.VERSION.SDK_INT >= 21) ? flushLollipops(paramResources) : false));
  }
  
  private static boolean flushLollipops(Resources paramResources) {
    if (!sDrawableCacheFieldFetched) {
      try {
        Field field1 = Resources.class.getDeclaredField("mDrawableCache");
        sDrawableCacheField = field1;
        field1.setAccessible(true);
      } catch (NoSuchFieldException noSuchFieldException) {
        Log.e("ResourcesFlusher", "Could not retrieve Resources#mDrawableCache field", noSuchFieldException);
      } 
      sDrawableCacheFieldFetched = true;
    } 
    Field field = sDrawableCacheField;
    if (field != null) {
      IllegalAccessException illegalAccessException2 = null;
      try {
        Map map = (Map)field.get(paramResources);
      } catch (IllegalAccessException illegalAccessException1) {
        Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mDrawableCache", illegalAccessException1);
        illegalAccessException1 = illegalAccessException2;
      } 
      if (illegalAccessException1 != null) {
        illegalAccessException1.clear();
        return true;
      } 
    } 
    return false;
  }
  
  private static boolean flushMarshmallows(Resources paramResources) {
    boolean bool = sDrawableCacheFieldFetched;
    boolean bool1 = true;
    if (!bool) {
      try {
        Field field1 = Resources.class.getDeclaredField("mDrawableCache");
        sDrawableCacheField = field1;
        field1.setAccessible(true);
      } catch (NoSuchFieldException noSuchFieldException) {
        Log.e("ResourcesFlusher", "Could not retrieve Resources#mDrawableCache field", noSuchFieldException);
      } 
      sDrawableCacheFieldFetched = true;
    } 
    Object object1 = null;
    Field field = sDrawableCacheField;
    Object object = object1;
    if (field != null)
      try {
        object = field.get(paramResources);
      } catch (IllegalAccessException illegalAccessException) {
        Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mDrawableCache", illegalAccessException);
        object = object1;
      }  
    if (object == null)
      return false; 
    if (object == null || !flushThemedResourcesCache(object))
      bool1 = false; 
    return bool1;
  }
  
  private static boolean flushNougats(Resources paramResources) {
    boolean bool = sResourcesImplFieldFetched;
    boolean bool1 = true;
    if (!bool) {
      try {
        Field field = Resources.class.getDeclaredField("mResourcesImpl");
        sResourcesImplField = field;
        field.setAccessible(true);
      } catch (NoSuchFieldException noSuchFieldException) {
        Log.e("ResourcesFlusher", "Could not retrieve Resources#mResourcesImpl field", noSuchFieldException);
      } 
      sResourcesImplFieldFetched = true;
    } 
    Field field1 = sResourcesImplField;
    if (field1 == null)
      return false; 
    Field field2 = null;
    try {
      object = field1.get(paramResources);
    } catch (IllegalAccessException object) {
      Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mResourcesImpl", (Throwable)object);
      object = null;
    } 
    if (object == null)
      return false; 
    if (!sDrawableCacheFieldFetched) {
      try {
        field1 = object.getClass().getDeclaredField("mDrawableCache");
        sDrawableCacheField = field1;
        field1.setAccessible(true);
      } catch (NoSuchFieldException noSuchFieldException) {
        Log.e("ResourcesFlusher", "Could not retrieve ResourcesImpl#mDrawableCache field", noSuchFieldException);
      } 
      sDrawableCacheFieldFetched = true;
    } 
    Field field3 = sDrawableCacheField;
    field1 = field2;
    if (field3 != null)
      try {
        Object object1 = field3.get(object);
      } catch (IllegalAccessException illegalAccessException) {
        Log.e("ResourcesFlusher", "Could not retrieve value from ResourcesImpl#mDrawableCache", illegalAccessException);
        field1 = field2;
      }  
    if (field1 == null || !flushThemedResourcesCache(field1))
      bool1 = false; 
    return bool1;
  }
  
  private static boolean flushThemedResourcesCache(Object paramObject) {
    Class clazz1;
    if (!sThemedResourceCacheClazzFetched) {
      try {
        sThemedResourceCacheClazz = Class.forName("android.content.res.ThemedResourceCache");
      } catch (ClassNotFoundException classNotFoundException) {
        Log.e("ResourcesFlusher", "Could not find ThemedResourceCache class", classNotFoundException);
      } 
      sThemedResourceCacheClazzFetched = true;
    } 
    Class clazz2 = sThemedResourceCacheClazz;
    if (clazz2 == null)
      return false; 
    if (!sThemedResourceCache_mUnthemedEntriesFieldFetched) {
      try {
        Field field1 = clazz2.getDeclaredField("mUnthemedEntries");
        sThemedResourceCache_mUnthemedEntriesField = field1;
        field1.setAccessible(true);
      } catch (NoSuchFieldException noSuchFieldException) {
        Log.e("ResourcesFlusher", "Could not retrieve ThemedResourceCache#mUnthemedEntries field", noSuchFieldException);
      } 
      sThemedResourceCache_mUnthemedEntriesFieldFetched = true;
    } 
    Field field = sThemedResourceCache_mUnthemedEntriesField;
    if (field == null)
      return false; 
    clazz2 = null;
    try {
      paramObject = field.get(paramObject);
    } catch (IllegalAccessException illegalAccessException) {
      Log.e("ResourcesFlusher", "Could not retrieve value from ThemedResourceCache#mUnthemedEntries", illegalAccessException);
      clazz1 = clazz2;
    } 
    if (clazz1 != null) {
      clazz1.clear();
      return true;
    } 
    return false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\app\ResourcesFlusher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */