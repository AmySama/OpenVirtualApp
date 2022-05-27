package mirror;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RefMethod<T> {
  private Method method;
  
  public RefMethod(Class<?> paramClass, Field paramField) throws NoSuchMethodException {
    Method method;
    boolean bool = paramField.isAnnotationPresent((Class)MethodParams.class);
    byte b = 0;
    int i = 0;
    int j = 0;
    if (bool) {
      Class[] arrayOfClass = ((MethodParams)paramField.<MethodParams>getAnnotation(MethodParams.class)).value();
      for (i = j; i < arrayOfClass.length; i++) {
        Class clazz = arrayOfClass[i];
        if (clazz.getClassLoader() == getClass().getClassLoader())
          try {
            Class.forName(clazz.getName());
            arrayOfClass[i] = (Class)clazz.getField("TYPE").get((Object)null);
          } finally {
            paramClass = null;
          }  
      } 
      method = paramClass.getDeclaredMethod(paramField.getName(), arrayOfClass);
      this.method = method;
      method.setAccessible(true);
    } else if (paramField.isAnnotationPresent((Class)MethodReflectParams.class)) {
      String[] arrayOfString = ((MethodReflectParams)paramField.<MethodReflectParams>getAnnotation(MethodReflectParams.class)).value();
      Class[] arrayOfClass = new Class[arrayOfString.length];
      for (i = b; i < arrayOfString.length; i++) {
        Class<?> clazz2 = RefStaticMethod.getProtoType(arrayOfString[i]);
        Class<?> clazz1 = clazz2;
        if (clazz2 == null)
          try {
            clazz1 = Class.forName(arrayOfString[i]);
          } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
            clazz1 = clazz2;
          }  
        arrayOfClass[i] = clazz1;
      } 
      method = method.getDeclaredMethod(paramField.getName(), arrayOfClass);
      this.method = method;
      method.setAccessible(true);
    } else {
      Method[] arrayOfMethod = method.getDeclaredMethods();
      j = arrayOfMethod.length;
      while (i < j) {
        Method method1 = arrayOfMethod[i];
        if (method1.getName().equals(paramField.getName())) {
          this.method = method1;
          method1.setAccessible(true);
          break;
        } 
        i++;
      } 
    } 
    if (this.method != null)
      return; 
    throw new NoSuchMethodException(paramField.getName());
  }
  
  public T call(Object paramObject, Object... paramVarArgs) {
    try {
      return (T)this.method.invoke(paramObject, paramVarArgs);
    } catch (InvocationTargetException invocationTargetException) {
    
    } finally {
      paramObject = null;
    } 
    return null;
  }
  
  public T callWithException(Object paramObject, Object... paramVarArgs) throws Throwable {
    try {
      return (T)this.method.invoke(paramObject, paramVarArgs);
    } catch (InvocationTargetException invocationTargetException) {
      if (invocationTargetException.getCause() != null)
        throw invocationTargetException.getCause(); 
      throw invocationTargetException;
    } 
  }
  
  public Class<?>[] paramList() {
    return this.method.getParameterTypes();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\RefMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */