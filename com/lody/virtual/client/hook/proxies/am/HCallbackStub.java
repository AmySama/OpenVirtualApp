package com.lody.virtual.client.hook.proxies.am;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.proxies.app.ActivityClientControllerStub;
import com.lody.virtual.client.interfaces.IInjector;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.AvoidRecursive;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.ComponentUtils;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.remote.ShadowActivityInfo;
import mirror.android.app.ActivityClient;
import mirror.android.app.ActivityManagerNative;
import mirror.android.app.ActivityThread;
import mirror.android.app.ClientTransactionHandler;
import mirror.android.app.IActivityManager;
import mirror.android.app.servertransaction.ClientTransaction;
import mirror.android.app.servertransaction.LaunchActivityItem;
import mirror.android.app.servertransaction.TopResumedActivityChangeItem;
import mirror.android.os.Handler;

public class HCallbackStub implements Handler.Callback, IInjector {
  private static final int EXECUTE_TRANSACTION;
  
  private static final int LAUNCH_ACTIVITY;
  
  private static final int SCHEDULE_CRASH = ActivityThread.H.SCHEDULE_CRASH.get();
  
  private static final String TAG;
  
  private static final HCallbackStub sCallback;
  
  private final AvoidRecursive mAvoidRecurisve = new AvoidRecursive();
  
  private Handler.Callback otherCallback;
  
  static {
    boolean bool = BuildCompat.isPie();
    byte b = -1;
    if (bool) {
      i = -1;
    } else {
      i = ActivityThread.H.LAUNCH_ACTIVITY.get();
    } 
    LAUNCH_ACTIVITY = i;
    int i = b;
    if (BuildCompat.isPie())
      i = ActivityThread.H.EXECUTE_TRANSACTION.get(); 
    EXECUTE_TRANSACTION = i;
    TAG = HCallbackStub.class.getSimpleName();
    sCallback = new HCallbackStub();
  }
  
  public static HCallbackStub getDefault() {
    return sCallback;
  }
  
  private static Handler getH() {
    return (Handler)ActivityThread.mH.get(VirtualCore.mainThread());
  }
  
  private static Handler.Callback getHCallback() {
    try {
      return (Handler.Callback)Handler.mCallback.get(handler);
    } finally {
      Exception exception = null;
      exception.printStackTrace();
    } 
  }
  
  private boolean handleExecuteTransaction(Message paramMessage) {
    Object object1 = paramMessage.obj;
    IBinder iBinder = (IBinder)ClientTransaction.mActivityToken.get(object1);
    Object object2 = ClientTransactionHandler.getActivityClient.call(VirtualCore.mainThread(), new Object[] { iBinder });
    object1 = ClientTransaction.mActivityCallbacks.get(object1);
    if (object1 != null && !object1.isEmpty()) {
      object1 = object1.get(0);
      if (object1 == null)
        return true; 
      if (object2 == null)
        return (object1.getClass() != LaunchActivityItem.TYPE) ? true : handleLaunchActivity(paramMessage, object1); 
      if (BuildCompat.isQ() && TopResumedActivityChangeItem.TYPE != null && object1.getClass() == TopResumedActivityChangeItem.TYPE) {
        try {
          if (TopResumedActivityChangeItem.mOnTop.get(object1) == ActivityThread.ActivityClientRecord.isTopResumedActivity.get(object2)) {
            object2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            this();
            stringBuilder.append("Activity top position already set to onTop=");
            stringBuilder.append(TopResumedActivityChangeItem.mOnTop.get(object1));
            Log.e((String)object2, stringBuilder.toString());
            return false;
          } 
          return true;
        } finally {}
        return false;
      } 
    } 
    return true;
  }
  
  private boolean handleLaunchActivity(Message paramMessage, Object paramObject) {
    Intent intent1;
    IBinder iBinder;
    if (BuildCompat.isPie()) {
      intent1 = (Intent)LaunchActivityItem.mIntent.get(paramObject);
    } else {
      intent1 = (Intent)ActivityThread.ActivityClientRecord.intent.get(paramObject);
    } 
    ShadowActivityInfo shadowActivityInfo = new ShadowActivityInfo(intent1);
    if (shadowActivityInfo.intent == null)
      return true; 
    Intent intent2 = shadowActivityInfo.intent;
    if (BuildCompat.isPie()) {
      iBinder = (IBinder)ClientTransaction.mActivityToken.get(paramMessage.obj);
    } else {
      iBinder = (IBinder)ActivityThread.ActivityClientRecord.token.get(paramObject);
    } 
    ActivityInfo activityInfo = shadowActivityInfo.info;
    if (activityInfo == null)
      return true; 
    if (VClient.get().getClientConfig() == null) {
      if (VirtualCore.get().getInstalledAppInfo(activityInfo.packageName, 0) == null)
        return true; 
      VActivityManager.get().processRestarted(activityInfo.packageName, activityInfo.processName, shadowActivityInfo.userId);
      getH().sendMessageAtFrontOfQueue(Message.obtain(paramMessage));
      return false;
    } 
    VClient.get().bindApplication(activityInfo.packageName, activityInfo.processName);
    int i = ((Integer)IActivityManager.getTaskForActivity.call(ActivityManagerNative.getDefault.call(new Object[0]), new Object[] { iBinder, Boolean.valueOf(false) })).intValue();
    VActivityManager.get().onActivityCreate(shadowActivityInfo.virtualToken, iBinder, i);
    ComponentUtils.unpackFillIn(intent2, VClient.get().getClassLoader(activityInfo.applicationInfo));
    if (BuildCompat.isPie()) {
      if (BuildCompat.isS() && ActivityThread.getLaunchingActivity != null) {
        Object object = ActivityThread.getLaunchingActivity.call(VirtualCore.mainThread(), new Object[] { iBinder });
        if (object != null) {
          Object object1 = ActivityThread.ActivityClientRecord.compatInfo.get(object);
          object1 = ActivityThread.getPackageInfoNoCheck.call(VirtualCore.mainThread(), new Object[] { activityInfo.applicationInfo, object1 });
          ActivityThread.ActivityClientRecord.intent.set(object, intent2);
          ActivityThread.ActivityClientRecord.activityInfo.set(object, activityInfo);
          ActivityThread.ActivityClientRecord.packageInfo.set(object, object1);
        } 
      } 
      if (BuildCompat.isS() && LaunchActivityItem.mActivityClientController != null && (IInterface)LaunchActivityItem.mActivityClientController.get(paramObject) != null)
        ActivityClient.ActivityClientControllerSingleton.mKnownInstance.set(ActivityClient.INTERFACE_SINGLETON.get(), ActivityClientControllerStub.getProxyInterface()); 
      LaunchActivityItem.mIntent.set(paramObject, intent2);
      LaunchActivityItem.mInfo.set(paramObject, activityInfo);
    } else {
      ActivityThread.ActivityClientRecord.intent.set(paramObject, intent2);
      ActivityThread.ActivityClientRecord.activityInfo.set(paramObject, activityInfo);
    } 
    return true;
  }
  
  public boolean handleMessage(Message paramMessage) {
    if (this.mAvoidRecurisve.beginCall())
      try {
        String str;
        if (LAUNCH_ACTIVITY == paramMessage.what) {
          boolean bool = handleLaunchActivity(paramMessage, paramMessage.obj);
          if (!bool)
            return true; 
        } else if (BuildCompat.isPie() && EXECUTE_TRANSACTION == paramMessage.what) {
          if (!handleExecuteTransaction(paramMessage))
            return true; 
        } else if (SCHEDULE_CRASH == paramMessage.what) {
          str = (String)paramMessage.obj;
          RemoteException remoteException = new RemoteException();
          this(str);
          remoteException.printStackTrace();
          return false;
        } 
        if (this.otherCallback != null)
          return this.otherCallback.handleMessage((Message)str); 
      } finally {
        this.mAvoidRecurisve.finishCall();
      }  
    return false;
  }
  
  public void inject() {
    this.otherCallback = getHCallback();
    Handler.mCallback.set(getH(), this);
  }
  
  public boolean isEnvBad() {
    boolean bool;
    Handler.Callback callback = getHCallback();
    if (callback != this) {
      bool = true;
    } else {
      bool = false;
    } 
    if (callback != null && bool) {
      String str = TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("HCallback has bad, other callback = ");
      stringBuilder.append(callback);
      VLog.d(str, stringBuilder.toString(), new Object[0]);
    } 
    return bool;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\am\HCallbackStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */