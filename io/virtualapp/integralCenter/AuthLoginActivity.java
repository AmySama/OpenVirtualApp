package io.virtualapp.integralCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import com.mobile.auth.gatewayauth.TokenResultListener;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.stub.StubApp;
import io.virtualapp.Utils.ExecutorManager;
import io.virtualapp.Utils.MockRequest;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.bean.MessageEvent;
import io.virtualapp.config.BaseUIConfig;
import io.virtualapp.dialog.TipsDialog;
import io.virtualapp.dialog.WhiteLoadingDialog;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class AuthLoginActivity extends VActivity implements HttpCall {
  private static final String TAG = AuthLoginActivity.class.getSimpleName();
  
  private PhoneNumberAuthHelper mPhoneNumberAuthHelper;
  
  private WhiteLoadingDialog mProgressDialog;
  
  private TokenResultListener mTokenResultListener;
  
  private TextView mTvResult;
  
  private BaseUIConfig mUIConfig;
  
  private int mUIType;
  
  private void autologin(String paramString) {
    (new HttpManger(this)).autoLogin(paramString);
  }
  
  private void oneKeyLogin() {
    Log.e(TAG, "oneKeyLogin start");
    PhoneNumberAuthHelper phoneNumberAuthHelper = PhoneNumberAuthHelper.getInstance(StubApp.getOrigApplicationContext(getApplicationContext()), this.mTokenResultListener);
    this.mPhoneNumberAuthHelper = phoneNumberAuthHelper;
    phoneNumberAuthHelper.checkEnvAvailable();
    if (this.mPhoneNumberAuthHelper != null) {
      Log.e(TAG, "oneKeyLogin start 2");
      this.mUIConfig.configAuthPage();
      getLoginToken(4000);
    } 
  }
  
  public void getLoginToken(int paramInt) {
    this.mPhoneNumberAuthHelper.getLoginToken((Context)this, paramInt);
  }
  
  public void getResultWithToken(final String token) {
    ExecutorManager.run(new Runnable() {
          public void run() {
            final String result = MockRequest.getPhoneNumber(token);
            AuthLoginActivity.this.runOnUiThread(new Runnable() {
                  public void run() {
                    String str = AuthLoginActivity.TAG;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("登录成功：");
                    stringBuilder2.append(result);
                    Log.e(str, stringBuilder2.toString());
                    TextView textView = AuthLoginActivity.this.mTvResult;
                    StringBuilder stringBuilder1 = new StringBuilder();
                    stringBuilder1.append("登录成功：");
                    stringBuilder1.append(result);
                    textView.setText(stringBuilder1.toString());
                    AuthLoginActivity.this.mPhoneNumberAuthHelper.quitLoginPage();
                    AuthLoginActivity.this.autologin(token);
                  }
                });
          }
        });
  }
  
  public void hideLoadingDialog() {
    WhiteLoadingDialog whiteLoadingDialog = this.mProgressDialog;
    if (whiteLoadingDialog != null)
      whiteLoadingDialog.dismiss(); 
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt1 == 1002)
      if (paramInt2 == 1) {
        TextView textView = this.mTvResult;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("登录成功onActivityResult：");
        stringBuilder.append(paramIntent.getStringExtra("result"));
        textView.setText(stringBuilder.toString());
      } else {
        finish();
      }  
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  protected void onDestroy() {
    super.onDestroy();
  }
  
  public void onError() {}
  
  protected void onResume() {
    super.onResume();
    this.mUIConfig.onResume();
  }
  
  public void onSuccess(String paramString, JSONObject paramJSONObject) {
    HttpBean httpBean = (HttpBean)JSON.parseObject(paramJSONObject.toString(), HttpBean.class);
    if (httpBean != null) {
      if (paramString.equals(HttpManger.KEY_AUTO_LOGIN))
        if ("1".equals(httpBean.getStatus())) {
          try {
            String str = httpBean.getData().toString();
            HttpManger httpManger = new HttpManger();
            this(this);
            httpManger.checkSign(str);
          } catch (Exception exception) {
            Intent intent = new Intent((Context)this, SignInActivity.class);
            intent.putExtra("auth", "err");
            startActivity(intent);
            finish();
          } 
        } else {
          Intent intent = new Intent((Context)this, SignInActivity.class);
          intent.putExtra("auth", "err");
          startActivity(intent);
          finish();
        }  
      if (paramString.equals(HttpManger.KEY_CHECK_SIGN))
        try {
          MessageEvent messageEvent;
          JSONObject jSONObject = new JSONObject();
          this(httpBean.getData().toString());
          if (jSONObject.getString("signstatus").equals("1")) {
            SPUtils.put((Context)this, "my_mobile", jSONObject.getString("mobile"));
            EventBus eventBus = EventBus.getDefault();
            messageEvent = new MessageEvent();
            this(2);
            eventBus.post(messageEvent);
            finish();
          } else {
            String str = messageEvent.getString("infos");
            TipsDialog tipsDialog = new TipsDialog();
            this((Context)this, "提示", str, "确定");
            TipsDialog.OnPositionLisenter onPositionLisenter = new TipsDialog.OnPositionLisenter() {
                public void onPositionLisenter() {
                  dialog.dismiss();
                }
              };
            super(this, tipsDialog);
            tipsDialog.setOnPositionLisenter(onPositionLisenter);
            tipsDialog.show();
          } 
        } catch (JSONException jSONException) {
          jSONException.printStackTrace();
        }  
    } 
  }
  
  public void sdkInit(String paramString) {
    TokenResultListener tokenResultListener = new TokenResultListener() {
        public void onTokenFailed(String param1String) {
          String str = AuthLoginActivity.TAG;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("获取token失败：");
          stringBuilder.append(param1String);
          Log.e(str, stringBuilder.toString());
          AuthLoginActivity.this.hideLoadingDialog();
          try {
            TokenRet tokenRet = TokenRet.fromJson(param1String);
            if ("700000".equals(tokenRet.getCode())) {
              AuthLoginActivity.this.finish();
            } else {
              str = AuthLoginActivity.TAG;
              StringBuilder stringBuilder1 = new StringBuilder();
              this();
              stringBuilder1.append(tokenRet.getCode());
              stringBuilder1.append("   ");
              stringBuilder1.append(tokenRet.getMsg());
              Log.e(str, stringBuilder1.toString());
              Intent intent = new Intent();
              this(SignInActivity.class);
              intent.putExtra("auth", "err");
              AuthLoginActivity.this.startActivity(intent);
              AuthLoginActivity.this.mPhoneNumberAuthHelper.quitLoginPage();
              AuthLoginActivity.this.finish();
            } 
          } catch (Exception exception) {
            exception.printStackTrace();
          } 
          AuthLoginActivity.this.mPhoneNumberAuthHelper.setAuthListener(null);
        }
        
        public void onTokenSuccess(String param1String) {
          AuthLoginActivity.this.hideLoadingDialog();
          try {
            TokenRet tokenRet = TokenRet.fromJson(param1String);
            boolean bool = "600001".equals(tokenRet.getCode());
            if (bool) {
              StringBuilder stringBuilder = new StringBuilder();
              this();
              stringBuilder.append("唤起授权页成功：");
              stringBuilder.append(param1String);
              Log.i("TAG", stringBuilder.toString());
            } 
            if ("600000".equals(tokenRet.getCode())) {
              StringBuilder stringBuilder = new StringBuilder();
              this();
              stringBuilder.append("获取token成功：");
              stringBuilder.append(param1String);
              Log.i("TAG", stringBuilder.toString());
              AuthLoginActivity.this.getResultWithToken(tokenRet.getToken());
              AuthLoginActivity.this.mPhoneNumberAuthHelper.setAuthListener(null);
            } 
          } catch (Exception exception) {
            exception.printStackTrace();
          } 
        }
      };
    this.mTokenResultListener = tokenResultListener;
    PhoneNumberAuthHelper phoneNumberAuthHelper = PhoneNumberAuthHelper.getInstance((Context)this, tokenResultListener);
    this.mPhoneNumberAuthHelper = phoneNumberAuthHelper;
    phoneNumberAuthHelper.getReporter().setLoggerEnable(true);
    this.mPhoneNumberAuthHelper.setAuthSDKInfo(paramString);
  }
  
  public void showLoadingDialog(String paramString) {
    if (this.mProgressDialog == null)
      this.mProgressDialog = new WhiteLoadingDialog((Context)this); 
    this.mProgressDialog.setCancelable(true);
    this.mProgressDialog.show();
    this.mProgressDialog.setText(paramString);
  }
  
  static {
    StubApp.interface11(9809);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\integralCenter\AuthLoginActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */