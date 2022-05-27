package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

class ThemeUtils {
  static final int[] ACTIVATED_STATE_SET;
  
  static final int[] CHECKED_STATE_SET;
  
  static final int[] DISABLED_STATE_SET;
  
  static final int[] EMPTY_STATE_SET;
  
  static final int[] FOCUSED_STATE_SET;
  
  static final int[] NOT_PRESSED_OR_FOCUSED_STATE_SET;
  
  static final int[] PRESSED_STATE_SET;
  
  static final int[] SELECTED_STATE_SET;
  
  private static final int[] TEMP_ARRAY;
  
  private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal<TypedValue>();
  
  static {
    DISABLED_STATE_SET = new int[] { -16842910 };
    FOCUSED_STATE_SET = new int[] { 16842908 };
    ACTIVATED_STATE_SET = new int[] { 16843518 };
    PRESSED_STATE_SET = new int[] { 16842919 };
    CHECKED_STATE_SET = new int[] { 16842912 };
    SELECTED_STATE_SET = new int[] { 16842913 };
    NOT_PRESSED_OR_FOCUSED_STATE_SET = new int[] { -16842919, -16842908 };
    EMPTY_STATE_SET = new int[0];
    TEMP_ARRAY = new int[1];
  }
  
  public static ColorStateList createDisabledStateList(int paramInt1, int paramInt2) {
    return new ColorStateList(new int[][] { DISABLED_STATE_SET, EMPTY_STATE_SET }, new int[] { paramInt2, paramInt1 });
  }
  
  public static int getDisabledThemeAttrColor(Context paramContext, int paramInt) {
    ColorStateList colorStateList = getThemeAttrColorStateList(paramContext, paramInt);
    if (colorStateList != null && colorStateList.isStateful())
      return colorStateList.getColorForState(DISABLED_STATE_SET, colorStateList.getDefaultColor()); 
    TypedValue typedValue = getTypedValue();
    paramContext.getTheme().resolveAttribute(16842803, typedValue, true);
    return getThemeAttrColor(paramContext, paramInt, typedValue.getFloat());
  }
  
  public static int getThemeAttrColor(Context paramContext, int paramInt) {
    null = TEMP_ARRAY;
    null[0] = paramInt;
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, (AttributeSet)null, null);
    try {
      paramInt = tintTypedArray.getColor(0, 0);
      return paramInt;
    } finally {
      tintTypedArray.recycle();
    } 
  }
  
  static int getThemeAttrColor(Context paramContext, int paramInt, float paramFloat) {
    paramInt = getThemeAttrColor(paramContext, paramInt);
    return ColorUtils.setAlphaComponent(paramInt, Math.round(Color.alpha(paramInt) * paramFloat));
  }
  
  public static ColorStateList getThemeAttrColorStateList(Context paramContext, int paramInt) {
    null = TEMP_ARRAY;
    null[0] = paramInt;
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, (AttributeSet)null, null);
    try {
      return tintTypedArray.getColorStateList(0);
    } finally {
      tintTypedArray.recycle();
    } 
  }
  
  private static TypedValue getTypedValue() {
    TypedValue typedValue1 = TL_TYPED_VALUE.get();
    TypedValue typedValue2 = typedValue1;
    if (typedValue1 == null) {
      typedValue2 = new TypedValue();
      TL_TYPED_VALUE.set(typedValue2);
    } 
    return typedValue2;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\ThemeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */