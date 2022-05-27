package io.virtualapp.widgets;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

public class EatBeansView extends BaseView {
  private float beansWidth = 10.0F;
  
  private float eatErEndAngle = 360.0F - 34.0F * 2.0F;
  
  private float eatErPositionX = 0.0F;
  
  private float eatErStartAngle = 34.0F;
  
  private float eatErWidth = 60.0F;
  
  int eatSpeed = 5;
  
  private float mAngle = 34.0F;
  
  private float mHigh = 0.0F;
  
  private float mPadding = 5.0F;
  
  private Paint mPaint;
  
  private Paint mPaintEye;
  
  private float mWidth = 0.0F;
  
  public EatBeansView(Context paramContext) {
    super(paramContext);
  }
  
  public EatBeansView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public EatBeansView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void initPaint() {
    Paint paint = new Paint();
    this.mPaint = paint;
    paint.setAntiAlias(true);
    this.mPaint.setStyle(Paint.Style.FILL);
    this.mPaint.setColor(-1);
    paint = new Paint();
    this.mPaintEye = paint;
    paint.setAntiAlias(true);
    this.mPaintEye.setStyle(Paint.Style.FILL);
    this.mPaintEye.setColor(-16777216);
  }
  
  protected void AnimIsRunning() {}
  
  protected void InitPaint() {
    initPaint();
  }
  
  protected void OnAnimationRepeat(Animator paramAnimator) {}
  
  protected void OnAnimationUpdate(ValueAnimator paramValueAnimator) {
    float f1 = ((Float)paramValueAnimator.getAnimatedValue()).floatValue();
    this.eatErPositionX = (this.mWidth - this.mPadding * 2.0F - this.eatErWidth) * f1;
    float f2 = this.mAngle;
    int i = this.eatSpeed;
    f1 = f2 * (1.0F - i * f1 - (int)(f1 * i));
    this.eatErStartAngle = f1;
    this.eatErEndAngle = 360.0F - f1 * 2.0F;
    invalidate();
  }
  
  protected int OnStopAnim() {
    this.eatErPositionX = 0.0F;
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
    float f1 = this.mPadding + this.eatErWidth + this.eatErPositionX;
    float f2 = this.mPadding;
    float f3 = this.eatErPositionX;
    float f4 = this.mHigh;
    float f5 = f4 / 2.0F;
    float f6 = this.eatErWidth;
    paramCanvas.drawArc(new RectF(f2 + f3, f5 - f6 / 2.0F, f1, f4 / 2.0F + f6 / 2.0F), this.eatErStartAngle, this.eatErEndAngle, true, this.mPaint);
    f6 = this.mPadding;
    f5 = this.eatErPositionX;
    f3 = this.eatErWidth;
    paramCanvas.drawCircle(f6 + f5 + f3 / 2.0F, this.mHigh / 2.0F - f3 / 4.0F, this.beansWidth / 2.0F, this.mPaintEye);
    int i = (int)((this.mWidth - this.mPadding * 2.0F - this.eatErWidth) / this.beansWidth / 2.0F);
    for (byte b = 0; b < i; b++) {
      f5 = (i * b);
      f6 = this.beansWidth;
      f5 = f5 + f6 / 2.0F + this.mPadding + this.eatErWidth;
      if (f5 > f1)
        paramCanvas.drawCircle(f5, this.mHigh / 2.0F, f6 / 2.0F, this.mPaint); 
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    super.onMeasure(paramInt1, paramInt2);
    this.mWidth = getMeasuredWidth();
    this.mHigh = getMeasuredHeight();
  }
  
  public void setEyeColor(int paramInt) {
    this.mPaintEye.setColor(paramInt);
    postInvalidate();
  }
  
  public void setViewColor(int paramInt) {
    this.mPaint.setColor(paramInt);
    postInvalidate();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\EatBeansView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */