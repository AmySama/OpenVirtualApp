package android.support.design.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.design.R;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.drawable.DrawableWrapper;

class ShadowDrawableWrapper extends DrawableWrapper {
  static final double COS_45 = Math.cos(Math.toRadians(45.0D));
  
  static final float SHADOW_BOTTOM_SCALE = 1.0F;
  
  static final float SHADOW_HORIZ_SCALE = 0.5F;
  
  static final float SHADOW_MULTIPLIER = 1.5F;
  
  static final float SHADOW_TOP_SCALE = 0.25F;
  
  private boolean mAddPaddingForCorners = true;
  
  final RectF mContentBounds;
  
  float mCornerRadius;
  
  final Paint mCornerShadowPaint;
  
  Path mCornerShadowPath;
  
  private boolean mDirty = true;
  
  final Paint mEdgeShadowPaint;
  
  float mMaxShadowSize;
  
  private boolean mPrintedShadowClipWarning = false;
  
  float mRawMaxShadowSize;
  
  float mRawShadowSize;
  
  private float mRotation;
  
  private final int mShadowEndColor;
  
  private final int mShadowMiddleColor;
  
  float mShadowSize;
  
  private final int mShadowStartColor;
  
  public ShadowDrawableWrapper(Context paramContext, Drawable paramDrawable, float paramFloat1, float paramFloat2, float paramFloat3) {
    super(paramDrawable);
    this.mShadowStartColor = ContextCompat.getColor(paramContext, R.color.design_fab_shadow_start_color);
    this.mShadowMiddleColor = ContextCompat.getColor(paramContext, R.color.design_fab_shadow_mid_color);
    this.mShadowEndColor = ContextCompat.getColor(paramContext, R.color.design_fab_shadow_end_color);
    Paint paint = new Paint(5);
    this.mCornerShadowPaint = paint;
    paint.setStyle(Paint.Style.FILL);
    this.mCornerRadius = Math.round(paramFloat1);
    this.mContentBounds = new RectF();
    paint = new Paint(this.mCornerShadowPaint);
    this.mEdgeShadowPaint = paint;
    paint.setAntiAlias(false);
    setShadowSize(paramFloat2, paramFloat3);
  }
  
  private void buildComponents(Rect paramRect) {
    float f = this.mRawMaxShadowSize * 1.5F;
    this.mContentBounds.set(paramRect.left + this.mRawMaxShadowSize, paramRect.top + f, paramRect.right - this.mRawMaxShadowSize, paramRect.bottom - f);
    getWrappedDrawable().setBounds((int)this.mContentBounds.left, (int)this.mContentBounds.top, (int)this.mContentBounds.right, (int)this.mContentBounds.bottom);
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
    f1 = -rectF2.top;
    if (f1 > 0.0F) {
      float f3 = this.mCornerRadius / f1;
      float f4 = (1.0F - f3) / 2.0F;
      Paint paint1 = this.mCornerShadowPaint;
      int m = this.mShadowStartColor;
      int n = this.mShadowMiddleColor;
      int i1 = this.mShadowEndColor;
      Shader.TileMode tileMode1 = Shader.TileMode.CLAMP;
      paint1.setShader((Shader)new RadialGradient(0.0F, 0.0F, f1, new int[] { 0, m, n, i1 }, new float[] { 0.0F, f3, f4 + f3, 1.0F }, tileMode1));
    } 
    Paint paint = this.mEdgeShadowPaint;
    float f2 = rectF1.top;
    f1 = rectF2.top;
    int k = this.mShadowStartColor;
    int j = this.mShadowMiddleColor;
    int i = this.mShadowEndColor;
    Shader.TileMode tileMode = Shader.TileMode.CLAMP;
    paint.setShader((Shader)new LinearGradient(0.0F, f2, 0.0F, f1, new int[] { k, j, i }, new float[] { 0.0F, 0.5F, 1.0F }, tileMode));
    this.mEdgeShadowPaint.setAntiAlias(false);
  }
  
  public static float calculateHorizontalPadding(float paramFloat1, float paramFloat2, boolean paramBoolean) {
    float f = paramFloat1;
    if (paramBoolean)
      f = (float)(paramFloat1 + (1.0D - COS_45) * paramFloat2); 
    return f;
  }
  
  public static float calculateVerticalPadding(float paramFloat1, float paramFloat2, boolean paramBoolean) {
    return paramBoolean ? (float)((paramFloat1 * 1.5F) + (1.0D - COS_45) * paramFloat2) : (paramFloat1 * 1.5F);
  }
  
  private void drawShadow(Canvas paramCanvas) {
    boolean bool;
    int i = paramCanvas.save();
    paramCanvas.rotate(this.mRotation, this.mContentBounds.centerX(), this.mContentBounds.centerY());
    float f1 = this.mCornerRadius;
    float f2 = -f1 - this.mShadowSize;
    float f3 = this.mContentBounds.width();
    float f4 = f1 * 2.0F;
    if (f3 - f4 > 0.0F) {
      j = 1;
    } else {
      j = 0;
    } 
    if (this.mContentBounds.height() - f4 > 0.0F) {
      bool = true;
    } else {
      bool = false;
    } 
    f3 = this.mRawShadowSize;
    float f5 = f1 / (f3 - 0.5F * f3 + f1);
    float f6 = f1 / (f3 - 0.25F * f3 + f1);
    f3 = f1 / (f3 - f3 * 1.0F + f1);
    int k = paramCanvas.save();
    paramCanvas.translate(this.mContentBounds.left + f1, this.mContentBounds.top + f1);
    paramCanvas.scale(f5, f6);
    paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
    if (j) {
      paramCanvas.scale(1.0F / f5, 1.0F);
      paramCanvas.drawRect(0.0F, f2, this.mContentBounds.width() - f4, -this.mCornerRadius, this.mEdgeShadowPaint);
    } 
    paramCanvas.restoreToCount(k);
    k = paramCanvas.save();
    paramCanvas.translate(this.mContentBounds.right - f1, this.mContentBounds.bottom - f1);
    paramCanvas.scale(f5, f3);
    paramCanvas.rotate(180.0F);
    paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
    if (j) {
      paramCanvas.scale(1.0F / f5, 1.0F);
      paramCanvas.drawRect(0.0F, f2, this.mContentBounds.width() - f4, -this.mCornerRadius + this.mShadowSize, this.mEdgeShadowPaint);
    } 
    paramCanvas.restoreToCount(k);
    int j = paramCanvas.save();
    paramCanvas.translate(this.mContentBounds.left + f1, this.mContentBounds.bottom - f1);
    paramCanvas.scale(f5, f3);
    paramCanvas.rotate(270.0F);
    paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
    if (bool) {
      paramCanvas.scale(1.0F / f3, 1.0F);
      paramCanvas.drawRect(0.0F, f2, this.mContentBounds.height() - f4, -this.mCornerRadius, this.mEdgeShadowPaint);
    } 
    paramCanvas.restoreToCount(j);
    j = paramCanvas.save();
    paramCanvas.translate(this.mContentBounds.right - f1, this.mContentBounds.top + f1);
    paramCanvas.scale(f5, f6);
    paramCanvas.rotate(90.0F);
    paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
    if (bool) {
      paramCanvas.scale(1.0F / f6, 1.0F);
      paramCanvas.drawRect(0.0F, f2, this.mContentBounds.height() - f4, -this.mCornerRadius, this.mEdgeShadowPaint);
    } 
    paramCanvas.restoreToCount(j);
    paramCanvas.restoreToCount(i);
  }
  
  private static int toEven(float paramFloat) {
    int i = Math.round(paramFloat);
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
    drawShadow(paramCanvas);
    super.draw(paramCanvas);
  }
  
  public float getCornerRadius() {
    return this.mCornerRadius;
  }
  
  public float getMaxShadowSize() {
    return this.mRawMaxShadowSize;
  }
  
  public float getMinHeight() {
    float f = this.mRawMaxShadowSize;
    return Math.max(f, this.mCornerRadius + f * 1.5F / 2.0F) * 2.0F + this.mRawMaxShadowSize * 1.5F * 2.0F;
  }
  
  public float getMinWidth() {
    float f = this.mRawMaxShadowSize;
    return Math.max(f, this.mCornerRadius + f / 2.0F) * 2.0F + this.mRawMaxShadowSize * 2.0F;
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
  
  public float getShadowSize() {
    return this.mRawShadowSize;
  }
  
  protected void onBoundsChange(Rect paramRect) {
    this.mDirty = true;
  }
  
  public void setAddPaddingForCorners(boolean paramBoolean) {
    this.mAddPaddingForCorners = paramBoolean;
    invalidateSelf();
  }
  
  public void setAlpha(int paramInt) {
    super.setAlpha(paramInt);
    this.mCornerShadowPaint.setAlpha(paramInt);
    this.mEdgeShadowPaint.setAlpha(paramInt);
  }
  
  public void setCornerRadius(float paramFloat) {
    paramFloat = Math.round(paramFloat);
    if (this.mCornerRadius == paramFloat)
      return; 
    this.mCornerRadius = paramFloat;
    this.mDirty = true;
    invalidateSelf();
  }
  
  public void setMaxShadowSize(float paramFloat) {
    setShadowSize(this.mRawShadowSize, paramFloat);
  }
  
  final void setRotation(float paramFloat) {
    if (this.mRotation != paramFloat) {
      this.mRotation = paramFloat;
      invalidateSelf();
    } 
  }
  
  public void setShadowSize(float paramFloat) {
    setShadowSize(paramFloat, this.mRawMaxShadowSize);
  }
  
  void setShadowSize(float paramFloat1, float paramFloat2) {
    if (paramFloat1 >= 0.0F && paramFloat2 >= 0.0F) {
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
      this.mShadowSize = Math.round(paramFloat1 * 1.5F);
      this.mMaxShadowSize = paramFloat2;
      this.mDirty = true;
      invalidateSelf();
      return;
    } 
    throw new IllegalArgumentException("invalid shadow size");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\ShadowDrawableWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */