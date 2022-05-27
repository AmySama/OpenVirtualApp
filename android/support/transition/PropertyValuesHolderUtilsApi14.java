package android.support.transition;

import android.animation.PropertyValuesHolder;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Property;

class PropertyValuesHolderUtilsApi14 implements PropertyValuesHolderUtilsImpl {
  public PropertyValuesHolder ofPointF(Property<?, PointF> paramProperty, Path paramPath) {
    return PropertyValuesHolder.ofFloat(new PathProperty(paramProperty, paramPath), new float[] { 0.0F, 1.0F });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\PropertyValuesHolderUtilsApi14.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */