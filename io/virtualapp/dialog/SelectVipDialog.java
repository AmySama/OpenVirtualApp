package io.virtualapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import io.virtualapp.Utils.ScreenUtils;
import io.virtualapp.Utils.ToastUtil;
import io.virtualapp.bean.VIPGoodsBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SelectVipDialog extends Dialog implements View.OnClickListener {
  private String accessToken;
  
  private String bizid;
  
  private TextView goPayView;
  
  private String integral;
  
  private GridView listView;
  
  private MyAdapter mAdapter;
  
  private String mContent;
  
  private Context mContext;
  
  private List<VIPGoodsBean> mIntegralInfos;
  
  private PositionLisenter mLisenter;
  
  private VIPGoodsBean mPayBean;
  
  private TextView payAmount;
  
  private String total;
  
  private TextView tvContent;
  
  public SelectVipDialog(Context paramContext) {
    super(paramContext);
    this.mContext = paramContext;
  }
  
  public SelectVipDialog(Context paramContext, int paramInt, String paramString1, List<VIPGoodsBean> paramList, String paramString2) {
    super(paramContext, paramInt);
    this.mContext = paramContext;
    this.accessToken = paramString1;
    this.mIntegralInfos = paramList;
    this.mContent = paramString2;
  }
  
  protected SelectVipDialog(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener) {
    super(paramContext, paramBoolean, paramOnCancelListener);
    this.mContext = paramContext;
  }
  
  private void initData() {}
  
  private void initView() {
    findViewById(2131296414).setOnClickListener(this);
    this.payAmount = (TextView)findViewById(2131296611);
    this.goPayView = (TextView)findViewById(2131296787);
    this.listView = (GridView)findViewById(2131296780);
    TextView textView = (TextView)findViewById(2131296425);
    this.tvContent = textView;
    textView.setText(this.mContent);
    this.goPayView.setOnClickListener(this);
    this.mAdapter = new MyAdapter(this.mContext);
    List<VIPGoodsBean> list = this.mIntegralInfos;
    if (list != null && list.size() > 0) {
      ((VIPGoodsBean)this.mIntegralInfos.get(0)).setSelect(true);
      this.mPayBean = this.mIntegralInfos.get(0);
    } 
    this.mAdapter.setData(this.mIntegralInfos);
    this.listView.setAdapter((ListAdapter)this.mAdapter);
    this.listView.setSelector((Drawable)new ColorDrawable(0));
    this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
            VIPGoodsBean vIPGoodsBean = SelectVipDialog.this.mIntegralInfos.get(param1Int);
            Iterator<VIPGoodsBean> iterator = SelectVipDialog.this.mIntegralInfos.iterator();
            while (iterator.hasNext())
              ((VIPGoodsBean)iterator.next()).setSelect(false); 
            ((VIPGoodsBean)SelectVipDialog.this.mIntegralInfos.get(param1Int)).setSelect(true);
            SelectVipDialog.access$102(SelectVipDialog.this, vIPGoodsBean);
            TextView textView = SelectVipDialog.this.payAmount;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("￥");
            stringBuilder.append(SelectVipDialog.this.mPayBean.getMoney());
            textView.setText(stringBuilder.toString());
            SelectVipDialog.this.mAdapter.setData(SelectVipDialog.this.mIntegralInfos);
          }
        });
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == 2131296414) {
      dismiss();
    } else if (paramView.getId() == 2131296787) {
      VIPGoodsBean vIPGoodsBean = this.mPayBean;
      if (vIPGoodsBean == null) {
        ToastUtil.showToast("请选择开通时长");
        return;
      } 
      this.mLisenter.setValue(vIPGoodsBean);
      dismiss();
    } 
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131427431);
    setCanceledOnTouchOutside(false);
    setCancelable(true);
    Window window = getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.width = ScreenUtils.getScreenWidth(this.mContext) * 1;
    window.setGravity(80);
    window.setAttributes(layoutParams);
    initView();
    initData();
  }
  
  public void setPositionLisenter(PositionLisenter paramPositionLisenter) {
    this.mLisenter = paramPositionLisenter;
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
      SelectVipDialog.ViewHolder viewHolder;
      StringBuilder stringBuilder1;
      if (param1View == null) {
        viewHolder = new SelectVipDialog.ViewHolder();
        param1View = LayoutInflater.from(this.mContext).inflate(2131427459, null);
        viewHolder.vipName = (TextView)param1View.findViewById(2131296786);
        viewHolder.vipMemo = (TextView)param1View.findViewById(2131296792);
        viewHolder.vipLayout = (LinearLayout)param1View.findViewById(2131296779);
        param1View.setTag(viewHolder);
      } else {
        viewHolder = (SelectVipDialog.ViewHolder)param1View.getTag();
      } 
      VIPGoodsBean vIPGoodsBean = this.mDatas.get(i);
      viewHolder.vipName.setText(vIPGoodsBean.getBiztext());
      TextView textView = viewHolder.vipMemo;
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(vIPGoodsBean.getValue());
      stringBuilder2.append("天");
      textView.setText(stringBuilder2.toString());
      if (vIPGoodsBean.isSelect()) {
        viewHolder.vipLayout.setBackgroundResource(2131230868);
        viewHolder.vipName.setTextColor(this.mContext.getResources().getColor(2131099843));
        viewHolder.vipMemo.setTextColor(this.mContext.getResources().getColor(2131099843));
        TextView textView1 = SelectVipDialog.this.payAmount;
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("￥");
        stringBuilder1.append(((VIPGoodsBean)this.mDatas.get(i)).getMoney());
        textView1.setText(stringBuilder1.toString());
      } else {
        ((SelectVipDialog.ViewHolder)stringBuilder1).vipLayout.setBackgroundResource(2131230874);
        ((SelectVipDialog.ViewHolder)stringBuilder1).vipName.setTextColor(this.mContext.getResources().getColor(2131099756));
        ((SelectVipDialog.ViewHolder)stringBuilder1).vipMemo.setTextColor(this.mContext.getResources().getColor(2131099846));
      } 
      param1View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param2View) {
              Iterator<VIPGoodsBean> iterator = SelectVipDialog.MyAdapter.this.mDatas.iterator();
              while (iterator.hasNext())
                ((VIPGoodsBean)iterator.next()).setSelect(false); 
              ((VIPGoodsBean)SelectVipDialog.MyAdapter.this.mDatas.get(i)).setSelect(true);
              TextView textView = SelectVipDialog.this.payAmount;
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("￥");
              stringBuilder.append(((VIPGoodsBean)SelectVipDialog.MyAdapter.this.mDatas.get(i)).getMoney());
              textView.setText(stringBuilder.toString());
              SelectVipDialog.access$102(SelectVipDialog.this, SelectVipDialog.MyAdapter.this.mDatas.get(i));
              SelectVipDialog.MyAdapter.this.notifyDataSetChanged();
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
      TextView textView = SelectVipDialog.this.payAmount;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("￥");
      stringBuilder.append(((VIPGoodsBean)this.this$1.mDatas.get(i)).getMoney());
      textView.setText(stringBuilder.toString());
      SelectVipDialog.access$102(SelectVipDialog.this, this.this$1.mDatas.get(i));
      this.this$1.notifyDataSetChanged();
    }
  }
  
  public static interface PositionLisenter {
    void setValue(VIPGoodsBean param1VIPGoodsBean);
  }
  
  private class ViewHolder {
    LinearLayout vipLayout;
    
    TextView vipMemo;
    
    TextView vipName;
    
    TextView vipPrice;
    
    private ViewHolder() {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\dialog\SelectVipDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */