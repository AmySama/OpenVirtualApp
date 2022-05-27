package com.tencent.map.geolocation;

import android.content.Context;
import android.os.Looper;
import c.t.m.g.ci;
import c.t.m.g.cj;
import c.t.m.g.cy;

public final class TencentLocationManager {
  public static final int COORDINATE_TYPE_GCJ02 = 1;
  
  public static final int COORDINATE_TYPE_WGS84 = 0;
  
  private static TencentLocationManager d;
  
  private final byte[] a = new byte[0];
  
  private final ci b;
  
  private final cy c;
  
  private TencentLocationManager(Context paramContext) {
    this.b = ci.a(paramContext);
    this.c = new cy(this.b);
  }
  
  public static TencentLocationManager getInstance(Context paramContext) throws NullPointerException, IllegalArgumentException {
    // Byte code:
    //   0: ldc com/tencent/map/geolocation/TencentLocationManager
    //   2: monitorenter
    //   3: getstatic com/tencent/map/geolocation/TencentLocationManager.d : Lcom/tencent/map/geolocation/TencentLocationManager;
    //   6: ifnonnull -> 69
    //   9: aload_0
    //   10: ifnull -> 57
    //   13: aload_0
    //   14: invokevirtual getApplicationContext : ()Landroid/content/Context;
    //   17: invokestatic getOrigApplicationContext : (Landroid/content/Context;)Landroid/content/Context;
    //   20: ifnull -> 45
    //   23: new com/tencent/map/geolocation/TencentLocationManager
    //   26: astore_1
    //   27: aload_1
    //   28: aload_0
    //   29: invokevirtual getApplicationContext : ()Landroid/content/Context;
    //   32: invokestatic getOrigApplicationContext : (Landroid/content/Context;)Landroid/content/Context;
    //   35: invokespecial <init> : (Landroid/content/Context;)V
    //   38: aload_1
    //   39: putstatic com/tencent/map/geolocation/TencentLocationManager.d : Lcom/tencent/map/geolocation/TencentLocationManager;
    //   42: goto -> 69
    //   45: new java/lang/NullPointerException
    //   48: astore_0
    //   49: aload_0
    //   50: ldc 'application context is null'
    //   52: invokespecial <init> : (Ljava/lang/String;)V
    //   55: aload_0
    //   56: athrow
    //   57: new java/lang/NullPointerException
    //   60: astore_0
    //   61: aload_0
    //   62: ldc 'context is null'
    //   64: invokespecial <init> : (Ljava/lang/String;)V
    //   67: aload_0
    //   68: athrow
    //   69: getstatic com/tencent/map/geolocation/TencentLocationManager.d : Lcom/tencent/map/geolocation/TencentLocationManager;
    //   72: astore_0
    //   73: ldc com/tencent/map/geolocation/TencentLocationManager
    //   75: monitorexit
    //   76: aload_0
    //   77: areturn
    //   78: astore_0
    //   79: ldc com/tencent/map/geolocation/TencentLocationManager
    //   81: monitorexit
    //   82: aload_0
    //   83: athrow
    // Exception table:
    //   from	to	target	type
    //   3	9	78	finally
    //   13	42	78	finally
    //   45	57	78	finally
    //   57	69	78	finally
    //   69	73	78	finally
  }
  
  public final String getBuild() {
    cj cj = this.b.i();
    return (cj != null) ? cj.e() : "None";
  }
  
  public final int getCoordinateType() {
    return this.c.f();
  }
  
  public final TencentLocation getLastKnownLocation() {
    return this.c.a();
  }
  
  public final String getVersion() {
    cj cj = this.b.i();
    return (cj != null) ? cj.d() : "None";
  }
  
  public final void removeUpdates(TencentLocationListener paramTencentLocationListener) {
    synchronized (this.a) {
      this.c.e();
      return;
    } 
  }
  
  public final int requestLocationUpdates(TencentLocationRequest paramTencentLocationRequest, TencentLocationListener paramTencentLocationListener) {
    return requestLocationUpdates(paramTencentLocationRequest, paramTencentLocationListener, Looper.myLooper());
  }
  
  public final int requestLocationUpdates(TencentLocationRequest paramTencentLocationRequest, TencentLocationListener paramTencentLocationListener, Looper paramLooper) {
    if (paramTencentLocationRequest != null) {
      if (paramTencentLocationListener != null) {
        if (paramLooper != null)
          synchronized (this.a) {
            return this.c.a(paramTencentLocationRequest, paramTencentLocationListener, paramLooper);
          }  
        throw new NullPointerException("looper is null");
      } 
      throw new NullPointerException("listener is null");
    } 
    throw new NullPointerException("request is null");
  }
  
  public final int requestSingleFreshLocation(TencentLocationListener paramTencentLocationListener, Looper paramLooper) {
    if (paramTencentLocationListener != null) {
      if (paramLooper != null)
        synchronized (this.a) {
          return this.c.a(paramTencentLocationListener, paramLooper);
        }  
      throw new NullPointerException("looper is null");
    } 
    throw new NullPointerException("listener is null");
  }
  
  public final void setCoordinateType(int paramInt) {
    if (paramInt == 1 || paramInt == 0)
      synchronized (this.a) {
        this.c.a(paramInt);
        return;
      }  
    StringBuilder stringBuilder = new StringBuilder("unknown coordinate type: ");
    stringBuilder.append(paramInt);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public final int startDistanceCalculate(TencentDistanceListener paramTencentDistanceListener) {
    if (paramTencentDistanceListener != null)
      synchronized (this.a) {
        return this.c.a(paramTencentDistanceListener);
      }  
    throw new NullPointerException("listener is null");
  }
  
  public final boolean startIndoorLocation() {
    return this.c.c();
  }
  
  public final TencentDistanceAnalysis stopDistanceCalculate(TencentDistanceListener paramTencentDistanceListener) {
    synchronized (this.a) {
      return this.c.b();
    } 
  }
  
  public final boolean stopIndoorLocation() {
    return this.c.d();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\map\geolocation\TencentLocationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */