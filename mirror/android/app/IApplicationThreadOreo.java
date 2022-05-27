package mirror.android.app;

import android.os.IBinder;
import android.os.IInterface;
import mirror.MethodParams;
import mirror.MethodReflectParams;
import mirror.RefClass;
import mirror.RefMethod;
import mirror.RefStaticMethod;

public class IApplicationThreadOreo {
  public static Class<?> TYPE = RefClass.load(IApplicationThreadOreo.class, "android.app.IApplicationThread");
  
  @MethodReflectParams({"android.os.IBinder", "android.content.pm.ParceledListSlice"})
  public static RefMethod<Void> scheduleServiceArgs;
  
  public static final class Stub {
    public static Class<?> TYPE = RefClass.load(Stub.class, "android.app.IApplicationThread$Stub");
    
    @MethodParams({IBinder.class})
    public static RefStaticMethod<IInterface> asInterface;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\IApplicationThreadOreo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */