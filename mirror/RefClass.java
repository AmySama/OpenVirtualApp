package mirror;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public final class RefClass {
  private static HashMap<Class<?>, Constructor<?>> REF_TYPES;
  
  static {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    REF_TYPES = (HashMap)hashMap;
    try {
      hashMap.put(RefObject.class, RefObject.class.getConstructor(new Class[] { Class.class, Field.class }));
      REF_TYPES.put(RefMethod.class, RefMethod.class.getConstructor(new Class[] { Class.class, Field.class }));
      REF_TYPES.put(RefInt.class, RefInt.class.getConstructor(new Class[] { Class.class, Field.class }));
      REF_TYPES.put(RefLong.class, RefLong.class.getConstructor(new Class[] { Class.class, Field.class }));
      REF_TYPES.put(RefFloat.class, RefFloat.class.getConstructor(new Class[] { Class.class, Field.class }));
      REF_TYPES.put(RefDouble.class, RefDouble.class.getConstructor(new Class[] { Class.class, Field.class }));
      REF_TYPES.put(RefBoolean.class, RefBoolean.class.getConstructor(new Class[] { Class.class, Field.class }));
      REF_TYPES.put(RefStaticObject.class, RefStaticObject.class.getConstructor(new Class[] { Class.class, Field.class }));
      REF_TYPES.put(RefStaticInt.class, RefStaticInt.class.getConstructor(new Class[] { Class.class, Field.class }));
      REF_TYPES.put(RefStaticMethod.class, RefStaticMethod.class.getConstructor(new Class[] { Class.class, Field.class }));
      REF_TYPES.put(RefConstructor.class, RefConstructor.class.getConstructor(new Class[] { Class.class, Field.class }));
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public static Class load(Class paramClass, Class<?> paramClass1) {
    Field[] arrayOfField = paramClass.getDeclaredFields();
    int i = arrayOfField.length;
    byte b = 0;
    while (true) {
      if (b < i) {
        Field field = arrayOfField[b];
        try {
          if (Modifier.isStatic(field.getModifiers())) {
            Constructor constructor = REF_TYPES.get(field.getType());
            if (constructor != null)
              field.set(null, constructor.newInstance(new Object[] { paramClass1, field })); 
          } 
        } catch (Exception exception) {}
        b++;
        continue;
      } 
      return paramClass1;
    } 
  }
  
  public static Class<?> load(Class<?> paramClass, String paramString) {
    try {
      return load(paramClass, Class.forName(paramString));
    } catch (Exception exception) {
      return null;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\RefClass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */