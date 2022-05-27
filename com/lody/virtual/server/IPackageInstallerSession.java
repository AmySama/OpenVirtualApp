package com.lody.virtual.server;

import android.content.IntentSender;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

public interface IPackageInstallerSession extends IInterface {
  void abandon() throws RemoteException;
  
  void addClientProgress(float paramFloat) throws RemoteException;
  
  void close() throws RemoteException;
  
  void commit(IntentSender paramIntentSender) throws RemoteException;
  
  String[] getNames() throws RemoteException;
  
  ParcelFileDescriptor openRead(String paramString) throws RemoteException;
  
  ParcelFileDescriptor openWrite(String paramString, long paramLong1, long paramLong2) throws RemoteException;
  
  void setClientProgress(float paramFloat) throws RemoteException;
  
  public static class Default implements IPackageInstallerSession {
    public void abandon() throws RemoteException {}
    
    public void addClientProgress(float param1Float) throws RemoteException {}
    
    public IBinder asBinder() {
      return null;
    }
    
    public void close() throws RemoteException {}
    
    public void commit(IntentSender param1IntentSender) throws RemoteException {}
    
    public String[] getNames() throws RemoteException {
      return null;
    }
    
    public ParcelFileDescriptor openRead(String param1String) throws RemoteException {
      return null;
    }
    
    public ParcelFileDescriptor openWrite(String param1String, long param1Long1, long param1Long2) throws RemoteException {
      return null;
    }
    
    public void setClientProgress(float param1Float) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IPackageInstallerSession {
    private static final String DESCRIPTOR = "com.lody.virtual.server.IPackageInstallerSession";
    
    static final int TRANSACTION_abandon = 8;
    
    static final int TRANSACTION_addClientProgress = 2;
    
    static final int TRANSACTION_close = 6;
    
    static final int TRANSACTION_commit = 7;
    
    static final int TRANSACTION_getNames = 3;
    
    static final int TRANSACTION_openRead = 5;
    
    static final int TRANSACTION_openWrite = 4;
    
    static final int TRANSACTION_setClientProgress = 1;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.IPackageInstallerSession");
    }
    
    public static IPackageInstallerSession asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.IPackageInstallerSession");
      return (iInterface != null && iInterface instanceof IPackageInstallerSession) ? (IPackageInstallerSession)iInterface : new Proxy(param1IBinder);
    }
    
    public static IPackageInstallerSession getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IPackageInstallerSession param1IPackageInstallerSession) {
      if (Proxy.sDefaultImpl == null && param1IPackageInstallerSession != null) {
        Proxy.sDefaultImpl = param1IPackageInstallerSession;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1598968902) {
        ParcelFileDescriptor parcelFileDescriptor;
        String[] arrayOfString;
        switch (param1Int1) {
          default:
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2);
          case 8:
            param1Parcel1.enforceInterface("com.lody.virtual.server.IPackageInstallerSession");
            abandon();
            param1Parcel2.writeNoException();
            return true;
          case 7:
            param1Parcel1.enforceInterface("com.lody.virtual.server.IPackageInstallerSession");
            if (param1Parcel1.readInt() != 0) {
              IntentSender intentSender = (IntentSender)IntentSender.CREATOR.createFromParcel(param1Parcel1);
            } else {
              param1Parcel1 = null;
            } 
            commit((IntentSender)param1Parcel1);
            param1Parcel2.writeNoException();
            return true;
          case 6:
            param1Parcel1.enforceInterface("com.lody.virtual.server.IPackageInstallerSession");
            close();
            param1Parcel2.writeNoException();
            return true;
          case 5:
            param1Parcel1.enforceInterface("com.lody.virtual.server.IPackageInstallerSession");
            parcelFileDescriptor = openRead(param1Parcel1.readString());
            param1Parcel2.writeNoException();
            if (parcelFileDescriptor != null) {
              param1Parcel2.writeInt(1);
              parcelFileDescriptor.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 4:
            parcelFileDescriptor.enforceInterface("com.lody.virtual.server.IPackageInstallerSession");
            parcelFileDescriptor = openWrite(parcelFileDescriptor.readString(), parcelFileDescriptor.readLong(), parcelFileDescriptor.readLong());
            param1Parcel2.writeNoException();
            if (parcelFileDescriptor != null) {
              param1Parcel2.writeInt(1);
              parcelFileDescriptor.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 3:
            parcelFileDescriptor.enforceInterface("com.lody.virtual.server.IPackageInstallerSession");
            arrayOfString = getNames();
            param1Parcel2.writeNoException();
            param1Parcel2.writeStringArray(arrayOfString);
            return true;
          case 2:
            arrayOfString.enforceInterface("com.lody.virtual.server.IPackageInstallerSession");
            addClientProgress(arrayOfString.readFloat());
            param1Parcel2.writeNoException();
            return true;
          case 1:
            break;
        } 
        arrayOfString.enforceInterface("com.lody.virtual.server.IPackageInstallerSession");
        setClientProgress(arrayOfString.readFloat());
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel2.writeString("com.lody.virtual.server.IPackageInstallerSession");
      return true;
    }
    
    private static class Proxy implements IPackageInstallerSession {
      public static IPackageInstallerSession sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public void abandon() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstallerSession");
          if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IPackageInstallerSession.Stub.getDefaultImpl() != null) {
            IPackageInstallerSession.Stub.getDefaultImpl().abandon();
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void addClientProgress(float param2Float) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstallerSession");
          parcel1.writeFloat(param2Float);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IPackageInstallerSession.Stub.getDefaultImpl() != null) {
            IPackageInstallerSession.Stub.getDefaultImpl().addClientProgress(param2Float);
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
      
      public void close() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstallerSession");
          if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IPackageInstallerSession.Stub.getDefaultImpl() != null) {
            IPackageInstallerSession.Stub.getDefaultImpl().close();
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void commit(IntentSender param2IntentSender) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstallerSession");
          if (param2IntentSender != null) {
            parcel1.writeInt(1);
            param2IntentSender.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IPackageInstallerSession.Stub.getDefaultImpl() != null) {
            IPackageInstallerSession.Stub.getDefaultImpl().commit(param2IntentSender);
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
        return "com.lody.virtual.server.IPackageInstallerSession";
      }
      
      public String[] getNames() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstallerSession");
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IPackageInstallerSession.Stub.getDefaultImpl() != null)
            return IPackageInstallerSession.Stub.getDefaultImpl().getNames(); 
          parcel2.readException();
          return parcel2.createStringArray();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public ParcelFileDescriptor openRead(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstallerSession");
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IPackageInstallerSession.Stub.getDefaultImpl() != null)
            return IPackageInstallerSession.Stub.getDefaultImpl().openRead(param2String); 
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
      
      public ParcelFileDescriptor openWrite(String param2String, long param2Long1, long param2Long2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstallerSession");
          parcel1.writeString(param2String);
          parcel1.writeLong(param2Long1);
          parcel1.writeLong(param2Long2);
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IPackageInstallerSession.Stub.getDefaultImpl() != null)
            return IPackageInstallerSession.Stub.getDefaultImpl().openWrite(param2String, param2Long1, param2Long2); 
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
      
      public void setClientProgress(float param2Float) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstallerSession");
          parcel1.writeFloat(param2Float);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IPackageInstallerSession.Stub.getDefaultImpl() != null) {
            IPackageInstallerSession.Stub.getDefaultImpl().setClientProgress(param2Float);
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
  
  private static class Proxy implements IPackageInstallerSession {
    public static IPackageInstallerSession sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public void abandon() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstallerSession");
        if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IPackageInstallerSession.Stub.getDefaultImpl() != null) {
          IPackageInstallerSession.Stub.getDefaultImpl().abandon();
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void addClientProgress(float param1Float) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstallerSession");
        parcel1.writeFloat(param1Float);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IPackageInstallerSession.Stub.getDefaultImpl() != null) {
          IPackageInstallerSession.Stub.getDefaultImpl().addClientProgress(param1Float);
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
    
    public void close() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstallerSession");
        if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IPackageInstallerSession.Stub.getDefaultImpl() != null) {
          IPackageInstallerSession.Stub.getDefaultImpl().close();
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void commit(IntentSender param1IntentSender) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstallerSession");
        if (param1IntentSender != null) {
          parcel1.writeInt(1);
          param1IntentSender.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IPackageInstallerSession.Stub.getDefaultImpl() != null) {
          IPackageInstallerSession.Stub.getDefaultImpl().commit(param1IntentSender);
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
      return "com.lody.virtual.server.IPackageInstallerSession";
    }
    
    public String[] getNames() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstallerSession");
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IPackageInstallerSession.Stub.getDefaultImpl() != null)
          return IPackageInstallerSession.Stub.getDefaultImpl().getNames(); 
        parcel2.readException();
        return parcel2.createStringArray();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public ParcelFileDescriptor openRead(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstallerSession");
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IPackageInstallerSession.Stub.getDefaultImpl() != null)
          return IPackageInstallerSession.Stub.getDefaultImpl().openRead(param1String); 
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
    
    public ParcelFileDescriptor openWrite(String param1String, long param1Long1, long param1Long2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstallerSession");
        parcel1.writeString(param1String);
        parcel1.writeLong(param1Long1);
        parcel1.writeLong(param1Long2);
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IPackageInstallerSession.Stub.getDefaultImpl() != null)
          return IPackageInstallerSession.Stub.getDefaultImpl().openWrite(param1String, param1Long1, param1Long2); 
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
    
    public void setClientProgress(float param1Float) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstallerSession");
        parcel1.writeFloat(param1Float);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IPackageInstallerSession.Stub.getDefaultImpl() != null) {
          IPackageInstallerSession.Stub.getDefaultImpl().setClientProgress(param1Float);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\IPackageInstallerSession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */