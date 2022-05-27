package android.support.v4.widget;

import android.os.Build;
import android.view.View;
import android.widget.ListView;

public final class ListViewCompat {
  public static boolean canScrollList(ListView paramListView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 19)
      return paramListView.canScrollList(paramInt); 
    int i = paramListView.getChildCount();
    boolean bool = false;
    null = false;
    if (i == 0)
      return false; 
    int j = paramListView.getFirstVisiblePosition();
    if (paramInt > 0) {
      paramInt = paramListView.getChildAt(i - 1).getBottom();
      if (j + i < paramListView.getCount() || paramInt > paramListView.getHeight() - paramListView.getListPaddingBottom())
        null = true; 
      return null;
    } 
    paramInt = paramListView.getChildAt(0).getTop();
    if (j <= 0) {
      null = bool;
      return (paramInt < paramListView.getListPaddingTop()) ? true : null;
    } 
    return true;
  }
  
  public static void scrollListBy(ListView paramListView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 19) {
      paramListView.scrollListBy(paramInt);
    } else {
      int i = paramListView.getFirstVisiblePosition();
      if (i == -1)
        return; 
      View view = paramListView.getChildAt(0);
      if (view == null)
        return; 
      paramListView.setSelectionFromTop(i, view.getTop() - paramInt);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\ListViewCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */