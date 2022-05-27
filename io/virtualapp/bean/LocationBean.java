package io.virtualapp.bean;

import android.text.TextUtils;
import com.baidu.mapapi.favorite.FavoritePoiInfo;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import java.io.Serializable;

public class LocationBean implements Serializable {
  public float accuracy = 0.0F;
  
  private String address;
  
  public double altitude = 0.0D;
  
  public float bearing;
  
  private String cityName;
  
  private String id;
  
  public double latitude = 0.0D;
  
  private LatLng location;
  
  public double longitude = 0.0D;
  
  private String name;
  
  public float speed;
  
  private String uid;
  
  public LocationBean() {}
  
  public LocationBean(FavoritePoiInfo paramFavoritePoiInfo) {
    if (!TextUtils.isEmpty(paramFavoritePoiInfo.getID()))
      this.id = paramFavoritePoiInfo.getID(); 
    if (!TextUtils.isEmpty(paramFavoritePoiInfo.getUid()))
      this.uid = paramFavoritePoiInfo.getUid(); 
    if (!TextUtils.isEmpty(paramFavoritePoiInfo.getAddr()))
      this.address = paramFavoritePoiInfo.getAddr(); 
    if (!TextUtils.isEmpty(paramFavoritePoiInfo.getPoiName()))
      this.name = paramFavoritePoiInfo.getPoiName(); 
    if (!TextUtils.isEmpty(paramFavoritePoiInfo.getCityName()))
      this.cityName = paramFavoritePoiInfo.getCityName(); 
    if (paramFavoritePoiInfo.getPt() != null) {
      this.location = paramFavoritePoiInfo.getPt();
      this.latitude = (paramFavoritePoiInfo.getPt()).latitude;
      this.longitude = (paramFavoritePoiInfo.getPt()).longitude;
    } 
  }
  
  public LocationBean(PoiInfo paramPoiInfo) {
    if (!TextUtils.isEmpty(paramPoiInfo.uid))
      this.uid = paramPoiInfo.uid; 
    if (!TextUtils.isEmpty(paramPoiInfo.address))
      this.address = paramPoiInfo.address; 
    if (!TextUtils.isEmpty(paramPoiInfo.name))
      this.name = paramPoiInfo.name; 
    if (!TextUtils.isEmpty(paramPoiInfo.city))
      this.cityName = paramPoiInfo.city; 
    if (paramPoiInfo.location != null) {
      this.location = paramPoiInfo.location;
      this.latitude = paramPoiInfo.location.latitude;
      this.longitude = paramPoiInfo.location.longitude;
    } 
  }
  
  public float getAccuracy() {
    return this.accuracy;
  }
  
  public String getAddress() {
    return this.address;
  }
  
  public double getAltitude() {
    return this.altitude;
  }
  
  public float getBearing() {
    return this.bearing;
  }
  
  public String getCityName() {
    return this.cityName;
  }
  
  public String getId() {
    return this.id;
  }
  
  public double getLatitude() {
    return this.latitude;
  }
  
  public LatLng getLocation() {
    return this.location;
  }
  
  public double getLongitude() {
    return this.longitude;
  }
  
  public String getName() {
    return this.name;
  }
  
  public float getSpeed() {
    return this.speed;
  }
  
  public String getUid() {
    return this.uid;
  }
  
  public void setAccuracy(float paramFloat) {
    this.accuracy = paramFloat;
  }
  
  public void setAddress(String paramString) {
    this.address = paramString;
  }
  
  public void setAltitude(double paramDouble) {
    this.altitude = paramDouble;
  }
  
  public void setBearing(float paramFloat) {
    this.bearing = paramFloat;
  }
  
  public void setCityName(String paramString) {
    this.cityName = paramString;
  }
  
  public void setId(String paramString) {
    this.id = paramString;
  }
  
  public void setLatitude(double paramDouble) {
    this.latitude = paramDouble;
  }
  
  public void setLocation(LatLng paramLatLng) {
    this.location = paramLatLng;
  }
  
  public void setLongitude(double paramDouble) {
    this.longitude = paramDouble;
  }
  
  public void setName(String paramString) {
    this.name = paramString;
  }
  
  public void setSpeed(float paramFloat) {
    this.speed = paramFloat;
  }
  
  public void setUid(String paramString) {
    this.uid = paramString;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\bean\LocationBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */