package android.support.v7.view.menu;

import android.content.Context;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.internal.view.SupportSubMenu;
import android.support.v4.util.ArrayMap;
import android.view.MenuItem;
import android.view.SubMenu;
import java.util.Iterator;
import java.util.Map;

abstract class BaseMenuWrapper<T> extends BaseWrapper<T> {
  final Context mContext;
  
  private Map<SupportMenuItem, MenuItem> mMenuItems;
  
  private Map<SupportSubMenu, SubMenu> mSubMenus;
  
  BaseMenuWrapper(Context paramContext, T paramT) {
    super(paramT);
    this.mContext = paramContext;
  }
  
  final MenuItem getMenuItemWrapper(MenuItem paramMenuItem) {
    MenuItem menuItem = paramMenuItem;
    if (paramMenuItem instanceof SupportMenuItem) {
      SupportMenuItem supportMenuItem = (SupportMenuItem)paramMenuItem;
      if (this.mMenuItems == null)
        this.mMenuItems = (Map<SupportMenuItem, MenuItem>)new ArrayMap(); 
      paramMenuItem = this.mMenuItems.get(paramMenuItem);
      menuItem = paramMenuItem;
      if (paramMenuItem == null) {
        menuItem = MenuWrapperFactory.wrapSupportMenuItem(this.mContext, supportMenuItem);
        this.mMenuItems.put(supportMenuItem, menuItem);
      } 
    } 
    return menuItem;
  }
  
  final SubMenu getSubMenuWrapper(SubMenu paramSubMenu) {
    if (paramSubMenu instanceof SupportSubMenu) {
      SupportSubMenu supportSubMenu = (SupportSubMenu)paramSubMenu;
      if (this.mSubMenus == null)
        this.mSubMenus = (Map<SupportSubMenu, SubMenu>)new ArrayMap(); 
      SubMenu subMenu = this.mSubMenus.get(supportSubMenu);
      paramSubMenu = subMenu;
      if (subMenu == null) {
        paramSubMenu = MenuWrapperFactory.wrapSupportSubMenu(this.mContext, supportSubMenu);
        this.mSubMenus.put(supportSubMenu, paramSubMenu);
      } 
      return paramSubMenu;
    } 
    return paramSubMenu;
  }
  
  final void internalClear() {
    Map<SupportMenuItem, MenuItem> map1 = this.mMenuItems;
    if (map1 != null)
      map1.clear(); 
    Map<SupportSubMenu, SubMenu> map = this.mSubMenus;
    if (map != null)
      map.clear(); 
  }
  
  final void internalRemoveGroup(int paramInt) {
    Map<SupportMenuItem, MenuItem> map = this.mMenuItems;
    if (map == null)
      return; 
    Iterator<MenuItem> iterator = map.keySet().iterator();
    while (iterator.hasNext()) {
      if (paramInt == ((MenuItem)iterator.next()).getGroupId())
        iterator.remove(); 
    } 
  }
  
  final void internalRemoveItem(int paramInt) {
    Map<SupportMenuItem, MenuItem> map = this.mMenuItems;
    if (map == null)
      return; 
    Iterator<MenuItem> iterator = map.keySet().iterator();
    while (iterator.hasNext()) {
      if (paramInt == ((MenuItem)iterator.next()).getItemId()) {
        iterator.remove();
        break;
      } 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\menu\BaseMenuWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */