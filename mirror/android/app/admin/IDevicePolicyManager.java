package mirror.android.app.admin;

import android.os.IBinder;
import android.os.IInterface;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class IDevicePolicyManager {
  public static Class<?> TYPE = RefClass.load(IDevicePolicyManager.class, "android.app.admin.IDevicePolicyManager");
  
  public static class Stub {
    public static Class<?> TYPE = RefClass.load(Stub.class, "android.app.admin.IDevicePolicyManager$Stub");
    
    @MethodParams({IBinder.class})
    public static RefStaticMethod<IInterface> asInterface;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\admin\IDevicePolicyManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */