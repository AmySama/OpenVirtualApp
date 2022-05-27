package android.support.v7.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.internal.view.SupportSubMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

class SubMenuWrapperICS extends MenuWrapperICS implements SubMenu {
  SubMenuWrapperICS(Context paramContext, SupportSubMenu paramSupportSubMenu) {
    super(paramContext, (SupportMenu)paramSupportSubMenu);
  }
  
  public void clearHeader() {
    getWrappedObject().clearHeader();
  }
  
  public MenuItem getItem() {
    return getMenuItemWrapper(getWrappedObject().getItem());
  }
  
  public SupportSubMenu getWrappedObject() {
    return (SupportSubMenu)this.mWrappedObject;
  }
  
  public SubMenu setHeaderIcon(int paramInt) {
    getWrappedObject().setHeaderIcon(paramInt);
    return this;
  }
  
  public SubMenu setHeaderIcon(Drawable paramDrawable) {
    getWrappedObject().setHeaderIcon(paramDrawable);
    return this;
  }
  
  public SubMenu setHeaderTitle(int paramInt) {
    getWrappedObject().setHeaderTitle(paramInt);
    return this;
  }
  
  public SubMenu setHeaderTitle(CharSequence paramCharSequence) {
    getWrappedObject().setHeaderTitle(paramCharSequence);
    return this;
  }
  
  public SubMenu setHeaderView(View paramView) {
    getWrappedObject().setHeaderView(paramView);
    return this;
  }
  
  public SubMenu setIcon(int paramInt) {
    getWrappedObject().setIcon(paramInt);
    return this;
  }
  
  public SubMenu setIcon(Drawable paramDrawable) {
    getWrappedObject().setIcon(paramDrawable);
    return this;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\menu\SubMenuWrapperICS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */