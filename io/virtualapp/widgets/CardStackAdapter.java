package io.virtualapp.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.List;

public abstract class CardStackAdapter implements View.OnTouchListener, View.OnClickListener {
  public static final int ANIM_DURATION = 600;
  
  public static final int DECELERATION_FACTOR = 2;
  
  public static final int INVALID_CARD_POSITION = -1;
  
  private final int dp30;
  
  private float dp8;
  
  private int fullCardHeight;
  
  private float mCardGap;
  
  private float mCardGapBottom;
  
  private int mCardPaddingInternal = 0;
  
  private View[] mCardViews;
  
  private boolean mParallaxEnabled;
  
  private int mParallaxScale;
  
  private CardStackLayout mParent;
  
  private int mParentPaddingTop = 0;
  
  private final int mScreenHeight;
  
  private boolean mScreenTouchable = false;
  
  private int mSelectedCardPosition = -1;
  
  private boolean mShowInitAnimation;
  
  private float mTouchDistance = 0.0F;
  
  private float mTouchFirstY = -1.0F;
  
  private float mTouchPrevY = -1.0F;
  
  private float scaleFactorForElasticEffect;
  
  public CardStackAdapter(Context paramContext) {
    Resources resources = paramContext.getResources();
    this.mScreenHeight = (Resources.getSystem().getDisplayMetrics()).heightPixels;
    this.dp30 = (int)resources.getDimension(2131165374);
    this.scaleFactorForElasticEffect = (int)resources.getDimension(2131165375);
    this.dp8 = (int)resources.getDimension(2131165375);
  }
  
  private void moveCards(int paramInt, float paramFloat) {
    if (paramFloat >= 0.0F && paramInt >= 0) {
      int i = paramInt;
      if (paramInt < getCount())
        while (i < getCount()) {
          View view = this.mCardViews[i];
          float f1 = paramFloat / this.scaleFactorForElasticEffect;
          if (this.mParallaxEnabled) {
            paramInt = this.mParallaxScale;
            if (paramInt > 0) {
              f1 *= (paramInt / 3);
              paramInt = getCount() + 1 - i;
            } else {
              paramInt = paramInt * -1 / 3 * i;
              paramInt++;
            } 
          } else {
            paramInt = getCount() * 2;
            paramInt++;
          } 
          float f2 = paramInt;
          view.setY(getCardOriginalY(i) + f1 * f2);
          i++;
        }  
    } 
  }
  
  private void setScreenTouchable(boolean paramBoolean) {
    this.mScreenTouchable = paramBoolean;
  }
  
  private void startAnimations(List<Animator> paramList, final Runnable r, final boolean isReset) {
    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playTogether(paramList);
    animatorSet.setDuration(600L);
    animatorSet.setInterpolator((TimeInterpolator)new DecelerateInterpolator(2.0F));
    animatorSet.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
          public void onAnimationEnd(Animator param1Animator) {
            Runnable runnable = r;
            if (runnable != null)
              runnable.run(); 
            CardStackAdapter.this.setScreenTouchable(true);
            if (isReset)
              CardStackAdapter.access$102(CardStackAdapter.this, -1); 
          }
        });
    animatorSet.start();
  }
  
  void addView(int paramInt) {
    View view = createView(paramInt, (ViewGroup)this.mParent);
    view.setOnTouchListener(this);
    view.setTag(2131296397, Integer.valueOf(paramInt));
    view.setLayerType(2, null);
    this.mCardPaddingInternal = view.getPaddingTop();
    view.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, this.fullCardHeight));
    if (this.mShowInitAnimation) {
      view.setY(getCardFinalY(paramInt));
      setScreenTouchable(false);
    } else {
      view.setY(getCardOriginalY(paramInt) - this.mParentPaddingTop);
      setScreenTouchable(true);
    } 
    this.mCardViews[paramInt] = view;
    this.mParent.addView(view);
  }
  
  public abstract View createView(int paramInt, ViewGroup paramViewGroup);
  
  protected Animator getAnimatorForView(View paramView, int paramInt1, int paramInt2) {
    return (Animator)((paramInt1 != paramInt2) ? ObjectAnimator.ofFloat(paramView, View.Y, new float[] { (int)paramView.getY(), getCardFinalY(paramInt1) }) : ObjectAnimator.ofFloat(paramView, View.Y, new float[] { (int)paramView.getY(), getCardOriginalY(0) + paramInt1 * this.mCardGapBottom }));
  }
  
  protected float getCardFinalY(int paramInt) {
    return (this.mScreenHeight - this.dp30) - (getCount() - paramInt) * this.mCardGapBottom - this.mCardPaddingInternal;
  }
  
  protected float getCardGapBottom() {
    return this.mCardGapBottom;
  }
  
  protected float getCardOriginalY(int paramInt) {
    return this.mParentPaddingTop + this.mCardGap * paramInt;
  }
  
  public View getCardView(int paramInt) {
    View[] arrayOfView = this.mCardViews;
    return (arrayOfView == null) ? null : arrayOfView[paramInt];
  }
  
  public abstract int getCount();
  
  public int getSelectedCardPosition() {
    return this.mSelectedCardPosition;
  }
  
  public boolean isCardSelected() {
    boolean bool;
    if (this.mSelectedCardPosition != -1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isScreenTouchable() {
    return this.mScreenTouchable;
  }
  
  public void onClick(View paramView) {
    if (!isScreenTouchable())
      return; 
    setScreenTouchable(false);
    if (this.mSelectedCardPosition == -1) {
      this.mSelectedCardPosition = ((Integer)paramView.getTag(2131296397)).intValue();
      ArrayList<Animator> arrayList = new ArrayList(getCount());
      for (byte b = 0; b < getCount(); b++)
        arrayList.add(getAnimatorForView(this.mCardViews[b], b, this.mSelectedCardPosition)); 
      startAnimations(arrayList, new _$$Lambda$CardStackAdapter$3zofJzLL2nHQz14D89AjeM4_vQw(this, paramView), false);
    } 
  }
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
    if (!isScreenTouchable())
      return false; 
    float f = paramMotionEvent.getRawY();
    int i = ((Integer)paramView.getTag(2131296397)).intValue();
    int j = paramMotionEvent.getAction();
    if (j != 0) {
      if (j != 1)
        if (j != 2) {
          if (j != 3)
            return true; 
        } else {
          if (this.mSelectedCardPosition == -1)
            moveCards(i, f - this.mTouchFirstY); 
          this.mTouchDistance += Math.abs(f - this.mTouchPrevY);
          return true;
        }  
      if (this.mTouchDistance < this.dp8 && Math.abs(f - this.mTouchFirstY) < this.dp8 && this.mSelectedCardPosition == -1) {
        onClick(paramView);
      } else {
        resetCards();
      } 
      this.mTouchFirstY = -1.0F;
      this.mTouchPrevY = -1.0F;
      this.mTouchDistance = 0.0F;
      return false;
    } 
    if (this.mTouchFirstY != -1.0F)
      return false; 
    this.mTouchFirstY = f;
    this.mTouchPrevY = f;
    this.mTouchDistance = 0.0F;
    return true;
  }
  
  public void resetCards() {
    resetCards(null);
  }
  
  public void resetCards(Runnable paramRunnable) {
    ArrayList<ObjectAnimator> arrayList = new ArrayList(getCount());
    for (byte b = 0; b < getCount(); b++) {
      View view = this.mCardViews[b];
      arrayList.add(ObjectAnimator.ofFloat(view, View.Y, new float[] { (int)view.getY(), getCardOriginalY(b) }));
    } 
    startAnimations((List)arrayList, paramRunnable, true);
  }
  
  void setAdapterParams(CardStackLayout paramCardStackLayout) {
    this.mParent = paramCardStackLayout;
    this.mCardViews = new View[getCount()];
    this.mCardGapBottom = paramCardStackLayout.getCardGapBottom();
    this.mCardGap = paramCardStackLayout.getCardGap();
    this.mParallaxScale = paramCardStackLayout.getParallaxScale();
    boolean bool = paramCardStackLayout.isParallaxEnabled();
    this.mParallaxEnabled = bool;
    if (bool && this.mParallaxScale == 0)
      this.mParallaxEnabled = false; 
    this.mShowInitAnimation = paramCardStackLayout.isShowInitAnimation();
    this.mParentPaddingTop = paramCardStackLayout.getPaddingTop();
    this.fullCardHeight = (int)((this.mScreenHeight - this.dp30) - this.dp8 - getCount() * this.mCardGapBottom);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\CardStackAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */