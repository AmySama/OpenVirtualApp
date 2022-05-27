package com.tencent.mapsdk.rastercore.d;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.stub.StubApp;
import com.tencent.mapsdk.raster.model.QMapLanguage;
import com.tencent.mapsdk.rastercore.a;
import com.tencent.mapsdk.rastercore.b;
import com.tencent.mapsdk.rastercore.b.c;
import com.tencent.mapsdk.rastercore.d;
import com.tencent.mapsdk.rastercore.g.a;
import com.tencent.mapsdk.rastercore.g.b;
import com.tencent.mapsdk.rastercore.tile.MapTile;
import com.tencent.mapsdk.rastercore.tile.a;
import com.tencent.mapsdk.rastercore.tile.a.a;
import com.tencent.mapsdk.rastercore.tile.f;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import java.net.HttpURLConnection;
import java.net.URL;

public final class e implements d.b {
  private static volatile int A = 0;
  
  private static volatile int B = 0;
  
  private static volatile int C = 0;
  
  private static volatile int D = 0;
  
  private static volatile int E = 0;
  
  private static volatile String F;
  
  public static int a = 0;
  
  public static int b = 0;
  
  public static int c = 0;
  
  public static int d = 0;
  
  public static int e = 0;
  
  public static int f = 0;
  
  public static int g = 0;
  
  public static int h = 0;
  
  private static volatile Context i;
  
  private static boolean w = true;
  
  private static boolean y;
  
  private static volatile int z = b.a;
  
  private MapView j;
  
  private a k;
  
  private b l;
  
  private f m;
  
  private a.null n;
  
  private f o;
  
  private c p;
  
  private a.null q;
  
  private volatile b r;
  
  private a s;
  
  private int t;
  
  private TencentMap.OnScreenShotListener u;
  
  private boolean v;
  
  private Rect x;
  
  static {
    A = b.c;
    B = 1000;
    C = 1000;
    D = b.d;
    E = b.b;
    F = QMapLanguage.getLanguageCode(QMapLanguage.QMapLanguage_en);
  }
  
  public e(MapView paramMapView) {
    boolean bool;
    this.t = 1;
    this.u = null;
    this.v = false;
    this.x = null;
    Context context = StubApp.getOrigApplicationContext(paramMapView.getContext().getApplicationContext());
    i = context;
    if (context == null) {
      bool = false;
    } else {
      bool = i.getSharedPreferences("mapsdk_pref", 0).getBoolean("worldEnable", false);
    } 
    y = bool;
    a.a().a(i);
    if (i != null) {
      C = d.a.a().a(i.getPackageName(), false);
      z = d.a.a().a(C, 0);
      (new StringBuilder("CurrentVersion in MapContext:")).append(z);
      B = d.a.a().a(i.getPackageName(), true);
      A = d.a.a().a(B, 2);
      D = d.a.a().a(-1, 3);
      E = d.a.a().a(-1, 1);
      (new Thread(this) {
          public final void run() {
            a.a().a(MapTile.MapSource.BING, e.z());
            a.a().a(MapTile.MapSource.SATELLITE, e.A());
            a.a().c();
          }
        }).start();
    } 
    this.j = paramMapView;
    this.q = (a.null)new Object(this);
    this.r = new b(this);
    this.p = new c(this);
    this.s = new a(this);
    this.k = new a(this);
    this.l = new b(this);
    this.m = new f(this);
    this.n = (a.null)new Object(this, z, A, D);
    this.o = new f(this);
    this.p.a();
    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
    paramMapView.addView(this.l, layoutParams);
    paramMapView.addView((View)this.s, layoutParams);
    paramMapView.addView((View)this.r, layoutParams);
    this.q.b(1);
    this.q.a(true);
    this.q.c(0);
    a.a(this);
    (new d(i, this)).a();
  }
  
  public static Context a() {
    return i;
  }
  
  public static void a(String paramString) {
    F = paramString;
  }
  
  public static void c(boolean paramBoolean) {
    w = false;
  }
  
  public static void d(boolean paramBoolean) {
    y = paramBoolean;
  }
  
  public static void e(boolean paramBoolean) {
    if (i == null)
      return; 
    SharedPreferences.Editor editor = i.getSharedPreferences("mapsdk_pref", 0).edit();
    editor.putBoolean("worldEnable", paramBoolean);
    editor.commit();
  }
  
  public static void n() {}
  
  public static boolean q() {
    return w;
  }
  
  public static boolean r() {
    return y;
  }
  
  public static int s() {
    return A;
  }
  
  public static int t() {
    return B;
  }
  
  public static String u() {
    return F;
  }
  
  public static int v() {
    return z;
  }
  
  public static int w() {
    return D;
  }
  
  public static int x() {
    return E;
  }
  
  public static int y() {
    return C;
  }
  
  public final void a(int paramInt) {
    if (paramInt == 2) {
      this.r.a(true);
    } else {
      this.r.a(false);
    } 
    this.t = paramInt;
    a(false, false);
  }
  
  public final void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Bitmap paramBitmap) {
    C = paramInt1;
    z = paramInt2;
    (new StringBuilder("CurrentVersion in Update:")).append(z);
    A = paramInt4;
    B = paramInt3;
    D = paramInt5;
    E = paramInt6;
    this.n.a(paramInt2);
    this.n.b(paramInt4);
    this.n.c(paramInt5);
    if (this.r != null)
      this.r.a(paramBitmap); 
  }
  
  public final void a(Bundle paramBundle) {
    if (paramBundle != null) {
      this.q.e(paramBundle.getBoolean("ANIMATION_ENABLED", true));
      this.q.b(paramBundle.getBoolean("SCROLL_ENABLED", true));
      this.q.c(paramBundle.getBoolean("ZOOM_ENABLED", true));
      this.q.b(paramBundle.getInt("LOGO_POSITION", 0));
      this.q.c(paramBundle.getInt("SCALEVIEW_POSITION", 0));
      this.q.a(paramBundle.getBoolean("SCALE_CONTROLL_ENABLED", true));
      b b1 = this.l;
      b1.b(paramBundle.getDouble("ZOOM", b1.c()), false, null);
      Double double_2 = Double.valueOf(paramBundle.getDouble("CENTERX", Double.NaN));
      Double double_1 = Double.valueOf(paramBundle.getDouble("CENTERY", Double.NaN));
      if (!double_2.isNaN() && !double_1.isNaN())
        this.l.a(new c(double_2.doubleValue(), double_1.doubleValue())); 
    } 
  }
  
  public final void a(TencentMap.OnScreenShotListener paramOnScreenShotListener) {
    a(paramOnScreenShotListener, (Rect)null);
  }
  
  public final void a(TencentMap.OnScreenShotListener paramOnScreenShotListener, Rect paramRect) {
    this.u = paramOnScreenShotListener;
    this.x = paramRect;
    if (this.v) {
      o();
      return;
    } 
    this.l.a(true);
    a(false, false);
  }
  
  public final void a(boolean paramBoolean) {
    if (paramBoolean) {
      this.s.setVisibility(0);
      this.s.d();
      return;
    } 
    a.b();
    a.c();
    this.s.setVisibility(8);
  }
  
  public final void a(boolean paramBoolean1, boolean paramBoolean2) {
    this.v = false;
    this.n.a(paramBoolean1, paramBoolean2);
    this.j.layout();
    this.j.postInvalidate();
  }
  
  public final c b() {
    return this.p;
  }
  
  public final void b(int paramInt) {
    if (this.r != null) {
      this.r.a(paramInt);
      this.r.invalidate();
      if (this.s.getVisibility() == 0)
        this.s.invalidate(); 
    } 
  }
  
  public final void b(Bundle paramBundle) {
    paramBundle.putBoolean("ANIMATION_ENABLED", this.q.k());
    paramBundle.putBoolean("SCROLL_ENABLED", this.q.h());
    paramBundle.putBoolean("ZOOM_ENABLED", this.q.i());
    paramBundle.putInt("LOGO_POSITION", this.q.j());
    paramBundle.putInt("SCALEVIEW_POSITION", this.q.f());
    paramBundle.putBoolean("SCALE_CONTROLL_ENABLED", this.q.g());
    paramBundle.putDouble("ZOOM", this.l.c());
    paramBundle.putDouble("CENTERX", this.l.b().b());
    paramBundle.putDouble("CENTERY", this.l.b().a());
  }
  
  protected final void b(boolean paramBoolean) {
    this.v = paramBoolean;
  }
  
  public final b c() {
    return this.l;
  }
  
  public final void c(int paramInt) {
    a a1 = this.s;
    if (a1 != null && a1.getVisibility() == 0) {
      this.s.a(paramInt);
      this.s.invalidate();
    } 
  }
  
  public final MapView d() {
    return this.j;
  }
  
  public final a e() {
    return this.k;
  }
  
  public final a.null f() {
    return this.q;
  }
  
  public final a.null g() {
    return this.n;
  }
  
  public final f h() {
    return this.m;
  }
  
  public final f i() {
    return this.o;
  }
  
  public final void j() {
    this.s.e();
  }
  
  public final void k() {
    this.s.d();
  }
  
  public final int l() {
    return this.t;
  }
  
  public final void m() {
    this.s.a();
    this.r.a();
    this.k.b();
    this.j.stopAnimation();
    this.j.removeAllViews();
    this.n.a();
    a.a().d();
    (new Thread(this) {
        public final void run() {
          HttpURLConnection httpURLConnection = null;
          try {
            StringBuilder stringBuilder1 = new StringBuilder();
            this();
            stringBuilder1.append(Integer.toString(e.c));
            stringBuilder1.append(",");
            stringBuilder1.append(Integer.toString(e.d));
            String str1 = stringBuilder1.toString();
            StringBuilder stringBuilder2 = new StringBuilder();
            this();
            stringBuilder2.append(Integer.toString(e.a));
            stringBuilder2.append(",");
            stringBuilder2.append(Integer.toString(e.b));
            String str2 = stringBuilder2.toString();
            StringBuilder stringBuilder3 = new StringBuilder();
            this();
            stringBuilder3.append(Integer.toString(e.e));
            stringBuilder3.append(",");
            stringBuilder3.append(Integer.toString(0));
            String str3 = stringBuilder3.toString();
            StringBuilder stringBuilder4 = new StringBuilder();
            this();
            stringBuilder4.append(Integer.toString(e.f));
            stringBuilder4.append(",");
            stringBuilder4.append(Integer.toString(0));
            String str4 = stringBuilder4.toString();
            stringBuilder4 = new StringBuilder();
            this("https://pr.map.qq.com/pingd?");
            stringBuilder4.append(a.a.toString());
            stringBuilder4.append("&appid=sdk&logid=ditu&miss=");
            stringBuilder4.append(str1);
            stringBuilder4.append("&hit=");
            stringBuilder4.append(str2);
            stringBuilder4.append("&keep=");
            stringBuilder4.append(str3);
            stringBuilder4.append("&change=");
            stringBuilder4.append(str4);
            str2 = stringBuilder4.toString();
            URL uRL = new URL();
            this(str2);
          } catch (Exception exception) {
          
          } finally {
            httpURLConnection = null;
            if (httpURLConnection != null)
              httpURLConnection.disconnect(); 
          } 
          if (SYNTHETIC_LOCAL_VARIABLE_2 != null)
            SYNTHETIC_LOCAL_VARIABLE_2.disconnect(); 
        }
      }).start();
    System.gc();
  }
  
  protected final void o() {
    if (this.u != null) {
      Bitmap bitmap;
      this.j.setDrawingCacheEnabled(true);
      this.j.buildDrawingCache();
      if (this.x == null) {
        bitmap = Bitmap.createBitmap(this.j.getDrawingCache());
      } else {
        bitmap = Bitmap.createBitmap(this.j.getDrawingCache(), this.x.left, this.x.top, this.x.width(), this.x.height());
      } 
      this.j.destroyDrawingCache();
      this.u.onMapScreenShot(bitmap);
    } 
  }
  
  public final void p() {
    if (this.r != null)
      this.r.invalidate(); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\d\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */