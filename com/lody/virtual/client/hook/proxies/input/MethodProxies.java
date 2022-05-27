package com.lody.virtual.client.hook.proxies.input;

import android.view.inputmethod.EditorInfo;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.helper.utils.ArrayUtils;
import java.lang.reflect.Method;

class MethodProxies {
  static class StartInput extends StartInputOrWindowGainedFocus {
    public String getMethodName() {
      return "startInput";
    }
  }
  
  static class StartInputOrWindowGainedFocus extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      int i = ArrayUtils.indexOfFirst(param1VarArgs, EditorInfo.class);
      if (i != -1)
        ((EditorInfo)param1VarArgs[i]).packageName = getHostPkg(); 
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "startInputOrWindowGainedFocus";
    }
  }
  
  static class WindowGainedFocus extends StartInputOrWindowGainedFocus {
    public String getMethodName() {
      return "windowGainedFocus";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\input\MethodProxies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */