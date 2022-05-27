package android.support.transition;

import android.animation.TypeEvaluator;
import android.graphics.Rect;

class RectEvaluator implements TypeEvaluator<Rect> {
  private Rect mRect;
  
  RectEvaluator() {}
  
  RectEvaluator(Rect paramRect) {
    this.mRect = paramRect;
  }
  
  public Rect evaluate(float paramFloat, Rect paramRect1, Rect paramRect2) {
    int i = paramRect1.left + (int)((paramRect2.left - paramRect1.left) * paramFloat);
    int j = paramRect1.top + (int)((paramRect2.top - paramRect1.top) * paramFloat);
    int k = paramRect1.right + (int)((paramRect2.right - paramRect1.right) * paramFloat);
    int m = paramRect1.bottom + (int)((paramRect2.bottom - paramRect1.bottom) * paramFloat);
    paramRect1 = this.mRect;
    if (paramRect1 == null)
      return new Rect(i, j, k, m); 
    paramRect1.set(i, j, k, m);
    return this.mRect;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\RectEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */