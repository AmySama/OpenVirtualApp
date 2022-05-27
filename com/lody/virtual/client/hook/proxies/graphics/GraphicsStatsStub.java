package com.lody.virtual.client.hook.proxies.graphics;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import mirror.android.view.IGraphicsStats;

public class GraphicsStatsStub extends BinderInvocationProxy {
  public GraphicsStatsStub() {
    super(IGraphicsStats.Stub.asInterface, "graphicsstats");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("requestBufferForProcess"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\graphics\GraphicsStatsStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */