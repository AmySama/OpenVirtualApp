package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import java.lang.reflect.Method;

class WrappedDrawableApi21 extends WrappedDrawableApi19 {
  private static final String TAG = "WrappedDrawableApi21";
  
  private static Method sIsProjectedDrawableMethod;
  
  WrappedDrawableApi21(Drawable paramDrawable) {
    super(paramDrawable);
    findAndCacheIsProjectedDrawableMethod();
  }
  
  WrappedDrawableApi21(WrappedDrawableApi14.DrawableWrapperState paramDrawableWrapperState, Resources paramResources) {
    super(paramDrawableWrapperState, paramResources);
    findAndCacheIsProjectedDrawableMethod();
  }
  
  private void findAndCacheIsProjectedDrawableMethod() {
    if (sIsProjectedDrawableMethod == null)
      try {
        sIsProjectedDrawableMethod = Drawable.class.getDeclaredMethod("isProjected", new Class[0]);
      } catch (Exception exception) {
        Log.w("WrappedDrawableApi21", "Failed to retrieve Drawable#isProjected() method", exception);
      }  
  }
  
  public Rect getDirtyBounds() {
    return this.mDrawable.getDirtyBounds();
  }
  
  public void getOutline(Outline paramOutline) {
    this.mDrawable.getOutline(paramOutline);
  }
  
  protected boolean isCompatTintEnabled() {
    int i = Build.VERSION.SDK_INT;
    boolean bool = false;
    null = bool;
    if (i == 21) {
      Drawable drawable = this.mDrawable;
      if (!(drawable instanceof android.graphics.drawable.GradientDrawable) && !(drawable instanceof android.graphics.drawable.DrawableContainer) && !(drawable instanceof android.graphics.drawable.InsetDrawable)) {
        null = bool;
        return (drawable instanceof android.graphics.drawable.RippleDrawable) ? true : null;
      } 
    } else {
      return null;
    } 
    return true;
  }
  
  public boolean isProjected() {
    if (this.mDrawable != null) {
      Method method = sIsProjectedDrawableMethod;
      if (method != null)
        try {
          return ((Boolean)method.invoke(this.mDrawable, new Object[0])).booleanValue();
        } catch (Exception exception) {
          Log.w("WrappedDrawableApi21", "Error calling Drawable#isProjected() method", exception);
        }  
    } 
    return false;
  }
  
  WrappedDrawableApi14.DrawableWrapperState mutateConstantState() {
    return new DrawableWrapperStateLollipop(this.mState, null);
  }
  
  public void setHotspot(float paramFloat1, float paramFloat2) {
    this.mDrawable.setHotspot(paramFloat1, paramFloat2);
  }
  
  public void setHotspotBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mDrawable.setHotspotBounds(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public boolean setState(int[] paramArrayOfint) {
    if (super.setState(paramArrayOfint)) {
      invalidateSelf();
      return true;
    } 
    return false;
  }
  
  public void setTint(int paramInt) {
    if (isCompatTintEnabled()) {
      super.setTint(paramInt);
    } else {
      this.mDrawable.setTint(paramInt);
    } 
  }
  
  public void setTintList(ColorStateList paramColorStateList) {
    if (isCompatTintEnabled()) {
      super.setTintList(paramColorStateList);
    } else {
      this.mDrawable.setTintList(paramColorStateList);
    } 
  }
  
  public void setTintMode(PorterDuff.Mode paramMode) {
    if (isCompatTintEnabled()) {
      super.setTintMode(paramMode);
    } else {
      this.mDrawable.setTintMode(paramMode);
    } 
  }
  
  private static class DrawableWrapperStateLollipop extends WrappedDrawableApi14.DrawableWrapperState {
    DrawableWrapperStateLollipop(WrappedDrawableApi14.DrawableWrapperState param1DrawableWrapperState, Resources param1Resources) {
      super(param1DrawableWrapperState, param1Resources);
    }
    
    public Drawable newDrawable(Resources param1Resources) {
      return new WrappedDrawableApi21(this, param1Resources);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\graphics\drawable\WrappedDrawableApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */