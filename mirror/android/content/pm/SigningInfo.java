package mirror.android.content.pm;

import android.content.pm.PackageParser;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefConstructor;

public class SigningInfo {
  public static Class<?> TYPE = RefClass.load(SigningInfo.class, "android.content.pm.SigningInfo");
  
  @MethodParams({PackageParser.SigningDetails.class})
  public static RefConstructor<Object> ctor;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\pm\SigningInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */