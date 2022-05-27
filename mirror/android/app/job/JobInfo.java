package mirror.android.app.job;

import android.content.ComponentName;
import mirror.RefClass;
import mirror.RefInt;
import mirror.RefObject;

public class JobInfo {
  public static Class<?> TYPE = RefClass.load(JobInfo.class, android.app.job.JobInfo.class);
  
  public static RefInt jobId;
  
  public static RefObject<ComponentName> service;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\job\JobInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */