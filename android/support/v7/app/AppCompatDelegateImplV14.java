package android.support.v7.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.view.SupportActionModeWrapper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.view.Window;

class AppCompatDelegateImplV14 extends AppCompatDelegateImplV9 {
  private static final String KEY_LOCAL_NIGHT_MODE = "appcompat:local_night_mode";
  
  private boolean mApplyDayNightCalled;
  
  private AutoNightModeManager mAutoNightModeManager;
  
  private boolean mHandleNativeActionModes = true;
  
  private int mLocalNightMode = -100;
  
  AppCompatDelegateImplV14(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback) {
    super(paramContext, paramWindow, paramAppCompatCallback);
  }
  
  private void ensureAutoNightModeManager() {
    if (this.mAutoNightModeManager == null)
      this.mAutoNightModeManager = new AutoNightModeManager(TwilightManager.getInstance(this.mContext)); 
  }
  
  private int getNightMode() {
    int i = this.mLocalNightMode;
    if (i == -100)
      i = getDefaultNightMode(); 
    return i;
  }
  
  private boolean shouldRecreateOnNightModeChange() {
    boolean bool = this.mApplyDayNightCalled;
    boolean bool1 = false;
    if (bool && this.mContext instanceof Activity) {
      PackageManager packageManager = this.mContext.getPackageManager();
      try {
        ComponentName componentName = new ComponentName();
        this(this.mContext, this.mContext.getClass());
        int i = (packageManager.getActivityInfo(componentName, 0)).configChanges;
        if ((i & 0x200) == 0)
          bool1 = true; 
        return bool1;
      } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
        Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", (Throwable)nameNotFoundException);
        return true;
      } 
    } 
    return false;
  }
  
  private boolean updateForNightMode(int paramInt) {
    Resources resources = this.mContext.getResources();
    Configuration configuration = resources.getConfiguration();
    int i = configuration.uiMode;
    if (paramInt == 2) {
      paramInt = 32;
    } else {
      paramInt = 16;
    } 
    if ((i & 0x30) != paramInt) {
      if (shouldRecreateOnNightModeChange()) {
        ((Activity)this.mContext).recreate();
      } else {
        Configuration configuration1 = new Configuration(configuration);
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration1.uiMode = paramInt | configuration1.uiMode & 0xFFFFFFCF;
        resources.updateConfiguration(configuration1, displayMetrics);
        if (Build.VERSION.SDK_INT < 26)
          ResourcesFlusher.flush(resources); 
      } 
      return true;
    } 
    return false;
  }
  
  public boolean applyDayNight() {
    boolean bool;
    int i = getNightMode();
    int j = mapNightMode(i);
    if (j != -1) {
      bool = updateForNightMode(j);
    } else {
      bool = false;
    } 
    if (i == 0) {
      ensureAutoNightModeManager();
      this.mAutoNightModeManager.setup();
    } 
    this.mApplyDayNightCalled = true;
    return bool;
  }
  
  View callActivityOnCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet) {
    return null;
  }
  
  final AutoNightModeManager getAutoNightModeManager() {
    ensureAutoNightModeManager();
    return this.mAutoNightModeManager;
  }
  
  public boolean hasWindowFeature(int paramInt) {
    return (super.hasWindowFeature(paramInt) || this.mWindow.hasFeature(paramInt));
  }
  
  public boolean isHandleNativeActionModesEnabled() {
    return this.mHandleNativeActionModes;
  }
  
  int mapNightMode(int paramInt) {
    if (paramInt != -100) {
      if (paramInt != 0)
        return paramInt; 
      ensureAutoNightModeManager();
      return this.mAutoNightModeManager.getApplyableNightMode();
    } 
    return -1;
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    if (paramBundle != null && this.mLocalNightMode == -100)
      this.mLocalNightMode = paramBundle.getInt("appcompat:local_night_mode", -100); 
  }
  
  public void onDestroy() {
    super.onDestroy();
    AutoNightModeManager autoNightModeManager = this.mAutoNightModeManager;
    if (autoNightModeManager != null)
      autoNightModeManager.cleanup(); 
  }
  
  public void onSaveInstanceState(Bundle paramBundle) {
    super.onSaveInstanceState(paramBundle);
    int i = this.mLocalNightMode;
    if (i != -100)
      paramBundle.putInt("appcompat:local_night_mode", i); 
  }
  
  public void onStart() {
    super.onStart();
    applyDayNight();
  }
  
  public void onStop() {
    super.onStop();
    AutoNightModeManager autoNightModeManager = this.mAutoNightModeManager;
    if (autoNightModeManager != null)
      autoNightModeManager.cleanup(); 
  }
  
  public void setHandleNativeActionModesEnabled(boolean paramBoolean) {
    this.mHandleNativeActionModes = paramBoolean;
  }
  
  public void setLocalNightMode(int paramInt) {
    if (paramInt != -1 && paramInt != 0 && paramInt != 1 && paramInt != 2) {
      Log.i("AppCompatDelegate", "setLocalNightMode() called with an unknown mode");
    } else if (this.mLocalNightMode != paramInt) {
      this.mLocalNightMode = paramInt;
      if (this.mApplyDayNightCalled)
        applyDayNight(); 
    } 
  }
  
  Window.Callback wrapWindowCallback(Window.Callback paramCallback) {
    return (Window.Callback)new AppCompatWindowCallbackV14(paramCallback);
  }
  
  class AppCompatWindowCallbackV14 extends AppCompatDelegateImplBase.AppCompatWindowCallbackBase {
    AppCompatWindowCallbackV14(Window.Callback param1Callback) {
      super(param1Callback);
    }
    
    public ActionMode onWindowStartingActionMode(ActionMode.Callback param1Callback) {
      return AppCompatDelegateImplV14.this.isHandleNativeActionModesEnabled() ? startAsSupportActionMode(param1Callback) : super.onWindowStartingActionMode(param1Callback);
    }
    
    final ActionMode startAsSupportActionMode(ActionMode.Callback param1Callback) {
      SupportActionModeWrapper.CallbackWrapper callbackWrapper = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImplV14.this.mContext, param1Callback);
      ActionMode actionMode = AppCompatDelegateImplV14.this.startSupportActionMode((ActionMode.Callback)callbackWrapper);
      return (actionMode != null) ? callbackWrapper.getActionModeWrapper(actionMode) : null;
    }
  }
  
  final class AutoNightModeManager {
    private BroadcastReceiver mAutoTimeChangeReceiver;
    
    private IntentFilter mAutoTimeChangeReceiverFilter;
    
    private boolean mIsNight;
    
    private TwilightManager mTwilightManager;
    
    AutoNightModeManager(TwilightManager param1TwilightManager) {
      this.mTwilightManager = param1TwilightManager;
      this.mIsNight = param1TwilightManager.isNight();
    }
    
    final void cleanup() {
      if (this.mAutoTimeChangeReceiver != null) {
        AppCompatDelegateImplV14.this.mContext.unregisterReceiver(this.mAutoTimeChangeReceiver);
        this.mAutoTimeChangeReceiver = null;
      } 
    }
    
    final void dispatchTimeChanged() {
      boolean bool = this.mTwilightManager.isNight();
      if (bool != this.mIsNight) {
        this.mIsNight = bool;
        AppCompatDelegateImplV14.this.applyDayNight();
      } 
    }
    
    final int getApplyableNightMode() {
      boolean bool1;
      boolean bool = this.mTwilightManager.isNight();
      this.mIsNight = bool;
      if (bool) {
        bool1 = true;
      } else {
        bool1 = true;
      } 
      return bool1;
    }
    
    final void setup() {
      cleanup();
      if (this.mAutoTimeChangeReceiver == null)
        this.mAutoTimeChangeReceiver = new BroadcastReceiver() {
            public void onReceive(Context param2Context, Intent param2Intent) {
              AppCompatDelegateImplV14.AutoNightModeManager.this.dispatchTimeChanged();
            }
          }; 
      if (this.mAutoTimeChangeReceiverFilter == null) {
        IntentFilter intentFilter = new IntentFilter();
        this.mAutoTimeChangeReceiverFilter = intentFilter;
        intentFilter.addAction("android.intent.action.TIME_SET");
        this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIME_TICK");
      } 
      AppCompatDelegateImplV14.this.mContext.registerReceiver(this.mAutoTimeChangeReceiver, this.mAutoTimeChangeReceiverFilter);
    }
  }
  
  class null extends BroadcastReceiver {
    public void onReceive(Context param1Context, Intent param1Intent) {
      this.this$1.dispatchTimeChanged();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\app\AppCompatDelegateImplV14.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */