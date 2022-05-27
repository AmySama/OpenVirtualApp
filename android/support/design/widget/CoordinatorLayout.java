package android.support.design.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.coreui.R;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.math.MathUtils;
import android.support.v4.util.ObjectsCompat;
import android.support.v4.util.Pools;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v4.widget.DirectedAcyclicGraph;
import android.support.v4.widget.ViewGroupUtils;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinatorLayout extends ViewGroup implements NestedScrollingParent2 {
  static final Class<?>[] CONSTRUCTOR_PARAMS;
  
  static final int EVENT_NESTED_SCROLL = 1;
  
  static final int EVENT_PRE_DRAW = 0;
  
  static final int EVENT_VIEW_REMOVED = 2;
  
  static final String TAG = "CoordinatorLayout";
  
  static final Comparator<View> TOP_SORTED_CHILDREN_COMPARATOR;
  
  private static final int TYPE_ON_INTERCEPT = 0;
  
  private static final int TYPE_ON_TOUCH = 1;
  
  static final String WIDGET_PACKAGE_NAME;
  
  static final ThreadLocal<Map<String, Constructor<Behavior>>> sConstructors;
  
  private static final Pools.Pool<Rect> sRectPool;
  
  private OnApplyWindowInsetsListener mApplyWindowInsetsListener;
  
  private View mBehaviorTouchView;
  
  private final DirectedAcyclicGraph<View> mChildDag;
  
  private final List<View> mDependencySortedChildren;
  
  private boolean mDisallowInterceptReset;
  
  private boolean mDrawStatusBarBackground;
  
  private boolean mIsAttachedToWindow;
  
  private int[] mKeylines;
  
  private WindowInsetsCompat mLastInsets;
  
  private boolean mNeedsPreDrawListener;
  
  private final NestedScrollingParentHelper mNestedScrollingParentHelper;
  
  private View mNestedScrollingTarget;
  
  ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;
  
  private OnPreDrawListener mOnPreDrawListener;
  
  private Paint mScrimPaint;
  
  private Drawable mStatusBarBackground;
  
  private final List<View> mTempDependenciesList;
  
  private final int[] mTempIntPair;
  
  private final List<View> mTempList1;
  
  static {
    Package package_ = CoordinatorLayout.class.getPackage();
    if (package_ != null) {
      String str = package_.getName();
    } else {
      package_ = null;
    } 
    WIDGET_PACKAGE_NAME = (String)package_;
    if (Build.VERSION.SDK_INT >= 21) {
      TOP_SORTED_CHILDREN_COMPARATOR = new ViewElevationComparator();
    } else {
      TOP_SORTED_CHILDREN_COMPARATOR = null;
    } 
    CONSTRUCTOR_PARAMS = new Class[] { Context.class, AttributeSet.class };
    sConstructors = new ThreadLocal<Map<String, Constructor<Behavior>>>();
    sRectPool = (Pools.Pool<Rect>)new Pools.SynchronizedPool(12);
  }
  
  public CoordinatorLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public CoordinatorLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, R.attr.coordinatorLayoutStyle);
  }
  
  public CoordinatorLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray typedArray;
    this.mDependencySortedChildren = new ArrayList<View>();
    this.mChildDag = new DirectedAcyclicGraph();
    this.mTempList1 = new ArrayList<View>();
    this.mTempDependenciesList = new ArrayList<View>();
    this.mTempIntPair = new int[2];
    this.mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    boolean bool = false;
    if (paramInt == 0) {
      typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.CoordinatorLayout, 0, R.style.Widget_Support_CoordinatorLayout);
    } else {
      typedArray = paramContext.obtainStyledAttributes((AttributeSet)typedArray, R.styleable.CoordinatorLayout, paramInt, 0);
    } 
    paramInt = typedArray.getResourceId(R.styleable.CoordinatorLayout_keylines, 0);
    if (paramInt != 0) {
      Resources resources = paramContext.getResources();
      this.mKeylines = resources.getIntArray(paramInt);
      float f = (resources.getDisplayMetrics()).density;
      int i = this.mKeylines.length;
      for (paramInt = bool; paramInt < i; paramInt++) {
        int[] arrayOfInt = this.mKeylines;
        arrayOfInt[paramInt] = (int)(arrayOfInt[paramInt] * f);
      } 
    } 
    this.mStatusBarBackground = typedArray.getDrawable(R.styleable.CoordinatorLayout_statusBarBackground);
    typedArray.recycle();
    setupForInsets();
    super.setOnHierarchyChangeListener(new HierarchyChangeListener());
  }
  
  private static Rect acquireTempRect() {
    Rect rect1 = (Rect)sRectPool.acquire();
    Rect rect2 = rect1;
    if (rect1 == null)
      rect2 = new Rect(); 
    return rect2;
  }
  
  private void constrainChildRect(LayoutParams paramLayoutParams, Rect paramRect, int paramInt1, int paramInt2) {
    int i = getWidth();
    int j = getHeight();
    i = Math.max(getPaddingLeft() + paramLayoutParams.leftMargin, Math.min(paramRect.left, i - getPaddingRight() - paramInt1 - paramLayoutParams.rightMargin));
    j = Math.max(getPaddingTop() + paramLayoutParams.topMargin, Math.min(paramRect.top, j - getPaddingBottom() - paramInt2 - paramLayoutParams.bottomMargin));
    paramRect.set(i, j, paramInt1 + i, paramInt2 + j);
  }
  
  private WindowInsetsCompat dispatchApplyWindowInsetsToBehaviors(WindowInsetsCompat paramWindowInsetsCompat) {
    WindowInsetsCompat windowInsetsCompat;
    if (paramWindowInsetsCompat.isConsumed())
      return paramWindowInsetsCompat; 
    byte b = 0;
    int i = getChildCount();
    while (true) {
      windowInsetsCompat = paramWindowInsetsCompat;
      if (b < i) {
        View view = getChildAt(b);
        windowInsetsCompat = paramWindowInsetsCompat;
        if (ViewCompat.getFitsSystemWindows(view)) {
          Behavior<View> behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
          windowInsetsCompat = paramWindowInsetsCompat;
          if (behavior != null) {
            paramWindowInsetsCompat = behavior.onApplyWindowInsets(this, view, paramWindowInsetsCompat);
            windowInsetsCompat = paramWindowInsetsCompat;
            if (paramWindowInsetsCompat.isConsumed()) {
              windowInsetsCompat = paramWindowInsetsCompat;
              break;
            } 
          } 
        } 
        b++;
        paramWindowInsetsCompat = windowInsetsCompat;
        continue;
      } 
      break;
    } 
    return windowInsetsCompat;
  }
  
  private void getDesiredAnchoredChildRectWithoutConstraints(View paramView, int paramInt1, Rect paramRect1, Rect paramRect2, LayoutParams paramLayoutParams, int paramInt2, int paramInt3) {
    int i = GravityCompat.getAbsoluteGravity(resolveAnchoredChildGravity(paramLayoutParams.gravity), paramInt1);
    int j = GravityCompat.getAbsoluteGravity(resolveGravity(paramLayoutParams.anchorGravity), paramInt1);
    int k = i & 0x7;
    int m = i & 0x70;
    paramInt1 = j & 0x7;
    j &= 0x70;
    if (paramInt1 != 1) {
      if (paramInt1 != 5) {
        paramInt1 = paramRect1.left;
      } else {
        paramInt1 = paramRect1.right;
      } 
    } else {
      paramInt1 = paramRect1.left + paramRect1.width() / 2;
    } 
    if (j != 16) {
      if (j != 80) {
        j = paramRect1.top;
      } else {
        j = paramRect1.bottom;
      } 
    } else {
      j = paramRect1.top + paramRect1.height() / 2;
    } 
    if (k != 1) {
      i = paramInt1;
      if (k != 5)
        i = paramInt1 - paramInt2; 
    } else {
      i = paramInt1 - paramInt2 / 2;
    } 
    if (m != 16) {
      paramInt1 = j;
      if (m != 80)
        paramInt1 = j - paramInt3; 
    } else {
      paramInt1 = j - paramInt3 / 2;
    } 
    paramRect2.set(i, paramInt1, paramInt2 + i, paramInt3 + paramInt1);
  }
  
  private int getKeyline(int paramInt) {
    StringBuilder stringBuilder;
    int[] arrayOfInt = this.mKeylines;
    if (arrayOfInt == null) {
      stringBuilder = new StringBuilder();
      stringBuilder.append("No keylines defined for ");
      stringBuilder.append(this);
      stringBuilder.append(" - attempted index lookup ");
      stringBuilder.append(paramInt);
      Log.e("CoordinatorLayout", stringBuilder.toString());
      return 0;
    } 
    if (paramInt < 0 || paramInt >= stringBuilder.length) {
      stringBuilder = new StringBuilder();
      stringBuilder.append("Keyline index ");
      stringBuilder.append(paramInt);
      stringBuilder.append(" out of range for ");
      stringBuilder.append(this);
      Log.e("CoordinatorLayout", stringBuilder.toString());
      return 0;
    } 
    return stringBuilder[paramInt];
  }
  
  private void getTopSortedChildren(List<View> paramList) {
    paramList.clear();
    boolean bool = isChildrenDrawingOrderEnabled();
    int i = getChildCount();
    for (int j = i - 1; j >= 0; j--) {
      int k;
      if (bool) {
        k = getChildDrawingOrder(i, j);
      } else {
        k = j;
      } 
      paramList.add(getChildAt(k));
    } 
    Comparator<View> comparator = TOP_SORTED_CHILDREN_COMPARATOR;
    if (comparator != null)
      Collections.sort(paramList, comparator); 
  }
  
  private boolean hasDependencies(View paramView) {
    return this.mChildDag.hasOutgoingEdges(paramView);
  }
  
  private void layoutChild(View paramView, int paramInt) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    Rect rect1 = acquireTempRect();
    rect1.set(getPaddingLeft() + layoutParams.leftMargin, getPaddingTop() + layoutParams.topMargin, getWidth() - getPaddingRight() - layoutParams.rightMargin, getHeight() - getPaddingBottom() - layoutParams.bottomMargin);
    if (this.mLastInsets != null && ViewCompat.getFitsSystemWindows((View)this) && !ViewCompat.getFitsSystemWindows(paramView)) {
      rect1.left += this.mLastInsets.getSystemWindowInsetLeft();
      rect1.top += this.mLastInsets.getSystemWindowInsetTop();
      rect1.right -= this.mLastInsets.getSystemWindowInsetRight();
      rect1.bottom -= this.mLastInsets.getSystemWindowInsetBottom();
    } 
    Rect rect2 = acquireTempRect();
    GravityCompat.apply(resolveGravity(layoutParams.gravity), paramView.getMeasuredWidth(), paramView.getMeasuredHeight(), rect1, rect2, paramInt);
    paramView.layout(rect2.left, rect2.top, rect2.right, rect2.bottom);
    releaseTempRect(rect1);
    releaseTempRect(rect2);
  }
  
  private void layoutChildWithAnchor(View paramView1, View paramView2, int paramInt) {
    LayoutParams layoutParams = (LayoutParams)paramView1.getLayoutParams();
    Rect rect1 = acquireTempRect();
    Rect rect2 = acquireTempRect();
    try {
      getDescendantRect(paramView2, rect1);
      getDesiredAnchoredChildRect(paramView1, paramInt, rect1, rect2);
      paramView1.layout(rect2.left, rect2.top, rect2.right, rect2.bottom);
      return;
    } finally {
      releaseTempRect(rect1);
      releaseTempRect(rect2);
    } 
  }
  
  private void layoutChildWithKeyline(View paramView, int paramInt1, int paramInt2) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    int i = GravityCompat.getAbsoluteGravity(resolveKeylineGravity(layoutParams.gravity), paramInt2);
    int j = i & 0x7;
    int k = i & 0x70;
    int m = getWidth();
    int n = getHeight();
    int i1 = paramView.getMeasuredWidth();
    int i2 = paramView.getMeasuredHeight();
    i = paramInt1;
    if (paramInt2 == 1)
      i = m - paramInt1; 
    paramInt1 = getKeyline(i) - i1;
    paramInt2 = 0;
    if (j != 1) {
      if (j == 5)
        paramInt1 += i1; 
    } else {
      paramInt1 += i1 / 2;
    } 
    if (k != 16) {
      if (k == 80)
        paramInt2 = i2 + 0; 
    } else {
      paramInt2 = 0 + i2 / 2;
    } 
    paramInt1 = Math.max(getPaddingLeft() + layoutParams.leftMargin, Math.min(paramInt1, m - getPaddingRight() - i1 - layoutParams.rightMargin));
    paramInt2 = Math.max(getPaddingTop() + layoutParams.topMargin, Math.min(paramInt2, n - getPaddingBottom() - i2 - layoutParams.bottomMargin));
    paramView.layout(paramInt1, paramInt2, i1 + paramInt1, i2 + paramInt2);
  }
  
  private void offsetChildByInset(View paramView, Rect paramRect, int paramInt) {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic isLaidOut : (Landroid/view/View;)Z
    //   4: ifne -> 8
    //   7: return
    //   8: aload_1
    //   9: invokevirtual getWidth : ()I
    //   12: ifle -> 453
    //   15: aload_1
    //   16: invokevirtual getHeight : ()I
    //   19: ifgt -> 25
    //   22: goto -> 453
    //   25: aload_1
    //   26: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   29: checkcast android/support/design/widget/CoordinatorLayout$LayoutParams
    //   32: astore #4
    //   34: aload #4
    //   36: invokevirtual getBehavior : ()Landroid/support/design/widget/CoordinatorLayout$Behavior;
    //   39: astore #5
    //   41: invokestatic acquireTempRect : ()Landroid/graphics/Rect;
    //   44: astore #6
    //   46: invokestatic acquireTempRect : ()Landroid/graphics/Rect;
    //   49: astore #7
    //   51: aload #7
    //   53: aload_1
    //   54: invokevirtual getLeft : ()I
    //   57: aload_1
    //   58: invokevirtual getTop : ()I
    //   61: aload_1
    //   62: invokevirtual getRight : ()I
    //   65: aload_1
    //   66: invokevirtual getBottom : ()I
    //   69: invokevirtual set : (IIII)V
    //   72: aload #5
    //   74: ifnull -> 158
    //   77: aload #5
    //   79: aload_0
    //   80: aload_1
    //   81: aload #6
    //   83: invokevirtual getInsetDodgeRect : (Landroid/support/design/widget/CoordinatorLayout;Landroid/view/View;Landroid/graphics/Rect;)Z
    //   86: ifeq -> 158
    //   89: aload #7
    //   91: aload #6
    //   93: invokevirtual contains : (Landroid/graphics/Rect;)Z
    //   96: ifeq -> 102
    //   99: goto -> 165
    //   102: new java/lang/StringBuilder
    //   105: dup
    //   106: invokespecial <init> : ()V
    //   109: astore_1
    //   110: aload_1
    //   111: ldc_w 'Rect should be within the child's bounds. Rect:'
    //   114: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   117: pop
    //   118: aload_1
    //   119: aload #6
    //   121: invokevirtual toShortString : ()Ljava/lang/String;
    //   124: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   127: pop
    //   128: aload_1
    //   129: ldc_w ' | Bounds:'
    //   132: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: pop
    //   136: aload_1
    //   137: aload #7
    //   139: invokevirtual toShortString : ()Ljava/lang/String;
    //   142: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: pop
    //   146: new java/lang/IllegalArgumentException
    //   149: dup
    //   150: aload_1
    //   151: invokevirtual toString : ()Ljava/lang/String;
    //   154: invokespecial <init> : (Ljava/lang/String;)V
    //   157: athrow
    //   158: aload #6
    //   160: aload #7
    //   162: invokevirtual set : (Landroid/graphics/Rect;)V
    //   165: aload #7
    //   167: invokestatic releaseTempRect : (Landroid/graphics/Rect;)V
    //   170: aload #6
    //   172: invokevirtual isEmpty : ()Z
    //   175: ifeq -> 184
    //   178: aload #6
    //   180: invokestatic releaseTempRect : (Landroid/graphics/Rect;)V
    //   183: return
    //   184: aload #4
    //   186: getfield dodgeInsetEdges : I
    //   189: iload_3
    //   190: invokestatic getAbsoluteGravity : (II)I
    //   193: istore #8
    //   195: iconst_1
    //   196: istore #9
    //   198: iload #8
    //   200: bipush #48
    //   202: iand
    //   203: bipush #48
    //   205: if_icmpne -> 250
    //   208: aload #6
    //   210: getfield top : I
    //   213: aload #4
    //   215: getfield topMargin : I
    //   218: isub
    //   219: aload #4
    //   221: getfield mInsetOffsetY : I
    //   224: isub
    //   225: istore_3
    //   226: iload_3
    //   227: aload_2
    //   228: getfield top : I
    //   231: if_icmpge -> 250
    //   234: aload_0
    //   235: aload_1
    //   236: aload_2
    //   237: getfield top : I
    //   240: iload_3
    //   241: isub
    //   242: invokespecial setInsetOffsetY : (Landroid/view/View;I)V
    //   245: iconst_1
    //   246: istore_3
    //   247: goto -> 252
    //   250: iconst_0
    //   251: istore_3
    //   252: iload_3
    //   253: istore #10
    //   255: iload #8
    //   257: bipush #80
    //   259: iand
    //   260: bipush #80
    //   262: if_icmpne -> 316
    //   265: aload_0
    //   266: invokevirtual getHeight : ()I
    //   269: aload #6
    //   271: getfield bottom : I
    //   274: isub
    //   275: aload #4
    //   277: getfield bottomMargin : I
    //   280: isub
    //   281: aload #4
    //   283: getfield mInsetOffsetY : I
    //   286: iadd
    //   287: istore #11
    //   289: iload_3
    //   290: istore #10
    //   292: iload #11
    //   294: aload_2
    //   295: getfield bottom : I
    //   298: if_icmpge -> 316
    //   301: aload_0
    //   302: aload_1
    //   303: iload #11
    //   305: aload_2
    //   306: getfield bottom : I
    //   309: isub
    //   310: invokespecial setInsetOffsetY : (Landroid/view/View;I)V
    //   313: iconst_1
    //   314: istore #10
    //   316: iload #10
    //   318: ifne -> 327
    //   321: aload_0
    //   322: aload_1
    //   323: iconst_0
    //   324: invokespecial setInsetOffsetY : (Landroid/view/View;I)V
    //   327: iload #8
    //   329: iconst_3
    //   330: iand
    //   331: iconst_3
    //   332: if_icmpne -> 377
    //   335: aload #6
    //   337: getfield left : I
    //   340: aload #4
    //   342: getfield leftMargin : I
    //   345: isub
    //   346: aload #4
    //   348: getfield mInsetOffsetX : I
    //   351: isub
    //   352: istore_3
    //   353: iload_3
    //   354: aload_2
    //   355: getfield left : I
    //   358: if_icmpge -> 377
    //   361: aload_0
    //   362: aload_1
    //   363: aload_2
    //   364: getfield left : I
    //   367: iload_3
    //   368: isub
    //   369: invokespecial setInsetOffsetX : (Landroid/view/View;I)V
    //   372: iconst_1
    //   373: istore_3
    //   374: goto -> 379
    //   377: iconst_0
    //   378: istore_3
    //   379: iload #8
    //   381: iconst_5
    //   382: iand
    //   383: iconst_5
    //   384: if_icmpne -> 438
    //   387: aload_0
    //   388: invokevirtual getWidth : ()I
    //   391: aload #6
    //   393: getfield right : I
    //   396: isub
    //   397: aload #4
    //   399: getfield rightMargin : I
    //   402: isub
    //   403: aload #4
    //   405: getfield mInsetOffsetX : I
    //   408: iadd
    //   409: istore #10
    //   411: iload #10
    //   413: aload_2
    //   414: getfield right : I
    //   417: if_icmpge -> 438
    //   420: aload_0
    //   421: aload_1
    //   422: iload #10
    //   424: aload_2
    //   425: getfield right : I
    //   428: isub
    //   429: invokespecial setInsetOffsetX : (Landroid/view/View;I)V
    //   432: iload #9
    //   434: istore_3
    //   435: goto -> 438
    //   438: iload_3
    //   439: ifne -> 448
    //   442: aload_0
    //   443: aload_1
    //   444: iconst_0
    //   445: invokespecial setInsetOffsetX : (Landroid/view/View;I)V
    //   448: aload #6
    //   450: invokestatic releaseTempRect : (Landroid/graphics/Rect;)V
    //   453: return
  }
  
  static Behavior parseBehavior(Context paramContext, AttributeSet paramAttributeSet, String paramString) {
    String str;
    if (TextUtils.isEmpty(paramString))
      return null; 
    if (paramString.startsWith(".")) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramContext.getPackageName());
      stringBuilder.append(paramString);
      str = stringBuilder.toString();
    } else if (paramString.indexOf('.') >= 0) {
      str = paramString;
    } else {
      str = paramString;
      if (!TextUtils.isEmpty(WIDGET_PACKAGE_NAME)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(WIDGET_PACKAGE_NAME);
        stringBuilder.append('.');
        stringBuilder.append(paramString);
        str = stringBuilder.toString();
      } 
    } 
    try {
      Map<Object, Object> map2 = (Map)sConstructors.get();
      Map<Object, Object> map1 = map2;
      if (map2 == null) {
        map1 = new HashMap<Object, Object>();
        super();
        sConstructors.set(map1);
      } 
      Constructor<?> constructor2 = (Constructor)map1.get(str);
      Constructor<?> constructor1 = constructor2;
      if (constructor2 == null) {
        constructor1 = paramContext.getClassLoader().loadClass(str).getConstructor(CONSTRUCTOR_PARAMS);
        constructor1.setAccessible(true);
        map1.put(str, constructor1);
      } 
      return (Behavior)constructor1.newInstance(new Object[] { paramContext, paramAttributeSet });
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Could not inflate Behavior subclass ");
      stringBuilder.append(str);
      throw new RuntimeException(stringBuilder.toString(), exception);
    } 
  }
  
  private boolean performIntercept(MotionEvent paramMotionEvent, int paramInt) {
    boolean bool2;
    int i = paramMotionEvent.getActionMasked();
    List<View> list = this.mTempList1;
    getTopSortedChildren(list);
    int j = list.size();
    LayoutParams layoutParams = null;
    byte b = 0;
    boolean bool1 = false;
    boolean bool = false;
    while (true) {
      bool2 = bool1;
      if (b < j) {
        MotionEvent motionEvent;
        LayoutParams layoutParams1;
        boolean bool3;
        boolean bool4;
        View view = list.get(b);
        LayoutParams layoutParams2 = (LayoutParams)view.getLayoutParams();
        Behavior<View> behavior = layoutParams2.getBehavior();
        if ((bool1 || bool) && i != 0) {
          layoutParams2 = layoutParams;
          bool3 = bool1;
          bool4 = bool;
          if (behavior != null) {
            layoutParams2 = layoutParams;
            if (layoutParams == null) {
              long l = SystemClock.uptimeMillis();
              motionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
            } 
            if (paramInt != 0) {
              if (paramInt != 1) {
                bool3 = bool1;
                bool4 = bool;
              } else {
                behavior.onTouchEvent(this, view, motionEvent);
                bool3 = bool1;
                bool4 = bool;
              } 
            } else {
              behavior.onInterceptTouchEvent(this, view, motionEvent);
              bool3 = bool1;
              bool4 = bool;
            } 
          } 
        } else {
          bool2 = bool1;
          if (!bool1) {
            bool2 = bool1;
            if (behavior != null) {
              if (paramInt != 0) {
                if (paramInt == 1)
                  bool1 = behavior.onTouchEvent(this, view, paramMotionEvent); 
              } else {
                bool1 = behavior.onInterceptTouchEvent(this, view, paramMotionEvent);
              } 
              bool2 = bool1;
              if (bool1) {
                this.mBehaviorTouchView = view;
                bool2 = bool1;
              } 
            } 
          } 
          bool3 = motionEvent.didBlockInteraction();
          bool1 = motionEvent.isBlockingInteractionBelow(this, view);
          if (bool1 && !bool3) {
            bool = true;
          } else {
            bool = false;
          } 
          layoutParams1 = layoutParams;
          bool3 = bool2;
          bool4 = bool;
          if (bool1) {
            layoutParams1 = layoutParams;
            bool3 = bool2;
            bool4 = bool;
            if (!bool)
              break; 
          } 
        } 
        b++;
        layoutParams = layoutParams1;
        bool1 = bool3;
        bool = bool4;
        continue;
      } 
      break;
    } 
    list.clear();
    return bool2;
  }
  
  private void prepareChildren() {
    this.mDependencySortedChildren.clear();
    this.mChildDag.clear();
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = getChildAt(b);
      LayoutParams layoutParams = getResolvedLayoutParams(view);
      layoutParams.findAnchorView(this, view);
      this.mChildDag.addNode(view);
      for (byte b1 = 0; b1 < i; b1++) {
        if (b1 != b) {
          View view1 = getChildAt(b1);
          if (layoutParams.dependsOn(this, view, view1)) {
            if (!this.mChildDag.contains(view1))
              this.mChildDag.addNode(view1); 
            this.mChildDag.addEdge(view1, view);
          } 
        } 
      } 
    } 
    this.mDependencySortedChildren.addAll(this.mChildDag.getSortedList());
    Collections.reverse(this.mDependencySortedChildren);
  }
  
  private static void releaseTempRect(Rect paramRect) {
    paramRect.setEmpty();
    sRectPool.release(paramRect);
  }
  
  private void resetTouchBehaviors(boolean paramBoolean) {
    int i = getChildCount();
    byte b;
    for (b = 0; b < i; b++) {
      View view = getChildAt(b);
      Behavior<View> behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
      if (behavior != null) {
        long l = SystemClock.uptimeMillis();
        MotionEvent motionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
        if (paramBoolean) {
          behavior.onInterceptTouchEvent(this, view, motionEvent);
        } else {
          behavior.onTouchEvent(this, view, motionEvent);
        } 
        motionEvent.recycle();
      } 
    } 
    for (b = 0; b < i; b++)
      ((LayoutParams)getChildAt(b).getLayoutParams()).resetTouchBehaviorTracking(); 
    this.mBehaviorTouchView = null;
    this.mDisallowInterceptReset = false;
  }
  
  private static int resolveAnchoredChildGravity(int paramInt) {
    int i = paramInt;
    if (paramInt == 0)
      i = 17; 
    return i;
  }
  
  private static int resolveGravity(int paramInt) {
    int i = paramInt;
    if ((paramInt & 0x7) == 0)
      i = paramInt | 0x800003; 
    paramInt = i;
    if ((i & 0x70) == 0)
      paramInt = i | 0x30; 
    return paramInt;
  }
  
  private static int resolveKeylineGravity(int paramInt) {
    int i = paramInt;
    if (paramInt == 0)
      i = 8388661; 
    return i;
  }
  
  private void setInsetOffsetX(View paramView, int paramInt) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if (layoutParams.mInsetOffsetX != paramInt) {
      ViewCompat.offsetLeftAndRight(paramView, paramInt - layoutParams.mInsetOffsetX);
      layoutParams.mInsetOffsetX = paramInt;
    } 
  }
  
  private void setInsetOffsetY(View paramView, int paramInt) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if (layoutParams.mInsetOffsetY != paramInt) {
      ViewCompat.offsetTopAndBottom(paramView, paramInt - layoutParams.mInsetOffsetY);
      layoutParams.mInsetOffsetY = paramInt;
    } 
  }
  
  private void setupForInsets() {
    if (Build.VERSION.SDK_INT < 21)
      return; 
    if (ViewCompat.getFitsSystemWindows((View)this)) {
      if (this.mApplyWindowInsetsListener == null)
        this.mApplyWindowInsetsListener = new OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View param1View, WindowInsetsCompat param1WindowInsetsCompat) {
              return CoordinatorLayout.this.setWindowInsets(param1WindowInsetsCompat);
            }
          }; 
      ViewCompat.setOnApplyWindowInsetsListener((View)this, this.mApplyWindowInsetsListener);
      setSystemUiVisibility(1280);
    } else {
      ViewCompat.setOnApplyWindowInsetsListener((View)this, null);
    } 
  }
  
  void addPreDrawListener() {
    if (this.mIsAttachedToWindow) {
      if (this.mOnPreDrawListener == null)
        this.mOnPreDrawListener = new OnPreDrawListener(); 
      getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
    } 
    this.mNeedsPreDrawListener = true;
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
  
  public void dispatchDependentViewsChanged(View paramView) {
    List<View> list = this.mChildDag.getIncomingEdges(paramView);
    if (list != null && !list.isEmpty())
      for (byte b = 0; b < list.size(); b++) {
        View view = list.get(b);
        Behavior<View> behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
        if (behavior != null)
          behavior.onDependentViewChanged(this, view, paramView); 
      }  
  }
  
  public boolean doViewsOverlap(View paramView1, View paramView2) {
    int i = paramView1.getVisibility();
    boolean bool = false;
    if (i == 0 && paramView2.getVisibility() == 0) {
      Rect rect2 = acquireTempRect();
      if (paramView1.getParent() != this) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      getChildRect(paramView1, bool1, rect2);
      Rect rect1 = acquireTempRect();
      if (paramView2.getParent() != this) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      getChildRect(paramView2, bool1, rect1);
      boolean bool1 = bool;
      try {
        if (rect2.left <= rect1.right) {
          bool1 = bool;
          if (rect2.top <= rect1.bottom) {
            bool1 = bool;
            if (rect2.right >= rect1.left) {
              int j = rect2.bottom;
              i = rect1.top;
              bool1 = bool;
              if (j >= i)
                bool1 = true; 
            } 
          } 
        } 
        return bool1;
      } finally {
        releaseTempRect(rect2);
        releaseTempRect(rect1);
      } 
    } 
    return false;
  }
  
  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if (layoutParams.mBehavior != null) {
      float f = layoutParams.mBehavior.getScrimOpacity(this, paramView);
      if (f > 0.0F) {
        if (this.mScrimPaint == null)
          this.mScrimPaint = new Paint(); 
        this.mScrimPaint.setColor(layoutParams.mBehavior.getScrimColor(this, paramView));
        this.mScrimPaint.setAlpha(MathUtils.clamp(Math.round(f * 255.0F), 0, 255));
        int i = paramCanvas.save();
        if (paramView.isOpaque())
          paramCanvas.clipRect(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom(), Region.Op.DIFFERENCE); 
        paramCanvas.drawRect(getPaddingLeft(), getPaddingTop(), (getWidth() - getPaddingRight()), (getHeight() - getPaddingBottom()), this.mScrimPaint);
        paramCanvas.restoreToCount(i);
      } 
    } 
    return super.drawChild(paramCanvas, paramView, paramLong);
  }
  
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    int[] arrayOfInt = getDrawableState();
    Drawable drawable = this.mStatusBarBackground;
    byte b = 0;
    int i = b;
    if (drawable != null) {
      i = b;
      if (drawable.isStateful())
        i = false | drawable.setState(arrayOfInt); 
    } 
    if (i != 0)
      invalidate(); 
  }
  
  void ensurePreDrawListener() {
    boolean bool2;
    int i = getChildCount();
    boolean bool1 = false;
    byte b = 0;
    while (true) {
      bool2 = bool1;
      if (b < i) {
        if (hasDependencies(getChildAt(b))) {
          bool2 = true;
          break;
        } 
        b++;
        continue;
      } 
      break;
    } 
    if (bool2 != this.mNeedsPreDrawListener)
      if (bool2) {
        addPreDrawListener();
      } else {
        removePreDrawListener();
      }  
  }
  
  protected LayoutParams generateDefaultLayoutParams() {
    return new LayoutParams(-2, -2);
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return (paramLayoutParams instanceof LayoutParams) ? new LayoutParams((LayoutParams)paramLayoutParams) : ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams) ? new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams) : new LayoutParams(paramLayoutParams));
  }
  
  void getChildRect(View paramView, boolean paramBoolean, Rect paramRect) {
    if (paramView.isLayoutRequested() || paramView.getVisibility() == 8) {
      paramRect.setEmpty();
      return;
    } 
    if (paramBoolean) {
      getDescendantRect(paramView, paramRect);
    } else {
      paramRect.set(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom());
    } 
  }
  
  public List<View> getDependencies(View paramView) {
    List<? extends View> list = this.mChildDag.getOutgoingEdges(paramView);
    this.mTempDependenciesList.clear();
    if (list != null)
      this.mTempDependenciesList.addAll(list); 
    return this.mTempDependenciesList;
  }
  
  final List<View> getDependencySortedChildren() {
    prepareChildren();
    return Collections.unmodifiableList(this.mDependencySortedChildren);
  }
  
  public List<View> getDependents(View paramView) {
    List<? extends View> list = this.mChildDag.getIncomingEdges(paramView);
    this.mTempDependenciesList.clear();
    if (list != null)
      this.mTempDependenciesList.addAll(list); 
    return this.mTempDependenciesList;
  }
  
  void getDescendantRect(View paramView, Rect paramRect) {
    ViewGroupUtils.getDescendantRect(this, paramView, paramRect);
  }
  
  void getDesiredAnchoredChildRect(View paramView, int paramInt, Rect paramRect1, Rect paramRect2) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    int i = paramView.getMeasuredWidth();
    int j = paramView.getMeasuredHeight();
    getDesiredAnchoredChildRectWithoutConstraints(paramView, paramInt, paramRect1, paramRect2, layoutParams, i, j);
    constrainChildRect(layoutParams, paramRect2, i, j);
  }
  
  void getLastChildRect(View paramView, Rect paramRect) {
    paramRect.set(((LayoutParams)paramView.getLayoutParams()).getLastChildRect());
  }
  
  public final WindowInsetsCompat getLastWindowInsets() {
    return this.mLastInsets;
  }
  
  public int getNestedScrollAxes() {
    return this.mNestedScrollingParentHelper.getNestedScrollAxes();
  }
  
  LayoutParams getResolvedLayoutParams(View paramView) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if (!layoutParams.mBehaviorResolved) {
      Behavior behavior;
      if (paramView instanceof AttachedBehavior) {
        behavior = ((AttachedBehavior)paramView).getBehavior();
        if (behavior == null)
          Log.e("CoordinatorLayout", "Attached behavior class is null"); 
        layoutParams.setBehavior(behavior);
        layoutParams.mBehaviorResolved = true;
      } else {
        DefaultBehavior defaultBehavior;
        Class<?> clazz = behavior.getClass();
        behavior = null;
        while (clazz != null) {
          DefaultBehavior defaultBehavior1 = clazz.<DefaultBehavior>getAnnotation(DefaultBehavior.class);
          defaultBehavior = defaultBehavior1;
          if (defaultBehavior1 == null) {
            clazz = clazz.getSuperclass();
            defaultBehavior = defaultBehavior1;
          } 
        } 
        if (defaultBehavior != null)
          try {
            layoutParams.setBehavior(defaultBehavior.value().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
          } catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Default behavior class ");
            stringBuilder.append(defaultBehavior.value().getName());
            stringBuilder.append(" could not be instantiated. Did you forget");
            stringBuilder.append(" a default constructor?");
            Log.e("CoordinatorLayout", stringBuilder.toString(), exception);
          }  
        layoutParams.mBehaviorResolved = true;
      } 
    } 
    return layoutParams;
  }
  
  public Drawable getStatusBarBackground() {
    return this.mStatusBarBackground;
  }
  
  protected int getSuggestedMinimumHeight() {
    return Math.max(super.getSuggestedMinimumHeight(), getPaddingTop() + getPaddingBottom());
  }
  
  protected int getSuggestedMinimumWidth() {
    return Math.max(super.getSuggestedMinimumWidth(), getPaddingLeft() + getPaddingRight());
  }
  
  public boolean isPointInChildBounds(View paramView, int paramInt1, int paramInt2) {
    Rect rect = acquireTempRect();
    getDescendantRect(paramView, rect);
    try {
      return rect.contains(paramInt1, paramInt2);
    } finally {
      releaseTempRect(rect);
    } 
  }
  
  void offsetChildToAnchor(View paramView, int paramInt) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   4: checkcast android/support/design/widget/CoordinatorLayout$LayoutParams
    //   7: astore_3
    //   8: aload_3
    //   9: getfield mAnchorView : Landroid/view/View;
    //   12: ifnull -> 210
    //   15: invokestatic acquireTempRect : ()Landroid/graphics/Rect;
    //   18: astore #4
    //   20: invokestatic acquireTempRect : ()Landroid/graphics/Rect;
    //   23: astore #5
    //   25: invokestatic acquireTempRect : ()Landroid/graphics/Rect;
    //   28: astore #6
    //   30: aload_0
    //   31: aload_3
    //   32: getfield mAnchorView : Landroid/view/View;
    //   35: aload #4
    //   37: invokevirtual getDescendantRect : (Landroid/view/View;Landroid/graphics/Rect;)V
    //   40: iconst_0
    //   41: istore #7
    //   43: aload_0
    //   44: aload_1
    //   45: iconst_0
    //   46: aload #5
    //   48: invokevirtual getChildRect : (Landroid/view/View;ZLandroid/graphics/Rect;)V
    //   51: aload_1
    //   52: invokevirtual getMeasuredWidth : ()I
    //   55: istore #8
    //   57: aload_1
    //   58: invokevirtual getMeasuredHeight : ()I
    //   61: istore #9
    //   63: aload_0
    //   64: aload_1
    //   65: iload_2
    //   66: aload #4
    //   68: aload #6
    //   70: aload_3
    //   71: iload #8
    //   73: iload #9
    //   75: invokespecial getDesiredAnchoredChildRectWithoutConstraints : (Landroid/view/View;ILandroid/graphics/Rect;Landroid/graphics/Rect;Landroid/support/design/widget/CoordinatorLayout$LayoutParams;II)V
    //   78: aload #6
    //   80: getfield left : I
    //   83: aload #5
    //   85: getfield left : I
    //   88: if_icmpne -> 107
    //   91: iload #7
    //   93: istore_2
    //   94: aload #6
    //   96: getfield top : I
    //   99: aload #5
    //   101: getfield top : I
    //   104: if_icmpeq -> 109
    //   107: iconst_1
    //   108: istore_2
    //   109: aload_0
    //   110: aload_3
    //   111: aload #6
    //   113: iload #8
    //   115: iload #9
    //   117: invokespecial constrainChildRect : (Landroid/support/design/widget/CoordinatorLayout$LayoutParams;Landroid/graphics/Rect;II)V
    //   120: aload #6
    //   122: getfield left : I
    //   125: aload #5
    //   127: getfield left : I
    //   130: isub
    //   131: istore #7
    //   133: aload #6
    //   135: getfield top : I
    //   138: aload #5
    //   140: getfield top : I
    //   143: isub
    //   144: istore #8
    //   146: iload #7
    //   148: ifeq -> 157
    //   151: aload_1
    //   152: iload #7
    //   154: invokestatic offsetLeftAndRight : (Landroid/view/View;I)V
    //   157: iload #8
    //   159: ifeq -> 168
    //   162: aload_1
    //   163: iload #8
    //   165: invokestatic offsetTopAndBottom : (Landroid/view/View;I)V
    //   168: iload_2
    //   169: ifeq -> 195
    //   172: aload_3
    //   173: invokevirtual getBehavior : ()Landroid/support/design/widget/CoordinatorLayout$Behavior;
    //   176: astore #10
    //   178: aload #10
    //   180: ifnull -> 195
    //   183: aload #10
    //   185: aload_0
    //   186: aload_1
    //   187: aload_3
    //   188: getfield mAnchorView : Landroid/view/View;
    //   191: invokevirtual onDependentViewChanged : (Landroid/support/design/widget/CoordinatorLayout;Landroid/view/View;Landroid/view/View;)Z
    //   194: pop
    //   195: aload #4
    //   197: invokestatic releaseTempRect : (Landroid/graphics/Rect;)V
    //   200: aload #5
    //   202: invokestatic releaseTempRect : (Landroid/graphics/Rect;)V
    //   205: aload #6
    //   207: invokestatic releaseTempRect : (Landroid/graphics/Rect;)V
    //   210: return
  }
  
  public void onAttachedToWindow() {
    super.onAttachedToWindow();
    resetTouchBehaviors(false);
    if (this.mNeedsPreDrawListener) {
      if (this.mOnPreDrawListener == null)
        this.mOnPreDrawListener = new OnPreDrawListener(); 
      getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
    } 
    if (this.mLastInsets == null && ViewCompat.getFitsSystemWindows((View)this))
      ViewCompat.requestApplyInsets((View)this); 
    this.mIsAttachedToWindow = true;
  }
  
  final void onChildViewsChanged(int paramInt) {
    int i = ViewCompat.getLayoutDirection((View)this);
    int j = this.mDependencySortedChildren.size();
    Rect rect1 = acquireTempRect();
    Rect rect2 = acquireTempRect();
    Rect rect3 = acquireTempRect();
    for (byte b = 0; b < j; b++) {
      View view = this.mDependencySortedChildren.get(b);
      LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
      if (paramInt == 0 && view.getVisibility() == 8)
        continue; 
      int k;
      for (k = 0; k < b; k++) {
        View view1 = this.mDependencySortedChildren.get(k);
        if (layoutParams.mAnchorDirectChild == view1)
          offsetChildToAnchor(view, i); 
      } 
      getChildRect(view, true, rect2);
      if (layoutParams.insetEdge != 0 && !rect2.isEmpty()) {
        k = GravityCompat.getAbsoluteGravity(layoutParams.insetEdge, i);
        int m = k & 0x70;
        if (m != 48) {
          if (m == 80)
            rect1.bottom = Math.max(rect1.bottom, getHeight() - rect2.top); 
        } else {
          rect1.top = Math.max(rect1.top, rect2.bottom);
        } 
        k &= 0x7;
        if (k != 3) {
          if (k == 5)
            rect1.right = Math.max(rect1.right, getWidth() - rect2.left); 
        } else {
          rect1.left = Math.max(rect1.left, rect2.right);
        } 
      } 
      if (layoutParams.dodgeInsetEdges != 0 && view.getVisibility() == 0)
        offsetChildByInset(view, rect1, i); 
      if (paramInt != 2) {
        getLastChildRect(view, rect3);
        if (rect3.equals(rect2))
          continue; 
        recordLastChildRect(view, rect2);
      } 
      for (k = b + 1; k < j; k++) {
        View view1 = this.mDependencySortedChildren.get(k);
        layoutParams = (LayoutParams)view1.getLayoutParams();
        Behavior<View> behavior = layoutParams.getBehavior();
        if (behavior != null && behavior.layoutDependsOn(this, view1, view))
          if (paramInt == 0 && layoutParams.getChangedAfterNestedScroll()) {
            layoutParams.resetChangedAfterNestedScroll();
          } else {
            boolean bool;
            if (paramInt != 2) {
              bool = behavior.onDependentViewChanged(this, view1, view);
            } else {
              behavior.onDependentViewRemoved(this, view1, view);
              bool = true;
            } 
            if (paramInt == 1)
              layoutParams.setChangedAfterNestedScroll(bool); 
          }  
      } 
      continue;
    } 
    releaseTempRect(rect1);
    releaseTempRect(rect2);
    releaseTempRect(rect3);
  }
  
  public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    resetTouchBehaviors(false);
    if (this.mNeedsPreDrawListener && this.mOnPreDrawListener != null)
      getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener); 
    View view = this.mNestedScrollingTarget;
    if (view != null)
      onStopNestedScroll(view); 
    this.mIsAttachedToWindow = false;
  }
  
  public void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    if (this.mDrawStatusBarBackground && this.mStatusBarBackground != null) {
      boolean bool;
      WindowInsetsCompat windowInsetsCompat = this.mLastInsets;
      if (windowInsetsCompat != null) {
        bool = windowInsetsCompat.getSystemWindowInsetTop();
      } else {
        bool = false;
      } 
      if (bool) {
        this.mStatusBarBackground.setBounds(0, 0, getWidth(), bool);
        this.mStatusBarBackground.draw(paramCanvas);
      } 
    } 
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getActionMasked();
    if (i == 0)
      resetTouchBehaviors(true); 
    boolean bool = performIntercept(paramMotionEvent, 0);
    if (i == 1 || i == 3)
      resetTouchBehaviors(true); 
    return bool;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    paramInt2 = ViewCompat.getLayoutDirection((View)this);
    paramInt3 = this.mDependencySortedChildren.size();
    for (paramInt1 = 0; paramInt1 < paramInt3; paramInt1++) {
      View view = this.mDependencySortedChildren.get(paramInt1);
      if (view.getVisibility() != 8) {
        Behavior<View> behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
        if (behavior == null || !behavior.onLayoutChild(this, view, paramInt2))
          onLayoutChild(view, paramInt2); 
      } 
    } 
  }
  
  public void onLayoutChild(View paramView, int paramInt) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if (!layoutParams.checkAnchorChanged()) {
      if (layoutParams.mAnchorView != null) {
        layoutChildWithAnchor(paramView, layoutParams.mAnchorView, paramInt);
      } else if (layoutParams.keyline >= 0) {
        layoutChildWithKeyline(paramView, layoutParams.keyline, paramInt);
      } else {
        layoutChild(paramView, paramInt);
      } 
      return;
    } 
    throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial prepareChildren : ()V
    //   4: aload_0
    //   5: invokevirtual ensurePreDrawListener : ()V
    //   8: aload_0
    //   9: invokevirtual getPaddingLeft : ()I
    //   12: istore_3
    //   13: aload_0
    //   14: invokevirtual getPaddingTop : ()I
    //   17: istore #4
    //   19: aload_0
    //   20: invokevirtual getPaddingRight : ()I
    //   23: istore #5
    //   25: aload_0
    //   26: invokevirtual getPaddingBottom : ()I
    //   29: istore #6
    //   31: aload_0
    //   32: invokestatic getLayoutDirection : (Landroid/view/View;)I
    //   35: istore #7
    //   37: iload #7
    //   39: iconst_1
    //   40: if_icmpne -> 49
    //   43: iconst_1
    //   44: istore #8
    //   46: goto -> 52
    //   49: iconst_0
    //   50: istore #8
    //   52: iload_1
    //   53: invokestatic getMode : (I)I
    //   56: istore #9
    //   58: iload_1
    //   59: invokestatic getSize : (I)I
    //   62: istore #10
    //   64: iload_2
    //   65: invokestatic getMode : (I)I
    //   68: istore #11
    //   70: iload_2
    //   71: invokestatic getSize : (I)I
    //   74: istore #12
    //   76: aload_0
    //   77: invokevirtual getSuggestedMinimumWidth : ()I
    //   80: istore #13
    //   82: aload_0
    //   83: invokevirtual getSuggestedMinimumHeight : ()I
    //   86: istore #14
    //   88: aload_0
    //   89: getfield mLastInsets : Landroid/support/v4/view/WindowInsetsCompat;
    //   92: ifnull -> 108
    //   95: aload_0
    //   96: invokestatic getFitsSystemWindows : (Landroid/view/View;)Z
    //   99: ifeq -> 108
    //   102: iconst_1
    //   103: istore #15
    //   105: goto -> 111
    //   108: iconst_0
    //   109: istore #15
    //   111: aload_0
    //   112: getfield mDependencySortedChildren : Ljava/util/List;
    //   115: invokeinterface size : ()I
    //   120: istore #16
    //   122: iconst_0
    //   123: istore #17
    //   125: iconst_0
    //   126: istore #18
    //   128: iload_3
    //   129: istore #19
    //   131: iload #19
    //   133: istore #20
    //   135: iload #18
    //   137: iload #16
    //   139: if_icmpge -> 520
    //   142: aload_0
    //   143: getfield mDependencySortedChildren : Ljava/util/List;
    //   146: iload #18
    //   148: invokeinterface get : (I)Ljava/lang/Object;
    //   153: checkcast android/view/View
    //   156: astore #21
    //   158: aload #21
    //   160: invokevirtual getVisibility : ()I
    //   163: bipush #8
    //   165: if_icmpne -> 171
    //   168: goto -> 510
    //   171: aload #21
    //   173: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   176: checkcast android/support/design/widget/CoordinatorLayout$LayoutParams
    //   179: astore #22
    //   181: aload #22
    //   183: getfield keyline : I
    //   186: iflt -> 298
    //   189: iload #9
    //   191: ifeq -> 298
    //   194: aload_0
    //   195: aload #22
    //   197: getfield keyline : I
    //   200: invokespecial getKeyline : (I)I
    //   203: istore #19
    //   205: aload #22
    //   207: getfield gravity : I
    //   210: invokestatic resolveKeylineGravity : (I)I
    //   213: iload #7
    //   215: invokestatic getAbsoluteGravity : (II)I
    //   218: bipush #7
    //   220: iand
    //   221: istore #23
    //   223: iload #23
    //   225: iconst_3
    //   226: if_icmpne -> 234
    //   229: iload #8
    //   231: ifeq -> 245
    //   234: iload #23
    //   236: iconst_5
    //   237: if_icmpne -> 262
    //   240: iload #8
    //   242: ifeq -> 262
    //   245: iconst_0
    //   246: iload #10
    //   248: iload #5
    //   250: isub
    //   251: iload #19
    //   253: isub
    //   254: invokestatic max : (II)I
    //   257: istore #19
    //   259: goto -> 301
    //   262: iload #23
    //   264: iconst_5
    //   265: if_icmpne -> 273
    //   268: iload #8
    //   270: ifeq -> 284
    //   273: iload #23
    //   275: iconst_3
    //   276: if_icmpne -> 298
    //   279: iload #8
    //   281: ifeq -> 298
    //   284: iconst_0
    //   285: iload #19
    //   287: iload #20
    //   289: isub
    //   290: invokestatic max : (II)I
    //   293: istore #19
    //   295: goto -> 301
    //   298: iconst_0
    //   299: istore #19
    //   301: iload #17
    //   303: istore #24
    //   305: iload #15
    //   307: ifeq -> 387
    //   310: aload #21
    //   312: invokestatic getFitsSystemWindows : (Landroid/view/View;)Z
    //   315: ifne -> 387
    //   318: aload_0
    //   319: getfield mLastInsets : Landroid/support/v4/view/WindowInsetsCompat;
    //   322: invokevirtual getSystemWindowInsetLeft : ()I
    //   325: istore #25
    //   327: aload_0
    //   328: getfield mLastInsets : Landroid/support/v4/view/WindowInsetsCompat;
    //   331: invokevirtual getSystemWindowInsetRight : ()I
    //   334: istore #17
    //   336: aload_0
    //   337: getfield mLastInsets : Landroid/support/v4/view/WindowInsetsCompat;
    //   340: invokevirtual getSystemWindowInsetTop : ()I
    //   343: istore #26
    //   345: aload_0
    //   346: getfield mLastInsets : Landroid/support/v4/view/WindowInsetsCompat;
    //   349: invokevirtual getSystemWindowInsetBottom : ()I
    //   352: istore #23
    //   354: iload #10
    //   356: iload #25
    //   358: iload #17
    //   360: iadd
    //   361: isub
    //   362: iload #9
    //   364: invokestatic makeMeasureSpec : (II)I
    //   367: istore #17
    //   369: iload #12
    //   371: iload #26
    //   373: iload #23
    //   375: iadd
    //   376: isub
    //   377: iload #11
    //   379: invokestatic makeMeasureSpec : (II)I
    //   382: istore #23
    //   384: goto -> 393
    //   387: iload_1
    //   388: istore #17
    //   390: iload_2
    //   391: istore #23
    //   393: aload #22
    //   395: invokevirtual getBehavior : ()Landroid/support/design/widget/CoordinatorLayout$Behavior;
    //   398: astore #27
    //   400: aload #27
    //   402: ifnull -> 426
    //   405: aload #27
    //   407: aload_0
    //   408: aload #21
    //   410: iload #17
    //   412: iload #19
    //   414: iload #23
    //   416: iconst_0
    //   417: invokevirtual onMeasureChild : (Landroid/support/design/widget/CoordinatorLayout;Landroid/view/View;IIII)Z
    //   420: ifne -> 439
    //   423: goto -> 426
    //   426: aload_0
    //   427: aload #21
    //   429: iload #17
    //   431: iload #19
    //   433: iload #23
    //   435: iconst_0
    //   436: invokevirtual onMeasureChild : (Landroid/view/View;IIII)V
    //   439: iload #13
    //   441: iload_3
    //   442: iload #5
    //   444: iadd
    //   445: aload #21
    //   447: invokevirtual getMeasuredWidth : ()I
    //   450: iadd
    //   451: aload #22
    //   453: getfield leftMargin : I
    //   456: iadd
    //   457: aload #22
    //   459: getfield rightMargin : I
    //   462: iadd
    //   463: invokestatic max : (II)I
    //   466: istore #13
    //   468: iload #14
    //   470: iload #4
    //   472: iload #6
    //   474: iadd
    //   475: aload #21
    //   477: invokevirtual getMeasuredHeight : ()I
    //   480: iadd
    //   481: aload #22
    //   483: getfield topMargin : I
    //   486: iadd
    //   487: aload #22
    //   489: getfield bottomMargin : I
    //   492: iadd
    //   493: invokestatic max : (II)I
    //   496: istore #14
    //   498: iload #24
    //   500: aload #21
    //   502: invokevirtual getMeasuredState : ()I
    //   505: invokestatic combineMeasuredStates : (II)I
    //   508: istore #17
    //   510: iinc #18, 1
    //   513: iload #20
    //   515: istore #19
    //   517: goto -> 131
    //   520: aload_0
    //   521: iload #13
    //   523: iload_1
    //   524: ldc_w -16777216
    //   527: iload #17
    //   529: iand
    //   530: invokestatic resolveSizeAndState : (III)I
    //   533: iload #14
    //   535: iload_2
    //   536: iload #17
    //   538: bipush #16
    //   540: ishl
    //   541: invokestatic resolveSizeAndState : (III)I
    //   544: invokevirtual setMeasuredDimension : (II)V
    //   547: return
  }
  
  public void onMeasureChild(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    measureChildWithMargins(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public boolean onNestedFling(View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean) {
    int i = getChildCount();
    byte b = 0;
    boolean bool;
    for (bool = false; b < i; bool = bool1) {
      boolean bool1;
      View view = getChildAt(b);
      if (view.getVisibility() == 8) {
        bool1 = bool;
      } else {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (!layoutParams.isNestedScrollAccepted(0)) {
          bool1 = bool;
        } else {
          Behavior<View> behavior = layoutParams.getBehavior();
          bool1 = bool;
          if (behavior != null)
            bool1 = bool | behavior.onNestedFling(this, view, paramView, paramFloat1, paramFloat2, paramBoolean); 
        } 
      } 
      b++;
    } 
    if (bool)
      onChildViewsChanged(1); 
    return bool;
  }
  
  public boolean onNestedPreFling(View paramView, float paramFloat1, float paramFloat2) {
    int i = getChildCount();
    byte b = 0;
    boolean bool;
    for (bool = false; b < i; bool = bool1) {
      boolean bool1;
      View view = getChildAt(b);
      if (view.getVisibility() == 8) {
        bool1 = bool;
      } else {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (!layoutParams.isNestedScrollAccepted(0)) {
          bool1 = bool;
        } else {
          Behavior<View> behavior = layoutParams.getBehavior();
          bool1 = bool;
          if (behavior != null)
            bool1 = bool | behavior.onNestedPreFling(this, view, paramView, paramFloat1, paramFloat2); 
        } 
      } 
      b++;
    } 
    return bool;
  }
  
  public void onNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfint) {
    onNestedPreScroll(paramView, paramInt1, paramInt2, paramArrayOfint, 0);
  }
  
  public void onNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
    int i = getChildCount();
    boolean bool = false;
    byte b = 0;
    int j = 0;
    int k;
    for (k = 0; b < i; k = n) {
      int m;
      int n;
      View view = getChildAt(b);
      if (view.getVisibility() == 8) {
        m = j;
        n = k;
      } else {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (!layoutParams.isNestedScrollAccepted(paramInt3)) {
          m = j;
          n = k;
        } else {
          Behavior<View> behavior = layoutParams.getBehavior();
          m = j;
          n = k;
          if (behavior != null) {
            int[] arrayOfInt2 = this.mTempIntPair;
            arrayOfInt2[1] = 0;
            arrayOfInt2[0] = 0;
            behavior.onNestedPreScroll(this, view, paramView, paramInt1, paramInt2, arrayOfInt2, paramInt3);
            int[] arrayOfInt1 = this.mTempIntPair;
            if (paramInt1 > 0) {
              n = Math.max(j, arrayOfInt1[0]);
            } else {
              n = Math.min(j, arrayOfInt1[0]);
            } 
            j = n;
            arrayOfInt1 = this.mTempIntPair;
            if (paramInt2 > 0) {
              n = Math.max(k, arrayOfInt1[1]);
            } else {
              n = Math.min(k, arrayOfInt1[1]);
            } 
            bool = true;
            m = j;
          } 
        } 
      } 
      b++;
      j = m;
    } 
    paramArrayOfint[0] = j;
    paramArrayOfint[1] = k;
    if (bool)
      onChildViewsChanged(1); 
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    onNestedScroll(paramView, paramInt1, paramInt2, paramInt3, paramInt4, 0);
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    int i = getChildCount();
    boolean bool = false;
    for (byte b = 0; b < i; b++) {
      View view = getChildAt(b);
      if (view.getVisibility() != 8) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.isNestedScrollAccepted(paramInt5)) {
          Behavior<View> behavior = layoutParams.getBehavior();
          if (behavior != null) {
            behavior.onNestedScroll(this, view, paramView, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
            bool = true;
          } 
        } 
      } 
    } 
    if (bool)
      onChildViewsChanged(1); 
  }
  
  public void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt) {
    onNestedScrollAccepted(paramView1, paramView2, paramInt, 0);
  }
  
  public void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt1, int paramInt2) {
    this.mNestedScrollingParentHelper.onNestedScrollAccepted(paramView1, paramView2, paramInt1, paramInt2);
    this.mNestedScrollingTarget = paramView2;
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = getChildAt(b);
      LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
      if (layoutParams.isNestedScrollAccepted(paramInt2)) {
        Behavior<View> behavior = layoutParams.getBehavior();
        if (behavior != null)
          behavior.onNestedScrollAccepted(this, view, paramView1, paramView2, paramInt1, paramInt2); 
      } 
    } 
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable) {
    if (!(paramParcelable instanceof SavedState)) {
      super.onRestoreInstanceState(paramParcelable);
      return;
    } 
    SavedState savedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(savedState.getSuperState());
    SparseArray<Parcelable> sparseArray = savedState.behaviorStates;
    byte b = 0;
    int i = getChildCount();
    while (b < i) {
      View view = getChildAt(b);
      int j = view.getId();
      Behavior<View> behavior = getResolvedLayoutParams(view).getBehavior();
      if (j != -1 && behavior != null) {
        Parcelable parcelable = (Parcelable)sparseArray.get(j);
        if (parcelable != null)
          behavior.onRestoreInstanceState(this, view, parcelable); 
      } 
      b++;
    } 
  }
  
  protected Parcelable onSaveInstanceState() {
    SavedState savedState = new SavedState(super.onSaveInstanceState());
    SparseArray<Parcelable> sparseArray = new SparseArray();
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = getChildAt(b);
      int j = view.getId();
      Behavior<View> behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
      if (j != -1 && behavior != null) {
        Parcelable parcelable = behavior.onSaveInstanceState(this, view);
        if (parcelable != null)
          sparseArray.append(j, parcelable); 
      } 
    } 
    savedState.behaviorStates = sparseArray;
    return (Parcelable)savedState;
  }
  
  public boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt) {
    return onStartNestedScroll(paramView1, paramView2, paramInt, 0);
  }
  
  public boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt1, int paramInt2) {
    int i = getChildCount();
    byte b = 0;
    boolean bool = false;
    while (b < i) {
      View view = getChildAt(b);
      if (view.getVisibility() != 8) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        Behavior<View> behavior = layoutParams.getBehavior();
        if (behavior != null) {
          boolean bool1 = behavior.onStartNestedScroll(this, view, paramView1, paramView2, paramInt1, paramInt2);
          bool |= bool1;
          layoutParams.setNestedScrollAccepted(paramInt2, bool1);
        } else {
          layoutParams.setNestedScrollAccepted(paramInt2, false);
        } 
      } 
      b++;
    } 
    return bool;
  }
  
  public void onStopNestedScroll(View paramView) {
    onStopNestedScroll(paramView, 0);
  }
  
  public void onStopNestedScroll(View paramView, int paramInt) {
    this.mNestedScrollingParentHelper.onStopNestedScroll(paramView, paramInt);
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = getChildAt(b);
      LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
      if (layoutParams.isNestedScrollAccepted(paramInt)) {
        Behavior<View> behavior = layoutParams.getBehavior();
        if (behavior != null)
          behavior.onStopNestedScroll(this, view, paramView, paramInt); 
        layoutParams.resetNestedScroll(paramInt);
        layoutParams.resetChangedAfterNestedScroll();
      } 
    } 
    this.mNestedScrollingTarget = null;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getActionMasked : ()I
    //   4: istore_2
    //   5: aload_0
    //   6: getfield mBehaviorTouchView : Landroid/view/View;
    //   9: ifnonnull -> 29
    //   12: aload_0
    //   13: aload_1
    //   14: iconst_1
    //   15: invokespecial performIntercept : (Landroid/view/MotionEvent;I)Z
    //   18: istore_3
    //   19: iload_3
    //   20: istore #4
    //   22: iload_3
    //   23: ifeq -> 76
    //   26: goto -> 31
    //   29: iconst_0
    //   30: istore_3
    //   31: aload_0
    //   32: getfield mBehaviorTouchView : Landroid/view/View;
    //   35: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   38: checkcast android/support/design/widget/CoordinatorLayout$LayoutParams
    //   41: invokevirtual getBehavior : ()Landroid/support/design/widget/CoordinatorLayout$Behavior;
    //   44: astore #5
    //   46: iload_3
    //   47: istore #4
    //   49: aload #5
    //   51: ifnull -> 76
    //   54: aload #5
    //   56: aload_0
    //   57: aload_0
    //   58: getfield mBehaviorTouchView : Landroid/view/View;
    //   61: aload_1
    //   62: invokevirtual onTouchEvent : (Landroid/support/design/widget/CoordinatorLayout;Landroid/view/View;Landroid/view/MotionEvent;)Z
    //   65: istore #6
    //   67: iload_3
    //   68: istore #4
    //   70: iload #6
    //   72: istore_3
    //   73: goto -> 78
    //   76: iconst_0
    //   77: istore_3
    //   78: aload_0
    //   79: getfield mBehaviorTouchView : Landroid/view/View;
    //   82: astore #7
    //   84: aconst_null
    //   85: astore #5
    //   87: aload #7
    //   89: ifnonnull -> 107
    //   92: iload_3
    //   93: aload_0
    //   94: aload_1
    //   95: invokespecial onTouchEvent : (Landroid/view/MotionEvent;)Z
    //   98: ior
    //   99: istore #6
    //   101: aload #5
    //   103: astore_1
    //   104: goto -> 144
    //   107: iload_3
    //   108: istore #6
    //   110: aload #5
    //   112: astore_1
    //   113: iload #4
    //   115: ifeq -> 144
    //   118: invokestatic uptimeMillis : ()J
    //   121: lstore #8
    //   123: lload #8
    //   125: lload #8
    //   127: iconst_3
    //   128: fconst_0
    //   129: fconst_0
    //   130: iconst_0
    //   131: invokestatic obtain : (JJIFFI)Landroid/view/MotionEvent;
    //   134: astore_1
    //   135: aload_0
    //   136: aload_1
    //   137: invokespecial onTouchEvent : (Landroid/view/MotionEvent;)Z
    //   140: pop
    //   141: iload_3
    //   142: istore #6
    //   144: aload_1
    //   145: ifnull -> 152
    //   148: aload_1
    //   149: invokevirtual recycle : ()V
    //   152: iload_2
    //   153: iconst_1
    //   154: if_icmpeq -> 162
    //   157: iload_2
    //   158: iconst_3
    //   159: if_icmpne -> 167
    //   162: aload_0
    //   163: iconst_0
    //   164: invokespecial resetTouchBehaviors : (Z)V
    //   167: iload #6
    //   169: ireturn
  }
  
  void recordLastChildRect(View paramView, Rect paramRect) {
    ((LayoutParams)paramView.getLayoutParams()).setLastChildRect(paramRect);
  }
  
  void removePreDrawListener() {
    if (this.mIsAttachedToWindow && this.mOnPreDrawListener != null)
      getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener); 
    this.mNeedsPreDrawListener = false;
  }
  
  public boolean requestChildRectangleOnScreen(View paramView, Rect paramRect, boolean paramBoolean) {
    Behavior<View> behavior = ((LayoutParams)paramView.getLayoutParams()).getBehavior();
    return (behavior != null && behavior.onRequestChildRectangleOnScreen(this, paramView, paramRect, paramBoolean)) ? true : super.requestChildRectangleOnScreen(paramView, paramRect, paramBoolean);
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean) {
    super.requestDisallowInterceptTouchEvent(paramBoolean);
    if (paramBoolean && !this.mDisallowInterceptReset) {
      resetTouchBehaviors(false);
      this.mDisallowInterceptReset = true;
    } 
  }
  
  public void setFitsSystemWindows(boolean paramBoolean) {
    super.setFitsSystemWindows(paramBoolean);
    setupForInsets();
  }
  
  public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener paramOnHierarchyChangeListener) {
    this.mOnHierarchyChangeListener = paramOnHierarchyChangeListener;
  }
  
  public void setStatusBarBackground(Drawable paramDrawable) {
    Drawable drawable = this.mStatusBarBackground;
    if (drawable != paramDrawable) {
      Drawable drawable1 = null;
      if (drawable != null)
        drawable.setCallback(null); 
      if (paramDrawable != null)
        drawable1 = paramDrawable.mutate(); 
      this.mStatusBarBackground = drawable1;
      if (drawable1 != null) {
        boolean bool;
        if (drawable1.isStateful())
          this.mStatusBarBackground.setState(getDrawableState()); 
        DrawableCompat.setLayoutDirection(this.mStatusBarBackground, ViewCompat.getLayoutDirection((View)this));
        paramDrawable = this.mStatusBarBackground;
        if (getVisibility() == 0) {
          bool = true;
        } else {
          bool = false;
        } 
        paramDrawable.setVisible(bool, false);
        this.mStatusBarBackground.setCallback((Drawable.Callback)this);
      } 
      ViewCompat.postInvalidateOnAnimation((View)this);
    } 
  }
  
  public void setStatusBarBackgroundColor(int paramInt) {
    setStatusBarBackground((Drawable)new ColorDrawable(paramInt));
  }
  
  public void setStatusBarBackgroundResource(int paramInt) {
    Drawable drawable;
    if (paramInt != 0) {
      drawable = ContextCompat.getDrawable(getContext(), paramInt);
    } else {
      drawable = null;
    } 
    setStatusBarBackground(drawable);
  }
  
  public void setVisibility(int paramInt) {
    boolean bool;
    super.setVisibility(paramInt);
    if (paramInt == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    Drawable drawable = this.mStatusBarBackground;
    if (drawable != null && drawable.isVisible() != bool)
      this.mStatusBarBackground.setVisible(bool, false); 
  }
  
  final WindowInsetsCompat setWindowInsets(WindowInsetsCompat paramWindowInsetsCompat) {
    WindowInsetsCompat windowInsetsCompat = paramWindowInsetsCompat;
    if (!ObjectsCompat.equals(this.mLastInsets, paramWindowInsetsCompat)) {
      boolean bool2;
      this.mLastInsets = paramWindowInsetsCompat;
      boolean bool1 = true;
      if (paramWindowInsetsCompat != null && paramWindowInsetsCompat.getSystemWindowInsetTop() > 0) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      this.mDrawStatusBarBackground = bool2;
      if (!bool2 && getBackground() == null) {
        bool2 = bool1;
      } else {
        bool2 = false;
      } 
      setWillNotDraw(bool2);
      windowInsetsCompat = dispatchApplyWindowInsetsToBehaviors(paramWindowInsetsCompat);
      requestLayout();
    } 
    return windowInsetsCompat;
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable) {
    return (super.verifyDrawable(paramDrawable) || paramDrawable == this.mStatusBarBackground);
  }
  
  public static interface AttachedBehavior {
    CoordinatorLayout.Behavior getBehavior();
  }
  
  public static abstract class Behavior<V extends View> {
    public Behavior() {}
    
    public Behavior(Context param1Context, AttributeSet param1AttributeSet) {}
    
    public static Object getTag(View param1View) {
      return ((CoordinatorLayout.LayoutParams)param1View.getLayoutParams()).mBehaviorTag;
    }
    
    public static void setTag(View param1View, Object param1Object) {
      ((CoordinatorLayout.LayoutParams)param1View.getLayoutParams()).mBehaviorTag = param1Object;
    }
    
    public boolean blocksInteractionBelow(CoordinatorLayout param1CoordinatorLayout, V param1V) {
      boolean bool;
      if (getScrimOpacity(param1CoordinatorLayout, param1V) > 0.0F) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public boolean getInsetDodgeRect(CoordinatorLayout param1CoordinatorLayout, V param1V, Rect param1Rect) {
      return false;
    }
    
    public int getScrimColor(CoordinatorLayout param1CoordinatorLayout, V param1V) {
      return -16777216;
    }
    
    public float getScrimOpacity(CoordinatorLayout param1CoordinatorLayout, V param1V) {
      return 0.0F;
    }
    
    public boolean layoutDependsOn(CoordinatorLayout param1CoordinatorLayout, V param1V, View param1View) {
      return false;
    }
    
    public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout param1CoordinatorLayout, V param1V, WindowInsetsCompat param1WindowInsetsCompat) {
      return param1WindowInsetsCompat;
    }
    
    public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams param1LayoutParams) {}
    
    public boolean onDependentViewChanged(CoordinatorLayout param1CoordinatorLayout, V param1V, View param1View) {
      return false;
    }
    
    public void onDependentViewRemoved(CoordinatorLayout param1CoordinatorLayout, V param1V, View param1View) {}
    
    public void onDetachedFromLayoutParams() {}
    
    public boolean onInterceptTouchEvent(CoordinatorLayout param1CoordinatorLayout, V param1V, MotionEvent param1MotionEvent) {
      return false;
    }
    
    public boolean onLayoutChild(CoordinatorLayout param1CoordinatorLayout, V param1V, int param1Int) {
      return false;
    }
    
    public boolean onMeasureChild(CoordinatorLayout param1CoordinatorLayout, V param1V, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      return false;
    }
    
    public boolean onNestedFling(CoordinatorLayout param1CoordinatorLayout, V param1V, View param1View, float param1Float1, float param1Float2, boolean param1Boolean) {
      return false;
    }
    
    public boolean onNestedPreFling(CoordinatorLayout param1CoordinatorLayout, V param1V, View param1View, float param1Float1, float param1Float2) {
      return false;
    }
    
    @Deprecated
    public void onNestedPreScroll(CoordinatorLayout param1CoordinatorLayout, V param1V, View param1View, int param1Int1, int param1Int2, int[] param1ArrayOfint) {}
    
    public void onNestedPreScroll(CoordinatorLayout param1CoordinatorLayout, V param1V, View param1View, int param1Int1, int param1Int2, int[] param1ArrayOfint, int param1Int3) {
      if (param1Int3 == 0)
        onNestedPreScroll(param1CoordinatorLayout, param1V, param1View, param1Int1, param1Int2, param1ArrayOfint); 
    }
    
    @Deprecated
    public void onNestedScroll(CoordinatorLayout param1CoordinatorLayout, V param1V, View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {}
    
    public void onNestedScroll(CoordinatorLayout param1CoordinatorLayout, V param1V, View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
      if (param1Int5 == 0)
        onNestedScroll(param1CoordinatorLayout, param1V, param1View, param1Int1, param1Int2, param1Int3, param1Int4); 
    }
    
    @Deprecated
    public void onNestedScrollAccepted(CoordinatorLayout param1CoordinatorLayout, V param1V, View param1View1, View param1View2, int param1Int) {}
    
    public void onNestedScrollAccepted(CoordinatorLayout param1CoordinatorLayout, V param1V, View param1View1, View param1View2, int param1Int1, int param1Int2) {
      if (param1Int2 == 0)
        onNestedScrollAccepted(param1CoordinatorLayout, param1V, param1View1, param1View2, param1Int1); 
    }
    
    public boolean onRequestChildRectangleOnScreen(CoordinatorLayout param1CoordinatorLayout, V param1V, Rect param1Rect, boolean param1Boolean) {
      return false;
    }
    
    public void onRestoreInstanceState(CoordinatorLayout param1CoordinatorLayout, V param1V, Parcelable param1Parcelable) {}
    
    public Parcelable onSaveInstanceState(CoordinatorLayout param1CoordinatorLayout, V param1V) {
      return (Parcelable)View.BaseSavedState.EMPTY_STATE;
    }
    
    @Deprecated
    public boolean onStartNestedScroll(CoordinatorLayout param1CoordinatorLayout, V param1V, View param1View1, View param1View2, int param1Int) {
      return false;
    }
    
    public boolean onStartNestedScroll(CoordinatorLayout param1CoordinatorLayout, V param1V, View param1View1, View param1View2, int param1Int1, int param1Int2) {
      return (param1Int2 == 0) ? onStartNestedScroll(param1CoordinatorLayout, param1V, param1View1, param1View2, param1Int1) : false;
    }
    
    @Deprecated
    public void onStopNestedScroll(CoordinatorLayout param1CoordinatorLayout, V param1V, View param1View) {}
    
    public void onStopNestedScroll(CoordinatorLayout param1CoordinatorLayout, V param1V, View param1View, int param1Int) {
      if (param1Int == 0)
        onStopNestedScroll(param1CoordinatorLayout, param1V, param1View); 
    }
    
    public boolean onTouchEvent(CoordinatorLayout param1CoordinatorLayout, V param1V, MotionEvent param1MotionEvent) {
      return false;
    }
  }
  
  @Deprecated
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface DefaultBehavior {
    Class<? extends CoordinatorLayout.Behavior> value();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface DispatchChangeEvent {}
  
  private class HierarchyChangeListener implements ViewGroup.OnHierarchyChangeListener {
    public void onChildViewAdded(View param1View1, View param1View2) {
      if (CoordinatorLayout.this.mOnHierarchyChangeListener != null)
        CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewAdded(param1View1, param1View2); 
    }
    
    public void onChildViewRemoved(View param1View1, View param1View2) {
      CoordinatorLayout.this.onChildViewsChanged(2);
      if (CoordinatorLayout.this.mOnHierarchyChangeListener != null)
        CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewRemoved(param1View1, param1View2); 
    }
  }
  
  public static class LayoutParams extends ViewGroup.MarginLayoutParams {
    public int anchorGravity = 0;
    
    public int dodgeInsetEdges = 0;
    
    public int gravity = 0;
    
    public int insetEdge = 0;
    
    public int keyline = -1;
    
    View mAnchorDirectChild;
    
    int mAnchorId = -1;
    
    View mAnchorView;
    
    CoordinatorLayout.Behavior mBehavior;
    
    boolean mBehaviorResolved = false;
    
    Object mBehaviorTag;
    
    private boolean mDidAcceptNestedScrollNonTouch;
    
    private boolean mDidAcceptNestedScrollTouch;
    
    private boolean mDidBlockInteraction;
    
    private boolean mDidChangeAfterNestedScroll;
    
    int mInsetOffsetX;
    
    int mInsetOffsetY;
    
    final Rect mLastChildRect = new Rect();
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
    }
    
    LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, R.styleable.CoordinatorLayout_Layout);
      this.gravity = typedArray.getInteger(R.styleable.CoordinatorLayout_Layout_android_layout_gravity, 0);
      this.mAnchorId = typedArray.getResourceId(R.styleable.CoordinatorLayout_Layout_layout_anchor, -1);
      this.anchorGravity = typedArray.getInteger(R.styleable.CoordinatorLayout_Layout_layout_anchorGravity, 0);
      this.keyline = typedArray.getInteger(R.styleable.CoordinatorLayout_Layout_layout_keyline, -1);
      this.insetEdge = typedArray.getInt(R.styleable.CoordinatorLayout_Layout_layout_insetEdge, 0);
      this.dodgeInsetEdges = typedArray.getInt(R.styleable.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0);
      boolean bool = typedArray.hasValue(R.styleable.CoordinatorLayout_Layout_layout_behavior);
      this.mBehaviorResolved = bool;
      if (bool)
        this.mBehavior = CoordinatorLayout.parseBehavior(param1Context, param1AttributeSet, typedArray.getString(R.styleable.CoordinatorLayout_Layout_layout_behavior)); 
      typedArray.recycle();
      CoordinatorLayout.Behavior behavior = this.mBehavior;
      if (behavior != null)
        behavior.onAttachedToLayoutParams(this); 
    }
    
    public LayoutParams(LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams param1MarginLayoutParams) {
      super(param1MarginLayoutParams);
    }
    
    private void resolveAnchorView(View param1View, CoordinatorLayout param1CoordinatorLayout) {
      View view = param1CoordinatorLayout.findViewById(this.mAnchorId);
      this.mAnchorView = view;
      if (view != null) {
        if (view == param1CoordinatorLayout) {
          if (param1CoordinatorLayout.isInEditMode()) {
            this.mAnchorDirectChild = null;
            this.mAnchorView = null;
            return;
          } 
          throw new IllegalStateException("View can not be anchored to the the parent CoordinatorLayout");
        } 
        for (ViewParent viewParent = view.getParent(); viewParent != param1CoordinatorLayout && viewParent != null; viewParent = viewParent.getParent()) {
          if (viewParent == param1View) {
            if (param1CoordinatorLayout.isInEditMode()) {
              this.mAnchorDirectChild = null;
              this.mAnchorView = null;
              return;
            } 
            throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
          } 
          if (viewParent instanceof View)
            view = (View)viewParent; 
        } 
        this.mAnchorDirectChild = view;
        return;
      } 
      if (param1CoordinatorLayout.isInEditMode()) {
        this.mAnchorDirectChild = null;
        this.mAnchorView = null;
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Could not find CoordinatorLayout descendant view with id ");
      stringBuilder.append(param1CoordinatorLayout.getResources().getResourceName(this.mAnchorId));
      stringBuilder.append(" to anchor view ");
      stringBuilder.append(param1View);
      throw new IllegalStateException(stringBuilder.toString());
    }
    
    private boolean shouldDodge(View param1View, int param1Int) {
      boolean bool;
      int i = GravityCompat.getAbsoluteGravity(((LayoutParams)param1View.getLayoutParams()).insetEdge, param1Int);
      if (i != 0 && (GravityCompat.getAbsoluteGravity(this.dodgeInsetEdges, param1Int) & i) == i) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    private boolean verifyAnchorView(View param1View, CoordinatorLayout param1CoordinatorLayout) {
      if (this.mAnchorView.getId() != this.mAnchorId)
        return false; 
      View view = this.mAnchorView;
      for (ViewParent viewParent = view.getParent(); viewParent != param1CoordinatorLayout; viewParent = viewParent.getParent()) {
        if (viewParent == null || viewParent == param1View) {
          this.mAnchorDirectChild = null;
          this.mAnchorView = null;
          return false;
        } 
        if (viewParent instanceof View)
          view = (View)viewParent; 
      } 
      this.mAnchorDirectChild = view;
      return true;
    }
    
    boolean checkAnchorChanged() {
      boolean bool;
      if (this.mAnchorView == null && this.mAnchorId != -1) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    boolean dependsOn(CoordinatorLayout param1CoordinatorLayout, View param1View1, View param1View2) {
      if (param1View2 != this.mAnchorDirectChild && !shouldDodge(param1View2, ViewCompat.getLayoutDirection((View)param1CoordinatorLayout))) {
        CoordinatorLayout.Behavior<View> behavior = this.mBehavior;
        return (behavior != null && behavior.layoutDependsOn(param1CoordinatorLayout, param1View1, param1View2));
      } 
      return true;
    }
    
    boolean didBlockInteraction() {
      if (this.mBehavior == null)
        this.mDidBlockInteraction = false; 
      return this.mDidBlockInteraction;
    }
    
    View findAnchorView(CoordinatorLayout param1CoordinatorLayout, View param1View) {
      if (this.mAnchorId == -1) {
        this.mAnchorDirectChild = null;
        this.mAnchorView = null;
        return null;
      } 
      if (this.mAnchorView == null || !verifyAnchorView(param1View, param1CoordinatorLayout))
        resolveAnchorView(param1View, param1CoordinatorLayout); 
      return this.mAnchorView;
    }
    
    public int getAnchorId() {
      return this.mAnchorId;
    }
    
    public CoordinatorLayout.Behavior getBehavior() {
      return this.mBehavior;
    }
    
    boolean getChangedAfterNestedScroll() {
      return this.mDidChangeAfterNestedScroll;
    }
    
    Rect getLastChildRect() {
      return this.mLastChildRect;
    }
    
    void invalidateAnchor() {
      this.mAnchorDirectChild = null;
      this.mAnchorView = null;
    }
    
    boolean isBlockingInteractionBelow(CoordinatorLayout param1CoordinatorLayout, View param1View) {
      boolean bool2;
      boolean bool1 = this.mDidBlockInteraction;
      if (bool1)
        return true; 
      CoordinatorLayout.Behavior<View> behavior = this.mBehavior;
      if (behavior != null) {
        bool2 = behavior.blocksInteractionBelow(param1CoordinatorLayout, param1View);
      } else {
        bool2 = false;
      } 
      bool2 |= bool1;
      this.mDidBlockInteraction = bool2;
      return bool2;
    }
    
    boolean isNestedScrollAccepted(int param1Int) {
      return (param1Int != 0) ? ((param1Int != 1) ? false : this.mDidAcceptNestedScrollNonTouch) : this.mDidAcceptNestedScrollTouch;
    }
    
    void resetChangedAfterNestedScroll() {
      this.mDidChangeAfterNestedScroll = false;
    }
    
    void resetNestedScroll(int param1Int) {
      setNestedScrollAccepted(param1Int, false);
    }
    
    void resetTouchBehaviorTracking() {
      this.mDidBlockInteraction = false;
    }
    
    public void setAnchorId(int param1Int) {
      invalidateAnchor();
      this.mAnchorId = param1Int;
    }
    
    public void setBehavior(CoordinatorLayout.Behavior param1Behavior) {
      CoordinatorLayout.Behavior behavior = this.mBehavior;
      if (behavior != param1Behavior) {
        if (behavior != null)
          behavior.onDetachedFromLayoutParams(); 
        this.mBehavior = param1Behavior;
        this.mBehaviorTag = null;
        this.mBehaviorResolved = true;
        if (param1Behavior != null)
          param1Behavior.onAttachedToLayoutParams(this); 
      } 
    }
    
    void setChangedAfterNestedScroll(boolean param1Boolean) {
      this.mDidChangeAfterNestedScroll = param1Boolean;
    }
    
    void setLastChildRect(Rect param1Rect) {
      this.mLastChildRect.set(param1Rect);
    }
    
    void setNestedScrollAccepted(int param1Int, boolean param1Boolean) {
      if (param1Int != 0) {
        if (param1Int == 1)
          this.mDidAcceptNestedScrollNonTouch = param1Boolean; 
      } else {
        this.mDidAcceptNestedScrollTouch = param1Boolean;
      } 
    }
  }
  
  class OnPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
    public boolean onPreDraw() {
      CoordinatorLayout.this.onChildViewsChanged(0);
      return true;
    }
  }
  
  protected static class SavedState extends AbsSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR = (Parcelable.Creator<SavedState>)new Parcelable.ClassLoaderCreator<SavedState>() {
        public CoordinatorLayout.SavedState createFromParcel(Parcel param2Parcel) {
          return new CoordinatorLayout.SavedState(param2Parcel, null);
        }
        
        public CoordinatorLayout.SavedState createFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
          return new CoordinatorLayout.SavedState(param2Parcel, param2ClassLoader);
        }
        
        public CoordinatorLayout.SavedState[] newArray(int param2Int) {
          return new CoordinatorLayout.SavedState[param2Int];
        }
      };
    
    SparseArray<Parcelable> behaviorStates;
    
    public SavedState(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      super(param1Parcel, param1ClassLoader);
      int i = param1Parcel.readInt();
      int[] arrayOfInt = new int[i];
      param1Parcel.readIntArray(arrayOfInt);
      Parcelable[] arrayOfParcelable = param1Parcel.readParcelableArray(param1ClassLoader);
      this.behaviorStates = new SparseArray(i);
      for (byte b = 0; b < i; b++)
        this.behaviorStates.append(arrayOfInt[b], arrayOfParcelable[b]); 
    }
    
    public SavedState(Parcelable param1Parcelable) {
      super(param1Parcelable);
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      byte b2;
      super.writeToParcel(param1Parcel, param1Int);
      SparseArray<Parcelable> sparseArray = this.behaviorStates;
      byte b1 = 0;
      if (sparseArray != null) {
        b2 = sparseArray.size();
      } else {
        b2 = 0;
      } 
      param1Parcel.writeInt(b2);
      int[] arrayOfInt = new int[b2];
      Parcelable[] arrayOfParcelable = new Parcelable[b2];
      while (b1 < b2) {
        arrayOfInt[b1] = this.behaviorStates.keyAt(b1);
        arrayOfParcelable[b1] = (Parcelable)this.behaviorStates.valueAt(b1);
        b1++;
      } 
      param1Parcel.writeIntArray(arrayOfInt);
      param1Parcel.writeParcelableArray(arrayOfParcelable, param1Int);
    }
  }
  
  static final class null implements Parcelable.ClassLoaderCreator<SavedState> {
    public CoordinatorLayout.SavedState createFromParcel(Parcel param1Parcel) {
      return new CoordinatorLayout.SavedState(param1Parcel, null);
    }
    
    public CoordinatorLayout.SavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return new CoordinatorLayout.SavedState(param1Parcel, param1ClassLoader);
    }
    
    public CoordinatorLayout.SavedState[] newArray(int param1Int) {
      return new CoordinatorLayout.SavedState[param1Int];
    }
  }
  
  static class ViewElevationComparator implements Comparator<View> {
    public int compare(View param1View1, View param1View2) {
      float f1 = ViewCompat.getZ(param1View1);
      float f2 = ViewCompat.getZ(param1View2);
      return (f1 > f2) ? -1 : ((f1 < f2) ? 1 : 0);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\CoordinatorLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */