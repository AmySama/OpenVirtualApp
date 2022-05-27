package android.support.design.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

class ViewOffsetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
  private int mTempLeftRightOffset = 0;
  
  private int mTempTopBottomOffset = 0;
  
  private ViewOffsetHelper mViewOffsetHelper;
  
  public ViewOffsetBehavior() {}
  
  public ViewOffsetBehavior(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public int getLeftAndRightOffset() {
    boolean bool;
    ViewOffsetHelper viewOffsetHelper = this.mViewOffsetHelper;
    if (viewOffsetHelper != null) {
      bool = viewOffsetHelper.getLeftAndRightOffset();
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int getTopAndBottomOffset() {
    boolean bool;
    ViewOffsetHelper viewOffsetHelper = this.mViewOffsetHelper;
    if (viewOffsetHelper != null) {
      bool = viewOffsetHelper.getTopAndBottomOffset();
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected void layoutChild(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt) {
    paramCoordinatorLayout.onLayoutChild((View)paramV, paramInt);
  }
  
  public boolean onLayoutChild(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt) {
    layoutChild(paramCoordinatorLayout, paramV, paramInt);
    if (this.mViewOffsetHelper == null)
      this.mViewOffsetHelper = new ViewOffsetHelper((View)paramV); 
    this.mViewOffsetHelper.onViewLayout();
    paramInt = this.mTempTopBottomOffset;
    if (paramInt != 0) {
      this.mViewOffsetHelper.setTopAndBottomOffset(paramInt);
      this.mTempTopBottomOffset = 0;
    } 
    paramInt = this.mTempLeftRightOffset;
    if (paramInt != 0) {
      this.mViewOffsetHelper.setLeftAndRightOffset(paramInt);
      this.mTempLeftRightOffset = 0;
    } 
    return true;
  }
  
  public boolean setLeftAndRightOffset(int paramInt) {
    ViewOffsetHelper viewOffsetHelper = this.mViewOffsetHelper;
    if (viewOffsetHelper != null)
      return viewOffsetHelper.setLeftAndRightOffset(paramInt); 
    this.mTempLeftRightOffset = paramInt;
    return false;
  }
  
  public boolean setTopAndBottomOffset(int paramInt) {
    ViewOffsetHelper viewOffsetHelper = this.mViewOffsetHelper;
    if (viewOffsetHelper != null)
      return viewOffsetHelper.setTopAndBottomOffset(paramInt); 
    this.mTempTopBottomOffset = paramInt;
    return false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\ViewOffsetBehavior.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */