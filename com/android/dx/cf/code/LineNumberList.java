package com.android.dx.cf.code;

import com.android.dx.util.FixedSizeList;

public final class LineNumberList extends FixedSizeList {
  public static final LineNumberList EMPTY = new LineNumberList(0);
  
  public LineNumberList(int paramInt) {
    super(paramInt);
  }
  
  public static LineNumberList concat(LineNumberList paramLineNumberList1, LineNumberList paramLineNumberList2) {
    byte b3;
    if (paramLineNumberList1 == EMPTY)
      return paramLineNumberList2; 
    int i = paramLineNumberList1.size();
    int j = paramLineNumberList2.size();
    LineNumberList lineNumberList = new LineNumberList(i + j);
    byte b1 = 0;
    byte b2 = 0;
    while (true) {
      b3 = b1;
      if (b2 < i) {
        lineNumberList.set(b2, paramLineNumberList1.get(b2));
        b2++;
        continue;
      } 
      break;
    } 
    while (b3 < j) {
      lineNumberList.set(i + b3, paramLineNumberList2.get(b3));
      b3++;
    } 
    return lineNumberList;
  }
  
  public Item get(int paramInt) {
    return (Item)get0(paramInt);
  }
  
  public int pcToLine(int paramInt) {
    int m;
    int i = size();
    int j = -1;
    int k = -1;
    byte b = 0;
    while (true) {
      m = k;
      if (b < i) {
        Item item = get(b);
        int n = item.getStartPc();
        int i1 = j;
        m = k;
        if (n <= paramInt) {
          i1 = j;
          m = k;
          if (n > j) {
            m = item.getLineNumber();
            if (n == paramInt)
              break; 
            i1 = n;
          } 
        } 
        b++;
        j = i1;
        k = m;
        continue;
      } 
      break;
    } 
    return m;
  }
  
  public void set(int paramInt1, int paramInt2, int paramInt3) {
    set0(paramInt1, new Item(paramInt2, paramInt3));
  }
  
  public void set(int paramInt, Item paramItem) {
    if (paramItem != null) {
      set0(paramInt, paramItem);
      return;
    } 
    throw new NullPointerException("item == null");
  }
  
  public static class Item {
    private final int lineNumber;
    
    private final int startPc;
    
    public Item(int param1Int1, int param1Int2) {
      if (param1Int1 >= 0) {
        if (param1Int2 >= 0) {
          this.startPc = param1Int1;
          this.lineNumber = param1Int2;
          return;
        } 
        throw new IllegalArgumentException("lineNumber < 0");
      } 
      throw new IllegalArgumentException("startPc < 0");
    }
    
    public int getLineNumber() {
      return this.lineNumber;
    }
    
    public int getStartPc() {
      return this.startPc;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\LineNumberList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */