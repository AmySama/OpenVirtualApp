package mirror.android.content.pm;

import android.content.pm.PackageManager;
import android.os.IInterface;
import mirror.RefClass;
import mirror.RefObject;
import mirror.com.android.internal.os.UserManager;

public class LauncherApps {
  public static Class<?> TYPE = RefClass.load(LauncherApps.class, "android.content.pm.LauncherApps");
  
  public static RefObject<PackageManager> mPm;
  
  public static RefObject<IInterface> mService;
  
  public static RefObject<UserManager> mUserManager;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\pm\LauncherApps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */