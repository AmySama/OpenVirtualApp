package android.support.design.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class BaselineLayout extends ViewGroup {
  private int mBaseline = -1;
  
  public BaselineLayout(Context paramContext) {
    super(paramContext, null, 0);
  }
  
  public BaselineLayout(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet, 0);
  }
  
  public BaselineLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public int getBaseline() {
    return this.mBaseline;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = getChildCount();
    int j = getPaddingLeft();
    int k = getPaddingRight();
    int m = getPaddingTop();
    for (paramInt2 = 0; paramInt2 < i; paramInt2++) {
      View view = getChildAt(paramInt2);
      if (view.getVisibility() != 8) {
        int n = view.getMeasuredWidth();
        int i1 = view.getMeasuredHeight();
        int i2 = (paramInt3 - paramInt1 - k - j - n) / 2 + j;
        if (this.mBaseline != -1 && view.getBaseline() != -1) {
          paramInt4 = this.mBaseline + m - view.getBaseline();
        } else {
          paramInt4 = m;
        } 
        view.layout(i2, paramInt4, n + i2, i1 + paramInt4);
      } 
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    int i = getChildCount();
    byte b = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = -1;
    int i1 = -1;
    while (b < i) {
      View view = getChildAt(b);
      if (view.getVisibility() != 8) {
        measureChild(view, paramInt1, paramInt2);
        int i3 = view.getBaseline();
        int i4 = n;
        int i5 = i1;
        if (i3 != -1) {
          i4 = Math.max(n, i3);
          i5 = Math.max(i1, view.getMeasuredHeight() - i3);
        } 
        k = Math.max(k, view.getMeasuredWidth());
        j = Math.max(j, view.getMeasuredHeight());
        m = View.combineMeasuredStates(m, view.getMeasuredState());
        i1 = i5;
        n = i4;
      } 
      b++;
    } 
    int i2 = j;
    if (n != -1) {
      i2 = Math.max(j, Math.max(i1, getPaddingBottom()) + n);
      this.mBaseline = n;
    } 
    i1 = Math.max(i2, getSuggestedMinimumHeight());
    setMeasuredDimension(View.resolveSizeAndState(Math.max(k, getSuggestedMinimumWidth()), paramInt1, m), View.resolveSizeAndState(i1, paramInt2, m << 16));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\internal\BaselineLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */