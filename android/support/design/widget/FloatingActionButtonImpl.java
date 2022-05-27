package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.design.R;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;

class FloatingActionButtonImpl {
  static final Interpolator ANIM_INTERPOLATOR = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
  
  static final int ANIM_STATE_HIDING = 1;
  
  static final int ANIM_STATE_NONE = 0;
  
  static final int ANIM_STATE_SHOWING = 2;
  
  static final int[] EMPTY_STATE_SET;
  
  static final int[] ENABLED_STATE_SET;
  
  static final int[] FOCUSED_ENABLED_STATE_SET;
  
  static final long PRESSED_ANIM_DELAY = 100L;
  
  static final long PRESSED_ANIM_DURATION = 100L;
  
  static final int[] PRESSED_ENABLED_STATE_SET = new int[] { 16842919, 16842910 };
  
  static final int SHOW_HIDE_ANIM_DURATION = 200;
  
  int mAnimState = 0;
  
  CircularBorderDrawable mBorderDrawable;
  
  Drawable mContentBackground;
  
  float mElevation;
  
  private ViewTreeObserver.OnPreDrawListener mPreDrawListener;
  
  float mPressedTranslationZ;
  
  Drawable mRippleDrawable;
  
  private float mRotation;
  
  ShadowDrawableWrapper mShadowDrawable;
  
  final ShadowViewDelegate mShadowViewDelegate;
  
  Drawable mShapeDrawable;
  
  private final StateListAnimator mStateListAnimator;
  
  private final Rect mTmpRect = new Rect();
  
  final VisibilityAwareImageButton mView;
  
  static {
    FOCUSED_ENABLED_STATE_SET = new int[] { 16842908, 16842910 };
    ENABLED_STATE_SET = new int[] { 16842910 };
    EMPTY_STATE_SET = new int[0];
  }
  
  FloatingActionButtonImpl(VisibilityAwareImageButton paramVisibilityAwareImageButton, ShadowViewDelegate paramShadowViewDelegate) {
    this.mView = paramVisibilityAwareImageButton;
    this.mShadowViewDelegate = paramShadowViewDelegate;
    StateListAnimator stateListAnimator = new StateListAnimator();
    this.mStateListAnimator = stateListAnimator;
    stateListAnimator.addState(PRESSED_ENABLED_STATE_SET, createAnimator(new ElevateToTranslationZAnimation()));
    this.mStateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, createAnimator(new ElevateToTranslationZAnimation()));
    this.mStateListAnimator.addState(ENABLED_STATE_SET, createAnimator(new ResetElevationAnimation()));
    this.mStateListAnimator.addState(EMPTY_STATE_SET, createAnimator(new DisabledElevationAnimation()));
    this.mRotation = this.mView.getRotation();
  }
  
  private ValueAnimator createAnimator(ShadowAnimatorImpl paramShadowAnimatorImpl) {
    ValueAnimator valueAnimator = new ValueAnimator();
    valueAnimator.setInterpolator((TimeInterpolator)ANIM_INTERPOLATOR);
    valueAnimator.setDuration(100L);
    valueAnimator.addListener((Animator.AnimatorListener)paramShadowAnimatorImpl);
    valueAnimator.addUpdateListener(paramShadowAnimatorImpl);
    valueAnimator.setFloatValues(new float[] { 0.0F, 1.0F });
    return valueAnimator;
  }
  
  private static ColorStateList createColorStateList(int paramInt) {
    return new ColorStateList(new int[][] { FOCUSED_ENABLED_STATE_SET, PRESSED_ENABLED_STATE_SET, {} }, new int[] { paramInt, paramInt, 0 });
  }
  
  private void ensurePreDrawListener() {
    if (this.mPreDrawListener == null)
      this.mPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
          public boolean onPreDraw() {
            FloatingActionButtonImpl.this.onPreDraw();
            return true;
          }
        }; 
  }
  
  private boolean shouldAnimateVisibilityChange() {
    boolean bool;
    if (ViewCompat.isLaidOut((View)this.mView) && !this.mView.isInEditMode()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private void updateFromViewRotation() {
    if (Build.VERSION.SDK_INT == 19)
      if (this.mRotation % 90.0F != 0.0F) {
        if (this.mView.getLayerType() != 1)
          this.mView.setLayerType(1, null); 
      } else if (this.mView.getLayerType() != 0) {
        this.mView.setLayerType(0, null);
      }  
    ShadowDrawableWrapper shadowDrawableWrapper = this.mShadowDrawable;
    if (shadowDrawableWrapper != null)
      shadowDrawableWrapper.setRotation(-this.mRotation); 
    CircularBorderDrawable circularBorderDrawable = this.mBorderDrawable;
    if (circularBorderDrawable != null)
      circularBorderDrawable.setRotation(-this.mRotation); 
  }
  
  CircularBorderDrawable createBorderDrawable(int paramInt, ColorStateList paramColorStateList) {
    Context context = this.mView.getContext();
    CircularBorderDrawable circularBorderDrawable = newCircularDrawable();
    circularBorderDrawable.setGradientColors(ContextCompat.getColor(context, R.color.design_fab_stroke_top_outer_color), ContextCompat.getColor(context, R.color.design_fab_stroke_top_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_outer_color));
    circularBorderDrawable.setBorderWidth(paramInt);
    circularBorderDrawable.setBorderTint(paramColorStateList);
    return circularBorderDrawable;
  }
  
  GradientDrawable createShapeDrawable() {
    GradientDrawable gradientDrawable = newGradientDrawableForShape();
    gradientDrawable.setShape(1);
    gradientDrawable.setColor(-1);
    return gradientDrawable;
  }
  
  final Drawable getContentBackground() {
    return this.mContentBackground;
  }
  
  float getElevation() {
    return this.mElevation;
  }
  
  void getPadding(Rect paramRect) {
    this.mShadowDrawable.getPadding(paramRect);
  }
  
  void hide(final InternalVisibilityChangedListener listener, final boolean fromUser) {
    if (isOrWillBeHidden())
      return; 
    this.mView.animate().cancel();
    if (shouldAnimateVisibilityChange()) {
      this.mAnimState = 1;
      this.mView.animate().scaleX(0.0F).scaleY(0.0F).alpha(0.0F).setDuration(200L).setInterpolator((TimeInterpolator)AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
            private boolean mCancelled;
            
            public void onAnimationCancel(Animator param1Animator) {
              this.mCancelled = true;
            }
            
            public void onAnimationEnd(Animator param1Animator) {
              FloatingActionButtonImpl.this.mAnimState = 0;
              if (!this.mCancelled) {
                byte b;
                VisibilityAwareImageButton visibilityAwareImageButton = FloatingActionButtonImpl.this.mView;
                if (fromUser) {
                  b = 8;
                } else {
                  b = 4;
                } 
                visibilityAwareImageButton.internalSetVisibility(b, fromUser);
                FloatingActionButtonImpl.InternalVisibilityChangedListener internalVisibilityChangedListener = listener;
                if (internalVisibilityChangedListener != null)
                  internalVisibilityChangedListener.onHidden(); 
              } 
            }
            
            public void onAnimationStart(Animator param1Animator) {
              FloatingActionButtonImpl.this.mView.internalSetVisibility(0, fromUser);
              this.mCancelled = false;
            }
          });
    } else {
      byte b;
      VisibilityAwareImageButton visibilityAwareImageButton = this.mView;
      if (fromUser) {
        b = 8;
      } else {
        b = 4;
      } 
      visibilityAwareImageButton.internalSetVisibility(b, fromUser);
      if (listener != null)
        listener.onHidden(); 
    } 
  }
  
  boolean isOrWillBeHidden() {
    int i = this.mView.getVisibility();
    boolean bool1 = false;
    boolean bool2 = false;
    if (i == 0) {
      if (this.mAnimState == 1)
        bool2 = true; 
      return bool2;
    } 
    bool2 = bool1;
    if (this.mAnimState != 2)
      bool2 = true; 
    return bool2;
  }
  
  boolean isOrWillBeShown() {
    int i = this.mView.getVisibility();
    boolean bool1 = false;
    boolean bool2 = false;
    if (i != 0) {
      if (this.mAnimState == 2)
        bool2 = true; 
      return bool2;
    } 
    bool2 = bool1;
    if (this.mAnimState != 1)
      bool2 = true; 
    return bool2;
  }
  
  void jumpDrawableToCurrentState() {
    this.mStateListAnimator.jumpToCurrentState();
  }
  
  CircularBorderDrawable newCircularDrawable() {
    return new CircularBorderDrawable();
  }
  
  GradientDrawable newGradientDrawableForShape() {
    return new GradientDrawable();
  }
  
  void onAttachedToWindow() {
    if (requirePreDrawListener()) {
      ensurePreDrawListener();
      this.mView.getViewTreeObserver().addOnPreDrawListener(this.mPreDrawListener);
    } 
  }
  
  void onCompatShadowChanged() {}
  
  void onDetachedFromWindow() {
    if (this.mPreDrawListener != null) {
      this.mView.getViewTreeObserver().removeOnPreDrawListener(this.mPreDrawListener);
      this.mPreDrawListener = null;
    } 
  }
  
  void onDrawableStateChanged(int[] paramArrayOfint) {
    this.mStateListAnimator.setState(paramArrayOfint);
  }
  
  void onElevationsChanged(float paramFloat1, float paramFloat2) {
    ShadowDrawableWrapper shadowDrawableWrapper = this.mShadowDrawable;
    if (shadowDrawableWrapper != null) {
      shadowDrawableWrapper.setShadowSize(paramFloat1, this.mPressedTranslationZ + paramFloat1);
      updatePadding();
    } 
  }
  
  void onPaddingUpdated(Rect paramRect) {}
  
  void onPreDraw() {
    float f = this.mView.getRotation();
    if (this.mRotation != f) {
      this.mRotation = f;
      updateFromViewRotation();
    } 
  }
  
  boolean requirePreDrawListener() {
    return true;
  }
  
  void setBackgroundDrawable(ColorStateList paramColorStateList, PorterDuff.Mode paramMode, int paramInt1, int paramInt2) {
    Drawable[] arrayOfDrawable;
    Drawable drawable2 = DrawableCompat.wrap((Drawable)createShapeDrawable());
    this.mShapeDrawable = drawable2;
    DrawableCompat.setTintList(drawable2, paramColorStateList);
    if (paramMode != null)
      DrawableCompat.setTintMode(this.mShapeDrawable, paramMode); 
    Drawable drawable1 = DrawableCompat.wrap((Drawable)createShapeDrawable());
    this.mRippleDrawable = drawable1;
    DrawableCompat.setTintList(drawable1, createColorStateList(paramInt1));
    if (paramInt2 > 0) {
      drawable1 = createBorderDrawable(paramInt2, paramColorStateList);
      this.mBorderDrawable = (CircularBorderDrawable)drawable1;
      arrayOfDrawable = new Drawable[3];
      arrayOfDrawable[0] = drawable1;
      arrayOfDrawable[1] = this.mShapeDrawable;
      arrayOfDrawable[2] = this.mRippleDrawable;
    } else {
      this.mBorderDrawable = null;
      arrayOfDrawable = new Drawable[2];
      arrayOfDrawable[0] = this.mShapeDrawable;
      arrayOfDrawable[1] = this.mRippleDrawable;
    } 
    this.mContentBackground = (Drawable)new LayerDrawable(arrayOfDrawable);
    Context context = this.mView.getContext();
    drawable1 = this.mContentBackground;
    float f1 = this.mShadowViewDelegate.getRadius();
    float f2 = this.mElevation;
    ShadowDrawableWrapper shadowDrawableWrapper = new ShadowDrawableWrapper(context, drawable1, f1, f2, f2 + this.mPressedTranslationZ);
    this.mShadowDrawable = shadowDrawableWrapper;
    shadowDrawableWrapper.setAddPaddingForCorners(false);
    this.mShadowViewDelegate.setBackgroundDrawable((Drawable)this.mShadowDrawable);
  }
  
  void setBackgroundTintList(ColorStateList paramColorStateList) {
    Drawable drawable = this.mShapeDrawable;
    if (drawable != null)
      DrawableCompat.setTintList(drawable, paramColorStateList); 
    drawable = this.mBorderDrawable;
    if (drawable != null)
      drawable.setBorderTint(paramColorStateList); 
  }
  
  void setBackgroundTintMode(PorterDuff.Mode paramMode) {
    Drawable drawable = this.mShapeDrawable;
    if (drawable != null)
      DrawableCompat.setTintMode(drawable, paramMode); 
  }
  
  final void setElevation(float paramFloat) {
    if (this.mElevation != paramFloat) {
      this.mElevation = paramFloat;
      onElevationsChanged(paramFloat, this.mPressedTranslationZ);
    } 
  }
  
  final void setPressedTranslationZ(float paramFloat) {
    if (this.mPressedTranslationZ != paramFloat) {
      this.mPressedTranslationZ = paramFloat;
      onElevationsChanged(this.mElevation, paramFloat);
    } 
  }
  
  void setRippleColor(int paramInt) {
    Drawable drawable = this.mRippleDrawable;
    if (drawable != null)
      DrawableCompat.setTintList(drawable, createColorStateList(paramInt)); 
  }
  
  void show(final InternalVisibilityChangedListener listener, final boolean fromUser) {
    if (isOrWillBeShown())
      return; 
    this.mView.animate().cancel();
    if (shouldAnimateVisibilityChange()) {
      this.mAnimState = 2;
      if (this.mView.getVisibility() != 0) {
        this.mView.setAlpha(0.0F);
        this.mView.setScaleY(0.0F);
        this.mView.setScaleX(0.0F);
      } 
      this.mView.animate().scaleX(1.0F).scaleY(1.0F).alpha(1.0F).setDuration(200L).setInterpolator((TimeInterpolator)AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator param1Animator) {
              FloatingActionButtonImpl.this.mAnimState = 0;
              FloatingActionButtonImpl.InternalVisibilityChangedListener internalVisibilityChangedListener = listener;
              if (internalVisibilityChangedListener != null)
                internalVisibilityChangedListener.onShown(); 
            }
            
            public void onAnimationStart(Animator param1Animator) {
              FloatingActionButtonImpl.this.mView.internalSetVisibility(0, fromUser);
            }
          });
    } else {
      this.mView.internalSetVisibility(0, fromUser);
      this.mView.setAlpha(1.0F);
      this.mView.setScaleY(1.0F);
      this.mView.setScaleX(1.0F);
      if (listener != null)
        listener.onShown(); 
    } 
  }
  
  final void updatePadding() {
    Rect rect = this.mTmpRect;
    getPadding(rect);
    onPaddingUpdated(rect);
    this.mShadowViewDelegate.setShadowPadding(rect.left, rect.top, rect.right, rect.bottom);
  }
  
  private class DisabledElevationAnimation extends ShadowAnimatorImpl {
    protected float getTargetShadowSize() {
      return 0.0F;
    }
  }
  
  private class ElevateToTranslationZAnimation extends ShadowAnimatorImpl {
    protected float getTargetShadowSize() {
      return FloatingActionButtonImpl.this.mElevation + FloatingActionButtonImpl.this.mPressedTranslationZ;
    }
  }
  
  static interface InternalVisibilityChangedListener {
    void onHidden();
    
    void onShown();
  }
  
  private class ResetElevationAnimation extends ShadowAnimatorImpl {
    protected float getTargetShadowSize() {
      return FloatingActionButtonImpl.this.mElevation;
    }
  }
  
  private abstract class ShadowAnimatorImpl extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
    private float mShadowSizeEnd;
    
    private float mShadowSizeStart;
    
    private boolean mValidValues;
    
    private ShadowAnimatorImpl() {}
    
    protected abstract float getTargetShadowSize();
    
    public void onAnimationEnd(Animator param1Animator) {
      FloatingActionButtonImpl.this.mShadowDrawable.setShadowSize(this.mShadowSizeEnd);
      this.mValidValues = false;
    }
    
    public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
      if (!this.mValidValues) {
        this.mShadowSizeStart = FloatingActionButtonImpl.this.mShadowDrawable.getShadowSize();
        this.mShadowSizeEnd = getTargetShadowSize();
        this.mValidValues = true;
      } 
      ShadowDrawableWrapper shadowDrawableWrapper = FloatingActionButtonImpl.this.mShadowDrawable;
      float f = this.mShadowSizeStart;
      shadowDrawableWrapper.setShadowSize(f + (this.mShadowSizeEnd - f) * param1ValueAnimator.getAnimatedFraction());
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\FloatingActionButtonImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */