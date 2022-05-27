package com.tencent.lbssearch;

import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.Address2GeoParam;
import com.tencent.lbssearch.object.param.DistrictChildrenParam;
import com.tencent.lbssearch.object.param.Geo2AddressParam;
import com.tencent.lbssearch.object.param.RoutePlanningParam;
import com.tencent.lbssearch.object.param.SearchParam;
import com.tencent.lbssearch.object.param.StreetViewParam;
import com.tencent.lbssearch.object.param.SuggestionParam;
import com.tencent.lbssearch.object.param.TranslateParam;

public interface ITencentSearch {
  void address2geo(Address2GeoParam paramAddress2GeoParam, HttpResponseListener paramHttpResponseListener);
  
  void geo2address(Geo2AddressParam paramGeo2AddressParam, HttpResponseListener paramHttpResponseListener);
  
  void getDirection(RoutePlanningParam paramRoutePlanningParam, HttpResponseListener paramHttpResponseListener);
  
  void getDistrictChildren(DistrictChildrenParam paramDistrictChildrenParam, HttpResponseListener paramHttpResponseListener);
  
  void getDistrictList(HttpResponseListener paramHttpResponseListener);
  
  void getpano(StreetViewParam paramStreetViewParam, HttpResponseListener paramHttpResponseListener);
  
  void mBikeGeo2address(Geo2AddressParam paramGeo2AddressParam, HttpResponseListener paramHttpResponseListener);
  
  void search(SearchParam paramSearchParam, HttpResponseListener paramHttpResponseListener);
  
  void suggestion(SuggestionParam paramSuggestionParam, HttpResponseListener paramHttpResponseListener);
  
  void translate(TranslateParam paramTranslateParam, HttpResponseListener paramHttpResponseListener);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\ITencentSearch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */