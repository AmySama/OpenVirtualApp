package android.support.v7.widget;

import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewUtils {
  private static final String TAG = "ViewUtils";
  
  private static Method sComputeFitSystemWindowsMethod;
  
  static {
    if (Build.VERSION.SDK_INT >= 18)
      try {
        Method method = View.class.getDeclaredMethod("computeFitSystemWindows", new Class[] { Rect.class, Rect.class });
        sComputeFitSystemWindowsMethod = method;
        if (!method.isAccessible())
          sComputeFitSystemWindowsMethod.setAccessible(true); 
      } catch (NoSuchMethodException noSuchMethodException) {
        Log.d("ViewUtils", "Could not find method computeFitSystemWindows. Oh well.");
      }  
  }
  
  public static void computeFitSystemWindows(View paramView, Rect paramRect1, Rect paramRect2) {
    Method method = sComputeFitSystemWindowsMethod;
    if (method != null)
      try {
        method.invoke(paramView, new Object[] { paramRect1, paramRect2 });
      } catch (Exception exception) {
        Log.d("ViewUtils", "Could not invoke computeFitSystemWindows", exception);
      }  
  }
  
  public static boolean isLayoutRtl(View paramView) {
    int i = ViewCompat.getLayoutDirection(paramView);
    boolean bool = true;
    if (i != 1)
      bool = false; 
    return bool;
  }
  
  public static void makeOptionalFitsSystemWindows(View paramView) {
    if (Build.VERSION.SDK_INT >= 16)
      try {
        Method method = paramView.getClass().getMethod("makeOptionalFitsSystemWindows", new Class[0]);
        if (!method.isAccessible())
          method.setAccessible(true); 
        method.invoke(paramView, new Object[0]);
      } catch (NoSuchMethodException noSuchMethodException) {
        Log.d("ViewUtils", "Could not find method makeOptionalFitsSystemWindows. Oh well...");
      } catch (InvocationTargetException invocationTargetException) {
        Log.d("ViewUtils", "Could not invoke makeOptionalFitsSystemWindows", invocationTargetException);
      } catch (IllegalAccessException illegalAccessException) {
        Log.d("ViewUtils", "Could not invoke makeOptionalFitsSystemWindows", illegalAccessException);
      }  
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\ViewUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */