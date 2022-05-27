package mirror.android.app;

import android.content.Intent;
import android.os.IBinder;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefMethod;

public class IActivityManagerICS {
  public static Class<?> TYPE = RefClass.load(IActivityManagerICS.class, "android.app.IActivityManager");
  
  @MethodParams({IBinder.class, int.class, Intent.class})
  public static RefMethod<Boolean> finishActivity;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\IActivityManagerICS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */