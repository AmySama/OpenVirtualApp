package com.tencent.mapsdk.rastercore.d;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.tencent.mapsdk.raster.model.CircleOptions;
import com.tencent.mapsdk.raster.model.GeoPoint;
import com.tencent.mapsdk.raster.model.GroundOverlayOptions;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.mapsdk.raster.model.PolygonOptions;
import com.tencent.mapsdk.raster.model.PolylineOptions;
import com.tencent.mapsdk.rastercore.e.a.c;
import com.tencent.mapsdk.rastercore.e.a.d;
import com.tencent.mapsdk.rastercore.e.b;
import com.tencent.mapsdk.rastercore.e.c;
import com.tencent.mapsdk.rastercore.e.d;
import com.tencent.mapsdk.rastercore.tile.MapTile;
import com.tencent.mapsdk.rastercore.tile.a.b;
import com.tencent.tencentmap.mapsdk.map.Overlay;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class a {
  private static int e;
  
  private e a;
  
  private CopyOnWriteArrayList<b> b = new CopyOnWriteArrayList<b>();
  
  private SortedMap<String, c> c = new TreeMap<String, c>();
  
  private a d = new a((byte)0);
  
  private int f = 0;
  
  public a(e parame) {
    this.a = parame;
  }
  
  public static String a(String paramString) {
    e++;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append(e);
    return stringBuilder.toString();
  }
  
  public final c a(MarkerOptions paramMarkerOptions) {
    c c = new c(this.a, paramMarkerOptions);
    this.c.put(c.m(), c);
    return c;
  }
  
  public final com.tencent.mapsdk.rastercore.e.a a(CircleOptions paramCircleOptions) {
    com.tencent.mapsdk.rastercore.e.a a1 = new com.tencent.mapsdk.rastercore.e.a(this.a, paramCircleOptions);
    a((b)a1);
    return a1;
  }
  
  public final c a(PolygonOptions paramPolygonOptions) {
    c c = new c(this.a, paramPolygonOptions);
    a((b)c);
    return c;
  }
  
  public final d a(PolylineOptions paramPolylineOptions) {
    d d = new d(this.a, paramPolylineOptions);
    a((b)d);
    return (d)d;
  }
  
  public final b a(GroundOverlayOptions paramGroundOverlayOptions) {
    b b = new b(this.a, paramGroundOverlayOptions);
    a((b)b);
    return b;
  }
  
  public final void a() {
    try {
      Iterator<Map.Entry> iterator = this.c.entrySet().iterator();
      while (iterator.hasNext())
        ((c)((Map.Entry)iterator.next()).getValue()).c(); 
      this.c.clear();
      iterator = (Iterator)this.b.iterator();
      while (iterator.hasNext())
        ((b)iterator.next()).destroy(); 
      this.b.clear();
      com.tencent.mapsdk.rastercore.f.a.a(this.a.c(), 2);
    } catch (Exception exception) {}
  }
  
  public final void a(Canvas paramCanvas) {
    int i = this.b.size();
    for (b b : this.b) {
      if (b.isVisible() && (i <= 20 || b.checkInBounds()))
        b.draw(paramCanvas); 
    } 
  }
  
  public final void a(b paramb) {
    b(paramb.getId());
    this.b.add(paramb);
    if (!(paramb instanceof com.tencent.mapsdk.rastercore.e.a)) {
      int i = this.f + 1;
      this.f = i;
      if (i > 0)
        com.tencent.mapsdk.rastercore.f.a.a(this.a.c(), 1); 
    } 
    c();
    this.a.a(false, false);
  }
  
  public final boolean a(MotionEvent paramMotionEvent) {
    for (b b : this.b) {
      if (b instanceof Overlay)
        ((Overlay)b).onTouchEvent(paramMotionEvent, this.a.d()); 
    } 
    return false;
  }
  
  public final boolean a(GeoPoint paramGeoPoint) {
    // Byte code:
    //   0: aload_0
    //   1: getfield b : Ljava/util/concurrent/CopyOnWriteArrayList;
    //   4: invokevirtual iterator : ()Ljava/util/Iterator;
    //   7: astore_2
    //   8: aload_2
    //   9: invokeinterface hasNext : ()Z
    //   14: ifeq -> 58
    //   17: aload_2
    //   18: invokeinterface next : ()Ljava/lang/Object;
    //   23: checkcast com/tencent/mapsdk/rastercore/e/b
    //   26: astore_3
    //   27: aload_3
    //   28: instanceof com/tencent/tencentmap/mapsdk/map/Overlay
    //   31: ifeq -> 8
    //   34: aload_3
    //   35: checkcast com/tencent/tencentmap/mapsdk/map/Overlay
    //   38: aload_1
    //   39: aload_0
    //   40: getfield a : Lcom/tencent/mapsdk/rastercore/d/e;
    //   43: invokevirtual d : ()Lcom/tencent/tencentmap/mapsdk/map/MapView;
    //   46: invokevirtual onTap : (Lcom/tencent/mapsdk/raster/model/GeoPoint;Lcom/tencent/tencentmap/mapsdk/map/MapView;)Z
    //   49: ifeq -> 8
    //   52: iconst_1
    //   53: istore #4
    //   55: goto -> 61
    //   58: iconst_0
    //   59: istore #4
    //   61: iload #4
    //   63: ifne -> 111
    //   66: aload_0
    //   67: getfield b : Ljava/util/concurrent/CopyOnWriteArrayList;
    //   70: invokevirtual iterator : ()Ljava/util/Iterator;
    //   73: astore_2
    //   74: aload_2
    //   75: invokeinterface hasNext : ()Z
    //   80: ifeq -> 111
    //   83: aload_2
    //   84: invokeinterface next : ()Ljava/lang/Object;
    //   89: checkcast com/tencent/mapsdk/rastercore/e/b
    //   92: astore_3
    //   93: aload_3
    //   94: instanceof com/tencent/tencentmap/mapsdk/map/Overlay
    //   97: ifeq -> 74
    //   100: aload_3
    //   101: checkcast com/tencent/tencentmap/mapsdk/map/Overlay
    //   104: aload_1
    //   105: invokevirtual onEmptyTap : (Lcom/tencent/mapsdk/raster/model/GeoPoint;)V
    //   108: goto -> 74
    //   111: iload #4
    //   113: ireturn
  }
  
  public final boolean a(GeoPoint paramGeoPoint, MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_0
    //   1: getfield b : Ljava/util/concurrent/CopyOnWriteArrayList;
    //   4: invokevirtual iterator : ()Ljava/util/Iterator;
    //   7: astore_3
    //   8: aload_3
    //   9: invokeinterface hasNext : ()Z
    //   14: ifeq -> 62
    //   17: aload_3
    //   18: invokeinterface next : ()Ljava/lang/Object;
    //   23: checkcast com/tencent/mapsdk/rastercore/e/b
    //   26: astore #4
    //   28: aload #4
    //   30: instanceof com/tencent/tencentmap/mapsdk/map/Overlay
    //   33: ifeq -> 8
    //   36: aload #4
    //   38: checkcast com/tencent/tencentmap/mapsdk/map/Overlay
    //   41: aload_1
    //   42: aload_2
    //   43: aload_0
    //   44: getfield a : Lcom/tencent/mapsdk/rastercore/d/e;
    //   47: invokevirtual d : ()Lcom/tencent/tencentmap/mapsdk/map/MapView;
    //   50: invokevirtual onLongPress : (Lcom/tencent/mapsdk/raster/model/GeoPoint;Landroid/view/MotionEvent;Lcom/tencent/tencentmap/mapsdk/map/MapView;)Z
    //   53: ifeq -> 8
    //   56: iconst_1
    //   57: istore #5
    //   59: goto -> 65
    //   62: iconst_0
    //   63: istore #5
    //   65: iload #5
    //   67: ireturn
  }
  
  protected final void b() {
    a();
  }
  
  public final boolean b(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: getfield b : Ljava/util/concurrent/CopyOnWriteArrayList;
    //   4: invokevirtual iterator : ()Ljava/util/Iterator;
    //   7: astore_2
    //   8: aload_2
    //   9: invokeinterface hasNext : ()Z
    //   14: ifeq -> 49
    //   17: aload_2
    //   18: invokeinterface next : ()Ljava/lang/Object;
    //   23: checkcast com/tencent/mapsdk/rastercore/e/b
    //   26: astore_3
    //   27: aload_3
    //   28: ifnull -> 8
    //   31: aload_3
    //   32: invokeinterface getId : ()Ljava/lang/String;
    //   37: aload_1
    //   38: invokevirtual equals : (Ljava/lang/Object;)Z
    //   41: ifeq -> 8
    //   44: aload_3
    //   45: astore_1
    //   46: goto -> 51
    //   49: aconst_null
    //   50: astore_1
    //   51: aload_1
    //   52: ifnull -> 122
    //   55: aload_0
    //   56: getfield b : Ljava/util/concurrent/CopyOnWriteArrayList;
    //   59: aload_1
    //   60: invokevirtual remove : (Ljava/lang/Object;)Z
    //   63: istore #4
    //   65: iload #4
    //   67: ifeq -> 110
    //   70: aload_0
    //   71: getfield b : Ljava/util/concurrent/CopyOnWriteArrayList;
    //   74: instanceof com/tencent/mapsdk/rastercore/e/a
    //   77: ifne -> 110
    //   80: aload_0
    //   81: getfield f : I
    //   84: iconst_1
    //   85: isub
    //   86: istore #5
    //   88: aload_0
    //   89: iload #5
    //   91: putfield f : I
    //   94: iload #5
    //   96: ifne -> 110
    //   99: aload_0
    //   100: getfield a : Lcom/tencent/mapsdk/rastercore/d/e;
    //   103: invokevirtual c : ()Lcom/tencent/mapsdk/rastercore/d/b;
    //   106: iconst_2
    //   107: invokestatic a : (Landroid/view/View;I)V
    //   110: aload_0
    //   111: getfield a : Lcom/tencent/mapsdk/rastercore/d/e;
    //   114: iconst_0
    //   115: iconst_0
    //   116: invokevirtual a : (ZZ)V
    //   119: iload #4
    //   121: ireturn
    //   122: iconst_0
    //   123: ireturn
  }
  
  public final void c() {
    Object[] arrayOfObject = this.b.toArray();
    Arrays.sort(arrayOfObject, this.d);
    this.b.clear();
    int i = arrayOfObject.length;
    for (byte b = 0; b < i; b++) {
      Object object = arrayOfObject[b];
      this.b.add((b)object);
    } 
  }
  
  public final boolean c(String paramString) {
    c c = this.c.remove(paramString);
    if (c == null)
      return false; 
    c.c();
    return true;
  }
  
  final class a implements Comparator<Object> {
    private a(a this$0) {}
    
    public final int compare(Object param1Object1, Object param1Object2) {
      param1Object1 = param1Object1;
      param1Object2 = param1Object2;
      if (param1Object1 != null && param1Object2 != null)
        try {
          if (param1Object1.getZIndex() > param1Object2.getZIndex())
            return 1; 
          float f1 = param1Object1.getZIndex();
          float f2 = param1Object2.getZIndex();
          if (f1 < f2)
            return -1; 
        } catch (Exception exception) {
          exception.printStackTrace();
        }  
      return 0;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\d\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */