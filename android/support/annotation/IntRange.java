package android.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
public @interface IntRange {
  long from() default -9223372036854775808L;
  
  long to() default 9223372036854775807L;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\annotation\IntRange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */