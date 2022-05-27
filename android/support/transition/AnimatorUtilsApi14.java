package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import java.util.ArrayList;

class AnimatorUtilsApi14 implements AnimatorUtilsImpl {
  public void addPauseListener(Animator paramAnimator, AnimatorListenerAdapter paramAnimatorListenerAdapter) {}
  
  public void pause(Animator paramAnimator) {
    ArrayList<Animator.AnimatorListener> arrayList = paramAnimator.getListeners();
    if (arrayList != null) {
      byte b = 0;
      int i = arrayList.size();
      while (b < i) {
        Animator.AnimatorListener animatorListener = arrayList.get(b);
        if (animatorListener instanceof AnimatorPauseListenerCompat)
          ((AnimatorPauseListenerCompat)animatorListener).onAnimationPause(paramAnimator); 
        b++;
      } 
    } 
  }
  
  public void resume(Animator paramAnimator) {
    ArrayList<Animator.AnimatorListener> arrayList = paramAnimator.getListeners();
    if (arrayList != null) {
      byte b = 0;
      int i = arrayList.size();
      while (b < i) {
        Animator.AnimatorListener animatorListener = arrayList.get(b);
        if (animatorListener instanceof AnimatorPauseListenerCompat)
          ((AnimatorPauseListenerCompat)animatorListener).onAnimationResume(paramAnimator); 
        b++;
      } 
    } 
  }
  
  static interface AnimatorPauseListenerCompat {
    void onAnimationPause(Animator param1Animator);
    
    void onAnimationResume(Animator param1Animator);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\AnimatorUtilsApi14.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */