package com.lody.virtual.remote;

import android.content.Intent;
import android.os.Parcelable;

public class BroadcastIntentData {
  public Intent intent;
  
  public String targetPackage;
  
  public int userId;
  
  public BroadcastIntentData(int paramInt, Intent paramIntent, String paramString) {
    this.userId = paramInt;
    this.intent = paramIntent;
    this.targetPackage = paramString;
  }
  
  public BroadcastIntentData(Intent paramIntent) {
    this.userId = paramIntent.getIntExtra("_VA_|_user_id_", -1);
    this.intent = (Intent)paramIntent.getParcelableExtra("_VA_|_intent_");
    this.targetPackage = paramIntent.getStringExtra("_VA_|_target_pkg_");
  }
  
  public void saveIntent(Intent paramIntent) {
    paramIntent.putExtra("_VA_|_user_id_", this.userId);
    paramIntent.putExtra("_VA_|_intent_", (Parcelable)this.intent);
    paramIntent.putExtra("_VA_|_target_pkg_", this.targetPackage);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\BroadcastIntentData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */