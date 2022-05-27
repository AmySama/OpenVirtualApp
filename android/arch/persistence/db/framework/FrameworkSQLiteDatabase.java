package android.arch.persistence.db.framework;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteProgram;
import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteProgram;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteTransactionListener;
import android.os.CancellationSignal;
import android.text.TextUtils;
import android.util.Pair;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

class FrameworkSQLiteDatabase implements SupportSQLiteDatabase {
  private static final String[] CONFLICT_VALUES = new String[] { "", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE " };
  
  private static final String[] EMPTY_STRING_ARRAY = new String[0];
  
  private final SQLiteDatabase mDelegate;
  
  FrameworkSQLiteDatabase(SQLiteDatabase paramSQLiteDatabase) {
    this.mDelegate = paramSQLiteDatabase;
  }
  
  public void beginTransaction() {
    this.mDelegate.beginTransaction();
  }
  
  public void beginTransactionNonExclusive() {
    this.mDelegate.beginTransactionNonExclusive();
  }
  
  public void beginTransactionWithListener(SQLiteTransactionListener paramSQLiteTransactionListener) {
    this.mDelegate.beginTransactionWithListener(paramSQLiteTransactionListener);
  }
  
  public void beginTransactionWithListenerNonExclusive(SQLiteTransactionListener paramSQLiteTransactionListener) {
    this.mDelegate.beginTransactionWithListenerNonExclusive(paramSQLiteTransactionListener);
  }
  
  public void close() throws IOException {
    this.mDelegate.close();
  }
  
  public SupportSQLiteStatement compileStatement(String paramString) {
    return new FrameworkSQLiteStatement(this.mDelegate.compileStatement(paramString));
  }
  
  public int delete(String paramString1, String paramString2, Object[] paramArrayOfObject) {
    String str;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("DELETE FROM ");
    stringBuilder.append(paramString1);
    if (TextUtils.isEmpty(paramString2)) {
      paramString1 = "";
    } else {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(" WHERE ");
      stringBuilder1.append(paramString2);
      str = stringBuilder1.toString();
    } 
    stringBuilder.append(str);
    SupportSQLiteStatement supportSQLiteStatement = compileStatement(stringBuilder.toString());
    SimpleSQLiteQuery.bind((SupportSQLiteProgram)supportSQLiteStatement, paramArrayOfObject);
    return supportSQLiteStatement.executeUpdateDelete();
  }
  
  public void disableWriteAheadLogging() {
    this.mDelegate.disableWriteAheadLogging();
  }
  
  public boolean enableWriteAheadLogging() {
    return this.mDelegate.enableWriteAheadLogging();
  }
  
  public void endTransaction() {
    this.mDelegate.endTransaction();
  }
  
  public void execSQL(String paramString) throws SQLException {
    this.mDelegate.execSQL(paramString);
  }
  
  public void execSQL(String paramString, Object[] paramArrayOfObject) throws SQLException {
    this.mDelegate.execSQL(paramString, paramArrayOfObject);
  }
  
  public List<Pair<String, String>> getAttachedDbs() {
    return this.mDelegate.getAttachedDbs();
  }
  
  public long getMaximumSize() {
    return this.mDelegate.getMaximumSize();
  }
  
  public long getPageSize() {
    return this.mDelegate.getPageSize();
  }
  
  public String getPath() {
    return this.mDelegate.getPath();
  }
  
  public int getVersion() {
    return this.mDelegate.getVersion();
  }
  
  public boolean inTransaction() {
    return this.mDelegate.inTransaction();
  }
  
  public long insert(String paramString, int paramInt, ContentValues paramContentValues) throws SQLException {
    return this.mDelegate.insertWithOnConflict(paramString, null, paramContentValues, paramInt);
  }
  
  public boolean isDatabaseIntegrityOk() {
    return this.mDelegate.isDatabaseIntegrityOk();
  }
  
  public boolean isDbLockedByCurrentThread() {
    return this.mDelegate.isDbLockedByCurrentThread();
  }
  
  public boolean isOpen() {
    return this.mDelegate.isOpen();
  }
  
  public boolean isReadOnly() {
    return this.mDelegate.isReadOnly();
  }
  
  public boolean isWriteAheadLoggingEnabled() {
    return this.mDelegate.isWriteAheadLoggingEnabled();
  }
  
  public boolean needUpgrade(int paramInt) {
    return this.mDelegate.needUpgrade(paramInt);
  }
  
  public Cursor query(final SupportSQLiteQuery supportQuery) {
    return this.mDelegate.rawQueryWithFactory(new SQLiteDatabase.CursorFactory() {
          public Cursor newCursor(SQLiteDatabase param1SQLiteDatabase, SQLiteCursorDriver param1SQLiteCursorDriver, String param1String, SQLiteQuery param1SQLiteQuery) {
            supportQuery.bindTo(new FrameworkSQLiteProgram((SQLiteProgram)param1SQLiteQuery));
            return (Cursor)new SQLiteCursor(param1SQLiteCursorDriver, param1String, param1SQLiteQuery);
          }
        }supportQuery.getSql(), EMPTY_STRING_ARRAY, null);
  }
  
  public Cursor query(final SupportSQLiteQuery supportQuery, CancellationSignal paramCancellationSignal) {
    return this.mDelegate.rawQueryWithFactory(new SQLiteDatabase.CursorFactory() {
          public Cursor newCursor(SQLiteDatabase param1SQLiteDatabase, SQLiteCursorDriver param1SQLiteCursorDriver, String param1String, SQLiteQuery param1SQLiteQuery) {
            supportQuery.bindTo(new FrameworkSQLiteProgram((SQLiteProgram)param1SQLiteQuery));
            return (Cursor)new SQLiteCursor(param1SQLiteCursorDriver, param1String, param1SQLiteQuery);
          }
        }supportQuery.getSql(), EMPTY_STRING_ARRAY, null, paramCancellationSignal);
  }
  
  public Cursor query(String paramString) {
    return query((SupportSQLiteQuery)new SimpleSQLiteQuery(paramString));
  }
  
  public Cursor query(String paramString, Object[] paramArrayOfObject) {
    return query((SupportSQLiteQuery)new SimpleSQLiteQuery(paramString, paramArrayOfObject));
  }
  
  public void setForeignKeyConstraintsEnabled(boolean paramBoolean) {
    this.mDelegate.setForeignKeyConstraintsEnabled(paramBoolean);
  }
  
  public void setLocale(Locale paramLocale) {
    this.mDelegate.setLocale(paramLocale);
  }
  
  public void setMaxSqlCacheSize(int paramInt) {
    this.mDelegate.setMaxSqlCacheSize(paramInt);
  }
  
  public long setMaximumSize(long paramLong) {
    return this.mDelegate.setMaximumSize(paramLong);
  }
  
  public void setPageSize(long paramLong) {
    this.mDelegate.setPageSize(paramLong);
  }
  
  public void setTransactionSuccessful() {
    this.mDelegate.setTransactionSuccessful();
  }
  
  public void setVersion(int paramInt) {
    this.mDelegate.setVersion(paramInt);
  }
  
  public int update(String paramString1, int paramInt, ContentValues paramContentValues, String paramString2, Object[] paramArrayOfObject) {
    if (paramContentValues != null && paramContentValues.size() != 0) {
      int i;
      StringBuilder stringBuilder = new StringBuilder(120);
      stringBuilder.append("UPDATE ");
      stringBuilder.append(CONFLICT_VALUES[paramInt]);
      stringBuilder.append(paramString1);
      stringBuilder.append(" SET ");
      paramInt = paramContentValues.size();
      if (paramArrayOfObject == null) {
        i = paramInt;
      } else {
        i = paramArrayOfObject.length + paramInt;
      } 
      Object[] arrayOfObject = new Object[i];
      int j = 0;
      for (String str : paramContentValues.keySet()) {
        if (j) {
          paramString1 = ",";
        } else {
          paramString1 = "";
        } 
        stringBuilder.append(paramString1);
        stringBuilder.append(str);
        arrayOfObject[j] = paramContentValues.get(str);
        stringBuilder.append("=?");
        j++;
      } 
      if (paramArrayOfObject != null)
        for (j = paramInt; j < i; j++)
          arrayOfObject[j] = paramArrayOfObject[j - paramInt];  
      if (!TextUtils.isEmpty(paramString2)) {
        stringBuilder.append(" WHERE ");
        stringBuilder.append(paramString2);
      } 
      SupportSQLiteStatement supportSQLiteStatement = compileStatement(stringBuilder.toString());
      SimpleSQLiteQuery.bind((SupportSQLiteProgram)supportSQLiteStatement, arrayOfObject);
      return supportSQLiteStatement.executeUpdateDelete();
    } 
    throw new IllegalArgumentException("Empty values");
  }
  
  public boolean yieldIfContendedSafely() {
    return this.mDelegate.yieldIfContendedSafely();
  }
  
  public boolean yieldIfContendedSafely(long paramLong) {
    return this.mDelegate.yieldIfContendedSafely(paramLong);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\db\framework\FrameworkSQLiteDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */