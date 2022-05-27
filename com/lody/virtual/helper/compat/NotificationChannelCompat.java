package com.lody.virtual.helper.compat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.Constants;

public class NotificationChannelCompat {
  public static final String DAEMON_ID = Constants.NOTIFICATION_DAEMON_CHANNEL;
  
  public static final String DEFAULT_ID = Constants.NOTIFICATION_CHANNEL;
  
  public static void checkOrCreateChannel(Context paramContext, String paramString1, String paramString2) {
    if (Build.VERSION.SDK_INT >= 26) {
      NotificationManager notificationManager = (NotificationManager)paramContext.getSystemService("notification");
      if (notificationManager.getNotificationChannel(paramString1) == null) {
        NotificationChannel notificationChannel = new NotificationChannel(paramString1, paramString2, 4);
        notificationChannel.setDescription("Compatibility of old versions");
        notificationChannel.setSound(null, null);
        notificationChannel.setShowBadge(false);
      } 
    } 
  }
  
  public static Notification.Builder createBuilder(Context paramContext, String paramString) {
    return (Build.VERSION.SDK_INT >= 26 && VirtualCore.get().getTargetSdkVersion() >= 26) ? new Notification.Builder(paramContext, paramString) : new Notification.Builder(paramContext);
  }
  
  public static boolean enable() {
    int i = Build.VERSION.SDK_INT;
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (i > 26) {
      bool2 = bool1;
      if (VirtualCore.get().getTargetSdkVersion() >= 26)
        bool2 = true; 
    } 
    return bool2;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\NotificationChannelCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */