package android.support.transition;

import android.animation.Animator;
import android.graphics.Matrix;
import android.os.Build;
import android.widget.ImageView;

class ImageViewUtils {
  private static final ImageViewUtilsImpl IMPL;
  
  static {
    if (Build.VERSION.SDK_INT >= 21) {
      IMPL = new ImageViewUtilsApi21();
    } else {
      IMPL = new ImageViewUtilsApi14();
    } 
  }
  
  static void animateTransform(ImageView paramImageView, Matrix paramMatrix) {
    IMPL.animateTransform(paramImageView, paramMatrix);
  }
  
  static void reserveEndAnimateTransform(ImageView paramImageView, Animator paramAnimator) {
    IMPL.reserveEndAnimateTransform(paramImageView, paramAnimator);
  }
  
  static void startAnimateTransform(ImageView paramImageView) {
    IMPL.startAnimateTransform(paramImageView);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ImageViewUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */