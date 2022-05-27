package android.support.v4.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V> {
  private int createCount;
  
  private int evictionCount;
  
  private int hitCount;
  
  private final LinkedHashMap<K, V> map;
  
  private int maxSize;
  
  private int missCount;
  
  private int putCount;
  
  private int size;
  
  public LruCache(int paramInt) {
    if (paramInt > 0) {
      this.maxSize = paramInt;
      this.map = new LinkedHashMap<K, V>(0, 0.75F, true);
      return;
    } 
    throw new IllegalArgumentException("maxSize <= 0");
  }
  
  private int safeSizeOf(K paramK, V paramV) {
    int i = sizeOf(paramK, paramV);
    if (i >= 0)
      return i; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Negative size: ");
    stringBuilder.append(paramK);
    stringBuilder.append("=");
    stringBuilder.append(paramV);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  protected V create(K paramK) {
    return null;
  }
  
  public final int createCount() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield createCount : I
    //   6: istore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: iload_1
    //   10: ireturn
    //   11: astore_2
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_2
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	11	finally
  }
  
  protected void entryRemoved(boolean paramBoolean, K paramK, V paramV1, V paramV2) {}
  
  public final void evictAll() {
    trimToSize(-1);
  }
  
  public final int evictionCount() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield evictionCount : I
    //   6: istore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: iload_1
    //   10: ireturn
    //   11: astore_2
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_2
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	11	finally
  }
  
  public final V get(K paramK) {
    // Byte code:
    //   0: aload_1
    //   1: ifnull -> 147
    //   4: aload_0
    //   5: monitorenter
    //   6: aload_0
    //   7: getfield map : Ljava/util/LinkedHashMap;
    //   10: aload_1
    //   11: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   14: astore_2
    //   15: aload_2
    //   16: ifnull -> 33
    //   19: aload_0
    //   20: aload_0
    //   21: getfield hitCount : I
    //   24: iconst_1
    //   25: iadd
    //   26: putfield hitCount : I
    //   29: aload_0
    //   30: monitorexit
    //   31: aload_2
    //   32: areturn
    //   33: aload_0
    //   34: aload_0
    //   35: getfield missCount : I
    //   38: iconst_1
    //   39: iadd
    //   40: putfield missCount : I
    //   43: aload_0
    //   44: monitorexit
    //   45: aload_0
    //   46: aload_1
    //   47: invokevirtual create : (Ljava/lang/Object;)Ljava/lang/Object;
    //   50: astore_3
    //   51: aload_3
    //   52: ifnonnull -> 57
    //   55: aconst_null
    //   56: areturn
    //   57: aload_0
    //   58: monitorenter
    //   59: aload_0
    //   60: aload_0
    //   61: getfield createCount : I
    //   64: iconst_1
    //   65: iadd
    //   66: putfield createCount : I
    //   69: aload_0
    //   70: getfield map : Ljava/util/LinkedHashMap;
    //   73: aload_1
    //   74: aload_3
    //   75: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   78: astore_2
    //   79: aload_2
    //   80: ifnull -> 96
    //   83: aload_0
    //   84: getfield map : Ljava/util/LinkedHashMap;
    //   87: aload_1
    //   88: aload_2
    //   89: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   92: pop
    //   93: goto -> 111
    //   96: aload_0
    //   97: aload_0
    //   98: getfield size : I
    //   101: aload_0
    //   102: aload_1
    //   103: aload_3
    //   104: invokespecial safeSizeOf : (Ljava/lang/Object;Ljava/lang/Object;)I
    //   107: iadd
    //   108: putfield size : I
    //   111: aload_0
    //   112: monitorexit
    //   113: aload_2
    //   114: ifnull -> 127
    //   117: aload_0
    //   118: iconst_0
    //   119: aload_1
    //   120: aload_3
    //   121: aload_2
    //   122: invokevirtual entryRemoved : (ZLjava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
    //   125: aload_2
    //   126: areturn
    //   127: aload_0
    //   128: aload_0
    //   129: getfield maxSize : I
    //   132: invokevirtual trimToSize : (I)V
    //   135: aload_3
    //   136: areturn
    //   137: astore_1
    //   138: aload_0
    //   139: monitorexit
    //   140: aload_1
    //   141: athrow
    //   142: astore_1
    //   143: aload_0
    //   144: monitorexit
    //   145: aload_1
    //   146: athrow
    //   147: new java/lang/NullPointerException
    //   150: dup
    //   151: ldc 'key == null'
    //   153: invokespecial <init> : (Ljava/lang/String;)V
    //   156: athrow
    // Exception table:
    //   from	to	target	type
    //   6	15	142	finally
    //   19	31	142	finally
    //   33	45	142	finally
    //   59	79	137	finally
    //   83	93	137	finally
    //   96	111	137	finally
    //   111	113	137	finally
    //   138	140	137	finally
    //   143	145	142	finally
  }
  
  public final int hitCount() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield hitCount : I
    //   6: istore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: iload_1
    //   10: ireturn
    //   11: astore_2
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_2
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	11	finally
  }
  
  public final int maxSize() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield maxSize : I
    //   6: istore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: iload_1
    //   10: ireturn
    //   11: astore_2
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_2
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	11	finally
  }
  
  public final int missCount() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield missCount : I
    //   6: istore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: iload_1
    //   10: ireturn
    //   11: astore_2
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_2
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	11	finally
  }
  
  public final V put(K paramK, V paramV) {
    // Byte code:
    //   0: aload_1
    //   1: ifnull -> 93
    //   4: aload_2
    //   5: ifnull -> 93
    //   8: aload_0
    //   9: monitorenter
    //   10: aload_0
    //   11: aload_0
    //   12: getfield putCount : I
    //   15: iconst_1
    //   16: iadd
    //   17: putfield putCount : I
    //   20: aload_0
    //   21: aload_0
    //   22: getfield size : I
    //   25: aload_0
    //   26: aload_1
    //   27: aload_2
    //   28: invokespecial safeSizeOf : (Ljava/lang/Object;Ljava/lang/Object;)I
    //   31: iadd
    //   32: putfield size : I
    //   35: aload_0
    //   36: getfield map : Ljava/util/LinkedHashMap;
    //   39: aload_1
    //   40: aload_2
    //   41: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   44: astore_3
    //   45: aload_3
    //   46: ifnull -> 64
    //   49: aload_0
    //   50: aload_0
    //   51: getfield size : I
    //   54: aload_0
    //   55: aload_1
    //   56: aload_3
    //   57: invokespecial safeSizeOf : (Ljava/lang/Object;Ljava/lang/Object;)I
    //   60: isub
    //   61: putfield size : I
    //   64: aload_0
    //   65: monitorexit
    //   66: aload_3
    //   67: ifnull -> 78
    //   70: aload_0
    //   71: iconst_0
    //   72: aload_1
    //   73: aload_3
    //   74: aload_2
    //   75: invokevirtual entryRemoved : (ZLjava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
    //   78: aload_0
    //   79: aload_0
    //   80: getfield maxSize : I
    //   83: invokevirtual trimToSize : (I)V
    //   86: aload_3
    //   87: areturn
    //   88: astore_1
    //   89: aload_0
    //   90: monitorexit
    //   91: aload_1
    //   92: athrow
    //   93: new java/lang/NullPointerException
    //   96: dup
    //   97: ldc 'key == null || value == null'
    //   99: invokespecial <init> : (Ljava/lang/String;)V
    //   102: athrow
    // Exception table:
    //   from	to	target	type
    //   10	45	88	finally
    //   49	64	88	finally
    //   64	66	88	finally
    //   89	91	88	finally
  }
  
  public final int putCount() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield putCount : I
    //   6: istore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: iload_1
    //   10: ireturn
    //   11: astore_2
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_2
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	11	finally
  }
  
  public final V remove(K paramK) {
    // Byte code:
    //   0: aload_1
    //   1: ifnull -> 55
    //   4: aload_0
    //   5: monitorenter
    //   6: aload_0
    //   7: getfield map : Ljava/util/LinkedHashMap;
    //   10: aload_1
    //   11: invokevirtual remove : (Ljava/lang/Object;)Ljava/lang/Object;
    //   14: astore_2
    //   15: aload_2
    //   16: ifnull -> 34
    //   19: aload_0
    //   20: aload_0
    //   21: getfield size : I
    //   24: aload_0
    //   25: aload_1
    //   26: aload_2
    //   27: invokespecial safeSizeOf : (Ljava/lang/Object;Ljava/lang/Object;)I
    //   30: isub
    //   31: putfield size : I
    //   34: aload_0
    //   35: monitorexit
    //   36: aload_2
    //   37: ifnull -> 48
    //   40: aload_0
    //   41: iconst_0
    //   42: aload_1
    //   43: aload_2
    //   44: aconst_null
    //   45: invokevirtual entryRemoved : (ZLjava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
    //   48: aload_2
    //   49: areturn
    //   50: astore_1
    //   51: aload_0
    //   52: monitorexit
    //   53: aload_1
    //   54: athrow
    //   55: new java/lang/NullPointerException
    //   58: dup
    //   59: ldc 'key == null'
    //   61: invokespecial <init> : (Ljava/lang/String;)V
    //   64: athrow
    // Exception table:
    //   from	to	target	type
    //   6	15	50	finally
    //   19	34	50	finally
    //   34	36	50	finally
    //   51	53	50	finally
  }
  
  public void resize(int paramInt) {
    // Byte code:
    //   0: iload_1
    //   1: ifle -> 24
    //   4: aload_0
    //   5: monitorenter
    //   6: aload_0
    //   7: iload_1
    //   8: putfield maxSize : I
    //   11: aload_0
    //   12: monitorexit
    //   13: aload_0
    //   14: iload_1
    //   15: invokevirtual trimToSize : (I)V
    //   18: return
    //   19: astore_2
    //   20: aload_0
    //   21: monitorexit
    //   22: aload_2
    //   23: athrow
    //   24: new java/lang/IllegalArgumentException
    //   27: dup
    //   28: ldc 'maxSize <= 0'
    //   30: invokespecial <init> : (Ljava/lang/String;)V
    //   33: athrow
    // Exception table:
    //   from	to	target	type
    //   6	13	19	finally
    //   20	22	19	finally
  }
  
  public final int size() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield size : I
    //   6: istore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: iload_1
    //   10: ireturn
    //   11: astore_2
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_2
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	11	finally
  }
  
  protected int sizeOf(K paramK, V paramV) {
    return 1;
  }
  
  public final Map<K, V> snapshot() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new java/util/LinkedHashMap
    //   5: dup
    //   6: aload_0
    //   7: getfield map : Ljava/util/LinkedHashMap;
    //   10: invokespecial <init> : (Ljava/util/Map;)V
    //   13: astore_1
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_1
    //   17: areturn
    //   18: astore_1
    //   19: aload_0
    //   20: monitorexit
    //   21: aload_1
    //   22: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	18	finally
  }
  
  public final String toString() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield hitCount : I
    //   6: aload_0
    //   7: getfield missCount : I
    //   10: iadd
    //   11: istore_1
    //   12: iload_1
    //   13: ifeq -> 29
    //   16: aload_0
    //   17: getfield hitCount : I
    //   20: bipush #100
    //   22: imul
    //   23: iload_1
    //   24: idiv
    //   25: istore_1
    //   26: goto -> 31
    //   29: iconst_0
    //   30: istore_1
    //   31: getstatic java/util/Locale.US : Ljava/util/Locale;
    //   34: ldc 'LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]'
    //   36: iconst_4
    //   37: anewarray java/lang/Object
    //   40: dup
    //   41: iconst_0
    //   42: aload_0
    //   43: getfield maxSize : I
    //   46: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   49: aastore
    //   50: dup
    //   51: iconst_1
    //   52: aload_0
    //   53: getfield hitCount : I
    //   56: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   59: aastore
    //   60: dup
    //   61: iconst_2
    //   62: aload_0
    //   63: getfield missCount : I
    //   66: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   69: aastore
    //   70: dup
    //   71: iconst_3
    //   72: iload_1
    //   73: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   76: aastore
    //   77: invokestatic format : (Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   80: astore_2
    //   81: aload_0
    //   82: monitorexit
    //   83: aload_2
    //   84: areturn
    //   85: astore_2
    //   86: aload_0
    //   87: monitorexit
    //   88: aload_2
    //   89: athrow
    // Exception table:
    //   from	to	target	type
    //   2	12	85	finally
    //   16	26	85	finally
    //   31	81	85	finally
  }
  
  public void trimToSize(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield size : I
    //   6: iflt -> 132
    //   9: aload_0
    //   10: getfield map : Ljava/util/LinkedHashMap;
    //   13: invokevirtual isEmpty : ()Z
    //   16: ifeq -> 26
    //   19: aload_0
    //   20: getfield size : I
    //   23: ifne -> 132
    //   26: aload_0
    //   27: getfield size : I
    //   30: iload_1
    //   31: if_icmple -> 129
    //   34: aload_0
    //   35: getfield map : Ljava/util/LinkedHashMap;
    //   38: invokevirtual isEmpty : ()Z
    //   41: ifeq -> 47
    //   44: goto -> 129
    //   47: aload_0
    //   48: getfield map : Ljava/util/LinkedHashMap;
    //   51: invokevirtual entrySet : ()Ljava/util/Set;
    //   54: invokeinterface iterator : ()Ljava/util/Iterator;
    //   59: invokeinterface next : ()Ljava/lang/Object;
    //   64: checkcast java/util/Map$Entry
    //   67: astore_2
    //   68: aload_2
    //   69: invokeinterface getKey : ()Ljava/lang/Object;
    //   74: astore_3
    //   75: aload_2
    //   76: invokeinterface getValue : ()Ljava/lang/Object;
    //   81: astore_2
    //   82: aload_0
    //   83: getfield map : Ljava/util/LinkedHashMap;
    //   86: aload_3
    //   87: invokevirtual remove : (Ljava/lang/Object;)Ljava/lang/Object;
    //   90: pop
    //   91: aload_0
    //   92: aload_0
    //   93: getfield size : I
    //   96: aload_0
    //   97: aload_3
    //   98: aload_2
    //   99: invokespecial safeSizeOf : (Ljava/lang/Object;Ljava/lang/Object;)I
    //   102: isub
    //   103: putfield size : I
    //   106: aload_0
    //   107: aload_0
    //   108: getfield evictionCount : I
    //   111: iconst_1
    //   112: iadd
    //   113: putfield evictionCount : I
    //   116: aload_0
    //   117: monitorexit
    //   118: aload_0
    //   119: iconst_1
    //   120: aload_3
    //   121: aload_2
    //   122: aconst_null
    //   123: invokevirtual entryRemoved : (ZLjava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
    //   126: goto -> 0
    //   129: aload_0
    //   130: monitorexit
    //   131: return
    //   132: new java/lang/IllegalStateException
    //   135: astore_2
    //   136: new java/lang/StringBuilder
    //   139: astore_3
    //   140: aload_3
    //   141: invokespecial <init> : ()V
    //   144: aload_3
    //   145: aload_0
    //   146: invokevirtual getClass : ()Ljava/lang/Class;
    //   149: invokevirtual getName : ()Ljava/lang/String;
    //   152: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   155: pop
    //   156: aload_3
    //   157: ldc '.sizeOf() is reporting inconsistent results!'
    //   159: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   162: pop
    //   163: aload_2
    //   164: aload_3
    //   165: invokevirtual toString : ()Ljava/lang/String;
    //   168: invokespecial <init> : (Ljava/lang/String;)V
    //   171: aload_2
    //   172: athrow
    //   173: astore_3
    //   174: aload_0
    //   175: monitorexit
    //   176: aload_3
    //   177: athrow
    // Exception table:
    //   from	to	target	type
    //   2	26	173	finally
    //   26	44	173	finally
    //   47	118	173	finally
    //   129	131	173	finally
    //   132	173	173	finally
    //   174	176	173	finally
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v\\util\LruCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */