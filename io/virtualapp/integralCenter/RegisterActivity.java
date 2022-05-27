package io.virtualapp.integralCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.stub.StubApp;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.bean.MessageEvent;
import io.virtualapp.dialog.TipsDialog;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import io.virtualapp.webview.WebViewActivity;
import java.util.Random;
import java.util.regex.Pattern;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends VActivity implements View.OnClickListener, HttpCall {
  EditText inputCode;
  
  EditText inputMobile;
  
  private TimeCount time;
  
  TextView tvGetCode;
  
  TextView tvNext;
  
  TextView tvSign;
  
  private TextView tvXieyi;
  
  private TextView tvYinsiXieyi;
  
  static {
    StubApp.interface11(9831);
  }
  
  public static boolean isMobileNO(String paramString) {
    boolean bool;
    try {
      bool = Pattern.compile("^(1[0-9])\\d{9}$").matcher(paramString).matches();
    } catch (Exception exception) {
      bool = false;
    } 
    return bool;
  }
  
  protected void initView() {
    TextView textView = (TextView)findViewById(2131296809);
    this.tvXieyi = textView;
    textView.getPaint().setFlags(8);
    this.tvXieyi.getPaint().setAntiAlias(true);
    this.tvXieyi.setOnClickListener(this);
    textView = (TextView)findViewById(2131296810);
    this.tvYinsiXieyi = textView;
    textView.getPaint().setFlags(8);
    this.tvYinsiXieyi.getPaint().setAntiAlias(true);
    this.tvYinsiXieyi.setOnClickListener(this);
    this.inputMobile = (EditText)findViewById(2131296529);
    this.inputCode = (EditText)findViewById(2131296526);
    this.tvGetCode = (TextView)findViewById(2131296744);
    this.tvSign = (TextView)findViewById(2131296753);
    this.tvNext = (TextView)findViewById(2131296748);
    this.time = new TimeCount(90000L, 1000L);
    this.tvGetCode.setOnClickListener(this);
    this.tvSign.setOnClickListener(this);
    this.tvNext.setOnClickListener(this);
  }
  
  public void onClick(View paramView) {
    Random random;
    Intent intent;
    StringBuilder stringBuilder;
    String str2;
    switch (paramView.getId()) {
      default:
        return;
      case 2131296810:
        random = new Random();
        intent = new Intent((Context)this, WebViewActivity.class);
        stringBuilder = new StringBuilder();
        stringBuilder.append("https://xiaoyintech.com/user_privacy.html?t=");
        stringBuilder.append(random.nextInt(9999));
        startActivity(intent.putExtra("weburl", stringBuilder.toString()).putExtra("title", "隐私保护"));
      case 2131296809:
        startActivity((new Intent((Context)this, WebViewActivity.class)).putExtra("weburl", "https://xiaoyintech.com/user_soft_service_info.html").putExtra("title", getResources().getString(2131624054)));
      case 2131296753:
        str2 = this.inputMobile.getText().toString();
        str1 = this.inputCode.getText().toString();
        if (isMobileNO(str2) && str1.length() < 10) {
          (new HttpManger(this)).checkCode(str2, str1);
        } else {
          final TipsDialog dialog = new TipsDialog((Context)this, "提示", "请输入正确的手机号和验证码", "确定");
          tipsDialog1.setOnPositionLisenter(new TipsDialog.OnPositionLisenter() {
                public void onPositionLisenter() {
                  dialog.dismiss();
                }
              });
          tipsDialog1.show();
        } 
      case 2131296748:
        startActivity(new Intent((Context)this, SignInActivity.class));
        finish();
      case 2131296744:
        break;
    } 
    String str1 = this.inputMobile.getText().toString();
    if (isMobileNO(str1)) {
      this.time.start();
      this.inputMobile.setEnabled(false);
      (new HttpManger(this)).sendCode(str1);
    } 
    final TipsDialog dialog = new TipsDialog((Context)this, "提示", "请输入正确的手机号", "确定");
    tipsDialog.setOnPositionLisenter(new TipsDialog.OnPositionLisenter() {
          public void onPositionLisenter() {
            dialog.dismiss();
          }
        });
    tipsDialog.show();
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  public void onError() {}
  
  public void onSuccess(String paramString, JSONObject paramJSONObject) {
    TipsDialog.OnPositionLisenter onPositionLisenter;
    HttpBean httpBean = (HttpBean)JSON.parseObject(paramJSONObject.toString(), HttpBean.class);
    if (httpBean.getStatus().equals("1")) {
      try {
        paramString.equals(HttpManger.KEY_SEND_CODE);
        if (paramString.equals(HttpManger.KEY_CHECK_CODE)) {
          HttpManger httpManger = new HttpManger();
          this(this);
          httpManger.checkSign(this.inputMobile.getText().toString());
        } 
        if (paramString.equals(HttpManger.KEY_CHECK_SIGN)) {
          MessageEvent messageEvent;
          JSONObject jSONObject = new JSONObject();
          this(httpBean.getData().toString());
          if (jSONObject.getString("signstatus").equals("1")) {
            EventBus eventBus = EventBus.getDefault();
            messageEvent = new MessageEvent();
            this(2);
            eventBus.post(messageEvent);
            finish();
          } else {
            String str = messageEvent.getString("infos");
            final TipsDialog dialog = new TipsDialog();
            this((Context)this, "提示", str, "确定");
            onPositionLisenter = new TipsDialog.OnPositionLisenter() {
                public void onPositionLisenter() {
                  dialog.dismiss();
                }
              };
            super(this, tipsDialog);
            tipsDialog.setOnPositionLisenter(onPositionLisenter);
            tipsDialog.show();
          } 
        } 
      } catch (JSONException jSONException) {
        jSONException.printStackTrace();
      } 
    } else {
      String str = onPositionLisenter.getInfo();
      paramString = str;
      if (str.equals("参数不正确"))
        paramString = "验证码有误或已过期"; 
      final TipsDialog dialog = new TipsDialog((Context)this, "提示", paramString, "确定");
      tipsDialog.setOnPositionLisenter(new TipsDialog.OnPositionLisenter() {
            public void onPositionLisenter() {
              dialog.dismiss();
            }
          });
      tipsDialog.show();
    } 
  }
  
  class TimeCount extends CountDownTimer {
    public TimeCount(long param1Long1, long param1Long2) {
      super(param1Long1, param1Long2);
    }
    
    public void onFinish() {
      RegisterActivity.this.tvGetCode.setText("重新获取");
      RegisterActivity.this.tvGetCode.setClickable(true);
      RegisterActivity.this.inputMobile.setEnabled(true);
    }
    
    public void onTick(long param1Long) {
      RegisterActivity.this.tvGetCode.setClickable(false);
      TextView textView = RegisterActivity.this.tvGetCode;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("(");
      stringBuilder.append(param1Long / 1000L);
      stringBuilder.append(") 秒后可重新发送");
      textView.setText(stringBuilder.toString());
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\integralCenter\RegisterActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */