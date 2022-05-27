package external.org.apache.commons.lang3.builder;

import external.org.apache.commons.lang3.ArrayUtils;
import external.org.apache.commons.lang3.tuple.Pair;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Set;

public class EqualsBuilder implements Builder<Boolean> {
  private static final ThreadLocal<Set<Pair<IDKey, IDKey>>> REGISTRY = new ThreadLocal<Set<Pair<IDKey, IDKey>>>();
  
  private boolean isEquals = true;
  
  static Pair<IDKey, IDKey> getRegisterPair(Object paramObject1, Object paramObject2) {
    return Pair.of(new IDKey(paramObject1), new IDKey(paramObject2));
  }
  
  static Set<Pair<IDKey, IDKey>> getRegistry() {
    return REGISTRY.get();
  }
  
  static boolean isRegistered(Object paramObject1, Object<IDKey, IDKey> paramObject2) {
    boolean bool;
    Set<Pair<IDKey, IDKey>> set = getRegistry();
    paramObject2 = (Object<IDKey, IDKey>)getRegisterPair(paramObject1, paramObject2);
    paramObject1 = Pair.of(paramObject2.getLeft(), paramObject2.getRight());
    if (set != null && (set.contains(paramObject2) || set.contains(paramObject1))) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private static void reflectionAppend(Object paramObject1, Object paramObject2, Class<?> paramClass, EqualsBuilder paramEqualsBuilder, boolean paramBoolean, String[] paramArrayOfString) {
    if (isRegistered(paramObject1, paramObject2))
      return; 
    try {
      register(paramObject1, paramObject2);
      Field[] arrayOfField = paramClass.getDeclaredFields();
      AccessibleObject.setAccessible((AccessibleObject[])arrayOfField, true);
      for (byte b = 0; b < arrayOfField.length && paramEqualsBuilder.isEquals; b++) {
        Field field = arrayOfField[b];
        if (!ArrayUtils.contains((Object[])paramArrayOfString, field.getName()) && field.getName().indexOf('$') == -1 && (paramBoolean || !Modifier.isTransient(field.getModifiers()))) {
          boolean bool = Modifier.isStatic(field.getModifiers());
          if (!bool)
            try {
              paramEqualsBuilder.append(field.get(paramObject1), field.get(paramObject2));
            } catch (IllegalAccessException illegalAccessException) {
              InternalError internalError = new InternalError();
              this("Unexpected IllegalAccessException");
              throw internalError;
            }  
        } 
      } 
      return;
    } finally {
      unregister(paramObject1, paramObject2);
    } 
  }
  
  public static boolean reflectionEquals(Object paramObject1, Object paramObject2, Collection<String> paramCollection) {
    return reflectionEquals(paramObject1, paramObject2, ReflectionToStringBuilder.toNoNullStringArray(paramCollection));
  }
  
  public static boolean reflectionEquals(Object paramObject1, Object paramObject2, boolean paramBoolean) {
    return reflectionEquals(paramObject1, paramObject2, paramBoolean, null, new String[0]);
  }
  
  public static boolean reflectionEquals(Object paramObject1, Object paramObject2, boolean paramBoolean, Class<?> paramClass, String... paramVarArgs) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: if_acmpne -> 7
    //   5: iconst_1
    //   6: ireturn
    //   7: aload_0
    //   8: ifnull -> 139
    //   11: aload_1
    //   12: ifnonnull -> 18
    //   15: goto -> 139
    //   18: aload_0
    //   19: invokevirtual getClass : ()Ljava/lang/Class;
    //   22: astore #5
    //   24: aload_1
    //   25: invokevirtual getClass : ()Ljava/lang/Class;
    //   28: astore #6
    //   30: aload #5
    //   32: aload_1
    //   33: invokevirtual isInstance : (Ljava/lang/Object;)Z
    //   36: ifeq -> 51
    //   39: aload #6
    //   41: aload_0
    //   42: invokevirtual isInstance : (Ljava/lang/Object;)Z
    //   45: ifne -> 76
    //   48: goto -> 72
    //   51: aload #6
    //   53: aload_0
    //   54: invokevirtual isInstance : (Ljava/lang/Object;)Z
    //   57: ifeq -> 139
    //   60: aload #5
    //   62: aload_1
    //   63: invokevirtual isInstance : (Ljava/lang/Object;)Z
    //   66: ifne -> 72
    //   69: goto -> 76
    //   72: aload #6
    //   74: astore #5
    //   76: new external/org/apache/commons/lang3/builder/EqualsBuilder
    //   79: dup
    //   80: invokespecial <init> : ()V
    //   83: astore #6
    //   85: aload_0
    //   86: aload_1
    //   87: aload #5
    //   89: aload #6
    //   91: iload_2
    //   92: aload #4
    //   94: invokestatic reflectionAppend : (Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Class;Lexternal/org/apache/commons/lang3/builder/EqualsBuilder;Z[Ljava/lang/String;)V
    //   97: aload #5
    //   99: invokevirtual getSuperclass : ()Ljava/lang/Class;
    //   102: ifnull -> 133
    //   105: aload #5
    //   107: aload_3
    //   108: if_acmpeq -> 133
    //   111: aload #5
    //   113: invokevirtual getSuperclass : ()Ljava/lang/Class;
    //   116: astore #5
    //   118: aload_0
    //   119: aload_1
    //   120: aload #5
    //   122: aload #6
    //   124: iload_2
    //   125: aload #4
    //   127: invokestatic reflectionAppend : (Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Class;Lexternal/org/apache/commons/lang3/builder/EqualsBuilder;Z[Ljava/lang/String;)V
    //   130: goto -> 97
    //   133: aload #6
    //   135: invokevirtual isEquals : ()Z
    //   138: ireturn
    //   139: iconst_0
    //   140: ireturn
    //   141: astore_0
    //   142: goto -> 139
    // Exception table:
    //   from	to	target	type
    //   85	97	141	java/lang/IllegalArgumentException
    //   97	105	141	java/lang/IllegalArgumentException
    //   111	130	141	java/lang/IllegalArgumentException
  }
  
  public static boolean reflectionEquals(Object paramObject1, Object paramObject2, String... paramVarArgs) {
    return reflectionEquals(paramObject1, paramObject2, false, null, paramVarArgs);
  }
  
  static void register(Object paramObject1, Object paramObject2) {
    // Byte code:
    //   0: ldc external/org/apache/commons/lang3/builder/EqualsBuilder
    //   2: monitorenter
    //   3: invokestatic getRegistry : ()Ljava/util/Set;
    //   6: ifnonnull -> 26
    //   9: getstatic external/org/apache/commons/lang3/builder/EqualsBuilder.REGISTRY : Ljava/lang/ThreadLocal;
    //   12: astore_2
    //   13: new java/util/HashSet
    //   16: astore_3
    //   17: aload_3
    //   18: invokespecial <init> : ()V
    //   21: aload_2
    //   22: aload_3
    //   23: invokevirtual set : (Ljava/lang/Object;)V
    //   26: ldc external/org/apache/commons/lang3/builder/EqualsBuilder
    //   28: monitorexit
    //   29: invokestatic getRegistry : ()Ljava/util/Set;
    //   32: aload_0
    //   33: aload_1
    //   34: invokestatic getRegisterPair : (Ljava/lang/Object;Ljava/lang/Object;)Lexternal/org/apache/commons/lang3/tuple/Pair;
    //   37: invokeinterface add : (Ljava/lang/Object;)Z
    //   42: pop
    //   43: return
    //   44: astore_0
    //   45: ldc external/org/apache/commons/lang3/builder/EqualsBuilder
    //   47: monitorexit
    //   48: aload_0
    //   49: athrow
    // Exception table:
    //   from	to	target	type
    //   3	26	44	finally
    //   26	29	44	finally
    //   45	48	44	finally
  }
  
  static void unregister(Object<Pair<IDKey, IDKey>> paramObject1, Object paramObject2) {
    // Byte code:
    //   0: invokestatic getRegistry : ()Ljava/util/Set;
    //   3: astore_2
    //   4: aload_2
    //   5: ifnull -> 58
    //   8: aload_2
    //   9: aload_0
    //   10: aload_1
    //   11: invokestatic getRegisterPair : (Ljava/lang/Object;Ljava/lang/Object;)Lexternal/org/apache/commons/lang3/tuple/Pair;
    //   14: invokeinterface remove : (Ljava/lang/Object;)Z
    //   19: pop
    //   20: ldc external/org/apache/commons/lang3/builder/EqualsBuilder
    //   22: monitorenter
    //   23: invokestatic getRegistry : ()Ljava/util/Set;
    //   26: astore_0
    //   27: aload_0
    //   28: ifnull -> 46
    //   31: aload_0
    //   32: invokeinterface isEmpty : ()Z
    //   37: ifeq -> 46
    //   40: getstatic external/org/apache/commons/lang3/builder/EqualsBuilder.REGISTRY : Ljava/lang/ThreadLocal;
    //   43: invokevirtual remove : ()V
    //   46: ldc external/org/apache/commons/lang3/builder/EqualsBuilder
    //   48: monitorexit
    //   49: goto -> 58
    //   52: astore_0
    //   53: ldc external/org/apache/commons/lang3/builder/EqualsBuilder
    //   55: monitorexit
    //   56: aload_0
    //   57: athrow
    //   58: return
    // Exception table:
    //   from	to	target	type
    //   23	27	52	finally
    //   31	46	52	finally
    //   46	49	52	finally
    //   53	56	52	finally
  }
  
  public EqualsBuilder append(byte paramByte1, byte paramByte2) {
    boolean bool;
    if (!this.isEquals)
      return this; 
    if (paramByte1 == paramByte2) {
      bool = true;
    } else {
      bool = false;
    } 
    this.isEquals = bool;
    return this;
  }
  
  public EqualsBuilder append(char paramChar1, char paramChar2) {
    boolean bool;
    if (!this.isEquals)
      return this; 
    if (paramChar1 == paramChar2) {
      bool = true;
    } else {
      bool = false;
    } 
    this.isEquals = bool;
    return this;
  }
  
  public EqualsBuilder append(double paramDouble1, double paramDouble2) {
    return !this.isEquals ? this : append(Double.doubleToLongBits(paramDouble1), Double.doubleToLongBits(paramDouble2));
  }
  
  public EqualsBuilder append(float paramFloat1, float paramFloat2) {
    return !this.isEquals ? this : append(Float.floatToIntBits(paramFloat1), Float.floatToIntBits(paramFloat2));
  }
  
  public EqualsBuilder append(int paramInt1, int paramInt2) {
    boolean bool;
    if (!this.isEquals)
      return this; 
    if (paramInt1 == paramInt2) {
      bool = true;
    } else {
      bool = false;
    } 
    this.isEquals = bool;
    return this;
  }
  
  public EqualsBuilder append(long paramLong1, long paramLong2) {
    boolean bool;
    if (!this.isEquals)
      return this; 
    if (paramLong1 == paramLong2) {
      bool = true;
    } else {
      bool = false;
    } 
    this.isEquals = bool;
    return this;
  }
  
  public EqualsBuilder append(Object paramObject1, Object paramObject2) {
    if (!this.isEquals)
      return this; 
    if (paramObject1 == paramObject2)
      return this; 
    if (paramObject1 == null || paramObject2 == null) {
      setEquals(false);
      return this;
    } 
    if (!paramObject1.getClass().isArray()) {
      this.isEquals = paramObject1.equals(paramObject2);
    } else if (paramObject1.getClass() != paramObject2.getClass()) {
      setEquals(false);
    } else if (paramObject1 instanceof long[]) {
      append((long[])paramObject1, (long[])paramObject2);
    } else if (paramObject1 instanceof int[]) {
      append((int[])paramObject1, (int[])paramObject2);
    } else if (paramObject1 instanceof short[]) {
      append((short[])paramObject1, (short[])paramObject2);
    } else if (paramObject1 instanceof char[]) {
      append((char[])paramObject1, (char[])paramObject2);
    } else if (paramObject1 instanceof byte[]) {
      append((byte[])paramObject1, (byte[])paramObject2);
    } else if (paramObject1 instanceof double[]) {
      append((double[])paramObject1, (double[])paramObject2);
    } else if (paramObject1 instanceof float[]) {
      append((float[])paramObject1, (float[])paramObject2);
    } else if (paramObject1 instanceof boolean[]) {
      append((boolean[])paramObject1, (boolean[])paramObject2);
    } else {
      append((Object[])paramObject1, (Object[])paramObject2);
    } 
    return this;
  }
  
  public EqualsBuilder append(short paramShort1, short paramShort2) {
    boolean bool;
    if (!this.isEquals)
      return this; 
    if (paramShort1 == paramShort2) {
      bool = true;
    } else {
      bool = false;
    } 
    this.isEquals = bool;
    return this;
  }
  
  public EqualsBuilder append(boolean paramBoolean1, boolean paramBoolean2) {
    if (!this.isEquals)
      return this; 
    if (paramBoolean1 == paramBoolean2) {
      paramBoolean1 = true;
    } else {
      paramBoolean1 = false;
    } 
    this.isEquals = paramBoolean1;
    return this;
  }
  
  public EqualsBuilder append(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    if (!this.isEquals)
      return this; 
    if (paramArrayOfbyte1 == paramArrayOfbyte2)
      return this; 
    byte b = 0;
    if (paramArrayOfbyte1 == null || paramArrayOfbyte2 == null) {
      setEquals(false);
      return this;
    } 
    if (paramArrayOfbyte1.length != paramArrayOfbyte2.length) {
      setEquals(false);
      return this;
    } 
    while (b < paramArrayOfbyte1.length && this.isEquals) {
      append(paramArrayOfbyte1[b], paramArrayOfbyte2[b]);
      b++;
    } 
    return this;
  }
  
  public EqualsBuilder append(char[] paramArrayOfchar1, char[] paramArrayOfchar2) {
    if (!this.isEquals)
      return this; 
    if (paramArrayOfchar1 == paramArrayOfchar2)
      return this; 
    byte b = 0;
    if (paramArrayOfchar1 == null || paramArrayOfchar2 == null) {
      setEquals(false);
      return this;
    } 
    if (paramArrayOfchar1.length != paramArrayOfchar2.length) {
      setEquals(false);
      return this;
    } 
    while (b < paramArrayOfchar1.length && this.isEquals) {
      append(paramArrayOfchar1[b], paramArrayOfchar2[b]);
      b++;
    } 
    return this;
  }
  
  public EqualsBuilder append(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
    if (!this.isEquals)
      return this; 
    if (paramArrayOfdouble1 == paramArrayOfdouble2)
      return this; 
    byte b = 0;
    if (paramArrayOfdouble1 == null || paramArrayOfdouble2 == null) {
      setEquals(false);
      return this;
    } 
    if (paramArrayOfdouble1.length != paramArrayOfdouble2.length) {
      setEquals(false);
      return this;
    } 
    while (b < paramArrayOfdouble1.length && this.isEquals) {
      append(paramArrayOfdouble1[b], paramArrayOfdouble2[b]);
      b++;
    } 
    return this;
  }
  
  public EqualsBuilder append(float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
    if (!this.isEquals)
      return this; 
    if (paramArrayOffloat1 == paramArrayOffloat2)
      return this; 
    byte b = 0;
    if (paramArrayOffloat1 == null || paramArrayOffloat2 == null) {
      setEquals(false);
      return this;
    } 
    if (paramArrayOffloat1.length != paramArrayOffloat2.length) {
      setEquals(false);
      return this;
    } 
    while (b < paramArrayOffloat1.length && this.isEquals) {
      append(paramArrayOffloat1[b], paramArrayOffloat2[b]);
      b++;
    } 
    return this;
  }
  
  public EqualsBuilder append(int[] paramArrayOfint1, int[] paramArrayOfint2) {
    if (!this.isEquals)
      return this; 
    if (paramArrayOfint1 == paramArrayOfint2)
      return this; 
    byte b = 0;
    if (paramArrayOfint1 == null || paramArrayOfint2 == null) {
      setEquals(false);
      return this;
    } 
    if (paramArrayOfint1.length != paramArrayOfint2.length) {
      setEquals(false);
      return this;
    } 
    while (b < paramArrayOfint1.length && this.isEquals) {
      append(paramArrayOfint1[b], paramArrayOfint2[b]);
      b++;
    } 
    return this;
  }
  
  public EqualsBuilder append(long[] paramArrayOflong1, long[] paramArrayOflong2) {
    if (!this.isEquals)
      return this; 
    if (paramArrayOflong1 == paramArrayOflong2)
      return this; 
    byte b = 0;
    if (paramArrayOflong1 == null || paramArrayOflong2 == null) {
      setEquals(false);
      return this;
    } 
    if (paramArrayOflong1.length != paramArrayOflong2.length) {
      setEquals(false);
      return this;
    } 
    while (b < paramArrayOflong1.length && this.isEquals) {
      append(paramArrayOflong1[b], paramArrayOflong2[b]);
      b++;
    } 
    return this;
  }
  
  public EqualsBuilder append(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2) {
    if (!this.isEquals)
      return this; 
    if (paramArrayOfObject1 == paramArrayOfObject2)
      return this; 
    byte b = 0;
    if (paramArrayOfObject1 == null || paramArrayOfObject2 == null) {
      setEquals(false);
      return this;
    } 
    if (paramArrayOfObject1.length != paramArrayOfObject2.length) {
      setEquals(false);
      return this;
    } 
    while (b < paramArrayOfObject1.length && this.isEquals) {
      append(paramArrayOfObject1[b], paramArrayOfObject2[b]);
      b++;
    } 
    return this;
  }
  
  public EqualsBuilder append(short[] paramArrayOfshort1, short[] paramArrayOfshort2) {
    if (!this.isEquals)
      return this; 
    if (paramArrayOfshort1 == paramArrayOfshort2)
      return this; 
    byte b = 0;
    if (paramArrayOfshort1 == null || paramArrayOfshort2 == null) {
      setEquals(false);
      return this;
    } 
    if (paramArrayOfshort1.length != paramArrayOfshort2.length) {
      setEquals(false);
      return this;
    } 
    while (b < paramArrayOfshort1.length && this.isEquals) {
      append(paramArrayOfshort1[b], paramArrayOfshort2[b]);
      b++;
    } 
    return this;
  }
  
  public EqualsBuilder append(boolean[] paramArrayOfboolean1, boolean[] paramArrayOfboolean2) {
    if (!this.isEquals)
      return this; 
    if (paramArrayOfboolean1 == paramArrayOfboolean2)
      return this; 
    byte b = 0;
    if (paramArrayOfboolean1 == null || paramArrayOfboolean2 == null) {
      setEquals(false);
      return this;
    } 
    if (paramArrayOfboolean1.length != paramArrayOfboolean2.length) {
      setEquals(false);
      return this;
    } 
    while (b < paramArrayOfboolean1.length && this.isEquals) {
      append(paramArrayOfboolean1[b], paramArrayOfboolean2[b]);
      b++;
    } 
    return this;
  }
  
  public EqualsBuilder appendSuper(boolean paramBoolean) {
    if (!this.isEquals)
      return this; 
    this.isEquals = paramBoolean;
    return this;
  }
  
  public Boolean build() {
    return Boolean.valueOf(isEquals());
  }
  
  public boolean isEquals() {
    return this.isEquals;
  }
  
  public void reset() {
    this.isEquals = true;
  }
  
  protected void setEquals(boolean paramBoolean) {
    this.isEquals = paramBoolean;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\builder\EqualsBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */