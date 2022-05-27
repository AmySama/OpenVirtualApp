package mirror;

import java.lang.reflect.Field;

public class RefBoolean {
  private Field field;
  
  public RefBoolean(Class<?> paramClass, Field paramField) throws NoSuchFieldException {
    Field field = paramClass.getDeclaredField(paramField.getName());
    this.field = field;
    field.setAccessible(true);
  }
  
  public boolean get(Object paramObject) {
    try {
      return this.field.getBoolean(paramObject);
    } catch (Exception exception) {
      return false;
    } 
  }
  
  public void set(Object paramObject, boolean paramBoolean) {
    try {
      this.field.setBoolean(paramObject, paramBoolean);
    } catch (Exception exception) {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\RefBoolean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */