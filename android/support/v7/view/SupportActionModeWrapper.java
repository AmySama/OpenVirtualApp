package android.support.v7.view;

import android.content.Context;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.view.menu.MenuWrapperFactory;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;

public class SupportActionModeWrapper extends ActionMode {
  final Context mContext;
  
  final ActionMode mWrappedObject;
  
  public SupportActionModeWrapper(Context paramContext, ActionMode paramActionMode) {
    this.mContext = paramContext;
    this.mWrappedObject = paramActionMode;
  }
  
  public void finish() {
    this.mWrappedObject.finish();
  }
  
  public View getCustomView() {
    return this.mWrappedObject.getCustomView();
  }
  
  public Menu getMenu() {
    return MenuWrapperFactory.wrapSupportMenu(this.mContext, (SupportMenu)this.mWrappedObject.getMenu());
  }
  
  public MenuInflater getMenuInflater() {
    return this.mWrappedObject.getMenuInflater();
  }
  
  public CharSequence getSubtitle() {
    return this.mWrappedObject.getSubtitle();
  }
  
  public Object getTag() {
    return this.mWrappedObject.getTag();
  }
  
  public CharSequence getTitle() {
    return this.mWrappedObject.getTitle();
  }
  
  public boolean getTitleOptionalHint() {
    return this.mWrappedObject.getTitleOptionalHint();
  }
  
  public void invalidate() {
    this.mWrappedObject.invalidate();
  }
  
  public boolean isTitleOptional() {
    return this.mWrappedObject.isTitleOptional();
  }
  
  public void setCustomView(View paramView) {
    this.mWrappedObject.setCustomView(paramView);
  }
  
  public void setSubtitle(int paramInt) {
    this.mWrappedObject.setSubtitle(paramInt);
  }
  
  public void setSubtitle(CharSequence paramCharSequence) {
    this.mWrappedObject.setSubtitle(paramCharSequence);
  }
  
  public void setTag(Object paramObject) {
    this.mWrappedObject.setTag(paramObject);
  }
  
  public void setTitle(int paramInt) {
    this.mWrappedObject.setTitle(paramInt);
  }
  
  public void setTitle(CharSequence paramCharSequence) {
    this.mWrappedObject.setTitle(paramCharSequence);
  }
  
  public void setTitleOptionalHint(boolean paramBoolean) {
    this.mWrappedObject.setTitleOptionalHint(paramBoolean);
  }
  
  public static class CallbackWrapper implements ActionMode.Callback {
    final ArrayList<SupportActionModeWrapper> mActionModes;
    
    final Context mContext;
    
    final SimpleArrayMap<Menu, Menu> mMenus;
    
    final ActionMode.Callback mWrappedCallback;
    
    public CallbackWrapper(Context param1Context, ActionMode.Callback param1Callback) {
      this.mContext = param1Context;
      this.mWrappedCallback = param1Callback;
      this.mActionModes = new ArrayList<SupportActionModeWrapper>();
      this.mMenus = new SimpleArrayMap();
    }
    
    private Menu getMenuWrapper(Menu param1Menu) {
      Menu menu1 = (Menu)this.mMenus.get(param1Menu);
      Menu menu2 = menu1;
      if (menu1 == null) {
        menu2 = MenuWrapperFactory.wrapSupportMenu(this.mContext, (SupportMenu)param1Menu);
        this.mMenus.put(param1Menu, menu2);
      } 
      return menu2;
    }
    
    public ActionMode getActionModeWrapper(ActionMode param1ActionMode) {
      int i = this.mActionModes.size();
      for (byte b = 0; b < i; b++) {
        SupportActionModeWrapper supportActionModeWrapper1 = this.mActionModes.get(b);
        if (supportActionModeWrapper1 != null && supportActionModeWrapper1.mWrappedObject == param1ActionMode)
          return supportActionModeWrapper1; 
      } 
      SupportActionModeWrapper supportActionModeWrapper = new SupportActionModeWrapper(this.mContext, param1ActionMode);
      this.mActionModes.add(supportActionModeWrapper);
      return supportActionModeWrapper;
    }
    
    public boolean onActionItemClicked(ActionMode param1ActionMode, MenuItem param1MenuItem) {
      return this.mWrappedCallback.onActionItemClicked(getActionModeWrapper(param1ActionMode), MenuWrapperFactory.wrapSupportMenuItem(this.mContext, (SupportMenuItem)param1MenuItem));
    }
    
    public boolean onCreateActionMode(ActionMode param1ActionMode, Menu param1Menu) {
      return this.mWrappedCallback.onCreateActionMode(getActionModeWrapper(param1ActionMode), getMenuWrapper(param1Menu));
    }
    
    public void onDestroyActionMode(ActionMode param1ActionMode) {
      this.mWrappedCallback.onDestroyActionMode(getActionModeWrapper(param1ActionMode));
    }
    
    public boolean onPrepareActionMode(ActionMode param1ActionMode, Menu param1Menu) {
      return this.mWrappedCallback.onPrepareActionMode(getActionModeWrapper(param1ActionMode), getMenuWrapper(param1Menu));
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\SupportActionModeWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */