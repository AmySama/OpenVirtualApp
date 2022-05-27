package mirror;

import java.lang.reflect.Field;

public class RefObject<T> {
  private Field field;
  
  public RefObject(Class<?> paramClass, Field paramField) throws NoSuchFieldException {
    Field field = paramClass.getDeclaredField(paramField.getName());
    this.field = field;
    field.setAccessible(true);
  }
  
  public T get(Object paramObject) {
    try {
      return (T)this.field.get(paramObject);
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
  
  public boolean set(Object paramObject, T paramT) {
    try {
      this.field.set(paramObject, paramT);
      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
      return false;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\RefObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */