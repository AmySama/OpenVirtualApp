package com.lody.virtual.client.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.env.SpecialComponentList;
import com.lody.virtual.client.ipc.VPackageManager;
import com.lody.virtual.helper.utils.ComponentUtils;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.remote.BroadcastIntentData;
import com.lody.virtual.remote.ReceiverInfo;
import java.util.HashMap;
import java.util.Map;
import mirror.android.content.BroadcastReceiver;

public class StaticReceiverSystem {
  private static final int BROADCAST_TIME_OUT = 8500;
  
  private static final String TAG = "StaticReceiverSystem";
  
  private static final StaticReceiverSystem mSystem = new StaticReceiverSystem();
  
  private ApplicationInfo mApplicationInfo;
  
  private final Map<IBinder, BroadcastRecord> mBroadcastRecords = new HashMap<IBinder, BroadcastRecord>();
  
  private Context mContext;
  
  private StaticScheduler mScheduler;
  
  private TimeoutHandler mTimeoutHandler;
  
  private int mUserId;
  
  public static StaticReceiverSystem get() {
    return mSystem;
  }
  
  private boolean handleStaticBroadcast(BroadcastIntentData paramBroadcastIntentData, ActivityInfo paramActivityInfo, BroadcastReceiver.PendingResult paramPendingResult) {
    Map<IBinder, BroadcastRecord> map;
    Message message;
    if (paramBroadcastIntentData.targetPackage != null && !paramBroadcastIntentData.targetPackage.equals(paramActivityInfo.packageName))
      return false; 
    if (paramBroadcastIntentData.userId != -1 && paramBroadcastIntentData.userId != this.mUserId)
      return false; 
    ComponentName componentName = ComponentUtils.toComponentName((ComponentInfo)paramActivityInfo);
    BroadcastRecord broadcastRecord = new BroadcastRecord(paramActivityInfo, paramPendingResult);
    IBinder iBinder = (IBinder)BroadcastReceiver.PendingResult.mToken.get(paramPendingResult);
    synchronized (this.mBroadcastRecords) {
      this.mBroadcastRecords.put(iBinder, broadcastRecord);
      message = new Message();
      message.obj = iBinder;
      this.mTimeoutHandler.sendMessageDelayed(message, 8500L);
      VClient.get().scheduleReceiver(paramActivityInfo.processName, componentName, paramBroadcastIntentData.intent, paramPendingResult);
      return true;
    } 
  }
  
  public void attach(String paramString, Context paramContext, ApplicationInfo paramApplicationInfo, int paramInt) {
    if (this.mApplicationInfo == null) {
      this.mContext = paramContext;
      this.mApplicationInfo = paramApplicationInfo;
      this.mUserId = paramInt;
      HandlerThread handlerThread2 = new HandlerThread("BroadcastThread");
      HandlerThread handlerThread1 = new HandlerThread("BroadcastAnrThread");
      handlerThread2.start();
      handlerThread1.start();
      this.mScheduler = new StaticScheduler(handlerThread2.getLooper());
      this.mTimeoutHandler = new TimeoutHandler(handlerThread1.getLooper());
      for (ReceiverInfo receiverInfo : VPackageManager.get().getReceiverInfos(paramApplicationInfo.packageName, paramString, paramInt)) {
        IntentFilter intentFilter = new IntentFilter(ComponentUtils.getComponentAction(receiverInfo.info));
        intentFilter.addCategory("__VA__|_static_receiver_");
        this.mContext.registerReceiver(new StaticReceiver(receiverInfo.info), intentFilter, null, this.mScheduler);
        for (IntentFilter intentFilter1 : receiverInfo.filters) {
          SpecialComponentList.protectIntentFilter(intentFilter1);
          intentFilter1.addCategory("__VA__|_static_receiver_");
          this.mContext.registerReceiver(new StaticReceiver(receiverInfo.info), intentFilter1, null, this.mScheduler);
        } 
      } 
      return;
    } 
    throw new IllegalStateException("attached");
  }
  
  public boolean broadcastFinish(IBinder paramIBinder) {
    synchronized (this.mBroadcastRecords) {
      BroadcastRecord broadcastRecord = this.mBroadcastRecords.remove(paramIBinder);
      if (broadcastRecord == null)
        return false; 
      this.mTimeoutHandler.removeMessages(0, paramIBinder);
      broadcastRecord.pendingResult.finish();
      return true;
    } 
  }
  
  private static final class BroadcastRecord {
    BroadcastReceiver.PendingResult pendingResult;
    
    ActivityInfo receiverInfo;
    
    BroadcastRecord(ActivityInfo param1ActivityInfo, BroadcastReceiver.PendingResult param1PendingResult) {
      this.receiverInfo = param1ActivityInfo;
      this.pendingResult = param1PendingResult;
    }
  }
  
  private class StaticReceiver extends BroadcastReceiver {
    private ActivityInfo info;
    
    public StaticReceiver(ActivityInfo param1ActivityInfo) {
      this.info = param1ActivityInfo;
    }
    
    public void onReceive(Context param1Context, Intent param1Intent) {
      if ((param1Intent.getFlags() & 0x40000000) == 0 && !isInitialStickyBroadcast() && VClient.get() != null && VClient.get().getCurrentApplication() != null) {
        param1Intent.setExtrasClassLoader(VClient.get().getCurrentApplication().getClassLoader());
        BroadcastIntentData broadcastIntentData = new BroadcastIntentData(param1Intent);
        if (broadcastIntentData.intent == null) {
          broadcastIntentData.intent = param1Intent;
          broadcastIntentData.targetPackage = param1Intent.getPackage();
          param1Intent.setPackage(null);
        } 
        BroadcastReceiver.PendingResult pendingResult = goAsync();
        if (pendingResult != null && !StaticReceiverSystem.this.handleStaticBroadcast(broadcastIntentData, this.info, pendingResult))
          pendingResult.finish(); 
      } 
    }
  }
  
  private static final class StaticScheduler extends Handler {
    StaticScheduler(Looper param1Looper) {
      super(param1Looper);
    }
  }
  
  private final class TimeoutHandler extends Handler {
    TimeoutHandler(Looper param1Looper) {
      super(param1Looper);
    }
    
    public void handleMessage(Message param1Message) {
      IBinder iBinder = (IBinder)param1Message.obj;
      StaticReceiverSystem.BroadcastRecord broadcastRecord = (StaticReceiverSystem.BroadcastRecord)StaticReceiverSystem.this.mBroadcastRecords.remove(iBinder);
      if (broadcastRecord != null) {
        VLog.w("StaticReceiverSystem", "Broadcast timeout, cancel to dispatch it.", new Object[0]);
        broadcastRecord.pendingResult.finish();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\receiver\StaticReceiverSystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */