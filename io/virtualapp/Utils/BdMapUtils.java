package io.virtualapp.Utils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;

public class BdMapUtils {
  static GeoCoder geoCoder = GeoCoder.newInstance();
  
  public static void reverseGeoParse(double paramDouble1, double paramDouble2, OnGetGeoCoderResultListener paramOnGetGeoCoderResultListener) {
    geoCoder.setOnGetGeoCodeResultListener(paramOnGetGeoCoderResultListener);
    LatLng latLng = new LatLng(paramDouble2, paramDouble1);
    geoCoder.reverseGeoCode((new ReverseGeoCodeOption()).location(latLng));
  }
  
  public static void reverseGeoParse(LatLng paramLatLng, OnGetGeoCoderResultListener paramOnGetGeoCoderResultListener) {
    geoCoder.setOnGetGeoCodeResultListener(paramOnGetGeoCoderResultListener);
    geoCoder.reverseGeoCode((new ReverseGeoCodeOption()).location(paramLatLng));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\BdMapUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */