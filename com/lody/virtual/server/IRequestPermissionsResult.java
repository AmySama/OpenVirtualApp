package com.lody.virtual.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRequestPermissionsResult extends IInterface {
  boolean onResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) throws RemoteException;
  
  public static class Default implements IRequestPermissionsResult {
    public IBinder asBinder() {
      return null;
    }
    
    public boolean onResult(int param1Int, String[] param1ArrayOfString, int[] param1ArrayOfint) throws RemoteException {
      return false;
    }
  }
  
  public static abstract class Stub extends Binder implements IRequestPermissionsResult {
    private static final String DESCRIPTOR = "com.lody.virtual.server.IRequestPermissionsResult";
    
    static final int TRANSACTION_onResult = 1;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.IRequestPermissionsResult");
    }
    
    public static IRequestPermissionsResult asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.IRequestPermissionsResult");
      return (iInterface != null && iInterface instanceof IRequestPermissionsResult) ? (IRequestPermissionsResult)iInterface : new Proxy(param1IBinder);
    }
    
    public static IRequestPermissionsResult getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IRequestPermissionsResult param1IRequestPermissionsResult) {
      if (Proxy.sDefaultImpl == null && param1IRequestPermissionsResult != null) {
        Proxy.sDefaultImpl = param1IRequestPermissionsResult;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1) {
        if (param1Int1 != 1598968902)
          return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
        param1Parcel2.writeString("com.lody.virtual.server.IRequestPermissionsResult");
        return true;
      } 
      param1Parcel1.enforceInterface("com.lody.virtual.server.IRequestPermissionsResult");
      boolean bool = onResult(param1Parcel1.readInt(), param1Parcel1.createStringArray(), param1Parcel1.createIntArray());
      param1Parcel2.writeNoException();
      param1Parcel2.writeInt(bool);
      return true;
    }
    
    private static class Proxy implements IRequestPermissionsResult {
      public static IRequestPermissionsResult sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.IRequestPermissionsResult";
      }
      
      public boolean onResult(int param2Int, String[] param2ArrayOfString, int[] param2ArrayOfint) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.IRequestPermissionsResult");
          parcel1.writeInt(param2Int);
          parcel1.writeStringArray(param2ArrayOfString);
          parcel1.writeIntArray(param2ArrayOfint);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(1, parcel1, parcel2, 0) && IRequestPermissionsResult.Stub.getDefaultImpl() != null) {
            bool = IRequestPermissionsResult.Stub.getDefaultImpl().onResult(param2Int, param2ArrayOfString, param2ArrayOfint);
            return bool;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          if (param2Int != 0)
            bool = true; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
    }
  }
  
  private static class Proxy implements IRequestPermissionsResult {
    public static IRequestPermissionsResult sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.IRequestPermissionsResult";
    }
    
    public boolean onResult(int param1Int, String[] param1ArrayOfString, int[] param1ArrayOfint) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.IRequestPermissionsResult");
        parcel1.writeInt(param1Int);
        parcel1.writeStringArray(param1ArrayOfString);
        parcel1.writeIntArray(param1ArrayOfint);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(1, parcel1, parcel2, 0) && IRequestPermissionsResult.Stub.getDefaultImpl() != null) {
          bool = IRequestPermissionsResult.Stub.getDefaultImpl().onResult(param1Int, param1ArrayOfString, param1ArrayOfint);
          return bool;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        if (param1Int != 0)
          bool = true; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\IRequestPermissionsResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */