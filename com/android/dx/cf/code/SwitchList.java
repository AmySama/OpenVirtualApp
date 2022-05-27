package com.android.dx.cf.code;

import com.android.dx.util.IntList;
import com.android.dx.util.MutabilityControl;

public final class SwitchList extends MutabilityControl {
  private int size;
  
  private final IntList targets;
  
  private final IntList values;
  
  public SwitchList(int paramInt) {
    super(true);
    this.values = new IntList(paramInt);
    this.targets = new IntList(paramInt + 1);
    this.size = paramInt;
  }
  
  public void add(int paramInt1, int paramInt2) {
    throwIfImmutable();
    if (paramInt2 >= 0) {
      this.values.add(paramInt1);
      this.targets.add(paramInt2);
      return;
    } 
    throw new IllegalArgumentException("target < 0");
  }
  
  public int getDefaultTarget() {
    return this.targets.get(this.size);
  }
  
  public int getTarget(int paramInt) {
    return this.targets.get(paramInt);
  }
  
  public IntList getTargets() {
    return this.targets;
  }
  
  public int getValue(int paramInt) {
    return this.values.get(paramInt);
  }
  
  public IntList getValues() {
    return this.values;
  }
  
  public void removeSuperfluousDefaults() {
    throwIfImmutable();
    int i = this.size;
    if (i == this.targets.size() - 1) {
      int j = this.targets.get(i);
      int k = 0;
      int m;
      for (m = 0; k < i; m = i1) {
        int n = this.targets.get(k);
        int i1 = m;
        if (n != j) {
          if (k != m) {
            this.targets.set(m, n);
            IntList intList = this.values;
            intList.set(m, intList.get(k));
          } 
          i1 = m + 1;
        } 
        k++;
      } 
      if (m != i) {
        this.values.shrink(m);
        this.targets.set(m, j);
        this.targets.shrink(m + 1);
        this.size = m;
      } 
      return;
    } 
    throw new IllegalArgumentException("incomplete instance");
  }
  
  public void setDefaultTarget(int paramInt) {
    throwIfImmutable();
    if (paramInt >= 0) {
      if (this.targets.size() == this.size) {
        this.targets.add(paramInt);
        return;
      } 
      throw new RuntimeException("non-default elements not all set");
    } 
    throw new IllegalArgumentException("target < 0");
  }
  
  public void setImmutable() {
    this.values.setImmutable();
    this.targets.setImmutable();
    super.setImmutable();
  }
  
  public int size() {
    return this.size;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\SwitchList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */