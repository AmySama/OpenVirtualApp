package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;

public interface TintableBackgroundView {
  ColorStateList getSupportBackgroundTintList();
  
  PorterDuff.Mode getSupportBackgroundTintMode();
  
  void setSupportBackgroundTintList(ColorStateList paramColorStateList);
  
  void setSupportBackgroundTintMode(PorterDuff.Mode paramMode);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\TintableBackgroundView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */