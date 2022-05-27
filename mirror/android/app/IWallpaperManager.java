package mirror.android.app;

import android.os.IBinder;
import android.os.IInterface;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class IWallpaperManager {
  public static Class<?> TYPE = RefClass.load(IWallpaperManager.class, "android.app.IWallpaperManager");
  
  public static class Stub {
    public static Class<?> TYPE = RefClass.load(IUsageStatsManager.Stub.class, "android.app.IWallpaperManager$Stub");
    
    @MethodParams({IBinder.class})
    public static RefStaticMethod<IInterface> asInterface;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\IWallpaperManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */