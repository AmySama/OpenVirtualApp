package io.virtualapp.integralCenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.stub.StubApp;
import io.virtualapp.App;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.Utils.UpdateManager;
import io.virtualapp.abs.ui.VActivity;

public class PluginsActivity extends VActivity implements View.OnClickListener {
  private LinearLayout bottom;
  
  private LinearLayout bottomMarket;
  
  private TextView goDownload;
  
  private TextView goHwDownload;
  
  private TextView goVivoDownload;
  
  private TextView goXiaomiDownload;
  
  Activity myContext;
  
  private TextView tvDownload;
  
  static {
    StubApp.interface11(9824);
  }
  
  protected void initView() {
    findViewById(2131296568).setOnClickListener(this);
    TextView textView = (TextView)bind(2131296742);
    this.tvDownload = textView;
    textView.setOnClickListener(this);
    this.bottom = (LinearLayout)bind(2131296344);
    this.bottomMarket = (LinearLayout)bind(2131296346);
    String str = SPUtils.get((Context)App.getApp(), "download_status");
    if (!TextUtils.isEmpty(str))
      if (str.equals("1")) {
        this.bottom.setVisibility(4);
        this.bottomMarket.setVisibility(0);
      } else {
        this.bottom.setVisibility(0);
        this.bottomMarket.setVisibility(4);
      }  
    this.goDownload = (TextView)bind(2131296483);
    this.goHwDownload = (TextView)bind(2131296484);
    this.goXiaomiDownload = (TextView)bind(2131296487);
    this.goVivoDownload = (TextView)bind(2131296486);
    this.goDownload.setOnClickListener(this);
    this.goXiaomiDownload.setOnClickListener(this);
    this.goVivoDownload.setOnClickListener(this);
    this.goHwDownload.setOnClickListener(this);
  }
  
  public void onClick(View paramView) {
    switch (paramView.getId()) {
      default:
        return;
      case 2131296742:
        (new UpdateManager((Context)this, "http://vilocation.oss-cn-shenzhen.aliyuncs.com/file/doushang/bd/15060.apk", "")).down();
      case 2131296568:
        finish();
      case 2131296487:
        intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("https://app.mi.com/details?id=com.imuji.vhelper"));
        startActivity(intent);
      case 2131296486:
        intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("http://info.appstore.vivo.com.cn/detail/3029675"));
        startActivity(intent);
      case 2131296484:
        intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("https://appgallery.huawei.com/#/app/C103512613"));
        startActivity(intent);
      case 2131296483:
        break;
    } 
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.setData(Uri.parse("http://sj.qq.com/myapp/detail.htm?apkName=com.imuji.vhelper"));
    startActivity(intent);
  }
  
  protected native void onCreate(Bundle paramBundle);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\integralCenter\PluginsActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */