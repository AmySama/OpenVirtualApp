package android.support.v7.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.support.v4.graphics.drawable.WrappedDrawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

class AppCompatProgressBarHelper {
  private static final int[] TINT_ATTRS = new int[] { 16843067, 16843068 };
  
  private Bitmap mSampleTile;
  
  private final ProgressBar mView;
  
  AppCompatProgressBarHelper(ProgressBar paramProgressBar) {
    this.mView = paramProgressBar;
  }
  
  private Shape getDrawableShape() {
    return (Shape)new RoundRectShape(new float[] { 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F }, null, null);
  }
  
  private Drawable tileify(Drawable paramDrawable, boolean paramBoolean) {
    ClipDrawable clipDrawable;
    if (paramDrawable instanceof WrappedDrawable) {
      WrappedDrawable wrappedDrawable = (WrappedDrawable)paramDrawable;
      Drawable drawable = wrappedDrawable.getWrappedDrawable();
      if (drawable != null)
        wrappedDrawable.setWrappedDrawable(tileify(drawable, paramBoolean)); 
    } else {
      LayerDrawable layerDrawable;
      if (paramDrawable instanceof LayerDrawable) {
        layerDrawable = (LayerDrawable)paramDrawable;
        int i = layerDrawable.getNumberOfLayers();
        Drawable[] arrayOfDrawable = new Drawable[i];
        boolean bool = false;
        byte b;
        for (b = 0; b < i; b++) {
          int j = layerDrawable.getId(b);
          Drawable drawable = layerDrawable.getDrawable(b);
          if (j == 16908301 || j == 16908303) {
            paramBoolean = true;
          } else {
            paramBoolean = false;
          } 
          arrayOfDrawable[b] = tileify(drawable, paramBoolean);
        } 
        LayerDrawable layerDrawable1 = new LayerDrawable(arrayOfDrawable);
        for (b = bool; b < i; b++)
          layerDrawable1.setId(b, layerDrawable.getId(b)); 
        return (Drawable)layerDrawable1;
      } 
      if (layerDrawable instanceof BitmapDrawable) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable)layerDrawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        if (this.mSampleTile == null)
          this.mSampleTile = bitmap; 
        ShapeDrawable shapeDrawable2 = new ShapeDrawable(getDrawableShape());
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        shapeDrawable2.getPaint().setShader((Shader)bitmapShader);
        shapeDrawable2.getPaint().setColorFilter(bitmapDrawable.getPaint().getColorFilter());
        ShapeDrawable shapeDrawable1 = shapeDrawable2;
        if (paramBoolean)
          clipDrawable = new ClipDrawable((Drawable)shapeDrawable2, 3, 1); 
        return (Drawable)clipDrawable;
      } 
    } 
    return (Drawable)clipDrawable;
  }
  
  private Drawable tileifyIndeterminate(Drawable paramDrawable) {
    AnimationDrawable animationDrawable;
    Drawable drawable = paramDrawable;
    if (paramDrawable instanceof AnimationDrawable) {
      AnimationDrawable animationDrawable1 = (AnimationDrawable)paramDrawable;
      int i = animationDrawable1.getNumberOfFrames();
      animationDrawable = new AnimationDrawable();
      animationDrawable.setOneShot(animationDrawable1.isOneShot());
      for (byte b = 0; b < i; b++) {
        paramDrawable = tileify(animationDrawable1.getFrame(b), true);
        paramDrawable.setLevel(10000);
        animationDrawable.addFrame(paramDrawable, animationDrawable1.getDuration(b));
      } 
      animationDrawable.setLevel(10000);
    } 
    return (Drawable)animationDrawable;
  }
  
  Bitmap getSampleTime() {
    return this.mSampleTile;
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt) {
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), paramAttributeSet, TINT_ATTRS, paramInt, 0);
    Drawable drawable = tintTypedArray.getDrawableIfKnown(0);
    if (drawable != null)
      this.mView.setIndeterminateDrawable(tileifyIndeterminate(drawable)); 
    drawable = tintTypedArray.getDrawableIfKnown(1);
    if (drawable != null)
      this.mView.setProgressDrawable(tileify(drawable, false)); 
    tintTypedArray.recycle();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\AppCompatProgressBarHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */