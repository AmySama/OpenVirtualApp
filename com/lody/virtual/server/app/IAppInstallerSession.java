package com.lody.virtual.server.app;

import android.content.IntentSender;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAppInstallerSession extends IInterface {
  void addPackage(Uri paramUri) throws RemoteException;
  
  void addSplit(Uri paramUri) throws RemoteException;
  
  void cancel() throws RemoteException;
  
  void commit(IntentSender paramIntentSender) throws RemoteException;
  
  public static class Default implements IAppInstallerSession {
    public void addPackage(Uri param1Uri) throws RemoteException {}
    
    public void addSplit(Uri param1Uri) throws RemoteException {}
    
    public IBinder asBinder() {
      return null;
    }
    
    public void cancel() throws RemoteException {}
    
    public void commit(IntentSender param1IntentSender) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IAppInstallerSession {
    private static final String DESCRIPTOR = "com.lody.virtual.server.app.IAppInstallerSession";
    
    static final int TRANSACTION_addPackage = 1;
    
    static final int TRANSACTION_addSplit = 2;
    
    static final int TRANSACTION_cancel = 4;
    
    static final int TRANSACTION_commit = 3;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.app.IAppInstallerSession");
    }
    
    public static IAppInstallerSession asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.app.IAppInstallerSession");
      return (iInterface != null && iInterface instanceof IAppInstallerSession) ? (IAppInstallerSession)iInterface : new Proxy(param1IBinder);
    }
    
    public static IAppInstallerSession getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IAppInstallerSession param1IAppInstallerSession) {
      if (Proxy.sDefaultImpl == null && param1IAppInstallerSession != null) {
        Proxy.sDefaultImpl = param1IAppInstallerSession;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      Uri uri;
      IntentSender intentSender1 = null;
      IntentSender intentSender2 = null;
      IntentSender intentSender3 = null;
      if (param1Int1 != 1) {
        if (param1Int1 != 2) {
          if (param1Int1 != 3) {
            if (param1Int1 != 4) {
              if (param1Int1 != 1598968902)
                return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
              param1Parcel2.writeString("com.lody.virtual.server.app.IAppInstallerSession");
              return true;
            } 
            param1Parcel1.enforceInterface("com.lody.virtual.server.app.IAppInstallerSession");
            cancel();
            param1Parcel2.writeNoException();
            return true;
          } 
          param1Parcel1.enforceInterface("com.lody.virtual.server.app.IAppInstallerSession");
          if (param1Parcel1.readInt() != 0)
            intentSender3 = (IntentSender)IntentSender.CREATOR.createFromParcel(param1Parcel1); 
          commit(intentSender3);
          param1Parcel2.writeNoException();
          return true;
        } 
        param1Parcel1.enforceInterface("com.lody.virtual.server.app.IAppInstallerSession");
        intentSender3 = intentSender1;
        if (param1Parcel1.readInt() != 0)
          uri = (Uri)Uri.CREATOR.createFromParcel(param1Parcel1); 
        addSplit(uri);
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel1.enforceInterface("com.lody.virtual.server.app.IAppInstallerSession");
      intentSender3 = intentSender2;
      if (param1Parcel1.readInt() != 0)
        uri = (Uri)Uri.CREATOR.createFromParcel(param1Parcel1); 
      addPackage(uri);
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements IAppInstallerSession {
      public static IAppInstallerSession sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public void addPackage(Uri param2Uri) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.app.IAppInstallerSession");
          if (param2Uri != null) {
            parcel1.writeInt(1);
            param2Uri.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IAppInstallerSession.Stub.getDefaultImpl() != null) {
            IAppInstallerSession.Stub.getDefaultImpl().addPackage(param2Uri);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void addSplit(Uri param2Uri) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.app.IAppInstallerSession");
          if (param2Uri != null) {
            parcel1.writeInt(1);
            param2Uri.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IAppInstallerSession.Stub.getDefaultImpl() != null) {
            IAppInstallerSession.Stub.getDefaultImpl().addSplit(param2Uri);
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
      
      public void cancel() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.app.IAppInstallerSession");
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IAppInstallerSession.Stub.getDefaultImpl() != null) {
            IAppInstallerSession.Stub.getDefaultImpl().cancel();
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
          parcel1.writeInterfaceToken("com.lody.virtual.server.app.IAppInstallerSession");
          if (param2IntentSender != null) {
            parcel1.writeInt(1);
            param2IntentSender.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IAppInstallerSession.Stub.getDefaultImpl() != null) {
            IAppInstallerSession.Stub.getDefaultImpl().commit(param2IntentSender);
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
        return "com.lody.virtual.server.app.IAppInstallerSession";
      }
    }
  }
  
  private static class Proxy implements IAppInstallerSession {
    public static IAppInstallerSession sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public void addPackage(Uri param1Uri) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.app.IAppInstallerSession");
        if (param1Uri != null) {
          parcel1.writeInt(1);
          param1Uri.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IAppInstallerSession.Stub.getDefaultImpl() != null) {
          IAppInstallerSession.Stub.getDefaultImpl().addPackage(param1Uri);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void addSplit(Uri param1Uri) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.app.IAppInstallerSession");
        if (param1Uri != null) {
          parcel1.writeInt(1);
          param1Uri.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IAppInstallerSession.Stub.getDefaultImpl() != null) {
          IAppInstallerSession.Stub.getDefaultImpl().addSplit(param1Uri);
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
    
    public void cancel() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.app.IAppInstallerSession");
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IAppInstallerSession.Stub.getDefaultImpl() != null) {
          IAppInstallerSession.Stub.getDefaultImpl().cancel();
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
        parcel1.writeInterfaceToken("com.lody.virtual.server.app.IAppInstallerSession");
        if (param1IntentSender != null) {
          parcel1.writeInt(1);
          param1IntentSender.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IAppInstallerSession.Stub.getDefaultImpl() != null) {
          IAppInstallerSession.Stub.getDefaultImpl().commit(param1IntentSender);
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
      return "com.lody.virtual.server.app.IAppInstallerSession";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\app\IAppInstallerSession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */