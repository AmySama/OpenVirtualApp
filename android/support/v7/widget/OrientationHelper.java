package android.support.v7.widget;

import android.graphics.Rect;
import android.view.View;

public abstract class OrientationHelper {
  public static final int HORIZONTAL = 0;
  
  private static final int INVALID_SIZE = -2147483648;
  
  public static final int VERTICAL = 1;
  
  private int mLastTotalSpace = Integer.MIN_VALUE;
  
  protected final RecyclerView.LayoutManager mLayoutManager;
  
  final Rect mTmpRect = new Rect();
  
  private OrientationHelper(RecyclerView.LayoutManager paramLayoutManager) {
    this.mLayoutManager = paramLayoutManager;
  }
  
  public static OrientationHelper createHorizontalHelper(RecyclerView.LayoutManager paramLayoutManager) {
    return new OrientationHelper(paramLayoutManager) {
        public int getDecoratedEnd(View param1View) {
          RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param1View.getLayoutParams();
          return this.mLayoutManager.getDecoratedRight(param1View) + layoutParams.rightMargin;
        }
        
        public int getDecoratedMeasurement(View param1View) {
          RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param1View.getLayoutParams();
          return this.mLayoutManager.getDecoratedMeasuredWidth(param1View) + layoutParams.leftMargin + layoutParams.rightMargin;
        }
        
        public int getDecoratedMeasurementInOther(View param1View) {
          RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param1View.getLayoutParams();
          return this.mLayoutManager.getDecoratedMeasuredHeight(param1View) + layoutParams.topMargin + layoutParams.bottomMargin;
        }
        
        public int getDecoratedStart(View param1View) {
          RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param1View.getLayoutParams();
          return this.mLayoutManager.getDecoratedLeft(param1View) - layoutParams.leftMargin;
        }
        
        public int getEnd() {
          return this.mLayoutManager.getWidth();
        }
        
        public int getEndAfterPadding() {
          return this.mLayoutManager.getWidth() - this.mLayoutManager.getPaddingRight();
        }
        
        public int getEndPadding() {
          return this.mLayoutManager.getPaddingRight();
        }
        
        public int getMode() {
          return this.mLayoutManager.getWidthMode();
        }
        
        public int getModeInOther() {
          return this.mLayoutManager.getHeightMode();
        }
        
        public int getStartAfterPadding() {
          return this.mLayoutManager.getPaddingLeft();
        }
        
        public int getTotalSpace() {
          return this.mLayoutManager.getWidth() - this.mLayoutManager.getPaddingLeft() - this.mLayoutManager.getPaddingRight();
        }
        
        public int getTransformedEndWithDecoration(View param1View) {
          this.mLayoutManager.getTransformedBoundingBox(param1View, true, this.mTmpRect);
          return this.mTmpRect.right;
        }
        
        public int getTransformedStartWithDecoration(View param1View) {
          this.mLayoutManager.getTransformedBoundingBox(param1View, true, this.mTmpRect);
          return this.mTmpRect.left;
        }
        
        public void offsetChild(View param1View, int param1Int) {
          param1View.offsetLeftAndRight(param1Int);
        }
        
        public void offsetChildren(int param1Int) {
          this.mLayoutManager.offsetChildrenHorizontal(param1Int);
        }
      };
  }
  
  public static OrientationHelper createOrientationHelper(RecyclerView.LayoutManager paramLayoutManager, int paramInt) {
    if (paramInt != 0) {
      if (paramInt == 1)
        return createVerticalHelper(paramLayoutManager); 
      throw new IllegalArgumentException("invalid orientation");
    } 
    return createHorizontalHelper(paramLayoutManager);
  }
  
  public static OrientationHelper createVerticalHelper(RecyclerView.LayoutManager paramLayoutManager) {
    return new OrientationHelper(paramLayoutManager) {
        public int getDecoratedEnd(View param1View) {
          RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param1View.getLayoutParams();
          return this.mLayoutManager.getDecoratedBottom(param1View) + layoutParams.bottomMargin;
        }
        
        public int getDecoratedMeasurement(View param1View) {
          RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param1View.getLayoutParams();
          return this.mLayoutManager.getDecoratedMeasuredHeight(param1View) + layoutParams.topMargin + layoutParams.bottomMargin;
        }
        
        public int getDecoratedMeasurementInOther(View param1View) {
          RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param1View.getLayoutParams();
          return this.mLayoutManager.getDecoratedMeasuredWidth(param1View) + layoutParams.leftMargin + layoutParams.rightMargin;
        }
        
        public int getDecoratedStart(View param1View) {
          RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param1View.getLayoutParams();
          return this.mLayoutManager.getDecoratedTop(param1View) - layoutParams.topMargin;
        }
        
        public int getEnd() {
          return this.mLayoutManager.getHeight();
        }
        
        public int getEndAfterPadding() {
          return this.mLayoutManager.getHeight() - this.mLayoutManager.getPaddingBottom();
        }
        
        public int getEndPadding() {
          return this.mLayoutManager.getPaddingBottom();
        }
        
        public int getMode() {
          return this.mLayoutManager.getHeightMode();
        }
        
        public int getModeInOther() {
          return this.mLayoutManager.getWidthMode();
        }
        
        public int getStartAfterPadding() {
          return this.mLayoutManager.getPaddingTop();
        }
        
        public int getTotalSpace() {
          return this.mLayoutManager.getHeight() - this.mLayoutManager.getPaddingTop() - this.mLayoutManager.getPaddingBottom();
        }
        
        public int getTransformedEndWithDecoration(View param1View) {
          this.mLayoutManager.getTransformedBoundingBox(param1View, true, this.mTmpRect);
          return this.mTmpRect.bottom;
        }
        
        public int getTransformedStartWithDecoration(View param1View) {
          this.mLayoutManager.getTransformedBoundingBox(param1View, true, this.mTmpRect);
          return this.mTmpRect.top;
        }
        
        public void offsetChild(View param1View, int param1Int) {
          param1View.offsetTopAndBottom(param1Int);
        }
        
        public void offsetChildren(int param1Int) {
          this.mLayoutManager.offsetChildrenVertical(param1Int);
        }
      };
  }
  
  public abstract int getDecoratedEnd(View paramView);
  
  public abstract int getDecoratedMeasurement(View paramView);
  
  public abstract int getDecoratedMeasurementInOther(View paramView);
  
  public abstract int getDecoratedStart(View paramView);
  
  public abstract int getEnd();
  
  public abstract int getEndAfterPadding();
  
  public abstract int getEndPadding();
  
  public RecyclerView.LayoutManager getLayoutManager() {
    return this.mLayoutManager;
  }
  
  public abstract int getMode();
  
  public abstract int getModeInOther();
  
  public abstract int getStartAfterPadding();
  
  public abstract int getTotalSpace();
  
  public int getTotalSpaceChange() {
    int i;
    if (Integer.MIN_VALUE == this.mLastTotalSpace) {
      i = 0;
    } else {
      i = getTotalSpace() - this.mLastTotalSpace;
    } 
    return i;
  }
  
  public abstract int getTransformedEndWithDecoration(View paramView);
  
  public abstract int getTransformedStartWithDecoration(View paramView);
  
  public abstract void offsetChild(View paramView, int paramInt);
  
  public abstract void offsetChildren(int paramInt);
  
  public void onLayoutComplete() {
    this.mLastTotalSpace = getTotalSpace();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\OrientationHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */