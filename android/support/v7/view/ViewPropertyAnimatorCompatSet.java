package android.support.v7.view;

import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.view.View;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.Iterator;

public class ViewPropertyAnimatorCompatSet {
  final ArrayList<ViewPropertyAnimatorCompat> mAnimators = new ArrayList<ViewPropertyAnimatorCompat>();
  
  private long mDuration = -1L;
  
  private Interpolator mInterpolator;
  
  private boolean mIsStarted;
  
  ViewPropertyAnimatorListener mListener;
  
  private final ViewPropertyAnimatorListenerAdapter mProxyListener = new ViewPropertyAnimatorListenerAdapter() {
      private int mProxyEndCount = 0;
      
      private boolean mProxyStarted = false;
      
      public void onAnimationEnd(View param1View) {
        int i = this.mProxyEndCount + 1;
        this.mProxyEndCount = i;
        if (i == ViewPropertyAnimatorCompatSet.this.mAnimators.size()) {
          if (ViewPropertyAnimatorCompatSet.this.mListener != null)
            ViewPropertyAnimatorCompatSet.this.mListener.onAnimationEnd(null); 
          onEnd();
        } 
      }
      
      public void onAnimationStart(View param1View) {
        if (this.mProxyStarted)
          return; 
        this.mProxyStarted = true;
        if (ViewPropertyAnimatorCompatSet.this.mListener != null)
          ViewPropertyAnimatorCompatSet.this.mListener.onAnimationStart(null); 
      }
      
      void onEnd() {
        this.mProxyEndCount = 0;
        this.mProxyStarted = false;
        ViewPropertyAnimatorCompatSet.this.onAnimationsEnded();
      }
    };
  
  public void cancel() {
    if (!this.mIsStarted)
      return; 
    Iterator<ViewPropertyAnimatorCompat> iterator = this.mAnimators.iterator();
    while (iterator.hasNext())
      ((ViewPropertyAnimatorCompat)iterator.next()).cancel(); 
    this.mIsStarted = false;
  }
  
  void onAnimationsEnded() {
    this.mIsStarted = false;
  }
  
  public ViewPropertyAnimatorCompatSet play(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat) {
    if (!this.mIsStarted)
      this.mAnimators.add(paramViewPropertyAnimatorCompat); 
    return this;
  }
  
  public ViewPropertyAnimatorCompatSet playSequentially(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat1, ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat2) {
    this.mAnimators.add(paramViewPropertyAnimatorCompat1);
    paramViewPropertyAnimatorCompat2.setStartDelay(paramViewPropertyAnimatorCompat1.getDuration());
    this.mAnimators.add(paramViewPropertyAnimatorCompat2);
    return this;
  }
  
  public ViewPropertyAnimatorCompatSet setDuration(long paramLong) {
    if (!this.mIsStarted)
      this.mDuration = paramLong; 
    return this;
  }
  
  public ViewPropertyAnimatorCompatSet setInterpolator(Interpolator paramInterpolator) {
    if (!this.mIsStarted)
      this.mInterpolator = paramInterpolator; 
    return this;
  }
  
  public ViewPropertyAnimatorCompatSet setListener(ViewPropertyAnimatorListener paramViewPropertyAnimatorListener) {
    if (!this.mIsStarted)
      this.mListener = paramViewPropertyAnimatorListener; 
    return this;
  }
  
  public void start() {
    if (this.mIsStarted)
      return; 
    for (ViewPropertyAnimatorCompat viewPropertyAnimatorCompat : this.mAnimators) {
      long l = this.mDuration;
      if (l >= 0L)
        viewPropertyAnimatorCompat.setDuration(l); 
      Interpolator interpolator = this.mInterpolator;
      if (interpolator != null)
        viewPropertyAnimatorCompat.setInterpolator(interpolator); 
      if (this.mListener != null)
        viewPropertyAnimatorCompat.setListener((ViewPropertyAnimatorListener)this.mProxyListener); 
      viewPropertyAnimatorCompat.start();
    } 
    this.mIsStarted = true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\ViewPropertyAnimatorCompatSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */