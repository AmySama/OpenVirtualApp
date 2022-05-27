package android.support.transition;

import android.graphics.Matrix;
import android.view.View;

interface ViewUtilsImpl {
  void clearNonTransitionAlpha(View paramView);
  
  ViewOverlayImpl getOverlay(View paramView);
  
  float getTransitionAlpha(View paramView);
  
  WindowIdImpl getWindowId(View paramView);
  
  void saveNonTransitionAlpha(View paramView);
  
  void setAnimationMatrix(View paramView, Matrix paramMatrix);
  
  void setLeftTopRightBottom(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void setTransitionAlpha(View paramView, float paramFloat);
  
  void transformMatrixToGlobal(View paramView, Matrix paramMatrix);
  
  void transformMatrixToLocal(View paramView, Matrix paramMatrix);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ViewUtilsImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */