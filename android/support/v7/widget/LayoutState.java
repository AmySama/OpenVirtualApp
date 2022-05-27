package android.support.v7.widget;

import android.view.View;

class LayoutState {
  static final int INVALID_LAYOUT = -2147483648;
  
  static final int ITEM_DIRECTION_HEAD = -1;
  
  static final int ITEM_DIRECTION_TAIL = 1;
  
  static final int LAYOUT_END = 1;
  
  static final int LAYOUT_START = -1;
  
  static final String TAG = "LayoutState";
  
  int mAvailable;
  
  int mCurrentPosition;
  
  int mEndLine = 0;
  
  boolean mInfinite;
  
  int mItemDirection;
  
  int mLayoutDirection;
  
  boolean mRecycle = true;
  
  int mStartLine = 0;
  
  boolean mStopInFocusable;
  
  boolean hasMore(RecyclerView.State paramState) {
    boolean bool;
    int i = this.mCurrentPosition;
    if (i >= 0 && i < paramState.getItemCount()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  View next(RecyclerView.Recycler paramRecycler) {
    View view = paramRecycler.getViewForPosition(this.mCurrentPosition);
    this.mCurrentPosition += this.mItemDirection;
    return view;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("LayoutState{mAvailable=");
    stringBuilder.append(this.mAvailable);
    stringBuilder.append(", mCurrentPosition=");
    stringBuilder.append(this.mCurrentPosition);
    stringBuilder.append(", mItemDirection=");
    stringBuilder.append(this.mItemDirection);
    stringBuilder.append(", mLayoutDirection=");
    stringBuilder.append(this.mLayoutDirection);
    stringBuilder.append(", mStartLine=");
    stringBuilder.append(this.mStartLine);
    stringBuilder.append(", mEndLine=");
    stringBuilder.append(this.mEndLine);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\LayoutState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */