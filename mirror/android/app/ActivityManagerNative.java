package mirror.android.app;

import android.os.IInterface;
import mirror.RefClass;
import mirror.RefStaticMethod;
import mirror.RefStaticObject;

public class ActivityManagerNative {
  public static Class<?> TYPE = RefClass.load(ActivityManagerNative.class, "android.app.ActivityManagerNative");
  
  public static RefStaticObject<Object> gDefault;
  
  public static RefStaticMethod<IInterface> getDefault;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\ActivityManagerNative.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */