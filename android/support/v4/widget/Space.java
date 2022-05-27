package android.support.v4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

@Deprecated
public class Space extends View {
  @Deprecated
  public Space(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  @Deprecated
  public Space(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  @Deprecated
  public Space(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    if (getVisibility() == 0)
      setVisibility(4); 
  }
  
  private static int getDefaultSize2(int paramInt1, int paramInt2) {
    int i = View.MeasureSpec.getMode(paramInt2);
    paramInt2 = View.MeasureSpec.getSize(paramInt2);
    if (i != Integer.MIN_VALUE) {
      if (i == 1073741824)
        paramInt1 = paramInt2; 
    } else {
      paramInt1 = Math.min(paramInt1, paramInt2);
    } 
    return paramInt1;
  }
  
  @Deprecated
  public void draw(Canvas paramCanvas) {}
  
  @Deprecated
  protected void onMeasure(int paramInt1, int paramInt2) {
    setMeasuredDimension(getDefaultSize2(getSuggestedMinimumWidth(), paramInt1), getDefaultSize2(getSuggestedMinimumHeight(), paramInt2));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\Space.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */