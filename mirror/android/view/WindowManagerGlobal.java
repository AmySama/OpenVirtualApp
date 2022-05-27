package mirror.android.view;

import android.os.IInterface;
import mirror.RefClass;
import mirror.RefStaticInt;
import mirror.RefStaticObject;

public class WindowManagerGlobal {
  public static RefStaticInt ADD_PERMISSION_DENIED;
  
  public static Class<?> TYPE = RefClass.load(WindowManagerGlobal.class, "android.view.WindowManagerGlobal");
  
  public static RefStaticObject<IInterface> sWindowManagerService;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\view\WindowManagerGlobal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */