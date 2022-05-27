package mirror.android.os;

import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class UserHandle {
  public static Class<?> TYPE = RefClass.load(UserHandle.class, "android.os.UserHandle");
  
  @MethodParams({int.class})
  public static RefStaticMethod<Integer> getUserId;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\os\UserHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */