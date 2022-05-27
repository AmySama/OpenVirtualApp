package com.android.dx.dex.code;

import com.android.dx.util.FixedSizeList;

public final class CatchTable extends FixedSizeList implements Comparable<CatchTable> {
  public static final CatchTable EMPTY = new CatchTable(0);
  
  public CatchTable(int paramInt) {
    super(paramInt);
  }
  
  public int compareTo(CatchTable paramCatchTable) {
    if (this == paramCatchTable)
      return 0; 
    int i = size();
    int j = paramCatchTable.size();
    int k = Math.min(i, j);
    for (byte b = 0; b < k; b++) {
      int m = get(b).compareTo(paramCatchTable.get(b));
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
  
  public static class Entry implements Comparable<Entry> {
    private final int end;
    
    private final CatchHandlerList handlers;
    
    private final int start;
    
    public Entry(int param1Int1, int param1Int2, CatchHandlerList param1CatchHandlerList) {
      if (param1Int1 >= 0) {
        if (param1Int2 > param1Int1) {
          if (!param1CatchHandlerList.isMutable()) {
            this.start = param1Int1;
            this.end = param1Int2;
            this.handlers = param1CatchHandlerList;
            return;
          } 
          throw new IllegalArgumentException("handlers.isMutable()");
        } 
        throw new IllegalArgumentException("end <= start");
      } 
      throw new IllegalArgumentException("start < 0");
    }
    
    public int compareTo(Entry param1Entry) {
      int i = this.start;
      int j = param1Entry.start;
      if (i < j)
        return -1; 
      if (i > j)
        return 1; 
      i = this.end;
      j = param1Entry.end;
      return (i < j) ? -1 : ((i > j) ? 1 : this.handlers.compareTo(param1Entry.handlers));
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
    
    public int getEnd() {
      return this.end;
    }
    
    public CatchHandlerList getHandlers() {
      return this.handlers;
    }
    
    public int getStart() {
      return this.start;
    }
    
    public int hashCode() {
      return (this.start * 31 + this.end) * 31 + this.handlers.hashCode();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\CatchTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */