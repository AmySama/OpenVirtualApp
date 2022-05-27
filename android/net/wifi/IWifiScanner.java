package android.net.wifi;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWifiScanner extends IInterface {
  Bundle getAvailableChannels(int paramInt) throws RemoteException;
  
  Messenger getMessenger() throws RemoteException;
  
  public static class Default implements IWifiScanner {
    public IBinder asBinder() {
      return null;
    }
    
    public Bundle getAvailableChannels(int param1Int) throws RemoteException {
      return null;
    }
    
    public Messenger getMessenger() throws RemoteException {
      return null;
    }
  }
  
  public static abstract class Stub extends Binder implements IWifiScanner {
    private static final String DESCRIPTOR = "android.net.wifi.IWifiScanner";
    
    static final int TRANSACTION_getAvailableChannels = 2;
    
    static final int TRANSACTION_getMessenger = 1;
    
    public Stub() {
      attachInterface(this, "android.net.wifi.IWifiScanner");
    }
    
    public static IWifiScanner asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.net.wifi.IWifiScanner");
      return (iInterface != null && iInterface instanceof IWifiScanner) ? (IWifiScanner)iInterface : new Proxy(param1IBinder);
    }
    
    public static IWifiScanner getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IWifiScanner param1IWifiScanner) {
      if (Proxy.sDefaultImpl == null && param1IWifiScanner != null) {
        Proxy.sDefaultImpl = param1IWifiScanner;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      Bundle bundle;
      if (param1Int1 != 1) {
        if (param1Int1 != 2) {
          if (param1Int1 != 1598968902)
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
          param1Parcel2.writeString("android.net.wifi.IWifiScanner");
          return true;
        } 
        param1Parcel1.enforceInterface("android.net.wifi.IWifiScanner");
        bundle = getAvailableChannels(param1Parcel1.readInt());
        param1Parcel2.writeNoException();
        if (bundle != null) {
          param1Parcel2.writeInt(1);
          bundle.writeToParcel(param1Parcel2, 1);
        } else {
          param1Parcel2.writeInt(0);
        } 
        return true;
      } 
      bundle.enforceInterface("android.net.wifi.IWifiScanner");
      Messenger messenger = getMessenger();
      param1Parcel2.writeNoException();
      if (messenger != null) {
        param1Parcel2.writeInt(1);
        messenger.writeToParcel(param1Parcel2, 1);
      } else {
        param1Parcel2.writeInt(0);
      } 
      return true;
    }
    
    private static class Proxy implements IWifiScanner {
      public static IWifiScanner sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public Bundle getAvailableChannels(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          Bundle bundle;
          parcel1.writeInterfaceToken("android.net.wifi.IWifiScanner");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IWifiScanner.Stub.getDefaultImpl() != null) {
            bundle = IWifiScanner.Stub.getDefaultImpl().getAvailableChannels(param2Int);
            return bundle;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            bundle = (Bundle)Bundle.CREATOR.createFromParcel(parcel2);
          } else {
            bundle = null;
          } 
          return bundle;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "android.net.wifi.IWifiScanner";
      }
      
      public Messenger getMessenger() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          Messenger messenger;
          parcel1.writeInterfaceToken("android.net.wifi.IWifiScanner");
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IWifiScanner.Stub.getDefaultImpl() != null) {
            messenger = IWifiScanner.Stub.getDefaultImpl().getMessenger();
            return messenger;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            messenger = (Messenger)Messenger.CREATOR.createFromParcel(parcel2);
          } else {
            messenger = null;
          } 
          return messenger;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
    }
  }
  
  private static class Proxy implements IWifiScanner {
    public static IWifiScanner sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public Bundle getAvailableChannels(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        Bundle bundle;
        parcel1.writeInterfaceToken("android.net.wifi.IWifiScanner");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IWifiScanner.Stub.getDefaultImpl() != null) {
          bundle = IWifiScanner.Stub.getDefaultImpl().getAvailableChannels(param1Int);
          return bundle;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          bundle = (Bundle)Bundle.CREATOR.createFromParcel(parcel2);
        } else {
          bundle = null;
        } 
        return bundle;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "android.net.wifi.IWifiScanner";
    }
    
    public Messenger getMessenger() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        Messenger messenger;
        parcel1.writeInterfaceToken("android.net.wifi.IWifiScanner");
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IWifiScanner.Stub.getDefaultImpl() != null) {
          messenger = IWifiScanner.Stub.getDefaultImpl().getMessenger();
          return messenger;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          messenger = (Messenger)Messenger.CREATOR.createFromParcel(parcel2);
        } else {
          messenger = null;
        } 
        return messenger;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\net\wifi\IWifiScanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */