package android.support.v7.widget;

import android.os.Build;
import android.view.View;

public class TooltipCompat {
  public static void setTooltipText(View paramView, CharSequence paramCharSequence) {
    if (Build.VERSION.SDK_INT >= 26) {
      paramView.setTooltipText(paramCharSequence);
    } else {
      TooltipCompatHandler.setTooltipText(paramView, paramCharSequence);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\TooltipCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */