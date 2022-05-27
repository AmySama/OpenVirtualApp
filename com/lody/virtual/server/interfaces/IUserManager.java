package com.lody.virtual.server.interfaces;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.lody.virtual.os.VUserInfo;
import java.util.List;

public interface IUserManager extends IInterface {
  VUserInfo createUser(String paramString, int paramInt) throws RemoteException;
  
  int getUserHandle(int paramInt) throws RemoteException;
  
  Bitmap getUserIcon(int paramInt) throws RemoteException;
  
  VUserInfo getUserInfo(int paramInt) throws RemoteException;
  
  int getUserSerialNumber(int paramInt) throws RemoteException;
  
  List<VUserInfo> getUsers(boolean paramBoolean) throws RemoteException;
  
  boolean isGuestEnabled() throws RemoteException;
  
  boolean removeUser(int paramInt) throws RemoteException;
  
  void setGuestEnabled(boolean paramBoolean) throws RemoteException;
  
  void setUserIcon(int paramInt, Bitmap paramBitmap) throws RemoteException;
  
  void setUserName(int paramInt, String paramString) throws RemoteException;
  
  void wipeUser(int paramInt) throws RemoteException;
  
  public static class Default implements IUserManager {
    public IBinder asBinder() {
      return null;
    }
    
    public VUserInfo createUser(String param1String, int param1Int) throws RemoteException {
      return null;
    }
    
    public int getUserHandle(int param1Int) throws RemoteException {
      return 0;
    }
    
    public Bitmap getUserIcon(int param1Int) throws RemoteException {
      return null;
    }
    
    public VUserInfo getUserInfo(int param1Int) throws RemoteException {
      return null;
    }
    
    public int getUserSerialNumber(int param1Int) throws RemoteException {
      return 0;
    }
    
    public List<VUserInfo> getUsers(boolean param1Boolean) throws RemoteException {
      return null;
    }
    
    public boolean isGuestEnabled() throws RemoteException {
      return false;
    }
    
    public boolean removeUser(int param1Int) throws RemoteException {
      return false;
    }
    
    public void setGuestEnabled(boolean param1Boolean) throws RemoteException {}
    
    public void setUserIcon(int param1Int, Bitmap param1Bitmap) throws RemoteException {}
    
    public void setUserName(int param1Int, String param1String) throws RemoteException {}
    
    public void wipeUser(int param1Int) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IUserManager {
    private static final String DESCRIPTOR = "com.lody.virtual.server.interfaces.IUserManager";
    
    static final int TRANSACTION_createUser = 1;
    
    static final int TRANSACTION_getUserHandle = 12;
    
    static final int TRANSACTION_getUserIcon = 5;
    
    static final int TRANSACTION_getUserInfo = 7;
    
    static final int TRANSACTION_getUserSerialNumber = 11;
    
    static final int TRANSACTION_getUsers = 6;
    
    static final int TRANSACTION_isGuestEnabled = 9;
    
    static final int TRANSACTION_removeUser = 2;
    
    static final int TRANSACTION_setGuestEnabled = 8;
    
    static final int TRANSACTION_setUserIcon = 4;
    
    static final int TRANSACTION_setUserName = 3;
    
    static final int TRANSACTION_wipeUser = 10;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.interfaces.IUserManager");
    }
    
    public static IUserManager asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.interfaces.IUserManager");
      return (iInterface != null && iInterface instanceof IUserManager) ? (IUserManager)iInterface : new Proxy(param1IBinder);
    }
    
    public static IUserManager getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IUserManager param1IUserManager) {
      if (Proxy.sDefaultImpl == null && param1IUserManager != null) {
        Proxy.sDefaultImpl = param1IUserManager;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1598968902) {
        boolean bool2;
        int i;
        boolean bool1;
        VUserInfo vUserInfo2;
        List<VUserInfo> list;
        Bitmap bitmap;
        boolean bool3 = false;
        boolean bool4 = false;
        switch (param1Int1) {
          default:
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2);
          case 12:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IUserManager");
            param1Int1 = getUserHandle(param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(param1Int1);
            return true;
          case 11:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IUserManager");
            param1Int1 = getUserSerialNumber(param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(param1Int1);
            return true;
          case 10:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IUserManager");
            wipeUser(param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 9:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IUserManager");
            bool2 = isGuestEnabled();
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool2);
            return true;
          case 8:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IUserManager");
            if (param1Parcel1.readInt() != 0)
              bool4 = true; 
            setGuestEnabled(bool4);
            param1Parcel2.writeNoException();
            return true;
          case 7:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IUserManager");
            vUserInfo2 = getUserInfo(param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            if (vUserInfo2 != null) {
              param1Parcel2.writeInt(1);
              vUserInfo2.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 6:
            vUserInfo2.enforceInterface("com.lody.virtual.server.interfaces.IUserManager");
            bool4 = bool3;
            if (vUserInfo2.readInt() != 0)
              bool4 = true; 
            list = getUsers(bool4);
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list);
            return true;
          case 5:
            list.enforceInterface("com.lody.virtual.server.interfaces.IUserManager");
            bitmap = getUserIcon(list.readInt());
            param1Parcel2.writeNoException();
            if (bitmap != null) {
              param1Parcel2.writeInt(1);
              bitmap.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 4:
            bitmap.enforceInterface("com.lody.virtual.server.interfaces.IUserManager");
            i = bitmap.readInt();
            if (bitmap.readInt() != 0) {
              bitmap = (Bitmap)Bitmap.CREATOR.createFromParcel((Parcel)bitmap);
            } else {
              bitmap = null;
            } 
            setUserIcon(i, bitmap);
            param1Parcel2.writeNoException();
            return true;
          case 3:
            bitmap.enforceInterface("com.lody.virtual.server.interfaces.IUserManager");
            setUserName(bitmap.readInt(), bitmap.readString());
            param1Parcel2.writeNoException();
            return true;
          case 2:
            bitmap.enforceInterface("com.lody.virtual.server.interfaces.IUserManager");
            bool1 = removeUser(bitmap.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool1);
            return true;
          case 1:
            break;
        } 
        bitmap.enforceInterface("com.lody.virtual.server.interfaces.IUserManager");
        VUserInfo vUserInfo1 = createUser(bitmap.readString(), bitmap.readInt());
        param1Parcel2.writeNoException();
        if (vUserInfo1 != null) {
          param1Parcel2.writeInt(1);
          vUserInfo1.writeToParcel(param1Parcel2, 1);
        } else {
          param1Parcel2.writeInt(0);
        } 
        return true;
      } 
      param1Parcel2.writeString("com.lody.virtual.server.interfaces.IUserManager");
      return true;
    }
    
    private static class Proxy implements IUserManager {
      public static IUserManager sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public VUserInfo createUser(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null)
            return IUserManager.Stub.getDefaultImpl().createUser(param2String, param2Int); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            VUserInfo vUserInfo = (VUserInfo)VUserInfo.CREATOR.createFromParcel(parcel2);
          } else {
            param2String = null;
          } 
          return (VUserInfo)param2String;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.interfaces.IUserManager";
      }
      
      public int getUserHandle(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(12, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
            param2Int = IUserManager.Stub.getDefaultImpl().getUserHandle(param2Int);
            return param2Int;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          return param2Int;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public Bitmap getUserIcon(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          Bitmap bitmap;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
            bitmap = IUserManager.Stub.getDefaultImpl().getUserIcon(param2Int);
            return bitmap;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            bitmap = (Bitmap)Bitmap.CREATOR.createFromParcel(parcel2);
          } else {
            bitmap = null;
          } 
          return bitmap;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public VUserInfo getUserInfo(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          VUserInfo vUserInfo;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
            vUserInfo = IUserManager.Stub.getDefaultImpl().getUserInfo(param2Int);
            return vUserInfo;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            vUserInfo = (VUserInfo)VUserInfo.CREATOR.createFromParcel(parcel2);
          } else {
            vUserInfo = null;
          } 
          return vUserInfo;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int getUserSerialNumber(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(11, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
            param2Int = IUserManager.Stub.getDefaultImpl().getUserSerialNumber(param2Int);
            return param2Int;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          return param2Int;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<VUserInfo> getUsers(boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null)
            return IUserManager.Stub.getDefaultImpl().getUsers(param2Boolean); 
          parcel2.readException();
          return parcel2.createTypedArrayList(VUserInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean isGuestEnabled() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(9, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
            bool = IUserManager.Stub.getDefaultImpl().isGuestEnabled();
            return bool;
          } 
          parcel2.readException();
          int i = parcel2.readInt();
          if (i != 0)
            bool = true; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean removeUser(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
          parcel1.writeInt(param2Int);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(2, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
            bool = IUserManager.Stub.getDefaultImpl().removeUser(param2Int);
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
      
      public void setGuestEnabled(boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
            IUserManager.Stub.getDefaultImpl().setGuestEnabled(param2Boolean);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setUserIcon(int param2Int, Bitmap param2Bitmap) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
          parcel1.writeInt(param2Int);
          if (param2Bitmap != null) {
            parcel1.writeInt(1);
            param2Bitmap.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
            IUserManager.Stub.getDefaultImpl().setUserIcon(param2Int, param2Bitmap);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setUserName(int param2Int, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
            IUserManager.Stub.getDefaultImpl().setUserName(param2Int, param2String);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void wipeUser(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(10, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
            IUserManager.Stub.getDefaultImpl().wipeUser(param2Int);
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
  
  private static class Proxy implements IUserManager {
    public static IUserManager sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public VUserInfo createUser(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null)
          return IUserManager.Stub.getDefaultImpl().createUser(param1String, param1Int); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          VUserInfo vUserInfo = (VUserInfo)VUserInfo.CREATOR.createFromParcel(parcel2);
        } else {
          param1String = null;
        } 
        return (VUserInfo)param1String;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.interfaces.IUserManager";
    }
    
    public int getUserHandle(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(12, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
          param1Int = IUserManager.Stub.getDefaultImpl().getUserHandle(param1Int);
          return param1Int;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        return param1Int;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public Bitmap getUserIcon(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        Bitmap bitmap;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
          bitmap = IUserManager.Stub.getDefaultImpl().getUserIcon(param1Int);
          return bitmap;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          bitmap = (Bitmap)Bitmap.CREATOR.createFromParcel(parcel2);
        } else {
          bitmap = null;
        } 
        return bitmap;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public VUserInfo getUserInfo(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        VUserInfo vUserInfo;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
          vUserInfo = IUserManager.Stub.getDefaultImpl().getUserInfo(param1Int);
          return vUserInfo;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          vUserInfo = (VUserInfo)VUserInfo.CREATOR.createFromParcel(parcel2);
        } else {
          vUserInfo = null;
        } 
        return vUserInfo;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int getUserSerialNumber(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(11, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
          param1Int = IUserManager.Stub.getDefaultImpl().getUserSerialNumber(param1Int);
          return param1Int;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        return param1Int;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<VUserInfo> getUsers(boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null)
          return IUserManager.Stub.getDefaultImpl().getUsers(param1Boolean); 
        parcel2.readException();
        return parcel2.createTypedArrayList(VUserInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean isGuestEnabled() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(9, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
          bool = IUserManager.Stub.getDefaultImpl().isGuestEnabled();
          return bool;
        } 
        parcel2.readException();
        int i = parcel2.readInt();
        if (i != 0)
          bool = true; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean removeUser(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
        parcel1.writeInt(param1Int);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(2, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
          bool = IUserManager.Stub.getDefaultImpl().removeUser(param1Int);
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
    
    public void setGuestEnabled(boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
          IUserManager.Stub.getDefaultImpl().setGuestEnabled(param1Boolean);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setUserIcon(int param1Int, Bitmap param1Bitmap) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
        parcel1.writeInt(param1Int);
        if (param1Bitmap != null) {
          parcel1.writeInt(1);
          param1Bitmap.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
          IUserManager.Stub.getDefaultImpl().setUserIcon(param1Int, param1Bitmap);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setUserName(int param1Int, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
          IUserManager.Stub.getDefaultImpl().setUserName(param1Int, param1String);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void wipeUser(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IUserManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(10, parcel1, parcel2, 0) && IUserManager.Stub.getDefaultImpl() != null) {
          IUserManager.Stub.getDefaultImpl().wipeUser(param1Int);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\interfaces\IUserManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */