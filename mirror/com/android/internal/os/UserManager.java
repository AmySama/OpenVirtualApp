package mirror.com.android.internal.os;

import android.os.IBinder;
import android.os.IInterface;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class UserManager {
  public static Class<?> TYPE = RefClass.load(UserManager.class, "android.os.UserManager");
  
  public static class Stub {
    public static Class<?> TYPE = RefClass.load(Stub.class, "android.os.IUserManager$Stub");
    
    @MethodParams({IBinder.class})
    public static RefStaticMethod<IInterface> asInterface;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\com\android\internal\os\UserManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */