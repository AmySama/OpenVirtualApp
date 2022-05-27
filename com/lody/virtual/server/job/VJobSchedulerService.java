package com.lody.virtual.server.job;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.text.TextUtils;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VJobScheduler;
import com.lody.virtual.client.stub.StubManifest;
import com.lody.virtual.helper.utils.Singleton;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.remote.VJobWorkItem;
import com.lody.virtual.server.interfaces.IJobService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import mirror.RefObject;
import mirror.android.app.job.JobInfo;

public class VJobSchedulerService extends IJobService.Stub {
  private static final int JOB_FILE_VERSION = 1;
  
  private static final String TAG = VJobScheduler.class.getSimpleName();
  
  private static final Singleton<VJobSchedulerService> gDefault = new Singleton<VJobSchedulerService>() {
      protected VJobSchedulerService create() {
        return new VJobSchedulerService();
      }
    };
  
  private final ComponentName mJobProxyComponent = new ComponentName(VirtualCore.get().getHostPkg(), StubManifest.STUB_JOB);
  
  private final Map<JobId, JobConfig> mJobStore = new HashMap<JobId, JobConfig>();
  
  private int mNextJobId = 1;
  
  private final JobScheduler mScheduler = (JobScheduler)VirtualCore.get().getContext().getSystemService("jobscheduler");
  
  private VJobSchedulerService() {
    readJobs();
  }
  
  public static VJobSchedulerService get() {
    return (VJobSchedulerService)gDefault.get();
  }
  
  private void readJobs() {
    File file = VEnvironment.getJobConfigFile();
    if (!file.exists())
      return; 
    Parcel parcel = Parcel.obtain();
    try {
      FileInputStream fileInputStream = new FileInputStream();
      this(file);
      int i = (int)file.length();
      byte[] arrayOfByte = new byte[i];
      int j = fileInputStream.read(arrayOfByte);
      fileInputStream.close();
      if (j == i) {
        j = 0;
        parcel.unmarshall(arrayOfByte, 0, i);
        parcel.setDataPosition(0);
        i = parcel.readInt();
        if (i == 1) {
          if (!this.mJobStore.isEmpty())
            this.mJobStore.clear(); 
          int k = parcel.readInt();
          i = 0;
          while (j < k) {
            JobId jobId = new JobId();
            this(parcel);
            JobConfig jobConfig = new JobConfig();
            this(parcel);
            this.mJobStore.put(jobId, jobConfig);
            i = Math.max(i, jobConfig.virtualJobId);
            j++;
          } 
          this.mNextJobId = i + 1;
        } else {
          IOException iOException = new IOException();
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("Bad version of job file: ");
          stringBuilder.append(i);
          this(stringBuilder.toString());
          throw iOException;
        } 
      } else {
        IOException iOException = new IOException();
        this("Unable to read job config.");
        throw iOException;
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      Exception exception;
    } 
    parcel.recycle();
  }
  
  private void saveJobs() {
    File file = VEnvironment.getJobConfigFile();
    Parcel parcel = Parcel.obtain();
    try {
      parcel.writeInt(1);
      parcel.writeInt(this.mJobStore.size());
      for (Map.Entry<JobId, JobConfig> entry : this.mJobStore.entrySet()) {
        ((JobId)entry.getKey()).writeToParcel(parcel, 0);
        ((JobConfig)entry.getValue()).writeToParcel(parcel, 0);
      } 
      FileOutputStream fileOutputStream = new FileOutputStream();
      this(file);
      fileOutputStream.write(parcel.marshall());
      fileOutputStream.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {}
    parcel.recycle();
  }
  
  public void cancel(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mJobStore : Ljava/util/Map;
    //   4: astore_3
    //   5: aload_3
    //   6: monitorenter
    //   7: iconst_0
    //   8: istore #4
    //   10: aload_0
    //   11: getfield mJobStore : Ljava/util/Map;
    //   14: invokeinterface entrySet : ()Ljava/util/Set;
    //   19: invokeinterface iterator : ()Ljava/util/Iterator;
    //   24: astore #5
    //   26: iload #4
    //   28: istore #6
    //   30: aload #5
    //   32: invokeinterface hasNext : ()Z
    //   37: ifeq -> 121
    //   40: aload #5
    //   42: invokeinterface next : ()Ljava/lang/Object;
    //   47: checkcast java/util/Map$Entry
    //   50: astore #7
    //   52: aload #7
    //   54: invokeinterface getKey : ()Ljava/lang/Object;
    //   59: checkcast com/lody/virtual/server/job/VJobSchedulerService$JobId
    //   62: astore #8
    //   64: aload #7
    //   66: invokeinterface getValue : ()Ljava/lang/Object;
    //   71: checkcast com/lody/virtual/server/job/VJobSchedulerService$JobConfig
    //   74: astore #7
    //   76: iload_1
    //   77: iconst_m1
    //   78: if_icmpeq -> 90
    //   81: aload #8
    //   83: getfield vuid : I
    //   86: iload_1
    //   87: if_icmpne -> 26
    //   90: aload #8
    //   92: getfield clientJobId : I
    //   95: iload_2
    //   96: if_icmpne -> 26
    //   99: iconst_1
    //   100: istore #6
    //   102: aload_0
    //   103: getfield mScheduler : Landroid/app/job/JobScheduler;
    //   106: aload #7
    //   108: getfield virtualJobId : I
    //   111: invokevirtual cancel : (I)V
    //   114: aload #5
    //   116: invokeinterface remove : ()V
    //   121: iload #6
    //   123: ifeq -> 130
    //   126: aload_0
    //   127: invokespecial saveJobs : ()V
    //   130: aload_3
    //   131: monitorexit
    //   132: return
    //   133: astore #5
    //   135: aload_3
    //   136: monitorexit
    //   137: aload #5
    //   139: athrow
    // Exception table:
    //   from	to	target	type
    //   10	26	133	finally
    //   30	76	133	finally
    //   81	90	133	finally
    //   90	99	133	finally
    //   102	121	133	finally
    //   126	130	133	finally
    //   130	132	133	finally
    //   135	137	133	finally
  }
  
  public void cancelAll(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mJobStore : Ljava/util/Map;
    //   4: astore_2
    //   5: aload_2
    //   6: monitorenter
    //   7: iconst_0
    //   8: istore_3
    //   9: aload_0
    //   10: getfield mJobStore : Ljava/util/Map;
    //   13: invokeinterface entrySet : ()Ljava/util/Set;
    //   18: invokeinterface iterator : ()Ljava/util/Iterator;
    //   23: astore #4
    //   25: iload_3
    //   26: istore #5
    //   28: aload #4
    //   30: invokeinterface hasNext : ()Z
    //   35: ifeq -> 101
    //   38: aload #4
    //   40: invokeinterface next : ()Ljava/lang/Object;
    //   45: checkcast java/util/Map$Entry
    //   48: astore #6
    //   50: aload #6
    //   52: invokeinterface getKey : ()Ljava/lang/Object;
    //   57: checkcast com/lody/virtual/server/job/VJobSchedulerService$JobId
    //   60: getfield vuid : I
    //   63: iload_1
    //   64: if_icmpne -> 25
    //   67: aload #6
    //   69: invokeinterface getValue : ()Ljava/lang/Object;
    //   74: checkcast com/lody/virtual/server/job/VJobSchedulerService$JobConfig
    //   77: astore #6
    //   79: aload_0
    //   80: getfield mScheduler : Landroid/app/job/JobScheduler;
    //   83: aload #6
    //   85: getfield virtualJobId : I
    //   88: invokevirtual cancel : (I)V
    //   91: iconst_1
    //   92: istore #5
    //   94: aload #4
    //   96: invokeinterface remove : ()V
    //   101: iload #5
    //   103: ifeq -> 110
    //   106: aload_0
    //   107: invokespecial saveJobs : ()V
    //   110: aload_2
    //   111: monitorexit
    //   112: return
    //   113: astore #4
    //   115: aload_2
    //   116: monitorexit
    //   117: aload #4
    //   119: athrow
    // Exception table:
    //   from	to	target	type
    //   9	25	113	finally
    //   28	91	113	finally
    //   94	101	113	finally
    //   106	110	113	finally
    //   110	112	113	finally
    //   115	117	113	finally
  }
  
  public int enqueue(int paramInt, JobInfo paramJobInfo, VJobWorkItem paramVJobWorkItem) {
    if (paramVJobWorkItem.get() == null)
      return -1; 
    int i = paramJobInfo.getId();
    ComponentName componentName = paramJobInfo.getService();
    JobId jobId = new JobId(paramInt, componentName.getPackageName(), i);
    synchronized (this.mJobStore) {
      JobConfig jobConfig1 = this.mJobStore.get(jobId);
      JobConfig jobConfig2 = jobConfig1;
      if (jobConfig1 == null) {
        paramInt = this.mNextJobId;
        this.mNextJobId++;
        jobConfig2 = new JobConfig();
        this(paramInt, componentName.getClassName(), paramJobInfo.getExtras());
        this.mJobStore.put(jobId, jobConfig2);
      } 
      jobConfig2.serviceName = componentName.getClassName();
      jobConfig2.extras = paramJobInfo.getExtras();
      saveJobs();
      JobInfo.jobId.set(paramJobInfo, jobConfig2.virtualJobId);
      JobInfo.service.set(paramJobInfo, this.mJobProxyComponent);
      return this.mScheduler.enqueue(paramJobInfo, paramVJobWorkItem.get());
    } 
  }
  
  public Map.Entry<JobId, JobConfig> findJobByVirtualJobId(int paramInt) {
    synchronized (this.mJobStore) {
      for (Map.Entry<JobId, JobConfig> entry : this.mJobStore.entrySet()) {
        if (((JobConfig)entry.getValue()).virtualJobId == paramInt)
          return entry; 
      } 
      return null;
    } 
  }
  
  public List<JobInfo> getAllPendingJobs(int paramInt) {
    null = this.mScheduler.getAllPendingJobs();
    synchronized (this.mJobStore) {
      ListIterator<JobInfo> listIterator = null.listIterator();
      while (listIterator.hasNext()) {
        JobInfo jobInfo = listIterator.next();
        if (!StubManifest.STUB_JOB.equals(jobInfo.getService().getClassName())) {
          listIterator.remove();
          continue;
        } 
        Map.Entry<JobId, JobConfig> entry = findJobByVirtualJobId(jobInfo.getId());
        if (entry == null) {
          listIterator.remove();
          continue;
        } 
        JobId jobId = entry.getKey();
        JobConfig jobConfig = entry.getValue();
        if (jobId.vuid != paramInt) {
          listIterator.remove();
          continue;
        } 
        JobInfo.jobId.set(jobInfo, jobId.clientJobId);
        RefObject refObject = JobInfo.service;
        ComponentName componentName = new ComponentName();
        this(jobId.packageName, jobConfig.serviceName);
        refObject.set(jobInfo, componentName);
      } 
      return null;
    } 
  }
  
  public JobInfo getPendingJob(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mJobStore : Ljava/util/Map;
    //   4: astore_3
    //   5: aload_3
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield mJobStore : Ljava/util/Map;
    //   11: invokeinterface entrySet : ()Ljava/util/Set;
    //   16: invokeinterface iterator : ()Ljava/util/Iterator;
    //   21: astore #4
    //   23: aload #4
    //   25: invokeinterface hasNext : ()Z
    //   30: ifeq -> 88
    //   33: aload #4
    //   35: invokeinterface next : ()Ljava/lang/Object;
    //   40: checkcast java/util/Map$Entry
    //   43: invokeinterface getKey : ()Ljava/lang/Object;
    //   48: checkcast com/lody/virtual/server/job/VJobSchedulerService$JobId
    //   51: astore #5
    //   53: aload #5
    //   55: getfield vuid : I
    //   58: iload_1
    //   59: if_icmpne -> 23
    //   62: aload #5
    //   64: getfield clientJobId : I
    //   67: iload_2
    //   68: if_icmpne -> 23
    //   71: aload_0
    //   72: getfield mScheduler : Landroid/app/job/JobScheduler;
    //   75: aload #5
    //   77: getfield clientJobId : I
    //   80: invokevirtual getPendingJob : (I)Landroid/app/job/JobInfo;
    //   83: astore #5
    //   85: goto -> 91
    //   88: aconst_null
    //   89: astore #5
    //   91: aload_3
    //   92: monitorexit
    //   93: aload #5
    //   95: areturn
    //   96: astore #5
    //   98: aload_3
    //   99: monitorexit
    //   100: aload #5
    //   102: athrow
    // Exception table:
    //   from	to	target	type
    //   7	23	96	finally
    //   23	85	96	finally
    //   91	93	96	finally
    //   98	100	96	finally
  }
  
  public int schedule(int paramInt, JobInfo paramJobInfo) {
    int i = paramJobInfo.getId();
    ComponentName componentName = paramJobInfo.getService();
    JobId jobId = new JobId(paramInt, componentName.getPackageName(), i);
    synchronized (this.mJobStore) {
      JobConfig jobConfig1 = this.mJobStore.get(jobId);
      JobConfig jobConfig2 = jobConfig1;
      if (jobConfig1 == null) {
        paramInt = this.mNextJobId;
        this.mNextJobId++;
        jobConfig2 = new JobConfig();
        this(paramInt, componentName.getClassName(), paramJobInfo.getExtras());
        this.mJobStore.put(jobId, jobConfig2);
      } 
      jobConfig2.serviceName = componentName.getClassName();
      jobConfig2.extras = paramJobInfo.getExtras();
      saveJobs();
      JobInfo.jobId.set(paramJobInfo, jobConfig2.virtualJobId);
      JobInfo.service.set(paramJobInfo, this.mJobProxyComponent);
      return this.mScheduler.schedule(paramJobInfo);
    } 
  }
  
  public static final class JobConfig implements Parcelable {
    public static final Parcelable.Creator<JobConfig> CREATOR = new Parcelable.Creator<JobConfig>() {
        public VJobSchedulerService.JobConfig createFromParcel(Parcel param2Parcel) {
          return new VJobSchedulerService.JobConfig(param2Parcel);
        }
        
        public VJobSchedulerService.JobConfig[] newArray(int param2Int) {
          return new VJobSchedulerService.JobConfig[param2Int];
        }
      };
    
    public PersistableBundle extras;
    
    public String serviceName;
    
    public int virtualJobId;
    
    JobConfig(int param1Int, String param1String, PersistableBundle param1PersistableBundle) {
      this.virtualJobId = param1Int;
      this.serviceName = param1String;
      this.extras = param1PersistableBundle;
    }
    
    JobConfig(Parcel param1Parcel) {
      this.virtualJobId = param1Parcel.readInt();
      this.serviceName = param1Parcel.readString();
      this.extras = (PersistableBundle)param1Parcel.readParcelable(PersistableBundle.class.getClassLoader());
    }
    
    public int describeContents() {
      return 0;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeInt(this.virtualJobId);
      param1Parcel.writeString(this.serviceName);
      param1Parcel.writeParcelable((Parcelable)this.extras, param1Int);
    }
  }
  
  class null implements Parcelable.Creator<JobConfig> {
    public VJobSchedulerService.JobConfig createFromParcel(Parcel param1Parcel) {
      return new VJobSchedulerService.JobConfig(param1Parcel);
    }
    
    public VJobSchedulerService.JobConfig[] newArray(int param1Int) {
      return new VJobSchedulerService.JobConfig[param1Int];
    }
  }
  
  public static final class JobId implements Parcelable {
    public static final Parcelable.Creator<JobId> CREATOR = new Parcelable.Creator<JobId>() {
        public VJobSchedulerService.JobId createFromParcel(Parcel param2Parcel) {
          return new VJobSchedulerService.JobId(param2Parcel);
        }
        
        public VJobSchedulerService.JobId[] newArray(int param2Int) {
          return new VJobSchedulerService.JobId[param2Int];
        }
      };
    
    public int clientJobId;
    
    public String packageName;
    
    public int vuid;
    
    JobId(int param1Int1, String param1String, int param1Int2) {
      this.vuid = param1Int1;
      this.packageName = param1String;
      this.clientJobId = param1Int2;
    }
    
    JobId(Parcel param1Parcel) {
      this.vuid = param1Parcel.readInt();
      this.packageName = param1Parcel.readString();
      this.clientJobId = param1Parcel.readInt();
    }
    
    public int describeContents() {
      return 0;
    }
    
    public boolean equals(Object param1Object) {
      boolean bool = true;
      if (this == param1Object)
        return true; 
      if (param1Object == null || getClass() != param1Object.getClass())
        return false; 
      param1Object = param1Object;
      if (this.vuid != ((JobId)param1Object).vuid || this.clientJobId != ((JobId)param1Object).clientJobId || !TextUtils.equals(this.packageName, ((JobId)param1Object).packageName))
        bool = false; 
      return bool;
    }
    
    public int hashCode() {
      byte b;
      int i = this.vuid;
      String str = this.packageName;
      if (str != null) {
        b = str.hashCode();
      } else {
        b = 0;
      } 
      return (i * 31 + b) * 31 + this.clientJobId;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeInt(this.vuid);
      param1Parcel.writeString(this.packageName);
      param1Parcel.writeInt(this.clientJobId);
    }
  }
  
  class null implements Parcelable.Creator<JobId> {
    public VJobSchedulerService.JobId createFromParcel(Parcel param1Parcel) {
      return new VJobSchedulerService.JobId(param1Parcel);
    }
    
    public VJobSchedulerService.JobId[] newArray(int param1Int) {
      return new VJobSchedulerService.JobId[param1Int];
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\job\VJobSchedulerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */