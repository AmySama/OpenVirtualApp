package com.lody.virtual.helper.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Reflect {
  private final boolean isClass;
  
  private final Object object;
  
  private Reflect(Class<?> paramClass) {
    this.object = paramClass;
    this.isClass = true;
  }
  
  private Reflect(Object paramObject) {
    this.object = paramObject;
    this.isClass = false;
  }
  
  public static <T extends java.lang.reflect.AccessibleObject> T accessible(T paramT) {
    if (paramT == null)
      return null; 
    if (paramT instanceof Member) {
      Member member = (Member)paramT;
      if (Modifier.isPublic(member.getModifiers()) && Modifier.isPublic(member.getDeclaringClass().getModifiers()))
        return paramT; 
    } 
    if (!paramT.isAccessible())
      paramT.setAccessible(true); 
    return paramT;
  }
  
  public static Object defaultValue(Class<?> paramClass) {
    paramClass = wrapper(paramClass);
    if (paramClass == null)
      return null; 
    if (paramClass.isPrimitive()) {
      if (Boolean.class == paramClass)
        return Boolean.valueOf(false); 
      if (Number.class.isAssignableFrom(paramClass))
        return Integer.valueOf(0); 
      if (Character.class == paramClass)
        return Character.valueOf(false); 
      if (Void.class == paramClass);
    } 
    return null;
  }
  
  private Field field0(String paramString) throws ReflectException {
    Class<?> clazz = type();
    try {
      return clazz.getField(paramString);
    } catch (NoSuchFieldException noSuchFieldException) {
      while (true) {
        try {
          return accessible(clazz.getDeclaredField(paramString));
        } catch (NoSuchFieldException noSuchFieldException1) {
          clazz = clazz.getSuperclass();
          if (clazz != null)
            continue; 
          throw new ReflectException(noSuchFieldException);
        } 
      } 
    } 
  }
  
  private static Class<?> forName(String paramString) throws ReflectException {
    try {
      return Class.forName(paramString);
    } catch (Exception exception) {
      throw new ReflectException(exception);
    } 
  }
  
  private static Class<?> forName(String paramString, ClassLoader paramClassLoader) throws ReflectException {
    try {
      return Class.forName(paramString, true, paramClassLoader);
    } catch (Exception exception) {
      throw new ReflectException(exception);
    } 
  }
  
  public static String getMethodDetails(Method paramMethod) {
    StringBuilder stringBuilder = new StringBuilder(40);
    stringBuilder.append(Modifier.toString(paramMethod.getModifiers()));
    stringBuilder.append(" ");
    stringBuilder.append(paramMethod.getReturnType().getName());
    stringBuilder.append(" ");
    stringBuilder.append(paramMethod.getName());
    stringBuilder.append("(");
    Class[] arrayOfClass = paramMethod.getParameterTypes();
    int i = arrayOfClass.length;
    for (byte b = 0; b < i; b++) {
      stringBuilder.append(arrayOfClass[b].getName());
      stringBuilder.append(", ");
    } 
    if (arrayOfClass.length > 0)
      stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()); 
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  private boolean isSimilarSignature(Method paramMethod, String paramString, Class<?>[] paramArrayOfClass) {
    boolean bool;
    if (paramMethod.getName().equals(paramString) && match(paramMethod.getParameterTypes(), paramArrayOfClass)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private boolean match(Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2) {
    if (paramArrayOfClass1.length == paramArrayOfClass2.length) {
      byte b = 0;
      while (b < paramArrayOfClass2.length) {
        if (paramArrayOfClass2[b] == NULL.class || wrapper(paramArrayOfClass1[b]).isAssignableFrom(wrapper(paramArrayOfClass2[b]))) {
          b++;
          continue;
        } 
        return false;
      } 
      return true;
    } 
    return false;
  }
  
  private boolean matchObject(Class<?>[] paramArrayOfClass) {
    int i = paramArrayOfClass.length;
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (i > 0) {
      bool2 = bool1;
      if (paramArrayOfClass[0].isAssignableFrom(Object[].class))
        bool2 = true; 
    } 
    return bool2;
  }
  
  private boolean matchObjectMethod(Method paramMethod, String paramString, Class<?>[] paramArrayOfClass) {
    boolean bool;
    if (paramMethod.getName().equals(paramString) && matchObject(paramMethod.getParameterTypes())) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static Reflect on(Class<?> paramClass) {
    return new Reflect(paramClass);
  }
  
  public static Reflect on(Object paramObject) {
    return new Reflect(paramObject);
  }
  
  public static Reflect on(String paramString) throws ReflectException {
    return on(forName(paramString));
  }
  
  public static Reflect on(String paramString, ClassLoader paramClassLoader) throws ReflectException {
    return on(forName(paramString, paramClassLoader));
  }
  
  private static Reflect on(Constructor<?> paramConstructor, Object... paramVarArgs) throws ReflectException {
    try {
      return on(((Constructor)accessible(paramConstructor)).newInstance(paramVarArgs));
    } catch (Exception exception) {
      throw new ReflectException(exception);
    } 
  }
  
  private static Reflect on(Method paramMethod, Object paramObject, Object... paramVarArgs) throws ReflectException {
    try {
      accessible(paramMethod);
      if (paramMethod.getReturnType() == void.class) {
        paramMethod.invoke(paramObject, paramVarArgs);
        return on(paramObject);
      } 
      return on(paramMethod.invoke(paramObject, paramVarArgs));
    } catch (Exception exception) {
      throw new ReflectException(exception);
    } 
  }
  
  private static String property(String paramString) {
    int i = paramString.length();
    if (i == 0)
      return ""; 
    if (i == 1)
      return paramString.toLowerCase(); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString.substring(0, 1).toLowerCase());
    stringBuilder.append(paramString.substring(1));
    return stringBuilder.toString();
  }
  
  private Method similarMethod(String paramString, Class<?>[] paramArrayOfClass) throws NoSuchMethodException {
    Method method;
    Class<?> clazz = type();
    Method[] arrayOfMethod = clazz.getMethods();
    int i = arrayOfMethod.length;
    byte b = 0;
    while (true) {
      Class<?> clazz1 = clazz;
      if (b < i) {
        method = arrayOfMethod[b];
        if (isSimilarSignature(method, paramString, paramArrayOfClass))
          return method; 
        b++;
        continue;
      } 
      break;
    } 
    while (true) {
      for (Method method1 : method.getDeclaredMethods()) {
        if (isSimilarSignature(method1, paramString, paramArrayOfClass))
          return method1; 
      } 
      Class clazz1 = method.getSuperclass();
      if (clazz1 != null)
        continue; 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("No similar method ");
      stringBuilder.append(paramString);
      stringBuilder.append(" with params ");
      stringBuilder.append(Arrays.toString((Object[])paramArrayOfClass));
      stringBuilder.append(" could be found on type ");
      stringBuilder.append(type());
      stringBuilder.append(".");
      throw new NoSuchMethodException(stringBuilder.toString());
    } 
  }
  
  private static Class<?>[] types(Object... paramVarArgs) {
    byte b = 0;
    if (paramVarArgs == null)
      return new Class[0]; 
    Class[] arrayOfClass = new Class[paramVarArgs.length];
    while (b < paramVarArgs.length) {
      Object<NULL> object = (Object<NULL>)paramVarArgs[b];
      if (object == null) {
        object = (Object<NULL>)NULL.class;
      } else {
        object = (Object)object.getClass();
      } 
      arrayOfClass[b] = (Class)object;
      b++;
    } 
    return arrayOfClass;
  }
  
  private static Object unwrap(Object paramObject) {
    Object object = paramObject;
    if (paramObject instanceof Reflect)
      object = ((Reflect)paramObject).get(); 
    return object;
  }
  
  public static Class<?> wrapper(Class<?> paramClass) {
    if (paramClass == null)
      return null; 
    Class<?> clazz = paramClass;
    if (paramClass.isPrimitive()) {
      if (boolean.class == paramClass)
        return Boolean.class; 
      if (int.class == paramClass)
        return Integer.class; 
      if (long.class == paramClass)
        return Long.class; 
      if (short.class == paramClass)
        return Short.class; 
      if (byte.class == paramClass)
        return Byte.class; 
      if (double.class == paramClass)
        return Double.class; 
      if (float.class == paramClass)
        return Float.class; 
      if (char.class == paramClass)
        return Character.class; 
      clazz = paramClass;
      if (void.class == paramClass)
        clazz = Void.class; 
    } 
    return clazz;
  }
  
  public <P> P as(Class<P> paramClass) {
    InvocationHandler invocationHandler = new InvocationHandler() {
        public Object invoke(Object param1Object, Method param1Method, Object[] param1ArrayOfObject) throws Throwable {
          param1Object = param1Method.getName();
          try {
            return Reflect.on(Reflect.this.object).call((String)param1Object, param1ArrayOfObject).get();
          } catch (ReflectException reflectException) {
            if (isMap) {
              int i;
              Map<String, Object> map = (Map)Reflect.this.object;
              if (param1ArrayOfObject == null) {
                i = 0;
              } else {
                i = param1ArrayOfObject.length;
              } 
              if (i == 0 && param1Object.startsWith("get"))
                return map.get(Reflect.property(param1Object.substring(3))); 
              if (i == 0 && param1Object.startsWith("is"))
                return map.get(Reflect.property(param1Object.substring(2))); 
              if (i == 1 && param1Object.startsWith("set")) {
                map.put(Reflect.property(param1Object.substring(3)), param1ArrayOfObject[0]);
                return null;
              } 
            } 
            throw reflectException;
          } 
        }
      };
    return (P)Proxy.newProxyInstance(paramClass.getClassLoader(), new Class[] { paramClass }, invocationHandler);
  }
  
  public Reflect call(String paramString) throws ReflectException {
    return call(paramString, new Object[0]);
  }
  
  public Reflect call(String paramString, Object... paramVarArgs) throws ReflectException {
    Class[] arrayOfClass = types(paramVarArgs);
    try {
      return on(exactMethod(paramString, arrayOfClass), this.object, paramVarArgs);
    } catch (NoSuchMethodException noSuchMethodException) {
      try {
        return on(similarMethod(paramString, arrayOfClass), this.object, paramVarArgs);
      } catch (NoSuchMethodException noSuchMethodException1) {
        throw new ReflectException(noSuchMethodException1);
      } 
    } 
  }
  
  public Reflect callBest(String paramString, Object... paramVarArgs) throws ReflectException {
    Object[] arrayOfObject;
    Method method2;
    byte b3;
    Class[] arrayOfClass = types(paramVarArgs);
    Method[] arrayOfMethod = type().getDeclaredMethods();
    int i = arrayOfMethod.length;
    Method method1 = null;
    byte b1 = 0;
    byte b2 = 0;
    while (true) {
      method2 = method1;
      b3 = b2;
      if (b1 < i) {
        Method method;
        method2 = arrayOfMethod[b1];
        if (isSimilarSignature(method2, paramString, arrayOfClass)) {
          b3 = 2;
          break;
        } 
        if (matchObjectMethod(method2, paramString, arrayOfClass)) {
          b3 = 1;
          method = method2;
        } else {
          method = method1;
          b3 = b2;
          if (method2.getName().equals(paramString)) {
            method = method1;
            b3 = b2;
            if ((method2.getParameterTypes()).length == 0) {
              method = method1;
              b3 = b2;
              if (b2 == 0) {
                b3 = b2;
                method = method2;
              } 
            } 
          } 
        } 
        b1++;
        method1 = method;
        b2 = b3;
        continue;
      } 
      break;
    } 
    if (method2 != null) {
      if (b3 == 0)
        paramVarArgs = new Object[0]; 
      arrayOfObject = paramVarArgs;
      if (b3 == 1)
        arrayOfObject = new Object[] { paramVarArgs }; 
      return on(method2, this.object, arrayOfObject);
    } 
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("no method found for ");
    stringBuilder1.append((String)arrayOfObject);
    String str = stringBuilder1.toString();
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("No best method ");
    stringBuilder2.append((String)arrayOfObject);
    stringBuilder2.append(" with params ");
    stringBuilder2.append(Arrays.toString((Object[])arrayOfClass));
    stringBuilder2.append(" could be found on type ");
    stringBuilder2.append(type());
    stringBuilder2.append(".");
    throw new ReflectException(str, new NoSuchMethodException(stringBuilder2.toString()));
  }
  
  public Reflect create() throws ReflectException {
    return create(new Object[0]);
  }
  
  public Reflect create(Object... paramVarArgs) throws ReflectException {
    Class[] arrayOfClass = types(paramVarArgs);
    try {
      return on(type().getDeclaredConstructor(arrayOfClass), paramVarArgs);
    } catch (NoSuchMethodException noSuchMethodException) {
      for (Constructor<?> constructor : type().getDeclaredConstructors()) {
        if (match(constructor.getParameterTypes(), arrayOfClass))
          return on(constructor, paramVarArgs); 
      } 
      throw new ReflectException(noSuchMethodException);
    } 
  }
  
  public boolean equals(Object paramObject) {
    boolean bool;
    if (paramObject instanceof Reflect && this.object.equals(((Reflect)paramObject).get())) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public Method exactMethod(String paramString, Class<?>[] paramArrayOfClass) throws NoSuchMethodException {
    Class<?> clazz = type();
    try {
      return clazz.getMethod(paramString, paramArrayOfClass);
    } catch (NoSuchMethodException noSuchMethodException) {
      while (true) {
        try {
          return clazz.getDeclaredMethod(paramString, paramArrayOfClass);
        } catch (NoSuchMethodException noSuchMethodException1) {
          clazz = clazz.getSuperclass();
          if (clazz != null)
            continue; 
          throw new NoSuchMethodException();
        } 
      } 
    } 
  }
  
  public Reflect field(String paramString) throws ReflectException {
    try {
      return on(field0(paramString).get(this.object));
    } catch (Exception exception) {
      throw new ReflectException(this.object.getClass().getName(), exception);
    } 
  }
  
  public Map<String, Reflect> fields() {
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
    Class<?> clazz = type();
    while (true) {
      for (Field field : clazz.getDeclaredFields()) {
        if ((this.isClass ^ true ^ Modifier.isStatic(field.getModifiers())) != 0) {
          String str = field.getName();
          if (!linkedHashMap.containsKey(str))
            linkedHashMap.put(str, field(str)); 
        } 
      } 
      Class<?> clazz1 = clazz.getSuperclass();
      clazz = clazz1;
      if (clazz1 == null)
        return (Map)linkedHashMap; 
    } 
  }
  
  public <T> T get() {
    return (T)this.object;
  }
  
  public <T> T get(String paramString) throws ReflectException {
    return field(paramString).get();
  }
  
  public int hashCode() {
    return this.object.hashCode();
  }
  
  public <T> T opt(String paramString) {
    try {
      return (T)field(paramString).get();
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
  
  public void printFields() {
    Object object;
    if (this.object == null)
      return; 
    Map<String, Reflect> map = fields();
    if (map == null)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    for (Map.Entry<String, Reflect> entry : map.entrySet()) {
      String str = (String)entry.getKey();
      object = ((Reflect)entry.getValue()).object;
      if (object == null) {
        object = "null";
      } else {
        object = object.toString();
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str);
      stringBuilder1.append(" = ");
      stringBuilder1.append((String)object);
      stringBuilder.append(stringBuilder1.toString());
      stringBuilder.append('\n');
    } 
    if (this.isClass) {
      object = this.object;
    } else {
      object = this.object.getClass();
    } 
    VLog.e(object.getSimpleName(), stringBuilder.toString());
  }
  
  public Reflect set(String paramString, Object paramObject) throws ReflectException {
    try {
      Field field = field0(paramString);
      field.setAccessible(true);
      field.set(this.object, unwrap(paramObject));
      return this;
    } catch (Exception exception) {
      throw new ReflectException(exception);
    } 
  }
  
  public String toString() {
    return this.object.toString();
  }
  
  public Class<?> type() {
    return this.isClass ? (Class)this.object : this.object.getClass();
  }
  
  private static class NULL {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\Reflect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */