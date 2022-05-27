package mirror.android.app;

import android.os.IBinder;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefMethod;

public class ClientTransactionHandler {
  public static Class<?> TYPE = RefClass.load(ClientTransactionHandler.class, "android.app.ClientTransactionHandler");
  
  @MethodParams({IBinder.class})
  public static RefMethod<Object> getActivityClient;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\ClientTransactionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */