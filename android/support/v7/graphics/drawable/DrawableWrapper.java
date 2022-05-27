package android.support.v7.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

public class DrawableWrapper extends Drawable implements Drawable.Callback {
  private Drawable mDrawable;
  
  public DrawableWrapper(Drawable paramDrawable) {
    setWrappedDrawable(paramDrawable);
  }
  
  public void draw(Canvas paramCanvas) {
    this.mDrawable.draw(paramCanvas);
  }
  
  public int getChangingConfigurations() {
    return this.mDrawable.getChangingConfigurations();
  }
  
  public Drawable getCurrent() {
    return this.mDrawable.getCurrent();
  }
  
  public int getIntrinsicHeight() {
    return this.mDrawable.getIntrinsicHeight();
  }
  
  public int getIntrinsicWidth() {
    return this.mDrawable.getIntrinsicWidth();
  }
  
  public int getMinimumHeight() {
    return this.mDrawable.getMinimumHeight();
  }
  
  public int getMinimumWidth() {
    return this.mDrawable.getMinimumWidth();
  }
  
  public int getOpacity() {
    return this.mDrawable.getOpacity();
  }
  
  public boolean getPadding(Rect paramRect) {
    return this.mDrawable.getPadding(paramRect);
  }
  
  public int[] getState() {
    return this.mDrawable.getState();
  }
  
  public Region getTransparentRegion() {
    return this.mDrawable.getTransparentRegion();
  }
  
  public Drawable getWrappedDrawable() {
    return this.mDrawable;
  }
  
  public void invalidateDrawable(Drawable paramDrawable) {
    invalidateSelf();
  }
  
  public boolean isAutoMirrored() {
    return DrawableCompat.isAutoMirrored(this.mDrawable);
  }
  
  public boolean isStateful() {
    return this.mDrawable.isStateful();
  }
  
  public void jumpToCurrentState() {
    DrawableCompat.jumpToCurrentState(this.mDrawable);
  }
  
  protected void onBoundsChange(Rect paramRect) {
    this.mDrawable.setBounds(paramRect);
  }
  
  protected boolean onLevelChange(int paramInt) {
    return this.mDrawable.setLevel(paramInt);
  }
  
  public void scheduleDrawable(Drawable paramDrawable, Runnable paramRunnable, long paramLong) {
    scheduleSelf(paramRunnable, paramLong);
  }
  
  public void setAlpha(int paramInt) {
    this.mDrawable.setAlpha(paramInt);
  }
  
  public void setAutoMirrored(boolean paramBoolean) {
    DrawableCompat.setAutoMirrored(this.mDrawable, paramBoolean);
  }
  
  public void setChangingConfigurations(int paramInt) {
    this.mDrawable.setChangingConfigurations(paramInt);
  }
  
  public void setColorFilter(ColorFilter paramColorFilter) {
    this.mDrawable.setColorFilter(paramColorFilter);
  }
  
  public void setDither(boolean paramBoolean) {
    this.mDrawable.setDither(paramBoolean);
  }
  
  public void setFilterBitmap(boolean paramBoolean) {
    this.mDrawable.setFilterBitmap(paramBoolean);
  }
  
  public void setHotspot(float paramFloat1, float paramFloat2) {
    DrawableCompat.setHotspot(this.mDrawable, paramFloat1, paramFloat2);
  }
  
  public void setHotspotBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    DrawableCompat.setHotspotBounds(this.mDrawable, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public boolean setState(int[] paramArrayOfint) {
    return this.mDrawable.setState(paramArrayOfint);
  }
  
  public void setTint(int paramInt) {
    DrawableCompat.setTint(this.mDrawable, paramInt);
  }
  
  public void setTintList(ColorStateList paramColorStateList) {
    DrawableCompat.setTintList(this.mDrawable, paramColorStateList);
  }
  
  public void setTintMode(PorterDuff.Mode paramMode) {
    DrawableCompat.setTintMode(this.mDrawable, paramMode);
  }
  
  public boolean setVisible(boolean paramBoolean1, boolean paramBoolean2) {
    return (super.setVisible(paramBoolean1, paramBoolean2) || this.mDrawable.setVisible(paramBoolean1, paramBoolean2));
  }
  
  public void setWrappedDrawable(Drawable paramDrawable) {
    Drawable drawable = this.mDrawable;
    if (drawable != null)
      drawable.setCallback(null); 
    this.mDrawable = paramDrawable;
    if (paramDrawable != null)
      paramDrawable.setCallback(this); 
  }
  
  public void unscheduleDrawable(Drawable paramDrawable, Runnable paramRunnable) {
    unscheduleSelf(paramRunnable);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\graphics\drawable\DrawableWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */