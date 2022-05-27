package android.support.v4.view;

import android.view.View;

public interface NestedScrollingParent {
  int getNestedScrollAxes();
  
  boolean onNestedFling(View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean);
  
  boolean onNestedPreFling(View paramView, float paramFloat1, float paramFloat2);
  
  void onNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfint);
  
  void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt);
  
  boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt);
  
  void onStopNestedScroll(View paramView);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\NestedScrollingParent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */