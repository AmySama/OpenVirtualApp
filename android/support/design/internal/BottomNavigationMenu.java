package android.support.design.internal;

import android.content.Context;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.view.MenuItem;
import android.view.SubMenu;

public final class BottomNavigationMenu extends MenuBuilder {
  public static final int MAX_ITEM_COUNT = 5;
  
  public BottomNavigationMenu(Context paramContext) {
    super(paramContext);
  }
  
  protected MenuItem addInternal(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence) {
    if (size() + 1 <= 5) {
      stopDispatchingItemsChanged();
      MenuItem menuItem = super.addInternal(paramInt1, paramInt2, paramInt3, paramCharSequence);
      if (menuItem instanceof MenuItemImpl)
        ((MenuItemImpl)menuItem).setExclusiveCheckable(true); 
      startDispatchingItemsChanged();
      return menuItem;
    } 
    throw new IllegalArgumentException("Maximum number of items supported by BottomNavigationView is 5. Limit can be checked with BottomNavigationView#getMaxItemCount()");
  }
  
  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence) {
    throw new UnsupportedOperationException("BottomNavigationView does not support submenus");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\internal\BottomNavigationMenu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */