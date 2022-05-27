package io.virtualapp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import io.virtualapp.R;

public class LabelView extends View {
  private static final int DEFAULT_DEGREES = 45;
  
  private int mBackgroundColor;
  
  private Paint mBackgroundPaint = new Paint(1);
  
  private boolean mFillTriangle;
  
  private int mGravity;
  
  private float mMinSize;
  
  private float mPadding;
  
  private Path mPath = new Path();
  
  private boolean mTextAllCaps;
  
  private boolean mTextBold;
  
  private int mTextColor;
  
  private String mTextContent;
  
  private Paint mTextPaint = new Paint(1);
  
  private float mTextSize;
  
  public LabelView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public LabelView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    obtainAttributes(paramContext, paramAttributeSet);
    this.mTextPaint.setTextAlign(Paint.Align.CENTER);
  }
  
  private void drawText(int paramInt, float paramFloat1, Canvas paramCanvas, float paramFloat2, boolean paramBoolean) {
    String str;
    paramCanvas.save();
    float f = paramInt / 2.0F;
    paramCanvas.rotate(paramFloat1, f, f);
    paramFloat1 = paramFloat2 + this.mPadding * 2.0F;
    if (paramBoolean) {
      paramFloat1 = -paramFloat1 / 2.0F;
    } else {
      paramFloat1 /= 2.0F;
    } 
    f = (paramInt / 2);
    paramFloat2 = (this.mTextPaint.descent() + this.mTextPaint.ascent()) / 2.0F;
    if (this.mTextAllCaps) {
      str = this.mTextContent.toUpperCase();
    } else {
      str = this.mTextContent;
    } 
    paramCanvas.drawText(str, (getPaddingLeft() + (paramInt - getPaddingLeft() - getPaddingRight()) / 2), f - paramFloat2 + paramFloat1, this.mTextPaint);
    paramCanvas.restore();
  }
  
  private void drawTextWhenFill(int paramInt, float paramFloat, Canvas paramCanvas, boolean paramBoolean) {
    int i;
    String str;
    paramCanvas.save();
    float f1 = paramInt / 2.0F;
    paramCanvas.rotate(paramFloat, f1, f1);
    if (paramBoolean) {
      i = -paramInt / 4;
    } else {
      i = paramInt / 4;
    } 
    paramFloat = i;
    f1 = (paramInt / 2);
    float f2 = (this.mTextPaint.descent() + this.mTextPaint.ascent()) / 2.0F;
    if (this.mTextAllCaps) {
      str = this.mTextContent.toUpperCase();
    } else {
      str = this.mTextContent;
    } 
    paramCanvas.drawText(str, (getPaddingLeft() + (paramInt - getPaddingLeft() - getPaddingRight()) / 2), f1 - f2 + paramFloat, this.mTextPaint);
    paramCanvas.restore();
  }
  
  private int measureWidth(int paramInt) {
    int i = View.MeasureSpec.getMode(paramInt);
    int j = View.MeasureSpec.getSize(paramInt);
    if (i == 1073741824) {
      paramInt = j;
    } else {
      paramInt = getPaddingLeft();
      int k = getPaddingRight();
      this.mTextPaint.setColor(this.mTextColor);
      this.mTextPaint.setTextSize(this.mTextSize);
      Paint paint = this.mTextPaint;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mTextContent);
      stringBuilder.append("");
      k = (int)((paramInt + k + (int)paint.measureText(stringBuilder.toString())) * Math.sqrt(2.0D));
      paramInt = k;
      if (i == Integer.MIN_VALUE)
        paramInt = Math.min(k, j); 
      paramInt = Math.max((int)this.mMinSize, paramInt);
    } 
    return paramInt;
  }
  
  private void obtainAttributes(Context paramContext, AttributeSet paramAttributeSet) {
    float f;
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.LabelView);
    this.mTextContent = typedArray.getString(5);
    this.mTextColor = typedArray.getColor(8, Color.parseColor("#ffffff"));
    this.mTextSize = typedArray.getDimension(9, sp2px(11.0F));
    this.mTextBold = typedArray.getBoolean(7, true);
    this.mTextAllCaps = typedArray.getBoolean(6, true);
    this.mFillTriangle = typedArray.getBoolean(1, false);
    this.mBackgroundColor = typedArray.getColor(0, Color.parseColor("#FF4081"));
    if (this.mFillTriangle) {
      f = 35.0F;
    } else {
      f = 50.0F;
    } 
    this.mMinSize = typedArray.getDimension(3, dp2px(f));
    this.mPadding = typedArray.getDimension(4, dp2px(3.5F));
    this.mGravity = typedArray.getInt(2, 51);
    typedArray.recycle();
  }
  
  protected int dp2px(float paramFloat) {
    return (int)(paramFloat * (getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  public int getBgColor() {
    return this.mBackgroundColor;
  }
  
  public int getGravity() {
    return this.mGravity;
  }
  
  public float getMinSize() {
    return this.mMinSize;
  }
  
  public float getPadding() {
    return this.mPadding;
  }
  
  public String getText() {
    return this.mTextContent;
  }
  
  public int getTextColor() {
    return this.mTextColor;
  }
  
  public float getTextSize() {
    return this.mTextSize;
  }
  
  public boolean isFillTriangle() {
    return this.mFillTriangle;
  }
  
  public boolean isTextAllCaps() {
    return this.mTextAllCaps;
  }
  
  public boolean isTextBold() {
    return this.mTextBold;
  }
  
  protected void onDraw(Canvas paramCanvas) {
    int i = getHeight();
    this.mTextPaint.setColor(this.mTextColor);
    this.mTextPaint.setTextSize(this.mTextSize);
    this.mTextPaint.setFakeBoldText(this.mTextBold);
    this.mBackgroundPaint.setColor(this.mBackgroundColor);
    float f = this.mTextPaint.descent() - this.mTextPaint.ascent();
    if (this.mFillTriangle) {
      int j = this.mGravity;
      if (j == 51) {
        this.mPath.reset();
        this.mPath.moveTo(0.0F, 0.0F);
        Path path = this.mPath;
        f = i;
        path.lineTo(0.0F, f);
        this.mPath.lineTo(f, 0.0F);
        this.mPath.close();
        paramCanvas.drawPath(this.mPath, this.mBackgroundPaint);
        drawTextWhenFill(i, -45.0F, paramCanvas, true);
      } else if (j == 53) {
        this.mPath.reset();
        Path path = this.mPath;
        f = i;
        path.moveTo(f, 0.0F);
        this.mPath.lineTo(0.0F, 0.0F);
        this.mPath.lineTo(f, f);
        this.mPath.close();
        paramCanvas.drawPath(this.mPath, this.mBackgroundPaint);
        drawTextWhenFill(i, 45.0F, paramCanvas, true);
      } else if (j == 83) {
        this.mPath.reset();
        Path path = this.mPath;
        f = i;
        path.moveTo(0.0F, f);
        this.mPath.lineTo(0.0F, 0.0F);
        this.mPath.lineTo(f, f);
        this.mPath.close();
        paramCanvas.drawPath(this.mPath, this.mBackgroundPaint);
        drawTextWhenFill(i, 45.0F, paramCanvas, false);
      } else if (j == 85) {
        this.mPath.reset();
        Path path = this.mPath;
        f = i;
        path.moveTo(f, f);
        this.mPath.lineTo(0.0F, f);
        this.mPath.lineTo(f, 0.0F);
        this.mPath.close();
        paramCanvas.drawPath(this.mPath, this.mBackgroundPaint);
        drawTextWhenFill(i, -45.0F, paramCanvas, false);
      } 
    } else {
      double d = (this.mPadding * 2.0F + f) * Math.sqrt(2.0D);
      int j = this.mGravity;
      if (j == 51) {
        this.mPath.reset();
        Path path = this.mPath;
        float f1 = (float)(i - d);
        path.moveTo(0.0F, f1);
        path = this.mPath;
        float f2 = i;
        path.lineTo(0.0F, f2);
        this.mPath.lineTo(f2, 0.0F);
        this.mPath.lineTo(f1, 0.0F);
        this.mPath.close();
        paramCanvas.drawPath(this.mPath, this.mBackgroundPaint);
        drawText(i, -45.0F, paramCanvas, f, true);
      } else if (j == 53) {
        this.mPath.reset();
        this.mPath.moveTo(0.0F, 0.0F);
        this.mPath.lineTo((float)d, 0.0F);
        Path path = this.mPath;
        float f1 = i;
        path.lineTo(f1, (float)(i - d));
        this.mPath.lineTo(f1, f1);
        this.mPath.close();
        paramCanvas.drawPath(this.mPath, this.mBackgroundPaint);
        drawText(i, 45.0F, paramCanvas, f, true);
      } else if (j == 83) {
        this.mPath.reset();
        this.mPath.moveTo(0.0F, 0.0F);
        this.mPath.lineTo(0.0F, (float)d);
        Path path = this.mPath;
        float f2 = (float)(i - d);
        float f1 = i;
        path.lineTo(f2, f1);
        this.mPath.lineTo(f1, f1);
        this.mPath.close();
        paramCanvas.drawPath(this.mPath, this.mBackgroundPaint);
        drawText(i, 45.0F, paramCanvas, f, false);
      } else if (j == 85) {
        this.mPath.reset();
        Path path = this.mPath;
        float f2 = i;
        path.moveTo(0.0F, f2);
        path = this.mPath;
        float f1 = (float)d;
        path.lineTo(f1, f2);
        this.mPath.lineTo(f2, f1);
        this.mPath.lineTo(f2, 0.0F);
        this.mPath.close();
        paramCanvas.drawPath(this.mPath, this.mBackgroundPaint);
        drawText(i, -45.0F, paramCanvas, f, false);
      } 
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    paramInt1 = measureWidth(paramInt1);
    setMeasuredDimension(paramInt1, paramInt1);
  }
  
  public void setBgColor(int paramInt) {
    this.mBackgroundColor = paramInt;
    invalidate();
  }
  
  public void setFillTriangle(boolean paramBoolean) {
    this.mFillTriangle = paramBoolean;
    invalidate();
  }
  
  public void setGravity(int paramInt) {
    this.mGravity = paramInt;
  }
  
  public void setMinSize(float paramFloat) {
    this.mMinSize = dp2px(paramFloat);
    invalidate();
  }
  
  public void setPadding(float paramFloat) {
    this.mPadding = dp2px(paramFloat);
    invalidate();
  }
  
  public void setText(String paramString) {
    this.mTextContent = paramString;
    invalidate();
  }
  
  public void setTextAllCaps(boolean paramBoolean) {
    this.mTextAllCaps = paramBoolean;
    invalidate();
  }
  
  public void setTextBold(boolean paramBoolean) {
    this.mTextBold = paramBoolean;
    invalidate();
  }
  
  public void setTextColor(int paramInt) {
    this.mTextColor = paramInt;
    invalidate();
  }
  
  public void setTextSize(float paramFloat) {
    this.mTextSize = sp2px(paramFloat);
    invalidate();
  }
  
  protected int sp2px(float paramFloat) {
    return (int)(paramFloat * (getResources().getDisplayMetrics()).scaledDensity + 0.5F);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\LabelView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */