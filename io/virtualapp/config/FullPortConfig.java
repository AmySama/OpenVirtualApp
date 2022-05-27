package io.virtualapp.config;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.mobile.auth.gatewayauth.AuthRegisterViewConfig;
import com.mobile.auth.gatewayauth.AuthUIConfig;
import com.mobile.auth.gatewayauth.AuthUIControlClickListener;
import com.mobile.auth.gatewayauth.CustomInterface;
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import io.virtualapp.integralCenter.SignInActivity;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

public class FullPortConfig extends BaseUIConfig {
  private final String TAG = "全屏竖屏样式";
  
  public FullPortConfig(Activity paramActivity, PhoneNumberAuthHelper paramPhoneNumberAuthHelper) {
    super(paramActivity, paramPhoneNumberAuthHelper);
  }
  
  public void configAuthPage() {
    if (this.mAuthHelper != null) {
      this.mAuthHelper.setUIClickListener(new AuthUIControlClickListener() {
            public void onClick(String param1String1, Context param1Context, String param1String2) {
              JSONObject jSONObject;
              try {
                if (!TextUtils.isEmpty(param1String2)) {
                  jSONObject = new JSONObject();
                  this(param1String2);
                } else {
                  param1Context = null;
                } 
              } catch (JSONException jSONException) {
                jSONObject = new JSONObject();
              } 
              byte b = -1;
              switch (param1String1.hashCode()) {
                case 1620409949:
                  if (param1String1.equals("700004"))
                    b = 4; 
                  break;
                case 1620409948:
                  if (param1String1.equals("700003"))
                    b = 3; 
                  break;
                case 1620409947:
                  if (param1String1.equals("700002"))
                    b = 2; 
                  break;
                case 1620409946:
                  if (param1String1.equals("700001"))
                    b = 1; 
                  break;
                case 1620409945:
                  if (param1String1.equals("700000"))
                    b = 0; 
                  break;
              } 
              if (b != 0) {
                if (b != 1) {
                  if (b != 2) {
                    if (b != 3) {
                      if (b == 4) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("点击协议，name: ");
                        stringBuilder.append(jSONObject.optString("name"));
                        stringBuilder.append(", url: ");
                        stringBuilder.append(jSONObject.optString("url"));
                        Log.e("全屏竖屏样式", stringBuilder.toString());
                      } 
                    } else {
                      StringBuilder stringBuilder = new StringBuilder();
                      stringBuilder.append("checkbox状态变为");
                      stringBuilder.append(jSONObject.optBoolean("isChecked"));
                      Log.e("全屏竖屏样式", stringBuilder.toString());
                    } 
                  } else if (!jSONObject.optBoolean("isChecked")) {
                    Toast.makeText(FullPortConfig.this.mContext, 2131623995, 0).show();
                  } 
                } else {
                  Log.e("全屏竖屏样式", "点击了授权页默认切换其他登录方式");
                } 
              } else {
                Log.e("全屏竖屏样式", "点击了授权页默认返回按钮");
                FullPortConfig.this.mAuthHelper.quitLoginPage();
                FullPortConfig.this.mActivity.finish();
              } 
            }
          });
      this.mAuthHelper.removeAuthRegisterXmlConfig();
      this.mAuthHelper.removeAuthRegisterViewConfig();
      this.mAuthHelper.addAuthRegistViewConfig("switch_msg", (new AuthRegisterViewConfig.Builder()).setView(initSwitchView(350)).setRootViewId(0).setCustomInterface(new CustomInterface() {
              public void onClick(Context param1Context) {
                Intent intent = new Intent((Context)FullPortConfig.this.mActivity, SignInActivity.class);
                FullPortConfig.this.mActivity.startActivityForResult(intent, 1002);
                FullPortConfig.this.mAuthHelper.quitLoginPage();
                FullPortConfig.this.mActivity.finish();
              }
            }).build());
      byte b = 7;
      if (Build.VERSION.SDK_INT == 26)
        b = 3; 
      Random random = new Random();
      PhoneNumberAuthHelper phoneNumberAuthHelper = this.mAuthHelper;
      AuthUIConfig.Builder builder = new AuthUIConfig.Builder();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("https://xiaoyintech.com/user_privacy.html?t=");
      stringBuilder.append(random.nextInt(9999));
      phoneNumberAuthHelper.setAuthUIConfig(builder.setAppPrivacyOne("《分身助手隐私政策》", stringBuilder.toString()).setAppPrivacyColor(-7829368, Color.parseColor("#002E00")).setSwitchAccHidden(true).setLogBtnToastHidden(true).setLogBtnText("本机号码一键登录").setNavColor(Color.parseColor("#00D1FF")).setStatusBarColor(Color.parseColor("#00D1FF")).setWebViewStatusBarColor(Color.parseColor("#00D1FF")).setWebNavColor(Color.parseColor("#00D1FF")).setLogBtnBackgroundDrawable(this.mActivity.getResources().getDrawable(2131230855)).setNavText("一键登录").setSloganTextSize(12).setSloganOffsetY(170).setNumFieldOffsetY(120).setNumberSizeDp(36).setLightColor(false).setWebNavTextSizeDp(20).setNavReturnImgDrawable(this.mActivity.getResources().getDrawable(2131230951)).setWebNavReturnImgDrawable(this.mActivity.getResources().getDrawable(2131230951)).setAuthPageActIn("in_activity", "out_activity").setAuthPageActOut("in_activity", "out_activity").setVendorPrivacyPrefix("《").setVendorPrivacySuffix("》").setPageBackgroundPath("page_background_color").setLogoImgPath("mytel_app_launcher").setLogBtnBackgroundPath("login_btn_bg").setScreenOrientation(b).create());
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\config\FullPortConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */