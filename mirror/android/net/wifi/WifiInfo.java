package mirror.android.net.wifi;

import android.net.wifi.SupplicantState;
import java.net.InetAddress;
import mirror.RefClass;
import mirror.RefConstructor;
import mirror.RefInt;
import mirror.RefObject;

public class WifiInfo {
  public static Class<?> TYPE = RefClass.load(WifiInfo.class, android.net.wifi.WifiInfo.class);
  
  public static RefConstructor<android.net.wifi.WifiInfo> ctor;
  
  public static RefObject<String> mBSSID;
  
  public static RefInt mFrequency;
  
  public static RefObject<InetAddress> mIpAddress;
  
  public static RefInt mLinkSpeed;
  
  public static RefObject<String> mMacAddress;
  
  public static RefInt mNetworkId;
  
  public static RefInt mRssi;
  
  public static RefObject<String> mSSID;
  
  public static RefObject<SupplicantState> mSupplicantState;
  
  public static RefObject<Object> mWifiSsid;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\net\wifi\WifiInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */