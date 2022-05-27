package android.support.v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;

public final class ViewPropertyAnimatorCompat {
  static final int LISTENER_TAG_ID = 2113929216;
  
  private static final String TAG = "ViewAnimatorCompat";
  
  Runnable mEndAction = null;
  
  int mOldLayerType = -1;
  
  Runnable mStartAction = null;
  
  private WeakReference<View> mView;
  
  ViewPropertyAnimatorCompat(View paramView) {
    this.mView = new WeakReference<View>(paramView);
  }
  
  private void setListenerInternal(final View view, final ViewPropertyAnimatorListener listener) {
    if (listener != null) {
      view.animate().setListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
            public void onAnimationCancel(Animator param1Animator) {
              listener.onAnimationCancel(view);
            }
            
            public void onAnimationEnd(Animator param1Animator) {
              listener.onAnimationEnd(view);
            }
            
            public void onAnimationStart(Animator param1Animator) {
              listener.onAnimationStart(view);
            }
          });
    } else {
      view.animate().setListener(null);
    } 
  }
  
  public ViewPropertyAnimatorCompat alpha(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().alpha(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat alphaBy(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().alphaBy(paramFloat); 
    return this;
  }
  
  public void cancel() {
    View view = this.mView.get();
    if (view != null)
      view.animate().cancel(); 
  }
  
  public long getDuration() {
    View view = this.mView.get();
    return (view != null) ? view.animate().getDuration() : 0L;
  }
  
  public Interpolator getInterpolator() {
    View view = this.mView.get();
    return (view != null && Build.VERSION.SDK_INT >= 18) ? (Interpolator)view.animate().getInterpolator() : null;
  }
  
  public long getStartDelay() {
    View view = this.mView.get();
    return (view != null) ? view.animate().getStartDelay() : 0L;
  }
  
  public ViewPropertyAnimatorCompat rotation(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().rotation(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat rotationBy(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().rotationBy(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat rotationX(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().rotationX(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat rotationXBy(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().rotationXBy(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat rotationY(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().rotationY(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat rotationYBy(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().rotationYBy(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat scaleX(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().scaleX(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat scaleXBy(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().scaleXBy(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat scaleY(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().scaleY(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat scaleYBy(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().scaleYBy(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat setDuration(long paramLong) {
    View view = this.mView.get();
    if (view != null)
      view.animate().setDuration(paramLong); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat setInterpolator(Interpolator paramInterpolator) {
    View view = this.mView.get();
    if (view != null)
      view.animate().setInterpolator((TimeInterpolator)paramInterpolator); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat setListener(ViewPropertyAnimatorListener paramViewPropertyAnimatorListener) {
    View view = this.mView.get();
    if (view != null)
      if (Build.VERSION.SDK_INT >= 16) {
        setListenerInternal(view, paramViewPropertyAnimatorListener);
      } else {
        view.setTag(2113929216, paramViewPropertyAnimatorListener);
        setListenerInternal(view, new ViewPropertyAnimatorListenerApi14(this));
      }  
    return this;
  }
  
  public ViewPropertyAnimatorCompat setStartDelay(long paramLong) {
    View view = this.mView.get();
    if (view != null)
      view.animate().setStartDelay(paramLong); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat setUpdateListener(final ViewPropertyAnimatorUpdateListener listener) {
    final View view = this.mView.get();
    if (view != null && Build.VERSION.SDK_INT >= 19) {
      ValueAnimator.AnimatorUpdateListener animatorUpdateListener = null;
      if (listener != null)
        animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
              listener.onAnimationUpdate(view);
            }
          }; 
      view.animate().setUpdateListener(animatorUpdateListener);
    } 
    return this;
  }
  
  public void start() {
    View view = this.mView.get();
    if (view != null)
      view.animate().start(); 
  }
  
  public ViewPropertyAnimatorCompat translationX(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().translationX(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat translationXBy(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().translationXBy(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat translationY(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().translationY(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat translationYBy(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().translationYBy(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat translationZ(float paramFloat) {
    View view = this.mView.get();
    if (view != null && Build.VERSION.SDK_INT >= 21)
      view.animate().translationZ(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat translationZBy(float paramFloat) {
    View view = this.mView.get();
    if (view != null && Build.VERSION.SDK_INT >= 21)
      view.animate().translationZBy(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat withEndAction(Runnable paramRunnable) {
    View view = this.mView.get();
    if (view != null)
      if (Build.VERSION.SDK_INT >= 16) {
        view.animate().withEndAction(paramRunnable);
      } else {
        setListenerInternal(view, new ViewPropertyAnimatorListenerApi14(this));
        this.mEndAction = paramRunnable;
      }  
    return this;
  }
  
  public ViewPropertyAnimatorCompat withLayer() {
    View view = this.mView.get();
    if (view != null)
      if (Build.VERSION.SDK_INT >= 16) {
        view.animate().withLayer();
      } else {
        this.mOldLayerType = view.getLayerType();
        setListenerInternal(view, new ViewPropertyAnimatorListenerApi14(this));
      }  
    return this;
  }
  
  public ViewPropertyAnimatorCompat withStartAction(Runnable paramRunnable) {
    View view = this.mView.get();
    if (view != null)
      if (Build.VERSION.SDK_INT >= 16) {
        view.animate().withStartAction(paramRunnable);
      } else {
        setListenerInternal(view, new ViewPropertyAnimatorListenerApi14(this));
        this.mStartAction = paramRunnable;
      }  
    return this;
  }
  
  public ViewPropertyAnimatorCompat x(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().x(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat xBy(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().xBy(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat y(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().y(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat yBy(float paramFloat) {
    View view = this.mView.get();
    if (view != null)
      view.animate().yBy(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat z(float paramFloat) {
    View view = this.mView.get();
    if (view != null && Build.VERSION.SDK_INT >= 21)
      view.animate().z(paramFloat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompat zBy(float paramFloat) {
    View view = this.mView.get();
    if (view != null && Build.VERSION.SDK_INT >= 21)
      view.animate().zBy(paramFloat); 
    return this;
  }
  
  static class ViewPropertyAnimatorListenerApi14 implements ViewPropertyAnimatorListener {
    boolean mAnimEndCalled;
    
    ViewPropertyAnimatorCompat mVpa;
    
    ViewPropertyAnimatorListenerApi14(ViewPropertyAnimatorCompat param1ViewPropertyAnimatorCompat) {
      this.mVpa = param1ViewPropertyAnimatorCompat;
    }
    
    public void onAnimationCancel(View param1View) {
      Object object = param1View.getTag(2113929216);
      if (object instanceof ViewPropertyAnimatorListener) {
        object = object;
      } else {
        object = null;
      } 
      if (object != null)
        object.onAnimationCancel(param1View); 
    }
    
    public void onAnimationEnd(View param1View) {
      int i = this.mVpa.mOldLayerType;
      ViewPropertyAnimatorListener viewPropertyAnimatorListener = null;
      if (i > -1) {
        param1View.setLayerType(this.mVpa.mOldLayerType, null);
        this.mVpa.mOldLayerType = -1;
      } 
      if (Build.VERSION.SDK_INT >= 16 || !this.mAnimEndCalled) {
        if (this.mVpa.mEndAction != null) {
          Runnable runnable = this.mVpa.mEndAction;
          this.mVpa.mEndAction = null;
          runnable.run();
        } 
        Object object = param1View.getTag(2113929216);
        if (object instanceof ViewPropertyAnimatorListener)
          viewPropertyAnimatorListener = (ViewPropertyAnimatorListener)object; 
        if (viewPropertyAnimatorListener != null)
          viewPropertyAnimatorListener.onAnimationEnd(param1View); 
        this.mAnimEndCalled = true;
      } 
    }
    
    public void onAnimationStart(View param1View) {
      this.mAnimEndCalled = false;
      int i = this.mVpa.mOldLayerType;
      ViewPropertyAnimatorListener viewPropertyAnimatorListener = null;
      if (i > -1)
        param1View.setLayerType(2, null); 
      if (this.mVpa.mStartAction != null) {
        Runnable runnable = this.mVpa.mStartAction;
        this.mVpa.mStartAction = null;
        runnable.run();
      } 
      Object object = param1View.getTag(2113929216);
      if (object instanceof ViewPropertyAnimatorListener)
        viewPropertyAnimatorListener = (ViewPropertyAnimatorListener)object; 
      if (viewPropertyAnimatorListener != null)
        viewPropertyAnimatorListener.onAnimationStart(param1View); 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\ViewPropertyAnimatorCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */