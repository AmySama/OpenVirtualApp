package com.lody.virtual.client.hook.instruments;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import com.lody.virtual.client.core.AppCallback;
import com.lody.virtual.client.core.InvocationStubManager;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.fixer.ActivityFixer;
import com.lody.virtual.client.fixer.ContextFixer;
import com.lody.virtual.client.hook.proxies.am.HCallbackStub;
import com.lody.virtual.client.interfaces.IInjector;
import mirror.android.app.Activity;
import mirror.android.app.ActivityThread;

public class InstrumentationVirtualApp extends InstrumentationProxy implements IInjector {
  private static final String TAG = InstrumentationVirtualApp.class.getSimpleName();
  
  private static InstrumentationVirtualApp gDefault;
  
  public InstrumentationVirtualApp(Instrumentation paramInstrumentation) {
    super(paramInstrumentation);
  }
  
  private void checkActivityCallback() {
    InvocationStubManager.getInstance().checkEnv(HCallbackStub.class);
    InvocationStubManager.getInstance().checkEnv(InstrumentationVirtualApp.class);
  }
  
  private static InstrumentationVirtualApp create() {
    Instrumentation instrumentation = (Instrumentation)ActivityThread.mInstrumentation.get(VirtualCore.mainThread());
    return (instrumentation instanceof InstrumentationVirtualApp) ? (InstrumentationVirtualApp)instrumentation : new InstrumentationVirtualApp(instrumentation);
  }
  
  private void dynamicResolveConflict() {
    try {
    
    } finally {
      Exception exception = null;
    } 
  }
  
  private AppCallback getAppCallback() {
    return VirtualCore.get().getAppCallback();
  }
  
  public static InstrumentationVirtualApp getDefault() {
    // Byte code:
    //   0: getstatic com/lody/virtual/client/hook/instruments/InstrumentationVirtualApp.gDefault : Lcom/lody/virtual/client/hook/instruments/InstrumentationVirtualApp;
    //   3: ifnonnull -> 33
    //   6: ldc com/lody/virtual/client/hook/instruments/InstrumentationVirtualApp
    //   8: monitorenter
    //   9: getstatic com/lody/virtual/client/hook/instruments/InstrumentationVirtualApp.gDefault : Lcom/lody/virtual/client/hook/instruments/InstrumentationVirtualApp;
    //   12: ifnonnull -> 21
    //   15: invokestatic create : ()Lcom/lody/virtual/client/hook/instruments/InstrumentationVirtualApp;
    //   18: putstatic com/lody/virtual/client/hook/instruments/InstrumentationVirtualApp.gDefault : Lcom/lody/virtual/client/hook/instruments/InstrumentationVirtualApp;
    //   21: ldc com/lody/virtual/client/hook/instruments/InstrumentationVirtualApp
    //   23: monitorexit
    //   24: goto -> 33
    //   27: astore_0
    //   28: ldc com/lody/virtual/client/hook/instruments/InstrumentationVirtualApp
    //   30: monitorexit
    //   31: aload_0
    //   32: athrow
    //   33: getstatic com/lody/virtual/client/hook/instruments/InstrumentationVirtualApp.gDefault : Lcom/lody/virtual/client/hook/instruments/InstrumentationVirtualApp;
    //   36: areturn
    // Exception table:
    //   from	to	target	type
    //   9	21	27	finally
    //   21	24	27	finally
    //   28	31	27	finally
  }
  
  public void callActivityOnCreate(Activity paramActivity, Bundle paramBundle) {
    checkActivityCallback();
    ActivityInfo activityInfo = (ActivityInfo)Activity.mActivityInfo.get(paramActivity);
    if (activityInfo != null) {
      String str = activityInfo.packageName;
    } else {
      activityInfo = null;
    } 
    ContextFixer.fixContext((Context)paramActivity, (String)activityInfo);
    ActivityFixer.fixActivity(paramActivity);
    AppCallback appCallback = getAppCallback();
    appCallback.beforeActivityOnCreate(paramActivity);
    super.callActivityOnCreate(paramActivity, paramBundle);
    appCallback.afterActivityOnCreate(paramActivity);
  }
  
  public void callActivityOnCreate(Activity paramActivity, Bundle paramBundle, PersistableBundle paramPersistableBundle) {
    checkActivityCallback();
    ActivityInfo activityInfo = (ActivityInfo)Activity.mActivityInfo.get(paramActivity);
    if (activityInfo != null) {
      String str = activityInfo.packageName;
    } else {
      activityInfo = null;
    } 
    ContextFixer.fixContext((Context)paramActivity, (String)activityInfo);
    ActivityFixer.fixActivity(paramActivity);
    AppCallback appCallback = getAppCallback();
    appCallback.beforeActivityOnCreate(paramActivity);
    super.callActivityOnCreate(paramActivity, paramBundle, paramPersistableBundle);
    appCallback.afterActivityOnCreate(paramActivity);
  }
  
  public void callActivityOnDestroy(Activity paramActivity) {
    AppCallback appCallback = getAppCallback();
    appCallback.beforeActivityOnDestroy(paramActivity);
    super.callActivityOnDestroy(paramActivity);
    appCallback.afterActivityOnDestroy(paramActivity);
  }
  
  public void callActivityOnResume(Activity paramActivity) {
    AppCallback appCallback = getAppCallback();
    appCallback.beforeActivityOnResume(paramActivity);
    super.callActivityOnResume(paramActivity);
    appCallback.afterActivityOnResume(paramActivity);
  }
  
  public void callActivityOnStart(Activity paramActivity) {
    AppCallback appCallback = getAppCallback();
    appCallback.beforeActivityOnStart(paramActivity);
    super.callActivityOnStart(paramActivity);
    if (!VirtualCore.getConfig().disableSetScreenOrientation(paramActivity.getPackageName())) {
      ActivityInfo activityInfo = (ActivityInfo)Activity.mActivityInfo.get(paramActivity);
      if (activityInfo != null && activityInfo.screenOrientation != -1 && paramActivity.getRequestedOrientation() == -1)
        paramActivity.setRequestedOrientation(activityInfo.screenOrientation); 
    } 
    appCallback.afterActivityOnStart(paramActivity);
  }
  
  public void callActivityOnStop(Activity paramActivity) {
    AppCallback appCallback = getAppCallback();
    appCallback.beforeActivityOnStop(paramActivity);
    super.callActivityOnStop(paramActivity);
    appCallback.afterActivityOnStop(paramActivity);
  }
  
  public void callApplicationOnCreate(Application paramApplication) {
    checkActivityCallback();
    super.callApplicationOnCreate(paramApplication);
  }
  
  public Instrumentation getBaseInstrumentation() {
    return this.base;
  }
  
  public void inject() throws Throwable {
    Instrumentation instrumentation = (Instrumentation)ActivityThread.mInstrumentation.get(VirtualCore.mainThread());
    if (this.base == null)
      this.base = instrumentation; 
    if (instrumentation != this.base) {
      this.root = this.base;
      this.base = instrumentation;
      dynamicResolveConflict();
    } 
    ActivityThread.mInstrumentation.set(VirtualCore.mainThread(), this);
  }
  
  public boolean isEnvBad() {
    return (Instrumentation)ActivityThread.mInstrumentation.get(VirtualCore.mainThread()) instanceof InstrumentationVirtualApp ^ true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\instruments\InstrumentationVirtualApp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */