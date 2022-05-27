package com.lody.virtual.client.hook.secondary;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.lody.virtual.client.core.VirtualCore;
import java.io.FileDescriptor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

abstract class StubBinder implements IBinder {
  private Context context;
  
  private IBinder mBase;
  
  private ClassLoader mClassLoader;
  
  private IInterface mInterface;
  
  StubBinder(Context paramContext, ClassLoader paramClassLoader, IBinder paramIBinder) {
    this.context = paramContext;
    this.mClassLoader = paramClassLoader;
    this.mBase = paramIBinder;
  }
  
  public abstract InvocationHandler createHandler(Class<?> paramClass, IInterface paramIInterface);
  
  public void dump(FileDescriptor paramFileDescriptor, String[] paramArrayOfString) throws RemoteException {
    this.mBase.dump(paramFileDescriptor, paramArrayOfString);
  }
  
  public void dumpAsync(FileDescriptor paramFileDescriptor, String[] paramArrayOfString) throws RemoteException {
    this.mBase.dumpAsync(paramFileDescriptor, paramArrayOfString);
  }
  
  public String getAppPkg() {
    return this.context.getPackageName();
  }
  
  public String getHostPkg() {
    return VirtualCore.get().getHostPkg();
  }
  
  public String getInterfaceDescriptor() throws RemoteException {
    return this.mBase.getInterfaceDescriptor();
  }
  
  public boolean isBinderAlive() {
    return this.mBase.isBinderAlive();
  }
  
  public void linkToDeath(IBinder.DeathRecipient paramDeathRecipient, int paramInt) throws RemoteException {
    this.mBase.linkToDeath(paramDeathRecipient, paramInt);
  }
  
  public boolean pingBinder() {
    return this.mBase.pingBinder();
  }
  
  public IInterface queryLocalInterface(String paramString) {
    if (this.mInterface == null) {
      StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
      if (arrayOfStackTraceElement == null || arrayOfStackTraceElement.length <= 1)
        return null; 
      int i = arrayOfStackTraceElement.length;
      String str = null;
      paramString = str;
      byte b = 0;
      while (true) {
        IInterface iInterface;
        Exception exception;
        if (b < i) {
          String str1;
          IInterface iInterface1;
          Exception exception1;
          StackTraceElement stackTraceElement = arrayOfStackTraceElement[b];
          if (stackTraceElement.isNativeMethod()) {
            String str2 = str;
            str1 = paramString;
          } else {
            IInterface iInterface2;
            try {
              Method method = this.mClassLoader.loadClass(str1.getClassName()).getDeclaredMethod(str1.getMethodName(), new Class[] { IBinder.class });
              String str2 = str;
              str1 = paramString;
              if ((method.getModifiers() & 0x8) != 0) {
                method.setAccessible(true);
                Class<?> clazz = method.getReturnType();
                str2 = str;
                str1 = paramString;
                if (clazz.isInterface()) {
                  boolean bool = IInterface.class.isAssignableFrom(clazz);
                  str2 = str;
                  str1 = paramString;
                  if (bool) {
                    try {
                      IInterface iInterface3 = (IInterface)method.invoke(null, new Object[] { this.mBase });
                      iInterface2 = iInterface3;
                    } catch (Exception exception2) {}
                    Class<?> clazz1 = clazz;
                    iInterface1 = iInterface2;
                  } 
                } 
              } 
            } catch (Exception exception3) {
              exception1 = exception2;
              iInterface1 = iInterface2;
            } 
          } 
          b++;
          exception = exception1;
          iInterface = iInterface1;
          continue;
        } 
        if (exception == null || iInterface == null)
          return null; 
        InvocationHandler invocationHandler = createHandler((Class<?>)exception, iInterface);
        this.mInterface = (IInterface)Proxy.newProxyInstance(this.mClassLoader, new Class[] { (Class)exception }, invocationHandler);
        return this.mInterface;
      } 
    } 
    return this.mInterface;
  }
  
  public boolean transact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2) throws RemoteException {
    return this.mBase.transact(paramInt1, paramParcel1, paramParcel2, paramInt2);
  }
  
  public boolean unlinkToDeath(IBinder.DeathRecipient paramDeathRecipient, int paramInt) {
    return this.mBase.unlinkToDeath(paramDeathRecipient, paramInt);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\secondary\StubBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */