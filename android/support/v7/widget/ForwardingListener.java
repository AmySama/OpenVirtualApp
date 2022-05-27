package android.support.v7.widget;

import android.os.SystemClock;
import android.support.v7.view.menu.ShowableListMenu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;

public abstract class ForwardingListener implements View.OnTouchListener, View.OnAttachStateChangeListener {
  private int mActivePointerId;
  
  private Runnable mDisallowIntercept;
  
  private boolean mForwarding;
  
  private final int mLongPressTimeout;
  
  private final float mScaledTouchSlop;
  
  final View mSrc;
  
  private final int mTapTimeout;
  
  private final int[] mTmpLocation = new int[2];
  
  private Runnable mTriggerLongPress;
  
  public ForwardingListener(View paramView) {
    this.mSrc = paramView;
    paramView.setLongClickable(true);
    paramView.addOnAttachStateChangeListener(this);
    this.mScaledTouchSlop = ViewConfiguration.get(paramView.getContext()).getScaledTouchSlop();
    int i = ViewConfiguration.getTapTimeout();
    this.mTapTimeout = i;
    this.mLongPressTimeout = (i + ViewConfiguration.getLongPressTimeout()) / 2;
  }
  
  private void clearCallbacks() {
    Runnable runnable = this.mTriggerLongPress;
    if (runnable != null)
      this.mSrc.removeCallbacks(runnable); 
    runnable = this.mDisallowIntercept;
    if (runnable != null)
      this.mSrc.removeCallbacks(runnable); 
  }
  
  private boolean onTouchForwarded(MotionEvent paramMotionEvent) {
    View view = this.mSrc;
    ShowableListMenu showableListMenu = getPopup();
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (showableListMenu != null)
      if (!showableListMenu.isShowing()) {
        bool2 = bool1;
      } else {
        DropDownListView dropDownListView = (DropDownListView)showableListMenu.getListView();
        bool2 = bool1;
        if (dropDownListView != null)
          if (!dropDownListView.isShown()) {
            bool2 = bool1;
          } else {
            MotionEvent motionEvent = MotionEvent.obtainNoHistory(paramMotionEvent);
            toGlobalMotionEvent(view, motionEvent);
            toLocalMotionEvent((View)dropDownListView, motionEvent);
            boolean bool = dropDownListView.onForwardedEvent(motionEvent, this.mActivePointerId);
            motionEvent.recycle();
            int i = paramMotionEvent.getActionMasked();
            if (i != 1 && i != 3) {
              i = 1;
            } else {
              i = 0;
            } 
            bool2 = bool1;
            if (bool) {
              bool2 = bool1;
              if (i != 0)
                bool2 = true; 
            } 
          }  
      }  
    return bool2;
  }
  
  private boolean onTouchObserved(MotionEvent paramMotionEvent) {
    View view = this.mSrc;
    if (!view.isEnabled())
      return false; 
    int i = paramMotionEvent.getActionMasked();
    if (i != 0) {
      if (i != 1)
        if (i != 2) {
          if (i != 3)
            return false; 
        } else {
          i = paramMotionEvent.findPointerIndex(this.mActivePointerId);
          if (i >= 0 && !pointInView(view, paramMotionEvent.getX(i), paramMotionEvent.getY(i), this.mScaledTouchSlop)) {
            clearCallbacks();
            view.getParent().requestDisallowInterceptTouchEvent(true);
            return true;
          } 
          return false;
        }  
      clearCallbacks();
    } else {
      this.mActivePointerId = paramMotionEvent.getPointerId(0);
      if (this.mDisallowIntercept == null)
        this.mDisallowIntercept = new DisallowIntercept(); 
      view.postDelayed(this.mDisallowIntercept, this.mTapTimeout);
      if (this.mTriggerLongPress == null)
        this.mTriggerLongPress = new TriggerLongPress(); 
      view.postDelayed(this.mTriggerLongPress, this.mLongPressTimeout);
    } 
    return false;
  }
  
  private static boolean pointInView(View paramView, float paramFloat1, float paramFloat2, float paramFloat3) {
    boolean bool;
    float f = -paramFloat3;
    if (paramFloat1 >= f && paramFloat2 >= f && paramFloat1 < (paramView.getRight() - paramView.getLeft()) + paramFloat3 && paramFloat2 < (paramView.getBottom() - paramView.getTop()) + paramFloat3) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private boolean toGlobalMotionEvent(View paramView, MotionEvent paramMotionEvent) {
    int[] arrayOfInt = this.mTmpLocation;
    paramView.getLocationOnScreen(arrayOfInt);
    paramMotionEvent.offsetLocation(arrayOfInt[0], arrayOfInt[1]);
    return true;
  }
  
  private boolean toLocalMotionEvent(View paramView, MotionEvent paramMotionEvent) {
    int[] arrayOfInt = this.mTmpLocation;
    paramView.getLocationOnScreen(arrayOfInt);
    paramMotionEvent.offsetLocation(-arrayOfInt[0], -arrayOfInt[1]);
    return true;
  }
  
  public abstract ShowableListMenu getPopup();
  
  protected boolean onForwardingStarted() {
    ShowableListMenu showableListMenu = getPopup();
    if (showableListMenu != null && !showableListMenu.isShowing())
      showableListMenu.show(); 
    return true;
  }
  
  protected boolean onForwardingStopped() {
    ShowableListMenu showableListMenu = getPopup();
    if (showableListMenu != null && showableListMenu.isShowing())
      showableListMenu.dismiss(); 
    return true;
  }
  
  void onLongPress() {
    clearCallbacks();
    View view = this.mSrc;
    if (view.isEnabled() && !view.isLongClickable()) {
      if (!onForwardingStarted())
        return; 
      view.getParent().requestDisallowInterceptTouchEvent(true);
      long l = SystemClock.uptimeMillis();
      MotionEvent motionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
      view.onTouchEvent(motionEvent);
      motionEvent.recycle();
      this.mForwarding = true;
    } 
  }
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
    boolean bool2;
    boolean bool = this.mForwarding;
    boolean bool1 = true;
    if (bool) {
      if (onTouchForwarded(paramMotionEvent) || !onForwardingStopped()) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
    } else {
      boolean bool4;
      if (onTouchObserved(paramMotionEvent) && onForwardingStarted()) {
        bool4 = true;
      } else {
        bool4 = false;
      } 
      bool2 = bool4;
      if (bool4) {
        long l = SystemClock.uptimeMillis();
        MotionEvent motionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
        this.mSrc.onTouchEvent(motionEvent);
        motionEvent.recycle();
        bool2 = bool4;
      } 
    } 
    this.mForwarding = bool2;
    boolean bool3 = bool1;
    if (!bool2)
      if (bool) {
        bool3 = bool1;
      } else {
        bool3 = false;
      }  
    return bool3;
  }
  
  public void onViewAttachedToWindow(View paramView) {}
  
  public void onViewDetachedFromWindow(View paramView) {
    this.mForwarding = false;
    this.mActivePointerId = -1;
    Runnable runnable = this.mDisallowIntercept;
    if (runnable != null)
      this.mSrc.removeCallbacks(runnable); 
  }
  
  private class DisallowIntercept implements Runnable {
    public void run() {
      ViewParent viewParent = ForwardingListener.this.mSrc.getParent();
      if (viewParent != null)
        viewParent.requestDisallowInterceptTouchEvent(true); 
    }
  }
  
  private class TriggerLongPress implements Runnable {
    public void run() {
      ForwardingListener.this.onLongPress();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\ForwardingListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */