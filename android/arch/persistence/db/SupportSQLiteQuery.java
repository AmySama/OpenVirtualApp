package android.arch.persistence.db;

public interface SupportSQLiteQuery {
  void bindTo(SupportSQLiteProgram paramSupportSQLiteProgram);
  
  int getArgCount();
  
  String getSql();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\db\SupportSQLiteQuery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */