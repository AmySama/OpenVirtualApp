package com.tencent.mapsdk.rastercore.a;

import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import com.tencent.mapsdk.rastercore.d.b;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.mapsdk.rastercore.d.f;
import com.tencent.tencentmap.mapsdk.map.CancelableCallback;

public abstract class a {
  protected e a;
  
  protected b b;
  
  protected CancelableCallback c;
  
  private a d = a.c;
  
  private Handler e = new Handler();
  
  private Scroller f;
  
  private long g;
  
  private float h = 0.0F;
  
  private boolean i = false;
  
  private double j = 0.0D;
  
  private Runnable k = new Runnable(this) {
      public final void run() {
        f f;
        boolean bool = a.a(this.a).computeScrollOffset();
        boolean bool1 = false;
        if (bool) {
          float f1 = a.a(this.a).getCurrX() * 1.0F / 10000.0F;
          float f2 = f1 - a.b(this.a);
          a.a(this.a, f2);
          if (a.c(this.a) < 1.0D)
            this.a.a(f2); 
          a.a(this.a, f1);
          if (a.d(this.a))
            a.f(this.a).postDelayed(a.e(this.a), 5L); 
          f = this.a.a.h();
        } else {
          this.a.d();
          if (this.a.c != null)
            this.a.c.onFinish(); 
          a.a(this.a, false);
          f = this.a.a.h();
          bool1 = true;
        } 
        f.a(bool1);
      }
    };
  
  public a(e parame, long paramLong, CancelableCallback paramCancelableCallback) {
    this.a = parame;
    this.b = parame.c();
    this.g = paramLong;
    this.c = paramCancelableCallback;
  }
  
  public final void a() {
    Scroller scroller;
    int i = null.a[this.d.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          scroller = new Scroller(e.a());
        } else {
          scroller = new Scroller(e.a(), (Interpolator)new AccelerateDecelerateInterpolator());
        } 
      } else {
        scroller = new Scroller(e.a(), (Interpolator)new DecelerateInterpolator());
      } 
    } else {
      scroller = new Scroller(e.a(), (Interpolator)new AccelerateInterpolator());
    } 
    this.f = scroller;
    c();
    this.i = true;
    this.f.startScroll(0, 0, 10000, 0, (int)this.g);
    this.e.postDelayed(this.k, 5L);
    this.a.a(false, false);
  }
  
  protected abstract void a(float paramFloat);
  
  public final void a(a parama) {
    this.d = parama;
  }
  
  public final void b() {
    if (this.i) {
      this.i = false;
      CancelableCallback cancelableCallback = this.c;
      if (cancelableCallback != null)
        cancelableCallback.onCancel(); 
      this.a.h().a(true);
    } 
  }
  
  protected abstract void c();
  
  protected abstract void d();
  
  public enum a {
    a, b, c;
    
    static {
      a a1 = new a("ACCELERATE_DECELERATE", 2);
      c = a1;
      d = new a[] { a, b, a1 };
    }
    
    public static a[] a() {
      return (a[])d.clone();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */