package io.virtualapp.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

public class CircularAnim {
  public static final int MINI_RADIUS = 0;
  
  public static final long PERFECT_MILLS = 618L;
  
  private static Integer sColorOrImageRes;
  
  private static Long sFullActivityPerfectMills;
  
  private static Long sPerfectMills;
  
  public static FullActivityBuilder fullActivity(Activity paramActivity, View paramView) {
    return new FullActivityBuilder(paramActivity, paramView);
  }
  
  private static int getColorOrImageRes() {
    Integer integer = sColorOrImageRes;
    return (integer != null) ? integer.intValue() : 17170443;
  }
  
  private static long getFullActivityMills() {
    Long long_ = sFullActivityPerfectMills;
    return (long_ != null) ? long_.longValue() : 618L;
  }
  
  private static long getPerfectMills() {
    Long long_ = sPerfectMills;
    return (long_ != null) ? long_.longValue() : 618L;
  }
  
  public static VisibleBuilder hide(View paramView) {
    return new VisibleBuilder(paramView, false);
  }
  
  public static void init(long paramLong1, long paramLong2, int paramInt) {
    sPerfectMills = Long.valueOf(paramLong1);
    sFullActivityPerfectMills = Long.valueOf(paramLong2);
    sColorOrImageRes = Integer.valueOf(paramInt);
  }
  
  public static VisibleBuilder show(View paramView) {
    return new VisibleBuilder(paramView, true);
  }
  
  public static class FullActivityBuilder {
    private Activity mActivity;
    
    private int mColorOrImageRes = CircularAnim.getColorOrImageRes();
    
    private Long mDurationMills;
    
    private int mEnterAnim = 17432576;
    
    private int mExitAnim = 17432577;
    
    private CircularAnim.OnAnimationEndListener mOnAnimationEndListener;
    
    private float mStartRadius = 0.0F;
    
    private View mTriggerView;
    
    public FullActivityBuilder(Activity param1Activity, View param1View) {
      this.mActivity = param1Activity;
      this.mTriggerView = param1View;
    }
    
    private void doOnEnd() {
      this.mOnAnimationEndListener.onAnimationEnd();
    }
    
    public FullActivityBuilder colorOrImageRes(int param1Int) {
      this.mColorOrImageRes = param1Int;
      return this;
    }
    
    public FullActivityBuilder duration(long param1Long) {
      this.mDurationMills = Long.valueOf(param1Long);
      return this;
    }
    
    public void go(CircularAnim.OnAnimationEndListener param1OnAnimationEndListener) {
      this.mOnAnimationEndListener = param1OnAnimationEndListener;
      if (Build.VERSION.SDK_INT < 21) {
        doOnEnd();
        return;
      } 
      int[] arrayOfInt = new int[2];
      this.mTriggerView.getLocationInWindow(arrayOfInt);
      int i = arrayOfInt[0] + this.mTriggerView.getWidth() / 2;
      int j = arrayOfInt[1] + this.mTriggerView.getHeight() / 2;
      ImageView imageView = new ImageView((Context)this.mActivity);
      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      imageView.setImageResource(this.mColorOrImageRes);
      ViewGroup viewGroup = (ViewGroup)this.mActivity.getWindow().getDecorView();
      int k = viewGroup.getWidth();
      int m = viewGroup.getHeight();
      viewGroup.addView((View)imageView, k, m);
      int n = Math.max(i, k - i);
      int i1 = Math.max(j, m - j);
      n = (int)Math.sqrt((n * n + i1 * i1)) + 1;
      try {
        Animator animator = ViewAnimationUtils.createCircularReveal((View)imageView, i, j, this.mStartRadius, n);
        m = (int)Math.sqrt((k * k + m * m));
        if (this.mDurationMills == null) {
          double d = n * 1.0D / (m + 1);
          this.mDurationMills = Long.valueOf((long)(CircularAnim.getFullActivityMills() * Math.sqrt(d)));
        } 
        long l = this.mDurationMills.longValue();
        animator.setDuration((long)(l * 0.9D));
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator param2Animator) {
              super.onAnimationEnd(param2Animator);
              CircularAnim.FullActivityBuilder.this.doOnEnd();
              CircularAnim.FullActivityBuilder.this.mActivity.overridePendingTransition(CircularAnim.FullActivityBuilder.this.mEnterAnim, CircularAnim.FullActivityBuilder.this.mExitAnim);
              CircularAnim.FullActivityBuilder.this.mTriggerView.postDelayed(new Runnable() {
                    public void run() {
                      if (CircularAnim.FullActivityBuilder.this.mActivity.isFinishing())
                        return; 
                      try {
                        Animator animator = ViewAnimationUtils.createCircularReveal((View)view, cx, cy, finalRadius, CircularAnim.FullActivityBuilder.this.mStartRadius);
                        animator.setDuration(finalDuration);
                        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
                            public void onAnimationEnd(Animator param4Animator) {
                              super.onAnimationEnd(param4Animator);
                              try {
                                decorView.removeView((View)view);
                              } catch (Exception exception) {
                                exception.printStackTrace();
                              } 
                            }
                          };
                        super(this);
                        animator.addListener((Animator.AnimatorListener)animatorListenerAdapter);
                        animator.start();
                      } catch (Exception exception) {
                        exception.printStackTrace();
                        try {
                          decorView.removeView((View)view);
                        } catch (Exception exception1) {
                          exception1.printStackTrace();
                        } 
                      } 
                    }
                  }1000L);
            }
          };
        super(this, imageView, i, j, n, l, viewGroup);
        animator.addListener((Animator.AnimatorListener)animatorListenerAdapter);
        animator.start();
      } catch (Exception exception) {
        exception.printStackTrace();
        doOnEnd();
      } 
    }
    
    public FullActivityBuilder overridePendingTransition(int param1Int1, int param1Int2) {
      this.mEnterAnim = param1Int1;
      this.mExitAnim = param1Int2;
      return this;
    }
    
    public FullActivityBuilder startRadius(float param1Float) {
      this.mStartRadius = param1Float;
      return this;
    }
  }
  
  class null extends AnimatorListenerAdapter {
    public void onAnimationEnd(Animator param1Animator) {
      super.onAnimationEnd(param1Animator);
      this.this$0.doOnEnd();
      this.this$0.mActivity.overridePendingTransition(this.this$0.mEnterAnim, this.this$0.mExitAnim);
      this.this$0.mTriggerView.postDelayed(new Runnable() {
            public void run() {
              if (this.this$1.this$0.mActivity.isFinishing())
                return; 
              try {
                Animator animator = ViewAnimationUtils.createCircularReveal((View)view, cx, cy, finalRadius, this.this$1.this$0.mStartRadius);
                animator.setDuration(finalDuration);
                AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator param4Animator) {
                      super.onAnimationEnd(param4Animator);
                      try {
                        decorView.removeView((View)view);
                      } catch (Exception exception) {
                        exception.printStackTrace();
                      } 
                    }
                  };
                super(this);
                animator.addListener((Animator.AnimatorListener)animatorListenerAdapter);
                animator.start();
              } catch (Exception exception) {
                exception.printStackTrace();
                try {
                  decorView.removeView((View)view);
                } catch (Exception exception1) {
                  exception1.printStackTrace();
                } 
              } 
            }
          }1000L);
    }
  }
  
  class null implements Runnable {
    public void run() {
      if (this.this$1.this$0.mActivity.isFinishing())
        return; 
      try {
        Animator animator = ViewAnimationUtils.createCircularReveal((View)view, cx, cy, finalRadius, this.this$1.this$0.mStartRadius);
        animator.setDuration(finalDuration);
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator param4Animator) {
              super.onAnimationEnd(param4Animator);
              try {
                decorView.removeView((View)view);
              } catch (Exception exception) {
                exception.printStackTrace();
              } 
            }
          };
        super(this);
        animator.addListener((Animator.AnimatorListener)animatorListenerAdapter);
        animator.start();
      } catch (Exception exception) {
        exception.printStackTrace();
        try {
          decorView.removeView((View)view);
        } catch (Exception exception1) {
          exception1.printStackTrace();
        } 
      } 
    }
  }
  
  class null extends AnimatorListenerAdapter {
    public void onAnimationEnd(Animator param1Animator) {
      super.onAnimationEnd(param1Animator);
      try {
        decorView.removeView((View)view);
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
    }
  }
  
  public static interface OnAnimationEndListener {
    void onAnimationEnd();
  }
  
  public static class VisibleBuilder {
    private boolean isShow;
    
    private View mAnimView;
    
    private long mDurationMills = CircularAnim.getPerfectMills();
    
    private Float mEndRadius;
    
    private CircularAnim.OnAnimationEndListener mOnAnimationEndListener;
    
    private Float mStartRadius;
    
    private View mTriggerView;
    
    public VisibleBuilder(View param1View, boolean param1Boolean) {
      this.mAnimView = param1View;
      this.isShow = param1Boolean;
      Float float_ = Float.valueOf(0.0F);
      if (param1Boolean) {
        this.mStartRadius = float_;
      } else {
        this.mEndRadius = float_;
      } 
    }
    
    private void doOnEnd() {
      if (this.isShow) {
        this.mAnimView.setVisibility(0);
      } else {
        this.mAnimView.setVisibility(4);
      } 
      CircularAnim.OnAnimationEndListener onAnimationEndListener = this.mOnAnimationEndListener;
      if (onAnimationEndListener != null)
        onAnimationEndListener.onAnimationEnd(); 
    }
    
    public VisibleBuilder duration(long param1Long) {
      this.mDurationMills = param1Long;
      return this;
    }
    
    public VisibleBuilder endRadius(float param1Float) {
      this.mEndRadius = Float.valueOf(param1Float);
      return this;
    }
    
    public void go() {
      go(null);
    }
    
    public void go(CircularAnim.OnAnimationEndListener param1OnAnimationEndListener) {
      int j;
      int k;
      double d;
      this.mOnAnimationEndListener = param1OnAnimationEndListener;
      if (Build.VERSION.SDK_INT < 21) {
        doOnEnd();
        return;
      } 
      View view = this.mTriggerView;
      if (view != null) {
        int[] arrayOfInt2 = new int[2];
        view.getLocationInWindow(arrayOfInt2);
        int m = arrayOfInt2[0];
        int n = this.mTriggerView.getWidth() / 2;
        int i1 = arrayOfInt2[1];
        int i2 = this.mTriggerView.getHeight() / 2;
        int[] arrayOfInt1 = new int[2];
        this.mAnimView.getLocationInWindow(arrayOfInt1);
        j = arrayOfInt1[0];
        k = arrayOfInt1[1];
        n = Math.min(Math.max(j, m + n), this.mAnimView.getWidth() + j);
        m = Math.min(Math.max(k, i1 + i2), this.mAnimView.getHeight() + k);
        i1 = this.mAnimView.getWidth();
        i2 = this.mAnimView.getHeight();
        j = n - j;
        k = m - k;
        n = Math.max(j, i1 - j);
        i2 = Math.max(k, i2 - k);
        d = Math.sqrt((n * n + i2 * i2));
      } else {
        j = (this.mAnimView.getLeft() + this.mAnimView.getRight()) / 2;
        k = (this.mAnimView.getTop() + this.mAnimView.getBottom()) / 2;
        int m = this.mAnimView.getWidth();
        int n = this.mAnimView.getHeight();
        d = Math.sqrt((m * m + n * n));
      } 
      int i = (int)d + 1;
      if (this.isShow && this.mEndRadius == null) {
        this.mEndRadius = Float.valueOf(i + 0.0F);
      } else if (!this.isShow && this.mStartRadius == null) {
        this.mStartRadius = Float.valueOf(i + 0.0F);
      } 
      try {
        Animator animator = ViewAnimationUtils.createCircularReveal(this.mAnimView, j, k, this.mStartRadius.floatValue(), this.mEndRadius.floatValue());
        this.mAnimView.setVisibility(0);
        animator.setDuration(this.mDurationMills);
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator param2Animator) {
              super.onAnimationEnd(param2Animator);
              CircularAnim.VisibleBuilder.this.doOnEnd();
            }
          };
        super(this);
        animator.addListener((Animator.AnimatorListener)animatorListenerAdapter);
        animator.start();
      } catch (Exception exception) {
        exception.printStackTrace();
        doOnEnd();
      } 
    }
    
    @Deprecated
    public VisibleBuilder onAnimationEndListener(CircularAnim.OnAnimationEndListener param1OnAnimationEndListener) {
      this.mOnAnimationEndListener = param1OnAnimationEndListener;
      return this;
    }
    
    public VisibleBuilder startRadius(float param1Float) {
      this.mStartRadius = Float.valueOf(param1Float);
      return this;
    }
    
    public VisibleBuilder triggerView(View param1View) {
      this.mTriggerView = param1View;
      return this;
    }
  }
  
  class null extends AnimatorListenerAdapter {
    public void onAnimationEnd(Animator param1Animator) {
      super.onAnimationEnd(param1Animator);
      this.this$0.doOnEnd();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\CircularAnim.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */