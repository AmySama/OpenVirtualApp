package io.virtualapp.integralCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.stub.StubApp;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.bean.IntegralInfoBean;
import io.virtualapp.bean.MessageEvent;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class MyIntegralActivity extends VActivity implements View.OnClickListener, HttpCall {
  private LinearLayout backLayout;
  
  private TextView intergalView;
  
  private ListView listView;
  
  private MyAdapter mAdapter;
  
  private List<IntegralInfoBean> mIntegralInfos;
  
  private TextView titleView;
  
  static {
    StubApp.interface11(9815);
  }
  
  private void bindViews() {
    TextView textView = (TextView)findViewById(2131296570);
    this.titleView = textView;
    textView.setText("我的账号");
    this.backLayout = (LinearLayout)findViewById(2131296568);
    this.intergalView = (TextView)findViewById(2131296533);
    this.backLayout.setOnClickListener(this);
    this.listView = (ListView)findViewById(2131296575);
    MyAdapter myAdapter = new MyAdapter((Context)this);
    this.mAdapter = myAdapter;
    this.listView.setAdapter((ListAdapter)myAdapter);
    this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
            IntegralInfoBean integralInfoBean = MyIntegralActivity.this.mIntegralInfos.get(param1Int);
            if (integralInfoBean != null && !TextUtils.isEmpty(integralInfoBean.getMoney())) {
              Intent intent = new Intent();
              intent.setClass((Context)MyIntegralActivity.this, MyIntegralRechargeActivity.class);
              intent.putExtra("intergal_info_bean", (Serializable)integralInfoBean);
              MyIntegralActivity.this.startActivity(intent);
            } 
          }
        });
    if (TextUtils.isEmpty(SPUtils.get((Context)this, "my_intergal"))) {
      this.intergalView.setText("0");
    } else {
      this.intergalView.setText(SPUtils.get((Context)this, "my_intergal"));
    } 
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == 2131296568)
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
    paramMessageEvent.getType();
  }
  
  public void onSuccess(String paramString, JSONObject paramJSONObject) {
    HttpBean httpBean = (HttpBean)JSON.parseObject(paramJSONObject.toString(), HttpBean.class);
    if (httpBean != null)
      try {
        List<IntegralInfoBean> list;
        JSONObject jSONObject = new JSONObject();
        this(httpBean.getData().toString());
        if (paramString.equals(HttpManger.KEY_FEELIST)) {
          if (!jSONObject.isNull("list")) {
            list = JSON.parseArray(jSONObject.getString("list"), IntegralInfoBean.class);
            this.mIntegralInfos = list;
            if (list != null && list.size() > 0) {
              this.mAdapter.setData(this.mIntegralInfos);
            } else {
              MyAdapter myAdapter = this.mAdapter;
              list = new ArrayList<IntegralInfoBean>();
              super();
              myAdapter.setData(list);
            } 
          } 
        } else if (list.equals(HttpManger.KEY_USER_INFO) && !jSONObject.isNull("integral")) {
          this.intergalView.setText(jSONObject.getString("integral"));
          SPUtils.put((Context)this, "my_intergal", jSONObject.getString("integral"));
          EventBus eventBus = EventBus.getDefault();
          MessageEvent messageEvent = new MessageEvent();
          this(3);
          eventBus.post(messageEvent);
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
    
    public View getView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
      MyIntegralActivity.ViewHolder viewHolder1;
      MyIntegralActivity.ViewHolder viewHolder2;
      if (param1View == null) {
        viewHolder1 = new MyIntegralActivity.ViewHolder();
        View view = LayoutInflater.from(this.mContext).inflate(2131427455, null);
        viewHolder1.amonutView = (TextView)view.findViewById(2131296311);
        viewHolder1.priceView = (TextView)view.findViewById(2131296622);
        view.setTag(viewHolder1);
      } else {
        MyIntegralActivity.ViewHolder viewHolder = (MyIntegralActivity.ViewHolder)viewHolder1.getTag();
        viewHolder2 = viewHolder1;
        viewHolder1 = viewHolder;
      } 
      TextView textView = viewHolder1.amonutView;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(((IntegralInfoBean)this.mDatas.get(param1Int)).getIntegral());
      stringBuilder.append("积分");
      textView.setText(stringBuilder.toString());
      viewHolder1.priceView.setText(this.mContext.getResources().getString(2131623937, new Object[] { ((IntegralInfoBean)this.mDatas.get(param1Int)).getMoney() }));
      return (View)viewHolder2;
    }
    
    public void setData(List<IntegralInfoBean> param1List) {
      this.mDatas = param1List;
      notifyDataSetChanged();
    }
  }
  
  class ViewHolder {
    TextView amonutView;
    
    TextView priceView;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\integralCenter\MyIntegralActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */