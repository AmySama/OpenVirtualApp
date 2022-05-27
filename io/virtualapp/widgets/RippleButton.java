package io.virtualapp.widgets;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import io.virtualapp.R;

public class RippleButton extends AppCompatButton {
  private float mAlphaFactor;
  
  private boolean mAnimationIsCancel;
  
  private float mDensity;
  
  private float mDownX;
  
  private float mDownY;
  
  private boolean mHover = true;
  
  private boolean mIsAnimating = false;
  
  private float mMaxRadius;
  
  private Paint mPaint;
  
  private Path mPath = new Path();
  
  private RadialGradient mRadialGradient;
  
  private float mRadius;
  
  private ObjectAnimator mRadiusAnimator;
  
  private Rect mRect;
  
  private int mRippleColor;
  
  public RippleButton(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public RippleButton(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public RippleButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init();
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.RippleButton);
    this.mRippleColor = typedArray.getColor(2, this.mRippleColor);
    this.mAlphaFactor = typedArray.getFloat(0, this.mAlphaFactor);
    this.mHover = typedArray.getBoolean(1, this.mHover);
    typedArray.recycle();
  }
  
  private int dp(int paramInt) {
    return (int)(paramInt * this.mDensity + 0.5F);
  }
  
  public int adjustAlpha(int paramInt, float paramFloat) {
    return Color.argb(Math.round(Color.alpha(paramInt) * paramFloat), Color.red(paramInt), Color.green(paramInt), Color.blue(paramInt));
  }
  
  public void init() {
    this.mDensity = (getContext().getResources().getDisplayMetrics()).density;
    Paint paint = new Paint(1);
    this.mPaint = paint;
    paint.setAlpha(100);
    setRippleColor(-16777216, 0.2F);
  }
  
  protected void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    if (isInEditMode())
      return; 
    paramCanvas.save();
    this.mPath.reset();
    paramCanvas.clipPath(this.mPath);
    if (Build.VERSION.SDK_INT < 23)
      paramCanvas.restore(); 
    paramCanvas.drawCircle(this.mDownX, this.mDownY, this.mRadius, this.mPaint);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mMaxRadius = (float)Math.sqrt((paramInt1 * paramInt1 + paramInt2 * paramInt2));
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    ObjectAnimator objectAnimator;
    Log.d("TouchEvent", String.valueOf(paramMotionEvent.getActionMasked()));
    Log.d("mIsAnimating", String.valueOf(this.mIsAnimating));
    Log.d("mAnimationIsCancel", String.valueOf(this.mAnimationIsCancel));
    boolean bool = super.onTouchEvent(paramMotionEvent);
    if (paramMotionEvent.getActionMasked() == 0 && isEnabled() && this.mHover) {
      this.mRect = new Rect(getLeft(), getTop(), getRight(), getBottom());
      this.mAnimationIsCancel = false;
      this.mDownX = paramMotionEvent.getX();
      this.mDownY = paramMotionEvent.getY();
      objectAnimator = ObjectAnimator.ofFloat(this, "radius", new float[] { 0.0F, dp(50) }).setDuration(400L);
      this.mRadiusAnimator = objectAnimator;
      objectAnimator.setInterpolator((TimeInterpolator)new AccelerateDecelerateInterpolator());
      this.mRadiusAnimator.addListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator param1Animator) {}
            
            public void onAnimationEnd(Animator param1Animator) {
              RippleButton.this.setRadius(0.0F);
              RippleButton.this.setAlpha(1.0F);
              RippleButton.access$002(RippleButton.this, false);
            }
            
            public void onAnimationRepeat(Animator param1Animator) {}
            
            public void onAnimationStart(Animator param1Animator) {
              RippleButton.access$002(RippleButton.this, true);
            }
          });
      this.mRadiusAnimator.start();
      if (!bool)
        return true; 
    } else if (objectAnimator.getActionMasked() == 2 && isEnabled() && this.mHover) {
      this.mDownX = objectAnimator.getX();
      this.mDownY = objectAnimator.getY();
      int i = this.mRect.contains(getLeft() + (int)objectAnimator.getX(), getTop() + (int)objectAnimator.getY()) ^ true;
      this.mAnimationIsCancel = i;
      if (i != 0) {
        setRadius(0.0F);
      } else {
        setRadius(dp(50));
      } 
      if (!bool)
        return true; 
    } else if (objectAnimator.getActionMasked() == 1 && !this.mAnimationIsCancel && isEnabled()) {
      this.mDownX = objectAnimator.getX();
      float f1 = objectAnimator.getY();
      this.mDownY = f1;
      float f2 = this.mDownX;
      f1 = Math.max((float)Math.sqrt((f2 * f2 + f1 * f1)), this.mMaxRadius);
      if (this.mIsAnimating)
        this.mRadiusAnimator.cancel(); 
      objectAnimator = ObjectAnimator.ofFloat(this, "radius", new float[] { dp(50), f1 });
      this.mRadiusAnimator = objectAnimator;
      objectAnimator.setDuration(500L);
      this.mRadiusAnimator.setInterpolator((TimeInterpolator)new AccelerateDecelerateInterpolator());
      this.mRadiusAnimator.addListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator param1Animator) {}
            
            public void onAnimationEnd(Animator param1Animator) {
              RippleButton.this.setRadius(0.0F);
              RippleButton.this.setAlpha(1.0F);
              RippleButton.access$002(RippleButton.this, false);
            }
            
            public void onAnimationRepeat(Animator param1Animator) {}
            
            public void onAnimationStart(Animator param1Animator) {
              RippleButton.access$002(RippleButton.this, true);
            }
          });
      this.mRadiusAnimator.start();
      if (!bool)
        return true; 
    } 
    return bool;
  }
  
  public void setHover(boolean paramBoolean) {
    this.mHover = paramBoolean;
  }
  
  public void setRadius(float paramFloat) {
    this.mRadius = paramFloat;
    if (paramFloat > 0.0F) {
      RadialGradient radialGradient = new RadialGradient(this.mDownX, this.mDownY, this.mRadius, adjustAlpha(this.mRippleColor, this.mAlphaFactor), this.mRippleColor, Shader.TileMode.MIRROR);
      this.mRadialGradient = radialGradient;
      this.mPaint.setShader((Shader)radialGradient);
    } 
    invalidate();
  }
  
  public void setRippleColor(int paramInt, float paramFloat) {
    this.mRippleColor = paramInt;
    this.mAlphaFactor = paramFloat;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\RippleButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */