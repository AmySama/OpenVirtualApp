package io.virtualapp.integralCenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.stub.StubApp;
import io.virtualapp.Utils.AliPayUtil;
import io.virtualapp.Utils.NumberUtils;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.bean.MessageEvent;
import io.virtualapp.bean.PayTypeBean;
import io.virtualapp.bean.VIPGoodsBean;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import io.virtualapp.webview.WebViewActivity;
import io.virtualapp.widgets.MyListView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class VipActivity extends VActivity implements View.OnClickListener, HttpCall {
  RelativeLayout actionLayout;
  
  LinearLayout changeLayout;
  
  LinearLayout leftLayout;
  
  private MyAdapter mAdapter;
  
  private PayTypeBean mPayTypeBean;
  
  private String mToken;
  
  private VIPGoodsBean mVip;
  
  TextView payPrice;
  
  MyListView vipGoodsList;
  
  ImageView vipIcon;
  
  private List<VIPGoodsBean> vipInfos;
  
  LinearLayout vipLayout;
  
  TextView vipName;
  
  TextView vipOpen;
  
  TextView vipText;
  
  CheckBox xieyiCheckBox;
  
  LinearLayout xieyiLayout;
  
  LinearLayout zixun;
  
  static {
    StubApp.interface11(9851);
  }
  
  private void initData() {
    ArrayList<VIPGoodsBean> arrayList = new ArrayList();
    this.mAdapter.setData(arrayList);
  }
  
  private void initEvent() {
    this.leftLayout.setOnClickListener(this);
    this.vipOpen.setOnClickListener(this);
    this.zixun.setOnClickListener(this);
    this.xieyiLayout.setOnClickListener(this);
  }
  
  private void initView() {
    ((TextView)findViewById(2131296401)).setText("VIP会员");
    this.changeLayout = (LinearLayout)findViewById(2131296719);
    String str = SPUtils.get((Context)this, "is_show_plugin");
    if (!TextUtils.isEmpty(str))
      if (str.equals("1")) {
        this.changeLayout.setVisibility(0);
      } else {
        this.changeLayout.setVisibility(8);
      }  
    MyAdapter myAdapter = new MyAdapter((Context)this);
    this.mAdapter = myAdapter;
    this.vipGoodsList.setAdapter((ListAdapter)myAdapter);
    initData();
  }
  
  private void pay() {
    VIPGoodsBean vIPGoodsBean = this.mVip;
    if (vIPGoodsBean != null) {
      String str2 = vIPGoodsBean.getMoney();
      String str1 = this.mVip.getBizcode();
      float f = Float.valueOf(str2).floatValue();
      (new AliPayUtil((Activity)this)).requestOrder(this.mToken, NumberUtils.format5(f), str1);
    } 
  }
  
  public void onClick(View paramView) {
    Intent intent;
    switch (paramView.getId()) {
      default:
        return;
      case 2131296811:
        intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("http://www.vxiaoya.com/kf/kefu.html"));
        startActivity(intent);
      case 2131296808:
        startActivity((new Intent((Context)this, WebViewActivity.class)).putExtra("weburl", "https://xiaoyintech.com/user_service_info.html").putExtra("title", getResources().getString(2131624054)));
      case 2131296787:
        pay();
      case 2131296568:
        break;
    } 
    finish();
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  public void onError() {}
  
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMesageEvent(MessageEvent paramMessageEvent) {
    if (paramMessageEvent.getType() == 4)
      finish(); 
  }
  
  public void onSuccess(String paramString, JSONObject paramJSONObject) {
    HttpBean httpBean = (HttpBean)JSON.parseObject(paramJSONObject.toString(), HttpBean.class);
    if (httpBean != null && !TextUtils.isEmpty(httpBean.getData().toString()))
      try {
        TextView textView;
        JSONObject jSONObject = new JSONObject();
        this(httpBean.getData().toString());
        if (paramString.equals(HttpManger.KEY_VIP_LIST)) {
          if (!jSONObject.isNull("list")) {
            List<VIPGoodsBean> list = JSON.parseArray(jSONObject.getString("list"), VIPGoodsBean.class);
            this.vipInfos = list;
            if (list != null && list.size() > 0) {
              ((VIPGoodsBean)this.vipInfos.get(0)).setSelect(true);
              this.mVip = this.vipInfos.get(0);
              textView = this.payPrice;
              StringBuilder stringBuilder = new StringBuilder();
              this();
              stringBuilder.append("￥");
              stringBuilder.append(((VIPGoodsBean)this.vipInfos.get(0)).getMoney());
              textView.setText(stringBuilder.toString());
            } 
            this.mAdapter.setData(this.vipInfos);
          } 
        } else if (textView.equals(HttpManger.KEY_REPORT_VIP) && !jSONObject.isNull("difference")) {
          SPUtils.put((Context)this, "my_expried_time", jSONObject.getString("difference"));
          SPUtils.put((Context)this, "my_expried_time_str", jSONObject.getString("expriedtime"));
          SPUtils.put((Context)this, "my_alwaysvip", jSONObject.getString("alwaysvip"));
          String str4 = jSONObject.getString("expriedtime");
          String str2 = jSONObject.getString("difference");
          String str3 = jSONObject.getString("alwaysvip");
          String str1 = str2;
          if (TextUtils.isEmpty(str2))
            str1 = "0"; 
          str2 = str4;
          if (TextUtils.isEmpty(str4))
            str2 = ""; 
          long l = Long.valueOf(str1).longValue();
          if (l <= 0L) {
            this.vipName.setText("您还不是VIP");
            this.vipLayout.setBackgroundColor(Color.parseColor("#000000"));
            this.vipText.setText("仅免费使用2小时，开通会员无限分身");
            this.vipIcon.setImageDrawable(getResources().getDrawable(2131230952));
            this.actionLayout.setBackgroundColor(Color.parseColor("#000000"));
          } else {
            this.vipName.setText("您是VIP至尊会员");
            this.vipLayout.setBackgroundColor(Color.parseColor("#21a134"));
            this.actionLayout.setBackgroundColor(Color.parseColor("#21a134"));
            this.vipIcon.setImageDrawable(getResources().getDrawable(2131230953));
            long l1 = l / 86400L;
            long l2 = l / 60L;
            l1 = l / 60L / 60L;
            if (l2 > 60L) {
              l2 = l / 60L;
              if (l1 > 24L)
                l = l / 60L / 60L; 
            } 
            StringBuilder stringBuilder = new StringBuilder();
            this();
            stringBuilder.append("您的VIP会员将于<font color='#fa9d3b'>");
            stringBuilder.append(str2);
            stringBuilder.append("</font>到期");
            String str = stringBuilder.toString();
            this.vipText.setText((CharSequence)Html.fromHtml(str));
          } 
          if (!TextUtils.isEmpty(str3) && str3.equals("1")) {
            this.vipName.setText("您是VIP至尊会员");
            this.vipLayout.setBackgroundColor(Color.parseColor("#21a134"));
            this.vipIcon.setImageDrawable(getResources().getDrawable(2131230953));
            this.actionLayout.setBackgroundColor(Color.parseColor("#21a134"));
            this.vipText.setText("永久有效");
            this.vipGoodsList.setVisibility(8);
            findViewById(2131296345).setVisibility(8);
            findViewById(2131296720).setVisibility(8);
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
      VipActivity.ViewHolder viewHolder;
      if (param1View == null) {
        viewHolder = new VipActivity.ViewHolder();
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
        viewHolder = (VipActivity.ViewHolder)param1View.getTag();
      } 
      VIPGoodsBean vIPGoodsBean = this.mDatas.get(i);
      viewHolder.vipName.setText(vIPGoodsBean.getBiztext());
      viewHolder.vipCoupon.setText(vIPGoodsBean.getRemark());
      viewHolder.vipMemo.setText(vIPGoodsBean.getMemo());
      TextView textView = viewHolder.vipPrice;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("￥");
      stringBuilder.append(vIPGoodsBean.getMoney());
      textView.setText(stringBuilder.toString());
      if (vIPGoodsBean.isSelect()) {
        viewHolder.checkedView.setVisibility(0);
        viewHolder.vipLayout.setBackgroundResource(2131230873);
      } else {
        viewHolder.checkedView.setVisibility(4);
        viewHolder.vipLayout.setBackgroundResource(2131230874);
      } 
      param1View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param2View) {
              Iterator<VIPGoodsBean> iterator = VipActivity.MyAdapter.this.mDatas.iterator();
              while (iterator.hasNext())
                ((VIPGoodsBean)iterator.next()).setSelect(false); 
              ((VIPGoodsBean)VipActivity.MyAdapter.this.mDatas.get(i)).setSelect(true);
              TextView textView = VipActivity.this.payPrice;
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("￥");
              stringBuilder.append(((VIPGoodsBean)VipActivity.MyAdapter.this.mDatas.get(i)).getMoney());
              textView.setText(stringBuilder.toString());
              VipActivity.access$202(VipActivity.this, VipActivity.MyAdapter.this.mDatas.get(i));
              VipActivity.MyAdapter.this.notifyDataSetChanged();
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
      TextView textView = VipActivity.this.payPrice;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("￥");
      stringBuilder.append(((VIPGoodsBean)this.this$1.mDatas.get(i)).getMoney());
      textView.setText(stringBuilder.toString());
      VipActivity.access$202(VipActivity.this, this.this$1.mDatas.get(i));
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\integralCenter\VipActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */