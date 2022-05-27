package android.support.v7.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.appcompat.R;
import android.support.v7.view.ActionMode;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.WindowCallbackWrapper;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.TintTypedArray;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;

abstract class AppCompatDelegateImplBase extends AppCompatDelegate {
  static final boolean DEBUG = false;
  
  static final String EXCEPTION_HANDLER_MESSAGE_SUFFIX = ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.";
  
  private static final boolean SHOULD_INSTALL_EXCEPTION_HANDLER;
  
  private static boolean sInstalledExceptionHandler;
  
  private static final int[] sWindowBackgroundStyleable = new int[] { 16842836 };
  
  ActionBar mActionBar;
  
  final AppCompatCallback mAppCompatCallback;
  
  final Window.Callback mAppCompatWindowCallback;
  
  final Context mContext;
  
  private boolean mEatKeyUpEvent;
  
  boolean mHasActionBar;
  
  private boolean mIsDestroyed;
  
  boolean mIsFloating;
  
  private boolean mIsStarted;
  
  MenuInflater mMenuInflater;
  
  final Window.Callback mOriginalWindowCallback;
  
  boolean mOverlayActionBar;
  
  boolean mOverlayActionMode;
  
  private CharSequence mTitle;
  
  final Window mWindow;
  
  boolean mWindowNoTitle;
  
  AppCompatDelegateImplBase(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback) {
    this.mContext = paramContext;
    this.mWindow = paramWindow;
    this.mAppCompatCallback = paramAppCompatCallback;
    Window.Callback callback = paramWindow.getCallback();
    this.mOriginalWindowCallback = callback;
    if (!(callback instanceof AppCompatWindowCallbackBase)) {
      callback = wrapWindowCallback(callback);
      this.mAppCompatWindowCallback = callback;
      this.mWindow.setCallback(callback);
      TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, null, sWindowBackgroundStyleable);
      Drawable drawable = tintTypedArray.getDrawableIfKnown(0);
      if (drawable != null)
        this.mWindow.setBackgroundDrawable(drawable); 
      tintTypedArray.recycle();
      return;
    } 
    throw new IllegalStateException("AppCompat has already installed itself into the Window");
  }
  
  public boolean applyDayNight() {
    return false;
  }
  
  abstract boolean dispatchKeyEvent(KeyEvent paramKeyEvent);
  
  final Context getActionBarThemedContext() {
    Context context;
    ActionBar actionBar1 = getSupportActionBar();
    if (actionBar1 != null) {
      Context context1 = actionBar1.getThemedContext();
    } else {
      actionBar1 = null;
    } 
    ActionBar actionBar2 = actionBar1;
    if (actionBar1 == null)
      context = this.mContext; 
    return context;
  }
  
  public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
    return new ActionBarDrawableToggleImpl();
  }
  
  public MenuInflater getMenuInflater() {
    if (this.mMenuInflater == null) {
      Context context;
      initWindowDecorActionBar();
      ActionBar actionBar = this.mActionBar;
      if (actionBar != null) {
        context = actionBar.getThemedContext();
      } else {
        context = this.mContext;
      } 
      this.mMenuInflater = (MenuInflater)new SupportMenuInflater(context);
    } 
    return this.mMenuInflater;
  }
  
  public ActionBar getSupportActionBar() {
    initWindowDecorActionBar();
    return this.mActionBar;
  }
  
  final CharSequence getTitle() {
    Window.Callback callback = this.mOriginalWindowCallback;
    return (callback instanceof Activity) ? ((Activity)callback).getTitle() : this.mTitle;
  }
  
  final Window.Callback getWindowCallback() {
    return this.mWindow.getCallback();
  }
  
  abstract void initWindowDecorActionBar();
  
  final boolean isDestroyed() {
    return this.mIsDestroyed;
  }
  
  public boolean isHandleNativeActionModesEnabled() {
    return false;
  }
  
  final boolean isStarted() {
    return this.mIsStarted;
  }
  
  public void onDestroy() {
    this.mIsDestroyed = true;
  }
  
  abstract boolean onKeyShortcut(int paramInt, KeyEvent paramKeyEvent);
  
  abstract boolean onMenuOpened(int paramInt, Menu paramMenu);
  
  abstract void onPanelClosed(int paramInt, Menu paramMenu);
  
  public void onSaveInstanceState(Bundle paramBundle) {}
  
  public void onStart() {
    this.mIsStarted = true;
  }
  
  public void onStop() {
    this.mIsStarted = false;
  }
  
  abstract void onTitleChanged(CharSequence paramCharSequence);
  
  final ActionBar peekSupportActionBar() {
    return this.mActionBar;
  }
  
  public void setHandleNativeActionModesEnabled(boolean paramBoolean) {}
  
  public void setLocalNightMode(int paramInt) {}
  
  public final void setTitle(CharSequence paramCharSequence) {
    this.mTitle = paramCharSequence;
    onTitleChanged(paramCharSequence);
  }
  
  abstract ActionMode startSupportActionModeFromWindow(ActionMode.Callback paramCallback);
  
  Window.Callback wrapWindowCallback(Window.Callback paramCallback) {
    return (Window.Callback)new AppCompatWindowCallbackBase(paramCallback);
  }
  
  static {
    boolean bool;
    if (Build.VERSION.SDK_INT < 21) {
      bool = true;
    } else {
      bool = false;
    } 
    SHOULD_INSTALL_EXCEPTION_HANDLER = bool;
    if (bool && !sInstalledExceptionHandler) {
      Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler()) {
            private boolean shouldWrapException(Throwable param1Throwable) {
              boolean bool = param1Throwable instanceof Resources.NotFoundException;
              boolean bool1 = false;
              null = bool1;
              if (bool) {
                String str = param1Throwable.getMessage();
                null = bool1;
                if (str != null) {
                  if (!str.contains("drawable")) {
                    null = bool1;
                    return str.contains("Drawable") ? true : null;
                  } 
                } else {
                  return null;
                } 
              } else {
                return null;
              } 
              return true;
            }
            
            public void uncaughtException(Thread param1Thread, Throwable param1Throwable) {
              if (shouldWrapException(param1Throwable)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(param1Throwable.getMessage());
                stringBuilder.append(". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.");
                Resources.NotFoundException notFoundException = new Resources.NotFoundException(stringBuilder.toString());
                notFoundException.initCause(param1Throwable.getCause());
                notFoundException.setStackTrace(param1Throwable.getStackTrace());
                defHandler.uncaughtException(param1Thread, (Throwable)notFoundException);
              } else {
                defHandler.uncaughtException(param1Thread, param1Throwable);
              } 
            }
          });
      sInstalledExceptionHandler = true;
    } 
  }
  
  private class ActionBarDrawableToggleImpl implements ActionBarDrawerToggle.Delegate {
    public Context getActionBarThemedContext() {
      return AppCompatDelegateImplBase.this.getActionBarThemedContext();
    }
    
    public Drawable getThemeUpIndicator() {
      TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(getActionBarThemedContext(), null, new int[] { R.attr.homeAsUpIndicator });
      Drawable drawable = tintTypedArray.getDrawable(0);
      tintTypedArray.recycle();
      return drawable;
    }
    
    public boolean isNavigationVisible() {
      boolean bool;
      ActionBar actionBar = AppCompatDelegateImplBase.this.getSupportActionBar();
      if (actionBar != null && (actionBar.getDisplayOptions() & 0x4) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void setActionBarDescription(int param1Int) {
      ActionBar actionBar = AppCompatDelegateImplBase.this.getSupportActionBar();
      if (actionBar != null)
        actionBar.setHomeActionContentDescription(param1Int); 
    }
    
    public void setActionBarUpIndicator(Drawable param1Drawable, int param1Int) {
      ActionBar actionBar = AppCompatDelegateImplBase.this.getSupportActionBar();
      if (actionBar != null) {
        actionBar.setHomeAsUpIndicator(param1Drawable);
        actionBar.setHomeActionContentDescription(param1Int);
      } 
    }
  }
  
  class AppCompatWindowCallbackBase extends WindowCallbackWrapper {
    AppCompatWindowCallbackBase(Window.Callback param1Callback) {
      super(param1Callback);
    }
    
    public boolean dispatchKeyEvent(KeyEvent param1KeyEvent) {
      return (AppCompatDelegateImplBase.this.dispatchKeyEvent(param1KeyEvent) || super.dispatchKeyEvent(param1KeyEvent));
    }
    
    public boolean dispatchKeyShortcutEvent(KeyEvent param1KeyEvent) {
      return (super.dispatchKeyShortcutEvent(param1KeyEvent) || AppCompatDelegateImplBase.this.onKeyShortcut(param1KeyEvent.getKeyCode(), param1KeyEvent));
    }
    
    public void onContentChanged() {}
    
    public boolean onCreatePanelMenu(int param1Int, Menu param1Menu) {
      return (param1Int == 0 && !(param1Menu instanceof MenuBuilder)) ? false : super.onCreatePanelMenu(param1Int, param1Menu);
    }
    
    public boolean onMenuOpened(int param1Int, Menu param1Menu) {
      super.onMenuOpened(param1Int, param1Menu);
      AppCompatDelegateImplBase.this.onMenuOpened(param1Int, param1Menu);
      return true;
    }
    
    public void onPanelClosed(int param1Int, Menu param1Menu) {
      super.onPanelClosed(param1Int, param1Menu);
      AppCompatDelegateImplBase.this.onPanelClosed(param1Int, param1Menu);
    }
    
    public boolean onPreparePanel(int param1Int, View param1View, Menu param1Menu) {
      MenuBuilder menuBuilder;
      if (param1Menu instanceof MenuBuilder) {
        menuBuilder = (MenuBuilder)param1Menu;
      } else {
        menuBuilder = null;
      } 
      if (param1Int == 0 && menuBuilder == null)
        return false; 
      if (menuBuilder != null)
        menuBuilder.setOverrideVisibleItems(true); 
      boolean bool = super.onPreparePanel(param1Int, param1View, param1Menu);
      if (menuBuilder != null)
        menuBuilder.setOverrideVisibleItems(false); 
      return bool;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\app\AppCompatDelegateImplBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */