package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;

class AppCompatSeekBarHelper extends AppCompatProgressBarHelper {
  private boolean mHasTickMarkTint = false;
  
  private boolean mHasTickMarkTintMode = false;
  
  private Drawable mTickMark;
  
  private ColorStateList mTickMarkTintList = null;
  
  private PorterDuff.Mode mTickMarkTintMode = null;
  
  private final SeekBar mView;
  
  AppCompatSeekBarHelper(SeekBar paramSeekBar) {
    super((ProgressBar)paramSeekBar);
    this.mView = paramSeekBar;
  }
  
  private void applyTickMarkTint() {
    if (this.mTickMark != null && (this.mHasTickMarkTint || this.mHasTickMarkTintMode)) {
      Drawable drawable = DrawableCompat.wrap(this.mTickMark.mutate());
      this.mTickMark = drawable;
      if (this.mHasTickMarkTint)
        DrawableCompat.setTintList(drawable, this.mTickMarkTintList); 
      if (this.mHasTickMarkTintMode)
        DrawableCompat.setTintMode(this.mTickMark, this.mTickMarkTintMode); 
      if (this.mTickMark.isStateful())
        this.mTickMark.setState(this.mView.getDrawableState()); 
    } 
  }
  
  void drawTickMarks(Canvas paramCanvas) {
    if (this.mTickMark != null) {
      int i = this.mView.getMax();
      int j = 1;
      if (i > 1) {
        int k = this.mTickMark.getIntrinsicWidth();
        int m = this.mTickMark.getIntrinsicHeight();
        if (k >= 0) {
          k /= 2;
        } else {
          k = 1;
        } 
        if (m >= 0)
          j = m / 2; 
        this.mTickMark.setBounds(-k, -j, k, j);
        float f = (this.mView.getWidth() - this.mView.getPaddingLeft() - this.mView.getPaddingRight()) / i;
        j = paramCanvas.save();
        paramCanvas.translate(this.mView.getPaddingLeft(), (this.mView.getHeight() / 2));
        for (k = 0; k <= i; k++) {
          this.mTickMark.draw(paramCanvas);
          paramCanvas.translate(f, 0.0F);
        } 
        paramCanvas.restoreToCount(j);
      } 
    } 
  }
  
  void drawableStateChanged() {
    Drawable drawable = this.mTickMark;
    if (drawable != null && drawable.isStateful() && drawable.setState(this.mView.getDrawableState()))
      this.mView.invalidateDrawable(drawable); 
  }
  
  Drawable getTickMark() {
    return this.mTickMark;
  }
  
  ColorStateList getTickMarkTintList() {
    return this.mTickMarkTintList;
  }
  
  PorterDuff.Mode getTickMarkTintMode() {
    return this.mTickMarkTintMode;
  }
  
  void jumpDrawablesToCurrentState() {
    Drawable drawable = this.mTickMark;
    if (drawable != null)
      drawable.jumpToCurrentState(); 
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt) {
    super.loadFromAttributes(paramAttributeSet, paramInt);
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), paramAttributeSet, R.styleable.AppCompatSeekBar, paramInt, 0);
    Drawable drawable = tintTypedArray.getDrawableIfKnown(R.styleable.AppCompatSeekBar_android_thumb);
    if (drawable != null)
      this.mView.setThumb(drawable); 
    setTickMark(tintTypedArray.getDrawable(R.styleable.AppCompatSeekBar_tickMark));
    if (tintTypedArray.hasValue(R.styleable.AppCompatSeekBar_tickMarkTintMode)) {
      this.mTickMarkTintMode = DrawableUtils.parseTintMode(tintTypedArray.getInt(R.styleable.AppCompatSeekBar_tickMarkTintMode, -1), this.mTickMarkTintMode);
      this.mHasTickMarkTintMode = true;
    } 
    if (tintTypedArray.hasValue(R.styleable.AppCompatSeekBar_tickMarkTint)) {
      this.mTickMarkTintList = tintTypedArray.getColorStateList(R.styleable.AppCompatSeekBar_tickMarkTint);
      this.mHasTickMarkTint = true;
    } 
    tintTypedArray.recycle();
    applyTickMarkTint();
  }
  
  void setTickMark(Drawable paramDrawable) {
    Drawable drawable = this.mTickMark;
    if (drawable != null)
      drawable.setCallback(null); 
    this.mTickMark = paramDrawable;
    if (paramDrawable != null) {
      paramDrawable.setCallback((Drawable.Callback)this.mView);
      DrawableCompat.setLayoutDirection(paramDrawable, ViewCompat.getLayoutDirection((View)this.mView));
      if (paramDrawable.isStateful())
        paramDrawable.setState(this.mView.getDrawableState()); 
      applyTickMarkTint();
    } 
    this.mView.invalidate();
  }
  
  void setTickMarkTintList(ColorStateList paramColorStateList) {
    this.mTickMarkTintList = paramColorStateList;
    this.mHasTickMarkTint = true;
    applyTickMarkTint();
  }
  
  void setTickMarkTintMode(PorterDuff.Mode paramMode) {
    this.mTickMarkTintMode = paramMode;
    this.mHasTickMarkTintMode = true;
    applyTickMarkTint();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\AppCompatSeekBarHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */