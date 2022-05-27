package mirror.android.security.net.config;

import android.content.Context;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class NetworkSecurityConfigProvider {
  public static Class<?> TYPE = RefClass.load(NetworkSecurityConfigProvider.class, "android.security.net.config.NetworkSecurityConfigProvider");
  
  @MethodParams({Context.class})
  public static RefStaticMethod<Void> install;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\security\net\config\NetworkSecurityConfigProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */