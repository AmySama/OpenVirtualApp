package android.support.v4.content;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.util.TypedValue;
import java.io.File;

public class ContextCompat {
  private static final String TAG = "ContextCompat";
  
  private static final Object sLock = new Object();
  
  private static TypedValue sTempValue;
  
  private static File buildPath(File paramFile, String... paramVarArgs) {
    int i = paramVarArgs.length;
    byte b = 0;
    File file;
    for (file = paramFile; b < i; file = paramFile) {
      String str = paramVarArgs[b];
      if (file == null) {
        paramFile = new File(str);
      } else {
        paramFile = file;
        if (str != null)
          paramFile = new File(file, str); 
      } 
      b++;
    } 
    return file;
  }
  
  public static int checkSelfPermission(Context paramContext, String paramString) {
    if (paramString != null)
      return paramContext.checkPermission(paramString, Process.myPid(), Process.myUid()); 
    throw new IllegalArgumentException("permission is null");
  }
  
  public static Context createDeviceProtectedStorageContext(Context paramContext) {
    return (Build.VERSION.SDK_INT >= 24) ? paramContext.createDeviceProtectedStorageContext() : null;
  }
  
  private static File createFilesDir(File paramFile) {
    // Byte code:
    //   0: ldc android/support/v4/content/ContextCompat
    //   2: monitorenter
    //   3: aload_0
    //   4: invokevirtual exists : ()Z
    //   7: ifne -> 70
    //   10: aload_0
    //   11: invokevirtual mkdirs : ()Z
    //   14: ifne -> 70
    //   17: aload_0
    //   18: invokevirtual exists : ()Z
    //   21: istore_1
    //   22: iload_1
    //   23: ifeq -> 31
    //   26: ldc android/support/v4/content/ContextCompat
    //   28: monitorexit
    //   29: aload_0
    //   30: areturn
    //   31: new java/lang/StringBuilder
    //   34: astore_2
    //   35: aload_2
    //   36: invokespecial <init> : ()V
    //   39: aload_2
    //   40: ldc 'Unable to create files subdir '
    //   42: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: aload_2
    //   47: aload_0
    //   48: invokevirtual getPath : ()Ljava/lang/String;
    //   51: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   54: pop
    //   55: ldc 'ContextCompat'
    //   57: aload_2
    //   58: invokevirtual toString : ()Ljava/lang/String;
    //   61: invokestatic w : (Ljava/lang/String;Ljava/lang/String;)I
    //   64: pop
    //   65: ldc android/support/v4/content/ContextCompat
    //   67: monitorexit
    //   68: aconst_null
    //   69: areturn
    //   70: ldc android/support/v4/content/ContextCompat
    //   72: monitorexit
    //   73: aload_0
    //   74: areturn
    //   75: astore_0
    //   76: ldc android/support/v4/content/ContextCompat
    //   78: monitorexit
    //   79: aload_0
    //   80: athrow
    // Exception table:
    //   from	to	target	type
    //   3	22	75	finally
    //   31	65	75	finally
  }
  
  public static File getCodeCacheDir(Context paramContext) {
    return (Build.VERSION.SDK_INT >= 21) ? paramContext.getCodeCacheDir() : createFilesDir(new File((paramContext.getApplicationInfo()).dataDir, "code_cache"));
  }
  
  public static int getColor(Context paramContext, int paramInt) {
    return (Build.VERSION.SDK_INT >= 23) ? paramContext.getColor(paramInt) : paramContext.getResources().getColor(paramInt);
  }
  
  public static ColorStateList getColorStateList(Context paramContext, int paramInt) {
    return (Build.VERSION.SDK_INT >= 23) ? paramContext.getColorStateList(paramInt) : paramContext.getResources().getColorStateList(paramInt);
  }
  
  public static File getDataDir(Context paramContext) {
    if (Build.VERSION.SDK_INT >= 24)
      return paramContext.getDataDir(); 
    String str = (paramContext.getApplicationInfo()).dataDir;
    if (str != null) {
      File file = new File(str);
    } else {
      str = null;
    } 
    return (File)str;
  }
  
  public static Drawable getDrawable(Context paramContext, int paramInt) {
    if (Build.VERSION.SDK_INT >= 21)
      return paramContext.getDrawable(paramInt); 
    if (Build.VERSION.SDK_INT >= 16)
      return paramContext.getResources().getDrawable(paramInt); 
    synchronized (sLock) {
      if (sTempValue == null) {
        TypedValue typedValue = new TypedValue();
        this();
        sTempValue = typedValue;
      } 
      paramContext.getResources().getValue(paramInt, sTempValue, true);
      paramInt = sTempValue.resourceId;
      return paramContext.getResources().getDrawable(paramInt);
    } 
  }
  
  public static File[] getExternalCacheDirs(Context paramContext) {
    return (Build.VERSION.SDK_INT >= 19) ? paramContext.getExternalCacheDirs() : new File[] { paramContext.getExternalCacheDir() };
  }
  
  public static File[] getExternalFilesDirs(Context paramContext, String paramString) {
    return (Build.VERSION.SDK_INT >= 19) ? paramContext.getExternalFilesDirs(paramString) : new File[] { paramContext.getExternalFilesDir(paramString) };
  }
  
  public static File getNoBackupFilesDir(Context paramContext) {
    return (Build.VERSION.SDK_INT >= 21) ? paramContext.getNoBackupFilesDir() : createFilesDir(new File((paramContext.getApplicationInfo()).dataDir, "no_backup"));
  }
  
  public static File[] getObbDirs(Context paramContext) {
    return (Build.VERSION.SDK_INT >= 19) ? paramContext.getObbDirs() : new File[] { paramContext.getObbDir() };
  }
  
  public static boolean isDeviceProtectedStorage(Context paramContext) {
    return (Build.VERSION.SDK_INT >= 24) ? paramContext.isDeviceProtectedStorage() : false;
  }
  
  public static boolean startActivities(Context paramContext, Intent[] paramArrayOfIntent) {
    return startActivities(paramContext, paramArrayOfIntent, null);
  }
  
  public static boolean startActivities(Context paramContext, Intent[] paramArrayOfIntent, Bundle paramBundle) {
    if (Build.VERSION.SDK_INT >= 16) {
      paramContext.startActivities(paramArrayOfIntent, paramBundle);
    } else {
      paramContext.startActivities(paramArrayOfIntent);
    } 
    return true;
  }
  
  public static void startActivity(Context paramContext, Intent paramIntent, Bundle paramBundle) {
    if (Build.VERSION.SDK_INT >= 16) {
      paramContext.startActivity(paramIntent, paramBundle);
    } else {
      paramContext.startActivity(paramIntent);
    } 
  }
  
  public static void startForegroundService(Context paramContext, Intent paramIntent) {
    if (Build.VERSION.SDK_INT >= 26) {
      paramContext.startForegroundService(paramIntent);
    } else {
      paramContext.startService(paramIntent);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\content\ContextCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */