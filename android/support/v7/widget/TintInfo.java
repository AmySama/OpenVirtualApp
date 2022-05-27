package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;

class TintInfo {
  public boolean mHasTintList;
  
  public boolean mHasTintMode;
  
  public ColorStateList mTintList;
  
  public PorterDuff.Mode mTintMode;
  
  void clear() {
    this.mTintList = null;
    this.mHasTintList = false;
    this.mTintMode = null;
    this.mHasTintMode = false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\TintInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */