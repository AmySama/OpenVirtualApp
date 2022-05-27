package android.support.transition;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Property;

class ObjectAnimatorUtilsApi21 implements ObjectAnimatorUtilsImpl {
  public <T> ObjectAnimator ofPointF(T paramT, Property<T, PointF> paramProperty, Path paramPath) {
    return ObjectAnimator.ofObject(paramT, paramProperty, null, paramPath);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ObjectAnimatorUtilsApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */