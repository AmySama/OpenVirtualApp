package android.support.transition;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewOverlay;

class ViewOverlayApi18 implements ViewOverlayImpl {
  private final ViewOverlay mViewOverlay;
  
  ViewOverlayApi18(View paramView) {
    this.mViewOverlay = paramView.getOverlay();
  }
  
  public void add(Drawable paramDrawable) {
    this.mViewOverlay.add(paramDrawable);
  }
  
  public void clear() {
    this.mViewOverlay.clear();
  }
  
  public void remove(Drawable paramDrawable) {
    this.mViewOverlay.remove(paramDrawable);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ViewOverlayApi18.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */