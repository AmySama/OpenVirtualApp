package com.tencent.tencentmap.mapsdk.map;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.mapsdk.raster.model.GeoPoint;

public class OverlayItem implements Parcelable {
  public static final Parcelable.Creator<OverlayItem> CREATOR = new Parcelable.Creator<OverlayItem>() {
      public final OverlayItem createFromParcel(Parcel param1Parcel) {
        return new OverlayItem(param1Parcel);
      }
      
      public final OverlayItem[] newArray(int param1Int) {
        return new OverlayItem[param1Int];
      }
    };
  
  protected boolean boDragable = true;
  
  private Drawable mMarker;
  
  protected GeoPoint mPoint;
  
  protected final String mSnippet;
  
  protected final String mTitle;
  
  protected OverlayItem(Parcel paramParcel) {
    this.mPoint = (GeoPoint)paramParcel.readValue(GeoPoint.class.getClassLoader());
    this.mTitle = paramParcel.readString();
    this.mSnippet = paramParcel.readString();
  }
  
  public OverlayItem(GeoPoint paramGeoPoint, String paramString1, String paramString2) {
    this.mPoint = paramGeoPoint.Copy();
    this.mTitle = paramString1;
    this.mSnippet = paramString2;
    this.mMarker = null;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public Drawable getMarker() {
    return this.mMarker;
  }
  
  public GeoPoint getPoint() {
    return this.mPoint;
  }
  
  public String getSnippet() {
    return this.mSnippet;
  }
  
  public String getTitle() {
    return this.mTitle;
  }
  
  public boolean isDragable() {
    return this.boDragable;
  }
  
  public void setDragable(boolean paramBoolean) {
    this.boDragable = paramBoolean;
  }
  
  public void setMarker(Drawable paramDrawable) {
    this.mMarker = paramDrawable;
    if (paramDrawable == null)
      return; 
    Rect rect = paramDrawable.getBounds();
    if (rect == null) {
      ItemizedOverlay.boundCenterBottom(this.mMarker);
      return;
    } 
    if (rect.left == 0 && rect.right == 0 && rect.top == 0 && rect.bottom == 0)
      ItemizedOverlay.boundCenterBottom(this.mMarker); 
  }
  
  public void setPoint(GeoPoint paramGeoPoint) {
    this.mPoint = paramGeoPoint.Copy();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeValue(this.mPoint);
    paramParcel.writeString(this.mTitle);
    paramParcel.writeString(this.mSnippet);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\tencentmap\mapsdk\map\OverlayItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */