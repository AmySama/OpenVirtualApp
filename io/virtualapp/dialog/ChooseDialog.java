package io.virtualapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import io.virtualapp.Utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

public class ChooseDialog extends Dialog implements View.OnClickListener {
  private ListView listView;
  
  private MyAdapter mAdapter;
  
  private Context mContext;
  
  private PositionLisenter mLisenter;
  
  private List<String> values;
  
  public ChooseDialog(Context paramContext) {
    super(paramContext);
    this.mContext = paramContext;
  }
  
  public ChooseDialog(Context paramContext, int paramInt, List<String> paramList) {
    super(paramContext, paramInt);
    this.mContext = paramContext;
    this.values = paramList;
  }
  
  private void initData() {}
  
  private void initView() {
    this.listView = (ListView)findViewById(2131296780);
    MyAdapter myAdapter = new MyAdapter(this.mContext);
    this.mAdapter = myAdapter;
    myAdapter.setData(this.values);
    this.listView.setDivider(null);
    this.listView.setAdapter((ListAdapter)this.mAdapter);
    this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
            String str = ChooseDialog.this.values.get(param1Int);
            ChooseDialog.this.mLisenter.setValue(str);
          }
        });
  }
  
  public void onClick(View paramView) {}
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131427423);
    setCanceledOnTouchOutside(true);
    setCancelable(true);
    Window window = getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.width = (int)(ScreenUtils.getScreenWidth(this.mContext) * 0.5D);
    int i = this.values.size() * 90;
    int j = (int)(ScreenUtils.getScreenHeight(this.mContext) * 0.8D);
    if (i <= j) {
      layoutParams.height = i;
    } else {
      layoutParams.height = j;
    } 
    window.setGravity(17);
    window.setAttributes(layoutParams);
    initView();
    initData();
  }
  
  public void setPositionLisenter(PositionLisenter paramPositionLisenter) {
    this.mLisenter = paramPositionLisenter;
  }
  
  private class MyAdapter extends BaseAdapter {
    private Context mContext;
    
    private List<String> mDatas;
    
    public MyAdapter(Context param1Context) {
      this.mContext = param1Context;
      this.mDatas = new ArrayList<String>();
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
      ChooseDialog.ViewHolder viewHolder1;
      ChooseDialog.ViewHolder viewHolder2;
      if (param1View == null) {
        viewHolder1 = new ChooseDialog.ViewHolder();
        View view = LayoutInflater.from(this.mContext).inflate(2131427454, null);
        viewHolder1.name = (TextView)view.findViewById(2131296407);
        view.setTag(viewHolder1);
      } else {
        ChooseDialog.ViewHolder viewHolder = (ChooseDialog.ViewHolder)viewHolder1.getTag();
        viewHolder2 = viewHolder1;
        viewHolder1 = viewHolder;
      } 
      String str = this.mDatas.get(param1Int);
      viewHolder1.name.setText(str);
      return (View)viewHolder2;
    }
    
    public void setData(List<String> param1List) {
      this.mDatas = param1List;
      notifyDataSetChanged();
    }
  }
  
  public static interface PositionLisenter {
    void setValue(String param1String);
  }
  
  private class ViewHolder {
    TextView name;
    
    private ViewHolder() {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\dialog\ChooseDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */