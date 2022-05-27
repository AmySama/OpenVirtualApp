package com.lody.virtual.client.hook.proxies.appwidget;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import mirror.com.android.internal.appwidget.IAppWidgetService;

public class AppWidgetManagerStub extends BinderInvocationProxy {
  public AppWidgetManagerStub() {
    super(IAppWidgetService.Stub.asInterface, "appwidget");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    Boolean bool = Boolean.valueOf(false);
    Integer integer = Integer.valueOf(0);
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("startListening", new int[0]));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("stopListening", integer));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("allocateAppWidgetId", integer));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("deleteAppWidgetId", integer));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("deleteHost", integer));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("deleteAllHosts", integer));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getAppWidgetViews", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getAppWidgetIdsForHost", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("createAppWidgetConfigIntentSender", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("updateAppWidgetIds", integer));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("updateAppWidgetOptions", integer));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getAppWidgetOptions", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("partiallyUpdateAppWidgetIds", integer));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("updateAppWidgetProvider", integer));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("notifyAppWidgetViewDataChanged", integer));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getInstalledProvidersForProfile", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getAppWidgetInfo", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("hasBindAppWidgetPermission", bool));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("setBindAppWidgetPermission", integer));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("bindAppWidgetId", bool));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("bindRemoteViewsService", integer));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("unbindRemoteViewsService", integer));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getAppWidgetIds", new int[0]));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("isBoundWidgetPackage", bool));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\appwidget\AppWidgetManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */