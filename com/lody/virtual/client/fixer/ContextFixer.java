package com.lody.virtual.client.fixer;

import android.content.Context;
import android.content.ContextWrapper;
import com.lody.virtual.client.core.InvocationStubManager;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.proxies.am.ActivityManagerStub;
import com.lody.virtual.client.hook.proxies.graphics.GraphicsStatsStub;
import com.lody.virtual.helper.compat.BuildCompat;
import mirror.android.app.ContextImpl;
import mirror.android.app.ContextImplKitkat;
import mirror.android.content.AttributionSource;
import mirror.android.content.AttributionSourceState;
import mirror.android.content.ContentResolverJBMR2;

public class ContextFixer {
  public static void fixContext(Context paramContext, String paramString) {
    try {
      paramContext.getPackageName();
      int i = 0;
      while (paramContext instanceof ContextWrapper) {
        paramContext = ((ContextWrapper)paramContext).getBaseContext();
        int j = i + 1;
        i = j;
        if (j >= 10)
          return; 
      } 
      ContextImpl.mPackageManager.set(paramContext, null);
      try {
        paramContext.getPackageManager();
      } finally {
        Exception exception = null;
      } 
      InvocationStubManager invocationStubManager = InvocationStubManager.getInstance();
      invocationStubManager.checkEnv(GraphicsStatsStub.class);
      invocationStubManager.checkEnv(ActivityManagerStub.class);
      if (paramString != null) {
        String str = VirtualCore.get().getHostPkg();
        ContextImpl.mBasePackageName.set(paramContext, str);
        ContextImplKitkat.mOpPackageName.set(paramContext, str);
        ContentResolverJBMR2.mPackageName.set(paramContext.getContentResolver(), paramString);
        if (BuildCompat.isS()) {
          Object object = AttributionSource.mAttributionSourceState(ContextImpl.getAttributionSource(paramContext));
          if (object != null) {
            AttributionSourceState.packageName(object, str);
            if (VirtualCore.get().myUid() > 0)
              AttributionSourceState.uid(object, VirtualCore.get().myUid()); 
          } 
        } 
      } 
    } finally {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\fixer\ContextFixer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */