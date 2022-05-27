package com.lody.virtual.helper.collection;

import java.util.Arrays;

public class IntArray {
  private static final int[] EMPTY_ARRAY = new int[0];
  
  private int[] mData;
  
  private int mSize;
  
  private IntArray() {}
  
  public IntArray(int paramInt) {
    this.mData = new int[paramInt];
  }
  
  private void ensureCapacity() {
    int i = this.mSize;
    int[] arrayOfInt = this.mData;
    if (i <= arrayOfInt.length)
      return; 
    for (i = arrayOfInt.length; this.mSize > i; i = i * 3 / 2 + 1);
    this.mData = Arrays.copyOf(this.mData, i);
  }
  
  public static IntArray of(int... paramVarArgs) {
    IntArray intArray = new IntArray();
    intArray.mData = Arrays.copyOf(paramVarArgs, paramVarArgs.length);
    intArray.mSize = paramVarArgs.length;
    return intArray;
  }
  
  public void add(int paramInt) {
    this.mSize++;
    ensureCapacity();
    this.mData[this.mSize - 1] = paramInt;
  }
  
  public void addAll(int[] paramArrayOfint) {
    int i = this.mSize;
    this.mSize = paramArrayOfint.length + i;
    ensureCapacity();
    System.arraycopy(paramArrayOfint, 0, this.mData, i, paramArrayOfint.length);
  }
  
  public void clear() {
    this.mSize = 0;
  }
  
  public boolean contains(int paramInt) {
    for (byte b = 0; b < this.mSize; b++) {
      if (this.mData[b] == paramInt)
        return true; 
    } 
    return false;
  }
  
  public int get(int paramInt) {
    return this.mData[paramInt];
  }
  
  public int[] getAll() {
    int[] arrayOfInt;
    int i = this.mSize;
    if (i > 0) {
      arrayOfInt = Arrays.copyOf(this.mData, i);
    } else {
      arrayOfInt = EMPTY_ARRAY;
    } 
    return arrayOfInt;
  }
  
  public int[] getRange(int paramInt1, int paramInt2) {
    return Arrays.copyOfRange(this.mData, paramInt1, paramInt2);
  }
  
  public void optimize() {
    int i = this.mSize;
    int[] arrayOfInt = this.mData;
    if (i > arrayOfInt.length)
      this.mData = Arrays.copyOf(arrayOfInt, i); 
  }
  
  public void remove(int paramInt) {
    remove(paramInt, 1);
  }
  
  public void remove(int paramInt1, int paramInt2) {
    int[] arrayOfInt = this.mData;
    System.arraycopy(arrayOfInt, paramInt1 + paramInt2, arrayOfInt, paramInt1, this.mSize - paramInt1 - paramInt2);
    this.mSize -= paramInt2;
  }
  
  public void set(int paramInt1, int paramInt2) {
    if (paramInt1 < this.mSize) {
      this.mData[paramInt1] = paramInt2;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Index ");
    stringBuilder.append(paramInt1);
    stringBuilder.append(" is greater than the list size ");
    stringBuilder.append(this.mSize);
    throw new IndexOutOfBoundsException(stringBuilder.toString());
  }
  
  public int size() {
    return this.mSize;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\collection\IntArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */