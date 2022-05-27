package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.AutoSizeableTextView;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.widget.TextView;
import java.lang.ref.WeakReference;

class AppCompatTextHelper {
  private static final int MONOSPACE = 3;
  
  private static final int SANS = 1;
  
  private static final int SERIF = 2;
  
  private boolean mAsyncFontPending;
  
  private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
  
  private TintInfo mDrawableBottomTint;
  
  private TintInfo mDrawableLeftTint;
  
  private TintInfo mDrawableRightTint;
  
  private TintInfo mDrawableTopTint;
  
  private Typeface mFontTypeface;
  
  private int mStyle = 0;
  
  final TextView mView;
  
  AppCompatTextHelper(TextView paramTextView) {
    this.mView = paramTextView;
    this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(this.mView);
  }
  
  static AppCompatTextHelper create(TextView paramTextView) {
    return (Build.VERSION.SDK_INT >= 17) ? new AppCompatTextHelperV17(paramTextView) : new AppCompatTextHelper(paramTextView);
  }
  
  protected static TintInfo createTintInfo(Context paramContext, AppCompatDrawableManager paramAppCompatDrawableManager, int paramInt) {
    ColorStateList colorStateList = paramAppCompatDrawableManager.getTintList(paramContext, paramInt);
    if (colorStateList != null) {
      TintInfo tintInfo = new TintInfo();
      tintInfo.mHasTintList = true;
      tintInfo.mTintList = colorStateList;
      return tintInfo;
    } 
    return null;
  }
  
  private void onAsyncTypefaceReceived(WeakReference<TextView> paramWeakReference, Typeface paramTypeface) {
    if (this.mAsyncFontPending) {
      this.mFontTypeface = paramTypeface;
      TextView textView = paramWeakReference.get();
      if (textView != null)
        textView.setTypeface(paramTypeface, this.mStyle); 
    } 
  }
  
  private void setTextSizeInternal(int paramInt, float paramFloat) {
    this.mAutoSizeTextHelper.setTextSizeInternal(paramInt, paramFloat);
  }
  
  private void updateTypefaceAndStyle(Context paramContext, TintTypedArray paramTintTypedArray) {
    this.mStyle = paramTintTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, this.mStyle);
    boolean bool = paramTintTypedArray.hasValue(R.styleable.TextAppearance_android_fontFamily);
    boolean bool1 = false;
    if (bool || paramTintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily)) {
      int i;
      this.mFontTypeface = null;
      if (paramTintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily)) {
        i = R.styleable.TextAppearance_fontFamily;
      } else {
        i = R.styleable.TextAppearance_android_fontFamily;
      } 
      if (!paramContext.isRestricted()) {
        ResourcesCompat.FontCallback fontCallback = new ResourcesCompat.FontCallback() {
            public void onFontRetrievalFailed(int param1Int) {}
            
            public void onFontRetrieved(Typeface param1Typeface) {
              AppCompatTextHelper.this.onAsyncTypefaceReceived(textViewWeak, param1Typeface);
            }
          };
        try {
          Typeface typeface = paramTintTypedArray.getFont(i, this.mStyle, fontCallback);
          this.mFontTypeface = typeface;
          if (typeface == null)
            bool1 = true; 
          this.mAsyncFontPending = bool1;
        } catch (UnsupportedOperationException|android.content.res.Resources.NotFoundException unsupportedOperationException) {}
      } 
      if (this.mFontTypeface == null) {
        String str = paramTintTypedArray.getString(i);
        if (str != null)
          this.mFontTypeface = Typeface.create(str, this.mStyle); 
      } 
      return;
    } 
    if (paramTintTypedArray.hasValue(R.styleable.TextAppearance_android_typeface)) {
      this.mAsyncFontPending = false;
      int i = paramTintTypedArray.getInt(R.styleable.TextAppearance_android_typeface, 1);
      if (i != 1) {
        if (i != 2) {
          if (i == 3)
            this.mFontTypeface = Typeface.MONOSPACE; 
        } else {
          this.mFontTypeface = Typeface.SERIF;
        } 
      } else {
        this.mFontTypeface = Typeface.SANS_SERIF;
      } 
    } 
  }
  
  final void applyCompoundDrawableTint(Drawable paramDrawable, TintInfo paramTintInfo) {
    if (paramDrawable != null && paramTintInfo != null)
      AppCompatDrawableManager.tintDrawable(paramDrawable, paramTintInfo, this.mView.getDrawableState()); 
  }
  
  void applyCompoundDrawablesTints() {
    if (this.mDrawableLeftTint != null || this.mDrawableTopTint != null || this.mDrawableRightTint != null || this.mDrawableBottomTint != null) {
      Drawable[] arrayOfDrawable = this.mView.getCompoundDrawables();
      applyCompoundDrawableTint(arrayOfDrawable[0], this.mDrawableLeftTint);
      applyCompoundDrawableTint(arrayOfDrawable[1], this.mDrawableTopTint);
      applyCompoundDrawableTint(arrayOfDrawable[2], this.mDrawableRightTint);
      applyCompoundDrawableTint(arrayOfDrawable[3], this.mDrawableBottomTint);
    } 
  }
  
  void autoSizeText() {
    this.mAutoSizeTextHelper.autoSizeText();
  }
  
  int getAutoSizeMaxTextSize() {
    return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize();
  }
  
  int getAutoSizeMinTextSize() {
    return this.mAutoSizeTextHelper.getAutoSizeMinTextSize();
  }
  
  int getAutoSizeStepGranularity() {
    return this.mAutoSizeTextHelper.getAutoSizeStepGranularity();
  }
  
  int[] getAutoSizeTextAvailableSizes() {
    return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
  }
  
  int getAutoSizeTextType() {
    return this.mAutoSizeTextHelper.getAutoSizeTextType();
  }
  
  boolean isAutoSizeEnabled() {
    return this.mAutoSizeTextHelper.isAutoSizeEnabled();
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt) {
    TintTypedArray tintTypedArray1;
    ColorStateList colorStateList1;
    boolean bool2;
    ColorStateList colorStateList4;
    Context context = this.mView.getContext();
    AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
    TintTypedArray tintTypedArray2 = TintTypedArray.obtainStyledAttributes(context, paramAttributeSet, R.styleable.AppCompatTextHelper, paramInt, 0);
    int i = tintTypedArray2.getResourceId(R.styleable.AppCompatTextHelper_android_textAppearance, -1);
    if (tintTypedArray2.hasValue(R.styleable.AppCompatTextHelper_android_drawableLeft))
      this.mDrawableLeftTint = createTintInfo(context, appCompatDrawableManager, tintTypedArray2.getResourceId(R.styleable.AppCompatTextHelper_android_drawableLeft, 0)); 
    if (tintTypedArray2.hasValue(R.styleable.AppCompatTextHelper_android_drawableTop))
      this.mDrawableTopTint = createTintInfo(context, appCompatDrawableManager, tintTypedArray2.getResourceId(R.styleable.AppCompatTextHelper_android_drawableTop, 0)); 
    if (tintTypedArray2.hasValue(R.styleable.AppCompatTextHelper_android_drawableRight))
      this.mDrawableRightTint = createTintInfo(context, appCompatDrawableManager, tintTypedArray2.getResourceId(R.styleable.AppCompatTextHelper_android_drawableRight, 0)); 
    if (tintTypedArray2.hasValue(R.styleable.AppCompatTextHelper_android_drawableBottom))
      this.mDrawableBottomTint = createTintInfo(context, appCompatDrawableManager, tintTypedArray2.getResourceId(R.styleable.AppCompatTextHelper_android_drawableBottom, 0)); 
    tintTypedArray2.recycle();
    boolean bool1 = this.mView.getTransformationMethod() instanceof android.text.method.PasswordTransformationMethod;
    boolean bool = true;
    appCompatDrawableManager = null;
    TintTypedArray tintTypedArray3 = null;
    ColorStateList colorStateList3 = null;
    if (i != -1) {
      tintTypedArray3 = TintTypedArray.obtainStyledAttributes(context, i, R.styleable.TextAppearance);
      if (!bool1 && tintTypedArray3.hasValue(R.styleable.TextAppearance_textAllCaps)) {
        bool2 = tintTypedArray3.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
        i = 1;
      } else {
        bool2 = false;
        i = 0;
      } 
      updateTypefaceAndStyle(context, tintTypedArray3);
      if (Build.VERSION.SDK_INT < 23) {
        if (tintTypedArray3.hasValue(R.styleable.TextAppearance_android_textColor)) {
          ColorStateList colorStateList = tintTypedArray3.getColorStateList(R.styleable.TextAppearance_android_textColor);
        } else {
          appCompatDrawableManager = null;
        } 
        if (tintTypedArray3.hasValue(R.styleable.TextAppearance_android_textColorHint)) {
          colorStateList1 = tintTypedArray3.getColorStateList(R.styleable.TextAppearance_android_textColorHint);
        } else {
          tintTypedArray2 = null;
        } 
        if (tintTypedArray3.hasValue(R.styleable.TextAppearance_android_textColorLink))
          colorStateList3 = tintTypedArray3.getColorStateList(R.styleable.TextAppearance_android_textColorLink); 
      } else {
        colorStateList3 = null;
        colorStateList1 = colorStateList3;
      } 
      tintTypedArray3.recycle();
    } else {
      colorStateList3 = null;
      colorStateList1 = colorStateList3;
      bool2 = false;
      i = 0;
      tintTypedArray1 = tintTypedArray3;
    } 
    TintTypedArray tintTypedArray4 = TintTypedArray.obtainStyledAttributes(context, paramAttributeSet, R.styleable.TextAppearance, paramInt, 0);
    if (!bool1 && tintTypedArray4.hasValue(R.styleable.TextAppearance_textAllCaps)) {
      bool2 = tintTypedArray4.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
      i = bool;
    } 
    TintTypedArray tintTypedArray5 = tintTypedArray1;
    ColorStateList colorStateList5 = colorStateList3;
    ColorStateList colorStateList2 = colorStateList1;
    if (Build.VERSION.SDK_INT < 23) {
      ColorStateList colorStateList;
      if (tintTypedArray4.hasValue(R.styleable.TextAppearance_android_textColor))
        colorStateList = tintTypedArray4.getColorStateList(R.styleable.TextAppearance_android_textColor); 
      if (tintTypedArray4.hasValue(R.styleable.TextAppearance_android_textColorHint))
        colorStateList1 = tintTypedArray4.getColorStateList(R.styleable.TextAppearance_android_textColorHint); 
      colorStateList4 = colorStateList;
      colorStateList5 = colorStateList3;
      colorStateList2 = colorStateList1;
      if (tintTypedArray4.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
        colorStateList5 = tintTypedArray4.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
        colorStateList2 = colorStateList1;
        colorStateList4 = colorStateList;
      } 
    } 
    updateTypefaceAndStyle(context, tintTypedArray4);
    tintTypedArray4.recycle();
    if (colorStateList4 != null)
      this.mView.setTextColor(colorStateList4); 
    if (colorStateList2 != null)
      this.mView.setHintTextColor(colorStateList2); 
    if (colorStateList5 != null)
      this.mView.setLinkTextColor(colorStateList5); 
    if (!bool1 && i != 0)
      setAllCaps(bool2); 
    Typeface typeface = this.mFontTypeface;
    if (typeface != null)
      this.mView.setTypeface(typeface, this.mStyle); 
    this.mAutoSizeTextHelper.loadFromAttributes(paramAttributeSet, paramInt);
    if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && this.mAutoSizeTextHelper.getAutoSizeTextType() != 0) {
      int[] arrayOfInt = this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
      if (arrayOfInt.length > 0)
        if (this.mView.getAutoSizeStepGranularity() != -1.0F) {
          this.mView.setAutoSizeTextTypeUniformWithConfiguration(this.mAutoSizeTextHelper.getAutoSizeMinTextSize(), this.mAutoSizeTextHelper.getAutoSizeMaxTextSize(), this.mAutoSizeTextHelper.getAutoSizeStepGranularity(), 0);
        } else {
          this.mView.setAutoSizeTextTypeUniformWithPresetSizes(arrayOfInt, 0);
        }  
    } 
  }
  
  void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE)
      autoSizeText(); 
  }
  
  void onSetTextAppearance(Context paramContext, int paramInt) {
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramInt, R.styleable.TextAppearance);
    if (tintTypedArray.hasValue(R.styleable.TextAppearance_textAllCaps))
      setAllCaps(tintTypedArray.getBoolean(R.styleable.TextAppearance_textAllCaps, false)); 
    if (Build.VERSION.SDK_INT < 23 && tintTypedArray.hasValue(R.styleable.TextAppearance_android_textColor)) {
      ColorStateList colorStateList = tintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColor);
      if (colorStateList != null)
        this.mView.setTextColor(colorStateList); 
    } 
    updateTypefaceAndStyle(paramContext, tintTypedArray);
    tintTypedArray.recycle();
    Typeface typeface = this.mFontTypeface;
    if (typeface != null)
      this.mView.setTypeface(typeface, this.mStyle); 
  }
  
  void setAllCaps(boolean paramBoolean) {
    this.mView.setAllCaps(paramBoolean);
  }
  
  void setAutoSizeTextTypeUniformWithConfiguration(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IllegalArgumentException {
    this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  void setAutoSizeTextTypeUniformWithPresetSizes(int[] paramArrayOfint, int paramInt) throws IllegalArgumentException {
    this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(paramArrayOfint, paramInt);
  }
  
  void setAutoSizeTextTypeWithDefaults(int paramInt) {
    this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(paramInt);
  }
  
  void setTextSize(int paramInt, float paramFloat) {
    if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && !isAutoSizeEnabled())
      setTextSizeInternal(paramInt, paramFloat); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\AppCompatTextHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */