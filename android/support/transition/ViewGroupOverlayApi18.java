package android.support.transition;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;

class ViewGroupOverlayApi18 implements ViewGroupOverlayImpl {
  private final ViewGroupOverlay mViewGroupOverlay;
  
  ViewGroupOverlayApi18(ViewGroup paramViewGroup) {
    this.mViewGroupOverlay = paramViewGroup.getOverlay();
  }
  
  public void add(Drawable paramDrawable) {
    this.mViewGroupOverlay.add(paramDrawable);
  }
  
  public void add(View paramView) {
    this.mViewGroupOverlay.add(paramView);
  }
  
  public void clear() {
    this.mViewGroupOverlay.clear();
  }
  
  public void remove(Drawable paramDrawable) {
    this.mViewGroupOverlay.remove(paramDrawable);
  }
  
  public void remove(View paramView) {
    this.mViewGroupOverlay.remove(paramView);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ViewGroupOverlayApi18.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */