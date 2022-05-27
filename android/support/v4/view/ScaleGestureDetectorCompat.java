package android.support.v4.view;

import android.os.Build;
import android.view.ScaleGestureDetector;

public final class ScaleGestureDetectorCompat {
  public static boolean isQuickScaleEnabled(ScaleGestureDetector paramScaleGestureDetector) {
    return (Build.VERSION.SDK_INT >= 19) ? paramScaleGestureDetector.isQuickScaleEnabled() : false;
  }
  
  @Deprecated
  public static boolean isQuickScaleEnabled(Object paramObject) {
    return isQuickScaleEnabled((ScaleGestureDetector)paramObject);
  }
  
  public static void setQuickScaleEnabled(ScaleGestureDetector paramScaleGestureDetector, boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 19)
      paramScaleGestureDetector.setQuickScaleEnabled(paramBoolean); 
  }
  
  @Deprecated
  public static void setQuickScaleEnabled(Object paramObject, boolean paramBoolean) {
    setQuickScaleEnabled((ScaleGestureDetector)paramObject, paramBoolean);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\ScaleGestureDetectorCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */