package com.lody.virtual.client.hook.proxies.persistent_data_block;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import mirror.android.service.persistentdata.IPersistentDataBlockService;

public class PersistentDataBlockServiceStub extends BinderInvocationProxy {
  public PersistentDataBlockServiceStub() {
    super(IPersistentDataBlockService.Stub.TYPE, "persistent_data_block");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("write", Integer.valueOf(-1)));
    Integer integer = Integer.valueOf(0);
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("read", new byte[0]));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("wipe", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getDataBlockSize", integer));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getMaximumDataBlockSize", integer));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("setOemUnlockEnabled", integer));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getOemUnlockEnabled", Boolean.valueOf(false)));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\persistent_data_block\PersistentDataBlockServiceStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */