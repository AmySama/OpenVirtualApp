package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;

public interface TintableCompoundButton {
  ColorStateList getSupportButtonTintList();
  
  PorterDuff.Mode getSupportButtonTintMode();
  
  void setSupportButtonTintList(ColorStateList paramColorStateList);
  
  void setSupportButtonTintMode(PorterDuff.Mode paramMode);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\TintableCompoundButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */