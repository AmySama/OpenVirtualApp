package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

public class LinearSmoothScroller extends RecyclerView.SmoothScroller {
  private static final boolean DEBUG = false;
  
  private static final float MILLISECONDS_PER_INCH = 25.0F;
  
  public static final int SNAP_TO_ANY = 0;
  
  public static final int SNAP_TO_END = 1;
  
  public static final int SNAP_TO_START = -1;
  
  private static final String TAG = "LinearSmoothScroller";
  
  private static final float TARGET_SEEK_EXTRA_SCROLL_RATIO = 1.2F;
  
  private static final int TARGET_SEEK_SCROLL_DISTANCE_PX = 10000;
  
  private final float MILLISECONDS_PER_PX;
  
  protected final DecelerateInterpolator mDecelerateInterpolator = new DecelerateInterpolator();
  
  protected int mInterimTargetDx = 0;
  
  protected int mInterimTargetDy = 0;
  
  protected final LinearInterpolator mLinearInterpolator = new LinearInterpolator();
  
  protected PointF mTargetVector;
  
  public LinearSmoothScroller(Context paramContext) {
    this.MILLISECONDS_PER_PX = calculateSpeedPerPixel(paramContext.getResources().getDisplayMetrics());
  }
  
  private int clampApplyScroll(int paramInt1, int paramInt2) {
    paramInt2 = paramInt1 - paramInt2;
    return (paramInt1 * paramInt2 <= 0) ? 0 : paramInt2;
  }
  
  public int calculateDtToFit(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    if (paramInt5 != -1) {
      if (paramInt5 != 0) {
        if (paramInt5 == 1)
          return paramInt4 - paramInt2; 
        throw new IllegalArgumentException("snap preference should be one of the constants defined in SmoothScroller, starting with SNAP_");
      } 
      paramInt1 = paramInt3 - paramInt1;
      if (paramInt1 > 0)
        return paramInt1; 
      paramInt1 = paramInt4 - paramInt2;
      return (paramInt1 < 0) ? paramInt1 : 0;
    } 
    return paramInt3 - paramInt1;
  }
  
  public int calculateDxToMakeVisible(View paramView, int paramInt) {
    RecyclerView.LayoutManager layoutManager = getLayoutManager();
    if (layoutManager == null || !layoutManager.canScrollHorizontally())
      return 0; 
    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
    return calculateDtToFit(layoutManager.getDecoratedLeft(paramView) - layoutParams.leftMargin, layoutManager.getDecoratedRight(paramView) + layoutParams.rightMargin, layoutManager.getPaddingLeft(), layoutManager.getWidth() - layoutManager.getPaddingRight(), paramInt);
  }
  
  public int calculateDyToMakeVisible(View paramView, int paramInt) {
    RecyclerView.LayoutManager layoutManager = getLayoutManager();
    if (layoutManager == null || !layoutManager.canScrollVertically())
      return 0; 
    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
    return calculateDtToFit(layoutManager.getDecoratedTop(paramView) - layoutParams.topMargin, layoutManager.getDecoratedBottom(paramView) + layoutParams.bottomMargin, layoutManager.getPaddingTop(), layoutManager.getHeight() - layoutManager.getPaddingBottom(), paramInt);
  }
  
  protected float calculateSpeedPerPixel(DisplayMetrics paramDisplayMetrics) {
    return 25.0F / paramDisplayMetrics.densityDpi;
  }
  
  protected int calculateTimeForDeceleration(int paramInt) {
    return (int)Math.ceil(calculateTimeForScrolling(paramInt) / 0.3356D);
  }
  
  protected int calculateTimeForScrolling(int paramInt) {
    return (int)Math.ceil((Math.abs(paramInt) * this.MILLISECONDS_PER_PX));
  }
  
  public PointF computeScrollVectorForPosition(int paramInt) {
    RecyclerView.LayoutManager layoutManager = getLayoutManager();
    if (layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)
      return ((RecyclerView.SmoothScroller.ScrollVectorProvider)layoutManager).computeScrollVectorForPosition(paramInt); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("You should override computeScrollVectorForPosition when the LayoutManager does not implement ");
    stringBuilder.append(RecyclerView.SmoothScroller.ScrollVectorProvider.class.getCanonicalName());
    Log.w("LinearSmoothScroller", stringBuilder.toString());
    return null;
  }
  
  protected int getHorizontalSnapPreference() {
    byte b;
    PointF pointF = this.mTargetVector;
    if (pointF == null || pointF.x == 0.0F)
      return 0; 
    if (this.mTargetVector.x > 0.0F) {
      b = 1;
    } else {
      b = -1;
    } 
    return b;
  }
  
  protected int getVerticalSnapPreference() {
    byte b;
    PointF pointF = this.mTargetVector;
    if (pointF == null || pointF.y == 0.0F)
      return 0; 
    if (this.mTargetVector.y > 0.0F) {
      b = 1;
    } else {
      b = -1;
    } 
    return b;
  }
  
  protected void onSeekTargetStep(int paramInt1, int paramInt2, RecyclerView.State paramState, RecyclerView.SmoothScroller.Action paramAction) {
    if (getChildCount() == 0) {
      stop();
      return;
    } 
    this.mInterimTargetDx = clampApplyScroll(this.mInterimTargetDx, paramInt1);
    paramInt1 = clampApplyScroll(this.mInterimTargetDy, paramInt2);
    this.mInterimTargetDy = paramInt1;
    if (this.mInterimTargetDx == 0 && paramInt1 == 0)
      updateActionForInterimTarget(paramAction); 
  }
  
  protected void onStart() {}
  
  protected void onStop() {
    this.mInterimTargetDy = 0;
    this.mInterimTargetDx = 0;
    this.mTargetVector = null;
  }
  
  protected void onTargetFound(View paramView, RecyclerView.State paramState, RecyclerView.SmoothScroller.Action paramAction) {
    int i = calculateDxToMakeVisible(paramView, getHorizontalSnapPreference());
    int j = calculateDyToMakeVisible(paramView, getVerticalSnapPreference());
    int k = calculateTimeForDeceleration((int)Math.sqrt((i * i + j * j)));
    if (k > 0)
      paramAction.update(-i, -j, k, (Interpolator)this.mDecelerateInterpolator); 
  }
  
  protected void updateActionForInterimTarget(RecyclerView.SmoothScroller.Action paramAction) {
    PointF pointF = computeScrollVectorForPosition(getTargetPosition());
    if (pointF == null || (pointF.x == 0.0F && pointF.y == 0.0F)) {
      paramAction.jumpTo(getTargetPosition());
      stop();
      return;
    } 
    normalize(pointF);
    this.mTargetVector = pointF;
    this.mInterimTargetDx = (int)(pointF.x * 10000.0F);
    this.mInterimTargetDy = (int)(pointF.y * 10000.0F);
    int i = calculateTimeForScrolling(10000);
    paramAction.update((int)(this.mInterimTargetDx * 1.2F), (int)(this.mInterimTargetDy * 1.2F), (int)(i * 1.2F), (Interpolator)this.mLinearInterpolator);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\LinearSmoothScroller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */