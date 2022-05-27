package mirror;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class RefConstructor<T> {
  private Constructor<?> ctor;
  
  public RefConstructor(Class<?> paramClass, Field paramField) throws NoSuchMethodException {
    if (paramField.isAnnotationPresent((Class)MethodParams.class)) {
      this.ctor = paramClass.getDeclaredConstructor(((MethodParams)paramField.<MethodParams>getAnnotation(MethodParams.class)).value());
    } else {
      boolean bool = paramField.isAnnotationPresent((Class)MethodReflectParams.class);
      byte b = 0;
      if (bool) {
        String[] arrayOfString = ((MethodReflectParams)paramField.<MethodReflectParams>getAnnotation(MethodReflectParams.class)).value();
        Class[] arrayOfClass = new Class[arrayOfString.length];
        while (b < arrayOfString.length) {
          try {
            Class<?> clazz2 = RefStaticMethod.getProtoType(arrayOfString[b]);
            Class<?> clazz1 = clazz2;
            if (clazz2 == null)
              clazz1 = Class.forName(arrayOfString[b]); 
            arrayOfClass[b] = clazz1;
            b++;
          } catch (Exception exception) {
            exception.printStackTrace();
          } 
        } 
        this.ctor = paramClass.getDeclaredConstructor(arrayOfClass);
      } else {
        this.ctor = paramClass.getDeclaredConstructor(new Class[0]);
      } 
    } 
    Constructor<?> constructor = this.ctor;
    if (constructor != null && !constructor.isAccessible())
      this.ctor.setAccessible(true); 
  }
  
  public T newInstance() {
    try {
      return (T)this.ctor.newInstance(new Object[0]);
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public T newInstance(Object... paramVarArgs) {
    try {
      return (T)this.ctor.newInstance(paramVarArgs);
    } catch (Exception exception) {
      return null;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\RefConstructor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */