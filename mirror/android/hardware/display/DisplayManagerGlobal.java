package mirror.android.hardware.display;

import android.os.IInterface;
import mirror.RefClass;
import mirror.RefObject;
import mirror.RefStaticMethod;

public class DisplayManagerGlobal {
  public static Class<?> TYPE = RefClass.load(DisplayManagerGlobal.class, "android.hardware.display.DisplayManagerGlobal");
  
  public static RefStaticMethod<Object> getInstance;
  
  public static RefObject<IInterface> mDm;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\hardware\display\DisplayManagerGlobal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */