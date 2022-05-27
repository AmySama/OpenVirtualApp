package com.tencent.lbssearch.object.param;

public enum CoordTypeEnum {
  BAIDU, DEFAULT, GPS, MAPBAR, SOGOU, SOGOUMERCATOR;
  
  static {
    BAIDU = new CoordTypeEnum("BAIDU", 2);
    MAPBAR = new CoordTypeEnum("MAPBAR", 3);
    DEFAULT = new CoordTypeEnum("DEFAULT", 4);
    CoordTypeEnum coordTypeEnum = new CoordTypeEnum("SOGOUMERCATOR", 5);
    SOGOUMERCATOR = coordTypeEnum;
    $VALUES = new CoordTypeEnum[] { GPS, SOGOU, BAIDU, MAPBAR, DEFAULT, coordTypeEnum };
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\param\CoordTypeEnum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */