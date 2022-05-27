package com.lody.virtual.remote;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.IBinder;
import android.os.Parcelable;
import com.lody.virtual.helper.compat.BundleCompat;

public class ShadowActivityInfo {
  public ActivityInfo info;
  
  public Intent intent;
  
  public int userId;
  
  public IBinder virtualToken;
  
  public ShadowActivityInfo(Intent paramIntent) {
    try {
      this.intent = (Intent)paramIntent.getParcelableExtra("_VA_|_intent_");
      this.info = (ActivityInfo)paramIntent.getParcelableExtra("_VA_|_info_");
      this.userId = paramIntent.getIntExtra("_VA_|_user_id_", -1);
      this.virtualToken = BundleCompat.getBinder(paramIntent, "_VA_|_token_");
    } finally {
      paramIntent = null;
    } 
  }
  
  public ShadowActivityInfo(Intent paramIntent, ActivityInfo paramActivityInfo, int paramInt, IBinder paramIBinder) {
    this.intent = paramIntent;
    this.info = paramActivityInfo;
    this.userId = paramInt;
    this.virtualToken = paramIBinder;
  }
  
  public void saveToIntent(Intent paramIntent) {
    paramIntent.putExtra("_VA_|_intent_", (Parcelable)this.intent);
    paramIntent.putExtra("_VA_|_info_", (Parcelable)this.info);
    paramIntent.putExtra("_VA_|_user_id_", this.userId);
    BundleCompat.putBinder(paramIntent, "_VA_|_token_", this.virtualToken);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\ShadowActivityInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */