package mirror.android.app;

import android.os.IInterface;
import mirror.RefClass;
import mirror.RefObject;

public class AppOpsManager {
  public static Class<?> TYPE = RefClass.load(AppOpsManager.class, android.app.AppOpsManager.class);
  
  public static RefObject<IInterface> mService;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\AppOpsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */