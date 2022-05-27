package io.virtualapp.Utils;

import android.view.View;

public class ViewUtils {
  private static void calculateViewMeasure(View paramView) {
    paramView.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
  }
  
  public static int getViewMeasuredHeight(View paramView) {
    calculateViewMeasure(paramView);
    return paramView.getMeasuredHeight();
  }
  
  public static int getViewMeasuredWidth(View paramView) {
    calculateViewMeasure(paramView);
    return paramView.getMeasuredWidth();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\ViewUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */