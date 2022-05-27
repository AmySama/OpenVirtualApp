package com.lody.virtual.helper.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class DrawableUtils {
  public static Bitmap drawableToBitMap(Drawable paramDrawable) {
    Bitmap.Config config;
    if (paramDrawable == null)
      return null; 
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
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\DrawableUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */