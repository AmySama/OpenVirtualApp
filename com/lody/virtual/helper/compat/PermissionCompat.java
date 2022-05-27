package com.lody.virtual.helper.compat;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.stub.RequestPermissionsActivity;
import com.lody.virtual.server.IRequestPermissionsResult;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PermissionCompat {
  public static Set<String> DANGEROUS_PERMISSION = new HashSet<String>() {
    
    };
  
  public static boolean checkPermissions(String[] paramArrayOfString, boolean paramBoolean) {
    if (paramArrayOfString == null)
      return true; 
    int i = paramArrayOfString.length;
    for (byte b = 0; b < i; b++) {
      String str = paramArrayOfString[b];
      if (!VirtualCore.get().checkSelfPermission(str, paramBoolean))
        return false; 
    } 
    return true;
  }
  
  public static String[] findDangerousPermissions(List<String> paramList) {
    if (paramList == null)
      return null; 
    ArrayList<String> arrayList = new ArrayList();
    for (String str : paramList) {
      if (DANGEROUS_PERMISSION.contains(str))
        arrayList.add(str); 
    } 
    return arrayList.<String>toArray(new String[0]);
  }
  
  public static String[] findDangrousPermissions(String[] paramArrayOfString) {
    if (paramArrayOfString == null)
      return null; 
    ArrayList<String> arrayList = new ArrayList();
    int i = paramArrayOfString.length;
    for (byte b = 0; b < i; b++) {
      String str = paramArrayOfString[b];
      if (DANGEROUS_PERMISSION.contains(str))
        arrayList.add(str); 
    } 
    return arrayList.<String>toArray(new String[0]);
  }
  
  public static boolean isCheckPermissionRequired(ApplicationInfo paramApplicationInfo) {
    int i = Build.VERSION.SDK_INT;
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (i >= 23)
      if (VirtualCore.get().getTargetSdkVersion() < 23) {
        bool2 = bool1;
      } else {
        bool2 = bool1;
        if (paramApplicationInfo.targetSdkVersion < 23)
          bool2 = true; 
      }  
    return bool2;
  }
  
  public static boolean isRequestGranted(int[] paramArrayOfint) {
    // Byte code:
    //   0: aload_0
    //   1: arraylength
    //   2: istore_1
    //   3: iconst_0
    //   4: istore_2
    //   5: iconst_0
    //   6: istore_3
    //   7: iload_3
    //   8: iload_1
    //   9: if_icmpge -> 28
    //   12: aload_0
    //   13: iload_3
    //   14: iaload
    //   15: iconst_m1
    //   16: if_icmpne -> 22
    //   19: goto -> 30
    //   22: iinc #3, 1
    //   25: goto -> 7
    //   28: iconst_1
    //   29: istore_2
    //   30: iload_2
    //   31: ireturn
  }
  
  public static void startRequestPermissions(Context paramContext, boolean paramBoolean, String[] paramArrayOfString, final CallBack callBack) {
    RequestPermissionsActivity.request(paramContext, paramBoolean, paramArrayOfString, (IRequestPermissionsResult)new IRequestPermissionsResult.Stub() {
          public boolean onResult(int param1Int, String[] param1ArrayOfString, int[] param1ArrayOfint) {
            PermissionCompat.CallBack callBack = callBack;
            return (callBack != null) ? callBack.onResult(param1Int, param1ArrayOfString, param1ArrayOfint) : false;
          }
        });
  }
  
  public static interface CallBack {
    boolean onResult(int param1Int, String[] param1ArrayOfString, int[] param1ArrayOfint);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\PermissionCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */