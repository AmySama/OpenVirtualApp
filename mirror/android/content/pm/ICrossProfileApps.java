package mirror.android.content.pm;

import android.os.IBinder;
import android.os.IInterface;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class ICrossProfileApps {
  public static Class<?> TYPE = RefClass.load(ICrossProfileApps.class, "android.content.pm.ICrossProfileApps");
  
  public static class Stub {
    public static Class<?> TYPE = RefClass.load(Stub.class, "android.content.pm.ICrossProfileApps$Stub");
    
    @MethodParams({IBinder.class})
    public static RefStaticMethod<IInterface> asInterface;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\pm\ICrossProfileApps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */