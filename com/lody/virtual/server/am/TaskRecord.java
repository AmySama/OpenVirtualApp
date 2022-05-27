package com.lody.virtual.server.am;

import android.content.ComponentName;
import android.content.Intent;
import com.lody.virtual.remote.AppTaskInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class TaskRecord {
  public final List<ActivityRecord> activities = new ArrayList<ActivityRecord>();
  
  public String affinity;
  
  public int taskId;
  
  public Intent taskRoot;
  
  public int userId;
  
  TaskRecord(int paramInt1, int paramInt2, String paramString, Intent paramIntent) {
    this.taskId = paramInt1;
    this.userId = paramInt2;
    this.affinity = paramString;
    this.taskRoot = paramIntent;
  }
  
  public void finish() {
    synchronized (this.activities) {
      Iterator<ActivityRecord> iterator = this.activities.iterator();
      while (iterator.hasNext())
        ((ActivityRecord)iterator.next()).marked = true; 
      return;
    } 
  }
  
  AppTaskInfo getAppTaskInfo() {
    int i = this.activities.size();
    if (i <= 0)
      return null; 
    ComponentName componentName = ((ActivityRecord)this.activities.get(i - 1)).component;
    i = this.taskId;
    Intent intent = this.taskRoot;
    return new AppTaskInfo(i, intent, intent.getComponent(), componentName);
  }
  
  ActivityRecord getRootActivityRecord() {
    // Byte code:
    //   0: aload_0
    //   1: getfield activities : Ljava/util/List;
    //   4: astore_1
    //   5: aload_1
    //   6: monitorenter
    //   7: iconst_0
    //   8: istore_2
    //   9: iload_2
    //   10: aload_0
    //   11: getfield activities : Ljava/util/List;
    //   14: invokeinterface size : ()I
    //   19: if_icmpge -> 60
    //   22: aload_0
    //   23: getfield activities : Ljava/util/List;
    //   26: iload_2
    //   27: invokeinterface get : (I)Ljava/lang/Object;
    //   32: checkcast com/lody/virtual/server/am/ActivityRecord
    //   35: astore_3
    //   36: aload_3
    //   37: getfield started : Z
    //   40: ifeq -> 56
    //   43: aload_3
    //   44: getfield marked : Z
    //   47: ifeq -> 56
    //   50: iinc #2, 1
    //   53: goto -> 9
    //   56: aload_1
    //   57: monitorexit
    //   58: aload_3
    //   59: areturn
    //   60: aload_1
    //   61: monitorexit
    //   62: aconst_null
    //   63: areturn
    //   64: astore_3
    //   65: aload_1
    //   66: monitorexit
    //   67: aload_3
    //   68: athrow
    // Exception table:
    //   from	to	target	type
    //   9	50	64	finally
    //   56	58	64	finally
    //   60	62	64	finally
    //   65	67	64	finally
  }
  
  public ActivityRecord getTopActivityRecord() {
    return getTopActivityRecord(false);
  }
  
  public ActivityRecord getTopActivityRecord(boolean paramBoolean) {
    synchronized (this.activities) {
      if (this.activities.isEmpty())
        return null; 
      for (int i = this.activities.size() - 1; i >= 0; i--) {
        ActivityRecord activityRecord = this.activities.get(i);
        if (activityRecord.started && (paramBoolean || !activityRecord.marked))
          return activityRecord; 
      } 
      return null;
    } 
  }
  
  public boolean isFinishing() {
    synchronized (this.activities) {
      for (ActivityRecord activityRecord : this.activities) {
        if (activityRecord.started && !activityRecord.marked)
          return false; 
      } 
      return true;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\am\TaskRecord.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */