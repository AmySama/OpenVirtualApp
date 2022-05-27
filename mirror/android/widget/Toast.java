package mirror.android.widget;

import android.os.IInterface;
import mirror.RefClass;
import mirror.RefStaticObject;

public class Toast {
  public static Class<?> TYPE = RefClass.load(Toast.class, android.widget.Toast.class);
  
  public static RefStaticObject<IInterface> sService;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\widget\Toast.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */