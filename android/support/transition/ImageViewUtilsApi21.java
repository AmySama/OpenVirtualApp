package android.support.transition;

import android.animation.Animator;
import android.graphics.Matrix;
import android.util.Log;
import android.widget.ImageView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ImageViewUtilsApi21 implements ImageViewUtilsImpl {
  private static final String TAG = "ImageViewUtilsApi21";
  
  private static Method sAnimateTransformMethod;
  
  private static boolean sAnimateTransformMethodFetched;
  
  private void fetchAnimateTransformMethod() {
    if (!sAnimateTransformMethodFetched) {
      try {
        Method method = ImageView.class.getDeclaredMethod("animateTransform", new Class[] { Matrix.class });
        sAnimateTransformMethod = method;
        method.setAccessible(true);
      } catch (NoSuchMethodException noSuchMethodException) {
        Log.i("ImageViewUtilsApi21", "Failed to retrieve animateTransform method", noSuchMethodException);
      } 
      sAnimateTransformMethodFetched = true;
    } 
  }
  
  public void animateTransform(ImageView paramImageView, Matrix paramMatrix) {
    fetchAnimateTransformMethod();
    Method method = sAnimateTransformMethod;
    if (method != null)
      try {
        method.invoke(paramImageView, new Object[] { paramMatrix });
      } catch (IllegalAccessException illegalAccessException) {
      
      } catch (InvocationTargetException invocationTargetException) {
        throw new RuntimeException(invocationTargetException.getCause());
      }  
  }
  
  public void reserveEndAnimateTransform(ImageView paramImageView, Animator paramAnimator) {}
  
  public void startAnimateTransform(ImageView paramImageView) {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ImageViewUtilsApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */