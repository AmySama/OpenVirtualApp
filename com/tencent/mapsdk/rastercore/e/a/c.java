package com.tencent.mapsdk.rastercore.e.a;

import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import com.tencent.mapsdk.raster.model.BitmapDescriptor;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.mapsdk.rastercore.d.f;
import com.tencent.tencentmap.mapsdk.map.MapView;

public final class c {
  private static int a;
  
  private static BitmapDescriptor b;
  
  private Animation A;
  
  private int B = 25;
  
  private Object C;
  
  private e c;
  
  private Context d;
  
  private f e;
  
  private MapView f;
  
  private View g;
  
  private View h;
  
  private Animation i = null;
  
  private Animation j = null;
  
  private GestureDetector k;
  
  private BitmapDescriptor l = null;
  
  private boolean m = false;
  
  private boolean n = false;
  
  private boolean o = false;
  
  private boolean p = false;
  
  private float q = 0.5F;
  
  private float r = 1.0F;
  
  private boolean s = true;
  
  private float t = 0.0F;
  
  private float u = 1.0F;
  
  private String v;
  
  private LatLng w;
  
  private String x;
  
  private String y;
  
  private Animation z;
  
  public c(e parame, MarkerOptions paramMarkerOptions) {
    this.c = parame;
    this.d = e.a();
    this.f = parame.d();
    this.e = parame.h();
    this.n = paramMarkerOptions.isGps();
    if (paramMarkerOptions.getPosition() != null) {
      if (this.n)
        try {
          double[] arrayOfDouble = e.a(paramMarkerOptions.getPosition().getLongitude(), paramMarkerOptions.getPosition().getLatitude());
        } catch (Exception exception) {
          paramMarkerOptions.getPosition();
        }  
      this.w = paramMarkerOptions.getPosition();
    } 
    a(paramMarkerOptions.getIcon());
    this.g = paramMarkerOptions.getMarkerView();
    this.q = paramMarkerOptions.getAnchorU();
    this.r = paramMarkerOptions.getAnchorV();
    this.u = paramMarkerOptions.getAlpha();
    this.t = paramMarkerOptions.getRotation();
    this.s = paramMarkerOptions.isVisible();
    this.v = paramMarkerOptions.getSnippet();
    this.x = paramMarkerOptions.getTitle();
    this.m = paramMarkerOptions.isDraggable();
    this.y = m();
    this.j = paramMarkerOptions.getInfoWindowHideAnimation();
    this.i = paramMarkerOptions.getInfoWindowShowAnimation();
    this.z = paramMarkerOptions.getShowingAnination();
    this.A = paramMarkerOptions.getHidingAnination();
    this.C = paramMarkerOptions.getTag();
    this.B = (int)(this.B * (this.d.getResources().getDisplayMetrics()).density);
    this.k = new GestureDetector(this.d, new GestureDetector.OnGestureListener(this) {
          public final boolean onDown(MotionEvent param1MotionEvent) {
            return true;
          }
          
          public final boolean onFling(MotionEvent param1MotionEvent1, MotionEvent param1MotionEvent2, float param1Float1, float param1Float2) {
            return false;
          }
          
          public final void onLongPress(MotionEvent param1MotionEvent) {
            if (c.b(this.a)) {
              float f1 = c.a(this.a).c().getX();
              float f2 = c.a(this.a).c().getY();
              c.a(this.a, true);
              c c1 = this.a;
              c.b(c1, c1.e());
              if (c.e(this.a))
                this.a.g(); 
              c1 = this.a;
              c.a(c1, f1, f2 - c.f(c1));
              if (c.a(this.a).e() != null)
                c.a(this.a).e().onMarkerDragStart(new Marker(this.a)); 
            } 
          }
          
          public final boolean onScroll(MotionEvent param1MotionEvent1, MotionEvent param1MotionEvent2, float param1Float1, float param1Float2) {
            if (!c.b(this.a) || !c.c(this.a)) {
              c.d(this.a).requestDisallowInterceptTouchEvent(false);
              return false;
            } 
            return true;
          }
          
          public final void onShowPress(MotionEvent param1MotionEvent) {}
          
          public final boolean onSingleTapUp(MotionEvent param1MotionEvent) {
            this.a.n();
            if (c.a(this.a).d() == null) {
              if (this.a.e()) {
                this.a.g();
              } else {
                this.a.f();
              } 
              return true;
            } 
            return c.a(this.a).d().onMarkerClick(new Marker(this.a));
          }
        });
    q();
  }
  
  private static boolean a(float paramFloat1, float paramFloat2, float paramFloat3) {
    return (paramFloat1 > paramFloat2 && paramFloat1 <= paramFloat3);
  }
  
  private void q() {
    if (this.g == null) {
      if (this.l == null) {
        if (b == null)
          b = BitmapDescriptorFactory.defaultMarker(); 
        this.l = b;
      } 
      ImageView imageView = new ImageView(this.d);
      this.g = (View)imageView;
      imageView.setImageBitmap(this.l.getBitmap());
    } 
    this.g.measure(0, 0);
    b(this.u);
    a(this.t);
    MapView.LayoutParams layoutParams = s();
    this.f.addView(this.g, (ViewGroup.LayoutParams)layoutParams);
    if (this.s && this.z != null) {
      this.g.clearAnimation();
      this.z.setAnimationListener(new a(this) {
            public final void onAnimationEnd(Animation param1Animation) {
              c.g(this.a).setAnimationListener(null);
              c.h(this.a);
            }
          });
      this.g.startAnimation(this.z);
      return;
    } 
    r();
  }
  
  private void r() {
    this.g.setOnTouchListener(new View.OnTouchListener(this) {
          public final boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
            int i = param1MotionEvent.getAction();
            if (i != 1)
              if (i != 2) {
                if (i != 3)
                  return c.i(this.a).onTouchEvent(param1MotionEvent); 
              } else {
                if (c.b(this.a) && c.c(this.a)) {
                  float f1 = c.a(this.a).c().getX();
                  float f2 = c.a(this.a).c().getY();
                  c c1 = this.a;
                  c.a(c1, f1, f2 - c.f(c1));
                  StringBuilder stringBuilder = new StringBuilder();
                  stringBuilder.append(f1);
                  stringBuilder.append(",");
                  stringBuilder.append(f2);
                  if (c.a(this.a).e() != null)
                    c.a(this.a).e().onMarkerDrag(new Marker(this.a)); 
                  return true;
                } 
                return c.i(this.a).onTouchEvent(param1MotionEvent);
              }  
            if (c.b(this.a) && c.c(this.a)) {
              c.a(this.a, false);
              this.a.n();
              (new StringBuilder()).append(c.e(this.a));
              if (c.e(this.a)) {
                this.a.f();
                c.b(this.a, false);
              } 
              if (c.a(this.a).e() != null)
                c.a(this.a).e().onMarkerDragEnd(new Marker(this.a)); 
            } 
            return c.i(this.a).onTouchEvent(param1MotionEvent);
          }
        });
  }
  
  private MapView.LayoutParams s() {
    PointF pointF = t();
    return new MapView.LayoutParams(-2, -2, this.w, -((int)pointF.x), -((int)pointF.y), 0);
  }
  
  private PointF t() {
    PointF pointF = new PointF();
    pointF.x = this.g.getMeasuredWidth() * this.q;
    pointF.y = this.g.getMeasuredHeight() * this.r;
    return pointF;
  }
  
  private PointF u() {
    PointF pointF2;
    PointF pointF1 = new PointF();
    if (a(this.t, 45.0F, 135.0F)) {
      pointF2 = new PointF(0.0F, this.g.getMeasuredHeight() * 0.5F);
    } else if (a(this.t, 135.0F, 225.0F)) {
      pointF2 = new PointF(this.g.getMeasuredWidth() * 0.5F, this.g.getMeasuredHeight());
    } else if (a(this.t, 225.0F, 315.0F)) {
      pointF2 = new PointF(this.g.getMeasuredWidth(), this.g.getMeasuredHeight() * 0.5F);
    } else {
      pointF2 = new PointF(this.g.getMeasuredWidth() * 0.5F, 0.0F);
    } 
    PointF pointF3 = t();
    float f1 = pointF2.x - pointF3.x;
    float f2 = -pointF2.y + pointF3.y;
    float f3 = (float)Math.sqrt((f1 * f1 + f2 * f2));
    double d1 = Math.atan((f1 / f2));
    if (f1 <= 0.0F || f2 >= 0.0F) {
      double d = d1;
      if (f1 < 0.0F) {
        d = d1;
        if (f2 < 0.0F) {
          d = d1 + Math.PI;
          d += (this.t / 180.0F) * Math.PI;
          d1 = f3;
          pointF1.x = (float)(Math.sin(d) * d1);
          pointF1.y = -((float)(d1 * Math.cos(d)));
          return pointF1;
        } 
      } 
      d += (this.t / 180.0F) * Math.PI;
      d1 = f3;
      pointF1.x = (float)(Math.sin(d) * d1);
      pointF1.y = -((float)(d1 * Math.cos(d)));
      return pointF1;
    } 
    double d2 = d1 + Math.PI;
    d2 += (this.t / 180.0F) * Math.PI;
    d1 = f3;
    pointF1.x = (float)(Math.sin(d2) * d1);
    pointF1.y = -((float)(d1 * Math.cos(d2)));
    return pointF1;
  }
  
  private void v() {
    if (e()) {
      this.h.clearAnimation();
      this.f.removeView(this.h);
      PointF pointF = u();
      MapView.LayoutParams layoutParams = new MapView.LayoutParams(-2, -2, this.w, (int)pointF.x, (int)pointF.y, 81);
      int i = this.f.indexOfChild(this.g);
      this.f.addView(this.h, i + 1, (ViewGroup.LayoutParams)layoutParams);
    } 
  }
  
  private void w() {
    if (e()) {
      View view = this.h;
      if (view instanceof b) {
        ((b)view).a.setText(this.x);
        ((b)this.h).b.setText(this.v);
      } 
    } 
  }
  
  public final View a() {
    return this.g;
  }
  
  public final void a(float paramFloat) {
    paramFloat = (paramFloat + 360.0F) % 360.0F;
    if (Build.VERSION.SDK_INT < 11) {
      RotateAnimation rotateAnimation = new RotateAnimation(this.t, paramFloat, 1, this.q, 1, this.r);
      rotateAnimation.setFillAfter(true);
      rotateAnimation.setDuration(0L);
      this.g.clearAnimation();
      this.g.startAnimation((Animation)rotateAnimation);
    } else {
      PointF pointF = t();
      this.g.setPivotX(pointF.x);
      this.g.setPivotY(pointF.y);
      this.g.setRotation(paramFloat);
    } 
    this.t = paramFloat;
    v();
  }
  
  public final void a(float paramFloat1, float paramFloat2) {
    if (this.q == paramFloat1 && this.r == paramFloat2)
      return; 
    this.q = paramFloat1;
    this.r = paramFloat2;
    this.g.setLayoutParams((ViewGroup.LayoutParams)s());
    a(this.t);
    v();
  }
  
  public final void a(View paramView) {
    View view = this.g;
    if (view != null) {
      Animation animation = this.A;
      if (animation != null) {
        animation.setAnimationListener(new a(this, paramView) {
              public final void onAnimationEnd(Animation param1Animation) {
                (new Handler()).post(new Runnable(this) {
                      public final void run() {
                        c.d(this.a.b).removeView(c.j(this.a.b));
                        c.k(this.a.b).setAnimationListener(null);
                        c.a(this.a.b, this.a.a);
                        c.l(this.a.b);
                        c.m(this.a.b);
                      }
                    });
              }
            });
        this.g.setOnTouchListener(null);
        this.g.startAnimation(this.A);
        return;
      } 
      this.f.removeView(view);
    } 
    this.g = paramView;
    q();
    v();
  }
  
  public final void a(Animation paramAnimation) {
    this.i = paramAnimation;
  }
  
  public final void a(BitmapDescriptor paramBitmapDescriptor) {
    this.l = paramBitmapDescriptor;
    View view = this.g;
    if (view != null && view instanceof ImageView) {
      ((ImageView)view).setImageBitmap(paramBitmapDescriptor.getBitmap());
      this.g.measure(0, 0);
      MapView.LayoutParams layoutParams = s();
      this.g.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    } 
  }
  
  public final void a(LatLng paramLatLng) {
    this.w = paramLatLng;
    ((MapView.LayoutParams)this.g.getLayoutParams()).setPoint(paramLatLng);
    this.f.layout(this.g);
    View view = this.h;
    if (view != null) {
      ((MapView.LayoutParams)view.getLayoutParams()).setPoint(paramLatLng);
      this.f.layout(this.h);
    } 
  }
  
  public final void a(Object paramObject) {
    this.C = paramObject;
  }
  
  public final void a(String paramString) {
    this.v = paramString;
    w();
  }
  
  public final void a(boolean paramBoolean) {
    byte b;
    View view = this.g;
    if (paramBoolean) {
      b = 0;
    } else {
      b = 8;
    } 
    view.setVisibility(b);
    if (!paramBoolean && e())
      g(); 
    this.s = paramBoolean;
  }
  
  public final void b() {
    this.c.e().c(m());
  }
  
  public final void b(float paramFloat) {
    float f1;
    if (paramFloat < 0.0F) {
      f1 = 0.0F;
    } else {
      f1 = paramFloat;
      if (paramFloat > 1.0F)
        f1 = 1.0F; 
    } 
    if (Build.VERSION.SDK_INT < 11) {
      AlphaAnimation alphaAnimation = new AlphaAnimation(this.u, f1);
      alphaAnimation.setFillAfter(true);
      alphaAnimation.setDuration(0L);
      this.g.clearAnimation();
      this.g.startAnimation((Animation)alphaAnimation);
    } else {
      this.g.setAlpha(f1);
    } 
    this.u = f1;
  }
  
  public final void b(Animation paramAnimation) {
    this.j = paramAnimation;
  }
  
  public final void b(String paramString) {
    this.x = paramString;
    w();
  }
  
  public final void b(boolean paramBoolean) {
    this.m = paramBoolean;
  }
  
  public final void c() {
    Animation animation = this.A;
    if (animation != null) {
      animation.setAnimationListener(new a(this) {
            public final void onAnimationEnd(Animation param1Animation) {
              (new Handler()).post(new Runnable(this) {
                    public final void run() {
                      c.d(this.a.a).removeView(c.j(this.a.a));
                      c.d(this.a.a).removeView(c.n(this.a.a));
                      c.k(this.a.a).setAnimationListener(null);
                    }
                  });
            }
          });
      this.g.setOnTouchListener(null);
      this.g.clearAnimation();
      this.g.startAnimation(this.A);
      return;
    } 
    this.f.removeView(this.g);
    this.f.removeView(this.h);
  }
  
  public final boolean d() {
    return this.m;
  }
  
  public final boolean e() {
    View view = this.h;
    return (view != null && view.getParent() != null);
  }
  
  public final boolean equals(Object paramObject) {
    return (paramObject == null) ? false : (!(paramObject instanceof c) ? false : ((paramObject.hashCode() != hashCode()) ? false : (!!((c)paramObject).m().equals(m()))));
  }
  
  public final void f() {
    b b;
    AnimationSet animationSet;
    if (e())
      return; 
    if (this.e.g() != null) {
      Marker marker = new Marker(this);
      View view2 = this.e.g().getInfoWindow(marker);
      View view1 = view2;
      if (view2 == null)
        b = new b(this.d, this.x, this.v); 
    } else {
      b = new b(this.d, this.x, this.v);
    } 
    this.h = (View)b;
    t();
    PointF pointF = u();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(pointF.x);
    stringBuilder.append(", ");
    stringBuilder.append(pointF.y);
    MapView.LayoutParams layoutParams = new MapView.LayoutParams(-2, -2, this.w, (int)pointF.x, (int)pointF.y, 81);
    int i = this.f.indexOfChild(this.g);
    this.f.addView(this.h, i + 1, (ViewGroup.LayoutParams)layoutParams);
    Animation animation = this.i;
    if (animation == null) {
      animationSet = new AnimationSet(false);
      ScaleAnimation scaleAnimation1 = new ScaleAnimation(0.0F, 1.1F, 0.0F, 1.1F, 1, 0.5F, 1, 1.0F);
      scaleAnimation1.setDuration(150L);
      scaleAnimation1.setInterpolator((Interpolator)new AccelerateInterpolator());
      ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.0F, 0.9F, 1.0F, 0.9F, 1, 0.5F, 1, 1.0F);
      scaleAnimation2.setDuration(100L);
      scaleAnimation2.setInterpolator((Interpolator)new DecelerateInterpolator());
      scaleAnimation2.setStartOffset(150L);
      animationSet.addAnimation((Animation)scaleAnimation1);
      animationSet.addAnimation((Animation)scaleAnimation2);
    } 
    this.h.startAnimation((Animation)animationSet);
    this.h.setOnClickListener(new View.OnClickListener(this) {
          public final void onClick(View param1View) {
            if (c.a(this.a).f() != null)
              c.a(this.a).f().onInfoWindowClick(new Marker(this.a)); 
          }
        });
  }
  
  public final void g() {
    AnimationSet animationSet;
    if (!e())
      return; 
    this.h.clearAnimation();
    Animation animation = this.j;
    if (animation == null) {
      animationSet = new AnimationSet(true);
      animationSet.setInterpolator((Interpolator)new AccelerateInterpolator());
      animationSet.addAnimation((Animation)new AlphaAnimation(1.0F, 0.0F));
      animationSet.addAnimation((Animation)new ScaleAnimation(1.0F, 0.0F, 1.0F, 0.0F, 1, 0.5F, 1, 1.0F));
      animationSet.setDuration(100L);
    } 
    animationSet.setAnimationListener(new a(this) {
          public final void onAnimationEnd(Animation param1Animation) {
            (new Handler()).post(new Runnable(this) {
                  public final void run() {
                    c.d(this.a.a).removeView(c.n(this.a.a));
                    if (c.a(this.a.a).g() != null)
                      c.o(this.a.a).h().g().onInfoWindowDettached(new Marker(this.a.a), c.n(this.a.a)); 
                    c.b(this.a.a, (View)null);
                  }
                });
          }
        });
    this.h.startAnimation((Animation)animationSet);
  }
  
  public final String h() {
    return this.x;
  }
  
  public final int hashCode() {
    return m().hashCode();
  }
  
  public final String i() {
    return this.v;
  }
  
  public final float j() {
    return this.t;
  }
  
  public final boolean k() {
    return this.s;
  }
  
  public final LatLng l() {
    return this.w;
  }
  
  public final String m() {
    if (this.y == null) {
      a++;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Marker");
      stringBuilder.append(a);
      this.y = stringBuilder.toString();
    } 
    return this.y;
  }
  
  public final void n() {
    if (Build.VERSION.SDK_INT < 11)
      this.g.clearAnimation(); 
    this.f.removeView(this.g);
    this.f.addView(this.g);
    v();
  }
  
  public final float o() {
    return this.u;
  }
  
  public final Object p() {
    return this.C;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\e\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */