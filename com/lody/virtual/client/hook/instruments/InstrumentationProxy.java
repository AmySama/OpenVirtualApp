package com.lody.virtual.client.hook.instruments;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.Instrumentation;
import android.app.UiAutomation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.lody.virtual.helper.MultiAvoidRecursive;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class InstrumentationProxy extends Instrumentation {
  private MultiAvoidRecursive avoidRecursive = new MultiAvoidRecursive(20);
  
  protected Instrumentation base;
  
  protected Instrumentation root;
  
  public InstrumentationProxy(Instrumentation paramInstrumentation) {
    this.base = paramInstrumentation;
    this.root = paramInstrumentation;
  }
  
  private static Method findDeclaredMethod(Object paramObject, String paramString, Class<?>... paramVarArgs) throws NoSuchMethodException {
    Class<?> clazz = paramObject.getClass();
    while (clazz != null) {
      try {
        Method method = clazz.getDeclaredMethod(paramString, paramVarArgs);
        if (!method.isAccessible())
          method.setAccessible(true); 
        return method;
      } catch (NoSuchMethodException noSuchMethodException) {
        clazz = clazz.getSuperclass();
      } 
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Method ");
    stringBuilder.append(paramString);
    stringBuilder.append(" with parameters ");
    stringBuilder.append(Arrays.asList(paramVarArgs));
    stringBuilder.append(" not found in ");
    stringBuilder.append(paramObject.getClass());
    throw new NoSuchMethodException(stringBuilder.toString());
  }
  
  public Instrumentation.ActivityMonitor addMonitor(IntentFilter paramIntentFilter, Instrumentation.ActivityResult paramActivityResult, boolean paramBoolean) {
    return this.base.addMonitor(paramIntentFilter, paramActivityResult, paramBoolean);
  }
  
  public Instrumentation.ActivityMonitor addMonitor(String paramString, Instrumentation.ActivityResult paramActivityResult, boolean paramBoolean) {
    return this.base.addMonitor(paramString, paramActivityResult, paramBoolean);
  }
  
  public void addMonitor(Instrumentation.ActivityMonitor paramActivityMonitor) {
    this.base.addMonitor(paramActivityMonitor);
  }
  
  public void callActivityOnCreate(Activity paramActivity, Bundle paramBundle) {
    try {
      if (this.avoidRecursive.beginCall(4)) {
        this.base.callActivityOnCreate(paramActivity, paramBundle);
      } else {
        this.root.callActivityOnCreate(paramActivity, paramBundle);
      } 
      return;
    } finally {
      this.avoidRecursive.finishCall(4);
    } 
  }
  
  public void callActivityOnCreate(Activity paramActivity, Bundle paramBundle, PersistableBundle paramPersistableBundle) {
    try {
      if (this.avoidRecursive.beginCall(5)) {
        this.base.callActivityOnCreate(paramActivity, paramBundle, paramPersistableBundle);
      } else {
        this.root.callActivityOnCreate(paramActivity, paramBundle, paramPersistableBundle);
      } 
      return;
    } finally {
      this.avoidRecursive.finishCall(5);
    } 
  }
  
  public void callActivityOnDestroy(Activity paramActivity) {
    try {
      if (this.avoidRecursive.beginCall(6)) {
        this.base.callActivityOnDestroy(paramActivity);
      } else {
        this.root.callActivityOnDestroy(paramActivity);
      } 
      return;
    } finally {
      this.avoidRecursive.finishCall(6);
    } 
  }
  
  public void callActivityOnNewIntent(Activity paramActivity, Intent paramIntent) {
    try {
      if (this.avoidRecursive.beginCall(11)) {
        this.base.callActivityOnNewIntent(paramActivity, paramIntent);
      } else {
        this.root.callActivityOnNewIntent(paramActivity, paramIntent);
      } 
      return;
    } finally {
      this.avoidRecursive.finishCall(11);
    } 
  }
  
  public void callActivityOnPause(Activity paramActivity) {
    try {
      if (this.avoidRecursive.beginCall(18)) {
        this.base.callActivityOnPause(paramActivity);
      } else {
        this.root.callActivityOnPause(paramActivity);
      } 
      return;
    } finally {
      this.avoidRecursive.finishCall(18);
    } 
  }
  
  public void callActivityOnPictureInPictureRequested(Activity paramActivity) {
    this.base.callActivityOnPictureInPictureRequested(paramActivity);
  }
  
  public void callActivityOnPostCreate(Activity paramActivity, Bundle paramBundle) {
    try {
      if (this.avoidRecursive.beginCall(9)) {
        this.base.callActivityOnPostCreate(paramActivity, paramBundle);
      } else {
        this.root.callActivityOnPostCreate(paramActivity, paramBundle);
      } 
      return;
    } finally {
      this.avoidRecursive.finishCall(9);
    } 
  }
  
  public void callActivityOnPostCreate(Activity paramActivity, Bundle paramBundle, PersistableBundle paramPersistableBundle) {
    try {
      if (this.avoidRecursive.beginCall(10)) {
        this.base.callActivityOnPostCreate(paramActivity, paramBundle, paramPersistableBundle);
      } else {
        this.root.callActivityOnPostCreate(paramActivity, paramBundle, paramPersistableBundle);
      } 
      return;
    } finally {
      this.avoidRecursive.finishCall(10);
    } 
  }
  
  public void callActivityOnRestart(Activity paramActivity) {
    try {
      if (this.avoidRecursive.beginCall(13)) {
        this.base.callActivityOnRestart(paramActivity);
      } else {
        this.root.callActivityOnRestart(paramActivity);
      } 
      return;
    } finally {
      this.avoidRecursive.finishCall(13);
    } 
  }
  
  public void callActivityOnRestoreInstanceState(Activity paramActivity, Bundle paramBundle) {
    try {
      if (this.avoidRecursive.beginCall(7)) {
        this.base.callActivityOnRestoreInstanceState(paramActivity, paramBundle);
      } else {
        this.root.callActivityOnRestoreInstanceState(paramActivity, paramBundle);
      } 
      return;
    } finally {
      this.avoidRecursive.finishCall(7);
    } 
  }
  
  public void callActivityOnRestoreInstanceState(Activity paramActivity, Bundle paramBundle, PersistableBundle paramPersistableBundle) {
    try {
      if (this.avoidRecursive.beginCall(8)) {
        this.base.callActivityOnRestoreInstanceState(paramActivity, paramBundle, paramPersistableBundle);
      } else {
        this.root.callActivityOnRestoreInstanceState(paramActivity, paramBundle, paramPersistableBundle);
      } 
      return;
    } finally {
      this.avoidRecursive.finishCall(8);
    } 
  }
  
  public void callActivityOnResume(Activity paramActivity) {
    try {
      if (this.avoidRecursive.beginCall(14)) {
        this.base.callActivityOnResume(paramActivity);
      } else {
        this.root.callActivityOnResume(paramActivity);
      } 
      return;
    } finally {
      this.avoidRecursive.finishCall(14);
    } 
  }
  
  public void callActivityOnSaveInstanceState(Activity paramActivity, Bundle paramBundle) {
    try {
      if (this.avoidRecursive.beginCall(16)) {
        this.base.callActivityOnSaveInstanceState(paramActivity, paramBundle);
      } else {
        this.root.callActivityOnSaveInstanceState(paramActivity, paramBundle);
      } 
      return;
    } finally {
      this.avoidRecursive.finishCall(16);
    } 
  }
  
  public void callActivityOnSaveInstanceState(Activity paramActivity, Bundle paramBundle, PersistableBundle paramPersistableBundle) {
    try {
      if (this.avoidRecursive.beginCall(17)) {
        this.base.callActivityOnSaveInstanceState(paramActivity, paramBundle, paramPersistableBundle);
      } else {
        this.root.callActivityOnSaveInstanceState(paramActivity, paramBundle, paramPersistableBundle);
      } 
      return;
    } finally {
      this.avoidRecursive.finishCall(17);
    } 
  }
  
  public void callActivityOnStart(Activity paramActivity) {
    try {
      if (this.avoidRecursive.beginCall(12)) {
        this.base.callActivityOnStart(paramActivity);
      } else {
        this.root.callActivityOnStart(paramActivity);
      } 
      return;
    } finally {
      this.avoidRecursive.finishCall(12);
    } 
  }
  
  public void callActivityOnStop(Activity paramActivity) {
    try {
      if (this.avoidRecursive.beginCall(15)) {
        this.base.callActivityOnStop(paramActivity);
      } else {
        this.root.callActivityOnStop(paramActivity);
      } 
      return;
    } finally {
      this.avoidRecursive.finishCall(15);
    } 
  }
  
  public void callActivityOnUserLeaving(Activity paramActivity) {
    try {
      if (this.avoidRecursive.beginCall(19)) {
        this.base.callActivityOnUserLeaving(paramActivity);
      } else {
        this.root.callActivityOnUserLeaving(paramActivity);
      } 
      return;
    } finally {
      this.avoidRecursive.finishCall(19);
    } 
  }
  
  public void callApplicationOnCreate(Application paramApplication) {
    try {
    
    } finally {
      paramApplication = null;
    } 
    this.avoidRecursive.finishCall(1);
  }
  
  public boolean checkMonitorHit(Instrumentation.ActivityMonitor paramActivityMonitor, int paramInt) {
    return this.base.checkMonitorHit(paramActivityMonitor, paramInt);
  }
  
  public void endPerformanceSnapshot() {
    this.base.endPerformanceSnapshot();
  }
  
  public Instrumentation.ActivityResult execStartActivity(Context paramContext, IBinder paramIBinder1, IBinder paramIBinder2, Activity paramActivity, Intent paramIntent, int paramInt) throws Throwable {
    try {
      boolean bool = this.avoidRecursive.beginCall(23);
      if (bool) {
        activityResult = (Instrumentation.ActivityResult)findDeclaredMethod(this.base, "execStartActivity", new Class[] { Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class }).invoke(this.base, new Object[] { paramContext, paramIBinder1, paramIBinder2, paramActivity, paramIntent, Integer.valueOf(paramInt) });
        this.avoidRecursive.finishCall(23);
        return activityResult;
      } 
      Instrumentation.ActivityResult activityResult = (Instrumentation.ActivityResult)findDeclaredMethod(this.root, "execStartActivity", new Class[] { Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class }).invoke(this.root, new Object[] { activityResult, paramIBinder1, paramIBinder2, paramActivity, paramIntent, Integer.valueOf(paramInt) });
      this.avoidRecursive.finishCall(23);
      return activityResult;
    } catch (InvocationTargetException invocationTargetException) {
    
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {}
    this.avoidRecursive.finishCall(23);
    return null;
  }
  
  public Instrumentation.ActivityResult execStartActivity(Context paramContext, IBinder paramIBinder1, IBinder paramIBinder2, Activity paramActivity, Intent paramIntent, int paramInt, Bundle paramBundle) throws Throwable {
    try {
      boolean bool = this.avoidRecursive.beginCall(20);
      if (bool) {
        activityResult = (Instrumentation.ActivityResult)findDeclaredMethod(this.base, "execStartActivity", new Class[] { Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class }).invoke(this.base, new Object[] { paramContext, paramIBinder1, paramIBinder2, paramActivity, paramIntent, Integer.valueOf(paramInt), paramBundle });
        this.avoidRecursive.finishCall(20);
        return activityResult;
      } 
      Instrumentation.ActivityResult activityResult = (Instrumentation.ActivityResult)findDeclaredMethod(this.root, "execStartActivity", new Class[] { Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class }).invoke(this.root, new Object[] { activityResult, paramIBinder1, paramIBinder2, paramActivity, paramIntent, Integer.valueOf(paramInt), paramBundle });
      this.avoidRecursive.finishCall(20);
      return activityResult;
    } catch (InvocationTargetException invocationTargetException) {
    
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {}
    this.avoidRecursive.finishCall(20);
    return null;
  }
  
  public Instrumentation.ActivityResult execStartActivity(Context paramContext, IBinder paramIBinder1, IBinder paramIBinder2, Activity paramActivity, Intent paramIntent, int paramInt, Bundle paramBundle, UserHandle paramUserHandle) throws Throwable {
    try {
      boolean bool = this.avoidRecursive.beginCall(25);
      if (bool) {
        activityResult = (Instrumentation.ActivityResult)findDeclaredMethod(this.base, "execStartActivity", new Class[] { Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class, UserHandle.class }).invoke(this.base, new Object[] { paramContext, paramIBinder1, paramIBinder2, paramActivity, paramIntent, Integer.valueOf(paramInt), paramBundle, paramUserHandle });
        this.avoidRecursive.finishCall(25);
        return activityResult;
      } 
      Instrumentation.ActivityResult activityResult = (Instrumentation.ActivityResult)findDeclaredMethod(this.root, "execStartActivity", new Class[] { Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class, UserHandle.class }).invoke(this.root, new Object[] { activityResult, paramIBinder1, paramIBinder2, paramActivity, paramIntent, Integer.valueOf(paramInt), paramBundle, paramUserHandle });
      this.avoidRecursive.finishCall(25);
      return activityResult;
    } catch (InvocationTargetException invocationTargetException) {
    
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {}
    this.avoidRecursive.finishCall(25);
    return null;
  }
  
  public Instrumentation.ActivityResult execStartActivity(Context paramContext, IBinder paramIBinder1, IBinder paramIBinder2, Fragment paramFragment, Intent paramIntent, int paramInt) throws Throwable {
    try {
      boolean bool = this.avoidRecursive.beginCall(22);
      if (bool) {
        activityResult = (Instrumentation.ActivityResult)findDeclaredMethod(this.base, "execStartActivity", new Class[] { Context.class, IBinder.class, IBinder.class, Fragment.class, Intent.class, int.class }).invoke(this.base, new Object[] { paramContext, paramIBinder1, paramIBinder2, paramFragment, paramIntent, Integer.valueOf(paramInt) });
        this.avoidRecursive.finishCall(22);
        return activityResult;
      } 
      Instrumentation.ActivityResult activityResult = (Instrumentation.ActivityResult)findDeclaredMethod(this.root, "execStartActivity", new Class[] { Context.class, IBinder.class, IBinder.class, Fragment.class, Intent.class, int.class }).invoke(this.root, new Object[] { activityResult, paramIBinder1, paramIBinder2, paramFragment, paramIntent, Integer.valueOf(paramInt) });
      this.avoidRecursive.finishCall(22);
      return activityResult;
    } catch (InvocationTargetException invocationTargetException) {
    
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {}
    this.avoidRecursive.finishCall(22);
    return null;
  }
  
  public Instrumentation.ActivityResult execStartActivity(Context paramContext, IBinder paramIBinder1, IBinder paramIBinder2, Fragment paramFragment, Intent paramIntent, int paramInt, Bundle paramBundle) throws Throwable {
    try {
      boolean bool = this.avoidRecursive.beginCall(24);
      if (bool) {
        activityResult = (Instrumentation.ActivityResult)findDeclaredMethod(this.base, "execStartActivity", new Class[] { Context.class, IBinder.class, IBinder.class, Fragment.class, Intent.class, int.class, Bundle.class }).invoke(this.base, new Object[] { paramContext, paramIBinder1, paramIBinder2, paramFragment, paramIntent, Integer.valueOf(paramInt), paramBundle });
        this.avoidRecursive.finishCall(24);
        return activityResult;
      } 
      Instrumentation.ActivityResult activityResult = (Instrumentation.ActivityResult)findDeclaredMethod(this.root, "execStartActivity", new Class[] { Context.class, IBinder.class, IBinder.class, Fragment.class, Intent.class, int.class, Bundle.class }).invoke(this.root, new Object[] { activityResult, paramIBinder1, paramIBinder2, paramFragment, paramIntent, Integer.valueOf(paramInt), paramBundle });
      this.avoidRecursive.finishCall(24);
      return activityResult;
    } catch (InvocationTargetException invocationTargetException) {
    
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {}
    this.avoidRecursive.finishCall(24);
    return null;
  }
  
  public Instrumentation.ActivityResult execStartActivity(Context paramContext, IBinder paramIBinder1, IBinder paramIBinder2, String paramString, Intent paramIntent, int paramInt, Bundle paramBundle) throws Throwable {
    try {
      boolean bool = this.avoidRecursive.beginCall(21);
      if (bool) {
        activityResult = (Instrumentation.ActivityResult)findDeclaredMethod(this.base, "execStartActivity", new Class[] { Context.class, IBinder.class, IBinder.class, String.class, Intent.class, int.class, Bundle.class }).invoke(this.base, new Object[] { paramContext, paramIBinder1, paramIBinder2, paramString, paramIntent, Integer.valueOf(paramInt), paramBundle });
        this.avoidRecursive.finishCall(21);
        return activityResult;
      } 
      Instrumentation.ActivityResult activityResult = (Instrumentation.ActivityResult)findDeclaredMethod(this.root, "execStartActivity", new Class[] { Context.class, IBinder.class, IBinder.class, String.class, Intent.class, int.class, Bundle.class }).invoke(this.root, new Object[] { activityResult, paramIBinder1, paramIBinder2, paramString, paramIntent, Integer.valueOf(paramInt), paramBundle });
      this.avoidRecursive.finishCall(21);
      return activityResult;
    } catch (InvocationTargetException invocationTargetException) {
    
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {}
    this.avoidRecursive.finishCall(21);
    return null;
  }
  
  public void finish(int paramInt, Bundle paramBundle) {
    this.base.finish(paramInt, paramBundle);
  }
  
  public Bundle getAllocCounts() {
    return this.base.getAllocCounts();
  }
  
  public Bundle getBinderCounts() {
    return this.base.getBinderCounts();
  }
  
  public ComponentName getComponentName() {
    return this.base.getComponentName();
  }
  
  public Context getContext() {
    return this.base.getContext();
  }
  
  public Context getTargetContext() {
    return this.base.getTargetContext();
  }
  
  public UiAutomation getUiAutomation() {
    return this.base.getUiAutomation();
  }
  
  public boolean invokeContextMenuAction(Activity paramActivity, int paramInt1, int paramInt2) {
    return this.base.invokeContextMenuAction(paramActivity, paramInt1, paramInt2);
  }
  
  public boolean invokeMenuActionSync(Activity paramActivity, int paramInt1, int paramInt2) {
    return this.base.invokeMenuActionSync(paramActivity, paramInt1, paramInt2);
  }
  
  public boolean isProfiling() {
    return this.base.isProfiling();
  }
  
  public Activity newActivity(Class<?> paramClass, Context paramContext, IBinder paramIBinder, Application paramApplication, Intent paramIntent, ActivityInfo paramActivityInfo, CharSequence paramCharSequence, Activity paramActivity, String paramString, Object paramObject) throws InstantiationException, IllegalAccessException {
    try {
      if (this.avoidRecursive.beginCall(2)) {
        activity = this.base.newActivity(paramClass, paramContext, paramIBinder, paramApplication, paramIntent, paramActivityInfo, paramCharSequence, paramActivity, paramString, paramObject);
        return activity;
      } 
      Activity activity = this.root.newActivity((Class)activity, paramContext, paramIBinder, paramApplication, paramIntent, paramActivityInfo, paramCharSequence, paramActivity, paramString, paramObject);
      return activity;
    } finally {
      this.avoidRecursive.finishCall(2);
    } 
  }
  
  public Activity newActivity(ClassLoader paramClassLoader, String paramString, Intent paramIntent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    try {
      if (this.avoidRecursive.beginCall(3)) {
        activity = this.base.newActivity(paramClassLoader, paramString, paramIntent);
        return activity;
      } 
      Activity activity = this.root.newActivity((ClassLoader)activity, paramString, paramIntent);
      return activity;
    } finally {
      this.avoidRecursive.finishCall(3);
    } 
  }
  
  public Application newApplication(ClassLoader paramClassLoader, String paramString, Context paramContext) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    try {
      if (this.avoidRecursive.beginCall(0)) {
        application = this.base.newApplication(paramClassLoader, paramString, paramContext);
        return application;
      } 
      Application application = this.root.newApplication((ClassLoader)application, paramString, paramContext);
      return application;
    } finally {
      this.avoidRecursive.finishCall(0);
    } 
  }
  
  public void onCreate(Bundle paramBundle) {
    this.base.onCreate(paramBundle);
  }
  
  public void onDestroy() {
    this.base.onDestroy();
  }
  
  public boolean onException(Object paramObject, Throwable paramThrowable) {
    return this.base.onException(paramObject, paramThrowable);
  }
  
  public void onStart() {
    this.base.onStart();
  }
  
  public void removeMonitor(Instrumentation.ActivityMonitor paramActivityMonitor) {
    this.base.removeMonitor(paramActivityMonitor);
  }
  
  public void runOnMainSync(Runnable paramRunnable) {
    this.base.runOnMainSync(paramRunnable);
  }
  
  public void sendCharacterSync(int paramInt) {
    this.base.sendCharacterSync(paramInt);
  }
  
  public void sendKeyDownUpSync(int paramInt) {
    this.base.sendKeyDownUpSync(paramInt);
  }
  
  public void sendKeySync(KeyEvent paramKeyEvent) {
    this.base.sendKeySync(paramKeyEvent);
  }
  
  public void sendPointerSync(MotionEvent paramMotionEvent) {
    this.base.sendPointerSync(paramMotionEvent);
  }
  
  public void sendStatus(int paramInt, Bundle paramBundle) {
    this.base.sendStatus(paramInt, paramBundle);
  }
  
  public void sendStringSync(String paramString) {
    this.base.sendStringSync(paramString);
  }
  
  public void sendTrackballEventSync(MotionEvent paramMotionEvent) {
    this.base.sendTrackballEventSync(paramMotionEvent);
  }
  
  public void setAutomaticPerformanceSnapshots() {
    this.base.setAutomaticPerformanceSnapshots();
  }
  
  public void setInTouchMode(boolean paramBoolean) {
    this.base.setInTouchMode(paramBoolean);
  }
  
  public void start() {
    this.base.start();
  }
  
  public Activity startActivitySync(Intent paramIntent) {
    return this.base.startActivitySync(paramIntent);
  }
  
  public void startPerformanceSnapshot() {
    this.base.startPerformanceSnapshot();
  }
  
  public void startProfiling() {
    this.base.startProfiling();
  }
  
  public void stopProfiling() {
    this.base.stopProfiling();
  }
  
  public void waitForIdle(Runnable paramRunnable) {
    this.base.waitForIdle(paramRunnable);
  }
  
  public void waitForIdleSync() {
    this.base.waitForIdleSync();
  }
  
  public Activity waitForMonitor(Instrumentation.ActivityMonitor paramActivityMonitor) {
    return this.base.waitForMonitor(paramActivityMonitor);
  }
  
  public Activity waitForMonitorWithTimeout(Instrumentation.ActivityMonitor paramActivityMonitor, long paramLong) {
    return this.base.waitForMonitorWithTimeout(paramActivityMonitor, paramLong);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\instruments\InstrumentationProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */