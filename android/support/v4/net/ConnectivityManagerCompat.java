package android.support.v4.net;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ConnectivityManagerCompat {
  public static final int RESTRICT_BACKGROUND_STATUS_DISABLED = 1;
  
  public static final int RESTRICT_BACKGROUND_STATUS_ENABLED = 3;
  
  public static final int RESTRICT_BACKGROUND_STATUS_WHITELISTED = 2;
  
  public static NetworkInfo getNetworkInfoFromBroadcast(ConnectivityManager paramConnectivityManager, Intent paramIntent) {
    NetworkInfo networkInfo = (NetworkInfo)paramIntent.getParcelableExtra("networkInfo");
    return (networkInfo != null) ? paramConnectivityManager.getNetworkInfo(networkInfo.getType()) : null;
  }
  
  public static int getRestrictBackgroundStatus(ConnectivityManager paramConnectivityManager) {
    return (Build.VERSION.SDK_INT >= 24) ? paramConnectivityManager.getRestrictBackgroundStatus() : 3;
  }
  
  public static boolean isActiveNetworkMetered(ConnectivityManager paramConnectivityManager) {
    if (Build.VERSION.SDK_INT >= 16)
      return paramConnectivityManager.isActiveNetworkMetered(); 
    NetworkInfo networkInfo = paramConnectivityManager.getActiveNetworkInfo();
    if (networkInfo == null)
      return true; 
    int i = networkInfo.getType();
    return (i != 1 && i != 7 && i != 9);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface RestrictBackgroundStatus {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\net\ConnectivityManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */