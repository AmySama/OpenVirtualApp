package com.android.dx.rop.cst;

import com.android.dx.util.FixedSizeList;

public class CstArray extends Constant {
  private final List list;
  
  public CstArray(List paramList) {
    if (paramList != null) {
      paramList.throwIfMutable();
      this.list = paramList;
      return;
    } 
    throw new NullPointerException("list == null");
  }
  
  protected int compareTo0(Constant paramConstant) {
    return this.list.compareTo(((CstArray)paramConstant).list);
  }
  
  public boolean equals(Object paramObject) {
    return !(paramObject instanceof CstArray) ? false : this.list.equals(((CstArray)paramObject).list);
  }
  
  public List getList() {
    return this.list;
  }
  
  public int hashCode() {
    return this.list.hashCode();
  }
  
  public boolean isCategory2() {
    return false;
  }
  
  public String toHuman() {
    return this.list.toHuman("{", ", ", "}");
  }
  
  public String toString() {
    return this.list.toString("array{", ", ", "}");
  }
  
  public String typeName() {
    return "array";
  }
  
  public static final class List extends FixedSizeList implements Comparable<List> {
    public List(int param1Int) {
      super(param1Int);
    }
    
    public int compareTo(List param1List) {
      int k;
      int i = size();
      int j = param1List.size();
      if (i < j) {
        k = i;
      } else {
        k = j;
      } 
      for (byte b = 0; b < k; b++) {
        int m = ((Constant)get0(b)).compareTo((Constant)param1List.get0(b));
        if (m != 0)
          return m; 
      } 
      return (i < j) ? -1 : ((i > j) ? 1 : 0);
    }
    
    public Constant get(int param1Int) {
      return (Constant)get0(param1Int);
    }
    
    public void set(int param1Int, Constant param1Constant) {
      set0(param1Int, param1Constant);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */