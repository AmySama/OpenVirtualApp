package mirror.android.app;

import android.os.IInterface;
import mirror.RefClass;
import mirror.RefInt;
import mirror.RefObject;

public class AlarmManager {
  public static Class<?> TYPE = RefClass.load(AlarmManager.class, android.app.AlarmManager.class);
  
  public static RefObject<IInterface> mService;
  
  public static RefInt mTargetSdkVersion;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\AlarmManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */