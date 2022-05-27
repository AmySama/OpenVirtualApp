package android.support.v4.util;

public final class CircularArray<E> {
  private int mCapacityBitmask;
  
  private E[] mElements;
  
  private int mHead;
  
  private int mTail;
  
  public CircularArray() {
    this(8);
  }
  
  public CircularArray(int paramInt) {
    if (paramInt >= 1) {
      if (paramInt <= 1073741824) {
        int i = paramInt;
        if (Integer.bitCount(paramInt) != 1)
          i = Integer.highestOneBit(paramInt - 1) << 1; 
        this.mCapacityBitmask = i - 1;
        this.mElements = (E[])new Object[i];
        return;
      } 
      throw new IllegalArgumentException("capacity must be <= 2^30");
    } 
    throw new IllegalArgumentException("capacity must be >= 1");
  }
  
  private void doubleCapacity() {
    E[] arrayOfE = this.mElements;
    int i = arrayOfE.length;
    int j = this.mHead;
    int k = i - j;
    int m = i << 1;
    if (m >= 0) {
      Object[] arrayOfObject = new Object[m];
      System.arraycopy(arrayOfE, j, arrayOfObject, 0, k);
      System.arraycopy(this.mElements, 0, arrayOfObject, k, this.mHead);
      this.mElements = (E[])arrayOfObject;
      this.mHead = 0;
      this.mTail = i;
      this.mCapacityBitmask = m - 1;
      return;
    } 
    throw new RuntimeException("Max array capacity exceeded");
  }
  
  public void addFirst(E paramE) {
    int i = this.mHead - 1 & this.mCapacityBitmask;
    this.mHead = i;
    this.mElements[i] = paramE;
    if (i == this.mTail)
      doubleCapacity(); 
  }
  
  public void addLast(E paramE) {
    E[] arrayOfE = this.mElements;
    int i = this.mTail;
    arrayOfE[i] = paramE;
    i = this.mCapacityBitmask & i + 1;
    this.mTail = i;
    if (i == this.mHead)
      doubleCapacity(); 
  }
  
  public void clear() {
    removeFromStart(size());
  }
  
  public E get(int paramInt) {
    if (paramInt >= 0 && paramInt < size()) {
      E[] arrayOfE = this.mElements;
      int i = this.mHead;
      return arrayOfE[this.mCapacityBitmask & i + paramInt];
    } 
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public E getFirst() {
    int i = this.mHead;
    if (i != this.mTail)
      return this.mElements[i]; 
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public E getLast() {
    int i = this.mHead;
    int j = this.mTail;
    if (i != j)
      return this.mElements[j - 1 & this.mCapacityBitmask]; 
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public boolean isEmpty() {
    boolean bool;
    if (this.mHead == this.mTail) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public E popFirst() {
    int i = this.mHead;
    if (i != this.mTail) {
      E[] arrayOfE = this.mElements;
      E e = arrayOfE[i];
      arrayOfE[i] = null;
      this.mHead = i + 1 & this.mCapacityBitmask;
      return e;
    } 
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public E popLast() {
    int i = this.mHead;
    int j = this.mTail;
    if (i != j) {
      i = this.mCapacityBitmask & j - 1;
      E[] arrayOfE = this.mElements;
      E e = arrayOfE[i];
      arrayOfE[i] = null;
      this.mTail = i;
      return e;
    } 
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public void removeFromEnd(int paramInt) {
    if (paramInt <= 0)
      return; 
    if (paramInt <= size()) {
      int i = 0;
      int j = this.mTail;
      if (paramInt < j)
        i = j - paramInt; 
      j = i;
      while (true) {
        int k = this.mTail;
        if (j < k) {
          this.mElements[j] = null;
          j++;
          continue;
        } 
        i = k - i;
        paramInt -= i;
        this.mTail = k - i;
        if (paramInt > 0) {
          i = this.mElements.length;
          this.mTail = i;
          i -= paramInt;
          for (paramInt = i; paramInt < this.mTail; paramInt++)
            this.mElements[paramInt] = null; 
          this.mTail = i;
        } 
        return;
      } 
    } 
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public void removeFromStart(int paramInt) {
    if (paramInt <= 0)
      return; 
    if (paramInt <= size()) {
      int i = this.mElements.length;
      int j = this.mHead;
      int k = i;
      if (paramInt < i - j)
        k = j + paramInt; 
      for (i = this.mHead; i < k; i++)
        this.mElements[i] = null; 
      i = this.mHead;
      j = k - i;
      k = paramInt - j;
      this.mHead = this.mCapacityBitmask & i + j;
      if (k > 0) {
        for (paramInt = 0; paramInt < k; paramInt++)
          this.mElements[paramInt] = null; 
        this.mHead = k;
      } 
      return;
    } 
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public int size() {
    return this.mTail - this.mHead & this.mCapacityBitmask;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v\\util\CircularArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */