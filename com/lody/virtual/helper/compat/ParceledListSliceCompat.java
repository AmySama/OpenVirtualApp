package com.lody.virtual.helper.compat;

import java.lang.reflect.Method;
import java.util.List;
import mirror.android.content.pm.ParceledListSlice;
import mirror.android.content.pm.ParceledListSliceJBMR2;

public class ParceledListSliceCompat {
  public static Object create(List paramList) {
    if (ParceledListSliceJBMR2.ctor != null)
      return ParceledListSliceJBMR2.ctor.newInstance(new Object[] { paramList }); 
    Object object = ParceledListSlice.ctor.newInstance();
    for (Object object1 : paramList) {
      ParceledListSlice.append.call(object, new Object[] { object1 });
    } 
    ParceledListSlice.setLastSlice.call(object, new Object[] { Boolean.valueOf(true) });
    return object;
  }
  
  public static boolean isReturnParceledListSlice(Method paramMethod) {
    boolean bool;
    if (paramMethod != null && paramMethod.getReturnType() == ParceledListSlice.TYPE) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\ParceledListSliceCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */