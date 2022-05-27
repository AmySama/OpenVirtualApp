package com.lody.virtual.client.natives;

import android.hardware.Camera;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Parcelable;
import com.lody.virtual.client.NativeEngine;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import com.lody.virtual.helper.compat.BuildCompat;
import dalvik.system.DexFile;
import java.lang.reflect.Method;

public class NativeMethods {
  public static int gAudioRecordMethodType;
  
  public static Method gAudioRecordNativeCheckPermission;
  
  public static Method gAudioRecordNativeSetup;
  
  public static int gCameraMethodType;
  
  public static Method gCameraNativeSetup;
  
  public static Method gMediaRecorderNativeSetup;
  
  public static Method gNativeLoad;
  
  public static Method gNativeMask;
  
  public static Method gOpenDexFileNative;
  
  static {
    try {
      init();
    } finally {
      Exception exception = null;
    } 
  }
  
  private static Method getAudioRecordNativeSetup() {
    try {
      Method method = AudioRecord.class.getDeclaredMethod("native_setup", new Class[] { Object.class, Object.class, int[].class, int.class, int.class, int.class, int.class, int[].class, String.class, long.class });
    } catch (NoSuchMethodException noSuchMethodException1) {
      noSuchMethodException1 = null;
    } 
    noSuchMethodException2 = noSuchMethodException1;
    if (noSuchMethodException1 == null)
      try {
        Method method = AudioRecord.class.getDeclaredMethod("native_setup", new Class[] { Object.class, Object.class, int.class, int.class, int.class, int.class, int.class, int[].class, String.class });
      } catch (NoSuchMethodException noSuchMethodException2) {
        noSuchMethodException2 = noSuchMethodException1;
      }  
    return (Method)noSuchMethodException2;
  }
  
  private static Method getCameraNativeSetup() {
    Method[] arrayOfMethod = Camera.class.getDeclaredMethods();
    if (arrayOfMethod != null) {
      int i = arrayOfMethod.length;
      for (byte b = 0; b < i; b++) {
        Method method = arrayOfMethod[b];
        if ("native_setup".equals(method.getName()))
          return method; 
      } 
    } 
    return null;
  }
  
  private static Method getMediaRecorderNativeSetup() {
    try {
      if (BuildCompat.isS()) {
        Method method = MediaRecorder.class.getDeclaredMethod("native_setup", new Class[] { Object.class, String.class, Parcelable.class });
      } else {
        Method method = MediaRecorder.class.getDeclaredMethod("native_setup", new Class[] { Object.class, String.class, String.class });
      } 
    } catch (NoSuchMethodException noSuchMethodException1) {
      noSuchMethodException1 = null;
    } 
    noSuchMethodException2 = noSuchMethodException1;
    if (noSuchMethodException1 == null)
      try {
        Method method = MediaRecorder.class.getDeclaredMethod("native_setup", new Class[] { Object.class, String.class });
      } catch (NoSuchMethodException noSuchMethodException2) {
        noSuchMethodException2 = noSuchMethodException1;
      }  
    return (Method)noSuchMethodException2;
  }
  
  private static void init() {
    String str;
    try {
      gNativeMask = NativeEngine.class.getDeclaredMethod("nativeMark", new Class[0]);
    } catch (NoSuchMethodException noSuchMethodException) {
      noSuchMethodException.printStackTrace();
    } 
    if (BuildCompat.isR())
      try {
        gNativeLoad = Runtime.class.getDeclaredMethod("nativeLoad", new Class[] { String.class, ClassLoader.class, Class.class });
      } catch (NoSuchMethodException noSuchMethodException) {
        noSuchMethodException.printStackTrace();
      }  
    gMediaRecorderNativeSetup = getMediaRecorderNativeSetup();
    Method method1 = getAudioRecordNativeSetup();
    gAudioRecordNativeSetup = method1;
    if (method1 != null && (method1.getParameterTypes()).length == 10) {
      gAudioRecordMethodType = 2;
    } else {
      gAudioRecordMethodType = 1;
    } 
    if (Build.VERSION.SDK_INT >= 19) {
      str = "openDexFileNative";
    } else {
      str = "openDexFile";
    } 
    for (Method method : DexFile.class.getDeclaredMethods()) {
      if (method.getName().equals(str)) {
        gOpenDexFileNative = method;
        break;
      } 
    } 
    method2 = gOpenDexFileNative;
    if (method2 != null) {
      method2.setAccessible(true);
      gCameraMethodType = -1;
      Method method = getCameraNativeSetup();
      if (method != null) {
        int i = MethodParameterUtils.getParamsIndex(method.getParameterTypes(), String.class);
        gCameraNativeSetup = method;
        gCameraMethodType = i + 16;
      } 
      for (Method method2 : AudioRecord.class.getDeclaredMethods()) {
        if (method2.getName().equals("native_check_permission") && (method2.getParameterTypes()).length == 1 && method2.getParameterTypes()[0] == String.class) {
          gAudioRecordNativeCheckPermission = method2;
          method2.setAccessible(true);
          break;
        } 
      } 
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Unable to find method : ");
    stringBuilder.append(str);
    throw new RuntimeException(stringBuilder.toString());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\natives\NativeMethods.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */