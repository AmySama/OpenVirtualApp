package io.virtualapp.abs.nestedadapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class RecyclerViewAdapterWrapper extends RecyclerView.Adapter {
  protected final RecyclerView.Adapter wrapped;
  
  public RecyclerViewAdapterWrapper(RecyclerView.Adapter paramAdapter) {
    this.wrapped = paramAdapter;
    paramAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
          public void onChanged() {
            RecyclerViewAdapterWrapper.this.notifyDataSetChanged();
          }
          
          public void onItemRangeChanged(int param1Int1, int param1Int2) {
            RecyclerViewAdapterWrapper.this.notifyItemRangeChanged(param1Int1, param1Int2);
          }
          
          public void onItemRangeInserted(int param1Int1, int param1Int2) {
            RecyclerViewAdapterWrapper.this.notifyItemRangeInserted(param1Int1, param1Int2);
          }
          
          public void onItemRangeMoved(int param1Int1, int param1Int2, int param1Int3) {
            RecyclerViewAdapterWrapper.this.notifyItemMoved(param1Int1, param1Int2);
          }
          
          public void onItemRangeRemoved(int param1Int1, int param1Int2) {
            RecyclerViewAdapterWrapper.this.notifyItemRangeRemoved(param1Int1, param1Int2);
          }
        });
  }
  
  public int getItemCount() {
    return this.wrapped.getItemCount();
  }
  
  public long getItemId(int paramInt) {
    return this.wrapped.getItemId(paramInt);
  }
  
  public int getItemViewType(int paramInt) {
    return this.wrapped.getItemViewType(paramInt);
  }
  
  public RecyclerView.Adapter getWrappedAdapter() {
    return this.wrapped;
  }
  
  public void onAttachedToRecyclerView(RecyclerView paramRecyclerView) {
    this.wrapped.onAttachedToRecyclerView(paramRecyclerView);
  }
  
  public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt) {
    this.wrapped.onBindViewHolder(paramViewHolder, paramInt);
  }
  
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
    return this.wrapped.onCreateViewHolder(paramViewGroup, paramInt);
  }
  
  public void onDetachedFromRecyclerView(RecyclerView paramRecyclerView) {
    this.wrapped.onDetachedFromRecyclerView(paramRecyclerView);
  }
  
  public boolean onFailedToRecycleView(RecyclerView.ViewHolder paramViewHolder) {
    return this.wrapped.onFailedToRecycleView(paramViewHolder);
  }
  
  public void onViewAttachedToWindow(RecyclerView.ViewHolder paramViewHolder) {
    this.wrapped.onViewAttachedToWindow(paramViewHolder);
  }
  
  public void onViewDetachedFromWindow(RecyclerView.ViewHolder paramViewHolder) {
    this.wrapped.onViewDetachedFromWindow(paramViewHolder);
  }
  
  public void onViewRecycled(RecyclerView.ViewHolder paramViewHolder) {
    this.wrapped.onViewRecycled(paramViewHolder);
  }
  
  public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver paramAdapterDataObserver) {
    this.wrapped.registerAdapterDataObserver(paramAdapterDataObserver);
  }
  
  public void setHasStableIds(boolean paramBoolean) {
    this.wrapped.setHasStableIds(paramBoolean);
  }
  
  public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver paramAdapterDataObserver) {
    this.wrapped.unregisterAdapterDataObserver(paramAdapterDataObserver);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\abs\nestedadapter\RecyclerViewAdapterWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */