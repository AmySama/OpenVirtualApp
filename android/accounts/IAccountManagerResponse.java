package android.accounts;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAccountManagerResponse extends IInterface {
  void onError(int paramInt, String paramString) throws RemoteException;
  
  void onResult(Bundle paramBundle) throws RemoteException;
  
  public static class Default implements IAccountManagerResponse {
    public IBinder asBinder() {
      return null;
    }
    
    public void onError(int param1Int, String param1String) throws RemoteException {}
    
    public void onResult(Bundle param1Bundle) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IAccountManagerResponse {
    private static final String DESCRIPTOR = "android.accounts.IAccountManagerResponse";
    
    static final int TRANSACTION_onError = 2;
    
    static final int TRANSACTION_onResult = 1;
    
    public Stub() {
      attachInterface(this, "android.accounts.IAccountManagerResponse");
    }
    
    public static IAccountManagerResponse asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.accounts.IAccountManagerResponse");
      return (iInterface != null && iInterface instanceof IAccountManagerResponse) ? (IAccountManagerResponse)iInterface : new Proxy(param1IBinder);
    }
    
    public static IAccountManagerResponse getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IAccountManagerResponse param1IAccountManagerResponse) {
      if (Proxy.sDefaultImpl == null && param1IAccountManagerResponse != null) {
        Proxy.sDefaultImpl = param1IAccountManagerResponse;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1) {
        if (param1Int1 != 2) {
          if (param1Int1 != 1598968902)
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
          param1Parcel2.writeString("android.accounts.IAccountManagerResponse");
          return true;
        } 
        param1Parcel1.enforceInterface("android.accounts.IAccountManagerResponse");
        onError(param1Parcel1.readInt(), param1Parcel1.readString());
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel1.enforceInterface("android.accounts.IAccountManagerResponse");
      if (param1Parcel1.readInt() != 0) {
        Bundle bundle = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1);
      } else {
        param1Parcel1 = null;
      } 
      onResult((Bundle)param1Parcel1);
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements IAccountManagerResponse {
      public static IAccountManagerResponse sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "android.accounts.IAccountManagerResponse";
      }
      
      public void onError(int param2Int, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.accounts.IAccountManagerResponse");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IAccountManagerResponse.Stub.getDefaultImpl() != null) {
            IAccountManagerResponse.Stub.getDefaultImpl().onError(param2Int, param2String);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void onResult(Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.accounts.IAccountManagerResponse");
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IAccountManagerResponse.Stub.getDefaultImpl() != null) {
            IAccountManagerResponse.Stub.getDefaultImpl().onResult(param2Bundle);
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
  
  private static class Proxy implements IAccountManagerResponse {
    public static IAccountManagerResponse sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "android.accounts.IAccountManagerResponse";
    }
    
    public void onError(int param1Int, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.accounts.IAccountManagerResponse");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IAccountManagerResponse.Stub.getDefaultImpl() != null) {
          IAccountManagerResponse.Stub.getDefaultImpl().onError(param1Int, param1String);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void onResult(Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.accounts.IAccountManagerResponse");
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IAccountManagerResponse.Stub.getDefaultImpl() != null) {
          IAccountManagerResponse.Stub.getDefaultImpl().onResult(param1Bundle);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\accounts\IAccountManagerResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */