package android.support.design.widget;

import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

class AnimationUtils {
  static final Interpolator DECELERATE_INTERPOLATOR;
  
  static final Interpolator FAST_OUT_LINEAR_IN_INTERPOLATOR;
  
  static final Interpolator FAST_OUT_SLOW_IN_INTERPOLATOR;
  
  static final Interpolator LINEAR_INTERPOLATOR = (Interpolator)new LinearInterpolator();
  
  static final Interpolator LINEAR_OUT_SLOW_IN_INTERPOLATOR;
  
  static {
    FAST_OUT_SLOW_IN_INTERPOLATOR = (Interpolator)new FastOutSlowInInterpolator();
    FAST_OUT_LINEAR_IN_INTERPOLATOR = (Interpolator)new FastOutLinearInInterpolator();
    LINEAR_OUT_SLOW_IN_INTERPOLATOR = (Interpolator)new LinearOutSlowInInterpolator();
    DECELERATE_INTERPOLATOR = (Interpolator)new DecelerateInterpolator();
  }
  
  static float lerp(float paramFloat1, float paramFloat2, float paramFloat3) {
    return paramFloat1 + paramFloat3 * (paramFloat2 - paramFloat1);
  }
  
  static int lerp(int paramInt1, int paramInt2, float paramFloat) {
    return paramInt1 + Math.round(paramFloat * (paramInt2 - paramInt1));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\AnimationUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */