package com.tencent.mm.opensdk.constants;

public final class Build {
  public static final int CHECK_TOKEN_SDK_INT = 620824064;
  
  public static final int CHOOSE_INVOICE_TILE_SUPPORT_SDK_INT = 620822528;
  
  public static final int EMOJI_SUPPORTED_SDK_INT = 553844737;
  
  public static final int FAVORITE_SUPPPORTED_SDK_INT = 570425345;
  
  public static final int INVOICE_AUTH_INSERT_SDK_INT = 620823552;
  
  public static final int LAUNCH_MINIPROGRAM_SUPPORTED_SDK_INT = 620757000;
  
  public static final int LAUNCH_MINIPROGRAM_WITH_TOKEN_SUPPORTED_SDK_INT = 621086464;
  
  public static final int MESSAGE_ACTION_SUPPPORTED_SDK_INT = 570490883;
  
  public static final int MINIPROGRAM_SUPPORTED_SDK_INT = 620756993;
  
  public static final int MIN_SDK_INT = 553713665;
  
  public static final int MUSIC_DATA_URL_SUPPORTED_SDK_INT = 553910273;
  
  public static final int NON_TAX_PAY_SDK_INT = 620823552;
  
  public static final int OFFLINE_PAY_SDK_INT = 620823808;
  
  public static final int OPENID_SUPPORTED_SDK_INT = 570425345;
  
  public static final int OPEN_BUSINESS_VIEW_SDK_INT = 620889344;
  
  public static final int OPEN_BUSINESS_WEBVIEW_SDK_INT = 620824064;
  
  public static final int PAY_INSURANCE_SDK_INT = 620823552;
  
  public static final int PAY_SUPPORTED_SDK_INT = 570425345;
  
  public static final int SCAN_QRCODE_AUTH_SUPPORTED_SDK_INT = 587268097;
  
  public static final int SDK_INT = 638058496;
  
  public static final String SDK_VERSION_NAME = "android 6.8.0";
  
  public static final int SEND_25M_IMAGE_SDK_INT = 620889088;
  
  public static final int SEND_AUTH_SCOPE_SNSAPI_WXAAPP_INFO_SUPPORTED_SDK_INT = 621086464;
  
  public static final int SEND_BUSINESS_CARD_SDK_INT = 620889344;
  
  public static final int SEND_TO_SPECIFIED_CONTACT_SDK_INT = 620824064;
  
  public static final int SUBSCRIBE_MESSAGE_SUPPORTED_SDK_INT = 620756998;
  
  public static final int SUBSCRIBE_MINI_PROGRAM_MSG_SUPPORTED_SDK_INT = 620823808;
  
  public static final int SUPPORTED_CHANNEL_OPEN_FEED = 654317312;
  
  public static final int SUPPORTED_CHANNEL_OPEN_LIVE = 654317312;
  
  public static final int SUPPORTED_CHANNEL_OPEN_PROFILE = 671089152;
  
  public static final int SUPPORTED_CHANNEL_SHARE_VIDEO = 654317312;
  
  public static final int SUPPORTED_JOINT_PAY = 621021440;
  
  public static final int SUPPORTED_PRELOAD_MINI_PROGRAM = 621085952;
  
  public static final int SUPPORTED_QRCODE_OPEN_PAY = 671092224;
  
  public static final int SUPPORTED_SEND_AUTH_ADD_OPTIONS = 671091456;
  
  public static final int SUPPORTED_SEND_MINIPROGRAM_SECRET_MESSAGE = 654315776;
  
  public static final int SUPPORTED_SEND_TO_STATUS = 671089664;
  
  public static final int SUPPORTED_SEND_WEB_PAGE_SECRET_MESSAGE = 671090432;
  
  public static final int SUPPORTED_SEND_WX_WEWORK_OBJECT = 620954624;
  
  public static final int SUPPORT_OPEN_CUSTOMER_SERVICE_CHAT = 671090490;
  
  public static final int SUPPORT_SEND_MUSIC_VIDEO_MESSAGE = 671088640;
  
  public static final int TIMELINE_SUPPORTED_SDK_INT = 553779201;
  
  public static final int VIDEO_FILE_SUPPORTED_SDK_INT = 620756996;
  
  public static final int WEISHI_MINIPROGRAM_SUPPORTED_SDK_INT = 620953856;
  
  private Build() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Build.class.getSimpleName());
    stringBuilder.append(" should not be instantiated");
    throw new RuntimeException(stringBuilder.toString());
  }
  
  public static int getMajorVersion() {
    return 6;
  }
  
  public static int getMinorVersion() {
    return 8;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\constants\Build.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */