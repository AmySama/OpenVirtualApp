package android.support.transition;

import android.os.Build;
import android.view.ViewGroup;

class ViewGroupUtils {
  private static final ViewGroupUtilsImpl IMPL;
  
  static {
    if (Build.VERSION.SDK_INT >= 18) {
      IMPL = new ViewGroupUtilsApi18();
    } else {
      IMPL = new ViewGroupUtilsApi14();
    } 
  }
  
  static ViewGroupOverlayImpl getOverlay(ViewGroup paramViewGroup) {
    return IMPL.getOverlay(paramViewGroup);
  }
  
  static void suppressLayout(ViewGroup paramViewGroup, boolean paramBoolean) {
    IMPL.suppressLayout(paramViewGroup, paramBoolean);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ViewGroupUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */