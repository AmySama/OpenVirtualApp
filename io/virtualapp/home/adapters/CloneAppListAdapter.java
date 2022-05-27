package io.virtualapp.home.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import io.virtualapp.abs.ui.VUiKit;
import io.virtualapp.home.models.AppInfo;
import io.virtualapp.widgets.DragSelectRecyclerViewAdapter;
import io.virtualapp.widgets.LabelView;
import io.virtualapp.widgets.MarqueeTextView;
import java.util.ArrayList;
import java.util.List;

public class CloneAppListAdapter extends DragSelectRecyclerViewAdapter<CloneAppListAdapter.ViewHolder> {
  private static final int TYPE_FOOTER = -2;
  
  private List<AppInfo> mAppList;
  
  private final View mFooterView;
  
  private LayoutInflater mInflater;
  
  private ItemEventListener mItemEventListener;
  
  public CloneAppListAdapter(Context paramContext) {
    this.mInflater = LayoutInflater.from(paramContext);
    this.mFooterView = new View(paramContext);
    StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(-1, VUiKit.dpToPx(paramContext, 60));
    layoutParams.setFullSpan(true);
    this.mFooterView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
  }
  
  public AppInfo getItem(int paramInt) {
    return this.mAppList.get(paramInt);
  }
  
  public int getItemCount() {
    List<AppInfo> list = this.mAppList;
    int i = 1;
    if (list != null)
      i = 1 + list.size(); 
    return i;
  }
  
  public int getItemViewType(int paramInt) {
    return (paramInt == getItemCount() - 1) ? -2 : super.getItemViewType(paramInt);
  }
  
  public List<AppInfo> getList() {
    return this.mAppList;
  }
  
  protected boolean isIndexSelectable(int paramInt) {
    return this.mItemEventListener.isSelectable(paramInt);
  }
  
  public void onAttachedToRecyclerView(RecyclerView paramRecyclerView) {
    super.onAttachedToRecyclerView(paramRecyclerView);
  }
  
  public void onBindViewHolder(ViewHolder paramViewHolder, final int position) {
    if (getItemViewType(position) == -2)
      return; 
    super.onBindViewHolder(paramViewHolder, position);
    final AppInfo info = this.mAppList.get(position);
    paramViewHolder.iconView.setImageDrawable(appInfo.icon);
    paramViewHolder.nameView.setText(appInfo.name);
    if (isIndexSelected(position)) {
      paramViewHolder.iconView.setAlpha(1.0F);
    } else {
      paramViewHolder.iconView.setAlpha(0.65F);
    } 
    if (appInfo.cloneCount > 0) {
      paramViewHolder.labelView.setVisibility(0);
      LabelView labelView = paramViewHolder.labelView;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(appInfo.cloneCount + 1);
      stringBuilder.append("");
      labelView.setText(stringBuilder.toString());
    } else {
      paramViewHolder.labelView.setVisibility(4);
    } 
    if (appInfo.getAppCloneCount().intValue() > 0) {
      TextView textView = paramViewHolder.countView;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("分身次数");
      stringBuilder.append(appInfo.getAppCloneCount());
      stringBuilder.append("");
      textView.setText(stringBuilder.toString());
    } else {
      paramViewHolder.countView.setText("");
    } 
    paramViewHolder.makeView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            CloneAppListAdapter.this.mItemEventListener.onItemClick(info, position);
          }
        });
    paramViewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            CloneAppListAdapter.this.mItemEventListener.onItemClick(info, position);
          }
        });
  }
  
  public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
    return (paramInt == -2) ? new ViewHolder(this.mFooterView) : new ViewHolder(this.mInflater.inflate(2131427446, null));
  }
  
  public void setList(List<AppInfo> paramList) {
    this.mAppList = new ArrayList<AppInfo>();
    this.mAppList = paramList;
    notifyDataSetChanged();
  }
  
  public void setOnItemClickListener(ItemEventListener paramItemEventListener) {
    this.mItemEventListener = paramItemEventListener;
  }
  
  public static interface ItemEventListener {
    boolean isSelectable(int param1Int);
    
    void onItemClick(AppInfo param1AppInfo, int param1Int);
  }
  
  class ViewHolder extends RecyclerView.ViewHolder {
    private TextView countView;
    
    private ImageView iconView;
    
    private FrameLayout itemLayout;
    
    private LabelView labelView;
    
    private ImageView makeView;
    
    private TextView nameView;
    
    ViewHolder(View param1View) {
      super(param1View);
      if (param1View != CloneAppListAdapter.this.mFooterView) {
        this.itemLayout = (FrameLayout)param1View.findViewById(2131296544);
        this.iconView = (ImageView)param1View.findViewById(2131296536);
        this.nameView = (TextView)param1View.findViewById(2131296537);
        this.labelView = (LabelView)param1View.findViewById(2131296535);
        this.makeView = (ImageView)param1View.findViewById(2131296688);
        this.countView = (TextView)param1View.findViewById(2131296538);
        TextView textView = this.nameView;
        if (textView instanceof MarqueeTextView)
          ((MarqueeTextView)textView).start(); 
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\adapters\CloneAppListAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */