package mirror.android.app;

import android.os.IBinder;
import java.util.List;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefMethod;

public class ActivityThreadNMR1 {
  public static Class<?> Class = RefClass.load(ActivityThreadNMR1.class, "android.app.ActivityThread");
  
  @MethodParams({IBinder.class, List.class, boolean.class})
  public static RefMethod<Void> performNewIntents;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\ActivityThreadNMR1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */