package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.v7.cardview.R;

class RoundRectDrawableWithShadow extends Drawable {
  private static final double COS_45 = Math.cos(Math.toRadians(45.0D));
  
  private static final float SHADOW_MULTIPLIER = 1.5F;
  
  static RoundRectHelper sRoundRectHelper;
  
  private boolean mAddPaddingForCorners = true;
  
  private ColorStateList mBackground;
  
  private final RectF mCardBounds;
  
  private float mCornerRadius;
  
  private Paint mCornerShadowPaint;
  
  private Path mCornerShadowPath;
  
  private boolean mDirty = true;
  
  private Paint mEdgeShadowPaint;
  
  private final int mInsetShadow;
  
  private Paint mPaint;
  
  private boolean mPrintedShadowClipWarning = false;
  
  private float mRawMaxShadowSize;
  
  private float mRawShadowSize;
  
  private final int mShadowEndColor;
  
  private float mShadowSize;
  
  private final int mShadowStartColor;
  
  RoundRectDrawableWithShadow(Resources paramResources, ColorStateList paramColorStateList, float paramFloat1, float paramFloat2, float paramFloat3) {
    this.mShadowStartColor = paramResources.getColor(R.color.cardview_shadow_start_color);
    this.mShadowEndColor = paramResources.getColor(R.color.cardview_shadow_end_color);
    this.mInsetShadow = paramResources.getDimensionPixelSize(R.dimen.cardview_compat_inset_shadow);
    this.mPaint = new Paint(5);
    setBackground(paramColorStateList);
    Paint paint = new Paint(5);
    this.mCornerShadowPaint = paint;
    paint.setStyle(Paint.Style.FILL);
    this.mCornerRadius = (int)(paramFloat1 + 0.5F);
    this.mCardBounds = new RectF();
    paint = new Paint(this.mCornerShadowPaint);
    this.mEdgeShadowPaint = paint;
    paint.setAntiAlias(false);
    setShadowSize(paramFloat2, paramFloat3);
  }
  
  private void buildComponents(Rect paramRect) {
    float f = this.mRawMaxShadowSize * 1.5F;
    this.mCardBounds.set(paramRect.left + this.mRawMaxShadowSize, paramRect.top + f, paramRect.right - this.mRawMaxShadowSize, paramRect.bottom - f);
    buildShadowCorners();
  }
  
  private void buildShadowCorners() {
    float f1 = this.mCornerRadius;
    RectF rectF1 = new RectF(-f1, -f1, f1, f1);
    RectF rectF2 = new RectF(rectF1);
    f1 = this.mShadowSize;
    rectF2.inset(-f1, -f1);
    Path path = this.mCornerShadowPath;
    if (path == null) {
      this.mCornerShadowPath = new Path();
    } else {
      path.reset();
    } 
    this.mCornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
    this.mCornerShadowPath.moveTo(-this.mCornerRadius, 0.0F);
    this.mCornerShadowPath.rLineTo(-this.mShadowSize, 0.0F);
    this.mCornerShadowPath.arcTo(rectF2, 180.0F, 90.0F, false);
    this.mCornerShadowPath.arcTo(rectF1, 270.0F, -90.0F, false);
    this.mCornerShadowPath.close();
    f1 = this.mCornerRadius;
    float f2 = f1 / (this.mShadowSize + f1);
    Paint paint = this.mCornerShadowPaint;
    float f3 = this.mCornerRadius;
    f1 = this.mShadowSize;
    int i = this.mShadowStartColor;
    int j = this.mShadowEndColor;
    Shader.TileMode tileMode = Shader.TileMode.CLAMP;
    paint.setShader((Shader)new RadialGradient(0.0F, 0.0F, f3 + f1, new int[] { i, i, j }, new float[] { 0.0F, f2, 1.0F }, tileMode));
    paint = this.mEdgeShadowPaint;
    f3 = this.mCornerRadius;
    f2 = -f3;
    f1 = this.mShadowSize;
    f3 = -f3;
    i = this.mShadowStartColor;
    j = this.mShadowEndColor;
    tileMode = Shader.TileMode.CLAMP;
    paint.setShader((Shader)new LinearGradient(0.0F, f2 + f1, 0.0F, f3 - f1, new int[] { i, i, j }, new float[] { 0.0F, 0.5F, 1.0F }, tileMode));
    this.mEdgeShadowPaint.setAntiAlias(false);
  }
  
  static float calculateHorizontalPadding(float paramFloat1, float paramFloat2, boolean paramBoolean) {
    float f = paramFloat1;
    if (paramBoolean)
      f = (float)(paramFloat1 + (1.0D - COS_45) * paramFloat2); 
    return f;
  }
  
  static float calculateVerticalPadding(float paramFloat1, float paramFloat2, boolean paramBoolean) {
    return paramBoolean ? (float)((paramFloat1 * 1.5F) + (1.0D - COS_45) * paramFloat2) : (paramFloat1 * 1.5F);
  }
  
  private void drawShadow(Canvas paramCanvas) {
    boolean bool;
    float f1 = this.mCornerRadius;
    float f2 = -f1 - this.mShadowSize;
    float f3 = f1 + this.mInsetShadow + this.mRawShadowSize / 2.0F;
    f1 = this.mCardBounds.width();
    float f4 = f3 * 2.0F;
    if (f1 - f4 > 0.0F) {
      i = 1;
    } else {
      i = 0;
    } 
    if (this.mCardBounds.height() - f4 > 0.0F) {
      bool = true;
    } else {
      bool = false;
    } 
    int j = paramCanvas.save();
    paramCanvas.translate(this.mCardBounds.left + f3, this.mCardBounds.top + f3);
    paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
    if (i)
      paramCanvas.drawRect(0.0F, f2, this.mCardBounds.width() - f4, -this.mCornerRadius, this.mEdgeShadowPaint); 
    paramCanvas.restoreToCount(j);
    j = paramCanvas.save();
    paramCanvas.translate(this.mCardBounds.right - f3, this.mCardBounds.bottom - f3);
    paramCanvas.rotate(180.0F);
    paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
    if (i)
      paramCanvas.drawRect(0.0F, f2, this.mCardBounds.width() - f4, -this.mCornerRadius + this.mShadowSize, this.mEdgeShadowPaint); 
    paramCanvas.restoreToCount(j);
    int i = paramCanvas.save();
    paramCanvas.translate(this.mCardBounds.left + f3, this.mCardBounds.bottom - f3);
    paramCanvas.rotate(270.0F);
    paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
    if (bool)
      paramCanvas.drawRect(0.0F, f2, this.mCardBounds.height() - f4, -this.mCornerRadius, this.mEdgeShadowPaint); 
    paramCanvas.restoreToCount(i);
    i = paramCanvas.save();
    paramCanvas.translate(this.mCardBounds.right - f3, this.mCardBounds.top + f3);
    paramCanvas.rotate(90.0F);
    paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
    if (bool)
      paramCanvas.drawRect(0.0F, f2, this.mCardBounds.height() - f4, -this.mCornerRadius, this.mEdgeShadowPaint); 
    paramCanvas.restoreToCount(i);
  }
  
  private void setBackground(ColorStateList paramColorStateList) {
    ColorStateList colorStateList = paramColorStateList;
    if (paramColorStateList == null)
      colorStateList = ColorStateList.valueOf(0); 
    this.mBackground = colorStateList;
    this.mPaint.setColor(colorStateList.getColorForState(getState(), this.mBackground.getDefaultColor()));
  }
  
  private void setShadowSize(float paramFloat1, float paramFloat2) {
    if (paramFloat1 >= 0.0F) {
      if (paramFloat2 >= 0.0F) {
        float f = toEven(paramFloat1);
        paramFloat2 = toEven(paramFloat2);
        paramFloat1 = f;
        if (f > paramFloat2) {
          if (!this.mPrintedShadowClipWarning)
            this.mPrintedShadowClipWarning = true; 
          paramFloat1 = paramFloat2;
        } 
        if (this.mRawShadowSize == paramFloat1 && this.mRawMaxShadowSize == paramFloat2)
          return; 
        this.mRawShadowSize = paramFloat1;
        this.mRawMaxShadowSize = paramFloat2;
        this.mShadowSize = (int)(paramFloat1 * 1.5F + this.mInsetShadow + 0.5F);
        this.mDirty = true;
        invalidateSelf();
        return;
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Invalid max shadow size ");
      stringBuilder1.append(paramFloat2);
      stringBuilder1.append(". Must be >= 0");
      throw new IllegalArgumentException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Invalid shadow size ");
    stringBuilder.append(paramFloat1);
    stringBuilder.append(". Must be >= 0");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  private int toEven(float paramFloat) {
    int i = (int)(paramFloat + 0.5F);
    int j = i;
    if (i % 2 == 1)
      j = i - 1; 
    return j;
  }
  
  public void draw(Canvas paramCanvas) {
    if (this.mDirty) {
      buildComponents(getBounds());
      this.mDirty = false;
    } 
    paramCanvas.translate(0.0F, this.mRawShadowSize / 2.0F);
    drawShadow(paramCanvas);
    paramCanvas.translate(0.0F, -this.mRawShadowSize / 2.0F);
    sRoundRectHelper.drawRoundRect(paramCanvas, this.mCardBounds, this.mCornerRadius, this.mPaint);
  }
  
  ColorStateList getColor() {
    return this.mBackground;
  }
  
  float getCornerRadius() {
    return this.mCornerRadius;
  }
  
  void getMaxShadowAndCornerPadding(Rect paramRect) {
    getPadding(paramRect);
  }
  
  float getMaxShadowSize() {
    return this.mRawMaxShadowSize;
  }
  
  float getMinHeight() {
    float f = this.mRawMaxShadowSize;
    return Math.max(f, this.mCornerRadius + this.mInsetShadow + f * 1.5F / 2.0F) * 2.0F + (this.mRawMaxShadowSize * 1.5F + this.mInsetShadow) * 2.0F;
  }
  
  float getMinWidth() {
    float f = this.mRawMaxShadowSize;
    return Math.max(f, this.mCornerRadius + this.mInsetShadow + f / 2.0F) * 2.0F + (this.mRawMaxShadowSize + this.mInsetShadow) * 2.0F;
  }
  
  public int getOpacity() {
    return -3;
  }
  
  public boolean getPadding(Rect paramRect) {
    int i = (int)Math.ceil(calculateVerticalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
    int j = (int)Math.ceil(calculateHorizontalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
    paramRect.set(j, i, j, i);
    return true;
  }
  
  float getShadowSize() {
    return this.mRawShadowSize;
  }
  
  public boolean isStateful() {
    boolean bool;
    ColorStateList colorStateList = this.mBackground;
    if ((colorStateList != null && colorStateList.isStateful()) || super.isStateful()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected void onBoundsChange(Rect paramRect) {
    super.onBoundsChange(paramRect);
    this.mDirty = true;
  }
  
  protected boolean onStateChange(int[] paramArrayOfint) {
    ColorStateList colorStateList = this.mBackground;
    int i = colorStateList.getColorForState(paramArrayOfint, colorStateList.getDefaultColor());
    if (this.mPaint.getColor() == i)
      return false; 
    this.mPaint.setColor(i);
    this.mDirty = true;
    invalidateSelf();
    return true;
  }
  
  void setAddPaddingForCorners(boolean paramBoolean) {
    this.mAddPaddingForCorners = paramBoolean;
    invalidateSelf();
  }
  
  public void setAlpha(int paramInt) {
    this.mPaint.setAlpha(paramInt);
    this.mCornerShadowPaint.setAlpha(paramInt);
    this.mEdgeShadowPaint.setAlpha(paramInt);
  }
  
  void setColor(ColorStateList paramColorStateList) {
    setBackground(paramColorStateList);
    invalidateSelf();
  }
  
  public void setColorFilter(ColorFilter paramColorFilter) {
    this.mPaint.setColorFilter(paramColorFilter);
  }
  
  void setCornerRadius(float paramFloat) {
    if (paramFloat >= 0.0F) {
      paramFloat = (int)(paramFloat + 0.5F);
      if (this.mCornerRadius == paramFloat)
        return; 
      this.mCornerRadius = paramFloat;
      this.mDirty = true;
      invalidateSelf();
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Invalid radius ");
    stringBuilder.append(paramFloat);
    stringBuilder.append(". Must be >= 0");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  void setMaxShadowSize(float paramFloat) {
    setShadowSize(this.mRawShadowSize, paramFloat);
  }
  
  void setShadowSize(float paramFloat) {
    setShadowSize(paramFloat, this.mRawMaxShadowSize);
  }
  
  static interface RoundRectHelper {
    void drawRoundRect(Canvas param1Canvas, RectF param1RectF, float param1Float, Paint param1Paint);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\RoundRectDrawableWithShadow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */