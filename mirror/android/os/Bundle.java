package mirror.android.os;

import android.os.IBinder;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefMethod;

public class Bundle {
  public static Class<?> TYPE = RefClass.load(Bundle.class, android.os.Bundle.class);
  
  @MethodParams({String.class})
  public static RefMethod<IBinder> getIBinder;
  
  @MethodParams({String.class, IBinder.class})
  public static RefMethod<Void> putIBinder;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\os\Bundle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */