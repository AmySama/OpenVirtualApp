package com.tencent.mapsdk.rastercore.g;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import com.tencent.mapsdk.rastercore.d.e;

public final class a extends View {
  private e a;
  
  private final int[] b = new int[] { 
      12000000, 6000000, 3000000, 1500000, 800000, 400000, 200000, 100000, 50000, 25000, 
      12000, 6000, 3000, 1500, 800, 400, 200, 100, 50, 25, 
      17, 8, 4 };
  
  private int c = 0;
  
  private String d = "";
  
  private int e = 0;
  
  private int f = 0;
  
  private Paint g;
  
  private Paint h;
  
  private Paint i;
  
  private Rect j;
  
  private String k;
  
  private int l = 10;
  
  private int m = 0;
  
  private int n = 10;
  
  private double o = 80.0D;
  
  private int p = 0;
  
  private int q = 0;
  
  private int r = 0;
  
  private Paint s;
  
  private AlphaAnimation t = null;
  
  private AlphaAnimation u = null;
  
  public a(e parame) {
    super(e.a());
    this.a = parame;
    Paint paint = new Paint();
    this.h = paint;
    paint.setAntiAlias(true);
    this.h.setColor(-16777216);
    this.h.setStrokeWidth(parame.f().b() * 6.0F);
    this.h.setStyle(Paint.Style.STROKE);
    paint = new Paint();
    this.i = paint;
    paint.setAntiAlias(true);
    this.i.setColor(Color.rgb(100, 100, 100));
    this.i.setStrokeWidth(parame.f().b() * 10.0F);
    this.i.setStyle(Paint.Style.STROKE);
    paint = new Paint();
    this.s = paint;
    paint.setAntiAlias(true);
    this.s.setColor(Color.rgb(255, 255, 255));
    this.s.setStrokeWidth(parame.f().b() * 7.0F);
    this.s.setStyle(Paint.Style.STROKE);
    paint = new Paint();
    this.g = paint;
    paint.setAntiAlias(true);
    this.g.setColor(-16777216);
    this.g.setTextSize(parame.f().b() * 25.0F);
    this.j = new Rect();
  }
  
  public static void b() {}
  
  public static void c() {}
  
  public final void a() {
    clearAnimation();
    this.h = null;
    this.g = null;
    this.j = null;
    this.d = null;
    this.i = null;
  }
  
  public final void a(int paramInt) {
    this.c = paramInt;
  }
  
  public final void d() {
    if (!isShown())
      return; 
    clearAnimation();
    AlphaAnimation alphaAnimation = new AlphaAnimation(1.0F, 0.0F);
    this.u = alphaAnimation;
    alphaAnimation.setDuration(2000L);
    this.u.setFillAfter(true);
    startAnimation((Animation)this.u);
  }
  
  public final void e() {
    clearAnimation();
    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0F, 1.0F);
    this.t = alphaAnimation;
    alphaAnimation.setDuration(500L);
    this.t.setFillAfter(true);
    startAnimation((Animation)this.t);
  }
  
  protected final void onDraw(Canvas paramCanvas) {
    Paint paint1;
    float f3;
    float f4;
    double d = this.a.b().e();
    int i = this.a.c().d().a();
    int j = this.a.c().getWidth();
    this.q = j;
    if (j - this.n * 2.0D - this.o < 0.0D) {
      i = 0;
    } else {
      double d1;
      String str1;
      i = this.b[i];
      d = i / d;
      while (true) {
        j = i;
        d1 = d;
        if (d < this.q / 5.0D) {
          d *= 2.0D;
          i *= 2;
          continue;
        } 
        break;
      } 
      while (d1 > this.q - this.n * 2.0D - this.o) {
        d1 /= 2.0D;
        j = (int)(j / 2.0D);
      } 
      this.f = (int)d1;
      if (j > 2000) {
        j /= 1000;
        str1 = "km";
      } else {
        str1 = "m";
      } 
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(j);
      stringBuilder2.append(str1);
      this.d = stringBuilder2.toString();
      StringBuilder stringBuilder1 = new StringBuilder();
      i = j / 2;
      stringBuilder1.append(i);
      this.k = stringBuilder1.toString();
      this.e = i;
      i = 1;
    } 
    if (i == 0)
      return; 
    if (this.d.equals("") || this.f == 0)
      return; 
    Paint paint2 = this.g;
    String str = this.d;
    paint2.getTextBounds(str, 0, str.length(), this.j);
    this.p = (int)((getHeight() - 8) - this.h.getStrokeWidth());
    i = this.a.c().getWidth();
    this.q = i;
    j = this.c;
    if (j == 1) {
      int k = i / 2;
      j = this.f;
      this.l = k - j / 2;
      i = i / 2 + j / 2;
    } else if (j == 2) {
      j = this.n;
      this.l = i - j - this.f;
      i -= j;
    } else {
      i = this.n;
      this.l = i;
      i += this.f;
    } 
    this.m = i;
    j = this.l;
    i = this.m;
    this.r = (j + i) / 2;
    float f1 = j;
    j = this.p;
    paramCanvas.drawLine(f1, j, i, j, this.i);
    f1 = this.l;
    float f2 = this.a.f().b();
    i = this.p;
    paramCanvas.drawLine(f1 + f2 * 4.0F, i, this.r, i, this.h);
    paramCanvas.drawText("0", this.l - this.a.f().b() * 6.0F, (this.p - this.n), this.g);
    str = this.d;
    paramCanvas.drawText(str, this.m - (str.length() * 6) * this.a.f().b(), (this.p - this.n), this.g);
    if (this.e != 0) {
      str = this.k;
      paramCanvas.drawText(str, this.r - (str.length() * 6) * this.a.f().b(), (this.p - 10), this.g);
      f3 = this.r;
      f4 = this.p;
      f2 = this.m - this.a.f().b() * 4.0F;
      f1 = this.p;
      paint1 = this.s;
    } else {
      f3 = this.r;
      f4 = this.p;
      f2 = this.m - this.a.f().b() * 4.0F;
      f1 = this.p;
      paint1 = this.h;
    } 
    paramCanvas.drawLine(f3, f4, f2, f1, paint1);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\g\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */