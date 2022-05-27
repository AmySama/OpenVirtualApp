package com.tencent.mapsdk.rastercore.tile.a;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import com.tencent.mapsdk.raster.model.BitmapDescriptor;
import com.tencent.mapsdk.raster.model.GroundOverlayOptions;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.LatLngBounds;
import com.tencent.mapsdk.rastercore.d.a;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.mapsdk.rastercore.e.b;
import com.tencent.mapsdk.rastercore.tile.MapTile;

public class b implements b {
  private BitmapDescriptor a;
  
  private LatLng b;
  
  private float c;
  
  private float d;
  
  private LatLngBounds e;
  
  private float f;
  
  private float g;
  
  private boolean h = true;
  
  private float i = 0.0F;
  
  private float j = 0.5F;
  
  private float k = 0.5F;
  
  private String l;
  
  private Bitmap m;
  
  private e n;
  
  private a o;
  
  public b(e parame, GroundOverlayOptions paramGroundOverlayOptions) {
    this.n = parame;
    this.o = parame.e();
    this.l = getId();
    this.j = paramGroundOverlayOptions.getAnchorU();
    this.k = paramGroundOverlayOptions.getAnchorV();
    this.f = paramGroundOverlayOptions.getBearing();
    this.c = paramGroundOverlayOptions.getWidth();
    this.d = paramGroundOverlayOptions.getHeight();
    this.a = paramGroundOverlayOptions.getImage();
    this.b = paramGroundOverlayOptions.getLocation();
    this.e = paramGroundOverlayOptions.getBounds();
    this.i = paramGroundOverlayOptions.getTransparency();
    this.h = paramGroundOverlayOptions.isVisible();
    this.g = paramGroundOverlayOptions.getZIndex();
  }
  
  public static String a(MapTile.MapSource paramMapSource, int paramInt) {
    int i = null.a[paramMapSource.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i != 4) {
            StringBuilder stringBuilder3 = new StringBuilder("customized/");
            stringBuilder3.append(paramMapSource);
            return stringBuilder3.toString();
          } 
          return "Traffic";
        } 
        StringBuilder stringBuilder2 = new StringBuilder("Sate/V");
        stringBuilder2.append(paramInt);
        return stringBuilder2.toString();
      } 
      StringBuilder stringBuilder1 = new StringBuilder("BingGrid/");
      stringBuilder1.append(e.t());
      stringBuilder1.append("/");
      stringBuilder1.append(e.s());
      stringBuilder1.append("/");
      stringBuilder1.append(e.u());
      return stringBuilder1.toString();
    } 
    StringBuilder stringBuilder = new StringBuilder("Grid/");
    stringBuilder.append(e.y());
    stringBuilder.append("/");
    stringBuilder.append(paramInt);
    return stringBuilder.toString();
  }
  
  private void g() {
    double d1 = this.c / Math.cos(this.b.getLatitude() * 0.01745329251994329D) * 6371000.79D * 0.01745329251994329D;
    double d2 = this.d / 111194.94043265979D;
    this.e = new LatLngBounds(new LatLng(this.b.getLatitude() - (1.0F - this.k) * d2, this.b.getLongitude() - this.j * d1), new LatLng(this.b.getLatitude() + this.k * d2, this.b.getLongitude() + (1.0F - this.j) * d1));
  }
  
  private void h() {
    LatLng latLng1 = this.e.getSouthwest();
    LatLng latLng2 = this.e.getNortheast();
    LatLng latLng3 = new LatLng(latLng1.getLatitude() + (1.0F - this.k) * (latLng2.getLatitude() - latLng1.getLatitude()), latLng1.getLongitude() + this.j * (latLng2.getLongitude() - latLng1.getLongitude()));
    this.b = latLng3;
    this.c = (float)(Math.cos(latLng3.getLatitude() * 0.01745329251994329D) * 6371000.79D * (latLng2.getLongitude() - latLng1.getLongitude()) * 0.01745329251994329D);
    this.d = (float)((latLng2.getLatitude() - latLng1.getLatitude()) * 6371000.79D * 0.01745329251994329D);
  }
  
  public LatLng a() {
    return this.b;
  }
  
  public void a(float paramFloat) {
    float f = this.c;
    this.c = paramFloat;
    this.d = paramFloat;
    if (f != paramFloat)
      g(); 
    this.n.a(false, false);
  }
  
  public void a(float paramFloat1, float paramFloat2) {
    if (this.c != paramFloat1 && this.d != paramFloat2) {
      this.c = paramFloat1;
      this.d = paramFloat2;
      g();
    } else {
      this.c = paramFloat1;
      this.d = paramFloat2;
    } 
    this.n.a(false, false);
  }
  
  public void a(BitmapDescriptor paramBitmapDescriptor) {
    this.a = paramBitmapDescriptor;
    this.n.a(false, false);
  }
  
  public void a(LatLng paramLatLng) {
    LatLng latLng = this.b;
    if (latLng != null && !latLng.equals(paramLatLng)) {
      this.b = paramLatLng;
      g();
    } else {
      this.b = paramLatLng;
    } 
    this.n.a(false, false);
  }
  
  public void a(LatLngBounds paramLatLngBounds) {
    LatLngBounds latLngBounds = this.e;
    if (latLngBounds != null && !latLngBounds.equals(paramLatLngBounds)) {
      this.e = paramLatLngBounds;
      h();
    } else {
      this.e = paramLatLngBounds;
    } 
    this.n.a(false, false);
  }
  
  public float b() {
    return this.c;
  }
  
  public void b(float paramFloat) {
    this.f = paramFloat;
    this.n.a(false, false);
  }
  
  public void b(float paramFloat1, float paramFloat2) {
    this.j = paramFloat1;
    this.k = paramFloat2;
    this.n.a(false, false);
  }
  
  public float c() {
    return this.d;
  }
  
  public void c(float paramFloat) {
    this.i = paramFloat;
    this.n.a(false, false);
  }
  
  public boolean checkInBounds() {
    if (this.e == null)
      return false; 
    LatLngBounds latLngBounds = this.n.b().c();
    return (latLngBounds == null) ? true : ((latLngBounds.contains(this.e) || this.e.intersects(latLngBounds)));
  }
  
  public LatLngBounds d() {
    return this.e;
  }
  
  public void destroy() {
    try {
      remove();
      if (this.a != null) {
        Bitmap bitmap = this.a.getBitmap();
        if (bitmap != null) {
          bitmap.recycle();
          this.a = null;
        } 
      } 
      this.b = null;
      this.e = null;
      return;
    } catch (Exception exception) {
      (new StringBuilder("GroundOverlayDelegateImp destroy")).append(exception.getMessage());
      return;
    } 
  }
  
  public void draw(Canvas paramCanvas) {
    if (this.h && (this.b != null || this.e != null) && this.a != null) {
      if (this.b == null) {
        h();
      } else if (this.e == null) {
        g();
      } 
      if (this.c == 0.0F && this.d == 0.0F)
        return; 
      Bitmap bitmap = this.a.getBitmap();
      this.m = bitmap;
      if (bitmap != null && !bitmap.isRecycled()) {
        LatLng latLng2 = this.e.getSouthwest();
        LatLng latLng1 = this.e.getNortheast();
        PointF pointF1 = this.n.b().a(latLng2);
        PointF pointF2 = this.n.b().a(latLng1);
        Paint paint = new Paint();
        float f1 = (pointF2.x - pointF1.x) * this.j + pointF1.x;
        float f2 = (pointF1.y - pointF2.y) * this.k + pointF2.y;
        RectF rectF = new RectF(pointF1.x - f1, pointF2.y - f2, pointF2.x - f1, pointF1.y - f2);
        paint.setAlpha((int)(255.0F - this.i * 255.0F));
        paint.setFilterBitmap(true);
        paramCanvas.save();
        paramCanvas.translate(f1, f2);
        paramCanvas.rotate(this.f);
        paramCanvas.drawBitmap(this.m, null, rectF, paint);
        paramCanvas.restore();
      } 
    } 
  }
  
  public float e() {
    return this.f;
  }
  
  public boolean equalsRemote(b paramb) {
    return (equals(paramb) || paramb.getId().equals(getId()));
  }
  
  public float f() {
    return this.i;
  }
  
  public String getId() {
    if (this.l == null)
      this.l = a.a("GroundOverlay"); 
    return this.l;
  }
  
  public float getZIndex() {
    return this.g;
  }
  
  public int hashCodeRemote() {
    return hashCode();
  }
  
  public boolean isVisible() {
    return this.h;
  }
  
  public void remove() {
    this.o.b(getId());
  }
  
  public void setVisible(boolean paramBoolean) {
    this.h = paramBoolean;
    this.n.a(false, false);
  }
  
  public void setZIndex(float paramFloat) {
    this.g = paramFloat;
    this.o.c();
    this.n.a(false, false);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\tile\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */