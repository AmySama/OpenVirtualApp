package android.arch.persistence.room;

import android.arch.persistence.db.SupportSQLiteStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class EntityInsertionAdapter<T> extends SharedSQLiteStatement {
  public EntityInsertionAdapter(RoomDatabase paramRoomDatabase) {
    super(paramRoomDatabase);
  }
  
  protected abstract void bind(SupportSQLiteStatement paramSupportSQLiteStatement, T paramT);
  
  public final void insert(Iterable<T> paramIterable) {
    SupportSQLiteStatement supportSQLiteStatement = acquire();
    try {
      Iterator<T> iterator = paramIterable.iterator();
      while (iterator.hasNext()) {
        bind(supportSQLiteStatement, iterator.next());
        supportSQLiteStatement.executeInsert();
      } 
      return;
    } finally {
      release(supportSQLiteStatement);
    } 
  }
  
  public final void insert(T paramT) {
    SupportSQLiteStatement supportSQLiteStatement = acquire();
    try {
      bind(supportSQLiteStatement, paramT);
      supportSQLiteStatement.executeInsert();
      return;
    } finally {
      release(supportSQLiteStatement);
    } 
  }
  
  public final void insert(T[] paramArrayOfT) {
    SupportSQLiteStatement supportSQLiteStatement = acquire();
    try {
      int i = paramArrayOfT.length;
      for (byte b = 0; b < i; b++) {
        bind(supportSQLiteStatement, paramArrayOfT[b]);
        supportSQLiteStatement.executeInsert();
      } 
      return;
    } finally {
      release(supportSQLiteStatement);
    } 
  }
  
  public final long insertAndReturnId(T paramT) {
    SupportSQLiteStatement supportSQLiteStatement = acquire();
    try {
      bind(supportSQLiteStatement, paramT);
      return supportSQLiteStatement.executeInsert();
    } finally {
      release(supportSQLiteStatement);
    } 
  }
  
  public final long[] insertAndReturnIdsArray(Collection<T> paramCollection) {
    SupportSQLiteStatement supportSQLiteStatement = acquire();
    try {
      long[] arrayOfLong = new long[paramCollection.size()];
      byte b = 0;
      Iterator<T> iterator = paramCollection.iterator();
      while (iterator.hasNext()) {
        bind(supportSQLiteStatement, iterator.next());
        arrayOfLong[b] = supportSQLiteStatement.executeInsert();
        b++;
      } 
      return arrayOfLong;
    } finally {
      release(supportSQLiteStatement);
    } 
  }
  
  public final long[] insertAndReturnIdsArray(T[] paramArrayOfT) {
    SupportSQLiteStatement supportSQLiteStatement = acquire();
    try {
      long[] arrayOfLong = new long[paramArrayOfT.length];
      int i = paramArrayOfT.length;
      byte b1 = 0;
      byte b2 = 0;
      while (b1 < i) {
        bind(supportSQLiteStatement, paramArrayOfT[b1]);
        arrayOfLong[b2] = supportSQLiteStatement.executeInsert();
        b2++;
        b1++;
      } 
      return arrayOfLong;
    } finally {
      release(supportSQLiteStatement);
    } 
  }
  
  public final Long[] insertAndReturnIdsArrayBox(Collection<T> paramCollection) {
    SupportSQLiteStatement supportSQLiteStatement = acquire();
    try {
      Long[] arrayOfLong = new Long[paramCollection.size()];
      byte b = 0;
      Iterator<T> iterator = paramCollection.iterator();
      while (iterator.hasNext()) {
        bind(supportSQLiteStatement, iterator.next());
        arrayOfLong[b] = Long.valueOf(supportSQLiteStatement.executeInsert());
        b++;
      } 
      return arrayOfLong;
    } finally {
      release(supportSQLiteStatement);
    } 
  }
  
  public final Long[] insertAndReturnIdsArrayBox(T[] paramArrayOfT) {
    SupportSQLiteStatement supportSQLiteStatement = acquire();
    try {
      Long[] arrayOfLong = new Long[paramArrayOfT.length];
      int i = paramArrayOfT.length;
      byte b1 = 0;
      byte b2 = 0;
      while (b1 < i) {
        bind(supportSQLiteStatement, paramArrayOfT[b1]);
        arrayOfLong[b2] = Long.valueOf(supportSQLiteStatement.executeInsert());
        b2++;
        b1++;
      } 
      return arrayOfLong;
    } finally {
      release(supportSQLiteStatement);
    } 
  }
  
  public final List<Long> insertAndReturnIdsList(Collection<T> paramCollection) {
    SupportSQLiteStatement supportSQLiteStatement = acquire();
    try {
      ArrayList<Long> arrayList = new ArrayList();
      this(paramCollection.size());
      byte b = 0;
      Iterator<T> iterator = paramCollection.iterator();
      while (iterator.hasNext()) {
        bind(supportSQLiteStatement, iterator.next());
        arrayList.add(b, Long.valueOf(supportSQLiteStatement.executeInsert()));
        b++;
      } 
      return arrayList;
    } finally {
      release(supportSQLiteStatement);
    } 
  }
  
  public final List<Long> insertAndReturnIdsList(T[] paramArrayOfT) {
    SupportSQLiteStatement supportSQLiteStatement = acquire();
    try {
      ArrayList<Long> arrayList = new ArrayList();
      this(paramArrayOfT.length);
      int i = paramArrayOfT.length;
      byte b1 = 0;
      byte b2 = 0;
      while (b1 < i) {
        bind(supportSQLiteStatement, paramArrayOfT[b1]);
        arrayList.add(b2, Long.valueOf(supportSQLiteStatement.executeInsert()));
        b2++;
        b1++;
      } 
      return arrayList;
    } finally {
      release(supportSQLiteStatement);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\room\EntityInsertionAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */