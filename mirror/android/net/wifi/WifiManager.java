package mirror.android.net.wifi;

import android.os.IInterface;
import mirror.RefClass;
import mirror.RefObject;
import mirror.RefStaticObject;

public class WifiManager {
  public static Class<?> TYPE = RefClass.load(WifiManager.class, android.net.wifi.WifiManager.class);
  
  public static RefObject<IInterface> mService;
  
  public static RefStaticObject<IInterface> sService;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\net\wifi\WifiManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */