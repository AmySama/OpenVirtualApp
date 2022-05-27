package io.virtualapp.widgets;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

public class SquareCardView extends CardView {
  public SquareCardView(Context paramContext) {
    super(paramContext);
  }
  
  public SquareCardView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public SquareCardView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    setMeasuredDimension(getDefaultSize(0, paramInt1), getDefaultSize(0, paramInt2));
    paramInt1 = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
    super.onMeasure(paramInt1, paramInt1);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\SquareCardView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */