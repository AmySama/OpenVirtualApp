package com.lody.virtual.server.content;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ISyncStatusObserver;
import android.content.PeriodicSync;
import android.content.SyncAdapterType;
import android.content.SyncInfo;
import android.content.SyncRequest;
import android.content.SyncStatusInfo;
import android.database.IContentObserver;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VBinder;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.server.interfaces.IContentService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mirror.android.content.PeriodicSync;
import mirror.android.content.SyncRequest;

public final class VContentService extends IContentService.Stub {
  private static final String TAG = "ContentService";
  
  private static final VContentService sInstance = new VContentService();
  
  private Context mContext = VirtualCore.get().getContext();
  
  private final ObserverNode mRootNode = new ObserverNode("");
  
  private SyncManager mSyncManager = null;
  
  private final Object mSyncManagerLock = new Object();
  
  public static VContentService get() {
    return sInstance;
  }
  
  private SyncManager getSyncManager() {
    Object object = this.mSyncManagerLock;
    /* monitor enter ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
    try {
      if (this.mSyncManager == null) {
        SyncManager syncManager1 = new SyncManager();
        this(this.mContext);
        this.mSyncManager = syncManager1;
      } 
    } catch (SQLiteException sQLiteException) {
      Log.e("ContentService", "Can't create SyncManager", (Throwable)sQLiteException);
    } finally {
      Exception exception;
    } 
    SyncManager syncManager = this.mSyncManager;
    /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=null} */
    return syncManager;
  }
  
  public static void systemReady() {
    get().getSyncManager();
  }
  
  public void addPeriodicSync(Account paramAccount, String paramString, Bundle paramBundle, long paramLong) {
    if (paramAccount != null) {
      if (!TextUtils.isEmpty(paramString)) {
        int i = VUserHandle.getCallingUserId();
        long l = paramLong;
        if (paramLong < 60L) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Requested poll frequency of ");
          stringBuilder.append(paramLong);
          stringBuilder.append(" seconds being rounded up to 60 seconds.");
          VLog.w("ContentService", stringBuilder.toString(), new Object[0]);
          l = 60L;
        } 
        paramLong = clearCallingIdentity();
        try {
          PeriodicSync periodicSync = new PeriodicSync();
          this(paramAccount, paramString, paramBundle, l);
          PeriodicSync.flexTime.set(periodicSync, SyncStorageEngine.calculateDefaultFlexTime(l));
          getSyncManager().getSyncStorageEngine().addPeriodicSync(periodicSync, i);
          return;
        } finally {
          restoreCallingIdentity(paramLong);
        } 
      } 
      throw new IllegalArgumentException("Authority must not be empty.");
    } 
    throw new IllegalArgumentException("Account must not be null");
  }
  
  public void addStatusChangeListener(int paramInt, ISyncStatusObserver paramISyncStatusObserver) {
    long l = clearCallingIdentity();
    try {
      SyncManager syncManager = getSyncManager();
      if (syncManager != null && paramISyncStatusObserver != null)
        syncManager.getSyncStorageEngine().addStatusChangeListener(paramInt, paramISyncStatusObserver); 
      return;
    } finally {
      restoreCallingIdentity(l);
    } 
  }
  
  public void cancelSync(Account paramAccount, String paramString) {
    if (paramString == null || paramString.length() != 0) {
      int i = VUserHandle.getCallingUserId();
      long l = clearCallingIdentity();
      try {
        SyncManager syncManager = getSyncManager();
        if (syncManager != null) {
          syncManager.clearScheduledSyncOperations(paramAccount, i, paramString);
          syncManager.cancelActiveSync(paramAccount, i, paramString);
        } 
        return;
      } finally {
        restoreCallingIdentity(l);
      } 
    } 
    throw new IllegalArgumentException("Authority must be non-empty");
  }
  
  public List<SyncInfo> getCurrentSyncs() {
    int i = VUserHandle.getCallingUserId();
    long l = clearCallingIdentity();
    try {
      List<VSyncInfo> list = getSyncManager().getSyncStorageEngine().getCurrentSyncsCopy(i);
      ArrayList<SyncInfo> arrayList = new ArrayList();
      this(list.size());
      Iterator<VSyncInfo> iterator = list.iterator();
      while (iterator.hasNext())
        arrayList.add(((VSyncInfo)iterator.next()).toSyncInfo()); 
      return arrayList;
    } finally {
      restoreCallingIdentity(l);
    } 
  }
  
  public int getIsSyncable(Account paramAccount, String paramString) {
    int i = VUserHandle.getCallingUserId();
    long l = clearCallingIdentity();
    try {
      SyncManager syncManager = getSyncManager();
      if (syncManager != null) {
        i = syncManager.getIsSyncable(paramAccount, i, paramString);
        return i;
      } 
      return -1;
    } finally {
      restoreCallingIdentity(l);
    } 
  }
  
  public boolean getMasterSyncAutomatically() {
    int i = VUserHandle.getCallingUserId();
    long l = clearCallingIdentity();
    try {
      SyncManager syncManager = getSyncManager();
      if (syncManager != null)
        return syncManager.getSyncStorageEngine().getMasterSyncAutomatically(i); 
      return false;
    } finally {
      restoreCallingIdentity(l);
    } 
  }
  
  public List<PeriodicSync> getPeriodicSyncs(Account paramAccount, String paramString) {
    if (paramAccount != null) {
      if (!TextUtils.isEmpty(paramString)) {
        int i = VUserHandle.getCallingUserId();
        long l = clearCallingIdentity();
        try {
          return getSyncManager().getSyncStorageEngine().getPeriodicSyncs(paramAccount, i, paramString);
        } finally {
          restoreCallingIdentity(l);
        } 
      } 
      throw new IllegalArgumentException("Authority must not be empty");
    } 
    throw new IllegalArgumentException("Account must not be null");
  }
  
  public SyncAdapterType[] getSyncAdapterTypes() {
    VUserHandle.getCallingUserId();
    long l = clearCallingIdentity();
    try {
      return getSyncManager().getSyncAdapterTypes();
    } finally {
      restoreCallingIdentity(l);
    } 
  }
  
  public boolean getSyncAutomatically(Account paramAccount, String paramString) {
    int i = VUserHandle.getCallingUserId();
    long l = clearCallingIdentity();
    try {
      SyncManager syncManager = getSyncManager();
      if (syncManager != null)
        return syncManager.getSyncStorageEngine().getSyncAutomatically(paramAccount, i, paramString); 
      return false;
    } finally {
      restoreCallingIdentity(l);
    } 
  }
  
  public SyncStatusInfo getSyncStatus(Account paramAccount, String paramString) {
    if (!TextUtils.isEmpty(paramString)) {
      int i = VUserHandle.getCallingUserId();
      long l = clearCallingIdentity();
      try {
        SyncManager syncManager = getSyncManager();
        if (syncManager != null)
          return syncManager.getSyncStorageEngine().getStatusByAccountAndAuthority(paramAccount, i, paramString); 
        return null;
      } finally {
        restoreCallingIdentity(l);
      } 
    } 
    throw new IllegalArgumentException("Authority must not be empty");
  }
  
  public boolean isSyncActive(Account paramAccount, String paramString) {
    int i = VUserHandle.getCallingUserId();
    long l = clearCallingIdentity();
    try {
      SyncManager syncManager = getSyncManager();
      if (syncManager != null)
        return syncManager.getSyncStorageEngine().isSyncActive(paramAccount, i, paramString); 
      return false;
    } finally {
      restoreCallingIdentity(l);
    } 
  }
  
  public boolean isSyncPending(Account paramAccount, String paramString) {
    int i = VUserHandle.getCallingUserId();
    long l = clearCallingIdentity();
    try {
      SyncManager syncManager = getSyncManager();
      if (syncManager != null)
        return syncManager.getSyncStorageEngine().isSyncPending(paramAccount, i, paramString); 
      return false;
    } finally {
      restoreCallingIdentity(l);
    } 
  }
  
  public void notifyChange(Uri paramUri, IContentObserver paramIContentObserver, boolean paramBoolean1, boolean paramBoolean2) {
    notifyChange(paramUri, paramIContentObserver, paramBoolean1, paramBoolean2, VUserHandle.getCallingUserId());
  }
  
  public void notifyChange(Uri paramUri, IContentObserver paramIContentObserver, boolean paramBoolean1, boolean paramBoolean2, int paramInt) {
    if (Log.isLoggable("ContentService", 2)) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Notifying update of ");
      stringBuilder.append(paramUri);
      stringBuilder.append(" for user ");
      stringBuilder.append(paramInt);
      stringBuilder.append(" from observer ");
      stringBuilder.append(paramIContentObserver);
      stringBuilder.append(", syncToNetwork ");
      stringBuilder.append(paramBoolean2);
      Log.v("ContentService", stringBuilder.toString());
    } 
    int i = VBinder.getCallingUid();
    long l = clearCallingIdentity();
    try {
      ArrayList arrayList1;
      ArrayList<ObserverCall> arrayList = new ArrayList();
      this();
      ObserverNode observerNode = this.mRootNode;
      /* monitor enter ClassFileLocalVariableReferenceExpression{type=InnerObjectType{ObjectType{com/lody/virtual/server/content/VContentService}.Lcom/lody/virtual/server/content/VContentService$ObserverNode;}, name=null} */
      try {
        ObserverNode observerNode1 = this.mRootNode;
        ObserverNode observerNode2 = observerNode;
        observerNode = observerNode2;
      } finally {
        paramUri = null;
      } 
      ArrayList arrayList2 = arrayList1;
      /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/util/ArrayList}, name=null} */
      throw paramUri;
    } finally {
      restoreCallingIdentity(l);
    } 
  }
  
  public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2) throws RemoteException {
    try {
      return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
    } catch (RuntimeException runtimeException) {
      if (!(runtimeException instanceof SecurityException))
        runtimeException.printStackTrace(); 
      throw runtimeException;
    } 
  }
  
  public void registerContentObserver(Uri paramUri, boolean paramBoolean, IContentObserver paramIContentObserver) {
    registerContentObserver(paramUri, paramBoolean, paramIContentObserver, VUserHandle.getCallingUserId());
  }
  
  public void registerContentObserver(Uri paramUri, boolean paramBoolean, IContentObserver paramIContentObserver, int paramInt) {
    if (paramIContentObserver != null && paramUri != null)
      synchronized (this.mRootNode) {
        this.mRootNode.addObserverLocked(paramUri, paramIContentObserver, paramBoolean, this.mRootNode, VBinder.getCallingUid(), VBinder.getCallingPid(), paramInt);
        return;
      }  
    throw new IllegalArgumentException("You must pass a valid uri and observer");
  }
  
  public void removePeriodicSync(Account paramAccount, String paramString, Bundle paramBundle) {
    if (paramAccount != null) {
      if (!TextUtils.isEmpty(paramString)) {
        int i = VUserHandle.getCallingUserId();
        long l = clearCallingIdentity();
        try {
          PeriodicSync periodicSync = new PeriodicSync();
          this(paramAccount, paramString, paramBundle, 0L);
          getSyncManager().getSyncStorageEngine().removePeriodicSync(periodicSync, i);
          return;
        } finally {
          restoreCallingIdentity(l);
        } 
      } 
      throw new IllegalArgumentException("Authority must not be empty");
    } 
    throw new IllegalArgumentException("Account must not be null");
  }
  
  public void removeStatusChangeListener(ISyncStatusObserver paramISyncStatusObserver) {
    long l = clearCallingIdentity();
    try {
      SyncManager syncManager = getSyncManager();
      if (syncManager != null && paramISyncStatusObserver != null)
        syncManager.getSyncStorageEngine().removeStatusChangeListener(paramISyncStatusObserver); 
      return;
    } finally {
      restoreCallingIdentity(l);
    } 
  }
  
  public void requestSync(Account paramAccount, String paramString, Bundle paramBundle) {
    ContentResolver.validateSyncExtrasBundle(paramBundle);
    int i = VUserHandle.getCallingUserId();
    int j = VBinder.getCallingUid();
    long l = clearCallingIdentity();
    try {
      SyncManager syncManager = getSyncManager();
      if (syncManager != null)
        syncManager.scheduleSync(paramAccount, i, j, paramString, paramBundle, 0L, 0L, false); 
      return;
    } finally {
      restoreCallingIdentity(l);
    } 
  }
  
  public void setIsSyncable(Account paramAccount, String paramString, int paramInt) {
    if (!TextUtils.isEmpty(paramString)) {
      int i = VUserHandle.getCallingUserId();
      long l = clearCallingIdentity();
      try {
        SyncManager syncManager = getSyncManager();
        if (syncManager != null)
          syncManager.getSyncStorageEngine().setIsSyncable(paramAccount, i, paramString, paramInt); 
        return;
      } finally {
        restoreCallingIdentity(l);
      } 
    } 
    throw new IllegalArgumentException("Authority must not be empty");
  }
  
  public void setMasterSyncAutomatically(boolean paramBoolean) {
    int i = VUserHandle.getCallingUserId();
    long l = clearCallingIdentity();
    try {
      SyncManager syncManager = getSyncManager();
      if (syncManager != null)
        syncManager.getSyncStorageEngine().setMasterSyncAutomatically(paramBoolean, i); 
      return;
    } finally {
      restoreCallingIdentity(l);
    } 
  }
  
  public void setSyncAutomatically(Account paramAccount, String paramString, boolean paramBoolean) {
    if (!TextUtils.isEmpty(paramString)) {
      int i = VUserHandle.getCallingUserId();
      long l = clearCallingIdentity();
      try {
        SyncManager syncManager = getSyncManager();
        if (syncManager != null)
          syncManager.getSyncStorageEngine().setSyncAutomatically(paramAccount, i, paramString, paramBoolean); 
        return;
      } finally {
        restoreCallingIdentity(l);
      } 
    } 
    throw new IllegalArgumentException("Authority must be non-empty");
  }
  
  public void sync(SyncRequest paramSyncRequest) {
    Bundle bundle = (Bundle)SyncRequest.mExtras.get(paramSyncRequest);
    long l1 = SyncRequest.mSyncFlexTimeSecs.get(paramSyncRequest);
    long l2 = SyncRequest.mSyncRunTimeSecs.get(paramSyncRequest);
    int i = VUserHandle.getCallingUserId();
    int j = VBinder.getCallingUid();
    long l3 = clearCallingIdentity();
    try {
      SyncManager syncManager = getSyncManager();
      if (syncManager != null) {
        Account account = (Account)SyncRequest.mAccountToSync.get(paramSyncRequest);
        String str = (String)SyncRequest.mAuthority.get(paramSyncRequest);
        if (SyncRequest.mIsPeriodic.get(paramSyncRequest)) {
          long l = 60L;
          if (l2 < 60L) {
            StringBuilder stringBuilder = new StringBuilder();
            this();
            stringBuilder.append("Requested poll frequency of ");
            stringBuilder.append(l2);
            stringBuilder.append(" seconds being rounded up to 60 seconds.");
            VLog.w("ContentService", stringBuilder.toString(), new Object[0]);
            l2 = l;
          } 
          PeriodicSync periodicSync = new PeriodicSync();
          this(account, str, bundle, l2);
          PeriodicSync.flexTime.set(periodicSync, l1);
          getSyncManager().getSyncStorageEngine().addPeriodicSync(periodicSync, i);
        } else {
          syncManager.scheduleSync(account, i, j, str, bundle, l1 * 1000L, 1000L * l2, false);
        } 
      } 
      return;
    } finally {
      restoreCallingIdentity(l3);
    } 
  }
  
  public void unregisterContentObserver(IContentObserver paramIContentObserver) {
    if (paramIContentObserver != null)
      synchronized (this.mRootNode) {
        this.mRootNode.removeObserverLocked(paramIContentObserver);
        return;
      }  
    throw new IllegalArgumentException("You must pass a valid observer");
  }
  
  public static final class ObserverCall {
    final VContentService.ObserverNode mNode;
    
    final IContentObserver mObserver;
    
    final boolean mSelfChange;
    
    ObserverCall(VContentService.ObserverNode param1ObserverNode, IContentObserver param1IContentObserver, boolean param1Boolean) {
      this.mNode = param1ObserverNode;
      this.mObserver = param1IContentObserver;
      this.mSelfChange = param1Boolean;
    }
  }
  
  public static final class ObserverNode {
    public static final int DELETE_TYPE = 2;
    
    public static final int INSERT_TYPE = 0;
    
    public static final int UPDATE_TYPE = 1;
    
    private ArrayList<ObserverNode> mChildren = new ArrayList<ObserverNode>();
    
    private String mName;
    
    private ArrayList<ObserverEntry> mObservers = new ArrayList<ObserverEntry>();
    
    public ObserverNode(String param1String) {
      this.mName = param1String;
    }
    
    private void addObserverLocked(Uri param1Uri, int param1Int1, IContentObserver param1IContentObserver, boolean param1Boolean, Object param1Object, int param1Int2, int param1Int3, int param1Int4) {
      if (param1Int1 == countUriSegments(param1Uri)) {
        this.mObservers.add(new ObserverEntry(param1IContentObserver, param1Boolean, param1Object, param1Int2, param1Int3, param1Int4));
        return;
      } 
      String str = getUriSegment(param1Uri, param1Int1);
      if (str != null) {
        int i = this.mChildren.size();
        for (byte b = 0; b < i; b++) {
          ObserverNode observerNode1 = this.mChildren.get(b);
          if (observerNode1.mName.equals(str)) {
            observerNode1.addObserverLocked(param1Uri, param1Int1 + 1, param1IContentObserver, param1Boolean, param1Object, param1Int2, param1Int3, param1Int4);
            return;
          } 
        } 
        ObserverNode observerNode = new ObserverNode(str);
        this.mChildren.add(observerNode);
        observerNode.addObserverLocked(param1Uri, param1Int1 + 1, param1IContentObserver, param1Boolean, param1Object, param1Int2, param1Int3, param1Int4);
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Invalid Uri (");
      stringBuilder.append(param1Uri);
      stringBuilder.append(") used for observer");
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    private void collectMyObserversLocked(boolean param1Boolean1, IContentObserver param1IContentObserver, boolean param1Boolean2, int param1Int, ArrayList<VContentService.ObserverCall> param1ArrayList) {
      IBinder iBinder;
      int i = this.mObservers.size();
      if (param1IContentObserver == null) {
        param1IContentObserver = null;
      } else {
        iBinder = param1IContentObserver.asBinder();
      } 
      for (byte b = 0; b < i; b++) {
        boolean bool;
        ObserverEntry observerEntry = this.mObservers.get(b);
        if (observerEntry.observer.asBinder() == iBinder) {
          bool = true;
        } else {
          bool = false;
        } 
        if ((!bool || param1Boolean2) && (param1Int == -1 || observerEntry.userHandle == -1 || param1Int == observerEntry.userHandle) && (param1Boolean1 || observerEntry.notifyForDescendants))
          param1ArrayList.add(new VContentService.ObserverCall(this, observerEntry.observer, bool)); 
      } 
    }
    
    private int countUriSegments(Uri param1Uri) {
      return (param1Uri == null) ? 0 : (param1Uri.getPathSegments().size() + 1);
    }
    
    private String getUriSegment(Uri param1Uri, int param1Int) {
      return (param1Uri != null) ? ((param1Int == 0) ? param1Uri.getAuthority() : param1Uri.getPathSegments().get(param1Int - 1)) : null;
    }
    
    public void addObserverLocked(Uri param1Uri, IContentObserver param1IContentObserver, boolean param1Boolean, Object param1Object, int param1Int1, int param1Int2, int param1Int3) {
      addObserverLocked(param1Uri, 0, param1IContentObserver, param1Boolean, param1Object, param1Int1, param1Int2, param1Int3);
    }
    
    public void collectObserversLocked(Uri param1Uri, int param1Int1, IContentObserver param1IContentObserver, boolean param1Boolean, int param1Int2, ArrayList<VContentService.ObserverCall> param1ArrayList) {
      String str;
      if (param1Int1 >= countUriSegments(param1Uri)) {
        collectMyObserversLocked(true, param1IContentObserver, param1Boolean, param1Int2, param1ArrayList);
        str = null;
      } else {
        str = getUriSegment(param1Uri, param1Int1);
        collectMyObserversLocked(false, param1IContentObserver, param1Boolean, param1Int2, param1ArrayList);
      } 
      int i = this.mChildren.size();
      for (byte b = 0; b < i; b++) {
        ObserverNode observerNode = this.mChildren.get(b);
        if (str == null || observerNode.mName.equals(str)) {
          observerNode.collectObserversLocked(param1Uri, param1Int1 + 1, param1IContentObserver, param1Boolean, param1Int2, param1ArrayList);
          if (str != null)
            break; 
        } 
      } 
    }
    
    public boolean removeObserverLocked(IContentObserver param1IContentObserver) {
      int i = this.mChildren.size();
      int j = 0;
      while (j < i) {
        int k = i;
        int m = j;
        if (((ObserverNode)this.mChildren.get(j)).removeObserverLocked(param1IContentObserver)) {
          this.mChildren.remove(j);
          m = j - 1;
          k = i - 1;
        } 
        j = m + 1;
        i = k;
      } 
      IBinder iBinder = param1IContentObserver.asBinder();
      i = this.mObservers.size();
      for (j = 0; j < i; j++) {
        ObserverEntry observerEntry = this.mObservers.get(j);
        if (observerEntry.observer.asBinder() == iBinder) {
          this.mObservers.remove(j);
          iBinder.unlinkToDeath(observerEntry, 0);
          break;
        } 
      } 
      return (this.mChildren.size() == 0 && this.mObservers.size() == 0);
    }
    
    private class ObserverEntry implements IBinder.DeathRecipient {
      public final boolean notifyForDescendants;
      
      public final IContentObserver observer;
      
      private final Object observersLock;
      
      public final int pid;
      
      public final int uid;
      
      private final int userHandle;
      
      public ObserverEntry(IContentObserver param2IContentObserver, boolean param2Boolean, Object param2Object, int param2Int1, int param2Int2, int param2Int3) {
        this.observersLock = param2Object;
        this.observer = param2IContentObserver;
        this.uid = param2Int1;
        this.pid = param2Int2;
        this.userHandle = param2Int3;
        this.notifyForDescendants = param2Boolean;
        try {
          param2IContentObserver.asBinder().linkToDeath(this, 0);
        } catch (RemoteException remoteException) {
          binderDied();
        } 
      }
      
      public void binderDied() {
        synchronized (this.observersLock) {
          VContentService.ObserverNode.this.removeObserverLocked(this.observer);
          return;
        } 
      }
    }
  }
  
  private class ObserverEntry implements IBinder.DeathRecipient {
    public final boolean notifyForDescendants;
    
    public final IContentObserver observer;
    
    private final Object observersLock;
    
    public final int pid;
    
    public final int uid;
    
    private final int userHandle;
    
    public ObserverEntry(IContentObserver param1IContentObserver, boolean param1Boolean, Object param1Object, int param1Int1, int param1Int2, int param1Int3) {
      this.observersLock = param1Object;
      this.observer = param1IContentObserver;
      this.uid = param1Int1;
      this.pid = param1Int2;
      this.userHandle = param1Int3;
      this.notifyForDescendants = param1Boolean;
      try {
        param1IContentObserver.asBinder().linkToDeath(this, 0);
      } catch (RemoteException remoteException) {
        binderDied();
      } 
    }
    
    public void binderDied() {
      synchronized (this.observersLock) {
        this.this$0.removeObserverLocked(this.observer);
        return;
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\content\VContentService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */