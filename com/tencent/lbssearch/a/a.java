package com.tencent.lbssearch.a;

import android.content.Context;
import android.content.pm.PackageManager;

public class a {
  public static String a(Context paramContext) {
    String str;
    try {
      str = (paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128)).metaData.getString("TencentMapSDK");
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      nameNotFoundException.printStackTrace();
      str = "";
    } 
    return str;
  }
  
  public static String a(String... paramVarArgs) {
    String str;
    if (paramVarArgs != null) {
      byte b = 0;
      String str1 = "category=";
      while (true) {
        str = str1;
        if (b < paramVarArgs.length) {
          if (b == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str1);
            stringBuilder.append(paramVarArgs[b]);
            str1 = stringBuilder.toString();
          } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str1);
            stringBuilder.append(",");
            stringBuilder.append(paramVarArgs[b]);
            str1 = stringBuilder.toString();
          } 
          b++;
          continue;
        } 
        break;
      } 
    } else {
      str = "";
    } 
    return str;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */