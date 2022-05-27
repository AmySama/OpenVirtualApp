package com.android.dx.dex.code;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstType;
import com.android.dx.util.FixedSizeList;
import com.android.dx.util.Hex;

public final class CatchHandlerList extends FixedSizeList implements Comparable<CatchHandlerList> {
  public static final CatchHandlerList EMPTY = new CatchHandlerList(0);
  
  public CatchHandlerList(int paramInt) {
    super(paramInt);
  }
  
  public boolean catchesAll() {
    int i = size();
    return (i == 0) ? false : get(i - 1).getExceptionType().equals(CstType.OBJECT);
  }
  
  public int compareTo(CatchHandlerList paramCatchHandlerList) {
    if (this == paramCatchHandlerList)
      return 0; 
    int i = size();
    int j = paramCatchHandlerList.size();
    int k = Math.min(i, j);
    for (byte b = 0; b < k; b++) {
      int m = get(b).compareTo(paramCatchHandlerList.get(b));
      if (m != 0)
        return m; 
    } 
    return (i < j) ? -1 : ((i > j) ? 1 : 0);
  }
  
  public Entry get(int paramInt) {
    return (Entry)get0(paramInt);
  }
  
  public void set(int paramInt, Entry paramEntry) {
    set0(paramInt, paramEntry);
  }
  
  public void set(int paramInt1, CstType paramCstType, int paramInt2) {
    set0(paramInt1, new Entry(paramCstType, paramInt2));
  }
  
  public String toHuman() {
    return toHuman("", "");
  }
  
  public String toHuman(String paramString1, String paramString2) {
    StringBuilder stringBuilder = new StringBuilder(100);
    int i = size();
    stringBuilder.append(paramString1);
    stringBuilder.append(paramString2);
    stringBuilder.append("catch ");
    for (byte b = 0; b < i; b++) {
      Entry entry = get(b);
      if (b != 0) {
        stringBuilder.append(",\n");
        stringBuilder.append(paramString1);
        stringBuilder.append("  ");
      } 
      if (b == i - 1 && catchesAll()) {
        stringBuilder.append("<any>");
      } else {
        stringBuilder.append(entry.getExceptionType().toHuman());
      } 
      stringBuilder.append(" -> ");
      stringBuilder.append(Hex.u2or4(entry.getHandler()));
    } 
    return stringBuilder.toString();
  }
  
  public static class Entry implements Comparable<Entry> {
    private final CstType exceptionType;
    
    private final int handler;
    
    public Entry(CstType param1CstType, int param1Int) {
      if (param1Int >= 0) {
        if (param1CstType != null) {
          this.handler = param1Int;
          this.exceptionType = param1CstType;
          return;
        } 
        throw new NullPointerException("exceptionType == null");
      } 
      throw new IllegalArgumentException("handler < 0");
    }
    
    public int compareTo(Entry param1Entry) {
      int i = this.handler;
      int j = param1Entry.handler;
      return (i < j) ? -1 : ((i > j) ? 1 : this.exceptionType.compareTo((Constant)param1Entry.exceptionType));
    }
    
    public boolean equals(Object param1Object) {
      boolean bool = param1Object instanceof Entry;
      boolean bool1 = false;
      boolean bool2 = bool1;
      if (bool) {
        bool2 = bool1;
        if (compareTo((Entry)param1Object) == 0)
          bool2 = true; 
      } 
      return bool2;
    }
    
    public CstType getExceptionType() {
      return this.exceptionType;
    }
    
    public int getHandler() {
      return this.handler;
    }
    
    public int hashCode() {
      return this.handler * 31 + this.exceptionType.hashCode();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\CatchHandlerList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */