package android.arch.persistence.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.util.Log;
import android.util.Pair;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public interface SupportSQLiteOpenHelper {
  void close();
  
  String getDatabaseName();
  
  SupportSQLiteDatabase getReadableDatabase();
  
  SupportSQLiteDatabase getWritableDatabase();
  
  void setWriteAheadLoggingEnabled(boolean paramBoolean);
  
  public static abstract class Callback {
    private static final String TAG = "SupportSQLite";
    
    public final int version;
    
    public Callback(int param1Int) {
      this.version = param1Int;
    }
    
    private void deleteDatabaseFile(String param1String) {
      if (!param1String.equalsIgnoreCase(":memory:") && param1String.trim().length() != 0) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("deleting the database file: ");
        stringBuilder.append(param1String);
        Log.w("SupportSQLite", stringBuilder.toString());
        try {
          if (Build.VERSION.SDK_INT >= 16) {
            File file = new File();
            this(param1String);
            SQLiteDatabase.deleteDatabase(file);
          } else {
            try {
              File file = new File();
              this(param1String);
              if (!file.delete()) {
                StringBuilder stringBuilder1 = new StringBuilder();
                this();
                stringBuilder1.append("Could not delete the database file ");
                stringBuilder1.append(param1String);
                Log.e("SupportSQLite", stringBuilder1.toString());
              } 
            } catch (Exception exception) {
              Log.e("SupportSQLite", "error while deleting corrupted database file", exception);
            } 
          } 
        } catch (Exception exception) {
          Log.w("SupportSQLite", "delete failed: ", exception);
        } 
      } 
    }
    
    public void onConfigure(SupportSQLiteDatabase param1SupportSQLiteDatabase) {}
    
    public void onCorruption(SupportSQLiteDatabase param1SupportSQLiteDatabase) {
      Iterator iterator;
      null = new StringBuilder();
      null.append("Corruption reported by sqlite on database: ");
      null.append(param1SupportSQLiteDatabase.getPath());
      Log.e("SupportSQLite", null.toString());
      if (!param1SupportSQLiteDatabase.isOpen()) {
        deleteDatabaseFile(param1SupportSQLiteDatabase.getPath());
        return;
      } 
      null = null;
      sQLiteException = null;
      try {
        List<Pair<String, String>> list2 = param1SupportSQLiteDatabase.getAttachedDbs();
      } catch (SQLiteException sQLiteException) {
      
      } finally {
        if (sQLiteException != null) {
          iterator = sQLiteException.iterator();
          while (iterator.hasNext())
            deleteDatabaseFile((String)((Pair)iterator.next()).second); 
        } else {
          deleteDatabaseFile(iterator.getPath());
        } 
      } 
      Object object = SYNTHETIC_LOCAL_VARIABLE_2;
      try {
        iterator.close();
      } catch (IOException iOException) {}
      if (SYNTHETIC_LOCAL_VARIABLE_2 != null) {
        iterator = SYNTHETIC_LOCAL_VARIABLE_2.iterator();
        while (iterator.hasNext())
          deleteDatabaseFile((String)((Pair)iterator.next()).second); 
      } else {
        deleteDatabaseFile(iterator.getPath());
      } 
    }
    
    public abstract void onCreate(SupportSQLiteDatabase param1SupportSQLiteDatabase);
    
    public void onDowngrade(SupportSQLiteDatabase param1SupportSQLiteDatabase, int param1Int1, int param1Int2) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Can't downgrade database from version ");
      stringBuilder.append(param1Int1);
      stringBuilder.append(" to ");
      stringBuilder.append(param1Int2);
      throw new SQLiteException(stringBuilder.toString());
    }
    
    public void onOpen(SupportSQLiteDatabase param1SupportSQLiteDatabase) {}
    
    public abstract void onUpgrade(SupportSQLiteDatabase param1SupportSQLiteDatabase, int param1Int1, int param1Int2);
  }
  
  public static class Configuration {
    public final SupportSQLiteOpenHelper.Callback callback;
    
    public final Context context;
    
    public final String name;
    
    Configuration(Context param1Context, String param1String, SupportSQLiteOpenHelper.Callback param1Callback) {
      this.context = param1Context;
      this.name = param1String;
      this.callback = param1Callback;
    }
    
    public static Builder builder(Context param1Context) {
      return new Builder(param1Context);
    }
    
    public static class Builder {
      SupportSQLiteOpenHelper.Callback mCallback;
      
      Context mContext;
      
      String mName;
      
      Builder(Context param2Context) {
        this.mContext = param2Context;
      }
      
      public SupportSQLiteOpenHelper.Configuration build() {
        SupportSQLiteOpenHelper.Callback callback = this.mCallback;
        if (callback != null) {
          Context context = this.mContext;
          if (context != null)
            return new SupportSQLiteOpenHelper.Configuration(context, this.mName, callback); 
          throw new IllegalArgumentException("Must set a non-null context to create the configuration.");
        } 
        throw new IllegalArgumentException("Must set a callback to create the configuration.");
      }
      
      public Builder callback(SupportSQLiteOpenHelper.Callback param2Callback) {
        this.mCallback = param2Callback;
        return this;
      }
      
      public Builder name(String param2String) {
        this.mName = param2String;
        return this;
      }
    }
  }
  
  public static class Builder {
    SupportSQLiteOpenHelper.Callback mCallback;
    
    Context mContext;
    
    String mName;
    
    Builder(Context param1Context) {
      this.mContext = param1Context;
    }
    
    public SupportSQLiteOpenHelper.Configuration build() {
      SupportSQLiteOpenHelper.Callback callback = this.mCallback;
      if (callback != null) {
        Context context = this.mContext;
        if (context != null)
          return new SupportSQLiteOpenHelper.Configuration(context, this.mName, callback); 
        throw new IllegalArgumentException("Must set a non-null context to create the configuration.");
      } 
      throw new IllegalArgumentException("Must set a callback to create the configuration.");
    }
    
    public Builder callback(SupportSQLiteOpenHelper.Callback param1Callback) {
      this.mCallback = param1Callback;
      return this;
    }
    
    public Builder name(String param1String) {
      this.mName = param1String;
      return this;
    }
  }
  
  public static interface Factory {
    SupportSQLiteOpenHelper create(SupportSQLiteOpenHelper.Configuration param1Configuration);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\db\SupportSQLiteOpenHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */