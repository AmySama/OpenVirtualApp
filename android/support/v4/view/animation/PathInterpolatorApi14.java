package android.support.v4.view.animation;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.Interpolator;

class PathInterpolatorApi14 implements Interpolator {
  private static final float PRECISION = 0.002F;
  
  private final float[] mX;
  
  private final float[] mY;
  
  PathInterpolatorApi14(float paramFloat1, float paramFloat2) {
    this(createQuad(paramFloat1, paramFloat2));
  }
  
  PathInterpolatorApi14(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    this(createCubic(paramFloat1, paramFloat2, paramFloat3, paramFloat4));
  }
  
  PathInterpolatorApi14(Path paramPath) {
    PathMeasure pathMeasure = new PathMeasure(paramPath, false);
    float f = pathMeasure.getLength();
    int i = (int)(f / 0.002F) + 1;
    this.mX = new float[i];
    this.mY = new float[i];
    float[] arrayOfFloat = new float[2];
    for (byte b = 0; b < i; b++) {
      pathMeasure.getPosTan(b * f / (i - 1), arrayOfFloat, null);
      this.mX[b] = arrayOfFloat[0];
      this.mY[b] = arrayOfFloat[1];
    } 
  }
  
  private static Path createCubic(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    Path path = new Path();
    path.moveTo(0.0F, 0.0F);
    path.cubicTo(paramFloat1, paramFloat2, paramFloat3, paramFloat4, 1.0F, 1.0F);
    return path;
  }
  
  private static Path createQuad(float paramFloat1, float paramFloat2) {
    Path path = new Path();
    path.moveTo(0.0F, 0.0F);
    path.quadTo(paramFloat1, paramFloat2, 1.0F, 1.0F);
    return path;
  }
  
  public float getInterpolation(float paramFloat) {
    if (paramFloat <= 0.0F)
      return 0.0F; 
    if (paramFloat >= 1.0F)
      return 1.0F; 
    int i = 0;
    int j = this.mX.length - 1;
    while (j - i > 1) {
      int k = (i + j) / 2;
      if (paramFloat < this.mX[k]) {
        j = k;
        continue;
      } 
      i = k;
    } 
    float[] arrayOfFloat = this.mX;
    float f = arrayOfFloat[j] - arrayOfFloat[i];
    if (f == 0.0F)
      return this.mY[i]; 
    f = (paramFloat - arrayOfFloat[i]) / f;
    arrayOfFloat = this.mY;
    paramFloat = arrayOfFloat[i];
    return paramFloat + f * (arrayOfFloat[j] - paramFloat);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\animation\PathInterpolatorApi14.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */