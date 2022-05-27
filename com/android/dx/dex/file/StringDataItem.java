package com.android.dx.dex.file;

import com.android.dex.Leb128;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstString;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.ByteArray;
import com.android.dx.util.Hex;

public final class StringDataItem extends OffsettedItem {
  private final CstString value;
  
  public StringDataItem(CstString paramCstString) {
    super(1, writeSize(paramCstString));
    this.value = paramCstString;
  }
  
  private static int writeSize(CstString paramCstString) {
    return Leb128.unsignedLeb128Size(paramCstString.getUtf16Size()) + paramCstString.getUtf8Size() + 1;
  }
  
  public void addContents(DexFile paramDexFile) {}
  
  protected int compareTo0(OffsettedItem paramOffsettedItem) {
    paramOffsettedItem = paramOffsettedItem;
    return this.value.compareTo((Constant)((StringDataItem)paramOffsettedItem).value);
  }
  
  public ItemType itemType() {
    return ItemType.TYPE_STRING_DATA_ITEM;
  }
  
  public String toHuman() {
    return this.value.toQuoted();
  }
  
  public void writeTo0(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    ByteArray byteArray = this.value.getBytes();
    int i = this.value.getUtf16Size();
    if (paramAnnotatedOutput.annotates()) {
      int j = Leb128.unsignedLeb128Size(i);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("utf16_size: ");
      stringBuilder.append(Hex.u4(i));
      paramAnnotatedOutput.annotate(j, stringBuilder.toString());
      paramAnnotatedOutput.annotate(byteArray.size() + 1, this.value.toQuoted());
    } 
    paramAnnotatedOutput.writeUleb128(i);
    paramAnnotatedOutput.write(byteArray);
    paramAnnotatedOutput.writeByte(0);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\StringDataItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */