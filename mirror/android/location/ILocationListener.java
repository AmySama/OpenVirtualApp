package mirror.android.location;

import android.location.Location;
import android.os.IBinder;
import android.os.IInterface;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefMethod;
import mirror.RefStaticMethod;

public class ILocationListener {
  public static Class<?> TYPE = RefClass.load(ILocationListener.class, "android.location.ILocationListener");
  
  @MethodParams({Location.class})
  public static RefMethod<Void> onLocationChanged;
  
  public static class Stub {
    public static Class<?> TYPE = RefClass.load(Stub.class, "android.location.ILocationListener$Stub");
    
    @MethodParams({IBinder.class})
    public static RefStaticMethod<IInterface> asInterface;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\location\ILocationListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */