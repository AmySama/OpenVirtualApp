package android.support.v7.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.appcompat.R;
import android.support.v7.view.ActionMode;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class ActionBar {
  public static final int DISPLAY_HOME_AS_UP = 4;
  
  public static final int DISPLAY_SHOW_CUSTOM = 16;
  
  public static final int DISPLAY_SHOW_HOME = 2;
  
  public static final int DISPLAY_SHOW_TITLE = 8;
  
  public static final int DISPLAY_USE_LOGO = 1;
  
  @Deprecated
  public static final int NAVIGATION_MODE_LIST = 1;
  
  @Deprecated
  public static final int NAVIGATION_MODE_STANDARD = 0;
  
  @Deprecated
  public static final int NAVIGATION_MODE_TABS = 2;
  
  public abstract void addOnMenuVisibilityListener(OnMenuVisibilityListener paramOnMenuVisibilityListener);
  
  @Deprecated
  public abstract void addTab(Tab paramTab);
  
  @Deprecated
  public abstract void addTab(Tab paramTab, int paramInt);
  
  @Deprecated
  public abstract void addTab(Tab paramTab, int paramInt, boolean paramBoolean);
  
  @Deprecated
  public abstract void addTab(Tab paramTab, boolean paramBoolean);
  
  public boolean closeOptionsMenu() {
    return false;
  }
  
  public boolean collapseActionView() {
    return false;
  }
  
  public void dispatchMenuVisibilityChanged(boolean paramBoolean) {}
  
  public abstract View getCustomView();
  
  public abstract int getDisplayOptions();
  
  public float getElevation() {
    return 0.0F;
  }
  
  public abstract int getHeight();
  
  public int getHideOffset() {
    return 0;
  }
  
  @Deprecated
  public abstract int getNavigationItemCount();
  
  @Deprecated
  public abstract int getNavigationMode();
  
  @Deprecated
  public abstract int getSelectedNavigationIndex();
  
  @Deprecated
  public abstract Tab getSelectedTab();
  
  public abstract CharSequence getSubtitle();
  
  @Deprecated
  public abstract Tab getTabAt(int paramInt);
  
  @Deprecated
  public abstract int getTabCount();
  
  public Context getThemedContext() {
    return null;
  }
  
  public abstract CharSequence getTitle();
  
  public abstract void hide();
  
  public boolean invalidateOptionsMenu() {
    return false;
  }
  
  public boolean isHideOnContentScrollEnabled() {
    return false;
  }
  
  public abstract boolean isShowing();
  
  public boolean isTitleTruncated() {
    return false;
  }
  
  @Deprecated
  public abstract Tab newTab();
  
  public void onConfigurationChanged(Configuration paramConfiguration) {}
  
  void onDestroy() {}
  
  public boolean onKeyShortcut(int paramInt, KeyEvent paramKeyEvent) {
    return false;
  }
  
  public boolean onMenuKeyEvent(KeyEvent paramKeyEvent) {
    return false;
  }
  
  public boolean openOptionsMenu() {
    return false;
  }
  
  @Deprecated
  public abstract void removeAllTabs();
  
  public abstract void removeOnMenuVisibilityListener(OnMenuVisibilityListener paramOnMenuVisibilityListener);
  
  @Deprecated
  public abstract void removeTab(Tab paramTab);
  
  @Deprecated
  public abstract void removeTabAt(int paramInt);
  
  boolean requestFocus() {
    return false;
  }
  
  @Deprecated
  public abstract void selectTab(Tab paramTab);
  
  public abstract void setBackgroundDrawable(Drawable paramDrawable);
  
  public abstract void setCustomView(int paramInt);
  
  public abstract void setCustomView(View paramView);
  
  public abstract void setCustomView(View paramView, LayoutParams paramLayoutParams);
  
  public void setDefaultDisplayHomeAsUpEnabled(boolean paramBoolean) {}
  
  public abstract void setDisplayHomeAsUpEnabled(boolean paramBoolean);
  
  public abstract void setDisplayOptions(int paramInt);
  
  public abstract void setDisplayOptions(int paramInt1, int paramInt2);
  
  public abstract void setDisplayShowCustomEnabled(boolean paramBoolean);
  
  public abstract void setDisplayShowHomeEnabled(boolean paramBoolean);
  
  public abstract void setDisplayShowTitleEnabled(boolean paramBoolean);
  
  public abstract void setDisplayUseLogoEnabled(boolean paramBoolean);
  
  public void setElevation(float paramFloat) {
    if (paramFloat == 0.0F)
      return; 
    throw new UnsupportedOperationException("Setting a non-zero elevation is not supported in this action bar configuration.");
  }
  
  public void setHideOffset(int paramInt) {
    if (paramInt == 0)
      return; 
    throw new UnsupportedOperationException("Setting an explicit action bar hide offset is not supported in this action bar configuration.");
  }
  
  public void setHideOnContentScrollEnabled(boolean paramBoolean) {
    if (!paramBoolean)
      return; 
    throw new UnsupportedOperationException("Hide on content scroll is not supported in this action bar configuration.");
  }
  
  public void setHomeActionContentDescription(int paramInt) {}
  
  public void setHomeActionContentDescription(CharSequence paramCharSequence) {}
  
  public void setHomeAsUpIndicator(int paramInt) {}
  
  public void setHomeAsUpIndicator(Drawable paramDrawable) {}
  
  public void setHomeButtonEnabled(boolean paramBoolean) {}
  
  public abstract void setIcon(int paramInt);
  
  public abstract void setIcon(Drawable paramDrawable);
  
  @Deprecated
  public abstract void setListNavigationCallbacks(SpinnerAdapter paramSpinnerAdapter, OnNavigationListener paramOnNavigationListener);
  
  public abstract void setLogo(int paramInt);
  
  public abstract void setLogo(Drawable paramDrawable);
  
  @Deprecated
  public abstract void setNavigationMode(int paramInt);
  
  @Deprecated
  public abstract void setSelectedNavigationItem(int paramInt);
  
  public void setShowHideAnimationEnabled(boolean paramBoolean) {}
  
  public void setSplitBackgroundDrawable(Drawable paramDrawable) {}
  
  public void setStackedBackgroundDrawable(Drawable paramDrawable) {}
  
  public abstract void setSubtitle(int paramInt);
  
  public abstract void setSubtitle(CharSequence paramCharSequence);
  
  public abstract void setTitle(int paramInt);
  
  public abstract void setTitle(CharSequence paramCharSequence);
  
  public void setWindowTitle(CharSequence paramCharSequence) {}
  
  public abstract void show();
  
  public ActionMode startActionMode(ActionMode.Callback paramCallback) {
    return null;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface DisplayOptions {}
  
  public static class LayoutParams extends ViewGroup.MarginLayoutParams {
    public int gravity = 0;
    
    public LayoutParams(int param1Int) {
      this(-2, -1, param1Int);
    }
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
      this.gravity = 8388627;
    }
    
    public LayoutParams(int param1Int1, int param1Int2, int param1Int3) {
      super(param1Int1, param1Int2);
      this.gravity = param1Int3;
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, R.styleable.ActionBarLayout);
      this.gravity = typedArray.getInt(R.styleable.ActionBarLayout_android_layout_gravity, 0);
      typedArray.recycle();
    }
    
    public LayoutParams(LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
      this.gravity = param1LayoutParams.gravity;
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface NavigationMode {}
  
  public static interface OnMenuVisibilityListener {
    void onMenuVisibilityChanged(boolean param1Boolean);
  }
  
  @Deprecated
  public static interface OnNavigationListener {
    boolean onNavigationItemSelected(int param1Int, long param1Long);
  }
  
  @Deprecated
  public static abstract class Tab {
    public static final int INVALID_POSITION = -1;
    
    public abstract CharSequence getContentDescription();
    
    public abstract View getCustomView();
    
    public abstract Drawable getIcon();
    
    public abstract int getPosition();
    
    public abstract Object getTag();
    
    public abstract CharSequence getText();
    
    public abstract void select();
    
    public abstract Tab setContentDescription(int param1Int);
    
    public abstract Tab setContentDescription(CharSequence param1CharSequence);
    
    public abstract Tab setCustomView(int param1Int);
    
    public abstract Tab setCustomView(View param1View);
    
    public abstract Tab setIcon(int param1Int);
    
    public abstract Tab setIcon(Drawable param1Drawable);
    
    public abstract Tab setTabListener(ActionBar.TabListener param1TabListener);
    
    public abstract Tab setTag(Object param1Object);
    
    public abstract Tab setText(int param1Int);
    
    public abstract Tab setText(CharSequence param1CharSequence);
  }
  
  @Deprecated
  public static interface TabListener {
    void onTabReselected(ActionBar.Tab param1Tab, FragmentTransaction param1FragmentTransaction);
    
    void onTabSelected(ActionBar.Tab param1Tab, FragmentTransaction param1FragmentTransaction);
    
    void onTabUnselected(ActionBar.Tab param1Tab, FragmentTransaction param1FragmentTransaction);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\app\ActionBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */