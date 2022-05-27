package mirror.com.android.internal.telephony;

import android.os.IBinder;
import android.os.IInterface;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class IMms {
  public static Class<?> TYPE = RefClass.load(IMms.class, "com.android.internal.telephony.IMms");
  
  public static class Stub {
    public static Class<?> TYPE = RefClass.load(Stub.class, "com.android.internal.telephony.IMms$Stub");
    
    @MethodParams({IBinder.class})
    public static RefStaticMethod<IInterface> asInterface;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\com\android\internal\telephony\IMms.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */