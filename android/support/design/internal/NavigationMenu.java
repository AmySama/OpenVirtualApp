package android.support.design.internal;

import android.content.Context;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.view.SubMenu;

public class NavigationMenu extends MenuBuilder {
  public NavigationMenu(Context paramContext) {
    super(paramContext);
  }
  
  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence) {
    MenuItemImpl menuItemImpl = (MenuItemImpl)addInternal(paramInt1, paramInt2, paramInt3, paramCharSequence);
    NavigationSubMenu navigationSubMenu = new NavigationSubMenu(getContext(), this, menuItemImpl);
    menuItemImpl.setSubMenu(navigationSubMenu);
    return (SubMenu)navigationSubMenu;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\internal\NavigationMenu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */