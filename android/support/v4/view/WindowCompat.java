package android.support.v4.view;

import android.view.View;
import android.view.Window;

public final class WindowCompat {
  public static final int FEATURE_ACTION_BAR = 8;
  
  public static final int FEATURE_ACTION_BAR_OVERLAY = 9;
  
  public static final int FEATURE_ACTION_MODE_OVERLAY = 10;
  
  public static <T extends View> T requireViewById(Window paramWindow, int paramInt) {
    View view = paramWindow.findViewById(paramInt);
    if (view != null)
      return (T)view; 
    throw new IllegalArgumentException("ID does not reference a View inside this Window");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\WindowCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */