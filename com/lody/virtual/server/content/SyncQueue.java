package com.lody.virtual.server.content;

import android.accounts.Account;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SyncQueue {
  private static final String TAG = "SyncManager";
  
  private final HashMap<String, SyncOperation> mOperationsMap = new HashMap<String, SyncOperation>();
  
  private final SyncAdaptersCache mSyncAdapters;
  
  private final SyncStorageEngine mSyncStorageEngine;
  
  public SyncQueue(SyncStorageEngine paramSyncStorageEngine, SyncAdaptersCache paramSyncAdaptersCache) {
    this.mSyncStorageEngine = paramSyncStorageEngine;
    this.mSyncAdapters = paramSyncAdaptersCache;
  }
  
  private boolean add(SyncOperation paramSyncOperation, SyncStorageEngine.PendingOperation paramPendingOperation) {
    String str = paramSyncOperation.key;
    SyncOperation syncOperation = this.mOperationsMap.get(str);
    boolean bool = true;
    if (syncOperation != null) {
      if (paramSyncOperation.compareTo(syncOperation) <= 0) {
        syncOperation.expedited = paramSyncOperation.expedited;
        syncOperation.latestRunTime = Math.min(syncOperation.latestRunTime, paramSyncOperation.latestRunTime);
        syncOperation.flexTime = paramSyncOperation.flexTime;
      } else {
        bool = false;
      } 
      return bool;
    } 
    paramSyncOperation.pendingOperation = paramPendingOperation;
    if (paramSyncOperation.pendingOperation == null) {
      paramPendingOperation = new SyncStorageEngine.PendingOperation(paramSyncOperation.account, paramSyncOperation.userId, paramSyncOperation.reason, paramSyncOperation.syncSource, paramSyncOperation.authority, paramSyncOperation.extras, paramSyncOperation.expedited);
      paramPendingOperation = this.mSyncStorageEngine.insertIntoPending(paramPendingOperation);
      if (paramPendingOperation != null) {
        paramSyncOperation.pendingOperation = paramPendingOperation;
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("error adding pending sync operation ");
        stringBuilder.append(paramSyncOperation);
        throw new IllegalStateException(stringBuilder.toString());
      } 
    } 
    this.mOperationsMap.put(str, paramSyncOperation);
    return true;
  }
  
  public boolean add(SyncOperation paramSyncOperation) {
    return add(paramSyncOperation, null);
  }
  
  public void addPendingOperations(int paramInt) {
    for (SyncStorageEngine.PendingOperation pendingOperation : this.mSyncStorageEngine.getPendingOperations()) {
      long l;
      if (pendingOperation.userId != paramInt)
        continue; 
      Pair<Long, Long> pair = this.mSyncStorageEngine.getBackoff(pendingOperation.account, pendingOperation.userId, pendingOperation.authority);
      SyncAdaptersCache.SyncAdapterInfo syncAdapterInfo = this.mSyncAdapters.getServiceInfo(pendingOperation.account, pendingOperation.authority);
      if (syncAdapterInfo == null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Missing sync adapter info for authority ");
        stringBuilder.append(pendingOperation.authority);
        stringBuilder.append(", userId ");
        stringBuilder.append(pendingOperation.userId);
        Log.w("SyncManager", stringBuilder.toString());
        continue;
      } 
      Account account = pendingOperation.account;
      int i = pendingOperation.userId;
      int j = pendingOperation.reason;
      int k = pendingOperation.syncSource;
      String str = pendingOperation.authority;
      Bundle bundle = pendingOperation.extras;
      if (pair != null) {
        l = ((Long)pair.first).longValue();
      } else {
        l = 0L;
      } 
      SyncOperation syncOperation = new SyncOperation(account, i, j, k, str, bundle, 0L, 0L, l, this.mSyncStorageEngine.getDelayUntilTime(pendingOperation.account, pendingOperation.userId, pendingOperation.authority), syncAdapterInfo.type.allowParallelSyncs());
      syncOperation.expedited = pendingOperation.expedited;
      syncOperation.pendingOperation = pendingOperation;
      add(syncOperation, pendingOperation);
    } 
  }
  
  public Collection<SyncOperation> getOperations() {
    return this.mOperationsMap.values();
  }
  
  public void onBackoffChanged(Account paramAccount, int paramInt, String paramString, long paramLong) {
    for (SyncOperation syncOperation : this.mOperationsMap.values()) {
      if (syncOperation.account.equals(paramAccount) && syncOperation.authority.equals(paramString) && syncOperation.userId == paramInt) {
        syncOperation.backoff = Long.valueOf(paramLong);
        syncOperation.updateEffectiveRunTime();
      } 
    } 
  }
  
  public void onDelayUntilTimeChanged(Account paramAccount, String paramString, long paramLong) {
    for (SyncOperation syncOperation : this.mOperationsMap.values()) {
      if (syncOperation.account.equals(paramAccount) && syncOperation.authority.equals(paramString)) {
        syncOperation.delayUntil = paramLong;
        syncOperation.updateEffectiveRunTime();
      } 
    } 
  }
  
  public void remove(Account paramAccount, int paramInt, String paramString) {
    Iterator<Map.Entry> iterator = this.mOperationsMap.entrySet().iterator();
    while (iterator.hasNext()) {
      SyncOperation syncOperation = (SyncOperation)((Map.Entry)iterator.next()).getValue();
      if ((paramAccount != null && !syncOperation.account.equals(paramAccount)) || (paramString != null && !syncOperation.authority.equals(paramString)) || paramInt != syncOperation.userId)
        continue; 
      iterator.remove();
      if (!this.mSyncStorageEngine.deleteFromPending(syncOperation.pendingOperation)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unable to find pending row for ");
        stringBuilder.append(syncOperation);
        String str = stringBuilder.toString();
        Log.e("SyncManager", str, new IllegalStateException(str));
      } 
    } 
  }
  
  public void remove(SyncOperation paramSyncOperation) {
    SyncOperation syncOperation = this.mOperationsMap.remove(paramSyncOperation.key);
    if (syncOperation == null)
      return; 
    if (!this.mSyncStorageEngine.deleteFromPending(syncOperation.pendingOperation)) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("unable to find pending row for ");
      stringBuilder.append(syncOperation);
      String str = stringBuilder.toString();
      Log.e("SyncManager", str, new IllegalStateException(str));
    } 
  }
  
  public void removeUser(int paramInt) {
    ArrayList<SyncOperation> arrayList = new ArrayList();
    for (SyncOperation syncOperation : this.mOperationsMap.values()) {
      if (syncOperation.userId == paramInt)
        arrayList.add(syncOperation); 
    } 
    Iterator<SyncOperation> iterator = arrayList.iterator();
    while (iterator.hasNext())
      remove(iterator.next()); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\content\SyncQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */