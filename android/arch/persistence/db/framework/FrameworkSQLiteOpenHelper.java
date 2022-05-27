package android.arch.persistence.db.framework;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class FrameworkSQLiteOpenHelper implements SupportSQLiteOpenHelper {
  private final OpenHelper mDelegate;
  
  FrameworkSQLiteOpenHelper(Context paramContext, String paramString, SupportSQLiteOpenHelper.Callback paramCallback) {
    this.mDelegate = createDelegate(paramContext, paramString, paramCallback);
  }
  
  private OpenHelper createDelegate(Context paramContext, String paramString, SupportSQLiteOpenHelper.Callback paramCallback) {
    return new OpenHelper(paramContext, paramString, new FrameworkSQLiteDatabase[1], paramCallback);
  }
  
  public void close() {
    this.mDelegate.close();
  }
  
  public String getDatabaseName() {
    return this.mDelegate.getDatabaseName();
  }
  
  public SupportSQLiteDatabase getReadableDatabase() {
    return this.mDelegate.getReadableSupportDatabase();
  }
  
  public SupportSQLiteDatabase getWritableDatabase() {
    return this.mDelegate.getWritableSupportDatabase();
  }
  
  public void setWriteAheadLoggingEnabled(boolean paramBoolean) {
    this.mDelegate.setWriteAheadLoggingEnabled(paramBoolean);
  }
  
  static class OpenHelper extends SQLiteOpenHelper {
    final SupportSQLiteOpenHelper.Callback mCallback;
    
    final FrameworkSQLiteDatabase[] mDbRef;
    
    private boolean mMigrated;
    
    OpenHelper(Context param1Context, String param1String, FrameworkSQLiteDatabase[] param1ArrayOfFrameworkSQLiteDatabase, SupportSQLiteOpenHelper.Callback param1Callback) {
      super(param1Context, param1String, null, param1Callback.version, new DatabaseErrorHandler(param1ArrayOfFrameworkSQLiteDatabase, param1Callback) {
            public void onCorruption(SQLiteDatabase param2SQLiteDatabase) {
              FrameworkSQLiteDatabase frameworkSQLiteDatabase = dbRef[0];
              if (frameworkSQLiteDatabase != null)
                callback.onCorruption(frameworkSQLiteDatabase); 
            }
          });
      this.mCallback = param1Callback;
      this.mDbRef = param1ArrayOfFrameworkSQLiteDatabase;
    }
    
    public void close() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: invokespecial close : ()V
      //   6: aload_0
      //   7: getfield mDbRef : [Landroid/arch/persistence/db/framework/FrameworkSQLiteDatabase;
      //   10: iconst_0
      //   11: aconst_null
      //   12: aastore
      //   13: aload_0
      //   14: monitorexit
      //   15: return
      //   16: astore_1
      //   17: aload_0
      //   18: monitorexit
      //   19: aload_1
      //   20: athrow
      // Exception table:
      //   from	to	target	type
      //   2	13	16	finally
    }
    
    SupportSQLiteDatabase getReadableSupportDatabase() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: iconst_0
      //   4: putfield mMigrated : Z
      //   7: aload_0
      //   8: invokespecial getReadableDatabase : ()Landroid/database/sqlite/SQLiteDatabase;
      //   11: astore_1
      //   12: aload_0
      //   13: getfield mMigrated : Z
      //   16: ifeq -> 32
      //   19: aload_0
      //   20: invokevirtual close : ()V
      //   23: aload_0
      //   24: invokevirtual getReadableSupportDatabase : ()Landroid/arch/persistence/db/SupportSQLiteDatabase;
      //   27: astore_1
      //   28: aload_0
      //   29: monitorexit
      //   30: aload_1
      //   31: areturn
      //   32: aload_0
      //   33: aload_1
      //   34: invokevirtual getWrappedDb : (Landroid/database/sqlite/SQLiteDatabase;)Landroid/arch/persistence/db/framework/FrameworkSQLiteDatabase;
      //   37: astore_1
      //   38: aload_0
      //   39: monitorexit
      //   40: aload_1
      //   41: areturn
      //   42: astore_1
      //   43: aload_0
      //   44: monitorexit
      //   45: aload_1
      //   46: athrow
      // Exception table:
      //   from	to	target	type
      //   2	28	42	finally
      //   32	38	42	finally
    }
    
    FrameworkSQLiteDatabase getWrappedDb(SQLiteDatabase param1SQLiteDatabase) {
      if (this.mDbRef[0] == null) {
        FrameworkSQLiteDatabase frameworkSQLiteDatabase = new FrameworkSQLiteDatabase(param1SQLiteDatabase);
        this.mDbRef[0] = frameworkSQLiteDatabase;
      } 
      return this.mDbRef[0];
    }
    
    SupportSQLiteDatabase getWritableSupportDatabase() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: iconst_0
      //   4: putfield mMigrated : Z
      //   7: aload_0
      //   8: invokespecial getWritableDatabase : ()Landroid/database/sqlite/SQLiteDatabase;
      //   11: astore_1
      //   12: aload_0
      //   13: getfield mMigrated : Z
      //   16: ifeq -> 32
      //   19: aload_0
      //   20: invokevirtual close : ()V
      //   23: aload_0
      //   24: invokevirtual getWritableSupportDatabase : ()Landroid/arch/persistence/db/SupportSQLiteDatabase;
      //   27: astore_1
      //   28: aload_0
      //   29: monitorexit
      //   30: aload_1
      //   31: areturn
      //   32: aload_0
      //   33: aload_1
      //   34: invokevirtual getWrappedDb : (Landroid/database/sqlite/SQLiteDatabase;)Landroid/arch/persistence/db/framework/FrameworkSQLiteDatabase;
      //   37: astore_1
      //   38: aload_0
      //   39: monitorexit
      //   40: aload_1
      //   41: areturn
      //   42: astore_1
      //   43: aload_0
      //   44: monitorexit
      //   45: aload_1
      //   46: athrow
      // Exception table:
      //   from	to	target	type
      //   2	28	42	finally
      //   32	38	42	finally
    }
    
    public void onConfigure(SQLiteDatabase param1SQLiteDatabase) {
      this.mCallback.onConfigure(getWrappedDb(param1SQLiteDatabase));
    }
    
    public void onCreate(SQLiteDatabase param1SQLiteDatabase) {
      this.mCallback.onCreate(getWrappedDb(param1SQLiteDatabase));
    }
    
    public void onDowngrade(SQLiteDatabase param1SQLiteDatabase, int param1Int1, int param1Int2) {
      this.mMigrated = true;
      this.mCallback.onDowngrade(getWrappedDb(param1SQLiteDatabase), param1Int1, param1Int2);
    }
    
    public void onOpen(SQLiteDatabase param1SQLiteDatabase) {
      if (!this.mMigrated)
        this.mCallback.onOpen(getWrappedDb(param1SQLiteDatabase)); 
    }
    
    public void onUpgrade(SQLiteDatabase param1SQLiteDatabase, int param1Int1, int param1Int2) {
      this.mMigrated = true;
      this.mCallback.onUpgrade(getWrappedDb(param1SQLiteDatabase), param1Int1, param1Int2);
    }
  }
  
  class null implements DatabaseErrorHandler {
    public void onCorruption(SQLiteDatabase param1SQLiteDatabase) {
      FrameworkSQLiteDatabase frameworkSQLiteDatabase = dbRef[0];
      if (frameworkSQLiteDatabase != null)
        callback.onCorruption(frameworkSQLiteDatabase); 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\db\framework\FrameworkSQLiteOpenHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */