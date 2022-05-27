package android.arch.persistence.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteTransactionListener;
import android.os.CancellationSignal;
import android.util.Pair;
import java.io.Closeable;
import java.util.List;
import java.util.Locale;

public interface SupportSQLiteDatabase extends Closeable {
  void beginTransaction();
  
  void beginTransactionNonExclusive();
  
  void beginTransactionWithListener(SQLiteTransactionListener paramSQLiteTransactionListener);
  
  void beginTransactionWithListenerNonExclusive(SQLiteTransactionListener paramSQLiteTransactionListener);
  
  SupportSQLiteStatement compileStatement(String paramString);
  
  int delete(String paramString1, String paramString2, Object[] paramArrayOfObject);
  
  void disableWriteAheadLogging();
  
  boolean enableWriteAheadLogging();
  
  void endTransaction();
  
  void execSQL(String paramString) throws SQLException;
  
  void execSQL(String paramString, Object[] paramArrayOfObject) throws SQLException;
  
  List<Pair<String, String>> getAttachedDbs();
  
  long getMaximumSize();
  
  long getPageSize();
  
  String getPath();
  
  int getVersion();
  
  boolean inTransaction();
  
  long insert(String paramString, int paramInt, ContentValues paramContentValues) throws SQLException;
  
  boolean isDatabaseIntegrityOk();
  
  boolean isDbLockedByCurrentThread();
  
  boolean isOpen();
  
  boolean isReadOnly();
  
  boolean isWriteAheadLoggingEnabled();
  
  boolean needUpgrade(int paramInt);
  
  Cursor query(SupportSQLiteQuery paramSupportSQLiteQuery);
  
  Cursor query(SupportSQLiteQuery paramSupportSQLiteQuery, CancellationSignal paramCancellationSignal);
  
  Cursor query(String paramString);
  
  Cursor query(String paramString, Object[] paramArrayOfObject);
  
  void setForeignKeyConstraintsEnabled(boolean paramBoolean);
  
  void setLocale(Locale paramLocale);
  
  void setMaxSqlCacheSize(int paramInt);
  
  long setMaximumSize(long paramLong);
  
  void setPageSize(long paramLong);
  
  void setTransactionSuccessful();
  
  void setVersion(int paramInt);
  
  int update(String paramString1, int paramInt, ContentValues paramContentValues, String paramString2, Object[] paramArrayOfObject);
  
  boolean yieldIfContendedSafely();
  
  boolean yieldIfContendedSafely(long paramLong);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\db\SupportSQLiteDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */