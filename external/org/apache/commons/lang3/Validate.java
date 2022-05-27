package external.org.apache.commons.lang3;

import java.util.Iterator;
import java.util.regex.Pattern;

public class Validate {
  private static final String DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified exclusive range of %s to %s";
  
  private static final String DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified inclusive range of %s to %s";
  
  private static final String DEFAULT_IS_ASSIGNABLE_EX_MESSAGE = "Cannot assign a %s to a %s";
  
  private static final String DEFAULT_IS_INSTANCE_OF_EX_MESSAGE = "Expected type: %s, actual: %s";
  
  private static final String DEFAULT_IS_NULL_EX_MESSAGE = "The validated object is null";
  
  private static final String DEFAULT_IS_TRUE_EX_MESSAGE = "The validated expression is false";
  
  private static final String DEFAULT_MATCHES_PATTERN_EX = "The string %s does not match the pattern %s";
  
  private static final String DEFAULT_NOT_BLANK_EX_MESSAGE = "The validated character sequence is blank";
  
  private static final String DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE = "The validated array is empty";
  
  private static final String DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence is empty";
  
  private static final String DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE = "The validated collection is empty";
  
  private static final String DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE = "The validated map is empty";
  
  private static final String DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE = "The validated array contains null element at index: %d";
  
  private static final String DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE = "The validated collection contains null element at index: %d";
  
  private static final String DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE = "The validated array index is invalid: %d";
  
  private static final String DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence index is invalid: %d";
  
  private static final String DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE = "The validated collection index is invalid: %d";
  
  private static final String DEFAULT_VALID_STATE_EX_MESSAGE = "The validated state is false";
  
  public static <T> void exclusiveBetween(T paramT1, T paramT2, Comparable<T> paramComparable) {
    if (paramComparable.compareTo(paramT1) > 0 && paramComparable.compareTo(paramT2) < 0)
      return; 
    throw new IllegalArgumentException(String.format("The value %s is not in the specified exclusive range of %s to %s", new Object[] { paramComparable, paramT1, paramT2 }));
  }
  
  public static <T> void exclusiveBetween(T paramT1, T paramT2, Comparable<T> paramComparable, String paramString, Object... paramVarArgs) {
    if (paramComparable.compareTo(paramT1) > 0 && paramComparable.compareTo(paramT2) < 0)
      return; 
    throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
  }
  
  public static <T> void inclusiveBetween(T paramT1, T paramT2, Comparable<T> paramComparable) {
    if (paramComparable.compareTo(paramT1) >= 0 && paramComparable.compareTo(paramT2) <= 0)
      return; 
    throw new IllegalArgumentException(String.format("The value %s is not in the specified inclusive range of %s to %s", new Object[] { paramComparable, paramT1, paramT2 }));
  }
  
  public static <T> void inclusiveBetween(T paramT1, T paramT2, Comparable<T> paramComparable, String paramString, Object... paramVarArgs) {
    if (paramComparable.compareTo(paramT1) >= 0 && paramComparable.compareTo(paramT2) <= 0)
      return; 
    throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
  }
  
  public static void isAssignableFrom(Class<?> paramClass1, Class<?> paramClass2) {
    if (!paramClass1.isAssignableFrom(paramClass2)) {
      String str;
      if (paramClass2 == null) {
        str = "null";
      } else {
        str = str.getName();
      } 
      throw new IllegalArgumentException(String.format("Cannot assign a %s to a %s", new Object[] { str, paramClass1.getName() }));
    } 
  }
  
  public static void isAssignableFrom(Class<?> paramClass1, Class<?> paramClass2, String paramString, Object... paramVarArgs) {
    if (paramClass1.isAssignableFrom(paramClass2))
      return; 
    throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
  }
  
  public static void isInstanceOf(Class<?> paramClass, Object paramObject) {
    if (!paramClass.isInstance(paramObject)) {
      String str1;
      String str2 = paramClass.getName();
      if (paramObject == null) {
        str1 = "null";
      } else {
        str1 = paramObject.getClass().getName();
      } 
      throw new IllegalArgumentException(String.format("Expected type: %s, actual: %s", new Object[] { str2, str1 }));
    } 
  }
  
  public static void isInstanceOf(Class<?> paramClass, Object paramObject, String paramString, Object... paramVarArgs) {
    if (paramClass.isInstance(paramObject))
      return; 
    throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
  }
  
  public static void isTrue(boolean paramBoolean) {
    if (paramBoolean)
      return; 
    throw new IllegalArgumentException("The validated expression is false");
  }
  
  public static void isTrue(boolean paramBoolean, String paramString, double paramDouble) {
    if (paramBoolean)
      return; 
    throw new IllegalArgumentException(String.format(paramString, new Object[] { Double.valueOf(paramDouble) }));
  }
  
  public static void isTrue(boolean paramBoolean, String paramString, long paramLong) {
    if (paramBoolean)
      return; 
    throw new IllegalArgumentException(String.format(paramString, new Object[] { Long.valueOf(paramLong) }));
  }
  
  public static void isTrue(boolean paramBoolean, String paramString, Object... paramVarArgs) {
    if (paramBoolean)
      return; 
    throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
  }
  
  public static void matchesPattern(CharSequence paramCharSequence, String paramString) {
    if (Pattern.matches(paramString, paramCharSequence))
      return; 
    throw new IllegalArgumentException(String.format("The string %s does not match the pattern %s", new Object[] { paramCharSequence, paramString }));
  }
  
  public static void matchesPattern(CharSequence paramCharSequence, String paramString1, String paramString2, Object... paramVarArgs) {
    if (Pattern.matches(paramString1, paramCharSequence))
      return; 
    throw new IllegalArgumentException(String.format(paramString2, paramVarArgs));
  }
  
  public static <T extends Iterable<?>> T noNullElements(T paramT) {
    return noNullElements(paramT, "The validated collection contains null element at index: %d", new Object[0]);
  }
  
  public static <T extends Iterable<?>> T noNullElements(T paramT, String paramString, Object... paramVarArgs) {
    notNull(paramT);
    Iterator iterator = paramT.iterator();
    byte b = 0;
    while (iterator.hasNext()) {
      if (iterator.next() != null) {
        b++;
        continue;
      } 
      throw new IllegalArgumentException(String.format(paramString, ArrayUtils.addAll(paramVarArgs, new Object[] { Integer.valueOf(b) })));
    } 
    return paramT;
  }
  
  public static <T> T[] noNullElements(T[] paramArrayOfT) {
    return noNullElements(paramArrayOfT, "The validated array contains null element at index: %d", new Object[0]);
  }
  
  public static <T> T[] noNullElements(T[] paramArrayOfT, String paramString, Object... paramVarArgs) {
    notNull(paramArrayOfT);
    byte b = 0;
    while (b < paramArrayOfT.length) {
      if (paramArrayOfT[b] != null) {
        b++;
        continue;
      } 
      throw new IllegalArgumentException(String.format(paramString, ArrayUtils.add(paramVarArgs, Integer.valueOf(b))));
    } 
    return paramArrayOfT;
  }
  
  public static <T extends CharSequence> T notBlank(T paramT) {
    return notBlank(paramT, "The validated character sequence is blank", new Object[0]);
  }
  
  public static <T extends CharSequence> T notBlank(T paramT, String paramString, Object... paramVarArgs) {
    if (paramT != null) {
      if (!StringUtils.isBlank((CharSequence)paramT))
        return paramT; 
      throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
    } 
    throw new NullPointerException(String.format(paramString, paramVarArgs));
  }
  
  public static <T extends CharSequence> T notEmpty(T paramT) {
    return notEmpty(paramT, "The validated character sequence is empty", new Object[0]);
  }
  
  public static <T extends CharSequence> T notEmpty(T paramT, String paramString, Object... paramVarArgs) {
    if (paramT != null) {
      if (paramT.length() != 0)
        return paramT; 
      throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
    } 
    throw new NullPointerException(String.format(paramString, paramVarArgs));
  }
  
  public static <T extends java.util.Collection<?>> T notEmpty(T paramT) {
    return notEmpty(paramT, "The validated collection is empty", new Object[0]);
  }
  
  public static <T extends java.util.Collection<?>> T notEmpty(T paramT, String paramString, Object... paramVarArgs) {
    if (paramT != null) {
      if (!paramT.isEmpty())
        return paramT; 
      throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
    } 
    throw new NullPointerException(String.format(paramString, paramVarArgs));
  }
  
  public static <T extends java.util.Map<?, ?>> T notEmpty(T paramT) {
    return notEmpty(paramT, "The validated map is empty", new Object[0]);
  }
  
  public static <T extends java.util.Map<?, ?>> T notEmpty(T paramT, String paramString, Object... paramVarArgs) {
    if (paramT != null) {
      if (!paramT.isEmpty())
        return paramT; 
      throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
    } 
    throw new NullPointerException(String.format(paramString, paramVarArgs));
  }
  
  public static <T> T[] notEmpty(T[] paramArrayOfT) {
    return notEmpty(paramArrayOfT, "The validated array is empty", new Object[0]);
  }
  
  public static <T> T[] notEmpty(T[] paramArrayOfT, String paramString, Object... paramVarArgs) {
    if (paramArrayOfT != null) {
      if (paramArrayOfT.length != 0)
        return paramArrayOfT; 
      throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
    } 
    throw new NullPointerException(String.format(paramString, paramVarArgs));
  }
  
  public static <T> T notNull(T paramT) {
    return notNull(paramT, "The validated object is null", new Object[0]);
  }
  
  public static <T> T notNull(T paramT, String paramString, Object... paramVarArgs) {
    if (paramT != null)
      return paramT; 
    throw new NullPointerException(String.format(paramString, paramVarArgs));
  }
  
  public static <T extends CharSequence> T validIndex(T paramT, int paramInt) {
    return validIndex(paramT, paramInt, "The validated character sequence index is invalid: %d", new Object[] { Integer.valueOf(paramInt) });
  }
  
  public static <T extends CharSequence> T validIndex(T paramT, int paramInt, String paramString, Object... paramVarArgs) {
    notNull(paramT);
    if (paramInt >= 0 && paramInt < paramT.length())
      return paramT; 
    throw new IndexOutOfBoundsException(String.format(paramString, paramVarArgs));
  }
  
  public static <T extends java.util.Collection<?>> T validIndex(T paramT, int paramInt) {
    return validIndex(paramT, paramInt, "The validated collection index is invalid: %d", new Object[] { Integer.valueOf(paramInt) });
  }
  
  public static <T extends java.util.Collection<?>> T validIndex(T paramT, int paramInt, String paramString, Object... paramVarArgs) {
    notNull(paramT);
    if (paramInt >= 0 && paramInt < paramT.size())
      return paramT; 
    throw new IndexOutOfBoundsException(String.format(paramString, paramVarArgs));
  }
  
  public static <T> T[] validIndex(T[] paramArrayOfT, int paramInt) {
    return validIndex(paramArrayOfT, paramInt, "The validated array index is invalid: %d", new Object[] { Integer.valueOf(paramInt) });
  }
  
  public static <T> T[] validIndex(T[] paramArrayOfT, int paramInt, String paramString, Object... paramVarArgs) {
    notNull(paramArrayOfT);
    if (paramInt >= 0 && paramInt < paramArrayOfT.length)
      return paramArrayOfT; 
    throw new IndexOutOfBoundsException(String.format(paramString, paramVarArgs));
  }
  
  public static void validState(boolean paramBoolean) {
    if (paramBoolean)
      return; 
    throw new IllegalStateException("The validated state is false");
  }
  
  public static void validState(boolean paramBoolean, String paramString, Object... paramVarArgs) {
    if (paramBoolean)
      return; 
    throw new IllegalStateException(String.format(paramString, paramVarArgs));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\Validate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */