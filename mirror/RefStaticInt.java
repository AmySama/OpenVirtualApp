package mirror;

import java.lang.reflect.Field;

public class RefStaticInt {
  private Field field;
  
  public RefStaticInt(Class<?> paramClass, Field paramField) throws NoSuchFieldException {
    Field field = paramClass.getDeclaredField(paramField.getName());
    this.field = field;
    field.setAccessible(true);
  }
  
  public int get() {
    try {
      return this.field.getInt(null);
    } catch (Exception exception) {
      return 0;
    } 
  }
  
  public void set(int paramInt) {
    try {
      this.field.setInt(null, paramInt);
    } catch (Exception exception) {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\RefStaticInt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */