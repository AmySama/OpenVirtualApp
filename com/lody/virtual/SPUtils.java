package com.lody.virtual;

import android.content.Context;
import android.content.SharedPreferences;
import java.lang.reflect.Method;
import java.util.Map;

public class SPUtils {
  public static final String FILE_NAME = "share_data";
  
  public static void clear(Context paramContext) {
    SharedPreferences.Editor editor = paramContext.getSharedPreferences("share_data", 0).edit();
    editor.clear();
    SharedPreferencesCompat.apply(editor);
  }
  
  public static boolean contains(Context paramContext, String paramString) {
    return paramContext.getSharedPreferences("share_data", 0).contains(paramString);
  }
  
  public static Object get(Context paramContext, String paramString, Object paramObject) {
    SharedPreferences sharedPreferences = paramContext.getSharedPreferences("share_data", 0);
    return (paramObject instanceof String) ? sharedPreferences.getString(paramString, (String)paramObject) : ((paramObject instanceof Integer) ? Integer.valueOf(sharedPreferences.getInt(paramString, ((Integer)paramObject).intValue())) : ((paramObject instanceof Boolean) ? Boolean.valueOf(sharedPreferences.getBoolean(paramString, ((Boolean)paramObject).booleanValue())) : ((paramObject instanceof Float) ? Float.valueOf(sharedPreferences.getFloat(paramString, ((Float)paramObject).floatValue())) : ((paramObject instanceof Long) ? Long.valueOf(sharedPreferences.getLong(paramString, ((Long)paramObject).longValue())) : null))));
  }
  
  public static String get(Context paramContext, String paramString) {
    return paramContext.getSharedPreferences("share_data", 0).getString(paramString, "");
  }
  
  public static Map<String, ?> getAll(Context paramContext) {
    return paramContext.getSharedPreferences("share_data", 0).getAll();
  }
  
  public static void put(Context paramContext, String paramString, Object paramObject) {
    SharedPreferences.Editor editor = paramContext.getSharedPreferences("share_data", 0).edit();
    if (paramObject instanceof String) {
      editor.putString(paramString, (String)paramObject);
    } else if (paramObject instanceof Integer) {
      editor.putInt(paramString, ((Integer)paramObject).intValue());
    } else if (paramObject instanceof Boolean) {
      editor.putBoolean(paramString, ((Boolean)paramObject).booleanValue());
    } else if (paramObject instanceof Float) {
      editor.putFloat(paramString, ((Float)paramObject).floatValue());
    } else if (paramObject instanceof Long) {
      editor.putLong(paramString, ((Long)paramObject).longValue());
    } else {
      editor.putString(paramString, paramObject.toString());
    } 
    SharedPreferencesCompat.apply(editor);
  }
  
  public static void remove(Context paramContext, String paramString) {
    SharedPreferences.Editor editor = paramContext.getSharedPreferences("share_data", 0).edit();
    editor.remove(paramString);
    SharedPreferencesCompat.apply(editor);
  }
  
  private static class SharedPreferencesCompat {
    private static final Method sApplyMethod = findApplyMethod();
    
    public static void apply(SharedPreferences.Editor param1Editor) {
      try {
        if (sApplyMethod != null) {
          sApplyMethod.invoke(param1Editor, new Object[0]);
          return;
        } 
      } catch (IllegalArgumentException|IllegalAccessException|java.lang.reflect.InvocationTargetException illegalArgumentException) {}
      param1Editor.commit();
    }
    
    private static Method findApplyMethod() {
      try {
        return SharedPreferences.Editor.class.getMethod("apply", new Class[0]);
      } catch (NoSuchMethodException noSuchMethodException) {
        return null;
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\SPUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */