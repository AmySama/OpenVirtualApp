package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

class AnimatorUtilsApi19 implements AnimatorUtilsImpl {
  public void addPauseListener(Animator paramAnimator, AnimatorListenerAdapter paramAnimatorListenerAdapter) {
    paramAnimator.addPauseListener((Animator.AnimatorPauseListener)paramAnimatorListenerAdapter);
  }
  
  public void pause(Animator paramAnimator) {
    paramAnimator.pause();
  }
  
  public void resume(Animator paramAnimator) {
    paramAnimator.resume();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\AnimatorUtilsApi19.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */