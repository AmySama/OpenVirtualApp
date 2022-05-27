package android.support.v7.widget;

import android.graphics.PointF;
import android.view.View;

public class LinearSnapHelper extends SnapHelper {
  private static final float INVALID_DISTANCE = 1.0F;
  
  private OrientationHelper mHorizontalHelper;
  
  private OrientationHelper mVerticalHelper;
  
  private float computeDistancePerChild(RecyclerView.LayoutManager paramLayoutManager, OrientationHelper paramOrientationHelper) {
    int i = paramLayoutManager.getChildCount();
    if (i == 0)
      return 1.0F; 
    byte b = 0;
    View view1 = null;
    View view2 = null;
    int j = Integer.MAX_VALUE;
    int k;
    for (k = Integer.MIN_VALUE; b < i; k = i1) {
      View view4;
      int i1;
      View view3 = paramLayoutManager.getChildAt(b);
      int n = paramLayoutManager.getPosition(view3);
      if (n == -1) {
        view4 = view1;
        i1 = k;
      } else {
        int i2 = j;
        if (n < j) {
          view1 = view3;
          i2 = n;
        } 
        view4 = view1;
        j = i2;
        i1 = k;
        if (n > k) {
          view2 = view3;
          i1 = n;
          j = i2;
          view4 = view1;
        } 
      } 
      b++;
      view1 = view4;
    } 
    if (view1 == null || view2 == null)
      return 1.0F; 
    int m = Math.min(paramOrientationHelper.getDecoratedStart(view1), paramOrientationHelper.getDecoratedStart(view2));
    m = Math.max(paramOrientationHelper.getDecoratedEnd(view1), paramOrientationHelper.getDecoratedEnd(view2)) - m;
    return (m == 0) ? 1.0F : (m * 1.0F / (k - j + 1));
  }
  
  private int distanceToCenter(RecyclerView.LayoutManager paramLayoutManager, View paramView, OrientationHelper paramOrientationHelper) {
    int k;
    int i = paramOrientationHelper.getDecoratedStart(paramView);
    int j = paramOrientationHelper.getDecoratedMeasurement(paramView) / 2;
    if (paramLayoutManager.getClipToPadding()) {
      k = paramOrientationHelper.getStartAfterPadding() + paramOrientationHelper.getTotalSpace() / 2;
    } else {
      k = paramOrientationHelper.getEnd() / 2;
    } 
    return i + j - k;
  }
  
  private int estimateNextPositionDiffForFling(RecyclerView.LayoutManager paramLayoutManager, OrientationHelper paramOrientationHelper, int paramInt1, int paramInt2) {
    int[] arrayOfInt = calculateScrollDistance(paramInt1, paramInt2);
    float f = computeDistancePerChild(paramLayoutManager, paramOrientationHelper);
    if (f <= 0.0F)
      return 0; 
    if (Math.abs(arrayOfInt[0]) > Math.abs(arrayOfInt[1])) {
      paramInt1 = arrayOfInt[0];
    } else {
      paramInt1 = arrayOfInt[1];
    } 
    return Math.round(paramInt1 / f);
  }
  
  private View findCenterView(RecyclerView.LayoutManager paramLayoutManager, OrientationHelper paramOrientationHelper) {
    int j;
    int i = paramLayoutManager.getChildCount();
    View view = null;
    if (i == 0)
      return null; 
    if (paramLayoutManager.getClipToPadding()) {
      j = paramOrientationHelper.getStartAfterPadding() + paramOrientationHelper.getTotalSpace() / 2;
    } else {
      j = paramOrientationHelper.getEnd() / 2;
    } 
    int k = Integer.MAX_VALUE;
    byte b = 0;
    while (b < i) {
      View view1 = paramLayoutManager.getChildAt(b);
      int m = Math.abs(paramOrientationHelper.getDecoratedStart(view1) + paramOrientationHelper.getDecoratedMeasurement(view1) / 2 - j);
      int n = k;
      if (m < k) {
        view = view1;
        n = m;
      } 
      b++;
      k = n;
    } 
    return view;
  }
  
  private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager paramLayoutManager) {
    OrientationHelper orientationHelper = this.mHorizontalHelper;
    if (orientationHelper == null || orientationHelper.mLayoutManager != paramLayoutManager)
      this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(paramLayoutManager); 
    return this.mHorizontalHelper;
  }
  
  private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager paramLayoutManager) {
    OrientationHelper orientationHelper = this.mVerticalHelper;
    if (orientationHelper == null || orientationHelper.mLayoutManager != paramLayoutManager)
      this.mVerticalHelper = OrientationHelper.createVerticalHelper(paramLayoutManager); 
    return this.mVerticalHelper;
  }
  
  public int[] calculateDistanceToFinalSnap(RecyclerView.LayoutManager paramLayoutManager, View paramView) {
    int[] arrayOfInt = new int[2];
    if (paramLayoutManager.canScrollHorizontally()) {
      arrayOfInt[0] = distanceToCenter(paramLayoutManager, paramView, getHorizontalHelper(paramLayoutManager));
    } else {
      arrayOfInt[0] = 0;
    } 
    if (paramLayoutManager.canScrollVertically()) {
      arrayOfInt[1] = distanceToCenter(paramLayoutManager, paramView, getVerticalHelper(paramLayoutManager));
    } else {
      arrayOfInt[1] = 0;
    } 
    return arrayOfInt;
  }
  
  public View findSnapView(RecyclerView.LayoutManager paramLayoutManager) {
    return paramLayoutManager.canScrollVertically() ? findCenterView(paramLayoutManager, getVerticalHelper(paramLayoutManager)) : (paramLayoutManager.canScrollHorizontally() ? findCenterView(paramLayoutManager, getHorizontalHelper(paramLayoutManager)) : null);
  }
  
  public int findTargetSnapPosition(RecyclerView.LayoutManager paramLayoutManager, int paramInt1, int paramInt2) {
    if (!(paramLayoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider))
      return -1; 
    int i = paramLayoutManager.getItemCount();
    if (i == 0)
      return -1; 
    View view = findSnapView(paramLayoutManager);
    if (view == null)
      return -1; 
    int j = paramLayoutManager.getPosition(view);
    if (j == -1)
      return -1; 
    RecyclerView.SmoothScroller.ScrollVectorProvider scrollVectorProvider = (RecyclerView.SmoothScroller.ScrollVectorProvider)paramLayoutManager;
    int k = i - 1;
    PointF pointF = scrollVectorProvider.computeScrollVectorForPosition(k);
    if (pointF == null)
      return -1; 
    boolean bool = paramLayoutManager.canScrollHorizontally();
    boolean bool1 = false;
    if (bool) {
      int m = estimateNextPositionDiffForFling(paramLayoutManager, getHorizontalHelper(paramLayoutManager), paramInt1, 0);
      paramInt1 = m;
      if (pointF.x < 0.0F)
        paramInt1 = -m; 
    } else {
      paramInt1 = 0;
    } 
    if (paramLayoutManager.canScrollVertically()) {
      int m = estimateNextPositionDiffForFling(paramLayoutManager, getVerticalHelper(paramLayoutManager), 0, paramInt2);
      paramInt2 = m;
      if (pointF.y < 0.0F)
        paramInt2 = -m; 
    } else {
      paramInt2 = 0;
    } 
    if (paramLayoutManager.canScrollVertically())
      paramInt1 = paramInt2; 
    if (paramInt1 == 0)
      return -1; 
    paramInt1 = j + paramInt1;
    if (paramInt1 < 0)
      paramInt1 = bool1; 
    if (paramInt1 >= i)
      paramInt1 = k; 
    return paramInt1;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\LinearSnapHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */