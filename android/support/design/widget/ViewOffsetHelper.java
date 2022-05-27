package android.support.design.widget;

import android.support.v4.view.ViewCompat;
import android.view.View;

class ViewOffsetHelper {
  private int mLayoutLeft;
  
  private int mLayoutTop;
  
  private int mOffsetLeft;
  
  private int mOffsetTop;
  
  private final View mView;
  
  public ViewOffsetHelper(View paramView) {
    this.mView = paramView;
  }
  
  private void updateOffsets() {
    View view = this.mView;
    ViewCompat.offsetTopAndBottom(view, this.mOffsetTop - view.getTop() - this.mLayoutTop);
    view = this.mView;
    ViewCompat.offsetLeftAndRight(view, this.mOffsetLeft - view.getLeft() - this.mLayoutLeft);
  }
  
  public int getLayoutLeft() {
    return this.mLayoutLeft;
  }
  
  public int getLayoutTop() {
    return this.mLayoutTop;
  }
  
  public int getLeftAndRightOffset() {
    return this.mOffsetLeft;
  }
  
  public int getTopAndBottomOffset() {
    return this.mOffsetTop;
  }
  
  public void onViewLayout() {
    this.mLayoutTop = this.mView.getTop();
    this.mLayoutLeft = this.mView.getLeft();
    updateOffsets();
  }
  
  public boolean setLeftAndRightOffset(int paramInt) {
    if (this.mOffsetLeft != paramInt) {
      this.mOffsetLeft = paramInt;
      updateOffsets();
      return true;
    } 
    return false;
  }
  
  public boolean setTopAndBottomOffset(int paramInt) {
    if (this.mOffsetTop != paramInt) {
      this.mOffsetTop = paramInt;
      updateOffsets();
      return true;
    } 
    return false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\ViewOffsetHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */