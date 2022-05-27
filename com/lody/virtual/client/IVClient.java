package com.lody.virtual.client;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface IVClient extends IInterface {
  IBinder acquireProviderClient(ProviderInfo paramProviderInfo) throws RemoteException;
  
  IBinder createProxyService(ComponentName paramComponentName, IBinder paramIBinder) throws RemoteException;
  
  void finishActivity(IBinder paramIBinder) throws RemoteException;
  
  boolean finishReceiver(IBinder paramIBinder) throws RemoteException;
  
  IBinder getAppThread() throws RemoteException;
  
  String getDebugInfo() throws RemoteException;
  
  List<ActivityManager.RunningServiceInfo> getServices() throws RemoteException;
  
  IBinder getToken() throws RemoteException;
  
  boolean isAppRunning() throws RemoteException;
  
  void scheduleNewIntent(String paramString, IBinder paramIBinder, Intent paramIntent) throws RemoteException;
  
  public static class Default implements IVClient {
    public IBinder acquireProviderClient(ProviderInfo param1ProviderInfo) throws RemoteException {
      return null;
    }
    
    public IBinder asBinder() {
      return null;
    }
    
    public IBinder createProxyService(ComponentName param1ComponentName, IBinder param1IBinder) throws RemoteException {
      return null;
    }
    
    public void finishActivity(IBinder param1IBinder) throws RemoteException {}
    
    public boolean finishReceiver(IBinder param1IBinder) throws RemoteException {
      return false;
    }
    
    public IBinder getAppThread() throws RemoteException {
      return null;
    }
    
    public String getDebugInfo() throws RemoteException {
      return null;
    }
    
    public List<ActivityManager.RunningServiceInfo> getServices() throws RemoteException {
      return null;
    }
    
    public IBinder getToken() throws RemoteException {
      return null;
    }
    
    public boolean isAppRunning() throws RemoteException {
      return false;
    }
    
    public void scheduleNewIntent(String param1String, IBinder param1IBinder, Intent param1Intent) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IVClient {
    private static final String DESCRIPTOR = "com.lody.virtual.client.IVClient";
    
    static final int TRANSACTION_acquireProviderClient = 4;
    
    static final int TRANSACTION_createProxyService = 3;
    
    static final int TRANSACTION_finishActivity = 2;
    
    static final int TRANSACTION_finishReceiver = 9;
    
    static final int TRANSACTION_getAppThread = 5;
    
    static final int TRANSACTION_getDebugInfo = 8;
    
    static final int TRANSACTION_getServices = 10;
    
    static final int TRANSACTION_getToken = 6;
    
    static final int TRANSACTION_isAppRunning = 7;
    
    static final int TRANSACTION_scheduleNewIntent = 1;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.client.IVClient");
    }
    
    public static IVClient asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.client.IVClient");
      return (iInterface != null && iInterface instanceof IVClient) ? (IVClient)iInterface : new Proxy(param1IBinder);
    }
    
    public static IVClient getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IVClient param1IVClient) {
      if (Proxy.sDefaultImpl == null && param1IVClient != null) {
        Proxy.sDefaultImpl = param1IVClient;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1598968902) {
        boolean bool;
        List<ActivityManager.RunningServiceInfo> list;
        String str1;
        IBinder iBinder1;
        ComponentName componentName;
        Intent intent;
        ProviderInfo providerInfo1 = null;
        ProviderInfo providerInfo2 = null;
        ProviderInfo providerInfo3 = null;
        switch (param1Int1) {
          default:
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2);
          case 10:
            param1Parcel1.enforceInterface("com.lody.virtual.client.IVClient");
            list = getServices();
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list);
            return true;
          case 9:
            list.enforceInterface("com.lody.virtual.client.IVClient");
            bool = finishReceiver(list.readStrongBinder());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool);
            return true;
          case 8:
            list.enforceInterface("com.lody.virtual.client.IVClient");
            str1 = getDebugInfo();
            param1Parcel2.writeNoException();
            param1Parcel2.writeString(str1);
            return true;
          case 7:
            str1.enforceInterface("com.lody.virtual.client.IVClient");
            bool = isAppRunning();
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool);
            return true;
          case 6:
            str1.enforceInterface("com.lody.virtual.client.IVClient");
            iBinder1 = getToken();
            param1Parcel2.writeNoException();
            param1Parcel2.writeStrongBinder(iBinder1);
            return true;
          case 5:
            iBinder1.enforceInterface("com.lody.virtual.client.IVClient");
            iBinder1 = getAppThread();
            param1Parcel2.writeNoException();
            param1Parcel2.writeStrongBinder(iBinder1);
            return true;
          case 4:
            iBinder1.enforceInterface("com.lody.virtual.client.IVClient");
            providerInfo2 = providerInfo3;
            if (iBinder1.readInt() != 0)
              providerInfo2 = (ProviderInfo)ProviderInfo.CREATOR.createFromParcel((Parcel)iBinder1); 
            iBinder1 = acquireProviderClient(providerInfo2);
            param1Parcel2.writeNoException();
            param1Parcel2.writeStrongBinder(iBinder1);
            return true;
          case 3:
            iBinder1.enforceInterface("com.lody.virtual.client.IVClient");
            providerInfo2 = providerInfo1;
            if (iBinder1.readInt() != 0)
              componentName = (ComponentName)ComponentName.CREATOR.createFromParcel((Parcel)iBinder1); 
            iBinder1 = createProxyService(componentName, iBinder1.readStrongBinder());
            param1Parcel2.writeNoException();
            param1Parcel2.writeStrongBinder(iBinder1);
            return true;
          case 2:
            iBinder1.enforceInterface("com.lody.virtual.client.IVClient");
            finishActivity(iBinder1.readStrongBinder());
            param1Parcel2.writeNoException();
            return true;
          case 1:
            break;
        } 
        iBinder1.enforceInterface("com.lody.virtual.client.IVClient");
        String str2 = iBinder1.readString();
        IBinder iBinder2 = iBinder1.readStrongBinder();
        if (iBinder1.readInt() != 0)
          intent = (Intent)Intent.CREATOR.createFromParcel((Parcel)iBinder1); 
        scheduleNewIntent(str2, iBinder2, intent);
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel2.writeString("com.lody.virtual.client.IVClient");
      return true;
    }
    
    private static class Proxy implements IVClient {
      public static IVClient sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder acquireProviderClient(ProviderInfo param2ProviderInfo) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
          if (param2ProviderInfo != null) {
            parcel1.writeInt(1);
            param2ProviderInfo.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null)
            return IVClient.Stub.getDefaultImpl().acquireProviderClient(param2ProviderInfo); 
          parcel2.readException();
          return parcel2.readStrongBinder();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public IBinder createProxyService(ComponentName param2ComponentName, IBinder param2IBinder) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
          if (param2ComponentName != null) {
            parcel1.writeInt(1);
            param2ComponentName.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeStrongBinder(param2IBinder);
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null)
            return IVClient.Stub.getDefaultImpl().createProxyService(param2ComponentName, param2IBinder); 
          parcel2.readException();
          return parcel2.readStrongBinder();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void finishActivity(IBinder param2IBinder) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
          parcel1.writeStrongBinder(param2IBinder);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null) {
            IVClient.Stub.getDefaultImpl().finishActivity(param2IBinder);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean finishReceiver(IBinder param2IBinder) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
          parcel1.writeStrongBinder(param2IBinder);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(9, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null) {
            bool = IVClient.Stub.getDefaultImpl().finishReceiver(param2IBinder);
            return bool;
          } 
          parcel2.readException();
          int i = parcel2.readInt();
          if (i != 0)
            bool = true; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public IBinder getAppThread() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
          if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null)
            return IVClient.Stub.getDefaultImpl().getAppThread(); 
          parcel2.readException();
          return parcel2.readStrongBinder();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getDebugInfo() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
          if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null)
            return IVClient.Stub.getDefaultImpl().getDebugInfo(); 
          parcel2.readException();
          return parcel2.readString();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.client.IVClient";
      }
      
      public List<ActivityManager.RunningServiceInfo> getServices() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
          if (!this.mRemote.transact(10, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null)
            return IVClient.Stub.getDefaultImpl().getServices(); 
          parcel2.readException();
          return parcel2.createTypedArrayList(ActivityManager.RunningServiceInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public IBinder getToken() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
          if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null)
            return IVClient.Stub.getDefaultImpl().getToken(); 
          parcel2.readException();
          return parcel2.readStrongBinder();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean isAppRunning() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(7, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null) {
            bool = IVClient.Stub.getDefaultImpl().isAppRunning();
            return bool;
          } 
          parcel2.readException();
          int i = parcel2.readInt();
          if (i != 0)
            bool = true; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void scheduleNewIntent(String param2String, IBinder param2IBinder, Intent param2Intent) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
          parcel1.writeString(param2String);
          parcel1.writeStrongBinder(param2IBinder);
          if (param2Intent != null) {
            parcel1.writeInt(1);
            param2Intent.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null) {
            IVClient.Stub.getDefaultImpl().scheduleNewIntent(param2String, param2IBinder, param2Intent);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
    }
  }
  
  private static class Proxy implements IVClient {
    public static IVClient sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder acquireProviderClient(ProviderInfo param1ProviderInfo) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
        if (param1ProviderInfo != null) {
          parcel1.writeInt(1);
          param1ProviderInfo.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null)
          return IVClient.Stub.getDefaultImpl().acquireProviderClient(param1ProviderInfo); 
        parcel2.readException();
        return parcel2.readStrongBinder();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public IBinder createProxyService(ComponentName param1ComponentName, IBinder param1IBinder) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
        if (param1ComponentName != null) {
          parcel1.writeInt(1);
          param1ComponentName.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeStrongBinder(param1IBinder);
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null)
          return IVClient.Stub.getDefaultImpl().createProxyService(param1ComponentName, param1IBinder); 
        parcel2.readException();
        return parcel2.readStrongBinder();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void finishActivity(IBinder param1IBinder) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
        parcel1.writeStrongBinder(param1IBinder);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null) {
          IVClient.Stub.getDefaultImpl().finishActivity(param1IBinder);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean finishReceiver(IBinder param1IBinder) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
        parcel1.writeStrongBinder(param1IBinder);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(9, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null) {
          bool = IVClient.Stub.getDefaultImpl().finishReceiver(param1IBinder);
          return bool;
        } 
        parcel2.readException();
        int i = parcel2.readInt();
        if (i != 0)
          bool = true; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public IBinder getAppThread() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
        if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null)
          return IVClient.Stub.getDefaultImpl().getAppThread(); 
        parcel2.readException();
        return parcel2.readStrongBinder();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getDebugInfo() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
        if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null)
          return IVClient.Stub.getDefaultImpl().getDebugInfo(); 
        parcel2.readException();
        return parcel2.readString();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.client.IVClient";
    }
    
    public List<ActivityManager.RunningServiceInfo> getServices() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
        if (!this.mRemote.transact(10, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null)
          return IVClient.Stub.getDefaultImpl().getServices(); 
        parcel2.readException();
        return parcel2.createTypedArrayList(ActivityManager.RunningServiceInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public IBinder getToken() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
        if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null)
          return IVClient.Stub.getDefaultImpl().getToken(); 
        parcel2.readException();
        return parcel2.readStrongBinder();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean isAppRunning() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(7, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null) {
          bool = IVClient.Stub.getDefaultImpl().isAppRunning();
          return bool;
        } 
        parcel2.readException();
        int i = parcel2.readInt();
        if (i != 0)
          bool = true; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void scheduleNewIntent(String param1String, IBinder param1IBinder, Intent param1Intent) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.client.IVClient");
        parcel1.writeString(param1String);
        parcel1.writeStrongBinder(param1IBinder);
        if (param1Intent != null) {
          parcel1.writeInt(1);
          param1Intent.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IVClient.Stub.getDefaultImpl() != null) {
          IVClient.Stub.getDefaultImpl().scheduleNewIntent(param1String, param1IBinder, param1Intent);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\IVClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */