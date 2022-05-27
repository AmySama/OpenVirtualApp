package io.virtualapp.widgets;

public interface ShimmerViewBase {
  float getGradientX();
  
  int getPrimaryColor();
  
  int getReflectionColor();
  
  boolean isSetUp();
  
  boolean isShimmering();
  
  void setAnimationSetupCallback(ShimmerViewHelper.AnimationSetupCallback paramAnimationSetupCallback);
  
  void setGradientX(float paramFloat);
  
  void setPrimaryColor(int paramInt);
  
  void setReflectionColor(int paramInt);
  
  void setShimmering(boolean paramBoolean);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\ShimmerViewBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */