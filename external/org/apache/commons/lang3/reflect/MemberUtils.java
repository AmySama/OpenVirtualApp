package external.org.apache.commons.lang3.reflect;

import external.org.apache.commons.lang3.ClassUtils;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

public abstract class MemberUtils {
  private static final int ACCESS_TEST = 7;
  
  private static final Class<?>[] ORDERED_PRIMITIVE_TYPES = new Class[] { byte.class, short.class, char.class, int.class, long.class, float.class, double.class };
  
  public static int compareParameterTypes(Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2, Class<?>[] paramArrayOfClass3) {
    boolean bool;
    float f1 = getTotalTransformationCost(paramArrayOfClass3, paramArrayOfClass1);
    float f2 = getTotalTransformationCost(paramArrayOfClass3, paramArrayOfClass2);
    if (f1 < f2) {
      bool = true;
    } else if (f2 < f1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private static float getObjectTransformationCost(Class<?> paramClass1, Class<?> paramClass2) {
    float f2;
    if (paramClass2.isPrimitive())
      return getPrimitivePromotionCost(paramClass1, paramClass2); 
    float f1 = 0.0F;
    while (true) {
      f2 = f1;
      if (paramClass1 != null) {
        f2 = f1;
        if (!paramClass2.equals(paramClass1)) {
          if (paramClass2.isInterface() && ClassUtils.isAssignable(paramClass1, paramClass2)) {
            f2 = f1 + 0.25F;
            break;
          } 
          f1++;
          paramClass1 = paramClass1.getSuperclass();
          continue;
        } 
      } 
      break;
    } 
    f1 = f2;
    if (paramClass1 == null)
      f1 = f2 + 1.5F; 
    return f1;
  }
  
  private static float getPrimitivePromotionCost(Class<?> paramClass1, Class<?> paramClass2) {
    float f1;
    if (!paramClass1.isPrimitive()) {
      paramClass1 = ClassUtils.wrapperToPrimitive(paramClass1);
      f1 = 0.1F;
    } else {
      f1 = 0.0F;
    } 
    byte b = 0;
    Class<?> clazz = paramClass1;
    float f2 = f1;
    while (clazz != paramClass2) {
      Class<?>[] arrayOfClass = ORDERED_PRIMITIVE_TYPES;
      if (b < arrayOfClass.length) {
        f1 = f2;
        paramClass1 = clazz;
        if (clazz == arrayOfClass[b]) {
          f2 += 0.1F;
          f1 = f2;
          paramClass1 = clazz;
          if (b < arrayOfClass.length - 1) {
            paramClass1 = arrayOfClass[b + 1];
            f1 = f2;
          } 
        } 
        b++;
        f2 = f1;
        clazz = paramClass1;
      } 
    } 
    return f2;
  }
  
  private static float getTotalTransformationCost(Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2) {
    float f = 0.0F;
    for (byte b = 0; b < paramArrayOfClass1.length; b++)
      f += getObjectTransformationCost(paramArrayOfClass1[b], paramArrayOfClass2[b]); 
    return f;
  }
  
  static boolean isAccessible(Member paramMember) {
    boolean bool;
    if (paramMember != null && Modifier.isPublic(paramMember.getModifiers()) && !paramMember.isSynthetic()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  static boolean isPackageAccess(int paramInt) {
    boolean bool;
    if ((paramInt & 0x7) == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  static void setAccessibleWorkaround(AccessibleObject paramAccessibleObject) {
    if (paramAccessibleObject != null && !paramAccessibleObject.isAccessible()) {
      Member member = (Member)paramAccessibleObject;
      if (Modifier.isPublic(member.getModifiers()) && isPackageAccess(member.getDeclaringClass().getModifiers()))
        try {
          paramAccessibleObject.setAccessible(true);
        } catch (SecurityException securityException) {} 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\reflect\MemberUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */