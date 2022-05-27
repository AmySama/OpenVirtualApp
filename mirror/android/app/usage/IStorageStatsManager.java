package mirror.android.app.usage;

import android.os.IBinder;
import android.os.IInterface;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;
import mirror.android.bluetooth.IBluetoothManager;

public class IStorageStatsManager {
  public static Class<?> TYPE = RefClass.load(IBluetoothManager.class, "android.app.usage.IStorageStatsManager");
  
  public static class Stub {
    public static Class<?> TYPE = RefClass.load(IBluetoothManager.Stub.class, "android.app.usage.IStorageStatsManager$Stub");
    
    @MethodParams({IBinder.class})
    public static RefStaticMethod<IInterface> asInterface;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\ap\\usage\IStorageStatsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */