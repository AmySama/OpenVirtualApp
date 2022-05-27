package com.lody.virtual.client.hook.providers;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IInterface;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.MethodBox;
import com.lody.virtual.helper.utils.VLog;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

class DownloadProviderHook extends ExternalProviderHook {
  public static final String COLUMN_DESCRIPTION = "description";
  
  public static final String COLUMN_FILE_NAME_HINT = "hint";
  
  public static final String COLUMN_IS_VISIBLE_IN_DOWNLOADS_UI = "is_visible_in_downloads_ui";
  
  private static final String COLUMN_NOTIFICATION_CLASS = "notificationclass";
  
  private static final String COLUMN_NOTIFICATION_PACKAGE = "notificationpackage";
  
  public static final String COLUMN_VISIBILITY = "visibility";
  
  private static final String TAG = DownloadProviderHook.class.getSimpleName();
  
  DownloadProviderHook(IInterface paramIInterface) {
    super(paramIInterface);
  }
  
  public Uri insert(MethodBox paramMethodBox, Uri paramUri, ContentValues paramContentValues) throws InvocationTargetException {
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("insert: ");
    stringBuilder1.append(paramContentValues);
    VLog.e("DownloadManager", stringBuilder1.toString());
    String str = paramContentValues.getAsString("notificationpackage");
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("notificationPkg: ");
    stringBuilder2.append(str);
    VLog.e("DownloadManager", stringBuilder2.toString());
    if (str == null)
      return (Uri)paramMethodBox.call(); 
    paramContentValues.put("notificationpackage", VirtualCore.get().getHostPkg());
    paramContentValues.put("visibility", Integer.valueOf(1));
    paramContentValues.put("hint", paramContentValues.getAsString("hint").replace(str, VirtualCore.get().getHostPkg()));
    return super.insert(paramMethodBox, paramUri, paramContentValues);
  }
  
  public Object invoke(Object paramObject, Method paramMethod, Object... paramVarArgs) throws Throwable {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("call ");
    stringBuilder.append(paramMethod.getName());
    stringBuilder.append(" -> ");
    stringBuilder.append(Arrays.toString(paramVarArgs));
    VLog.e("DownloadManager", stringBuilder.toString());
    return super.invoke(paramObject, paramMethod, paramVarArgs);
  }
  
  public Cursor query(MethodBox paramMethodBox, Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2, Bundle paramBundle) throws InvocationTargetException {
    String str = TAG;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("query : selection: ");
    stringBuilder.append(paramString1);
    stringBuilder.append(", args: ");
    stringBuilder.append(Arrays.toString((Object[])paramArrayOfString2));
    VLog.e(str, stringBuilder.toString());
    return super.query(paramMethodBox, paramUri, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2, paramBundle);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\providers\DownloadProviderHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */