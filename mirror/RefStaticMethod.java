package mirror;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RefStaticMethod<T> {
  private Method method;
  
  private String name;
  
  private String parent;
  
  public RefStaticMethod(Class<?> paramClass, Field paramField) throws NoSuchMethodException {
    Method method;
    this.name = paramField.getName();
    this.parent = paramClass.getName();
    boolean bool = paramField.isAnnotationPresent((Class)MethodParams.class);
    int i = 0;
    boolean bool1 = false;
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
      Class[] arrayOfClass1 = new Class[arrayOfString.length];
      Class[] arrayOfClass2 = new Class[arrayOfString.length];
      bool1 = false;
      while (i < arrayOfString.length) {
        Class<?> clazz1 = getProtoType(arrayOfString[i]);
        Class<?> clazz2 = clazz1;
        if (clazz1 == null)
          try {
            clazz2 = Class.forName(arrayOfString[i]);
          } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
            clazz2 = clazz1;
          }  
        arrayOfClass1[i] = clazz2;
        if ("java.util.HashSet".equals(arrayOfString[i])) {
          Class<?> clazz;
          try {
            clazz1 = Class.forName("android.util.ArraySet");
          } catch (ClassNotFoundException classNotFoundException) {
            clazz = clazz2;
          } 
          if (clazz != null) {
            arrayOfClass2[i] = clazz;
          } else {
            arrayOfClass2[i] = clazz2;
          } 
          bool1 = true;
        } else {
          arrayOfClass2[i] = clazz2;
        } 
        i++;
      } 
      try {
        this.method = method.getDeclaredMethod(paramField.getName(), arrayOfClass1);
      } catch (Exception exception) {
        exception.printStackTrace();
        if (bool1)
          this.method = method.getDeclaredMethod(paramField.getName(), arrayOfClass2); 
      } 
      this.method.setAccessible(true);
    } else {
      Method[] arrayOfMethod = method.getDeclaredMethods();
      j = arrayOfMethod.length;
      for (i = bool1; i < j; i++) {
        Method method1 = arrayOfMethod[i];
        if (method1.getName().equals(paramField.getName())) {
          this.method = method1;
          method1.setAccessible(true);
          break;
        } 
      } 
    } 
    if (this.method != null)
      return; 
    throw new NoSuchMethodException(paramField.getName());
  }
  
  static Class<?> getProtoType(String paramString) {
    return (Class<?>)(paramString.equals("int") ? int.class : (paramString.equals("long") ? long.class : (paramString.equals("boolean") ? boolean.class : (paramString.equals("byte") ? byte.class : (paramString.equals("short") ? short.class : (paramString.equals("char") ? char.class : (paramString.equals("float") ? float.class : (paramString.equals("double") ? double.class : (paramString.equals("void") ? void.class : null)))))))));
  }
  
  public T call(Object... paramVarArgs) {
    Exception exception = null;
    try {
      object = this.method.invoke(null, paramVarArgs);
    } catch (Exception object) {
      object.printStackTrace();
      object = exception;
    } 
    return (T)object;
  }
  
  public T callWithException(Object... paramVarArgs) throws Throwable {
    try {
      return (T)this.method.invoke(null, paramVarArgs);
    } catch (InvocationTargetException invocationTargetException) {
      if (invocationTargetException.getCause() != null)
        throw invocationTargetException.getCause(); 
      throw invocationTargetException;
    } 
  }
  
  public String toString() {
    boolean bool;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("RefStaticMethod{");
    stringBuilder.append(this.parent);
    stringBuilder.append("@");
    stringBuilder.append(this.name);
    stringBuilder.append(" find=");
    if (this.method != null) {
      bool = true;
    } else {
      bool = false;
    } 
    stringBuilder.append(bool);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\RefStaticMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */