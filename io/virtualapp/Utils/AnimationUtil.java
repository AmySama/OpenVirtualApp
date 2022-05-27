package io.virtualapp.Utils;

import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

public class AnimationUtil {
  public static final int ANIMATION_IN_TIME = 500;
  
  public static final int ANIMATION_OUT_TIME = 500;
  
  public static Animation createInAnimation(Context paramContext, int paramInt) {
    AnimationSet animationSet = new AnimationSet(paramContext, null);
    animationSet.setFillAfter(true);
    TranslateAnimation translateAnimation = new TranslateAnimation(0.0F, 0.0F, paramInt, 0.0F);
    translateAnimation.setDuration(500L);
    animationSet.addAnimation((Animation)translateAnimation);
    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0F, 1.0F);
    alphaAnimation.setDuration(500L);
    animationSet.addAnimation((Animation)alphaAnimation);
    return (Animation)animationSet;
  }
  
  public static Animation createOutAnimation(Context paramContext, int paramInt) {
    AnimationSet animationSet = new AnimationSet(paramContext, null);
    animationSet.setFillAfter(true);
    TranslateAnimation translateAnimation = new TranslateAnimation(0.0F, 0.0F, 0.0F, paramInt);
    translateAnimation.setDuration(500L);
    animationSet.addAnimation((Animation)translateAnimation);
    AlphaAnimation alphaAnimation = new AlphaAnimation(1.0F, 0.0F);
    alphaAnimation.setDuration(500L);
    animationSet.addAnimation((Animation)alphaAnimation);
    return (Animation)animationSet;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\AnimationUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */