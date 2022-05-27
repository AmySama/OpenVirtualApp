package android.support.v7.view.menu;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ActionProvider;
import android.support.v7.view.CollapsibleActionView;
import android.util.Log;
import android.view.ActionProvider;
import android.view.CollapsibleActionView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;
import java.lang.reflect.Method;

public class MenuItemWrapperICS extends BaseMenuWrapper<SupportMenuItem> implements MenuItem {
  static final String LOG_TAG = "MenuItemWrapper";
  
  private Method mSetExclusiveCheckableMethod;
  
  MenuItemWrapperICS(Context paramContext, SupportMenuItem paramSupportMenuItem) {
    super(paramContext, paramSupportMenuItem);
  }
  
  public boolean collapseActionView() {
    return this.mWrappedObject.collapseActionView();
  }
  
  ActionProviderWrapper createActionProviderWrapper(ActionProvider paramActionProvider) {
    return new ActionProviderWrapper(this.mContext, paramActionProvider);
  }
  
  public boolean expandActionView() {
    return this.mWrappedObject.expandActionView();
  }
  
  public ActionProvider getActionProvider() {
    ActionProvider actionProvider = this.mWrappedObject.getSupportActionProvider();
    return (actionProvider instanceof ActionProviderWrapper) ? ((ActionProviderWrapper)actionProvider).mInner : null;
  }
  
  public View getActionView() {
    View view1 = this.mWrappedObject.getActionView();
    View view2 = view1;
    if (view1 instanceof CollapsibleActionViewWrapper)
      view2 = ((CollapsibleActionViewWrapper)view1).getWrappedView(); 
    return view2;
  }
  
  public int getAlphabeticModifiers() {
    return this.mWrappedObject.getAlphabeticModifiers();
  }
  
  public char getAlphabeticShortcut() {
    return this.mWrappedObject.getAlphabeticShortcut();
  }
  
  public CharSequence getContentDescription() {
    return this.mWrappedObject.getContentDescription();
  }
  
  public int getGroupId() {
    return this.mWrappedObject.getGroupId();
  }
  
  public Drawable getIcon() {
    return this.mWrappedObject.getIcon();
  }
  
  public ColorStateList getIconTintList() {
    return this.mWrappedObject.getIconTintList();
  }
  
  public PorterDuff.Mode getIconTintMode() {
    return this.mWrappedObject.getIconTintMode();
  }
  
  public Intent getIntent() {
    return this.mWrappedObject.getIntent();
  }
  
  public int getItemId() {
    return this.mWrappedObject.getItemId();
  }
  
  public ContextMenu.ContextMenuInfo getMenuInfo() {
    return this.mWrappedObject.getMenuInfo();
  }
  
  public int getNumericModifiers() {
    return this.mWrappedObject.getNumericModifiers();
  }
  
  public char getNumericShortcut() {
    return this.mWrappedObject.getNumericShortcut();
  }
  
  public int getOrder() {
    return this.mWrappedObject.getOrder();
  }
  
  public SubMenu getSubMenu() {
    return getSubMenuWrapper(this.mWrappedObject.getSubMenu());
  }
  
  public CharSequence getTitle() {
    return this.mWrappedObject.getTitle();
  }
  
  public CharSequence getTitleCondensed() {
    return this.mWrappedObject.getTitleCondensed();
  }
  
  public CharSequence getTooltipText() {
    return this.mWrappedObject.getTooltipText();
  }
  
  public boolean hasSubMenu() {
    return this.mWrappedObject.hasSubMenu();
  }
  
  public boolean isActionViewExpanded() {
    return this.mWrappedObject.isActionViewExpanded();
  }
  
  public boolean isCheckable() {
    return this.mWrappedObject.isCheckable();
  }
  
  public boolean isChecked() {
    return this.mWrappedObject.isChecked();
  }
  
  public boolean isEnabled() {
    return this.mWrappedObject.isEnabled();
  }
  
  public boolean isVisible() {
    return this.mWrappedObject.isVisible();
  }
  
  public MenuItem setActionProvider(ActionProvider paramActionProvider) {
    SupportMenuItem supportMenuItem = this.mWrappedObject;
    if (paramActionProvider != null) {
      ActionProviderWrapper actionProviderWrapper = createActionProviderWrapper(paramActionProvider);
    } else {
      paramActionProvider = null;
    } 
    supportMenuItem.setSupportActionProvider((ActionProvider)paramActionProvider);
    return this;
  }
  
  public MenuItem setActionView(int paramInt) {
    this.mWrappedObject.setActionView(paramInt);
    View view = this.mWrappedObject.getActionView();
    if (view instanceof CollapsibleActionView)
      this.mWrappedObject.setActionView((View)new CollapsibleActionViewWrapper(view)); 
    return this;
  }
  
  public MenuItem setActionView(View paramView) {
    CollapsibleActionViewWrapper collapsibleActionViewWrapper;
    View view = paramView;
    if (paramView instanceof CollapsibleActionView)
      collapsibleActionViewWrapper = new CollapsibleActionViewWrapper(paramView); 
    this.mWrappedObject.setActionView((View)collapsibleActionViewWrapper);
    return this;
  }
  
  public MenuItem setAlphabeticShortcut(char paramChar) {
    this.mWrappedObject.setAlphabeticShortcut(paramChar);
    return this;
  }
  
  public MenuItem setAlphabeticShortcut(char paramChar, int paramInt) {
    this.mWrappedObject.setAlphabeticShortcut(paramChar, paramInt);
    return this;
  }
  
  public MenuItem setCheckable(boolean paramBoolean) {
    this.mWrappedObject.setCheckable(paramBoolean);
    return this;
  }
  
  public MenuItem setChecked(boolean paramBoolean) {
    this.mWrappedObject.setChecked(paramBoolean);
    return this;
  }
  
  public MenuItem setContentDescription(CharSequence paramCharSequence) {
    this.mWrappedObject.setContentDescription(paramCharSequence);
    return this;
  }
  
  public MenuItem setEnabled(boolean paramBoolean) {
    this.mWrappedObject.setEnabled(paramBoolean);
    return this;
  }
  
  public void setExclusiveCheckable(boolean paramBoolean) {
    try {
      if (this.mSetExclusiveCheckableMethod == null)
        this.mSetExclusiveCheckableMethod = this.mWrappedObject.getClass().getDeclaredMethod("setExclusiveCheckable", new Class[] { boolean.class }); 
      this.mSetExclusiveCheckableMethod.invoke(this.mWrappedObject, new Object[] { Boolean.valueOf(paramBoolean) });
    } catch (Exception exception) {
      Log.w("MenuItemWrapper", "Error while calling setExclusiveCheckable", exception);
    } 
  }
  
  public MenuItem setIcon(int paramInt) {
    this.mWrappedObject.setIcon(paramInt);
    return this;
  }
  
  public MenuItem setIcon(Drawable paramDrawable) {
    this.mWrappedObject.setIcon(paramDrawable);
    return this;
  }
  
  public MenuItem setIconTintList(ColorStateList paramColorStateList) {
    this.mWrappedObject.setIconTintList(paramColorStateList);
    return this;
  }
  
  public MenuItem setIconTintMode(PorterDuff.Mode paramMode) {
    this.mWrappedObject.setIconTintMode(paramMode);
    return this;
  }
  
  public MenuItem setIntent(Intent paramIntent) {
    this.mWrappedObject.setIntent(paramIntent);
    return this;
  }
  
  public MenuItem setNumericShortcut(char paramChar) {
    this.mWrappedObject.setNumericShortcut(paramChar);
    return this;
  }
  
  public MenuItem setNumericShortcut(char paramChar, int paramInt) {
    this.mWrappedObject.setNumericShortcut(paramChar, paramInt);
    return this;
  }
  
  public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener paramOnActionExpandListener) {
    SupportMenuItem supportMenuItem = this.mWrappedObject;
    if (paramOnActionExpandListener != null) {
      paramOnActionExpandListener = new OnActionExpandListenerWrapper(paramOnActionExpandListener);
    } else {
      paramOnActionExpandListener = null;
    } 
    supportMenuItem.setOnActionExpandListener(paramOnActionExpandListener);
    return this;
  }
  
  public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener paramOnMenuItemClickListener) {
    SupportMenuItem supportMenuItem = this.mWrappedObject;
    if (paramOnMenuItemClickListener != null) {
      paramOnMenuItemClickListener = new OnMenuItemClickListenerWrapper(paramOnMenuItemClickListener);
    } else {
      paramOnMenuItemClickListener = null;
    } 
    supportMenuItem.setOnMenuItemClickListener(paramOnMenuItemClickListener);
    return this;
  }
  
  public MenuItem setShortcut(char paramChar1, char paramChar2) {
    this.mWrappedObject.setShortcut(paramChar1, paramChar2);
    return this;
  }
  
  public MenuItem setShortcut(char paramChar1, char paramChar2, int paramInt1, int paramInt2) {
    this.mWrappedObject.setShortcut(paramChar1, paramChar2, paramInt1, paramInt2);
    return this;
  }
  
  public void setShowAsAction(int paramInt) {
    this.mWrappedObject.setShowAsAction(paramInt);
  }
  
  public MenuItem setShowAsActionFlags(int paramInt) {
    this.mWrappedObject.setShowAsActionFlags(paramInt);
    return this;
  }
  
  public MenuItem setTitle(int paramInt) {
    this.mWrappedObject.setTitle(paramInt);
    return this;
  }
  
  public MenuItem setTitle(CharSequence paramCharSequence) {
    this.mWrappedObject.setTitle(paramCharSequence);
    return this;
  }
  
  public MenuItem setTitleCondensed(CharSequence paramCharSequence) {
    this.mWrappedObject.setTitleCondensed(paramCharSequence);
    return this;
  }
  
  public MenuItem setTooltipText(CharSequence paramCharSequence) {
    this.mWrappedObject.setTooltipText(paramCharSequence);
    return this;
  }
  
  public MenuItem setVisible(boolean paramBoolean) {
    return this.mWrappedObject.setVisible(paramBoolean);
  }
  
  class ActionProviderWrapper extends ActionProvider {
    final ActionProvider mInner;
    
    public ActionProviderWrapper(Context param1Context, ActionProvider param1ActionProvider) {
      super(param1Context);
      this.mInner = param1ActionProvider;
    }
    
    public boolean hasSubMenu() {
      return this.mInner.hasSubMenu();
    }
    
    public View onCreateActionView() {
      return this.mInner.onCreateActionView();
    }
    
    public boolean onPerformDefaultAction() {
      return this.mInner.onPerformDefaultAction();
    }
    
    public void onPrepareSubMenu(SubMenu param1SubMenu) {
      this.mInner.onPrepareSubMenu(MenuItemWrapperICS.this.getSubMenuWrapper(param1SubMenu));
    }
  }
  
  static class CollapsibleActionViewWrapper extends FrameLayout implements CollapsibleActionView {
    final CollapsibleActionView mWrappedView;
    
    CollapsibleActionViewWrapper(View param1View) {
      super(param1View.getContext());
      this.mWrappedView = (CollapsibleActionView)param1View;
      addView(param1View);
    }
    
    View getWrappedView() {
      return (View)this.mWrappedView;
    }
    
    public void onActionViewCollapsed() {
      this.mWrappedView.onActionViewCollapsed();
    }
    
    public void onActionViewExpanded() {
      this.mWrappedView.onActionViewExpanded();
    }
  }
  
  private class OnActionExpandListenerWrapper extends BaseWrapper<MenuItem.OnActionExpandListener> implements MenuItem.OnActionExpandListener {
    OnActionExpandListenerWrapper(MenuItem.OnActionExpandListener param1OnActionExpandListener) {
      super(param1OnActionExpandListener);
    }
    
    public boolean onMenuItemActionCollapse(MenuItem param1MenuItem) {
      return this.mWrappedObject.onMenuItemActionCollapse(MenuItemWrapperICS.this.getMenuItemWrapper(param1MenuItem));
    }
    
    public boolean onMenuItemActionExpand(MenuItem param1MenuItem) {
      return this.mWrappedObject.onMenuItemActionExpand(MenuItemWrapperICS.this.getMenuItemWrapper(param1MenuItem));
    }
  }
  
  private class OnMenuItemClickListenerWrapper extends BaseWrapper<MenuItem.OnMenuItemClickListener> implements MenuItem.OnMenuItemClickListener {
    OnMenuItemClickListenerWrapper(MenuItem.OnMenuItemClickListener param1OnMenuItemClickListener) {
      super(param1OnMenuItemClickListener);
    }
    
    public boolean onMenuItemClick(MenuItem param1MenuItem) {
      return this.mWrappedObject.onMenuItemClick(MenuItemWrapperICS.this.getMenuItemWrapper(param1MenuItem));
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\menu\MenuItemWrapperICS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */