package com.lody.virtual.helper;

import java.util.HashSet;

public class ActionsBan {
  public static HashSet sActionsBan;
  
  static {
    HashSet<String> hashSet = new HashSet();
    sActionsBan = hashSet;
    hashSet.add("com.vivo.hotfixcollect");
  }
  
  public static boolean isBanAction(String paramString) {
    return sActionsBan.contains(paramString);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\ActionsBan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */