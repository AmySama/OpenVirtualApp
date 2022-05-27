package io.virtualapp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import io.virtualapp.R;

public class CardStackLayout extends FrameLayout {
  public static final boolean PARALLAX_ENABLED_DEFAULT = false;
  
  public static final boolean SHOW_INIT_ANIMATION_DEFAULT = true;
  
  private CardStackAdapter mAdapter = null;
  
  private float mCardGap;
  
  private float mCardGapBottom;
  
  private OnCardSelected mOnCardSelectedListener = null;
  
  private boolean mParallaxEnabled;
  
  private int mParallaxScale;
  
  private boolean mShowInitAnimation;
  
  public CardStackLayout(Context paramContext) {
    super(paramContext);
    resetDefaults();
  }
  
  public CardStackLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public CardStackLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    handleArgs(paramContext, paramAttributeSet, paramInt, 0);
  }
  
  public CardStackLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    handleArgs(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  private void handleArgs(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    resetDefaults();
    TypedArray typedArray = paramContext.getTheme().obtainStyledAttributes(paramAttributeSet, R.styleable.CardStackLayout, paramInt1, paramInt2);
    this.mParallaxEnabled = typedArray.getBoolean(2, false);
    this.mShowInitAnimation = typedArray.getBoolean(4, true);
    this.mParallaxScale = typedArray.getInteger(3, getResources().getInteger(2131361801));
    this.mCardGap = typedArray.getDimension(0, getResources().getDimension(2131165261));
    this.mCardGapBottom = typedArray.getDimension(1, getResources().getDimension(2131165262));
    typedArray.recycle();
  }
  
  private void resetDefaults() {
    this.mOnCardSelectedListener = null;
  }
  
  public CardStackAdapter getAdapter() {
    return this.mAdapter;
  }
  
  public float getCardGap() {
    return this.mCardGap;
  }
  
  public float getCardGapBottom() {
    return this.mCardGapBottom;
  }
  
  OnCardSelected getOnCardSelectedListener() {
    return this.mOnCardSelectedListener;
  }
  
  public int getParallaxScale() {
    return this.mParallaxScale;
  }
  
  public boolean isCardSelected() {
    return this.mAdapter.isCardSelected();
  }
  
  public boolean isParallaxEnabled() {
    return this.mParallaxEnabled;
  }
  
  public boolean isShowInitAnimation() {
    return this.mShowInitAnimation;
  }
  
  public void removeAdapter() {
    if (getChildCount() > 0)
      removeAllViews(); 
    this.mAdapter = null;
    this.mOnCardSelectedListener = null;
  }
  
  public void restoreCards() {
    this.mAdapter.resetCards();
  }
  
  public void setAdapter(CardStackAdapter paramCardStackAdapter) {
    this.mAdapter = paramCardStackAdapter;
    paramCardStackAdapter.setAdapterParams(this);
    for (byte b = 0; b < this.mAdapter.getCount(); b++)
      this.mAdapter.addView(b); 
    if (this.mShowInitAnimation)
      postDelayed(new _$$Lambda$zKbZi8O4ULg9k4Id6_ThYN7drQA(this), 500L); 
  }
  
  public void setCardGap(float paramFloat) {
    this.mCardGap = paramFloat;
  }
  
  public void setCardGapBottom(float paramFloat) {
    this.mCardGapBottom = paramFloat;
  }
  
  public void setOnCardSelectedListener(OnCardSelected paramOnCardSelected) {
    this.mOnCardSelectedListener = paramOnCardSelected;
  }
  
  public void setParallaxEnabled(boolean paramBoolean) {
    this.mParallaxEnabled = paramBoolean;
  }
  
  public void setParallaxScale(int paramInt) {
    this.mParallaxScale = paramInt;
  }
  
  public void setShowInitAnimation(boolean paramBoolean) {
    this.mShowInitAnimation = paramBoolean;
  }
  
  public static interface OnCardSelected {
    void onCardSelected(View param1View, int param1Int);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\CardStackLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */