package com.lody.virtual.helper.compat;

import java.util.Arrays;

public class ObjectsCompat {
  public static boolean equals(Object paramObject1, Object paramObject2) {
    boolean bool;
    if (paramObject1 == null) {
      if (paramObject2 == null) {
        bool = true;
      } else {
        bool = false;
      } 
    } else {
      bool = paramObject1.equals(paramObject2);
    } 
    return bool;
  }
  
  public static int hash(Object... paramVarArgs) {
    return Arrays.hashCode(paramVarArgs);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\ObjectsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */