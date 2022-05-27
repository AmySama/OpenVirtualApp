package com.tencent.mm.opensdk.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class b {
  public static Context a;
  
  private static final int b;
  
  private static final int c;
  
  private static final int d;
  
  public static ThreadPoolExecutor e = new ThreadPoolExecutor(c, d, 1L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
  
  public static int a(ContentResolver paramContentResolver, Uri paramUri) {
    Log.i("MicroMsg.SDK.Util", "getFileSize with content url");
    if (paramContentResolver == null || paramUri == null) {
      Log.w("MicroMsg.SDK.Util", "getFileSize fail, resolver or uri is null");
      return 0;
    } 
    IOException iOException1 = null;
    IOException iOException2 = null;
    try {
      InputStream inputStream = paramContentResolver.openInputStream(paramUri);
      if (inputStream == null) {
        if (inputStream != null)
          try {
            inputStream.close();
          } catch (IOException iOException) {} 
        return 0;
      } 
      iOException2 = iOException;
      iOException1 = iOException;
      int i = iOException.available();
      try {
        iOException.close();
      } catch (IOException iOException3) {}
    } catch (Exception exception) {
      iOException2 = iOException1;
      StringBuilder stringBuilder = new StringBuilder();
      iOException2 = iOException1;
      this();
      iOException2 = iOException1;
      stringBuilder.append("getFileSize fail, ");
      iOException2 = iOException1;
      stringBuilder.append(exception.getMessage());
      iOException2 = iOException1;
      Log.w("MicroMsg.SDK.Util", stringBuilder.toString());
      if (iOException1 != null)
        try {
          iOException1.close();
        } catch (IOException iOException) {} 
      return 0;
    } finally {}
    if (iOException2 != null)
      try {
        iOException2.close();
      } catch (IOException iOException) {} 
    throw paramContentResolver;
  }
  
  public static int a(String paramString) {
    if (paramString == null || paramString.length() == 0)
      return 0; 
    File file = new File(paramString);
    if (!file.exists()) {
      if (a != null && paramString.startsWith("content"))
        try {
          return a(a.getContentResolver(), Uri.parse(paramString));
        } catch (Exception exception) {} 
      return 0;
    } 
    return (int)file.length();
  }
  
  public static int a(String paramString, int paramInt) {
    int i = paramInt;
    if (paramString != null)
      try {
        if (paramString.length() <= 0) {
          i = paramInt;
        } else {
          i = Integer.parseInt(paramString);
        } 
      } catch (Exception exception) {
        i = paramInt;
      }  
    return i;
  }
  
  public static boolean a(int paramInt) {
    return (paramInt == 36 || paramInt == 46);
  }
  
  public static boolean b(String paramString) {
    return (paramString == null || paramString.length() <= 0);
  }
  
  static {
    int i = Runtime.getRuntime().availableProcessors();
    b = i;
    c = i + 1;
    d = i * 2 + 1;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensd\\utils\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */