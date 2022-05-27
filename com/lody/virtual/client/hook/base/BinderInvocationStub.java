package com.lody.virtual.client.hook.base;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import com.lody.virtual.client.core.ServiceLocalManager;
import com.lody.virtual.client.core.VirtualCore;
import java.io.FileDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import mirror.RefStaticMethod;
import mirror.android.os.ServiceManager;

public class BinderInvocationStub extends MethodInvocationStub<IInterface> implements IBinder {
  private static final String TAG = BinderInvocationStub.class.getSimpleName();
  
  private IBinder mBaseBinder;
  
  public BinderInvocationStub(IInterface paramIInterface) {
    super(paramIInterface);
    if (getBaseInterface() != null) {
      IBinder iBinder = getBaseInterface().asBinder();
    } else {
      paramIInterface = null;
    } 
    this.mBaseBinder = (IBinder)paramIInterface;
    addMethodProxy(new AsBinder());
  }
  
  public BinderInvocationStub(Class<?> paramClass, IBinder paramIBinder) {
    this(asInterface(paramClass, paramIBinder));
  }
  
  public BinderInvocationStub(RefStaticMethod<IInterface> paramRefStaticMethod, IBinder paramIBinder) {
    this(asInterface(paramRefStaticMethod, paramIBinder));
  }
  
  private static IInterface asInterface(Class<?> paramClass, IBinder paramIBinder) {
    String str;
    if (paramClass == null)
      return null; 
    if (paramIBinder == null)
      try {
        str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("Could not create stub because binder = null, stubClass=");
        stringBuilder.append(paramClass);
        Log.w(str, stringBuilder.toString());
        return null;
      } catch (Exception exception) {
        str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not create stub ");
        stringBuilder.append(paramClass.getName());
        stringBuilder.append(". Cause: ");
        stringBuilder.append(exception);
        Log.d(str, stringBuilder.toString());
        return null;
      }  
    return (IInterface)paramClass.getMethod("asInterface", new Class[] { IBinder.class }).invoke(null, new Object[] { str });
  }
  
  private static IInterface asInterface(RefStaticMethod<IInterface> paramRefStaticMethod, IBinder paramIBinder) {
    return (paramRefStaticMethod == null || paramIBinder == null) ? null : (IInterface)paramRefStaticMethod.call(new Object[] { paramIBinder });
  }
  
  public void dump(FileDescriptor paramFileDescriptor, String[] paramArrayOfString) throws RemoteException {
    this.mBaseBinder.dump(paramFileDescriptor, paramArrayOfString);
  }
  
  public void dumpAsync(FileDescriptor paramFileDescriptor, String[] paramArrayOfString) throws RemoteException {
    this.mBaseBinder.dumpAsync(paramFileDescriptor, paramArrayOfString);
  }
  
  public IBinder getBaseBinder() {
    return this.mBaseBinder;
  }
  
  public Context getContext() {
    return VirtualCore.get().getContext();
  }
  
  public String getInterfaceDescriptor() throws RemoteException {
    return this.mBaseBinder.getInterfaceDescriptor();
  }
  
  public boolean isBinderAlive() {
    return this.mBaseBinder.isBinderAlive();
  }
  
  public void linkToDeath(IBinder.DeathRecipient paramDeathRecipient, int paramInt) throws RemoteException {
    this.mBaseBinder.linkToDeath(paramDeathRecipient, paramInt);
  }
  
  public boolean pingBinder() {
    return this.mBaseBinder.pingBinder();
  }
  
  public IInterface queryLocalInterface(String paramString) {
    return getProxyInterface();
  }
  
  public void replaceService(String paramString) {
    if (this.mBaseBinder != null) {
      ((Map<String, BinderInvocationStub>)ServiceManager.sCache.get()).put(paramString, this);
      ServiceLocalManager.addService(paramString, this);
    } 
  }
  
  public boolean transact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2) throws RemoteException {
    return this.mBaseBinder.transact(paramInt1, paramParcel1, paramParcel2, paramInt2);
  }
  
  public boolean unlinkToDeath(IBinder.DeathRecipient paramDeathRecipient, int paramInt) {
    return this.mBaseBinder.unlinkToDeath(paramDeathRecipient, paramInt);
  }
  
  private final class AsBinder extends MethodProxy {
    private AsBinder() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return BinderInvocationStub.this;
    }
    
    public String getMethodName() {
      return "asBinder";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\base\BinderInvocationStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */