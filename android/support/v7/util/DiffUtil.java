package android.support.v7.util;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DiffUtil {
  private static final Comparator<Snake> SNAKE_COMPARATOR = new Comparator<Snake>() {
      public int compare(DiffUtil.Snake param1Snake1, DiffUtil.Snake param1Snake2) {
        int i = param1Snake1.x - param1Snake2.x;
        int j = i;
        if (i == 0)
          j = param1Snake1.y - param1Snake2.y; 
        return j;
      }
    };
  
  public static DiffResult calculateDiff(Callback paramCallback) {
    return calculateDiff(paramCallback, true);
  }
  
  public static DiffResult calculateDiff(Callback paramCallback, boolean paramBoolean) {
    int i = paramCallback.getOldListSize();
    int j = paramCallback.getNewListSize();
    ArrayList<Snake> arrayList = new ArrayList();
    ArrayList<Range> arrayList1 = new ArrayList();
    arrayList1.add(new Range(0, i, 0, j));
    i = i + j + Math.abs(i - j);
    j = i * 2;
    int[] arrayOfInt1 = new int[j];
    int[] arrayOfInt2 = new int[j];
    ArrayList<Range> arrayList2 = new ArrayList();
    while (!arrayList1.isEmpty()) {
      Range range = arrayList1.remove(arrayList1.size() - 1);
      Snake snake = diffPartial(paramCallback, range.oldListStart, range.oldListEnd, range.newListStart, range.newListEnd, arrayOfInt1, arrayOfInt2, i);
      if (snake != null) {
        Range range1;
        if (snake.size > 0)
          arrayList.add(snake); 
        snake.x += range.oldListStart;
        snake.y += range.newListStart;
        if (arrayList2.isEmpty()) {
          range1 = new Range();
        } else {
          range1 = arrayList2.remove(arrayList2.size() - 1);
        } 
        range1.oldListStart = range.oldListStart;
        range1.newListStart = range.newListStart;
        if (snake.reverse) {
          range1.oldListEnd = snake.x;
          range1.newListEnd = snake.y;
        } else if (snake.removal) {
          range1.oldListEnd = snake.x - 1;
          range1.newListEnd = snake.y;
        } else {
          range1.oldListEnd = snake.x;
          range1.newListEnd = snake.y - 1;
        } 
        arrayList1.add(range1);
        if (snake.reverse) {
          if (snake.removal) {
            range.oldListStart = snake.x + snake.size + 1;
            range.newListStart = snake.y + snake.size;
          } else {
            range.oldListStart = snake.x + snake.size;
            range.newListStart = snake.y + snake.size + 1;
          } 
        } else {
          range.oldListStart = snake.x + snake.size;
          range.newListStart = snake.y + snake.size;
        } 
        arrayList1.add(range);
        continue;
      } 
      arrayList2.add(range);
    } 
    Collections.sort(arrayList, SNAKE_COMPARATOR);
    return new DiffResult(paramCallback, arrayList, arrayOfInt1, arrayOfInt2, paramBoolean);
  }
  
  private static Snake diffPartial(Callback paramCallback, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt5) {
    paramInt2 -= paramInt1;
    int i = paramInt4 - paramInt3;
    if (paramInt2 < 1 || i < 1)
      return null; 
    int j = paramInt2 - i;
    int k = (paramInt2 + i + 1) / 2;
    paramInt4 = paramInt5 - k - 1;
    int m = paramInt5 + k + 1;
    Arrays.fill(paramArrayOfint1, paramInt4, m, 0);
    Arrays.fill(paramArrayOfint2, paramInt4 + j, m + j, paramInt2);
    if (j % 2 != 0) {
      m = 1;
    } else {
      m = 0;
    } 
    for (byte b = 0; b <= k; b++) {
      byte b1 = -b;
      byte b2 = b1;
      while (true)
        b2 += 2; 
      b2 = b1;
      while (true)
        b2 += 2; 
      continue;
    } 
    throw new IllegalStateException("DiffUtil hit an unexpected case while trying to calculate the optimal path. Please make sure your data is not changing during the diff calculation.");
  }
  
  public static abstract class Callback {
    public abstract boolean areContentsTheSame(int param1Int1, int param1Int2);
    
    public abstract boolean areItemsTheSame(int param1Int1, int param1Int2);
    
    public Object getChangePayload(int param1Int1, int param1Int2) {
      return null;
    }
    
    public abstract int getNewListSize();
    
    public abstract int getOldListSize();
  }
  
  public static class DiffResult {
    private static final int FLAG_CHANGED = 2;
    
    private static final int FLAG_IGNORE = 16;
    
    private static final int FLAG_MASK = 31;
    
    private static final int FLAG_MOVED_CHANGED = 4;
    
    private static final int FLAG_MOVED_NOT_CHANGED = 8;
    
    private static final int FLAG_NOT_CHANGED = 1;
    
    private static final int FLAG_OFFSET = 5;
    
    private final DiffUtil.Callback mCallback;
    
    private final boolean mDetectMoves;
    
    private final int[] mNewItemStatuses;
    
    private final int mNewListSize;
    
    private final int[] mOldItemStatuses;
    
    private final int mOldListSize;
    
    private final List<DiffUtil.Snake> mSnakes;
    
    DiffResult(DiffUtil.Callback param1Callback, List<DiffUtil.Snake> param1List, int[] param1ArrayOfint1, int[] param1ArrayOfint2, boolean param1Boolean) {
      this.mSnakes = param1List;
      this.mOldItemStatuses = param1ArrayOfint1;
      this.mNewItemStatuses = param1ArrayOfint2;
      Arrays.fill(param1ArrayOfint1, 0);
      Arrays.fill(this.mNewItemStatuses, 0);
      this.mCallback = param1Callback;
      this.mOldListSize = param1Callback.getOldListSize();
      this.mNewListSize = param1Callback.getNewListSize();
      this.mDetectMoves = param1Boolean;
      addRootSnake();
      findMatchingItems();
    }
    
    private void addRootSnake() {
      DiffUtil.Snake snake;
      if (this.mSnakes.isEmpty()) {
        snake = null;
      } else {
        snake = this.mSnakes.get(0);
      } 
      if (snake == null || snake.x != 0 || snake.y != 0) {
        snake = new DiffUtil.Snake();
        snake.x = 0;
        snake.y = 0;
        snake.removal = false;
        snake.size = 0;
        snake.reverse = false;
        this.mSnakes.add(0, snake);
      } 
    }
    
    private void dispatchAdditions(List<DiffUtil.PostponedUpdate> param1List, ListUpdateCallback param1ListUpdateCallback, int param1Int1, int param1Int2, int param1Int3) {
      if (!this.mDetectMoves) {
        param1ListUpdateCallback.onInserted(param1Int1, param1Int2);
        return;
      } 
      while (--param1Int2 >= 0) {
        StringBuilder stringBuilder;
        int[] arrayOfInt = this.mNewItemStatuses;
        int i = param1Int3 + param1Int2;
        int j = arrayOfInt[i] & 0x1F;
        if (j != 0) {
          if (j != 4 && j != 8) {
            if (j == 16) {
              param1List.add(new DiffUtil.PostponedUpdate(i, param1Int1, false));
            } else {
              stringBuilder = new StringBuilder();
              stringBuilder.append("unknown flag for pos ");
              stringBuilder.append(i);
              stringBuilder.append(" ");
              stringBuilder.append(Long.toBinaryString(j));
              throw new IllegalStateException(stringBuilder.toString());
            } 
          } else {
            int k = this.mNewItemStatuses[i] >> 5;
            param1ListUpdateCallback.onMoved((removePostponedUpdate((List<DiffUtil.PostponedUpdate>)stringBuilder, k, true)).currentPos, param1Int1);
            if (j == 4)
              param1ListUpdateCallback.onChanged(param1Int1, 1, this.mCallback.getChangePayload(k, i)); 
          } 
        } else {
          param1ListUpdateCallback.onInserted(param1Int1, 1);
          for (DiffUtil.PostponedUpdate postponedUpdate : stringBuilder)
            postponedUpdate.currentPos++; 
        } 
        param1Int2--;
      } 
    }
    
    private void dispatchRemovals(List<DiffUtil.PostponedUpdate> param1List, ListUpdateCallback param1ListUpdateCallback, int param1Int1, int param1Int2, int param1Int3) {
      if (!this.mDetectMoves) {
        param1ListUpdateCallback.onRemoved(param1Int1, param1Int2);
        return;
      } 
      while (--param1Int2 >= 0) {
        StringBuilder stringBuilder;
        int[] arrayOfInt = this.mOldItemStatuses;
        int i = param1Int3 + param1Int2;
        int j = arrayOfInt[i] & 0x1F;
        if (j != 0) {
          if (j != 4 && j != 8) {
            if (j == 16) {
              param1List.add(new DiffUtil.PostponedUpdate(i, param1Int1 + param1Int2, true));
            } else {
              stringBuilder = new StringBuilder();
              stringBuilder.append("unknown flag for pos ");
              stringBuilder.append(i);
              stringBuilder.append(" ");
              stringBuilder.append(Long.toBinaryString(j));
              throw new IllegalStateException(stringBuilder.toString());
            } 
          } else {
            int k = this.mOldItemStatuses[i] >> 5;
            DiffUtil.PostponedUpdate postponedUpdate = removePostponedUpdate((List<DiffUtil.PostponedUpdate>)stringBuilder, k, false);
            param1ListUpdateCallback.onMoved(param1Int1 + param1Int2, postponedUpdate.currentPos - 1);
            if (j == 4)
              param1ListUpdateCallback.onChanged(postponedUpdate.currentPos - 1, 1, this.mCallback.getChangePayload(i, k)); 
          } 
        } else {
          param1ListUpdateCallback.onRemoved(param1Int1 + param1Int2, 1);
          for (DiffUtil.PostponedUpdate postponedUpdate : stringBuilder)
            postponedUpdate.currentPos--; 
        } 
        param1Int2--;
      } 
    }
    
    private void findAddition(int param1Int1, int param1Int2, int param1Int3) {
      if (this.mOldItemStatuses[param1Int1 - 1] != 0)
        return; 
      findMatchingItem(param1Int1, param1Int2, param1Int3, false);
    }
    
    private boolean findMatchingItem(int param1Int1, int param1Int2, int param1Int3, boolean param1Boolean) {
      int i;
      int j;
      if (param1Boolean) {
        i = param1Int2 - 1;
        param1Int2 = param1Int1;
        j = i;
      } else {
        j = param1Int1 - 1;
        int k = j;
        i = param1Int2;
        param1Int2 = k;
      } 
      while (param1Int3 >= 0) {
        int[] arrayOfInt;
        DiffUtil.Snake snake = this.mSnakes.get(param1Int3);
        int k = snake.x;
        int m = snake.size;
        int n = snake.y;
        int i1 = snake.size;
        byte b = 8;
        if (param1Boolean) {
          while (--param1Int2 >= k + m) {
            if (this.mCallback.areItemsTheSame(param1Int2, j)) {
              if (!this.mCallback.areContentsTheSame(param1Int2, j))
                b = 4; 
              this.mNewItemStatuses[j] = param1Int2 << 5 | 0x10;
              this.mOldItemStatuses[param1Int2] = j << 5 | b;
              return true;
            } 
            param1Int2--;
          } 
        } else {
          for (param1Int2 = i - 1; param1Int2 >= n + i1; param1Int2--) {
            if (this.mCallback.areItemsTheSame(j, param1Int2)) {
              if (!this.mCallback.areContentsTheSame(j, param1Int2))
                b = 4; 
              arrayOfInt = this.mOldItemStatuses;
              arrayOfInt[--param1Int1] = param1Int2 << 5 | 0x10;
              this.mNewItemStatuses[param1Int2] = param1Int1 << 5 | b;
              return true;
            } 
          } 
        } 
        param1Int2 = ((DiffUtil.Snake)arrayOfInt).x;
        i = ((DiffUtil.Snake)arrayOfInt).y;
        param1Int3--;
      } 
      return false;
    }
    
    private void findMatchingItems() {
      int i = this.mOldListSize;
      int j = this.mNewListSize;
      for (int k = this.mSnakes.size() - 1; k >= 0; k--) {
        DiffUtil.Snake snake = this.mSnakes.get(k);
        int m = snake.x;
        int n = snake.size;
        int i1 = snake.y;
        int i2 = snake.size;
        if (this.mDetectMoves) {
          int i3;
          while (true) {
            i3 = j;
            if (i > m + n) {
              findAddition(i, j, k);
              i--;
              continue;
            } 
            break;
          } 
          while (i3 > i1 + i2) {
            findRemoval(i, i3, k);
            i3--;
          } 
        } 
        for (j = 0; j < snake.size; j++) {
          i2 = snake.x + j;
          int i3 = snake.y + j;
          if (this.mCallback.areContentsTheSame(i2, i3)) {
            i = 1;
          } else {
            i = 2;
          } 
          this.mOldItemStatuses[i2] = i3 << 5 | i;
          this.mNewItemStatuses[i3] = i2 << 5 | i;
        } 
        i = snake.x;
        j = snake.y;
      } 
    }
    
    private void findRemoval(int param1Int1, int param1Int2, int param1Int3) {
      if (this.mNewItemStatuses[param1Int2 - 1] != 0)
        return; 
      findMatchingItem(param1Int1, param1Int2, param1Int3, true);
    }
    
    private static DiffUtil.PostponedUpdate removePostponedUpdate(List<DiffUtil.PostponedUpdate> param1List, int param1Int, boolean param1Boolean) {
      for (int i = param1List.size() - 1; i >= 0; i--) {
        DiffUtil.PostponedUpdate postponedUpdate = param1List.get(i);
        if (postponedUpdate.posInOwnerList == param1Int && postponedUpdate.removal == param1Boolean) {
          param1List.remove(i);
          while (i < param1List.size()) {
            DiffUtil.PostponedUpdate postponedUpdate1 = param1List.get(i);
            int j = postponedUpdate1.currentPos;
            if (param1Boolean) {
              param1Int = 1;
            } else {
              param1Int = -1;
            } 
            postponedUpdate1.currentPos = j + param1Int;
            i++;
          } 
          return postponedUpdate;
        } 
      } 
      return null;
    }
    
    public void dispatchUpdatesTo(ListUpdateCallback param1ListUpdateCallback) {
      if (param1ListUpdateCallback instanceof BatchingListUpdateCallback) {
        param1ListUpdateCallback = param1ListUpdateCallback;
      } else {
        param1ListUpdateCallback = new BatchingListUpdateCallback(param1ListUpdateCallback);
      } 
      ArrayList<DiffUtil.PostponedUpdate> arrayList = new ArrayList();
      int i = this.mOldListSize;
      int j = this.mNewListSize;
      for (int k = this.mSnakes.size(); --k >= 0; k--) {
        DiffUtil.Snake snake = this.mSnakes.get(k);
        int m = snake.size;
        int n = snake.x + m;
        int i1 = snake.y + m;
        if (n < i)
          dispatchRemovals(arrayList, param1ListUpdateCallback, n, i - n, n); 
        if (i1 < j)
          dispatchAdditions(arrayList, param1ListUpdateCallback, n, j - i1, i1); 
        for (i = m - 1; i >= 0; i--) {
          if ((this.mOldItemStatuses[snake.x + i] & 0x1F) == 2)
            param1ListUpdateCallback.onChanged(snake.x + i, 1, this.mCallback.getChangePayload(snake.x + i, snake.y + i)); 
        } 
        i = snake.x;
        j = snake.y;
      } 
      param1ListUpdateCallback.dispatchLastEvent();
    }
    
    public void dispatchUpdatesTo(RecyclerView.Adapter param1Adapter) {
      dispatchUpdatesTo(new AdapterListUpdateCallback(param1Adapter));
    }
    
    List<DiffUtil.Snake> getSnakes() {
      return this.mSnakes;
    }
  }
  
  public static abstract class ItemCallback<T> {
    public abstract boolean areContentsTheSame(T param1T1, T param1T2);
    
    public abstract boolean areItemsTheSame(T param1T1, T param1T2);
    
    public Object getChangePayload(T param1T1, T param1T2) {
      return null;
    }
  }
  
  private static class PostponedUpdate {
    int currentPos;
    
    int posInOwnerList;
    
    boolean removal;
    
    public PostponedUpdate(int param1Int1, int param1Int2, boolean param1Boolean) {
      this.posInOwnerList = param1Int1;
      this.currentPos = param1Int2;
      this.removal = param1Boolean;
    }
  }
  
  static class Range {
    int newListEnd;
    
    int newListStart;
    
    int oldListEnd;
    
    int oldListStart;
    
    public Range() {}
    
    public Range(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      this.oldListStart = param1Int1;
      this.oldListEnd = param1Int2;
      this.newListStart = param1Int3;
      this.newListEnd = param1Int4;
    }
  }
  
  static class Snake {
    boolean removal;
    
    boolean reverse;
    
    int size;
    
    int x;
    
    int y;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v\\util\DiffUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */