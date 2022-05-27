package android.support.v7.util;

import android.util.SparseArray;
import java.lang.reflect.Array;

class TileList<T> {
  Tile<T> mLastAccessedTile;
  
  final int mTileSize;
  
  private final SparseArray<Tile<T>> mTiles = new SparseArray(10);
  
  public TileList(int paramInt) {
    this.mTileSize = paramInt;
  }
  
  public Tile<T> addOrReplace(Tile<T> paramTile) {
    int i = this.mTiles.indexOfKey(paramTile.mStartPosition);
    if (i < 0) {
      this.mTiles.put(paramTile.mStartPosition, paramTile);
      return null;
    } 
    Tile<T> tile = (Tile)this.mTiles.valueAt(i);
    this.mTiles.setValueAt(i, paramTile);
    if (this.mLastAccessedTile == tile)
      this.mLastAccessedTile = paramTile; 
    return tile;
  }
  
  public void clear() {
    this.mTiles.clear();
  }
  
  public Tile<T> getAtIndex(int paramInt) {
    return (Tile<T>)this.mTiles.valueAt(paramInt);
  }
  
  public T getItemAt(int paramInt) {
    Tile<T> tile = this.mLastAccessedTile;
    if (tile == null || !tile.containsPosition(paramInt)) {
      int i = this.mTileSize;
      i = this.mTiles.indexOfKey(paramInt - paramInt % i);
      if (i < 0)
        return null; 
      this.mLastAccessedTile = (Tile<T>)this.mTiles.valueAt(i);
    } 
    return this.mLastAccessedTile.getByPosition(paramInt);
  }
  
  public Tile<T> removeAtPos(int paramInt) {
    Tile<T> tile = (Tile)this.mTiles.get(paramInt);
    if (this.mLastAccessedTile == tile)
      this.mLastAccessedTile = null; 
    this.mTiles.delete(paramInt);
    return tile;
  }
  
  public int size() {
    return this.mTiles.size();
  }
  
  public static class Tile<T> {
    public int mItemCount;
    
    public final T[] mItems;
    
    Tile<T> mNext;
    
    public int mStartPosition;
    
    public Tile(Class<T> param1Class, int param1Int) {
      this.mItems = (T[])Array.newInstance(param1Class, param1Int);
    }
    
    boolean containsPosition(int param1Int) {
      boolean bool;
      int i = this.mStartPosition;
      if (i <= param1Int && param1Int < i + this.mItemCount) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    T getByPosition(int param1Int) {
      return this.mItems[param1Int - this.mStartPosition];
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v\\util\TileList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */