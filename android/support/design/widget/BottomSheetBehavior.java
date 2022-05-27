package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.R;
import android.support.v4.math.MathUtils;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

public class BottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
  private static final float HIDE_FRICTION = 0.1F;
  
  private static final float HIDE_THRESHOLD = 0.5F;
  
  public static final int PEEK_HEIGHT_AUTO = -1;
  
  public static final int STATE_COLLAPSED = 4;
  
  public static final int STATE_DRAGGING = 1;
  
  public static final int STATE_EXPANDED = 3;
  
  public static final int STATE_HIDDEN = 5;
  
  public static final int STATE_SETTLING = 2;
  
  int mActivePointerId;
  
  private BottomSheetCallback mCallback;
  
  private final ViewDragHelper.Callback mDragCallback = new ViewDragHelper.Callback() {
      public int clampViewPositionHorizontal(View param1View, int param1Int1, int param1Int2) {
        return param1View.getLeft();
      }
      
      public int clampViewPositionVertical(View param1View, int param1Int1, int param1Int2) {
        int i = BottomSheetBehavior.this.mMinOffset;
        if (BottomSheetBehavior.this.mHideable) {
          param1Int2 = BottomSheetBehavior.this.mParentHeight;
        } else {
          param1Int2 = BottomSheetBehavior.this.mMaxOffset;
        } 
        return MathUtils.clamp(param1Int1, i, param1Int2);
      }
      
      public int getViewVerticalDragRange(View param1View) {
        if (BottomSheetBehavior.this.mHideable) {
          int k = BottomSheetBehavior.this.mParentHeight;
          int m = BottomSheetBehavior.this.mMinOffset;
          return k - m;
        } 
        int i = BottomSheetBehavior.this.mMaxOffset;
        int j = BottomSheetBehavior.this.mMinOffset;
        return i - j;
      }
      
      public void onViewDragStateChanged(int param1Int) {
        if (param1Int == 1)
          BottomSheetBehavior.this.setStateInternal(1); 
      }
      
      public void onViewPositionChanged(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
        BottomSheetBehavior.this.dispatchOnSlide(param1Int2);
      }
      
      public void onViewReleased(View param1View, float param1Float1, float param1Float2) {
        int i;
        byte b = 3;
        if (param1Float2 < 0.0F) {
          i = BottomSheetBehavior.this.mMinOffset;
        } else if (BottomSheetBehavior.this.mHideable && BottomSheetBehavior.this.shouldHide(param1View, param1Float2)) {
          i = BottomSheetBehavior.this.mParentHeight;
          b = 5;
        } else if (param1Float2 == 0.0F) {
          i = param1View.getTop();
          if (Math.abs(i - BottomSheetBehavior.this.mMinOffset) < Math.abs(i - BottomSheetBehavior.this.mMaxOffset)) {
            i = BottomSheetBehavior.this.mMinOffset;
          } else {
            i = BottomSheetBehavior.this.mMaxOffset;
            b = 4;
          } 
        } else {
          i = BottomSheetBehavior.this.mMaxOffset;
          b = 4;
        } 
        if (BottomSheetBehavior.this.mViewDragHelper.settleCapturedViewAt(param1View.getLeft(), i)) {
          BottomSheetBehavior.this.setStateInternal(2);
          ViewCompat.postOnAnimation(param1View, new BottomSheetBehavior.SettleRunnable(param1View, b));
        } else {
          BottomSheetBehavior.this.setStateInternal(b);
        } 
      }
      
      public boolean tryCaptureView(View param1View, int param1Int) {
        int i = BottomSheetBehavior.this.mState;
        boolean bool = true;
        if (i == 1)
          return false; 
        if (BottomSheetBehavior.this.mTouchingScrollingChild)
          return false; 
        if (BottomSheetBehavior.this.mState == 3 && BottomSheetBehavior.this.mActivePointerId == param1Int) {
          View view = BottomSheetBehavior.this.mNestedScrollingChildRef.get();
          if (view != null && view.canScrollVertically(-1))
            return false; 
        } 
        if (BottomSheetBehavior.this.mViewRef == null || BottomSheetBehavior.this.mViewRef.get() != param1View)
          bool = false; 
        return bool;
      }
    };
  
  boolean mHideable;
  
  private boolean mIgnoreEvents;
  
  private int mInitialY;
  
  private int mLastNestedScrollDy;
  
  int mMaxOffset;
  
  private float mMaximumVelocity;
  
  int mMinOffset;
  
  private boolean mNestedScrolled;
  
  WeakReference<View> mNestedScrollingChildRef;
  
  int mParentHeight;
  
  private int mPeekHeight;
  
  private boolean mPeekHeightAuto;
  
  private int mPeekHeightMin;
  
  private boolean mSkipCollapsed;
  
  int mState = 4;
  
  boolean mTouchingScrollingChild;
  
  private VelocityTracker mVelocityTracker;
  
  ViewDragHelper mViewDragHelper;
  
  WeakReference<V> mViewRef;
  
  public BottomSheetBehavior() {}
  
  public BottomSheetBehavior(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.BottomSheetBehavior_Layout);
    TypedValue typedValue = typedArray.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
    if (typedValue != null && typedValue.data == -1) {
      setPeekHeight(typedValue.data);
    } else {
      setPeekHeight(typedArray.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
    } 
    setHideable(typedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
    setSkipCollapsed(typedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
    typedArray.recycle();
    this.mMaximumVelocity = ViewConfiguration.get(paramContext).getScaledMaximumFlingVelocity();
  }
  
  public static <V extends View> BottomSheetBehavior<V> from(V paramV) {
    ViewGroup.LayoutParams layoutParams = paramV.getLayoutParams();
    if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
      CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams)layoutParams).getBehavior();
      if (behavior instanceof BottomSheetBehavior)
        return (BottomSheetBehavior<V>)behavior; 
      throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
    } 
    throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
  }
  
  private float getYVelocity() {
    this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
    return this.mVelocityTracker.getYVelocity(this.mActivePointerId);
  }
  
  private void reset() {
    this.mActivePointerId = -1;
    VelocityTracker velocityTracker = this.mVelocityTracker;
    if (velocityTracker != null) {
      velocityTracker.recycle();
      this.mVelocityTracker = null;
    } 
  }
  
  void dispatchOnSlide(int paramInt) {
    View view = (View)this.mViewRef.get();
    if (view != null) {
      BottomSheetCallback bottomSheetCallback = this.mCallback;
      if (bottomSheetCallback != null) {
        int i = this.mMaxOffset;
        if (paramInt > i) {
          bottomSheetCallback.onSlide(view, (i - paramInt) / (this.mParentHeight - i));
        } else {
          bottomSheetCallback.onSlide(view, (i - paramInt) / (i - this.mMinOffset));
        } 
      } 
    } 
  }
  
  View findScrollingChild(View paramView) {
    if (ViewCompat.isNestedScrollingEnabled(paramView))
      return paramView; 
    if (paramView instanceof ViewGroup) {
      ViewGroup viewGroup = (ViewGroup)paramView;
      byte b = 0;
      int i = viewGroup.getChildCount();
      while (b < i) {
        View view = findScrollingChild(viewGroup.getChildAt(b));
        if (view != null)
          return view; 
        b++;
      } 
    } 
    return null;
  }
  
  public final int getPeekHeight() {
    int i;
    if (this.mPeekHeightAuto) {
      i = -1;
    } else {
      i = this.mPeekHeight;
    } 
    return i;
  }
  
  int getPeekHeightMin() {
    return this.mPeekHeightMin;
  }
  
  public boolean getSkipCollapsed() {
    return this.mSkipCollapsed;
  }
  
  public final int getState() {
    return this.mState;
  }
  
  public boolean isHideable() {
    return this.mHideable;
  }
  
  public boolean onInterceptTouchEvent(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent) {
    boolean bool = paramV.isShown();
    boolean bool1 = false;
    if (!bool) {
      this.mIgnoreEvents = true;
      return false;
    } 
    int i = paramMotionEvent.getActionMasked();
    if (i == 0)
      reset(); 
    if (this.mVelocityTracker == null)
      this.mVelocityTracker = VelocityTracker.obtain(); 
    this.mVelocityTracker.addMovement(paramMotionEvent);
    if (i != 0) {
      if (i == 1 || i == 3) {
        this.mTouchingScrollingChild = false;
        this.mActivePointerId = -1;
        if (this.mIgnoreEvents) {
          this.mIgnoreEvents = false;
          return false;
        } 
      } 
    } else {
      int j = (int)paramMotionEvent.getX();
      this.mInitialY = (int)paramMotionEvent.getY();
      WeakReference<View> weakReference = this.mNestedScrollingChildRef;
      if (weakReference != null) {
        View view1 = weakReference.get();
      } else {
        weakReference = null;
      } 
      if (weakReference != null && paramCoordinatorLayout.isPointInChildBounds((View)weakReference, j, this.mInitialY)) {
        this.mActivePointerId = paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex());
        this.mTouchingScrollingChild = true;
      } 
      if (this.mActivePointerId == -1 && !paramCoordinatorLayout.isPointInChildBounds((View)paramV, j, this.mInitialY)) {
        bool = true;
      } else {
        bool = false;
      } 
      this.mIgnoreEvents = bool;
    } 
    if (!this.mIgnoreEvents && this.mViewDragHelper.shouldInterceptTouchEvent(paramMotionEvent))
      return true; 
    View view = this.mNestedScrollingChildRef.get();
    bool = bool1;
    if (i == 2) {
      bool = bool1;
      if (view != null) {
        bool = bool1;
        if (!this.mIgnoreEvents) {
          bool = bool1;
          if (this.mState != 1) {
            bool = bool1;
            if (!paramCoordinatorLayout.isPointInChildBounds(view, (int)paramMotionEvent.getX(), (int)paramMotionEvent.getY())) {
              bool = bool1;
              if (Math.abs(this.mInitialY - paramMotionEvent.getY()) > this.mViewDragHelper.getTouchSlop())
                bool = true; 
            } 
          } 
        } 
      } 
    } 
    return bool;
  }
  
  public boolean onLayoutChild(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt) {
    if (ViewCompat.getFitsSystemWindows((View)paramCoordinatorLayout) && !ViewCompat.getFitsSystemWindows((View)paramV))
      ViewCompat.setFitsSystemWindows((View)paramV, true); 
    int i = paramV.getTop();
    paramCoordinatorLayout.onLayoutChild((View)paramV, paramInt);
    this.mParentHeight = paramCoordinatorLayout.getHeight();
    if (this.mPeekHeightAuto) {
      if (this.mPeekHeightMin == 0)
        this.mPeekHeightMin = paramCoordinatorLayout.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min); 
      paramInt = Math.max(this.mPeekHeightMin, this.mParentHeight - paramCoordinatorLayout.getWidth() * 9 / 16);
    } else {
      paramInt = this.mPeekHeight;
    } 
    int j = Math.max(0, this.mParentHeight - paramV.getHeight());
    this.mMinOffset = j;
    this.mMaxOffset = Math.max(this.mParentHeight - paramInt, j);
    paramInt = this.mState;
    if (paramInt == 3) {
      ViewCompat.offsetTopAndBottom((View)paramV, this.mMinOffset);
    } else if (this.mHideable && paramInt == 5) {
      ViewCompat.offsetTopAndBottom((View)paramV, this.mParentHeight);
    } else {
      paramInt = this.mState;
      if (paramInt == 4) {
        ViewCompat.offsetTopAndBottom((View)paramV, this.mMaxOffset);
      } else if (paramInt == 1 || paramInt == 2) {
        ViewCompat.offsetTopAndBottom((View)paramV, i - paramV.getTop());
      } 
    } 
    if (this.mViewDragHelper == null)
      this.mViewDragHelper = ViewDragHelper.create(paramCoordinatorLayout, this.mDragCallback); 
    this.mViewRef = new WeakReference<V>(paramV);
    this.mNestedScrollingChildRef = new WeakReference<View>(findScrollingChild((View)paramV));
    return true;
  }
  
  public boolean onNestedPreFling(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, float paramFloat1, float paramFloat2) {
    boolean bool;
    if (paramView == this.mNestedScrollingChildRef.get() && (this.mState != 3 || super.onNestedPreFling(paramCoordinatorLayout, paramV, paramView, paramFloat1, paramFloat2))) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void onNestedPreScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, int paramInt1, int paramInt2, int[] paramArrayOfint) {
    if (paramView != (View)this.mNestedScrollingChildRef.get())
      return; 
    int i = paramV.getTop();
    paramInt1 = i - paramInt2;
    if (paramInt2 > 0) {
      int j = this.mMinOffset;
      if (paramInt1 < j) {
        paramArrayOfint[1] = i - j;
        ViewCompat.offsetTopAndBottom((View)paramV, -paramArrayOfint[1]);
        setStateInternal(3);
      } else {
        paramArrayOfint[1] = paramInt2;
        ViewCompat.offsetTopAndBottom((View)paramV, -paramInt2);
        setStateInternal(1);
      } 
    } else if (paramInt2 < 0 && !paramView.canScrollVertically(-1)) {
      int j = this.mMaxOffset;
      if (paramInt1 <= j || this.mHideable) {
        paramArrayOfint[1] = paramInt2;
        ViewCompat.offsetTopAndBottom((View)paramV, -paramInt2);
        setStateInternal(1);
      } else {
        paramArrayOfint[1] = i - j;
        ViewCompat.offsetTopAndBottom((View)paramV, -paramArrayOfint[1]);
        setStateInternal(4);
      } 
    } 
    dispatchOnSlide(paramV.getTop());
    this.mLastNestedScrollDy = paramInt2;
    this.mNestedScrolled = true;
  }
  
  public void onRestoreInstanceState(CoordinatorLayout paramCoordinatorLayout, V paramV, Parcelable paramParcelable) {
    SavedState savedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramCoordinatorLayout, paramV, savedState.getSuperState());
    if (savedState.state == 1 || savedState.state == 2) {
      this.mState = 4;
      return;
    } 
    this.mState = savedState.state;
  }
  
  public Parcelable onSaveInstanceState(CoordinatorLayout paramCoordinatorLayout, V paramV) {
    return (Parcelable)new SavedState(super.onSaveInstanceState(paramCoordinatorLayout, paramV), this.mState);
  }
  
  public boolean onStartNestedScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView1, View paramView2, int paramInt) {
    boolean bool = false;
    this.mLastNestedScrollDy = 0;
    this.mNestedScrolled = false;
    if ((paramInt & 0x2) != 0)
      bool = true; 
    return bool;
  }
  
  public void onStopNestedScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView) {
    int i = paramV.getTop();
    int j = this.mMinOffset;
    byte b = 3;
    if (i == j) {
      setStateInternal(3);
      return;
    } 
    WeakReference<View> weakReference = this.mNestedScrollingChildRef;
    if (weakReference != null && paramView == weakReference.get() && this.mNestedScrolled) {
      if (this.mLastNestedScrollDy > 0) {
        j = this.mMinOffset;
      } else if (this.mHideable && shouldHide((View)paramV, getYVelocity())) {
        j = this.mParentHeight;
        b = 5;
      } else if (this.mLastNestedScrollDy == 0) {
        j = paramV.getTop();
        if (Math.abs(j - this.mMinOffset) < Math.abs(j - this.mMaxOffset)) {
          j = this.mMinOffset;
        } else {
          j = this.mMaxOffset;
          b = 4;
        } 
      } else {
        j = this.mMaxOffset;
        b = 4;
      } 
      if (this.mViewDragHelper.smoothSlideViewTo((View)paramV, paramV.getLeft(), j)) {
        setStateInternal(2);
        ViewCompat.postOnAnimation((View)paramV, new SettleRunnable((View)paramV, b));
      } else {
        setStateInternal(b);
      } 
      this.mNestedScrolled = false;
    } 
  }
  
  public boolean onTouchEvent(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent) {
    if (!paramV.isShown())
      return false; 
    int i = paramMotionEvent.getActionMasked();
    if (this.mState == 1 && i == 0)
      return true; 
    ViewDragHelper viewDragHelper = this.mViewDragHelper;
    if (viewDragHelper != null)
      viewDragHelper.processTouchEvent(paramMotionEvent); 
    if (i == 0)
      reset(); 
    if (this.mVelocityTracker == null)
      this.mVelocityTracker = VelocityTracker.obtain(); 
    this.mVelocityTracker.addMovement(paramMotionEvent);
    if (i == 2 && !this.mIgnoreEvents && Math.abs(this.mInitialY - paramMotionEvent.getY()) > this.mViewDragHelper.getTouchSlop())
      this.mViewDragHelper.captureChildView((View)paramV, paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex())); 
    return this.mIgnoreEvents ^ true;
  }
  
  public void setBottomSheetCallback(BottomSheetCallback paramBottomSheetCallback) {
    this.mCallback = paramBottomSheetCallback;
  }
  
  public void setHideable(boolean paramBoolean) {
    this.mHideable = paramBoolean;
  }
  
  public final void setPeekHeight(int paramInt) {
    // Byte code:
    //   0: iconst_1
    //   1: istore_2
    //   2: iload_1
    //   3: iconst_m1
    //   4: if_icmpne -> 24
    //   7: aload_0
    //   8: getfield mPeekHeightAuto : Z
    //   11: ifne -> 42
    //   14: aload_0
    //   15: iconst_1
    //   16: putfield mPeekHeightAuto : Z
    //   19: iload_2
    //   20: istore_1
    //   21: goto -> 73
    //   24: aload_0
    //   25: getfield mPeekHeightAuto : Z
    //   28: ifne -> 47
    //   31: aload_0
    //   32: getfield mPeekHeight : I
    //   35: iload_1
    //   36: if_icmpeq -> 42
    //   39: goto -> 47
    //   42: iconst_0
    //   43: istore_1
    //   44: goto -> 73
    //   47: aload_0
    //   48: iconst_0
    //   49: putfield mPeekHeightAuto : Z
    //   52: aload_0
    //   53: iconst_0
    //   54: iload_1
    //   55: invokestatic max : (II)I
    //   58: putfield mPeekHeight : I
    //   61: aload_0
    //   62: aload_0
    //   63: getfield mParentHeight : I
    //   66: iload_1
    //   67: isub
    //   68: putfield mMaxOffset : I
    //   71: iload_2
    //   72: istore_1
    //   73: iload_1
    //   74: ifeq -> 110
    //   77: aload_0
    //   78: getfield mState : I
    //   81: iconst_4
    //   82: if_icmpne -> 110
    //   85: aload_0
    //   86: getfield mViewRef : Ljava/lang/ref/WeakReference;
    //   89: astore_3
    //   90: aload_3
    //   91: ifnull -> 110
    //   94: aload_3
    //   95: invokevirtual get : ()Ljava/lang/Object;
    //   98: checkcast android/view/View
    //   101: astore_3
    //   102: aload_3
    //   103: ifnull -> 110
    //   106: aload_3
    //   107: invokevirtual requestLayout : ()V
    //   110: return
  }
  
  public void setSkipCollapsed(boolean paramBoolean) {
    this.mSkipCollapsed = paramBoolean;
  }
  
  public final void setState(final int state) {
    if (state == this.mState)
      return; 
    WeakReference<V> weakReference = this.mViewRef;
    if (weakReference == null) {
      if (state == 4 || state == 3 || (this.mHideable && state == 5))
        this.mState = state; 
      return;
    } 
    final View child = (View)weakReference.get();
    if (view == null)
      return; 
    ViewParent viewParent = view.getParent();
    if (viewParent != null && viewParent.isLayoutRequested() && ViewCompat.isAttachedToWindow(view)) {
      view.post(new Runnable() {
            public void run() {
              BottomSheetBehavior.this.startSettlingAnimation(child, state);
            }
          });
    } else {
      startSettlingAnimation(view, state);
    } 
  }
  
  void setStateInternal(int paramInt) {
    if (this.mState == paramInt)
      return; 
    this.mState = paramInt;
    View view = (View)this.mViewRef.get();
    if (view != null) {
      BottomSheetCallback bottomSheetCallback = this.mCallback;
      if (bottomSheetCallback != null)
        bottomSheetCallback.onStateChanged(view, paramInt); 
    } 
  }
  
  boolean shouldHide(View paramView, float paramFloat) {
    boolean bool = this.mSkipCollapsed;
    boolean bool1 = true;
    if (bool)
      return true; 
    if (paramView.getTop() < this.mMaxOffset)
      return false; 
    if (Math.abs(paramView.getTop() + paramFloat * 0.1F - this.mMaxOffset) / this.mPeekHeight <= 0.5F)
      bool1 = false; 
    return bool1;
  }
  
  void startSettlingAnimation(View paramView, int paramInt) {
    StringBuilder stringBuilder;
    int i;
    if (paramInt == 4) {
      i = this.mMaxOffset;
    } else if (paramInt == 3) {
      i = this.mMinOffset;
    } else if (this.mHideable && paramInt == 5) {
      i = this.mParentHeight;
    } else {
      stringBuilder = new StringBuilder();
      stringBuilder.append("Illegal state argument: ");
      stringBuilder.append(paramInt);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    if (this.mViewDragHelper.smoothSlideViewTo((View)stringBuilder, stringBuilder.getLeft(), i)) {
      setStateInternal(2);
      ViewCompat.postOnAnimation((View)stringBuilder, new SettleRunnable((View)stringBuilder, paramInt));
    } else {
      setStateInternal(paramInt);
    } 
  }
  
  public static abstract class BottomSheetCallback {
    public abstract void onSlide(View param1View, float param1Float);
    
    public abstract void onStateChanged(View param1View, int param1Int);
  }
  
  protected static class SavedState extends AbsSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR = (Parcelable.Creator<SavedState>)new Parcelable.ClassLoaderCreator<SavedState>() {
        public BottomSheetBehavior.SavedState createFromParcel(Parcel param2Parcel) {
          return new BottomSheetBehavior.SavedState(param2Parcel, null);
        }
        
        public BottomSheetBehavior.SavedState createFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
          return new BottomSheetBehavior.SavedState(param2Parcel, param2ClassLoader);
        }
        
        public BottomSheetBehavior.SavedState[] newArray(int param2Int) {
          return new BottomSheetBehavior.SavedState[param2Int];
        }
      };
    
    final int state;
    
    public SavedState(Parcel param1Parcel) {
      this(param1Parcel, (ClassLoader)null);
    }
    
    public SavedState(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      super(param1Parcel, param1ClassLoader);
      this.state = param1Parcel.readInt();
    }
    
    public SavedState(Parcelable param1Parcelable, int param1Int) {
      super(param1Parcelable);
      this.state = param1Int;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      super.writeToParcel(param1Parcel, param1Int);
      param1Parcel.writeInt(this.state);
    }
  }
  
  static final class null implements Parcelable.ClassLoaderCreator<SavedState> {
    public BottomSheetBehavior.SavedState createFromParcel(Parcel param1Parcel) {
      return new BottomSheetBehavior.SavedState(param1Parcel, null);
    }
    
    public BottomSheetBehavior.SavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return new BottomSheetBehavior.SavedState(param1Parcel, param1ClassLoader);
    }
    
    public BottomSheetBehavior.SavedState[] newArray(int param1Int) {
      return new BottomSheetBehavior.SavedState[param1Int];
    }
  }
  
  private class SettleRunnable implements Runnable {
    private final int mTargetState;
    
    private final View mView;
    
    SettleRunnable(View param1View, int param1Int) {
      this.mView = param1View;
      this.mTargetState = param1Int;
    }
    
    public void run() {
      if (BottomSheetBehavior.this.mViewDragHelper != null && BottomSheetBehavior.this.mViewDragHelper.continueSettling(true)) {
        ViewCompat.postOnAnimation(this.mView, this);
      } else {
        BottomSheetBehavior.this.setStateInternal(this.mTargetState);
      } 
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface State {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\BottomSheetBehavior.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */