package com.lody.virtual.server.am;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

class ActivityRecord extends Binder {
  public ComponentName component;
  
  public ActivityInfo info;
  
  public Intent intent;
  
  public boolean marked;
  
  Bundle options;
  
  public ClearTaskAction pendingClearAction = ClearTaskAction.NONE;
  
  public PendingNewIntent pendingNewIntent;
  
  public ProcessRecord process;
  
  int requestCode;
  
  public IBinder resultTo;
  
  String resultWho;
  
  public boolean started;
  
  public TaskRecord task;
  
  public IBinder token;
  
  public int userId;
  
  public ActivityRecord(int paramInt, Intent paramIntent, ActivityInfo paramActivityInfo, IBinder paramIBinder) {
    this.userId = paramInt;
    this.intent = paramIntent;
    this.info = paramActivityInfo;
    if (paramActivityInfo.targetActivity != null) {
      this.component = new ComponentName(paramActivityInfo.packageName, paramActivityInfo.targetActivity);
    } else {
      this.component = new ComponentName(paramActivityInfo.packageName, paramActivityInfo.name);
    } 
    this.resultTo = paramIBinder;
  }
  
  public void init(TaskRecord paramTaskRecord, ProcessRecord paramProcessRecord, IBinder paramIBinder) {
    this.userId = paramTaskRecord.userId;
    this.task = paramTaskRecord;
    this.process = paramProcessRecord;
    this.token = paramIBinder;
    this.started = true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\am\ActivityRecord.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */