package com.lody.virtual.client.hook.providers;

import android.os.IInterface;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.os.VUserHandle;
import java.lang.reflect.Method;
import mirror.android.content.AttributionSource;
import mirror.android.content.AttributionSourceState;

public class InternalProviderHook extends ProviderHook {
  public InternalProviderHook(IInterface paramIInterface) {
    super(paramIInterface);
  }
  
  public void processArgs(Method paramMethod, Object... paramVarArgs) {
    if (paramVarArgs != null && paramVarArgs.length > 0 && paramVarArgs[0] instanceof android.content.AttributionSource)
      try {
        Object object = AttributionSource.mAttributionSourceState(paramVarArgs[0]);
        AttributionSourceState.uid(object, VUserHandle.getAppId(VClient.get().getVUid()));
        AttributionSourceState.packageName(object, VirtualCore.get().getHostPkg());
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\providers\InternalProviderHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */