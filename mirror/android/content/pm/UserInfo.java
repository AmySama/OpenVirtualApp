package mirror.android.content.pm;

import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefConstructor;
import mirror.RefStaticInt;

public class UserInfo {
  public static RefStaticInt FLAG_PRIMARY;
  
  public static Class<?> TYPE = RefClass.load(UserInfo.class, "android.content.pm.UserInfo");
  
  @MethodParams({int.class, String.class, int.class})
  public static RefConstructor<Object> ctor;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\pm\UserInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */