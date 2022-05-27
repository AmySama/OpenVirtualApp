package mirror.android.app;

import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import java.util.List;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefMethod;

public class IApplicationThread {
  public static Class<?> TYPE = RefClass.load(IApplicationThread.class, "android.app.IApplicationThread");
  
  @MethodParams({IBinder.class, Intent.class, boolean.class})
  public static RefMethod<Void> scheduleBindService;
  
  @MethodParams({IBinder.class, ServiceInfo.class})
  public static RefMethod<Void> scheduleCreateService;
  
  @MethodParams({List.class, IBinder.class})
  public static RefMethod<Void> scheduleNewIntent;
  
  @MethodParams({IBinder.class, int.class, int.class, Intent.class})
  public static RefMethod<Void> scheduleServiceArgs;
  
  @MethodParams({IBinder.class})
  public static RefMethod<Void> scheduleStopService;
  
  @MethodParams({IBinder.class, Intent.class})
  public static RefMethod<Void> scheduleUnbindService;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\IApplicationThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */