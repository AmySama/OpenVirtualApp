package mirror;

import java.lang.reflect.Field;

public class RefStaticObject<T> {
  private Field field;
  
  public RefStaticObject(Class<?> paramClass, Field paramField) throws NoSuchFieldException {
    Field field = paramClass.getDeclaredField(paramField.getName());
    this.field = field;
    field.setAccessible(true);
  }
  
  public T get() {
    Object object = null;
    try {
      Object object1 = this.field.get(null);
      object = object1;
    } catch (Exception exception) {}
    return (T)object;
  }
  
  public void set(T paramT) {
    try {
      this.field.set(null, paramT);
    } catch (Exception exception) {}
  }
  
  public Class<?> type() {
    return this.field.getType();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\RefStaticObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */