package com.tencent.mm.opensdk.openapi;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.stub.StubApp;
import com.tencent.mm.opensdk.channel.MMessageActV2;
import com.tencent.mm.opensdk.channel.a.a;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.AddCardToWXCardPackage;
import com.tencent.mm.opensdk.modelbiz.ChooseCardFromWXCardPackage;
import com.tencent.mm.opensdk.modelbiz.CreateChatroom;
import com.tencent.mm.opensdk.modelbiz.HandleScanResult;
import com.tencent.mm.opensdk.modelbiz.IWXChannelJumpInfo;
import com.tencent.mm.opensdk.modelbiz.JoinChatroom;
import com.tencent.mm.opensdk.modelbiz.OpenWebview;
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage;
import com.tencent.mm.opensdk.modelbiz.SubscribeMiniProgramMsg;
import com.tencent.mm.opensdk.modelbiz.WXChannelBaseJumpInfo;
import com.tencent.mm.opensdk.modelbiz.WXChannelJumpMiniProgramInfo;
import com.tencent.mm.opensdk.modelbiz.WXChannelJumpUrlInfo;
import com.tencent.mm.opensdk.modelbiz.WXChannelOpenFeed;
import com.tencent.mm.opensdk.modelbiz.WXChannelOpenLive;
import com.tencent.mm.opensdk.modelbiz.WXChannelOpenProfile;
import com.tencent.mm.opensdk.modelbiz.WXChannelShareVideo;
import com.tencent.mm.opensdk.modelbiz.WXInvoiceAuthInsert;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgramWithToken;
import com.tencent.mm.opensdk.modelbiz.WXLaunchWxaRedirectingPage;
import com.tencent.mm.opensdk.modelbiz.WXNontaxPay;
import com.tencent.mm.opensdk.modelbiz.WXOpenBusinessView;
import com.tencent.mm.opensdk.modelbiz.WXOpenBusinessWebview;
import com.tencent.mm.opensdk.modelbiz.WXOpenCustomerServiceChat;
import com.tencent.mm.opensdk.modelbiz.WXPayInsurance;
import com.tencent.mm.opensdk.modelbiz.WXPreloadMiniProgram;
import com.tencent.mm.opensdk.modelbiz.WXPreloadMiniProgramEnvironment;
import com.tencent.mm.opensdk.modelbiz.WXQRCodePay;
import com.tencent.mm.opensdk.modelmsg.GetMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.LaunchFromWX;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.SendTdiAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelpay.JumpToOfflinePay;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.modelpay.WXJointPay;
import com.tencent.mm.opensdk.utils.ILog;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

class BaseWXApiImplV10 implements IWXAPI {
  protected static final String TAG = "MicroMsg.SDK.WXApiImplV10";
  
  private static String wxappPayEntryClassname;
  
  protected String appId;
  
  protected boolean checkSignature = false;
  
  protected Context context;
  
  protected boolean detached = false;
  
  private int launchMode = 2;
  
  private int wxSdkVersion;
  
  BaseWXApiImplV10(Context paramContext, String paramString, boolean paramBoolean, int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<init>, appId = ");
    stringBuilder.append(paramString);
    stringBuilder.append(", checkSignature = ");
    stringBuilder.append(paramBoolean);
    stringBuilder.append(", launchMode = ");
    stringBuilder.append(paramInt);
    Log.d("MicroMsg.SDK.WXApiImplV10", stringBuilder.toString());
    this.context = paramContext;
    this.appId = paramString;
    this.checkSignature = paramBoolean;
    this.launchMode = paramInt;
    b.a = StubApp.getOrigApplicationContext(paramContext.getApplicationContext());
  }
  
  private boolean checkSumConsistent(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    String str;
    if (paramArrayOfbyte1 == null || paramArrayOfbyte1.length == 0 || paramArrayOfbyte2 == null || paramArrayOfbyte2.length == 0) {
      str = "checkSumConsistent fail, invalid arguments";
      Log.e("MicroMsg.SDK.WXApiImplV10", str);
      return false;
    } 
    if (str.length != paramArrayOfbyte2.length) {
      str = "checkSumConsistent fail, length is different";
      Log.e("MicroMsg.SDK.WXApiImplV10", str);
      return false;
    } 
    for (byte b = 0; b < str.length; b++) {
      if (str[b] != paramArrayOfbyte2[b])
        return false; 
    } 
    return true;
  }
  
  private boolean createChatroom(Context paramContext, Bundle paramBundle) {
    launchWXIfNeed();
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/createChatroom"), null, null, new String[] { this.appId, paramBundle.getString("_wxapi_basereq_transaction", ""), paramBundle.getString("_wxapi_create_chatroom_group_id", ""), paramBundle.getString("_wxapi_create_chatroom_chatroom_name", ""), paramBundle.getString("_wxapi_create_chatroom_chatroom_nickname", ""), paramBundle.getString("_wxapi_create_chatroom_ext_msg", ""), paramBundle.getString("_wxapi_basereq_openid", "") }null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private String finderShareVideoJumpInfoToString(IWXChannelJumpInfo paramIWXChannelJumpInfo) {
    try {
      JSONObject jSONObject = new JSONObject();
      this();
      jSONObject.put("jumpType", paramIWXChannelJumpInfo.type());
      boolean bool = paramIWXChannelJumpInfo instanceof WXChannelBaseJumpInfo;
      if (bool) {
        String str1;
        String str2;
        jSONObject.put("wording", ((WXChannelBaseJumpInfo)paramIWXChannelJumpInfo).wording);
        jSONObject.put("extra", ((WXChannelBaseJumpInfo)paramIWXChannelJumpInfo).extra);
        bool = paramIWXChannelJumpInfo instanceof WXChannelJumpMiniProgramInfo;
        if (bool) {
          jSONObject.put("username", ((WXChannelJumpMiniProgramInfo)paramIWXChannelJumpInfo).username);
          str2 = ((WXChannelJumpMiniProgramInfo)paramIWXChannelJumpInfo).path;
          str1 = "path";
        } else if (str1 instanceof WXChannelJumpUrlInfo) {
          str2 = ((WXChannelJumpUrlInfo)str1).url;
          str1 = "url";
        } else {
          return jSONObject.toString();
        } 
        jSONObject.put(str1, str2);
      } 
      return jSONObject.toString();
    } catch (Exception exception) {
      return "";
    } 
  }
  
  private String getTokenFromWX(Context paramContext) {
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/genTokenForOpenSdk"), null, null, new String[] { this.appId, "638058496" }, null);
    if (cursor != null && cursor.moveToFirst()) {
      String str = cursor.getString(0);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("getTokenFromWX token is ");
      stringBuilder.append(str);
      Log.i("MicroMsg.SDK.WXApiImplV10", stringBuilder.toString());
      cursor.close();
      return str;
    } 
    Log.e("MicroMsg.SDK.WXApiImplV10", "getTokenFromWX , token is null , if your app targetSdkVersion >= 30, include 'com.tencent.mm' in a set of <package> elements inside the <queries> element");
    return null;
  }
  
  private boolean handleWxInternalRespType(String paramString, IWXAPIEventHandler paramIWXAPIEventHandler) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("handleWxInternalRespType, extInfo = ");
    stringBuilder.append(paramString);
    Log.i("MicroMsg.SDK.WXApiImplV10", stringBuilder.toString());
    try {
      SubscribeMessage.Resp resp1;
      String str1;
      WXNontaxPay.Resp resp;
      Uri uri = Uri.parse(paramString);
      String str2 = uri.getQueryParameter("wx_internal_resptype");
      StringBuilder stringBuilder1 = new StringBuilder();
      this();
      stringBuilder1.append("handleWxInternalRespType, respType = ");
      stringBuilder1.append(str2);
      Log.i("MicroMsg.SDK.WXApiImplV10", stringBuilder1.toString());
      if (b.b(str2)) {
        Log.e("MicroMsg.SDK.WXApiImplV10", "handleWxInternalRespType fail, respType is null");
        return false;
      } 
      boolean bool = str2.equals("subscribemessage");
      if (bool) {
        resp1 = new SubscribeMessage.Resp();
        this();
        String str = uri.getQueryParameter("ret");
        if (str != null && str.length() > 0)
          ((BaseResp)resp1).errCode = b.a(str, 0); 
        ((BaseResp)resp1).openId = uri.getQueryParameter("openid");
        resp1.templateID = uri.getQueryParameter("template_id");
        resp1.scene = b.a(uri.getQueryParameter("scene"), 0);
        resp1.action = uri.getQueryParameter("action");
        resp1.reserved = uri.getQueryParameter("reserved");
        paramIWXAPIEventHandler.onResp((BaseResp)resp1);
        return true;
      } 
      bool = resp1.contains("invoice_auth_insert");
      if (bool) {
        WXInvoiceAuthInsert.Resp resp2 = new WXInvoiceAuthInsert.Resp();
        this();
        str1 = uri.getQueryParameter("ret");
        if (str1 != null && str1.length() > 0)
          ((BaseResp)resp2).errCode = b.a(str1, 0); 
        resp2.wxOrderId = uri.getQueryParameter("wx_order_id");
        paramIWXAPIEventHandler.onResp((BaseResp)resp2);
        return true;
      } 
      if (str1.contains("payinsurance")) {
        WXPayInsurance.Resp resp2 = new WXPayInsurance.Resp();
        this();
        str1 = uri.getQueryParameter("ret");
        if (str1 != null && str1.length() > 0)
          ((BaseResp)resp2).errCode = b.a(str1, 0); 
        resp2.wxOrderId = uri.getQueryParameter("wx_order_id");
        paramIWXAPIEventHandler.onResp((BaseResp)resp2);
        return true;
      } 
      if (str1.contains("nontaxpay")) {
        resp = new WXNontaxPay.Resp();
        this();
        String str = uri.getQueryParameter("ret");
        if (str != null && str.length() > 0)
          ((BaseResp)resp).errCode = b.a(str, 0); 
        resp.wxOrderId = uri.getQueryParameter("wx_order_id");
        paramIWXAPIEventHandler.onResp((BaseResp)resp);
        return true;
      } 
      if ("subscribeminiprogrammsg".equals(resp) || "5".equals(resp)) {
        SubscribeMiniProgramMsg.Resp resp2 = new SubscribeMiniProgramMsg.Resp();
        this();
        String str = uri.getQueryParameter("ret");
        if (str != null && str.length() > 0)
          ((BaseResp)resp2).errCode = b.a(str, 0); 
        ((BaseResp)resp2).openId = uri.getQueryParameter("openid");
        resp2.unionId = uri.getQueryParameter("unionid");
        resp2.nickname = uri.getQueryParameter("nickname");
        ((BaseResp)resp2).errStr = uri.getQueryParameter("errmsg");
        paramIWXAPIEventHandler.onResp((BaseResp)resp2);
        return true;
      } 
      Log.e("MicroMsg.SDK.WXApiImplV10", "this open sdk version not support the request type");
    } catch (Exception exception) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("handleWxInternalRespType fail, ex = ");
      stringBuilder1.append(exception.getMessage());
      Log.e("MicroMsg.SDK.WXApiImplV10", stringBuilder1.toString());
    } 
    return false;
  }
  
  private boolean joinChatroom(Context paramContext, Bundle paramBundle) {
    launchWXIfNeed();
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/joinChatroom"), null, null, new String[] { this.appId, paramBundle.getString("_wxapi_basereq_transaction", ""), paramBundle.getString("_wxapi_join_chatroom_group_id", ""), paramBundle.getString("_wxapi_join_chatroom_chatroom_nickname", ""), paramBundle.getString("_wxapi_join_chatroom_ext_msg", ""), paramBundle.getString("_wxapi_basereq_openid", "") }null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private void launchWXIfNeed() {
    if (Build.VERSION.SDK_INT >= 29 && this.launchMode == 2) {
      launchWXUsingPendingIntent();
    } else {
      openWXApp();
    } 
  }
  
  private void launchWXUsingPendingIntent() {
    if (!this.detached) {
      if (!isWXAppInstalled()) {
        Log.e("MicroMsg.SDK.WXApiImplV10", "openWXApp failed, not installed or signature check failed");
        return;
      } 
      try {
        Log.i("MicroMsg.SDK.WXApiImplV10", "launchWXUsingPendingIntent");
        Intent intent = this.context.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
        PendingIntent pendingIntent = PendingIntent.getActivity(this.context, 1, intent, 134217728);
        Context context = this.context;
        PendingIntent.OnFinished onFinished = new PendingIntent.OnFinished() {
            public void onSendFinished(PendingIntent param1PendingIntent, Intent param1Intent, int param1Int, String param1String, Bundle param1Bundle) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("onSendFinished resultCode: ");
              stringBuilder.append(param1Int);
              stringBuilder.append(", resultData: ");
              stringBuilder.append(param1String);
              Log.d("MicroMsg.SDK.WXApiImplV10", stringBuilder.toString());
            }
          };
        super(this);
        pendingIntent.send(context, 2, null, onFinished, null);
      } catch (Exception exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("launchWXUsingPendingIntent pendingIntent send failed: ");
        stringBuilder.append(exception.getMessage());
        Log.e("MicroMsg.SDK.WXApiImplV10", stringBuilder.toString());
        openWXApp();
      } 
      return;
    } 
    throw new IllegalStateException("openWXApp fail, WXMsgImpl has been detached");
  }
  
  private boolean sendAddCardToWX(Context paramContext, Bundle paramBundle) {
    launchWXIfNeed();
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/addCardToWX"), null, null, new String[] { this.appId, paramBundle.getString("_wxapi_add_card_to_wx_card_list"), paramBundle.getString("_wxapi_basereq_transaction") }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendChooseCardFromWX(Context paramContext, Bundle paramBundle) {
    launchWXIfNeed();
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/chooseCardFromWX"), null, null, new String[] { paramBundle.getString("_wxapi_choose_card_from_wx_card_app_id"), paramBundle.getString("_wxapi_choose_card_from_wx_card_location_id"), paramBundle.getString("_wxapi_choose_card_from_wx_card_sign_type"), paramBundle.getString("_wxapi_choose_card_from_wx_card_card_sign"), paramBundle.getString("_wxapi_choose_card_from_wx_card_time_stamp"), paramBundle.getString("_wxapi_choose_card_from_wx_card_nonce_str"), paramBundle.getString("_wxapi_choose_card_from_wx_card_card_id"), paramBundle.getString("_wxapi_choose_card_from_wx_card_card_type"), paramBundle.getString("_wxapi_choose_card_from_wx_card_can_multi_select"), paramBundle.getString("_wxapi_basereq_transaction") }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendFinderOpenFeed(Context paramContext, BaseReq paramBaseReq) {
    launchWXIfNeed();
    Log.i("MicroMsg.SDK.WXApiImplV10", "sendFinderOpenFeed");
    WXChannelOpenFeed.Req req = (WXChannelOpenFeed.Req)paramBaseReq;
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/finderOpenFeed"), null, null, new String[] { this.appId, req.feedID, req.nonceID, String.valueOf(req.notGetReleatedList) }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendFinderOpenLive(Context paramContext, BaseReq paramBaseReq) {
    launchWXIfNeed();
    Log.i("MicroMsg.SDK.WXApiImplV10", "sendFinderOpenLive");
    WXChannelOpenLive.Req req = (WXChannelOpenLive.Req)paramBaseReq;
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/finderOpenLive"), null, null, new String[] { this.appId, req.feedID, req.nonceID }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendFinderOpenProfile(Context paramContext, BaseReq paramBaseReq) {
    launchWXIfNeed();
    Log.i("MicroMsg.SDK.WXApiImplV10", "sendFinderOpenProfile");
    WXChannelOpenProfile.Req req = (WXChannelOpenProfile.Req)paramBaseReq;
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/finderOpenProfile"), null, null, new String[] { this.appId, req.userName }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendFinderShareVideo(Context paramContext, BaseReq paramBaseReq) {
    launchWXIfNeed();
    Log.i("MicroMsg.SDK.WXApiImplV10", "sendFinderShareVideo");
    WXChannelShareVideo.Req req = (WXChannelShareVideo.Req)paramBaseReq;
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/finderShareVideo"), null, null, new String[] { this.appId, req.videoPath, "", "", req.extData, finderShareVideoJumpInfoToString(req.jumpInfo) }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendHandleScanResult(Context paramContext, Bundle paramBundle) {
    launchWXIfNeed();
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/handleScanResult"), null, null, new String[] { this.appId, paramBundle.getString("_wxapi_scan_qrcode_result") }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendInvoiceAuthInsert(Context paramContext, BaseReq paramBaseReq) {
    launchWXIfNeed();
    WXInvoiceAuthInsert.Req req = (WXInvoiceAuthInsert.Req)paramBaseReq;
    ContentResolver contentResolver = paramContext.getContentResolver();
    Uri uri = Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview");
    String str = URLEncoder.encode(String.format("url=%s", new Object[] { URLEncoder.encode(req.url) }));
    Cursor cursor = contentResolver.query(uri, null, null, new String[] { this.appId, String.valueOf(2), str }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendJumpToOfflinePayReq(Context paramContext, Bundle paramBundle) {
    launchWXIfNeed();
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/jumpToOfflinePay"), null, null, new String[] { this.appId }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendLaunchWXMiniprogram(Context paramContext, BaseReq paramBaseReq) {
    launchWXIfNeed();
    WXLaunchMiniProgram.Req req = (WXLaunchMiniProgram.Req)paramBaseReq;
    ContentResolver contentResolver = paramContext.getContentResolver();
    Uri uri = Uri.parse("content://com.tencent.mm.sdk.comm.provider/launchWXMiniprogram");
    String str1 = this.appId;
    String str2 = req.userName;
    String str3 = req.path;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(req.miniprogramType);
    stringBuilder.append("");
    Cursor cursor = contentResolver.query(uri, null, null, new String[] { str1, str2, str3, stringBuilder.toString(), req.extData }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendLaunchWXMiniprogramWithToken(Context paramContext, BaseReq paramBaseReq) {
    launchWXIfNeed();
    WXLaunchMiniProgramWithToken.Req req = (WXLaunchMiniProgramWithToken.Req)paramBaseReq;
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/launchWXMiniprogramWithToken"), null, null, new String[] { this.appId, req.token }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendNonTaxPay(Context paramContext, BaseReq paramBaseReq) {
    launchWXIfNeed();
    WXNontaxPay.Req req = (WXNontaxPay.Req)paramBaseReq;
    ContentResolver contentResolver = paramContext.getContentResolver();
    Uri uri = Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview");
    String str = URLEncoder.encode(String.format("url=%s", new Object[] { URLEncoder.encode(req.url) }));
    Cursor cursor = contentResolver.query(uri, null, null, new String[] { this.appId, String.valueOf(3), str }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendOpenBusiLuckyMoney(Context paramContext, Bundle paramBundle) {
    launchWXIfNeed();
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openBusiLuckyMoney"), null, null, new String[] { this.appId, paramBundle.getString("_wxapi_open_busi_lucky_money_timeStamp"), paramBundle.getString("_wxapi_open_busi_lucky_money_nonceStr"), paramBundle.getString("_wxapi_open_busi_lucky_money_signType"), paramBundle.getString("_wxapi_open_busi_lucky_money_signature"), paramBundle.getString("_wxapi_open_busi_lucky_money_package") }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendOpenBusinessView(Context paramContext, BaseReq paramBaseReq) {
    launchWXIfNeed();
    WXOpenBusinessView.Req req = (WXOpenBusinessView.Req)paramBaseReq;
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openBusinessView"), null, null, new String[] { this.appId, req.businessType, req.query, req.extInfo, ((BaseReq)req).transaction, ((BaseReq)req).openId }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendOpenBusinessWebview(Context paramContext, BaseReq paramBaseReq) {
    String str1;
    launchWXIfNeed();
    WXOpenBusinessWebview.Req req = (WXOpenBusinessWebview.Req)paramBaseReq;
    ContentResolver contentResolver = paramContext.getContentResolver();
    Uri uri = Uri.parse("content://com.tencent.mm.sdk.comm.provider/openBusinessWebview");
    HashMap hashMap = req.queryInfo;
    if (hashMap != null && hashMap.size() > 0) {
      str1 = (new JSONObject(req.queryInfo)).toString();
    } else {
      str1 = "";
    } 
    String str2 = this.appId;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(req.businessType);
    stringBuilder.append("");
    Cursor cursor = contentResolver.query(uri, null, null, new String[] { str2, stringBuilder.toString(), str1 }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendOpenCustomerServiceChat(Context paramContext, BaseReq paramBaseReq) {
    launchWXIfNeed();
    WXOpenCustomerServiceChat.Req req = (WXOpenCustomerServiceChat.Req)paramBaseReq;
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openCustomerServiceChat"), null, null, new String[] { this.appId, req.corpId, req.url }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendOpenRankListReq(Context paramContext, Bundle paramBundle) {
    launchWXIfNeed();
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openRankList"), null, null, new String[0], null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendOpenWebview(Context paramContext, Bundle paramBundle) {
    launchWXIfNeed();
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openWebview"), null, null, new String[] { this.appId, paramBundle.getString("_wxapi_jump_to_webview_url"), paramBundle.getString("_wxapi_basereq_transaction") }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendPayInSurance(Context paramContext, BaseReq paramBaseReq) {
    launchWXIfNeed();
    WXPayInsurance.Req req = (WXPayInsurance.Req)paramBaseReq;
    ContentResolver contentResolver = paramContext.getContentResolver();
    Uri uri = Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview");
    String str = URLEncoder.encode(String.format("url=%s", new Object[] { URLEncoder.encode(req.url) }));
    Cursor cursor = contentResolver.query(uri, null, null, new String[] { this.appId, String.valueOf(4), str }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendPayReq(Context paramContext, Bundle paramBundle) {
    if (wxappPayEntryClassname == null) {
      wxappPayEntryClassname = (new MMSharedPreferences(paramContext)).getString("_wxapp_pay_entry_classname_", null);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("pay, set wxappPayEntryClassname = ");
      stringBuilder.append(wxappPayEntryClassname);
      Log.d("MicroMsg.SDK.WXApiImplV10", stringBuilder.toString());
      if (wxappPayEntryClassname == null)
        try {
          wxappPayEntryClassname = (paramContext.getPackageManager().getApplicationInfo("com.tencent.mm", 128)).metaData.getString("com.tencent.mm.BuildInfo.OPEN_SDK_PAY_ENTRY_CLASSNAME", null);
        } catch (Exception exception) {
          stringBuilder = new StringBuilder();
          stringBuilder.append("get from metaData failed : ");
          stringBuilder.append(exception.getMessage());
          Log.e("MicroMsg.SDK.WXApiImplV10", stringBuilder.toString());
        }  
      if (wxappPayEntryClassname == null) {
        Log.e("MicroMsg.SDK.WXApiImplV10", "pay fail, wxappPayEntryClassname is null");
        return false;
      } 
    } 
    MMessageActV2.Args args = new MMessageActV2.Args();
    args.bundle = paramBundle;
    args.targetPkgName = "com.tencent.mm";
    args.targetClassName = wxappPayEntryClassname;
    args.launchMode = this.launchMode;
    try {
      String str = getTokenFromWX(paramContext);
      if (str != null)
        args.token = str; 
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("getTokenFromWX fail, exception = ");
      stringBuilder.append(exception);
      Log.e("MicroMsg.SDK.WXApiImplV10", stringBuilder.toString());
    } 
    return MMessageActV2.send(paramContext, args);
  }
  
  private boolean sendPreloadWXMiniProgramEnvironment(Context paramContext, BaseReq paramBaseReq) {
    WXPreloadMiniProgramEnvironment.Req req = (WXPreloadMiniProgramEnvironment.Req)paramBaseReq;
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/preloadWXMiniprogramEnvironment"), null, null, new String[] { this.appId, req.extData }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendPreloadWXMiniprogram(Context paramContext, BaseReq paramBaseReq) {
    WXPreloadMiniProgram.Req req = (WXPreloadMiniProgram.Req)paramBaseReq;
    ContentResolver contentResolver = paramContext.getContentResolver();
    Uri uri = Uri.parse("content://com.tencent.mm.sdk.comm.provider/preloadWXMiniprogram");
    String str1 = this.appId;
    String str2 = req.userName;
    String str3 = req.path;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(req.miniprogramType);
    stringBuilder.append("");
    Cursor cursor = contentResolver.query(uri, null, null, new String[] { str1, str2, str3, stringBuilder.toString(), req.extData }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendQRCodePayReq(Context paramContext, BaseReq paramBaseReq) {
    launchWXIfNeed();
    WXQRCodePay.Req req = (WXQRCodePay.Req)paramBaseReq;
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/QRCodePay"), null, null, new String[] { this.appId, req.codeContent, req.extraMsg }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendSubscribeMessage(Context paramContext, BaseReq paramBaseReq) {
    launchWXIfNeed();
    SubscribeMessage.Req req = (SubscribeMessage.Req)paramBaseReq;
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview"), null, null, new String[] { this.appId, String.valueOf(1), String.valueOf(req.scene), req.templateID, req.reserved }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendSubscribeMiniProgramMsg(Context paramContext, BaseReq paramBaseReq) {
    launchWXIfNeed();
    SubscribeMiniProgramMsg.Req req = (SubscribeMiniProgramMsg.Req)paramBaseReq;
    Cursor cursor = paramContext.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview"), null, null, new String[] { this.appId, String.valueOf(5), req.miniProgramAppId }, null);
    if (cursor != null)
      cursor.close(); 
    return true;
  }
  
  private boolean sendToWxaRedirectingPage(Context paramContext, BaseReq paramBaseReq) {
    launchWXIfNeed();
    WXLaunchWxaRedirectingPage.Req req = (WXLaunchWxaRedirectingPage.Req)paramBaseReq;
    ContentResolver contentResolver = paramContext.getContentResolver();
    Uri uri = Uri.parse("content://com.tencent.mm.sdk.comm.provider/launchWxaOpenApiRedirectingPage");
    try {
      ArrayList<String> arrayList = new ArrayList();
      this();
      arrayList.add(0, this.appId);
      arrayList.addAll(Arrays.asList(req.toArray()));
      Cursor cursor = contentResolver.query(uri, null, null, arrayList.<String>toArray(new String[0]), null);
      if (cursor != null)
        cursor.close(); 
      return true;
    } finally {}
  }
  
  public void detach() {
    Log.d("MicroMsg.SDK.WXApiImplV10", "detach");
    this.detached = true;
    this.context = null;
  }
  
  public int getWXAppSupportAPI() {
    if (!this.detached) {
      if (!isWXAppInstalled()) {
        Log.e("MicroMsg.SDK.WXApiImplV10", "open wx app failed, not installed or signature check failed");
        return 0;
      } 
      this.wxSdkVersion = 0;
      final CountDownLatch countDownWait = new CountDownLatch(1);
      b.e.submit(new Runnable() {
            public void run() {
              try {
                MMSharedPreferences mMSharedPreferences = new MMSharedPreferences();
                this(BaseWXApiImplV10.this.context);
                BaseWXApiImplV10.access$002(BaseWXApiImplV10.this, mMSharedPreferences.getInt("_build_info_sdk_int_", 0));
              } catch (Exception exception) {
                Log.w("MicroMsg.SDK.WXApiImplV10", exception.getMessage());
              } 
              countDownWait.countDown();
            }
          });
      try {
        countDownLatch.await(1000L, TimeUnit.MILLISECONDS);
      } catch (InterruptedException interruptedException) {
        Log.w("MicroMsg.SDK.WXApiImplV10", interruptedException.getMessage());
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("wxSdkVersion = ");
      stringBuilder.append(this.wxSdkVersion);
      Log.d("MicroMsg.SDK.WXApiImplV10", stringBuilder.toString());
      if (this.wxSdkVersion == 0)
        try {
          this.wxSdkVersion = (this.context.getPackageManager().getApplicationInfo("com.tencent.mm", 128)).metaData.getInt("com.tencent.mm.BuildInfo.OPEN_SDK_VERSION", 0);
          stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("OPEN_SDK_VERSION = ");
          stringBuilder.append(this.wxSdkVersion);
          Log.d("MicroMsg.SDK.WXApiImplV10", stringBuilder.toString());
        } catch (Exception exception) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("get from metaData failed : ");
          stringBuilder1.append(exception.getMessage());
          Log.e("MicroMsg.SDK.WXApiImplV10", stringBuilder1.toString());
        }  
      return this.wxSdkVersion;
    } 
    throw new IllegalStateException("getWXAppSupportAPI fail, WXMsgImpl has been detached");
  }
  
  public boolean handleIntent(Intent paramIntent, IWXAPIEventHandler paramIWXAPIEventHandler) {
    try {
      if (!WXApiImplComm.isIntentFromWx(paramIntent, "com.tencent.mm.openapi.token")) {
        Log.i("MicroMsg.SDK.WXApiImplV10", "handleIntent fail, intent not from weixin msg");
        return false;
      } 
      if (!this.detached) {
        String str1;
        StringBuilder stringBuilder2;
        WXQRCodePay.Resp resp22;
        WXOpenCustomerServiceChat.Resp resp21;
        WXChannelOpenFeed.Resp resp20;
        WXChannelOpenLive.Resp resp19;
        WXChannelOpenProfile.Resp resp18;
        WXChannelShareVideo.Resp resp17;
        WXPreloadMiniProgramEnvironment.Resp resp16;
        SendTdiAuth.Resp resp15;
        WXLaunchWxaRedirectingPage.Resp resp14;
        WXLaunchMiniProgramWithToken.Resp resp13;
        WXPreloadMiniProgram.Resp resp12;
        WXJointPay.JointPayResp jointPayResp;
        WXOpenBusinessView.Resp resp11;
        WXOpenBusinessWebview.Resp resp10;
        JumpToOfflinePay.Resp resp9;
        WXLaunchMiniProgram.Resp resp8;
        HandleScanResult.Resp resp7;
        ChooseCardFromWXCardPackage.Resp resp6;
        JoinChatroom.Resp resp5;
        CreateChatroom.Resp resp4;
        OpenWebview.Resp resp3;
        AddCardToWXCardPackage.Resp resp2;
        LaunchFromWX.Req req2;
        PayResp payResp;
        ShowMessageFromWX.Req req1;
        GetMessageFromWX.Req req;
        SendMessageToWX.Resp resp1;
        SendAuth.Resp resp;
        String str2 = paramIntent.getStringExtra("_mmessage_content");
        int i = paramIntent.getIntExtra("_mmessage_sdkVersion", 0);
        String str3 = paramIntent.getStringExtra("_mmessage_appPackage");
        if (str3 == null || str3.length() == 0) {
          Log.e("MicroMsg.SDK.WXApiImplV10", "invalid argument");
          return false;
        } 
        if (!checkSumConsistent(paramIntent.getByteArrayExtra("_mmessage_checksum"), a.a(str2, i, str3))) {
          Log.e("MicroMsg.SDK.WXApiImplV10", "checksum fail");
          return false;
        } 
        i = paramIntent.getIntExtra("_wxapi_command_type", 0);
        StringBuilder stringBuilder3 = new StringBuilder();
        this();
        stringBuilder3.append("handleIntent, cmd = ");
        stringBuilder3.append(i);
        Log.i("MicroMsg.SDK.WXApiImplV10", stringBuilder3.toString());
        switch (i) {
          case 38:
            resp22 = new WXQRCodePay.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp22);
            return true;
          case 37:
            resp21 = new WXOpenCustomerServiceChat.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp21);
            return true;
          case 36:
            resp20 = new WXChannelOpenFeed.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp20);
            return true;
          case 35:
            resp19 = new WXChannelOpenLive.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp19);
            return true;
          case 34:
            resp18 = new WXChannelOpenProfile.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp18);
            return true;
          case 33:
            resp17 = new WXChannelShareVideo.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp17);
            return true;
          case 32:
            resp16 = new WXPreloadMiniProgramEnvironment.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp16);
            return true;
          case 31:
            resp15 = new SendTdiAuth.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp15);
            return true;
          case 30:
            resp14 = new WXLaunchWxaRedirectingPage.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp14);
            return true;
          case 29:
            resp13 = new WXLaunchMiniProgramWithToken.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp13);
            return true;
          case 28:
            resp12 = new WXPreloadMiniProgram.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp12);
            return true;
          case 27:
            jointPayResp = new WXJointPay.JointPayResp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)jointPayResp);
            return true;
          case 26:
            resp11 = new WXOpenBusinessView.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp11);
            return true;
          case 25:
            resp10 = new WXOpenBusinessWebview.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp10);
            return true;
          case 24:
            resp9 = new JumpToOfflinePay.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp9);
            return true;
          case 19:
            resp8 = new WXLaunchMiniProgram.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp8);
            return true;
          case 17:
            resp7 = new HandleScanResult.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp7);
            return true;
          case 16:
            resp6 = new ChooseCardFromWXCardPackage.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp6);
            return true;
          case 15:
            resp5 = new JoinChatroom.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp5);
            return true;
          case 14:
            resp4 = new CreateChatroom.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp4);
            return true;
          case 12:
            resp3 = new OpenWebview.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp3);
            return true;
          case 9:
            resp2 = new AddCardToWXCardPackage.Resp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp2);
            return true;
          case 6:
            req2 = new LaunchFromWX.Req();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onReq((BaseReq)req2);
            return true;
          case 5:
            payResp = new PayResp();
            this(paramIntent.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)payResp);
            return true;
          case 4:
            req1 = new ShowMessageFromWX.Req();
            this(paramIntent.getExtras());
            str1 = req1.message.messageExt;
            if (str1 != null && str1.contains("wx_internal_resptype")) {
              boolean bool = handleWxInternalRespType(str1, paramIWXAPIEventHandler);
              stringBuilder2 = new StringBuilder();
              this();
              stringBuilder2.append("handleIntent, extInfo contains wx_internal_resptype, ret = ");
              stringBuilder2.append(bool);
              Log.i("MicroMsg.SDK.WXApiImplV10", stringBuilder2.toString());
              return bool;
            } 
            if (stringBuilder2 != null) {
              boolean bool = stringBuilder2.contains("openbusinesswebview");
              if (bool)
                try {
                  WXOpenBusinessWebview.Resp resp23;
                  Uri uri = Uri.parse((String)stringBuilder2);
                  if (uri != null && "openbusinesswebview".equals(uri.getHost())) {
                    resp23 = new WXOpenBusinessWebview.Resp();
                    this();
                    String str5 = uri.getQueryParameter("ret");
                    if (str5 != null && str5.length() > 0)
                      ((BaseResp)resp23).errCode = b.a(str5, 0); 
                    resp23.resultInfo = uri.getQueryParameter("resultInfo");
                    ((BaseResp)resp23).errStr = uri.getQueryParameter("errmsg");
                    String str4 = uri.getQueryParameter("type");
                    if (str4 != null && str4.length() > 0)
                      resp23.businessType = b.a(str4, 0); 
                    paramIWXAPIEventHandler.onResp((BaseResp)resp23);
                    return true;
                  } 
                  StringBuilder stringBuilder = new StringBuilder();
                  this();
                  stringBuilder.append("not openbusinesswebview %");
                  stringBuilder.append((String)resp23);
                  Log.d("MicroMsg.SDK.WXApiImplV10", stringBuilder.toString());
                } catch (Exception exception) {
                  StringBuilder stringBuilder = new StringBuilder();
                  this();
                  stringBuilder.append("parse fail, ex = ");
                  stringBuilder.append(exception.getMessage());
                  Log.e("MicroMsg.SDK.WXApiImplV10", stringBuilder.toString());
                }  
            } 
            paramIWXAPIEventHandler.onReq((BaseReq)req1);
            return true;
          case 3:
            req = new GetMessageFromWX.Req();
            this(exception.getExtras());
            paramIWXAPIEventHandler.onReq((BaseReq)req);
            return true;
          case 2:
            resp1 = new SendMessageToWX.Resp();
            this(exception.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp1);
            return true;
          case 1:
            resp = new SendAuth.Resp();
            this(exception.getExtras());
            paramIWXAPIEventHandler.onResp((BaseResp)resp);
            return true;
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        this();
        stringBuilder1.append("unknown cmd = ");
        stringBuilder1.append(i);
        Log.e("MicroMsg.SDK.WXApiImplV10", stringBuilder1.toString());
      } else {
        IllegalStateException illegalStateException = new IllegalStateException();
        this("handleIntent fail, WXMsgImpl has been detached");
        throw illegalStateException;
      } 
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("handleIntent fail, ex = ");
      stringBuilder.append(exception.getMessage());
      Log.e("MicroMsg.SDK.WXApiImplV10", stringBuilder.toString());
    } 
    return false;
  }
  
  public boolean isWXAppInstalled() {
    if (!this.detached) {
      boolean bool2;
      boolean bool1 = false;
      try {
        PackageInfo packageInfo = this.context.getPackageManager().getPackageInfo("com.tencent.mm", 64);
        if (packageInfo == null)
          return false; 
        bool2 = WXApiImplComm.validateAppSignature(this.context, packageInfo.signatures, this.checkSignature);
      } catch (Exception exception) {
        bool2 = bool1;
      } 
      return bool2;
    } 
    throw new IllegalStateException("isWXAppInstalled fail, WXMsgImpl has been detached");
  }
  
  public boolean openWXApp() {
    if (!this.detached) {
      String str;
      if (!isWXAppInstalled()) {
        str = "open wx app failed, not installed or signature check failed";
        Log.e("MicroMsg.SDK.WXApiImplV10", str);
        return false;
      } 
      try {
        this.context.startActivity(this.context.getPackageManager().getLaunchIntentForPackage("com.tencent.mm"));
        return true;
      } catch (Exception exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("startActivity fail, exception = ");
        stringBuilder.append(exception.getMessage());
        str = stringBuilder.toString();
      } 
      Log.e("MicroMsg.SDK.WXApiImplV10", str);
      return false;
    } 
    throw new IllegalStateException("openWXApp fail, WXMsgImpl has been detached");
  }
  
  public boolean registerApp(String paramString) {
    return registerApp(paramString, 0L);
  }
  
  public boolean registerApp(String paramString, long paramLong) {
    if (!this.detached) {
      if (!WXApiImplComm.validateAppSignatureForPackage(this.context, "com.tencent.mm", this.checkSignature)) {
        Log.e("MicroMsg.SDK.WXApiImplV10", "register app failed for wechat app signature check failed");
        return false;
      } 
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append("registerApp, appId = ");
      stringBuilder2.append(paramString);
      Log.d("MicroMsg.SDK.WXApiImplV10", stringBuilder2.toString());
      if (paramString != null)
        this.appId = paramString; 
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append("registerApp, appId = ");
      stringBuilder2.append(paramString);
      Log.d("MicroMsg.SDK.WXApiImplV10", stringBuilder2.toString());
      if (paramString != null)
        this.appId = paramString; 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("register app ");
      stringBuilder1.append(this.context.getPackageName());
      Log.d("MicroMsg.SDK.WXApiImplV10", stringBuilder1.toString());
      a.a a = new a.a();
      a.a = "com.tencent.mm";
      a.b = "com.tencent.mm.plugin.openapi.Intent.ACTION_HANDLE_APP_REGISTER";
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("weixin://registerapp?appid=");
      stringBuilder1.append(this.appId);
      a.c = stringBuilder1.toString();
      a.d = paramLong;
      return a.a(this.context, a);
    } 
    throw new IllegalStateException("registerApp fail, WXMsgImpl has been detached");
  }
  
  public boolean sendReq(BaseReq paramBaseReq) {
    // Byte code:
    //   0: aload_0
    //   1: getfield detached : Z
    //   4: ifne -> 1168
    //   7: aload_0
    //   8: getfield context : Landroid/content/Context;
    //   11: ldc_w 'com.tencent.mm'
    //   14: aload_0
    //   15: getfield checkSignature : Z
    //   18: invokestatic validateAppSignatureForPackage : (Landroid/content/Context;Ljava/lang/String;Z)Z
    //   21: ifne -> 36
    //   24: ldc_w 'sendReq failed for wechat app signature check failed'
    //   27: astore_1
    //   28: ldc 'MicroMsg.SDK.WXApiImplV10'
    //   30: aload_1
    //   31: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   34: iconst_0
    //   35: ireturn
    //   36: aload_1
    //   37: invokevirtual getType : ()I
    //   40: iconst_2
    //   41: if_icmpne -> 147
    //   44: aload_1
    //   45: checkcast com/tencent/mm/opensdk/modelmsg/SendMessageToWX$Req
    //   48: astore_2
    //   49: aload_2
    //   50: getfield scene : I
    //   53: iconst_4
    //   54: if_icmpne -> 147
    //   57: aload_2
    //   58: getfield sceneDataObject : Lcom/tencent/mm/opensdk/modelmsg/SendMessageToWX$IWXSceneDataObject;
    //   61: astore_3
    //   62: aload_3
    //   63: instanceof com/tencent/mm/opensdk/modelmsg/WXStateSceneDataObject
    //   66: ifeq -> 147
    //   69: aload_3
    //   70: checkcast com/tencent/mm/opensdk/modelmsg/WXStateSceneDataObject
    //   73: astore_3
    //   74: aload_2
    //   75: getfield message : Lcom/tencent/mm/opensdk/modelmsg/WXMediaMessage;
    //   78: astore #4
    //   80: aload #4
    //   82: getfield mediaObject : Lcom/tencent/mm/opensdk/modelmsg/WXMediaMessage$IMediaObject;
    //   85: ifnonnull -> 100
    //   88: aload #4
    //   90: new com/tencent/mm/opensdk/modelmsg/WXTextObject
    //   93: dup
    //   94: invokespecial <init> : ()V
    //   97: putfield mediaObject : Lcom/tencent/mm/opensdk/modelmsg/WXMediaMessage$IMediaObject;
    //   100: aload_2
    //   101: getfield message : Lcom/tencent/mm/opensdk/modelmsg/WXMediaMessage;
    //   104: invokevirtual getType : ()I
    //   107: iconst_1
    //   108: if_icmpne -> 147
    //   111: aload_3
    //   112: getfield stateTitle : Ljava/lang/String;
    //   115: astore #4
    //   117: aload #4
    //   119: ifnull -> 130
    //   122: aload #4
    //   124: invokevirtual length : ()I
    //   127: ifgt -> 147
    //   130: aload_3
    //   131: aload_2
    //   132: getfield message : Lcom/tencent/mm/opensdk/modelmsg/WXMediaMessage;
    //   135: getfield mediaObject : Lcom/tencent/mm/opensdk/modelmsg/WXMediaMessage$IMediaObject;
    //   138: checkcast com/tencent/mm/opensdk/modelmsg/WXTextObject
    //   141: getfield text : Ljava/lang/String;
    //   144: putfield stateTitle : Ljava/lang/String;
    //   147: aload_1
    //   148: invokevirtual checkArgs : ()Z
    //   151: ifne -> 161
    //   154: ldc_w 'sendReq checkArgs fail'
    //   157: astore_1
    //   158: goto -> 28
    //   161: new java/lang/StringBuilder
    //   164: dup
    //   165: invokespecial <init> : ()V
    //   168: astore_2
    //   169: aload_2
    //   170: ldc_w 'sendReq, req type = '
    //   173: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   176: pop
    //   177: aload_2
    //   178: aload_1
    //   179: invokevirtual getType : ()I
    //   182: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   185: pop
    //   186: ldc 'MicroMsg.SDK.WXApiImplV10'
    //   188: aload_2
    //   189: invokevirtual toString : ()Ljava/lang/String;
    //   192: invokestatic i : (Ljava/lang/String;Ljava/lang/String;)V
    //   195: new android/os/Bundle
    //   198: dup
    //   199: invokespecial <init> : ()V
    //   202: astore #4
    //   204: aload_1
    //   205: aload #4
    //   207: invokevirtual toBundle : (Landroid/os/Bundle;)V
    //   210: aload_1
    //   211: invokevirtual getType : ()I
    //   214: iconst_5
    //   215: if_icmpeq -> 1157
    //   218: aload_1
    //   219: invokevirtual getType : ()I
    //   222: bipush #27
    //   224: if_icmpne -> 230
    //   227: goto -> 1157
    //   230: aload_1
    //   231: invokevirtual getType : ()I
    //   234: bipush #9
    //   236: if_icmpne -> 250
    //   239: aload_0
    //   240: aload_0
    //   241: getfield context : Landroid/content/Context;
    //   244: aload #4
    //   246: invokespecial sendAddCardToWX : (Landroid/content/Context;Landroid/os/Bundle;)Z
    //   249: ireturn
    //   250: aload_1
    //   251: invokevirtual getType : ()I
    //   254: bipush #16
    //   256: if_icmpne -> 270
    //   259: aload_0
    //   260: aload_0
    //   261: getfield context : Landroid/content/Context;
    //   264: aload #4
    //   266: invokespecial sendChooseCardFromWX : (Landroid/content/Context;Landroid/os/Bundle;)Z
    //   269: ireturn
    //   270: aload_1
    //   271: invokevirtual getType : ()I
    //   274: bipush #11
    //   276: if_icmpne -> 290
    //   279: aload_0
    //   280: aload_0
    //   281: getfield context : Landroid/content/Context;
    //   284: aload #4
    //   286: invokespecial sendOpenRankListReq : (Landroid/content/Context;Landroid/os/Bundle;)Z
    //   289: ireturn
    //   290: aload_1
    //   291: invokevirtual getType : ()I
    //   294: bipush #12
    //   296: if_icmpne -> 310
    //   299: aload_0
    //   300: aload_0
    //   301: getfield context : Landroid/content/Context;
    //   304: aload #4
    //   306: invokespecial sendOpenWebview : (Landroid/content/Context;Landroid/os/Bundle;)Z
    //   309: ireturn
    //   310: aload_1
    //   311: invokevirtual getType : ()I
    //   314: bipush #25
    //   316: if_icmpne -> 329
    //   319: aload_0
    //   320: aload_0
    //   321: getfield context : Landroid/content/Context;
    //   324: aload_1
    //   325: invokespecial sendOpenBusinessWebview : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   328: ireturn
    //   329: aload_1
    //   330: invokevirtual getType : ()I
    //   333: bipush #13
    //   335: if_icmpne -> 349
    //   338: aload_0
    //   339: aload_0
    //   340: getfield context : Landroid/content/Context;
    //   343: aload #4
    //   345: invokespecial sendOpenBusiLuckyMoney : (Landroid/content/Context;Landroid/os/Bundle;)Z
    //   348: ireturn
    //   349: aload_1
    //   350: invokevirtual getType : ()I
    //   353: bipush #14
    //   355: if_icmpne -> 369
    //   358: aload_0
    //   359: aload_0
    //   360: getfield context : Landroid/content/Context;
    //   363: aload #4
    //   365: invokespecial createChatroom : (Landroid/content/Context;Landroid/os/Bundle;)Z
    //   368: ireturn
    //   369: aload_1
    //   370: invokevirtual getType : ()I
    //   373: bipush #15
    //   375: if_icmpne -> 389
    //   378: aload_0
    //   379: aload_0
    //   380: getfield context : Landroid/content/Context;
    //   383: aload #4
    //   385: invokespecial joinChatroom : (Landroid/content/Context;Landroid/os/Bundle;)Z
    //   388: ireturn
    //   389: aload_1
    //   390: invokevirtual getType : ()I
    //   393: bipush #17
    //   395: if_icmpne -> 409
    //   398: aload_0
    //   399: aload_0
    //   400: getfield context : Landroid/content/Context;
    //   403: aload #4
    //   405: invokespecial sendHandleScanResult : (Landroid/content/Context;Landroid/os/Bundle;)Z
    //   408: ireturn
    //   409: aload_1
    //   410: invokevirtual getType : ()I
    //   413: bipush #18
    //   415: if_icmpne -> 428
    //   418: aload_0
    //   419: aload_0
    //   420: getfield context : Landroid/content/Context;
    //   423: aload_1
    //   424: invokespecial sendSubscribeMessage : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   427: ireturn
    //   428: aload_1
    //   429: invokevirtual getType : ()I
    //   432: bipush #28
    //   434: if_icmpne -> 447
    //   437: aload_0
    //   438: aload_0
    //   439: getfield context : Landroid/content/Context;
    //   442: aload_1
    //   443: invokespecial sendPreloadWXMiniprogram : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   446: ireturn
    //   447: aload_1
    //   448: invokevirtual getType : ()I
    //   451: bipush #29
    //   453: if_icmpne -> 466
    //   456: aload_0
    //   457: aload_0
    //   458: getfield context : Landroid/content/Context;
    //   461: aload_1
    //   462: invokespecial sendLaunchWXMiniprogramWithToken : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   465: ireturn
    //   466: aload_1
    //   467: invokevirtual getType : ()I
    //   470: bipush #23
    //   472: if_icmpne -> 485
    //   475: aload_0
    //   476: aload_0
    //   477: getfield context : Landroid/content/Context;
    //   480: aload_1
    //   481: invokespecial sendSubscribeMiniProgramMsg : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   484: ireturn
    //   485: aload_1
    //   486: invokevirtual getType : ()I
    //   489: bipush #19
    //   491: if_icmpne -> 504
    //   494: aload_0
    //   495: aload_0
    //   496: getfield context : Landroid/content/Context;
    //   499: aload_1
    //   500: invokespecial sendLaunchWXMiniprogram : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   503: ireturn
    //   504: aload_1
    //   505: invokevirtual getType : ()I
    //   508: bipush #32
    //   510: if_icmpne -> 523
    //   513: aload_0
    //   514: aload_0
    //   515: getfield context : Landroid/content/Context;
    //   518: aload_1
    //   519: invokespecial sendPreloadWXMiniProgramEnvironment : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   522: ireturn
    //   523: aload_1
    //   524: invokevirtual getType : ()I
    //   527: bipush #30
    //   529: if_icmpne -> 542
    //   532: aload_0
    //   533: aload_0
    //   534: getfield context : Landroid/content/Context;
    //   537: aload_1
    //   538: invokespecial sendToWxaRedirectingPage : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   541: ireturn
    //   542: aload_1
    //   543: invokevirtual getType : ()I
    //   546: bipush #26
    //   548: if_icmpne -> 561
    //   551: aload_0
    //   552: aload_0
    //   553: getfield context : Landroid/content/Context;
    //   556: aload_1
    //   557: invokespecial sendOpenBusinessView : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   560: ireturn
    //   561: aload_1
    //   562: invokevirtual getType : ()I
    //   565: bipush #33
    //   567: if_icmpne -> 580
    //   570: aload_0
    //   571: aload_0
    //   572: getfield context : Landroid/content/Context;
    //   575: aload_1
    //   576: invokespecial sendFinderShareVideo : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   579: ireturn
    //   580: aload_1
    //   581: invokevirtual getType : ()I
    //   584: bipush #34
    //   586: if_icmpne -> 599
    //   589: aload_0
    //   590: aload_0
    //   591: getfield context : Landroid/content/Context;
    //   594: aload_1
    //   595: invokespecial sendFinderOpenProfile : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   598: ireturn
    //   599: aload_1
    //   600: invokevirtual getType : ()I
    //   603: bipush #35
    //   605: if_icmpne -> 618
    //   608: aload_0
    //   609: aload_0
    //   610: getfield context : Landroid/content/Context;
    //   613: aload_1
    //   614: invokespecial sendFinderOpenLive : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   617: ireturn
    //   618: aload_1
    //   619: invokevirtual getType : ()I
    //   622: bipush #36
    //   624: if_icmpne -> 637
    //   627: aload_0
    //   628: aload_0
    //   629: getfield context : Landroid/content/Context;
    //   632: aload_1
    //   633: invokespecial sendFinderOpenFeed : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   636: ireturn
    //   637: aload_1
    //   638: invokevirtual getType : ()I
    //   641: bipush #37
    //   643: if_icmpne -> 656
    //   646: aload_0
    //   647: aload_0
    //   648: getfield context : Landroid/content/Context;
    //   651: aload_1
    //   652: invokespecial sendOpenCustomerServiceChat : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   655: ireturn
    //   656: aload_1
    //   657: invokevirtual getType : ()I
    //   660: bipush #38
    //   662: if_icmpne -> 675
    //   665: aload_0
    //   666: aload_0
    //   667: getfield context : Landroid/content/Context;
    //   670: aload_1
    //   671: invokespecial sendQRCodePayReq : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   674: ireturn
    //   675: aload_1
    //   676: invokevirtual getType : ()I
    //   679: bipush #20
    //   681: if_icmpne -> 694
    //   684: aload_0
    //   685: aload_0
    //   686: getfield context : Landroid/content/Context;
    //   689: aload_1
    //   690: invokespecial sendInvoiceAuthInsert : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   693: ireturn
    //   694: aload_1
    //   695: invokevirtual getType : ()I
    //   698: bipush #21
    //   700: if_icmpne -> 713
    //   703: aload_0
    //   704: aload_0
    //   705: getfield context : Landroid/content/Context;
    //   708: aload_1
    //   709: invokespecial sendNonTaxPay : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   712: ireturn
    //   713: aload_1
    //   714: invokevirtual getType : ()I
    //   717: bipush #22
    //   719: if_icmpne -> 732
    //   722: aload_0
    //   723: aload_0
    //   724: getfield context : Landroid/content/Context;
    //   727: aload_1
    //   728: invokespecial sendPayInSurance : (Landroid/content/Context;Lcom/tencent/mm/opensdk/modelbase/BaseReq;)Z
    //   731: ireturn
    //   732: aload_1
    //   733: invokevirtual getType : ()I
    //   736: bipush #24
    //   738: if_icmpne -> 752
    //   741: aload_0
    //   742: aload_0
    //   743: getfield context : Landroid/content/Context;
    //   746: aload #4
    //   748: invokespecial sendJumpToOfflinePayReq : (Landroid/content/Context;Landroid/os/Bundle;)Z
    //   751: ireturn
    //   752: aload_1
    //   753: invokevirtual getType : ()I
    //   756: iconst_2
    //   757: if_icmpne -> 1026
    //   760: aload_1
    //   761: checkcast com/tencent/mm/opensdk/modelmsg/SendMessageToWX$Req
    //   764: astore #5
    //   766: aload #5
    //   768: getfield message : Lcom/tencent/mm/opensdk/modelmsg/WXMediaMessage;
    //   771: invokevirtual getType : ()I
    //   774: istore #6
    //   776: iload #6
    //   778: invokestatic a : (I)Z
    //   781: ifeq -> 1026
    //   784: aload_0
    //   785: invokevirtual getWXAppSupportAPI : ()I
    //   788: ldc_w 620756993
    //   791: if_icmpge -> 826
    //   794: new com/tencent/mm/opensdk/modelmsg/WXWebpageObject
    //   797: dup
    //   798: invokespecial <init> : ()V
    //   801: astore_2
    //   802: aload_2
    //   803: aload #4
    //   805: ldc_w '_wxminiprogram_webpageurl'
    //   808: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   811: putfield webpageUrl : Ljava/lang/String;
    //   814: aload #5
    //   816: getfield message : Lcom/tencent/mm/opensdk/modelmsg/WXMediaMessage;
    //   819: aload_2
    //   820: putfield mediaObject : Lcom/tencent/mm/opensdk/modelmsg/WXMediaMessage$IMediaObject;
    //   823: goto -> 995
    //   826: iload #6
    //   828: bipush #46
    //   830: if_icmpne -> 854
    //   833: aload_0
    //   834: invokevirtual getWXAppSupportAPI : ()I
    //   837: ldc_w 620953856
    //   840: if_icmpge -> 854
    //   843: new com/tencent/mm/opensdk/modelmsg/WXWebpageObject
    //   846: dup
    //   847: invokespecial <init> : ()V
    //   850: astore_2
    //   851: goto -> 802
    //   854: aload #5
    //   856: getfield message : Lcom/tencent/mm/opensdk/modelmsg/WXMediaMessage;
    //   859: getfield mediaObject : Lcom/tencent/mm/opensdk/modelmsg/WXMediaMessage$IMediaObject;
    //   862: checkcast com/tencent/mm/opensdk/modelmsg/WXMiniProgramObject
    //   865: astore #7
    //   867: new java/lang/StringBuilder
    //   870: dup
    //   871: invokespecial <init> : ()V
    //   874: astore_2
    //   875: aload_2
    //   876: aload #7
    //   878: getfield userName : Ljava/lang/String;
    //   881: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   884: pop
    //   885: aload_2
    //   886: ldc_w '@app'
    //   889: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   892: pop
    //   893: aload #7
    //   895: aload_2
    //   896: invokevirtual toString : ()Ljava/lang/String;
    //   899: putfield userName : Ljava/lang/String;
    //   902: aload #7
    //   904: getfield path : Ljava/lang/String;
    //   907: astore_2
    //   908: aload_2
    //   909: invokestatic b : (Ljava/lang/String;)Z
    //   912: ifne -> 995
    //   915: aload_2
    //   916: ldc_w '\?'
    //   919: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   922: astore_2
    //   923: aload_2
    //   924: arraylength
    //   925: iconst_1
    //   926: if_icmple -> 960
    //   929: new java/lang/StringBuilder
    //   932: dup
    //   933: invokespecial <init> : ()V
    //   936: astore_3
    //   937: aload_3
    //   938: aload_2
    //   939: iconst_0
    //   940: aaload
    //   941: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   944: pop
    //   945: aload_3
    //   946: ldc_w '.html?'
    //   949: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   952: pop
    //   953: aload_2
    //   954: iconst_1
    //   955: aaload
    //   956: astore_2
    //   957: goto -> 980
    //   960: new java/lang/StringBuilder
    //   963: dup
    //   964: invokespecial <init> : ()V
    //   967: astore_3
    //   968: aload_3
    //   969: aload_2
    //   970: iconst_0
    //   971: aaload
    //   972: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   975: pop
    //   976: ldc_w '.html'
    //   979: astore_2
    //   980: aload_3
    //   981: aload_2
    //   982: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   985: pop
    //   986: aload #7
    //   988: aload_3
    //   989: invokevirtual toString : ()Ljava/lang/String;
    //   992: putfield path : Ljava/lang/String;
    //   995: aload #5
    //   997: getfield scene : I
    //   1000: istore #6
    //   1002: iload #6
    //   1004: iconst_3
    //   1005: if_icmpeq -> 1020
    //   1008: iload #6
    //   1010: iconst_1
    //   1011: if_icmpeq -> 1020
    //   1014: aload #5
    //   1016: iconst_0
    //   1017: putfield scene : I
    //   1020: aload_1
    //   1021: aload #4
    //   1023: invokevirtual toBundle : (Landroid/os/Bundle;)V
    //   1026: new com/tencent/mm/opensdk/channel/MMessageActV2$Args
    //   1029: dup
    //   1030: invokespecial <init> : ()V
    //   1033: astore_1
    //   1034: aload_1
    //   1035: aload #4
    //   1037: putfield bundle : Landroid/os/Bundle;
    //   1040: new java/lang/StringBuilder
    //   1043: dup
    //   1044: invokespecial <init> : ()V
    //   1047: astore_2
    //   1048: aload_2
    //   1049: ldc_w 'weixin://sendreq?appid='
    //   1052: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1055: pop
    //   1056: aload_2
    //   1057: aload_0
    //   1058: getfield appId : Ljava/lang/String;
    //   1061: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1064: pop
    //   1065: aload_1
    //   1066: aload_2
    //   1067: invokevirtual toString : ()Ljava/lang/String;
    //   1070: putfield content : Ljava/lang/String;
    //   1073: aload_1
    //   1074: ldc_w 'com.tencent.mm'
    //   1077: putfield targetPkgName : Ljava/lang/String;
    //   1080: aload_1
    //   1081: ldc_w 'com.tencent.mm.plugin.base.stub.WXEntryActivity'
    //   1084: putfield targetClassName : Ljava/lang/String;
    //   1087: aload_1
    //   1088: aload_0
    //   1089: getfield launchMode : I
    //   1092: putfield launchMode : I
    //   1095: aload_0
    //   1096: aload_0
    //   1097: getfield context : Landroid/content/Context;
    //   1100: invokespecial getTokenFromWX : (Landroid/content/Context;)Ljava/lang/String;
    //   1103: astore_2
    //   1104: aload_2
    //   1105: ifnull -> 1148
    //   1108: aload_1
    //   1109: aload_2
    //   1110: putfield token : Ljava/lang/String;
    //   1113: goto -> 1148
    //   1116: astore_3
    //   1117: new java/lang/StringBuilder
    //   1120: dup
    //   1121: invokespecial <init> : ()V
    //   1124: astore_2
    //   1125: aload_2
    //   1126: ldc_w 'getTokenFromWX fail, exception = '
    //   1129: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1132: pop
    //   1133: aload_2
    //   1134: aload_3
    //   1135: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1138: pop
    //   1139: ldc 'MicroMsg.SDK.WXApiImplV10'
    //   1141: aload_2
    //   1142: invokevirtual toString : ()Ljava/lang/String;
    //   1145: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1148: aload_0
    //   1149: getfield context : Landroid/content/Context;
    //   1152: aload_1
    //   1153: invokestatic send : (Landroid/content/Context;Lcom/tencent/mm/opensdk/channel/MMessageActV2$Args;)Z
    //   1156: ireturn
    //   1157: aload_0
    //   1158: aload_0
    //   1159: getfield context : Landroid/content/Context;
    //   1162: aload #4
    //   1164: invokespecial sendPayReq : (Landroid/content/Context;Landroid/os/Bundle;)Z
    //   1167: ireturn
    //   1168: new java/lang/IllegalStateException
    //   1171: dup
    //   1172: ldc_w 'sendReq fail, WXMsgImpl has been detached'
    //   1175: invokespecial <init> : (Ljava/lang/String;)V
    //   1178: athrow
    // Exception table:
    //   from	to	target	type
    //   1095	1104	1116	java/lang/Exception
    //   1108	1113	1116	java/lang/Exception
  }
  
  public boolean sendResp(BaseResp paramBaseResp) {
    if (!this.detached) {
      String str;
      if (!WXApiImplComm.validateAppSignatureForPackage(this.context, "com.tencent.mm", this.checkSignature)) {
        str = "sendResp failed for wechat app signature check failed";
        Log.e("MicroMsg.SDK.WXApiImplV10", str);
        return false;
      } 
      if (!str.checkArgs()) {
        str = "sendResp checkArgs fail";
        Log.e("MicroMsg.SDK.WXApiImplV10", str);
        return false;
      } 
      Bundle bundle = new Bundle();
      str.toBundle(bundle);
      MMessageActV2.Args args = new MMessageActV2.Args();
      args.bundle = bundle;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("weixin://sendresp?appid=");
      stringBuilder.append(this.appId);
      args.content = stringBuilder.toString();
      args.targetPkgName = "com.tencent.mm";
      args.targetClassName = "com.tencent.mm.plugin.base.stub.WXEntryActivity";
      try {
        String str1 = getTokenFromWX(this.context);
        if (str1 != null)
          args.token = str1; 
      } catch (Exception exception) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("getTokenFromWX fail, exception = ");
        stringBuilder1.append(exception);
        Log.e("MicroMsg.SDK.WXApiImplV10", stringBuilder1.toString());
      } 
      return MMessageActV2.send(this.context, args);
    } 
    throw new IllegalStateException("sendResp fail, WXMsgImpl has been detached");
  }
  
  public void setLogImpl(ILog paramILog) {
    Log.setLogImpl(paramILog);
  }
  
  public void unregisterApp() {
    if (!this.detached) {
      if (!WXApiImplComm.validateAppSignatureForPackage(this.context, "com.tencent.mm", this.checkSignature)) {
        Log.e("MicroMsg.SDK.WXApiImplV10", "unregister app failed for wechat app signature check failed");
        return;
      } 
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append("unregisterApp, appId = ");
      stringBuilder2.append(this.appId);
      Log.d("MicroMsg.SDK.WXApiImplV10", stringBuilder2.toString());
      String str = this.appId;
      if (str == null || str.length() == 0) {
        Log.e("MicroMsg.SDK.WXApiImplV10", "unregisterApp fail, appId is empty");
        return;
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("unregister app ");
      stringBuilder1.append(this.context.getPackageName());
      Log.d("MicroMsg.SDK.WXApiImplV10", stringBuilder1.toString());
      a.a a = new a.a();
      a.a = "com.tencent.mm";
      a.b = "com.tencent.mm.plugin.openapi.Intent.ACTION_HANDLE_APP_UNREGISTER";
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("weixin://unregisterapp?appid=");
      stringBuilder1.append(this.appId);
      a.c = stringBuilder1.toString();
      a.a(this.context, a);
      return;
    } 
    throw new IllegalStateException("unregisterApp fail, WXMsgImpl has been detached");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\openapi\BaseWXApiImplV10.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */