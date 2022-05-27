package io.virtualapp.abs.nestedadapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

public class SmartRecyclerAdapter extends RecyclerViewAdapterWrapper {
  public static final int TYPE_FOOTER = -2;
  
  public static final int TYPE_HEADER = -1;
  
  private View footerView;
  
  private View headerView;
  
  private RecyclerView.LayoutManager layoutManager;
  
  public SmartRecyclerAdapter(RecyclerView.Adapter paramAdapter) {
    super(paramAdapter);
  }
  
  private boolean hasFooter() {
    boolean bool;
    if (this.footerView != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private boolean hasHeader() {
    boolean bool;
    if (this.headerView != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private void setGridHeaderFooter(RecyclerView.LayoutManager paramLayoutManager) {
    if (paramLayoutManager instanceof GridLayoutManager) {
      final GridLayoutManager gridLayoutManager = (GridLayoutManager)paramLayoutManager;
      gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            public int getSpanSize(int param1Int) {
              boolean bool2;
              boolean bool1 = false;
              if (param1Int == 0 && SmartRecyclerAdapter.this.hasHeader()) {
                bool2 = true;
              } else {
                bool2 = false;
              } 
              boolean bool3 = bool1;
              if (param1Int == SmartRecyclerAdapter.this.getItemCount() - 1) {
                bool3 = bool1;
                if (SmartRecyclerAdapter.this.hasFooter())
                  bool3 = true; 
              } 
              return (bool3 || bool2) ? gridLayoutManager.getSpanCount() : 1;
            }
          });
    } 
  }
  
  public int getItemCount() {
    return super.getItemCount() + hasHeader() + hasFooter();
  }
  
  public int getItemViewType(int paramInt) {
    if (hasHeader() && paramInt == 0)
      return -1; 
    if (hasFooter() && paramInt == getItemCount() - 1)
      return -2; 
    int i = paramInt;
    if (hasHeader())
      i = paramInt - 1; 
    return super.getItemViewType(i);
  }
  
  public void onAttachedToRecyclerView(RecyclerView paramRecyclerView) {
    super.onAttachedToRecyclerView(paramRecyclerView);
    RecyclerView.LayoutManager layoutManager = paramRecyclerView.getLayoutManager();
    this.layoutManager = layoutManager;
    setGridHeaderFooter(layoutManager);
  }
  
  public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt) {
    if (getItemViewType(paramInt) != -1 && getItemViewType(paramInt) != -2) {
      int i = paramInt;
      if (hasHeader())
        i = paramInt - 1; 
      super.onBindViewHolder(paramViewHolder, i);
    } 
  }
  
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
    StaggeredGridLayoutManager.LayoutParams layoutParams;
    View view;
    if (paramInt == -1) {
      view = this.headerView;
    } else if (paramInt == -2) {
      view = this.footerView;
    } else {
      view = null;
    } 
    if (view != null) {
      if (this.layoutManager instanceof StaggeredGridLayoutManager) {
        ViewGroup.LayoutParams layoutParams1 = view.getLayoutParams();
        if (layoutParams1 != null) {
          layoutParams = new StaggeredGridLayoutManager.LayoutParams(layoutParams1.width, layoutParams1.height);
        } else {
          layoutParams = new StaggeredGridLayoutManager.LayoutParams(-1, -2);
        } 
        layoutParams.setFullSpan(true);
        view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      } 
      return new RecyclerView.ViewHolder(view) {
        
        };
    } 
    return super.onCreateViewHolder((ViewGroup)layoutParams, paramInt);
  }
  
  public void removeFooterView() {
    this.footerView = null;
    getWrappedAdapter().notifyDataSetChanged();
  }
  
  public void removeHeaderView() {
    this.headerView = null;
    getWrappedAdapter().notifyDataSetChanged();
  }
  
  public void setFooterView(View paramView) {
    this.footerView = paramView;
    getWrappedAdapter().notifyDataSetChanged();
  }
  
  public void setHeaderView(View paramView) {
    this.headerView = paramView;
    getWrappedAdapter().notifyDataSetChanged();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\abs\nestedadapter\SmartRecyclerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */