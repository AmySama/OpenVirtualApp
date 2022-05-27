package com.lody.virtual.client.hook.proxies.window.session;

import android.os.Build;
import android.view.WindowManager;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.helper.utils.ArrayUtils;
import java.lang.reflect.Method;

class BaseMethodProxy extends StaticMethodProxy {
  private boolean mDrawOverlays = false;
  
  public BaseMethodProxy(String paramString) {
    super(paramString);
  }
  
  public boolean beforeCall(Object paramObject, Method paramMethod, Object... paramVarArgs) {
    this.mDrawOverlays = false;
    int i = ArrayUtils.indexOfFirst(paramVarArgs, WindowManager.LayoutParams.class);
    if (i != -1) {
      paramObject = paramVarArgs[i];
      if (paramObject != null) {
        ((WindowManager.LayoutParams)paramObject).packageName = getHostPkg();
        i = ((WindowManager.LayoutParams)paramObject).type;
        if (i == 2002 || i == 2003 || i == 2006 || i == 2007 || i == 2010 || i == 2038)
          this.mDrawOverlays = true; 
        if (Build.VERSION.SDK_INT >= 26 && VirtualCore.get().getTargetSdkVersion() >= 26 && this.mDrawOverlays)
          ((WindowManager.LayoutParams)paramObject).type = 2038; 
      } 
    } 
    return true;
  }
  
  protected boolean isDrawOverlays() {
    return this.mDrawOverlays;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\window\session\BaseMethodProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */