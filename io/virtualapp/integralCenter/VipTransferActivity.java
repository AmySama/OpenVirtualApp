package io.virtualapp.integralCenter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.stub.StubApp;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.Utils.ToastUtil;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.bean.MessageEvent;
import io.virtualapp.dialog.TipsGrayDialog;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class VipTransferActivity extends VActivity implements View.OnClickListener, HttpCall {
  private String alwaysvip;
  
  TextView btnActivate;
  
  private String code;
  
  EditText inputVipCode;
  
  private String status = "0";
  
  TextView toActivate;
  
  TextView toTransfer;
  
  LinearLayout vipActivateLayout;
  
  LinearLayout vipBtnLayout;
  
  EditText vipCode;
  
  LinearLayout vipCodeLayout;
  
  static {
    StubApp.interface11(9852);
  }
  
  private void initData() {
    if (this.status.equals("0")) {
      this.vipBtnLayout.setVisibility(0);
      this.vipCodeLayout.setVisibility(8);
    } else {
      this.vipBtnLayout.setVisibility(8);
      this.vipCodeLayout.setVisibility(0);
      this.vipCode.setText(this.code);
    } 
  }
  
  protected void initView() {
    ((TextView)findViewById(2131296401)).setText("VIP迁移到新手机");
    findViewById(2131296568).setOnClickListener(this);
    this.toTransfer = (TextView)findViewById(2131296728);
    this.toActivate = (TextView)findViewById(2131296726);
    this.btnActivate = (TextView)findViewById(2131296380);
    this.vipBtnLayout = (LinearLayout)findViewById(2131296773);
    this.vipCodeLayout = (LinearLayout)findViewById(2131296776);
    this.vipActivateLayout = (LinearLayout)findViewById(2131296772);
    this.vipCode = (EditText)findViewById(2131296775);
    this.inputVipCode = (EditText)findViewById(2131296531);
    this.toTransfer.setOnClickListener(this);
    this.toActivate.setOnClickListener(this);
    this.btnActivate.setOnClickListener(this);
  }
  
  public void onClick(View paramView) {
    switch (paramView.getId()) {
      default:
        return;
      case 2131296728:
        if (this.alwaysvip.equals("0")) {
          (new TipsGrayDialog((Context)this, "对不起，您还不是永久VIP，无法进行迁移操作")).show();
        } else {
          (new HttpManger(this)).vipTransfer();
        } 
      case 2131296726:
        if (this.alwaysvip.equals("1")) {
          (new TipsGrayDialog((Context)this, "您已经是永久VIP，无需进行迁移操作")).show();
        } else {
          this.vipBtnLayout.setVisibility(8);
          this.vipCodeLayout.setVisibility(8);
          this.vipActivateLayout.setVisibility(0);
        } 
      case 2131296568:
        finish();
      case 2131296380:
        break;
    } 
    String str = this.inputVipCode.getText().toString();
    (new HttpManger(this)).vipActivate(str);
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  public void onError() {}
  
  public void onSuccess(String paramString, JSONObject paramJSONObject) {
    HttpBean httpBean = (HttpBean)JSON.parseObject(paramJSONObject.toString(), HttpBean.class);
    if (httpBean != null && !TextUtils.isEmpty(httpBean.getData().toString())) {
      TipsGrayDialog tipsGrayDialog;
      if (httpBean.getStatus().equals("1")) {
        try {
          paramJSONObject = new JSONObject();
          this(httpBean.getData().toString());
          boolean bool = paramString.equals(HttpManger.KEY_FIND_VIP_TRANSFER);
          if (bool) {
            this.status = paramJSONObject.getString("status");
            this.alwaysvip = paramJSONObject.getString("alwaysvip");
            if (this.status.equals("1"))
              this.code = paramJSONObject.getString("code"); 
            initData();
          } 
          bool = paramString.equals(HttpManger.KEY_VIP_TRANSFER);
          if (bool) {
            this.status = paramJSONObject.getString("status");
            String str = paramJSONObject.getString("msg");
            if (this.status.equals("1")) {
              String str1 = paramJSONObject.getString("code");
              this.code = str1;
              this.vipCode.setText(str1);
            } else {
              tipsGrayDialog = new TipsGrayDialog();
              this((Context)this, str);
              tipsGrayDialog.show();
            } 
            initData();
          } 
          if (paramString.equals(HttpManger.KEY_VIP_ACTIVATE)) {
            String str = paramJSONObject.getString("status");
            paramString = paramJSONObject.getString("msg");
            tipsGrayDialog = new TipsGrayDialog();
            this((Context)this, paramString);
            tipsGrayDialog.show();
            if (str.equals("1")) {
              SPUtils.put((Context)this, "my_alwaysvip", paramJSONObject.getString("alwaysvip"));
              EventBus eventBus = EventBus.getDefault();
              MessageEvent messageEvent = new MessageEvent();
              this(2);
              eventBus.post(messageEvent);
            } 
          } 
        } catch (JSONException jSONException) {
          jSONException.printStackTrace();
        } 
      } else {
        ToastUtil.showToast(tipsGrayDialog.getInfo());
      } 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\integralCenter\VipTransferActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */