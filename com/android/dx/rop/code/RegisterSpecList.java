package com.android.dx.rop.code;

import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;
import com.android.dx.util.FixedSizeList;
import java.util.BitSet;

public final class RegisterSpecList extends FixedSizeList implements TypeList {
  public static final RegisterSpecList EMPTY = new RegisterSpecList(0);
  
  public RegisterSpecList(int paramInt) {
    super(paramInt);
  }
  
  public static RegisterSpecList make(RegisterSpec paramRegisterSpec) {
    RegisterSpecList registerSpecList = new RegisterSpecList(1);
    registerSpecList.set(0, paramRegisterSpec);
    return registerSpecList;
  }
  
  public static RegisterSpecList make(RegisterSpec paramRegisterSpec1, RegisterSpec paramRegisterSpec2) {
    RegisterSpecList registerSpecList = new RegisterSpecList(2);
    registerSpecList.set(0, paramRegisterSpec1);
    registerSpecList.set(1, paramRegisterSpec2);
    return registerSpecList;
  }
  
  public static RegisterSpecList make(RegisterSpec paramRegisterSpec1, RegisterSpec paramRegisterSpec2, RegisterSpec paramRegisterSpec3) {
    RegisterSpecList registerSpecList = new RegisterSpecList(3);
    registerSpecList.set(0, paramRegisterSpec1);
    registerSpecList.set(1, paramRegisterSpec2);
    registerSpecList.set(2, paramRegisterSpec3);
    return registerSpecList;
  }
  
  public static RegisterSpecList make(RegisterSpec paramRegisterSpec1, RegisterSpec paramRegisterSpec2, RegisterSpec paramRegisterSpec3, RegisterSpec paramRegisterSpec4) {
    RegisterSpecList registerSpecList = new RegisterSpecList(4);
    registerSpecList.set(0, paramRegisterSpec1);
    registerSpecList.set(1, paramRegisterSpec2);
    registerSpecList.set(2, paramRegisterSpec3);
    registerSpecList.set(3, paramRegisterSpec4);
    return registerSpecList;
  }
  
  public RegisterSpec get(int paramInt) {
    return (RegisterSpec)get0(paramInt);
  }
  
  public int getRegistersSize() {
    int i = size();
    byte b = 0;
    int j;
    for (j = 0; b < i; j = k) {
      RegisterSpec registerSpec = (RegisterSpec)get0(b);
      int k = j;
      if (registerSpec != null) {
        int m = registerSpec.getNextReg();
        k = j;
        if (m > j)
          k = m; 
      } 
      b++;
    } 
    return j;
  }
  
  public Type getType(int paramInt) {
    return get(paramInt).getType().getType();
  }
  
  public int getWordCount() {
    int i = size();
    byte b = 0;
    int j = 0;
    while (b < i) {
      j += getType(b).getCategory();
      b++;
    } 
    return j;
  }
  
  public int indexOfRegister(int paramInt) {
    int i = size();
    for (byte b = 0; b < i; b++) {
      if (get(b).getReg() == paramInt)
        return b; 
    } 
    return -1;
  }
  
  public void set(int paramInt, RegisterSpec paramRegisterSpec) {
    set0(paramInt, paramRegisterSpec);
  }
  
  public RegisterSpec specForRegister(int paramInt) {
    int i = size();
    for (byte b = 0; b < i; b++) {
      RegisterSpec registerSpec = get(b);
      if (registerSpec.getReg() == paramInt)
        return registerSpec; 
    } 
    return null;
  }
  
  public RegisterSpecList subset(BitSet paramBitSet) {
    int i = size() - paramBitSet.cardinality();
    if (i == 0)
      return EMPTY; 
    RegisterSpecList registerSpecList = new RegisterSpecList(i);
    i = 0;
    int j;
    for (j = 0; i < size(); j = k) {
      int k = j;
      if (!paramBitSet.get(i)) {
        registerSpecList.set0(j, get0(i));
        k = j + 1;
      } 
      i++;
    } 
    if (isImmutable())
      registerSpecList.setImmutable(); 
    return registerSpecList;
  }
  
  public TypeList withAddedType(Type paramType) {
    throw new UnsupportedOperationException("unsupported");
  }
  
  public RegisterSpecList withExpandedRegisters(int paramInt, boolean paramBoolean, BitSet paramBitSet) {
    int i = size();
    if (i == 0)
      return this; 
    Expander expander = new Expander(this, paramBitSet, paramInt, paramBoolean);
    for (paramInt = 0; paramInt < i; paramInt++)
      expander.expandRegister(paramInt); 
    return expander.getResult();
  }
  
  public RegisterSpecList withFirst(RegisterSpec paramRegisterSpec) {
    int i = size();
    RegisterSpecList registerSpecList = new RegisterSpecList(i + 1);
    int j;
    for (j = 0; j < i; j = k) {
      int k = j + 1;
      registerSpecList.set0(k, get0(j));
    } 
    registerSpecList.set0(0, paramRegisterSpec);
    if (isImmutable())
      registerSpecList.setImmutable(); 
    return registerSpecList;
  }
  
  public RegisterSpecList withOffset(int paramInt) {
    int i = size();
    if (i == 0)
      return this; 
    RegisterSpecList registerSpecList = new RegisterSpecList(i);
    for (byte b = 0; b < i; b++) {
      RegisterSpec registerSpec = (RegisterSpec)get0(b);
      if (registerSpec != null)
        registerSpecList.set0(b, registerSpec.withOffset(paramInt)); 
    } 
    if (isImmutable())
      registerSpecList.setImmutable(); 
    return registerSpecList;
  }
  
  public RegisterSpecList withoutFirst() {
    int i = size() - 1;
    if (i == 0)
      return EMPTY; 
    RegisterSpecList registerSpecList = new RegisterSpecList(i);
    for (int j = 0; j < i; j = k) {
      int k = j + 1;
      registerSpecList.set0(j, get0(k));
    } 
    if (isImmutable())
      registerSpecList.setImmutable(); 
    return registerSpecList;
  }
  
  public RegisterSpecList withoutLast() {
    int i = size() - 1;
    if (i == 0)
      return EMPTY; 
    RegisterSpecList registerSpecList = new RegisterSpecList(i);
    for (byte b = 0; b < i; b++)
      registerSpecList.set0(b, get0(b)); 
    if (isImmutable())
      registerSpecList.setImmutable(); 
    return registerSpecList;
  }
  
  private static class Expander {
    private int base;
    
    private final BitSet compatRegs;
    
    private boolean duplicateFirst;
    
    private final RegisterSpecList regSpecList;
    
    private final RegisterSpecList result;
    
    private Expander(RegisterSpecList param1RegisterSpecList, BitSet param1BitSet, int param1Int, boolean param1Boolean) {
      this.regSpecList = param1RegisterSpecList;
      this.compatRegs = param1BitSet;
      this.base = param1Int;
      this.result = new RegisterSpecList(param1RegisterSpecList.size());
      this.duplicateFirst = param1Boolean;
    }
    
    private void expandRegister(int param1Int) {
      expandRegister(param1Int, (RegisterSpec)this.regSpecList.get0(param1Int));
    }
    
    private void expandRegister(int param1Int, RegisterSpec param1RegisterSpec) {
      BitSet bitSet = this.compatRegs;
      boolean bool = true;
      if (bitSet != null && bitSet.get(param1Int))
        bool = false; 
      RegisterSpec registerSpec = param1RegisterSpec;
      if (bool) {
        param1RegisterSpec = param1RegisterSpec.withReg(this.base);
        registerSpec = param1RegisterSpec;
        if (!this.duplicateFirst) {
          this.base += param1RegisterSpec.getCategory();
          registerSpec = param1RegisterSpec;
        } 
      } 
      this.duplicateFirst = false;
      this.result.set0(param1Int, registerSpec);
    }
    
    private RegisterSpecList getResult() {
      if (this.regSpecList.isImmutable())
        this.result.setImmutable(); 
      return this.result;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\RegisterSpecList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */