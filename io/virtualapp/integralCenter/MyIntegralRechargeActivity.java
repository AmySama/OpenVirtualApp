package io.virtualapp.integralCenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.stub.StubApp;
import io.virtualapp.Utils.AliPayUtil;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.bean.IntegralInfoBean;
import io.virtualapp.webview.H5PayWebViewActivity;

public class MyIntegralRechargeActivity extends VActivity implements View.OnClickListener {
  private CheckBox alipayBox;
  
  private RelativeLayout alipayPayLayout;
  
  private LinearLayout backLayout;
  
  private TextView goPayView;
  
  private TextView goodsView;
  
  private IntegralInfoBean mPayBean;
  
  private String mToken;
  
  private int payType = 1;
  
  private TextView priceView;
  
  private CheckBox wechatBox;
  
  private RelativeLayout wechatPayLayout;
  
  static {
    StubApp.interface11(9816);
  }
  
  private void bindViews() {
    ((TextView)findViewById(2131296570)).setText("积分充值");
    this.backLayout = (LinearLayout)findViewById(2131296568);
    this.goodsView = (TextView)findViewById(2131296490);
    this.priceView = (TextView)findViewById(2131296491);
    this.wechatPayLayout = (RelativeLayout)findViewById(2131296803);
    this.alipayPayLayout = (RelativeLayout)findViewById(2131296306);
    this.wechatBox = (CheckBox)findViewById(2131296800);
    this.alipayBox = (CheckBox)findViewById(2131296307);
    this.goPayView = (TextView)findViewById(2131296485);
    this.backLayout.setOnClickListener(this);
    this.wechatPayLayout.setOnClickListener(this);
    this.alipayPayLayout.setOnClickListener(this);
    this.goPayView.setOnClickListener(this);
  }
  
  private void initData() {
    if (!TextUtils.isEmpty(this.mPayBean.getMoney())) {
      this.priceView.setText(getResources().getString(2131623937, new Object[] { this.mPayBean.getMoney() }));
    } else {
      this.priceView.setText(getResources().getString(2131623936, new Object[] { Integer.valueOf(0) }));
    } 
    if (!TextUtils.isEmpty(this.mPayBean.getIntegral())) {
      TextView textView = this.goodsView;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mPayBean.getIntegral());
      stringBuilder.append("积分");
      textView.setText(stringBuilder.toString());
    } else {
      this.goodsView.setText("0积分");
    } 
  }
  
  private void pay() {
    int i = this.payType;
    if (i == 0) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("http://copy.ymzer.com/api/index.php?m=Home&c=WxH5Pay&a=payIndex&token=");
      stringBuilder.append(SPUtils.get((Context)this, "token"));
      stringBuilder.append("&bizcode=");
      stringBuilder.append(this.mPayBean.getBizcode());
      stringBuilder.append("&money=");
      stringBuilder.append(this.mPayBean.getMoney());
      String str = stringBuilder.toString();
      startActivity((new Intent((Context)this, H5PayWebViewActivity.class)).putExtra("title", "充值").putExtra("url", str).putExtra("isPay", true));
    } else if (i == 1) {
      (new AliPayUtil((Activity)this)).requestOrder(this.mToken, this.mPayBean.getMoney(), this.mPayBean.getBizcode());
    } 
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == 2131296568) {
      finish();
    } else if (paramView.getId() == 2131296803) {
      this.payType = 0;
      this.wechatBox.setChecked(true);
      this.alipayBox.setChecked(false);
    } else if (paramView.getId() == 2131296306) {
      this.payType = 1;
      this.wechatBox.setChecked(false);
      this.alipayBox.setChecked(true);
    } else if (paramView.getId() == 2131296485) {
      pay();
      finish();
    } 
  }
  
  protected native void onCreate(Bundle paramBundle);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\integralCenter\MyIntegralRechargeActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */