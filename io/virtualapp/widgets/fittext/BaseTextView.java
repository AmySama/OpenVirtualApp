package io.virtualapp.widgets.fittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

class BaseTextView extends TextView {
  private static final int[] ANDROID_ATTRS = new int[] { 16843103, 16843288, 16843287, 16843091, 16843101 };
  
  protected boolean mIncludeFontPadding = true;
  
  protected boolean mJustify = false;
  
  protected boolean mKeepWord = true;
  
  protected boolean mLineEndNoSpace = true;
  
  protected float mLineSpacingAdd = 0.0F;
  
  protected float mLineSpacingMult = 1.0F;
  
  protected int mMaxLines = Integer.MAX_VALUE;
  
  protected boolean mSingleLine = false;
  
  public BaseTextView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public BaseTextView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    if (paramAttributeSet != null) {
      TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, ANDROID_ATTRS);
      if (Build.VERSION.SDK_INT < 16) {
        this.mIncludeFontPadding = typedArray.getBoolean(typedArray.getIndex(0), this.mIncludeFontPadding);
        this.mLineSpacingMult = typedArray.getFloat(typedArray.getIndex(1), this.mLineSpacingMult);
        this.mLineSpacingAdd = typedArray.getDimensionPixelSize(typedArray.getIndex(2), (int)this.mLineSpacingAdd);
        this.mMaxLines = typedArray.getInteger(typedArray.getIndex(3), this.mMaxLines);
      } 
      this.mSingleLine = typedArray.getBoolean(16843101, this.mSingleLine);
      typedArray.recycle();
    } 
  }
  
  public BaseTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet);
  }
  
  protected int countEmpty(CharSequence paramCharSequence) {
    int i = paramCharSequence.length();
    int j = 0;
    int k;
    for (k = 0; j < i; k = n) {
      int m = j + 1;
      int n = k;
      if (isEmpty(paramCharSequence, j, m))
        n = k + 1; 
      j = m;
    } 
    return k;
  }
  
  public boolean getIncludeFontPaddingCompat() {
    return (Build.VERSION.SDK_INT >= 16) ? getIncludeFontPadding() : this.mIncludeFontPadding;
  }
  
  public float getLineSpacingExtraCompat() {
    return (Build.VERSION.SDK_INT >= 16) ? getLineSpacingExtra() : this.mLineSpacingAdd;
  }
  
  public float getLineSpacingMultiplierCompat() {
    return (Build.VERSION.SDK_INT >= 16) ? getLineSpacingMultiplier() : this.mLineSpacingMult;
  }
  
  public int getMaxLinesCompat() {
    return (Build.VERSION.SDK_INT >= 16) ? getMaxLines() : this.mMaxLines;
  }
  
  public int getTextHeight() {
    return getMeasuredHeight() - getCompoundPaddingTop() - getCompoundPaddingBottom();
  }
  
  public float getTextLineHeight() {
    return getLineHeight();
  }
  
  public TextView getTextView() {
    return this;
  }
  
  public int getTextWidth() {
    return FitTextHelper.getTextWidth(this);
  }
  
  protected boolean isEmpty(CharSequence paramCharSequence, int paramInt1, int paramInt2) {
    int i = paramCharSequence.length();
    boolean bool = false;
    if (paramInt2 >= i)
      return false; 
    paramCharSequence = paramCharSequence.subSequence(paramInt1, paramInt2);
    if (TextUtils.equals(paramCharSequence, "\t") || TextUtils.equals(paramCharSequence, " ") || FitTextHelper.sSpcaeList.contains(paramCharSequence))
      bool = true; 
    return bool;
  }
  
  public boolean isItalicText() {
    boolean bool;
    if (getPaint().getTextSkewX() != 0.0F) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isJustify() {
    return this.mJustify;
  }
  
  public boolean isKeepWord() {
    return this.mKeepWord;
  }
  
  public boolean isLineEndNoSpace() {
    return this.mLineEndNoSpace;
  }
  
  public boolean isSingleLine() {
    return this.mSingleLine;
  }
  
  protected boolean needScale(CharSequence paramCharSequence) {
    return TextUtils.equals(paramCharSequence, " ");
  }
  
  protected void onDraw(Canvas paramCanvas) {
    StaticLayout staticLayout1;
    if (!this.mJustify || this.mSingleLine) {
      super.onDraw(paramCanvas);
      return;
    } 
    TextPaint textPaint = getPaint();
    float f1 = getTextWidth();
    float f2 = f1;
    if (isItalicText())
      f2 = f1 - getPaint().measureText("a"); 
    CharSequence charSequence = getText();
    Layout layout1 = getLayout();
    Layout layout2 = layout1;
    if (layout1 == null)
      staticLayout1 = FitTextHelper.getStaticLayout(this, getText(), getPaint()); 
    int i = staticLayout1.getLineCount();
    int j = 0;
    StaticLayout staticLayout2 = staticLayout1;
    while (j < i) {
      int k = staticLayout2.getLineStart(j);
      int m = staticLayout2.getLineEnd(j);
      float f = staticLayout2.getLineLeft(j);
      int n = staticLayout2.getTopPadding();
      int i1 = j + 1;
      int i2 = n + getLineHeight() * i1;
      CharSequence charSequence1 = charSequence.subSequence(k, m);
      if (charSequence1.length() != 0) {
        if (this.mLineEndNoSpace) {
          CharSequence charSequence2;
          if (TextUtils.equals(charSequence1.subSequence(charSequence1.length() - 1, charSequence1.length()), " ")) {
            charSequence2 = charSequence1.subSequence(0, charSequence1.length() - 1);
          } else {
            charSequence2 = charSequence1;
          } 
          charSequence1 = charSequence2;
          if (TextUtils.equals(charSequence2.subSequence(0, 1), " "))
            charSequence1 = charSequence2.subSequence(1, charSequence2.length() - 1); 
        } 
        n = 1;
        f1 = getPaint().measureText(charSequence, k, m);
        if (j < i - 1 && needScale(charSequence.subSequence(m - 1, m))) {
          j = n;
        } else {
          j = 0;
        } 
        if (j != 0 && f2 > f1) {
          float f3 = (f2 - f1) / countEmpty(charSequence1);
          for (j = 0; j < charSequence1.length(); j = n) {
            TextPaint textPaint1 = getPaint();
            n = j + 1;
            f1 = textPaint1.measureText(charSequence1, j, n);
            paramCanvas.drawText(charSequence1, j, n, f, i2, (Paint)getPaint());
            f += f1;
            f1 = f;
            if (isEmpty(charSequence1, n, j + 2))
              f1 = f + f3 / 2.0F; 
            f = f1;
            if (isEmpty(charSequence1, j, n))
              f = f1 + f3 / 2.0F; 
          } 
        } else {
          paramCanvas.drawText(charSequence1, 0, charSequence1.length(), f, i2, (Paint)textPaint);
        } 
      } 
      j = i1;
    } 
  }
  
  public void setBoldText(boolean paramBoolean) {
    getPaint().setFakeBoldText(paramBoolean);
  }
  
  public void setIncludeFontPadding(boolean paramBoolean) {
    super.setIncludeFontPadding(paramBoolean);
    this.mIncludeFontPadding = paramBoolean;
  }
  
  public void setItalicText(boolean paramBoolean) {
    float f;
    TextPaint textPaint = getPaint();
    if (paramBoolean) {
      f = -0.25F;
    } else {
      f = 0.0F;
    } 
    textPaint.setTextSkewX(f);
  }
  
  public void setJustify(boolean paramBoolean) {
    this.mJustify = paramBoolean;
  }
  
  public void setKeepWord(boolean paramBoolean) {
    this.mKeepWord = paramBoolean;
  }
  
  public void setLineEndNoSpace(boolean paramBoolean) {
    this.mLineEndNoSpace = paramBoolean;
  }
  
  public void setLineSpacing(float paramFloat1, float paramFloat2) {
    super.setLineSpacing(paramFloat1, paramFloat2);
    this.mLineSpacingAdd = paramFloat1;
    this.mLineSpacingMult = paramFloat2;
  }
  
  public void setMaxLines(int paramInt) {
    super.setMaxLines(paramInt);
    this.mMaxLines = paramInt;
  }
  
  public void setSingleLine(boolean paramBoolean) {
    super.setSingleLine(paramBoolean);
    this.mSingleLine = paramBoolean;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\fittext\BaseTextView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */