package com.tencent.lbssearch;

import android.content.Context;
import com.tencent.lbssearch.b.a;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.Address2GeoParam;
import com.tencent.lbssearch.object.param.DistrictChildrenParam;
import com.tencent.lbssearch.object.param.Geo2AddressParam;
import com.tencent.lbssearch.object.param.RoutePlanningParam;
import com.tencent.lbssearch.object.param.SearchParam;
import com.tencent.lbssearch.object.param.StreetViewParam;
import com.tencent.lbssearch.object.param.SuggestionParam;
import com.tencent.lbssearch.object.param.TranslateParam;

public class TencentSearch implements ITencentSearch {
  private ITencentSearch mApi;
  
  public TencentSearch(Context paramContext) {
    this.mApi = (ITencentSearch)new a(paramContext);
  }
  
  public void address2geo(Address2GeoParam paramAddress2GeoParam, HttpResponseListener paramHttpResponseListener) {
    this.mApi.address2geo(paramAddress2GeoParam, paramHttpResponseListener);
  }
  
  public void geo2address(Geo2AddressParam paramGeo2AddressParam, HttpResponseListener paramHttpResponseListener) {
    this.mApi.geo2address(paramGeo2AddressParam, paramHttpResponseListener);
  }
  
  public void getDirection(RoutePlanningParam paramRoutePlanningParam, HttpResponseListener paramHttpResponseListener) {
    this.mApi.getDirection(paramRoutePlanningParam, paramHttpResponseListener);
  }
  
  public void getDistrictChildren(DistrictChildrenParam paramDistrictChildrenParam, HttpResponseListener paramHttpResponseListener) {
    this.mApi.getDistrictChildren(paramDistrictChildrenParam, paramHttpResponseListener);
  }
  
  public void getDistrictList(HttpResponseListener paramHttpResponseListener) {
    this.mApi.getDistrictList(paramHttpResponseListener);
  }
  
  public void getpano(StreetViewParam paramStreetViewParam, HttpResponseListener paramHttpResponseListener) {
    this.mApi.getpano(paramStreetViewParam, paramHttpResponseListener);
  }
  
  public void mBikeGeo2address(Geo2AddressParam paramGeo2AddressParam, HttpResponseListener paramHttpResponseListener) {
    this.mApi.mBikeGeo2address(paramGeo2AddressParam, paramHttpResponseListener);
  }
  
  public void search(SearchParam paramSearchParam, HttpResponseListener paramHttpResponseListener) {
    this.mApi.search(paramSearchParam, paramHttpResponseListener);
  }
  
  public void suggestion(SuggestionParam paramSuggestionParam, HttpResponseListener paramHttpResponseListener) {
    this.mApi.suggestion(paramSuggestionParam, paramHttpResponseListener);
  }
  
  public void translate(TranslateParam paramTranslateParam, HttpResponseListener paramHttpResponseListener) {
    this.mApi.translate(paramTranslateParam, paramHttpResponseListener);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\TencentSearch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */