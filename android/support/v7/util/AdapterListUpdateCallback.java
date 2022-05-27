package android.support.v7.util;

import android.support.v7.widget.RecyclerView;

public final class AdapterListUpdateCallback implements ListUpdateCallback {
  private final RecyclerView.Adapter mAdapter;
  
  public AdapterListUpdateCallback(RecyclerView.Adapter paramAdapter) {
    this.mAdapter = paramAdapter;
  }
  
  public void onChanged(int paramInt1, int paramInt2, Object paramObject) {
    this.mAdapter.notifyItemRangeChanged(paramInt1, paramInt2, paramObject);
  }
  
  public void onInserted(int paramInt1, int paramInt2) {
    this.mAdapter.notifyItemRangeInserted(paramInt1, paramInt2);
  }
  
  public void onMoved(int paramInt1, int paramInt2) {
    this.mAdapter.notifyItemMoved(paramInt1, paramInt2);
  }
  
  public void onRemoved(int paramInt1, int paramInt2) {
    this.mAdapter.notifyItemRangeRemoved(paramInt1, paramInt2);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v\\util\AdapterListUpdateCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */