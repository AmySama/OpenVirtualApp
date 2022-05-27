package com.android.dx.util;

import java.util.Arrays;

public class LabeledList extends FixedSizeList {
  private final IntList labelToIndex;
  
  public LabeledList(int paramInt) {
    super(paramInt);
    this.labelToIndex = new IntList(paramInt);
  }
  
  public LabeledList(LabeledList paramLabeledList) {
    super(paramLabeledList.size());
    this.labelToIndex = paramLabeledList.labelToIndex.mutableCopy();
    int i = paramLabeledList.size();
    for (byte b = 0; b < i; b++) {
      Object object = paramLabeledList.get0(b);
      if (object != null)
        set0(b, object); 
    } 
  }
  
  private void addLabelIndex(int paramInt1, int paramInt2) {
    int i = this.labelToIndex.size();
    for (byte b = 0; b <= paramInt1 - i; b++)
      this.labelToIndex.add(-1); 
    this.labelToIndex.set(paramInt1, paramInt2);
  }
  
  private void rebuildLabelToIndex() {
    int i = size();
    for (byte b = 0; b < i; b++) {
      LabeledItem labeledItem = (LabeledItem)get0(b);
      if (labeledItem != null)
        this.labelToIndex.set(labeledItem.getLabel(), b); 
    } 
  }
  
  private void removeLabel(int paramInt) {
    this.labelToIndex.set(paramInt, -1);
  }
  
  public final int[] getLabelsInOrder() {
    int i = size();
    int[] arrayOfInt = new int[i];
    byte b = 0;
    while (b < i) {
      LabeledItem labeledItem = (LabeledItem)get0(b);
      if (labeledItem != null) {
        arrayOfInt[b] = labeledItem.getLabel();
        b++;
        continue;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("null at index ");
      stringBuilder.append(b);
      throw new NullPointerException(stringBuilder.toString());
    } 
    Arrays.sort(arrayOfInt);
    return arrayOfInt;
  }
  
  public final int getMaxLabel() {
    int i;
    for (i = this.labelToIndex.size() - 1; i >= 0 && this.labelToIndex.get(i) < 0; i--);
    this.labelToIndex.shrink(++i);
    return i;
  }
  
  public final int indexOfLabel(int paramInt) {
    return (paramInt >= this.labelToIndex.size()) ? -1 : this.labelToIndex.get(paramInt);
  }
  
  protected void set(int paramInt, LabeledItem paramLabeledItem) {
    LabeledItem labeledItem = (LabeledItem)getOrNull0(paramInt);
    set0(paramInt, paramLabeledItem);
    if (labeledItem != null)
      removeLabel(labeledItem.getLabel()); 
    if (paramLabeledItem != null)
      addLabelIndex(paramLabeledItem.getLabel(), paramInt); 
  }
  
  public void shrinkToFit() {
    super.shrinkToFit();
    rebuildLabelToIndex();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\d\\util\LabeledList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */