package com.lody.virtual.helper.compat;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;

public class ContentProviderCompat {
  private static ContentProviderClient acquireContentProviderClient(Context paramContext, Uri paramUri) {
    try {
      return (Build.VERSION.SDK_INT >= 16) ? paramContext.getContentResolver().acquireUnstableContentProviderClient(paramUri) : paramContext.getContentResolver().acquireContentProviderClient(paramUri);
    } catch (SecurityException securityException) {
      securityException.printStackTrace();
      return null;
    } 
  }
  
  private static ContentProviderClient acquireContentProviderClient(Context paramContext, String paramString) {
    return (Build.VERSION.SDK_INT >= 16) ? paramContext.getContentResolver().acquireUnstableContentProviderClient(paramString) : paramContext.getContentResolver().acquireContentProviderClient(paramString);
  }
  
  public static ContentProviderClient acquireContentProviderClientRetry(Context paramContext, Uri paramUri, int paramInt) {
    ContentProviderClient contentProviderClient1 = acquireContentProviderClient(paramContext, paramUri);
    ContentProviderClient contentProviderClient2 = contentProviderClient1;
    if (contentProviderClient1 == null) {
      byte b = 0;
      while (true) {
        contentProviderClient2 = contentProviderClient1;
        if (b < paramInt) {
          contentProviderClient2 = contentProviderClient1;
          if (contentProviderClient1 == null) {
            SystemClock.sleep(100L);
            b++;
            contentProviderClient1 = acquireContentProviderClient(paramContext, paramUri);
            continue;
          } 
        } 
        break;
      } 
    } 
    return contentProviderClient2;
  }
  
  public static ContentProviderClient acquireContentProviderClientRetry(Context paramContext, String paramString, int paramInt) {
    ContentProviderClient contentProviderClient1 = acquireContentProviderClient(paramContext, paramString);
    ContentProviderClient contentProviderClient2 = contentProviderClient1;
    if (contentProviderClient1 == null) {
      byte b = 0;
      while (true) {
        contentProviderClient2 = contentProviderClient1;
        if (b < paramInt) {
          contentProviderClient2 = contentProviderClient1;
          if (contentProviderClient1 == null) {
            SystemClock.sleep(100L);
            b++;
            contentProviderClient1 = acquireContentProviderClient(paramContext, paramString);
            continue;
          } 
        } 
        break;
      } 
    } 
    return contentProviderClient2;
  }
  
  public static Bundle call(Context paramContext, Uri paramUri, String paramString1, String paramString2, Bundle paramBundle, int paramInt) throws IllegalAccessException {
    IllegalAccessException illegalAccessException;
    if (Build.VERSION.SDK_INT < 17)
      return paramContext.getContentResolver().call(paramUri, paramString1, paramString2, paramBundle); 
    ContentProviderClient contentProviderClient = acquireContentProviderClientRetry(paramContext, paramUri, paramInt);
    if (contentProviderClient != null) {
      try {
        Bundle bundle = contentProviderClient.call(paramString1, paramString2, paramBundle);
        releaseQuietly(contentProviderClient);
        return bundle;
      } catch (RemoteException remoteException) {
        illegalAccessException = new IllegalAccessException();
        this(remoteException.getMessage());
        throw illegalAccessException;
      } finally {}
    } else {
      illegalAccessException = new IllegalAccessException();
      this();
      throw illegalAccessException;
    } 
    releaseQuietly(contentProviderClient);
    throw illegalAccessException;
  }
  
  private static void releaseQuietly(ContentProviderClient paramContentProviderClient) {
    if (paramContentProviderClient != null)
      try {
        if (Build.VERSION.SDK_INT >= 24) {
          paramContentProviderClient.close();
        } else {
          paramContentProviderClient.release();
        } 
      } catch (Exception exception) {} 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\ContentProviderCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */