package com.lody.virtual.client.hook.proxies.mount;

import android.app.usage.StorageStats;
import android.content.pm.PackageManager;
import android.os.IInterface;
import android.os.ParcelableException;
import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.client.ipc.VPackageManager;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.ArrayUtils;
import java.lang.reflect.Method;
import mirror.RefStaticMethod;
import mirror.android.app.usage.StorageStats;
import mirror.android.os.mount.IMountService;
import mirror.android.os.storage.IStorageManager;

@Inject(MethodProxies.class)
public class MountServiceStub extends BinderInvocationProxy {
  public MountServiceStub() {
    super(getInterfaceMethod(), "mount");
  }
  
  private static RefStaticMethod<IInterface> getInterfaceMethod() {
    return BuildCompat.isOreo() ? IStorageManager.Stub.asInterface : IMountService.Stub.asInterface;
  }
  
  private StorageStats queryStatsForPackage(String paramString, int paramInt) {
    StorageStats storageStats;
    if (VPackageManager.get().getApplicationInfo(paramString, 0, paramInt) != null) {
      storageStats = (StorageStats)StorageStats.ctor.newInstance();
      StorageStats.cacheBytes.set(storageStats, 0L);
      StorageStats.codeBytes.set(storageStats, 0L);
      StorageStats.dataBytes.set(storageStats, 0L);
      return storageStats;
    } 
    throw new ParcelableException(new PackageManager.NameNotFoundException(storageStats));
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getTotalBytes"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getCacheBytes"));
    addMethodProxy((MethodProxy)new StaticMethodProxy("getCacheQuotaBytes") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            if (param1VarArgs[param1VarArgs.length - 1] instanceof Integer)
              param1VarArgs[param1VarArgs.length - 1] = Integer.valueOf(getRealUid()); 
            return param1Method.invoke(param1Object, param1VarArgs);
          }
        });
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("queryStatsForUser") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            replaceLastUserId(param1VarArgs);
            return super.call(param1Object, param1Method, param1VarArgs);
          }
        });
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("queryExternalStatsForUser") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            replaceLastUserId(param1VarArgs);
            return super.call(param1Object, param1Method, param1VarArgs);
          }
        });
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("queryStatsForUid"));
    addMethodProxy((MethodProxy)new StaticMethodProxy("queryStatsForPackage") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            int i = ArrayUtils.indexOfFirst(param1VarArgs, String.class);
            int j = ArrayUtils.indexOfLast(param1VarArgs, Integer.class);
            if (i != -1 && j != -1) {
              param1Object = param1VarArgs[i];
              j = ((Integer)param1VarArgs[j]).intValue();
              return MountServiceStub.this.queryStatsForPackage((String)param1Object, j);
            } 
            replaceLastUserId(param1VarArgs);
            return super.call(param1Object, param1Method, param1VarArgs);
          }
        });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\mount\MountServiceStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */