package android.support.v4.app;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Build;

public final class AppOpsManagerCompat {
  public static final int MODE_ALLOWED = 0;
  
  public static final int MODE_DEFAULT = 3;
  
  public static final int MODE_ERRORED = 2;
  
  public static final int MODE_IGNORED = 1;
  
  public static int noteOp(Context paramContext, String paramString1, int paramInt, String paramString2) {
    return (Build.VERSION.SDK_INT >= 19) ? ((AppOpsManager)paramContext.getSystemService("appops")).noteOp(paramString1, paramInt, paramString2) : 1;
  }
  
  public static int noteOpNoThrow(Context paramContext, String paramString1, int paramInt, String paramString2) {
    return (Build.VERSION.SDK_INT >= 19) ? ((AppOpsManager)paramContext.getSystemService("appops")).noteOpNoThrow(paramString1, paramInt, paramString2) : 1;
  }
  
  public static int noteProxyOp(Context paramContext, String paramString1, String paramString2) {
    return (Build.VERSION.SDK_INT >= 23) ? ((AppOpsManager)paramContext.getSystemService(AppOpsManager.class)).noteProxyOp(paramString1, paramString2) : 1;
  }
  
  public static int noteProxyOpNoThrow(Context paramContext, String paramString1, String paramString2) {
    return (Build.VERSION.SDK_INT >= 23) ? ((AppOpsManager)paramContext.getSystemService(AppOpsManager.class)).noteProxyOpNoThrow(paramString1, paramString2) : 1;
  }
  
  public static String permissionToOp(String paramString) {
    return (Build.VERSION.SDK_INT >= 23) ? AppOpsManager.permissionToOp(paramString) : null;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\AppOpsManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */