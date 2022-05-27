package com.android.dx.rop.code;

import com.android.dx.util.MutabilityControl;

public final class RegisterSpecSet extends MutabilityControl {
  public static final RegisterSpecSet EMPTY = new RegisterSpecSet(0);
  
  private int size;
  
  private final RegisterSpec[] specs;
  
  public RegisterSpecSet(int paramInt) {
    super(bool);
    boolean bool;
    this.specs = new RegisterSpec[paramInt];
    this.size = 0;
  }
  
  public boolean equals(Object paramObject) {
    if (!(paramObject instanceof RegisterSpecSet))
      return false; 
    RegisterSpecSet registerSpecSet = (RegisterSpecSet)paramObject;
    paramObject = registerSpecSet.specs;
    int i = this.specs.length;
    if (i != paramObject.length || size() != registerSpecSet.size())
      return false; 
    for (byte b = 0; b < i; b++) {
      RegisterSpec registerSpec = this.specs[b];
      Object object = paramObject[b];
      if (registerSpec != object && (registerSpec == null || !registerSpec.equals(object)))
        return false; 
    } 
    return true;
  }
  
  public RegisterSpec findMatchingLocal(RegisterSpec paramRegisterSpec) {
    int i = this.specs.length;
    for (byte b = 0; b < i; b++) {
      RegisterSpec registerSpec = this.specs[b];
      if (registerSpec != null && paramRegisterSpec.matchesVariable(registerSpec))
        return registerSpec; 
    } 
    return null;
  }
  
  public RegisterSpec get(int paramInt) {
    try {
      return this.specs[paramInt];
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      throw new IllegalArgumentException("bogus reg");
    } 
  }
  
  public RegisterSpec get(RegisterSpec paramRegisterSpec) {
    return get(paramRegisterSpec.getReg());
  }
  
  public int getMaxSize() {
    return this.specs.length;
  }
  
  public int hashCode() {
    int i = this.specs.length;
    byte b = 0;
    int j = 0;
    while (b < i) {
      int k;
      RegisterSpec registerSpec = this.specs[b];
      if (registerSpec == null) {
        k = 0;
      } else {
        k = registerSpec.hashCode();
      } 
      j = j * 31 + k;
      b++;
    } 
    return j;
  }
  
  public void intersect(RegisterSpecSet paramRegisterSpecSet, boolean paramBoolean) {
    int k;
    throwIfImmutable();
    RegisterSpec[] arrayOfRegisterSpec = paramRegisterSpecSet.specs;
    int i = this.specs.length;
    int j = Math.min(i, arrayOfRegisterSpec.length);
    this.size = -1;
    byte b = 0;
    while (true) {
      k = j;
      if (b < j) {
        RegisterSpec registerSpec = this.specs[b];
        if (registerSpec != null) {
          RegisterSpec registerSpec1 = registerSpec.intersect(arrayOfRegisterSpec[b], paramBoolean);
          if (registerSpec1 != registerSpec)
            this.specs[b] = registerSpec1; 
        } 
        b++;
        continue;
      } 
      break;
    } 
    while (k < i) {
      this.specs[k] = null;
      k++;
    } 
  }
  
  public RegisterSpec localItemToSpec(LocalItem paramLocalItem) {
    int i = this.specs.length;
    for (byte b = 0; b < i; b++) {
      RegisterSpec registerSpec = this.specs[b];
      if (registerSpec != null && paramLocalItem.equals(registerSpec.getLocalItem()))
        return registerSpec; 
    } 
    return null;
  }
  
  public RegisterSpecSet mutableCopy() {
    int i = this.specs.length;
    RegisterSpecSet registerSpecSet = new RegisterSpecSet(i);
    for (byte b = 0; b < i; b++) {
      RegisterSpec registerSpec = this.specs[b];
      if (registerSpec != null)
        registerSpecSet.put(registerSpec); 
    } 
    registerSpecSet.size = this.size;
    return registerSpecSet;
  }
  
  public void put(RegisterSpec paramRegisterSpec) {
    throwIfImmutable();
    if (paramRegisterSpec != null) {
      this.size = -1;
      try {
        int i = paramRegisterSpec.getReg();
        this.specs[i] = paramRegisterSpec;
        if (i > 0) {
          int j = i - 1;
          RegisterSpec registerSpec = this.specs[j];
          if (registerSpec != null && registerSpec.getCategory() == 2)
            this.specs[j] = null; 
        } 
        if (paramRegisterSpec.getCategory() == 2)
          this.specs[i + 1] = null; 
        return;
      } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        throw new IllegalArgumentException("spec.getReg() out of range");
      } 
    } 
    throw new NullPointerException("spec == null");
  }
  
  public void putAll(RegisterSpecSet paramRegisterSpecSet) {
    int i = paramRegisterSpecSet.getMaxSize();
    for (byte b = 0; b < i; b++) {
      RegisterSpec registerSpec = paramRegisterSpecSet.get(b);
      if (registerSpec != null)
        put(registerSpec); 
    } 
  }
  
  public void remove(RegisterSpec paramRegisterSpec) {
    try {
      this.specs[paramRegisterSpec.getReg()] = null;
      this.size = -1;
      return;
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      throw new IllegalArgumentException("bogus reg");
    } 
  }
  
  public int size() {
    int i = this.size;
    int j = i;
    if (i < 0) {
      int k = this.specs.length;
      j = 0;
      i = 0;
      while (i < k) {
        int m = j;
        if (this.specs[i] != null)
          m = j + 1; 
        i++;
        j = m;
      } 
      this.size = j;
    } 
    return j;
  }
  
  public String toString() {
    int i = this.specs.length;
    StringBuilder stringBuilder = new StringBuilder(i * 25);
    stringBuilder.append('{');
    byte b = 0;
    boolean bool;
    for (bool = false; b < i; bool = bool1) {
      RegisterSpec registerSpec = this.specs[b];
      boolean bool1 = bool;
      if (registerSpec != null) {
        if (bool) {
          stringBuilder.append(", ");
        } else {
          bool = true;
        } 
        stringBuilder.append(registerSpec);
        bool1 = bool;
      } 
      b++;
    } 
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public RegisterSpecSet withOffset(int paramInt) {
    int i = this.specs.length;
    RegisterSpecSet registerSpecSet = new RegisterSpecSet(i + paramInt);
    for (byte b = 0; b < i; b++) {
      RegisterSpec registerSpec = this.specs[b];
      if (registerSpec != null)
        registerSpecSet.put(registerSpec.withOffset(paramInt)); 
    } 
    registerSpecSet.size = this.size;
    if (isImmutable())
      registerSpecSet.setImmutable(); 
    return registerSpecSet;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\RegisterSpecSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */