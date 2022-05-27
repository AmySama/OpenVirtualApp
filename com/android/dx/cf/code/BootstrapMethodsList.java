package com.android.dx.cf.code;

import com.android.dx.rop.cst.CstMethodHandle;
import com.android.dx.rop.cst.CstType;
import com.android.dx.util.FixedSizeList;

public class BootstrapMethodsList extends FixedSizeList {
  public static final BootstrapMethodsList EMPTY = new BootstrapMethodsList(0);
  
  public BootstrapMethodsList(int paramInt) {
    super(paramInt);
  }
  
  public static BootstrapMethodsList concat(BootstrapMethodsList paramBootstrapMethodsList1, BootstrapMethodsList paramBootstrapMethodsList2) {
    byte b3;
    BootstrapMethodsList bootstrapMethodsList = EMPTY;
    if (paramBootstrapMethodsList1 == bootstrapMethodsList)
      return paramBootstrapMethodsList2; 
    if (paramBootstrapMethodsList2 == bootstrapMethodsList)
      return paramBootstrapMethodsList1; 
    int i = paramBootstrapMethodsList1.size();
    int j = paramBootstrapMethodsList2.size();
    bootstrapMethodsList = new BootstrapMethodsList(i + j);
    byte b1 = 0;
    byte b2 = 0;
    while (true) {
      b3 = b1;
      if (b2 < i) {
        bootstrapMethodsList.set(b2, paramBootstrapMethodsList1.get(b2));
        b2++;
        continue;
      } 
      break;
    } 
    while (b3 < j) {
      bootstrapMethodsList.set(i + b3, paramBootstrapMethodsList2.get(b3));
      b3++;
    } 
    return bootstrapMethodsList;
  }
  
  public Item get(int paramInt) {
    return (Item)get0(paramInt);
  }
  
  public void set(int paramInt, Item paramItem) {
    if (paramItem != null) {
      set0(paramInt, paramItem);
      return;
    } 
    throw new NullPointerException("item == null");
  }
  
  public void set(int paramInt, CstType paramCstType, CstMethodHandle paramCstMethodHandle, BootstrapMethodArgumentsList paramBootstrapMethodArgumentsList) {
    set(paramInt, new Item(paramCstType, paramCstMethodHandle, paramBootstrapMethodArgumentsList));
  }
  
  public static class Item {
    private final BootstrapMethodArgumentsList bootstrapMethodArgumentsList;
    
    private final CstMethodHandle bootstrapMethodHandle;
    
    private final CstType declaringClass;
    
    public Item(CstType param1CstType, CstMethodHandle param1CstMethodHandle, BootstrapMethodArgumentsList param1BootstrapMethodArgumentsList) {
      if (param1CstType != null) {
        if (param1CstMethodHandle != null) {
          if (param1BootstrapMethodArgumentsList != null) {
            this.bootstrapMethodHandle = param1CstMethodHandle;
            this.bootstrapMethodArgumentsList = param1BootstrapMethodArgumentsList;
            this.declaringClass = param1CstType;
            return;
          } 
          throw new NullPointerException("bootstrapMethodArguments == null");
        } 
        throw new NullPointerException("bootstrapMethodHandle == null");
      } 
      throw new NullPointerException("declaringClass == null");
    }
    
    public BootstrapMethodArgumentsList getBootstrapMethodArguments() {
      return this.bootstrapMethodArgumentsList;
    }
    
    public CstMethodHandle getBootstrapMethodHandle() {
      return this.bootstrapMethodHandle;
    }
    
    public CstType getDeclaringClass() {
      return this.declaringClass;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\BootstrapMethodsList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */