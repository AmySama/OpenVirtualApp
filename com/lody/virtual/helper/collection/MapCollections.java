package com.lody.virtual.helper.collection;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

abstract class MapCollections<K, V> {
  EntrySet mEntrySet;
  
  KeySet mKeySet;
  
  ValuesCollection mValues;
  
  public static <K, V> boolean containsAllHelper(Map<K, V> paramMap, Collection<?> paramCollection) {
    Iterator<?> iterator = paramCollection.iterator();
    while (iterator.hasNext()) {
      if (!paramMap.containsKey(iterator.next()))
        return false; 
    } 
    return true;
  }
  
  public static <T> boolean equalsSetHelper(Set<T> paramSet, Object paramObject) {
    boolean bool = true;
    if (paramSet == paramObject)
      return true; 
    if (paramObject instanceof Set) {
      paramObject = paramObject;
      try {
        if (paramSet.size() == paramObject.size()) {
          boolean bool1 = paramSet.containsAll((Collection<?>)paramObject);
          if (bool1)
            return bool; 
        } 
        return false;
      } catch (NullPointerException|ClassCastException nullPointerException) {}
    } 
    return false;
  }
  
  public static <K, V> boolean removeAllHelper(Map<K, V> paramMap, Collection<?> paramCollection) {
    boolean bool;
    int i = paramMap.size();
    Iterator<?> iterator = paramCollection.iterator();
    while (iterator.hasNext())
      paramMap.remove(iterator.next()); 
    if (i != paramMap.size()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static <K, V> boolean retainAllHelper(Map<K, V> paramMap, Collection<?> paramCollection) {
    boolean bool;
    int i = paramMap.size();
    Iterator iterator = paramMap.keySet().iterator();
    while (iterator.hasNext()) {
      if (!paramCollection.contains(iterator.next()))
        iterator.remove(); 
    } 
    if (i != paramMap.size()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected abstract void colClear();
  
  protected abstract Object colGetEntry(int paramInt1, int paramInt2);
  
  protected abstract Map<K, V> colGetMap();
  
  protected abstract int colGetSize();
  
  protected abstract int colIndexOfKey(Object paramObject);
  
  protected abstract int colIndexOfValue(Object paramObject);
  
  protected abstract void colPut(K paramK, V paramV);
  
  protected abstract void colRemoveAt(int paramInt);
  
  protected abstract V colSetValue(int paramInt, V paramV);
  
  public Set<Map.Entry<K, V>> getEntrySet() {
    if (this.mEntrySet == null)
      this.mEntrySet = new EntrySet(); 
    return this.mEntrySet;
  }
  
  public Set<K> getKeySet() {
    if (this.mKeySet == null)
      this.mKeySet = new KeySet(); 
    return this.mKeySet;
  }
  
  public Collection<V> getValues() {
    if (this.mValues == null)
      this.mValues = new ValuesCollection(); 
    return this.mValues;
  }
  
  public Object[] toArrayHelper(int paramInt) {
    int i = colGetSize();
    Object[] arrayOfObject = new Object[i];
    for (byte b = 0; b < i; b++)
      arrayOfObject[b] = colGetEntry(b, paramInt); 
    return arrayOfObject;
  }
  
  public <T> T[] toArrayHelper(T[] paramArrayOfT, int paramInt) {
    int i = colGetSize();
    T[] arrayOfT = paramArrayOfT;
    if (paramArrayOfT.length < i)
      arrayOfT = (T[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i); 
    for (byte b = 0; b < i; b++)
      arrayOfT[b] = (T)colGetEntry(b, paramInt); 
    if (arrayOfT.length > i)
      arrayOfT[i] = null; 
    return arrayOfT;
  }
  
  final class ArrayIterator<T> implements Iterator<T> {
    boolean mCanRemove = false;
    
    int mIndex;
    
    final int mOffset;
    
    int mSize;
    
    ArrayIterator(int param1Int) {
      this.mOffset = param1Int;
      this.mSize = MapCollections.this.colGetSize();
    }
    
    public boolean hasNext() {
      boolean bool;
      if (this.mIndex < this.mSize) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public T next() {
      Object object = MapCollections.this.colGetEntry(this.mIndex, this.mOffset);
      this.mIndex++;
      this.mCanRemove = true;
      return (T)object;
    }
    
    public void remove() {
      if (this.mCanRemove) {
        int i = this.mIndex - 1;
        this.mIndex = i;
        this.mSize--;
        this.mCanRemove = false;
        MapCollections.this.colRemoveAt(i);
        return;
      } 
      throw new IllegalStateException();
    }
  }
  
  final class EntrySet implements Set<Map.Entry<K, V>> {
    public boolean add(Map.Entry<K, V> param1Entry) {
      throw new UnsupportedOperationException();
    }
    
    public boolean addAll(Collection<? extends Map.Entry<K, V>> param1Collection) {
      boolean bool;
      int i = MapCollections.this.colGetSize();
      for (Map.Entry<K, V> entry : param1Collection)
        MapCollections.this.colPut(entry.getKey(), entry.getValue()); 
      if (i != MapCollections.this.colGetSize()) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void clear() {
      MapCollections.this.colClear();
    }
    
    public boolean contains(Object param1Object) {
      if (!(param1Object instanceof Map.Entry))
        return false; 
      param1Object = param1Object;
      int i = MapCollections.this.colIndexOfKey(param1Object.getKey());
      return (i < 0) ? false : ContainerHelpers.equal(MapCollections.this.colGetEntry(i, 1), param1Object.getValue());
    }
    
    public boolean containsAll(Collection<?> param1Collection) {
      Iterator<?> iterator = param1Collection.iterator();
      while (iterator.hasNext()) {
        if (!contains(iterator.next()))
          return false; 
      } 
      return true;
    }
    
    public boolean equals(Object param1Object) {
      return MapCollections.equalsSetHelper(this, param1Object);
    }
    
    public int hashCode() {
      int i = MapCollections.this.colGetSize() - 1;
      int j = 0;
      while (i >= 0) {
        int k;
        int m;
        Object object1 = MapCollections.this.colGetEntry(i, 0);
        Object object2 = MapCollections.this.colGetEntry(i, 1);
        if (object1 == null) {
          k = 0;
        } else {
          k = object1.hashCode();
        } 
        if (object2 == null) {
          m = 0;
        } else {
          m = object2.hashCode();
        } 
        j += k ^ m;
        i--;
      } 
      return j;
    }
    
    public boolean isEmpty() {
      boolean bool;
      if (MapCollections.this.colGetSize() == 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public Iterator<Map.Entry<K, V>> iterator() {
      return new MapCollections.MapIterator();
    }
    
    public boolean remove(Object param1Object) {
      throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(Collection<?> param1Collection) {
      throw new UnsupportedOperationException();
    }
    
    public boolean retainAll(Collection<?> param1Collection) {
      throw new UnsupportedOperationException();
    }
    
    public int size() {
      return MapCollections.this.colGetSize();
    }
    
    public Object[] toArray() {
      throw new UnsupportedOperationException();
    }
    
    public <T> T[] toArray(T[] param1ArrayOfT) {
      throw new UnsupportedOperationException();
    }
  }
  
  final class KeySet implements Set<K> {
    public boolean add(K param1K) {
      throw new UnsupportedOperationException();
    }
    
    public boolean addAll(Collection<? extends K> param1Collection) {
      throw new UnsupportedOperationException();
    }
    
    public void clear() {
      MapCollections.this.colClear();
    }
    
    public boolean contains(Object param1Object) {
      boolean bool;
      if (MapCollections.this.colIndexOfKey(param1Object) >= 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public boolean containsAll(Collection<?> param1Collection) {
      return MapCollections.containsAllHelper(MapCollections.this.colGetMap(), param1Collection);
    }
    
    public boolean equals(Object param1Object) {
      return MapCollections.equalsSetHelper(this, param1Object);
    }
    
    public int hashCode() {
      int i = MapCollections.this.colGetSize() - 1;
      int j = 0;
      while (i >= 0) {
        int k;
        Object object = MapCollections.this.colGetEntry(i, 0);
        if (object == null) {
          k = 0;
        } else {
          k = object.hashCode();
        } 
        j += k;
        i--;
      } 
      return j;
    }
    
    public boolean isEmpty() {
      boolean bool;
      if (MapCollections.this.colGetSize() == 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public Iterator<K> iterator() {
      return new MapCollections.ArrayIterator<K>(0);
    }
    
    public boolean remove(Object param1Object) {
      int i = MapCollections.this.colIndexOfKey(param1Object);
      if (i >= 0) {
        MapCollections.this.colRemoveAt(i);
        return true;
      } 
      return false;
    }
    
    public boolean removeAll(Collection<?> param1Collection) {
      return MapCollections.removeAllHelper(MapCollections.this.colGetMap(), param1Collection);
    }
    
    public boolean retainAll(Collection<?> param1Collection) {
      return MapCollections.retainAllHelper(MapCollections.this.colGetMap(), param1Collection);
    }
    
    public int size() {
      return MapCollections.this.colGetSize();
    }
    
    public Object[] toArray() {
      return MapCollections.this.toArrayHelper(0);
    }
    
    public <T> T[] toArray(T[] param1ArrayOfT) {
      return (T[])MapCollections.this.toArrayHelper((Object[])param1ArrayOfT, 0);
    }
  }
  
  final class MapIterator implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V> {
    int mEnd = MapCollections.this.colGetSize() - 1;
    
    boolean mEntryValid = false;
    
    int mIndex = -1;
    
    public final boolean equals(Object param1Object) {
      if (this.mEntryValid) {
        boolean bool = param1Object instanceof Map.Entry;
        boolean bool1 = false;
        if (!bool)
          return false; 
        param1Object = param1Object;
        bool = bool1;
        if (ContainerHelpers.equal(param1Object.getKey(), MapCollections.this.colGetEntry(this.mIndex, 0))) {
          bool = bool1;
          if (ContainerHelpers.equal(param1Object.getValue(), MapCollections.this.colGetEntry(this.mIndex, 1)))
            bool = true; 
        } 
        return bool;
      } 
      throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }
    
    public K getKey() {
      if (this.mEntryValid)
        return (K)MapCollections.this.colGetEntry(this.mIndex, 0); 
      throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }
    
    public V getValue() {
      if (this.mEntryValid)
        return (V)MapCollections.this.colGetEntry(this.mIndex, 1); 
      throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }
    
    public boolean hasNext() {
      boolean bool;
      if (this.mIndex < this.mEnd) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public final int hashCode() {
      if (this.mEntryValid) {
        MapCollections mapCollections = MapCollections.this;
        int i = this.mIndex;
        int j = 0;
        Object object2 = mapCollections.colGetEntry(i, 0);
        Object object1 = MapCollections.this.colGetEntry(this.mIndex, 1);
        if (object2 == null) {
          i = 0;
        } else {
          i = object2.hashCode();
        } 
        if (object1 != null)
          j = object1.hashCode(); 
        return i ^ j;
      } 
      throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }
    
    public Map.Entry<K, V> next() {
      this.mIndex++;
      this.mEntryValid = true;
      return this;
    }
    
    public void remove() {
      if (this.mEntryValid) {
        MapCollections.this.colRemoveAt(this.mIndex);
        this.mIndex--;
        this.mEnd--;
        this.mEntryValid = false;
        return;
      } 
      throw new IllegalStateException();
    }
    
    public V setValue(V param1V) {
      if (this.mEntryValid)
        return (V)MapCollections.this.colSetValue(this.mIndex, param1V); 
      throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }
    
    public final String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(getKey());
      stringBuilder.append("=");
      stringBuilder.append(getValue());
      return stringBuilder.toString();
    }
  }
  
  final class ValuesCollection implements Collection<V> {
    public boolean add(V param1V) {
      throw new UnsupportedOperationException();
    }
    
    public boolean addAll(Collection<? extends V> param1Collection) {
      throw new UnsupportedOperationException();
    }
    
    public void clear() {
      MapCollections.this.colClear();
    }
    
    public boolean contains(Object param1Object) {
      boolean bool;
      if (MapCollections.this.colIndexOfValue(param1Object) >= 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public boolean containsAll(Collection<?> param1Collection) {
      Iterator<?> iterator = param1Collection.iterator();
      while (iterator.hasNext()) {
        if (!contains(iterator.next()))
          return false; 
      } 
      return true;
    }
    
    public boolean isEmpty() {
      boolean bool;
      if (MapCollections.this.colGetSize() == 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public Iterator<V> iterator() {
      return new MapCollections.ArrayIterator<V>(1);
    }
    
    public boolean remove(Object param1Object) {
      int i = MapCollections.this.colIndexOfValue(param1Object);
      if (i >= 0) {
        MapCollections.this.colRemoveAt(i);
        return true;
      } 
      return false;
    }
    
    public boolean removeAll(Collection<?> param1Collection) {
      int i = MapCollections.this.colGetSize();
      int j = 0;
      boolean bool = false;
      while (j < i) {
        int k = i;
        int m = j;
        if (param1Collection.contains(MapCollections.this.colGetEntry(j, 1))) {
          MapCollections.this.colRemoveAt(j);
          m = j - 1;
          k = i - 1;
          bool = true;
        } 
        j = m + 1;
        i = k;
      } 
      return bool;
    }
    
    public boolean retainAll(Collection<?> param1Collection) {
      int i = MapCollections.this.colGetSize();
      int j = 0;
      boolean bool = false;
      while (j < i) {
        int k = i;
        int m = j;
        if (!param1Collection.contains(MapCollections.this.colGetEntry(j, 1))) {
          MapCollections.this.colRemoveAt(j);
          m = j - 1;
          k = i - 1;
          bool = true;
        } 
        j = m + 1;
        i = k;
      } 
      return bool;
    }
    
    public int size() {
      return MapCollections.this.colGetSize();
    }
    
    public Object[] toArray() {
      return MapCollections.this.toArrayHelper(1);
    }
    
    public <T> T[] toArray(T[] param1ArrayOfT) {
      return (T[])MapCollections.this.toArrayHelper((Object[])param1ArrayOfT, 1);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\collection\MapCollections.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */