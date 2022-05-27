package android.support.design.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.math.MathUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;

abstract class HeaderScrollingViewBehavior extends ViewOffsetBehavior<View> {
  private int mOverlayTop;
  
  final Rect mTempRect1 = new Rect();
  
  final Rect mTempRect2 = new Rect();
  
  private int mVerticalLayoutGap = 0;
  
  public HeaderScrollingViewBehavior() {}
  
  public HeaderScrollingViewBehavior(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  private static int resolveGravity(int paramInt) {
    int i = paramInt;
    if (paramInt == 0)
      i = 8388659; 
    return i;
  }
  
  abstract View findFirstDependency(List<View> paramList);
  
  final int getOverlapPixelsForOffset(View paramView) {
    int i = this.mOverlayTop;
    int j = 0;
    if (i != 0) {
      float f = getOverlapRatioForOffset(paramView);
      j = this.mOverlayTop;
      j = MathUtils.clamp((int)(f * j), 0, j);
    } 
    return j;
  }
  
  float getOverlapRatioForOffset(View paramView) {
    return 1.0F;
  }
  
  public final int getOverlayTop() {
    return this.mOverlayTop;
  }
  
  int getScrollRange(View paramView) {
    return paramView.getMeasuredHeight();
  }
  
  final int getVerticalLayoutGap() {
    return this.mVerticalLayoutGap;
  }
  
  protected void layoutChild(CoordinatorLayout paramCoordinatorLayout, View paramView, int paramInt) {
    Rect rect;
    View view = findFirstDependency(paramCoordinatorLayout.getDependencies(paramView));
    if (view != null) {
      CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)paramView.getLayoutParams();
      Rect rect1 = this.mTempRect1;
      rect1.set(paramCoordinatorLayout.getPaddingLeft() + layoutParams.leftMargin, view.getBottom() + layoutParams.topMargin, paramCoordinatorLayout.getWidth() - paramCoordinatorLayout.getPaddingRight() - layoutParams.rightMargin, paramCoordinatorLayout.getHeight() + view.getBottom() - paramCoordinatorLayout.getPaddingBottom() - layoutParams.bottomMargin);
      WindowInsetsCompat windowInsetsCompat = paramCoordinatorLayout.getLastWindowInsets();
      if (windowInsetsCompat != null && ViewCompat.getFitsSystemWindows((View)paramCoordinatorLayout) && !ViewCompat.getFitsSystemWindows(paramView)) {
        rect1.left += windowInsetsCompat.getSystemWindowInsetLeft();
        rect1.right -= windowInsetsCompat.getSystemWindowInsetRight();
      } 
      rect = this.mTempRect2;
      GravityCompat.apply(resolveGravity(layoutParams.gravity), paramView.getMeasuredWidth(), paramView.getMeasuredHeight(), rect1, rect, paramInt);
      paramInt = getOverlapPixelsForOffset(view);
      paramView.layout(rect.left, rect.top - paramInt, rect.right, rect.bottom - paramInt);
      this.mVerticalLayoutGap = rect.top - view.getBottom();
    } else {
      super.layoutChild((CoordinatorLayout)rect, paramView, paramInt);
      this.mVerticalLayoutGap = 0;
    } 
  }
  
  public boolean onMeasureChild(CoordinatorLayout paramCoordinatorLayout, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = (paramView.getLayoutParams()).height;
    if (i == -1 || i == -2) {
      View view = findFirstDependency(paramCoordinatorLayout.getDependencies(paramView));
      if (view != null) {
        if (ViewCompat.getFitsSystemWindows(view) && !ViewCompat.getFitsSystemWindows(paramView)) {
          ViewCompat.setFitsSystemWindows(paramView, true);
          if (ViewCompat.getFitsSystemWindows(paramView)) {
            paramView.requestLayout();
            return true;
          } 
        } 
        int j = View.MeasureSpec.getSize(paramInt3);
        paramInt3 = j;
        if (j == 0)
          paramInt3 = paramCoordinatorLayout.getHeight(); 
        int k = view.getMeasuredHeight();
        int m = getScrollRange(view);
        if (i == -1) {
          j = 1073741824;
        } else {
          j = Integer.MIN_VALUE;
        } 
        paramCoordinatorLayout.onMeasureChild(paramView, paramInt1, paramInt2, View.MeasureSpec.makeMeasureSpec(paramInt3 - k + m, j), paramInt4);
        return true;
      } 
    } 
    return false;
  }
  
  public final void setOverlayTop(int paramInt) {
    this.mOverlayTop = paramInt;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\HeaderScrollingViewBehavior.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */