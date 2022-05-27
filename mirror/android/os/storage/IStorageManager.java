package mirror.android.os.storage;

import android.os.IBinder;
import android.os.IInterface;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class IStorageManager {
  public static Class<?> Class = RefClass.load(IStorageManager.class, "android.os.storage.IStorageManager");
  
  public static class Stub {
    public static Class<?> Class = RefClass.load(Stub.class, "android.os.storage.IStorageManager$Stub");
    
    @MethodParams({IBinder.class})
    public static RefStaticMethod<IInterface> asInterface;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\os\storage\IStorageManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */