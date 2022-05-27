package com.lody.virtual.server.interfaces;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.lody.virtual.remote.InstalledAppInfo;
import com.lody.virtual.remote.VAppInstallerParams;
import com.lody.virtual.remote.VAppInstallerResult;
import java.util.List;

public interface IAppManager extends IInterface {
  boolean cleanPackageData(String paramString, int paramInt) throws RemoteException;
  
  int getInstalledAppCount() throws RemoteException;
  
  InstalledAppInfo getInstalledAppInfo(String paramString, int paramInt) throws RemoteException;
  
  List<InstalledAppInfo> getInstalledApps(int paramInt) throws RemoteException;
  
  List<InstalledAppInfo> getInstalledAppsAsUser(int paramInt1, int paramInt2) throws RemoteException;
  
  List<String> getInstalledSplitNames(String paramString) throws RemoteException;
  
  int[] getPackageInstalledUsers(String paramString) throws RemoteException;
  
  int getUidForSharedUser(String paramString) throws RemoteException;
  
  VAppInstallerResult installPackage(Uri paramUri, VAppInstallerParams paramVAppInstallerParams) throws RemoteException;
  
  boolean installPackageAsUser(int paramInt, String paramString) throws RemoteException;
  
  boolean isAppInstalled(String paramString) throws RemoteException;
  
  boolean isAppInstalledAsUser(int paramInt, String paramString) throws RemoteException;
  
  boolean isPackageLaunched(int paramInt, String paramString) throws RemoteException;
  
  boolean isRunInExtProcess(String paramString) throws RemoteException;
  
  void registerObserver(IPackageObserver paramIPackageObserver) throws RemoteException;
  
  void scanApps() throws RemoteException;
  
  void setPackageHidden(int paramInt, String paramString, boolean paramBoolean) throws RemoteException;
  
  boolean uninstallPackage(String paramString) throws RemoteException;
  
  boolean uninstallPackageAsUser(String paramString, int paramInt) throws RemoteException;
  
  void unregisterObserver(IPackageObserver paramIPackageObserver) throws RemoteException;
  
  public static class Default implements IAppManager {
    public IBinder asBinder() {
      return null;
    }
    
    public boolean cleanPackageData(String param1String, int param1Int) throws RemoteException {
      return false;
    }
    
    public int getInstalledAppCount() throws RemoteException {
      return 0;
    }
    
    public InstalledAppInfo getInstalledAppInfo(String param1String, int param1Int) throws RemoteException {
      return null;
    }
    
    public List<InstalledAppInfo> getInstalledApps(int param1Int) throws RemoteException {
      return null;
    }
    
    public List<InstalledAppInfo> getInstalledAppsAsUser(int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public List<String> getInstalledSplitNames(String param1String) throws RemoteException {
      return null;
    }
    
    public int[] getPackageInstalledUsers(String param1String) throws RemoteException {
      return null;
    }
    
    public int getUidForSharedUser(String param1String) throws RemoteException {
      return 0;
    }
    
    public VAppInstallerResult installPackage(Uri param1Uri, VAppInstallerParams param1VAppInstallerParams) throws RemoteException {
      return null;
    }
    
    public boolean installPackageAsUser(int param1Int, String param1String) throws RemoteException {
      return false;
    }
    
    public boolean isAppInstalled(String param1String) throws RemoteException {
      return false;
    }
    
    public boolean isAppInstalledAsUser(int param1Int, String param1String) throws RemoteException {
      return false;
    }
    
    public boolean isPackageLaunched(int param1Int, String param1String) throws RemoteException {
      return false;
    }
    
    public boolean isRunInExtProcess(String param1String) throws RemoteException {
      return false;
    }
    
    public void registerObserver(IPackageObserver param1IPackageObserver) throws RemoteException {}
    
    public void scanApps() throws RemoteException {}
    
    public void setPackageHidden(int param1Int, String param1String, boolean param1Boolean) throws RemoteException {}
    
    public boolean uninstallPackage(String param1String) throws RemoteException {
      return false;
    }
    
    public boolean uninstallPackageAsUser(String param1String, int param1Int) throws RemoteException {
      return false;
    }
    
    public void unregisterObserver(IPackageObserver param1IPackageObserver) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IAppManager {
    private static final String DESCRIPTOR = "com.lody.virtual.server.interfaces.IAppManager";
    
    static final int TRANSACTION_cleanPackageData = 20;
    
    static final int TRANSACTION_getInstalledAppCount = 14;
    
    static final int TRANSACTION_getInstalledAppInfo = 4;
    
    static final int TRANSACTION_getInstalledApps = 11;
    
    static final int TRANSACTION_getInstalledAppsAsUser = 12;
    
    static final int TRANSACTION_getInstalledSplitNames = 13;
    
    static final int TRANSACTION_getPackageInstalledUsers = 1;
    
    static final int TRANSACTION_getUidForSharedUser = 3;
    
    static final int TRANSACTION_installPackage = 5;
    
    static final int TRANSACTION_installPackageAsUser = 8;
    
    static final int TRANSACTION_isAppInstalled = 15;
    
    static final int TRANSACTION_isAppInstalledAsUser = 16;
    
    static final int TRANSACTION_isPackageLaunched = 6;
    
    static final int TRANSACTION_isRunInExtProcess = 19;
    
    static final int TRANSACTION_registerObserver = 17;
    
    static final int TRANSACTION_scanApps = 2;
    
    static final int TRANSACTION_setPackageHidden = 7;
    
    static final int TRANSACTION_uninstallPackage = 10;
    
    static final int TRANSACTION_uninstallPackageAsUser = 9;
    
    static final int TRANSACTION_unregisterObserver = 18;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.interfaces.IAppManager");
    }
    
    public static IAppManager asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.interfaces.IAppManager");
      return (iInterface != null && iInterface instanceof IAppManager) ? (IAppManager)iInterface : new Proxy(param1IBinder);
    }
    
    public static IAppManager getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IAppManager param1IAppManager) {
      if (Proxy.sDefaultImpl == null && param1IAppManager != null) {
        Proxy.sDefaultImpl = param1IAppManager;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1598968902) {
        boolean bool3;
        int k;
        boolean bool2;
        int j;
        boolean bool1;
        int i;
        List<String> list;
        VAppInstallerResult vAppInstallerResult;
        InstalledAppInfo installedAppInfo;
        String str;
        VAppInstallerParams vAppInstallerParams;
        boolean bool = false;
        switch (param1Int1) {
          default:
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2);
          case 20:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            bool3 = cleanPackageData(param1Parcel1.readString(), param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool3);
            return true;
          case 19:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            bool3 = isRunInExtProcess(param1Parcel1.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool3);
            return true;
          case 18:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            unregisterObserver(IPackageObserver.Stub.asInterface(param1Parcel1.readStrongBinder()));
            param1Parcel2.writeNoException();
            return true;
          case 17:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            registerObserver(IPackageObserver.Stub.asInterface(param1Parcel1.readStrongBinder()));
            param1Parcel2.writeNoException();
            return true;
          case 16:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            bool3 = isAppInstalledAsUser(param1Parcel1.readInt(), param1Parcel1.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool3);
            return true;
          case 15:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            bool3 = isAppInstalled(param1Parcel1.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool3);
            return true;
          case 14:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            k = getInstalledAppCount();
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(k);
            return true;
          case 13:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            list = getInstalledSplitNames(param1Parcel1.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeStringList(list);
            return true;
          case 12:
            list.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            list = (List)getInstalledAppsAsUser(list.readInt(), list.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list);
            return true;
          case 11:
            list.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            list = (List)getInstalledApps(list.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list);
            return true;
          case 10:
            list.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            bool2 = uninstallPackage(list.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool2);
            return true;
          case 9:
            list.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            bool2 = uninstallPackageAsUser(list.readString(), list.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool2);
            return true;
          case 8:
            list.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            bool2 = installPackageAsUser(list.readInt(), list.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool2);
            return true;
          case 7:
            list.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            j = list.readInt();
            str = list.readString();
            if (list.readInt() != 0)
              bool = true; 
            setPackageHidden(j, str, bool);
            param1Parcel2.writeNoException();
            return true;
          case 6:
            list.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            bool1 = isPackageLaunched(list.readInt(), list.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool1);
            return true;
          case 5:
            list.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            i = list.readInt();
            vAppInstallerParams = null;
            if (i != 0) {
              Uri uri = (Uri)Uri.CREATOR.createFromParcel((Parcel)list);
            } else {
              str = null;
            } 
            if (list.readInt() != 0)
              vAppInstallerParams = (VAppInstallerParams)VAppInstallerParams.CREATOR.createFromParcel((Parcel)list); 
            vAppInstallerResult = installPackage((Uri)str, vAppInstallerParams);
            param1Parcel2.writeNoException();
            if (vAppInstallerResult != null) {
              param1Parcel2.writeInt(1);
              vAppInstallerResult.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 4:
            vAppInstallerResult.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            installedAppInfo = getInstalledAppInfo(vAppInstallerResult.readString(), vAppInstallerResult.readInt());
            param1Parcel2.writeNoException();
            if (installedAppInfo != null) {
              param1Parcel2.writeInt(1);
              installedAppInfo.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 3:
            installedAppInfo.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            i = getUidForSharedUser(installedAppInfo.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(i);
            return true;
          case 2:
            installedAppInfo.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
            scanApps();
            param1Parcel2.writeNoException();
            return true;
          case 1:
            break;
        } 
        installedAppInfo.enforceInterface("com.lody.virtual.server.interfaces.IAppManager");
        int[] arrayOfInt = getPackageInstalledUsers(installedAppInfo.readString());
        param1Parcel2.writeNoException();
        param1Parcel2.writeIntArray(arrayOfInt);
        return true;
      } 
      param1Parcel2.writeString("com.lody.virtual.server.interfaces.IAppManager");
      return true;
    }
    
    private static class Proxy implements IAppManager {
      public static IAppManager sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public boolean cleanPackageData(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(20, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
            bool = IAppManager.Stub.getDefaultImpl().cleanPackageData(param2String, param2Int);
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
      
      public int getInstalledAppCount() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          if (!this.mRemote.transact(14, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null)
            return IAppManager.Stub.getDefaultImpl().getInstalledAppCount(); 
          parcel2.readException();
          return parcel2.readInt();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public InstalledAppInfo getInstalledAppInfo(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null)
            return IAppManager.Stub.getDefaultImpl().getInstalledAppInfo(param2String, param2Int); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            InstalledAppInfo installedAppInfo = (InstalledAppInfo)InstalledAppInfo.CREATOR.createFromParcel(parcel2);
          } else {
            param2String = null;
          } 
          return (InstalledAppInfo)param2String;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<InstalledAppInfo> getInstalledApps(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(11, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null)
            return IAppManager.Stub.getDefaultImpl().getInstalledApps(param2Int); 
          parcel2.readException();
          return parcel2.createTypedArrayList(InstalledAppInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<InstalledAppInfo> getInstalledAppsAsUser(int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(12, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null)
            return IAppManager.Stub.getDefaultImpl().getInstalledAppsAsUser(param2Int1, param2Int2); 
          parcel2.readException();
          return parcel2.createTypedArrayList(InstalledAppInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<String> getInstalledSplitNames(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(13, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null)
            return IAppManager.Stub.getDefaultImpl().getInstalledSplitNames(param2String); 
          parcel2.readException();
          return parcel2.createStringArrayList();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.interfaces.IAppManager";
      }
      
      public int[] getPackageInstalledUsers(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null)
            return IAppManager.Stub.getDefaultImpl().getPackageInstalledUsers(param2String); 
          parcel2.readException();
          return parcel2.createIntArray();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int getUidForSharedUser(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null)
            return IAppManager.Stub.getDefaultImpl().getUidForSharedUser(param2String); 
          parcel2.readException();
          return parcel2.readInt();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public VAppInstallerResult installPackage(Uri param2Uri, VAppInstallerParams param2VAppInstallerParams) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          if (param2Uri != null) {
            parcel1.writeInt(1);
            param2Uri.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (param2VAppInstallerParams != null) {
            parcel1.writeInt(1);
            param2VAppInstallerParams.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null)
            return IAppManager.Stub.getDefaultImpl().installPackage(param2Uri, param2VAppInstallerParams); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            VAppInstallerResult vAppInstallerResult = (VAppInstallerResult)VAppInstallerResult.CREATOR.createFromParcel(parcel2);
          } else {
            param2Uri = null;
          } 
          return (VAppInstallerResult)param2Uri;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean installPackageAsUser(int param2Int, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(8, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
            bool = IAppManager.Stub.getDefaultImpl().installPackageAsUser(param2Int, param2String);
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
      
      public boolean isAppInstalled(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          parcel1.writeString(param2String);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(15, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
            bool = IAppManager.Stub.getDefaultImpl().isAppInstalled(param2String);
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
      
      public boolean isAppInstalledAsUser(int param2Int, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(16, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
            bool = IAppManager.Stub.getDefaultImpl().isAppInstalledAsUser(param2Int, param2String);
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
      
      public boolean isPackageLaunched(int param2Int, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(6, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
            bool = IAppManager.Stub.getDefaultImpl().isPackageLaunched(param2Int, param2String);
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
      
      public boolean isRunInExtProcess(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          parcel1.writeString(param2String);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(19, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
            bool = IAppManager.Stub.getDefaultImpl().isRunInExtProcess(param2String);
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
      
      public void registerObserver(IPackageObserver param2IPackageObserver) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          if (param2IPackageObserver != null) {
            iBinder = param2IPackageObserver.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (!this.mRemote.transact(17, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
            IAppManager.Stub.getDefaultImpl().registerObserver(param2IPackageObserver);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void scanApps() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
            IAppManager.Stub.getDefaultImpl().scanApps();
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setPackageHidden(int param2Int, String param2String, boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
            IAppManager.Stub.getDefaultImpl().setPackageHidden(param2Int, param2String, param2Boolean);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean uninstallPackage(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          parcel1.writeString(param2String);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(10, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
            bool = IAppManager.Stub.getDefaultImpl().uninstallPackage(param2String);
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
      
      public boolean uninstallPackageAsUser(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(9, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
            bool = IAppManager.Stub.getDefaultImpl().uninstallPackageAsUser(param2String, param2Int);
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
      
      public void unregisterObserver(IPackageObserver param2IPackageObserver) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
          if (param2IPackageObserver != null) {
            iBinder = param2IPackageObserver.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (!this.mRemote.transact(18, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
            IAppManager.Stub.getDefaultImpl().unregisterObserver(param2IPackageObserver);
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
  
  private static class Proxy implements IAppManager {
    public static IAppManager sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public boolean cleanPackageData(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(20, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
          bool = IAppManager.Stub.getDefaultImpl().cleanPackageData(param1String, param1Int);
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
    
    public int getInstalledAppCount() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        if (!this.mRemote.transact(14, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null)
          return IAppManager.Stub.getDefaultImpl().getInstalledAppCount(); 
        parcel2.readException();
        return parcel2.readInt();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public InstalledAppInfo getInstalledAppInfo(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null)
          return IAppManager.Stub.getDefaultImpl().getInstalledAppInfo(param1String, param1Int); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          InstalledAppInfo installedAppInfo = (InstalledAppInfo)InstalledAppInfo.CREATOR.createFromParcel(parcel2);
        } else {
          param1String = null;
        } 
        return (InstalledAppInfo)param1String;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<InstalledAppInfo> getInstalledApps(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(11, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null)
          return IAppManager.Stub.getDefaultImpl().getInstalledApps(param1Int); 
        parcel2.readException();
        return parcel2.createTypedArrayList(InstalledAppInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<InstalledAppInfo> getInstalledAppsAsUser(int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(12, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null)
          return IAppManager.Stub.getDefaultImpl().getInstalledAppsAsUser(param1Int1, param1Int2); 
        parcel2.readException();
        return parcel2.createTypedArrayList(InstalledAppInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<String> getInstalledSplitNames(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(13, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null)
          return IAppManager.Stub.getDefaultImpl().getInstalledSplitNames(param1String); 
        parcel2.readException();
        return parcel2.createStringArrayList();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.interfaces.IAppManager";
    }
    
    public int[] getPackageInstalledUsers(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null)
          return IAppManager.Stub.getDefaultImpl().getPackageInstalledUsers(param1String); 
        parcel2.readException();
        return parcel2.createIntArray();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int getUidForSharedUser(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null)
          return IAppManager.Stub.getDefaultImpl().getUidForSharedUser(param1String); 
        parcel2.readException();
        return parcel2.readInt();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public VAppInstallerResult installPackage(Uri param1Uri, VAppInstallerParams param1VAppInstallerParams) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        if (param1Uri != null) {
          parcel1.writeInt(1);
          param1Uri.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (param1VAppInstallerParams != null) {
          parcel1.writeInt(1);
          param1VAppInstallerParams.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null)
          return IAppManager.Stub.getDefaultImpl().installPackage(param1Uri, param1VAppInstallerParams); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          VAppInstallerResult vAppInstallerResult = (VAppInstallerResult)VAppInstallerResult.CREATOR.createFromParcel(parcel2);
        } else {
          param1Uri = null;
        } 
        return (VAppInstallerResult)param1Uri;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean installPackageAsUser(int param1Int, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(8, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
          bool = IAppManager.Stub.getDefaultImpl().installPackageAsUser(param1Int, param1String);
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
    
    public boolean isAppInstalled(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        parcel1.writeString(param1String);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(15, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
          bool = IAppManager.Stub.getDefaultImpl().isAppInstalled(param1String);
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
    
    public boolean isAppInstalledAsUser(int param1Int, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(16, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
          bool = IAppManager.Stub.getDefaultImpl().isAppInstalledAsUser(param1Int, param1String);
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
    
    public boolean isPackageLaunched(int param1Int, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(6, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
          bool = IAppManager.Stub.getDefaultImpl().isPackageLaunched(param1Int, param1String);
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
    
    public boolean isRunInExtProcess(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        parcel1.writeString(param1String);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(19, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
          bool = IAppManager.Stub.getDefaultImpl().isRunInExtProcess(param1String);
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
    
    public void registerObserver(IPackageObserver param1IPackageObserver) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        if (param1IPackageObserver != null) {
          iBinder = param1IPackageObserver.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (!this.mRemote.transact(17, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
          IAppManager.Stub.getDefaultImpl().registerObserver(param1IPackageObserver);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void scanApps() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
          IAppManager.Stub.getDefaultImpl().scanApps();
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setPackageHidden(int param1Int, String param1String, boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
          IAppManager.Stub.getDefaultImpl().setPackageHidden(param1Int, param1String, param1Boolean);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean uninstallPackage(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        parcel1.writeString(param1String);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(10, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
          bool = IAppManager.Stub.getDefaultImpl().uninstallPackage(param1String);
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
    
    public boolean uninstallPackageAsUser(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(9, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
          bool = IAppManager.Stub.getDefaultImpl().uninstallPackageAsUser(param1String, param1Int);
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
    
    public void unregisterObserver(IPackageObserver param1IPackageObserver) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAppManager");
        if (param1IPackageObserver != null) {
          iBinder = param1IPackageObserver.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (!this.mRemote.transact(18, parcel1, parcel2, 0) && IAppManager.Stub.getDefaultImpl() != null) {
          IAppManager.Stub.getDefaultImpl().unregisterObserver(param1IPackageObserver);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\interfaces\IAppManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */