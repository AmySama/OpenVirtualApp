package mirror.android.os;

import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class Process {
  public static Class<?> TYPE = RefClass.load(Process.class, android.os.Process.class);
  
  @MethodParams({String.class})
  public static RefStaticMethod<Void> setArgV0;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\os\Process.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */