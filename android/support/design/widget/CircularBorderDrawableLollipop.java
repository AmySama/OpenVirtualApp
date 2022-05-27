package android.support.design.widget;

import android.graphics.Outline;

class CircularBorderDrawableLollipop extends CircularBorderDrawable {
  public void getOutline(Outline paramOutline) {
    copyBounds(this.mRect);
    paramOutline.setOval(this.mRect);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\CircularBorderDrawableLollipop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */