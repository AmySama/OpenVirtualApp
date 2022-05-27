package com.android.dx.util;

import java.util.NoSuchElementException;

public class ListIntSet implements IntSet {
  final IntList ints;
  
  public ListIntSet() {
    IntList intList = new IntList();
    this.ints = intList;
    intList.sort();
  }
  
  public void add(int paramInt) {
    int i = this.ints.binarysearch(paramInt);
    if (i < 0)
      this.ints.insert(-(i + 1), paramInt); 
  }
  
  public int elements() {
    return this.ints.size();
  }
  
  public boolean has(int paramInt) {
    boolean bool;
    if (this.ints.indexOf(paramInt) >= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public IntIterator iterator() {
    return new IntIterator() {
        private int idx = 0;
        
        public boolean hasNext() {
          boolean bool;
          if (this.idx < ListIntSet.this.ints.size()) {
            bool = true;
          } else {
            bool = false;
          } 
          return bool;
        }
        
        public int next() {
          if (hasNext()) {
            IntList intList = ListIntSet.this.ints;
            int i = this.idx;
            this.idx = i + 1;
            return intList.get(i);
          } 
          throw new NoSuchElementException();
        }
      };
  }
  
  public void merge(IntSet paramIntSet) {
    boolean bool = paramIntSet instanceof ListIntSet;
    int i = 0;
    int j = 0;
    if (bool) {
      paramIntSet = paramIntSet;
      int k = this.ints.size();
      int m = ((ListIntSet)paramIntSet).ints.size();
      byte b = 0;
      label46: while (true) {
        i = j;
        if (j < m) {
          i = j;
          if (b < k) {
            for (i = j; i < m && ((ListIntSet)paramIntSet).ints.get(i) < this.ints.get(b); i++)
              add(((ListIntSet)paramIntSet).ints.get(i)); 
            byte b1 = b;
            if (i == m)
              break; 
            while (true) {
              j = i;
              b = b1;
              if (b1 < k) {
                j = i;
                b = b1;
                if (((ListIntSet)paramIntSet).ints.get(i) >= this.ints.get(b1)) {
                  b1++;
                  continue;
                } 
                continue label46;
              } 
              continue label46;
            } 
          } 
        } 
        break;
      } 
      while (i < m) {
        add(((ListIntSet)paramIntSet).ints.get(i));
        i++;
      } 
      this.ints.sort();
    } else if (paramIntSet instanceof BitIntSet) {
      paramIntSet = paramIntSet;
      for (j = i; j >= 0; j = Bits.findFirst(((BitIntSet)paramIntSet).bits, j + 1))
        this.ints.add(j); 
      this.ints.sort();
    } else {
      IntIterator intIterator = paramIntSet.iterator();
      while (intIterator.hasNext())
        add(intIterator.next()); 
    } 
  }
  
  public void remove(int paramInt) {
    paramInt = this.ints.indexOf(paramInt);
    if (paramInt >= 0)
      this.ints.removeIndex(paramInt); 
  }
  
  public String toString() {
    return this.ints.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\d\\util\ListIntSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */