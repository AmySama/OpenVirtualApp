package android.support.v4.util;

public class LongSparseArray<E> implements Cloneable {
  private static final Object DELETED = new Object();
  
  private boolean mGarbage = false;
  
  private long[] mKeys;
  
  private int mSize;
  
  private Object[] mValues;
  
  public LongSparseArray() {
    this(10);
  }
  
  public LongSparseArray(int paramInt) {
    if (paramInt == 0) {
      this.mKeys = ContainerHelpers.EMPTY_LONGS;
      this.mValues = ContainerHelpers.EMPTY_OBJECTS;
    } else {
      paramInt = ContainerHelpers.idealLongArraySize(paramInt);
      this.mKeys = new long[paramInt];
      this.mValues = new Object[paramInt];
    } 
    this.mSize = 0;
  }
  
  private void gc() {
    int i = this.mSize;
    long[] arrayOfLong = this.mKeys;
    Object[] arrayOfObject = this.mValues;
    int j = 0;
    int k;
    for (k = 0; j < i; k = m) {
      Object object = arrayOfObject[j];
      int m = k;
      if (object != DELETED) {
        if (j != k) {
          arrayOfLong[k] = arrayOfLong[j];
          arrayOfObject[k] = object;
          arrayOfObject[j] = null;
        } 
        m = k + 1;
      } 
      j++;
    } 
    this.mGarbage = false;
    this.mSize = k;
  }
  
  public void append(long paramLong, E paramE) {
    int i = this.mSize;
    if (i != 0 && paramLong <= this.mKeys[i - 1]) {
      put(paramLong, paramE);
      return;
    } 
    if (this.mGarbage && this.mSize >= this.mKeys.length)
      gc(); 
    int j = this.mSize;
    if (j >= this.mKeys.length) {
      i = ContainerHelpers.idealLongArraySize(j + 1);
      long[] arrayOfLong1 = new long[i];
      Object[] arrayOfObject1 = new Object[i];
      long[] arrayOfLong2 = this.mKeys;
      System.arraycopy(arrayOfLong2, 0, arrayOfLong1, 0, arrayOfLong2.length);
      Object[] arrayOfObject2 = this.mValues;
      System.arraycopy(arrayOfObject2, 0, arrayOfObject1, 0, arrayOfObject2.length);
      this.mKeys = arrayOfLong1;
      this.mValues = arrayOfObject1;
    } 
    this.mKeys[j] = paramLong;
    this.mValues[j] = paramE;
    this.mSize = j + 1;
  }
  
  public void clear() {
    int i = this.mSize;
    Object[] arrayOfObject = this.mValues;
    for (byte b = 0; b < i; b++)
      arrayOfObject[b] = null; 
    this.mSize = 0;
    this.mGarbage = false;
  }
  
  public LongSparseArray<E> clone() {
    cloneNotSupportedException1 = null;
    try {
      LongSparseArray longSparseArray = (LongSparseArray)super.clone();
      try {
        longSparseArray.mKeys = (long[])this.mKeys.clone();
        longSparseArray.mValues = (Object[])this.mValues.clone();
      } catch (CloneNotSupportedException cloneNotSupportedException1) {}
    } catch (CloneNotSupportedException cloneNotSupportedException2) {
      cloneNotSupportedException2 = cloneNotSupportedException1;
    } 
    return (LongSparseArray<E>)cloneNotSupportedException2;
  }
  
  public void delete(long paramLong) {
    int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramLong);
    if (i >= 0) {
      Object[] arrayOfObject = this.mValues;
      Object object1 = arrayOfObject[i];
      Object object2 = DELETED;
      if (object1 != object2) {
        arrayOfObject[i] = object2;
        this.mGarbage = true;
      } 
    } 
  }
  
  public E get(long paramLong) {
    return get(paramLong, null);
  }
  
  public E get(long paramLong, E paramE) {
    int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramLong);
    if (i >= 0) {
      Object[] arrayOfObject = this.mValues;
      if (arrayOfObject[i] != DELETED)
        return (E)arrayOfObject[i]; 
    } 
    return paramE;
  }
  
  public int indexOfKey(long paramLong) {
    if (this.mGarbage)
      gc(); 
    return ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramLong);
  }
  
  public int indexOfValue(E paramE) {
    if (this.mGarbage)
      gc(); 
    for (byte b = 0; b < this.mSize; b++) {
      if (this.mValues[b] == paramE)
        return b; 
    } 
    return -1;
  }
  
  public long keyAt(int paramInt) {
    if (this.mGarbage)
      gc(); 
    return this.mKeys[paramInt];
  }
  
  public void put(long paramLong, E paramE) {
    int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramLong);
    if (i >= 0) {
      this.mValues[i] = paramE;
    } else {
      int j = i;
      if (j < this.mSize) {
        Object[] arrayOfObject = this.mValues;
        if (arrayOfObject[j] == DELETED) {
          this.mKeys[j] = paramLong;
          arrayOfObject[j] = paramE;
          return;
        } 
      } 
      i = j;
      if (this.mGarbage) {
        i = j;
        if (this.mSize >= this.mKeys.length) {
          gc();
          i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, paramLong);
        } 
      } 
      j = this.mSize;
      if (j >= this.mKeys.length) {
        j = ContainerHelpers.idealLongArraySize(j + 1);
        long[] arrayOfLong1 = new long[j];
        Object[] arrayOfObject1 = new Object[j];
        long[] arrayOfLong2 = this.mKeys;
        System.arraycopy(arrayOfLong2, 0, arrayOfLong1, 0, arrayOfLong2.length);
        Object[] arrayOfObject2 = this.mValues;
        System.arraycopy(arrayOfObject2, 0, arrayOfObject1, 0, arrayOfObject2.length);
        this.mKeys = arrayOfLong1;
        this.mValues = arrayOfObject1;
      } 
      j = this.mSize;
      if (j - i != 0) {
        long[] arrayOfLong = this.mKeys;
        int k = i + 1;
        System.arraycopy(arrayOfLong, i, arrayOfLong, k, j - i);
        Object[] arrayOfObject = this.mValues;
        System.arraycopy(arrayOfObject, i, arrayOfObject, k, this.mSize - i);
      } 
      this.mKeys[i] = paramLong;
      this.mValues[i] = paramE;
      this.mSize++;
    } 
  }
  
  public void remove(long paramLong) {
    delete(paramLong);
  }
  
  public void removeAt(int paramInt) {
    Object[] arrayOfObject = this.mValues;
    Object object1 = arrayOfObject[paramInt];
    Object object2 = DELETED;
    if (object1 != object2) {
      arrayOfObject[paramInt] = object2;
      this.mGarbage = true;
    } 
  }
  
  public void setValueAt(int paramInt, E paramE) {
    if (this.mGarbage)
      gc(); 
    this.mValues[paramInt] = paramE;
  }
  
  public int size() {
    if (this.mGarbage)
      gc(); 
    return this.mSize;
  }
  
  public String toString() {
    if (size() <= 0)
      return "{}"; 
    StringBuilder stringBuilder = new StringBuilder(this.mSize * 28);
    stringBuilder.append('{');
    for (byte b = 0; b < this.mSize; b++) {
      if (b > 0)
        stringBuilder.append(", "); 
      stringBuilder.append(keyAt(b));
      stringBuilder.append('=');
      E e = valueAt(b);
      if (e != this) {
        stringBuilder.append(e);
      } else {
        stringBuilder.append("(this Map)");
      } 
    } 
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public E valueAt(int paramInt) {
    if (this.mGarbage)
      gc(); 
    return (E)this.mValues[paramInt];
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v\\util\LongSparseArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */