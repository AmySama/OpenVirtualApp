package com.tencent.tencentmap.mapsdk.map;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class QSupportMapFragment extends Fragment {
  private MapView mapView;
  
  public static QSupportMapFragment newInstance() {
    return new QSupportMapFragment();
  }
  
  public MapView getMapView() {
    return this.mapView;
  }
  
  public void onAttach(Activity paramActivity) {
    super.onAttach(paramActivity);
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    Bundle bundle = paramBundle;
    if (paramBundle == null)
      bundle = getArguments(); 
    if (this.mapView == null) {
      MapView mapView = new MapView(paramLayoutInflater.getContext());
      this.mapView = mapView;
      mapView.onCreate(bundle);
    } 
    return (View)this.mapView;
  }
  
  public void onDestroy() {
    this.mapView.onDestroy();
    super.onDestroy();
  }
  
  public void onDestroyView() {
    this.mapView.onDestroyView();
    super.onDestroyView();
  }
  
  public void onInflate(Activity paramActivity, AttributeSet paramAttributeSet, Bundle paramBundle) {
    super.onInflate(paramActivity, paramAttributeSet, paramBundle);
  }
  
  public void onLowMemory() {
    super.onLowMemory();
    this.mapView.onLowMemory();
  }
  
  public void onPause() {
    super.onPause();
    this.mapView.onPause();
  }
  
  public void onResume() {
    super.onResume();
    this.mapView.onResume();
  }
  
  public void onSaveInstanceState(Bundle paramBundle) {
    this.mapView.onSaveInstanceState(paramBundle);
    super.onSaveInstanceState(paramBundle);
  }
  
  public void setArguments(Bundle paramBundle) {
    super.setArguments(paramBundle);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\tencentmap\mapsdk\map\QSupportMapFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */