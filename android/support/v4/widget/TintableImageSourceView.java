package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;

public interface TintableImageSourceView {
  ColorStateList getSupportImageTintList();
  
  PorterDuff.Mode getSupportImageTintMode();
  
  void setSupportImageTintList(ColorStateList paramColorStateList);
  
  void setSupportImageTintMode(PorterDuff.Mode paramMode);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\TintableImageSourceView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */