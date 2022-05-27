package android.support.design.widget;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.R;
import android.support.v4.math.MathUtils;
import android.support.v4.util.ObjectsCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@DefaultBehavior(AppBarLayout.Behavior.class)
public class AppBarLayout extends LinearLayout {
  private static final int INVALID_SCROLL_RANGE = -1;
  
  static final int PENDING_ACTION_ANIMATE_ENABLED = 4;
  
  static final int PENDING_ACTION_COLLAPSED = 2;
  
  static final int PENDING_ACTION_EXPANDED = 1;
  
  static final int PENDING_ACTION_FORCE = 8;
  
  static final int PENDING_ACTION_NONE = 0;
  
  private boolean mCollapsed;
  
  private boolean mCollapsible;
  
  private int mDownPreScrollRange = -1;
  
  private int mDownScrollRange = -1;
  
  private boolean mHaveChildWithInterpolator;
  
  private WindowInsetsCompat mLastInsets;
  
  private List<OnOffsetChangedListener> mListeners;
  
  private int mPendingAction = 0;
  
  private int[] mTmpStatesArray;
  
  private int mTotalScrollRange = -1;
  
  public AppBarLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public AppBarLayout(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    setOrientation(1);
    ThemeUtils.checkAppCompatTheme(paramContext);
    if (Build.VERSION.SDK_INT >= 21) {
      ViewUtilsLollipop.setBoundsViewOutlineProvider((View)this);
      ViewUtilsLollipop.setStateListAnimatorFromAttrs((View)this, paramAttributeSet, 0, R.style.Widget_Design_AppBarLayout);
    } 
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.AppBarLayout, 0, R.style.Widget_Design_AppBarLayout);
    ViewCompat.setBackground((View)this, typedArray.getDrawable(R.styleable.AppBarLayout_android_background));
    if (typedArray.hasValue(R.styleable.AppBarLayout_expanded))
      setExpanded(typedArray.getBoolean(R.styleable.AppBarLayout_expanded, false), false, false); 
    if (Build.VERSION.SDK_INT >= 21 && typedArray.hasValue(R.styleable.AppBarLayout_elevation))
      ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator((View)this, typedArray.getDimensionPixelSize(R.styleable.AppBarLayout_elevation, 0)); 
    if (Build.VERSION.SDK_INT >= 26) {
      if (typedArray.hasValue(R.styleable.AppBarLayout_android_keyboardNavigationCluster))
        setKeyboardNavigationCluster(typedArray.getBoolean(R.styleable.AppBarLayout_android_keyboardNavigationCluster, false)); 
      if (typedArray.hasValue(R.styleable.AppBarLayout_android_touchscreenBlocksFocus))
        setTouchscreenBlocksFocus(typedArray.getBoolean(R.styleable.AppBarLayout_android_touchscreenBlocksFocus, false)); 
    } 
    typedArray.recycle();
    ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener() {
          public WindowInsetsCompat onApplyWindowInsets(View param1View, WindowInsetsCompat param1WindowInsetsCompat) {
            return AppBarLayout.this.onWindowInsetChanged(param1WindowInsetsCompat);
          }
        });
  }
  
  private void invalidateScrollRanges() {
    this.mTotalScrollRange = -1;
    this.mDownPreScrollRange = -1;
    this.mDownScrollRange = -1;
  }
  
  private boolean setCollapsibleState(boolean paramBoolean) {
    if (this.mCollapsible != paramBoolean) {
      this.mCollapsible = paramBoolean;
      refreshDrawableState();
      return true;
    } 
    return false;
  }
  
  private void setExpanded(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
    byte b1;
    byte b3;
    if (paramBoolean1) {
      b1 = 1;
    } else {
      b1 = 2;
    } 
    byte b2 = 0;
    if (paramBoolean2) {
      b3 = 4;
    } else {
      b3 = 0;
    } 
    if (paramBoolean3)
      b2 = 8; 
    this.mPendingAction = b1 | b3 | b2;
    requestLayout();
  }
  
  private void updateCollapsible() {
    boolean bool2;
    int i = getChildCount();
    boolean bool1 = false;
    byte b = 0;
    while (true) {
      bool2 = bool1;
      if (b < i) {
        if (((LayoutParams)getChildAt(b).getLayoutParams()).isCollapsible()) {
          bool2 = true;
          break;
        } 
        b++;
        continue;
      } 
      break;
    } 
    setCollapsibleState(bool2);
  }
  
  public void addOnOffsetChangedListener(OnOffsetChangedListener paramOnOffsetChangedListener) {
    if (this.mListeners == null)
      this.mListeners = new ArrayList<OnOffsetChangedListener>(); 
    if (paramOnOffsetChangedListener != null && !this.mListeners.contains(paramOnOffsetChangedListener))
      this.mListeners.add(paramOnOffsetChangedListener); 
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  void dispatchOffsetUpdates(int paramInt) {
    List<OnOffsetChangedListener> list = this.mListeners;
    if (list != null) {
      byte b = 0;
      int i = list.size();
      while (b < i) {
        OnOffsetChangedListener onOffsetChangedListener = this.mListeners.get(b);
        if (onOffsetChangedListener != null)
          onOffsetChangedListener.onOffsetChanged(this, paramInt); 
        b++;
      } 
    } 
  }
  
  protected LayoutParams generateDefaultLayoutParams() {
    return new LayoutParams(-1, -2);
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return (Build.VERSION.SDK_INT >= 19 && paramLayoutParams instanceof LinearLayout.LayoutParams) ? new LayoutParams((LinearLayout.LayoutParams)paramLayoutParams) : ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams) ? new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams) : new LayoutParams(paramLayoutParams));
  }
  
  int getDownNestedPreScrollRange() {
    int i = this.mDownPreScrollRange;
    if (i != -1)
      return i; 
    int j = getChildCount() - 1;
    int k;
    for (k = 0; j >= 0; k = i) {
      View view = getChildAt(j);
      LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
      int m = view.getMeasuredHeight();
      i = layoutParams.mScrollFlags;
      if ((i & 0x5) == 5) {
        k += layoutParams.topMargin + layoutParams.bottomMargin;
        if ((i & 0x8) != 0) {
          i = k + ViewCompat.getMinimumHeight(view);
        } else {
          if ((i & 0x2) != 0) {
            i = ViewCompat.getMinimumHeight(view);
          } else {
            i = getTopInset();
          } 
          i = k + m - i;
        } 
      } else {
        i = k;
        if (k > 0)
          break; 
      } 
      j--;
    } 
    i = Math.max(0, k);
    this.mDownPreScrollRange = i;
    return i;
  }
  
  int getDownNestedScrollRange() {
    int k;
    int i = this.mDownScrollRange;
    if (i != -1)
      return i; 
    int j = getChildCount();
    byte b = 0;
    i = 0;
    while (true) {
      k = i;
      if (b < j) {
        View view = getChildAt(b);
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int m = view.getMeasuredHeight();
        int n = layoutParams.topMargin;
        int i1 = layoutParams.bottomMargin;
        int i2 = layoutParams.mScrollFlags;
        k = i;
        if ((i2 & 0x1) != 0) {
          i += m + n + i1;
          if ((i2 & 0x2) != 0) {
            k = i - ViewCompat.getMinimumHeight(view) + getTopInset();
            break;
          } 
          b++;
          continue;
        } 
      } 
      break;
    } 
    i = Math.max(0, k);
    this.mDownScrollRange = i;
    return i;
  }
  
  final int getMinimumHeightForVisibleOverlappingContent() {
    int i = getTopInset();
    int j = ViewCompat.getMinimumHeight((View)this);
    if (j == 0) {
      j = getChildCount();
      if (j >= 1) {
        j = ViewCompat.getMinimumHeight(getChildAt(j - 1));
      } else {
        j = 0;
      } 
      if (j == 0)
        return getHeight() / 3; 
    } 
    return j * 2 + i;
  }
  
  int getPendingAction() {
    return this.mPendingAction;
  }
  
  @Deprecated
  public float getTargetElevation() {
    return 0.0F;
  }
  
  final int getTopInset() {
    boolean bool;
    WindowInsetsCompat windowInsetsCompat = this.mLastInsets;
    if (windowInsetsCompat != null) {
      bool = windowInsetsCompat.getSystemWindowInsetTop();
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final int getTotalScrollRange() {
    int k;
    int i = this.mTotalScrollRange;
    if (i != -1)
      return i; 
    int j = getChildCount();
    byte b = 0;
    i = 0;
    while (true) {
      k = i;
      if (b < j) {
        View view = getChildAt(b);
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int m = view.getMeasuredHeight();
        int n = layoutParams.mScrollFlags;
        k = i;
        if ((n & 0x1) != 0) {
          i += m + layoutParams.topMargin + layoutParams.bottomMargin;
          if ((n & 0x2) != 0) {
            k = i - ViewCompat.getMinimumHeight(view);
            break;
          } 
          b++;
          continue;
        } 
      } 
      break;
    } 
    i = Math.max(0, k - getTopInset());
    this.mTotalScrollRange = i;
    return i;
  }
  
  int getUpNestedPreScrollRange() {
    return getTotalScrollRange();
  }
  
  boolean hasChildWithInterpolator() {
    return this.mHaveChildWithInterpolator;
  }
  
  boolean hasScrollableChildren() {
    boolean bool;
    if (getTotalScrollRange() != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected int[] onCreateDrawableState(int paramInt) {
    if (this.mTmpStatesArray == null)
      this.mTmpStatesArray = new int[2]; 
    int[] arrayOfInt1 = this.mTmpStatesArray;
    int[] arrayOfInt2 = super.onCreateDrawableState(paramInt + arrayOfInt1.length);
    if (this.mCollapsible) {
      paramInt = R.attr.state_collapsible;
    } else {
      paramInt = -R.attr.state_collapsible;
    } 
    arrayOfInt1[0] = paramInt;
    if (this.mCollapsible && this.mCollapsed) {
      paramInt = R.attr.state_collapsed;
    } else {
      paramInt = -R.attr.state_collapsed;
    } 
    arrayOfInt1[1] = paramInt;
    return mergeDrawableStates(arrayOfInt2, arrayOfInt1);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    invalidateScrollRanges();
    paramInt1 = 0;
    this.mHaveChildWithInterpolator = false;
    paramInt2 = getChildCount();
    while (paramInt1 < paramInt2) {
      if (((LayoutParams)getChildAt(paramInt1).getLayoutParams()).getScrollInterpolator() != null) {
        this.mHaveChildWithInterpolator = true;
        break;
      } 
      paramInt1++;
    } 
    updateCollapsible();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    super.onMeasure(paramInt1, paramInt2);
    invalidateScrollRanges();
  }
  
  WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat paramWindowInsetsCompat) {
    WindowInsetsCompat windowInsetsCompat;
    if (ViewCompat.getFitsSystemWindows((View)this)) {
      windowInsetsCompat = paramWindowInsetsCompat;
    } else {
      windowInsetsCompat = null;
    } 
    if (!ObjectsCompat.equals(this.mLastInsets, windowInsetsCompat)) {
      this.mLastInsets = windowInsetsCompat;
      invalidateScrollRanges();
    } 
    return paramWindowInsetsCompat;
  }
  
  public void removeOnOffsetChangedListener(OnOffsetChangedListener paramOnOffsetChangedListener) {
    List<OnOffsetChangedListener> list = this.mListeners;
    if (list != null && paramOnOffsetChangedListener != null)
      list.remove(paramOnOffsetChangedListener); 
  }
  
  void resetPendingAction() {
    this.mPendingAction = 0;
  }
  
  boolean setCollapsedState(boolean paramBoolean) {
    if (this.mCollapsed != paramBoolean) {
      this.mCollapsed = paramBoolean;
      refreshDrawableState();
      return true;
    } 
    return false;
  }
  
  public void setExpanded(boolean paramBoolean) {
    setExpanded(paramBoolean, ViewCompat.isLaidOut((View)this));
  }
  
  public void setExpanded(boolean paramBoolean1, boolean paramBoolean2) {
    setExpanded(paramBoolean1, paramBoolean2, true);
  }
  
  public void setOrientation(int paramInt) {
    if (paramInt == 1) {
      super.setOrientation(paramInt);
      return;
    } 
    throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
  }
  
  @Deprecated
  public void setTargetElevation(float paramFloat) {
    if (Build.VERSION.SDK_INT >= 21)
      ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator((View)this, paramFloat); 
  }
  
  public static class Behavior extends HeaderBehavior<AppBarLayout> {
    private static final int INVALID_POSITION = -1;
    
    private static final int MAX_OFFSET_ANIMATION_DURATION = 600;
    
    private WeakReference<View> mLastNestedScrollingChildRef;
    
    private ValueAnimator mOffsetAnimator;
    
    private int mOffsetDelta;
    
    private int mOffsetToChildIndexOnLayout = -1;
    
    private boolean mOffsetToChildIndexOnLayoutIsMinHeight;
    
    private float mOffsetToChildIndexOnLayoutPerc;
    
    private DragCallback mOnDragCallback;
    
    public Behavior() {}
    
    public Behavior(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
    }
    
    private void animateOffsetTo(CoordinatorLayout param1CoordinatorLayout, AppBarLayout param1AppBarLayout, int param1Int, float param1Float) {
      int i = Math.abs(getTopBottomOffsetForScrollingSibling() - param1Int);
      param1Float = Math.abs(param1Float);
      if (param1Float > 0.0F) {
        i = Math.round(i / param1Float * 1000.0F) * 3;
      } else {
        i = (int)((i / param1AppBarLayout.getHeight() + 1.0F) * 150.0F);
      } 
      animateOffsetWithDuration(param1CoordinatorLayout, param1AppBarLayout, param1Int, i);
    }
    
    private void animateOffsetWithDuration(CoordinatorLayout param1CoordinatorLayout, final AppBarLayout child, int param1Int1, int param1Int2) {
      final ValueAnimator coordinatorLayout;
      int i = getTopBottomOffsetForScrollingSibling();
      if (i == param1Int1) {
        valueAnimator1 = this.mOffsetAnimator;
        if (valueAnimator1 != null && valueAnimator1.isRunning())
          this.mOffsetAnimator.cancel(); 
        return;
      } 
      ValueAnimator valueAnimator2 = this.mOffsetAnimator;
      if (valueAnimator2 == null) {
        valueAnimator2 = new ValueAnimator();
        this.mOffsetAnimator = valueAnimator2;
        valueAnimator2.setInterpolator((TimeInterpolator)AnimationUtils.DECELERATE_INTERPOLATOR);
        this.mOffsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
              public void onAnimationUpdate(ValueAnimator param2ValueAnimator) {
                AppBarLayout.Behavior.this.setHeaderTopBottomOffset(coordinatorLayout, child, ((Integer)param2ValueAnimator.getAnimatedValue()).intValue());
              }
            });
      } else {
        valueAnimator2.cancel();
      } 
      this.mOffsetAnimator.setDuration(Math.min(param1Int2, 600));
      this.mOffsetAnimator.setIntValues(new int[] { i, param1Int1 });
      this.mOffsetAnimator.start();
    }
    
    private static boolean checkFlag(int param1Int1, int param1Int2) {
      boolean bool;
      if ((param1Int1 & param1Int2) == param1Int2) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    private static View getAppBarChildOnOffset(AppBarLayout param1AppBarLayout, int param1Int) {
      int i = Math.abs(param1Int);
      int j = param1AppBarLayout.getChildCount();
      for (param1Int = 0; param1Int < j; param1Int++) {
        View view = param1AppBarLayout.getChildAt(param1Int);
        if (i >= view.getTop() && i <= view.getBottom())
          return view; 
      } 
      return null;
    }
    
    private int getChildIndexOnOffset(AppBarLayout param1AppBarLayout, int param1Int) {
      int i = param1AppBarLayout.getChildCount();
      for (byte b = 0; b < i; b++) {
        View view = param1AppBarLayout.getChildAt(b);
        int j = view.getTop();
        int k = -param1Int;
        if (j <= k && view.getBottom() >= k)
          return b; 
      } 
      return -1;
    }
    
    private int interpolateOffset(AppBarLayout param1AppBarLayout, int param1Int) {
      int i = Math.abs(param1Int);
      int j = param1AppBarLayout.getChildCount();
      int k = 0;
      for (int m = 0; m < j; m++) {
        View view = param1AppBarLayout.getChildAt(m);
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams)view.getLayoutParams();
        Interpolator interpolator = layoutParams.getScrollInterpolator();
        if (i >= view.getTop() && i <= view.getBottom()) {
          if (interpolator != null) {
            j = layoutParams.getScrollFlags();
            m = k;
            if ((j & 0x1) != 0) {
              k = 0 + view.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
              m = k;
              if ((j & 0x2) != 0)
                m = k - ViewCompat.getMinimumHeight(view); 
            } 
            k = m;
            if (ViewCompat.getFitsSystemWindows(view))
              k = m - param1AppBarLayout.getTopInset(); 
            if (k > 0) {
              m = view.getTop();
              float f = k;
              m = Math.round(f * interpolator.getInterpolation((i - m) / f));
              return Integer.signum(param1Int) * (view.getTop() + m);
            } 
          } 
          break;
        } 
      } 
      return param1Int;
    }
    
    private boolean shouldJumpElevationState(CoordinatorLayout param1CoordinatorLayout, AppBarLayout param1AppBarLayout) {
      List<View> list = param1CoordinatorLayout.getDependents((View)param1AppBarLayout);
      int i = list.size();
      boolean bool = false;
      for (byte b = 0; b < i; b++) {
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams)((View)list.get(b)).getLayoutParams()).getBehavior();
        if (behavior instanceof AppBarLayout.ScrollingViewBehavior) {
          if (((AppBarLayout.ScrollingViewBehavior)behavior).getOverlayTop() != 0)
            bool = true; 
          return bool;
        } 
      } 
      return false;
    }
    
    private void snapToChildIfNeeded(CoordinatorLayout param1CoordinatorLayout, AppBarLayout param1AppBarLayout) {
      int i = getTopBottomOffsetForScrollingSibling();
      int j = getChildIndexOnOffset(param1AppBarLayout, i);
      if (j >= 0) {
        View view = param1AppBarLayout.getChildAt(j);
        int k = ((AppBarLayout.LayoutParams)view.getLayoutParams()).getScrollFlags();
        if ((k & 0x11) == 17) {
          int m = -view.getTop();
          int n = -view.getBottom();
          int i1 = n;
          if (j == param1AppBarLayout.getChildCount() - 1)
            i1 = n + param1AppBarLayout.getTopInset(); 
          if (checkFlag(k, 2)) {
            n = i1 + ViewCompat.getMinimumHeight(view);
            j = m;
          } else {
            j = m;
            n = i1;
            if (checkFlag(k, 5)) {
              n = ViewCompat.getMinimumHeight(view) + i1;
              if (i < n) {
                j = n;
                n = i1;
              } else {
                j = m;
              } 
            } 
          } 
          i1 = j;
          if (i < (n + j) / 2)
            i1 = n; 
          animateOffsetTo(param1CoordinatorLayout, param1AppBarLayout, MathUtils.clamp(i1, -param1AppBarLayout.getTotalScrollRange(), 0), 0.0F);
        } 
      } 
    }
    
    private void updateAppBarLayoutDrawableState(CoordinatorLayout param1CoordinatorLayout, AppBarLayout param1AppBarLayout, int param1Int1, int param1Int2, boolean param1Boolean) {
      // Byte code:
      //   0: aload_2
      //   1: iload_3
      //   2: invokestatic getAppBarChildOnOffset : (Landroid/support/design/widget/AppBarLayout;I)Landroid/view/View;
      //   5: astore #6
      //   7: aload #6
      //   9: ifnull -> 162
      //   12: aload #6
      //   14: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
      //   17: checkcast android/support/design/widget/AppBarLayout$LayoutParams
      //   20: invokevirtual getScrollFlags : ()I
      //   23: istore #7
      //   25: iconst_0
      //   26: istore #8
      //   28: iload #8
      //   30: istore #9
      //   32: iload #7
      //   34: iconst_1
      //   35: iand
      //   36: ifeq -> 123
      //   39: aload #6
      //   41: invokestatic getMinimumHeight : (Landroid/view/View;)I
      //   44: istore #10
      //   46: iload #4
      //   48: ifle -> 87
      //   51: iload #7
      //   53: bipush #12
      //   55: iand
      //   56: ifeq -> 87
      //   59: iload #8
      //   61: istore #9
      //   63: iload_3
      //   64: ineg
      //   65: aload #6
      //   67: invokevirtual getBottom : ()I
      //   70: iload #10
      //   72: isub
      //   73: aload_2
      //   74: invokevirtual getTopInset : ()I
      //   77: isub
      //   78: if_icmplt -> 123
      //   81: iconst_1
      //   82: istore #9
      //   84: goto -> 123
      //   87: iload #8
      //   89: istore #9
      //   91: iload #7
      //   93: iconst_2
      //   94: iand
      //   95: ifeq -> 123
      //   98: iload #8
      //   100: istore #9
      //   102: iload_3
      //   103: ineg
      //   104: aload #6
      //   106: invokevirtual getBottom : ()I
      //   109: iload #10
      //   111: isub
      //   112: aload_2
      //   113: invokevirtual getTopInset : ()I
      //   116: isub
      //   117: if_icmplt -> 123
      //   120: goto -> 81
      //   123: aload_2
      //   124: iload #9
      //   126: invokevirtual setCollapsedState : (Z)Z
      //   129: istore #9
      //   131: getstatic android/os/Build$VERSION.SDK_INT : I
      //   134: bipush #11
      //   136: if_icmplt -> 162
      //   139: iload #5
      //   141: ifne -> 158
      //   144: iload #9
      //   146: ifeq -> 162
      //   149: aload_0
      //   150: aload_1
      //   151: aload_2
      //   152: invokespecial shouldJumpElevationState : (Landroid/support/design/widget/CoordinatorLayout;Landroid/support/design/widget/AppBarLayout;)Z
      //   155: ifeq -> 162
      //   158: aload_2
      //   159: invokevirtual jumpDrawablesToCurrentState : ()V
      //   162: return
    }
    
    boolean canDragView(AppBarLayout param1AppBarLayout) {
      DragCallback dragCallback = this.mOnDragCallback;
      if (dragCallback != null)
        return dragCallback.canDrag(param1AppBarLayout); 
      WeakReference<View> weakReference = this.mLastNestedScrollingChildRef;
      boolean bool1 = true;
      boolean bool2 = bool1;
      if (weakReference != null) {
        View view = weakReference.get();
        if (view != null && view.isShown() && !view.canScrollVertically(-1)) {
          bool2 = bool1;
        } else {
          bool2 = false;
        } 
      } 
      return bool2;
    }
    
    int getMaxDragOffset(AppBarLayout param1AppBarLayout) {
      return -param1AppBarLayout.getDownNestedScrollRange();
    }
    
    int getScrollRangeForDragFling(AppBarLayout param1AppBarLayout) {
      return param1AppBarLayout.getTotalScrollRange();
    }
    
    int getTopBottomOffsetForScrollingSibling() {
      return getTopAndBottomOffset() + this.mOffsetDelta;
    }
    
    boolean isOffsetAnimatorRunning() {
      boolean bool;
      ValueAnimator valueAnimator = this.mOffsetAnimator;
      if (valueAnimator != null && valueAnimator.isRunning()) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    void onFlingFinished(CoordinatorLayout param1CoordinatorLayout, AppBarLayout param1AppBarLayout) {
      snapToChildIfNeeded(param1CoordinatorLayout, param1AppBarLayout);
    }
    
    public boolean onLayoutChild(CoordinatorLayout param1CoordinatorLayout, AppBarLayout param1AppBarLayout, int param1Int) {
      boolean bool = super.onLayoutChild(param1CoordinatorLayout, param1AppBarLayout, param1Int);
      int i = param1AppBarLayout.getPendingAction();
      param1Int = this.mOffsetToChildIndexOnLayout;
      if (param1Int >= 0 && (i & 0x8) == 0) {
        View view = param1AppBarLayout.getChildAt(param1Int);
        i = -view.getBottom();
        if (this.mOffsetToChildIndexOnLayoutIsMinHeight) {
          param1Int = ViewCompat.getMinimumHeight(view) + param1AppBarLayout.getTopInset();
        } else {
          param1Int = Math.round(view.getHeight() * this.mOffsetToChildIndexOnLayoutPerc);
        } 
        setHeaderTopBottomOffset(param1CoordinatorLayout, param1AppBarLayout, i + param1Int);
      } else if (i != 0) {
        if ((i & 0x4) != 0) {
          param1Int = 1;
        } else {
          param1Int = 0;
        } 
        if ((i & 0x2) != 0) {
          i = -param1AppBarLayout.getUpNestedPreScrollRange();
          if (param1Int != 0) {
            animateOffsetTo(param1CoordinatorLayout, param1AppBarLayout, i, 0.0F);
          } else {
            setHeaderTopBottomOffset(param1CoordinatorLayout, param1AppBarLayout, i);
          } 
        } else if ((i & 0x1) != 0) {
          if (param1Int != 0) {
            animateOffsetTo(param1CoordinatorLayout, param1AppBarLayout, 0, 0.0F);
          } else {
            setHeaderTopBottomOffset(param1CoordinatorLayout, param1AppBarLayout, 0);
          } 
        } 
      } 
      param1AppBarLayout.resetPendingAction();
      this.mOffsetToChildIndexOnLayout = -1;
      setTopAndBottomOffset(MathUtils.clamp(getTopAndBottomOffset(), -param1AppBarLayout.getTotalScrollRange(), 0));
      updateAppBarLayoutDrawableState(param1CoordinatorLayout, param1AppBarLayout, getTopAndBottomOffset(), 0, true);
      param1AppBarLayout.dispatchOffsetUpdates(getTopAndBottomOffset());
      return bool;
    }
    
    public boolean onMeasureChild(CoordinatorLayout param1CoordinatorLayout, AppBarLayout param1AppBarLayout, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      if (((CoordinatorLayout.LayoutParams)param1AppBarLayout.getLayoutParams()).height == -2) {
        param1CoordinatorLayout.onMeasureChild((View)param1AppBarLayout, param1Int1, param1Int2, View.MeasureSpec.makeMeasureSpec(0, 0), param1Int4);
        return true;
      } 
      return super.onMeasureChild(param1CoordinatorLayout, param1AppBarLayout, param1Int1, param1Int2, param1Int3, param1Int4);
    }
    
    public void onNestedPreScroll(CoordinatorLayout param1CoordinatorLayout, AppBarLayout param1AppBarLayout, View param1View, int param1Int1, int param1Int2, int[] param1ArrayOfint, int param1Int3) {
      if (param1Int2 != 0) {
        if (param1Int2 < 0) {
          param1Int3 = -param1AppBarLayout.getTotalScrollRange();
          int i = param1AppBarLayout.getDownNestedPreScrollRange();
          param1Int1 = param1Int3;
          param1Int3 = i + param1Int3;
        } else {
          param1Int1 = -param1AppBarLayout.getUpNestedPreScrollRange();
          param1Int3 = 0;
        } 
        if (param1Int1 != param1Int3)
          param1ArrayOfint[1] = scroll(param1CoordinatorLayout, param1AppBarLayout, param1Int2, param1Int1, param1Int3); 
      } 
    }
    
    public void onNestedScroll(CoordinatorLayout param1CoordinatorLayout, AppBarLayout param1AppBarLayout, View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
      if (param1Int4 < 0)
        scroll(param1CoordinatorLayout, param1AppBarLayout, param1Int4, -param1AppBarLayout.getDownNestedScrollRange(), 0); 
    }
    
    public void onRestoreInstanceState(CoordinatorLayout param1CoordinatorLayout, AppBarLayout param1AppBarLayout, Parcelable param1Parcelable) {
      SavedState savedState;
      if (param1Parcelable instanceof SavedState) {
        savedState = (SavedState)param1Parcelable;
        super.onRestoreInstanceState(param1CoordinatorLayout, param1AppBarLayout, savedState.getSuperState());
        this.mOffsetToChildIndexOnLayout = savedState.firstVisibleChildIndex;
        this.mOffsetToChildIndexOnLayoutPerc = savedState.firstVisibleChildPercentageShown;
        this.mOffsetToChildIndexOnLayoutIsMinHeight = savedState.firstVisibleChildAtMinimumHeight;
      } else {
        super.onRestoreInstanceState(param1CoordinatorLayout, param1AppBarLayout, (Parcelable)savedState);
        this.mOffsetToChildIndexOnLayout = -1;
      } 
    }
    
    public Parcelable onSaveInstanceState(CoordinatorLayout param1CoordinatorLayout, AppBarLayout param1AppBarLayout) {
      SavedState savedState;
      Parcelable parcelable = super.onSaveInstanceState(param1CoordinatorLayout, param1AppBarLayout);
      int i = getTopAndBottomOffset();
      int j = param1AppBarLayout.getChildCount();
      boolean bool = false;
      for (byte b = 0; b < j; b++) {
        View view = param1AppBarLayout.getChildAt(b);
        int k = view.getBottom() + i;
        if (view.getTop() + i <= 0 && k >= 0) {
          savedState = new SavedState(parcelable);
          savedState.firstVisibleChildIndex = b;
          if (k == ViewCompat.getMinimumHeight(view) + param1AppBarLayout.getTopInset())
            bool = true; 
          savedState.firstVisibleChildAtMinimumHeight = bool;
          savedState.firstVisibleChildPercentageShown = k / view.getHeight();
          return (Parcelable)savedState;
        } 
      } 
      return (Parcelable)savedState;
    }
    
    public boolean onStartNestedScroll(CoordinatorLayout param1CoordinatorLayout, AppBarLayout param1AppBarLayout, View param1View1, View param1View2, int param1Int1, int param1Int2) {
      boolean bool;
      if ((param1Int1 & 0x2) != 0 && param1AppBarLayout.hasScrollableChildren() && param1CoordinatorLayout.getHeight() - param1View1.getHeight() <= param1AppBarLayout.getHeight()) {
        bool = true;
      } else {
        bool = false;
      } 
      if (bool) {
        ValueAnimator valueAnimator = this.mOffsetAnimator;
        if (valueAnimator != null)
          valueAnimator.cancel(); 
      } 
      this.mLastNestedScrollingChildRef = null;
      return bool;
    }
    
    public void onStopNestedScroll(CoordinatorLayout param1CoordinatorLayout, AppBarLayout param1AppBarLayout, View param1View, int param1Int) {
      if (param1Int == 0)
        snapToChildIfNeeded(param1CoordinatorLayout, param1AppBarLayout); 
      this.mLastNestedScrollingChildRef = new WeakReference<View>(param1View);
    }
    
    public void setDragCallback(DragCallback param1DragCallback) {
      this.mOnDragCallback = param1DragCallback;
    }
    
    int setHeaderTopBottomOffset(CoordinatorLayout param1CoordinatorLayout, AppBarLayout param1AppBarLayout, int param1Int1, int param1Int2, int param1Int3) {
      int i = getTopBottomOffsetForScrollingSibling();
      boolean bool = false;
      if (param1Int2 != 0 && i >= param1Int2 && i <= param1Int3) {
        param1Int2 = MathUtils.clamp(param1Int1, param1Int2, param1Int3);
        param1Int1 = bool;
        if (i != param1Int2) {
          if (param1AppBarLayout.hasChildWithInterpolator()) {
            param1Int1 = interpolateOffset(param1AppBarLayout, param1Int2);
          } else {
            param1Int1 = param1Int2;
          } 
          boolean bool1 = setTopAndBottomOffset(param1Int1);
          param1Int3 = i - param1Int2;
          this.mOffsetDelta = param1Int2 - param1Int1;
          if (!bool1 && param1AppBarLayout.hasChildWithInterpolator())
            param1CoordinatorLayout.dispatchDependentViewsChanged((View)param1AppBarLayout); 
          param1AppBarLayout.dispatchOffsetUpdates(getTopAndBottomOffset());
          if (param1Int2 < i) {
            param1Int1 = -1;
          } else {
            param1Int1 = 1;
          } 
          updateAppBarLayoutDrawableState(param1CoordinatorLayout, param1AppBarLayout, param1Int2, param1Int1, false);
          param1Int1 = param1Int3;
        } 
      } else {
        this.mOffsetDelta = 0;
        param1Int1 = bool;
      } 
      return param1Int1;
    }
    
    public static abstract class DragCallback {
      public abstract boolean canDrag(AppBarLayout param2AppBarLayout);
    }
    
    protected static class SavedState extends AbsSavedState {
      public static final Parcelable.Creator<SavedState> CREATOR = (Parcelable.Creator<SavedState>)new Parcelable.ClassLoaderCreator<SavedState>() {
          public AppBarLayout.Behavior.SavedState createFromParcel(Parcel param3Parcel) {
            return new AppBarLayout.Behavior.SavedState(param3Parcel, null);
          }
          
          public AppBarLayout.Behavior.SavedState createFromParcel(Parcel param3Parcel, ClassLoader param3ClassLoader) {
            return new AppBarLayout.Behavior.SavedState(param3Parcel, param3ClassLoader);
          }
          
          public AppBarLayout.Behavior.SavedState[] newArray(int param3Int) {
            return new AppBarLayout.Behavior.SavedState[param3Int];
          }
        };
      
      boolean firstVisibleChildAtMinimumHeight;
      
      int firstVisibleChildIndex;
      
      float firstVisibleChildPercentageShown;
      
      public SavedState(Parcel param2Parcel, ClassLoader param2ClassLoader) {
        super(param2Parcel, param2ClassLoader);
        boolean bool;
        this.firstVisibleChildIndex = param2Parcel.readInt();
        this.firstVisibleChildPercentageShown = param2Parcel.readFloat();
        if (param2Parcel.readByte() != 0) {
          bool = true;
        } else {
          bool = false;
        } 
        this.firstVisibleChildAtMinimumHeight = bool;
      }
      
      public SavedState(Parcelable param2Parcelable) {
        super(param2Parcelable);
      }
      
      public void writeToParcel(Parcel param2Parcel, int param2Int) {
        super.writeToParcel(param2Parcel, param2Int);
        param2Parcel.writeInt(this.firstVisibleChildIndex);
        param2Parcel.writeFloat(this.firstVisibleChildPercentageShown);
        param2Parcel.writeByte((byte)this.firstVisibleChildAtMinimumHeight);
      }
    }
    
    static final class null implements Parcelable.ClassLoaderCreator<SavedState> {
      public AppBarLayout.Behavior.SavedState createFromParcel(Parcel param2Parcel) {
        return new AppBarLayout.Behavior.SavedState(param2Parcel, null);
      }
      
      public AppBarLayout.Behavior.SavedState createFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
        return new AppBarLayout.Behavior.SavedState(param2Parcel, param2ClassLoader);
      }
      
      public AppBarLayout.Behavior.SavedState[] newArray(int param2Int) {
        return new AppBarLayout.Behavior.SavedState[param2Int];
      }
    }
  }
  
  class null implements ValueAnimator.AnimatorUpdateListener {
    public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
      this.this$0.setHeaderTopBottomOffset(coordinatorLayout, child, ((Integer)param1ValueAnimator.getAnimatedValue()).intValue());
    }
  }
  
  public static abstract class DragCallback {
    public abstract boolean canDrag(AppBarLayout param1AppBarLayout);
  }
  
  protected static class SavedState extends AbsSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR = (Parcelable.Creator<SavedState>)new Parcelable.ClassLoaderCreator<SavedState>() {
        public AppBarLayout.Behavior.SavedState createFromParcel(Parcel param3Parcel) {
          return new AppBarLayout.Behavior.SavedState(param3Parcel, null);
        }
        
        public AppBarLayout.Behavior.SavedState createFromParcel(Parcel param3Parcel, ClassLoader param3ClassLoader) {
          return new AppBarLayout.Behavior.SavedState(param3Parcel, param3ClassLoader);
        }
        
        public AppBarLayout.Behavior.SavedState[] newArray(int param3Int) {
          return new AppBarLayout.Behavior.SavedState[param3Int];
        }
      };
    
    boolean firstVisibleChildAtMinimumHeight;
    
    int firstVisibleChildIndex;
    
    float firstVisibleChildPercentageShown;
    
    public SavedState(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      super(param1Parcel, param1ClassLoader);
      boolean bool;
      this.firstVisibleChildIndex = param1Parcel.readInt();
      this.firstVisibleChildPercentageShown = param1Parcel.readFloat();
      if (param1Parcel.readByte() != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      this.firstVisibleChildAtMinimumHeight = bool;
    }
    
    public SavedState(Parcelable param1Parcelable) {
      super(param1Parcelable);
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      super.writeToParcel(param1Parcel, param1Int);
      param1Parcel.writeInt(this.firstVisibleChildIndex);
      param1Parcel.writeFloat(this.firstVisibleChildPercentageShown);
      param1Parcel.writeByte((byte)this.firstVisibleChildAtMinimumHeight);
    }
  }
  
  static final class null implements Parcelable.ClassLoaderCreator<Behavior.SavedState> {
    public AppBarLayout.Behavior.SavedState createFromParcel(Parcel param1Parcel) {
      return new AppBarLayout.Behavior.SavedState(param1Parcel, null);
    }
    
    public AppBarLayout.Behavior.SavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return new AppBarLayout.Behavior.SavedState(param1Parcel, param1ClassLoader);
    }
    
    public AppBarLayout.Behavior.SavedState[] newArray(int param1Int) {
      return new AppBarLayout.Behavior.SavedState[param1Int];
    }
  }
  
  public static class LayoutParams extends LinearLayout.LayoutParams {
    static final int COLLAPSIBLE_FLAGS = 10;
    
    static final int FLAG_QUICK_RETURN = 5;
    
    static final int FLAG_SNAP = 17;
    
    public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
    
    public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
    
    public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
    
    public static final int SCROLL_FLAG_SCROLL = 1;
    
    public static final int SCROLL_FLAG_SNAP = 16;
    
    int mScrollFlags = 1;
    
    Interpolator mScrollInterpolator;
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
    }
    
    public LayoutParams(int param1Int1, int param1Int2, float param1Float) {
      super(param1Int1, param1Int2, param1Float);
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, R.styleable.AppBarLayout_Layout);
      this.mScrollFlags = typedArray.getInt(R.styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
      if (typedArray.hasValue(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator))
        this.mScrollInterpolator = AnimationUtils.loadInterpolator(param1Context, typedArray.getResourceId(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0)); 
      typedArray.recycle();
    }
    
    public LayoutParams(LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
      this.mScrollFlags = param1LayoutParams.mScrollFlags;
      this.mScrollInterpolator = param1LayoutParams.mScrollInterpolator;
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams param1MarginLayoutParams) {
      super(param1MarginLayoutParams);
    }
    
    public LayoutParams(LinearLayout.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public int getScrollFlags() {
      return this.mScrollFlags;
    }
    
    public Interpolator getScrollInterpolator() {
      return this.mScrollInterpolator;
    }
    
    boolean isCollapsible() {
      int i = this.mScrollFlags;
      boolean bool = true;
      if ((i & 0x1) != 1 || (i & 0xA) == 0)
        bool = false; 
      return bool;
    }
    
    public void setScrollFlags(int param1Int) {
      this.mScrollFlags = param1Int;
    }
    
    public void setScrollInterpolator(Interpolator param1Interpolator) {
      this.mScrollInterpolator = param1Interpolator;
    }
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface ScrollFlags {}
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ScrollFlags {}
  
  public static interface OnOffsetChangedListener {
    void onOffsetChanged(AppBarLayout param1AppBarLayout, int param1Int);
  }
  
  public static class ScrollingViewBehavior extends HeaderScrollingViewBehavior {
    public ScrollingViewBehavior() {}
    
    public ScrollingViewBehavior(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, R.styleable.ScrollingViewBehavior_Layout);
      setOverlayTop(typedArray.getDimensionPixelSize(R.styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
      typedArray.recycle();
    }
    
    private static int getAppBarLayoutOffset(AppBarLayout param1AppBarLayout) {
      CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams)param1AppBarLayout.getLayoutParams()).getBehavior();
      return (behavior instanceof AppBarLayout.Behavior) ? ((AppBarLayout.Behavior)behavior).getTopBottomOffsetForScrollingSibling() : 0;
    }
    
    private void offsetChildAsNeeded(CoordinatorLayout param1CoordinatorLayout, View param1View1, View param1View2) {
      CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams)param1View2.getLayoutParams()).getBehavior();
      if (behavior instanceof AppBarLayout.Behavior) {
        behavior = behavior;
        ViewCompat.offsetTopAndBottom(param1View1, param1View2.getBottom() - param1View1.getTop() + ((AppBarLayout.Behavior)behavior).mOffsetDelta + getVerticalLayoutGap() - getOverlapPixelsForOffset(param1View2));
      } 
    }
    
    AppBarLayout findFirstDependency(List<View> param1List) {
      int i = param1List.size();
      for (byte b = 0; b < i; b++) {
        View view = param1List.get(b);
        if (view instanceof AppBarLayout)
          return (AppBarLayout)view; 
      } 
      return null;
    }
    
    float getOverlapRatioForOffset(View param1View) {
      if (param1View instanceof AppBarLayout) {
        AppBarLayout appBarLayout = (AppBarLayout)param1View;
        int i = appBarLayout.getTotalScrollRange();
        int j = appBarLayout.getDownNestedPreScrollRange();
        int k = getAppBarLayoutOffset(appBarLayout);
        if (j != 0 && i + k <= j)
          return 0.0F; 
        i -= j;
        if (i != 0)
          return k / i + 1.0F; 
      } 
      return 0.0F;
    }
    
    int getScrollRange(View param1View) {
      return (param1View instanceof AppBarLayout) ? ((AppBarLayout)param1View).getTotalScrollRange() : super.getScrollRange(param1View);
    }
    
    public boolean layoutDependsOn(CoordinatorLayout param1CoordinatorLayout, View param1View1, View param1View2) {
      return param1View2 instanceof AppBarLayout;
    }
    
    public boolean onDependentViewChanged(CoordinatorLayout param1CoordinatorLayout, View param1View1, View param1View2) {
      offsetChildAsNeeded(param1CoordinatorLayout, param1View1, param1View2);
      return false;
    }
    
    public boolean onRequestChildRectangleOnScreen(CoordinatorLayout param1CoordinatorLayout, View param1View, Rect param1Rect, boolean param1Boolean) {
      AppBarLayout appBarLayout = findFirstDependency(param1CoordinatorLayout.getDependencies(param1View));
      if (appBarLayout != null) {
        param1Rect.offset(param1View.getLeft(), param1View.getTop());
        Rect rect = this.mTempRect1;
        rect.set(0, 0, param1CoordinatorLayout.getWidth(), param1CoordinatorLayout.getHeight());
        if (!rect.contains(param1Rect)) {
          appBarLayout.setExpanded(false, param1Boolean ^ true);
          return true;
        } 
      } 
      return false;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\AppBarLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */