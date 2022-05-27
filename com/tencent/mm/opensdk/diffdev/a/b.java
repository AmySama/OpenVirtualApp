package com.tencent.mm.opensdk.diffdev.a;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Base64;
import com.tencent.mm.opensdk.diffdev.OAuthErrCode;
import com.tencent.mm.opensdk.diffdev.OAuthListener;
import com.tencent.mm.opensdk.utils.Log;
import org.json.JSONObject;

public class b extends AsyncTask<Void, Void, b.a> {
  private String a;
  
  private String b;
  
  private String c;
  
  private String d;
  
  private String e;
  
  private OAuthListener f;
  
  private c g;
  
  public b(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, OAuthListener paramOAuthListener) {
    this.a = paramString1;
    this.b = paramString2;
    this.c = paramString3;
    this.d = paramString4;
    this.e = paramString5;
    this.f = paramOAuthListener;
  }
  
  public boolean a() {
    Log.i("MicroMsg.SDK.GetQRCodeTask", "cancelTask");
    c c1 = this.g;
    return (c1 == null) ? cancel(true) : c1.cancel(true);
  }
  
  protected Object doInBackground(Object[] paramArrayOfObject) {
    Void[] arrayOfVoid = (Void[])paramArrayOfObject;
    Thread.currentThread().setName("OpenSdkGetQRCodeTask");
    Log.i("MicroMsg.SDK.GetQRCodeTask", "doInBackground");
    String str = String.format("https://open.weixin.qq.com/connect/sdk/qrconnect?appid=%s&noncestr=%s&timestamp=%s&scope=%s&signature=%s", new Object[] { this.a, this.c, this.d, this.b, this.e });
    long l = System.currentTimeMillis();
    byte[] arrayOfByte = com.tencent.mm.opensdk.channel.a.a.a(str, 60000);
    Log.d("MicroMsg.SDK.GetQRCodeTask", String.format("doInBackground, url = %s, time consumed = %d(ms)", new Object[] { str, Long.valueOf(System.currentTimeMillis() - l) }));
    return a.a(arrayOfByte);
  }
  
  protected void onPostExecute(Object paramObject) {
    StringBuilder stringBuilder;
    paramObject = paramObject;
    OAuthErrCode oAuthErrCode = ((a)paramObject).a;
    if (oAuthErrCode == OAuthErrCode.WechatAuth_Err_OK) {
      stringBuilder = new StringBuilder();
      stringBuilder.append("onPostExecute, get qrcode success imgBufSize = ");
      stringBuilder.append(((a)paramObject).e.length);
      Log.d("MicroMsg.SDK.GetQRCodeTask", stringBuilder.toString());
      this.f.onAuthGotQrcode(((a)paramObject).d, ((a)paramObject).e);
      paramObject = new c(((a)paramObject).b, this.f);
      this.g = (c)paramObject;
      if (Build.VERSION.SDK_INT >= 11) {
        paramObject.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
      } else {
        paramObject.execute((Object[])new Void[0]);
      } 
    } else {
      Log.e("MicroMsg.SDK.GetQRCodeTask", String.format("onPostExecute, get qrcode fail, OAuthErrCode = %s", new Object[] { stringBuilder }));
      this.f.onAuthFinish(((a)paramObject).a, null);
    } 
  }
  
  static class a {
    public OAuthErrCode a;
    
    public String b;
    
    public String c;
    
    public String d;
    
    public byte[] e;
    
    public static a a(byte[] param1ArrayOfbyte) {
      OAuthErrCode oAuthErrCode2;
      String str;
      a a1 = new a();
      if (param1ArrayOfbyte == null || param1ArrayOfbyte.length == 0) {
        Log.e("MicroMsg.SDK.GetQRCodeResult", "parse fail, buf is null");
        oAuthErrCode2 = OAuthErrCode.WechatAuth_Err_NetworkErr;
        a1.a = oAuthErrCode2;
        return a1;
      } 
      try {
        String str1 = new String();
        this((byte[])oAuthErrCode2, "utf-8");
        try {
          JSONObject jSONObject = new JSONObject();
          this(str1);
          int i = jSONObject.getInt("errcode");
          if (i != 0) {
            Log.e("MicroMsg.SDK.GetQRCodeResult", String.format("resp errcode = %d", new Object[] { Integer.valueOf(i) }));
            a1.a = OAuthErrCode.WechatAuth_Err_NormalErr;
            jSONObject.optString("errmsg");
            return a1;
          } 
          str1 = jSONObject.getJSONObject("qrcode").getString("qrcodebase64");
          if (str1 == null || str1.length() == 0) {
            Log.e("MicroMsg.SDK.GetQRCodeResult", "parse fail, qrcodeBase64 is null");
            a1.a = OAuthErrCode.WechatAuth_Err_JsonDecodeErr;
            return a1;
          } 
          byte[] arrayOfByte = Base64.decode(str1, 0);
          if (arrayOfByte == null || arrayOfByte.length == 0) {
            Log.e("MicroMsg.SDK.GetQRCodeResult", "parse fail, qrcodeBuf is null");
            a1.a = OAuthErrCode.WechatAuth_Err_JsonDecodeErr;
            return a1;
          } 
          a1.a = OAuthErrCode.WechatAuth_Err_OK;
          a1.e = arrayOfByte;
          a1.b = jSONObject.getString("uuid");
          str = jSONObject.getString("appname");
          a1.c = str;
          Log.d("MicroMsg.SDK.GetQRCodeResult", String.format("parse succ, save in memory, uuid = %s, appname = %s, imgBufLength = %d", new Object[] { a1.b, str, Integer.valueOf(a1.e.length) }));
          return a1;
        } catch (Exception exception) {
          str = String.format("parse json fail, ex = %s", new Object[] { exception.getMessage() });
        } 
      } catch (Exception exception) {
        str = String.format("parse fail, build String fail, ex = %s", new Object[] { exception.getMessage() });
      } 
      Log.e("MicroMsg.SDK.GetQRCodeResult", str);
      OAuthErrCode oAuthErrCode1 = OAuthErrCode.WechatAuth_Err_NormalErr;
      a1.a = oAuthErrCode1;
      return a1;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\diffdev\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */