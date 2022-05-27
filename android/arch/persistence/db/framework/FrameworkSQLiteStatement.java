package android.arch.persistence.db.framework;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.database.sqlite.SQLiteProgram;
import android.database.sqlite.SQLiteStatement;

class FrameworkSQLiteStatement extends FrameworkSQLiteProgram implements SupportSQLiteStatement {
  private final SQLiteStatement mDelegate;
  
  FrameworkSQLiteStatement(SQLiteStatement paramSQLiteStatement) {
    super((SQLiteProgram)paramSQLiteStatement);
    this.mDelegate = paramSQLiteStatement;
  }
  
  public void execute() {
    this.mDelegate.execute();
  }
  
  public long executeInsert() {
    return this.mDelegate.executeInsert();
  }
  
  public int executeUpdateDelete() {
    return this.mDelegate.executeUpdateDelete();
  }
  
  public long simpleQueryForLong() {
    return this.mDelegate.simpleQueryForLong();
  }
  
  public String simpleQueryForString() {
    return this.mDelegate.simpleQueryForString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\db\framework\FrameworkSQLiteStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */