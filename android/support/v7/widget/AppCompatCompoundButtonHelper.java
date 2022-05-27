package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.widget.CompoundButton;

class AppCompatCompoundButtonHelper {
  private ColorStateList mButtonTintList = null;
  
  private PorterDuff.Mode mButtonTintMode = null;
  
  private boolean mHasButtonTint = false;
  
  private boolean mHasButtonTintMode = false;
  
  private boolean mSkipNextApply;
  
  private final CompoundButton mView;
  
  AppCompatCompoundButtonHelper(CompoundButton paramCompoundButton) {
    this.mView = paramCompoundButton;
  }
  
  void applyButtonTint() {
    Drawable drawable = CompoundButtonCompat.getButtonDrawable(this.mView);
    if (drawable != null && (this.mHasButtonTint || this.mHasButtonTintMode)) {
      drawable = DrawableCompat.wrap(drawable).mutate();
      if (this.mHasButtonTint)
        DrawableCompat.setTintList(drawable, this.mButtonTintList); 
      if (this.mHasButtonTintMode)
        DrawableCompat.setTintMode(drawable, this.mButtonTintMode); 
      if (drawable.isStateful())
        drawable.setState(this.mView.getDrawableState()); 
      this.mView.setButtonDrawable(drawable);
    } 
  }
  
  int getCompoundPaddingLeft(int paramInt) {
    int i = paramInt;
    if (Build.VERSION.SDK_INT < 17) {
      Drawable drawable = CompoundButtonCompat.getButtonDrawable(this.mView);
      i = paramInt;
      if (drawable != null)
        i = paramInt + drawable.getIntrinsicWidth(); 
    } 
    return i;
  }
  
  ColorStateList getSupportButtonTintList() {
    return this.mButtonTintList;
  }
  
  PorterDuff.Mode getSupportButtonTintMode() {
    return this.mButtonTintMode;
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt) {
    TypedArray typedArray = this.mView.getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.CompoundButton, paramInt, 0);
    try {
      if (typedArray.hasValue(R.styleable.CompoundButton_android_button)) {
        paramInt = typedArray.getResourceId(R.styleable.CompoundButton_android_button, 0);
        if (paramInt != 0)
          this.mView.setButtonDrawable(AppCompatResources.getDrawable(this.mView.getContext(), paramInt)); 
      } 
      if (typedArray.hasValue(R.styleable.CompoundButton_buttonTint))
        CompoundButtonCompat.setButtonTintList(this.mView, typedArray.getColorStateList(R.styleable.CompoundButton_buttonTint)); 
      if (typedArray.hasValue(R.styleable.CompoundButton_buttonTintMode))
        CompoundButtonCompat.setButtonTintMode(this.mView, DrawableUtils.parseTintMode(typedArray.getInt(R.styleable.CompoundButton_buttonTintMode, -1), null)); 
      return;
    } finally {
      typedArray.recycle();
    } 
  }
  
  void onSetButtonDrawable() {
    if (this.mSkipNextApply) {
      this.mSkipNextApply = false;
      return;
    } 
    this.mSkipNextApply = true;
    applyButtonTint();
  }
  
  void setSupportButtonTintList(ColorStateList paramColorStateList) {
    this.mButtonTintList = paramColorStateList;
    this.mHasButtonTint = true;
    applyButtonTint();
  }
  
  void setSupportButtonTintMode(PorterDuff.Mode paramMode) {
    this.mButtonTintMode = paramMode;
    this.mHasButtonTintMode = true;
    applyButtonTint();
  }
  
  static interface DirectSetButtonDrawableInterface {
    void setButtonDrawable(Drawable param1Drawable);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\AppCompatCompoundButtonHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */