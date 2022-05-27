package external.org.apache.commons.lang3;

import external.org.apache.commons.lang3.builder.EqualsBuilder;
import external.org.apache.commons.lang3.builder.HashCodeBuilder;
import external.org.apache.commons.lang3.builder.ToStringBuilder;
import external.org.apache.commons.lang3.builder.ToStringStyle;
import external.org.apache.commons.lang3.mutable.MutableInt;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class ArrayUtils {
  public static final boolean[] EMPTY_BOOLEAN_ARRAY;
  
  public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY;
  
  public static final byte[] EMPTY_BYTE_ARRAY;
  
  public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY;
  
  public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY;
  
  public static final char[] EMPTY_CHAR_ARRAY;
  
  public static final Class<?>[] EMPTY_CLASS_ARRAY;
  
  public static final double[] EMPTY_DOUBLE_ARRAY;
  
  public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY;
  
  public static final float[] EMPTY_FLOAT_ARRAY;
  
  public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY;
  
  public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY;
  
  public static final int[] EMPTY_INT_ARRAY;
  
  public static final long[] EMPTY_LONG_ARRAY;
  
  public static final Long[] EMPTY_LONG_OBJECT_ARRAY;
  
  public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
  
  public static final short[] EMPTY_SHORT_ARRAY;
  
  public static final Short[] EMPTY_SHORT_OBJECT_ARRAY;
  
  public static final String[] EMPTY_STRING_ARRAY;
  
  public static final int INDEX_NOT_FOUND = -1;
  
  static {
    EMPTY_CLASS_ARRAY = new Class[0];
    EMPTY_STRING_ARRAY = new String[0];
    EMPTY_LONG_ARRAY = new long[0];
    EMPTY_LONG_OBJECT_ARRAY = new Long[0];
    EMPTY_INT_ARRAY = new int[0];
    EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
    EMPTY_SHORT_ARRAY = new short[0];
    EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
    EMPTY_BYTE_ARRAY = new byte[0];
    EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
    EMPTY_DOUBLE_ARRAY = new double[0];
    EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
    EMPTY_FLOAT_ARRAY = new float[0];
    EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
    EMPTY_BOOLEAN_ARRAY = new boolean[0];
    EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
    EMPTY_CHAR_ARRAY = new char[0];
    EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];
  }
  
  private static Object add(Object paramObject1, int paramInt, Object paramObject2, Class<?> paramClass) {
    if (paramObject1 == null) {
      if (paramInt == 0) {
        paramObject1 = Array.newInstance(paramClass, 1);
        Array.set(paramObject1, 0, paramObject2);
        return paramObject1;
      } 
      paramObject1 = new StringBuilder();
      paramObject1.append("Index: ");
      paramObject1.append(paramInt);
      paramObject1.append(", Length: 0");
      throw new IndexOutOfBoundsException(paramObject1.toString());
    } 
    int i = Array.getLength(paramObject1);
    if (paramInt <= i && paramInt >= 0) {
      Object object = Array.newInstance(paramClass, i + 1);
      System.arraycopy(paramObject1, 0, object, 0, paramInt);
      Array.set(object, paramInt, paramObject2);
      if (paramInt < i)
        System.arraycopy(paramObject1, paramInt, object, paramInt + 1, i - paramInt); 
      return object;
    } 
    paramObject1 = new StringBuilder();
    paramObject1.append("Index: ");
    paramObject1.append(paramInt);
    paramObject1.append(", Length: ");
    paramObject1.append(i);
    throw new IndexOutOfBoundsException(paramObject1.toString());
  }
  
  public static byte[] add(byte[] paramArrayOfbyte, byte paramByte) {
    paramArrayOfbyte = (byte[])copyArrayGrow1(paramArrayOfbyte, byte.class);
    paramArrayOfbyte[paramArrayOfbyte.length - 1] = (byte)paramByte;
    return paramArrayOfbyte;
  }
  
  public static byte[] add(byte[] paramArrayOfbyte, int paramInt, byte paramByte) {
    return (byte[])add(paramArrayOfbyte, paramInt, Byte.valueOf(paramByte), byte.class);
  }
  
  public static char[] add(char[] paramArrayOfchar, char paramChar) {
    paramArrayOfchar = (char[])copyArrayGrow1(paramArrayOfchar, char.class);
    paramArrayOfchar[paramArrayOfchar.length - 1] = (char)paramChar;
    return paramArrayOfchar;
  }
  
  public static char[] add(char[] paramArrayOfchar, int paramInt, char paramChar) {
    return (char[])add(paramArrayOfchar, paramInt, Character.valueOf(paramChar), char.class);
  }
  
  public static double[] add(double[] paramArrayOfdouble, double paramDouble) {
    paramArrayOfdouble = (double[])copyArrayGrow1(paramArrayOfdouble, double.class);
    paramArrayOfdouble[paramArrayOfdouble.length - 1] = paramDouble;
    return paramArrayOfdouble;
  }
  
  public static double[] add(double[] paramArrayOfdouble, int paramInt, double paramDouble) {
    return (double[])add(paramArrayOfdouble, paramInt, Double.valueOf(paramDouble), double.class);
  }
  
  public static float[] add(float[] paramArrayOffloat, float paramFloat) {
    paramArrayOffloat = (float[])copyArrayGrow1(paramArrayOffloat, float.class);
    paramArrayOffloat[paramArrayOffloat.length - 1] = paramFloat;
    return paramArrayOffloat;
  }
  
  public static float[] add(float[] paramArrayOffloat, int paramInt, float paramFloat) {
    return (float[])add(paramArrayOffloat, paramInt, Float.valueOf(paramFloat), float.class);
  }
  
  public static int[] add(int[] paramArrayOfint, int paramInt) {
    paramArrayOfint = (int[])copyArrayGrow1(paramArrayOfint, int.class);
    paramArrayOfint[paramArrayOfint.length - 1] = paramInt;
    return paramArrayOfint;
  }
  
  public static int[] add(int[] paramArrayOfint, int paramInt1, int paramInt2) {
    return (int[])add(paramArrayOfint, paramInt1, Integer.valueOf(paramInt2), int.class);
  }
  
  public static long[] add(long[] paramArrayOflong, int paramInt, long paramLong) {
    return (long[])add(paramArrayOflong, paramInt, Long.valueOf(paramLong), long.class);
  }
  
  public static long[] add(long[] paramArrayOflong, long paramLong) {
    paramArrayOflong = (long[])copyArrayGrow1(paramArrayOflong, long.class);
    paramArrayOflong[paramArrayOflong.length - 1] = paramLong;
    return paramArrayOflong;
  }
  
  public static <T> T[] add(T[] paramArrayOfT, int paramInt, T paramT) {
    Class<?> clazz;
    if (paramArrayOfT != null) {
      clazz = paramArrayOfT.getClass().getComponentType();
    } else {
      if (paramT != null) {
        clazz = paramT.getClass();
        return (T[])add(paramArrayOfT, paramInt, paramT, clazz);
      } 
      throw new IllegalArgumentException("Array and element cannot both be null");
    } 
    return (T[])add(paramArrayOfT, paramInt, paramT, clazz);
  }
  
  public static <T> T[] add(T[] paramArrayOfT, T paramT) {
    Class<?> clazz;
    if (paramArrayOfT != null) {
      clazz = paramArrayOfT.getClass();
    } else {
      if (paramT != null) {
        clazz = paramT.getClass();
        paramArrayOfT = (T[])copyArrayGrow1(paramArrayOfT, clazz);
        paramArrayOfT[paramArrayOfT.length - 1] = paramT;
        return paramArrayOfT;
      } 
      throw new IllegalArgumentException("Arguments cannot both be null");
    } 
    paramArrayOfT = (T[])copyArrayGrow1(paramArrayOfT, clazz);
    paramArrayOfT[paramArrayOfT.length - 1] = paramT;
    return paramArrayOfT;
  }
  
  public static short[] add(short[] paramArrayOfshort, int paramInt, short paramShort) {
    return (short[])add(paramArrayOfshort, paramInt, Short.valueOf(paramShort), short.class);
  }
  
  public static short[] add(short[] paramArrayOfshort, short paramShort) {
    paramArrayOfshort = (short[])copyArrayGrow1(paramArrayOfshort, short.class);
    paramArrayOfshort[paramArrayOfshort.length - 1] = (short)paramShort;
    return paramArrayOfshort;
  }
  
  public static boolean[] add(boolean[] paramArrayOfboolean, int paramInt, boolean paramBoolean) {
    return (boolean[])add(paramArrayOfboolean, paramInt, Boolean.valueOf(paramBoolean), boolean.class);
  }
  
  public static boolean[] add(boolean[] paramArrayOfboolean, boolean paramBoolean) {
    paramArrayOfboolean = (boolean[])copyArrayGrow1(paramArrayOfboolean, boolean.class);
    paramArrayOfboolean[paramArrayOfboolean.length - 1] = paramBoolean;
    return paramArrayOfboolean;
  }
  
  public static byte[] addAll(byte[] paramArrayOfbyte1, byte... paramVarArgs1) {
    if (paramArrayOfbyte1 == null)
      return clone(paramVarArgs1); 
    if (paramVarArgs1 == null)
      return clone(paramArrayOfbyte1); 
    byte[] arrayOfByte = new byte[paramArrayOfbyte1.length + paramVarArgs1.length];
    System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte, 0, paramArrayOfbyte1.length);
    System.arraycopy(paramVarArgs1, 0, arrayOfByte, paramArrayOfbyte1.length, paramVarArgs1.length);
    return arrayOfByte;
  }
  
  public static char[] addAll(char[] paramArrayOfchar1, char... paramVarArgs1) {
    if (paramArrayOfchar1 == null)
      return clone(paramVarArgs1); 
    if (paramVarArgs1 == null)
      return clone(paramArrayOfchar1); 
    char[] arrayOfChar = new char[paramArrayOfchar1.length + paramVarArgs1.length];
    System.arraycopy(paramArrayOfchar1, 0, arrayOfChar, 0, paramArrayOfchar1.length);
    System.arraycopy(paramVarArgs1, 0, arrayOfChar, paramArrayOfchar1.length, paramVarArgs1.length);
    return arrayOfChar;
  }
  
  public static double[] addAll(double[] paramArrayOfdouble1, double... paramVarArgs1) {
    if (paramArrayOfdouble1 == null)
      return clone(paramVarArgs1); 
    if (paramVarArgs1 == null)
      return clone(paramArrayOfdouble1); 
    double[] arrayOfDouble = new double[paramArrayOfdouble1.length + paramVarArgs1.length];
    System.arraycopy(paramArrayOfdouble1, 0, arrayOfDouble, 0, paramArrayOfdouble1.length);
    System.arraycopy(paramVarArgs1, 0, arrayOfDouble, paramArrayOfdouble1.length, paramVarArgs1.length);
    return arrayOfDouble;
  }
  
  public static float[] addAll(float[] paramArrayOffloat1, float... paramVarArgs1) {
    if (paramArrayOffloat1 == null)
      return clone(paramVarArgs1); 
    if (paramVarArgs1 == null)
      return clone(paramArrayOffloat1); 
    float[] arrayOfFloat = new float[paramArrayOffloat1.length + paramVarArgs1.length];
    System.arraycopy(paramArrayOffloat1, 0, arrayOfFloat, 0, paramArrayOffloat1.length);
    System.arraycopy(paramVarArgs1, 0, arrayOfFloat, paramArrayOffloat1.length, paramVarArgs1.length);
    return arrayOfFloat;
  }
  
  public static int[] addAll(int[] paramArrayOfint1, int... paramVarArgs1) {
    if (paramArrayOfint1 == null)
      return clone(paramVarArgs1); 
    if (paramVarArgs1 == null)
      return clone(paramArrayOfint1); 
    int[] arrayOfInt = new int[paramArrayOfint1.length + paramVarArgs1.length];
    System.arraycopy(paramArrayOfint1, 0, arrayOfInt, 0, paramArrayOfint1.length);
    System.arraycopy(paramVarArgs1, 0, arrayOfInt, paramArrayOfint1.length, paramVarArgs1.length);
    return arrayOfInt;
  }
  
  public static long[] addAll(long[] paramArrayOflong1, long... paramVarArgs1) {
    if (paramArrayOflong1 == null)
      return clone(paramVarArgs1); 
    if (paramVarArgs1 == null)
      return clone(paramArrayOflong1); 
    long[] arrayOfLong = new long[paramArrayOflong1.length + paramVarArgs1.length];
    System.arraycopy(paramArrayOflong1, 0, arrayOfLong, 0, paramArrayOflong1.length);
    System.arraycopy(paramVarArgs1, 0, arrayOfLong, paramArrayOflong1.length, paramVarArgs1.length);
    return arrayOfLong;
  }
  
  public static <T> T[] addAll(T[] paramArrayOfT1, T... paramVarArgs1) {
    if (paramArrayOfT1 == null)
      return clone(paramVarArgs1); 
    if (paramVarArgs1 == null)
      return clone(paramArrayOfT1); 
    Class<?> clazz = paramArrayOfT1.getClass().getComponentType();
    Object[] arrayOfObject = (Object[])Array.newInstance(clazz, paramArrayOfT1.length + paramVarArgs1.length);
    System.arraycopy(paramArrayOfT1, 0, arrayOfObject, 0, paramArrayOfT1.length);
    try {
      System.arraycopy(paramVarArgs1, 0, arrayOfObject, paramArrayOfT1.length, paramVarArgs1.length);
      return (T[])arrayOfObject;
    } catch (ArrayStoreException arrayStoreException) {
      Class<?> clazz1 = paramVarArgs1.getClass().getComponentType();
      if (!clazz.isAssignableFrom(clazz1)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot store ");
        stringBuilder.append(clazz1.getName());
        stringBuilder.append(" in an array of ");
        stringBuilder.append(clazz.getName());
        throw new IllegalArgumentException(stringBuilder.toString(), arrayStoreException);
      } 
      throw arrayStoreException;
    } 
  }
  
  public static short[] addAll(short[] paramArrayOfshort1, short... paramVarArgs1) {
    if (paramArrayOfshort1 == null)
      return clone(paramVarArgs1); 
    if (paramVarArgs1 == null)
      return clone(paramArrayOfshort1); 
    short[] arrayOfShort = new short[paramArrayOfshort1.length + paramVarArgs1.length];
    System.arraycopy(paramArrayOfshort1, 0, arrayOfShort, 0, paramArrayOfshort1.length);
    System.arraycopy(paramVarArgs1, 0, arrayOfShort, paramArrayOfshort1.length, paramVarArgs1.length);
    return arrayOfShort;
  }
  
  public static boolean[] addAll(boolean[] paramArrayOfboolean1, boolean... paramVarArgs1) {
    if (paramArrayOfboolean1 == null)
      return clone(paramVarArgs1); 
    if (paramVarArgs1 == null)
      return clone(paramArrayOfboolean1); 
    boolean[] arrayOfBoolean = new boolean[paramArrayOfboolean1.length + paramVarArgs1.length];
    System.arraycopy(paramArrayOfboolean1, 0, arrayOfBoolean, 0, paramArrayOfboolean1.length);
    System.arraycopy(paramVarArgs1, 0, arrayOfBoolean, paramArrayOfboolean1.length, paramVarArgs1.length);
    return arrayOfBoolean;
  }
  
  public static byte[] clone(byte[] paramArrayOfbyte) {
    return (paramArrayOfbyte == null) ? null : (byte[])paramArrayOfbyte.clone();
  }
  
  public static char[] clone(char[] paramArrayOfchar) {
    return (paramArrayOfchar == null) ? null : (char[])paramArrayOfchar.clone();
  }
  
  public static double[] clone(double[] paramArrayOfdouble) {
    return (paramArrayOfdouble == null) ? null : (double[])paramArrayOfdouble.clone();
  }
  
  public static float[] clone(float[] paramArrayOffloat) {
    return (paramArrayOffloat == null) ? null : (float[])paramArrayOffloat.clone();
  }
  
  public static int[] clone(int[] paramArrayOfint) {
    return (paramArrayOfint == null) ? null : (int[])paramArrayOfint.clone();
  }
  
  public static long[] clone(long[] paramArrayOflong) {
    return (paramArrayOflong == null) ? null : (long[])paramArrayOflong.clone();
  }
  
  public static <T> T[] clone(T[] paramArrayOfT) {
    return (T[])((paramArrayOfT == null) ? null : (Object[])paramArrayOfT.clone());
  }
  
  public static short[] clone(short[] paramArrayOfshort) {
    return (paramArrayOfshort == null) ? null : (short[])paramArrayOfshort.clone();
  }
  
  public static boolean[] clone(boolean[] paramArrayOfboolean) {
    return (paramArrayOfboolean == null) ? null : (boolean[])paramArrayOfboolean.clone();
  }
  
  public static boolean contains(byte[] paramArrayOfbyte, byte paramByte) {
    boolean bool;
    if (indexOf(paramArrayOfbyte, paramByte) != -1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean contains(char[] paramArrayOfchar, char paramChar) {
    boolean bool;
    if (indexOf(paramArrayOfchar, paramChar) != -1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean contains(double[] paramArrayOfdouble, double paramDouble) {
    boolean bool;
    if (indexOf(paramArrayOfdouble, paramDouble) != -1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean contains(double[] paramArrayOfdouble, double paramDouble1, double paramDouble2) {
    boolean bool;
    if (indexOf(paramArrayOfdouble, paramDouble1, 0, paramDouble2) != -1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean contains(float[] paramArrayOffloat, float paramFloat) {
    boolean bool;
    if (indexOf(paramArrayOffloat, paramFloat) != -1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean contains(int[] paramArrayOfint, int paramInt) {
    boolean bool;
    if (indexOf(paramArrayOfint, paramInt) != -1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean contains(long[] paramArrayOflong, long paramLong) {
    boolean bool;
    if (indexOf(paramArrayOflong, paramLong) != -1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean contains(Object[] paramArrayOfObject, Object paramObject) {
    boolean bool;
    if (indexOf(paramArrayOfObject, paramObject) != -1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean contains(short[] paramArrayOfshort, short paramShort) {
    boolean bool;
    if (indexOf(paramArrayOfshort, paramShort) != -1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean contains(boolean[] paramArrayOfboolean, boolean paramBoolean) {
    if (indexOf(paramArrayOfboolean, paramBoolean) != -1) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    } 
    return paramBoolean;
  }
  
  private static Object copyArrayGrow1(Object paramObject, Class<?> paramClass) {
    Object object;
    if (paramObject != null) {
      int i = Array.getLength(paramObject);
      object = Array.newInstance(paramObject.getClass().getComponentType(), i + 1);
      System.arraycopy(paramObject, 0, object, 0, i);
      return object;
    } 
    return Array.newInstance((Class<?>)object, 1);
  }
  
  private static int[] extractIndices(HashSet<Integer> paramHashSet) {
    int[] arrayOfInt = new int[paramHashSet.size()];
    Iterator<Integer> iterator = paramHashSet.iterator();
    for (byte b = 0; iterator.hasNext(); b++)
      arrayOfInt[b] = ((Integer)iterator.next()).intValue(); 
    return arrayOfInt;
  }
  
  public static int getLength(Object paramObject) {
    return (paramObject == null) ? 0 : Array.getLength(paramObject);
  }
  
  public static int hashCode(Object paramObject) {
    return (new HashCodeBuilder()).append(paramObject).toHashCode();
  }
  
  public static int indexOf(byte[] paramArrayOfbyte, byte paramByte) {
    return indexOf(paramArrayOfbyte, paramByte, 0);
  }
  
  public static int indexOf(byte[] paramArrayOfbyte, byte paramByte, int paramInt) {
    if (paramArrayOfbyte == null)
      return -1; 
    int i = paramInt;
    if (paramInt < 0)
      i = 0; 
    while (i < paramArrayOfbyte.length) {
      if (paramByte == paramArrayOfbyte[i])
        return i; 
      i++;
    } 
    return -1;
  }
  
  public static int indexOf(char[] paramArrayOfchar, char paramChar) {
    return indexOf(paramArrayOfchar, paramChar, 0);
  }
  
  public static int indexOf(char[] paramArrayOfchar, char paramChar, int paramInt) {
    if (paramArrayOfchar == null)
      return -1; 
    int i = paramInt;
    if (paramInt < 0)
      i = 0; 
    while (i < paramArrayOfchar.length) {
      if (paramChar == paramArrayOfchar[i])
        return i; 
      i++;
    } 
    return -1;
  }
  
  public static int indexOf(double[] paramArrayOfdouble, double paramDouble) {
    return indexOf(paramArrayOfdouble, paramDouble, 0);
  }
  
  public static int indexOf(double[] paramArrayOfdouble, double paramDouble1, double paramDouble2) {
    return indexOf(paramArrayOfdouble, paramDouble1, 0, paramDouble2);
  }
  
  public static int indexOf(double[] paramArrayOfdouble, double paramDouble, int paramInt) {
    if (isEmpty(paramArrayOfdouble))
      return -1; 
    int i = paramInt;
    if (paramInt < 0)
      i = 0; 
    while (i < paramArrayOfdouble.length) {
      if (paramDouble == paramArrayOfdouble[i])
        return i; 
      i++;
    } 
    return -1;
  }
  
  public static int indexOf(double[] paramArrayOfdouble, double paramDouble1, int paramInt, double paramDouble2) {
    if (isEmpty(paramArrayOfdouble))
      return -1; 
    int i = paramInt;
    if (paramInt < 0)
      i = 0; 
    while (i < paramArrayOfdouble.length) {
      if (paramArrayOfdouble[i] >= paramDouble1 - paramDouble2 && paramArrayOfdouble[i] <= paramDouble1 + paramDouble2)
        return i; 
      i++;
    } 
    return -1;
  }
  
  public static int indexOf(float[] paramArrayOffloat, float paramFloat) {
    return indexOf(paramArrayOffloat, paramFloat, 0);
  }
  
  public static int indexOf(float[] paramArrayOffloat, float paramFloat, int paramInt) {
    if (isEmpty(paramArrayOffloat))
      return -1; 
    int i = paramInt;
    if (paramInt < 0)
      i = 0; 
    while (i < paramArrayOffloat.length) {
      if (paramFloat == paramArrayOffloat[i])
        return i; 
      i++;
    } 
    return -1;
  }
  
  public static int indexOf(int[] paramArrayOfint, int paramInt) {
    return indexOf(paramArrayOfint, paramInt, 0);
  }
  
  public static int indexOf(int[] paramArrayOfint, int paramInt1, int paramInt2) {
    if (paramArrayOfint == null)
      return -1; 
    int i = paramInt2;
    if (paramInt2 < 0)
      i = 0; 
    while (i < paramArrayOfint.length) {
      if (paramInt1 == paramArrayOfint[i])
        return i; 
      i++;
    } 
    return -1;
  }
  
  public static int indexOf(long[] paramArrayOflong, long paramLong) {
    return indexOf(paramArrayOflong, paramLong, 0);
  }
  
  public static int indexOf(long[] paramArrayOflong, long paramLong, int paramInt) {
    if (paramArrayOflong == null)
      return -1; 
    int i = paramInt;
    if (paramInt < 0)
      i = 0; 
    while (i < paramArrayOflong.length) {
      if (paramLong == paramArrayOflong[i])
        return i; 
      i++;
    } 
    return -1;
  }
  
  public static int indexOf(Object[] paramArrayOfObject, Object paramObject) {
    return indexOf(paramArrayOfObject, paramObject, 0);
  }
  
  public static int indexOf(Object[] paramArrayOfObject, Object paramObject, int paramInt) {
    if (paramArrayOfObject == null)
      return -1; 
    int i = paramInt;
    if (paramInt < 0)
      i = 0; 
    if (paramObject == null) {
      while (i < paramArrayOfObject.length) {
        if (paramArrayOfObject[i] == null)
          return i; 
        i++;
      } 
    } else if (paramArrayOfObject.getClass().getComponentType().isInstance(paramObject)) {
      while (i < paramArrayOfObject.length) {
        if (paramObject.equals(paramArrayOfObject[i]))
          return i; 
        i++;
      } 
    } 
    return -1;
  }
  
  public static int indexOf(short[] paramArrayOfshort, short paramShort) {
    return indexOf(paramArrayOfshort, paramShort, 0);
  }
  
  public static int indexOf(short[] paramArrayOfshort, short paramShort, int paramInt) {
    if (paramArrayOfshort == null)
      return -1; 
    int i = paramInt;
    if (paramInt < 0)
      i = 0; 
    while (i < paramArrayOfshort.length) {
      if (paramShort == paramArrayOfshort[i])
        return i; 
      i++;
    } 
    return -1;
  }
  
  public static int indexOf(boolean[] paramArrayOfboolean, boolean paramBoolean) {
    return indexOf(paramArrayOfboolean, paramBoolean, 0);
  }
  
  public static int indexOf(boolean[] paramArrayOfboolean, boolean paramBoolean, int paramInt) {
    if (isEmpty(paramArrayOfboolean))
      return -1; 
    int i = paramInt;
    if (paramInt < 0)
      i = 0; 
    while (i < paramArrayOfboolean.length) {
      if (paramBoolean == paramArrayOfboolean[i])
        return i; 
      i++;
    } 
    return -1;
  }
  
  public static boolean isEmpty(byte[] paramArrayOfbyte) {
    return (paramArrayOfbyte == null || paramArrayOfbyte.length == 0);
  }
  
  public static boolean isEmpty(char[] paramArrayOfchar) {
    return (paramArrayOfchar == null || paramArrayOfchar.length == 0);
  }
  
  public static boolean isEmpty(double[] paramArrayOfdouble) {
    return (paramArrayOfdouble == null || paramArrayOfdouble.length == 0);
  }
  
  public static boolean isEmpty(float[] paramArrayOffloat) {
    return (paramArrayOffloat == null || paramArrayOffloat.length == 0);
  }
  
  public static boolean isEmpty(int[] paramArrayOfint) {
    return (paramArrayOfint == null || paramArrayOfint.length == 0);
  }
  
  public static boolean isEmpty(long[] paramArrayOflong) {
    return (paramArrayOflong == null || paramArrayOflong.length == 0);
  }
  
  public static boolean isEmpty(Object[] paramArrayOfObject) {
    return (paramArrayOfObject == null || paramArrayOfObject.length == 0);
  }
  
  public static boolean isEmpty(short[] paramArrayOfshort) {
    return (paramArrayOfshort == null || paramArrayOfshort.length == 0);
  }
  
  public static boolean isEmpty(boolean[] paramArrayOfboolean) {
    return (paramArrayOfboolean == null || paramArrayOfboolean.length == 0);
  }
  
  public static boolean isEquals(Object paramObject1, Object paramObject2) {
    return (new EqualsBuilder()).append(paramObject1, paramObject2).isEquals();
  }
  
  public static boolean isNotEmpty(byte[] paramArrayOfbyte) {
    boolean bool;
    if (paramArrayOfbyte != null && paramArrayOfbyte.length != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isNotEmpty(char[] paramArrayOfchar) {
    boolean bool;
    if (paramArrayOfchar != null && paramArrayOfchar.length != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isNotEmpty(double[] paramArrayOfdouble) {
    boolean bool;
    if (paramArrayOfdouble != null && paramArrayOfdouble.length != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isNotEmpty(float[] paramArrayOffloat) {
    boolean bool;
    if (paramArrayOffloat != null && paramArrayOffloat.length != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isNotEmpty(int[] paramArrayOfint) {
    boolean bool;
    if (paramArrayOfint != null && paramArrayOfint.length != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isNotEmpty(long[] paramArrayOflong) {
    boolean bool;
    if (paramArrayOflong != null && paramArrayOflong.length != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static <T> boolean isNotEmpty(T[] paramArrayOfT) {
    boolean bool;
    if (paramArrayOfT != null && paramArrayOfT.length != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isNotEmpty(short[] paramArrayOfshort) {
    boolean bool;
    if (paramArrayOfshort != null && paramArrayOfshort.length != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isNotEmpty(boolean[] paramArrayOfboolean) {
    boolean bool;
    if (paramArrayOfboolean != null && paramArrayOfboolean.length != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isSameLength(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    return !((paramArrayOfbyte1 == null && paramArrayOfbyte2 != null && paramArrayOfbyte2.length > 0) || (paramArrayOfbyte2 == null && paramArrayOfbyte1 != null && paramArrayOfbyte1.length > 0) || (paramArrayOfbyte1 != null && paramArrayOfbyte2 != null && paramArrayOfbyte1.length != paramArrayOfbyte2.length));
  }
  
  public static boolean isSameLength(char[] paramArrayOfchar1, char[] paramArrayOfchar2) {
    return !((paramArrayOfchar1 == null && paramArrayOfchar2 != null && paramArrayOfchar2.length > 0) || (paramArrayOfchar2 == null && paramArrayOfchar1 != null && paramArrayOfchar1.length > 0) || (paramArrayOfchar1 != null && paramArrayOfchar2 != null && paramArrayOfchar1.length != paramArrayOfchar2.length));
  }
  
  public static boolean isSameLength(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
    return !((paramArrayOfdouble1 == null && paramArrayOfdouble2 != null && paramArrayOfdouble2.length > 0) || (paramArrayOfdouble2 == null && paramArrayOfdouble1 != null && paramArrayOfdouble1.length > 0) || (paramArrayOfdouble1 != null && paramArrayOfdouble2 != null && paramArrayOfdouble1.length != paramArrayOfdouble2.length));
  }
  
  public static boolean isSameLength(float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
    return !((paramArrayOffloat1 == null && paramArrayOffloat2 != null && paramArrayOffloat2.length > 0) || (paramArrayOffloat2 == null && paramArrayOffloat1 != null && paramArrayOffloat1.length > 0) || (paramArrayOffloat1 != null && paramArrayOffloat2 != null && paramArrayOffloat1.length != paramArrayOffloat2.length));
  }
  
  public static boolean isSameLength(int[] paramArrayOfint1, int[] paramArrayOfint2) {
    return !((paramArrayOfint1 == null && paramArrayOfint2 != null && paramArrayOfint2.length > 0) || (paramArrayOfint2 == null && paramArrayOfint1 != null && paramArrayOfint1.length > 0) || (paramArrayOfint1 != null && paramArrayOfint2 != null && paramArrayOfint1.length != paramArrayOfint2.length));
  }
  
  public static boolean isSameLength(long[] paramArrayOflong1, long[] paramArrayOflong2) {
    return !((paramArrayOflong1 == null && paramArrayOflong2 != null && paramArrayOflong2.length > 0) || (paramArrayOflong2 == null && paramArrayOflong1 != null && paramArrayOflong1.length > 0) || (paramArrayOflong1 != null && paramArrayOflong2 != null && paramArrayOflong1.length != paramArrayOflong2.length));
  }
  
  public static boolean isSameLength(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2) {
    return !((paramArrayOfObject1 == null && paramArrayOfObject2 != null && paramArrayOfObject2.length > 0) || (paramArrayOfObject2 == null && paramArrayOfObject1 != null && paramArrayOfObject1.length > 0) || (paramArrayOfObject1 != null && paramArrayOfObject2 != null && paramArrayOfObject1.length != paramArrayOfObject2.length));
  }
  
  public static boolean isSameLength(short[] paramArrayOfshort1, short[] paramArrayOfshort2) {
    return !((paramArrayOfshort1 == null && paramArrayOfshort2 != null && paramArrayOfshort2.length > 0) || (paramArrayOfshort2 == null && paramArrayOfshort1 != null && paramArrayOfshort1.length > 0) || (paramArrayOfshort1 != null && paramArrayOfshort2 != null && paramArrayOfshort1.length != paramArrayOfshort2.length));
  }
  
  public static boolean isSameLength(boolean[] paramArrayOfboolean1, boolean[] paramArrayOfboolean2) {
    return !((paramArrayOfboolean1 == null && paramArrayOfboolean2 != null && paramArrayOfboolean2.length > 0) || (paramArrayOfboolean2 == null && paramArrayOfboolean1 != null && paramArrayOfboolean1.length > 0) || (paramArrayOfboolean1 != null && paramArrayOfboolean2 != null && paramArrayOfboolean1.length != paramArrayOfboolean2.length));
  }
  
  public static boolean isSameType(Object paramObject1, Object paramObject2) {
    if (paramObject1 != null && paramObject2 != null)
      return paramObject1.getClass().getName().equals(paramObject2.getClass().getName()); 
    throw new IllegalArgumentException("The Array must not be null");
  }
  
  public static int lastIndexOf(byte[] paramArrayOfbyte, byte paramByte) {
    return lastIndexOf(paramArrayOfbyte, paramByte, 2147483647);
  }
  
  public static int lastIndexOf(byte[] paramArrayOfbyte, byte paramByte, int paramInt) {
    if (paramArrayOfbyte == null)
      return -1; 
    if (paramInt < 0)
      return -1; 
    int i = paramInt;
    if (paramInt >= paramArrayOfbyte.length)
      i = paramArrayOfbyte.length - 1; 
    while (i >= 0) {
      if (paramByte == paramArrayOfbyte[i])
        return i; 
      i--;
    } 
    return -1;
  }
  
  public static int lastIndexOf(char[] paramArrayOfchar, char paramChar) {
    return lastIndexOf(paramArrayOfchar, paramChar, 2147483647);
  }
  
  public static int lastIndexOf(char[] paramArrayOfchar, char paramChar, int paramInt) {
    if (paramArrayOfchar == null)
      return -1; 
    if (paramInt < 0)
      return -1; 
    int i = paramInt;
    if (paramInt >= paramArrayOfchar.length)
      i = paramArrayOfchar.length - 1; 
    while (i >= 0) {
      if (paramChar == paramArrayOfchar[i])
        return i; 
      i--;
    } 
    return -1;
  }
  
  public static int lastIndexOf(double[] paramArrayOfdouble, double paramDouble) {
    return lastIndexOf(paramArrayOfdouble, paramDouble, 2147483647);
  }
  
  public static int lastIndexOf(double[] paramArrayOfdouble, double paramDouble1, double paramDouble2) {
    return lastIndexOf(paramArrayOfdouble, paramDouble1, 2147483647, paramDouble2);
  }
  
  public static int lastIndexOf(double[] paramArrayOfdouble, double paramDouble, int paramInt) {
    if (isEmpty(paramArrayOfdouble))
      return -1; 
    if (paramInt < 0)
      return -1; 
    int i = paramInt;
    if (paramInt >= paramArrayOfdouble.length)
      i = paramArrayOfdouble.length - 1; 
    while (i >= 0) {
      if (paramDouble == paramArrayOfdouble[i])
        return i; 
      i--;
    } 
    return -1;
  }
  
  public static int lastIndexOf(double[] paramArrayOfdouble, double paramDouble1, int paramInt, double paramDouble2) {
    if (isEmpty(paramArrayOfdouble))
      return -1; 
    if (paramInt < 0)
      return -1; 
    int i = paramInt;
    if (paramInt >= paramArrayOfdouble.length)
      i = paramArrayOfdouble.length - 1; 
    while (i >= 0) {
      if (paramArrayOfdouble[i] >= paramDouble1 - paramDouble2 && paramArrayOfdouble[i] <= paramDouble1 + paramDouble2)
        return i; 
      i--;
    } 
    return -1;
  }
  
  public static int lastIndexOf(float[] paramArrayOffloat, float paramFloat) {
    return lastIndexOf(paramArrayOffloat, paramFloat, 2147483647);
  }
  
  public static int lastIndexOf(float[] paramArrayOffloat, float paramFloat, int paramInt) {
    if (isEmpty(paramArrayOffloat))
      return -1; 
    if (paramInt < 0)
      return -1; 
    int i = paramInt;
    if (paramInt >= paramArrayOffloat.length)
      i = paramArrayOffloat.length - 1; 
    while (i >= 0) {
      if (paramFloat == paramArrayOffloat[i])
        return i; 
      i--;
    } 
    return -1;
  }
  
  public static int lastIndexOf(int[] paramArrayOfint, int paramInt) {
    return lastIndexOf(paramArrayOfint, paramInt, 2147483647);
  }
  
  public static int lastIndexOf(int[] paramArrayOfint, int paramInt1, int paramInt2) {
    if (paramArrayOfint == null)
      return -1; 
    if (paramInt2 < 0)
      return -1; 
    int i = paramInt2;
    if (paramInt2 >= paramArrayOfint.length)
      i = paramArrayOfint.length - 1; 
    while (i >= 0) {
      if (paramInt1 == paramArrayOfint[i])
        return i; 
      i--;
    } 
    return -1;
  }
  
  public static int lastIndexOf(long[] paramArrayOflong, long paramLong) {
    return lastIndexOf(paramArrayOflong, paramLong, 2147483647);
  }
  
  public static int lastIndexOf(long[] paramArrayOflong, long paramLong, int paramInt) {
    if (paramArrayOflong == null)
      return -1; 
    if (paramInt < 0)
      return -1; 
    int i = paramInt;
    if (paramInt >= paramArrayOflong.length)
      i = paramArrayOflong.length - 1; 
    while (i >= 0) {
      if (paramLong == paramArrayOflong[i])
        return i; 
      i--;
    } 
    return -1;
  }
  
  public static int lastIndexOf(Object[] paramArrayOfObject, Object paramObject) {
    return lastIndexOf(paramArrayOfObject, paramObject, 2147483647);
  }
  
  public static int lastIndexOf(Object[] paramArrayOfObject, Object paramObject, int paramInt) {
    if (paramArrayOfObject == null)
      return -1; 
    if (paramInt < 0)
      return -1; 
    int i = paramInt;
    if (paramInt >= paramArrayOfObject.length)
      i = paramArrayOfObject.length - 1; 
    if (paramObject == null) {
      while (i >= 0) {
        if (paramArrayOfObject[i] == null)
          return i; 
        i--;
      } 
    } else if (paramArrayOfObject.getClass().getComponentType().isInstance(paramObject)) {
      while (i >= 0) {
        if (paramObject.equals(paramArrayOfObject[i]))
          return i; 
        i--;
      } 
    } 
    return -1;
  }
  
  public static int lastIndexOf(short[] paramArrayOfshort, short paramShort) {
    return lastIndexOf(paramArrayOfshort, paramShort, 2147483647);
  }
  
  public static int lastIndexOf(short[] paramArrayOfshort, short paramShort, int paramInt) {
    if (paramArrayOfshort == null)
      return -1; 
    if (paramInt < 0)
      return -1; 
    int i = paramInt;
    if (paramInt >= paramArrayOfshort.length)
      i = paramArrayOfshort.length - 1; 
    while (i >= 0) {
      if (paramShort == paramArrayOfshort[i])
        return i; 
      i--;
    } 
    return -1;
  }
  
  public static int lastIndexOf(boolean[] paramArrayOfboolean, boolean paramBoolean) {
    return lastIndexOf(paramArrayOfboolean, paramBoolean, 2147483647);
  }
  
  public static int lastIndexOf(boolean[] paramArrayOfboolean, boolean paramBoolean, int paramInt) {
    if (isEmpty(paramArrayOfboolean))
      return -1; 
    if (paramInt < 0)
      return -1; 
    int i = paramInt;
    if (paramInt >= paramArrayOfboolean.length)
      i = paramArrayOfboolean.length - 1; 
    while (i >= 0) {
      if (paramBoolean == paramArrayOfboolean[i])
        return i; 
      i--;
    } 
    return -1;
  }
  
  public static byte[] nullToEmpty(byte[] paramArrayOfbyte) {
    return (paramArrayOfbyte == null || paramArrayOfbyte.length == 0) ? EMPTY_BYTE_ARRAY : paramArrayOfbyte;
  }
  
  public static char[] nullToEmpty(char[] paramArrayOfchar) {
    return (paramArrayOfchar == null || paramArrayOfchar.length == 0) ? EMPTY_CHAR_ARRAY : paramArrayOfchar;
  }
  
  public static double[] nullToEmpty(double[] paramArrayOfdouble) {
    return (paramArrayOfdouble == null || paramArrayOfdouble.length == 0) ? EMPTY_DOUBLE_ARRAY : paramArrayOfdouble;
  }
  
  public static float[] nullToEmpty(float[] paramArrayOffloat) {
    return (paramArrayOffloat == null || paramArrayOffloat.length == 0) ? EMPTY_FLOAT_ARRAY : paramArrayOffloat;
  }
  
  public static int[] nullToEmpty(int[] paramArrayOfint) {
    return (paramArrayOfint == null || paramArrayOfint.length == 0) ? EMPTY_INT_ARRAY : paramArrayOfint;
  }
  
  public static long[] nullToEmpty(long[] paramArrayOflong) {
    return (paramArrayOflong == null || paramArrayOflong.length == 0) ? EMPTY_LONG_ARRAY : paramArrayOflong;
  }
  
  public static Boolean[] nullToEmpty(Boolean[] paramArrayOfBoolean) {
    return (paramArrayOfBoolean == null || paramArrayOfBoolean.length == 0) ? EMPTY_BOOLEAN_OBJECT_ARRAY : paramArrayOfBoolean;
  }
  
  public static Byte[] nullToEmpty(Byte[] paramArrayOfByte) {
    return (paramArrayOfByte == null || paramArrayOfByte.length == 0) ? EMPTY_BYTE_OBJECT_ARRAY : paramArrayOfByte;
  }
  
  public static Character[] nullToEmpty(Character[] paramArrayOfCharacter) {
    return (paramArrayOfCharacter == null || paramArrayOfCharacter.length == 0) ? EMPTY_CHARACTER_OBJECT_ARRAY : paramArrayOfCharacter;
  }
  
  public static Double[] nullToEmpty(Double[] paramArrayOfDouble) {
    return (paramArrayOfDouble == null || paramArrayOfDouble.length == 0) ? EMPTY_DOUBLE_OBJECT_ARRAY : paramArrayOfDouble;
  }
  
  public static Float[] nullToEmpty(Float[] paramArrayOfFloat) {
    return (paramArrayOfFloat == null || paramArrayOfFloat.length == 0) ? EMPTY_FLOAT_OBJECT_ARRAY : paramArrayOfFloat;
  }
  
  public static Integer[] nullToEmpty(Integer[] paramArrayOfInteger) {
    return (paramArrayOfInteger == null || paramArrayOfInteger.length == 0) ? EMPTY_INTEGER_OBJECT_ARRAY : paramArrayOfInteger;
  }
  
  public static Long[] nullToEmpty(Long[] paramArrayOfLong) {
    return (paramArrayOfLong == null || paramArrayOfLong.length == 0) ? EMPTY_LONG_OBJECT_ARRAY : paramArrayOfLong;
  }
  
  public static Object[] nullToEmpty(Object[] paramArrayOfObject) {
    return (paramArrayOfObject == null || paramArrayOfObject.length == 0) ? EMPTY_OBJECT_ARRAY : paramArrayOfObject;
  }
  
  public static Short[] nullToEmpty(Short[] paramArrayOfShort) {
    return (paramArrayOfShort == null || paramArrayOfShort.length == 0) ? EMPTY_SHORT_OBJECT_ARRAY : paramArrayOfShort;
  }
  
  public static String[] nullToEmpty(String[] paramArrayOfString) {
    return (paramArrayOfString == null || paramArrayOfString.length == 0) ? EMPTY_STRING_ARRAY : paramArrayOfString;
  }
  
  public static short[] nullToEmpty(short[] paramArrayOfshort) {
    return (paramArrayOfshort == null || paramArrayOfshort.length == 0) ? EMPTY_SHORT_ARRAY : paramArrayOfshort;
  }
  
  public static boolean[] nullToEmpty(boolean[] paramArrayOfboolean) {
    return (paramArrayOfboolean == null || paramArrayOfboolean.length == 0) ? EMPTY_BOOLEAN_ARRAY : paramArrayOfboolean;
  }
  
  private static Object remove(Object paramObject, int paramInt) {
    int i = getLength(paramObject);
    if (paramInt >= 0 && paramInt < i) {
      Class<?> clazz = paramObject.getClass().getComponentType();
      int j = i - 1;
      Object object = Array.newInstance(clazz, j);
      System.arraycopy(paramObject, 0, object, 0, paramInt);
      if (paramInt < j)
        System.arraycopy(paramObject, paramInt + 1, object, paramInt, i - paramInt - 1); 
      return object;
    } 
    paramObject = new StringBuilder();
    paramObject.append("Index: ");
    paramObject.append(paramInt);
    paramObject.append(", Length: ");
    paramObject.append(i);
    throw new IndexOutOfBoundsException(paramObject.toString());
  }
  
  public static byte[] remove(byte[] paramArrayOfbyte, int paramInt) {
    return (byte[])remove(paramArrayOfbyte, paramInt);
  }
  
  public static char[] remove(char[] paramArrayOfchar, int paramInt) {
    return (char[])remove(paramArrayOfchar, paramInt);
  }
  
  public static double[] remove(double[] paramArrayOfdouble, int paramInt) {
    return (double[])remove(paramArrayOfdouble, paramInt);
  }
  
  public static float[] remove(float[] paramArrayOffloat, int paramInt) {
    return (float[])remove(paramArrayOffloat, paramInt);
  }
  
  public static int[] remove(int[] paramArrayOfint, int paramInt) {
    return (int[])remove(paramArrayOfint, paramInt);
  }
  
  public static long[] remove(long[] paramArrayOflong, int paramInt) {
    return (long[])remove(paramArrayOflong, paramInt);
  }
  
  public static <T> T[] remove(T[] paramArrayOfT, int paramInt) {
    return (T[])remove(paramArrayOfT, paramInt);
  }
  
  public static short[] remove(short[] paramArrayOfshort, int paramInt) {
    return (short[])remove(paramArrayOfshort, paramInt);
  }
  
  public static boolean[] remove(boolean[] paramArrayOfboolean, int paramInt) {
    return (boolean[])remove(paramArrayOfboolean, paramInt);
  }
  
  private static Object removeAll(Object paramObject, int... paramVarArgs) {
    int j;
    int i = getLength(paramObject);
    if (isNotEmpty(paramVarArgs)) {
      Arrays.sort(paramVarArgs);
      j = paramVarArgs.length;
      int m = i;
      byte b = 0;
      while (true) {
        int n = j - 1;
        j = b;
        if (n >= 0) {
          j = paramVarArgs[n];
          if (j >= 0 && j < i) {
            if (j >= m) {
              j = n;
              continue;
            } 
            b++;
            m = j;
            j = n;
            continue;
          } 
          paramObject = new StringBuilder();
          paramObject.append("Index: ");
          paramObject.append(j);
          paramObject.append(", Length: ");
          paramObject.append(i);
          throw new IndexOutOfBoundsException(paramObject.toString());
        } 
        break;
      } 
    } else {
      j = 0;
    } 
    Class<?> clazz = paramObject.getClass().getComponentType();
    int k = i - j;
    Object object = Array.newInstance(clazz, k);
    if (j < i) {
      int m = paramVarArgs.length - 1;
      j = i;
      while (m >= 0) {
        int n = paramVarArgs[m];
        j -= n;
        i = k;
        if (j > 1) {
          i = k - --j;
          System.arraycopy(paramObject, n + 1, object, i, j);
        } 
        m--;
        j = n;
        k = i;
      } 
      if (j > 0)
        System.arraycopy(paramObject, 0, object, 0, j); 
    } 
    return object;
  }
  
  public static byte[] removeAll(byte[] paramArrayOfbyte, int... paramVarArgs) {
    return (byte[])removeAll(paramArrayOfbyte, clone(paramVarArgs));
  }
  
  public static char[] removeAll(char[] paramArrayOfchar, int... paramVarArgs) {
    return (char[])removeAll(paramArrayOfchar, clone(paramVarArgs));
  }
  
  public static double[] removeAll(double[] paramArrayOfdouble, int... paramVarArgs) {
    return (double[])removeAll(paramArrayOfdouble, clone(paramVarArgs));
  }
  
  public static float[] removeAll(float[] paramArrayOffloat, int... paramVarArgs) {
    return (float[])removeAll(paramArrayOffloat, clone(paramVarArgs));
  }
  
  public static int[] removeAll(int[] paramArrayOfint1, int... paramVarArgs1) {
    return (int[])removeAll(paramArrayOfint1, clone(paramVarArgs1));
  }
  
  public static long[] removeAll(long[] paramArrayOflong, int... paramVarArgs) {
    return (long[])removeAll(paramArrayOflong, clone(paramVarArgs));
  }
  
  public static <T> T[] removeAll(T[] paramArrayOfT, int... paramVarArgs) {
    return (T[])removeAll(paramArrayOfT, clone(paramVarArgs));
  }
  
  public static short[] removeAll(short[] paramArrayOfshort, int... paramVarArgs) {
    return (short[])removeAll(paramArrayOfshort, clone(paramVarArgs));
  }
  
  public static boolean[] removeAll(boolean[] paramArrayOfboolean, int... paramVarArgs) {
    return (boolean[])removeAll(paramArrayOfboolean, clone(paramVarArgs));
  }
  
  public static byte[] removeElement(byte[] paramArrayOfbyte, byte paramByte) {
    int i = indexOf(paramArrayOfbyte, paramByte);
    return (i == -1) ? clone(paramArrayOfbyte) : remove(paramArrayOfbyte, i);
  }
  
  public static char[] removeElement(char[] paramArrayOfchar, char paramChar) {
    int i = indexOf(paramArrayOfchar, paramChar);
    return (i == -1) ? clone(paramArrayOfchar) : remove(paramArrayOfchar, i);
  }
  
  public static double[] removeElement(double[] paramArrayOfdouble, double paramDouble) {
    int i = indexOf(paramArrayOfdouble, paramDouble);
    return (i == -1) ? clone(paramArrayOfdouble) : remove(paramArrayOfdouble, i);
  }
  
  public static float[] removeElement(float[] paramArrayOffloat, float paramFloat) {
    int i = indexOf(paramArrayOffloat, paramFloat);
    return (i == -1) ? clone(paramArrayOffloat) : remove(paramArrayOffloat, i);
  }
  
  public static int[] removeElement(int[] paramArrayOfint, int paramInt) {
    paramInt = indexOf(paramArrayOfint, paramInt);
    return (paramInt == -1) ? clone(paramArrayOfint) : remove(paramArrayOfint, paramInt);
  }
  
  public static long[] removeElement(long[] paramArrayOflong, long paramLong) {
    int i = indexOf(paramArrayOflong, paramLong);
    return (i == -1) ? clone(paramArrayOflong) : remove(paramArrayOflong, i);
  }
  
  public static <T> T[] removeElement(T[] paramArrayOfT, Object paramObject) {
    int i = indexOf((Object[])paramArrayOfT, paramObject);
    return (i == -1) ? clone(paramArrayOfT) : remove(paramArrayOfT, i);
  }
  
  public static short[] removeElement(short[] paramArrayOfshort, short paramShort) {
    int i = indexOf(paramArrayOfshort, paramShort);
    return (i == -1) ? clone(paramArrayOfshort) : remove(paramArrayOfshort, i);
  }
  
  public static boolean[] removeElement(boolean[] paramArrayOfboolean, boolean paramBoolean) {
    int i = indexOf(paramArrayOfboolean, paramBoolean);
    return (i == -1) ? clone(paramArrayOfboolean) : remove(paramArrayOfboolean, i);
  }
  
  public static byte[] removeElements(byte[] paramArrayOfbyte1, byte... paramVarArgs1) {
    if (isEmpty(paramArrayOfbyte1) || isEmpty(paramVarArgs1))
      return clone(paramArrayOfbyte1); 
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>(paramVarArgs1.length);
    int i = paramVarArgs1.length;
    byte b;
    for (b = 0; b < i; b++) {
      Byte byte_ = Byte.valueOf(paramVarArgs1[b]);
      MutableInt mutableInt = (MutableInt)hashMap.get(byte_);
      if (mutableInt == null) {
        hashMap.put(byte_, new MutableInt(1));
      } else {
        mutableInt.increment();
      } 
    } 
    HashSet<Integer> hashSet = new HashSet();
    for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
      Byte byte_ = (Byte)entry.getKey();
      int j = ((MutableInt)entry.getValue()).intValue();
      b = 0;
      for (i = 0; b < j; i++) {
        i = indexOf(paramArrayOfbyte1, byte_.byteValue(), i);
        if (i < 0)
          break; 
        hashSet.add(Integer.valueOf(i));
        b++;
      } 
    } 
    return removeAll(paramArrayOfbyte1, extractIndices(hashSet));
  }
  
  public static char[] removeElements(char[] paramArrayOfchar1, char... paramVarArgs1) {
    if (isEmpty(paramArrayOfchar1) || isEmpty(paramVarArgs1))
      return clone(paramArrayOfchar1); 
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>(paramVarArgs1.length);
    int i = paramVarArgs1.length;
    byte b;
    for (b = 0; b < i; b++) {
      Character character = Character.valueOf(paramVarArgs1[b]);
      MutableInt mutableInt = (MutableInt)hashMap.get(character);
      if (mutableInt == null) {
        hashMap.put(character, new MutableInt(1));
      } else {
        mutableInt.increment();
      } 
    } 
    HashSet<Integer> hashSet = new HashSet();
    for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
      Character character = (Character)entry.getKey();
      int j = ((MutableInt)entry.getValue()).intValue();
      b = 0;
      for (i = 0; b < j; i++) {
        i = indexOf(paramArrayOfchar1, character.charValue(), i);
        if (i < 0)
          break; 
        hashSet.add(Integer.valueOf(i));
        b++;
      } 
    } 
    return removeAll(paramArrayOfchar1, extractIndices(hashSet));
  }
  
  public static double[] removeElements(double[] paramArrayOfdouble1, double... paramVarArgs1) {
    if (isEmpty(paramArrayOfdouble1) || isEmpty(paramVarArgs1))
      return clone(paramArrayOfdouble1); 
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>(paramVarArgs1.length);
    int i = paramVarArgs1.length;
    byte b;
    for (b = 0; b < i; b++) {
      Double double_ = Double.valueOf(paramVarArgs1[b]);
      MutableInt mutableInt = (MutableInt)hashMap.get(double_);
      if (mutableInt == null) {
        hashMap.put(double_, new MutableInt(1));
      } else {
        mutableInt.increment();
      } 
    } 
    HashSet<Integer> hashSet = new HashSet();
    for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
      Double double_ = (Double)entry.getKey();
      int j = ((MutableInt)entry.getValue()).intValue();
      b = 0;
      for (i = 0; b < j; i++) {
        i = indexOf(paramArrayOfdouble1, double_.doubleValue(), i);
        if (i < 0)
          break; 
        hashSet.add(Integer.valueOf(i));
        b++;
      } 
    } 
    return removeAll(paramArrayOfdouble1, extractIndices(hashSet));
  }
  
  public static float[] removeElements(float[] paramArrayOffloat1, float... paramVarArgs1) {
    if (isEmpty(paramArrayOffloat1) || isEmpty(paramVarArgs1))
      return clone(paramArrayOffloat1); 
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>(paramVarArgs1.length);
    int i = paramVarArgs1.length;
    byte b;
    for (b = 0; b < i; b++) {
      Float float_ = Float.valueOf(paramVarArgs1[b]);
      MutableInt mutableInt = (MutableInt)hashMap.get(float_);
      if (mutableInt == null) {
        hashMap.put(float_, new MutableInt(1));
      } else {
        mutableInt.increment();
      } 
    } 
    HashSet<Integer> hashSet = new HashSet();
    for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
      Float float_ = (Float)entry.getKey();
      int j = ((MutableInt)entry.getValue()).intValue();
      b = 0;
      for (i = 0; b < j; i++) {
        i = indexOf(paramArrayOffloat1, float_.floatValue(), i);
        if (i < 0)
          break; 
        hashSet.add(Integer.valueOf(i));
        b++;
      } 
    } 
    return removeAll(paramArrayOffloat1, extractIndices(hashSet));
  }
  
  public static int[] removeElements(int[] paramArrayOfint1, int... paramVarArgs1) {
    if (isEmpty(paramArrayOfint1) || isEmpty(paramVarArgs1))
      return clone(paramArrayOfint1); 
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>(paramVarArgs1.length);
    int i = paramVarArgs1.length;
    byte b;
    for (b = 0; b < i; b++) {
      Integer integer = Integer.valueOf(paramVarArgs1[b]);
      MutableInt mutableInt = (MutableInt)hashMap.get(integer);
      if (mutableInt == null) {
        hashMap.put(integer, new MutableInt(1));
      } else {
        mutableInt.increment();
      } 
    } 
    HashSet<Integer> hashSet = new HashSet();
    for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
      Integer integer = (Integer)entry.getKey();
      int j = ((MutableInt)entry.getValue()).intValue();
      b = 0;
      for (i = 0; b < j; i++) {
        i = indexOf(paramArrayOfint1, integer.intValue(), i);
        if (i < 0)
          break; 
        hashSet.add(Integer.valueOf(i));
        b++;
      } 
    } 
    return removeAll(paramArrayOfint1, extractIndices(hashSet));
  }
  
  public static long[] removeElements(long[] paramArrayOflong1, long... paramVarArgs1) {
    if (isEmpty(paramArrayOflong1) || isEmpty(paramVarArgs1))
      return clone(paramArrayOflong1); 
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>(paramVarArgs1.length);
    int i = paramVarArgs1.length;
    byte b;
    for (b = 0; b < i; b++) {
      Long long_ = Long.valueOf(paramVarArgs1[b]);
      MutableInt mutableInt = (MutableInt)hashMap.get(long_);
      if (mutableInt == null) {
        hashMap.put(long_, new MutableInt(1));
      } else {
        mutableInt.increment();
      } 
    } 
    HashSet<Integer> hashSet = new HashSet();
    for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
      Long long_ = (Long)entry.getKey();
      int j = ((MutableInt)entry.getValue()).intValue();
      b = 0;
      for (i = 0; b < j; i++) {
        i = indexOf(paramArrayOflong1, long_.longValue(), i);
        if (i < 0)
          break; 
        hashSet.add(Integer.valueOf(i));
        b++;
      } 
    } 
    return removeAll(paramArrayOflong1, extractIndices(hashSet));
  }
  
  public static <T> T[] removeElements(T[] paramArrayOfT1, T... paramVarArgs1) {
    if (isEmpty((Object[])paramArrayOfT1) || isEmpty((Object[])paramVarArgs1))
      return clone(paramArrayOfT1); 
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>(paramVarArgs1.length);
    int i = paramVarArgs1.length;
    byte b;
    for (b = 0; b < i; b++) {
      T t = paramVarArgs1[b];
      MutableInt mutableInt = (MutableInt)hashMap.get(t);
      if (mutableInt == null) {
        hashMap.put(t, new MutableInt(1));
      } else {
        mutableInt.increment();
      } 
    } 
    HashSet<Integer> hashSet = new HashSet();
    for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
      hashMap = (HashMap<Object, Object>)entry.getKey();
      int j = ((MutableInt)entry.getValue()).intValue();
      b = 0;
      for (i = 0; b < j; i++) {
        i = indexOf((Object[])paramArrayOfT1, hashMap, i);
        if (i < 0)
          break; 
        hashSet.add(Integer.valueOf(i));
        b++;
      } 
    } 
    return removeAll(paramArrayOfT1, extractIndices(hashSet));
  }
  
  public static short[] removeElements(short[] paramArrayOfshort1, short... paramVarArgs1) {
    if (isEmpty(paramArrayOfshort1) || isEmpty(paramVarArgs1))
      return clone(paramArrayOfshort1); 
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>(paramVarArgs1.length);
    int i = paramVarArgs1.length;
    byte b;
    for (b = 0; b < i; b++) {
      Short short_ = Short.valueOf(paramVarArgs1[b]);
      MutableInt mutableInt = (MutableInt)hashMap.get(short_);
      if (mutableInt == null) {
        hashMap.put(short_, new MutableInt(1));
      } else {
        mutableInt.increment();
      } 
    } 
    HashSet<Integer> hashSet = new HashSet();
    for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
      Short short_ = (Short)entry.getKey();
      int j = ((MutableInt)entry.getValue()).intValue();
      b = 0;
      for (i = 0; b < j; i++) {
        i = indexOf(paramArrayOfshort1, short_.shortValue(), i);
        if (i < 0)
          break; 
        hashSet.add(Integer.valueOf(i));
        b++;
      } 
    } 
    return removeAll(paramArrayOfshort1, extractIndices(hashSet));
  }
  
  public static boolean[] removeElements(boolean[] paramArrayOfboolean1, boolean... paramVarArgs1) {
    if (isEmpty(paramArrayOfboolean1) || isEmpty(paramVarArgs1))
      return clone(paramArrayOfboolean1); 
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>(paramVarArgs1.length);
    int i = paramVarArgs1.length;
    byte b;
    for (b = 0; b < i; b++) {
      Boolean bool = Boolean.valueOf(paramVarArgs1[b]);
      MutableInt mutableInt = (MutableInt)hashMap.get(bool);
      if (mutableInt == null) {
        hashMap.put(bool, new MutableInt(1));
      } else {
        mutableInt.increment();
      } 
    } 
    HashSet<Integer> hashSet = new HashSet();
    for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
      Boolean bool = (Boolean)entry.getKey();
      int j = ((MutableInt)entry.getValue()).intValue();
      b = 0;
      for (i = 0; b < j; i++) {
        i = indexOf(paramArrayOfboolean1, bool.booleanValue(), i);
        if (i < 0)
          break; 
        hashSet.add(Integer.valueOf(i));
        b++;
      } 
    } 
    return removeAll(paramArrayOfboolean1, extractIndices(hashSet));
  }
  
  public static void reverse(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte == null)
      return; 
    byte b = 0;
    int i = paramArrayOfbyte.length - 1;
    while (i > b) {
      byte b1 = paramArrayOfbyte[i];
      paramArrayOfbyte[i] = (byte)paramArrayOfbyte[b];
      paramArrayOfbyte[b] = (byte)b1;
      i--;
      b++;
    } 
  }
  
  public static void reverse(char[] paramArrayOfchar) {
    if (paramArrayOfchar == null)
      return; 
    byte b = 0;
    int i = paramArrayOfchar.length - 1;
    while (i > b) {
      char c = paramArrayOfchar[i];
      paramArrayOfchar[i] = (char)paramArrayOfchar[b];
      paramArrayOfchar[b] = (char)c;
      i--;
      b++;
    } 
  }
  
  public static void reverse(double[] paramArrayOfdouble) {
    if (paramArrayOfdouble == null)
      return; 
    byte b = 0;
    int i = paramArrayOfdouble.length - 1;
    while (i > b) {
      double d = paramArrayOfdouble[i];
      paramArrayOfdouble[i] = paramArrayOfdouble[b];
      paramArrayOfdouble[b] = d;
      i--;
      b++;
    } 
  }
  
  public static void reverse(float[] paramArrayOffloat) {
    if (paramArrayOffloat == null)
      return; 
    byte b = 0;
    int i = paramArrayOffloat.length - 1;
    while (i > b) {
      float f = paramArrayOffloat[i];
      paramArrayOffloat[i] = paramArrayOffloat[b];
      paramArrayOffloat[b] = f;
      i--;
      b++;
    } 
  }
  
  public static void reverse(int[] paramArrayOfint) {
    if (paramArrayOfint == null)
      return; 
    byte b = 0;
    int i = paramArrayOfint.length - 1;
    while (i > b) {
      int j = paramArrayOfint[i];
      paramArrayOfint[i] = paramArrayOfint[b];
      paramArrayOfint[b] = j;
      i--;
      b++;
    } 
  }
  
  public static void reverse(long[] paramArrayOflong) {
    if (paramArrayOflong == null)
      return; 
    byte b = 0;
    int i = paramArrayOflong.length - 1;
    while (i > b) {
      long l = paramArrayOflong[i];
      paramArrayOflong[i] = paramArrayOflong[b];
      paramArrayOflong[b] = l;
      i--;
      b++;
    } 
  }
  
  public static void reverse(Object[] paramArrayOfObject) {
    if (paramArrayOfObject == null)
      return; 
    byte b = 0;
    int i = paramArrayOfObject.length - 1;
    while (i > b) {
      Object object = paramArrayOfObject[i];
      paramArrayOfObject[i] = paramArrayOfObject[b];
      paramArrayOfObject[b] = object;
      i--;
      b++;
    } 
  }
  
  public static void reverse(short[] paramArrayOfshort) {
    if (paramArrayOfshort == null)
      return; 
    byte b = 0;
    int i = paramArrayOfshort.length - 1;
    while (i > b) {
      short s = paramArrayOfshort[i];
      paramArrayOfshort[i] = (short)paramArrayOfshort[b];
      paramArrayOfshort[b] = (short)s;
      i--;
      b++;
    } 
  }
  
  public static void reverse(boolean[] paramArrayOfboolean) {
    if (paramArrayOfboolean == null)
      return; 
    byte b = 0;
    int i = paramArrayOfboolean.length - 1;
    while (i > b) {
      boolean bool = paramArrayOfboolean[i];
      paramArrayOfboolean[i] = paramArrayOfboolean[b];
      paramArrayOfboolean[b] = bool;
      i--;
      b++;
    } 
  }
  
  public static byte[] subarray(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (paramArrayOfbyte == null)
      return null; 
    int i = paramInt1;
    if (paramInt1 < 0)
      i = 0; 
    paramInt1 = paramInt2;
    if (paramInt2 > paramArrayOfbyte.length)
      paramInt1 = paramArrayOfbyte.length; 
    paramInt1 -= i;
    if (paramInt1 <= 0)
      return EMPTY_BYTE_ARRAY; 
    byte[] arrayOfByte = new byte[paramInt1];
    System.arraycopy(paramArrayOfbyte, i, arrayOfByte, 0, paramInt1);
    return arrayOfByte;
  }
  
  public static char[] subarray(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
    if (paramArrayOfchar == null)
      return null; 
    int i = paramInt1;
    if (paramInt1 < 0)
      i = 0; 
    paramInt1 = paramInt2;
    if (paramInt2 > paramArrayOfchar.length)
      paramInt1 = paramArrayOfchar.length; 
    paramInt1 -= i;
    if (paramInt1 <= 0)
      return EMPTY_CHAR_ARRAY; 
    char[] arrayOfChar = new char[paramInt1];
    System.arraycopy(paramArrayOfchar, i, arrayOfChar, 0, paramInt1);
    return arrayOfChar;
  }
  
  public static double[] subarray(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
    if (paramArrayOfdouble == null)
      return null; 
    int i = paramInt1;
    if (paramInt1 < 0)
      i = 0; 
    paramInt1 = paramInt2;
    if (paramInt2 > paramArrayOfdouble.length)
      paramInt1 = paramArrayOfdouble.length; 
    paramInt1 -= i;
    if (paramInt1 <= 0)
      return EMPTY_DOUBLE_ARRAY; 
    double[] arrayOfDouble = new double[paramInt1];
    System.arraycopy(paramArrayOfdouble, i, arrayOfDouble, 0, paramInt1);
    return arrayOfDouble;
  }
  
  public static float[] subarray(float[] paramArrayOffloat, int paramInt1, int paramInt2) {
    if (paramArrayOffloat == null)
      return null; 
    int i = paramInt1;
    if (paramInt1 < 0)
      i = 0; 
    paramInt1 = paramInt2;
    if (paramInt2 > paramArrayOffloat.length)
      paramInt1 = paramArrayOffloat.length; 
    paramInt1 -= i;
    if (paramInt1 <= 0)
      return EMPTY_FLOAT_ARRAY; 
    float[] arrayOfFloat = new float[paramInt1];
    System.arraycopy(paramArrayOffloat, i, arrayOfFloat, 0, paramInt1);
    return arrayOfFloat;
  }
  
  public static int[] subarray(int[] paramArrayOfint, int paramInt1, int paramInt2) {
    if (paramArrayOfint == null)
      return null; 
    int i = paramInt1;
    if (paramInt1 < 0)
      i = 0; 
    paramInt1 = paramInt2;
    if (paramInt2 > paramArrayOfint.length)
      paramInt1 = paramArrayOfint.length; 
    paramInt1 -= i;
    if (paramInt1 <= 0)
      return EMPTY_INT_ARRAY; 
    int[] arrayOfInt = new int[paramInt1];
    System.arraycopy(paramArrayOfint, i, arrayOfInt, 0, paramInt1);
    return arrayOfInt;
  }
  
  public static long[] subarray(long[] paramArrayOflong, int paramInt1, int paramInt2) {
    if (paramArrayOflong == null)
      return null; 
    int i = paramInt1;
    if (paramInt1 < 0)
      i = 0; 
    paramInt1 = paramInt2;
    if (paramInt2 > paramArrayOflong.length)
      paramInt1 = paramArrayOflong.length; 
    paramInt1 -= i;
    if (paramInt1 <= 0)
      return EMPTY_LONG_ARRAY; 
    long[] arrayOfLong = new long[paramInt1];
    System.arraycopy(paramArrayOflong, i, arrayOfLong, 0, paramInt1);
    return arrayOfLong;
  }
  
  public static <T> T[] subarray(T[] paramArrayOfT, int paramInt1, int paramInt2) {
    if (paramArrayOfT == null)
      return null; 
    int i = paramInt1;
    if (paramInt1 < 0)
      i = 0; 
    paramInt1 = paramInt2;
    if (paramInt2 > paramArrayOfT.length)
      paramInt1 = paramArrayOfT.length; 
    paramInt1 -= i;
    Class<?> clazz = paramArrayOfT.getClass().getComponentType();
    if (paramInt1 <= 0)
      return (T[])Array.newInstance(clazz, 0); 
    Object[] arrayOfObject = (Object[])Array.newInstance(clazz, paramInt1);
    System.arraycopy(paramArrayOfT, i, arrayOfObject, 0, paramInt1);
    return (T[])arrayOfObject;
  }
  
  public static short[] subarray(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
    if (paramArrayOfshort == null)
      return null; 
    int i = paramInt1;
    if (paramInt1 < 0)
      i = 0; 
    paramInt1 = paramInt2;
    if (paramInt2 > paramArrayOfshort.length)
      paramInt1 = paramArrayOfshort.length; 
    paramInt1 -= i;
    if (paramInt1 <= 0)
      return EMPTY_SHORT_ARRAY; 
    short[] arrayOfShort = new short[paramInt1];
    System.arraycopy(paramArrayOfshort, i, arrayOfShort, 0, paramInt1);
    return arrayOfShort;
  }
  
  public static boolean[] subarray(boolean[] paramArrayOfboolean, int paramInt1, int paramInt2) {
    if (paramArrayOfboolean == null)
      return null; 
    int i = paramInt1;
    if (paramInt1 < 0)
      i = 0; 
    paramInt1 = paramInt2;
    if (paramInt2 > paramArrayOfboolean.length)
      paramInt1 = paramArrayOfboolean.length; 
    paramInt1 -= i;
    if (paramInt1 <= 0)
      return EMPTY_BOOLEAN_ARRAY; 
    boolean[] arrayOfBoolean = new boolean[paramInt1];
    System.arraycopy(paramArrayOfboolean, i, arrayOfBoolean, 0, paramInt1);
    return arrayOfBoolean;
  }
  
  public static <T> T[] toArray(T... paramVarArgs) {
    return paramVarArgs;
  }
  
  public static Map<Object, Object> toMap(Object[] paramArrayOfObject) {
    if (paramArrayOfObject == null)
      return null; 
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>((int)(paramArrayOfObject.length * 1.5D));
    for (byte b = 0; b < paramArrayOfObject.length; b++) {
      Object object = paramArrayOfObject[b];
      if (object instanceof Map.Entry) {
        object = object;
        hashMap.put(object.getKey(), object.getValue());
      } else if (object instanceof Object[]) {
        Object[] arrayOfObject = (Object[])object;
        if (arrayOfObject.length >= 2) {
          hashMap.put(arrayOfObject[0], arrayOfObject[1]);
        } else {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Array element ");
          stringBuilder.append(b);
          stringBuilder.append(", '");
          stringBuilder.append(object);
          stringBuilder.append("', has a length less than 2");
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Array element ");
        stringBuilder.append(b);
        stringBuilder.append(", '");
        stringBuilder.append(object);
        stringBuilder.append("', is neither of type Map.Entry nor an Array");
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
    } 
    return hashMap;
  }
  
  public static Boolean[] toObject(boolean[] paramArrayOfboolean) {
    if (paramArrayOfboolean == null)
      return null; 
    if (paramArrayOfboolean.length == 0)
      return EMPTY_BOOLEAN_OBJECT_ARRAY; 
    Boolean[] arrayOfBoolean = new Boolean[paramArrayOfboolean.length];
    for (byte b = 0; b < paramArrayOfboolean.length; b++) {
      Boolean bool;
      if (paramArrayOfboolean[b]) {
        bool = Boolean.TRUE;
      } else {
        bool = Boolean.FALSE;
      } 
      arrayOfBoolean[b] = bool;
    } 
    return arrayOfBoolean;
  }
  
  public static Byte[] toObject(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte == null)
      return null; 
    if (paramArrayOfbyte.length == 0)
      return EMPTY_BYTE_OBJECT_ARRAY; 
    Byte[] arrayOfByte = new Byte[paramArrayOfbyte.length];
    for (byte b = 0; b < paramArrayOfbyte.length; b++)
      arrayOfByte[b] = Byte.valueOf(paramArrayOfbyte[b]); 
    return arrayOfByte;
  }
  
  public static Character[] toObject(char[] paramArrayOfchar) {
    if (paramArrayOfchar == null)
      return null; 
    if (paramArrayOfchar.length == 0)
      return EMPTY_CHARACTER_OBJECT_ARRAY; 
    Character[] arrayOfCharacter = new Character[paramArrayOfchar.length];
    for (byte b = 0; b < paramArrayOfchar.length; b++)
      arrayOfCharacter[b] = Character.valueOf(paramArrayOfchar[b]); 
    return arrayOfCharacter;
  }
  
  public static Double[] toObject(double[] paramArrayOfdouble) {
    if (paramArrayOfdouble == null)
      return null; 
    if (paramArrayOfdouble.length == 0)
      return EMPTY_DOUBLE_OBJECT_ARRAY; 
    Double[] arrayOfDouble = new Double[paramArrayOfdouble.length];
    for (byte b = 0; b < paramArrayOfdouble.length; b++)
      arrayOfDouble[b] = Double.valueOf(paramArrayOfdouble[b]); 
    return arrayOfDouble;
  }
  
  public static Float[] toObject(float[] paramArrayOffloat) {
    if (paramArrayOffloat == null)
      return null; 
    if (paramArrayOffloat.length == 0)
      return EMPTY_FLOAT_OBJECT_ARRAY; 
    Float[] arrayOfFloat = new Float[paramArrayOffloat.length];
    for (byte b = 0; b < paramArrayOffloat.length; b++)
      arrayOfFloat[b] = Float.valueOf(paramArrayOffloat[b]); 
    return arrayOfFloat;
  }
  
  public static Integer[] toObject(int[] paramArrayOfint) {
    if (paramArrayOfint == null)
      return null; 
    if (paramArrayOfint.length == 0)
      return EMPTY_INTEGER_OBJECT_ARRAY; 
    Integer[] arrayOfInteger = new Integer[paramArrayOfint.length];
    for (byte b = 0; b < paramArrayOfint.length; b++)
      arrayOfInteger[b] = Integer.valueOf(paramArrayOfint[b]); 
    return arrayOfInteger;
  }
  
  public static Long[] toObject(long[] paramArrayOflong) {
    if (paramArrayOflong == null)
      return null; 
    if (paramArrayOflong.length == 0)
      return EMPTY_LONG_OBJECT_ARRAY; 
    Long[] arrayOfLong = new Long[paramArrayOflong.length];
    for (byte b = 0; b < paramArrayOflong.length; b++)
      arrayOfLong[b] = Long.valueOf(paramArrayOflong[b]); 
    return arrayOfLong;
  }
  
  public static Short[] toObject(short[] paramArrayOfshort) {
    if (paramArrayOfshort == null)
      return null; 
    if (paramArrayOfshort.length == 0)
      return EMPTY_SHORT_OBJECT_ARRAY; 
    Short[] arrayOfShort = new Short[paramArrayOfshort.length];
    for (byte b = 0; b < paramArrayOfshort.length; b++)
      arrayOfShort[b] = Short.valueOf(paramArrayOfshort[b]); 
    return arrayOfShort;
  }
  
  public static byte[] toPrimitive(Byte[] paramArrayOfByte) {
    if (paramArrayOfByte == null)
      return null; 
    if (paramArrayOfByte.length == 0)
      return EMPTY_BYTE_ARRAY; 
    byte[] arrayOfByte = new byte[paramArrayOfByte.length];
    for (byte b = 0; b < paramArrayOfByte.length; b++)
      arrayOfByte[b] = paramArrayOfByte[b].byteValue(); 
    return arrayOfByte;
  }
  
  public static byte[] toPrimitive(Byte[] paramArrayOfByte, byte paramByte) {
    if (paramArrayOfByte == null)
      return null; 
    if (paramArrayOfByte.length == 0)
      return EMPTY_BYTE_ARRAY; 
    byte[] arrayOfByte = new byte[paramArrayOfByte.length];
    for (byte b = 0; b < paramArrayOfByte.length; b++) {
      byte b1;
      Byte byte_ = paramArrayOfByte[b];
      if (byte_ == null) {
        b1 = paramByte;
      } else {
        b1 = byte_.byteValue();
      } 
      arrayOfByte[b] = (byte)b1;
    } 
    return arrayOfByte;
  }
  
  public static char[] toPrimitive(Character[] paramArrayOfCharacter) {
    if (paramArrayOfCharacter == null)
      return null; 
    if (paramArrayOfCharacter.length == 0)
      return EMPTY_CHAR_ARRAY; 
    char[] arrayOfChar = new char[paramArrayOfCharacter.length];
    for (byte b = 0; b < paramArrayOfCharacter.length; b++)
      arrayOfChar[b] = paramArrayOfCharacter[b].charValue(); 
    return arrayOfChar;
  }
  
  public static char[] toPrimitive(Character[] paramArrayOfCharacter, char paramChar) {
    if (paramArrayOfCharacter == null)
      return null; 
    if (paramArrayOfCharacter.length == 0)
      return EMPTY_CHAR_ARRAY; 
    char[] arrayOfChar = new char[paramArrayOfCharacter.length];
    for (byte b = 0; b < paramArrayOfCharacter.length; b++) {
      char c;
      Character character = paramArrayOfCharacter[b];
      if (character == null) {
        c = paramChar;
      } else {
        c = character.charValue();
      } 
      arrayOfChar[b] = (char)c;
    } 
    return arrayOfChar;
  }
  
  public static double[] toPrimitive(Double[] paramArrayOfDouble) {
    if (paramArrayOfDouble == null)
      return null; 
    if (paramArrayOfDouble.length == 0)
      return EMPTY_DOUBLE_ARRAY; 
    double[] arrayOfDouble = new double[paramArrayOfDouble.length];
    for (byte b = 0; b < paramArrayOfDouble.length; b++)
      arrayOfDouble[b] = paramArrayOfDouble[b].doubleValue(); 
    return arrayOfDouble;
  }
  
  public static double[] toPrimitive(Double[] paramArrayOfDouble, double paramDouble) {
    if (paramArrayOfDouble == null)
      return null; 
    if (paramArrayOfDouble.length == 0)
      return EMPTY_DOUBLE_ARRAY; 
    double[] arrayOfDouble = new double[paramArrayOfDouble.length];
    for (byte b = 0; b < paramArrayOfDouble.length; b++) {
      double d;
      Double double_ = paramArrayOfDouble[b];
      if (double_ == null) {
        d = paramDouble;
      } else {
        d = double_.doubleValue();
      } 
      arrayOfDouble[b] = d;
    } 
    return arrayOfDouble;
  }
  
  public static float[] toPrimitive(Float[] paramArrayOfFloat) {
    if (paramArrayOfFloat == null)
      return null; 
    if (paramArrayOfFloat.length == 0)
      return EMPTY_FLOAT_ARRAY; 
    float[] arrayOfFloat = new float[paramArrayOfFloat.length];
    for (byte b = 0; b < paramArrayOfFloat.length; b++)
      arrayOfFloat[b] = paramArrayOfFloat[b].floatValue(); 
    return arrayOfFloat;
  }
  
  public static float[] toPrimitive(Float[] paramArrayOfFloat, float paramFloat) {
    if (paramArrayOfFloat == null)
      return null; 
    if (paramArrayOfFloat.length == 0)
      return EMPTY_FLOAT_ARRAY; 
    float[] arrayOfFloat = new float[paramArrayOfFloat.length];
    for (byte b = 0; b < paramArrayOfFloat.length; b++) {
      float f;
      Float float_ = paramArrayOfFloat[b];
      if (float_ == null) {
        f = paramFloat;
      } else {
        f = float_.floatValue();
      } 
      arrayOfFloat[b] = f;
    } 
    return arrayOfFloat;
  }
  
  public static int[] toPrimitive(Integer[] paramArrayOfInteger) {
    if (paramArrayOfInteger == null)
      return null; 
    if (paramArrayOfInteger.length == 0)
      return EMPTY_INT_ARRAY; 
    int[] arrayOfInt = new int[paramArrayOfInteger.length];
    for (byte b = 0; b < paramArrayOfInteger.length; b++)
      arrayOfInt[b] = paramArrayOfInteger[b].intValue(); 
    return arrayOfInt;
  }
  
  public static int[] toPrimitive(Integer[] paramArrayOfInteger, int paramInt) {
    if (paramArrayOfInteger == null)
      return null; 
    if (paramArrayOfInteger.length == 0)
      return EMPTY_INT_ARRAY; 
    int[] arrayOfInt = new int[paramArrayOfInteger.length];
    for (byte b = 0; b < paramArrayOfInteger.length; b++) {
      int i;
      Integer integer = paramArrayOfInteger[b];
      if (integer == null) {
        i = paramInt;
      } else {
        i = integer.intValue();
      } 
      arrayOfInt[b] = i;
    } 
    return arrayOfInt;
  }
  
  public static long[] toPrimitive(Long[] paramArrayOfLong) {
    if (paramArrayOfLong == null)
      return null; 
    if (paramArrayOfLong.length == 0)
      return EMPTY_LONG_ARRAY; 
    long[] arrayOfLong = new long[paramArrayOfLong.length];
    for (byte b = 0; b < paramArrayOfLong.length; b++)
      arrayOfLong[b] = paramArrayOfLong[b].longValue(); 
    return arrayOfLong;
  }
  
  public static long[] toPrimitive(Long[] paramArrayOfLong, long paramLong) {
    if (paramArrayOfLong == null)
      return null; 
    if (paramArrayOfLong.length == 0)
      return EMPTY_LONG_ARRAY; 
    long[] arrayOfLong = new long[paramArrayOfLong.length];
    for (byte b = 0; b < paramArrayOfLong.length; b++) {
      long l;
      Long long_ = paramArrayOfLong[b];
      if (long_ == null) {
        l = paramLong;
      } else {
        l = long_.longValue();
      } 
      arrayOfLong[b] = l;
    } 
    return arrayOfLong;
  }
  
  public static short[] toPrimitive(Short[] paramArrayOfShort) {
    if (paramArrayOfShort == null)
      return null; 
    if (paramArrayOfShort.length == 0)
      return EMPTY_SHORT_ARRAY; 
    short[] arrayOfShort = new short[paramArrayOfShort.length];
    for (byte b = 0; b < paramArrayOfShort.length; b++)
      arrayOfShort[b] = paramArrayOfShort[b].shortValue(); 
    return arrayOfShort;
  }
  
  public static short[] toPrimitive(Short[] paramArrayOfShort, short paramShort) {
    if (paramArrayOfShort == null)
      return null; 
    if (paramArrayOfShort.length == 0)
      return EMPTY_SHORT_ARRAY; 
    short[] arrayOfShort = new short[paramArrayOfShort.length];
    for (byte b = 0; b < paramArrayOfShort.length; b++) {
      short s;
      Short short_ = paramArrayOfShort[b];
      if (short_ == null) {
        s = paramShort;
      } else {
        s = short_.shortValue();
      } 
      arrayOfShort[b] = (short)s;
    } 
    return arrayOfShort;
  }
  
  public static boolean[] toPrimitive(Boolean[] paramArrayOfBoolean) {
    if (paramArrayOfBoolean == null)
      return null; 
    if (paramArrayOfBoolean.length == 0)
      return EMPTY_BOOLEAN_ARRAY; 
    boolean[] arrayOfBoolean = new boolean[paramArrayOfBoolean.length];
    for (byte b = 0; b < paramArrayOfBoolean.length; b++)
      arrayOfBoolean[b] = paramArrayOfBoolean[b].booleanValue(); 
    return arrayOfBoolean;
  }
  
  public static boolean[] toPrimitive(Boolean[] paramArrayOfBoolean, boolean paramBoolean) {
    if (paramArrayOfBoolean == null)
      return null; 
    if (paramArrayOfBoolean.length == 0)
      return EMPTY_BOOLEAN_ARRAY; 
    boolean[] arrayOfBoolean = new boolean[paramArrayOfBoolean.length];
    for (byte b = 0; b < paramArrayOfBoolean.length; b++) {
      boolean bool1;
      Boolean bool = paramArrayOfBoolean[b];
      if (bool == null) {
        bool1 = paramBoolean;
      } else {
        bool1 = bool.booleanValue();
      } 
      arrayOfBoolean[b] = bool1;
    } 
    return arrayOfBoolean;
  }
  
  public static String toString(Object paramObject) {
    return toString(paramObject, "{}");
  }
  
  public static String toString(Object paramObject, String paramString) {
    return (paramObject == null) ? paramString : (new ToStringBuilder(paramObject, ToStringStyle.SIMPLE_STYLE)).append(paramObject).toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\ArrayUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */