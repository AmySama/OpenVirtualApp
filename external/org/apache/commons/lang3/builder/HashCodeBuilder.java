package external.org.apache.commons.lang3.builder;

import external.org.apache.commons.lang3.ArrayUtils;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Set;

public class HashCodeBuilder implements Builder<Integer> {
  private static final ThreadLocal<Set<IDKey>> REGISTRY = new ThreadLocal<Set<IDKey>>();
  
  private final int iConstant;
  
  private int iTotal = 0;
  
  public HashCodeBuilder() {
    this.iConstant = 37;
    this.iTotal = 17;
  }
  
  public HashCodeBuilder(int paramInt1, int paramInt2) {
    if (paramInt1 != 0) {
      if (paramInt1 % 2 != 0) {
        if (paramInt2 != 0) {
          if (paramInt2 % 2 != 0) {
            this.iConstant = paramInt2;
            this.iTotal = paramInt1;
            return;
          } 
          throw new IllegalArgumentException("HashCodeBuilder requires an odd multiplier");
        } 
        throw new IllegalArgumentException("HashCodeBuilder requires a non zero multiplier");
      } 
      throw new IllegalArgumentException("HashCodeBuilder requires an odd initial value");
    } 
    throw new IllegalArgumentException("HashCodeBuilder requires a non zero initial value");
  }
  
  static Set<IDKey> getRegistry() {
    return REGISTRY.get();
  }
  
  static boolean isRegistered(Object paramObject) {
    boolean bool;
    Set<IDKey> set = getRegistry();
    if (set != null && set.contains(new IDKey(paramObject))) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private static void reflectionAppend(Object paramObject, Class<?> paramClass, HashCodeBuilder paramHashCodeBuilder, boolean paramBoolean, String[] paramArrayOfString) {
    if (isRegistered(paramObject))
      return; 
    try {
      register(paramObject);
      Field[] arrayOfField = paramClass.getDeclaredFields();
      AccessibleObject.setAccessible((AccessibleObject[])arrayOfField, true);
      int i = arrayOfField.length;
      for (byte b = 0; b < i; b++) {
        Field field = arrayOfField[b];
        if (!ArrayUtils.contains((Object[])paramArrayOfString, field.getName()) && field.getName().indexOf('$') == -1 && (paramBoolean || !Modifier.isTransient(field.getModifiers()))) {
          boolean bool = Modifier.isStatic(field.getModifiers());
          if (!bool)
            try {
              paramHashCodeBuilder.append(field.get(paramObject));
            } catch (IllegalAccessException illegalAccessException) {
              InternalError internalError = new InternalError();
              this("Unexpected IllegalAccessException");
              throw internalError;
            }  
        } 
      } 
      return;
    } finally {
      unregister(paramObject);
    } 
  }
  
  public static int reflectionHashCode(int paramInt1, int paramInt2, Object paramObject) {
    return reflectionHashCode(paramInt1, paramInt2, paramObject, false, null, new String[0]);
  }
  
  public static int reflectionHashCode(int paramInt1, int paramInt2, Object paramObject, boolean paramBoolean) {
    return reflectionHashCode(paramInt1, paramInt2, paramObject, paramBoolean, null, new String[0]);
  }
  
  public static <T> int reflectionHashCode(int paramInt1, int paramInt2, T paramT, boolean paramBoolean, Class<? super T> paramClass, String... paramVarArgs) {
    if (paramT != null) {
      HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(paramInt1, paramInt2);
      Class<?> clazz = paramT.getClass();
      reflectionAppend(paramT, clazz, hashCodeBuilder, paramBoolean, paramVarArgs);
      while (clazz.getSuperclass() != null && clazz != paramClass) {
        clazz = clazz.getSuperclass();
        reflectionAppend(paramT, clazz, hashCodeBuilder, paramBoolean, paramVarArgs);
      } 
      return hashCodeBuilder.toHashCode();
    } 
    throw new IllegalArgumentException("The object to build a hash code for must not be null");
  }
  
  public static int reflectionHashCode(Object paramObject, Collection<String> paramCollection) {
    return reflectionHashCode(paramObject, ReflectionToStringBuilder.toNoNullStringArray(paramCollection));
  }
  
  public static int reflectionHashCode(Object paramObject, boolean paramBoolean) {
    return reflectionHashCode(17, 37, paramObject, paramBoolean, null, new String[0]);
  }
  
  public static int reflectionHashCode(Object paramObject, String... paramVarArgs) {
    return reflectionHashCode(17, 37, paramObject, false, null, paramVarArgs);
  }
  
  static void register(Object paramObject) {
    // Byte code:
    //   0: ldc external/org/apache/commons/lang3/builder/HashCodeBuilder
    //   2: monitorenter
    //   3: invokestatic getRegistry : ()Ljava/util/Set;
    //   6: ifnonnull -> 26
    //   9: getstatic external/org/apache/commons/lang3/builder/HashCodeBuilder.REGISTRY : Ljava/lang/ThreadLocal;
    //   12: astore_1
    //   13: new java/util/HashSet
    //   16: astore_2
    //   17: aload_2
    //   18: invokespecial <init> : ()V
    //   21: aload_1
    //   22: aload_2
    //   23: invokevirtual set : (Ljava/lang/Object;)V
    //   26: ldc external/org/apache/commons/lang3/builder/HashCodeBuilder
    //   28: monitorexit
    //   29: invokestatic getRegistry : ()Ljava/util/Set;
    //   32: new external/org/apache/commons/lang3/builder/IDKey
    //   35: dup
    //   36: aload_0
    //   37: invokespecial <init> : (Ljava/lang/Object;)V
    //   40: invokeinterface add : (Ljava/lang/Object;)Z
    //   45: pop
    //   46: return
    //   47: astore_0
    //   48: ldc external/org/apache/commons/lang3/builder/HashCodeBuilder
    //   50: monitorexit
    //   51: aload_0
    //   52: athrow
    // Exception table:
    //   from	to	target	type
    //   3	26	47	finally
    //   26	29	47	finally
    //   48	51	47	finally
  }
  
  static void unregister(Object<IDKey> paramObject) {
    // Byte code:
    //   0: invokestatic getRegistry : ()Ljava/util/Set;
    //   3: astore_1
    //   4: aload_1
    //   5: ifnull -> 61
    //   8: aload_1
    //   9: new external/org/apache/commons/lang3/builder/IDKey
    //   12: dup
    //   13: aload_0
    //   14: invokespecial <init> : (Ljava/lang/Object;)V
    //   17: invokeinterface remove : (Ljava/lang/Object;)Z
    //   22: pop
    //   23: ldc external/org/apache/commons/lang3/builder/HashCodeBuilder
    //   25: monitorenter
    //   26: invokestatic getRegistry : ()Ljava/util/Set;
    //   29: astore_0
    //   30: aload_0
    //   31: ifnull -> 49
    //   34: aload_0
    //   35: invokeinterface isEmpty : ()Z
    //   40: ifeq -> 49
    //   43: getstatic external/org/apache/commons/lang3/builder/HashCodeBuilder.REGISTRY : Ljava/lang/ThreadLocal;
    //   46: invokevirtual remove : ()V
    //   49: ldc external/org/apache/commons/lang3/builder/HashCodeBuilder
    //   51: monitorexit
    //   52: goto -> 61
    //   55: astore_0
    //   56: ldc external/org/apache/commons/lang3/builder/HashCodeBuilder
    //   58: monitorexit
    //   59: aload_0
    //   60: athrow
    //   61: return
    // Exception table:
    //   from	to	target	type
    //   26	30	55	finally
    //   34	49	55	finally
    //   49	52	55	finally
    //   56	59	55	finally
  }
  
  public HashCodeBuilder append(byte paramByte) {
    this.iTotal = this.iTotal * this.iConstant + paramByte;
    return this;
  }
  
  public HashCodeBuilder append(char paramChar) {
    this.iTotal = this.iTotal * this.iConstant + paramChar;
    return this;
  }
  
  public HashCodeBuilder append(double paramDouble) {
    return append(Double.doubleToLongBits(paramDouble));
  }
  
  public HashCodeBuilder append(float paramFloat) {
    this.iTotal = this.iTotal * this.iConstant + Float.floatToIntBits(paramFloat);
    return this;
  }
  
  public HashCodeBuilder append(int paramInt) {
    this.iTotal = this.iTotal * this.iConstant + paramInt;
    return this;
  }
  
  public HashCodeBuilder append(long paramLong) {
    this.iTotal = this.iTotal * this.iConstant + (int)(paramLong ^ paramLong >> 32L);
    return this;
  }
  
  public HashCodeBuilder append(Object paramObject) {
    if (paramObject == null) {
      this.iTotal *= this.iConstant;
    } else if (paramObject.getClass().isArray()) {
      if (paramObject instanceof long[]) {
        append((long[])paramObject);
      } else if (paramObject instanceof int[]) {
        append((int[])paramObject);
      } else if (paramObject instanceof short[]) {
        append((short[])paramObject);
      } else if (paramObject instanceof char[]) {
        append((char[])paramObject);
      } else if (paramObject instanceof byte[]) {
        append((byte[])paramObject);
      } else if (paramObject instanceof double[]) {
        append((double[])paramObject);
      } else if (paramObject instanceof float[]) {
        append((float[])paramObject);
      } else if (paramObject instanceof boolean[]) {
        append((boolean[])paramObject);
      } else {
        append((Object[])paramObject);
      } 
    } else {
      this.iTotal = this.iTotal * this.iConstant + paramObject.hashCode();
    } 
    return this;
  }
  
  public HashCodeBuilder append(short paramShort) {
    this.iTotal = this.iTotal * this.iConstant + paramShort;
    return this;
  }
  
  public HashCodeBuilder append(boolean paramBoolean) {
    this.iTotal = this.iTotal * this.iConstant + (paramBoolean ^ true);
    return this;
  }
  
  public HashCodeBuilder append(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte == null) {
      this.iTotal *= this.iConstant;
    } else {
      int i = paramArrayOfbyte.length;
      for (byte b = 0; b < i; b++)
        append(paramArrayOfbyte[b]); 
    } 
    return this;
  }
  
  public HashCodeBuilder append(char[] paramArrayOfchar) {
    if (paramArrayOfchar == null) {
      this.iTotal *= this.iConstant;
    } else {
      int i = paramArrayOfchar.length;
      for (byte b = 0; b < i; b++)
        append(paramArrayOfchar[b]); 
    } 
    return this;
  }
  
  public HashCodeBuilder append(double[] paramArrayOfdouble) {
    if (paramArrayOfdouble == null) {
      this.iTotal *= this.iConstant;
    } else {
      int i = paramArrayOfdouble.length;
      for (byte b = 0; b < i; b++)
        append(paramArrayOfdouble[b]); 
    } 
    return this;
  }
  
  public HashCodeBuilder append(float[] paramArrayOffloat) {
    if (paramArrayOffloat == null) {
      this.iTotal *= this.iConstant;
    } else {
      int i = paramArrayOffloat.length;
      for (byte b = 0; b < i; b++)
        append(paramArrayOffloat[b]); 
    } 
    return this;
  }
  
  public HashCodeBuilder append(int[] paramArrayOfint) {
    if (paramArrayOfint == null) {
      this.iTotal *= this.iConstant;
    } else {
      int i = paramArrayOfint.length;
      for (byte b = 0; b < i; b++)
        append(paramArrayOfint[b]); 
    } 
    return this;
  }
  
  public HashCodeBuilder append(long[] paramArrayOflong) {
    if (paramArrayOflong == null) {
      this.iTotal *= this.iConstant;
    } else {
      int i = paramArrayOflong.length;
      for (byte b = 0; b < i; b++)
        append(paramArrayOflong[b]); 
    } 
    return this;
  }
  
  public HashCodeBuilder append(Object[] paramArrayOfObject) {
    if (paramArrayOfObject == null) {
      this.iTotal *= this.iConstant;
    } else {
      int i = paramArrayOfObject.length;
      for (byte b = 0; b < i; b++)
        append(paramArrayOfObject[b]); 
    } 
    return this;
  }
  
  public HashCodeBuilder append(short[] paramArrayOfshort) {
    if (paramArrayOfshort == null) {
      this.iTotal *= this.iConstant;
    } else {
      int i = paramArrayOfshort.length;
      for (byte b = 0; b < i; b++)
        append(paramArrayOfshort[b]); 
    } 
    return this;
  }
  
  public HashCodeBuilder append(boolean[] paramArrayOfboolean) {
    if (paramArrayOfboolean == null) {
      this.iTotal *= this.iConstant;
    } else {
      int i = paramArrayOfboolean.length;
      for (byte b = 0; b < i; b++)
        append(paramArrayOfboolean[b]); 
    } 
    return this;
  }
  
  public HashCodeBuilder appendSuper(int paramInt) {
    this.iTotal = this.iTotal * this.iConstant + paramInt;
    return this;
  }
  
  public Integer build() {
    return Integer.valueOf(toHashCode());
  }
  
  public int hashCode() {
    return toHashCode();
  }
  
  public int toHashCode() {
    return this.iTotal;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\builder\HashCodeBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */