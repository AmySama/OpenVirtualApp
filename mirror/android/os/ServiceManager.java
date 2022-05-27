package mirror.android.os;

import android.os.IBinder;
import android.os.IInterface;
import java.util.Map;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;
import mirror.RefStaticObject;

public class ServiceManager {
  public static Class<?> TYPE = RefClass.load(ServiceManager.class, "android.os.ServiceManager");
  
  @MethodParams({String.class, IBinder.class})
  public static RefStaticMethod<Void> addService;
  
  public static RefStaticMethod<IBinder> checkService;
  
  public static RefStaticMethod<IInterface> getIServiceManager;
  
  public static RefStaticMethod<IBinder> getService;
  
  public static RefStaticMethod<String[]> listServices;
  
  public static RefStaticObject<Map<String, IBinder>> sCache;
  
  public static RefStaticObject<IInterface> sServiceManager;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\os\ServiceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */