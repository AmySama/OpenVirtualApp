package android.support.v7.view;

import android.content.Context;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionBarContextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.lang.ref.WeakReference;

public class StandaloneActionMode extends ActionMode implements MenuBuilder.Callback {
  private ActionMode.Callback mCallback;
  
  private Context mContext;
  
  private ActionBarContextView mContextView;
  
  private WeakReference<View> mCustomView;
  
  private boolean mFinished;
  
  private boolean mFocusable;
  
  private MenuBuilder mMenu;
  
  public StandaloneActionMode(Context paramContext, ActionBarContextView paramActionBarContextView, ActionMode.Callback paramCallback, boolean paramBoolean) {
    this.mContext = paramContext;
    this.mContextView = paramActionBarContextView;
    this.mCallback = paramCallback;
    MenuBuilder menuBuilder = (new MenuBuilder(paramActionBarContextView.getContext())).setDefaultShowAsAction(1);
    this.mMenu = menuBuilder;
    menuBuilder.setCallback(this);
    this.mFocusable = paramBoolean;
  }
  
  public void finish() {
    if (this.mFinished)
      return; 
    this.mFinished = true;
    this.mContextView.sendAccessibilityEvent(32);
    this.mCallback.onDestroyActionMode(this);
  }
  
  public View getCustomView() {
    WeakReference<View> weakReference = this.mCustomView;
    if (weakReference != null) {
      View view = weakReference.get();
    } else {
      weakReference = null;
    } 
    return (View)weakReference;
  }
  
  public Menu getMenu() {
    return (Menu)this.mMenu;
  }
  
  public MenuInflater getMenuInflater() {
    return new SupportMenuInflater(this.mContextView.getContext());
  }
  
  public CharSequence getSubtitle() {
    return this.mContextView.getSubtitle();
  }
  
  public CharSequence getTitle() {
    return this.mContextView.getTitle();
  }
  
  public void invalidate() {
    this.mCallback.onPrepareActionMode(this, (Menu)this.mMenu);
  }
  
  public boolean isTitleOptional() {
    return this.mContextView.isTitleOptional();
  }
  
  public boolean isUiFocusable() {
    return this.mFocusable;
  }
  
  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {}
  
  public void onCloseSubMenu(SubMenuBuilder paramSubMenuBuilder) {}
  
  public boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem) {
    return this.mCallback.onActionItemClicked(this, paramMenuItem);
  }
  
  public void onMenuModeChange(MenuBuilder paramMenuBuilder) {
    invalidate();
    this.mContextView.showOverflowMenu();
  }
  
  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder) {
    if (!paramSubMenuBuilder.hasVisibleItems())
      return true; 
    (new MenuPopupHelper(this.mContextView.getContext(), (MenuBuilder)paramSubMenuBuilder)).show();
    return true;
  }
  
  public void setCustomView(View paramView) {
    this.mContextView.setCustomView(paramView);
    if (paramView != null) {
      WeakReference<View> weakReference = new WeakReference<View>(paramView);
    } else {
      paramView = null;
    } 
    this.mCustomView = (WeakReference<View>)paramView;
  }
  
  public void setSubtitle(int paramInt) {
    setSubtitle(this.mContext.getString(paramInt));
  }
  
  public void setSubtitle(CharSequence paramCharSequence) {
    this.mContextView.setSubtitle(paramCharSequence);
  }
  
  public void setTitle(int paramInt) {
    setTitle(this.mContext.getString(paramInt));
  }
  
  public void setTitle(CharSequence paramCharSequence) {
    this.mContextView.setTitle(paramCharSequence);
  }
  
  public void setTitleOptionalHint(boolean paramBoolean) {
    super.setTitleOptionalHint(paramBoolean);
    this.mContextView.setTitleOptional(paramBoolean);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\StandaloneActionMode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */