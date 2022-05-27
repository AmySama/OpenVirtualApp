package android.support.v4.util;

import android.text.TextUtils;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

public class Preconditions {
  public static void checkArgument(boolean paramBoolean) {
    if (paramBoolean)
      return; 
    throw new IllegalArgumentException();
  }
  
  public static void checkArgument(boolean paramBoolean, Object paramObject) {
    if (paramBoolean)
      return; 
    throw new IllegalArgumentException(String.valueOf(paramObject));
  }
  
  public static float checkArgumentFinite(float paramFloat, String paramString) {
    if (!Float.isNaN(paramFloat)) {
      if (!Float.isInfinite(paramFloat))
        return paramFloat; 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString);
      stringBuilder1.append(" must not be infinite");
      throw new IllegalArgumentException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append(" must not be NaN");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public static float checkArgumentInRange(float paramFloat1, float paramFloat2, float paramFloat3, String paramString) {
    if (!Float.isNaN(paramFloat1)) {
      if (paramFloat1 >= paramFloat2) {
        if (paramFloat1 <= paramFloat3)
          return paramFloat1; 
        throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%f, %f] (too high)", new Object[] { paramString, Float.valueOf(paramFloat2), Float.valueOf(paramFloat3) }));
      } 
      throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%f, %f] (too low)", new Object[] { paramString, Float.valueOf(paramFloat2), Float.valueOf(paramFloat3) }));
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append(" must not be NaN");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public static int checkArgumentInRange(int paramInt1, int paramInt2, int paramInt3, String paramString) {
    if (paramInt1 >= paramInt2) {
      if (paramInt1 <= paramInt3)
        return paramInt1; 
      throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too high)", new Object[] { paramString, Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) }));
    } 
    throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too low)", new Object[] { paramString, Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) }));
  }
  
  public static long checkArgumentInRange(long paramLong1, long paramLong2, long paramLong3, String paramString) {
    if (paramLong1 >= paramLong2) {
      if (paramLong1 <= paramLong3)
        return paramLong1; 
      throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too high)", new Object[] { paramString, Long.valueOf(paramLong2), Long.valueOf(paramLong3) }));
    } 
    throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too low)", new Object[] { paramString, Long.valueOf(paramLong2), Long.valueOf(paramLong3) }));
  }
  
  public static int checkArgumentNonnegative(int paramInt) {
    if (paramInt >= 0)
      return paramInt; 
    throw new IllegalArgumentException();
  }
  
  public static int checkArgumentNonnegative(int paramInt, String paramString) {
    if (paramInt >= 0)
      return paramInt; 
    throw new IllegalArgumentException(paramString);
  }
  
  public static long checkArgumentNonnegative(long paramLong) {
    if (paramLong >= 0L)
      return paramLong; 
    throw new IllegalArgumentException();
  }
  
  public static long checkArgumentNonnegative(long paramLong, String paramString) {
    if (paramLong >= 0L)
      return paramLong; 
    throw new IllegalArgumentException(paramString);
  }
  
  public static int checkArgumentPositive(int paramInt, String paramString) {
    if (paramInt > 0)
      return paramInt; 
    throw new IllegalArgumentException(paramString);
  }
  
  public static float[] checkArrayElementsInRange(float[] paramArrayOffloat, float paramFloat1, float paramFloat2, String paramString) {
    StringBuilder stringBuilder1;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString);
    stringBuilder2.append(" must not be null");
    checkNotNull(paramArrayOffloat, stringBuilder2.toString());
    byte b = 0;
    while (b < paramArrayOffloat.length) {
      float f = paramArrayOffloat[b];
      if (!Float.isNaN(f)) {
        if (f >= paramFloat1) {
          if (f <= paramFloat2) {
            b++;
            continue;
          } 
          throw new IllegalArgumentException(String.format(Locale.US, "%s[%d] is out of range of [%f, %f] (too high)", new Object[] { paramString, Integer.valueOf(b), Float.valueOf(paramFloat1), Float.valueOf(paramFloat2) }));
        } 
        throw new IllegalArgumentException(String.format(Locale.US, "%s[%d] is out of range of [%f, %f] (too low)", new Object[] { paramString, Integer.valueOf(b), Float.valueOf(paramFloat1), Float.valueOf(paramFloat2) }));
      } 
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString);
      stringBuilder1.append("[");
      stringBuilder1.append(b);
      stringBuilder1.append("] must not be NaN");
      throw new IllegalArgumentException(stringBuilder1.toString());
    } 
    return (float[])stringBuilder1;
  }
  
  public static <T> T[] checkArrayElementsNotNull(T[] paramArrayOfT, String paramString) {
    if (paramArrayOfT != null) {
      byte b = 0;
      while (b < paramArrayOfT.length) {
        if (paramArrayOfT[b] != null) {
          b++;
          continue;
        } 
        throw new NullPointerException(String.format(Locale.US, "%s[%d] must not be null", new Object[] { paramString, Integer.valueOf(b) }));
      } 
      return paramArrayOfT;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append(" must not be null");
    throw new NullPointerException(stringBuilder.toString());
  }
  
  public static <C extends Collection<T>, T> C checkCollectionElementsNotNull(C paramC, String paramString) {
    if (paramC != null) {
      long l = 0L;
      Iterator iterator = paramC.iterator();
      while (iterator.hasNext()) {
        if (iterator.next() != null) {
          l++;
          continue;
        } 
        throw new NullPointerException(String.format(Locale.US, "%s[%d] must not be null", new Object[] { paramString, Long.valueOf(l) }));
      } 
      return paramC;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append(" must not be null");
    throw new NullPointerException(stringBuilder.toString());
  }
  
  public static <T> Collection<T> checkCollectionNotEmpty(Collection<T> paramCollection, String paramString) {
    if (paramCollection != null) {
      if (!paramCollection.isEmpty())
        return paramCollection; 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString);
      stringBuilder1.append(" is empty");
      throw new IllegalArgumentException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append(" must not be null");
    throw new NullPointerException(stringBuilder.toString());
  }
  
  public static int checkFlagsArgument(int paramInt1, int paramInt2) {
    if ((paramInt1 & paramInt2) == paramInt1)
      return paramInt1; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Requested flags 0x");
    stringBuilder.append(Integer.toHexString(paramInt1));
    stringBuilder.append(", but only 0x");
    stringBuilder.append(Integer.toHexString(paramInt2));
    stringBuilder.append(" are allowed");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public static <T> T checkNotNull(T paramT) {
    if (paramT != null)
      return paramT; 
    throw null;
  }
  
  public static <T> T checkNotNull(T paramT, Object paramObject) {
    if (paramT != null)
      return paramT; 
    throw new NullPointerException(String.valueOf(paramObject));
  }
  
  public static void checkState(boolean paramBoolean) {
    checkState(paramBoolean, null);
  }
  
  public static void checkState(boolean paramBoolean, String paramString) {
    if (paramBoolean)
      return; 
    throw new IllegalStateException(paramString);
  }
  
  public static <T extends CharSequence> T checkStringNotEmpty(T paramT) {
    if (!TextUtils.isEmpty((CharSequence)paramT))
      return paramT; 
    throw new IllegalArgumentException();
  }
  
  public static <T extends CharSequence> T checkStringNotEmpty(T paramT, Object paramObject) {
    if (!TextUtils.isEmpty((CharSequence)paramT))
      return paramT; 
    throw new IllegalArgumentException(String.valueOf(paramObject));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v\\util\Preconditions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */