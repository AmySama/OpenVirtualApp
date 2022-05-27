package mirror;

import java.lang.reflect.Field;

public class RefDouble {
  private Field field;
  
  public RefDouble(Class paramClass, Field paramField) throws NoSuchFieldException {
    Field field = paramClass.getDeclaredField(paramField.getName());
    this.field = field;
    field.setAccessible(true);
  }
  
  public double get(Object paramObject) {
    try {
      return this.field.getDouble(paramObject);
    } catch (Exception exception) {
      return 0.0D;
    } 
  }
  
  public void set(Object paramObject, double paramDouble) {
    try {
      this.field.setDouble(paramObject, paramDouble);
    } catch (Exception exception) {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\RefDouble.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */