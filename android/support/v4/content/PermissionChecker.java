package android.support.v4.content;

import android.content.Context;
import android.os.Binder;
import android.os.Process;
import android.support.v4.app.AppOpsManagerCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PermissionChecker {
  public static final int PERMISSION_DENIED = -1;
  
  public static final int PERMISSION_DENIED_APP_OP = -2;
  
  public static final int PERMISSION_GRANTED = 0;
  
  public static int checkCallingOrSelfPermission(Context paramContext, String paramString) {
    String str;
    if (Binder.getCallingPid() == Process.myPid()) {
      str = paramContext.getPackageName();
    } else {
      str = null;
    } 
    return checkPermission(paramContext, paramString, Binder.getCallingPid(), Binder.getCallingUid(), str);
  }
  
  public static int checkCallingPermission(Context paramContext, String paramString1, String paramString2) {
    return (Binder.getCallingPid() == Process.myPid()) ? -1 : checkPermission(paramContext, paramString1, Binder.getCallingPid(), Binder.getCallingUid(), paramString2);
  }
  
  public static int checkPermission(Context paramContext, String paramString1, int paramInt1, int paramInt2, String paramString2) {
    String str1;
    if (paramContext.checkPermission(paramString1, paramInt1, paramInt2) == -1)
      return -1; 
    String str2 = AppOpsManagerCompat.permissionToOp(paramString1);
    if (str2 == null)
      return 0; 
    paramString1 = paramString2;
    if (paramString2 == null) {
      String[] arrayOfString = paramContext.getPackageManager().getPackagesForUid(paramInt2);
      if (arrayOfString == null || arrayOfString.length <= 0)
        return -1; 
      str1 = arrayOfString[0];
    } 
    return (AppOpsManagerCompat.noteProxyOpNoThrow(paramContext, str2, str1) != 0) ? -2 : 0;
  }
  
  public static int checkSelfPermission(Context paramContext, String paramString) {
    return checkPermission(paramContext, paramString, Process.myPid(), Process.myUid(), paramContext.getPackageName());
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface PermissionResult {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\content\PermissionChecker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */