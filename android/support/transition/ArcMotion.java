package android.support.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AttributeSet;
import org.xmlpull.v1.XmlPullParser;

public class ArcMotion extends PathMotion {
  private static final float DEFAULT_MAX_ANGLE_DEGREES = 70.0F;
  
  private static final float DEFAULT_MAX_TANGENT = (float)Math.tan(Math.toRadians(35.0D));
  
  private static final float DEFAULT_MIN_ANGLE_DEGREES = 0.0F;
  
  private float mMaximumAngle = 70.0F;
  
  private float mMaximumTangent = DEFAULT_MAX_TANGENT;
  
  private float mMinimumHorizontalAngle = 0.0F;
  
  private float mMinimumHorizontalTangent = 0.0F;
  
  private float mMinimumVerticalAngle = 0.0F;
  
  private float mMinimumVerticalTangent = 0.0F;
  
  public ArcMotion() {}
  
  public ArcMotion(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, Styleable.ARC_MOTION);
    XmlPullParser xmlPullParser = (XmlPullParser)paramAttributeSet;
    setMinimumVerticalAngle(TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "minimumVerticalAngle", 1, 0.0F));
    setMinimumHorizontalAngle(TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "minimumHorizontalAngle", 0, 0.0F));
    setMaximumAngle(TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "maximumAngle", 2, 70.0F));
    typedArray.recycle();
  }
  
  private static float toTangent(float paramFloat) {
    if (paramFloat >= 0.0F && paramFloat <= 90.0F)
      return (float)Math.tan(Math.toRadians((paramFloat / 2.0F))); 
    throw new IllegalArgumentException("Arc must be between 0 and 90 degrees");
  }
  
  public float getMaximumAngle() {
    return this.mMaximumAngle;
  }
  
  public float getMinimumHorizontalAngle() {
    return this.mMinimumHorizontalAngle;
  }
  
  public float getMinimumVerticalAngle() {
    return this.mMinimumVerticalAngle;
  }
  
  public Path getPath(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    boolean bool;
    Path path = new Path();
    path.moveTo(paramFloat1, paramFloat2);
    float f1 = paramFloat3 - paramFloat1;
    float f2 = paramFloat4 - paramFloat2;
    float f3 = f1 * f1 + f2 * f2;
    float f4 = (paramFloat1 + paramFloat3) / 2.0F;
    float f5 = (paramFloat2 + paramFloat4) / 2.0F;
    float f6 = 0.25F * f3;
    if (paramFloat2 > paramFloat4) {
      bool = true;
    } else {
      bool = false;
    } 
    if (Math.abs(f1) < Math.abs(f2)) {
      f3 = Math.abs(f3 / f2 * 2.0F);
      if (bool) {
        f3 += paramFloat4;
        f1 = paramFloat3;
      } else {
        f3 += paramFloat2;
        f1 = paramFloat1;
      } 
      f2 = this.mMinimumVerticalTangent;
    } else {
      f1 = f3 / f1 * 2.0F;
      if (bool) {
        f3 = paramFloat2;
        f1 += paramFloat1;
      } else {
        f1 = paramFloat3 - f1;
        f3 = paramFloat4;
      } 
      f2 = this.mMinimumHorizontalTangent;
    } 
    f2 = f6 * f2 * f2;
    float f7 = f4 - f1;
    float f8 = f5 - f3;
    f7 = f7 * f7 + f8 * f8;
    f8 = this.mMaximumTangent;
    f6 = f6 * f8 * f8;
    if (f7 >= f2)
      if (f7 > f6) {
        f2 = f6;
      } else {
        f2 = 0.0F;
      }  
    f8 = f3;
    f6 = f1;
    if (f2 != 0.0F) {
      f2 = (float)Math.sqrt((f2 / f7));
      f6 = (f1 - f4) * f2 + f4;
      f8 = f5 + f2 * (f3 - f5);
    } 
    path.cubicTo((paramFloat1 + f6) / 2.0F, (paramFloat2 + f8) / 2.0F, (f6 + paramFloat3) / 2.0F, (f8 + paramFloat4) / 2.0F, paramFloat3, paramFloat4);
    return path;
  }
  
  public void setMaximumAngle(float paramFloat) {
    this.mMaximumAngle = paramFloat;
    this.mMaximumTangent = toTangent(paramFloat);
  }
  
  public void setMinimumHorizontalAngle(float paramFloat) {
    this.mMinimumHorizontalAngle = paramFloat;
    this.mMinimumHorizontalTangent = toTangent(paramFloat);
  }
  
  public void setMinimumVerticalAngle(float paramFloat) {
    this.mMinimumVerticalAngle = paramFloat;
    this.mMinimumVerticalTangent = toTangent(paramFloat);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ArcMotion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */