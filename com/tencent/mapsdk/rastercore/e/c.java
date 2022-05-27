package com.tencent.mapsdk.rastercore.e;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.LatLngBounds;
import com.tencent.mapsdk.raster.model.PolygonOptions;
import com.tencent.mapsdk.rastercore.d.a;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.mapsdk.rastercore.f.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class c implements b {
  private List<LatLng> a = new ArrayList<LatLng>();
  
  private int b;
  
  private int c;
  
  private LatLngBounds d = null;
  
  private boolean e = true;
  
  private float f;
  
  private float g = 0.0F;
  
  private String h;
  
  private e i;
  
  private a j;
  
  public c(e parame, PolygonOptions paramPolygonOptions) {
    this.i = parame;
    this.j = parame.e();
    this.h = getId();
    this.b = paramPolygonOptions.getFillColor();
    b(paramPolygonOptions.getPoints());
    this.e = paramPolygonOptions.isVisible();
    this.f = paramPolygonOptions.getStrokeWidth();
    this.g = paramPolygonOptions.getZIndex();
    this.c = paramPolygonOptions.getStrokeColor();
  }
  
  private void b(List<LatLng> paramList) {
    LatLngBounds.Builder builder = LatLngBounds.builder();
    this.a.clear();
    if (paramList != null) {
      List list = null;
      Iterator<LatLng> iterator = paramList.iterator();
      paramList = list;
      while (iterator.hasNext()) {
        LatLng latLng = iterator.next();
        if (!latLng.equals(paramList)) {
          this.a.add(latLng);
          builder.include(latLng);
          LatLng latLng1 = latLng;
        } 
      } 
      int i = this.a.size();
      if (i > 1) {
        LatLng latLng = this.a.get(0);
        paramList = this.a;
        if (latLng.equals(paramList.get(--i)))
          this.a.remove(i); 
      } 
    } 
    this.d = builder.build();
  }
  
  public float a() {
    return this.f;
  }
  
  public void a(float paramFloat) {
    this.f = paramFloat;
    this.i.a(false, false);
  }
  
  public void a(int paramInt) {
    this.b = paramInt;
    this.i.a(false, false);
  }
  
  public void a(List<LatLng> paramList) {
    b(paramList);
    this.i.a(false, false);
  }
  
  public boolean a(LatLng paramLatLng) {
    return a.a(paramLatLng, c());
  }
  
  public int b() {
    return this.b;
  }
  
  public void b(int paramInt) {
    this.c = paramInt;
    this.i.a(false, false);
  }
  
  public List<LatLng> c() {
    return this.a;
  }
  
  public boolean checkInBounds() {
    if (this.d == null)
      return false; 
    LatLngBounds latLngBounds = this.i.b().c();
    return (latLngBounds == null) ? true : ((this.d.contains(latLngBounds) || this.d.intersects(latLngBounds)));
  }
  
  public int d() {
    return this.c;
  }
  
  public void destroy() {}
  
  public void draw(Canvas paramCanvas) {
    List<LatLng> list = this.a;
    if (list != null && list.size() != 0) {
      Path path = new Path();
      LatLng latLng = this.a.get(0);
      new PointF();
      PointF pointF = this.i.b().a(latLng);
      path.moveTo(pointF.x, pointF.y);
      for (byte b1 = 1; b1 < this.a.size(); b1++) {
        LatLng latLng1 = this.a.get(b1);
        new PointF();
        PointF pointF1 = this.i.b().a(latLng1);
        path.lineTo(pointF1.x, pointF1.y);
      } 
      Paint paint = new Paint();
      paint.setColor(b());
      paint.setAntiAlias(true);
      paint.setStyle(Paint.Style.FILL);
      path.close();
      paramCanvas.drawPath(path, paint);
      if (!a.a(a(), 0.0F)) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(d());
        paint.setStrokeWidth(a());
        paramCanvas.drawPath(path, paint);
      } 
    } 
  }
  
  public boolean equalsRemote(b paramb) {
    return (equals(paramb) || paramb.getId().equals(getId()));
  }
  
  public String getId() {
    if (this.h == null)
      this.h = a.a("Polygon"); 
    return this.h;
  }
  
  public float getZIndex() {
    return this.g;
  }
  
  public int hashCodeRemote() {
    return hashCode();
  }
  
  public boolean isVisible() {
    return this.e;
  }
  
  public void remove() {
    this.j.b(getId());
  }
  
  public void setVisible(boolean paramBoolean) {
    this.e = paramBoolean;
    this.i.a(false, false);
  }
  
  public void setZIndex(float paramFloat) {
    this.g = paramFloat;
    this.j.c();
    this.i.a(false, false);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\e\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */