package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

class RoundRectDrawable extends Drawable {
  private ColorStateList mBackground;
  
  private final RectF mBoundsF;
  
  private final Rect mBoundsI;
  
  private boolean mInsetForPadding = false;
  
  private boolean mInsetForRadius = true;
  
  private float mPadding;
  
  private final Paint mPaint;
  
  private float mRadius;
  
  private ColorStateList mTint;
  
  private PorterDuffColorFilter mTintFilter;
  
  private PorterDuff.Mode mTintMode = PorterDuff.Mode.SRC_IN;
  
  RoundRectDrawable(ColorStateList paramColorStateList, float paramFloat) {
    this.mRadius = paramFloat;
    this.mPaint = new Paint(5);
    setBackground(paramColorStateList);
    this.mBoundsF = new RectF();
    this.mBoundsI = new Rect();
  }
  
  private PorterDuffColorFilter createTintFilter(ColorStateList paramColorStateList, PorterDuff.Mode paramMode) {
    return (paramColorStateList == null || paramMode == null) ? null : new PorterDuffColorFilter(paramColorStateList.getColorForState(getState(), 0), paramMode);
  }
  
  private void setBackground(ColorStateList paramColorStateList) {
    ColorStateList colorStateList = paramColorStateList;
    if (paramColorStateList == null)
      colorStateList = ColorStateList.valueOf(0); 
    this.mBackground = colorStateList;
    this.mPaint.setColor(colorStateList.getColorForState(getState(), this.mBackground.getDefaultColor()));
  }
  
  private void updateBounds(Rect paramRect) {
    Rect rect = paramRect;
    if (paramRect == null)
      rect = getBounds(); 
    this.mBoundsF.set(rect.left, rect.top, rect.right, rect.bottom);
    this.mBoundsI.set(rect);
    if (this.mInsetForPadding) {
      float f1 = RoundRectDrawableWithShadow.calculateVerticalPadding(this.mPadding, this.mRadius, this.mInsetForRadius);
      float f2 = RoundRectDrawableWithShadow.calculateHorizontalPadding(this.mPadding, this.mRadius, this.mInsetForRadius);
      this.mBoundsI.inset((int)Math.ceil(f2), (int)Math.ceil(f1));
      this.mBoundsF.set(this.mBoundsI);
    } 
  }
  
  public void draw(Canvas paramCanvas) {
    boolean bool;
    Paint paint = this.mPaint;
    if (this.mTintFilter != null && paint.getColorFilter() == null) {
      paint.setColorFilter((ColorFilter)this.mTintFilter);
      bool = true;
    } else {
      bool = false;
    } 
    RectF rectF = this.mBoundsF;
    float f = this.mRadius;
    paramCanvas.drawRoundRect(rectF, f, f, paint);
    if (bool)
      paint.setColorFilter(null); 
  }
  
  public ColorStateList getColor() {
    return this.mBackground;
  }
  
  public int getOpacity() {
    return -3;
  }
  
  public void getOutline(Outline paramOutline) {
    paramOutline.setRoundRect(this.mBoundsI, this.mRadius);
  }
  
  float getPadding() {
    return this.mPadding;
  }
  
  public float getRadius() {
    return this.mRadius;
  }
  
  public boolean isStateful() {
    ColorStateList colorStateList = this.mTint;
    if (colorStateList == null || !colorStateList.isStateful()) {
      colorStateList = this.mBackground;
      return ((colorStateList != null && colorStateList.isStateful()) || super.isStateful());
    } 
    return true;
  }
  
  protected void onBoundsChange(Rect paramRect) {
    super.onBoundsChange(paramRect);
    updateBounds(paramRect);
  }
  
  protected boolean onStateChange(int[] paramArrayOfint) {
    boolean bool;
    ColorStateList colorStateList2 = this.mBackground;
    int i = colorStateList2.getColorForState(paramArrayOfint, colorStateList2.getDefaultColor());
    if (i != this.mPaint.getColor()) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool)
      this.mPaint.setColor(i); 
    ColorStateList colorStateList1 = this.mTint;
    if (colorStateList1 != null) {
      PorterDuff.Mode mode = this.mTintMode;
      if (mode != null) {
        this.mTintFilter = createTintFilter(colorStateList1, mode);
        return true;
      } 
    } 
    return bool;
  }
  
  public void setAlpha(int paramInt) {
    this.mPaint.setAlpha(paramInt);
  }
  
  public void setColor(ColorStateList paramColorStateList) {
    setBackground(paramColorStateList);
    invalidateSelf();
  }
  
  public void setColorFilter(ColorFilter paramColorFilter) {
    this.mPaint.setColorFilter(paramColorFilter);
  }
  
  void setPadding(float paramFloat, boolean paramBoolean1, boolean paramBoolean2) {
    if (paramFloat == this.mPadding && this.mInsetForPadding == paramBoolean1 && this.mInsetForRadius == paramBoolean2)
      return; 
    this.mPadding = paramFloat;
    this.mInsetForPadding = paramBoolean1;
    this.mInsetForRadius = paramBoolean2;
    updateBounds((Rect)null);
    invalidateSelf();
  }
  
  void setRadius(float paramFloat) {
    if (paramFloat == this.mRadius)
      return; 
    this.mRadius = paramFloat;
    updateBounds((Rect)null);
    invalidateSelf();
  }
  
  public void setTintList(ColorStateList paramColorStateList) {
    this.mTint = paramColorStateList;
    this.mTintFilter = createTintFilter(paramColorStateList, this.mTintMode);
    invalidateSelf();
  }
  
  public void setTintMode(PorterDuff.Mode paramMode) {
    this.mTintMode = paramMode;
    this.mTintFilter = createTintFilter(this.mTint, paramMode);
    invalidateSelf();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\RoundRectDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */