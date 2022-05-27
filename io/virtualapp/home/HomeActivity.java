package io.virtualapp.home;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Process;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.oem.OemPermissionHelper;
import com.sheep2.dkfs.common.AppInfo;
import com.sheep2.dkfs.util.ApkSearchUtils;
import com.stub.StubApp;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbiz.WXOpenCustomerServiceChat;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import io.virtualapp.App;
import io.virtualapp.Utils.AppUtils;
import io.virtualapp.Utils.ChannelUtils;
import io.virtualapp.Utils.DownloadData2Manager;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.Utils.ToastUtil;
import io.virtualapp.Utils.UpdateManager;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.appManage.AppManageActivity;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.bean.MessageEvent;
import io.virtualapp.bean.TokenItem;
import io.virtualapp.bean.UpdateBean;
import io.virtualapp.dialog.TipsDialog;
import io.virtualapp.dialog.TipsWhiteDialog;
import io.virtualapp.dialog.XieyiDialog;
import io.virtualapp.home.adapters.HomeViewPagerAdapter;
import io.virtualapp.home.models.AppData;
import io.virtualapp.home.models.AppInfoLite;
import io.virtualapp.home.models.PackageAppData;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import io.virtualapp.integralCenter.PersonalCenterActivity;
import io.virtualapp.integralCenter.PluginsActivity;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends VActivity implements HomeContract.HomeView, HttpCall {
  private static final String TAG = HomeActivity.class.getSimpleName();
  
  List<AppData> AppDatas;
  
  private String adJson;
  
  private ImageView addAppView;
  
  private String alwaysvip;
  
  private CountDownTimer cdt;
  
  private ImageView contactView;
  
  private int current = 0;
  
  private String diff;
  
  private List<Fragment> fragments;
  
  private TextView homeTips;
  
  private boolean isComment = false;
  
  private boolean isExitApp = false;
  
  Activity mContext;
  
  private Fragment mCurrentFragment;
  
  private LinearLayout mPointLayut;
  
  private HomeContract.HomePresenter mPresenter;
  
  private ViewPager mViewPager;
  
  private HomeViewPagerAdapter mViewPagerAdapter;
  
  private LinearLayout manageAppLayout;
  
  private LinearLayout myIntegralLayout;
  
  private View.OnClickListener onclick = new View.OnClickListener() {
      public void onClick(View param1View) {
        IWXAPI iWXAPI;
        switch (param1View.getId()) {
          default:
            return;
          case 2131296782:
            HomeActivity.this.startActivity(new Intent((Context)HomeActivity.this, PersonalCenterActivity.class));
          case 2131296671:
            HomeActivity.this.startActivity(new Intent((Context)HomeActivity.this, PersonalCenterActivity.class));
          case 2131296604:
            HomeActivity.this.startActivity(new Intent((Context)HomeActivity.this, PersonalCenterActivity.class));
          case 2131296591:
            HomeActivity.this.startActivity(new Intent((Context)HomeActivity.this, PluginsActivity.class));
          case 2131296501:
            iWXAPI = WXAPIFactory.createWXAPI((Context)HomeActivity.this, "wxff78878e744f199f");
            if (iWXAPI.getWXAppSupportAPI() >= 671090490) {
              WXOpenCustomerServiceChat.Req req = new WXOpenCustomerServiceChat.Req();
              req.corpId = "wwb55b48ef9357a13e";
              req.url = "https://work.weixin.qq.com/kfid/kfca64b5a64e5b743b9";
              iWXAPI.sendReq((BaseReq)req);
            } 
          case 2131296315:
            HomeActivity.this.startActivity(new Intent((Context)HomeActivity.this, AppManageActivity.class));
          case 2131296300:
            break;
        } 
        ListAppActivity.gotoListApp((Activity)HomeActivity.this);
      }
    };
  
  private ArrayList<String> pkgNames;
  
  private ImageView settingView;
  
  private ImageView vipBar;
  
  private TextView vipStatus;
  
  private void bindViews() {
    this.mViewPager = (ViewPager)findViewById(2131296771);
    this.mPointLayut = (LinearLayout)findViewById(2131296621);
    this.manageAppLayout = (LinearLayout)findViewById(2131296315);
    this.addAppView = (ImageView)findViewById(2131296300);
    this.myIntegralLayout = (LinearLayout)findViewById(2131296591);
    this.settingView = (ImageView)findViewById(2131296671);
    this.contactView = (ImageView)findViewById(2131296501);
    this.vipBar = (ImageView)findViewById(2131296782);
    this.vipStatus = (TextView)findViewById(2131296790);
    this.homeTips = (TextView)findViewById(2131296502);
    this.vipBar.setOnClickListener(this.onclick);
    this.settingView.setOnClickListener(this.onclick);
    this.contactView.setOnClickListener(this.onclick);
    findViewById(2131296604).setOnClickListener(this.onclick);
  }
  
  private void checkData2(boolean paramBoolean) {
    Log.e("checkData2", "start");
    String str = SPUtils.get((Context)this, "download_url");
    if (!TextUtils.isEmpty(str)) {
      Activity activity2 = this.mContext;
      StringBuilder stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str);
      stringBuilder3.append("ydkbdata2");
      (new DownloadData2Manager((Context)activity2, stringBuilder3.toString(), "ydkbdata2", paramBoolean)).downloadApk();
      activity2 = this.mContext;
      stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str);
      stringBuilder3.append("ydkbdata1");
      (new DownloadData2Manager((Context)activity2, stringBuilder3.toString(), "ydkbdata1", paramBoolean)).downloadApk();
      Activity activity3 = this.mContext;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str);
      stringBuilder1.append("ydkbdata");
      (new DownloadData2Manager((Context)activity3, stringBuilder1.toString(), "ydkbdata", paramBoolean)).downloadApk();
      activity3 = this.mContext;
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str);
      stringBuilder1.append("ydkbdata3");
      (new DownloadData2Manager((Context)activity3, stringBuilder1.toString(), "ydkbdata3", paramBoolean)).downloadApk();
      Activity activity1 = this.mContext;
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str);
      stringBuilder2.append("ydkbdata4");
      (new DownloadData2Manager((Context)activity1, stringBuilder2.toString(), "ydkbdata4", paramBoolean)).downloadApk();
    } 
  }
  
  private void checkFirstLaunch() {
    if (!((Boolean)SPUtils.get((Context)this, "is_frist_lucher_app", Boolean.valueOf(false))).booleanValue()) {
      final XieyiDialog dialog = new XieyiDialog((Context)getActivity(), "温馨提示", "");
      xieyiDialog.show();
      xieyiDialog.setOnPositionLisenter(new XieyiDialog.OnPositionLisenter() {
            public void onPositionLisenter() {
              dialog.dismiss();
              SPUtils.put((Context)HomeActivity.this, "is_frist_lucher_app", Boolean.valueOf(true));
              HomeActivity.this.checkMyPermission();
              new HomePresenterImpl(HomeActivity.this);
              HomeActivity.this.mPresenter.start();
              HomeActivity.this.initIntent();
            }
          });
    } else {
      refreshBar();
      new HomePresenterImpl(this);
      this.mPresenter.start();
      initIntent();
      String str = SPUtils.get((Context)this, "my_alwaysvip");
      if (!TextUtils.isEmpty(str))
        str.equals("1"); 
    } 
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
    if (arrayList2.size() > 0)
      ActivityCompat.requestPermissions((Activity)this, arrayList2.<String>toArray(new String[arrayList2.size()]), 1111); 
  }
  
  public static String getPackageSourceDir(Context paramContext, String paramString) {
    PackageManager packageManager = paramContext.getPackageManager();
    byte b = 0;
    List<PackageInfo> list = packageManager.getInstalledPackages(0);
    while (b < list.size()) {
      PackageInfo packageInfo = list.get(b);
      if (packageInfo.packageName.equals(paramString))
        return packageInfo.applicationInfo.sourceDir; 
      b++;
    } 
    return "";
  }
  
  public static void goHome(Context paramContext) {
    Intent intent = new Intent(paramContext, HomeActivity.class);
    intent.addFlags(131072);
    intent.addFlags(268435456);
    paramContext.startActivity(intent);
  }
  
  public static void goHome(Context paramContext, ArrayList<String> paramArrayList) {
    Intent intent = new Intent(paramContext, HomeActivity.class);
    intent.addFlags(131072);
    intent.addFlags(268435456);
    intent.putStringArrayListExtra("list_nomal_app", paramArrayList);
    paramContext.startActivity(intent);
  }
  
  public static void goHome(Context paramContext, ArrayList<AppInfoLite> paramArrayList, boolean paramBoolean) {
    Intent intent = new Intent(paramContext, HomeActivity.class);
    intent.addFlags(131072);
    intent.addFlags(268435456);
    intent.putParcelableArrayListExtra("va.extra.APP_INFO_LIST", paramArrayList);
    intent.putExtra("formAppManager", paramBoolean);
    AtyContainer.finishAllActivity();
    paramContext.startActivity(intent);
  }
  
  public static void goToMarket(Context paramContext, String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("market://details?id=");
    stringBuilder.append(paramString);
    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString()));
    try {
      paramContext.startActivity(intent);
    } catch (ActivityNotFoundException activityNotFoundException) {
      Toast.makeText(paramContext, "您没有安装应用市场", 0).show();
    } 
  }
  
  private void initCommit() {
    // Byte code:
    //   0: iconst_0
    //   1: istore_1
    //   2: aload_0
    //   3: ldc_w 'is_sec_lucher_app'
    //   6: iconst_0
    //   7: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   10: invokestatic get : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   13: checkcast java/lang/Integer
    //   16: invokevirtual intValue : ()I
    //   19: iconst_1
    //   20: iadd
    //   21: istore_2
    //   22: aload_0
    //   23: ldc_w 'is_sec_lucher_app'
    //   26: iload_2
    //   27: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   30: invokestatic put : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/Object;)V
    //   33: aload_0
    //   34: ldc_w 'is_qurey_sec_lucher_app'
    //   37: iconst_0
    //   38: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   41: invokestatic get : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   44: checkcast java/lang/Boolean
    //   47: invokevirtual booleanValue : ()Z
    //   50: ifeq -> 59
    //   53: aload_0
    //   54: iconst_0
    //   55: putfield isComment : Z
    //   58: return
    //   59: aload_0
    //   60: ldc_w 'is_sec_lucher_date'
    //   63: invokestatic currentTimeMillis : ()J
    //   66: invokestatic valueOf : (J)Ljava/lang/Long;
    //   69: invokestatic get : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
    //   72: checkcast java/lang/Long
    //   75: invokevirtual longValue : ()J
    //   78: lstore_3
    //   79: iload_2
    //   80: iconst_2
    //   81: if_icmpne -> 101
    //   84: aload_0
    //   85: ldc_w 'is_sec_lucher_app'
    //   88: iconst_3
    //   89: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   92: invokestatic put : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/Object;)V
    //   95: iconst_1
    //   96: istore #5
    //   98: goto -> 140
    //   101: iload_1
    //   102: istore #5
    //   104: iload_2
    //   105: iconst_2
    //   106: if_icmple -> 140
    //   109: iload_1
    //   110: istore #5
    //   112: invokestatic currentTimeMillis : ()J
    //   115: lload_3
    //   116: lsub
    //   117: ldc2_w 2592000000
    //   120: lcmp
    //   121: ifle -> 140
    //   124: aload_0
    //   125: ldc_w 'is_sec_lucher_date'
    //   128: invokestatic currentTimeMillis : ()J
    //   131: invokestatic valueOf : (J)Ljava/lang/Long;
    //   134: invokestatic put : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/Object;)V
    //   137: goto -> 95
    //   140: iload #5
    //   142: ifeq -> 198
    //   145: new io/virtualapp/dialog/TipsDialog
    //   148: dup
    //   149: aload_0
    //   150: ldc_w '参与5星好评，送VIP时长'
    //   153: ldc_w '点击下方“去评分”按钮，选择五星好评，最长可获得30天VIP时长'
    //   156: ldc_w '去评分'
    //   159: ldc_w '*每月可参与一次'
    //   162: invokespecial <init> : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   165: astore #6
    //   167: aload #6
    //   169: invokevirtual show : ()V
    //   172: aload_0
    //   173: ldc_w 'is_qurey_sec_lucher_app'
    //   176: iconst_1
    //   177: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   180: invokestatic put : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/Object;)V
    //   183: aload #6
    //   185: new io/virtualapp/home/HomeActivity$5
    //   188: dup
    //   189: aload_0
    //   190: aload #6
    //   192: invokespecial <init> : (Lio/virtualapp/home/HomeActivity;Lio/virtualapp/dialog/TipsDialog;)V
    //   195: invokevirtual setOnPositionLisenter : (Lio/virtualapp/dialog/TipsDialog$OnPositionLisenter;)V
    //   198: return
  }
  
  private void initEvent() {
    this.vipBar.setOnClickListener(this.onclick);
    this.settingView.setOnClickListener(this.onclick);
    this.addAppView.setOnClickListener(this.onclick);
    this.manageAppLayout.setOnClickListener(this.onclick);
    this.myIntegralLayout.setOnClickListener(this.onclick);
    this.mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
          public void onPageScrollStateChanged(int param1Int) {}
          
          public void onPageScrolled(int param1Int1, float param1Float, int param1Int2) {
            ((Fragment)HomeActivity.this.fragments.get(HomeActivity.this.current)).onPause();
            ((Fragment)HomeActivity.this.fragments.get(param1Int1)).onResume();
            HomeActivity homeActivity = HomeActivity.this;
            HomeActivity.access$202(homeActivity, homeActivity.fragments.get(param1Int1));
            HomeActivity.access$002(HomeActivity.this, param1Int1);
            int i = param1Int1 * 12;
            param1Int2 = i + 12;
            if (param1Int2 < HomeActivity.this.AppDatas.size()) {
              ((HomeAppListFragment)HomeActivity.this.mCurrentFragment).setData(HomeActivity.this.AppDatas.subList(i, param1Int2));
            } else {
              ((HomeAppListFragment)HomeActivity.this.mCurrentFragment).setData(HomeActivity.this.AppDatas.subList(i, HomeActivity.this.AppDatas.size()));
            } 
            if (HomeActivity.this.mPointLayut != null && HomeActivity.this.mPointLayut.getChildCount() >= param1Int1)
              for (param1Int2 = 0; param1Int2 < HomeActivity.this.mPointLayut.getChildCount(); param1Int2++) {
                if (HomeActivity.this.mPointLayut.getChildAt(param1Int2) instanceof ImageView) {
                  ImageView imageView = (ImageView)HomeActivity.this.mPointLayut.getChildAt(param1Int2);
                  if (param1Int2 != param1Int1) {
                    imageView.setImageDrawable(HomeActivity.this.getResources().getDrawable(2131231112));
                  } else {
                    imageView.setImageDrawable(HomeActivity.this.getResources().getDrawable(2131230865));
                  } 
                  imageView.setScaleType(ImageView.ScaleType.CENTER);
                } 
              }  
          }
          
          public void onPageSelected(int param1Int) {}
        });
  }
  
  private void initIntent() {
    ArrayList arrayList = getIntent().getParcelableArrayListExtra("va.extra.APP_INFO_LIST");
    new ArrayList();
    if (arrayList != null)
      for (AppInfoLite appInfoLite : arrayList)
        this.mPresenter.addApp(appInfoLite);  
  }
  
  private void initTimer() {
    this.cdt = new CountDownTimer(4000L, 1000L) {
        public void onFinish() {
          HomeActivity.access$802(HomeActivity.this, true);
        }
        
        public void onTick(long param1Long) {
          HomeActivity.access$802(HomeActivity.this, false);
        }
      };
  }
  
  private void login() {
    if (TextUtils.isEmpty(SPUtils.get((Context)App.getApp(), "token"))) {
      (new HttpManger(this)).regist();
    } else {
      (new HttpManger(this)).login();
    } 
  }
  
  private void refreshBar() {
    this.diff = SPUtils.get((Context)App.getApp(), "my_expried_time");
    this.alwaysvip = SPUtils.get((Context)App.getApp(), "my_alwaysvip");
    long l = Long.valueOf(this.diff).longValue();
    if (l <= 0L) {
      this.vipBar.setImageDrawable(getResources().getDrawable(2131230996));
      this.vipStatus.setText("开通会员");
    } else {
      long l1 = l / 86400L;
      l /= 60L;
      long l2 = l / 60L;
      if (l <= 60L || l2 > 24L);
      TextView textView = this.vipStatus;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("剩");
      stringBuilder.append(l1);
      stringBuilder.append("天");
      textView.setText(stringBuilder.toString());
      this.vipBar.setImageDrawable(getResources().getDrawable(2131230983));
    } 
    if (!TextUtils.isEmpty(this.alwaysvip) && this.alwaysvip.equals("1")) {
      this.vipStatus.setText("尊贵的永久会员");
      this.vipBar.setImageDrawable(getResources().getDrawable(2131230983));
    } 
    String str = SPUtils.get((Context)App.getApp(), "show_location");
    if (!TextUtils.isEmpty(str))
      if (str.equals("1")) {
        this.myIntegralLayout.setVisibility(8);
      } else {
        this.myIntegralLayout.setVisibility(8);
      }  
  }
  
  public void addAppToLauncher(AppData paramAppData) {
    hideRank();
    hideLoading();
  }
  
  public void askInstallGms() {}
  
  public void hideBottomAction() {}
  
  public void hideLoading() {}
  
  public void hideRank() {
    this.homeTips.setVisibility(8);
  }
  
  public void isUpdateApp() {
    (new HttpManger(new HttpCall() {
          public void onError() {}
          
          public void onSuccess(String param1String, JSONObject param1JSONObject) {
            if (param1JSONObject != null) {
              HttpBean httpBean = (HttpBean)JSON.parseObject(param1JSONObject.toString(), HttpBean.class);
              if ("1".equals(httpBean.getStatus()) && !TextUtils.isEmpty(httpBean.getData().toString())) {
                UpdateBean updateBean = (UpdateBean)JSON.parseObject(httpBean.getData().toString(), UpdateBean.class);
                if (updateBean != null) {
                  ChannelUtils.getAppMetaData((Context)App.getApp(), "BaiduMobAd_CHANNEL");
                  if (!TextUtils.isEmpty(updateBean.getVer()) && AppUtils.getVersionCode((Context)HomeActivity.this) < Float.valueOf(updateBean.getVer()).floatValue())
                    if ("1".equals(updateBean.getForce())) {
                      (new UpdateManager((Context)HomeActivity.this, updateBean.getDownloadurl(), updateBean.getVerinfo())).showNoticeDialog();
                    } else {
                      (new UpdateManager((Context)HomeActivity.this, updateBean.getDownloadurl(), updateBean.getVerinfo())).showTipDialog();
                    }  
                } 
              } 
            } 
          }
        })).updateApk();
  }
  
  public void loadError(Throwable paramThrowable) {
    paramThrowable.printStackTrace();
    hideLoading();
  }
  
  public void loadFinish(List<AppData> paramList) {
    hideLoading();
    this.AppDatas = new ArrayList<AppData>();
    this.fragments = new ArrayList<Fragment>();
    LinearLayout linearLayout = this.mPointLayut;
    if (linearLayout != null)
      linearLayout.removeAllViews(); 
    Activity activity = this.mContext;
    byte b = 1;
    ApkSearchUtils apkSearchUtils = new ApkSearchUtils((Context)activity, true);
    apkSearchUtils.AddInstalledAppInfo();
    for (AppInfo appInfo : apkSearchUtils.getMyFiles())
      paramList.add(new PackageAppData(appInfo.packageName, appInfo.appName, appInfo.icon, true)); 
    int i = b;
    if (paramList != null) {
      i = b;
      if (paramList.size() > 0) {
        this.AppDatas = paramList;
        hideRank();
        if (paramList.size() % 12 > 0) {
          i = paramList.size() / 12 + 1;
        } else {
          i = paramList.size() / 12;
        } 
        if (i == 0)
          i = b; 
      } 
    } 
    for (b = 0; b < i; b++) {
      HomeAppListFragment homeAppListFragment = HomeAppListFragment.newInstance((Activity)this);
      this.fragments.add(homeAppListFragment);
      ImageView imageView = new ImageView((Context)this);
      if (b == 0) {
        imageView.setImageDrawable(getResources().getDrawable(2131230865));
      } else {
        imageView.setImageDrawable(getResources().getDrawable(2131231112));
      } 
      imageView.setScaleType(ImageView.ScaleType.CENTER);
      this.mPointLayut.addView((View)imageView);
    } 
    ViewPager viewPager = this.mViewPager;
    if (viewPager != null)
      viewPager.removeAllViews(); 
    HomeViewPagerAdapter homeViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager());
    this.mViewPagerAdapter = homeViewPagerAdapter;
    homeViewPagerAdapter.setListfragment(this.fragments);
    this.mViewPager.setAdapter((PagerAdapter)this.mViewPagerAdapter);
    this.mViewPager.setCurrentItem(0);
    this.mCurrentFragment = this.fragments.get(0);
    this.mViewPager.setOffscreenPageLimit(this.fragments.size());
    if (12 < this.AppDatas.size()) {
      ((HomeAppListFragment)this.mCurrentFragment).setData(this.AppDatas.subList(0, 12));
    } else {
      HomeAppListFragment homeAppListFragment = (HomeAppListFragment)this.mCurrentFragment;
      List<AppData> list = this.AppDatas;
      homeAppListFragment.setData(list.subList(0, list.size()));
    } 
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt1 == 5) {
      if (paramInt2 == -1 && paramIntent != null) {
        ArrayList arrayList = paramIntent.getParcelableArrayListExtra("va.extra.APP_INFO_LIST");
        if (arrayList != null)
          for (AppInfoLite appInfoLite : arrayList)
            this.mPresenter.addApp(appInfoLite);  
      } 
    } else if (paramInt1 == 6 && paramInt2 == -1) {
      String str = appInfoLite.getStringExtra("pkg");
      paramInt1 = appInfoLite.getIntExtra("user_id", -1);
      VActivityManager.get().launchApp(paramInt1, str);
    } 
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  protected void onDestroy() {
    super.onDestroy();
    AtyContainer.getInstance().removeActivity((Activity)this);
    EventBus.getDefault().unregister(this);
    ToastUtil.cancelToast();
    finish();
    System.exit(0);
    Process.killProcess(Process.myPid());
  }
  
  public void onError() {}
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    if (paramInt == 4) {
      if (!this.isExitApp) {
        ToastUtil.showToast("再按一次退出");
        this.isExitApp = true;
        return false;
      } 
      ToastUtil.cancelToast();
      finish();
      System.exit(0);
      Process.killProcess(Process.myPid());
    } 
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(MessageEvent paramMessageEvent) {
    if (paramMessageEvent.getType() == 1)
      (new HomePresenterImpl(this)).start(); 
    if (paramMessageEvent.getType() == 2)
      (new HttpManger(this)).reportVip(); 
  }
  
  protected void onPause() {
    super.onPause();
    CountDownTimer countDownTimer = this.cdt;
    if (countDownTimer != null)
      countDownTimer.start(); 
  }
  
  public native void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint);
  
  protected void onRestart() {
    super.onRestart();
    if (this.isComment)
      (new HttpManger(this)).vipGift("1", "copy_gift_market_mark"); 
    if (this.cdt != null)
      this.cdt = null; 
  }
  
  protected void onResume() {
    super.onResume();
    if (((Boolean)SPUtils.get((Context)this, "is_frist_lucher_app", Boolean.valueOf(false))).booleanValue())
      (new HomePresenterImpl(this)).dataChanged(); 
  }
  
  public void onSuccess(String paramString, JSONObject paramJSONObject) {
    HttpBean httpBean = (HttpBean)JSON.parseObject(paramJSONObject.toString(), HttpBean.class);
    if (paramString.equals(HttpManger.KEY_LOGIN))
      if ("1".equals(httpBean.getStatus())) {
        TokenItem tokenItem = (TokenItem)JSON.parseObject(httpBean.getData().toString(), TokenItem.class);
        SPUtils.put((Context)App.getApp(), "token", tokenItem.getToken());
        SPUtils.put((Context)App.getApp(), "my_intergal", tokenItem.getIntegral());
        SPUtils.put((Context)App.getApp(), "viplevel", tokenItem.getViplevel());
        (new HttpManger(this)).reportVip();
      } else if ("001".equals(httpBean.getStatus())) {
        (new HttpManger(this)).regist();
      } else {
        ToastUtil.showToast(httpBean.getInfo());
      }  
    if (paramString.equals(HttpManger.KEY_REGIST))
      if ("1".equals(httpBean.getStatus())) {
        TokenItem tokenItem = (TokenItem)JSON.parseObject(httpBean.getData().toString(), TokenItem.class);
        SPUtils.put((Context)App.getApp(), "token", tokenItem.getToken());
        SPUtils.put((Context)App.getApp(), "my_intergal", tokenItem.getIntegral());
        SPUtils.put((Context)App.getApp(), "viplevel", tokenItem.getViplevel());
        (new HttpManger(this)).reportVip();
      } else if ("006".equals(httpBean.getStatus())) {
        TokenItem tokenItem = (TokenItem)JSON.parseObject(httpBean.getData().toString(), TokenItem.class);
        if (tokenItem != null && !TextUtils.isEmpty(tokenItem.getToken())) {
          SPUtils.put((Context)App.getApp(), "token", tokenItem.getToken());
          (new HttpManger(this)).login();
        } 
      } else {
        ToastUtil.showToast(httpBean.getInfo());
      }  
    if (httpBean != null && !TextUtils.isEmpty(httpBean.getData().toString()))
      try {
        JSONObject jSONObject = new JSONObject();
        this(httpBean.getData().toString());
        boolean bool = paramString.equals(HttpManger.KEY_REPORT_VIP);
        if (bool && !jSONObject.isNull("difference")) {
          SPUtils.put((Context)this, "my_expried_time", jSONObject.getString("difference"));
          SPUtils.put((Context)this, "my_expried_time_str", jSONObject.getString("expriedtime"));
          SPUtils.put((Context)this, "my_alwaysvip", jSONObject.getString("alwaysvip"));
          SPUtils.put((Context)this, "my_mobile", jSONObject.getString("mobile"));
          SPUtils.put((Context)this, "is_svip", jSONObject.getString("svip"));
          SPUtils.put((Context)this, "package_userid", jSONObject.getString("packageuserid"));
          SPUtils.put((Context)this, "show_location", jSONObject.getString("showlocation"));
          SPUtils.put((Context)this, "download_status", jSONObject.getString("downloadstatus"));
          SPUtils.put((Context)this, "key_dypnskey", jSONObject.getString("dypnskey"));
          refreshBar();
        } 
        if (paramString.equals(HttpManger.KEY_VIP_GIFT) && !jSONObject.isNull("difference")) {
          SPUtils.put((Context)this, "my_expried_time", jSONObject.getString("difference"));
          SPUtils.put((Context)this, "my_expried_time_str", jSONObject.getString("expriedtime"));
          SPUtils.put((Context)this, "my_alwaysvip", jSONObject.getString("alwaysvip"));
          refreshBar();
          TipsWhiteDialog tipsWhiteDialog = new TipsWhiteDialog();
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("恭喜您，获得VIP时长");
          stringBuilder.append(jSONObject.getString("value"));
          stringBuilder.append("天");
          this((Context)this, stringBuilder.toString(), "时间已自动累加到VIP时长里");
          this.isComment = false;
          tipsWhiteDialog.show();
        } 
      } catch (JSONException jSONException) {
        jSONException.printStackTrace();
      }  
  }
  
  public void refreshLauncherItem(AppData paramAppData) {
    ((HomeAppListFragment)this.mCurrentFragment).refresh(paramAppData);
  }
  
  public void removeAppToLauncher(AppData paramAppData) {
    ((HomeAppListFragment)this.mCurrentFragment).remove(paramAppData);
  }
  
  public void setPresenter(HomeContract.HomePresenter paramHomePresenter) {
    this.mPresenter = paramHomePresenter;
  }
  
  public void showBottomAction() {}
  
  public void showGuide() {}
  
  public void showLoading() {}
  
  public void showOverlayPermissionDialog() {}
  
  public void showPermissionDialog() {
    Intent intent = OemPermissionHelper.getPermissionActivityIntent((Context)this);
    (new AlertDialog.Builder((Context)this)).setTitle("提示").setMessage("您需要授予64位引擎使用的权限").setCancelable(false).setNegativeButton("前往", new _$$Lambda$HomeActivity$KnhMo6uxeCF_XjyYDkTGgU6d5U4(this, intent)).show();
  }
  
  public void showRank(String paramString) {
    hideLoading();
    this.homeTips.setVisibility(0);
  }
  
  static {
    StubApp.interface11(9686);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\HomeActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */