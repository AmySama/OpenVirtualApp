package com.lody.virtual.server.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface INotificationManager extends IInterface {
  void addNotification(int paramInt1, String paramString1, String paramString2, int paramInt2) throws RemoteException;
  
  boolean areNotificationsEnabledForPackage(String paramString, int paramInt) throws RemoteException;
  
  void cancelAllNotification(String paramString, int paramInt) throws RemoteException;
  
  int dealNotificationId(int paramInt1, String paramString1, String paramString2, int paramInt2) throws RemoteException;
  
  String dealNotificationTag(int paramInt1, String paramString1, String paramString2, int paramInt2) throws RemoteException;
  
  void setNotificationsEnabledForPackage(String paramString, boolean paramBoolean, int paramInt) throws RemoteException;
  
  public static class Default implements INotificationManager {
    public void addNotification(int param1Int1, String param1String1, String param1String2, int param1Int2) throws RemoteException {}
    
    public boolean areNotificationsEnabledForPackage(String param1String, int param1Int) throws RemoteException {
      return false;
    }
    
    public IBinder asBinder() {
      return null;
    }
    
    public void cancelAllNotification(String param1String, int param1Int) throws RemoteException {}
    
    public int dealNotificationId(int param1Int1, String param1String1, String param1String2, int param1Int2) throws RemoteException {
      return 0;
    }
    
    public String dealNotificationTag(int param1Int1, String param1String1, String param1String2, int param1Int2) throws RemoteException {
      return null;
    }
    
    public void setNotificationsEnabledForPackage(String param1String, boolean param1Boolean, int param1Int) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements INotificationManager {
    private static final String DESCRIPTOR = "com.lody.virtual.server.interfaces.INotificationManager";
    
    static final int TRANSACTION_addNotification = 5;
    
    static final int TRANSACTION_areNotificationsEnabledForPackage = 3;
    
    static final int TRANSACTION_cancelAllNotification = 6;
    
    static final int TRANSACTION_dealNotificationId = 1;
    
    static final int TRANSACTION_dealNotificationTag = 2;
    
    static final int TRANSACTION_setNotificationsEnabledForPackage = 4;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.interfaces.INotificationManager");
    }
    
    public static INotificationManager asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.interfaces.INotificationManager");
      return (iInterface != null && iInterface instanceof INotificationManager) ? (INotificationManager)iInterface : new Proxy(param1IBinder);
    }
    
    public static INotificationManager getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(INotificationManager param1INotificationManager) {
      if (Proxy.sDefaultImpl == null && param1INotificationManager != null) {
        Proxy.sDefaultImpl = param1INotificationManager;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1598968902) {
        boolean bool;
        String str1;
        String str2;
        boolean bool1;
        switch (param1Int1) {
          default:
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2);
          case 6:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.INotificationManager");
            cancelAllNotification(param1Parcel1.readString(), param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 5:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.INotificationManager");
            addNotification(param1Parcel1.readInt(), param1Parcel1.readString(), param1Parcel1.readString(), param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 4:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.INotificationManager");
            str2 = param1Parcel1.readString();
            if (param1Parcel1.readInt() != 0) {
              bool1 = true;
            } else {
              bool1 = false;
            } 
            setNotificationsEnabledForPackage(str2, bool1, param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 3:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.INotificationManager");
            bool = areNotificationsEnabledForPackage(param1Parcel1.readString(), param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool);
            return true;
          case 2:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.INotificationManager");
            str1 = dealNotificationTag(param1Parcel1.readInt(), param1Parcel1.readString(), param1Parcel1.readString(), param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeString(str1);
            return true;
          case 1:
            break;
        } 
        str1.enforceInterface("com.lody.virtual.server.interfaces.INotificationManager");
        int i = dealNotificationId(str1.readInt(), str1.readString(), str1.readString(), str1.readInt());
        param1Parcel2.writeNoException();
        param1Parcel2.writeInt(i);
        return true;
      } 
      param1Parcel2.writeString("com.lody.virtual.server.interfaces.INotificationManager");
      return true;
    }
    
    private static class Proxy implements INotificationManager {
      public static INotificationManager sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public void addNotification(int param2Int1, String param2String1, String param2String2, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.INotificationManager");
          parcel1.writeInt(param2Int1);
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(5, parcel1, parcel2, 0) && INotificationManager.Stub.getDefaultImpl() != null) {
            INotificationManager.Stub.getDefaultImpl().addNotification(param2Int1, param2String1, param2String2, param2Int2);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean areNotificationsEnabledForPackage(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.INotificationManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(3, parcel1, parcel2, 0) && INotificationManager.Stub.getDefaultImpl() != null) {
            bool = INotificationManager.Stub.getDefaultImpl().areNotificationsEnabledForPackage(param2String, param2Int);
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
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public void cancelAllNotification(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.INotificationManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(6, parcel1, parcel2, 0) && INotificationManager.Stub.getDefaultImpl() != null) {
            INotificationManager.Stub.getDefaultImpl().cancelAllNotification(param2String, param2Int);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int dealNotificationId(int param2Int1, String param2String1, String param2String2, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.INotificationManager");
          parcel1.writeInt(param2Int1);
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && INotificationManager.Stub.getDefaultImpl() != null) {
            param2Int1 = INotificationManager.Stub.getDefaultImpl().dealNotificationId(param2Int1, param2String1, param2String2, param2Int2);
            return param2Int1;
          } 
          parcel2.readException();
          param2Int1 = parcel2.readInt();
          return param2Int1;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String dealNotificationTag(int param2Int1, String param2String1, String param2String2, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.INotificationManager");
          parcel1.writeInt(param2Int1);
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && INotificationManager.Stub.getDefaultImpl() != null) {
            param2String1 = INotificationManager.Stub.getDefaultImpl().dealNotificationTag(param2Int1, param2String1, param2String2, param2Int2);
            return param2String1;
          } 
          parcel2.readException();
          param2String1 = parcel2.readString();
          return param2String1;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.interfaces.INotificationManager";
      }
      
      public void setNotificationsEnabledForPackage(String param2String, boolean param2Boolean, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.INotificationManager");
          parcel1.writeString(param2String);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && INotificationManager.Stub.getDefaultImpl() != null) {
            INotificationManager.Stub.getDefaultImpl().setNotificationsEnabledForPackage(param2String, param2Boolean, param2Int);
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
  
  private static class Proxy implements INotificationManager {
    public static INotificationManager sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public void addNotification(int param1Int1, String param1String1, String param1String2, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.INotificationManager");
        parcel1.writeInt(param1Int1);
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(5, parcel1, parcel2, 0) && INotificationManager.Stub.getDefaultImpl() != null) {
          INotificationManager.Stub.getDefaultImpl().addNotification(param1Int1, param1String1, param1String2, param1Int2);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean areNotificationsEnabledForPackage(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.INotificationManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(3, parcel1, parcel2, 0) && INotificationManager.Stub.getDefaultImpl() != null) {
          bool = INotificationManager.Stub.getDefaultImpl().areNotificationsEnabledForPackage(param1String, param1Int);
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
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public void cancelAllNotification(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.INotificationManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(6, parcel1, parcel2, 0) && INotificationManager.Stub.getDefaultImpl() != null) {
          INotificationManager.Stub.getDefaultImpl().cancelAllNotification(param1String, param1Int);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int dealNotificationId(int param1Int1, String param1String1, String param1String2, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.INotificationManager");
        parcel1.writeInt(param1Int1);
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && INotificationManager.Stub.getDefaultImpl() != null) {
          param1Int1 = INotificationManager.Stub.getDefaultImpl().dealNotificationId(param1Int1, param1String1, param1String2, param1Int2);
          return param1Int1;
        } 
        parcel2.readException();
        param1Int1 = parcel2.readInt();
        return param1Int1;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String dealNotificationTag(int param1Int1, String param1String1, String param1String2, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.INotificationManager");
        parcel1.writeInt(param1Int1);
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && INotificationManager.Stub.getDefaultImpl() != null) {
          param1String1 = INotificationManager.Stub.getDefaultImpl().dealNotificationTag(param1Int1, param1String1, param1String2, param1Int2);
          return param1String1;
        } 
        parcel2.readException();
        param1String1 = parcel2.readString();
        return param1String1;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.interfaces.INotificationManager";
    }
    
    public void setNotificationsEnabledForPackage(String param1String, boolean param1Boolean, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.INotificationManager");
        parcel1.writeString(param1String);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && INotificationManager.Stub.getDefaultImpl() != null) {
          INotificationManager.Stub.getDefaultImpl().setNotificationsEnabledForPackage(param1String, param1Boolean, param1Int);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\interfaces\INotificationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */