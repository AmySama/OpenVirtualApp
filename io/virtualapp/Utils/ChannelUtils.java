package io.virtualapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class ChannelUtils {
  private static final String CHANNEL_KEY = "";
  
  private static final String CHANNEL_VERSION_KEY = "";
  
  private static String mChannel;
  
  private static String mDefaultChannel = "qihoo";
  
  public static String getAppMetaData(Context paramContext, String paramString) {
    String str1 = null;
    String str2 = str1;
    if (paramContext != null)
      if (TextUtils.isEmpty(paramString)) {
        str2 = str1;
      } else {
        try {
          PackageManager packageManager = paramContext.getPackageManager();
          str2 = str1;
          if (packageManager != null) {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(paramContext.getPackageName(), 128);
            str2 = str1;
            if (applicationInfo != null) {
              str2 = str1;
              if (applicationInfo.metaData != null)
                str2 = applicationInfo.metaData.getString(paramString); 
            } 
          } 
        } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
          nameNotFoundException.printStackTrace();
          str2 = str1;
        } 
      }  
    return str2;
  }
  
  public static String getChannel(Context paramContext) {
    return getChannel(paramContext, mDefaultChannel);
  }
  
  private static String getChannel(Context paramContext, String paramString) {
    if (!TextUtils.isEmpty(mChannel))
      return mChannel; 
    String str = getChannelBySharedPreferences(paramContext);
    mChannel = str;
    if (!TextUtils.isEmpty(str))
      return mChannel; 
    str = getChannelFromApk(paramContext, "");
    mChannel = str;
    if (!TextUtils.isEmpty(str)) {
      saveChannelBySharedPreferences(paramContext, mChannel);
      return mChannel;
    } 
    return paramString;
  }
  
  private static String getChannelBySharedPreferences(Context paramContext) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    int i = getVersionCode(paramContext);
    if (i == -1)
      return ""; 
    int j = sharedPreferences.getInt("", -1);
    return (j == -1) ? "" : ((i != j) ? "" : sharedPreferences.getString("", ""));
  }
  
  private static String getChannelFromApk(Context paramContext, String paramString) {
    // Byte code:
    //   0: ldc ''
    //   2: astore_2
    //   3: aload_0
    //   4: invokevirtual getApplicationInfo : ()Landroid/content/pm/ApplicationInfo;
    //   7: getfield sourceDir : Ljava/lang/String;
    //   10: astore_3
    //   11: new java/lang/StringBuilder
    //   14: dup
    //   15: invokespecial <init> : ()V
    //   18: astore_0
    //   19: aload_0
    //   20: ldc 'META-INF/'
    //   22: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   25: pop
    //   26: aload_0
    //   27: aload_1
    //   28: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   31: pop
    //   32: aload_0
    //   33: invokevirtual toString : ()Ljava/lang/String;
    //   36: astore #4
    //   38: aconst_null
    //   39: astore #5
    //   41: aconst_null
    //   42: astore #6
    //   44: aload #6
    //   46: astore_0
    //   47: new java/util/zip/ZipFile
    //   50: astore_1
    //   51: aload #6
    //   53: astore_0
    //   54: aload_1
    //   55: aload_3
    //   56: invokespecial <init> : (Ljava/lang/String;)V
    //   59: aload_1
    //   60: invokevirtual entries : ()Ljava/util/Enumeration;
    //   63: astore #6
    //   65: aload #6
    //   67: invokeinterface hasMoreElements : ()Z
    //   72: ifeq -> 105
    //   75: aload #6
    //   77: invokeinterface nextElement : ()Ljava/lang/Object;
    //   82: checkcast java/util/zip/ZipEntry
    //   85: invokevirtual getName : ()Ljava/lang/String;
    //   88: astore_0
    //   89: aload_0
    //   90: aload #4
    //   92: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   95: istore #7
    //   97: iload #7
    //   99: ifeq -> 65
    //   102: goto -> 108
    //   105: ldc ''
    //   107: astore_0
    //   108: aload_1
    //   109: invokevirtual close : ()V
    //   112: goto -> 173
    //   115: astore_1
    //   116: aload_1
    //   117: invokevirtual printStackTrace : ()V
    //   120: goto -> 173
    //   123: astore_0
    //   124: goto -> 200
    //   127: astore #6
    //   129: goto -> 147
    //   132: astore #6
    //   134: aload_0
    //   135: astore_1
    //   136: aload #6
    //   138: astore_0
    //   139: goto -> 200
    //   142: astore #6
    //   144: aload #5
    //   146: astore_1
    //   147: aload_1
    //   148: astore_0
    //   149: aload #6
    //   151: invokevirtual printStackTrace : ()V
    //   154: aload_1
    //   155: ifnull -> 170
    //   158: aload_1
    //   159: invokevirtual close : ()V
    //   162: goto -> 170
    //   165: astore_0
    //   166: aload_0
    //   167: invokevirtual printStackTrace : ()V
    //   170: ldc ''
    //   172: astore_0
    //   173: aload_0
    //   174: ldc '_'
    //   176: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   179: astore_1
    //   180: aload_2
    //   181: astore_0
    //   182: aload_1
    //   183: ifnull -> 198
    //   186: aload_2
    //   187: astore_0
    //   188: aload_1
    //   189: arraylength
    //   190: iconst_3
    //   191: if_icmplt -> 198
    //   194: aload_1
    //   195: iconst_2
    //   196: aaload
    //   197: astore_0
    //   198: aload_0
    //   199: areturn
    //   200: aload_1
    //   201: ifnull -> 216
    //   204: aload_1
    //   205: invokevirtual close : ()V
    //   208: goto -> 216
    //   211: astore_1
    //   212: aload_1
    //   213: invokevirtual printStackTrace : ()V
    //   216: aload_0
    //   217: athrow
    // Exception table:
    //   from	to	target	type
    //   47	51	142	java/io/IOException
    //   47	51	132	finally
    //   54	59	142	java/io/IOException
    //   54	59	132	finally
    //   59	65	127	java/io/IOException
    //   59	65	123	finally
    //   65	97	127	java/io/IOException
    //   65	97	123	finally
    //   108	112	115	java/io/IOException
    //   149	154	132	finally
    //   158	162	165	java/io/IOException
    //   204	208	211	java/io/IOException
  }
  
  private static int getVersionCode(Context paramContext) {
    try {
      return (paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0)).versionCode;
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      nameNotFoundException.printStackTrace();
      return -1;
    } 
  }
  
  private static void saveChannelBySharedPreferences(Context paramContext, String paramString) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
    editor.putString("", paramString);
    editor.putInt("", getVersionCode(paramContext));
    editor.apply();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\ChannelUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */