package android.support.transition;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Property;

class ObjectAnimatorUtilsApi14 implements ObjectAnimatorUtilsImpl {
  public <T> ObjectAnimator ofPointF(T paramT, Property<T, PointF> paramProperty, Path paramPath) {
    return ObjectAnimator.ofFloat(paramT, new PathProperty<T>(paramProperty, paramPath), new float[] { 0.0F, 1.0F });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ObjectAnimatorUtilsApi14.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */