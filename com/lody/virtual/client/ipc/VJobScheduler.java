package com.lody.virtual.client.ipc;

import android.app.job.JobInfo;
import android.app.job.JobWorkItem;
import android.os.RemoteException;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.remote.VJobWorkItem;
import com.lody.virtual.server.interfaces.IJobService;
import java.util.List;

public class VJobScheduler {
  private static final VJobScheduler sInstance = new VJobScheduler();
  
  private IJobService mService;
  
  public static VJobScheduler get() {
    return sInstance;
  }
  
  private Object getRemoteInterface() {
    return IJobService.Stub.asInterface(ServiceManagerNative.getService("job"));
  }
  
  public void cancel(int paramInt) {
    try {
      getService().cancel(VClient.get().getVUid(), paramInt);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public void cancelAll() {
    try {
      getService().cancelAll(VClient.get().getVUid());
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public int enqueue(JobInfo paramJobInfo, JobWorkItem paramJobWorkItem) {
    if (paramJobWorkItem == null)
      return -1; 
    if (BuildCompat.isOreo())
      try {
        IJobService iJobService = getService();
        null = VClient.get().getVUid();
        VJobWorkItem vJobWorkItem = new VJobWorkItem();
        this(paramJobWorkItem);
        return iJobService.enqueue(null, paramJobInfo, vJobWorkItem);
      } catch (RemoteException remoteException) {
        return ((Integer)VirtualRuntime.crash((Throwable)remoteException)).intValue();
      }  
    return -1;
  }
  
  public List<JobInfo> getAllPendingJobs() {
    try {
      return getService().getAllPendingJobs(VClient.get().getVUid());
    } catch (RemoteException remoteException) {
      return (List<JobInfo>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public JobInfo getPendingJob(int paramInt) {
    try {
      return getService().getPendingJob(VClient.get().getVUid(), paramInt);
    } catch (RemoteException remoteException) {
      return (JobInfo)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public IJobService getService() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mService : Lcom/lody/virtual/server/interfaces/IJobService;
    //   4: astore_1
    //   5: aload_1
    //   6: ifnull -> 16
    //   9: aload_1
    //   10: invokestatic isAlive : (Landroid/os/IInterface;)Z
    //   13: ifne -> 36
    //   16: aload_0
    //   17: monitorenter
    //   18: aload_0
    //   19: ldc com/lody/virtual/server/interfaces/IJobService
    //   21: aload_0
    //   22: invokespecial getRemoteInterface : ()Ljava/lang/Object;
    //   25: invokestatic genProxy : (Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    //   28: checkcast com/lody/virtual/server/interfaces/IJobService
    //   31: putfield mService : Lcom/lody/virtual/server/interfaces/IJobService;
    //   34: aload_0
    //   35: monitorexit
    //   36: aload_0
    //   37: getfield mService : Lcom/lody/virtual/server/interfaces/IJobService;
    //   40: areturn
    //   41: astore_1
    //   42: aload_0
    //   43: monitorexit
    //   44: aload_1
    //   45: athrow
    // Exception table:
    //   from	to	target	type
    //   18	36	41	finally
    //   42	44	41	finally
  }
  
  public int schedule(JobInfo paramJobInfo) {
    try {
      return getService().schedule(VClient.get().getVUid(), paramJobInfo);
    } catch (RemoteException remoteException) {
      return ((Integer)VirtualRuntime.crash((Throwable)remoteException)).intValue();
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\ipc\VJobScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */