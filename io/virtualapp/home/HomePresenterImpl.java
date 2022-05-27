package io.virtualapp.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.open.MultiAppHelper;
import com.lody.virtual.remote.InstalledAppInfo;
import com.lody.virtual.remote.VAppInstallerResult;
import com.sheep2.dkfs.biz.FMultOpenManager;
import com.sheep2.dkfs.common.OldWeixinModel;
import io.virtualapp.App;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.abs.ui.VUiKit;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.dialog.LoadingDialog;
import io.virtualapp.home.models.AppData;
import io.virtualapp.home.models.AppInfoLite;
import io.virtualapp.home.models.MultiplePackageAppData;
import io.virtualapp.home.models.PackageAppData;
import io.virtualapp.home.repo.AppRepository;
import io.virtualapp.home.repo.PackageAppDataStorage;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import java.util.ArrayList;
import java.util.List;
import jonathanfinerty.once.Once;
import org.json.JSONObject;

class HomePresenterImpl implements HomeContract.HomePresenter {
  private String appName = "";
  
  private FMultOpenManager fmm;
  
  private OldWeixinModel isOldMakeModel;
  
  private Activity mActivity;
  
  private Handler mHandler = new Handler() {
      public void handleMessage(Message param1Message) {
        super.handleMessage(param1Message);
        if (param1Message.what == 99 && HomePresenterImpl.this.mdialog != null && HomePresenterImpl.this.mdialog.isShowing()) {
          HomePresenterImpl.this.mdialog.dismiss();
          HomePresenterImpl.this.dataChanged();
        } 
      }
    };
  
  private AppRepository mRepo;
  
  private HomeContract.HomeView mView;
  
  private LoadingDialog mdialog;
  
  private ArrayList<String> pkgNames;
  
  HomePresenterImpl(HomeContract.HomeView paramHomeView) {
    this.mView = paramHomeView;
    this.mActivity = paramHomeView.getActivity();
    this.mRepo = new AppRepository((Context)this.mActivity);
    this.mView.setPresenter(this);
    this.mdialog = new LoadingDialog((Context)this.mActivity);
  }
  
  private void commitAppInfo(String paramString1, String paramString2, String paramString3, String paramString4) {
    (new HttpManger(new HttpCall() {
          public void onError() {}
          
          public void onSuccess(String param1String, JSONObject param1JSONObject) {
            if (param1JSONObject != null)
              HttpBean httpBean = (HttpBean)JSON.parseObject(param1JSONObject.toString(), HttpBean.class); 
          }
        })).reportInfo(0, paramString1, paramString2, paramString3, paramString4);
  }
  
  private void handleLoadingApp(AppData paramAppData) {
    VUiKit.defer().when((Runnable)_$$Lambda$HomePresenterImpl$AWArjJ8mztOp8dDt5SnJEpt4wQA.INSTANCE).done(new _$$Lambda$HomePresenterImpl$qt8dPTPy2qTPQD9R6omnJVQBhp4(this, paramAppData));
  }
  
  private void launchApp(int paramInt, String paramString) {
    LoadingActivity.launch((Context)this.mActivity, paramString, paramInt);
  }
  
  public void addApp(AppInfoLite paramAppInfoLite) {
    class AddResult {
      private PackageAppData appData;
      
      private int userId;
    };
    AddResult addResult = new AddResult();
    this.mdialog.show();
    Boolean bool = (Boolean)SPUtils.get((Context)App.getApp(), "is_sys_app", Boolean.valueOf(false));
    VUiKit.defer().when(new _$$Lambda$HomePresenterImpl$D8dO7X6E_H99xJPNDl9oNJuBz0w(this, paramAppInfoLite, bool, addResult)).then(new _$$Lambda$HomePresenterImpl$N_X2r9ejExYKisUTltxB7SyGw7M(addResult, paramAppInfoLite)).fail(new _$$Lambda$HomePresenterImpl$9elBV3COuvUFR8ud1RXLpaC_XoA(this)).done(new _$$Lambda$HomePresenterImpl$8gQXzb8cgHAIBf4tBPdBPp_4dEo(this, bool, addResult, paramAppInfoLite));
  }
  
  public boolean checkExtPackageBootPermission() {
    return false;
  }
  
  public void dataChanged() {
    this.mView.showLoading();
    List<AppData> list = this.mRepo.getVirtualAppsDirect();
    this.mView.loadFinish(list);
  }
  
  public void deleteApp(AppData paramAppData) {
    this.mView.removeAppToLauncher(paramAppData);
    Activity activity = this.mActivity;
    ProgressDialog progressDialog = ProgressDialog.show((Context)activity, activity.getString(2131624070), paramAppData.getName());
    VUiKit.defer().when(new _$$Lambda$HomePresenterImpl$1kqE0vyeiqGL__X5dRm_DjYNLdY(this, paramAppData)).fail(new _$$Lambda$HomePresenterImpl$iDdLT6pyhMJLD3uQ5Ewtz8tH380(progressDialog)).done(new _$$Lambda$HomePresenterImpl$3LRlmnFAbUqljyTrR2Es_Ut7rXY(progressDialog));
  }
  
  public void enterAppSetting(AppData paramAppData) {
    AppSettingActivity.enterAppSetting((Context)this.mActivity, paramAppData.getPackageName(), paramAppData.getUserId());
  }
  
  public int getAppCount() {
    return VirtualCore.get().getInstalledApps(0).size();
  }
  
  public String getLabel(String paramString) {
    return this.mRepo.getLabel(paramString);
  }
  
  public void launchApp(AppData paramAppData) {
    try {
      int i = paramAppData.getUserId();
      String str = paramAppData.getPackageName();
    } finally {
      paramAppData = null;
    } 
  }
  
  public void start() {
    dataChanged();
    if (!Once.beenDone("Should show add app guide")) {
      this.mView.showGuide();
      Once.markDone("Should show add app guide");
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\HomePresenterImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */