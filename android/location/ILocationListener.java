package android.location;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILocationListener extends IInterface {
  void onLocationChanged(Location paramLocation) throws RemoteException;
  
  void onProviderDisabled(String paramString) throws RemoteException;
  
  void onProviderEnabled(String paramString) throws RemoteException;
  
  void onStatusChanged(String paramString, int paramInt, Bundle paramBundle) throws RemoteException;
  
  public static class Default implements ILocationListener {
    public IBinder asBinder() {
      return null;
    }
    
    public void onLocationChanged(Location param1Location) throws RemoteException {}
    
    public void onProviderDisabled(String param1String) throws RemoteException {}
    
    public void onProviderEnabled(String param1String) throws RemoteException {}
    
    public void onStatusChanged(String param1String, int param1Int, Bundle param1Bundle) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements ILocationListener {
    private static final String DESCRIPTOR = "android.location.ILocationListener";
    
    static final int TRANSACTION_onLocationChanged = 1;
    
    static final int TRANSACTION_onProviderDisabled = 4;
    
    static final int TRANSACTION_onProviderEnabled = 3;
    
    static final int TRANSACTION_onStatusChanged = 2;
    
    public Stub() {
      attachInterface(this, "android.location.ILocationListener");
    }
    
    public static ILocationListener asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.location.ILocationListener");
      return (iInterface != null && iInterface instanceof ILocationListener) ? (ILocationListener)iInterface : new Proxy(param1IBinder);
    }
    
    public static ILocationListener getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(ILocationListener param1ILocationListener) {
      if (Proxy.sDefaultImpl == null && param1ILocationListener != null) {
        Proxy.sDefaultImpl = param1ILocationListener;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      Location location;
      String str1 = null;
      Bundle bundle = null;
      if (param1Int1 != 1) {
        if (param1Int1 != 2) {
          if (param1Int1 != 3) {
            if (param1Int1 != 4) {
              if (param1Int1 != 1598968902)
                return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
              param1Parcel2.writeString("android.location.ILocationListener");
              return true;
            } 
            param1Parcel1.enforceInterface("android.location.ILocationListener");
            onProviderDisabled(param1Parcel1.readString());
            param1Parcel2.writeNoException();
            return true;
          } 
          param1Parcel1.enforceInterface("android.location.ILocationListener");
          onProviderEnabled(param1Parcel1.readString());
          param1Parcel2.writeNoException();
          return true;
        } 
        param1Parcel1.enforceInterface("android.location.ILocationListener");
        str1 = param1Parcel1.readString();
        param1Int1 = param1Parcel1.readInt();
        if (param1Parcel1.readInt() != 0)
          bundle = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1); 
        onStatusChanged(str1, param1Int1, bundle);
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel1.enforceInterface("android.location.ILocationListener");
      String str2 = str1;
      if (param1Parcel1.readInt() != 0)
        location = (Location)Location.CREATOR.createFromParcel(param1Parcel1); 
      onLocationChanged(location);
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements ILocationListener {
      public static ILocationListener sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "android.location.ILocationListener";
      }
      
      public void onLocationChanged(Location param2Location) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.location.ILocationListener");
          if (param2Location != null) {
            parcel1.writeInt(1);
            param2Location.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && ILocationListener.Stub.getDefaultImpl() != null) {
            ILocationListener.Stub.getDefaultImpl().onLocationChanged(param2Location);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void onProviderDisabled(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.location.ILocationListener");
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && ILocationListener.Stub.getDefaultImpl() != null) {
            ILocationListener.Stub.getDefaultImpl().onProviderDisabled(param2String);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void onProviderEnabled(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.location.ILocationListener");
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && ILocationListener.Stub.getDefaultImpl() != null) {
            ILocationListener.Stub.getDefaultImpl().onProviderEnabled(param2String);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void onStatusChanged(String param2String, int param2Int, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.location.ILocationListener");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && ILocationListener.Stub.getDefaultImpl() != null) {
            ILocationListener.Stub.getDefaultImpl().onStatusChanged(param2String, param2Int, param2Bundle);
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
  
  private static class Proxy implements ILocationListener {
    public static ILocationListener sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "android.location.ILocationListener";
    }
    
    public void onLocationChanged(Location param1Location) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.location.ILocationListener");
        if (param1Location != null) {
          parcel1.writeInt(1);
          param1Location.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && ILocationListener.Stub.getDefaultImpl() != null) {
          ILocationListener.Stub.getDefaultImpl().onLocationChanged(param1Location);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void onProviderDisabled(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.location.ILocationListener");
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && ILocationListener.Stub.getDefaultImpl() != null) {
          ILocationListener.Stub.getDefaultImpl().onProviderDisabled(param1String);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void onProviderEnabled(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.location.ILocationListener");
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && ILocationListener.Stub.getDefaultImpl() != null) {
          ILocationListener.Stub.getDefaultImpl().onProviderEnabled(param1String);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void onStatusChanged(String param1String, int param1Int, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.location.ILocationListener");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && ILocationListener.Stub.getDefaultImpl() != null) {
          ILocationListener.Stub.getDefaultImpl().onStatusChanged(param1String, param1Int, param1Bundle);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\location\ILocationListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */