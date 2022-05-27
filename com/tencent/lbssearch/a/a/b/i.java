package com.tencent.lbssearch.a.a.b;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class i {
  private static final Map<Class<?>, Class<?>> a;
  
  private static final Map<Class<?>, Class<?>> b;
  
  static {
    HashMap<Object, Object> hashMap1 = new HashMap<Object, Object>(16);
    HashMap<Object, Object> hashMap2 = new HashMap<Object, Object>(16);
    a((Map)hashMap1, (Map)hashMap2, boolean.class, Boolean.class);
    a((Map)hashMap1, (Map)hashMap2, byte.class, Byte.class);
    a((Map)hashMap1, (Map)hashMap2, char.class, Character.class);
    a((Map)hashMap1, (Map)hashMap2, double.class, Double.class);
    a((Map)hashMap1, (Map)hashMap2, float.class, Float.class);
    a((Map)hashMap1, (Map)hashMap2, int.class, Integer.class);
    a((Map)hashMap1, (Map)hashMap2, long.class, Long.class);
    a((Map)hashMap1, (Map)hashMap2, short.class, Short.class);
    a((Map)hashMap1, (Map)hashMap2, void.class, Void.class);
    a = Collections.unmodifiableMap(hashMap1);
    b = Collections.unmodifiableMap(hashMap2);
  }
  
  public static <T> Class<T> a(Class<T> paramClass) {
    Class<T> clazz = (Class)a.get(a.a(paramClass));
    if (clazz != null)
      paramClass = clazz; 
    return paramClass;
  }
  
  private static void a(Map<Class<?>, Class<?>> paramMap1, Map<Class<?>, Class<?>> paramMap2, Class<?> paramClass1, Class<?> paramClass2) {
    paramMap1.put(paramClass1, paramClass2);
    paramMap2.put(paramClass2, paramClass1);
  }
  
  public static boolean a(Type paramType) {
    return a.containsKey(paramType);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */