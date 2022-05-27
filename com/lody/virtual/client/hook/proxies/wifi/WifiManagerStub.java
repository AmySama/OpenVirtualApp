package com.lody.virtual.client.hook.proxies.wifi;

import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Log;
import com.lody.virtual.client.core.SettingConfig;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import com.lody.virtual.client.ipc.VirtualLocationManager;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.Reflect;
import com.lody.virtual.remote.vloc.VWifi;
import com.stub.StubApp;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;
import mirror.android.net.wifi.IWifiManager;
import mirror.android.net.wifi.WifiInfo;
import mirror.android.net.wifi.WifiManager;
import mirror.android.net.wifi.WifiSsid;

public class WifiManagerStub extends BinderInvocationProxy {
  public WifiManagerStub() {
    super(IWifiManager.Stub.asInterface, "wifi");
  }
  
  private static int InetAddress_to_hex(InetAddress paramInetAddress) {
    byte[] arrayOfByte = paramInetAddress.getAddress();
    byte b = 0;
    int i = 0;
    while (b < 4) {
      i |= (arrayOfByte[b] & 0xFF) << b * 8;
      b++;
    } 
    return i;
  }
  
  private static ScanResult cloneScanResult(Parcelable paramParcelable) {
    Parcel parcel = Parcel.obtain();
    paramParcelable.writeToParcel(parcel, 0);
    parcel.setDataPosition(0);
    ScanResult scanResult = (ScanResult)Reflect.on(paramParcelable).field("CREATOR").call("createFromParcel", new Object[] { parcel }).get();
    parcel.recycle();
    return scanResult;
  }
  
  private DhcpInfo createDhcpInfo(IPInfo paramIPInfo) {
    DhcpInfo dhcpInfo = new DhcpInfo();
    dhcpInfo.ipAddress = paramIPInfo.ip_hex;
    dhcpInfo.netmask = paramIPInfo.netmask_hex;
    dhcpInfo.dns1 = 67372036;
    dhcpInfo.dns2 = 134744072;
    return dhcpInfo;
  }
  
  private static WifiInfo createWifiInfo(SettingConfig.FakeWifiStatus paramFakeWifiStatus) {
    WifiInfo wifiInfo = (WifiInfo)WifiInfo.ctor.newInstance();
    IPInfo iPInfo = getIPInfo();
    if (iPInfo != null) {
      InetAddress inetAddress = iPInfo.addr;
    } else {
      iPInfo = null;
    } 
    WifiInfo.mNetworkId.set(wifiInfo, 1);
    WifiInfo.mSupplicantState.set(wifiInfo, SupplicantState.COMPLETED);
    WifiInfo.mBSSID.set(wifiInfo, paramFakeWifiStatus.getBSSID());
    WifiInfo.mMacAddress.set(wifiInfo, paramFakeWifiStatus.getMAC());
    WifiInfo.mIpAddress.set(wifiInfo, iPInfo);
    WifiInfo.mLinkSpeed.set(wifiInfo, 65);
    if (Build.VERSION.SDK_INT >= 21)
      WifiInfo.mFrequency.set(wifiInfo, 5000); 
    WifiInfo.mRssi.set(wifiInfo, 200);
    if (WifiInfo.mWifiSsid != null) {
      WifiInfo.mWifiSsid.set(wifiInfo, WifiSsid.createFromAsciiEncoded.call(new Object[] { paramFakeWifiStatus.getSSID() }));
    } else {
      WifiInfo.mSSID.set(wifiInfo, paramFakeWifiStatus.getSSID());
    } 
    return wifiInfo;
  }
  
  private static IPInfo getIPInfo() {
    try {
      Iterator<NetworkInterface> iterator = Collections.<NetworkInterface>list(NetworkInterface.getNetworkInterfaces()).iterator();
      while (iterator.hasNext()) {
        NetworkInterface networkInterface = iterator.next();
        for (InetAddress inetAddress : Collections.<InetAddress>list(networkInterface.getInetAddresses())) {
          if (!inetAddress.isLoopbackAddress()) {
            String str = inetAddress.getHostAddress().toUpperCase();
            if (isIPv4Address(str)) {
              IPInfo iPInfo = new IPInfo();
              this();
              iPInfo.addr = inetAddress;
              iPInfo.intf = networkInterface;
              iPInfo.ip = str;
              iPInfo.ip_hex = InetAddress_to_hex(inetAddress);
              iPInfo.netmask_hex = netmask_to_hex(((InterfaceAddress)networkInterface.getInterfaceAddresses().get(0)).getNetworkPrefixLength());
              return iPInfo;
            } 
          } 
        } 
      } 
    } catch (SocketException socketException) {
      socketException.printStackTrace();
    } 
    return null;
  }
  
  private static boolean isIPv4Address(String paramString) {
    return Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$").matcher(paramString).matches();
  }
  
  private static int netmask_to_hex(int paramInt) {
    byte b = 0;
    int i = 0;
    for (int j = 1; b < paramInt; j <<= 1) {
      i |= j;
      b++;
    } 
    return i;
  }
  
  public void copy2ScanResult(ScanResult paramScanResult, VWifi paramVWifi) {
    paramScanResult.SSID = paramVWifi.ssid;
    paramScanResult.BSSID = paramVWifi.bssid;
    paramScanResult.capabilities = paramVWifi.capabilities;
    paramScanResult.level = paramVWifi.level;
    paramScanResult.frequency = paramVWifi.frequency;
  }
  
  public void inject() throws Throwable {
    super.inject();
    WifiManager wifiManager = (WifiManager)VirtualCore.get().getContext().getSystemService("wifi");
    if (WifiManager.mService != null) {
      try {
        WifiManager.mService.set(wifiManager, ((BinderInvocationStub)getInvocationStub()).getProxyInterface());
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
    } else if (WifiManager.sService != null) {
      try {
        WifiManager.sService.set(((BinderInvocationStub)getInvocationStub()).getProxyInterface());
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
    } 
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy(new MethodProxy() {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            return (getConfig().getFakeWifiStatus() != null) ? Boolean.valueOf(true) : super.call(param1Object, param1Method, param1VarArgs);
          }
          
          public String getMethodName() {
            return "isWifiEnabled";
          }
        });
    addMethodProxy(new MethodProxy() {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            return (getConfig().getFakeWifiStatus() != null) ? Integer.valueOf(3) : super.call(param1Object, param1Method, param1VarArgs);
          }
          
          public String getMethodName() {
            return "getWifiEnabledState";
          }
        });
    addMethodProxy(new MethodProxy() {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            if (getConfig().getFakeWifiStatus() != null) {
              WifiManagerStub.IPInfo iPInfo = WifiManagerStub.getIPInfo();
              if (iPInfo != null)
                return WifiManagerStub.this.createDhcpInfo(iPInfo); 
            } 
            return super.call(param1Object, param1Method, param1VarArgs);
          }
          
          public String getMethodName() {
            return "createDhcpInfo";
          }
        });
    addMethodProxy(new GetConnectionInfo());
    addMethodProxy((MethodProxy)new GetScanResults());
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getBatchedScanResults"));
    addMethodProxy((MethodProxy)new RemoveWorkSourceMethodProxy("acquireWifiLock"));
    addMethodProxy((MethodProxy)new RemoveWorkSourceMethodProxy("updateWifiLockWorkSource"));
    if (Build.VERSION.SDK_INT > 21)
      addMethodProxy((MethodProxy)new RemoveWorkSourceMethodProxy("startLocationRestrictedScan")); 
    if (Build.VERSION.SDK_INT >= 19)
      addMethodProxy((MethodProxy)new RemoveWorkSourceMethodProxy("requestBatchedScan")); 
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("setWifiEnabled"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getConfiguredNetworks"));
    addMethodProxy((MethodProxy)new StaticMethodProxy("getWifiApConfiguration") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            param1Object = ((WifiManager)StubApp.getOrigApplicationContext(WifiManagerStub.this.getContext().getApplicationContext()).getSystemService("wifi")).getConfiguredNetworks();
            if (!param1Object.isEmpty())
              return param1Object.get(0); 
            param1Object = new WifiConfiguration();
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("AndroidAP_");
            stringBuilder1.append((new Random()).nextInt(9000));
            stringBuilder1.append(1000);
            ((WifiConfiguration)param1Object).SSID = stringBuilder1.toString();
            ((WifiConfiguration)param1Object).allowedKeyManagement.set(4);
            String str = UUID.randomUUID().toString();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(str.substring(0, 8));
            stringBuilder2.append(str.substring(9, 13));
            ((WifiConfiguration)param1Object).preSharedKey = stringBuilder2.toString();
            return param1Object;
          }
        });
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("setWifiApConfiguration", Integer.valueOf(0)));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("startLocalOnlyHotspot"));
    if (BuildCompat.isOreo()) {
      addMethodProxy((MethodProxy)new RemoveWorkSourceMethodProxy("startScan") {
            public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
              MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
              return super.call(param1Object, param1Method, param1VarArgs);
            }
          });
    } else if (Build.VERSION.SDK_INT >= 19) {
      addMethodProxy((MethodProxy)new RemoveWorkSourceMethodProxy("startScan"));
    } 
  }
  
  private final class GetConnectionInfo extends MethodProxy {
    private GetConnectionInfo() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      SettingConfig.FakeWifiStatus fakeWifiStatus = getConfig().getFakeWifiStatus();
      if (fakeWifiStatus != null)
        return WifiManagerStub.createWifiInfo(fakeWifiStatus); 
      WifiInfo wifiInfo = (WifiInfo)param1Method.invoke(param1Object, param1VarArgs);
      if (wifiInfo != null)
        if (isFakeLocationEnable()) {
          WifiInfo.mBSSID.set(wifiInfo, "00:00:00:00:00:00");
          WifiInfo.mMacAddress.set(wifiInfo, "00:00:00:00:00:00");
        } else if ((getDeviceConfig()).enable) {
          param1Object = (getDeviceConfig()).wifiMac;
          if (!TextUtils.isEmpty((CharSequence)param1Object))
            WifiInfo.mMacAddress.set(wifiInfo, param1Object); 
        }  
      return wifiInfo;
    }
    
    public String getMethodName() {
      return "getConnectionInfo";
    }
  }
  
  private final class GetScanResults extends ReplaceCallingPkgMethodProxy {
    public GetScanResults() {
      super("getScanResults");
    }
    
    public Object afterCall(Object param1Object1, Method param1Method, Object[] param1ArrayOfObject, Object param1Object2) throws Throwable {
      if (isFakeLocationEnable())
        try {
          List<ScanResult> list = (List)param1Object2;
          ArrayList<Object> arrayList = new ArrayList();
          this();
          param1Object1 = VirtualLocationManager.get().getAllWifi(getAppUserId(), getAppPkg());
          if (param1Object1 != null) {
            if (list.size() > 0 && param1Object1.size() > 0) {
              param1Object2 = list.get(0);
              ((ScanResult)param1Object2).BSSID = ((VWifi)param1Object1.get(0)).bssid;
              arrayList.add(param1Object2);
              if (list.size() > 1 && param1Object1.size() > 1) {
                param1Object2 = list.get(1);
                ((ScanResult)param1Object2).BSSID = ((VWifi)param1Object1.get(1)).bssid;
                arrayList.add(param1Object2);
              } 
              if (list.size() > 2 && param1Object1.size() > 2) {
                ScanResult scanResult = list.get(2);
                scanResult.BSSID = ((VWifi)param1Object1.get(2)).bssid;
                arrayList.add(scanResult);
              } 
              return arrayList;
            } 
            return new ArrayList();
          } 
          return new ArrayList();
        } catch (Exception exception) {
          Log.e("wcnmd", "取wifi失败返回");
          return new ArrayList();
        }  
      return param1Object2;
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (isFakeLocationEnable())
        Log.e("wcnm", "getScanResults"); 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
  
  public static class IPInfo {
    InetAddress addr;
    
    NetworkInterface intf;
    
    String ip;
    
    int ip_hex;
    
    int netmask_hex;
  }
  
  private class RemoveWorkSourceMethodProxy extends StaticMethodProxy {
    RemoveWorkSourceMethodProxy(String param1String) {
      super(param1String);
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      int i = ArrayUtils.indexOfFirst(param1VarArgs, WorkSource.class);
      if (i >= 0)
        param1VarArgs[i] = null; 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\wifi\WifiManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */