package io.virtualapp.widgets;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;

public class Shimmer {
  public static final int ANIMATION_DIRECTION_LTR = 0;
  
  public static final int ANIMATION_DIRECTION_RTL = 1;
  
  private static final int DEFAULT_DIRECTION = 0;
  
  private static final long DEFAULT_DURATION = 1000L;
  
  private static final int DEFAULT_REPEAT_COUNT = -1;
  
  private static final long DEFAULT_START_DELAY = 0L;
  
  private ObjectAnimator animator;
  
  private Animator.AnimatorListener animatorListener;
  
  private int direction = 0;
  
  private long duration = 1000L;
  
  private int repeatCount = -1;
  
  private long startDelay = 0L;
  
  public void cancel() {
    ObjectAnimator objectAnimator = this.animator;
    if (objectAnimator != null)
      objectAnimator.cancel(); 
  }
  
  public Animator.AnimatorListener getAnimatorListener() {
    return this.animatorListener;
  }
  
  public int getDirection() {
    return this.direction;
  }
  
  public long getDuration() {
    return this.duration;
  }
  
  public int getRepeatCount() {
    return this.repeatCount;
  }
  
  public long getStartDelay() {
    return this.startDelay;
  }
  
  public boolean isAnimating() {
    boolean bool;
    ObjectAnimator objectAnimator = this.animator;
    if (objectAnimator != null && objectAnimator.isRunning()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public Shimmer setAnimatorListener(Animator.AnimatorListener paramAnimatorListener) {
    this.animatorListener = paramAnimatorListener;
    return this;
  }
  
  public Shimmer setDirection(int paramInt) {
    if (paramInt == 0 || paramInt == 1) {
      this.direction = paramInt;
      return this;
    } 
    throw new IllegalArgumentException("The animation direction must be either ANIMATION_DIRECTION_LTR or ANIMATION_DIRECTION_RTL");
  }
  
  public Shimmer setDuration(long paramLong) {
    this.duration = paramLong;
    return this;
  }
  
  public Shimmer setRepeatCount(int paramInt) {
    this.repeatCount = paramInt;
    return this;
  }
  
  public Shimmer setStartDelay(long paramLong) {
    this.startDelay = paramLong;
    return this;
  }
  
  public <V extends View & ShimmerViewBase> void start(final V shimmerView) {
    if (isAnimating())
      return; 
    final Runnable animate = new Runnable() {
        public void run() {
          ((ShimmerViewBase)shimmerView).setShimmering(true);
          float f1 = shimmerView.getWidth();
          int i = Shimmer.this.direction;
          float f2 = 0.0F;
          if (i == 1) {
            f2 = shimmerView.getWidth();
            f1 = 0.0F;
          } 
          Shimmer.access$102(Shimmer.this, ObjectAnimator.ofFloat(shimmerView, "gradientX", new float[] { f2, f1 }));
          Shimmer.this.animator.setRepeatCount(Shimmer.this.repeatCount);
          Shimmer.this.animator.setDuration(Shimmer.this.duration);
          Shimmer.this.animator.setStartDelay(Shimmer.this.startDelay);
          Shimmer.this.animator.addListener(new Animator.AnimatorListener() {
                public void onAnimationCancel(Animator param2Animator) {}
                
                public void onAnimationEnd(Animator param2Animator) {
                  ((ShimmerViewBase)shimmerView).setShimmering(false);
                  if (Build.VERSION.SDK_INT < 16) {
                    shimmerView.postInvalidate();
                  } else {
                    shimmerView.postInvalidateOnAnimation();
                  } 
                  Shimmer.access$102(Shimmer.this, null);
                }
                
                public void onAnimationRepeat(Animator param2Animator) {}
                
                public void onAnimationStart(Animator param2Animator) {}
              });
          if (Shimmer.this.animatorListener != null)
            Shimmer.this.animator.addListener(Shimmer.this.animatorListener); 
          Shimmer.this.animator.start();
        }
      };
    ShimmerViewBase shimmerViewBase = (ShimmerViewBase)shimmerView;
    if (!shimmerViewBase.isSetUp()) {
      shimmerViewBase.setAnimationSetupCallback(new ShimmerViewHelper.AnimationSetupCallback() {
            public void onSetupAnimation(View param1View) {
              animate.run();
            }
          });
    } else {
      runnable.run();
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\Shimmer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */