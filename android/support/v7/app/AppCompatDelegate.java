package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class AppCompatDelegate {
  public static final int FEATURE_ACTION_MODE_OVERLAY = 10;
  
  public static final int FEATURE_SUPPORT_ACTION_BAR = 108;
  
  public static final int FEATURE_SUPPORT_ACTION_BAR_OVERLAY = 109;
  
  public static final int MODE_NIGHT_AUTO = 0;
  
  public static final int MODE_NIGHT_FOLLOW_SYSTEM = -1;
  
  public static final int MODE_NIGHT_NO = 1;
  
  static final int MODE_NIGHT_UNSPECIFIED = -100;
  
  public static final int MODE_NIGHT_YES = 2;
  
  static final String TAG = "AppCompatDelegate";
  
  private static boolean sCompatVectorFromResourcesEnabled = false;
  
  private static int sDefaultNightMode = -1;
  
  public static AppCompatDelegate create(Activity paramActivity, AppCompatCallback paramAppCompatCallback) {
    return create((Context)paramActivity, paramActivity.getWindow(), paramAppCompatCallback);
  }
  
  public static AppCompatDelegate create(Dialog paramDialog, AppCompatCallback paramAppCompatCallback) {
    return create(paramDialog.getContext(), paramDialog.getWindow(), paramAppCompatCallback);
  }
  
  private static AppCompatDelegate create(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback) {
    return (Build.VERSION.SDK_INT >= 24) ? new AppCompatDelegateImplN(paramContext, paramWindow, paramAppCompatCallback) : ((Build.VERSION.SDK_INT >= 23) ? new AppCompatDelegateImplV23(paramContext, paramWindow, paramAppCompatCallback) : new AppCompatDelegateImplV14(paramContext, paramWindow, paramAppCompatCallback));
  }
  
  public static int getDefaultNightMode() {
    return sDefaultNightMode;
  }
  
  public static boolean isCompatVectorFromResourcesEnabled() {
    return sCompatVectorFromResourcesEnabled;
  }
  
  public static void setCompatVectorFromResourcesEnabled(boolean paramBoolean) {
    sCompatVectorFromResourcesEnabled = paramBoolean;
  }
  
  public static void setDefaultNightMode(int paramInt) {
    if (paramInt != -1 && paramInt != 0 && paramInt != 1 && paramInt != 2) {
      Log.d("AppCompatDelegate", "setDefaultNightMode() called with an unknown mode");
    } else {
      sDefaultNightMode = paramInt;
    } 
  }
  
  public abstract void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams);
  
  public abstract boolean applyDayNight();
  
  public abstract View createView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet);
  
  public abstract <T extends View> T findViewById(int paramInt);
  
  public abstract ActionBarDrawerToggle.Delegate getDrawerToggleDelegate();
  
  public abstract MenuInflater getMenuInflater();
  
  public abstract ActionBar getSupportActionBar();
  
  public abstract boolean hasWindowFeature(int paramInt);
  
  public abstract void installViewFactory();
  
  public abstract void invalidateOptionsMenu();
  
  public abstract boolean isHandleNativeActionModesEnabled();
  
  public abstract void onConfigurationChanged(Configuration paramConfiguration);
  
  public abstract void onCreate(Bundle paramBundle);
  
  public abstract void onDestroy();
  
  public abstract void onPostCreate(Bundle paramBundle);
  
  public abstract void onPostResume();
  
  public abstract void onSaveInstanceState(Bundle paramBundle);
  
  public abstract void onStart();
  
  public abstract void onStop();
  
  public abstract boolean requestWindowFeature(int paramInt);
  
  public abstract void setContentView(int paramInt);
  
  public abstract void setContentView(View paramView);
  
  public abstract void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams);
  
  public abstract void setHandleNativeActionModesEnabled(boolean paramBoolean);
  
  public abstract void setLocalNightMode(int paramInt);
  
  public abstract void setSupportActionBar(Toolbar paramToolbar);
  
  public abstract void setTitle(CharSequence paramCharSequence);
  
  public abstract ActionMode startSupportActionMode(ActionMode.Callback paramCallback);
  
  @Retention(RetentionPolicy.SOURCE)
  static @interface ApplyableNightMode {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface NightMode {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\app\AppCompatDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */