package android.arch.persistence.room;

import android.arch.persistence.db.SupportSQLiteStatement;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class SharedSQLiteStatement {
  private final RoomDatabase mDatabase;
  
  private final AtomicBoolean mLock = new AtomicBoolean(false);
  
  private volatile SupportSQLiteStatement mStmt;
  
  public SharedSQLiteStatement(RoomDatabase paramRoomDatabase) {
    this.mDatabase = paramRoomDatabase;
  }
  
  private SupportSQLiteStatement createNewStatement() {
    String str = createQuery();
    return this.mDatabase.compileStatement(str);
  }
  
  private SupportSQLiteStatement getStmt(boolean paramBoolean) {
    SupportSQLiteStatement supportSQLiteStatement;
    if (paramBoolean) {
      if (this.mStmt == null)
        this.mStmt = createNewStatement(); 
      supportSQLiteStatement = this.mStmt;
    } else {
      supportSQLiteStatement = createNewStatement();
    } 
    return supportSQLiteStatement;
  }
  
  public SupportSQLiteStatement acquire() {
    assertNotMainThread();
    return getStmt(this.mLock.compareAndSet(false, true));
  }
  
  protected void assertNotMainThread() {
    this.mDatabase.assertNotMainThread();
  }
  
  protected abstract String createQuery();
  
  public void release(SupportSQLiteStatement paramSupportSQLiteStatement) {
    if (paramSupportSQLiteStatement == this.mStmt)
      this.mLock.set(false); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\room\SharedSQLiteStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */