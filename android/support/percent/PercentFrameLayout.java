package android.support.percent;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

@Deprecated
public class PercentFrameLayout extends FrameLayout {
  private final PercentLayoutHelper mHelper = new PercentLayoutHelper((ViewGroup)this);
  
  public PercentFrameLayout(Context paramContext) {
    super(paramContext);
  }
  
  public PercentFrameLayout(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public PercentFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected LayoutParams generateDefaultLayoutParams() {
    return new LayoutParams(-1, -1);
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mHelper.restoreOriginalParams();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    this.mHelper.adjustChildren(paramInt1, paramInt2);
    super.onMeasure(paramInt1, paramInt2);
    if (this.mHelper.handleMeasuredStateTooSmall())
      super.onMeasure(paramInt1, paramInt2); 
  }
  
  @Deprecated
  public static class LayoutParams extends FrameLayout.LayoutParams implements PercentLayoutHelper.PercentLayoutParams {
    private PercentLayoutHelper.PercentLayoutInfo mPercentLayoutInfo;
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
    }
    
    public LayoutParams(int param1Int1, int param1Int2, int param1Int3) {
      super(param1Int1, param1Int2, param1Int3);
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      this.mPercentLayoutInfo = PercentLayoutHelper.getPercentLayoutInfo(param1Context, param1AttributeSet);
    }
    
    public LayoutParams(LayoutParams param1LayoutParams) {
      this(param1LayoutParams);
      this.mPercentLayoutInfo = param1LayoutParams.mPercentLayoutInfo;
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams param1MarginLayoutParams) {
      super(param1MarginLayoutParams);
    }
    
    public LayoutParams(FrameLayout.LayoutParams param1LayoutParams) {
      super((ViewGroup.MarginLayoutParams)param1LayoutParams);
      this.gravity = param1LayoutParams.gravity;
    }
    
    public PercentLayoutHelper.PercentLayoutInfo getPercentLayoutInfo() {
      if (this.mPercentLayoutInfo == null)
        this.mPercentLayoutInfo = new PercentLayoutHelper.PercentLayoutInfo(); 
      return this.mPercentLayoutInfo;
    }
    
    protected void setBaseAttributes(TypedArray param1TypedArray, int param1Int1, int param1Int2) {
      PercentLayoutHelper.fetchWidthAndHeight((ViewGroup.LayoutParams)this, param1TypedArray, param1Int1, param1Int2);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\percent\PercentFrameLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */