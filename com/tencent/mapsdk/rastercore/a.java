package com.tencent.mapsdk.rastercore;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.mapsdk.rastercore.f.a;
import com.tencent.tencentmap.mapsdk.map.MapView;
import java.io.BufferedInputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;
import org.json.JSONObject;

public class a {
  public static StringBuffer a;
  
  private static final String b = a.class.getSimpleName();
  
  private static boolean c;
  
  private static WeakReference<e> d;
  
  static {
    a = new StringBuffer(300);
    c = false;
    d = null;
  }
  
  public static String a() {
    String str1;
    Context context = e.a();
    if (Build.VERSION.SDK_INT < 23 || (context != null && context.checkSelfPermission("android.permission.READ_PHONE_STATE") == 0)) {
      str1 = ((TelephonyManager)context.getSystemService("phone")).getDeviceId();
    } else {
      str1 = "";
    } 
    String str2 = str1;
    if (TextUtils.isEmpty(str1))
      str2 = "noIMEI"; 
    return str2;
  }
  
  public static void a(e parame) {
    d = new WeakReference<e>(parame);
    if (c)
      return; 
    (new Thread() {
        public final void run() {
          a.a(true);
          String str1 = a.a(e.a());
          try {
            str1 = URLEncoder.encode(str1, "UTF-8");
            a.a.append("key=");
            a.a.append(str1);
          } catch (Exception null) {}
          a.a.append("&output=json");
          a.a.append("&pf=and_2Dmap");
          a.a.append("&ver=");
          try {
            a.a.append(URLEncoder.encode("1.2.8", "UTF-8"));
          } catch (Exception null) {}
          try {
            a.a.append("&hm=");
            a.a.append(URLEncoder.encode(Build.MODEL, "UTF-8"));
            a.a.append("&os=A");
            a.a.append(Build.VERSION.SDK_INT);
            a.a.append("&pid=");
            a.a.append(URLEncoder.encode(e.a().getPackageName(), "UTF-8"));
            a.a.append("&nt=");
            a.a.append(URLEncoder.encode(a.b()));
          } catch (Exception null) {}
          a.a.append("&suid=");
          a.a.append(a.a());
          String str2 = null;
          try {
            PackageManager packageManager = e.a().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(e.a().getPackageName(), 0);
            String str = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            try {
              String str4 = packageInfo.versionName;
              boolean bool = TextUtils.isEmpty(str4);
              String str3 = str;
              if (!bool) {
                str2 = str4;
                str3 = str;
              } 
            } catch (Exception exception1) {
              String str3 = str;
            } 
          } catch (Exception exception) {
            exception = null;
          } 
          if (exception != null) {
            a.a.append("&ref=");
            try {
              a.a.append(URLEncoder.encode((String)exception, "UTF-8"));
            } catch (Exception exception1) {}
          } 
          if (str2 != null) {
            a.a.append("&psv=");
            try {
              a.a.append(URLEncoder.encode(str2, "UTF-8"));
            } catch (Exception exception1) {}
          } 
          try {
            DisplayMetrics displayMetrics = e.a().getResources().getDisplayMetrics();
            int i = displayMetrics.densityDpi;
            int j = displayMetrics.widthPixels;
            int k = displayMetrics.heightPixels;
            a.a.append("&dpi=");
            a.a.append(i);
            a.a.append("&scrn=");
            a.a.append(j);
            a.a.append("*");
            a.a.append(k);
          } catch (Exception exception1) {}
          StringBuffer stringBuffer = a.a;
          for (byte b = 0; b < 2; b++) {
            try {
              URL uRL = new URL();
              StringBuilder stringBuilder = new StringBuilder();
              this();
              stringBuilder.append("https://apikey.map.qq.com/mkey/index.php/mkey/check?");
              stringBuilder.append(a.a.toString());
              this(stringBuilder.toString());
              HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
              httpURLConnection.setRequestProperty("Accept-Encoding", "gzip");
              if (httpURLConnection.getResponseCode() == 200) {
                BufferedInputStream bufferedInputStream;
                boolean bool;
                String str3 = httpURLConnection.getHeaderField("Content-Encoding");
                if (str3 != null && str3.length() > 0 && str3.toLowerCase().contains("gzip")) {
                  bool = true;
                } else {
                  bool = false;
                } 
                if (bool) {
                  bufferedInputStream = new BufferedInputStream();
                  GZIPInputStream gZIPInputStream = new GZIPInputStream();
                  this(httpURLConnection.getInputStream());
                  this(gZIPInputStream);
                } else {
                  bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                } 
                String str4 = new String();
                this(d.a.a(bufferedInputStream));
                a.a(str4);
                break;
              } 
            } catch (Exception exception1) {
              exception1.printStackTrace();
            } 
          } 
          a.a(false);
        }
      }).start();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */