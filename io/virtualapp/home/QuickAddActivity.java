package io.virtualapp.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.stub.StubApp;
import io.virtualapp.Utils.AppUtils;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.dialog.LoadingDialog;
import io.virtualapp.home.models.NomalAppButton;
import io.virtualapp.widgets.MyListView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuickAddActivity extends VActivity implements View.OnClickListener {
  private MyListView appList;
  
  private LinearLayout goHomeLayout;
  
  private ImageView leftImage;
  
  private MyAdapter mAdapter;
  
  Activity mContext;
  
  private HomeContract.HomePresenter mPresenter;
  
  private LoadingDialog mdialog;
  
  private List<NomalAppButton> normalDatas = new ArrayList<NomalAppButton>();
  
  private LinearLayout quickAddLayout;
  
  private TextView titleView;
  
  private TextView tvTurnOver;
  
  static {
    StubApp.interface11(9720);
  }
  
  private void initDatas() {
    if (AppUtils.checkApkExist((Context)this, "com.tencent.mm")) {
      NomalAppButton nomalAppButton = new NomalAppButton(getResources().getDrawable(2131558416), "微信", "com.tencent.mm");
      this.normalDatas.add(nomalAppButton);
    } 
    if (AppUtils.checkApkExist((Context)this, "com.tencent.mobileqq")) {
      NomalAppButton nomalAppButton = new NomalAppButton(getResources().getDrawable(2131558414), "QQ", "com.tencent.mobileqq");
      this.normalDatas.add(nomalAppButton);
    } 
    this.mAdapter.setData(this.normalDatas);
  }
  
  private void initViews() {
    this.goHomeLayout = (LinearLayout)findViewById(2131296482);
    this.quickAddLayout = (LinearLayout)findViewById(2131296629);
    this.tvTurnOver = (TextView)findViewById(2131296639);
    this.titleView = (TextView)findViewById(2131296401);
    this.leftImage = (ImageView)findViewById(2131296567);
    this.appList = (MyListView)findViewById(2131296314);
    MyAdapter myAdapter = new MyAdapter((Context)this);
    this.mAdapter = myAdapter;
    this.appList.setAdapter((ListAdapter)myAdapter);
    this.tvTurnOver.setText("跳过");
    this.titleView.setText("快速制作一个分身");
    this.goHomeLayout.setOnClickListener(this);
    this.tvTurnOver.setOnClickListener(this);
    this.quickAddLayout.setOnClickListener(this);
  }
  
  public void onClick(View paramView) {
    int i = paramView.getId();
    if (i != 2131296482) {
      if (i != 2131296629) {
        if (i == 2131296639) {
          HomeActivity.goHome((Context)this);
          finish();
        } 
      } else {
        ListAppActivity.gotoListApp((Activity)this, true);
        finish();
      } 
    } else {
      ArrayList<String> arrayList = new ArrayList();
      for (NomalAppButton nomalAppButton : this.normalDatas) {
        if (nomalAppButton.isSelect())
          arrayList.add(nomalAppButton.getPkgName()); 
      } 
      HomeActivity.goHome((Context)this, arrayList);
      finish();
    } 
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  private class MyAdapter extends BaseAdapter {
    private Context mContext;
    
    private List<NomalAppButton> mDatas;
    
    public MyAdapter(Context param1Context) {
      this.mContext = param1Context;
      this.mDatas = new ArrayList<NomalAppButton>();
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
      QuickAddActivity.ViewHolder viewHolder1;
      QuickAddActivity.ViewHolder viewHolder2;
      if (param1View == null) {
        viewHolder1 = new QuickAddActivity.ViewHolder();
        View view = LayoutInflater.from(this.mContext).inflate(2131427451, null);
        viewHolder1.appIcon = (ImageView)view.findViewById(2131296536);
        viewHolder1.appName = (TextView)view.findViewById(2131296537);
        viewHolder1.appText = (TextView)view.findViewById(2131296539);
        viewHolder1.appCheck = (CheckBox)view.findViewById(2131296540);
        view.setTag(viewHolder1);
      } else {
        QuickAddActivity.ViewHolder viewHolder = (QuickAddActivity.ViewHolder)viewHolder1.getTag();
        viewHolder2 = viewHolder1;
        viewHolder1 = viewHolder;
      } 
      NomalAppButton nomalAppButton = this.mDatas.get(i);
      viewHolder1.appIcon.setImageDrawable(nomalAppButton.getIcon());
      viewHolder1.appName.setText(nomalAppButton.getName());
      viewHolder1.appText.setText("让您生活工作两不误");
      viewHolder1.appCheck.setChecked(nomalAppButton.isSelect());
      viewHolder2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param2View) {
              if (((NomalAppButton)QuickAddActivity.MyAdapter.this.mDatas.get(i)).isSelect()) {
                Iterator<NomalAppButton> iterator = QuickAddActivity.MyAdapter.this.mDatas.iterator();
                while (iterator.hasNext())
                  ((NomalAppButton)iterator.next()).setSelect(false); 
              } else {
                Iterator<NomalAppButton> iterator = QuickAddActivity.MyAdapter.this.mDatas.iterator();
                while (iterator.hasNext())
                  ((NomalAppButton)iterator.next()).setSelect(false); 
                ((NomalAppButton)QuickAddActivity.MyAdapter.this.mDatas.get(i)).setSelect(true);
              } 
              QuickAddActivity.access$202(QuickAddActivity.this, QuickAddActivity.MyAdapter.this.mDatas);
              QuickAddActivity.MyAdapter.this.notifyDataSetChanged();
            }
          });
      return (View)viewHolder2;
    }
    
    public void setData(List<NomalAppButton> param1List) {
      this.mDatas = param1List;
      notifyDataSetChanged();
    }
  }
  
  class null implements View.OnClickListener {
    public void onClick(View param1View) {
      if (((NomalAppButton)this.this$1.mDatas.get(i)).isSelect()) {
        Iterator<NomalAppButton> iterator = this.this$1.mDatas.iterator();
        while (iterator.hasNext())
          ((NomalAppButton)iterator.next()).setSelect(false); 
      } else {
        Iterator<NomalAppButton> iterator = this.this$1.mDatas.iterator();
        while (iterator.hasNext())
          ((NomalAppButton)iterator.next()).setSelect(false); 
        ((NomalAppButton)this.this$1.mDatas.get(i)).setSelect(true);
      } 
      QuickAddActivity.access$202(QuickAddActivity.this, this.this$1.mDatas);
      this.this$1.notifyDataSetChanged();
    }
  }
  
  private class ViewHolder {
    CheckBox appCheck;
    
    ImageView appIcon;
    
    TextView appName;
    
    TextView appText;
    
    private ViewHolder() {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\QuickAddActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */