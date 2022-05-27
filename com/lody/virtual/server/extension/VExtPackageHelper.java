package com.lody.virtual.server.extension;

import android.app.ActivityManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import com.lody.virtual.IExtHelperInterface;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.client.ipc.FileTransfer;
import com.lody.virtual.helper.DexOptimizer;
import com.lody.virtual.helper.compat.BundleCompat;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.remote.InstalledAppInfo;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class VExtPackageHelper extends ContentProvider {
  private static final String TAG = VExtPackageHelper.class.getSimpleName();
  
  private final Binder mExtHelperInterface = (Binder)new IExtHelperInterface.Stub() {
      public void cleanPackageData(int[] param1ArrayOfint, String param1String) {
        // Byte code:
        //   0: aload_0
        //   1: monitorenter
        //   2: aload_2
        //   3: ifnull -> 58
        //   6: aload_1
        //   7: ifnonnull -> 13
        //   10: goto -> 58
        //   13: aload_1
        //   14: arraylength
        //   15: istore_3
        //   16: iconst_0
        //   17: istore #4
        //   19: iload #4
        //   21: iload_3
        //   22: if_icmpge -> 55
        //   25: aload_1
        //   26: iload #4
        //   28: iaload
        //   29: istore #5
        //   31: iload #5
        //   33: aload_2
        //   34: invokestatic getDataUserPackageDirectoryExt : (ILjava/lang/String;)Ljava/io/File;
        //   37: invokestatic deleteDir : (Ljava/io/File;)V
        //   40: iload #5
        //   42: aload_2
        //   43: invokestatic getDeDataUserPackageDirectoryExt : (ILjava/lang/String;)Ljava/io/File;
        //   46: invokestatic deleteDir : (Ljava/io/File;)V
        //   49: iinc #4, 1
        //   52: goto -> 19
        //   55: aload_0
        //   56: monitorexit
        //   57: return
        //   58: aload_0
        //   59: monitorexit
        //   60: return
        //   61: astore_1
        //   62: aload_0
        //   63: monitorexit
        //   64: aload_1
        //   65: athrow
        // Exception table:
        //   from	to	target	type
        //   13	16	61	finally
        //   31	49	61	finally
        //   55	57	61	finally
        //   58	60	61	finally
        //   62	64	61	finally
      }
      
      public void copyPackage(InstalledAppInfo param1InstalledAppInfo) {
        String str1 = param1InstalledAppInfo.packageName;
        String str2 = VExtPackageHelper.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("copyPackage: ");
        stringBuilder.append(str1);
        VLog.e(str2, stringBuilder.toString());
        FileUtils.ensureDirCreate(new File[] { VEnvironment.getDataAppPackageDirectoryExt(str1), VEnvironment.getDataAppLibDirectoryExt(str1) });
        FileTransfer fileTransfer = FileTransfer.get();
        fileTransfer.copyFile(VEnvironment.getPackageFile(str1), VEnvironment.getPackageFileExt(str1));
        for (String str : param1InstalledAppInfo.getSplitNames())
          fileTransfer.copyFile(VEnvironment.getSplitPackageFile(str1, str), VEnvironment.getSplitPackageFileExt(str1, str)); 
        fileTransfer.copyDirectory(VEnvironment.getDataAppLibDirectory(str1), VEnvironment.getDataAppLibDirectoryExt(str1));
        if (VirtualCore.get().isRunInExtProcess(str1)) {
          String str = VirtualRuntime.getCurrentInstructionSet();
          try {
            DexOptimizer.dex2oat(VEnvironment.getPackageFileExt(str1).getPath(), VEnvironment.getOatFileExt(str1, str).getPath());
          } catch (IOException iOException) {
            iOException.printStackTrace();
          } 
        } 
      }
      
      public void forceStop(int[] param1ArrayOfint) {
        // Byte code:
        //   0: aload_0
        //   1: monitorenter
        //   2: aload_1
        //   3: arraylength
        //   4: istore_2
        //   5: iconst_0
        //   6: istore_3
        //   7: iload_3
        //   8: iload_2
        //   9: if_icmpge -> 24
        //   12: aload_1
        //   13: iload_3
        //   14: iaload
        //   15: invokestatic killProcess : (I)V
        //   18: iinc #3, 1
        //   21: goto -> 7
        //   24: aload_0
        //   25: monitorexit
        //   26: return
        //   27: astore_1
        //   28: aload_0
        //   29: monitorexit
        //   30: aload_1
        //   31: athrow
        // Exception table:
        //   from	to	target	type
        //   2	5	27	finally
        //   12	18	27	finally
        //   24	26	27	finally
        //   28	30	27	finally
      }
      
      public List<ActivityManager.RecentTaskInfo> getRecentTasks(int param1Int1, int param1Int2) {
        Context context = VExtPackageHelper.this.getContext();
        if (context == null)
          return Collections.emptyList(); 
        ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
        return (activityManager == null) ? Collections.emptyList() : activityManager.getRecentTasks(param1Int1, param1Int2);
      }
      
      public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() {
        Context context = VExtPackageHelper.this.getContext();
        if (context == null)
          return Collections.emptyList(); 
        ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
        return (activityManager == null) ? Collections.emptyList() : activityManager.getRunningAppProcesses();
      }
      
      public List<ActivityManager.RunningTaskInfo> getRunningTasks(int param1Int) {
        Context context = VExtPackageHelper.this.getContext();
        if (context == null)
          return Collections.emptyList(); 
        ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
        return (activityManager == null) ? Collections.emptyList() : activityManager.getRunningTasks(param1Int);
      }
      
      public boolean isExternalStorageManager() throws RemoteException {
        return (Build.VERSION.SDK_INT >= 30) ? Environment.isExternalStorageManager() : true;
      }
      
      public int syncPackages() {
        // Byte code:
        //   0: aload_0
        //   1: monitorenter
        //   2: invokestatic getDataUserDirectoryExt : ()Ljava/io/File;
        //   5: invokestatic cleanUsers : (Ljava/io/File;)V
        //   8: invokestatic cleanUninstalledPackages : ()V
        //   11: invokestatic get : ()Lcom/lody/virtual/client/core/VirtualCore;
        //   14: iconst_0
        //   15: invokevirtual getInstalledApps : (I)Ljava/util/List;
        //   18: invokeinterface iterator : ()Ljava/util/Iterator;
        //   23: astore_1
        //   24: aload_1
        //   25: invokeinterface hasNext : ()Z
        //   30: ifeq -> 155
        //   33: aload_1
        //   34: invokeinterface next : ()Ljava/lang/Object;
        //   39: checkcast com/lody/virtual/remote/InstalledAppInfo
        //   42: astore_2
        //   43: aload_2
        //   44: invokevirtual getSplitNames : ()Ljava/util/List;
        //   47: astore_3
        //   48: aload_3
        //   49: invokeinterface isEmpty : ()Z
        //   54: ifne -> 112
        //   57: aload_3
        //   58: invokeinterface iterator : ()Ljava/util/Iterator;
        //   63: astore_3
        //   64: iconst_0
        //   65: istore #4
        //   67: iload #4
        //   69: istore #5
        //   71: aload_3
        //   72: invokeinterface hasNext : ()Z
        //   77: ifeq -> 115
        //   80: aload_3
        //   81: invokeinterface next : ()Ljava/lang/Object;
        //   86: checkcast java/lang/String
        //   89: astore #6
        //   91: aload_2
        //   92: getfield packageName : Ljava/lang/String;
        //   95: aload #6
        //   97: invokestatic getSplitPackageFileExt : (Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;
        //   100: invokevirtual exists : ()Z
        //   103: ifne -> 67
        //   106: iconst_1
        //   107: istore #4
        //   109: goto -> 67
        //   112: iconst_0
        //   113: istore #5
        //   115: aload_2
        //   116: getfield packageName : Ljava/lang/String;
        //   119: invokestatic getDataAppPackageDirectoryExt : (Ljava/lang/String;)Ljava/io/File;
        //   122: astore_3
        //   123: aload_3
        //   124: invokevirtual exists : ()Z
        //   127: ifeq -> 135
        //   130: iload #5
        //   132: ifeq -> 24
        //   135: aload_3
        //   136: invokestatic ensureDirCreate : (Ljava/io/File;)Z
        //   139: pop
        //   140: aload_2
        //   141: getfield dynamic : Z
        //   144: ifne -> 24
        //   147: aload_0
        //   148: aload_2
        //   149: invokevirtual copyPackage : (Lcom/lody/virtual/remote/InstalledAppInfo;)V
        //   152: goto -> 24
        //   155: aload_0
        //   156: monitorexit
        //   157: iconst_0
        //   158: ireturn
        //   159: astore_2
        //   160: aload_0
        //   161: monitorexit
        //   162: aload_2
        //   163: athrow
        // Exception table:
        //   from	to	target	type
        //   2	24	159	finally
        //   24	64	159	finally
        //   71	106	159	finally
        //   115	130	159	finally
        //   135	152	159	finally
        //   155	157	159	finally
        //   160	162	159	finally
      }
    };
  
  public Bundle call(String paramString1, String paramString2, Bundle paramBundle) {
    if (paramString1.equals("connect")) {
      Bundle bundle = new Bundle();
      BundleCompat.putBinder(bundle, "_VA_|_binder_", (IBinder)this.mExtHelperInterface);
      return bundle;
    } 
    return null;
  }
  
  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString) {
    return 0;
  }
  
  public String getType(Uri paramUri) {
    return null;
  }
  
  public Uri insert(Uri paramUri, ContentValues paramContentValues) {
    return null;
  }
  
  public boolean onCreate() {
    return true;
  }
  
  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2) {
    return null;
  }
  
  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString) {
    return 0;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\extension\VExtPackageHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */