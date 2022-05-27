package com.lody.virtual.server.fs;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import com.lody.virtual.remote.FileInfo;

public interface IFileTransfer extends IInterface {
  FileInfo[] listFiles(String paramString) throws RemoteException;
  
  ParcelFileDescriptor openFile(String paramString) throws RemoteException;
  
  public static class Default implements IFileTransfer {
    public IBinder asBinder() {
      return null;
    }
    
    public FileInfo[] listFiles(String param1String) throws RemoteException {
      return null;
    }
    
    public ParcelFileDescriptor openFile(String param1String) throws RemoteException {
      return null;
    }
  }
  
  public static abstract class Stub extends Binder implements IFileTransfer {
    private static final String DESCRIPTOR = "com.lody.virtual.server.fs.IFileTransfer";
    
    static final int TRANSACTION_listFiles = 1;
    
    static final int TRANSACTION_openFile = 2;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.fs.IFileTransfer");
    }
    
    public static IFileTransfer asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.fs.IFileTransfer");
      return (iInterface != null && iInterface instanceof IFileTransfer) ? (IFileTransfer)iInterface : new Proxy(param1IBinder);
    }
    
    public static IFileTransfer getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IFileTransfer param1IFileTransfer) {
      if (Proxy.sDefaultImpl == null && param1IFileTransfer != null) {
        Proxy.sDefaultImpl = param1IFileTransfer;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      ParcelFileDescriptor parcelFileDescriptor;
      if (param1Int1 != 1) {
        if (param1Int1 != 2) {
          if (param1Int1 != 1598968902)
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
          param1Parcel2.writeString("com.lody.virtual.server.fs.IFileTransfer");
          return true;
        } 
        param1Parcel1.enforceInterface("com.lody.virtual.server.fs.IFileTransfer");
        parcelFileDescriptor = openFile(param1Parcel1.readString());
        param1Parcel2.writeNoException();
        if (parcelFileDescriptor != null) {
          param1Parcel2.writeInt(1);
          parcelFileDescriptor.writeToParcel(param1Parcel2, 1);
        } else {
          param1Parcel2.writeInt(0);
        } 
        return true;
      } 
      parcelFileDescriptor.enforceInterface("com.lody.virtual.server.fs.IFileTransfer");
      FileInfo[] arrayOfFileInfo = listFiles(parcelFileDescriptor.readString());
      param1Parcel2.writeNoException();
      param1Parcel2.writeTypedArray((Parcelable[])arrayOfFileInfo, 1);
      return true;
    }
    
    private static class Proxy implements IFileTransfer {
      public static IFileTransfer sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.fs.IFileTransfer";
      }
      
      public FileInfo[] listFiles(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.fs.IFileTransfer");
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IFileTransfer.Stub.getDefaultImpl() != null)
            return IFileTransfer.Stub.getDefaultImpl().listFiles(param2String); 
          parcel2.readException();
          return (FileInfo[])parcel2.createTypedArray(FileInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public ParcelFileDescriptor openFile(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.fs.IFileTransfer");
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IFileTransfer.Stub.getDefaultImpl() != null)
            return IFileTransfer.Stub.getDefaultImpl().openFile(param2String); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor)ParcelFileDescriptor.CREATOR.createFromParcel(parcel2);
          } else {
            param2String = null;
          } 
          return (ParcelFileDescriptor)param2String;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
    }
  }
  
  private static class Proxy implements IFileTransfer {
    public static IFileTransfer sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.fs.IFileTransfer";
    }
    
    public FileInfo[] listFiles(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.fs.IFileTransfer");
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IFileTransfer.Stub.getDefaultImpl() != null)
          return IFileTransfer.Stub.getDefaultImpl().listFiles(param1String); 
        parcel2.readException();
        return (FileInfo[])parcel2.createTypedArray(FileInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public ParcelFileDescriptor openFile(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.fs.IFileTransfer");
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IFileTransfer.Stub.getDefaultImpl() != null)
          return IFileTransfer.Stub.getDefaultImpl().openFile(param1String); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor)ParcelFileDescriptor.CREATOR.createFromParcel(parcel2);
        } else {
          param1String = null;
        } 
        return (ParcelFileDescriptor)param1String;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\fs\IFileTransfer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */