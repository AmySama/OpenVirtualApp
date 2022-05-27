package mirror.android.view;

import android.os.IInterface;
import mirror.RefClass;
import mirror.RefStaticObject;

public class Display {
  public static Class<?> TYPE = RefClass.load(Display.class, android.view.Display.class);
  
  public static RefStaticObject<IInterface> sWindowManager;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\view\Display.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */