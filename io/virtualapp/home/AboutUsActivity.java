package io.virtualapp.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.stub.StubApp;
import io.virtualapp.App;
import io.virtualapp.Utils.AppUtils;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.Utils.ToastUtil;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.bean.MessageEvent;
import io.virtualapp.dialog.ConfirmDialog;
import io.virtualapp.home.models.NomalAppButton;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import io.virtualapp.webview.WebViewActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class AboutUsActivity extends VActivity implements View.OnClickListener, HttpCall {
  private ImageView leftImage;
  
  private LinearLayout leftLayout;
  
  Activity mContext;
  
  private List<NomalAppButton> normalDatas = new ArrayList<NomalAppButton>();
  
  private TextView titleView;
  
  private TextView tvExit;
  
  private TextView tvQuit;
  
  private TextView tvUrl;
  
  private TextView tvVersion;
  
  private TextView tvXieyi;
  
  private TextView tvYinsiXieyi;
  
  static {
    StubApp.interface11(9676);
  }
  
  private void initViews() {
    this.titleView = (TextView)findViewById(2131296401);
    this.tvVersion = (TextView)findViewById(2131296769);
    this.leftImage = (ImageView)findViewById(2131296567);
    LinearLayout linearLayout = (LinearLayout)findViewById(2131296568);
    this.leftLayout = linearLayout;
    linearLayout.setOnClickListener(this);
    this.titleView.setText("关于我们");
    TextView textView = this.tvVersion;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("当前版本：");
    stringBuilder.append(AppUtils.getVersionName((Context)App.getApp()));
    textView.setText(stringBuilder.toString());
    textView = (TextView)findViewById(2131296764);
    this.tvUrl = textView;
    textView.setAutoLinkMask(15);
    textView = (TextView)findViewById(2131296809);
    this.tvXieyi = textView;
    textView.getPaint().setFlags(8);
    this.tvXieyi.getPaint().setAntiAlias(true);
    this.tvXieyi.setOnClickListener(this);
    textView = (TextView)findViewById(2131296810);
    this.tvYinsiXieyi = textView;
    textView.getPaint().setFlags(8);
    this.tvYinsiXieyi.getPaint().setAntiAlias(true);
    this.tvYinsiXieyi.setOnClickListener(this);
    textView = (TextView)findViewById(2131296743);
    this.tvExit = textView;
    textView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            final ConfirmDialog dialog = new ConfirmDialog((Context)AboutUsActivity.this, 2131689644, "提示", "注销后您的VIP信息、个人资料、平台将全部销毁，再次使用应用多开分身将不存在。 您确认要注销帐号吗？", "立即注销", "以后再说");
            confirmDialog.setOnPositionLisenter(new ConfirmDialog.OnPositionLisenter() {
                  public void onPositionLisenter() {
                    (new HttpManger(AboutUsActivity.this)).logoff();
                    dialog.dismiss();
                  }
                });
            confirmDialog.show();
          }
        });
    textView = (TextView)findViewById(2131296749);
    this.tvQuit = textView;
    textView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            final ConfirmDialog dialog = new ConfirmDialog((Context)AboutUsActivity.this, 2131689644, "提示", "退出意味着解绑您的手机号码。 您确认要退出吗？", "立即退出", "以后再说");
            confirmDialog.setOnPositionLisenter(new ConfirmDialog.OnPositionLisenter() {
                  public void onPositionLisenter() {
                    (new HttpManger(AboutUsActivity.this)).quit();
                    dialog.dismiss();
                  }
                });
            confirmDialog.show();
          }
        });
  }
  
  public void onClick(View paramView) {
    Random random;
    Intent intent;
    StringBuilder stringBuilder;
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
      case 2131296568:
        break;
    } 
    finish();
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  public void onError() {}
  
  public void onSuccess(String paramString, JSONObject paramJSONObject) {
    HttpBean httpBean = (HttpBean)JSON.parseObject(paramJSONObject.toString(), HttpBean.class);
    if (httpBean != null && !TextUtils.isEmpty(httpBean.getData().toString()))
      try {
        paramJSONObject = new JSONObject();
        this(httpBean.getData().toString());
        if (paramString.equals(HttpManger.KEY_LOGOFF)) {
          EventBus eventBus = EventBus.getDefault();
          MessageEvent messageEvent = new MessageEvent();
          this(2);
          eventBus.post(messageEvent);
          ToastUtil.showToast("注销成功");
          finish();
        } 
        if (paramString.equals(HttpManger.KEY_QUIT)) {
          EventBus eventBus = EventBus.getDefault();
          MessageEvent messageEvent = new MessageEvent();
          this(2);
          eventBus.post(messageEvent);
          ToastUtil.showToast("退出成功");
          finish();
        } 
        if (paramString.equals(HttpManger.KEY_REPORT_VIP) && !paramJSONObject.isNull("difference")) {
          SPUtils.put((Context)this, "my_expried_time", paramJSONObject.getString("difference"));
          SPUtils.put((Context)this, "my_expried_time_str", paramJSONObject.getString("expriedtime"));
          SPUtils.put((Context)this, "my_alwaysvip", paramJSONObject.getString("alwaysvip"));
          SPUtils.put((Context)this, "is_svip", paramJSONObject.getString("svip"));
          SPUtils.put((Context)this, "package_userid", paramJSONObject.getString("packageuserid"));
        } 
      } catch (JSONException jSONException) {
        jSONException.printStackTrace();
      }  
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\AboutUsActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */