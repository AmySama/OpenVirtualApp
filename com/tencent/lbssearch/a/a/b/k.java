package com.tencent.lbssearch.a.a.b;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class k {
  public static k a() {
    try {
      Class<?> clazz = Class.forName("sun.misc.Unsafe");
      Field field = clazz.getDeclaredField("theUnsafe");
      field.setAccessible(true);
      Object object = field.get(null);
      return new k(clazz.getMethod("allocateInstance", new Class[] { Class.class }), object) {
          public <T> T a(Class<T> param1Class) throws Exception {
            return (T)this.a.invoke(this.b, new Object[] { param1Class });
          }
        };
    } catch (Exception exception) {
      try {
        Method method = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[] { Class.class, Class.class });
        method.setAccessible(true);
        return new k(method) {
            public <T> T a(Class<T> param1Class) throws Exception {
              return (T)this.a.invoke(null, new Object[] { param1Class, Object.class });
            }
          };
      } catch (Exception exception1) {
        try {
          Method method = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[] { Class.class });
          method.setAccessible(true);
          int i = ((Integer)method.invoke(null, new Object[] { Object.class })).intValue();
          method = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[] { Class.class, int.class });
          method.setAccessible(true);
          return new k(method, i) {
              public <T> T a(Class<T> param1Class) throws Exception {
                return (T)this.a.invoke(null, new Object[] { param1Class, Integer.valueOf(this.b) });
              }
            };
        } catch (Exception exception2) {
          return new k() {
              public <T> T a(Class<T> param1Class) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot allocate ");
                stringBuilder.append(param1Class);
                throw new UnsupportedOperationException(stringBuilder.toString());
              }
            };
        } 
      } 
    } 
  }
  
  public abstract <T> T a(Class<T> paramClass) throws Exception;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */