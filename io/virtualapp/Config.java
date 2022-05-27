package io.virtualapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Config {
  public static final String ACTION_NOTIFY_LISTENER_SERVICE_CONNECT = "com.codeboy.qianghongbao.NOTIFY_LISTENER_CONNECT";
  
  public static final String ACTION_NOTIFY_LISTENER_SERVICE_DISCONNECT = "com.codeboy.qianghongbao.NOTIFY_LISTENER_DISCONNECT";
  
  public static final String ACTION_QIANGHONGBAO_SERVICE_CONNECT = "com.codeboy.qianghongbao.ACCESSBILITY_CONNECT";
  
  public static final String ACTION_QIANGHONGBAO_SERVICE_DISCONNECT = "com.codeboy.qianghongbao.ACCESSBILITY_DISCONNECT";
  
  public static final String ALIPAY_CALLBACK_URL = "http://fee.ymzer.com/api/callback.php";
  
  public static final String BASE_URL = "http://fee.ymzer.com/api/";
  
  public static final String C000 = "C000";
  
  public static final String C001 = "C001";
  
  public static final String C002 = "C002";
  
  public static final String C003 = "C003";
  
  public static final String C004 = "C004";
  
  public static final String C005 = "C005";
  
  public static final String C006 = "C006";
  
  public static final String C008 = "C008";
  
  public static final String C014 = "C014";
  
  public static final String COPY_GIFT_GO_LOTTERY = "copy_gift_go_lottery";
  
  public static final String COPY_GIFT_MARKET_MARK = "copy_gift_market_mark";
  
  public static final String COPY_GIFT_SHARE_FRIEND = "copy_gift_share_friend";
  
  public static final String COPY_GIFT_SHARE_MOMENTS = "copy_gift_share_moments";
  
  public static final String DOMAIN = "http://fee.ymzer.com/api/";
  
  public static final String ISVIP = "isvip";
  
  public static final String KEY_APPNAME = "key_appname";
  
  public static final String KEY_ENABLE_WECHAT = "KEY_ENABLE_WECHAT";
  
  public static final String KEY_ICONBYTE = "key_iconbyte";
  
  public static final String KEY_IS_SHOW_PLUGIN = "is_show_plugin";
  
  public static final String KEY_MY_INTERGAL = "my_intergal";
  
  public static final String KEY_NOTIFICATION_SERVICE_ENABLE = "KEY_NOTIFICATION_SERVICE_ENABLE";
  
  public static final String KEY_NOTIFY_NIGHT_ENABLE = "KEY_NOTIFY_NIGHT_ENABLE";
  
  public static final String KEY_NOTIFY_SOUND = "KEY_NOTIFY_SOUND";
  
  public static final String KEY_NOTIFY_VIBRATE = "KEY_NOTIFY_VIBRATE";
  
  public static final String KEY_OPENSERVER = "openserver";
  
  public static final String KEY_PINGBI = "PINGBI";
  
  public static final String KEY_QQ_GROUP = "4MdcZXJu7FQQhHWPvaQ_cIkaf0ZolMV0";
  
  public static final String KEY_REPLY = "reply";
  
  public static final String KEY_SOUND = "KEY_SOUND";
  
  public static final String KEY_TOKEN = "token";
  
  public static final String KEY_WAIT = "yanchi";
  
  public static final String KEY_WECHAT_AFTER_GET_HONGBAO = "KEY_WECHAT_AFTER_GET_HONGBAO";
  
  public static final String KEY_WECHAT_AFTER_OPEN_HONGBAO = "KEY_WECHAT_AFTER_OPEN_HONGBAO";
  
  public static final String KEY_WECHAT_DELAY_TIME = "KEY_WECHAT_DELAY_TIME";
  
  public static final String KEY_WECHAT_ICON = "WeChat_Icon";
  
  public static final String KEY_WECHAT_MODE = "KEY_WECHAT_MODE";
  
  public static final String KEY_WECHAT_NICKNAME = "WeChat_NickName";
  
  public static final String KEY_WECHAT_OPENID = "WeChat_OpenId";
  
  public static final String META_CHANNEL = "channel";
  
  public static final String PREFERENCE_NAME = "config";
  
  public static final int SERVICES_SLEEP_TIME = 5000;
  
  public static final String WEB_ABOUT = "http://copy.ymzer.com/page/about.html";
  
  public static final String WEB_BAIDU_BRIDGE = "http://www.vxiaoya.com/kf/kefu.html";
  
  public static final String WEB_DETAIL;
  
  public static final String WEB_FFHJT = "http://copy.ymzer.com/page/wxffhjt.html";
  
  public static final String WEB_HELP = "http://copy.ymzer.com/page/help_wxfsdk.html";
  
  public static final String WEB_PROTOCOL = "https://xiaoyintech.com/user_soft_service_info.html";
  
  public static final String WEB_QUESTION = "http://fee.ymzer.com/page/question.html?token=";
  
  public static final String WEB_SECRET_PROTOCOL = "https://xiaoyintech.com/user_privacy.html";
  
  public static final String WEB_TWJIAOCHENG = "http://copy.ymzer.com/page/dkfs/permission.html";
  
  public static final String WEB_URL = "http://fee.ymzer.com/api/static/share.html";
  
  public static final String WEB_USER_XIEYI = "https://xiaoyintech.com/user_service_info.html";
  
  public static final String WEB_WECHAT_PAY = "http://copy.ymzer.com/api/index.php?m=Home&c=WxH5Pay&a=payIndex";
  
  public static final String WEB_XINSHOUJIAOCHENG = "http://copy.ymzer.com/page/dkfs/course.html";
  
  public static final String WECHAT_CALLBACK_URL = "http://fee.ymzer.com/api/callbackwx.php";
  
  public static final int WX_AFTER_GET_GOHOME = 0;
  
  public static final int WX_AFTER_GET_NONE = 1;
  
  public static final int WX_AFTER_OPEN_HONGBAO = 0;
  
  public static final int WX_AFTER_OPEN_NONE = 2;
  
  public static final int WX_AFTER_OPEN_SEE = 1;
  
  public static final int WX_MODE_0 = 0;
  
  public static final int WX_MODE_1 = 1;
  
  public static final int WX_MODE_2 = 2;
  
  public static final int WX_MODE_3 = 3;
  
  private static Config current;
  
  private Context mContext;
  
  private SharedPreferences preferences;
  
  static {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/page/");
    stringBuilder.append(App.getApp().getPackageName());
    stringBuilder.append("/vipdetail.html");
    WEB_DETAIL = stringBuilder.toString();
  }
  
  private Config(Context paramContext) {
    this.mContext = paramContext;
    this.preferences = paramContext.getSharedPreferences("config", 0);
  }
  
  public static Config getConfig(Context paramContext) {
    // Byte code:
    //   0: ldc io/virtualapp/Config
    //   2: monitorenter
    //   3: getstatic io/virtualapp/Config.current : Lio/virtualapp/Config;
    //   6: ifnonnull -> 28
    //   9: new io/virtualapp/Config
    //   12: astore_1
    //   13: aload_1
    //   14: aload_0
    //   15: invokevirtual getApplicationContext : ()Landroid/content/Context;
    //   18: invokestatic getOrigApplicationContext : (Landroid/content/Context;)Landroid/content/Context;
    //   21: invokespecial <init> : (Landroid/content/Context;)V
    //   24: aload_1
    //   25: putstatic io/virtualapp/Config.current : Lio/virtualapp/Config;
    //   28: getstatic io/virtualapp/Config.current : Lio/virtualapp/Config;
    //   31: astore_0
    //   32: ldc io/virtualapp/Config
    //   34: monitorexit
    //   35: aload_0
    //   36: areturn
    //   37: astore_0
    //   38: ldc io/virtualapp/Config
    //   40: monitorexit
    //   41: aload_0
    //   42: athrow
    // Exception table:
    //   from	to	target	type
    //   3	28	37	finally
    //   28	32	37	finally
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Config.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */