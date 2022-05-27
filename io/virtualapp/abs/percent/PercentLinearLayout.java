package io.virtualapp.abs.percent;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.percent.PercentLayoutHelper;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class PercentLinearLayout extends LinearLayout {
  private PercentLayoutHelper mPercentLayoutHelper = new PercentLayoutHelper((ViewGroup)this);
  
  public PercentLinearLayout(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mPercentLayoutHelper.restoreOriginalParams();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    this.mPercentLayoutHelper.adjustChildren(paramInt1, paramInt2);
    super.onMeasure(paramInt1, paramInt2);
    if (this.mPercentLayoutHelper.handleMeasuredStateTooSmall())
      super.onMeasure(paramInt1, paramInt2); 
  }
  
  public static class LayoutParams extends LinearLayout.LayoutParams implements PercentLayoutHelper.PercentLayoutParams {
    private PercentLayoutHelper.PercentLayoutInfo mPercentLayoutInfo;
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      this.mPercentLayoutInfo = PercentLayoutHelper.getPercentLayoutInfo(param1Context, param1AttributeSet);
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams param1MarginLayoutParams) {
      super(param1MarginLayoutParams);
    }
    
    public PercentLayoutHelper.PercentLayoutInfo getPercentLayoutInfo() {
      return this.mPercentLayoutInfo;
    }
    
    protected void setBaseAttributes(TypedArray param1TypedArray, int param1Int1, int param1Int2) {
      PercentLayoutHelper.fetchWidthAndHeight((ViewGroup.LayoutParams)this, param1TypedArray, param1Int1, param1Int2);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\abs\percent\PercentLinearLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */