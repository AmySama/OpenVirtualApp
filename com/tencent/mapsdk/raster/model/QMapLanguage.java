package com.tencent.mapsdk.raster.model;

public enum QMapLanguage {
  QMapLanguage_en, QMapLanguage_zh;
  
  static {
    QMapLanguage qMapLanguage = new QMapLanguage("QMapLanguage_en", 1);
    QMapLanguage_en = qMapLanguage;
    $VALUES = new QMapLanguage[] { QMapLanguage_zh, qMapLanguage };
  }
  
  public static String getLanguageCode(QMapLanguage paramQMapLanguage) {
    return (paramQMapLanguage == null || paramQMapLanguage.name() == null || paramQMapLanguage.name().indexOf("_") == -1) ? "en" : paramQMapLanguage.name().substring(paramQMapLanguage.name().indexOf("_") + 1);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\QMapLanguage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */