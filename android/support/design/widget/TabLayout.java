package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.R;
import android.support.v4.util.Pools;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PointerIconCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.DecorView;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.TooltipCompat;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

@DecorView
public class TabLayout extends HorizontalScrollView {
  private static final int ANIMATION_DURATION = 300;
  
  static final int DEFAULT_GAP_TEXT_ICON = 8;
  
  private static final int DEFAULT_HEIGHT = 48;
  
  private static final int DEFAULT_HEIGHT_WITH_TEXT_ICON = 72;
  
  static final int FIXED_WRAP_GUTTER_MIN = 16;
  
  public static final int GRAVITY_CENTER = 1;
  
  public static final int GRAVITY_FILL = 0;
  
  private static final int INVALID_WIDTH = -1;
  
  public static final int MODE_FIXED = 1;
  
  public static final int MODE_SCROLLABLE = 0;
  
  static final int MOTION_NON_ADJACENT_OFFSET = 24;
  
  private static final int TAB_MIN_WIDTH_MARGIN = 56;
  
  private static final Pools.Pool<Tab> sTabPool = (Pools.Pool<Tab>)new Pools.SynchronizedPool(16);
  
  private AdapterChangeListener mAdapterChangeListener;
  
  private int mContentInsetStart;
  
  private OnTabSelectedListener mCurrentVpSelectedListener;
  
  int mMode;
  
  private TabLayoutOnPageChangeListener mPageChangeListener;
  
  private PagerAdapter mPagerAdapter;
  
  private DataSetObserver mPagerAdapterObserver;
  
  private final int mRequestedTabMaxWidth;
  
  private final int mRequestedTabMinWidth;
  
  private ValueAnimator mScrollAnimator;
  
  private final int mScrollableTabMinWidth;
  
  private OnTabSelectedListener mSelectedListener;
  
  private final ArrayList<OnTabSelectedListener> mSelectedListeners;
  
  private Tab mSelectedTab;
  
  private boolean mSetupViewPagerImplicitly;
  
  final int mTabBackgroundResId;
  
  int mTabGravity;
  
  int mTabMaxWidth;
  
  int mTabPaddingBottom;
  
  int mTabPaddingEnd;
  
  int mTabPaddingStart;
  
  int mTabPaddingTop;
  
  private final SlidingTabStrip mTabStrip;
  
  int mTabTextAppearance;
  
  ColorStateList mTabTextColors;
  
  float mTabTextMultiLineSize;
  
  float mTabTextSize;
  
  private final Pools.Pool<TabView> mTabViewPool;
  
  private final ArrayList<Tab> mTabs;
  
  ViewPager mViewPager;
  
  public TabLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public TabLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public TabLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    Resources resources;
    this.mTabs = new ArrayList<Tab>();
    this.mTabMaxWidth = Integer.MAX_VALUE;
    this.mSelectedListeners = new ArrayList<OnTabSelectedListener>();
    this.mTabViewPool = (Pools.Pool<TabView>)new Pools.SimplePool(12);
    ThemeUtils.checkAppCompatTheme(paramContext);
    setHorizontalScrollBarEnabled(false);
    SlidingTabStrip slidingTabStrip = new SlidingTabStrip(paramContext);
    this.mTabStrip = slidingTabStrip;
    super.addView((View)slidingTabStrip, 0, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -1));
    null = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.TabLayout, paramInt, R.style.Widget_Design_TabLayout);
    this.mTabStrip.setSelectedIndicatorHeight(null.getDimensionPixelSize(R.styleable.TabLayout_tabIndicatorHeight, 0));
    this.mTabStrip.setSelectedIndicatorColor(null.getColor(R.styleable.TabLayout_tabIndicatorColor, 0));
    paramInt = null.getDimensionPixelSize(R.styleable.TabLayout_tabPadding, 0);
    this.mTabPaddingBottom = paramInt;
    this.mTabPaddingEnd = paramInt;
    this.mTabPaddingTop = paramInt;
    this.mTabPaddingStart = paramInt;
    this.mTabPaddingStart = null.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingStart, this.mTabPaddingStart);
    this.mTabPaddingTop = null.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingTop, this.mTabPaddingTop);
    this.mTabPaddingEnd = null.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingEnd, this.mTabPaddingEnd);
    this.mTabPaddingBottom = null.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingBottom, this.mTabPaddingBottom);
    paramInt = null.getResourceId(R.styleable.TabLayout_tabTextAppearance, R.style.TextAppearance_Design_Tab);
    this.mTabTextAppearance = paramInt;
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramInt, R.styleable.TextAppearance);
    try {
      this.mTabTextSize = typedArray.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
      this.mTabTextColors = typedArray.getColorStateList(R.styleable.TextAppearance_android_textColor);
      typedArray.recycle();
      if (null.hasValue(R.styleable.TabLayout_tabTextColor))
        this.mTabTextColors = null.getColorStateList(R.styleable.TabLayout_tabTextColor); 
      if (null.hasValue(R.styleable.TabLayout_tabSelectedTextColor)) {
        paramInt = null.getColor(R.styleable.TabLayout_tabSelectedTextColor, 0);
        this.mTabTextColors = createColorStateList(this.mTabTextColors.getDefaultColor(), paramInt);
      } 
      this.mRequestedTabMinWidth = null.getDimensionPixelSize(R.styleable.TabLayout_tabMinWidth, -1);
      this.mRequestedTabMaxWidth = null.getDimensionPixelSize(R.styleable.TabLayout_tabMaxWidth, -1);
      this.mTabBackgroundResId = null.getResourceId(R.styleable.TabLayout_tabBackground, 0);
      this.mContentInsetStart = null.getDimensionPixelSize(R.styleable.TabLayout_tabContentStart, 0);
      this.mMode = null.getInt(R.styleable.TabLayout_tabMode, 1);
      this.mTabGravity = null.getInt(R.styleable.TabLayout_tabGravity, 0);
      null.recycle();
      resources = getResources();
      this.mTabTextMultiLineSize = resources.getDimensionPixelSize(R.dimen.design_tab_text_size_2line);
      this.mScrollableTabMinWidth = resources.getDimensionPixelSize(R.dimen.design_tab_scrollable_min_width);
      return;
    } finally {
      resources.recycle();
    } 
  }
  
  private void addTabFromItemView(TabItem paramTabItem) {
    Tab tab = newTab();
    if (paramTabItem.mText != null)
      tab.setText(paramTabItem.mText); 
    if (paramTabItem.mIcon != null)
      tab.setIcon(paramTabItem.mIcon); 
    if (paramTabItem.mCustomLayout != 0)
      tab.setCustomView(paramTabItem.mCustomLayout); 
    if (!TextUtils.isEmpty(paramTabItem.getContentDescription()))
      tab.setContentDescription(paramTabItem.getContentDescription()); 
    addTab(tab);
  }
  
  private void addTabView(Tab paramTab) {
    TabView tabView = paramTab.mView;
    this.mTabStrip.addView((View)tabView, paramTab.getPosition(), (ViewGroup.LayoutParams)createLayoutParamsForTabs());
  }
  
  private void addViewInternal(View paramView) {
    if (paramView instanceof TabItem) {
      addTabFromItemView((TabItem)paramView);
      return;
    } 
    throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
  }
  
  private void animateToTab(int paramInt) {
    if (paramInt == -1)
      return; 
    if (getWindowToken() == null || !ViewCompat.isLaidOut((View)this) || this.mTabStrip.childrenNeedLayout()) {
      setScrollPosition(paramInt, 0.0F, true);
      return;
    } 
    int i = getScrollX();
    int j = calculateScrollXForTab(paramInt, 0.0F);
    if (i != j) {
      ensureScrollAnimator();
      this.mScrollAnimator.setIntValues(new int[] { i, j });
      this.mScrollAnimator.start();
    } 
    this.mTabStrip.animateIndicatorToPosition(paramInt, 300);
  }
  
  private void applyModeAndGravity() {
    if (this.mMode == 0) {
      i = Math.max(0, this.mContentInsetStart - this.mTabPaddingStart);
    } else {
      i = 0;
    } 
    ViewCompat.setPaddingRelative((View)this.mTabStrip, i, 0, 0, 0);
    int i = this.mMode;
    if (i != 0) {
      if (i == 1)
        this.mTabStrip.setGravity(1); 
    } else {
      this.mTabStrip.setGravity(8388611);
    } 
    updateTabViews(true);
  }
  
  private int calculateScrollXForTab(int paramInt, float paramFloat) {
    int i = this.mMode;
    int j = 0;
    if (i == 0) {
      View view2;
      View view1 = this.mTabStrip.getChildAt(paramInt);
      if (++paramInt < this.mTabStrip.getChildCount()) {
        view2 = this.mTabStrip.getChildAt(paramInt);
      } else {
        view2 = null;
      } 
      if (view1 != null) {
        paramInt = view1.getWidth();
      } else {
        paramInt = 0;
      } 
      if (view2 != null)
        j = view2.getWidth(); 
      i = view1.getLeft() + paramInt / 2 - getWidth() / 2;
      paramInt = (int)((paramInt + j) * 0.5F * paramFloat);
      if (ViewCompat.getLayoutDirection((View)this) == 0) {
        paramInt = i + paramInt;
      } else {
        paramInt = i - paramInt;
      } 
      return paramInt;
    } 
    return 0;
  }
  
  private void configureTab(Tab paramTab, int paramInt) {
    paramTab.setPosition(paramInt);
    this.mTabs.add(paramInt, paramTab);
    int i = this.mTabs.size();
    while (++paramInt < i)
      ((Tab)this.mTabs.get(paramInt)).setPosition(paramInt); 
  }
  
  private static ColorStateList createColorStateList(int paramInt1, int paramInt2) {
    return new ColorStateList(new int[][] { SELECTED_STATE_SET, EMPTY_STATE_SET }, new int[] { paramInt2, paramInt1 });
  }
  
  private LinearLayout.LayoutParams createLayoutParamsForTabs() {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
    updateTabViewLayoutParams(layoutParams);
    return layoutParams;
  }
  
  private TabView createTabView(Tab paramTab) {
    TabView tabView;
    Pools.Pool<TabView> pool1 = this.mTabViewPool;
    if (pool1 != null) {
      TabView tabView1 = (TabView)pool1.acquire();
    } else {
      pool1 = null;
    } 
    Pools.Pool<TabView> pool2 = pool1;
    if (pool1 == null)
      tabView = new TabView(getContext()); 
    tabView.setTab(paramTab);
    tabView.setFocusable(true);
    tabView.setMinimumWidth(getTabMinWidth());
    return tabView;
  }
  
  private void dispatchTabReselected(Tab paramTab) {
    for (int i = this.mSelectedListeners.size() - 1; i >= 0; i--)
      ((OnTabSelectedListener)this.mSelectedListeners.get(i)).onTabReselected(paramTab); 
  }
  
  private void dispatchTabSelected(Tab paramTab) {
    for (int i = this.mSelectedListeners.size() - 1; i >= 0; i--)
      ((OnTabSelectedListener)this.mSelectedListeners.get(i)).onTabSelected(paramTab); 
  }
  
  private void dispatchTabUnselected(Tab paramTab) {
    for (int i = this.mSelectedListeners.size() - 1; i >= 0; i--)
      ((OnTabSelectedListener)this.mSelectedListeners.get(i)).onTabUnselected(paramTab); 
  }
  
  private void ensureScrollAnimator() {
    if (this.mScrollAnimator == null) {
      ValueAnimator valueAnimator = new ValueAnimator();
      this.mScrollAnimator = valueAnimator;
      valueAnimator.setInterpolator((TimeInterpolator)AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
      this.mScrollAnimator.setDuration(300L);
      this.mScrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
              TabLayout.this.scrollTo(((Integer)param1ValueAnimator.getAnimatedValue()).intValue(), 0);
            }
          });
    } 
  }
  
  private int getDefaultHeight() {
    byte b3;
    int i = this.mTabs.size();
    byte b1 = 0;
    byte b2 = 0;
    while (true) {
      b3 = b1;
      if (b2 < i) {
        Tab tab = this.mTabs.get(b2);
        if (tab != null && tab.getIcon() != null && !TextUtils.isEmpty(tab.getText())) {
          b3 = 1;
          break;
        } 
        b2++;
        continue;
      } 
      break;
    } 
    if (b3) {
      b3 = 72;
    } else {
      b3 = 48;
    } 
    return b3;
  }
  
  private float getScrollPosition() {
    return this.mTabStrip.getIndicatorPosition();
  }
  
  private int getTabMinWidth() {
    int i = this.mRequestedTabMinWidth;
    if (i != -1)
      return i; 
    if (this.mMode == 0) {
      i = this.mScrollableTabMinWidth;
    } else {
      i = 0;
    } 
    return i;
  }
  
  private int getTabScrollRange() {
    return Math.max(0, this.mTabStrip.getWidth() - getWidth() - getPaddingLeft() - getPaddingRight());
  }
  
  private void removeTabViewAt(int paramInt) {
    TabView tabView = (TabView)this.mTabStrip.getChildAt(paramInt);
    this.mTabStrip.removeViewAt(paramInt);
    if (tabView != null) {
      tabView.reset();
      this.mTabViewPool.release(tabView);
    } 
    requestLayout();
  }
  
  private void setSelectedTabView(int paramInt) {
    int i = this.mTabStrip.getChildCount();
    if (paramInt < i)
      for (byte b = 0; b < i; b++) {
        boolean bool;
        View view = this.mTabStrip.getChildAt(b);
        if (b == paramInt) {
          bool = true;
        } else {
          bool = false;
        } 
        view.setSelected(bool);
      }  
  }
  
  private void setupWithViewPager(ViewPager paramViewPager, boolean paramBoolean1, boolean paramBoolean2) {
    ViewPager viewPager = this.mViewPager;
    if (viewPager != null) {
      TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener = this.mPageChangeListener;
      if (tabLayoutOnPageChangeListener != null)
        viewPager.removeOnPageChangeListener(tabLayoutOnPageChangeListener); 
      AdapterChangeListener adapterChangeListener = this.mAdapterChangeListener;
      if (adapterChangeListener != null)
        this.mViewPager.removeOnAdapterChangeListener(adapterChangeListener); 
    } 
    OnTabSelectedListener onTabSelectedListener = this.mCurrentVpSelectedListener;
    if (onTabSelectedListener != null) {
      removeOnTabSelectedListener(onTabSelectedListener);
      this.mCurrentVpSelectedListener = null;
    } 
    if (paramViewPager != null) {
      this.mViewPager = paramViewPager;
      if (this.mPageChangeListener == null)
        this.mPageChangeListener = new TabLayoutOnPageChangeListener(this); 
      this.mPageChangeListener.reset();
      paramViewPager.addOnPageChangeListener(this.mPageChangeListener);
      onTabSelectedListener = new ViewPagerOnTabSelectedListener(paramViewPager);
      this.mCurrentVpSelectedListener = onTabSelectedListener;
      addOnTabSelectedListener(onTabSelectedListener);
      PagerAdapter pagerAdapter = paramViewPager.getAdapter();
      if (pagerAdapter != null)
        setPagerAdapter(pagerAdapter, paramBoolean1); 
      if (this.mAdapterChangeListener == null)
        this.mAdapterChangeListener = new AdapterChangeListener(); 
      this.mAdapterChangeListener.setAutoRefresh(paramBoolean1);
      paramViewPager.addOnAdapterChangeListener(this.mAdapterChangeListener);
      setScrollPosition(paramViewPager.getCurrentItem(), 0.0F, true);
    } else {
      this.mViewPager = null;
      setPagerAdapter((PagerAdapter)null, false);
    } 
    this.mSetupViewPagerImplicitly = paramBoolean2;
  }
  
  private void updateAllTabs() {
    int i = this.mTabs.size();
    for (byte b = 0; b < i; b++)
      ((Tab)this.mTabs.get(b)).updateView(); 
  }
  
  private void updateTabViewLayoutParams(LinearLayout.LayoutParams paramLayoutParams) {
    if (this.mMode == 1 && this.mTabGravity == 0) {
      paramLayoutParams.width = 0;
      paramLayoutParams.weight = 1.0F;
    } else {
      paramLayoutParams.width = -2;
      paramLayoutParams.weight = 0.0F;
    } 
  }
  
  public void addOnTabSelectedListener(OnTabSelectedListener paramOnTabSelectedListener) {
    if (!this.mSelectedListeners.contains(paramOnTabSelectedListener))
      this.mSelectedListeners.add(paramOnTabSelectedListener); 
  }
  
  public void addTab(Tab paramTab) {
    addTab(paramTab, this.mTabs.isEmpty());
  }
  
  public void addTab(Tab paramTab, int paramInt) {
    addTab(paramTab, paramInt, this.mTabs.isEmpty());
  }
  
  public void addTab(Tab paramTab, int paramInt, boolean paramBoolean) {
    if (paramTab.mParent == this) {
      configureTab(paramTab, paramInt);
      addTabView(paramTab);
      if (paramBoolean)
        paramTab.select(); 
      return;
    } 
    throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
  }
  
  public void addTab(Tab paramTab, boolean paramBoolean) {
    addTab(paramTab, this.mTabs.size(), paramBoolean);
  }
  
  public void addView(View paramView) {
    addViewInternal(paramView);
  }
  
  public void addView(View paramView, int paramInt) {
    addViewInternal(paramView);
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams) {
    addViewInternal(paramView);
  }
  
  public void addView(View paramView, ViewGroup.LayoutParams paramLayoutParams) {
    addViewInternal(paramView);
  }
  
  public void clearOnTabSelectedListeners() {
    this.mSelectedListeners.clear();
  }
  
  int dpToPx(int paramInt) {
    return Math.round((getResources().getDisplayMetrics()).density * paramInt);
  }
  
  public FrameLayout.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return generateDefaultLayoutParams();
  }
  
  public int getSelectedTabPosition() {
    byte b;
    Tab tab = this.mSelectedTab;
    if (tab != null) {
      b = tab.getPosition();
    } else {
      b = -1;
    } 
    return b;
  }
  
  public Tab getTabAt(int paramInt) {
    return (paramInt < 0 || paramInt >= getTabCount()) ? null : this.mTabs.get(paramInt);
  }
  
  public int getTabCount() {
    return this.mTabs.size();
  }
  
  public int getTabGravity() {
    return this.mTabGravity;
  }
  
  int getTabMaxWidth() {
    return this.mTabMaxWidth;
  }
  
  public int getTabMode() {
    return this.mMode;
  }
  
  public ColorStateList getTabTextColors() {
    return this.mTabTextColors;
  }
  
  public Tab newTab() {
    Tab tab1 = (Tab)sTabPool.acquire();
    Tab tab2 = tab1;
    if (tab1 == null)
      tab2 = new Tab(); 
    tab2.mParent = this;
    tab2.mView = createTabView(tab2);
    return tab2;
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (this.mViewPager == null) {
      ViewParent viewParent = getParent();
      if (viewParent instanceof ViewPager)
        setupWithViewPager((ViewPager)viewParent, true, true); 
    } 
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    if (this.mSetupViewPagerImplicitly) {
      setupWithViewPager((ViewPager)null);
      this.mSetupViewPagerImplicitly = false;
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    View view;
    int i = dpToPx(getDefaultHeight()) + getPaddingTop() + getPaddingBottom();
    int j = View.MeasureSpec.getMode(paramInt2);
    if (j != Integer.MIN_VALUE) {
      if (j == 0)
        paramInt2 = View.MeasureSpec.makeMeasureSpec(i, 1073741824); 
    } else {
      paramInt2 = View.MeasureSpec.makeMeasureSpec(Math.min(i, View.MeasureSpec.getSize(paramInt2)), 1073741824);
    } 
    i = View.MeasureSpec.getSize(paramInt1);
    if (View.MeasureSpec.getMode(paramInt1) != 0) {
      j = this.mRequestedTabMaxWidth;
      if (j <= 0)
        j = i - dpToPx(56); 
      this.mTabMaxWidth = j;
    } 
    super.onMeasure(paramInt1, paramInt2);
    j = getChildCount();
    paramInt1 = 1;
    if (j == 1) {
      j = 0;
      view = getChildAt(0);
      i = this.mMode;
      if (i != 0) {
        if (i != 1) {
          paramInt1 = j;
        } else {
          if (view.getMeasuredWidth() != getMeasuredWidth()) {
            if (paramInt1 != 0) {
              paramInt1 = getChildMeasureSpec(paramInt2, getPaddingTop() + getPaddingBottom(), (view.getLayoutParams()).height);
              view.measure(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), paramInt1);
            } 
            return;
          } 
          paramInt1 = 0;
        } 
      } else {
        if (view.getMeasuredWidth() < getMeasuredWidth()) {
          if (paramInt1 != 0) {
            paramInt1 = getChildMeasureSpec(paramInt2, getPaddingTop() + getPaddingBottom(), (view.getLayoutParams()).height);
            view.measure(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), paramInt1);
          } 
          return;
        } 
        paramInt1 = 0;
      } 
    } else {
      return;
    } 
    if (paramInt1 != 0) {
      paramInt1 = getChildMeasureSpec(paramInt2, getPaddingTop() + getPaddingBottom(), (view.getLayoutParams()).height);
      view.measure(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), paramInt1);
    } 
  }
  
  void populateFromPagerAdapter() {
    removeAllTabs();
    PagerAdapter pagerAdapter = this.mPagerAdapter;
    if (pagerAdapter != null) {
      int i = pagerAdapter.getCount();
      int j;
      for (j = 0; j < i; j++)
        addTab(newTab().setText(this.mPagerAdapter.getPageTitle(j)), false); 
      ViewPager viewPager = this.mViewPager;
      if (viewPager != null && i > 0) {
        j = viewPager.getCurrentItem();
        if (j != getSelectedTabPosition() && j < getTabCount())
          selectTab(getTabAt(j)); 
      } 
    } 
  }
  
  public void removeAllTabs() {
    for (int i = this.mTabStrip.getChildCount() - 1; i >= 0; i--)
      removeTabViewAt(i); 
    Iterator<Tab> iterator = this.mTabs.iterator();
    while (iterator.hasNext()) {
      Tab tab = iterator.next();
      iterator.remove();
      tab.reset();
      sTabPool.release(tab);
    } 
    this.mSelectedTab = null;
  }
  
  public void removeOnTabSelectedListener(OnTabSelectedListener paramOnTabSelectedListener) {
    this.mSelectedListeners.remove(paramOnTabSelectedListener);
  }
  
  public void removeTab(Tab paramTab) {
    if (paramTab.mParent == this) {
      removeTabAt(paramTab.getPosition());
      return;
    } 
    throw new IllegalArgumentException("Tab does not belong to this TabLayout.");
  }
  
  public void removeTabAt(int paramInt) {
    int i;
    Tab tab = this.mSelectedTab;
    if (tab != null) {
      i = tab.getPosition();
    } else {
      i = 0;
    } 
    removeTabViewAt(paramInt);
    tab = this.mTabs.remove(paramInt);
    if (tab != null) {
      tab.reset();
      sTabPool.release(tab);
    } 
    int j = this.mTabs.size();
    for (int k = paramInt; k < j; k++)
      ((Tab)this.mTabs.get(k)).setPosition(k); 
    if (i == paramInt) {
      if (this.mTabs.isEmpty()) {
        tab = null;
      } else {
        tab = this.mTabs.get(Math.max(0, paramInt - 1));
      } 
      selectTab(tab);
    } 
  }
  
  void selectTab(Tab paramTab) {
    selectTab(paramTab, true);
  }
  
  void selectTab(Tab paramTab, boolean paramBoolean) {
    Tab tab = this.mSelectedTab;
    if (tab == paramTab) {
      if (tab != null) {
        dispatchTabReselected(paramTab);
        animateToTab(paramTab.getPosition());
      } 
    } else {
      byte b;
      if (paramTab != null) {
        b = paramTab.getPosition();
      } else {
        b = -1;
      } 
      if (paramBoolean) {
        if ((tab == null || tab.getPosition() == -1) && b != -1) {
          setScrollPosition(b, 0.0F, true);
        } else {
          animateToTab(b);
        } 
        if (b != -1)
          setSelectedTabView(b); 
      } 
      if (tab != null)
        dispatchTabUnselected(tab); 
      this.mSelectedTab = paramTab;
      if (paramTab != null)
        dispatchTabSelected(paramTab); 
    } 
  }
  
  @Deprecated
  public void setOnTabSelectedListener(OnTabSelectedListener paramOnTabSelectedListener) {
    OnTabSelectedListener onTabSelectedListener = this.mSelectedListener;
    if (onTabSelectedListener != null)
      removeOnTabSelectedListener(onTabSelectedListener); 
    this.mSelectedListener = paramOnTabSelectedListener;
    if (paramOnTabSelectedListener != null)
      addOnTabSelectedListener(paramOnTabSelectedListener); 
  }
  
  void setPagerAdapter(PagerAdapter paramPagerAdapter, boolean paramBoolean) {
    PagerAdapter pagerAdapter = this.mPagerAdapter;
    if (pagerAdapter != null) {
      DataSetObserver dataSetObserver = this.mPagerAdapterObserver;
      if (dataSetObserver != null)
        pagerAdapter.unregisterDataSetObserver(dataSetObserver); 
    } 
    this.mPagerAdapter = paramPagerAdapter;
    if (paramBoolean && paramPagerAdapter != null) {
      if (this.mPagerAdapterObserver == null)
        this.mPagerAdapterObserver = new PagerAdapterObserver(); 
      paramPagerAdapter.registerDataSetObserver(this.mPagerAdapterObserver);
    } 
    populateFromPagerAdapter();
  }
  
  void setScrollAnimatorListener(Animator.AnimatorListener paramAnimatorListener) {
    ensureScrollAnimator();
    this.mScrollAnimator.addListener(paramAnimatorListener);
  }
  
  public void setScrollPosition(int paramInt, float paramFloat, boolean paramBoolean) {
    setScrollPosition(paramInt, paramFloat, paramBoolean, true);
  }
  
  void setScrollPosition(int paramInt, float paramFloat, boolean paramBoolean1, boolean paramBoolean2) {
    int i = Math.round(paramInt + paramFloat);
    if (i >= 0 && i < this.mTabStrip.getChildCount()) {
      if (paramBoolean2)
        this.mTabStrip.setIndicatorPositionFromTabPosition(paramInt, paramFloat); 
      ValueAnimator valueAnimator = this.mScrollAnimator;
      if (valueAnimator != null && valueAnimator.isRunning())
        this.mScrollAnimator.cancel(); 
      scrollTo(calculateScrollXForTab(paramInt, paramFloat), 0);
      if (paramBoolean1)
        setSelectedTabView(i); 
    } 
  }
  
  public void setSelectedTabIndicatorColor(int paramInt) {
    this.mTabStrip.setSelectedIndicatorColor(paramInt);
  }
  
  public void setSelectedTabIndicatorHeight(int paramInt) {
    this.mTabStrip.setSelectedIndicatorHeight(paramInt);
  }
  
  public void setTabGravity(int paramInt) {
    if (this.mTabGravity != paramInt) {
      this.mTabGravity = paramInt;
      applyModeAndGravity();
    } 
  }
  
  public void setTabMode(int paramInt) {
    if (paramInt != this.mMode) {
      this.mMode = paramInt;
      applyModeAndGravity();
    } 
  }
  
  public void setTabTextColors(int paramInt1, int paramInt2) {
    setTabTextColors(createColorStateList(paramInt1, paramInt2));
  }
  
  public void setTabTextColors(ColorStateList paramColorStateList) {
    if (this.mTabTextColors != paramColorStateList) {
      this.mTabTextColors = paramColorStateList;
      updateAllTabs();
    } 
  }
  
  @Deprecated
  public void setTabsFromPagerAdapter(PagerAdapter paramPagerAdapter) {
    setPagerAdapter(paramPagerAdapter, false);
  }
  
  public void setupWithViewPager(ViewPager paramViewPager) {
    setupWithViewPager(paramViewPager, true);
  }
  
  public void setupWithViewPager(ViewPager paramViewPager, boolean paramBoolean) {
    setupWithViewPager(paramViewPager, paramBoolean, false);
  }
  
  public boolean shouldDelayChildPressedState() {
    boolean bool;
    if (getTabScrollRange() > 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  void updateTabViews(boolean paramBoolean) {
    for (byte b = 0; b < this.mTabStrip.getChildCount(); b++) {
      View view = this.mTabStrip.getChildAt(b);
      view.setMinimumWidth(getTabMinWidth());
      updateTabViewLayoutParams((LinearLayout.LayoutParams)view.getLayoutParams());
      if (paramBoolean)
        view.requestLayout(); 
    } 
  }
  
  private class AdapterChangeListener implements ViewPager.OnAdapterChangeListener {
    private boolean mAutoRefresh;
    
    public void onAdapterChanged(ViewPager param1ViewPager, PagerAdapter param1PagerAdapter1, PagerAdapter param1PagerAdapter2) {
      if (TabLayout.this.mViewPager == param1ViewPager)
        TabLayout.this.setPagerAdapter(param1PagerAdapter2, this.mAutoRefresh); 
    }
    
    void setAutoRefresh(boolean param1Boolean) {
      this.mAutoRefresh = param1Boolean;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Mode {}
  
  public static interface OnTabSelectedListener {
    void onTabReselected(TabLayout.Tab param1Tab);
    
    void onTabSelected(TabLayout.Tab param1Tab);
    
    void onTabUnselected(TabLayout.Tab param1Tab);
  }
  
  private class PagerAdapterObserver extends DataSetObserver {
    public void onChanged() {
      TabLayout.this.populateFromPagerAdapter();
    }
    
    public void onInvalidated() {
      TabLayout.this.populateFromPagerAdapter();
    }
  }
  
  private class SlidingTabStrip extends LinearLayout {
    private ValueAnimator mIndicatorAnimator;
    
    private int mIndicatorLeft = -1;
    
    private int mIndicatorRight = -1;
    
    private int mLayoutDirection = -1;
    
    private int mSelectedIndicatorHeight;
    
    private final Paint mSelectedIndicatorPaint;
    
    int mSelectedPosition = -1;
    
    float mSelectionOffset;
    
    SlidingTabStrip(Context param1Context) {
      super(param1Context);
      setWillNotDraw(false);
      this.mSelectedIndicatorPaint = new Paint();
    }
    
    private void updateIndicatorPosition() {
      byte b1;
      byte b2;
      View view = getChildAt(this.mSelectedPosition);
      if (view != null && view.getWidth() > 0) {
        int i = view.getLeft();
        int j = view.getRight();
        b1 = j;
        b2 = i;
        if (this.mSelectionOffset > 0.0F) {
          b1 = j;
          b2 = i;
          if (this.mSelectedPosition < getChildCount() - 1) {
            view = getChildAt(this.mSelectedPosition + 1);
            float f1 = this.mSelectionOffset;
            float f2 = view.getLeft();
            float f3 = this.mSelectionOffset;
            b2 = (int)(f1 * f2 + (1.0F - f3) * i);
            b1 = (int)(f3 * view.getRight() + (1.0F - this.mSelectionOffset) * j);
          } 
        } 
      } else {
        b2 = -1;
        b1 = -1;
      } 
      setIndicatorPosition(b2, b1);
    }
    
    void animateIndicatorToPosition(final int position, int param1Int2) {
      final int startLeft;
      final int startRight;
      ValueAnimator valueAnimator = this.mIndicatorAnimator;
      if (valueAnimator != null && valueAnimator.isRunning())
        this.mIndicatorAnimator.cancel(); 
      if (ViewCompat.getLayoutDirection((View)this) == 1) {
        i = 1;
      } else {
        i = 0;
      } 
      View view = getChildAt(position);
      if (view == null) {
        updateIndicatorPosition();
        return;
      } 
      final int targetLeft = view.getLeft();
      final int targetRight = view.getRight();
      if (Math.abs(position - this.mSelectedPosition) <= 1) {
        i = this.mIndicatorLeft;
        m = this.mIndicatorRight;
      } else {
        m = TabLayout.this.dpToPx(24);
        if ((position < this.mSelectedPosition) ? (i != 0) : (i == 0)) {
          i = j - m;
        } else {
          i = m + k;
        } 
        m = i;
      } 
      if (i != j || m != k) {
        ValueAnimator valueAnimator1 = new ValueAnimator();
        this.mIndicatorAnimator = valueAnimator1;
        valueAnimator1.setInterpolator((TimeInterpolator)AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        valueAnimator1.setDuration(param1Int2);
        valueAnimator1.setFloatValues(new float[] { 0.0F, 1.0F });
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
              public void onAnimationUpdate(ValueAnimator param2ValueAnimator) {
                float f = param2ValueAnimator.getAnimatedFraction();
                TabLayout.SlidingTabStrip.this.setIndicatorPosition(AnimationUtils.lerp(startLeft, targetLeft, f), AnimationUtils.lerp(startRight, targetRight, f));
              }
            });
        valueAnimator1.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
              public void onAnimationEnd(Animator param2Animator) {
                TabLayout.SlidingTabStrip.this.mSelectedPosition = position;
                TabLayout.SlidingTabStrip.this.mSelectionOffset = 0.0F;
              }
            });
        valueAnimator1.start();
      } 
    }
    
    boolean childrenNeedLayout() {
      int i = getChildCount();
      for (byte b = 0; b < i; b++) {
        if (getChildAt(b).getWidth() <= 0)
          return true; 
      } 
      return false;
    }
    
    public void draw(Canvas param1Canvas) {
      super.draw(param1Canvas);
      int i = this.mIndicatorLeft;
      if (i >= 0 && this.mIndicatorRight > i)
        param1Canvas.drawRect(i, (getHeight() - this.mSelectedIndicatorHeight), this.mIndicatorRight, getHeight(), this.mSelectedIndicatorPaint); 
    }
    
    float getIndicatorPosition() {
      return this.mSelectedPosition + this.mSelectionOffset;
    }
    
    protected void onLayout(boolean param1Boolean, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      super.onLayout(param1Boolean, param1Int1, param1Int2, param1Int3, param1Int4);
      ValueAnimator valueAnimator = this.mIndicatorAnimator;
      if (valueAnimator != null && valueAnimator.isRunning()) {
        this.mIndicatorAnimator.cancel();
        long l = this.mIndicatorAnimator.getDuration();
        animateIndicatorToPosition(this.mSelectedPosition, Math.round((1.0F - this.mIndicatorAnimator.getAnimatedFraction()) * (float)l));
      } else {
        updateIndicatorPosition();
      } 
    }
    
    protected void onMeasure(int param1Int1, int param1Int2) {
      super.onMeasure(param1Int1, param1Int2);
      if (View.MeasureSpec.getMode(param1Int1) != 1073741824)
        return; 
      int i = TabLayout.this.mMode;
      boolean bool = true;
      if (i == 1 && TabLayout.this.mTabGravity == 1) {
        int j = getChildCount();
        byte b = 0;
        i = 0;
        int k;
        for (k = 0; i < j; k = m) {
          View view = getChildAt(i);
          int m = k;
          if (view.getVisibility() == 0)
            m = Math.max(k, view.getMeasuredWidth()); 
          i++;
        } 
        if (k <= 0)
          return; 
        i = TabLayout.this.dpToPx(16);
        if (k * j <= getMeasuredWidth() - i * 2) {
          i = 0;
          for (byte b1 = b; b1 < j; b1++) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)getChildAt(b1).getLayoutParams();
            if (layoutParams.width != k || layoutParams.weight != 0.0F) {
              layoutParams.width = k;
              layoutParams.weight = 0.0F;
              i = 1;
            } 
          } 
        } else {
          TabLayout.this.mTabGravity = 0;
          TabLayout.this.updateTabViews(false);
          i = bool;
        } 
        if (i != 0)
          super.onMeasure(param1Int1, param1Int2); 
      } 
    }
    
    public void onRtlPropertiesChanged(int param1Int) {
      super.onRtlPropertiesChanged(param1Int);
      if (Build.VERSION.SDK_INT < 23 && this.mLayoutDirection != param1Int) {
        requestLayout();
        this.mLayoutDirection = param1Int;
      } 
    }
    
    void setIndicatorPosition(int param1Int1, int param1Int2) {
      if (param1Int1 != this.mIndicatorLeft || param1Int2 != this.mIndicatorRight) {
        this.mIndicatorLeft = param1Int1;
        this.mIndicatorRight = param1Int2;
        ViewCompat.postInvalidateOnAnimation((View)this);
      } 
    }
    
    void setIndicatorPositionFromTabPosition(int param1Int, float param1Float) {
      ValueAnimator valueAnimator = this.mIndicatorAnimator;
      if (valueAnimator != null && valueAnimator.isRunning())
        this.mIndicatorAnimator.cancel(); 
      this.mSelectedPosition = param1Int;
      this.mSelectionOffset = param1Float;
      updateIndicatorPosition();
    }
    
    void setSelectedIndicatorColor(int param1Int) {
      if (this.mSelectedIndicatorPaint.getColor() != param1Int) {
        this.mSelectedIndicatorPaint.setColor(param1Int);
        ViewCompat.postInvalidateOnAnimation((View)this);
      } 
    }
    
    void setSelectedIndicatorHeight(int param1Int) {
      if (this.mSelectedIndicatorHeight != param1Int) {
        this.mSelectedIndicatorHeight = param1Int;
        ViewCompat.postInvalidateOnAnimation((View)this);
      } 
    }
  }
  
  class null implements ValueAnimator.AnimatorUpdateListener {
    public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
      float f = param1ValueAnimator.getAnimatedFraction();
      this.this$1.setIndicatorPosition(AnimationUtils.lerp(startLeft, targetLeft, f), AnimationUtils.lerp(startRight, targetRight, f));
    }
  }
  
  class null extends AnimatorListenerAdapter {
    public void onAnimationEnd(Animator param1Animator) {
      this.this$1.mSelectedPosition = position;
      this.this$1.mSelectionOffset = 0.0F;
    }
  }
  
  public static final class Tab {
    public static final int INVALID_POSITION = -1;
    
    private CharSequence mContentDesc;
    
    private View mCustomView;
    
    private Drawable mIcon;
    
    TabLayout mParent;
    
    private int mPosition = -1;
    
    private Object mTag;
    
    private CharSequence mText;
    
    TabLayout.TabView mView;
    
    public CharSequence getContentDescription() {
      return this.mContentDesc;
    }
    
    public View getCustomView() {
      return this.mCustomView;
    }
    
    public Drawable getIcon() {
      return this.mIcon;
    }
    
    public int getPosition() {
      return this.mPosition;
    }
    
    public Object getTag() {
      return this.mTag;
    }
    
    public CharSequence getText() {
      return this.mText;
    }
    
    public boolean isSelected() {
      TabLayout tabLayout = this.mParent;
      if (tabLayout != null) {
        boolean bool;
        if (tabLayout.getSelectedTabPosition() == this.mPosition) {
          bool = true;
        } else {
          bool = false;
        } 
        return bool;
      } 
      throw new IllegalArgumentException("Tab not attached to a TabLayout");
    }
    
    void reset() {
      this.mParent = null;
      this.mView = null;
      this.mTag = null;
      this.mIcon = null;
      this.mText = null;
      this.mContentDesc = null;
      this.mPosition = -1;
      this.mCustomView = null;
    }
    
    public void select() {
      TabLayout tabLayout = this.mParent;
      if (tabLayout != null) {
        tabLayout.selectTab(this);
        return;
      } 
      throw new IllegalArgumentException("Tab not attached to a TabLayout");
    }
    
    public Tab setContentDescription(int param1Int) {
      TabLayout tabLayout = this.mParent;
      if (tabLayout != null)
        return setContentDescription(tabLayout.getResources().getText(param1Int)); 
      throw new IllegalArgumentException("Tab not attached to a TabLayout");
    }
    
    public Tab setContentDescription(CharSequence param1CharSequence) {
      this.mContentDesc = param1CharSequence;
      updateView();
      return this;
    }
    
    public Tab setCustomView(int param1Int) {
      return setCustomView(LayoutInflater.from(this.mView.getContext()).inflate(param1Int, (ViewGroup)this.mView, false));
    }
    
    public Tab setCustomView(View param1View) {
      this.mCustomView = param1View;
      updateView();
      return this;
    }
    
    public Tab setIcon(int param1Int) {
      TabLayout tabLayout = this.mParent;
      if (tabLayout != null)
        return setIcon(AppCompatResources.getDrawable(tabLayout.getContext(), param1Int)); 
      throw new IllegalArgumentException("Tab not attached to a TabLayout");
    }
    
    public Tab setIcon(Drawable param1Drawable) {
      this.mIcon = param1Drawable;
      updateView();
      return this;
    }
    
    void setPosition(int param1Int) {
      this.mPosition = param1Int;
    }
    
    public Tab setTag(Object param1Object) {
      this.mTag = param1Object;
      return this;
    }
    
    public Tab setText(int param1Int) {
      TabLayout tabLayout = this.mParent;
      if (tabLayout != null)
        return setText(tabLayout.getResources().getText(param1Int)); 
      throw new IllegalArgumentException("Tab not attached to a TabLayout");
    }
    
    public Tab setText(CharSequence param1CharSequence) {
      this.mText = param1CharSequence;
      updateView();
      return this;
    }
    
    void updateView() {
      TabLayout.TabView tabView = this.mView;
      if (tabView != null)
        tabView.update(); 
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface TabGravity {}
  
  public static class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
    private int mPreviousScrollState;
    
    private int mScrollState;
    
    private final WeakReference<TabLayout> mTabLayoutRef;
    
    public TabLayoutOnPageChangeListener(TabLayout param1TabLayout) {
      this.mTabLayoutRef = new WeakReference<TabLayout>(param1TabLayout);
    }
    
    public void onPageScrollStateChanged(int param1Int) {
      this.mPreviousScrollState = this.mScrollState;
      this.mScrollState = param1Int;
    }
    
    public void onPageScrolled(int param1Int1, float param1Float, int param1Int2) {
      TabLayout tabLayout = this.mTabLayoutRef.get();
      if (tabLayout != null) {
        boolean bool2;
        param1Int2 = this.mScrollState;
        boolean bool1 = false;
        if (param1Int2 != 2 || this.mPreviousScrollState == 1) {
          bool2 = true;
        } else {
          bool2 = false;
        } 
        if (this.mScrollState != 2 || this.mPreviousScrollState != 0)
          bool1 = true; 
        tabLayout.setScrollPosition(param1Int1, param1Float, bool2, bool1);
      } 
    }
    
    public void onPageSelected(int param1Int) {
      TabLayout tabLayout = this.mTabLayoutRef.get();
      if (tabLayout != null && tabLayout.getSelectedTabPosition() != param1Int && param1Int < tabLayout.getTabCount()) {
        boolean bool;
        int i = this.mScrollState;
        if (i == 0 || (i == 2 && this.mPreviousScrollState == 0)) {
          bool = true;
        } else {
          bool = false;
        } 
        tabLayout.selectTab(tabLayout.getTabAt(param1Int), bool);
      } 
    }
    
    void reset() {
      this.mScrollState = 0;
      this.mPreviousScrollState = 0;
    }
  }
  
  class TabView extends LinearLayout {
    private ImageView mCustomIconView;
    
    private TextView mCustomTextView;
    
    private View mCustomView;
    
    private int mDefaultMaxLines = 2;
    
    private ImageView mIconView;
    
    private TabLayout.Tab mTab;
    
    private TextView mTextView;
    
    public TabView(Context param1Context) {
      super(param1Context);
      if (TabLayout.this.mTabBackgroundResId != 0)
        ViewCompat.setBackground((View)this, AppCompatResources.getDrawable(param1Context, TabLayout.this.mTabBackgroundResId)); 
      ViewCompat.setPaddingRelative((View)this, TabLayout.this.mTabPaddingStart, TabLayout.this.mTabPaddingTop, TabLayout.this.mTabPaddingEnd, TabLayout.this.mTabPaddingBottom);
      setGravity(17);
      setOrientation(1);
      setClickable(true);
      ViewCompat.setPointerIcon((View)this, PointerIconCompat.getSystemIcon(getContext(), 1002));
    }
    
    private float approximateLineWidth(Layout param1Layout, int param1Int, float param1Float) {
      return param1Layout.getLineWidth(param1Int) * param1Float / param1Layout.getPaint().getTextSize();
    }
    
    private void updateTextAndIcon(TextView param1TextView, ImageView param1ImageView) {
      TabLayout.Tab tab1;
      Drawable drawable;
      CharSequence charSequence;
      TabLayout.Tab tab2 = this.mTab;
      TextView textView = null;
      if (tab2 != null) {
        drawable = tab2.getIcon();
      } else {
        drawable = null;
      } 
      tab2 = this.mTab;
      if (tab2 != null) {
        charSequence = tab2.getText();
      } else {
        charSequence = null;
      } 
      tab2 = this.mTab;
      if (tab2 != null) {
        CharSequence charSequence1 = tab2.getContentDescription();
      } else {
        tab2 = null;
      } 
      byte b = 0;
      if (param1ImageView != null) {
        if (drawable != null) {
          param1ImageView.setImageDrawable(drawable);
          param1ImageView.setVisibility(0);
          setVisibility(0);
        } else {
          param1ImageView.setVisibility(8);
          param1ImageView.setImageDrawable(null);
        } 
        param1ImageView.setContentDescription((CharSequence)tab2);
      } 
      int i = TextUtils.isEmpty(charSequence) ^ true;
      if (param1TextView != null) {
        if (i != 0) {
          param1TextView.setText(charSequence);
          param1TextView.setVisibility(0);
          setVisibility(0);
        } else {
          param1TextView.setVisibility(8);
          param1TextView.setText(null);
        } 
        param1TextView.setContentDescription((CharSequence)tab2);
      } 
      if (param1ImageView != null) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)param1ImageView.getLayoutParams();
        int j = b;
        if (i != 0) {
          j = b;
          if (param1ImageView.getVisibility() == 0)
            j = TabLayout.this.dpToPx(8); 
        } 
        if (j != marginLayoutParams.bottomMargin) {
          marginLayoutParams.bottomMargin = j;
          param1ImageView.requestLayout();
        } 
      } 
      if (i != 0) {
        param1TextView = textView;
      } else {
        tab1 = tab2;
      } 
      TooltipCompat.setTooltipText((View)this, (CharSequence)tab1);
    }
    
    public TabLayout.Tab getTab() {
      return this.mTab;
    }
    
    public void onInitializeAccessibilityEvent(AccessibilityEvent param1AccessibilityEvent) {
      super.onInitializeAccessibilityEvent(param1AccessibilityEvent);
      param1AccessibilityEvent.setClassName(ActionBar.Tab.class.getName());
    }
    
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo param1AccessibilityNodeInfo) {
      super.onInitializeAccessibilityNodeInfo(param1AccessibilityNodeInfo);
      param1AccessibilityNodeInfo.setClassName(ActionBar.Tab.class.getName());
    }
    
    public void onMeasure(int param1Int1, int param1Int2) {
      // Byte code:
      //   0: iload_1
      //   1: invokestatic getSize : (I)I
      //   4: istore_3
      //   5: iload_1
      //   6: invokestatic getMode : (I)I
      //   9: istore #4
      //   11: aload_0
      //   12: getfield this$0 : Landroid/support/design/widget/TabLayout;
      //   15: invokevirtual getTabMaxWidth : ()I
      //   18: istore #5
      //   20: iload_1
      //   21: istore #6
      //   23: iload #5
      //   25: ifle -> 56
      //   28: iload #4
      //   30: ifeq -> 42
      //   33: iload_1
      //   34: istore #6
      //   36: iload_3
      //   37: iload #5
      //   39: if_icmple -> 56
      //   42: aload_0
      //   43: getfield this$0 : Landroid/support/design/widget/TabLayout;
      //   46: getfield mTabMaxWidth : I
      //   49: ldc -2147483648
      //   51: invokestatic makeMeasureSpec : (II)I
      //   54: istore #6
      //   56: aload_0
      //   57: iload #6
      //   59: iload_2
      //   60: invokespecial onMeasure : (II)V
      //   63: aload_0
      //   64: getfield mTextView : Landroid/widget/TextView;
      //   67: ifnull -> 328
      //   70: aload_0
      //   71: invokevirtual getResources : ()Landroid/content/res/Resources;
      //   74: pop
      //   75: aload_0
      //   76: getfield this$0 : Landroid/support/design/widget/TabLayout;
      //   79: getfield mTabTextSize : F
      //   82: fstore #7
      //   84: aload_0
      //   85: getfield mDefaultMaxLines : I
      //   88: istore #4
      //   90: aload_0
      //   91: getfield mIconView : Landroid/widget/ImageView;
      //   94: astore #8
      //   96: iconst_1
      //   97: istore #5
      //   99: aload #8
      //   101: ifnull -> 121
      //   104: aload #8
      //   106: invokevirtual getVisibility : ()I
      //   109: ifne -> 121
      //   112: iconst_1
      //   113: istore_1
      //   114: fload #7
      //   116: fstore #9
      //   118: goto -> 167
      //   121: aload_0
      //   122: getfield mTextView : Landroid/widget/TextView;
      //   125: astore #8
      //   127: fload #7
      //   129: fstore #9
      //   131: iload #4
      //   133: istore_1
      //   134: aload #8
      //   136: ifnull -> 167
      //   139: fload #7
      //   141: fstore #9
      //   143: iload #4
      //   145: istore_1
      //   146: aload #8
      //   148: invokevirtual getLineCount : ()I
      //   151: iconst_1
      //   152: if_icmple -> 167
      //   155: aload_0
      //   156: getfield this$0 : Landroid/support/design/widget/TabLayout;
      //   159: getfield mTabTextMultiLineSize : F
      //   162: fstore #9
      //   164: iload #4
      //   166: istore_1
      //   167: aload_0
      //   168: getfield mTextView : Landroid/widget/TextView;
      //   171: invokevirtual getTextSize : ()F
      //   174: fstore #7
      //   176: aload_0
      //   177: getfield mTextView : Landroid/widget/TextView;
      //   180: invokevirtual getLineCount : ()I
      //   183: istore_3
      //   184: aload_0
      //   185: getfield mTextView : Landroid/widget/TextView;
      //   188: invokestatic getMaxLines : (Landroid/widget/TextView;)I
      //   191: istore #4
      //   193: fload #9
      //   195: fload #7
      //   197: fcmpl
      //   198: istore #10
      //   200: iload #10
      //   202: ifne -> 216
      //   205: iload #4
      //   207: iflt -> 328
      //   210: iload_1
      //   211: iload #4
      //   213: if_icmpeq -> 328
      //   216: iload #5
      //   218: istore #4
      //   220: aload_0
      //   221: getfield this$0 : Landroid/support/design/widget/TabLayout;
      //   224: getfield mMode : I
      //   227: iconst_1
      //   228: if_icmpne -> 298
      //   231: iload #5
      //   233: istore #4
      //   235: iload #10
      //   237: ifle -> 298
      //   240: iload #5
      //   242: istore #4
      //   244: iload_3
      //   245: iconst_1
      //   246: if_icmpne -> 298
      //   249: aload_0
      //   250: getfield mTextView : Landroid/widget/TextView;
      //   253: invokevirtual getLayout : ()Landroid/text/Layout;
      //   256: astore #8
      //   258: aload #8
      //   260: ifnull -> 295
      //   263: iload #5
      //   265: istore #4
      //   267: aload_0
      //   268: aload #8
      //   270: iconst_0
      //   271: fload #9
      //   273: invokespecial approximateLineWidth : (Landroid/text/Layout;IF)F
      //   276: aload_0
      //   277: invokevirtual getMeasuredWidth : ()I
      //   280: aload_0
      //   281: invokevirtual getPaddingLeft : ()I
      //   284: isub
      //   285: aload_0
      //   286: invokevirtual getPaddingRight : ()I
      //   289: isub
      //   290: i2f
      //   291: fcmpl
      //   292: ifle -> 298
      //   295: iconst_0
      //   296: istore #4
      //   298: iload #4
      //   300: ifeq -> 328
      //   303: aload_0
      //   304: getfield mTextView : Landroid/widget/TextView;
      //   307: iconst_0
      //   308: fload #9
      //   310: invokevirtual setTextSize : (IF)V
      //   313: aload_0
      //   314: getfield mTextView : Landroid/widget/TextView;
      //   317: iload_1
      //   318: invokevirtual setMaxLines : (I)V
      //   321: aload_0
      //   322: iload #6
      //   324: iload_2
      //   325: invokespecial onMeasure : (II)V
      //   328: return
    }
    
    public boolean performClick() {
      boolean bool1 = super.performClick();
      boolean bool2 = bool1;
      if (this.mTab != null) {
        if (!bool1)
          playSoundEffect(0); 
        this.mTab.select();
        bool2 = true;
      } 
      return bool2;
    }
    
    void reset() {
      setTab((TabLayout.Tab)null);
      setSelected(false);
    }
    
    public void setSelected(boolean param1Boolean) {
      boolean bool;
      if (isSelected() != param1Boolean) {
        bool = true;
      } else {
        bool = false;
      } 
      super.setSelected(param1Boolean);
      if (bool && param1Boolean && Build.VERSION.SDK_INT < 16)
        sendAccessibilityEvent(4); 
      TextView textView = this.mTextView;
      if (textView != null)
        textView.setSelected(param1Boolean); 
      ImageView imageView = this.mIconView;
      if (imageView != null)
        imageView.setSelected(param1Boolean); 
      View view = this.mCustomView;
      if (view != null)
        view.setSelected(param1Boolean); 
    }
    
    void setTab(TabLayout.Tab param1Tab) {
      if (param1Tab != this.mTab) {
        this.mTab = param1Tab;
        update();
      } 
    }
    
    final void update() {
      TabLayout.Tab tab = this.mTab;
      if (tab != null) {
        view = tab.getCustomView();
      } else {
        view = null;
      } 
      if (view != null) {
        ViewParent viewParent = view.getParent();
        if (viewParent != this) {
          if (viewParent != null)
            ((ViewGroup)viewParent).removeView(view); 
          addView(view);
        } 
        this.mCustomView = view;
        TextView textView2 = this.mTextView;
        if (textView2 != null)
          textView2.setVisibility(8); 
        ImageView imageView = this.mIconView;
        if (imageView != null) {
          imageView.setVisibility(8);
          this.mIconView.setImageDrawable(null);
        } 
        TextView textView1 = (TextView)view.findViewById(16908308);
        this.mCustomTextView = textView1;
        if (textView1 != null)
          this.mDefaultMaxLines = TextViewCompat.getMaxLines(textView1); 
        this.mCustomIconView = (ImageView)view.findViewById(16908294);
      } else {
        view = this.mCustomView;
        if (view != null) {
          removeView(view);
          this.mCustomView = null;
        } 
        this.mCustomTextView = null;
        this.mCustomIconView = null;
      } 
      View view = this.mCustomView;
      boolean bool1 = false;
      if (view == null) {
        if (this.mIconView == null) {
          ImageView imageView = (ImageView)LayoutInflater.from(getContext()).inflate(R.layout.design_layout_tab_icon, (ViewGroup)this, false);
          addView((View)imageView, 0);
          this.mIconView = imageView;
        } 
        if (this.mTextView == null) {
          TextView textView = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.design_layout_tab_text, (ViewGroup)this, false);
          addView((View)textView);
          this.mTextView = textView;
          this.mDefaultMaxLines = TextViewCompat.getMaxLines(textView);
        } 
        TextViewCompat.setTextAppearance(this.mTextView, TabLayout.this.mTabTextAppearance);
        if (TabLayout.this.mTabTextColors != null)
          this.mTextView.setTextColor(TabLayout.this.mTabTextColors); 
        updateTextAndIcon(this.mTextView, this.mIconView);
      } else if (this.mCustomTextView != null || this.mCustomIconView != null) {
        updateTextAndIcon(this.mCustomTextView, this.mCustomIconView);
      } 
      boolean bool2 = bool1;
      if (tab != null) {
        bool2 = bool1;
        if (tab.isSelected())
          bool2 = true; 
      } 
      setSelected(bool2);
    }
  }
  
  public static class ViewPagerOnTabSelectedListener implements OnTabSelectedListener {
    private final ViewPager mViewPager;
    
    public ViewPagerOnTabSelectedListener(ViewPager param1ViewPager) {
      this.mViewPager = param1ViewPager;
    }
    
    public void onTabReselected(TabLayout.Tab param1Tab) {}
    
    public void onTabSelected(TabLayout.Tab param1Tab) {
      this.mViewPager.setCurrentItem(param1Tab.getPosition());
    }
    
    public void onTabUnselected(TabLayout.Tab param1Tab) {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\TabLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */