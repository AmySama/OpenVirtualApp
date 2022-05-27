package com.tencent.lbssearch.object.result;

import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.object.Location;
import java.util.List;

public class MBikeAddress2GeoResultObject extends BaseObject {
  public MBikeReverseAddressResult result;
  
  public class MBikeReverseAddressResult {
    public MBikeAdInfo ad_info;
    
    public String address;
    
    public AddressComponent address_component;
    
    public Reference address_reference;
    
    public FormatterAddress formatted_addresses;
    
    public List<Poi> pois;
    
    public class FormatterAddress {
      public String recommend;
      
      public String rough;
    }
    
    public class MBikeAdInfo {
      public String adcode;
      
      public String city;
      
      public String city_code_mo;
      
      public String district;
      
      public Location location;
      
      public String name;
      
      public String nation;
      
      public String province;
    }
    
    public class Poi {
      public float _distance;
      
      public String address;
      
      public String category;
      
      public String id;
      
      public Location location;
      
      public String title;
    }
    
    public class Reference {
      public Area crossroad;
      
      public Area famous_area;
      
      public Area landmark_l1;
      
      public Area landmark_l2;
      
      public Area street;
      
      public Area street_number;
      
      public Area water;
      
      public class Area {
        public String _dir_desc;
        
        public float _distance;
        
        public Location location;
        
        public String title;
      }
    }
    
    public class Area {
      public String _dir_desc;
      
      public float _distance;
      
      public Location location;
      
      public String title;
    }
  }
  
  public class FormatterAddress {
    public String recommend;
    
    public String rough;
  }
  
  public class MBikeAdInfo {
    public String adcode;
    
    public String city;
    
    public String city_code_mo;
    
    public String district;
    
    public Location location;
    
    public String name;
    
    public String nation;
    
    public String province;
  }
  
  public class Poi {
    public float _distance;
    
    public String address;
    
    public String category;
    
    public String id;
    
    public Location location;
    
    public String title;
  }
  
  public class Reference {
    public Area crossroad;
    
    public Area famous_area;
    
    public Area landmark_l1;
    
    public Area landmark_l2;
    
    public Area street;
    
    public Area street_number;
    
    public Area water;
    
    public class Area {
      public String _dir_desc;
      
      public float _distance;
      
      public Location location;
      
      public String title;
    }
  }
  
  public class Area {
    public String _dir_desc;
    
    public float _distance;
    
    public Location location;
    
    public String title;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\result\MBikeAddress2GeoResultObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */