package mirror.android.app.job;

import android.os.IBinder;
import android.os.PersistableBundle;
import mirror.RefClass;
import mirror.RefInt;
import mirror.RefObject;

public class JobParameters {
  public static Class<?> TYPE = RefClass.load(JobParameters.class, android.app.job.JobParameters.class);
  
  public static RefObject<IBinder> callback;
  
  public static RefObject<PersistableBundle> extras;
  
  public static RefInt jobId;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\job\JobParameters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */