package io.virtualapp.integralCenter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.stub.StubApp;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.Utils.ToastUtil;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.bean.MessageEvent;
import io.virtualapp.dialog.TipsDialog;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class FCodeExchangeActivity extends VActivity implements View.OnClickListener, HttpCall {
  EditText inputFcode;
  
  TextView usedFcode;
  
  static {
    StubApp.interface11(9811);
  }
  
  protected void initView() {
    ((TextView)findViewById(2131296401)).setText("F码");
    findViewById(2131296568).setOnClickListener(this);
    this.inputFcode = (EditText)findViewById(2131296528);
    TextView textView = (TextView)findViewById(2131296766);
    this.usedFcode = textView;
    textView.setOnClickListener(this);
  }
  
  public void onClick(View paramView) {
    int i = paramView.getId();
    if (i != 2131296568) {
      if (i == 2131296766) {
        String str = this.inputFcode.getText().toString();
        if (TextUtils.isEmpty(str)) {
          ToastUtil.showToast("F码为空");
          return;
        } 
        (new HttpManger(this)).usefcode(str);
      } 
    } else {
      finish();
    } 
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  public void onError() {}
  
  public void onSuccess(String paramString, JSONObject paramJSONObject) {
    HttpBean httpBean = (HttpBean)JSON.parseObject(paramJSONObject.toString(), HttpBean.class);
    if (httpBean != null && !TextUtils.isEmpty(httpBean.getData().toString())) {
      StringBuilder stringBuilder;
      if (httpBean.getStatus().equals("1")) {
        try {
          paramJSONObject = new JSONObject();
          this(httpBean.getData().toString());
          if (paramString.equals(HttpManger.KEY_USE_FCODE) && !paramJSONObject.isNull("difference")) {
            SPUtils.put((Context)this, "my_expried_time", paramJSONObject.getString("difference"));
            SPUtils.put((Context)this, "my_expried_time_str", paramJSONObject.getString("expriedtime"));
            SPUtils.put((Context)this, "my_alwaysvip", paramJSONObject.getString("alwaysvip"));
            EventBus eventBus = EventBus.getDefault();
            MessageEvent messageEvent = new MessageEvent();
            this(2);
            eventBus.post(messageEvent);
            TipsDialog tipsDialog = new TipsDialog();
            stringBuilder = new StringBuilder();
            this();
            stringBuilder.append("您的F码(");
            stringBuilder.append(this.inputFcode.getText().toString());
            stringBuilder.append(") 成功兑换了");
            stringBuilder.append(paramJSONObject.getString("value"));
            stringBuilder.append("天VIP,系统已自动对您的VIP延期");
            stringBuilder.append(paramJSONObject.getString("value"));
            stringBuilder.append("天");
            this((Context)this, "兑换成功", stringBuilder.toString(), "确定");
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
      } else {
        ToastUtil.showToast(stringBuilder.getInfo());
      } 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\integralCenter\FCodeExchangeActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */