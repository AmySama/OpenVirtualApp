package io.virtualapp.appManage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.lody.virtual.server.pm.VAppManagerService;
import com.sheep2.dkfs.common.AppInfo;
import com.sheep2.dkfs.util.ApkSearchUtils;
import com.sheep2.dkfs.util.UtilTool;
import com.sheep2.dkfs.web.WebServiceManager;
import com.stub.StubApp;
import io.virtualapp.App;
import io.virtualapp.Config;
import io.virtualapp.Utils.AliPayUtil;
import io.virtualapp.Utils.BitmapUtil;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.Utils.ToastUtil;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.bean.IntegralInfoBean;
import io.virtualapp.bean.ItemIconBean;
import io.virtualapp.bean.MessageEvent;
import io.virtualapp.bean.VIPGoodsBean;
import io.virtualapp.dialog.ConfirmDialog;
import io.virtualapp.dialog.PermissionDialog;
import io.virtualapp.dialog.SelectIconDialog;
import io.virtualapp.dialog.TipsWhiteDialog;
import io.virtualapp.home.HomeActivity;
import io.virtualapp.home.models.AppInfoLite;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import io.virtualapp.integralCenter.PersonalCenterActivity;
import io.virtualapp.webview.WebViewActivity;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangeAppActivity extends VActivity implements View.OnClickListener, HttpCall {
  private static final String IMAGE_UNSPECIFIED = "image/*";
  
  private static final String SELECT_ICON = "selectIcon";
  
  private final int IMAGE_CODE = 0;
  
  private ImageView addAppIcon;
  
  private byte[] appIcons;
  
  private AppInfoLite appInfoLite;
  
  private String appName;
  
  private TextView appVersion;
  
  private Bitmap bitmap;
  
  CountDownTimer cdt;
  
  private TextView centerTitle;
  
  private ImageView changedAppIcon;
  
  private TextView detail;
  
  private EditText editAppName;
  
  private File form;
  
  private Handler handler = new Handler() {
      public void handleMessage(Message param1Message) {
        if (param1Message.what == 100) {
          TextView textView = ChangeAppActivity.this.appVersion;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(ChangeAppActivity.this.textStr);
          stringBuilder.append(ChangeAppActivity.this.appInfoLite.label);
          textView.setText(stringBuilder.toString());
          if (ChangeAppActivity.this.is64bit)
            ChangeAppActivity.this.virtualLayout.setVisibility(4); 
        } 
      }
    };
  
  private LinearLayout imgLayout;
  
  private TextView imgTv;
  
  private boolean is64bit = false;
  
  private boolean isCheck64Over = false;
  
  private boolean isComment = false;
  
  private boolean isSys = true;
  
  private LinearLayout leftLayout;
  
  List<AppInfo> list = new ArrayList<AppInfo>();
  
  private List<ItemIconBean> mIconList = new ArrayList<ItemIconBean>();
  
  private IntegralInfoBean mPayBean;
  
  private String mToken;
  
  private String pkgName;
  
  private String pknumber = "";
  
  private TextView saveBT;
  
  private byte[] selectIcon;
  
  private LinearLayout sysLayout;
  
  private TextView sysText;
  
  private String textStr = "";
  
  private Integer userId;
  
  private VIPGoodsBean vip;
  
  private List<VIPGoodsBean> vipInfos;
  
  private FrameLayout virtualLayout;
  
  private TextView virtualText;
  
  static {
    StubApp.interface11(9557);
  }
  
  private void autoSave() {
    byte[] arrayOfByte = BitmapUtil.DrawableToByte(this.changedAppIcon.getDrawable());
    String str2 = this.editAppName.getText().toString();
    String str1 = new String(Base64.encode(arrayOfByte, 0));
    SPUtils.put((Context)App.getApp(), "key_iconbyte", str1);
    SPUtils.put((Context)App.getApp(), "key_appname", str2);
    ArrayList<AppInfoLite> arrayList = new ArrayList();
    arrayList.add(new AppInfoLite(this.appInfoLite.packageName, this.appInfoLite.path, this.appInfoLite.label, this.appInfoLite.dynamic, this.appInfoLite.targetSdkVersion, this.appInfoLite.requestedPermissions));
    Intent intent = new Intent();
    intent.putParcelableArrayListExtra("va.extra.APP_INFO_LIST", arrayList);
    getActivity().setResult(-1, intent);
    SPUtils.put((Context)App.getApp(), "is_sys_app", Boolean.valueOf(this.isSys));
    finish();
  }
  
  private void bindView() {
    LinearLayout linearLayout = (LinearLayout)findViewById(2131296568);
    this.leftLayout = linearLayout;
    linearLayout.setOnClickListener(this);
    TextView textView2 = (TextView)findViewById(2131296401);
    this.centerTitle = textView2;
    textView2.setText("制作分身");
    this.editAppName = (EditText)findViewById(2131296449);
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(Environment.getExternalStorageDirectory().getPath());
    stringBuilder2.append("/");
    String str = stringBuilder2.toString();
    List list = UtilTool.getPackageInfos((Context)App.getApp());
    ApkSearchUtils apkSearchUtils = new ApkSearchUtils((Context)App.getApp(), false);
    apkSearchUtils.FindAllAPKFile(new File(str));
    this.pknumber = UtilTool.getKLPackageNum(list, apkSearchUtils.getMyFiles());
    EditText editText = this.editAppName;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(this.appName);
    stringBuilder1.append(this.pknumber);
    editText.setText(stringBuilder1.toString());
    this.changedAppIcon = (ImageView)findViewById(2131296404);
    byte[] arrayOfByte = this.selectIcon;
    if (arrayOfByte != null) {
      this.bitmap = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
    } else {
      arrayOfByte = this.appIcons;
      this.bitmap = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
    } 
    this.changedAppIcon.setImageBitmap(this.bitmap);
    this.changedAppIcon.setOnClickListener(this);
    ImageView imageView = (ImageView)findViewById(2131296299);
    this.addAppIcon = imageView;
    imageView.setOnClickListener(this);
    TextView textView1 = (TextView)findViewById(2131296643);
    this.saveBT = textView1;
    textView1.setOnClickListener(this);
    this.sysLayout = (LinearLayout)findViewById(2131296700);
    this.virtualLayout = (FrameLayout)findViewById(2131296795);
    this.imgLayout = (LinearLayout)findViewById(2131296518);
    this.sysText = (TextView)findViewById(2131296701);
    this.virtualText = (TextView)findViewById(2131296796);
    this.appVersion = (TextView)findViewById(2131296319);
    this.imgTv = (TextView)findViewById(2131296524);
    textView1 = (TextView)findViewById(2131296443);
    this.detail = textView1;
    textView1.getPaint().setFlags(8);
    this.detail.getPaint().setAntiAlias(true);
    this.detail.setOnClickListener(this);
    this.sysLayout.setOnClickListener(this);
    this.virtualLayout.setOnClickListener(this);
    this.appVersion.setText(this.appInfoLite.label);
    (new CheckThead()).start();
  }
  
  private void check64bit() {
    String str = HomeActivity.getPackageSourceDir((Context)App.getApp(), this.appInfoLite.packageName);
    if (VAppManagerService.get().isPackage64bit(str)) {
      this.textStr = "64位版";
      this.is64bit = true;
      SPUtils.put((Context)this, "is_64_bit", Boolean.valueOf(true));
    } else {
      this.textStr = "32位版";
      this.is64bit = false;
      SPUtils.put((Context)this, "is_64_bit", Boolean.valueOf(false));
    } 
    Message message = new Message();
    message.what = 100;
    this.handler.sendMessage(message);
  }
  
  private boolean check64bitOk() {
    if (this.isCheck64Over)
      return true; 
    ToastUtil.showToast("虚拟环境还在准备中，请稍后再点击");
    return false;
  }
  
  private void checkData2() {
    App app = App.getApp();
    Boolean bool = Boolean.valueOf(false);
    boolean bool1 = ((Boolean)SPUtils.get((Context)app, "download_is_over", bool)).booleanValue();
    boolean bool2 = ((Boolean)SPUtils.get((Context)App.getApp(), "download_data4_is_over", bool)).booleanValue();
    if (bool1 && bool2) {
      save();
    } else {
      ToastUtil.showToast("虚拟环境还在准备中，请稍后重试");
    } 
  }
  
  private boolean checkVip() {
    String str1 = SPUtils.get((Context)App.getApp(), "my_expried_time");
    String str2 = SPUtils.get((Context)App.getApp(), "my_alwaysvip");
    String str3 = SPUtils.get((Context)App.getApp(), "is_svip");
    if (!TextUtils.isEmpty(str2) && str2.equals("1"))
      return true; 
    if (this.isSys) {
      if (TextUtils.isEmpty(str1) || Long.valueOf(str1).longValue() < 0L) {
        confirmDialog = new ConfirmDialog((Context)this, 2131689644, "提示", "创建系统分身需要开通SVIP会员特权 才能使用", "立即开通SVIP", "以后再说");
        confirmDialog.setOnPositionLisenter(new ConfirmDialog.OnPositionLisenter() {
              public void onPositionLisenter() {
                if (TextUtils.isEmpty(SPUtils.get((Context)App.getApp(), "my_mobile"))) {
                  Intent intent = new Intent();
                  intent.setClass((Context)ChangeAppActivity.this, PersonalCenterActivity.class);
                  ChangeAppActivity.this.startActivity(intent);
                } else {
                  Intent intent = new Intent();
                  intent.setClass((Context)ChangeAppActivity.this, PersonalCenterActivity.class);
                  ChangeAppActivity.this.startActivity(intent);
                } 
                dialog.dismiss();
              }
            });
        confirmDialog.show();
        return false;
      } 
      if (!TextUtils.isEmpty((CharSequence)confirmDialog) && confirmDialog.equals("1"))
        return true; 
      final ConfirmDialog dialog = new ConfirmDialog((Context)this, 2131689644, "提示", "创建系统分身需要开通SVIP会员特权 才能使用", "立即开通SVIP", "以后再说");
      confirmDialog.setOnPositionLisenter(new ConfirmDialog.OnPositionLisenter() {
            public void onPositionLisenter() {
              if (TextUtils.isEmpty(SPUtils.get((Context)App.getApp(), "my_mobile"))) {
                Intent intent = new Intent();
                intent.setClass((Context)ChangeAppActivity.this, PersonalCenterActivity.class);
                ChangeAppActivity.this.startActivity(intent);
              } else {
                Intent intent = new Intent();
                intent.setClass((Context)ChangeAppActivity.this, PersonalCenterActivity.class);
                ChangeAppActivity.this.startActivity(intent);
              } 
              dialog.dismiss();
            }
          });
      confirmDialog.show();
      return false;
    } 
    if (TextUtils.isEmpty(str1) || Long.valueOf(str1).longValue() < 0L) {
      final ConfirmDialog dialog = new ConfirmDialog((Context)this, 2131689644, "提示", "创建分身需要开通会员特权 才能使用", "立即开通", "以后再说");
      confirmDialog.setOnPositionLisenter(new ConfirmDialog.OnPositionLisenter() {
            public void onPositionLisenter() {
              if (TextUtils.isEmpty(SPUtils.get((Context)App.getApp(), "my_mobile"))) {
                Intent intent = new Intent();
                intent.setClass((Context)ChangeAppActivity.this, PersonalCenterActivity.class);
                ChangeAppActivity.this.startActivity(intent);
              } else {
                Intent intent = new Intent();
                intent.setClass((Context)ChangeAppActivity.this, PersonalCenterActivity.class);
                ChangeAppActivity.this.startActivity(intent);
              } 
              dialog.dismiss();
            }
          });
      confirmDialog.show();
      return false;
    } 
    return true;
  }
  
  private void getVip() {
    (new HttpManger(this)).reportVip();
    ApkSearchUtils apkSearchUtils = new ApkSearchUtils((Context)App.getApp(), true);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
    stringBuilder.append("/");
    apkSearchUtils.FindAllAPKFile(new File(stringBuilder.toString()));
    apkSearchUtils.AddInstalledAppInfo();
    this.list = apkSearchUtils.getMyFiles();
    this.list = WebServiceManager.getUpgrage((Context)App.getApp(), this.list);
  }
  
  private void initIntent() {
    this.selectIcon = getIntent().getByteArrayExtra("selectIcon");
    this.appName = getIntent().getStringExtra("appName");
    ArrayList arrayList = getIntent().getParcelableArrayListExtra("va.extra.APP_INFO_LIST");
    this.appIcons = getIntent().getByteArrayExtra("appIcon");
    if (arrayList != null)
      for (AppInfoLite appInfoLite : arrayList) {
        this.appInfoLite = appInfoLite;
        SPUtils.put((Context)this, "icon_packet", appInfoLite.packageName);
      }  
  }
  
  private void initTimer() {
    this.cdt = new CountDownTimer(4000L, 1000L) {
        public void onFinish() {
          ChangeAppActivity.access$1102(ChangeAppActivity.this, true);
        }
        
        public void onTick(long param1Long) {
          ChangeAppActivity.access$1102(ChangeAppActivity.this, false);
        }
      };
  }
  
  private void pay() {
    (new AliPayUtil(getActivity())).requestOrder(this.mToken, this.vip.getMoney(), this.vip.getBizcode());
  }
  
  private void save() {
    byte[] arrayOfByte = BitmapUtil.DrawableToByte(this.changedAppIcon.getDrawable());
    String str2 = this.editAppName.getText().toString();
    if (TextUtils.isEmpty(str2)) {
      ToastUtil.showToast("名称不能为空");
      return;
    } 
    String str1 = new String(Base64.encode(arrayOfByte, 0));
    SPUtils.put((Context)App.getApp(), "key_iconbyte", str1);
    SPUtils.put((Context)App.getApp(), "key_appname", str2);
    ArrayList<AppInfoLite> arrayList = new ArrayList();
    arrayList.add(new AppInfoLite(this.appInfoLite.packageName, this.appInfoLite.path, this.appInfoLite.label, this.appInfoLite.dynamic, this.appInfoLite.targetSdkVersion, this.appInfoLite.requestedPermissions));
    Intent intent = new Intent();
    intent.putParcelableArrayListExtra("va.extra.APP_INFO_LIST", arrayList);
    getActivity().setResult(-1, intent);
    SPUtils.put((Context)App.getApp(), "is_sys_app", Boolean.valueOf(this.isSys));
    finish();
  }
  
  private void setImage() {}
  
  public void onClick(View paramView) {
    EditText editText;
    StringBuilder stringBuilder;
    switch (paramView.getId()) {
      default:
        return;
      case 2131296795:
        this.isSys = false;
        this.editAppName.setText(this.appName);
        this.editAppName.setEnabled(false);
        this.sysText.setTextColor(getResources().getColor(2131099848));
        this.virtualText.setTextColor(getResources().getColor(2131099843));
        this.imgTv.setVisibility(8);
        this.imgLayout.setVisibility(8);
      case 2131296700:
        this.isSys = true;
        editText = this.editAppName;
        stringBuilder = new StringBuilder();
        stringBuilder.append(this.appName);
        stringBuilder.append(this.pknumber);
        editText.setText(stringBuilder.toString());
        this.editAppName.setEnabled(true);
        this.sysText.setTextColor(getResources().getColor(2131099843));
        this.virtualText.setTextColor(getResources().getColor(2131099848));
        this.imgTv.setVisibility(0);
        this.imgLayout.setVisibility(0);
      case 2131296643:
        if (checkVip() && check64bitOk())
          checkData2(); 
      case 2131296568:
        finish();
      case 2131296443:
        startActivity((new Intent((Context)this, WebViewActivity.class)).putExtra("weburl", Config.WEB_DETAIL).putExtra("title", "详细说明"));
      case 2131296299:
        break;
    } 
    final SelectIconDialog selectIconDialog = new SelectIconDialog((Context)this, 2131689644);
    selectIconDialog.setOnPositionLisenter(new PermissionDialog.OnPositionLisenter() {
          public void onPositionLisenter() {
            String str = ChangeAppActivity.this.getSharedPreferences("selectIcon", 0).getString("content", "");
            if (str == null || str.length() <= 0) {
              selectIconDialog.dismiss();
              return;
            } 
            ChangeAppActivity.access$302(ChangeAppActivity.this, Base64.decode(str.getBytes(), 0));
            ChangeAppActivity changeAppActivity = ChangeAppActivity.this;
            ChangeAppActivity.access$402(changeAppActivity, BitmapFactory.decodeByteArray(changeAppActivity.selectIcon, 0, ChangeAppActivity.this.selectIcon.length));
            ChangeAppActivity.this.changedAppIcon.setImageBitmap(ChangeAppActivity.this.bitmap);
            selectIconDialog.dismiss();
          }
        });
    selectIconDialog.show();
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  public void onError() {}
  
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(MessageEvent paramMessageEvent) {
    if (paramMessageEvent.getType() == 2)
      (new HttpManger(this)).reportVip(); 
    if (paramMessageEvent.getType() == 6)
      autoSave(); 
  }
  
  protected void onPause() {
    super.onPause();
    CountDownTimer countDownTimer = this.cdt;
    if (countDownTimer != null)
      countDownTimer.start(); 
  }
  
  protected void onRestart() {
    super.onRestart();
  }
  
  public void onSuccess(String paramString, JSONObject paramJSONObject) {
    if (paramJSONObject != null)
      try {
        JSONException jSONException;
        boolean bool;
        HttpBean httpBean = (HttpBean)JSON.parseObject(paramJSONObject.toString(), HttpBean.class);
        if (httpBean != null) {
          bool = true;
        } else {
          bool = false;
        } 
        if (((true ^ TextUtils.isEmpty(httpBean.getData().toString())) & bool) != 0) {
          JSONObject jSONObject = new JSONObject();
          this(httpBean.getData().toString());
          boolean bool1 = paramString.equals(HttpManger.KEY_REPORT_VIP);
          paramJSONObject = jSONObject;
          if (bool1) {
            bool1 = "1".equals(httpBean.getStatus());
            if (bool1) {
              try {
                paramJSONObject = new JSONObject();
                this(httpBean.getData().toString());
                try {
                  if (!paramJSONObject.isNull("difference")) {
                    SPUtils.put((Context)this, "my_expried_time", paramJSONObject.getString("difference"));
                    SPUtils.put((Context)this, "my_expried_time_str", paramJSONObject.getString("expriedtime"));
                    SPUtils.put((Context)this, "my_alwaysvip", paramJSONObject.getString("alwaysvip"));
                    SPUtils.put((Context)this, "my_mobile", paramJSONObject.getString("mobile"));
                    SPUtils.put((Context)this, "is_svip", paramJSONObject.getString("svip"));
                    SPUtils.put((Context)this, "package_userid", paramJSONObject.getString("packageuserid"));
                  } 
                } catch (JSONException jSONException2) {
                  jSONException1 = jSONException2;
                } 
              } catch (JSONException jSONException1) {
                JSONException jSONException3 = jSONException2;
              } 
              jSONException1.printStackTrace();
            } 
            ToastUtil.showToast(jSONException1.getInfo());
            jSONException = jSONException2;
          } 
        } else {
          return;
        } 
        if (paramString.equals(HttpManger.KEY_VIP_LIST) && !jSONException.isNull("list"))
          this.vipInfos = JSON.parseArray(jSONException.getString("list"), VIPGoodsBean.class); 
        if (paramString.equals(HttpManger.KEY_VIP_GIFT) && !jSONException.isNull("appcount")) {
          SPUtils.put((Context)this, "my_app_count", jSONException.getString("appcount"));
          SPUtils.put((Context)this, "my_alwaysvip", jSONException.getString("alwaysvip"));
          TipsWhiteDialog tipsWhiteDialog = new TipsWhiteDialog();
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("恭喜您，获得免费分身");
          stringBuilder.append(jSONException.getString("value"));
          stringBuilder.append("个");
          this((Context)this, stringBuilder.toString(), "");
          this.isComment = false;
          tipsWhiteDialog.show();
        } 
      } catch (JSONException jSONException) {
        jSONException.printStackTrace();
      }  
  }
  
  private class CheckThead extends Thread {
    private CheckThead() {}
    
    public void run() {
      super.run();
      ChangeAppActivity.access$102(ChangeAppActivity.this, false);
      ChangeAppActivity.this.check64bit();
      ChangeAppActivity.access$102(ChangeAppActivity.this, true);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\appManage\ChangeAppActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */