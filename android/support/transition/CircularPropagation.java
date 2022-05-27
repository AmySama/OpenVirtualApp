package android.support.transition;

import android.graphics.Rect;
import android.view.ViewGroup;

public class CircularPropagation extends VisibilityPropagation {
  private float mPropagationSpeed = 3.0F;
  
  private static float distance(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    paramFloat1 = paramFloat3 - paramFloat1;
    paramFloat2 = paramFloat4 - paramFloat2;
    return (float)Math.sqrt((paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2));
  }
  
  public long getStartDelay(ViewGroup paramViewGroup, Transition paramTransition, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2) {
    boolean bool;
    int k;
    int m;
    if (paramTransitionValues1 == null && paramTransitionValues2 == null)
      return 0L; 
    if (paramTransitionValues2 == null || getViewVisibility(paramTransitionValues1) == 0) {
      bool = true;
    } else {
      paramTransitionValues1 = paramTransitionValues2;
      bool = true;
    } 
    int i = getViewX(paramTransitionValues1);
    int j = getViewY(paramTransitionValues1);
    Rect rect = paramTransition.getEpicenter();
    if (rect != null) {
      k = rect.centerX();
      m = rect.centerY();
    } else {
      int[] arrayOfInt = new int[2];
      paramViewGroup.getLocationOnScreen(arrayOfInt);
      k = Math.round((arrayOfInt[0] + paramViewGroup.getWidth() / 2) + paramViewGroup.getTranslationX());
      m = Math.round((arrayOfInt[1] + paramViewGroup.getHeight() / 2) + paramViewGroup.getTranslationY());
    } 
    float f = distance(i, j, k, m) / distance(0.0F, 0.0F, paramViewGroup.getWidth(), paramViewGroup.getHeight());
    long l1 = paramTransition.getDuration();
    long l2 = l1;
    if (l1 < 0L)
      l2 = 300L; 
    return Math.round((float)(l2 * bool) / this.mPropagationSpeed * f);
  }
  
  public void setPropagationSpeed(float paramFloat) {
    if (paramFloat != 0.0F) {
      this.mPropagationSpeed = paramFloat;
      return;
    } 
    throw new IllegalArgumentException("propagationSpeed may not be 0");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\CircularPropagation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */