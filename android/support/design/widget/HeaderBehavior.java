package android.support.design.widget;

import android.content.Context;
import android.support.v4.math.MathUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

abstract class HeaderBehavior<V extends View> extends ViewOffsetBehavior<V> {
  private static final int INVALID_POINTER = -1;
  
  private int mActivePointerId = -1;
  
  private Runnable mFlingRunnable;
  
  private boolean mIsBeingDragged;
  
  private int mLastMotionY;
  
  OverScroller mScroller;
  
  private int mTouchSlop = -1;
  
  private VelocityTracker mVelocityTracker;
  
  public HeaderBehavior() {}
  
  public HeaderBehavior(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  private void ensureVelocityTracker() {
    if (this.mVelocityTracker == null)
      this.mVelocityTracker = VelocityTracker.obtain(); 
  }
  
  boolean canDragView(V paramV) {
    return false;
  }
  
  final boolean fling(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt1, int paramInt2, float paramFloat) {
    FlingRunnable flingRunnable;
    Runnable runnable = this.mFlingRunnable;
    if (runnable != null) {
      paramV.removeCallbacks(runnable);
      this.mFlingRunnable = null;
    } 
    if (this.mScroller == null)
      this.mScroller = new OverScroller(paramV.getContext()); 
    this.mScroller.fling(0, getTopAndBottomOffset(), 0, Math.round(paramFloat), 0, 0, paramInt1, paramInt2);
    if (this.mScroller.computeScrollOffset()) {
      flingRunnable = new FlingRunnable(paramCoordinatorLayout, paramV);
      this.mFlingRunnable = flingRunnable;
      ViewCompat.postOnAnimation((View)paramV, flingRunnable);
      return true;
    } 
    onFlingFinished((CoordinatorLayout)flingRunnable, paramV);
    return false;
  }
  
  int getMaxDragOffset(V paramV) {
    return -paramV.getHeight();
  }
  
  int getScrollRangeForDragFling(V paramV) {
    return paramV.getHeight();
  }
  
  int getTopBottomOffsetForScrollingSibling() {
    return getTopAndBottomOffset();
  }
  
  void onFlingFinished(CoordinatorLayout paramCoordinatorLayout, V paramV) {}
  
  public boolean onInterceptTouchEvent(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent) {
    if (this.mTouchSlop < 0)
      this.mTouchSlop = ViewConfiguration.get(paramCoordinatorLayout.getContext()).getScaledTouchSlop(); 
    if (paramMotionEvent.getAction() == 2 && this.mIsBeingDragged)
      return true; 
    int i = paramMotionEvent.getActionMasked();
    if (i != 0) {
      if (i != 1)
        if (i != 2) {
          if (i != 3)
            VelocityTracker velocityTracker1 = this.mVelocityTracker; 
        } else {
          i = this.mActivePointerId;
          if (i != -1) {
            i = paramMotionEvent.findPointerIndex(i);
            if (i != -1) {
              i = (int)paramMotionEvent.getY(i);
              if (Math.abs(i - this.mLastMotionY) > this.mTouchSlop) {
                this.mIsBeingDragged = true;
                this.mLastMotionY = i;
              } 
            } 
          } 
          VelocityTracker velocityTracker1 = this.mVelocityTracker;
        }  
      this.mIsBeingDragged = false;
      this.mActivePointerId = -1;
      velocityTracker = this.mVelocityTracker;
      if (velocityTracker != null) {
        velocityTracker.recycle();
        this.mVelocityTracker = null;
      } 
    } else {
      this.mIsBeingDragged = false;
      i = (int)paramMotionEvent.getX();
      int j = (int)paramMotionEvent.getY();
      if (canDragView(paramV) && velocityTracker.isPointInChildBounds((View)paramV, i, j)) {
        this.mLastMotionY = j;
        this.mActivePointerId = paramMotionEvent.getPointerId(0);
        ensureVelocityTracker();
      } 
    } 
    VelocityTracker velocityTracker = this.mVelocityTracker;
  }
  
  public boolean onTouchEvent(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent) {
    if (this.mTouchSlop < 0)
      this.mTouchSlop = ViewConfiguration.get(paramCoordinatorLayout.getContext()).getScaledTouchSlop(); 
    int i = paramMotionEvent.getActionMasked();
    if (i != 0) {
      if (i != 1) {
        if (i != 2) {
          if (i != 3)
            velocityTracker = this.mVelocityTracker; 
        } else {
          i = paramMotionEvent.findPointerIndex(this.mActivePointerId);
          if (i == -1)
            return false; 
          int j = (int)paramMotionEvent.getY(i);
          int k = this.mLastMotionY - j;
          i = k;
          if (!this.mIsBeingDragged) {
            int m = Math.abs(k);
            int n = this.mTouchSlop;
            i = k;
            if (m > n) {
              this.mIsBeingDragged = true;
              if (k > 0) {
                i = k - n;
              } else {
                i = k + n;
              } 
            } 
          } 
          if (this.mIsBeingDragged) {
            this.mLastMotionY = j;
            scroll((CoordinatorLayout)velocityTracker, paramV, i, getMaxDragOffset(paramV), 0);
          } 
          velocityTracker = this.mVelocityTracker;
        } 
      } else {
        VelocityTracker velocityTracker1 = this.mVelocityTracker;
        if (velocityTracker1 != null) {
          velocityTracker1.addMovement(paramMotionEvent);
          this.mVelocityTracker.computeCurrentVelocity(1000);
          float f = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
          fling((CoordinatorLayout)velocityTracker, paramV, -getScrollRangeForDragFling(paramV), 0, f);
        } 
      } 
      this.mIsBeingDragged = false;
      this.mActivePointerId = -1;
      velocityTracker = this.mVelocityTracker;
      if (velocityTracker != null) {
        velocityTracker.recycle();
        this.mVelocityTracker = null;
      } 
    } else {
      i = (int)paramMotionEvent.getX();
      int j = (int)paramMotionEvent.getY();
      if (velocityTracker.isPointInChildBounds((View)paramV, i, j) && canDragView(paramV)) {
        this.mLastMotionY = j;
        this.mActivePointerId = paramMotionEvent.getPointerId(0);
        ensureVelocityTracker();
      } else {
        return false;
      } 
    } 
    VelocityTracker velocityTracker = this.mVelocityTracker;
  }
  
  final int scroll(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt1, int paramInt2, int paramInt3) {
    return setHeaderTopBottomOffset(paramCoordinatorLayout, paramV, getTopBottomOffsetForScrollingSibling() - paramInt1, paramInt2, paramInt3);
  }
  
  int setHeaderTopBottomOffset(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt) {
    return setHeaderTopBottomOffset(paramCoordinatorLayout, paramV, paramInt, -2147483648, 2147483647);
  }
  
  int setHeaderTopBottomOffset(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt1, int paramInt2, int paramInt3) {
    int i = getTopAndBottomOffset();
    if (paramInt2 != 0 && i >= paramInt2 && i <= paramInt3) {
      paramInt1 = MathUtils.clamp(paramInt1, paramInt2, paramInt3);
      if (i != paramInt1) {
        setTopAndBottomOffset(paramInt1);
        return i - paramInt1;
      } 
    } 
    return 0;
  }
  
  private class FlingRunnable implements Runnable {
    private final V mLayout;
    
    private final CoordinatorLayout mParent;
    
    FlingRunnable(CoordinatorLayout param1CoordinatorLayout, V param1V) {
      this.mParent = param1CoordinatorLayout;
      this.mLayout = param1V;
    }
    
    public void run() {
      if (this.mLayout != null && HeaderBehavior.this.mScroller != null)
        if (HeaderBehavior.this.mScroller.computeScrollOffset()) {
          HeaderBehavior<V> headerBehavior = HeaderBehavior.this;
          headerBehavior.setHeaderTopBottomOffset(this.mParent, this.mLayout, headerBehavior.mScroller.getCurrY());
          ViewCompat.postOnAnimation((View)this.mLayout, this);
        } else {
          HeaderBehavior.this.onFlingFinished(this.mParent, this.mLayout);
        }  
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\HeaderBehavior.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */