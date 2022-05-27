package android.support.v4.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import java.util.Arrays;

public class ViewDragHelper {
  private static final int BASE_SETTLE_DURATION = 256;
  
  public static final int DIRECTION_ALL = 3;
  
  public static final int DIRECTION_HORIZONTAL = 1;
  
  public static final int DIRECTION_VERTICAL = 2;
  
  public static final int EDGE_ALL = 15;
  
  public static final int EDGE_BOTTOM = 8;
  
  public static final int EDGE_LEFT = 1;
  
  public static final int EDGE_RIGHT = 2;
  
  private static final int EDGE_SIZE = 20;
  
  public static final int EDGE_TOP = 4;
  
  public static final int INVALID_POINTER = -1;
  
  private static final int MAX_SETTLE_DURATION = 600;
  
  public static final int STATE_DRAGGING = 1;
  
  public static final int STATE_IDLE = 0;
  
  public static final int STATE_SETTLING = 2;
  
  private static final String TAG = "ViewDragHelper";
  
  private static final Interpolator sInterpolator = new Interpolator() {
      public float getInterpolation(float param1Float) {
        param1Float--;
        return param1Float * param1Float * param1Float * param1Float * param1Float + 1.0F;
      }
    };
  
  private int mActivePointerId = -1;
  
  private final Callback mCallback;
  
  private View mCapturedView;
  
  private int mDragState;
  
  private int[] mEdgeDragsInProgress;
  
  private int[] mEdgeDragsLocked;
  
  private int mEdgeSize;
  
  private int[] mInitialEdgesTouched;
  
  private float[] mInitialMotionX;
  
  private float[] mInitialMotionY;
  
  private float[] mLastMotionX;
  
  private float[] mLastMotionY;
  
  private float mMaxVelocity;
  
  private float mMinVelocity;
  
  private final ViewGroup mParentView;
  
  private int mPointersDown;
  
  private boolean mReleaseInProgress;
  
  private OverScroller mScroller;
  
  private final Runnable mSetIdleRunnable = new Runnable() {
      public void run() {
        ViewDragHelper.this.setDragState(0);
      }
    };
  
  private int mTouchSlop;
  
  private int mTrackingEdges;
  
  private VelocityTracker mVelocityTracker;
  
  private ViewDragHelper(Context paramContext, ViewGroup paramViewGroup, Callback paramCallback) {
    if (paramViewGroup != null) {
      if (paramCallback != null) {
        this.mParentView = paramViewGroup;
        this.mCallback = paramCallback;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(paramContext);
        this.mEdgeSize = (int)((paramContext.getResources().getDisplayMetrics()).density * 20.0F + 0.5F);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMaxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mMinVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mScroller = new OverScroller(paramContext, sInterpolator);
        return;
      } 
      throw new IllegalArgumentException("Callback may not be null");
    } 
    throw new IllegalArgumentException("Parent view may not be null");
  }
  
  private boolean checkNewEdgeDrag(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2) {
    paramFloat1 = Math.abs(paramFloat1);
    paramFloat2 = Math.abs(paramFloat2);
    int i = this.mInitialEdgesTouched[paramInt1];
    boolean bool1 = false;
    boolean bool2 = bool1;
    if ((i & paramInt2) == paramInt2) {
      bool2 = bool1;
      if ((this.mTrackingEdges & paramInt2) != 0) {
        bool2 = bool1;
        if ((this.mEdgeDragsLocked[paramInt1] & paramInt2) != paramInt2) {
          bool2 = bool1;
          if ((this.mEdgeDragsInProgress[paramInt1] & paramInt2) != paramInt2) {
            i = this.mTouchSlop;
            if (paramFloat1 <= i && paramFloat2 <= i) {
              bool2 = bool1;
            } else {
              if (paramFloat1 < paramFloat2 * 0.5F && this.mCallback.onEdgeLock(paramInt2)) {
                int[] arrayOfInt = this.mEdgeDragsLocked;
                arrayOfInt[paramInt1] = arrayOfInt[paramInt1] | paramInt2;
                return false;
              } 
              bool2 = bool1;
              if ((this.mEdgeDragsInProgress[paramInt1] & paramInt2) == 0) {
                bool2 = bool1;
                if (paramFloat1 > this.mTouchSlop)
                  bool2 = true; 
              } 
            } 
          } 
        } 
      } 
    } 
    return bool2;
  }
  
  private boolean checkTouchSlop(View paramView, float paramFloat1, float paramFloat2) {
    int i;
    boolean bool4;
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    if (paramView == null)
      return false; 
    if (this.mCallback.getViewHorizontalDragRange(paramView) > 0) {
      i = 1;
    } else {
      i = 0;
    } 
    if (this.mCallback.getViewVerticalDragRange(paramView) > 0) {
      bool4 = true;
    } else {
      bool4 = false;
    } 
    if (i && bool4) {
      i = this.mTouchSlop;
      if (paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2 > (i * i))
        bool3 = true; 
      return bool3;
    } 
    if (i != 0) {
      bool3 = bool1;
      if (Math.abs(paramFloat1) > this.mTouchSlop)
        bool3 = true; 
      return bool3;
    } 
    bool3 = bool2;
    if (bool4) {
      bool3 = bool2;
      if (Math.abs(paramFloat2) > this.mTouchSlop)
        bool3 = true; 
    } 
    return bool3;
  }
  
  private float clampMag(float paramFloat1, float paramFloat2, float paramFloat3) {
    float f = Math.abs(paramFloat1);
    if (f < paramFloat2)
      return 0.0F; 
    if (f > paramFloat3) {
      if (paramFloat1 <= 0.0F)
        paramFloat3 = -paramFloat3; 
      return paramFloat3;
    } 
    return paramFloat1;
  }
  
  private int clampMag(int paramInt1, int paramInt2, int paramInt3) {
    int i = Math.abs(paramInt1);
    if (i < paramInt2)
      return 0; 
    if (i > paramInt3) {
      if (paramInt1 <= 0)
        paramInt3 = -paramInt3; 
      return paramInt3;
    } 
    return paramInt1;
  }
  
  private void clearMotionHistory() {
    float[] arrayOfFloat = this.mInitialMotionX;
    if (arrayOfFloat == null)
      return; 
    Arrays.fill(arrayOfFloat, 0.0F);
    Arrays.fill(this.mInitialMotionY, 0.0F);
    Arrays.fill(this.mLastMotionX, 0.0F);
    Arrays.fill(this.mLastMotionY, 0.0F);
    Arrays.fill(this.mInitialEdgesTouched, 0);
    Arrays.fill(this.mEdgeDragsInProgress, 0);
    Arrays.fill(this.mEdgeDragsLocked, 0);
    this.mPointersDown = 0;
  }
  
  private void clearMotionHistory(int paramInt) {
    if (this.mInitialMotionX != null && isPointerDown(paramInt)) {
      this.mInitialMotionX[paramInt] = 0.0F;
      this.mInitialMotionY[paramInt] = 0.0F;
      this.mLastMotionX[paramInt] = 0.0F;
      this.mLastMotionY[paramInt] = 0.0F;
      this.mInitialEdgesTouched[paramInt] = 0;
      this.mEdgeDragsInProgress[paramInt] = 0;
      this.mEdgeDragsLocked[paramInt] = 0;
      this.mPointersDown = 1 << paramInt & this.mPointersDown;
    } 
  }
  
  private int computeAxisDuration(int paramInt1, int paramInt2, int paramInt3) {
    if (paramInt1 == 0)
      return 0; 
    int i = this.mParentView.getWidth();
    int j = i / 2;
    float f1 = Math.min(1.0F, Math.abs(paramInt1) / i);
    float f2 = j;
    f1 = distanceInfluenceForSnapDuration(f1);
    paramInt2 = Math.abs(paramInt2);
    if (paramInt2 > 0) {
      paramInt1 = Math.round(Math.abs((f2 + f1 * f2) / paramInt2) * 1000.0F) * 4;
    } else {
      paramInt1 = (int)((Math.abs(paramInt1) / paramInt3 + 1.0F) * 256.0F);
    } 
    return Math.min(paramInt1, 600);
  }
  
  private int computeSettleDuration(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    float f1;
    paramInt3 = clampMag(paramInt3, (int)this.mMinVelocity, (int)this.mMaxVelocity);
    paramInt4 = clampMag(paramInt4, (int)this.mMinVelocity, (int)this.mMaxVelocity);
    int i = Math.abs(paramInt1);
    int j = Math.abs(paramInt2);
    int k = Math.abs(paramInt3);
    int m = Math.abs(paramInt4);
    int n = k + m;
    int i1 = i + j;
    if (paramInt3 != 0) {
      f1 = k;
      f2 = n;
    } else {
      f1 = i;
      f2 = i1;
    } 
    float f3 = f1 / f2;
    if (paramInt4 != 0) {
      f1 = m;
      f2 = n;
    } else {
      f1 = j;
      f2 = i1;
    } 
    float f2 = f1 / f2;
    paramInt1 = computeAxisDuration(paramInt1, paramInt3, this.mCallback.getViewHorizontalDragRange(paramView));
    paramInt2 = computeAxisDuration(paramInt2, paramInt4, this.mCallback.getViewVerticalDragRange(paramView));
    return (int)(paramInt1 * f3 + paramInt2 * f2);
  }
  
  public static ViewDragHelper create(ViewGroup paramViewGroup, float paramFloat, Callback paramCallback) {
    ViewDragHelper viewDragHelper = create(paramViewGroup, paramCallback);
    viewDragHelper.mTouchSlop = (int)(viewDragHelper.mTouchSlop * 1.0F / paramFloat);
    return viewDragHelper;
  }
  
  public static ViewDragHelper create(ViewGroup paramViewGroup, Callback paramCallback) {
    return new ViewDragHelper(paramViewGroup.getContext(), paramViewGroup, paramCallback);
  }
  
  private void dispatchViewReleased(float paramFloat1, float paramFloat2) {
    this.mReleaseInProgress = true;
    this.mCallback.onViewReleased(this.mCapturedView, paramFloat1, paramFloat2);
    this.mReleaseInProgress = false;
    if (this.mDragState == 1)
      setDragState(0); 
  }
  
  private float distanceInfluenceForSnapDuration(float paramFloat) {
    return (float)Math.sin(((paramFloat - 0.5F) * 0.47123894F));
  }
  
  private void dragTo(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = this.mCapturedView.getLeft();
    int j = this.mCapturedView.getTop();
    int k = paramInt1;
    if (paramInt3 != 0) {
      k = this.mCallback.clampViewPositionHorizontal(this.mCapturedView, paramInt1, paramInt3);
      ViewCompat.offsetLeftAndRight(this.mCapturedView, k - i);
    } 
    paramInt1 = paramInt2;
    if (paramInt4 != 0) {
      paramInt1 = this.mCallback.clampViewPositionVertical(this.mCapturedView, paramInt2, paramInt4);
      ViewCompat.offsetTopAndBottom(this.mCapturedView, paramInt1 - j);
    } 
    if (paramInt3 != 0 || paramInt4 != 0)
      this.mCallback.onViewPositionChanged(this.mCapturedView, k, paramInt1, k - i, paramInt1 - j); 
  }
  
  private void ensureMotionHistorySizeForId(int paramInt) {
    float[] arrayOfFloat = this.mInitialMotionX;
    if (arrayOfFloat == null || arrayOfFloat.length <= paramInt) {
      float[] arrayOfFloat1 = new float[++paramInt];
      float[] arrayOfFloat2 = new float[paramInt];
      float[] arrayOfFloat3 = new float[paramInt];
      float[] arrayOfFloat4 = new float[paramInt];
      int[] arrayOfInt2 = new int[paramInt];
      int[] arrayOfInt3 = new int[paramInt];
      int[] arrayOfInt1 = new int[paramInt];
      float[] arrayOfFloat5 = this.mInitialMotionX;
      if (arrayOfFloat5 != null) {
        System.arraycopy(arrayOfFloat5, 0, arrayOfFloat1, 0, arrayOfFloat5.length);
        arrayOfFloat5 = this.mInitialMotionY;
        System.arraycopy(arrayOfFloat5, 0, arrayOfFloat2, 0, arrayOfFloat5.length);
        arrayOfFloat5 = this.mLastMotionX;
        System.arraycopy(arrayOfFloat5, 0, arrayOfFloat3, 0, arrayOfFloat5.length);
        arrayOfFloat5 = this.mLastMotionY;
        System.arraycopy(arrayOfFloat5, 0, arrayOfFloat4, 0, arrayOfFloat5.length);
        int[] arrayOfInt = this.mInitialEdgesTouched;
        System.arraycopy(arrayOfInt, 0, arrayOfInt2, 0, arrayOfInt.length);
        arrayOfInt = this.mEdgeDragsInProgress;
        System.arraycopy(arrayOfInt, 0, arrayOfInt3, 0, arrayOfInt.length);
        arrayOfInt = this.mEdgeDragsLocked;
        System.arraycopy(arrayOfInt, 0, arrayOfInt1, 0, arrayOfInt.length);
      } 
      this.mInitialMotionX = arrayOfFloat1;
      this.mInitialMotionY = arrayOfFloat2;
      this.mLastMotionX = arrayOfFloat3;
      this.mLastMotionY = arrayOfFloat4;
      this.mInitialEdgesTouched = arrayOfInt2;
      this.mEdgeDragsInProgress = arrayOfInt3;
      this.mEdgeDragsLocked = arrayOfInt1;
    } 
  }
  
  private boolean forceSettleCapturedViewAt(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = this.mCapturedView.getLeft();
    int j = this.mCapturedView.getTop();
    paramInt1 -= i;
    paramInt2 -= j;
    if (paramInt1 == 0 && paramInt2 == 0) {
      this.mScroller.abortAnimation();
      setDragState(0);
      return false;
    } 
    paramInt3 = computeSettleDuration(this.mCapturedView, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mScroller.startScroll(i, j, paramInt1, paramInt2, paramInt3);
    setDragState(2);
    return true;
  }
  
  private int getEdgesTouched(int paramInt1, int paramInt2) {
    if (paramInt1 < this.mParentView.getLeft() + this.mEdgeSize) {
      i = 1;
    } else {
      i = 0;
    } 
    int j = i;
    if (paramInt2 < this.mParentView.getTop() + this.mEdgeSize)
      j = i | 0x4; 
    int i = j;
    if (paramInt1 > this.mParentView.getRight() - this.mEdgeSize)
      i = j | 0x2; 
    paramInt1 = i;
    if (paramInt2 > this.mParentView.getBottom() - this.mEdgeSize)
      paramInt1 = i | 0x8; 
    return paramInt1;
  }
  
  private boolean isValidPointerForActionMove(int paramInt) {
    if (!isPointerDown(paramInt)) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Ignoring pointerId=");
      stringBuilder.append(paramInt);
      stringBuilder.append(" because ACTION_DOWN was not received ");
      stringBuilder.append("for this pointer before ACTION_MOVE. It likely happened because ");
      stringBuilder.append(" ViewDragHelper did not receive all the events in the event stream.");
      Log.e("ViewDragHelper", stringBuilder.toString());
      return false;
    } 
    return true;
  }
  
  private void releaseViewForPointerUp() {
    this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
    dispatchViewReleased(clampMag(this.mVelocityTracker.getXVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity), clampMag(this.mVelocityTracker.getYVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity));
  }
  
  private void reportNewEdgeDrags(float paramFloat1, float paramFloat2, int paramInt) {
    int i = 1;
    if (!checkNewEdgeDrag(paramFloat1, paramFloat2, paramInt, 1))
      i = 0; 
    int j = i;
    if (checkNewEdgeDrag(paramFloat2, paramFloat1, paramInt, 4))
      j = i | 0x4; 
    i = j;
    if (checkNewEdgeDrag(paramFloat1, paramFloat2, paramInt, 2))
      i = j | 0x2; 
    j = i;
    if (checkNewEdgeDrag(paramFloat2, paramFloat1, paramInt, 8))
      j = i | 0x8; 
    if (j != 0) {
      int[] arrayOfInt = this.mEdgeDragsInProgress;
      arrayOfInt[paramInt] = arrayOfInt[paramInt] | j;
      this.mCallback.onEdgeDragStarted(j, paramInt);
    } 
  }
  
  private void saveInitialMotion(float paramFloat1, float paramFloat2, int paramInt) {
    ensureMotionHistorySizeForId(paramInt);
    float[] arrayOfFloat = this.mInitialMotionX;
    this.mLastMotionX[paramInt] = paramFloat1;
    arrayOfFloat[paramInt] = paramFloat1;
    arrayOfFloat = this.mInitialMotionY;
    this.mLastMotionY[paramInt] = paramFloat2;
    arrayOfFloat[paramInt] = paramFloat2;
    this.mInitialEdgesTouched[paramInt] = getEdgesTouched((int)paramFloat1, (int)paramFloat2);
    this.mPointersDown |= 1 << paramInt;
  }
  
  private void saveLastMotion(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getPointerCount();
    for (byte b = 0; b < i; b++) {
      int j = paramMotionEvent.getPointerId(b);
      if (isValidPointerForActionMove(j)) {
        float f1 = paramMotionEvent.getX(b);
        float f2 = paramMotionEvent.getY(b);
        this.mLastMotionX[j] = f1;
        this.mLastMotionY[j] = f2;
      } 
    } 
  }
  
  public void abort() {
    cancel();
    if (this.mDragState == 2) {
      int i = this.mScroller.getCurrX();
      int j = this.mScroller.getCurrY();
      this.mScroller.abortAnimation();
      int k = this.mScroller.getCurrX();
      int m = this.mScroller.getCurrY();
      this.mCallback.onViewPositionChanged(this.mCapturedView, k, m, k - i, m - j);
    } 
    setDragState(0);
  }
  
  protected boolean canScroll(View paramView, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    boolean bool = paramView instanceof ViewGroup;
    boolean bool1 = true;
    if (bool) {
      ViewGroup viewGroup = (ViewGroup)paramView;
      int i = paramView.getScrollX();
      int j = paramView.getScrollY();
      for (int k = viewGroup.getChildCount() - 1; k >= 0; k--) {
        View view = viewGroup.getChildAt(k);
        int m = paramInt3 + i;
        if (m >= view.getLeft() && m < view.getRight()) {
          int n = paramInt4 + j;
          if (n >= view.getTop() && n < view.getBottom() && canScroll(view, true, paramInt1, paramInt2, m - view.getLeft(), n - view.getTop()))
            return true; 
        } 
      } 
    } 
    if (paramBoolean) {
      paramBoolean = bool1;
      if (!paramView.canScrollHorizontally(-paramInt1)) {
        if (paramView.canScrollVertically(-paramInt2))
          return bool1; 
      } else {
        return paramBoolean;
      } 
    } 
    return false;
  }
  
  public void cancel() {
    this.mActivePointerId = -1;
    clearMotionHistory();
    VelocityTracker velocityTracker = this.mVelocityTracker;
    if (velocityTracker != null) {
      velocityTracker.recycle();
      this.mVelocityTracker = null;
    } 
  }
  
  public void captureChildView(View paramView, int paramInt) {
    if (paramView.getParent() == this.mParentView) {
      this.mCapturedView = paramView;
      this.mActivePointerId = paramInt;
      this.mCallback.onViewCaptured(paramView, paramInt);
      setDragState(1);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (");
    stringBuilder.append(this.mParentView);
    stringBuilder.append(")");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public boolean checkTouchSlop(int paramInt) {
    int i = this.mInitialMotionX.length;
    for (byte b = 0; b < i; b++) {
      if (checkTouchSlop(paramInt, b))
        return true; 
    } 
    return false;
  }
  
  public boolean checkTouchSlop(int paramInt1, int paramInt2) {
    boolean bool4;
    boolean bool = isPointerDown(paramInt2);
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    if (!bool)
      return false; 
    if ((paramInt1 & 0x1) == 1) {
      bool4 = true;
    } else {
      bool4 = false;
    } 
    if ((paramInt1 & 0x2) == 2) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    } 
    float f1 = this.mLastMotionX[paramInt2] - this.mInitialMotionX[paramInt2];
    float f2 = this.mLastMotionY[paramInt2] - this.mInitialMotionY[paramInt2];
    if (bool4 && paramInt1 != 0) {
      paramInt1 = this.mTouchSlop;
      if (f1 * f1 + f2 * f2 > (paramInt1 * paramInt1))
        bool3 = true; 
      return bool3;
    } 
    if (bool4) {
      bool3 = bool1;
      if (Math.abs(f1) > this.mTouchSlop)
        bool3 = true; 
      return bool3;
    } 
    bool3 = bool2;
    if (paramInt1 != 0) {
      bool3 = bool2;
      if (Math.abs(f2) > this.mTouchSlop)
        bool3 = true; 
    } 
    return bool3;
  }
  
  public boolean continueSettling(boolean paramBoolean) {
    int i = this.mDragState;
    boolean bool = false;
    if (i == 2) {
      boolean bool1 = this.mScroller.computeScrollOffset();
      i = this.mScroller.getCurrX();
      int j = this.mScroller.getCurrY();
      int k = i - this.mCapturedView.getLeft();
      int m = j - this.mCapturedView.getTop();
      if (k != 0)
        ViewCompat.offsetLeftAndRight(this.mCapturedView, k); 
      if (m != 0)
        ViewCompat.offsetTopAndBottom(this.mCapturedView, m); 
      if (k != 0 || m != 0)
        this.mCallback.onViewPositionChanged(this.mCapturedView, i, j, k, m); 
      boolean bool2 = bool1;
      if (bool1) {
        bool2 = bool1;
        if (i == this.mScroller.getFinalX()) {
          bool2 = bool1;
          if (j == this.mScroller.getFinalY()) {
            this.mScroller.abortAnimation();
            bool2 = false;
          } 
        } 
      } 
      if (!bool2)
        if (paramBoolean) {
          this.mParentView.post(this.mSetIdleRunnable);
        } else {
          setDragState(0);
        }  
    } 
    paramBoolean = bool;
    if (this.mDragState == 2)
      paramBoolean = true; 
    return paramBoolean;
  }
  
  public View findTopChildUnder(int paramInt1, int paramInt2) {
    for (int i = this.mParentView.getChildCount() - 1; i >= 0; i--) {
      View view = this.mParentView.getChildAt(this.mCallback.getOrderedChildIndex(i));
      if (paramInt1 >= view.getLeft() && paramInt1 < view.getRight() && paramInt2 >= view.getTop() && paramInt2 < view.getBottom())
        return view; 
    } 
    return null;
  }
  
  public void flingCapturedView(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (this.mReleaseInProgress) {
      this.mScroller.fling(this.mCapturedView.getLeft(), this.mCapturedView.getTop(), (int)this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int)this.mVelocityTracker.getYVelocity(this.mActivePointerId), paramInt1, paramInt3, paramInt2, paramInt4);
      setDragState(2);
      return;
    } 
    throw new IllegalStateException("Cannot flingCapturedView outside of a call to Callback#onViewReleased");
  }
  
  public int getActivePointerId() {
    return this.mActivePointerId;
  }
  
  public View getCapturedView() {
    return this.mCapturedView;
  }
  
  public int getEdgeSize() {
    return this.mEdgeSize;
  }
  
  public float getMinVelocity() {
    return this.mMinVelocity;
  }
  
  public int getTouchSlop() {
    return this.mTouchSlop;
  }
  
  public int getViewDragState() {
    return this.mDragState;
  }
  
  public boolean isCapturedViewUnder(int paramInt1, int paramInt2) {
    return isViewUnder(this.mCapturedView, paramInt1, paramInt2);
  }
  
  public boolean isEdgeTouched(int paramInt) {
    int i = this.mInitialEdgesTouched.length;
    for (byte b = 0; b < i; b++) {
      if (isEdgeTouched(paramInt, b))
        return true; 
    } 
    return false;
  }
  
  public boolean isEdgeTouched(int paramInt1, int paramInt2) {
    boolean bool;
    if (isPointerDown(paramInt2) && (paramInt1 & this.mInitialEdgesTouched[paramInt2]) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isPointerDown(int paramInt) {
    int i = this.mPointersDown;
    boolean bool = true;
    if ((1 << paramInt & i) == 0)
      bool = false; 
    return bool;
  }
  
  public boolean isViewUnder(View paramView, int paramInt1, int paramInt2) {
    boolean bool1 = false;
    if (paramView == null)
      return false; 
    boolean bool2 = bool1;
    if (paramInt1 >= paramView.getLeft()) {
      bool2 = bool1;
      if (paramInt1 < paramView.getRight()) {
        bool2 = bool1;
        if (paramInt2 >= paramView.getTop()) {
          bool2 = bool1;
          if (paramInt2 < paramView.getBottom())
            bool2 = true; 
        } 
      } 
    } 
    return bool2;
  }
  
  public void processTouchEvent(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getActionMasked();
    int j = paramMotionEvent.getActionIndex();
    if (i == 0)
      cancel(); 
    if (this.mVelocityTracker == null)
      this.mVelocityTracker = VelocityTracker.obtain(); 
    this.mVelocityTracker.addMovement(paramMotionEvent);
    int k = 0;
    int m = 0;
    if (i != 0) {
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i != 5) {
              if (i == 6) {
                k = paramMotionEvent.getPointerId(j);
                if (this.mDragState == 1 && k == this.mActivePointerId) {
                  j = paramMotionEvent.getPointerCount();
                  while (true) {
                    if (m < j) {
                      i = paramMotionEvent.getPointerId(m);
                      if (i != this.mActivePointerId) {
                        float f1 = paramMotionEvent.getX(m);
                        float f2 = paramMotionEvent.getY(m);
                        View view1 = findTopChildUnder((int)f1, (int)f2);
                        View view2 = this.mCapturedView;
                        if (view1 == view2 && tryCaptureViewForDrag(view2, i)) {
                          m = this.mActivePointerId;
                          break;
                        } 
                      } 
                      m++;
                      continue;
                    } 
                    m = -1;
                    break;
                  } 
                  if (m == -1)
                    releaseViewForPointerUp(); 
                } 
                clearMotionHistory(k);
              } 
            } else {
              m = paramMotionEvent.getPointerId(j);
              float f2 = paramMotionEvent.getX(j);
              float f1 = paramMotionEvent.getY(j);
              saveInitialMotion(f2, f1, m);
              if (this.mDragState == 0) {
                tryCaptureViewForDrag(findTopChildUnder((int)f2, (int)f1), m);
                k = this.mInitialEdgesTouched[m];
                j = this.mTrackingEdges;
                if ((k & j) != 0)
                  this.mCallback.onEdgeTouched(k & j, m); 
              } else if (isCapturedViewUnder((int)f2, (int)f1)) {
                tryCaptureViewForDrag(this.mCapturedView, m);
              } 
            } 
          } else {
            if (this.mDragState == 1)
              dispatchViewReleased(0.0F, 0.0F); 
            cancel();
          } 
        } else if (this.mDragState == 1) {
          if (isValidPointerForActionMove(this.mActivePointerId)) {
            m = paramMotionEvent.findPointerIndex(this.mActivePointerId);
            float f2 = paramMotionEvent.getX(m);
            float f1 = paramMotionEvent.getY(m);
            float[] arrayOfFloat = this.mLastMotionX;
            k = this.mActivePointerId;
            m = (int)(f2 - arrayOfFloat[k]);
            k = (int)(f1 - this.mLastMotionY[k]);
            dragTo(this.mCapturedView.getLeft() + m, this.mCapturedView.getTop() + k, m, k);
            saveLastMotion(paramMotionEvent);
          } 
        } else {
          j = paramMotionEvent.getPointerCount();
          for (m = k; m < j; m++) {
            k = paramMotionEvent.getPointerId(m);
            if (isValidPointerForActionMove(k)) {
              float f2 = paramMotionEvent.getX(m);
              float f1 = paramMotionEvent.getY(m);
              float f3 = f2 - this.mInitialMotionX[k];
              float f4 = f1 - this.mInitialMotionY[k];
              reportNewEdgeDrags(f3, f4, k);
              if (this.mDragState == 1)
                break; 
              View view = findTopChildUnder((int)f2, (int)f1);
              if (checkTouchSlop(view, f3, f4) && tryCaptureViewForDrag(view, k))
                break; 
            } 
          } 
          saveLastMotion(paramMotionEvent);
        } 
      } else {
        if (this.mDragState == 1)
          releaseViewForPointerUp(); 
        cancel();
      } 
    } else {
      float f1 = paramMotionEvent.getX();
      float f2 = paramMotionEvent.getY();
      m = paramMotionEvent.getPointerId(0);
      View view = findTopChildUnder((int)f1, (int)f2);
      saveInitialMotion(f1, f2, m);
      tryCaptureViewForDrag(view, m);
      k = this.mInitialEdgesTouched[m];
      j = this.mTrackingEdges;
      if ((k & j) != 0)
        this.mCallback.onEdgeTouched(k & j, m); 
    } 
  }
  
  void setDragState(int paramInt) {
    this.mParentView.removeCallbacks(this.mSetIdleRunnable);
    if (this.mDragState != paramInt) {
      this.mDragState = paramInt;
      this.mCallback.onViewDragStateChanged(paramInt);
      if (this.mDragState == 0)
        this.mCapturedView = null; 
    } 
  }
  
  public void setEdgeTrackingEnabled(int paramInt) {
    this.mTrackingEdges = paramInt;
  }
  
  public void setMinVelocity(float paramFloat) {
    this.mMinVelocity = paramFloat;
  }
  
  public boolean settleCapturedViewAt(int paramInt1, int paramInt2) {
    if (this.mReleaseInProgress)
      return forceSettleCapturedViewAt(paramInt1, paramInt2, (int)this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int)this.mVelocityTracker.getYVelocity(this.mActivePointerId)); 
    throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
  }
  
  public boolean shouldInterceptTouchEvent(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getActionMasked : ()I
    //   4: istore_2
    //   5: aload_1
    //   6: invokevirtual getActionIndex : ()I
    //   9: istore_3
    //   10: iload_2
    //   11: ifne -> 18
    //   14: aload_0
    //   15: invokevirtual cancel : ()V
    //   18: aload_0
    //   19: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   22: ifnonnull -> 32
    //   25: aload_0
    //   26: invokestatic obtain : ()Landroid/view/VelocityTracker;
    //   29: putfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   32: aload_0
    //   33: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   36: aload_1
    //   37: invokevirtual addMovement : (Landroid/view/MotionEvent;)V
    //   40: iload_2
    //   41: ifeq -> 500
    //   44: iload_2
    //   45: iconst_1
    //   46: if_icmpeq -> 493
    //   49: iload_2
    //   50: iconst_2
    //   51: if_icmpeq -> 192
    //   54: iload_2
    //   55: iconst_3
    //   56: if_icmpeq -> 493
    //   59: iload_2
    //   60: iconst_5
    //   61: if_icmpeq -> 85
    //   64: iload_2
    //   65: bipush #6
    //   67: if_icmpeq -> 73
    //   70: goto -> 595
    //   73: aload_0
    //   74: aload_1
    //   75: iload_3
    //   76: invokevirtual getPointerId : (I)I
    //   79: invokespecial clearMotionHistory : (I)V
    //   82: goto -> 70
    //   85: aload_1
    //   86: iload_3
    //   87: invokevirtual getPointerId : (I)I
    //   90: istore_2
    //   91: aload_1
    //   92: iload_3
    //   93: invokevirtual getX : (I)F
    //   96: fstore #4
    //   98: aload_1
    //   99: iload_3
    //   100: invokevirtual getY : (I)F
    //   103: fstore #5
    //   105: aload_0
    //   106: fload #4
    //   108: fload #5
    //   110: iload_2
    //   111: invokespecial saveInitialMotion : (FFI)V
    //   114: aload_0
    //   115: getfield mDragState : I
    //   118: istore_3
    //   119: iload_3
    //   120: ifne -> 158
    //   123: aload_0
    //   124: getfield mInitialEdgesTouched : [I
    //   127: iload_2
    //   128: iaload
    //   129: istore #6
    //   131: aload_0
    //   132: getfield mTrackingEdges : I
    //   135: istore_3
    //   136: iload #6
    //   138: iload_3
    //   139: iand
    //   140: ifeq -> 70
    //   143: aload_0
    //   144: getfield mCallback : Landroid/support/v4/widget/ViewDragHelper$Callback;
    //   147: iload #6
    //   149: iload_3
    //   150: iand
    //   151: iload_2
    //   152: invokevirtual onEdgeTouched : (II)V
    //   155: goto -> 70
    //   158: iload_3
    //   159: iconst_2
    //   160: if_icmpne -> 70
    //   163: aload_0
    //   164: fload #4
    //   166: f2i
    //   167: fload #5
    //   169: f2i
    //   170: invokevirtual findTopChildUnder : (II)Landroid/view/View;
    //   173: astore_1
    //   174: aload_1
    //   175: aload_0
    //   176: getfield mCapturedView : Landroid/view/View;
    //   179: if_acmpne -> 70
    //   182: aload_0
    //   183: aload_1
    //   184: iload_2
    //   185: invokevirtual tryCaptureViewForDrag : (Landroid/view/View;I)Z
    //   188: pop
    //   189: goto -> 70
    //   192: aload_0
    //   193: getfield mInitialMotionX : [F
    //   196: ifnull -> 70
    //   199: aload_0
    //   200: getfield mInitialMotionY : [F
    //   203: ifnonnull -> 209
    //   206: goto -> 70
    //   209: aload_1
    //   210: invokevirtual getPointerCount : ()I
    //   213: istore #6
    //   215: iconst_0
    //   216: istore_2
    //   217: iload_2
    //   218: iload #6
    //   220: if_icmpge -> 485
    //   223: aload_1
    //   224: iload_2
    //   225: invokevirtual getPointerId : (I)I
    //   228: istore #7
    //   230: aload_0
    //   231: iload #7
    //   233: invokespecial isValidPointerForActionMove : (I)Z
    //   236: ifne -> 242
    //   239: goto -> 479
    //   242: aload_1
    //   243: iload_2
    //   244: invokevirtual getX : (I)F
    //   247: fstore #8
    //   249: aload_1
    //   250: iload_2
    //   251: invokevirtual getY : (I)F
    //   254: fstore #5
    //   256: fload #8
    //   258: aload_0
    //   259: getfield mInitialMotionX : [F
    //   262: iload #7
    //   264: faload
    //   265: fsub
    //   266: fstore #9
    //   268: fload #5
    //   270: aload_0
    //   271: getfield mInitialMotionY : [F
    //   274: iload #7
    //   276: faload
    //   277: fsub
    //   278: fstore #4
    //   280: aload_0
    //   281: fload #8
    //   283: f2i
    //   284: fload #5
    //   286: f2i
    //   287: invokevirtual findTopChildUnder : (II)Landroid/view/View;
    //   290: astore #10
    //   292: aload #10
    //   294: ifnull -> 315
    //   297: aload_0
    //   298: aload #10
    //   300: fload #9
    //   302: fload #4
    //   304: invokespecial checkTouchSlop : (Landroid/view/View;FF)Z
    //   307: ifeq -> 315
    //   310: iconst_1
    //   311: istore_3
    //   312: goto -> 317
    //   315: iconst_0
    //   316: istore_3
    //   317: iload_3
    //   318: ifeq -> 440
    //   321: aload #10
    //   323: invokevirtual getLeft : ()I
    //   326: istore #11
    //   328: fload #9
    //   330: f2i
    //   331: istore #12
    //   333: aload_0
    //   334: getfield mCallback : Landroid/support/v4/widget/ViewDragHelper$Callback;
    //   337: aload #10
    //   339: iload #11
    //   341: iload #12
    //   343: iadd
    //   344: iload #12
    //   346: invokevirtual clampViewPositionHorizontal : (Landroid/view/View;II)I
    //   349: istore #12
    //   351: aload #10
    //   353: invokevirtual getTop : ()I
    //   356: istore #13
    //   358: fload #4
    //   360: f2i
    //   361: istore #14
    //   363: aload_0
    //   364: getfield mCallback : Landroid/support/v4/widget/ViewDragHelper$Callback;
    //   367: aload #10
    //   369: iload #13
    //   371: iload #14
    //   373: iadd
    //   374: iload #14
    //   376: invokevirtual clampViewPositionVertical : (Landroid/view/View;II)I
    //   379: istore #15
    //   381: aload_0
    //   382: getfield mCallback : Landroid/support/v4/widget/ViewDragHelper$Callback;
    //   385: aload #10
    //   387: invokevirtual getViewHorizontalDragRange : (Landroid/view/View;)I
    //   390: istore #16
    //   392: aload_0
    //   393: getfield mCallback : Landroid/support/v4/widget/ViewDragHelper$Callback;
    //   396: aload #10
    //   398: invokevirtual getViewVerticalDragRange : (Landroid/view/View;)I
    //   401: istore #14
    //   403: iload #16
    //   405: ifeq -> 420
    //   408: iload #16
    //   410: ifle -> 440
    //   413: iload #12
    //   415: iload #11
    //   417: if_icmpne -> 440
    //   420: iload #14
    //   422: ifeq -> 485
    //   425: iload #14
    //   427: ifle -> 440
    //   430: iload #15
    //   432: iload #13
    //   434: if_icmpne -> 440
    //   437: goto -> 485
    //   440: aload_0
    //   441: fload #9
    //   443: fload #4
    //   445: iload #7
    //   447: invokespecial reportNewEdgeDrags : (FFI)V
    //   450: aload_0
    //   451: getfield mDragState : I
    //   454: iconst_1
    //   455: if_icmpne -> 461
    //   458: goto -> 485
    //   461: iload_3
    //   462: ifeq -> 479
    //   465: aload_0
    //   466: aload #10
    //   468: iload #7
    //   470: invokevirtual tryCaptureViewForDrag : (Landroid/view/View;I)Z
    //   473: ifeq -> 479
    //   476: goto -> 485
    //   479: iinc #2, 1
    //   482: goto -> 217
    //   485: aload_0
    //   486: aload_1
    //   487: invokespecial saveLastMotion : (Landroid/view/MotionEvent;)V
    //   490: goto -> 70
    //   493: aload_0
    //   494: invokevirtual cancel : ()V
    //   497: goto -> 70
    //   500: aload_1
    //   501: invokevirtual getX : ()F
    //   504: fstore #4
    //   506: aload_1
    //   507: invokevirtual getY : ()F
    //   510: fstore #5
    //   512: aload_1
    //   513: iconst_0
    //   514: invokevirtual getPointerId : (I)I
    //   517: istore #6
    //   519: aload_0
    //   520: fload #4
    //   522: fload #5
    //   524: iload #6
    //   526: invokespecial saveInitialMotion : (FFI)V
    //   529: aload_0
    //   530: fload #4
    //   532: f2i
    //   533: fload #5
    //   535: f2i
    //   536: invokevirtual findTopChildUnder : (II)Landroid/view/View;
    //   539: astore_1
    //   540: aload_1
    //   541: aload_0
    //   542: getfield mCapturedView : Landroid/view/View;
    //   545: if_acmpne -> 564
    //   548: aload_0
    //   549: getfield mDragState : I
    //   552: iconst_2
    //   553: if_icmpne -> 564
    //   556: aload_0
    //   557: aload_1
    //   558: iload #6
    //   560: invokevirtual tryCaptureViewForDrag : (Landroid/view/View;I)Z
    //   563: pop
    //   564: aload_0
    //   565: getfield mInitialEdgesTouched : [I
    //   568: iload #6
    //   570: iaload
    //   571: istore_3
    //   572: aload_0
    //   573: getfield mTrackingEdges : I
    //   576: istore_2
    //   577: iload_3
    //   578: iload_2
    //   579: iand
    //   580: ifeq -> 595
    //   583: aload_0
    //   584: getfield mCallback : Landroid/support/v4/widget/ViewDragHelper$Callback;
    //   587: iload_3
    //   588: iload_2
    //   589: iand
    //   590: iload #6
    //   592: invokevirtual onEdgeTouched : (II)V
    //   595: iconst_0
    //   596: istore #17
    //   598: aload_0
    //   599: getfield mDragState : I
    //   602: iconst_1
    //   603: if_icmpne -> 609
    //   606: iconst_1
    //   607: istore #17
    //   609: iload #17
    //   611: ireturn
  }
  
  public boolean smoothSlideViewTo(View paramView, int paramInt1, int paramInt2) {
    this.mCapturedView = paramView;
    this.mActivePointerId = -1;
    boolean bool = forceSettleCapturedViewAt(paramInt1, paramInt2, 0, 0);
    if (!bool && this.mDragState == 0 && this.mCapturedView != null)
      this.mCapturedView = null; 
    return bool;
  }
  
  boolean tryCaptureViewForDrag(View paramView, int paramInt) {
    if (paramView == this.mCapturedView && this.mActivePointerId == paramInt)
      return true; 
    if (paramView != null && this.mCallback.tryCaptureView(paramView, paramInt)) {
      this.mActivePointerId = paramInt;
      captureChildView(paramView, paramInt);
      return true;
    } 
    return false;
  }
  
  public static abstract class Callback {
    public int clampViewPositionHorizontal(View param1View, int param1Int1, int param1Int2) {
      return 0;
    }
    
    public int clampViewPositionVertical(View param1View, int param1Int1, int param1Int2) {
      return 0;
    }
    
    public int getOrderedChildIndex(int param1Int) {
      return param1Int;
    }
    
    public int getViewHorizontalDragRange(View param1View) {
      return 0;
    }
    
    public int getViewVerticalDragRange(View param1View) {
      return 0;
    }
    
    public void onEdgeDragStarted(int param1Int1, int param1Int2) {}
    
    public boolean onEdgeLock(int param1Int) {
      return false;
    }
    
    public void onEdgeTouched(int param1Int1, int param1Int2) {}
    
    public void onViewCaptured(View param1View, int param1Int) {}
    
    public void onViewDragStateChanged(int param1Int) {}
    
    public void onViewPositionChanged(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {}
    
    public void onViewReleased(View param1View, float param1Float1, float param1Float2) {}
    
    public abstract boolean tryCaptureView(View param1View, int param1Int);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\ViewDragHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */