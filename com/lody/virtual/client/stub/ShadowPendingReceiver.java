package com.lody.virtual.client.stub;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.lody.virtual.helper.utils.ComponentUtils;

public class ShadowPendingReceiver extends BroadcastReceiver {
  public void onReceive(Context paramContext, Intent paramIntent) {
    try {
      ComponentUtils.IntentSenderInfo intentSenderInfo = ComponentUtils.parseIntentSenderInfo(paramIntent, false);
    } finally {
      paramIntent = null;
      paramIntent.printStackTrace();
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\ShadowPendingReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */