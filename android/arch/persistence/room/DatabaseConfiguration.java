package android.arch.persistence.room;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.content.Context;
import java.util.List;
import java.util.Set;

public class DatabaseConfiguration {
  public final boolean allowMainThreadQueries;
  
  public final List<RoomDatabase.Callback> callbacks;
  
  public final Context context;
  
  public final RoomDatabase.JournalMode journalMode;
  
  private final Set<Integer> mMigrationNotRequiredFrom;
  
  public final RoomDatabase.MigrationContainer migrationContainer;
  
  public final String name;
  
  public final boolean requireMigration;
  
  public final SupportSQLiteOpenHelper.Factory sqliteOpenHelperFactory;
  
  public DatabaseConfiguration(Context paramContext, String paramString, SupportSQLiteOpenHelper.Factory paramFactory, RoomDatabase.MigrationContainer paramMigrationContainer, List<RoomDatabase.Callback> paramList, boolean paramBoolean1, RoomDatabase.JournalMode paramJournalMode, boolean paramBoolean2, Set<Integer> paramSet) {
    this.sqliteOpenHelperFactory = paramFactory;
    this.context = paramContext;
    this.name = paramString;
    this.migrationContainer = paramMigrationContainer;
    this.callbacks = paramList;
    this.allowMainThreadQueries = paramBoolean1;
    this.journalMode = paramJournalMode;
    this.requireMigration = paramBoolean2;
    this.mMigrationNotRequiredFrom = paramSet;
  }
  
  public boolean isMigrationRequiredFrom(int paramInt) {
    if (this.requireMigration) {
      Set<Integer> set = this.mMigrationNotRequiredFrom;
      if (set == null || !set.contains(Integer.valueOf(paramInt)))
        return true; 
    } 
    return false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\room\DatabaseConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */