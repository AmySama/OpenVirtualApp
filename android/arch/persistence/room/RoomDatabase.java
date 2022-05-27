package android.arch.persistence.room;

import android.app.ActivityManager;
import android.arch.core.executor.ArchTaskExecutor;
import android.arch.persistence.db.SimpleSQLiteQuery;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class RoomDatabase {
  private static final String DB_IMPL_SUFFIX = "_Impl";
  
  public static final int MAX_BIND_PARAMETER_CNT = 999;
  
  private boolean mAllowMainThreadQueries;
  
  protected List<Callback> mCallbacks;
  
  private final ReentrantLock mCloseLock = new ReentrantLock();
  
  protected volatile SupportSQLiteDatabase mDatabase;
  
  private final InvalidationTracker mInvalidationTracker = createInvalidationTracker();
  
  private SupportSQLiteOpenHelper mOpenHelper;
  
  boolean mWriteAheadLoggingEnabled;
  
  public void assertNotMainThread() {
    if (this.mAllowMainThreadQueries)
      return; 
    if (!ArchTaskExecutor.getInstance().isMainThread())
      return; 
    throw new IllegalStateException("Cannot access database on the main thread since it may potentially lock the UI for a long period of time.");
  }
  
  public void beginTransaction() {
    assertNotMainThread();
    SupportSQLiteDatabase supportSQLiteDatabase = this.mOpenHelper.getWritableDatabase();
    this.mInvalidationTracker.syncTriggers(supportSQLiteDatabase);
    supportSQLiteDatabase.beginTransaction();
  }
  
  public abstract void clearAllTables();
  
  public void close() {
    if (isOpen())
      try {
        this.mCloseLock.lock();
        this.mOpenHelper.close();
      } finally {
        this.mCloseLock.unlock();
      }  
  }
  
  public SupportSQLiteStatement compileStatement(String paramString) {
    assertNotMainThread();
    return this.mOpenHelper.getWritableDatabase().compileStatement(paramString);
  }
  
  protected abstract InvalidationTracker createInvalidationTracker();
  
  protected abstract SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration paramDatabaseConfiguration);
  
  public void endTransaction() {
    this.mOpenHelper.getWritableDatabase().endTransaction();
    if (!inTransaction())
      this.mInvalidationTracker.refreshVersionsAsync(); 
  }
  
  Lock getCloseLock() {
    return this.mCloseLock;
  }
  
  public InvalidationTracker getInvalidationTracker() {
    return this.mInvalidationTracker;
  }
  
  public SupportSQLiteOpenHelper getOpenHelper() {
    return this.mOpenHelper;
  }
  
  public boolean inTransaction() {
    return this.mOpenHelper.getWritableDatabase().inTransaction();
  }
  
  public void init(DatabaseConfiguration paramDatabaseConfiguration) {
    this.mOpenHelper = createOpenHelper(paramDatabaseConfiguration);
    int i = Build.VERSION.SDK_INT;
    boolean bool1 = false;
    boolean bool2 = false;
    if (i >= 16) {
      bool1 = bool2;
      if (paramDatabaseConfiguration.journalMode == JournalMode.WRITE_AHEAD_LOGGING)
        bool1 = true; 
      this.mOpenHelper.setWriteAheadLoggingEnabled(bool1);
    } 
    this.mCallbacks = paramDatabaseConfiguration.callbacks;
    this.mAllowMainThreadQueries = paramDatabaseConfiguration.allowMainThreadQueries;
    this.mWriteAheadLoggingEnabled = bool1;
  }
  
  protected void internalInitInvalidationTracker(SupportSQLiteDatabase paramSupportSQLiteDatabase) {
    this.mInvalidationTracker.internalInit(paramSupportSQLiteDatabase);
  }
  
  public boolean isOpen() {
    boolean bool;
    SupportSQLiteDatabase supportSQLiteDatabase = this.mDatabase;
    if (supportSQLiteDatabase != null && supportSQLiteDatabase.isOpen()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public Cursor query(SupportSQLiteQuery paramSupportSQLiteQuery) {
    assertNotMainThread();
    return this.mOpenHelper.getWritableDatabase().query(paramSupportSQLiteQuery);
  }
  
  public Cursor query(String paramString, Object[] paramArrayOfObject) {
    return this.mOpenHelper.getWritableDatabase().query((SupportSQLiteQuery)new SimpleSQLiteQuery(paramString, paramArrayOfObject));
  }
  
  public <V> V runInTransaction(Callable<V> paramCallable) {
    beginTransaction();
    try {
      paramCallable = (Callable<V>)paramCallable.call();
      setTransactionSuccessful();
      endTransaction();
      return (V)paramCallable;
    } catch (RuntimeException runtimeException) {
      throw runtimeException;
    } catch (Exception exception) {
      RuntimeException runtimeException = new RuntimeException();
      this("Exception in transaction", exception);
      throw runtimeException;
    } finally {}
    endTransaction();
    throw paramCallable;
  }
  
  public void runInTransaction(Runnable paramRunnable) {
    beginTransaction();
    try {
      paramRunnable.run();
      setTransactionSuccessful();
      return;
    } finally {
      endTransaction();
    } 
  }
  
  public void setTransactionSuccessful() {
    this.mOpenHelper.getWritableDatabase().setTransactionSuccessful();
  }
  
  public static class Builder<T extends RoomDatabase> {
    private boolean mAllowMainThreadQueries;
    
    private ArrayList<RoomDatabase.Callback> mCallbacks;
    
    private final Context mContext;
    
    private final Class<T> mDatabaseClass;
    
    private SupportSQLiteOpenHelper.Factory mFactory;
    
    private RoomDatabase.JournalMode mJournalMode;
    
    private final RoomDatabase.MigrationContainer mMigrationContainer;
    
    private Set<Integer> mMigrationStartAndEndVersions;
    
    private Set<Integer> mMigrationsNotRequiredFrom;
    
    private final String mName;
    
    private boolean mRequireMigration;
    
    Builder(Context param1Context, Class<T> param1Class, String param1String) {
      this.mContext = param1Context;
      this.mDatabaseClass = param1Class;
      this.mName = param1String;
      this.mJournalMode = RoomDatabase.JournalMode.AUTOMATIC;
      this.mRequireMigration = true;
      this.mMigrationContainer = new RoomDatabase.MigrationContainer();
    }
    
    public Builder<T> addCallback(RoomDatabase.Callback param1Callback) {
      if (this.mCallbacks == null)
        this.mCallbacks = new ArrayList<RoomDatabase.Callback>(); 
      this.mCallbacks.add(param1Callback);
      return this;
    }
    
    public Builder<T> addMigrations(Migration... param1VarArgs) {
      if (this.mMigrationStartAndEndVersions == null)
        this.mMigrationStartAndEndVersions = new HashSet<Integer>(); 
      int i = param1VarArgs.length;
      for (byte b = 0; b < i; b++) {
        Migration migration = param1VarArgs[b];
        this.mMigrationStartAndEndVersions.add(Integer.valueOf(migration.startVersion));
        this.mMigrationStartAndEndVersions.add(Integer.valueOf(migration.endVersion));
      } 
      this.mMigrationContainer.addMigrations(param1VarArgs);
      return this;
    }
    
    public Builder<T> allowMainThreadQueries() {
      this.mAllowMainThreadQueries = true;
      return this;
    }
    
    public T build() {
      if (this.mContext != null) {
        if (this.mDatabaseClass != null) {
          Set<Integer> set = this.mMigrationStartAndEndVersions;
          if (set != null && this.mMigrationsNotRequiredFrom != null) {
            Iterator<Integer> iterator = set.iterator();
            while (iterator.hasNext()) {
              Integer integer = iterator.next();
              if (!this.mMigrationsNotRequiredFrom.contains(integer))
                continue; 
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Inconsistency detected. A Migration was supplied to addMigration(Migration... migrations) that has a start or end version equal to a start version supplied to fallbackToDestructiveMigrationFrom(int... startVersions). Start version: ");
              stringBuilder.append(integer);
              throw new IllegalArgumentException(stringBuilder.toString());
            } 
          } 
          if (this.mFactory == null)
            this.mFactory = (SupportSQLiteOpenHelper.Factory)new FrameworkSQLiteOpenHelperFactory(); 
          Context context = this.mContext;
          DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(context, this.mName, this.mFactory, this.mMigrationContainer, this.mCallbacks, this.mAllowMainThreadQueries, this.mJournalMode.resolve(context), this.mRequireMigration, this.mMigrationsNotRequiredFrom);
          RoomDatabase roomDatabase = Room.<RoomDatabase, T>getGeneratedImplementation(this.mDatabaseClass, "_Impl");
          roomDatabase.init(databaseConfiguration);
          return (T)roomDatabase;
        } 
        throw new IllegalArgumentException("Must provide an abstract class that extends RoomDatabase");
      } 
      throw new IllegalArgumentException("Cannot provide null context for the database.");
    }
    
    public Builder<T> fallbackToDestructiveMigration() {
      this.mRequireMigration = false;
      return this;
    }
    
    public Builder<T> fallbackToDestructiveMigrationFrom(int... param1VarArgs) {
      if (this.mMigrationsNotRequiredFrom == null)
        this.mMigrationsNotRequiredFrom = new HashSet<Integer>(param1VarArgs.length); 
      int i = param1VarArgs.length;
      for (byte b = 0; b < i; b++) {
        int j = param1VarArgs[b];
        this.mMigrationsNotRequiredFrom.add(Integer.valueOf(j));
      } 
      return this;
    }
    
    public Builder<T> openHelperFactory(SupportSQLiteOpenHelper.Factory param1Factory) {
      this.mFactory = param1Factory;
      return this;
    }
    
    public Builder<T> setJournalMode(RoomDatabase.JournalMode param1JournalMode) {
      this.mJournalMode = param1JournalMode;
      return this;
    }
  }
  
  public static abstract class Callback {
    public void onCreate(SupportSQLiteDatabase param1SupportSQLiteDatabase) {}
    
    public void onOpen(SupportSQLiteDatabase param1SupportSQLiteDatabase) {}
  }
  
  public enum JournalMode {
    AUTOMATIC, TRUNCATE, WRITE_AHEAD_LOGGING;
    
    static {
      JournalMode journalMode = new JournalMode("WRITE_AHEAD_LOGGING", 2);
      WRITE_AHEAD_LOGGING = journalMode;
      $VALUES = new JournalMode[] { AUTOMATIC, TRUNCATE, journalMode };
    }
    
    JournalMode resolve(Context param1Context) {
      if (this != AUTOMATIC)
        return this; 
      if (Build.VERSION.SDK_INT >= 16) {
        ActivityManager activityManager = (ActivityManager)param1Context.getSystemService("activity");
        if (activityManager != null && !ActivityManagerCompat.isLowRamDevice(activityManager))
          return WRITE_AHEAD_LOGGING; 
      } 
      return TRUNCATE;
    }
  }
  
  public static class MigrationContainer {
    private SparseArrayCompat<SparseArrayCompat<Migration>> mMigrations = new SparseArrayCompat();
    
    private void addMigration(Migration param1Migration) {
      int i = param1Migration.startVersion;
      int j = param1Migration.endVersion;
      SparseArrayCompat sparseArrayCompat1 = (SparseArrayCompat)this.mMigrations.get(i);
      SparseArrayCompat sparseArrayCompat2 = sparseArrayCompat1;
      if (sparseArrayCompat1 == null) {
        sparseArrayCompat2 = new SparseArrayCompat();
        this.mMigrations.put(i, sparseArrayCompat2);
      } 
      Migration migration = (Migration)sparseArrayCompat2.get(j);
      if (migration != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Overriding migration ");
        stringBuilder.append(migration);
        stringBuilder.append(" with ");
        stringBuilder.append(param1Migration);
        Log.w("ROOM", stringBuilder.toString());
      } 
      sparseArrayCompat2.append(j, param1Migration);
    }
    
    private List<Migration> findUpMigrationPath(List<Migration> param1List, boolean param1Boolean, int param1Int1, int param1Int2) {
      byte b;
      int i;
      if (param1Boolean) {
        b = -1;
        i = param1Int1;
      } else {
        b = 1;
        i = param1Int1;
      } 
      while (param1Boolean ? (i < param1Int2) : (i > param1Int2)) {
        boolean bool2;
        int k;
        SparseArrayCompat sparseArrayCompat = (SparseArrayCompat)this.mMigrations.get(i);
        if (sparseArrayCompat == null)
          return null; 
        int j = sparseArrayCompat.size();
        boolean bool1 = false;
        if (param1Boolean) {
          param1Int1 = j - 1;
          j = -1;
        } else {
          param1Int1 = 0;
        } 
        while (true) {
          bool2 = bool1;
          k = i;
          if (param1Int1 != j) {
            k = sparseArrayCompat.keyAt(param1Int1);
            if (param1Boolean ? (k <= param1Int2 && k > i) : (k >= param1Int2 && k < i)) {
              bool2 = true;
            } else {
              bool2 = false;
            } 
            if (bool2) {
              param1List.add(sparseArrayCompat.valueAt(param1Int1));
              bool2 = true;
              break;
            } 
            param1Int1 += b;
            continue;
          } 
          break;
        } 
        i = k;
        if (!bool2)
          return null; 
      } 
      return param1List;
    }
    
    public void addMigrations(Migration... param1VarArgs) {
      int i = param1VarArgs.length;
      for (byte b = 0; b < i; b++)
        addMigration(param1VarArgs[b]); 
    }
    
    public List<Migration> findMigrationPath(int param1Int1, int param1Int2) {
      boolean bool;
      if (param1Int1 == param1Int2)
        return Collections.emptyList(); 
      if (param1Int2 > param1Int1) {
        bool = true;
      } else {
        bool = false;
      } 
      return findUpMigrationPath(new ArrayList<Migration>(), bool, param1Int1, param1Int2);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\room\RoomDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */