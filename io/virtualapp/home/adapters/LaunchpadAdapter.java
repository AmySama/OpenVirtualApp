package io.virtualapp.home.adapters;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import io.virtualapp.App;
import io.virtualapp.Utils.BitmapUtil;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.home.models.AppData;
import io.virtualapp.home.models.MultiplePackageAppData;
import io.virtualapp.widgets.LauncherIconView;
import io.virtualapp.widgets.MarqueeTextView;
import java.util.ArrayList;
import java.util.List;

public class LaunchpadAdapter extends RecyclerView.Adapter<LaunchpadAdapter.ViewHolder> {
  private OnAppClickListener mAppClickListener;
  
  private SparseIntArray mColorArray = new SparseIntArray();
  
  private LayoutInflater mInflater;
  
  private List<AppData> mList;
  
  public LaunchpadAdapter(Context paramContext) {
    this.mInflater = LayoutInflater.from(paramContext);
  }
  
  private int getColor(int paramInt) {
    int i = this.mColorArray.get(paramInt);
    int j = i;
    if (i == 0) {
      j = paramInt % 3;
      i = paramInt / 3 % 3;
      if (i == 0) {
        if (j == 0) {
          j = this.mInflater.getContext().getResources().getColor(2131099694);
        } else if (j == 1) {
          j = this.mInflater.getContext().getResources().getColor(2131099695);
        } else {
          j = this.mInflater.getContext().getResources().getColor(2131099696);
        } 
      } else if (i == 1) {
        if (j == 0) {
          j = this.mInflater.getContext().getResources().getColor(2131099695);
        } else if (j == 1) {
          j = this.mInflater.getContext().getResources().getColor(2131099696);
        } else {
          j = this.mInflater.getContext().getResources().getColor(2131099694);
        } 
      } else if (j == 0) {
        j = this.mInflater.getContext().getResources().getColor(2131099696);
      } else if (j == 1) {
        j = this.mInflater.getContext().getResources().getColor(2131099694);
      } else {
        j = this.mInflater.getContext().getResources().getColor(2131099695);
      } 
      this.mColorArray.put(paramInt, j);
    } 
    return j;
  }
  
  private void startLoadingAnimation(LauncherIconView paramLauncherIconView) {}
  
  public void add(AppData paramAppData) {
    int i = this.mList.size();
    this.mList.add(i, paramAppData);
    notifyItemInserted(i);
  }
  
  public int getItemCount() {
    int i;
    List<AppData> list = this.mList;
    if (list == null) {
      i = 0;
    } else {
      i = list.size();
    } 
    return i;
  }
  
  public List<AppData> getList() {
    if (this.mList == null)
      this.mList = new ArrayList<AppData>(); 
    return this.mList;
  }
  
  public void moveItem(int paramInt1, int paramInt2) {
    AppData appData = this.mList.remove(paramInt1);
    this.mList.add(paramInt2, appData);
    notifyItemMoved(paramInt1, paramInt2);
  }
  
  public void onBindViewHolder(ViewHolder paramViewHolder, final int position) {
    final AppData data = this.mList.get(position);
    boolean bool = TextUtils.isEmpty(appData.getPackageName());
    Drawable drawable1 = null;
    Drawable drawable2 = null;
    if (!bool) {
      String str3;
      drawable1 = drawable2;
      try {
        PackageManager packageManager = App.getApp().getPackageManager();
        drawable1 = drawable2;
        ApplicationInfo applicationInfo = packageManager.getApplicationInfo(appData.getPackageName(), 0);
        drawable1 = drawable2;
        Drawable drawable = applicationInfo.loadIcon(packageManager);
        drawable1 = drawable;
        str3 = applicationInfo.loadLabel(packageManager).toString();
        drawable1 = drawable;
      } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
        nameNotFoundException.printStackTrace();
        str3 = "";
      } 
      paramViewHolder.iconView.setImageDrawable(appData.getIcon());
      int i = appData.getUserId() + 1;
      if (!appData.isSys()) {
        TextView textView = paramViewHolder.nameView;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(appData.getName());
        stringBuilder.append(i);
        textView.setText(stringBuilder.toString());
        if (!TextUtils.isEmpty(str3)) {
          TextView textView1 = paramViewHolder.nameView;
          StringBuilder stringBuilder3 = new StringBuilder();
          stringBuilder3.append(str3);
          stringBuilder3.append(i);
          textView1.setText(stringBuilder3.toString());
        } 
      } else {
        paramViewHolder.nameView.setText(appData.getName());
      } 
      if (drawable1 != null)
        paramViewHolder.iconView.setImageDrawable(drawable1); 
      App app1 = App.getApp();
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(appData.getPackageName());
      stringBuilder2.append(appData.getUserId());
      String str2 = SPUtils.get((Context)app1, stringBuilder2.toString());
      if (!TextUtils.isEmpty(str2) && !str2.equals(""))
        paramViewHolder.nameView.setText(str2); 
      App app2 = App.getApp();
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(appData.getPackageName());
      stringBuilder1.append(appData.getUserId());
      stringBuilder1.append("icon");
      String str1 = SPUtils.get((Context)app2, stringBuilder1.toString());
      if (!TextUtils.isEmpty(str1) && !str1.equals(""))
        try {
          Bitmap bitmap1;
          if (str1.contains("android.resource://")) {
            Uri uri = Uri.parse(str1);
            bitmap1 = MediaStore.Images.Media.getBitmap(App.getApp().getContentResolver(), uri);
          } else {
            bitmap1 = BitmapFactory.decodeFile((String)bitmap1);
          } 
          Bitmap bitmap2 = bitmap1;
          if (bitmap1 != null)
            bitmap2 = BitmapUtil.toRoundCorner(bitmap1, 50); 
          paramViewHolder.iconView.setImageBitmap(bitmap2);
        } catch (Exception exception) {} 
      if (appData.isSys()) {
        paramViewHolder.svipImg.setVisibility(0);
      } else {
        paramViewHolder.svipImg.setVisibility(8);
      } 
      paramViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
              if (LaunchpadAdapter.this.mAppClickListener != null)
                LaunchpadAdapter.this.mAppClickListener.onAppClick(position, data); 
            }
          });
      if (appData instanceof MultiplePackageAppData)
        MultiplePackageAppData multiplePackageAppData = (MultiplePackageAppData)appData; 
      if (!appData.isLoading())
        paramViewHolder.iconView.setProgress(100, false); 
      return;
    } 
    String str = "";
  }
  
  public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
    return new ViewHolder(this.mInflater.inflate(2131427448, null));
  }
  
  public void refresh(AppData paramAppData) {
    int i = this.mList.indexOf(paramAppData);
    if (i >= 0)
      notifyItemChanged(i); 
  }
  
  public void remove(AppData paramAppData) {
    if (this.mList.remove(paramAppData))
      notifyDataSetChanged(); 
  }
  
  public void replace(int paramInt, AppData paramAppData) {
    this.mList.set(paramInt, paramAppData);
    notifyItemChanged(paramInt);
  }
  
  public void setAppClickListener(OnAppClickListener paramOnAppClickListener) {
    this.mAppClickListener = paramOnAppClickListener;
  }
  
  public void setList(List<AppData> paramList) {
    this.mList = paramList;
    notifyDataSetChanged();
  }
  
  public static interface OnAppClickListener {
    void onAppClick(int param1Int, AppData param1AppData);
  }
  
  public class ViewHolder extends RecyclerView.ViewHolder {
    public int color;
    
    View firstOpenDot;
    
    LauncherIconView iconView;
    
    TextView nameView;
    
    ImageView svipImg;
    
    ViewHolder(View param1View) {
      super(param1View);
      this.iconView = (LauncherIconView)param1View.findViewById(2131296536);
      TextView textView = (TextView)param1View.findViewById(2131296537);
      this.nameView = textView;
      if (textView instanceof MarqueeTextView)
        ((MarqueeTextView)textView).start(); 
      this.firstOpenDot = param1View.findViewById(2131296542);
      this.svipImg = (ImageView)param1View.findViewById(2131296696);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\adapters\LaunchpadAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */