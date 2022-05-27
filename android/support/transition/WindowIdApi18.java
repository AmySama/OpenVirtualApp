package android.support.transition;

import android.view.View;
import android.view.WindowId;

class WindowIdApi18 implements WindowIdImpl {
  private final WindowId mWindowId;
  
  WindowIdApi18(View paramView) {
    this.mWindowId = paramView.getWindowId();
  }
  
  public boolean equals(Object paramObject) {
    boolean bool;
    if (paramObject instanceof WindowIdApi18 && ((WindowIdApi18)paramObject).mWindowId.equals(this.mWindowId)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int hashCode() {
    return this.mWindowId.hashCode();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\WindowIdApi18.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */