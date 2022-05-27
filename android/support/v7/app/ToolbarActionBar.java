package android.support.v7.app;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.view.WindowCallbackWrapper;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;

class ToolbarActionBar extends ActionBar {
  DecorToolbar mDecorToolbar;
  
  private boolean mLastMenuVisibility;
  
  private boolean mMenuCallbackSet;
  
  private final Toolbar.OnMenuItemClickListener mMenuClicker = new Toolbar.OnMenuItemClickListener() {
      public boolean onMenuItemClick(MenuItem param1MenuItem) {
        return ToolbarActionBar.this.mWindowCallback.onMenuItemSelected(0, param1MenuItem);
      }
    };
  
  private final Runnable mMenuInvalidator = new Runnable() {
      public void run() {
        ToolbarActionBar.this.populateOptionsMenu();
      }
    };
  
  private ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList<ActionBar.OnMenuVisibilityListener>();
  
  boolean mToolbarMenuPrepared;
  
  Window.Callback mWindowCallback;
  
  ToolbarActionBar(Toolbar paramToolbar, CharSequence paramCharSequence, Window.Callback paramCallback) {
    this.mDecorToolbar = (DecorToolbar)new ToolbarWidgetWrapper(paramToolbar, false);
    ToolbarCallbackWrapper toolbarCallbackWrapper = new ToolbarCallbackWrapper(paramCallback);
    this.mWindowCallback = (Window.Callback)toolbarCallbackWrapper;
    this.mDecorToolbar.setWindowCallback((Window.Callback)toolbarCallbackWrapper);
    paramToolbar.setOnMenuItemClickListener(this.mMenuClicker);
    this.mDecorToolbar.setWindowTitle(paramCharSequence);
  }
  
  private Menu getMenu() {
    if (!this.mMenuCallbackSet) {
      this.mDecorToolbar.setMenuCallbacks(new ActionMenuPresenterCallback(), new MenuBuilderCallback());
      this.mMenuCallbackSet = true;
    } 
    return this.mDecorToolbar.getMenu();
  }
  
  public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener paramOnMenuVisibilityListener) {
    this.mMenuVisibilityListeners.add(paramOnMenuVisibilityListener);
  }
  
  public void addTab(ActionBar.Tab paramTab) {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public void addTab(ActionBar.Tab paramTab, int paramInt) {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public void addTab(ActionBar.Tab paramTab, int paramInt, boolean paramBoolean) {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public void addTab(ActionBar.Tab paramTab, boolean paramBoolean) {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public boolean closeOptionsMenu() {
    return this.mDecorToolbar.hideOverflowMenu();
  }
  
  public boolean collapseActionView() {
    if (this.mDecorToolbar.hasExpandedActionView()) {
      this.mDecorToolbar.collapseActionView();
      return true;
    } 
    return false;
  }
  
  public void dispatchMenuVisibilityChanged(boolean paramBoolean) {
    if (paramBoolean == this.mLastMenuVisibility)
      return; 
    this.mLastMenuVisibility = paramBoolean;
    int i = this.mMenuVisibilityListeners.size();
    for (byte b = 0; b < i; b++)
      ((ActionBar.OnMenuVisibilityListener)this.mMenuVisibilityListeners.get(b)).onMenuVisibilityChanged(paramBoolean); 
  }
  
  public View getCustomView() {
    return this.mDecorToolbar.getCustomView();
  }
  
  public int getDisplayOptions() {
    return this.mDecorToolbar.getDisplayOptions();
  }
  
  public float getElevation() {
    return ViewCompat.getElevation((View)this.mDecorToolbar.getViewGroup());
  }
  
  public int getHeight() {
    return this.mDecorToolbar.getHeight();
  }
  
  public int getNavigationItemCount() {
    return 0;
  }
  
  public int getNavigationMode() {
    return 0;
  }
  
  public int getSelectedNavigationIndex() {
    return -1;
  }
  
  public ActionBar.Tab getSelectedTab() {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public CharSequence getSubtitle() {
    return this.mDecorToolbar.getSubtitle();
  }
  
  public ActionBar.Tab getTabAt(int paramInt) {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public int getTabCount() {
    return 0;
  }
  
  public Context getThemedContext() {
    return this.mDecorToolbar.getContext();
  }
  
  public CharSequence getTitle() {
    return this.mDecorToolbar.getTitle();
  }
  
  public Window.Callback getWrappedWindowCallback() {
    return this.mWindowCallback;
  }
  
  public void hide() {
    this.mDecorToolbar.setVisibility(8);
  }
  
  public boolean invalidateOptionsMenu() {
    this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator);
    ViewCompat.postOnAnimation((View)this.mDecorToolbar.getViewGroup(), this.mMenuInvalidator);
    return true;
  }
  
  public boolean isShowing() {
    boolean bool;
    if (this.mDecorToolbar.getVisibility() == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isTitleTruncated() {
    return super.isTitleTruncated();
  }
  
  public ActionBar.Tab newTab() {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    super.onConfigurationChanged(paramConfiguration);
  }
  
  void onDestroy() {
    this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator);
  }
  
  public boolean onKeyShortcut(int paramInt, KeyEvent paramKeyEvent) {
    Menu menu = getMenu();
    if (menu != null) {
      if (paramKeyEvent != null) {
        i = paramKeyEvent.getDeviceId();
      } else {
        i = -1;
      } 
      int i = KeyCharacterMap.load(i).getKeyboardType();
      boolean bool = true;
      if (i == 1)
        bool = false; 
      menu.setQwertyMode(bool);
      return menu.performShortcut(paramInt, paramKeyEvent, 0);
    } 
    return false;
  }
  
  public boolean onMenuKeyEvent(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getAction() == 1)
      openOptionsMenu(); 
    return true;
  }
  
  public boolean openOptionsMenu() {
    return this.mDecorToolbar.showOverflowMenu();
  }
  
  void populateOptionsMenu() {
    MenuBuilder menuBuilder;
    null = getMenu();
    if (null instanceof MenuBuilder) {
      menuBuilder = (MenuBuilder)null;
    } else {
      menuBuilder = null;
    } 
    if (menuBuilder != null)
      menuBuilder.stopDispatchingItemsChanged(); 
    try {
      null.clear();
      if (!this.mWindowCallback.onCreatePanelMenu(0, null) || !this.mWindowCallback.onPreparePanel(0, null, null))
        null.clear(); 
      return;
    } finally {
      if (menuBuilder != null)
        menuBuilder.startDispatchingItemsChanged(); 
    } 
  }
  
  public void removeAllTabs() {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener paramOnMenuVisibilityListener) {
    this.mMenuVisibilityListeners.remove(paramOnMenuVisibilityListener);
  }
  
  public void removeTab(ActionBar.Tab paramTab) {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public void removeTabAt(int paramInt) {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public boolean requestFocus() {
    ViewGroup viewGroup = this.mDecorToolbar.getViewGroup();
    if (viewGroup != null && !viewGroup.hasFocus()) {
      viewGroup.requestFocus();
      return true;
    } 
    return false;
  }
  
  public void selectTab(ActionBar.Tab paramTab) {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable) {
    this.mDecorToolbar.setBackgroundDrawable(paramDrawable);
  }
  
  public void setCustomView(int paramInt) {
    setCustomView(LayoutInflater.from(this.mDecorToolbar.getContext()).inflate(paramInt, this.mDecorToolbar.getViewGroup(), false));
  }
  
  public void setCustomView(View paramView) {
    setCustomView(paramView, new ActionBar.LayoutParams(-2, -2));
  }
  
  public void setCustomView(View paramView, ActionBar.LayoutParams paramLayoutParams) {
    if (paramView != null)
      paramView.setLayoutParams((ViewGroup.LayoutParams)paramLayoutParams); 
    this.mDecorToolbar.setCustomView(paramView);
  }
  
  public void setDefaultDisplayHomeAsUpEnabled(boolean paramBoolean) {}
  
  public void setDisplayHomeAsUpEnabled(boolean paramBoolean) {
    boolean bool;
    if (paramBoolean) {
      bool = true;
    } else {
      bool = false;
    } 
    setDisplayOptions(bool, 4);
  }
  
  public void setDisplayOptions(int paramInt) {
    setDisplayOptions(paramInt, -1);
  }
  
  public void setDisplayOptions(int paramInt1, int paramInt2) {
    int i = this.mDecorToolbar.getDisplayOptions();
    this.mDecorToolbar.setDisplayOptions(paramInt1 & paramInt2 | paramInt2 & i);
  }
  
  public void setDisplayShowCustomEnabled(boolean paramBoolean) {
    boolean bool;
    if (paramBoolean) {
      bool = true;
    } else {
      bool = false;
    } 
    setDisplayOptions(bool, 16);
  }
  
  public void setDisplayShowHomeEnabled(boolean paramBoolean) {
    boolean bool;
    if (paramBoolean) {
      bool = true;
    } else {
      bool = false;
    } 
    setDisplayOptions(bool, 2);
  }
  
  public void setDisplayShowTitleEnabled(boolean paramBoolean) {
    boolean bool;
    if (paramBoolean) {
      bool = true;
    } else {
      bool = false;
    } 
    setDisplayOptions(bool, 8);
  }
  
  public void setDisplayUseLogoEnabled(boolean paramBoolean) {
    setDisplayOptions(paramBoolean, 1);
  }
  
  public void setElevation(float paramFloat) {
    ViewCompat.setElevation((View)this.mDecorToolbar.getViewGroup(), paramFloat);
  }
  
  public void setHomeActionContentDescription(int paramInt) {
    this.mDecorToolbar.setNavigationContentDescription(paramInt);
  }
  
  public void setHomeActionContentDescription(CharSequence paramCharSequence) {
    this.mDecorToolbar.setNavigationContentDescription(paramCharSequence);
  }
  
  public void setHomeAsUpIndicator(int paramInt) {
    this.mDecorToolbar.setNavigationIcon(paramInt);
  }
  
  public void setHomeAsUpIndicator(Drawable paramDrawable) {
    this.mDecorToolbar.setNavigationIcon(paramDrawable);
  }
  
  public void setHomeButtonEnabled(boolean paramBoolean) {}
  
  public void setIcon(int paramInt) {
    this.mDecorToolbar.setIcon(paramInt);
  }
  
  public void setIcon(Drawable paramDrawable) {
    this.mDecorToolbar.setIcon(paramDrawable);
  }
  
  public void setListNavigationCallbacks(SpinnerAdapter paramSpinnerAdapter, ActionBar.OnNavigationListener paramOnNavigationListener) {
    this.mDecorToolbar.setDropdownParams(paramSpinnerAdapter, new NavItemSelectedListener(paramOnNavigationListener));
  }
  
  public void setLogo(int paramInt) {
    this.mDecorToolbar.setLogo(paramInt);
  }
  
  public void setLogo(Drawable paramDrawable) {
    this.mDecorToolbar.setLogo(paramDrawable);
  }
  
  public void setNavigationMode(int paramInt) {
    if (paramInt != 2) {
      this.mDecorToolbar.setNavigationMode(paramInt);
      return;
    } 
    throw new IllegalArgumentException("Tabs not supported in this configuration");
  }
  
  public void setSelectedNavigationItem(int paramInt) {
    if (this.mDecorToolbar.getNavigationMode() == 1) {
      this.mDecorToolbar.setDropdownSelectedPosition(paramInt);
      return;
    } 
    throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
  }
  
  public void setShowHideAnimationEnabled(boolean paramBoolean) {}
  
  public void setSplitBackgroundDrawable(Drawable paramDrawable) {}
  
  public void setStackedBackgroundDrawable(Drawable paramDrawable) {}
  
  public void setSubtitle(int paramInt) {
    CharSequence charSequence;
    DecorToolbar decorToolbar = this.mDecorToolbar;
    if (paramInt != 0) {
      charSequence = decorToolbar.getContext().getText(paramInt);
    } else {
      charSequence = null;
    } 
    decorToolbar.setSubtitle(charSequence);
  }
  
  public void setSubtitle(CharSequence paramCharSequence) {
    this.mDecorToolbar.setSubtitle(paramCharSequence);
  }
  
  public void setTitle(int paramInt) {
    CharSequence charSequence;
    DecorToolbar decorToolbar = this.mDecorToolbar;
    if (paramInt != 0) {
      charSequence = decorToolbar.getContext().getText(paramInt);
    } else {
      charSequence = null;
    } 
    decorToolbar.setTitle(charSequence);
  }
  
  public void setTitle(CharSequence paramCharSequence) {
    this.mDecorToolbar.setTitle(paramCharSequence);
  }
  
  public void setWindowTitle(CharSequence paramCharSequence) {
    this.mDecorToolbar.setWindowTitle(paramCharSequence);
  }
  
  public void show() {
    this.mDecorToolbar.setVisibility(0);
  }
  
  private final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
    private boolean mClosingActionMenu;
    
    public void onCloseMenu(MenuBuilder param1MenuBuilder, boolean param1Boolean) {
      if (this.mClosingActionMenu)
        return; 
      this.mClosingActionMenu = true;
      ToolbarActionBar.this.mDecorToolbar.dismissPopupMenus();
      if (ToolbarActionBar.this.mWindowCallback != null)
        ToolbarActionBar.this.mWindowCallback.onPanelClosed(108, (Menu)param1MenuBuilder); 
      this.mClosingActionMenu = false;
    }
    
    public boolean onOpenSubMenu(MenuBuilder param1MenuBuilder) {
      if (ToolbarActionBar.this.mWindowCallback != null) {
        ToolbarActionBar.this.mWindowCallback.onMenuOpened(108, (Menu)param1MenuBuilder);
        return true;
      } 
      return false;
    }
  }
  
  private final class MenuBuilderCallback implements MenuBuilder.Callback {
    public boolean onMenuItemSelected(MenuBuilder param1MenuBuilder, MenuItem param1MenuItem) {
      return false;
    }
    
    public void onMenuModeChange(MenuBuilder param1MenuBuilder) {
      if (ToolbarActionBar.this.mWindowCallback != null)
        if (ToolbarActionBar.this.mDecorToolbar.isOverflowMenuShowing()) {
          ToolbarActionBar.this.mWindowCallback.onPanelClosed(108, (Menu)param1MenuBuilder);
        } else if (ToolbarActionBar.this.mWindowCallback.onPreparePanel(0, null, (Menu)param1MenuBuilder)) {
          ToolbarActionBar.this.mWindowCallback.onMenuOpened(108, (Menu)param1MenuBuilder);
        }  
    }
  }
  
  private class ToolbarCallbackWrapper extends WindowCallbackWrapper {
    public ToolbarCallbackWrapper(Window.Callback param1Callback) {
      super(param1Callback);
    }
    
    public View onCreatePanelView(int param1Int) {
      return (param1Int == 0) ? new View(ToolbarActionBar.this.mDecorToolbar.getContext()) : super.onCreatePanelView(param1Int);
    }
    
    public boolean onPreparePanel(int param1Int, View param1View, Menu param1Menu) {
      boolean bool = super.onPreparePanel(param1Int, param1View, param1Menu);
      if (bool && !ToolbarActionBar.this.mToolbarMenuPrepared) {
        ToolbarActionBar.this.mDecorToolbar.setMenuPrepared();
        ToolbarActionBar.this.mToolbarMenuPrepared = true;
      } 
      return bool;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\app\ToolbarActionBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */