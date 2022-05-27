package io.virtualapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class ScreenUtils {
  private ScreenUtils() {
    throw new UnsupportedOperationException("cannot be instantiated");
  }
  
  public static int dip2px(Context paramContext, float paramFloat) {
    return (int)(paramFloat * (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  public static int getScreenHeight(Context paramContext) {
    WindowManager windowManager = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics displayMetrics = new DisplayMetrics();
    windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    return displayMetrics.heightPixels;
  }
  
  public static int getScreenWidth(Context paramContext) {
    WindowManager windowManager = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics displayMetrics = new DisplayMetrics();
    windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    return displayMetrics.widthPixels;
  }
  
  public static int getStatusHeight(Context paramContext) {
    byte b;
    try {
      Class<?> clazz = Class.forName("com.android.internal.R$dimen");
      Object object = clazz.newInstance();
      b = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
      b = paramContext.getResources().getDimensionPixelSize(b);
    } catch (Exception exception) {
      exception.printStackTrace();
      b = -1;
    } 
    return b;
  }
  
  public static int px2dip(Context paramContext, float paramFloat) {
    return (int)(paramFloat / (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  public static Bitmap snapShotWithStatusBar(Activity paramActivity) {
    View view = paramActivity.getWindow().getDecorView();
    view.setDrawingCacheEnabled(true);
    view.buildDrawingCache();
    Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, getScreenWidth((Context)paramActivity), getScreenHeight((Context)paramActivity));
    view.destroyDrawingCache();
    return bitmap;
  }
  
  public static Bitmap snapShotWithoutStatusBar(Activity paramActivity) {
    View view = paramActivity.getWindow().getDecorView();
    view.setDrawingCacheEnabled(true);
    view.buildDrawingCache();
    Bitmap bitmap2 = view.getDrawingCache();
    Rect rect = new Rect();
    paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
    int i = rect.top;
    Bitmap bitmap1 = Bitmap.createBitmap(bitmap2, 0, i, getScreenWidth((Context)paramActivity), getScreenHeight((Context)paramActivity) - i);
    view.destroyDrawingCache();
    return bitmap1;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\ScreenUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */