package com.lody.virtual.client.hook.providers;

import android.content.AttributionSource;
import android.os.IInterface;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import com.lody.virtual.helper.compat.BuildCompat;
import java.lang.reflect.Method;
import mirror.android.content.AttributionSource;
import mirror.android.content.AttributionSourceState;

public class ExternalProviderHook extends ProviderHook {
  public ExternalProviderHook(IInterface paramIInterface) {
    super(paramIInterface);
  }
  
  protected void processArgs(Method paramMethod, Object... paramVarArgs) {
    if (paramVarArgs != null && paramVarArgs.length > 0)
      if (paramVarArgs[0] instanceof String) {
        String str = (String)paramVarArgs[0];
        if (VirtualCore.get().isAppInstalled(str))
          paramVarArgs[0] = VirtualCore.get().getHostPkg(); 
      } else {
        try {
          if (BuildCompat.isS()) {
            int i = MethodParameterUtils.getIndex(paramVarArgs, AttributionSource.class);
            if (i < 0)
              return; 
            Object object = AttributionSource.mAttributionSourceState(paramVarArgs[i]);
            AttributionSourceState.uid(object, VirtualCore.get().myUid());
            AttributionSourceState.packageName(object, VirtualCore.get().getHostPkg());
          } 
        } catch (Exception exception) {
          exception.printStackTrace();
        } 
      }  
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\providers\ExternalProviderHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */