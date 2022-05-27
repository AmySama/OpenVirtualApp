package android.support.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface VisibleForTesting {
  public static final int NONE = 5;
  
  public static final int PACKAGE_PRIVATE = 3;
  
  public static final int PRIVATE = 2;
  
  public static final int PROTECTED = 4;
  
  int otherwise() default 2;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\annotation\VisibleForTesting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */