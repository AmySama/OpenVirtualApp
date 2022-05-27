package android.support.v7.view.menu;

import android.support.v7.appcompat.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

public class MenuAdapter extends BaseAdapter {
  static final int ITEM_LAYOUT = R.layout.abc_popup_menu_item_layout;
  
  MenuBuilder mAdapterMenu;
  
  private int mExpandedIndex = -1;
  
  private boolean mForceShowIcon;
  
  private final LayoutInflater mInflater;
  
  private final boolean mOverflowOnly;
  
  public MenuAdapter(MenuBuilder paramMenuBuilder, LayoutInflater paramLayoutInflater, boolean paramBoolean) {
    this.mOverflowOnly = paramBoolean;
    this.mInflater = paramLayoutInflater;
    this.mAdapterMenu = paramMenuBuilder;
    findExpandedIndex();
  }
  
  void findExpandedIndex() {
    MenuItemImpl menuItemImpl = this.mAdapterMenu.getExpandedItem();
    if (menuItemImpl != null) {
      ArrayList<MenuItemImpl> arrayList = this.mAdapterMenu.getNonActionItems();
      int i = arrayList.size();
      for (byte b = 0; b < i; b++) {
        if ((MenuItemImpl)arrayList.get(b) == menuItemImpl) {
          this.mExpandedIndex = b;
          return;
        } 
      } 
    } 
    this.mExpandedIndex = -1;
  }
  
  public MenuBuilder getAdapterMenu() {
    return this.mAdapterMenu;
  }
  
  public int getCount() {
    ArrayList<MenuItemImpl> arrayList;
    if (this.mOverflowOnly) {
      arrayList = this.mAdapterMenu.getNonActionItems();
    } else {
      arrayList = this.mAdapterMenu.getVisibleItems();
    } 
    return (this.mExpandedIndex < 0) ? arrayList.size() : (arrayList.size() - 1);
  }
  
  public boolean getForceShowIcon() {
    return this.mForceShowIcon;
  }
  
  public MenuItemImpl getItem(int paramInt) {
    ArrayList<MenuItemImpl> arrayList;
    if (this.mOverflowOnly) {
      arrayList = this.mAdapterMenu.getNonActionItems();
    } else {
      arrayList = this.mAdapterMenu.getVisibleItems();
    } 
    int i = this.mExpandedIndex;
    int j = paramInt;
    if (i >= 0) {
      j = paramInt;
      if (paramInt >= i)
        j = paramInt + 1; 
    } 
    return arrayList.get(j);
  }
  
  public long getItemId(int paramInt) {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    View view = paramView;
    if (paramView == null)
      view = this.mInflater.inflate(ITEM_LAYOUT, paramViewGroup, false); 
    MenuView.ItemView itemView = (MenuView.ItemView)view;
    if (this.mForceShowIcon)
      ((ListMenuItemView)view).setForceShowIcon(true); 
    itemView.initialize(getItem(paramInt), 0);
    return view;
  }
  
  public void notifyDataSetChanged() {
    findExpandedIndex();
    super.notifyDataSetChanged();
  }
  
  public void setForceShowIcon(boolean paramBoolean) {
    this.mForceShowIcon = paramBoolean;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\menu\MenuAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */