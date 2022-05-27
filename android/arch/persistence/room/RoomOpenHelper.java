package android.arch.persistence.room;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteQuery;
import android.database.Cursor;

public class RoomOpenHelper extends SupportSQLiteOpenHelper.Callback {
  private DatabaseConfiguration mConfiguration;
  
  private final Delegate mDelegate;
  
  private final String mIdentityHash;
  
  private final String mLegacyHash;
  
  public RoomOpenHelper(DatabaseConfiguration paramDatabaseConfiguration, Delegate paramDelegate, String paramString) {
    this(paramDatabaseConfiguration, paramDelegate, "", paramString);
  }
  
  public RoomOpenHelper(DatabaseConfiguration paramDatabaseConfiguration, Delegate paramDelegate, String paramString1, String paramString2) {
    super(paramDelegate.version);
    this.mConfiguration = paramDatabaseConfiguration;
    this.mDelegate = paramDelegate;
    this.mIdentityHash = paramString1;
    this.mLegacyHash = paramString2;
  }
  
  private void checkIdentity(SupportSQLiteDatabase paramSupportSQLiteDatabase) {
    String str;
    boolean bool = hasRoomMasterTable(paramSupportSQLiteDatabase);
    Cursor cursor = null;
    SupportSQLiteDatabase supportSQLiteDatabase = null;
    if (bool) {
      cursor = paramSupportSQLiteDatabase.query((SupportSQLiteQuery)new SimpleSQLiteQuery("SELECT identity_hash FROM room_master_table WHERE id = 42 LIMIT 1"));
      paramSupportSQLiteDatabase = supportSQLiteDatabase;
      try {
        String str1;
        if (cursor.moveToFirst())
          str1 = cursor.getString(0); 
        cursor.close();
      } finally {
        str.close();
      } 
    } 
    if (this.mIdentityHash.equals(str) || this.mLegacyHash.equals(str))
      return; 
    throw new IllegalStateException("Room cannot verify the data integrity. Looks like you've changed schema but forgot to update the version number. You can simply fix this by increasing the version number.");
  }
  
  private void createMasterTableIfNotExists(SupportSQLiteDatabase paramSupportSQLiteDatabase) {
    paramSupportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
  }
  
  private static boolean hasRoomMasterTable(SupportSQLiteDatabase paramSupportSQLiteDatabase) {
    Cursor cursor = paramSupportSQLiteDatabase.query("SELECT 1 FROM sqlite_master WHERE type = 'table' AND name='room_master_table'");
    try {
      boolean bool = cursor.moveToFirst();
      boolean bool1 = false;
      boolean bool2 = bool1;
      if (bool) {
        int i = cursor.getInt(0);
        bool2 = bool1;
        if (i != 0)
          bool2 = true; 
      } 
      return bool2;
    } finally {
      cursor.close();
    } 
  }
  
  private void updateIdentity(SupportSQLiteDatabase paramSupportSQLiteDatabase) {
    createMasterTableIfNotExists(paramSupportSQLiteDatabase);
    paramSupportSQLiteDatabase.execSQL(RoomMasterTable.createInsertQuery(this.mIdentityHash));
  }
  
  public void onConfigure(SupportSQLiteDatabase paramSupportSQLiteDatabase) {
    super.onConfigure(paramSupportSQLiteDatabase);
  }
  
  public void onCreate(SupportSQLiteDatabase paramSupportSQLiteDatabase) {
    updateIdentity(paramSupportSQLiteDatabase);
    this.mDelegate.createAllTables(paramSupportSQLiteDatabase);
    this.mDelegate.onCreate(paramSupportSQLiteDatabase);
  }
  
  public void onDowngrade(SupportSQLiteDatabase paramSupportSQLiteDatabase, int paramInt1, int paramInt2) {
    onUpgrade(paramSupportSQLiteDatabase, paramInt1, paramInt2);
  }
  
  public void onOpen(SupportSQLiteDatabase paramSupportSQLiteDatabase) {
    super.onOpen(paramSupportSQLiteDatabase);
    checkIdentity(paramSupportSQLiteDatabase);
    this.mDelegate.onOpen(paramSupportSQLiteDatabase);
    this.mConfiguration = null;
  }
  
  public void onUpgrade(SupportSQLiteDatabase paramSupportSQLiteDatabase, int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mConfiguration : Landroid/arch/persistence/room/DatabaseConfiguration;
    //   4: astore #4
    //   6: aload #4
    //   8: ifnull -> 83
    //   11: aload #4
    //   13: getfield migrationContainer : Landroid/arch/persistence/room/RoomDatabase$MigrationContainer;
    //   16: iload_2
    //   17: iload_3
    //   18: invokevirtual findMigrationPath : (II)Ljava/util/List;
    //   21: astore #4
    //   23: aload #4
    //   25: ifnull -> 83
    //   28: aload #4
    //   30: invokeinterface iterator : ()Ljava/util/Iterator;
    //   35: astore #4
    //   37: aload #4
    //   39: invokeinterface hasNext : ()Z
    //   44: ifeq -> 64
    //   47: aload #4
    //   49: invokeinterface next : ()Ljava/lang/Object;
    //   54: checkcast android/arch/persistence/room/migration/Migration
    //   57: aload_1
    //   58: invokevirtual migrate : (Landroid/arch/persistence/db/SupportSQLiteDatabase;)V
    //   61: goto -> 37
    //   64: aload_0
    //   65: getfield mDelegate : Landroid/arch/persistence/room/RoomOpenHelper$Delegate;
    //   68: aload_1
    //   69: invokevirtual validateMigration : (Landroid/arch/persistence/db/SupportSQLiteDatabase;)V
    //   72: aload_0
    //   73: aload_1
    //   74: invokespecial updateIdentity : (Landroid/arch/persistence/db/SupportSQLiteDatabase;)V
    //   77: iconst_1
    //   78: istore #5
    //   80: goto -> 86
    //   83: iconst_0
    //   84: istore #5
    //   86: iload #5
    //   88: ifne -> 211
    //   91: aload_0
    //   92: getfield mConfiguration : Landroid/arch/persistence/room/DatabaseConfiguration;
    //   95: astore #4
    //   97: aload #4
    //   99: ifnull -> 130
    //   102: aload #4
    //   104: iload_2
    //   105: invokevirtual isMigrationRequiredFrom : (I)Z
    //   108: ifne -> 130
    //   111: aload_0
    //   112: getfield mDelegate : Landroid/arch/persistence/room/RoomOpenHelper$Delegate;
    //   115: aload_1
    //   116: invokevirtual dropAllTables : (Landroid/arch/persistence/db/SupportSQLiteDatabase;)V
    //   119: aload_0
    //   120: getfield mDelegate : Landroid/arch/persistence/room/RoomOpenHelper$Delegate;
    //   123: aload_1
    //   124: invokevirtual createAllTables : (Landroid/arch/persistence/db/SupportSQLiteDatabase;)V
    //   127: goto -> 211
    //   130: new java/lang/StringBuilder
    //   133: dup
    //   134: invokespecial <init> : ()V
    //   137: astore_1
    //   138: aload_1
    //   139: ldc 'A migration from '
    //   141: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   144: pop
    //   145: aload_1
    //   146: iload_2
    //   147: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   150: pop
    //   151: aload_1
    //   152: ldc ' to '
    //   154: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   157: pop
    //   158: aload_1
    //   159: iload_3
    //   160: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   163: pop
    //   164: aload_1
    //   165: ldc ' was required but not found. Please provide the '
    //   167: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   170: pop
    //   171: aload_1
    //   172: ldc 'necessary Migration path via '
    //   174: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   177: pop
    //   178: aload_1
    //   179: ldc 'RoomDatabase.Builder.addMigration(Migration ...) or allow for '
    //   181: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   184: pop
    //   185: aload_1
    //   186: ldc 'destructive migrations via one of the '
    //   188: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   191: pop
    //   192: aload_1
    //   193: ldc 'RoomDatabase.Builder.fallbackToDestructiveMigration* methods.'
    //   195: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   198: pop
    //   199: new java/lang/IllegalStateException
    //   202: dup
    //   203: aload_1
    //   204: invokevirtual toString : ()Ljava/lang/String;
    //   207: invokespecial <init> : (Ljava/lang/String;)V
    //   210: athrow
    //   211: return
  }
  
  public static abstract class Delegate {
    public final int version;
    
    public Delegate(int param1Int) {
      this.version = param1Int;
    }
    
    protected abstract void createAllTables(SupportSQLiteDatabase param1SupportSQLiteDatabase);
    
    protected abstract void dropAllTables(SupportSQLiteDatabase param1SupportSQLiteDatabase);
    
    protected abstract void onCreate(SupportSQLiteDatabase param1SupportSQLiteDatabase);
    
    protected abstract void onOpen(SupportSQLiteDatabase param1SupportSQLiteDatabase);
    
    protected abstract void validateMigration(SupportSQLiteDatabase param1SupportSQLiteDatabase);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\room\RoomOpenHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */