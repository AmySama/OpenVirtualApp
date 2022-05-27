package android.support.design.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.design.R;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;

public class ForegroundLinearLayout extends LinearLayoutCompat {
  private Drawable mForeground;
  
  boolean mForegroundBoundsChanged = false;
  
  private int mForegroundGravity = 119;
  
  protected boolean mForegroundInPadding = true;
  
  private final Rect mOverlayBounds = new Rect();
  
  private final Rect mSelfBounds = new Rect();
  
  public ForegroundLinearLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public ForegroundLinearLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ForegroundLinearLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ForegroundLinearLayout, paramInt, 0);
    this.mForegroundGravity = typedArray.getInt(R.styleable.ForegroundLinearLayout_android_foregroundGravity, this.mForegroundGravity);
    Drawable drawable = typedArray.getDrawable(R.styleable.ForegroundLinearLayout_android_foreground);
    if (drawable != null)
      setForeground(drawable); 
    this.mForegroundInPadding = typedArray.getBoolean(R.styleable.ForegroundLinearLayout_foregroundInsidePadding, true);
    typedArray.recycle();
  }
  
  public void draw(Canvas paramCanvas) {
    super.draw(paramCanvas);
    Drawable drawable = this.mForeground;
    if (drawable != null) {
      if (this.mForegroundBoundsChanged) {
        this.mForegroundBoundsChanged = false;
        Rect rect1 = this.mSelfBounds;
        Rect rect2 = this.mOverlayBounds;
        int i = getRight() - getLeft();
        int j = getBottom() - getTop();
        if (this.mForegroundInPadding) {
          rect1.set(0, 0, i, j);
        } else {
          rect1.set(getPaddingLeft(), getPaddingTop(), i - getPaddingRight(), j - getPaddingBottom());
        } 
        Gravity.apply(this.mForegroundGravity, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), rect1, rect2);
        drawable.setBounds(rect2);
      } 
      drawable.draw(paramCanvas);
    } 
  }
  
  public void drawableHotspotChanged(float paramFloat1, float paramFloat2) {
    super.drawableHotspotChanged(paramFloat1, paramFloat2);
    Drawable drawable = this.mForeground;
    if (drawable != null)
      drawable.setHotspot(paramFloat1, paramFloat2); 
  }
  
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    Drawable drawable = this.mForeground;
    if (drawable != null && drawable.isStateful())
      this.mForeground.setState(getDrawableState()); 
  }
  
  public Drawable getForeground() {
    return this.mForeground;
  }
  
  public int getForegroundGravity() {
    return this.mForegroundGravity;
  }
  
  public void jumpDrawablesToCurrentState() {
    super.jumpDrawablesToCurrentState();
    Drawable drawable = this.mForeground;
    if (drawable != null)
      drawable.jumpToCurrentState(); 
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mForegroundBoundsChanged = paramBoolean | this.mForegroundBoundsChanged;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mForegroundBoundsChanged = true;
  }
  
  public void setForeground(Drawable paramDrawable) {
    Drawable drawable = this.mForeground;
    if (drawable != paramDrawable) {
      if (drawable != null) {
        drawable.setCallback(null);
        unscheduleDrawable(this.mForeground);
      } 
      this.mForeground = paramDrawable;
      if (paramDrawable != null) {
        setWillNotDraw(false);
        paramDrawable.setCallback((Drawable.Callback)this);
        if (paramDrawable.isStateful())
          paramDrawable.setState(getDrawableState()); 
        if (this.mForegroundGravity == 119)
          paramDrawable.getPadding(new Rect()); 
      } else {
        setWillNotDraw(true);
      } 
      requestLayout();
      invalidate();
    } 
  }
  
  public void setForegroundGravity(int paramInt) {
    if (this.mForegroundGravity != paramInt) {
      int i = paramInt;
      if ((0x800007 & paramInt) == 0)
        i = paramInt | 0x800003; 
      paramInt = i;
      if ((i & 0x70) == 0)
        paramInt = i | 0x30; 
      this.mForegroundGravity = paramInt;
      if (paramInt == 119 && this.mForeground != null) {
        Rect rect = new Rect();
        this.mForeground.getPadding(rect);
      } 
      requestLayout();
    } 
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable) {
    return (super.verifyDrawable(paramDrawable) || paramDrawable == this.mForeground);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\internal\ForegroundLinearLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */