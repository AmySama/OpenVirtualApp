package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.Arrays;

public class GridLayoutManager extends LinearLayoutManager {
  private static final boolean DEBUG = false;
  
  public static final int DEFAULT_SPAN_COUNT = -1;
  
  private static final String TAG = "GridLayoutManager";
  
  int[] mCachedBorders;
  
  final Rect mDecorInsets = new Rect();
  
  boolean mPendingSpanCountChange = false;
  
  final SparseIntArray mPreLayoutSpanIndexCache = new SparseIntArray();
  
  final SparseIntArray mPreLayoutSpanSizeCache = new SparseIntArray();
  
  View[] mSet;
  
  int mSpanCount = -1;
  
  SpanSizeLookup mSpanSizeLookup = new DefaultSpanSizeLookup();
  
  public GridLayoutManager(Context paramContext, int paramInt) {
    super(paramContext);
    setSpanCount(paramInt);
  }
  
  public GridLayoutManager(Context paramContext, int paramInt1, int paramInt2, boolean paramBoolean) {
    super(paramContext, paramInt2, paramBoolean);
    setSpanCount(paramInt1);
  }
  
  public GridLayoutManager(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    setSpanCount((getProperties(paramContext, paramAttributeSet, paramInt1, paramInt2)).spanCount);
  }
  
  private void assignSpans(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt1, int paramInt2, boolean paramBoolean) {
    int i = 0;
    int j = -1;
    if (paramBoolean) {
      boolean bool = false;
      paramInt2 = 1;
      j = paramInt1;
      paramInt1 = bool;
    } else {
      paramInt1--;
      paramInt2 = -1;
    } 
    while (paramInt1 != j) {
      View view = this.mSet[paramInt1];
      LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
      layoutParams.mSpanSize = getSpanSize(paramRecycler, paramState, getPosition(view));
      layoutParams.mSpanIndex = i;
      i += layoutParams.mSpanSize;
      paramInt1 += paramInt2;
    } 
  }
  
  private void cachePreLayoutSpanMapping() {
    int i = getChildCount();
    for (byte b = 0; b < i; b++) {
      LayoutParams layoutParams = (LayoutParams)getChildAt(b).getLayoutParams();
      int j = layoutParams.getViewLayoutPosition();
      this.mPreLayoutSpanSizeCache.put(j, layoutParams.getSpanSize());
      this.mPreLayoutSpanIndexCache.put(j, layoutParams.getSpanIndex());
    } 
  }
  
  private void calculateItemBorders(int paramInt) {
    this.mCachedBorders = calculateItemBorders(this.mCachedBorders, this.mSpanCount, paramInt);
  }
  
  static int[] calculateItemBorders(int[] paramArrayOfint, int paramInt1, int paramInt2) {
    // Byte code:
    //   0: iconst_1
    //   1: istore_3
    //   2: aload_0
    //   3: ifnull -> 27
    //   6: aload_0
    //   7: arraylength
    //   8: iload_1
    //   9: iconst_1
    //   10: iadd
    //   11: if_icmpne -> 27
    //   14: aload_0
    //   15: astore #4
    //   17: aload_0
    //   18: aload_0
    //   19: arraylength
    //   20: iconst_1
    //   21: isub
    //   22: iaload
    //   23: iload_2
    //   24: if_icmpeq -> 34
    //   27: iload_1
    //   28: iconst_1
    //   29: iadd
    //   30: newarray int
    //   32: astore #4
    //   34: iconst_0
    //   35: istore #5
    //   37: aload #4
    //   39: iconst_0
    //   40: iconst_0
    //   41: iastore
    //   42: iload_2
    //   43: iload_1
    //   44: idiv
    //   45: istore #6
    //   47: iload_2
    //   48: iload_1
    //   49: irem
    //   50: istore #7
    //   52: iconst_0
    //   53: istore #8
    //   55: iload #5
    //   57: istore_2
    //   58: iload_3
    //   59: iload_1
    //   60: if_icmpgt -> 116
    //   63: iload_2
    //   64: iload #7
    //   66: iadd
    //   67: istore_2
    //   68: iload_2
    //   69: ifle -> 93
    //   72: iload_1
    //   73: iload_2
    //   74: isub
    //   75: iload #7
    //   77: if_icmpge -> 93
    //   80: iload #6
    //   82: iconst_1
    //   83: iadd
    //   84: istore #5
    //   86: iload_2
    //   87: iload_1
    //   88: isub
    //   89: istore_2
    //   90: goto -> 97
    //   93: iload #6
    //   95: istore #5
    //   97: iload #8
    //   99: iload #5
    //   101: iadd
    //   102: istore #8
    //   104: aload #4
    //   106: iload_3
    //   107: iload #8
    //   109: iastore
    //   110: iinc #3, 1
    //   113: goto -> 58
    //   116: aload #4
    //   118: areturn
  }
  
  private void clearPreLayoutSpanMappingCache() {
    this.mPreLayoutSpanSizeCache.clear();
    this.mPreLayoutSpanIndexCache.clear();
  }
  
  private void ensureAnchorIsInCorrectSpan(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, LinearLayoutManager.AnchorInfo paramAnchorInfo, int paramInt) {
    if (paramInt == 1) {
      paramInt = 1;
    } else {
      paramInt = 0;
    } 
    int i = getSpanIndex(paramRecycler, paramState, paramAnchorInfo.mPosition);
    if (paramInt != 0) {
      while (i > 0 && paramAnchorInfo.mPosition > 0) {
        paramAnchorInfo.mPosition--;
        i = getSpanIndex(paramRecycler, paramState, paramAnchorInfo.mPosition);
      } 
    } else {
      int j = paramState.getItemCount();
      paramInt = paramAnchorInfo.mPosition;
      while (paramInt < j - 1) {
        int k = paramInt + 1;
        int m = getSpanIndex(paramRecycler, paramState, k);
        if (m > i) {
          paramInt = k;
          i = m;
        } 
      } 
      paramAnchorInfo.mPosition = paramInt;
    } 
  }
  
  private void ensureViewSet() {
    View[] arrayOfView = this.mSet;
    if (arrayOfView == null || arrayOfView.length != this.mSpanCount)
      this.mSet = new View[this.mSpanCount]; 
  }
  
  private int getSpanGroupIndex(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt) {
    if (!paramState.isPreLayout())
      return this.mSpanSizeLookup.getSpanGroupIndex(paramInt, this.mSpanCount); 
    int i = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (i == -1) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Cannot find span size for pre layout position. ");
      stringBuilder.append(paramInt);
      Log.w("GridLayoutManager", stringBuilder.toString());
      return 0;
    } 
    return this.mSpanSizeLookup.getSpanGroupIndex(i, this.mSpanCount);
  }
  
  private int getSpanIndex(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt) {
    if (!paramState.isPreLayout())
      return this.mSpanSizeLookup.getCachedSpanIndex(paramInt, this.mSpanCount); 
    int i = this.mPreLayoutSpanIndexCache.get(paramInt, -1);
    if (i != -1)
      return i; 
    i = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (i == -1) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:");
      stringBuilder.append(paramInt);
      Log.w("GridLayoutManager", stringBuilder.toString());
      return 0;
    } 
    return this.mSpanSizeLookup.getCachedSpanIndex(i, this.mSpanCount);
  }
  
  private int getSpanSize(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt) {
    if (!paramState.isPreLayout())
      return this.mSpanSizeLookup.getSpanSize(paramInt); 
    int i = this.mPreLayoutSpanSizeCache.get(paramInt, -1);
    if (i != -1)
      return i; 
    i = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (i == -1) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:");
      stringBuilder.append(paramInt);
      Log.w("GridLayoutManager", stringBuilder.toString());
      return 1;
    } 
    return this.mSpanSizeLookup.getSpanSize(i);
  }
  
  private void guessMeasurement(float paramFloat, int paramInt) {
    calculateItemBorders(Math.max(Math.round(paramFloat * this.mSpanCount), paramInt));
  }
  
  private void measureChild(View paramView, int paramInt, boolean paramBoolean) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    Rect rect = layoutParams.mDecorInsets;
    int i = rect.top + rect.bottom + layoutParams.topMargin + layoutParams.bottomMargin;
    int j = rect.left + rect.right + layoutParams.leftMargin + layoutParams.rightMargin;
    int k = getSpaceForSpanRange(layoutParams.mSpanIndex, layoutParams.mSpanSize);
    if (this.mOrientation == 1) {
      j = getChildMeasureSpec(k, paramInt, j, layoutParams.width, false);
      paramInt = getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getHeightMode(), i, layoutParams.height, true);
    } else {
      paramInt = getChildMeasureSpec(k, paramInt, i, layoutParams.height, false);
      j = getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getWidthMode(), j, layoutParams.width, true);
    } 
    measureChildWithDecorationsAndMargin(paramView, j, paramInt, paramBoolean);
  }
  
  private void measureChildWithDecorationsAndMargin(View paramView, int paramInt1, int paramInt2, boolean paramBoolean) {
    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
    if (paramBoolean) {
      paramBoolean = shouldReMeasureChild(paramView, paramInt1, paramInt2, layoutParams);
    } else {
      paramBoolean = shouldMeasureChild(paramView, paramInt1, paramInt2, layoutParams);
    } 
    if (paramBoolean)
      paramView.measure(paramInt1, paramInt2); 
  }
  
  private void updateMeasurements() {
    int i;
    int j;
    if (getOrientation() == 1) {
      i = getWidth() - getPaddingRight();
      j = getPaddingLeft();
    } else {
      i = getHeight() - getPaddingBottom();
      j = getPaddingTop();
    } 
    calculateItemBorders(i - j);
  }
  
  public boolean checkLayoutParams(RecyclerView.LayoutParams paramLayoutParams) {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  void collectPrefetchPositionsForLayoutState(RecyclerView.State paramState, LinearLayoutManager.LayoutState paramLayoutState, RecyclerView.LayoutManager.LayoutPrefetchRegistry paramLayoutPrefetchRegistry) {
    int i = this.mSpanCount;
    for (byte b = 0; b < this.mSpanCount && paramLayoutState.hasMore(paramState) && i > 0; b++) {
      int j = paramLayoutState.mCurrentPosition;
      paramLayoutPrefetchRegistry.addPosition(j, Math.max(0, paramLayoutState.mScrollingOffset));
      i -= this.mSpanSizeLookup.getSpanSize(j);
      paramLayoutState.mCurrentPosition += paramLayoutState.mItemDirection;
    } 
  }
  
  View findReferenceChild(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt1, int paramInt2, int paramInt3) {
    byte b;
    ensureLayoutState();
    int i = this.mOrientationHelper.getStartAfterPadding();
    int j = this.mOrientationHelper.getEndAfterPadding();
    if (paramInt2 > paramInt1) {
      b = 1;
    } else {
      b = -1;
    } 
    View view1 = null;
    View view2;
    for (view2 = null; paramInt1 != paramInt2; view2 = view5) {
      View view3 = getChildAt(paramInt1);
      int k = getPosition(view3);
      View view4 = view1;
      View view5 = view2;
      if (k >= 0) {
        view4 = view1;
        view5 = view2;
        if (k < paramInt3)
          if (getSpanIndex(paramRecycler, paramState, k) != 0) {
            view4 = view1;
            view5 = view2;
          } else if (((RecyclerView.LayoutParams)view3.getLayoutParams()).isItemRemoved()) {
            view4 = view1;
            view5 = view2;
            if (view2 == null) {
              view5 = view3;
              view4 = view1;
            } 
          } else if (this.mOrientationHelper.getDecoratedStart(view3) >= j || this.mOrientationHelper.getDecoratedEnd(view3) < i) {
            view4 = view1;
            view5 = view2;
            if (view1 == null) {
              view4 = view3;
              view5 = view2;
            } 
          } else {
            return view3;
          }  
      } 
      paramInt1 += b;
      view1 = view4;
    } 
    if (view1 != null)
      view2 = view1; 
    return view2;
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
    return (this.mOrientation == 1) ? this.mSpanCount : ((paramState.getItemCount() < 1) ? 0 : (getSpanGroupIndex(paramRecycler, paramState, paramState.getItemCount() - 1) + 1));
  }
  
  public int getRowCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState) {
    return (this.mOrientation == 0) ? this.mSpanCount : ((paramState.getItemCount() < 1) ? 0 : (getSpanGroupIndex(paramRecycler, paramState, paramState.getItemCount() - 1) + 1));
  }
  
  int getSpaceForSpanRange(int paramInt1, int paramInt2) {
    if (this.mOrientation == 1 && isLayoutRTL()) {
      int[] arrayOfInt1 = this.mCachedBorders;
      int i = this.mSpanCount;
      return arrayOfInt1[i - paramInt1] - arrayOfInt1[i - paramInt1 - paramInt2];
    } 
    int[] arrayOfInt = this.mCachedBorders;
    return arrayOfInt[paramInt2 + paramInt1] - arrayOfInt[paramInt1];
  }
  
  public int getSpanCount() {
    return this.mSpanCount;
  }
  
  public SpanSizeLookup getSpanSizeLookup() {
    return this.mSpanSizeLookup;
  }
  
  void layoutChunk(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, LinearLayoutManager.LayoutState paramLayoutState, LinearLayoutManager.LayoutChunkResult paramLayoutChunkResult) {
    StringBuilder stringBuilder;
    int j;
    int k;
    boolean bool;
    int i = this.mOrientationHelper.getModeInOther();
    if (i != 1073741824) {
      j = 1;
    } else {
      j = 0;
    } 
    if (getChildCount() > 0) {
      k = this.mCachedBorders[this.mSpanCount];
    } else {
      k = 0;
    } 
    if (j)
      updateMeasurements(); 
    if (paramLayoutState.mItemDirection == 1) {
      bool = true;
    } else {
      bool = false;
    } 
    int m = this.mSpanCount;
    if (!bool)
      m = getSpanIndex(paramRecycler, paramState, paramLayoutState.mCurrentPosition) + getSpanSize(paramRecycler, paramState, paramLayoutState.mCurrentPosition); 
    int n = 0;
    byte b = 0;
    while (b < this.mSpanCount && paramLayoutState.hasMore(paramState) && m > 0) {
      int i3 = paramLayoutState.mCurrentPosition;
      int i4 = getSpanSize(paramRecycler, paramState, i3);
      if (i4 <= this.mSpanCount) {
        m -= i4;
        if (m < 0)
          break; 
        View view = paramLayoutState.next(paramRecycler);
        if (view == null)
          break; 
        n += i4;
        this.mSet[b] = view;
        b++;
        continue;
      } 
      stringBuilder = new StringBuilder();
      stringBuilder.append("Item at position ");
      stringBuilder.append(i3);
      stringBuilder.append(" requires ");
      stringBuilder.append(i4);
      stringBuilder.append(" spans but GridLayoutManager has only ");
      stringBuilder.append(this.mSpanCount);
      stringBuilder.append(" spans.");
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    if (b == 0) {
      paramLayoutChunkResult.mFinished = true;
      return;
    } 
    float f = 0.0F;
    assignSpans((RecyclerView.Recycler)stringBuilder, paramState, b, n, bool);
    int i2 = 0;
    m = 0;
    while (i2 < b) {
      View view = this.mSet[i2];
      if (paramLayoutState.mScrapList == null) {
        if (bool) {
          addView(view);
        } else {
          addView(view, 0);
        } 
      } else if (bool) {
        addDisappearingView(view);
      } else {
        addDisappearingView(view, 0);
      } 
      calculateItemDecorationsForChild(view, this.mDecorInsets);
      measureChild(view, i, false);
      int i3 = this.mOrientationHelper.getDecoratedMeasurement(view);
      n = m;
      if (i3 > m)
        n = i3; 
      LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
      float f1 = this.mOrientationHelper.getDecoratedMeasurementInOther(view) * 1.0F / layoutParams.mSpanSize;
      float f2 = f;
      if (f1 > f)
        f2 = f1; 
      i2++;
      m = n;
      f = f2;
    } 
    n = m;
    if (j) {
      guessMeasurement(f, k);
      m = 0;
      j = 0;
      while (true) {
        n = m;
        if (j < b) {
          View view = this.mSet[j];
          measureChild(view, 1073741824, true);
          k = this.mOrientationHelper.getDecoratedMeasurement(view);
          n = m;
          if (k > m)
            n = k; 
          j++;
          m = n;
          continue;
        } 
        break;
      } 
    } 
    for (m = 0; m < b; m++) {
      View view = this.mSet[m];
      if (this.mOrientationHelper.getDecoratedMeasurement(view) != n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        Rect rect = layoutParams.mDecorInsets;
        k = rect.top + rect.bottom + layoutParams.topMargin + layoutParams.bottomMargin;
        j = rect.left + rect.right + layoutParams.leftMargin + layoutParams.rightMargin;
        i2 = getSpaceForSpanRange(layoutParams.mSpanIndex, layoutParams.mSpanSize);
        if (this.mOrientation == 1) {
          j = getChildMeasureSpec(i2, 1073741824, j, layoutParams.width, false);
          k = View.MeasureSpec.makeMeasureSpec(n - k, 1073741824);
        } else {
          j = View.MeasureSpec.makeMeasureSpec(n - j, 1073741824);
          k = getChildMeasureSpec(i2, 1073741824, k, layoutParams.height, false);
        } 
        measureChildWithDecorationsAndMargin(view, j, k, true);
      } 
    } 
    int i1 = 0;
    paramLayoutChunkResult.mConsumed = n;
    if (this.mOrientation == 1) {
      if (paramLayoutState.mLayoutDirection == -1) {
        m = paramLayoutState.mOffset;
        i2 = 0;
        k = 0;
        j = m - n;
        n = i2;
      } else {
        j = paramLayoutState.mOffset;
        m = j + n;
        n = 0;
        k = 0;
      } 
    } else {
      if (paramLayoutState.mLayoutDirection == -1) {
        k = paramLayoutState.mOffset;
        n = k - n;
      } else {
        m = paramLayoutState.mOffset;
        k = m + n;
        n = m;
      } 
      m = 0;
      j = 0;
    } 
    while (i1 < b) {
      View view = this.mSet[i1];
      LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
      if (this.mOrientation == 1) {
        if (isLayoutRTL()) {
          k = getPaddingLeft() + this.mCachedBorders[this.mSpanCount - layoutParams.mSpanIndex];
          i2 = this.mOrientationHelper.getDecoratedMeasurementInOther(view);
          n = k;
          k -= i2;
        } else {
          n = getPaddingLeft() + this.mCachedBorders[layoutParams.mSpanIndex];
          i2 = this.mOrientationHelper.getDecoratedMeasurementInOther(view);
          k = n;
          n = i2 + n;
        } 
        i2 = m;
        m = j;
        j = n;
      } else {
        i2 = getPaddingTop() + this.mCachedBorders[layoutParams.mSpanIndex];
        i = this.mOrientationHelper.getDecoratedMeasurementInOther(view);
        m = i2;
        j = k;
        i2 = i + i2;
        k = n;
      } 
      layoutDecoratedWithMargins(view, k, m, j, i2);
      if (layoutParams.isItemRemoved() || layoutParams.isItemChanged())
        paramLayoutChunkResult.mIgnoreConsumed = true; 
      paramLayoutChunkResult.mFocusable |= view.hasFocusable();
      i = i1 + 1;
      n = k;
      i1 = m;
      m = i2;
      k = j;
      j = i1;
      i1 = i;
    } 
    Arrays.fill((Object[])this.mSet, (Object)null);
  }
  
  void onAnchorReady(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, LinearLayoutManager.AnchorInfo paramAnchorInfo, int paramInt) {
    super.onAnchorReady(paramRecycler, paramState, paramAnchorInfo, paramInt);
    updateMeasurements();
    if (paramState.getItemCount() > 0 && !paramState.isPreLayout())
      ensureAnchorIsInCorrectSpan(paramRecycler, paramState, paramAnchorInfo, paramInt); 
    ensureViewSet();
  }
  
  public View onFocusSearchFailed(View paramView, int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual findContainingItemView : (Landroid/view/View;)Landroid/view/View;
    //   5: astore #5
    //   7: aconst_null
    //   8: astore #6
    //   10: aload #5
    //   12: ifnonnull -> 17
    //   15: aconst_null
    //   16: areturn
    //   17: aload #5
    //   19: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   22: checkcast android/support/v7/widget/GridLayoutManager$LayoutParams
    //   25: astore #7
    //   27: aload #7
    //   29: getfield mSpanIndex : I
    //   32: istore #8
    //   34: aload #7
    //   36: getfield mSpanIndex : I
    //   39: aload #7
    //   41: getfield mSpanSize : I
    //   44: iadd
    //   45: istore #9
    //   47: aload_0
    //   48: aload_1
    //   49: iload_2
    //   50: aload_3
    //   51: aload #4
    //   53: invokespecial onFocusSearchFailed : (Landroid/view/View;ILandroid/support/v7/widget/RecyclerView$Recycler;Landroid/support/v7/widget/RecyclerView$State;)Landroid/view/View;
    //   56: ifnonnull -> 61
    //   59: aconst_null
    //   60: areturn
    //   61: aload_0
    //   62: iload_2
    //   63: invokevirtual convertFocusDirectionToLayoutDirection : (I)I
    //   66: iconst_1
    //   67: if_icmpne -> 76
    //   70: iconst_1
    //   71: istore #10
    //   73: goto -> 79
    //   76: iconst_0
    //   77: istore #10
    //   79: iload #10
    //   81: aload_0
    //   82: getfield mShouldReverseLayout : Z
    //   85: if_icmpeq -> 93
    //   88: iconst_1
    //   89: istore_2
    //   90: goto -> 95
    //   93: iconst_0
    //   94: istore_2
    //   95: iload_2
    //   96: ifeq -> 115
    //   99: aload_0
    //   100: invokevirtual getChildCount : ()I
    //   103: iconst_1
    //   104: isub
    //   105: istore_2
    //   106: iconst_m1
    //   107: istore #11
    //   109: iconst_m1
    //   110: istore #12
    //   112: goto -> 126
    //   115: aload_0
    //   116: invokevirtual getChildCount : ()I
    //   119: istore #11
    //   121: iconst_0
    //   122: istore_2
    //   123: iconst_1
    //   124: istore #12
    //   126: aload_0
    //   127: getfield mOrientation : I
    //   130: iconst_1
    //   131: if_icmpne -> 147
    //   134: aload_0
    //   135: invokevirtual isLayoutRTL : ()Z
    //   138: ifeq -> 147
    //   141: iconst_1
    //   142: istore #13
    //   144: goto -> 150
    //   147: iconst_0
    //   148: istore #13
    //   150: aload_0
    //   151: aload_3
    //   152: aload #4
    //   154: iload_2
    //   155: invokespecial getSpanGroupIndex : (Landroid/support/v7/widget/RecyclerView$Recycler;Landroid/support/v7/widget/RecyclerView$State;I)I
    //   158: istore #14
    //   160: iload_2
    //   161: istore #15
    //   163: iconst_0
    //   164: istore #16
    //   166: iconst_m1
    //   167: istore #17
    //   169: iconst_m1
    //   170: istore #18
    //   172: iconst_0
    //   173: istore_2
    //   174: aconst_null
    //   175: astore_1
    //   176: iload #11
    //   178: istore #19
    //   180: iload #16
    //   182: istore #11
    //   184: iload #15
    //   186: iload #19
    //   188: if_icmpeq -> 562
    //   191: aload_0
    //   192: aload_3
    //   193: aload #4
    //   195: iload #15
    //   197: invokespecial getSpanGroupIndex : (Landroid/support/v7/widget/RecyclerView$Recycler;Landroid/support/v7/widget/RecyclerView$State;I)I
    //   200: istore #16
    //   202: aload_0
    //   203: iload #15
    //   205: invokevirtual getChildAt : (I)Landroid/view/View;
    //   208: astore #7
    //   210: aload #7
    //   212: aload #5
    //   214: if_acmpne -> 220
    //   217: goto -> 562
    //   220: aload #7
    //   222: invokevirtual hasFocusable : ()Z
    //   225: ifeq -> 246
    //   228: iload #16
    //   230: iload #14
    //   232: if_icmpeq -> 246
    //   235: aload #6
    //   237: ifnull -> 243
    //   240: goto -> 562
    //   243: goto -> 552
    //   246: aload #7
    //   248: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   251: checkcast android/support/v7/widget/GridLayoutManager$LayoutParams
    //   254: astore #20
    //   256: aload #20
    //   258: getfield mSpanIndex : I
    //   261: istore #21
    //   263: aload #20
    //   265: getfield mSpanIndex : I
    //   268: aload #20
    //   270: getfield mSpanSize : I
    //   273: iadd
    //   274: istore #22
    //   276: aload #7
    //   278: invokevirtual hasFocusable : ()Z
    //   281: ifeq -> 301
    //   284: iload #21
    //   286: iload #8
    //   288: if_icmpne -> 301
    //   291: iload #22
    //   293: iload #9
    //   295: if_icmpne -> 301
    //   298: aload #7
    //   300: areturn
    //   301: aload #7
    //   303: invokevirtual hasFocusable : ()Z
    //   306: ifeq -> 314
    //   309: aload #6
    //   311: ifnull -> 326
    //   314: aload #7
    //   316: invokevirtual hasFocusable : ()Z
    //   319: ifne -> 332
    //   322: aload_1
    //   323: ifnonnull -> 332
    //   326: iconst_1
    //   327: istore #16
    //   329: goto -> 479
    //   332: iload #21
    //   334: iload #8
    //   336: invokestatic max : (II)I
    //   339: istore #16
    //   341: iload #22
    //   343: iload #9
    //   345: invokestatic min : (II)I
    //   348: iload #16
    //   350: isub
    //   351: istore #23
    //   353: aload #7
    //   355: invokevirtual hasFocusable : ()Z
    //   358: ifeq -> 404
    //   361: iload #23
    //   363: iload #11
    //   365: if_icmple -> 371
    //   368: goto -> 326
    //   371: iload #23
    //   373: iload #11
    //   375: if_icmpne -> 476
    //   378: iload #21
    //   380: iload #17
    //   382: if_icmple -> 391
    //   385: iconst_1
    //   386: istore #16
    //   388: goto -> 394
    //   391: iconst_0
    //   392: istore #16
    //   394: iload #13
    //   396: iload #16
    //   398: if_icmpne -> 476
    //   401: goto -> 326
    //   404: aload #6
    //   406: ifnonnull -> 476
    //   409: iconst_1
    //   410: istore #24
    //   412: iconst_1
    //   413: istore #16
    //   415: aload_0
    //   416: aload #7
    //   418: iconst_0
    //   419: iconst_1
    //   420: invokevirtual isViewPartiallyVisible : (Landroid/view/View;ZZ)Z
    //   423: ifeq -> 476
    //   426: iload_2
    //   427: istore #25
    //   429: iload #23
    //   431: iload #25
    //   433: if_icmple -> 443
    //   436: iload #24
    //   438: istore #16
    //   440: goto -> 479
    //   443: iload #23
    //   445: iload #25
    //   447: if_icmpne -> 473
    //   450: iload #21
    //   452: iload #18
    //   454: if_icmple -> 460
    //   457: goto -> 463
    //   460: iconst_0
    //   461: istore #16
    //   463: iload #13
    //   465: iload #16
    //   467: if_icmpne -> 476
    //   470: goto -> 326
    //   473: goto -> 476
    //   476: iconst_0
    //   477: istore #16
    //   479: iload #16
    //   481: ifeq -> 552
    //   484: aload #7
    //   486: invokevirtual hasFocusable : ()Z
    //   489: ifeq -> 523
    //   492: aload #20
    //   494: getfield mSpanIndex : I
    //   497: istore #17
    //   499: iload #22
    //   501: iload #9
    //   503: invokestatic min : (II)I
    //   506: iload #21
    //   508: iload #8
    //   510: invokestatic max : (II)I
    //   513: isub
    //   514: istore #11
    //   516: aload #7
    //   518: astore #6
    //   520: goto -> 552
    //   523: aload #20
    //   525: getfield mSpanIndex : I
    //   528: istore #18
    //   530: iload #22
    //   532: iload #9
    //   534: invokestatic min : (II)I
    //   537: iload #21
    //   539: iload #8
    //   541: invokestatic max : (II)I
    //   544: isub
    //   545: istore_2
    //   546: aload #7
    //   548: astore_1
    //   549: goto -> 552
    //   552: iload #15
    //   554: iload #12
    //   556: iadd
    //   557: istore #15
    //   559: goto -> 184
    //   562: aload #6
    //   564: ifnull -> 570
    //   567: goto -> 573
    //   570: aload_1
    //   571: astore #6
    //   573: aload #6
    //   575: areturn
  }
  
  public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat) {
    ViewGroup.LayoutParams layoutParams1 = paramView.getLayoutParams();
    if (!(layoutParams1 instanceof LayoutParams)) {
      onInitializeAccessibilityNodeInfoForItem(paramView, paramAccessibilityNodeInfoCompat);
      return;
    } 
    LayoutParams layoutParams = (LayoutParams)layoutParams1;
    int i = getSpanGroupIndex(paramRecycler, paramState, layoutParams.getViewLayoutPosition());
    if (this.mOrientation == 0) {
      boolean bool;
      int j = layoutParams.getSpanIndex();
      int k = layoutParams.getSpanSize();
      if (this.mSpanCount > 1 && layoutParams.getSpanSize() == this.mSpanCount) {
        bool = true;
      } else {
        bool = false;
      } 
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(j, k, i, 1, bool, false));
    } else {
      boolean bool;
      int k = layoutParams.getSpanIndex();
      int j = layoutParams.getSpanSize();
      if (this.mSpanCount > 1 && layoutParams.getSpanSize() == this.mSpanCount) {
        bool = true;
      } else {
        bool = false;
      } 
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(i, 1, k, j, bool, false));
    } 
  }
  
  public void onItemsAdded(RecyclerView paramRecyclerView, int paramInt1, int paramInt2) {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }
  
  public void onItemsChanged(RecyclerView paramRecyclerView) {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }
  
  public void onItemsMoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, int paramInt3) {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }
  
  public void onItemsRemoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2) {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }
  
  public void onItemsUpdated(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, Object paramObject) {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
  }
  
  public void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState) {
    if (paramState.isPreLayout())
      cachePreLayoutSpanMapping(); 
    super.onLayoutChildren(paramRecycler, paramState);
    clearPreLayoutSpanMappingCache();
  }
  
  public void onLayoutCompleted(RecyclerView.State paramState) {
    super.onLayoutCompleted(paramState);
    this.mPendingSpanCountChange = false;
  }
  
  public int scrollHorizontallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState) {
    updateMeasurements();
    ensureViewSet();
    return super.scrollHorizontallyBy(paramInt, paramRecycler, paramState);
  }
  
  public int scrollVerticallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState) {
    updateMeasurements();
    ensureViewSet();
    return super.scrollVerticallyBy(paramInt, paramRecycler, paramState);
  }
  
  public void setMeasuredDimension(Rect paramRect, int paramInt1, int paramInt2) {
    int[] arrayOfInt;
    if (this.mCachedBorders == null)
      super.setMeasuredDimension(paramRect, paramInt1, paramInt2); 
    int i = getPaddingLeft() + getPaddingRight();
    int j = getPaddingTop() + getPaddingBottom();
    if (this.mOrientation == 1) {
      paramInt2 = chooseSize(paramInt2, paramRect.height() + j, getMinimumHeight());
      arrayOfInt = this.mCachedBorders;
      j = chooseSize(paramInt1, arrayOfInt[arrayOfInt.length - 1] + i, getMinimumWidth());
      paramInt1 = paramInt2;
      paramInt2 = j;
    } else {
      paramInt1 = chooseSize(paramInt1, arrayOfInt.width() + i, getMinimumWidth());
      arrayOfInt = this.mCachedBorders;
      j = chooseSize(paramInt2, arrayOfInt[arrayOfInt.length - 1] + j, getMinimumHeight());
      paramInt2 = paramInt1;
      paramInt1 = j;
    } 
    setMeasuredDimension(paramInt2, paramInt1);
  }
  
  public void setSpanCount(int paramInt) {
    if (paramInt == this.mSpanCount)
      return; 
    this.mPendingSpanCountChange = true;
    if (paramInt >= 1) {
      this.mSpanCount = paramInt;
      this.mSpanSizeLookup.invalidateSpanIndexCache();
      requestLayout();
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Span count should be at least 1. Provided ");
    stringBuilder.append(paramInt);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void setSpanSizeLookup(SpanSizeLookup paramSpanSizeLookup) {
    this.mSpanSizeLookup = paramSpanSizeLookup;
  }
  
  public void setStackFromEnd(boolean paramBoolean) {
    if (!paramBoolean) {
      super.setStackFromEnd(false);
      return;
    } 
    throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
  }
  
  public boolean supportsPredictiveItemAnimations() {
    boolean bool;
    if (this.mPendingSavedState == null && !this.mPendingSpanCountChange) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static final class DefaultSpanSizeLookup extends SpanSizeLookup {
    public int getSpanIndex(int param1Int1, int param1Int2) {
      return param1Int1 % param1Int2;
    }
    
    public int getSpanSize(int param1Int) {
      return 1;
    }
  }
  
  public static class LayoutParams extends RecyclerView.LayoutParams {
    public static final int INVALID_SPAN_ID = -1;
    
    int mSpanIndex = -1;
    
    int mSpanSize = 0;
    
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
    
    public int getSpanIndex() {
      return this.mSpanIndex;
    }
    
    public int getSpanSize() {
      return this.mSpanSize;
    }
  }
  
  public static abstract class SpanSizeLookup {
    private boolean mCacheSpanIndices = false;
    
    final SparseIntArray mSpanIndexCache = new SparseIntArray();
    
    int findReferenceIndexFromCache(int param1Int) {
      int i = this.mSpanIndexCache.size() - 1;
      int j = 0;
      while (j <= i) {
        int k = j + i >>> 1;
        if (this.mSpanIndexCache.keyAt(k) < param1Int) {
          j = k + 1;
          continue;
        } 
        i = k - 1;
      } 
      param1Int = j - 1;
      return (param1Int >= 0 && param1Int < this.mSpanIndexCache.size()) ? this.mSpanIndexCache.keyAt(param1Int) : -1;
    }
    
    int getCachedSpanIndex(int param1Int1, int param1Int2) {
      if (!this.mCacheSpanIndices)
        return getSpanIndex(param1Int1, param1Int2); 
      int i = this.mSpanIndexCache.get(param1Int1, -1);
      if (i != -1)
        return i; 
      param1Int2 = getSpanIndex(param1Int1, param1Int2);
      this.mSpanIndexCache.put(param1Int1, param1Int2);
      return param1Int2;
    }
    
    public int getSpanGroupIndex(int param1Int1, int param1Int2) {
      int i = getSpanSize(param1Int1);
      byte b = 0;
      int j = 0;
      int k;
      for (k = 0; b < param1Int1; k = i1) {
        int i1;
        int m = getSpanSize(b);
        int n = j + m;
        if (n == param1Int2) {
          i1 = k + 1;
          j = 0;
        } else {
          j = n;
          i1 = k;
          if (n > param1Int2) {
            i1 = k + 1;
            j = m;
          } 
        } 
        b++;
      } 
      param1Int1 = k;
      if (j + i > param1Int2)
        param1Int1 = k + 1; 
      return param1Int1;
    }
    
    public int getSpanIndex(int param1Int1, int param1Int2) {
      // Byte code:
      //   0: aload_0
      //   1: iload_1
      //   2: invokevirtual getSpanSize : (I)I
      //   5: istore_3
      //   6: iload_3
      //   7: iload_2
      //   8: if_icmpne -> 13
      //   11: iconst_0
      //   12: ireturn
      //   13: aload_0
      //   14: getfield mCacheSpanIndices : Z
      //   17: ifeq -> 63
      //   20: aload_0
      //   21: getfield mSpanIndexCache : Landroid/util/SparseIntArray;
      //   24: invokevirtual size : ()I
      //   27: ifle -> 63
      //   30: aload_0
      //   31: iload_1
      //   32: invokevirtual findReferenceIndexFromCache : (I)I
      //   35: istore #4
      //   37: iload #4
      //   39: iflt -> 63
      //   42: aload_0
      //   43: getfield mSpanIndexCache : Landroid/util/SparseIntArray;
      //   46: iload #4
      //   48: invokevirtual get : (I)I
      //   51: aload_0
      //   52: iload #4
      //   54: invokevirtual getSpanSize : (I)I
      //   57: iadd
      //   58: istore #5
      //   60: goto -> 128
      //   63: iconst_0
      //   64: istore #6
      //   66: iconst_0
      //   67: istore #5
      //   69: iload #6
      //   71: iload_1
      //   72: if_icmpge -> 137
      //   75: aload_0
      //   76: iload #6
      //   78: invokevirtual getSpanSize : (I)I
      //   81: istore #7
      //   83: iload #5
      //   85: iload #7
      //   87: iadd
      //   88: istore #8
      //   90: iload #8
      //   92: iload_2
      //   93: if_icmpne -> 106
      //   96: iconst_0
      //   97: istore #5
      //   99: iload #6
      //   101: istore #4
      //   103: goto -> 128
      //   106: iload #6
      //   108: istore #4
      //   110: iload #8
      //   112: istore #5
      //   114: iload #8
      //   116: iload_2
      //   117: if_icmple -> 128
      //   120: iload #7
      //   122: istore #5
      //   124: iload #6
      //   126: istore #4
      //   128: iload #4
      //   130: iconst_1
      //   131: iadd
      //   132: istore #6
      //   134: goto -> 69
      //   137: iload_3
      //   138: iload #5
      //   140: iadd
      //   141: iload_2
      //   142: if_icmpgt -> 148
      //   145: iload #5
      //   147: ireturn
      //   148: iconst_0
      //   149: ireturn
    }
    
    public abstract int getSpanSize(int param1Int);
    
    public void invalidateSpanIndexCache() {
      this.mSpanIndexCache.clear();
    }
    
    public boolean isSpanIndexCacheEnabled() {
      return this.mCacheSpanIndices;
    }
    
    public void setSpanIndexCacheEnabled(boolean param1Boolean) {
      this.mCacheSpanIndices = param1Boolean;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\GridLayoutManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */