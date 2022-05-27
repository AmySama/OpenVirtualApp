package com.lody.virtual.client.stub;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import com.lody.virtual.R;
import com.lody.virtual.helper.compat.NotificationChannelCompat;
import com.stub.StubApp;

public class HiddenForeNotification extends Service {
  private static final int ID = 2781;
  
  public static void bindForeground(Service paramService) {
    Notification.Builder builder = NotificationChannelCompat.createBuilder(StubApp.getOrigApplicationContext(paramService.getApplicationContext()), NotificationChannelCompat.DAEMON_ID);
    builder.setSmallIcon(17301544);
    if (Build.VERSION.SDK_INT > 24) {
      builder.setContentTitle(paramService.getString(R.string.keep_service_damon_noti_title_v24));
      builder.setContentText(paramService.getString(R.string.keep_service_damon_noti_text_v24));
    } else {
      builder.setContentTitle(paramService.getString(R.string.keep_service_damon_noti_title));
      builder.setContentText(paramService.getString(R.string.keep_service_damon_noti_text));
      builder.setContentIntent(PendingIntent.getService((Context)paramService, 0, new Intent((Context)paramService, HiddenForeNotification.class), 0));
    } 
    builder.setSound(null);
    paramService.startForeground(2781, builder.getNotification());
    if (Build.VERSION.SDK_INT <= 24)
      paramService.startService(new Intent((Context)paramService, HiddenForeNotification.class)); 
  }
  
  public IBinder onBind(Intent paramIntent) {
    return null;
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
    try {
      Notification.Builder builder = NotificationChannelCompat.createBuilder(getBaseContext(), NotificationChannelCompat.DAEMON_ID);
      builder.setSmallIcon(17301544);
      builder.setContentTitle(getString(R.string.keep_service_noti_title));
      builder.setContentText(getString(R.string.keep_service_noti_text));
      builder.setSound(null);
      startForeground(2781, builder.getNotification());
      stopForeground(true);
      stopSelf();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return 2;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\HiddenForeNotification.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */