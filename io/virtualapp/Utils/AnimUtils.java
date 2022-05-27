package io.virtualapp.Utils;

import android.animation.Animator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class AnimUtils {
  public static void bottomDownAnim(View paramView, int paramInt) {
    downAnim(paramView, paramInt, paramView.getHeight());
  }
  
  public static void bottomUpAnim(View paramView, int paramInt) {
    upAnim(paramView, paramInt, paramView.getHeight());
  }
  
  private static void downAnim(final View view, final int vis, int paramInt2) {
    if (view.getVisibility() == vis)
      return; 
    view.setTranslationY(0.0F);
    if (vis == 0)
      view.setVisibility(0); 
    view.animate().translationY(paramInt2).setListener(new Animator.AnimatorListener() {
          public void onAnimationCancel(Animator param1Animator) {
            int i = vis;
            if (i != 0)
              view.setVisibility(i); 
          }
          
          public void onAnimationEnd(Animator param1Animator) {
            int i = vis;
            if (i != 0)
              view.setVisibility(i); 
          }
          
          public void onAnimationRepeat(Animator param1Animator) {}
          
          public void onAnimationStart(Animator param1Animator) {}
        }).setDuration(500L).start();
  }
  
  public static void hide(final View view) {
    view.clearAnimation();
    AlphaAnimation alphaAnimation = new AlphaAnimation(1.0F, 0.0F);
    alphaAnimation.setDuration(500L);
    alphaAnimation.setFillAfter(true);
    alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
          public void onAnimationEnd(Animation param1Animation) {
            view.setVisibility(8);
          }
          
          public void onAnimationRepeat(Animation param1Animation) {}
          
          public void onAnimationStart(Animation param1Animation) {}
        });
    view.startAnimation((Animation)alphaAnimation);
  }
  
  public static void show(final View view) {
    if (view.getVisibility() == 0)
      return; 
    view.clearAnimation();
    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0F, 1.0F);
    alphaAnimation.setDuration(500L);
    alphaAnimation.setFillAfter(true);
    alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
          public void onAnimationEnd(Animation param1Animation) {
            view.setVisibility(0);
          }
          
          public void onAnimationRepeat(Animation param1Animation) {}
          
          public void onAnimationStart(Animation param1Animation) {
            view.setVisibility(0);
          }
        });
    view.startAnimation((Animation)alphaAnimation);
  }
  
  public static void topDownAnim(View paramView, int paramInt) {
    upAnim(paramView, paramInt, -paramView.getHeight());
  }
  
  public static void topUpAnim(View paramView, int paramInt) {
    downAnim(paramView, paramInt, -paramView.getHeight());
  }
  
  private static void upAnim(final View view, final int vis, int paramInt2) {
    if (vis == 0) {
      view.setTranslationY(paramInt2);
      view.setVisibility(0);
    } 
    view.animate().translationY(0.0F).setListener(new Animator.AnimatorListener() {
          public void onAnimationCancel(Animator param1Animator) {
            int i = vis;
            if (i != 0)
              view.setVisibility(i); 
          }
          
          public void onAnimationEnd(Animator param1Animator) {
            int i = vis;
            if (i != 0)
              view.setVisibility(i); 
          }
          
          public void onAnimationRepeat(Animator param1Animator) {}
          
          public void onAnimationStart(Animator param1Animator) {}
        }).setDuration(500L).start();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\AnimUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */