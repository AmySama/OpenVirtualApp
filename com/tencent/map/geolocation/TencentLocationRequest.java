package com.tencent.map.geolocation;

import android.os.Bundle;
import c.t.m.g.dl;

public final class TencentLocationRequest {
  public static final int REQUEST_LEVEL_ADMIN_AREA = 3;
  
  public static final int REQUEST_LEVEL_GEO = 0;
  
  public static final int REQUEST_LEVEL_NAME = 1;
  
  public static final int REQUEST_LEVEL_POI = 4;
  
  private long a;
  
  private int b;
  
  private boolean c;
  
  private boolean d;
  
  private boolean e;
  
  private long f;
  
  private int g;
  
  private String h;
  
  private Bundle i;
  
  private TencentLocationRequest() {}
  
  public TencentLocationRequest(TencentLocationRequest paramTencentLocationRequest) {
    this.a = paramTencentLocationRequest.a;
    this.b = paramTencentLocationRequest.b;
    this.d = paramTencentLocationRequest.d;
    this.e = paramTencentLocationRequest.e;
    this.f = paramTencentLocationRequest.f;
    this.g = paramTencentLocationRequest.g;
    this.c = paramTencentLocationRequest.c;
    this.h = paramTencentLocationRequest.h;
    Bundle bundle = new Bundle();
    this.i = bundle;
    bundle.putAll(paramTencentLocationRequest.i);
  }
  
  public static void copy(TencentLocationRequest paramTencentLocationRequest1, TencentLocationRequest paramTencentLocationRequest2) {
    paramTencentLocationRequest1.a = paramTencentLocationRequest2.a;
    paramTencentLocationRequest1.b = paramTencentLocationRequest2.b;
    paramTencentLocationRequest1.d = paramTencentLocationRequest2.d;
    paramTencentLocationRequest1.e = paramTencentLocationRequest2.e;
    paramTencentLocationRequest1.f = paramTencentLocationRequest2.f;
    paramTencentLocationRequest1.g = paramTencentLocationRequest2.g;
    paramTencentLocationRequest1.c = paramTencentLocationRequest2.c;
    paramTencentLocationRequest1.h = paramTencentLocationRequest2.h;
    paramTencentLocationRequest1.i.clear();
    paramTencentLocationRequest1.i.putAll(paramTencentLocationRequest2.i);
  }
  
  public static TencentLocationRequest create() {
    TencentLocationRequest tencentLocationRequest = new TencentLocationRequest();
    tencentLocationRequest.a = 10000L;
    tencentLocationRequest.b = 1;
    tencentLocationRequest.d = true;
    tencentLocationRequest.e = false;
    tencentLocationRequest.f = Long.MAX_VALUE;
    tencentLocationRequest.g = Integer.MAX_VALUE;
    tencentLocationRequest.c = true;
    tencentLocationRequest.h = "";
    tencentLocationRequest.i = new Bundle();
    return tencentLocationRequest;
  }
  
  public final Bundle getExtras() {
    return this.i;
  }
  
  public final long getInterval() {
    return this.a;
  }
  
  public final String getPhoneNumber() {
    String str1 = this.i.getString("phoneNumber");
    String str2 = str1;
    if (str1 == null)
      str2 = ""; 
    return str2;
  }
  
  public final String getQQ() {
    return this.h;
  }
  
  public final int getRequestLevel() {
    return this.b;
  }
  
  public final boolean isAllowCache() {
    return this.d;
  }
  
  public final boolean isAllowDirection() {
    return this.e;
  }
  
  public final boolean isAllowGPS() {
    return this.c;
  }
  
  public final TencentLocationRequest setAllowCache(boolean paramBoolean) {
    this.d = paramBoolean;
    return this;
  }
  
  public final TencentLocationRequest setAllowDirection(boolean paramBoolean) {
    this.e = paramBoolean;
    return this;
  }
  
  public final TencentLocationRequest setAllowGPS(boolean paramBoolean) {
    this.c = paramBoolean;
    return this;
  }
  
  public final TencentLocationRequest setInterval(long paramLong) {
    if (paramLong >= 0L) {
      this.a = paramLong;
      return this;
    } 
    throw new IllegalArgumentException("interval should >= 0");
  }
  
  public final TencentLocationRequest setPhoneNumber(String paramString) {
    String str = paramString;
    if (paramString == null)
      str = ""; 
    this.i.putString("phoneNumber", str);
    return this;
  }
  
  public final TencentLocationRequest setQQ(String paramString) {
    this.h = paramString;
    return this;
  }
  
  public final TencentLocationRequest setRequestLevel(int paramInt) {
    if (dl.a(paramInt)) {
      this.b = paramInt;
      return this;
    } 
    StringBuilder stringBuilder = new StringBuilder("request_level: ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" not supported!");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public final String toString() {
    StringBuilder stringBuilder = new StringBuilder("TencentLocationRequest {interval=");
    stringBuilder.append(this.a);
    stringBuilder.append("ms,level=");
    stringBuilder.append(this.b);
    stringBuilder.append(",allowCache=");
    stringBuilder.append(this.d);
    stringBuilder.append(",allowGps=");
    stringBuilder.append(this.c);
    stringBuilder.append(",allowDirection=");
    stringBuilder.append(this.e);
    stringBuilder.append(",QQ=");
    stringBuilder.append(this.h);
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\map\geolocation\TencentLocationRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */