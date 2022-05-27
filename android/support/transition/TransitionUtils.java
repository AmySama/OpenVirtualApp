package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

class TransitionUtils {
  private static final int MAX_IMAGE_SIZE = 1048576;
  
  static View copyViewImage(ViewGroup paramViewGroup, View paramView1, View paramView2) {
    Matrix matrix = new Matrix();
    matrix.setTranslate(-paramView2.getScrollX(), -paramView2.getScrollY());
    ViewUtils.transformMatrixToGlobal(paramView1, matrix);
    ViewUtils.transformMatrixToLocal((View)paramViewGroup, matrix);
    RectF rectF = new RectF(0.0F, 0.0F, paramView1.getWidth(), paramView1.getHeight());
    matrix.mapRect(rectF);
    int i = Math.round(rectF.left);
    int j = Math.round(rectF.top);
    int k = Math.round(rectF.right);
    int m = Math.round(rectF.bottom);
    ImageView imageView = new ImageView(paramView1.getContext());
    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    Bitmap bitmap = createViewBitmap(paramView1, matrix, rectF);
    if (bitmap != null)
      imageView.setImageBitmap(bitmap); 
    imageView.measure(View.MeasureSpec.makeMeasureSpec(k - i, 1073741824), View.MeasureSpec.makeMeasureSpec(m - j, 1073741824));
    imageView.layout(i, j, k, m);
    return (View)imageView;
  }
  
  private static Bitmap createViewBitmap(View paramView, Matrix paramMatrix, RectF paramRectF) {
    int i = Math.round(paramRectF.width());
    int j = Math.round(paramRectF.height());
    if (i > 0 && j > 0) {
      float f = Math.min(1.0F, 1048576.0F / (i * j));
      i = (int)(i * f);
      j = (int)(j * f);
      paramMatrix.postTranslate(-paramRectF.left, -paramRectF.top);
      paramMatrix.postScale(f, f);
      Bitmap bitmap2 = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmap2);
      canvas.concat(paramMatrix);
      paramView.draw(canvas);
      Bitmap bitmap1 = bitmap2;
    } else {
      paramView = null;
    } 
    return (Bitmap)paramView;
  }
  
  static Animator mergeAnimators(Animator paramAnimator1, Animator paramAnimator2) {
    if (paramAnimator1 == null)
      return paramAnimator2; 
    if (paramAnimator2 == null)
      return paramAnimator1; 
    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playTogether(new Animator[] { paramAnimator1, paramAnimator2 });
    return (Animator)animatorSet;
  }
  
  static class MatrixEvaluator implements TypeEvaluator<Matrix> {
    final float[] mTempEndValues = new float[9];
    
    final Matrix mTempMatrix = new Matrix();
    
    final float[] mTempStartValues = new float[9];
    
    public Matrix evaluate(float param1Float, Matrix param1Matrix1, Matrix param1Matrix2) {
      param1Matrix1.getValues(this.mTempStartValues);
      param1Matrix2.getValues(this.mTempEndValues);
      for (byte b = 0; b < 9; b++) {
        float[] arrayOfFloat1 = this.mTempEndValues;
        float f1 = arrayOfFloat1[b];
        float[] arrayOfFloat2 = this.mTempStartValues;
        float f2 = arrayOfFloat2[b];
        arrayOfFloat1[b] = arrayOfFloat2[b] + (f1 - f2) * param1Float;
      } 
      this.mTempMatrix.setValues(this.mTempEndValues);
      return this.mTempMatrix;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\TransitionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */