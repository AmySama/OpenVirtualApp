package com.lody.virtual.server.notification;

import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.RemoteViews;
import com.lody.virtual.client.core.VirtualCore;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import mirror.com.android.internal.R_Hide;

public abstract class NotificationCompat {
  public static final String EXTRA_BIG_TEXT = "android.bigText";
  
  public static final String EXTRA_BUILDER_APPLICATION_INFO = "android.appInfo";
  
  public static final String EXTRA_INFO_TEXT = "android.infoText";
  
  public static final String EXTRA_PROGRESS = "android.progress";
  
  public static final String EXTRA_PROGRESS_MAX = "android.progressMax";
  
  public static final String EXTRA_SUB_TEXT = "android.subText";
  
  public static final String EXTRA_SUMMARY_TEXT = "android.summaryText";
  
  public static final String EXTRA_TEXT = "android.text";
  
  public static final String EXTRA_TITLE = "android.title";
  
  public static final String EXTRA_TITLE_BIG = "android.title.big";
  
  static final String SYSTEM_UI_PKG = "com.android.systemui";
  
  static final String TAG = NotificationCompat.class.getSimpleName();
  
  private NotificationFixer mNotificationFixer;
  
  private final List<Integer> sSystemLayoutResIds = new ArrayList<Integer>(10);
  
  NotificationCompat() {
    loadSystemLayoutRes();
    this.mNotificationFixer = new NotificationFixer(this);
  }
  
  public static NotificationCompat create() {
    return (Build.VERSION.SDK_INT >= 21) ? new NotificationCompatCompatV21() : new NotificationCompatCompatV14();
  }
  
  private void loadSystemLayoutRes() {
    Field[] arrayOfField = R_Hide.layout.TYPE.getFields();
    int i = arrayOfField.length;
    byte b = 0;
    while (true) {
      if (b < i) {
        Field field = arrayOfField[b];
        if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()))
          try {
            int j = field.getInt(null);
            this.sSystemLayoutResIds.add(Integer.valueOf(j));
          } finally {} 
        b++;
        continue;
      } 
      return;
    } 
  }
  
  public abstract boolean dealNotification(int paramInt, Notification paramNotification, String paramString);
  
  public Context getHostContext() {
    return VirtualCore.get().getContext();
  }
  
  NotificationFixer getNotificationFixer() {
    return this.mNotificationFixer;
  }
  
  PackageInfo getPackageInfo(String paramString) {
    try {
      return VirtualCore.get().getHostPackageManager().getPackageInfo(paramString, 0);
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      return null;
    } 
  }
  
  boolean isSystemLayout(RemoteViews paramRemoteViews) {
    boolean bool;
    if (paramRemoteViews != null && this.sSystemLayoutResIds.contains(Integer.valueOf(paramRemoteViews.getLayoutId()))) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\notification\NotificationCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */