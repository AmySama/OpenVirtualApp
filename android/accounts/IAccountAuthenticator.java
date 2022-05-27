package android.accounts;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAccountAuthenticator extends IInterface {
  void addAccount(IAccountAuthenticatorResponse paramIAccountAuthenticatorResponse, String paramString1, String paramString2, String[] paramArrayOfString, Bundle paramBundle) throws RemoteException;
  
  void addAccountFromCredentials(IAccountAuthenticatorResponse paramIAccountAuthenticatorResponse, Account paramAccount, Bundle paramBundle) throws RemoteException;
  
  void confirmCredentials(IAccountAuthenticatorResponse paramIAccountAuthenticatorResponse, Account paramAccount, Bundle paramBundle) throws RemoteException;
  
  void editProperties(IAccountAuthenticatorResponse paramIAccountAuthenticatorResponse, String paramString) throws RemoteException;
  
  void getAccountCredentialsForCloning(IAccountAuthenticatorResponse paramIAccountAuthenticatorResponse, Account paramAccount) throws RemoteException;
  
  void getAccountRemovalAllowed(IAccountAuthenticatorResponse paramIAccountAuthenticatorResponse, Account paramAccount) throws RemoteException;
  
  void getAuthToken(IAccountAuthenticatorResponse paramIAccountAuthenticatorResponse, Account paramAccount, String paramString, Bundle paramBundle) throws RemoteException;
  
  void getAuthTokenLabel(IAccountAuthenticatorResponse paramIAccountAuthenticatorResponse, String paramString) throws RemoteException;
  
  void hasFeatures(IAccountAuthenticatorResponse paramIAccountAuthenticatorResponse, Account paramAccount, String[] paramArrayOfString) throws RemoteException;
  
  void updateCredentials(IAccountAuthenticatorResponse paramIAccountAuthenticatorResponse, Account paramAccount, String paramString, Bundle paramBundle) throws RemoteException;
  
  public static class Default implements IAccountAuthenticator {
    public void addAccount(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, String param1String1, String param1String2, String[] param1ArrayOfString, Bundle param1Bundle) throws RemoteException {}
    
    public void addAccountFromCredentials(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, Account param1Account, Bundle param1Bundle) throws RemoteException {}
    
    public IBinder asBinder() {
      return null;
    }
    
    public void confirmCredentials(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, Account param1Account, Bundle param1Bundle) throws RemoteException {}
    
    public void editProperties(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, String param1String) throws RemoteException {}
    
    public void getAccountCredentialsForCloning(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, Account param1Account) throws RemoteException {}
    
    public void getAccountRemovalAllowed(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, Account param1Account) throws RemoteException {}
    
    public void getAuthToken(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, Account param1Account, String param1String, Bundle param1Bundle) throws RemoteException {}
    
    public void getAuthTokenLabel(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, String param1String) throws RemoteException {}
    
    public void hasFeatures(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, Account param1Account, String[] param1ArrayOfString) throws RemoteException {}
    
    public void updateCredentials(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, Account param1Account, String param1String, Bundle param1Bundle) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IAccountAuthenticator {
    private static final String DESCRIPTOR = "android.accounts.IAccountAuthenticator";
    
    static final int TRANSACTION_addAccount = 1;
    
    static final int TRANSACTION_addAccountFromCredentials = 10;
    
    static final int TRANSACTION_confirmCredentials = 2;
    
    static final int TRANSACTION_editProperties = 6;
    
    static final int TRANSACTION_getAccountCredentialsForCloning = 9;
    
    static final int TRANSACTION_getAccountRemovalAllowed = 8;
    
    static final int TRANSACTION_getAuthToken = 3;
    
    static final int TRANSACTION_getAuthTokenLabel = 4;
    
    static final int TRANSACTION_hasFeatures = 7;
    
    static final int TRANSACTION_updateCredentials = 5;
    
    public Stub() {
      attachInterface(this, "android.accounts.IAccountAuthenticator");
    }
    
    public static IAccountAuthenticator asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.accounts.IAccountAuthenticator");
      return (iInterface != null && iInterface instanceof IAccountAuthenticator) ? (IAccountAuthenticator)iInterface : new Proxy(param1IBinder);
    }
    
    public static IAccountAuthenticator getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IAccountAuthenticator param1IAccountAuthenticator) {
      if (Proxy.sDefaultImpl == null && param1IAccountAuthenticator != null) {
        Proxy.sDefaultImpl = param1IAccountAuthenticator;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1598968902) {
        IAccountAuthenticatorResponse iAccountAuthenticatorResponse2;
        Bundle bundle3;
        IAccountAuthenticatorResponse iAccountAuthenticatorResponse1;
        Bundle bundle2;
        String str1;
        Bundle bundle1;
        Bundle bundle5;
        String str4;
        Account account1 = null;
        Account account2 = null;
        Account account3 = null;
        Bundle bundle4 = null;
        IAccountAuthenticatorResponse iAccountAuthenticatorResponse3 = null;
        String str3 = null;
        Account account4 = null;
        Bundle bundle6 = null;
        switch (param1Int1) {
          default:
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2);
          case 10:
            param1Parcel1.enforceInterface("android.accounts.IAccountAuthenticator");
            iAccountAuthenticatorResponse3 = IAccountAuthenticatorResponse.Stub.asInterface(param1Parcel1.readStrongBinder());
            if (param1Parcel1.readInt() != 0) {
              account4 = (Account)Account.CREATOR.createFromParcel(param1Parcel1);
            } else {
              account4 = null;
            } 
            bundle4 = bundle6;
            if (param1Parcel1.readInt() != 0)
              bundle4 = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1); 
            addAccountFromCredentials(iAccountAuthenticatorResponse3, account4, bundle4);
            param1Parcel2.writeNoException();
            return true;
          case 9:
            param1Parcel1.enforceInterface("android.accounts.IAccountAuthenticator");
            iAccountAuthenticatorResponse2 = IAccountAuthenticatorResponse.Stub.asInterface(param1Parcel1.readStrongBinder());
            account4 = account1;
            if (param1Parcel1.readInt() != 0)
              account4 = (Account)Account.CREATOR.createFromParcel(param1Parcel1); 
            getAccountCredentialsForCloning(iAccountAuthenticatorResponse2, account4);
            param1Parcel2.writeNoException();
            return true;
          case 8:
            param1Parcel1.enforceInterface("android.accounts.IAccountAuthenticator");
            iAccountAuthenticatorResponse2 = IAccountAuthenticatorResponse.Stub.asInterface(param1Parcel1.readStrongBinder());
            account4 = account2;
            if (param1Parcel1.readInt() != 0)
              account4 = (Account)Account.CREATOR.createFromParcel(param1Parcel1); 
            getAccountRemovalAllowed(iAccountAuthenticatorResponse2, account4);
            param1Parcel2.writeNoException();
            return true;
          case 7:
            param1Parcel1.enforceInterface("android.accounts.IAccountAuthenticator");
            iAccountAuthenticatorResponse2 = IAccountAuthenticatorResponse.Stub.asInterface(param1Parcel1.readStrongBinder());
            account4 = account3;
            if (param1Parcel1.readInt() != 0)
              account4 = (Account)Account.CREATOR.createFromParcel(param1Parcel1); 
            hasFeatures(iAccountAuthenticatorResponse2, account4, param1Parcel1.createStringArray());
            param1Parcel2.writeNoException();
            return true;
          case 6:
            param1Parcel1.enforceInterface("android.accounts.IAccountAuthenticator");
            editProperties(IAccountAuthenticatorResponse.Stub.asInterface(param1Parcel1.readStrongBinder()), param1Parcel1.readString());
            param1Parcel2.writeNoException();
            return true;
          case 5:
            param1Parcel1.enforceInterface("android.accounts.IAccountAuthenticator");
            iAccountAuthenticatorResponse3 = IAccountAuthenticatorResponse.Stub.asInterface(param1Parcel1.readStrongBinder());
            if (param1Parcel1.readInt() != 0) {
              account4 = (Account)Account.CREATOR.createFromParcel(param1Parcel1);
            } else {
              account4 = null;
            } 
            str4 = param1Parcel1.readString();
            if (param1Parcel1.readInt() != 0)
              bundle3 = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1); 
            updateCredentials(iAccountAuthenticatorResponse3, account4, str4, bundle3);
            param1Parcel2.writeNoException();
            return true;
          case 4:
            param1Parcel1.enforceInterface("android.accounts.IAccountAuthenticator");
            getAuthTokenLabel(IAccountAuthenticatorResponse.Stub.asInterface(param1Parcel1.readStrongBinder()), param1Parcel1.readString());
            param1Parcel2.writeNoException();
            return true;
          case 3:
            param1Parcel1.enforceInterface("android.accounts.IAccountAuthenticator");
            iAccountAuthenticatorResponse4 = IAccountAuthenticatorResponse.Stub.asInterface(param1Parcel1.readStrongBinder());
            if (param1Parcel1.readInt() != 0) {
              account4 = (Account)Account.CREATOR.createFromParcel(param1Parcel1);
            } else {
              account4 = null;
            } 
            str3 = param1Parcel1.readString();
            iAccountAuthenticatorResponse1 = iAccountAuthenticatorResponse3;
            if (param1Parcel1.readInt() != 0)
              bundle2 = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1); 
            getAuthToken(iAccountAuthenticatorResponse4, account4, str3, bundle2);
            param1Parcel2.writeNoException();
            return true;
          case 2:
            param1Parcel1.enforceInterface("android.accounts.IAccountAuthenticator");
            iAccountAuthenticatorResponse3 = IAccountAuthenticatorResponse.Stub.asInterface(param1Parcel1.readStrongBinder());
            if (param1Parcel1.readInt() != 0) {
              account4 = (Account)Account.CREATOR.createFromParcel(param1Parcel1);
            } else {
              account4 = null;
            } 
            str1 = str3;
            if (param1Parcel1.readInt() != 0)
              bundle1 = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1); 
            confirmCredentials(iAccountAuthenticatorResponse3, account4, bundle1);
            param1Parcel2.writeNoException();
            return true;
          case 1:
            break;
        } 
        param1Parcel1.enforceInterface("android.accounts.IAccountAuthenticator");
        IAccountAuthenticatorResponse iAccountAuthenticatorResponse4 = IAccountAuthenticatorResponse.Stub.asInterface(param1Parcel1.readStrongBinder());
        String str2 = param1Parcel1.readString();
        str3 = param1Parcel1.readString();
        String[] arrayOfString = param1Parcel1.createStringArray();
        if (param1Parcel1.readInt() != 0)
          bundle5 = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1); 
        addAccount(iAccountAuthenticatorResponse4, str2, str3, arrayOfString, bundle5);
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel2.writeString("android.accounts.IAccountAuthenticator");
      return true;
    }
    
    private static class Proxy implements IAccountAuthenticator {
      public static IAccountAuthenticator sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public void addAccount(IAccountAuthenticatorResponse param2IAccountAuthenticatorResponse, String param2String1, String param2String2, String[] param2ArrayOfString, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
          if (param2IAccountAuthenticatorResponse != null) {
            iBinder = param2IAccountAuthenticatorResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          parcel1.writeStringArray(param2ArrayOfString);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
            IAccountAuthenticator.Stub.getDefaultImpl().addAccount(param2IAccountAuthenticatorResponse, param2String1, param2String2, param2ArrayOfString, param2Bundle);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void addAccountFromCredentials(IAccountAuthenticatorResponse param2IAccountAuthenticatorResponse, Account param2Account, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
          if (param2IAccountAuthenticatorResponse != null) {
            iBinder = param2IAccountAuthenticatorResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
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
          if (!this.mRemote.transact(10, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
            IAccountAuthenticator.Stub.getDefaultImpl().addAccountFromCredentials(param2IAccountAuthenticatorResponse, param2Account, param2Bundle);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public void confirmCredentials(IAccountAuthenticatorResponse param2IAccountAuthenticatorResponse, Account param2Account, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
          if (param2IAccountAuthenticatorResponse != null) {
            iBinder = param2IAccountAuthenticatorResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
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
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
            IAccountAuthenticator.Stub.getDefaultImpl().confirmCredentials(param2IAccountAuthenticatorResponse, param2Account, param2Bundle);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void editProperties(IAccountAuthenticatorResponse param2IAccountAuthenticatorResponse, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
          if (param2IAccountAuthenticatorResponse != null) {
            iBinder = param2IAccountAuthenticatorResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
            IAccountAuthenticator.Stub.getDefaultImpl().editProperties(param2IAccountAuthenticatorResponse, param2String);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void getAccountCredentialsForCloning(IAccountAuthenticatorResponse param2IAccountAuthenticatorResponse, Account param2Account) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
          if (param2IAccountAuthenticatorResponse != null) {
            iBinder = param2IAccountAuthenticatorResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(9, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
            IAccountAuthenticator.Stub.getDefaultImpl().getAccountCredentialsForCloning(param2IAccountAuthenticatorResponse, param2Account);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void getAccountRemovalAllowed(IAccountAuthenticatorResponse param2IAccountAuthenticatorResponse, Account param2Account) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
          if (param2IAccountAuthenticatorResponse != null) {
            iBinder = param2IAccountAuthenticatorResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
            IAccountAuthenticator.Stub.getDefaultImpl().getAccountRemovalAllowed(param2IAccountAuthenticatorResponse, param2Account);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void getAuthToken(IAccountAuthenticatorResponse param2IAccountAuthenticatorResponse, Account param2Account, String param2String, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
          if (param2IAccountAuthenticatorResponse != null) {
            iBinder = param2IAccountAuthenticatorResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
            IAccountAuthenticator.Stub.getDefaultImpl().getAuthToken(param2IAccountAuthenticatorResponse, param2Account, param2String, param2Bundle);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void getAuthTokenLabel(IAccountAuthenticatorResponse param2IAccountAuthenticatorResponse, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
          if (param2IAccountAuthenticatorResponse != null) {
            iBinder = param2IAccountAuthenticatorResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
            IAccountAuthenticator.Stub.getDefaultImpl().getAuthTokenLabel(param2IAccountAuthenticatorResponse, param2String);
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
        return "android.accounts.IAccountAuthenticator";
      }
      
      public void hasFeatures(IAccountAuthenticatorResponse param2IAccountAuthenticatorResponse, Account param2Account, String[] param2ArrayOfString) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
          if (param2IAccountAuthenticatorResponse != null) {
            iBinder = param2IAccountAuthenticatorResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeStringArray(param2ArrayOfString);
          if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
            IAccountAuthenticator.Stub.getDefaultImpl().hasFeatures(param2IAccountAuthenticatorResponse, param2Account, param2ArrayOfString);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void updateCredentials(IAccountAuthenticatorResponse param2IAccountAuthenticatorResponse, Account param2Account, String param2String, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
          if (param2IAccountAuthenticatorResponse != null) {
            iBinder = param2IAccountAuthenticatorResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
            IAccountAuthenticator.Stub.getDefaultImpl().updateCredentials(param2IAccountAuthenticatorResponse, param2Account, param2String, param2Bundle);
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
  
  private static class Proxy implements IAccountAuthenticator {
    public static IAccountAuthenticator sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public void addAccount(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, String param1String1, String param1String2, String[] param1ArrayOfString, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
        if (param1IAccountAuthenticatorResponse != null) {
          iBinder = param1IAccountAuthenticatorResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        parcel1.writeStringArray(param1ArrayOfString);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
          IAccountAuthenticator.Stub.getDefaultImpl().addAccount(param1IAccountAuthenticatorResponse, param1String1, param1String2, param1ArrayOfString, param1Bundle);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void addAccountFromCredentials(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, Account param1Account, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
        if (param1IAccountAuthenticatorResponse != null) {
          iBinder = param1IAccountAuthenticatorResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
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
        if (!this.mRemote.transact(10, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
          IAccountAuthenticator.Stub.getDefaultImpl().addAccountFromCredentials(param1IAccountAuthenticatorResponse, param1Account, param1Bundle);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public void confirmCredentials(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, Account param1Account, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
        if (param1IAccountAuthenticatorResponse != null) {
          iBinder = param1IAccountAuthenticatorResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
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
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
          IAccountAuthenticator.Stub.getDefaultImpl().confirmCredentials(param1IAccountAuthenticatorResponse, param1Account, param1Bundle);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void editProperties(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
        if (param1IAccountAuthenticatorResponse != null) {
          iBinder = param1IAccountAuthenticatorResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
          IAccountAuthenticator.Stub.getDefaultImpl().editProperties(param1IAccountAuthenticatorResponse, param1String);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void getAccountCredentialsForCloning(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, Account param1Account) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
        if (param1IAccountAuthenticatorResponse != null) {
          iBinder = param1IAccountAuthenticatorResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(9, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
          IAccountAuthenticator.Stub.getDefaultImpl().getAccountCredentialsForCloning(param1IAccountAuthenticatorResponse, param1Account);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void getAccountRemovalAllowed(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, Account param1Account) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
        if (param1IAccountAuthenticatorResponse != null) {
          iBinder = param1IAccountAuthenticatorResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
          IAccountAuthenticator.Stub.getDefaultImpl().getAccountRemovalAllowed(param1IAccountAuthenticatorResponse, param1Account);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void getAuthToken(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, Account param1Account, String param1String, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
        if (param1IAccountAuthenticatorResponse != null) {
          iBinder = param1IAccountAuthenticatorResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
          IAccountAuthenticator.Stub.getDefaultImpl().getAuthToken(param1IAccountAuthenticatorResponse, param1Account, param1String, param1Bundle);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void getAuthTokenLabel(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
        if (param1IAccountAuthenticatorResponse != null) {
          iBinder = param1IAccountAuthenticatorResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
          IAccountAuthenticator.Stub.getDefaultImpl().getAuthTokenLabel(param1IAccountAuthenticatorResponse, param1String);
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
      return "android.accounts.IAccountAuthenticator";
    }
    
    public void hasFeatures(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, Account param1Account, String[] param1ArrayOfString) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
        if (param1IAccountAuthenticatorResponse != null) {
          iBinder = param1IAccountAuthenticatorResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeStringArray(param1ArrayOfString);
        if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
          IAccountAuthenticator.Stub.getDefaultImpl().hasFeatures(param1IAccountAuthenticatorResponse, param1Account, param1ArrayOfString);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void updateCredentials(IAccountAuthenticatorResponse param1IAccountAuthenticatorResponse, Account param1Account, String param1String, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("android.accounts.IAccountAuthenticator");
        if (param1IAccountAuthenticatorResponse != null) {
          iBinder = param1IAccountAuthenticatorResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IAccountAuthenticator.Stub.getDefaultImpl() != null) {
          IAccountAuthenticator.Stub.getDefaultImpl().updateCredentials(param1IAccountAuthenticatorResponse, param1Account, param1String, param1Bundle);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\accounts\IAccountAuthenticator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */