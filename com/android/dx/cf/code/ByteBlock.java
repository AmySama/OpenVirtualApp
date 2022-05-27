package com.android.dx.cf.code;

import com.android.dx.util.Hex;
import com.android.dx.util.IntList;
import com.android.dx.util.LabeledItem;

public final class ByteBlock implements LabeledItem {
  private final ByteCatchList catches;
  
  private final int end;
  
  private final int label;
  
  private final int start;
  
  private final IntList successors;
  
  public ByteBlock(int paramInt1, int paramInt2, int paramInt3, IntList paramIntList, ByteCatchList paramByteCatchList) {
    if (paramInt1 >= 0) {
      if (paramInt2 >= 0) {
        if (paramInt3 > paramInt2) {
          if (paramIntList != null) {
            StringBuilder stringBuilder;
            int i = paramIntList.size();
            byte b = 0;
            while (b < i) {
              if (paramIntList.get(b) >= 0) {
                b++;
                continue;
              } 
              stringBuilder = new StringBuilder();
              stringBuilder.append("successors[");
              stringBuilder.append(b);
              stringBuilder.append("] == ");
              stringBuilder.append(paramIntList.get(b));
              throw new IllegalArgumentException(stringBuilder.toString());
            } 
            if (stringBuilder != null) {
              this.label = paramInt1;
              this.start = paramInt2;
              this.end = paramInt3;
              this.successors = paramIntList;
              this.catches = (ByteCatchList)stringBuilder;
              return;
            } 
            throw new NullPointerException("catches == null");
          } 
          throw new NullPointerException("targets == null");
        } 
        throw new IllegalArgumentException("end <= start");
      } 
      throw new IllegalArgumentException("start < 0");
    } 
    throw new IllegalArgumentException("label < 0");
  }
  
  public ByteCatchList getCatches() {
    return this.catches;
  }
  
  public int getEnd() {
    return this.end;
  }
  
  public int getLabel() {
    return this.label;
  }
  
  public int getStart() {
    return this.start;
  }
  
  public IntList getSuccessors() {
    return this.successors;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('{');
    stringBuilder.append(Hex.u2(this.label));
    stringBuilder.append(": ");
    stringBuilder.append(Hex.u2(this.start));
    stringBuilder.append("..");
    stringBuilder.append(Hex.u2(this.end));
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\ByteBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */