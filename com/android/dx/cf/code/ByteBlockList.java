package com.android.dx.cf.code;

import com.android.dx.util.Hex;
import com.android.dx.util.LabeledList;

public final class ByteBlockList extends LabeledList {
  public ByteBlockList(int paramInt) {
    super(paramInt);
  }
  
  public ByteBlock get(int paramInt) {
    return (ByteBlock)get0(paramInt);
  }
  
  public ByteBlock labelToBlock(int paramInt) {
    int i = indexOfLabel(paramInt);
    if (i >= 0)
      return get(i); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("no such label: ");
    stringBuilder.append(Hex.u2(paramInt));
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void set(int paramInt, ByteBlock paramByteBlock) {
    set(paramInt, paramByteBlock);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\ByteBlockList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */