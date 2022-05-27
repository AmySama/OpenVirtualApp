package com.lody.virtual.helper.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class BitmapUtils {
  public static Bitmap drawableToBitmap(Drawable paramDrawable) {
    Bitmap.Config config;
    if (paramDrawable instanceof BitmapDrawable)
      return ((BitmapDrawable)paramDrawable).getBitmap(); 
    int i = paramDrawable.getIntrinsicWidth();
    int j = paramDrawable.getIntrinsicHeight();
    if (paramDrawable.getOpacity() != -1) {
      config = Bitmap.Config.ARGB_8888;
    } else {
      config = Bitmap.Config.RGB_565;
    } 
    Bitmap bitmap = Bitmap.createBitmap(i, j, config);
    Canvas canvas = new Canvas(bitmap);
    paramDrawable.setBounds(0, 0, paramDrawable.getIntrinsicWidth(), paramDrawable.getIntrinsicHeight());
    paramDrawable.draw(canvas);
    return bitmap;
  }
  
  public static Bitmap warrperIcon(Bitmap paramBitmap, int paramInt1, int paramInt2) {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    Bitmap bitmap = paramBitmap;
    if (i >= paramInt1)
      if (j < paramInt2) {
        bitmap = paramBitmap;
      } else {
        float f1 = paramInt1 / i;
        float f2 = paramInt2 / j;
        Matrix matrix = new Matrix();
        matrix.postScale(f1, f2);
        bitmap = Bitmap.createBitmap(paramBitmap, 0, 0, i, j, matrix, true);
      }  
    return bitmap;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\BitmapUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */