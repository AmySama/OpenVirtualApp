package android.support.v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

class WrappedDrawableApi19 extends WrappedDrawableApi14 {
  WrappedDrawableApi19(Drawable paramDrawable) {
    super(paramDrawable);
  }
  
  WrappedDrawableApi19(WrappedDrawableApi14.DrawableWrapperState paramDrawableWrapperState, Resources paramResources) {
    super(paramDrawableWrapperState, paramResources);
  }
  
  public boolean isAutoMirrored() {
    return this.mDrawable.isAutoMirrored();
  }
  
  WrappedDrawableApi14.DrawableWrapperState mutateConstantState() {
    return new DrawableWrapperStateKitKat(this.mState, null);
  }
  
  public void setAutoMirrored(boolean paramBoolean) {
    this.mDrawable.setAutoMirrored(paramBoolean);
  }
  
  private static class DrawableWrapperStateKitKat extends WrappedDrawableApi14.DrawableWrapperState {
    DrawableWrapperStateKitKat(WrappedDrawableApi14.DrawableWrapperState param1DrawableWrapperState, Resources param1Resources) {
      super(param1DrawableWrapperState, param1Resources);
    }
    
    public Drawable newDrawable(Resources param1Resources) {
      return new WrappedDrawableApi19(this, param1Resources);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\graphics\drawable\WrappedDrawableApi19.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */