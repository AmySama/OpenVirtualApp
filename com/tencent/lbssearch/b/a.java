package com.tencent.lbssearch.b;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.lbssearch.ITencentSearch;
import com.tencent.lbssearch.a.b.d;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.Address2GeoParam;
import com.tencent.lbssearch.object.param.DistrictChildrenParam;
import com.tencent.lbssearch.object.param.Geo2AddressParam;
import com.tencent.lbssearch.object.param.ParamObject;
import com.tencent.lbssearch.object.param.RoutePlanningParam;
import com.tencent.lbssearch.object.param.SearchParam;
import com.tencent.lbssearch.object.param.StreetViewParam;
import com.tencent.lbssearch.object.param.SuggestionParam;
import com.tencent.lbssearch.object.param.TranslateParam;
import com.tencent.lbssearch.object.result.Address2GeoResultObject;
import com.tencent.lbssearch.object.result.DistrictResultObject;
import com.tencent.lbssearch.object.result.Geo2AddressResultObject;
import com.tencent.lbssearch.object.result.MBikeAddress2GeoResultObject;
import com.tencent.lbssearch.object.result.SearchResultObject;
import com.tencent.lbssearch.object.result.StreetViewResultObject;
import com.tencent.lbssearch.object.result.SuggestionResultObject;
import com.tencent.lbssearch.object.result.TranslateResultObject;

public class a implements ITencentSearch {
  private Context a;
  
  public a(Context paramContext) {
    this.a = paramContext;
  }
  
  private <T> void a(String paramString, ParamObject paramParamObject, Class<T> paramClass, HttpResponseListener paramHttpResponseListener) {
    if (paramParamObject == null || !paramParamObject.checkParams()) {
      com.tencent.lbssearch.a.c.a.a("wrong parameter");
      return;
    } 
    String str = com.tencent.lbssearch.a.a.a(this.a);
    if (TextUtils.isEmpty(str)) {
      if (paramHttpResponseListener != null)
        paramHttpResponseListener.onFailure(-1, "请申请并填写开发者密钥", null); 
      return;
    } 
    d d = paramParamObject.buildParameters();
    if (d != null) {
      d.b("key", str);
      d.b("output", "json");
    } 
    com.tencent.lbssearch.a.b.a.a(this.a, paramString, d, paramClass, paramHttpResponseListener);
  }
  
  public void address2geo(Address2GeoParam paramAddress2GeoParam, HttpResponseListener paramHttpResponseListener) {
    a("https://apis.map.qq.com/ws/geocoder/v1", (ParamObject)paramAddress2GeoParam, Address2GeoResultObject.class, paramHttpResponseListener);
  }
  
  public void geo2address(Geo2AddressParam paramGeo2AddressParam, HttpResponseListener paramHttpResponseListener) {
    a("https://apis.map.qq.com/ws/geocoder/v1", (ParamObject)paramGeo2AddressParam, Geo2AddressResultObject.class, paramHttpResponseListener);
  }
  
  public void getDirection(RoutePlanningParam paramRoutePlanningParam, HttpResponseListener paramHttpResponseListener) {
    a(paramRoutePlanningParam.getUrl(), (ParamObject)paramRoutePlanningParam, paramRoutePlanningParam.getResultClass(), paramHttpResponseListener);
  }
  
  public void getDistrictChildren(DistrictChildrenParam paramDistrictChildrenParam, HttpResponseListener paramHttpResponseListener) {
    a("https://apis.map.qq.com/ws/district/v1/getchildren", (ParamObject)paramDistrictChildrenParam, DistrictResultObject.class, paramHttpResponseListener);
  }
  
  public void getDistrictList(HttpResponseListener paramHttpResponseListener) {
    a("https://apis.map.qq.com/ws/district/v1/list", (ParamObject)new DistrictChildrenParam(), DistrictResultObject.class, paramHttpResponseListener);
  }
  
  public void getpano(StreetViewParam paramStreetViewParam, HttpResponseListener paramHttpResponseListener) {
    a("https://apis.map.qq.com/ws/streetview/v1/getpano", (ParamObject)paramStreetViewParam, StreetViewResultObject.class, paramHttpResponseListener);
  }
  
  public void mBikeGeo2address(Geo2AddressParam paramGeo2AddressParam, HttpResponseListener paramHttpResponseListener) {
    a("https://apis.map.qq.com/customapi/mobike/geocoder", (ParamObject)paramGeo2AddressParam, MBikeAddress2GeoResultObject.class, paramHttpResponseListener);
  }
  
  public void search(SearchParam paramSearchParam, HttpResponseListener paramHttpResponseListener) {
    a("https://apis.map.qq.com/ws/place/v1/search", (ParamObject)paramSearchParam, SearchResultObject.class, paramHttpResponseListener);
  }
  
  public void suggestion(SuggestionParam paramSuggestionParam, HttpResponseListener paramHttpResponseListener) {
    a("https://apis.map.qq.com/ws/place/v1/suggestion", (ParamObject)paramSuggestionParam, SuggestionResultObject.class, paramHttpResponseListener);
  }
  
  public void translate(TranslateParam paramTranslateParam, HttpResponseListener paramHttpResponseListener) {
    a("https://apis.map.qq.com/ws/coord/v1/translate", (ParamObject)paramTranslateParam, TranslateResultObject.class, paramHttpResponseListener);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\b\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */