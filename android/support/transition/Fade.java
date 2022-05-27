package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import org.xmlpull.v1.XmlPullParser;

public class Fade extends Visibility {
  public static final int IN = 1;
  
  private static final String LOG_TAG = "Fade";
  
  public static final int OUT = 2;
  
  private static final String PROPNAME_TRANSITION_ALPHA = "android:fade:transitionAlpha";
  
  public Fade() {}
  
  public Fade(int paramInt) {
    setMode(paramInt);
  }
  
  public Fade(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, Styleable.FADE);
    setMode(TypedArrayUtils.getNamedInt(typedArray, (XmlPullParser)paramAttributeSet, "fadingMode", 0, getMode()));
    typedArray.recycle();
  }
  
  private Animator createAnimation(final View view, float paramFloat1, float paramFloat2) {
    if (paramFloat1 == paramFloat2)
      return null; 
    ViewUtils.setTransitionAlpha(view, paramFloat1);
    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, ViewUtils.TRANSITION_ALPHA, new float[] { paramFloat2 });
    objectAnimator.addListener((Animator.AnimatorListener)new FadeAnimatorListener(view));
    addListener(new TransitionListenerAdapter() {
          public void onTransitionEnd(Transition param1Transition) {
            ViewUtils.setTransitionAlpha(view, 1.0F);
            ViewUtils.clearNonTransitionAlpha(view);
            param1Transition.removeListener(this);
          }
        });
    return (Animator)objectAnimator;
  }
  
  private static float getStartAlpha(TransitionValues paramTransitionValues, float paramFloat) {
    float f = paramFloat;
    if (paramTransitionValues != null) {
      Float float_ = (Float)paramTransitionValues.values.get("android:fade:transitionAlpha");
      f = paramFloat;
      if (float_ != null)
        f = float_.floatValue(); 
    } 
    return f;
  }
  
  public void captureStartValues(TransitionValues paramTransitionValues) {
    super.captureStartValues(paramTransitionValues);
    paramTransitionValues.values.put("android:fade:transitionAlpha", Float.valueOf(ViewUtils.getTransitionAlpha(paramTransitionValues.view)));
  }
  
  public Animator onAppear(ViewGroup paramViewGroup, View paramView, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2) {
    float f1 = 0.0F;
    float f2 = getStartAlpha(paramTransitionValues1, 0.0F);
    if (f2 == 1.0F)
      f2 = f1; 
    return createAnimation(paramView, f2, 1.0F);
  }
  
  public Animator onDisappear(ViewGroup paramViewGroup, View paramView, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2) {
    ViewUtils.saveNonTransitionAlpha(paramView);
    return createAnimation(paramView, getStartAlpha(paramTransitionValues1, 1.0F), 0.0F);
  }
  
  private static class FadeAnimatorListener extends AnimatorListenerAdapter {
    private boolean mLayerTypeChanged = false;
    
    private final View mView;
    
    FadeAnimatorListener(View param1View) {
      this.mView = param1View;
    }
    
    public void onAnimationEnd(Animator param1Animator) {
      ViewUtils.setTransitionAlpha(this.mView, 1.0F);
      if (this.mLayerTypeChanged)
        this.mView.setLayerType(0, null); 
    }
    
    public void onAnimationStart(Animator param1Animator) {
      if (ViewCompat.hasOverlappingRendering(this.mView) && this.mView.getLayerType() == 0) {
        this.mLayerTypeChanged = true;
        this.mView.setLayerType(2, null);
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\Fade.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */