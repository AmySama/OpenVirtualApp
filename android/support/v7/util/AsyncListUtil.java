package android.support.v7.util;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

public class AsyncListUtil<T> {
  static final boolean DEBUG = false;
  
  static final String TAG = "AsyncListUtil";
  
  boolean mAllowScrollHints;
  
  private final ThreadUtil.BackgroundCallback<T> mBackgroundCallback = new ThreadUtil.BackgroundCallback<T>() {
      private int mFirstRequiredTileStart;
      
      private int mGeneration;
      
      private int mItemCount;
      
      private int mLastRequiredTileStart;
      
      final SparseBooleanArray mLoadedTiles = new SparseBooleanArray();
      
      private TileList.Tile<T> mRecycledRoot;
      
      private TileList.Tile<T> acquireTile() {
        TileList.Tile<T> tile = this.mRecycledRoot;
        if (tile != null) {
          this.mRecycledRoot = tile.mNext;
          return tile;
        } 
        return new TileList.Tile<T>(AsyncListUtil.this.mTClass, AsyncListUtil.this.mTileSize);
      }
      
      private void addTile(TileList.Tile<T> param1Tile) {
        this.mLoadedTiles.put(param1Tile.mStartPosition, true);
        AsyncListUtil.this.mMainThreadProxy.addTile(this.mGeneration, param1Tile);
      }
      
      private void flushTileCache(int param1Int) {
        int i = AsyncListUtil.this.mDataCallback.getMaxCachedTiles();
        while (this.mLoadedTiles.size() >= i) {
          int j = this.mLoadedTiles.keyAt(0);
          SparseBooleanArray sparseBooleanArray = this.mLoadedTiles;
          int k = sparseBooleanArray.keyAt(sparseBooleanArray.size() - 1);
          int m = this.mFirstRequiredTileStart - j;
          int n = k - this.mLastRequiredTileStart;
          if (m > 0 && (m >= n || param1Int == 2)) {
            removeTile(j);
            continue;
          } 
          if (n > 0 && (m < n || param1Int == 1))
            removeTile(k); 
        } 
      }
      
      private int getTileStart(int param1Int) {
        return param1Int - param1Int % AsyncListUtil.this.mTileSize;
      }
      
      private boolean isTileLoaded(int param1Int) {
        return this.mLoadedTiles.get(param1Int);
      }
      
      private void log(String param1String, Object... param1VarArgs) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[BKGR] ");
        stringBuilder.append(String.format(param1String, param1VarArgs));
        Log.d("AsyncListUtil", stringBuilder.toString());
      }
      
      private void removeTile(int param1Int) {
        this.mLoadedTiles.delete(param1Int);
        AsyncListUtil.this.mMainThreadProxy.removeTile(this.mGeneration, param1Int);
      }
      
      private void requestTiles(int param1Int1, int param1Int2, int param1Int3, boolean param1Boolean) {
        int i;
        for (i = param1Int1; i <= param1Int2; i += AsyncListUtil.this.mTileSize) {
          int j;
          if (param1Boolean) {
            j = param1Int2 + param1Int1 - i;
          } else {
            j = i;
          } 
          AsyncListUtil.this.mBackgroundProxy.loadTile(j, param1Int3);
        } 
      }
      
      public void loadTile(int param1Int1, int param1Int2) {
        if (isTileLoaded(param1Int1))
          return; 
        TileList.Tile<T> tile = acquireTile();
        tile.mStartPosition = param1Int1;
        tile.mItemCount = Math.min(AsyncListUtil.this.mTileSize, this.mItemCount - tile.mStartPosition);
        AsyncListUtil.this.mDataCallback.fillData(tile.mItems, tile.mStartPosition, tile.mItemCount);
        flushTileCache(param1Int2);
        addTile(tile);
      }
      
      public void recycleTile(TileList.Tile<T> param1Tile) {
        AsyncListUtil.this.mDataCallback.recycleData(param1Tile.mItems, param1Tile.mItemCount);
        param1Tile.mNext = this.mRecycledRoot;
        this.mRecycledRoot = param1Tile;
      }
      
      public void refresh(int param1Int) {
        this.mGeneration = param1Int;
        this.mLoadedTiles.clear();
        this.mItemCount = AsyncListUtil.this.mDataCallback.refreshData();
        AsyncListUtil.this.mMainThreadProxy.updateItemCount(this.mGeneration, this.mItemCount);
      }
      
      public void updateRange(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
        if (param1Int1 > param1Int2)
          return; 
        param1Int1 = getTileStart(param1Int1);
        param1Int2 = getTileStart(param1Int2);
        this.mFirstRequiredTileStart = getTileStart(param1Int3);
        param1Int3 = getTileStart(param1Int4);
        this.mLastRequiredTileStart = param1Int3;
        if (param1Int5 == 1) {
          requestTiles(this.mFirstRequiredTileStart, param1Int2, param1Int5, true);
          requestTiles(param1Int2 + AsyncListUtil.this.mTileSize, this.mLastRequiredTileStart, param1Int5, false);
        } else {
          requestTiles(param1Int1, param1Int3, param1Int5, false);
          requestTiles(this.mFirstRequiredTileStart, param1Int1 - AsyncListUtil.this.mTileSize, param1Int5, true);
        } 
      }
    };
  
  final ThreadUtil.BackgroundCallback<T> mBackgroundProxy;
  
  final DataCallback<T> mDataCallback;
  
  int mDisplayedGeneration = 0;
  
  int mItemCount = 0;
  
  private final ThreadUtil.MainThreadCallback<T> mMainThreadCallback = new ThreadUtil.MainThreadCallback<T>() {
      private boolean isRequestedGeneration(int param1Int) {
        boolean bool;
        if (param1Int == AsyncListUtil.this.mRequestedGeneration) {
          bool = true;
        } else {
          bool = false;
        } 
        return bool;
      }
      
      private void recycleAllTiles() {
        for (byte b = 0; b < AsyncListUtil.this.mTileList.size(); b++)
          AsyncListUtil.this.mBackgroundProxy.recycleTile(AsyncListUtil.this.mTileList.getAtIndex(b)); 
        AsyncListUtil.this.mTileList.clear();
      }
      
      public void addTile(int param1Int, TileList.Tile<T> param1Tile) {
        if (!isRequestedGeneration(param1Int)) {
          AsyncListUtil.this.mBackgroundProxy.recycleTile(param1Tile);
          return;
        } 
        TileList.Tile<T> tile = AsyncListUtil.this.mTileList.addOrReplace(param1Tile);
        if (tile != null) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("duplicate tile @");
          stringBuilder.append(tile.mStartPosition);
          Log.e("AsyncListUtil", stringBuilder.toString());
          AsyncListUtil.this.mBackgroundProxy.recycleTile(tile);
        } 
        int i = param1Tile.mStartPosition;
        int j = param1Tile.mItemCount;
        for (param1Int = 0; param1Int < AsyncListUtil.this.mMissingPositions.size(); param1Int++) {
          int k = AsyncListUtil.this.mMissingPositions.keyAt(param1Int);
          if (param1Tile.mStartPosition <= k && k < i + j) {
            AsyncListUtil.this.mMissingPositions.removeAt(param1Int);
            AsyncListUtil.this.mViewCallback.onItemLoaded(k);
            continue;
          } 
        } 
      }
      
      public void removeTile(int param1Int1, int param1Int2) {
        StringBuilder stringBuilder;
        if (!isRequestedGeneration(param1Int1))
          return; 
        TileList.Tile tile = AsyncListUtil.this.mTileList.removeAtPos(param1Int2);
        if (tile == null) {
          stringBuilder = new StringBuilder();
          stringBuilder.append("tile not found @");
          stringBuilder.append(param1Int2);
          Log.e("AsyncListUtil", stringBuilder.toString());
          return;
        } 
        AsyncListUtil.this.mBackgroundProxy.recycleTile((TileList.Tile)stringBuilder);
      }
      
      public void updateItemCount(int param1Int1, int param1Int2) {
        if (!isRequestedGeneration(param1Int1))
          return; 
        AsyncListUtil.this.mItemCount = param1Int2;
        AsyncListUtil.this.mViewCallback.onDataRefresh();
        AsyncListUtil asyncListUtil = AsyncListUtil.this;
        asyncListUtil.mDisplayedGeneration = asyncListUtil.mRequestedGeneration;
        recycleAllTiles();
        AsyncListUtil.this.mAllowScrollHints = false;
        AsyncListUtil.this.updateRange();
      }
    };
  
  final ThreadUtil.MainThreadCallback<T> mMainThreadProxy;
  
  final SparseIntArray mMissingPositions = new SparseIntArray();
  
  final int[] mPrevRange = new int[2];
  
  int mRequestedGeneration = 0;
  
  private int mScrollHint = 0;
  
  final Class<T> mTClass;
  
  final TileList<T> mTileList;
  
  final int mTileSize;
  
  final int[] mTmpRange = new int[2];
  
  final int[] mTmpRangeExtended = new int[2];
  
  final ViewCallback mViewCallback;
  
  public AsyncListUtil(Class<T> paramClass, int paramInt, DataCallback<T> paramDataCallback, ViewCallback paramViewCallback) {
    this.mTClass = paramClass;
    this.mTileSize = paramInt;
    this.mDataCallback = paramDataCallback;
    this.mViewCallback = paramViewCallback;
    this.mTileList = new TileList<T>(paramInt);
    MessageThreadUtil<T> messageThreadUtil = new MessageThreadUtil();
    this.mMainThreadProxy = messageThreadUtil.getMainThreadProxy(this.mMainThreadCallback);
    this.mBackgroundProxy = messageThreadUtil.getBackgroundProxy(this.mBackgroundCallback);
    refresh();
  }
  
  private boolean isRefreshPending() {
    boolean bool;
    if (this.mRequestedGeneration != this.mDisplayedGeneration) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public T getItem(int paramInt) {
    if (paramInt >= 0 && paramInt < this.mItemCount) {
      T t = this.mTileList.getItemAt(paramInt);
      if (t == null && !isRefreshPending())
        this.mMissingPositions.put(paramInt, 0); 
      return t;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramInt);
    stringBuilder.append(" is not within 0 and ");
    stringBuilder.append(this.mItemCount);
    throw new IndexOutOfBoundsException(stringBuilder.toString());
  }
  
  public int getItemCount() {
    return this.mItemCount;
  }
  
  void log(String paramString, Object... paramVarArgs) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[MAIN] ");
    stringBuilder.append(String.format(paramString, paramVarArgs));
    Log.d("AsyncListUtil", stringBuilder.toString());
  }
  
  public void onRangeChanged() {
    if (isRefreshPending())
      return; 
    updateRange();
    this.mAllowScrollHints = true;
  }
  
  public void refresh() {
    this.mMissingPositions.clear();
    ThreadUtil.BackgroundCallback<T> backgroundCallback = this.mBackgroundProxy;
    int i = this.mRequestedGeneration + 1;
    this.mRequestedGeneration = i;
    backgroundCallback.refresh(i);
  }
  
  void updateRange() {
    this.mViewCallback.getItemRangeInto(this.mTmpRange);
    int[] arrayOfInt = this.mTmpRange;
    if (arrayOfInt[0] <= arrayOfInt[1] && arrayOfInt[0] >= 0) {
      if (arrayOfInt[1] >= this.mItemCount)
        return; 
      if (!this.mAllowScrollHints) {
        this.mScrollHint = 0;
      } else {
        int k = arrayOfInt[0];
        int[] arrayOfInt2 = this.mPrevRange;
        if (k > arrayOfInt2[1] || arrayOfInt2[0] > arrayOfInt[1]) {
          this.mScrollHint = 0;
        } else if (arrayOfInt[0] < arrayOfInt2[0]) {
          this.mScrollHint = 1;
        } else if (arrayOfInt[0] > arrayOfInt2[0]) {
          this.mScrollHint = 2;
        } 
      } 
      arrayOfInt = this.mPrevRange;
      int[] arrayOfInt1 = this.mTmpRange;
      arrayOfInt[0] = arrayOfInt1[0];
      arrayOfInt[1] = arrayOfInt1[1];
      this.mViewCallback.extendRangeInto(arrayOfInt1, this.mTmpRangeExtended, this.mScrollHint);
      arrayOfInt = this.mTmpRangeExtended;
      arrayOfInt[0] = Math.min(this.mTmpRange[0], Math.max(arrayOfInt[0], 0));
      arrayOfInt = this.mTmpRangeExtended;
      arrayOfInt[1] = Math.max(this.mTmpRange[1], Math.min(arrayOfInt[1], this.mItemCount - 1));
      ThreadUtil.BackgroundCallback<T> backgroundCallback = this.mBackgroundProxy;
      arrayOfInt1 = this.mTmpRange;
      int i = arrayOfInt1[0];
      int j = arrayOfInt1[1];
      arrayOfInt1 = this.mTmpRangeExtended;
      backgroundCallback.updateRange(i, j, arrayOfInt1[0], arrayOfInt1[1], this.mScrollHint);
    } 
  }
  
  public static abstract class DataCallback<T> {
    public abstract void fillData(T[] param1ArrayOfT, int param1Int1, int param1Int2);
    
    public int getMaxCachedTiles() {
      return 10;
    }
    
    public void recycleData(T[] param1ArrayOfT, int param1Int) {}
    
    public abstract int refreshData();
  }
  
  public static abstract class ViewCallback {
    public static final int HINT_SCROLL_ASC = 2;
    
    public static final int HINT_SCROLL_DESC = 1;
    
    public static final int HINT_SCROLL_NONE = 0;
    
    public void extendRangeInto(int[] param1ArrayOfint1, int[] param1ArrayOfint2, int param1Int) {
      int i = param1ArrayOfint1[1] - param1ArrayOfint1[0] + 1;
      int j = i / 2;
      int k = param1ArrayOfint1[0];
      if (param1Int == 1) {
        m = i;
      } else {
        m = j;
      } 
      param1ArrayOfint2[0] = k - m;
      int m = param1ArrayOfint1[1];
      if (param1Int != 2)
        i = j; 
      param1ArrayOfint2[1] = m + i;
    }
    
    public abstract void getItemRangeInto(int[] param1ArrayOfint);
    
    public abstract void onDataRefresh();
    
    public abstract void onItemLoaded(int param1Int);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v\\util\AsyncListUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */