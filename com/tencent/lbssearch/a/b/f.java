package com.tencent.lbssearch.a.b;

import android.text.TextUtils;
import com.tencent.lbssearch.a.a.j;
import com.tencent.lbssearch.a.a.k;
import com.tencent.lbssearch.a.a.l;
import com.tencent.lbssearch.a.a.p;
import com.tencent.lbssearch.object.Location;
import com.tencent.lbssearch.object.result.TransitResultObject;
import java.lang.reflect.Type;

public class f implements k<TransitResultObject.LatLngBounds> {
  public TransitResultObject.LatLngBounds a(l paraml, Type paramType, j paramj) throws p {
    TransitResultObject.LatLngBounds latLngBounds = new TransitResultObject.LatLngBounds();
    String str = paraml.c();
    if (TextUtils.isEmpty(str))
      return null; 
    String[] arrayOfString = str.split(",");
    if (arrayOfString.length == 4) {
      latLngBounds.northeast = new Location(Float.valueOf(arrayOfString[0]).floatValue(), Float.valueOf(arrayOfString[1]).floatValue());
      latLngBounds.southwest = new Location(Float.valueOf(arrayOfString[2]).floatValue(), Float.valueOf(arrayOfString[3]).floatValue());
      return latLngBounds;
    } 
    return null;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\b\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */