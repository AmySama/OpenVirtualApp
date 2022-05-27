package com.lody.virtual.server.am;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ComponentInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.stub.StubManifest;
import com.lody.virtual.helper.collection.SparseArray;
import com.lody.virtual.helper.compat.ObjectsCompat;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.ClassUtils;
import com.lody.virtual.helper.utils.ComponentUtils;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.remote.AppTaskInfo;
import com.lody.virtual.remote.ShadowActivityInfo;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import mirror.android.app.ActivityManagerNative;
import mirror.android.app.IActivityManager;

class ActivityStack {
  private static final String TAG = "ActivityStack";
  
  private static final boolean sTrace = false;
  
  private final ActivityManager mAM;
  
  private final SparseArray<TaskRecord> mHistory = new SparseArray();
  
  private final Set<ActivityRecord> mPendingLaunchActivities = Collections.synchronizedSet(new HashSet<ActivityRecord>());
  
  private final VActivityManagerService mService;
  
  ActivityStack(VActivityManagerService paramVActivityManagerService) {
    this.mService = paramVActivityManagerService;
    this.mAM = (ActivityManager)VirtualCore.get().getContext().getSystemService("activity");
  }
  
  private static String activityInfoFlagsToString(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    int i = paramInt;
    if (containFlags(paramInt, 1)) {
      stringBuilder.append("FLAG_MULTIPROCESS | ");
      i = removeFlags(paramInt, 1);
    } 
    int j = i;
    if (containFlags(i, 1048576)) {
      stringBuilder.append("FLAG_VISIBLE_TO_INSTANT_APP | ");
      j = removeFlags(i, 1048576);
    } 
    paramInt = j;
    if (containFlags(j, 2)) {
      stringBuilder.append("FLAG_FINISH_ON_TASK_LAUNCH | ");
      paramInt = removeFlags(j, 2);
    } 
    i = paramInt;
    if (containFlags(paramInt, 4)) {
      stringBuilder.append("FLAG_CLEAR_TASK_ON_LAUNCH | ");
      i = removeFlags(paramInt, 4);
    } 
    paramInt = i;
    if (containFlags(i, 8)) {
      stringBuilder.append("FLAG_ALWAYS_RETAIN_TASK_STATE | ");
      paramInt = removeFlags(i, 8);
    } 
    i = paramInt;
    if (containFlags(paramInt, 16)) {
      stringBuilder.append("FLAG_STATE_NOT_NEEDED | ");
      i = removeFlags(paramInt, 16);
    } 
    paramInt = i;
    if (containFlags(i, 64)) {
      stringBuilder.append("FLAG_ALLOW_TASK_REPARENTING | ");
      paramInt = removeFlags(i, 64);
    } 
    i = paramInt;
    if (containFlags(paramInt, 128)) {
      stringBuilder.append("FLAG_NO_HISTORY | ");
      i = removeFlags(paramInt, 128);
    } 
    paramInt = i;
    if (containFlags(i, 256)) {
      stringBuilder.append("FLAG_FINISH_ON_CLOSE_SYSTEM_DIALOGS | ");
      paramInt = removeFlags(i, 256);
    } 
    i = paramInt;
    if (containFlags(paramInt, 512)) {
      stringBuilder.append("FLAG_HARDWARE_ACCELERATED | ");
      i = removeFlags(paramInt, 512);
    } 
    paramInt = i;
    if (containFlags(i, 1073741824)) {
      stringBuilder.append("FLAG_SINGLE_USER | ");
      paramInt = removeFlags(i, 1073741824);
    } 
    i = paramInt;
    if (containFlags(paramInt, 32)) {
      stringBuilder.append("FLAG_EXCLUDE_FROM_RECENTS | ");
      i = removeFlags(paramInt, 32);
    } 
    if (i != 0) {
      stringBuilder.append("0x");
      stringBuilder.append(Integer.toHexString(i));
    } else if (stringBuilder.length() > 2) {
      stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
    } 
    return stringBuilder.toString();
  }
  
  private static String activityInfoToString(ActivityInfo paramActivityInfo) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("launchMode: ");
    stringBuilder.append(launchModeToString(paramActivityInfo.launchMode));
    if (Build.VERSION.SDK_INT >= 21) {
      stringBuilder.append("\ndocumentLaunchMode: ");
      stringBuilder.append(documentLaunchModeToString(paramActivityInfo.documentLaunchMode));
    } 
    stringBuilder.append("\naffinity: ");
    stringBuilder.append(paramActivityInfo.taskAffinity);
    stringBuilder.append("\nflags: ");
    stringBuilder.append(activityInfoFlagsToString(paramActivityInfo.flags));
    return stringBuilder.toString();
  }
  
  private static String componentInfoToString(ComponentInfo paramComponentInfo) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramComponentInfo.packageName);
    stringBuilder.append("/");
    stringBuilder.append(paramComponentInfo.name);
    return stringBuilder.toString();
  }
  
  private static boolean containFlags(int paramInt1, int paramInt2) {
    boolean bool;
    if ((paramInt1 & paramInt2) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private static boolean containFlags(Intent paramIntent, int paramInt) {
    boolean bool;
    if ((paramIntent.getFlags() & paramInt) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private void deliverNewIntentLocked(ActivityRecord paramActivityRecord1, ActivityRecord paramActivityRecord2, Intent paramIntent) {
    String str;
    if (paramActivityRecord2 == null)
      return; 
    if (paramActivityRecord1 != null) {
      str = paramActivityRecord1.component.getPackageName();
    } else {
      str = "android";
    } 
    if (paramActivityRecord2.started && paramActivityRecord2.process != null && paramActivityRecord2.process.client != null) {
      try {
        paramActivityRecord2.process.client.scheduleNewIntent(str, paramActivityRecord2.token, paramIntent);
      } catch (RemoteException remoteException) {
        remoteException.printStackTrace();
      } 
    } else {
      paramActivityRecord2.pendingNewIntent = new PendingNewIntent((String)remoteException, paramIntent);
    } 
  }
  
  private static String documentLaunchModeToString(int paramInt) {
    return (paramInt != 0) ? ((paramInt != 1) ? ((paramInt != 2) ? ((paramInt != 3) ? "unknown" : "never") : "always") : "intoExisting") : "none";
  }
  
  private ActivityRecord findActivityByComponentName(TaskRecord paramTaskRecord, ComponentName paramComponentName) {
    synchronized (paramTaskRecord.activities) {
      for (int i = paramTaskRecord.activities.size() - 1; i >= 0; i--) {
        ActivityRecord activityRecord = paramTaskRecord.activities.get(i);
        if (!activityRecord.marked && activityRecord.component.equals(paramComponentName))
          return activityRecord; 
      } 
      return null;
    } 
  }
  
  private ActivityRecord findActivityByToken(int paramInt, IBinder paramIBinder) {
    TaskRecord taskRecord1 = null;
    TaskRecord taskRecord2 = null;
    if (paramIBinder != null)
      for (byte b = 0;; b++) {
        taskRecord1 = taskRecord2;
        if (b < this.mHistory.size()) {
          taskRecord1 = (TaskRecord)this.mHistory.valueAt(b);
          if (taskRecord1.userId == paramInt) {
            synchronized (taskRecord1.activities) {
              for (ActivityRecord activityRecord : taskRecord1.activities) {
                if (activityRecord.token == paramIBinder)
                  ActivityRecord activityRecord1 = activityRecord; 
              } 
              b++;
            } 
            continue;
          } 
        } else {
          break;
        } 
      }  
    return activityRecord;
  }
  
  private TaskRecord findTaskByAffinityLocked(int paramInt, String paramString) {
    for (byte b = 0; b < this.mHistory.size(); b++) {
      TaskRecord taskRecord = (TaskRecord)this.mHistory.valueAt(b);
      if (paramInt == taskRecord.userId && paramString.equals(taskRecord.affinity) && !taskRecord.isFinishing())
        return taskRecord; 
    } 
    return null;
  }
  
  private TaskRecord findTaskByComponentLocked(int paramInt, ComponentName paramComponentName) {
    for (byte b = 0; b < this.mHistory.size(); b++) {
      TaskRecord taskRecord = (TaskRecord)this.mHistory.valueAt(b);
      if (paramInt == taskRecord.userId)
        synchronized (taskRecord.activities) {
          for (ActivityRecord activityRecord : taskRecord.activities) {
            if (!activityRecord.marked && activityRecord.component.equals(paramComponentName))
              return taskRecord; 
          } 
        }  
    } 
    return null;
  }
  
  private TaskRecord findTaskByIntentLocked(int paramInt, Intent paramIntent) {
    for (byte b = 0; b < this.mHistory.size(); b++) {
      TaskRecord taskRecord = (TaskRecord)this.mHistory.valueAt(b);
      if (paramInt == taskRecord.userId && taskRecord.taskRoot != null && ObjectsCompat.equals(paramIntent.getComponent(), taskRecord.taskRoot.getComponent()))
        return taskRecord; 
    } 
    return null;
  }
  
  private void finishMarkedActivity() {
    synchronized (this.mHistory) {
      int i = this.mHistory.size();
      while (true) {
        int j = i - 1;
        if (i > 0) {
          null = (TaskRecord)this.mHistory.valueAt(j);
          synchronized (null.activities) {
            Iterator<ActivityRecord> iterator = null.activities.iterator();
            while (iterator.hasNext()) {
              ActivityRecord activityRecord = iterator.next();
              if (!activityRecord.marked)
                continue; 
              boolean bool = activityRecord.started;
              if (bool)
                try {
                  if (activityRecord.process != null && activityRecord.process.client != null)
                    activityRecord.process.client.finishActivity(activityRecord.token); 
                  iterator.remove();
                } catch (RemoteException remoteException) {
                  remoteException.printStackTrace();
                }  
            } 
            i = j;
          } 
          continue;
        } 
        return;
      } 
    } 
  }
  
  private ActivityRecord getCallingRecordLocked(int paramInt, IBinder paramIBinder) {
    ActivityRecord activityRecord = findActivityByToken(paramInt, paramIBinder);
    return (activityRecord == null) ? null : findActivityByToken(paramInt, activityRecord.resultTo);
  }
  
  private Intent getStartShadowActivityIntentInner(Intent paramIntent, boolean paramBoolean, int paramInt1, int paramInt2, ActivityRecord paramActivityRecord, ActivityInfo paramActivityInfo) {
    Intent intent1 = new Intent(paramIntent);
    Intent intent2 = new Intent();
    if (paramActivityInfo.screenOrientation == 3 && paramActivityRecord.task != null && paramActivityRecord.task.getTopActivityRecord() != null)
      paramActivityInfo.screenOrientation = (paramActivityRecord.task.getTopActivityRecord()).info.screenOrientation; 
    intent2.setClassName(StubManifest.getStubPackageName(paramBoolean), selectShadowActivity(paramInt1, paramActivityInfo));
    ComponentName componentName2 = intent1.getComponent();
    ComponentName componentName1 = componentName2;
    if (componentName2 == null)
      componentName1 = ComponentUtils.toComponentName((ComponentInfo)paramActivityInfo); 
    intent2.setType(componentName1.flattenToString());
    (new ShadowActivityInfo(intent1, paramActivityInfo, paramInt2, (IBinder)paramActivityRecord)).saveToIntent(intent2);
    return intent2;
  }
  
  private static String launchModeToString(int paramInt) {
    return (paramInt != 0) ? ((paramInt != 1) ? ((paramInt != 2) ? ((paramInt != 3) ? "unknown" : "singleInstance") : "singleTask") : "singleTop") : "standard";
  }
  
  private ActivityRecord newActivityRecord(int paramInt, Intent paramIntent, ActivityInfo paramActivityInfo, IBinder paramIBinder) {
    return new ActivityRecord(paramInt, paramIntent, paramActivityInfo, paramIBinder);
  }
  
  private void optimizeTasksLocked() {
    List list = VirtualCore.get().getRecentTasksEx(2147483647, 3);
    int i = this.mHistory.size();
    while (true) {
      int j = i - 1;
      if (i > 0) {
        TaskRecord taskRecord = (TaskRecord)this.mHistory.valueAt(j);
        ListIterator listIterator = list.listIterator();
        boolean bool = false;
        while (true) {
          i = bool;
          if (listIterator.hasNext()) {
            if (((ActivityManager.RecentTaskInfo)listIterator.next()).id == taskRecord.taskId) {
              i = 1;
              listIterator.remove();
              break;
            } 
            continue;
          } 
          break;
        } 
        if (i == 0)
          this.mHistory.removeAt(j); 
        i = j;
        continue;
      } 
      break;
    } 
  }
  
  private static String parseIntentFlagsToString(Intent paramIntent) {
    int i = paramIntent.getFlags();
    if (i == 0)
      return "0x0"; 
    StringBuilder stringBuilder = new StringBuilder();
    int j = i;
    if (containFlags(i, 268435456)) {
      stringBuilder.append("FLAG_ACTIVITY_NEW_TASK | ");
      j = removeFlags(i, 268435456);
    } 
    int k = j;
    if (containFlags(j, 32768)) {
      stringBuilder.append("FLAG_ACTIVITY_CLEAR_TASK | ");
      k = removeFlags(j, 32768);
    } 
    i = k;
    if (containFlags(k, 134217728)) {
      stringBuilder.append("FLAG_ACTIVITY_MULTIPLE_TASK | ");
      i = removeFlags(k, 134217728);
    } 
    j = i;
    if (containFlags(i, 131072)) {
      stringBuilder.append("FLAG_ACTIVITY_REORDER_TO_FRONT | ");
      j = removeFlags(i, 131072);
    } 
    i = j;
    if (containFlags(j, 131072)) {
      stringBuilder.append("FLAG_ACTIVITY_REORDER_TO_FRONT | ");
      i = removeFlags(j, 131072);
    } 
    k = i;
    if (containFlags(i, 536870912)) {
      stringBuilder.append("FLAG_ACTIVITY_SINGLE_TOP | ");
      k = removeFlags(i, 536870912);
    } 
    j = k;
    if (containFlags(k, 134217728)) {
      stringBuilder.append("FLAG_ACTIVITY_MULTIPLE_TASK | ");
      j = removeFlags(k, 134217728);
    } 
    i = j;
    if (containFlags(j, 33554432)) {
      stringBuilder.append("FLAG_ACTIVITY_FORWARD_RESULT | ");
      i = removeFlags(j, 33554432);
    } 
    k = i;
    if (containFlags(i, 16384)) {
      stringBuilder.append("FLAG_ACTIVITY_TASK_ON_HOME | ");
      k = removeFlags(i, 16384);
    } 
    j = k;
    if (containFlags(k, 67108864)) {
      stringBuilder.append("FLAG_ACTIVITY_CLEAR_TOP | ");
      j = removeFlags(k, 67108864);
    } 
    i = j;
    if (containFlags(j, 262144)) {
      stringBuilder.append("FLAG_ACTIVITY_NO_USER_ACTION | ");
      i = removeFlags(j, 262144);
    } 
    j = i;
    if (containFlags(i, 8192)) {
      stringBuilder.append("FLAG_ACTIVITY_RETAIN_IN_RECENTS | ");
      j = removeFlags(i, 8192);
    } 
    if (j != 0) {
      stringBuilder.append("0x");
      stringBuilder.append(Integer.toHexString(j));
    } else if (stringBuilder.length() >= 2) {
      stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
    } 
    return stringBuilder.toString();
  }
  
  private int realStartActivityLocked(IInterface paramIInterface, IBinder paramIBinder, Intent paramIntent, String paramString, int paramInt, Bundle paramBundle) {
    Class[] arrayOfClass = IActivityManager.startActivity.paramList();
    Object[] arrayOfObject = new Object[arrayOfClass.length];
    arrayOfObject[0] = paramIInterface;
    int i = ArrayUtils.protoIndexOf(arrayOfClass, Intent.class);
    int j = ArrayUtils.protoIndexOf(arrayOfClass, IBinder.class, 2);
    int k = ArrayUtils.protoIndexOf(arrayOfClass, Bundle.class);
    arrayOfObject[i] = paramIntent;
    arrayOfObject[j] = paramIBinder;
    arrayOfObject[j + 1] = paramString;
    arrayOfObject[j + 2] = Integer.valueOf(paramInt);
    if (k != -1)
      arrayOfObject[k] = paramBundle; 
    arrayOfObject[i + 1] = paramIntent.getType();
    arrayOfObject[i - 1] = VirtualCore.get().getHostPkg();
    ClassUtils.fixArgs(arrayOfClass, arrayOfObject);
    try {
      return ((Integer)IActivityManager.startActivity.call(ActivityManagerNative.getDefault.call(new Object[0]), arrayOfObject)).intValue();
    } finally {
      paramIInterface = null;
      paramIInterface.printStackTrace();
    } 
  }
  
  private static int removeFlags(int paramInt1, int paramInt2) {
    return paramInt1 & paramInt2;
  }
  
  private static void removeFlags(Intent paramIntent, int paramInt) {
    paramIntent.setFlags(paramInt & paramIntent.getFlags());
  }
  
  private String selectShadowActivity(int paramInt, ActivityInfo paramActivityInfo) {
    boolean bool2;
    boolean bool3;
    boolean bool4;
    boolean bool1 = false;
    try {
    
    } finally {
      Exception exception = null;
      bool4 = false;
      bool2 = false;
      exception.printStackTrace();
      boolean bool = false;
      bool3 = bool4;
    } 
    if (bool4 || bool3 || bool2)
      bool1 = true; 
    return bool1 ? StubManifest.getStubDialogName(paramInt, paramActivityInfo) : StubManifest.getStubActivityName(paramInt, paramActivityInfo);
  }
  
  private int startActivityFromSourceTask(ProcessRecord paramProcessRecord, IBinder paramIBinder, Intent paramIntent, String paramString, int paramInt, Bundle paramBundle) {
    return realStartActivityLocked(paramProcessRecord.appThread, paramIBinder, paramIntent, paramString, paramInt, paramBundle);
  }
  
  private int startActivityInNewTaskLocked(int paramInt1, int paramInt2, Intent paramIntent, ActivityInfo paramActivityInfo, Bundle paramBundle) {
    ActivityRecord activityRecord = newActivityRecord(paramInt2, paramIntent, paramActivityInfo, null);
    activityRecord.options = paramBundle;
    paramIntent = startActivityProcess(paramInt2, activityRecord, paramIntent, paramActivityInfo);
    if (paramIntent == null)
      return -1; 
    paramIntent.addFlags(paramInt1);
    paramIntent.addFlags(268435456);
    paramIntent.addFlags(134217728);
    paramIntent.addFlags(2097152);
    paramIntent.addFlags(524288);
    if (paramBundle != null) {
      VirtualCore.get().getContext().startActivity(paramIntent, paramBundle);
    } else {
      VirtualCore.get().getContext().startActivity(paramIntent);
    } 
    return 0;
  }
  
  private Intent startActivityProcess(int paramInt, ActivityRecord paramActivityRecord, Intent paramIntent, ActivityInfo paramActivityInfo) {
    ProcessRecord processRecord = this.mService.startProcessIfNeeded(paramActivityInfo.processName, paramInt, paramActivityInfo.packageName, -1);
    return (processRecord == null) ? null : getStartShadowActivityIntentInner(paramIntent, processRecord.isExt, processRecord.vpid, paramInt, paramActivityRecord, paramActivityInfo);
  }
  
  public boolean finishActivityAffinity(int paramInt, IBinder paramIBinder) {
    synchronized (this.mHistory) {
      ActivityRecord activityRecord = findActivityByToken(paramInt, paramIBinder);
      if (activityRecord == null)
        return false; 
      null = ComponentUtils.getTaskAffinity(activityRecord.info);
      synchronized (activityRecord.task.activities) {
        for (paramInt = activityRecord.task.activities.indexOf(activityRecord); paramInt >= 0; paramInt--) {
          ActivityRecord activityRecord1 = activityRecord.task.activities.get(paramInt);
          if (!ComponentUtils.getTaskAffinity(activityRecord1.info).equals(null))
            break; 
          activityRecord1.marked = true;
        } 
        finishMarkedActivity();
        return false;
      } 
    } 
  }
  
  public void finishAllActivities(ProcessRecord paramProcessRecord) {
    synchronized (this.mHistory) {
      int i = this.mHistory.size();
      while (true) {
        int j = i - 1;
        if (i > 0) {
          TaskRecord taskRecord = (TaskRecord)this.mHistory.valueAt(j);
          synchronized (taskRecord.activities) {
            for (ActivityRecord activityRecord : taskRecord.activities) {
              if (activityRecord.process.pid != paramProcessRecord.pid)
                continue; 
              activityRecord.marked = true;
            } 
            i = j;
          } 
          continue;
        } 
        finishMarkedActivity();
        return;
      } 
    } 
  }
  
  ComponentName getActivityClassForToken(int paramInt, IBinder paramIBinder) {
    synchronized (this.mHistory) {
      ActivityRecord activityRecord = findActivityByToken(paramInt, paramIBinder);
      if (activityRecord != null)
        return activityRecord.component; 
      return null;
    } 
  }
  
  ComponentName getCallingActivity(int paramInt, IBinder paramIBinder) {
    ActivityRecord activityRecord = getCallingRecordLocked(paramInt, paramIBinder);
    if (activityRecord != null) {
      ComponentName componentName = activityRecord.component;
    } else {
      activityRecord = null;
    } 
    return (ComponentName)activityRecord;
  }
  
  String getCallingPackage(int paramInt, IBinder paramIBinder) {
    ActivityRecord activityRecord = getCallingRecordLocked(paramInt, paramIBinder);
    if (activityRecord != null) {
      String str = activityRecord.info.packageName;
    } else {
      activityRecord = null;
    } 
    return (String)activityRecord;
  }
  
  String getPackageForToken(int paramInt, IBinder paramIBinder) {
    synchronized (this.mHistory) {
      ActivityRecord activityRecord = findActivityByToken(paramInt, paramIBinder);
      if (activityRecord != null)
        return activityRecord.info.packageName; 
      return null;
    } 
  }
  
  AppTaskInfo getTaskInfo(int paramInt) {
    synchronized (this.mHistory) {
      TaskRecord taskRecord = (TaskRecord)this.mHistory.get(paramInt);
      if (taskRecord != null)
        return taskRecord.getAppTaskInfo(); 
      return null;
    } 
  }
  
  void onActivityCreated(ProcessRecord paramProcessRecord, IBinder paramIBinder, int paramInt, ActivityRecord paramActivityRecord) {
    synchronized (this.mHistory) {
      this.mPendingLaunchActivities.remove(paramActivityRecord);
      optimizeTasksLocked();
      TaskRecord taskRecord1 = (TaskRecord)this.mHistory.get(paramInt);
      TaskRecord taskRecord2 = taskRecord1;
      if (taskRecord1 == null)
        if (paramActivityRecord.task != null) {
          taskRecord2 = paramActivityRecord.task;
        } else {
          taskRecord2 = new TaskRecord();
          this(paramInt, paramProcessRecord.userId, ComponentUtils.getTaskAffinity(paramActivityRecord.info), paramActivityRecord.intent);
          this.mHistory.put(paramInt, taskRecord2);
        }  
      if (paramActivityRecord.task != null && paramActivityRecord.task != taskRecord2)
        synchronized (paramActivityRecord.task.activities) {
          paramActivityRecord.task.activities.remove(paramActivityRecord);
        }  
      paramActivityRecord.task = taskRecord2;
      synchronized (paramActivityRecord.task.activities) {
        taskRecord2.activities.remove(paramActivityRecord);
        if (paramActivityRecord.pendingClearAction != ClearTaskAction.NONE) {
          performClearTaskLocked(taskRecord2, paramActivityRecord.component, paramActivityRecord.pendingClearAction, false);
          paramActivityRecord.pendingClearAction = ClearTaskAction.NONE;
        } 
        paramActivityRecord.init(taskRecord2, paramProcessRecord, paramIBinder);
        taskRecord2.activities.add(paramActivityRecord);
        if (paramActivityRecord.pendingNewIntent != null) {
          PendingNewIntent pendingNewIntent = paramActivityRecord.pendingNewIntent;
          try {
            paramActivityRecord.process.client.scheduleNewIntent(pendingNewIntent.creator, paramActivityRecord.token, pendingNewIntent.intent);
          } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
          } 
          paramActivityRecord.pendingNewIntent = null;
        } 
        finishMarkedActivity();
        return;
      } 
    } 
  }
  
  ActivityRecord onActivityDestroyed(int paramInt, IBinder paramIBinder) {
    synchronized (this.mHistory) {
      optimizeTasksLocked();
      ActivityRecord activityRecord = findActivityByToken(paramInt, paramIBinder);
      if (activityRecord != null) {
        activityRecord.marked = true;
        synchronized (activityRecord.task.activities) {
          activityRecord.task.activities.remove(activityRecord);
        } 
      } 
      return activityRecord;
    } 
  }
  
  void onActivityFinish(int paramInt, IBinder paramIBinder) {
    synchronized (this.mHistory) {
      ActivityRecord activityRecord = findActivityByToken(paramInt, paramIBinder);
      if (activityRecord != null)
        activityRecord.marked = true; 
      return;
    } 
  }
  
  void onActivityResumed(int paramInt, IBinder paramIBinder) {
    synchronized (this.mHistory) {
      optimizeTasksLocked();
      ActivityRecord activityRecord = findActivityByToken(paramInt, paramIBinder);
      if (activityRecord != null)
        synchronized (activityRecord.task.activities) {
          activityRecord.task.activities.remove(activityRecord);
          activityRecord.task.activities.add(activityRecord);
        }  
      return;
    } 
  }
  
  boolean performClearTaskLocked(TaskRecord paramTaskRecord, ComponentName paramComponentName, ClearTaskAction paramClearTaskAction, boolean paramBoolean) {
    // Byte code:
    //   0: aload_1
    //   1: getfield activities : Ljava/util/List;
    //   4: astore #5
    //   6: aload #5
    //   8: monitorenter
    //   9: getstatic com/lody/virtual/server/am/ActivityStack$1.$SwitchMap$com$lody$virtual$server$am$ClearTaskAction : [I
    //   12: aload_3
    //   13: invokevirtual ordinal : ()I
    //   16: iaload
    //   17: istore #6
    //   19: iconst_1
    //   20: istore #7
    //   22: iconst_0
    //   23: istore #8
    //   25: iload #6
    //   27: iconst_1
    //   28: if_icmpeq -> 192
    //   31: iload #6
    //   33: iconst_2
    //   34: if_icmpeq -> 163
    //   37: iload #6
    //   39: iconst_3
    //   40: if_icmpeq -> 46
    //   43: goto -> 186
    //   46: aload_1
    //   47: getfield activities : Ljava/util/List;
    //   50: invokeinterface size : ()I
    //   55: iconst_1
    //   56: isub
    //   57: istore #6
    //   59: iload #6
    //   61: iflt -> 97
    //   64: aload_1
    //   65: getfield activities : Ljava/util/List;
    //   68: iload #6
    //   70: invokeinterface get : (I)Ljava/lang/Object;
    //   75: checkcast com/lody/virtual/server/am/ActivityRecord
    //   78: getfield component : Landroid/content/ComponentName;
    //   81: aload_2
    //   82: invokevirtual equals : (Ljava/lang/Object;)Z
    //   85: ifeq -> 91
    //   88: goto -> 100
    //   91: iinc #6, -1
    //   94: goto -> 59
    //   97: iconst_m1
    //   98: istore #6
    //   100: iload #6
    //   102: iflt -> 186
    //   105: iload #6
    //   107: istore #9
    //   109: iload #4
    //   111: ifeq -> 120
    //   114: iload #6
    //   116: iconst_1
    //   117: iadd
    //   118: istore #9
    //   120: iload #7
    //   122: istore #4
    //   124: iload #9
    //   126: aload_1
    //   127: getfield activities : Ljava/util/List;
    //   130: invokeinterface size : ()I
    //   135: if_icmpge -> 234
    //   138: aload_1
    //   139: getfield activities : Ljava/util/List;
    //   142: iload #9
    //   144: invokeinterface get : (I)Ljava/lang/Object;
    //   149: checkcast com/lody/virtual/server/am/ActivityRecord
    //   152: iconst_1
    //   153: putfield marked : Z
    //   156: iload #9
    //   158: istore #6
    //   160: goto -> 114
    //   163: aload_0
    //   164: aload_1
    //   165: aload_2
    //   166: invokespecial findActivityByComponentName : (Lcom/lody/virtual/server/am/TaskRecord;Landroid/content/ComponentName;)Lcom/lody/virtual/server/am/ActivityRecord;
    //   169: astore_1
    //   170: aload_1
    //   171: ifnull -> 186
    //   174: aload_1
    //   175: iconst_1
    //   176: putfield marked : Z
    //   179: iload #7
    //   181: istore #4
    //   183: goto -> 234
    //   186: iconst_0
    //   187: istore #4
    //   189: goto -> 234
    //   192: aload_1
    //   193: getfield activities : Ljava/util/List;
    //   196: invokeinterface iterator : ()Ljava/util/Iterator;
    //   201: astore_1
    //   202: iload #8
    //   204: istore #4
    //   206: aload_1
    //   207: invokeinterface hasNext : ()Z
    //   212: ifeq -> 234
    //   215: aload_1
    //   216: invokeinterface next : ()Ljava/lang/Object;
    //   221: checkcast com/lody/virtual/server/am/ActivityRecord
    //   224: iconst_1
    //   225: putfield marked : Z
    //   228: iconst_1
    //   229: istore #4
    //   231: goto -> 206
    //   234: aload #5
    //   236: monitorexit
    //   237: iload #4
    //   239: ireturn
    //   240: astore_1
    //   241: aload #5
    //   243: monitorexit
    //   244: aload_1
    //   245: athrow
    // Exception table:
    //   from	to	target	type
    //   9	19	240	finally
    //   46	59	240	finally
    //   64	88	240	finally
    //   124	156	240	finally
    //   163	170	240	finally
    //   174	179	240	finally
    //   192	202	240	finally
    //   206	228	240	finally
    //   234	237	240	finally
    //   241	244	240	finally
  }
  
  void processDied(ProcessRecord paramProcessRecord) {
    synchronized (this.mHistory) {
      optimizeTasksLocked();
      int i = this.mHistory.size();
      while (true) {
        int j = i - 1;
        if (i > 0) {
          TaskRecord taskRecord = (TaskRecord)this.mHistory.valueAt(j);
          synchronized (taskRecord.activities) {
            Iterator<ActivityRecord> iterator = taskRecord.activities.iterator();
            while (iterator.hasNext()) {
              ActivityRecord activityRecord = iterator.next();
              if (!activityRecord.started || (activityRecord.process != null && activityRecord.process.pid != paramProcessRecord.pid))
                continue; 
              iterator.remove();
              if (taskRecord.activities.isEmpty())
                this.mHistory.remove(taskRecord.taskId); 
            } 
            i = j;
          } 
          continue;
        } 
        return;
      } 
    } 
  }
  
  int startActivitiesLocked(int paramInt, Intent[] paramArrayOfIntent, ActivityInfo[] paramArrayOfActivityInfo, IBinder paramIBinder, Bundle paramBundle) {
    for (byte b = 0; b < paramArrayOfIntent.length; b++)
      startActivityLocked(paramInt, paramArrayOfIntent[b], paramArrayOfActivityInfo[b], paramIBinder, paramBundle, null, 0); 
    return 0;
  }
  
  public int startActivityFromHistoryLocked(Intent paramIntent) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("startActivityFromHistory: ");
    stringBuilder.append(paramIntent);
    VLog.e("ActivityStack", stringBuilder.toString());
    synchronized (this.mHistory) {
      ShadowActivityInfo shadowActivityInfo = new ShadowActivityInfo();
      this(paramIntent);
      ActivityRecord activityRecord2 = (ActivityRecord)shadowActivityInfo.virtualToken;
      if (activityRecord2 == null || !this.mPendingLaunchActivities.contains(activityRecord2)) {
        VLog.e("ActivityStack", "record not in pending list.");
        return -1;
      } 
      if (activityRecord2.task == null) {
        VirtualCore.get().getContext().startActivity(paramIntent);
        return 0;
      } 
      ActivityRecord activityRecord3 = findActivityByToken(activityRecord2.userId, activityRecord2.resultTo);
      if (activityRecord3 != null) {
        ActivityRecord activityRecord = activityRecord3;
        if (activityRecord3.task != activityRecord2.task) {
          activityRecord = activityRecord2.task.getTopActivityRecord();
          return startActivityFromSourceTask(activityRecord.process, activityRecord.token, paramIntent, activityRecord2.resultWho, activityRecord2.requestCode, activityRecord2.options);
        } 
        return startActivityFromSourceTask(activityRecord.process, activityRecord.token, paramIntent, activityRecord2.resultWho, activityRecord2.requestCode, activityRecord2.options);
      } 
      ActivityRecord activityRecord1 = activityRecord2.task.getTopActivityRecord();
      return startActivityFromSourceTask(activityRecord1.process, activityRecord1.token, paramIntent, activityRecord2.resultWho, activityRecord2.requestCode, activityRecord2.options);
    } 
  }
  
  int startActivityLocked(int paramInt1, Intent paramIntent, ActivityInfo paramActivityInfo, IBinder paramIBinder, Bundle paramBundle, String paramString, int paramInt2) {
    synchronized (this.mHistory) {
      boolean bool3;
      boolean bool5;
      optimizeTasksLocked();
      ActivityRecord activityRecord = findActivityByToken(paramInt1, paramIBinder);
      if (paramIBinder != null && activityRecord == null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not found source record: ");
        stringBuilder.append(paramIBinder);
        VLog.e("ActivityStack", stringBuilder.toString());
      } 
      if (activityRecord != null) {
        TaskRecord taskRecord = activityRecord.task;
      } else {
        paramIBinder = null;
        null = null;
      } 
      String str = ComponentUtils.getTaskAffinity(paramActivityInfo);
      boolean bool1 = containFlags(paramIntent, 268435456);
      boolean bool2 = containFlags(paramIntent, 67108864);
      if (bool1 && containFlags(paramIntent, 32768)) {
        bool3 = true;
      } else {
        bool3 = false;
      } 
      if (bool1 && containFlags(paramIntent, 134217728)) {
        boolean bool = true;
      } else {
        boolean bool = false;
      } 
      boolean bool4 = containFlags(paramIntent, 536870912);
      if (containFlags(paramIntent, 131072) && !bool2) {
        bool5 = true;
      } else {
        bool5 = false;
      } 
      boolean bool6 = containFlags(paramIntent, 33554432);
      int i = paramActivityInfo.launchMode;
      int j = paramActivityInfo.documentLaunchMode;
      int k = paramActivityInfo.flags;
      int m = 8388608;
      int n = m;
      if ((k & 0x20) == 0)
        if (containFlags(paramIntent, 8388608)) {
          n = m;
        } else {
          n = 0;
        }  
      m = n;
      if (containFlags(paramIntent, 65536))
        m = n | 0x10000; 
      n = m;
      if (containFlags(paramIntent, 8192))
        n = m | 0x2000; 
      if (i == 1 || i == 2 || i == 3)
        bool4 = true; 
      if (bool6) {
        ActivityRecord activityRecord1;
        int i1;
        IBinder iBinder1;
        ComponentName componentName;
        if (activityRecord != null && activityRecord.resultTo != null) {
          ActivityRecord activityRecord3 = findActivityByToken(paramInt1, activityRecord.resultTo);
          if (activityRecord3 != null) {
            n |= 0x2000000;
            iBinder1 = activityRecord3.token;
          } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("forwardResult failed: ");
            stringBuilder.append(paramIntent);
            VLog.e("ActivityStack", stringBuilder.toString());
            iBinder1 = paramIBinder;
          } 
        } else {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("forwardResult failed: ");
          stringBuilder.append(paramIntent);
          VLog.e("ActivityStack", stringBuilder.toString());
          iBinder1 = paramIBinder;
        } 
        if (paramActivityInfo.targetActivity != null) {
          componentName = new ComponentName(paramActivityInfo.packageName, paramActivityInfo.targetActivity);
        } else {
          componentName = new ComponentName(paramActivityInfo.packageName, paramActivityInfo.name);
        } 
        m = n;
        if (j == 2) {
          i1 = 0;
          n = 1;
        } else if ((j == 1 || containFlags(paramIntent.getFlags(), 524288)) && bool1) {
          n = i1;
          i1 = 1;
        } else {
          j = 0;
          n = i1;
          i1 = j;
        } 
        if (n == 0) {
          if (i1 != 0) {
            TaskRecord taskRecord = findTaskByIntentLocked(paramInt1, paramIntent);
          } else {
            if (!bool1 && activityRecord != null && (paramInt2 >= 0 || (activityRecord.info.launchMode != 3 && (i == 0 || i == 1)))) {
              SparseArray<TaskRecord> sparseArray = null;
            } else {
              paramIBinder = null;
            } 
            if (paramIBinder == null)
              if (i == 3) {
                TaskRecord taskRecord = findTaskByComponentLocked(paramInt1, componentName);
              } else {
                TaskRecord taskRecord = findTaskByAffinityLocked(paramInt1, str);
              }  
          } 
        } else {
          paramIBinder = null;
        } 
        if (paramIBinder == null || paramIBinder.isFinishing())
          return startActivityInNewTaskLocked(m, paramInt1, paramIntent, paramActivityInfo, paramBundle); 
        try {
          this.mAM.moveTaskToFront(((TaskRecord)paramIBinder).taskId, 0);
        } catch (Exception exception) {
          exception.printStackTrace();
        } 
        if (i == 0 && !bool4 && !bool3 && !bool2 && !bool5 && paramInt2 <= 0 && iBinder1 == null) {
          bool1 = ComponentUtils.intentFilterEquals(((TaskRecord)paramIBinder).taskRoot, paramIntent);
        } else {
          bool1 = false;
        } 
        Intent intent2 = paramIntent;
        if (bool1)
          return 0; 
        ClearTaskAction clearTaskAction = ClearTaskAction.NONE;
        if (i == 2 || i == 3 || bool2)
          clearTaskAction = ClearTaskAction.TOP; 
        if (bool5)
          clearTaskAction = ClearTaskAction.ACTIVITY; 
        if (bool3)
          clearTaskAction = ClearTaskAction.TASK; 
        if (bool4) {
          if (clearTaskAction == ClearTaskAction.TOP) {
            bool4 = performClearTaskLocked((TaskRecord)paramIBinder, componentName, ClearTaskAction.TOP, true);
          } else if (clearTaskAction == ClearTaskAction.NONE) {
            bool4 = true;
          } else {
            bool4 = false;
          } 
          if (bool4) {
            activityRecord1 = paramIBinder.getTopActivityRecord();
            if (activityRecord1 != null && activityRecord1.component.equals(componentName)) {
              deliverNewIntentLocked(activityRecord, activityRecord1, intent2);
              finishMarkedActivity();
              return 0;
            } 
          } 
          clearTaskAction = ClearTaskAction.NONE;
        } else {
          bool4 = false;
        } 
        if (bool4)
          finishMarkedActivity(); 
        ActivityRecord activityRecord2 = newActivityRecord(paramInt1, intent2, paramActivityInfo, iBinder1);
        activityRecord2.requestCode = paramInt2;
        activityRecord2.resultWho = paramString;
        activityRecord2.options = paramBundle;
        activityRecord2.pendingClearAction = clearTaskAction;
        activityRecord2.task = (TaskRecord)paramIBinder;
        Intent intent1 = startActivityProcess(paramInt1, activityRecord2, intent2, paramActivityInfo);
        if (intent1 == null)
          return -1; 
        ((TaskRecord)paramIBinder).activities.add(activityRecord2);
        this.mPendingLaunchActivities.add(activityRecord2);
        intent1.addFlags(m);
        if (null == paramIBinder) {
          activityRecord1 = activityRecord;
        } else {
          activityRecord1 = paramIBinder.getTopActivityRecord(false);
        } 
        if (activityRecord1 == null || activityRecord1.process == null)
          return -1; 
        activityRecord2.started = true;
        return startActivityFromSourceTask(activityRecord1.process, activityRecord1.token, intent1, paramString, paramInt2, paramBundle);
      } 
      IBinder iBinder = paramIBinder;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\am\ActivityStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */