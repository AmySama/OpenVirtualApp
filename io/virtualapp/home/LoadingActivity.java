package io.virtualapp.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.utils.VLog;
import com.stub.StubApp;
import io.virtualapp.App;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.Utils.ToastUtil;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.appManage.TipRenewalActivity;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.dialog.TipsDialog;
import io.virtualapp.home.models.PackageAppData;
import io.virtualapp.home.repo.PackageAppDataStorage;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import org.json.JSONException;
import org.json.JSONObject;

public class LoadingActivity extends VActivity implements HttpCall {
  private static final String KEY_INTENT = "KEY_INTENT";
  
  private static final String KEY_USER = "KEY_USER";
  
  private static final String PKG_NAME_ARGUMENT = "MODEL_ARGUMENT";
  
  private PackageAppData appModel;
  
  private String pkg;
  
  private Integer userId;
  
  static {
    StubApp.interface11(9714);
  }
  
  public static void launch(Context paramContext, String paramString, int paramInt) {
    Intent intent = VirtualCore.get().getLaunchIntent(paramString, paramInt);
    if (intent != null) {
      Intent intent1 = new Intent(paramContext, LoadingActivity.class);
      intent1.putExtra("MODEL_ARGUMENT", paramString);
      intent1.addFlags(268435456);
      intent1.putExtra("KEY_INTENT", (Parcelable)intent);
      intent1.putExtra("KEY_USER", paramInt);
      paramContext.startActivity(intent1);
    } 
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  protected void onDestroy() {
    super.onDestroy();
  }
  
  public void onError() {}
  
  protected void onPause() {
    super.onPause();
  }
  
  protected void onResume() {
    super.onResume();
  }
  
  public void onSuccess(String paramString, JSONObject paramJSONObject) {
    HttpBean httpBean = (HttpBean)JSON.parseObject(paramJSONObject.toString(), HttpBean.class);
    if (paramString.equals(HttpManger.KEY_REPORT_VIP)) {
      String str;
      if ("1".equals(httpBean.getStatus())) {
        try {
          paramJSONObject = new JSONObject();
          this(httpBean.getData().toString());
          if (!paramJSONObject.isNull("difference")) {
            str = paramJSONObject.getString("difference");
            paramString = paramJSONObject.getString("alwaysvip");
            boolean bool = TextUtils.isEmpty(paramString);
            if (bool)
              paramString = "0"; 
            SPUtils.put((Context)App.getApp(), "my_expried_time", paramJSONObject.getString("difference"));
            SPUtils.put((Context)this, "my_alwaysvip", paramJSONObject.getString("alwaysvip"));
            SPUtils.put((Context)this, "is_svip", paramJSONObject.getString("svip"));
            if ((TextUtils.isEmpty(str) || Long.valueOf(str).longValue() < 0L) && paramString.equals("0")) {
              Intent intent = new Intent();
              this((Context)this, TipRenewalActivity.class);
              startActivity(intent);
              finish();
            } else {
              startIntent();
            } 
          } 
        } catch (JSONException jSONException) {
          jSONException.printStackTrace();
        } 
      } else {
        ToastUtil.showToast(str.getInfo());
      } 
    } 
  }
  
  protected void startIntent() {
    this.appModel = PackageAppDataStorage.get().acquire(this.pkg);
    if (VirtualCore.get().getLaunchIntent(this.pkg, this.userId.intValue()) == null) {
      TipsDialog tipsDialog = new TipsDialog((Context)this, "提示", "该应用可能已被卸载，请删除快捷方式", "确定");
      tipsDialog.setOnPositionLisenter(new TipsDialog.OnPositionLisenter() {
            public void onPositionLisenter() {
              LoadingActivity.this.finish();
            }
          });
      tipsDialog.show();
    } else {
      try {
        Intent intent = VirtualCore.get().getLaunchIntent(this.pkg, this.userId.intValue());
        VLog.e("GGG", this.pkg);
        VActivityManager.get().startActivity(intent, this.userId.intValue());
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\LoadingActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */