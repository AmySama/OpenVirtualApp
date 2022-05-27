package io.virtualapp.appManage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VirtualLocationManager;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.remote.InstalledAppInfo;
import com.lody.virtual.remote.vloc.VLocation;
import com.sheep2.dkfs.common.AppInfo;
import com.sheep2.dkfs.util.ApkSearchUtils;
import com.stub.StubApp;
import io.virtualapp.App;
import io.virtualapp.Utils.AliPayUtil;
import io.virtualapp.Utils.AnimationUtil;
import io.virtualapp.Utils.BitmapUtil;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.Utils.ToastUtil;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.bean.IntegralInfoBean;
import io.virtualapp.bean.MessageEvent;
import io.virtualapp.bean.VIPGoodsBean;
import io.virtualapp.dialog.AppManageDialog;
import io.virtualapp.dialog.SelectVipDialog;
import io.virtualapp.dialog.TipsDialog;
import io.virtualapp.home.ListAppActivity;
import io.virtualapp.home.LoadingActivity;
import io.virtualapp.home.models.AppData;
import io.virtualapp.home.models.LocationData;
import io.virtualapp.home.models.MultiplePackageAppData;
import io.virtualapp.home.models.PackageAppData;
import io.virtualapp.home.repo.AppRepository;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import io.virtualapp.webview.WebViewActivity;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class AppManageActivity extends VActivity implements HttpCall {
  private static final String KEY_INTENT = "KEY_INTENT";
  
  private static final String KEY_USER = "KEY_USER";
  
  private static final String PKG_NAME_ARGUMENT = "MODEL_ARGUMENT";
  
  private static final int REQUSET_CHANGE_CODE = 1002;
  
  private static final int REQUSET_CODE = 1001;
  
  private LinearLayout backLayout;
  
  int fromYDelta;
  
  private boolean isPopWindowShowing = false;
  
  private GridView listView;
  
  private MyAdapter mAdapter;
  
  private MultiplePackageAppData mAppData;
  
  private View mGrayLayout;
  
  private List<AppData> mInstalledBeans;
  
  private IntegralInfoBean mPayBean;
  
  private PopupWindow mPopupWindow;
  
  private AppRepository mRepo;
  
  private LocationData mSelectData;
  
  private String mToken;
  
  private LinearLayout mainLayout;
  
  private LinearLayout noAppLayout;
  
  View.OnClickListener onclick = new View.OnClickListener() {
      public void onClick(View param1View) {
        if (param1View.getId() == 2131296568) {
          AppManageActivity.this.finish();
        } else if (param1View.getId() == 2131296688) {
          ListAppActivity.gotoListApp((Activity)AppManageActivity.this, true);
          AppManageActivity.this.finish();
        } 
      }
    };
  
  private TextView startMakeBnt;
  
  private TextView titleView;
  
  private VIPGoodsBean vip;
  
  private List<VIPGoodsBean> vipInfos;
  
  static {
    StubApp.interface11(9546);
  }
  
  private void bindViews() {
    this.titleView = (TextView)findViewById(2131296401);
    this.backLayout = (LinearLayout)findViewById(2131296568);
    GridView gridView = (GridView)findViewById(2131296575);
    this.listView = gridView;
    gridView.setSelector((Drawable)new ColorDrawable(0));
    this.listView.setAdapter((ListAdapter)this.mAdapter);
    this.noAppLayout = (LinearLayout)findViewById(2131296597);
    this.mainLayout = (LinearLayout)findViewById(2131296316);
    this.mGrayLayout = findViewById(2131296492);
    this.startMakeBnt = (TextView)findViewById(2131296688);
    findViewById(2131296423).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            AppManageActivity.this.startActivity((new Intent((Context)AppManageActivity.this, WebViewActivity.class)).putExtra("weburl", "http://copy.ymzer.com/page/dkfs/permission.html").putExtra("title", "图文教程"));
          }
        });
  }
  
  private void checkMyPermission(MultiplePackageAppData paramMultiplePackageAppData) {
    this.mAppData = paramMultiplePackageAppData;
    if (ContextCompat.checkSelfPermission((Context)getApplication(), "com.android.launcher.permission.INSTALL_SHORTCUT") == 0) {
      createShortcut(paramMultiplePackageAppData);
    } else {
      ActivityCompat.requestPermissions((Activity)this, new String[] { "com.android.launcher.permission.INSTALL_SHORTCUT" }, 1111);
    } 
  }
  
  private boolean checkVip() {
    String str1 = SPUtils.get((Context)App.getApp(), "my_expried_time");
    String str2 = SPUtils.get((Context)App.getApp(), "my_alwaysvip");
    return (!TextUtils.isEmpty(str2) && str2.equals("1")) ? true : (!(TextUtils.isEmpty(str1) || Long.valueOf(str1).longValue() < 0L));
  }
  
  private void createShortcut(final MultiplePackageAppData packageAppData) {
    VirtualCore.OnEmitShortcutListener onEmitShortcutListener = new VirtualCore.OnEmitShortcutListener() {
        public Bitmap getIcon(Bitmap param1Bitmap) {
          App app = App.getApp();
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(packageAppData.getPackageName());
          stringBuilder.append(packageAppData.getUserId());
          stringBuilder.append("icon");
          String str = SPUtils.get((Context)app, stringBuilder.toString());
          try {
            byte[] arrayOfByte = Base64.decode(str.getBytes(), 0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
            if (bitmap != null)
              param1Bitmap = bitmap; 
          } catch (Exception exception) {
            exception.printStackTrace();
          } 
          return param1Bitmap;
        }
        
        public String getName(String param1String) {
          int i = packageAppData.getUserId();
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(packageAppData.getName());
          stringBuilder.append(i + 1);
          String str2 = stringBuilder.toString();
          App app = App.getApp();
          stringBuilder = new StringBuilder();
          stringBuilder.append(packageAppData.getPackageName());
          stringBuilder.append(packageAppData.getUserId());
          String str3 = SPUtils.get((Context)app, stringBuilder.toString());
          String str1 = str2;
          if (!TextUtils.isEmpty(str3)) {
            str1 = str2;
            if (!str3.equals(""))
              str1 = str3; 
          } 
          return str1;
        }
      };
    try {
      Intent intent = new Intent();
      this();
      intent.setClass((Context)App.getApp(), LoadingActivity.class);
      intent.putExtra("MODEL_ARGUMENT", packageAppData.appInfo.packageName);
      intent.putExtra("KEY_USER", packageAppData.userId);
    } finally {
      packageAppData = null;
      packageAppData.printStackTrace();
    } 
  }
  
  private void doTags(String paramString) {
    byte b;
    switch (paramString.hashCode()) {
      default:
        b = -1;
        break;
      case 1746605041:
        if (paramString.equals("appmanagedelete")) {
          b = 3;
          break;
        } 
      case 1720426998:
        if (paramString.equals("appmanagechange")) {
          b = 1;
          break;
        } 
      case 236565627:
        if (paramString.equals("appmanagelocation")) {
          b = 0;
          break;
        } 
      case -2006978292:
        if (paramString.equals("appmanageshortcut")) {
          b = 2;
          break;
        } 
    } 
    if (b != 1) {
      if (b != 2) {
        if (b == 3)
          uninstallDialog(this.mAppData); 
      } else {
        if (!checkVip()) {
          final SelectVipDialog dialog = new SelectVipDialog((Context)getActivity(), 2131689644, SPUtils.get((Context)App.getApp(), "token"), this.vipInfos, "开通会员可以无限制创建桌面快捷");
          selectVipDialog.setPositionLisenter(new SelectVipDialog.PositionLisenter() {
                public void setValue(VIPGoodsBean param1VIPGoodsBean) {
                  AppManageActivity.access$602(AppManageActivity.this, param1VIPGoodsBean);
                  AppManageActivity.this.pay();
                  dialog.dismiss();
                }
              });
          selectVipDialog.show();
          return;
        } 
        checkMyPermission(this.mAppData);
      } 
    } else {
      Intent intent = new Intent((Context)this, ChangeAppActivity.class);
      intent.putExtra("pkgName", this.mAppData.getPackageName());
      intent.putExtra("userId", this.mAppData.getUserId());
      intent.putExtra("appIcon", BitmapUtil.DrawableToByte(this.mAppData.getIcon()));
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mAppData.getName());
      stringBuilder.append(this.mAppData.getUserId() + 1);
      String str2 = stringBuilder.toString();
      App app = App.getApp();
      stringBuilder = new StringBuilder();
      stringBuilder.append(this.mAppData.appInfo.packageName);
      stringBuilder.append(this.mAppData.getUserId());
      String str3 = SPUtils.get((Context)app, stringBuilder.toString());
      String str1 = str2;
      if (!TextUtils.isEmpty(str3)) {
        str1 = str2;
        if (!str3.equals(""))
          str1 = str3; 
      } 
      intent.putExtra("appName", str1);
      startActivity(intent);
    } 
  }
  
  private void initData() {
    this.titleView.setText("分身管理");
    this.backLayout.setOnClickListener(this.onclick);
    this.startMakeBnt.setOnClickListener(this.onclick);
    this.mAdapter = new MyAdapter((Context)this);
    List list = VirtualCore.get().getInstalledApps(0);
    ArrayList<MultiplePackageAppData> arrayList = new ArrayList();
    for (InstalledAppInfo installedAppInfo : list) {
      if (!VirtualCore.get().isPackageLaunchable(installedAppInfo.packageName))
        continue; 
      PackageAppData packageAppData = new PackageAppData(getBaseContext(), installedAppInfo);
      if (VirtualCore.get().isAppInstalledAsUser(0, installedAppInfo.packageName))
        arrayList.add(new MultiplePackageAppData(packageAppData, 0)); 
      for (int i : installedAppInfo.getInstalledUsers()) {
        if (i != 0)
          arrayList.add(new MultiplePackageAppData(packageAppData, i)); 
      } 
    } 
    ApkSearchUtils apkSearchUtils = new ApkSearchUtils((Context)App.getApp(), true);
    apkSearchUtils.AddInstalledAppInfo();
    for (AppInfo appInfo : apkSearchUtils.getMyFiles())
      arrayList.add(new MultiplePackageAppData(new PackageAppData(appInfo.packageName, appInfo.appName, appInfo.icon, true), true)); 
    this.listView.setAdapter((ListAdapter)this.mAdapter);
    this.mAdapter.setData(arrayList);
  }
  
  private void pay() {
    (new AliPayUtil(getActivity())).requestOrder(this.mToken, this.vip.getMoney(), this.vip.getBizcode());
  }
  
  private void readLocation(LocationData paramLocationData) {
    paramLocationData.mode = VirtualLocationManager.get().getMode(paramLocationData.userId, paramLocationData.packageName);
    paramLocationData.location = VirtualLocationManager.get().getLocation(paramLocationData.userId, paramLocationData.packageName);
  }
  
  private void saveLocation(LocationData paramLocationData) {
    if (paramLocationData.location == null || paramLocationData.location.isEmpty()) {
      VirtualLocationManager.get().setMode(paramLocationData.userId, paramLocationData.packageName, 0);
    } else if (paramLocationData.mode != 2) {
      VirtualLocationManager.get().setMode(paramLocationData.userId, paramLocationData.packageName, 2);
    } 
    VirtualLocationManager.get().setLocation(paramLocationData.userId, paramLocationData.packageName, paramLocationData.location);
  }
  
  private void showExceptionDialog(String paramString1, String paramString2) {
    AlertDialog.Builder builder = (new AlertDialog.Builder((Context)this)).setCancelable(false).setTitle(paramString1);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString2);
    stringBuilder.append("请到“设置”>“应用”>“权限”中配置权限。");
    builder.setMessage(stringBuilder.toString()).setNegativeButton("取消", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            param1DialogInterface.cancel();
          }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            AppManageActivity.this.startAppSettings();
            param1DialogInterface.cancel();
          }
        }).show();
  }
  
  private void startAppSettings() {
    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("package:");
    stringBuilder.append(getPackageName());
    intent.setData(Uri.parse(stringBuilder.toString()));
    startActivity(intent);
  }
  
  private void uninstallDialog(MultiplePackageAppData paramMultiplePackageAppData) {
    Dialog dialog = new Dialog((Context)this, 2131689644);
    dialog.setContentView(2131427435);
    dialog.setCancelable(false);
    TextView textView1 = (TextView)dialog.findViewById(2131296721);
    TextView textView2 = (TextView)dialog.findViewById(2131296497);
    TextView textView3 = (TextView)dialog.findViewById(2131296394);
    TextView textView4 = (TextView)dialog.findViewById(2131296758);
    textView1.setText(getResources().getString(2131624082, new Object[] { paramMultiplePackageAppData.getName() }));
    textView2.setText(getResources().getString(2131624081, new Object[] { paramMultiplePackageAppData.getName() }));
    textView3.setOnClickListener(new _$$Lambda$AppManageActivity$_woF_dvD8o72mvEHj3Bwkh7wdLM(dialog));
    textView4.setOnClickListener(new _$$Lambda$AppManageActivity$ij2MFwjUHc3vywc42nqq0Fk7Z4o(this, paramMultiplePackageAppData, dialog));
    dialog.show();
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    if (paramInt1 == 1001 && paramInt2 == -1) {
      VLocation vLocation = (VLocation)paramIntent.getParcelableExtra("virtual_location");
      LocationData locationData = this.mSelectData;
      if (locationData != null) {
        locationData.location = vLocation;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("set");
        stringBuilder.append(this.mSelectData);
        VLog.i("kk", stringBuilder.toString(), new Object[0]);
        saveLocation(this.mSelectData);
        this.mSelectData = null;
        initData();
      } 
    } 
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }
  
  public void onError() {}
  
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(MessageEvent paramMessageEvent) {
    if (paramMessageEvent.getType() == 1)
      initData(); 
  }
  
  public native void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint);
  
  protected void onResume() {
    super.onResume();
    initData();
  }
  
  public void onSuccess(String paramString, JSONObject paramJSONObject) {
    if (paramJSONObject != null)
      try {
        boolean bool;
        HttpBean httpBean = (HttpBean)JSON.parseObject(paramJSONObject.toString(), HttpBean.class);
        if (httpBean != null) {
          bool = true;
        } else {
          bool = false;
        } 
        if ((bool & (TextUtils.isEmpty(httpBean.getData().toString()) ^ true)) != 0) {
          ArrayList<MultiplePackageAppData> arrayList;
          paramJSONObject = new JSONObject();
          this(httpBean.getData().toString());
          boolean bool1 = paramString.equals(HttpManger.KEY_VIP_LIST);
          if (bool1 && !paramJSONObject.isNull("list"))
            this.vipInfos = JSON.parseArray(paramJSONObject.getString("list"), VIPGoodsBean.class); 
          if (paramString.equals(HttpManger.KEY_GETAPPLIST)) {
            if (!paramJSONObject.isNull("list")) {
              List list = JSON.parseArray(paramJSONObject.getString("list"), InstalledAppInfo.class);
              arrayList = new ArrayList();
              this();
              if (list != null && list.size() > 0) {
                for (InstalledAppInfo installedAppInfo : list) {
                  if (!VirtualCore.get().isAppInstalled(installedAppInfo.packageName) || !VirtualCore.get().isPackageLaunchable(installedAppInfo.packageName))
                    continue; 
                  new PackageAppData((Context)this, installedAppInfo);
                } 
                this.mAdapter.setData(arrayList);
              } 
            } 
          } else if (arrayList.equals(HttpManger.KEY_REPORTINFO) && ("1".equals(installedAppInfo.getStatus()) || "3".equals(installedAppInfo.getStatus()))) {
            EventBus eventBus = EventBus.getDefault();
            MessageEvent messageEvent = new MessageEvent();
            this(1);
            eventBus.post(messageEvent);
            ToastUtil.showToast("操作成功");
          } 
        } 
      } catch (JSONException jSONException) {
        jSONException.printStackTrace();
      }  
  }
  
  private class MyAdapter extends BaseAdapter {
    private Context mContext;
    
    private List<MultiplePackageAppData> mDatas;
    
    public MyAdapter(Context param1Context) {
      this.mContext = param1Context;
      this.mDatas = new ArrayList<MultiplePackageAppData>();
    }
    
    public int getCount() {
      return this.mDatas.size();
    }
    
    public Object getItem(int param1Int) {
      return this.mDatas.get(param1Int);
    }
    
    public long getItemId(int param1Int) {
      return param1Int;
    }
    
    public View getView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
      AppManageActivity.ViewHolder viewHolder1;
      AppManageActivity.ViewHolder viewHolder2;
      if (param1View == null) {
        viewHolder1 = new AppManageActivity.ViewHolder();
        View view = LayoutInflater.from(this.mContext).inflate(2131427477, null);
        viewHolder1.itemLayout = (RelativeLayout)view.findViewById(2131296495);
        viewHolder1.iconView = (ImageView)view.findViewById(2131296312);
        viewHolder1.nameView = (TextView)view.findViewById(2131296593);
        viewHolder1.timeView = (TextView)view.findViewById(2131296717);
        viewHolder1.arrowView = (ImageView)view.findViewById(2131296321);
        view.setTag(viewHolder1);
      } else {
        AppManageActivity.ViewHolder viewHolder = (AppManageActivity.ViewHolder)viewHolder1.getTag();
        viewHolder2 = viewHolder1;
        viewHolder1 = viewHolder;
      } 
      final MultiplePackageAppData packageAppData = this.mDatas.get(param1Int);
      viewHolder1.iconView.setImageDrawable(multiplePackageAppData.getIcon());
      param1Int = multiplePackageAppData.getUserId();
      TextView textView = viewHolder1.nameView;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(multiplePackageAppData.getName());
      stringBuilder.append(param1Int + 1);
      textView.setText(stringBuilder.toString());
      if (multiplePackageAppData.isSys()) {
        PackageManager packageManager = App.getApp().getPackageManager();
        try {
          Drawable drawable = packageManager.getApplicationInfo(multiplePackageAppData.packageName, 0).loadIcon(packageManager);
          viewHolder1.iconView.setImageDrawable(drawable);
        } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
          nameNotFoundException.printStackTrace();
        } 
        viewHolder1.nameView.setText(multiplePackageAppData.getName());
      } else {
        viewHolder1.iconView.setImageDrawable(multiplePackageAppData.getIcon());
      } 
      viewHolder1.itemLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param2View) {
              AppManageActivity.access$002(AppManageActivity.this, packageAppData);
              final AppManageDialog dialog = new AppManageDialog((Context)AppManageActivity.this.getActivity(), 2131689644, "");
              appManageDialog.setPositionLisenter(new AppManageDialog.PositionLisenter() {
                    public void setValue(String param3String) {
                      AppManageActivity.this.doTags(param3String);
                      dialog.dismiss();
                    }
                  });
              appManageDialog.show();
            }
          });
      AppManageActivity.this.mGrayLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param2View) {
              if (AppManageActivity.this.isPopWindowShowing) {
                AppManageActivity.this.mPopupWindow.getContentView().startAnimation(AnimationUtil.createOutAnimation((Context)AppManageActivity.this, AppManageActivity.this.fromYDelta));
                AppManageActivity.this.mPopupWindow.getContentView().postDelayed(new Runnable() {
                      public void run() {
                        AppManageActivity.this.mPopupWindow.dismiss();
                      }
                    },  500L);
              } 
            }
          });
      return (View)viewHolder2;
    }
    
    public void setData(List<MultiplePackageAppData> param1List) {
      this.mDatas = param1List;
      notifyDataSetChanged();
    }
  }
  
  class null implements View.OnClickListener {
    public void onClick(View param1View) {
      AppManageActivity.access$002(AppManageActivity.this, packageAppData);
      final AppManageDialog dialog = new AppManageDialog((Context)AppManageActivity.this.getActivity(), 2131689644, "");
      appManageDialog.setPositionLisenter(new AppManageDialog.PositionLisenter() {
            public void setValue(String param3String) {
              AppManageActivity.this.doTags(param3String);
              dialog.dismiss();
            }
          });
      appManageDialog.show();
    }
  }
  
  class null implements AppManageDialog.PositionLisenter {
    public void setValue(String param1String) {
      AppManageActivity.this.doTags(param1String);
      dialog.dismiss();
    }
  }
  
  class null implements View.OnClickListener {
    public void onClick(View param1View) {
      if (AppManageActivity.this.isPopWindowShowing) {
        AppManageActivity.this.mPopupWindow.getContentView().startAnimation(AnimationUtil.createOutAnimation((Context)AppManageActivity.this, AppManageActivity.this.fromYDelta));
        AppManageActivity.this.mPopupWindow.getContentView().postDelayed(new Runnable() {
              public void run() {
                AppManageActivity.this.mPopupWindow.dismiss();
              }
            },  500L);
      } 
    }
  }
  
  class null implements Runnable {
    public void run() {
      AppManageActivity.this.mPopupWindow.dismiss();
    }
  }
  
  public static interface OnEmitShortcutListener {
    Bitmap getIcon(Bitmap param1Bitmap);
    
    String getName(String param1String);
  }
  
  class ViewHolder {
    ImageView arrowView;
    
    ImageView iconView;
    
    RelativeLayout itemLayout;
    
    TextView nameView;
    
    TextView timeView;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\appManage\AppManageActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */