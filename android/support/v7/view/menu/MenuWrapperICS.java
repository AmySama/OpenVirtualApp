package android.support.v7.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.internal.view.SupportMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

class MenuWrapperICS extends BaseMenuWrapper<SupportMenu> implements Menu {
  MenuWrapperICS(Context paramContext, SupportMenu paramSupportMenu) {
    super(paramContext, paramSupportMenu);
  }
  
  public MenuItem add(int paramInt) {
    return getMenuItemWrapper(this.mWrappedObject.add(paramInt));
  }
  
  public MenuItem add(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    return getMenuItemWrapper(this.mWrappedObject.add(paramInt1, paramInt2, paramInt3, paramInt4));
  }
  
  public MenuItem add(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence) {
    return getMenuItemWrapper(this.mWrappedObject.add(paramInt1, paramInt2, paramInt3, paramCharSequence));
  }
  
  public MenuItem add(CharSequence paramCharSequence) {
    return getMenuItemWrapper(this.mWrappedObject.add(paramCharSequence));
  }
  
  public int addIntentOptions(int paramInt1, int paramInt2, int paramInt3, ComponentName paramComponentName, Intent[] paramArrayOfIntent, Intent paramIntent, int paramInt4, MenuItem[] paramArrayOfMenuItem) {
    MenuItem[] arrayOfMenuItem;
    if (paramArrayOfMenuItem != null) {
      arrayOfMenuItem = new MenuItem[paramArrayOfMenuItem.length];
    } else {
      arrayOfMenuItem = null;
    } 
    paramInt2 = this.mWrappedObject.addIntentOptions(paramInt1, paramInt2, paramInt3, paramComponentName, paramArrayOfIntent, paramIntent, paramInt4, arrayOfMenuItem);
    if (arrayOfMenuItem != null) {
      paramInt1 = 0;
      paramInt3 = arrayOfMenuItem.length;
      while (paramInt1 < paramInt3) {
        paramArrayOfMenuItem[paramInt1] = getMenuItemWrapper(arrayOfMenuItem[paramInt1]);
        paramInt1++;
      } 
    } 
    return paramInt2;
  }
  
  public SubMenu addSubMenu(int paramInt) {
    return getSubMenuWrapper(this.mWrappedObject.addSubMenu(paramInt));
  }
  
  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    return getSubMenuWrapper(this.mWrappedObject.addSubMenu(paramInt1, paramInt2, paramInt3, paramInt4));
  }
  
  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence) {
    return getSubMenuWrapper(this.mWrappedObject.addSubMenu(paramInt1, paramInt2, paramInt3, paramCharSequence));
  }
  
  public SubMenu addSubMenu(CharSequence paramCharSequence) {
    return getSubMenuWrapper(this.mWrappedObject.addSubMenu(paramCharSequence));
  }
  
  public void clear() {
    internalClear();
    this.mWrappedObject.clear();
  }
  
  public void close() {
    this.mWrappedObject.close();
  }
  
  public MenuItem findItem(int paramInt) {
    return getMenuItemWrapper(this.mWrappedObject.findItem(paramInt));
  }
  
  public MenuItem getItem(int paramInt) {
    return getMenuItemWrapper(this.mWrappedObject.getItem(paramInt));
  }
  
  public boolean hasVisibleItems() {
    return this.mWrappedObject.hasVisibleItems();
  }
  
  public boolean isShortcutKey(int paramInt, KeyEvent paramKeyEvent) {
    return this.mWrappedObject.isShortcutKey(paramInt, paramKeyEvent);
  }
  
  public boolean performIdentifierAction(int paramInt1, int paramInt2) {
    return this.mWrappedObject.performIdentifierAction(paramInt1, paramInt2);
  }
  
  public boolean performShortcut(int paramInt1, KeyEvent paramKeyEvent, int paramInt2) {
    return this.mWrappedObject.performShortcut(paramInt1, paramKeyEvent, paramInt2);
  }
  
  public void removeGroup(int paramInt) {
    internalRemoveGroup(paramInt);
    this.mWrappedObject.removeGroup(paramInt);
  }
  
  public void removeItem(int paramInt) {
    internalRemoveItem(paramInt);
    this.mWrappedObject.removeItem(paramInt);
  }
  
  public void setGroupCheckable(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
    this.mWrappedObject.setGroupCheckable(paramInt, paramBoolean1, paramBoolean2);
  }
  
  public void setGroupEnabled(int paramInt, boolean paramBoolean) {
    this.mWrappedObject.setGroupEnabled(paramInt, paramBoolean);
  }
  
  public void setGroupVisible(int paramInt, boolean paramBoolean) {
    this.mWrappedObject.setGroupVisible(paramInt, paramBoolean);
  }
  
  public void setQwertyMode(boolean paramBoolean) {
    this.mWrappedObject.setQwertyMode(paramBoolean);
  }
  
  public int size() {
    return this.mWrappedObject.size();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\menu\MenuWrapperICS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */