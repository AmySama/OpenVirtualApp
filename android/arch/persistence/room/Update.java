package android.arch.persistence.room;

public @interface Update {
  int onConflict() default 3;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\room\Update.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */