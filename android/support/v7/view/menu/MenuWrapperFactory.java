package android.support.v7.view.menu;

import android.content.Context;
import android.os.Build;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.internal.view.SupportSubMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

public final class MenuWrapperFactory {
  public static Menu wrapSupportMenu(Context paramContext, SupportMenu paramSupportMenu) {
    return new MenuWrapperICS(paramContext, paramSupportMenu);
  }
  
  public static MenuItem wrapSupportMenuItem(Context paramContext, SupportMenuItem paramSupportMenuItem) {
    return (Build.VERSION.SDK_INT >= 16) ? new MenuItemWrapperJB(paramContext, paramSupportMenuItem) : new MenuItemWrapperICS(paramContext, paramSupportMenuItem);
  }
  
  public static SubMenu wrapSupportSubMenu(Context paramContext, SupportSubMenu paramSupportSubMenu) {
    return new SubMenuWrapperICS(paramContext, paramSupportSubMenu);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\menu\MenuWrapperFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */