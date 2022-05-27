package com.lody.virtual.client.hook.proxies.camera;

import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import java.lang.reflect.Method;
import mirror.android.camera.ICameraService;

public class CameraServiceStub extends BinderInvocationProxy {
  private static final String SERVER_NAME = "media.camera";
  
  public CameraServiceStub() {
    super(ICameraService.Stub.asInterface, "media.camera");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("connect"));
    addMethodProxy((MethodProxy)new StaticMethodProxy("connectDevice") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            if (param1VarArgs[2] instanceof String)
              param1VarArgs[2] = VirtualCore.get().getHostPkg(); 
            return super.call(param1Object, param1Method, param1VarArgs);
          }
        });
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("connectLegacy"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\camera\CameraServiceStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */