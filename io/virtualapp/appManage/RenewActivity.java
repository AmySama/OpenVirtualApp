package io.virtualapp.appManage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.remote.InstalledAppInfo;
import com.stub.StubApp;
import io.virtualapp.App;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.Utils.ToastUtil;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.bean.IntegralInfoBean;
import io.virtualapp.bean.MessageEvent;
import io.virtualapp.home.models.AppInfoBean;
import io.virtualapp.home.models.PackageAppData;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import io.virtualapp.integralCenter.PersonalCenterActivity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class RenewActivity extends VActivity implements View.OnClickListener, HttpCall {
  private TextView activate;
  
  private AppInfoBean appBean;
  
  private ImageView appIcon;
  
  private TextView appName;
  
  private TextView appTitle;
  
  private LinearLayout backLayout;
  
  private LinearLayout dynamicLayout;
  
  private TextView integral;
  
  private MyAdapter mAdapter;
  
  private IntegralInfoBean mInfoBean;
  
  private List<IntegralInfoBean> mIntegralInfos;
  
  private Float mIntergal;
  
  private TextView recharge;
  
  private TextView timeView;
  
  private TextView titleView;
  
  static {
    StubApp.interface11(9567);
  }
  
  private void addItemView(View paramView, final IntegralInfoBean bean) {
    if (bean == null)
      return; 
    LinearLayout linearLayout = (LinearLayout)paramView.findViewById(2131296544);
    final CheckBox checkBox = (CheckBox)paramView.findViewById(2131296405);
    TextView textView2 = (TextView)paramView.findViewById(2131296311);
    TextView textView1 = (TextView)paramView.findViewById(2131296622);
    if (bean.isSelect()) {
      checkBox.setChecked(true);
    } else {
      checkBox.setChecked(false);
    } 
    if (!TextUtils.isEmpty(bean.getBiztext()))
      textView2.setText(bean.getBiztext()); 
    if (TextUtils.isEmpty(bean.getIntegral()))
      bean.setIntegral("0"); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Math.abs(Integer.valueOf(bean.getIntegral()).intValue()));
    stringBuilder.append("积分");
    textView1.setText(stringBuilder.toString());
    linearLayout.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            for (byte b = 0; b < RenewActivity.this.dynamicLayout.getChildCount(); b++) {
              if (RenewActivity.this.dynamicLayout.getChildAt(b) instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout)RenewActivity.this.dynamicLayout.getChildAt(b);
                for (byte b1 = 0; b1 < linearLayout.getChildCount(); b1++) {
                  if (linearLayout.getChildAt(b1) instanceof LinearLayout) {
                    LinearLayout linearLayout1 = (LinearLayout)linearLayout.getChildAt(b1);
                    for (byte b2 = 0; b2 < linearLayout1.getChildCount(); b2++) {
                      if (linearLayout1.getChildAt(b2) instanceof CheckBox) {
                        ((CheckBox)linearLayout1.getChildAt(b2)).setChecked(false);
                        break;
                      } 
                    } 
                  } 
                  if (linearLayout.getChildAt(b1) instanceof CheckBox) {
                    ((CheckBox)linearLayout.getChildAt(b1)).setChecked(false);
                    break;
                  } 
                } 
              } 
            } 
            checkBox.setChecked(true);
            bean.setSelect(true);
            RenewActivity.access$102(RenewActivity.this, bean);
          }
        });
  }
  
  private void addView(List<IntegralInfoBean> paramList) {
    if (paramList == null)
      return; 
    for (byte b = 0; b < paramList.size(); b++) {
      if (paramList.get(b) != null) {
        if (b == 0) {
          ((IntegralInfoBean)paramList.get(b)).setSelect(true);
          this.mInfoBean = paramList.get(b);
        } 
        addItemView(ViewGroupUtil.addItemView((ViewGroup)this.dynamicLayout), paramList.get(b));
      } 
    } 
  }
  
  private void bindViews() {
    this.backLayout = (LinearLayout)findViewById(2131296568);
    this.titleView = (TextView)findViewById(2131296401);
    this.appIcon = (ImageView)findViewById(2131296312);
    this.appName = (TextView)findViewById(2131296318);
    this.appTitle = (TextView)findViewById(2131296721);
    this.timeView = (TextView)findViewById(2131296767);
    this.dynamicLayout = (LinearLayout)findViewById(2131296448);
    this.integral = (TextView)findViewById(2131296532);
    this.recharge = (TextView)findViewById(2131296631);
    this.activate = (TextView)findViewById(2131296296);
    this.backLayout.setOnClickListener(this);
    this.recharge.setOnClickListener(this);
    this.activate.setOnClickListener(this);
  }
  
  private void commitDialog(final IntegralInfoBean bean) {
    final Dialog dialog = new Dialog((Context)this, 2131689644);
    dialog.setCanceledOnTouchOutside(true);
    dialog.setCancelable(true);
    dialog.setContentView(2131427428);
    TextView textView1 = (TextView)dialog.findViewById(2131296721);
    TextView textView2 = (TextView)dialog.findViewById(2131296425);
    TextView textView3 = (TextView)dialog.findViewById(2131296403);
    TextView textView4 = (TextView)dialog.findViewById(2131296596);
    textView1.setText("确定续期");
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("您确定消耗");
    stringBuilder.append(Math.abs(Integer.valueOf(this.mInfoBean.getIntegral()).intValue()));
    stringBuilder.append("积分，");
    stringBuilder.append(bean.getBiztext());
    textView2.setText(stringBuilder.toString());
    textView3.setText(2131624041);
    textView4.setText(2131623983);
    textView3.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            (new HttpManger(RenewActivity.this)).renewUserAccredit(bean.getBizcode());
            dialog.dismiss();
          }
        });
    textView4.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            dialog.dismiss();
          }
        });
    dialog.show();
  }
  
  private void initData() {
    this.titleView.setText("账号续费");
    AppInfoBean appInfoBean = (AppInfoBean)getIntent().getSerializableExtra("appBean");
    this.appBean = appInfoBean;
    if (appInfoBean != null) {
      this.appName.setText(appInfoBean.getName());
      TextView textView = this.appTitle;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.appBean.getName());
      stringBuilder.append("_账号激活");
      textView.setText(stringBuilder.toString());
      InstalledAppInfo installedAppInfo = VirtualCore.get().getInstalledAppInfo(this.appBean.getPackageName(), 0);
      PackageAppData packageAppData = new PackageAppData(getBaseContext(), installedAppInfo);
      this.appIcon.setImageDrawable(packageAppData.getIcon());
    } 
    if (TextUtils.isEmpty(SPUtils.get((Context)this, "my_intergal"))) {
      this.integral.setText("剩余0积分");
    } else {
      TextView textView = this.integral;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("剩余");
      stringBuilder.append(SPUtils.get((Context)this, "my_intergal"));
      stringBuilder.append("积分");
      textView.setText(stringBuilder.toString());
      this.mIntergal = Float.valueOf(SPUtils.get((Context)this, "my_intergal"));
    } 
    if (TextUtils.isEmpty(SPUtils.get((Context)this, "my_expried_time")) || Long.valueOf(SPUtils.get((Context)this, "my_expried_time")).longValue() < 0L) {
      this.timeView.setTextColor(getResources().getColor(2131099781));
      this.timeView.setText("已过期");
      return;
    } 
    long l1 = Long.valueOf(SPUtils.get((Context)this, "my_expried_time")).longValue();
    long l2 = l1 / 86400L;
    long l3 = l1 / 60L;
    long l4 = l3 / 60L;
    l1 = l3;
    long l5 = l4;
    if (l3 > 60L) {
      l3 %= 60L;
      l1 = l3;
      l5 = l4;
      if (l4 > 24L) {
        l5 = l4 % 24L;
        l1 = l3;
      } 
    } 
    if (l2 > 10L) {
      TextView textView = this.timeView;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("有效期");
      stringBuilder.append(l2);
      stringBuilder.append("天");
      textView.setText(stringBuilder.toString());
    } else {
      this.timeView.setTextColor(getResources().getColor(2131099781));
      if (l2 > 1L) {
        TextView textView = this.timeView;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("有效期");
        stringBuilder.append(l2);
        stringBuilder.append("天");
        textView.setText(stringBuilder.toString());
      } else {
        TextView textView = this.timeView;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("有效期");
        stringBuilder.append(l5);
        stringBuilder.append("时");
        stringBuilder.append(l1);
        stringBuilder.append("分");
        textView.setText(stringBuilder.toString());
      } 
    } 
  }
  
  private void qureyRenewalList() {
    (new HttpManger(this)).getRenewBizList();
  }
  
  private void rechargeDialog() {
    final Dialog dialog = new Dialog((Context)this, 2131689644);
    dialog.setCanceledOnTouchOutside(true);
    dialog.setCancelable(true);
    dialog.setContentView(2131427428);
    TextView textView1 = (TextView)dialog.findViewById(2131296721);
    TextView textView2 = (TextView)dialog.findViewById(2131296425);
    TextView textView3 = (TextView)dialog.findViewById(2131296403);
    TextView textView4 = (TextView)dialog.findViewById(2131296596);
    textView1.setText("请充值");
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("您的积分不足");
    stringBuilder.append(Math.abs(Integer.valueOf(this.mInfoBean.getIntegral()).intValue()));
    stringBuilder.append("，是否购买积分？");
    textView2.setText(stringBuilder.toString());
    textView3.setText("立即购买");
    textView4.setText("取消");
    textView3.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            RenewActivity.this.startActivity(new Intent((Context)RenewActivity.this, PersonalCenterActivity.class));
            RenewActivity.this.finish();
            dialog.dismiss();
          }
        });
    textView4.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            dialog.dismiss();
          }
        });
    dialog.show();
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == 2131296568) {
      finish();
    } else if (paramView.getId() == 2131296631) {
      startActivity(new Intent((Context)this, PersonalCenterActivity.class));
    } else if (paramView.getId() == 2131296296) {
      IntegralInfoBean integralInfoBean = this.mInfoBean;
      if (integralInfoBean != null) {
        if (TextUtils.isEmpty(integralInfoBean.getIntegral()))
          this.mInfoBean.setIntegral("0"); 
        if (this.mIntergal.floatValue() >= Math.abs(Float.valueOf(this.mInfoBean.getIntegral()).floatValue())) {
          commitDialog(this.mInfoBean);
        } else {
          rechargeDialog();
        } 
      } 
    } 
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  public void onError() {}
  
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMesageEvent(MessageEvent paramMessageEvent) {
    if (paramMessageEvent.getType() == 2)
      if (TextUtils.isEmpty(SPUtils.get((Context)this, "my_intergal"))) {
        this.integral.setText("剩余0积分");
      } else {
        TextView textView = this.integral;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("剩余");
        stringBuilder.append(SPUtils.get((Context)this, "my_intergal"));
        stringBuilder.append("积分");
        textView.setText(stringBuilder.toString());
        this.mIntergal = Float.valueOf(SPUtils.get((Context)this, "my_intergal"));
      }  
  }
  
  public void onSuccess(String paramString, JSONObject paramJSONObject) {
    HttpBean httpBean = (HttpBean)JSON.parseObject(paramJSONObject.toString(), HttpBean.class);
    if (httpBean != null)
      try {
        List<IntegralInfoBean> list;
        JSONObject jSONObject = new JSONObject();
        this(httpBean.getData().toString());
        if (paramString.equals(HttpManger.KEY_RENEWBIZLIST)) {
          if (!jSONObject.isNull("list")) {
            list = JSON.parseArray(jSONObject.getString("list"), IntegralInfoBean.class);
            this.mIntegralInfos = list;
            if (list != null && list.size() > 0) {
              addView(this.mIntegralInfos);
            } else {
              list = new ArrayList<IntegralInfoBean>();
              super();
              addView(list);
            } 
          } 
        } else if (list.equals(HttpManger.KEY_RENEWUSERACCREDIT)) {
          MessageEvent messageEvent;
          if (TextUtils.equals("1", httpBean.getStatus())) {
            EventBus eventBus = EventBus.getDefault();
            MessageEvent messageEvent1 = new MessageEvent();
            this(1);
            eventBus.post(messageEvent1);
            ToastUtil.showToast(httpBean.getInfo());
            if (!jSONObject.isNull("difference")) {
              SPUtils.put((Context)App.getApp(), "my_expried_time", jSONObject.getString("difference"));
              SPUtils.put((Context)this, "my_alwaysvip", jSONObject.getString("alwaysvip"));
            } 
            eventBus = EventBus.getDefault();
            messageEvent = new MessageEvent();
            this(2);
            eventBus.post(messageEvent);
            Intent intent = new Intent();
            this();
            intent.setClass((Context)this, PersonalCenterActivity.class);
            startActivity(intent);
            finish();
          } else {
            ToastUtil.showToast(messageEvent.getInfo());
          } 
        } 
      } catch (JSONException jSONException) {
        jSONException.printStackTrace();
      }  
  }
  
  private class MyAdapter extends BaseAdapter {
    private Context mContext;
    
    private List<IntegralInfoBean> mDatas;
    
    public MyAdapter(Context param1Context) {
      this.mContext = param1Context;
      this.mDatas = new ArrayList<IntegralInfoBean>();
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
    
    public View getView(final int position, View param1View, ViewGroup param1ViewGroup) {
      RenewActivity.ViewHolder viewHolder1;
      RenewActivity.ViewHolder viewHolder2;
      if (param1View == null) {
        viewHolder1 = new RenewActivity.ViewHolder();
        View view = LayoutInflater.from(this.mContext).inflate(2131427457, null);
        viewHolder1.itemLayout = (LinearLayout)view.findViewById(2131296544);
        viewHolder1.checkBox = (CheckBox)view.findViewById(2131296405);
        viewHolder1.amonutView = (TextView)view.findViewById(2131296311);
        viewHolder1.priceView = (TextView)view.findViewById(2131296622);
        view.setTag(viewHolder1);
      } else {
        RenewActivity.ViewHolder viewHolder = (RenewActivity.ViewHolder)viewHolder1.getTag();
        viewHolder2 = viewHolder1;
        viewHolder1 = viewHolder;
      } 
      IntegralInfoBean integralInfoBean2 = this.mDatas.get(position);
      IntegralInfoBean integralInfoBean1 = integralInfoBean2;
      if (integralInfoBean2 == null)
        integralInfoBean1 = new IntegralInfoBean(); 
      if (integralInfoBean1.isSelect()) {
        viewHolder1.checkBox.setChecked(true);
      } else {
        viewHolder1.checkBox.setChecked(false);
      } 
      if (!TextUtils.isEmpty(integralInfoBean1.getBiztext()))
        viewHolder1.amonutView.setText(integralInfoBean1.getBiztext().trim()); 
      if (TextUtils.isEmpty(integralInfoBean1.getIntegral()))
        integralInfoBean1.setIntegral("0"); 
      TextView textView = viewHolder1.priceView;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Math.abs(Float.valueOf(integralInfoBean1.getIntegral()).floatValue()));
      stringBuilder.append("积分");
      textView.setText(stringBuilder.toString());
      viewHolder1.itemLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param2View) {
              RenewActivity.access$102(RenewActivity.this, RenewActivity.MyAdapter.this.mDatas.get(position));
              Iterator<IntegralInfoBean> iterator = RenewActivity.MyAdapter.this.mDatas.iterator();
              while (iterator.hasNext())
                ((IntegralInfoBean)iterator.next()).setSelect(false); 
              ((IntegralInfoBean)RenewActivity.MyAdapter.this.mDatas.get(position)).setSelect(true);
              RenewActivity.MyAdapter.this.notifyDataSetChanged();
            }
          });
      return (View)viewHolder2;
    }
    
    public void setData(List<IntegralInfoBean> param1List) {
      this.mDatas = param1List;
      notifyDataSetChanged();
    }
  }
  
  class null implements View.OnClickListener {
    public void onClick(View param1View) {
      RenewActivity.access$102(RenewActivity.this, this.this$1.mDatas.get(position));
      Iterator<IntegralInfoBean> iterator = this.this$1.mDatas.iterator();
      while (iterator.hasNext())
        ((IntegralInfoBean)iterator.next()).setSelect(false); 
      ((IntegralInfoBean)this.this$1.mDatas.get(position)).setSelect(true);
      this.this$1.notifyDataSetChanged();
    }
  }
  
  private static class ViewGroupUtil {
    public static View addItemView(ViewGroup param1ViewGroup) {
      View view = LayoutInflater.from(param1ViewGroup.getContext()).inflate(2131427457, null);
      param1ViewGroup.addView(view);
      return view;
    }
  }
  
  class ViewHolder {
    TextView amonutView;
    
    CheckBox checkBox;
    
    LinearLayout itemLayout;
    
    TextView priceView;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\appManage\RenewActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */