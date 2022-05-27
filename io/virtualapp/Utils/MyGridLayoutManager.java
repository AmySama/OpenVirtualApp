package io.virtualapp.Utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

public class MyGridLayoutManager extends GridLayoutManager {
  private boolean isScrollEnabled = true;
  
  public MyGridLayoutManager(Context paramContext, int paramInt) {
    super(paramContext, paramInt);
  }
  
  public MyGridLayoutManager(Context paramContext, int paramInt1, int paramInt2, boolean paramBoolean) {
    super(paramContext, paramInt1, paramInt2, paramBoolean);
  }
  
  public MyGridLayoutManager(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  public boolean canScrollVertically() {
    boolean bool;
    if (this.isScrollEnabled && super.canScrollVertically()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void setScrollEnabled(boolean paramBoolean) {
    this.isScrollEnabled = paramBoolean;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\MyGridLayoutManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */