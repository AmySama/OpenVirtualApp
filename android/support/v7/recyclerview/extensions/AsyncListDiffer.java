package android.support.v7.recyclerview.extensions;

import android.support.v7.util.AdapterListUpdateCallback;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import java.util.Collections;
import java.util.List;

public class AsyncListDiffer<T> {
  private final AsyncDifferConfig<T> mConfig;
  
  private List<T> mList;
  
  private int mMaxScheduledGeneration;
  
  private List<T> mReadOnlyList = Collections.emptyList();
  
  private final ListUpdateCallback mUpdateCallback;
  
  public AsyncListDiffer(ListUpdateCallback paramListUpdateCallback, AsyncDifferConfig<T> paramAsyncDifferConfig) {
    this.mUpdateCallback = paramListUpdateCallback;
    this.mConfig = paramAsyncDifferConfig;
  }
  
  public AsyncListDiffer(RecyclerView.Adapter paramAdapter, DiffUtil.ItemCallback<T> paramItemCallback) {
    this.mUpdateCallback = (ListUpdateCallback)new AdapterListUpdateCallback(paramAdapter);
    this.mConfig = (new AsyncDifferConfig.Builder<T>(paramItemCallback)).build();
  }
  
  private void latchList(List<T> paramList, DiffUtil.DiffResult paramDiffResult) {
    this.mList = paramList;
    this.mReadOnlyList = Collections.unmodifiableList(paramList);
    paramDiffResult.dispatchUpdatesTo(this.mUpdateCallback);
  }
  
  public List<T> getCurrentList() {
    return this.mReadOnlyList;
  }
  
  public void submitList(final List<T> newList) {
    final List<T> oldList = this.mList;
    if (newList == list)
      return; 
    final int runGeneration = this.mMaxScheduledGeneration + 1;
    this.mMaxScheduledGeneration = i;
    if (newList == null) {
      i = list.size();
      this.mList = null;
      this.mReadOnlyList = Collections.emptyList();
      this.mUpdateCallback.onRemoved(0, i);
      return;
    } 
    if (list == null) {
      this.mList = newList;
      this.mReadOnlyList = Collections.unmodifiableList(newList);
      this.mUpdateCallback.onInserted(0, newList.size());
      return;
    } 
    this.mConfig.getBackgroundThreadExecutor().execute(new Runnable() {
          public void run() {
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                  public boolean areContentsTheSame(int param2Int1, int param2Int2) {
                    return AsyncListDiffer.this.mConfig.getDiffCallback().areContentsTheSame(oldList.get(param2Int1), newList.get(param2Int2));
                  }
                  
                  public boolean areItemsTheSame(int param2Int1, int param2Int2) {
                    return AsyncListDiffer.this.mConfig.getDiffCallback().areItemsTheSame(oldList.get(param2Int1), newList.get(param2Int2));
                  }
                  
                  public Object getChangePayload(int param2Int1, int param2Int2) {
                    return AsyncListDiffer.this.mConfig.getDiffCallback().getChangePayload(oldList.get(param2Int1), newList.get(param2Int2));
                  }
                  
                  public int getNewListSize() {
                    return newList.size();
                  }
                  
                  public int getOldListSize() {
                    return oldList.size();
                  }
                });
            AsyncListDiffer.this.mConfig.getMainThreadExecutor().execute(new Runnable() {
                  public void run() {
                    if (AsyncListDiffer.this.mMaxScheduledGeneration == runGeneration)
                      AsyncListDiffer.this.latchList(newList, result); 
                  }
                });
          }
        });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\recyclerview\extensions\AsyncListDiffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */