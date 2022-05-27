package com.lody.virtual.helper.utils;

import android.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class EncodeUtils {
  private static final Map<String, String> sStringPool = new HashMap<String, String>();
  
  public static String decodeBase64(String paramString) {
    synchronized (sStringPool) {
      if (!sStringPool.containsKey(paramString)) {
        String str = new String();
        this(Base64.decode(paramString, 0));
        sStringPool.put(paramString, str);
        paramString = str;
      } else {
        paramString = sStringPool.get(paramString);
      } 
      return paramString;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\EncodeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */