package com.tencent.lbssearch.a.d.a;

import java.util.Map;

public class c {
  public static String a(Map<String, String> paramMap) {
    return a(paramMap, "ISO-8859-1");
  }
  
  public static String a(Map<String, String> paramMap, String paramString) {
    String str = paramMap.get("Content-Type");
    if (str != null) {
      String[] arrayOfString = str.split(";");
      for (byte b = 1; b < arrayOfString.length; b++) {
        String[] arrayOfString1 = arrayOfString[b].trim().split("=");
        if (arrayOfString1.length == 2 && arrayOfString1[0].equals("charset"))
          return arrayOfString1[1]; 
      } 
    } 
    return paramString;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\d\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */