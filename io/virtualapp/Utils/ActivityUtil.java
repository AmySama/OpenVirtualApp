package io.virtualapp.Utils;

import android.app.Activity;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ActivityUtil {
  public static Activity getCurrentActivity() {
    try {
      Class<?> clazz = Class.forName("android.app.ActivityThread");
      object = clazz.getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
      Field field = clazz.getDeclaredField("mActivities");
      field.setAccessible(true);
      for (Object object : ((Map)field.get(object)).values()) {
        Class<?> clazz1 = object.getClass();
        field = clazz1.getDeclaredField("paused");
        field.setAccessible(true);
        if (!field.getBoolean(object)) {
          field = clazz1.getDeclaredField("activity");
          field.setAccessible(true);
          return (Activity)field.get(object);
        } 
      } 
    } catch (ClassNotFoundException classNotFoundException) {
      classNotFoundException.printStackTrace();
    } catch (InvocationTargetException invocationTargetException) {
      invocationTargetException.printStackTrace();
    } catch (NoSuchMethodException noSuchMethodException) {
      noSuchMethodException.printStackTrace();
    } catch (NoSuchFieldException noSuchFieldException) {
      noSuchFieldException.printStackTrace();
    } catch (IllegalAccessException illegalAccessException) {
      illegalAccessException.printStackTrace();
    } 
    return null;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\ActivityUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */