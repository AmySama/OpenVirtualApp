package io.virtualapp.integralCenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.stub.StubApp;
import io.virtualapp.App;
import io.virtualapp.Utils.AliPayUtil;
import io.virtualapp.Utils.NumberUtils;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.bean.MessageEvent;
import io.virtualapp.bean.VIPGoodsBean;
import io.virtualapp.dialog.SelectPayTypeDialog;
import io.virtualapp.home.AboutUsActivity;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import io.virtualapp.webview.WebViewActivity;
import io.virtualapp.widgets.MyListView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class PersonalCenterActivity extends VActivity implements View.OnClickListener, HttpCall {
  private MyAdapter mAdapter;
  
  private MyAdapterSvip mAdapterSvip;
  
  private String mToken;
  
  private VIPGoodsBean mVip;
  
  private int payMode = 1;
  
  private TextView payPrice;
  
  private List<VIPGoodsBean> svipInfos;
  
  private LinearLayout svipLayout;
  
  private MyListView svipList;
  
  private TextView svipTitle;
  
  private TextView timeView;
  
  private List<VIPGoodsBean> vipInfos;
  
  private LinearLayout vipLayout;
  
  private MyListView vipList;
  
  private TextView vipOpen;
  
  private TextView vipText;
  
  private TextView vipTitle;
  
  static {
    StubApp.interface11(9823);
  }
  
  private void pay() {
    if (this.mVip != null) {
      if (TextUtils.isEmpty(SPUtils.get((Context)App.getApp(), "my_mobile"))) {
        startActivity(new Intent((Context)this, AuthLoginActivity.class));
        return;
      } 
      String str1 = this.mVip.getMoney();
      String str2 = this.mVip.getBizcode();
      float f = Float.valueOf(str1).floatValue();
      (new AliPayUtil((Activity)this)).requestOrder(this.mToken, NumberUtils.format5(f), str2);
    } 
  }
  
  private void refreshDays() {
    String str1 = SPUtils.get((Context)App.getApp(), "my_expried_time");
    String str2 = SPUtils.get((Context)App.getApp(), "my_alwaysvip");
    String str3 = str1;
    if (TextUtils.isEmpty(str1))
      str3 = "0"; 
    if (Long.valueOf(str3).longValue() <= 0L) {
      this.timeView.setText("开通VIP，无限制添加");
      (new HttpManger(this)).getSvipList();
    } else {
      str1 = SPUtils.get((Context)this, "my_expried_time_str");
      TextView textView = this.timeView;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str1);
      stringBuilder.append("到期");
      textView.setText(stringBuilder.toString());
      if (SPUtils.get((Context)this, "is_svip").equals("1")) {
        (new HttpManger(this)).getSvipList();
      } else {
        (new HttpManger(this)).getVipupgradeList();
      } 
    } 
    if (!TextUtils.isEmpty(str2) && str2.equals("1"))
      this.timeView.setText("永久会员"); 
    str3 = SPUtils.get((Context)App.getApp(), "my_mobile");
    if (TextUtils.isEmpty(str3)) {
      this.vipText.setText("登陆/注册");
      this.vipText.setEnabled(true);
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str3.substring(0, 3));
      stringBuilder.append("****");
      stringBuilder.append(str3.substring(7, str3.length()));
      str3 = stringBuilder.toString();
      this.vipText.setText(str3);
      this.vipText.setEnabled(false);
    } 
  }
  
  private void showPayDialog(String paramString1, String paramString2, String paramString3) {
    (new SelectPayTypeDialog((Context)this, 2131689644, SPUtils.get((Context)this, "token"), paramString1, paramString2, paramString3)).show();
  }
  
  protected void initView() {
    findViewById(2131296568).setOnClickListener(this);
    findViewById(2131296808).setOnClickListener(this);
    findViewById(2131296636).setOnClickListener(this);
    this.vipText = (TextView)findViewById(2131296613);
    this.timeView = (TextView)findViewById(2131296718);
    this.payPrice = (TextView)findViewById(2131296611);
    TextView textView = (TextView)findViewById(2131296787);
    this.vipOpen = textView;
    textView.setOnClickListener(this);
    this.vipList = (MyListView)findViewById(2131296575);
    MyAdapter myAdapter = new MyAdapter((Context)this);
    this.mAdapter = myAdapter;
    this.vipList.setAdapter((ListAdapter)myAdapter);
    this.svipList = (MyListView)findViewById(2131296698);
    MyAdapterSvip myAdapterSvip = new MyAdapterSvip((Context)this);
    this.mAdapterSvip = myAdapterSvip;
    this.svipList.setAdapter((ListAdapter)myAdapterSvip);
    this.vipTitle = (TextView)findViewById(2131296794);
    this.svipTitle = (TextView)findViewById(2131296699);
    this.vipTitle.setOnClickListener(this);
    this.svipTitle.setOnClickListener(this);
    this.vipText.setOnClickListener(this);
    this.vipLayout = (LinearLayout)findViewById(2131296784);
    this.svipLayout = (LinearLayout)findViewById(2131296697);
  }
  
  public void onClick(View paramView) {
    List<VIPGoodsBean> list;
    switch (paramView.getId()) {
      default:
        return;
      case 2131296808:
        startActivity((new Intent((Context)this, WebViewActivity.class)).putExtra("weburl", "https://xiaoyintech.com/user_service_info.html").putExtra("title", getResources().getString(2131624054)));
      case 2131296794:
        list = this.vipInfos;
        if (list != null && list.size() > 0) {
          list = this.svipInfos;
          if (list != null && list.size() > 0) {
            if (this.payMode == 1) {
              Iterator<VIPGoodsBean> iterator = this.svipInfos.iterator();
              while (iterator.hasNext())
                ((VIPGoodsBean)iterator.next()).setSelect(false); 
              iterator = this.vipInfos.iterator();
              while (iterator.hasNext())
                ((VIPGoodsBean)iterator.next()).setSelect(false); 
              this.payMode = 0;
              ((VIPGoodsBean)this.vipInfos.get(0)).setSelect(true);
              TextView textView = this.payPrice;
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("￥");
              stringBuilder.append(((VIPGoodsBean)this.vipInfos.get(0)).getMoney());
              textView.setText(stringBuilder.toString());
              this.mVip = this.vipInfos.get(0);
              this.mAdapter.notifyDataSetChanged();
              this.vipTitle.setTextColor(getResources().getColor(2131099881));
              this.svipTitle.setTextColor(getResources().getColor(2131099849));
              this.vipTitle.setTypeface(Typeface.defaultFromStyle(1));
              this.svipTitle.setTypeface(Typeface.defaultFromStyle(0));
            } 
            this.svipList.setVisibility(8);
            this.svipLayout.setVisibility(8);
            this.vipList.setVisibility(0);
            this.vipLayout.setVisibility(0);
          } 
        } 
      case 2131296787:
        pay();
      case 2131296699:
        list = this.svipInfos;
        if (list != null && list.size() > 0) {
          list = this.vipInfos;
          if (list != null && list.size() > 0) {
            if (this.payMode == 0) {
              Iterator<VIPGoodsBean> iterator = this.svipInfos.iterator();
              while (iterator.hasNext())
                ((VIPGoodsBean)iterator.next()).setSelect(false); 
              iterator = this.vipInfos.iterator();
              while (iterator.hasNext())
                ((VIPGoodsBean)iterator.next()).setSelect(false); 
              this.payMode = 1;
              ((VIPGoodsBean)this.svipInfos.get(0)).setSelect(true);
              TextView textView = this.payPrice;
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("￥");
              stringBuilder.append(((VIPGoodsBean)this.svipInfos.get(0)).getMoney());
              textView.setText(stringBuilder.toString());
              this.mVip = this.svipInfos.get(0);
              this.mAdapterSvip.notifyDataSetChanged();
              this.vipTitle.setTextColor(getResources().getColor(2131099849));
              this.svipTitle.setTextColor(getResources().getColor(2131099881));
              this.svipTitle.setTypeface(Typeface.defaultFromStyle(1));
              this.vipTitle.setTypeface(Typeface.defaultFromStyle(0));
            } 
            this.svipList.setVisibility(0);
            this.svipLayout.setVisibility(0);
            this.vipList.setVisibility(8);
            this.vipLayout.setVisibility(8);
          } 
        } 
      case 2131296636:
        startActivity(new Intent((Context)this, AboutUsActivity.class));
      case 2131296613:
        if (TextUtils.isEmpty(SPUtils.get((Context)App.getApp(), "my_mobile"))) {
          startActivity(new Intent((Context)this, AuthLoginActivity.class));
          return;
        } 
      case 2131296568:
        break;
    } 
    finish();
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }
  
  public void onError() {}
  
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMesageEvent(MessageEvent paramMessageEvent) {
    if (paramMessageEvent.getType() == 2)
      (new HttpManger(this)).reportVip(); 
  }
  
  protected void onResume() {
    super.onResume();
  }
  
  public void onSuccess(String paramString, JSONObject paramJSONObject) {
    HttpBean httpBean = (HttpBean)JSON.parseObject(paramJSONObject.toString(), HttpBean.class);
    if (httpBean != null && !TextUtils.isEmpty(httpBean.getData().toString()))
      try {
        paramJSONObject = new JSONObject();
        this(httpBean.getData().toString());
        boolean bool = paramString.equals(HttpManger.KEY_VIP_LIST);
        if (bool) {
          if (!paramJSONObject.isNull("list")) {
            List<VIPGoodsBean> list = JSON.parseArray(paramJSONObject.getString("list"), VIPGoodsBean.class);
            this.vipInfos = list;
            for (VIPGoodsBean vIPGoodsBean : list)
              vIPGoodsBean.setPrice(vIPGoodsBean.getMoney()); 
            if (this.vipInfos != null)
              this.vipInfos.size(); 
            this.mAdapter.setData(this.vipInfos);
          } 
        } else {
          StringBuilder stringBuilder;
          TextView textView;
          bool = vIPGoodsBean.equals(HttpManger.KEY_SVIP_LIST);
          if (bool) {
            if (!paramJSONObject.isNull("list")) {
              List<VIPGoodsBean> list = JSON.parseArray(paramJSONObject.getString("list"), VIPGoodsBean.class);
              this.svipInfos = list;
              for (VIPGoodsBean vIPGoodsBean1 : list)
                vIPGoodsBean1.setPrice(vIPGoodsBean1.getMoney()); 
              if (this.svipInfos != null && this.svipInfos.size() > 0) {
                ((VIPGoodsBean)this.svipInfos.get(0)).setSelect(true);
                this.mVip = this.svipInfos.get(0);
                textView = this.payPrice;
                stringBuilder = new StringBuilder();
                this();
                stringBuilder.append("￥");
                stringBuilder.append(((VIPGoodsBean)this.svipInfos.get(0)).getMoney());
                textView.setText(stringBuilder.toString());
              } 
              this.mAdapterSvip.setData(this.svipInfos);
            } 
          } else {
            StringBuilder stringBuilder1;
            if (stringBuilder.equals(HttpManger.KEY_VIPUPGRADE_LIST)) {
              if (!textView.isNull("list")) {
                List<VIPGoodsBean> list = JSON.parseArray(textView.getString("list"), VIPGoodsBean.class);
                this.svipInfos = list;
                for (VIPGoodsBean vIPGoodsBean1 : list)
                  vIPGoodsBean1.setPrice(vIPGoodsBean1.getMoney()); 
                if (this.svipInfos != null && this.svipInfos.size() > 0) {
                  ((VIPGoodsBean)this.svipInfos.get(0)).setSelect(true);
                  this.mVip = this.svipInfos.get(0);
                  textView = this.payPrice;
                  stringBuilder1 = new StringBuilder();
                  this();
                  stringBuilder1.append("￥");
                  stringBuilder1.append(((VIPGoodsBean)this.svipInfos.get(0)).getMoney());
                  textView.setText(stringBuilder1.toString());
                } 
                this.mAdapterSvip.setData(this.svipInfos);
              } 
            } else if (stringBuilder1.equals(HttpManger.KEY_REPORT_VIP) && !textView.isNull("difference")) {
              SPUtils.put((Context)this, "my_expried_time", textView.getString("difference"));
              SPUtils.put((Context)this, "my_expried_time_str", textView.getString("expriedtime"));
              SPUtils.put((Context)this, "my_alwaysvip", textView.getString("alwaysvip"));
              SPUtils.put((Context)this, "my_mobile", textView.getString("mobile"));
              SPUtils.put((Context)this, "is_svip", textView.getString("svip"));
              SPUtils.put((Context)this, "package_userid", textView.getString("packageuserid"));
              refreshDays();
            } 
          } 
        } 
      } catch (JSONException jSONException) {
        jSONException.printStackTrace();
      }  
  }
  
  private class MyAdapter extends BaseAdapter {
    private Context mContext;
    
    private List<VIPGoodsBean> mDatas;
    
    public MyAdapter(Context param1Context) {
      this.mContext = param1Context;
      this.mDatas = new ArrayList<VIPGoodsBean>();
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
    
    public View getView(final int i, View param1View, ViewGroup param1ViewGroup) {
      PersonalCenterActivity.ViewHolder viewHolder1;
      PersonalCenterActivity.ViewHolder viewHolder2;
      if (param1View == null) {
        viewHolder1 = new PersonalCenterActivity.ViewHolder();
        View view = LayoutInflater.from(this.mContext).inflate(2131427458, null);
        viewHolder1.vipName = (TextView)view.findViewById(2131296786);
        viewHolder1.vipCoupon = (TextView)view.findViewById(2131296777);
        viewHolder1.vipCoupon.getPaint().setFlags(16);
        viewHolder1.vipPrice = (TextView)view.findViewById(2131296788);
        viewHolder1.vipMemo = (TextView)view.findViewById(2131296785);
        viewHolder1.vipLayout = (LinearLayout)view.findViewById(2131296783);
        viewHolder1.checkedView = (ImageView)view.findViewById(2131296774);
        view.setTag(viewHolder1);
      } else {
        PersonalCenterActivity.ViewHolder viewHolder = (PersonalCenterActivity.ViewHolder)viewHolder1.getTag();
        viewHolder2 = viewHolder1;
        viewHolder1 = viewHolder;
      } 
      VIPGoodsBean vIPGoodsBean = this.mDatas.get(i);
      viewHolder1.vipName.setText(vIPGoodsBean.getBiztext());
      viewHolder1.vipCoupon.setText(vIPGoodsBean.getRemark());
      viewHolder1.vipMemo.setText(vIPGoodsBean.getMemo());
      TextView textView = viewHolder1.vipPrice;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("￥");
      stringBuilder.append(vIPGoodsBean.getMoney());
      textView.setText(stringBuilder.toString());
      if (vIPGoodsBean.isSelect()) {
        viewHolder1.checkedView.setVisibility(0);
        viewHolder1.vipLayout.setBackgroundResource(2131230830);
        viewHolder1.vipMemo.setTextColor(-1);
        viewHolder1.vipMemo.setAlpha(0.5F);
        viewHolder1.vipName.setTextColor(-1);
        viewHolder1.vipCoupon.setTextColor(-1);
        viewHolder1.vipCoupon.setAlpha(0.5F);
        viewHolder1.vipPrice.setTextColor(-1);
      } else {
        viewHolder1.checkedView.setVisibility(4);
        viewHolder1.vipLayout.setBackgroundResource(2131230874);
        viewHolder1.vipMemo.setTextColor(this.mContext.getResources().getColor(2131099846));
        viewHolder1.vipName.setTextColor(this.mContext.getResources().getColor(2131099845));
        viewHolder1.vipCoupon.setTextColor(this.mContext.getResources().getColor(2131099846));
        viewHolder1.vipPrice.setTextColor(this.mContext.getResources().getColor(2131099845));
      } 
      viewHolder2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param2View) {
              Iterator<VIPGoodsBean> iterator = PersonalCenterActivity.MyAdapter.this.mDatas.iterator();
              while (iterator.hasNext())
                ((VIPGoodsBean)iterator.next()).setSelect(false); 
              ((VIPGoodsBean)PersonalCenterActivity.MyAdapter.this.mDatas.get(i)).setSelect(true);
              TextView textView = PersonalCenterActivity.this.payPrice;
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("￥");
              stringBuilder.append(((VIPGoodsBean)PersonalCenterActivity.MyAdapter.this.mDatas.get(i)).getMoney());
              textView.setText(stringBuilder.toString());
              PersonalCenterActivity.access$302(PersonalCenterActivity.this, PersonalCenterActivity.MyAdapter.this.mDatas.get(i));
              PersonalCenterActivity.MyAdapter.this.notifyDataSetChanged();
            }
          });
      return (View)viewHolder2;
    }
    
    public void setData(List<VIPGoodsBean> param1List) {
      this.mDatas = param1List;
      notifyDataSetChanged();
    }
  }
  
  class null implements View.OnClickListener {
    public void onClick(View param1View) {
      Iterator<VIPGoodsBean> iterator = this.this$1.mDatas.iterator();
      while (iterator.hasNext())
        ((VIPGoodsBean)iterator.next()).setSelect(false); 
      ((VIPGoodsBean)this.this$1.mDatas.get(i)).setSelect(true);
      TextView textView = PersonalCenterActivity.this.payPrice;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("￥");
      stringBuilder.append(((VIPGoodsBean)this.this$1.mDatas.get(i)).getMoney());
      textView.setText(stringBuilder.toString());
      PersonalCenterActivity.access$302(PersonalCenterActivity.this, this.this$1.mDatas.get(i));
      this.this$1.notifyDataSetChanged();
    }
  }
  
  private class MyAdapterSvip extends BaseAdapter {
    private Context mContext;
    
    private List<VIPGoodsBean> mDatas;
    
    public MyAdapterSvip(Context param1Context) {
      this.mContext = param1Context;
      this.mDatas = new ArrayList<VIPGoodsBean>();
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
    
    public View getView(final int i, View param1View, ViewGroup param1ViewGroup) {
      PersonalCenterActivity.ViewHolder viewHolder;
      TextView textView1;
      if (param1View == null) {
        viewHolder = new PersonalCenterActivity.ViewHolder();
        param1View = LayoutInflater.from(this.mContext).inflate(2131427458, null);
        viewHolder.vipName = (TextView)param1View.findViewById(2131296786);
        viewHolder.vipCoupon = (TextView)param1View.findViewById(2131296777);
        viewHolder.vipCoupon.getPaint().setFlags(16);
        viewHolder.vipPrice = (TextView)param1View.findViewById(2131296788);
        viewHolder.vipMemo = (TextView)param1View.findViewById(2131296785);
        viewHolder.vipLayout = (LinearLayout)param1View.findViewById(2131296783);
        viewHolder.checkedView = (ImageView)param1View.findViewById(2131296774);
        param1View.setTag(viewHolder);
      } else {
        viewHolder = (PersonalCenterActivity.ViewHolder)param1View.getTag();
      } 
      VIPGoodsBean vIPGoodsBean = this.mDatas.get(i);
      if (vIPGoodsBean.getType().equals("11")) {
        float f1 = Float.valueOf(vIPGoodsBean.getPrice()).floatValue();
        long l = Long.valueOf(SPUtils.get((Context)PersonalCenterActivity.this, "my_expried_time")).longValue() / 86400L + 1L;
        float f2 = (float)l * f1;
        if (l > 30L)
          f2 = 30.0F * f1 + (float)(l - 30L) * f1 / 2.0F; 
        vIPGoodsBean.setMoney(NumberUtils.format5(f2));
      } 
      viewHolder.vipName.setText(vIPGoodsBean.getBiztext());
      viewHolder.vipCoupon.setText(vIPGoodsBean.getRemark());
      viewHolder.vipMemo.setText(vIPGoodsBean.getMemo());
      TextView textView2 = viewHolder.vipPrice;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("￥");
      stringBuilder.append(vIPGoodsBean.getMoney());
      textView2.setText(stringBuilder.toString());
      if (vIPGoodsBean.isSelect()) {
        viewHolder.checkedView.setVisibility(0);
        viewHolder.vipLayout.setBackgroundResource(2131230830);
        viewHolder.vipMemo.setTextColor(-1);
        viewHolder.vipMemo.setAlpha(0.5F);
        viewHolder.vipName.setTextColor(-1);
        viewHolder.vipCoupon.setTextColor(-1);
        viewHolder.vipCoupon.setAlpha(0.5F);
        viewHolder.vipPrice.setTextColor(-1);
        textView1 = PersonalCenterActivity.this.payPrice;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("￥");
        stringBuilder1.append(vIPGoodsBean.getMoney());
        textView1.setText(stringBuilder1.toString());
      } else {
        ((PersonalCenterActivity.ViewHolder)textView1).checkedView.setVisibility(4);
        ((PersonalCenterActivity.ViewHolder)textView1).vipLayout.setBackgroundResource(2131230874);
        ((PersonalCenterActivity.ViewHolder)textView1).vipMemo.setTextColor(this.mContext.getResources().getColor(2131099846));
        ((PersonalCenterActivity.ViewHolder)textView1).vipName.setTextColor(this.mContext.getResources().getColor(2131099845));
        ((PersonalCenterActivity.ViewHolder)textView1).vipCoupon.setTextColor(this.mContext.getResources().getColor(2131099846));
        ((PersonalCenterActivity.ViewHolder)textView1).vipPrice.setTextColor(this.mContext.getResources().getColor(2131099845));
      } 
      param1View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param2View) {
              Iterator<VIPGoodsBean> iterator = PersonalCenterActivity.MyAdapterSvip.this.mDatas.iterator();
              while (iterator.hasNext())
                ((VIPGoodsBean)iterator.next()).setSelect(false); 
              ((VIPGoodsBean)PersonalCenterActivity.MyAdapterSvip.this.mDatas.get(i)).setSelect(true);
              TextView textView = PersonalCenterActivity.this.payPrice;
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("￥");
              stringBuilder.append(((VIPGoodsBean)PersonalCenterActivity.MyAdapterSvip.this.mDatas.get(i)).getMoney());
              textView.setText(stringBuilder.toString());
              PersonalCenterActivity.access$302(PersonalCenterActivity.this, PersonalCenterActivity.MyAdapterSvip.this.mDatas.get(i));
              PersonalCenterActivity.MyAdapterSvip.this.notifyDataSetChanged();
            }
          });
      return param1View;
    }
    
    public void setData(List<VIPGoodsBean> param1List) {
      this.mDatas = param1List;
      notifyDataSetChanged();
    }
  }
  
  class null implements View.OnClickListener {
    public void onClick(View param1View) {
      Iterator<VIPGoodsBean> iterator = this.this$1.mDatas.iterator();
      while (iterator.hasNext())
        ((VIPGoodsBean)iterator.next()).setSelect(false); 
      ((VIPGoodsBean)this.this$1.mDatas.get(i)).setSelect(true);
      TextView textView = PersonalCenterActivity.this.payPrice;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("￥");
      stringBuilder.append(((VIPGoodsBean)this.this$1.mDatas.get(i)).getMoney());
      textView.setText(stringBuilder.toString());
      PersonalCenterActivity.access$302(PersonalCenterActivity.this, this.this$1.mDatas.get(i));
      this.this$1.notifyDataSetChanged();
    }
  }
  
  private class ViewHolder {
    ImageView checkedView;
    
    TextView vipCoupon;
    
    LinearLayout vipLayout;
    
    TextView vipMemo;
    
    TextView vipName;
    
    TextView vipPrice;
    
    private ViewHolder() {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\integralCenter\PersonalCenterActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */