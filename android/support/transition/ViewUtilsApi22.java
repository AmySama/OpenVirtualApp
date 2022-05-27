package android.support.transition;

import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewUtilsApi22 extends ViewUtilsApi21 {
  private static final String TAG = "ViewUtilsApi22";
  
  private static Method sSetLeftTopRightBottomMethod;
  
  private static boolean sSetLeftTopRightBottomMethodFetched;
  
  private void fetchSetLeftTopRightBottomMethod() {
    if (!sSetLeftTopRightBottomMethodFetched) {
      try {
        Method method = View.class.getDeclaredMethod("setLeftTopRightBottom", new Class[] { int.class, int.class, int.class, int.class });
        sSetLeftTopRightBottomMethod = method;
        method.setAccessible(true);
      } catch (NoSuchMethodException noSuchMethodException) {
        Log.i("ViewUtilsApi22", "Failed to retrieve setLeftTopRightBottom method", noSuchMethodException);
      } 
      sSetLeftTopRightBottomMethodFetched = true;
    } 
  }
  
  public void setLeftTopRightBottom(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    fetchSetLeftTopRightBottomMethod();
    Method method = sSetLeftTopRightBottomMethod;
    if (method != null)
      try {
        method.invoke(paramView, new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4) });
      } catch (IllegalAccessException illegalAccessException) {
      
      } catch (InvocationTargetException invocationTargetException) {
        throw new RuntimeException(invocationTargetException.getCause());
      }  
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ViewUtilsApi22.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */