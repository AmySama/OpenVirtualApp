package com.lody.virtual.server.notification;

import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;

class NotificationCompatCompatV14 extends NotificationCompat {
  private final RemoteViewsFixer mRemoteViewsFixer = new RemoteViewsFixer(this);
  
  private RemoteViewsFixer getRemoteViewsFixer() {
    return this.mRemoteViewsFixer;
  }
  
  public boolean dealNotification(int paramInt, Notification paramNotification, String paramString) {
    Context context = getAppContext(paramString);
    if (context == null)
      return false; 
    if (VClient.get().isDynamicApp() && VirtualCore.get().isOutsideInstalled(paramString)) {
      if (paramNotification.icon != 0) {
        getNotificationFixer().fixIconImage(context.getResources(), paramNotification.contentView, false, paramNotification);
        if (Build.VERSION.SDK_INT >= 16)
          getNotificationFixer().fixIconImage(context.getResources(), paramNotification.bigContentView, false, paramNotification); 
        paramNotification.icon = (getHostContext().getApplicationInfo()).icon;
      } 
      return true;
    } 
    remakeRemoteViews(paramInt, paramNotification, context);
    if (paramNotification.icon != 0)
      paramNotification.icon = (getHostContext().getApplicationInfo()).icon; 
    return true;
  }
  
  Context getAppContext(String paramString) {
    try {
      Context context = getHostContext().createPackageContext(paramString, 3);
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      nameNotFoundException.printStackTrace();
      nameNotFoundException = null;
    } 
    return (Context)nameNotFoundException;
  }
  
  protected void remakeRemoteViews(int paramInt, Notification paramNotification, Context paramContext) {
    if (paramNotification.tickerView != null)
      if (isSystemLayout(paramNotification.tickerView)) {
        getNotificationFixer().fixRemoteViewActions(paramContext, false, paramNotification.tickerView);
      } else {
        RemoteViewsFixer remoteViewsFixer = getRemoteViewsFixer();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paramInt);
        stringBuilder.append(":tickerView");
        paramNotification.tickerView = remoteViewsFixer.makeRemoteViews(stringBuilder.toString(), paramContext, paramNotification.tickerView, false, false);
      }  
    if (paramNotification.contentView != null)
      if (isSystemLayout(paramNotification.contentView)) {
        boolean bool = getNotificationFixer().fixRemoteViewActions(paramContext, false, paramNotification.contentView);
        getNotificationFixer().fixIconImage(paramContext.getResources(), paramNotification.contentView, bool, paramNotification);
      } else {
        RemoteViewsFixer remoteViewsFixer = getRemoteViewsFixer();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paramInt);
        stringBuilder.append(":contentView");
        paramNotification.contentView = remoteViewsFixer.makeRemoteViews(stringBuilder.toString(), paramContext, paramNotification.contentView, false, true);
      }  
    if (Build.VERSION.SDK_INT >= 16 && paramNotification.bigContentView != null)
      if (isSystemLayout(paramNotification.bigContentView)) {
        getNotificationFixer().fixRemoteViewActions(paramContext, false, paramNotification.bigContentView);
      } else {
        RemoteViewsFixer remoteViewsFixer = getRemoteViewsFixer();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paramInt);
        stringBuilder.append(":bigContentView");
        paramNotification.bigContentView = remoteViewsFixer.makeRemoteViews(stringBuilder.toString(), paramContext, paramNotification.bigContentView, true, true);
      }  
    if (Build.VERSION.SDK_INT >= 21 && paramNotification.headsUpContentView != null)
      if (isSystemLayout(paramNotification.headsUpContentView)) {
        boolean bool = getNotificationFixer().fixRemoteViewActions(paramContext, false, paramNotification.headsUpContentView);
        getNotificationFixer().fixIconImage(paramContext.getResources(), paramNotification.contentView, bool, paramNotification);
      } else {
        RemoteViewsFixer remoteViewsFixer = getRemoteViewsFixer();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paramInt);
        stringBuilder.append(":headsUpContentView");
        paramNotification.headsUpContentView = remoteViewsFixer.makeRemoteViews(stringBuilder.toString(), paramContext, paramNotification.headsUpContentView, false, false);
      }  
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\notification\NotificationCompatCompatV14.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */