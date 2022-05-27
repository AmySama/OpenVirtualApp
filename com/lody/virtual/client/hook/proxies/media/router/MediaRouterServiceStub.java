package com.lody.virtual.client.hook.proxies.media.router;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import mirror.android.media.IMediaRouterService;

public class MediaRouterServiceStub extends BinderInvocationProxy {
  public MediaRouterServiceStub() {
    super(IMediaRouterService.Stub.asInterface, "media_router");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("registerClientAsUser"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\media\router\MediaRouterServiceStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */