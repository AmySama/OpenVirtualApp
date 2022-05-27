package com.lody.virtual.client.fixer;

import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.text.TextUtils;

public class ComponentFixer {
  public static String fixComponentClassName(String paramString1, String paramString2) {
    if (paramString2 != null) {
      if (paramString2.charAt(0) == '.') {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paramString1);
        stringBuilder.append(paramString2);
        return stringBuilder.toString();
      } 
      return paramString2;
    } 
    return null;
  }
  
  public static void fixComponentInfo(ComponentInfo paramComponentInfo) {
    if (paramComponentInfo != null) {
      if (TextUtils.isEmpty(paramComponentInfo.processName))
        paramComponentInfo.processName = paramComponentInfo.packageName; 
      paramComponentInfo.name = fixComponentClassName(paramComponentInfo.packageName, paramComponentInfo.name);
      if (paramComponentInfo.processName == null)
        paramComponentInfo.processName = paramComponentInfo.applicationInfo.processName; 
    } 
  }
  
  public static void fixOutsideApplicationInfo(ApplicationInfo paramApplicationInfo) {
    if (paramApplicationInfo != null)
      paramApplicationInfo.uid = 9000; 
  }
  
  public static void fixOutsideComponentInfo(ComponentInfo paramComponentInfo) {
    if (paramComponentInfo != null)
      fixOutsideApplicationInfo(paramComponentInfo.applicationInfo); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\fixer\ComponentFixer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */