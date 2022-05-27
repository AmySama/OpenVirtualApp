package android.support.v4.widget;

import android.support.v4.os.BuildCompat;

public interface AutoSizeableTextView {
  public static final boolean PLATFORM_SUPPORTS_AUTOSIZE = BuildCompat.isAtLeastOMR1();
  
  int getAutoSizeMaxTextSize();
  
  int getAutoSizeMinTextSize();
  
  int getAutoSizeStepGranularity();
  
  int[] getAutoSizeTextAvailableSizes();
  
  int getAutoSizeTextType();
  
  void setAutoSizeTextTypeUniformWithConfiguration(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IllegalArgumentException;
  
  void setAutoSizeTextTypeUniformWithPresetSizes(int[] paramArrayOfint, int paramInt) throws IllegalArgumentException;
  
  void setAutoSizeTextTypeWithDefaults(int paramInt);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\AutoSizeableTextView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */