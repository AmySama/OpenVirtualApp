package android.support.transition;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.util.Property;

class ObjectAnimatorUtils {
  private static final ObjectAnimatorUtilsImpl IMPL;
  
  static {
    if (Build.VERSION.SDK_INT >= 21) {
      IMPL = new ObjectAnimatorUtilsApi21();
    } else {
      IMPL = new ObjectAnimatorUtilsApi14();
    } 
  }
  
  static <T> ObjectAnimator ofPointF(T paramT, Property<T, PointF> paramProperty, Path paramPath) {
    return IMPL.ofPointF(paramT, paramProperty, paramPath);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ObjectAnimatorUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */