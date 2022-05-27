package mirror.android.app;

import android.os.IInterface;
import mirror.RefClass;
import mirror.RefStaticMethod;
import mirror.RefStaticObject;

public class NotificationManager {
  public static Class<?> TYPE = RefClass.load(NotificationManager.class, "android.app.NotificationManager");
  
  public static RefStaticMethod<IInterface> getService;
  
  public static RefStaticObject<IInterface> sService;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\NotificationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */