package android.support.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.Map;

public class ChangeImageTransform extends Transition {
  private static final Property<ImageView, Matrix> ANIMATED_TRANSFORM_PROPERTY;
  
  private static final TypeEvaluator<Matrix> NULL_MATRIX_EVALUATOR;
  
  private static final String PROPNAME_BOUNDS = "android:changeImageTransform:bounds";
  
  private static final String PROPNAME_MATRIX = "android:changeImageTransform:matrix";
  
  private static final String[] sTransitionProperties = new String[] { "android:changeImageTransform:matrix", "android:changeImageTransform:bounds" };
  
  static {
    NULL_MATRIX_EVALUATOR = new TypeEvaluator<Matrix>() {
        public Matrix evaluate(float param1Float, Matrix param1Matrix1, Matrix param1Matrix2) {
          return null;
        }
      };
    ANIMATED_TRANSFORM_PROPERTY = new Property<ImageView, Matrix>(Matrix.class, "animatedTransform") {
        public Matrix get(ImageView param1ImageView) {
          return null;
        }
        
        public void set(ImageView param1ImageView, Matrix param1Matrix) {
          ImageViewUtils.animateTransform(param1ImageView, param1Matrix);
        }
      };
  }
  
  public ChangeImageTransform() {}
  
  public ChangeImageTransform(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  private void captureValues(TransitionValues paramTransitionValues) {
    View view = paramTransitionValues.view;
    if (view instanceof ImageView && view.getVisibility() == 0) {
      ImageView imageView = (ImageView)view;
      if (imageView.getDrawable() == null)
        return; 
      Map<String, Object> map = paramTransitionValues.values;
      map.put("android:changeImageTransform:bounds", new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
      map.put("android:changeImageTransform:matrix", copyImageMatrix(imageView));
    } 
  }
  
  private static Matrix centerCropMatrix(ImageView paramImageView) {
    Drawable drawable = paramImageView.getDrawable();
    int i = drawable.getIntrinsicWidth();
    float f1 = paramImageView.getWidth();
    float f2 = i;
    float f3 = f1 / f2;
    i = drawable.getIntrinsicHeight();
    float f4 = paramImageView.getHeight();
    float f5 = i;
    f3 = Math.max(f3, f4 / f5);
    int j = Math.round((f1 - f2 * f3) / 2.0F);
    i = Math.round((f4 - f5 * f3) / 2.0F);
    Matrix matrix = new Matrix();
    matrix.postScale(f3, f3);
    matrix.postTranslate(j, i);
    return matrix;
  }
  
  private static Matrix copyImageMatrix(ImageView paramImageView) {
    int i = null.$SwitchMap$android$widget$ImageView$ScaleType[paramImageView.getScaleType().ordinal()];
    return (i != 1) ? ((i != 2) ? new Matrix(paramImageView.getImageMatrix()) : centerCropMatrix(paramImageView)) : fitXYMatrix(paramImageView);
  }
  
  private ObjectAnimator createMatrixAnimator(ImageView paramImageView, Matrix paramMatrix1, Matrix paramMatrix2) {
    return ObjectAnimator.ofObject(paramImageView, ANIMATED_TRANSFORM_PROPERTY, new TransitionUtils.MatrixEvaluator(), (Object[])new Matrix[] { paramMatrix1, paramMatrix2 });
  }
  
  private ObjectAnimator createNullAnimator(ImageView paramImageView) {
    return ObjectAnimator.ofObject(paramImageView, ANIMATED_TRANSFORM_PROPERTY, NULL_MATRIX_EVALUATOR, (Object[])new Matrix[] { null, null });
  }
  
  private static Matrix fitXYMatrix(ImageView paramImageView) {
    Drawable drawable = paramImageView.getDrawable();
    Matrix matrix = new Matrix();
    matrix.postScale(paramImageView.getWidth() / drawable.getIntrinsicWidth(), paramImageView.getHeight() / drawable.getIntrinsicHeight());
    return matrix;
  }
  
  public void captureEndValues(TransitionValues paramTransitionValues) {
    captureValues(paramTransitionValues);
  }
  
  public void captureStartValues(TransitionValues paramTransitionValues) {
    captureValues(paramTransitionValues);
  }
  
  public Animator createAnimator(ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2) {
    if (paramTransitionValues1 == null || paramTransitionValues2 == null)
      return null; 
    Rect rect2 = (Rect)paramTransitionValues1.values.get("android:changeImageTransform:bounds");
    Rect rect1 = (Rect)paramTransitionValues2.values.get("android:changeImageTransform:bounds");
    if (rect2 == null || rect1 == null)
      return null; 
    Matrix matrix2 = (Matrix)paramTransitionValues1.values.get("android:changeImageTransform:matrix");
    Matrix matrix3 = (Matrix)paramTransitionValues2.values.get("android:changeImageTransform:matrix");
    if ((matrix2 == null && matrix3 == null) || (matrix2 != null && matrix2.equals(matrix3))) {
      i = 1;
    } else {
      i = 0;
    } 
    if (rect2.equals(rect1) && i)
      return null; 
    ImageView imageView = (ImageView)paramTransitionValues2.view;
    Drawable drawable = imageView.getDrawable();
    int i = drawable.getIntrinsicWidth();
    int j = drawable.getIntrinsicHeight();
    ImageViewUtils.startAnimateTransform(imageView);
    if (i == 0 || j == 0) {
      ObjectAnimator objectAnimator1 = createNullAnimator(imageView);
      ImageViewUtils.reserveEndAnimateTransform(imageView, (Animator)objectAnimator1);
      return (Animator)objectAnimator1;
    } 
    Matrix matrix1 = matrix2;
    if (matrix2 == null)
      matrix1 = MatrixUtils.IDENTITY_MATRIX; 
    matrix2 = matrix3;
    if (matrix3 == null)
      matrix2 = MatrixUtils.IDENTITY_MATRIX; 
    ANIMATED_TRANSFORM_PROPERTY.set(imageView, matrix1);
    ObjectAnimator objectAnimator = createMatrixAnimator(imageView, matrix1, matrix2);
    ImageViewUtils.reserveEndAnimateTransform(imageView, (Animator)objectAnimator);
    return (Animator)objectAnimator;
  }
  
  public String[] getTransitionProperties() {
    return sTransitionProperties;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ChangeImageTransform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */