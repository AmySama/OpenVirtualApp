package mirror.android.view;

import android.graphics.Bitmap;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class SurfaceControl {
  public static Class<?> TYPE = RefClass.load(SurfaceControl.class, "android.view.SurfaceControl");
  
  @MethodParams({int.class, int.class})
  public static RefStaticMethod<Bitmap> screnshot;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\view\SurfaceControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */