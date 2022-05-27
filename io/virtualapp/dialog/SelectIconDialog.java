package io.virtualapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import io.virtualapp.App;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.home.models.AppInfo;
import io.virtualapp.home.repo.AppDataSource;
import io.virtualapp.home.repo.AppRepository;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SelectIconDialog extends Dialog implements View.OnClickListener {
  private static final String KEY_SELECT_FROM = "key_select_from";
  
  private static final String SELECT_ICON = "selectIcon";
  
  private ImageView closedIV;
  
  private byte[] iconByte;
  
  private GridView listView;
  
  private MyAdapter mAdapter;
  
  private List<AppInfo> mDatas;
  
  private AppDataSource mRepository;
  
  private Context mcontext;
  
  public PermissionDialog.OnPositionLisenter onPositionLisenter;
  
  private TextView saveTV;
  
  public SelectIconDialog(Context paramContext) {
    this(paramContext, 2131689644);
    this.mcontext = paramContext;
  }
  
  public SelectIconDialog(Context paramContext, int paramInt) {
    super(paramContext, paramInt);
    this.mcontext = paramContext;
  }
  
  protected SelectIconDialog(Context paramContext, int paramInt, DialogInterface.OnCancelListener paramOnCancelListener) {
    super(paramContext, paramInt);
    this.mcontext = paramContext;
  }
  
  private void initData() {
    AppRepository appRepository = new AppRepository((Context)App.getApp());
    this.mRepository = (AppDataSource)appRepository;
    this.mDatas = appRepository.getInstalledAppsDirect((Context)App.getApp());
  }
  
  private void initView() {
    ImageView imageView = (ImageView)findViewById(2131296415);
    this.closedIV = imageView;
    imageView.setOnClickListener(this);
    TextView textView = (TextView)findViewById(2131296643);
    this.saveTV = textView;
    textView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            SharedPreferences.Editor editor = SelectIconDialog.this.mcontext.getSharedPreferences("selectIcon", 0).edit();
            editor.putString("content", null);
            editor.commit();
            for (AppInfo appInfo : SelectIconDialog.this.mDatas) {
              if (appInfo.isSelected) {
                SelectIconDialog selectIconDialog = SelectIconDialog.this;
                SelectIconDialog.access$202(selectIconDialog, selectIconDialog.DrawableToByte(appInfo.icon));
                SharedPreferences.Editor editor1 = SelectIconDialog.this.mcontext.getSharedPreferences("selectIcon", 0).edit();
                editor1.putString("content", new String(Base64.encode(SelectIconDialog.this.iconByte, 0)));
                SPUtils.put(SelectIconDialog.this.mcontext, "icon_packet", appInfo.getPackageName());
                editor1.commit();
              } 
            } 
            SelectIconDialog.this.onPositionLisenter.onPositionLisenter();
          }
        });
    GridView gridView = (GridView)findViewById(2131296510);
    this.listView = gridView;
    gridView.setSelector((Drawable)new ColorDrawable(0));
    MyAdapter myAdapter = new MyAdapter(this.mcontext);
    this.mAdapter = myAdapter;
    myAdapter.setData(this.mDatas);
    this.listView.setAdapter((ListAdapter)this.mAdapter);
    this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
            if (((AppInfo)SelectIconDialog.this.mDatas.get(param1Int)).isSelected()) {
              ((AppInfo)SelectIconDialog.this.mDatas.get(param1Int)).setSelected(false);
            } else {
              Iterator<AppInfo> iterator = SelectIconDialog.this.mDatas.iterator();
              while (iterator.hasNext())
                ((AppInfo)iterator.next()).setSelected(false); 
              ((AppInfo)SelectIconDialog.this.mDatas.get(param1Int)).setSelected(true);
            } 
            SelectIconDialog.this.mAdapter.setData(SelectIconDialog.this.mDatas);
          }
        });
  }
  
  public byte[] DrawableToByte(Drawable paramDrawable) {
    Bitmap.Config config;
    int i = paramDrawable.getIntrinsicWidth();
    int j = paramDrawable.getIntrinsicHeight();
    if (paramDrawable.getOpacity() != -1) {
      config = Bitmap.Config.ARGB_8888;
    } else {
      config = Bitmap.Config.RGB_565;
    } 
    Bitmap bitmap = Bitmap.createBitmap(i, j, config);
    Canvas canvas = new Canvas(bitmap);
    paramDrawable.setBounds(0, 0, i, j);
    paramDrawable.draw(canvas);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == 2131296415)
      dismiss(); 
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setCanceledOnTouchOutside(false);
    setCancelable(true);
    setContentView(2131427429);
    Window window = getWindow();
    Display display = ((WindowManager)this.mcontext.getSystemService("window")).getDefaultDisplay();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.height = (int)(display.getHeight() * 0.6D);
    layoutParams.width = display.getWidth() * 1;
    window.setGravity(80);
    window.setAttributes(layoutParams);
    initData();
    initView();
  }
  
  public void setOnPositionLisenter(PermissionDialog.OnPositionLisenter paramOnPositionLisenter) {
    this.onPositionLisenter = paramOnPositionLisenter;
  }
  
  private class MyAdapter extends BaseAdapter {
    private Context mContext;
    
    private List<AppInfo> mDatas;
    
    public MyAdapter(Context param1Context) {
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
      SelectIconDialog.ViewHolder viewHolder1;
      SelectIconDialog.ViewHolder viewHolder2;
      if (param1View == null) {
        viewHolder1 = new SelectIconDialog.ViewHolder();
        View view = LayoutInflater.from(this.mContext).inflate(2131427447, null);
        viewHolder1.itemIcon = (ImageView)view.findViewById(2131296543);
        viewHolder1.itemChecked = (ImageView)view.findViewById(2131296541);
        view.setTag(viewHolder1);
      } else {
        SelectIconDialog.ViewHolder viewHolder = (SelectIconDialog.ViewHolder)viewHolder1.getTag();
        viewHolder2 = viewHolder1;
        viewHolder1 = viewHolder;
      } 
      if (((AppInfo)this.mDatas.get(param1Int)).isSelected()) {
        viewHolder1.itemChecked.setVisibility(0);
      } else {
        viewHolder1.itemChecked.setVisibility(4);
      } 
      viewHolder1.itemIcon.setImageDrawable(((AppInfo)this.mDatas.get(param1Int)).getIcon());
      return (View)viewHolder2;
    }
    
    public void setData(List<AppInfo> param1List) {
      this.mDatas = param1List;
      notifyDataSetChanged();
    }
  }
  
  public static interface OnPositionLisenter {
    void onPositionLisenter();
  }
  
  class ViewHolder {
    ImageView itemChecked;
    
    ImageView itemIcon;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\dialog\SelectIconDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */