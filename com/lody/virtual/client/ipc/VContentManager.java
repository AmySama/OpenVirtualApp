package com.lody.virtual.client.ipc;

import android.accounts.Account;
import android.content.ISyncStatusObserver;
import android.content.PeriodicSync;
import android.content.SyncAdapterType;
import android.content.SyncInfo;
import android.content.SyncRequest;
import android.content.SyncStatusInfo;
import android.database.IContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import com.lody.virtual.server.interfaces.IContentService;
import java.util.List;

public class VContentManager {
  private static final VContentManager sInstance = new VContentManager();
  
  private IContentService mService;
  
  public static VContentManager get() {
    return sInstance;
  }
  
  private Object getRemoteInterface() {
    return IContentService.Stub.asInterface(ServiceManagerNative.getService("content"));
  }
  
  public void addPeriodicSync(Account paramAccount, String paramString, Bundle paramBundle, long paramLong) throws RemoteException {
    getService().addPeriodicSync(paramAccount, paramString, paramBundle, paramLong);
  }
  
  public void addStatusChangeListener(int paramInt, ISyncStatusObserver paramISyncStatusObserver) throws RemoteException {
    getService().addStatusChangeListener(paramInt, paramISyncStatusObserver);
  }
  
  public void cancelSync(Account paramAccount, String paramString) throws RemoteException {
    getService().cancelSync(paramAccount, paramString);
  }
  
  public List<SyncInfo> getCurrentSyncs() throws RemoteException {
    return getService().getCurrentSyncs();
  }
  
  public int getIsSyncable(Account paramAccount, String paramString) throws RemoteException {
    return getService().getIsSyncable(paramAccount, paramString);
  }
  
  public boolean getMasterSyncAutomatically() throws RemoteException {
    return getService().getMasterSyncAutomatically();
  }
  
  public List<PeriodicSync> getPeriodicSyncs(Account paramAccount, String paramString) throws RemoteException {
    return getService().getPeriodicSyncs(paramAccount, paramString);
  }
  
  public IContentService getService() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mService : Lcom/lody/virtual/server/interfaces/IContentService;
    //   4: invokestatic isAlive : (Landroid/os/IInterface;)Z
    //   7: ifne -> 38
    //   10: aload_0
    //   11: monitorenter
    //   12: aload_0
    //   13: ldc com/lody/virtual/server/interfaces/IContentService
    //   15: aload_0
    //   16: invokespecial getRemoteInterface : ()Ljava/lang/Object;
    //   19: invokestatic genProxy : (Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    //   22: checkcast com/lody/virtual/server/interfaces/IContentService
    //   25: putfield mService : Lcom/lody/virtual/server/interfaces/IContentService;
    //   28: aload_0
    //   29: monitorexit
    //   30: goto -> 38
    //   33: astore_1
    //   34: aload_0
    //   35: monitorexit
    //   36: aload_1
    //   37: athrow
    //   38: aload_0
    //   39: getfield mService : Lcom/lody/virtual/server/interfaces/IContentService;
    //   42: areturn
    // Exception table:
    //   from	to	target	type
    //   12	30	33	finally
    //   34	36	33	finally
  }
  
  public SyncAdapterType[] getSyncAdapterTypes() throws RemoteException {
    return getService().getSyncAdapterTypes();
  }
  
  public boolean getSyncAutomatically(Account paramAccount, String paramString) throws RemoteException {
    return getService().getSyncAutomatically(paramAccount, paramString);
  }
  
  public SyncStatusInfo getSyncStatus(Account paramAccount, String paramString) throws RemoteException {
    return getService().getSyncStatus(paramAccount, paramString);
  }
  
  public boolean isSyncActive(Account paramAccount, String paramString) throws RemoteException {
    return getService().isSyncActive(paramAccount, paramString);
  }
  
  public boolean isSyncPending(Account paramAccount, String paramString) throws RemoteException {
    return getService().isSyncPending(paramAccount, paramString);
  }
  
  public void notifyChange(Uri paramUri, IContentObserver paramIContentObserver, boolean paramBoolean1, boolean paramBoolean2, int paramInt) throws RemoteException {
    getService().notifyChange(paramUri, paramIContentObserver, paramBoolean1, paramBoolean2, paramInt);
  }
  
  public void registerContentObserver(Uri paramUri, boolean paramBoolean, IContentObserver paramIContentObserver, int paramInt) throws RemoteException {
    getService().registerContentObserver(paramUri, paramBoolean, paramIContentObserver, paramInt);
  }
  
  public void removePeriodicSync(Account paramAccount, String paramString, Bundle paramBundle) throws RemoteException {
    getService().removePeriodicSync(paramAccount, paramString, paramBundle);
  }
  
  public void removeStatusChangeListener(ISyncStatusObserver paramISyncStatusObserver) throws RemoteException {
    getService().removeStatusChangeListener(paramISyncStatusObserver);
  }
  
  public void requestSync(Account paramAccount, String paramString, Bundle paramBundle) throws RemoteException {
    getService().requestSync(paramAccount, paramString, paramBundle);
  }
  
  public void setIsSyncable(Account paramAccount, String paramString, int paramInt) throws RemoteException {
    getService().setIsSyncable(paramAccount, paramString, paramInt);
  }
  
  public void setMasterSyncAutomatically(boolean paramBoolean) throws RemoteException {
    getService().setMasterSyncAutomatically(paramBoolean);
  }
  
  public void setSyncAutomatically(Account paramAccount, String paramString, boolean paramBoolean) throws RemoteException {
    getService().setSyncAutomatically(paramAccount, paramString, paramBoolean);
  }
  
  public void sync(SyncRequest paramSyncRequest) throws RemoteException {
    getService().sync(paramSyncRequest);
  }
  
  public void unregisterContentObserver(IContentObserver paramIContentObserver) throws RemoteException {
    getService().unregisterContentObserver(paramIContentObserver);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\ipc\VContentManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */