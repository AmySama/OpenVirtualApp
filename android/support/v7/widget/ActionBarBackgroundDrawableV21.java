package android.support.v7.widget;

import android.graphics.Outline;

class ActionBarBackgroundDrawableV21 extends ActionBarBackgroundDrawable {
  public ActionBarBackgroundDrawableV21(ActionBarContainer paramActionBarContainer) {
    super(paramActionBarContainer);
  }
  
  public void getOutline(Outline paramOutline) {
    if (this.mContainer.mIsSplit) {
      if (this.mContainer.mSplitBackground != null)
        this.mContainer.mSplitBackground.getOutline(paramOutline); 
    } else if (this.mContainer.mBackground != null) {
      this.mContainer.mBackground.getOutline(paramOutline);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\ActionBarBackgroundDrawableV21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */