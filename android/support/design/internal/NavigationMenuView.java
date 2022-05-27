package android.support.design.internal;

import android.content.Context;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class NavigationMenuView extends RecyclerView implements MenuView {
  public NavigationMenuView(Context paramContext) {
    this(paramContext, null);
  }
  
  public NavigationMenuView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public NavigationMenuView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    setLayoutManager((RecyclerView.LayoutManager)new LinearLayoutManager(paramContext, 1, false));
  }
  
  public int getWindowAnimations() {
    return 0;
  }
  
  public void initialize(MenuBuilder paramMenuBuilder) {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\internal\NavigationMenuView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */