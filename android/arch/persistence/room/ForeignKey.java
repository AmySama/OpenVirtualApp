package android.arch.persistence.room;

public @interface ForeignKey {
  public static final int CASCADE = 5;
  
  public static final int NO_ACTION = 1;
  
  public static final int RESTRICT = 2;
  
  public static final int SET_DEFAULT = 4;
  
  public static final int SET_NULL = 3;
  
  String[] childColumns();
  
  boolean deferred() default false;
  
  Class entity();
  
  int onDelete() default 1;
  
  int onUpdate() default 1;
  
  String[] parentColumns();
  
  public static @interface Action {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\room\ForeignKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */