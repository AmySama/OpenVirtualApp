package android.support.transition;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

class ViewGroupOverlayApi14 extends ViewOverlayApi14 implements ViewGroupOverlayImpl {
  ViewGroupOverlayApi14(Context paramContext, ViewGroup paramViewGroup, View paramView) {
    super(paramContext, paramViewGroup, paramView);
  }
  
  static ViewGroupOverlayApi14 createFrom(ViewGroup paramViewGroup) {
    return (ViewGroupOverlayApi14)ViewOverlayApi14.createFrom((View)paramViewGroup);
  }
  
  public void add(View paramView) {
    this.mOverlayViewGroup.add(paramView);
  }
  
  public void remove(View paramView) {
    this.mOverlayViewGroup.remove(paramView);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ViewGroupOverlayApi14.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */