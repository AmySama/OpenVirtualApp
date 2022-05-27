package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Matrix;
import android.widget.ImageView;

class ImageViewUtilsApi14 implements ImageViewUtilsImpl {
  public void animateTransform(ImageView paramImageView, Matrix paramMatrix) {
    paramImageView.setImageMatrix(paramMatrix);
  }
  
  public void reserveEndAnimateTransform(final ImageView view, Animator paramAnimator) {
    paramAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
          public void onAnimationEnd(Animator param1Animator) {
            ImageView.ScaleType scaleType = (ImageView.ScaleType)view.getTag(R.id.save_scale_type);
            view.setScaleType(scaleType);
            view.setTag(R.id.save_scale_type, null);
            if (scaleType == ImageView.ScaleType.MATRIX) {
              ImageView imageView = view;
              imageView.setImageMatrix((Matrix)imageView.getTag(R.id.save_image_matrix));
              view.setTag(R.id.save_image_matrix, null);
            } 
            param1Animator.removeListener((Animator.AnimatorListener)this);
          }
        });
  }
  
  public void startAnimateTransform(ImageView paramImageView) {
    ImageView.ScaleType scaleType = paramImageView.getScaleType();
    paramImageView.setTag(R.id.save_scale_type, scaleType);
    if (scaleType == ImageView.ScaleType.MATRIX) {
      paramImageView.setTag(R.id.save_image_matrix, paramImageView.getImageMatrix());
    } else {
      paramImageView.setScaleType(ImageView.ScaleType.MATRIX);
    } 
    paramImageView.setImageMatrix(MatrixUtils.IDENTITY_MATRIX);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ImageViewUtilsApi14.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */