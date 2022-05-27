package com.android.dx.util;

public interface IntSet {
  void add(int paramInt);
  
  int elements();
  
  boolean has(int paramInt);
  
  IntIterator iterator();
  
  void merge(IntSet paramIntSet);
  
  void remove(int paramInt);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\d\\util\IntSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */