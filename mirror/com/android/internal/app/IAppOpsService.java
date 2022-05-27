package mirror.com.android.internal.app;

import android.os.IBinder;
import android.os.IInterface;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class IAppOpsService {
  public static Class<?> TYPE = RefClass.load(IAppOpsService.class, "com.android.internal.app.IAppOpsService");
  
  public static class Stub {
    public static Class<?> TYPE = RefClass.load(Stub.class, "com.android.internal.app.IAppOpsService$Stub");
    
    @MethodParams({IBinder.class})
    public static RefStaticMethod<IInterface> asInterface;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\com\android\internal\app\IAppOpsService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */