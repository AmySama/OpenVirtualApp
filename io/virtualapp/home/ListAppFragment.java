package io.virtualapp.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.lody.virtual.client.core.VirtualCore;
import com.stub.StubApp;
import io.virtualapp.App;
import io.virtualapp.Utils.AliPayUtil;
import io.virtualapp.Utils.BitmapUtil;
import io.virtualapp.Utils.DownloadData2Manager;
import io.virtualapp.Utils.MyGridLayoutManager;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.Utils.ToastUtil;
import io.virtualapp.abs.BasePresenter;
import io.virtualapp.abs.ui.VFragment;
import io.virtualapp.appManage.ChangeAppActivity;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.bean.IntegralInfoBean;
import io.virtualapp.bean.ServerAppInfoBean;
import io.virtualapp.bean.VIPGoodsBean;
import io.virtualapp.dialog.WhiteLoadingDialog;
import io.virtualapp.home.adapters.CloneAppListAdapter;
import io.virtualapp.home.models.AppInfo;
import io.virtualapp.home.models.AppInfoLite;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import io.virtualapp.widgets.DragSelectRecyclerView;
import io.virtualapp.widgets.DragSelectRecyclerViewAdapter;
import io.virtualapp.widgets.LabelView;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public class ListAppFragment extends VFragment<ListAppContract.ListAppPresenter> implements ListAppContract.ListAppView, HttpCall {
  private static final String KEY_SELECT_FROM = "key_select_from";
  
  CountDownTimer cdt;
  
  private AppInfo choosedAppInfo;
  
  private boolean formAM;
  
  private List<AppInfo> grideInfos = new ArrayList<AppInfo>();
  
  private List<AppInfo> infos = new ArrayList<AppInfo>();
  
  private boolean isResume = false;
  
  private CloneAppListAdapter mAdapter;
  
  private WhiteLoadingDialog mDialog;
  
  private GridView mGridView;
  
  private MyGridViewApater mGridViewApater;
  
  private List<IntegralInfoBean> mIntegralInfos;
  
  private IntegralInfoBean mPayBean;
  
  private ProgressBar mProgressBar;
  
  private DragSelectRecyclerView mRecyclerView;
  
  private String mToken;
  
  private String newDataurl;
  
  private AppInfo nowInfo;
  
  private VIPGoodsBean vip;
  
  private List<VIPGoodsBean> vipInfos;
  
  private void checkData2(boolean paramBoolean) {
    Log.e("checkData2", "start");
    String str = SPUtils.get((Context)getActivity(), "download_url");
    if (!TextUtils.isEmpty(str)) {
      FragmentActivity fragmentActivity2 = getActivity();
      StringBuilder stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str);
      stringBuilder3.append("ydkbdata2");
      (new DownloadData2Manager((Context)fragmentActivity2, stringBuilder3.toString(), "ydkbdata2", paramBoolean)).downloadApk();
      fragmentActivity2 = getActivity();
      stringBuilder3 = new StringBuilder();
      stringBuilder3.append(str);
      stringBuilder3.append("ydkbdata1");
      (new DownloadData2Manager((Context)fragmentActivity2, stringBuilder3.toString(), "ydkbdata1", paramBoolean)).downloadApk();
      FragmentActivity fragmentActivity3 = getActivity();
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str);
      stringBuilder1.append("ydkbdata");
      (new DownloadData2Manager((Context)fragmentActivity3, stringBuilder1.toString(), "ydkbdata", paramBoolean)).downloadApk();
      FragmentActivity fragmentActivity1 = getActivity();
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str);
      stringBuilder2.append("ydkbdata3");
      (new DownloadData2Manager((Context)fragmentActivity1, stringBuilder2.toString(), "ydkbdata3", paramBoolean)).downloadApk();
      fragmentActivity1 = getActivity();
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str);
      stringBuilder2.append("ydkbdata4");
      (new DownloadData2Manager((Context)fragmentActivity1, stringBuilder2.toString(), "ydkbdata4", paramBoolean)).downloadApk();
    } 
  }
  
  private void checkDownload() {
    if (TextUtils.isEmpty(this.newDataurl))
      return; 
    String str = SPUtils.get((Context)getActivity(), "download_url");
    SPUtils.put((Context)getActivity(), "download_url", this.newDataurl);
    if (TextUtils.isEmpty(str)) {
      this.mDialog.show();
      this.mDialog.setText("正在为您准备分身环境");
      checkData2(true);
      initTimer();
    } else if (str.equals(this.newDataurl)) {
      boolean bool1 = ((Boolean)SPUtils.get((Context)App.getApp(), "download_is_over", Boolean.valueOf(false))).booleanValue();
      boolean bool2 = ((Boolean)SPUtils.get((Context)App.getApp(), "download_data4_is_over", Boolean.valueOf(false))).booleanValue();
      if (bool1 && bool2) {
        gotoMakeApp();
      } else {
        this.mDialog.show();
        this.mDialog.setText("正在为您准备分身环境");
        checkData2(true);
        initTimer();
      } 
    } else {
      this.mDialog.show();
      this.mDialog.setText("正在为您准备分身环境");
      checkData2(true);
      initTimer();
    } 
  }
  
  private File getSelectFrom() {
    Bundle bundle = getArguments();
    if (bundle != null) {
      String str = bundle.getString("key_select_from");
      if (str != null)
        return new File(str); 
    } 
    return null;
  }
  
  private void gotoMakeApp() {
    try {
      String str1;
      Intent intent = new Intent();
      this((Context)getActivity(), ChangeAppActivity.class);
      ArrayList<AppInfoLite> arrayList = new ArrayList();
      byte[] arrayOfByte = null;
      if (this.choosedAppInfo.icon == null) {
        try {
          PackageManager packageManager = App.getApp().getPackageManager();
          byte[] arrayOfByte1 = BitmapUtil.DrawableToByte(packageManager.getApplicationInfo(this.choosedAppInfo.getPackageName(), 0).loadIcon(packageManager));
          arrayOfByte = arrayOfByte1;
        } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
          nameNotFoundException.printStackTrace();
        } 
      } else {
        arrayOfByte = BitmapUtil.DrawableToByte(this.choosedAppInfo.icon);
      } 
      intent.putExtra("appIcon", arrayOfByte);
      if (this.choosedAppInfo.name == null) {
        str1 = this.choosedAppInfo.appName;
      } else {
        str1 = this.choosedAppInfo.name.toString();
      } 
      intent.putExtra("appName", str1);
      String str2 = this.choosedAppInfo.packageName;
      String str3 = this.choosedAppInfo.path;
      if (this.choosedAppInfo.name == null) {
        str1 = this.choosedAppInfo.appName;
      } else {
        str1 = this.choosedAppInfo.name.toString();
      } 
      arrayList.add(new AppInfoLite(str2, str3, str1, this.choosedAppInfo.cloneMode, this.choosedAppInfo.targetSdkVersion, this.choosedAppInfo.requestedPermissions));
      intent.putParcelableArrayListExtra("va.extra.APP_INFO_LIST", arrayList);
      getActivity().startActivityForResult(intent, 100);
    } catch (Exception exception) {}
  }
  
  private void initTimer() {
    CountDownTimer countDownTimer = new CountDownTimer(90000L, 500L) {
        public void onFinish() {
          ToastUtil.showToast("虚拟环境准备失败，请你检查网络是否正常");
          ListAppFragment.this.mDialog.dismiss();
        }
        
        public void onTick(long param1Long) {
          App app = App.getApp();
          Boolean bool = Boolean.valueOf(false);
          boolean bool1 = ((Boolean)SPUtils.get((Context)app, "download_is_over", bool)).booleanValue();
          boolean bool2 = ((Boolean)SPUtils.get((Context)App.getApp(), "download_data4_is_over", bool)).booleanValue();
          if (bool1 && bool2) {
            ListAppFragment.this.mDialog.dismiss();
            ListAppFragment.this.gotoMakeApp();
            ListAppFragment.this.cdt.cancel();
          } 
        }
      };
    this.cdt = countDownTimer;
    countDownTimer.start();
  }
  
  public static ListAppFragment newInstance(File paramFile, boolean paramBoolean) {
    Bundle bundle = new Bundle();
    if (paramFile != null)
      bundle.putString("key_select_from", paramFile.getPath()); 
    bundle.putBoolean("formAppManager", paramBoolean);
    ListAppFragment listAppFragment = new ListAppFragment();
    listAppFragment.setArguments(bundle);
    return listAppFragment;
  }
  
  private void pay() {
    (new AliPayUtil((Activity)getActivity())).requestOrder(this.mToken, this.vip.getMoney(), this.vip.getBizcode());
  }
  
  private void refreshList(List<AppInfo> paramList) {
    if (paramList.size() > 3) {
      this.mAdapter.setList(paramList.subList(3, paramList.size()));
      this.mGridViewApater.setData(paramList.subList(0, 3));
      this.grideInfos = paramList.subList(0, 3);
    } else {
      this.mGridViewApater.setData(paramList);
      this.mAdapter.setList(new ArrayList());
      this.grideInfos = paramList;
    } 
    this.mRecyclerView.setDragSelectActive(false, 0);
    this.mAdapter.setSelected(0, false);
    this.mProgressBar.setVisibility(8);
    this.mRecyclerView.setVisibility(0);
  }
  
  public void loadFinish(List<AppInfo> paramList) {
    ArrayList<AppInfo> arrayList = new ArrayList();
    this.infos = arrayList;
    arrayList.addAll(paramList);
    if (this.infos.size() > 3) {
      this.grideInfos = this.infos.subList(0, 3);
      CloneAppListAdapter cloneAppListAdapter = this.mAdapter;
      paramList = this.infos;
      cloneAppListAdapter.setList(paramList.subList(3, paramList.size()));
      this.mGridViewApater.setData(this.infos.subList(0, 3));
    } else {
      paramList = this.infos;
      this.grideInfos = paramList;
      this.mGridViewApater.setData(paramList);
      this.mAdapter.setList(new ArrayList());
    } 
    (new HttpManger(this)).getBlack();
    (new HttpManger(this)).getVipList();
    this.mRecyclerView.setDragSelectActive(false, 0);
    this.mAdapter.setSelected(0, false);
    this.mProgressBar.setVisibility(8);
    this.mRecyclerView.setVisibility(0);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    return paramLayoutInflater.inflate(2131427442, null);
  }
  
  public void onError() {}
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    StubApp.interface22(paramInt, paramArrayOfString, paramArrayOfint);
    int i = paramArrayOfint.length;
    for (paramInt = 0; paramInt < i; paramInt++) {
      if (paramArrayOfint[paramInt] == 0) {
        checkDownload();
        break;
      } 
      ToastUtil.showToast("为正常使用本软件，请您同意赋予存储权限");
    } 
  }
  
  public void onResume() {
    super.onResume();
  }
  
  public void onSaveInstanceState(Bundle paramBundle) {
    super.onSaveInstanceState(paramBundle);
    this.mAdapter.saveInstanceState(paramBundle);
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
        if (((true ^ TextUtils.isEmpty(httpBean.getData().toString())) & bool) != 0) {
          JSONObject jSONObject = new JSONObject();
          this(httpBean.getData().toString());
          if (paramString.equals(HttpManger.KEY_GETBLACK) && !jSONObject.isNull("countList")) {
            List list1 = JSON.parseArray(jSONObject.getString("blackList"), ServerAppInfoBean.class);
            List list2 = JSON.parseArray(jSONObject.getString("countList"), ServerAppInfoBean.class);
            List<AppInfo> list = this.infos;
            Iterator<AppInfo> iterator = list.iterator();
            while (iterator.hasNext()) {
              AppInfo appInfo = iterator.next();
              for (ServerAppInfoBean serverAppInfoBean : list1) {
                if (appInfo.packageName.equals(serverAppInfoBean.getPacket()) && serverAppInfoBean.getBlack().equals("1"))
                  iterator.remove(); 
              } 
              if (appInfo.packageName.contains("com.huihu.mult"))
                iterator.remove(); 
              if (appInfo.packageName.contains("com.sheep.mult"))
                iterator.remove(); 
            } 
            for (AppInfo appInfo : list) {
              for (ServerAppInfoBean serverAppInfoBean : list2) {
                if (appInfo.packageName.equals(serverAppInfoBean.getPacket()))
                  appInfo.setAppCloneCount(Integer.valueOf(serverAppInfoBean.getViewcount())); 
              } 
            } 
            myComprator myComprator = new myComprator();
            this(this);
            Collections.sort(list, myComprator);
            refreshList(list);
          } 
          if (paramString.equals(HttpManger.KEY_VIP_LIST) && !jSONObject.isNull("list"))
            this.vipInfos = JSON.parseArray(jSONObject.getString("list"), VIPGoodsBean.class); 
        } 
        if (paramString.equals(HttpManger.KEY_DATA_URL)) {
          JSONObject jSONObject = new JSONObject();
          this(httpBean.getData().toString());
          if (!jSONObject.isNull("dataurl"))
            this.newDataurl = jSONObject.getString("dataurl"); 
        } 
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {
    this.mGridView = (GridView)paramView.findViewById(2131296494);
    MyGridViewApater myGridViewApater = new MyGridViewApater((Context)getActivity());
    this.mGridViewApater = myGridViewApater;
    this.mGridView.setAdapter((ListAdapter)myGridViewApater);
    this.mRecyclerView = (DragSelectRecyclerView)paramView.findViewById(2131296668);
    this.mProgressBar = (ProgressBar)paramView.findViewById(2131296667);
    MyGridLayoutManager myGridLayoutManager = new MyGridLayoutManager((Context)getActivity(), 3);
    myGridLayoutManager.setScrollEnabled(false);
    this.mRecyclerView.setLayoutManager((RecyclerView.LayoutManager)myGridLayoutManager);
    CloneAppListAdapter cloneAppListAdapter = new CloneAppListAdapter((Context)getActivity());
    this.mAdapter = cloneAppListAdapter;
    this.mRecyclerView.setAdapter((DragSelectRecyclerViewAdapter)cloneAppListAdapter);
    if (!TextUtils.isEmpty(SPUtils.get((Context)App.getApp(), "token")))
      this.mToken = SPUtils.get((Context)App.getApp(), "token"); 
    paramView.findViewById(2131296423).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse("http://www.vxiaoya.com/kf/kefu.html"));
            ListAppFragment.this.startActivity(intent);
          }
        });
    this.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
            AppInfo appInfo = ListAppFragment.this.grideInfos.get(param1Int);
            ListAppFragment.access$102(ListAppFragment.this, appInfo);
            if (Build.VERSION.SDK_INT >= 23 && VirtualCore.get().getTargetSdkVersion() >= 23) {
              if (ActivityCompat.checkSelfPermission((Context)ListAppFragment.this.getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                ListAppFragment.this.requestPermissions(new String[] { "android.permission.WRITE_EXTERNAL_STORAGE" }, 0);
              } else {
                ListAppFragment.this.checkDownload();
              } 
            } else {
              ListAppFragment.this.checkDownload();
            } 
          }
        });
    this.mAdapter.setOnItemClickListener(new CloneAppListAdapter.ItemEventListener() {
          public boolean isSelectable(int param1Int) {
            return (ListAppFragment.this.mAdapter.isIndexSelected(param1Int) || ListAppFragment.this.mAdapter.getSelectedCount() < 9);
          }
          
          public void onItemClick(AppInfo param1AppInfo, int param1Int) {
            ListAppFragment.access$102(ListAppFragment.this, param1AppInfo);
            if (Build.VERSION.SDK_INT >= 23 && VirtualCore.get().getTargetSdkVersion() >= 23) {
              if (ActivityCompat.checkSelfPermission((Context)ListAppFragment.this.getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                ListAppFragment.this.requestPermissions(new String[] { "android.permission.WRITE_EXTERNAL_STORAGE" }, 0);
              } else {
                ListAppFragment.this.checkDownload();
              } 
            } else {
              ListAppFragment.this.checkDownload();
            } 
            ListAppFragment.this.mAdapter.toggleSelected(param1Int);
          }
        });
    this.formAM = getArguments().getBoolean("formAppManager", false);
    (new ListAppPresenterImpl((Activity)getActivity(), this, getSelectFrom())).start();
    this.mDialog = new WhiteLoadingDialog((Context)getActivity());
    (new HttpManger(this)).getDataUrl();
  }
  
  public void setPresenter(ListAppContract.ListAppPresenter paramListAppPresenter) {
    this.mPresenter = paramListAppPresenter;
  }
  
  public void startLoading() {
    this.mProgressBar.setVisibility(0);
    this.mRecyclerView.setVisibility(8);
  }
  
  private class MyGridViewApater extends BaseAdapter {
    private Context mContext;
    
    private List<AppInfo> mDatas;
    
    public MyGridViewApater(Context param1Context) {
      this.mContext = param1Context;
      this.mDatas = new ArrayList<AppInfo>();
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
      ListAppFragment.ViewHolder viewHolder;
      TextView textView;
      if (param1View == null) {
        viewHolder = new ListAppFragment.ViewHolder();
        param1View = LayoutInflater.from(this.mContext).inflate(2131427446, null);
        ListAppFragment.ViewHolder.access$502(viewHolder, (ImageView)param1View.findViewById(2131296536));
        ListAppFragment.ViewHolder.access$602(viewHolder, (TextView)param1View.findViewById(2131296537));
        ListAppFragment.ViewHolder.access$702(viewHolder, (LabelView)param1View.findViewById(2131296535));
        ListAppFragment.ViewHolder.access$802(viewHolder, (ImageView)param1View.findViewById(2131296688));
        ListAppFragment.ViewHolder.access$902(viewHolder, (TextView)param1View.findViewById(2131296538));
        param1View.setTag(viewHolder);
      } else {
        viewHolder = (ListAppFragment.ViewHolder)param1View.getTag();
      } 
      AppInfo appInfo = this.mDatas.get(param1Int);
      viewHolder.iconView.setImageDrawable(appInfo.icon);
      viewHolder.nameView.setText(appInfo.name);
      if (appInfo.cloneCount > 0) {
        viewHolder.labelView.setVisibility(0);
        LabelView labelView = viewHolder.labelView;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(appInfo.cloneCount + 1);
        stringBuilder.append("");
        labelView.setText(stringBuilder.toString());
      } else {
        viewHolder.labelView.setVisibility(4);
      } 
      if (appInfo.getAppCloneCount().intValue() > 0) {
        textView = viewHolder.countView;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("分身次数");
        stringBuilder.append(appInfo.getAppCloneCount());
        stringBuilder.append("");
        textView.setText(stringBuilder.toString());
      } else {
        ((ListAppFragment.ViewHolder)textView).countView.setText("");
      } 
      return param1View;
    }
    
    public void setData(List<AppInfo> param1List) {
      this.mDatas = new ArrayList<AppInfo>();
      this.mDatas = param1List;
      notifyDataSetChanged();
    }
  }
  
  private class ViewHolder {
    private TextView countView;
    
    private ImageView iconView;
    
    private LabelView labelView;
    
    private ImageView makeView;
    
    private TextView nameView;
    
    private ViewHolder() {}
  }
  
  public class myComprator implements Comparator<AppInfo> {
    public int compare(AppInfo param1AppInfo1, AppInfo param1AppInfo2) {
      double d = Integer.valueOf(param1AppInfo1.getAppCloneCount().intValue()).intValue();
      return (int)(Integer.valueOf(param1AppInfo2.getAppCloneCount().intValue()).intValue() - d);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\ListAppFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */