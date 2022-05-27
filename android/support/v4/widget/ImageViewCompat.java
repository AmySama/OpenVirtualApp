package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

public class ImageViewCompat {
  static final ImageViewCompatImpl IMPL;
  
  static {
    if (Build.VERSION.SDK_INT >= 21) {
      IMPL = new LollipopViewCompatImpl();
    } else {
      IMPL = new BaseViewCompatImpl();
    } 
  }
  
  public static ColorStateList getImageTintList(ImageView paramImageView) {
    return IMPL.getImageTintList(paramImageView);
  }
  
  public static PorterDuff.Mode getImageTintMode(ImageView paramImageView) {
    return IMPL.getImageTintMode(paramImageView);
  }
  
  public static void setImageTintList(ImageView paramImageView, ColorStateList paramColorStateList) {
    IMPL.setImageTintList(paramImageView, paramColorStateList);
  }
  
  public static void setImageTintMode(ImageView paramImageView, PorterDuff.Mode paramMode) {
    IMPL.setImageTintMode(paramImageView, paramMode);
  }
  
  static class BaseViewCompatImpl implements ImageViewCompatImpl {
    public ColorStateList getImageTintList(ImageView param1ImageView) {
      if (param1ImageView instanceof TintableImageSourceView) {
        ColorStateList colorStateList = ((TintableImageSourceView)param1ImageView).getSupportImageTintList();
      } else {
        param1ImageView = null;
      } 
      return (ColorStateList)param1ImageView;
    }
    
    public PorterDuff.Mode getImageTintMode(ImageView param1ImageView) {
      if (param1ImageView instanceof TintableImageSourceView) {
        PorterDuff.Mode mode = ((TintableImageSourceView)param1ImageView).getSupportImageTintMode();
      } else {
        param1ImageView = null;
      } 
      return (PorterDuff.Mode)param1ImageView;
    }
    
    public void setImageTintList(ImageView param1ImageView, ColorStateList param1ColorStateList) {
      if (param1ImageView instanceof TintableImageSourceView)
        ((TintableImageSourceView)param1ImageView).setSupportImageTintList(param1ColorStateList); 
    }
    
    public void setImageTintMode(ImageView param1ImageView, PorterDuff.Mode param1Mode) {
      if (param1ImageView instanceof TintableImageSourceView)
        ((TintableImageSourceView)param1ImageView).setSupportImageTintMode(param1Mode); 
    }
  }
  
  static interface ImageViewCompatImpl {
    ColorStateList getImageTintList(ImageView param1ImageView);
    
    PorterDuff.Mode getImageTintMode(ImageView param1ImageView);
    
    void setImageTintList(ImageView param1ImageView, ColorStateList param1ColorStateList);
    
    void setImageTintMode(ImageView param1ImageView, PorterDuff.Mode param1Mode);
  }
  
  static class LollipopViewCompatImpl extends BaseViewCompatImpl {
    public ColorStateList getImageTintList(ImageView param1ImageView) {
      return param1ImageView.getImageTintList();
    }
    
    public PorterDuff.Mode getImageTintMode(ImageView param1ImageView) {
      return param1ImageView.getImageTintMode();
    }
    
    public void setImageTintList(ImageView param1ImageView, ColorStateList param1ColorStateList) {
      param1ImageView.setImageTintList(param1ColorStateList);
      if (Build.VERSION.SDK_INT == 21) {
        boolean bool;
        Drawable drawable = param1ImageView.getDrawable();
        if (param1ImageView.getImageTintList() != null && param1ImageView.getImageTintMode() != null) {
          bool = true;
        } else {
          bool = false;
        } 
        if (drawable != null && bool) {
          if (drawable.isStateful())
            drawable.setState(param1ImageView.getDrawableState()); 
          param1ImageView.setImageDrawable(drawable);
        } 
      } 
    }
    
    public void setImageTintMode(ImageView param1ImageView, PorterDuff.Mode param1Mode) {
      param1ImageView.setImageTintMode(param1Mode);
      if (Build.VERSION.SDK_INT == 21) {
        boolean bool;
        Drawable drawable = param1ImageView.getDrawable();
        if (param1ImageView.getImageTintList() != null && param1ImageView.getImageTintMode() != null) {
          bool = true;
        } else {
          bool = false;
        } 
        if (drawable != null && bool) {
          if (drawable.isStateful())
            drawable.setState(param1ImageView.getDrawableState()); 
          param1ImageView.setImageDrawable(drawable);
        } 
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\ImageViewCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */