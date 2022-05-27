package com.lody.virtual.client.stub;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.lody.virtual.helper.utils.ComponentUtils;

public class ShadowPendingService extends Service {
  public IBinder onBind(Intent paramIntent) {
    return null;
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
    stopSelf();
    try {
      ComponentUtils.IntentSenderInfo intentSenderInfo = ComponentUtils.parseIntentSenderInfo(paramIntent, false);
    } finally {
      paramIntent = null;
      paramIntent.printStackTrace();
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\ShadowPendingService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */