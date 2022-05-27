package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class DrawerLayout extends ViewGroup {
  private static final boolean ALLOW_EDGE_LOCK = false;
  
  static final boolean CAN_HIDE_DESCENDANTS;
  
  private static final boolean CHILDREN_DISALLOW_INTERCEPT = true;
  
  private static final int DEFAULT_SCRIM_COLOR = -1728053248;
  
  private static final int DRAWER_ELEVATION = 10;
  
  static final int[] LAYOUT_ATTRS;
  
  public static final int LOCK_MODE_LOCKED_CLOSED = 1;
  
  public static final int LOCK_MODE_LOCKED_OPEN = 2;
  
  public static final int LOCK_MODE_UNDEFINED = 3;
  
  public static final int LOCK_MODE_UNLOCKED = 0;
  
  private static final int MIN_DRAWER_MARGIN = 64;
  
  private static final int MIN_FLING_VELOCITY = 400;
  
  private static final int PEEK_DELAY = 160;
  
  private static final boolean SET_DRAWER_SHADOW_FROM_ELEVATION;
  
  public static final int STATE_DRAGGING = 1;
  
  public static final int STATE_IDLE = 0;
  
  public static final int STATE_SETTLING = 2;
  
  private static final String TAG = "DrawerLayout";
  
  private static final int[] THEME_ATTRS = new int[] { 16843828 };
  
  private static final float TOUCH_SLOP_SENSITIVITY = 1.0F;
  
  private final ChildAccessibilityDelegate mChildAccessibilityDelegate = new ChildAccessibilityDelegate();
  
  private boolean mChildrenCanceledTouch;
  
  private boolean mDisallowInterceptRequested;
  
  private boolean mDrawStatusBarBackground;
  
  private float mDrawerElevation;
  
  private int mDrawerState;
  
  private boolean mFirstLayout = true;
  
  private boolean mInLayout;
  
  private float mInitialMotionX;
  
  private float mInitialMotionY;
  
  private Object mLastInsets;
  
  private final ViewDragCallback mLeftCallback;
  
  private final ViewDragHelper mLeftDragger;
  
  private DrawerListener mListener;
  
  private List<DrawerListener> mListeners;
  
  private int mLockModeEnd = 3;
  
  private int mLockModeLeft = 3;
  
  private int mLockModeRight = 3;
  
  private int mLockModeStart = 3;
  
  private int mMinDrawerMargin;
  
  private final ArrayList<View> mNonDrawerViews;
  
  private final ViewDragCallback mRightCallback;
  
  private final ViewDragHelper mRightDragger;
  
  private int mScrimColor = -1728053248;
  
  private float mScrimOpacity;
  
  private Paint mScrimPaint = new Paint();
  
  private Drawable mShadowEnd = null;
  
  private Drawable mShadowLeft = null;
  
  private Drawable mShadowLeftResolved;
  
  private Drawable mShadowRight = null;
  
  private Drawable mShadowRightResolved;
  
  private Drawable mShadowStart = null;
  
  private Drawable mStatusBarBackground;
  
  private CharSequence mTitleLeft;
  
  private CharSequence mTitleRight;
  
  static {
    LAYOUT_ATTRS = new int[] { 16842931 };
    if (Build.VERSION.SDK_INT >= 19) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    CAN_HIDE_DESCENDANTS = bool2;
    if (Build.VERSION.SDK_INT >= 21) {
      bool2 = bool1;
    } else {
      bool2 = false;
    } 
    SET_DRAWER_SHADOW_FROM_ELEVATION = bool2;
  }
  
  public DrawerLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public DrawerLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public DrawerLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    setDescendantFocusability(262144);
    float f1 = (getResources().getDisplayMetrics()).density;
    this.mMinDrawerMargin = (int)(64.0F * f1 + 0.5F);
    float f2 = 400.0F * f1;
    this.mLeftCallback = new ViewDragCallback(3);
    this.mRightCallback = new ViewDragCallback(5);
    ViewDragHelper viewDragHelper = ViewDragHelper.create(this, 1.0F, this.mLeftCallback);
    this.mLeftDragger = viewDragHelper;
    viewDragHelper.setEdgeTrackingEnabled(1);
    this.mLeftDragger.setMinVelocity(f2);
    this.mLeftCallback.setDragger(this.mLeftDragger);
    viewDragHelper = ViewDragHelper.create(this, 1.0F, this.mRightCallback);
    this.mRightDragger = viewDragHelper;
    viewDragHelper.setEdgeTrackingEnabled(2);
    this.mRightDragger.setMinVelocity(f2);
    this.mRightCallback.setDragger(this.mRightDragger);
    setFocusableInTouchMode(true);
    ViewCompat.setImportantForAccessibility((View)this, 1);
    ViewCompat.setAccessibilityDelegate((View)this, new AccessibilityDelegate());
    setMotionEventSplittingEnabled(false);
    if (ViewCompat.getFitsSystemWindows((View)this))
      if (Build.VERSION.SDK_INT >= 21) {
        setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
              public WindowInsets onApplyWindowInsets(View param1View, WindowInsets param1WindowInsets) {
                boolean bool;
                DrawerLayout drawerLayout = (DrawerLayout)param1View;
                if (param1WindowInsets.getSystemWindowInsetTop() > 0) {
                  bool = true;
                } else {
                  bool = false;
                } 
                drawerLayout.setChildInsets(param1WindowInsets, bool);
                return param1WindowInsets.consumeSystemWindowInsets();
              }
            });
        setSystemUiVisibility(1280);
        TypedArray typedArray = paramContext.obtainStyledAttributes(THEME_ATTRS);
        try {
          this.mStatusBarBackground = typedArray.getDrawable(0);
        } finally {
          typedArray.recycle();
        } 
      } else {
        this.mStatusBarBackground = null;
      }  
    this.mDrawerElevation = f1 * 10.0F;
    this.mNonDrawerViews = new ArrayList<View>();
  }
  
  static String gravityToString(int paramInt) {
    return ((paramInt & 0x3) == 3) ? "LEFT" : (((paramInt & 0x5) == 5) ? "RIGHT" : Integer.toHexString(paramInt));
  }
  
  private static boolean hasOpaqueBackground(View paramView) {
    Drawable drawable = paramView.getBackground();
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (drawable != null) {
      bool2 = bool1;
      if (drawable.getOpacity() == -1)
        bool2 = true; 
    } 
    return bool2;
  }
  
  private boolean hasPeekingDrawer() {
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      if (((LayoutParams)getChildAt(b).getLayoutParams()).isPeeking)
        return true; 
    } 
    return false;
  }
  
  private boolean hasVisibleDrawer() {
    boolean bool;
    if (findVisibleDrawer() != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  static boolean includeChildForAccessibility(View paramView) {
    boolean bool;
    if (ViewCompat.getImportantForAccessibility(paramView) != 4 && ViewCompat.getImportantForAccessibility(paramView) != 2) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private boolean mirror(Drawable paramDrawable, int paramInt) {
    if (paramDrawable == null || !DrawableCompat.isAutoMirrored(paramDrawable))
      return false; 
    DrawableCompat.setLayoutDirection(paramDrawable, paramInt);
    return true;
  }
  
  private Drawable resolveLeftShadow() {
    int i = ViewCompat.getLayoutDirection((View)this);
    if (i == 0) {
      Drawable drawable = this.mShadowStart;
      if (drawable != null) {
        mirror(drawable, i);
        return this.mShadowStart;
      } 
    } else {
      Drawable drawable = this.mShadowEnd;
      if (drawable != null) {
        mirror(drawable, i);
        return this.mShadowEnd;
      } 
    } 
    return this.mShadowLeft;
  }
  
  private Drawable resolveRightShadow() {
    int i = ViewCompat.getLayoutDirection((View)this);
    if (i == 0) {
      Drawable drawable = this.mShadowEnd;
      if (drawable != null) {
        mirror(drawable, i);
        return this.mShadowEnd;
      } 
    } else {
      Drawable drawable = this.mShadowStart;
      if (drawable != null) {
        mirror(drawable, i);
        return this.mShadowStart;
      } 
    } 
    return this.mShadowRight;
  }
  
  private void resolveShadowDrawables() {
    if (SET_DRAWER_SHADOW_FROM_ELEVATION)
      return; 
    this.mShadowLeftResolved = resolveLeftShadow();
    this.mShadowRightResolved = resolveRightShadow();
  }
  
  private void updateChildrenImportantForAccessibility(View paramView, boolean paramBoolean) {
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = getChildAt(b);
      if ((!paramBoolean && !isDrawerView(view)) || (paramBoolean && view == paramView)) {
        ViewCompat.setImportantForAccessibility(view, 1);
      } else {
        ViewCompat.setImportantForAccessibility(view, 4);
      } 
    } 
  }
  
  public void addDrawerListener(DrawerListener paramDrawerListener) {
    if (paramDrawerListener == null)
      return; 
    if (this.mListeners == null)
      this.mListeners = new ArrayList<DrawerListener>(); 
    this.mListeners.add(paramDrawerListener);
  }
  
  public void addFocusables(ArrayList<View> paramArrayList, int paramInt1, int paramInt2) {
    if (getDescendantFocusability() == 393216)
      return; 
    int i = getChildCount();
    boolean bool = false;
    byte b = 0;
    int j = 0;
    while (b < i) {
      View view = getChildAt(b);
      if (isDrawerView(view)) {
        if (isDrawerOpen(view)) {
          view.addFocusables(paramArrayList, paramInt1, paramInt2);
          j = 1;
        } 
      } else {
        this.mNonDrawerViews.add(view);
      } 
      b++;
    } 
    if (!j) {
      j = this.mNonDrawerViews.size();
      for (b = bool; b < j; b++) {
        View view = this.mNonDrawerViews.get(b);
        if (view.getVisibility() == 0)
          view.addFocusables(paramArrayList, paramInt1, paramInt2); 
      } 
    } 
    this.mNonDrawerViews.clear();
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams) {
    super.addView(paramView, paramInt, paramLayoutParams);
    if (findOpenDrawer() != null || isDrawerView(paramView)) {
      ViewCompat.setImportantForAccessibility(paramView, 4);
    } else {
      ViewCompat.setImportantForAccessibility(paramView, 1);
    } 
    if (!CAN_HIDE_DESCENDANTS)
      ViewCompat.setAccessibilityDelegate(paramView, this.mChildAccessibilityDelegate); 
  }
  
  void cancelChildViewTouch() {
    if (!this.mChildrenCanceledTouch) {
      long l = SystemClock.uptimeMillis();
      MotionEvent motionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
      int i = getChildCount();
      for (byte b = 0; b < i; b++)
        getChildAt(b).dispatchTouchEvent(motionEvent); 
      motionEvent.recycle();
      this.mChildrenCanceledTouch = true;
    } 
  }
  
  boolean checkDrawerViewAbsoluteGravity(View paramView, int paramInt) {
    boolean bool;
    if ((getDrawerViewAbsoluteGravity(paramView) & paramInt) == paramInt) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    boolean bool;
    if (paramLayoutParams instanceof LayoutParams && super.checkLayoutParams(paramLayoutParams)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void closeDrawer(int paramInt) {
    closeDrawer(paramInt, true);
  }
  
  public void closeDrawer(int paramInt, boolean paramBoolean) {
    View view = findDrawerWithGravity(paramInt);
    if (view != null) {
      closeDrawer(view, paramBoolean);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No drawer view found with gravity ");
    stringBuilder.append(gravityToString(paramInt));
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void closeDrawer(View paramView) {
    closeDrawer(paramView, true);
  }
  
  public void closeDrawer(View paramView, boolean paramBoolean) {
    if (isDrawerView(paramView)) {
      LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
      if (this.mFirstLayout) {
        layoutParams.onScreen = 0.0F;
        layoutParams.openState = 0;
      } else if (paramBoolean) {
        layoutParams.openState |= 0x4;
        if (checkDrawerViewAbsoluteGravity(paramView, 3)) {
          this.mLeftDragger.smoothSlideViewTo(paramView, -paramView.getWidth(), paramView.getTop());
        } else {
          this.mRightDragger.smoothSlideViewTo(paramView, getWidth(), paramView.getTop());
        } 
      } else {
        moveDrawerToOffset(paramView, 0.0F);
        updateDrawerState(layoutParams.gravity, 0, paramView);
        paramView.setVisibility(4);
      } 
      invalidate();
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("View ");
    stringBuilder.append(paramView);
    stringBuilder.append(" is not a sliding drawer");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void closeDrawers() {
    closeDrawers(false);
  }
  
  void closeDrawers(boolean paramBoolean) {
    int j;
    int i = getChildCount();
    byte b = 0;
    int k = 0;
    while (b < i) {
      int m;
      View view = getChildAt(b);
      LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
      int n = k;
      if (isDrawerView(view))
        if (paramBoolean && !layoutParams.isPeeking) {
          n = k;
        } else {
          int i1;
          n = view.getWidth();
          if (checkDrawerViewAbsoluteGravity(view, 3)) {
            i1 = this.mLeftDragger.smoothSlideViewTo(view, -n, view.getTop());
          } else {
            i1 = this.mRightDragger.smoothSlideViewTo(view, getWidth(), view.getTop());
          } 
          m = k | i1;
          layoutParams.isPeeking = false;
        }  
      b++;
      j = m;
    } 
    this.mLeftCallback.removeCallbacks();
    this.mRightCallback.removeCallbacks();
    if (j != 0)
      invalidate(); 
  }
  
  public void computeScroll() {
    int i = getChildCount();
    float f = 0.0F;
    for (byte b = 0; b < i; b++)
      f = Math.max(f, ((LayoutParams)getChildAt(b).getLayoutParams()).onScreen); 
    this.mScrimOpacity = f;
    boolean bool1 = this.mLeftDragger.continueSettling(true);
    boolean bool2 = this.mRightDragger.continueSettling(true);
    if (bool1 || bool2)
      ViewCompat.postInvalidateOnAnimation((View)this); 
  }
  
  void dispatchOnDrawerClosed(View paramView) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if ((layoutParams.openState & 0x1) == 1) {
      layoutParams.openState = 0;
      List<DrawerListener> list = this.mListeners;
      if (list != null)
        for (int i = list.size() - 1; i >= 0; i--)
          ((DrawerListener)this.mListeners.get(i)).onDrawerClosed(paramView);  
      updateChildrenImportantForAccessibility(paramView, false);
      if (hasWindowFocus()) {
        paramView = getRootView();
        if (paramView != null)
          paramView.sendAccessibilityEvent(32); 
      } 
    } 
  }
  
  void dispatchOnDrawerOpened(View paramView) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if ((layoutParams.openState & 0x1) == 0) {
      layoutParams.openState = 1;
      List<DrawerListener> list = this.mListeners;
      if (list != null)
        for (int i = list.size() - 1; i >= 0; i--)
          ((DrawerListener)this.mListeners.get(i)).onDrawerOpened(paramView);  
      updateChildrenImportantForAccessibility(paramView, true);
      if (hasWindowFocus())
        sendAccessibilityEvent(32); 
    } 
  }
  
  void dispatchOnDrawerSlide(View paramView, float paramFloat) {
    List<DrawerListener> list = this.mListeners;
    if (list != null)
      for (int i = list.size() - 1; i >= 0; i--)
        ((DrawerListener)this.mListeners.get(i)).onDrawerSlide(paramView, paramFloat);  
  }
  
  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong) {
    int i = getHeight();
    boolean bool1 = isContentView(paramView);
    int j = getWidth();
    int k = paramCanvas.save();
    int m = 0;
    int n = j;
    if (bool1) {
      int i1 = getChildCount();
      n = 0;
      for (m = 0; n < i1; m = i3) {
        View view = getChildAt(n);
        int i2 = j;
        int i3 = m;
        if (view != paramView) {
          i2 = j;
          i3 = m;
          if (view.getVisibility() == 0) {
            i2 = j;
            i3 = m;
            if (hasOpaqueBackground(view)) {
              i2 = j;
              i3 = m;
              if (isDrawerView(view))
                if (view.getHeight() < i) {
                  i2 = j;
                  i3 = m;
                } else if (checkDrawerViewAbsoluteGravity(view, 3)) {
                  int i4 = view.getRight();
                  i2 = j;
                  i3 = m;
                  if (i4 > m) {
                    i3 = i4;
                    i2 = j;
                  } 
                } else {
                  int i4 = view.getLeft();
                  i2 = j;
                  i3 = m;
                  if (i4 < j) {
                    i2 = i4;
                    i3 = m;
                  } 
                }  
            } 
          } 
        } 
        n++;
        j = i2;
      } 
      paramCanvas.clipRect(m, 0, j, getHeight());
      n = j;
    } 
    boolean bool2 = super.drawChild(paramCanvas, paramView, paramLong);
    paramCanvas.restoreToCount(k);
    float f = this.mScrimOpacity;
    if (f > 0.0F && bool1) {
      int i1 = this.mScrimColor;
      j = (int)(((0xFF000000 & i1) >>> 24) * f);
      this.mScrimPaint.setColor(i1 & 0xFFFFFF | j << 24);
      paramCanvas.drawRect(m, 0.0F, n, getHeight(), this.mScrimPaint);
    } else if (this.mShadowLeftResolved != null && checkDrawerViewAbsoluteGravity(paramView, 3)) {
      m = this.mShadowLeftResolved.getIntrinsicWidth();
      n = paramView.getRight();
      j = this.mLeftDragger.getEdgeSize();
      f = Math.max(0.0F, Math.min(n / j, 1.0F));
      this.mShadowLeftResolved.setBounds(n, paramView.getTop(), m + n, paramView.getBottom());
      this.mShadowLeftResolved.setAlpha((int)(f * 255.0F));
      this.mShadowLeftResolved.draw(paramCanvas);
    } else if (this.mShadowRightResolved != null && checkDrawerViewAbsoluteGravity(paramView, 5)) {
      int i1 = this.mShadowRightResolved.getIntrinsicWidth();
      m = paramView.getLeft();
      n = getWidth();
      j = this.mRightDragger.getEdgeSize();
      f = Math.max(0.0F, Math.min((n - m) / j, 1.0F));
      this.mShadowRightResolved.setBounds(m - i1, paramView.getTop(), m, paramView.getBottom());
      this.mShadowRightResolved.setAlpha((int)(f * 255.0F));
      this.mShadowRightResolved.draw(paramCanvas);
    } 
    return bool2;
  }
  
  View findDrawerWithGravity(int paramInt) {
    int i = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection((View)this));
    int j = getChildCount();
    for (paramInt = 0; paramInt < j; paramInt++) {
      View view = getChildAt(paramInt);
      if ((getDrawerViewAbsoluteGravity(view) & 0x7) == (i & 0x7))
        return view; 
    } 
    return null;
  }
  
  View findOpenDrawer() {
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = getChildAt(b);
      if ((((LayoutParams)view.getLayoutParams()).openState & 0x1) == 1)
        return view; 
    } 
    return null;
  }
  
  View findVisibleDrawer() {
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = getChildAt(b);
      if (isDrawerView(view) && isDrawerVisible(view))
        return view; 
    } 
    return null;
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
    return (ViewGroup.LayoutParams)new LayoutParams(-1, -1);
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return (ViewGroup.LayoutParams)new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    LayoutParams layoutParams;
    if (paramLayoutParams instanceof LayoutParams) {
      layoutParams = new LayoutParams((LayoutParams)paramLayoutParams);
    } else if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
      layoutParams = new LayoutParams(layoutParams);
    } else {
      layoutParams = new LayoutParams((ViewGroup.LayoutParams)layoutParams);
    } 
    return (ViewGroup.LayoutParams)layoutParams;
  }
  
  public float getDrawerElevation() {
    return SET_DRAWER_SHADOW_FROM_ELEVATION ? this.mDrawerElevation : 0.0F;
  }
  
  public int getDrawerLockMode(int paramInt) {
    int i = ViewCompat.getLayoutDirection((View)this);
    if (paramInt != 3) {
      if (paramInt != 5) {
        if (paramInt != 8388611) {
          if (paramInt == 8388613) {
            paramInt = this.mLockModeEnd;
            if (paramInt != 3)
              return paramInt; 
            if (i == 0) {
              paramInt = this.mLockModeRight;
            } else {
              paramInt = this.mLockModeLeft;
            } 
            if (paramInt != 3)
              return paramInt; 
          } 
        } else {
          paramInt = this.mLockModeStart;
          if (paramInt != 3)
            return paramInt; 
          if (i == 0) {
            paramInt = this.mLockModeLeft;
          } else {
            paramInt = this.mLockModeRight;
          } 
          if (paramInt != 3)
            return paramInt; 
        } 
      } else {
        paramInt = this.mLockModeRight;
        if (paramInt != 3)
          return paramInt; 
        if (i == 0) {
          paramInt = this.mLockModeEnd;
        } else {
          paramInt = this.mLockModeStart;
        } 
        if (paramInt != 3)
          return paramInt; 
      } 
    } else {
      paramInt = this.mLockModeLeft;
      if (paramInt != 3)
        return paramInt; 
      if (i == 0) {
        paramInt = this.mLockModeStart;
      } else {
        paramInt = this.mLockModeEnd;
      } 
      if (paramInt != 3)
        return paramInt; 
    } 
    return 0;
  }
  
  public int getDrawerLockMode(View paramView) {
    if (isDrawerView(paramView))
      return getDrawerLockMode(((LayoutParams)paramView.getLayoutParams()).gravity); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("View ");
    stringBuilder.append(paramView);
    stringBuilder.append(" is not a drawer");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public CharSequence getDrawerTitle(int paramInt) {
    paramInt = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection((View)this));
    return (paramInt == 3) ? this.mTitleLeft : ((paramInt == 5) ? this.mTitleRight : null);
  }
  
  int getDrawerViewAbsoluteGravity(View paramView) {
    return GravityCompat.getAbsoluteGravity(((LayoutParams)paramView.getLayoutParams()).gravity, ViewCompat.getLayoutDirection((View)this));
  }
  
  float getDrawerViewOffset(View paramView) {
    return ((LayoutParams)paramView.getLayoutParams()).onScreen;
  }
  
  public Drawable getStatusBarBackgroundDrawable() {
    return this.mStatusBarBackground;
  }
  
  boolean isContentView(View paramView) {
    boolean bool;
    if (((LayoutParams)paramView.getLayoutParams()).gravity == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isDrawerOpen(int paramInt) {
    View view = findDrawerWithGravity(paramInt);
    return (view != null) ? isDrawerOpen(view) : false;
  }
  
  public boolean isDrawerOpen(View paramView) {
    if (isDrawerView(paramView)) {
      int i = ((LayoutParams)paramView.getLayoutParams()).openState;
      boolean bool = true;
      if ((i & 0x1) != 1)
        bool = false; 
      return bool;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("View ");
    stringBuilder.append(paramView);
    stringBuilder.append(" is not a drawer");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  boolean isDrawerView(View paramView) {
    int i = GravityCompat.getAbsoluteGravity(((LayoutParams)paramView.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(paramView));
    return ((i & 0x3) != 0) ? true : (((i & 0x5) != 0));
  }
  
  public boolean isDrawerVisible(int paramInt) {
    View view = findDrawerWithGravity(paramInt);
    return (view != null) ? isDrawerVisible(view) : false;
  }
  
  public boolean isDrawerVisible(View paramView) {
    if (isDrawerView(paramView)) {
      boolean bool;
      if (((LayoutParams)paramView.getLayoutParams()).onScreen > 0.0F) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("View ");
    stringBuilder.append(paramView);
    stringBuilder.append(" is not a drawer");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  void moveDrawerToOffset(View paramView, float paramFloat) {
    float f1 = getDrawerViewOffset(paramView);
    float f2 = paramView.getWidth();
    int i = (int)(f1 * f2);
    i = (int)(f2 * paramFloat) - i;
    if (!checkDrawerViewAbsoluteGravity(paramView, 3))
      i = -i; 
    paramView.offsetLeftAndRight(i);
    setDrawerViewOffset(paramView, paramFloat);
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    this.mFirstLayout = true;
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    this.mFirstLayout = true;
  }
  
  public void onDraw(Canvas paramCanvas) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokespecial onDraw : (Landroid/graphics/Canvas;)V
    //   5: aload_0
    //   6: getfield mDrawStatusBarBackground : Z
    //   9: ifeq -> 75
    //   12: aload_0
    //   13: getfield mStatusBarBackground : Landroid/graphics/drawable/Drawable;
    //   16: ifnull -> 75
    //   19: getstatic android/os/Build$VERSION.SDK_INT : I
    //   22: bipush #21
    //   24: if_icmplt -> 47
    //   27: aload_0
    //   28: getfield mLastInsets : Ljava/lang/Object;
    //   31: astore_2
    //   32: aload_2
    //   33: ifnull -> 47
    //   36: aload_2
    //   37: checkcast android/view/WindowInsets
    //   40: invokevirtual getSystemWindowInsetTop : ()I
    //   43: istore_3
    //   44: goto -> 49
    //   47: iconst_0
    //   48: istore_3
    //   49: iload_3
    //   50: ifle -> 75
    //   53: aload_0
    //   54: getfield mStatusBarBackground : Landroid/graphics/drawable/Drawable;
    //   57: iconst_0
    //   58: iconst_0
    //   59: aload_0
    //   60: invokevirtual getWidth : ()I
    //   63: iload_3
    //   64: invokevirtual setBounds : (IIII)V
    //   67: aload_0
    //   68: getfield mStatusBarBackground : Landroid/graphics/drawable/Drawable;
    //   71: aload_1
    //   72: invokevirtual draw : (Landroid/graphics/Canvas;)V
    //   75: return
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getActionMasked : ()I
    //   4: istore_2
    //   5: aload_0
    //   6: getfield mLeftDragger : Landroid/support/v4/widget/ViewDragHelper;
    //   9: aload_1
    //   10: invokevirtual shouldInterceptTouchEvent : (Landroid/view/MotionEvent;)Z
    //   13: istore_3
    //   14: aload_0
    //   15: getfield mRightDragger : Landroid/support/v4/widget/ViewDragHelper;
    //   18: aload_1
    //   19: invokevirtual shouldInterceptTouchEvent : (Landroid/view/MotionEvent;)Z
    //   22: istore #4
    //   24: iconst_1
    //   25: istore #5
    //   27: iload_2
    //   28: ifeq -> 97
    //   31: iload_2
    //   32: iconst_1
    //   33: if_icmpeq -> 77
    //   36: iload_2
    //   37: iconst_2
    //   38: if_icmpeq -> 49
    //   41: iload_2
    //   42: iconst_3
    //   43: if_icmpeq -> 77
    //   46: goto -> 92
    //   49: aload_0
    //   50: getfield mLeftDragger : Landroid/support/v4/widget/ViewDragHelper;
    //   53: iconst_3
    //   54: invokevirtual checkTouchSlop : (I)Z
    //   57: ifeq -> 92
    //   60: aload_0
    //   61: getfield mLeftCallback : Landroid/support/v4/widget/DrawerLayout$ViewDragCallback;
    //   64: invokevirtual removeCallbacks : ()V
    //   67: aload_0
    //   68: getfield mRightCallback : Landroid/support/v4/widget/DrawerLayout$ViewDragCallback;
    //   71: invokevirtual removeCallbacks : ()V
    //   74: goto -> 92
    //   77: aload_0
    //   78: iconst_1
    //   79: invokevirtual closeDrawers : (Z)V
    //   82: aload_0
    //   83: iconst_0
    //   84: putfield mDisallowInterceptRequested : Z
    //   87: aload_0
    //   88: iconst_0
    //   89: putfield mChildrenCanceledTouch : Z
    //   92: iconst_0
    //   93: istore_2
    //   94: goto -> 173
    //   97: aload_1
    //   98: invokevirtual getX : ()F
    //   101: fstore #6
    //   103: aload_1
    //   104: invokevirtual getY : ()F
    //   107: fstore #7
    //   109: aload_0
    //   110: fload #6
    //   112: putfield mInitialMotionX : F
    //   115: aload_0
    //   116: fload #7
    //   118: putfield mInitialMotionY : F
    //   121: aload_0
    //   122: getfield mScrimOpacity : F
    //   125: fconst_0
    //   126: fcmpl
    //   127: ifle -> 161
    //   130: aload_0
    //   131: getfield mLeftDragger : Landroid/support/v4/widget/ViewDragHelper;
    //   134: fload #6
    //   136: f2i
    //   137: fload #7
    //   139: f2i
    //   140: invokevirtual findTopChildUnder : (II)Landroid/view/View;
    //   143: astore_1
    //   144: aload_1
    //   145: ifnull -> 161
    //   148: aload_0
    //   149: aload_1
    //   150: invokevirtual isContentView : (Landroid/view/View;)Z
    //   153: ifeq -> 161
    //   156: iconst_1
    //   157: istore_2
    //   158: goto -> 163
    //   161: iconst_0
    //   162: istore_2
    //   163: aload_0
    //   164: iconst_0
    //   165: putfield mDisallowInterceptRequested : Z
    //   168: aload_0
    //   169: iconst_0
    //   170: putfield mChildrenCanceledTouch : Z
    //   173: iload #5
    //   175: istore #8
    //   177: iload_3
    //   178: iload #4
    //   180: ior
    //   181: ifne -> 220
    //   184: iload #5
    //   186: istore #8
    //   188: iload_2
    //   189: ifne -> 220
    //   192: iload #5
    //   194: istore #8
    //   196: aload_0
    //   197: invokespecial hasPeekingDrawer : ()Z
    //   200: ifne -> 220
    //   203: aload_0
    //   204: getfield mChildrenCanceledTouch : Z
    //   207: ifeq -> 217
    //   210: iload #5
    //   212: istore #8
    //   214: goto -> 220
    //   217: iconst_0
    //   218: istore #8
    //   220: iload #8
    //   222: ireturn
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    if (paramInt == 4 && hasVisibleDrawer()) {
      paramKeyEvent.startTracking();
      return true;
    } 
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent) {
    View view;
    if (paramInt == 4) {
      boolean bool;
      view = findVisibleDrawer();
      if (view != null && getDrawerLockMode(view) == 0)
        closeDrawers(); 
      if (view != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    } 
    return super.onKeyUp(paramInt, (KeyEvent)view);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mInLayout = true;
    int i = paramInt3 - paramInt1;
    int j = getChildCount();
    for (paramInt3 = 0; paramInt3 < j; paramInt3++) {
      View view = getChildAt(paramInt3);
      if (view.getVisibility() != 8) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (isContentView(view)) {
          view.layout(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.leftMargin + view.getMeasuredWidth(), layoutParams.topMargin + view.getMeasuredHeight());
        } else {
          float f;
          int n;
          boolean bool;
          int k = view.getMeasuredWidth();
          int m = view.getMeasuredHeight();
          if (checkDrawerViewAbsoluteGravity(view, 3)) {
            paramInt1 = -k;
            f = k;
            n = paramInt1 + (int)(layoutParams.onScreen * f);
            f = (k + n) / f;
          } else {
            f = k;
            n = i - (int)(layoutParams.onScreen * f);
            f = (i - n) / f;
          } 
          if (f != layoutParams.onScreen) {
            bool = true;
          } else {
            bool = false;
          } 
          paramInt1 = layoutParams.gravity & 0x70;
          if (paramInt1 != 16) {
            if (paramInt1 != 80) {
              view.layout(n, layoutParams.topMargin, k + n, layoutParams.topMargin + m);
            } else {
              paramInt1 = paramInt4 - paramInt2;
              view.layout(n, paramInt1 - layoutParams.bottomMargin - view.getMeasuredHeight(), k + n, paramInt1 - layoutParams.bottomMargin);
            } 
          } else {
            int i1 = paramInt4 - paramInt2;
            int i2 = (i1 - m) / 2;
            if (i2 < layoutParams.topMargin) {
              paramInt1 = layoutParams.topMargin;
            } else {
              paramInt1 = i2;
              if (i2 + m > i1 - layoutParams.bottomMargin)
                paramInt1 = i1 - layoutParams.bottomMargin - m; 
            } 
            view.layout(n, paramInt1, k + n, m + paramInt1);
          } 
          if (bool)
            setDrawerViewOffset(view, f); 
          if (layoutParams.onScreen > 0.0F) {
            paramInt1 = 0;
          } else {
            paramInt1 = 4;
          } 
          if (view.getVisibility() != paramInt1)
            view.setVisibility(paramInt1); 
        } 
      } 
    } 
    this.mInLayout = false;
    this.mFirstLayout = false;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: iload_1
    //   1: invokestatic getMode : (I)I
    //   4: istore_3
    //   5: iload_2
    //   6: invokestatic getMode : (I)I
    //   9: istore #4
    //   11: iload_1
    //   12: invokestatic getSize : (I)I
    //   15: istore #5
    //   17: iload_2
    //   18: invokestatic getSize : (I)I
    //   21: istore #6
    //   23: iload_3
    //   24: ldc_w 1073741824
    //   27: if_icmpne -> 46
    //   30: iload #5
    //   32: istore #7
    //   34: iload #6
    //   36: istore #8
    //   38: iload #4
    //   40: ldc_w 1073741824
    //   43: if_icmpeq -> 113
    //   46: aload_0
    //   47: invokevirtual isInEditMode : ()Z
    //   50: ifeq -> 810
    //   53: iload_3
    //   54: ldc_w -2147483648
    //   57: if_icmpne -> 63
    //   60: goto -> 72
    //   63: iload_3
    //   64: ifne -> 72
    //   67: sipush #300
    //   70: istore #5
    //   72: iload #4
    //   74: ldc_w -2147483648
    //   77: if_icmpne -> 91
    //   80: iload #5
    //   82: istore #7
    //   84: iload #6
    //   86: istore #8
    //   88: goto -> 113
    //   91: iload #5
    //   93: istore #7
    //   95: iload #6
    //   97: istore #8
    //   99: iload #4
    //   101: ifne -> 113
    //   104: sipush #300
    //   107: istore #8
    //   109: iload #5
    //   111: istore #7
    //   113: aload_0
    //   114: iload #7
    //   116: iload #8
    //   118: invokevirtual setMeasuredDimension : (II)V
    //   121: aload_0
    //   122: getfield mLastInsets : Ljava/lang/Object;
    //   125: ifnull -> 141
    //   128: aload_0
    //   129: invokestatic getFitsSystemWindows : (Landroid/view/View;)Z
    //   132: ifeq -> 141
    //   135: iconst_1
    //   136: istore #4
    //   138: goto -> 144
    //   141: iconst_0
    //   142: istore #4
    //   144: aload_0
    //   145: invokestatic getLayoutDirection : (Landroid/view/View;)I
    //   148: istore #9
    //   150: aload_0
    //   151: invokevirtual getChildCount : ()I
    //   154: istore #10
    //   156: iconst_0
    //   157: istore_3
    //   158: iconst_0
    //   159: istore #6
    //   161: iconst_0
    //   162: istore #5
    //   164: iload_3
    //   165: iload #10
    //   167: if_icmpge -> 809
    //   170: aload_0
    //   171: iload_3
    //   172: invokevirtual getChildAt : (I)Landroid/view/View;
    //   175: astore #11
    //   177: aload #11
    //   179: invokevirtual getVisibility : ()I
    //   182: bipush #8
    //   184: if_icmpne -> 190
    //   187: goto -> 494
    //   190: aload #11
    //   192: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   195: checkcast android/support/v4/widget/DrawerLayout$LayoutParams
    //   198: astore #12
    //   200: iload #4
    //   202: ifeq -> 440
    //   205: aload #12
    //   207: getfield gravity : I
    //   210: iload #9
    //   212: invokestatic getAbsoluteGravity : (II)I
    //   215: istore #13
    //   217: aload #11
    //   219: invokestatic getFitsSystemWindows : (Landroid/view/View;)Z
    //   222: ifeq -> 318
    //   225: getstatic android/os/Build$VERSION.SDK_INT : I
    //   228: bipush #21
    //   230: if_icmplt -> 440
    //   233: aload_0
    //   234: getfield mLastInsets : Ljava/lang/Object;
    //   237: checkcast android/view/WindowInsets
    //   240: astore #14
    //   242: iload #13
    //   244: iconst_3
    //   245: if_icmpne -> 274
    //   248: aload #14
    //   250: aload #14
    //   252: invokevirtual getSystemWindowInsetLeft : ()I
    //   255: aload #14
    //   257: invokevirtual getSystemWindowInsetTop : ()I
    //   260: iconst_0
    //   261: aload #14
    //   263: invokevirtual getSystemWindowInsetBottom : ()I
    //   266: invokevirtual replaceSystemWindowInsets : (IIII)Landroid/view/WindowInsets;
    //   269: astore #15
    //   271: goto -> 307
    //   274: aload #14
    //   276: astore #15
    //   278: iload #13
    //   280: iconst_5
    //   281: if_icmpne -> 307
    //   284: aload #14
    //   286: iconst_0
    //   287: aload #14
    //   289: invokevirtual getSystemWindowInsetTop : ()I
    //   292: aload #14
    //   294: invokevirtual getSystemWindowInsetRight : ()I
    //   297: aload #14
    //   299: invokevirtual getSystemWindowInsetBottom : ()I
    //   302: invokevirtual replaceSystemWindowInsets : (IIII)Landroid/view/WindowInsets;
    //   305: astore #15
    //   307: aload #11
    //   309: aload #15
    //   311: invokevirtual dispatchApplyWindowInsets : (Landroid/view/WindowInsets;)Landroid/view/WindowInsets;
    //   314: pop
    //   315: goto -> 440
    //   318: getstatic android/os/Build$VERSION.SDK_INT : I
    //   321: bipush #21
    //   323: if_icmplt -> 440
    //   326: aload_0
    //   327: getfield mLastInsets : Ljava/lang/Object;
    //   330: checkcast android/view/WindowInsets
    //   333: astore #14
    //   335: iload #13
    //   337: iconst_3
    //   338: if_icmpne -> 367
    //   341: aload #14
    //   343: aload #14
    //   345: invokevirtual getSystemWindowInsetLeft : ()I
    //   348: aload #14
    //   350: invokevirtual getSystemWindowInsetTop : ()I
    //   353: iconst_0
    //   354: aload #14
    //   356: invokevirtual getSystemWindowInsetBottom : ()I
    //   359: invokevirtual replaceSystemWindowInsets : (IIII)Landroid/view/WindowInsets;
    //   362: astore #15
    //   364: goto -> 400
    //   367: aload #14
    //   369: astore #15
    //   371: iload #13
    //   373: iconst_5
    //   374: if_icmpne -> 400
    //   377: aload #14
    //   379: iconst_0
    //   380: aload #14
    //   382: invokevirtual getSystemWindowInsetTop : ()I
    //   385: aload #14
    //   387: invokevirtual getSystemWindowInsetRight : ()I
    //   390: aload #14
    //   392: invokevirtual getSystemWindowInsetBottom : ()I
    //   395: invokevirtual replaceSystemWindowInsets : (IIII)Landroid/view/WindowInsets;
    //   398: astore #15
    //   400: aload #12
    //   402: aload #15
    //   404: invokevirtual getSystemWindowInsetLeft : ()I
    //   407: putfield leftMargin : I
    //   410: aload #12
    //   412: aload #15
    //   414: invokevirtual getSystemWindowInsetTop : ()I
    //   417: putfield topMargin : I
    //   420: aload #12
    //   422: aload #15
    //   424: invokevirtual getSystemWindowInsetRight : ()I
    //   427: putfield rightMargin : I
    //   430: aload #12
    //   432: aload #15
    //   434: invokevirtual getSystemWindowInsetBottom : ()I
    //   437: putfield bottomMargin : I
    //   440: aload_0
    //   441: aload #11
    //   443: invokevirtual isContentView : (Landroid/view/View;)Z
    //   446: ifeq -> 497
    //   449: aload #11
    //   451: iload #7
    //   453: aload #12
    //   455: getfield leftMargin : I
    //   458: isub
    //   459: aload #12
    //   461: getfield rightMargin : I
    //   464: isub
    //   465: ldc_w 1073741824
    //   468: invokestatic makeMeasureSpec : (II)I
    //   471: iload #8
    //   473: aload #12
    //   475: getfield topMargin : I
    //   478: isub
    //   479: aload #12
    //   481: getfield bottomMargin : I
    //   484: isub
    //   485: ldc_w 1073741824
    //   488: invokestatic makeMeasureSpec : (II)I
    //   491: invokevirtual measure : (II)V
    //   494: goto -> 730
    //   497: aload_0
    //   498: aload #11
    //   500: invokevirtual isDrawerView : (Landroid/view/View;)Z
    //   503: ifeq -> 736
    //   506: getstatic android/support/v4/widget/DrawerLayout.SET_DRAWER_SHADOW_FROM_ELEVATION : Z
    //   509: ifeq -> 540
    //   512: aload #11
    //   514: invokestatic getElevation : (Landroid/view/View;)F
    //   517: fstore #16
    //   519: aload_0
    //   520: getfield mDrawerElevation : F
    //   523: fstore #17
    //   525: fload #16
    //   527: fload #17
    //   529: fcmpl
    //   530: ifeq -> 540
    //   533: aload #11
    //   535: fload #17
    //   537: invokestatic setElevation : (Landroid/view/View;F)V
    //   540: aload_0
    //   541: aload #11
    //   543: invokevirtual getDrawerViewAbsoluteGravity : (Landroid/view/View;)I
    //   546: bipush #7
    //   548: iand
    //   549: istore #18
    //   551: iload #18
    //   553: iconst_3
    //   554: if_icmpne -> 563
    //   557: iconst_1
    //   558: istore #13
    //   560: goto -> 566
    //   563: iconst_0
    //   564: istore #13
    //   566: iload #13
    //   568: ifeq -> 576
    //   571: iload #6
    //   573: ifne -> 589
    //   576: iload #13
    //   578: ifne -> 666
    //   581: iload #5
    //   583: ifne -> 589
    //   586: goto -> 666
    //   589: new java/lang/StringBuilder
    //   592: dup
    //   593: invokespecial <init> : ()V
    //   596: astore #15
    //   598: aload #15
    //   600: ldc_w 'Child drawer has absolute gravity '
    //   603: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   606: pop
    //   607: aload #15
    //   609: iload #18
    //   611: invokestatic gravityToString : (I)Ljava/lang/String;
    //   614: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   617: pop
    //   618: aload #15
    //   620: ldc_w ' but this '
    //   623: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   626: pop
    //   627: aload #15
    //   629: ldc 'DrawerLayout'
    //   631: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   634: pop
    //   635: aload #15
    //   637: ldc_w ' already has a '
    //   640: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   643: pop
    //   644: aload #15
    //   646: ldc_w 'drawer view along that edge'
    //   649: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   652: pop
    //   653: new java/lang/IllegalStateException
    //   656: dup
    //   657: aload #15
    //   659: invokevirtual toString : ()Ljava/lang/String;
    //   662: invokespecial <init> : (Ljava/lang/String;)V
    //   665: athrow
    //   666: iload #13
    //   668: ifeq -> 677
    //   671: iconst_1
    //   672: istore #6
    //   674: goto -> 680
    //   677: iconst_1
    //   678: istore #5
    //   680: aload #11
    //   682: iload_1
    //   683: aload_0
    //   684: getfield mMinDrawerMargin : I
    //   687: aload #12
    //   689: getfield leftMargin : I
    //   692: iadd
    //   693: aload #12
    //   695: getfield rightMargin : I
    //   698: iadd
    //   699: aload #12
    //   701: getfield width : I
    //   704: invokestatic getChildMeasureSpec : (III)I
    //   707: iload_2
    //   708: aload #12
    //   710: getfield topMargin : I
    //   713: aload #12
    //   715: getfield bottomMargin : I
    //   718: iadd
    //   719: aload #12
    //   721: getfield height : I
    //   724: invokestatic getChildMeasureSpec : (III)I
    //   727: invokevirtual measure : (II)V
    //   730: iinc #3, 1
    //   733: goto -> 164
    //   736: new java/lang/StringBuilder
    //   739: dup
    //   740: invokespecial <init> : ()V
    //   743: astore #15
    //   745: aload #15
    //   747: ldc_w 'Child '
    //   750: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   753: pop
    //   754: aload #15
    //   756: aload #11
    //   758: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   761: pop
    //   762: aload #15
    //   764: ldc_w ' at index '
    //   767: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   770: pop
    //   771: aload #15
    //   773: iload_3
    //   774: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   777: pop
    //   778: aload #15
    //   780: ldc_w ' does not have a valid layout_gravity - must be Gravity.LEFT, '
    //   783: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   786: pop
    //   787: aload #15
    //   789: ldc_w 'Gravity.RIGHT or Gravity.NO_GRAVITY'
    //   792: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   795: pop
    //   796: new java/lang/IllegalStateException
    //   799: dup
    //   800: aload #15
    //   802: invokevirtual toString : ()Ljava/lang/String;
    //   805: invokespecial <init> : (Ljava/lang/String;)V
    //   808: athrow
    //   809: return
    //   810: new java/lang/IllegalArgumentException
    //   813: dup
    //   814: ldc_w 'DrawerLayout must be measured with MeasureSpec.EXACTLY.'
    //   817: invokespecial <init> : (Ljava/lang/String;)V
    //   820: athrow
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable) {
    if (!(paramParcelable instanceof SavedState)) {
      super.onRestoreInstanceState(paramParcelable);
      return;
    } 
    SavedState savedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(savedState.getSuperState());
    if (savedState.openDrawerGravity != 0) {
      View view = findDrawerWithGravity(savedState.openDrawerGravity);
      if (view != null)
        openDrawer(view); 
    } 
    if (savedState.lockModeLeft != 3)
      setDrawerLockMode(savedState.lockModeLeft, 3); 
    if (savedState.lockModeRight != 3)
      setDrawerLockMode(savedState.lockModeRight, 5); 
    if (savedState.lockModeStart != 3)
      setDrawerLockMode(savedState.lockModeStart, 8388611); 
    if (savedState.lockModeEnd != 3)
      setDrawerLockMode(savedState.lockModeEnd, 8388613); 
  }
  
  public void onRtlPropertiesChanged(int paramInt) {
    resolveShadowDrawables();
  }
  
  protected Parcelable onSaveInstanceState() {
    SavedState savedState = new SavedState(super.onSaveInstanceState());
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      LayoutParams layoutParams = (LayoutParams)getChildAt(b).getLayoutParams();
      int j = layoutParams.openState;
      boolean bool = true;
      if (j == 1) {
        j = 1;
      } else {
        j = 0;
      } 
      if (layoutParams.openState != 2)
        bool = false; 
      if (j != 0 || bool) {
        savedState.openDrawerGravity = layoutParams.gravity;
        break;
      } 
    } 
    savedState.lockModeLeft = this.mLockModeLeft;
    savedState.lockModeRight = this.mLockModeRight;
    savedState.lockModeStart = this.mLockModeStart;
    savedState.lockModeEnd = this.mLockModeEnd;
    return (Parcelable)savedState;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mLeftDragger : Landroid/support/v4/widget/ViewDragHelper;
    //   4: aload_1
    //   5: invokevirtual processTouchEvent : (Landroid/view/MotionEvent;)V
    //   8: aload_0
    //   9: getfield mRightDragger : Landroid/support/v4/widget/ViewDragHelper;
    //   12: aload_1
    //   13: invokevirtual processTouchEvent : (Landroid/view/MotionEvent;)V
    //   16: aload_1
    //   17: invokevirtual getAction : ()I
    //   20: sipush #255
    //   23: iand
    //   24: istore_2
    //   25: iload_2
    //   26: ifeq -> 181
    //   29: iload_2
    //   30: iconst_1
    //   31: if_icmpeq -> 60
    //   34: iload_2
    //   35: iconst_3
    //   36: if_icmpeq -> 42
    //   39: goto -> 213
    //   42: aload_0
    //   43: iconst_1
    //   44: invokevirtual closeDrawers : (Z)V
    //   47: aload_0
    //   48: iconst_0
    //   49: putfield mDisallowInterceptRequested : Z
    //   52: aload_0
    //   53: iconst_0
    //   54: putfield mChildrenCanceledTouch : Z
    //   57: goto -> 213
    //   60: aload_1
    //   61: invokevirtual getX : ()F
    //   64: fstore_3
    //   65: aload_1
    //   66: invokevirtual getY : ()F
    //   69: fstore #4
    //   71: aload_0
    //   72: getfield mLeftDragger : Landroid/support/v4/widget/ViewDragHelper;
    //   75: fload_3
    //   76: f2i
    //   77: fload #4
    //   79: f2i
    //   80: invokevirtual findTopChildUnder : (II)Landroid/view/View;
    //   83: astore_1
    //   84: aload_1
    //   85: ifnull -> 164
    //   88: aload_0
    //   89: aload_1
    //   90: invokevirtual isContentView : (Landroid/view/View;)Z
    //   93: ifeq -> 164
    //   96: fload_3
    //   97: aload_0
    //   98: getfield mInitialMotionX : F
    //   101: fsub
    //   102: fstore_3
    //   103: fload #4
    //   105: aload_0
    //   106: getfield mInitialMotionY : F
    //   109: fsub
    //   110: fstore #4
    //   112: aload_0
    //   113: getfield mLeftDragger : Landroid/support/v4/widget/ViewDragHelper;
    //   116: invokevirtual getTouchSlop : ()I
    //   119: istore_2
    //   120: fload_3
    //   121: fload_3
    //   122: fmul
    //   123: fload #4
    //   125: fload #4
    //   127: fmul
    //   128: fadd
    //   129: iload_2
    //   130: iload_2
    //   131: imul
    //   132: i2f
    //   133: fcmpg
    //   134: ifge -> 164
    //   137: aload_0
    //   138: invokevirtual findOpenDrawer : ()Landroid/view/View;
    //   141: astore_1
    //   142: aload_1
    //   143: ifnull -> 164
    //   146: aload_0
    //   147: aload_1
    //   148: invokevirtual getDrawerLockMode : (Landroid/view/View;)I
    //   151: iconst_2
    //   152: if_icmpne -> 158
    //   155: goto -> 164
    //   158: iconst_0
    //   159: istore #5
    //   161: goto -> 167
    //   164: iconst_1
    //   165: istore #5
    //   167: aload_0
    //   168: iload #5
    //   170: invokevirtual closeDrawers : (Z)V
    //   173: aload_0
    //   174: iconst_0
    //   175: putfield mDisallowInterceptRequested : Z
    //   178: goto -> 213
    //   181: aload_1
    //   182: invokevirtual getX : ()F
    //   185: fstore #4
    //   187: aload_1
    //   188: invokevirtual getY : ()F
    //   191: fstore_3
    //   192: aload_0
    //   193: fload #4
    //   195: putfield mInitialMotionX : F
    //   198: aload_0
    //   199: fload_3
    //   200: putfield mInitialMotionY : F
    //   203: aload_0
    //   204: iconst_0
    //   205: putfield mDisallowInterceptRequested : Z
    //   208: aload_0
    //   209: iconst_0
    //   210: putfield mChildrenCanceledTouch : Z
    //   213: iconst_1
    //   214: ireturn
  }
  
  public void openDrawer(int paramInt) {
    openDrawer(paramInt, true);
  }
  
  public void openDrawer(int paramInt, boolean paramBoolean) {
    View view = findDrawerWithGravity(paramInt);
    if (view != null) {
      openDrawer(view, paramBoolean);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No drawer view found with gravity ");
    stringBuilder.append(gravityToString(paramInt));
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void openDrawer(View paramView) {
    openDrawer(paramView, true);
  }
  
  public void openDrawer(View paramView, boolean paramBoolean) {
    if (isDrawerView(paramView)) {
      LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
      if (this.mFirstLayout) {
        layoutParams.onScreen = 1.0F;
        layoutParams.openState = 1;
        updateChildrenImportantForAccessibility(paramView, true);
      } else if (paramBoolean) {
        layoutParams.openState |= 0x2;
        if (checkDrawerViewAbsoluteGravity(paramView, 3)) {
          this.mLeftDragger.smoothSlideViewTo(paramView, 0, paramView.getTop());
        } else {
          this.mRightDragger.smoothSlideViewTo(paramView, getWidth() - paramView.getWidth(), paramView.getTop());
        } 
      } else {
        moveDrawerToOffset(paramView, 1.0F);
        updateDrawerState(layoutParams.gravity, 0, paramView);
        paramView.setVisibility(0);
      } 
      invalidate();
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("View ");
    stringBuilder.append(paramView);
    stringBuilder.append(" is not a sliding drawer");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void removeDrawerListener(DrawerListener paramDrawerListener) {
    if (paramDrawerListener == null)
      return; 
    List<DrawerListener> list = this.mListeners;
    if (list == null)
      return; 
    list.remove(paramDrawerListener);
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean) {
    super.requestDisallowInterceptTouchEvent(paramBoolean);
    this.mDisallowInterceptRequested = paramBoolean;
    if (paramBoolean)
      closeDrawers(true); 
  }
  
  public void requestLayout() {
    if (!this.mInLayout)
      super.requestLayout(); 
  }
  
  public void setChildInsets(Object paramObject, boolean paramBoolean) {
    this.mLastInsets = paramObject;
    this.mDrawStatusBarBackground = paramBoolean;
    if (!paramBoolean && getBackground() == null) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    } 
    setWillNotDraw(paramBoolean);
    requestLayout();
  }
  
  public void setDrawerElevation(float paramFloat) {
    this.mDrawerElevation = paramFloat;
    for (byte b = 0; b < getChildCount(); b++) {
      View view = getChildAt(b);
      if (isDrawerView(view))
        ViewCompat.setElevation(view, this.mDrawerElevation); 
    } 
  }
  
  @Deprecated
  public void setDrawerListener(DrawerListener paramDrawerListener) {
    DrawerListener drawerListener = this.mListener;
    if (drawerListener != null)
      removeDrawerListener(drawerListener); 
    if (paramDrawerListener != null)
      addDrawerListener(paramDrawerListener); 
    this.mListener = paramDrawerListener;
  }
  
  public void setDrawerLockMode(int paramInt) {
    setDrawerLockMode(paramInt, 3);
    setDrawerLockMode(paramInt, 5);
  }
  
  public void setDrawerLockMode(int paramInt1, int paramInt2) {
    int i = GravityCompat.getAbsoluteGravity(paramInt2, ViewCompat.getLayoutDirection((View)this));
    if (paramInt2 != 3) {
      if (paramInt2 != 5) {
        if (paramInt2 != 8388611) {
          if (paramInt2 == 8388613)
            this.mLockModeEnd = paramInt1; 
        } else {
          this.mLockModeStart = paramInt1;
        } 
      } else {
        this.mLockModeRight = paramInt1;
      } 
    } else {
      this.mLockModeLeft = paramInt1;
    } 
    if (paramInt1 != 0) {
      ViewDragHelper viewDragHelper;
      if (i == 3) {
        viewDragHelper = this.mLeftDragger;
      } else {
        viewDragHelper = this.mRightDragger;
      } 
      viewDragHelper.cancel();
    } 
    if (paramInt1 != 1) {
      if (paramInt1 == 2) {
        View view = findDrawerWithGravity(i);
        if (view != null)
          openDrawer(view); 
      } 
    } else {
      View view = findDrawerWithGravity(i);
      if (view != null)
        closeDrawer(view); 
    } 
  }
  
  public void setDrawerLockMode(int paramInt, View paramView) {
    if (isDrawerView(paramView)) {
      setDrawerLockMode(paramInt, ((LayoutParams)paramView.getLayoutParams()).gravity);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("View ");
    stringBuilder.append(paramView);
    stringBuilder.append(" is not a ");
    stringBuilder.append("drawer with appropriate layout_gravity");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void setDrawerShadow(int paramInt1, int paramInt2) {
    setDrawerShadow(ContextCompat.getDrawable(getContext(), paramInt1), paramInt2);
  }
  
  public void setDrawerShadow(Drawable paramDrawable, int paramInt) {
    if (SET_DRAWER_SHADOW_FROM_ELEVATION)
      return; 
    if ((paramInt & 0x800003) == 8388611) {
      this.mShadowStart = paramDrawable;
    } else if ((paramInt & 0x800005) == 8388613) {
      this.mShadowEnd = paramDrawable;
    } else if ((paramInt & 0x3) == 3) {
      this.mShadowLeft = paramDrawable;
    } else if ((paramInt & 0x5) == 5) {
      this.mShadowRight = paramDrawable;
    } else {
      return;
    } 
    resolveShadowDrawables();
    invalidate();
  }
  
  public void setDrawerTitle(int paramInt, CharSequence paramCharSequence) {
    paramInt = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection((View)this));
    if (paramInt == 3) {
      this.mTitleLeft = paramCharSequence;
    } else if (paramInt == 5) {
      this.mTitleRight = paramCharSequence;
    } 
  }
  
  void setDrawerViewOffset(View paramView, float paramFloat) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if (paramFloat == layoutParams.onScreen)
      return; 
    layoutParams.onScreen = paramFloat;
    dispatchOnDrawerSlide(paramView, paramFloat);
  }
  
  public void setScrimColor(int paramInt) {
    this.mScrimColor = paramInt;
    invalidate();
  }
  
  public void setStatusBarBackground(int paramInt) {
    Drawable drawable;
    if (paramInt != 0) {
      drawable = ContextCompat.getDrawable(getContext(), paramInt);
    } else {
      drawable = null;
    } 
    this.mStatusBarBackground = drawable;
    invalidate();
  }
  
  public void setStatusBarBackground(Drawable paramDrawable) {
    this.mStatusBarBackground = paramDrawable;
    invalidate();
  }
  
  public void setStatusBarBackgroundColor(int paramInt) {
    this.mStatusBarBackground = (Drawable)new ColorDrawable(paramInt);
    invalidate();
  }
  
  void updateDrawerState(int paramInt1, int paramInt2, View paramView) {
    int i = this.mLeftDragger.getViewDragState();
    int j = this.mRightDragger.getViewDragState();
    byte b = 2;
    if (i == 1 || j == 1) {
      paramInt1 = 1;
    } else {
      paramInt1 = b;
      if (i != 2)
        if (j == 2) {
          paramInt1 = b;
        } else {
          paramInt1 = 0;
        }  
    } 
    if (paramView != null && paramInt2 == 0) {
      LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
      if (layoutParams.onScreen == 0.0F) {
        dispatchOnDrawerClosed(paramView);
      } else if (layoutParams.onScreen == 1.0F) {
        dispatchOnDrawerOpened(paramView);
      } 
    } 
    if (paramInt1 != this.mDrawerState) {
      this.mDrawerState = paramInt1;
      List<DrawerListener> list = this.mListeners;
      if (list != null)
        for (paramInt2 = list.size() - 1; paramInt2 >= 0; paramInt2--)
          ((DrawerListener)this.mListeners.get(paramInt2)).onDrawerStateChanged(paramInt1);  
    } 
  }
  
  static {
    boolean bool2;
    boolean bool1 = true;
  }
  
  class AccessibilityDelegate extends AccessibilityDelegateCompat {
    private final Rect mTmpRect = new Rect();
    
    private void addChildrenForAccessibility(AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat, ViewGroup param1ViewGroup) {
      int i = param1ViewGroup.getChildCount();
      for (byte b = 0; b < i; b++) {
        View view = param1ViewGroup.getChildAt(b);
        if (DrawerLayout.includeChildForAccessibility(view))
          param1AccessibilityNodeInfoCompat.addChild(view); 
      } 
    }
    
    private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat1, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat2) {
      Rect rect = this.mTmpRect;
      param1AccessibilityNodeInfoCompat2.getBoundsInParent(rect);
      param1AccessibilityNodeInfoCompat1.setBoundsInParent(rect);
      param1AccessibilityNodeInfoCompat2.getBoundsInScreen(rect);
      param1AccessibilityNodeInfoCompat1.setBoundsInScreen(rect);
      param1AccessibilityNodeInfoCompat1.setVisibleToUser(param1AccessibilityNodeInfoCompat2.isVisibleToUser());
      param1AccessibilityNodeInfoCompat1.setPackageName(param1AccessibilityNodeInfoCompat2.getPackageName());
      param1AccessibilityNodeInfoCompat1.setClassName(param1AccessibilityNodeInfoCompat2.getClassName());
      param1AccessibilityNodeInfoCompat1.setContentDescription(param1AccessibilityNodeInfoCompat2.getContentDescription());
      param1AccessibilityNodeInfoCompat1.setEnabled(param1AccessibilityNodeInfoCompat2.isEnabled());
      param1AccessibilityNodeInfoCompat1.setClickable(param1AccessibilityNodeInfoCompat2.isClickable());
      param1AccessibilityNodeInfoCompat1.setFocusable(param1AccessibilityNodeInfoCompat2.isFocusable());
      param1AccessibilityNodeInfoCompat1.setFocused(param1AccessibilityNodeInfoCompat2.isFocused());
      param1AccessibilityNodeInfoCompat1.setAccessibilityFocused(param1AccessibilityNodeInfoCompat2.isAccessibilityFocused());
      param1AccessibilityNodeInfoCompat1.setSelected(param1AccessibilityNodeInfoCompat2.isSelected());
      param1AccessibilityNodeInfoCompat1.setLongClickable(param1AccessibilityNodeInfoCompat2.isLongClickable());
      param1AccessibilityNodeInfoCompat1.addAction(param1AccessibilityNodeInfoCompat2.getActions());
    }
    
    public boolean dispatchPopulateAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      List<CharSequence> list;
      CharSequence charSequence;
      if (param1AccessibilityEvent.getEventType() == 32) {
        list = param1AccessibilityEvent.getText();
        View view = DrawerLayout.this.findVisibleDrawer();
        if (view != null) {
          int i = DrawerLayout.this.getDrawerViewAbsoluteGravity(view);
          charSequence = DrawerLayout.this.getDrawerTitle(i);
          if (charSequence != null)
            list.add(charSequence); 
        } 
        return true;
      } 
      return super.dispatchPopulateAccessibilityEvent((View)list, (AccessibilityEvent)charSequence);
    }
    
    public void onInitializeAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      super.onInitializeAccessibilityEvent(param1View, param1AccessibilityEvent);
      param1AccessibilityEvent.setClassName(DrawerLayout.class.getName());
    }
    
    public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
      if (DrawerLayout.CAN_HIDE_DESCENDANTS) {
        super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfoCompat);
      } else {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = AccessibilityNodeInfoCompat.obtain(param1AccessibilityNodeInfoCompat);
        super.onInitializeAccessibilityNodeInfo(param1View, accessibilityNodeInfoCompat);
        param1AccessibilityNodeInfoCompat.setSource(param1View);
        ViewParent viewParent = ViewCompat.getParentForAccessibility(param1View);
        if (viewParent instanceof View)
          param1AccessibilityNodeInfoCompat.setParent((View)viewParent); 
        copyNodeInfoNoChildren(param1AccessibilityNodeInfoCompat, accessibilityNodeInfoCompat);
        accessibilityNodeInfoCompat.recycle();
        addChildrenForAccessibility(param1AccessibilityNodeInfoCompat, (ViewGroup)param1View);
      } 
      param1AccessibilityNodeInfoCompat.setClassName(DrawerLayout.class.getName());
      param1AccessibilityNodeInfoCompat.setFocusable(false);
      param1AccessibilityNodeInfoCompat.setFocused(false);
      param1AccessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_FOCUS);
      param1AccessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLEAR_FOCUS);
    }
    
    public boolean onRequestSendAccessibilityEvent(ViewGroup param1ViewGroup, View param1View, AccessibilityEvent param1AccessibilityEvent) {
      return (DrawerLayout.CAN_HIDE_DESCENDANTS || DrawerLayout.includeChildForAccessibility(param1View)) ? super.onRequestSendAccessibilityEvent(param1ViewGroup, param1View, param1AccessibilityEvent) : false;
    }
  }
  
  static final class ChildAccessibilityDelegate extends AccessibilityDelegateCompat {
    public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
      super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfoCompat);
      if (!DrawerLayout.includeChildForAccessibility(param1View))
        param1AccessibilityNodeInfoCompat.setParent(null); 
    }
  }
  
  public static interface DrawerListener {
    void onDrawerClosed(View param1View);
    
    void onDrawerOpened(View param1View);
    
    void onDrawerSlide(View param1View, float param1Float);
    
    void onDrawerStateChanged(int param1Int);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  private static @interface EdgeGravity {}
  
  public static class LayoutParams extends ViewGroup.MarginLayoutParams {
    private static final int FLAG_IS_CLOSING = 4;
    
    private static final int FLAG_IS_OPENED = 1;
    
    private static final int FLAG_IS_OPENING = 2;
    
    public int gravity = 0;
    
    boolean isPeeking;
    
    float onScreen;
    
    int openState;
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
    }
    
    public LayoutParams(int param1Int1, int param1Int2, int param1Int3) {
      this(param1Int1, param1Int2);
      this.gravity = param1Int3;
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, DrawerLayout.LAYOUT_ATTRS);
      this.gravity = typedArray.getInt(0, 0);
      typedArray.recycle();
    }
    
    public LayoutParams(LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
      this.gravity = param1LayoutParams.gravity;
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams param1MarginLayoutParams) {
      super(param1MarginLayoutParams);
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  private static @interface LockMode {}
  
  protected static class SavedState extends AbsSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR = (Parcelable.Creator<SavedState>)new Parcelable.ClassLoaderCreator<SavedState>() {
        public DrawerLayout.SavedState createFromParcel(Parcel param2Parcel) {
          return new DrawerLayout.SavedState(param2Parcel, null);
        }
        
        public DrawerLayout.SavedState createFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
          return new DrawerLayout.SavedState(param2Parcel, param2ClassLoader);
        }
        
        public DrawerLayout.SavedState[] newArray(int param2Int) {
          return new DrawerLayout.SavedState[param2Int];
        }
      };
    
    int lockModeEnd;
    
    int lockModeLeft;
    
    int lockModeRight;
    
    int lockModeStart;
    
    int openDrawerGravity = 0;
    
    public SavedState(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      super(param1Parcel, param1ClassLoader);
      this.openDrawerGravity = param1Parcel.readInt();
      this.lockModeLeft = param1Parcel.readInt();
      this.lockModeRight = param1Parcel.readInt();
      this.lockModeStart = param1Parcel.readInt();
      this.lockModeEnd = param1Parcel.readInt();
    }
    
    public SavedState(Parcelable param1Parcelable) {
      super(param1Parcelable);
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      super.writeToParcel(param1Parcel, param1Int);
      param1Parcel.writeInt(this.openDrawerGravity);
      param1Parcel.writeInt(this.lockModeLeft);
      param1Parcel.writeInt(this.lockModeRight);
      param1Parcel.writeInt(this.lockModeStart);
      param1Parcel.writeInt(this.lockModeEnd);
    }
  }
  
  static final class null implements Parcelable.ClassLoaderCreator<SavedState> {
    public DrawerLayout.SavedState createFromParcel(Parcel param1Parcel) {
      return new DrawerLayout.SavedState(param1Parcel, null);
    }
    
    public DrawerLayout.SavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return new DrawerLayout.SavedState(param1Parcel, param1ClassLoader);
    }
    
    public DrawerLayout.SavedState[] newArray(int param1Int) {
      return new DrawerLayout.SavedState[param1Int];
    }
  }
  
  public static abstract class SimpleDrawerListener implements DrawerListener {
    public void onDrawerClosed(View param1View) {}
    
    public void onDrawerOpened(View param1View) {}
    
    public void onDrawerSlide(View param1View, float param1Float) {}
    
    public void onDrawerStateChanged(int param1Int) {}
  }
  
  @Retention(RetentionPolicy.SOURCE)
  private static @interface State {}
  
  private class ViewDragCallback extends ViewDragHelper.Callback {
    private final int mAbsGravity;
    
    private ViewDragHelper mDragger;
    
    private final Runnable mPeekRunnable = new Runnable() {
        public void run() {
          DrawerLayout.ViewDragCallback.this.peekDrawer();
        }
      };
    
    ViewDragCallback(int param1Int) {
      this.mAbsGravity = param1Int;
    }
    
    private void closeOtherDrawer() {
      int i = this.mAbsGravity;
      byte b = 3;
      if (i == 3)
        b = 5; 
      View view = DrawerLayout.this.findDrawerWithGravity(b);
      if (view != null)
        DrawerLayout.this.closeDrawer(view); 
    }
    
    public int clampViewPositionHorizontal(View param1View, int param1Int1, int param1Int2) {
      if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(param1View, 3))
        return Math.max(-param1View.getWidth(), Math.min(param1Int1, 0)); 
      param1Int2 = DrawerLayout.this.getWidth();
      return Math.max(param1Int2 - param1View.getWidth(), Math.min(param1Int1, param1Int2));
    }
    
    public int clampViewPositionVertical(View param1View, int param1Int1, int param1Int2) {
      return param1View.getTop();
    }
    
    public int getViewHorizontalDragRange(View param1View) {
      boolean bool;
      if (DrawerLayout.this.isDrawerView(param1View)) {
        bool = param1View.getWidth();
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void onEdgeDragStarted(int param1Int1, int param1Int2) {
      View view;
      if ((param1Int1 & 0x1) == 1) {
        view = DrawerLayout.this.findDrawerWithGravity(3);
      } else {
        view = DrawerLayout.this.findDrawerWithGravity(5);
      } 
      if (view != null && DrawerLayout.this.getDrawerLockMode(view) == 0)
        this.mDragger.captureChildView(view, param1Int2); 
    }
    
    public boolean onEdgeLock(int param1Int) {
      return false;
    }
    
    public void onEdgeTouched(int param1Int1, int param1Int2) {
      DrawerLayout.this.postDelayed(this.mPeekRunnable, 160L);
    }
    
    public void onViewCaptured(View param1View, int param1Int) {
      ((DrawerLayout.LayoutParams)param1View.getLayoutParams()).isPeeking = false;
      closeOtherDrawer();
    }
    
    public void onViewDragStateChanged(int param1Int) {
      DrawerLayout.this.updateDrawerState(this.mAbsGravity, param1Int, this.mDragger.getCapturedView());
    }
    
    public void onViewPositionChanged(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      float f;
      param1Int2 = param1View.getWidth();
      if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(param1View, 3)) {
        f = (param1Int1 + param1Int2);
      } else {
        f = (DrawerLayout.this.getWidth() - param1Int1);
      } 
      f /= param1Int2;
      DrawerLayout.this.setDrawerViewOffset(param1View, f);
      if (f == 0.0F) {
        param1Int1 = 4;
      } else {
        param1Int1 = 0;
      } 
      param1View.setVisibility(param1Int1);
      DrawerLayout.this.invalidate();
    }
    
    public void onViewReleased(View param1View, float param1Float1, float param1Float2) {
      // Byte code:
      //   0: aload_0
      //   1: getfield this$0 : Landroid/support/v4/widget/DrawerLayout;
      //   4: aload_1
      //   5: invokevirtual getDrawerViewOffset : (Landroid/view/View;)F
      //   8: fstore_3
      //   9: aload_1
      //   10: invokevirtual getWidth : ()I
      //   13: istore #4
      //   15: aload_0
      //   16: getfield this$0 : Landroid/support/v4/widget/DrawerLayout;
      //   19: aload_1
      //   20: iconst_3
      //   21: invokevirtual checkDrawerViewAbsoluteGravity : (Landroid/view/View;I)Z
      //   24: ifeq -> 66
      //   27: fload_2
      //   28: fconst_0
      //   29: fcmpl
      //   30: istore #5
      //   32: iload #5
      //   34: ifgt -> 60
      //   37: iload #5
      //   39: ifne -> 52
      //   42: fload_3
      //   43: ldc 0.5
      //   45: fcmpl
      //   46: ifle -> 52
      //   49: goto -> 60
      //   52: iload #4
      //   54: ineg
      //   55: istore #5
      //   57: goto -> 109
      //   60: iconst_0
      //   61: istore #5
      //   63: goto -> 109
      //   66: aload_0
      //   67: getfield this$0 : Landroid/support/v4/widget/DrawerLayout;
      //   70: invokevirtual getWidth : ()I
      //   73: istore #6
      //   75: fload_2
      //   76: fconst_0
      //   77: fcmpg
      //   78: iflt -> 102
      //   81: iload #6
      //   83: istore #5
      //   85: fload_2
      //   86: fconst_0
      //   87: fcmpl
      //   88: ifne -> 109
      //   91: iload #6
      //   93: istore #5
      //   95: fload_3
      //   96: ldc 0.5
      //   98: fcmpl
      //   99: ifle -> 109
      //   102: iload #6
      //   104: iload #4
      //   106: isub
      //   107: istore #5
      //   109: aload_0
      //   110: getfield mDragger : Landroid/support/v4/widget/ViewDragHelper;
      //   113: iload #5
      //   115: aload_1
      //   116: invokevirtual getTop : ()I
      //   119: invokevirtual settleCapturedViewAt : (II)Z
      //   122: pop
      //   123: aload_0
      //   124: getfield this$0 : Landroid/support/v4/widget/DrawerLayout;
      //   127: invokevirtual invalidate : ()V
      //   130: return
    }
    
    void peekDrawer() {
      View view;
      int i = this.mDragger.getEdgeSize();
      int j = this.mAbsGravity;
      int k = 0;
      if (j == 3) {
        j = 1;
      } else {
        j = 0;
      } 
      if (j != 0) {
        view = DrawerLayout.this.findDrawerWithGravity(3);
        if (view != null)
          k = -view.getWidth(); 
        k += i;
      } else {
        view = DrawerLayout.this.findDrawerWithGravity(5);
        k = DrawerLayout.this.getWidth() - i;
      } 
      if (view != null && ((j != 0 && view.getLeft() < k) || (j == 0 && view.getLeft() > k)) && DrawerLayout.this.getDrawerLockMode(view) == 0) {
        DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams)view.getLayoutParams();
        this.mDragger.smoothSlideViewTo(view, k, view.getTop());
        layoutParams.isPeeking = true;
        DrawerLayout.this.invalidate();
        closeOtherDrawer();
        DrawerLayout.this.cancelChildViewTouch();
      } 
    }
    
    public void removeCallbacks() {
      DrawerLayout.this.removeCallbacks(this.mPeekRunnable);
    }
    
    public void setDragger(ViewDragHelper param1ViewDragHelper) {
      this.mDragger = param1ViewDragHelper;
    }
    
    public boolean tryCaptureView(View param1View, int param1Int) {
      boolean bool;
      if (DrawerLayout.this.isDrawerView(param1View) && DrawerLayout.this.checkDrawerViewAbsoluteGravity(param1View, this.mAbsGravity) && DrawerLayout.this.getDrawerLockMode(param1View) == 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
  }
  
  class null implements Runnable {
    public void run() {
      this.this$1.peekDrawer();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\DrawerLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */