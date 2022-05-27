package android.support.v7.widget;

import android.content.Context;
import android.support.v7.appcompat.R;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.ShowableListMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;

public class PopupMenu {
  private final View mAnchor;
  
  private final Context mContext;
  
  private View.OnTouchListener mDragListener;
  
  private final MenuBuilder mMenu;
  
  OnMenuItemClickListener mMenuItemClickListener;
  
  OnDismissListener mOnDismissListener;
  
  final MenuPopupHelper mPopup;
  
  public PopupMenu(Context paramContext, View paramView) {
    this(paramContext, paramView, 0);
  }
  
  public PopupMenu(Context paramContext, View paramView, int paramInt) {
    this(paramContext, paramView, paramInt, R.attr.popupMenuStyle, 0);
  }
  
  public PopupMenu(Context paramContext, View paramView, int paramInt1, int paramInt2, int paramInt3) {
    this.mContext = paramContext;
    this.mAnchor = paramView;
    MenuBuilder menuBuilder = new MenuBuilder(paramContext);
    this.mMenu = menuBuilder;
    menuBuilder.setCallback(new MenuBuilder.Callback() {
          public boolean onMenuItemSelected(MenuBuilder param1MenuBuilder, MenuItem param1MenuItem) {
            return (PopupMenu.this.mMenuItemClickListener != null) ? PopupMenu.this.mMenuItemClickListener.onMenuItemClick(param1MenuItem) : false;
          }
          
          public void onMenuModeChange(MenuBuilder param1MenuBuilder) {}
        });
    MenuPopupHelper menuPopupHelper = new MenuPopupHelper(paramContext, this.mMenu, paramView, false, paramInt2, paramInt3);
    this.mPopup = menuPopupHelper;
    menuPopupHelper.setGravity(paramInt1);
    this.mPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
          public void onDismiss() {
            if (PopupMenu.this.mOnDismissListener != null)
              PopupMenu.this.mOnDismissListener.onDismiss(PopupMenu.this); 
          }
        });
  }
  
  public void dismiss() {
    this.mPopup.dismiss();
  }
  
  public View.OnTouchListener getDragToOpenListener() {
    if (this.mDragListener == null)
      this.mDragListener = new ForwardingListener(this.mAnchor) {
          public ShowableListMenu getPopup() {
            return (ShowableListMenu)PopupMenu.this.mPopup.getPopup();
          }
          
          protected boolean onForwardingStarted() {
            PopupMenu.this.show();
            return true;
          }
          
          protected boolean onForwardingStopped() {
            PopupMenu.this.dismiss();
            return true;
          }
        }; 
    return this.mDragListener;
  }
  
  public int getGravity() {
    return this.mPopup.getGravity();
  }
  
  public Menu getMenu() {
    return (Menu)this.mMenu;
  }
  
  public MenuInflater getMenuInflater() {
    return (MenuInflater)new SupportMenuInflater(this.mContext);
  }
  
  ListView getMenuListView() {
    return !this.mPopup.isShowing() ? null : this.mPopup.getListView();
  }
  
  public void inflate(int paramInt) {
    getMenuInflater().inflate(paramInt, (Menu)this.mMenu);
  }
  
  public void setGravity(int paramInt) {
    this.mPopup.setGravity(paramInt);
  }
  
  public void setOnDismissListener(OnDismissListener paramOnDismissListener) {
    this.mOnDismissListener = paramOnDismissListener;
  }
  
  public void setOnMenuItemClickListener(OnMenuItemClickListener paramOnMenuItemClickListener) {
    this.mMenuItemClickListener = paramOnMenuItemClickListener;
  }
  
  public void show() {
    this.mPopup.show();
  }
  
  public static interface OnDismissListener {
    void onDismiss(PopupMenu param1PopupMenu);
  }
  
  public static interface OnMenuItemClickListener {
    boolean onMenuItemClick(MenuItem param1MenuItem);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\PopupMenu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */