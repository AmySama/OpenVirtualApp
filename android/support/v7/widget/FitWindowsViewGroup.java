package android.support.v7.widget;

import android.graphics.Rect;

public interface FitWindowsViewGroup {
  void setOnFitSystemWindowsListener(OnFitSystemWindowsListener paramOnFitSystemWindowsListener);
  
  public static interface OnFitSystemWindowsListener {
    void onFitSystemWindows(Rect param1Rect);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\FitWindowsViewGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */