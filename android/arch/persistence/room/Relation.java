package android.arch.persistence.room;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD})
public @interface Relation {
  Class entity() default Object.class;
  
  String entityColumn();
  
  String parentColumn();
  
  String[] projection() default {};
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\room\Relation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */