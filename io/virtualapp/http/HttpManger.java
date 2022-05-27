package io.virtualapp.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import io.virtualapp.App;
import io.virtualapp.Utils.AppUtils;
import io.virtualapp.Utils.ChannelUtils;
import io.virtualapp.Utils.SPUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpManger {
  private static final String ALIPAY = "/pay/alipay/genorder";
  
  public static final String BASE_DOMAIN = "http://api.cellocation.com:81/";
  
  private static final String BASE_URL = "http://fee.ymzer.com/api/";
  
  private static final String COPY = "index.php?m=Home&c=copyyydkb&a=";
  
  private static final String COPY_URL = "http://copy.ymzer.com/api/";
  
  public static String KEY_ALIPAY = "alipay";
  
  public static String KEY_AUTO_LOGIN = "getusermobile";
  
  public static String KEY_CHECK_CODE = "checkcode";
  
  public static String KEY_CHECK_SIGN = "checksign";
  
  public static String KEY_DATA_URL = "getdataurlver";
  
  public static String KEY_DISCLAIMER = "disclaimer";
  
  public static String KEY_FEELIST = "feelist";
  
  public static String KEY_FINDLOGLIST = "findloglist";
  
  public static String KEY_FIND_VIP_TRANSFER = "findviptransfer";
  
  public static String KEY_GETAPPINFO = "getappinfo";
  
  public static String KEY_GETAPPLIST = "getapplist";
  
  public static String KEY_GETBIZDATA = "getbizdata";
  
  public static String KEY_GETBLACK = "getblack";
  
  public static String KEY_GETINFO = "getinfo";
  
  public static String KEY_GETSEQUENCE = "getsequence";
  
  public static String KEY_LOGIN = "login";
  
  public static String KEY_LOGOFF = "logoff";
  
  public static String KEY_PAY_TYPE = "pay_type";
  
  public static String KEY_PLUGIN_LIST = "getpluginlist";
  
  public static String KEY_QUIT = "quit";
  
  public static String KEY_REGIST = "regist";
  
  public static String KEY_REGISTERWX = "registerwx";
  
  public static String KEY_RENEW = "renew";
  
  public static String KEY_RENEWBIZLIST = "renewbizlist";
  
  public static String KEY_RENEWUSERACCREDIT = "renewuseraccredit";
  
  public static String KEY_REPORTINFO = "reportinfover";
  
  public static String KEY_REPORTUSERACCREDIT = "reportuseraccredit";
  
  public static String KEY_REPORT_VIP = "reportvip";
  
  public static String KEY_REWARD_LIST = "getrewardlist";
  
  public static String KEY_SEND_CODE = "sendcode";
  
  public static String KEY_SVIP_LIST = "getsviprenewlist";
  
  public static String KEY_UPDATE = "appupgrade";
  
  public static String KEY_USER_INFO = "getuserinfo";
  
  public static String KEY_USE_FCODE = "usefcode";
  
  public static String KEY_VIPUPGRADE_LIST = "getvipupgradelist";
  
  public static String KEY_VIP_ACTIVATE = "vipactivate";
  
  public static String KEY_VIP_GIFT = "vipgift";
  
  public static String KEY_VIP_LIST = "getviprenewlist";
  
  public static String KEY_VIP_TRANSFER = "viptransfer";
  
  public static String KEY_WECHAT_PAY = "wx_pay";
  
  private static final String PAY_TYPE = "pay/pay/paytype";
  
  private static final String SMS_URL = "http://sms.ymzer.com/api/sms/";
  
  private static final String SYS = "sys/";
  
  private static String TAG = "HttpManger";
  
  private static final String USER = "sys/user/";
  
  private static final String WECHATPAY = "/pay/wx/genorder";
  
  private ProgressDialog dialog;
  
  JsonHttpResponseHandler httpGetResponseHandler = new JsonHttpResponseHandler() {
      public void onFailure(int param1Int, Header[] param1ArrayOfHeader, String param1String, Throwable param1Throwable) {
        super.onFailure(param1Int, param1ArrayOfHeader, param1String, param1Throwable);
        if (HttpManger.this.dialog != null && HttpManger.this.dialog.isShowing())
          HttpManger.this.dialog.dismiss(); 
        Toast.makeText((Context)App.getApp(), "网络请求失败", 1).show();
        Log.i(HttpManger.TAG, param1String);
      }
      
      public void onSuccess(int param1Int, Header[] param1ArrayOfHeader, JSONArray param1JSONArray) {
        super.onSuccess(param1Int, param1ArrayOfHeader, param1JSONArray);
        if (HttpManger.this.dialog != null && HttpManger.this.dialog.isShowing())
          HttpManger.this.dialog.dismiss(); 
        try {
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("{");
          stringBuilder.append("data:");
          stringBuilder.append(param1JSONArray.toString());
          stringBuilder.append("}");
          JSONObject jSONObject = new JSONObject();
          this(stringBuilder.toString());
          String str = HttpManger.TAG;
          stringBuilder = new StringBuilder();
          this();
          stringBuilder.append(HttpManger.this.key);
          stringBuilder.append(":");
          stringBuilder.append(jSONObject.toString());
          Log.i(str, stringBuilder.toString());
          HttpManger.this.mHttpCall.onSuccess(HttpManger.this.key, jSONObject);
        } catch (JSONException jSONException) {
          jSONException.printStackTrace();
          Toast.makeText((Context)App.getApp(), "网络请求失败", 1).show();
          Log.i(HttpManger.TAG, jSONException.getMessage());
        } 
        if (HttpManger.this.mHttpCall != null)
          HttpManger.this.mHttpCall.onError(); 
      }
    };
  
  JsonHttpResponseHandler httpResponseHandler = new JsonHttpResponseHandler() {
      public void onFailure(int param1Int, Header[] param1ArrayOfHeader, String param1String, Throwable param1Throwable) {
        super.onFailure(param1Int, param1ArrayOfHeader, param1String, param1Throwable);
        if (HttpManger.this.dialog != null && HttpManger.this.dialog.isShowing())
          HttpManger.this.dialog.dismiss(); 
        Toast.makeText((Context)App.getApp(), "网络请求失败", 1).show();
        Log.i(HttpManger.TAG, param1String);
      }
      
      public void onSuccess(int param1Int, Header[] param1ArrayOfHeader, JSONObject param1JSONObject) {
        super.onSuccess(param1Int, param1ArrayOfHeader, param1JSONObject);
        if (HttpManger.this.dialog != null && HttpManger.this.dialog.isShowing())
          HttpManger.this.dialog.dismiss(); 
        String str = HttpManger.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HttpManger.this.key);
        stringBuilder.append(":");
        stringBuilder.append(param1JSONObject.toString());
        Log.i(str, stringBuilder.toString());
        try {
          HttpManger.this.mHttpCall.onSuccess(HttpManger.this.key, param1JSONObject);
          Log.i(HttpManger.TAG, param1JSONObject.getString("info"));
        } catch (JSONException jSONException) {
          jSONException.printStackTrace();
          Toast.makeText((Context)App.getApp(), "网络请求失败", 1).show();
          Log.i(HttpManger.TAG, jSONException.getMessage());
        } 
        if (HttpManger.this.mHttpCall != null)
          HttpManger.this.mHttpCall.onError(); 
      }
    };
  
  private String key = "";
  
  private HttpCall mHttpCall;
  
  public HttpManger(HttpCall paramHttpCall) {
    this.mHttpCall = paramHttpCall;
  }
  
  public HttpManger(HttpCall paramHttpCall, Context paramContext, boolean paramBoolean) {
    this.mHttpCall = paramHttpCall;
    ProgressDialog progressDialog = new ProgressDialog(paramContext);
    this.dialog = progressDialog;
    progressDialog.setTitle("正在加载,请稍后......");
    this.dialog.show();
  }
  
  public void autoLogin(String paramString) {
    this.key = KEY_AUTO_LOGIN;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    requestParams.put("accesstoken", paramString);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://sms.ymzer.com/api/sms/");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void checkCode(String paramString1, String paramString2) {
    this.key = KEY_CHECK_CODE;
    RequestParams requestParams = new RequestParams();
    requestParams.put("tel", paramString1);
    requestParams.put("code", paramString2);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://sms.ymzer.com/api/sms/");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void checkSign(String paramString) {
    this.key = KEY_CHECK_SIGN;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    requestParams.put("mobile", paramString);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void disclaimer() {
    this.key = KEY_DISCLAIMER;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://fee.ymzer.com/api/sys/");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void findVipTransfer() {
    this.key = KEY_FIND_VIP_TRANSFER;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getAliPayInfo(String paramString1, String paramString2) {
    this.key = KEY_ALIPAY;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    requestParams.put("total_amount", paramString1);
    requestParams.put("bizcode", paramString2);
    HttpClient.post("http://fee.ymzer.com/api//pay/alipay/genorder", requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getAppInfo(String paramString1, String paramString2) {
    this.key = KEY_GETAPPINFO;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    requestParams.put("packet", paramString1);
    requestParams.put("appname", paramString2);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getAppList() {
    this.key = KEY_GETAPPLIST;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getBlack() {
    this.key = KEY_GETBLACK;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getDataUrl() {
    this.key = KEY_DATA_URL;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    requestParams.put("ver", AppUtils.getVersionCode((Context)App.getApp()));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getFeeList() {
    this.key = KEY_FEELIST;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getInfo(String paramString1, String paramString2) {
    this.key = KEY_GETINFO;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    requestParams.put("packet", paramString1);
    requestParams.put("uuid", paramString2);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getLogList() {
    this.key = KEY_FINDLOGLIST;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getPayType() {
    this.key = KEY_PAY_TYPE;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    HttpClient.post("http://fee.ymzer.com/api/pay/pay/paytype", requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getPluginList() {
    this.key = KEY_PLUGIN_LIST;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getRenewBizList() {
    this.key = KEY_RENEWBIZLIST;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getRewardList() {
    this.key = KEY_REWARD_LIST;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getSequence() {
    this.key = KEY_GETSEQUENCE;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getSvipList() {
    this.key = KEY_SVIP_LIST;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getVipList() {
    this.key = KEY_VIP_LIST;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getVipupgradeList() {
    this.key = KEY_VIPUPGRADE_LIST;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getbizdata() {
    this.key = KEY_GETBIZDATA;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://fee.ymzer.com/api/sys/user/");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void getuserinfo() {
    this.key = KEY_USER_INFO;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://fee.ymzer.com/api/sys/user/");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void login() {
    String str1 = "";
    this.key = KEY_LOGIN;
    RequestParams requestParams = new RequestParams();
    try {
      String str = AppUtils.getImei((Context)App.getApp());
      str2 = AppUtils.getImsi((Context)App.getApp());
      str1 = str;
    } catch (Exception exception) {
      str2 = "";
    } 
    requestParams.put("imei", str1);
    requestParams.put("imsi", str2);
    requestParams.put("pseudouniqueid", AppUtils.getUniquePsuedoID());
    String str2 = Build.BRAND;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(str2);
    stringBuilder1.append(" ");
    stringBuilder1.append(Build.MODEL);
    stringBuilder1.append("_Android ");
    stringBuilder1.append(Build.VERSION.RELEASE);
    requestParams.put("devicetype", stringBuilder1.toString());
    requestParams.put("mac", AppUtils.getLocalMacAddressFromWifiInfo((Context)App.getApp()));
    requestParams.put("bd", ChannelUtils.getAppMetaData((Context)App.getApp(), "BaiduMobAd_CHANNEL"));
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    requestParams.put("ver", AppUtils.getVersionCode((Context)App.getApp()));
    requestParams.put("packet", App.getApp().getPackageName());
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("http://fee.ymzer.com/api/sys/user/");
    stringBuilder2.append(this.key);
    HttpClient.post(stringBuilder2.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void logoff() {
    this.key = KEY_LOGOFF;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    requestParams.put("packet", App.getApp().getPackageName());
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void quit() {
    this.key = KEY_QUIT;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    requestParams.put("packet", App.getApp().getPackageName());
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void reWifi(String paramString1, double paramDouble1, double paramDouble2, int paramInt, String paramString2, String paramString3) {
    this.key = KEY_USER_INFO;
    RequestParams requestParams = new RequestParams();
    requestParams.put("lat", Double.valueOf(paramDouble1));
    requestParams.put("lon", Double.valueOf(paramDouble2));
    requestParams.put("number", paramInt);
    requestParams.put("incoord", paramString2);
    requestParams.put("coord", paramString3);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString1);
    stringBuilder.append("rewifi/?");
    HttpClient.get(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpGetResponseHandler);
  }
  
  public void recell(String paramString1, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2, int paramInt3, String paramString2, String paramString3) {
    this.key = KEY_USER_INFO;
    RequestParams requestParams = new RequestParams();
    requestParams.put("lat", Double.valueOf(paramDouble1));
    requestParams.put("lon", Double.valueOf(paramDouble2));
    requestParams.put("number", paramInt1);
    requestParams.put("mnc", paramInt2);
    requestParams.put("radius", paramInt3);
    requestParams.put("incoord", paramString2);
    requestParams.put("coord", paramString3);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString1);
    stringBuilder.append("recell/?");
    HttpClient.get(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpGetResponseHandler);
  }
  
  public void regist() {
    String str1 = "";
    this.key = KEY_REGIST;
    RequestParams requestParams = new RequestParams();
    try {
      String str = AppUtils.getImei((Context)App.getApp());
      str2 = AppUtils.getImsi((Context)App.getApp());
      str1 = str;
    } catch (Exception exception) {
      str2 = "";
    } 
    requestParams.put("imei", str1);
    requestParams.put("imsi", str2);
    requestParams.put("pseudouniqueid", AppUtils.getUniquePsuedoID());
    String str2 = Build.BRAND;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(str2);
    stringBuilder1.append(" ");
    stringBuilder1.append(Build.MODEL);
    stringBuilder1.append("_Android ");
    stringBuilder1.append(Build.VERSION.RELEASE);
    requestParams.put("devicetype", stringBuilder1.toString());
    requestParams.put("mac", AppUtils.getLocalMacAddressFromWifiInfo((Context)App.getApp()));
    requestParams.put("packet", App.getApp().getPackageName());
    requestParams.put("ver", AppUtils.getVersionCode((Context)App.getApp()));
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    requestParams.put("bd", ChannelUtils.getAppMetaData((Context)App.getApp(), "BaiduMobAd_CHANNEL"));
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("http://fee.ymzer.com/api/sys/user/");
    stringBuilder2.append(this.key);
    HttpClient.post(stringBuilder2.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void registerwx(String paramString1, String paramString2, String paramString3) {
    this.key = KEY_REGISTERWX;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    requestParams.put("wxid", paramString1);
    requestParams.put("icon", paramString2);
    requestParams.put("nickname", paramString3);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://fee.ymzer.com/api/sys/user/");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void renew(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
    this.key = KEY_RENEW;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    requestParams.put("act", paramString1);
    requestParams.put("packet", paramString2);
    requestParams.put("appname", paramString3);
    requestParams.put("uuid", paramString4);
    requestParams.put("bizid", paramString5);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void renewUserAccredit(String paramString) {
    this.key = KEY_RENEWUSERACCREDIT;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    requestParams.put("bizid", paramString);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void reportInfo(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4) {
    this.key = KEY_REPORTINFO;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    requestParams.put("act", paramInt);
    requestParams.put("packet", paramString1);
    requestParams.put("appname", paramString2);
    requestParams.put("uuid", paramString3);
    requestParams.put("packetver", paramString4);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void reportUserAccredit() {
    this.key = KEY_REPORTUSERACCREDIT;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void reportVip() {
    this.key = KEY_REPORT_VIP;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void sendCode(String paramString) {
    this.key = KEY_SEND_CODE;
    RequestParams requestParams = new RequestParams();
    requestParams.put("tel", paramString);
    requestParams.put("packet", App.getApp().getPackageName());
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://sms.ymzer.com/api/sms/");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void updateApk() {
    this.key = KEY_UPDATE;
    RequestParams requestParams = new RequestParams();
    requestParams.put("ver", AppUtils.getVersionCode((Context)App.getApp()));
    requestParams.put("packet", App.getApp().getPackageName());
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://fee.ymzer.com/api/sys/");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void usefcode(String paramString) {
    this.key = KEY_USE_FCODE;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    requestParams.put("fcode", paramString);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void vipActivate(String paramString) {
    this.key = KEY_VIP_ACTIVATE;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    requestParams.put("code", paramString);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void vipGift(String paramString1, String paramString2) {
    this.key = KEY_VIP_GIFT;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    requestParams.put("days", paramString1);
    requestParams.put("optcode", paramString2);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
  
  public void vipTransfer() {
    this.key = KEY_VIP_TRANSFER;
    RequestParams requestParams = new RequestParams();
    requestParams.put("token", SPUtils.get((Context)App.getApp(), "token"));
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=copyyydkb&a=");
    stringBuilder.append(this.key);
    HttpClient.post(stringBuilder.toString(), requestParams, (AsyncHttpResponseHandler)this.httpResponseHandler);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\http\HttpManger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */