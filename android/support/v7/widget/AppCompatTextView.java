package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.widget.AutoSizeableTextView;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;

public class AppCompatTextView extends TextView implements TintableBackgroundView, AutoSizeableTextView {
  private final AppCompatBackgroundHelper mBackgroundTintHelper;
  
  private final AppCompatTextHelper mTextHelper;
  
  public AppCompatTextView(Context paramContext) {
    this(paramContext, null);
  }
  
  public AppCompatTextView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 16842884);
  }
  
  public AppCompatTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(TintContextWrapper.wrap(paramContext), paramAttributeSet, paramInt);
    AppCompatBackgroundHelper appCompatBackgroundHelper = new AppCompatBackgroundHelper((View)this);
    this.mBackgroundTintHelper = appCompatBackgroundHelper;
    appCompatBackgroundHelper.loadFromAttributes(paramAttributeSet, paramInt);
    AppCompatTextHelper appCompatTextHelper = AppCompatTextHelper.create(this);
    this.mTextHelper = appCompatTextHelper;
    appCompatTextHelper.loadFromAttributes(paramAttributeSet, paramInt);
    this.mTextHelper.applyCompoundDrawablesTints();
  }
  
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null)
      appCompatBackgroundHelper.applySupportBackgroundTint(); 
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    if (appCompatTextHelper != null)
      appCompatTextHelper.applyCompoundDrawablesTints(); 
  }
  
  public int getAutoSizeMaxTextSize() {
    if (PLATFORM_SUPPORTS_AUTOSIZE)
      return super.getAutoSizeMaxTextSize(); 
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    return (appCompatTextHelper != null) ? appCompatTextHelper.getAutoSizeMaxTextSize() : -1;
  }
  
  public int getAutoSizeMinTextSize() {
    if (PLATFORM_SUPPORTS_AUTOSIZE)
      return super.getAutoSizeMinTextSize(); 
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    return (appCompatTextHelper != null) ? appCompatTextHelper.getAutoSizeMinTextSize() : -1;
  }
  
  public int getAutoSizeStepGranularity() {
    if (PLATFORM_SUPPORTS_AUTOSIZE)
      return super.getAutoSizeStepGranularity(); 
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    return (appCompatTextHelper != null) ? appCompatTextHelper.getAutoSizeStepGranularity() : -1;
  }
  
  public int[] getAutoSizeTextAvailableSizes() {
    if (PLATFORM_SUPPORTS_AUTOSIZE)
      return super.getAutoSizeTextAvailableSizes(); 
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    return (appCompatTextHelper != null) ? appCompatTextHelper.getAutoSizeTextAvailableSizes() : new int[0];
  }
  
  public int getAutoSizeTextType() {
    boolean bool = PLATFORM_SUPPORTS_AUTOSIZE;
    boolean bool1 = false;
    if (bool) {
      if (super.getAutoSizeTextType() == 1)
        bool1 = true; 
      return bool1;
    } 
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    return (appCompatTextHelper != null) ? appCompatTextHelper.getAutoSizeTextType() : 0;
  }
  
  public ColorStateList getSupportBackgroundTintList() {
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null) {
      ColorStateList colorStateList = appCompatBackgroundHelper.getSupportBackgroundTintList();
    } else {
      appCompatBackgroundHelper = null;
    } 
    return (ColorStateList)appCompatBackgroundHelper;
  }
  
  public PorterDuff.Mode getSupportBackgroundTintMode() {
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null) {
      PorterDuff.Mode mode = appCompatBackgroundHelper.getSupportBackgroundTintMode();
    } else {
      appCompatBackgroundHelper = null;
    } 
    return (PorterDuff.Mode)appCompatBackgroundHelper;
  }
  
  public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo) {
    return AppCompatHintHelper.onCreateInputConnection(super.onCreateInputConnection(paramEditorInfo), paramEditorInfo, (View)this);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    if (appCompatTextHelper != null)
      appCompatTextHelper.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4); 
  }
  
  protected void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
    super.onTextChanged(paramCharSequence, paramInt1, paramInt2, paramInt3);
    if (this.mTextHelper != null && !PLATFORM_SUPPORTS_AUTOSIZE && this.mTextHelper.isAutoSizeEnabled())
      this.mTextHelper.autoSizeText(); 
  }
  
  public void setAutoSizeTextTypeUniformWithConfiguration(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IllegalArgumentException {
    if (PLATFORM_SUPPORTS_AUTOSIZE) {
      super.setAutoSizeTextTypeUniformWithConfiguration(paramInt1, paramInt2, paramInt3, paramInt4);
    } else {
      AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
      if (appCompatTextHelper != null)
        appCompatTextHelper.setAutoSizeTextTypeUniformWithConfiguration(paramInt1, paramInt2, paramInt3, paramInt4); 
    } 
  }
  
  public void setAutoSizeTextTypeUniformWithPresetSizes(int[] paramArrayOfint, int paramInt) throws IllegalArgumentException {
    if (PLATFORM_SUPPORTS_AUTOSIZE) {
      super.setAutoSizeTextTypeUniformWithPresetSizes(paramArrayOfint, paramInt);
    } else {
      AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
      if (appCompatTextHelper != null)
        appCompatTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(paramArrayOfint, paramInt); 
    } 
  }
  
  public void setAutoSizeTextTypeWithDefaults(int paramInt) {
    if (PLATFORM_SUPPORTS_AUTOSIZE) {
      super.setAutoSizeTextTypeWithDefaults(paramInt);
    } else {
      AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
      if (appCompatTextHelper != null)
        appCompatTextHelper.setAutoSizeTextTypeWithDefaults(paramInt); 
    } 
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable) {
    super.setBackgroundDrawable(paramDrawable);
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null)
      appCompatBackgroundHelper.onSetBackgroundDrawable(paramDrawable); 
  }
  
  public void setBackgroundResource(int paramInt) {
    super.setBackgroundResource(paramInt);
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null)
      appCompatBackgroundHelper.onSetBackgroundResource(paramInt); 
  }
  
  public void setSupportBackgroundTintList(ColorStateList paramColorStateList) {
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null)
      appCompatBackgroundHelper.setSupportBackgroundTintList(paramColorStateList); 
  }
  
  public void setSupportBackgroundTintMode(PorterDuff.Mode paramMode) {
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null)
      appCompatBackgroundHelper.setSupportBackgroundTintMode(paramMode); 
  }
  
  public void setTextAppearance(Context paramContext, int paramInt) {
    super.setTextAppearance(paramContext, paramInt);
    AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
    if (appCompatTextHelper != null)
      appCompatTextHelper.onSetTextAppearance(paramContext, paramInt); 
  }
  
  public void setTextSize(int paramInt, float paramFloat) {
    if (PLATFORM_SUPPORTS_AUTOSIZE) {
      super.setTextSize(paramInt, paramFloat);
    } else {
      AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
      if (appCompatTextHelper != null)
        appCompatTextHelper.setTextSize(paramInt, paramFloat); 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\AppCompatTextView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */