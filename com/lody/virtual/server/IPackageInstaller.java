package com.lody.virtual.server;

import android.content.IntentSender;
import android.content.pm.IPackageInstallerCallback;
import android.content.pm.IPackageInstallerSession;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.lody.virtual.remote.VParceledListSlice;
import com.lody.virtual.server.pm.installer.SessionInfo;
import com.lody.virtual.server.pm.installer.SessionParams;

public interface IPackageInstaller extends IInterface {
  void abandonSession(int paramInt) throws RemoteException;
  
  int createSession(SessionParams paramSessionParams, String paramString, int paramInt) throws RemoteException;
  
  VParceledListSlice getAllSessions(int paramInt) throws RemoteException;
  
  VParceledListSlice getMySessions(String paramString, int paramInt) throws RemoteException;
  
  SessionInfo getSessionInfo(int paramInt) throws RemoteException;
  
  IPackageInstallerSession openSession(int paramInt) throws RemoteException;
  
  void registerCallback(IPackageInstallerCallback paramIPackageInstallerCallback, int paramInt) throws RemoteException;
  
  void setPermissionsResult(int paramInt, boolean paramBoolean) throws RemoteException;
  
  void uninstall(String paramString1, String paramString2, int paramInt1, IntentSender paramIntentSender, int paramInt2) throws RemoteException;
  
  void unregisterCallback(IPackageInstallerCallback paramIPackageInstallerCallback) throws RemoteException;
  
  void updateSessionAppIcon(int paramInt, Bitmap paramBitmap) throws RemoteException;
  
  void updateSessionAppLabel(int paramInt, String paramString) throws RemoteException;
  
  public static class Default implements IPackageInstaller {
    public void abandonSession(int param1Int) throws RemoteException {}
    
    public IBinder asBinder() {
      return null;
    }
    
    public int createSession(SessionParams param1SessionParams, String param1String, int param1Int) throws RemoteException {
      return 0;
    }
    
    public VParceledListSlice getAllSessions(int param1Int) throws RemoteException {
      return null;
    }
    
    public VParceledListSlice getMySessions(String param1String, int param1Int) throws RemoteException {
      return null;
    }
    
    public SessionInfo getSessionInfo(int param1Int) throws RemoteException {
      return null;
    }
    
    public IPackageInstallerSession openSession(int param1Int) throws RemoteException {
      return null;
    }
    
    public void registerCallback(IPackageInstallerCallback param1IPackageInstallerCallback, int param1Int) throws RemoteException {}
    
    public void setPermissionsResult(int param1Int, boolean param1Boolean) throws RemoteException {}
    
    public void uninstall(String param1String1, String param1String2, int param1Int1, IntentSender param1IntentSender, int param1Int2) throws RemoteException {}
    
    public void unregisterCallback(IPackageInstallerCallback param1IPackageInstallerCallback) throws RemoteException {}
    
    public void updateSessionAppIcon(int param1Int, Bitmap param1Bitmap) throws RemoteException {}
    
    public void updateSessionAppLabel(int param1Int, String param1String) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IPackageInstaller {
    private static final String DESCRIPTOR = "com.lody.virtual.server.IPackageInstaller";
    
    static final int TRANSACTION_abandonSession = 4;
    
    static final int TRANSACTION_createSession = 1;
    
    static final int TRANSACTION_getAllSessions = 7;
    
    static final int TRANSACTION_getMySessions = 8;
    
    static final int TRANSACTION_getSessionInfo = 6;
    
    static final int TRANSACTION_openSession = 5;
    
    static final int TRANSACTION_registerCallback = 9;
    
    static final int TRANSACTION_setPermissionsResult = 12;
    
    static final int TRANSACTION_uninstall = 11;
    
    static final int TRANSACTION_unregisterCallback = 10;
    
    static final int TRANSACTION_updateSessionAppIcon = 2;
    
    static final int TRANSACTION_updateSessionAppLabel = 3;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.IPackageInstaller");
    }
    
    public static IPackageInstaller asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.IPackageInstaller");
      return (iInterface != null && iInterface instanceof IPackageInstaller) ? (IPackageInstaller)iInterface : new Proxy(param1IBinder);
    }
    
    public static IPackageInstaller getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IPackageInstaller param1IPackageInstaller) {
      if (Proxy.sDefaultImpl == null && param1IPackageInstaller != null) {
        Proxy.sDefaultImpl = param1IPackageInstaller;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1598968902) {
        VParceledListSlice vParceledListSlice;
        SessionInfo sessionInfo;
        String str1;
        IBinder iBinder;
        IPackageInstallerSession iPackageInstallerSession;
        String str4;
        SessionParams sessionParams;
        boolean bool = false;
        String str2 = null;
        String str3 = null;
        Bitmap bitmap1 = null;
        IntentSender intentSender = null;
        switch (param1Int1) {
          default:
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2);
          case 12:
            param1Parcel1.enforceInterface("com.lody.virtual.server.IPackageInstaller");
            param1Int1 = param1Parcel1.readInt();
            if (param1Parcel1.readInt() != 0)
              bool = true; 
            setPermissionsResult(param1Int1, bool);
            param1Parcel2.writeNoException();
            return true;
          case 11:
            param1Parcel1.enforceInterface("com.lody.virtual.server.IPackageInstaller");
            str2 = param1Parcel1.readString();
            str3 = param1Parcel1.readString();
            param1Int1 = param1Parcel1.readInt();
            if (param1Parcel1.readInt() != 0)
              intentSender = (IntentSender)IntentSender.CREATOR.createFromParcel(param1Parcel1); 
            uninstall(str2, str3, param1Int1, intentSender, param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 10:
            param1Parcel1.enforceInterface("com.lody.virtual.server.IPackageInstaller");
            unregisterCallback(IPackageInstallerCallback.Stub.asInterface(param1Parcel1.readStrongBinder()));
            param1Parcel2.writeNoException();
            return true;
          case 9:
            param1Parcel1.enforceInterface("com.lody.virtual.server.IPackageInstaller");
            registerCallback(IPackageInstallerCallback.Stub.asInterface(param1Parcel1.readStrongBinder()), param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 8:
            param1Parcel1.enforceInterface("com.lody.virtual.server.IPackageInstaller");
            vParceledListSlice = getMySessions(param1Parcel1.readString(), param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            if (vParceledListSlice != null) {
              param1Parcel2.writeInt(1);
              vParceledListSlice.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 7:
            vParceledListSlice.enforceInterface("com.lody.virtual.server.IPackageInstaller");
            vParceledListSlice = getAllSessions(vParceledListSlice.readInt());
            param1Parcel2.writeNoException();
            if (vParceledListSlice != null) {
              param1Parcel2.writeInt(1);
              vParceledListSlice.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 6:
            vParceledListSlice.enforceInterface("com.lody.virtual.server.IPackageInstaller");
            sessionInfo = getSessionInfo(vParceledListSlice.readInt());
            param1Parcel2.writeNoException();
            if (sessionInfo != null) {
              param1Parcel2.writeInt(1);
              sessionInfo.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 5:
            sessionInfo.enforceInterface("com.lody.virtual.server.IPackageInstaller");
            iPackageInstallerSession = openSession(sessionInfo.readInt());
            param1Parcel2.writeNoException();
            str1 = str2;
            if (iPackageInstallerSession != null)
              iBinder = iPackageInstallerSession.asBinder(); 
            param1Parcel2.writeStrongBinder(iBinder);
            return true;
          case 4:
            iBinder.enforceInterface("com.lody.virtual.server.IPackageInstaller");
            abandonSession(iBinder.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 3:
            iBinder.enforceInterface("com.lody.virtual.server.IPackageInstaller");
            updateSessionAppLabel(iBinder.readInt(), iBinder.readString());
            param1Parcel2.writeNoException();
            return true;
          case 2:
            iBinder.enforceInterface("com.lody.virtual.server.IPackageInstaller");
            param1Int1 = iBinder.readInt();
            str4 = str3;
            if (iBinder.readInt() != 0)
              bitmap2 = (Bitmap)Bitmap.CREATOR.createFromParcel((Parcel)iBinder); 
            updateSessionAppIcon(param1Int1, bitmap2);
            param1Parcel2.writeNoException();
            return true;
          case 1:
            break;
        } 
        iBinder.enforceInterface("com.lody.virtual.server.IPackageInstaller");
        Bitmap bitmap2 = bitmap1;
        if (iBinder.readInt() != 0)
          sessionParams = (SessionParams)SessionParams.CREATOR.createFromParcel((Parcel)iBinder); 
        param1Int1 = createSession(sessionParams, iBinder.readString(), iBinder.readInt());
        param1Parcel2.writeNoException();
        param1Parcel2.writeInt(param1Int1);
        return true;
      } 
      param1Parcel2.writeString("com.lody.virtual.server.IPackageInstaller");
      return true;
    }
    
    private static class Proxy implements IPackageInstaller {
      public static IPackageInstaller sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public void abandonSession(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
            IPackageInstaller.Stub.getDefaultImpl().abandonSession(param2Int);
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
      
      public int createSession(SessionParams param2SessionParams, String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
          if (param2SessionParams != null) {
            parcel1.writeInt(1);
            param2SessionParams.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
            param2Int = IPackageInstaller.Stub.getDefaultImpl().createSession(param2SessionParams, param2String, param2Int);
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
      
      public VParceledListSlice getAllSessions(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          VParceledListSlice vParceledListSlice;
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
            vParceledListSlice = IPackageInstaller.Stub.getDefaultImpl().getAllSessions(param2Int);
            return vParceledListSlice;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            vParceledListSlice = (VParceledListSlice)VParceledListSlice.CREATOR.createFromParcel(parcel2);
          } else {
            vParceledListSlice = null;
          } 
          return vParceledListSlice;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.IPackageInstaller";
      }
      
      public VParceledListSlice getMySessions(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null)
            return IPackageInstaller.Stub.getDefaultImpl().getMySessions(param2String, param2Int); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            VParceledListSlice vParceledListSlice = (VParceledListSlice)VParceledListSlice.CREATOR.createFromParcel(parcel2);
          } else {
            param2String = null;
          } 
          return (VParceledListSlice)param2String;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public SessionInfo getSessionInfo(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          SessionInfo sessionInfo;
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
            sessionInfo = IPackageInstaller.Stub.getDefaultImpl().getSessionInfo(param2Int);
            return sessionInfo;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            sessionInfo = (SessionInfo)SessionInfo.CREATOR.createFromParcel(parcel2);
          } else {
            sessionInfo = null;
          } 
          return sessionInfo;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public IPackageInstallerSession openSession(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null)
            return IPackageInstaller.Stub.getDefaultImpl().openSession(param2Int); 
          parcel2.readException();
          return IPackageInstallerSession.Stub.asInterface(parcel2.readStrongBinder());
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void registerCallback(IPackageInstallerCallback param2IPackageInstallerCallback, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
          if (param2IPackageInstallerCallback != null) {
            iBinder = param2IPackageInstallerCallback.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(9, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
            IPackageInstaller.Stub.getDefaultImpl().registerCallback(param2IPackageInstallerCallback, param2Int);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setPermissionsResult(int param2Int, boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
          parcel1.writeInt(param2Int);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(12, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
            IPackageInstaller.Stub.getDefaultImpl().setPermissionsResult(param2Int, param2Boolean);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void uninstall(String param2String1, String param2String2, int param2Int1, IntentSender param2IntentSender, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          parcel1.writeInt(param2Int1);
          if (param2IntentSender != null) {
            parcel1.writeInt(1);
            param2IntentSender.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(11, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
            IPackageInstaller.Stub.getDefaultImpl().uninstall(param2String1, param2String2, param2Int1, param2IntentSender, param2Int2);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void unregisterCallback(IPackageInstallerCallback param2IPackageInstallerCallback) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
          if (param2IPackageInstallerCallback != null) {
            iBinder = param2IPackageInstallerCallback.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (!this.mRemote.transact(10, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
            IPackageInstaller.Stub.getDefaultImpl().unregisterCallback(param2IPackageInstallerCallback);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void updateSessionAppIcon(int param2Int, Bitmap param2Bitmap) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
          parcel1.writeInt(param2Int);
          if (param2Bitmap != null) {
            parcel1.writeInt(1);
            param2Bitmap.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
            IPackageInstaller.Stub.getDefaultImpl().updateSessionAppIcon(param2Int, param2Bitmap);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void updateSessionAppLabel(int param2Int, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
            IPackageInstaller.Stub.getDefaultImpl().updateSessionAppLabel(param2Int, param2String);
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
  
  private static class Proxy implements IPackageInstaller {
    public static IPackageInstaller sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public void abandonSession(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
          IPackageInstaller.Stub.getDefaultImpl().abandonSession(param1Int);
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
    
    public int createSession(SessionParams param1SessionParams, String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
        if (param1SessionParams != null) {
          parcel1.writeInt(1);
          param1SessionParams.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
          param1Int = IPackageInstaller.Stub.getDefaultImpl().createSession(param1SessionParams, param1String, param1Int);
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
    
    public VParceledListSlice getAllSessions(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        VParceledListSlice vParceledListSlice;
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
          vParceledListSlice = IPackageInstaller.Stub.getDefaultImpl().getAllSessions(param1Int);
          return vParceledListSlice;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          vParceledListSlice = (VParceledListSlice)VParceledListSlice.CREATOR.createFromParcel(parcel2);
        } else {
          vParceledListSlice = null;
        } 
        return vParceledListSlice;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.IPackageInstaller";
    }
    
    public VParceledListSlice getMySessions(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null)
          return IPackageInstaller.Stub.getDefaultImpl().getMySessions(param1String, param1Int); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          VParceledListSlice vParceledListSlice = (VParceledListSlice)VParceledListSlice.CREATOR.createFromParcel(parcel2);
        } else {
          param1String = null;
        } 
        return (VParceledListSlice)param1String;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public SessionInfo getSessionInfo(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        SessionInfo sessionInfo;
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
          sessionInfo = IPackageInstaller.Stub.getDefaultImpl().getSessionInfo(param1Int);
          return sessionInfo;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          sessionInfo = (SessionInfo)SessionInfo.CREATOR.createFromParcel(parcel2);
        } else {
          sessionInfo = null;
        } 
        return sessionInfo;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public IPackageInstallerSession openSession(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null)
          return IPackageInstaller.Stub.getDefaultImpl().openSession(param1Int); 
        parcel2.readException();
        return IPackageInstallerSession.Stub.asInterface(parcel2.readStrongBinder());
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void registerCallback(IPackageInstallerCallback param1IPackageInstallerCallback, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
        if (param1IPackageInstallerCallback != null) {
          iBinder = param1IPackageInstallerCallback.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(9, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
          IPackageInstaller.Stub.getDefaultImpl().registerCallback(param1IPackageInstallerCallback, param1Int);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setPermissionsResult(int param1Int, boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
        parcel1.writeInt(param1Int);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(12, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
          IPackageInstaller.Stub.getDefaultImpl().setPermissionsResult(param1Int, param1Boolean);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void uninstall(String param1String1, String param1String2, int param1Int1, IntentSender param1IntentSender, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        parcel1.writeInt(param1Int1);
        if (param1IntentSender != null) {
          parcel1.writeInt(1);
          param1IntentSender.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(11, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
          IPackageInstaller.Stub.getDefaultImpl().uninstall(param1String1, param1String2, param1Int1, param1IntentSender, param1Int2);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void unregisterCallback(IPackageInstallerCallback param1IPackageInstallerCallback) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
        if (param1IPackageInstallerCallback != null) {
          iBinder = param1IPackageInstallerCallback.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (!this.mRemote.transact(10, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
          IPackageInstaller.Stub.getDefaultImpl().unregisterCallback(param1IPackageInstallerCallback);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void updateSessionAppIcon(int param1Int, Bitmap param1Bitmap) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
        parcel1.writeInt(param1Int);
        if (param1Bitmap != null) {
          parcel1.writeInt(1);
          param1Bitmap.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
          IPackageInstaller.Stub.getDefaultImpl().updateSessionAppIcon(param1Int, param1Bitmap);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void updateSessionAppLabel(int param1Int, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.IPackageInstaller");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IPackageInstaller.Stub.getDefaultImpl() != null) {
          IPackageInstaller.Stub.getDefaultImpl().updateSessionAppLabel(param1Int, param1String);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\IPackageInstaller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */