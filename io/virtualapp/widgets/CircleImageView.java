package io.virtualapp.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import com.lody.virtual.helper.utils.BitmapUtils;

public class CircleImageView extends AppCompatImageView {
  private float height;
  
  private Matrix matrix;
  
  private Paint paint;
  
  private float radius;
  
  private float width;
  
  public CircleImageView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public CircleImageView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public CircleImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    Paint paint = new Paint();
    this.paint = paint;
    paint.setAntiAlias(true);
    this.matrix = new Matrix();
  }
  
  private BitmapShader initBitmapShader(BitmapDrawable paramBitmapDrawable) {
    Bitmap bitmap2 = paramBitmapDrawable.getBitmap();
    Bitmap bitmap1 = bitmap2;
    if (bitmap2 == null)
      bitmap1 = BitmapUtils.drawableToBitmap(getResources().getDrawable(2131231001)); 
    BitmapShader bitmapShader = new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    float f = Math.max(this.width / bitmap1.getWidth(), this.height / bitmap1.getHeight());
    this.matrix.setScale(f, f);
    bitmapShader.setLocalMatrix(this.matrix);
    return bitmapShader;
  }
  
  public int dip2px(float paramFloat) {
    return (int)(paramFloat * (getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  protected void onDraw(Canvas paramCanvas) {
    Drawable drawable = getDrawable();
    if (drawable == null) {
      super.onDraw(paramCanvas);
      return;
    } 
    if (drawable instanceof BitmapDrawable) {
      this.paint.setShader((Shader)initBitmapShader((BitmapDrawable)drawable));
      paramCanvas.drawCircle(this.width / 2.0F, this.height / 2.0F, this.radius, this.paint);
      return;
    } 
    super.onDraw(paramCanvas);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    super.onMeasure(paramInt1, paramInt2);
    this.width = getMeasuredWidth();
    float f = getMeasuredHeight();
    this.height = f;
    this.radius = Math.min(this.width, f) / 2.4F;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\CircleImageView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */