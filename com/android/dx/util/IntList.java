package com.android.dx.util;

import java.util.Arrays;

public final class IntList extends MutabilityControl {
  public static final IntList EMPTY;
  
  private int size;
  
  private boolean sorted;
  
  private int[] values;
  
  static {
    IntList intList = new IntList(0);
    EMPTY = intList;
    intList.setImmutable();
  }
  
  public IntList() {
    this(4);
  }
  
  public IntList(int paramInt) {
    super(true);
    try {
      this.values = new int[paramInt];
      this.size = 0;
      this.sorted = true;
      return;
    } catch (NegativeArraySizeException negativeArraySizeException) {
      throw new IllegalArgumentException("size < 0");
    } 
  }
  
  private void growIfNeeded() {
    int i = this.size;
    int[] arrayOfInt = this.values;
    if (i == arrayOfInt.length) {
      int[] arrayOfInt1 = new int[i * 3 / 2 + 10];
      System.arraycopy(arrayOfInt, 0, arrayOfInt1, 0, i);
      this.values = arrayOfInt1;
    } 
  }
  
  public static IntList makeImmutable(int paramInt) {
    IntList intList = new IntList(1);
    intList.add(paramInt);
    intList.setImmutable();
    return intList;
  }
  
  public static IntList makeImmutable(int paramInt1, int paramInt2) {
    IntList intList = new IntList(2);
    intList.add(paramInt1);
    intList.add(paramInt2);
    intList.setImmutable();
    return intList;
  }
  
  public void add(int paramInt) {
    throwIfImmutable();
    growIfNeeded();
    int[] arrayOfInt = this.values;
    int i = this.size;
    int j = i + 1;
    this.size = j;
    arrayOfInt[i] = paramInt;
    if (this.sorted) {
      boolean bool = true;
      if (j > 1) {
        if (paramInt < arrayOfInt[j - 2])
          bool = false; 
        this.sorted = bool;
      } 
    } 
  }
  
  public int binarysearch(int paramInt) {
    int i = this.size;
    if (!this.sorted) {
      for (byte b = 0; b < i; b++) {
        if (this.values[b] == paramInt)
          return b; 
      } 
      return -i;
    } 
    int k = -1;
    int j = i;
    while (j > k + 1) {
      int m = (j - k >> 1) + k;
      if (paramInt <= this.values[m]) {
        j = m;
        continue;
      } 
      k = m;
    } 
    if (j != i) {
      if (paramInt != this.values[j])
        j = -j - 1; 
      return j;
    } 
    return -i - 1;
  }
  
  public boolean contains(int paramInt) {
    boolean bool;
    if (indexOf(paramInt) >= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == this)
      return true; 
    if (!(paramObject instanceof IntList))
      return false; 
    paramObject = paramObject;
    if (this.sorted != ((IntList)paramObject).sorted)
      return false; 
    if (this.size != ((IntList)paramObject).size)
      return false; 
    for (byte b = 0; b < this.size; b++) {
      if (this.values[b] != ((IntList)paramObject).values[b])
        return false; 
    } 
    return true;
  }
  
  public int get(int paramInt) {
    if (paramInt < this.size)
      try {
        return this.values[paramInt];
      } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        throw new IndexOutOfBoundsException("n < 0");
      }  
    throw new IndexOutOfBoundsException("n >= size()");
  }
  
  public int hashCode() {
    byte b = 0;
    int i = 0;
    while (b < this.size) {
      i = i * 31 + this.values[b];
      b++;
    } 
    return i;
  }
  
  public int indexOf(int paramInt) {
    paramInt = binarysearch(paramInt);
    if (paramInt < 0)
      paramInt = -1; 
    return paramInt;
  }
  
  public void insert(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: iload_1
    //   1: aload_0
    //   2: getfield size : I
    //   5: if_icmpgt -> 124
    //   8: aload_0
    //   9: invokespecial growIfNeeded : ()V
    //   12: aload_0
    //   13: getfield values : [I
    //   16: astore_3
    //   17: iload_1
    //   18: iconst_1
    //   19: iadd
    //   20: istore #4
    //   22: aload_3
    //   23: iload_1
    //   24: aload_3
    //   25: iload #4
    //   27: aload_0
    //   28: getfield size : I
    //   31: iload_1
    //   32: isub
    //   33: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   36: aload_0
    //   37: getfield values : [I
    //   40: astore_3
    //   41: aload_3
    //   42: iload_1
    //   43: iload_2
    //   44: iastore
    //   45: aload_0
    //   46: getfield size : I
    //   49: istore #5
    //   51: iconst_1
    //   52: istore #6
    //   54: aload_0
    //   55: iload #5
    //   57: iconst_1
    //   58: iadd
    //   59: putfield size : I
    //   62: aload_0
    //   63: getfield sorted : Z
    //   66: ifeq -> 114
    //   69: iload_1
    //   70: ifeq -> 82
    //   73: iload_2
    //   74: aload_3
    //   75: iload_1
    //   76: iconst_1
    //   77: isub
    //   78: iaload
    //   79: if_icmple -> 114
    //   82: iload #6
    //   84: istore #7
    //   86: iload_1
    //   87: aload_0
    //   88: getfield size : I
    //   91: iconst_1
    //   92: isub
    //   93: if_icmpeq -> 117
    //   96: iload_2
    //   97: aload_0
    //   98: getfield values : [I
    //   101: iload #4
    //   103: iaload
    //   104: if_icmpge -> 114
    //   107: iload #6
    //   109: istore #7
    //   111: goto -> 117
    //   114: iconst_0
    //   115: istore #7
    //   117: aload_0
    //   118: iload #7
    //   120: putfield sorted : Z
    //   123: return
    //   124: new java/lang/IndexOutOfBoundsException
    //   127: dup
    //   128: ldc 'n > size()'
    //   130: invokespecial <init> : (Ljava/lang/String;)V
    //   133: athrow
  }
  
  public IntList mutableCopy() {
    int i = this.size;
    IntList intList = new IntList(i);
    for (byte b = 0; b < i; b++)
      intList.add(this.values[b]); 
    return intList;
  }
  
  public int pop() {
    throwIfImmutable();
    int i = get(this.size - 1);
    this.size--;
    return i;
  }
  
  public void pop(int paramInt) {
    throwIfImmutable();
    this.size -= paramInt;
  }
  
  public void removeIndex(int paramInt) {
    int i = this.size;
    if (paramInt < i) {
      int[] arrayOfInt = this.values;
      System.arraycopy(arrayOfInt, paramInt + 1, arrayOfInt, paramInt, i - paramInt - 1);
      this.size--;
      return;
    } 
    throw new IndexOutOfBoundsException("n >= size()");
  }
  
  public void set(int paramInt1, int paramInt2) {
    throwIfImmutable();
    if (paramInt1 < this.size) {
      try {
        this.values[paramInt1] = paramInt2;
        this.sorted = false;
      } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        if (paramInt1 < 0)
          throw new IllegalArgumentException("n < 0"); 
      } 
      return;
    } 
    throw new IndexOutOfBoundsException("n >= size()");
  }
  
  public void shrink(int paramInt) {
    if (paramInt >= 0) {
      if (paramInt <= this.size) {
        throwIfImmutable();
        this.size = paramInt;
        return;
      } 
      throw new IllegalArgumentException("newSize > size");
    } 
    throw new IllegalArgumentException("newSize < 0");
  }
  
  public int size() {
    return this.size;
  }
  
  public void sort() {
    throwIfImmutable();
    if (!this.sorted) {
      Arrays.sort(this.values, 0, this.size);
      this.sorted = true;
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(this.size * 5 + 10);
    stringBuilder.append('{');
    for (byte b = 0; b < this.size; b++) {
      if (b != 0)
        stringBuilder.append(", "); 
      stringBuilder.append(this.values[b]);
    } 
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public int top() {
    return get(this.size - 1);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\d\\util\IntList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */