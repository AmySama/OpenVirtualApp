package com.tencent.lbssearch.object.param;

import android.text.TextUtils;
import com.tencent.lbssearch.a.a;
import com.tencent.lbssearch.a.b.d;
import com.tencent.lbssearch.object.Location;

public class SearchParam implements ParamObject {
  private static final String BOUNDARY = "boundary";
  
  private static final String DISTANCE_ASCE = "_distance";
  
  private static final String DISTANCE_DESC = "_distance desc";
  
  private static final String FILTER = "filter";
  
  private static final String KEYWORD = "keyword";
  
  private static final String NEARBY = "nearby";
  
  private static final String ORDERBY = "orderby";
  
  private static final String PAGE_INDEX = "page_index";
  
  private static final String PAGE_SIZE = "page_size";
  
  private static final String RECTANGLE = "rectangle";
  
  private static final String REGION = "region";
  
  private Boundary boundary;
  
  private boolean distance_order = true;
  
  private String filter;
  
  private String keyword;
  
  private int page_index = 1;
  
  private int page_size = -1;
  
  private Region region;
  
  public SearchParam boundary(Boundary paramBoundary) {
    this.boundary = paramBoundary;
    return this;
  }
  
  public d buildParameters() {
    String str;
    d d = new d();
    if (!TextUtils.isEmpty(this.keyword))
      d.b("keyword", this.keyword); 
    Boundary boundary = this.boundary;
    if (boundary != null)
      d.b("boundary", boundary.toString()); 
    if (!TextUtils.isEmpty(this.filter))
      d.b("filter", this.filter); 
    boundary = this.region;
    if (boundary != null)
      d.b("region", boundary.toString()); 
    if (this.distance_order) {
      str = "_distance";
    } else {
      str = "_distance desc";
    } 
    d.b("orderby", str);
    int i = this.page_size;
    if (i > 0)
      d.b("page_size", String.valueOf(i)); 
    i = this.page_index;
    if (i > 0)
      d.b("page_index", String.valueOf(i)); 
    return d;
  }
  
  public boolean checkParams() {
    return !(TextUtils.isEmpty(this.keyword) || this.boundary == null);
  }
  
  public SearchParam filter(String... paramVarArgs) {
    this.filter = a.a(paramVarArgs);
    return this;
  }
  
  public SearchParam keyword(String paramString) {
    this.keyword = paramString;
    return this;
  }
  
  public SearchParam orderby(boolean paramBoolean) {
    this.distance_order = paramBoolean;
    return this;
  }
  
  public SearchParam page_index(int paramInt) {
    this.page_index = paramInt;
    return this;
  }
  
  public SearchParam page_size(int paramInt) {
    this.page_size = paramInt;
    return this;
  }
  
  public SearchParam region(Region paramRegion) {
    this.region = paramRegion;
    return this;
  }
  
  static interface Boundary {
    String toString();
  }
  
  public static class Nearby implements Boundary {
    private Location point;
    
    private int r;
    
    public Nearby point(Location param1Location) {
      this.point = param1Location;
      return this;
    }
    
    public Nearby r(int param1Int) {
      this.r = param1Int;
      return this;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("nearby(");
      stringBuilder.append(String.valueOf(this.point.lat));
      stringBuilder.append(",");
      stringBuilder.append(String.valueOf(this.point.lng));
      stringBuilder.append(",");
      stringBuilder.append(String.valueOf(this.r));
      stringBuilder.append(")");
      return stringBuilder.toString();
    }
  }
  
  public static class Rectangle implements Boundary {
    private Location left_bottom;
    
    private Location right_top;
    
    public Rectangle point(Location param1Location1, Location param1Location2) {
      this.left_bottom = param1Location1;
      this.right_top = param1Location2;
      return this;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("rectangle(");
      stringBuilder.append(String.valueOf(this.left_bottom.lat));
      stringBuilder.append(",");
      stringBuilder.append(String.valueOf(this.left_bottom.lng));
      stringBuilder.append(",");
      stringBuilder.append(String.valueOf(this.right_top.lat));
      stringBuilder.append(",");
      stringBuilder.append(String.valueOf(this.right_top.lng));
      stringBuilder.append(")");
      return stringBuilder.toString();
    }
  }
  
  public static class Region implements Boundary {
    private boolean autoExtend = false;
    
    private String poi;
    
    public Region autoExtend(boolean param1Boolean) {
      this.autoExtend = param1Boolean;
      return this;
    }
    
    public Region poi(String param1String) {
      this.poi = param1String;
      return this;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("region(");
      stringBuilder.append(this.poi);
      stringBuilder.append(",");
      stringBuilder.append(this.autoExtend);
      stringBuilder.append(")");
      return stringBuilder.toString();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\param\SearchParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */