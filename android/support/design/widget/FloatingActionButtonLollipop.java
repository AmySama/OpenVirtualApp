package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.animation.TimeInterpolator;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import java.util.ArrayList;

class FloatingActionButtonLollipop extends FloatingActionButtonImpl {
  private InsetDrawable mInsetDrawable;
  
  FloatingActionButtonLollipop(VisibilityAwareImageButton paramVisibilityAwareImageButton, ShadowViewDelegate paramShadowViewDelegate) {
    super(paramVisibilityAwareImageButton, paramShadowViewDelegate);
  }
  
  public float getElevation() {
    return this.mView.getElevation();
  }
  
  void getPadding(Rect paramRect) {
    if (this.mShadowViewDelegate.isCompatPaddingEnabled()) {
      float f1 = this.mShadowViewDelegate.getRadius();
      float f2 = getElevation() + this.mPressedTranslationZ;
      int i = (int)Math.ceil(ShadowDrawableWrapper.calculateHorizontalPadding(f2, f1, false));
      int j = (int)Math.ceil(ShadowDrawableWrapper.calculateVerticalPadding(f2, f1, false));
      paramRect.set(i, j, i, j);
    } else {
      paramRect.set(0, 0, 0, 0);
    } 
  }
  
  void jumpDrawableToCurrentState() {}
  
  CircularBorderDrawable newCircularDrawable() {
    return new CircularBorderDrawableLollipop();
  }
  
  GradientDrawable newGradientDrawableForShape() {
    return new AlwaysStatefulGradientDrawable();
  }
  
  void onCompatShadowChanged() {
    updatePadding();
  }
  
  void onDrawableStateChanged(int[] paramArrayOfint) {}
  
  void onElevationsChanged(float paramFloat1, float paramFloat2) {
    if (Build.VERSION.SDK_INT == 21) {
      if (this.mView.isEnabled()) {
        this.mView.setElevation(paramFloat1);
        if (this.mView.isFocused() || this.mView.isPressed()) {
          this.mView.setTranslationZ(paramFloat2);
        } else {
          this.mView.setTranslationZ(0.0F);
        } 
      } else {
        this.mView.setElevation(0.0F);
        this.mView.setTranslationZ(0.0F);
      } 
    } else {
      StateListAnimator stateListAnimator = new StateListAnimator();
      AnimatorSet animatorSet = new AnimatorSet();
      animatorSet.play((Animator)ObjectAnimator.ofFloat(this.mView, "elevation", new float[] { paramFloat1 }).setDuration(0L)).with((Animator)ObjectAnimator.ofFloat(this.mView, View.TRANSLATION_Z, new float[] { paramFloat2 }).setDuration(100L));
      animatorSet.setInterpolator((TimeInterpolator)ANIM_INTERPOLATOR);
      stateListAnimator.addState(PRESSED_ENABLED_STATE_SET, (Animator)animatorSet);
      animatorSet = new AnimatorSet();
      animatorSet.play((Animator)ObjectAnimator.ofFloat(this.mView, "elevation", new float[] { paramFloat1 }).setDuration(0L)).with((Animator)ObjectAnimator.ofFloat(this.mView, View.TRANSLATION_Z, new float[] { paramFloat2 }).setDuration(100L));
      animatorSet.setInterpolator((TimeInterpolator)ANIM_INTERPOLATOR);
      stateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, (Animator)animatorSet);
      animatorSet = new AnimatorSet();
      ArrayList<ObjectAnimator> arrayList = new ArrayList();
      arrayList.add(ObjectAnimator.ofFloat(this.mView, "elevation", new float[] { paramFloat1 }).setDuration(0L));
      if (Build.VERSION.SDK_INT >= 22 && Build.VERSION.SDK_INT <= 24)
        arrayList.add(ObjectAnimator.ofFloat(this.mView, View.TRANSLATION_Z, new float[] { this.mView.getTranslationZ() }).setDuration(100L)); 
      arrayList.add(ObjectAnimator.ofFloat(this.mView, View.TRANSLATION_Z, new float[] { 0.0F }).setDuration(100L));
      animatorSet.playSequentially((Animator[])arrayList.toArray((Object[])new ObjectAnimator[0]));
      animatorSet.setInterpolator((TimeInterpolator)ANIM_INTERPOLATOR);
      stateListAnimator.addState(ENABLED_STATE_SET, (Animator)animatorSet);
      animatorSet = new AnimatorSet();
      animatorSet.play((Animator)ObjectAnimator.ofFloat(this.mView, "elevation", new float[] { 0.0F }).setDuration(0L)).with((Animator)ObjectAnimator.ofFloat(this.mView, View.TRANSLATION_Z, new float[] { 0.0F }).setDuration(0L));
      animatorSet.setInterpolator((TimeInterpolator)ANIM_INTERPOLATOR);
      stateListAnimator.addState(EMPTY_STATE_SET, (Animator)animatorSet);
      this.mView.setStateListAnimator(stateListAnimator);
    } 
    if (this.mShadowViewDelegate.isCompatPaddingEnabled())
      updatePadding(); 
  }
  
  void onPaddingUpdated(Rect paramRect) {
    if (this.mShadowViewDelegate.isCompatPaddingEnabled()) {
      this.mInsetDrawable = new InsetDrawable(this.mRippleDrawable, paramRect.left, paramRect.top, paramRect.right, paramRect.bottom);
      this.mShadowViewDelegate.setBackgroundDrawable((Drawable)this.mInsetDrawable);
    } else {
      this.mShadowViewDelegate.setBackgroundDrawable(this.mRippleDrawable);
    } 
  }
  
  boolean requirePreDrawListener() {
    return false;
  }
  
  void setBackgroundDrawable(ColorStateList paramColorStateList, PorterDuff.Mode paramMode, int paramInt1, int paramInt2) {
    Drawable drawable;
    this.mShapeDrawable = DrawableCompat.wrap((Drawable)createShapeDrawable());
    DrawableCompat.setTintList(this.mShapeDrawable, paramColorStateList);
    if (paramMode != null)
      DrawableCompat.setTintMode(this.mShapeDrawable, paramMode); 
    if (paramInt2 > 0) {
      this.mBorderDrawable = createBorderDrawable(paramInt2, paramColorStateList);
      LayerDrawable layerDrawable = new LayerDrawable(new Drawable[] { this.mBorderDrawable, this.mShapeDrawable });
    } else {
      this.mBorderDrawable = null;
      drawable = this.mShapeDrawable;
    } 
    this.mRippleDrawable = (Drawable)new RippleDrawable(ColorStateList.valueOf(paramInt1), drawable, null);
    this.mContentBackground = this.mRippleDrawable;
    this.mShadowViewDelegate.setBackgroundDrawable(this.mRippleDrawable);
  }
  
  void setRippleColor(int paramInt) {
    if (this.mRippleDrawable instanceof RippleDrawable) {
      ((RippleDrawable)this.mRippleDrawable).setColor(ColorStateList.valueOf(paramInt));
    } else {
      super.setRippleColor(paramInt);
    } 
  }
  
  static class AlwaysStatefulGradientDrawable extends GradientDrawable {
    public boolean isStateful() {
      return true;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\FloatingActionButtonLollipop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */