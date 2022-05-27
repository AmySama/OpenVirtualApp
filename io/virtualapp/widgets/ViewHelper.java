package io.virtualapp.widgets;

import io.virtualapp.App;

public class ViewHelper {
  public static int dip2px(float paramFloat) {
    return (int)(paramFloat * (App.getApp().getResources().getDisplayMetrics()).density + 0.5F);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\ViewHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */