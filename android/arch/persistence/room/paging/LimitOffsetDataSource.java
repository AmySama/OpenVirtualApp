package android.arch.persistence.room.paging;

import android.arch.paging.PositionalDataSource;
import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class LimitOffsetDataSource<T> extends PositionalDataSource<T> {
  private final String mCountQuery;
  
  private final RoomDatabase mDb;
  
  private final boolean mInTransaction;
  
  private final String mLimitOffsetQuery;
  
  private final InvalidationTracker.Observer mObserver;
  
  private final RoomSQLiteQuery mSourceQuery;
  
  protected LimitOffsetDataSource(RoomDatabase paramRoomDatabase, SupportSQLiteQuery paramSupportSQLiteQuery, boolean paramBoolean, String... paramVarArgs) {
    this(paramRoomDatabase, RoomSQLiteQuery.copyFrom(paramSupportSQLiteQuery), paramBoolean, paramVarArgs);
  }
  
  protected LimitOffsetDataSource(RoomDatabase paramRoomDatabase, RoomSQLiteQuery paramRoomSQLiteQuery, boolean paramBoolean, String... paramVarArgs) {
    this.mDb = paramRoomDatabase;
    this.mSourceQuery = paramRoomSQLiteQuery;
    this.mInTransaction = paramBoolean;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("SELECT COUNT(*) FROM ( ");
    stringBuilder.append(this.mSourceQuery.getSql());
    stringBuilder.append(" )");
    this.mCountQuery = stringBuilder.toString();
    stringBuilder = new StringBuilder();
    stringBuilder.append("SELECT * FROM ( ");
    stringBuilder.append(this.mSourceQuery.getSql());
    stringBuilder.append(" ) LIMIT ? OFFSET ?");
    this.mLimitOffsetQuery = stringBuilder.toString();
    this.mObserver = new InvalidationTracker.Observer(paramVarArgs) {
        public void onInvalidated(Set<String> param1Set) {
          LimitOffsetDataSource.this.invalidate();
        }
      };
    paramRoomDatabase.getInvalidationTracker().addWeakObserver(this.mObserver);
  }
  
  protected abstract List<T> convertRows(Cursor paramCursor);
  
  public int countItems() {
    RoomSQLiteQuery roomSQLiteQuery = RoomSQLiteQuery.acquire(this.mCountQuery, this.mSourceQuery.getArgCount());
    roomSQLiteQuery.copyArgumentsFrom(this.mSourceQuery);
    Cursor cursor = this.mDb.query((SupportSQLiteQuery)roomSQLiteQuery);
    try {
      if (cursor.moveToFirst())
        return cursor.getInt(0); 
      return 0;
    } finally {
      cursor.close();
      roomSQLiteQuery.release();
    } 
  }
  
  public boolean isInvalid() {
    this.mDb.getInvalidationTracker().refreshVersionsSync();
    return super.isInvalid();
  }
  
  public void loadInitial(PositionalDataSource.LoadInitialParams paramLoadInitialParams, PositionalDataSource.LoadInitialCallback<T> paramLoadInitialCallback) {
    int i = countItems();
    if (i == 0) {
      paramLoadInitialCallback.onResult(Collections.emptyList(), 0, 0);
      return;
    } 
    int j = computeInitialLoadPosition(paramLoadInitialParams, i);
    int k = computeInitialLoadSize(paramLoadInitialParams, j, i);
    List<T> list = loadRange(j, k);
    if (list != null && list.size() == k) {
      paramLoadInitialCallback.onResult(list, j, i);
    } else {
      invalidate();
    } 
  }
  
  public List<T> loadRange(int paramInt1, int paramInt2) {
    RoomSQLiteQuery roomSQLiteQuery = RoomSQLiteQuery.acquire(this.mLimitOffsetQuery, this.mSourceQuery.getArgCount() + 2);
    roomSQLiteQuery.copyArgumentsFrom(this.mSourceQuery);
    roomSQLiteQuery.bindLong(roomSQLiteQuery.getArgCount() - 1, paramInt2);
    roomSQLiteQuery.bindLong(roomSQLiteQuery.getArgCount(), paramInt1);
    if (this.mInTransaction) {
      this.mDb.beginTransaction();
      Cursor cursor1 = null;
      try {
        Cursor cursor2 = this.mDb.query((SupportSQLiteQuery)roomSQLiteQuery);
        cursor1 = cursor2;
        List<T> list = convertRows(cursor2);
        cursor1 = cursor2;
        this.mDb.setTransactionSuccessful();
        return list;
      } finally {
        if (cursor1 != null)
          cursor1.close(); 
        this.mDb.endTransaction();
        roomSQLiteQuery.release();
      } 
    } 
    Cursor cursor = this.mDb.query((SupportSQLiteQuery)roomSQLiteQuery);
    try {
      return convertRows(cursor);
    } finally {
      cursor.close();
      roomSQLiteQuery.release();
    } 
  }
  
  public void loadRange(PositionalDataSource.LoadRangeParams paramLoadRangeParams, PositionalDataSource.LoadRangeCallback<T> paramLoadRangeCallback) {
    List<T> list = loadRange(paramLoadRangeParams.startPosition, paramLoadRangeParams.loadSize);
    if (list != null) {
      paramLoadRangeCallback.onResult(list);
    } else {
      invalidate();
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\room\paging\LimitOffsetDataSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */