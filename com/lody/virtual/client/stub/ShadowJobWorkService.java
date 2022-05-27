package com.lody.virtual.client.stub;

import android.app.Service;
import android.app.job.IJobCallback;
import android.app.job.IJobService;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobWorkItem;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.lody.virtual.client.core.InvocationStubManager;
import com.lody.virtual.client.hook.proxies.am.ActivityManagerStub;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.collection.SparseArray;
import com.lody.virtual.helper.compat.JobWorkItemCompat;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.server.job.VJobSchedulerService;
import com.stub.StubApp;
import java.util.Map;
import mirror.android.app.job.JobParameters;

public class ShadowJobWorkService extends Service {
  private static final String TAG = ShadowJobWorkService.class.getSimpleName();
  
  private static final boolean debug = true;
  
  private final SparseArray<JobSession> mJobSessions = new SparseArray();
  
  private JobScheduler mScheduler;
  
  private void emptyCallback(IJobCallback paramIJobCallback, int paramInt) {
    try {
      paramIJobCallback.acknowledgeStartMessage(paramInt, false);
      paramIJobCallback.jobFinished(paramInt, false);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public static void startJob(Context paramContext, JobParameters paramJobParameters) {
    Intent intent = new Intent(paramContext, ShadowJobWorkService.class);
    intent.setAction("action.startJob");
    intent.putExtra("jobParams", (Parcelable)paramJobParameters);
    paramContext.startService(intent);
  }
  
  public static void stopJob(Context paramContext, JobParameters paramJobParameters) {
    Intent intent = new Intent(paramContext, ShadowJobWorkService.class);
    intent.setAction("action.stopJob");
    intent.putExtra("jobParams", (Parcelable)paramJobParameters);
    paramContext.startService(intent);
  }
  
  public IBinder onBind(Intent paramIntent) {
    return null;
  }
  
  public void onCreate() {
    super.onCreate();
    InvocationStubManager.getInstance().checkEnv(ActivityManagerStub.class);
    this.mScheduler = (JobScheduler)getSystemService("jobscheduler");
  }
  
  public void onDestroy() {
    VLog.i(TAG, "ShadowJobService:onDestroy", new Object[0]);
    synchronized (this.mJobSessions) {
      for (int i = this.mJobSessions.size() - 1; i >= 0; i--)
        ((JobSession)this.mJobSessions.valueAt(i)).stopSessionLocked(); 
      this.mJobSessions.clear();
      super.onDestroy();
      return;
    } 
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
    if (paramIntent != null) {
      String str = paramIntent.getAction();
      if ("action.startJob".equals(str)) {
        startJob((JobParameters)paramIntent.getParcelableExtra("jobParams"));
      } else if ("action.stopJob".equals(str)) {
        stopJob((JobParameters)paramIntent.getParcelableExtra("jobParams"));
      } 
    } 
    return 2;
  }
  
  public void startJob(JobParameters paramJobParameters) {
    int i = paramJobParameters.getJobId();
    IJobCallback iJobCallback = IJobCallback.Stub.asInterface((IBinder)JobParameters.callback.get(paramJobParameters));
    Map.Entry entry = VJobSchedulerService.get().findJobByVirtualJobId(i);
    if (entry == null) {
      emptyCallback(iJobCallback, i);
      this.mScheduler.cancel(i);
    } else {
      VJobSchedulerService.JobId jobId = (VJobSchedulerService.JobId)entry.getKey();
      VJobSchedulerService.JobConfig jobConfig = (VJobSchedulerService.JobConfig)entry.getValue();
      synchronized (this.mJobSessions) {
        JobSession jobSession = (JobSession)this.mJobSessions.get(i);
        if (jobSession != null) {
          jobSession.startJob(true);
        } else {
          synchronized (this.mJobSessions) {
            JobParameters.jobId.set(paramJobParameters, jobId.clientJobId);
            jobSession = new JobSession();
            this(this, i, iJobCallback, paramJobParameters, jobId.packageName);
            JobParameters.callback.set(paramJobParameters, jobSession.asBinder());
            this.mJobSessions.put(i, jobSession);
            Intent intent = new Intent();
            this();
            ComponentName componentName = new ComponentName();
            this(jobId.packageName, jobConfig.serviceName);
            intent.setComponent(componentName);
            boolean bool = false;
            try {
              VLog.i(TAG, "ShadowJobService:binService:%s, jobId=%s", new Object[] { intent.getComponent(), Integer.valueOf(i) });
              boolean bool1 = VActivityManager.get().bindService((Context)this, intent, jobSession, 5, VUserHandle.getUserId(jobId.vuid));
            } finally {
              componentName = null;
            } 
            if (!bool)
              synchronized (this.mJobSessions) {
                this.mJobSessions.remove(i);
                emptyCallback(iJobCallback, i);
                this.mScheduler.cancel(i);
                VJobSchedulerService.get().cancel(-1, i);
              }  
            return;
          } 
        } 
        return;
      } 
    } 
  }
  
  public void stopJob(JobParameters paramJobParameters) {
    int i = paramJobParameters.getJobId();
    synchronized (this.mJobSessions) {
      JobSession jobSession = (JobSession)this.mJobSessions.get(i);
      if (jobSession != null) {
        VLog.i(TAG, "stopJob:%d", new Object[] { Integer.valueOf(i) });
        jobSession.stopSessionLocked();
      } 
      return;
    } 
  }
  
  static {
    StubApp.interface11(6505);
  }
  
  private final class JobSession extends IJobCallback.Stub implements ServiceConnection {
    private IJobCallback clientCallback;
    
    private IJobService clientJobService;
    
    private boolean isWorking;
    
    private int jobId;
    
    private JobParameters jobParams;
    
    private JobWorkItem lastWorkItem;
    
    private String packageName;
    
    JobSession(int param1Int, IJobCallback param1IJobCallback, JobParameters param1JobParameters, String param1String) {
      this.jobId = param1Int;
      this.clientCallback = param1IJobCallback;
      this.jobParams = param1JobParameters;
      this.packageName = param1String;
    }
    
    public void acknowledgeStartMessage(int param1Int, boolean param1Boolean) throws RemoteException {
      this.isWorking = true;
      VLog.i(ShadowJobWorkService.TAG, "ShadowJobService:acknowledgeStartMessage:%d", new Object[] { Integer.valueOf(this.jobId) });
      this.clientCallback.acknowledgeStartMessage(this.jobId, param1Boolean);
    }
    
    public void acknowledgeStopMessage(int param1Int, boolean param1Boolean) throws RemoteException {
      this.isWorking = false;
      VLog.i(ShadowJobWorkService.TAG, "ShadowJobService:acknowledgeStopMessage:%d", new Object[] { Integer.valueOf(this.jobId) });
      this.clientCallback.acknowledgeStopMessage(this.jobId, param1Boolean);
    }
    
    public boolean completeWork(int param1Int1, int param1Int2) throws RemoteException {
      VLog.i(ShadowJobWorkService.TAG, "ShadowJobService:completeWork:%d", new Object[] { Integer.valueOf(this.jobId) });
      return this.clientCallback.completeWork(this.jobId, param1Int2);
    }
    
    public JobWorkItem dequeueWork(int param1Int) throws RemoteException {
      try {
        this.lastWorkItem = null;
        VLog.i(ShadowJobWorkService.TAG, "ShadowJobService:dequeueWork:%d", new Object[] { Integer.valueOf(this.jobId) });
        JobWorkItem jobWorkItem = this.clientCallback.dequeueWork(this.jobId);
        if (jobWorkItem != null) {
          jobWorkItem = JobWorkItemCompat.parse(jobWorkItem);
          this.lastWorkItem = jobWorkItem;
          return jobWorkItem;
        } 
        this.isWorking = false;
        stopSessionLocked();
      } catch (Exception exception) {
        exception.printStackTrace();
        String str = ShadowJobWorkService.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ShadowJobService:dequeueWork:");
        stringBuilder.append(exception);
        VLog.i(str, stringBuilder.toString(), new Object[0]);
      } 
      return null;
    }
    
    void forceFinishJob() {
      try {
        this.clientCallback.jobFinished(this.jobId, false);
        synchronized (ShadowJobWorkService.this.mJobSessions) {
          stopSessionLocked();
        } 
      } catch (RemoteException remoteException) {
        remoteException.printStackTrace();
        synchronized (ShadowJobWorkService.this.mJobSessions) {
          stopSessionLocked();
          return;
        } 
      } finally {
        Exception exception;
      } 
    }
    
    public void jobFinished(int param1Int, boolean param1Boolean) throws RemoteException {
      this.isWorking = false;
      VLog.i(ShadowJobWorkService.TAG, "ShadowJobService:jobFinished:%d", new Object[] { Integer.valueOf(this.jobId) });
      this.clientCallback.jobFinished(this.jobId, param1Boolean);
    }
    
    public void onServiceConnected(ComponentName param1ComponentName, IBinder param1IBinder) {
      VLog.i(ShadowJobWorkService.TAG, "ShadowJobService:onServiceConnected:%s", new Object[] { param1ComponentName });
      this.clientJobService = IJobService.Stub.asInterface(param1IBinder);
      startJob(false);
    }
    
    public void onServiceDisconnected(ComponentName param1ComponentName) {}
    
    public void startJob(boolean param1Boolean) {
      if (this.isWorking) {
        VLog.w(ShadowJobWorkService.TAG, "ShadowJobService:startJob:%d,but is working", new Object[] { Integer.valueOf(this.jobId) });
        return;
      } 
      VLog.i(ShadowJobWorkService.TAG, "ShadowJobService:startJob:%d", new Object[] { Integer.valueOf(this.jobId) });
      IJobService iJobService = this.clientJobService;
      if (iJobService == null) {
        if (!param1Boolean) {
          ShadowJobWorkService.this.emptyCallback(this.clientCallback, this.jobId);
          synchronized (ShadowJobWorkService.this.mJobSessions) {
            stopSessionLocked();
          } 
        } 
        return;
      } 
      try {
        iJobService.startJob(this.jobParams);
      } catch (RemoteException remoteException) {
        forceFinishJob();
        Log.e(ShadowJobWorkService.TAG, "ShadowJobService:startJob", (Throwable)remoteException);
      } 
    }
    
    void stopSessionLocked() {
      VLog.i(ShadowJobWorkService.TAG, "ShadowJobService:stopSession:%d", new Object[] { Integer.valueOf(this.jobId) });
      IJobService iJobService = this.clientJobService;
      if (iJobService != null)
        try {
          iJobService.stopJob(this.jobParams);
        } catch (RemoteException remoteException) {
          remoteException.printStackTrace();
        }  
      ShadowJobWorkService.this.mJobSessions.remove(this.jobId);
      VActivityManager.get().unbindService((Context)ShadowJobWorkService.this, this);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\ShadowJobWorkService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */