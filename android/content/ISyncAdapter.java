package android.content;

import android.accounts.Account;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISyncAdapter extends IInterface {
  void cancelSync(ISyncContext paramISyncContext) throws RemoteException;
  
  void initialize(Account paramAccount, String paramString) throws RemoteException;
  
  void startSync(ISyncContext paramISyncContext, String paramString, Account paramAccount, Bundle paramBundle) throws RemoteException;
  
  public static class Default implements ISyncAdapter {
    public IBinder asBinder() {
      return null;
    }
    
    public void cancelSync(ISyncContext param1ISyncContext) throws RemoteException {}
    
    public void initialize(Account param1Account, String param1String) throws RemoteException {}
    
    public void startSync(ISyncContext param1ISyncContext, String param1String, Account param1Account, Bundle param1Bundle) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements ISyncAdapter {
    private static final String DESCRIPTOR = "android.content.ISyncAdapter";
    
    static final int TRANSACTION_cancelSync = 2;
    
    static final int TRANSACTION_initialize = 3;
    
    static final int TRANSACTION_startSync = 1;
    
    public Stub() {
      attachInterface(this, "android.content.ISyncAdapter");
    }
    
    public static ISyncAdapter asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.content.ISyncAdapter");
      return (iInterface != null && iInterface instanceof ISyncAdapter) ? (ISyncAdapter)iInterface : new Proxy(param1IBinder);
    }
    
    public static ISyncAdapter getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(ISyncAdapter param1ISyncAdapter) {
      if (Proxy.sDefaultImpl == null && param1ISyncAdapter != null) {
        Proxy.sDefaultImpl = param1ISyncAdapter;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      Bundle bundle = null;
      Account account = null;
      if (param1Int1 != 1) {
        if (param1Int1 != 2) {
          if (param1Int1 != 3) {
            if (param1Int1 != 1598968902)
              return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
            param1Parcel2.writeString("android.content.ISyncAdapter");
            return true;
          } 
          param1Parcel1.enforceInterface("android.content.ISyncAdapter");
          if (param1Parcel1.readInt() != 0)
            account = (Account)Account.CREATOR.createFromParcel(param1Parcel1); 
          initialize(account, param1Parcel1.readString());
          param1Parcel2.writeNoException();
          return true;
        } 
        param1Parcel1.enforceInterface("android.content.ISyncAdapter");
        cancelSync(ISyncContext.Stub.asInterface(param1Parcel1.readStrongBinder()));
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel1.enforceInterface("android.content.ISyncAdapter");
      ISyncContext iSyncContext = ISyncContext.Stub.asInterface(param1Parcel1.readStrongBinder());
      String str = param1Parcel1.readString();
      if (param1Parcel1.readInt() != 0) {
        account = (Account)Account.CREATOR.createFromParcel(param1Parcel1);
      } else {
        account = null;
      } 
      if (param1Parcel1.readInt() != 0)
        bundle = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1); 
      startSync(iSyncContext, str, account, bundle);
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements ISyncAdapter {
      public static ISyncAdapter sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public void cancelSync(ISyncContext param2ISyncContext) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("android.content.ISyncAdapter");
          if (param2ISyncContext != null) {
            iBinder = param2ISyncContext.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && ISyncAdapter.Stub.getDefaultImpl() != null) {
            ISyncAdapter.Stub.getDefaultImpl().cancelSync(param2ISyncContext);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "android.content.ISyncAdapter";
      }
      
      public void initialize(Account param2Account, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.content.ISyncAdapter");
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && ISyncAdapter.Stub.getDefaultImpl() != null) {
            ISyncAdapter.Stub.getDefaultImpl().initialize(param2Account, param2String);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void startSync(ISyncContext param2ISyncContext, String param2String, Account param2Account, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("android.content.ISyncAdapter");
          if (param2ISyncContext != null) {
            iBinder = param2ISyncContext.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          parcel1.writeString(param2String);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && ISyncAdapter.Stub.getDefaultImpl() != null) {
            ISyncAdapter.Stub.getDefaultImpl().startSync(param2ISyncContext, param2String, param2Account, param2Bundle);
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
  
  private static class Proxy implements ISyncAdapter {
    public static ISyncAdapter sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public void cancelSync(ISyncContext param1ISyncContext) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("android.content.ISyncAdapter");
        if (param1ISyncContext != null) {
          iBinder = param1ISyncContext.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && ISyncAdapter.Stub.getDefaultImpl() != null) {
          ISyncAdapter.Stub.getDefaultImpl().cancelSync(param1ISyncContext);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "android.content.ISyncAdapter";
    }
    
    public void initialize(Account param1Account, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.content.ISyncAdapter");
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && ISyncAdapter.Stub.getDefaultImpl() != null) {
          ISyncAdapter.Stub.getDefaultImpl().initialize(param1Account, param1String);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void startSync(ISyncContext param1ISyncContext, String param1String, Account param1Account, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("android.content.ISyncAdapter");
        if (param1ISyncContext != null) {
          iBinder = param1ISyncContext.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        parcel1.writeString(param1String);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && ISyncAdapter.Stub.getDefaultImpl() != null) {
          ISyncAdapter.Stub.getDefaultImpl().startSync(param1ISyncContext, param1String, param1Account, param1Bundle);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\content\ISyncAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */