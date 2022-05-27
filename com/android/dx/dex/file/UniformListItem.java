package com.android.dx.dex.file;

import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.util.Iterator;
import java.util.List;

public final class UniformListItem<T extends OffsettedItem> extends OffsettedItem {
  private static final int HEADER_SIZE = 4;
  
  private final ItemType itemType;
  
  private final List<T> items;
  
  public UniformListItem(ItemType paramItemType, List<T> paramList) {
    super(getAlignment(paramList), writeSize(paramList));
    if (paramItemType != null) {
      this.items = paramList;
      this.itemType = paramItemType;
      return;
    } 
    throw new NullPointerException("itemType == null");
  }
  
  private static int getAlignment(List<? extends OffsettedItem> paramList) {
    try {
      return Math.max(4, ((OffsettedItem)paramList.get(0)).getAlignment());
    } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
      throw new IllegalArgumentException("items.size() == 0");
    } catch (NullPointerException nullPointerException) {
      throw new NullPointerException("items == null");
    } 
  }
  
  private int headerSize() {
    return getAlignment();
  }
  
  private static int writeSize(List<? extends OffsettedItem> paramList) {
    OffsettedItem offsettedItem = paramList.get(0);
    return paramList.size() * offsettedItem.writeSize() + getAlignment(paramList);
  }
  
  public void addContents(DexFile paramDexFile) {
    Iterator<T> iterator = this.items.iterator();
    while (iterator.hasNext())
      ((OffsettedItem)iterator.next()).addContents(paramDexFile); 
  }
  
  public final List<T> getItems() {
    return this.items;
  }
  
  public ItemType itemType() {
    return this.itemType;
  }
  
  protected void place0(Section paramSection, int paramInt) {
    paramInt += headerSize();
    Iterator<T> iterator = this.items.iterator();
    int i = -1;
    int j = -1;
    for (int k = 1; iterator.hasNext(); k = n) {
      int n;
      OffsettedItem offsettedItem = (OffsettedItem)iterator.next();
      int m = offsettedItem.writeSize();
      if (k) {
        k = offsettedItem.getAlignment();
        i = m;
        n = 0;
      } else if (m == i) {
        if (offsettedItem.getAlignment() == j) {
          n = k;
          k = j;
        } else {
          throw new UnsupportedOperationException("item alignment mismatch");
        } 
      } else {
        throw new UnsupportedOperationException("item size mismatch");
      } 
      paramInt = offsettedItem.place(paramSection, paramInt) + m;
      j = k;
    } 
  }
  
  public final String toHuman() {
    StringBuilder stringBuilder = new StringBuilder(100);
    stringBuilder.append("{");
    Iterator<T> iterator = this.items.iterator();
    boolean bool = true;
    while (iterator.hasNext()) {
      OffsettedItem offsettedItem = (OffsettedItem)iterator.next();
      if (bool) {
        bool = false;
      } else {
        stringBuilder.append(", ");
      } 
      stringBuilder.append(offsettedItem.toHuman());
    } 
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(100);
    stringBuilder.append(getClass().getName());
    stringBuilder.append(this.items);
    return stringBuilder.toString();
  }
  
  protected void writeTo0(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    int i = this.items.size();
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(offsetString());
      stringBuilder.append(" ");
      stringBuilder.append(typeName());
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  size: ");
      stringBuilder.append(Hex.u4(i));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
    } 
    paramAnnotatedOutput.writeInt(i);
    Iterator<T> iterator = this.items.iterator();
    while (iterator.hasNext())
      ((OffsettedItem)iterator.next()).writeTo(paramDexFile, paramAnnotatedOutput); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\UniformListItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */