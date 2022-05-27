package android.arch.persistence.room;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD})
public @interface ColumnInfo {
  public static final int BINARY = 2;
  
  public static final int BLOB = 5;
  
  public static final String INHERIT_FIELD_NAME = "[field-name]";
  
  public static final int INTEGER = 3;
  
  public static final int LOCALIZED = 5;
  
  public static final int NOCASE = 3;
  
  public static final int REAL = 4;
  
  public static final int RTRIM = 4;
  
  public static final int TEXT = 2;
  
  public static final int UNDEFINED = 1;
  
  public static final int UNICODE = 6;
  
  public static final int UNSPECIFIED = 1;
  
  int collate() default 1;
  
  boolean index() default false;
  
  String name() default "[field-name]";
  
  int typeAffinity() default 1;
  
  public static @interface Collate {}
  
  public static @interface SQLiteTypeAffinity {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\room\ColumnInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */