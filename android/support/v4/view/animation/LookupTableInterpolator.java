package android.support.v4.view.animation;

import android.view.animation.Interpolator;

abstract class LookupTableInterpolator implements Interpolator {
  private final float mStepSize;
  
  private final float[] mValues;
  
  protected LookupTableInterpolator(float[] paramArrayOffloat) {
    this.mValues = paramArrayOffloat;
    this.mStepSize = 1.0F / (paramArrayOffloat.length - 1);
  }
  
  public float getInterpolation(float paramFloat) {
    if (paramFloat >= 1.0F)
      return 1.0F; 
    if (paramFloat <= 0.0F)
      return 0.0F; 
    float[] arrayOfFloat = this.mValues;
    int i = Math.min((int)((arrayOfFloat.length - 1) * paramFloat), arrayOfFloat.length - 2);
    float f1 = i;
    float f2 = this.mStepSize;
    paramFloat = (paramFloat - f1 * f2) / f2;
    arrayOfFloat = this.mValues;
    return arrayOfFloat[i] + paramFloat * (arrayOfFloat[i + 1] - arrayOfFloat[i]);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\animation\LookupTableInterpolator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */