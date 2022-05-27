package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityRecord;
import android.view.animation.AnimationUtils;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;

public class NestedScrollView extends FrameLayout implements NestedScrollingParent, NestedScrollingChild2, ScrollingView {
  private static final AccessibilityDelegate ACCESSIBILITY_DELEGATE = new AccessibilityDelegate();
  
  static final int ANIMATED_SCROLL_GAP = 250;
  
  private static final int INVALID_POINTER = -1;
  
  static final float MAX_SCROLL_FACTOR = 0.5F;
  
  private static final int[] SCROLLVIEW_STYLEABLE = new int[] { 16843130 };
  
  private static final String TAG = "NestedScrollView";
  
  private int mActivePointerId = -1;
  
  private final NestedScrollingChildHelper mChildHelper;
  
  private View mChildToScrollTo = null;
  
  private EdgeEffect mEdgeGlowBottom;
  
  private EdgeEffect mEdgeGlowTop;
  
  private boolean mFillViewport;
  
  private boolean mIsBeingDragged = false;
  
  private boolean mIsLaidOut = false;
  
  private boolean mIsLayoutDirty = true;
  
  private int mLastMotionY;
  
  private long mLastScroll;
  
  private int mLastScrollerY;
  
  private int mMaximumVelocity;
  
  private int mMinimumVelocity;
  
  private int mNestedYOffset;
  
  private OnScrollChangeListener mOnScrollChangeListener;
  
  private final NestedScrollingParentHelper mParentHelper;
  
  private SavedState mSavedState;
  
  private final int[] mScrollConsumed = new int[2];
  
  private final int[] mScrollOffset = new int[2];
  
  private OverScroller mScroller;
  
  private boolean mSmoothScrollingEnabled = true;
  
  private final Rect mTempRect = new Rect();
  
  private int mTouchSlop;
  
  private VelocityTracker mVelocityTracker;
  
  private float mVerticalScrollFactor;
  
  public NestedScrollView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public NestedScrollView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public NestedScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    initScrollView();
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, SCROLLVIEW_STYLEABLE, paramInt, 0);
    setFillViewport(typedArray.getBoolean(0, false));
    typedArray.recycle();
    this.mParentHelper = new NestedScrollingParentHelper((ViewGroup)this);
    this.mChildHelper = new NestedScrollingChildHelper((View)this);
    setNestedScrollingEnabled(true);
    ViewCompat.setAccessibilityDelegate((View)this, ACCESSIBILITY_DELEGATE);
  }
  
  private boolean canScroll() {
    boolean bool1 = false;
    View view = getChildAt(0);
    boolean bool2 = bool1;
    if (view != null) {
      int i = view.getHeight();
      bool2 = bool1;
      if (getHeight() < i + getPaddingTop() + getPaddingBottom())
        bool2 = true; 
    } 
    return bool2;
  }
  
  private static int clamp(int paramInt1, int paramInt2, int paramInt3) {
    return (paramInt2 >= paramInt3 || paramInt1 < 0) ? 0 : ((paramInt2 + paramInt1 > paramInt3) ? (paramInt3 - paramInt2) : paramInt1);
  }
  
  private void doScrollY(int paramInt) {
    if (paramInt != 0)
      if (this.mSmoothScrollingEnabled) {
        smoothScrollBy(0, paramInt);
      } else {
        scrollBy(0, paramInt);
      }  
  }
  
  private void endDrag() {
    this.mIsBeingDragged = false;
    recycleVelocityTracker();
    stopNestedScroll(0);
    EdgeEffect edgeEffect = this.mEdgeGlowTop;
    if (edgeEffect != null) {
      edgeEffect.onRelease();
      this.mEdgeGlowBottom.onRelease();
    } 
  }
  
  private void ensureGlows() {
    if (getOverScrollMode() != 2) {
      if (this.mEdgeGlowTop == null) {
        Context context = getContext();
        this.mEdgeGlowTop = new EdgeEffect(context);
        this.mEdgeGlowBottom = new EdgeEffect(context);
      } 
    } else {
      this.mEdgeGlowTop = null;
      this.mEdgeGlowBottom = null;
    } 
  }
  
  private View findFocusableViewInBounds(boolean paramBoolean, int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: iconst_2
    //   2: invokevirtual getFocusables : (I)Ljava/util/ArrayList;
    //   5: astore #4
    //   7: aload #4
    //   9: invokeinterface size : ()I
    //   14: istore #5
    //   16: aconst_null
    //   17: astore #6
    //   19: iconst_0
    //   20: istore #7
    //   22: iconst_0
    //   23: istore #8
    //   25: iload #7
    //   27: iload #5
    //   29: if_icmpge -> 246
    //   32: aload #4
    //   34: iload #7
    //   36: invokeinterface get : (I)Ljava/lang/Object;
    //   41: checkcast android/view/View
    //   44: astore #9
    //   46: aload #9
    //   48: invokevirtual getTop : ()I
    //   51: istore #10
    //   53: aload #9
    //   55: invokevirtual getBottom : ()I
    //   58: istore #11
    //   60: aload #6
    //   62: astore #12
    //   64: iload #8
    //   66: istore #13
    //   68: iload_2
    //   69: iload #11
    //   71: if_icmpge -> 232
    //   74: aload #6
    //   76: astore #12
    //   78: iload #8
    //   80: istore #13
    //   82: iload #10
    //   84: iload_3
    //   85: if_icmpge -> 232
    //   88: iload_2
    //   89: iload #10
    //   91: if_icmpge -> 106
    //   94: iload #11
    //   96: iload_3
    //   97: if_icmpge -> 106
    //   100: iconst_1
    //   101: istore #14
    //   103: goto -> 109
    //   106: iconst_0
    //   107: istore #14
    //   109: aload #6
    //   111: ifnonnull -> 125
    //   114: aload #9
    //   116: astore #12
    //   118: iload #14
    //   120: istore #13
    //   122: goto -> 232
    //   125: iload_1
    //   126: ifeq -> 139
    //   129: iload #10
    //   131: aload #6
    //   133: invokevirtual getTop : ()I
    //   136: if_icmplt -> 153
    //   139: iload_1
    //   140: ifne -> 159
    //   143: iload #11
    //   145: aload #6
    //   147: invokevirtual getBottom : ()I
    //   150: if_icmple -> 159
    //   153: iconst_1
    //   154: istore #11
    //   156: goto -> 162
    //   159: iconst_0
    //   160: istore #11
    //   162: iload #8
    //   164: ifeq -> 196
    //   167: aload #6
    //   169: astore #12
    //   171: iload #8
    //   173: istore #13
    //   175: iload #14
    //   177: ifeq -> 232
    //   180: aload #6
    //   182: astore #12
    //   184: iload #8
    //   186: istore #13
    //   188: iload #11
    //   190: ifeq -> 232
    //   193: goto -> 224
    //   196: iload #14
    //   198: ifeq -> 211
    //   201: aload #9
    //   203: astore #12
    //   205: iconst_1
    //   206: istore #13
    //   208: goto -> 232
    //   211: aload #6
    //   213: astore #12
    //   215: iload #8
    //   217: istore #13
    //   219: iload #11
    //   221: ifeq -> 232
    //   224: aload #9
    //   226: astore #12
    //   228: iload #8
    //   230: istore #13
    //   232: iinc #7, 1
    //   235: aload #12
    //   237: astore #6
    //   239: iload #13
    //   241: istore #8
    //   243: goto -> 25
    //   246: aload #6
    //   248: areturn
  }
  
  private void flingWithNestedDispatch(int paramInt) {
    boolean bool;
    int i = getScrollY();
    if ((i > 0 || paramInt > 0) && (i < getScrollRange() || paramInt < 0)) {
      bool = true;
    } else {
      bool = false;
    } 
    float f = paramInt;
    if (!dispatchNestedPreFling(0.0F, f)) {
      dispatchNestedFling(0.0F, f, bool);
      fling(paramInt);
    } 
  }
  
  private float getVerticalScrollFactorCompat() {
    if (this.mVerticalScrollFactor == 0.0F) {
      TypedValue typedValue = new TypedValue();
      Context context = getContext();
      if (context.getTheme().resolveAttribute(16842829, typedValue, true)) {
        this.mVerticalScrollFactor = typedValue.getDimension(context.getResources().getDisplayMetrics());
      } else {
        throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
      } 
    } 
    return this.mVerticalScrollFactor;
  }
  
  private boolean inChild(int paramInt1, int paramInt2) {
    int i = getChildCount();
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (i > 0) {
      i = getScrollY();
      View view = getChildAt(0);
      bool2 = bool1;
      if (paramInt2 >= view.getTop() - i) {
        bool2 = bool1;
        if (paramInt2 < view.getBottom() - i) {
          bool2 = bool1;
          if (paramInt1 >= view.getLeft()) {
            bool2 = bool1;
            if (paramInt1 < view.getRight())
              bool2 = true; 
          } 
        } 
      } 
    } 
    return bool2;
  }
  
  private void initOrResetVelocityTracker() {
    VelocityTracker velocityTracker = this.mVelocityTracker;
    if (velocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    } else {
      velocityTracker.clear();
    } 
  }
  
  private void initScrollView() {
    this.mScroller = new OverScroller(getContext());
    setFocusable(true);
    setDescendantFocusability(262144);
    setWillNotDraw(false);
    ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
    this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
    this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
    this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
  }
  
  private void initVelocityTrackerIfNotExists() {
    if (this.mVelocityTracker == null)
      this.mVelocityTracker = VelocityTracker.obtain(); 
  }
  
  private boolean isOffScreen(View paramView) {
    return isWithinDeltaOfScreen(paramView, 0, getHeight()) ^ true;
  }
  
  private static boolean isViewDescendantOf(View paramView1, View paramView2) {
    boolean bool = true;
    if (paramView1 == paramView2)
      return true; 
    ViewParent viewParent = paramView1.getParent();
    if (!(viewParent instanceof ViewGroup) || !isViewDescendantOf((View)viewParent, paramView2))
      bool = false; 
    return bool;
  }
  
  private boolean isWithinDeltaOfScreen(View paramView, int paramInt1, int paramInt2) {
    boolean bool;
    paramView.getDrawingRect(this.mTempRect);
    offsetDescendantRectToMyCoords(paramView, this.mTempRect);
    if (this.mTempRect.bottom + paramInt1 >= getScrollY() && this.mTempRect.top - paramInt1 <= getScrollY() + paramInt2) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private void onSecondaryPointerUp(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getActionIndex();
    if (paramMotionEvent.getPointerId(i) == this.mActivePointerId) {
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      } 
      this.mLastMotionY = (int)paramMotionEvent.getY(i);
      this.mActivePointerId = paramMotionEvent.getPointerId(i);
      VelocityTracker velocityTracker = this.mVelocityTracker;
      if (velocityTracker != null)
        velocityTracker.clear(); 
    } 
  }
  
  private void recycleVelocityTracker() {
    VelocityTracker velocityTracker = this.mVelocityTracker;
    if (velocityTracker != null) {
      velocityTracker.recycle();
      this.mVelocityTracker = null;
    } 
  }
  
  private boolean scrollAndFocus(int paramInt1, int paramInt2, int paramInt3) {
    boolean bool2;
    NestedScrollView nestedScrollView;
    int i = getHeight();
    int j = getScrollY();
    i += j;
    boolean bool1 = false;
    if (paramInt1 == 33) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    View view1 = findFocusableViewInBounds(bool2, paramInt2, paramInt3);
    View view2 = view1;
    if (view1 == null)
      nestedScrollView = this; 
    if (paramInt2 >= j && paramInt3 <= i) {
      bool2 = bool1;
    } else {
      if (bool2) {
        paramInt2 -= j;
      } else {
        paramInt2 = paramInt3 - i;
      } 
      doScrollY(paramInt2);
      bool2 = true;
    } 
    if (nestedScrollView != findFocus())
      nestedScrollView.requestFocus(paramInt1); 
    return bool2;
  }
  
  private void scrollToChild(View paramView) {
    paramView.getDrawingRect(this.mTempRect);
    offsetDescendantRectToMyCoords(paramView, this.mTempRect);
    int i = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
    if (i != 0)
      scrollBy(0, i); 
  }
  
  private boolean scrollToChildRect(Rect paramRect, boolean paramBoolean) {
    boolean bool;
    int i = computeScrollDeltaToGetChildRectOnScreen(paramRect);
    if (i != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool)
      if (paramBoolean) {
        scrollBy(0, i);
      } else {
        smoothScrollBy(0, i);
      }  
    return bool;
  }
  
  public void addView(View paramView) {
    if (getChildCount() <= 0) {
      super.addView(paramView);
      return;
    } 
    throw new IllegalStateException("ScrollView can host only one direct child");
  }
  
  public void addView(View paramView, int paramInt) {
    if (getChildCount() <= 0) {
      super.addView(paramView, paramInt);
      return;
    } 
    throw new IllegalStateException("ScrollView can host only one direct child");
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams) {
    if (getChildCount() <= 0) {
      super.addView(paramView, paramInt, paramLayoutParams);
      return;
    } 
    throw new IllegalStateException("ScrollView can host only one direct child");
  }
  
  public void addView(View paramView, ViewGroup.LayoutParams paramLayoutParams) {
    if (getChildCount() <= 0) {
      super.addView(paramView, paramLayoutParams);
      return;
    } 
    throw new IllegalStateException("ScrollView can host only one direct child");
  }
  
  public boolean arrowScroll(int paramInt) {
    View view1 = findFocus();
    View view2 = view1;
    if (view1 == this)
      view2 = null; 
    view1 = FocusFinder.getInstance().findNextFocus((ViewGroup)this, view2, paramInt);
    int i = getMaxScrollAmount();
    if (view1 != null && isWithinDeltaOfScreen(view1, i, getHeight())) {
      view1.getDrawingRect(this.mTempRect);
      offsetDescendantRectToMyCoords(view1, this.mTempRect);
      doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
      view1.requestFocus(paramInt);
    } else {
      int j;
      if (paramInt == 33 && getScrollY() < i) {
        j = getScrollY();
      } else {
        j = i;
        if (paramInt == 130) {
          j = i;
          if (getChildCount() > 0) {
            int k = getChildAt(0).getBottom() - getScrollY() + getHeight() - getPaddingBottom();
            j = i;
            if (k < i)
              j = k; 
          } 
        } 
      } 
      if (j == 0)
        return false; 
      if (paramInt != 130)
        j = -j; 
      doScrollY(j);
    } 
    if (view2 != null && view2.isFocused() && isOffScreen(view2)) {
      paramInt = getDescendantFocusability();
      setDescendantFocusability(131072);
      requestFocus();
      setDescendantFocusability(paramInt);
    } 
    return true;
  }
  
  public int computeHorizontalScrollExtent() {
    return super.computeHorizontalScrollExtent();
  }
  
  public int computeHorizontalScrollOffset() {
    return super.computeHorizontalScrollOffset();
  }
  
  public int computeHorizontalScrollRange() {
    return super.computeHorizontalScrollRange();
  }
  
  public void computeScroll() {
    if (this.mScroller.computeScrollOffset()) {
      this.mScroller.getCurrX();
      int i = this.mScroller.getCurrY();
      int j = i - this.mLastScrollerY;
      int k = j;
      if (dispatchNestedPreScroll(0, j, this.mScrollConsumed, (int[])null, 1))
        k = j - this.mScrollConsumed[1]; 
      if (k != 0) {
        j = getScrollRange();
        int m = getScrollY();
        overScrollByCompat(0, k, getScrollX(), m, 0, j, 0, 0, false);
        int n = getScrollY() - m;
        if (!dispatchNestedScroll(0, n, 0, k - n, (int[])null, 1)) {
          k = getOverScrollMode();
          if (k == 0 || (k == 1 && j > 0)) {
            k = 1;
          } else {
            k = 0;
          } 
          if (k != 0) {
            ensureGlows();
            if (i <= 0 && m > 0) {
              this.mEdgeGlowTop.onAbsorb((int)this.mScroller.getCurrVelocity());
            } else if (i >= j && m < j) {
              this.mEdgeGlowBottom.onAbsorb((int)this.mScroller.getCurrVelocity());
            } 
          } 
        } 
      } 
      this.mLastScrollerY = i;
      ViewCompat.postInvalidateOnAnimation((View)this);
    } else {
      if (hasNestedScrollingParent(1))
        stopNestedScroll(1); 
      this.mLastScrollerY = 0;
    } 
  }
  
  protected int computeScrollDeltaToGetChildRectOnScreen(Rect paramRect) {
    int i = getChildCount();
    boolean bool = false;
    if (i == 0)
      return 0; 
    int j = getHeight();
    int k = getScrollY();
    i = k + j;
    int m = getVerticalFadingEdgeLength();
    int n = k;
    if (paramRect.top > 0)
      n = k + m; 
    k = i;
    if (paramRect.bottom < getChildAt(0).getHeight())
      k = i - m; 
    if (paramRect.bottom > k && paramRect.top > n) {
      if (paramRect.height() > j) {
        i = paramRect.top - n;
      } else {
        i = paramRect.bottom - k;
      } 
      i = Math.min(i + 0, getChildAt(0).getBottom() - k);
    } else {
      i = bool;
      if (paramRect.top < n) {
        i = bool;
        if (paramRect.bottom < k) {
          if (paramRect.height() > j) {
            i = 0 - k - paramRect.bottom;
          } else {
            i = 0 - n - paramRect.top;
          } 
          i = Math.max(i, -getScrollY());
        } 
      } 
    } 
    return i;
  }
  
  public int computeVerticalScrollExtent() {
    return super.computeVerticalScrollExtent();
  }
  
  public int computeVerticalScrollOffset() {
    return Math.max(0, super.computeVerticalScrollOffset());
  }
  
  public int computeVerticalScrollRange() {
    int i = getChildCount();
    int j = getHeight() - getPaddingBottom() - getPaddingTop();
    if (i == 0)
      return j; 
    i = getChildAt(0).getBottom();
    int k = getScrollY();
    int m = Math.max(0, i - j);
    if (k < 0) {
      j = i - k;
    } else {
      j = i;
      if (k > m)
        j = i + k - m; 
    } 
    return j;
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
    return (super.dispatchKeyEvent(paramKeyEvent) || executeKeyEvent(paramKeyEvent));
  }
  
  public boolean dispatchNestedFling(float paramFloat1, float paramFloat2, boolean paramBoolean) {
    return this.mChildHelper.dispatchNestedFling(paramFloat1, paramFloat2, paramBoolean);
  }
  
  public boolean dispatchNestedPreFling(float paramFloat1, float paramFloat2) {
    return this.mChildHelper.dispatchNestedPreFling(paramFloat1, paramFloat2);
  }
  
  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2) {
    return this.mChildHelper.dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfint1, paramArrayOfint2);
  }
  
  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt3) {
    return this.mChildHelper.dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfint1, paramArrayOfint2, paramInt3);
  }
  
  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
    return this.mChildHelper.dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint);
  }
  
  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, int paramInt5) {
    return this.mChildHelper.dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint, paramInt5);
  }
  
  public void draw(Canvas paramCanvas) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokespecial draw : (Landroid/graphics/Canvas;)V
    //   5: aload_0
    //   6: getfield mEdgeGlowTop : Landroid/widget/EdgeEffect;
    //   9: ifnull -> 395
    //   12: aload_0
    //   13: invokevirtual getScrollY : ()I
    //   16: istore_2
    //   17: aload_0
    //   18: getfield mEdgeGlowTop : Landroid/widget/EdgeEffect;
    //   21: invokevirtual isFinished : ()Z
    //   24: istore_3
    //   25: iconst_0
    //   26: istore #4
    //   28: iload_3
    //   29: ifne -> 199
    //   32: aload_1
    //   33: invokevirtual save : ()I
    //   36: istore #5
    //   38: aload_0
    //   39: invokevirtual getWidth : ()I
    //   42: istore #6
    //   44: aload_0
    //   45: invokevirtual getHeight : ()I
    //   48: istore #7
    //   50: iconst_0
    //   51: iload_2
    //   52: invokestatic min : (II)I
    //   55: istore #8
    //   57: getstatic android/os/Build$VERSION.SDK_INT : I
    //   60: bipush #21
    //   62: if_icmplt -> 81
    //   65: aload_0
    //   66: invokevirtual getClipToPadding : ()Z
    //   69: ifeq -> 75
    //   72: goto -> 81
    //   75: iconst_0
    //   76: istore #9
    //   78: goto -> 103
    //   81: iload #6
    //   83: aload_0
    //   84: invokevirtual getPaddingLeft : ()I
    //   87: aload_0
    //   88: invokevirtual getPaddingRight : ()I
    //   91: iadd
    //   92: isub
    //   93: istore #6
    //   95: aload_0
    //   96: invokevirtual getPaddingLeft : ()I
    //   99: iconst_0
    //   100: iadd
    //   101: istore #9
    //   103: iload #7
    //   105: istore #10
    //   107: iload #8
    //   109: istore #11
    //   111: getstatic android/os/Build$VERSION.SDK_INT : I
    //   114: bipush #21
    //   116: if_icmplt -> 157
    //   119: iload #7
    //   121: istore #10
    //   123: iload #8
    //   125: istore #11
    //   127: aload_0
    //   128: invokevirtual getClipToPadding : ()Z
    //   131: ifeq -> 157
    //   134: iload #7
    //   136: aload_0
    //   137: invokevirtual getPaddingTop : ()I
    //   140: aload_0
    //   141: invokevirtual getPaddingBottom : ()I
    //   144: iadd
    //   145: isub
    //   146: istore #10
    //   148: iload #8
    //   150: aload_0
    //   151: invokevirtual getPaddingTop : ()I
    //   154: iadd
    //   155: istore #11
    //   157: aload_1
    //   158: iload #9
    //   160: i2f
    //   161: iload #11
    //   163: i2f
    //   164: invokevirtual translate : (FF)V
    //   167: aload_0
    //   168: getfield mEdgeGlowTop : Landroid/widget/EdgeEffect;
    //   171: iload #6
    //   173: iload #10
    //   175: invokevirtual setSize : (II)V
    //   178: aload_0
    //   179: getfield mEdgeGlowTop : Landroid/widget/EdgeEffect;
    //   182: aload_1
    //   183: invokevirtual draw : (Landroid/graphics/Canvas;)Z
    //   186: ifeq -> 193
    //   189: aload_0
    //   190: invokestatic postInvalidateOnAnimation : (Landroid/view/View;)V
    //   193: aload_1
    //   194: iload #5
    //   196: invokevirtual restoreToCount : (I)V
    //   199: aload_0
    //   200: getfield mEdgeGlowBottom : Landroid/widget/EdgeEffect;
    //   203: invokevirtual isFinished : ()Z
    //   206: ifne -> 395
    //   209: aload_1
    //   210: invokevirtual save : ()I
    //   213: istore #5
    //   215: aload_0
    //   216: invokevirtual getWidth : ()I
    //   219: istore #11
    //   221: aload_0
    //   222: invokevirtual getHeight : ()I
    //   225: istore #7
    //   227: aload_0
    //   228: invokevirtual getScrollRange : ()I
    //   231: iload_2
    //   232: invokestatic max : (II)I
    //   235: iload #7
    //   237: iadd
    //   238: istore #8
    //   240: getstatic android/os/Build$VERSION.SDK_INT : I
    //   243: bipush #21
    //   245: if_icmplt -> 263
    //   248: iload #4
    //   250: istore #9
    //   252: iload #11
    //   254: istore #6
    //   256: aload_0
    //   257: invokevirtual getClipToPadding : ()Z
    //   260: ifeq -> 285
    //   263: iload #11
    //   265: aload_0
    //   266: invokevirtual getPaddingLeft : ()I
    //   269: aload_0
    //   270: invokevirtual getPaddingRight : ()I
    //   273: iadd
    //   274: isub
    //   275: istore #6
    //   277: iconst_0
    //   278: aload_0
    //   279: invokevirtual getPaddingLeft : ()I
    //   282: iadd
    //   283: istore #9
    //   285: iload #8
    //   287: istore #10
    //   289: iload #7
    //   291: istore #11
    //   293: getstatic android/os/Build$VERSION.SDK_INT : I
    //   296: bipush #21
    //   298: if_icmplt -> 339
    //   301: iload #8
    //   303: istore #10
    //   305: iload #7
    //   307: istore #11
    //   309: aload_0
    //   310: invokevirtual getClipToPadding : ()Z
    //   313: ifeq -> 339
    //   316: iload #7
    //   318: aload_0
    //   319: invokevirtual getPaddingTop : ()I
    //   322: aload_0
    //   323: invokevirtual getPaddingBottom : ()I
    //   326: iadd
    //   327: isub
    //   328: istore #11
    //   330: iload #8
    //   332: aload_0
    //   333: invokevirtual getPaddingBottom : ()I
    //   336: isub
    //   337: istore #10
    //   339: aload_1
    //   340: iload #9
    //   342: iload #6
    //   344: isub
    //   345: i2f
    //   346: iload #10
    //   348: i2f
    //   349: invokevirtual translate : (FF)V
    //   352: aload_1
    //   353: ldc_w 180.0
    //   356: iload #6
    //   358: i2f
    //   359: fconst_0
    //   360: invokevirtual rotate : (FFF)V
    //   363: aload_0
    //   364: getfield mEdgeGlowBottom : Landroid/widget/EdgeEffect;
    //   367: iload #6
    //   369: iload #11
    //   371: invokevirtual setSize : (II)V
    //   374: aload_0
    //   375: getfield mEdgeGlowBottom : Landroid/widget/EdgeEffect;
    //   378: aload_1
    //   379: invokevirtual draw : (Landroid/graphics/Canvas;)Z
    //   382: ifeq -> 389
    //   385: aload_0
    //   386: invokestatic postInvalidateOnAnimation : (Landroid/view/View;)V
    //   389: aload_1
    //   390: iload #5
    //   392: invokevirtual restoreToCount : (I)V
    //   395: return
  }
  
  public boolean executeKeyEvent(KeyEvent paramKeyEvent) {
    View view;
    this.mTempRect.setEmpty();
    boolean bool = canScroll();
    boolean bool1 = false;
    boolean bool2 = false;
    char c = '';
    if (!bool) {
      bool = bool2;
      if (isFocused()) {
        bool = bool2;
        if (paramKeyEvent.getKeyCode() != 4) {
          View view1 = findFocus();
          view = view1;
          if (view1 == this)
            view = null; 
          view = FocusFinder.getInstance().findNextFocus((ViewGroup)this, view, 130);
          bool = bool2;
          if (view != null) {
            bool = bool2;
            if (view != this) {
              bool = bool2;
              if (view.requestFocus(130))
                bool = true; 
            } 
          } 
        } 
      } 
      return bool;
    } 
    bool = bool1;
    if (view.getAction() == 0) {
      int i = view.getKeyCode();
      if (i != 19) {
        if (i != 20) {
          if (i != 62) {
            bool = bool1;
          } else {
            if (view.isShiftPressed())
              c = '!'; 
            pageScroll(c);
            bool = bool1;
          } 
        } else if (!view.isAltPressed()) {
          bool = arrowScroll(130);
        } else {
          bool = fullScroll(130);
        } 
      } else if (!view.isAltPressed()) {
        bool = arrowScroll(33);
      } else {
        bool = fullScroll(33);
      } 
    } 
    return bool;
  }
  
  public void fling(int paramInt) {
    if (getChildCount() > 0) {
      startNestedScroll(2, 1);
      this.mScroller.fling(getScrollX(), getScrollY(), 0, paramInt, 0, 0, -2147483648, 2147483647, 0, 0);
      this.mLastScrollerY = getScrollY();
      ViewCompat.postInvalidateOnAnimation((View)this);
    } 
  }
  
  public boolean fullScroll(int paramInt) {
    int i;
    if (paramInt == 130) {
      i = 1;
    } else {
      i = 0;
    } 
    int j = getHeight();
    this.mTempRect.top = 0;
    this.mTempRect.bottom = j;
    if (i) {
      i = getChildCount();
      if (i > 0) {
        View view = getChildAt(i - 1);
        this.mTempRect.bottom = view.getBottom() + getPaddingBottom();
        Rect rect = this.mTempRect;
        rect.top = rect.bottom - j;
      } 
    } 
    return scrollAndFocus(paramInt, this.mTempRect.top, this.mTempRect.bottom);
  }
  
  protected float getBottomFadingEdgeStrength() {
    if (getChildCount() == 0)
      return 0.0F; 
    int i = getVerticalFadingEdgeLength();
    int j = getHeight();
    int k = getPaddingBottom();
    k = getChildAt(0).getBottom() - getScrollY() - j - k;
    return (k < i) ? (k / i) : 1.0F;
  }
  
  public int getMaxScrollAmount() {
    return (int)(getHeight() * 0.5F);
  }
  
  public int getNestedScrollAxes() {
    return this.mParentHelper.getNestedScrollAxes();
  }
  
  int getScrollRange() {
    int i = getChildCount();
    int j = 0;
    if (i > 0)
      j = Math.max(0, getChildAt(0).getHeight() - getHeight() - getPaddingBottom() - getPaddingTop()); 
    return j;
  }
  
  protected float getTopFadingEdgeStrength() {
    if (getChildCount() == 0)
      return 0.0F; 
    int i = getVerticalFadingEdgeLength();
    int j = getScrollY();
    return (j < i) ? (j / i) : 1.0F;
  }
  
  public boolean hasNestedScrollingParent() {
    return this.mChildHelper.hasNestedScrollingParent();
  }
  
  public boolean hasNestedScrollingParent(int paramInt) {
    return this.mChildHelper.hasNestedScrollingParent(paramInt);
  }
  
  public boolean isFillViewport() {
    return this.mFillViewport;
  }
  
  public boolean isNestedScrollingEnabled() {
    return this.mChildHelper.isNestedScrollingEnabled();
  }
  
  public boolean isSmoothScrollingEnabled() {
    return this.mSmoothScrollingEnabled;
  }
  
  protected void measureChild(View paramView, int paramInt1, int paramInt2) {
    ViewGroup.LayoutParams layoutParams = paramView.getLayoutParams();
    paramView.measure(getChildMeasureSpec(paramInt1, getPaddingLeft() + getPaddingRight(), layoutParams.width), View.MeasureSpec.makeMeasureSpec(0, 0));
  }
  
  protected void measureChildWithMargins(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    paramView.measure(getChildMeasureSpec(paramInt1, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + paramInt2, marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec(marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, 0));
  }
  
  public void onAttachedToWindow() {
    super.onAttachedToWindow();
    this.mIsLaidOut = false;
  }
  
  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent) {
    if ((paramMotionEvent.getSource() & 0x2) != 0 && paramMotionEvent.getAction() == 8 && !this.mIsBeingDragged) {
      float f = paramMotionEvent.getAxisValue(9);
      if (f != 0.0F) {
        int i = (int)(f * getVerticalScrollFactorCompat());
        int j = getScrollRange();
        int k = getScrollY();
        i = k - i;
        if (i < 0) {
          j = 0;
        } else if (i <= j) {
          j = i;
        } 
        if (j != k) {
          super.scrollTo(getScrollX(), j);
          return true;
        } 
      } 
    } 
    return false;
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    ViewParent viewParent;
    int i = paramMotionEvent.getAction();
    if (i == 2 && this.mIsBeingDragged)
      return true; 
    i &= 0xFF;
    if (i != 0) {
      if (i != 1)
        if (i != 2) {
          if (i != 3) {
            if (i == 6)
              onSecondaryPointerUp(paramMotionEvent); 
            return this.mIsBeingDragged;
          } 
        } else {
          i = this.mActivePointerId;
          if (i != -1) {
            StringBuilder stringBuilder;
            int j = paramMotionEvent.findPointerIndex(i);
            if (j == -1) {
              stringBuilder = new StringBuilder();
              stringBuilder.append("Invalid pointerId=");
              stringBuilder.append(i);
              stringBuilder.append(" in onInterceptTouchEvent");
              Log.e("NestedScrollView", stringBuilder.toString());
            } else {
              i = (int)stringBuilder.getY(j);
              if (Math.abs(i - this.mLastMotionY) > this.mTouchSlop && (0x2 & getNestedScrollAxes()) == 0) {
                this.mIsBeingDragged = true;
                this.mLastMotionY = i;
                initVelocityTrackerIfNotExists();
                this.mVelocityTracker.addMovement((MotionEvent)stringBuilder);
                this.mNestedYOffset = 0;
                viewParent = getParent();
                if (viewParent != null)
                  viewParent.requestDisallowInterceptTouchEvent(true); 
              } 
            } 
          } 
          return this.mIsBeingDragged;
        }  
      this.mIsBeingDragged = false;
      this.mActivePointerId = -1;
      recycleVelocityTracker();
      if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange()))
        ViewCompat.postInvalidateOnAnimation((View)this); 
      stopNestedScroll(0);
    } else {
      i = (int)viewParent.getY();
      if (!inChild((int)viewParent.getX(), i)) {
        this.mIsBeingDragged = false;
        recycleVelocityTracker();
      } else {
        this.mLastMotionY = i;
        this.mActivePointerId = viewParent.getPointerId(0);
        initOrResetVelocityTracker();
        this.mVelocityTracker.addMovement((MotionEvent)viewParent);
        this.mScroller.computeScrollOffset();
        this.mIsBeingDragged = this.mScroller.isFinished() ^ true;
        startNestedScroll(2, 0);
      } 
    } 
    return this.mIsBeingDragged;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mIsLayoutDirty = false;
    View view = this.mChildToScrollTo;
    if (view != null && isViewDescendantOf(view, (View)this))
      scrollToChild(this.mChildToScrollTo); 
    this.mChildToScrollTo = null;
    if (!this.mIsLaidOut) {
      if (this.mSavedState != null) {
        scrollTo(getScrollX(), this.mSavedState.scrollPosition);
        this.mSavedState = null;
      } 
      if (getChildCount() > 0) {
        paramInt1 = getChildAt(0).getMeasuredHeight();
      } else {
        paramInt1 = 0;
      } 
      paramInt1 = Math.max(0, paramInt1 - paramInt4 - paramInt2 - getPaddingBottom() - getPaddingTop());
      if (getScrollY() > paramInt1) {
        scrollTo(getScrollX(), paramInt1);
      } else if (getScrollY() < 0) {
        scrollTo(getScrollX(), 0);
      } 
    } 
    scrollTo(getScrollX(), getScrollY());
    this.mIsLaidOut = true;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    super.onMeasure(paramInt1, paramInt2);
    if (!this.mFillViewport)
      return; 
    if (View.MeasureSpec.getMode(paramInt2) == 0)
      return; 
    if (getChildCount() > 0) {
      View view = getChildAt(0);
      paramInt2 = getMeasuredHeight();
      if (view.getMeasuredHeight() < paramInt2) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        view.measure(getChildMeasureSpec(paramInt1, getPaddingLeft() + getPaddingRight(), layoutParams.width), View.MeasureSpec.makeMeasureSpec(paramInt2 - getPaddingTop() - getPaddingBottom(), 1073741824));
      } 
    } 
  }
  
  public boolean onNestedFling(View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean) {
    if (!paramBoolean) {
      flingWithNestedDispatch((int)paramFloat2);
      return true;
    } 
    return false;
  }
  
  public boolean onNestedPreFling(View paramView, float paramFloat1, float paramFloat2) {
    return dispatchNestedPreFling(paramFloat1, paramFloat2);
  }
  
  public void onNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfint) {
    dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfint, (int[])null);
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    paramInt1 = getScrollY();
    scrollBy(0, paramInt4);
    paramInt1 = getScrollY() - paramInt1;
    dispatchNestedScroll(0, paramInt1, 0, paramInt4 - paramInt1, (int[])null);
  }
  
  public void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt) {
    this.mParentHelper.onNestedScrollAccepted(paramView1, paramView2, paramInt);
    startNestedScroll(2);
  }
  
  protected void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {
    super.scrollTo(paramInt1, paramInt2);
  }
  
  protected boolean onRequestFocusInDescendants(int paramInt, Rect paramRect) {
    int i;
    View view;
    if (paramInt == 2) {
      i = 130;
    } else {
      i = paramInt;
      if (paramInt == 1)
        i = 33; 
    } 
    if (paramRect == null) {
      view = FocusFinder.getInstance().findNextFocus((ViewGroup)this, null, i);
    } else {
      view = FocusFinder.getInstance().findNextFocusFromRect((ViewGroup)this, paramRect, i);
    } 
    return (view == null) ? false : (isOffScreen(view) ? false : view.requestFocus(i, paramRect));
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable) {
    if (!(paramParcelable instanceof SavedState)) {
      super.onRestoreInstanceState(paramParcelable);
      return;
    } 
    SavedState savedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(savedState.getSuperState());
    this.mSavedState = savedState;
    requestLayout();
  }
  
  protected Parcelable onSaveInstanceState() {
    SavedState savedState = new SavedState(super.onSaveInstanceState());
    savedState.scrollPosition = getScrollY();
    return (Parcelable)savedState;
  }
  
  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    OnScrollChangeListener onScrollChangeListener = this.mOnScrollChangeListener;
    if (onScrollChangeListener != null)
      onScrollChangeListener.onScrollChange(this, paramInt1, paramInt2, paramInt3, paramInt4); 
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    View view = findFocus();
    if (view != null && this != view && isWithinDeltaOfScreen(view, 0, paramInt4)) {
      view.getDrawingRect(this.mTempRect);
      offsetDescendantRectToMyCoords(view, this.mTempRect);
      doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
    } 
  }
  
  public boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt) {
    boolean bool;
    if ((paramInt & 0x2) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void onStopNestedScroll(View paramView) {
    this.mParentHelper.onStopNestedScroll(paramView);
    stopNestedScroll();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    initVelocityTrackerIfNotExists();
    MotionEvent motionEvent = MotionEvent.obtain(paramMotionEvent);
    int i = paramMotionEvent.getActionMasked();
    if (i == 0)
      this.mNestedYOffset = 0; 
    motionEvent.offsetLocation(0.0F, this.mNestedYOffset);
    if (i != 0) {
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i != 5) {
              if (i == 6) {
                onSecondaryPointerUp(paramMotionEvent);
                this.mLastMotionY = (int)paramMotionEvent.getY(paramMotionEvent.findPointerIndex(this.mActivePointerId));
              } 
            } else {
              i = paramMotionEvent.getActionIndex();
              this.mLastMotionY = (int)paramMotionEvent.getY(i);
              this.mActivePointerId = paramMotionEvent.getPointerId(i);
            } 
          } else {
            if (this.mIsBeingDragged && getChildCount() > 0 && this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange()))
              ViewCompat.postInvalidateOnAnimation((View)this); 
            this.mActivePointerId = -1;
            endDrag();
          } 
        } else {
          StringBuilder stringBuilder;
          int j = paramMotionEvent.findPointerIndex(this.mActivePointerId);
          if (j == -1) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid pointerId=");
            stringBuilder.append(this.mActivePointerId);
            stringBuilder.append(" in onTouchEvent");
            Log.e("NestedScrollView", stringBuilder.toString());
          } else {
            int k = (int)stringBuilder.getY(j);
            i = this.mLastMotionY - k;
            int m = i;
            if (dispatchNestedPreScroll(0, i, this.mScrollConsumed, this.mScrollOffset, 0)) {
              m = i - this.mScrollConsumed[1];
              motionEvent.offsetLocation(0.0F, this.mScrollOffset[1]);
              this.mNestedYOffset += this.mScrollOffset[1];
            } 
            i = m;
            if (!this.mIsBeingDragged) {
              i = m;
              if (Math.abs(m) > this.mTouchSlop) {
                ViewParent viewParent = getParent();
                if (viewParent != null)
                  viewParent.requestDisallowInterceptTouchEvent(true); 
                this.mIsBeingDragged = true;
                if (m > 0) {
                  i = m - this.mTouchSlop;
                } else {
                  i = m + this.mTouchSlop;
                } 
              } 
            } 
            if (this.mIsBeingDragged) {
              int[] arrayOfInt;
              this.mLastMotionY = k - this.mScrollOffset[1];
              int n = getScrollY();
              k = getScrollRange();
              m = getOverScrollMode();
              if (m == 0 || (m == 1 && k > 0)) {
                m = 1;
              } else {
                m = 0;
              } 
              if (overScrollByCompat(0, i, 0, getScrollY(), 0, k, 0, 0, true) && !hasNestedScrollingParent(0))
                this.mVelocityTracker.clear(); 
              int i1 = getScrollY() - n;
              if (dispatchNestedScroll(0, i1, 0, i - i1, this.mScrollOffset, 0)) {
                i = this.mLastMotionY;
                arrayOfInt = this.mScrollOffset;
                this.mLastMotionY = i - arrayOfInt[1];
                motionEvent.offsetLocation(0.0F, arrayOfInt[1]);
                this.mNestedYOffset += this.mScrollOffset[1];
              } else if (m != 0) {
                ensureGlows();
                m = n + i;
                if (m < 0) {
                  EdgeEffectCompat.onPull(this.mEdgeGlowTop, i / getHeight(), arrayOfInt.getX(j) / getWidth());
                  if (!this.mEdgeGlowBottom.isFinished())
                    this.mEdgeGlowBottom.onRelease(); 
                } else if (m > k) {
                  EdgeEffectCompat.onPull(this.mEdgeGlowBottom, i / getHeight(), 1.0F - arrayOfInt.getX(j) / getWidth());
                  if (!this.mEdgeGlowTop.isFinished())
                    this.mEdgeGlowTop.onRelease(); 
                } 
                EdgeEffect edgeEffect = this.mEdgeGlowTop;
                if (edgeEffect != null && (!edgeEffect.isFinished() || !this.mEdgeGlowBottom.isFinished()))
                  ViewCompat.postInvalidateOnAnimation((View)this); 
              } 
            } 
          } 
        } 
      } else {
        velocityTracker = this.mVelocityTracker;
        velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
        i = (int)velocityTracker.getYVelocity(this.mActivePointerId);
        if (Math.abs(i) > this.mMinimumVelocity) {
          flingWithNestedDispatch(-i);
        } else if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
          ViewCompat.postInvalidateOnAnimation((View)this);
        } 
        this.mActivePointerId = -1;
        endDrag();
      } 
    } else {
      if (getChildCount() == 0)
        return false; 
      int j = this.mScroller.isFinished() ^ true;
      this.mIsBeingDragged = j;
      if (j != 0) {
        ViewParent viewParent = getParent();
        if (viewParent != null)
          viewParent.requestDisallowInterceptTouchEvent(true); 
      } 
      if (!this.mScroller.isFinished())
        this.mScroller.abortAnimation(); 
      this.mLastMotionY = (int)velocityTracker.getY();
      this.mActivePointerId = velocityTracker.getPointerId(0);
      startNestedScroll(2, 0);
    } 
    VelocityTracker velocityTracker = this.mVelocityTracker;
    if (velocityTracker != null)
      velocityTracker.addMovement(motionEvent); 
    motionEvent.recycle();
    return true;
  }
  
  boolean overScrollByCompat(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getOverScrollMode : ()I
    //   4: istore #10
    //   6: aload_0
    //   7: invokevirtual computeHorizontalScrollRange : ()I
    //   10: istore #11
    //   12: aload_0
    //   13: invokevirtual computeHorizontalScrollExtent : ()I
    //   16: istore #12
    //   18: iconst_0
    //   19: istore #13
    //   21: iload #11
    //   23: iload #12
    //   25: if_icmple -> 34
    //   28: iconst_1
    //   29: istore #12
    //   31: goto -> 37
    //   34: iconst_0
    //   35: istore #12
    //   37: aload_0
    //   38: invokevirtual computeVerticalScrollRange : ()I
    //   41: aload_0
    //   42: invokevirtual computeVerticalScrollExtent : ()I
    //   45: if_icmple -> 54
    //   48: iconst_1
    //   49: istore #11
    //   51: goto -> 57
    //   54: iconst_0
    //   55: istore #11
    //   57: iload #10
    //   59: ifeq -> 82
    //   62: iload #10
    //   64: iconst_1
    //   65: if_icmpne -> 76
    //   68: iload #12
    //   70: ifeq -> 76
    //   73: goto -> 82
    //   76: iconst_0
    //   77: istore #12
    //   79: goto -> 85
    //   82: iconst_1
    //   83: istore #12
    //   85: iload #10
    //   87: ifeq -> 110
    //   90: iload #10
    //   92: iconst_1
    //   93: if_icmpne -> 104
    //   96: iload #11
    //   98: ifeq -> 104
    //   101: goto -> 110
    //   104: iconst_0
    //   105: istore #11
    //   107: goto -> 113
    //   110: iconst_1
    //   111: istore #11
    //   113: iload_3
    //   114: iload_1
    //   115: iadd
    //   116: istore_3
    //   117: iload #12
    //   119: ifne -> 127
    //   122: iconst_0
    //   123: istore_1
    //   124: goto -> 130
    //   127: iload #7
    //   129: istore_1
    //   130: iload #4
    //   132: iload_2
    //   133: iadd
    //   134: istore #4
    //   136: iload #11
    //   138: ifne -> 146
    //   141: iconst_0
    //   142: istore_2
    //   143: goto -> 149
    //   146: iload #8
    //   148: istore_2
    //   149: iload_1
    //   150: ineg
    //   151: istore #7
    //   153: iload_1
    //   154: iload #5
    //   156: iadd
    //   157: istore_1
    //   158: iload_2
    //   159: ineg
    //   160: istore #5
    //   162: iload_2
    //   163: iload #6
    //   165: iadd
    //   166: istore #6
    //   168: iload_3
    //   169: iload_1
    //   170: if_icmple -> 181
    //   173: iconst_1
    //   174: istore #9
    //   176: iload_1
    //   177: istore_2
    //   178: goto -> 198
    //   181: iload_3
    //   182: iload #7
    //   184: if_icmpge -> 193
    //   187: iload #7
    //   189: istore_1
    //   190: goto -> 173
    //   193: iconst_0
    //   194: istore #9
    //   196: iload_3
    //   197: istore_2
    //   198: iload #4
    //   200: iload #6
    //   202: if_icmple -> 214
    //   205: iload #6
    //   207: istore_1
    //   208: iconst_1
    //   209: istore #14
    //   211: goto -> 233
    //   214: iload #4
    //   216: iload #5
    //   218: if_icmpge -> 227
    //   221: iload #5
    //   223: istore_1
    //   224: goto -> 208
    //   227: iconst_0
    //   228: istore #14
    //   230: iload #4
    //   232: istore_1
    //   233: iload #14
    //   235: ifeq -> 263
    //   238: aload_0
    //   239: iconst_1
    //   240: invokevirtual hasNestedScrollingParent : (I)Z
    //   243: ifne -> 263
    //   246: aload_0
    //   247: getfield mScroller : Landroid/widget/OverScroller;
    //   250: iload_2
    //   251: iload_1
    //   252: iconst_0
    //   253: iconst_0
    //   254: iconst_0
    //   255: aload_0
    //   256: invokevirtual getScrollRange : ()I
    //   259: invokevirtual springBack : (IIIIII)Z
    //   262: pop
    //   263: aload_0
    //   264: iload_2
    //   265: iload_1
    //   266: iload #9
    //   268: iload #14
    //   270: invokevirtual onOverScrolled : (IIZZ)V
    //   273: iload #9
    //   275: ifne -> 287
    //   278: iload #13
    //   280: istore #9
    //   282: iload #14
    //   284: ifeq -> 290
    //   287: iconst_1
    //   288: istore #9
    //   290: iload #9
    //   292: ireturn
  }
  
  public boolean pageScroll(int paramInt) {
    int i;
    if (paramInt == 130) {
      i = 1;
    } else {
      i = 0;
    } 
    int j = getHeight();
    if (i) {
      this.mTempRect.top = getScrollY() + j;
      i = getChildCount();
      if (i > 0) {
        View view = getChildAt(i - 1);
        if (this.mTempRect.top + j > view.getBottom())
          this.mTempRect.top = view.getBottom() - j; 
      } 
    } else {
      this.mTempRect.top = getScrollY() - j;
      if (this.mTempRect.top < 0)
        this.mTempRect.top = 0; 
    } 
    Rect rect = this.mTempRect;
    rect.bottom = rect.top + j;
    return scrollAndFocus(paramInt, this.mTempRect.top, this.mTempRect.bottom);
  }
  
  public void requestChildFocus(View paramView1, View paramView2) {
    if (!this.mIsLayoutDirty) {
      scrollToChild(paramView2);
    } else {
      this.mChildToScrollTo = paramView2;
    } 
    super.requestChildFocus(paramView1, paramView2);
  }
  
  public boolean requestChildRectangleOnScreen(View paramView, Rect paramRect, boolean paramBoolean) {
    paramRect.offset(paramView.getLeft() - paramView.getScrollX(), paramView.getTop() - paramView.getScrollY());
    return scrollToChildRect(paramRect, paramBoolean);
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean) {
    if (paramBoolean)
      recycleVelocityTracker(); 
    super.requestDisallowInterceptTouchEvent(paramBoolean);
  }
  
  public void requestLayout() {
    this.mIsLayoutDirty = true;
    super.requestLayout();
  }
  
  public void scrollTo(int paramInt1, int paramInt2) {
    if (getChildCount() > 0) {
      View view = getChildAt(0);
      paramInt1 = clamp(paramInt1, getWidth() - getPaddingRight() - getPaddingLeft(), view.getWidth());
      paramInt2 = clamp(paramInt2, getHeight() - getPaddingBottom() - getPaddingTop(), view.getHeight());
      if (paramInt1 != getScrollX() || paramInt2 != getScrollY())
        super.scrollTo(paramInt1, paramInt2); 
    } 
  }
  
  public void setFillViewport(boolean paramBoolean) {
    if (paramBoolean != this.mFillViewport) {
      this.mFillViewport = paramBoolean;
      requestLayout();
    } 
  }
  
  public void setNestedScrollingEnabled(boolean paramBoolean) {
    this.mChildHelper.setNestedScrollingEnabled(paramBoolean);
  }
  
  public void setOnScrollChangeListener(OnScrollChangeListener paramOnScrollChangeListener) {
    this.mOnScrollChangeListener = paramOnScrollChangeListener;
  }
  
  public void setSmoothScrollingEnabled(boolean paramBoolean) {
    this.mSmoothScrollingEnabled = paramBoolean;
  }
  
  public boolean shouldDelayChildPressedState() {
    return true;
  }
  
  public final void smoothScrollBy(int paramInt1, int paramInt2) {
    if (getChildCount() == 0)
      return; 
    if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250L) {
      paramInt1 = getHeight();
      int i = getPaddingBottom();
      int j = getPaddingTop();
      i = Math.max(0, getChildAt(0).getHeight() - paramInt1 - i - j);
      paramInt1 = getScrollY();
      paramInt2 = Math.max(0, Math.min(paramInt2 + paramInt1, i));
      this.mScroller.startScroll(getScrollX(), paramInt1, 0, paramInt2 - paramInt1);
      ViewCompat.postInvalidateOnAnimation((View)this);
    } else {
      if (!this.mScroller.isFinished())
        this.mScroller.abortAnimation(); 
      scrollBy(paramInt1, paramInt2);
    } 
    this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
  }
  
  public final void smoothScrollTo(int paramInt1, int paramInt2) {
    smoothScrollBy(paramInt1 - getScrollX(), paramInt2 - getScrollY());
  }
  
  public boolean startNestedScroll(int paramInt) {
    return this.mChildHelper.startNestedScroll(paramInt);
  }
  
  public boolean startNestedScroll(int paramInt1, int paramInt2) {
    return this.mChildHelper.startNestedScroll(paramInt1, paramInt2);
  }
  
  public void stopNestedScroll() {
    this.mChildHelper.stopNestedScroll();
  }
  
  public void stopNestedScroll(int paramInt) {
    this.mChildHelper.stopNestedScroll(paramInt);
  }
  
  static class AccessibilityDelegate extends AccessibilityDelegateCompat {
    public void onInitializeAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      boolean bool;
      super.onInitializeAccessibilityEvent(param1View, param1AccessibilityEvent);
      NestedScrollView nestedScrollView = (NestedScrollView)param1View;
      param1AccessibilityEvent.setClassName(ScrollView.class.getName());
      if (nestedScrollView.getScrollRange() > 0) {
        bool = true;
      } else {
        bool = false;
      } 
      param1AccessibilityEvent.setScrollable(bool);
      param1AccessibilityEvent.setScrollX(nestedScrollView.getScrollX());
      param1AccessibilityEvent.setScrollY(nestedScrollView.getScrollY());
      AccessibilityRecordCompat.setMaxScrollX((AccessibilityRecord)param1AccessibilityEvent, nestedScrollView.getScrollX());
      AccessibilityRecordCompat.setMaxScrollY((AccessibilityRecord)param1AccessibilityEvent, nestedScrollView.getScrollRange());
    }
    
    public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
      super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfoCompat);
      NestedScrollView nestedScrollView = (NestedScrollView)param1View;
      param1AccessibilityNodeInfoCompat.setClassName(ScrollView.class.getName());
      if (nestedScrollView.isEnabled()) {
        int i = nestedScrollView.getScrollRange();
        if (i > 0) {
          param1AccessibilityNodeInfoCompat.setScrollable(true);
          if (nestedScrollView.getScrollY() > 0)
            param1AccessibilityNodeInfoCompat.addAction(8192); 
          if (nestedScrollView.getScrollY() < i)
            param1AccessibilityNodeInfoCompat.addAction(4096); 
        } 
      } 
    }
    
    public boolean performAccessibilityAction(View param1View, int param1Int, Bundle param1Bundle) {
      if (super.performAccessibilityAction(param1View, param1Int, param1Bundle))
        return true; 
      NestedScrollView nestedScrollView = (NestedScrollView)param1View;
      if (!nestedScrollView.isEnabled())
        return false; 
      if (param1Int != 4096) {
        if (param1Int != 8192)
          return false; 
        param1Int = nestedScrollView.getHeight();
        int k = nestedScrollView.getPaddingBottom();
        int m = nestedScrollView.getPaddingTop();
        param1Int = Math.max(nestedScrollView.getScrollY() - param1Int - k - m, 0);
        if (param1Int != nestedScrollView.getScrollY()) {
          nestedScrollView.smoothScrollTo(0, param1Int);
          return true;
        } 
        return false;
      } 
      param1Int = nestedScrollView.getHeight();
      int j = nestedScrollView.getPaddingBottom();
      int i = nestedScrollView.getPaddingTop();
      param1Int = Math.min(nestedScrollView.getScrollY() + param1Int - j - i, nestedScrollView.getScrollRange());
      if (param1Int != nestedScrollView.getScrollY()) {
        nestedScrollView.smoothScrollTo(0, param1Int);
        return true;
      } 
      return false;
    }
  }
  
  public static interface OnScrollChangeListener {
    void onScrollChange(NestedScrollView param1NestedScrollView, int param1Int1, int param1Int2, int param1Int3, int param1Int4);
  }
  
  static class SavedState extends View.BaseSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
        public NestedScrollView.SavedState createFromParcel(Parcel param2Parcel) {
          return new NestedScrollView.SavedState(param2Parcel);
        }
        
        public NestedScrollView.SavedState[] newArray(int param2Int) {
          return new NestedScrollView.SavedState[param2Int];
        }
      };
    
    public int scrollPosition;
    
    SavedState(Parcel param1Parcel) {
      super(param1Parcel);
      this.scrollPosition = param1Parcel.readInt();
    }
    
    SavedState(Parcelable param1Parcelable) {
      super(param1Parcelable);
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("HorizontalScrollView.SavedState{");
      stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
      stringBuilder.append(" scrollPosition=");
      stringBuilder.append(this.scrollPosition);
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      super.writeToParcel(param1Parcel, param1Int);
      param1Parcel.writeInt(this.scrollPosition);
    }
  }
  
  static final class null implements Parcelable.Creator<SavedState> {
    public NestedScrollView.SavedState createFromParcel(Parcel param1Parcel) {
      return new NestedScrollView.SavedState(param1Parcel);
    }
    
    public NestedScrollView.SavedState[] newArray(int param1Int) {
      return new NestedScrollView.SavedState[param1Int];
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\NestedScrollView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */