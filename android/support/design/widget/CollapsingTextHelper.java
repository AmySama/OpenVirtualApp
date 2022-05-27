package android.support.design.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.math.MathUtils;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v4.text.TextDirectionHeuristicsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.TintTypedArray;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Interpolator;

final class CollapsingTextHelper {
  private static final boolean DEBUG_DRAW = false;
  
  private static final Paint DEBUG_DRAW_PAINT;
  
  private static final boolean USE_SCALING_TEXTURE;
  
  private boolean mBoundsChanged;
  
  private final Rect mCollapsedBounds;
  
  private float mCollapsedDrawX;
  
  private float mCollapsedDrawY;
  
  private int mCollapsedShadowColor;
  
  private float mCollapsedShadowDx;
  
  private float mCollapsedShadowDy;
  
  private float mCollapsedShadowRadius;
  
  private ColorStateList mCollapsedTextColor;
  
  private int mCollapsedTextGravity = 16;
  
  private float mCollapsedTextSize = 15.0F;
  
  private Typeface mCollapsedTypeface;
  
  private final RectF mCurrentBounds;
  
  private float mCurrentDrawX;
  
  private float mCurrentDrawY;
  
  private float mCurrentTextSize;
  
  private Typeface mCurrentTypeface;
  
  private boolean mDrawTitle;
  
  private final Rect mExpandedBounds;
  
  private float mExpandedDrawX;
  
  private float mExpandedDrawY;
  
  private float mExpandedFraction;
  
  private int mExpandedShadowColor;
  
  private float mExpandedShadowDx;
  
  private float mExpandedShadowDy;
  
  private float mExpandedShadowRadius;
  
  private ColorStateList mExpandedTextColor;
  
  private int mExpandedTextGravity = 16;
  
  private float mExpandedTextSize = 15.0F;
  
  private Bitmap mExpandedTitleTexture;
  
  private Typeface mExpandedTypeface;
  
  private boolean mIsRtl;
  
  private Interpolator mPositionInterpolator;
  
  private float mScale;
  
  private int[] mState;
  
  private CharSequence mText;
  
  private final TextPaint mTextPaint;
  
  private Interpolator mTextSizeInterpolator;
  
  private CharSequence mTextToDraw;
  
  private float mTextureAscent;
  
  private float mTextureDescent;
  
  private Paint mTexturePaint;
  
  private boolean mUseTexture;
  
  private final View mView;
  
  static {
    boolean bool;
    if (Build.VERSION.SDK_INT < 18) {
      bool = true;
    } else {
      bool = false;
    } 
    USE_SCALING_TEXTURE = bool;
    DEBUG_DRAW_PAINT = null;
    if (false)
      throw new NullPointerException(); 
  }
  
  public CollapsingTextHelper(View paramView) {
    this.mView = paramView;
    this.mTextPaint = new TextPaint(129);
    this.mCollapsedBounds = new Rect();
    this.mExpandedBounds = new Rect();
    this.mCurrentBounds = new RectF();
  }
  
  private boolean areTypefacesDifferent(Typeface paramTypeface1, Typeface paramTypeface2) {
    boolean bool;
    if ((paramTypeface1 != null && !paramTypeface1.equals(paramTypeface2)) || (paramTypeface1 == null && paramTypeface2 != null)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private static int blendColors(int paramInt1, int paramInt2, float paramFloat) {
    float f1 = 1.0F - paramFloat;
    float f2 = Color.alpha(paramInt1);
    float f3 = Color.alpha(paramInt2);
    float f4 = Color.red(paramInt1);
    float f5 = Color.red(paramInt2);
    float f6 = Color.green(paramInt1);
    float f7 = Color.green(paramInt2);
    float f8 = Color.blue(paramInt1);
    float f9 = Color.blue(paramInt2);
    return Color.argb((int)(f2 * f1 + f3 * paramFloat), (int)(f4 * f1 + f5 * paramFloat), (int)(f6 * f1 + f7 * paramFloat), (int)(f8 * f1 + f9 * paramFloat));
  }
  
  private void calculateBaseOffsets() {
    float f1 = this.mCurrentTextSize;
    calculateUsingTextSize(this.mCollapsedTextSize);
    CharSequence charSequence = this.mTextToDraw;
    float f2 = 0.0F;
    if (charSequence != null) {
      f3 = this.mTextPaint.measureText(charSequence, 0, charSequence.length());
    } else {
      f3 = 0.0F;
    } 
    int i = GravityCompat.getAbsoluteGravity(this.mCollapsedTextGravity, this.mIsRtl);
    int j = i & 0x70;
    if (j != 48) {
      if (j != 80) {
        float f4 = (this.mTextPaint.descent() - this.mTextPaint.ascent()) / 2.0F;
        float f5 = this.mTextPaint.descent();
        this.mCollapsedDrawY = this.mCollapsedBounds.centerY() + f4 - f5;
      } else {
        this.mCollapsedDrawY = this.mCollapsedBounds.bottom;
      } 
    } else {
      this.mCollapsedDrawY = this.mCollapsedBounds.top - this.mTextPaint.ascent();
    } 
    i &= 0x800007;
    if (i != 1) {
      if (i != 5) {
        this.mCollapsedDrawX = this.mCollapsedBounds.left;
      } else {
        this.mCollapsedDrawX = this.mCollapsedBounds.right - f3;
      } 
    } else {
      this.mCollapsedDrawX = this.mCollapsedBounds.centerX() - f3 / 2.0F;
    } 
    calculateUsingTextSize(this.mExpandedTextSize);
    charSequence = this.mTextToDraw;
    float f3 = f2;
    if (charSequence != null)
      f3 = this.mTextPaint.measureText(charSequence, 0, charSequence.length()); 
    i = GravityCompat.getAbsoluteGravity(this.mExpandedTextGravity, this.mIsRtl);
    j = i & 0x70;
    if (j != 48) {
      if (j != 80) {
        f2 = (this.mTextPaint.descent() - this.mTextPaint.ascent()) / 2.0F;
        float f = this.mTextPaint.descent();
        this.mExpandedDrawY = this.mExpandedBounds.centerY() + f2 - f;
      } else {
        this.mExpandedDrawY = this.mExpandedBounds.bottom;
      } 
    } else {
      this.mExpandedDrawY = this.mExpandedBounds.top - this.mTextPaint.ascent();
    } 
    i &= 0x800007;
    if (i != 1) {
      if (i != 5) {
        this.mExpandedDrawX = this.mExpandedBounds.left;
      } else {
        this.mExpandedDrawX = this.mExpandedBounds.right - f3;
      } 
    } else {
      this.mExpandedDrawX = this.mExpandedBounds.centerX() - f3 / 2.0F;
    } 
    clearTexture();
    setInterpolatedTextSize(f1);
  }
  
  private void calculateCurrentOffsets() {
    calculateOffsets(this.mExpandedFraction);
  }
  
  private boolean calculateIsRtl(CharSequence paramCharSequence) {
    TextDirectionHeuristicCompat textDirectionHeuristicCompat;
    int i = ViewCompat.getLayoutDirection(this.mView);
    boolean bool = true;
    if (i != 1)
      bool = false; 
    if (bool) {
      textDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL;
    } else {
      textDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
    } 
    return textDirectionHeuristicCompat.isRtl(paramCharSequence, 0, paramCharSequence.length());
  }
  
  private void calculateOffsets(float paramFloat) {
    interpolateBounds(paramFloat);
    this.mCurrentDrawX = lerp(this.mExpandedDrawX, this.mCollapsedDrawX, paramFloat, this.mPositionInterpolator);
    this.mCurrentDrawY = lerp(this.mExpandedDrawY, this.mCollapsedDrawY, paramFloat, this.mPositionInterpolator);
    setInterpolatedTextSize(lerp(this.mExpandedTextSize, this.mCollapsedTextSize, paramFloat, this.mTextSizeInterpolator));
    if (this.mCollapsedTextColor != this.mExpandedTextColor) {
      this.mTextPaint.setColor(blendColors(getCurrentExpandedTextColor(), getCurrentCollapsedTextColor(), paramFloat));
    } else {
      this.mTextPaint.setColor(getCurrentCollapsedTextColor());
    } 
    this.mTextPaint.setShadowLayer(lerp(this.mExpandedShadowRadius, this.mCollapsedShadowRadius, paramFloat, null), lerp(this.mExpandedShadowDx, this.mCollapsedShadowDx, paramFloat, null), lerp(this.mExpandedShadowDy, this.mCollapsedShadowDy, paramFloat, null), blendColors(this.mExpandedShadowColor, this.mCollapsedShadowColor, paramFloat));
    ViewCompat.postInvalidateOnAnimation(this.mView);
  }
  
  private void calculateUsingTextSize(float paramFloat) {
    float f3;
    boolean bool2;
    if (this.mText == null)
      return; 
    float f1 = this.mCollapsedBounds.width();
    float f2 = this.mExpandedBounds.width();
    boolean bool = isClose(paramFloat, this.mCollapsedTextSize);
    boolean bool1 = true;
    if (bool) {
      f3 = this.mCollapsedTextSize;
      this.mScale = 1.0F;
      if (areTypefacesDifferent(this.mCurrentTypeface, this.mCollapsedTypeface)) {
        this.mCurrentTypeface = this.mCollapsedTypeface;
        bool2 = true;
        paramFloat = f1;
      } else {
        bool2 = false;
        paramFloat = f1;
      } 
    } else {
      f3 = this.mExpandedTextSize;
      if (areTypefacesDifferent(this.mCurrentTypeface, this.mExpandedTypeface)) {
        this.mCurrentTypeface = this.mExpandedTypeface;
        bool2 = true;
      } else {
        bool2 = false;
      } 
      if (isClose(paramFloat, this.mExpandedTextSize)) {
        this.mScale = 1.0F;
      } else {
        this.mScale = paramFloat / this.mExpandedTextSize;
      } 
      paramFloat = this.mCollapsedTextSize / this.mExpandedTextSize;
      if (f2 * paramFloat > f1) {
        paramFloat = Math.min(f1 / paramFloat, f2);
      } else {
        paramFloat = f2;
      } 
    } 
    boolean bool3 = bool2;
    if (paramFloat > 0.0F) {
      if (this.mCurrentTextSize != f3 || this.mBoundsChanged || bool2) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      this.mCurrentTextSize = f3;
      this.mBoundsChanged = false;
      bool3 = bool2;
    } 
    if (this.mTextToDraw == null || bool3) {
      this.mTextPaint.setTextSize(this.mCurrentTextSize);
      this.mTextPaint.setTypeface(this.mCurrentTypeface);
      TextPaint textPaint = this.mTextPaint;
      if (this.mScale == 1.0F)
        bool1 = false; 
      textPaint.setLinearText(bool1);
      CharSequence charSequence = TextUtils.ellipsize(this.mText, this.mTextPaint, paramFloat, TextUtils.TruncateAt.END);
      if (!TextUtils.equals(charSequence, this.mTextToDraw)) {
        this.mTextToDraw = charSequence;
        this.mIsRtl = calculateIsRtl(charSequence);
      } 
    } 
  }
  
  private void clearTexture() {
    Bitmap bitmap = this.mExpandedTitleTexture;
    if (bitmap != null) {
      bitmap.recycle();
      this.mExpandedTitleTexture = null;
    } 
  }
  
  private void ensureExpandedTexture() {
    if (this.mExpandedTitleTexture == null && !this.mExpandedBounds.isEmpty() && !TextUtils.isEmpty(this.mTextToDraw)) {
      calculateOffsets(0.0F);
      this.mTextureAscent = this.mTextPaint.ascent();
      this.mTextureDescent = this.mTextPaint.descent();
      TextPaint textPaint = this.mTextPaint;
      CharSequence charSequence = this.mTextToDraw;
      int i = Math.round(textPaint.measureText(charSequence, 0, charSequence.length()));
      int j = Math.round(this.mTextureDescent - this.mTextureAscent);
      if (i > 0 && j > 0) {
        this.mExpandedTitleTexture = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(this.mExpandedTitleTexture);
        charSequence = this.mTextToDraw;
        canvas.drawText(charSequence, 0, charSequence.length(), 0.0F, j - this.mTextPaint.descent(), (Paint)this.mTextPaint);
        if (this.mTexturePaint == null)
          this.mTexturePaint = new Paint(3); 
      } 
    } 
  }
  
  private int getCurrentCollapsedTextColor() {
    int[] arrayOfInt = this.mState;
    return (arrayOfInt != null) ? this.mCollapsedTextColor.getColorForState(arrayOfInt, 0) : this.mCollapsedTextColor.getDefaultColor();
  }
  
  private int getCurrentExpandedTextColor() {
    int[] arrayOfInt = this.mState;
    return (arrayOfInt != null) ? this.mExpandedTextColor.getColorForState(arrayOfInt, 0) : this.mExpandedTextColor.getDefaultColor();
  }
  
  private void interpolateBounds(float paramFloat) {
    this.mCurrentBounds.left = lerp(this.mExpandedBounds.left, this.mCollapsedBounds.left, paramFloat, this.mPositionInterpolator);
    this.mCurrentBounds.top = lerp(this.mExpandedDrawY, this.mCollapsedDrawY, paramFloat, this.mPositionInterpolator);
    this.mCurrentBounds.right = lerp(this.mExpandedBounds.right, this.mCollapsedBounds.right, paramFloat, this.mPositionInterpolator);
    this.mCurrentBounds.bottom = lerp(this.mExpandedBounds.bottom, this.mCollapsedBounds.bottom, paramFloat, this.mPositionInterpolator);
  }
  
  private static boolean isClose(float paramFloat1, float paramFloat2) {
    boolean bool;
    if (Math.abs(paramFloat1 - paramFloat2) < 0.001F) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private static float lerp(float paramFloat1, float paramFloat2, float paramFloat3, Interpolator paramInterpolator) {
    float f = paramFloat3;
    if (paramInterpolator != null)
      f = paramInterpolator.getInterpolation(paramFloat3); 
    return AnimationUtils.lerp(paramFloat1, paramFloat2, f);
  }
  
  private Typeface readFontFamilyTypeface(int paramInt) {
    TypedArray typedArray = this.mView.getContext().obtainStyledAttributes(paramInt, new int[] { 16843692 });
    try {
      String str = typedArray.getString(0);
      if (str != null)
        return Typeface.create(str, 0); 
      return null;
    } finally {
      typedArray.recycle();
    } 
  }
  
  private static boolean rectEquals(Rect paramRect, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    boolean bool;
    if (paramRect.left == paramInt1 && paramRect.top == paramInt2 && paramRect.right == paramInt3 && paramRect.bottom == paramInt4) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private void setInterpolatedTextSize(float paramFloat) {
    boolean bool;
    calculateUsingTextSize(paramFloat);
    if (USE_SCALING_TEXTURE && this.mScale != 1.0F) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mUseTexture = bool;
    if (bool)
      ensureExpandedTexture(); 
    ViewCompat.postInvalidateOnAnimation(this.mView);
  }
  
  public void draw(Canvas paramCanvas) {
    int i = paramCanvas.save();
    if (this.mTextToDraw != null && this.mDrawTitle) {
      boolean bool;
      float f1 = this.mCurrentDrawX;
      float f2 = this.mCurrentDrawY;
      if (this.mUseTexture && this.mExpandedTitleTexture != null) {
        bool = true;
      } else {
        bool = false;
      } 
      if (bool) {
        f3 = this.mTextureAscent * this.mScale;
      } else {
        f3 = this.mTextPaint.ascent() * this.mScale;
        this.mTextPaint.descent();
      } 
      float f4 = f2;
      if (bool)
        f4 = f2 + f3; 
      float f3 = this.mScale;
      if (f3 != 1.0F)
        paramCanvas.scale(f3, f3, f1, f4); 
      if (bool) {
        paramCanvas.drawBitmap(this.mExpandedTitleTexture, f1, f4, this.mTexturePaint);
      } else {
        CharSequence charSequence = this.mTextToDraw;
        paramCanvas.drawText(charSequence, 0, charSequence.length(), f1, f4, (Paint)this.mTextPaint);
      } 
    } 
    paramCanvas.restoreToCount(i);
  }
  
  ColorStateList getCollapsedTextColor() {
    return this.mCollapsedTextColor;
  }
  
  int getCollapsedTextGravity() {
    return this.mCollapsedTextGravity;
  }
  
  float getCollapsedTextSize() {
    return this.mCollapsedTextSize;
  }
  
  Typeface getCollapsedTypeface() {
    Typeface typeface = this.mCollapsedTypeface;
    if (typeface == null)
      typeface = Typeface.DEFAULT; 
    return typeface;
  }
  
  ColorStateList getExpandedTextColor() {
    return this.mExpandedTextColor;
  }
  
  int getExpandedTextGravity() {
    return this.mExpandedTextGravity;
  }
  
  float getExpandedTextSize() {
    return this.mExpandedTextSize;
  }
  
  Typeface getExpandedTypeface() {
    Typeface typeface = this.mExpandedTypeface;
    if (typeface == null)
      typeface = Typeface.DEFAULT; 
    return typeface;
  }
  
  float getExpansionFraction() {
    return this.mExpandedFraction;
  }
  
  CharSequence getText() {
    return this.mText;
  }
  
  final boolean isStateful() {
    ColorStateList colorStateList = this.mCollapsedTextColor;
    if (colorStateList == null || !colorStateList.isStateful()) {
      colorStateList = this.mExpandedTextColor;
      return (colorStateList != null && colorStateList.isStateful());
    } 
    return true;
  }
  
  void onBoundsChanged() {
    boolean bool;
    if (this.mCollapsedBounds.width() > 0 && this.mCollapsedBounds.height() > 0 && this.mExpandedBounds.width() > 0 && this.mExpandedBounds.height() > 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mDrawTitle = bool;
  }
  
  public void recalculate() {
    if (this.mView.getHeight() > 0 && this.mView.getWidth() > 0) {
      calculateBaseOffsets();
      calculateCurrentOffsets();
    } 
  }
  
  void setCollapsedBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (!rectEquals(this.mCollapsedBounds, paramInt1, paramInt2, paramInt3, paramInt4)) {
      this.mCollapsedBounds.set(paramInt1, paramInt2, paramInt3, paramInt4);
      this.mBoundsChanged = true;
      onBoundsChanged();
    } 
  }
  
  void setCollapsedTextAppearance(int paramInt) {
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), paramInt, R.styleable.TextAppearance);
    if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_textColor))
      this.mCollapsedTextColor = tintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColor); 
    if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_textSize))
      this.mCollapsedTextSize = tintTypedArray.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, (int)this.mCollapsedTextSize); 
    this.mCollapsedShadowColor = tintTypedArray.getInt(R.styleable.TextAppearance_android_shadowColor, 0);
    this.mCollapsedShadowDx = tintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0F);
    this.mCollapsedShadowDy = tintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0F);
    this.mCollapsedShadowRadius = tintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0F);
    tintTypedArray.recycle();
    if (Build.VERSION.SDK_INT >= 16)
      this.mCollapsedTypeface = readFontFamilyTypeface(paramInt); 
    recalculate();
  }
  
  void setCollapsedTextColor(ColorStateList paramColorStateList) {
    if (this.mCollapsedTextColor != paramColorStateList) {
      this.mCollapsedTextColor = paramColorStateList;
      recalculate();
    } 
  }
  
  void setCollapsedTextGravity(int paramInt) {
    if (this.mCollapsedTextGravity != paramInt) {
      this.mCollapsedTextGravity = paramInt;
      recalculate();
    } 
  }
  
  void setCollapsedTextSize(float paramFloat) {
    if (this.mCollapsedTextSize != paramFloat) {
      this.mCollapsedTextSize = paramFloat;
      recalculate();
    } 
  }
  
  void setCollapsedTypeface(Typeface paramTypeface) {
    if (areTypefacesDifferent(this.mCollapsedTypeface, paramTypeface)) {
      this.mCollapsedTypeface = paramTypeface;
      recalculate();
    } 
  }
  
  void setExpandedBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (!rectEquals(this.mExpandedBounds, paramInt1, paramInt2, paramInt3, paramInt4)) {
      this.mExpandedBounds.set(paramInt1, paramInt2, paramInt3, paramInt4);
      this.mBoundsChanged = true;
      onBoundsChanged();
    } 
  }
  
  void setExpandedTextAppearance(int paramInt) {
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), paramInt, R.styleable.TextAppearance);
    if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_textColor))
      this.mExpandedTextColor = tintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColor); 
    if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_textSize))
      this.mExpandedTextSize = tintTypedArray.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, (int)this.mExpandedTextSize); 
    this.mExpandedShadowColor = tintTypedArray.getInt(R.styleable.TextAppearance_android_shadowColor, 0);
    this.mExpandedShadowDx = tintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0F);
    this.mExpandedShadowDy = tintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0F);
    this.mExpandedShadowRadius = tintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0F);
    tintTypedArray.recycle();
    if (Build.VERSION.SDK_INT >= 16)
      this.mExpandedTypeface = readFontFamilyTypeface(paramInt); 
    recalculate();
  }
  
  void setExpandedTextColor(ColorStateList paramColorStateList) {
    if (this.mExpandedTextColor != paramColorStateList) {
      this.mExpandedTextColor = paramColorStateList;
      recalculate();
    } 
  }
  
  void setExpandedTextGravity(int paramInt) {
    if (this.mExpandedTextGravity != paramInt) {
      this.mExpandedTextGravity = paramInt;
      recalculate();
    } 
  }
  
  void setExpandedTextSize(float paramFloat) {
    if (this.mExpandedTextSize != paramFloat) {
      this.mExpandedTextSize = paramFloat;
      recalculate();
    } 
  }
  
  void setExpandedTypeface(Typeface paramTypeface) {
    if (areTypefacesDifferent(this.mExpandedTypeface, paramTypeface)) {
      this.mExpandedTypeface = paramTypeface;
      recalculate();
    } 
  }
  
  void setExpansionFraction(float paramFloat) {
    paramFloat = MathUtils.clamp(paramFloat, 0.0F, 1.0F);
    if (paramFloat != this.mExpandedFraction) {
      this.mExpandedFraction = paramFloat;
      calculateCurrentOffsets();
    } 
  }
  
  void setPositionInterpolator(Interpolator paramInterpolator) {
    this.mPositionInterpolator = paramInterpolator;
    recalculate();
  }
  
  final boolean setState(int[] paramArrayOfint) {
    this.mState = paramArrayOfint;
    if (isStateful()) {
      recalculate();
      return true;
    } 
    return false;
  }
  
  void setText(CharSequence paramCharSequence) {
    if (paramCharSequence == null || !paramCharSequence.equals(this.mText)) {
      this.mText = paramCharSequence;
      this.mTextToDraw = null;
      clearTexture();
      recalculate();
    } 
  }
  
  void setTextSizeInterpolator(Interpolator paramInterpolator) {
    this.mTextSizeInterpolator = paramInterpolator;
    recalculate();
  }
  
  void setTypefaces(Typeface paramTypeface) {
    this.mExpandedTypeface = paramTypeface;
    this.mCollapsedTypeface = paramTypeface;
    recalculate();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\CollapsingTextHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */