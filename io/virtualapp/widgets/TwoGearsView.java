package io.virtualapp.widgets;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

public class TwoGearsView extends BaseView {
  float bigRingCenterX = 0.0F;
  
  float bigRingCenterY = 0.0F;
  
  float hypotenuse = 0.0F;
  
  float mAnimatedValue = 0.0F;
  
  private float mPadding = 0.0F;
  
  private Paint mPaint;
  
  private Paint mPaintAxle;
  
  private Paint mPaintRing;
  
  private int mWheelBigSpace = 8;
  
  private float mWheelLength;
  
  private int mWheelSmallSpace = 10;
  
  private float mWidth = 0.0F;
  
  float smallRingCenterX = 0.0F;
  
  float smallRingCenterY = 0.0F;
  
  ValueAnimator valueAnimator = null;
  
  public TwoGearsView(Context paramContext) {
    super(paramContext);
  }
  
  public TwoGearsView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public TwoGearsView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private int dip2px(float paramFloat) {
    return (int)(paramFloat * (getContext().getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  private void drawAxle(Canvas paramCanvas) {
    byte b3;
    byte b1 = 0;
    byte b2 = 0;
    while (true) {
      b3 = b1;
      if (b2 < 3) {
        double d1 = this.smallRingCenterX;
        double d2 = (b2 * 120) * Math.PI / 180.0D;
        float f1 = (float)(d1 * Math.cos(d2));
        float f2 = (float)(this.smallRingCenterY * Math.sin(d2));
        float f3 = this.mPadding;
        float f4 = this.smallRingCenterX;
        float f5 = this.smallRingCenterY;
        paramCanvas.drawLine(f3 + f4, f3 + f5, f4 + f3 - f1, f3 + f5 - f2, this.mPaintAxle);
        b2++;
        continue;
      } 
      break;
    } 
    while (b3 < 3) {
      double d2 = (this.bigRingCenterX - this.smallRingCenterX);
      double d1 = (b3 * 120) * Math.PI / 180.0D;
      float f4 = (float)(d2 * Math.cos(d1));
      float f6 = (float)((this.bigRingCenterY - this.smallRingCenterY) * Math.sin(d1));
      float f3 = this.bigRingCenterX;
      float f1 = this.mPadding;
      float f5 = this.mWheelLength;
      float f2 = this.bigRingCenterY;
      paramCanvas.drawLine(f3 + f1 + f5 * 2.0F, f2 + f1 + f5 * 2.0F, f3 + f1 + f5 * 2.0F - f4, f2 + f1 + f5 * 2.0F - f6, this.mPaintAxle);
      b3++;
    } 
  }
  
  private void drawBigGear(Canvas paramCanvas) {
    this.bigRingCenterX = (float)((this.hypotenuse / 2.0F) * Math.cos(0.7853981633974483D));
    this.bigRingCenterY = (float)((this.hypotenuse / 2.0F) * Math.sin(0.7853981633974483D));
    float f = (dip2px(1.5F) / 4);
    this.mPaint.setStrokeWidth(dip2px(1.5F));
    for (int i = 0; i < 360; i += this.mWheelBigSpace) {
      int j = (int)(360.0F - this.mAnimatedValue * this.mWheelBigSpace + i);
      double d1 = (this.bigRingCenterX - this.smallRingCenterX);
      double d2 = j * Math.PI / 180.0D;
      float f1 = (float)(d1 * Math.cos(d2));
      float f2 = (float)((this.bigRingCenterY - this.smallRingCenterY) * Math.sin(d2));
      float f3 = (float)((this.bigRingCenterX - this.smallRingCenterX + this.mWheelLength) * Math.cos(d2));
      float f4 = (float)((this.bigRingCenterY - this.smallRingCenterY + this.mWheelLength) * Math.sin(d2));
      float f5 = this.bigRingCenterX;
      float f6 = this.mPadding;
      float f7 = this.mWheelLength;
      float f8 = this.bigRingCenterY;
      paramCanvas.drawLine(f5 + f6 - f3 + f7 * 2.0F + f, f8 + f6 - f4 + f7 * 2.0F + f, f5 + f6 - f1 + f7 * 2.0F + f, f8 + f6 - f2 + f7 * 2.0F + f, this.mPaint);
    } 
  }
  
  private void drawBigRing(Canvas paramCanvas) {
    float f1 = (dip2px(1.5F) / 4);
    this.mPaintRing.setStrokeWidth(dip2px(1.5F));
    float f2 = this.bigRingCenterX;
    float f3 = this.mPadding;
    float f4 = this.mWheelLength;
    paramCanvas.drawCircle(f2 + f3 + f4 * 2.0F + f1, this.bigRingCenterY + f3 + f4 * 2.0F + f1, f2 - this.smallRingCenterX - f1, this.mPaintRing);
    this.mPaintRing.setStrokeWidth(dip2px(1.5F));
    f3 = this.bigRingCenterX;
    f2 = this.mPadding;
    f4 = this.mWheelLength;
    paramCanvas.drawCircle(f3 + f2 + f4 * 2.0F + f1, this.bigRingCenterY + f2 + f4 * 2.0F + f1, (f3 - this.smallRingCenterX) / 2.0F - f1, this.mPaintRing);
  }
  
  private void drawSmallGear(Canvas paramCanvas) {
    this.mPaint.setStrokeWidth(dip2px(1.0F));
    for (int i = 0; i < 360; i += this.mWheelSmallSpace) {
      int j = (int)(this.mAnimatedValue * this.mWheelSmallSpace + i);
      double d1 = this.smallRingCenterX;
      double d2 = j * Math.PI / 180.0D;
      float f1 = (float)(d1 * Math.cos(d2));
      float f2 = (float)(this.smallRingCenterY * Math.sin(d2));
      float f3 = (float)((this.smallRingCenterX + this.mWheelLength) * Math.cos(d2));
      float f4 = (float)((this.smallRingCenterY + this.mWheelLength) * Math.sin(d2));
      float f5 = this.mPadding;
      float f6 = this.smallRingCenterX;
      float f7 = this.smallRingCenterY;
      paramCanvas.drawLine(f5 + f6 - f3, f7 + f5 - f4, f6 + f5 - f1, f7 + f5 - f2, this.mPaint);
    } 
  }
  
  private void drawSmallRing(Canvas paramCanvas) {
    float f1 = (float)(this.mWidth * Math.sqrt(2.0D));
    this.hypotenuse = f1;
    this.smallRingCenterX = (float)((f1 / 6.0F) * Math.cos(0.7853981633974483D));
    this.smallRingCenterY = (float)((this.hypotenuse / 6.0F) * Math.sin(0.7853981633974483D));
    this.mPaintRing.setStrokeWidth(dip2px(1.0F));
    f1 = this.mPadding;
    float f2 = this.smallRingCenterX;
    paramCanvas.drawCircle(f1 + f2, this.smallRingCenterY + f1, f2, this.mPaintRing);
    this.mPaintRing.setStrokeWidth(dip2px(1.5F));
    f1 = this.mPadding;
    f2 = this.smallRingCenterX;
    paramCanvas.drawCircle(f1 + f2, this.smallRingCenterY + f1, f2 / 2.0F, this.mPaintRing);
  }
  
  private void initPaint() {
    Paint paint = new Paint();
    this.mPaintRing = paint;
    paint.setAntiAlias(true);
    this.mPaintRing.setStyle(Paint.Style.STROKE);
    this.mPaintRing.setColor(-1);
    this.mPaintRing.setStrokeWidth(dip2px(1.5F));
    paint = new Paint();
    this.mPaint = paint;
    paint.setAntiAlias(true);
    this.mPaint.setStyle(Paint.Style.STROKE);
    this.mPaint.setColor(-1);
    this.mPaint.setStrokeWidth(dip2px(1.0F));
    paint = new Paint();
    this.mPaintAxle = paint;
    paint.setAntiAlias(true);
    this.mPaintAxle.setStyle(Paint.Style.FILL);
    this.mPaintAxle.setColor(-1);
    this.mPaintAxle.setStrokeWidth(dip2px(1.5F));
    this.mWheelLength = dip2px(2.0F);
  }
  
  protected void AnimIsRunning() {}
  
  protected void InitPaint() {
    initPaint();
  }
  
  protected void OnAnimationRepeat(Animator paramAnimator) {}
  
  protected void OnAnimationUpdate(ValueAnimator paramValueAnimator) {
    this.mAnimatedValue = ((Float)paramValueAnimator.getAnimatedValue()).floatValue();
    postInvalidate();
  }
  
  protected int OnStopAnim() {
    postInvalidate();
    return 1;
  }
  
  protected int SetAnimRepeatCount() {
    return -1;
  }
  
  protected int SetAnimRepeatMode() {
    return 1;
  }
  
  protected void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    this.mPadding = dip2px(5.0F);
    paramCanvas.save();
    float f = this.mWidth;
    paramCanvas.rotate(180.0F, f / 2.0F, f / 2.0F);
    drawSmallRing(paramCanvas);
    drawSmallGear(paramCanvas);
    drawBigGear(paramCanvas);
    drawBigRing(paramCanvas);
    drawAxle(paramCanvas);
    paramCanvas.restore();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    super.onMeasure(paramInt1, paramInt2);
    if (getMeasuredWidth() > getHeight()) {
      this.mWidth = getMeasuredHeight();
    } else {
      this.mWidth = getMeasuredWidth();
    } 
  }
  
  public void setViewColor(int paramInt) {
    this.mPaint.setColor(paramInt);
    this.mPaintAxle.setColor(paramInt);
    this.mPaintRing.setColor(paramInt);
    postInvalidate();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\TwoGearsView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */