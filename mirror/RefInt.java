package mirror;

import java.lang.reflect.Field;

public class RefInt {
  private Field field;
  
  public RefInt(Class paramClass, Field paramField) throws NoSuchFieldException {
    Field field = paramClass.getDeclaredField(paramField.getName());
    this.field = field;
    field.setAccessible(true);
  }
  
  public int get(Object paramObject) {
    try {
      return this.field.getInt(paramObject);
    } catch (Exception exception) {
      return 0;
    } 
  }
  
  public void set(Object paramObject, int paramInt) {
    try {
      this.field.setInt(paramObject, paramInt);
    } catch (Exception exception) {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\RefInt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */