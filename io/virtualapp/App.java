package io.virtualapp;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.lody.virtual.client.core.AppCallback;
import com.lody.virtual.client.core.SettingConfig;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.delegate.TaskDescriptionDelegate;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.VLog;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.delegate.MyAppRequestListener;
import io.virtualapp.delegate.MyComponentDelegate;
import io.virtualapp.delegate.MyTaskDescDelegate;
import io.virtualapp.home.BackHomeActivity;
import jonathanfinerty.once.Once;

public class App extends Application {
  private static App gApp;
  
  private SettingConfig mConfig = new SettingConfig() {
      public String getExtPackageName() {
        return "com.smallyin.moreopen.no";
      }
      
      public String getMainPackageName() {
        return "com.smallyin.moreopen";
      }
      
      public boolean isAllowCreateShortcut() {
        return false;
      }
      
      public boolean isEnableIORedirect() {
        return true;
      }
      
      public boolean isEnableVirtualSdcardAndroidData() {
        return BuildCompat.isR();
      }
      
      public boolean isHostIntent(Intent param1Intent) {
        boolean bool;
        if (param1Intent.getData() != null && "market".equals(param1Intent.getData().getScheme())) {
          bool = true;
        } else {
          bool = false;
        } 
        return bool;
      }
      
      public boolean isOutsidePackage(String param1String) {
        return false;
      }
      
      public boolean isUseRealApkPath(String param1String) {
        return param1String.equals("com.seeyon.cmp");
      }
      
      public boolean isUseRealDataDir(String param1String) {
        return false;
      }
      
      public Intent onHandleLauncherIntent(Intent param1Intent) {
        param1Intent = new Intent();
        param1Intent.setComponent(new ComponentName(getMainPackageName(), BackHomeActivity.class.getName()));
        param1Intent.addFlags(268435456);
        return param1Intent;
      }
    };
  
  private BroadcastReceiver mGmsInitializeReceiver = new BroadcastReceiver() {
      public void onReceive(Context param1Context, Intent param1Intent) {
        View view = LayoutInflater.from(param1Context).inflate(2131427403, null);
        Toast toast = new Toast(param1Context);
        toast.setGravity(81, 0, 0);
        toast.setDuration(0);
        toast.setView(view);
        try {
          toast.show();
        } finally {
          toast = null;
        } 
      }
    };
  
  private static int dp2px(Context paramContext, float paramFloat) {
    return (int)(paramFloat * (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  public static App getApp() {
    return gApp;
  }
  
  private static int sp2px(Context paramContext, float paramFloat) {
    return (int)(paramFloat * (paramContext.getResources().getDisplayMetrics()).scaledDensity + 0.5F);
  }
  
  protected void attachBaseContext(Context paramContext) {
    super.attachBaseContext(paramContext);
    VLog.OPEN_LOG = true;
    try {
      VirtualCore.get().startup(paramContext, this.mConfig);
    } finally {
      paramContext = null;
    } 
  }
  
  public void onCreate() {
    gApp = this;
    super.onCreate();
    ((Boolean)SPUtils.get((Context)this, "is_frist_lucher_app", Boolean.valueOf(false))).booleanValue();
    final VirtualCore virtualCore = VirtualCore.get();
    virtualCore.initialize(new VirtualCore.VirtualInitializer() {
          public void onMainProcess() {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
            Once.initialise((Context)App.this);
            App app = App.this;
            app.registerReceiver(app.mGmsInitializeReceiver, new IntentFilter("android.intent.action.GMS_INITIALIZED"));
          }
          
          public void onServerProcess() {}
          
          public void onVirtualProcess() {
            virtualCore.setAppCallback((AppCallback)new MyComponentDelegate());
            virtualCore.setTaskDescriptionDelegate((TaskDescriptionDelegate)new MyTaskDescDelegate());
            virtualCore.setAppRequestListener((VirtualCore.AppRequestListener)new MyAppRequestListener((Context)App.this));
          }
        });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\App.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */