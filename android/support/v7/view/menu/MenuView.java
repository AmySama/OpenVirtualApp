package android.support.v7.view.menu;

import android.graphics.drawable.Drawable;

public interface MenuView {
  int getWindowAnimations();
  
  void initialize(MenuBuilder paramMenuBuilder);
  
  public static interface ItemView {
    MenuItemImpl getItemData();
    
    void initialize(MenuItemImpl param1MenuItemImpl, int param1Int);
    
    boolean prefersCondensedTitle();
    
    void setCheckable(boolean param1Boolean);
    
    void setChecked(boolean param1Boolean);
    
    void setEnabled(boolean param1Boolean);
    
    void setIcon(Drawable param1Drawable);
    
    void setShortcut(boolean param1Boolean, char param1Char);
    
    void setTitle(CharSequence param1CharSequence);
    
    boolean showsIcon();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\menu\MenuView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */