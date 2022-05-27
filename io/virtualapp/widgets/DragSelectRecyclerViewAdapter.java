package io.virtualapp.widgets;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

public abstract class DragSelectRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
  private int mLastCount = -1;
  
  private int mMaxSelectionCount = -1;
  
  private ArrayList<Integer> mSelectedIndices = new ArrayList<Integer>();
  
  private SelectionListener mSelectionListener;
  
  private void fireSelectionListener() {
    if (this.mLastCount == this.mSelectedIndices.size())
      return; 
    int i = this.mSelectedIndices.size();
    this.mLastCount = i;
    SelectionListener selectionListener = this.mSelectionListener;
    if (selectionListener != null)
      selectionListener.onDragSelectionChanged(i); 
  }
  
  public final void clearSelected() {
    this.mSelectedIndices.clear();
    notifyDataSetChanged();
    fireSelectionListener();
  }
  
  public final int getSelectedCount() {
    return this.mSelectedIndices.size();
  }
  
  public final Integer[] getSelectedIndices() {
    ArrayList<Integer> arrayList = this.mSelectedIndices;
    return arrayList.<Integer>toArray(new Integer[arrayList.size()]);
  }
  
  protected boolean isIndexSelectable(int paramInt) {
    return true;
  }
  
  public final boolean isIndexSelected(int paramInt) {
    return this.mSelectedIndices.contains(Integer.valueOf(paramInt));
  }
  
  public void onBindViewHolder(VH paramVH, int paramInt) {
    ((RecyclerView.ViewHolder)paramVH).itemView.setTag(paramVH);
  }
  
  public void restoreInstanceState(Bundle paramBundle) {
    restoreInstanceState("selected_indices", paramBundle);
  }
  
  public void restoreInstanceState(String paramString, Bundle paramBundle) {
    if (paramBundle != null && paramBundle.containsKey(paramString)) {
      ArrayList<Integer> arrayList = (ArrayList)paramBundle.getSerializable(paramString);
      this.mSelectedIndices = arrayList;
      if (arrayList == null) {
        this.mSelectedIndices = new ArrayList<Integer>();
      } else {
        fireSelectionListener();
      } 
    } 
  }
  
  public void saveInstanceState(Bundle paramBundle) {
    saveInstanceState("selected_indices", paramBundle);
  }
  
  public void saveInstanceState(String paramString, Bundle paramBundle) {
    paramBundle.putSerializable(paramString, this.mSelectedIndices);
  }
  
  public final void selectAll() {
    int i = getItemCount();
    this.mSelectedIndices.clear();
    for (byte b = 0; b < i; b++) {
      if (isIndexSelectable(b))
        this.mSelectedIndices.add(Integer.valueOf(b)); 
    } 
    notifyDataSetChanged();
    fireSelectionListener();
  }
  
  public final void selectRange(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (paramInt1 == paramInt2) {
      while (paramInt3 <= paramInt4) {
        if (paramInt3 != paramInt1)
          setSelected(paramInt3, false); 
        paramInt3++;
      } 
      fireSelectionListener();
      return;
    } 
    if (paramInt2 < paramInt1) {
      for (int i = paramInt2; i <= paramInt1; i++)
        setSelected(i, true); 
      if (paramInt3 > -1 && paramInt3 < paramInt2)
        while (paramInt3 < paramInt2) {
          if (paramInt3 != paramInt1)
            setSelected(paramInt3, false); 
          paramInt3++;
        }  
      if (paramInt4 > -1)
        while (++paramInt1 <= paramInt4) {
          setSelected(paramInt1, false);
          paramInt1++;
        }  
    } else {
      for (int i = paramInt1; i <= paramInt2; i++)
        setSelected(i, true); 
      if (paramInt4 > -1 && paramInt4 > paramInt2)
        while (++paramInt2 <= paramInt4) {
          if (paramInt2 != paramInt1)
            setSelected(paramInt2, false); 
          paramInt2++;
        }  
      if (paramInt3 > -1)
        while (paramInt3 < paramInt1) {
          setSelected(paramInt3, false);
          paramInt3++;
        }  
    } 
    fireSelectionListener();
  }
  
  public void setMaxSelectionCount(int paramInt) {
    this.mMaxSelectionCount = paramInt;
  }
  
  public final void setSelected(int paramInt, boolean paramBoolean) {
    if (!isIndexSelectable(paramInt))
      paramBoolean = false; 
    if (paramBoolean) {
      if (!this.mSelectedIndices.contains(Integer.valueOf(paramInt)) && (this.mMaxSelectionCount == -1 || this.mSelectedIndices.size() < this.mMaxSelectionCount)) {
        this.mSelectedIndices.add(Integer.valueOf(paramInt));
        notifyItemChanged(paramInt);
      } 
    } else if (this.mSelectedIndices.contains(Integer.valueOf(paramInt))) {
      this.mSelectedIndices.remove(Integer.valueOf(paramInt));
      notifyItemChanged(paramInt);
    } 
    fireSelectionListener();
  }
  
  public void setSelectionListener(SelectionListener paramSelectionListener) {
    this.mSelectionListener = paramSelectionListener;
  }
  
  public final boolean toggleSelected(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: iload_1
    //   2: invokevirtual isIndexSelectable : (I)Z
    //   5: istore_2
    //   6: iconst_0
    //   7: istore_3
    //   8: iconst_0
    //   9: istore #4
    //   11: iload_2
    //   12: ifeq -> 91
    //   15: aload_0
    //   16: getfield mSelectedIndices : Ljava/util/ArrayList;
    //   19: iload_1
    //   20: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   23: invokevirtual contains : (Ljava/lang/Object;)Z
    //   26: ifeq -> 47
    //   29: aload_0
    //   30: getfield mSelectedIndices : Ljava/util/ArrayList;
    //   33: iload_1
    //   34: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   37: invokevirtual remove : (Ljava/lang/Object;)Z
    //   40: pop
    //   41: iload #4
    //   43: istore_3
    //   44: goto -> 86
    //   47: aload_0
    //   48: getfield mMaxSelectionCount : I
    //   51: iconst_m1
    //   52: if_icmpeq -> 72
    //   55: iload #4
    //   57: istore_3
    //   58: aload_0
    //   59: getfield mSelectedIndices : Ljava/util/ArrayList;
    //   62: invokevirtual size : ()I
    //   65: aload_0
    //   66: getfield mMaxSelectionCount : I
    //   69: if_icmpge -> 86
    //   72: aload_0
    //   73: getfield mSelectedIndices : Ljava/util/ArrayList;
    //   76: iload_1
    //   77: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   80: invokevirtual add : (Ljava/lang/Object;)Z
    //   83: pop
    //   84: iconst_1
    //   85: istore_3
    //   86: aload_0
    //   87: iload_1
    //   88: invokevirtual notifyItemChanged : (I)V
    //   91: aload_0
    //   92: invokespecial fireSelectionListener : ()V
    //   95: iload_3
    //   96: ireturn
  }
  
  public static interface SelectionListener {
    void onDragSelectionChanged(int param1Int);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\DragSelectRecyclerViewAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */