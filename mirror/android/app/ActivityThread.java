package mirror.android.app;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.ProviderInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import mirror.MethodParams;
import mirror.MethodReflectParams;
import mirror.RefClass;
import mirror.RefConstructor;
import mirror.RefMethod;
import mirror.RefObject;
import mirror.RefStaticInt;
import mirror.RefStaticMethod;
import mirror.RefStaticObject;

public class ActivityThread {
  public static Class<?> TYPE = RefClass.load(ActivityThread.class, "android.app.ActivityThread");
  
  public static RefStaticMethod<Object> currentActivityThread;
  
  public static RefMethod<IBinder> getApplicationThread;
  
  public static RefMethod<Handler> getHandler;
  
  public static RefMethod<Object> getLaunchingActivity;
  
  public static RefMethod<Object> getPackageInfoNoCheck;
  
  public static RefStaticMethod<IPackageManager> getPackageManager;
  
  public static RefMethod<String> getProcessName;
  
  public static RefMethod<Object> installProvider;
  
  public static RefObject<Map<IBinder, Object>> mActivities;
  
  public static RefObject<Object> mBoundApplication;
  
  public static RefObject<Handler> mH;
  
  public static RefObject<Application> mInitialApplication;
  
  public static RefObject<Instrumentation> mInstrumentation;
  
  public static RefObject<Map<String, WeakReference<?>>> mPackages;
  
  public static RefObject<Map> mProviderMap;
  
  @MethodParams({IBinder.class, List.class})
  public static RefMethod<Void> performNewIntents;
  
  public static RefStaticObject<IInterface> sPackageManager;
  
  public static RefStaticObject<IInterface> sPermissionManager;
  
  @MethodParams({IBinder.class, String.class, int.class, int.class, Intent.class})
  public static RefMethod<Void> sendActivityResult;
  
  public static void handleNewIntent(Object paramObject, List paramList) {
    try {
      Object object = currentActivityThread.call(new Object[0]);
      if (object != null) {
        Method method = object.getClass().getDeclaredMethod("handleNewIntent", new Class[] { paramObject.getClass(), List.class });
        if (method != null) {
          method.setAccessible(true);
          method.invoke(object, new Object[] { paramObject, paramList });
        } 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public static Object installProvider(Object paramObject1, Context paramContext, ProviderInfo paramProviderInfo, Object paramObject2) throws Throwable {
    RefMethod<Object> refMethod = installProvider;
    Boolean bool = Boolean.valueOf(true);
    return refMethod.callWithException(paramObject1, new Object[] { paramContext, paramObject2, paramProviderInfo, Boolean.valueOf(false), bool, bool });
  }
  
  public static class ActivityClientRecord {
    public static Class<?> TYPE = RefClass.load(ActivityClientRecord.class, "android.app.ActivityThread$ActivityClientRecord");
    
    public static RefObject<Activity> activity;
    
    public static RefObject<ActivityInfo> activityInfo;
    
    public static RefObject<Object> compatInfo;
    
    public static RefObject<Intent> intent;
    
    public static RefObject<Boolean> isTopResumedActivity;
    
    public static RefObject<Object> packageInfo;
    
    public static RefObject<IBinder> token;
  }
  
  public static class AppBindData {
    public static Class<?> TYPE = RefClass.load(AppBindData.class, "android.app.ActivityThread$AppBindData");
    
    public static RefObject<ApplicationInfo> appInfo;
    
    public static RefObject<Object> info;
    
    public static RefObject<ComponentName> instrumentationName;
    
    public static RefObject<String> processName;
    
    public static RefObject<List<ProviderInfo>> providers;
  }
  
  public static class H {
    public static RefStaticInt EXECUTE_TRANSACTION;
    
    public static RefStaticInt LAUNCH_ACTIVITY;
    
    public static RefStaticInt SCHEDULE_CRASH;
    
    public static Class<?> TYPE = RefClass.load(H.class, "android.app.ActivityThread$H");
  }
  
  public static class ProviderClientRecord {
    public static Class<?> TYPE = RefClass.load(ProviderClientRecord.class, "android.app.ActivityThread$ProviderClientRecord");
    
    @MethodReflectParams({"android.app.ActivityThread", "java.lang.String", "android.content.IContentProvider", "android.content.ContentProvider"})
    public static RefConstructor<?> ctor;
    
    public static RefObject<String> mName;
    
    public static RefObject<IInterface> mProvider;
  }
  
  public static class ProviderClientRecordJB {
    public static Class<?> TYPE = RefClass.load(ProviderClientRecordJB.class, "android.app.ActivityThread$ProviderClientRecord");
    
    public static RefObject<Object> mHolder;
    
    public static RefObject<IInterface> mProvider;
  }
  
  public static class ProviderKeyJBMR1 {
    public static Class<?> TYPE = RefClass.load(ProviderKeyJBMR1.class, "android.app.ActivityThread$ProviderKey");
    
    @MethodParams({String.class, int.class})
    public static RefConstructor<?> ctor;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\ActivityThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */