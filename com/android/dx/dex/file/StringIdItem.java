package com.android.dx.dex.file;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstString;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;

public final class StringIdItem extends IndexedItem implements Comparable {
  private StringDataItem data;
  
  private final CstString value;
  
  public StringIdItem(CstString paramCstString) {
    if (paramCstString != null) {
      this.value = paramCstString;
      this.data = null;
      return;
    } 
    throw new NullPointerException("value == null");
  }
  
  public void addContents(DexFile paramDexFile) {
    if (this.data == null) {
      MixedItemSection mixedItemSection = paramDexFile.getStringData();
      StringDataItem stringDataItem = new StringDataItem(this.value);
      this.data = stringDataItem;
      mixedItemSection.add(stringDataItem);
    } 
  }
  
  public int compareTo(Object paramObject) {
    paramObject = paramObject;
    return this.value.compareTo((Constant)((StringIdItem)paramObject).value);
  }
  
  public boolean equals(Object paramObject) {
    if (!(paramObject instanceof StringIdItem))
      return false; 
    paramObject = paramObject;
    return this.value.equals(((StringIdItem)paramObject).value);
  }
  
  public StringDataItem getData() {
    return this.data;
  }
  
  public CstString getValue() {
    return this.value;
  }
  
  public int hashCode() {
    return this.value.hashCode();
  }
  
  public ItemType itemType() {
    return ItemType.TYPE_STRING_ID_ITEM;
  }
  
  public int writeSize() {
    return 4;
  }
  
  public void writeTo(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    int i = this.data.getAbsoluteOffset();
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(indexString());
      stringBuilder.append(' ');
      stringBuilder.append(this.value.toQuoted(100));
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  string_data_off: ");
      stringBuilder.append(Hex.u4(i));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
    } 
    paramAnnotatedOutput.writeInt(i);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\StringIdItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */