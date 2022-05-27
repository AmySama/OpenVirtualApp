package com.lody.virtual.client.hook.proxies.pm;

import android.content.Context;
import android.os.IInterface;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodInvocationProxy;
import com.lody.virtual.client.hook.base.MethodInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.Reflect;
import mirror.android.app.ActivityThread;
import mirror.huawei.android.app.HwApiCacheManagerEx;

@Inject(MethodProxies.class)
public final class PackageManagerStub extends MethodInvocationProxy<MethodInvocationStub<IInterface>> {
  public PackageManagerStub() {
    super(new MethodInvocationStub(ActivityThread.sPackageManager.get()));
  }
  
  public void inject() throws Throwable {
    IInterface iInterface = (IInterface)getInvocationStub().getProxyInterface();
    ActivityThread.sPackageManager.set(iInterface);
    BinderInvocationStub binderInvocationStub = new BinderInvocationStub((IInterface)getInvocationStub().getBaseInterface());
    binderInvocationStub.copyMethodProxies(getInvocationStub());
    binderInvocationStub.replaceService("package");
    try {
      Context context = (Context)Reflect.on(VirtualCore.mainThread()).call("getSystemContext").get();
      if (Reflect.on(context).field("mPackageManager").get() != null)
        Reflect.on(context).field("mPackageManager").set("mPM", iInterface); 
    } finally {
      iInterface = null;
    } 
    if (HwApiCacheManagerEx.mPkg != null)
      HwApiCacheManagerEx.mPkg.set(HwApiCacheManagerEx.getDefault.call(new Object[0]), VirtualCore.getPM()); 
  }
  
  public boolean isEnvBad() {
    boolean bool;
    if (getInvocationStub().getProxyInterface() != ActivityThread.sPackageManager.get()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    Boolean bool1 = Boolean.valueOf(true);
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("addPermissionAsync", bool1));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("addPermission", bool1));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("performDexOpt", bool1));
    Boolean bool2 = Boolean.valueOf(false);
    Integer integer = Integer.valueOf(0);
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("performDexOptIfNeeded", bool2));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("performDexOptSecondary", bool1));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("addOnPermissionsChangeListener", integer));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("removeOnPermissionsChangeListener", integer));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("shouldShowRequestPermissionRationale"));
    if (BuildCompat.isOreo()) {
      addMethodProxy((MethodProxy)new ResultStaticMethodProxy("notifyDexLoad", integer));
      addMethodProxy((MethodProxy)new ResultStaticMethodProxy("notifyPackageUse", integer));
      addMethodProxy((MethodProxy)new ResultStaticMethodProxy("setInstantAppCookie", bool2));
      addMethodProxy((MethodProxy)new ResultStaticMethodProxy("isInstantApp", bool2));
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\pm\PackageManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */