package com.android.dx.cf.code;

import com.android.dx.rop.code.LocalItem;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.type.Type;
import com.android.dx.util.FixedSizeList;

public final class LocalVariableList extends FixedSizeList {
  public static final LocalVariableList EMPTY = new LocalVariableList(0);
  
  public LocalVariableList(int paramInt) {
    super(paramInt);
  }
  
  public static LocalVariableList concat(LocalVariableList paramLocalVariableList1, LocalVariableList paramLocalVariableList2) {
    byte b3;
    if (paramLocalVariableList1 == EMPTY)
      return paramLocalVariableList2; 
    int i = paramLocalVariableList1.size();
    int j = paramLocalVariableList2.size();
    LocalVariableList localVariableList = new LocalVariableList(i + j);
    byte b1 = 0;
    byte b2 = 0;
    while (true) {
      b3 = b1;
      if (b2 < i) {
        localVariableList.set(b2, paramLocalVariableList1.get(b2));
        b2++;
        continue;
      } 
      break;
    } 
    while (b3 < j) {
      localVariableList.set(i + b3, paramLocalVariableList2.get(b3));
      b3++;
    } 
    localVariableList.setImmutable();
    return localVariableList;
  }
  
  public static LocalVariableList mergeDescriptorsAndSignatures(LocalVariableList paramLocalVariableList1, LocalVariableList paramLocalVariableList2) {
    int i = paramLocalVariableList1.size();
    LocalVariableList localVariableList = new LocalVariableList(i);
    for (byte b = 0; b < i; b++) {
      Item item1 = paramLocalVariableList1.get(b);
      Item item2 = paramLocalVariableList2.itemToLocal(item1);
      Item item3 = item1;
      if (item2 != null)
        item3 = item1.withSignature(item2.getSignature()); 
      localVariableList.set(b, item3);
    } 
    localVariableList.setImmutable();
    return localVariableList;
  }
  
  public Item get(int paramInt) {
    return (Item)get0(paramInt);
  }
  
  public Item itemToLocal(Item paramItem) {
    int i = size();
    for (byte b = 0; b < i; b++) {
      Item item = (Item)get0(b);
      if (item != null && item.matchesAllButType(paramItem))
        return item; 
    } 
    return null;
  }
  
  public Item pcAndIndexToLocal(int paramInt1, int paramInt2) {
    int i = size();
    for (byte b = 0; b < i; b++) {
      Item item = (Item)get0(b);
      if (item != null && item.matchesPcAndIndex(paramInt1, paramInt2))
        return item; 
    } 
    return null;
  }
  
  public void set(int paramInt1, int paramInt2, int paramInt3, CstString paramCstString1, CstString paramCstString2, CstString paramCstString3, int paramInt4) {
    set0(paramInt1, new Item(paramInt2, paramInt3, paramCstString1, paramCstString2, paramCstString3, paramInt4));
  }
  
  public void set(int paramInt, Item paramItem) {
    if (paramItem != null) {
      set0(paramInt, paramItem);
      return;
    } 
    throw new NullPointerException("item == null");
  }
  
  public static class Item {
    private final CstString descriptor;
    
    private final int index;
    
    private final int length;
    
    private final CstString name;
    
    private final CstString signature;
    
    private final int startPc;
    
    public Item(int param1Int1, int param1Int2, CstString param1CstString1, CstString param1CstString2, CstString param1CstString3, int param1Int3) {
      if (param1Int1 >= 0) {
        if (param1Int2 >= 0) {
          if (param1CstString1 != null) {
            if (param1CstString2 != null || param1CstString3 != null) {
              if (param1Int3 >= 0) {
                this.startPc = param1Int1;
                this.length = param1Int2;
                this.name = param1CstString1;
                this.descriptor = param1CstString2;
                this.signature = param1CstString3;
                this.index = param1Int3;
                return;
              } 
              throw new IllegalArgumentException("index < 0");
            } 
            throw new NullPointerException("(descriptor == null) && (signature == null)");
          } 
          throw new NullPointerException("name == null");
        } 
        throw new IllegalArgumentException("length < 0");
      } 
      throw new IllegalArgumentException("startPc < 0");
    }
    
    private CstString getSignature() {
      return this.signature;
    }
    
    public CstString getDescriptor() {
      return this.descriptor;
    }
    
    public int getIndex() {
      return this.index;
    }
    
    public int getLength() {
      return this.length;
    }
    
    public LocalItem getLocalItem() {
      return LocalItem.make(this.name, this.signature);
    }
    
    public int getStartPc() {
      return this.startPc;
    }
    
    public Type getType() {
      return Type.intern(this.descriptor.getString());
    }
    
    public boolean matchesAllButType(Item param1Item) {
      boolean bool;
      if (this.startPc == param1Item.startPc && this.length == param1Item.length && this.index == param1Item.index && this.name.equals(param1Item.name)) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public boolean matchesPcAndIndex(int param1Int1, int param1Int2) {
      if (param1Int2 == this.index) {
        param1Int2 = this.startPc;
        if (param1Int1 >= param1Int2 && param1Int1 < param1Int2 + this.length)
          return true; 
      } 
      return false;
    }
    
    public Item withSignature(CstString param1CstString) {
      return new Item(this.startPc, this.length, this.name, this.descriptor, param1CstString, this.index);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\LocalVariableList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */