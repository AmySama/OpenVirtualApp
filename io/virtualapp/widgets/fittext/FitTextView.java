package io.virtualapp.widgets.fittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class FitTextView extends BaseTextView {
  protected FitTextHelper mFitTextHelper;
  
  protected volatile boolean mFittingText = false;
  
  private float mMaxTextSize;
  
  private boolean mMeasured = false;
  
  private float mMinTextSize;
  
  private boolean mNeedFit = true;
  
  protected CharSequence mOriginalText;
  
  protected float mOriginalTextSize = 0.0F;
  
  public FitTextView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public FitTextView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public FitTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    float f = getTextSize();
    this.mOriginalTextSize = f;
    if (paramAttributeSet != null) {
      TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, new int[] { 2130968751, 2130968752 });
      this.mMaxTextSize = typedArray.getDimension(0, this.mOriginalTextSize * 2.0F);
      this.mMinTextSize = typedArray.getDimension(1, this.mOriginalTextSize / 2.0F);
      typedArray.recycle();
    } else {
      this.mMinTextSize = f;
      this.mMaxTextSize = f;
    } 
  }
  
  protected void fitText(CharSequence paramCharSequence) {
    if (!this.mNeedFit)
      return; 
    if (this.mMeasured && !this.mFittingText && !this.mSingleLine && !TextUtils.isEmpty(paramCharSequence)) {
      this.mFittingText = true;
      TextPaint textPaint = getPaint();
      super.setTextSize(0, getFitTextHelper().fitTextSize(textPaint, paramCharSequence, this.mMaxTextSize, this.mMinTextSize));
      setText(getFitTextHelper().getLineBreaks(paramCharSequence, getPaint()));
      this.mFittingText = false;
    } 
  }
  
  protected FitTextHelper getFitTextHelper() {
    if (this.mFitTextHelper == null)
      this.mFitTextHelper = new FitTextHelper(this); 
    return this.mFitTextHelper;
  }
  
  public float getMaxTextSize() {
    return this.mMaxTextSize;
  }
  
  public float getMinTextSize() {
    return this.mMinTextSize;
  }
  
  public CharSequence getOriginalText() {
    return this.mOriginalText;
  }
  
  public float getOriginalTextSize() {
    return this.mOriginalTextSize;
  }
  
  public boolean isNeedFit() {
    return this.mNeedFit;
  }
  
  protected void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    super.onMeasure(paramInt1, paramInt2);
    paramInt1 = View.MeasureSpec.getMode(paramInt1);
    paramInt2 = View.MeasureSpec.getMode(paramInt2);
    if (paramInt1 == 0 && paramInt2 == 0) {
      super.setTextSize(0, this.mOriginalTextSize);
      this.mMeasured = false;
    } else {
      this.mMeasured = true;
      fitText(getOriginalText());
    } 
  }
  
  public void setMaxTextSize(float paramFloat) {
    this.mMaxTextSize = paramFloat;
  }
  
  public void setMinTextSize(float paramFloat) {
    this.mMinTextSize = paramFloat;
  }
  
  public void setNeedFit(boolean paramBoolean) {
    this.mNeedFit = paramBoolean;
  }
  
  public void setText(CharSequence paramCharSequence, TextView.BufferType paramBufferType) {
    this.mOriginalText = paramCharSequence;
    super.setText(paramCharSequence, paramBufferType);
    fitText(paramCharSequence);
  }
  
  public void setTextSize(int paramInt, float paramFloat) {
    super.setTextSize(paramInt, paramFloat);
    this.mOriginalTextSize = getTextSize();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\fittext\FitTextView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */