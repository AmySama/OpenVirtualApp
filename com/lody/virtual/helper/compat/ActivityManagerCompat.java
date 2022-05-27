package com.lody.virtual.helper.compat;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import mirror.android.app.Activity;
import mirror.android.app.ActivityManager;
import mirror.android.app.ActivityManagerNative;
import mirror.android.app.IActivityManagerICS;
import mirror.android.app.IActivityManagerL;
import mirror.android.app.IActivityManagerN;

public class ActivityManagerCompat {
  public static final int INTENT_SENDER_ACTIVITY = 2;
  
  public static final int INTENT_SENDER_ACTIVITY_RESULT = 3;
  
  public static final int INTENT_SENDER_BROADCAST = 1;
  
  public static final int INTENT_SENDER_SERVICE = 4;
  
  public static final int SERVICE_DONE_EXECUTING_ANON = 0;
  
  public static final int SERVICE_DONE_EXECUTING_START = 1;
  
  public static final int SERVICE_DONE_EXECUTING_STOP = 2;
  
  public static final int START_INTENT_NOT_RESOLVED;
  
  public static final int START_NOT_CURRENT_USER_ACTIVITY;
  
  public static final int START_TASK_TO_FRONT;
  
  public static final int USER_OP_SUCCESS = 0;
  
  static {
    int i;
    if (ActivityManager.START_INTENT_NOT_RESOLVED == null) {
      i = -1;
    } else {
      i = ActivityManager.START_INTENT_NOT_RESOLVED.get();
    } 
    START_INTENT_NOT_RESOLVED = i;
    if (ActivityManager.START_NOT_CURRENT_USER_ACTIVITY == null) {
      i = -8;
    } else {
      i = ActivityManager.START_NOT_CURRENT_USER_ACTIVITY.get();
    } 
    START_NOT_CURRENT_USER_ACTIVITY = i;
    if (ActivityManager.START_TASK_TO_FRONT == null) {
      i = 2;
    } else {
      i = ActivityManager.START_TASK_TO_FRONT.get();
    } 
    START_TASK_TO_FRONT = i;
  }
  
  public static boolean finishActivity(IBinder paramIBinder, int paramInt, Intent paramIntent) {
    if (Build.VERSION.SDK_INT >= 24)
      return ((Boolean)IActivityManagerN.finishActivity.call(ActivityManagerNative.getDefault.call(new Object[0]), new Object[] { paramIBinder, Integer.valueOf(paramInt), paramIntent, Integer.valueOf(0) })).booleanValue(); 
    if (Build.VERSION.SDK_INT >= 21)
      return ((Boolean)IActivityManagerL.finishActivity.call(ActivityManagerNative.getDefault.call(new Object[0]), new Object[] { paramIBinder, Integer.valueOf(paramInt), paramIntent, Boolean.valueOf(false) })).booleanValue(); 
    IActivityManagerICS.finishActivity.call(ActivityManagerNative.getDefault.call(new Object[0]), new Object[] { paramIBinder, Integer.valueOf(paramInt), paramIntent });
    return false;
  }
  
  public static void setActivityOrientation(Activity paramActivity, int paramInt) {
    try {
      paramActivity.setRequestedOrientation(paramInt);
    } finally {
      Exception exception = null;
      exception.printStackTrace();
      for (paramActivity = (Activity)Activity.mParent.get(paramActivity); paramActivity != null; paramActivity = (Activity)Activity.mParent.get(paramActivity));
      IBinder iBinder = (IBinder)Activity.mToken.get(paramActivity);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\ActivityManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */