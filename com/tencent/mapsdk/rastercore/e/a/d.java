package com.tencent.mapsdk.rastercore.e.a;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import com.tencent.mapsdk.raster.model.BitmapDescriptor;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.LatLngBounds;
import com.tencent.mapsdk.raster.model.PolylineOptions;
import com.tencent.mapsdk.rastercore.d.a;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.mapsdk.rastercore.e.b;
import com.tencent.mapsdk.rastercore.e.d;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class d implements d {
  private float a = 90.0F;
  
  private List<LatLng> b = new ArrayList<LatLng>();
  
  private List<LatLng> c = new ArrayList<LatLng>();
  
  private e d;
  
  private a e;
  
  private boolean f = false;
  
  private LatLngBounds g = null;
  
  private boolean h = false;
  
  private boolean i = true;
  
  private int j = -16777216;
  
  private float k = 10.0F;
  
  private float l = 0.0F;
  
  private BitmapDescriptor m;
  
  private float n = 0.0F;
  
  private int o = -983041;
  
  private String p;
  
  public d(e parame, PolylineOptions paramPolylineOptions) {
    this.d = parame;
    this.e = parame.e();
    this.p = getId();
    this.j = paramPolylineOptions.getColor();
    this.f = paramPolylineOptions.isDottedLine();
    boolean bool = paramPolylineOptions.isGeodesic();
    this.h = bool;
    if (bool || this.f)
      this.b = paramPolylineOptions.getPoints(); 
    b(paramPolylineOptions.getPoints());
    this.i = paramPolylineOptions.isVisible();
    this.k = paramPolylineOptions.getWidth();
    this.l = paramPolylineOptions.getZIndex();
    this.m = paramPolylineOptions.getArrowTexture();
    this.n = paramPolylineOptions.getEdgeWidth();
    this.o = paramPolylineOptions.getEdgeColor();
    this.a = paramPolylineOptions.getArrowGap();
  }
  
  private static int a(byte[] paramArrayOfbyte, int paramInt) {
    byte b1 = paramArrayOfbyte[paramInt + 0];
    byte b2 = paramArrayOfbyte[paramInt + 1];
    byte b3 = paramArrayOfbyte[paramInt + 2];
    return (paramArrayOfbyte[paramInt + 3] & 0xFF) << 24 | (b2 & 0xFF) << 8 | b1 & 0xFF | (b3 & 0xFF) << 16;
  }
  
  protected static Drawable a(Context paramContext, String paramString) throws Exception {
    int i1;
    InputStream inputStream = paramContext.getAssets().open(paramString);
    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
    int i = bitmap.getWidth();
    int j = bitmap.getHeight();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    int k;
    for (k = 0; k < 32; k++)
      byteArrayOutputStream.write(0); 
    int m = i - 2;
    int[] arrayOfInt = new int[m];
    bitmap.getPixels(arrayOfInt, 0, i, 1, 0, m, 1);
    if (arrayOfInt[0] == -16777216) {
      n = 1;
    } else {
      n = 0;
    } 
    if (arrayOfInt[m - 1] == -16777216) {
      i1 = 1;
    } else {
      i1 = 0;
    } 
    i = 0;
    int i2 = 0;
    for (k = 0; i < m; k = i7) {
      int i6 = i2;
      int i7 = k;
      if (i2 != arrayOfInt[i]) {
        i7 = k + 1;
        a(byteArrayOutputStream, i);
        i6 = arrayOfInt[i];
      } 
      i++;
      i2 = i6;
    } 
    i = k;
    if (i1) {
      i = k + 1;
      a(byteArrayOutputStream, m);
    } 
    int i4 = i + 1;
    k = i4;
    if (n)
      k = i4 - 1; 
    int n = k;
    if (i1)
      n = k - 1; 
    int i5 = j - 2;
    arrayOfInt = new int[i5];
    bitmap.getPixels(arrayOfInt, 0, 1, 0, 1, 1, i5);
    if (arrayOfInt[0] == -16777216) {
      i4 = 1;
    } else {
      i4 = 0;
    } 
    if (arrayOfInt[i5 - 1] == -16777216) {
      i1 = 1;
    } else {
      i1 = 0;
    } 
    i2 = 0;
    j = 0;
    for (k = 0; i2 < i5; k = i6) {
      m = j;
      int i6 = k;
      if (j != arrayOfInt[i2]) {
        i6 = k + 1;
        a(byteArrayOutputStream, i2);
        m = arrayOfInt[i2];
      } 
      i2++;
      j = m;
    } 
    int i3 = k;
    if (i1) {
      i3 = k + 1;
      a(byteArrayOutputStream, i5);
    } 
    i2 = i3 + 1;
    k = i2;
    if (i4 != 0)
      k = i2 - 1; 
    i4 = k;
    if (i1)
      i4 = k - 1; 
    k = 0;
    while (true) {
      Bitmap bitmap1;
      i1 = n * i4;
      if (k < i1) {
        a(byteArrayOutputStream, 1);
        k++;
        continue;
      } 
      byte[] arrayOfByte2 = byteArrayOutputStream.toByteArray();
      arrayOfByte2[0] = (byte)1;
      arrayOfByte2[1] = (byte)(byte)i;
      arrayOfByte2[2] = (byte)(byte)i3;
      arrayOfByte2[3] = (byte)(byte)i1;
      a(bitmap, arrayOfByte2);
      if (NinePatch.isNinePatchChunk(arrayOfByte2)) {
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 1, 1, bitmap.getWidth() - 2, bitmap.getHeight() - 2);
        bitmap.recycle();
        Field field = bitmap2.getClass().getDeclaredField("mNinePatchChunk");
        field.setAccessible(true);
        field.set(bitmap2, arrayOfByte2);
        bitmap1 = bitmap2;
      } 
      inputStream.close();
      if (bitmap1.getNinePatchChunk() == null)
        return (Drawable)new BitmapDrawable(bitmap1); 
      Rect rect = new Rect();
      byte[] arrayOfByte1 = bitmap1.getNinePatchChunk();
      rect.left = a(arrayOfByte1, 12);
      rect.right = a(arrayOfByte1, 16);
      rect.top = a(arrayOfByte1, 20);
      rect.bottom = a(arrayOfByte1, 24);
      return (Drawable)new NinePatchDrawable(paramContext.getResources(), bitmap1, bitmap1.getNinePatchChunk(), rect, null);
    } 
  }
  
  private static void a(Bitmap paramBitmap, byte[] paramArrayOfbyte) {
    int i = paramBitmap.getWidth() - 2;
    int[] arrayOfInt = new int[i];
    paramBitmap.getPixels(arrayOfInt, 0, i, 1, paramBitmap.getHeight() - 1, i, 1);
    boolean bool = false;
    int j;
    for (j = 0; j < i; j++) {
      if (-16777216 == arrayOfInt[j]) {
        a(paramArrayOfbyte, 12, j);
        break;
      } 
    } 
    for (j = i - 1; j >= 0; j--) {
      if (-16777216 == arrayOfInt[j]) {
        a(paramArrayOfbyte, 16, i - j - 2);
        break;
      } 
    } 
    i = paramBitmap.getHeight() - 2;
    arrayOfInt = new int[i];
    paramBitmap.getPixels(arrayOfInt, 0, 1, paramBitmap.getWidth() - 1, 0, 1, i);
    for (j = bool; j < i; j++) {
      if (-16777216 == arrayOfInt[j]) {
        a(paramArrayOfbyte, 20, j);
        break;
      } 
    } 
    for (j = i - 1; j >= 0; j--) {
      if (-16777216 == arrayOfInt[j]) {
        a(paramArrayOfbyte, 24, i - j - 2);
        return;
      } 
    } 
  }
  
  private void a(Canvas paramCanvas, List<PointF> paramList) {
    d d1 = this;
    if (paramList != null && paramList.size() != 0) {
      BitmapDescriptor bitmapDescriptor = d1.m;
      if (bitmapDescriptor != null && bitmapDescriptor.getBitmap() != null) {
        int i = d1.m.getWidth();
        int j = d1.m.getHeight();
        float f1 = a();
        float f2 = j;
        double d2 = (f1 / f2);
        double d3 = (i * d1.k / f2 * d1.a);
        double d4 = -(i * d2 / 2.0D);
        int k = 0;
        while (true) {
          List<PointF> list = paramList;
          if (k < paramList.size() - 1) {
            double d5;
            PointF pointF1 = list.get(k);
            int m = k + 1;
            PointF pointF2 = list.get(m);
            if (pointF1 == null || pointF2 == null) {
              d5 = 0.0D;
            } else {
              d5 = Math.sqrt(Math.pow((pointF1.x - pointF2.x), 2.0D) + Math.pow((pointF1.y - pointF2.y), 2.0D));
            } 
            double d6 = d3;
            int n = j;
            double d7 = d5 + d4;
            f2 = this.a;
            if (d7 < f2) {
              k = n;
              j = i;
              d3 = d2;
              d4 = d7;
            } else {
              double d8 = d5 / f2;
              d7 = (f2 - d4) / f2;
              d5 = Math.acos((pointF2.x - pointF1.x) / d5) * 180.0D / Math.PI;
              if (pointF2.x <= pointF1.x && pointF2.y >= pointF1.y) {
                d3 = 180.0D - d5 + 180.0D;
              } else {
                d3 = d5;
                if (pointF2.x >= pointF1.x) {
                  d3 = d5;
                  if (pointF2.y >= pointF1.y)
                    d3 = 360.0D - d5; 
                } 
              } 
              j = i;
              i = k;
              k = n;
              d5 = d7;
              while (true) {
                Canvas canvas = paramCanvas;
                d d9 = this;
                if (d5 <= d8) {
                  if (i == paramList.size() - 2 && d5 + d6 > d8)
                    return; 
                  paramCanvas.save();
                  canvas.translate((float)(pointF1.x + (pointF2.x - pointF1.x) * d5 / d8), (float)(pointF1.y + (pointF2.y - pointF1.y) * d5 / d8));
                  f2 = (float)d2;
                  canvas.scale(f2, f2);
                  canvas.rotate((float)-d3);
                  canvas.drawBitmap(d9.m.getBitmap(), (-j / 2), (-k / 2), null);
                  paramCanvas.restore();
                  d7 = 1.0D + d5;
                  if (d7 > d8)
                    d4 = (d8 - d5) * d9.a; 
                  d5 = d7;
                  continue;
                } 
                d3 = d2;
                break;
              } 
            } 
            n = m;
            d2 = d3;
            i = j;
            j = k;
            d3 = d6;
            k = n;
            continue;
          } 
          break;
        } 
      } 
    } 
  }
  
  private static void a(OutputStream paramOutputStream, int paramInt) throws IOException {
    paramOutputStream.write(paramInt >> 0 & 0xFF);
    paramOutputStream.write(paramInt >> 8 & 0xFF);
    paramOutputStream.write(paramInt >> 16 & 0xFF);
    paramOutputStream.write(paramInt >> 24 & 0xFF);
  }
  
  private static void a(List<LatLng> paramList1, List<LatLng> paramList2, double paramDouble) {
    if (paramList1.size() != 3)
      return; 
    int i;
    for (i = 0; i <= 10; i = (int)(f1 + 1.0F)) {
      float f1 = i;
      float f2 = f1 / 10.0F;
      double d1 = 1.0D - f2;
      double d2 = d1 * d1;
      double d3 = ((LatLng)paramList1.get(0)).getLongitude();
      double d4 = (2.0F * f2) * d1;
      double d5 = ((LatLng)paramList1.get(1)).getLongitude();
      double d6 = (f2 * f2);
      double d7 = ((LatLng)paramList1.get(2)).getLongitude();
      double d8 = ((LatLng)paramList1.get(0)).getLatitude();
      double d9 = ((LatLng)paramList1.get(1)).getLatitude();
      double d10 = ((LatLng)paramList1.get(2)).getLatitude();
      d1 = d2 + d4 * paramDouble + d6;
      paramList2.add(new LatLng((d8 * d2 + d9 * d4 * paramDouble + d10 * d6) / d1, (d3 * d2 + d5 * d4 * paramDouble + d7 * d6) / d1));
    } 
  }
  
  private static void a(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    paramArrayOfbyte[paramInt1 + 0] = (byte)(byte)(paramInt2 >> 0);
    paramArrayOfbyte[paramInt1 + 1] = (byte)(byte)(paramInt2 >> 8);
    paramArrayOfbyte[paramInt1 + 2] = (byte)(byte)(paramInt2 >> 16);
    paramArrayOfbyte[paramInt1 + 3] = (byte)(byte)(paramInt2 >> 24);
  }
  
  private void b(List<LatLng> paramList) {
    // Byte code:
    //   0: aload_1
    //   1: ifnull -> 471
    //   4: aload_1
    //   5: invokeinterface size : ()I
    //   10: ifne -> 16
    //   13: goto -> 471
    //   16: invokestatic builder : ()Lcom/tencent/mapsdk/raster/model/LatLngBounds$Builder;
    //   19: astore_2
    //   20: aload_0
    //   21: getfield c : Ljava/util/List;
    //   24: invokeinterface clear : ()V
    //   29: aload_2
    //   30: astore_3
    //   31: aload_1
    //   32: ifnull -> 462
    //   35: aconst_null
    //   36: astore #4
    //   38: aload_1
    //   39: invokeinterface iterator : ()Ljava/util/Iterator;
    //   44: astore #5
    //   46: aload_2
    //   47: astore_1
    //   48: aload_0
    //   49: astore_2
    //   50: aload_1
    //   51: astore_3
    //   52: aload #5
    //   54: invokeinterface hasNext : ()Z
    //   59: ifeq -> 462
    //   62: aload #5
    //   64: invokeinterface next : ()Ljava/lang/Object;
    //   69: checkcast com/tencent/mapsdk/raster/model/LatLng
    //   72: astore_3
    //   73: aload_3
    //   74: ifnull -> 459
    //   77: aload_3
    //   78: aload #4
    //   80: invokevirtual equals : (Ljava/lang/Object;)Z
    //   83: ifne -> 459
    //   86: aload_2
    //   87: getfield h : Z
    //   90: ifne -> 113
    //   93: aload_2
    //   94: getfield c : Ljava/util/List;
    //   97: aload_3
    //   98: invokeinterface add : (Ljava/lang/Object;)Z
    //   103: pop
    //   104: aload_1
    //   105: aload_3
    //   106: invokevirtual include : (Lcom/tencent/mapsdk/raster/model/LatLng;)Lcom/tencent/mapsdk/raster/model/LatLngBounds$Builder;
    //   109: pop
    //   110: goto -> 453
    //   113: aload #4
    //   115: ifnull -> 453
    //   118: aload_3
    //   119: invokevirtual getLongitude : ()D
    //   122: aload #4
    //   124: invokevirtual getLongitude : ()D
    //   127: dsub
    //   128: invokestatic abs : (D)D
    //   131: dstore #6
    //   133: aload_2
    //   134: getfield c : Ljava/util/List;
    //   137: astore #8
    //   139: dload #6
    //   141: ldc2_w 0.01
    //   144: dcmpg
    //   145: ifge -> 168
    //   148: aload #8
    //   150: aload #4
    //   152: invokeinterface add : (Ljava/lang/Object;)Z
    //   157: pop
    //   158: aload_1
    //   159: aload #4
    //   161: invokevirtual include : (Lcom/tencent/mapsdk/raster/model/LatLng;)Lcom/tencent/mapsdk/raster/model/LatLngBounds$Builder;
    //   164: pop
    //   165: goto -> 93
    //   168: aload #4
    //   170: invokevirtual getLongitude : ()D
    //   173: aload_3
    //   174: invokevirtual getLongitude : ()D
    //   177: dsub
    //   178: invokestatic abs : (D)D
    //   181: ldc2_w 3.141592653589793
    //   184: dmul
    //   185: ldc2_w 180.0
    //   188: ddiv
    //   189: dstore #6
    //   191: new com/tencent/mapsdk/raster/model/LatLng
    //   194: dup
    //   195: aload_3
    //   196: invokevirtual getLatitude : ()D
    //   199: aload #4
    //   201: invokevirtual getLatitude : ()D
    //   204: dadd
    //   205: ldc2_w 2.0
    //   208: ddiv
    //   209: aload_3
    //   210: invokevirtual getLongitude : ()D
    //   213: aload #4
    //   215: invokevirtual getLongitude : ()D
    //   218: dadd
    //   219: ldc2_w 2.0
    //   222: ddiv
    //   223: invokespecial <init> : (DD)V
    //   226: astore_2
    //   227: aload_1
    //   228: aload #4
    //   230: invokevirtual include : (Lcom/tencent/mapsdk/raster/model/LatLng;)Lcom/tencent/mapsdk/raster/model/LatLngBounds$Builder;
    //   233: aload_2
    //   234: invokevirtual include : (Lcom/tencent/mapsdk/raster/model/LatLng;)Lcom/tencent/mapsdk/raster/model/LatLngBounds$Builder;
    //   237: aload_3
    //   238: invokevirtual include : (Lcom/tencent/mapsdk/raster/model/LatLng;)Lcom/tencent/mapsdk/raster/model/LatLngBounds$Builder;
    //   241: pop
    //   242: aload_2
    //   243: invokevirtual getLatitude : ()D
    //   246: dconst_0
    //   247: dcmpl
    //   248: ifle -> 257
    //   251: iconst_1
    //   252: istore #9
    //   254: goto -> 260
    //   257: iconst_m1
    //   258: istore #9
    //   260: dload #6
    //   262: ldc2_w 0.5
    //   265: dmul
    //   266: dstore #10
    //   268: dload #10
    //   270: invokestatic cos : (D)D
    //   273: dstore #6
    //   275: aload #4
    //   277: invokevirtual getLongitude : ()D
    //   280: aload_3
    //   281: invokevirtual getLongitude : ()D
    //   284: dsub
    //   285: aload #4
    //   287: invokevirtual getLatitude : ()D
    //   290: aload_3
    //   291: invokevirtual getLatitude : ()D
    //   294: dsub
    //   295: invokestatic hypot : (DD)D
    //   298: dstore #12
    //   300: dload #10
    //   302: invokestatic tan : (D)D
    //   305: dstore #14
    //   307: aload_3
    //   308: invokevirtual getLongitude : ()D
    //   311: aload #4
    //   313: invokevirtual getLongitude : ()D
    //   316: dsub
    //   317: dstore #16
    //   319: aload_3
    //   320: invokevirtual getLatitude : ()D
    //   323: aload #4
    //   325: invokevirtual getLatitude : ()D
    //   328: dsub
    //   329: dstore #10
    //   331: dload #10
    //   333: dload #10
    //   335: dmul
    //   336: dload #16
    //   338: dload #16
    //   340: dmul
    //   341: ddiv
    //   342: dstore #18
    //   344: iload #9
    //   346: i2d
    //   347: dload #12
    //   349: ldc2_w 0.5
    //   352: dmul
    //   353: dload #14
    //   355: dmul
    //   356: dmul
    //   357: dload #18
    //   359: dconst_1
    //   360: dadd
    //   361: invokestatic sqrt : (D)D
    //   364: ddiv
    //   365: aload_2
    //   366: invokevirtual getLatitude : ()D
    //   369: dadd
    //   370: d2i
    //   371: i2d
    //   372: dstore #12
    //   374: new com/tencent/mapsdk/raster/model/LatLng
    //   377: dup
    //   378: dload #12
    //   380: aload_2
    //   381: invokevirtual getLatitude : ()D
    //   384: dload #12
    //   386: dsub
    //   387: dload #10
    //   389: dmul
    //   390: dload #16
    //   392: ddiv
    //   393: aload_2
    //   394: invokevirtual getLongitude : ()D
    //   397: dadd
    //   398: d2i
    //   399: i2d
    //   400: invokespecial <init> : (DD)V
    //   403: astore_2
    //   404: new java/util/ArrayList
    //   407: dup
    //   408: invokespecial <init> : ()V
    //   411: astore #20
    //   413: aload #20
    //   415: aload #4
    //   417: invokeinterface add : (Ljava/lang/Object;)Z
    //   422: pop
    //   423: aload #20
    //   425: aload_2
    //   426: invokeinterface add : (Ljava/lang/Object;)Z
    //   431: pop
    //   432: aload #20
    //   434: aload_3
    //   435: invokeinterface add : (Ljava/lang/Object;)Z
    //   440: pop
    //   441: aload #20
    //   443: aload #8
    //   445: dload #6
    //   447: invokestatic a : (Ljava/util/List;Ljava/util/List;D)V
    //   450: goto -> 453
    //   453: aload_3
    //   454: astore #4
    //   456: goto -> 459
    //   459: goto -> 48
    //   462: aload_0
    //   463: aload_3
    //   464: invokevirtual build : ()Lcom/tencent/mapsdk/raster/model/LatLngBounds;
    //   467: putfield g : Lcom/tencent/mapsdk/raster/model/LatLngBounds;
    //   470: return
    //   471: return
  }
  
  public float a() {
    return this.k;
  }
  
  public void a(float paramFloat) {
    this.k = paramFloat;
    this.d.a(false, false);
  }
  
  public void a(int paramInt) {
    this.j = paramInt;
    this.d.a(false, false);
  }
  
  public void a(List<LatLng> paramList) {
    if (this.h || this.f)
      this.b = paramList; 
    b(paramList);
    this.d.a(false, false);
  }
  
  public void a(boolean paramBoolean) {
    this.f = paramBoolean;
    this.d.a(false, false);
  }
  
  public int b() {
    return this.j;
  }
  
  public void b(boolean paramBoolean) {
    this.h = paramBoolean;
    this.d.a(false, false);
  }
  
  public List<LatLng> c() {
    return (this.h || this.f) ? this.b : this.c;
  }
  
  public boolean checkInBounds() {
    if (this.g == null)
      return false; 
    LatLngBounds latLngBounds = this.d.b().c();
    return (latLngBounds == null) ? true : ((latLngBounds.contains(this.g) || this.g.intersects(latLngBounds)));
  }
  
  public boolean d() {
    return this.f;
  }
  
  public void destroy() {}
  
  public void draw(Canvas paramCanvas) {
    ArrayList<PointF> arrayList = new ArrayList();
    List<LatLng> list = this.c;
    if (list != null && list.size() != 0 && this.k > 0.0F) {
      Path path = new Path();
      LatLng latLng = this.c.get(0);
      PointF pointF = this.d.b().a(latLng);
      arrayList.add(pointF);
      path.moveTo(pointF.x, pointF.y);
      int i;
      for (i = 1; i < this.c.size(); i++) {
        LatLng latLng1 = this.c.get(i);
        PointF pointF1 = this.d.b().a(latLng1);
        arrayList.add(pointF1);
        path.lineTo(pointF1.x, pointF1.y);
      } 
      Paint paint = new Paint();
      paint.setStyle(Paint.Style.STROKE);
      if (this.f) {
        i = (int)a();
        float f1 = (i * 3);
        float f2 = i;
        paint.setPathEffect((PathEffect)new DashPathEffect(new float[] { f1, f2, f1, f2 }, 1.0F));
      } else {
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
      } 
      paint.setAntiAlias(true);
      if (this.n > 0.0F) {
        paint.setColor(this.o);
        paint.setStrokeWidth(a() + this.n * 2.0F);
        paramCanvas.drawPath(path, paint);
      } 
      paint.setColor(b());
      paint.setStrokeWidth(a());
      paramCanvas.drawPath(path, paint);
      if (this.m != null)
        a(paramCanvas, arrayList); 
      arrayList.clear();
    } 
  }
  
  public boolean e() {
    return this.h;
  }
  
  public boolean equalsRemote(b paramb) {
    return (equals(paramb) || paramb.getId().equals(getId()));
  }
  
  public String getId() {
    if (this.p == null)
      this.p = a.a("Polyline"); 
    return this.p;
  }
  
  public float getZIndex() {
    return this.l;
  }
  
  public int hashCodeRemote() {
    return hashCode();
  }
  
  public boolean isVisible() {
    return this.i;
  }
  
  public void remove() {
    this.e.b(getId());
  }
  
  public void setVisible(boolean paramBoolean) {
    this.i = paramBoolean;
    this.e.c();
    this.d.a(false, false);
  }
  
  public void setZIndex(float paramFloat) {
    this.l = paramFloat;
    this.e.c();
    this.d.a(false, false);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\e\a\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */