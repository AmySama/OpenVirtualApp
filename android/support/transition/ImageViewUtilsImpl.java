package android.support.transition;

import android.animation.Animator;
import android.graphics.Matrix;
import android.widget.ImageView;

interface ImageViewUtilsImpl {
  void animateTransform(ImageView paramImageView, Matrix paramMatrix);
  
  void reserveEndAnimateTransform(ImageView paramImageView, Animator paramAnimator);
  
  void startAnimateTransform(ImageView paramImageView);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ImageViewUtilsImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */