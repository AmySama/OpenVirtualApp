package com.lody.virtual.client.stub;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.lody.virtual.client.core.VirtualCore;

public class KeepAliveService extends Service {
  public IBinder onBind(Intent paramIntent) {
    return null;
  }
  
  public void onCreate() {
    super.onCreate();
    if (!VirtualCore.getConfig().isHideForegroundNotification())
      HiddenForeNotification.bindForeground(this); 
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
    return 1;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\KeepAliveService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */