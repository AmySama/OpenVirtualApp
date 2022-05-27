package com.android.dx.util;

import java.util.NoSuchElementException;

public class BitIntSet implements IntSet {
  int[] bits;
  
  public BitIntSet(int paramInt) {
    this.bits = Bits.makeBitSet(paramInt);
  }
  
  private void ensureCapacity(int paramInt) {
    if (paramInt >= Bits.getMax(this.bits)) {
      int[] arrayOfInt1 = Bits.makeBitSet(Math.max(paramInt + 1, Bits.getMax(this.bits) * 2));
      int[] arrayOfInt2 = this.bits;
      System.arraycopy(arrayOfInt2, 0, arrayOfInt1, 0, arrayOfInt2.length);
      this.bits = arrayOfInt1;
    } 
  }
  
  public void add(int paramInt) {
    ensureCapacity(paramInt);
    Bits.set(this.bits, paramInt, true);
  }
  
  public int elements() {
    return Bits.bitCount(this.bits);
  }
  
  public boolean has(int paramInt) {
    boolean bool;
    if (paramInt < Bits.getMax(this.bits) && Bits.get(this.bits, paramInt)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public IntIterator iterator() {
    return new IntIterator() {
        private int idx = Bits.findFirst(BitIntSet.this.bits, 0);
        
        public boolean hasNext() {
          boolean bool;
          if (this.idx >= 0) {
            bool = true;
          } else {
            bool = false;
          } 
          return bool;
        }
        
        public int next() {
          if (hasNext()) {
            int i = this.idx;
            this.idx = Bits.findFirst(BitIntSet.this.bits, this.idx + 1);
            return i;
          } 
          throw new NoSuchElementException();
        }
      };
  }
  
  public void merge(IntSet paramIntSet) {
    if (paramIntSet instanceof BitIntSet) {
      paramIntSet = paramIntSet;
      ensureCapacity(Bits.getMax(((BitIntSet)paramIntSet).bits) + 1);
      Bits.or(this.bits, ((BitIntSet)paramIntSet).bits);
    } else if (paramIntSet instanceof ListIntSet) {
      paramIntSet = paramIntSet;
      int i = ((ListIntSet)paramIntSet).ints.size();
      if (i > 0)
        ensureCapacity(((ListIntSet)paramIntSet).ints.get(i - 1)); 
      for (i = 0; i < ((ListIntSet)paramIntSet).ints.size(); i++)
        Bits.set(this.bits, ((ListIntSet)paramIntSet).ints.get(i), true); 
    } else {
      IntIterator intIterator = paramIntSet.iterator();
      while (intIterator.hasNext())
        add(intIterator.next()); 
    } 
  }
  
  public void remove(int paramInt) {
    if (paramInt < Bits.getMax(this.bits))
      Bits.set(this.bits, paramInt, false); 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('{');
    int i = Bits.findFirst(this.bits, 0);
    for (boolean bool = true; i >= 0; bool = false) {
      if (!bool)
        stringBuilder.append(", "); 
      stringBuilder.append(i);
      i = Bits.findFirst(this.bits, i + 1);
    } 
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\d\\util\BitIntSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */