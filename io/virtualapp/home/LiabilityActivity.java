package io.virtualapp.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.stub.StubApp;
import io.virtualapp.App;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.Utils.ToastUtil;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.bean.TokenItem;
import io.virtualapp.dialog.WebDialog;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import io.virtualapp.webview.WebViewActivity;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class LiabilityActivity extends VActivity implements View.OnClickListener, HttpCall {
  private TextView toHelp;
  
  private TextView toPerm;
  
  private TokenItem tokenItem;
  
  static {
    StubApp.interface11(9698);
  }
  
  private void checkPerm() {
    ArrayList<String> arrayList1 = new ArrayList();
    arrayList1.add("android.permission.READ_PHONE_STATE");
    arrayList1.add("android.permission.READ_EXTERNAL_STORAGE");
    ArrayList<String> arrayList2 = new ArrayList();
    for (String str : arrayList1) {
      if (ContextCompat.checkSelfPermission((Context)getApplication(), str) != 0)
        arrayList2.add(str); 
    } 
    if (arrayList2.size() > 0) {
      ActivityCompat.requestPermissions((Activity)this, arrayList2.<String>toArray(new String[arrayList2.size()]), 1111);
    } else {
      login();
    } 
  }
  
  public static void goLiability(Activity paramActivity) {
    paramActivity.startActivity(new Intent((Context)paramActivity, LiabilityActivity.class));
  }
  
  private void initViews() {
    this.toPerm = (TextView)findViewById(2131296727);
    this.toHelp = (TextView)findViewById(2131296807);
    this.toPerm.setOnClickListener(this);
    this.toHelp.setOnClickListener(this);
  }
  
  private void login() {
    if (TextUtils.isEmpty(SPUtils.get((Context)App.getApp(), "token"))) {
      (new HttpManger(this)).regist();
    } else {
      (new HttpManger(this)).login();
    } 
  }
  
  public void onClick(View paramView) {
    int i = paramView.getId();
    if (i != 2131296727) {
      if (i == 2131296807)
        startActivity((new Intent((Context)this, WebViewActivity.class)).putExtra("weburl", "http://copy.ymzer.com/page/dkfs/permission.html").putExtra("title", "图文教程")); 
    } else {
      Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("package:");
      stringBuilder.append(getPackageName());
      intent.setData(Uri.parse(stringBuilder.toString()));
      try {
        startActivity(intent);
      } catch (Exception exception) {}
    } 
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  public void onError() {}
  
  public native void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint);
  
  protected void onRestart() {
    super.onRestart();
    checkPerm();
  }
  
  public void onSuccess(String paramString, JSONObject paramJSONObject) {
    HttpBean httpBean = (HttpBean)JSON.parseObject(paramJSONObject.toString(), HttpBean.class);
    if (paramString.equals(HttpManger.KEY_LOGIN))
      if ("1".equals(httpBean.getStatus())) {
        this.tokenItem = (TokenItem)JSON.parseObject(httpBean.getData().toString(), TokenItem.class);
        SPUtils.put((Context)App.getApp(), "token", this.tokenItem.getToken());
        SPUtils.put((Context)App.getApp(), "my_intergal", this.tokenItem.getIntegral());
        SPUtils.put((Context)App.getApp(), "viplevel", this.tokenItem.getViplevel());
        (new HttpManger(this)).reportVip();
      } else if ("001".equals(httpBean.getStatus())) {
        (new HttpManger(this)).regist();
      } else {
        ToastUtil.showToast(httpBean.getInfo());
      }  
    if (paramString.equals(HttpManger.KEY_REGIST))
      if ("1".equals(httpBean.getStatus())) {
        this.tokenItem = (TokenItem)JSON.parseObject(httpBean.getData().toString(), TokenItem.class);
        SPUtils.put((Context)App.getApp(), "token", this.tokenItem.getToken());
        SPUtils.put((Context)App.getApp(), "my_intergal", this.tokenItem.getIntegral());
        SPUtils.put((Context)App.getApp(), "viplevel", this.tokenItem.getViplevel());
        (new HttpManger(this)).reportVip();
      } else if ("006".equals(httpBean.getStatus())) {
        TokenItem tokenItem = (TokenItem)JSON.parseObject(httpBean.getData().toString(), TokenItem.class);
        this.tokenItem = tokenItem;
        if (tokenItem != null && !TextUtils.isEmpty(tokenItem.getToken())) {
          SPUtils.put((Context)App.getApp(), "token", this.tokenItem.getToken());
          (new HttpManger(this)).login();
        } 
      } else {
        ToastUtil.showToast(httpBean.getInfo());
      }  
    if (paramString.equals(HttpManger.KEY_REPORT_VIP))
      if ("1".equals(httpBean.getStatus())) {
        try {
          JSONObject jSONObject = new JSONObject();
          this(httpBean.getData().toString());
          if (!jSONObject.isNull("difference")) {
            SPUtils.put((Context)this, "my_expried_time", jSONObject.getString("difference"));
            SPUtils.put((Context)this, "my_expried_time_str", jSONObject.getString("expriedtime"));
            SPUtils.put((Context)this, "my_alwaysvip", jSONObject.getString("alwaysvip"));
            HomeActivity.goHome((Context)this);
            finish();
          } 
        } catch (JSONException jSONException) {
          jSONException.printStackTrace();
        } 
      } else {
        ToastUtil.showToast(httpBean.getInfo());
      }  
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\LiabilityActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */