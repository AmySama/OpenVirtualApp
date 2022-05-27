package com.lody.virtual.client.hook.base;

import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.helper.utils.VLog;
import mirror.RefStaticMethod;
import mirror.android.os.ServiceManager;

public abstract class BinderInvocationProxy extends MethodInvocationProxy<BinderInvocationStub> {
  protected String mServiceName;
  
  public BinderInvocationProxy(IInterface paramIInterface, String paramString) {
    this(new BinderInvocationStub(paramIInterface), paramString);
  }
  
  public BinderInvocationProxy(BinderInvocationStub paramBinderInvocationStub, String paramString) {
    super(paramBinderInvocationStub);
    if (paramBinderInvocationStub.getBaseInterface() == null)
      VLog.d("BinderInvocationProxy", "Unable to build HookDelegate: %s.", new Object[] { paramString }); 
    this.mServiceName = paramString;
  }
  
  public BinderInvocationProxy(Class<?> paramClass, String paramString) {
    this(new BinderInvocationStub(paramClass, getService(paramString)), paramString);
  }
  
  public BinderInvocationProxy(RefStaticMethod<IInterface> paramRefStaticMethod, String paramString) {
    this(new BinderInvocationStub(paramRefStaticMethod, getService(paramString)), paramString);
  }
  
  private static IBinder getService(String paramString) {
    return (IBinder)ServiceManager.getService.call(new Object[] { paramString });
  }
  
  public void inject() throws Throwable {
    getInvocationStub().replaceService(this.mServiceName);
  }
  
  public boolean isEnvBad() {
    RefStaticMethod refStaticMethod = ServiceManager.getService;
    boolean bool = true;
    IBinder iBinder = (IBinder)refStaticMethod.call(new Object[] { this.mServiceName });
    if (iBinder == null || getInvocationStub() == iBinder)
      bool = false; 
    return bool;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\base\BinderInvocationProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */