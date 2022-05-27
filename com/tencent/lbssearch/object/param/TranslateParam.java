package com.tencent.lbssearch.object.param;

import com.tencent.lbssearch.a.b.d;
import com.tencent.lbssearch.object.Location;
import java.util.ArrayList;
import java.util.List;

public class TranslateParam implements ParamObject {
  private static final String LOCATIONS = "locations";
  
  private static final String TYPES = "type";
  
  private List<Location> locations;
  
  private CoordTypeEnum type = CoordTypeEnum.DEFAULT;
  
  public TranslateParam addLocation(Location paramLocation) {
    if (this.locations == null)
      this.locations = new ArrayList<Location>(); 
    this.locations.add(paramLocation);
    return this;
  }
  
  public d buildParameters() {
    d d = new d();
    List<Location> list = this.locations;
    if (list != null && list.size() > 0) {
      byte b = 0;
      String str = "";
      while (b < this.locations.size()) {
        String str1;
        if (b != 0) {
          str1 = ";";
        } else {
          str1 = "";
        } 
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(str1);
        stringBuilder2.append(String.valueOf(((Location)this.locations.get(b)).lat));
        stringBuilder2.append(",");
        stringBuilder2.append(String.valueOf(((Location)this.locations.get(b)).lng));
        String str2 = stringBuilder2.toString();
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str);
        stringBuilder1.append(str2);
        str = stringBuilder1.toString();
        b++;
      } 
      d.b("locations", str);
    } 
    switch (this.type) {
      default:
        return d;
      case null:
        d.b("type", "6");
      case null:
        d.b("type", "5");
      case null:
        d.b("type", "4");
      case null:
        d.b("type", "3");
      case null:
        d.b("type", "2");
      case null:
        break;
    } 
    d.b("type", "1");
  }
  
  public boolean checkParams() {
    return !(this.locations == null);
  }
  
  public TranslateParam coord_type(CoordTypeEnum paramCoordTypeEnum) {
    this.type = paramCoordTypeEnum;
    return this;
  }
  
  public TranslateParam locations(Location... paramVarArgs) {
    if (this.locations == null)
      this.locations = new ArrayList<Location>(); 
    for (byte b = 0; b < paramVarArgs.length; b++)
      this.locations.add(paramVarArgs[b]); 
    return this;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\param\TranslateParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */