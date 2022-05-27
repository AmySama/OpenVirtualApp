package com.lody.virtual.client.hook.base;

import android.os.Process;
import com.lody.virtual.helper.utils.ArrayUtils;
import java.lang.reflect.Method;

public class ReplaceLastUidMethodProxy extends StaticMethodProxy {
  public ReplaceLastUidMethodProxy(String paramString) {
    super(paramString);
  }
  
  public boolean beforeCall(Object paramObject, Method paramMethod, Object... paramVarArgs) {
    int i = ArrayUtils.indexOfLast(paramVarArgs, Integer.class);
    if (i != -1 && ((Integer)paramVarArgs[i]).intValue() == Process.myUid())
      paramVarArgs[i] = Integer.valueOf(getRealUid()); 
    return super.beforeCall(paramObject, paramMethod, paramVarArgs);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\base\ReplaceLastUidMethodProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */