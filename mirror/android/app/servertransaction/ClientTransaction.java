package mirror.android.app.servertransaction;

import android.os.IBinder;
import java.util.List;
import mirror.RefClass;
import mirror.RefObject;

public class ClientTransaction {
  public static Class<?> TYPE = RefClass.load(ClientTransaction.class, "android.app.servertransaction.ClientTransaction");
  
  public static RefObject<List<Object>> mActivityCallbacks;
  
  public static RefObject<IBinder> mActivityToken;
  
  public static RefObject<Object> mLifecycleStateRequest;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\servertransaction\ClientTransaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */