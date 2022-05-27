package mirror.com.android.internal.policy;

import android.os.IInterface;
import mirror.RefClass;
import mirror.RefStaticObject;

public class PhoneWindow {
  public static Class<?> TYPE;
  
  public static RefStaticObject<IInterface> sWindowManager;
  
  static {
    Class<?> clazz = RefClass.load(PhoneWindow.class, "com.android.internal.policy.impl.PhoneWindow$WindowManagerHolder");
    TYPE = clazz;
    if (clazz == null)
      TYPE = RefClass.load(PhoneWindow.class, "com.android.internal.policy.PhoneWindow$WindowManagerHolder"); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\com\android\internal\policy\PhoneWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */