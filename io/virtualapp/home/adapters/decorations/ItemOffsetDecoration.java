package io.virtualapp.home.adapters.decorations;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
  private int mItemOffset;
  
  public ItemOffsetDecoration(int paramInt) {
    this.mItemOffset = paramInt;
  }
  
  public ItemOffsetDecoration(Context paramContext, int paramInt) {
    this(paramContext.getResources().getDimensionPixelSize(paramInt));
  }
  
  public void getItemOffsets(Rect paramRect, View paramView, RecyclerView paramRecyclerView, RecyclerView.State paramState) {
    int i = this.mItemOffset;
    paramRect.set(i, i, i, i);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\adapters\decorations\ItemOffsetDecoration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */