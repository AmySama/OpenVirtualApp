package android.support.v7.widget;

import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

class ChildHelper {
  private static final boolean DEBUG = false;
  
  private static final String TAG = "ChildrenHelper";
  
  final Bucket mBucket;
  
  final Callback mCallback;
  
  final List<View> mHiddenViews;
  
  ChildHelper(Callback paramCallback) {
    this.mCallback = paramCallback;
    this.mBucket = new Bucket();
    this.mHiddenViews = new ArrayList<View>();
  }
  
  private int getOffset(int paramInt) {
    if (paramInt < 0)
      return -1; 
    int i = this.mCallback.getChildCount();
    for (int j = paramInt; j < i; j += k) {
      int k = paramInt - j - this.mBucket.countOnesBefore(j);
      if (k == 0) {
        while (this.mBucket.get(j))
          j++; 
        return j;
      } 
    } 
    return -1;
  }
  
  private void hideViewInternal(View paramView) {
    this.mHiddenViews.add(paramView);
    this.mCallback.onEnteredHiddenState(paramView);
  }
  
  private boolean unhideViewInternal(View paramView) {
    if (this.mHiddenViews.remove(paramView)) {
      this.mCallback.onLeftHiddenState(paramView);
      return true;
    } 
    return false;
  }
  
  void addView(View paramView, int paramInt, boolean paramBoolean) {
    if (paramInt < 0) {
      paramInt = this.mCallback.getChildCount();
    } else {
      paramInt = getOffset(paramInt);
    } 
    this.mBucket.insert(paramInt, paramBoolean);
    if (paramBoolean)
      hideViewInternal(paramView); 
    this.mCallback.addView(paramView, paramInt);
  }
  
  void addView(View paramView, boolean paramBoolean) {
    addView(paramView, -1, paramBoolean);
  }
  
  void attachViewToParent(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams, boolean paramBoolean) {
    if (paramInt < 0) {
      paramInt = this.mCallback.getChildCount();
    } else {
      paramInt = getOffset(paramInt);
    } 
    this.mBucket.insert(paramInt, paramBoolean);
    if (paramBoolean)
      hideViewInternal(paramView); 
    this.mCallback.attachViewToParent(paramView, paramInt, paramLayoutParams);
  }
  
  void detachViewFromParent(int paramInt) {
    paramInt = getOffset(paramInt);
    this.mBucket.remove(paramInt);
    this.mCallback.detachViewFromParent(paramInt);
  }
  
  View findHiddenNonRemovedView(int paramInt) {
    int i = this.mHiddenViews.size();
    for (byte b = 0; b < i; b++) {
      View view = this.mHiddenViews.get(b);
      RecyclerView.ViewHolder viewHolder = this.mCallback.getChildViewHolder(view);
      if (viewHolder.getLayoutPosition() == paramInt && !viewHolder.isInvalid() && !viewHolder.isRemoved())
        return view; 
    } 
    return null;
  }
  
  View getChildAt(int paramInt) {
    paramInt = getOffset(paramInt);
    return this.mCallback.getChildAt(paramInt);
  }
  
  int getChildCount() {
    return this.mCallback.getChildCount() - this.mHiddenViews.size();
  }
  
  View getUnfilteredChildAt(int paramInt) {
    return this.mCallback.getChildAt(paramInt);
  }
  
  int getUnfilteredChildCount() {
    return this.mCallback.getChildCount();
  }
  
  void hide(View paramView) {
    int i = this.mCallback.indexOfChild(paramView);
    if (i >= 0) {
      this.mBucket.set(i);
      hideViewInternal(paramView);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("view is not a child, cannot hide ");
    stringBuilder.append(paramView);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  int indexOfChild(View paramView) {
    int i = this.mCallback.indexOfChild(paramView);
    return (i == -1) ? -1 : (this.mBucket.get(i) ? -1 : (i - this.mBucket.countOnesBefore(i)));
  }
  
  boolean isHidden(View paramView) {
    return this.mHiddenViews.contains(paramView);
  }
  
  void removeAllViewsUnfiltered() {
    this.mBucket.reset();
    for (int i = this.mHiddenViews.size() - 1; i >= 0; i--) {
      this.mCallback.onLeftHiddenState(this.mHiddenViews.get(i));
      this.mHiddenViews.remove(i);
    } 
    this.mCallback.removeAllViews();
  }
  
  void removeView(View paramView) {
    int i = this.mCallback.indexOfChild(paramView);
    if (i < 0)
      return; 
    if (this.mBucket.remove(i))
      unhideViewInternal(paramView); 
    this.mCallback.removeViewAt(i);
  }
  
  void removeViewAt(int paramInt) {
    paramInt = getOffset(paramInt);
    View view = this.mCallback.getChildAt(paramInt);
    if (view == null)
      return; 
    if (this.mBucket.remove(paramInt))
      unhideViewInternal(view); 
    this.mCallback.removeViewAt(paramInt);
  }
  
  boolean removeViewIfHidden(View paramView) {
    int i = this.mCallback.indexOfChild(paramView);
    if (i == -1) {
      unhideViewInternal(paramView);
      return true;
    } 
    if (this.mBucket.get(i)) {
      this.mBucket.remove(i);
      unhideViewInternal(paramView);
      this.mCallback.removeViewAt(i);
      return true;
    } 
    return false;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.mBucket.toString());
    stringBuilder.append(", hidden list:");
    stringBuilder.append(this.mHiddenViews.size());
    return stringBuilder.toString();
  }
  
  void unhide(View paramView) {
    int i = this.mCallback.indexOfChild(paramView);
    if (i >= 0) {
      if (this.mBucket.get(i)) {
        this.mBucket.clear(i);
        unhideViewInternal(paramView);
        return;
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("trying to unhide a view that was not hidden");
      stringBuilder1.append(paramView);
      throw new RuntimeException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("view is not a child, cannot hide ");
    stringBuilder.append(paramView);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  static class Bucket {
    static final int BITS_PER_WORD = 64;
    
    static final long LAST_BIT = -9223372036854775808L;
    
    long mData = 0L;
    
    Bucket mNext;
    
    private void ensureNext() {
      if (this.mNext == null)
        this.mNext = new Bucket(); 
    }
    
    void clear(int param1Int) {
      if (param1Int >= 64) {
        Bucket bucket = this.mNext;
        if (bucket != null)
          bucket.clear(param1Int - 64); 
      } else {
        this.mData &= 1L << param1Int;
      } 
    }
    
    int countOnesBefore(int param1Int) {
      Bucket bucket = this.mNext;
      return (bucket == null) ? ((param1Int >= 64) ? Long.bitCount(this.mData) : Long.bitCount(this.mData & (1L << param1Int) - 1L)) : ((param1Int < 64) ? Long.bitCount(this.mData & (1L << param1Int) - 1L) : (bucket.countOnesBefore(param1Int - 64) + Long.bitCount(this.mData)));
    }
    
    boolean get(int param1Int) {
      boolean bool;
      if (param1Int >= 64) {
        ensureNext();
        return this.mNext.get(param1Int - 64);
      } 
      if ((this.mData & 1L << param1Int) != 0L) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    void insert(int param1Int, boolean param1Boolean) {
      if (param1Int >= 64) {
        ensureNext();
        this.mNext.insert(param1Int - 64, param1Boolean);
      } else {
        boolean bool;
        if ((this.mData & Long.MIN_VALUE) != 0L) {
          bool = true;
        } else {
          bool = false;
        } 
        long l1 = (1L << param1Int) - 1L;
        long l2 = this.mData;
        this.mData = (l2 & l1) << 1L | l2 & l1;
        if (param1Boolean) {
          set(param1Int);
        } else {
          clear(param1Int);
        } 
        if (bool || this.mNext != null) {
          ensureNext();
          this.mNext.insert(0, bool);
        } 
      } 
    }
    
    boolean remove(int param1Int) {
      boolean bool;
      if (param1Int >= 64) {
        ensureNext();
        return this.mNext.remove(param1Int - 64);
      } 
      long l1 = 1L << param1Int;
      if ((this.mData & l1) != 0L) {
        bool = true;
      } else {
        bool = false;
      } 
      long l2 = this.mData & l1;
      this.mData = l2;
      l1--;
      this.mData = l2 & l1 | Long.rotateRight(l1 & l2, 1);
      Bucket bucket = this.mNext;
      if (bucket != null) {
        if (bucket.get(0))
          set(63); 
        this.mNext.remove(0);
      } 
      return bool;
    }
    
    void reset() {
      this.mData = 0L;
      Bucket bucket = this.mNext;
      if (bucket != null)
        bucket.reset(); 
    }
    
    void set(int param1Int) {
      if (param1Int >= 64) {
        ensureNext();
        this.mNext.set(param1Int - 64);
      } else {
        this.mData |= 1L << param1Int;
      } 
    }
    
    public String toString() {
      String str;
      if (this.mNext == null) {
        str = Long.toBinaryString(this.mData);
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mNext.toString());
        stringBuilder.append("xx");
        stringBuilder.append(Long.toBinaryString(this.mData));
        str = stringBuilder.toString();
      } 
      return str;
    }
  }
  
  static interface Callback {
    void addView(View param1View, int param1Int);
    
    void attachViewToParent(View param1View, int param1Int, ViewGroup.LayoutParams param1LayoutParams);
    
    void detachViewFromParent(int param1Int);
    
    View getChildAt(int param1Int);
    
    int getChildCount();
    
    RecyclerView.ViewHolder getChildViewHolder(View param1View);
    
    int indexOfChild(View param1View);
    
    void onEnteredHiddenState(View param1View);
    
    void onLeftHiddenState(View param1View);
    
    void removeAllViews();
    
    void removeViewAt(int param1Int);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\ChildHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */