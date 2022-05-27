package com.lody.virtual.server.notification;

import android.app.Notification;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VPackageManager;
import com.lody.virtual.helper.compat.NotificationChannelCompat;
import com.lody.virtual.helper.utils.Reflect;
import com.lody.virtual.helper.utils.VLog;
import mirror.android.app.NotificationO;
import mirror.android.widget.RemoteViews;

class NotificationCompatCompatV21 extends NotificationCompatCompatV14 {
  private static final String TAG = NotificationCompatCompatV21.class.getSimpleName();
  
  private void fixApplicationInfo(RemoteViews paramRemoteViews, ApplicationInfo paramApplicationInfo) {
    if (paramRemoteViews != null)
      RemoteViews.mApplication.set(paramRemoteViews, paramApplicationInfo); 
  }
  
  private ApplicationInfo getApplicationInfo(Notification paramNotification) {
    ApplicationInfo applicationInfo = getApplicationInfo(paramNotification.tickerView);
    if (applicationInfo != null)
      return applicationInfo; 
    applicationInfo = getApplicationInfo(paramNotification.contentView);
    if (applicationInfo != null)
      return applicationInfo; 
    if (Build.VERSION.SDK_INT >= 16) {
      applicationInfo = getApplicationInfo(paramNotification.bigContentView);
      if (applicationInfo != null)
        return applicationInfo; 
    } 
    if (Build.VERSION.SDK_INT >= 21) {
      ApplicationInfo applicationInfo1 = getApplicationInfo(paramNotification.headsUpContentView);
      if (applicationInfo1 != null)
        return applicationInfo1; 
    } 
    return null;
  }
  
  private ApplicationInfo getApplicationInfo(RemoteViews paramRemoteViews) {
    return (paramRemoteViews != null) ? (ApplicationInfo)RemoteViews.mApplication.get(paramRemoteViews) : null;
  }
  
  private PackageInfo getOutSidePackageInfo(String paramString) {
    try {
      return VirtualCore.get().getHostPackageManager().getPackageInfo(paramString, 1024);
    } finally {
      paramString = null;
    } 
  }
  
  private boolean resolveRemoteViews(Context paramContext, int paramInt, String paramString, Notification paramNotification) {
    ApplicationInfo applicationInfo1;
    boolean bool;
    if (paramNotification == null)
      return false; 
    ApplicationInfo applicationInfo2 = getHostContext().getApplicationInfo();
    PackageInfo packageInfo2 = getOutSidePackageInfo(paramString);
    PackageInfo packageInfo1 = VPackageManager.get().getPackageInfo(paramString, 1024, 0);
    if (packageInfo2 != null && packageInfo2.versionCode == packageInfo1.versionCode) {
      bool = true;
    } else {
      bool = false;
    } 
    getNotificationFixer().fixNotificationRemoteViews(paramContext, paramNotification);
    if (Build.VERSION.SDK_INT >= 23) {
      getNotificationFixer().fixIcon(paramNotification.getSmallIcon(), paramContext, bool);
      getNotificationFixer().fixIcon(paramNotification.getLargeIcon(), paramContext, bool);
    } else {
      getNotificationFixer().fixIconImage(paramContext.getResources(), paramNotification.contentView, false, paramNotification);
    } 
    paramNotification.icon = applicationInfo2.icon;
    if (bool) {
      applicationInfo1 = packageInfo2.applicationInfo;
    } else {
      applicationInfo1 = ((PackageInfo)applicationInfo1).applicationInfo;
    } 
    applicationInfo1.targetSdkVersion = 22;
    fixApplicationInfo(paramNotification.tickerView, applicationInfo1);
    fixApplicationInfo(paramNotification.contentView, applicationInfo1);
    fixApplicationInfo(paramNotification.bigContentView, applicationInfo1);
    fixApplicationInfo(paramNotification.headsUpContentView, applicationInfo1);
    Bundle bundle = (Bundle)Reflect.on(paramNotification).get("extras");
    if (bundle != null)
      bundle.putParcelable("android.appInfo", (Parcelable)applicationInfo1); 
    if (Build.VERSION.SDK_INT >= 26 && !bool)
      remakeRemoteViews(paramInt, paramNotification, paramContext); 
    return true;
  }
  
  public boolean dealNotification(int paramInt, Notification paramNotification, String paramString) {
    Context context = getAppContext(paramString);
    if (Build.VERSION.SDK_INT >= 26 && VirtualCore.get().getTargetSdkVersion() >= 26 && TextUtils.isEmpty(paramNotification.getChannelId()))
      NotificationO.mChannelId.set(paramNotification, NotificationChannelCompat.DEFAULT_ID); 
    boolean bool = false;
    try {
      return true;
    } finally {
      paramNotification = null;
      VLog.e(TAG, "error deal Notification!");
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\notification\NotificationCompatCompatV21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */