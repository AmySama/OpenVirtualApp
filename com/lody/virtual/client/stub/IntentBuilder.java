package com.lody.virtual.client.stub;

import android.app.IServiceConnection;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.Process;
import com.lody.virtual.remote.ServiceData;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class IntentBuilder {
  private static final AtomicInteger sServiceBindCounter = new AtomicInteger(0);
  
  public static Intent createBindProxyServiceIntent(int paramInt1, boolean paramBoolean, ServiceInfo paramServiceInfo, Intent paramIntent, int paramInt2, int paramInt3, IServiceConnection paramIServiceConnection) {
    Intent intent = new Intent();
    String str = StubManifest.getStubServiceName(paramInt1);
    intent.setClassName(StubManifest.getStubPackageName(paramBoolean), str);
    intent.setType(String.format(Locale.ENGLISH, "bind_service_%d_%d_%s|%s", new Object[] { Integer.valueOf(Process.myPid()), Integer.valueOf(sServiceBindCounter.getAndIncrement()), paramServiceInfo.packageName, paramServiceInfo.name }));
    (new ServiceData.ServiceBindData(new ComponentName(paramServiceInfo.packageName, paramServiceInfo.name), paramServiceInfo, paramIntent, paramInt2, paramInt3, paramIServiceConnection)).saveToIntent(intent);
    return intent;
  }
  
  public static Intent createStartProxyServiceIntent(int paramInt1, boolean paramBoolean, ServiceInfo paramServiceInfo, Intent paramIntent, int paramInt2) {
    Intent intent = new Intent();
    String str = StubManifest.getStubServiceName(paramInt1);
    intent.setClassName(StubManifest.getStubPackageName(paramBoolean), str);
    (new ServiceData.ServiceStartData(new ComponentName(paramServiceInfo.packageName, paramServiceInfo.name), paramServiceInfo, paramIntent, paramInt2)).saveToIntent(intent);
    return intent;
  }
  
  public static Intent createStopProxyServiceIntent(int paramInt1, boolean paramBoolean, ComponentName paramComponentName, int paramInt2, int paramInt3, IBinder paramIBinder) {
    Intent intent = new Intent();
    String str = StubManifest.getStubServiceName(paramInt1);
    intent.setClassName(StubManifest.getStubPackageName(paramBoolean), str);
    (new ServiceData.ServiceStopData(paramInt2, paramComponentName, paramInt3, paramIBinder)).saveToIntent(intent);
    return intent;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\IntentBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */