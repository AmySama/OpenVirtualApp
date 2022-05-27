package com.lody.virtual.client.stub;

import android.app.Service;
import android.app.job.IJobService;
import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.lody.virtual.helper.utils.VLog;

public class ShadowJobService extends Service {
  private final IJobService mService = (IJobService)new IJobService.Stub() {
      public void startJob(JobParameters param1JobParameters) {
        ShadowJobWorkService.startJob((Context)ShadowJobService.this, param1JobParameters);
      }
      
      public void stopJob(JobParameters param1JobParameters) {
        ShadowJobWorkService.stopJob((Context)ShadowJobService.this, param1JobParameters);
      }
    };
  
  public IBinder onBind(Intent paramIntent) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("-> onBind: ");
    stringBuilder.append(paramIntent);
    VLog.e("ShadowJobService", stringBuilder.toString());
    return this.mService.asBinder();
  }
  
  public void onCreate() {
    super.onCreate();
    VLog.e("ShadowJobService", "-> onCreate");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\ShadowJobService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */