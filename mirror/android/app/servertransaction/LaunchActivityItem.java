package mirror.android.app.servertransaction;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.IInterface;
import mirror.RefClass;
import mirror.RefObject;

public class LaunchActivityItem {
  public static Class<?> TYPE = RefClass.load(LaunchActivityItem.class, "android.app.servertransaction.LaunchActivityItem");
  
  public static RefObject<IInterface> mActivityClientController;
  
  public static RefObject<ActivityInfo> mInfo;
  
  public static RefObject<Intent> mIntent;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\servertransaction\LaunchActivityItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */