package com.lody.virtual;

import android.app.ActivityManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface IExtHelperInterface extends IInterface {
  void cleanPackageData(int[] paramArrayOfint, String paramString) throws RemoteException;
  
  void forceStop(int[] paramArrayOfint) throws RemoteException;
  
  List<ActivityManager.RecentTaskInfo> getRecentTasks(int paramInt1, int paramInt2) throws RemoteException;
  
  List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws RemoteException;
  
  List<ActivityManager.RunningTaskInfo> getRunningTasks(int paramInt) throws RemoteException;
  
  boolean isExternalStorageManager() throws RemoteException;
  
  int syncPackages() throws RemoteException;
  
  public static class Default implements IExtHelperInterface {
    public IBinder asBinder() {
      return null;
    }
    
    public void cleanPackageData(int[] param1ArrayOfint, String param1String) throws RemoteException {}
    
    public void forceStop(int[] param1ArrayOfint) throws RemoteException {}
    
    public List<ActivityManager.RecentTaskInfo> getRecentTasks(int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws RemoteException {
      return null;
    }
    
    public List<ActivityManager.RunningTaskInfo> getRunningTasks(int param1Int) throws RemoteException {
      return null;
    }
    
    public boolean isExternalStorageManager() throws RemoteException {
      return false;
    }
    
    public int syncPackages() throws RemoteException {
      return 0;
    }
  }
  
  public static abstract class Stub extends Binder implements IExtHelperInterface {
    private static final String DESCRIPTOR = "com.lody.virtual.IExtHelperInterface";
    
    static final int TRANSACTION_cleanPackageData = 2;
    
    static final int TRANSACTION_forceStop = 3;
    
    static final int TRANSACTION_getRecentTasks = 5;
    
    static final int TRANSACTION_getRunningAppProcesses = 6;
    
    static final int TRANSACTION_getRunningTasks = 4;
    
    static final int TRANSACTION_isExternalStorageManager = 7;
    
    static final int TRANSACTION_syncPackages = 1;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.IExtHelperInterface");
    }
    
    public static IExtHelperInterface asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.IExtHelperInterface");
      return (iInterface != null && iInterface instanceof IExtHelperInterface) ? (IExtHelperInterface)iInterface : new Proxy(param1IBinder);
    }
    
    public static IExtHelperInterface getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IExtHelperInterface param1IExtHelperInterface) {
      if (Proxy.sDefaultImpl == null && param1IExtHelperInterface != null) {
        Proxy.sDefaultImpl = param1IExtHelperInterface;
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
        List<ActivityManager.RunningAppProcessInfo> list;
        switch (param1Int1) {
          default:
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2);
          case 7:
            param1Parcel1.enforceInterface("com.lody.virtual.IExtHelperInterface");
            bool = isExternalStorageManager();
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool);
            return true;
          case 6:
            param1Parcel1.enforceInterface("com.lody.virtual.IExtHelperInterface");
            list = getRunningAppProcesses();
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list);
            return true;
          case 5:
            list.enforceInterface("com.lody.virtual.IExtHelperInterface");
            list = (List)getRecentTasks(list.readInt(), list.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list);
            return true;
          case 4:
            list.enforceInterface("com.lody.virtual.IExtHelperInterface");
            list = (List)getRunningTasks(list.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list);
            return true;
          case 3:
            list.enforceInterface("com.lody.virtual.IExtHelperInterface");
            forceStop(list.createIntArray());
            param1Parcel2.writeNoException();
            return true;
          case 2:
            list.enforceInterface("com.lody.virtual.IExtHelperInterface");
            cleanPackageData(list.createIntArray(), list.readString());
            param1Parcel2.writeNoException();
            return true;
          case 1:
            break;
        } 
        list.enforceInterface("com.lody.virtual.IExtHelperInterface");
        int i = syncPackages();
        param1Parcel2.writeNoException();
        param1Parcel2.writeInt(i);
        return true;
      } 
      param1Parcel2.writeString("com.lody.virtual.IExtHelperInterface");
      return true;
    }
    
    private static class Proxy implements IExtHelperInterface {
      public static IExtHelperInterface sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public void cleanPackageData(int[] param2ArrayOfint, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.IExtHelperInterface");
          parcel1.writeIntArray(param2ArrayOfint);
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IExtHelperInterface.Stub.getDefaultImpl() != null) {
            IExtHelperInterface.Stub.getDefaultImpl().cleanPackageData(param2ArrayOfint, param2String);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void forceStop(int[] param2ArrayOfint) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.IExtHelperInterface");
          parcel1.writeIntArray(param2ArrayOfint);
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IExtHelperInterface.Stub.getDefaultImpl() != null) {
            IExtHelperInterface.Stub.getDefaultImpl().forceStop(param2ArrayOfint);
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
        return "com.lody.virtual.IExtHelperInterface";
      }
      
      public List<ActivityManager.RecentTaskInfo> getRecentTasks(int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.IExtHelperInterface");
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IExtHelperInterface.Stub.getDefaultImpl() != null)
            return IExtHelperInterface.Stub.getDefaultImpl().getRecentTasks(param2Int1, param2Int2); 
          parcel2.readException();
          return parcel2.createTypedArrayList(ActivityManager.RecentTaskInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.IExtHelperInterface");
          if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IExtHelperInterface.Stub.getDefaultImpl() != null)
            return IExtHelperInterface.Stub.getDefaultImpl().getRunningAppProcesses(); 
          parcel2.readException();
          return parcel2.createTypedArrayList(ActivityManager.RunningAppProcessInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<ActivityManager.RunningTaskInfo> getRunningTasks(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.IExtHelperInterface");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IExtHelperInterface.Stub.getDefaultImpl() != null)
            return IExtHelperInterface.Stub.getDefaultImpl().getRunningTasks(param2Int); 
          parcel2.readException();
          return parcel2.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean isExternalStorageManager() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.IExtHelperInterface");
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(7, parcel1, parcel2, 0) && IExtHelperInterface.Stub.getDefaultImpl() != null) {
            bool = IExtHelperInterface.Stub.getDefaultImpl().isExternalStorageManager();
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
      
      public int syncPackages() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.IExtHelperInterface");
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IExtHelperInterface.Stub.getDefaultImpl() != null)
            return IExtHelperInterface.Stub.getDefaultImpl().syncPackages(); 
          parcel2.readException();
          return parcel2.readInt();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
    }
  }
  
  private static class Proxy implements IExtHelperInterface {
    public static IExtHelperInterface sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public void cleanPackageData(int[] param1ArrayOfint, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.IExtHelperInterface");
        parcel1.writeIntArray(param1ArrayOfint);
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IExtHelperInterface.Stub.getDefaultImpl() != null) {
          IExtHelperInterface.Stub.getDefaultImpl().cleanPackageData(param1ArrayOfint, param1String);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void forceStop(int[] param1ArrayOfint) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.IExtHelperInterface");
        parcel1.writeIntArray(param1ArrayOfint);
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IExtHelperInterface.Stub.getDefaultImpl() != null) {
          IExtHelperInterface.Stub.getDefaultImpl().forceStop(param1ArrayOfint);
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
      return "com.lody.virtual.IExtHelperInterface";
    }
    
    public List<ActivityManager.RecentTaskInfo> getRecentTasks(int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.IExtHelperInterface");
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IExtHelperInterface.Stub.getDefaultImpl() != null)
          return IExtHelperInterface.Stub.getDefaultImpl().getRecentTasks(param1Int1, param1Int2); 
        parcel2.readException();
        return parcel2.createTypedArrayList(ActivityManager.RecentTaskInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.IExtHelperInterface");
        if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IExtHelperInterface.Stub.getDefaultImpl() != null)
          return IExtHelperInterface.Stub.getDefaultImpl().getRunningAppProcesses(); 
        parcel2.readException();
        return parcel2.createTypedArrayList(ActivityManager.RunningAppProcessInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<ActivityManager.RunningTaskInfo> getRunningTasks(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.IExtHelperInterface");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IExtHelperInterface.Stub.getDefaultImpl() != null)
          return IExtHelperInterface.Stub.getDefaultImpl().getRunningTasks(param1Int); 
        parcel2.readException();
        return parcel2.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean isExternalStorageManager() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.IExtHelperInterface");
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(7, parcel1, parcel2, 0) && IExtHelperInterface.Stub.getDefaultImpl() != null) {
          bool = IExtHelperInterface.Stub.getDefaultImpl().isExternalStorageManager();
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
    
    public int syncPackages() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.IExtHelperInterface");
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IExtHelperInterface.Stub.getDefaultImpl() != null)
          return IExtHelperInterface.Stub.getDefaultImpl().syncPackages(); 
        parcel2.readException();
        return parcel2.readInt();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\IExtHelperInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */