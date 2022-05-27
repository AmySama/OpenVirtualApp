package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;

class AnimatorUtils {
  private static final AnimatorUtilsImpl IMPL;
  
  static {
    if (Build.VERSION.SDK_INT >= 19) {
      IMPL = new AnimatorUtilsApi19();
    } else {
      IMPL = new AnimatorUtilsApi14();
    } 
  }
  
  static void addPauseListener(Animator paramAnimator, AnimatorListenerAdapter paramAnimatorListenerAdapter) {
    IMPL.addPauseListener(paramAnimator, paramAnimatorListenerAdapter);
  }
  
  static void pause(Animator paramAnimator) {
    IMPL.pause(paramAnimator);
  }
  
  static void resume(Animator paramAnimator) {
    IMPL.resume(paramAnimator);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\AnimatorUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */