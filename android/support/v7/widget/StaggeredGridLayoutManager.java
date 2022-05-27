package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class StaggeredGridLayoutManager extends RecyclerView.LayoutManager implements RecyclerView.SmoothScroller.ScrollVectorProvider {
  static final boolean DEBUG = false;
  
  @Deprecated
  public static final int GAP_HANDLING_LAZY = 1;
  
  public static final int GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS = 2;
  
  public static final int GAP_HANDLING_NONE = 0;
  
  public static final int HORIZONTAL = 0;
  
  static final int INVALID_OFFSET = -2147483648;
  
  private static final float MAX_SCROLL_FACTOR = 0.33333334F;
  
  private static final String TAG = "StaggeredGridLManager";
  
  public static final int VERTICAL = 1;
  
  private final AnchorInfo mAnchorInfo = new AnchorInfo();
  
  private final Runnable mCheckForGapsRunnable = new Runnable() {
      public void run() {
        StaggeredGridLayoutManager.this.checkForGaps();
      }
    };
  
  private int mFullSizeSpec;
  
  private int mGapStrategy = 2;
  
  private boolean mLaidOutInvalidFullSpan = false;
  
  private boolean mLastLayoutFromEnd;
  
  private boolean mLastLayoutRTL;
  
  private final LayoutState mLayoutState;
  
  LazySpanLookup mLazySpanLookup = new LazySpanLookup();
  
  private int mOrientation;
  
  private SavedState mPendingSavedState;
  
  int mPendingScrollPosition = -1;
  
  int mPendingScrollPositionOffset = Integer.MIN_VALUE;
  
  private int[] mPrefetchDistances;
  
  OrientationHelper mPrimaryOrientation;
  
  private BitSet mRemainingSpans;
  
  boolean mReverseLayout = false;
  
  OrientationHelper mSecondaryOrientation;
  
  boolean mShouldReverseLayout = false;
  
  private int mSizePerSpan;
  
  private boolean mSmoothScrollbarEnabled = true;
  
  private int mSpanCount = -1;
  
  Span[] mSpans;
  
  private final Rect mTmpRect = new Rect();
  
  public StaggeredGridLayoutManager(int paramInt1, int paramInt2) {
    this.mOrientation = paramInt2;
    setSpanCount(paramInt1);
    this.mLayoutState = new LayoutState();
    createOrientationHelpers();
  }
  
  public StaggeredGridLayoutManager(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    RecyclerView.LayoutManager.Properties properties = getProperties(paramContext, paramAttributeSet, paramInt1, paramInt2);
    setOrientation(properties.orientation);
    setSpanCount(properties.spanCount);
    setReverseLayout(properties.reverseLayout);
    this.mLayoutState = new LayoutState();
    createOrientationHelpers();
  }
  
  private void appendViewToAllSpans(View paramView) {
    for (int i = this.mSpanCount - 1; i >= 0; i--)
      this.mSpans[i].appendToSpan(paramView); 
  }
  
  private void applyPendingSavedState(AnchorInfo paramAnchorInfo) {
    if (this.mPendingSavedState.mSpanOffsetsSize > 0)
      if (this.mPendingSavedState.mSpanOffsetsSize == this.mSpanCount) {
        for (byte b = 0; b < this.mSpanCount; b++) {
          this.mSpans[b].clear();
          int i = this.mPendingSavedState.mSpanOffsets[b];
          int j = i;
          if (i != Integer.MIN_VALUE) {
            if (this.mPendingSavedState.mAnchorLayoutFromEnd) {
              j = this.mPrimaryOrientation.getEndAfterPadding();
            } else {
              j = this.mPrimaryOrientation.getStartAfterPadding();
            } 
            j = i + j;
          } 
          this.mSpans[b].setLine(j);
        } 
      } else {
        this.mPendingSavedState.invalidateSpanInfo();
        SavedState savedState = this.mPendingSavedState;
        savedState.mAnchorPosition = savedState.mVisibleAnchorPosition;
      }  
    this.mLastLayoutRTL = this.mPendingSavedState.mLastLayoutRTL;
    setReverseLayout(this.mPendingSavedState.mReverseLayout);
    resolveShouldLayoutReverse();
    if (this.mPendingSavedState.mAnchorPosition != -1) {
      this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
      paramAnchorInfo.mLayoutFromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd;
    } else {
      paramAnchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
    } 
    if (this.mPendingSavedState.mSpanLookupSize > 1) {
      this.mLazySpanLookup.mData = this.mPendingSavedState.mSpanLookup;
      this.mLazySpanLookup.mFullSpanItems = this.mPendingSavedState.mFullSpanItems;
    } 
  }
  
  private void attachViewToSpans(View paramView, LayoutParams paramLayoutParams, LayoutState paramLayoutState) {
    if (paramLayoutState.mLayoutDirection == 1) {
      if (paramLayoutParams.mFullSpan) {
        appendViewToAllSpans(paramView);
      } else {
        paramLayoutParams.mSpan.appendToSpan(paramView);
      } 
    } else if (paramLayoutParams.mFullSpan) {
      prependViewToAllSpans(paramView);
    } else {
      paramLayoutParams.mSpan.prependToSpan(paramView);
    } 
  }
  
  private int calculateScrollDirectionForPosition(int paramInt) {
    boolean bool;
    int i = getChildCount();
    byte b = -1;
    if (i == 0) {
      if (this.mShouldReverseLayout)
        b = 1; 
      return b;
    } 
    if (paramInt < getFirstChildPosition()) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool == this.mShouldReverseLayout)
      b = 1; 
    return b;
  }
  
  private boolean checkSpanForGap(Span paramSpan) {
    if (this.mShouldReverseLayout) {
      if (paramSpan.getEndLine() < this.mPrimaryOrientation.getEndAfterPadding())
        return (paramSpan.getLayoutParams((View)paramSpan.mViews.get(paramSpan.mViews.size() - 1))).mFullSpan ^ true; 
    } else if (paramSpan.getStartLine() > this.mPrimaryOrientation.getStartAfterPadding()) {
      return (paramSpan.getLayoutParams((View)paramSpan.mViews.get(0))).mFullSpan ^ true;
    } 
    return false;
  }
  
  private int computeScrollExtent(RecyclerView.State paramState) {
    return (getChildCount() == 0) ? 0 : ScrollbarHelper.computeScrollExtent(paramState, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ true), findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ true), this, this.mSmoothScrollbarEnabled);
  }
  
  private int computeScrollOffset(RecyclerView.State paramState) {
    return (getChildCount() == 0) ? 0 : ScrollbarHelper.computeScrollOffset(paramState, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ true), findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
  }
  
  private int computeScrollRange(RecyclerView.State paramState) {
    return (getChildCount() == 0) ? 0 : ScrollbarHelper.computeScrollRange(paramState, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ true), findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ true), this, this.mSmoothScrollbarEnabled);
  }
  
  private int convertFocusDirectionToLayoutDirection(int paramInt) {
    int i = -1;
    boolean bool1 = true;
    boolean bool2 = true;
    if (paramInt != 1) {
      if (paramInt != 2) {
        if (paramInt != 17) {
          if (paramInt != 33) {
            if (paramInt != 66) {
              if (paramInt != 130)
                return Integer.MIN_VALUE; 
              if (this.mOrientation == 1) {
                paramInt = bool2;
              } else {
                paramInt = Integer.MIN_VALUE;
              } 
              return paramInt;
            } 
            if (this.mOrientation == 0) {
              paramInt = bool1;
            } else {
              paramInt = Integer.MIN_VALUE;
            } 
            return paramInt;
          } 
          if (this.mOrientation != 1)
            i = Integer.MIN_VALUE; 
          return i;
        } 
        if (this.mOrientation != 0)
          i = Integer.MIN_VALUE; 
        return i;
      } 
      return (this.mOrientation == 1) ? 1 : (isLayoutRTL() ? -1 : 1);
    } 
    return (this.mOrientation == 1) ? -1 : (isLayoutRTL() ? 1 : -1);
  }
  
  private LazySpanLookup.FullSpanItem createFullSpanItemFromEnd(int paramInt) {
    LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
    fullSpanItem.mGapPerSpan = new int[this.mSpanCount];
    for (byte b = 0; b < this.mSpanCount; b++)
      fullSpanItem.mGapPerSpan[b] = paramInt - this.mSpans[b].getEndLine(paramInt); 
    return fullSpanItem;
  }
  
  private LazySpanLookup.FullSpanItem createFullSpanItemFromStart(int paramInt) {
    LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
    fullSpanItem.mGapPerSpan = new int[this.mSpanCount];
    for (byte b = 0; b < this.mSpanCount; b++)
      fullSpanItem.mGapPerSpan[b] = this.mSpans[b].getStartLine(paramInt) - paramInt; 
    return fullSpanItem;
  }
  
  private void createOrientationHelpers() {
    this.mPrimaryOrientation = OrientationHelper.createOrientationHelper(this, this.mOrientation);
    this.mSecondaryOrientation = OrientationHelper.createOrientationHelper(this, 1 - this.mOrientation);
  }
  
  private int fill(RecyclerView.Recycler paramRecycler, LayoutState paramLayoutState, RecyclerView.State paramState) {
    int i;
    int j;
    this.mRemainingSpans.set(0, this.mSpanCount, true);
    if (this.mLayoutState.mInfinite) {
      if (paramLayoutState.mLayoutDirection == 1) {
        i = Integer.MAX_VALUE;
      } else {
        i = Integer.MIN_VALUE;
      } 
    } else if (paramLayoutState.mLayoutDirection == 1) {
      i = paramLayoutState.mEndLine + paramLayoutState.mAvailable;
    } else {
      i = paramLayoutState.mStartLine - paramLayoutState.mAvailable;
    } 
    updateAllRemainingSpans(paramLayoutState.mLayoutDirection, i);
    if (this.mShouldReverseLayout) {
      j = this.mPrimaryOrientation.getEndAfterPadding();
    } else {
      j = this.mPrimaryOrientation.getStartAfterPadding();
    } 
    int k;
    for (k = 0; paramLayoutState.hasMore(paramState) && (this.mLayoutState.mInfinite || !this.mRemainingSpans.isEmpty()); k = 1) {
      int n;
      Span span;
      int i1;
      int i2;
      View view = paramLayoutState.next(paramRecycler);
      LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
      int m = layoutParams.getViewLayoutPosition();
      k = this.mLazySpanLookup.getSpan(m);
      if (k == -1) {
        n = 1;
      } else {
        n = 0;
      } 
      if (n) {
        if (layoutParams.mFullSpan) {
          span = this.mSpans[0];
        } else {
          span = getNextSpan(paramLayoutState);
        } 
        this.mLazySpanLookup.setSpan(m, span);
      } else {
        span = this.mSpans[k];
      } 
      layoutParams.mSpan = span;
      if (paramLayoutState.mLayoutDirection == 1) {
        addView(view);
      } else {
        addView(view, 0);
      } 
      measureChildWithDecorationsAndMargin(view, layoutParams, false);
      if (paramLayoutState.mLayoutDirection == 1) {
        if (layoutParams.mFullSpan) {
          k = getMaxEnd(j);
        } else {
          k = span.getEndLine(j);
        } 
        i1 = this.mPrimaryOrientation.getDecoratedMeasurement(view);
        if (n && layoutParams.mFullSpan) {
          LazySpanLookup.FullSpanItem fullSpanItem = createFullSpanItemFromEnd(k);
          fullSpanItem.mGapDir = -1;
          fullSpanItem.mPosition = m;
          this.mLazySpanLookup.addFullSpanItem(fullSpanItem);
        } 
        i2 = i1 + k;
        i1 = k;
      } else {
        if (layoutParams.mFullSpan) {
          k = getMinStart(j);
        } else {
          k = span.getStartLine(j);
        } 
        i1 = k - this.mPrimaryOrientation.getDecoratedMeasurement(view);
        if (n && layoutParams.mFullSpan) {
          LazySpanLookup.FullSpanItem fullSpanItem = createFullSpanItemFromStart(k);
          fullSpanItem.mGapDir = 1;
          fullSpanItem.mPosition = m;
          this.mLazySpanLookup.addFullSpanItem(fullSpanItem);
        } 
        i2 = k;
      } 
      if (layoutParams.mFullSpan && paramLayoutState.mItemDirection == -1)
        if (n) {
          this.mLaidOutInvalidFullSpan = true;
        } else {
          boolean bool;
          if (paramLayoutState.mLayoutDirection == 1) {
            bool = areAllEndsEqual();
          } else {
            bool = areAllStartsEqual();
          } 
          if ((bool ^ true) != 0) {
            LazySpanLookup.FullSpanItem fullSpanItem = this.mLazySpanLookup.getFullSpanItem(m);
            if (fullSpanItem != null)
              fullSpanItem.mHasUnwantedGapAfter = true; 
            this.mLaidOutInvalidFullSpan = true;
          } 
        }  
      attachViewToSpans(view, layoutParams, paramLayoutState);
      if (isLayoutRTL() && this.mOrientation == 1) {
        if (layoutParams.mFullSpan) {
          k = this.mSecondaryOrientation.getEndAfterPadding();
        } else {
          k = this.mSecondaryOrientation.getEndAfterPadding() - (this.mSpanCount - 1 - span.mIndex) * this.mSizePerSpan;
        } 
        m = this.mSecondaryOrientation.getDecoratedMeasurement(view);
        n = k;
        m = k - m;
        k = n;
      } else {
        if (layoutParams.mFullSpan) {
          k = this.mSecondaryOrientation.getStartAfterPadding();
        } else {
          k = span.mIndex * this.mSizePerSpan + this.mSecondaryOrientation.getStartAfterPadding();
        } 
        m = this.mSecondaryOrientation.getDecoratedMeasurement(view);
        n = k;
        k = m + k;
        m = n;
      } 
      if (this.mOrientation == 1) {
        layoutDecoratedWithMargins(view, m, i1, k, i2);
      } else {
        layoutDecoratedWithMargins(view, i1, m, i2, k);
      } 
      if (layoutParams.mFullSpan) {
        updateAllRemainingSpans(this.mLayoutState.mLayoutDirection, i);
      } else {
        updateRemainingSpans(span, this.mLayoutState.mLayoutDirection, i);
      } 
      recycle(paramRecycler, this.mLayoutState);
      if (this.mLayoutState.mStopInFocusable && view.hasFocusable())
        if (layoutParams.mFullSpan) {
          this.mRemainingSpans.clear();
        } else {
          this.mRemainingSpans.set(span.mIndex, false);
        }  
    } 
    if (k == 0)
      recycle(paramRecycler, this.mLayoutState); 
    if (this.mLayoutState.mLayoutDirection == -1) {
      i = getMinStart(this.mPrimaryOrientation.getStartAfterPadding());
      i = this.mPrimaryOrientation.getStartAfterPadding() - i;
    } else {
      i = getMaxEnd(this.mPrimaryOrientation.getEndAfterPadding()) - this.mPrimaryOrientation.getEndAfterPadding();
    } 
    if (i > 0) {
      i = Math.min(paramLayoutState.mAvailable, i);
    } else {
      i = 0;
    } 
    return i;
  }
  
  private int findFirstReferenceChildPosition(int paramInt) {
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      int j = getPosition(getChildAt(b));
      if (j >= 0 && j < paramInt)
        return j; 
    } 
    return 0;
  }
  
  private int findLastReferenceChildPosition(int paramInt) {
    for (int i = getChildCount() - 1; i >= 0; i--) {
      int j = getPosition(getChildAt(i));
      if (j >= 0 && j < paramInt)
        return j; 
    } 
    return 0;
  }
  
  private void fixEndGap(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, boolean paramBoolean) {
    int i = getMaxEnd(-2147483648);
    if (i == Integer.MIN_VALUE)
      return; 
    i = this.mPrimaryOrientation.getEndAfterPadding() - i;
    if (i > 0) {
      i -= -scrollBy(-i, paramRecycler, paramState);
      if (paramBoolean && i > 0)
        this.mPrimaryOrientation.offsetChildren(i); 
    } 
  }
  
  private void fixStartGap(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, boolean paramBoolean) {
    int i = getMinStart(2147483647);
    if (i == Integer.MAX_VALUE)
      return; 
    i -= this.mPrimaryOrientation.getStartAfterPadding();
    if (i > 0) {
      i -= scrollBy(i, paramRecycler, paramState);
      if (paramBoolean && i > 0)
        this.mPrimaryOrientation.offsetChildren(-i); 
    } 
  }
  
  private int getMaxEnd(int paramInt) {
    int i = this.mSpans[0].getEndLine(paramInt);
    byte b = 1;
    while (b < this.mSpanCount) {
      int j = this.mSpans[b].getEndLine(paramInt);
      int k = i;
      if (j > i)
        k = j; 
      b++;
      i = k;
    } 
    return i;
  }
  
  private int getMaxStart(int paramInt) {
    int i = this.mSpans[0].getStartLine(paramInt);
    byte b = 1;
    while (b < this.mSpanCount) {
      int j = this.mSpans[b].getStartLine(paramInt);
      int k = i;
      if (j > i)
        k = j; 
      b++;
      i = k;
    } 
    return i;
  }
  
  private int getMinEnd(int paramInt) {
    int i = this.mSpans[0].getEndLine(paramInt);
    byte b = 1;
    while (b < this.mSpanCount) {
      int j = this.mSpans[b].getEndLine(paramInt);
      int k = i;
      if (j < i)
        k = j; 
      b++;
      i = k;
    } 
    return i;
  }
  
  private int getMinStart(int paramInt) {
    int i = this.mSpans[0].getStartLine(paramInt);
    byte b = 1;
    while (b < this.mSpanCount) {
      int j = this.mSpans[b].getStartLine(paramInt);
      int k = i;
      if (j < i)
        k = j; 
      b++;
      i = k;
    } 
    return i;
  }
  
  private Span getNextSpan(LayoutState paramLayoutState) {
    int j;
    byte b;
    boolean bool = preferLastSpan(paramLayoutState.mLayoutDirection);
    int i = -1;
    if (bool) {
      j = this.mSpanCount - 1;
      b = -1;
    } else {
      j = 0;
      i = this.mSpanCount;
      b = 1;
    } 
    int k = paramLayoutState.mLayoutDirection;
    Span span2 = null;
    paramLayoutState = null;
    if (k == 1) {
      Span span;
      int i1 = Integer.MAX_VALUE;
      int i2 = this.mPrimaryOrientation.getStartAfterPadding();
      while (j != i) {
        span2 = this.mSpans[j];
        int i3 = span2.getEndLine(i2);
        k = i1;
        if (i3 < i1) {
          span = span2;
          k = i3;
        } 
        j += b;
        i1 = k;
      } 
      return span;
    } 
    k = Integer.MIN_VALUE;
    int n = this.mPrimaryOrientation.getEndAfterPadding();
    Span span1 = span2;
    int m = j;
    while (m != i) {
      span2 = this.mSpans[m];
      int i1 = span2.getStartLine(n);
      j = k;
      if (i1 > k) {
        span1 = span2;
        j = i1;
      } 
      m += b;
      k = j;
    } 
    return span1;
  }
  
  private void handleUpdate(int paramInt1, int paramInt2, int paramInt3) {
    if (this.mShouldReverseLayout) {
      int j = getLastChildPosition();
    } else {
      int j = getFirstChildPosition();
    } 
    if (paramInt3 == 8) {
      if (paramInt1 < paramInt2) {
        int j = paramInt2 + 1;
      } else {
        int j = paramInt1 + 1;
        int k = paramInt2;
        this.mLazySpanLookup.invalidateAfter(k);
      } 
    } else {
      int j = paramInt1 + paramInt2;
    } 
    int i = paramInt1;
    this.mLazySpanLookup.invalidateAfter(i);
  }
  
  private void measureChildWithDecorationsAndMargin(View paramView, int paramInt1, int paramInt2, boolean paramBoolean) {
    calculateItemDecorationsForChild(paramView, this.mTmpRect);
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    paramInt1 = updateSpecWithExtra(paramInt1, layoutParams.leftMargin + this.mTmpRect.left, layoutParams.rightMargin + this.mTmpRect.right);
    paramInt2 = updateSpecWithExtra(paramInt2, layoutParams.topMargin + this.mTmpRect.top, layoutParams.bottomMargin + this.mTmpRect.bottom);
    if (paramBoolean) {
      paramBoolean = shouldReMeasureChild(paramView, paramInt1, paramInt2, layoutParams);
    } else {
      paramBoolean = shouldMeasureChild(paramView, paramInt1, paramInt2, layoutParams);
    } 
    if (paramBoolean)
      paramView.measure(paramInt1, paramInt2); 
  }
  
  private void measureChildWithDecorationsAndMargin(View paramView, LayoutParams paramLayoutParams, boolean paramBoolean) {
    if (paramLayoutParams.mFullSpan) {
      if (this.mOrientation == 1) {
        measureChildWithDecorationsAndMargin(paramView, this.mFullSizeSpec, getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom(), paramLayoutParams.height, true), paramBoolean);
      } else {
        measureChildWithDecorationsAndMargin(paramView, getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight(), paramLayoutParams.width, true), this.mFullSizeSpec, paramBoolean);
      } 
    } else if (this.mOrientation == 1) {
      measureChildWithDecorationsAndMargin(paramView, getChildMeasureSpec(this.mSizePerSpan, getWidthMode(), 0, paramLayoutParams.width, false), getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom(), paramLayoutParams.height, true), paramBoolean);
    } else {
      measureChildWithDecorationsAndMargin(paramView, getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight(), paramLayoutParams.width, true), getChildMeasureSpec(this.mSizePerSpan, getHeightMode(), 0, paramLayoutParams.height, false), paramBoolean);
    } 
  }
  
  private void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mAnchorInfo : Landroid/support/v7/widget/StaggeredGridLayoutManager$AnchorInfo;
    //   4: astore #4
    //   6: aload_0
    //   7: getfield mPendingSavedState : Landroid/support/v7/widget/StaggeredGridLayoutManager$SavedState;
    //   10: ifnonnull -> 21
    //   13: aload_0
    //   14: getfield mPendingScrollPosition : I
    //   17: iconst_m1
    //   18: if_icmpeq -> 39
    //   21: aload_2
    //   22: invokevirtual getItemCount : ()I
    //   25: ifne -> 39
    //   28: aload_0
    //   29: aload_1
    //   30: invokevirtual removeAndRecycleAllViews : (Landroid/support/v7/widget/RecyclerView$Recycler;)V
    //   33: aload #4
    //   35: invokevirtual reset : ()V
    //   38: return
    //   39: aload #4
    //   41: getfield mValid : Z
    //   44: istore #5
    //   46: iconst_1
    //   47: istore #6
    //   49: iload #5
    //   51: ifeq -> 78
    //   54: aload_0
    //   55: getfield mPendingScrollPosition : I
    //   58: iconst_m1
    //   59: if_icmpne -> 78
    //   62: aload_0
    //   63: getfield mPendingSavedState : Landroid/support/v7/widget/StaggeredGridLayoutManager$SavedState;
    //   66: ifnull -> 72
    //   69: goto -> 78
    //   72: iconst_0
    //   73: istore #7
    //   75: goto -> 81
    //   78: iconst_1
    //   79: istore #7
    //   81: iload #7
    //   83: ifeq -> 133
    //   86: aload #4
    //   88: invokevirtual reset : ()V
    //   91: aload_0
    //   92: getfield mPendingSavedState : Landroid/support/v7/widget/StaggeredGridLayoutManager$SavedState;
    //   95: ifnull -> 107
    //   98: aload_0
    //   99: aload #4
    //   101: invokespecial applyPendingSavedState : (Landroid/support/v7/widget/StaggeredGridLayoutManager$AnchorInfo;)V
    //   104: goto -> 120
    //   107: aload_0
    //   108: invokespecial resolveShouldLayoutReverse : ()V
    //   111: aload #4
    //   113: aload_0
    //   114: getfield mShouldReverseLayout : Z
    //   117: putfield mLayoutFromEnd : Z
    //   120: aload_0
    //   121: aload_2
    //   122: aload #4
    //   124: invokevirtual updateAnchorInfoForLayout : (Landroid/support/v7/widget/RecyclerView$State;Landroid/support/v7/widget/StaggeredGridLayoutManager$AnchorInfo;)V
    //   127: aload #4
    //   129: iconst_1
    //   130: putfield mValid : Z
    //   133: aload_0
    //   134: getfield mPendingSavedState : Landroid/support/v7/widget/StaggeredGridLayoutManager$SavedState;
    //   137: ifnonnull -> 184
    //   140: aload_0
    //   141: getfield mPendingScrollPosition : I
    //   144: iconst_m1
    //   145: if_icmpne -> 184
    //   148: aload #4
    //   150: getfield mLayoutFromEnd : Z
    //   153: aload_0
    //   154: getfield mLastLayoutFromEnd : Z
    //   157: if_icmpne -> 171
    //   160: aload_0
    //   161: invokevirtual isLayoutRTL : ()Z
    //   164: aload_0
    //   165: getfield mLastLayoutRTL : Z
    //   168: if_icmpeq -> 184
    //   171: aload_0
    //   172: getfield mLazySpanLookup : Landroid/support/v7/widget/StaggeredGridLayoutManager$LazySpanLookup;
    //   175: invokevirtual clear : ()V
    //   178: aload #4
    //   180: iconst_1
    //   181: putfield mInvalidateOffsets : Z
    //   184: aload_0
    //   185: invokevirtual getChildCount : ()I
    //   188: ifle -> 385
    //   191: aload_0
    //   192: getfield mPendingSavedState : Landroid/support/v7/widget/StaggeredGridLayoutManager$SavedState;
    //   195: astore #8
    //   197: aload #8
    //   199: ifnull -> 211
    //   202: aload #8
    //   204: getfield mSpanOffsetsSize : I
    //   207: iconst_1
    //   208: if_icmpge -> 385
    //   211: aload #4
    //   213: getfield mInvalidateOffsets : Z
    //   216: ifeq -> 272
    //   219: iconst_0
    //   220: istore #7
    //   222: iload #7
    //   224: aload_0
    //   225: getfield mSpanCount : I
    //   228: if_icmpge -> 385
    //   231: aload_0
    //   232: getfield mSpans : [Landroid/support/v7/widget/StaggeredGridLayoutManager$Span;
    //   235: iload #7
    //   237: aaload
    //   238: invokevirtual clear : ()V
    //   241: aload #4
    //   243: getfield mOffset : I
    //   246: ldc -2147483648
    //   248: if_icmpeq -> 266
    //   251: aload_0
    //   252: getfield mSpans : [Landroid/support/v7/widget/StaggeredGridLayoutManager$Span;
    //   255: iload #7
    //   257: aaload
    //   258: aload #4
    //   260: getfield mOffset : I
    //   263: invokevirtual setLine : (I)V
    //   266: iinc #7, 1
    //   269: goto -> 222
    //   272: iload #7
    //   274: ifne -> 337
    //   277: aload_0
    //   278: getfield mAnchorInfo : Landroid/support/v7/widget/StaggeredGridLayoutManager$AnchorInfo;
    //   281: getfield mSpanReferenceLines : [I
    //   284: ifnonnull -> 290
    //   287: goto -> 337
    //   290: iconst_0
    //   291: istore #7
    //   293: iload #7
    //   295: aload_0
    //   296: getfield mSpanCount : I
    //   299: if_icmpge -> 385
    //   302: aload_0
    //   303: getfield mSpans : [Landroid/support/v7/widget/StaggeredGridLayoutManager$Span;
    //   306: iload #7
    //   308: aaload
    //   309: astore #8
    //   311: aload #8
    //   313: invokevirtual clear : ()V
    //   316: aload #8
    //   318: aload_0
    //   319: getfield mAnchorInfo : Landroid/support/v7/widget/StaggeredGridLayoutManager$AnchorInfo;
    //   322: getfield mSpanReferenceLines : [I
    //   325: iload #7
    //   327: iaload
    //   328: invokevirtual setLine : (I)V
    //   331: iinc #7, 1
    //   334: goto -> 293
    //   337: iconst_0
    //   338: istore #7
    //   340: iload #7
    //   342: aload_0
    //   343: getfield mSpanCount : I
    //   346: if_icmpge -> 374
    //   349: aload_0
    //   350: getfield mSpans : [Landroid/support/v7/widget/StaggeredGridLayoutManager$Span;
    //   353: iload #7
    //   355: aaload
    //   356: aload_0
    //   357: getfield mShouldReverseLayout : Z
    //   360: aload #4
    //   362: getfield mOffset : I
    //   365: invokevirtual cacheReferenceLineAndClear : (ZI)V
    //   368: iinc #7, 1
    //   371: goto -> 340
    //   374: aload_0
    //   375: getfield mAnchorInfo : Landroid/support/v7/widget/StaggeredGridLayoutManager$AnchorInfo;
    //   378: aload_0
    //   379: getfield mSpans : [Landroid/support/v7/widget/StaggeredGridLayoutManager$Span;
    //   382: invokevirtual saveSpanReferenceLines : ([Landroid/support/v7/widget/StaggeredGridLayoutManager$Span;)V
    //   385: aload_0
    //   386: aload_1
    //   387: invokevirtual detachAndScrapAttachedViews : (Landroid/support/v7/widget/RecyclerView$Recycler;)V
    //   390: aload_0
    //   391: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   394: iconst_0
    //   395: putfield mRecycle : Z
    //   398: aload_0
    //   399: iconst_0
    //   400: putfield mLaidOutInvalidFullSpan : Z
    //   403: aload_0
    //   404: aload_0
    //   405: getfield mSecondaryOrientation : Landroid/support/v7/widget/OrientationHelper;
    //   408: invokevirtual getTotalSpace : ()I
    //   411: invokevirtual updateMeasureSpecs : (I)V
    //   414: aload_0
    //   415: aload #4
    //   417: getfield mPosition : I
    //   420: aload_2
    //   421: invokespecial updateLayoutState : (ILandroid/support/v7/widget/RecyclerView$State;)V
    //   424: aload #4
    //   426: getfield mLayoutFromEnd : Z
    //   429: ifeq -> 487
    //   432: aload_0
    //   433: iconst_m1
    //   434: invokespecial setLayoutStateDirection : (I)V
    //   437: aload_0
    //   438: aload_1
    //   439: aload_0
    //   440: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   443: aload_2
    //   444: invokespecial fill : (Landroid/support/v7/widget/RecyclerView$Recycler;Landroid/support/v7/widget/LayoutState;Landroid/support/v7/widget/RecyclerView$State;)I
    //   447: pop
    //   448: aload_0
    //   449: iconst_1
    //   450: invokespecial setLayoutStateDirection : (I)V
    //   453: aload_0
    //   454: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   457: aload #4
    //   459: getfield mPosition : I
    //   462: aload_0
    //   463: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   466: getfield mItemDirection : I
    //   469: iadd
    //   470: putfield mCurrentPosition : I
    //   473: aload_0
    //   474: aload_1
    //   475: aload_0
    //   476: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   479: aload_2
    //   480: invokespecial fill : (Landroid/support/v7/widget/RecyclerView$Recycler;Landroid/support/v7/widget/LayoutState;Landroid/support/v7/widget/RecyclerView$State;)I
    //   483: pop
    //   484: goto -> 539
    //   487: aload_0
    //   488: iconst_1
    //   489: invokespecial setLayoutStateDirection : (I)V
    //   492: aload_0
    //   493: aload_1
    //   494: aload_0
    //   495: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   498: aload_2
    //   499: invokespecial fill : (Landroid/support/v7/widget/RecyclerView$Recycler;Landroid/support/v7/widget/LayoutState;Landroid/support/v7/widget/RecyclerView$State;)I
    //   502: pop
    //   503: aload_0
    //   504: iconst_m1
    //   505: invokespecial setLayoutStateDirection : (I)V
    //   508: aload_0
    //   509: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   512: aload #4
    //   514: getfield mPosition : I
    //   517: aload_0
    //   518: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   521: getfield mItemDirection : I
    //   524: iadd
    //   525: putfield mCurrentPosition : I
    //   528: aload_0
    //   529: aload_1
    //   530: aload_0
    //   531: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   534: aload_2
    //   535: invokespecial fill : (Landroid/support/v7/widget/RecyclerView$Recycler;Landroid/support/v7/widget/LayoutState;Landroid/support/v7/widget/RecyclerView$State;)I
    //   538: pop
    //   539: aload_0
    //   540: invokespecial repositionToWrapContentIfNecessary : ()V
    //   543: aload_0
    //   544: invokevirtual getChildCount : ()I
    //   547: ifle -> 588
    //   550: aload_0
    //   551: getfield mShouldReverseLayout : Z
    //   554: ifeq -> 574
    //   557: aload_0
    //   558: aload_1
    //   559: aload_2
    //   560: iconst_1
    //   561: invokespecial fixEndGap : (Landroid/support/v7/widget/RecyclerView$Recycler;Landroid/support/v7/widget/RecyclerView$State;Z)V
    //   564: aload_0
    //   565: aload_1
    //   566: aload_2
    //   567: iconst_0
    //   568: invokespecial fixStartGap : (Landroid/support/v7/widget/RecyclerView$Recycler;Landroid/support/v7/widget/RecyclerView$State;Z)V
    //   571: goto -> 588
    //   574: aload_0
    //   575: aload_1
    //   576: aload_2
    //   577: iconst_1
    //   578: invokespecial fixStartGap : (Landroid/support/v7/widget/RecyclerView$Recycler;Landroid/support/v7/widget/RecyclerView$State;Z)V
    //   581: aload_0
    //   582: aload_1
    //   583: aload_2
    //   584: iconst_0
    //   585: invokespecial fixEndGap : (Landroid/support/v7/widget/RecyclerView$Recycler;Landroid/support/v7/widget/RecyclerView$State;Z)V
    //   588: iload_3
    //   589: ifeq -> 664
    //   592: aload_2
    //   593: invokevirtual isPreLayout : ()Z
    //   596: ifne -> 664
    //   599: aload_0
    //   600: getfield mGapStrategy : I
    //   603: ifeq -> 633
    //   606: aload_0
    //   607: invokevirtual getChildCount : ()I
    //   610: ifle -> 633
    //   613: aload_0
    //   614: getfield mLaidOutInvalidFullSpan : Z
    //   617: ifne -> 627
    //   620: aload_0
    //   621: invokevirtual hasGapsToFix : ()Landroid/view/View;
    //   624: ifnull -> 633
    //   627: iconst_1
    //   628: istore #7
    //   630: goto -> 636
    //   633: iconst_0
    //   634: istore #7
    //   636: iload #7
    //   638: ifeq -> 664
    //   641: aload_0
    //   642: aload_0
    //   643: getfield mCheckForGapsRunnable : Ljava/lang/Runnable;
    //   646: invokevirtual removeCallbacks : (Ljava/lang/Runnable;)Z
    //   649: pop
    //   650: aload_0
    //   651: invokevirtual checkForGaps : ()Z
    //   654: ifeq -> 664
    //   657: iload #6
    //   659: istore #7
    //   661: goto -> 667
    //   664: iconst_0
    //   665: istore #7
    //   667: aload_2
    //   668: invokevirtual isPreLayout : ()Z
    //   671: ifeq -> 681
    //   674: aload_0
    //   675: getfield mAnchorInfo : Landroid/support/v7/widget/StaggeredGridLayoutManager$AnchorInfo;
    //   678: invokevirtual reset : ()V
    //   681: aload_0
    //   682: aload #4
    //   684: getfield mLayoutFromEnd : Z
    //   687: putfield mLastLayoutFromEnd : Z
    //   690: aload_0
    //   691: aload_0
    //   692: invokevirtual isLayoutRTL : ()Z
    //   695: putfield mLastLayoutRTL : Z
    //   698: iload #7
    //   700: ifeq -> 717
    //   703: aload_0
    //   704: getfield mAnchorInfo : Landroid/support/v7/widget/StaggeredGridLayoutManager$AnchorInfo;
    //   707: invokevirtual reset : ()V
    //   710: aload_0
    //   711: aload_1
    //   712: aload_2
    //   713: iconst_0
    //   714: invokespecial onLayoutChildren : (Landroid/support/v7/widget/RecyclerView$Recycler;Landroid/support/v7/widget/RecyclerView$State;Z)V
    //   717: return
  }
  
  private boolean preferLastSpan(int paramInt) {
    boolean bool;
    int i = this.mOrientation;
    boolean bool1 = true;
    boolean bool2 = true;
    if (i == 0) {
      if (paramInt == -1) {
        bool = true;
      } else {
        bool = false;
      } 
      if (bool != this.mShouldReverseLayout) {
        bool = bool2;
      } else {
        bool = false;
      } 
      return bool;
    } 
    if (paramInt == -1) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool == this.mShouldReverseLayout) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool == isLayoutRTL()) {
      bool = bool1;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private void prependViewToAllSpans(View paramView) {
    for (int i = this.mSpanCount - 1; i >= 0; i--)
      this.mSpans[i].prependToSpan(paramView); 
  }
  
  private void recycle(RecyclerView.Recycler paramRecycler, LayoutState paramLayoutState) {
    if (paramLayoutState.mRecycle && !paramLayoutState.mInfinite)
      if (paramLayoutState.mAvailable == 0) {
        if (paramLayoutState.mLayoutDirection == -1) {
          recycleFromEnd(paramRecycler, paramLayoutState.mEndLine);
        } else {
          recycleFromStart(paramRecycler, paramLayoutState.mStartLine);
        } 
      } else if (paramLayoutState.mLayoutDirection == -1) {
        int i = paramLayoutState.mStartLine - getMaxStart(paramLayoutState.mStartLine);
        if (i < 0) {
          i = paramLayoutState.mEndLine;
        } else {
          i = paramLayoutState.mEndLine - Math.min(i, paramLayoutState.mAvailable);
        } 
        recycleFromEnd(paramRecycler, i);
      } else {
        int i;
        int j = getMinEnd(paramLayoutState.mEndLine) - paramLayoutState.mEndLine;
        if (j < 0) {
          i = paramLayoutState.mStartLine;
        } else {
          i = paramLayoutState.mStartLine;
          i = Math.min(j, paramLayoutState.mAvailable) + i;
        } 
        recycleFromStart(paramRecycler, i);
      }  
  }
  
  private void recycleFromEnd(RecyclerView.Recycler paramRecycler, int paramInt) {
    int i = getChildCount() - 1;
    while (i >= 0) {
      View view = getChildAt(i);
      if (this.mPrimaryOrientation.getDecoratedStart(view) >= paramInt && this.mPrimaryOrientation.getTransformedStartWithDecoration(view) >= paramInt) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.mFullSpan) {
          byte b3;
          byte b1 = 0;
          byte b2 = 0;
          while (true) {
            b3 = b1;
            if (b2 < this.mSpanCount) {
              if ((this.mSpans[b2]).mViews.size() == 1)
                return; 
              b2++;
              continue;
            } 
            break;
          } 
          while (b3 < this.mSpanCount) {
            this.mSpans[b3].popEnd();
            b3++;
          } 
        } else {
          if (layoutParams.mSpan.mViews.size() == 1)
            return; 
          layoutParams.mSpan.popEnd();
        } 
        removeAndRecycleView(view, paramRecycler);
        i--;
      } 
    } 
  }
  
  private void recycleFromStart(RecyclerView.Recycler paramRecycler, int paramInt) {
    while (getChildCount() > 0) {
      byte b = 0;
      View view = getChildAt(0);
      if (this.mPrimaryOrientation.getDecoratedEnd(view) <= paramInt && this.mPrimaryOrientation.getTransformedEndWithDecoration(view) <= paramInt) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.mFullSpan) {
          byte b2;
          byte b1 = 0;
          while (true) {
            b2 = b;
            if (b1 < this.mSpanCount) {
              if ((this.mSpans[b1]).mViews.size() == 1)
                return; 
              b1++;
              continue;
            } 
            break;
          } 
          while (b2 < this.mSpanCount) {
            this.mSpans[b2].popStart();
            b2++;
          } 
        } else {
          if (layoutParams.mSpan.mViews.size() == 1)
            return; 
          layoutParams.mSpan.popStart();
        } 
        removeAndRecycleView(view, paramRecycler);
      } 
    } 
  }
  
  private void repositionToWrapContentIfNecessary() {
    if (this.mSecondaryOrientation.getMode() == 1073741824)
      return; 
    float f = 0.0F;
    int i = getChildCount();
    int j = 0;
    int k;
    for (k = 0; k < i; k++) {
      View view = getChildAt(k);
      float f1 = this.mSecondaryOrientation.getDecoratedMeasurement(view);
      if (f1 >= f) {
        float f2 = f1;
        if (((LayoutParams)view.getLayoutParams()).isFullSpan())
          f2 = f1 * 1.0F / this.mSpanCount; 
        f = Math.max(f, f2);
      } 
    } 
    int m = this.mSizePerSpan;
    int n = Math.round(f * this.mSpanCount);
    k = n;
    if (this.mSecondaryOrientation.getMode() == Integer.MIN_VALUE)
      k = Math.min(n, this.mSecondaryOrientation.getTotalSpace()); 
    updateMeasureSpecs(k);
    k = j;
    if (this.mSizePerSpan == m)
      return; 
    while (k < i) {
      View view = getChildAt(k);
      LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
      if (!layoutParams.mFullSpan)
        if (isLayoutRTL() && this.mOrientation == 1) {
          view.offsetLeftAndRight(-(this.mSpanCount - 1 - layoutParams.mSpan.mIndex) * this.mSizePerSpan - -(this.mSpanCount - 1 - layoutParams.mSpan.mIndex) * m);
        } else {
          j = layoutParams.mSpan.mIndex * this.mSizePerSpan;
          n = layoutParams.mSpan.mIndex * m;
          if (this.mOrientation == 1) {
            view.offsetLeftAndRight(j - n);
          } else {
            view.offsetTopAndBottom(j - n);
          } 
        }  
      k++;
    } 
  }
  
  private void resolveShouldLayoutReverse() {
    if (this.mOrientation == 1 || !isLayoutRTL()) {
      this.mShouldReverseLayout = this.mReverseLayout;
      return;
    } 
    this.mShouldReverseLayout = this.mReverseLayout ^ true;
  }
  
  private void setLayoutStateDirection(int paramInt) {
    boolean bool2;
    this.mLayoutState.mLayoutDirection = paramInt;
    LayoutState layoutState = this.mLayoutState;
    boolean bool1 = this.mShouldReverseLayout;
    boolean bool = true;
    if (paramInt == -1) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    if (bool1 == bool2) {
      paramInt = bool;
    } else {
      paramInt = -1;
    } 
    layoutState.mItemDirection = paramInt;
  }
  
  private void updateAllRemainingSpans(int paramInt1, int paramInt2) {
    for (byte b = 0; b < this.mSpanCount; b++) {
      if (!(this.mSpans[b]).mViews.isEmpty())
        updateRemainingSpans(this.mSpans[b], paramInt1, paramInt2); 
    } 
  }
  
  private boolean updateAnchorFromChildren(RecyclerView.State paramState, AnchorInfo paramAnchorInfo) {
    int i;
    if (this.mLastLayoutFromEnd) {
      i = findLastReferenceChildPosition(paramState.getItemCount());
    } else {
      i = findFirstReferenceChildPosition(paramState.getItemCount());
    } 
    paramAnchorInfo.mPosition = i;
    paramAnchorInfo.mOffset = Integer.MIN_VALUE;
    return true;
  }
  
  private void updateLayoutState(int paramInt, RecyclerView.State paramState) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   4: astore_3
    //   5: iconst_0
    //   6: istore #4
    //   8: aload_3
    //   9: iconst_0
    //   10: putfield mAvailable : I
    //   13: aload_0
    //   14: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   17: iload_1
    //   18: putfield mCurrentPosition : I
    //   21: aload_0
    //   22: invokevirtual isSmoothScrolling : ()Z
    //   25: ifeq -> 93
    //   28: aload_2
    //   29: invokevirtual getTargetScrollPosition : ()I
    //   32: istore #5
    //   34: iload #5
    //   36: iconst_m1
    //   37: if_icmpeq -> 93
    //   40: aload_0
    //   41: getfield mShouldReverseLayout : Z
    //   44: istore #6
    //   46: iload #5
    //   48: iload_1
    //   49: if_icmpge -> 58
    //   52: iconst_1
    //   53: istore #7
    //   55: goto -> 61
    //   58: iconst_0
    //   59: istore #7
    //   61: iload #6
    //   63: iload #7
    //   65: if_icmpne -> 79
    //   68: aload_0
    //   69: getfield mPrimaryOrientation : Landroid/support/v7/widget/OrientationHelper;
    //   72: invokevirtual getTotalSpace : ()I
    //   75: istore_1
    //   76: goto -> 95
    //   79: aload_0
    //   80: getfield mPrimaryOrientation : Landroid/support/v7/widget/OrientationHelper;
    //   83: invokevirtual getTotalSpace : ()I
    //   86: istore #5
    //   88: iconst_0
    //   89: istore_1
    //   90: goto -> 98
    //   93: iconst_0
    //   94: istore_1
    //   95: iconst_0
    //   96: istore #5
    //   98: aload_0
    //   99: invokevirtual getClipToPadding : ()Z
    //   102: ifeq -> 141
    //   105: aload_0
    //   106: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   109: aload_0
    //   110: getfield mPrimaryOrientation : Landroid/support/v7/widget/OrientationHelper;
    //   113: invokevirtual getStartAfterPadding : ()I
    //   116: iload #5
    //   118: isub
    //   119: putfield mStartLine : I
    //   122: aload_0
    //   123: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   126: aload_0
    //   127: getfield mPrimaryOrientation : Landroid/support/v7/widget/OrientationHelper;
    //   130: invokevirtual getEndAfterPadding : ()I
    //   133: iload_1
    //   134: iadd
    //   135: putfield mEndLine : I
    //   138: goto -> 167
    //   141: aload_0
    //   142: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   145: aload_0
    //   146: getfield mPrimaryOrientation : Landroid/support/v7/widget/OrientationHelper;
    //   149: invokevirtual getEnd : ()I
    //   152: iload_1
    //   153: iadd
    //   154: putfield mEndLine : I
    //   157: aload_0
    //   158: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   161: iload #5
    //   163: ineg
    //   164: putfield mStartLine : I
    //   167: aload_0
    //   168: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   171: iconst_0
    //   172: putfield mStopInFocusable : Z
    //   175: aload_0
    //   176: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   179: iconst_1
    //   180: putfield mRecycle : Z
    //   183: aload_0
    //   184: getfield mLayoutState : Landroid/support/v7/widget/LayoutState;
    //   187: astore_2
    //   188: iload #4
    //   190: istore #7
    //   192: aload_0
    //   193: getfield mPrimaryOrientation : Landroid/support/v7/widget/OrientationHelper;
    //   196: invokevirtual getMode : ()I
    //   199: ifne -> 219
    //   202: iload #4
    //   204: istore #7
    //   206: aload_0
    //   207: getfield mPrimaryOrientation : Landroid/support/v7/widget/OrientationHelper;
    //   210: invokevirtual getEnd : ()I
    //   213: ifne -> 219
    //   216: iconst_1
    //   217: istore #7
    //   219: aload_2
    //   220: iload #7
    //   222: putfield mInfinite : Z
    //   225: return
  }
  
  private void updateRemainingSpans(Span paramSpan, int paramInt1, int paramInt2) {
    int i = paramSpan.getDeletedSize();
    if (paramInt1 == -1) {
      if (paramSpan.getStartLine() + i <= paramInt2)
        this.mRemainingSpans.set(paramSpan.mIndex, false); 
    } else if (paramSpan.getEndLine() - i >= paramInt2) {
      this.mRemainingSpans.set(paramSpan.mIndex, false);
    } 
  }
  
  private int updateSpecWithExtra(int paramInt1, int paramInt2, int paramInt3) {
    if (paramInt2 == 0 && paramInt3 == 0)
      return paramInt1; 
    int i = View.MeasureSpec.getMode(paramInt1);
    return (i == Integer.MIN_VALUE || i == 1073741824) ? View.MeasureSpec.makeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(paramInt1) - paramInt2 - paramInt3), i) : paramInt1;
  }
  
  boolean areAllEndsEqual() {
    int i = this.mSpans[0].getEndLine(-2147483648);
    for (byte b = 1; b < this.mSpanCount; b++) {
      if (this.mSpans[b].getEndLine(-2147483648) != i)
        return false; 
    } 
    return true;
  }
  
  boolean areAllStartsEqual() {
    int i = this.mSpans[0].getStartLine(-2147483648);
    for (byte b = 1; b < this.mSpanCount; b++) {
      if (this.mSpans[b].getStartLine(-2147483648) != i)
        return false; 
    } 
    return true;
  }
  
  public void assertNotInLayoutOrScroll(String paramString) {
    if (this.mPendingSavedState == null)
      super.assertNotInLayoutOrScroll(paramString); 
  }
  
  public boolean canScrollHorizontally() {
    boolean bool;
    if (this.mOrientation == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean canScrollVertically() {
    int i = this.mOrientation;
    boolean bool = true;
    if (i != 1)
      bool = false; 
    return bool;
  }
  
  boolean checkForGaps() {
    int i;
    int j;
    byte b;
    if (getChildCount() == 0 || this.mGapStrategy == 0 || !isAttachedToWindow())
      return false; 
    if (this.mShouldReverseLayout) {
      i = getLastChildPosition();
      j = getFirstChildPosition();
    } else {
      i = getFirstChildPosition();
      j = getLastChildPosition();
    } 
    if (i == 0 && hasGapsToFix() != null) {
      this.mLazySpanLookup.clear();
      requestSimpleAnimationsInNextLayout();
      requestLayout();
      return true;
    } 
    if (!this.mLaidOutInvalidFullSpan)
      return false; 
    if (this.mShouldReverseLayout) {
      b = -1;
    } else {
      b = 1;
    } 
    LazySpanLookup lazySpanLookup = this.mLazySpanLookup;
    LazySpanLookup.FullSpanItem fullSpanItem2 = lazySpanLookup.getFirstFullSpanItemInRange(i, ++j, b, true);
    if (fullSpanItem2 == null) {
      this.mLaidOutInvalidFullSpan = false;
      this.mLazySpanLookup.forceInvalidateAfter(j);
      return false;
    } 
    LazySpanLookup.FullSpanItem fullSpanItem1 = this.mLazySpanLookup.getFirstFullSpanItemInRange(i, fullSpanItem2.mPosition, b * -1, true);
    if (fullSpanItem1 == null) {
      this.mLazySpanLookup.forceInvalidateAfter(fullSpanItem2.mPosition);
    } else {
      this.mLazySpanLookup.forceInvalidateAfter(fullSpanItem1.mPosition + 1);
    } 
    requestSimpleAnimationsInNextLayout();
    requestLayout();
    return true;
  }
  
  public boolean checkLayoutParams(RecyclerView.LayoutParams paramLayoutParams) {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  public void collectAdjacentPrefetchPositions(int paramInt1, int paramInt2, RecyclerView.State paramState, RecyclerView.LayoutManager.LayoutPrefetchRegistry paramLayoutPrefetchRegistry) {
    if (this.mOrientation != 0)
      paramInt1 = paramInt2; 
    if (getChildCount() != 0 && paramInt1 != 0) {
      prepareLayoutStateForDelta(paramInt1, paramState);
      int[] arrayOfInt = this.mPrefetchDistances;
      if (arrayOfInt == null || arrayOfInt.length < this.mSpanCount)
        this.mPrefetchDistances = new int[this.mSpanCount]; 
      boolean bool = false;
      paramInt2 = 0;
      for (paramInt1 = 0; paramInt2 < this.mSpanCount; paramInt1 = j) {
        int i;
        if (this.mLayoutState.mItemDirection == -1) {
          i = this.mLayoutState.mStartLine;
          j = this.mSpans[paramInt2].getStartLine(this.mLayoutState.mStartLine);
        } else {
          i = this.mSpans[paramInt2].getEndLine(this.mLayoutState.mEndLine);
          j = this.mLayoutState.mEndLine;
        } 
        i -= j;
        int j = paramInt1;
        if (i >= 0) {
          this.mPrefetchDistances[paramInt1] = i;
          j = paramInt1 + 1;
        } 
        paramInt2++;
      } 
      Arrays.sort(this.mPrefetchDistances, 0, paramInt1);
      for (paramInt2 = bool; paramInt2 < paramInt1 && this.mLayoutState.hasMore(paramState); paramInt2++) {
        paramLayoutPrefetchRegistry.addPosition(this.mLayoutState.mCurrentPosition, this.mPrefetchDistances[paramInt2]);
        LayoutState layoutState = this.mLayoutState;
        layoutState.mCurrentPosition += this.mLayoutState.mItemDirection;
      } 
    } 
  }
  
  public int computeHorizontalScrollExtent(RecyclerView.State paramState) {
    return computeScrollExtent(paramState);
  }
  
  public int computeHorizontalScrollOffset(RecyclerView.State paramState) {
    return computeScrollOffset(paramState);
  }
  
  public int computeHorizontalScrollRange(RecyclerView.State paramState) {
    return computeScrollRange(paramState);
  }
  
  public PointF computeScrollVectorForPosition(int paramInt) {
    paramInt = calculateScrollDirectionForPosition(paramInt);
    PointF pointF = new PointF();
    if (paramInt == 0)
      return null; 
    if (this.mOrientation == 0) {
      pointF.x = paramInt;
      pointF.y = 0.0F;
    } else {
      pointF.x = 0.0F;
      pointF.y = paramInt;
    } 
    return pointF;
  }
  
  public int computeVerticalScrollExtent(RecyclerView.State paramState) {
    return computeScrollExtent(paramState);
  }
  
  public int computeVerticalScrollOffset(RecyclerView.State paramState) {
    return computeScrollOffset(paramState);
  }
  
  public int computeVerticalScrollRange(RecyclerView.State paramState) {
    return computeScrollRange(paramState);
  }
  
  public int[] findFirstCompletelyVisibleItemPositions(int[] paramArrayOfint) {
    if (paramArrayOfint == null) {
      paramArrayOfint = new int[this.mSpanCount];
    } else if (paramArrayOfint.length < this.mSpanCount) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Provided int[]'s size must be more than or equal to span count. Expected:");
      stringBuilder.append(this.mSpanCount);
      stringBuilder.append(", array size:");
      stringBuilder.append(paramArrayOfint.length);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    for (byte b = 0; b < this.mSpanCount; b++)
      paramArrayOfint[b] = this.mSpans[b].findFirstCompletelyVisibleItemPosition(); 
    return paramArrayOfint;
  }
  
  View findFirstVisibleItemClosestToEnd(boolean paramBoolean) {
    int i = this.mPrimaryOrientation.getStartAfterPadding();
    int j = this.mPrimaryOrientation.getEndAfterPadding();
    int k = getChildCount() - 1;
    View view;
    for (view = null; k >= 0; view = view2) {
      View view1 = getChildAt(k);
      int m = this.mPrimaryOrientation.getDecoratedStart(view1);
      int n = this.mPrimaryOrientation.getDecoratedEnd(view1);
      View view2 = view;
      if (n > i)
        if (m >= j) {
          view2 = view;
        } else {
          if (n <= j || !paramBoolean)
            return view1; 
          view2 = view;
          if (view == null)
            view2 = view1; 
        }  
      k--;
    } 
    return view;
  }
  
  View findFirstVisibleItemClosestToStart(boolean paramBoolean) {
    int i = this.mPrimaryOrientation.getStartAfterPadding();
    int j = this.mPrimaryOrientation.getEndAfterPadding();
    int k = getChildCount();
    View view = null;
    byte b = 0;
    while (b < k) {
      View view1 = getChildAt(b);
      int m = this.mPrimaryOrientation.getDecoratedStart(view1);
      View view2 = view;
      if (this.mPrimaryOrientation.getDecoratedEnd(view1) > i)
        if (m >= j) {
          view2 = view;
        } else {
          if (m >= i || !paramBoolean)
            return view1; 
          view2 = view;
          if (view == null)
            view2 = view1; 
        }  
      b++;
      view = view2;
    } 
    return view;
  }
  
  int findFirstVisibleItemPositionInt() {
    View view;
    int i;
    if (this.mShouldReverseLayout) {
      view = findFirstVisibleItemClosestToEnd(true);
    } else {
      view = findFirstVisibleItemClosestToStart(true);
    } 
    if (view == null) {
      i = -1;
    } else {
      i = getPosition(view);
    } 
    return i;
  }
  
  public int[] findFirstVisibleItemPositions(int[] paramArrayOfint) {
    if (paramArrayOfint == null) {
      paramArrayOfint = new int[this.mSpanCount];
    } else if (paramArrayOfint.length < this.mSpanCount) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Provided int[]'s size must be more than or equal to span count. Expected:");
      stringBuilder.append(this.mSpanCount);
      stringBuilder.append(", array size:");
      stringBuilder.append(paramArrayOfint.length);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    for (byte b = 0; b < this.mSpanCount; b++)
      paramArrayOfint[b] = this.mSpans[b].findFirstVisibleItemPosition(); 
    return paramArrayOfint;
  }
  
  public int[] findLastCompletelyVisibleItemPositions(int[] paramArrayOfint) {
    if (paramArrayOfint == null) {
      paramArrayOfint = new int[this.mSpanCount];
    } else if (paramArrayOfint.length < this.mSpanCount) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Provided int[]'s size must be more than or equal to span count. Expected:");
      stringBuilder.append(this.mSpanCount);
      stringBuilder.append(", array size:");
      stringBuilder.append(paramArrayOfint.length);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    for (byte b = 0; b < this.mSpanCount; b++)
      paramArrayOfint[b] = this.mSpans[b].findLastCompletelyVisibleItemPosition(); 
    return paramArrayOfint;
  }
  
  public int[] findLastVisibleItemPositions(int[] paramArrayOfint) {
    if (paramArrayOfint == null) {
      paramArrayOfint = new int[this.mSpanCount];
    } else if (paramArrayOfint.length < this.mSpanCount) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Provided int[]'s size must be more than or equal to span count. Expected:");
      stringBuilder.append(this.mSpanCount);
      stringBuilder.append(", array size:");
      stringBuilder.append(paramArrayOfint.length);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    for (byte b = 0; b < this.mSpanCount; b++)
      paramArrayOfint[b] = this.mSpans[b].findLastVisibleItemPosition(); 
    return paramArrayOfint;
  }
  
  public RecyclerView.LayoutParams generateDefaultLayoutParams() {
    return (this.mOrientation == 0) ? new LayoutParams(-2, -1) : new LayoutParams(-1, -2);
  }
  
  public RecyclerView.LayoutParams generateLayoutParams(Context paramContext, AttributeSet paramAttributeSet) {
    return new LayoutParams(paramContext, paramAttributeSet);
  }
  
  public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return (paramLayoutParams instanceof ViewGroup.MarginLayoutParams) ? new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams) : new LayoutParams(paramLayoutParams);
  }
  
  public int getColumnCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState) {
    return (this.mOrientation == 1) ? this.mSpanCount : super.getColumnCountForAccessibility(paramRecycler, paramState);
  }
  
  int getFirstChildPosition() {
    int i = getChildCount();
    int j = 0;
    if (i != 0)
      j = getPosition(getChildAt(0)); 
    return j;
  }
  
  public int getGapStrategy() {
    return this.mGapStrategy;
  }
  
  int getLastChildPosition() {
    int i = getChildCount();
    if (i == 0) {
      i = 0;
    } else {
      i = getPosition(getChildAt(i - 1));
    } 
    return i;
  }
  
  public int getOrientation() {
    return this.mOrientation;
  }
  
  public boolean getReverseLayout() {
    return this.mReverseLayout;
  }
  
  public int getRowCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState) {
    return (this.mOrientation == 0) ? this.mSpanCount : super.getRowCountForAccessibility(paramRecycler, paramState);
  }
  
  public int getSpanCount() {
    return this.mSpanCount;
  }
  
  View hasGapsToFix() {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getChildCount : ()I
    //   4: iconst_1
    //   5: isub
    //   6: istore_1
    //   7: new java/util/BitSet
    //   10: dup
    //   11: aload_0
    //   12: getfield mSpanCount : I
    //   15: invokespecial <init> : (I)V
    //   18: astore_2
    //   19: aload_2
    //   20: iconst_0
    //   21: aload_0
    //   22: getfield mSpanCount : I
    //   25: iconst_1
    //   26: invokevirtual set : (IIZ)V
    //   29: aload_0
    //   30: getfield mOrientation : I
    //   33: istore_3
    //   34: iconst_m1
    //   35: istore #4
    //   37: iload_3
    //   38: iconst_1
    //   39: if_icmpne -> 54
    //   42: aload_0
    //   43: invokevirtual isLayoutRTL : ()Z
    //   46: ifeq -> 54
    //   49: iconst_1
    //   50: istore_3
    //   51: goto -> 56
    //   54: iconst_m1
    //   55: istore_3
    //   56: aload_0
    //   57: getfield mShouldReverseLayout : Z
    //   60: ifeq -> 69
    //   63: iconst_m1
    //   64: istore #5
    //   66: goto -> 76
    //   69: iload_1
    //   70: iconst_1
    //   71: iadd
    //   72: istore #5
    //   74: iconst_0
    //   75: istore_1
    //   76: iload_1
    //   77: istore #6
    //   79: iload_1
    //   80: iload #5
    //   82: if_icmpge -> 91
    //   85: iconst_1
    //   86: istore #4
    //   88: iload_1
    //   89: istore #6
    //   91: iload #6
    //   93: iload #5
    //   95: if_icmpeq -> 350
    //   98: aload_0
    //   99: iload #6
    //   101: invokevirtual getChildAt : (I)Landroid/view/View;
    //   104: astore #7
    //   106: aload #7
    //   108: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   111: checkcast android/support/v7/widget/StaggeredGridLayoutManager$LayoutParams
    //   114: astore #8
    //   116: aload_2
    //   117: aload #8
    //   119: getfield mSpan : Landroid/support/v7/widget/StaggeredGridLayoutManager$Span;
    //   122: getfield mIndex : I
    //   125: invokevirtual get : (I)Z
    //   128: ifeq -> 158
    //   131: aload_0
    //   132: aload #8
    //   134: getfield mSpan : Landroid/support/v7/widget/StaggeredGridLayoutManager$Span;
    //   137: invokespecial checkSpanForGap : (Landroid/support/v7/widget/StaggeredGridLayoutManager$Span;)Z
    //   140: ifeq -> 146
    //   143: aload #7
    //   145: areturn
    //   146: aload_2
    //   147: aload #8
    //   149: getfield mSpan : Landroid/support/v7/widget/StaggeredGridLayoutManager$Span;
    //   152: getfield mIndex : I
    //   155: invokevirtual clear : (I)V
    //   158: aload #8
    //   160: getfield mFullSpan : Z
    //   163: ifeq -> 169
    //   166: goto -> 340
    //   169: iload #6
    //   171: iload #4
    //   173: iadd
    //   174: istore_1
    //   175: iload_1
    //   176: iload #5
    //   178: if_icmpeq -> 340
    //   181: aload_0
    //   182: iload_1
    //   183: invokevirtual getChildAt : (I)Landroid/view/View;
    //   186: astore #9
    //   188: aload_0
    //   189: getfield mShouldReverseLayout : Z
    //   192: ifeq -> 234
    //   195: aload_0
    //   196: getfield mPrimaryOrientation : Landroid/support/v7/widget/OrientationHelper;
    //   199: aload #7
    //   201: invokevirtual getDecoratedEnd : (Landroid/view/View;)I
    //   204: istore #10
    //   206: aload_0
    //   207: getfield mPrimaryOrientation : Landroid/support/v7/widget/OrientationHelper;
    //   210: aload #9
    //   212: invokevirtual getDecoratedEnd : (Landroid/view/View;)I
    //   215: istore_1
    //   216: iload #10
    //   218: iload_1
    //   219: if_icmpge -> 225
    //   222: aload #7
    //   224: areturn
    //   225: iload #10
    //   227: iload_1
    //   228: if_icmpne -> 275
    //   231: goto -> 270
    //   234: aload_0
    //   235: getfield mPrimaryOrientation : Landroid/support/v7/widget/OrientationHelper;
    //   238: aload #7
    //   240: invokevirtual getDecoratedStart : (Landroid/view/View;)I
    //   243: istore_1
    //   244: aload_0
    //   245: getfield mPrimaryOrientation : Landroid/support/v7/widget/OrientationHelper;
    //   248: aload #9
    //   250: invokevirtual getDecoratedStart : (Landroid/view/View;)I
    //   253: istore #10
    //   255: iload_1
    //   256: iload #10
    //   258: if_icmple -> 264
    //   261: aload #7
    //   263: areturn
    //   264: iload_1
    //   265: iload #10
    //   267: if_icmpne -> 275
    //   270: iconst_1
    //   271: istore_1
    //   272: goto -> 277
    //   275: iconst_0
    //   276: istore_1
    //   277: iload_1
    //   278: ifeq -> 340
    //   281: aload #9
    //   283: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   286: checkcast android/support/v7/widget/StaggeredGridLayoutManager$LayoutParams
    //   289: astore #9
    //   291: aload #8
    //   293: getfield mSpan : Landroid/support/v7/widget/StaggeredGridLayoutManager$Span;
    //   296: getfield mIndex : I
    //   299: aload #9
    //   301: getfield mSpan : Landroid/support/v7/widget/StaggeredGridLayoutManager$Span;
    //   304: getfield mIndex : I
    //   307: isub
    //   308: ifge -> 316
    //   311: iconst_1
    //   312: istore_1
    //   313: goto -> 318
    //   316: iconst_0
    //   317: istore_1
    //   318: iload_3
    //   319: ifge -> 328
    //   322: iconst_1
    //   323: istore #10
    //   325: goto -> 331
    //   328: iconst_0
    //   329: istore #10
    //   331: iload_1
    //   332: iload #10
    //   334: if_icmpeq -> 340
    //   337: aload #7
    //   339: areturn
    //   340: iload #6
    //   342: iload #4
    //   344: iadd
    //   345: istore #6
    //   347: goto -> 91
    //   350: aconst_null
    //   351: areturn
  }
  
  public void invalidateSpanAssignments() {
    this.mLazySpanLookup.clear();
    requestLayout();
  }
  
  public boolean isAutoMeasureEnabled() {
    boolean bool;
    if (this.mGapStrategy != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  boolean isLayoutRTL() {
    int i = getLayoutDirection();
    boolean bool = true;
    if (i != 1)
      bool = false; 
    return bool;
  }
  
  public void offsetChildrenHorizontal(int paramInt) {
    super.offsetChildrenHorizontal(paramInt);
    for (byte b = 0; b < this.mSpanCount; b++)
      this.mSpans[b].onOffset(paramInt); 
  }
  
  public void offsetChildrenVertical(int paramInt) {
    super.offsetChildrenVertical(paramInt);
    for (byte b = 0; b < this.mSpanCount; b++)
      this.mSpans[b].onOffset(paramInt); 
  }
  
  public void onDetachedFromWindow(RecyclerView paramRecyclerView, RecyclerView.Recycler paramRecycler) {
    super.onDetachedFromWindow(paramRecyclerView, paramRecycler);
    removeCallbacks(this.mCheckForGapsRunnable);
    for (byte b = 0; b < this.mSpanCount; b++)
      this.mSpans[b].clear(); 
    paramRecyclerView.requestLayout();
  }
  
  public View onFocusSearchFailed(View paramView, int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState) {
    if (getChildCount() == 0)
      return null; 
    paramView = findContainingItemView(paramView);
    if (paramView == null)
      return null; 
    resolveShouldLayoutReverse();
    int i = convertFocusDirectionToLayoutDirection(paramInt);
    if (i == Integer.MIN_VALUE)
      return null; 
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    boolean bool1 = layoutParams.mFullSpan;
    Span span = layoutParams.mSpan;
    if (i == 1) {
      paramInt = getLastChildPosition();
    } else {
      paramInt = getFirstChildPosition();
    } 
    updateLayoutState(paramInt, paramState);
    setLayoutStateDirection(i);
    LayoutState layoutState = this.mLayoutState;
    layoutState.mCurrentPosition = layoutState.mItemDirection + paramInt;
    this.mLayoutState.mAvailable = (int)(this.mPrimaryOrientation.getTotalSpace() * 0.33333334F);
    this.mLayoutState.mStopInFocusable = true;
    layoutState = this.mLayoutState;
    int j = 0;
    layoutState.mRecycle = false;
    fill(paramRecycler, this.mLayoutState, paramState);
    this.mLastLayoutFromEnd = this.mShouldReverseLayout;
    if (!bool1) {
      View view = span.getFocusableViewAfter(paramInt, i);
      if (view != null && view != paramView)
        return view; 
    } 
    if (preferLastSpan(i)) {
      for (int m = this.mSpanCount - 1; m >= 0; m--) {
        View view = this.mSpans[m].getFocusableViewAfter(paramInt, i);
        if (view != null && view != paramView)
          return view; 
      } 
    } else {
      for (byte b = 0; b < this.mSpanCount; b++) {
        View view = this.mSpans[b].getFocusableViewAfter(paramInt, i);
        if (view != null && view != paramView)
          return view; 
      } 
    } 
    boolean bool2 = this.mReverseLayout;
    if (i == -1) {
      paramInt = 1;
    } else {
      paramInt = 0;
    } 
    if ((bool2 ^ true) == paramInt) {
      paramInt = 1;
    } else {
      paramInt = 0;
    } 
    if (!bool1) {
      int m;
      if (paramInt != 0) {
        m = span.findFirstPartiallyVisibleItemPosition();
      } else {
        m = span.findLastPartiallyVisibleItemPosition();
      } 
      View view = findViewByPosition(m);
      if (view != null && view != paramView)
        return view; 
    } 
    int k = j;
    if (preferLastSpan(i)) {
      for (k = this.mSpanCount - 1; k >= 0; k--) {
        if (k != span.mIndex) {
          if (paramInt != 0) {
            j = this.mSpans[k].findFirstPartiallyVisibleItemPosition();
          } else {
            j = this.mSpans[k].findLastPartiallyVisibleItemPosition();
          } 
          View view = findViewByPosition(j);
          if (view != null && view != paramView)
            return view; 
        } 
      } 
    } else {
      while (k < this.mSpanCount) {
        if (paramInt != 0) {
          j = this.mSpans[k].findFirstPartiallyVisibleItemPosition();
        } else {
          j = this.mSpans[k].findLastPartiallyVisibleItemPosition();
        } 
        View view = findViewByPosition(j);
        if (view != null && view != paramView)
          return view; 
        k++;
      } 
    } 
    return null;
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent) {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    if (getChildCount() > 0) {
      View view1 = findFirstVisibleItemClosestToStart(false);
      View view2 = findFirstVisibleItemClosestToEnd(false);
      if (view1 != null && view2 != null) {
        int i = getPosition(view1);
        int j = getPosition(view2);
        if (i < j) {
          paramAccessibilityEvent.setFromIndex(i);
          paramAccessibilityEvent.setToIndex(j);
        } else {
          paramAccessibilityEvent.setFromIndex(j);
          paramAccessibilityEvent.setToIndex(i);
        } 
      } 
    } 
  }
  
  public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat) {
    ViewGroup.LayoutParams layoutParams1 = paramView.getLayoutParams();
    if (!(layoutParams1 instanceof LayoutParams)) {
      onInitializeAccessibilityNodeInfoForItem(paramView, paramAccessibilityNodeInfoCompat);
      return;
    } 
    LayoutParams layoutParams = (LayoutParams)layoutParams1;
    if (this.mOrientation == 0) {
      boolean bool;
      int i = layoutParams.getSpanIndex();
      if (layoutParams.mFullSpan) {
        bool = this.mSpanCount;
      } else {
        bool = true;
      } 
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(i, bool, -1, -1, layoutParams.mFullSpan, false));
    } else {
      boolean bool;
      int i = layoutParams.getSpanIndex();
      if (layoutParams.mFullSpan) {
        bool = this.mSpanCount;
      } else {
        bool = true;
      } 
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(-1, -1, i, bool, layoutParams.mFullSpan, false));
    } 
  }
  
  public void onItemsAdded(RecyclerView paramRecyclerView, int paramInt1, int paramInt2) {
    handleUpdate(paramInt1, paramInt2, 1);
  }
  
  public void onItemsChanged(RecyclerView paramRecyclerView) {
    this.mLazySpanLookup.clear();
    requestLayout();
  }
  
  public void onItemsMoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, int paramInt3) {
    handleUpdate(paramInt1, paramInt2, 8);
  }
  
  public void onItemsRemoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2) {
    handleUpdate(paramInt1, paramInt2, 2);
  }
  
  public void onItemsUpdated(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, Object paramObject) {
    handleUpdate(paramInt1, paramInt2, 4);
  }
  
  public void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState) {
    onLayoutChildren(paramRecycler, paramState, true);
  }
  
  public void onLayoutCompleted(RecyclerView.State paramState) {
    super.onLayoutCompleted(paramState);
    this.mPendingScrollPosition = -1;
    this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
    this.mPendingSavedState = null;
    this.mAnchorInfo.reset();
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable) {
    if (paramParcelable instanceof SavedState) {
      this.mPendingSavedState = (SavedState)paramParcelable;
      requestLayout();
    } 
  }
  
  public Parcelable onSaveInstanceState() {
    if (this.mPendingSavedState != null)
      return new SavedState(this.mPendingSavedState); 
    SavedState savedState = new SavedState();
    savedState.mReverseLayout = this.mReverseLayout;
    savedState.mAnchorLayoutFromEnd = this.mLastLayoutFromEnd;
    savedState.mLastLayoutRTL = this.mLastLayoutRTL;
    LazySpanLookup lazySpanLookup = this.mLazySpanLookup;
    byte b = 0;
    if (lazySpanLookup != null && lazySpanLookup.mData != null) {
      savedState.mSpanLookup = this.mLazySpanLookup.mData;
      savedState.mSpanLookupSize = savedState.mSpanLookup.length;
      savedState.mFullSpanItems = this.mLazySpanLookup.mFullSpanItems;
    } else {
      savedState.mSpanLookupSize = 0;
    } 
    if (getChildCount() > 0) {
      int i;
      if (this.mLastLayoutFromEnd) {
        i = getLastChildPosition();
      } else {
        i = getFirstChildPosition();
      } 
      savedState.mAnchorPosition = i;
      savedState.mVisibleAnchorPosition = findFirstVisibleItemPositionInt();
      savedState.mSpanOffsetsSize = this.mSpanCount;
      savedState.mSpanOffsets = new int[this.mSpanCount];
      while (b < this.mSpanCount) {
        int j;
        if (this.mLastLayoutFromEnd) {
          j = this.mSpans[b].getEndLine(-2147483648);
          i = j;
          if (j != Integer.MIN_VALUE) {
            i = this.mPrimaryOrientation.getEndAfterPadding();
          } else {
            continue;
          } 
        } else {
          j = this.mSpans[b].getStartLine(-2147483648);
          i = j;
          if (j != Integer.MIN_VALUE) {
            i = this.mPrimaryOrientation.getStartAfterPadding();
          } else {
            continue;
          } 
        } 
        i = j - i;
        continue;
        savedState.mSpanOffsets[b] = i;
        b++;
      } 
    } else {
      savedState.mAnchorPosition = -1;
      savedState.mVisibleAnchorPosition = -1;
      savedState.mSpanOffsetsSize = 0;
    } 
    return savedState;
  }
  
  public void onScrollStateChanged(int paramInt) {
    if (paramInt == 0)
      checkForGaps(); 
  }
  
  void prepareLayoutStateForDelta(int paramInt, RecyclerView.State paramState) {
    int i;
    byte b;
    if (paramInt > 0) {
      i = getLastChildPosition();
      b = 1;
    } else {
      i = getFirstChildPosition();
      b = -1;
    } 
    this.mLayoutState.mRecycle = true;
    updateLayoutState(i, paramState);
    setLayoutStateDirection(b);
    LayoutState layoutState = this.mLayoutState;
    layoutState.mCurrentPosition = i + layoutState.mItemDirection;
    this.mLayoutState.mAvailable = Math.abs(paramInt);
  }
  
  int scrollBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState) {
    if (getChildCount() == 0 || paramInt == 0)
      return 0; 
    prepareLayoutStateForDelta(paramInt, paramState);
    int i = fill(paramRecycler, this.mLayoutState, paramState);
    if (this.mLayoutState.mAvailable >= i)
      if (paramInt < 0) {
        paramInt = -i;
      } else {
        paramInt = i;
      }  
    this.mPrimaryOrientation.offsetChildren(-paramInt);
    this.mLastLayoutFromEnd = this.mShouldReverseLayout;
    this.mLayoutState.mAvailable = 0;
    recycle(paramRecycler, this.mLayoutState);
    return paramInt;
  }
  
  public int scrollHorizontallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState) {
    return scrollBy(paramInt, paramRecycler, paramState);
  }
  
  public void scrollToPosition(int paramInt) {
    SavedState savedState = this.mPendingSavedState;
    if (savedState != null && savedState.mAnchorPosition != paramInt)
      this.mPendingSavedState.invalidateAnchorPositionInfo(); 
    this.mPendingScrollPosition = paramInt;
    this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
    requestLayout();
  }
  
  public void scrollToPositionWithOffset(int paramInt1, int paramInt2) {
    SavedState savedState = this.mPendingSavedState;
    if (savedState != null)
      savedState.invalidateAnchorPositionInfo(); 
    this.mPendingScrollPosition = paramInt1;
    this.mPendingScrollPositionOffset = paramInt2;
    requestLayout();
  }
  
  public int scrollVerticallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState) {
    return scrollBy(paramInt, paramRecycler, paramState);
  }
  
  public void setGapStrategy(int paramInt) {
    assertNotInLayoutOrScroll((String)null);
    if (paramInt == this.mGapStrategy)
      return; 
    if (paramInt == 0 || paramInt == 2) {
      this.mGapStrategy = paramInt;
      requestLayout();
      return;
    } 
    throw new IllegalArgumentException("invalid gap strategy. Must be GAP_HANDLING_NONE or GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS");
  }
  
  public void setMeasuredDimension(Rect paramRect, int paramInt1, int paramInt2) {
    int i = getPaddingLeft() + getPaddingRight();
    int j = getPaddingTop() + getPaddingBottom();
    if (this.mOrientation == 1) {
      paramInt2 = chooseSize(paramInt2, paramRect.height() + j, getMinimumHeight());
      i = chooseSize(paramInt1, this.mSizePerSpan * this.mSpanCount + i, getMinimumWidth());
      paramInt1 = paramInt2;
      paramInt2 = i;
    } else {
      paramInt1 = chooseSize(paramInt1, paramRect.width() + i, getMinimumWidth());
      i = chooseSize(paramInt2, this.mSizePerSpan * this.mSpanCount + j, getMinimumHeight());
      paramInt2 = paramInt1;
      paramInt1 = i;
    } 
    setMeasuredDimension(paramInt2, paramInt1);
  }
  
  public void setOrientation(int paramInt) {
    if (paramInt == 0 || paramInt == 1) {
      assertNotInLayoutOrScroll((String)null);
      if (paramInt == this.mOrientation)
        return; 
      this.mOrientation = paramInt;
      OrientationHelper orientationHelper = this.mPrimaryOrientation;
      this.mPrimaryOrientation = this.mSecondaryOrientation;
      this.mSecondaryOrientation = orientationHelper;
      requestLayout();
      return;
    } 
    throw new IllegalArgumentException("invalid orientation.");
  }
  
  public void setReverseLayout(boolean paramBoolean) {
    assertNotInLayoutOrScroll((String)null);
    SavedState savedState = this.mPendingSavedState;
    if (savedState != null && savedState.mReverseLayout != paramBoolean)
      this.mPendingSavedState.mReverseLayout = paramBoolean; 
    this.mReverseLayout = paramBoolean;
    requestLayout();
  }
  
  public void setSpanCount(int paramInt) {
    assertNotInLayoutOrScroll((String)null);
    if (paramInt != this.mSpanCount) {
      invalidateSpanAssignments();
      this.mSpanCount = paramInt;
      this.mRemainingSpans = new BitSet(this.mSpanCount);
      this.mSpans = new Span[this.mSpanCount];
      for (paramInt = 0; paramInt < this.mSpanCount; paramInt++)
        this.mSpans[paramInt] = new Span(paramInt); 
      requestLayout();
    } 
  }
  
  public void smoothScrollToPosition(RecyclerView paramRecyclerView, RecyclerView.State paramState, int paramInt) {
    LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(paramRecyclerView.getContext());
    linearSmoothScroller.setTargetPosition(paramInt);
    startSmoothScroll(linearSmoothScroller);
  }
  
  public boolean supportsPredictiveItemAnimations() {
    boolean bool;
    if (this.mPendingSavedState == null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  boolean updateAnchorFromPendingData(RecyclerView.State paramState, AnchorInfo paramAnchorInfo) {
    boolean bool = paramState.isPreLayout();
    boolean bool1 = false;
    if (!bool) {
      int i = this.mPendingScrollPosition;
      if (i == -1)
        return false; 
      if (i < 0 || i >= paramState.getItemCount()) {
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        return false;
      } 
      SavedState savedState = this.mPendingSavedState;
      if (savedState == null || savedState.mAnchorPosition == -1 || this.mPendingSavedState.mSpanOffsetsSize < 1) {
        View view = findViewByPosition(this.mPendingScrollPosition);
        if (view != null) {
          if (this.mShouldReverseLayout) {
            i = getLastChildPosition();
          } else {
            i = getFirstChildPosition();
          } 
          paramAnchorInfo.mPosition = i;
          if (this.mPendingScrollPositionOffset != Integer.MIN_VALUE) {
            if (paramAnchorInfo.mLayoutFromEnd) {
              paramAnchorInfo.mOffset = this.mPrimaryOrientation.getEndAfterPadding() - this.mPendingScrollPositionOffset - this.mPrimaryOrientation.getDecoratedEnd(view);
            } else {
              paramAnchorInfo.mOffset = this.mPrimaryOrientation.getStartAfterPadding() + this.mPendingScrollPositionOffset - this.mPrimaryOrientation.getDecoratedStart(view);
            } 
            return true;
          } 
          if (this.mPrimaryOrientation.getDecoratedMeasurement(view) > this.mPrimaryOrientation.getTotalSpace()) {
            if (paramAnchorInfo.mLayoutFromEnd) {
              i = this.mPrimaryOrientation.getEndAfterPadding();
            } else {
              i = this.mPrimaryOrientation.getStartAfterPadding();
            } 
            paramAnchorInfo.mOffset = i;
            return true;
          } 
          i = this.mPrimaryOrientation.getDecoratedStart(view) - this.mPrimaryOrientation.getStartAfterPadding();
          if (i < 0) {
            paramAnchorInfo.mOffset = -i;
            return true;
          } 
          i = this.mPrimaryOrientation.getEndAfterPadding() - this.mPrimaryOrientation.getDecoratedEnd(view);
          if (i < 0) {
            paramAnchorInfo.mOffset = i;
            return true;
          } 
          paramAnchorInfo.mOffset = Integer.MIN_VALUE;
        } else {
          paramAnchorInfo.mPosition = this.mPendingScrollPosition;
          i = this.mPendingScrollPositionOffset;
          if (i == Integer.MIN_VALUE) {
            if (calculateScrollDirectionForPosition(paramAnchorInfo.mPosition) == 1)
              bool1 = true; 
            paramAnchorInfo.mLayoutFromEnd = bool1;
            paramAnchorInfo.assignCoordinateFromPadding();
          } else {
            paramAnchorInfo.assignCoordinateFromPadding(i);
          } 
          paramAnchorInfo.mInvalidateOffsets = true;
        } 
        return true;
      } 
      paramAnchorInfo.mOffset = Integer.MIN_VALUE;
      paramAnchorInfo.mPosition = this.mPendingScrollPosition;
      return true;
    } 
    return false;
  }
  
  void updateAnchorInfoForLayout(RecyclerView.State paramState, AnchorInfo paramAnchorInfo) {
    if (updateAnchorFromPendingData(paramState, paramAnchorInfo))
      return; 
    if (updateAnchorFromChildren(paramState, paramAnchorInfo))
      return; 
    paramAnchorInfo.assignCoordinateFromPadding();
    paramAnchorInfo.mPosition = 0;
  }
  
  void updateMeasureSpecs(int paramInt) {
    this.mSizePerSpan = paramInt / this.mSpanCount;
    this.mFullSizeSpec = View.MeasureSpec.makeMeasureSpec(paramInt, this.mSecondaryOrientation.getMode());
  }
  
  class AnchorInfo {
    boolean mInvalidateOffsets;
    
    boolean mLayoutFromEnd;
    
    int mOffset;
    
    int mPosition;
    
    int[] mSpanReferenceLines;
    
    boolean mValid;
    
    AnchorInfo() {
      reset();
    }
    
    void assignCoordinateFromPadding() {
      int i;
      if (this.mLayoutFromEnd) {
        i = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding();
      } else {
        i = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
      } 
      this.mOffset = i;
    }
    
    void assignCoordinateFromPadding(int param1Int) {
      if (this.mLayoutFromEnd) {
        this.mOffset = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() - param1Int;
      } else {
        this.mOffset = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding() + param1Int;
      } 
    }
    
    void reset() {
      this.mPosition = -1;
      this.mOffset = Integer.MIN_VALUE;
      this.mLayoutFromEnd = false;
      this.mInvalidateOffsets = false;
      this.mValid = false;
      int[] arrayOfInt = this.mSpanReferenceLines;
      if (arrayOfInt != null)
        Arrays.fill(arrayOfInt, -1); 
    }
    
    void saveSpanReferenceLines(StaggeredGridLayoutManager.Span[] param1ArrayOfSpan) {
      int i = param1ArrayOfSpan.length;
      int[] arrayOfInt = this.mSpanReferenceLines;
      if (arrayOfInt == null || arrayOfInt.length < i)
        this.mSpanReferenceLines = new int[StaggeredGridLayoutManager.this.mSpans.length]; 
      for (byte b = 0; b < i; b++)
        this.mSpanReferenceLines[b] = param1ArrayOfSpan[b].getStartLine(-2147483648); 
    }
  }
  
  public static class LayoutParams extends RecyclerView.LayoutParams {
    public static final int INVALID_SPAN_ID = -1;
    
    boolean mFullSpan;
    
    StaggeredGridLayoutManager.Span mSpan;
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
    }
    
    public LayoutParams(RecyclerView.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams param1MarginLayoutParams) {
      super(param1MarginLayoutParams);
    }
    
    public final int getSpanIndex() {
      StaggeredGridLayoutManager.Span span = this.mSpan;
      return (span == null) ? -1 : span.mIndex;
    }
    
    public boolean isFullSpan() {
      return this.mFullSpan;
    }
    
    public void setFullSpan(boolean param1Boolean) {
      this.mFullSpan = param1Boolean;
    }
  }
  
  static class LazySpanLookup {
    private static final int MIN_SIZE = 10;
    
    int[] mData;
    
    List<FullSpanItem> mFullSpanItems;
    
    private int invalidateFullSpansAfter(int param1Int) {
      // Byte code:
      //   0: aload_0
      //   1: getfield mFullSpanItems : Ljava/util/List;
      //   4: ifnonnull -> 9
      //   7: iconst_m1
      //   8: ireturn
      //   9: aload_0
      //   10: iload_1
      //   11: invokevirtual getFullSpanItem : (I)Landroid/support/v7/widget/StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem;
      //   14: astore_2
      //   15: aload_2
      //   16: ifnull -> 30
      //   19: aload_0
      //   20: getfield mFullSpanItems : Ljava/util/List;
      //   23: aload_2
      //   24: invokeinterface remove : (Ljava/lang/Object;)Z
      //   29: pop
      //   30: aload_0
      //   31: getfield mFullSpanItems : Ljava/util/List;
      //   34: invokeinterface size : ()I
      //   39: istore_3
      //   40: iconst_0
      //   41: istore #4
      //   43: iload #4
      //   45: iload_3
      //   46: if_icmpge -> 79
      //   49: aload_0
      //   50: getfield mFullSpanItems : Ljava/util/List;
      //   53: iload #4
      //   55: invokeinterface get : (I)Ljava/lang/Object;
      //   60: checkcast android/support/v7/widget/StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem
      //   63: getfield mPosition : I
      //   66: iload_1
      //   67: if_icmplt -> 73
      //   70: goto -> 82
      //   73: iinc #4, 1
      //   76: goto -> 43
      //   79: iconst_m1
      //   80: istore #4
      //   82: iload #4
      //   84: iconst_m1
      //   85: if_icmpeq -> 120
      //   88: aload_0
      //   89: getfield mFullSpanItems : Ljava/util/List;
      //   92: iload #4
      //   94: invokeinterface get : (I)Ljava/lang/Object;
      //   99: checkcast android/support/v7/widget/StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem
      //   102: astore_2
      //   103: aload_0
      //   104: getfield mFullSpanItems : Ljava/util/List;
      //   107: iload #4
      //   109: invokeinterface remove : (I)Ljava/lang/Object;
      //   114: pop
      //   115: aload_2
      //   116: getfield mPosition : I
      //   119: ireturn
      //   120: iconst_m1
      //   121: ireturn
    }
    
    private void offsetFullSpansForAddition(int param1Int1, int param1Int2) {
      List<FullSpanItem> list = this.mFullSpanItems;
      if (list == null)
        return; 
      for (int i = list.size() - 1; i >= 0; i--) {
        FullSpanItem fullSpanItem = this.mFullSpanItems.get(i);
        if (fullSpanItem.mPosition >= param1Int1)
          fullSpanItem.mPosition += param1Int2; 
      } 
    }
    
    private void offsetFullSpansForRemoval(int param1Int1, int param1Int2) {
      List<FullSpanItem> list = this.mFullSpanItems;
      if (list == null)
        return; 
      for (int i = list.size() - 1; i >= 0; i--) {
        FullSpanItem fullSpanItem = this.mFullSpanItems.get(i);
        if (fullSpanItem.mPosition >= param1Int1)
          if (fullSpanItem.mPosition < param1Int1 + param1Int2) {
            this.mFullSpanItems.remove(i);
          } else {
            fullSpanItem.mPosition -= param1Int2;
          }  
      } 
    }
    
    public void addFullSpanItem(FullSpanItem param1FullSpanItem) {
      if (this.mFullSpanItems == null)
        this.mFullSpanItems = new ArrayList<FullSpanItem>(); 
      int i = this.mFullSpanItems.size();
      for (byte b = 0; b < i; b++) {
        FullSpanItem fullSpanItem = this.mFullSpanItems.get(b);
        if (fullSpanItem.mPosition == param1FullSpanItem.mPosition)
          this.mFullSpanItems.remove(b); 
        if (fullSpanItem.mPosition >= param1FullSpanItem.mPosition) {
          this.mFullSpanItems.add(b, param1FullSpanItem);
          return;
        } 
      } 
      this.mFullSpanItems.add(param1FullSpanItem);
    }
    
    void clear() {
      int[] arrayOfInt = this.mData;
      if (arrayOfInt != null)
        Arrays.fill(arrayOfInt, -1); 
      this.mFullSpanItems = null;
    }
    
    void ensureSize(int param1Int) {
      int[] arrayOfInt = this.mData;
      if (arrayOfInt == null) {
        arrayOfInt = new int[Math.max(param1Int, 10) + 1];
        this.mData = arrayOfInt;
        Arrays.fill(arrayOfInt, -1);
      } else if (param1Int >= arrayOfInt.length) {
        int[] arrayOfInt1 = new int[sizeForPosition(param1Int)];
        this.mData = arrayOfInt1;
        System.arraycopy(arrayOfInt, 0, arrayOfInt1, 0, arrayOfInt.length);
        arrayOfInt1 = this.mData;
        Arrays.fill(arrayOfInt1, arrayOfInt.length, arrayOfInt1.length, -1);
      } 
    }
    
    int forceInvalidateAfter(int param1Int) {
      List<FullSpanItem> list = this.mFullSpanItems;
      if (list != null)
        for (int i = list.size() - 1; i >= 0; i--) {
          if (((FullSpanItem)this.mFullSpanItems.get(i)).mPosition >= param1Int)
            this.mFullSpanItems.remove(i); 
        }  
      return invalidateAfter(param1Int);
    }
    
    public FullSpanItem getFirstFullSpanItemInRange(int param1Int1, int param1Int2, int param1Int3, boolean param1Boolean) {
      List<FullSpanItem> list = this.mFullSpanItems;
      if (list == null)
        return null; 
      int i = list.size();
      for (byte b = 0; b < i; b++) {
        FullSpanItem fullSpanItem = this.mFullSpanItems.get(b);
        if (fullSpanItem.mPosition >= param1Int2)
          return null; 
        if (fullSpanItem.mPosition >= param1Int1 && (param1Int3 == 0 || fullSpanItem.mGapDir == param1Int3 || (param1Boolean && fullSpanItem.mHasUnwantedGapAfter)))
          return fullSpanItem; 
      } 
      return null;
    }
    
    public FullSpanItem getFullSpanItem(int param1Int) {
      List<FullSpanItem> list = this.mFullSpanItems;
      if (list == null)
        return null; 
      for (int i = list.size() - 1; i >= 0; i--) {
        FullSpanItem fullSpanItem = this.mFullSpanItems.get(i);
        if (fullSpanItem.mPosition == param1Int)
          return fullSpanItem; 
      } 
      return null;
    }
    
    int getSpan(int param1Int) {
      int[] arrayOfInt = this.mData;
      return (arrayOfInt == null || param1Int >= arrayOfInt.length) ? -1 : arrayOfInt[param1Int];
    }
    
    int invalidateAfter(int param1Int) {
      int[] arrayOfInt = this.mData;
      if (arrayOfInt == null)
        return -1; 
      if (param1Int >= arrayOfInt.length)
        return -1; 
      int i = invalidateFullSpansAfter(param1Int);
      if (i == -1) {
        arrayOfInt = this.mData;
        Arrays.fill(arrayOfInt, param1Int, arrayOfInt.length, -1);
        return this.mData.length;
      } 
      arrayOfInt = this.mData;
      Arrays.fill(arrayOfInt, param1Int, ++i, -1);
      return i;
    }
    
    void offsetForAddition(int param1Int1, int param1Int2) {
      int[] arrayOfInt = this.mData;
      if (arrayOfInt != null && param1Int1 < arrayOfInt.length) {
        int i = param1Int1 + param1Int2;
        ensureSize(i);
        arrayOfInt = this.mData;
        System.arraycopy(arrayOfInt, param1Int1, arrayOfInt, i, arrayOfInt.length - param1Int1 - param1Int2);
        Arrays.fill(this.mData, param1Int1, i, -1);
        offsetFullSpansForAddition(param1Int1, param1Int2);
      } 
    }
    
    void offsetForRemoval(int param1Int1, int param1Int2) {
      int[] arrayOfInt = this.mData;
      if (arrayOfInt != null && param1Int1 < arrayOfInt.length) {
        int i = param1Int1 + param1Int2;
        ensureSize(i);
        arrayOfInt = this.mData;
        System.arraycopy(arrayOfInt, i, arrayOfInt, param1Int1, arrayOfInt.length - param1Int1 - param1Int2);
        arrayOfInt = this.mData;
        Arrays.fill(arrayOfInt, arrayOfInt.length - param1Int2, arrayOfInt.length, -1);
        offsetFullSpansForRemoval(param1Int1, param1Int2);
      } 
    }
    
    void setSpan(int param1Int, StaggeredGridLayoutManager.Span param1Span) {
      ensureSize(param1Int);
      this.mData[param1Int] = param1Span.mIndex;
    }
    
    int sizeForPosition(int param1Int) {
      int i;
      for (i = this.mData.length; i <= param1Int; i *= 2);
      return i;
    }
    
    static class FullSpanItem implements Parcelable {
      public static final Parcelable.Creator<FullSpanItem> CREATOR = new Parcelable.Creator<FullSpanItem>() {
          public StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem createFromParcel(Parcel param3Parcel) {
            return new StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem(param3Parcel);
          }
          
          public StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem[] newArray(int param3Int) {
            return new StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem[param3Int];
          }
        };
      
      int mGapDir;
      
      int[] mGapPerSpan;
      
      boolean mHasUnwantedGapAfter;
      
      int mPosition;
      
      FullSpanItem() {}
      
      FullSpanItem(Parcel param2Parcel) {
        this.mPosition = param2Parcel.readInt();
        this.mGapDir = param2Parcel.readInt();
        int i = param2Parcel.readInt();
        boolean bool = true;
        if (i != 1)
          bool = false; 
        this.mHasUnwantedGapAfter = bool;
        i = param2Parcel.readInt();
        if (i > 0) {
          int[] arrayOfInt = new int[i];
          this.mGapPerSpan = arrayOfInt;
          param2Parcel.readIntArray(arrayOfInt);
        } 
      }
      
      public int describeContents() {
        return 0;
      }
      
      int getGapForSpan(int param2Int) {
        int[] arrayOfInt = this.mGapPerSpan;
        if (arrayOfInt == null) {
          param2Int = 0;
        } else {
          param2Int = arrayOfInt[param2Int];
        } 
        return param2Int;
      }
      
      public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FullSpanItem{mPosition=");
        stringBuilder.append(this.mPosition);
        stringBuilder.append(", mGapDir=");
        stringBuilder.append(this.mGapDir);
        stringBuilder.append(", mHasUnwantedGapAfter=");
        stringBuilder.append(this.mHasUnwantedGapAfter);
        stringBuilder.append(", mGapPerSpan=");
        stringBuilder.append(Arrays.toString(this.mGapPerSpan));
        stringBuilder.append('}');
        return stringBuilder.toString();
      }
      
      public void writeToParcel(Parcel param2Parcel, int param2Int) {
        param2Parcel.writeInt(this.mPosition);
        param2Parcel.writeInt(this.mGapDir);
        param2Parcel.writeInt(this.mHasUnwantedGapAfter);
        int[] arrayOfInt = this.mGapPerSpan;
        if (arrayOfInt != null && arrayOfInt.length > 0) {
          param2Parcel.writeInt(arrayOfInt.length);
          param2Parcel.writeIntArray(this.mGapPerSpan);
        } else {
          param2Parcel.writeInt(0);
        } 
      }
    }
    
    static final class null implements Parcelable.Creator<FullSpanItem> {
      public StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem createFromParcel(Parcel param2Parcel) {
        return new StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem(param2Parcel);
      }
      
      public StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem[] newArray(int param2Int) {
        return new StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem[param2Int];
      }
    }
  }
  
  static class FullSpanItem implements Parcelable {
    public static final Parcelable.Creator<FullSpanItem> CREATOR = new Parcelable.Creator<FullSpanItem>() {
        public StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem createFromParcel(Parcel param3Parcel) {
          return new StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem(param3Parcel);
        }
        
        public StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem[] newArray(int param3Int) {
          return new StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem[param3Int];
        }
      };
    
    int mGapDir;
    
    int[] mGapPerSpan;
    
    boolean mHasUnwantedGapAfter;
    
    int mPosition;
    
    FullSpanItem() {}
    
    FullSpanItem(Parcel param1Parcel) {
      this.mPosition = param1Parcel.readInt();
      this.mGapDir = param1Parcel.readInt();
      int i = param1Parcel.readInt();
      boolean bool = true;
      if (i != 1)
        bool = false; 
      this.mHasUnwantedGapAfter = bool;
      i = param1Parcel.readInt();
      if (i > 0) {
        int[] arrayOfInt = new int[i];
        this.mGapPerSpan = arrayOfInt;
        param1Parcel.readIntArray(arrayOfInt);
      } 
    }
    
    public int describeContents() {
      return 0;
    }
    
    int getGapForSpan(int param1Int) {
      int[] arrayOfInt = this.mGapPerSpan;
      if (arrayOfInt == null) {
        param1Int = 0;
      } else {
        param1Int = arrayOfInt[param1Int];
      } 
      return param1Int;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("FullSpanItem{mPosition=");
      stringBuilder.append(this.mPosition);
      stringBuilder.append(", mGapDir=");
      stringBuilder.append(this.mGapDir);
      stringBuilder.append(", mHasUnwantedGapAfter=");
      stringBuilder.append(this.mHasUnwantedGapAfter);
      stringBuilder.append(", mGapPerSpan=");
      stringBuilder.append(Arrays.toString(this.mGapPerSpan));
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeInt(this.mPosition);
      param1Parcel.writeInt(this.mGapDir);
      param1Parcel.writeInt(this.mHasUnwantedGapAfter);
      int[] arrayOfInt = this.mGapPerSpan;
      if (arrayOfInt != null && arrayOfInt.length > 0) {
        param1Parcel.writeInt(arrayOfInt.length);
        param1Parcel.writeIntArray(this.mGapPerSpan);
      } else {
        param1Parcel.writeInt(0);
      } 
    }
  }
  
  static final class null implements Parcelable.Creator<LazySpanLookup.FullSpanItem> {
    public StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem createFromParcel(Parcel param1Parcel) {
      return new StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem(param1Parcel);
    }
    
    public StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem[] newArray(int param1Int) {
      return new StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem[param1Int];
    }
  }
  
  public static class SavedState implements Parcelable {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
        public StaggeredGridLayoutManager.SavedState createFromParcel(Parcel param2Parcel) {
          return new StaggeredGridLayoutManager.SavedState(param2Parcel);
        }
        
        public StaggeredGridLayoutManager.SavedState[] newArray(int param2Int) {
          return new StaggeredGridLayoutManager.SavedState[param2Int];
        }
      };
    
    boolean mAnchorLayoutFromEnd;
    
    int mAnchorPosition;
    
    List<StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem> mFullSpanItems;
    
    boolean mLastLayoutRTL;
    
    boolean mReverseLayout;
    
    int[] mSpanLookup;
    
    int mSpanLookupSize;
    
    int[] mSpanOffsets;
    
    int mSpanOffsetsSize;
    
    int mVisibleAnchorPosition;
    
    public SavedState() {}
    
    SavedState(Parcel param1Parcel) {
      this.mAnchorPosition = param1Parcel.readInt();
      this.mVisibleAnchorPosition = param1Parcel.readInt();
      int i = param1Parcel.readInt();
      this.mSpanOffsetsSize = i;
      if (i > 0) {
        int[] arrayOfInt = new int[i];
        this.mSpanOffsets = arrayOfInt;
        param1Parcel.readIntArray(arrayOfInt);
      } 
      i = param1Parcel.readInt();
      this.mSpanLookupSize = i;
      if (i > 0) {
        int[] arrayOfInt = new int[i];
        this.mSpanLookup = arrayOfInt;
        param1Parcel.readIntArray(arrayOfInt);
      } 
      i = param1Parcel.readInt();
      boolean bool1 = false;
      if (i == 1) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      this.mReverseLayout = bool2;
      if (param1Parcel.readInt() == 1) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      this.mAnchorLayoutFromEnd = bool2;
      boolean bool2 = bool1;
      if (param1Parcel.readInt() == 1)
        bool2 = true; 
      this.mLastLayoutRTL = bool2;
      this.mFullSpanItems = param1Parcel.readArrayList(StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem.class.getClassLoader());
    }
    
    public SavedState(SavedState param1SavedState) {
      this.mSpanOffsetsSize = param1SavedState.mSpanOffsetsSize;
      this.mAnchorPosition = param1SavedState.mAnchorPosition;
      this.mVisibleAnchorPosition = param1SavedState.mVisibleAnchorPosition;
      this.mSpanOffsets = param1SavedState.mSpanOffsets;
      this.mSpanLookupSize = param1SavedState.mSpanLookupSize;
      this.mSpanLookup = param1SavedState.mSpanLookup;
      this.mReverseLayout = param1SavedState.mReverseLayout;
      this.mAnchorLayoutFromEnd = param1SavedState.mAnchorLayoutFromEnd;
      this.mLastLayoutRTL = param1SavedState.mLastLayoutRTL;
      this.mFullSpanItems = param1SavedState.mFullSpanItems;
    }
    
    public int describeContents() {
      return 0;
    }
    
    void invalidateAnchorPositionInfo() {
      this.mSpanOffsets = null;
      this.mSpanOffsetsSize = 0;
      this.mAnchorPosition = -1;
      this.mVisibleAnchorPosition = -1;
    }
    
    void invalidateSpanInfo() {
      this.mSpanOffsets = null;
      this.mSpanOffsetsSize = 0;
      this.mSpanLookupSize = 0;
      this.mSpanLookup = null;
      this.mFullSpanItems = null;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeInt(this.mAnchorPosition);
      param1Parcel.writeInt(this.mVisibleAnchorPosition);
      param1Parcel.writeInt(this.mSpanOffsetsSize);
      if (this.mSpanOffsetsSize > 0)
        param1Parcel.writeIntArray(this.mSpanOffsets); 
      param1Parcel.writeInt(this.mSpanLookupSize);
      if (this.mSpanLookupSize > 0)
        param1Parcel.writeIntArray(this.mSpanLookup); 
      param1Parcel.writeInt(this.mReverseLayout);
      param1Parcel.writeInt(this.mAnchorLayoutFromEnd);
      param1Parcel.writeInt(this.mLastLayoutRTL);
      param1Parcel.writeList(this.mFullSpanItems);
    }
  }
  
  static final class null implements Parcelable.Creator<SavedState> {
    public StaggeredGridLayoutManager.SavedState createFromParcel(Parcel param1Parcel) {
      return new StaggeredGridLayoutManager.SavedState(param1Parcel);
    }
    
    public StaggeredGridLayoutManager.SavedState[] newArray(int param1Int) {
      return new StaggeredGridLayoutManager.SavedState[param1Int];
    }
  }
  
  class Span {
    static final int INVALID_LINE = -2147483648;
    
    int mCachedEnd = Integer.MIN_VALUE;
    
    int mCachedStart = Integer.MIN_VALUE;
    
    int mDeletedSize = 0;
    
    final int mIndex;
    
    ArrayList<View> mViews = new ArrayList<View>();
    
    Span(int param1Int) {
      this.mIndex = param1Int;
    }
    
    void appendToSpan(View param1View) {
      StaggeredGridLayoutManager.LayoutParams layoutParams = getLayoutParams(param1View);
      layoutParams.mSpan = this;
      this.mViews.add(param1View);
      this.mCachedEnd = Integer.MIN_VALUE;
      if (this.mViews.size() == 1)
        this.mCachedStart = Integer.MIN_VALUE; 
      if (layoutParams.isItemRemoved() || layoutParams.isItemChanged())
        this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(param1View); 
    }
    
    void cacheReferenceLineAndClear(boolean param1Boolean, int param1Int) {
      int i;
      if (param1Boolean) {
        i = getEndLine(-2147483648);
      } else {
        i = getStartLine(-2147483648);
      } 
      clear();
      if (i == Integer.MIN_VALUE)
        return; 
      if ((param1Boolean && i < StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding()) || (!param1Boolean && i > StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding()))
        return; 
      int j = i;
      if (param1Int != Integer.MIN_VALUE)
        j = i + param1Int; 
      this.mCachedEnd = j;
      this.mCachedStart = j;
    }
    
    void calculateCachedEnd() {
      ArrayList<View> arrayList = this.mViews;
      View view = arrayList.get(arrayList.size() - 1);
      StaggeredGridLayoutManager.LayoutParams layoutParams = getLayoutParams(view);
      this.mCachedEnd = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(view);
      if (layoutParams.mFullSpan) {
        StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem fullSpanItem = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem(layoutParams.getViewLayoutPosition());
        if (fullSpanItem != null && fullSpanItem.mGapDir == 1)
          this.mCachedEnd += fullSpanItem.getGapForSpan(this.mIndex); 
      } 
    }
    
    void calculateCachedStart() {
      View view = this.mViews.get(0);
      StaggeredGridLayoutManager.LayoutParams layoutParams = getLayoutParams(view);
      this.mCachedStart = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(view);
      if (layoutParams.mFullSpan) {
        StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem fullSpanItem = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem(layoutParams.getViewLayoutPosition());
        if (fullSpanItem != null && fullSpanItem.mGapDir == -1)
          this.mCachedStart -= fullSpanItem.getGapForSpan(this.mIndex); 
      } 
    }
    
    void clear() {
      this.mViews.clear();
      invalidateCache();
      this.mDeletedSize = 0;
    }
    
    public int findFirstCompletelyVisibleItemPosition() {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = findOneVisibleChild(this.mViews.size() - 1, -1, true);
      } else {
        i = findOneVisibleChild(0, this.mViews.size(), true);
      } 
      return i;
    }
    
    public int findFirstPartiallyVisibleItemPosition() {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
      } else {
        i = findOnePartiallyVisibleChild(0, this.mViews.size(), true);
      } 
      return i;
    }
    
    public int findFirstVisibleItemPosition() {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = findOneVisibleChild(this.mViews.size() - 1, -1, false);
      } else {
        i = findOneVisibleChild(0, this.mViews.size(), false);
      } 
      return i;
    }
    
    public int findLastCompletelyVisibleItemPosition() {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = findOneVisibleChild(0, this.mViews.size(), true);
      } else {
        i = findOneVisibleChild(this.mViews.size() - 1, -1, true);
      } 
      return i;
    }
    
    public int findLastPartiallyVisibleItemPosition() {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = findOnePartiallyVisibleChild(0, this.mViews.size(), true);
      } else {
        i = findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
      } 
      return i;
    }
    
    public int findLastVisibleItemPosition() {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = findOneVisibleChild(0, this.mViews.size(), false);
      } else {
        i = findOneVisibleChild(this.mViews.size() - 1, -1, false);
      } 
      return i;
    }
    
    int findOnePartiallyOrCompletelyVisibleChild(int param1Int1, int param1Int2, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3) {
      byte b;
      int i = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
      int j = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding();
      if (param1Int2 > param1Int1) {
        b = 1;
      } else {
        b = -1;
      } 
      while (param1Int1 != param1Int2) {
        boolean bool2;
        View view = this.mViews.get(param1Int1);
        int k = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(view);
        int m = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(view);
        boolean bool1 = false;
        if (param1Boolean3 ? (k <= j) : (k < j)) {
          bool2 = true;
        } else {
          bool2 = false;
        } 
        if (param1Boolean3 ? (m >= i) : (m > i))
          bool1 = true; 
        if (bool2 && bool1)
          if (param1Boolean1 && param1Boolean2) {
            if (k >= i && m <= j)
              return StaggeredGridLayoutManager.this.getPosition(view); 
          } else {
            if (param1Boolean2)
              return StaggeredGridLayoutManager.this.getPosition(view); 
            if (k < i || m > j)
              return StaggeredGridLayoutManager.this.getPosition(view); 
          }  
        param1Int1 += b;
      } 
      return -1;
    }
    
    int findOnePartiallyVisibleChild(int param1Int1, int param1Int2, boolean param1Boolean) {
      return findOnePartiallyOrCompletelyVisibleChild(param1Int1, param1Int2, false, false, param1Boolean);
    }
    
    int findOneVisibleChild(int param1Int1, int param1Int2, boolean param1Boolean) {
      return findOnePartiallyOrCompletelyVisibleChild(param1Int1, param1Int2, param1Boolean, true, false);
    }
    
    public int getDeletedSize() {
      return this.mDeletedSize;
    }
    
    int getEndLine() {
      int i = this.mCachedEnd;
      if (i != Integer.MIN_VALUE)
        return i; 
      calculateCachedEnd();
      return this.mCachedEnd;
    }
    
    int getEndLine(int param1Int) {
      int i = this.mCachedEnd;
      if (i != Integer.MIN_VALUE)
        return i; 
      if (this.mViews.size() == 0)
        return param1Int; 
      calculateCachedEnd();
      return this.mCachedEnd;
    }
    
    public View getFocusableViewAfter(int param1Int1, int param1Int2) {
      // Byte code:
      //   0: aconst_null
      //   1: astore_3
      //   2: aconst_null
      //   3: astore #4
      //   5: iload_2
      //   6: iconst_m1
      //   7: if_icmpne -> 119
      //   10: aload_0
      //   11: getfield mViews : Ljava/util/ArrayList;
      //   14: invokevirtual size : ()I
      //   17: istore #5
      //   19: iconst_0
      //   20: istore_2
      //   21: aload #4
      //   23: astore_3
      //   24: iload_2
      //   25: iload #5
      //   27: if_icmpge -> 228
      //   30: aload_0
      //   31: getfield mViews : Ljava/util/ArrayList;
      //   34: iload_2
      //   35: invokevirtual get : (I)Ljava/lang/Object;
      //   38: checkcast android/view/View
      //   41: astore #6
      //   43: aload_0
      //   44: getfield this$0 : Landroid/support/v7/widget/StaggeredGridLayoutManager;
      //   47: getfield mReverseLayout : Z
      //   50: ifeq -> 69
      //   53: aload #4
      //   55: astore_3
      //   56: aload_0
      //   57: getfield this$0 : Landroid/support/v7/widget/StaggeredGridLayoutManager;
      //   60: aload #6
      //   62: invokevirtual getPosition : (Landroid/view/View;)I
      //   65: iload_1
      //   66: if_icmple -> 228
      //   69: aload_0
      //   70: getfield this$0 : Landroid/support/v7/widget/StaggeredGridLayoutManager;
      //   73: getfield mReverseLayout : Z
      //   76: ifne -> 98
      //   79: aload_0
      //   80: getfield this$0 : Landroid/support/v7/widget/StaggeredGridLayoutManager;
      //   83: aload #6
      //   85: invokevirtual getPosition : (Landroid/view/View;)I
      //   88: iload_1
      //   89: if_icmplt -> 98
      //   92: aload #4
      //   94: astore_3
      //   95: goto -> 228
      //   98: aload #4
      //   100: astore_3
      //   101: aload #6
      //   103: invokevirtual hasFocusable : ()Z
      //   106: ifeq -> 228
      //   109: iinc #2, 1
      //   112: aload #6
      //   114: astore #4
      //   116: goto -> 21
      //   119: aload_0
      //   120: getfield mViews : Ljava/util/ArrayList;
      //   123: invokevirtual size : ()I
      //   126: iconst_1
      //   127: isub
      //   128: istore_2
      //   129: aload_3
      //   130: astore #4
      //   132: aload #4
      //   134: astore_3
      //   135: iload_2
      //   136: iflt -> 228
      //   139: aload_0
      //   140: getfield mViews : Ljava/util/ArrayList;
      //   143: iload_2
      //   144: invokevirtual get : (I)Ljava/lang/Object;
      //   147: checkcast android/view/View
      //   150: astore #6
      //   152: aload_0
      //   153: getfield this$0 : Landroid/support/v7/widget/StaggeredGridLayoutManager;
      //   156: getfield mReverseLayout : Z
      //   159: ifeq -> 178
      //   162: aload #4
      //   164: astore_3
      //   165: aload_0
      //   166: getfield this$0 : Landroid/support/v7/widget/StaggeredGridLayoutManager;
      //   169: aload #6
      //   171: invokevirtual getPosition : (Landroid/view/View;)I
      //   174: iload_1
      //   175: if_icmpge -> 228
      //   178: aload_0
      //   179: getfield this$0 : Landroid/support/v7/widget/StaggeredGridLayoutManager;
      //   182: getfield mReverseLayout : Z
      //   185: ifne -> 207
      //   188: aload_0
      //   189: getfield this$0 : Landroid/support/v7/widget/StaggeredGridLayoutManager;
      //   192: aload #6
      //   194: invokevirtual getPosition : (Landroid/view/View;)I
      //   197: iload_1
      //   198: if_icmpgt -> 207
      //   201: aload #4
      //   203: astore_3
      //   204: goto -> 228
      //   207: aload #4
      //   209: astore_3
      //   210: aload #6
      //   212: invokevirtual hasFocusable : ()Z
      //   215: ifeq -> 228
      //   218: iinc #2, -1
      //   221: aload #6
      //   223: astore #4
      //   225: goto -> 132
      //   228: aload_3
      //   229: areturn
    }
    
    StaggeredGridLayoutManager.LayoutParams getLayoutParams(View param1View) {
      return (StaggeredGridLayoutManager.LayoutParams)param1View.getLayoutParams();
    }
    
    int getStartLine() {
      int i = this.mCachedStart;
      if (i != Integer.MIN_VALUE)
        return i; 
      calculateCachedStart();
      return this.mCachedStart;
    }
    
    int getStartLine(int param1Int) {
      int i = this.mCachedStart;
      if (i != Integer.MIN_VALUE)
        return i; 
      if (this.mViews.size() == 0)
        return param1Int; 
      calculateCachedStart();
      return this.mCachedStart;
    }
    
    void invalidateCache() {
      this.mCachedStart = Integer.MIN_VALUE;
      this.mCachedEnd = Integer.MIN_VALUE;
    }
    
    void onOffset(int param1Int) {
      int i = this.mCachedStart;
      if (i != Integer.MIN_VALUE)
        this.mCachedStart = i + param1Int; 
      i = this.mCachedEnd;
      if (i != Integer.MIN_VALUE)
        this.mCachedEnd = i + param1Int; 
    }
    
    void popEnd() {
      int i = this.mViews.size();
      View view = this.mViews.remove(i - 1);
      StaggeredGridLayoutManager.LayoutParams layoutParams = getLayoutParams(view);
      layoutParams.mSpan = null;
      if (layoutParams.isItemRemoved() || layoutParams.isItemChanged())
        this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view); 
      if (i == 1)
        this.mCachedStart = Integer.MIN_VALUE; 
      this.mCachedEnd = Integer.MIN_VALUE;
    }
    
    void popStart() {
      View view = this.mViews.remove(0);
      StaggeredGridLayoutManager.LayoutParams layoutParams = getLayoutParams(view);
      layoutParams.mSpan = null;
      if (this.mViews.size() == 0)
        this.mCachedEnd = Integer.MIN_VALUE; 
      if (layoutParams.isItemRemoved() || layoutParams.isItemChanged())
        this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view); 
      this.mCachedStart = Integer.MIN_VALUE;
    }
    
    void prependToSpan(View param1View) {
      StaggeredGridLayoutManager.LayoutParams layoutParams = getLayoutParams(param1View);
      layoutParams.mSpan = this;
      this.mViews.add(0, param1View);
      this.mCachedStart = Integer.MIN_VALUE;
      if (this.mViews.size() == 1)
        this.mCachedEnd = Integer.MIN_VALUE; 
      if (layoutParams.isItemRemoved() || layoutParams.isItemChanged())
        this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(param1View); 
    }
    
    void setLine(int param1Int) {
      this.mCachedStart = param1Int;
      this.mCachedEnd = param1Int;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\StaggeredGridLayoutManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */