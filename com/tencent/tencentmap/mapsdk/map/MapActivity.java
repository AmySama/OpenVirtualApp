package com.tencent.tencentmap.mapsdk.map;

import android.app.Activity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public abstract class MapActivity extends Activity {
  private List<MapView> mapViewList = new ArrayList<MapView>();
  
  private Bundle mysavedInstanceState;
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    this.mysavedInstanceState = paramBundle;
  }
  
  protected void onDestroy() {
    super.onDestroy();
    for (MapView mapView : this.mapViewList) {
      if (mapView != null)
        mapView.onDestroy(); 
    } 
    this.mapViewList.clear();
  }
  
  protected void onPause() {
    super.onPause();
    for (MapView mapView : this.mapViewList) {
      if (mapView != null)
        mapView.onPause(); 
    } 
  }
  
  protected void onResume() {
    super.onResume();
    for (MapView mapView : this.mapViewList) {
      if (mapView != null)
        mapView.onResume(); 
    } 
  }
  
  protected void onSaveInstanceState(Bundle paramBundle) {
    super.onSaveInstanceState(paramBundle);
    for (MapView mapView : this.mapViewList) {
      if (mapView != null)
        mapView.onSaveInstanceState(paramBundle); 
    } 
  }
  
  protected void onStop() {
    super.onStop();
    for (MapView mapView : this.mapViewList) {
      if (mapView != null)
        mapView.onStop(); 
    } 
  }
  
  public void setMapView(MapView paramMapView) {
    this.mapViewList.add(paramMapView);
    paramMapView.onCreate(this.mysavedInstanceState);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\tencentmap\mapsdk\map\MapActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */