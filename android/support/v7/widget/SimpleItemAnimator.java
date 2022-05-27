package android.support.v7.widget;

import android.view.View;

public abstract class SimpleItemAnimator extends RecyclerView.ItemAnimator {
  private static final boolean DEBUG = false;
  
  private static final String TAG = "SimpleItemAnimator";
  
  boolean mSupportsChangeAnimations = true;
  
  public abstract boolean animateAdd(RecyclerView.ViewHolder paramViewHolder);
  
  public boolean animateAppearance(RecyclerView.ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2) {
    return (paramItemHolderInfo1 != null && (paramItemHolderInfo1.left != paramItemHolderInfo2.left || paramItemHolderInfo1.top != paramItemHolderInfo2.top)) ? animateMove(paramViewHolder, paramItemHolderInfo1.left, paramItemHolderInfo1.top, paramItemHolderInfo2.left, paramItemHolderInfo2.top) : animateAdd(paramViewHolder);
  }
  
  public abstract boolean animateChange(RecyclerView.ViewHolder paramViewHolder1, RecyclerView.ViewHolder paramViewHolder2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public boolean animateChange(RecyclerView.ViewHolder paramViewHolder1, RecyclerView.ViewHolder paramViewHolder2, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2) {
    int k;
    int m;
    int i = paramItemHolderInfo1.left;
    int j = paramItemHolderInfo1.top;
    if (paramViewHolder2.shouldIgnore()) {
      k = paramItemHolderInfo1.left;
      m = paramItemHolderInfo1.top;
    } else {
      k = paramItemHolderInfo2.left;
      m = paramItemHolderInfo2.top;
    } 
    return animateChange(paramViewHolder1, paramViewHolder2, i, j, k, m);
  }
  
  public boolean animateDisappearance(RecyclerView.ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2) {
    int k;
    int m;
    int i = paramItemHolderInfo1.left;
    int j = paramItemHolderInfo1.top;
    View view = paramViewHolder.itemView;
    if (paramItemHolderInfo2 == null) {
      k = view.getLeft();
    } else {
      k = paramItemHolderInfo2.left;
    } 
    if (paramItemHolderInfo2 == null) {
      m = view.getTop();
    } else {
      m = paramItemHolderInfo2.top;
    } 
    if (!paramViewHolder.isRemoved() && (i != k || j != m)) {
      view.layout(k, m, view.getWidth() + k, view.getHeight() + m);
      return animateMove(paramViewHolder, i, j, k, m);
    } 
    return animateRemove(paramViewHolder);
  }
  
  public abstract boolean animateMove(RecyclerView.ViewHolder paramViewHolder, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public boolean animatePersistence(RecyclerView.ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2) {
    if (paramItemHolderInfo1.left != paramItemHolderInfo2.left || paramItemHolderInfo1.top != paramItemHolderInfo2.top)
      return animateMove(paramViewHolder, paramItemHolderInfo1.left, paramItemHolderInfo1.top, paramItemHolderInfo2.left, paramItemHolderInfo2.top); 
    dispatchMoveFinished(paramViewHolder);
    return false;
  }
  
  public abstract boolean animateRemove(RecyclerView.ViewHolder paramViewHolder);
  
  public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder paramViewHolder) {
    return (!this.mSupportsChangeAnimations || paramViewHolder.isInvalid());
  }
  
  public final void dispatchAddFinished(RecyclerView.ViewHolder paramViewHolder) {
    onAddFinished(paramViewHolder);
    dispatchAnimationFinished(paramViewHolder);
  }
  
  public final void dispatchAddStarting(RecyclerView.ViewHolder paramViewHolder) {
    onAddStarting(paramViewHolder);
  }
  
  public final void dispatchChangeFinished(RecyclerView.ViewHolder paramViewHolder, boolean paramBoolean) {
    onChangeFinished(paramViewHolder, paramBoolean);
    dispatchAnimationFinished(paramViewHolder);
  }
  
  public final void dispatchChangeStarting(RecyclerView.ViewHolder paramViewHolder, boolean paramBoolean) {
    onChangeStarting(paramViewHolder, paramBoolean);
  }
  
  public final void dispatchMoveFinished(RecyclerView.ViewHolder paramViewHolder) {
    onMoveFinished(paramViewHolder);
    dispatchAnimationFinished(paramViewHolder);
  }
  
  public final void dispatchMoveStarting(RecyclerView.ViewHolder paramViewHolder) {
    onMoveStarting(paramViewHolder);
  }
  
  public final void dispatchRemoveFinished(RecyclerView.ViewHolder paramViewHolder) {
    onRemoveFinished(paramViewHolder);
    dispatchAnimationFinished(paramViewHolder);
  }
  
  public final void dispatchRemoveStarting(RecyclerView.ViewHolder paramViewHolder) {
    onRemoveStarting(paramViewHolder);
  }
  
  public boolean getSupportsChangeAnimations() {
    return this.mSupportsChangeAnimations;
  }
  
  public void onAddFinished(RecyclerView.ViewHolder paramViewHolder) {}
  
  public void onAddStarting(RecyclerView.ViewHolder paramViewHolder) {}
  
  public void onChangeFinished(RecyclerView.ViewHolder paramViewHolder, boolean paramBoolean) {}
  
  public void onChangeStarting(RecyclerView.ViewHolder paramViewHolder, boolean paramBoolean) {}
  
  public void onMoveFinished(RecyclerView.ViewHolder paramViewHolder) {}
  
  public void onMoveStarting(RecyclerView.ViewHolder paramViewHolder) {}
  
  public void onRemoveFinished(RecyclerView.ViewHolder paramViewHolder) {}
  
  public void onRemoveStarting(RecyclerView.ViewHolder paramViewHolder) {}
  
  public void setSupportsChangeAnimations(boolean paramBoolean) {
    this.mSupportsChangeAnimations = paramBoolean;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\SimpleItemAnimator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */