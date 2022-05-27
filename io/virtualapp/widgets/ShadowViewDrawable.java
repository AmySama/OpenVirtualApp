package io.virtualapp.widgets;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;

public class ShadowViewDrawable extends Drawable {
  private RectF bounds = new RectF();
  
  private RectF drawRect;
  
  private int height;
  
  private Paint paint;
  
  private float rx;
  
  private float ry;
  
  private int shadowOffset;
  
  private ShadowProperty shadowProperty;
  
  private PorterDuffXfermode srcOut = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
  
  private int width;
  
  public ShadowViewDrawable(ShadowProperty paramShadowProperty, int paramInt, float paramFloat1, float paramFloat2) {
    this.shadowProperty = paramShadowProperty;
    this.shadowOffset = paramShadowProperty.getShadowOffset();
    this.rx = paramFloat1;
    this.ry = paramFloat2;
    Paint paint = new Paint();
    this.paint = paint;
    paint.setAntiAlias(true);
    this.paint.setFilterBitmap(true);
    this.paint.setDither(true);
    this.paint.setStyle(Paint.Style.FILL);
    this.paint.setColor(paramInt);
    this.paint.setShadowLayer(paramShadowProperty.getShadowRadius(), paramShadowProperty.getShadowDx(), paramShadowProperty.getShadowDy(), paramShadowProperty.getShadowColor());
    this.drawRect = new RectF();
  }
  
  public void draw(Canvas paramCanvas) {
    this.paint.setXfermode(null);
    paramCanvas.drawRoundRect(this.drawRect, this.rx, this.ry, this.paint);
    this.paint.setXfermode((Xfermode)this.srcOut);
    paramCanvas.drawRoundRect(this.drawRect, this.rx, this.ry, this.paint);
  }
  
  public int getOpacity() {
    return 0;
  }
  
  protected void onBoundsChange(Rect paramRect) {
    super.onBoundsChange(paramRect);
    if (paramRect.right - paramRect.left > 0 && paramRect.bottom - paramRect.top > 0) {
      boolean bool1;
      boolean bool2;
      byte b;
      this.bounds.left = paramRect.left;
      this.bounds.right = paramRect.right;
      this.bounds.top = paramRect.top;
      this.bounds.bottom = paramRect.bottom;
      this.width = (int)(this.bounds.right - this.bounds.left);
      this.height = (int)(this.bounds.bottom - this.bounds.top);
      int i = this.shadowProperty.getShadowSide();
      int j = 0;
      if ((i & 0x1) == 1) {
        bool1 = this.shadowOffset;
      } else {
        bool1 = false;
      } 
      if ((i & 0x10) == 16) {
        bool2 = this.shadowOffset;
      } else {
        bool2 = false;
      } 
      int k = this.width;
      if ((i & 0x100) == 256) {
        b = this.shadowOffset;
      } else {
        b = 0;
      } 
      int m = this.height;
      if ((i & 0x1000) == 4096)
        j = this.shadowOffset; 
      this.drawRect = new RectF(bool1, bool2, (k - b), (m - j));
      invalidateSelf();
    } 
  }
  
  public void setAlpha(int paramInt) {}
  
  public ShadowViewDrawable setColor(int paramInt) {
    this.paint.setColor(paramInt);
    return this;
  }
  
  public void setColorFilter(ColorFilter paramColorFilter) {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\ShadowViewDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */