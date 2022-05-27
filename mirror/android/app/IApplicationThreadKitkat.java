package mirror.android.app;

import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import mirror.MethodParams;
import mirror.MethodReflectParams;
import mirror.RefClass;
import mirror.RefMethod;
import mirror.android.content.res.CompatibilityInfo;

public class IApplicationThreadKitkat {
  public static Class<?> TYPE = RefClass.load(IApplicationThreadKitkat.class, "android.app.IApplicationThread");
  
  @MethodParams({IBinder.class, Intent.class, boolean.class, int.class})
  public static RefMethod<Void> scheduleBindService;
  
  @MethodParams({IBinder.class, ServiceInfo.class, CompatibilityInfo.class, int.class})
  public static RefMethod<Void> scheduleCreateService;
  
  @MethodReflectParams({"android.content.Intent", "android.content.pm.ActivityInfo", "android.content.res.CompatibilityInfo", "int", "java.lang.String", "android.os.Bundle", "boolean", "int", "int"})
  public static RefMethod<Void> scheduleReceiver;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\IApplicationThreadKitkat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */