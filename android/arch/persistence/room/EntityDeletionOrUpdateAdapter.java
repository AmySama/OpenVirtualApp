package android.arch.persistence.room;

import android.arch.persistence.db.SupportSQLiteStatement;
import java.util.Iterator;

public abstract class EntityDeletionOrUpdateAdapter<T> extends SharedSQLiteStatement {
  public EntityDeletionOrUpdateAdapter(RoomDatabase paramRoomDatabase) {
    super(paramRoomDatabase);
  }
  
  protected abstract void bind(SupportSQLiteStatement paramSupportSQLiteStatement, T paramT);
  
  protected abstract String createQuery();
  
  public final int handle(T paramT) {
    SupportSQLiteStatement supportSQLiteStatement = acquire();
    try {
      bind(supportSQLiteStatement, paramT);
      return supportSQLiteStatement.executeUpdateDelete();
    } finally {
      release(supportSQLiteStatement);
    } 
  }
  
  public final int handleMultiple(Iterable<T> paramIterable) {
    SupportSQLiteStatement supportSQLiteStatement = acquire();
    int i = 0;
    try {
      Iterator<T> iterator = paramIterable.iterator();
      while (iterator.hasNext()) {
        bind(supportSQLiteStatement, iterator.next());
        int j = supportSQLiteStatement.executeUpdateDelete();
        i += j;
      } 
      return i;
    } finally {
      release(supportSQLiteStatement);
    } 
  }
  
  public final int handleMultiple(T[] paramArrayOfT) {
    SupportSQLiteStatement supportSQLiteStatement = acquire();
    try {
      int i = paramArrayOfT.length;
      byte b = 0;
      int j = 0;
      while (b < i) {
        bind(supportSQLiteStatement, paramArrayOfT[b]);
        int k = supportSQLiteStatement.executeUpdateDelete();
        j += k;
        b++;
      } 
      return j;
    } finally {
      release(supportSQLiteStatement);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\room\EntityDeletionOrUpdateAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */