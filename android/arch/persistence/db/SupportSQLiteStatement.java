package android.arch.persistence.db;

public interface SupportSQLiteStatement extends SupportSQLiteProgram {
  void execute();
  
  long executeInsert();
  
  int executeUpdateDelete();
  
  long simpleQueryForLong();
  
  String simpleQueryForString();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\db\SupportSQLiteStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */