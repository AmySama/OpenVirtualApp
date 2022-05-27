package com.tencent.mapsdk.raster.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.FrameLayout;
import com.tencent.mapsdk.rastercore.d;
import com.tencent.mapsdk.rastercore.d.e;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public final class BitmapDescriptorFactory {
  public static BitmapDescriptor defaultMarker() {
    return fromAsset("marker.png");
  }
  
  public static BitmapDescriptor fromAsset(String paramString) {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      this("/assets/");
      stringBuilder.append(paramString);
      InputStream inputStream = BitmapDescriptorFactory.class.getResourceAsStream(stringBuilder.toString());
      Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
      inputStream.close();
      return fromBitmap(bitmap);
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public static BitmapDescriptor fromBitmap(Bitmap paramBitmap) {
    return (paramBitmap == null) ? null : new BitmapDescriptor(paramBitmap);
  }
  
  public static BitmapDescriptor fromFile(String paramString) {
    try {
      File file = new File();
      this(paramString);
      FileInputStream fileInputStream = new FileInputStream();
      this(file);
      Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
      fileInputStream.close();
      return fromBitmap(bitmap);
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public static BitmapDescriptor fromPath(String paramString) {
    try {
      return fromBitmap(BitmapFactory.decodeFile(paramString));
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public static BitmapDescriptor fromResource(int paramInt) {
    Closeable closeable;
    try {
    
    } catch (Exception exception) {
    
    } finally {
      closeable = null;
      d.a.a(closeable);
    } 
    d.a.a(closeable);
    return null;
  }
  
  public static BitmapDescriptor fromView(View paramView) {
    try {
      Context context = e.a();
      if (context != null) {
        FrameLayout frameLayout = new FrameLayout();
        this(context);
        frameLayout.addView(paramView);
        frameLayout.destroyDrawingCache();
        return fromBitmap(getViewBitmap((View)frameLayout));
      } 
    } catch (Exception exception) {}
    return null;
  }
  
  private static Bitmap getViewBitmap(View paramView) {
    paramView.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
    paramView.layout(0, 0, paramView.getMeasuredWidth(), paramView.getMeasuredHeight());
    paramView.buildDrawingCache();
    return paramView.getDrawingCache().copy(Bitmap.Config.ARGB_8888, false);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\BitmapDescriptorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */