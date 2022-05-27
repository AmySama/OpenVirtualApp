package android.support.v4.widget;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ViewGroupUtils {
  private static final ThreadLocal<Matrix> sMatrix = new ThreadLocal<Matrix>();
  
  private static final ThreadLocal<RectF> sRectF = new ThreadLocal<RectF>();
  
  public static void getDescendantRect(ViewGroup paramViewGroup, View paramView, Rect paramRect) {
    paramRect.set(0, 0, paramView.getWidth(), paramView.getHeight());
    offsetDescendantRect(paramViewGroup, paramView, paramRect);
  }
  
  private static void offsetDescendantMatrix(ViewParent paramViewParent, View paramView, Matrix paramMatrix) {
    ViewParent viewParent = paramView.getParent();
    if (viewParent instanceof View && viewParent != paramViewParent) {
      View view = (View)viewParent;
      offsetDescendantMatrix(paramViewParent, view, paramMatrix);
      paramMatrix.preTranslate(-view.getScrollX(), -view.getScrollY());
    } 
    paramMatrix.preTranslate(paramView.getLeft(), paramView.getTop());
    if (!paramView.getMatrix().isIdentity())
      paramMatrix.preConcat(paramView.getMatrix()); 
  }
  
  static void offsetDescendantRect(ViewGroup paramViewGroup, View paramView, Rect paramRect) {
    Matrix matrix = sMatrix.get();
    if (matrix == null) {
      matrix = new Matrix();
      sMatrix.set(matrix);
    } else {
      matrix.reset();
    } 
    offsetDescendantMatrix((ViewParent)paramViewGroup, paramView, matrix);
    RectF rectF2 = sRectF.get();
    RectF rectF1 = rectF2;
    if (rectF2 == null) {
      rectF1 = new RectF();
      sRectF.set(rectF1);
    } 
    rectF1.set(paramRect);
    matrix.mapRect(rectF1);
    paramRect.set((int)(rectF1.left + 0.5F), (int)(rectF1.top + 0.5F), (int)(rectF1.right + 0.5F), (int)(rectF1.bottom + 0.5F));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\ViewGroupUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */