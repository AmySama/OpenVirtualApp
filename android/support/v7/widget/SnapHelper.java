package android.support.v7.widget;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public abstract class SnapHelper extends RecyclerView.OnFlingListener {
  static final float MILLISECONDS_PER_INCH = 100.0F;
  
  private Scroller mGravityScroller;
  
  RecyclerView mRecyclerView;
  
  private final RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
      boolean mScrolled = false;
      
      public void onScrollStateChanged(RecyclerView param1RecyclerView, int param1Int) {
        super.onScrollStateChanged(param1RecyclerView, param1Int);
        if (param1Int == 0 && this.mScrolled) {
          this.mScrolled = false;
          SnapHelper.this.snapToTargetExistingView();
        } 
      }
      
      public void onScrolled(RecyclerView param1RecyclerView, int param1Int1, int param1Int2) {
        if (param1Int1 != 0 || param1Int2 != 0)
          this.mScrolled = true; 
      }
    };
  
  private void destroyCallbacks() {
    this.mRecyclerView.removeOnScrollListener(this.mScrollListener);
    this.mRecyclerView.setOnFlingListener((RecyclerView.OnFlingListener)null);
  }
  
  private void setupCallbacks() throws IllegalStateException {
    if (this.mRecyclerView.getOnFlingListener() == null) {
      this.mRecyclerView.addOnScrollListener(this.mScrollListener);
      this.mRecyclerView.setOnFlingListener(this);
      return;
    } 
    throw new IllegalStateException("An instance of OnFlingListener already set.");
  }
  
  private boolean snapFromFling(RecyclerView.LayoutManager paramLayoutManager, int paramInt1, int paramInt2) {
    if (!(paramLayoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider))
      return false; 
    RecyclerView.SmoothScroller smoothScroller = createScroller(paramLayoutManager);
    if (smoothScroller == null)
      return false; 
    paramInt1 = findTargetSnapPosition(paramLayoutManager, paramInt1, paramInt2);
    if (paramInt1 == -1)
      return false; 
    smoothScroller.setTargetPosition(paramInt1);
    paramLayoutManager.startSmoothScroll(smoothScroller);
    return true;
  }
  
  public void attachToRecyclerView(RecyclerView paramRecyclerView) throws IllegalStateException {
    RecyclerView recyclerView = this.mRecyclerView;
    if (recyclerView == paramRecyclerView)
      return; 
    if (recyclerView != null)
      destroyCallbacks(); 
    this.mRecyclerView = paramRecyclerView;
    if (paramRecyclerView != null) {
      setupCallbacks();
      this.mGravityScroller = new Scroller(this.mRecyclerView.getContext(), (Interpolator)new DecelerateInterpolator());
      snapToTargetExistingView();
    } 
  }
  
  public abstract int[] calculateDistanceToFinalSnap(RecyclerView.LayoutManager paramLayoutManager, View paramView);
  
  public int[] calculateScrollDistance(int paramInt1, int paramInt2) {
    this.mGravityScroller.fling(0, 0, paramInt1, paramInt2, -2147483648, 2147483647, -2147483648, 2147483647);
    return new int[] { this.mGravityScroller.getFinalX(), this.mGravityScroller.getFinalY() };
  }
  
  protected RecyclerView.SmoothScroller createScroller(RecyclerView.LayoutManager paramLayoutManager) {
    return createSnapScroller(paramLayoutManager);
  }
  
  @Deprecated
  protected LinearSmoothScroller createSnapScroller(RecyclerView.LayoutManager paramLayoutManager) {
    return !(paramLayoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider) ? null : new LinearSmoothScroller(this.mRecyclerView.getContext()) {
        protected float calculateSpeedPerPixel(DisplayMetrics param1DisplayMetrics) {
          return 100.0F / param1DisplayMetrics.densityDpi;
        }
        
        protected void onTargetFound(View param1View, RecyclerView.State param1State, RecyclerView.SmoothScroller.Action param1Action) {
          if (SnapHelper.this.mRecyclerView == null)
            return; 
          SnapHelper snapHelper = SnapHelper.this;
          int[] arrayOfInt = snapHelper.calculateDistanceToFinalSnap(snapHelper.mRecyclerView.getLayoutManager(), param1View);
          int i = arrayOfInt[0];
          int j = arrayOfInt[1];
          int k = calculateTimeForDeceleration(Math.max(Math.abs(i), Math.abs(j)));
          if (k > 0)
            param1Action.update(i, j, k, (Interpolator)this.mDecelerateInterpolator); 
        }
      };
  }
  
  public abstract View findSnapView(RecyclerView.LayoutManager paramLayoutManager);
  
  public abstract int findTargetSnapPosition(RecyclerView.LayoutManager paramLayoutManager, int paramInt1, int paramInt2);
  
  public boolean onFling(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
    //   4: invokevirtual getLayoutManager : ()Landroid/support/v7/widget/RecyclerView$LayoutManager;
    //   7: astore_3
    //   8: iconst_0
    //   9: istore #4
    //   11: aload_3
    //   12: ifnonnull -> 17
    //   15: iconst_0
    //   16: ireturn
    //   17: aload_0
    //   18: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
    //   21: invokevirtual getAdapter : ()Landroid/support/v7/widget/RecyclerView$Adapter;
    //   24: ifnonnull -> 29
    //   27: iconst_0
    //   28: ireturn
    //   29: aload_0
    //   30: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
    //   33: invokevirtual getMinFlingVelocity : ()I
    //   36: istore #5
    //   38: iload_2
    //   39: invokestatic abs : (I)I
    //   42: iload #5
    //   44: if_icmpgt -> 60
    //   47: iload #4
    //   49: istore #6
    //   51: iload_1
    //   52: invokestatic abs : (I)I
    //   55: iload #5
    //   57: if_icmple -> 77
    //   60: iload #4
    //   62: istore #6
    //   64: aload_0
    //   65: aload_3
    //   66: iload_1
    //   67: iload_2
    //   68: invokespecial snapFromFling : (Landroid/support/v7/widget/RecyclerView$LayoutManager;II)Z
    //   71: ifeq -> 77
    //   74: iconst_1
    //   75: istore #6
    //   77: iload #6
    //   79: ireturn
  }
  
  void snapToTargetExistingView() {
    RecyclerView recyclerView = this.mRecyclerView;
    if (recyclerView == null)
      return; 
    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
    if (layoutManager == null)
      return; 
    View view = findSnapView(layoutManager);
    if (view == null)
      return; 
    int[] arrayOfInt = calculateDistanceToFinalSnap(layoutManager, view);
    if (arrayOfInt[0] != 0 || arrayOfInt[1] != 0)
      this.mRecyclerView.smoothScrollBy(arrayOfInt[0], arrayOfInt[1]); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\SnapHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */