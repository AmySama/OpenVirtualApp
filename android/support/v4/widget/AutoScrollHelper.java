package android.support.v4.widget;

import android.content.res.Resources;
import android.os.SystemClock;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

public abstract class AutoScrollHelper implements View.OnTouchListener {
  private static final int DEFAULT_ACTIVATION_DELAY = ViewConfiguration.getTapTimeout();
  
  private static final int DEFAULT_EDGE_TYPE = 1;
  
  private static final float DEFAULT_MAXIMUM_EDGE = 3.4028235E38F;
  
  private static final int DEFAULT_MAXIMUM_VELOCITY_DIPS = 1575;
  
  private static final int DEFAULT_MINIMUM_VELOCITY_DIPS = 315;
  
  private static final int DEFAULT_RAMP_DOWN_DURATION = 500;
  
  private static final int DEFAULT_RAMP_UP_DURATION = 500;
  
  private static final float DEFAULT_RELATIVE_EDGE = 0.2F;
  
  private static final float DEFAULT_RELATIVE_VELOCITY = 1.0F;
  
  public static final int EDGE_TYPE_INSIDE = 0;
  
  public static final int EDGE_TYPE_INSIDE_EXTEND = 1;
  
  public static final int EDGE_TYPE_OUTSIDE = 2;
  
  private static final int HORIZONTAL = 0;
  
  public static final float NO_MAX = 3.4028235E38F;
  
  public static final float NO_MIN = 0.0F;
  
  public static final float RELATIVE_UNSPECIFIED = 0.0F;
  
  private static final int VERTICAL = 1;
  
  private int mActivationDelay;
  
  private boolean mAlreadyDelayed;
  
  boolean mAnimating;
  
  private final Interpolator mEdgeInterpolator = (Interpolator)new AccelerateInterpolator();
  
  private int mEdgeType;
  
  private boolean mEnabled;
  
  private boolean mExclusive;
  
  private float[] mMaximumEdges = new float[] { Float.MAX_VALUE, Float.MAX_VALUE };
  
  private float[] mMaximumVelocity = new float[] { Float.MAX_VALUE, Float.MAX_VALUE };
  
  private float[] mMinimumVelocity = new float[] { 0.0F, 0.0F };
  
  boolean mNeedsCancel;
  
  boolean mNeedsReset;
  
  private float[] mRelativeEdges = new float[] { 0.0F, 0.0F };
  
  private float[] mRelativeVelocity = new float[] { 0.0F, 0.0F };
  
  private Runnable mRunnable;
  
  final ClampedScroller mScroller = new ClampedScroller();
  
  final View mTarget;
  
  public AutoScrollHelper(View paramView) {
    this.mTarget = paramView;
    DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
    int i = (int)(displayMetrics.density * 1575.0F + 0.5F);
    int j = (int)(displayMetrics.density * 315.0F + 0.5F);
    float f = i;
    setMaximumVelocity(f, f);
    f = j;
    setMinimumVelocity(f, f);
    setEdgeType(1);
    setMaximumEdges(Float.MAX_VALUE, Float.MAX_VALUE);
    setRelativeEdges(0.2F, 0.2F);
    setRelativeVelocity(1.0F, 1.0F);
    setActivationDelay(DEFAULT_ACTIVATION_DELAY);
    setRampUpDuration(500);
    setRampDownDuration(500);
  }
  
  private float computeTargetVelocity(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3) {
    paramFloat1 = getEdgeValue(this.mRelativeEdges[paramInt], paramFloat2, this.mMaximumEdges[paramInt], paramFloat1);
    int i = paramFloat1 cmp 0.0F;
    if (i == 0)
      return 0.0F; 
    float f1 = this.mRelativeVelocity[paramInt];
    paramFloat2 = this.mMinimumVelocity[paramInt];
    float f2 = this.mMaximumVelocity[paramInt];
    paramFloat3 = f1 * paramFloat3;
    return (i > 0) ? constrain(paramFloat1 * paramFloat3, paramFloat2, f2) : -constrain(-paramFloat1 * paramFloat3, paramFloat2, f2);
  }
  
  static float constrain(float paramFloat1, float paramFloat2, float paramFloat3) {
    return (paramFloat1 > paramFloat3) ? paramFloat3 : ((paramFloat1 < paramFloat2) ? paramFloat2 : paramFloat1);
  }
  
  static int constrain(int paramInt1, int paramInt2, int paramInt3) {
    return (paramInt1 > paramInt3) ? paramInt3 : ((paramInt1 < paramInt2) ? paramInt2 : paramInt1);
  }
  
  private float constrainEdgeValue(float paramFloat1, float paramFloat2) {
    if (paramFloat2 == 0.0F)
      return 0.0F; 
    int i = this.mEdgeType;
    if (i != 0 && i != 1) {
      if (i == 2 && paramFloat1 < 0.0F)
        return paramFloat1 / -paramFloat2; 
    } else if (paramFloat1 < paramFloat2) {
      if (paramFloat1 >= 0.0F)
        return 1.0F - paramFloat1 / paramFloat2; 
      if (this.mAnimating && this.mEdgeType == 1)
        return 1.0F; 
    } 
    return 0.0F;
  }
  
  private float getEdgeValue(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    paramFloat3 = constrain(paramFloat1 * paramFloat2, 0.0F, paramFloat3);
    paramFloat1 = constrainEdgeValue(paramFloat4, paramFloat3);
    paramFloat1 = constrainEdgeValue(paramFloat2 - paramFloat4, paramFloat3) - paramFloat1;
    if (paramFloat1 < 0.0F) {
      paramFloat1 = -this.mEdgeInterpolator.getInterpolation(-paramFloat1);
    } else {
      if (paramFloat1 > 0.0F) {
        paramFloat1 = this.mEdgeInterpolator.getInterpolation(paramFloat1);
        return constrain(paramFloat1, -1.0F, 1.0F);
      } 
      return 0.0F;
    } 
    return constrain(paramFloat1, -1.0F, 1.0F);
  }
  
  private void requestStop() {
    if (this.mNeedsReset) {
      this.mAnimating = false;
    } else {
      this.mScroller.requestStop();
    } 
  }
  
  private void startAnimating() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mRunnable : Ljava/lang/Runnable;
    //   4: ifnonnull -> 19
    //   7: aload_0
    //   8: new android/support/v4/widget/AutoScrollHelper$ScrollAnimationRunnable
    //   11: dup
    //   12: aload_0
    //   13: invokespecial <init> : (Landroid/support/v4/widget/AutoScrollHelper;)V
    //   16: putfield mRunnable : Ljava/lang/Runnable;
    //   19: aload_0
    //   20: iconst_1
    //   21: putfield mAnimating : Z
    //   24: aload_0
    //   25: iconst_1
    //   26: putfield mNeedsReset : Z
    //   29: aload_0
    //   30: getfield mAlreadyDelayed : Z
    //   33: ifne -> 61
    //   36: aload_0
    //   37: getfield mActivationDelay : I
    //   40: istore_1
    //   41: iload_1
    //   42: ifle -> 61
    //   45: aload_0
    //   46: getfield mTarget : Landroid/view/View;
    //   49: aload_0
    //   50: getfield mRunnable : Ljava/lang/Runnable;
    //   53: iload_1
    //   54: i2l
    //   55: invokestatic postOnAnimationDelayed : (Landroid/view/View;Ljava/lang/Runnable;J)V
    //   58: goto -> 70
    //   61: aload_0
    //   62: getfield mRunnable : Ljava/lang/Runnable;
    //   65: invokeinterface run : ()V
    //   70: aload_0
    //   71: iconst_1
    //   72: putfield mAlreadyDelayed : Z
    //   75: return
  }
  
  public abstract boolean canTargetScrollHorizontally(int paramInt);
  
  public abstract boolean canTargetScrollVertically(int paramInt);
  
  void cancelTargetTouch() {
    long l = SystemClock.uptimeMillis();
    MotionEvent motionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
    this.mTarget.onTouchEvent(motionEvent);
    motionEvent.recycle();
  }
  
  public boolean isEnabled() {
    return this.mEnabled;
  }
  
  public boolean isExclusive() {
    return this.mExclusive;
  }
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mEnabled : Z
    //   4: istore_3
    //   5: iconst_0
    //   6: istore #4
    //   8: iload_3
    //   9: ifne -> 14
    //   12: iconst_0
    //   13: ireturn
    //   14: aload_2
    //   15: invokevirtual getActionMasked : ()I
    //   18: istore #5
    //   20: iload #5
    //   22: ifeq -> 53
    //   25: iload #5
    //   27: iconst_1
    //   28: if_icmpeq -> 46
    //   31: iload #5
    //   33: iconst_2
    //   34: if_icmpeq -> 63
    //   37: iload #5
    //   39: iconst_3
    //   40: if_icmpeq -> 46
    //   43: goto -> 140
    //   46: aload_0
    //   47: invokespecial requestStop : ()V
    //   50: goto -> 140
    //   53: aload_0
    //   54: iconst_1
    //   55: putfield mNeedsCancel : Z
    //   58: aload_0
    //   59: iconst_0
    //   60: putfield mAlreadyDelayed : Z
    //   63: aload_0
    //   64: iconst_0
    //   65: aload_2
    //   66: invokevirtual getX : ()F
    //   69: aload_1
    //   70: invokevirtual getWidth : ()I
    //   73: i2f
    //   74: aload_0
    //   75: getfield mTarget : Landroid/view/View;
    //   78: invokevirtual getWidth : ()I
    //   81: i2f
    //   82: invokespecial computeTargetVelocity : (IFFF)F
    //   85: fstore #6
    //   87: aload_0
    //   88: iconst_1
    //   89: aload_2
    //   90: invokevirtual getY : ()F
    //   93: aload_1
    //   94: invokevirtual getHeight : ()I
    //   97: i2f
    //   98: aload_0
    //   99: getfield mTarget : Landroid/view/View;
    //   102: invokevirtual getHeight : ()I
    //   105: i2f
    //   106: invokespecial computeTargetVelocity : (IFFF)F
    //   109: fstore #7
    //   111: aload_0
    //   112: getfield mScroller : Landroid/support/v4/widget/AutoScrollHelper$ClampedScroller;
    //   115: fload #6
    //   117: fload #7
    //   119: invokevirtual setTargetVelocity : (FF)V
    //   122: aload_0
    //   123: getfield mAnimating : Z
    //   126: ifne -> 140
    //   129: aload_0
    //   130: invokevirtual shouldAnimate : ()Z
    //   133: ifeq -> 140
    //   136: aload_0
    //   137: invokespecial startAnimating : ()V
    //   140: iload #4
    //   142: istore_3
    //   143: aload_0
    //   144: getfield mExclusive : Z
    //   147: ifeq -> 162
    //   150: iload #4
    //   152: istore_3
    //   153: aload_0
    //   154: getfield mAnimating : Z
    //   157: ifeq -> 162
    //   160: iconst_1
    //   161: istore_3
    //   162: iload_3
    //   163: ireturn
  }
  
  public abstract void scrollTargetBy(int paramInt1, int paramInt2);
  
  public AutoScrollHelper setActivationDelay(int paramInt) {
    this.mActivationDelay = paramInt;
    return this;
  }
  
  public AutoScrollHelper setEdgeType(int paramInt) {
    this.mEdgeType = paramInt;
    return this;
  }
  
  public AutoScrollHelper setEnabled(boolean paramBoolean) {
    if (this.mEnabled && !paramBoolean)
      requestStop(); 
    this.mEnabled = paramBoolean;
    return this;
  }
  
  public AutoScrollHelper setExclusive(boolean paramBoolean) {
    this.mExclusive = paramBoolean;
    return this;
  }
  
  public AutoScrollHelper setMaximumEdges(float paramFloat1, float paramFloat2) {
    float[] arrayOfFloat = this.mMaximumEdges;
    arrayOfFloat[0] = paramFloat1;
    arrayOfFloat[1] = paramFloat2;
    return this;
  }
  
  public AutoScrollHelper setMaximumVelocity(float paramFloat1, float paramFloat2) {
    float[] arrayOfFloat = this.mMaximumVelocity;
    arrayOfFloat[0] = paramFloat1 / 1000.0F;
    arrayOfFloat[1] = paramFloat2 / 1000.0F;
    return this;
  }
  
  public AutoScrollHelper setMinimumVelocity(float paramFloat1, float paramFloat2) {
    float[] arrayOfFloat = this.mMinimumVelocity;
    arrayOfFloat[0] = paramFloat1 / 1000.0F;
    arrayOfFloat[1] = paramFloat2 / 1000.0F;
    return this;
  }
  
  public AutoScrollHelper setRampDownDuration(int paramInt) {
    this.mScroller.setRampDownDuration(paramInt);
    return this;
  }
  
  public AutoScrollHelper setRampUpDuration(int paramInt) {
    this.mScroller.setRampUpDuration(paramInt);
    return this;
  }
  
  public AutoScrollHelper setRelativeEdges(float paramFloat1, float paramFloat2) {
    float[] arrayOfFloat = this.mRelativeEdges;
    arrayOfFloat[0] = paramFloat1;
    arrayOfFloat[1] = paramFloat2;
    return this;
  }
  
  public AutoScrollHelper setRelativeVelocity(float paramFloat1, float paramFloat2) {
    float[] arrayOfFloat = this.mRelativeVelocity;
    arrayOfFloat[0] = paramFloat1 / 1000.0F;
    arrayOfFloat[1] = paramFloat2 / 1000.0F;
    return this;
  }
  
  boolean shouldAnimate() {
    boolean bool;
    ClampedScroller clampedScroller = this.mScroller;
    int i = clampedScroller.getVerticalDirection();
    int j = clampedScroller.getHorizontalDirection();
    if ((i != 0 && canTargetScrollVertically(i)) || (j != 0 && canTargetScrollHorizontally(j))) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private static class ClampedScroller {
    private long mDeltaTime = 0L;
    
    private int mDeltaX = 0;
    
    private int mDeltaY = 0;
    
    private int mEffectiveRampDown;
    
    private int mRampDownDuration;
    
    private int mRampUpDuration;
    
    private long mStartTime = Long.MIN_VALUE;
    
    private long mStopTime = -1L;
    
    private float mStopValue;
    
    private float mTargetVelocityX;
    
    private float mTargetVelocityY;
    
    private float getValueAt(long param1Long) {
      if (param1Long < this.mStartTime)
        return 0.0F; 
      long l = this.mStopTime;
      if (l < 0L || param1Long < l)
        return AutoScrollHelper.constrain((float)(param1Long - this.mStartTime) / this.mRampUpDuration, 0.0F, 1.0F) * 0.5F; 
      float f = this.mStopValue;
      return 1.0F - f + f * AutoScrollHelper.constrain((float)(param1Long - l) / this.mEffectiveRampDown, 0.0F, 1.0F);
    }
    
    private float interpolateValue(float param1Float) {
      return -4.0F * param1Float * param1Float + param1Float * 4.0F;
    }
    
    public void computeScrollDelta() {
      if (this.mDeltaTime != 0L) {
        long l1 = AnimationUtils.currentAnimationTimeMillis();
        float f = interpolateValue(getValueAt(l1));
        long l2 = this.mDeltaTime;
        this.mDeltaTime = l1;
        f = (float)(l1 - l2) * f;
        this.mDeltaX = (int)(this.mTargetVelocityX * f);
        this.mDeltaY = (int)(f * this.mTargetVelocityY);
        return;
      } 
      throw new RuntimeException("Cannot compute scroll delta before calling start()");
    }
    
    public int getDeltaX() {
      return this.mDeltaX;
    }
    
    public int getDeltaY() {
      return this.mDeltaY;
    }
    
    public int getHorizontalDirection() {
      float f = this.mTargetVelocityX;
      return (int)(f / Math.abs(f));
    }
    
    public int getVerticalDirection() {
      float f = this.mTargetVelocityY;
      return (int)(f / Math.abs(f));
    }
    
    public boolean isFinished() {
      boolean bool;
      if (this.mStopTime > 0L && AnimationUtils.currentAnimationTimeMillis() > this.mStopTime + this.mEffectiveRampDown) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void requestStop() {
      long l = AnimationUtils.currentAnimationTimeMillis();
      this.mEffectiveRampDown = AutoScrollHelper.constrain((int)(l - this.mStartTime), 0, this.mRampDownDuration);
      this.mStopValue = getValueAt(l);
      this.mStopTime = l;
    }
    
    public void setRampDownDuration(int param1Int) {
      this.mRampDownDuration = param1Int;
    }
    
    public void setRampUpDuration(int param1Int) {
      this.mRampUpDuration = param1Int;
    }
    
    public void setTargetVelocity(float param1Float1, float param1Float2) {
      this.mTargetVelocityX = param1Float1;
      this.mTargetVelocityY = param1Float2;
    }
    
    public void start() {
      long l = AnimationUtils.currentAnimationTimeMillis();
      this.mStartTime = l;
      this.mStopTime = -1L;
      this.mDeltaTime = l;
      this.mStopValue = 0.5F;
      this.mDeltaX = 0;
      this.mDeltaY = 0;
    }
  }
  
  private class ScrollAnimationRunnable implements Runnable {
    public void run() {
      if (!AutoScrollHelper.this.mAnimating)
        return; 
      if (AutoScrollHelper.this.mNeedsReset) {
        AutoScrollHelper.this.mNeedsReset = false;
        AutoScrollHelper.this.mScroller.start();
      } 
      AutoScrollHelper.ClampedScroller clampedScroller = AutoScrollHelper.this.mScroller;
      if (clampedScroller.isFinished() || !AutoScrollHelper.this.shouldAnimate()) {
        AutoScrollHelper.this.mAnimating = false;
        return;
      } 
      if (AutoScrollHelper.this.mNeedsCancel) {
        AutoScrollHelper.this.mNeedsCancel = false;
        AutoScrollHelper.this.cancelTargetTouch();
      } 
      clampedScroller.computeScrollDelta();
      int i = clampedScroller.getDeltaX();
      int j = clampedScroller.getDeltaY();
      AutoScrollHelper.this.scrollTargetBy(i, j);
      ViewCompat.postOnAnimation(AutoScrollHelper.this.mTarget, this);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\AutoScrollHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */