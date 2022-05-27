package android.arch.core.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

public class SafeIterableMap<K, V> implements Iterable<Map.Entry<K, V>> {
  private Entry<K, V> mEnd;
  
  private WeakHashMap<SupportRemove<K, V>, Boolean> mIterators = new WeakHashMap<SupportRemove<K, V>, Boolean>();
  
  private int mSize = 0;
  
  private Entry<K, V> mStart;
  
  public Iterator<Map.Entry<K, V>> descendingIterator() {
    DescendingIterator<K, V> descendingIterator = new DescendingIterator<K, V>(this.mEnd, this.mStart);
    this.mIterators.put(descendingIterator, Boolean.valueOf(false));
    return descendingIterator;
  }
  
  public Map.Entry<K, V> eldest() {
    return this.mStart;
  }
  
  public boolean equals(Object<Map.Entry<K, V>> paramObject) {
    boolean bool = true;
    if (paramObject == this)
      return true; 
    if (!(paramObject instanceof SafeIterableMap))
      return false; 
    SafeIterableMap safeIterableMap = (SafeIterableMap)paramObject;
    if (size() != safeIterableMap.size())
      return false; 
    paramObject = (Object<Map.Entry<K, V>>)iterator();
    Iterator<SafeIterableMap> iterator = safeIterableMap.iterator();
    while (paramObject.hasNext() && iterator.hasNext()) {
      Map.Entry entry = paramObject.next();
      safeIterableMap = iterator.next();
      if ((entry == null && safeIterableMap != null) || (entry != null && !entry.equals(safeIterableMap)))
        return false; 
    } 
    if (paramObject.hasNext() || iterator.hasNext())
      bool = false; 
    return bool;
  }
  
  protected Entry<K, V> get(K paramK) {
    Entry<K, V> entry;
    for (entry = this.mStart; entry != null && !entry.mKey.equals(paramK); entry = entry.mNext);
    return entry;
  }
  
  public Iterator<Map.Entry<K, V>> iterator() {
    AscendingIterator<K, V> ascendingIterator = new AscendingIterator<K, V>(this.mStart, this.mEnd);
    this.mIterators.put(ascendingIterator, Boolean.valueOf(false));
    return ascendingIterator;
  }
  
  public IteratorWithAdditions iteratorWithAdditions() {
    IteratorWithAdditions iteratorWithAdditions = new IteratorWithAdditions();
    this.mIterators.put(iteratorWithAdditions, Boolean.valueOf(false));
    return iteratorWithAdditions;
  }
  
  public Map.Entry<K, V> newest() {
    return this.mEnd;
  }
  
  protected Entry<K, V> put(K paramK, V paramV) {
    Entry<K, V> entry1 = new Entry<K, V>(paramK, paramV);
    this.mSize++;
    Entry<K, V> entry2 = this.mEnd;
    if (entry2 == null) {
      this.mStart = entry1;
      this.mEnd = entry1;
      return entry1;
    } 
    entry2.mNext = entry1;
    entry1.mPrevious = this.mEnd;
    this.mEnd = entry1;
    return entry1;
  }
  
  public V putIfAbsent(K paramK, V paramV) {
    Entry<K, V> entry = get(paramK);
    if (entry != null)
      return entry.mValue; 
    put(paramK, paramV);
    return null;
  }
  
  public V remove(K paramK) {
    Entry<K, V> entry = get(paramK);
    if (entry == null)
      return null; 
    this.mSize--;
    if (!this.mIterators.isEmpty()) {
      Iterator<SupportRemove<K, V>> iterator = this.mIterators.keySet().iterator();
      while (iterator.hasNext())
        ((SupportRemove<K, V>)iterator.next()).supportRemove(entry); 
    } 
    if (entry.mPrevious != null) {
      entry.mPrevious.mNext = entry.mNext;
    } else {
      this.mStart = entry.mNext;
    } 
    if (entry.mNext != null) {
      entry.mNext.mPrevious = entry.mPrevious;
    } else {
      this.mEnd = entry.mPrevious;
    } 
    entry.mNext = null;
    entry.mPrevious = null;
    return entry.mValue;
  }
  
  public int size() {
    return this.mSize;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    Iterator<Map.Entry<K, V>> iterator = iterator();
    while (iterator.hasNext()) {
      stringBuilder.append(((Map.Entry)iterator.next()).toString());
      if (iterator.hasNext())
        stringBuilder.append(", "); 
    } 
    stringBuilder.append("]");
    return stringBuilder.toString();
  }
  
  static class AscendingIterator<K, V> extends ListIterator<K, V> {
    AscendingIterator(SafeIterableMap.Entry<K, V> param1Entry1, SafeIterableMap.Entry<K, V> param1Entry2) {
      super(param1Entry1, param1Entry2);
    }
    
    SafeIterableMap.Entry<K, V> backward(SafeIterableMap.Entry<K, V> param1Entry) {
      return param1Entry.mPrevious;
    }
    
    SafeIterableMap.Entry<K, V> forward(SafeIterableMap.Entry<K, V> param1Entry) {
      return param1Entry.mNext;
    }
  }
  
  private static class DescendingIterator<K, V> extends ListIterator<K, V> {
    DescendingIterator(SafeIterableMap.Entry<K, V> param1Entry1, SafeIterableMap.Entry<K, V> param1Entry2) {
      super(param1Entry1, param1Entry2);
    }
    
    SafeIterableMap.Entry<K, V> backward(SafeIterableMap.Entry<K, V> param1Entry) {
      return param1Entry.mNext;
    }
    
    SafeIterableMap.Entry<K, V> forward(SafeIterableMap.Entry<K, V> param1Entry) {
      return param1Entry.mPrevious;
    }
  }
  
  static class Entry<K, V> implements Map.Entry<K, V> {
    final K mKey;
    
    Entry<K, V> mNext;
    
    Entry<K, V> mPrevious;
    
    final V mValue;
    
    Entry(K param1K, V param1V) {
      this.mKey = param1K;
      this.mValue = param1V;
    }
    
    public boolean equals(Object param1Object) {
      boolean bool = true;
      if (param1Object == this)
        return true; 
      if (!(param1Object instanceof Entry))
        return false; 
      param1Object = param1Object;
      if (!this.mKey.equals(((Entry)param1Object).mKey) || !this.mValue.equals(((Entry)param1Object).mValue))
        bool = false; 
      return bool;
    }
    
    public K getKey() {
      return this.mKey;
    }
    
    public V getValue() {
      return this.mValue;
    }
    
    public V setValue(V param1V) {
      throw new UnsupportedOperationException("An entry modification is not supported");
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mKey);
      stringBuilder.append("=");
      stringBuilder.append(this.mValue);
      return stringBuilder.toString();
    }
  }
  
  private class IteratorWithAdditions implements Iterator<Map.Entry<K, V>>, SupportRemove<K, V> {
    private boolean mBeforeStart = true;
    
    private SafeIterableMap.Entry<K, V> mCurrent;
    
    private IteratorWithAdditions() {}
    
    public boolean hasNext() {
      boolean bool = this.mBeforeStart;
      boolean bool1 = true;
      boolean bool2 = true;
      if (bool) {
        if (SafeIterableMap.this.mStart == null)
          bool2 = false; 
        return bool2;
      } 
      SafeIterableMap.Entry<K, V> entry = this.mCurrent;
      if (entry != null && entry.mNext != null) {
        bool2 = bool1;
      } else {
        bool2 = false;
      } 
      return bool2;
    }
    
    public Map.Entry<K, V> next() {
      if (this.mBeforeStart) {
        this.mBeforeStart = false;
        this.mCurrent = SafeIterableMap.this.mStart;
      } else {
        SafeIterableMap.Entry<K, V> entry = this.mCurrent;
        if (entry != null) {
          entry = entry.mNext;
        } else {
          entry = null;
        } 
        this.mCurrent = entry;
      } 
      return this.mCurrent;
    }
    
    public void supportRemove(SafeIterableMap.Entry<K, V> param1Entry) {
      SafeIterableMap.Entry<K, V> entry = this.mCurrent;
      if (param1Entry == entry) {
        boolean bool;
        param1Entry = entry.mPrevious;
        this.mCurrent = param1Entry;
        if (param1Entry == null) {
          bool = true;
        } else {
          bool = false;
        } 
        this.mBeforeStart = bool;
      } 
    }
  }
  
  private static abstract class ListIterator<K, V> implements Iterator<Map.Entry<K, V>>, SupportRemove<K, V> {
    SafeIterableMap.Entry<K, V> mExpectedEnd;
    
    SafeIterableMap.Entry<K, V> mNext;
    
    ListIterator(SafeIterableMap.Entry<K, V> param1Entry1, SafeIterableMap.Entry<K, V> param1Entry2) {
      this.mExpectedEnd = param1Entry2;
      this.mNext = param1Entry1;
    }
    
    private SafeIterableMap.Entry<K, V> nextNode() {
      SafeIterableMap.Entry<K, V> entry1 = this.mNext;
      SafeIterableMap.Entry<K, V> entry2 = this.mExpectedEnd;
      return (entry1 == entry2 || entry2 == null) ? null : forward(entry1);
    }
    
    abstract SafeIterableMap.Entry<K, V> backward(SafeIterableMap.Entry<K, V> param1Entry);
    
    abstract SafeIterableMap.Entry<K, V> forward(SafeIterableMap.Entry<K, V> param1Entry);
    
    public boolean hasNext() {
      boolean bool;
      if (this.mNext != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public Map.Entry<K, V> next() {
      SafeIterableMap.Entry<K, V> entry = this.mNext;
      this.mNext = nextNode();
      return entry;
    }
    
    public void supportRemove(SafeIterableMap.Entry<K, V> param1Entry) {
      if (this.mExpectedEnd == param1Entry && param1Entry == this.mNext) {
        this.mNext = null;
        this.mExpectedEnd = null;
      } 
      SafeIterableMap.Entry<K, V> entry = this.mExpectedEnd;
      if (entry == param1Entry)
        this.mExpectedEnd = backward(entry); 
      if (this.mNext == param1Entry)
        this.mNext = nextNode(); 
    }
  }
  
  static interface SupportRemove<K, V> {
    void supportRemove(SafeIterableMap.Entry<K, V> param1Entry);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\core\internal\SafeIterableMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */