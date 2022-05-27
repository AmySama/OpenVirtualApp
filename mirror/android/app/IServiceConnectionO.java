package mirror.android.app;

import android.content.ComponentName;
import android.os.IBinder;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefMethod;

public class IServiceConnectionO {
  public static Class<?> TYPE = RefClass.load(IServiceConnectionO.class, "android.app.IServiceConnection");
  
  @MethodParams({ComponentName.class, IBinder.class, boolean.class})
  public static RefMethod<Void> connected;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\IServiceConnectionO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */