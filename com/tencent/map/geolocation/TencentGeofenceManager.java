package com.tencent.map.geolocation;

import android.app.PendingIntent;
import android.content.Context;
import c.t.m.g.cp;

public class TencentGeofenceManager {
  private cp a;
  
  public TencentGeofenceManager(Context paramContext) {
    this.a = new cp(paramContext);
  }
  
  public void addFence(TencentGeofence paramTencentGeofence, PendingIntent paramPendingIntent) {
    this.a.a(paramTencentGeofence, paramPendingIntent);
  }
  
  public void destroy() {
    this.a.a();
  }
  
  public void removeAllFences() {
    this.a.b();
  }
  
  public void removeFence(TencentGeofence paramTencentGeofence) {
    this.a.a(paramTencentGeofence);
  }
  
  public void removeFence(String paramString) {
    this.a.a(paramString);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\map\geolocation\TencentGeofenceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */