package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IConnectivityManager extends IInterface {
  LinkProperties getActiveLinkProperties() throws RemoteException;
  
  NetworkInfo getActiveNetworkInfo() throws RemoteException;
  
  NetworkInfo getActiveNetworkInfoForUid(int paramInt, boolean paramBoolean) throws RemoteException;
  
  NetworkInfo[] getAllNetworkInfo() throws RemoteException;
  
  LinkProperties getLinkProperties(int paramInt) throws RemoteException;
  
  NetworkInfo getNetworkInfo(int paramInt) throws RemoteException;
  
  boolean isActiveNetworkMetered() throws RemoteException;
  
  boolean requestRouteToHostAddress(int paramInt1, int paramInt2) throws RemoteException;
  
  public static class Default implements IConnectivityManager {
    public IBinder asBinder() {
      return null;
    }
    
    public LinkProperties getActiveLinkProperties() throws RemoteException {
      return null;
    }
    
    public NetworkInfo getActiveNetworkInfo() throws RemoteException {
      return null;
    }
    
    public NetworkInfo getActiveNetworkInfoForUid(int param1Int, boolean param1Boolean) throws RemoteException {
      return null;
    }
    
    public NetworkInfo[] getAllNetworkInfo() throws RemoteException {
      return null;
    }
    
    public LinkProperties getLinkProperties(int param1Int) throws RemoteException {
      return null;
    }
    
    public NetworkInfo getNetworkInfo(int param1Int) throws RemoteException {
      return null;
    }
    
    public boolean isActiveNetworkMetered() throws RemoteException {
      return false;
    }
    
    public boolean requestRouteToHostAddress(int param1Int1, int param1Int2) throws RemoteException {
      return false;
    }
  }
  
  public static abstract class Stub extends Binder implements IConnectivityManager {
    private static final String DESCRIPTOR = "android.net.IConnectivityManager";
    
    static final int TRANSACTION_getActiveLinkProperties = 7;
    
    static final int TRANSACTION_getActiveNetworkInfo = 1;
    
    static final int TRANSACTION_getActiveNetworkInfoForUid = 2;
    
    static final int TRANSACTION_getAllNetworkInfo = 4;
    
    static final int TRANSACTION_getLinkProperties = 8;
    
    static final int TRANSACTION_getNetworkInfo = 3;
    
    static final int TRANSACTION_isActiveNetworkMetered = 5;
    
    static final int TRANSACTION_requestRouteToHostAddress = 6;
    
    public Stub() {
      attachInterface(this, "android.net.IConnectivityManager");
    }
    
    public static IConnectivityManager asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.net.IConnectivityManager");
      return (iInterface != null && iInterface instanceof IConnectivityManager) ? (IConnectivityManager)iInterface : new Proxy(param1IBinder);
    }
    
    public static IConnectivityManager getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IConnectivityManager param1IConnectivityManager) {
      if (Proxy.sDefaultImpl == null && param1IConnectivityManager != null) {
        Proxy.sDefaultImpl = param1IConnectivityManager;
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
        int i;
        LinkProperties linkProperties;
        NetworkInfo[] arrayOfNetworkInfo;
        boolean bool1;
        switch (param1Int1) {
          default:
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2);
          case 8:
            param1Parcel1.enforceInterface("android.net.IConnectivityManager");
            linkProperties = getLinkProperties(param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            if (linkProperties != null) {
              param1Parcel2.writeInt(1);
              linkProperties.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 7:
            linkProperties.enforceInterface("android.net.IConnectivityManager");
            linkProperties = getActiveLinkProperties();
            param1Parcel2.writeNoException();
            if (linkProperties != null) {
              param1Parcel2.writeInt(1);
              linkProperties.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 6:
            linkProperties.enforceInterface("android.net.IConnectivityManager");
            bool = requestRouteToHostAddress(linkProperties.readInt(), linkProperties.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool);
            return true;
          case 5:
            linkProperties.enforceInterface("android.net.IConnectivityManager");
            bool = isActiveNetworkMetered();
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool);
            return true;
          case 4:
            linkProperties.enforceInterface("android.net.IConnectivityManager");
            arrayOfNetworkInfo = getAllNetworkInfo();
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedArray((Parcelable[])arrayOfNetworkInfo, 1);
            return true;
          case 3:
            arrayOfNetworkInfo.enforceInterface("android.net.IConnectivityManager");
            networkInfo = getNetworkInfo(arrayOfNetworkInfo.readInt());
            param1Parcel2.writeNoException();
            if (networkInfo != null) {
              param1Parcel2.writeInt(1);
              networkInfo.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 2:
            networkInfo.enforceInterface("android.net.IConnectivityManager");
            i = networkInfo.readInt();
            if (networkInfo.readInt() != 0) {
              bool1 = true;
            } else {
              bool1 = false;
            } 
            networkInfo = getActiveNetworkInfoForUid(i, bool1);
            param1Parcel2.writeNoException();
            if (networkInfo != null) {
              param1Parcel2.writeInt(1);
              networkInfo.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 1:
            break;
        } 
        networkInfo.enforceInterface("android.net.IConnectivityManager");
        NetworkInfo networkInfo = getActiveNetworkInfo();
        param1Parcel2.writeNoException();
        if (networkInfo != null) {
          param1Parcel2.writeInt(1);
          networkInfo.writeToParcel(param1Parcel2, 1);
        } else {
          param1Parcel2.writeInt(0);
        } 
        return true;
      } 
      param1Parcel2.writeString("android.net.IConnectivityManager");
      return true;
    }
    
    private static class Proxy implements IConnectivityManager {
      public static IConnectivityManager sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public LinkProperties getActiveLinkProperties() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          LinkProperties linkProperties;
          parcel1.writeInterfaceToken("android.net.IConnectivityManager");
          if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IConnectivityManager.Stub.getDefaultImpl() != null) {
            linkProperties = IConnectivityManager.Stub.getDefaultImpl().getActiveLinkProperties();
            return linkProperties;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            linkProperties = (LinkProperties)LinkProperties.CREATOR.createFromParcel(parcel2);
          } else {
            linkProperties = null;
          } 
          return linkProperties;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public NetworkInfo getActiveNetworkInfo() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          NetworkInfo networkInfo;
          parcel1.writeInterfaceToken("android.net.IConnectivityManager");
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IConnectivityManager.Stub.getDefaultImpl() != null) {
            networkInfo = IConnectivityManager.Stub.getDefaultImpl().getActiveNetworkInfo();
            return networkInfo;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            networkInfo = (NetworkInfo)NetworkInfo.CREATOR.createFromParcel(parcel2);
          } else {
            networkInfo = null;
          } 
          return networkInfo;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public NetworkInfo getActiveNetworkInfoForUid(int param2Int, boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          NetworkInfo networkInfo;
          parcel1.writeInterfaceToken("android.net.IConnectivityManager");
          parcel1.writeInt(param2Int);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IConnectivityManager.Stub.getDefaultImpl() != null) {
            networkInfo = IConnectivityManager.Stub.getDefaultImpl().getActiveNetworkInfoForUid(param2Int, param2Boolean);
            return networkInfo;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            networkInfo = (NetworkInfo)NetworkInfo.CREATOR.createFromParcel(parcel2);
          } else {
            networkInfo = null;
          } 
          return networkInfo;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public NetworkInfo[] getAllNetworkInfo() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.net.IConnectivityManager");
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IConnectivityManager.Stub.getDefaultImpl() != null)
            return IConnectivityManager.Stub.getDefaultImpl().getAllNetworkInfo(); 
          parcel2.readException();
          return (NetworkInfo[])parcel2.createTypedArray(NetworkInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "android.net.IConnectivityManager";
      }
      
      public LinkProperties getLinkProperties(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          LinkProperties linkProperties;
          parcel1.writeInterfaceToken("android.net.IConnectivityManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IConnectivityManager.Stub.getDefaultImpl() != null) {
            linkProperties = IConnectivityManager.Stub.getDefaultImpl().getLinkProperties(param2Int);
            return linkProperties;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            linkProperties = (LinkProperties)LinkProperties.CREATOR.createFromParcel(parcel2);
          } else {
            linkProperties = null;
          } 
          return linkProperties;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public NetworkInfo getNetworkInfo(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          NetworkInfo networkInfo;
          parcel1.writeInterfaceToken("android.net.IConnectivityManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IConnectivityManager.Stub.getDefaultImpl() != null) {
            networkInfo = IConnectivityManager.Stub.getDefaultImpl().getNetworkInfo(param2Int);
            return networkInfo;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            networkInfo = (NetworkInfo)NetworkInfo.CREATOR.createFromParcel(parcel2);
          } else {
            networkInfo = null;
          } 
          return networkInfo;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean isActiveNetworkMetered() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.net.IConnectivityManager");
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(5, parcel1, parcel2, 0) && IConnectivityManager.Stub.getDefaultImpl() != null) {
            bool = IConnectivityManager.Stub.getDefaultImpl().isActiveNetworkMetered();
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
      
      public boolean requestRouteToHostAddress(int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.net.IConnectivityManager");
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(6, parcel1, parcel2, 0) && IConnectivityManager.Stub.getDefaultImpl() != null) {
            bool = IConnectivityManager.Stub.getDefaultImpl().requestRouteToHostAddress(param2Int1, param2Int2);
            return bool;
          } 
          parcel2.readException();
          param2Int1 = parcel2.readInt();
          if (param2Int1 != 0)
            bool = true; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
    }
  }
  
  private static class Proxy implements IConnectivityManager {
    public static IConnectivityManager sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public LinkProperties getActiveLinkProperties() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        LinkProperties linkProperties;
        parcel1.writeInterfaceToken("android.net.IConnectivityManager");
        if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IConnectivityManager.Stub.getDefaultImpl() != null) {
          linkProperties = IConnectivityManager.Stub.getDefaultImpl().getActiveLinkProperties();
          return linkProperties;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          linkProperties = (LinkProperties)LinkProperties.CREATOR.createFromParcel(parcel2);
        } else {
          linkProperties = null;
        } 
        return linkProperties;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public NetworkInfo getActiveNetworkInfo() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        NetworkInfo networkInfo;
        parcel1.writeInterfaceToken("android.net.IConnectivityManager");
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IConnectivityManager.Stub.getDefaultImpl() != null) {
          networkInfo = IConnectivityManager.Stub.getDefaultImpl().getActiveNetworkInfo();
          return networkInfo;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          networkInfo = (NetworkInfo)NetworkInfo.CREATOR.createFromParcel(parcel2);
        } else {
          networkInfo = null;
        } 
        return networkInfo;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public NetworkInfo getActiveNetworkInfoForUid(int param1Int, boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        NetworkInfo networkInfo;
        parcel1.writeInterfaceToken("android.net.IConnectivityManager");
        parcel1.writeInt(param1Int);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IConnectivityManager.Stub.getDefaultImpl() != null) {
          networkInfo = IConnectivityManager.Stub.getDefaultImpl().getActiveNetworkInfoForUid(param1Int, param1Boolean);
          return networkInfo;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          networkInfo = (NetworkInfo)NetworkInfo.CREATOR.createFromParcel(parcel2);
        } else {
          networkInfo = null;
        } 
        return networkInfo;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public NetworkInfo[] getAllNetworkInfo() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.net.IConnectivityManager");
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IConnectivityManager.Stub.getDefaultImpl() != null)
          return IConnectivityManager.Stub.getDefaultImpl().getAllNetworkInfo(); 
        parcel2.readException();
        return (NetworkInfo[])parcel2.createTypedArray(NetworkInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "android.net.IConnectivityManager";
    }
    
    public LinkProperties getLinkProperties(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        LinkProperties linkProperties;
        parcel1.writeInterfaceToken("android.net.IConnectivityManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IConnectivityManager.Stub.getDefaultImpl() != null) {
          linkProperties = IConnectivityManager.Stub.getDefaultImpl().getLinkProperties(param1Int);
          return linkProperties;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          linkProperties = (LinkProperties)LinkProperties.CREATOR.createFromParcel(parcel2);
        } else {
          linkProperties = null;
        } 
        return linkProperties;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public NetworkInfo getNetworkInfo(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        NetworkInfo networkInfo;
        parcel1.writeInterfaceToken("android.net.IConnectivityManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IConnectivityManager.Stub.getDefaultImpl() != null) {
          networkInfo = IConnectivityManager.Stub.getDefaultImpl().getNetworkInfo(param1Int);
          return networkInfo;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          networkInfo = (NetworkInfo)NetworkInfo.CREATOR.createFromParcel(parcel2);
        } else {
          networkInfo = null;
        } 
        return networkInfo;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean isActiveNetworkMetered() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.net.IConnectivityManager");
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(5, parcel1, parcel2, 0) && IConnectivityManager.Stub.getDefaultImpl() != null) {
          bool = IConnectivityManager.Stub.getDefaultImpl().isActiveNetworkMetered();
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
    
    public boolean requestRouteToHostAddress(int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.net.IConnectivityManager");
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(6, parcel1, parcel2, 0) && IConnectivityManager.Stub.getDefaultImpl() != null) {
          bool = IConnectivityManager.Stub.getDefaultImpl().requestRouteToHostAddress(param1Int1, param1Int2);
          return bool;
        } 
        parcel2.readException();
        param1Int1 = parcel2.readInt();
        if (param1Int1 != 0)
          bool = true; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\net\IConnectivityManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */