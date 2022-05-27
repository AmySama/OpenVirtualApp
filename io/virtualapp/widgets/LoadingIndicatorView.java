package io.virtualapp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import io.virtualapp.R;

public class LoadingIndicatorView extends View {
  private static final Indicator DEFAULT_INDICATOR = new BallGridBeatIndicator();
  
  private static final int MIN_DELAY = 500;
  
  private static final int MIN_SHOW_TIME = 500;
  
  private static final String TAG = "LoadingIndicatorView";
  
  private final Runnable mDelayedHide = new Runnable() {
      public void run() {
        LoadingIndicatorView.access$002(LoadingIndicatorView.this, false);
        LoadingIndicatorView.access$102(LoadingIndicatorView.this, -1L);
        LoadingIndicatorView.this.setVisibility(8);
      }
    };
  
  private final Runnable mDelayedShow = new Runnable() {
      public void run() {
        LoadingIndicatorView.access$202(LoadingIndicatorView.this, false);
        if (!LoadingIndicatorView.this.mDismissed) {
          LoadingIndicatorView.access$102(LoadingIndicatorView.this, System.currentTimeMillis());
          LoadingIndicatorView.this.setVisibility(0);
        } 
      }
    };
  
  private boolean mDismissed = false;
  
  private Indicator mIndicator;
  
  private int mIndicatorColor;
  
  int mMaxHeight;
  
  int mMaxWidth;
  
  int mMinHeight;
  
  int mMinWidth;
  
  private boolean mPostedHide = false;
  
  private boolean mPostedShow = false;
  
  private boolean mShouldStartAnimationDrawable;
  
  private long mStartTime = -1L;
  
  public LoadingIndicatorView(Context paramContext) {
    super(paramContext);
    init(paramContext, (AttributeSet)null, 0, 0);
  }
  
  public LoadingIndicatorView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0, 2131689472);
  }
  
  public LoadingIndicatorView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt, 2131689472);
  }
  
  public LoadingIndicatorView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    init(paramContext, paramAttributeSet, paramInt1, 2131689472);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    this.mMinWidth = 24;
    this.mMaxWidth = 48;
    this.mMinHeight = 24;
    this.mMaxHeight = 48;
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.LoadingIndicatorView, paramInt1, paramInt2);
    this.mMinWidth = typedArray.getDimensionPixelSize(5, this.mMinWidth);
    this.mMaxWidth = typedArray.getDimensionPixelSize(3, this.mMaxWidth);
    this.mMinHeight = typedArray.getDimensionPixelSize(4, this.mMinHeight);
    this.mMaxHeight = typedArray.getDimensionPixelSize(2, this.mMaxHeight);
    String str = typedArray.getString(1);
    this.mIndicatorColor = typedArray.getColor(0, -1);
    setIndicator(str);
    if (this.mIndicator == null)
      setIndicator(DEFAULT_INDICATOR); 
    typedArray.recycle();
  }
  
  private void removeCallbacks() {
    removeCallbacks(this.mDelayedHide);
    removeCallbacks(this.mDelayedShow);
  }
  
  private void updateDrawableBounds(int paramInt1, int paramInt2) {
    int i = paramInt1 - getPaddingRight() + getPaddingLeft();
    int j = paramInt2 - getPaddingTop() + getPaddingBottom();
    Indicator indicator = this.mIndicator;
    if (indicator != null) {
      paramInt2 = indicator.getIntrinsicWidth();
      paramInt1 = this.mIndicator.getIntrinsicHeight();
      float f1 = paramInt2 / paramInt1;
      float f2 = i;
      float f3 = j;
      float f4 = f2 / f3;
      int k = 0;
      paramInt1 = 0;
      paramInt2 = i;
      if (f1 != f4)
        if (f4 > f1) {
          k = (int)(f3 * f1);
          paramInt2 = (i - k) / 2;
          paramInt1 = paramInt2;
          paramInt2 = k + paramInt2;
        } else {
          int m = (int)(f2 * 1.0F / f1);
          paramInt2 = (j - m) / 2;
          paramInt1 = paramInt2;
          m += paramInt2;
          paramInt2 = k;
          j = i;
          i = m;
          this.mIndicator.setBounds(paramInt2, paramInt1, j, i);
        }  
      k = 0;
      i = j;
      j = paramInt2;
      paramInt2 = paramInt1;
      paramInt1 = k;
    } else {
      return;
    } 
    this.mIndicator.setBounds(paramInt2, paramInt1, j, i);
  }
  
  private void updateDrawableState() {
    int[] arrayOfInt = getDrawableState();
    Indicator indicator = this.mIndicator;
    if (indicator != null && indicator.isStateful())
      this.mIndicator.setState(arrayOfInt); 
  }
  
  void drawTrack(Canvas paramCanvas) {
    Indicator indicator = this.mIndicator;
    if (indicator != null) {
      int i = paramCanvas.save();
      paramCanvas.translate(getPaddingLeft(), getPaddingTop());
      indicator.draw(paramCanvas);
      paramCanvas.restoreToCount(i);
      if (this.mShouldStartAnimationDrawable && indicator instanceof android.graphics.drawable.Animatable) {
        indicator.start();
        this.mShouldStartAnimationDrawable = false;
      } 
    } 
  }
  
  public void drawableHotspotChanged(float paramFloat1, float paramFloat2) {
    super.drawableHotspotChanged(paramFloat1, paramFloat2);
    Indicator indicator = this.mIndicator;
    if (indicator != null)
      indicator.setHotspot(paramFloat1, paramFloat2); 
  }
  
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    updateDrawableState();
  }
  
  public Indicator getIndicator() {
    return this.mIndicator;
  }
  
  public void hide() {
    this.mDismissed = true;
    removeCallbacks(this.mDelayedShow);
    long l1 = System.currentTimeMillis();
    long l2 = this.mStartTime;
    l1 -= l2;
    if (l1 >= 500L || l2 == -1L) {
      setVisibility(8);
      return;
    } 
    if (!this.mPostedHide) {
      postDelayed(this.mDelayedHide, 500L - l1);
      this.mPostedHide = true;
    } 
  }
  
  public void invalidateDrawable(Drawable paramDrawable) {
    Rect rect;
    if (verifyDrawable(paramDrawable)) {
      rect = paramDrawable.getBounds();
      int i = getScrollX() + getPaddingLeft();
      int j = getScrollY() + getPaddingTop();
      invalidate(rect.left + i, rect.top + j, rect.right + i, rect.bottom + j);
    } else {
      super.invalidateDrawable((Drawable)rect);
    } 
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    startAnimation();
    removeCallbacks();
  }
  
  protected void onDetachedFromWindow() {
    stopAnimation();
    super.onDetachedFromWindow();
    removeCallbacks();
  }
  
  protected void onDraw(Canvas paramCanvas) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: invokespecial onDraw : (Landroid/graphics/Canvas;)V
    //   7: aload_0
    //   8: aload_1
    //   9: invokevirtual drawTrack : (Landroid/graphics/Canvas;)V
    //   12: aload_0
    //   13: monitorexit
    //   14: return
    //   15: astore_1
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_1
    //   19: athrow
    // Exception table:
    //   from	to	target	type
    //   2	12	15	finally
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mIndicator : Lio/virtualapp/widgets/Indicator;
    //   6: astore_3
    //   7: aload_3
    //   8: ifnull -> 54
    //   11: aload_0
    //   12: getfield mMinWidth : I
    //   15: aload_0
    //   16: getfield mMaxWidth : I
    //   19: aload_3
    //   20: invokevirtual getIntrinsicWidth : ()I
    //   23: invokestatic min : (II)I
    //   26: invokestatic max : (II)I
    //   29: istore #4
    //   31: aload_0
    //   32: getfield mMinHeight : I
    //   35: aload_0
    //   36: getfield mMaxHeight : I
    //   39: aload_3
    //   40: invokevirtual getIntrinsicHeight : ()I
    //   43: invokestatic min : (II)I
    //   46: invokestatic max : (II)I
    //   49: istore #5
    //   51: goto -> 60
    //   54: iconst_0
    //   55: istore #5
    //   57: iconst_0
    //   58: istore #4
    //   60: aload_0
    //   61: invokespecial updateDrawableState : ()V
    //   64: aload_0
    //   65: invokevirtual getPaddingLeft : ()I
    //   68: istore #6
    //   70: aload_0
    //   71: invokevirtual getPaddingRight : ()I
    //   74: istore #7
    //   76: aload_0
    //   77: invokevirtual getPaddingTop : ()I
    //   80: istore #8
    //   82: aload_0
    //   83: invokevirtual getPaddingBottom : ()I
    //   86: istore #9
    //   88: aload_0
    //   89: iload #4
    //   91: iload #6
    //   93: iload #7
    //   95: iadd
    //   96: iadd
    //   97: iload_1
    //   98: iconst_0
    //   99: invokestatic resolveSizeAndState : (III)I
    //   102: iload #5
    //   104: iload #8
    //   106: iload #9
    //   108: iadd
    //   109: iadd
    //   110: iload_2
    //   111: iconst_0
    //   112: invokestatic resolveSizeAndState : (III)I
    //   115: invokevirtual setMeasuredDimension : (II)V
    //   118: aload_0
    //   119: monitorexit
    //   120: return
    //   121: astore_3
    //   122: aload_0
    //   123: monitorexit
    //   124: aload_3
    //   125: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	121	finally
    //   11	51	121	finally
    //   60	118	121	finally
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    updateDrawableBounds(paramInt1, paramInt2);
  }
  
  protected void onVisibilityChanged(View paramView, int paramInt) {
    super.onVisibilityChanged(paramView, paramInt);
    if (paramInt == 8 || paramInt == 4) {
      stopAnimation();
      return;
    } 
    startAnimation();
  }
  
  public void setIndicator(Indicator paramIndicator) {
    Indicator indicator = this.mIndicator;
    if (indicator != paramIndicator) {
      if (indicator != null) {
        indicator.setCallback(null);
        unscheduleDrawable(this.mIndicator);
      } 
      this.mIndicator = paramIndicator;
      setIndicatorColor(this.mIndicatorColor);
      if (paramIndicator != null)
        paramIndicator.setCallback((Drawable.Callback)this); 
      postInvalidate();
    } 
  }
  
  public void setIndicator(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    if (!paramString.contains(".")) {
      stringBuilder.append(getClass().getPackage().getName());
      stringBuilder.append(".");
    } 
    stringBuilder.append(paramString);
    try {
      setIndicator((Indicator)Class.forName(stringBuilder.toString()).newInstance());
    } catch (ClassNotFoundException classNotFoundException) {
      Log.e("LoadingIndicatorView", "Didn't find your class , check the name again !");
    } catch (InstantiationException instantiationException) {
      instantiationException.printStackTrace();
    } catch (IllegalAccessException illegalAccessException) {
      illegalAccessException.printStackTrace();
    } 
  }
  
  public void setIndicatorColor(int paramInt) {
    this.mIndicatorColor = paramInt;
    this.mIndicator.setColor(paramInt);
  }
  
  public void setVisibility(int paramInt) {
    if (getVisibility() != paramInt) {
      super.setVisibility(paramInt);
      if (paramInt == 8 || paramInt == 4) {
        stopAnimation();
        return;
      } 
      startAnimation();
    } 
  }
  
  public void show() {
    this.mStartTime = -1L;
    this.mDismissed = false;
    removeCallbacks(this.mDelayedHide);
    if (!this.mPostedShow) {
      postDelayed(this.mDelayedShow, 500L);
      this.mPostedShow = true;
    } 
  }
  
  public void smoothToHide() {
    startAnimation(AnimationUtils.loadAnimation(getContext(), 17432577));
    setVisibility(8);
  }
  
  public void smoothToShow() {
    startAnimation(AnimationUtils.loadAnimation(getContext(), 17432576));
    setVisibility(0);
  }
  
  void startAnimation() {
    if (getVisibility() != 0)
      return; 
    if (this.mIndicator instanceof android.graphics.drawable.Animatable)
      this.mShouldStartAnimationDrawable = true; 
    postInvalidate();
  }
  
  void stopAnimation() {
    Indicator indicator = this.mIndicator;
    if (indicator instanceof android.graphics.drawable.Animatable) {
      indicator.stop();
      this.mShouldStartAnimationDrawable = false;
    } 
    postInvalidate();
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable) {
    return (paramDrawable == this.mIndicator || super.verifyDrawable(paramDrawable));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\LoadingIndicatorView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */