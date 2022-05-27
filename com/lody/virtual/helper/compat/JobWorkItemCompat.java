package com.lody.virtual.helper.compat;

import android.app.job.JobWorkItem;
import android.content.Intent;
import com.lody.virtual.helper.utils.ComponentUtils;
import mirror.android.app.job.JobWorkItem;

public class JobWorkItemCompat {
  public static JobWorkItem parse(JobWorkItem paramJobWorkItem) {
    if (paramJobWorkItem != null) {
      if (((Intent)JobWorkItem.getIntent.call(paramJobWorkItem, new Object[0])).hasExtra("_VA_|_intent_"))
        return paramJobWorkItem; 
      Intent intent = (ComponentUtils.parseIntentSenderInfo(paramJobWorkItem.getIntent(), false)).intent;
      ComponentUtils.unpackFillIn(intent, JobWorkItemCompat.class.getClassLoader());
      JobWorkItem jobWorkItem = (JobWorkItem)JobWorkItem.ctor.newInstance(new Object[] { intent });
      int i = JobWorkItem.mWorkId.get(paramJobWorkItem);
      JobWorkItem.mWorkId.set(jobWorkItem, i);
      Object object = JobWorkItem.mGrants.get(paramJobWorkItem);
      JobWorkItem.mGrants.set(jobWorkItem, object);
      i = JobWorkItem.mDeliveryCount.get(paramJobWorkItem);
      JobWorkItem.mDeliveryCount.set(jobWorkItem, i);
      return jobWorkItem;
    } 
    return null;
  }
  
  public static JobWorkItem redirect(int paramInt, JobWorkItem paramJobWorkItem, String paramString) {
    if (paramJobWorkItem != null) {
      Intent intent2 = (Intent)JobWorkItem.getIntent.call(paramJobWorkItem, new Object[0]);
      if (intent2.hasExtra("_VA_|_intent_"))
        return paramJobWorkItem; 
      Intent intent1 = ComponentUtils.getProxyIntentSenderIntent(paramInt, 4, paramString, intent2);
      JobWorkItem jobWorkItem = (JobWorkItem)JobWorkItem.ctor.newInstance(new Object[] { intent1 });
      paramInt = JobWorkItem.mWorkId.get(paramJobWorkItem);
      JobWorkItem.mWorkId.set(jobWorkItem, paramInt);
      Object object = JobWorkItem.mGrants.get(paramJobWorkItem);
      JobWorkItem.mGrants.set(jobWorkItem, object);
      paramInt = JobWorkItem.mDeliveryCount.get(paramJobWorkItem);
      JobWorkItem.mDeliveryCount.set(jobWorkItem, paramInt);
      return jobWorkItem;
    } 
    return null;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\JobWorkItemCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */