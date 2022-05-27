package external.org.apache.commons.lang3;

import external.org.apache.commons.lang3.exception.CloneFailedException;
import external.org.apache.commons.lang3.mutable.MutableInt;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

public class ObjectUtils {
  public static final Null NULL = new Null();
  
  public static <T> T clone(T paramT) {
    if (paramT instanceof Cloneable) {
      if (paramT.getClass().isArray()) {
        Class<?> clazz = paramT.getClass().getComponentType();
        if (!clazz.isPrimitive()) {
          paramT = (T)((Object[])paramT).clone();
        } else {
          int i = Array.getLength(paramT);
          Object object = Array.newInstance(clazz, i);
          while (true) {
            int j = i - 1;
            if (i > 0) {
              Array.set(object, j, Array.get(paramT, j));
              i = j;
              continue;
            } 
            return (T)object;
          } 
        } 
      } else {
        try {
          return (T)paramT.getClass().getMethod("clone", new Class[0]).invoke(paramT, new Object[0]);
        } catch (NoSuchMethodException noSuchMethodException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Cloneable type ");
          stringBuilder.append(paramT.getClass().getName());
          stringBuilder.append(" has no clone method");
          throw new CloneFailedException(stringBuilder.toString(), noSuchMethodException);
        } catch (IllegalAccessException illegalAccessException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Cannot clone Cloneable type ");
          stringBuilder.append(paramT.getClass().getName());
          throw new CloneFailedException(stringBuilder.toString(), illegalAccessException);
        } catch (InvocationTargetException invocationTargetException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Exception cloning Cloneable type ");
          stringBuilder.append(paramT.getClass().getName());
          throw new CloneFailedException(stringBuilder.toString(), invocationTargetException.getCause());
        } 
      } 
      return paramT;
    } 
    return null;
  }
  
  public static <T> T cloneIfPossible(T paramT) {
    T t = (T)clone((Object)paramT);
    if (t != null)
      paramT = t; 
    return paramT;
  }
  
  public static <T extends Comparable<? super T>> int compare(T paramT1, T paramT2) {
    return compare(paramT1, paramT2, false);
  }
  
  public static <T extends Comparable<? super T>> int compare(T paramT1, T paramT2, boolean paramBoolean) {
    if (paramT1 == paramT2)
      return 0; 
    boolean bool = true;
    byte b = 1;
    if (paramT1 == null) {
      if (!paramBoolean)
        b = -1; 
      return b;
    } 
    if (paramT2 == null) {
      b = bool;
      if (paramBoolean)
        b = -1; 
      return b;
    } 
    return paramT1.compareTo(paramT2);
  }
  
  public static <T> T defaultIfNull(T paramT1, T paramT2) {
    if (paramT1 == null)
      paramT1 = paramT2; 
    return paramT1;
  }
  
  public static boolean equals(Object paramObject1, Object paramObject2) {
    return (paramObject1 == paramObject2) ? true : ((paramObject1 == null || paramObject2 == null) ? false : paramObject1.equals(paramObject2));
  }
  
  public static <T> T firstNonNull(T... paramVarArgs) {
    if (paramVarArgs != null) {
      int i = paramVarArgs.length;
      for (byte b = 0; b < i; b++) {
        T t = paramVarArgs[b];
        if (t != null)
          return t; 
      } 
    } 
    return null;
  }
  
  public static int hashCode(Object paramObject) {
    int i;
    if (paramObject == null) {
      i = 0;
    } else {
      i = paramObject.hashCode();
    } 
    return i;
  }
  
  public static int hashCodeMulti(Object... paramVarArgs) {
    int i = 1;
    int j = 1;
    if (paramVarArgs != null) {
      int k = paramVarArgs.length;
      byte b = 0;
      while (true) {
        i = j;
        if (b < k) {
          j = j * 31 + hashCode(paramVarArgs[b]);
          b++;
          continue;
        } 
        break;
      } 
    } 
    return i;
  }
  
  public static String identityToString(Object paramObject) {
    if (paramObject == null)
      return null; 
    StringBuffer stringBuffer = new StringBuffer();
    identityToString(stringBuffer, paramObject);
    return stringBuffer.toString();
  }
  
  public static void identityToString(StringBuffer paramStringBuffer, Object paramObject) {
    if (paramObject != null) {
      paramStringBuffer.append(paramObject.getClass().getName());
      paramStringBuffer.append('@');
      paramStringBuffer.append(Integer.toHexString(System.identityHashCode(paramObject)));
      return;
    } 
    throw new NullPointerException("Cannot get the toString of a null identity");
  }
  
  public static <T extends Comparable<? super T>> T max(T... paramVarArgs) {
    T t1 = null;
    T t2 = null;
    if (paramVarArgs != null) {
      int i = paramVarArgs.length;
      byte b = 0;
      while (true) {
        t1 = t2;
        if (b < i) {
          T t4 = paramVarArgs[b];
          t1 = t2;
          if (compare(t4, t2, false) > 0)
            t1 = t4; 
          b++;
          T t3 = t1;
          continue;
        } 
        break;
      } 
    } 
    return t1;
  }
  
  public static <T extends Comparable<? super T>> T median(T... paramVarArgs) {
    Validate.notEmpty(paramVarArgs);
    Validate.noNullElements(paramVarArgs);
    TreeSet<? super T> treeSet = new TreeSet();
    Collections.addAll(treeSet, paramVarArgs);
    return (T)treeSet.toArray()[(treeSet.size() - 1) / 2];
  }
  
  public static <T> T median(Comparator<T> paramComparator, T... paramVarArgs) {
    Validate.notEmpty(paramVarArgs, "null/empty items", new Object[0]);
    Validate.noNullElements(paramVarArgs);
    Validate.notNull(paramComparator, "null comparator", new Object[0]);
    TreeSet<T> treeSet = new TreeSet<T>(paramComparator);
    Collections.addAll(treeSet, paramVarArgs);
    return (T)treeSet.toArray()[(treeSet.size() - 1) / 2];
  }
  
  public static <T extends Comparable<? super T>> T min(T... paramVarArgs) {
    T t1 = null;
    T t2 = null;
    if (paramVarArgs != null) {
      int i = paramVarArgs.length;
      byte b = 0;
      while (true) {
        t1 = t2;
        if (b < i) {
          T t4 = paramVarArgs[b];
          t1 = t2;
          if (compare(t4, t2, true) < 0)
            t1 = t4; 
          b++;
          T t3 = t1;
          continue;
        } 
        break;
      } 
    } 
    return t1;
  }
  
  public static <T> T mode(T... paramVarArgs) {
    if (ArrayUtils.isNotEmpty(paramVarArgs)) {
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>(paramVarArgs.length);
      int i = paramVarArgs.length;
      int j = 0;
      int k;
      for (k = 0; k < i; k++) {
        T t = paramVarArgs[k];
        MutableInt mutableInt = (MutableInt)hashMap.get(t);
        if (mutableInt == null) {
          hashMap.put(t, new MutableInt(1));
        } else {
          mutableInt.increment();
        } 
      } 
      Iterator<Map.Entry> iterator = hashMap.entrySet().iterator();
      k = j;
      label25: while (true) {
        paramVarArgs = null;
        while (iterator.hasNext()) {
          Map.Entry entry = iterator.next();
          j = ((MutableInt)entry.getValue()).intValue();
          if (j == k)
            continue label25; 
          if (j > k) {
            paramVarArgs = (T[])entry.getKey();
            k = j;
          } 
        } 
        return (T)paramVarArgs;
      } 
    } 
    return null;
  }
  
  public static boolean notEqual(Object paramObject1, Object paramObject2) {
    return equals(paramObject1, paramObject2) ^ true;
  }
  
  public static String toString(Object paramObject) {
    if (paramObject == null) {
      paramObject = "";
    } else {
      paramObject = paramObject.toString();
    } 
    return (String)paramObject;
  }
  
  public static String toString(Object paramObject, String paramString) {
    if (paramObject != null)
      paramString = paramObject.toString(); 
    return paramString;
  }
  
  public static class Null implements Serializable {
    private static final long serialVersionUID = 7092611880189329093L;
    
    private Object readResolve() {
      return ObjectUtils.NULL;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\ObjectUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */