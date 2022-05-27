package android.support.v7.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class AppCompatActivity extends FragmentActivity implements AppCompatCallback, TaskStackBuilder.SupportParentable, ActionBarDrawerToggle.DelegateProvider {
  private AppCompatDelegate mDelegate;
  
  private Resources mResources;
  
  private int mThemeId = 0;
  
  private boolean performMenuItemShortcut(int paramInt, KeyEvent paramKeyEvent) {
    if (Build.VERSION.SDK_INT < 26 && !paramKeyEvent.isCtrlPressed() && !KeyEvent.metaStateHasNoModifiers(paramKeyEvent.getMetaState()) && paramKeyEvent.getRepeatCount() == 0 && !KeyEvent.isModifierKey(paramKeyEvent.getKeyCode())) {
      Window window = getWindow();
      if (window != null && window.getDecorView() != null && window.getDecorView().dispatchKeyShortcutEvent(paramKeyEvent))
        return true; 
    } 
    return false;
  }
  
  public void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams) {
    getDelegate().addContentView(paramView, paramLayoutParams);
  }
  
  public void closeOptionsMenu() {
    ActionBar actionBar = getSupportActionBar();
    if (getWindow().hasFeature(0) && (actionBar == null || !actionBar.closeOptionsMenu()))
      super.closeOptionsMenu(); 
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
    int i = paramKeyEvent.getKeyCode();
    ActionBar actionBar = getSupportActionBar();
    return (i == 82 && actionBar != null && actionBar.onMenuKeyEvent(paramKeyEvent)) ? true : super.dispatchKeyEvent(paramKeyEvent);
  }
  
  public <T extends View> T findViewById(int paramInt) {
    return getDelegate().findViewById(paramInt);
  }
  
  public AppCompatDelegate getDelegate() {
    if (this.mDelegate == null)
      this.mDelegate = AppCompatDelegate.create((Activity)this, this); 
    return this.mDelegate;
  }
  
  public ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
    return getDelegate().getDrawerToggleDelegate();
  }
  
  public MenuInflater getMenuInflater() {
    return getDelegate().getMenuInflater();
  }
  
  public Resources getResources() {
    if (this.mResources == null && VectorEnabledTintResources.shouldBeUsed())
      this.mResources = (Resources)new VectorEnabledTintResources((Context)this, super.getResources()); 
    Resources resources1 = this.mResources;
    Resources resources2 = resources1;
    if (resources1 == null)
      resources2 = super.getResources(); 
    return resources2;
  }
  
  public ActionBar getSupportActionBar() {
    return getDelegate().getSupportActionBar();
  }
  
  public Intent getSupportParentActivityIntent() {
    return NavUtils.getParentActivityIntent((Activity)this);
  }
  
  public void invalidateOptionsMenu() {
    getDelegate().invalidateOptionsMenu();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    super.onConfigurationChanged(paramConfiguration);
    getDelegate().onConfigurationChanged(paramConfiguration);
    if (this.mResources != null) {
      DisplayMetrics displayMetrics = super.getResources().getDisplayMetrics();
      this.mResources.updateConfiguration(paramConfiguration, displayMetrics);
    } 
  }
  
  public void onContentChanged() {
    onSupportContentChanged();
  }
  
  protected void onCreate(Bundle paramBundle) {
    AppCompatDelegate appCompatDelegate = getDelegate();
    appCompatDelegate.installViewFactory();
    appCompatDelegate.onCreate(paramBundle);
    if (appCompatDelegate.applyDayNight() && this.mThemeId != 0)
      if (Build.VERSION.SDK_INT >= 23) {
        onApplyThemeResource(getTheme(), this.mThemeId, false);
      } else {
        setTheme(this.mThemeId);
      }  
    super.onCreate(paramBundle);
  }
  
  public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder paramTaskStackBuilder) {
    paramTaskStackBuilder.addParentStack((Activity)this);
  }
  
  protected void onDestroy() {
    super.onDestroy();
    getDelegate().onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    return performMenuItemShortcut(paramInt, paramKeyEvent) ? true : super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public final boolean onMenuItemSelected(int paramInt, MenuItem paramMenuItem) {
    if (super.onMenuItemSelected(paramInt, paramMenuItem))
      return true; 
    ActionBar actionBar = getSupportActionBar();
    return (paramMenuItem.getItemId() == 16908332 && actionBar != null && (actionBar.getDisplayOptions() & 0x4) != 0) ? onSupportNavigateUp() : false;
  }
  
  public boolean onMenuOpened(int paramInt, Menu paramMenu) {
    return super.onMenuOpened(paramInt, paramMenu);
  }
  
  public void onPanelClosed(int paramInt, Menu paramMenu) {
    super.onPanelClosed(paramInt, paramMenu);
  }
  
  protected void onPostCreate(Bundle paramBundle) {
    super.onPostCreate(paramBundle);
    getDelegate().onPostCreate(paramBundle);
  }
  
  protected void onPostResume() {
    super.onPostResume();
    getDelegate().onPostResume();
  }
  
  public void onPrepareSupportNavigateUpTaskStack(TaskStackBuilder paramTaskStackBuilder) {}
  
  protected void onSaveInstanceState(Bundle paramBundle) {
    super.onSaveInstanceState(paramBundle);
    getDelegate().onSaveInstanceState(paramBundle);
  }
  
  protected void onStart() {
    super.onStart();
    getDelegate().onStart();
  }
  
  protected void onStop() {
    super.onStop();
    getDelegate().onStop();
  }
  
  public void onSupportActionModeFinished(ActionMode paramActionMode) {}
  
  public void onSupportActionModeStarted(ActionMode paramActionMode) {}
  
  @Deprecated
  public void onSupportContentChanged() {}
  
  public boolean onSupportNavigateUp() {
    Intent intent = getSupportParentActivityIntent();
    if (intent != null) {
      if (supportShouldUpRecreateTask(intent)) {
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create((Context)this);
        onCreateSupportNavigateUpTaskStack(taskStackBuilder);
        onPrepareSupportNavigateUpTaskStack(taskStackBuilder);
        taskStackBuilder.startActivities();
        try {
          ActivityCompat.finishAffinity((Activity)this);
        } catch (IllegalStateException illegalStateException) {
          finish();
        } 
      } else {
        supportNavigateUpTo((Intent)illegalStateException);
      } 
      return true;
    } 
    return false;
  }
  
  protected void onTitleChanged(CharSequence paramCharSequence, int paramInt) {
    super.onTitleChanged(paramCharSequence, paramInt);
    getDelegate().setTitle(paramCharSequence);
  }
  
  public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback paramCallback) {
    return null;
  }
  
  public void openOptionsMenu() {
    ActionBar actionBar = getSupportActionBar();
    if (getWindow().hasFeature(0) && (actionBar == null || !actionBar.openOptionsMenu()))
      super.openOptionsMenu(); 
  }
  
  public void setContentView(int paramInt) {
    getDelegate().setContentView(paramInt);
  }
  
  public void setContentView(View paramView) {
    getDelegate().setContentView(paramView);
  }
  
  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams) {
    getDelegate().setContentView(paramView, paramLayoutParams);
  }
  
  public void setSupportActionBar(Toolbar paramToolbar) {
    getDelegate().setSupportActionBar(paramToolbar);
  }
  
  @Deprecated
  public void setSupportProgress(int paramInt) {}
  
  @Deprecated
  public void setSupportProgressBarIndeterminate(boolean paramBoolean) {}
  
  @Deprecated
  public void setSupportProgressBarIndeterminateVisibility(boolean paramBoolean) {}
  
  @Deprecated
  public void setSupportProgressBarVisibility(boolean paramBoolean) {}
  
  public void setTheme(int paramInt) {
    super.setTheme(paramInt);
    this.mThemeId = paramInt;
  }
  
  public ActionMode startSupportActionMode(ActionMode.Callback paramCallback) {
    return getDelegate().startSupportActionMode(paramCallback);
  }
  
  public void supportInvalidateOptionsMenu() {
    getDelegate().invalidateOptionsMenu();
  }
  
  public void supportNavigateUpTo(Intent paramIntent) {
    NavUtils.navigateUpTo((Activity)this, paramIntent);
  }
  
  public boolean supportRequestWindowFeature(int paramInt) {
    return getDelegate().requestWindowFeature(paramInt);
  }
  
  public boolean supportShouldUpRecreateTask(Intent paramIntent) {
    return NavUtils.shouldUpRecreateTask((Activity)this, paramIntent);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\app\AppCompatActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */