package com.android.dx.cf.code;

import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.TypeList;
import com.android.dx.util.FixedSizeList;
import com.android.dx.util.IntList;

public final class ByteCatchList extends FixedSizeList {
  public static final ByteCatchList EMPTY = new ByteCatchList(0);
  
  public ByteCatchList(int paramInt) {
    super(paramInt);
  }
  
  private static boolean typeNotFound(Item paramItem, Item[] paramArrayOfItem, int paramInt) {
    CstType cstType = paramItem.getExceptionClass();
    for (byte b = 0; b < paramInt; b++) {
      CstType cstType1 = paramArrayOfItem[b].getExceptionClass();
      if (cstType1 == cstType || cstType1 == CstType.OBJECT)
        return false; 
    } 
    return true;
  }
  
  public int byteLength() {
    return size() * 8 + 2;
  }
  
  public Item get(int paramInt) {
    return (Item)get0(paramInt);
  }
  
  public ByteCatchList listFor(int paramInt) {
    int i = size();
    Item[] arrayOfItem = new Item[i];
    boolean bool = false;
    byte b = 0;
    int j;
    for (j = 0; b < i; j = k) {
      Item item = get(b);
      int k = j;
      if (item.covers(paramInt)) {
        k = j;
        if (typeNotFound(item, arrayOfItem, j)) {
          arrayOfItem[j] = item;
          k = j + 1;
        } 
      } 
      b++;
    } 
    if (j == 0)
      return EMPTY; 
    ByteCatchList byteCatchList = new ByteCatchList(j);
    for (paramInt = bool; paramInt < j; paramInt++)
      byteCatchList.set(paramInt, arrayOfItem[paramInt]); 
    byteCatchList.setImmutable();
    return byteCatchList;
  }
  
  public void set(int paramInt1, int paramInt2, int paramInt3, int paramInt4, CstType paramCstType) {
    set0(paramInt1, new Item(paramInt2, paramInt3, paramInt4, paramCstType));
  }
  
  public void set(int paramInt, Item paramItem) {
    if (paramItem != null) {
      set0(paramInt, paramItem);
      return;
    } 
    throw new NullPointerException("item == null");
  }
  
  public TypeList toRopCatchList() {
    int i = size();
    if (i == 0)
      return (TypeList)StdTypeList.EMPTY; 
    StdTypeList stdTypeList = new StdTypeList(i);
    for (byte b = 0; b < i; b++)
      stdTypeList.set(b, get(b).getExceptionClass().getClassType()); 
    stdTypeList.setImmutable();
    return (TypeList)stdTypeList;
  }
  
  public IntList toTargetList(int paramInt) {
    if (paramInt >= -1) {
      byte b2;
      byte b1 = 0;
      if (paramInt >= 0) {
        b2 = 1;
      } else {
        b2 = 0;
      } 
      int i = size();
      if (i == 0)
        return b2 ? IntList.makeImmutable(paramInt) : IntList.EMPTY; 
      IntList intList = new IntList(i + b2);
      while (b1 < i) {
        intList.add(get(b1).getHandlerPc());
        b1++;
      } 
      if (b2 != 0)
        intList.add(paramInt); 
      intList.setImmutable();
      return intList;
    } 
    throw new IllegalArgumentException("noException < -1");
  }
  
  public static class Item {
    private final int endPc;
    
    private final CstType exceptionClass;
    
    private final int handlerPc;
    
    private final int startPc;
    
    public Item(int param1Int1, int param1Int2, int param1Int3, CstType param1CstType) {
      if (param1Int1 >= 0) {
        if (param1Int2 >= param1Int1) {
          if (param1Int3 >= 0) {
            this.startPc = param1Int1;
            this.endPc = param1Int2;
            this.handlerPc = param1Int3;
            this.exceptionClass = param1CstType;
            return;
          } 
          throw new IllegalArgumentException("handlerPc < 0");
        } 
        throw new IllegalArgumentException("endPc < startPc");
      } 
      throw new IllegalArgumentException("startPc < 0");
    }
    
    public boolean covers(int param1Int) {
      boolean bool;
      if (param1Int >= this.startPc && param1Int < this.endPc) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public int getEndPc() {
      return this.endPc;
    }
    
    public CstType getExceptionClass() {
      CstType cstType = this.exceptionClass;
      if (cstType == null)
        cstType = CstType.OBJECT; 
      return cstType;
    }
    
    public int getHandlerPc() {
      return this.handlerPc;
    }
    
    public int getStartPc() {
      return this.startPc;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\ByteCatchList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */