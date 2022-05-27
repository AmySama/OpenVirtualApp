package com.lody.virtual.client.hook.proxies.permissionmgr;

import android.os.IInterface;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodInvocationProxy;
import com.lody.virtual.client.hook.base.MethodInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.client.ipc.VPackageManager;
import com.lody.virtual.helper.utils.Reflect;
import java.lang.reflect.Method;
import mirror.android.app.ActivityThread;

public class PermissionManagerStub extends MethodInvocationProxy<MethodInvocationStub<IInterface>> {
  public PermissionManagerStub() {
    super(new MethodInvocationStub(ActivityThread.sPermissionManager.get()));
  }
  
  public void inject() throws Throwable {
    IInterface iInterface = (IInterface)getInvocationStub().getProxyInterface();
    ActivityThread.sPermissionManager.set(iInterface);
    try {
      if (Reflect.on(VirtualCore.getPM()).field("mPermissionManager").get() != iInterface)
        Reflect.on(VirtualCore.getPM()).set("mPermissionManager", iInterface); 
    } catch (Exception exception) {}
    BinderInvocationStub binderInvocationStub = new BinderInvocationStub((IInterface)getInvocationStub().getBaseInterface());
    binderInvocationStub.copyMethodProxies(getInvocationStub());
    binderInvocationStub.replaceService("permissionmgr");
  }
  
  public boolean isEnvBad() {
    return false;
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new StaticMethodProxy("addOnPermissionsChangeListener") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) {
            return Integer.valueOf(0);
          }
        });
    addMethodProxy((MethodProxy)new StaticMethodProxy("removeOnPermissionsChangeListener") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) {
            return Integer.valueOf(0);
          }
        });
    addMethodProxy((MethodProxy)new StaticMethodProxy("addPermission") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) {
            return Boolean.valueOf(true);
          }
        });
    addMethodProxy((MethodProxy)new StaticMethodProxy("checkPermission") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            return Integer.valueOf(VPackageManager.get().checkPermission((String)param1VarArgs[0], (String)param1VarArgs[1], ((Integer)param1VarArgs[2]).intValue()));
          }
        });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\permissionmgr\PermissionManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */