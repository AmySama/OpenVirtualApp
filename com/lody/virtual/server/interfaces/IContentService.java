package com.lody.virtual.server.interfaces;

import android.accounts.Account;
import android.content.ISyncStatusObserver;
import android.content.PeriodicSync;
import android.content.SyncAdapterType;
import android.content.SyncInfo;
import android.content.SyncRequest;
import android.content.SyncStatusInfo;
import android.database.IContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.List;

public interface IContentService extends IInterface {
  void addPeriodicSync(Account paramAccount, String paramString, Bundle paramBundle, long paramLong) throws RemoteException;
  
  void addStatusChangeListener(int paramInt, ISyncStatusObserver paramISyncStatusObserver) throws RemoteException;
  
  void cancelSync(Account paramAccount, String paramString) throws RemoteException;
  
  List<SyncInfo> getCurrentSyncs() throws RemoteException;
  
  int getIsSyncable(Account paramAccount, String paramString) throws RemoteException;
  
  boolean getMasterSyncAutomatically() throws RemoteException;
  
  List<PeriodicSync> getPeriodicSyncs(Account paramAccount, String paramString) throws RemoteException;
  
  SyncAdapterType[] getSyncAdapterTypes() throws RemoteException;
  
  boolean getSyncAutomatically(Account paramAccount, String paramString) throws RemoteException;
  
  SyncStatusInfo getSyncStatus(Account paramAccount, String paramString) throws RemoteException;
  
  boolean isSyncActive(Account paramAccount, String paramString) throws RemoteException;
  
  boolean isSyncPending(Account paramAccount, String paramString) throws RemoteException;
  
  void notifyChange(Uri paramUri, IContentObserver paramIContentObserver, boolean paramBoolean1, boolean paramBoolean2, int paramInt) throws RemoteException;
  
  void registerContentObserver(Uri paramUri, boolean paramBoolean, IContentObserver paramIContentObserver, int paramInt) throws RemoteException;
  
  void removePeriodicSync(Account paramAccount, String paramString, Bundle paramBundle) throws RemoteException;
  
  void removeStatusChangeListener(ISyncStatusObserver paramISyncStatusObserver) throws RemoteException;
  
  void requestSync(Account paramAccount, String paramString, Bundle paramBundle) throws RemoteException;
  
  void setIsSyncable(Account paramAccount, String paramString, int paramInt) throws RemoteException;
  
  void setMasterSyncAutomatically(boolean paramBoolean) throws RemoteException;
  
  void setSyncAutomatically(Account paramAccount, String paramString, boolean paramBoolean) throws RemoteException;
  
  void sync(SyncRequest paramSyncRequest) throws RemoteException;
  
  void unregisterContentObserver(IContentObserver paramIContentObserver) throws RemoteException;
  
  public static class Default implements IContentService {
    public void addPeriodicSync(Account param1Account, String param1String, Bundle param1Bundle, long param1Long) throws RemoteException {}
    
    public void addStatusChangeListener(int param1Int, ISyncStatusObserver param1ISyncStatusObserver) throws RemoteException {}
    
    public IBinder asBinder() {
      return null;
    }
    
    public void cancelSync(Account param1Account, String param1String) throws RemoteException {}
    
    public List<SyncInfo> getCurrentSyncs() throws RemoteException {
      return null;
    }
    
    public int getIsSyncable(Account param1Account, String param1String) throws RemoteException {
      return 0;
    }
    
    public boolean getMasterSyncAutomatically() throws RemoteException {
      return false;
    }
    
    public List<PeriodicSync> getPeriodicSyncs(Account param1Account, String param1String) throws RemoteException {
      return null;
    }
    
    public SyncAdapterType[] getSyncAdapterTypes() throws RemoteException {
      return null;
    }
    
    public boolean getSyncAutomatically(Account param1Account, String param1String) throws RemoteException {
      return false;
    }
    
    public SyncStatusInfo getSyncStatus(Account param1Account, String param1String) throws RemoteException {
      return null;
    }
    
    public boolean isSyncActive(Account param1Account, String param1String) throws RemoteException {
      return false;
    }
    
    public boolean isSyncPending(Account param1Account, String param1String) throws RemoteException {
      return false;
    }
    
    public void notifyChange(Uri param1Uri, IContentObserver param1IContentObserver, boolean param1Boolean1, boolean param1Boolean2, int param1Int) throws RemoteException {}
    
    public void registerContentObserver(Uri param1Uri, boolean param1Boolean, IContentObserver param1IContentObserver, int param1Int) throws RemoteException {}
    
    public void removePeriodicSync(Account param1Account, String param1String, Bundle param1Bundle) throws RemoteException {}
    
    public void removeStatusChangeListener(ISyncStatusObserver param1ISyncStatusObserver) throws RemoteException {}
    
    public void requestSync(Account param1Account, String param1String, Bundle param1Bundle) throws RemoteException {}
    
    public void setIsSyncable(Account param1Account, String param1String, int param1Int) throws RemoteException {}
    
    public void setMasterSyncAutomatically(boolean param1Boolean) throws RemoteException {}
    
    public void setSyncAutomatically(Account param1Account, String param1String, boolean param1Boolean) throws RemoteException {}
    
    public void sync(SyncRequest param1SyncRequest) throws RemoteException {}
    
    public void unregisterContentObserver(IContentObserver param1IContentObserver) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IContentService {
    private static final String DESCRIPTOR = "com.lody.virtual.server.interfaces.IContentService";
    
    static final int TRANSACTION_addPeriodicSync = 10;
    
    static final int TRANSACTION_addStatusChangeListener = 21;
    
    static final int TRANSACTION_cancelSync = 6;
    
    static final int TRANSACTION_getCurrentSyncs = 17;
    
    static final int TRANSACTION_getIsSyncable = 12;
    
    static final int TRANSACTION_getMasterSyncAutomatically = 15;
    
    static final int TRANSACTION_getPeriodicSyncs = 9;
    
    static final int TRANSACTION_getSyncAdapterTypes = 18;
    
    static final int TRANSACTION_getSyncAutomatically = 7;
    
    static final int TRANSACTION_getSyncStatus = 19;
    
    static final int TRANSACTION_isSyncActive = 16;
    
    static final int TRANSACTION_isSyncPending = 20;
    
    static final int TRANSACTION_notifyChange = 3;
    
    static final int TRANSACTION_registerContentObserver = 2;
    
    static final int TRANSACTION_removePeriodicSync = 11;
    
    static final int TRANSACTION_removeStatusChangeListener = 22;
    
    static final int TRANSACTION_requestSync = 4;
    
    static final int TRANSACTION_setIsSyncable = 13;
    
    static final int TRANSACTION_setMasterSyncAutomatically = 14;
    
    static final int TRANSACTION_setSyncAutomatically = 8;
    
    static final int TRANSACTION_sync = 5;
    
    static final int TRANSACTION_unregisterContentObserver = 1;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.interfaces.IContentService");
    }
    
    public static IContentService asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.interfaces.IContentService");
      return (iInterface != null && iInterface instanceof IContentService) ? (IContentService)iInterface : new Proxy(param1IBinder);
    }
    
    public static IContentService getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IContentService param1IContentService) {
      if (Proxy.sDefaultImpl == null && param1IContentService != null) {
        Proxy.sDefaultImpl = param1IContentService;
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
        SyncStatusInfo syncStatusInfo;
        SyncAdapterType[] arrayOfSyncAdapterType;
        List<SyncInfo> list;
        String str2;
        Bundle bundle2;
        String str1;
        Bundle bundle1;
        IContentObserver iContentObserver;
        SyncRequest syncRequest2;
        Uri uri2;
        boolean bool3 = false;
        boolean bool4 = false;
        boolean bool5 = false;
        Account account1 = null;
        Account account2 = null;
        Account account3 = null;
        Account account4 = null;
        Bundle bundle3 = null;
        String str3 = null;
        Account account5 = null;
        Account account6 = null;
        Account account7 = null;
        Account account8 = null;
        Account account9 = null;
        String str4 = null;
        SyncRequest syncRequest1 = null;
        Uri uri1 = null;
        Account account10 = null;
        switch (param1Int1) {
          default:
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2);
          case 22:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            removeStatusChangeListener(ISyncStatusObserver.Stub.asInterface(param1Parcel1.readStrongBinder()));
            param1Parcel2.writeNoException();
            return true;
          case 21:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            addStatusChangeListener(param1Parcel1.readInt(), ISyncStatusObserver.Stub.asInterface(param1Parcel1.readStrongBinder()));
            param1Parcel2.writeNoException();
            return true;
          case 20:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            if (param1Parcel1.readInt() != 0)
              account10 = (Account)Account.CREATOR.createFromParcel(param1Parcel1); 
            bool2 = isSyncPending(account10, param1Parcel1.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool2);
            return true;
          case 19:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            account10 = account1;
            if (param1Parcel1.readInt() != 0)
              account10 = (Account)Account.CREATOR.createFromParcel(param1Parcel1); 
            syncStatusInfo = getSyncStatus(account10, param1Parcel1.readString());
            param1Parcel2.writeNoException();
            if (syncStatusInfo != null) {
              param1Parcel2.writeInt(1);
              syncStatusInfo.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 18:
            syncStatusInfo.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            arrayOfSyncAdapterType = getSyncAdapterTypes();
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedArray((Parcelable[])arrayOfSyncAdapterType, 1);
            return true;
          case 17:
            arrayOfSyncAdapterType.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            list = getCurrentSyncs();
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list);
            return true;
          case 16:
            list.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            account10 = account2;
            if (list.readInt() != 0)
              account10 = (Account)Account.CREATOR.createFromParcel((Parcel)list); 
            bool2 = isSyncActive(account10, list.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool2);
            return true;
          case 15:
            list.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            bool2 = getMasterSyncAutomatically();
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool2);
            return true;
          case 14:
            list.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            if (list.readInt() != 0)
              bool5 = true; 
            setMasterSyncAutomatically(bool5);
            param1Parcel2.writeNoException();
            return true;
          case 13:
            list.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            account10 = account3;
            if (list.readInt() != 0)
              account10 = (Account)Account.CREATOR.createFromParcel((Parcel)list); 
            setIsSyncable(account10, list.readString(), list.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 12:
            list.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            account10 = account4;
            if (list.readInt() != 0)
              account10 = (Account)Account.CREATOR.createFromParcel((Parcel)list); 
            i = getIsSyncable(account10, list.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(i);
            return true;
          case 11:
            list.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            if (list.readInt() != 0) {
              account10 = (Account)Account.CREATOR.createFromParcel((Parcel)list);
            } else {
              account10 = null;
            } 
            str3 = list.readString();
            if (list.readInt() != 0)
              bundle3 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)list); 
            removePeriodicSync(account10, str3, bundle3);
            param1Parcel2.writeNoException();
            return true;
          case 10:
            list.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            if (list.readInt() != 0) {
              account10 = (Account)Account.CREATOR.createFromParcel((Parcel)list);
            } else {
              account10 = null;
            } 
            str4 = list.readString();
            str2 = str3;
            if (list.readInt() != 0)
              bundle2 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)list); 
            addPeriodicSync(account10, str4, bundle2, list.readLong());
            param1Parcel2.writeNoException();
            return true;
          case 9:
            list.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            account10 = account5;
            if (list.readInt() != 0)
              account10 = (Account)Account.CREATOR.createFromParcel((Parcel)list); 
            list = (List)getPeriodicSyncs(account10, list.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list);
            return true;
          case 8:
            list.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            account10 = account6;
            if (list.readInt() != 0)
              account10 = (Account)Account.CREATOR.createFromParcel((Parcel)list); 
            str1 = list.readString();
            bool5 = bool3;
            if (list.readInt() != 0)
              bool5 = true; 
            setSyncAutomatically(account10, str1, bool5);
            param1Parcel2.writeNoException();
            return true;
          case 7:
            list.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            account10 = account7;
            if (list.readInt() != 0)
              account10 = (Account)Account.CREATOR.createFromParcel((Parcel)list); 
            bool1 = getSyncAutomatically(account10, list.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool1);
            return true;
          case 6:
            list.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            account10 = account8;
            if (list.readInt() != 0)
              account10 = (Account)Account.CREATOR.createFromParcel((Parcel)list); 
            cancelSync(account10, list.readString());
            param1Parcel2.writeNoException();
            return true;
          case 5:
            list.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            account10 = account9;
            if (list.readInt() != 0)
              syncRequest2 = (SyncRequest)SyncRequest.CREATOR.createFromParcel((Parcel)list); 
            sync(syncRequest2);
            param1Parcel2.writeNoException();
            return true;
          case 4:
            list.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            if (list.readInt() != 0) {
              Account account = (Account)Account.CREATOR.createFromParcel((Parcel)list);
            } else {
              syncRequest2 = null;
            } 
            str3 = list.readString();
            str1 = str4;
            if (list.readInt() != 0)
              bundle1 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)list); 
            requestSync((Account)syncRequest2, str3, bundle1);
            param1Parcel2.writeNoException();
            return true;
          case 3:
            list.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            syncRequest2 = syncRequest1;
            if (list.readInt() != 0)
              uri2 = (Uri)Uri.CREATOR.createFromParcel((Parcel)list); 
            iContentObserver = IContentObserver.Stub.asInterface(list.readStrongBinder());
            if (list.readInt() != 0) {
              bool5 = true;
            } else {
              bool5 = false;
            } 
            if (list.readInt() != 0) {
              bool3 = true;
            } else {
              bool3 = false;
            } 
            notifyChange(uri2, iContentObserver, bool5, bool3, list.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 2:
            list.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
            uri2 = uri1;
            if (list.readInt() != 0)
              uri2 = (Uri)Uri.CREATOR.createFromParcel((Parcel)list); 
            bool5 = bool4;
            if (list.readInt() != 0)
              bool5 = true; 
            registerContentObserver(uri2, bool5, IContentObserver.Stub.asInterface(list.readStrongBinder()), list.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 1:
            break;
        } 
        list.enforceInterface("com.lody.virtual.server.interfaces.IContentService");
        unregisterContentObserver(IContentObserver.Stub.asInterface(list.readStrongBinder()));
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel2.writeString("com.lody.virtual.server.interfaces.IContentService");
      return true;
    }
    
    private static class Proxy implements IContentService {
      public static IContentService sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public void addPeriodicSync(Account param2Account, String param2String, Bundle param2Bundle, long param2Long) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeLong(param2Long);
          if (!this.mRemote.transact(10, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
            IContentService.Stub.getDefaultImpl().addPeriodicSync(param2Account, param2String, param2Bundle, param2Long);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void addStatusChangeListener(int param2Int, ISyncStatusObserver param2ISyncStatusObserver) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          parcel1.writeInt(param2Int);
          if (param2ISyncStatusObserver != null) {
            iBinder = param2ISyncStatusObserver.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (!this.mRemote.transact(21, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
            IContentService.Stub.getDefaultImpl().addStatusChangeListener(param2Int, param2ISyncStatusObserver);
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
      
      public void cancelSync(Account param2Account, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
            IContentService.Stub.getDefaultImpl().cancelSync(param2Account, param2String);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<SyncInfo> getCurrentSyncs() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          if (!this.mRemote.transact(17, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null)
            return IContentService.Stub.getDefaultImpl().getCurrentSyncs(); 
          parcel2.readException();
          return parcel2.createTypedArrayList(SyncInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.interfaces.IContentService";
      }
      
      public int getIsSyncable(Account param2Account, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(12, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null)
            return IContentService.Stub.getDefaultImpl().getIsSyncable(param2Account, param2String); 
          parcel2.readException();
          return parcel2.readInt();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean getMasterSyncAutomatically() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(15, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
            bool = IContentService.Stub.getDefaultImpl().getMasterSyncAutomatically();
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
      
      public List<PeriodicSync> getPeriodicSyncs(Account param2Account, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(9, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null)
            return IContentService.Stub.getDefaultImpl().getPeriodicSyncs(param2Account, param2String); 
          parcel2.readException();
          return parcel2.createTypedArrayList(PeriodicSync.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public SyncAdapterType[] getSyncAdapterTypes() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          if (!this.mRemote.transact(18, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null)
            return IContentService.Stub.getDefaultImpl().getSyncAdapterTypes(); 
          parcel2.readException();
          return (SyncAdapterType[])parcel2.createTypedArray(SyncAdapterType.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean getSyncAutomatically(Account param2Account, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          boolean bool = true;
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
            bool = IContentService.Stub.getDefaultImpl().getSyncAutomatically(param2Account, param2String);
            return bool;
          } 
          parcel2.readException();
          int i = parcel2.readInt();
          if (i == 0)
            bool = false; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public SyncStatusInfo getSyncStatus(Account param2Account, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(19, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null)
            return IContentService.Stub.getDefaultImpl().getSyncStatus(param2Account, param2String); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            SyncStatusInfo syncStatusInfo = (SyncStatusInfo)SyncStatusInfo.CREATOR.createFromParcel(parcel2);
          } else {
            param2Account = null;
          } 
          return (SyncStatusInfo)param2Account;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean isSyncActive(Account param2Account, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          boolean bool = true;
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(16, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
            bool = IContentService.Stub.getDefaultImpl().isSyncActive(param2Account, param2String);
            return bool;
          } 
          parcel2.readException();
          int i = parcel2.readInt();
          if (i == 0)
            bool = false; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean isSyncPending(Account param2Account, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          boolean bool = true;
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(20, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
            bool = IContentService.Stub.getDefaultImpl().isSyncPending(param2Account, param2String);
            return bool;
          } 
          parcel2.readException();
          int i = parcel2.readInt();
          if (i == 0)
            bool = false; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void notifyChange(Uri param2Uri, IContentObserver param2IContentObserver, boolean param2Boolean1, boolean param2Boolean2, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          boolean bool2;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          boolean bool1 = true;
          if (param2Uri != null) {
            parcel1.writeInt(1);
            param2Uri.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (param2IContentObserver != null) {
            iBinder = param2IContentObserver.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (param2Boolean1) {
            bool2 = true;
          } else {
            bool2 = false;
          } 
          parcel1.writeInt(bool2);
          if (param2Boolean2) {
            bool2 = bool1;
          } else {
            bool2 = false;
          } 
          parcel1.writeInt(bool2);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
            IContentService.Stub.getDefaultImpl().notifyChange(param2Uri, param2IContentObserver, param2Boolean1, param2Boolean2, param2Int);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void registerContentObserver(Uri param2Uri, boolean param2Boolean, IContentObserver param2IContentObserver, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          boolean bool = true;
          if (param2Uri != null) {
            parcel1.writeInt(1);
            param2Uri.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!param2Boolean)
            bool = false; 
          parcel1.writeInt(bool);
          if (param2IContentObserver != null) {
            iBinder = param2IContentObserver.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
            IContentService.Stub.getDefaultImpl().registerContentObserver(param2Uri, param2Boolean, param2IContentObserver, param2Int);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void removePeriodicSync(Account param2Account, String param2String, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(11, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
            IContentService.Stub.getDefaultImpl().removePeriodicSync(param2Account, param2String, param2Bundle);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void removeStatusChangeListener(ISyncStatusObserver param2ISyncStatusObserver) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          if (param2ISyncStatusObserver != null) {
            iBinder = param2ISyncStatusObserver.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (!this.mRemote.transact(22, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
            IContentService.Stub.getDefaultImpl().removeStatusChangeListener(param2ISyncStatusObserver);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void requestSync(Account param2Account, String param2String, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
            IContentService.Stub.getDefaultImpl().requestSync(param2Account, param2String, param2Bundle);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setIsSyncable(Account param2Account, String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(13, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
            IContentService.Stub.getDefaultImpl().setIsSyncable(param2Account, param2String, param2Int);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setMasterSyncAutomatically(boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(14, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
            IContentService.Stub.getDefaultImpl().setMasterSyncAutomatically(param2Boolean);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setSyncAutomatically(Account param2Account, String param2String, boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          boolean bool = true;
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (!param2Boolean)
            bool = false; 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
            IContentService.Stub.getDefaultImpl().setSyncAutomatically(param2Account, param2String, param2Boolean);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void sync(SyncRequest param2SyncRequest) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          if (param2SyncRequest != null) {
            parcel1.writeInt(1);
            param2SyncRequest.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
            IContentService.Stub.getDefaultImpl().sync(param2SyncRequest);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void unregisterContentObserver(IContentObserver param2IContentObserver) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
          if (param2IContentObserver != null) {
            iBinder = param2IContentObserver.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
            IContentService.Stub.getDefaultImpl().unregisterContentObserver(param2IContentObserver);
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
  
  private static class Proxy implements IContentService {
    public static IContentService sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public void addPeriodicSync(Account param1Account, String param1String, Bundle param1Bundle, long param1Long) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeLong(param1Long);
        if (!this.mRemote.transact(10, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
          IContentService.Stub.getDefaultImpl().addPeriodicSync(param1Account, param1String, param1Bundle, param1Long);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void addStatusChangeListener(int param1Int, ISyncStatusObserver param1ISyncStatusObserver) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        parcel1.writeInt(param1Int);
        if (param1ISyncStatusObserver != null) {
          iBinder = param1ISyncStatusObserver.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (!this.mRemote.transact(21, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
          IContentService.Stub.getDefaultImpl().addStatusChangeListener(param1Int, param1ISyncStatusObserver);
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
    
    public void cancelSync(Account param1Account, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
          IContentService.Stub.getDefaultImpl().cancelSync(param1Account, param1String);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<SyncInfo> getCurrentSyncs() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        if (!this.mRemote.transact(17, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null)
          return IContentService.Stub.getDefaultImpl().getCurrentSyncs(); 
        parcel2.readException();
        return parcel2.createTypedArrayList(SyncInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.interfaces.IContentService";
    }
    
    public int getIsSyncable(Account param1Account, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(12, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null)
          return IContentService.Stub.getDefaultImpl().getIsSyncable(param1Account, param1String); 
        parcel2.readException();
        return parcel2.readInt();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean getMasterSyncAutomatically() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(15, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
          bool = IContentService.Stub.getDefaultImpl().getMasterSyncAutomatically();
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
    
    public List<PeriodicSync> getPeriodicSyncs(Account param1Account, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(9, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null)
          return IContentService.Stub.getDefaultImpl().getPeriodicSyncs(param1Account, param1String); 
        parcel2.readException();
        return parcel2.createTypedArrayList(PeriodicSync.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public SyncAdapterType[] getSyncAdapterTypes() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        if (!this.mRemote.transact(18, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null)
          return IContentService.Stub.getDefaultImpl().getSyncAdapterTypes(); 
        parcel2.readException();
        return (SyncAdapterType[])parcel2.createTypedArray(SyncAdapterType.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean getSyncAutomatically(Account param1Account, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        boolean bool = true;
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
          bool = IContentService.Stub.getDefaultImpl().getSyncAutomatically(param1Account, param1String);
          return bool;
        } 
        parcel2.readException();
        int i = parcel2.readInt();
        if (i == 0)
          bool = false; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public SyncStatusInfo getSyncStatus(Account param1Account, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(19, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null)
          return IContentService.Stub.getDefaultImpl().getSyncStatus(param1Account, param1String); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          SyncStatusInfo syncStatusInfo = (SyncStatusInfo)SyncStatusInfo.CREATOR.createFromParcel(parcel2);
        } else {
          param1Account = null;
        } 
        return (SyncStatusInfo)param1Account;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean isSyncActive(Account param1Account, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        boolean bool = true;
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(16, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
          bool = IContentService.Stub.getDefaultImpl().isSyncActive(param1Account, param1String);
          return bool;
        } 
        parcel2.readException();
        int i = parcel2.readInt();
        if (i == 0)
          bool = false; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean isSyncPending(Account param1Account, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        boolean bool = true;
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(20, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
          bool = IContentService.Stub.getDefaultImpl().isSyncPending(param1Account, param1String);
          return bool;
        } 
        parcel2.readException();
        int i = parcel2.readInt();
        if (i == 0)
          bool = false; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void notifyChange(Uri param1Uri, IContentObserver param1IContentObserver, boolean param1Boolean1, boolean param1Boolean2, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        boolean bool2;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        boolean bool1 = true;
        if (param1Uri != null) {
          parcel1.writeInt(1);
          param1Uri.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (param1IContentObserver != null) {
          iBinder = param1IContentObserver.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (param1Boolean1) {
          bool2 = true;
        } else {
          bool2 = false;
        } 
        parcel1.writeInt(bool2);
        if (param1Boolean2) {
          bool2 = bool1;
        } else {
          bool2 = false;
        } 
        parcel1.writeInt(bool2);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
          IContentService.Stub.getDefaultImpl().notifyChange(param1Uri, param1IContentObserver, param1Boolean1, param1Boolean2, param1Int);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void registerContentObserver(Uri param1Uri, boolean param1Boolean, IContentObserver param1IContentObserver, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        boolean bool = true;
        if (param1Uri != null) {
          parcel1.writeInt(1);
          param1Uri.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!param1Boolean)
          bool = false; 
        parcel1.writeInt(bool);
        if (param1IContentObserver != null) {
          iBinder = param1IContentObserver.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
          IContentService.Stub.getDefaultImpl().registerContentObserver(param1Uri, param1Boolean, param1IContentObserver, param1Int);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void removePeriodicSync(Account param1Account, String param1String, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(11, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
          IContentService.Stub.getDefaultImpl().removePeriodicSync(param1Account, param1String, param1Bundle);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void removeStatusChangeListener(ISyncStatusObserver param1ISyncStatusObserver) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        if (param1ISyncStatusObserver != null) {
          iBinder = param1ISyncStatusObserver.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (!this.mRemote.transact(22, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
          IContentService.Stub.getDefaultImpl().removeStatusChangeListener(param1ISyncStatusObserver);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void requestSync(Account param1Account, String param1String, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
          IContentService.Stub.getDefaultImpl().requestSync(param1Account, param1String, param1Bundle);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setIsSyncable(Account param1Account, String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(13, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
          IContentService.Stub.getDefaultImpl().setIsSyncable(param1Account, param1String, param1Int);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setMasterSyncAutomatically(boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(14, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
          IContentService.Stub.getDefaultImpl().setMasterSyncAutomatically(param1Boolean);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setSyncAutomatically(Account param1Account, String param1String, boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        boolean bool = true;
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (!param1Boolean)
          bool = false; 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
          IContentService.Stub.getDefaultImpl().setSyncAutomatically(param1Account, param1String, param1Boolean);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void sync(SyncRequest param1SyncRequest) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        if (param1SyncRequest != null) {
          parcel1.writeInt(1);
          param1SyncRequest.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
          IContentService.Stub.getDefaultImpl().sync(param1SyncRequest);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void unregisterContentObserver(IContentObserver param1IContentObserver) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IContentService");
        if (param1IContentObserver != null) {
          iBinder = param1IContentObserver.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IContentService.Stub.getDefaultImpl() != null) {
          IContentService.Stub.getDefaultImpl().unregisterContentObserver(param1IContentObserver);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\interfaces\IContentService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */