package com.lody.virtual.server.content;

import android.accounts.Account;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;

public class SyncOperation implements Comparable {
  public static final int REASON_ACCOUNTS_UPDATED = -2;
  
  public static final int REASON_BACKGROUND_DATA_SETTINGS_CHANGED = -1;
  
  public static final int REASON_IS_SYNCABLE = -5;
  
  public static final int REASON_MASTER_SYNC_AUTO = -7;
  
  private static String[] REASON_NAMES = new String[] { "DataSettingsChanged", "AccountsUpdated", "ServiceChanged", "Periodic", "IsSyncable", "AutoSync", "MasterSyncAuto", "UserStart" };
  
  public static final int REASON_PERIODIC = -4;
  
  public static final int REASON_SERVICE_CHANGED = -3;
  
  public static final int REASON_SYNC_AUTO = -6;
  
  public static final int REASON_USER_START = -8;
  
  public final Account account;
  
  public final boolean allowParallelSyncs;
  
  public final String authority;
  
  public Long backoff;
  
  public long delayUntil;
  
  public long effectiveRunTime;
  
  public boolean expedited;
  
  public Bundle extras;
  
  public long flexTime;
  
  public final String key;
  
  public long latestRunTime;
  
  public SyncStorageEngine.PendingOperation pendingOperation;
  
  public final int reason;
  
  public final ComponentName service = null;
  
  public int syncSource;
  
  public final int userId;
  
  public SyncOperation(Account paramAccount, int paramInt1, int paramInt2, int paramInt3, String paramString, Bundle paramBundle, long paramLong1, long paramLong2, long paramLong3, long paramLong4, boolean paramBoolean) {
    this.account = paramAccount;
    this.authority = paramString;
    this.userId = paramInt1;
    this.reason = paramInt2;
    this.syncSource = paramInt3;
    this.allowParallelSyncs = paramBoolean;
    Bundle bundle = new Bundle(paramBundle);
    this.extras = bundle;
    cleanBundle(bundle);
    this.delayUntil = paramLong4;
    this.backoff = Long.valueOf(paramLong3);
    paramLong3 = SystemClock.elapsedRealtime();
    if (paramLong1 < 0L || isExpedited()) {
      this.expedited = true;
      this.latestRunTime = paramLong3;
      this.flexTime = 0L;
    } else {
      this.expedited = false;
      this.latestRunTime = paramLong3 + paramLong1;
      this.flexTime = paramLong2;
    } 
    updateEffectiveRunTime();
    this.key = toKey();
  }
  
  SyncOperation(SyncOperation paramSyncOperation) {
    this.account = paramSyncOperation.account;
    this.authority = paramSyncOperation.authority;
    this.userId = paramSyncOperation.userId;
    this.reason = paramSyncOperation.reason;
    this.syncSource = paramSyncOperation.syncSource;
    this.extras = new Bundle(paramSyncOperation.extras);
    this.expedited = paramSyncOperation.expedited;
    this.latestRunTime = SystemClock.elapsedRealtime();
    this.flexTime = 0L;
    this.backoff = paramSyncOperation.backoff;
    this.allowParallelSyncs = paramSyncOperation.allowParallelSyncs;
    updateEffectiveRunTime();
    this.key = toKey();
  }
  
  private void cleanBundle(Bundle paramBundle) {
    removeFalseExtra(paramBundle, "upload");
    removeFalseExtra(paramBundle, "force");
    removeFalseExtra(paramBundle, "ignore_settings");
    removeFalseExtra(paramBundle, "ignore_backoff");
    removeFalseExtra(paramBundle, "do_not_retry");
    removeFalseExtra(paramBundle, "discard_deletions");
    removeFalseExtra(paramBundle, "expedited");
    removeFalseExtra(paramBundle, "deletions_override");
    removeFalseExtra(paramBundle, "allow_metered");
    paramBundle.remove("expected_upload");
    paramBundle.remove("expected_download");
  }
  
  public static void extrasToStringBuilder(Bundle paramBundle, StringBuilder paramStringBuilder) {
    paramStringBuilder.append("[");
    for (String str : paramBundle.keySet()) {
      paramStringBuilder.append(str);
      paramStringBuilder.append("=");
      paramStringBuilder.append(paramBundle.get(str));
      paramStringBuilder.append(" ");
    } 
    paramStringBuilder.append("]");
  }
  
  public static String reasonToString(PackageManager paramPackageManager, int paramInt) {
    if (paramInt >= 0) {
      if (paramPackageManager != null) {
        String[] arrayOfString1 = paramPackageManager.getPackagesForUid(paramInt);
        if (arrayOfString1 != null && arrayOfString1.length == 1)
          return arrayOfString1[0]; 
        String str = paramPackageManager.getNameForUid(paramInt);
        return (str != null) ? str : String.valueOf(paramInt);
      } 
      return String.valueOf(paramInt);
    } 
    int i = -paramInt - 1;
    String[] arrayOfString = REASON_NAMES;
    return (i >= arrayOfString.length) ? String.valueOf(paramInt) : arrayOfString[i];
  }
  
  private void removeFalseExtra(Bundle paramBundle, String paramString) {
    if (!paramBundle.getBoolean(paramString, false))
      paramBundle.remove(paramString); 
  }
  
  private String toKey() {
    StringBuilder stringBuilder = new StringBuilder();
    if (this.service == null) {
      stringBuilder.append("authority: ");
      stringBuilder.append(this.authority);
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(" account {name=");
      stringBuilder1.append(this.account.name);
      stringBuilder1.append(", user=");
      stringBuilder1.append(this.userId);
      stringBuilder1.append(", type=");
      stringBuilder1.append(this.account.type);
      stringBuilder1.append("}");
      stringBuilder.append(stringBuilder1.toString());
    } else {
      stringBuilder.append("service {package=");
      stringBuilder.append(this.service.getPackageName());
      stringBuilder.append(" user=");
      stringBuilder.append(this.userId);
      stringBuilder.append(", class=");
      stringBuilder.append(this.service.getClassName());
      stringBuilder.append("}");
    } 
    stringBuilder.append(" extras: ");
    extrasToStringBuilder(this.extras, stringBuilder);
    return stringBuilder.toString();
  }
  
  public int compareTo(Object paramObject) {
    paramObject = paramObject;
    boolean bool1 = this.expedited;
    boolean bool2 = ((SyncOperation)paramObject).expedited;
    byte b = 1;
    if (bool1 != bool2) {
      if (bool1)
        b = -1; 
      return b;
    } 
    long l1 = Math.max(this.effectiveRunTime - this.flexTime, 0L);
    long l2 = Math.max(((SyncOperation)paramObject).effectiveRunTime - ((SyncOperation)paramObject).flexTime, 0L);
    return (l1 < l2) ? -1 : ((l2 < l1) ? 1 : 0);
  }
  
  public String dump(PackageManager paramPackageManager, boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.account.name);
    stringBuilder.append(" u");
    stringBuilder.append(this.userId);
    stringBuilder.append(" (");
    stringBuilder.append(this.account.type);
    stringBuilder.append(")");
    stringBuilder.append(", ");
    stringBuilder.append(this.authority);
    stringBuilder.append(", ");
    stringBuilder.append(SyncStorageEngine.SOURCES[this.syncSource]);
    stringBuilder.append(", latestRunTime ");
    stringBuilder.append(this.latestRunTime);
    if (this.expedited)
      stringBuilder.append(", EXPEDITED"); 
    stringBuilder.append(", reason: ");
    stringBuilder.append(reasonToString(paramPackageManager, this.reason));
    if (!paramBoolean && !this.extras.keySet().isEmpty()) {
      stringBuilder.append("\n    ");
      extrasToStringBuilder(this.extras, stringBuilder);
    } 
    return stringBuilder.toString();
  }
  
  public boolean ignoreBackoff() {
    return this.extras.getBoolean("ignore_backoff", false);
  }
  
  public boolean isExpedited() {
    Bundle bundle = this.extras;
    boolean bool = false;
    if (bundle.getBoolean("expedited", false) || this.expedited)
      bool = true; 
    return bool;
  }
  
  public boolean isInitialization() {
    return this.extras.getBoolean("initialize", false);
  }
  
  public boolean isMeteredDisallowed() {
    return this.extras.getBoolean("allow_metered", false);
  }
  
  public String toString() {
    return dump(null, true);
  }
  
  public void updateEffectiveRunTime() {
    long l;
    if (ignoreBackoff()) {
      l = this.latestRunTime;
    } else {
      l = Math.max(Math.max(this.latestRunTime, this.delayUntil), this.backoff.longValue());
    } 
    this.effectiveRunTime = l;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\content\SyncOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */