package external.org.apache.commons.lang3.builder;

import external.org.apache.commons.lang3.ArrayUtils;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Comparator;

public class CompareToBuilder implements Builder<Integer> {
  private int comparison = 0;
  
  private static void reflectionAppend(Object paramObject1, Object paramObject2, Class<?> paramClass, CompareToBuilder paramCompareToBuilder, boolean paramBoolean, String[] paramArrayOfString) {
    Field[] arrayOfField = paramClass.getDeclaredFields();
    AccessibleObject.setAccessible((AccessibleObject[])arrayOfField, true);
    for (byte b = 0; b < arrayOfField.length && paramCompareToBuilder.comparison == 0; b++) {
      Field field = arrayOfField[b];
      if (!ArrayUtils.contains((Object[])paramArrayOfString, field.getName()) && field.getName().indexOf('$') == -1 && (paramBoolean || !Modifier.isTransient(field.getModifiers())) && !Modifier.isStatic(field.getModifiers()))
        try {
          paramCompareToBuilder.append(field.get(paramObject1), field.get(paramObject2));
        } catch (IllegalAccessException illegalAccessException) {
          throw new InternalError("Unexpected IllegalAccessException");
        }  
    } 
  }
  
  public static int reflectionCompare(Object paramObject1, Object paramObject2) {
    return reflectionCompare(paramObject1, paramObject2, false, null, new String[0]);
  }
  
  public static int reflectionCompare(Object paramObject1, Object paramObject2, Collection<String> paramCollection) {
    return reflectionCompare(paramObject1, paramObject2, ReflectionToStringBuilder.toNoNullStringArray(paramCollection));
  }
  
  public static int reflectionCompare(Object paramObject1, Object paramObject2, boolean paramBoolean) {
    return reflectionCompare(paramObject1, paramObject2, paramBoolean, null, new String[0]);
  }
  
  public static int reflectionCompare(Object paramObject1, Object paramObject2, boolean paramBoolean, Class<?> paramClass, String... paramVarArgs) {
    if (paramObject1 == paramObject2)
      return 0; 
    if (paramObject1 != null && paramObject2 != null) {
      Class<?> clazz = paramObject1.getClass();
      if (clazz.isInstance(paramObject2)) {
        CompareToBuilder compareToBuilder = new CompareToBuilder();
        reflectionAppend(paramObject1, paramObject2, clazz, compareToBuilder, paramBoolean, paramVarArgs);
        while (clazz.getSuperclass() != null && clazz != paramClass) {
          clazz = clazz.getSuperclass();
          reflectionAppend(paramObject1, paramObject2, clazz, compareToBuilder, paramBoolean, paramVarArgs);
        } 
        return compareToBuilder.toComparison();
      } 
      throw new ClassCastException();
    } 
    throw null;
  }
  
  public static int reflectionCompare(Object paramObject1, Object paramObject2, String... paramVarArgs) {
    return reflectionCompare(paramObject1, paramObject2, false, null, paramVarArgs);
  }
  
  public CompareToBuilder append(byte paramByte1, byte paramByte2) {
    if (this.comparison != 0)
      return this; 
    if (paramByte1 < paramByte2) {
      paramByte1 = -1;
    } else if (paramByte1 > paramByte2) {
      paramByte1 = 1;
    } else {
      paramByte1 = 0;
    } 
    this.comparison = paramByte1;
    return this;
  }
  
  public CompareToBuilder append(char paramChar1, char paramChar2) {
    byte b;
    if (this.comparison != 0)
      return this; 
    if (paramChar1 < paramChar2) {
      b = -1;
    } else if (b > paramChar2) {
      b = 1;
    } else {
      b = 0;
    } 
    this.comparison = b;
    return this;
  }
  
  public CompareToBuilder append(double paramDouble1, double paramDouble2) {
    if (this.comparison != 0)
      return this; 
    this.comparison = Double.compare(paramDouble1, paramDouble2);
    return this;
  }
  
  public CompareToBuilder append(float paramFloat1, float paramFloat2) {
    if (this.comparison != 0)
      return this; 
    this.comparison = Float.compare(paramFloat1, paramFloat2);
    return this;
  }
  
  public CompareToBuilder append(int paramInt1, int paramInt2) {
    if (this.comparison != 0)
      return this; 
    if (paramInt1 < paramInt2) {
      paramInt1 = -1;
    } else if (paramInt1 > paramInt2) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    } 
    this.comparison = paramInt1;
    return this;
  }
  
  public CompareToBuilder append(long paramLong1, long paramLong2) {
    if (this.comparison != 0)
      return this; 
    int i = paramLong1 cmp paramLong2;
    if (i < 0) {
      i = -1;
    } else if (i > 0) {
      i = 1;
    } else {
      i = 0;
    } 
    this.comparison = i;
    return this;
  }
  
  public CompareToBuilder append(Object paramObject1, Object paramObject2) {
    return append(paramObject1, paramObject2, (Comparator<?>)null);
  }
  
  public CompareToBuilder append(Object paramObject1, Object paramObject2, Comparator<?> paramComparator) {
    if (this.comparison != 0)
      return this; 
    if (paramObject1 == paramObject2)
      return this; 
    if (paramObject1 == null) {
      this.comparison = -1;
      return this;
    } 
    if (paramObject2 == null) {
      this.comparison = 1;
      return this;
    } 
    if (paramObject1.getClass().isArray()) {
      if (paramObject1 instanceof long[]) {
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
        append((Object[])paramObject1, (Object[])paramObject2, paramComparator);
      } 
    } else if (paramComparator == null) {
      this.comparison = ((Comparable<Object>)paramObject1).compareTo(paramObject2);
    } else {
      this.comparison = paramComparator.compare(paramObject1, paramObject2);
    } 
    return this;
  }
  
  public CompareToBuilder append(short paramShort1, short paramShort2) {
    if (this.comparison != 0)
      return this; 
    if (paramShort1 < paramShort2) {
      paramShort1 = -1;
    } else if (paramShort1 > paramShort2) {
      paramShort1 = 1;
    } else {
      paramShort1 = 0;
    } 
    this.comparison = paramShort1;
    return this;
  }
  
  public CompareToBuilder append(boolean paramBoolean1, boolean paramBoolean2) {
    if (this.comparison != 0)
      return this; 
    if (paramBoolean1 == paramBoolean2)
      return this; 
    if (!paramBoolean1) {
      this.comparison = -1;
    } else {
      this.comparison = 1;
    } 
    return this;
  }
  
  public CompareToBuilder append(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    if (this.comparison != 0)
      return this; 
    if (paramArrayOfbyte1 == paramArrayOfbyte2)
      return this; 
    byte b = -1;
    if (paramArrayOfbyte1 == null) {
      this.comparison = -1;
      return this;
    } 
    if (paramArrayOfbyte2 == null) {
      this.comparison = 1;
      return this;
    } 
    if (paramArrayOfbyte1.length != paramArrayOfbyte2.length) {
      if (paramArrayOfbyte1.length >= paramArrayOfbyte2.length)
        b = 1; 
      this.comparison = b;
      return this;
    } 
    for (b = 0; b < paramArrayOfbyte1.length && this.comparison == 0; b++)
      append(paramArrayOfbyte1[b], paramArrayOfbyte2[b]); 
    return this;
  }
  
  public CompareToBuilder append(char[] paramArrayOfchar1, char[] paramArrayOfchar2) {
    if (this.comparison != 0)
      return this; 
    if (paramArrayOfchar1 == paramArrayOfchar2)
      return this; 
    byte b = -1;
    if (paramArrayOfchar1 == null) {
      this.comparison = -1;
      return this;
    } 
    if (paramArrayOfchar2 == null) {
      this.comparison = 1;
      return this;
    } 
    if (paramArrayOfchar1.length != paramArrayOfchar2.length) {
      if (paramArrayOfchar1.length >= paramArrayOfchar2.length)
        b = 1; 
      this.comparison = b;
      return this;
    } 
    for (b = 0; b < paramArrayOfchar1.length && this.comparison == 0; b++)
      append(paramArrayOfchar1[b], paramArrayOfchar2[b]); 
    return this;
  }
  
  public CompareToBuilder append(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
    if (this.comparison != 0)
      return this; 
    if (paramArrayOfdouble1 == paramArrayOfdouble2)
      return this; 
    byte b = -1;
    if (paramArrayOfdouble1 == null) {
      this.comparison = -1;
      return this;
    } 
    if (paramArrayOfdouble2 == null) {
      this.comparison = 1;
      return this;
    } 
    if (paramArrayOfdouble1.length != paramArrayOfdouble2.length) {
      if (paramArrayOfdouble1.length >= paramArrayOfdouble2.length)
        b = 1; 
      this.comparison = b;
      return this;
    } 
    for (b = 0; b < paramArrayOfdouble1.length && this.comparison == 0; b++)
      append(paramArrayOfdouble1[b], paramArrayOfdouble2[b]); 
    return this;
  }
  
  public CompareToBuilder append(float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
    if (this.comparison != 0)
      return this; 
    if (paramArrayOffloat1 == paramArrayOffloat2)
      return this; 
    byte b = -1;
    if (paramArrayOffloat1 == null) {
      this.comparison = -1;
      return this;
    } 
    if (paramArrayOffloat2 == null) {
      this.comparison = 1;
      return this;
    } 
    if (paramArrayOffloat1.length != paramArrayOffloat2.length) {
      if (paramArrayOffloat1.length >= paramArrayOffloat2.length)
        b = 1; 
      this.comparison = b;
      return this;
    } 
    for (b = 0; b < paramArrayOffloat1.length && this.comparison == 0; b++)
      append(paramArrayOffloat1[b], paramArrayOffloat2[b]); 
    return this;
  }
  
  public CompareToBuilder append(int[] paramArrayOfint1, int[] paramArrayOfint2) {
    if (this.comparison != 0)
      return this; 
    if (paramArrayOfint1 == paramArrayOfint2)
      return this; 
    byte b = -1;
    if (paramArrayOfint1 == null) {
      this.comparison = -1;
      return this;
    } 
    if (paramArrayOfint2 == null) {
      this.comparison = 1;
      return this;
    } 
    if (paramArrayOfint1.length != paramArrayOfint2.length) {
      if (paramArrayOfint1.length >= paramArrayOfint2.length)
        b = 1; 
      this.comparison = b;
      return this;
    } 
    for (b = 0; b < paramArrayOfint1.length && this.comparison == 0; b++)
      append(paramArrayOfint1[b], paramArrayOfint2[b]); 
    return this;
  }
  
  public CompareToBuilder append(long[] paramArrayOflong1, long[] paramArrayOflong2) {
    if (this.comparison != 0)
      return this; 
    if (paramArrayOflong1 == paramArrayOflong2)
      return this; 
    byte b = -1;
    if (paramArrayOflong1 == null) {
      this.comparison = -1;
      return this;
    } 
    if (paramArrayOflong2 == null) {
      this.comparison = 1;
      return this;
    } 
    if (paramArrayOflong1.length != paramArrayOflong2.length) {
      if (paramArrayOflong1.length >= paramArrayOflong2.length)
        b = 1; 
      this.comparison = b;
      return this;
    } 
    for (b = 0; b < paramArrayOflong1.length && this.comparison == 0; b++)
      append(paramArrayOflong1[b], paramArrayOflong2[b]); 
    return this;
  }
  
  public CompareToBuilder append(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2) {
    return append(paramArrayOfObject1, paramArrayOfObject2, (Comparator<?>)null);
  }
  
  public CompareToBuilder append(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2, Comparator<?> paramComparator) {
    if (this.comparison != 0)
      return this; 
    if (paramArrayOfObject1 == paramArrayOfObject2)
      return this; 
    byte b = -1;
    if (paramArrayOfObject1 == null) {
      this.comparison = -1;
      return this;
    } 
    if (paramArrayOfObject2 == null) {
      this.comparison = 1;
      return this;
    } 
    if (paramArrayOfObject1.length != paramArrayOfObject2.length) {
      if (paramArrayOfObject1.length >= paramArrayOfObject2.length)
        b = 1; 
      this.comparison = b;
      return this;
    } 
    for (b = 0; b < paramArrayOfObject1.length && this.comparison == 0; b++)
      append(paramArrayOfObject1[b], paramArrayOfObject2[b], paramComparator); 
    return this;
  }
  
  public CompareToBuilder append(short[] paramArrayOfshort1, short[] paramArrayOfshort2) {
    if (this.comparison != 0)
      return this; 
    if (paramArrayOfshort1 == paramArrayOfshort2)
      return this; 
    byte b = -1;
    if (paramArrayOfshort1 == null) {
      this.comparison = -1;
      return this;
    } 
    if (paramArrayOfshort2 == null) {
      this.comparison = 1;
      return this;
    } 
    if (paramArrayOfshort1.length != paramArrayOfshort2.length) {
      if (paramArrayOfshort1.length >= paramArrayOfshort2.length)
        b = 1; 
      this.comparison = b;
      return this;
    } 
    for (b = 0; b < paramArrayOfshort1.length && this.comparison == 0; b++)
      append(paramArrayOfshort1[b], paramArrayOfshort2[b]); 
    return this;
  }
  
  public CompareToBuilder append(boolean[] paramArrayOfboolean1, boolean[] paramArrayOfboolean2) {
    if (this.comparison != 0)
      return this; 
    if (paramArrayOfboolean1 == paramArrayOfboolean2)
      return this; 
    byte b = -1;
    if (paramArrayOfboolean1 == null) {
      this.comparison = -1;
      return this;
    } 
    if (paramArrayOfboolean2 == null) {
      this.comparison = 1;
      return this;
    } 
    if (paramArrayOfboolean1.length != paramArrayOfboolean2.length) {
      if (paramArrayOfboolean1.length >= paramArrayOfboolean2.length)
        b = 1; 
      this.comparison = b;
      return this;
    } 
    for (b = 0; b < paramArrayOfboolean1.length && this.comparison == 0; b++)
      append(paramArrayOfboolean1[b], paramArrayOfboolean2[b]); 
    return this;
  }
  
  public CompareToBuilder appendSuper(int paramInt) {
    if (this.comparison != 0)
      return this; 
    this.comparison = paramInt;
    return this;
  }
  
  public Integer build() {
    return Integer.valueOf(toComparison());
  }
  
  public int toComparison() {
    return this.comparison;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\builder\CompareToBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */