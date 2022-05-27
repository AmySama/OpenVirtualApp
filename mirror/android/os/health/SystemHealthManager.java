package mirror.android.os.health;

import android.os.IInterface;
import mirror.RefClass;
import mirror.RefObject;

public class SystemHealthManager {
  public static Class<?> TYPE = RefClass.load(SystemHealthManager.class, android.os.health.SystemHealthManager.class);
  
  public static RefObject<IInterface> mBatteryStats;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\os\health\SystemHealthManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */