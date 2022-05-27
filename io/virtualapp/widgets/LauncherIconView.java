package io.virtualapp.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import io.virtualapp.R;

public class LauncherIconView extends AppCompatImageView implements ShimmerViewBase {
  private static final int SMOOTH_ANIM_THRESHOLD = 5;
  
  private static final String TAG = "LauncherIconView";
  
  private int mHeight;
  
  private ValueAnimator mInterAnim;
  
  private float mInterDelta;
  
  private boolean mIsSquare;
  
  private float mMaskAnimDelta;
  
  private boolean mMaskAnimRunning;
  
  private int mMaskColor;
  
  private float mMaxMaskRadius;
  
  private long mMediumAnimTime;
  
  private Paint mPaint;
  
  private float mProgress;
  
  private ValueAnimator mProgressAnimator;
  
  private RectF mProgressOval;
  
  private float mRadius;
  
  private Shimmer mShimmer;
  
  private Paint mShimmerPaint;
  
  private ShimmerViewHelper mShimmerViewHelper;
  
  private int mStrokeWidth;
  
  private int mWidth;
  
  public LauncherIconView(Context paramContext) {
    super(paramContext);
    init(paramContext, (AttributeSet)null);
  }
  
  public LauncherIconView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }
  
  public LauncherIconView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  private void drawMask(Canvas paramCanvas) {
    paramCanvas.drawRect(0.0F, 0.0F, this.mWidth, this.mHeight, this.mPaint);
  }
  
  private void drawProgress(Canvas paramCanvas) {
    this.mPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    paramCanvas.drawCircle(this.mWidth / 2.0F, this.mHeight / 2.0F, this.mRadius, this.mPaint);
    this.mPaint.setXfermode(null);
    RectF rectF = this.mProgressOval;
    float f = this.mProgress;
    paramCanvas.drawArc(rectF, -90.0F + f * 3.6F, 360.0F - f * 3.6F, true, this.mPaint);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet) {
    this.mMediumAnimTime = getContext().getResources().getInteger(17694721);
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ProgressImageView);
    try {
      this.mProgress = typedArray.getInteger(2, 0);
      this.mStrokeWidth = typedArray.getDimensionPixelOffset(4, 8);
      this.mRadius = typedArray.getDimensionPixelOffset(3, 0);
      this.mIsSquare = typedArray.getBoolean(0, false);
      this.mMaskColor = typedArray.getColor(1, Color.argb(180, 0, 0, 0));
      Paint paint = new Paint();
      this();
      this.mPaint = paint;
      paint.setColor(this.mMaskColor);
      this.mPaint.setAntiAlias(true);
      paint = new Paint();
      this();
      this.mShimmerPaint = paint;
      paint.setColor(-1);
      typedArray.recycle();
      return;
    } finally {
      typedArray.recycle();
    } 
  }
  
  private void initParams() {
    if (this.mWidth == 0)
      this.mWidth = getWidth(); 
    if (this.mHeight == 0)
      this.mHeight = getHeight(); 
    int i = this.mWidth;
    if (i != 0) {
      int j = this.mHeight;
      if (j != 0) {
        if (this.mRadius == 0.0F)
          this.mRadius = Math.min(i, j) / 4.0F; 
        if (this.mMaxMaskRadius == 0.0F) {
          i = this.mWidth;
          j = this.mHeight;
          this.mMaxMaskRadius = (float)(Math.sqrt((i * i + j * j)) * 0.5D);
        } 
        if (this.mProgressOval == null) {
          i = this.mWidth;
          float f1 = i / 2.0F;
          float f2 = this.mRadius;
          int k = this.mStrokeWidth;
          float f3 = k;
          j = this.mHeight;
          this.mProgressOval = new RectF(f1 - f2 + f3, j / 2.0F - f2 + k, i / 2.0F + f2 - k, j / 2.0F + f2 - k);
        } 
      } 
    } 
  }
  
  private void startInterAnim(final int progress) {
    ValueAnimator valueAnimator = this.mInterAnim;
    if (valueAnimator != null)
      valueAnimator.cancel(); 
    valueAnimator = ValueAnimator.ofFloat(new float[] { 0.0F, this.mStrokeWidth });
    this.mInterAnim = valueAnimator;
    valueAnimator.setInterpolator((TimeInterpolator)new DecelerateInterpolator());
    this.mInterAnim.setDuration(getContext().getResources().getInteger(17694720));
    this.mInterAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
            LauncherIconView.access$002(LauncherIconView.this, ((Float)param1ValueAnimator.getAnimatedValue()).floatValue());
            LauncherIconView.this.invalidate();
          }
        });
    this.mInterAnim.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
          public void onAnimationCancel(Animator param1Animator) {
            super.onAnimationCancel(param1Animator);
          }
          
          public void onAnimationEnd(Animator param1Animator) {
            super.onAnimationEnd(param1Animator);
            int i = progress;
            if (i > 0)
              LauncherIconView.this.startProgressAnim(0.0F, i); 
          }
          
          public void onAnimationStart(Animator param1Animator) {
            super.onAnimationStart(param1Animator);
          }
        });
    this.mInterAnim.start();
  }
  
  private void startMaskAnim() {
    ValueAnimator valueAnimator = this.mProgressAnimator;
    if (valueAnimator != null)
      valueAnimator.cancel(); 
    valueAnimator = ValueAnimator.ofFloat(new float[] { 0.0F, this.mMaxMaskRadius });
    valueAnimator.setInterpolator((TimeInterpolator)new DecelerateInterpolator());
    valueAnimator.setDuration(this.mMediumAnimTime);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
            LauncherIconView.access$402(LauncherIconView.this, true);
            LauncherIconView.access$502(LauncherIconView.this, ((Float)param1ValueAnimator.getAnimatedValue()).floatValue());
            LauncherIconView.this.invalidate();
          }
        });
    valueAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
          public void onAnimationCancel(Animator param1Animator) {
            super.onAnimationCancel(param1Animator);
            LauncherIconView.access$402(LauncherIconView.this, false);
          }
          
          public void onAnimationEnd(Animator param1Animator) {
            super.onAnimationEnd(param1Animator);
            LauncherIconView.access$402(LauncherIconView.this, false);
          }
          
          public void onAnimationStart(Animator param1Animator) {
            super.onAnimationStart(param1Animator);
            LauncherIconView.access$402(LauncherIconView.this, true);
          }
        });
    valueAnimator.start();
  }
  
  private void startProgressAnim(float paramFloat1, float paramFloat2) {
    final boolean isReverse;
    ValueAnimator valueAnimator = this.mProgressAnimator;
    if (valueAnimator != null)
      valueAnimator.cancel(); 
    if (paramFloat1 > paramFloat2) {
      bool = true;
    } else {
      bool = false;
    } 
    valueAnimator = ValueAnimator.ofFloat(new float[] { paramFloat1, paramFloat2 });
    this.mProgressAnimator = valueAnimator;
    valueAnimator.setInterpolator((TimeInterpolator)new DecelerateInterpolator());
    this.mProgressAnimator.setDuration(this.mMediumAnimTime);
    this.mProgressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
            LauncherIconView.access$202(LauncherIconView.this, ((Float)param1ValueAnimator.getAnimatedValue()).floatValue());
            if (0.0F < LauncherIconView.this.mProgress && LauncherIconView.this.mProgress < 100.0F) {
              LauncherIconView.this.invalidate();
            } else if (LauncherIconView.this.mProgress == 100.0F && !isReverse) {
              LauncherIconView.this.startMaskAnim();
            } 
          }
        });
    this.mProgressAnimator.start();
  }
  
  private void updateInterAnim(Canvas paramCanvas) {
    this.mPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    paramCanvas.drawCircle(this.mWidth / 2.0F, this.mHeight / 2.0F, this.mRadius, this.mPaint);
    this.mPaint.setXfermode(null);
    paramCanvas.drawCircle(this.mWidth / 2.0F, this.mHeight / 2.0F, this.mRadius - this.mInterDelta, this.mPaint);
  }
  
  private void updateMaskAnim(Canvas paramCanvas) {
    paramCanvas.drawRect(0.0F, 0.0F, this.mWidth, this.mHeight, this.mPaint);
    this.mPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    paramCanvas.drawCircle(this.mWidth / 2.0F, this.mHeight / 2.0F, this.mRadius + this.mMaskAnimDelta, this.mPaint);
    this.mPaint.setXfermode(null);
  }
  
  public float getGradientX() {
    return this.mShimmerViewHelper.getGradientX();
  }
  
  public int getMaskColor() {
    return this.mMaskColor;
  }
  
  public int getPrimaryColor() {
    return this.mShimmerViewHelper.getPrimaryColor();
  }
  
  public int getProgress() {
    return (int)this.mProgress;
  }
  
  public float getRadius() {
    return this.mRadius;
  }
  
  public int getReflectionColor() {
    return this.mShimmerViewHelper.getReflectionColor();
  }
  
  public int getStrokeWidth() {
    return this.mStrokeWidth;
  }
  
  public boolean isSetUp() {
    return this.mShimmerViewHelper.isSetUp();
  }
  
  public boolean isShimmering() {
    return this.mShimmerViewHelper.isShimmering();
  }
  
  protected void onDraw(Canvas paramCanvas) {
    ShimmerViewHelper shimmerViewHelper = this.mShimmerViewHelper;
    if (shimmerViewHelper != null)
      shimmerViewHelper.onDraw(); 
    super.onDraw(paramCanvas);
    int i = paramCanvas.saveLayer(0.0F, 0.0F, getWidth(), getHeight(), null, 31);
    initParams();
    if (this.mProgress < 100.0F) {
      drawMask(paramCanvas);
      if (this.mProgress == 0.0F) {
        updateInterAnim(paramCanvas);
      } else {
        drawProgress(paramCanvas);
      } 
    } 
    if (this.mMaskAnimRunning)
      updateMaskAnim(paramCanvas); 
    paramCanvas.restoreToCount(i);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    super.onMeasure(paramInt1, paramInt2);
    if (this.mIsSquare) {
      int i = View.MeasureSpec.getSize(paramInt1);
      paramInt1 = i;
      if (i == 0)
        paramInt1 = View.MeasureSpec.getSize(paramInt2); 
      setMeasuredDimension(paramInt1, paramInt1);
    } 
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    ShimmerViewHelper shimmerViewHelper = this.mShimmerViewHelper;
    if (shimmerViewHelper != null)
      shimmerViewHelper.onSizeChanged(); 
  }
  
  public void setAnimationSetupCallback(ShimmerViewHelper.AnimationSetupCallback paramAnimationSetupCallback) {
    this.mShimmerViewHelper.setAnimationSetupCallback(paramAnimationSetupCallback);
  }
  
  public void setGradientX(float paramFloat) {
    this.mShimmerViewHelper.setGradientX(paramFloat);
  }
  
  public void setMaskColor(int paramInt) {
    this.mMaskColor = paramInt;
    this.mPaint.setColor(paramInt);
    invalidate();
  }
  
  public void setPrimaryColor(int paramInt) {
    this.mShimmerViewHelper.setPrimaryColor(paramInt);
  }
  
  public void setProgress(int paramInt) {
    setProgress(paramInt, true);
  }
  
  public void setProgress(int paramInt, boolean paramBoolean) {
    paramInt = Math.min(Math.max(paramInt, 0), 100);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("setProgress: p:");
    stringBuilder.append(paramInt);
    stringBuilder.append(",mp:");
    stringBuilder.append(this.mProgress);
    Log.d("LauncherIconView", stringBuilder.toString());
    float f = paramInt;
    if (Math.abs(f - this.mProgress) > 5.0F && paramBoolean) {
      float f1 = this.mProgress;
      if (f1 == 0.0F) {
        startInterAnim(paramInt);
      } else {
        startProgressAnim(f1, f);
      } 
    } else if (paramInt == 100 && paramBoolean) {
      this.mProgress = 100.0F;
      startMaskAnim();
    } else {
      this.mProgress = f;
      if (f == 0.0F)
        this.mInterDelta = 0.0F; 
      invalidate();
    } 
  }
  
  public void setRadius(float paramFloat) {
    this.mRadius = paramFloat;
    this.mProgressOval = null;
    invalidate();
  }
  
  public void setReflectionColor(int paramInt) {
    this.mShimmerViewHelper.setReflectionColor(paramInt);
  }
  
  public void setShimmering(boolean paramBoolean) {
    this.mShimmerViewHelper.setShimmering(paramBoolean);
  }
  
  public void setStrokeWidth(int paramInt) {
    this.mStrokeWidth = paramInt;
    this.mProgressOval = null;
    invalidate();
  }
  
  public void startShimmer() {
    stopShimmer();
    Shimmer shimmer = new Shimmer();
    this.mShimmer = shimmer;
    shimmer.setRepeatCount(1).setStartDelay(800L).setDirection(0).start(this);
  }
  
  public void stopShimmer() {
    Shimmer shimmer = this.mShimmer;
    if (shimmer != null && shimmer.isAnimating()) {
      this.mShimmer.cancel();
      this.mShimmer = null;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\LauncherIconView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */