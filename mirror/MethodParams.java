package mirror;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface MethodParams {
  Class<?>[] value();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\MethodParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */