package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.R;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class NavigationMenuPresenter implements MenuPresenter {
  private static final String STATE_ADAPTER = "android:menu:adapter";
  
  private static final String STATE_HEADER = "android:menu:header";
  
  private static final String STATE_HIERARCHY = "android:menu:list";
  
  NavigationMenuAdapter mAdapter;
  
  private MenuPresenter.Callback mCallback;
  
  LinearLayout mHeaderLayout;
  
  ColorStateList mIconTintList;
  
  private int mId;
  
  Drawable mItemBackground;
  
  LayoutInflater mLayoutInflater;
  
  MenuBuilder mMenu;
  
  private NavigationMenuView mMenuView;
  
  final View.OnClickListener mOnClickListener = new View.OnClickListener() {
      public void onClick(View param1View) {
        NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView)param1View;
        NavigationMenuPresenter.this.setUpdateSuspended(true);
        MenuItemImpl menuItemImpl = navigationMenuItemView.getItemData();
        boolean bool = NavigationMenuPresenter.this.mMenu.performItemAction((MenuItem)menuItemImpl, NavigationMenuPresenter.this, 0);
        if (menuItemImpl != null && menuItemImpl.isCheckable() && bool)
          NavigationMenuPresenter.this.mAdapter.setCheckedItem(menuItemImpl); 
        NavigationMenuPresenter.this.setUpdateSuspended(false);
        NavigationMenuPresenter.this.updateMenuView(false);
      }
    };
  
  int mPaddingSeparator;
  
  private int mPaddingTopDefault;
  
  int mTextAppearance;
  
  boolean mTextAppearanceSet;
  
  ColorStateList mTextColor;
  
  public void addHeaderView(View paramView) {
    this.mHeaderLayout.addView(paramView);
    NavigationMenuView navigationMenuView = this.mMenuView;
    navigationMenuView.setPadding(0, 0, 0, navigationMenuView.getPaddingBottom());
  }
  
  public boolean collapseItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl) {
    return false;
  }
  
  public void dispatchApplyWindowInsets(WindowInsetsCompat paramWindowInsetsCompat) {
    int i = paramWindowInsetsCompat.getSystemWindowInsetTop();
    if (this.mPaddingTopDefault != i) {
      this.mPaddingTopDefault = i;
      if (this.mHeaderLayout.getChildCount() == 0) {
        NavigationMenuView navigationMenuView = this.mMenuView;
        navigationMenuView.setPadding(0, this.mPaddingTopDefault, 0, navigationMenuView.getPaddingBottom());
      } 
    } 
    ViewCompat.dispatchApplyWindowInsets((View)this.mHeaderLayout, paramWindowInsetsCompat);
  }
  
  public boolean expandItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl) {
    return false;
  }
  
  public boolean flagActionItems() {
    return false;
  }
  
  public int getHeaderCount() {
    return this.mHeaderLayout.getChildCount();
  }
  
  public View getHeaderView(int paramInt) {
    return this.mHeaderLayout.getChildAt(paramInt);
  }
  
  public int getId() {
    return this.mId;
  }
  
  public Drawable getItemBackground() {
    return this.mItemBackground;
  }
  
  public ColorStateList getItemTextColor() {
    return this.mTextColor;
  }
  
  public ColorStateList getItemTintList() {
    return this.mIconTintList;
  }
  
  public MenuView getMenuView(ViewGroup paramViewGroup) {
    if (this.mMenuView == null) {
      this.mMenuView = (NavigationMenuView)this.mLayoutInflater.inflate(R.layout.design_navigation_menu, paramViewGroup, false);
      if (this.mAdapter == null)
        this.mAdapter = new NavigationMenuAdapter(); 
      this.mHeaderLayout = (LinearLayout)this.mLayoutInflater.inflate(R.layout.design_navigation_item_header, (ViewGroup)this.mMenuView, false);
      this.mMenuView.setAdapter(this.mAdapter);
    } 
    return this.mMenuView;
  }
  
  public View inflateHeaderView(int paramInt) {
    View view = this.mLayoutInflater.inflate(paramInt, (ViewGroup)this.mHeaderLayout, false);
    addHeaderView(view);
    return view;
  }
  
  public void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder) {
    this.mLayoutInflater = LayoutInflater.from(paramContext);
    this.mMenu = paramMenuBuilder;
    this.mPaddingSeparator = paramContext.getResources().getDimensionPixelOffset(R.dimen.design_navigation_separator_vertical_padding);
  }
  
  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {
    MenuPresenter.Callback callback = this.mCallback;
    if (callback != null)
      callback.onCloseMenu(paramMenuBuilder, paramBoolean); 
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable) {
    if (paramParcelable instanceof Bundle) {
      Bundle bundle1 = (Bundle)paramParcelable;
      SparseArray sparseArray2 = bundle1.getSparseParcelableArray("android:menu:list");
      if (sparseArray2 != null)
        this.mMenuView.restoreHierarchyState(sparseArray2); 
      Bundle bundle2 = bundle1.getBundle("android:menu:adapter");
      if (bundle2 != null)
        this.mAdapter.restoreInstanceState(bundle2); 
      SparseArray sparseArray1 = bundle1.getSparseParcelableArray("android:menu:header");
      if (sparseArray1 != null)
        this.mHeaderLayout.restoreHierarchyState(sparseArray1); 
    } 
  }
  
  public Parcelable onSaveInstanceState() {
    if (Build.VERSION.SDK_INT >= 11) {
      Bundle bundle = new Bundle();
      if (this.mMenuView != null) {
        SparseArray sparseArray = new SparseArray();
        this.mMenuView.saveHierarchyState(sparseArray);
        bundle.putSparseParcelableArray("android:menu:list", sparseArray);
      } 
      NavigationMenuAdapter navigationMenuAdapter = this.mAdapter;
      if (navigationMenuAdapter != null)
        bundle.putBundle("android:menu:adapter", navigationMenuAdapter.createInstanceState()); 
      if (this.mHeaderLayout != null) {
        SparseArray sparseArray = new SparseArray();
        this.mHeaderLayout.saveHierarchyState(sparseArray);
        bundle.putSparseParcelableArray("android:menu:header", sparseArray);
      } 
      return (Parcelable)bundle;
    } 
    return null;
  }
  
  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder) {
    return false;
  }
  
  public void removeHeaderView(View paramView) {
    this.mHeaderLayout.removeView(paramView);
    if (this.mHeaderLayout.getChildCount() == 0) {
      NavigationMenuView navigationMenuView = this.mMenuView;
      navigationMenuView.setPadding(0, this.mPaddingTopDefault, 0, navigationMenuView.getPaddingBottom());
    } 
  }
  
  public void setCallback(MenuPresenter.Callback paramCallback) {
    this.mCallback = paramCallback;
  }
  
  public void setCheckedItem(MenuItemImpl paramMenuItemImpl) {
    this.mAdapter.setCheckedItem(paramMenuItemImpl);
  }
  
  public void setId(int paramInt) {
    this.mId = paramInt;
  }
  
  public void setItemBackground(Drawable paramDrawable) {
    this.mItemBackground = paramDrawable;
    updateMenuView(false);
  }
  
  public void setItemIconTintList(ColorStateList paramColorStateList) {
    this.mIconTintList = paramColorStateList;
    updateMenuView(false);
  }
  
  public void setItemTextAppearance(int paramInt) {
    this.mTextAppearance = paramInt;
    this.mTextAppearanceSet = true;
    updateMenuView(false);
  }
  
  public void setItemTextColor(ColorStateList paramColorStateList) {
    this.mTextColor = paramColorStateList;
    updateMenuView(false);
  }
  
  public void setUpdateSuspended(boolean paramBoolean) {
    NavigationMenuAdapter navigationMenuAdapter = this.mAdapter;
    if (navigationMenuAdapter != null)
      navigationMenuAdapter.setUpdateSuspended(paramBoolean); 
  }
  
  public void updateMenuView(boolean paramBoolean) {
    NavigationMenuAdapter navigationMenuAdapter = this.mAdapter;
    if (navigationMenuAdapter != null)
      navigationMenuAdapter.update(); 
  }
  
  private static class HeaderViewHolder extends ViewHolder {
    public HeaderViewHolder(View param1View) {
      super(param1View);
    }
  }
  
  private class NavigationMenuAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final String STATE_ACTION_VIEWS = "android:menu:action_views";
    
    private static final String STATE_CHECKED_ITEM = "android:menu:checked";
    
    private static final int VIEW_TYPE_HEADER = 3;
    
    private static final int VIEW_TYPE_NORMAL = 0;
    
    private static final int VIEW_TYPE_SEPARATOR = 2;
    
    private static final int VIEW_TYPE_SUBHEADER = 1;
    
    private MenuItemImpl mCheckedItem;
    
    private final ArrayList<NavigationMenuPresenter.NavigationMenuItem> mItems = new ArrayList<NavigationMenuPresenter.NavigationMenuItem>();
    
    private boolean mUpdateSuspended;
    
    NavigationMenuAdapter() {
      prepareMenuItems();
    }
    
    private void appendTransparentIconIfMissing(int param1Int1, int param1Int2) {
      while (param1Int1 < param1Int2) {
        ((NavigationMenuPresenter.NavigationMenuTextItem)this.mItems.get(param1Int1)).needsEmptyIcon = true;
        param1Int1++;
      } 
    }
    
    private void prepareMenuItems() {
      if (this.mUpdateSuspended)
        return; 
      this.mUpdateSuspended = true;
      this.mItems.clear();
      this.mItems.add(new NavigationMenuPresenter.NavigationMenuHeaderItem());
      int i = -1;
      int j = NavigationMenuPresenter.this.mMenu.getVisibleItems().size();
      byte b = 0;
      boolean bool = false;
      int k;
      for (k = 0; b < j; k = n) {
        int m;
        boolean bool1;
        int n;
        MenuItemImpl menuItemImpl = NavigationMenuPresenter.this.mMenu.getVisibleItems().get(b);
        if (menuItemImpl.isChecked())
          setCheckedItem(menuItemImpl); 
        if (menuItemImpl.isCheckable())
          menuItemImpl.setExclusiveCheckable(false); 
        if (menuItemImpl.hasSubMenu()) {
          SubMenu subMenu = menuItemImpl.getSubMenu();
          m = i;
          bool1 = bool;
          n = k;
          if (subMenu.hasVisibleItems()) {
            if (b != 0)
              this.mItems.add(new NavigationMenuPresenter.NavigationMenuSeparatorItem(NavigationMenuPresenter.this.mPaddingSeparator, 0)); 
            this.mItems.add(new NavigationMenuPresenter.NavigationMenuTextItem(menuItemImpl));
            int i1 = this.mItems.size();
            int i2 = subMenu.size();
            m = 0;
            int i3;
            for (i3 = 0; m < i2; i3 = n) {
              MenuItemImpl menuItemImpl1 = (MenuItemImpl)subMenu.getItem(m);
              n = i3;
              if (menuItemImpl1.isVisible()) {
                n = i3;
                if (!i3) {
                  n = i3;
                  if (menuItemImpl1.getIcon() != null)
                    n = 1; 
                } 
                if (menuItemImpl1.isCheckable())
                  menuItemImpl1.setExclusiveCheckable(false); 
                if (menuItemImpl.isChecked())
                  setCheckedItem(menuItemImpl); 
                this.mItems.add(new NavigationMenuPresenter.NavigationMenuTextItem(menuItemImpl1));
              } 
              m++;
            } 
            m = i;
            bool1 = bool;
            n = k;
            if (i3) {
              appendTransparentIconIfMissing(i1, this.mItems.size());
              m = i;
              bool1 = bool;
              n = k;
            } 
          } 
        } else {
          int i1;
          m = menuItemImpl.getGroupId();
          if (m != i) {
            k = this.mItems.size();
            if (menuItemImpl.getIcon() != null) {
              bool = true;
            } else {
              bool = false;
            } 
            bool1 = bool;
            i1 = k;
            if (b != 0) {
              i1 = k + 1;
              this.mItems.add(new NavigationMenuPresenter.NavigationMenuSeparatorItem(NavigationMenuPresenter.this.mPaddingSeparator, NavigationMenuPresenter.this.mPaddingSeparator));
              bool1 = bool;
            } 
          } else {
            bool1 = bool;
            i1 = k;
            if (!bool) {
              bool1 = bool;
              i1 = k;
              if (menuItemImpl.getIcon() != null) {
                appendTransparentIconIfMissing(k, this.mItems.size());
                bool1 = true;
                i1 = k;
              } 
            } 
          } 
          NavigationMenuPresenter.NavigationMenuTextItem navigationMenuTextItem = new NavigationMenuPresenter.NavigationMenuTextItem(menuItemImpl);
          navigationMenuTextItem.needsEmptyIcon = bool1;
          this.mItems.add(navigationMenuTextItem);
          n = i1;
        } 
        b++;
        i = m;
        bool = bool1;
      } 
      this.mUpdateSuspended = false;
    }
    
    public Bundle createInstanceState() {
      Bundle bundle = new Bundle();
      MenuItemImpl menuItemImpl = this.mCheckedItem;
      if (menuItemImpl != null)
        bundle.putInt("android:menu:checked", menuItemImpl.getItemId()); 
      SparseArray sparseArray = new SparseArray();
      byte b = 0;
      int i = this.mItems.size();
      while (b < i) {
        NavigationMenuPresenter.NavigationMenuItem navigationMenuItem = this.mItems.get(b);
        if (navigationMenuItem instanceof NavigationMenuPresenter.NavigationMenuTextItem) {
          MenuItemImpl menuItemImpl1 = ((NavigationMenuPresenter.NavigationMenuTextItem)navigationMenuItem).getMenuItem();
          if (menuItemImpl1 != null) {
            View view = menuItemImpl1.getActionView();
          } else {
            navigationMenuItem = null;
          } 
          if (navigationMenuItem != null) {
            ParcelableSparseArray parcelableSparseArray = new ParcelableSparseArray();
            navigationMenuItem.saveHierarchyState(parcelableSparseArray);
            sparseArray.put(menuItemImpl1.getItemId(), parcelableSparseArray);
          } 
        } 
        b++;
      } 
      bundle.putSparseParcelableArray("android:menu:action_views", sparseArray);
      return bundle;
    }
    
    public int getItemCount() {
      return this.mItems.size();
    }
    
    public long getItemId(int param1Int) {
      return param1Int;
    }
    
    public int getItemViewType(int param1Int) {
      NavigationMenuPresenter.NavigationMenuItem navigationMenuItem = this.mItems.get(param1Int);
      if (navigationMenuItem instanceof NavigationMenuPresenter.NavigationMenuSeparatorItem)
        return 2; 
      if (navigationMenuItem instanceof NavigationMenuPresenter.NavigationMenuHeaderItem)
        return 3; 
      if (navigationMenuItem instanceof NavigationMenuPresenter.NavigationMenuTextItem)
        return ((NavigationMenuPresenter.NavigationMenuTextItem)navigationMenuItem).getMenuItem().hasSubMenu() ? 1 : 0; 
      throw new RuntimeException("Unknown item type.");
    }
    
    public void onBindViewHolder(NavigationMenuPresenter.ViewHolder param1ViewHolder, int param1Int) {
      int i = getItemViewType(param1Int);
      if (i != 0) {
        if (i != 1) {
          if (i == 2) {
            NavigationMenuPresenter.NavigationMenuSeparatorItem navigationMenuSeparatorItem = (NavigationMenuPresenter.NavigationMenuSeparatorItem)this.mItems.get(param1Int);
            param1ViewHolder.itemView.setPadding(0, navigationMenuSeparatorItem.getPaddingTop(), 0, navigationMenuSeparatorItem.getPaddingBottom());
          } 
        } else {
          ((TextView)param1ViewHolder.itemView).setText(((NavigationMenuPresenter.NavigationMenuTextItem)this.mItems.get(param1Int)).getMenuItem().getTitle());
        } 
      } else {
        NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView)param1ViewHolder.itemView;
        navigationMenuItemView.setIconTintList(NavigationMenuPresenter.this.mIconTintList);
        if (NavigationMenuPresenter.this.mTextAppearanceSet)
          navigationMenuItemView.setTextAppearance(NavigationMenuPresenter.this.mTextAppearance); 
        if (NavigationMenuPresenter.this.mTextColor != null)
          navigationMenuItemView.setTextColor(NavigationMenuPresenter.this.mTextColor); 
        if (NavigationMenuPresenter.this.mItemBackground != null) {
          Drawable drawable = NavigationMenuPresenter.this.mItemBackground.getConstantState().newDrawable();
        } else {
          param1ViewHolder = null;
        } 
        ViewCompat.setBackground((View)navigationMenuItemView, (Drawable)param1ViewHolder);
        NavigationMenuPresenter.NavigationMenuTextItem navigationMenuTextItem = (NavigationMenuPresenter.NavigationMenuTextItem)this.mItems.get(param1Int);
        navigationMenuItemView.setNeedsEmptyIcon(navigationMenuTextItem.needsEmptyIcon);
        navigationMenuItemView.initialize(navigationMenuTextItem.getMenuItem(), 0);
      } 
    }
    
    public NavigationMenuPresenter.ViewHolder onCreateViewHolder(ViewGroup param1ViewGroup, int param1Int) {
      return (NavigationMenuPresenter.ViewHolder)((param1Int != 0) ? ((param1Int != 1) ? ((param1Int != 2) ? ((param1Int != 3) ? null : new NavigationMenuPresenter.HeaderViewHolder((View)NavigationMenuPresenter.this.mHeaderLayout)) : new NavigationMenuPresenter.SeparatorViewHolder(NavigationMenuPresenter.this.mLayoutInflater, param1ViewGroup)) : new NavigationMenuPresenter.SubheaderViewHolder(NavigationMenuPresenter.this.mLayoutInflater, param1ViewGroup)) : new NavigationMenuPresenter.NormalViewHolder(NavigationMenuPresenter.this.mLayoutInflater, param1ViewGroup, NavigationMenuPresenter.this.mOnClickListener));
    }
    
    public void onViewRecycled(NavigationMenuPresenter.ViewHolder param1ViewHolder) {
      if (param1ViewHolder instanceof NavigationMenuPresenter.NormalViewHolder)
        ((NavigationMenuItemView)param1ViewHolder.itemView).recycle(); 
    }
    
    public void restoreInstanceState(Bundle param1Bundle) {
      byte b = 0;
      int i = param1Bundle.getInt("android:menu:checked", 0);
      if (i != 0) {
        this.mUpdateSuspended = true;
        int j = this.mItems.size();
        for (byte b1 = 0; b1 < j; b1++) {
          NavigationMenuPresenter.NavigationMenuItem navigationMenuItem = this.mItems.get(b1);
          if (navigationMenuItem instanceof NavigationMenuPresenter.NavigationMenuTextItem) {
            MenuItemImpl menuItemImpl = ((NavigationMenuPresenter.NavigationMenuTextItem)navigationMenuItem).getMenuItem();
            if (menuItemImpl != null && menuItemImpl.getItemId() == i) {
              setCheckedItem(menuItemImpl);
              break;
            } 
          } 
        } 
        this.mUpdateSuspended = false;
        prepareMenuItems();
      } 
      SparseArray sparseArray = param1Bundle.getSparseParcelableArray("android:menu:action_views");
      if (sparseArray != null) {
        i = this.mItems.size();
        for (byte b1 = b; b1 < i; b1++) {
          NavigationMenuPresenter.NavigationMenuItem navigationMenuItem = this.mItems.get(b1);
          if (navigationMenuItem instanceof NavigationMenuPresenter.NavigationMenuTextItem) {
            MenuItemImpl menuItemImpl = ((NavigationMenuPresenter.NavigationMenuTextItem)navigationMenuItem).getMenuItem();
            if (menuItemImpl != null) {
              View view = menuItemImpl.getActionView();
              if (view != null) {
                ParcelableSparseArray parcelableSparseArray = (ParcelableSparseArray)sparseArray.get(menuItemImpl.getItemId());
                if (parcelableSparseArray != null)
                  view.restoreHierarchyState(parcelableSparseArray); 
              } 
            } 
          } 
        } 
      } 
    }
    
    public void setCheckedItem(MenuItemImpl param1MenuItemImpl) {
      if (this.mCheckedItem != param1MenuItemImpl && param1MenuItemImpl.isCheckable()) {
        MenuItemImpl menuItemImpl = this.mCheckedItem;
        if (menuItemImpl != null)
          menuItemImpl.setChecked(false); 
        this.mCheckedItem = param1MenuItemImpl;
        param1MenuItemImpl.setChecked(true);
      } 
    }
    
    public void setUpdateSuspended(boolean param1Boolean) {
      this.mUpdateSuspended = param1Boolean;
    }
    
    public void update() {
      prepareMenuItems();
      notifyDataSetChanged();
    }
  }
  
  private static class NavigationMenuHeaderItem implements NavigationMenuItem {}
  
  private static interface NavigationMenuItem {}
  
  private static class NavigationMenuSeparatorItem implements NavigationMenuItem {
    private final int mPaddingBottom;
    
    private final int mPaddingTop;
    
    public NavigationMenuSeparatorItem(int param1Int1, int param1Int2) {
      this.mPaddingTop = param1Int1;
      this.mPaddingBottom = param1Int2;
    }
    
    public int getPaddingBottom() {
      return this.mPaddingBottom;
    }
    
    public int getPaddingTop() {
      return this.mPaddingTop;
    }
  }
  
  private static class NavigationMenuTextItem implements NavigationMenuItem {
    private final MenuItemImpl mMenuItem;
    
    boolean needsEmptyIcon;
    
    NavigationMenuTextItem(MenuItemImpl param1MenuItemImpl) {
      this.mMenuItem = param1MenuItemImpl;
    }
    
    public MenuItemImpl getMenuItem() {
      return this.mMenuItem;
    }
  }
  
  private static class NormalViewHolder extends ViewHolder {
    public NormalViewHolder(LayoutInflater param1LayoutInflater, ViewGroup param1ViewGroup, View.OnClickListener param1OnClickListener) {
      super(param1LayoutInflater.inflate(R.layout.design_navigation_item, param1ViewGroup, false));
      this.itemView.setOnClickListener(param1OnClickListener);
    }
  }
  
  private static class SeparatorViewHolder extends ViewHolder {
    public SeparatorViewHolder(LayoutInflater param1LayoutInflater, ViewGroup param1ViewGroup) {
      super(param1LayoutInflater.inflate(R.layout.design_navigation_item_separator, param1ViewGroup, false));
    }
  }
  
  private static class SubheaderViewHolder extends ViewHolder {
    public SubheaderViewHolder(LayoutInflater param1LayoutInflater, ViewGroup param1ViewGroup) {
      super(param1LayoutInflater.inflate(R.layout.design_navigation_item_subheader, param1ViewGroup, false));
    }
  }
  
  private static abstract class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View param1View) {
      super(param1View);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\internal\NavigationMenuPresenter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */