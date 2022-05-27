package mirror.android.app;

import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import mirror.MethodParams;
import mirror.MethodReflectParams;
import mirror.RefClass;
import mirror.RefMethod;
import mirror.android.content.res.CompatibilityInfo;

public class IApplicationThreadICSMR1 {
  public static Class<?> TYPE = RefClass.load(IApplicationThreadICSMR1.class, "android.app.IApplicationThread");
  
  @MethodParams({IBinder.class, ServiceInfo.class, CompatibilityInfo.class})
  public static RefMethod<Void> scheduleCreateService;
  
  @MethodReflectParams({"android.content.Intent", "android.content.pm.ActivityInfo", "android.content.res.CompatibilityInfo", "int", "java.lang.String", "android.os.Bundle", "boolean"})
  public static RefMethod<Void> scheduleReceiver;
  
  @MethodParams({IBinder.class, boolean.class, int.class, int.class, Intent.class})
  public static RefMethod<Void> scheduleServiceArgs;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\IApplicationThreadICSMR1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */