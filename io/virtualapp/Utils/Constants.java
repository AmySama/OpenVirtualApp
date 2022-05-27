package io.virtualapp.Utils;

public class Constants {
  public static final String ACCOUNT_VALIDATE = "([a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+)|(^[1]+[3,5,8]+\\d{9}$)";
  
  public static final String ACCOUNT_VALIDATOR = "^[0-9]*$";
  
  public static final String ALIPAY_APPID = "2018010201504177";
  
  public static final String ALIPAY_CALLBACK_URL = "";
  
  public static final String CONTACT_VALIDATE = "(\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$";
  
  public static final String CURRENT_CITY = "current_city";
  
  public static final String CURRENT_LAT = "current_lat";
  
  public static final String CURRENT_LON = "current_lon";
  
  public static final String EMAIL_VALIDATE = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
  
  public static final String EMPTY_VALUE = "";
  
  public static final String FACE = "\\{:face_\\d*:\\}";
  
  public static final String IDCODE_VALIDATOR = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
  
  public static final String INTERGAL_INFO_BEAN = "intergal_info_bean";
  
  public static final String IS_FRIST_LUCHER_APP = "is_frist_lucher_app";
  
  public static final String IS_LOAD_NORMAL_APP = "is_load_normal_app";
  
  public static final String IS_QUREY_SEC_LUCHER_APP = "is_qurey_sec_lucher_app";
  
  public static final String IS_SEC_LUCHER_APP = "is_sec_lucher_app";
  
  public static final String IS_SEC_LUCHER_DATE = "is_sec_lucher_date";
  
  public static final String KEY_DYPNSKEY = "key_dypnskey";
  
  public static final String LOCATION_URL = "location_url";
  
  public static final String MOBILE_VALIDATOR = "^1[3,4,5,7,8][0-9]{9}$";
  
  public static final String NUMBER_POINT_OR_NUMBER_VALIDATE = "([1-9]\\d*(\\.\\d*[1-9])?)|(0\\.\\d*[1-9])";
  
  public static final String NUMBER_POINT_VALIDATE = "[^\\d.]";
  
  public static final String PASSWROD_VALIDATOR = "^[\\w\\W]{6,16}$";
  
  public static final String PAY_BEAN = "pay_bean";
  
  public static final String SEQUENCE_RANK = "sequence_rank";
  
  public static final String SP_DATA4DOWNLOAD_IS_OVER = "download_data4_is_over";
  
  public static final String SP_DOWNLOAD_IS_OVER = "download_is_over";
  
  public static final String SP_DOWNLOAD_STATUS = "download_status";
  
  public static final String SP_DOWNLOAD_URL = "download_url";
  
  public static final String SP_ICON_PACKET = "icon_packet";
  
  public static final String SP_IS_64_BIT = "is_64_bit";
  
  public static final String SP_IS_SVIP = "is_svip";
  
  public static final String SP_IS_SYS_APP = "is_sys_app";
  
  public static final String SP_MY_ALWAYSVIP = "my_alwaysvip";
  
  public static final String SP_MY_APP_COUNT = "my_app_count";
  
  public static final String SP_MY_EXPRIED_TIME = "my_expried_time";
  
  public static final String SP_MY_EXPRIED_TIME_STR = "my_expried_time_str";
  
  public static final String SP_MY_INTERGAL = "my_intergal";
  
  public static final String SP_MY_MOBILE = "my_mobile";
  
  public static final String SP_PACKAGE_USERID = "package_userid";
  
  public static final String SP_SHOW_LOCATION = "show_location";
  
  public static final String VERIFY_CODE_VALIDATE = "^\\d{6}$";
  
  public static final String WECHAT_APPID = "wxff78878e744f199f";
  
  public static final String WECHAT_SECRET = "0676711f28136191fa3b2f444d2065ea";
  
  public static final String ZIPCODE_VALIDATE = "^[1-9]\\d{5}(?!\\d)";
  
  public static int appUserId;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\Constants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */