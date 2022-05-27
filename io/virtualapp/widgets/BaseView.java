package io.virtualapp.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public abstract class BaseView extends View {
  public ValueAnimator valueAnimator;
  
  public BaseView(Context paramContext) {
    this(paramContext, null);
  }
  
  public BaseView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public BaseView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    InitPaint();
  }
  
  private ValueAnimator startViewAnim(float paramFloat1, float paramFloat2, long paramLong) {
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[] { paramFloat1, paramFloat2 });
    this.valueAnimator = valueAnimator;
    valueAnimator.setDuration(paramLong);
    this.valueAnimator.setInterpolator((TimeInterpolator)new LinearInterpolator());
    this.valueAnimator.setRepeatCount(SetAnimRepeatCount());
    if (1 == SetAnimRepeatMode()) {
      this.valueAnimator.setRepeatMode(1);
    } else if (2 == SetAnimRepeatMode()) {
      this.valueAnimator.setRepeatMode(2);
    } 
    this.valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
            BaseView.this.OnAnimationUpdate(param1ValueAnimator);
          }
        });
    this.valueAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
          public void onAnimationEnd(Animator param1Animator) {
            super.onAnimationEnd(param1Animator);
          }
          
          public void onAnimationRepeat(Animator param1Animator) {
            super.onAnimationRepeat(param1Animator);
            BaseView.this.OnAnimationRepeat(param1Animator);
          }
          
          public void onAnimationStart(Animator param1Animator) {
            super.onAnimationStart(param1Animator);
          }
        });
    if (!this.valueAnimator.isRunning()) {
      AnimIsRunning();
      this.valueAnimator.start();
    } 
    return this.valueAnimator;
  }
  
  protected abstract void AnimIsRunning();
  
  protected abstract void InitPaint();
  
  protected abstract void OnAnimationRepeat(Animator paramAnimator);
  
  protected abstract void OnAnimationUpdate(ValueAnimator paramValueAnimator);
  
  protected abstract int OnStopAnim();
  
  protected abstract int SetAnimRepeatCount();
  
  protected abstract int SetAnimRepeatMode();
  
  public float getFontHeight(Paint paramPaint) {
    Paint.FontMetrics fontMetrics = paramPaint.getFontMetrics();
    return fontMetrics.descent - fontMetrics.ascent;
  }
  
  public float getFontHeight(Paint paramPaint, String paramString) {
    Rect rect = new Rect();
    paramPaint.getTextBounds(paramString, 0, paramString.length(), rect);
    return rect.height();
  }
  
  public float getFontlength(Paint paramPaint, String paramString) {
    Rect rect = new Rect();
    paramPaint.getTextBounds(paramString, 0, paramString.length(), rect);
    return rect.width();
  }
  
  public void startAnim() {
    stopAnim();
    startViewAnim(0.0F, 1.0F, 500L);
  }
  
  public void startAnim(int paramInt) {
    stopAnim();
    startViewAnim(0.0F, 1.0F, paramInt);
  }
  
  public void stopAnim() {
    if (this.valueAnimator != null) {
      clearAnimation();
      this.valueAnimator.setRepeatCount(0);
      this.valueAnimator.cancel();
      this.valueAnimator.end();
      if (OnStopAnim() == 0) {
        this.valueAnimator.setRepeatCount(0);
        this.valueAnimator.cancel();
        this.valueAnimator.end();
      } 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\BaseView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */