package android.support.v4.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewPager extends ViewGroup {
  private static final int CLOSE_ENOUGH = 2;
  
  private static final Comparator<ItemInfo> COMPARATOR;
  
  private static final boolean DEBUG = false;
  
  private static final int DEFAULT_GUTTER_SIZE = 16;
  
  private static final int DEFAULT_OFFSCREEN_PAGES = 1;
  
  private static final int DRAW_ORDER_DEFAULT = 0;
  
  private static final int DRAW_ORDER_FORWARD = 1;
  
  private static final int DRAW_ORDER_REVERSE = 2;
  
  private static final int INVALID_POINTER = -1;
  
  static final int[] LAYOUT_ATTRS = new int[] { 16842931 };
  
  private static final int MAX_SETTLE_DURATION = 600;
  
  private static final int MIN_DISTANCE_FOR_FLING = 25;
  
  private static final int MIN_FLING_VELOCITY = 400;
  
  public static final int SCROLL_STATE_DRAGGING = 1;
  
  public static final int SCROLL_STATE_IDLE = 0;
  
  public static final int SCROLL_STATE_SETTLING = 2;
  
  private static final String TAG = "ViewPager";
  
  private static final boolean USE_CACHE = false;
  
  private static final Interpolator sInterpolator;
  
  private static final ViewPositionComparator sPositionComparator;
  
  private int mActivePointerId = -1;
  
  PagerAdapter mAdapter;
  
  private List<OnAdapterChangeListener> mAdapterChangeListeners;
  
  private int mBottomPageBounds;
  
  private boolean mCalledSuper;
  
  private int mChildHeightMeasureSpec;
  
  private int mChildWidthMeasureSpec;
  
  private int mCloseEnough;
  
  int mCurItem;
  
  private int mDecorChildCount;
  
  private int mDefaultGutterSize;
  
  private int mDrawingOrder;
  
  private ArrayList<View> mDrawingOrderedChildren;
  
  private final Runnable mEndScrollRunnable = new Runnable() {
      public void run() {
        ViewPager.this.setScrollState(0);
        ViewPager.this.populate();
      }
    };
  
  private int mExpectedAdapterCount;
  
  private long mFakeDragBeginTime;
  
  private boolean mFakeDragging;
  
  private boolean mFirstLayout = true;
  
  private float mFirstOffset = -3.4028235E38F;
  
  private int mFlingDistance;
  
  private int mGutterSize;
  
  private boolean mInLayout;
  
  private float mInitialMotionX;
  
  private float mInitialMotionY;
  
  private OnPageChangeListener mInternalPageChangeListener;
  
  private boolean mIsBeingDragged;
  
  private boolean mIsScrollStarted;
  
  private boolean mIsUnableToDrag;
  
  private final ArrayList<ItemInfo> mItems = new ArrayList<ItemInfo>();
  
  private float mLastMotionX;
  
  private float mLastMotionY;
  
  private float mLastOffset = Float.MAX_VALUE;
  
  private EdgeEffect mLeftEdge;
  
  private Drawable mMarginDrawable;
  
  private int mMaximumVelocity;
  
  private int mMinimumVelocity;
  
  private boolean mNeedCalculatePageOffsets = false;
  
  private PagerObserver mObserver;
  
  private int mOffscreenPageLimit = 1;
  
  private OnPageChangeListener mOnPageChangeListener;
  
  private List<OnPageChangeListener> mOnPageChangeListeners;
  
  private int mPageMargin;
  
  private PageTransformer mPageTransformer;
  
  private int mPageTransformerLayerType;
  
  private boolean mPopulatePending;
  
  private Parcelable mRestoredAdapterState = null;
  
  private ClassLoader mRestoredClassLoader = null;
  
  private int mRestoredCurItem = -1;
  
  private EdgeEffect mRightEdge;
  
  private int mScrollState = 0;
  
  private Scroller mScroller;
  
  private boolean mScrollingCacheEnabled;
  
  private final ItemInfo mTempItem = new ItemInfo();
  
  private final Rect mTempRect = new Rect();
  
  private int mTopPageBounds;
  
  private int mTouchSlop;
  
  private VelocityTracker mVelocityTracker;
  
  static {
    COMPARATOR = new Comparator<ItemInfo>() {
        public int compare(ViewPager.ItemInfo param1ItemInfo1, ViewPager.ItemInfo param1ItemInfo2) {
          return param1ItemInfo1.position - param1ItemInfo2.position;
        }
      };
    sInterpolator = new Interpolator() {
        public float getInterpolation(float param1Float) {
          param1Float--;
          return param1Float * param1Float * param1Float * param1Float * param1Float + 1.0F;
        }
      };
    sPositionComparator = new ViewPositionComparator();
  }
  
  public ViewPager(Context paramContext) {
    super(paramContext);
    initViewPager();
  }
  
  public ViewPager(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    initViewPager();
  }
  
  private void calculatePageOffsets(ItemInfo paramItemInfo1, int paramInt, ItemInfo paramItemInfo2) {
    float f1;
    int i = this.mAdapter.getCount();
    int j = getClientWidth();
    if (j > 0) {
      f1 = this.mPageMargin / j;
    } else {
      f1 = 0.0F;
    } 
    if (paramItemInfo2 != null) {
      j = paramItemInfo2.position;
      if (j < paramItemInfo1.position) {
        f2 = paramItemInfo2.offset + paramItemInfo2.widthFactor + f1;
        j++;
        byte b = 0;
        while (j <= paramItemInfo1.position && b < this.mItems.size()) {
          int n;
          float f;
          paramItemInfo2 = this.mItems.get(b);
          while (true) {
            n = j;
            f = f2;
            if (j > paramItemInfo2.position) {
              n = j;
              f = f2;
              if (b < this.mItems.size() - 1) {
                paramItemInfo2 = this.mItems.get(++b);
                continue;
              } 
            } 
            break;
          } 
          while (n < paramItemInfo2.position) {
            f += this.mAdapter.getPageWidth(n) + f1;
            n++;
          } 
          paramItemInfo2.offset = f;
          f2 = f + paramItemInfo2.widthFactor + f1;
          j = n + 1;
        } 
      } else if (j > paramItemInfo1.position) {
        int n = this.mItems.size() - 1;
        f2 = paramItemInfo2.offset;
        while (--j >= paramItemInfo1.position && n >= 0) {
          int i1;
          float f;
          paramItemInfo2 = this.mItems.get(n);
          while (true) {
            i1 = j;
            f = f2;
            if (j < paramItemInfo2.position) {
              i1 = j;
              f = f2;
              if (n > 0) {
                paramItemInfo2 = this.mItems.get(--n);
                continue;
              } 
            } 
            break;
          } 
          while (i1 > paramItemInfo2.position) {
            f -= this.mAdapter.getPageWidth(i1) + f1;
            i1--;
          } 
          f2 = f - paramItemInfo2.widthFactor + f1;
          paramItemInfo2.offset = f2;
          j = i1 - 1;
        } 
      } 
    } 
    int m = this.mItems.size();
    float f3 = paramItemInfo1.offset;
    j = paramItemInfo1.position - 1;
    if (paramItemInfo1.position == 0) {
      f2 = paramItemInfo1.offset;
    } else {
      f2 = -3.4028235E38F;
    } 
    this.mFirstOffset = f2;
    int k = paramItemInfo1.position;
    if (k == --i) {
      f2 = paramItemInfo1.offset + paramItemInfo1.widthFactor - 1.0F;
    } else {
      f2 = Float.MAX_VALUE;
    } 
    this.mLastOffset = f2;
    k = paramInt - 1;
    float f2 = f3;
    while (k >= 0) {
      paramItemInfo2 = this.mItems.get(k);
      while (j > paramItemInfo2.position) {
        f2 -= this.mAdapter.getPageWidth(j) + f1;
        j--;
      } 
      f2 -= paramItemInfo2.widthFactor + f1;
      paramItemInfo2.offset = f2;
      if (paramItemInfo2.position == 0)
        this.mFirstOffset = f2; 
      k--;
      j--;
    } 
    f2 = paramItemInfo1.offset + paramItemInfo1.widthFactor + f1;
    k = paramItemInfo1.position + 1;
    j = paramInt + 1;
    for (paramInt = k; j < m; paramInt++) {
      paramItemInfo1 = this.mItems.get(j);
      while (paramInt < paramItemInfo1.position) {
        f2 += this.mAdapter.getPageWidth(paramInt) + f1;
        paramInt++;
      } 
      if (paramItemInfo1.position == i)
        this.mLastOffset = paramItemInfo1.widthFactor + f2 - 1.0F; 
      paramItemInfo1.offset = f2;
      f2 += paramItemInfo1.widthFactor + f1;
      j++;
    } 
    this.mNeedCalculatePageOffsets = false;
  }
  
  private void completeScroll(boolean paramBoolean) {
    boolean bool;
    if (this.mScrollState == 2) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool) {
      setScrollingCacheEnabled(false);
      if ((this.mScroller.isFinished() ^ true) != 0) {
        this.mScroller.abortAnimation();
        int i = getScrollX();
        int j = getScrollY();
        int k = this.mScroller.getCurrX();
        int m = this.mScroller.getCurrY();
        if (i != k || j != m) {
          scrollTo(k, m);
          if (k != i)
            pageScrolled(k); 
        } 
      } 
    } 
    this.mPopulatePending = false;
    for (byte b = 0; b < this.mItems.size(); b++) {
      ItemInfo itemInfo = this.mItems.get(b);
      if (itemInfo.scrolling) {
        itemInfo.scrolling = false;
        bool = true;
      } 
    } 
    if (bool)
      if (paramBoolean) {
        ViewCompat.postOnAnimation((View)this, this.mEndScrollRunnable);
      } else {
        this.mEndScrollRunnable.run();
      }  
  }
  
  private int determineTargetPage(int paramInt1, float paramFloat, int paramInt2, int paramInt3) {
    if (Math.abs(paramInt3) > this.mFlingDistance && Math.abs(paramInt2) > this.mMinimumVelocity) {
      if (paramInt2 <= 0)
        paramInt1++; 
    } else {
      float f;
      if (paramInt1 >= this.mCurItem) {
        f = 0.4F;
      } else {
        f = 0.6F;
      } 
      paramInt1 += (int)(paramFloat + f);
    } 
    paramInt2 = paramInt1;
    if (this.mItems.size() > 0) {
      ItemInfo itemInfo1 = this.mItems.get(0);
      ArrayList<ItemInfo> arrayList = this.mItems;
      ItemInfo itemInfo2 = arrayList.get(arrayList.size() - 1);
      paramInt2 = Math.max(itemInfo1.position, Math.min(paramInt1, itemInfo2.position));
    } 
    return paramInt2;
  }
  
  private void dispatchOnPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
    OnPageChangeListener onPageChangeListener2 = this.mOnPageChangeListener;
    if (onPageChangeListener2 != null)
      onPageChangeListener2.onPageScrolled(paramInt1, paramFloat, paramInt2); 
    List<OnPageChangeListener> list = this.mOnPageChangeListeners;
    if (list != null) {
      byte b = 0;
      int i = list.size();
      while (b < i) {
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListeners.get(b);
        if (onPageChangeListener != null)
          onPageChangeListener.onPageScrolled(paramInt1, paramFloat, paramInt2); 
        b++;
      } 
    } 
    OnPageChangeListener onPageChangeListener1 = this.mInternalPageChangeListener;
    if (onPageChangeListener1 != null)
      onPageChangeListener1.onPageScrolled(paramInt1, paramFloat, paramInt2); 
  }
  
  private void dispatchOnPageSelected(int paramInt) {
    OnPageChangeListener onPageChangeListener2 = this.mOnPageChangeListener;
    if (onPageChangeListener2 != null)
      onPageChangeListener2.onPageSelected(paramInt); 
    List<OnPageChangeListener> list = this.mOnPageChangeListeners;
    if (list != null) {
      byte b = 0;
      int i = list.size();
      while (b < i) {
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListeners.get(b);
        if (onPageChangeListener != null)
          onPageChangeListener.onPageSelected(paramInt); 
        b++;
      } 
    } 
    OnPageChangeListener onPageChangeListener1 = this.mInternalPageChangeListener;
    if (onPageChangeListener1 != null)
      onPageChangeListener1.onPageSelected(paramInt); 
  }
  
  private void dispatchOnScrollStateChanged(int paramInt) {
    OnPageChangeListener onPageChangeListener2 = this.mOnPageChangeListener;
    if (onPageChangeListener2 != null)
      onPageChangeListener2.onPageScrollStateChanged(paramInt); 
    List<OnPageChangeListener> list = this.mOnPageChangeListeners;
    if (list != null) {
      byte b = 0;
      int i = list.size();
      while (b < i) {
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListeners.get(b);
        if (onPageChangeListener != null)
          onPageChangeListener.onPageScrollStateChanged(paramInt); 
        b++;
      } 
    } 
    OnPageChangeListener onPageChangeListener1 = this.mInternalPageChangeListener;
    if (onPageChangeListener1 != null)
      onPageChangeListener1.onPageScrollStateChanged(paramInt); 
  }
  
  private void enableLayers(boolean paramBoolean) {
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      boolean bool;
      if (paramBoolean) {
        bool = this.mPageTransformerLayerType;
      } else {
        bool = false;
      } 
      getChildAt(b).setLayerType(bool, null);
    } 
  }
  
  private void endDrag() {
    this.mIsBeingDragged = false;
    this.mIsUnableToDrag = false;
    VelocityTracker velocityTracker = this.mVelocityTracker;
    if (velocityTracker != null) {
      velocityTracker.recycle();
      this.mVelocityTracker = null;
    } 
  }
  
  private Rect getChildRectInPagerCoordinates(Rect paramRect, View paramView) {
    Rect rect = paramRect;
    if (paramRect == null)
      rect = new Rect(); 
    if (paramView == null) {
      rect.set(0, 0, 0, 0);
      return rect;
    } 
    rect.left = paramView.getLeft();
    rect.right = paramView.getRight();
    rect.top = paramView.getTop();
    rect.bottom = paramView.getBottom();
    ViewParent viewParent = paramView.getParent();
    while (viewParent instanceof ViewGroup && viewParent != this) {
      ViewGroup viewGroup = (ViewGroup)viewParent;
      rect.left += viewGroup.getLeft();
      rect.right += viewGroup.getRight();
      rect.top += viewGroup.getTop();
      rect.bottom += viewGroup.getBottom();
      ViewParent viewParent1 = viewGroup.getParent();
    } 
    return rect;
  }
  
  private int getClientWidth() {
    return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
  }
  
  private ItemInfo infoForCurrentScrollPosition() {
    float f2;
    float f3;
    int i = getClientWidth();
    float f1 = 0.0F;
    if (i > 0) {
      f2 = getScrollX() / i;
    } else {
      f2 = 0.0F;
    } 
    if (i > 0) {
      f3 = this.mPageMargin / i;
    } else {
      f3 = 0.0F;
    } 
    ItemInfo itemInfo = null;
    float f4 = 0.0F;
    int j = -1;
    i = 0;
    boolean bool = true;
    while (i < this.mItems.size()) {
      ItemInfo itemInfo1 = this.mItems.get(i);
      int k = i;
      ItemInfo itemInfo2 = itemInfo1;
      if (!bool) {
        int m = itemInfo1.position;
        j++;
        k = i;
        itemInfo2 = itemInfo1;
        if (m != j) {
          itemInfo2 = this.mTempItem;
          itemInfo2.offset = f1 + f4 + f3;
          itemInfo2.position = j;
          itemInfo2.widthFactor = this.mAdapter.getPageWidth(itemInfo2.position);
          k = i - 1;
        } 
      } 
      f1 = itemInfo2.offset;
      f4 = itemInfo2.widthFactor;
      if (bool || f2 >= f1) {
        if (f2 < f4 + f1 + f3 || k == this.mItems.size() - 1)
          return itemInfo2; 
        j = itemInfo2.position;
        f4 = itemInfo2.widthFactor;
        i = k + 1;
        bool = false;
        itemInfo = itemInfo2;
        continue;
      } 
      return itemInfo;
    } 
    return itemInfo;
  }
  
  private static boolean isDecorView(View paramView) {
    boolean bool;
    if (paramView.getClass().getAnnotation(DecorView.class) != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private boolean isGutterDrag(float paramFloat1, float paramFloat2) {
    boolean bool;
    if ((paramFloat1 < this.mGutterSize && paramFloat2 > 0.0F) || (paramFloat1 > (getWidth() - this.mGutterSize) && paramFloat2 < 0.0F)) {
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
      this.mLastMotionX = paramMotionEvent.getX(i);
      this.mActivePointerId = paramMotionEvent.getPointerId(i);
      VelocityTracker velocityTracker = this.mVelocityTracker;
      if (velocityTracker != null)
        velocityTracker.clear(); 
    } 
  }
  
  private boolean pageScrolled(int paramInt) {
    if (this.mItems.size() == 0) {
      if (this.mFirstLayout)
        return false; 
      this.mCalledSuper = false;
      onPageScrolled(0, 0.0F, 0);
      if (this.mCalledSuper)
        return false; 
      throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    } 
    ItemInfo itemInfo = infoForCurrentScrollPosition();
    int i = getClientWidth();
    int j = this.mPageMargin;
    float f1 = j;
    float f2 = i;
    f1 /= f2;
    int k = itemInfo.position;
    f2 = (paramInt / f2 - itemInfo.offset) / (itemInfo.widthFactor + f1);
    paramInt = (int)((i + j) * f2);
    this.mCalledSuper = false;
    onPageScrolled(k, f2, paramInt);
    if (this.mCalledSuper)
      return true; 
    throw new IllegalStateException("onPageScrolled did not call superclass implementation");
  }
  
  private boolean performDrag(float paramFloat) {
    boolean bool4;
    float f1 = this.mLastMotionX;
    this.mLastMotionX = paramFloat;
    float f2 = getScrollX() + f1 - paramFloat;
    float f3 = getClientWidth();
    paramFloat = this.mFirstOffset * f3;
    f1 = this.mLastOffset * f3;
    ArrayList<ItemInfo> arrayList1 = this.mItems;
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    ItemInfo itemInfo1 = arrayList1.get(0);
    ArrayList<ItemInfo> arrayList2 = this.mItems;
    ItemInfo itemInfo2 = arrayList2.get(arrayList2.size() - 1);
    if (itemInfo1.position != 0) {
      paramFloat = itemInfo1.offset * f3;
      i = 0;
    } else {
      i = 1;
    } 
    if (itemInfo2.position != this.mAdapter.getCount() - 1) {
      f1 = itemInfo2.offset * f3;
      bool4 = false;
    } else {
      bool4 = true;
    } 
    if (f2 < paramFloat) {
      if (i) {
        this.mLeftEdge.onPull(Math.abs(paramFloat - f2) / f3);
        bool3 = true;
      } 
    } else {
      bool3 = bool2;
      paramFloat = f2;
      if (f2 > f1) {
        bool3 = bool1;
        if (bool4) {
          this.mRightEdge.onPull(Math.abs(f2 - f1) / f3);
          bool3 = true;
        } 
        paramFloat = f1;
      } 
    } 
    f1 = this.mLastMotionX;
    int i = (int)paramFloat;
    this.mLastMotionX = f1 + paramFloat - i;
    scrollTo(i, getScrollY());
    pageScrolled(i);
    return bool3;
  }
  
  private void recomputeScrollPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (paramInt2 > 0 && !this.mItems.isEmpty()) {
      if (!this.mScroller.isFinished()) {
        this.mScroller.setFinalX(getCurrentItem() * getClientWidth());
      } else {
        int i = getPaddingLeft();
        int j = getPaddingRight();
        int k = getPaddingLeft();
        int m = getPaddingRight();
        scrollTo((int)(getScrollX() / (paramInt2 - k - m + paramInt4) * (paramInt1 - i - j + paramInt3)), getScrollY());
      } 
    } else {
      float f;
      ItemInfo itemInfo = infoForPosition(this.mCurItem);
      if (itemInfo != null) {
        f = Math.min(itemInfo.offset, this.mLastOffset);
      } else {
        f = 0.0F;
      } 
      paramInt1 = (int)(f * (paramInt1 - getPaddingLeft() - getPaddingRight()));
      if (paramInt1 != getScrollX()) {
        completeScroll(false);
        scrollTo(paramInt1, getScrollY());
      } 
    } 
  }
  
  private void removeNonDecorViews() {
    for (int i = 0; i < getChildCount(); i = j + 1) {
      int j = i;
      if (!((LayoutParams)getChildAt(i).getLayoutParams()).isDecor) {
        removeViewAt(i);
        j = i - 1;
      } 
    } 
  }
  
  private void requestParentDisallowInterceptTouchEvent(boolean paramBoolean) {
    ViewParent viewParent = getParent();
    if (viewParent != null)
      viewParent.requestDisallowInterceptTouchEvent(paramBoolean); 
  }
  
  private boolean resetTouch() {
    this.mActivePointerId = -1;
    endDrag();
    this.mLeftEdge.onRelease();
    this.mRightEdge.onRelease();
    return (this.mLeftEdge.isFinished() || this.mRightEdge.isFinished());
  }
  
  private void scrollToItem(int paramInt1, boolean paramBoolean1, int paramInt2, boolean paramBoolean2) {
    boolean bool;
    ItemInfo itemInfo = infoForPosition(paramInt1);
    if (itemInfo != null) {
      bool = (int)(getClientWidth() * Math.max(this.mFirstOffset, Math.min(itemInfo.offset, this.mLastOffset)));
    } else {
      bool = false;
    } 
    if (paramBoolean1) {
      smoothScrollTo(bool, 0, paramInt2);
      if (paramBoolean2)
        dispatchOnPageSelected(paramInt1); 
    } else {
      if (paramBoolean2)
        dispatchOnPageSelected(paramInt1); 
      completeScroll(false);
      scrollTo(bool, 0);
      pageScrolled(bool);
    } 
  }
  
  private void setScrollingCacheEnabled(boolean paramBoolean) {
    if (this.mScrollingCacheEnabled != paramBoolean)
      this.mScrollingCacheEnabled = paramBoolean; 
  }
  
  private void sortChildDrawingOrder() {
    if (this.mDrawingOrder != 0) {
      ArrayList<View> arrayList = this.mDrawingOrderedChildren;
      if (arrayList == null) {
        this.mDrawingOrderedChildren = new ArrayList<View>();
      } else {
        arrayList.clear();
      } 
      int i = getChildCount();
      for (byte b = 0; b < i; b++) {
        View view = getChildAt(b);
        this.mDrawingOrderedChildren.add(view);
      } 
      Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
    } 
  }
  
  public void addFocusables(ArrayList<View> paramArrayList, int paramInt1, int paramInt2) {
    int i = paramArrayList.size();
    int j = getDescendantFocusability();
    if (j != 393216)
      for (byte b = 0; b < getChildCount(); b++) {
        View view = getChildAt(b);
        if (view.getVisibility() == 0) {
          ItemInfo itemInfo = infoForChild(view);
          if (itemInfo != null && itemInfo.position == this.mCurItem)
            view.addFocusables(paramArrayList, paramInt1, paramInt2); 
        } 
      }  
    if (j != 262144 || i == paramArrayList.size()) {
      if (!isFocusable())
        return; 
      if ((paramInt2 & 0x1) == 1 && isInTouchMode() && !isFocusableInTouchMode())
        return; 
      if (paramArrayList != null)
        paramArrayList.add(this); 
    } 
  }
  
  ItemInfo addNewItem(int paramInt1, int paramInt2) {
    ItemInfo itemInfo = new ItemInfo();
    itemInfo.position = paramInt1;
    itemInfo.object = this.mAdapter.instantiateItem(this, paramInt1);
    itemInfo.widthFactor = this.mAdapter.getPageWidth(paramInt1);
    if (paramInt2 < 0 || paramInt2 >= this.mItems.size()) {
      this.mItems.add(itemInfo);
      return itemInfo;
    } 
    this.mItems.add(paramInt2, itemInfo);
    return itemInfo;
  }
  
  public void addOnAdapterChangeListener(OnAdapterChangeListener paramOnAdapterChangeListener) {
    if (this.mAdapterChangeListeners == null)
      this.mAdapterChangeListeners = new ArrayList<OnAdapterChangeListener>(); 
    this.mAdapterChangeListeners.add(paramOnAdapterChangeListener);
  }
  
  public void addOnPageChangeListener(OnPageChangeListener paramOnPageChangeListener) {
    if (this.mOnPageChangeListeners == null)
      this.mOnPageChangeListeners = new ArrayList<OnPageChangeListener>(); 
    this.mOnPageChangeListeners.add(paramOnPageChangeListener);
  }
  
  public void addTouchables(ArrayList<View> paramArrayList) {
    for (byte b = 0; b < getChildCount(); b++) {
      View view = getChildAt(b);
      if (view.getVisibility() == 0) {
        ItemInfo itemInfo = infoForChild(view);
        if (itemInfo != null && itemInfo.position == this.mCurItem)
          view.addTouchables(paramArrayList); 
      } 
    } 
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams) {
    ViewGroup.LayoutParams layoutParams = paramLayoutParams;
    if (!checkLayoutParams(paramLayoutParams))
      layoutParams = generateLayoutParams(paramLayoutParams); 
    paramLayoutParams = layoutParams;
    ((LayoutParams)paramLayoutParams).isDecor |= isDecorView(paramView);
    if (this.mInLayout) {
      if (paramLayoutParams == null || !((LayoutParams)paramLayoutParams).isDecor) {
        ((LayoutParams)paramLayoutParams).needsMeasure = true;
        addViewInLayout(paramView, paramInt, layoutParams);
        return;
      } 
      throw new IllegalStateException("Cannot add pager decor view during layout");
    } 
    super.addView(paramView, paramInt, layoutParams);
  }
  
  public boolean arrowScroll(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual findFocus : ()Landroid/view/View;
    //   4: astore_2
    //   5: iconst_0
    //   6: istore_3
    //   7: aload_2
    //   8: aload_0
    //   9: if_acmpne -> 18
    //   12: aconst_null
    //   13: astore #4
    //   15: goto -> 188
    //   18: aload_2
    //   19: astore #4
    //   21: aload_2
    //   22: ifnull -> 188
    //   25: aload_2
    //   26: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   29: astore #4
    //   31: aload #4
    //   33: instanceof android/view/ViewGroup
    //   36: ifeq -> 63
    //   39: aload #4
    //   41: aload_0
    //   42: if_acmpne -> 51
    //   45: iconst_1
    //   46: istore #5
    //   48: goto -> 66
    //   51: aload #4
    //   53: invokeinterface getParent : ()Landroid/view/ViewParent;
    //   58: astore #4
    //   60: goto -> 31
    //   63: iconst_0
    //   64: istore #5
    //   66: aload_2
    //   67: astore #4
    //   69: iload #5
    //   71: ifne -> 188
    //   74: new java/lang/StringBuilder
    //   77: dup
    //   78: invokespecial <init> : ()V
    //   81: astore #6
    //   83: aload #6
    //   85: aload_2
    //   86: invokevirtual getClass : ()Ljava/lang/Class;
    //   89: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   92: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: pop
    //   96: aload_2
    //   97: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   100: astore #4
    //   102: aload #4
    //   104: instanceof android/view/ViewGroup
    //   107: ifeq -> 145
    //   110: aload #6
    //   112: ldc_w ' => '
    //   115: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   118: pop
    //   119: aload #6
    //   121: aload #4
    //   123: invokevirtual getClass : ()Ljava/lang/Class;
    //   126: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   129: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   132: pop
    //   133: aload #4
    //   135: invokeinterface getParent : ()Landroid/view/ViewParent;
    //   140: astore #4
    //   142: goto -> 102
    //   145: new java/lang/StringBuilder
    //   148: dup
    //   149: invokespecial <init> : ()V
    //   152: astore #4
    //   154: aload #4
    //   156: ldc_w 'arrowScroll tried to find focus based on non-child current focused view '
    //   159: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   162: pop
    //   163: aload #4
    //   165: aload #6
    //   167: invokevirtual toString : ()Ljava/lang/String;
    //   170: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   173: pop
    //   174: ldc 'ViewPager'
    //   176: aload #4
    //   178: invokevirtual toString : ()Ljava/lang/String;
    //   181: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   184: pop
    //   185: goto -> 12
    //   188: invokestatic getInstance : ()Landroid/view/FocusFinder;
    //   191: aload_0
    //   192: aload #4
    //   194: iload_1
    //   195: invokevirtual findNextFocus : (Landroid/view/ViewGroup;Landroid/view/View;I)Landroid/view/View;
    //   198: astore_2
    //   199: aload_2
    //   200: ifnull -> 335
    //   203: aload_2
    //   204: aload #4
    //   206: if_acmpeq -> 335
    //   209: iload_1
    //   210: bipush #17
    //   212: if_icmpne -> 272
    //   215: aload_0
    //   216: aload_0
    //   217: getfield mTempRect : Landroid/graphics/Rect;
    //   220: aload_2
    //   221: invokespecial getChildRectInPagerCoordinates : (Landroid/graphics/Rect;Landroid/view/View;)Landroid/graphics/Rect;
    //   224: getfield left : I
    //   227: istore #7
    //   229: aload_0
    //   230: aload_0
    //   231: getfield mTempRect : Landroid/graphics/Rect;
    //   234: aload #4
    //   236: invokespecial getChildRectInPagerCoordinates : (Landroid/graphics/Rect;Landroid/view/View;)Landroid/graphics/Rect;
    //   239: getfield left : I
    //   242: istore #5
    //   244: aload #4
    //   246: ifnull -> 264
    //   249: iload #7
    //   251: iload #5
    //   253: if_icmplt -> 264
    //   256: aload_0
    //   257: invokevirtual pageLeft : ()Z
    //   260: istore_3
    //   261: goto -> 269
    //   264: aload_2
    //   265: invokevirtual requestFocus : ()Z
    //   268: istore_3
    //   269: goto -> 373
    //   272: iload_1
    //   273: bipush #66
    //   275: if_icmpne -> 373
    //   278: aload_0
    //   279: aload_0
    //   280: getfield mTempRect : Landroid/graphics/Rect;
    //   283: aload_2
    //   284: invokespecial getChildRectInPagerCoordinates : (Landroid/graphics/Rect;Landroid/view/View;)Landroid/graphics/Rect;
    //   287: getfield left : I
    //   290: istore #5
    //   292: aload_0
    //   293: aload_0
    //   294: getfield mTempRect : Landroid/graphics/Rect;
    //   297: aload #4
    //   299: invokespecial getChildRectInPagerCoordinates : (Landroid/graphics/Rect;Landroid/view/View;)Landroid/graphics/Rect;
    //   302: getfield left : I
    //   305: istore #7
    //   307: aload #4
    //   309: ifnull -> 327
    //   312: iload #5
    //   314: iload #7
    //   316: if_icmpgt -> 327
    //   319: aload_0
    //   320: invokevirtual pageRight : ()Z
    //   323: istore_3
    //   324: goto -> 269
    //   327: aload_2
    //   328: invokevirtual requestFocus : ()Z
    //   331: istore_3
    //   332: goto -> 269
    //   335: iload_1
    //   336: bipush #17
    //   338: if_icmpeq -> 368
    //   341: iload_1
    //   342: iconst_1
    //   343: if_icmpne -> 349
    //   346: goto -> 368
    //   349: iload_1
    //   350: bipush #66
    //   352: if_icmpeq -> 360
    //   355: iload_1
    //   356: iconst_2
    //   357: if_icmpne -> 373
    //   360: aload_0
    //   361: invokevirtual pageRight : ()Z
    //   364: istore_3
    //   365: goto -> 373
    //   368: aload_0
    //   369: invokevirtual pageLeft : ()Z
    //   372: istore_3
    //   373: iload_3
    //   374: ifeq -> 385
    //   377: aload_0
    //   378: iload_1
    //   379: invokestatic getContantForFocusDirection : (I)I
    //   382: invokevirtual playSoundEffect : (I)V
    //   385: iload_3
    //   386: ireturn
  }
  
  public boolean beginFakeDrag() {
    if (this.mIsBeingDragged)
      return false; 
    this.mFakeDragging = true;
    setScrollState(1);
    this.mLastMotionX = 0.0F;
    this.mInitialMotionX = 0.0F;
    VelocityTracker velocityTracker = this.mVelocityTracker;
    if (velocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    } else {
      velocityTracker.clear();
    } 
    long l = SystemClock.uptimeMillis();
    MotionEvent motionEvent = MotionEvent.obtain(l, l, 0, 0.0F, 0.0F, 0);
    this.mVelocityTracker.addMovement(motionEvent);
    motionEvent.recycle();
    this.mFakeDragBeginTime = l;
    return true;
  }
  
  protected boolean canScroll(View paramView, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3) {
    boolean bool = paramView instanceof ViewGroup;
    boolean bool1 = true;
    if (bool) {
      ViewGroup viewGroup = (ViewGroup)paramView;
      int i = paramView.getScrollX();
      int j = paramView.getScrollY();
      for (int k = viewGroup.getChildCount() - 1; k >= 0; k--) {
        View view = viewGroup.getChildAt(k);
        int m = paramInt2 + i;
        if (m >= view.getLeft() && m < view.getRight()) {
          int n = paramInt3 + j;
          if (n >= view.getTop() && n < view.getBottom() && canScroll(view, true, paramInt1, m - view.getLeft(), n - view.getTop()))
            return true; 
        } 
      } 
    } 
    if (paramBoolean && paramView.canScrollHorizontally(-paramInt1)) {
      paramBoolean = bool1;
    } else {
      paramBoolean = false;
    } 
    return paramBoolean;
  }
  
  public boolean canScrollHorizontally(int paramInt) {
    PagerAdapter pagerAdapter = this.mAdapter;
    boolean bool1 = false;
    boolean bool2 = false;
    if (pagerAdapter == null)
      return false; 
    int i = getClientWidth();
    int j = getScrollX();
    if (paramInt < 0) {
      if (j > (int)(i * this.mFirstOffset))
        bool2 = true; 
      return bool2;
    } 
    bool2 = bool1;
    if (paramInt > 0) {
      bool2 = bool1;
      if (j < (int)(i * this.mLastOffset))
        bool2 = true; 
    } 
    return bool2;
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
  
  public void clearOnPageChangeListeners() {
    List<OnPageChangeListener> list = this.mOnPageChangeListeners;
    if (list != null)
      list.clear(); 
  }
  
  public void computeScroll() {
    this.mIsScrollStarted = true;
    if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
      int i = getScrollX();
      int j = getScrollY();
      int k = this.mScroller.getCurrX();
      int m = this.mScroller.getCurrY();
      if (i != k || j != m) {
        scrollTo(k, m);
        if (!pageScrolled(k)) {
          this.mScroller.abortAnimation();
          scrollTo(0, m);
        } 
      } 
      ViewCompat.postInvalidateOnAnimation((View)this);
      return;
    } 
    completeScroll(true);
  }
  
  void dataSetChanged() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   4: invokevirtual getCount : ()I
    //   7: istore_1
    //   8: aload_0
    //   9: iload_1
    //   10: putfield mExpectedAdapterCount : I
    //   13: aload_0
    //   14: getfield mItems : Ljava/util/ArrayList;
    //   17: invokevirtual size : ()I
    //   20: aload_0
    //   21: getfield mOffscreenPageLimit : I
    //   24: iconst_2
    //   25: imul
    //   26: iconst_1
    //   27: iadd
    //   28: if_icmpge -> 47
    //   31: aload_0
    //   32: getfield mItems : Ljava/util/ArrayList;
    //   35: invokevirtual size : ()I
    //   38: iload_1
    //   39: if_icmpge -> 47
    //   42: iconst_1
    //   43: istore_2
    //   44: goto -> 49
    //   47: iconst_0
    //   48: istore_2
    //   49: aload_0
    //   50: getfield mCurItem : I
    //   53: istore_3
    //   54: iconst_0
    //   55: istore #4
    //   57: iconst_0
    //   58: istore #5
    //   60: iload_2
    //   61: istore #6
    //   63: iload #4
    //   65: aload_0
    //   66: getfield mItems : Ljava/util/ArrayList;
    //   69: invokevirtual size : ()I
    //   72: if_icmpge -> 298
    //   75: aload_0
    //   76: getfield mItems : Ljava/util/ArrayList;
    //   79: iload #4
    //   81: invokevirtual get : (I)Ljava/lang/Object;
    //   84: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   87: astore #7
    //   89: aload_0
    //   90: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   93: aload #7
    //   95: getfield object : Ljava/lang/Object;
    //   98: invokevirtual getItemPosition : (Ljava/lang/Object;)I
    //   101: istore #8
    //   103: iload #8
    //   105: iconst_m1
    //   106: if_icmpne -> 122
    //   109: iload_3
    //   110: istore_2
    //   111: iload #4
    //   113: istore #9
    //   115: iload #5
    //   117: istore #10
    //   119: goto -> 283
    //   122: iload #8
    //   124: bipush #-2
    //   126: if_icmpne -> 238
    //   129: aload_0
    //   130: getfield mItems : Ljava/util/ArrayList;
    //   133: iload #4
    //   135: invokevirtual remove : (I)Ljava/lang/Object;
    //   138: pop
    //   139: iload #4
    //   141: iconst_1
    //   142: isub
    //   143: istore #6
    //   145: iload #5
    //   147: istore_2
    //   148: iload #5
    //   150: ifne -> 163
    //   153: aload_0
    //   154: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   157: aload_0
    //   158: invokevirtual startUpdate : (Landroid/view/ViewGroup;)V
    //   161: iconst_1
    //   162: istore_2
    //   163: aload_0
    //   164: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   167: aload_0
    //   168: aload #7
    //   170: getfield position : I
    //   173: aload #7
    //   175: getfield object : Ljava/lang/Object;
    //   178: invokevirtual destroyItem : (Landroid/view/ViewGroup;ILjava/lang/Object;)V
    //   181: iload #6
    //   183: istore #4
    //   185: iload_2
    //   186: istore #5
    //   188: aload_0
    //   189: getfield mCurItem : I
    //   192: aload #7
    //   194: getfield position : I
    //   197: if_icmpne -> 222
    //   200: iconst_0
    //   201: aload_0
    //   202: getfield mCurItem : I
    //   205: iload_1
    //   206: iconst_1
    //   207: isub
    //   208: invokestatic min : (II)I
    //   211: invokestatic max : (II)I
    //   214: istore_3
    //   215: iload_2
    //   216: istore #5
    //   218: iload #6
    //   220: istore #4
    //   222: iconst_1
    //   223: istore #6
    //   225: iload_3
    //   226: istore_2
    //   227: iload #4
    //   229: istore #9
    //   231: iload #5
    //   233: istore #10
    //   235: goto -> 283
    //   238: iload_3
    //   239: istore_2
    //   240: iload #4
    //   242: istore #9
    //   244: iload #5
    //   246: istore #10
    //   248: aload #7
    //   250: getfield position : I
    //   253: iload #8
    //   255: if_icmpeq -> 283
    //   258: aload #7
    //   260: getfield position : I
    //   263: aload_0
    //   264: getfield mCurItem : I
    //   267: if_icmpne -> 273
    //   270: iload #8
    //   272: istore_3
    //   273: aload #7
    //   275: iload #8
    //   277: putfield position : I
    //   280: goto -> 222
    //   283: iload #9
    //   285: iconst_1
    //   286: iadd
    //   287: istore #4
    //   289: iload_2
    //   290: istore_3
    //   291: iload #10
    //   293: istore #5
    //   295: goto -> 63
    //   298: iload #5
    //   300: ifeq -> 311
    //   303: aload_0
    //   304: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   307: aload_0
    //   308: invokevirtual finishUpdate : (Landroid/view/ViewGroup;)V
    //   311: aload_0
    //   312: getfield mItems : Ljava/util/ArrayList;
    //   315: getstatic android/support/v4/view/ViewPager.COMPARATOR : Ljava/util/Comparator;
    //   318: invokestatic sort : (Ljava/util/List;Ljava/util/Comparator;)V
    //   321: iload #6
    //   323: ifeq -> 387
    //   326: aload_0
    //   327: invokevirtual getChildCount : ()I
    //   330: istore #4
    //   332: iconst_0
    //   333: istore #5
    //   335: iload #5
    //   337: iload #4
    //   339: if_icmpge -> 376
    //   342: aload_0
    //   343: iload #5
    //   345: invokevirtual getChildAt : (I)Landroid/view/View;
    //   348: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   351: checkcast android/support/v4/view/ViewPager$LayoutParams
    //   354: astore #7
    //   356: aload #7
    //   358: getfield isDecor : Z
    //   361: ifne -> 370
    //   364: aload #7
    //   366: fconst_0
    //   367: putfield widthFactor : F
    //   370: iinc #5, 1
    //   373: goto -> 335
    //   376: aload_0
    //   377: iload_3
    //   378: iconst_0
    //   379: iconst_1
    //   380: invokevirtual setCurrentItemInternal : (IZZ)V
    //   383: aload_0
    //   384: invokevirtual requestLayout : ()V
    //   387: return
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
    return (super.dispatchKeyEvent(paramKeyEvent) || executeKeyEvent(paramKeyEvent));
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent) {
    if (paramAccessibilityEvent.getEventType() == 4096)
      return super.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent); 
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = getChildAt(b);
      if (view.getVisibility() == 0) {
        ItemInfo itemInfo = infoForChild(view);
        if (itemInfo != null && itemInfo.position == this.mCurItem && view.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent))
          return true; 
      } 
    } 
    return false;
  }
  
  float distanceInfluenceForSnapDuration(float paramFloat) {
    return (float)Math.sin(((paramFloat - 0.5F) * 0.47123894F));
  }
  
  public void draw(Canvas paramCanvas) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokespecial draw : (Landroid/graphics/Canvas;)V
    //   5: aload_0
    //   6: invokevirtual getOverScrollMode : ()I
    //   9: istore_2
    //   10: iconst_0
    //   11: istore_3
    //   12: iconst_0
    //   13: istore #4
    //   15: iload_2
    //   16: ifeq -> 64
    //   19: iload_2
    //   20: iconst_1
    //   21: if_icmpne -> 47
    //   24: aload_0
    //   25: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   28: astore #5
    //   30: aload #5
    //   32: ifnull -> 47
    //   35: aload #5
    //   37: invokevirtual getCount : ()I
    //   40: iconst_1
    //   41: if_icmple -> 47
    //   44: goto -> 64
    //   47: aload_0
    //   48: getfield mLeftEdge : Landroid/widget/EdgeEffect;
    //   51: invokevirtual finish : ()V
    //   54: aload_0
    //   55: getfield mRightEdge : Landroid/widget/EdgeEffect;
    //   58: invokevirtual finish : ()V
    //   61: goto -> 256
    //   64: aload_0
    //   65: getfield mLeftEdge : Landroid/widget/EdgeEffect;
    //   68: invokevirtual isFinished : ()Z
    //   71: ifne -> 154
    //   74: aload_1
    //   75: invokevirtual save : ()I
    //   78: istore_3
    //   79: aload_0
    //   80: invokevirtual getHeight : ()I
    //   83: aload_0
    //   84: invokevirtual getPaddingTop : ()I
    //   87: isub
    //   88: aload_0
    //   89: invokevirtual getPaddingBottom : ()I
    //   92: isub
    //   93: istore_2
    //   94: aload_0
    //   95: invokevirtual getWidth : ()I
    //   98: istore #4
    //   100: aload_1
    //   101: ldc_w 270.0
    //   104: invokevirtual rotate : (F)V
    //   107: aload_1
    //   108: iload_2
    //   109: ineg
    //   110: aload_0
    //   111: invokevirtual getPaddingTop : ()I
    //   114: iadd
    //   115: i2f
    //   116: aload_0
    //   117: getfield mFirstOffset : F
    //   120: iload #4
    //   122: i2f
    //   123: fmul
    //   124: invokevirtual translate : (FF)V
    //   127: aload_0
    //   128: getfield mLeftEdge : Landroid/widget/EdgeEffect;
    //   131: iload_2
    //   132: iload #4
    //   134: invokevirtual setSize : (II)V
    //   137: iconst_0
    //   138: aload_0
    //   139: getfield mLeftEdge : Landroid/widget/EdgeEffect;
    //   142: aload_1
    //   143: invokevirtual draw : (Landroid/graphics/Canvas;)Z
    //   146: ior
    //   147: istore #4
    //   149: aload_1
    //   150: iload_3
    //   151: invokevirtual restoreToCount : (I)V
    //   154: iload #4
    //   156: istore_3
    //   157: aload_0
    //   158: getfield mRightEdge : Landroid/widget/EdgeEffect;
    //   161: invokevirtual isFinished : ()Z
    //   164: ifne -> 256
    //   167: aload_1
    //   168: invokevirtual save : ()I
    //   171: istore_2
    //   172: aload_0
    //   173: invokevirtual getWidth : ()I
    //   176: istore #6
    //   178: aload_0
    //   179: invokevirtual getHeight : ()I
    //   182: istore #7
    //   184: aload_0
    //   185: invokevirtual getPaddingTop : ()I
    //   188: istore_3
    //   189: aload_0
    //   190: invokevirtual getPaddingBottom : ()I
    //   193: istore #8
    //   195: aload_1
    //   196: ldc_w 90.0
    //   199: invokevirtual rotate : (F)V
    //   202: aload_1
    //   203: aload_0
    //   204: invokevirtual getPaddingTop : ()I
    //   207: ineg
    //   208: i2f
    //   209: aload_0
    //   210: getfield mLastOffset : F
    //   213: fconst_1
    //   214: fadd
    //   215: fneg
    //   216: iload #6
    //   218: i2f
    //   219: fmul
    //   220: invokevirtual translate : (FF)V
    //   223: aload_0
    //   224: getfield mRightEdge : Landroid/widget/EdgeEffect;
    //   227: iload #7
    //   229: iload_3
    //   230: isub
    //   231: iload #8
    //   233: isub
    //   234: iload #6
    //   236: invokevirtual setSize : (II)V
    //   239: iload #4
    //   241: aload_0
    //   242: getfield mRightEdge : Landroid/widget/EdgeEffect;
    //   245: aload_1
    //   246: invokevirtual draw : (Landroid/graphics/Canvas;)Z
    //   249: ior
    //   250: istore_3
    //   251: aload_1
    //   252: iload_2
    //   253: invokevirtual restoreToCount : (I)V
    //   256: iload_3
    //   257: ifeq -> 264
    //   260: aload_0
    //   261: invokestatic postInvalidateOnAnimation : (Landroid/view/View;)V
    //   264: return
  }
  
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    Drawable drawable = this.mMarginDrawable;
    if (drawable != null && drawable.isStateful())
      drawable.setState(getDrawableState()); 
  }
  
  public void endFakeDrag() {
    if (this.mFakeDragging) {
      if (this.mAdapter != null) {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
        int i = (int)velocityTracker.getXVelocity(this.mActivePointerId);
        this.mPopulatePending = true;
        int j = getClientWidth();
        int k = getScrollX();
        ItemInfo itemInfo = infoForCurrentScrollPosition();
        setCurrentItemInternal(determineTargetPage(itemInfo.position, (k / j - itemInfo.offset) / itemInfo.widthFactor, i, (int)(this.mLastMotionX - this.mInitialMotionX)), true, true, i);
      } 
      endDrag();
      this.mFakeDragging = false;
      return;
    } 
    throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
  }
  
  public boolean executeKeyEvent(KeyEvent paramKeyEvent) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getAction : ()I
    //   4: ifne -> 118
    //   7: aload_1
    //   8: invokevirtual getKeyCode : ()I
    //   11: istore_2
    //   12: iload_2
    //   13: bipush #21
    //   15: if_icmpeq -> 92
    //   18: iload_2
    //   19: bipush #22
    //   21: if_icmpeq -> 66
    //   24: iload_2
    //   25: bipush #61
    //   27: if_icmpeq -> 33
    //   30: goto -> 118
    //   33: aload_1
    //   34: invokevirtual hasNoModifiers : ()Z
    //   37: ifeq -> 49
    //   40: aload_0
    //   41: iconst_2
    //   42: invokevirtual arrowScroll : (I)Z
    //   45: istore_3
    //   46: goto -> 120
    //   49: aload_1
    //   50: iconst_1
    //   51: invokevirtual hasModifiers : (I)Z
    //   54: ifeq -> 118
    //   57: aload_0
    //   58: iconst_1
    //   59: invokevirtual arrowScroll : (I)Z
    //   62: istore_3
    //   63: goto -> 120
    //   66: aload_1
    //   67: iconst_2
    //   68: invokevirtual hasModifiers : (I)Z
    //   71: ifeq -> 82
    //   74: aload_0
    //   75: invokevirtual pageRight : ()Z
    //   78: istore_3
    //   79: goto -> 120
    //   82: aload_0
    //   83: bipush #66
    //   85: invokevirtual arrowScroll : (I)Z
    //   88: istore_3
    //   89: goto -> 120
    //   92: aload_1
    //   93: iconst_2
    //   94: invokevirtual hasModifiers : (I)Z
    //   97: ifeq -> 108
    //   100: aload_0
    //   101: invokevirtual pageLeft : ()Z
    //   104: istore_3
    //   105: goto -> 120
    //   108: aload_0
    //   109: bipush #17
    //   111: invokevirtual arrowScroll : (I)Z
    //   114: istore_3
    //   115: goto -> 120
    //   118: iconst_0
    //   119: istore_3
    //   120: iload_3
    //   121: ireturn
  }
  
  public void fakeDragBy(float paramFloat) {
    if (this.mFakeDragging) {
      if (this.mAdapter == null)
        return; 
      this.mLastMotionX += paramFloat;
      float f1 = getScrollX() - paramFloat;
      float f2 = getClientWidth();
      paramFloat = this.mFirstOffset * f2;
      float f3 = this.mLastOffset * f2;
      ItemInfo itemInfo1 = this.mItems.get(0);
      ArrayList<ItemInfo> arrayList = this.mItems;
      ItemInfo itemInfo2 = arrayList.get(arrayList.size() - 1);
      if (itemInfo1.position != 0)
        paramFloat = itemInfo1.offset * f2; 
      if (itemInfo2.position != this.mAdapter.getCount() - 1)
        f3 = itemInfo2.offset * f2; 
      if (f1 >= paramFloat) {
        paramFloat = f1;
        if (f1 > f3)
          paramFloat = f3; 
      } 
      f3 = this.mLastMotionX;
      int i = (int)paramFloat;
      this.mLastMotionX = f3 + paramFloat - i;
      scrollTo(i, getScrollY());
      pageScrolled(i);
      long l = SystemClock.uptimeMillis();
      MotionEvent motionEvent = MotionEvent.obtain(this.mFakeDragBeginTime, l, 2, this.mLastMotionX, 0.0F, 0);
      this.mVelocityTracker.addMovement(motionEvent);
      motionEvent.recycle();
      return;
    } 
    throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
    return new LayoutParams();
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return generateDefaultLayoutParams();
  }
  
  public PagerAdapter getAdapter() {
    return this.mAdapter;
  }
  
  protected int getChildDrawingOrder(int paramInt1, int paramInt2) {
    int i = paramInt2;
    if (this.mDrawingOrder == 2)
      i = paramInt1 - 1 - paramInt2; 
    return ((LayoutParams)((View)this.mDrawingOrderedChildren.get(i)).getLayoutParams()).childIndex;
  }
  
  public int getCurrentItem() {
    return this.mCurItem;
  }
  
  public int getOffscreenPageLimit() {
    return this.mOffscreenPageLimit;
  }
  
  public int getPageMargin() {
    return this.mPageMargin;
  }
  
  ItemInfo infoForAnyChild(View paramView) {
    while (true) {
      ViewParent viewParent = paramView.getParent();
      if (viewParent != this) {
        if (viewParent != null) {
          if (!(viewParent instanceof View))
            return null; 
          paramView = (View)viewParent;
          continue;
        } 
        continue;
      } 
      return infoForChild(paramView);
    } 
  }
  
  ItemInfo infoForChild(View paramView) {
    for (byte b = 0; b < this.mItems.size(); b++) {
      ItemInfo itemInfo = this.mItems.get(b);
      if (this.mAdapter.isViewFromObject(paramView, itemInfo.object))
        return itemInfo; 
    } 
    return null;
  }
  
  ItemInfo infoForPosition(int paramInt) {
    for (byte b = 0; b < this.mItems.size(); b++) {
      ItemInfo itemInfo = this.mItems.get(b);
      if (itemInfo.position == paramInt)
        return itemInfo; 
    } 
    return null;
  }
  
  void initViewPager() {
    setWillNotDraw(false);
    setDescendantFocusability(262144);
    setFocusable(true);
    Context context = getContext();
    this.mScroller = new Scroller(context, sInterpolator);
    ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
    float f = (context.getResources().getDisplayMetrics()).density;
    this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
    this.mMinimumVelocity = (int)(400.0F * f);
    this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    this.mLeftEdge = new EdgeEffect(context);
    this.mRightEdge = new EdgeEffect(context);
    this.mFlingDistance = (int)(25.0F * f);
    this.mCloseEnough = (int)(2.0F * f);
    this.mDefaultGutterSize = (int)(f * 16.0F);
    ViewCompat.setAccessibilityDelegate((View)this, new MyAccessibilityDelegate());
    if (ViewCompat.getImportantForAccessibility((View)this) == 0)
      ViewCompat.setImportantForAccessibility((View)this, 1); 
    ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener() {
          private final Rect mTempRect = new Rect();
          
          public WindowInsetsCompat onApplyWindowInsets(View param1View, WindowInsetsCompat param1WindowInsetsCompat) {
            param1WindowInsetsCompat = ViewCompat.onApplyWindowInsets(param1View, param1WindowInsetsCompat);
            if (param1WindowInsetsCompat.isConsumed())
              return param1WindowInsetsCompat; 
            Rect rect = this.mTempRect;
            rect.left = param1WindowInsetsCompat.getSystemWindowInsetLeft();
            rect.top = param1WindowInsetsCompat.getSystemWindowInsetTop();
            rect.right = param1WindowInsetsCompat.getSystemWindowInsetRight();
            rect.bottom = param1WindowInsetsCompat.getSystemWindowInsetBottom();
            byte b = 0;
            int i = ViewPager.this.getChildCount();
            while (b < i) {
              WindowInsetsCompat windowInsetsCompat = ViewCompat.dispatchApplyWindowInsets(ViewPager.this.getChildAt(b), param1WindowInsetsCompat);
              rect.left = Math.min(windowInsetsCompat.getSystemWindowInsetLeft(), rect.left);
              rect.top = Math.min(windowInsetsCompat.getSystemWindowInsetTop(), rect.top);
              rect.right = Math.min(windowInsetsCompat.getSystemWindowInsetRight(), rect.right);
              rect.bottom = Math.min(windowInsetsCompat.getSystemWindowInsetBottom(), rect.bottom);
              b++;
            } 
            return param1WindowInsetsCompat.replaceSystemWindowInsets(rect.left, rect.top, rect.right, rect.bottom);
          }
        });
  }
  
  public boolean isFakeDragging() {
    return this.mFakeDragging;
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    this.mFirstLayout = true;
  }
  
  protected void onDetachedFromWindow() {
    removeCallbacks(this.mEndScrollRunnable);
    Scroller scroller = this.mScroller;
    if (scroller != null && !scroller.isFinished())
      this.mScroller.abortAnimation(); 
    super.onDetachedFromWindow();
  }
  
  protected void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
      int i = getScrollX();
      int j = getWidth();
      float f1 = this.mPageMargin;
      float f2 = j;
      float f3 = f1 / f2;
      ArrayList<ItemInfo> arrayList = this.mItems;
      byte b = 0;
      ItemInfo itemInfo = arrayList.get(0);
      f1 = itemInfo.offset;
      int k = this.mItems.size();
      int m = itemInfo.position;
      int n = ((ItemInfo)this.mItems.get(k - 1)).position;
      while (m < n) {
        ItemInfo itemInfo1;
        float f;
        while (m > itemInfo.position && b < k) {
          ArrayList<ItemInfo> arrayList1 = this.mItems;
          itemInfo1 = arrayList1.get(++b);
        } 
        if (m == itemInfo1.position) {
          f = (itemInfo1.offset + itemInfo1.widthFactor) * f2;
          f1 = itemInfo1.offset + itemInfo1.widthFactor + f3;
        } else {
          float f4 = this.mAdapter.getPageWidth(m);
          f = f1 + f4 + f3;
          f4 = (f1 + f4) * f2;
          f1 = f;
          f = f4;
        } 
        if (this.mPageMargin + f > i) {
          this.mMarginDrawable.setBounds(Math.round(f), this.mTopPageBounds, Math.round(this.mPageMargin + f), this.mBottomPageBounds);
          this.mMarginDrawable.draw(paramCanvas);
        } 
        if (f > (i + j))
          break; 
        m++;
      } 
    } 
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getAction() & 0xFF;
    if (i == 3 || i == 1) {
      resetTouch();
      return false;
    } 
    if (i != 0) {
      if (this.mIsBeingDragged)
        return true; 
      if (this.mIsUnableToDrag)
        return false; 
    } 
    if (i != 0) {
      if (i != 2) {
        if (i == 6)
          onSecondaryPointerUp(paramMotionEvent); 
      } else {
        i = this.mActivePointerId;
        if (i != -1) {
          i = paramMotionEvent.findPointerIndex(i);
          float f1 = paramMotionEvent.getX(i);
          float f2 = f1 - this.mLastMotionX;
          float f3 = Math.abs(f2);
          float f4 = paramMotionEvent.getY(i);
          float f5 = Math.abs(f4 - this.mInitialMotionY);
          i = f2 cmp 0.0F;
          if (i != 0 && !isGutterDrag(this.mLastMotionX, f2) && canScroll((View)this, false, (int)f2, (int)f1, (int)f4)) {
            this.mLastMotionX = f1;
            this.mLastMotionY = f4;
            this.mIsUnableToDrag = true;
            return false;
          } 
          if (f3 > this.mTouchSlop && f3 * 0.5F > f5) {
            this.mIsBeingDragged = true;
            requestParentDisallowInterceptTouchEvent(true);
            setScrollState(1);
            f5 = this.mInitialMotionX;
            f3 = this.mTouchSlop;
            if (i > 0) {
              f3 = f5 + f3;
            } else {
              f3 = f5 - f3;
            } 
            this.mLastMotionX = f3;
            this.mLastMotionY = f4;
            setScrollingCacheEnabled(true);
          } else if (f5 > this.mTouchSlop) {
            this.mIsUnableToDrag = true;
          } 
          if (this.mIsBeingDragged && performDrag(f1))
            ViewCompat.postInvalidateOnAnimation((View)this); 
        } 
      } 
    } else {
      float f = paramMotionEvent.getX();
      this.mInitialMotionX = f;
      this.mLastMotionX = f;
      f = paramMotionEvent.getY();
      this.mInitialMotionY = f;
      this.mLastMotionY = f;
      this.mActivePointerId = paramMotionEvent.getPointerId(0);
      this.mIsUnableToDrag = false;
      this.mIsScrollStarted = true;
      this.mScroller.computeScrollOffset();
      if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough) {
        this.mScroller.abortAnimation();
        this.mPopulatePending = false;
        populate();
        this.mIsBeingDragged = true;
        requestParentDisallowInterceptTouchEvent(true);
        setScrollState(1);
      } else {
        completeScroll(false);
        this.mIsBeingDragged = false;
      } 
    } 
    if (this.mVelocityTracker == null)
      this.mVelocityTracker = VelocityTracker.obtain(); 
    this.mVelocityTracker.addMovement(paramMotionEvent);
    return this.mIsBeingDragged;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = getChildCount();
    int j = paramInt3 - paramInt1;
    int k = paramInt4 - paramInt2;
    paramInt2 = getPaddingLeft();
    paramInt1 = getPaddingTop();
    paramInt4 = getPaddingRight();
    paramInt3 = getPaddingBottom();
    int m = getScrollX();
    int n = 0;
    int i1;
    for (i1 = 0; n < i; i1 = i6) {
      View view = getChildAt(n);
      int i2 = paramInt2;
      int i3 = paramInt1;
      int i4 = paramInt4;
      int i5 = paramInt3;
      int i6 = i1;
      if (view.getVisibility() != 8) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        i2 = paramInt2;
        i3 = paramInt1;
        i4 = paramInt4;
        i5 = paramInt3;
        i6 = i1;
        if (layoutParams.isDecor) {
          i6 = layoutParams.gravity & 0x7;
          i4 = layoutParams.gravity & 0x70;
          if (i6 != 1) {
            if (i6 != 3) {
              if (i6 != 5) {
                i6 = paramInt2;
                i5 = paramInt2;
                paramInt2 = i6;
              } else {
                i6 = j - paramInt4 - view.getMeasuredWidth();
                paramInt4 += view.getMeasuredWidth();
                i5 = i6;
              } 
            } else {
              i6 = view.getMeasuredWidth() + paramInt2;
              i5 = paramInt2;
              paramInt2 = i6;
            } 
          } else {
            i6 = Math.max((j - view.getMeasuredWidth()) / 2, paramInt2);
            i5 = i6;
          } 
          if (i4 != 16) {
            if (i4 != 48) {
              if (i4 != 80) {
                i4 = paramInt1;
                i6 = paramInt1;
                paramInt1 = i4;
              } else {
                i6 = k - paramInt3 - view.getMeasuredHeight();
                paramInt3 += view.getMeasuredHeight();
              } 
            } else {
              i4 = view.getMeasuredHeight() + paramInt1;
              i6 = paramInt1;
              paramInt1 = i4;
            } 
          } else {
            i6 = Math.max((k - view.getMeasuredHeight()) / 2, paramInt1);
          } 
          i5 += m;
          view.layout(i5, i6, view.getMeasuredWidth() + i5, i6 + view.getMeasuredHeight());
          i6 = i1 + 1;
          i5 = paramInt3;
          i4 = paramInt4;
          i3 = paramInt1;
          i2 = paramInt2;
        } 
      } 
      n++;
      paramInt2 = i2;
      paramInt1 = i3;
      paramInt4 = i4;
      paramInt3 = i5;
    } 
    for (byte b = 0; b < i; b++) {
      View view = getChildAt(b);
      if (view.getVisibility() != 8) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (!layoutParams.isDecor) {
          ItemInfo itemInfo = infoForChild(view);
          if (itemInfo != null) {
            float f = (j - paramInt2 - paramInt4);
            n = (int)(itemInfo.offset * f) + paramInt2;
            if (layoutParams.needsMeasure) {
              layoutParams.needsMeasure = false;
              view.measure(View.MeasureSpec.makeMeasureSpec((int)(f * layoutParams.widthFactor), 1073741824), View.MeasureSpec.makeMeasureSpec(k - paramInt1 - paramInt3, 1073741824));
            } 
            view.layout(n, paramInt1, view.getMeasuredWidth() + n, view.getMeasuredHeight() + paramInt1);
          } 
        } 
      } 
    } 
    this.mTopPageBounds = paramInt1;
    this.mBottomPageBounds = k - paramInt3;
    this.mDecorChildCount = i1;
    if (this.mFirstLayout)
      scrollToItem(this.mCurItem, false, 0, false); 
    this.mFirstLayout = false;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: iconst_0
    //   2: iload_1
    //   3: invokestatic getDefaultSize : (II)I
    //   6: iconst_0
    //   7: iload_2
    //   8: invokestatic getDefaultSize : (II)I
    //   11: invokevirtual setMeasuredDimension : (II)V
    //   14: aload_0
    //   15: invokevirtual getMeasuredWidth : ()I
    //   18: istore_1
    //   19: aload_0
    //   20: iload_1
    //   21: bipush #10
    //   23: idiv
    //   24: aload_0
    //   25: getfield mDefaultGutterSize : I
    //   28: invokestatic min : (II)I
    //   31: putfield mGutterSize : I
    //   34: iload_1
    //   35: aload_0
    //   36: invokevirtual getPaddingLeft : ()I
    //   39: isub
    //   40: aload_0
    //   41: invokevirtual getPaddingRight : ()I
    //   44: isub
    //   45: istore_1
    //   46: aload_0
    //   47: invokevirtual getMeasuredHeight : ()I
    //   50: aload_0
    //   51: invokevirtual getPaddingTop : ()I
    //   54: isub
    //   55: aload_0
    //   56: invokevirtual getPaddingBottom : ()I
    //   59: isub
    //   60: istore_2
    //   61: aload_0
    //   62: invokevirtual getChildCount : ()I
    //   65: istore_3
    //   66: iconst_0
    //   67: istore #4
    //   69: iconst_1
    //   70: istore #5
    //   72: ldc_w 1073741824
    //   75: istore #6
    //   77: iload #4
    //   79: iload_3
    //   80: if_icmpge -> 429
    //   83: aload_0
    //   84: iload #4
    //   86: invokevirtual getChildAt : (I)Landroid/view/View;
    //   89: astore #7
    //   91: iload_1
    //   92: istore #8
    //   94: iload_2
    //   95: istore #9
    //   97: aload #7
    //   99: invokevirtual getVisibility : ()I
    //   102: bipush #8
    //   104: if_icmpeq -> 417
    //   107: aload #7
    //   109: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   112: checkcast android/support/v4/view/ViewPager$LayoutParams
    //   115: astore #10
    //   117: iload_1
    //   118: istore #8
    //   120: iload_2
    //   121: istore #9
    //   123: aload #10
    //   125: ifnull -> 417
    //   128: iload_1
    //   129: istore #8
    //   131: iload_2
    //   132: istore #9
    //   134: aload #10
    //   136: getfield isDecor : Z
    //   139: ifeq -> 417
    //   142: aload #10
    //   144: getfield gravity : I
    //   147: bipush #7
    //   149: iand
    //   150: istore #8
    //   152: aload #10
    //   154: getfield gravity : I
    //   157: bipush #112
    //   159: iand
    //   160: istore #9
    //   162: iload #9
    //   164: bipush #48
    //   166: if_icmpeq -> 185
    //   169: iload #9
    //   171: bipush #80
    //   173: if_icmpne -> 179
    //   176: goto -> 185
    //   179: iconst_0
    //   180: istore #11
    //   182: goto -> 188
    //   185: iconst_1
    //   186: istore #11
    //   188: iload #5
    //   190: istore #12
    //   192: iload #8
    //   194: iconst_3
    //   195: if_icmpeq -> 214
    //   198: iload #8
    //   200: iconst_5
    //   201: if_icmpne -> 211
    //   204: iload #5
    //   206: istore #12
    //   208: goto -> 214
    //   211: iconst_0
    //   212: istore #12
    //   214: ldc_w -2147483648
    //   217: istore #8
    //   219: iload #11
    //   221: ifeq -> 232
    //   224: ldc_w 1073741824
    //   227: istore #9
    //   229: goto -> 257
    //   232: iload #8
    //   234: istore #9
    //   236: iload #12
    //   238: ifeq -> 257
    //   241: ldc_w 1073741824
    //   244: istore #5
    //   246: iload #8
    //   248: istore #9
    //   250: iload #5
    //   252: istore #8
    //   254: goto -> 262
    //   257: ldc_w -2147483648
    //   260: istore #8
    //   262: aload #10
    //   264: getfield width : I
    //   267: bipush #-2
    //   269: if_icmpeq -> 302
    //   272: aload #10
    //   274: getfield width : I
    //   277: iconst_m1
    //   278: if_icmpeq -> 291
    //   281: aload #10
    //   283: getfield width : I
    //   286: istore #9
    //   288: goto -> 294
    //   291: iload_1
    //   292: istore #9
    //   294: ldc_w 1073741824
    //   297: istore #5
    //   299: goto -> 309
    //   302: iload #9
    //   304: istore #5
    //   306: iload_1
    //   307: istore #9
    //   309: aload #10
    //   311: getfield height : I
    //   314: bipush #-2
    //   316: if_icmpeq -> 344
    //   319: aload #10
    //   321: getfield height : I
    //   324: iconst_m1
    //   325: if_icmpeq -> 338
    //   328: aload #10
    //   330: getfield height : I
    //   333: istore #8
    //   335: goto -> 355
    //   338: iload_2
    //   339: istore #8
    //   341: goto -> 355
    //   344: iload_2
    //   345: istore #13
    //   347: iload #8
    //   349: istore #6
    //   351: iload #13
    //   353: istore #8
    //   355: aload #7
    //   357: iload #9
    //   359: iload #5
    //   361: invokestatic makeMeasureSpec : (II)I
    //   364: iload #8
    //   366: iload #6
    //   368: invokestatic makeMeasureSpec : (II)I
    //   371: invokevirtual measure : (II)V
    //   374: iload #11
    //   376: ifeq -> 394
    //   379: iload_2
    //   380: aload #7
    //   382: invokevirtual getMeasuredHeight : ()I
    //   385: isub
    //   386: istore #9
    //   388: iload_1
    //   389: istore #8
    //   391: goto -> 417
    //   394: iload_1
    //   395: istore #8
    //   397: iload_2
    //   398: istore #9
    //   400: iload #12
    //   402: ifeq -> 417
    //   405: iload_1
    //   406: aload #7
    //   408: invokevirtual getMeasuredWidth : ()I
    //   411: isub
    //   412: istore #8
    //   414: iload_2
    //   415: istore #9
    //   417: iinc #4, 1
    //   420: iload #8
    //   422: istore_1
    //   423: iload #9
    //   425: istore_2
    //   426: goto -> 69
    //   429: aload_0
    //   430: iload_1
    //   431: ldc_w 1073741824
    //   434: invokestatic makeMeasureSpec : (II)I
    //   437: putfield mChildWidthMeasureSpec : I
    //   440: aload_0
    //   441: iload_2
    //   442: ldc_w 1073741824
    //   445: invokestatic makeMeasureSpec : (II)I
    //   448: putfield mChildHeightMeasureSpec : I
    //   451: aload_0
    //   452: iconst_1
    //   453: putfield mInLayout : Z
    //   456: aload_0
    //   457: invokevirtual populate : ()V
    //   460: iconst_0
    //   461: istore_2
    //   462: aload_0
    //   463: iconst_0
    //   464: putfield mInLayout : Z
    //   467: aload_0
    //   468: invokevirtual getChildCount : ()I
    //   471: istore #8
    //   473: iload_2
    //   474: iload #8
    //   476: if_icmpge -> 549
    //   479: aload_0
    //   480: iload_2
    //   481: invokevirtual getChildAt : (I)Landroid/view/View;
    //   484: astore #7
    //   486: aload #7
    //   488: invokevirtual getVisibility : ()I
    //   491: bipush #8
    //   493: if_icmpeq -> 543
    //   496: aload #7
    //   498: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   501: checkcast android/support/v4/view/ViewPager$LayoutParams
    //   504: astore #10
    //   506: aload #10
    //   508: ifnull -> 519
    //   511: aload #10
    //   513: getfield isDecor : Z
    //   516: ifne -> 543
    //   519: aload #7
    //   521: iload_1
    //   522: i2f
    //   523: aload #10
    //   525: getfield widthFactor : F
    //   528: fmul
    //   529: f2i
    //   530: ldc_w 1073741824
    //   533: invokestatic makeMeasureSpec : (II)I
    //   536: aload_0
    //   537: getfield mChildHeightMeasureSpec : I
    //   540: invokevirtual measure : (II)V
    //   543: iinc #2, 1
    //   546: goto -> 473
    //   549: return
  }
  
  protected void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
    int i = this.mDecorChildCount;
    boolean bool = false;
    if (i > 0) {
      int j = getScrollX();
      i = getPaddingLeft();
      int k = getPaddingRight();
      int m = getWidth();
      int n = getChildCount();
      for (byte b = 0; b < n; b++) {
        View view = getChildAt(b);
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.isDecor) {
          int i1 = layoutParams.gravity & 0x7;
          if (i1 != 1) {
            if (i1 != 3) {
              if (i1 != 5) {
                int i2 = i;
                i1 = i;
                i = i2;
              } else {
                i1 = m - k - view.getMeasuredWidth();
                k += view.getMeasuredWidth();
              } 
            } else {
              int i2 = view.getWidth() + i;
              i1 = i;
              i = i2;
            } 
          } else {
            i1 = Math.max((m - view.getMeasuredWidth()) / 2, i);
          } 
          i1 = i1 + j - view.getLeft();
          if (i1 != 0)
            view.offsetLeftAndRight(i1); 
        } 
      } 
    } 
    dispatchOnPageScrolled(paramInt1, paramFloat, paramInt2);
    if (this.mPageTransformer != null) {
      paramInt2 = getScrollX();
      i = getChildCount();
      for (paramInt1 = bool; paramInt1 < i; paramInt1++) {
        View view = getChildAt(paramInt1);
        if (!((LayoutParams)view.getLayoutParams()).isDecor) {
          paramFloat = (view.getLeft() - paramInt2) / getClientWidth();
          this.mPageTransformer.transformPage(view, paramFloat);
        } 
      } 
    } 
    this.mCalledSuper = true;
  }
  
  protected boolean onRequestFocusInDescendants(int paramInt, Rect paramRect) {
    byte b;
    int i = getChildCount();
    int j = -1;
    if ((paramInt & 0x2) != 0) {
      j = i;
      i = 0;
      b = 1;
    } else {
      i--;
      b = -1;
    } 
    while (i != j) {
      View view = getChildAt(i);
      if (view.getVisibility() == 0) {
        ItemInfo itemInfo = infoForChild(view);
        if (itemInfo != null && itemInfo.position == this.mCurItem && view.requestFocus(paramInt, paramRect))
          return true; 
      } 
      i += b;
    } 
    return false;
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable) {
    if (!(paramParcelable instanceof SavedState)) {
      super.onRestoreInstanceState(paramParcelable);
      return;
    } 
    paramParcelable = paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    PagerAdapter pagerAdapter = this.mAdapter;
    if (pagerAdapter != null) {
      pagerAdapter.restoreState(((SavedState)paramParcelable).adapterState, ((SavedState)paramParcelable).loader);
      setCurrentItemInternal(((SavedState)paramParcelable).position, false, true);
    } else {
      this.mRestoredCurItem = ((SavedState)paramParcelable).position;
      this.mRestoredAdapterState = ((SavedState)paramParcelable).adapterState;
      this.mRestoredClassLoader = ((SavedState)paramParcelable).loader;
    } 
  }
  
  public Parcelable onSaveInstanceState() {
    SavedState savedState = new SavedState(super.onSaveInstanceState());
    savedState.position = this.mCurItem;
    PagerAdapter pagerAdapter = this.mAdapter;
    if (pagerAdapter != null)
      savedState.adapterState = pagerAdapter.saveState(); 
    return savedState;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt1 != paramInt3) {
      paramInt2 = this.mPageMargin;
      recomputeScrollPosition(paramInt1, paramInt3, paramInt2, paramInt2);
    } 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mFakeDragging : Z
    //   4: ifeq -> 9
    //   7: iconst_1
    //   8: ireturn
    //   9: aload_1
    //   10: invokevirtual getAction : ()I
    //   13: istore_2
    //   14: iconst_0
    //   15: istore_3
    //   16: iload_2
    //   17: ifne -> 29
    //   20: aload_1
    //   21: invokevirtual getEdgeFlags : ()I
    //   24: ifeq -> 29
    //   27: iconst_0
    //   28: ireturn
    //   29: aload_0
    //   30: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   33: astore #4
    //   35: aload #4
    //   37: ifnull -> 611
    //   40: aload #4
    //   42: invokevirtual getCount : ()I
    //   45: ifne -> 51
    //   48: goto -> 611
    //   51: aload_0
    //   52: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   55: ifnonnull -> 65
    //   58: aload_0
    //   59: invokestatic obtain : ()Landroid/view/VelocityTracker;
    //   62: putfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   65: aload_0
    //   66: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   69: aload_1
    //   70: invokevirtual addMovement : (Landroid/view/MotionEvent;)V
    //   73: aload_1
    //   74: invokevirtual getAction : ()I
    //   77: sipush #255
    //   80: iand
    //   81: istore_2
    //   82: iload_2
    //   83: ifeq -> 540
    //   86: iload_2
    //   87: iconst_1
    //   88: if_icmpeq -> 396
    //   91: iload_2
    //   92: iconst_2
    //   93: if_icmpeq -> 191
    //   96: iload_2
    //   97: iconst_3
    //   98: if_icmpeq -> 165
    //   101: iload_2
    //   102: iconst_5
    //   103: if_icmpeq -> 139
    //   106: iload_2
    //   107: bipush #6
    //   109: if_icmpeq -> 115
    //   112: goto -> 601
    //   115: aload_0
    //   116: aload_1
    //   117: invokespecial onSecondaryPointerUp : (Landroid/view/MotionEvent;)V
    //   120: aload_0
    //   121: aload_1
    //   122: aload_1
    //   123: aload_0
    //   124: getfield mActivePointerId : I
    //   127: invokevirtual findPointerIndex : (I)I
    //   130: invokevirtual getX : (I)F
    //   133: putfield mLastMotionX : F
    //   136: goto -> 601
    //   139: aload_1
    //   140: invokevirtual getActionIndex : ()I
    //   143: istore_2
    //   144: aload_0
    //   145: aload_1
    //   146: iload_2
    //   147: invokevirtual getX : (I)F
    //   150: putfield mLastMotionX : F
    //   153: aload_0
    //   154: aload_1
    //   155: iload_2
    //   156: invokevirtual getPointerId : (I)I
    //   159: putfield mActivePointerId : I
    //   162: goto -> 601
    //   165: aload_0
    //   166: getfield mIsBeingDragged : Z
    //   169: ifeq -> 601
    //   172: aload_0
    //   173: aload_0
    //   174: getfield mCurItem : I
    //   177: iconst_1
    //   178: iconst_0
    //   179: iconst_0
    //   180: invokespecial scrollToItem : (IZIZ)V
    //   183: aload_0
    //   184: invokespecial resetTouch : ()Z
    //   187: istore_3
    //   188: goto -> 601
    //   191: aload_0
    //   192: getfield mIsBeingDragged : Z
    //   195: ifne -> 367
    //   198: aload_1
    //   199: aload_0
    //   200: getfield mActivePointerId : I
    //   203: invokevirtual findPointerIndex : (I)I
    //   206: istore_2
    //   207: iload_2
    //   208: iconst_m1
    //   209: if_icmpne -> 220
    //   212: aload_0
    //   213: invokespecial resetTouch : ()Z
    //   216: istore_3
    //   217: goto -> 601
    //   220: aload_1
    //   221: iload_2
    //   222: invokevirtual getX : (I)F
    //   225: fstore #5
    //   227: fload #5
    //   229: aload_0
    //   230: getfield mLastMotionX : F
    //   233: fsub
    //   234: invokestatic abs : (F)F
    //   237: fstore #6
    //   239: aload_1
    //   240: iload_2
    //   241: invokevirtual getY : (I)F
    //   244: fstore #7
    //   246: fload #7
    //   248: aload_0
    //   249: getfield mLastMotionY : F
    //   252: fsub
    //   253: invokestatic abs : (F)F
    //   256: fstore #8
    //   258: fload #6
    //   260: aload_0
    //   261: getfield mTouchSlop : I
    //   264: i2f
    //   265: fcmpl
    //   266: ifle -> 367
    //   269: fload #6
    //   271: fload #8
    //   273: fcmpl
    //   274: ifle -> 367
    //   277: aload_0
    //   278: iconst_1
    //   279: putfield mIsBeingDragged : Z
    //   282: aload_0
    //   283: iconst_1
    //   284: invokespecial requestParentDisallowInterceptTouchEvent : (Z)V
    //   287: aload_0
    //   288: getfield mInitialMotionX : F
    //   291: fstore #6
    //   293: fload #5
    //   295: fload #6
    //   297: fsub
    //   298: fconst_0
    //   299: fcmpl
    //   300: ifle -> 316
    //   303: fload #6
    //   305: aload_0
    //   306: getfield mTouchSlop : I
    //   309: i2f
    //   310: fadd
    //   311: fstore #5
    //   313: goto -> 326
    //   316: fload #6
    //   318: aload_0
    //   319: getfield mTouchSlop : I
    //   322: i2f
    //   323: fsub
    //   324: fstore #5
    //   326: aload_0
    //   327: fload #5
    //   329: putfield mLastMotionX : F
    //   332: aload_0
    //   333: fload #7
    //   335: putfield mLastMotionY : F
    //   338: aload_0
    //   339: iconst_1
    //   340: invokevirtual setScrollState : (I)V
    //   343: aload_0
    //   344: iconst_1
    //   345: invokespecial setScrollingCacheEnabled : (Z)V
    //   348: aload_0
    //   349: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   352: astore #4
    //   354: aload #4
    //   356: ifnull -> 367
    //   359: aload #4
    //   361: iconst_1
    //   362: invokeinterface requestDisallowInterceptTouchEvent : (Z)V
    //   367: aload_0
    //   368: getfield mIsBeingDragged : Z
    //   371: ifeq -> 601
    //   374: iconst_0
    //   375: aload_0
    //   376: aload_1
    //   377: aload_1
    //   378: aload_0
    //   379: getfield mActivePointerId : I
    //   382: invokevirtual findPointerIndex : (I)I
    //   385: invokevirtual getX : (I)F
    //   388: invokespecial performDrag : (F)Z
    //   391: ior
    //   392: istore_3
    //   393: goto -> 601
    //   396: aload_0
    //   397: getfield mIsBeingDragged : Z
    //   400: ifeq -> 601
    //   403: aload_0
    //   404: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   407: astore #4
    //   409: aload #4
    //   411: sipush #1000
    //   414: aload_0
    //   415: getfield mMaximumVelocity : I
    //   418: i2f
    //   419: invokevirtual computeCurrentVelocity : (IF)V
    //   422: aload #4
    //   424: aload_0
    //   425: getfield mActivePointerId : I
    //   428: invokevirtual getXVelocity : (I)F
    //   431: f2i
    //   432: istore #9
    //   434: aload_0
    //   435: iconst_1
    //   436: putfield mPopulatePending : Z
    //   439: aload_0
    //   440: invokespecial getClientWidth : ()I
    //   443: istore #10
    //   445: aload_0
    //   446: invokevirtual getScrollX : ()I
    //   449: istore_2
    //   450: aload_0
    //   451: invokespecial infoForCurrentScrollPosition : ()Landroid/support/v4/view/ViewPager$ItemInfo;
    //   454: astore #4
    //   456: aload_0
    //   457: getfield mPageMargin : I
    //   460: i2f
    //   461: fstore #7
    //   463: iload #10
    //   465: i2f
    //   466: fstore #5
    //   468: fload #7
    //   470: fload #5
    //   472: fdiv
    //   473: fstore #7
    //   475: aload_0
    //   476: aload_0
    //   477: aload #4
    //   479: getfield position : I
    //   482: iload_2
    //   483: i2f
    //   484: fload #5
    //   486: fdiv
    //   487: aload #4
    //   489: getfield offset : F
    //   492: fsub
    //   493: aload #4
    //   495: getfield widthFactor : F
    //   498: fload #7
    //   500: fadd
    //   501: fdiv
    //   502: iload #9
    //   504: aload_1
    //   505: aload_1
    //   506: aload_0
    //   507: getfield mActivePointerId : I
    //   510: invokevirtual findPointerIndex : (I)I
    //   513: invokevirtual getX : (I)F
    //   516: aload_0
    //   517: getfield mInitialMotionX : F
    //   520: fsub
    //   521: f2i
    //   522: invokespecial determineTargetPage : (IFII)I
    //   525: iconst_1
    //   526: iconst_1
    //   527: iload #9
    //   529: invokevirtual setCurrentItemInternal : (IZZI)V
    //   532: aload_0
    //   533: invokespecial resetTouch : ()Z
    //   536: istore_3
    //   537: goto -> 601
    //   540: aload_0
    //   541: getfield mScroller : Landroid/widget/Scroller;
    //   544: invokevirtual abortAnimation : ()V
    //   547: aload_0
    //   548: iconst_0
    //   549: putfield mPopulatePending : Z
    //   552: aload_0
    //   553: invokevirtual populate : ()V
    //   556: aload_1
    //   557: invokevirtual getX : ()F
    //   560: fstore #5
    //   562: aload_0
    //   563: fload #5
    //   565: putfield mInitialMotionX : F
    //   568: aload_0
    //   569: fload #5
    //   571: putfield mLastMotionX : F
    //   574: aload_1
    //   575: invokevirtual getY : ()F
    //   578: fstore #5
    //   580: aload_0
    //   581: fload #5
    //   583: putfield mInitialMotionY : F
    //   586: aload_0
    //   587: fload #5
    //   589: putfield mLastMotionY : F
    //   592: aload_0
    //   593: aload_1
    //   594: iconst_0
    //   595: invokevirtual getPointerId : (I)I
    //   598: putfield mActivePointerId : I
    //   601: iload_3
    //   602: ifeq -> 609
    //   605: aload_0
    //   606: invokestatic postInvalidateOnAnimation : (Landroid/view/View;)V
    //   609: iconst_1
    //   610: ireturn
    //   611: iconst_0
    //   612: ireturn
  }
  
  boolean pageLeft() {
    int i = this.mCurItem;
    if (i > 0) {
      setCurrentItem(i - 1, true);
      return true;
    } 
    return false;
  }
  
  boolean pageRight() {
    PagerAdapter pagerAdapter = this.mAdapter;
    if (pagerAdapter != null && this.mCurItem < pagerAdapter.getCount() - 1) {
      setCurrentItem(this.mCurItem + 1, true);
      return true;
    } 
    return false;
  }
  
  void populate() {
    populate(this.mCurItem);
  }
  
  void populate(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mCurItem : I
    //   4: istore_2
    //   5: iload_2
    //   6: iload_1
    //   7: if_icmpeq -> 24
    //   10: aload_0
    //   11: iload_2
    //   12: invokevirtual infoForPosition : (I)Landroid/support/v4/view/ViewPager$ItemInfo;
    //   15: astore_3
    //   16: aload_0
    //   17: iload_1
    //   18: putfield mCurItem : I
    //   21: goto -> 26
    //   24: aconst_null
    //   25: astore_3
    //   26: aload_0
    //   27: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   30: ifnonnull -> 38
    //   33: aload_0
    //   34: invokespecial sortChildDrawingOrder : ()V
    //   37: return
    //   38: aload_0
    //   39: getfield mPopulatePending : Z
    //   42: ifeq -> 50
    //   45: aload_0
    //   46: invokespecial sortChildDrawingOrder : ()V
    //   49: return
    //   50: aload_0
    //   51: invokevirtual getWindowToken : ()Landroid/os/IBinder;
    //   54: ifnonnull -> 58
    //   57: return
    //   58: aload_0
    //   59: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   62: aload_0
    //   63: invokevirtual startUpdate : (Landroid/view/ViewGroup;)V
    //   66: aload_0
    //   67: getfield mOffscreenPageLimit : I
    //   70: istore_1
    //   71: iconst_0
    //   72: aload_0
    //   73: getfield mCurItem : I
    //   76: iload_1
    //   77: isub
    //   78: invokestatic max : (II)I
    //   81: istore #4
    //   83: aload_0
    //   84: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   87: invokevirtual getCount : ()I
    //   90: istore #5
    //   92: iload #5
    //   94: iconst_1
    //   95: isub
    //   96: aload_0
    //   97: getfield mCurItem : I
    //   100: iload_1
    //   101: iadd
    //   102: invokestatic min : (II)I
    //   105: istore #6
    //   107: iload #5
    //   109: aload_0
    //   110: getfield mExpectedAdapterCount : I
    //   113: if_icmpne -> 1192
    //   116: iconst_0
    //   117: istore_1
    //   118: iload_1
    //   119: aload_0
    //   120: getfield mItems : Ljava/util/ArrayList;
    //   123: invokevirtual size : ()I
    //   126: if_icmpge -> 175
    //   129: aload_0
    //   130: getfield mItems : Ljava/util/ArrayList;
    //   133: iload_1
    //   134: invokevirtual get : (I)Ljava/lang/Object;
    //   137: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   140: astore #7
    //   142: aload #7
    //   144: getfield position : I
    //   147: aload_0
    //   148: getfield mCurItem : I
    //   151: if_icmplt -> 169
    //   154: aload #7
    //   156: getfield position : I
    //   159: aload_0
    //   160: getfield mCurItem : I
    //   163: if_icmpne -> 175
    //   166: goto -> 178
    //   169: iinc #1, 1
    //   172: goto -> 118
    //   175: aconst_null
    //   176: astore #7
    //   178: aload #7
    //   180: astore #8
    //   182: aload #7
    //   184: ifnonnull -> 207
    //   187: aload #7
    //   189: astore #8
    //   191: iload #5
    //   193: ifle -> 207
    //   196: aload_0
    //   197: aload_0
    //   198: getfield mCurItem : I
    //   201: iload_1
    //   202: invokevirtual addNewItem : (II)Landroid/support/v4/view/ViewPager$ItemInfo;
    //   205: astore #8
    //   207: aload #8
    //   209: ifnull -> 988
    //   212: iload_1
    //   213: iconst_1
    //   214: isub
    //   215: istore_2
    //   216: iload_2
    //   217: iflt -> 236
    //   220: aload_0
    //   221: getfield mItems : Ljava/util/ArrayList;
    //   224: iload_2
    //   225: invokevirtual get : (I)Ljava/lang/Object;
    //   228: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   231: astore #7
    //   233: goto -> 239
    //   236: aconst_null
    //   237: astore #7
    //   239: aload_0
    //   240: invokespecial getClientWidth : ()I
    //   243: istore #9
    //   245: iload #9
    //   247: ifgt -> 256
    //   250: fconst_0
    //   251: fstore #10
    //   253: goto -> 275
    //   256: fconst_2
    //   257: aload #8
    //   259: getfield widthFactor : F
    //   262: fsub
    //   263: aload_0
    //   264: invokevirtual getPaddingLeft : ()I
    //   267: i2f
    //   268: iload #9
    //   270: i2f
    //   271: fdiv
    //   272: fadd
    //   273: fstore #10
    //   275: aload_0
    //   276: getfield mCurItem : I
    //   279: iconst_1
    //   280: isub
    //   281: istore #11
    //   283: fconst_0
    //   284: fstore #12
    //   286: aload #7
    //   288: astore #13
    //   290: iload #11
    //   292: iflt -> 579
    //   295: fload #12
    //   297: fload #10
    //   299: fcmpl
    //   300: iflt -> 428
    //   303: iload #11
    //   305: iload #4
    //   307: if_icmpge -> 428
    //   310: aload #13
    //   312: ifnonnull -> 318
    //   315: goto -> 579
    //   318: iload_1
    //   319: istore #14
    //   321: iload_2
    //   322: istore #15
    //   324: aload #13
    //   326: astore #7
    //   328: fload #12
    //   330: fstore #16
    //   332: iload #11
    //   334: aload #13
    //   336: getfield position : I
    //   339: if_icmpne -> 559
    //   342: iload_1
    //   343: istore #14
    //   345: iload_2
    //   346: istore #15
    //   348: aload #13
    //   350: astore #7
    //   352: fload #12
    //   354: fstore #16
    //   356: aload #13
    //   358: getfield scrolling : Z
    //   361: ifne -> 559
    //   364: aload_0
    //   365: getfield mItems : Ljava/util/ArrayList;
    //   368: iload_2
    //   369: invokevirtual remove : (I)Ljava/lang/Object;
    //   372: pop
    //   373: aload_0
    //   374: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   377: aload_0
    //   378: iload #11
    //   380: aload #13
    //   382: getfield object : Ljava/lang/Object;
    //   385: invokevirtual destroyItem : (Landroid/view/ViewGroup;ILjava/lang/Object;)V
    //   388: iinc #2, -1
    //   391: iinc #1, -1
    //   394: iload_1
    //   395: istore #15
    //   397: iload_2
    //   398: istore #14
    //   400: fload #12
    //   402: fstore #16
    //   404: iload_2
    //   405: iflt -> 544
    //   408: aload_0
    //   409: getfield mItems : Ljava/util/ArrayList;
    //   412: iload_2
    //   413: invokevirtual get : (I)Ljava/lang/Object;
    //   416: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   419: astore #7
    //   421: fload #12
    //   423: fstore #16
    //   425: goto -> 553
    //   428: aload #13
    //   430: ifnull -> 490
    //   433: iload #11
    //   435: aload #13
    //   437: getfield position : I
    //   440: if_icmpne -> 490
    //   443: fload #12
    //   445: aload #13
    //   447: getfield widthFactor : F
    //   450: fadd
    //   451: fstore #12
    //   453: iinc #2, -1
    //   456: iload_1
    //   457: istore #15
    //   459: iload_2
    //   460: istore #14
    //   462: fload #12
    //   464: fstore #16
    //   466: iload_2
    //   467: iflt -> 544
    //   470: aload_0
    //   471: getfield mItems : Ljava/util/ArrayList;
    //   474: iload_2
    //   475: invokevirtual get : (I)Ljava/lang/Object;
    //   478: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   481: astore #7
    //   483: fload #12
    //   485: fstore #16
    //   487: goto -> 553
    //   490: fload #12
    //   492: aload_0
    //   493: iload #11
    //   495: iload_2
    //   496: iconst_1
    //   497: iadd
    //   498: invokevirtual addNewItem : (II)Landroid/support/v4/view/ViewPager$ItemInfo;
    //   501: getfield widthFactor : F
    //   504: fadd
    //   505: fstore #12
    //   507: iinc #1, 1
    //   510: iload_1
    //   511: istore #15
    //   513: iload_2
    //   514: istore #14
    //   516: fload #12
    //   518: fstore #16
    //   520: iload_2
    //   521: iflt -> 544
    //   524: aload_0
    //   525: getfield mItems : Ljava/util/ArrayList;
    //   528: iload_2
    //   529: invokevirtual get : (I)Ljava/lang/Object;
    //   532: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   535: astore #7
    //   537: fload #12
    //   539: fstore #16
    //   541: goto -> 553
    //   544: aconst_null
    //   545: astore #7
    //   547: iload #14
    //   549: istore_2
    //   550: iload #15
    //   552: istore_1
    //   553: iload_2
    //   554: istore #15
    //   556: iload_1
    //   557: istore #14
    //   559: iinc #11, -1
    //   562: iload #14
    //   564: istore_1
    //   565: iload #15
    //   567: istore_2
    //   568: aload #7
    //   570: astore #13
    //   572: fload #16
    //   574: fstore #12
    //   576: goto -> 290
    //   579: aload #8
    //   581: getfield widthFactor : F
    //   584: fstore #12
    //   586: iload_1
    //   587: iconst_1
    //   588: iadd
    //   589: istore #15
    //   591: fload #12
    //   593: fconst_2
    //   594: fcmpg
    //   595: ifge -> 963
    //   598: iload #15
    //   600: aload_0
    //   601: getfield mItems : Ljava/util/ArrayList;
    //   604: invokevirtual size : ()I
    //   607: if_icmpge -> 627
    //   610: aload_0
    //   611: getfield mItems : Ljava/util/ArrayList;
    //   614: iload #15
    //   616: invokevirtual get : (I)Ljava/lang/Object;
    //   619: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   622: astore #7
    //   624: goto -> 630
    //   627: aconst_null
    //   628: astore #7
    //   630: iload #9
    //   632: ifgt -> 641
    //   635: fconst_0
    //   636: fstore #10
    //   638: goto -> 654
    //   641: aload_0
    //   642: invokevirtual getPaddingRight : ()I
    //   645: i2f
    //   646: iload #9
    //   648: i2f
    //   649: fdiv
    //   650: fconst_2
    //   651: fadd
    //   652: fstore #10
    //   654: aload_0
    //   655: getfield mCurItem : I
    //   658: istore_2
    //   659: aload #7
    //   661: astore #13
    //   663: iload_2
    //   664: iconst_1
    //   665: iadd
    //   666: istore #14
    //   668: iload #14
    //   670: iload #5
    //   672: if_icmpge -> 963
    //   675: fload #12
    //   677: fload #10
    //   679: fcmpl
    //   680: iflt -> 812
    //   683: iload #14
    //   685: iload #6
    //   687: if_icmple -> 812
    //   690: aload #13
    //   692: ifnonnull -> 698
    //   695: goto -> 963
    //   698: fload #12
    //   700: fstore #16
    //   702: iload #15
    //   704: istore_2
    //   705: aload #13
    //   707: astore #7
    //   709: iload #14
    //   711: aload #13
    //   713: getfield position : I
    //   716: if_icmpne -> 946
    //   719: fload #12
    //   721: fstore #16
    //   723: iload #15
    //   725: istore_2
    //   726: aload #13
    //   728: astore #7
    //   730: aload #13
    //   732: getfield scrolling : Z
    //   735: ifne -> 946
    //   738: aload_0
    //   739: getfield mItems : Ljava/util/ArrayList;
    //   742: iload #15
    //   744: invokevirtual remove : (I)Ljava/lang/Object;
    //   747: pop
    //   748: aload_0
    //   749: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   752: aload_0
    //   753: iload #14
    //   755: aload #13
    //   757: getfield object : Ljava/lang/Object;
    //   760: invokevirtual destroyItem : (Landroid/view/ViewGroup;ILjava/lang/Object;)V
    //   763: fload #12
    //   765: fstore #16
    //   767: iload #15
    //   769: istore_2
    //   770: iload #15
    //   772: aload_0
    //   773: getfield mItems : Ljava/util/ArrayList;
    //   776: invokevirtual size : ()I
    //   779: if_icmpge -> 806
    //   782: aload_0
    //   783: getfield mItems : Ljava/util/ArrayList;
    //   786: iload #15
    //   788: invokevirtual get : (I)Ljava/lang/Object;
    //   791: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   794: astore #7
    //   796: fload #12
    //   798: fstore #16
    //   800: iload #15
    //   802: istore_2
    //   803: goto -> 946
    //   806: aconst_null
    //   807: astore #7
    //   809: goto -> 946
    //   812: aload #13
    //   814: ifnull -> 883
    //   817: iload #14
    //   819: aload #13
    //   821: getfield position : I
    //   824: if_icmpne -> 883
    //   827: fload #12
    //   829: aload #13
    //   831: getfield widthFactor : F
    //   834: fadd
    //   835: fstore #12
    //   837: iinc #15, 1
    //   840: fload #12
    //   842: fstore #16
    //   844: iload #15
    //   846: istore_2
    //   847: iload #15
    //   849: aload_0
    //   850: getfield mItems : Ljava/util/ArrayList;
    //   853: invokevirtual size : ()I
    //   856: if_icmpge -> 806
    //   859: aload_0
    //   860: getfield mItems : Ljava/util/ArrayList;
    //   863: iload #15
    //   865: invokevirtual get : (I)Ljava/lang/Object;
    //   868: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   871: astore #7
    //   873: fload #12
    //   875: fstore #16
    //   877: iload #15
    //   879: istore_2
    //   880: goto -> 946
    //   883: aload_0
    //   884: iload #14
    //   886: iload #15
    //   888: invokevirtual addNewItem : (II)Landroid/support/v4/view/ViewPager$ItemInfo;
    //   891: astore #7
    //   893: iinc #15, 1
    //   896: fload #12
    //   898: aload #7
    //   900: getfield widthFactor : F
    //   903: fadd
    //   904: fstore #12
    //   906: fload #12
    //   908: fstore #16
    //   910: iload #15
    //   912: istore_2
    //   913: iload #15
    //   915: aload_0
    //   916: getfield mItems : Ljava/util/ArrayList;
    //   919: invokevirtual size : ()I
    //   922: if_icmpge -> 806
    //   925: aload_0
    //   926: getfield mItems : Ljava/util/ArrayList;
    //   929: iload #15
    //   931: invokevirtual get : (I)Ljava/lang/Object;
    //   934: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   937: astore #7
    //   939: iload #15
    //   941: istore_2
    //   942: fload #12
    //   944: fstore #16
    //   946: fload #16
    //   948: fstore #12
    //   950: iload_2
    //   951: istore #15
    //   953: aload #7
    //   955: astore #13
    //   957: iload #14
    //   959: istore_2
    //   960: goto -> 663
    //   963: aload_0
    //   964: aload #8
    //   966: iload_1
    //   967: aload_3
    //   968: invokespecial calculatePageOffsets : (Landroid/support/v4/view/ViewPager$ItemInfo;ILandroid/support/v4/view/ViewPager$ItemInfo;)V
    //   971: aload_0
    //   972: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   975: aload_0
    //   976: aload_0
    //   977: getfield mCurItem : I
    //   980: aload #8
    //   982: getfield object : Ljava/lang/Object;
    //   985: invokevirtual setPrimaryItem : (Landroid/view/ViewGroup;ILjava/lang/Object;)V
    //   988: aload_0
    //   989: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   992: aload_0
    //   993: invokevirtual finishUpdate : (Landroid/view/ViewGroup;)V
    //   996: aload_0
    //   997: invokevirtual getChildCount : ()I
    //   1000: istore_2
    //   1001: iconst_0
    //   1002: istore_1
    //   1003: iload_1
    //   1004: iload_2
    //   1005: if_icmpge -> 1081
    //   1008: aload_0
    //   1009: iload_1
    //   1010: invokevirtual getChildAt : (I)Landroid/view/View;
    //   1013: astore_3
    //   1014: aload_3
    //   1015: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   1018: checkcast android/support/v4/view/ViewPager$LayoutParams
    //   1021: astore #7
    //   1023: aload #7
    //   1025: iload_1
    //   1026: putfield childIndex : I
    //   1029: aload #7
    //   1031: getfield isDecor : Z
    //   1034: ifne -> 1075
    //   1037: aload #7
    //   1039: getfield widthFactor : F
    //   1042: fconst_0
    //   1043: fcmpl
    //   1044: ifne -> 1075
    //   1047: aload_0
    //   1048: aload_3
    //   1049: invokevirtual infoForChild : (Landroid/view/View;)Landroid/support/v4/view/ViewPager$ItemInfo;
    //   1052: astore_3
    //   1053: aload_3
    //   1054: ifnull -> 1075
    //   1057: aload #7
    //   1059: aload_3
    //   1060: getfield widthFactor : F
    //   1063: putfield widthFactor : F
    //   1066: aload #7
    //   1068: aload_3
    //   1069: getfield position : I
    //   1072: putfield position : I
    //   1075: iinc #1, 1
    //   1078: goto -> 1003
    //   1081: aload_0
    //   1082: invokespecial sortChildDrawingOrder : ()V
    //   1085: aload_0
    //   1086: invokevirtual hasFocus : ()Z
    //   1089: ifeq -> 1191
    //   1092: aload_0
    //   1093: invokevirtual findFocus : ()Landroid/view/View;
    //   1096: astore #7
    //   1098: aload #7
    //   1100: ifnull -> 1114
    //   1103: aload_0
    //   1104: aload #7
    //   1106: invokevirtual infoForAnyChild : (Landroid/view/View;)Landroid/support/v4/view/ViewPager$ItemInfo;
    //   1109: astore #7
    //   1111: goto -> 1117
    //   1114: aconst_null
    //   1115: astore #7
    //   1117: aload #7
    //   1119: ifnull -> 1134
    //   1122: aload #7
    //   1124: getfield position : I
    //   1127: aload_0
    //   1128: getfield mCurItem : I
    //   1131: if_icmpeq -> 1191
    //   1134: iconst_0
    //   1135: istore_1
    //   1136: iload_1
    //   1137: aload_0
    //   1138: invokevirtual getChildCount : ()I
    //   1141: if_icmpge -> 1191
    //   1144: aload_0
    //   1145: iload_1
    //   1146: invokevirtual getChildAt : (I)Landroid/view/View;
    //   1149: astore_3
    //   1150: aload_0
    //   1151: aload_3
    //   1152: invokevirtual infoForChild : (Landroid/view/View;)Landroid/support/v4/view/ViewPager$ItemInfo;
    //   1155: astore #7
    //   1157: aload #7
    //   1159: ifnull -> 1185
    //   1162: aload #7
    //   1164: getfield position : I
    //   1167: aload_0
    //   1168: getfield mCurItem : I
    //   1171: if_icmpne -> 1185
    //   1174: aload_3
    //   1175: iconst_2
    //   1176: invokevirtual requestFocus : (I)Z
    //   1179: ifeq -> 1185
    //   1182: goto -> 1191
    //   1185: iinc #1, 1
    //   1188: goto -> 1136
    //   1191: return
    //   1192: aload_0
    //   1193: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   1196: aload_0
    //   1197: invokevirtual getId : ()I
    //   1200: invokevirtual getResourceName : (I)Ljava/lang/String;
    //   1203: astore #7
    //   1205: goto -> 1219
    //   1208: astore #7
    //   1210: aload_0
    //   1211: invokevirtual getId : ()I
    //   1214: invokestatic toHexString : (I)Ljava/lang/String;
    //   1217: astore #7
    //   1219: new java/lang/StringBuilder
    //   1222: dup
    //   1223: invokespecial <init> : ()V
    //   1226: astore_3
    //   1227: aload_3
    //   1228: ldc_w 'The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: '
    //   1231: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1234: pop
    //   1235: aload_3
    //   1236: aload_0
    //   1237: getfield mExpectedAdapterCount : I
    //   1240: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1243: pop
    //   1244: aload_3
    //   1245: ldc_w ', found: '
    //   1248: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1251: pop
    //   1252: aload_3
    //   1253: iload #5
    //   1255: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1258: pop
    //   1259: aload_3
    //   1260: ldc_w ' Pager id: '
    //   1263: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1266: pop
    //   1267: aload_3
    //   1268: aload #7
    //   1270: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1273: pop
    //   1274: aload_3
    //   1275: ldc_w ' Pager class: '
    //   1278: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1281: pop
    //   1282: aload_3
    //   1283: aload_0
    //   1284: invokevirtual getClass : ()Ljava/lang/Class;
    //   1287: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1290: pop
    //   1291: aload_3
    //   1292: ldc_w ' Problematic adapter: '
    //   1295: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1298: pop
    //   1299: aload_3
    //   1300: aload_0
    //   1301: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   1304: invokevirtual getClass : ()Ljava/lang/Class;
    //   1307: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1310: pop
    //   1311: new java/lang/IllegalStateException
    //   1314: dup
    //   1315: aload_3
    //   1316: invokevirtual toString : ()Ljava/lang/String;
    //   1319: invokespecial <init> : (Ljava/lang/String;)V
    //   1322: athrow
    // Exception table:
    //   from	to	target	type
    //   1192	1205	1208	android/content/res/Resources$NotFoundException
  }
  
  public void removeOnAdapterChangeListener(OnAdapterChangeListener paramOnAdapterChangeListener) {
    List<OnAdapterChangeListener> list = this.mAdapterChangeListeners;
    if (list != null)
      list.remove(paramOnAdapterChangeListener); 
  }
  
  public void removeOnPageChangeListener(OnPageChangeListener paramOnPageChangeListener) {
    List<OnPageChangeListener> list = this.mOnPageChangeListeners;
    if (list != null)
      list.remove(paramOnPageChangeListener); 
  }
  
  public void removeView(View paramView) {
    if (this.mInLayout) {
      removeViewInLayout(paramView);
    } else {
      super.removeView(paramView);
    } 
  }
  
  public void setAdapter(PagerAdapter paramPagerAdapter) {
    PagerAdapter pagerAdapter = this.mAdapter;
    byte b = 0;
    if (pagerAdapter != null) {
      pagerAdapter.setViewPagerObserver(null);
      this.mAdapter.startUpdate(this);
      for (byte b1 = 0; b1 < this.mItems.size(); b1++) {
        ItemInfo itemInfo = this.mItems.get(b1);
        this.mAdapter.destroyItem(this, itemInfo.position, itemInfo.object);
      } 
      this.mAdapter.finishUpdate(this);
      this.mItems.clear();
      removeNonDecorViews();
      this.mCurItem = 0;
      scrollTo(0, 0);
    } 
    pagerAdapter = this.mAdapter;
    this.mAdapter = paramPagerAdapter;
    this.mExpectedAdapterCount = 0;
    if (paramPagerAdapter != null) {
      if (this.mObserver == null)
        this.mObserver = new PagerObserver(); 
      this.mAdapter.setViewPagerObserver(this.mObserver);
      this.mPopulatePending = false;
      boolean bool = this.mFirstLayout;
      this.mFirstLayout = true;
      this.mExpectedAdapterCount = this.mAdapter.getCount();
      if (this.mRestoredCurItem >= 0) {
        this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
        setCurrentItemInternal(this.mRestoredCurItem, false, true);
        this.mRestoredCurItem = -1;
        this.mRestoredAdapterState = null;
        this.mRestoredClassLoader = null;
      } else if (!bool) {
        populate();
      } else {
        requestLayout();
      } 
    } 
    List<OnAdapterChangeListener> list = this.mAdapterChangeListeners;
    if (list != null && !list.isEmpty()) {
      int i = this.mAdapterChangeListeners.size();
      for (byte b1 = b; b1 < i; b1++)
        ((OnAdapterChangeListener)this.mAdapterChangeListeners.get(b1)).onAdapterChanged(this, pagerAdapter, paramPagerAdapter); 
    } 
  }
  
  public void setCurrentItem(int paramInt) {
    this.mPopulatePending = false;
    setCurrentItemInternal(paramInt, this.mFirstLayout ^ true, false);
  }
  
  public void setCurrentItem(int paramInt, boolean paramBoolean) {
    this.mPopulatePending = false;
    setCurrentItemInternal(paramInt, paramBoolean, false);
  }
  
  void setCurrentItemInternal(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
    setCurrentItemInternal(paramInt, paramBoolean1, paramBoolean2, 0);
  }
  
  void setCurrentItemInternal(int paramInt1, boolean paramBoolean1, boolean paramBoolean2, int paramInt2) {
    int i;
    PagerAdapter pagerAdapter = this.mAdapter;
    boolean bool = false;
    if (pagerAdapter == null || pagerAdapter.getCount() <= 0) {
      setScrollingCacheEnabled(false);
      return;
    } 
    if (!paramBoolean2 && this.mCurItem == paramInt1 && this.mItems.size() != 0) {
      setScrollingCacheEnabled(false);
      return;
    } 
    if (paramInt1 < 0) {
      i = 0;
    } else {
      i = paramInt1;
      if (paramInt1 >= this.mAdapter.getCount())
        i = this.mAdapter.getCount() - 1; 
    } 
    paramInt1 = this.mOffscreenPageLimit;
    int j = this.mCurItem;
    if (i > j + paramInt1 || i < j - paramInt1)
      for (paramInt1 = 0; paramInt1 < this.mItems.size(); paramInt1++)
        ((ItemInfo)this.mItems.get(paramInt1)).scrolling = true;  
    paramBoolean2 = bool;
    if (this.mCurItem != i)
      paramBoolean2 = true; 
    if (this.mFirstLayout) {
      this.mCurItem = i;
      if (paramBoolean2)
        dispatchOnPageSelected(i); 
      requestLayout();
    } else {
      populate(i);
      scrollToItem(i, paramBoolean1, paramInt2, paramBoolean2);
    } 
  }
  
  OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener paramOnPageChangeListener) {
    OnPageChangeListener onPageChangeListener = this.mInternalPageChangeListener;
    this.mInternalPageChangeListener = paramOnPageChangeListener;
    return onPageChangeListener;
  }
  
  public void setOffscreenPageLimit(int paramInt) {
    int i = paramInt;
    if (paramInt < 1) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Requested offscreen page limit ");
      stringBuilder.append(paramInt);
      stringBuilder.append(" too small; defaulting to ");
      stringBuilder.append(1);
      Log.w("ViewPager", stringBuilder.toString());
      i = 1;
    } 
    if (i != this.mOffscreenPageLimit) {
      this.mOffscreenPageLimit = i;
      populate();
    } 
  }
  
  @Deprecated
  public void setOnPageChangeListener(OnPageChangeListener paramOnPageChangeListener) {
    this.mOnPageChangeListener = paramOnPageChangeListener;
  }
  
  public void setPageMargin(int paramInt) {
    int i = this.mPageMargin;
    this.mPageMargin = paramInt;
    int j = getWidth();
    recomputeScrollPosition(j, j, paramInt, i);
    requestLayout();
  }
  
  public void setPageMarginDrawable(int paramInt) {
    setPageMarginDrawable(ContextCompat.getDrawable(getContext(), paramInt));
  }
  
  public void setPageMarginDrawable(Drawable paramDrawable) {
    boolean bool;
    this.mMarginDrawable = paramDrawable;
    if (paramDrawable != null)
      refreshDrawableState(); 
    if (paramDrawable == null) {
      bool = true;
    } else {
      bool = false;
    } 
    setWillNotDraw(bool);
    invalidate();
  }
  
  public void setPageTransformer(boolean paramBoolean, PageTransformer paramPageTransformer) {
    setPageTransformer(paramBoolean, paramPageTransformer, 2);
  }
  
  public void setPageTransformer(boolean paramBoolean, PageTransformer paramPageTransformer, int paramInt) {
    boolean bool1;
    boolean bool2;
    boolean bool3;
    byte b = 1;
    if (paramPageTransformer != null) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if (this.mPageTransformer != null) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    if (bool1 != bool2) {
      bool3 = true;
    } else {
      bool3 = false;
    } 
    this.mPageTransformer = paramPageTransformer;
    setChildrenDrawingOrderEnabled(bool1);
    if (bool1) {
      if (paramBoolean)
        b = 2; 
      this.mDrawingOrder = b;
      this.mPageTransformerLayerType = paramInt;
    } else {
      this.mDrawingOrder = 0;
    } 
    if (bool3)
      populate(); 
  }
  
  void setScrollState(int paramInt) {
    if (this.mScrollState == paramInt)
      return; 
    this.mScrollState = paramInt;
    if (this.mPageTransformer != null) {
      boolean bool;
      if (paramInt != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      enableLayers(bool);
    } 
    dispatchOnScrollStateChanged(paramInt);
  }
  
  void smoothScrollTo(int paramInt1, int paramInt2) {
    smoothScrollTo(paramInt1, paramInt2, 0);
  }
  
  void smoothScrollTo(int paramInt1, int paramInt2, int paramInt3) {
    int i;
    if (getChildCount() == 0) {
      setScrollingCacheEnabled(false);
      return;
    } 
    Scroller scroller = this.mScroller;
    if (scroller != null && !scroller.isFinished()) {
      i = 1;
    } else {
      i = 0;
    } 
    if (i) {
      if (this.mIsScrollStarted) {
        i = this.mScroller.getCurrX();
      } else {
        i = this.mScroller.getStartX();
      } 
      this.mScroller.abortAnimation();
      setScrollingCacheEnabled(false);
    } else {
      i = getScrollX();
    } 
    int j = getScrollY();
    int k = paramInt1 - i;
    paramInt2 -= j;
    if (k == 0 && paramInt2 == 0) {
      completeScroll(false);
      populate();
      setScrollState(0);
      return;
    } 
    setScrollingCacheEnabled(true);
    setScrollState(2);
    paramInt1 = getClientWidth();
    int m = paramInt1 / 2;
    float f1 = Math.abs(k);
    float f2 = paramInt1;
    float f3 = Math.min(1.0F, f1 * 1.0F / f2);
    f1 = m;
    f3 = distanceInfluenceForSnapDuration(f3);
    paramInt1 = Math.abs(paramInt3);
    if (paramInt1 > 0) {
      paramInt1 = Math.round(Math.abs((f1 + f3 * f1) / paramInt1) * 1000.0F) * 4;
    } else {
      f1 = this.mAdapter.getPageWidth(this.mCurItem);
      paramInt1 = (int)((Math.abs(k) / (f2 * f1 + this.mPageMargin) + 1.0F) * 100.0F);
    } 
    paramInt1 = Math.min(paramInt1, 600);
    this.mIsScrollStarted = false;
    this.mScroller.startScroll(i, j, k, paramInt2, paramInt1);
    ViewCompat.postInvalidateOnAnimation((View)this);
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable) {
    return (super.verifyDrawable(paramDrawable) || paramDrawable == this.mMarginDrawable);
  }
  
  @Inherited
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.TYPE})
  public static @interface DecorView {}
  
  static class ItemInfo {
    Object object;
    
    float offset;
    
    int position;
    
    boolean scrolling;
    
    float widthFactor;
  }
  
  public static class LayoutParams extends ViewGroup.LayoutParams {
    int childIndex;
    
    public int gravity;
    
    public boolean isDecor;
    
    boolean needsMeasure;
    
    int position;
    
    float widthFactor = 0.0F;
    
    public LayoutParams() {
      super(-1, -1);
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, ViewPager.LAYOUT_ATTRS);
      this.gravity = typedArray.getInteger(0, 48);
      typedArray.recycle();
    }
  }
  
  class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
    private boolean canScroll() {
      PagerAdapter pagerAdapter = ViewPager.this.mAdapter;
      boolean bool = true;
      if (pagerAdapter == null || ViewPager.this.mAdapter.getCount() <= 1)
        bool = false; 
      return bool;
    }
    
    public void onInitializeAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      super.onInitializeAccessibilityEvent(param1View, param1AccessibilityEvent);
      param1AccessibilityEvent.setClassName(ViewPager.class.getName());
      param1AccessibilityEvent.setScrollable(canScroll());
      if (param1AccessibilityEvent.getEventType() == 4096 && ViewPager.this.mAdapter != null) {
        param1AccessibilityEvent.setItemCount(ViewPager.this.mAdapter.getCount());
        param1AccessibilityEvent.setFromIndex(ViewPager.this.mCurItem);
        param1AccessibilityEvent.setToIndex(ViewPager.this.mCurItem);
      } 
    }
    
    public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
      super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfoCompat);
      param1AccessibilityNodeInfoCompat.setClassName(ViewPager.class.getName());
      param1AccessibilityNodeInfoCompat.setScrollable(canScroll());
      if (ViewPager.this.canScrollHorizontally(1))
        param1AccessibilityNodeInfoCompat.addAction(4096); 
      if (ViewPager.this.canScrollHorizontally(-1))
        param1AccessibilityNodeInfoCompat.addAction(8192); 
    }
    
    public boolean performAccessibilityAction(View param1View, int param1Int, Bundle param1Bundle) {
      if (super.performAccessibilityAction(param1View, param1Int, param1Bundle))
        return true; 
      if (param1Int != 4096) {
        if (param1Int != 8192)
          return false; 
        if (ViewPager.this.canScrollHorizontally(-1)) {
          ViewPager viewPager = ViewPager.this;
          viewPager.setCurrentItem(viewPager.mCurItem - 1);
          return true;
        } 
        return false;
      } 
      if (ViewPager.this.canScrollHorizontally(1)) {
        ViewPager viewPager = ViewPager.this;
        viewPager.setCurrentItem(viewPager.mCurItem + 1);
        return true;
      } 
      return false;
    }
  }
  
  public static interface OnAdapterChangeListener {
    void onAdapterChanged(ViewPager param1ViewPager, PagerAdapter param1PagerAdapter1, PagerAdapter param1PagerAdapter2);
  }
  
  public static interface OnPageChangeListener {
    void onPageScrollStateChanged(int param1Int);
    
    void onPageScrolled(int param1Int1, float param1Float, int param1Int2);
    
    void onPageSelected(int param1Int);
  }
  
  public static interface PageTransformer {
    void transformPage(View param1View, float param1Float);
  }
  
  private class PagerObserver extends DataSetObserver {
    public void onChanged() {
      ViewPager.this.dataSetChanged();
    }
    
    public void onInvalidated() {
      ViewPager.this.dataSetChanged();
    }
  }
  
  public static class SavedState extends AbsSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR = (Parcelable.Creator<SavedState>)new Parcelable.ClassLoaderCreator<SavedState>() {
        public ViewPager.SavedState createFromParcel(Parcel param2Parcel) {
          return new ViewPager.SavedState(param2Parcel, null);
        }
        
        public ViewPager.SavedState createFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
          return new ViewPager.SavedState(param2Parcel, param2ClassLoader);
        }
        
        public ViewPager.SavedState[] newArray(int param2Int) {
          return new ViewPager.SavedState[param2Int];
        }
      };
    
    Parcelable adapterState;
    
    ClassLoader loader;
    
    int position;
    
    SavedState(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      super(param1Parcel, param1ClassLoader);
      ClassLoader classLoader = param1ClassLoader;
      if (param1ClassLoader == null)
        classLoader = getClass().getClassLoader(); 
      this.position = param1Parcel.readInt();
      this.adapterState = param1Parcel.readParcelable(classLoader);
      this.loader = classLoader;
    }
    
    public SavedState(Parcelable param1Parcelable) {
      super(param1Parcelable);
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("FragmentPager.SavedState{");
      stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
      stringBuilder.append(" position=");
      stringBuilder.append(this.position);
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      super.writeToParcel(param1Parcel, param1Int);
      param1Parcel.writeInt(this.position);
      param1Parcel.writeParcelable(this.adapterState, param1Int);
    }
  }
  
  static final class null implements Parcelable.ClassLoaderCreator<SavedState> {
    public ViewPager.SavedState createFromParcel(Parcel param1Parcel) {
      return new ViewPager.SavedState(param1Parcel, null);
    }
    
    public ViewPager.SavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return new ViewPager.SavedState(param1Parcel, param1ClassLoader);
    }
    
    public ViewPager.SavedState[] newArray(int param1Int) {
      return new ViewPager.SavedState[param1Int];
    }
  }
  
  public static class SimpleOnPageChangeListener implements OnPageChangeListener {
    public void onPageScrollStateChanged(int param1Int) {}
    
    public void onPageScrolled(int param1Int1, float param1Float, int param1Int2) {}
    
    public void onPageSelected(int param1Int) {}
  }
  
  static class ViewPositionComparator implements Comparator<View> {
    public int compare(View param1View1, View param1View2) {
      ViewPager.LayoutParams layoutParams1 = (ViewPager.LayoutParams)param1View1.getLayoutParams();
      ViewPager.LayoutParams layoutParams2 = (ViewPager.LayoutParams)param1View2.getLayoutParams();
      if (layoutParams1.isDecor != layoutParams2.isDecor) {
        byte b;
        if (layoutParams1.isDecor) {
          b = 1;
        } else {
          b = -1;
        } 
        return b;
      } 
      return layoutParams1.position - layoutParams2.position;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\ViewPager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */