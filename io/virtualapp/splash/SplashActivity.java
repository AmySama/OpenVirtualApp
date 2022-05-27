package io.virtualapp.splash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.lody.virtual.client.core.VirtualCore;
import com.stub.StubApp;
import io.virtualapp.App;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.Utils.ToastUtil;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.abs.ui.VUiKit;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.bean.TokenItem;
import io.virtualapp.home.HomeActivity;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends VActivity implements HttpCall {
  private SplashActivity mContext;
  
  private TokenItem tokenItem;
  
  static {
    StubApp.interface11(9857);
  }
  
  private void checkMyPermission() {
    ArrayList<String> arrayList1 = new ArrayList();
    arrayList1.add("android.permission.READ_PHONE_STATE");
    arrayList1.add("android.permission.WRITE_EXTERNAL_STORAGE");
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
  
  private boolean checkXieyi() {
    boolean bool = ((Boolean)SPUtils.get((Context)this, "is_frist_lucher_app", Boolean.valueOf(false))).booleanValue();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(bool);
    stringBuilder.append("");
    Log.e("ischeck", stringBuilder.toString());
    return bool;
  }
  
  private void doActionInThread() {
    if (!VirtualCore.get().isEngineLaunched())
      VirtualCore.get().waitForEngine(); 
  }
  
  private void login() {
    if (TextUtils.isEmpty(SPUtils.get((Context)App.getApp(), "token"))) {
      (new HttpManger(this)).regist();
    } else {
      (new HttpManger(this)).login();
    } 
  }
  
  private void showExceptionDialog() {
    (new AlertDialog.Builder((Context)this)).setCancelable(false).setTitle("提示").setMessage("需要赋予读取设备信息的权限，请到“设置”>“应用”>“权限”中配置权限。").setNegativeButton("取消", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            param1DialogInterface.cancel();
            SplashActivity.this.finish();
          }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            param1DialogInterface.cancel();
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("package:");
            stringBuilder.append(SplashActivity.this.getPackageName());
            intent.setData(Uri.parse(stringBuilder.toString()));
            try {
              SplashActivity.this.startActivity(intent);
              SplashActivity.this.finish();
            } catch (Exception exception) {
              SplashActivity.this.finish();
            } 
          }
        }).show();
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  protected void onDestroy() {
    super.onDestroy();
  }
  
  public void onError() {}
  
  public native void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint);
  
  protected void onResume() {
    super.onResume();
  }
  
  protected void onStart() {
    super.onStart();
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
            SPUtils.put((Context)this, "my_mobile", jSONObject.getString("mobile"));
            SPUtils.put((Context)this, "is_svip", jSONObject.getString("svip"));
            SPUtils.put((Context)this, "package_userid", jSONObject.getString("packageuserid"));
            SPUtils.put((Context)this, "show_location", jSONObject.getString("showlocation"));
            SPUtils.put((Context)this, "download_status", jSONObject.getString("downloadstatus"));
            SPUtils.put((Context)this, "key_dypnskey", jSONObject.getString("dypnskey"));
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\splash\SplashActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */