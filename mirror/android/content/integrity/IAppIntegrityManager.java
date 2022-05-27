package mirror.android.content.integrity;

import android.os.IBinder;
import android.os.IInterface;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class IAppIntegrityManager {
  public static Class<?> TYPE = RefClass.load(IAppIntegrityManager.class, "android.content.integrity.IAppIntegrityManager");
  
  public static class Stub {
    public static Class<?> TYPE = RefClass.load(Stub.class, "android.content.integrity.IAppIntegrityManager$Stub");
    
    @MethodParams({IBinder.class})
    public static RefStaticMethod<IInterface> asInterface;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\integrity\IAppIntegrityManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */