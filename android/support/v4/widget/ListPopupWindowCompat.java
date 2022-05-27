package android.support.v4.widget;

import android.os.Build;
import android.view.View;
import android.widget.ListPopupWindow;

public final class ListPopupWindowCompat {
  public static View.OnTouchListener createDragToOpenListener(ListPopupWindow paramListPopupWindow, View paramView) {
    return (Build.VERSION.SDK_INT >= 19) ? paramListPopupWindow.createDragToOpenListener(paramView) : null;
  }
  
  @Deprecated
  public static View.OnTouchListener createDragToOpenListener(Object paramObject, View paramView) {
    return createDragToOpenListener((ListPopupWindow)paramObject, paramView);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\ListPopupWindowCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */