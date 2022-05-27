package com.tencent.mapsdk.rastercore.d;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.rastercore.a.a;
import com.tencent.mapsdk.rastercore.a.c;
import com.tencent.mapsdk.rastercore.b.a;
import com.tencent.mapsdk.rastercore.b.c;
import com.tencent.mapsdk.rastercore.c.a;
import com.tencent.mapsdk.rastercore.d;
import com.tencent.mapsdk.rastercore.f.a;
import com.tencent.tencentmap.mapsdk.map.CancelableCallback;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public final class b extends View {
  private e a;
  
  private c b;
  
  private boolean c = false;
  
  private volatile boolean d = false;
  
  private List<Runnable> e = new Vector<Runnable>();
  
  private a f;
  
  private c g = null;
  
  private PointF h = null;
  
  private TencentMap.OnMapLoadedListener i;
  
  private final Handler j = new Handler(this) {
      public final void handleMessage(Message param1Message) {
        int i = param1Message.what;
        if (i != 11) {
          if (i == 16)
            b.c(this.a).o(); 
        } else {
          Iterator<Runnable> iterator = b.a(this.a).iterator();
          while (iterator.hasNext())
            ((Runnable)iterator.next()).run(); 
          b.a(this.a).clear();
          if (b.b(this.a) != null)
            b.b(this.a).onMapLoaded(); 
        } 
      }
    };
  
  private c k;
  
  private com.tencent.mapsdk.rastercore.a.b l;
  
  public b(e parame) {
    super(e.a());
    this.a = parame;
    this.b = parame.b();
    this.f = new a(10.0D);
    if (parame.f().a() >= 3 && parame.f().b() > 1.0F)
      this.f.a(1.3D); 
    this.g = d.a.a(new LatLng(39.91669D, 116.39716D));
    this.h = new PointF((getWidth() / 2), (getHeight() / 2));
    a.a(this, 2);
  }
  
  private c a(int paramInt1, int paramInt2) {
    return new c(this.g.b() + paramInt1 * this.f.d(), this.g.a() - paramInt2 * this.f.d());
  }
  
  private void a(a parama, PointF paramPointF) {
    double d1 = getWidth() / 2.0D;
    double d2 = getHeight() / 2.0D;
    double d3 = this.g.b();
    double d4 = paramPointF.x;
    double d5 = this.f.d();
    double d6 = paramPointF.x;
    double d7 = parama.d();
    double d8 = this.g.a();
    double d9 = paramPointF.y;
    double d10 = this.f.d();
    double d11 = paramPointF.y;
    double d12 = parama.d();
    this.g.b(d3 + (d4 - d1) * d5 + (d1 - d6) * d7);
    this.g.a(d8 - (d9 - d2) * d10 + (d2 - d11) * d12);
    this.h.x = (int)d1;
    this.h.y = (int)d2;
    this.f.b(parama.c());
    this.b.a(this.g);
    this.a.a(false, false);
  }
  
  private void b(double paramDouble, PointF paramPointF, boolean paramBoolean, long paramLong, CancelableCallback paramCancelableCallback) {
    a(paramDouble, paramPointF, paramBoolean, paramLong, a.a.c, paramCancelableCallback);
  }
  
  private PointF e() {
    return new PointF((getWidth() / 2), (getHeight() / 2));
  }
  
  public final PointF a() {
    return this.h;
  }
  
  public final void a(double paramDouble, PointF paramPointF) {
    double d = this.f.b();
    a(this.b.a(new a(this.f.a(), d * paramDouble)), paramPointF);
  }
  
  public final void a(double paramDouble, PointF paramPointF, boolean paramBoolean, long paramLong, a.a parama, CancelableCallback paramCancelableCallback) {
    c c1;
    if (paramBoolean) {
      c c2 = this.k;
      if (c2 != null)
        c2.b(); 
      c1 = new c(this.a, paramDouble, paramPointF, paramLong, paramCancelableCallback);
      this.k = c1;
      c1.a(parama);
      this.k.a();
      return;
    } 
    a(this.b.a(new a(paramDouble)), (PointF)c1);
    if (paramCancelableCallback != null)
      paramCancelableCallback.onFinish(); 
  }
  
  public final void a(double paramDouble, PointF paramPointF, boolean paramBoolean, long paramLong, CancelableCallback paramCancelableCallback) {
    b(paramDouble, paramPointF, false, 0L, (CancelableCallback)null);
  }
  
  public final void a(double paramDouble, PointF paramPointF, boolean paramBoolean, CancelableCallback paramCancelableCallback) {
    b(this.f.c() + paramDouble, paramPointF, paramBoolean, 500L, paramCancelableCallback);
  }
  
  public final void a(double paramDouble, boolean paramBoolean, CancelableCallback paramCancelableCallback) {
    b(this.f.c() + paramDouble, e(), paramBoolean, 500L, paramCancelableCallback);
  }
  
  public final void a(int paramInt1, int paramInt2, long paramLong, CancelableCallback paramCancelableCallback) {
    a(a(paramInt1, paramInt2), paramLong, paramCancelableCallback);
  }
  
  public final void a(PointF paramPointF, boolean paramBoolean, CancelableCallback paramCancelableCallback) {
    b(this.f.c() + 1.0D, paramPointF, true, 500L, (CancelableCallback)null);
  }
  
  public final void a(c paramc) {
    this.g = paramc;
    this.b.a(paramc);
    this.a.a(false, false);
  }
  
  public final void a(c paramc, long paramLong, CancelableCallback paramCancelableCallback) {
    com.tencent.mapsdk.rastercore.a.b b2 = this.l;
    if (b2 != null)
      b2.b(); 
    com.tencent.mapsdk.rastercore.a.b b1 = new com.tencent.mapsdk.rastercore.a.b(this.a, paramc, paramLong, paramCancelableCallback);
    this.l = b1;
    b1.a();
  }
  
  public final void a(a parama) {
    if (!this.d && parama.a()) {
      this.e.add(new Runnable(this, parama) {
            public final void run() {
              this.a.a(b.c(this.b));
            }
          });
      return;
    } 
    parama.a(this.a);
  }
  
  public final void a(TencentMap.OnMapLoadedListener paramOnMapLoadedListener) {
    this.i = paramOnMapLoadedListener;
  }
  
  public final void a(boolean paramBoolean) {
    this.c = true;
  }
  
  public final void a(boolean paramBoolean, CancelableCallback paramCancelableCallback) {
    b(this.f.c() + 1.0D, e(), paramBoolean, 500L, paramCancelableCallback);
  }
  
  public final c b() {
    return this.g;
  }
  
  public final void b(double paramDouble, boolean paramBoolean, CancelableCallback paramCancelableCallback) {
    b(paramDouble, e(), paramBoolean, 500L, paramCancelableCallback);
  }
  
  public final void b(c paramc) {
    a(paramc);
  }
  
  public final void b(boolean paramBoolean, CancelableCallback paramCancelableCallback) {
    b(this.f.c() - 1.0D, e(), paramBoolean, 500L, paramCancelableCallback);
  }
  
  public final double c() {
    return this.f.c();
  }
  
  public final void clearAnimation() {
    super.clearAnimation();
    com.tencent.mapsdk.rastercore.a.b b1 = this.l;
    if (b1 != null)
      b1.b(); 
    c c1 = this.k;
    if (c1 != null)
      c1.b(); 
  }
  
  public final a d() {
    return this.f;
  }
  
  public final void draw(Canvas paramCanvas) {
    if (!this.d) {
      this.j.sendEmptyMessage(11);
      this.d = true;
    } 
    boolean bool = this.a.g().a(paramCanvas);
    this.a.e().a(paramCanvas);
    if (!e.q()) {
      Paint paint = new Paint();
      paint.setColor(-65536);
      paint.setTextSize(50.0F);
      Rect rect = new Rect();
      int i = getWidth();
      int j = getHeight();
      paint.getTextBounds("鉴权失败，请到腾讯", 0, 9, rect);
      i /= 2;
      float f = (i - rect.width() / 2);
      j /= 2;
      paramCanvas.drawText("鉴权失败，请到腾讯", f, (j - rect.height()), paint);
      paint.getTextBounds("地图官网申请密钥", 0, 8, rect);
      paramCanvas.drawText("地图官网申请密钥", (i - rect.width() / 2), (j + 2), paint);
    } 
    this.a.b(bool);
    if (this.c && bool) {
      this.c = false;
      this.j.sendEmptyMessage(16);
    } 
  }
  
  protected final void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.b.g() != 0.0D && this.b.h() != 0.0D) {
      c c1 = this.b;
      c1.b(c1.g(), this.b.h());
      this.b.a(0);
      this.b.b(0);
    } 
    this.h = new PointF((getWidth() / 2), (getHeight() / 2));
    paramInt1 = a.a;
    paramInt2 = Math.max(getWidth(), getHeight());
    while (Math.pow(2.0D, paramInt1) * 256.0D < paramInt2)
      paramInt1++; 
    a.a = paramInt1;
    if (this.b.j().a() < a.a)
      this.b.d(a.a); 
    this.a.a(false, false);
  }
  
  public final void scrollBy(int paramInt1, int paramInt2) {
    a(a(paramInt1, paramInt2));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\d\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */