package android.support.v4.widget;

import android.graphics.Rect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class FocusStrategy {
  private static boolean beamBeats(int paramInt, Rect paramRect1, Rect paramRect2, Rect paramRect3) {
    boolean bool1 = beamsOverlap(paramInt, paramRect1, paramRect2);
    boolean bool2 = beamsOverlap(paramInt, paramRect1, paramRect3);
    boolean bool = false;
    if (bool2 || !bool1)
      return false; 
    if (!isToDirectionOf(paramInt, paramRect1, paramRect3))
      return true; 
    if (paramInt == 17 || paramInt == 66)
      return true; 
    if (majorAxisDistance(paramInt, paramRect1, paramRect2) < majorAxisDistanceToFarEdge(paramInt, paramRect1, paramRect3))
      bool = true; 
    return bool;
  }
  
  private static boolean beamsOverlap(int paramInt, Rect paramRect1, Rect paramRect2) {
    // Byte code:
    //   0: iconst_1
    //   1: istore_3
    //   2: iconst_1
    //   3: istore #4
    //   5: iload_0
    //   6: bipush #17
    //   8: if_icmpeq -> 74
    //   11: iload_0
    //   12: bipush #33
    //   14: if_icmpeq -> 43
    //   17: iload_0
    //   18: bipush #66
    //   20: if_icmpeq -> 74
    //   23: iload_0
    //   24: sipush #130
    //   27: if_icmpne -> 33
    //   30: goto -> 43
    //   33: new java/lang/IllegalArgumentException
    //   36: dup
    //   37: ldc 'direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.'
    //   39: invokespecial <init> : (Ljava/lang/String;)V
    //   42: athrow
    //   43: aload_2
    //   44: getfield right : I
    //   47: aload_1
    //   48: getfield left : I
    //   51: if_icmplt -> 68
    //   54: aload_2
    //   55: getfield left : I
    //   58: aload_1
    //   59: getfield right : I
    //   62: if_icmpgt -> 68
    //   65: goto -> 71
    //   68: iconst_0
    //   69: istore #4
    //   71: iload #4
    //   73: ireturn
    //   74: aload_2
    //   75: getfield bottom : I
    //   78: aload_1
    //   79: getfield top : I
    //   82: if_icmplt -> 102
    //   85: aload_2
    //   86: getfield top : I
    //   89: aload_1
    //   90: getfield bottom : I
    //   93: if_icmpgt -> 102
    //   96: iload_3
    //   97: istore #4
    //   99: goto -> 105
    //   102: iconst_0
    //   103: istore #4
    //   105: iload #4
    //   107: ireturn
  }
  
  public static <L, T> T findNextFocusInAbsoluteDirection(L paramL, CollectionAdapter<L, T> paramCollectionAdapter, BoundsAdapter<T> paramBoundsAdapter, T paramT, Rect paramRect, int paramInt) {
    T t;
    Rect rect1 = new Rect(paramRect);
    byte b = 0;
    if (paramInt != 17) {
      if (paramInt != 33) {
        if (paramInt != 66) {
          if (paramInt == 130) {
            rect1.offset(0, -(paramRect.height() + 1));
          } else {
            throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
          } 
        } else {
          rect1.offset(-(paramRect.width() + 1), 0);
        } 
      } else {
        rect1.offset(0, paramRect.height() + 1);
      } 
    } else {
      rect1.offset(paramRect.width() + 1, 0);
    } 
    Object object = null;
    int i = paramCollectionAdapter.size(paramL);
    Rect rect2 = new Rect();
    while (b < i) {
      T t1 = paramCollectionAdapter.get(paramL, b);
      if (t1 != paramT) {
        paramBoundsAdapter.obtainBounds(t1, rect2);
        if (isBetterCandidate(paramInt, paramRect, rect2, rect1)) {
          rect1.set(rect2);
          t = t1;
        } 
      } 
      b++;
    } 
    return t;
  }
  
  public static <L, T> T findNextFocusInRelativeDirection(L paramL, CollectionAdapter<L, T> paramCollectionAdapter, BoundsAdapter<T> paramBoundsAdapter, T paramT, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
    int i = paramCollectionAdapter.size(paramL);
    ArrayList<?> arrayList = new ArrayList(i);
    for (byte b = 0; b < i; b++)
      arrayList.add(paramCollectionAdapter.get(paramL, b)); 
    Collections.sort(arrayList, new SequentialComparator(paramBoolean1, paramBoundsAdapter));
    if (paramInt != 1) {
      if (paramInt == 2)
        return getNextFocusable(paramT, (ArrayList)arrayList, paramBoolean2); 
      throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD}.");
    } 
    return getPreviousFocusable(paramT, (ArrayList)arrayList, paramBoolean2);
  }
  
  private static <T> T getNextFocusable(T paramT, ArrayList<T> paramArrayList, boolean paramBoolean) {
    int j;
    int i = paramArrayList.size();
    if (paramT == null) {
      j = -1;
    } else {
      j = paramArrayList.lastIndexOf(paramT);
    } 
    return (++j < i) ? paramArrayList.get(j) : ((paramBoolean && i > 0) ? paramArrayList.get(0) : null);
  }
  
  private static <T> T getPreviousFocusable(T paramT, ArrayList<T> paramArrayList, boolean paramBoolean) {
    int j;
    int i = paramArrayList.size();
    if (paramT == null) {
      j = i;
    } else {
      j = paramArrayList.indexOf(paramT);
    } 
    return (--j >= 0) ? paramArrayList.get(j) : ((paramBoolean && i > 0) ? paramArrayList.get(i - 1) : null);
  }
  
  private static int getWeightedDistanceFor(int paramInt1, int paramInt2) {
    return paramInt1 * 13 * paramInt1 + paramInt2 * paramInt2;
  }
  
  private static boolean isBetterCandidate(int paramInt, Rect paramRect1, Rect paramRect2, Rect paramRect3) {
    boolean bool = isCandidate(paramRect1, paramRect2, paramInt);
    boolean bool1 = false;
    if (!bool)
      return false; 
    if (!isCandidate(paramRect1, paramRect3, paramInt))
      return true; 
    if (beamBeats(paramInt, paramRect1, paramRect2, paramRect3))
      return true; 
    if (beamBeats(paramInt, paramRect1, paramRect3, paramRect2))
      return false; 
    if (getWeightedDistanceFor(majorAxisDistance(paramInt, paramRect1, paramRect2), minorAxisDistance(paramInt, paramRect1, paramRect2)) < getWeightedDistanceFor(majorAxisDistance(paramInt, paramRect1, paramRect3), minorAxisDistance(paramInt, paramRect1, paramRect3)))
      bool1 = true; 
    return bool1;
  }
  
  private static boolean isCandidate(Rect paramRect1, Rect paramRect2, int paramInt) {
    boolean bool1 = true;
    boolean bool2 = true;
    boolean bool3 = true;
    boolean bool4 = true;
    if (paramInt != 17) {
      if (paramInt != 33) {
        if (paramInt != 66) {
          if (paramInt == 130) {
            if ((paramRect1.top >= paramRect2.top && paramRect1.bottom > paramRect2.top) || paramRect1.bottom >= paramRect2.bottom)
              bool4 = false; 
            return bool4;
          } 
          throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        } 
        if ((paramRect1.left < paramRect2.left || paramRect1.right <= paramRect2.left) && paramRect1.right < paramRect2.right) {
          bool4 = bool1;
        } else {
          bool4 = false;
        } 
        return bool4;
      } 
      if ((paramRect1.bottom > paramRect2.bottom || paramRect1.top >= paramRect2.bottom) && paramRect1.top > paramRect2.top) {
        bool4 = bool2;
      } else {
        bool4 = false;
      } 
      return bool4;
    } 
    if ((paramRect1.right > paramRect2.right || paramRect1.left >= paramRect2.right) && paramRect1.left > paramRect2.left) {
      bool4 = bool3;
    } else {
      bool4 = false;
    } 
    return bool4;
  }
  
  private static boolean isToDirectionOf(int paramInt, Rect paramRect1, Rect paramRect2) {
    boolean bool1 = true;
    boolean bool2 = true;
    boolean bool3 = true;
    boolean bool4 = true;
    if (paramInt != 17) {
      if (paramInt != 33) {
        if (paramInt != 66) {
          if (paramInt == 130) {
            if (paramRect1.bottom > paramRect2.top)
              bool4 = false; 
            return bool4;
          } 
          throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        } 
        if (paramRect1.right <= paramRect2.left) {
          bool4 = bool1;
        } else {
          bool4 = false;
        } 
        return bool4;
      } 
      if (paramRect1.top >= paramRect2.bottom) {
        bool4 = bool2;
      } else {
        bool4 = false;
      } 
      return bool4;
    } 
    if (paramRect1.left >= paramRect2.right) {
      bool4 = bool3;
    } else {
      bool4 = false;
    } 
    return bool4;
  }
  
  private static int majorAxisDistance(int paramInt, Rect paramRect1, Rect paramRect2) {
    return Math.max(0, majorAxisDistanceRaw(paramInt, paramRect1, paramRect2));
  }
  
  private static int majorAxisDistanceRaw(int paramInt, Rect paramRect1, Rect paramRect2) {
    if (paramInt != 17) {
      if (paramInt != 33) {
        if (paramInt != 66) {
          if (paramInt == 130) {
            paramInt = paramRect2.top;
            int m = paramRect1.bottom;
            return paramInt - m;
          } 
          throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        } 
        paramInt = paramRect2.left;
        int k = paramRect1.right;
        return paramInt - k;
      } 
      paramInt = paramRect1.top;
      int j = paramRect2.bottom;
      return paramInt - j;
    } 
    paramInt = paramRect1.left;
    int i = paramRect2.right;
    return paramInt - i;
  }
  
  private static int majorAxisDistanceToFarEdge(int paramInt, Rect paramRect1, Rect paramRect2) {
    return Math.max(1, majorAxisDistanceToFarEdgeRaw(paramInt, paramRect1, paramRect2));
  }
  
  private static int majorAxisDistanceToFarEdgeRaw(int paramInt, Rect paramRect1, Rect paramRect2) {
    if (paramInt != 17) {
      if (paramInt != 33) {
        if (paramInt != 66) {
          if (paramInt == 130) {
            paramInt = paramRect2.bottom;
            int m = paramRect1.bottom;
            return paramInt - m;
          } 
          throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        } 
        paramInt = paramRect2.right;
        int k = paramRect1.right;
        return paramInt - k;
      } 
      paramInt = paramRect1.top;
      int j = paramRect2.top;
      return paramInt - j;
    } 
    paramInt = paramRect1.left;
    int i = paramRect2.left;
    return paramInt - i;
  }
  
  private static int minorAxisDistance(int paramInt, Rect paramRect1, Rect paramRect2) {
    if (paramInt != 17)
      if (paramInt != 33) {
        if (paramInt != 66) {
          if (paramInt != 130)
            throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}."); 
          return Math.abs(paramRect1.left + paramRect1.width() / 2 - paramRect2.left + paramRect2.width() / 2);
        } 
      } else {
        return Math.abs(paramRect1.left + paramRect1.width() / 2 - paramRect2.left + paramRect2.width() / 2);
      }  
    return Math.abs(paramRect1.top + paramRect1.height() / 2 - paramRect2.top + paramRect2.height() / 2);
  }
  
  public static interface BoundsAdapter<T> {
    void obtainBounds(T param1T, Rect param1Rect);
  }
  
  public static interface CollectionAdapter<T, V> {
    V get(T param1T, int param1Int);
    
    int size(T param1T);
  }
  
  private static class SequentialComparator<T> implements Comparator<T> {
    private final FocusStrategy.BoundsAdapter<T> mAdapter;
    
    private final boolean mIsLayoutRtl;
    
    private final Rect mTemp1 = new Rect();
    
    private final Rect mTemp2 = new Rect();
    
    SequentialComparator(boolean param1Boolean, FocusStrategy.BoundsAdapter<T> param1BoundsAdapter) {
      this.mIsLayoutRtl = param1Boolean;
      this.mAdapter = param1BoundsAdapter;
    }
    
    public int compare(T param1T1, T param1T2) {
      Rect rect1 = this.mTemp1;
      Rect rect2 = this.mTemp2;
      this.mAdapter.obtainBounds(param1T1, rect1);
      this.mAdapter.obtainBounds(param1T2, rect2);
      int i = rect1.top;
      int j = rect2.top;
      byte b = -1;
      if (i < j)
        return -1; 
      if (rect1.top > rect2.top)
        return 1; 
      if (rect1.left < rect2.left) {
        if (this.mIsLayoutRtl)
          b = 1; 
        return b;
      } 
      if (rect1.left > rect2.left) {
        if (!this.mIsLayoutRtl)
          b = 1; 
        return b;
      } 
      if (rect1.bottom < rect2.bottom)
        return -1; 
      if (rect1.bottom > rect2.bottom)
        return 1; 
      if (rect1.right < rect2.right) {
        if (this.mIsLayoutRtl)
          b = 1; 
        return b;
      } 
      if (rect1.right > rect2.right) {
        if (!this.mIsLayoutRtl)
          b = 1; 
        return b;
      } 
      return 0;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\FocusStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */