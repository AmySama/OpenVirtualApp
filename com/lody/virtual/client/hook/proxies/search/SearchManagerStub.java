package com.lody.virtual.client.hook.proxies.search;

import android.content.ComponentName;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import java.lang.reflect.Method;
import mirror.android.app.ISearchManager;

public class SearchManagerStub extends BinderInvocationProxy {
  public SearchManagerStub() {
    super(ISearchManager.Stub.asInterface, "search");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new StaticMethodProxy("launchLegacyAssist"));
    addMethodProxy(new GetSearchableInfo());
  }
  
  private static class GetSearchableInfo extends MethodProxy {
    private GetSearchableInfo() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      ComponentName componentName = (ComponentName)param1VarArgs[0];
      return (componentName != null && VirtualCore.getPM().getActivityInfo(componentName, 0) != null) ? null : param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getSearchableInfo";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\search\SearchManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */