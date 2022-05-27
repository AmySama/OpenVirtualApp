package com.lody.virtual.server.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.text.TextUtils;
import com.lody.virtual.helper.utils.Singleton;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.server.interfaces.INotificationManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VNotificationManagerService extends INotificationManager.Stub {
  static final String TAG;
  
  private static final Singleton<VNotificationManagerService> gService = new Singleton<VNotificationManagerService>() {
      protected VNotificationManagerService create() {
        return new VNotificationManagerService();
      }
    };
  
  private Context mContext;
  
  private final List<String> mDisables = new ArrayList<String>();
  
  private NotificationManager mNotificationManager;
  
  private final HashMap<String, List<NotificationInfo>> mNotifications = new HashMap<String, List<NotificationInfo>>();
  
  static {
    TAG = NotificationCompat.class.getSimpleName();
  }
  
  public static VNotificationManagerService get() {
    return (VNotificationManagerService)gService.get();
  }
  
  private void init(Context paramContext) {
    this.mContext = paramContext;
    this.mNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
  }
  
  public static void systemReady(Context paramContext) {
    get().init(paramContext);
  }
  
  public void addNotification(int paramInt1, String paramString1, String paramString2, int paramInt2) {
    NotificationInfo notificationInfo = new NotificationInfo(paramInt1, paramString1, paramString2, paramInt2);
    synchronized (this.mNotifications) {
      List<NotificationInfo> list2 = this.mNotifications.get(paramString2);
      List<NotificationInfo> list1 = list2;
      if (list2 == null) {
        list1 = new ArrayList();
        super();
        this.mNotifications.put(paramString2, list1);
      } 
      if (!list1.contains(notificationInfo))
        list1.add(notificationInfo); 
      return;
    } 
  }
  
  public boolean areNotificationsEnabledForPackage(String paramString, int paramInt) {
    List<String> list = this.mDisables;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append(":");
    stringBuilder.append(paramInt);
    return list.contains(stringBuilder.toString()) ^ true;
  }
  
  public void cancelAllNotification(String paramString, int paramInt) {
    HashMap<String, List<NotificationInfo>> hashMap;
    StringBuilder stringBuilder;
    ArrayList<NotificationInfo> arrayList = new ArrayList();
    synchronized (this.mNotifications) {
      List<NotificationInfo> list = this.mNotifications.get(paramString);
      if (list != null)
        for (int i = list.size() - 1; i >= 0; i--) {
          NotificationInfo notificationInfo = list.get(i);
          if (notificationInfo.userId == paramInt) {
            arrayList.add(notificationInfo);
            list.remove(i);
          } 
        }  
      for (NotificationInfo notificationInfo : arrayList) {
        String str = TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("cancel ");
        stringBuilder.append(notificationInfo.tag);
        stringBuilder.append(" ");
        stringBuilder.append(notificationInfo.id);
        VLog.d(str, stringBuilder.toString(), new Object[0]);
        this.mNotificationManager.cancel(notificationInfo.tag, notificationInfo.id);
      } 
      return;
    } 
  }
  
  public int dealNotificationId(int paramInt1, String paramString1, String paramString2, int paramInt2) {
    return paramInt1;
  }
  
  public String dealNotificationTag(int paramInt1, String paramString1, String paramString2, int paramInt2) {
    StringBuilder stringBuilder1;
    if (TextUtils.equals(this.mContext.getPackageName(), paramString1))
      return paramString2; 
    if (paramString2 == null) {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString1);
      stringBuilder1.append("@");
      stringBuilder1.append(paramInt2);
      return stringBuilder1.toString();
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString1);
    stringBuilder2.append(":");
    stringBuilder2.append((String)stringBuilder1);
    stringBuilder2.append("@");
    stringBuilder2.append(paramInt2);
    return stringBuilder2.toString();
  }
  
  public void setNotificationsEnabledForPackage(String paramString, boolean paramBoolean, int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append(":");
    stringBuilder.append(paramInt);
    paramString = stringBuilder.toString();
    if (paramBoolean) {
      if (this.mDisables.contains(paramString))
        this.mDisables.remove(paramString); 
    } else if (!this.mDisables.contains(paramString)) {
      this.mDisables.add(paramString);
    } 
  }
  
  private static class NotificationInfo {
    int id;
    
    String packageName;
    
    String tag;
    
    int userId;
    
    NotificationInfo(int param1Int1, String param1String1, String param1String2, int param1Int2) {
      this.id = param1Int1;
      this.tag = param1String1;
      this.packageName = param1String2;
      this.userId = param1Int2;
    }
    
    public boolean equals(Object param1Object) {
      if (param1Object instanceof NotificationInfo) {
        boolean bool;
        param1Object = param1Object;
        if (((NotificationInfo)param1Object).id == this.id && TextUtils.equals(((NotificationInfo)param1Object).tag, this.tag) && TextUtils.equals(this.packageName, ((NotificationInfo)param1Object).packageName) && ((NotificationInfo)param1Object).userId == this.userId) {
          bool = true;
        } else {
          bool = false;
        } 
        return bool;
      } 
      return super.equals(param1Object);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\notification\VNotificationManagerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */