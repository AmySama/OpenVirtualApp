package com.lody.virtual.client.hook.proxies.system;

import android.net.wifi.IWifiScanner;
import android.os.Bundle;
import android.os.Handler;
import android.os.IInterface;
import android.os.Looper;
import android.os.Messenger;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import java.util.ArrayList;
import mirror.android.net.wifi.WifiScanner;
import mirror.android.os.ServiceManager;

public class WifiScannerStub extends BinderInvocationProxy {
  private static final String SERVICE_NAME = "wifiscanner";
  
  public WifiScannerStub() {
    super((IInterface)new EmptyWifiScannerImpl(), "wifiscanner");
  }
  
  public void inject() throws Throwable {
    if (ServiceManager.checkService.call(new Object[] { "wifiscanner" }) == null)
      super.inject(); 
  }
  
  static class EmptyWifiScannerImpl extends IWifiScanner.Stub {
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    
    public Bundle getAvailableChannels(int param1Int) {
      Bundle bundle = new Bundle();
      bundle.putIntegerArrayList((String)WifiScanner.GET_AVAILABLE_CHANNELS_EXTRA.get(), new ArrayList(0));
      return bundle;
    }
    
    public Messenger getMessenger() {
      return new Messenger(this.mHandler);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\system\WifiScannerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */