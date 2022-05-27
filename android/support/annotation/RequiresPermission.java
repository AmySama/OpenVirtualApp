package android.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PARAMETER})
public @interface RequiresPermission {
  String[] allOf() default {};
  
  String[] anyOf() default {};
  
  boolean conditional() default false;
  
  String value() default "";
  
  @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
  public static @interface Read {
    RequiresPermission value() default @RequiresPermission;
  }
  
  @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
  public static @interface Write {
    RequiresPermission value() default @RequiresPermission;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\annotation\RequiresPermission.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */