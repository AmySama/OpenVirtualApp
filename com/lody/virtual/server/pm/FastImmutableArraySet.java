package com.lody.virtual.server.pm;

import java.util.AbstractSet;
import java.util.Iterator;

public final class FastImmutableArraySet<T> extends AbstractSet<T> {
  T[] mContents;
  
  FastIterator<T> mIterator;
  
  public FastImmutableArraySet(T[] paramArrayOfT) {
    this.mContents = paramArrayOfT;
  }
  
  public Iterator<T> iterator() {
    FastIterator<T> fastIterator = this.mIterator;
    if (fastIterator == null) {
      fastIterator = new FastIterator<T>(this.mContents);
      this.mIterator = fastIterator;
    } else {
      fastIterator.mIndex = 0;
    } 
    return fastIterator;
  }
  
  public int size() {
    return this.mContents.length;
  }
  
  private static final class FastIterator<T> implements Iterator<T> {
    private final T[] mContents;
    
    int mIndex;
    
    public FastIterator(T[] param1ArrayOfT) {
      this.mContents = param1ArrayOfT;
    }
    
    public boolean hasNext() {
      boolean bool;
      if (this.mIndex != this.mContents.length) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public T next() {
      T[] arrayOfT = this.mContents;
      int i = this.mIndex;
      this.mIndex = i + 1;
      return arrayOfT[i];
    }
    
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\FastImmutableArraySet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */