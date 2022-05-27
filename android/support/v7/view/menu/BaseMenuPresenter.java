package android.support.v7.view.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public abstract class BaseMenuPresenter implements MenuPresenter {
  private MenuPresenter.Callback mCallback;
  
  protected Context mContext;
  
  private int mId;
  
  protected LayoutInflater mInflater;
  
  private int mItemLayoutRes;
  
  protected MenuBuilder mMenu;
  
  private int mMenuLayoutRes;
  
  protected MenuView mMenuView;
  
  protected Context mSystemContext;
  
  protected LayoutInflater mSystemInflater;
  
  public BaseMenuPresenter(Context paramContext, int paramInt1, int paramInt2) {
    this.mSystemContext = paramContext;
    this.mSystemInflater = LayoutInflater.from(paramContext);
    this.mMenuLayoutRes = paramInt1;
    this.mItemLayoutRes = paramInt2;
  }
  
  protected void addItemView(View paramView, int paramInt) {
    ViewGroup viewGroup = (ViewGroup)paramView.getParent();
    if (viewGroup != null)
      viewGroup.removeView(paramView); 
    ((ViewGroup)this.mMenuView).addView(paramView, paramInt);
  }
  
  public abstract void bindItemView(MenuItemImpl paramMenuItemImpl, MenuView.ItemView paramItemView);
  
  public boolean collapseItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl) {
    return false;
  }
  
  public MenuView.ItemView createItemView(ViewGroup paramViewGroup) {
    return (MenuView.ItemView)this.mSystemInflater.inflate(this.mItemLayoutRes, paramViewGroup, false);
  }
  
  public boolean expandItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl) {
    return false;
  }
  
  protected boolean filterLeftoverView(ViewGroup paramViewGroup, int paramInt) {
    paramViewGroup.removeViewAt(paramInt);
    return true;
  }
  
  public boolean flagActionItems() {
    return false;
  }
  
  public MenuPresenter.Callback getCallback() {
    return this.mCallback;
  }
  
  public int getId() {
    return this.mId;
  }
  
  public View getItemView(MenuItemImpl paramMenuItemImpl, View paramView, ViewGroup paramViewGroup) {
    MenuView.ItemView itemView;
    if (paramView instanceof MenuView.ItemView) {
      itemView = (MenuView.ItemView)paramView;
    } else {
      itemView = createItemView(paramViewGroup);
    } 
    bindItemView(paramMenuItemImpl, itemView);
    return (View)itemView;
  }
  
  public MenuView getMenuView(ViewGroup paramViewGroup) {
    if (this.mMenuView == null) {
      MenuView menuView = (MenuView)this.mSystemInflater.inflate(this.mMenuLayoutRes, paramViewGroup, false);
      this.mMenuView = menuView;
      menuView.initialize(this.mMenu);
      updateMenuView(true);
    } 
    return this.mMenuView;
  }
  
  public void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder) {
    this.mContext = paramContext;
    this.mInflater = LayoutInflater.from(paramContext);
    this.mMenu = paramMenuBuilder;
  }
  
  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {
    MenuPresenter.Callback callback = this.mCallback;
    if (callback != null)
      callback.onCloseMenu(paramMenuBuilder, paramBoolean); 
  }
  
  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder) {
    MenuPresenter.Callback callback = this.mCallback;
    return (callback != null) ? callback.onOpenSubMenu(paramSubMenuBuilder) : false;
  }
  
  public void setCallback(MenuPresenter.Callback paramCallback) {
    this.mCallback = paramCallback;
  }
  
  public void setId(int paramInt) {
    this.mId = paramInt;
  }
  
  public boolean shouldIncludeItem(int paramInt, MenuItemImpl paramMenuItemImpl) {
    return true;
  }
  
  public void updateMenuView(boolean paramBoolean) {
    ViewGroup viewGroup = (ViewGroup)this.mMenuView;
    if (viewGroup == null)
      return; 
    MenuBuilder menuBuilder = this.mMenu;
    int i = 0;
    if (menuBuilder != null) {
      menuBuilder.flagActionItems();
      ArrayList<MenuItemImpl> arrayList = this.mMenu.getVisibleItems();
      int j = arrayList.size();
      byte b = 0;
      for (i = 0; b < j; i = k) {
        MenuItemImpl menuItemImpl = arrayList.get(b);
        int k = i;
        if (shouldIncludeItem(i, menuItemImpl)) {
          View view1 = viewGroup.getChildAt(i);
          if (view1 instanceof MenuView.ItemView) {
            MenuItemImpl menuItemImpl1 = ((MenuView.ItemView)view1).getItemData();
          } else {
            menuBuilder = null;
          } 
          View view2 = getItemView(menuItemImpl, view1, viewGroup);
          if (menuItemImpl != menuBuilder) {
            view2.setPressed(false);
            view2.jumpDrawablesToCurrentState();
          } 
          if (view2 != view1)
            addItemView(view2, i); 
          k = i + 1;
        } 
        b++;
      } 
    } 
    while (i < viewGroup.getChildCount()) {
      if (!filterLeftoverView(viewGroup, i))
        i++; 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\menu\BaseMenuPresenter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */