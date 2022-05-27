package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.util.TypedValue;

public class TintTypedArray {
  private final Context mContext;
  
  private TypedValue mTypedValue;
  
  private final TypedArray mWrapped;
  
  private TintTypedArray(Context paramContext, TypedArray paramTypedArray) {
    this.mContext = paramContext;
    this.mWrapped = paramTypedArray;
  }
  
  public static TintTypedArray obtainStyledAttributes(Context paramContext, int paramInt, int[] paramArrayOfint) {
    return new TintTypedArray(paramContext, paramContext.obtainStyledAttributes(paramInt, paramArrayOfint));
  }
  
  public static TintTypedArray obtainStyledAttributes(Context paramContext, AttributeSet paramAttributeSet, int[] paramArrayOfint) {
    return new TintTypedArray(paramContext, paramContext.obtainStyledAttributes(paramAttributeSet, paramArrayOfint));
  }
  
  public static TintTypedArray obtainStyledAttributes(Context paramContext, AttributeSet paramAttributeSet, int[] paramArrayOfint, int paramInt1, int paramInt2) {
    return new TintTypedArray(paramContext, paramContext.obtainStyledAttributes(paramAttributeSet, paramArrayOfint, paramInt1, paramInt2));
  }
  
  public boolean getBoolean(int paramInt, boolean paramBoolean) {
    return this.mWrapped.getBoolean(paramInt, paramBoolean);
  }
  
  public int getChangingConfigurations() {
    return this.mWrapped.getChangingConfigurations();
  }
  
  public int getColor(int paramInt1, int paramInt2) {
    return this.mWrapped.getColor(paramInt1, paramInt2);
  }
  
  public ColorStateList getColorStateList(int paramInt) {
    if (this.mWrapped.hasValue(paramInt)) {
      int i = this.mWrapped.getResourceId(paramInt, 0);
      if (i != 0) {
        ColorStateList colorStateList = AppCompatResources.getColorStateList(this.mContext, i);
        if (colorStateList != null)
          return colorStateList; 
      } 
    } 
    return this.mWrapped.getColorStateList(paramInt);
  }
  
  public float getDimension(int paramInt, float paramFloat) {
    return this.mWrapped.getDimension(paramInt, paramFloat);
  }
  
  public int getDimensionPixelOffset(int paramInt1, int paramInt2) {
    return this.mWrapped.getDimensionPixelOffset(paramInt1, paramInt2);
  }
  
  public int getDimensionPixelSize(int paramInt1, int paramInt2) {
    return this.mWrapped.getDimensionPixelSize(paramInt1, paramInt2);
  }
  
  public Drawable getDrawable(int paramInt) {
    if (this.mWrapped.hasValue(paramInt)) {
      int i = this.mWrapped.getResourceId(paramInt, 0);
      if (i != 0)
        return AppCompatResources.getDrawable(this.mContext, i); 
    } 
    return this.mWrapped.getDrawable(paramInt);
  }
  
  public Drawable getDrawableIfKnown(int paramInt) {
    if (this.mWrapped.hasValue(paramInt)) {
      paramInt = this.mWrapped.getResourceId(paramInt, 0);
      if (paramInt != 0)
        return AppCompatDrawableManager.get().getDrawable(this.mContext, paramInt, true); 
    } 
    return null;
  }
  
  public float getFloat(int paramInt, float paramFloat) {
    return this.mWrapped.getFloat(paramInt, paramFloat);
  }
  
  public Typeface getFont(int paramInt1, int paramInt2, ResourcesCompat.FontCallback paramFontCallback) {
    paramInt1 = this.mWrapped.getResourceId(paramInt1, 0);
    if (paramInt1 == 0)
      return null; 
    if (this.mTypedValue == null)
      this.mTypedValue = new TypedValue(); 
    return ResourcesCompat.getFont(this.mContext, paramInt1, this.mTypedValue, paramInt2, paramFontCallback);
  }
  
  public float getFraction(int paramInt1, int paramInt2, int paramInt3, float paramFloat) {
    return this.mWrapped.getFraction(paramInt1, paramInt2, paramInt3, paramFloat);
  }
  
  public int getIndex(int paramInt) {
    return this.mWrapped.getIndex(paramInt);
  }
  
  public int getIndexCount() {
    return this.mWrapped.getIndexCount();
  }
  
  public int getInt(int paramInt1, int paramInt2) {
    return this.mWrapped.getInt(paramInt1, paramInt2);
  }
  
  public int getInteger(int paramInt1, int paramInt2) {
    return this.mWrapped.getInteger(paramInt1, paramInt2);
  }
  
  public int getLayoutDimension(int paramInt1, int paramInt2) {
    return this.mWrapped.getLayoutDimension(paramInt1, paramInt2);
  }
  
  public int getLayoutDimension(int paramInt, String paramString) {
    return this.mWrapped.getLayoutDimension(paramInt, paramString);
  }
  
  public String getNonResourceString(int paramInt) {
    return this.mWrapped.getNonResourceString(paramInt);
  }
  
  public String getPositionDescription() {
    return this.mWrapped.getPositionDescription();
  }
  
  public int getResourceId(int paramInt1, int paramInt2) {
    return this.mWrapped.getResourceId(paramInt1, paramInt2);
  }
  
  public Resources getResources() {
    return this.mWrapped.getResources();
  }
  
  public String getString(int paramInt) {
    return this.mWrapped.getString(paramInt);
  }
  
  public CharSequence getText(int paramInt) {
    return this.mWrapped.getText(paramInt);
  }
  
  public CharSequence[] getTextArray(int paramInt) {
    return this.mWrapped.getTextArray(paramInt);
  }
  
  public int getType(int paramInt) {
    if (Build.VERSION.SDK_INT >= 21)
      return this.mWrapped.getType(paramInt); 
    if (this.mTypedValue == null)
      this.mTypedValue = new TypedValue(); 
    this.mWrapped.getValue(paramInt, this.mTypedValue);
    return this.mTypedValue.type;
  }
  
  public boolean getValue(int paramInt, TypedValue paramTypedValue) {
    return this.mWrapped.getValue(paramInt, paramTypedValue);
  }
  
  public boolean hasValue(int paramInt) {
    return this.mWrapped.hasValue(paramInt);
  }
  
  public int length() {
    return this.mWrapped.length();
  }
  
  public TypedValue peekValue(int paramInt) {
    return this.mWrapped.peekValue(paramInt);
  }
  
  public void recycle() {
    this.mWrapped.recycle();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\TintTypedArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */