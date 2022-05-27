package io.virtualapp.widgets;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class ListViewPlus extends ListView implements NestedScrollingChild {
  private final NestedScrollingChildHelper mScrollingChildHelper = new NestedScrollingChildHelper((View)this);
  
  public ListViewPlus(Context paramContext) {
    this(paramContext, null);
  }
  
  public ListViewPlus(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ListViewPlus(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    if (Build.VERSION.SDK_INT >= 21)
      setNestedScrollingEnabled(true); 
  }
  
  public boolean dispatchNestedFling(float paramFloat1, float paramFloat2, boolean paramBoolean) {
    return this.mScrollingChildHelper.dispatchNestedFling(paramFloat1, paramFloat2, paramBoolean);
  }
  
  public boolean dispatchNestedPreFling(float paramFloat1, float paramFloat2) {
    return this.mScrollingChildHelper.dispatchNestedPreFling(paramFloat1, paramFloat2);
  }
  
  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2) {
    return this.mScrollingChildHelper.dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfint1, paramArrayOfint2);
  }
  
  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
    return this.mScrollingChildHelper.dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint);
  }
  
  public boolean hasNestedScrollingParent() {
    return this.mScrollingChildHelper.hasNestedScrollingParent();
  }
  
  public boolean isNestedScrollingEnabled() {
    return this.mScrollingChildHelper.isNestedScrollingEnabled();
  }
  
  public void setNestedScrollingEnabled(boolean paramBoolean) {
    this.mScrollingChildHelper.setNestedScrollingEnabled(paramBoolean);
  }
  
  public boolean startNestedScroll(int paramInt) {
    return this.mScrollingChildHelper.startNestedScroll(paramInt);
  }
  
  public void stopNestedScroll() {
    this.mScrollingChildHelper.stopNestedScroll();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\ListViewPlus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */