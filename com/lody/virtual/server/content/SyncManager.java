package com.lody.virtual.server.content;

import android.accounts.Account;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ISyncAdapter;
import android.content.ISyncContext;
import android.content.ISyncStatusObserver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.PeriodicSync;
import android.content.ServiceConnection;
import android.content.SyncAdapterType;
import android.content.SyncResult;
import android.content.SyncStatusInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.util.Pair;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.compat.ContentResolverCompat;
import com.lody.virtual.os.BackgroundThread;
import com.lody.virtual.os.VUserInfo;
import com.lody.virtual.os.VUserManager;
import com.lody.virtual.server.accounts.AccountAndUser;
import com.lody.virtual.server.accounts.VAccountManagerService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import mirror.android.content.PeriodicSync;

public class SyncManager {
  private static final String ACTION_SYNC_ALARM = "android.content.syncmanager.SYNC_ALARM";
  
  private static final long DEFAULT_MAX_SYNC_RETRY_TIME_IN_SECONDS = 3600L;
  
  private static final int DELAY_RETRY_SYNC_IN_PROGRESS_IN_SECONDS = 10;
  
  private static final String HANDLE_SYNC_ALARM_WAKE_LOCK = "SyncManagerHandleSyncAlarm";
  
  private static final int INITIALIZATION_UNBIND_DELAY_MS = 5000;
  
  private static final AccountAndUser[] INITIAL_ACCOUNTS_ARRAY = new AccountAndUser[0];
  
  private static final long INITIAL_SYNC_RETRY_TIME_IN_MS = 30000L;
  
  private static final long LOCAL_SYNC_DELAY = 30000L;
  
  private static final int MAX_SIMULTANEOUS_INITIALIZATION_SYNCS = 5;
  
  private static final int MAX_SIMULTANEOUS_REGULAR_SYNCS = 2;
  
  private static final long MAX_TIME_PER_SYNC = 300000L;
  
  private static final long SYNC_ALARM_TIMEOUT_MAX = 7200000L;
  
  private static final long SYNC_ALARM_TIMEOUT_MIN = 30000L;
  
  private static final String SYNC_LOOP_WAKE_LOCK = "SyncLoopWakeLock";
  
  private static final long SYNC_NOTIFICATION_DELAY = 30000L;
  
  private static final String SYNC_WAKE_LOCK_PREFIX = "*sync*";
  
  private static final String TAG = "SyncManager";
  
  private BroadcastReceiver mAccountsUpdatedReceiver = new BroadcastReceiver() {
      public void onReceive(Context param1Context, Intent param1Intent) {
        SyncManager.this.updateRunningAccounts();
        SyncManager.this.scheduleSync(null, -1, -2, null, null, 0L, 0L, false);
      }
    };
  
  protected final ArrayList<ActiveSyncContext> mActiveSyncContexts = new ArrayList<ActiveSyncContext>();
  
  private AlarmManager mAlarmService = null;
  
  private BroadcastReceiver mBackgroundDataSettingChanged = new BroadcastReceiver() {
      public void onReceive(Context param1Context, Intent param1Intent) {
        if (SyncManager.this.getConnectivityManager().getBackgroundDataSetting())
          SyncManager.this.scheduleSync(null, -1, -1, null, new Bundle(), 0L, 0L, false); 
      }
    };
  
  private volatile boolean mBootCompleted = false;
  
  private BroadcastReceiver mBootCompletedReceiver = new BroadcastReceiver() {
      public void onReceive(Context param1Context, Intent param1Intent) {
        SyncManager.this.mSyncHandler.onBootCompleted();
      }
    };
  
  private ConnectivityManager mConnManagerDoNotUseDirectly;
  
  private BroadcastReceiver mConnectivityIntentReceiver = new BroadcastReceiver() {
      public void onReceive(Context param1Context, Intent param1Intent) {
        boolean bool = SyncManager.this.mDataConnectionIsConnected;
        SyncManager syncManager = SyncManager.this;
        SyncManager.access$402(syncManager, syncManager.readDataConnectionState());
        if (SyncManager.this.mDataConnectionIsConnected) {
          if (!bool) {
            Log.v("SyncManager", "Reconnection detected: clearing all backoffs");
            synchronized (SyncManager.this.mSyncQueue) {
              SyncManager.this.mSyncStorageEngine.clearAllBackoffsLocked(SyncManager.this.mSyncQueue);
            } 
          } 
          SyncManager.this.sendCheckAlarmsMessage();
        } 
      }
    };
  
  private Context mContext;
  
  private volatile boolean mDataConnectionIsConnected = false;
  
  private final PowerManager mPowerManager;
  
  private volatile AccountAndUser[] mRunningAccounts = INITIAL_ACCOUNTS_ARRAY;
  
  private BroadcastReceiver mShutdownIntentReceiver = new BroadcastReceiver() {
      public void onReceive(Context param1Context, Intent param1Intent) {
        Log.w("SyncManager", "Writing sync state before shutdown...");
        SyncManager.this.getSyncStorageEngine().writeAllState();
      }
    };
  
  private BroadcastReceiver mStorageIntentReceiver = new BroadcastReceiver() {
      public void onReceive(Context param1Context, Intent param1Intent) {
        String str = param1Intent.getAction();
        if ("android.intent.action.DEVICE_STORAGE_LOW".equals(str)) {
          Log.v("SyncManager", "Internal storage is low.");
          SyncManager.access$002(SyncManager.this, true);
          SyncManager.this.cancelActiveSync(null, -1, null);
        } else if ("android.intent.action.DEVICE_STORAGE_OK".equals(str)) {
          Log.v("SyncManager", "Internal storage is ok.");
          SyncManager.access$002(SyncManager.this, false);
          SyncManager.this.sendCheckAlarmsMessage();
        } 
      }
    };
  
  private volatile boolean mStorageIsLow = false;
  
  protected SyncAdaptersCache mSyncAdapters;
  
  private final PendingIntent mSyncAlarmIntent;
  
  private final SyncHandler mSyncHandler;
  
  private final SyncQueue mSyncQueue;
  
  private int mSyncRandomOffsetMillis;
  
  private SyncStorageEngine mSyncStorageEngine;
  
  private BroadcastReceiver mUserIntentReceiver = new BroadcastReceiver() {
      public void onReceive(Context param1Context, Intent param1Intent) {
        String str = param1Intent.getAction();
        int i = param1Intent.getIntExtra("android.intent.extra.user_handle", -10000);
        if (i == -10000)
          return; 
        if ("virtual.android.intent.action.USER_REMOVED".equals(str)) {
          SyncManager.this.onUserRemoved(i);
        } else if ("virtual.android.intent.action.USER_ADDED".equals(str)) {
          SyncManager.this.onUserStarting(i);
        } else if ("virtual.android.intent.action.USER_REMOVED".equals(str)) {
          SyncManager.this.onUserStopping(i);
        } 
      }
    };
  
  private final VUserManager mUserManager;
  
  public SyncManager(Context paramContext) {
    this.mContext = paramContext;
    SyncStorageEngine.init(paramContext);
    SyncStorageEngine syncStorageEngine = SyncStorageEngine.getSingleton();
    this.mSyncStorageEngine = syncStorageEngine;
    syncStorageEngine.setOnSyncRequestListener(new SyncStorageEngine.OnSyncRequestListener() {
          public void onSyncRequest(Account param1Account, int param1Int1, int param1Int2, String param1String, Bundle param1Bundle) {
            SyncManager.this.scheduleSync(param1Account, param1Int1, param1Int2, param1String, param1Bundle, 0L, 0L, false);
          }
        });
    SyncAdaptersCache syncAdaptersCache = new SyncAdaptersCache(this.mContext);
    this.mSyncAdapters = syncAdaptersCache;
    syncAdaptersCache.refreshServiceCache(null);
    this.mSyncQueue = new SyncQueue(this.mSyncStorageEngine, this.mSyncAdapters);
    this.mSyncHandler = new SyncHandler(BackgroundThread.get().getLooper());
    this.mSyncAlarmIntent = PendingIntent.getBroadcast(this.mContext, 0, new Intent("android.content.syncmanager.SYNC_ALARM"), 0);
    IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
    paramContext.registerReceiver(this.mConnectivityIntentReceiver, intentFilter);
    intentFilter = new IntentFilter("android.intent.action.BOOT_COMPLETED");
    paramContext.registerReceiver(this.mBootCompletedReceiver, intentFilter);
    intentFilter = new IntentFilter("android.net.conn.BACKGROUND_DATA_SETTING_CHANGED");
    paramContext.registerReceiver(this.mBackgroundDataSettingChanged, intentFilter);
    intentFilter = new IntentFilter("android.intent.action.DEVICE_STORAGE_LOW");
    intentFilter.addAction("android.intent.action.DEVICE_STORAGE_OK");
    paramContext.registerReceiver(this.mStorageIntentReceiver, intentFilter);
    intentFilter = new IntentFilter("android.intent.action.ACTION_SHUTDOWN");
    intentFilter.setPriority(100);
    paramContext.registerReceiver(this.mShutdownIntentReceiver, intentFilter);
    intentFilter = new IntentFilter();
    intentFilter.addAction("virtual.android.intent.action.USER_REMOVED");
    intentFilter.addAction("virtual.android.intent.action.USER_ADDED");
    intentFilter.addAction("virtual.android.intent.action.USER_REMOVED");
    this.mContext.registerReceiver(this.mUserIntentReceiver, intentFilter);
    paramContext.registerReceiver(new SyncAlarmIntentReceiver(), new IntentFilter("android.content.syncmanager.SYNC_ALARM"));
    this.mPowerManager = (PowerManager)paramContext.getSystemService("power");
    this.mUserManager = VUserManager.get();
    this.mSyncStorageEngine.addStatusChangeListener(1, (ISyncStatusObserver)new ISyncStatusObserver.Stub() {
          public void onStatusChanged(int param1Int) {
            SyncManager.this.sendCheckAlarmsMessage();
          }
        });
    this.mSyncRandomOffsetMillis = this.mSyncStorageEngine.getSyncRandomOffset() * 1000;
  }
  
  private void clearBackoffSetting(SyncOperation paramSyncOperation) {
    this.mSyncStorageEngine.setBackoff(paramSyncOperation.account, paramSyncOperation.userId, paramSyncOperation.authority, -1L, -1L);
    synchronized (this.mSyncQueue) {
      this.mSyncQueue.onBackoffChanged(paramSyncOperation.account, paramSyncOperation.userId, paramSyncOperation.authority, 0L);
      return;
    } 
  }
  
  private boolean containsAccountAndUser(AccountAndUser[] paramArrayOfAccountAndUser, Account paramAccount, int paramInt) {
    boolean bool2;
    boolean bool1 = false;
    byte b = 0;
    while (true) {
      bool2 = bool1;
      if (b < paramArrayOfAccountAndUser.length) {
        if ((paramArrayOfAccountAndUser[b]).userId == paramInt && (paramArrayOfAccountAndUser[b]).account.equals(paramAccount)) {
          bool2 = true;
          break;
        } 
        b++;
        continue;
      } 
      break;
    } 
    return bool2;
  }
  
  private void doDatabaseCleanup() {
    for (VUserInfo vUserInfo : this.mUserManager.getUsers(true)) {
      if (vUserInfo.partial)
        continue; 
      Account[] arrayOfAccount = VAccountManagerService.get().getAccounts(vUserInfo.id, null);
      this.mSyncStorageEngine.doDatabaseCleanup(arrayOfAccount, vUserInfo.id);
    } 
  }
  
  private void ensureAlarmService() {
    if (this.mAlarmService == null)
      this.mAlarmService = (AlarmManager)this.mContext.getSystemService("alarm"); 
  }
  
  static String formatTime(long paramLong) {
    Time time = new Time();
    time.set(paramLong);
    return time.format("%Y-%m-%d %H:%M:%S");
  }
  
  private List<VUserInfo> getAllUsers() {
    return this.mUserManager.getUsers();
  }
  
  private ConnectivityManager getConnectivityManager() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mConnManagerDoNotUseDirectly : Landroid/net/ConnectivityManager;
    //   6: ifnonnull -> 26
    //   9: aload_0
    //   10: aload_0
    //   11: getfield mContext : Landroid/content/Context;
    //   14: ldc_w 'connectivity'
    //   17: invokevirtual getSystemService : (Ljava/lang/String;)Ljava/lang/Object;
    //   20: checkcast android/net/ConnectivityManager
    //   23: putfield mConnManagerDoNotUseDirectly : Landroid/net/ConnectivityManager;
    //   26: aload_0
    //   27: getfield mConnManagerDoNotUseDirectly : Landroid/net/ConnectivityManager;
    //   30: astore_1
    //   31: aload_0
    //   32: monitorexit
    //   33: aload_1
    //   34: areturn
    //   35: astore_1
    //   36: aload_0
    //   37: monitorexit
    //   38: aload_1
    //   39: athrow
    // Exception table:
    //   from	to	target	type
    //   2	26	35	finally
    //   26	33	35	finally
    //   36	38	35	finally
  }
  
  private String getLastFailureMessage(int paramInt) {
    switch (paramInt) {
      default:
        return "unknown";
      case 8:
        return "internal error";
      case 7:
        return "too many retries error";
      case 6:
        return "too many deletions error";
      case 5:
        return "conflict error";
      case 4:
        return "parse error";
      case 3:
        return "I/O error";
      case 2:
        return "authentication error";
      case 1:
        break;
    } 
    return "sync already in progress";
  }
  
  private void increaseBackoffSetting(SyncOperation paramSyncOperation) {
    long l2;
    long l1 = SystemClock.elapsedRealtime();
    Pair<Long, Long> pair = this.mSyncStorageEngine.getBackoff(paramSyncOperation.account, paramSyncOperation.userId, paramSyncOperation.authority);
    if (pair != null) {
      if (l1 < ((Long)pair.first).longValue()) {
        null = new StringBuilder();
        null.append("Still in backoff, do not increase it. Remaining: ");
        null.append((((Long)pair.first).longValue() - l1) / 1000L);
        null.append(" seconds.");
        Log.v("SyncManager", null.toString());
        return;
      } 
      l2 = ((Long)pair.second).longValue() * 2L;
    } else {
      l2 = -1L;
    } 
    long l3 = l2;
    if (l2 <= 0L)
      l3 = jitterize(30000L, 33000L); 
    if (l3 > 3600000L) {
      l2 = 3600000L;
    } else {
      l2 = l3;
    } 
    l3 = l1 + l2;
    this.mSyncStorageEngine.setBackoff(((SyncOperation)null).account, ((SyncOperation)null).userId, ((SyncOperation)null).authority, l3, l2);
    ((SyncOperation)null).backoff = Long.valueOf(l3);
    null.updateEffectiveRunTime();
    synchronized (this.mSyncQueue) {
      this.mSyncQueue.onBackoffChanged(((SyncOperation)null).account, ((SyncOperation)null).userId, ((SyncOperation)null).authority, l3);
      return;
    } 
  }
  
  private boolean isSyncStillActive(ActiveSyncContext paramActiveSyncContext) {
    Iterator<ActiveSyncContext> iterator = this.mActiveSyncContexts.iterator();
    while (iterator.hasNext()) {
      if ((ActiveSyncContext)iterator.next() == paramActiveSyncContext)
        return true; 
    } 
    return false;
  }
  
  private long jitterize(long paramLong1, long paramLong2) {
    Random random = new Random(SystemClock.elapsedRealtime());
    paramLong2 -= paramLong1;
    if (paramLong2 <= 2147483647L)
      return paramLong1 + random.nextInt((int)paramLong2); 
    throw new IllegalArgumentException("the difference between the maxValue and the minValue must be less than 2147483647");
  }
  
  private void onUserRemoved(int paramInt) {
    updateRunningAccounts();
    this.mSyncStorageEngine.doDatabaseCleanup(new Account[0], paramInt);
    synchronized (this.mSyncQueue) {
      this.mSyncQueue.removeUser(paramInt);
      return;
    } 
  }
  
  private void onUserStarting(int paramInt) {
    SyncQueue syncQueue;
    Account[] arrayOfAccount;
    this.mSyncAdapters.refreshServiceCache(null);
    updateRunningAccounts();
    synchronized (this.mSyncQueue) {
      this.mSyncQueue.addPendingOperations(paramInt);
      arrayOfAccount = VAccountManagerService.get().getAccounts(paramInt, null);
      int i = arrayOfAccount.length;
      for (byte b = 0; b < i; b++)
        scheduleSync(arrayOfAccount[b], paramInt, -8, null, null, 0L, 0L, true); 
      sendCheckAlarmsMessage();
      return;
    } 
  }
  
  private void onUserStopping(int paramInt) {
    updateRunningAccounts();
    cancelActiveSync(null, paramInt, null);
  }
  
  private boolean readDataConnectionState() {
    boolean bool;
    NetworkInfo networkInfo = getConnectivityManager().getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private void sendCancelSyncsMessage(Account paramAccount, int paramInt, String paramString) {
    Log.v("SyncManager", "sending MESSAGE_CANCEL");
    Message message = this.mSyncHandler.obtainMessage();
    message.what = 6;
    message.obj = Pair.create(paramAccount, paramString);
    message.arg1 = paramInt;
    this.mSyncHandler.sendMessage(message);
  }
  
  private void sendCheckAlarmsMessage() {
    Log.v("SyncManager", "sending MESSAGE_CHECK_ALARMS");
    this.mSyncHandler.removeMessages(3);
    this.mSyncHandler.sendEmptyMessage(3);
  }
  
  private void sendSyncAlarmMessage() {
    Log.v("SyncManager", "sending MESSAGE_SYNC_ALARM");
    this.mSyncHandler.sendEmptyMessage(2);
  }
  
  private void sendSyncFinishedOrCanceledMessage(ActiveSyncContext paramActiveSyncContext, SyncResult paramSyncResult) {
    Log.v("SyncManager", "sending MESSAGE_SYNC_FINISHED");
    Message message = this.mSyncHandler.obtainMessage();
    message.what = 1;
    message.obj = new SyncHandlerMessagePayload(paramActiveSyncContext, paramSyncResult);
    this.mSyncHandler.sendMessage(message);
  }
  
  private void setDelayUntilTime(SyncOperation paramSyncOperation, long paramLong) {
    paramLong *= 1000L;
    long l = System.currentTimeMillis();
    if (paramLong > l) {
      paramLong = SystemClock.elapsedRealtime() + paramLong - l;
    } else {
      paramLong = 0L;
    } 
    this.mSyncStorageEngine.setDelayUntilTime(paramSyncOperation.account, paramSyncOperation.userId, paramSyncOperation.authority, paramLong);
    synchronized (this.mSyncQueue) {
      this.mSyncQueue.onDelayUntilTimeChanged(paramSyncOperation.account, paramSyncOperation.authority, paramLong);
      return;
    } 
  }
  
  public void cancelActiveSync(Account paramAccount, int paramInt, String paramString) {
    sendCancelSyncsMessage(paramAccount, paramInt, paramString);
  }
  
  public void clearScheduledSyncOperations(Account paramAccount, int paramInt, String paramString) {
    synchronized (this.mSyncQueue) {
      this.mSyncQueue.remove(paramAccount, paramInt, paramString);
      this.mSyncStorageEngine.setBackoff(paramAccount, paramInt, paramString, -1L, -1L);
      return;
    } 
  }
  
  public int getIsSyncable(Account paramAccount, int paramInt, String paramString) {
    int i = this.mSyncStorageEngine.getIsSyncable(paramAccount, paramInt, paramString);
    VUserInfo vUserInfo = VUserManager.get().getUserInfo(paramInt);
    return (vUserInfo == null || !vUserInfo.isRestricted()) ? i : ((this.mSyncAdapters.getServiceInfo(paramAccount, paramString) == null) ? i : 0);
  }
  
  public SyncAdapterType[] getSyncAdapterTypes() {
    Collection<SyncAdaptersCache.SyncAdapterInfo> collection = this.mSyncAdapters.getAllServices();
    SyncAdapterType[] arrayOfSyncAdapterType = new SyncAdapterType[collection.size()];
    Iterator<SyncAdaptersCache.SyncAdapterInfo> iterator = collection.iterator();
    for (byte b = 0; iterator.hasNext(); b++)
      arrayOfSyncAdapterType[b] = ((SyncAdaptersCache.SyncAdapterInfo)iterator.next()).type; 
    return arrayOfSyncAdapterType;
  }
  
  public SyncStorageEngine getSyncStorageEngine() {
    return this.mSyncStorageEngine;
  }
  
  void maybeRescheduleSync(SyncResult paramSyncResult, SyncOperation paramSyncOperation) {
    StringBuilder stringBuilder1;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("encountered error(s) during the sync: ");
    stringBuilder2.append(paramSyncResult);
    stringBuilder2.append(", ");
    stringBuilder2.append(paramSyncOperation);
    Log.d("SyncManager", stringBuilder2.toString());
    paramSyncOperation = new SyncOperation(paramSyncOperation);
    if (paramSyncOperation.extras.getBoolean("ignore_backoff", false))
      paramSyncOperation.extras.remove("ignore_backoff"); 
    if (paramSyncOperation.extras.getBoolean("do_not_retry", false)) {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("not retrying sync operation because SYNC_EXTRAS_DO_NOT_RETRY was specified ");
      stringBuilder1.append(paramSyncOperation);
      Log.d("SyncManager", stringBuilder1.toString());
    } else if (paramSyncOperation.extras.getBoolean("upload", false) && !((SyncResult)stringBuilder1).syncAlreadyInProgress) {
      paramSyncOperation.extras.remove("upload");
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("retrying sync operation as a two-way sync because an upload-only sync encountered an error: ");
      stringBuilder1.append(paramSyncOperation);
      Log.d("SyncManager", stringBuilder1.toString());
      scheduleSyncOperation(paramSyncOperation);
    } else if (((SyncResult)stringBuilder1).tooManyRetries) {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("not retrying sync operation because it retried too many times: ");
      stringBuilder1.append(paramSyncOperation);
      Log.d("SyncManager", stringBuilder1.toString());
    } else if (stringBuilder1.madeSomeProgress()) {
      Log.d("SyncManager", "retrying sync operation because even though it had an error it achieved some success");
      scheduleSyncOperation(paramSyncOperation);
    } else if (((SyncResult)stringBuilder1).syncAlreadyInProgress) {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("retrying sync operation that failed because there was already a sync in progress: ");
      stringBuilder1.append(paramSyncOperation);
      Log.d("SyncManager", stringBuilder1.toString());
      scheduleSyncOperation(new SyncOperation(paramSyncOperation.account, paramSyncOperation.userId, paramSyncOperation.reason, paramSyncOperation.syncSource, paramSyncOperation.authority, paramSyncOperation.extras, 10000L, paramSyncOperation.flexTime, paramSyncOperation.backoff.longValue(), paramSyncOperation.delayUntil, paramSyncOperation.allowParallelSyncs));
    } else if (stringBuilder1.hasSoftError()) {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("retrying sync operation because it encountered a soft error: ");
      stringBuilder1.append(paramSyncOperation);
      Log.d("SyncManager", stringBuilder1.toString());
      scheduleSyncOperation(paramSyncOperation);
    } else {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("not retrying sync operation because the error is a hard error: ");
      stringBuilder1.append(paramSyncOperation);
      Log.d("SyncManager", stringBuilder1.toString());
    } 
  }
  
  public void scheduleLocalSync(Account paramAccount, int paramInt1, int paramInt2, String paramString) {
    Bundle bundle = new Bundle();
    bundle.putBoolean("upload", true);
    long l = LOCAL_SYNC_DELAY;
    scheduleSync(paramAccount, paramInt1, paramInt2, paramString, bundle, l, l * 2L, false);
  }
  
  public void scheduleSync(Account paramAccount, int paramInt1, int paramInt2, String paramString, Bundle paramBundle, long paramLong1, long paramLong2, boolean paramBoolean) {
    AccountAndUser[] arrayOfAccountAndUser;
    int i;
    String str1 = paramString;
    if (!this.mBootCompleted || getConnectivityManager().getBackgroundDataSetting()) {
      i = 1;
    } else {
      i = 0;
    } 
    if (paramBundle == null)
      paramBundle = new Bundle(); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("one-time sync for: ");
    stringBuilder.append(paramAccount);
    stringBuilder.append(" ");
    stringBuilder.append(paramBundle.toString());
    stringBuilder.append(" ");
    stringBuilder.append(str1);
    String str3 = stringBuilder.toString();
    String str2 = "SyncManager";
    Log.d("SyncManager", str3);
    if (Boolean.valueOf(paramBundle.getBoolean("expedited", false)).booleanValue())
      paramLong2 = -1L; 
    if (paramAccount != null && paramInt1 != -1) {
      AccountAndUser[] arrayOfAccountAndUser1 = new AccountAndUser[1];
      arrayOfAccountAndUser1[0] = new AccountAndUser(paramAccount, paramInt1);
      arrayOfAccountAndUser = arrayOfAccountAndUser1;
    } else {
      AccountAndUser[] arrayOfAccountAndUser1 = this.mRunningAccounts;
      arrayOfAccountAndUser = arrayOfAccountAndUser1;
      if (arrayOfAccountAndUser1.length == 0) {
        Log.v("SyncManager", "scheduleSync: no accounts configured, dropping");
        return;
      } 
    } 
    boolean bool1 = paramBundle.getBoolean("upload", false);
    boolean bool2 = paramBundle.getBoolean("force", false);
    if (bool2) {
      paramBundle.putBoolean("ignore_backoff", true);
      paramBundle.putBoolean("ignore_settings", true);
    } 
    boolean bool3 = paramBundle.getBoolean("ignore_settings", false);
    if (bool1) {
      paramInt1 = 1;
    } else if (bool2) {
      paramInt1 = 3;
    } else if (str1 == null) {
      paramInt1 = 2;
    } else {
      paramInt1 = 0;
    } 
    int j = arrayOfAccountAndUser.length;
    int k = 0;
    str1 = str2;
    int m = paramInt1;
    paramInt1 = k;
    k = i;
    while (true) {
      str2 = paramString;
      if (paramInt1 < j) {
        AccountAndUser accountAndUser = arrayOfAccountAndUser[paramInt1];
        HashSet<String> hashSet = new HashSet();
        Iterator<SyncAdaptersCache.SyncAdapterInfo> iterator2 = this.mSyncAdapters.getAllServices().iterator();
        while (iterator2.hasNext())
          hashSet.add(((SyncAdaptersCache.SyncAdapterInfo)iterator2.next()).type.authority); 
        if (str2 != null) {
          bool2 = hashSet.contains(str2);
          hashSet.clear();
          if (bool2)
            hashSet.add(str2); 
        } 
        iterator2 = (Iterator)hashSet.iterator();
        i = j;
        Bundle bundle2 = paramBundle;
        Iterator<SyncAdaptersCache.SyncAdapterInfo> iterator1 = iterator2;
        while (iterator1.hasNext()) {
          boolean bool4;
          long l2;
          String str = (String)iterator1.next();
          j = getIsSyncable(accountAndUser.account, accountAndUser.userId, str);
          if (j == 0)
            continue; 
          SyncAdaptersCache.SyncAdapterInfo syncAdapterInfo = this.mSyncAdapters.getServiceInfo(accountAndUser.account, str);
          if (syncAdapterInfo == null)
            continue; 
          bool2 = syncAdapterInfo.type.allowParallelSyncs();
          boolean bool = syncAdapterInfo.type.isAlwaysSyncable();
          if (j < 0 && bool) {
            this.mSyncStorageEngine.setIsSyncable(accountAndUser.account, accountAndUser.userId, str, 1);
            j = 1;
          } 
          if ((paramBoolean && j >= 0) || (!syncAdapterInfo.type.supportsUploading() && bool1))
            continue; 
          if (j < 0 || bool3 || (k != 0 && this.mSyncStorageEngine.getMasterSyncAutomatically(accountAndUser.userId) && this.mSyncStorageEngine.getSyncAutomatically(accountAndUser.account, accountAndUser.userId, str))) {
            bool4 = true;
          } else {
            bool4 = false;
          } 
          if (!bool4) {
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("scheduleSync: sync of ");
            stringBuilder1.append(accountAndUser);
            stringBuilder1.append(", ");
            stringBuilder1.append(str);
            stringBuilder1.append(" is not allowed, dropping request");
            Log.d(str1, stringBuilder1.toString());
            continue;
          } 
          Pair<Long, Long> pair = this.mSyncStorageEngine.getBackoff(accountAndUser.account, accountAndUser.userId, str);
          long l1 = this.mSyncStorageEngine.getDelayUntilTime(accountAndUser.account, accountAndUser.userId, str);
          if (pair != null) {
            l2 = ((Long)pair.first).longValue();
          } else {
            l2 = 0L;
          } 
          if (j < 0) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("initialize", true);
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("schedule initialisation Sync:, delay until ");
            stringBuilder1.append(l1);
            stringBuilder1.append(", run by ");
            stringBuilder1.append(0);
            stringBuilder1.append(", source ");
            stringBuilder1.append(m);
            stringBuilder1.append(", account ");
            stringBuilder1.append(accountAndUser);
            stringBuilder1.append(", authority ");
            stringBuilder1.append(str);
            stringBuilder1.append(", extras ");
            stringBuilder1.append(bundle);
            Log.v(str1, stringBuilder1.toString());
            scheduleSyncOperation(new SyncOperation(accountAndUser.account, accountAndUser.userId, paramInt2, m, str, bundle, 0L, 0L, l2, l1, bool2));
          } 
          if (!paramBoolean) {
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("scheduleSync: delay until ");
            stringBuilder1.append(l1);
            stringBuilder1.append(" run by ");
            stringBuilder1.append(paramLong2);
            stringBuilder1.append(" flex ");
            stringBuilder1.append(paramLong1);
            stringBuilder1.append(", source ");
            stringBuilder1.append(m);
            stringBuilder1.append(", account ");
            stringBuilder1.append(accountAndUser);
            stringBuilder1.append(", authority ");
            stringBuilder1.append(str);
            stringBuilder1.append(", extras ");
            Bundle bundle = bundle2;
            stringBuilder1.append(bundle);
            Log.v(str1, stringBuilder1.toString());
            scheduleSyncOperation(new SyncOperation(accountAndUser.account, accountAndUser.userId, paramInt2, m, str, bundle, paramLong2, paramLong1, l2, l1, bool2));
          } 
        } 
        paramInt1++;
        Bundle bundle1 = bundle2;
        j = i;
        continue;
      } 
      break;
    } 
  }
  
  public void scheduleSyncOperation(SyncOperation paramSyncOperation) {
    SyncQueue syncQueue;
    StringBuilder stringBuilder;
    synchronized (this.mSyncQueue) {
      boolean bool = this.mSyncQueue.add(paramSyncOperation);
      if (bool) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("scheduleSyncOperation: enqueued ");
        stringBuilder.append(paramSyncOperation);
        Log.v("SyncManager", stringBuilder.toString());
        sendCheckAlarmsMessage();
      } else {
        stringBuilder = new StringBuilder();
        stringBuilder.append("scheduleSyncOperation: dropping duplicate sync operation ");
        stringBuilder.append(paramSyncOperation);
        Log.v("SyncManager", stringBuilder.toString());
      } 
      return;
    } 
  }
  
  public void updateRunningAccounts() {
    this.mRunningAccounts = VAccountManagerService.get().getAllAccounts();
    if (this.mBootCompleted)
      doDatabaseCleanup(); 
    for (ActiveSyncContext activeSyncContext : this.mActiveSyncContexts) {
      if (!containsAccountAndUser(this.mRunningAccounts, activeSyncContext.mSyncOperation.account, activeSyncContext.mSyncOperation.userId)) {
        Log.d("SyncManager", "canceling sync since the account is no longer running");
        sendSyncFinishedOrCanceledMessage(activeSyncContext, null);
      } 
    } 
    sendCheckAlarmsMessage();
  }
  
  class ActiveSyncContext extends ISyncContext.Stub implements ServiceConnection, IBinder.DeathRecipient {
    boolean mBound;
    
    final long mHistoryRowId;
    
    boolean mIsLinkedToDeath = false;
    
    final long mStartTime;
    
    ISyncAdapter mSyncAdapter;
    
    VSyncInfo mSyncInfo;
    
    final SyncOperation mSyncOperation;
    
    long mTimeoutStartTime;
    
    public ActiveSyncContext(SyncOperation param1SyncOperation, long param1Long) {
      this.mSyncOperation = param1SyncOperation;
      this.mHistoryRowId = param1Long;
      this.mSyncAdapter = null;
      param1Long = SystemClock.elapsedRealtime();
      this.mStartTime = param1Long;
      this.mTimeoutStartTime = param1Long;
    }
    
    boolean bindToSyncAdapter(SyncAdaptersCache.SyncAdapterInfo param1SyncAdapterInfo, int param1Int) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("bindToSyncAdapter: ");
      stringBuilder.append(param1SyncAdapterInfo.serviceInfo);
      stringBuilder.append(", connection ");
      stringBuilder.append(this);
      Log.d("SyncManager", stringBuilder.toString());
      Intent intent = new Intent();
      intent.setAction("android.content.SyncAdapter");
      intent.setComponent(param1SyncAdapterInfo.componentName);
      this.mBound = true;
      boolean bool = VActivityManager.get().bindService(SyncManager.this.mContext, intent, this, 21, this.mSyncOperation.userId);
      if (!bool)
        this.mBound = false; 
      return bool;
    }
    
    public void binderDied() {
      SyncManager.this.sendSyncFinishedOrCanceledMessage(this, null);
    }
    
    protected void close() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("unBindFromSyncAdapter: connection ");
      stringBuilder.append(this);
      Log.d("SyncManager", stringBuilder.toString());
      if (this.mBound) {
        this.mBound = false;
        VActivityManager.get().unbindService(SyncManager.this.mContext, this);
      } 
    }
    
    public void onFinished(SyncResult param1SyncResult) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("onFinished: ");
      stringBuilder.append(this);
      Log.v("SyncManager", stringBuilder.toString());
      SyncManager.this.sendSyncFinishedOrCanceledMessage(this, param1SyncResult);
    }
    
    public void onServiceConnected(ComponentName param1ComponentName, IBinder param1IBinder) {
      Message message = SyncManager.this.mSyncHandler.obtainMessage();
      message.what = 4;
      message.obj = new SyncManager.ServiceConnectionData(this, ISyncAdapter.Stub.asInterface(param1IBinder));
      SyncManager.this.mSyncHandler.sendMessage(message);
    }
    
    public void onServiceDisconnected(ComponentName param1ComponentName) {
      Message message = SyncManager.this.mSyncHandler.obtainMessage();
      message.what = 5;
      message.obj = new SyncManager.ServiceConnectionData(this, null);
      SyncManager.this.mSyncHandler.sendMessage(message);
    }
    
    public void sendHeartbeat() {}
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      toString(stringBuilder);
      return stringBuilder.toString();
    }
    
    public void toString(StringBuilder param1StringBuilder) {
      param1StringBuilder.append("startTime ");
      param1StringBuilder.append(this.mStartTime);
      param1StringBuilder.append(", mTimeoutStartTime ");
      param1StringBuilder.append(this.mTimeoutStartTime);
      param1StringBuilder.append(", mHistoryRowId ");
      param1StringBuilder.append(this.mHistoryRowId);
      param1StringBuilder.append(", syncOperation ");
      param1StringBuilder.append(this.mSyncOperation);
    }
  }
  
  class ServiceConnectionData {
    public final SyncManager.ActiveSyncContext activeSyncContext;
    
    public final ISyncAdapter syncAdapter;
    
    ServiceConnectionData(SyncManager.ActiveSyncContext param1ActiveSyncContext, ISyncAdapter param1ISyncAdapter) {
      this.activeSyncContext = param1ActiveSyncContext;
      this.syncAdapter = param1ISyncAdapter;
    }
  }
  
  class SyncAlarmIntentReceiver extends BroadcastReceiver {
    public void onReceive(Context param1Context, Intent param1Intent) {
      SyncManager.this.sendSyncAlarmMessage();
    }
  }
  
  class SyncHandler extends Handler {
    private static final int MESSAGE_CANCEL = 6;
    
    private static final int MESSAGE_CHECK_ALARMS = 3;
    
    private static final int MESSAGE_SERVICE_CONNECTED = 4;
    
    private static final int MESSAGE_SERVICE_DISCONNECTED = 5;
    
    private static final int MESSAGE_SYNC_ALARM = 2;
    
    private static final int MESSAGE_SYNC_FINISHED = 1;
    
    private Long mAlarmScheduleTime = null;
    
    private List<Message> mBootQueue = new ArrayList<Message>();
    
    public final SyncNotificationInfo mSyncNotificationInfo = new SyncNotificationInfo();
    
    public final SyncManager.SyncTimeTracker mSyncTimeTracker = new SyncManager.SyncTimeTracker();
    
    public SyncHandler(Looper param1Looper) {
      super(param1Looper);
    }
    
    private void cancelActiveSyncLocked(Account param1Account, int param1Int, String param1String) {
      for (SyncManager.ActiveSyncContext activeSyncContext : new ArrayList(SyncManager.this.mActiveSyncContexts)) {
        if (activeSyncContext == null || (param1Account != null && !param1Account.equals(activeSyncContext.mSyncOperation.account)) || (param1String != null && !param1String.equals(activeSyncContext.mSyncOperation.authority)) || (param1Int != -1 && param1Int != activeSyncContext.mSyncOperation.userId))
          continue; 
        runSyncFinishedOrCanceledLocked((SyncResult)null, activeSyncContext);
      } 
    }
    
    private void closeActiveSyncContext(SyncManager.ActiveSyncContext param1ActiveSyncContext) {
      param1ActiveSyncContext.close();
      SyncManager.this.mActiveSyncContexts.remove(param1ActiveSyncContext);
      SyncManager.this.mSyncStorageEngine.removeActiveSync(param1ActiveSyncContext.mSyncInfo, param1ActiveSyncContext.mSyncOperation.userId);
    }
    
    private boolean dispatchSyncOperation(SyncOperation param1SyncOperation) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("dispatchSyncOperation: we are going to sync ");
      stringBuilder1.append(param1SyncOperation);
      Log.v("SyncManager", stringBuilder1.toString());
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("num active syncs: ");
      stringBuilder1.append(SyncManager.this.mActiveSyncContexts.size());
      Log.v("SyncManager", stringBuilder1.toString());
      Iterator<SyncManager.ActiveSyncContext> iterator = SyncManager.this.mActiveSyncContexts.iterator();
      while (iterator.hasNext())
        Log.v("SyncManager", ((SyncManager.ActiveSyncContext)iterator.next()).toString()); 
      SyncAdaptersCache.SyncAdapterInfo syncAdapterInfo = SyncManager.this.mSyncAdapters.getServiceInfo(param1SyncOperation.account, param1SyncOperation.authority);
      if (syncAdapterInfo == null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("can't find a sync adapter for ");
        stringBuilder.append(param1SyncOperation.authority);
        stringBuilder.append(", removing settings for it");
        Log.d("SyncManager", stringBuilder.toString());
        SyncManager.this.mSyncStorageEngine.removeAuthority(param1SyncOperation.account, param1SyncOperation.userId, param1SyncOperation.authority);
        return false;
      } 
      SyncManager.ActiveSyncContext activeSyncContext = new SyncManager.ActiveSyncContext(param1SyncOperation, insertStartSyncEvent(param1SyncOperation));
      activeSyncContext.mSyncInfo = SyncManager.this.mSyncStorageEngine.addActiveSync(activeSyncContext);
      SyncManager.this.mActiveSyncContexts.add(activeSyncContext);
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append("dispatchSyncOperation: starting ");
      stringBuilder2.append(activeSyncContext);
      Log.v("SyncManager", stringBuilder2.toString());
      if (!activeSyncContext.bindToSyncAdapter(syncAdapterInfo, param1SyncOperation.userId)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bind attempt failed to ");
        stringBuilder.append(syncAdapterInfo);
        Log.e("SyncManager", stringBuilder.toString());
        closeActiveSyncContext(activeSyncContext);
        return false;
      } 
      return true;
    }
    
    private void manageSyncAlarmLocked(long param1Long1, long param1Long2) {
      // Byte code:
      //   0: aload_0
      //   1: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   4: invokestatic access$400 : (Lcom/lody/virtual/server/content/SyncManager;)Z
      //   7: ifne -> 11
      //   10: return
      //   11: aload_0
      //   12: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   15: invokestatic access$000 : (Lcom/lody/virtual/server/content/SyncManager;)Z
      //   18: ifeq -> 22
      //   21: return
      //   22: aload_0
      //   23: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   26: invokestatic access$200 : (Lcom/lody/virtual/server/content/SyncManager;)Lcom/lody/virtual/server/content/SyncManager$SyncHandler;
      //   29: getfield mSyncNotificationInfo : Lcom/lody/virtual/server/content/SyncManager$SyncHandler$SyncNotificationInfo;
      //   32: getfield isActive : Z
      //   35: ifne -> 79
      //   38: aload_0
      //   39: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   42: invokestatic access$200 : (Lcom/lody/virtual/server/content/SyncManager;)Lcom/lody/virtual/server/content/SyncManager$SyncHandler;
      //   45: getfield mSyncNotificationInfo : Lcom/lody/virtual/server/content/SyncManager$SyncHandler$SyncNotificationInfo;
      //   48: getfield startTime : Ljava/lang/Long;
      //   51: ifnull -> 79
      //   54: aload_0
      //   55: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   58: invokestatic access$200 : (Lcom/lody/virtual/server/content/SyncManager;)Lcom/lody/virtual/server/content/SyncManager$SyncHandler;
      //   61: getfield mSyncNotificationInfo : Lcom/lody/virtual/server/content/SyncManager$SyncHandler$SyncNotificationInfo;
      //   64: getfield startTime : Ljava/lang/Long;
      //   67: invokevirtual longValue : ()J
      //   70: invokestatic access$2900 : ()J
      //   73: ladd
      //   74: lstore #5
      //   76: goto -> 84
      //   79: ldc2_w 9223372036854775807
      //   82: lstore #5
      //   84: aload_0
      //   85: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   88: getfield mActiveSyncContexts : Ljava/util/ArrayList;
      //   91: invokevirtual iterator : ()Ljava/util/Iterator;
      //   94: astore #7
      //   96: ldc2_w 9223372036854775807
      //   99: lstore #8
      //   101: aload #7
      //   103: invokeinterface hasNext : ()Z
      //   108: ifeq -> 182
      //   111: aload #7
      //   113: invokeinterface next : ()Ljava/lang/Object;
      //   118: checkcast com/lody/virtual/server/content/SyncManager$ActiveSyncContext
      //   121: getfield mTimeoutStartTime : J
      //   124: invokestatic access$2300 : ()J
      //   127: ladd
      //   128: lstore #10
      //   130: new java/lang/StringBuilder
      //   133: dup
      //   134: invokespecial <init> : ()V
      //   137: astore #12
      //   139: aload #12
      //   141: ldc_w 'manageSyncAlarm: active sync, mTimeoutStartTime + MAX is '
      //   144: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   147: pop
      //   148: aload #12
      //   150: lload #10
      //   152: invokevirtual append : (J)Ljava/lang/StringBuilder;
      //   155: pop
      //   156: ldc 'SyncManager'
      //   158: aload #12
      //   160: invokevirtual toString : ()Ljava/lang/String;
      //   163: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   166: pop
      //   167: lload #8
      //   169: lload #10
      //   171: lcmp
      //   172: ifle -> 101
      //   175: lload #10
      //   177: lstore #8
      //   179: goto -> 101
      //   182: new java/lang/StringBuilder
      //   185: dup
      //   186: invokespecial <init> : ()V
      //   189: astore #12
      //   191: aload #12
      //   193: ldc_w 'manageSyncAlarm: notificationTime is '
      //   196: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   199: pop
      //   200: aload #12
      //   202: lload #5
      //   204: invokevirtual append : (J)Ljava/lang/StringBuilder;
      //   207: pop
      //   208: ldc 'SyncManager'
      //   210: aload #12
      //   212: invokevirtual toString : ()Ljava/lang/String;
      //   215: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   218: pop
      //   219: new java/lang/StringBuilder
      //   222: dup
      //   223: invokespecial <init> : ()V
      //   226: astore #12
      //   228: aload #12
      //   230: ldc_w 'manageSyncAlarm: earliestTimeoutTime is '
      //   233: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   236: pop
      //   237: aload #12
      //   239: lload #8
      //   241: invokevirtual append : (J)Ljava/lang/StringBuilder;
      //   244: pop
      //   245: ldc 'SyncManager'
      //   247: aload #12
      //   249: invokevirtual toString : ()Ljava/lang/String;
      //   252: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   255: pop
      //   256: new java/lang/StringBuilder
      //   259: dup
      //   260: invokespecial <init> : ()V
      //   263: astore #12
      //   265: aload #12
      //   267: ldc_w 'manageSyncAlarm: nextPeriodicEventElapsedTime is '
      //   270: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   273: pop
      //   274: aload #12
      //   276: lload_1
      //   277: invokevirtual append : (J)Ljava/lang/StringBuilder;
      //   280: pop
      //   281: ldc 'SyncManager'
      //   283: aload #12
      //   285: invokevirtual toString : ()Ljava/lang/String;
      //   288: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   291: pop
      //   292: new java/lang/StringBuilder
      //   295: dup
      //   296: invokespecial <init> : ()V
      //   299: astore #12
      //   301: aload #12
      //   303: ldc_w 'manageSyncAlarm: nextPendingEventElapsedTime is '
      //   306: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   309: pop
      //   310: aload #12
      //   312: lload_3
      //   313: invokevirtual append : (J)Ljava/lang/StringBuilder;
      //   316: pop
      //   317: ldc 'SyncManager'
      //   319: aload #12
      //   321: invokevirtual toString : ()Ljava/lang/String;
      //   324: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   327: pop
      //   328: lload #5
      //   330: lload #8
      //   332: invokestatic min : (JJ)J
      //   335: lload_1
      //   336: invokestatic min : (JJ)J
      //   339: lload_3
      //   340: invokestatic min : (JJ)J
      //   343: lstore #5
      //   345: invokestatic elapsedRealtime : ()J
      //   348: lstore #10
      //   350: ldc2_w 30000
      //   353: lload #10
      //   355: ladd
      //   356: lstore_3
      //   357: lload #5
      //   359: lload_3
      //   360: lcmp
      //   361: ifge -> 422
      //   364: new java/lang/StringBuilder
      //   367: dup
      //   368: invokespecial <init> : ()V
      //   371: astore #12
      //   373: aload #12
      //   375: ldc_w 'manageSyncAlarm: the alarmTime is too small, '
      //   378: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   381: pop
      //   382: aload #12
      //   384: lload #5
      //   386: invokevirtual append : (J)Ljava/lang/StringBuilder;
      //   389: pop
      //   390: aload #12
      //   392: ldc_w ', setting to '
      //   395: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   398: pop
      //   399: aload #12
      //   401: lload_3
      //   402: invokevirtual append : (J)Ljava/lang/StringBuilder;
      //   405: pop
      //   406: ldc 'SyncManager'
      //   408: aload #12
      //   410: invokevirtual toString : ()Ljava/lang/String;
      //   413: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   416: pop
      //   417: lload_3
      //   418: lstore_1
      //   419: goto -> 497
      //   422: ldc2_w 7200000
      //   425: lload #10
      //   427: ladd
      //   428: lstore #8
      //   430: lload #5
      //   432: lstore_1
      //   433: lload #5
      //   435: lload #8
      //   437: lcmp
      //   438: ifle -> 497
      //   441: new java/lang/StringBuilder
      //   444: dup
      //   445: invokespecial <init> : ()V
      //   448: astore #12
      //   450: aload #12
      //   452: ldc_w 'manageSyncAlarm: the alarmTime is too large, '
      //   455: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   458: pop
      //   459: aload #12
      //   461: lload #5
      //   463: invokevirtual append : (J)Ljava/lang/StringBuilder;
      //   466: pop
      //   467: aload #12
      //   469: ldc_w ', setting to '
      //   472: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   475: pop
      //   476: aload #12
      //   478: lload_3
      //   479: invokevirtual append : (J)Ljava/lang/StringBuilder;
      //   482: pop
      //   483: ldc 'SyncManager'
      //   485: aload #12
      //   487: invokevirtual toString : ()Ljava/lang/String;
      //   490: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   493: pop
      //   494: lload #8
      //   496: lstore_1
      //   497: aload_0
      //   498: getfield mAlarmScheduleTime : Ljava/lang/Long;
      //   501: astore #12
      //   503: iconst_1
      //   504: istore #13
      //   506: iconst_0
      //   507: istore #14
      //   509: iconst_0
      //   510: istore #15
      //   512: aload #12
      //   514: ifnull -> 534
      //   517: lload #10
      //   519: aload #12
      //   521: invokevirtual longValue : ()J
      //   524: lcmp
      //   525: ifge -> 534
      //   528: iconst_1
      //   529: istore #16
      //   531: goto -> 537
      //   534: iconst_0
      //   535: istore #16
      //   537: lload_1
      //   538: ldc2_w 9223372036854775807
      //   541: lcmp
      //   542: ifeq -> 551
      //   545: iconst_1
      //   546: istore #17
      //   548: goto -> 554
      //   551: iconst_0
      //   552: istore #17
      //   554: iload #17
      //   556: ifeq -> 599
      //   559: iload #13
      //   561: istore #18
      //   563: iload #14
      //   565: istore #17
      //   567: iload #16
      //   569: ifeq -> 606
      //   572: iload #15
      //   574: istore #16
      //   576: lload_1
      //   577: aload_0
      //   578: getfield mAlarmScheduleTime : Ljava/lang/Long;
      //   581: invokevirtual longValue : ()J
      //   584: lcmp
      //   585: ifge -> 599
      //   588: iload #13
      //   590: istore #18
      //   592: iload #14
      //   594: istore #17
      //   596: goto -> 606
      //   599: iconst_0
      //   600: istore #18
      //   602: iload #16
      //   604: istore #17
      //   606: aload_0
      //   607: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   610: invokestatic access$3000 : (Lcom/lody/virtual/server/content/SyncManager;)V
      //   613: iload #18
      //   615: ifeq -> 733
      //   618: new java/lang/StringBuilder
      //   621: dup
      //   622: invokespecial <init> : ()V
      //   625: astore #12
      //   627: aload #12
      //   629: ldc_w 'requesting that the alarm manager wake us up at elapsed time '
      //   632: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   635: pop
      //   636: aload #12
      //   638: lload_1
      //   639: invokevirtual append : (J)Ljava/lang/StringBuilder;
      //   642: pop
      //   643: aload #12
      //   645: ldc_w ', now is '
      //   648: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   651: pop
      //   652: aload #12
      //   654: lload #10
      //   656: invokevirtual append : (J)Ljava/lang/StringBuilder;
      //   659: pop
      //   660: aload #12
      //   662: ldc_w ', '
      //   665: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   668: pop
      //   669: aload #12
      //   671: lload_1
      //   672: lload #10
      //   674: lsub
      //   675: ldc2_w 1000
      //   678: ldiv
      //   679: invokevirtual append : (J)Ljava/lang/StringBuilder;
      //   682: pop
      //   683: aload #12
      //   685: ldc_w ' secs from now'
      //   688: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   691: pop
      //   692: ldc 'SyncManager'
      //   694: aload #12
      //   696: invokevirtual toString : ()Ljava/lang/String;
      //   699: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   702: pop
      //   703: aload_0
      //   704: lload_1
      //   705: invokestatic valueOf : (J)Ljava/lang/Long;
      //   708: putfield mAlarmScheduleTime : Ljava/lang/Long;
      //   711: aload_0
      //   712: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   715: invokestatic access$3200 : (Lcom/lody/virtual/server/content/SyncManager;)Landroid/app/AlarmManager;
      //   718: iconst_2
      //   719: lload_1
      //   720: aload_0
      //   721: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   724: invokestatic access$3100 : (Lcom/lody/virtual/server/content/SyncManager;)Landroid/app/PendingIntent;
      //   727: invokevirtual setExact : (IJLandroid/app/PendingIntent;)V
      //   730: goto -> 760
      //   733: iload #17
      //   735: ifeq -> 760
      //   738: aload_0
      //   739: aconst_null
      //   740: putfield mAlarmScheduleTime : Ljava/lang/Long;
      //   743: aload_0
      //   744: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   747: invokestatic access$3200 : (Lcom/lody/virtual/server/content/SyncManager;)Landroid/app/AlarmManager;
      //   750: aload_0
      //   751: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   754: invokestatic access$3100 : (Lcom/lody/virtual/server/content/SyncManager;)Landroid/app/PendingIntent;
      //   757: invokevirtual cancel : (Landroid/app/PendingIntent;)V
      //   760: return
    }
    
    private void manageSyncNotificationLocked() {
      // Byte code:
      //   0: aload_0
      //   1: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   4: getfield mActiveSyncContexts : Ljava/util/ArrayList;
      //   7: invokevirtual isEmpty : ()Z
      //   10: ifeq -> 34
      //   13: aload_0
      //   14: getfield mSyncNotificationInfo : Lcom/lody/virtual/server/content/SyncManager$SyncHandler$SyncNotificationInfo;
      //   17: aconst_null
      //   18: putfield startTime : Ljava/lang/Long;
      //   21: aload_0
      //   22: getfield mSyncNotificationInfo : Lcom/lody/virtual/server/content/SyncManager$SyncHandler$SyncNotificationInfo;
      //   25: getfield isActive : Z
      //   28: istore_1
      //   29: iconst_0
      //   30: istore_2
      //   31: goto -> 162
      //   34: invokestatic elapsedRealtime : ()J
      //   37: lstore_3
      //   38: aload_0
      //   39: getfield mSyncNotificationInfo : Lcom/lody/virtual/server/content/SyncManager$SyncHandler$SyncNotificationInfo;
      //   42: getfield startTime : Ljava/lang/Long;
      //   45: ifnonnull -> 59
      //   48: aload_0
      //   49: getfield mSyncNotificationInfo : Lcom/lody/virtual/server/content/SyncManager$SyncHandler$SyncNotificationInfo;
      //   52: lload_3
      //   53: invokestatic valueOf : (J)Ljava/lang/Long;
      //   56: putfield startTime : Ljava/lang/Long;
      //   59: aload_0
      //   60: getfield mSyncNotificationInfo : Lcom/lody/virtual/server/content/SyncManager$SyncHandler$SyncNotificationInfo;
      //   63: getfield isActive : Z
      //   66: ifeq -> 74
      //   69: iconst_0
      //   70: istore_1
      //   71: goto -> 29
      //   74: lload_3
      //   75: aload_0
      //   76: getfield mSyncNotificationInfo : Lcom/lody/virtual/server/content/SyncManager$SyncHandler$SyncNotificationInfo;
      //   79: getfield startTime : Ljava/lang/Long;
      //   82: invokevirtual longValue : ()J
      //   85: invokestatic access$2900 : ()J
      //   88: ladd
      //   89: lcmp
      //   90: ifle -> 98
      //   93: iconst_1
      //   94: istore_2
      //   95: goto -> 100
      //   98: iconst_0
      //   99: istore_2
      //   100: iload_2
      //   101: ifeq -> 111
      //   104: iconst_0
      //   105: istore_1
      //   106: iconst_1
      //   107: istore_2
      //   108: goto -> 162
      //   111: aload_0
      //   112: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   115: getfield mActiveSyncContexts : Ljava/util/ArrayList;
      //   118: invokevirtual iterator : ()Ljava/util/Iterator;
      //   121: astore #5
      //   123: aload #5
      //   125: invokeinterface hasNext : ()Z
      //   130: ifeq -> 69
      //   133: aload #5
      //   135: invokeinterface next : ()Ljava/lang/Object;
      //   140: checkcast com/lody/virtual/server/content/SyncManager$ActiveSyncContext
      //   143: getfield mSyncOperation : Lcom/lody/virtual/server/content/SyncOperation;
      //   146: getfield extras : Landroid/os/Bundle;
      //   149: ldc_w 'force'
      //   152: iconst_0
      //   153: invokevirtual getBoolean : (Ljava/lang/String;Z)Z
      //   156: ifeq -> 123
      //   159: goto -> 104
      //   162: iload_1
      //   163: ifeq -> 182
      //   166: iload_2
      //   167: ifne -> 182
      //   170: aload_0
      //   171: invokespecial sendSyncStateIntent : ()V
      //   174: aload_0
      //   175: getfield mSyncNotificationInfo : Lcom/lody/virtual/server/content/SyncManager$SyncHandler$SyncNotificationInfo;
      //   178: iconst_0
      //   179: putfield isActive : Z
      //   182: iload_2
      //   183: ifeq -> 198
      //   186: aload_0
      //   187: invokespecial sendSyncStateIntent : ()V
      //   190: aload_0
      //   191: getfield mSyncNotificationInfo : Lcom/lody/virtual/server/content/SyncManager$SyncHandler$SyncNotificationInfo;
      //   194: iconst_1
      //   195: putfield isActive : Z
      //   198: return
    }
    
    private long maybeStartNextSyncLocked() {
      // Byte code:
      //   0: aload_0
      //   1: astore_1
      //   2: ldc 'SyncManager'
      //   4: ldc_w 'maybeStartNextSync'
      //   7: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   10: pop
      //   11: aload_1
      //   12: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   15: invokestatic access$400 : (Lcom/lody/virtual/server/content/SyncManager;)Z
      //   18: ifne -> 34
      //   21: ldc 'SyncManager'
      //   23: ldc_w 'maybeStartNextSync: no data connection, skipping'
      //   26: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   29: pop
      //   30: ldc2_w 9223372036854775807
      //   33: lreturn
      //   34: aload_1
      //   35: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   38: invokestatic access$000 : (Lcom/lody/virtual/server/content/SyncManager;)Z
      //   41: ifeq -> 57
      //   44: ldc 'SyncManager'
      //   46: ldc_w 'maybeStartNextSync: memory low, skipping'
      //   49: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   52: pop
      //   53: ldc2_w 9223372036854775807
      //   56: lreturn
      //   57: aload_1
      //   58: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   61: invokestatic access$1800 : (Lcom/lody/virtual/server/content/SyncManager;)[Lcom/lody/virtual/server/accounts/AccountAndUser;
      //   64: astore_2
      //   65: aload_2
      //   66: invokestatic access$2100 : ()[Lcom/lody/virtual/server/accounts/AccountAndUser;
      //   69: if_acmpne -> 85
      //   72: ldc 'SyncManager'
      //   74: ldc_w 'maybeStartNextSync: accounts not known, skipping'
      //   77: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   80: pop
      //   81: ldc2_w 9223372036854775807
      //   84: lreturn
      //   85: invokestatic elapsedRealtime : ()J
      //   88: lstore_3
      //   89: new java/util/ArrayList
      //   92: dup
      //   93: invokespecial <init> : ()V
      //   96: astore #5
      //   98: aload_1
      //   99: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   102: invokestatic access$600 : (Lcom/lody/virtual/server/content/SyncManager;)Lcom/lody/virtual/server/content/SyncQueue;
      //   105: astore #6
      //   107: aload #6
      //   109: monitorenter
      //   110: new java/lang/StringBuilder
      //   113: astore #7
      //   115: aload #7
      //   117: invokespecial <init> : ()V
      //   120: aload #7
      //   122: ldc_w 'build the operation array, syncQueue size is '
      //   125: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   128: pop
      //   129: aload #7
      //   131: aload_1
      //   132: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   135: invokestatic access$600 : (Lcom/lody/virtual/server/content/SyncManager;)Lcom/lody/virtual/server/content/SyncQueue;
      //   138: invokevirtual getOperations : ()Ljava/util/Collection;
      //   141: invokeinterface size : ()I
      //   146: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   149: pop
      //   150: ldc 'SyncManager'
      //   152: aload #7
      //   154: invokevirtual toString : ()Ljava/lang/String;
      //   157: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   160: pop
      //   161: aload_1
      //   162: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   165: invokestatic access$600 : (Lcom/lody/virtual/server/content/SyncManager;)Lcom/lody/virtual/server/content/SyncQueue;
      //   168: invokevirtual getOperations : ()Ljava/util/Collection;
      //   171: invokeinterface iterator : ()Ljava/util/Iterator;
      //   176: astore #7
      //   178: new java/util/HashSet
      //   181: astore #8
      //   183: aload #8
      //   185: invokespecial <init> : ()V
      //   188: aload #7
      //   190: invokeinterface hasNext : ()Z
      //   195: ifeq -> 372
      //   198: aload #7
      //   200: invokeinterface next : ()Ljava/lang/Object;
      //   205: checkcast com/lody/virtual/server/content/SyncOperation
      //   208: astore #9
      //   210: aload_1
      //   211: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   214: aload_2
      //   215: aload #9
      //   217: getfield account : Landroid/accounts/Account;
      //   220: aload #9
      //   222: getfield userId : I
      //   225: invokestatic access$2000 : (Lcom/lody/virtual/server/content/SyncManager;[Lcom/lody/virtual/server/accounts/AccountAndUser;Landroid/accounts/Account;I)Z
      //   228: ifne -> 266
      //   231: aload #7
      //   233: invokeinterface remove : ()V
      //   238: aload_1
      //   239: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   242: invokestatic access$700 : (Lcom/lody/virtual/server/content/SyncManager;)Lcom/lody/virtual/server/content/SyncStorageEngine;
      //   245: aload #9
      //   247: getfield pendingOperation : Lcom/lody/virtual/server/content/SyncStorageEngine$PendingOperation;
      //   250: invokevirtual deleteFromPending : (Lcom/lody/virtual/server/content/SyncStorageEngine$PendingOperation;)Z
      //   253: pop
      //   254: ldc 'SyncManager'
      //   256: ldc_w '    Dropping sync operation: account doesn't exist.'
      //   259: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   262: pop
      //   263: goto -> 188
      //   266: aload_1
      //   267: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   270: aload #9
      //   272: getfield account : Landroid/accounts/Account;
      //   275: aload #9
      //   277: getfield userId : I
      //   280: aload #9
      //   282: getfield authority : Ljava/lang/String;
      //   285: invokevirtual getIsSyncable : (Landroid/accounts/Account;ILjava/lang/String;)I
      //   288: ifne -> 326
      //   291: aload #7
      //   293: invokeinterface remove : ()V
      //   298: aload_1
      //   299: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   302: invokestatic access$700 : (Lcom/lody/virtual/server/content/SyncManager;)Lcom/lody/virtual/server/content/SyncStorageEngine;
      //   305: aload #9
      //   307: getfield pendingOperation : Lcom/lody/virtual/server/content/SyncStorageEngine$PendingOperation;
      //   310: invokevirtual deleteFromPending : (Lcom/lody/virtual/server/content/SyncStorageEngine$PendingOperation;)Z
      //   313: pop
      //   314: ldc 'SyncManager'
      //   316: ldc_w '    Dropping sync operation: isSyncable == 0.'
      //   319: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   322: pop
      //   323: goto -> 188
      //   326: aload_1
      //   327: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   330: invokestatic access$2200 : (Lcom/lody/virtual/server/content/SyncManager;)Lcom/lody/virtual/os/VUserManager;
      //   333: aload #9
      //   335: getfield userId : I
      //   338: invokevirtual getUserInfo : (I)Lcom/lody/virtual/os/VUserInfo;
      //   341: ifnonnull -> 360
      //   344: aload #8
      //   346: aload #9
      //   348: getfield userId : I
      //   351: invokestatic valueOf : (I)Ljava/lang/Integer;
      //   354: invokeinterface add : (Ljava/lang/Object;)Z
      //   359: pop
      //   360: ldc 'SyncManager'
      //   362: ldc_w '    Dropping sync operation: user not running.'
      //   365: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   368: pop
      //   369: goto -> 188
      //   372: aload #8
      //   374: invokeinterface iterator : ()Ljava/util/Iterator;
      //   379: astore #7
      //   381: aload #7
      //   383: invokeinterface hasNext : ()Z
      //   388: ifeq -> 433
      //   391: aload #7
      //   393: invokeinterface next : ()Ljava/lang/Object;
      //   398: checkcast java/lang/Integer
      //   401: astore_2
      //   402: aload_1
      //   403: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   406: invokestatic access$2200 : (Lcom/lody/virtual/server/content/SyncManager;)Lcom/lody/virtual/os/VUserManager;
      //   409: aload_2
      //   410: invokevirtual intValue : ()I
      //   413: invokevirtual getUserInfo : (I)Lcom/lody/virtual/os/VUserInfo;
      //   416: ifnonnull -> 381
      //   419: aload_1
      //   420: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   423: aload_2
      //   424: invokevirtual intValue : ()I
      //   427: invokestatic access$800 : (Lcom/lody/virtual/server/content/SyncManager;I)V
      //   430: goto -> 381
      //   433: aload #6
      //   435: monitorexit
      //   436: new java/lang/StringBuilder
      //   439: dup
      //   440: invokespecial <init> : ()V
      //   443: astore_1
      //   444: aload_1
      //   445: ldc_w 'sort the candidate operations, size '
      //   448: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   451: pop
      //   452: aload_1
      //   453: aload #5
      //   455: invokevirtual size : ()I
      //   458: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   461: pop
      //   462: ldc 'SyncManager'
      //   464: aload_1
      //   465: invokevirtual toString : ()Ljava/lang/String;
      //   468: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   471: pop
      //   472: aload #5
      //   474: invokestatic sort : (Ljava/util/List;)V
      //   477: ldc 'SyncManager'
      //   479: ldc_w 'dispatch all ready sync operations'
      //   482: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   485: pop
      //   486: aload #5
      //   488: invokevirtual size : ()I
      //   491: istore #10
      //   493: iconst_0
      //   494: istore #11
      //   496: iload #11
      //   498: iload #10
      //   500: if_icmpge -> 1369
      //   503: aload #5
      //   505: iload #11
      //   507: invokevirtual get : (I)Ljava/lang/Object;
      //   510: checkcast com/lody/virtual/server/content/SyncOperation
      //   513: astore #9
      //   515: aload #9
      //   517: invokevirtual isInitialization : ()Z
      //   520: istore #12
      //   522: aload_0
      //   523: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   526: getfield mActiveSyncContexts : Ljava/util/ArrayList;
      //   529: invokevirtual iterator : ()Ljava/util/Iterator;
      //   532: astore #13
      //   534: aconst_null
      //   535: astore_2
      //   536: aconst_null
      //   537: astore_1
      //   538: iconst_0
      //   539: istore #14
      //   541: iconst_0
      //   542: istore #15
      //   544: aconst_null
      //   545: astore #6
      //   547: aload #13
      //   549: invokeinterface hasNext : ()Z
      //   554: ifeq -> 754
      //   557: aload #13
      //   559: invokeinterface next : ()Ljava/lang/Object;
      //   564: checkcast com/lody/virtual/server/content/SyncManager$ActiveSyncContext
      //   567: astore #7
      //   569: aload #7
      //   571: getfield mSyncOperation : Lcom/lody/virtual/server/content/SyncOperation;
      //   574: astore #16
      //   576: aload #16
      //   578: invokevirtual isInitialization : ()Z
      //   581: ifeq -> 590
      //   584: iinc #14, 1
      //   587: goto -> 624
      //   590: iinc #15, 1
      //   593: aload #16
      //   595: invokevirtual isExpedited : ()Z
      //   598: ifne -> 624
      //   601: aload_2
      //   602: ifnull -> 618
      //   605: aload_2
      //   606: getfield mStartTime : J
      //   609: aload #7
      //   611: getfield mStartTime : J
      //   614: lcmp
      //   615: ifle -> 624
      //   618: aload #7
      //   620: astore_2
      //   621: goto -> 624
      //   624: aload #16
      //   626: getfield account : Landroid/accounts/Account;
      //   629: getfield type : Ljava/lang/String;
      //   632: aload #9
      //   634: getfield account : Landroid/accounts/Account;
      //   637: getfield type : Ljava/lang/String;
      //   640: invokevirtual equals : (Ljava/lang/Object;)Z
      //   643: ifeq -> 711
      //   646: aload #16
      //   648: getfield authority : Ljava/lang/String;
      //   651: aload #9
      //   653: getfield authority : Ljava/lang/String;
      //   656: invokevirtual equals : (Ljava/lang/Object;)Z
      //   659: ifeq -> 711
      //   662: aload #16
      //   664: getfield userId : I
      //   667: aload #9
      //   669: getfield userId : I
      //   672: if_icmpne -> 711
      //   675: aload #16
      //   677: getfield allowParallelSyncs : Z
      //   680: ifeq -> 705
      //   683: aload #16
      //   685: getfield account : Landroid/accounts/Account;
      //   688: getfield name : Ljava/lang/String;
      //   691: aload #9
      //   693: getfield account : Landroid/accounts/Account;
      //   696: getfield name : Ljava/lang/String;
      //   699: invokevirtual equals : (Ljava/lang/Object;)Z
      //   702: ifeq -> 711
      //   705: aload #7
      //   707: astore_1
      //   708: goto -> 751
      //   711: aload #6
      //   713: astore #8
      //   715: iload #12
      //   717: aload #16
      //   719: invokevirtual isInitialization : ()Z
      //   722: if_icmpne -> 747
      //   725: aload #6
      //   727: astore #8
      //   729: aload #7
      //   731: getfield mStartTime : J
      //   734: invokestatic access$2300 : ()J
      //   737: ladd
      //   738: lload_3
      //   739: lcmp
      //   740: ifge -> 747
      //   743: aload #7
      //   745: astore #8
      //   747: aload #8
      //   749: astore #6
      //   751: goto -> 547
      //   754: new java/lang/StringBuilder
      //   757: dup
      //   758: invokespecial <init> : ()V
      //   761: astore #7
      //   763: aload #7
      //   765: ldc_w 'candidate '
      //   768: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   771: pop
      //   772: aload #7
      //   774: iload #11
      //   776: iconst_1
      //   777: iadd
      //   778: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   781: pop
      //   782: aload #7
      //   784: ldc_w ' of '
      //   787: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   790: pop
      //   791: aload #7
      //   793: iload #10
      //   795: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   798: pop
      //   799: aload #7
      //   801: ldc_w ': '
      //   804: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   807: pop
      //   808: aload #7
      //   810: aload #9
      //   812: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   815: pop
      //   816: ldc 'SyncManager'
      //   818: aload #7
      //   820: invokevirtual toString : ()Ljava/lang/String;
      //   823: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   826: pop
      //   827: new java/lang/StringBuilder
      //   830: dup
      //   831: invokespecial <init> : ()V
      //   834: astore #7
      //   836: aload #7
      //   838: ldc_w '  numActiveInit='
      //   841: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   844: pop
      //   845: aload #7
      //   847: iload #14
      //   849: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   852: pop
      //   853: aload #7
      //   855: ldc_w ', numActiveRegular='
      //   858: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   861: pop
      //   862: aload #7
      //   864: iload #15
      //   866: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   869: pop
      //   870: ldc 'SyncManager'
      //   872: aload #7
      //   874: invokevirtual toString : ()Ljava/lang/String;
      //   877: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   880: pop
      //   881: new java/lang/StringBuilder
      //   884: dup
      //   885: invokespecial <init> : ()V
      //   888: astore #7
      //   890: aload #7
      //   892: ldc_w '  longRunning: '
      //   895: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   898: pop
      //   899: aload #7
      //   901: aload #6
      //   903: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   906: pop
      //   907: ldc 'SyncManager'
      //   909: aload #7
      //   911: invokevirtual toString : ()Ljava/lang/String;
      //   914: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   917: pop
      //   918: new java/lang/StringBuilder
      //   921: dup
      //   922: invokespecial <init> : ()V
      //   925: astore #7
      //   927: aload #7
      //   929: ldc_w '  conflict: '
      //   932: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   935: pop
      //   936: aload #7
      //   938: aload_1
      //   939: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   942: pop
      //   943: ldc 'SyncManager'
      //   945: aload #7
      //   947: invokevirtual toString : ()Ljava/lang/String;
      //   950: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   953: pop
      //   954: new java/lang/StringBuilder
      //   957: dup
      //   958: invokespecial <init> : ()V
      //   961: astore #7
      //   963: aload #7
      //   965: ldc_w '  oldestNonExpeditedRegular: '
      //   968: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   971: pop
      //   972: aload #7
      //   974: aload_2
      //   975: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   978: pop
      //   979: ldc 'SyncManager'
      //   981: aload #7
      //   983: invokevirtual toString : ()Ljava/lang/String;
      //   986: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   989: pop
      //   990: iconst_1
      //   991: istore #17
      //   993: iload #12
      //   995: ifeq -> 1013
      //   998: iload #14
      //   1000: invokestatic access$2400 : ()I
      //   1003: if_icmpge -> 1028
      //   1006: iload #17
      //   1008: istore #15
      //   1010: goto -> 1031
      //   1013: iload #15
      //   1015: invokestatic access$2500 : ()I
      //   1018: if_icmpge -> 1028
      //   1021: iload #17
      //   1023: istore #15
      //   1025: goto -> 1031
      //   1028: iconst_0
      //   1029: istore #15
      //   1031: aload_1
      //   1032: ifnull -> 1172
      //   1035: iload #12
      //   1037: ifeq -> 1097
      //   1040: aload_1
      //   1041: getfield mSyncOperation : Lcom/lody/virtual/server/content/SyncOperation;
      //   1044: invokevirtual isInitialization : ()Z
      //   1047: ifne -> 1097
      //   1050: iload #14
      //   1052: invokestatic access$2400 : ()I
      //   1055: if_icmpge -> 1097
      //   1058: new java/lang/StringBuilder
      //   1061: dup
      //   1062: invokespecial <init> : ()V
      //   1065: astore #6
      //   1067: aload #6
      //   1069: ldc_w 'canceling and rescheduling sync since an initialization takes higher priority, '
      //   1072: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1075: pop
      //   1076: aload #6
      //   1078: aload_1
      //   1079: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   1082: pop
      //   1083: ldc 'SyncManager'
      //   1085: aload #6
      //   1087: invokevirtual toString : ()Ljava/lang/String;
      //   1090: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   1093: pop
      //   1094: goto -> 1163
      //   1097: aload #9
      //   1099: getfield expedited : Z
      //   1102: ifeq -> 1169
      //   1105: aload_1
      //   1106: getfield mSyncOperation : Lcom/lody/virtual/server/content/SyncOperation;
      //   1109: getfield expedited : Z
      //   1112: ifne -> 1169
      //   1115: iload #12
      //   1117: aload_1
      //   1118: getfield mSyncOperation : Lcom/lody/virtual/server/content/SyncOperation;
      //   1121: invokevirtual isInitialization : ()Z
      //   1124: if_icmpne -> 1169
      //   1127: new java/lang/StringBuilder
      //   1130: dup
      //   1131: invokespecial <init> : ()V
      //   1134: astore #6
      //   1136: aload #6
      //   1138: ldc_w 'canceling and rescheduling sync since an expedited takes higher priority, '
      //   1141: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1144: pop
      //   1145: aload #6
      //   1147: aload_1
      //   1148: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   1151: pop
      //   1152: ldc 'SyncManager'
      //   1154: aload #6
      //   1156: invokevirtual toString : ()Ljava/lang/String;
      //   1159: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   1162: pop
      //   1163: aload_1
      //   1164: astore #6
      //   1166: goto -> 1289
      //   1169: goto -> 1363
      //   1172: iload #15
      //   1174: ifeq -> 1183
      //   1177: aconst_null
      //   1178: astore #6
      //   1180: goto -> 1289
      //   1183: aload #9
      //   1185: invokevirtual isExpedited : ()Z
      //   1188: ifeq -> 1238
      //   1191: aload_2
      //   1192: ifnull -> 1238
      //   1195: iload #12
      //   1197: ifne -> 1238
      //   1200: new java/lang/StringBuilder
      //   1203: dup
      //   1204: invokespecial <init> : ()V
      //   1207: astore_1
      //   1208: aload_1
      //   1209: ldc_w 'canceling and rescheduling sync since an expedited is ready to run, '
      //   1212: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1215: pop
      //   1216: aload_1
      //   1217: aload_2
      //   1218: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   1221: pop
      //   1222: ldc 'SyncManager'
      //   1224: aload_1
      //   1225: invokevirtual toString : ()Ljava/lang/String;
      //   1228: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   1231: pop
      //   1232: aload_2
      //   1233: astore #6
      //   1235: goto -> 1289
      //   1238: aload #6
      //   1240: ifnull -> 1169
      //   1243: iload #12
      //   1245: aload #6
      //   1247: getfield mSyncOperation : Lcom/lody/virtual/server/content/SyncOperation;
      //   1250: invokevirtual isInitialization : ()Z
      //   1253: if_icmpne -> 1169
      //   1256: new java/lang/StringBuilder
      //   1259: dup
      //   1260: invokespecial <init> : ()V
      //   1263: astore_1
      //   1264: aload_1
      //   1265: ldc_w 'canceling and rescheduling sync since it ran roo long, '
      //   1268: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1271: pop
      //   1272: aload_1
      //   1273: aload #6
      //   1275: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   1278: pop
      //   1279: ldc 'SyncManager'
      //   1281: aload_1
      //   1282: invokevirtual toString : ()Ljava/lang/String;
      //   1285: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   1288: pop
      //   1289: aload #6
      //   1291: ifnull -> 1318
      //   1294: aload_0
      //   1295: astore_1
      //   1296: aload_1
      //   1297: aconst_null
      //   1298: aload #6
      //   1300: invokespecial runSyncFinishedOrCanceledLocked : (Landroid/content/SyncResult;Lcom/lody/virtual/server/content/SyncManager$ActiveSyncContext;)V
      //   1303: aload_1
      //   1304: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   1307: aload #6
      //   1309: getfield mSyncOperation : Lcom/lody/virtual/server/content/SyncOperation;
      //   1312: invokevirtual scheduleSyncOperation : (Lcom/lody/virtual/server/content/SyncOperation;)V
      //   1315: goto -> 1318
      //   1318: aload_0
      //   1319: astore_1
      //   1320: aload_1
      //   1321: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   1324: invokestatic access$600 : (Lcom/lody/virtual/server/content/SyncManager;)Lcom/lody/virtual/server/content/SyncQueue;
      //   1327: astore #6
      //   1329: aload #6
      //   1331: monitorenter
      //   1332: aload_1
      //   1333: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   1336: invokestatic access$600 : (Lcom/lody/virtual/server/content/SyncManager;)Lcom/lody/virtual/server/content/SyncQueue;
      //   1339: aload #9
      //   1341: invokevirtual remove : (Lcom/lody/virtual/server/content/SyncOperation;)V
      //   1344: aload #6
      //   1346: monitorexit
      //   1347: aload_1
      //   1348: aload #9
      //   1350: invokespecial dispatchSyncOperation : (Lcom/lody/virtual/server/content/SyncOperation;)Z
      //   1353: pop
      //   1354: goto -> 1363
      //   1357: astore_1
      //   1358: aload #6
      //   1360: monitorexit
      //   1361: aload_1
      //   1362: athrow
      //   1363: iinc #11, 1
      //   1366: goto -> 496
      //   1369: ldc2_w 9223372036854775807
      //   1372: lreturn
      //   1373: astore_1
      //   1374: aload #6
      //   1376: monitorexit
      //   1377: aload_1
      //   1378: athrow
      // Exception table:
      //   from	to	target	type
      //   110	188	1373	finally
      //   188	263	1373	finally
      //   266	323	1373	finally
      //   326	360	1373	finally
      //   360	369	1373	finally
      //   372	381	1373	finally
      //   381	430	1373	finally
      //   433	436	1373	finally
      //   1332	1347	1357	finally
      //   1358	1361	1357	finally
      //   1374	1377	1373	finally
    }
    
    private void runBoundToSyncAdapter(SyncManager.ActiveSyncContext param1ActiveSyncContext, ISyncAdapter param1ISyncAdapter) {
      param1ActiveSyncContext.mSyncAdapter = param1ISyncAdapter;
      SyncOperation syncOperation = param1ActiveSyncContext.mSyncOperation;
      try {
        param1ActiveSyncContext.mIsLinkedToDeath = true;
        param1ISyncAdapter.asBinder().linkToDeath(param1ActiveSyncContext, 0);
        param1ISyncAdapter.startSync((ISyncContext)param1ActiveSyncContext, syncOperation.authority, syncOperation.account, syncOperation.extras);
      } catch (RemoteException remoteException) {
        Log.d("SyncManager", "maybeStartNextSync: caught a RemoteException, rescheduling", (Throwable)remoteException);
        closeActiveSyncContext(param1ActiveSyncContext);
        SyncManager.this.increaseBackoffSetting(syncOperation);
        SyncManager.this.scheduleSyncOperation(new SyncOperation(syncOperation));
      } catch (RuntimeException runtimeException) {
        closeActiveSyncContext(param1ActiveSyncContext);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Caught RuntimeException while starting the sync ");
        stringBuilder.append(syncOperation);
        Log.e("SyncManager", stringBuilder.toString(), runtimeException);
      } 
    }
    
    private void runSyncFinishedOrCanceledLocked(SyncResult param1SyncResult, SyncManager.ActiveSyncContext param1ActiveSyncContext) {
      String str;
      if (param1ActiveSyncContext.mIsLinkedToDeath) {
        param1ActiveSyncContext.mSyncAdapter.asBinder().unlinkToDeath(param1ActiveSyncContext, 0);
        param1ActiveSyncContext.mIsLinkedToDeath = false;
      } 
      closeActiveSyncContext(param1ActiveSyncContext);
      SyncOperation syncOperation = param1ActiveSyncContext.mSyncOperation;
      long l1 = SystemClock.elapsedRealtime();
      long l2 = param1ActiveSyncContext.mStartTime;
      if (param1SyncResult != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("runSyncFinishedOrCanceled [finished]: ");
        stringBuilder.append(syncOperation);
        stringBuilder.append(", result ");
        stringBuilder.append(param1SyncResult);
        Log.v("SyncManager", stringBuilder.toString());
        if (!param1SyncResult.hasError()) {
          SyncManager.this.clearBackoffSetting(syncOperation);
          str = "success";
        } else {
          stringBuilder = new StringBuilder();
          stringBuilder.append("failed sync operation ");
          stringBuilder.append(syncOperation);
          stringBuilder.append(", ");
          stringBuilder.append(param1SyncResult);
          Log.d("SyncManager", stringBuilder.toString());
          if (!param1SyncResult.syncAlreadyInProgress)
            SyncManager.this.increaseBackoffSetting(syncOperation); 
          SyncManager.this.maybeRescheduleSync(param1SyncResult, syncOperation);
          str = ContentResolverCompat.syncErrorToString(syncResultToErrorNumber(param1SyncResult));
        } 
        SyncManager.this.setDelayUntilTime(syncOperation, param1SyncResult.delayUntil);
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("runSyncFinishedOrCanceled [canceled]: ");
        stringBuilder.append(syncOperation);
        Log.v("SyncManager", stringBuilder.toString());
        if (param1ActiveSyncContext.mSyncAdapter != null)
          try {
            param1ActiveSyncContext.mSyncAdapter.cancelSync((ISyncContext)param1ActiveSyncContext);
          } catch (RemoteException remoteException) {} 
        str = "canceled";
      } 
      stopSyncEvent(param1ActiveSyncContext.mHistoryRowId, syncOperation, str, 0, 0, l1 - l2);
      if (param1SyncResult != null && param1SyncResult.fullSyncRequested)
        SyncManager.this.scheduleSyncOperation(new SyncOperation(syncOperation.account, syncOperation.userId, syncOperation.reason, syncOperation.syncSource, syncOperation.authority, new Bundle(), 0L, 0L, syncOperation.backoff.longValue(), syncOperation.delayUntil, syncOperation.allowParallelSyncs)); 
    }
    
    private long scheduleReadyPeriodicSyncs() {
      SyncHandler syncHandler = this;
      String str = "SyncManager";
      Log.v("SyncManager", "scheduleReadyPeriodicSyncs");
      if (!SyncManager.this.getConnectivityManager().getBackgroundDataSetting())
        return Long.MAX_VALUE; 
      AccountAndUser[] arrayOfAccountAndUser = SyncManager.this.mRunningAccounts;
      long l1 = System.currentTimeMillis();
      long l2 = SyncManager.this.mSyncRandomOffsetMillis;
      long l3 = 0L;
      if (0L < l1 - l2) {
        l2 = l1 - SyncManager.this.mSyncRandomOffsetMillis;
      } else {
        l2 = 0L;
      } 
      Iterator<Pair<SyncStorageEngine.AuthorityInfo, SyncStatusInfo>> iterator = SyncManager.this.mSyncStorageEngine.getCopyOfAllAuthoritiesWithSyncStatus().iterator();
      for (long l4 = Long.MAX_VALUE;; l4 = l) {
        long l;
        SyncHandler syncHandler1 = this;
        if (iterator.hasNext()) {
          StringBuilder stringBuilder;
          Pair pair = iterator.next();
          SyncStorageEngine.AuthorityInfo authorityInfo = (SyncStorageEngine.AuthorityInfo)pair.first;
          SyncStatusInfo syncStatusInfo = (SyncStatusInfo)pair.second;
          if (TextUtils.isEmpty(authorityInfo.authority)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Got an empty provider string. Skipping: ");
            stringBuilder.append(authorityInfo);
            Log.e(str, stringBuilder.toString());
          } else if (!SyncManager.this.containsAccountAndUser(arrayOfAccountAndUser, authorityInfo.account, authorityInfo.userId) || !SyncManager.this.mSyncStorageEngine.getMasterSyncAutomatically(authorityInfo.userId) || !SyncManager.this.mSyncStorageEngine.getSyncAutomatically(authorityInfo.account, authorityInfo.userId, authorityInfo.authority) || SyncManager.this.getIsSyncable(authorityInfo.account, authorityInfo.userId, authorityInfo.authority) == 0) {
            l3 = 0L;
          } else {
            int i = authorityInfo.periodicSyncs.size();
            byte b = 0;
            SyncStatusInfo syncStatusInfo1 = syncStatusInfo;
            l3 = l4;
            l4 = l2;
            l2 = l1;
            while (b < i) {
              boolean bool;
              PeriodicSync periodicSync = authorityInfo.periodicSyncs.get(b);
              Bundle bundle = periodicSync.extras;
              long l6 = periodicSync.period * 1000L;
              long l7 = PeriodicSync.flexTime.get(periodicSync) * 1000L;
              if (l6 <= 0L)
                continue; 
              l1 = syncStatusInfo1.getPeriodicSyncTime(b);
              long l8 = l6 - l4 % l6;
              long l9 = l2 - l1;
              if (l8 <= l7 && l9 > l6 - l7) {
                bool = true;
              } else {
                bool = false;
              } 
              StringBuilder stringBuilder1 = new StringBuilder();
              stringBuilder1.append("sync: ");
              stringBuilder1.append(b);
              stringBuilder1.append(" for ");
              stringBuilder1.append(authorityInfo.authority);
              stringBuilder1.append(". period: ");
              stringBuilder1.append(l6);
              stringBuilder1.append(" flex: ");
              stringBuilder1.append(l7);
              stringBuilder1.append(" remaining: ");
              stringBuilder1.append(l8);
              stringBuilder1.append(" time_since_last: ");
              stringBuilder1.append(l9);
              stringBuilder1.append(" last poll absol: ");
              stringBuilder1.append(l1);
              stringBuilder1.append(" shifted now: ");
              stringBuilder1.append(l4);
              stringBuilder1.append(" run_early: ");
              stringBuilder1.append(bool);
              Log.v(str, stringBuilder1.toString());
              if (bool || l8 == l6 || l1 > l2 || l9 >= l6) {
                Pair<Long, Long> pair1 = SyncManager.this.mSyncStorageEngine.getBackoff(authorityInfo.account, authorityInfo.userId, authorityInfo.authority);
                SyncAdaptersCache.SyncAdapterInfo syncAdapterInfo = SyncManager.this.mSyncAdapters.getServiceInfo(authorityInfo.account, authorityInfo.authority);
                if (syncAdapterInfo == null)
                  continue; 
                SyncManager.this.mSyncStorageEngine.setPeriodicSyncTime(authorityInfo.ident, authorityInfo.periodicSyncs.get(b), l2);
                SyncManager syncManager = SyncManager.this;
                Account account = authorityInfo.account;
                int j = authorityInfo.userId;
                String str1 = authorityInfo.authority;
                if (pair1 != null) {
                  l1 = ((Long)pair1.first).longValue();
                } else {
                  l1 = 0L;
                } 
                syncManager.scheduleSyncOperation(new SyncOperation(account, j, -4, 4, str1, bundle, 0L, 0L, l1, SyncManager.this.mSyncStorageEngine.getDelayUntilTime(authorityInfo.account, authorityInfo.userId, authorityInfo.authority), syncAdapterInfo.type.allowParallelSyncs()));
              } 
              l1 = l2;
              if (bool) {
                l8 = l1 + l6 + l8;
              } else {
                l8 = l1 + l8;
              } 
              l2 = l1;
              if (l8 < l3) {
                l3 = l8;
                l2 = l1;
              } 
              continue;
              b++;
            } 
            long l5 = 0L;
            l1 = l2;
            l2 = l4;
            l4 = l5;
            l5 = l3;
            l3 = l4;
            l4 = l5;
          } 
          l = l4;
          l4 = l3;
        } else {
          if (l4 == Long.MAX_VALUE)
            return Long.MAX_VALUE; 
          l2 = SystemClock.elapsedRealtime();
          if (l4 >= l1)
            l3 = l4 - l1; 
          return l2 + l3;
        } 
        l3 = l4;
      } 
    }
    
    private void sendSyncStateIntent() {}
    
    private int syncResultToErrorNumber(SyncResult param1SyncResult) {
      if (param1SyncResult.syncAlreadyInProgress)
        return 1; 
      if (param1SyncResult.stats.numAuthExceptions > 0L)
        return 2; 
      if (param1SyncResult.stats.numIoExceptions > 0L)
        return 3; 
      if (param1SyncResult.stats.numParseExceptions > 0L)
        return 4; 
      if (param1SyncResult.stats.numConflictDetectedExceptions > 0L)
        return 5; 
      if (param1SyncResult.tooManyDeletions)
        return 6; 
      if (param1SyncResult.tooManyRetries)
        return 7; 
      if (param1SyncResult.databaseError)
        return 8; 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("we are not in an error state, ");
      stringBuilder.append(param1SyncResult);
      throw new IllegalStateException(stringBuilder.toString());
    }
    
    private boolean tryEnqueueMessageUntilReadyToRun(Message param1Message) {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   6: invokestatic access$1600 : (Lcom/lody/virtual/server/content/SyncManager;)Z
      //   9: ifne -> 30
      //   12: aload_0
      //   13: getfield mBootQueue : Ljava/util/List;
      //   16: aload_1
      //   17: invokestatic obtain : (Landroid/os/Message;)Landroid/os/Message;
      //   20: invokeinterface add : (Ljava/lang/Object;)Z
      //   25: pop
      //   26: aload_0
      //   27: monitorexit
      //   28: iconst_1
      //   29: ireturn
      //   30: aload_0
      //   31: monitorexit
      //   32: iconst_0
      //   33: ireturn
      //   34: astore_1
      //   35: aload_0
      //   36: monitorexit
      //   37: aload_1
      //   38: athrow
      // Exception table:
      //   from	to	target	type
      //   2	28	34	finally
      //   30	32	34	finally
      //   35	37	34	finally
    }
    
    public void handleMessage(Message param1Message) {
      // Byte code:
      //   0: aload_0
      //   1: aload_1
      //   2: invokespecial tryEnqueueMessageUntilReadyToRun : (Landroid/os/Message;)Z
      //   5: ifeq -> 9
      //   8: return
      //   9: ldc2_w 9223372036854775807
      //   12: lstore_2
      //   13: aload_0
      //   14: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   17: aload_0
      //   18: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   21: invokestatic access$500 : (Lcom/lody/virtual/server/content/SyncManager;)Z
      //   24: invokestatic access$402 : (Lcom/lody/virtual/server/content/SyncManager;Z)Z
      //   27: pop
      //   28: aload_0
      //   29: invokespecial scheduleReadyPeriodicSyncs : ()J
      //   32: lstore #4
      //   34: aload_1
      //   35: getfield what : I
      //   38: istore #6
      //   40: iload #6
      //   42: tableswitch default -> 80, 1 -> 444, 2 -> 421, 3 -> 403, 4 -> 320, 5 -> 192, 6 -> 86
      //   80: lload_2
      //   81: lstore #7
      //   83: goto -> 539
      //   86: aload_1
      //   87: getfield obj : Ljava/lang/Object;
      //   90: checkcast android/util/Pair
      //   93: astore #9
      //   95: new java/lang/StringBuilder
      //   98: astore #10
      //   100: aload #10
      //   102: invokespecial <init> : ()V
      //   105: aload #10
      //   107: ldc_w 'handleSyncHandlerMessage: MESSAGE_SERVICE_CANCEL: '
      //   110: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   113: pop
      //   114: aload #10
      //   116: aload #9
      //   118: getfield first : Ljava/lang/Object;
      //   121: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   124: pop
      //   125: aload #10
      //   127: ldc_w ', '
      //   130: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   133: pop
      //   134: aload #10
      //   136: aload #9
      //   138: getfield second : Ljava/lang/Object;
      //   141: checkcast java/lang/String
      //   144: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   147: pop
      //   148: ldc 'SyncManager'
      //   150: aload #10
      //   152: invokevirtual toString : ()Ljava/lang/String;
      //   155: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)I
      //   158: pop
      //   159: aload_0
      //   160: aload #9
      //   162: getfield first : Ljava/lang/Object;
      //   165: checkcast android/accounts/Account
      //   168: aload_1
      //   169: getfield arg1 : I
      //   172: aload #9
      //   174: getfield second : Ljava/lang/Object;
      //   177: checkcast java/lang/String
      //   180: invokespecial cancelActiveSyncLocked : (Landroid/accounts/Account;ILjava/lang/String;)V
      //   183: aload_0
      //   184: invokespecial maybeStartNextSyncLocked : ()J
      //   187: lstore #7
      //   189: goto -> 539
      //   192: aload_1
      //   193: getfield obj : Ljava/lang/Object;
      //   196: checkcast com/lody/virtual/server/content/SyncManager$ServiceConnectionData
      //   199: getfield activeSyncContext : Lcom/lody/virtual/server/content/SyncManager$ActiveSyncContext;
      //   202: astore_1
      //   203: new java/lang/StringBuilder
      //   206: astore #9
      //   208: aload #9
      //   210: invokespecial <init> : ()V
      //   213: aload #9
      //   215: ldc_w 'handleSyncHandlerMessage: MESSAGE_SERVICE_DISCONNECTED: '
      //   218: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   221: pop
      //   222: aload #9
      //   224: aload_1
      //   225: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   228: pop
      //   229: ldc 'SyncManager'
      //   231: aload #9
      //   233: invokevirtual toString : ()Ljava/lang/String;
      //   236: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)I
      //   239: pop
      //   240: lload_2
      //   241: lstore #7
      //   243: aload_0
      //   244: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   247: aload_1
      //   248: invokestatic access$1700 : (Lcom/lody/virtual/server/content/SyncManager;Lcom/lody/virtual/server/content/SyncManager$ActiveSyncContext;)Z
      //   251: ifeq -> 539
      //   254: aload_1
      //   255: getfield mSyncAdapter : Landroid/content/ISyncAdapter;
      //   258: astore #9
      //   260: aload #9
      //   262: ifnull -> 275
      //   265: aload_1
      //   266: getfield mSyncAdapter : Landroid/content/ISyncAdapter;
      //   269: aload_1
      //   270: invokeinterface cancelSync : (Landroid/content/ISyncContext;)V
      //   275: new android/content/SyncResult
      //   278: astore #10
      //   280: aload #10
      //   282: invokespecial <init> : ()V
      //   285: aload #10
      //   287: getfield stats : Landroid/content/SyncStats;
      //   290: astore #9
      //   292: aload #9
      //   294: aload #9
      //   296: getfield numIoExceptions : J
      //   299: lconst_1
      //   300: ladd
      //   301: putfield numIoExceptions : J
      //   304: aload_0
      //   305: aload #10
      //   307: aload_1
      //   308: invokespecial runSyncFinishedOrCanceledLocked : (Landroid/content/SyncResult;Lcom/lody/virtual/server/content/SyncManager$ActiveSyncContext;)V
      //   311: aload_0
      //   312: invokespecial maybeStartNextSyncLocked : ()J
      //   315: lstore #7
      //   317: goto -> 539
      //   320: aload_1
      //   321: getfield obj : Ljava/lang/Object;
      //   324: checkcast com/lody/virtual/server/content/SyncManager$ServiceConnectionData
      //   327: astore #9
      //   329: new java/lang/StringBuilder
      //   332: astore_1
      //   333: aload_1
      //   334: invokespecial <init> : ()V
      //   337: aload_1
      //   338: ldc_w 'handleSyncHandlerMessage: MESSAGE_SERVICE_CONNECTED: '
      //   341: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   344: pop
      //   345: aload_1
      //   346: aload #9
      //   348: getfield activeSyncContext : Lcom/lody/virtual/server/content/SyncManager$ActiveSyncContext;
      //   351: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   354: pop
      //   355: ldc 'SyncManager'
      //   357: aload_1
      //   358: invokevirtual toString : ()Ljava/lang/String;
      //   361: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)I
      //   364: pop
      //   365: lload_2
      //   366: lstore #7
      //   368: aload_0
      //   369: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   372: aload #9
      //   374: getfield activeSyncContext : Lcom/lody/virtual/server/content/SyncManager$ActiveSyncContext;
      //   377: invokestatic access$1700 : (Lcom/lody/virtual/server/content/SyncManager;Lcom/lody/virtual/server/content/SyncManager$ActiveSyncContext;)Z
      //   380: ifeq -> 539
      //   383: aload_0
      //   384: aload #9
      //   386: getfield activeSyncContext : Lcom/lody/virtual/server/content/SyncManager$ActiveSyncContext;
      //   389: aload #9
      //   391: getfield syncAdapter : Landroid/content/ISyncAdapter;
      //   394: invokespecial runBoundToSyncAdapter : (Lcom/lody/virtual/server/content/SyncManager$ActiveSyncContext;Landroid/content/ISyncAdapter;)V
      //   397: lload_2
      //   398: lstore #7
      //   400: goto -> 539
      //   403: ldc 'SyncManager'
      //   405: ldc_w 'handleSyncHandlerMessage: MESSAGE_CHECK_ALARMS'
      //   408: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   411: pop
      //   412: aload_0
      //   413: invokespecial maybeStartNextSyncLocked : ()J
      //   416: lstore #7
      //   418: goto -> 539
      //   421: ldc 'SyncManager'
      //   423: ldc_w 'handleSyncHandlerMessage: MESSAGE_SYNC_ALARM'
      //   426: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   429: pop
      //   430: aload_0
      //   431: aconst_null
      //   432: putfield mAlarmScheduleTime : Ljava/lang/Long;
      //   435: aload_0
      //   436: invokespecial maybeStartNextSyncLocked : ()J
      //   439: lstore #7
      //   441: goto -> 539
      //   444: ldc 'SyncManager'
      //   446: ldc_w 'handleSyncHandlerMessage: MESSAGE_SYNC_FINISHED'
      //   449: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   452: pop
      //   453: aload_1
      //   454: getfield obj : Ljava/lang/Object;
      //   457: checkcast com/lody/virtual/server/content/SyncManager$SyncHandlerMessagePayload
      //   460: astore #9
      //   462: aload_0
      //   463: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   466: aload #9
      //   468: getfield activeSyncContext : Lcom/lody/virtual/server/content/SyncManager$ActiveSyncContext;
      //   471: invokestatic access$1700 : (Lcom/lody/virtual/server/content/SyncManager;Lcom/lody/virtual/server/content/SyncManager$ActiveSyncContext;)Z
      //   474: ifne -> 519
      //   477: new java/lang/StringBuilder
      //   480: astore_1
      //   481: aload_1
      //   482: invokespecial <init> : ()V
      //   485: aload_1
      //   486: ldc_w 'handleSyncHandlerMessage: dropping since the sync is no longer active: '
      //   489: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   492: pop
      //   493: aload_1
      //   494: aload #9
      //   496: getfield activeSyncContext : Lcom/lody/virtual/server/content/SyncManager$ActiveSyncContext;
      //   499: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   502: pop
      //   503: ldc 'SyncManager'
      //   505: aload_1
      //   506: invokevirtual toString : ()Ljava/lang/String;
      //   509: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)I
      //   512: pop
      //   513: lload_2
      //   514: lstore #7
      //   516: goto -> 539
      //   519: aload_0
      //   520: aload #9
      //   522: getfield syncResult : Landroid/content/SyncResult;
      //   525: aload #9
      //   527: getfield activeSyncContext : Lcom/lody/virtual/server/content/SyncManager$ActiveSyncContext;
      //   530: invokespecial runSyncFinishedOrCanceledLocked : (Landroid/content/SyncResult;Lcom/lody/virtual/server/content/SyncManager$ActiveSyncContext;)V
      //   533: aload_0
      //   534: invokespecial maybeStartNextSyncLocked : ()J
      //   537: lstore #7
      //   539: aload_0
      //   540: invokespecial manageSyncNotificationLocked : ()V
      //   543: aload_0
      //   544: lload #4
      //   546: lload #7
      //   548: invokespecial manageSyncAlarmLocked : (JJ)V
      //   551: aload_0
      //   552: getfield mSyncTimeTracker : Lcom/lody/virtual/server/content/SyncManager$SyncTimeTracker;
      //   555: invokevirtual update : ()V
      //   558: return
      //   559: astore_1
      //   560: lload #4
      //   562: lstore #7
      //   564: goto -> 573
      //   567: astore_1
      //   568: ldc2_w 9223372036854775807
      //   571: lstore #7
      //   573: aload_0
      //   574: invokespecial manageSyncNotificationLocked : ()V
      //   577: aload_0
      //   578: lload #7
      //   580: ldc2_w 9223372036854775807
      //   583: invokespecial manageSyncAlarmLocked : (JJ)V
      //   586: aload_0
      //   587: getfield mSyncTimeTracker : Lcom/lody/virtual/server/content/SyncManager$SyncTimeTracker;
      //   590: invokevirtual update : ()V
      //   593: aload_1
      //   594: athrow
      //   595: astore #9
      //   597: goto -> 275
      // Exception table:
      //   from	to	target	type
      //   13	34	567	finally
      //   34	40	559	finally
      //   86	189	559	finally
      //   192	240	559	finally
      //   243	260	559	finally
      //   265	275	595	android/os/RemoteException
      //   265	275	559	finally
      //   275	317	559	finally
      //   320	365	559	finally
      //   368	397	559	finally
      //   403	418	559	finally
      //   421	441	559	finally
      //   444	513	559	finally
      //   519	539	559	finally
    }
    
    public long insertStartSyncEvent(SyncOperation param1SyncOperation) {
      int i = param1SyncOperation.syncSource;
      long l = System.currentTimeMillis();
      return SyncManager.this.mSyncStorageEngine.insertStartSyncEvent(param1SyncOperation.account, param1SyncOperation.userId, param1SyncOperation.reason, param1SyncOperation.authority, l, i, param1SyncOperation.isInitialization(), param1SyncOperation.extras);
    }
    
    public void onBootCompleted() {
      // Byte code:
      //   0: ldc 'SyncManager'
      //   2: ldc_w 'Boot completed, clearing boot queue.'
      //   5: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   8: pop
      //   9: aload_0
      //   10: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   13: invokestatic access$1500 : (Lcom/lody/virtual/server/content/SyncManager;)V
      //   16: aload_0
      //   17: monitorenter
      //   18: aload_0
      //   19: getfield mBootQueue : Ljava/util/List;
      //   22: invokeinterface iterator : ()Ljava/util/Iterator;
      //   27: astore_1
      //   28: aload_1
      //   29: invokeinterface hasNext : ()Z
      //   34: ifeq -> 54
      //   37: aload_0
      //   38: aload_1
      //   39: invokeinterface next : ()Ljava/lang/Object;
      //   44: checkcast android/os/Message
      //   47: invokevirtual sendMessage : (Landroid/os/Message;)Z
      //   50: pop
      //   51: goto -> 28
      //   54: aload_0
      //   55: aconst_null
      //   56: putfield mBootQueue : Ljava/util/List;
      //   59: aload_0
      //   60: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   63: iconst_1
      //   64: invokestatic access$1602 : (Lcom/lody/virtual/server/content/SyncManager;Z)Z
      //   67: pop
      //   68: aload_0
      //   69: monitorexit
      //   70: return
      //   71: astore_1
      //   72: aload_0
      //   73: monitorexit
      //   74: aload_1
      //   75: athrow
      // Exception table:
      //   from	to	target	type
      //   18	28	71	finally
      //   28	51	71	finally
      //   54	70	71	finally
      //   72	74	71	finally
    }
    
    public void stopSyncEvent(long param1Long1, SyncOperation param1SyncOperation, String param1String, int param1Int1, int param1Int2, long param1Long2) {
      SyncManager.this.mSyncStorageEngine.stopSyncEvent(param1Long1, param1Long2, param1String, param1Int2, param1Int1);
    }
    
    class SyncNotificationInfo {
      public boolean isActive = false;
      
      public Long startTime = null;
      
      public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        toString(stringBuilder);
        return stringBuilder.toString();
      }
      
      public void toString(StringBuilder param2StringBuilder) {
        param2StringBuilder.append("isActive ");
        param2StringBuilder.append(this.isActive);
        param2StringBuilder.append(", startTime ");
        param2StringBuilder.append(this.startTime);
      }
    }
  }
  
  class SyncNotificationInfo {
    public boolean isActive = false;
    
    public Long startTime = null;
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      toString(stringBuilder);
      return stringBuilder.toString();
    }
    
    public void toString(StringBuilder param1StringBuilder) {
      param1StringBuilder.append("isActive ");
      param1StringBuilder.append(this.isActive);
      param1StringBuilder.append(", startTime ");
      param1StringBuilder.append(this.startTime);
    }
  }
  
  class SyncHandlerMessagePayload {
    public final SyncManager.ActiveSyncContext activeSyncContext;
    
    public final SyncResult syncResult;
    
    SyncHandlerMessagePayload(SyncManager.ActiveSyncContext param1ActiveSyncContext, SyncResult param1SyncResult) {
      this.activeSyncContext = param1ActiveSyncContext;
      this.syncResult = param1SyncResult;
    }
  }
  
  private class SyncTimeTracker {
    boolean mLastWasSyncing = false;
    
    private long mTimeSpentSyncing;
    
    long mWhenSyncStarted = 0L;
    
    private SyncTimeTracker() {}
    
    public long timeSpentSyncing() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield mLastWasSyncing : Z
      //   6: ifne -> 18
      //   9: aload_0
      //   10: getfield mTimeSpentSyncing : J
      //   13: lstore_1
      //   14: aload_0
      //   15: monitorexit
      //   16: lload_1
      //   17: lreturn
      //   18: invokestatic elapsedRealtime : ()J
      //   21: lstore_3
      //   22: aload_0
      //   23: getfield mTimeSpentSyncing : J
      //   26: lstore_1
      //   27: aload_0
      //   28: getfield mWhenSyncStarted : J
      //   31: lstore #5
      //   33: aload_0
      //   34: monitorexit
      //   35: lload_1
      //   36: lload_3
      //   37: lload #5
      //   39: lsub
      //   40: ladd
      //   41: lreturn
      //   42: astore #7
      //   44: aload_0
      //   45: monitorexit
      //   46: aload #7
      //   48: athrow
      // Exception table:
      //   from	to	target	type
      //   2	14	42	finally
      //   18	33	42	finally
    }
    
    public void update() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield this$0 : Lcom/lody/virtual/server/content/SyncManager;
      //   6: getfield mActiveSyncContexts : Ljava/util/ArrayList;
      //   9: invokevirtual isEmpty : ()Z
      //   12: ifne -> 20
      //   15: iconst_1
      //   16: istore_1
      //   17: goto -> 22
      //   20: iconst_0
      //   21: istore_1
      //   22: aload_0
      //   23: getfield mLastWasSyncing : Z
      //   26: istore_2
      //   27: iload_1
      //   28: iload_2
      //   29: if_icmpne -> 35
      //   32: aload_0
      //   33: monitorexit
      //   34: return
      //   35: invokestatic elapsedRealtime : ()J
      //   38: lstore_3
      //   39: iload_1
      //   40: ifeq -> 51
      //   43: aload_0
      //   44: lload_3
      //   45: putfield mWhenSyncStarted : J
      //   48: goto -> 66
      //   51: aload_0
      //   52: aload_0
      //   53: getfield mTimeSpentSyncing : J
      //   56: lload_3
      //   57: aload_0
      //   58: getfield mWhenSyncStarted : J
      //   61: lsub
      //   62: ladd
      //   63: putfield mTimeSpentSyncing : J
      //   66: aload_0
      //   67: iload_1
      //   68: putfield mLastWasSyncing : Z
      //   71: aload_0
      //   72: monitorexit
      //   73: return
      //   74: astore #5
      //   76: aload_0
      //   77: monitorexit
      //   78: aload #5
      //   80: athrow
      // Exception table:
      //   from	to	target	type
      //   2	15	74	finally
      //   22	27	74	finally
      //   35	39	74	finally
      //   43	48	74	finally
      //   51	66	74	finally
      //   66	71	74	finally
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\content\SyncManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */