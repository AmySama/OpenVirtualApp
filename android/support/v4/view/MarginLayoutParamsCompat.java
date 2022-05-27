package android.support.v4.view;

import android.os.Build;
import android.view.ViewGroup;

public final class MarginLayoutParamsCompat {
  public static int getLayoutDirection(ViewGroup.MarginLayoutParams paramMarginLayoutParams) {
    int i = Build.VERSION.SDK_INT;
    boolean bool = false;
    if (i >= 17) {
      i = paramMarginLayoutParams.getLayoutDirection();
    } else {
      i = 0;
    } 
    if (i != 0 && i != 1)
      i = bool; 
    return i;
  }
  
  public static int getMarginEnd(ViewGroup.MarginLayoutParams paramMarginLayoutParams) {
    return (Build.VERSION.SDK_INT >= 17) ? paramMarginLayoutParams.getMarginEnd() : paramMarginLayoutParams.rightMargin;
  }
  
  public static int getMarginStart(ViewGroup.MarginLayoutParams paramMarginLayoutParams) {
    return (Build.VERSION.SDK_INT >= 17) ? paramMarginLayoutParams.getMarginStart() : paramMarginLayoutParams.leftMargin;
  }
  
  public static boolean isMarginRelative(ViewGroup.MarginLayoutParams paramMarginLayoutParams) {
    return (Build.VERSION.SDK_INT >= 17) ? paramMarginLayoutParams.isMarginRelative() : false;
  }
  
  public static void resolveLayoutDirection(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt) {
    if (Build.VERSION.SDK_INT >= 17)
      paramMarginLayoutParams.resolveLayoutDirection(paramInt); 
  }
  
  public static void setLayoutDirection(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt) {
    if (Build.VERSION.SDK_INT >= 17)
      paramMarginLayoutParams.setLayoutDirection(paramInt); 
  }
  
  public static void setMarginEnd(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt) {
    if (Build.VERSION.SDK_INT >= 17) {
      paramMarginLayoutParams.setMarginEnd(paramInt);
    } else {
      paramMarginLayoutParams.rightMargin = paramInt;
    } 
  }
  
  public static void setMarginStart(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt) {
    if (Build.VERSION.SDK_INT >= 17) {
      paramMarginLayoutParams.setMarginStart(paramInt);
    } else {
      paramMarginLayoutParams.leftMargin = paramInt;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\MarginLayoutParamsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */