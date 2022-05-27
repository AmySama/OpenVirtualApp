package mirror.android.content.pm;

import android.content.pm.ApplicationInfo;
import mirror.RefClass;
import mirror.RefObject;

public class ApplicationInfoN {
  public static Class<?> TYPE = RefClass.load(ApplicationInfoN.class, ApplicationInfo.class);
  
  public static RefObject<String> credentialEncryptedDataDir;
  
  public static RefObject<String> credentialProtectedDataDir;
  
  public static RefObject<String> deviceEncryptedDataDir;
  
  public static RefObject<String> deviceProtectedDataDir;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\pm\ApplicationInfoN.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */