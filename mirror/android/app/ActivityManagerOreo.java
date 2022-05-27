package mirror.android.app;

import android.os.IInterface;
import mirror.RefClass;
import mirror.RefStaticMethod;
import mirror.RefStaticObject;

public class ActivityManagerOreo {
  public static RefStaticObject<Object> IActivityManagerSingleton;
  
  public static Class<?> TYPE = RefClass.load(ActivityManagerOreo.class, "android.app.ActivityManager");
  
  public static RefStaticMethod<IInterface> getService;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\ActivityManagerOreo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */