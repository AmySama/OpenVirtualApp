package com.lody.virtual.client.hook.proxies.bluetooth;

import android.os.Build;
import android.os.IInterface;
import android.text.TextUtils;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.client.hook.base.ResultBinderMethodProxy;
import com.lody.virtual.remote.VDeviceConfig;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import mirror.android.bluetooth.IBluetooth;

public class BluetoothStub extends BinderInvocationProxy {
  private static final String SERVER_NAME;
  
  static {
    String str;
    if (Build.VERSION.SDK_INT >= 17) {
      str = "bluetooth_manager";
    } else {
      str = "bluetooth";
    } 
    SERVER_NAME = str;
  }
  
  public BluetoothStub() {
    super(IBluetooth.Stub.asInterface, SERVER_NAME);
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new GetAddress());
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("enable"));
    if (Build.VERSION.SDK_INT >= 17)
      addMethodProxy((MethodProxy)new ResultBinderMethodProxy("registerAdapter") {
            public InvocationHandler createProxy(final IInterface base) {
              return new InvocationHandler() {
                  public Object invoke(Object param2Object, Method param2Method, Object[] param2ArrayOfObject) throws Throwable {
                    if ("getAddress".equals(param2Method.getName()) && (BluetoothStub.null.getDeviceConfig()).enable) {
                      param2Object = (BluetoothStub.null.getDeviceConfig()).bluetoothMac;
                      if (!TextUtils.isEmpty((CharSequence)param2Object))
                        return param2Object; 
                    } 
                    return param2Method.invoke(base, param2ArrayOfObject);
                  }
                };
            }
          }); 
  }
  
  private static class GetAddress extends ReplaceLastPkgMethodProxy {
    public GetAddress() {
      super("getAddress");
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if ((getDeviceConfig()).enable) {
        String str = (getDeviceConfig()).bluetoothMac;
        if (!TextUtils.isEmpty(str))
          return str; 
      } 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\bluetooth\BluetoothStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */