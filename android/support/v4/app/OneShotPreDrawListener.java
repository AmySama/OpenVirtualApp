package android.support.v4.app;

import android.view.View;
import android.view.ViewTreeObserver;

class OneShotPreDrawListener implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
  private final Runnable mRunnable;
  
  private final View mView;
  
  private ViewTreeObserver mViewTreeObserver;
  
  private OneShotPreDrawListener(View paramView, Runnable paramRunnable) {
    this.mView = paramView;
    this.mViewTreeObserver = paramView.getViewTreeObserver();
    this.mRunnable = paramRunnable;
  }
  
  public static OneShotPreDrawListener add(View paramView, Runnable paramRunnable) {
    OneShotPreDrawListener oneShotPreDrawListener = new OneShotPreDrawListener(paramView, paramRunnable);
    paramView.getViewTreeObserver().addOnPreDrawListener(oneShotPreDrawListener);
    paramView.addOnAttachStateChangeListener(oneShotPreDrawListener);
    return oneShotPreDrawListener;
  }
  
  public boolean onPreDraw() {
    removeListener();
    this.mRunnable.run();
    return true;
  }
  
  public void onViewAttachedToWindow(View paramView) {
    this.mViewTreeObserver = paramView.getViewTreeObserver();
  }
  
  public void onViewDetachedFromWindow(View paramView) {
    removeListener();
  }
  
  public void removeListener() {
    if (this.mViewTreeObserver.isAlive()) {
      this.mViewTreeObserver.removeOnPreDrawListener(this);
    } else {
      this.mView.getViewTreeObserver().removeOnPreDrawListener(this);
    } 
    this.mView.removeOnAttachStateChangeListener(this);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\OneShotPreDrawListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */