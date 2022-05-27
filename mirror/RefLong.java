package mirror;

import java.lang.reflect.Field;

public class RefLong {
  private Field field;
  
  public RefLong(Class paramClass, Field paramField) throws NoSuchFieldException {
    Field field = paramClass.getDeclaredField(paramField.getName());
    this.field = field;
    field.setAccessible(true);
  }
  
  public long get(Object paramObject) {
    try {
      return this.field.getLong(paramObject);
    } catch (Exception exception) {
      return 0L;
    } 
  }
  
  public void set(Object paramObject, long paramLong) {
    try {
      this.field.setLong(paramObject, paramLong);
    } catch (Exception exception) {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\RefLong.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */