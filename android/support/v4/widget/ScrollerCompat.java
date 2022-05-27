package android.support.v4.widget;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

@Deprecated
public final class ScrollerCompat {
  OverScroller mScroller;
  
  ScrollerCompat(Context paramContext, Interpolator paramInterpolator) {
    OverScroller overScroller = new OverScroller();
    if (paramInterpolator != null) {
      this(paramContext, paramInterpolator);
    } else {
      this(paramContext);
    } 
    this.mScroller = overScroller;
  }
  
  @Deprecated
  public static ScrollerCompat create(Context paramContext) {
    return create(paramContext, null);
  }
  
  @Deprecated
  public static ScrollerCompat create(Context paramContext, Interpolator paramInterpolator) {
    return new ScrollerCompat(paramContext, paramInterpolator);
  }
  
  @Deprecated
  public void abortAnimation() {
    this.mScroller.abortAnimation();
  }
  
  @Deprecated
  public boolean computeScrollOffset() {
    return this.mScroller.computeScrollOffset();
  }
  
  @Deprecated
  public void fling(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
    this.mScroller.fling(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8);
  }
  
  @Deprecated
  public void fling(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10) {
    this.mScroller.fling(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10);
  }
  
  @Deprecated
  public float getCurrVelocity() {
    return this.mScroller.getCurrVelocity();
  }
  
  @Deprecated
  public int getCurrX() {
    return this.mScroller.getCurrX();
  }
  
  @Deprecated
  public int getCurrY() {
    return this.mScroller.getCurrY();
  }
  
  @Deprecated
  public int getFinalX() {
    return this.mScroller.getFinalX();
  }
  
  @Deprecated
  public int getFinalY() {
    return this.mScroller.getFinalY();
  }
  
  @Deprecated
  public boolean isFinished() {
    return this.mScroller.isFinished();
  }
  
  @Deprecated
  public boolean isOverScrolled() {
    return this.mScroller.isOverScrolled();
  }
  
  @Deprecated
  public void notifyHorizontalEdgeReached(int paramInt1, int paramInt2, int paramInt3) {
    this.mScroller.notifyHorizontalEdgeReached(paramInt1, paramInt2, paramInt3);
  }
  
  @Deprecated
  public void notifyVerticalEdgeReached(int paramInt1, int paramInt2, int paramInt3) {
    this.mScroller.notifyVerticalEdgeReached(paramInt1, paramInt2, paramInt3);
  }
  
  @Deprecated
  public boolean springBack(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    return this.mScroller.springBack(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  @Deprecated
  public void startScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mScroller.startScroll(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  @Deprecated
  public void startScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    this.mScroller.startScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\ScrollerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */