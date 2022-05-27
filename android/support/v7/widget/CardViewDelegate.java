package android.support.v7.widget;

import android.graphics.drawable.Drawable;
import android.view.View;

interface CardViewDelegate {
  Drawable getCardBackground();
  
  View getCardView();
  
  boolean getPreventCornerOverlap();
  
  boolean getUseCompatPadding();
  
  void setCardBackground(Drawable paramDrawable);
  
  void setMinWidthHeightInternal(int paramInt1, int paramInt2);
  
  void setShadowPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\CardViewDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */