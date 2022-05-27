package android.arch.persistence.room;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({})
public @interface Index {
  String name() default "";
  
  boolean unique() default false;
  
  String[] value();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\room\Index.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */