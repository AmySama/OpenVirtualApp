package com.lody.virtual.server.am;

import com.lody.virtual.helper.collection.ArrayMap;
import com.lody.virtual.helper.collection.SparseArray;

class ProcessMap<E> {
  private final ArrayMap<String, SparseArray<E>> mMap = new ArrayMap();
  
  public E get(String paramString, int paramInt) {
    SparseArray sparseArray = (SparseArray)this.mMap.get(paramString);
    return (E)((sparseArray == null) ? null : sparseArray.get(paramInt));
  }
  
  public ArrayMap<String, SparseArray<E>> getMap() {
    return this.mMap;
  }
  
  public E put(String paramString, int paramInt, E paramE) {
    SparseArray sparseArray1 = (SparseArray)this.mMap.get(paramString);
    SparseArray sparseArray2 = sparseArray1;
    if (sparseArray1 == null) {
      sparseArray2 = new SparseArray(2);
      this.mMap.put(paramString, sparseArray2);
    } 
    sparseArray2.put(paramInt, paramE);
    return paramE;
  }
  
  public E remove(String paramString, int paramInt) {
    SparseArray sparseArray = (SparseArray)this.mMap.get(paramString);
    if (sparseArray != null) {
      Object object = sparseArray.removeReturnOld(paramInt);
      if (sparseArray.size() == 0)
        this.mMap.remove(paramString); 
      return (E)object;
    } 
    return null;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\am\ProcessMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */