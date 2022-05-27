package android.support.transition;

import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewUtilsApi21 extends ViewUtilsApi19 {
  private static final String TAG = "ViewUtilsApi21";
  
  private static Method sSetAnimationMatrixMethod;
  
  private static boolean sSetAnimationMatrixMethodFetched;
  
  private static Method sTransformMatrixToGlobalMethod;
  
  private static boolean sTransformMatrixToGlobalMethodFetched;
  
  private static Method sTransformMatrixToLocalMethod;
  
  private static boolean sTransformMatrixToLocalMethodFetched;
  
  private void fetchSetAnimationMatrix() {
    if (!sSetAnimationMatrixMethodFetched) {
      try {
        Method method = View.class.getDeclaredMethod("setAnimationMatrix", new Class[] { Matrix.class });
        sSetAnimationMatrixMethod = method;
        method.setAccessible(true);
      } catch (NoSuchMethodException noSuchMethodException) {
        Log.i("ViewUtilsApi21", "Failed to retrieve setAnimationMatrix method", noSuchMethodException);
      } 
      sSetAnimationMatrixMethodFetched = true;
    } 
  }
  
  private void fetchTransformMatrixToGlobalMethod() {
    if (!sTransformMatrixToGlobalMethodFetched) {
      try {
        Method method = View.class.getDeclaredMethod("transformMatrixToGlobal", new Class[] { Matrix.class });
        sTransformMatrixToGlobalMethod = method;
        method.setAccessible(true);
      } catch (NoSuchMethodException noSuchMethodException) {
        Log.i("ViewUtilsApi21", "Failed to retrieve transformMatrixToGlobal method", noSuchMethodException);
      } 
      sTransformMatrixToGlobalMethodFetched = true;
    } 
  }
  
  private void fetchTransformMatrixToLocalMethod() {
    if (!sTransformMatrixToLocalMethodFetched) {
      try {
        Method method = View.class.getDeclaredMethod("transformMatrixToLocal", new Class[] { Matrix.class });
        sTransformMatrixToLocalMethod = method;
        method.setAccessible(true);
      } catch (NoSuchMethodException noSuchMethodException) {
        Log.i("ViewUtilsApi21", "Failed to retrieve transformMatrixToLocal method", noSuchMethodException);
      } 
      sTransformMatrixToLocalMethodFetched = true;
    } 
  }
  
  public void setAnimationMatrix(View paramView, Matrix paramMatrix) {
    fetchSetAnimationMatrix();
    Method method = sSetAnimationMatrixMethod;
    if (method != null)
      try {
        method.invoke(paramView, new Object[] { paramMatrix });
      } catch (InvocationTargetException invocationTargetException) {
      
      } catch (IllegalAccessException illegalAccessException) {
        throw new RuntimeException(illegalAccessException.getCause());
      }  
  }
  
  public void transformMatrixToGlobal(View paramView, Matrix paramMatrix) {
    fetchTransformMatrixToGlobalMethod();
    Method method = sTransformMatrixToGlobalMethod;
    if (method != null)
      try {
        method.invoke(paramView, new Object[] { paramMatrix });
      } catch (IllegalAccessException illegalAccessException) {
      
      } catch (InvocationTargetException invocationTargetException) {
        throw new RuntimeException(invocationTargetException.getCause());
      }  
  }
  
  public void transformMatrixToLocal(View paramView, Matrix paramMatrix) {
    fetchTransformMatrixToLocalMethod();
    Method method = sTransformMatrixToLocalMethod;
    if (method != null)
      try {
        method.invoke(paramView, new Object[] { paramMatrix });
      } catch (IllegalAccessException illegalAccessException) {
      
      } catch (InvocationTargetException invocationTargetException) {
        throw new RuntimeException(invocationTargetException.getCause());
      }  
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ViewUtilsApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */