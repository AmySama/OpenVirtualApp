package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.TintableCompoundButton;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class AppCompatRadioButton extends RadioButton implements TintableCompoundButton {
  private final AppCompatCompoundButtonHelper mCompoundButtonHelper;
  
  private final AppCompatTextHelper mTextHelper;
  
  public AppCompatRadioButton(Context paramContext) {
    this(paramContext, null);
  }
  
  public AppCompatRadioButton(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, R.attr.radioButtonStyle);
  }
  
  public AppCompatRadioButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(TintContextWrapper.wrap(paramContext), paramAttributeSet, paramInt);
    AppCompatCompoundButtonHelper appCompatCompoundButtonHelper = new AppCompatCompoundButtonHelper((CompoundButton)this);
    this.mCompoundButtonHelper = appCompatCompoundButtonHelper;
    appCompatCompoundButtonHelper.loadFromAttributes(paramAttributeSet, paramInt);
    AppCompatTextHelper appCompatTextHelper = new AppCompatTextHelper((TextView)this);
    this.mTextHelper = appCompatTextHelper;
    appCompatTextHelper.loadFromAttributes(paramAttributeSet, paramInt);
  }
  
  public int getCompoundPaddingLeft() {
    int i = super.getCompoundPaddingLeft();
    AppCompatCompoundButtonHelper appCompatCompoundButtonHelper = this.mCompoundButtonHelper;
    int j = i;
    if (appCompatCompoundButtonHelper != null)
      j = appCompatCompoundButtonHelper.getCompoundPaddingLeft(i); 
    return j;
  }
  
  public ColorStateList getSupportButtonTintList() {
    AppCompatCompoundButtonHelper appCompatCompoundButtonHelper = this.mCompoundButtonHelper;
    if (appCompatCompoundButtonHelper != null) {
      ColorStateList colorStateList = appCompatCompoundButtonHelper.getSupportButtonTintList();
    } else {
      appCompatCompoundButtonHelper = null;
    } 
    return (ColorStateList)appCompatCompoundButtonHelper;
  }
  
  public PorterDuff.Mode getSupportButtonTintMode() {
    AppCompatCompoundButtonHelper appCompatCompoundButtonHelper = this.mCompoundButtonHelper;
    if (appCompatCompoundButtonHelper != null) {
      PorterDuff.Mode mode = appCompatCompoundButtonHelper.getSupportButtonTintMode();
    } else {
      appCompatCompoundButtonHelper = null;
    } 
    return (PorterDuff.Mode)appCompatCompoundButtonHelper;
  }
  
  public void setButtonDrawable(int paramInt) {
    setButtonDrawable(AppCompatResources.getDrawable(getContext(), paramInt));
  }
  
  public void setButtonDrawable(Drawable paramDrawable) {
    super.setButtonDrawable(paramDrawable);
    AppCompatCompoundButtonHelper appCompatCompoundButtonHelper = this.mCompoundButtonHelper;
    if (appCompatCompoundButtonHelper != null)
      appCompatCompoundButtonHelper.onSetButtonDrawable(); 
  }
  
  public void setSupportButtonTintList(ColorStateList paramColorStateList) {
    AppCompatCompoundButtonHelper appCompatCompoundButtonHelper = this.mCompoundButtonHelper;
    if (appCompatCompoundButtonHelper != null)
      appCompatCompoundButtonHelper.setSupportButtonTintList(paramColorStateList); 
  }
  
  public void setSupportButtonTintMode(PorterDuff.Mode paramMode) {
    AppCompatCompoundButtonHelper appCompatCompoundButtonHelper = this.mCompoundButtonHelper;
    if (appCompatCompoundButtonHelper != null)
      appCompatCompoundButtonHelper.setSupportButtonTintMode(paramMode); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\AppCompatRadioButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */