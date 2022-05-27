package mirror;

import java.lang.reflect.Field;

public class RefFloat {
  private Field field;
  
  public RefFloat(Class paramClass, Field paramField) throws NoSuchFieldException {
    Field field = paramClass.getDeclaredField(paramField.getName());
    this.field = field;
    field.setAccessible(true);
  }
  
  public float get(Object paramObject) {
    try {
      return this.field.getFloat(paramObject);
    } catch (Exception exception) {
      return 0.0F;
    } 
  }
  
  public void set(Object paramObject, float paramFloat) {
    try {
      this.field.setFloat(paramObject, paramFloat);
    } catch (Exception exception) {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\RefFloat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */