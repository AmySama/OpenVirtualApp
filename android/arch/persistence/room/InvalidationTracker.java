package android.arch.persistence.room;

import android.arch.core.executor.ArchTaskExecutor;
import android.arch.core.internal.SafeIterableMap;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.ArraySet;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

public class InvalidationTracker {
  static final String CLEANUP_SQL = "DELETE FROM room_table_modification_log WHERE version NOT IN( SELECT MAX(version) FROM room_table_modification_log GROUP BY table_id)";
  
  private static final String CREATE_VERSION_TABLE_SQL = "CREATE TEMP TABLE room_table_modification_log(version INTEGER PRIMARY KEY AUTOINCREMENT, table_id INTEGER)";
  
  static final String SELECT_UPDATED_TABLES_SQL = "SELECT * FROM room_table_modification_log WHERE version  > ? ORDER BY version ASC;";
  
  private static final String TABLE_ID_COLUMN_NAME = "table_id";
  
  private static final String[] TRIGGERS = new String[] { "UPDATE", "DELETE", "INSERT" };
  
  private static final String UPDATE_TABLE_NAME = "room_table_modification_log";
  
  private static final String VERSION_COLUMN_NAME = "version";
  
  private volatile SupportSQLiteStatement mCleanupStatement;
  
  private final RoomDatabase mDatabase;
  
  private volatile boolean mInitialized;
  
  private long mMaxVersion = 0L;
  
  private ObservedTableTracker mObservedTableTracker;
  
  final SafeIterableMap<Observer, ObserverWrapper> mObserverMap;
  
  AtomicBoolean mPendingRefresh;
  
  private Object[] mQueryArgs = new Object[1];
  
  Runnable mRefreshRunnable;
  
  ArrayMap<String, Integer> mTableIdLookup;
  
  private String[] mTableNames;
  
  long[] mTableVersions;
  
  public InvalidationTracker(RoomDatabase paramRoomDatabase, String... paramVarArgs) {
    byte b = 0;
    this.mPendingRefresh = new AtomicBoolean(false);
    this.mInitialized = false;
    this.mObserverMap = new SafeIterableMap();
    this.mRefreshRunnable = new Runnable() {
        private boolean checkUpdatedTable() {
          Cursor cursor = InvalidationTracker.this.mDatabase.query("SELECT * FROM room_table_modification_log WHERE version  > ? ORDER BY version ASC;", InvalidationTracker.this.mQueryArgs);
          boolean bool = false;
          try {
            while (cursor.moveToNext()) {
              long l = cursor.getLong(0);
              int i = cursor.getInt(1);
              InvalidationTracker.this.mTableVersions[i] = l;
              InvalidationTracker.access$402(InvalidationTracker.this, l);
              bool = true;
            } 
            return bool;
          } finally {
            cursor.close();
          } 
        }
        
        public void run() {
          Lock lock = InvalidationTracker.this.mDatabase.getCloseLock();
          boolean bool1 = false;
          boolean bool2 = false;
          boolean bool3 = false;
          boolean bool4 = bool1;
          boolean bool5 = bool2;
          try {
            lock.lock();
            bool4 = bool1;
            bool5 = bool2;
            boolean bool = InvalidationTracker.this.ensureInitialization();
            if (!bool) {
              lock.unlock();
              return;
            } 
            bool4 = bool1;
            bool5 = bool2;
            bool = InvalidationTracker.this.mPendingRefresh.compareAndSet(true, false);
            if (!bool) {
              lock.unlock();
              return;
            } 
            bool4 = bool1;
            bool5 = bool2;
            bool = InvalidationTracker.this.mDatabase.inTransaction();
            if (bool) {
              lock.unlock();
              return;
            } 
            bool4 = bool1;
            bool5 = bool2;
            InvalidationTracker.this.mCleanupStatement.executeUpdateDelete();
            bool4 = bool1;
            bool5 = bool2;
            InvalidationTracker.this.mQueryArgs[0] = Long.valueOf(InvalidationTracker.this.mMaxVersion);
            bool4 = bool1;
            bool5 = bool2;
            if (InvalidationTracker.this.mDatabase.mWriteAheadLoggingEnabled) {
              bool4 = bool1;
              bool5 = bool2;
              SupportSQLiteDatabase supportSQLiteDatabase = InvalidationTracker.this.mDatabase.getOpenHelper().getWritableDatabase();
              bool1 = bool3;
              try {
                supportSQLiteDatabase.beginTransaction();
                bool1 = bool3;
                bool3 = checkUpdatedTable();
              } finally {
                bool4 = bool1;
                bool5 = bool1;
                supportSQLiteDatabase.endTransaction();
                bool4 = bool1;
                bool5 = bool1;
              } 
            } else {
              bool4 = bool1;
              bool5 = bool2;
              bool3 = checkUpdatedTable();
            } 
          } catch (IllegalStateException illegalStateException) {
            bool4 = bool5;
            Log.e("ROOM", "Cannot run invalidation tracker. Is the db closed?", illegalStateException);
            bool3 = bool4;
          } catch (SQLiteException sQLiteException) {
          
          } finally {
            Exception exception;
          } 
          lock.unlock();
          if (bool3)
            synchronized (InvalidationTracker.this.mObserverMap) {
              Iterator<Map.Entry> iterator = InvalidationTracker.this.mObserverMap.iterator();
              while (iterator.hasNext())
                ((InvalidationTracker.ObserverWrapper)((Map.Entry)iterator.next()).getValue()).checkForInvalidation(InvalidationTracker.this.mTableVersions); 
            }  
        }
      };
    this.mDatabase = paramRoomDatabase;
    this.mObservedTableTracker = new ObservedTableTracker(paramVarArgs.length);
    this.mTableIdLookup = new ArrayMap();
    int i = paramVarArgs.length;
    this.mTableNames = new String[i];
    while (b < i) {
      String str = paramVarArgs[b].toLowerCase(Locale.US);
      this.mTableIdLookup.put(str, Integer.valueOf(b));
      this.mTableNames[b] = str;
      b++;
    } 
    long[] arrayOfLong = new long[paramVarArgs.length];
    this.mTableVersions = arrayOfLong;
    Arrays.fill(arrayOfLong, 0L);
  }
  
  private static void appendTriggerName(StringBuilder paramStringBuilder, String paramString1, String paramString2) {
    paramStringBuilder.append("`");
    paramStringBuilder.append("room_table_modification_trigger_");
    paramStringBuilder.append(paramString1);
    paramStringBuilder.append("_");
    paramStringBuilder.append(paramString2);
    paramStringBuilder.append("`");
  }
  
  private boolean ensureInitialization() {
    if (!this.mDatabase.isOpen())
      return false; 
    if (!this.mInitialized)
      this.mDatabase.getOpenHelper().getWritableDatabase(); 
    if (!this.mInitialized) {
      Log.e("ROOM", "database is not initialized even though it is open");
      return false;
    } 
    return true;
  }
  
  private void startTrackingTable(SupportSQLiteDatabase paramSupportSQLiteDatabase, int paramInt) {
    String str = this.mTableNames[paramInt];
    StringBuilder stringBuilder = new StringBuilder();
    for (String str1 : TRIGGERS) {
      stringBuilder.setLength(0);
      stringBuilder.append("CREATE TEMP TRIGGER IF NOT EXISTS ");
      appendTriggerName(stringBuilder, str, str1);
      stringBuilder.append(" AFTER ");
      stringBuilder.append(str1);
      stringBuilder.append(" ON `");
      stringBuilder.append(str);
      stringBuilder.append("` BEGIN INSERT OR REPLACE INTO ");
      stringBuilder.append("room_table_modification_log");
      stringBuilder.append(" VALUES(null, ");
      stringBuilder.append(paramInt);
      stringBuilder.append("); END");
      paramSupportSQLiteDatabase.execSQL(stringBuilder.toString());
    } 
  }
  
  private void stopTrackingTable(SupportSQLiteDatabase paramSupportSQLiteDatabase, int paramInt) {
    String str = this.mTableNames[paramInt];
    StringBuilder stringBuilder = new StringBuilder();
    String[] arrayOfString = TRIGGERS;
    int i = arrayOfString.length;
    for (paramInt = 0; paramInt < i; paramInt++) {
      String str1 = arrayOfString[paramInt];
      stringBuilder.setLength(0);
      stringBuilder.append("DROP TRIGGER IF EXISTS ");
      appendTriggerName(stringBuilder, str, str1);
      paramSupportSQLiteDatabase.execSQL(stringBuilder.toString());
    } 
  }
  
  public void addObserver(Observer paramObserver) {
    String[] arrayOfString = paramObserver.mTables;
    int[] arrayOfInt = new int[arrayOfString.length];
    int i = arrayOfString.length;
    long[] arrayOfLong = new long[arrayOfString.length];
    byte b = 0;
    while (b < i) {
      Integer integer = (Integer)this.mTableIdLookup.get(arrayOfString[b].toLowerCase(Locale.US));
      if (integer != null) {
        arrayOfInt[b] = integer.intValue();
        arrayOfLong[b] = this.mMaxVersion;
        b++;
        continue;
      } 
      null = new StringBuilder();
      null.append("There is no table with name ");
      null.append(arrayOfString[b]);
      throw new IllegalArgumentException(null.toString());
    } 
    ObserverWrapper observerWrapper = new ObserverWrapper((Observer)null, arrayOfInt, arrayOfString, arrayOfLong);
    synchronized (this.mObserverMap) {
      ObserverWrapper observerWrapper1 = (ObserverWrapper)this.mObserverMap.putIfAbsent(null, observerWrapper);
      if (observerWrapper1 == null && this.mObservedTableTracker.onAdded(arrayOfInt))
        syncTriggers(); 
      return;
    } 
  }
  
  public void addWeakObserver(Observer paramObserver) {
    addObserver(new WeakObserver(this, paramObserver));
  }
  
  void internalInit(SupportSQLiteDatabase paramSupportSQLiteDatabase) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mInitialized : Z
    //   6: ifeq -> 21
    //   9: ldc 'ROOM'
    //   11: ldc_w 'Invalidation tracker is initialized twice :/.'
    //   14: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   17: pop
    //   18: aload_0
    //   19: monitorexit
    //   20: return
    //   21: aload_1
    //   22: invokeinterface beginTransaction : ()V
    //   27: aload_1
    //   28: ldc_w 'PRAGMA temp_store = MEMORY;'
    //   31: invokeinterface execSQL : (Ljava/lang/String;)V
    //   36: aload_1
    //   37: ldc_w 'PRAGMA recursive_triggers='ON';'
    //   40: invokeinterface execSQL : (Ljava/lang/String;)V
    //   45: aload_1
    //   46: ldc 'CREATE TEMP TABLE room_table_modification_log(version INTEGER PRIMARY KEY AUTOINCREMENT, table_id INTEGER)'
    //   48: invokeinterface execSQL : (Ljava/lang/String;)V
    //   53: aload_1
    //   54: invokeinterface setTransactionSuccessful : ()V
    //   59: aload_1
    //   60: invokeinterface endTransaction : ()V
    //   65: aload_0
    //   66: aload_1
    //   67: invokevirtual syncTriggers : (Landroid/arch/persistence/db/SupportSQLiteDatabase;)V
    //   70: aload_0
    //   71: aload_1
    //   72: ldc 'DELETE FROM room_table_modification_log WHERE version NOT IN( SELECT MAX(version) FROM room_table_modification_log GROUP BY table_id)'
    //   74: invokeinterface compileStatement : (Ljava/lang/String;)Landroid/arch/persistence/db/SupportSQLiteStatement;
    //   79: putfield mCleanupStatement : Landroid/arch/persistence/db/SupportSQLiteStatement;
    //   82: aload_0
    //   83: iconst_1
    //   84: putfield mInitialized : Z
    //   87: aload_0
    //   88: monitorexit
    //   89: return
    //   90: astore_2
    //   91: aload_1
    //   92: invokeinterface endTransaction : ()V
    //   97: aload_2
    //   98: athrow
    //   99: astore_1
    //   100: aload_0
    //   101: monitorexit
    //   102: aload_1
    //   103: athrow
    // Exception table:
    //   from	to	target	type
    //   2	20	99	finally
    //   21	27	99	finally
    //   27	59	90	finally
    //   59	89	99	finally
    //   91	99	99	finally
    //   100	102	99	finally
  }
  
  public void refreshVersionsAsync() {
    if (this.mPendingRefresh.compareAndSet(false, true))
      ArchTaskExecutor.getInstance().executeOnDiskIO(this.mRefreshRunnable); 
  }
  
  public void refreshVersionsSync() {
    syncTriggers();
    this.mRefreshRunnable.run();
  }
  
  public void removeObserver(Observer paramObserver) {
    synchronized (this.mObserverMap) {
      ObserverWrapper observerWrapper = (ObserverWrapper)this.mObserverMap.remove(paramObserver);
      if (observerWrapper != null && this.mObservedTableTracker.onRemoved(observerWrapper.mTableIds))
        syncTriggers(); 
      return;
    } 
  }
  
  void syncTriggers() {
    if (!this.mDatabase.isOpen())
      return; 
    syncTriggers(this.mDatabase.getOpenHelper().getWritableDatabase());
  }
  
  void syncTriggers(SupportSQLiteDatabase paramSupportSQLiteDatabase) {
    if (paramSupportSQLiteDatabase.inTransaction())
      return; 
    try {
      while (true) {
        Lock lock = this.mDatabase.getCloseLock();
        lock.lock();
        try {
          null = this.mObservedTableTracker.getTablesToSync();
          if (null == null)
            return; 
          int i = null.length;
        } finally {
          lock.unlock();
        } 
      } 
    } catch (IllegalStateException illegalStateException) {
    
    } catch (SQLiteException sQLiteException) {}
    Log.e("ROOM", "Cannot run invalidation tracker. Is the db closed?", (Throwable)sQLiteException);
  }
  
  static class ObservedTableTracker {
    static final int ADD = 1;
    
    static final int NO_OP = 0;
    
    static final int REMOVE = 2;
    
    boolean mNeedsSync;
    
    boolean mPendingSync;
    
    final long[] mTableObservers;
    
    final int[] mTriggerStateChanges;
    
    final boolean[] mTriggerStates;
    
    ObservedTableTracker(int param1Int) {
      long[] arrayOfLong = new long[param1Int];
      this.mTableObservers = arrayOfLong;
      this.mTriggerStates = new boolean[param1Int];
      this.mTriggerStateChanges = new int[param1Int];
      Arrays.fill(arrayOfLong, 0L);
      Arrays.fill(this.mTriggerStates, false);
    }
    
    int[] getTablesToSync() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield mNeedsSync : Z
      //   6: ifeq -> 131
      //   9: aload_0
      //   10: getfield mPendingSync : Z
      //   13: ifeq -> 19
      //   16: goto -> 131
      //   19: aload_0
      //   20: getfield mTableObservers : [J
      //   23: arraylength
      //   24: istore_1
      //   25: iconst_0
      //   26: istore_2
      //   27: iconst_1
      //   28: istore_3
      //   29: iload_2
      //   30: iload_1
      //   31: if_icmpge -> 110
      //   34: aload_0
      //   35: getfield mTableObservers : [J
      //   38: iload_2
      //   39: laload
      //   40: lconst_0
      //   41: lcmp
      //   42: ifle -> 51
      //   45: iconst_1
      //   46: istore #4
      //   48: goto -> 54
      //   51: iconst_0
      //   52: istore #4
      //   54: iload #4
      //   56: aload_0
      //   57: getfield mTriggerStates : [Z
      //   60: iload_2
      //   61: baload
      //   62: if_icmpeq -> 89
      //   65: aload_0
      //   66: getfield mTriggerStateChanges : [I
      //   69: astore #5
      //   71: iload #4
      //   73: ifeq -> 79
      //   76: goto -> 81
      //   79: iconst_2
      //   80: istore_3
      //   81: aload #5
      //   83: iload_2
      //   84: iload_3
      //   85: iastore
      //   86: goto -> 96
      //   89: aload_0
      //   90: getfield mTriggerStateChanges : [I
      //   93: iload_2
      //   94: iconst_0
      //   95: iastore
      //   96: aload_0
      //   97: getfield mTriggerStates : [Z
      //   100: iload_2
      //   101: iload #4
      //   103: bastore
      //   104: iinc #2, 1
      //   107: goto -> 27
      //   110: aload_0
      //   111: iconst_1
      //   112: putfield mPendingSync : Z
      //   115: aload_0
      //   116: iconst_0
      //   117: putfield mNeedsSync : Z
      //   120: aload_0
      //   121: getfield mTriggerStateChanges : [I
      //   124: astore #5
      //   126: aload_0
      //   127: monitorexit
      //   128: aload #5
      //   130: areturn
      //   131: aload_0
      //   132: monitorexit
      //   133: aconst_null
      //   134: areturn
      //   135: astore #5
      //   137: aload_0
      //   138: monitorexit
      //   139: aload #5
      //   141: athrow
      // Exception table:
      //   from	to	target	type
      //   2	16	135	finally
      //   19	25	135	finally
      //   34	45	135	finally
      //   54	71	135	finally
      //   89	96	135	finally
      //   96	104	135	finally
      //   110	128	135	finally
      //   131	133	135	finally
      //   137	139	135	finally
    }
    
    boolean onAdded(int... param1VarArgs) {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_1
      //   3: arraylength
      //   4: istore_2
      //   5: iconst_0
      //   6: istore_3
      //   7: iconst_0
      //   8: istore #4
      //   10: iload_3
      //   11: iload_2
      //   12: if_icmpge -> 61
      //   15: aload_1
      //   16: iload_3
      //   17: iaload
      //   18: istore #5
      //   20: aload_0
      //   21: getfield mTableObservers : [J
      //   24: iload #5
      //   26: laload
      //   27: lstore #6
      //   29: aload_0
      //   30: getfield mTableObservers : [J
      //   33: iload #5
      //   35: lconst_1
      //   36: lload #6
      //   38: ladd
      //   39: lastore
      //   40: lload #6
      //   42: lconst_0
      //   43: lcmp
      //   44: ifne -> 55
      //   47: aload_0
      //   48: iconst_1
      //   49: putfield mNeedsSync : Z
      //   52: iconst_1
      //   53: istore #4
      //   55: iinc #3, 1
      //   58: goto -> 10
      //   61: aload_0
      //   62: monitorexit
      //   63: iload #4
      //   65: ireturn
      //   66: astore_1
      //   67: aload_0
      //   68: monitorexit
      //   69: aload_1
      //   70: athrow
      // Exception table:
      //   from	to	target	type
      //   2	5	66	finally
      //   20	40	66	finally
      //   47	52	66	finally
      //   61	63	66	finally
      //   67	69	66	finally
    }
    
    boolean onRemoved(int... param1VarArgs) {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_1
      //   3: arraylength
      //   4: istore_2
      //   5: iconst_0
      //   6: istore_3
      //   7: iconst_0
      //   8: istore #4
      //   10: iload_3
      //   11: iload_2
      //   12: if_icmpge -> 61
      //   15: aload_1
      //   16: iload_3
      //   17: iaload
      //   18: istore #5
      //   20: aload_0
      //   21: getfield mTableObservers : [J
      //   24: iload #5
      //   26: laload
      //   27: lstore #6
      //   29: aload_0
      //   30: getfield mTableObservers : [J
      //   33: iload #5
      //   35: lload #6
      //   37: lconst_1
      //   38: lsub
      //   39: lastore
      //   40: lload #6
      //   42: lconst_1
      //   43: lcmp
      //   44: ifne -> 55
      //   47: aload_0
      //   48: iconst_1
      //   49: putfield mNeedsSync : Z
      //   52: iconst_1
      //   53: istore #4
      //   55: iinc #3, 1
      //   58: goto -> 10
      //   61: aload_0
      //   62: monitorexit
      //   63: iload #4
      //   65: ireturn
      //   66: astore_1
      //   67: aload_0
      //   68: monitorexit
      //   69: aload_1
      //   70: athrow
      // Exception table:
      //   from	to	target	type
      //   2	5	66	finally
      //   20	40	66	finally
      //   47	52	66	finally
      //   61	63	66	finally
      //   67	69	66	finally
    }
    
    void onSyncCompleted() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: iconst_0
      //   4: putfield mPendingSync : Z
      //   7: aload_0
      //   8: monitorexit
      //   9: return
      //   10: astore_1
      //   11: aload_0
      //   12: monitorexit
      //   13: aload_1
      //   14: athrow
      // Exception table:
      //   from	to	target	type
      //   2	9	10	finally
      //   11	13	10	finally
    }
  }
  
  public static abstract class Observer {
    final String[] mTables;
    
    protected Observer(String param1String, String... param1VarArgs) {
      String[] arrayOfString = Arrays.<String>copyOf(param1VarArgs, param1VarArgs.length + 1);
      this.mTables = arrayOfString;
      arrayOfString[param1VarArgs.length] = param1String;
    }
    
    public Observer(String[] param1ArrayOfString) {
      this.mTables = Arrays.<String>copyOf(param1ArrayOfString, param1ArrayOfString.length);
    }
    
    public abstract void onInvalidated(Set<String> param1Set);
  }
  
  static class ObserverWrapper {
    final InvalidationTracker.Observer mObserver;
    
    private final Set<String> mSingleTableSet;
    
    final int[] mTableIds;
    
    private final String[] mTableNames;
    
    private final long[] mVersions;
    
    ObserverWrapper(InvalidationTracker.Observer param1Observer, int[] param1ArrayOfint, String[] param1ArrayOfString, long[] param1ArrayOflong) {
      this.mObserver = param1Observer;
      this.mTableIds = param1ArrayOfint;
      this.mTableNames = param1ArrayOfString;
      this.mVersions = param1ArrayOflong;
      if (param1ArrayOfint.length == 1) {
        ArraySet arraySet = new ArraySet();
        arraySet.add(this.mTableNames[0]);
        this.mSingleTableSet = Collections.unmodifiableSet((Set<? extends String>)arraySet);
      } else {
        this.mSingleTableSet = null;
      } 
    }
    
    void checkForInvalidation(long[] param1ArrayOflong) {
      ArraySet<String> arraySet;
      int i = this.mTableIds.length;
      Set<String> set = null;
      byte b = 0;
      while (b < i) {
        ArraySet<String> arraySet1;
        long l = param1ArrayOflong[this.mTableIds[b]];
        long[] arrayOfLong = this.mVersions;
        Set<String> set1 = set;
        if (arrayOfLong[b] < l) {
          arrayOfLong[b] = l;
          if (i == 1) {
            set1 = this.mSingleTableSet;
          } else {
            set1 = set;
            if (set == null)
              arraySet1 = new ArraySet(i); 
            arraySet1.add(this.mTableNames[b]);
          } 
        } 
        b++;
        arraySet = arraySet1;
      } 
      if (arraySet != null)
        this.mObserver.onInvalidated((Set<String>)arraySet); 
    }
  }
  
  static class WeakObserver extends Observer {
    final WeakReference<InvalidationTracker.Observer> mDelegateRef;
    
    final InvalidationTracker mTracker;
    
    WeakObserver(InvalidationTracker param1InvalidationTracker, InvalidationTracker.Observer param1Observer) {
      super(param1Observer.mTables);
      this.mTracker = param1InvalidationTracker;
      this.mDelegateRef = new WeakReference<InvalidationTracker.Observer>(param1Observer);
    }
    
    public void onInvalidated(Set<String> param1Set) {
      InvalidationTracker.Observer observer = this.mDelegateRef.get();
      if (observer == null) {
        this.mTracker.removeObserver(this);
      } else {
        observer.onInvalidated(param1Set);
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\room\InvalidationTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */