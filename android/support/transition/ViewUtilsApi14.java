package android.support.transition;

import android.graphics.Matrix;
import android.view.View;
import android.view.ViewParent;

class ViewUtilsApi14 implements ViewUtilsImpl {
  private float[] mMatrixValues;
  
  public void clearNonTransitionAlpha(View paramView) {
    if (paramView.getVisibility() == 0)
      paramView.setTag(R.id.save_non_transition_alpha, null); 
  }
  
  public ViewOverlayImpl getOverlay(View paramView) {
    return ViewOverlayApi14.createFrom(paramView);
  }
  
  public float getTransitionAlpha(View paramView) {
    Float float_ = (Float)paramView.getTag(R.id.save_non_transition_alpha);
    return (float_ != null) ? (paramView.getAlpha() / float_.floatValue()) : paramView.getAlpha();
  }
  
  public WindowIdImpl getWindowId(View paramView) {
    return new WindowIdApi14(paramView.getWindowToken());
  }
  
  public void saveNonTransitionAlpha(View paramView) {
    if (paramView.getTag(R.id.save_non_transition_alpha) == null)
      paramView.setTag(R.id.save_non_transition_alpha, Float.valueOf(paramView.getAlpha())); 
  }
  
  public void setAnimationMatrix(View paramView, Matrix paramMatrix) {
    boolean bool;
    if (paramMatrix == null || paramMatrix.isIdentity()) {
      paramView.setPivotX((paramView.getWidth() / 2));
      paramView.setPivotY((paramView.getHeight() / 2));
      paramView.setTranslationX(0.0F);
      paramView.setTranslationY(0.0F);
      paramView.setScaleX(1.0F);
      paramView.setScaleY(1.0F);
      paramView.setRotation(0.0F);
      return;
    } 
    float[] arrayOfFloat1 = this.mMatrixValues;
    float[] arrayOfFloat2 = arrayOfFloat1;
    if (arrayOfFloat1 == null) {
      arrayOfFloat2 = new float[9];
      this.mMatrixValues = arrayOfFloat2;
    } 
    paramMatrix.getValues(arrayOfFloat2);
    float f1 = arrayOfFloat2[3];
    float f2 = (float)Math.sqrt((1.0F - f1 * f1));
    if (arrayOfFloat2[0] < 0.0F) {
      bool = true;
    } else {
      bool = true;
    } 
    float f3 = f2 * bool;
    f1 = (float)Math.toDegrees(Math.atan2(f1, f3));
    f2 = arrayOfFloat2[0] / f3;
    f3 = arrayOfFloat2[4] / f3;
    float f4 = arrayOfFloat2[2];
    float f5 = arrayOfFloat2[5];
    paramView.setPivotX(0.0F);
    paramView.setPivotY(0.0F);
    paramView.setTranslationX(f4);
    paramView.setTranslationY(f5);
    paramView.setRotation(f1);
    paramView.setScaleX(f2);
    paramView.setScaleY(f3);
  }
  
  public void setLeftTopRightBottom(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    paramView.setLeft(paramInt1);
    paramView.setTop(paramInt2);
    paramView.setRight(paramInt3);
    paramView.setBottom(paramInt4);
  }
  
  public void setTransitionAlpha(View paramView, float paramFloat) {
    Float float_ = (Float)paramView.getTag(R.id.save_non_transition_alpha);
    if (float_ != null) {
      paramView.setAlpha(float_.floatValue() * paramFloat);
    } else {
      paramView.setAlpha(paramFloat);
    } 
  }
  
  public void transformMatrixToGlobal(View paramView, Matrix paramMatrix) {
    ViewParent viewParent = paramView.getParent();
    if (viewParent instanceof View) {
      View view = (View)viewParent;
      transformMatrixToGlobal(view, paramMatrix);
      paramMatrix.preTranslate(-view.getScrollX(), -view.getScrollY());
    } 
    paramMatrix.preTranslate(paramView.getLeft(), paramView.getTop());
    Matrix matrix = paramView.getMatrix();
    if (!matrix.isIdentity())
      paramMatrix.preConcat(matrix); 
  }
  
  public void transformMatrixToLocal(View paramView, Matrix paramMatrix) {
    ViewParent viewParent = paramView.getParent();
    if (viewParent instanceof View) {
      View view = (View)viewParent;
      transformMatrixToLocal(view, paramMatrix);
      paramMatrix.postTranslate(view.getScrollX(), view.getScrollY());
    } 
    paramMatrix.postTranslate(paramView.getLeft(), paramView.getTop());
    Matrix matrix = paramView.getMatrix();
    if (!matrix.isIdentity()) {
      Matrix matrix1 = new Matrix();
      if (matrix.invert(matrix1))
        paramMatrix.postConcat(matrix1); 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ViewUtilsApi14.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */