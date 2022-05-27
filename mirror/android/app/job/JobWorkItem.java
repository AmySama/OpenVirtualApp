package mirror.android.app.job;

import android.content.Intent;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefConstructor;
import mirror.RefInt;
import mirror.RefMethod;
import mirror.RefObject;

public class JobWorkItem {
  public static Class<?> TYPE = RefClass.load(JobWorkItem.class, android.app.job.JobWorkItem.class);
  
  @MethodParams({Intent.class})
  public static RefConstructor<Object> ctor;
  
  public static RefMethod<Intent> getIntent;
  
  public static RefInt mDeliveryCount;
  
  public static RefObject<Object> mGrants;
  
  public static RefInt mWorkId;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\job\JobWorkItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */