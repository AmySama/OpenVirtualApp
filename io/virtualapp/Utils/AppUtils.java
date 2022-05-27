package io.virtualapp.Utils;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class AppUtils {
  private AppUtils() {
    throw new UnsupportedOperationException("cannot be instantiated");
  }
  
  public static boolean checkApkExist(Context paramContext, String paramString) {
    PackageManager packageManager = paramContext.getPackageManager();
    byte b = 0;
    List list = packageManager.getInstalledPackages(0);
    ArrayList<String> arrayList = new ArrayList();
    if (list != null)
      while (b < list.size()) {
        arrayList.add(((PackageInfo)list.get(b)).packageName);
        b++;
      }  
    return arrayList.contains(paramString);
  }
  
  public static int dp2px(Context paramContext, float paramFloat) {
    try {
      float f = (paramContext.getResources().getDisplayMetrics()).density;
      return (int)(paramFloat * f + 0.5F);
    } catch (Exception exception) {
      return (int)paramFloat;
    } 
  }
  
  private static String encryptionMD5(byte[] paramArrayOfbyte) {
    StringBuffer stringBuffer = new StringBuffer();
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.reset();
      messageDigest.update(paramArrayOfbyte);
      paramArrayOfbyte = messageDigest.digest();
      for (byte b = 0; b < paramArrayOfbyte.length; b++) {
        if (Integer.toHexString(paramArrayOfbyte[b] & 0xFF).length() == 1) {
          stringBuffer.append("0");
          stringBuffer.append(Integer.toHexString(paramArrayOfbyte[b] & 0xFF));
        } else {
          stringBuffer.append(Integer.toHexString(paramArrayOfbyte[b] & 0xFF));
        } 
      } 
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      noSuchAlgorithmException.printStackTrace();
    } 
    return stringBuffer.toString();
  }
  
  public static String getAppName(Context paramContext) {
    try {
      int i = (paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0)).applicationInfo.labelRes;
      return paramContext.getResources().getString(i);
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      nameNotFoundException.printStackTrace();
      return null;
    } 
  }
  
  public static String getIccid(Context paramContext) {
    String str;
    TelephonyManager telephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    if (telephonyManager.getSimSerialNumber() != null) {
      str = telephonyManager.getSimSerialNumber();
    } else {
      str = "";
    } 
    return str;
  }
  
  public static String getImei(Context paramContext) {
    String str;
    TelephonyManager telephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    if (telephonyManager.getDeviceId() != null) {
      str = telephonyManager.getDeviceId();
    } else {
      str = "";
    } 
    return str;
  }
  
  public static String getImsi(Context paramContext) {
    String str;
    TelephonyManager telephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    if (telephonyManager.getSubscriberId() != null) {
      str = telephonyManager.getSubscriberId();
    } else {
      str = "";
    } 
    return str;
  }
  
  public static String getLocalMacAddressFromWifiInfo(Context paramContext) {
    return "";
  }
  
  public static String getMetaValue(Context paramContext, String paramString) {
    String str1 = null;
    String str2 = str1;
    if (paramContext != null)
      if (paramString == null) {
        str2 = str1;
      } else {
        try {
          ApplicationInfo applicationInfo = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
          if (applicationInfo != null) {
            Bundle bundle = applicationInfo.metaData;
          } else {
            applicationInfo = null;
          } 
          str2 = str1;
          if (applicationInfo != null)
            str2 = applicationInfo.getString(paramString); 
        } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
          str2 = str1;
        } 
      }  
    return str2;
  }
  
  public static int getPhoneHeightPixels(Context paramContext) {
    WindowManager windowManager = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics displayMetrics = new DisplayMetrics();
    if (windowManager != null)
      windowManager.getDefaultDisplay().getMetrics(displayMetrics); 
    return displayMetrics.heightPixels;
  }
  
  public static int getPhoneWidthPixels(Context paramContext) {
    WindowManager windowManager = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics displayMetrics = new DisplayMetrics();
    if (windowManager != null)
      windowManager.getDefaultDisplay().getMetrics(displayMetrics); 
    return displayMetrics.widthPixels;
  }
  
  public static String getRunningActivityName(Context paramContext) {
    return ((ActivityManager.RunningTaskInfo)((ActivityManager)paramContext.getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getClassName();
  }
  
  public static String getSignMd5Str(Context paramContext) {
    try {
      return encryptionMD5((paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 64)).signatures[0].toByteArray());
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      nameNotFoundException.printStackTrace();
      return "";
    } 
  }
  
  public static String getSsid(Context paramContext) {
    String str;
    WifiInfo wifiInfo = ((WifiManager)paramContext.getSystemService("wifi")).getConnectionInfo();
    if (wifiInfo == null) {
      str = "NULL";
    } else {
      str = str.getSSID();
    } 
    return str;
  }
  
  public static String getUniquePsuedoID() {
    // Byte code:
    //   0: new java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore_0
    //   8: aload_0
    //   9: invokestatic getExternalStorageDirectory : ()Ljava/io/File;
    //   12: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   15: pop
    //   16: aload_0
    //   17: ldc_w '/systemConfig'
    //   20: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: pop
    //   24: aload_0
    //   25: invokevirtual toString : ()Ljava/lang/String;
    //   28: astore_1
    //   29: new java/io/File
    //   32: dup
    //   33: aload_1
    //   34: invokespecial <init> : (Ljava/lang/String;)V
    //   37: astore_0
    //   38: aload_0
    //   39: invokevirtual exists : ()Z
    //   42: ifne -> 50
    //   45: aload_0
    //   46: invokevirtual mkdirs : ()Z
    //   49: pop
    //   50: ldc ''
    //   52: astore_0
    //   53: aload_0
    //   54: astore_2
    //   55: new java/io/File
    //   58: astore_3
    //   59: aload_0
    //   60: astore_2
    //   61: new java/lang/StringBuilder
    //   64: astore #4
    //   66: aload_0
    //   67: astore_2
    //   68: aload #4
    //   70: invokespecial <init> : ()V
    //   73: aload_0
    //   74: astore_2
    //   75: aload #4
    //   77: aload_1
    //   78: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   81: pop
    //   82: aload_0
    //   83: astore_2
    //   84: aload #4
    //   86: ldc_w '/yydkbconfig'
    //   89: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   92: pop
    //   93: aload_0
    //   94: astore_2
    //   95: aload_3
    //   96: aload #4
    //   98: invokevirtual toString : ()Ljava/lang/String;
    //   101: invokespecial <init> : (Ljava/lang/String;)V
    //   104: aload_0
    //   105: astore_2
    //   106: aload_3
    //   107: invokevirtual exists : ()Z
    //   110: ifne -> 120
    //   113: aload_0
    //   114: astore_2
    //   115: aload_3
    //   116: invokevirtual createNewFile : ()Z
    //   119: pop
    //   120: aload_0
    //   121: astore_2
    //   122: new java/io/FileInputStream
    //   125: astore #4
    //   127: aload_0
    //   128: astore_2
    //   129: aload #4
    //   131: aload_3
    //   132: invokespecial <init> : (Ljava/io/File;)V
    //   135: aload_0
    //   136: astore_2
    //   137: new java/io/InputStreamReader
    //   140: astore #5
    //   142: aload_0
    //   143: astore_2
    //   144: aload #5
    //   146: aload #4
    //   148: invokespecial <init> : (Ljava/io/InputStream;)V
    //   151: aload_0
    //   152: astore_2
    //   153: new java/io/BufferedReader
    //   156: astore_3
    //   157: aload_0
    //   158: astore_2
    //   159: aload_3
    //   160: aload #5
    //   162: invokespecial <init> : (Ljava/io/Reader;)V
    //   165: aload_0
    //   166: astore_2
    //   167: aload_3
    //   168: invokevirtual readLine : ()Ljava/lang/String;
    //   171: astore #6
    //   173: aload #6
    //   175: ifnull -> 222
    //   178: aload_0
    //   179: astore_2
    //   180: new java/lang/StringBuilder
    //   183: astore #5
    //   185: aload_0
    //   186: astore_2
    //   187: aload #5
    //   189: invokespecial <init> : ()V
    //   192: aload_0
    //   193: astore_2
    //   194: aload #5
    //   196: aload_0
    //   197: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   200: pop
    //   201: aload_0
    //   202: astore_2
    //   203: aload #5
    //   205: aload #6
    //   207: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   210: pop
    //   211: aload_0
    //   212: astore_2
    //   213: aload #5
    //   215: invokevirtual toString : ()Ljava/lang/String;
    //   218: astore_0
    //   219: goto -> 165
    //   222: aload_0
    //   223: astore_2
    //   224: aload #4
    //   226: invokevirtual close : ()V
    //   229: goto -> 235
    //   232: astore_0
    //   233: aload_2
    //   234: astore_0
    //   235: new java/lang/StringBuilder
    //   238: dup
    //   239: invokespecial <init> : ()V
    //   242: astore_2
    //   243: aload_2
    //   244: ldc_w 'uuid1==='
    //   247: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   250: pop
    //   251: aload_2
    //   252: aload_0
    //   253: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   256: pop
    //   257: ldc_w 'zxnbl'
    //   260: aload_2
    //   261: invokevirtual toString : ()Ljava/lang/String;
    //   264: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   267: pop
    //   268: aload_0
    //   269: astore_2
    //   270: aload_0
    //   271: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   274: ifeq -> 354
    //   277: invokestatic randomUUID : ()Ljava/util/UUID;
    //   280: invokevirtual toString : ()Ljava/lang/String;
    //   283: astore_2
    //   284: new java/lang/StringBuilder
    //   287: astore_0
    //   288: aload_0
    //   289: invokespecial <init> : ()V
    //   292: aload_0
    //   293: aload_1
    //   294: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   297: pop
    //   298: aload_0
    //   299: ldc_w '/yydkbconfig'
    //   302: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   305: pop
    //   306: aload_0
    //   307: invokevirtual toString : ()Ljava/lang/String;
    //   310: aload_2
    //   311: invokestatic writeSDFile : (Ljava/lang/String;Ljava/lang/String;)V
    //   314: goto -> 354
    //   317: astore_0
    //   318: new java/lang/StringBuilder
    //   321: dup
    //   322: invokespecial <init> : ()V
    //   325: astore_1
    //   326: aload_1
    //   327: ldc_w 'error==='
    //   330: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   333: pop
    //   334: aload_1
    //   335: aload_0
    //   336: invokevirtual getMessage : ()Ljava/lang/String;
    //   339: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   342: pop
    //   343: ldc_w 'zxnbl'
    //   346: aload_1
    //   347: invokevirtual toString : ()Ljava/lang/String;
    //   350: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   353: pop
    //   354: new java/lang/StringBuilder
    //   357: dup
    //   358: invokespecial <init> : ()V
    //   361: astore_0
    //   362: aload_0
    //   363: ldc_w 'uuid2==='
    //   366: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   369: pop
    //   370: aload_0
    //   371: aload_2
    //   372: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   375: pop
    //   376: ldc_w 'zxnbl'
    //   379: aload_0
    //   380: invokevirtual toString : ()Ljava/lang/String;
    //   383: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   386: pop
    //   387: aload_2
    //   388: areturn
    // Exception table:
    //   from	to	target	type
    //   55	59	232	java/io/FileNotFoundException
    //   55	59	232	java/io/IOException
    //   61	66	232	java/io/FileNotFoundException
    //   61	66	232	java/io/IOException
    //   68	73	232	java/io/FileNotFoundException
    //   68	73	232	java/io/IOException
    //   75	82	232	java/io/FileNotFoundException
    //   75	82	232	java/io/IOException
    //   84	93	232	java/io/FileNotFoundException
    //   84	93	232	java/io/IOException
    //   95	104	232	java/io/FileNotFoundException
    //   95	104	232	java/io/IOException
    //   106	113	232	java/io/FileNotFoundException
    //   106	113	232	java/io/IOException
    //   115	120	232	java/io/FileNotFoundException
    //   115	120	232	java/io/IOException
    //   122	127	232	java/io/FileNotFoundException
    //   122	127	232	java/io/IOException
    //   129	135	232	java/io/FileNotFoundException
    //   129	135	232	java/io/IOException
    //   137	142	232	java/io/FileNotFoundException
    //   137	142	232	java/io/IOException
    //   144	151	232	java/io/FileNotFoundException
    //   144	151	232	java/io/IOException
    //   153	157	232	java/io/FileNotFoundException
    //   153	157	232	java/io/IOException
    //   159	165	232	java/io/FileNotFoundException
    //   159	165	232	java/io/IOException
    //   167	173	232	java/io/FileNotFoundException
    //   167	173	232	java/io/IOException
    //   180	185	232	java/io/FileNotFoundException
    //   180	185	232	java/io/IOException
    //   187	192	232	java/io/FileNotFoundException
    //   187	192	232	java/io/IOException
    //   194	201	232	java/io/FileNotFoundException
    //   194	201	232	java/io/IOException
    //   203	211	232	java/io/FileNotFoundException
    //   203	211	232	java/io/IOException
    //   213	219	232	java/io/FileNotFoundException
    //   213	219	232	java/io/IOException
    //   224	229	232	java/io/FileNotFoundException
    //   224	229	232	java/io/IOException
    //   284	314	317	java/io/IOException
  }
  
  public static int getVersionCode(Context paramContext) {
    try {
      return (paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0)).versionCode;
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      nameNotFoundException.printStackTrace();
      return 0;
    } 
  }
  
  public static String getVersionName(Context paramContext) {
    try {
      return (paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0)).versionName;
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      nameNotFoundException.printStackTrace();
      return null;
    } 
  }
  
  public static boolean isServiceWork(Context paramContext, String paramString) {
    ArrayList arrayList = (ArrayList)((ActivityManager)paramContext.getSystemService("activity")).getRunningServices(100);
    for (byte b = 0; b < arrayList.size(); b++) {
      if (((ActivityManager.RunningServiceInfo)arrayList.get(b)).service.getClassName().toString().equals(paramString))
        return true; 
    } 
    return false;
  }
  
  public static void joinQQGroup(Context paramContext, String paramString) {
    Intent intent = new Intent();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D");
    stringBuilder.append(paramString);
    intent.setData(Uri.parse(stringBuilder.toString()));
    try {
      paramContext.startActivity(intent);
    } catch (Exception exception) {
      ToastUtil.showToast("您未安装手机QQ或安装的版本不支持");
    } 
  }
  
  public static void openApplicationMarket(Context paramContext, String paramString) {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("market://details?id=");
      stringBuilder.append(paramString);
      String str = stringBuilder.toString();
      Intent intent = new Intent();
      this("android.intent.action.VIEW");
      intent.setData(Uri.parse(str));
      paramContext.startActivity(intent);
    } catch (Exception exception) {
      exception.printStackTrace();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("http://app.mi.com/details?id=");
      stringBuilder.append(paramString);
      stringBuilder.append("&ref=search");
      openLinkBySystem(paramContext, stringBuilder.toString());
    } 
  }
  
  public static void openFile(Context paramContext, File paramFile) {
    Intent intent = new Intent();
    intent.addFlags(268435456);
    intent.setAction("android.intent.action.VIEW");
    intent.setDataAndType(Uri.fromFile(paramFile), "application/vnd.android.package-archive");
    paramContext.startActivity(intent);
  }
  
  public static void openLinkBySystem(Context paramContext, String paramString) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.setData(Uri.parse(paramString));
    paramContext.startActivity(intent);
  }
  
  public static void power(Context paramContext) {
    ((PowerManager)paramContext.getSystemService("power")).newWakeLock(268435462, "bright").acquire();
    ((KeyguardManager)paramContext.getSystemService("keyguard")).newKeyguardLock("unLock").disableKeyguard();
  }
  
  public static int px2dp(Context paramContext, float paramFloat) {
    try {
      float f = (paramContext.getResources().getDisplayMetrics()).density;
      return (int)(paramFloat / f + 0.5F);
    } catch (Exception exception) {
      return (int)paramFloat;
    } 
  }
  
  public static void starturl(Context paramContext, String paramString) {
    try {
      Intent intent = new Intent();
      this();
      intent.setAction("android.intent.action.VIEW");
      intent.setData(Uri.parse(paramString));
      paramContext.startActivity(intent);
    } catch (ActivityNotFoundException activityNotFoundException) {
      activityNotFoundException.printStackTrace();
      Toast.makeText(paramContext, "Url解析出错", 1).show();
    } 
  }
  
  public static void writeSDFile(String paramString1, String paramString2) throws IOException {
    FileOutputStream fileOutputStream = new FileOutputStream(new File(paramString1));
    fileOutputStream.write(paramString2.getBytes());
    fileOutputStream.close();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\AppUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */