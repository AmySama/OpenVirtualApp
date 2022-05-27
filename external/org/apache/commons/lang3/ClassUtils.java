package external.org.apache.commons.lang3;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class ClassUtils {
  public static final String INNER_CLASS_SEPARATOR;
  
  public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
  
  public static final String PACKAGE_SEPARATOR = String.valueOf('.');
  
  public static final char PACKAGE_SEPARATOR_CHAR = '.';
  
  private static final Map<String, String> abbreviationMap;
  
  private static final Map<Class<?>, Class<?>> primitiveWrapperMap;
  
  private static final Map<String, String> reverseAbbreviationMap;
  
  private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap;
  
  static {
    INNER_CLASS_SEPARATOR = String.valueOf('$');
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    primitiveWrapperMap = (Map)hashMap;
    hashMap.put(boolean.class, Boolean.class);
    primitiveWrapperMap.put(byte.class, Byte.class);
    primitiveWrapperMap.put(char.class, Character.class);
    primitiveWrapperMap.put(short.class, Short.class);
    primitiveWrapperMap.put(int.class, Integer.class);
    primitiveWrapperMap.put(long.class, Long.class);
    primitiveWrapperMap.put(double.class, Double.class);
    primitiveWrapperMap.put(float.class, Float.class);
    primitiveWrapperMap.put(void.class, void.class);
    wrapperPrimitiveMap = new HashMap<Class<?>, Class<?>>();
    for (Class<?> clazz2 : primitiveWrapperMap.keySet()) {
      Class<?> clazz1 = primitiveWrapperMap.get(clazz2);
      if (!clazz2.equals(clazz1))
        wrapperPrimitiveMap.put(clazz1, clazz2); 
    } 
    abbreviationMap = new HashMap<String, String>();
    reverseAbbreviationMap = new HashMap<String, String>();
    addAbbreviation("int", "I");
    addAbbreviation("boolean", "Z");
    addAbbreviation("float", "F");
    addAbbreviation("long", "J");
    addAbbreviation("short", "S");
    addAbbreviation("byte", "B");
    addAbbreviation("double", "D");
    addAbbreviation("char", "C");
  }
  
  private static void addAbbreviation(String paramString1, String paramString2) {
    abbreviationMap.put(paramString1, paramString2);
    reverseAbbreviationMap.put(paramString2, paramString1);
  }
  
  public static List<Class<?>> convertClassNamesToClasses(List<String> paramList) {
    if (paramList == null)
      return null; 
    ArrayList<Class<?>> arrayList = new ArrayList(paramList.size());
    for (String str : paramList) {
      try {
        arrayList.add(Class.forName(str));
      } catch (Exception exception) {
        arrayList.add(null);
      } 
    } 
    return arrayList;
  }
  
  public static List<String> convertClassesToClassNames(List<Class<?>> paramList) {
    if (paramList == null)
      return null; 
    ArrayList<String> arrayList = new ArrayList(paramList.size());
    for (Class<?> clazz : paramList) {
      if (clazz == null) {
        arrayList.add(null);
        continue;
      } 
      arrayList.add(clazz.getName());
    } 
    return arrayList;
  }
  
  public static List<Class<?>> getAllInterfaces(Class<?> paramClass) {
    if (paramClass == null)
      return null; 
    LinkedHashSet<Class<?>> linkedHashSet = new LinkedHashSet();
    getAllInterfaces(paramClass, linkedHashSet);
    return new ArrayList<Class<?>>(linkedHashSet);
  }
  
  private static void getAllInterfaces(Class<?> paramClass, HashSet<Class<?>> paramHashSet) {
    while (paramClass != null) {
      for (Class<?> clazz : paramClass.getInterfaces()) {
        if (paramHashSet.add(clazz))
          getAllInterfaces(clazz, paramHashSet); 
      } 
      paramClass = paramClass.getSuperclass();
    } 
  }
  
  public static List<Class<?>> getAllSuperclasses(Class<?> paramClass) {
    if (paramClass == null)
      return null; 
    ArrayList<Class<?>> arrayList = new ArrayList();
    for (paramClass = paramClass.getSuperclass(); paramClass != null; paramClass = paramClass.getSuperclass())
      arrayList.add(paramClass); 
    return arrayList;
  }
  
  private static String getCanonicalName(String paramString) {
    String str = StringUtils.deleteWhitespace(paramString);
    if (str == null)
      return null; 
    byte b1 = 0;
    byte b2 = 0;
    while (str.startsWith("[")) {
      b2++;
      str = str.substring(1);
    } 
    if (b2 < 1)
      return str; 
    if (str.startsWith("L")) {
      int i;
      if (str.endsWith(";")) {
        i = str.length() - 1;
      } else {
        i = str.length();
      } 
      paramString = str.substring(1, i);
    } else {
      paramString = str;
      if (str.length() > 0)
        paramString = reverseAbbreviationMap.get(str.substring(0, 1)); 
    } 
    StringBuilder stringBuilder = new StringBuilder(paramString);
    for (byte b3 = b1; b3 < b2; b3++)
      stringBuilder.append("[]"); 
    return stringBuilder.toString();
  }
  
  public static Class<?> getClass(ClassLoader paramClassLoader, String paramString) throws ClassNotFoundException {
    return getClass(paramClassLoader, paramString, true);
  }
  
  public static Class<?> getClass(ClassLoader paramClassLoader, String paramString, boolean paramBoolean) throws ClassNotFoundException {
    Class<?> clazz;
    try {
      if (abbreviationMap.containsKey(paramString)) {
        StringBuilder stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("[");
        stringBuilder.append(abbreviationMap.get(paramString));
        Class<?> clazz1 = Class.forName(stringBuilder.toString(), paramBoolean, paramClassLoader).getComponentType();
        clazz = clazz1;
      } else {
        Class<?> clazz1 = Class.forName(toCanonicalName(paramString), paramBoolean, (ClassLoader)clazz);
        clazz = clazz1;
      } 
      return clazz;
    } catch (ClassNotFoundException classNotFoundException) {
      int i = paramString.lastIndexOf('.');
      if (i != -1)
        try {
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append(paramString.substring(0, i));
          stringBuilder.append('$');
          stringBuilder.append(paramString.substring(i + 1));
          return getClass((ClassLoader)clazz, stringBuilder.toString(), paramBoolean);
        } catch (ClassNotFoundException classNotFoundException1) {} 
      throw classNotFoundException;
    } 
  }
  
  public static Class<?> getClass(String paramString) throws ClassNotFoundException {
    return getClass(paramString, true);
  }
  
  public static Class<?> getClass(String paramString, boolean paramBoolean) throws ClassNotFoundException {
    ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();
    ClassLoader classLoader2 = classLoader1;
    if (classLoader1 == null)
      classLoader2 = ClassUtils.class.getClassLoader(); 
    return getClass(classLoader2, paramString, paramBoolean);
  }
  
  public static String getPackageCanonicalName(Class<?> paramClass) {
    return (paramClass == null) ? "" : getPackageCanonicalName(paramClass.getName());
  }
  
  public static String getPackageCanonicalName(Object paramObject, String paramString) {
    return (paramObject == null) ? paramString : getPackageCanonicalName(paramObject.getClass().getName());
  }
  
  public static String getPackageCanonicalName(String paramString) {
    return getPackageName(getCanonicalName(paramString));
  }
  
  public static String getPackageName(Class<?> paramClass) {
    return (paramClass == null) ? "" : getPackageName(paramClass.getName());
  }
  
  public static String getPackageName(Object paramObject, String paramString) {
    return (paramObject == null) ? paramString : getPackageName(paramObject.getClass());
  }
  
  public static String getPackageName(String paramString) {
    if (paramString != null) {
      String str = paramString;
      if (paramString.length() != 0) {
        while (str.charAt(0) == '[')
          str = str.substring(1); 
        paramString = str;
        if (str.charAt(0) == 'L') {
          paramString = str;
          if (str.charAt(str.length() - 1) == ';')
            paramString = str.substring(1); 
        } 
        int i = paramString.lastIndexOf('.');
        return (i == -1) ? "" : paramString.substring(0, i);
      } 
    } 
    return "";
  }
  
  public static Method getPublicMethod(Class<?> paramClass, String paramString, Class<?>... paramVarArgs) throws SecurityException, NoSuchMethodException {
    Method method = paramClass.getMethod(paramString, paramVarArgs);
    if (Modifier.isPublic(method.getDeclaringClass().getModifiers()))
      return method; 
    ArrayList<Class<?>> arrayList = new ArrayList();
    arrayList.addAll(getAllInterfaces(paramClass));
    arrayList.addAll(getAllSuperclasses(paramClass));
    for (Class<?> clazz : arrayList) {
      if (!Modifier.isPublic(clazz.getModifiers()))
        continue; 
      try {
        Method method1 = clazz.getMethod(paramString, paramVarArgs);
        if (Modifier.isPublic(method1.getDeclaringClass().getModifiers()))
          return method1; 
      } catch (NoSuchMethodException noSuchMethodException) {}
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Can't find a public method for ");
    stringBuilder.append(paramString);
    stringBuilder.append(" ");
    stringBuilder.append(ArrayUtils.toString(paramVarArgs));
    throw new NoSuchMethodException(stringBuilder.toString());
  }
  
  public static String getShortCanonicalName(Class<?> paramClass) {
    return (paramClass == null) ? "" : getShortCanonicalName(paramClass.getName());
  }
  
  public static String getShortCanonicalName(Object paramObject, String paramString) {
    return (paramObject == null) ? paramString : getShortCanonicalName(paramObject.getClass().getName());
  }
  
  public static String getShortCanonicalName(String paramString) {
    return getShortClassName(getCanonicalName(paramString));
  }
  
  public static String getShortClassName(Class<?> paramClass) {
    return (paramClass == null) ? "" : getShortClassName(paramClass.getName());
  }
  
  public static String getShortClassName(Object paramObject, String paramString) {
    return (paramObject == null) ? paramString : getShortClassName(paramObject.getClass());
  }
  
  public static String getShortClassName(String paramString) {
    if (paramString == null)
      return ""; 
    if (paramString.length() == 0)
      return ""; 
    StringBuilder stringBuilder1 = new StringBuilder();
    boolean bool = paramString.startsWith("[");
    int i = 0;
    String str = paramString;
    if (bool) {
      while (paramString.charAt(0) == '[') {
        paramString = paramString.substring(1);
        stringBuilder1.append("[]");
      } 
      str = paramString;
      if (paramString.charAt(0) == 'L') {
        str = paramString;
        if (paramString.charAt(paramString.length() - 1) == ';')
          str = paramString.substring(1, paramString.length() - 1); 
      } 
    } 
    paramString = str;
    if (reverseAbbreviationMap.containsKey(str))
      paramString = reverseAbbreviationMap.get(str); 
    int j = paramString.lastIndexOf('.');
    if (j != -1)
      i = j + 1; 
    i = paramString.indexOf('$', i);
    str = paramString.substring(j + 1);
    paramString = str;
    if (i != -1)
      paramString = str.replace('$', '.'); 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString);
    stringBuilder2.append(stringBuilder1);
    return stringBuilder2.toString();
  }
  
  public static String getSimpleName(Class<?> paramClass) {
    return (paramClass == null) ? "" : paramClass.getSimpleName();
  }
  
  public static String getSimpleName(Object paramObject, String paramString) {
    return (paramObject == null) ? paramString : getSimpleName(paramObject.getClass());
  }
  
  public static boolean isAssignable(Class<?> paramClass1, Class<?> paramClass2) {
    return isAssignable(paramClass1, paramClass2, SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_5));
  }
  
  public static boolean isAssignable(Class<?> paramClass1, Class<?> paramClass2, boolean paramBoolean) {
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    boolean bool4 = false;
    boolean bool5 = false;
    if (paramClass2 == null)
      return false; 
    if (paramClass1 == null)
      return paramClass2.isPrimitive() ^ true; 
    Class<?> clazz = paramClass1;
    if (paramBoolean) {
      Class<?> clazz1 = paramClass1;
      if (paramClass1.isPrimitive()) {
        clazz1 = paramClass1;
        if (!paramClass2.isPrimitive()) {
          paramClass1 = primitiveToWrapper(paramClass1);
          clazz1 = paramClass1;
          if (paramClass1 == null)
            return false; 
        } 
      } 
      clazz = clazz1;
      if (paramClass2.isPrimitive()) {
        clazz = clazz1;
        if (!clazz1.isPrimitive()) {
          paramClass1 = wrapperToPrimitive(clazz1);
          clazz = paramClass1;
          if (paramClass1 == null)
            return false; 
        } 
      } 
    } 
    if (clazz.equals(paramClass2))
      return true; 
    if (clazz.isPrimitive()) {
      if (!paramClass2.isPrimitive())
        return false; 
      if (int.class.equals(clazz)) {
        if (!long.class.equals(paramClass2) && !float.class.equals(paramClass2)) {
          paramBoolean = bool5;
          return double.class.equals(paramClass2) ? true : paramBoolean;
        } 
      } else {
        if (long.class.equals(clazz)) {
          if (!float.class.equals(paramClass2)) {
            paramBoolean = bool1;
            if (double.class.equals(paramClass2))
              paramBoolean = true; 
            return paramBoolean;
          } 
        } else {
          if (boolean.class.equals(clazz))
            return false; 
          if (double.class.equals(clazz))
            return false; 
          if (float.class.equals(clazz))
            return double.class.equals(paramClass2); 
          if (char.class.equals(clazz)) {
            if (!int.class.equals(paramClass2) && !long.class.equals(paramClass2) && !float.class.equals(paramClass2)) {
              paramBoolean = bool2;
              if (double.class.equals(paramClass2))
                paramBoolean = true; 
              return paramBoolean;
            } 
          } else {
            if (short.class.equals(clazz)) {
              if (!int.class.equals(paramClass2) && !long.class.equals(paramClass2) && !float.class.equals(paramClass2)) {
                paramBoolean = bool3;
                if (double.class.equals(paramClass2))
                  paramBoolean = true; 
                return paramBoolean;
              } 
            } else {
              paramBoolean = bool4;
              if (byte.class.equals(clazz)) {
                if (!short.class.equals(paramClass2) && !int.class.equals(paramClass2) && !long.class.equals(paramClass2) && !float.class.equals(paramClass2)) {
                  paramBoolean = bool4;
                  if (double.class.equals(paramClass2))
                    paramBoolean = true; 
                  return paramBoolean;
                } 
              } else {
                return paramBoolean;
              } 
              paramBoolean = true;
            } 
            paramBoolean = true;
          } 
          paramBoolean = true;
        } 
        paramBoolean = true;
      } 
    } else {
      return paramClass2.isAssignableFrom(clazz);
    } 
    return true;
  }
  
  public static boolean isAssignable(Class<?>[] paramArrayOfClass1, Class<?>... paramVarArgs1) {
    return isAssignable(paramArrayOfClass1, paramVarArgs1, SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_5));
  }
  
  public static boolean isAssignable(Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2, boolean paramBoolean) {
    if (!ArrayUtils.isSameLength((Object[])paramArrayOfClass1, (Object[])paramArrayOfClass2))
      return false; 
    Class<?>[] arrayOfClass = paramArrayOfClass1;
    if (paramArrayOfClass1 == null)
      arrayOfClass = ArrayUtils.EMPTY_CLASS_ARRAY; 
    paramArrayOfClass1 = paramArrayOfClass2;
    if (paramArrayOfClass2 == null)
      paramArrayOfClass1 = ArrayUtils.EMPTY_CLASS_ARRAY; 
    for (byte b = 0; b < arrayOfClass.length; b++) {
      if (!isAssignable(arrayOfClass[b], paramArrayOfClass1[b], paramBoolean))
        return false; 
    } 
    return true;
  }
  
  public static boolean isInnerClass(Class<?> paramClass) {
    boolean bool;
    if (paramClass != null && paramClass.getEnclosingClass() != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isPrimitiveOrWrapper(Class<?> paramClass) {
    boolean bool = false;
    if (paramClass == null)
      return false; 
    if (paramClass.isPrimitive() || isPrimitiveWrapper(paramClass))
      bool = true; 
    return bool;
  }
  
  public static boolean isPrimitiveWrapper(Class<?> paramClass) {
    return wrapperPrimitiveMap.containsKey(paramClass);
  }
  
  public static Class<?> primitiveToWrapper(Class<?> paramClass) {
    Class<?> clazz = paramClass;
    if (paramClass != null) {
      clazz = paramClass;
      if (paramClass.isPrimitive())
        clazz = primitiveWrapperMap.get(paramClass); 
    } 
    return clazz;
  }
  
  public static Class<?>[] primitivesToWrappers(Class<?>... paramVarArgs) {
    if (paramVarArgs == null)
      return null; 
    if (paramVarArgs.length == 0)
      return paramVarArgs; 
    Class[] arrayOfClass = new Class[paramVarArgs.length];
    for (byte b = 0; b < paramVarArgs.length; b++)
      arrayOfClass[b] = primitiveToWrapper(paramVarArgs[b]); 
    return arrayOfClass;
  }
  
  private static String toCanonicalName(String paramString) {
    paramString = StringUtils.deleteWhitespace(paramString);
    if (paramString != null) {
      String str = paramString;
      if (paramString.endsWith("[]")) {
        StringBuilder stringBuilder = new StringBuilder();
        while (paramString.endsWith("[]")) {
          paramString = paramString.substring(0, paramString.length() - 2);
          stringBuilder.append("[");
        } 
        String str1 = abbreviationMap.get(paramString);
        if (str1 != null) {
          stringBuilder.append(str1);
        } else {
          stringBuilder.append("L");
          stringBuilder.append(paramString);
          stringBuilder.append(";");
        } 
        str = stringBuilder.toString();
      } 
      return str;
    } 
    throw new NullPointerException("className must not be null.");
  }
  
  public static Class<?>[] toClass(Object... paramVarArgs) {
    if (paramVarArgs == null)
      return null; 
    if (paramVarArgs.length == 0)
      return ArrayUtils.EMPTY_CLASS_ARRAY; 
    Class[] arrayOfClass = new Class[paramVarArgs.length];
    for (byte b = 0; b < paramVarArgs.length; b++) {
      Class<?> clazz;
      if (paramVarArgs[b] == null) {
        clazz = null;
      } else {
        clazz = paramVarArgs[b].getClass();
      } 
      arrayOfClass[b] = clazz;
    } 
    return arrayOfClass;
  }
  
  public static Class<?> wrapperToPrimitive(Class<?> paramClass) {
    return wrapperPrimitiveMap.get(paramClass);
  }
  
  public static Class<?>[] wrappersToPrimitives(Class<?>... paramVarArgs) {
    if (paramVarArgs == null)
      return null; 
    if (paramVarArgs.length == 0)
      return paramVarArgs; 
    Class[] arrayOfClass = new Class[paramVarArgs.length];
    for (byte b = 0; b < paramVarArgs.length; b++)
      arrayOfClass[b] = wrapperToPrimitive(paramVarArgs[b]); 
    return arrayOfClass;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\ClassUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */