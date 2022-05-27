package com.android.internal.widget;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILockSettings extends IInterface {
  int[] getRecoverySecretTypes() throws RemoteException;
  
  void setRecoverySecretTypes(int[] paramArrayOfint) throws RemoteException;
  
  public static class Default implements ILockSettings {
    public IBinder asBinder() {
      return null;
    }
    
    public int[] getRecoverySecretTypes() throws RemoteException {
      return null;
    }
    
    public void setRecoverySecretTypes(int[] param1ArrayOfint) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements ILockSettings {
    private static final String DESCRIPTOR = "com.android.internal.widget.ILockSettings";
    
    static final int TRANSACTION_getRecoverySecretTypes = 2;
    
    static final int TRANSACTION_setRecoverySecretTypes = 1;
    
    public Stub() {
      attachInterface(this, "com.android.internal.widget.ILockSettings");
    }
    
    public static ILockSettings asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.android.internal.widget.ILockSettings");
      return (iInterface != null && iInterface instanceof ILockSettings) ? (ILockSettings)iInterface : new Proxy(param1IBinder);
    }
    
    public static ILockSettings getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(ILockSettings param1ILockSettings) {
      if (Proxy.sDefaultImpl == null && param1ILockSettings != null) {
        Proxy.sDefaultImpl = param1ILockSettings;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      int[] arrayOfInt;
      if (param1Int1 != 1) {
        if (param1Int1 != 2) {
          if (param1Int1 != 1598968902)
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
          param1Parcel2.writeString("com.android.internal.widget.ILockSettings");
          return true;
        } 
        param1Parcel1.enforceInterface("com.android.internal.widget.ILockSettings");
        arrayOfInt = getRecoverySecretTypes();
        param1Parcel2.writeNoException();
        param1Parcel2.writeIntArray(arrayOfInt);
        return true;
      } 
      arrayOfInt.enforceInterface("com.android.internal.widget.ILockSettings");
      setRecoverySecretTypes(arrayOfInt.createIntArray());
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements ILockSettings {
      public static ILockSettings sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "com.android.internal.widget.ILockSettings";
      }
      
      public int[] getRecoverySecretTypes() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.android.internal.widget.ILockSettings");
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && ILockSettings.Stub.getDefaultImpl() != null)
            return ILockSettings.Stub.getDefaultImpl().getRecoverySecretTypes(); 
          parcel2.readException();
          return parcel2.createIntArray();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setRecoverySecretTypes(int[] param2ArrayOfint) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.android.internal.widget.ILockSettings");
          parcel1.writeIntArray(param2ArrayOfint);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && ILockSettings.Stub.getDefaultImpl() != null) {
            ILockSettings.Stub.getDefaultImpl().setRecoverySecretTypes(param2ArrayOfint);
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
  
  private static class Proxy implements ILockSettings {
    public static ILockSettings sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "com.android.internal.widget.ILockSettings";
    }
    
    public int[] getRecoverySecretTypes() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.android.internal.widget.ILockSettings");
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && ILockSettings.Stub.getDefaultImpl() != null)
          return ILockSettings.Stub.getDefaultImpl().getRecoverySecretTypes(); 
        parcel2.readException();
        return parcel2.createIntArray();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setRecoverySecretTypes(int[] param1ArrayOfint) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.android.internal.widget.ILockSettings");
        parcel1.writeIntArray(param1ArrayOfint);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && ILockSettings.Stub.getDefaultImpl() != null) {
          ILockSettings.Stub.getDefaultImpl().setRecoverySecretTypes(param1ArrayOfint);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\internal\widget\ILockSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */