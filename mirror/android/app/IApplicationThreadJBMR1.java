package mirror.android.app;

import mirror.MethodReflectParams;
import mirror.RefClass;
import mirror.RefMethod;

public class IApplicationThreadJBMR1 {
  public static Class<?> TYPE = RefClass.load(IApplicationThreadJBMR1.class, "android.app.IApplicationThread");
  
  @MethodReflectParams({"android.content.Intent", "android.content.pm.ActivityInfo", "android.content.res.CompatibilityInfo", "int", "java.lang.String", "android.os.Bundle", "boolean", "int"})
  public static RefMethod<Void> scheduleReceiver;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\IApplicationThreadJBMR1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */