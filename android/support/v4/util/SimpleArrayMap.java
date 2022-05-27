package android.support.v4.util;

import java.util.ConcurrentModificationException;
import java.util.Map;

public class SimpleArrayMap<K, V> {
  private static final int BASE_SIZE = 4;
  
  private static final int CACHE_SIZE = 10;
  
  private static final boolean CONCURRENT_MODIFICATION_EXCEPTIONS = true;
  
  private static final boolean DEBUG = false;
  
  private static final String TAG = "ArrayMap";
  
  static Object[] mBaseCache;
  
  static int mBaseCacheSize;
  
  static Object[] mTwiceBaseCache;
  
  static int mTwiceBaseCacheSize;
  
  Object[] mArray;
  
  int[] mHashes;
  
  int mSize;
  
  public SimpleArrayMap() {
    this.mHashes = ContainerHelpers.EMPTY_INTS;
    this.mArray = ContainerHelpers.EMPTY_OBJECTS;
    this.mSize = 0;
  }
  
  public SimpleArrayMap(int paramInt) {
    if (paramInt == 0) {
      this.mHashes = ContainerHelpers.EMPTY_INTS;
      this.mArray = ContainerHelpers.EMPTY_OBJECTS;
    } else {
      allocArrays(paramInt);
    } 
    this.mSize = 0;
  }
  
  public SimpleArrayMap(SimpleArrayMap<K, V> paramSimpleArrayMap) {
    this();
    if (paramSimpleArrayMap != null)
      putAll(paramSimpleArrayMap); 
  }
  
  private void allocArrays(int paramInt) {
    // Byte code:
    //   0: iload_1
    //   1: bipush #8
    //   3: if_icmpne -> 75
    //   6: ldc android/support/v4/util/ArrayMap
    //   8: monitorenter
    //   9: getstatic android/support/v4/util/SimpleArrayMap.mTwiceBaseCache : [Ljava/lang/Object;
    //   12: ifnull -> 63
    //   15: getstatic android/support/v4/util/SimpleArrayMap.mTwiceBaseCache : [Ljava/lang/Object;
    //   18: astore_2
    //   19: aload_0
    //   20: aload_2
    //   21: putfield mArray : [Ljava/lang/Object;
    //   24: aload_2
    //   25: iconst_0
    //   26: aaload
    //   27: checkcast [Ljava/lang/Object;
    //   30: putstatic android/support/v4/util/SimpleArrayMap.mTwiceBaseCache : [Ljava/lang/Object;
    //   33: aload_0
    //   34: aload_2
    //   35: iconst_1
    //   36: aaload
    //   37: checkcast [I
    //   40: putfield mHashes : [I
    //   43: aload_2
    //   44: iconst_1
    //   45: aconst_null
    //   46: aastore
    //   47: aload_2
    //   48: iconst_0
    //   49: aconst_null
    //   50: aastore
    //   51: getstatic android/support/v4/util/SimpleArrayMap.mTwiceBaseCacheSize : I
    //   54: iconst_1
    //   55: isub
    //   56: putstatic android/support/v4/util/SimpleArrayMap.mTwiceBaseCacheSize : I
    //   59: ldc android/support/v4/util/ArrayMap
    //   61: monitorexit
    //   62: return
    //   63: ldc android/support/v4/util/ArrayMap
    //   65: monitorexit
    //   66: goto -> 149
    //   69: astore_2
    //   70: ldc android/support/v4/util/ArrayMap
    //   72: monitorexit
    //   73: aload_2
    //   74: athrow
    //   75: iload_1
    //   76: iconst_4
    //   77: if_icmpne -> 149
    //   80: ldc android/support/v4/util/ArrayMap
    //   82: monitorenter
    //   83: getstatic android/support/v4/util/SimpleArrayMap.mBaseCache : [Ljava/lang/Object;
    //   86: ifnull -> 137
    //   89: getstatic android/support/v4/util/SimpleArrayMap.mBaseCache : [Ljava/lang/Object;
    //   92: astore_2
    //   93: aload_0
    //   94: aload_2
    //   95: putfield mArray : [Ljava/lang/Object;
    //   98: aload_2
    //   99: iconst_0
    //   100: aaload
    //   101: checkcast [Ljava/lang/Object;
    //   104: putstatic android/support/v4/util/SimpleArrayMap.mBaseCache : [Ljava/lang/Object;
    //   107: aload_0
    //   108: aload_2
    //   109: iconst_1
    //   110: aaload
    //   111: checkcast [I
    //   114: putfield mHashes : [I
    //   117: aload_2
    //   118: iconst_1
    //   119: aconst_null
    //   120: aastore
    //   121: aload_2
    //   122: iconst_0
    //   123: aconst_null
    //   124: aastore
    //   125: getstatic android/support/v4/util/SimpleArrayMap.mBaseCacheSize : I
    //   128: iconst_1
    //   129: isub
    //   130: putstatic android/support/v4/util/SimpleArrayMap.mBaseCacheSize : I
    //   133: ldc android/support/v4/util/ArrayMap
    //   135: monitorexit
    //   136: return
    //   137: ldc android/support/v4/util/ArrayMap
    //   139: monitorexit
    //   140: goto -> 149
    //   143: astore_2
    //   144: ldc android/support/v4/util/ArrayMap
    //   146: monitorexit
    //   147: aload_2
    //   148: athrow
    //   149: aload_0
    //   150: iload_1
    //   151: newarray int
    //   153: putfield mHashes : [I
    //   156: aload_0
    //   157: iload_1
    //   158: iconst_1
    //   159: ishl
    //   160: anewarray java/lang/Object
    //   163: putfield mArray : [Ljava/lang/Object;
    //   166: return
    // Exception table:
    //   from	to	target	type
    //   9	43	69	finally
    //   51	62	69	finally
    //   63	66	69	finally
    //   70	73	69	finally
    //   83	117	143	finally
    //   125	136	143	finally
    //   137	140	143	finally
    //   144	147	143	finally
  }
  
  private static int binarySearchHashes(int[] paramArrayOfint, int paramInt1, int paramInt2) {
    try {
      return ContainerHelpers.binarySearch(paramArrayOfint, paramInt1, paramInt2);
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      throw new ConcurrentModificationException();
    } 
  }
  
  private static void freeArrays(int[] paramArrayOfint, Object[] paramArrayOfObject, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: arraylength
    //   2: bipush #8
    //   4: if_icmpne -> 73
    //   7: ldc android/support/v4/util/ArrayMap
    //   9: monitorenter
    //   10: getstatic android/support/v4/util/SimpleArrayMap.mTwiceBaseCacheSize : I
    //   13: bipush #10
    //   15: if_icmpge -> 61
    //   18: aload_1
    //   19: iconst_0
    //   20: getstatic android/support/v4/util/SimpleArrayMap.mTwiceBaseCache : [Ljava/lang/Object;
    //   23: aastore
    //   24: aload_1
    //   25: iconst_1
    //   26: aload_0
    //   27: aastore
    //   28: iload_2
    //   29: iconst_1
    //   30: ishl
    //   31: iconst_1
    //   32: isub
    //   33: istore_2
    //   34: iload_2
    //   35: iconst_2
    //   36: if_icmplt -> 49
    //   39: aload_1
    //   40: iload_2
    //   41: aconst_null
    //   42: aastore
    //   43: iinc #2, -1
    //   46: goto -> 34
    //   49: aload_1
    //   50: putstatic android/support/v4/util/SimpleArrayMap.mTwiceBaseCache : [Ljava/lang/Object;
    //   53: getstatic android/support/v4/util/SimpleArrayMap.mTwiceBaseCacheSize : I
    //   56: iconst_1
    //   57: iadd
    //   58: putstatic android/support/v4/util/SimpleArrayMap.mTwiceBaseCacheSize : I
    //   61: ldc android/support/v4/util/ArrayMap
    //   63: monitorexit
    //   64: goto -> 145
    //   67: astore_0
    //   68: ldc android/support/v4/util/ArrayMap
    //   70: monitorexit
    //   71: aload_0
    //   72: athrow
    //   73: aload_0
    //   74: arraylength
    //   75: iconst_4
    //   76: if_icmpne -> 145
    //   79: ldc android/support/v4/util/ArrayMap
    //   81: monitorenter
    //   82: getstatic android/support/v4/util/SimpleArrayMap.mBaseCacheSize : I
    //   85: bipush #10
    //   87: if_icmpge -> 133
    //   90: aload_1
    //   91: iconst_0
    //   92: getstatic android/support/v4/util/SimpleArrayMap.mBaseCache : [Ljava/lang/Object;
    //   95: aastore
    //   96: aload_1
    //   97: iconst_1
    //   98: aload_0
    //   99: aastore
    //   100: iload_2
    //   101: iconst_1
    //   102: ishl
    //   103: iconst_1
    //   104: isub
    //   105: istore_2
    //   106: iload_2
    //   107: iconst_2
    //   108: if_icmplt -> 121
    //   111: aload_1
    //   112: iload_2
    //   113: aconst_null
    //   114: aastore
    //   115: iinc #2, -1
    //   118: goto -> 106
    //   121: aload_1
    //   122: putstatic android/support/v4/util/SimpleArrayMap.mBaseCache : [Ljava/lang/Object;
    //   125: getstatic android/support/v4/util/SimpleArrayMap.mBaseCacheSize : I
    //   128: iconst_1
    //   129: iadd
    //   130: putstatic android/support/v4/util/SimpleArrayMap.mBaseCacheSize : I
    //   133: ldc android/support/v4/util/ArrayMap
    //   135: monitorexit
    //   136: goto -> 145
    //   139: astore_0
    //   140: ldc android/support/v4/util/ArrayMap
    //   142: monitorexit
    //   143: aload_0
    //   144: athrow
    //   145: return
    // Exception table:
    //   from	to	target	type
    //   10	24	67	finally
    //   49	61	67	finally
    //   61	64	67	finally
    //   68	71	67	finally
    //   82	96	139	finally
    //   121	133	139	finally
    //   133	136	139	finally
    //   140	143	139	finally
  }
  
  public void clear() {
    int i = this.mSize;
    if (i > 0) {
      int[] arrayOfInt = this.mHashes;
      Object[] arrayOfObject = this.mArray;
      this.mHashes = ContainerHelpers.EMPTY_INTS;
      this.mArray = ContainerHelpers.EMPTY_OBJECTS;
      this.mSize = 0;
      freeArrays(arrayOfInt, arrayOfObject, i);
    } 
    if (this.mSize <= 0)
      return; 
    throw new ConcurrentModificationException();
  }
  
  public boolean containsKey(Object paramObject) {
    boolean bool;
    if (indexOfKey(paramObject) >= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean containsValue(Object paramObject) {
    boolean bool;
    if (indexOfValue(paramObject) >= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void ensureCapacity(int paramInt) {
    int i = this.mSize;
    int[] arrayOfInt = this.mHashes;
    if (arrayOfInt.length < paramInt) {
      Object[] arrayOfObject = this.mArray;
      allocArrays(paramInt);
      if (this.mSize > 0) {
        System.arraycopy(arrayOfInt, 0, this.mHashes, 0, i);
        System.arraycopy(arrayOfObject, 0, this.mArray, 0, i << 1);
      } 
      freeArrays(arrayOfInt, arrayOfObject, i);
    } 
    if (this.mSize == i)
      return; 
    throw new ConcurrentModificationException();
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject instanceof SimpleArrayMap) {
      paramObject = paramObject;
      if (size() != paramObject.size())
        return false; 
      byte b = 0;
      try {
        while (b < this.mSize) {
          K k = keyAt(b);
          V v = valueAt(b);
          Object object = paramObject.get(k);
          if (v == null) {
            if (object != null || !paramObject.containsKey(k))
              return false; 
          } else {
            boolean bool = v.equals(object);
            if (!bool)
              return false; 
          } 
          b++;
        } 
        return true;
      } catch (NullPointerException|ClassCastException nullPointerException) {
        return false;
      } 
    } 
    if (nullPointerException instanceof Map) {
      Map map = (Map)nullPointerException;
      if (size() != map.size())
        return false; 
      byte b = 0;
      try {
        while (b < this.mSize) {
          nullPointerException = (NullPointerException)keyAt(b);
          V v = valueAt(b);
          Object object = map.get(nullPointerException);
          if (v == null) {
            if (object != null || !map.containsKey(nullPointerException))
              return false; 
          } else {
            boolean bool = v.equals(object);
            if (!bool)
              return false; 
          } 
          b++;
        } 
        return true;
      } catch (NullPointerException|ClassCastException nullPointerException1) {}
    } 
    return false;
  }
  
  public V get(Object paramObject) {
    int i = indexOfKey(paramObject);
    if (i >= 0) {
      paramObject = this.mArray[(i << 1) + 1];
    } else {
      paramObject = null;
    } 
    return (V)paramObject;
  }
  
  public int hashCode() {
    int[] arrayOfInt = this.mHashes;
    Object[] arrayOfObject = this.mArray;
    int i = this.mSize;
    boolean bool = true;
    byte b = 0;
    int j = 0;
    while (b < i) {
      int m;
      Object object = arrayOfObject[bool];
      int k = arrayOfInt[b];
      if (object == null) {
        m = 0;
      } else {
        m = object.hashCode();
      } 
      j += m ^ k;
      b++;
      bool += true;
    } 
    return j;
  }
  
  int indexOf(Object paramObject, int paramInt) {
    int i = this.mSize;
    if (i == 0)
      return -1; 
    int j = binarySearchHashes(this.mHashes, i, paramInt);
    if (j < 0)
      return j; 
    if (paramObject.equals(this.mArray[j << 1]))
      return j; 
    int k;
    for (k = j + 1; k < i && this.mHashes[k] == paramInt; k++) {
      if (paramObject.equals(this.mArray[k << 1]))
        return k; 
    } 
    for (i = j - 1; i >= 0 && this.mHashes[i] == paramInt; i--) {
      if (paramObject.equals(this.mArray[i << 1]))
        return i; 
    } 
    return k;
  }
  
  public int indexOfKey(Object paramObject) {
    int i;
    if (paramObject == null) {
      i = indexOfNull();
    } else {
      i = indexOf(paramObject, paramObject.hashCode());
    } 
    return i;
  }
  
  int indexOfNull() {
    int i = this.mSize;
    if (i == 0)
      return -1; 
    int j = binarySearchHashes(this.mHashes, i, 0);
    if (j < 0)
      return j; 
    if (this.mArray[j << 1] == null)
      return j; 
    int k;
    for (k = j + 1; k < i && this.mHashes[k] == 0; k++) {
      if (this.mArray[k << 1] == null)
        return k; 
    } 
    for (i = j - 1; i >= 0 && this.mHashes[i] == 0; i--) {
      if (this.mArray[i << 1] == null)
        return i; 
    } 
    return k;
  }
  
  int indexOfValue(Object paramObject) {
    int i = this.mSize * 2;
    Object[] arrayOfObject = this.mArray;
    if (paramObject == null) {
      for (byte b = 1; b < i; b += 2) {
        if (arrayOfObject[b] == null)
          return b >> 1; 
      } 
    } else {
      for (byte b = 1; b < i; b += 2) {
        if (paramObject.equals(arrayOfObject[b]))
          return b >> 1; 
      } 
    } 
    return -1;
  }
  
  public boolean isEmpty() {
    boolean bool;
    if (this.mSize <= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public K keyAt(int paramInt) {
    return (K)this.mArray[paramInt << 1];
  }
  
  public V put(K paramK, V paramV) {
    int k;
    int i = this.mSize;
    if (paramK == null) {
      j = indexOfNull();
      k = 0;
    } else {
      k = paramK.hashCode();
      j = indexOf(paramK, k);
    } 
    if (j >= 0) {
      j = (j << 1) + 1;
      Object[] arrayOfObject = this.mArray;
      paramK = (K)arrayOfObject[j];
      arrayOfObject[j] = paramV;
      return (V)paramK;
    } 
    int m = j;
    if (i >= this.mHashes.length) {
      j = 4;
      if (i >= 8) {
        j = (i >> 1) + i;
      } else if (i >= 4) {
        j = 8;
      } 
      int[] arrayOfInt = this.mHashes;
      Object[] arrayOfObject = this.mArray;
      allocArrays(j);
      if (i == this.mSize) {
        int[] arrayOfInt1 = this.mHashes;
        if (arrayOfInt1.length > 0) {
          System.arraycopy(arrayOfInt, 0, arrayOfInt1, 0, arrayOfInt.length);
          System.arraycopy(arrayOfObject, 0, this.mArray, 0, arrayOfObject.length);
        } 
        freeArrays(arrayOfInt, arrayOfObject, i);
      } else {
        throw new ConcurrentModificationException();
      } 
    } 
    if (m < i) {
      int[] arrayOfInt = this.mHashes;
      j = m + 1;
      System.arraycopy(arrayOfInt, m, arrayOfInt, j, i - m);
      Object[] arrayOfObject = this.mArray;
      System.arraycopy(arrayOfObject, m << 1, arrayOfObject, j << 1, this.mSize - m << 1);
    } 
    int j = this.mSize;
    if (i == j) {
      int[] arrayOfInt = this.mHashes;
      if (m < arrayOfInt.length) {
        arrayOfInt[m] = k;
        Object[] arrayOfObject = this.mArray;
        k = m << 1;
        arrayOfObject[k] = paramK;
        arrayOfObject[k + 1] = paramV;
        this.mSize = j + 1;
        return null;
      } 
    } 
    throw new ConcurrentModificationException();
  }
  
  public void putAll(SimpleArrayMap<? extends K, ? extends V> paramSimpleArrayMap) {
    int i = paramSimpleArrayMap.mSize;
    ensureCapacity(this.mSize + i);
    int j = this.mSize;
    byte b = 0;
    if (j == 0) {
      if (i > 0) {
        System.arraycopy(paramSimpleArrayMap.mHashes, 0, this.mHashes, 0, i);
        System.arraycopy(paramSimpleArrayMap.mArray, 0, this.mArray, 0, i << 1);
        this.mSize = i;
      } 
    } else {
      while (b < i) {
        put(paramSimpleArrayMap.keyAt(b), paramSimpleArrayMap.valueAt(b));
        b++;
      } 
    } 
  }
  
  public V remove(Object paramObject) {
    int i = indexOfKey(paramObject);
    return (i >= 0) ? removeAt(i) : null;
  }
  
  public V removeAt(int paramInt) {
    Object[] arrayOfObject = this.mArray;
    int i = paramInt << 1;
    Object object = arrayOfObject[i + 1];
    int j = this.mSize;
    int k = 0;
    if (j <= 1) {
      freeArrays(this.mHashes, arrayOfObject, j);
      this.mHashes = ContainerHelpers.EMPTY_INTS;
      this.mArray = ContainerHelpers.EMPTY_OBJECTS;
      paramInt = k;
    } else {
      int m = j - 1;
      int[] arrayOfInt = this.mHashes;
      int n = arrayOfInt.length;
      k = 8;
      if (n > 8 && j < arrayOfInt.length / 3) {
        if (j > 8)
          k = j + (j >> 1); 
        arrayOfInt = this.mHashes;
        Object[] arrayOfObject1 = this.mArray;
        allocArrays(k);
        if (j == this.mSize) {
          if (paramInt > 0) {
            System.arraycopy(arrayOfInt, 0, this.mHashes, 0, paramInt);
            System.arraycopy(arrayOfObject1, 0, this.mArray, 0, i);
          } 
          if (paramInt < m) {
            k = paramInt + 1;
            int[] arrayOfInt1 = this.mHashes;
            n = m - paramInt;
            System.arraycopy(arrayOfInt, k, arrayOfInt1, paramInt, n);
            System.arraycopy(arrayOfObject1, k << 1, this.mArray, i, n << 1);
          } 
        } else {
          throw new ConcurrentModificationException();
        } 
      } else {
        if (paramInt < m) {
          arrayOfInt = this.mHashes;
          n = paramInt + 1;
          k = m - paramInt;
          System.arraycopy(arrayOfInt, n, arrayOfInt, paramInt, k);
          Object[] arrayOfObject2 = this.mArray;
          System.arraycopy(arrayOfObject2, n << 1, arrayOfObject2, i, k << 1);
        } 
        Object[] arrayOfObject1 = this.mArray;
        paramInt = m << 1;
        arrayOfObject1[paramInt] = null;
        arrayOfObject1[paramInt + 1] = null;
      } 
      paramInt = m;
    } 
    if (j == this.mSize) {
      this.mSize = paramInt;
      return (V)object;
    } 
    throw new ConcurrentModificationException();
  }
  
  public V setValueAt(int paramInt, V paramV) {
    paramInt = (paramInt << 1) + 1;
    Object[] arrayOfObject = this.mArray;
    Object object = arrayOfObject[paramInt];
    arrayOfObject[paramInt] = paramV;
    return (V)object;
  }
  
  public int size() {
    return this.mSize;
  }
  
  public String toString() {
    if (isEmpty())
      return "{}"; 
    StringBuilder stringBuilder = new StringBuilder(this.mSize * 28);
    stringBuilder.append('{');
    for (byte b = 0; b < this.mSize; b++) {
      if (b > 0)
        stringBuilder.append(", "); 
      V v = (V)keyAt(b);
      if (v != this) {
        stringBuilder.append(v);
      } else {
        stringBuilder.append("(this Map)");
      } 
      stringBuilder.append('=');
      v = valueAt(b);
      if (v != this) {
        stringBuilder.append(v);
      } else {
        stringBuilder.append("(this Map)");
      } 
    } 
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public V valueAt(int paramInt) {
    return (V)this.mArray[(paramInt << 1) + 1];
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v\\util\SimpleArrayMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */