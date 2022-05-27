package android.arch.persistence.room;

import android.arch.persistence.db.SupportSQLiteProgram;
import android.arch.persistence.db.SupportSQLiteQuery;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class RoomSQLiteQuery implements SupportSQLiteQuery, SupportSQLiteProgram {
  private static final int BLOB = 5;
  
  static final int DESIRED_POOL_SIZE = 10;
  
  private static final int DOUBLE = 3;
  
  private static final int LONG = 2;
  
  private static final int NULL = 1;
  
  static final int POOL_LIMIT = 15;
  
  private static final int STRING = 4;
  
  static final TreeMap<Integer, RoomSQLiteQuery> sQueryPool = new TreeMap<Integer, RoomSQLiteQuery>();
  
  int mArgCount;
  
  private final int[] mBindingTypes;
  
  final byte[][] mBlobBindings;
  
  final int mCapacity;
  
  final double[] mDoubleBindings;
  
  final long[] mLongBindings;
  
  private volatile String mQuery;
  
  final String[] mStringBindings;
  
  private RoomSQLiteQuery(int paramInt) {
    this.mCapacity = paramInt;
    this.mBindingTypes = new int[++paramInt];
    this.mLongBindings = new long[paramInt];
    this.mDoubleBindings = new double[paramInt];
    this.mStringBindings = new String[paramInt];
    this.mBlobBindings = new byte[paramInt][];
  }
  
  public static RoomSQLiteQuery acquire(String paramString, int paramInt) {
    TreeMap<Integer, RoomSQLiteQuery> treeMap;
    RoomSQLiteQuery roomSQLiteQuery;
    synchronized (sQueryPool) {
      Map.Entry<Integer, RoomSQLiteQuery> entry = sQueryPool.ceilingEntry(Integer.valueOf(paramInt));
      if (entry != null) {
        sQueryPool.remove(entry.getKey());
        RoomSQLiteQuery roomSQLiteQuery1 = entry.getValue();
        roomSQLiteQuery1.init(paramString, paramInt);
        return roomSQLiteQuery1;
      } 
      roomSQLiteQuery = new RoomSQLiteQuery(paramInt);
      roomSQLiteQuery.init(paramString, paramInt);
      return roomSQLiteQuery;
    } 
  }
  
  public static RoomSQLiteQuery copyFrom(SupportSQLiteQuery paramSupportSQLiteQuery) {
    final RoomSQLiteQuery query = acquire(paramSupportSQLiteQuery.getSql(), paramSupportSQLiteQuery.getArgCount());
    paramSupportSQLiteQuery.bindTo(new SupportSQLiteProgram() {
          public void bindBlob(int param1Int, byte[] param1ArrayOfbyte) {
            query.bindBlob(param1Int, param1ArrayOfbyte);
          }
          
          public void bindDouble(int param1Int, double param1Double) {
            query.bindDouble(param1Int, param1Double);
          }
          
          public void bindLong(int param1Int, long param1Long) {
            query.bindLong(param1Int, param1Long);
          }
          
          public void bindNull(int param1Int) {
            query.bindNull(param1Int);
          }
          
          public void bindString(int param1Int, String param1String) {
            query.bindString(param1Int, param1String);
          }
          
          public void clearBindings() {
            query.clearBindings();
          }
          
          public void close() {}
        });
    return roomSQLiteQuery;
  }
  
  private static void prunePoolLocked() {
    if (sQueryPool.size() > 15) {
      int i = sQueryPool.size() - 10;
      Iterator iterator = sQueryPool.descendingKeySet().iterator();
      while (i > 0) {
        iterator.next();
        iterator.remove();
        i--;
      } 
    } 
  }
  
  public void bindBlob(int paramInt, byte[] paramArrayOfbyte) {
    this.mBindingTypes[paramInt] = 5;
    this.mBlobBindings[paramInt] = paramArrayOfbyte;
  }
  
  public void bindDouble(int paramInt, double paramDouble) {
    this.mBindingTypes[paramInt] = 3;
    this.mDoubleBindings[paramInt] = paramDouble;
  }
  
  public void bindLong(int paramInt, long paramLong) {
    this.mBindingTypes[paramInt] = 2;
    this.mLongBindings[paramInt] = paramLong;
  }
  
  public void bindNull(int paramInt) {
    this.mBindingTypes[paramInt] = 1;
  }
  
  public void bindString(int paramInt, String paramString) {
    this.mBindingTypes[paramInt] = 4;
    this.mStringBindings[paramInt] = paramString;
  }
  
  public void bindTo(SupportSQLiteProgram paramSupportSQLiteProgram) {
    for (byte b = 1; b <= this.mArgCount; b++) {
      int i = this.mBindingTypes[b];
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i != 4) {
              if (i == 5)
                paramSupportSQLiteProgram.bindBlob(b, this.mBlobBindings[b]); 
            } else {
              paramSupportSQLiteProgram.bindString(b, this.mStringBindings[b]);
            } 
          } else {
            paramSupportSQLiteProgram.bindDouble(b, this.mDoubleBindings[b]);
          } 
        } else {
          paramSupportSQLiteProgram.bindLong(b, this.mLongBindings[b]);
        } 
      } else {
        paramSupportSQLiteProgram.bindNull(b);
      } 
    } 
  }
  
  public void clearBindings() {
    Arrays.fill(this.mBindingTypes, 1);
    Arrays.fill((Object[])this.mStringBindings, (Object)null);
    Arrays.fill((Object[])this.mBlobBindings, (Object)null);
    this.mQuery = null;
  }
  
  public void close() {}
  
  public void copyArgumentsFrom(RoomSQLiteQuery paramRoomSQLiteQuery) {
    int i = paramRoomSQLiteQuery.getArgCount() + 1;
    System.arraycopy(paramRoomSQLiteQuery.mBindingTypes, 0, this.mBindingTypes, 0, i);
    System.arraycopy(paramRoomSQLiteQuery.mLongBindings, 0, this.mLongBindings, 0, i);
    System.arraycopy(paramRoomSQLiteQuery.mStringBindings, 0, this.mStringBindings, 0, i);
    System.arraycopy(paramRoomSQLiteQuery.mBlobBindings, 0, this.mBlobBindings, 0, i);
    System.arraycopy(paramRoomSQLiteQuery.mDoubleBindings, 0, this.mDoubleBindings, 0, i);
  }
  
  public int getArgCount() {
    return this.mArgCount;
  }
  
  public String getSql() {
    return this.mQuery;
  }
  
  void init(String paramString, int paramInt) {
    this.mQuery = paramString;
    this.mArgCount = paramInt;
  }
  
  public void release() {
    synchronized (sQueryPool) {
      sQueryPool.put(Integer.valueOf(this.mCapacity), this);
      prunePoolLocked();
      return;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\room\RoomSQLiteQuery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */