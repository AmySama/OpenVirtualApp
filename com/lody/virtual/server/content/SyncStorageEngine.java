package com.lody.virtual.server.content;

import android.accounts.Account;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ISyncStatusObserver;
import android.content.PeriodicSync;
import android.content.SyncStatusInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.AtomicFile;
import com.lody.virtual.helper.utils.FastXmlSerializer;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.server.accounts.AccountAndUser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import mirror.android.content.PeriodicSync;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

public class SyncStorageEngine extends Handler {
  private static final int ACCOUNTS_VERSION = 2;
  
  private static final boolean DEBUG = false;
  
  private static final double DEFAULT_FLEX_PERCENT_SYNC = 0.04D;
  
  private static final long DEFAULT_MIN_FLEX_ALLOWED_SECS = 5L;
  
  private static final long DEFAULT_POLL_FREQUENCY_SECONDS = 86400L;
  
  public static final String[] EVENTS = new String[] { "START", "STOP" };
  
  public static final int EVENT_START = 0;
  
  public static final int EVENT_STOP = 1;
  
  public static final int MAX_HISTORY = 100;
  
  public static final String MESG_CANCELED = "canceled";
  
  public static final String MESG_SUCCESS = "success";
  
  private static final int MSG_WRITE_STATISTICS = 2;
  
  private static final int MSG_WRITE_STATUS = 1;
  
  public static final long NOT_IN_BACKOFF_MODE = -1L;
  
  private static final int PENDING_FINISH_TO_WRITE = 4;
  
  public static final int PENDING_OPERATION_VERSION = 3;
  
  public static final String[] SOURCES = new String[] { "SERVER", "LOCAL", "POLL", "USER", "PERIODIC" };
  
  public static final int SOURCE_LOCAL = 1;
  
  public static final int SOURCE_PERIODIC = 4;
  
  public static final int SOURCE_POLL = 2;
  
  public static final int SOURCE_SERVER = 0;
  
  public static final int SOURCE_USER = 3;
  
  public static final int STATISTICS_FILE_END = 0;
  
  public static final int STATISTICS_FILE_ITEM = 101;
  
  public static final int STATISTICS_FILE_ITEM_OLD = 100;
  
  public static final int STATUS_FILE_END = 0;
  
  public static final int STATUS_FILE_ITEM = 100;
  
  private static final boolean SYNC_ENABLED_DEFAULT = false;
  
  private static final String TAG = "SyncManager";
  
  private static final String TAG_FILE = "SyncManagerFile";
  
  private static final long WRITE_STATISTICS_DELAY = 1800000L;
  
  private static final long WRITE_STATUS_DELAY = 600000L;
  
  private static final String XML_ATTR_AUTHORITYID = "authority_id";
  
  private static final String XML_ATTR_ENABLED = "enabled";
  
  private static final String XML_ATTR_EXPEDITED = "expedited";
  
  private static final String XML_ATTR_LISTEN_FOR_TICKLES = "listen-for-tickles";
  
  private static final String XML_ATTR_NEXT_AUTHORITY_ID = "nextAuthorityId";
  
  private static final String XML_ATTR_REASON = "reason";
  
  private static final String XML_ATTR_SOURCE = "source";
  
  private static final String XML_ATTR_SYNC_RANDOM_OFFSET = "offsetInSeconds";
  
  private static final String XML_ATTR_USER = "user";
  
  private static final String XML_ATTR_VERSION = "version";
  
  private static final String XML_TAG_LISTEN_FOR_TICKLES = "listenForTickles";
  
  private static HashMap<String, String> sAuthorityRenames;
  
  private static volatile SyncStorageEngine sSyncStorageEngine = null;
  
  private final AtomicFile mAccountInfoFile;
  
  private final HashMap<AccountAndUser, AccountInfo> mAccounts = new HashMap<AccountAndUser, AccountInfo>();
  
  private final SparseArray<AuthorityInfo> mAuthorities = new SparseArray();
  
  private final Calendar mCal;
  
  private final RemoteCallbackList<ISyncStatusObserver> mChangeListeners = new RemoteCallbackList();
  
  private final Context mContext;
  
  private final SparseArray<ArrayList<VSyncInfo>> mCurrentSyncs = new SparseArray();
  
  private final DayStats[] mDayStats = new DayStats[28];
  
  private boolean mDefaultMasterSyncAutomatically;
  
  private SparseArray<Boolean> mMasterSyncAutomatically = new SparseArray();
  
  private int mNextAuthorityId = 0;
  
  private int mNextHistoryId = 0;
  
  private int mNumPendingFinished = 0;
  
  private final AtomicFile mPendingFile;
  
  private final ArrayList<PendingOperation> mPendingOperations = new ArrayList<PendingOperation>();
  
  private final HashMap<ComponentName, SparseArray<AuthorityInfo>> mServices = new HashMap<ComponentName, SparseArray<AuthorityInfo>>();
  
  private final AtomicFile mStatisticsFile;
  
  private final AtomicFile mStatusFile;
  
  private final ArrayList<SyncHistoryItem> mSyncHistory = new ArrayList<SyncHistoryItem>();
  
  private int mSyncRandomOffset;
  
  private OnSyncRequestListener mSyncRequestListener;
  
  private final SparseArray<SyncStatusInfo> mSyncStatus = new SparseArray();
  
  private int mYear;
  
  private int mYearInDays;
  
  private SyncStorageEngine(Context paramContext, File paramFile) {
    this.mContext = paramContext;
    sSyncStorageEngine = this;
    this.mCal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
    this.mDefaultMasterSyncAutomatically = false;
    maybeDeleteLegacyPendingInfoLocked(paramFile);
    this.mAccountInfoFile = new AtomicFile(new File(paramFile, "accounts.xml"));
    this.mStatusFile = new AtomicFile(new File(paramFile, "status.bin"));
    this.mPendingFile = new AtomicFile(new File(paramFile, "pending.xml"));
    this.mStatisticsFile = new AtomicFile(new File(paramFile, "stats.bin"));
    readAccountInfoLocked();
    readStatusLocked();
    readPendingOperationsLocked();
    readStatisticsLocked();
    readAndDeleteLegacyAccountInfoLocked();
    writeAccountInfoLocked();
    writeStatusLocked();
    writePendingOperationsLocked();
    writeStatisticsLocked();
  }
  
  private void appendPendingOperationLocked(PendingOperation paramPendingOperation) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Appending to ");
    stringBuilder.append(this.mPendingFile.getBaseFile());
    Log.v("SyncManager", stringBuilder.toString());
    try {
      FileOutputStream fileOutputStream = this.mPendingFile.openAppend();
      try {
        FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
        this();
        fastXmlSerializer.setOutput(fileOutputStream, "utf-8");
        writePendingOperationLocked(paramPendingOperation, (XmlSerializer)fastXmlSerializer);
        fastXmlSerializer.endDocument();
        this.mPendingFile.finishWrite(fileOutputStream);
      } catch (IOException iOException) {
      
      } finally {}
      try {
        fileOutputStream.close();
      } catch (IOException iOException) {}
    } catch (IOException iOException) {
      Log.v("SyncManager", "Failed append; writing full file");
      writePendingOperationsLocked();
      return;
    } 
  }
  
  public static long calculateDefaultFlexTime(long paramLong) {
    return (paramLong < 5L) ? 0L : ((paramLong < 86400L) ? (long)(paramLong * 0.04D) : 3456L);
  }
  
  private Pair<AuthorityInfo, SyncStatusInfo> createCopyPairOfAuthorityWithSyncStatusLocked(AuthorityInfo paramAuthorityInfo) {
    SyncStatusInfo syncStatusInfo = getOrCreateSyncStatusLocked(paramAuthorityInfo.ident);
    return Pair.create(new AuthorityInfo(paramAuthorityInfo), new SyncStatusInfo(syncStatusInfo));
  }
  
  private void extrasToXml(XmlSerializer paramXmlSerializer, Bundle paramBundle) throws IOException {
    for (String str : paramBundle.keySet()) {
      paramXmlSerializer.startTag(null, "extra");
      paramXmlSerializer.attribute(null, "name", str);
      Object object = paramBundle.get(str);
      if (object instanceof Long) {
        paramXmlSerializer.attribute(null, "type", "long");
        paramXmlSerializer.attribute(null, "value1", object.toString());
      } else if (object instanceof Integer) {
        paramXmlSerializer.attribute(null, "type", "integer");
        paramXmlSerializer.attribute(null, "value1", object.toString());
      } else if (object instanceof Boolean) {
        paramXmlSerializer.attribute(null, "type", "boolean");
        paramXmlSerializer.attribute(null, "value1", object.toString());
      } else if (object instanceof Float) {
        paramXmlSerializer.attribute(null, "type", "float");
        paramXmlSerializer.attribute(null, "value1", object.toString());
      } else if (object instanceof Double) {
        paramXmlSerializer.attribute(null, "type", "double");
        paramXmlSerializer.attribute(null, "value1", object.toString());
      } else if (object instanceof String) {
        paramXmlSerializer.attribute(null, "type", "string");
        paramXmlSerializer.attribute(null, "value1", object.toString());
      } else if (object instanceof Account) {
        paramXmlSerializer.attribute(null, "type", "account");
        object = object;
        paramXmlSerializer.attribute(null, "value1", ((Account)object).name);
        paramXmlSerializer.attribute(null, "value2", ((Account)object).type);
      } 
      paramXmlSerializer.endTag(null, "extra");
    } 
  }
  
  private static byte[] flattenBundle(Bundle paramBundle) {
    Parcel parcel = Parcel.obtain();
    try {
      paramBundle.writeToParcel(parcel, 0);
      return parcel.marshall();
    } finally {
      parcel.recycle();
    } 
  }
  
  private AuthorityInfo getAuthorityLocked(Account paramAccount, int paramInt, String paramString1, String paramString2) {
    AccountAndUser accountAndUser = new AccountAndUser(paramAccount, paramInt);
    AccountInfo accountInfo = this.mAccounts.get(accountAndUser);
    if (accountInfo == null)
      return null; 
    AuthorityInfo authorityInfo = accountInfo.authorities.get(paramString1);
    return (authorityInfo == null) ? null : authorityInfo;
  }
  
  private AuthorityInfo getAuthorityLocked(ComponentName paramComponentName, int paramInt, String paramString) {
    AuthorityInfo authorityInfo2 = (AuthorityInfo)((SparseArray)this.mServices.get(paramComponentName)).get(paramInt);
    AuthorityInfo authorityInfo1 = authorityInfo2;
    if (authorityInfo2 == null)
      authorityInfo1 = null; 
    return authorityInfo1;
  }
  
  private int getCurrentDayLocked() {
    this.mCal.setTimeInMillis(System.currentTimeMillis());
    int i = this.mCal.get(6);
    if (this.mYear != this.mCal.get(1)) {
      this.mYear = this.mCal.get(1);
      this.mCal.clear();
      this.mCal.set(1, this.mYear);
      this.mYearInDays = (int)(this.mCal.getTimeInMillis() / 86400000L);
    } 
    return i + this.mYearInDays;
  }
  
  private List<VSyncInfo> getCurrentSyncs(int paramInt) {
    synchronized (this.mAuthorities) {
      return getCurrentSyncsLocked(paramInt);
    } 
  }
  
  private List<VSyncInfo> getCurrentSyncsLocked(int paramInt) {
    ArrayList<VSyncInfo> arrayList1 = (ArrayList)this.mCurrentSyncs.get(paramInt);
    ArrayList<VSyncInfo> arrayList2 = arrayList1;
    if (arrayList1 == null) {
      arrayList2 = new ArrayList();
      this.mCurrentSyncs.put(paramInt, arrayList2);
    } 
    return arrayList2;
  }
  
  static int getIntColumn(Cursor paramCursor, String paramString) {
    return paramCursor.getInt(paramCursor.getColumnIndex(paramString));
  }
  
  static long getLongColumn(Cursor paramCursor, String paramString) {
    return paramCursor.getLong(paramCursor.getColumnIndex(paramString));
  }
  
  private AuthorityInfo getOrCreateAuthorityLocked(Account paramAccount, int paramInt1, String paramString, int paramInt2, boolean paramBoolean) {
    AccountAndUser accountAndUser = new AccountAndUser(paramAccount, paramInt1);
    AccountInfo accountInfo1 = this.mAccounts.get(accountAndUser);
    AccountInfo accountInfo2 = accountInfo1;
    if (accountInfo1 == null) {
      accountInfo2 = new AccountInfo(accountAndUser);
      this.mAccounts.put(accountAndUser, accountInfo2);
    } 
    AuthorityInfo authorityInfo1 = accountInfo2.authorities.get(paramString);
    AuthorityInfo authorityInfo2 = authorityInfo1;
    if (authorityInfo1 == null) {
      int i = paramInt2;
      if (paramInt2 < 0) {
        i = this.mNextAuthorityId;
        this.mNextAuthorityId = i + 1;
        paramBoolean = true;
      } 
      AuthorityInfo authorityInfo = new AuthorityInfo(paramAccount, paramInt1, paramString, i);
      accountInfo2.authorities.put(paramString, authorityInfo);
      this.mAuthorities.put(i, authorityInfo);
      authorityInfo2 = authorityInfo;
      if (paramBoolean) {
        writeAccountInfoLocked();
        authorityInfo2 = authorityInfo;
      } 
    } 
    return authorityInfo2;
  }
  
  private AuthorityInfo getOrCreateAuthorityLocked(ComponentName paramComponentName, int paramInt1, int paramInt2, boolean paramBoolean) {
    SparseArray<AuthorityInfo> sparseArray1 = this.mServices.get(paramComponentName);
    SparseArray<AuthorityInfo> sparseArray2 = sparseArray1;
    if (sparseArray1 == null) {
      sparseArray2 = new SparseArray();
      this.mServices.put(paramComponentName, sparseArray2);
    } 
    AuthorityInfo authorityInfo2 = (AuthorityInfo)sparseArray2.get(paramInt1);
    AuthorityInfo authorityInfo1 = authorityInfo2;
    if (authorityInfo2 == null) {
      int i = paramInt2;
      if (paramInt2 < 0) {
        i = this.mNextAuthorityId;
        this.mNextAuthorityId = i + 1;
        paramBoolean = true;
      } 
      AuthorityInfo authorityInfo = new AuthorityInfo(paramComponentName, paramInt1, i);
      sparseArray2.put(paramInt1, authorityInfo);
      this.mAuthorities.put(i, authorityInfo);
      authorityInfo1 = authorityInfo;
      if (paramBoolean) {
        writeAccountInfoLocked();
        authorityInfo1 = authorityInfo;
      } 
    } 
    return authorityInfo1;
  }
  
  private SyncStatusInfo getOrCreateSyncStatusLocked(int paramInt) {
    SyncStatusInfo syncStatusInfo1 = (SyncStatusInfo)this.mSyncStatus.get(paramInt);
    SyncStatusInfo syncStatusInfo2 = syncStatusInfo1;
    if (syncStatusInfo1 == null) {
      syncStatusInfo2 = new SyncStatusInfo(paramInt);
      this.mSyncStatus.put(paramInt, syncStatusInfo2);
    } 
    return syncStatusInfo2;
  }
  
  public static SyncStorageEngine getSingleton() {
    if (sSyncStorageEngine != null)
      return sSyncStorageEngine; 
    throw new IllegalStateException("not initialized");
  }
  
  public static void init(Context paramContext) {
    if (sSyncStorageEngine != null)
      return; 
    File file = VEnvironment.getSyncDirectory();
    FileUtils.ensureDirCreate(file);
    sSyncStorageEngine = new SyncStorageEngine(paramContext, file);
  }
  
  private void maybeDeleteLegacyPendingInfoLocked(File paramFile) {
    paramFile = new File(paramFile, "pending.bin");
    if (!paramFile.exists())
      return; 
    paramFile.delete();
  }
  
  private boolean maybeMigrateSettingsForRenamedAuthorities() {
    ArrayList<AuthorityInfo> arrayList = new ArrayList();
    int i = this.mAuthorities.size();
    byte b = 0;
    boolean bool = false;
    while (b < i) {
      AuthorityInfo authorityInfo = (AuthorityInfo)this.mAuthorities.valueAt(b);
      String str = sAuthorityRenames.get(authorityInfo.authority);
      if (str != null) {
        arrayList.add(authorityInfo);
        if (authorityInfo.enabled && getAuthorityLocked(authorityInfo.account, authorityInfo.userId, str, "cleanup") == null) {
          (getOrCreateAuthorityLocked(authorityInfo.account, authorityInfo.userId, str, -1, false)).enabled = true;
          bool = true;
        } 
      } 
      b++;
    } 
    for (AuthorityInfo authorityInfo : arrayList) {
      removeAuthorityLocked(authorityInfo.account, authorityInfo.userId, authorityInfo.authority, false);
      bool = true;
    } 
    return bool;
  }
  
  private AuthorityInfo parseAuthority(XmlPullParser paramXmlPullParser, int paramInt) {
    AuthorityInfo authorityInfo;
    byte b;
    String str = null;
    try {
      b = Integer.parseInt(paramXmlPullParser.getAttributeValue(null, "id"));
    } catch (NumberFormatException numberFormatException) {
      Log.e("SyncManager", "error parsing the id of the authority", numberFormatException);
      b = -1;
    } catch (NullPointerException nullPointerException) {
      Log.e("SyncManager", "the id of the authority is null", nullPointerException);
    } 
    if (b >= 0) {
      String str1;
      AuthorityInfo authorityInfo1;
      int i;
      String str3 = paramXmlPullParser.getAttributeValue(null, "authority");
      String str4 = paramXmlPullParser.getAttributeValue(null, "enabled");
      String str2 = paramXmlPullParser.getAttributeValue(null, "syncable");
      String str5 = paramXmlPullParser.getAttributeValue(null, "account");
      str = paramXmlPullParser.getAttributeValue(null, "type");
      String str6 = paramXmlPullParser.getAttributeValue(null, "user");
      String str7 = paramXmlPullParser.getAttributeValue(null, "package");
      String str8 = paramXmlPullParser.getAttributeValue(null, "class");
      if (str6 == null) {
        i = 0;
      } else {
        i = Integer.parseInt(str6);
      } 
      if (str == null) {
        str1 = "com.google";
        str2 = "unknown";
      } else {
        str1 = str;
      } 
      authorityInfo = (AuthorityInfo)this.mAuthorities.get(b);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Adding authority: account=");
      stringBuilder.append(str5);
      stringBuilder.append(" auth=");
      stringBuilder.append(str3);
      stringBuilder.append(" user=");
      stringBuilder.append(i);
      stringBuilder.append(" enabled=");
      stringBuilder.append(str4);
      stringBuilder.append(" syncable=");
      stringBuilder.append(str2);
      Log.v("SyncManager", stringBuilder.toString());
      if (authorityInfo == null) {
        Log.v("SyncManager", "Creating entry");
        if (str5 != null && str1 != null) {
          authorityInfo = getOrCreateAuthorityLocked(new Account(str5, str1), i, str3, b, false);
        } else {
          authorityInfo = getOrCreateAuthorityLocked(new ComponentName(str7, str8), i, b, false);
        } 
        authorityInfo1 = authorityInfo;
        if (paramInt > 0) {
          authorityInfo.periodicSyncs.clear();
          authorityInfo1 = authorityInfo;
        } 
      } else {
        authorityInfo1 = authorityInfo;
      } 
      if (authorityInfo1 != null) {
        boolean bool;
        if (str4 == null || Boolean.parseBoolean(str4)) {
          bool = true;
        } else {
          bool = false;
        } 
        authorityInfo1.enabled = bool;
        if ("unknown".equals(str2)) {
          authorityInfo1.syncable = -1;
          authorityInfo = authorityInfo1;
        } else {
          if (str2 == null || Boolean.parseBoolean(str2)) {
            paramInt = 1;
          } else {
            paramInt = 0;
          } 
          authorityInfo1.syncable = paramInt;
          authorityInfo = authorityInfo1;
        } 
      } else {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Failure adding authority: account=");
        stringBuilder1.append(str5);
        stringBuilder1.append(" auth=");
        stringBuilder1.append(str3);
        stringBuilder1.append(" enabled=");
        stringBuilder1.append(str4);
        stringBuilder1.append(" syncable=");
        stringBuilder1.append(str2);
        Log.w("SyncManager", stringBuilder1.toString());
        authorityInfo = authorityInfo1;
      } 
    } 
    return authorityInfo;
  }
  
  private void parseExtra(XmlPullParser paramXmlPullParser, Bundle paramBundle) {
    String str2 = paramXmlPullParser.getAttributeValue(null, "name");
    String str3 = paramXmlPullParser.getAttributeValue(null, "type");
    String str4 = paramXmlPullParser.getAttributeValue(null, "value1");
    String str1 = paramXmlPullParser.getAttributeValue(null, "value2");
    try {
      if ("long".equals(str3)) {
        paramBundle.putLong(str2, Long.parseLong(str4));
      } else if ("integer".equals(str3)) {
        paramBundle.putInt(str2, Integer.parseInt(str4));
      } else if ("double".equals(str3)) {
        paramBundle.putDouble(str2, Double.parseDouble(str4));
      } else if ("float".equals(str3)) {
        paramBundle.putFloat(str2, Float.parseFloat(str4));
      } else if ("boolean".equals(str3)) {
        paramBundle.putBoolean(str2, Boolean.parseBoolean(str4));
      } else if ("string".equals(str3)) {
        paramBundle.putString(str2, str4);
      } else if ("account".equals(str3)) {
        Account account = new Account();
        this(str4, str1);
        paramBundle.putParcelable(str2, (Parcelable)account);
      } 
    } catch (NumberFormatException numberFormatException) {
      Log.e("SyncManager", "error parsing bundle value", numberFormatException);
    } catch (NullPointerException nullPointerException) {
      Log.e("SyncManager", "error parsing bundle value", nullPointerException);
    } 
  }
  
  private void parseListenForTickles(XmlPullParser paramXmlPullParser) {
    boolean bool2;
    String str2 = paramXmlPullParser.getAttributeValue(null, "user");
    boolean bool1 = false;
    try {
      bool2 = Integer.parseInt(str2);
    } catch (NumberFormatException numberFormatException) {
      Log.e("SyncManager", "error parsing the user for listen-for-tickles", numberFormatException);
      bool2 = false;
    } catch (NullPointerException nullPointerException) {
      Log.e("SyncManager", "the user in listen-for-tickles is null", nullPointerException);
    } 
    String str1 = paramXmlPullParser.getAttributeValue(null, "enabled");
    if (str1 == null || Boolean.parseBoolean(str1))
      bool1 = true; 
    this.mMasterSyncAutomatically.put(bool2, Boolean.valueOf(bool1));
  }
  
  private PeriodicSync parsePeriodicSync(XmlPullParser paramXmlPullParser, AuthorityInfo paramAuthorityInfo) {
    Bundle bundle = new Bundle();
    String str2 = paramXmlPullParser.getAttributeValue(null, "period");
    String str1 = paramXmlPullParser.getAttributeValue(null, "flex");
    try {
      long l2;
      long l1 = Long.parseLong(str2);
      try {
        l2 = Long.parseLong(str1);
      } catch (NumberFormatException numberFormatException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error formatting value parsed for periodic sync flex: ");
        stringBuilder.append(str1);
        Log.e("SyncManager", stringBuilder.toString());
        l2 = calculateDefaultFlexTime(l1);
      } catch (NullPointerException nullPointerException) {
        l2 = calculateDefaultFlexTime(l1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No flex time specified for this sync, using a default. period: ");
        stringBuilder.append(l1);
        stringBuilder.append(" flex: ");
        stringBuilder.append(l2);
        Log.d("SyncManager", stringBuilder.toString());
      } 
      PeriodicSync periodicSync = new PeriodicSync(paramAuthorityInfo.account, paramAuthorityInfo.authority, bundle, l1);
      PeriodicSync.flexTime.set(periodicSync, l2);
      paramAuthorityInfo.periodicSyncs.add(periodicSync);
      return periodicSync;
    } catch (NumberFormatException numberFormatException) {
      Log.e("SyncManager", "error parsing the period of a periodic sync", numberFormatException);
      return null;
    } catch (NullPointerException nullPointerException) {
      Log.e("SyncManager", "the period of a periodic sync is null", nullPointerException);
      return null;
    } 
  }
  
  private void readAccountInfoLocked() {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: iconst_m1
    //   3: istore_2
    //   4: aload_0
    //   5: getfield mAccountInfoFile : Lcom/lody/virtual/helper/utils/AtomicFile;
    //   8: invokevirtual openRead : ()Ljava/io/FileInputStream;
    //   11: astore_3
    //   12: iload_2
    //   13: istore #4
    //   15: aload_3
    //   16: astore #5
    //   18: iload_2
    //   19: istore #6
    //   21: iload_2
    //   22: istore #7
    //   24: new java/lang/StringBuilder
    //   27: astore_1
    //   28: iload_2
    //   29: istore #4
    //   31: aload_3
    //   32: astore #5
    //   34: iload_2
    //   35: istore #6
    //   37: iload_2
    //   38: istore #7
    //   40: aload_1
    //   41: invokespecial <init> : ()V
    //   44: iload_2
    //   45: istore #4
    //   47: aload_3
    //   48: astore #5
    //   50: iload_2
    //   51: istore #6
    //   53: iload_2
    //   54: istore #7
    //   56: aload_1
    //   57: ldc_w 'Reading '
    //   60: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   63: pop
    //   64: iload_2
    //   65: istore #4
    //   67: aload_3
    //   68: astore #5
    //   70: iload_2
    //   71: istore #6
    //   73: iload_2
    //   74: istore #7
    //   76: aload_1
    //   77: aload_0
    //   78: getfield mAccountInfoFile : Lcom/lody/virtual/helper/utils/AtomicFile;
    //   81: invokevirtual getBaseFile : ()Ljava/io/File;
    //   84: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   87: pop
    //   88: iload_2
    //   89: istore #4
    //   91: aload_3
    //   92: astore #5
    //   94: iload_2
    //   95: istore #6
    //   97: iload_2
    //   98: istore #7
    //   100: ldc 'SyncManager'
    //   102: aload_1
    //   103: invokevirtual toString : ()Ljava/lang/String;
    //   106: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   109: pop
    //   110: iload_2
    //   111: istore #4
    //   113: aload_3
    //   114: astore #5
    //   116: iload_2
    //   117: istore #6
    //   119: iload_2
    //   120: istore #7
    //   122: invokestatic newPullParser : ()Lorg/xmlpull/v1/XmlPullParser;
    //   125: astore #8
    //   127: iload_2
    //   128: istore #4
    //   130: aload_3
    //   131: astore #5
    //   133: iload_2
    //   134: istore #6
    //   136: iload_2
    //   137: istore #7
    //   139: aload #8
    //   141: aload_3
    //   142: aconst_null
    //   143: invokeinterface setInput : (Ljava/io/InputStream;Ljava/lang/String;)V
    //   148: iload_2
    //   149: istore #4
    //   151: aload_3
    //   152: astore #5
    //   154: iload_2
    //   155: istore #6
    //   157: iload_2
    //   158: istore #7
    //   160: aload #8
    //   162: invokeinterface getEventType : ()I
    //   167: istore #9
    //   169: iload #9
    //   171: istore #4
    //   173: iload #4
    //   175: iconst_2
    //   176: if_icmpeq -> 207
    //   179: iload_2
    //   180: istore #4
    //   182: aload_3
    //   183: astore #5
    //   185: iload_2
    //   186: istore #6
    //   188: iload_2
    //   189: istore #7
    //   191: aload #8
    //   193: invokeinterface next : ()I
    //   198: istore #9
    //   200: iload #9
    //   202: istore #4
    //   204: goto -> 173
    //   207: iload_2
    //   208: istore #9
    //   210: iload_2
    //   211: istore #4
    //   213: aload_3
    //   214: astore #5
    //   216: iload_2
    //   217: istore #6
    //   219: iload_2
    //   220: istore #7
    //   222: ldc_w 'accounts'
    //   225: aload #8
    //   227: invokeinterface getName : ()Ljava/lang/String;
    //   232: invokevirtual equals : (Ljava/lang/Object;)Z
    //   235: ifeq -> 1155
    //   238: iload_2
    //   239: istore #4
    //   241: aload_3
    //   242: astore #5
    //   244: iload_2
    //   245: istore #6
    //   247: iload_2
    //   248: istore #7
    //   250: aload #8
    //   252: aconst_null
    //   253: ldc 'listen-for-tickles'
    //   255: invokeinterface getAttributeValue : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   260: astore_1
    //   261: iload_2
    //   262: istore #4
    //   264: aload_3
    //   265: astore #5
    //   267: iload_2
    //   268: istore #6
    //   270: iload_2
    //   271: istore #7
    //   273: aload #8
    //   275: aconst_null
    //   276: ldc 'version'
    //   278: invokeinterface getAttributeValue : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   283: astore #10
    //   285: aload #10
    //   287: ifnonnull -> 293
    //   290: goto -> 315
    //   293: iload_2
    //   294: istore #4
    //   296: aload_3
    //   297: astore #5
    //   299: iload_2
    //   300: istore #6
    //   302: iload_2
    //   303: istore #7
    //   305: aload #10
    //   307: invokestatic parseInt : (Ljava/lang/String;)I
    //   310: istore #11
    //   312: goto -> 318
    //   315: iconst_0
    //   316: istore #11
    //   318: iload_2
    //   319: istore #4
    //   321: aload_3
    //   322: astore #5
    //   324: iload_2
    //   325: istore #6
    //   327: iload_2
    //   328: istore #7
    //   330: aload #8
    //   332: aconst_null
    //   333: ldc 'nextAuthorityId'
    //   335: invokeinterface getAttributeValue : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   340: astore #10
    //   342: aload #10
    //   344: ifnonnull -> 353
    //   347: iconst_0
    //   348: istore #9
    //   350: goto -> 372
    //   353: iload_2
    //   354: istore #4
    //   356: aload_3
    //   357: astore #5
    //   359: iload_2
    //   360: istore #6
    //   362: iload_2
    //   363: istore #7
    //   365: aload #10
    //   367: invokestatic parseInt : (Ljava/lang/String;)I
    //   370: istore #9
    //   372: iload_2
    //   373: istore #4
    //   375: aload_3
    //   376: astore #5
    //   378: iload_2
    //   379: istore #6
    //   381: iload_2
    //   382: istore #7
    //   384: aload_0
    //   385: aload_0
    //   386: getfield mNextAuthorityId : I
    //   389: iload #9
    //   391: invokestatic max : (II)I
    //   394: putfield mNextAuthorityId : I
    //   397: iload_2
    //   398: istore #4
    //   400: aload_3
    //   401: astore #5
    //   403: iload_2
    //   404: istore #6
    //   406: iload_2
    //   407: istore #7
    //   409: aload #8
    //   411: aconst_null
    //   412: ldc 'offsetInSeconds'
    //   414: invokeinterface getAttributeValue : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   419: astore #10
    //   421: aload #10
    //   423: ifnonnull -> 432
    //   426: iconst_0
    //   427: istore #9
    //   429: goto -> 451
    //   432: iload_2
    //   433: istore #4
    //   435: aload_3
    //   436: astore #5
    //   438: iload_2
    //   439: istore #6
    //   441: iload_2
    //   442: istore #7
    //   444: aload #10
    //   446: invokestatic parseInt : (Ljava/lang/String;)I
    //   449: istore #9
    //   451: iload_2
    //   452: istore #4
    //   454: aload_3
    //   455: astore #5
    //   457: iload_2
    //   458: istore #6
    //   460: iload_2
    //   461: istore #7
    //   463: aload_0
    //   464: iload #9
    //   466: putfield mSyncRandomOffset : I
    //   469: goto -> 491
    //   472: astore #5
    //   474: iload_2
    //   475: istore #4
    //   477: aload_3
    //   478: astore #5
    //   480: iload_2
    //   481: istore #6
    //   483: iload_2
    //   484: istore #7
    //   486: aload_0
    //   487: iconst_0
    //   488: putfield mSyncRandomOffset : I
    //   491: iload_2
    //   492: istore #4
    //   494: aload_3
    //   495: astore #5
    //   497: iload_2
    //   498: istore #6
    //   500: iload_2
    //   501: istore #7
    //   503: aload_0
    //   504: getfield mSyncRandomOffset : I
    //   507: ifne -> 571
    //   510: iload_2
    //   511: istore #4
    //   513: aload_3
    //   514: astore #5
    //   516: iload_2
    //   517: istore #6
    //   519: iload_2
    //   520: istore #7
    //   522: new java/util/Random
    //   525: astore #10
    //   527: iload_2
    //   528: istore #4
    //   530: aload_3
    //   531: astore #5
    //   533: iload_2
    //   534: istore #6
    //   536: iload_2
    //   537: istore #7
    //   539: aload #10
    //   541: invokestatic currentTimeMillis : ()J
    //   544: invokespecial <init> : (J)V
    //   547: iload_2
    //   548: istore #4
    //   550: aload_3
    //   551: astore #5
    //   553: iload_2
    //   554: istore #6
    //   556: iload_2
    //   557: istore #7
    //   559: aload_0
    //   560: aload #10
    //   562: ldc_w 86400
    //   565: invokevirtual nextInt : (I)I
    //   568: putfield mSyncRandomOffset : I
    //   571: iload_2
    //   572: istore #4
    //   574: aload_3
    //   575: astore #5
    //   577: iload_2
    //   578: istore #6
    //   580: iload_2
    //   581: istore #7
    //   583: aload_0
    //   584: getfield mMasterSyncAutomatically : Landroid/util/SparseArray;
    //   587: astore #10
    //   589: aload_1
    //   590: ifnull -> 621
    //   593: iload_2
    //   594: istore #4
    //   596: aload_3
    //   597: astore #5
    //   599: iload_2
    //   600: istore #6
    //   602: iload_2
    //   603: istore #7
    //   605: aload_1
    //   606: invokestatic parseBoolean : (Ljava/lang/String;)Z
    //   609: ifeq -> 615
    //   612: goto -> 621
    //   615: iconst_0
    //   616: istore #12
    //   618: goto -> 624
    //   621: iconst_1
    //   622: istore #12
    //   624: iload_2
    //   625: istore #4
    //   627: aload_3
    //   628: astore #5
    //   630: iload_2
    //   631: istore #6
    //   633: iload_2
    //   634: istore #7
    //   636: aload #10
    //   638: iconst_0
    //   639: iload #12
    //   641: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   644: invokevirtual put : (ILjava/lang/Object;)V
    //   647: iload_2
    //   648: istore #4
    //   650: aload_3
    //   651: astore #5
    //   653: iload_2
    //   654: istore #6
    //   656: iload_2
    //   657: istore #7
    //   659: aload #8
    //   661: invokeinterface next : ()I
    //   666: istore #9
    //   668: aconst_null
    //   669: astore #13
    //   671: aload #13
    //   673: astore #10
    //   675: iload #9
    //   677: istore #4
    //   679: iload_2
    //   680: istore #9
    //   682: aload #13
    //   684: astore_1
    //   685: aload #10
    //   687: astore #14
    //   689: iload #4
    //   691: iconst_2
    //   692: if_icmpne -> 1111
    //   695: iload_2
    //   696: istore #4
    //   698: aload_3
    //   699: astore #5
    //   701: iload_2
    //   702: istore #6
    //   704: iload_2
    //   705: istore #7
    //   707: aload #8
    //   709: invokeinterface getName : ()Ljava/lang/String;
    //   714: astore #15
    //   716: iload_2
    //   717: istore #4
    //   719: aload_3
    //   720: astore #5
    //   722: iload_2
    //   723: istore #6
    //   725: iload_2
    //   726: istore #7
    //   728: aload #8
    //   730: invokeinterface getDepth : ()I
    //   735: iconst_2
    //   736: if_icmpne -> 895
    //   739: iload_2
    //   740: istore #4
    //   742: aload_3
    //   743: astore #5
    //   745: iload_2
    //   746: istore #6
    //   748: iload_2
    //   749: istore #7
    //   751: ldc_w 'authority'
    //   754: aload #15
    //   756: invokevirtual equals : (Ljava/lang/Object;)Z
    //   759: ifeq -> 832
    //   762: iload_2
    //   763: istore #4
    //   765: aload_3
    //   766: astore #5
    //   768: iload_2
    //   769: istore #6
    //   771: iload_2
    //   772: istore #7
    //   774: aload_0
    //   775: aload #8
    //   777: iload #11
    //   779: invokespecial parseAuthority : (Lorg/xmlpull/v1/XmlPullParser;I)Lcom/lody/virtual/server/content/SyncStorageEngine$AuthorityInfo;
    //   782: astore #14
    //   784: iload_2
    //   785: istore #9
    //   787: iload_2
    //   788: istore #4
    //   790: aload_3
    //   791: astore #5
    //   793: iload_2
    //   794: istore #6
    //   796: iload_2
    //   797: istore #7
    //   799: aload #14
    //   801: getfield ident : I
    //   804: iload_2
    //   805: if_icmple -> 827
    //   808: iload_2
    //   809: istore #4
    //   811: aload_3
    //   812: astore #5
    //   814: iload_2
    //   815: istore #6
    //   817: iload_2
    //   818: istore #7
    //   820: aload #14
    //   822: getfield ident : I
    //   825: istore #9
    //   827: aconst_null
    //   828: astore_1
    //   829: goto -> 1111
    //   832: iload_2
    //   833: istore #9
    //   835: aload #13
    //   837: astore_1
    //   838: aload #10
    //   840: astore #14
    //   842: iload_2
    //   843: istore #4
    //   845: aload_3
    //   846: astore #5
    //   848: iload_2
    //   849: istore #6
    //   851: iload_2
    //   852: istore #7
    //   854: ldc 'listenForTickles'
    //   856: aload #15
    //   858: invokevirtual equals : (Ljava/lang/Object;)Z
    //   861: ifeq -> 1111
    //   864: iload_2
    //   865: istore #4
    //   867: aload_3
    //   868: astore #5
    //   870: iload_2
    //   871: istore #6
    //   873: iload_2
    //   874: istore #7
    //   876: aload_0
    //   877: aload #8
    //   879: invokespecial parseListenForTickles : (Lorg/xmlpull/v1/XmlPullParser;)V
    //   882: iload_2
    //   883: istore #9
    //   885: aload #13
    //   887: astore_1
    //   888: aload #10
    //   890: astore #14
    //   892: goto -> 1111
    //   895: iload_2
    //   896: istore #4
    //   898: aload_3
    //   899: astore #5
    //   901: iload_2
    //   902: istore #6
    //   904: iload_2
    //   905: istore #7
    //   907: aload #8
    //   909: invokeinterface getDepth : ()I
    //   914: iconst_3
    //   915: if_icmpne -> 997
    //   918: iload_2
    //   919: istore #9
    //   921: aload #13
    //   923: astore_1
    //   924: aload #10
    //   926: astore #14
    //   928: iload_2
    //   929: istore #4
    //   931: aload_3
    //   932: astore #5
    //   934: iload_2
    //   935: istore #6
    //   937: iload_2
    //   938: istore #7
    //   940: ldc_w 'periodicSync'
    //   943: aload #15
    //   945: invokevirtual equals : (Ljava/lang/Object;)Z
    //   948: ifeq -> 1111
    //   951: iload_2
    //   952: istore #9
    //   954: aload #13
    //   956: astore_1
    //   957: aload #10
    //   959: astore #14
    //   961: aload #10
    //   963: ifnull -> 1111
    //   966: iload_2
    //   967: istore #4
    //   969: aload_3
    //   970: astore #5
    //   972: iload_2
    //   973: istore #6
    //   975: iload_2
    //   976: istore #7
    //   978: aload_0
    //   979: aload #8
    //   981: aload #10
    //   983: invokespecial parsePeriodicSync : (Lorg/xmlpull/v1/XmlPullParser;Lcom/lody/virtual/server/content/SyncStorageEngine$AuthorityInfo;)Landroid/content/PeriodicSync;
    //   986: astore_1
    //   987: iload_2
    //   988: istore #9
    //   990: aload #10
    //   992: astore #14
    //   994: goto -> 1111
    //   997: iload_2
    //   998: istore #9
    //   1000: aload #13
    //   1002: astore_1
    //   1003: aload #10
    //   1005: astore #14
    //   1007: iload_2
    //   1008: istore #4
    //   1010: aload_3
    //   1011: astore #5
    //   1013: iload_2
    //   1014: istore #6
    //   1016: iload_2
    //   1017: istore #7
    //   1019: aload #8
    //   1021: invokeinterface getDepth : ()I
    //   1026: iconst_4
    //   1027: if_icmpne -> 1111
    //   1030: iload_2
    //   1031: istore #9
    //   1033: aload #13
    //   1035: astore_1
    //   1036: aload #10
    //   1038: astore #14
    //   1040: aload #13
    //   1042: ifnull -> 1111
    //   1045: iload_2
    //   1046: istore #9
    //   1048: aload #13
    //   1050: astore_1
    //   1051: aload #10
    //   1053: astore #14
    //   1055: iload_2
    //   1056: istore #4
    //   1058: aload_3
    //   1059: astore #5
    //   1061: iload_2
    //   1062: istore #6
    //   1064: iload_2
    //   1065: istore #7
    //   1067: ldc_w 'extra'
    //   1070: aload #15
    //   1072: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1075: ifeq -> 1111
    //   1078: iload_2
    //   1079: istore #4
    //   1081: aload_3
    //   1082: astore #5
    //   1084: iload_2
    //   1085: istore #6
    //   1087: iload_2
    //   1088: istore #7
    //   1090: aload_0
    //   1091: aload #8
    //   1093: aload #13
    //   1095: getfield extras : Landroid/os/Bundle;
    //   1098: invokespecial parseExtra : (Lorg/xmlpull/v1/XmlPullParser;Landroid/os/Bundle;)V
    //   1101: aload #10
    //   1103: astore #14
    //   1105: aload #13
    //   1107: astore_1
    //   1108: iload_2
    //   1109: istore #9
    //   1111: iload #9
    //   1113: istore #4
    //   1115: aload_3
    //   1116: astore #5
    //   1118: iload #9
    //   1120: istore #6
    //   1122: iload #9
    //   1124: istore #7
    //   1126: aload #8
    //   1128: invokeinterface next : ()I
    //   1133: istore #16
    //   1135: iload #9
    //   1137: istore_2
    //   1138: iload #16
    //   1140: istore #4
    //   1142: aload_1
    //   1143: astore #13
    //   1145: aload #14
    //   1147: astore #10
    //   1149: iload #16
    //   1151: iconst_1
    //   1152: if_icmpne -> 679
    //   1155: aload_0
    //   1156: iload #9
    //   1158: iconst_1
    //   1159: iadd
    //   1160: aload_0
    //   1161: getfield mNextAuthorityId : I
    //   1164: invokestatic max : (II)I
    //   1167: putfield mNextAuthorityId : I
    //   1170: aload_3
    //   1171: ifnull -> 1178
    //   1174: aload_3
    //   1175: invokevirtual close : ()V
    //   1178: aload_0
    //   1179: invokespecial maybeMigrateSettingsForRenamedAuthorities : ()Z
    //   1182: pop
    //   1183: return
    //   1184: astore_3
    //   1185: aload #5
    //   1187: astore_1
    //   1188: aload_3
    //   1189: astore #5
    //   1191: iload #4
    //   1193: istore_2
    //   1194: goto -> 1322
    //   1197: astore_1
    //   1198: iload #6
    //   1200: istore_2
    //   1201: goto -> 1219
    //   1204: astore_1
    //   1205: iload #7
    //   1207: istore_2
    //   1208: goto -> 1283
    //   1211: astore #5
    //   1213: goto -> 1322
    //   1216: astore_1
    //   1217: aconst_null
    //   1218: astore_3
    //   1219: aload_3
    //   1220: ifnonnull -> 1241
    //   1223: iload_2
    //   1224: istore #4
    //   1226: aload_3
    //   1227: astore #5
    //   1229: ldc 'SyncManager'
    //   1231: ldc_w 'No initial accounts'
    //   1234: invokestatic i : (Ljava/lang/String;Ljava/lang/String;)I
    //   1237: pop
    //   1238: goto -> 1257
    //   1241: iload_2
    //   1242: istore #4
    //   1244: aload_3
    //   1245: astore #5
    //   1247: ldc 'SyncManager'
    //   1249: ldc_w 'Error reading accounts'
    //   1252: aload_1
    //   1253: invokestatic w : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   1256: pop
    //   1257: aload_0
    //   1258: iload_2
    //   1259: iconst_1
    //   1260: iadd
    //   1261: aload_0
    //   1262: getfield mNextAuthorityId : I
    //   1265: invokestatic max : (II)I
    //   1268: putfield mNextAuthorityId : I
    //   1271: aload_3
    //   1272: ifnull -> 1279
    //   1275: aload_3
    //   1276: invokevirtual close : ()V
    //   1279: return
    //   1280: astore_1
    //   1281: aconst_null
    //   1282: astore_3
    //   1283: iload_2
    //   1284: istore #4
    //   1286: aload_3
    //   1287: astore #5
    //   1289: ldc 'SyncManager'
    //   1291: ldc_w 'Error reading accounts'
    //   1294: aload_1
    //   1295: invokestatic w : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   1298: pop
    //   1299: aload_0
    //   1300: iload_2
    //   1301: iconst_1
    //   1302: iadd
    //   1303: aload_0
    //   1304: getfield mNextAuthorityId : I
    //   1307: invokestatic max : (II)I
    //   1310: putfield mNextAuthorityId : I
    //   1313: aload_3
    //   1314: ifnull -> 1321
    //   1317: aload_3
    //   1318: invokevirtual close : ()V
    //   1321: return
    //   1322: aload_0
    //   1323: iload_2
    //   1324: iconst_1
    //   1325: iadd
    //   1326: aload_0
    //   1327: getfield mNextAuthorityId : I
    //   1330: invokestatic max : (II)I
    //   1333: putfield mNextAuthorityId : I
    //   1336: aload_1
    //   1337: ifnull -> 1344
    //   1340: aload_1
    //   1341: invokevirtual close : ()V
    //   1344: aload #5
    //   1346: athrow
    //   1347: astore #5
    //   1349: goto -> 315
    //   1352: astore #5
    //   1354: goto -> 397
    //   1357: astore #5
    //   1359: goto -> 1178
    //   1362: astore #5
    //   1364: goto -> 1279
    //   1367: astore #5
    //   1369: goto -> 1321
    //   1372: astore_3
    //   1373: goto -> 1344
    // Exception table:
    //   from	to	target	type
    //   4	12	1280	org/xmlpull/v1/XmlPullParserException
    //   4	12	1216	java/io/IOException
    //   4	12	1211	finally
    //   24	28	1204	org/xmlpull/v1/XmlPullParserException
    //   24	28	1197	java/io/IOException
    //   24	28	1184	finally
    //   40	44	1204	org/xmlpull/v1/XmlPullParserException
    //   40	44	1197	java/io/IOException
    //   40	44	1184	finally
    //   56	64	1204	org/xmlpull/v1/XmlPullParserException
    //   56	64	1197	java/io/IOException
    //   56	64	1184	finally
    //   76	88	1204	org/xmlpull/v1/XmlPullParserException
    //   76	88	1197	java/io/IOException
    //   76	88	1184	finally
    //   100	110	1204	org/xmlpull/v1/XmlPullParserException
    //   100	110	1197	java/io/IOException
    //   100	110	1184	finally
    //   122	127	1204	org/xmlpull/v1/XmlPullParserException
    //   122	127	1197	java/io/IOException
    //   122	127	1184	finally
    //   139	148	1204	org/xmlpull/v1/XmlPullParserException
    //   139	148	1197	java/io/IOException
    //   139	148	1184	finally
    //   160	169	1204	org/xmlpull/v1/XmlPullParserException
    //   160	169	1197	java/io/IOException
    //   160	169	1184	finally
    //   191	200	1204	org/xmlpull/v1/XmlPullParserException
    //   191	200	1197	java/io/IOException
    //   191	200	1184	finally
    //   222	238	1204	org/xmlpull/v1/XmlPullParserException
    //   222	238	1197	java/io/IOException
    //   222	238	1184	finally
    //   250	261	1204	org/xmlpull/v1/XmlPullParserException
    //   250	261	1197	java/io/IOException
    //   250	261	1184	finally
    //   273	285	1204	org/xmlpull/v1/XmlPullParserException
    //   273	285	1197	java/io/IOException
    //   273	285	1184	finally
    //   305	312	1347	java/lang/NumberFormatException
    //   305	312	1204	org/xmlpull/v1/XmlPullParserException
    //   305	312	1197	java/io/IOException
    //   305	312	1184	finally
    //   330	342	1204	org/xmlpull/v1/XmlPullParserException
    //   330	342	1197	java/io/IOException
    //   330	342	1184	finally
    //   365	372	1352	java/lang/NumberFormatException
    //   365	372	1204	org/xmlpull/v1/XmlPullParserException
    //   365	372	1197	java/io/IOException
    //   365	372	1184	finally
    //   384	397	1352	java/lang/NumberFormatException
    //   384	397	1204	org/xmlpull/v1/XmlPullParserException
    //   384	397	1197	java/io/IOException
    //   384	397	1184	finally
    //   409	421	1204	org/xmlpull/v1/XmlPullParserException
    //   409	421	1197	java/io/IOException
    //   409	421	1184	finally
    //   444	451	472	java/lang/NumberFormatException
    //   444	451	1204	org/xmlpull/v1/XmlPullParserException
    //   444	451	1197	java/io/IOException
    //   444	451	1184	finally
    //   463	469	472	java/lang/NumberFormatException
    //   463	469	1204	org/xmlpull/v1/XmlPullParserException
    //   463	469	1197	java/io/IOException
    //   463	469	1184	finally
    //   486	491	1204	org/xmlpull/v1/XmlPullParserException
    //   486	491	1197	java/io/IOException
    //   486	491	1184	finally
    //   503	510	1204	org/xmlpull/v1/XmlPullParserException
    //   503	510	1197	java/io/IOException
    //   503	510	1184	finally
    //   522	527	1204	org/xmlpull/v1/XmlPullParserException
    //   522	527	1197	java/io/IOException
    //   522	527	1184	finally
    //   539	547	1204	org/xmlpull/v1/XmlPullParserException
    //   539	547	1197	java/io/IOException
    //   539	547	1184	finally
    //   559	571	1204	org/xmlpull/v1/XmlPullParserException
    //   559	571	1197	java/io/IOException
    //   559	571	1184	finally
    //   583	589	1204	org/xmlpull/v1/XmlPullParserException
    //   583	589	1197	java/io/IOException
    //   583	589	1184	finally
    //   605	612	1204	org/xmlpull/v1/XmlPullParserException
    //   605	612	1197	java/io/IOException
    //   605	612	1184	finally
    //   636	647	1204	org/xmlpull/v1/XmlPullParserException
    //   636	647	1197	java/io/IOException
    //   636	647	1184	finally
    //   659	668	1204	org/xmlpull/v1/XmlPullParserException
    //   659	668	1197	java/io/IOException
    //   659	668	1184	finally
    //   707	716	1204	org/xmlpull/v1/XmlPullParserException
    //   707	716	1197	java/io/IOException
    //   707	716	1184	finally
    //   728	739	1204	org/xmlpull/v1/XmlPullParserException
    //   728	739	1197	java/io/IOException
    //   728	739	1184	finally
    //   751	762	1204	org/xmlpull/v1/XmlPullParserException
    //   751	762	1197	java/io/IOException
    //   751	762	1184	finally
    //   774	784	1204	org/xmlpull/v1/XmlPullParserException
    //   774	784	1197	java/io/IOException
    //   774	784	1184	finally
    //   799	808	1204	org/xmlpull/v1/XmlPullParserException
    //   799	808	1197	java/io/IOException
    //   799	808	1184	finally
    //   820	827	1204	org/xmlpull/v1/XmlPullParserException
    //   820	827	1197	java/io/IOException
    //   820	827	1184	finally
    //   854	864	1204	org/xmlpull/v1/XmlPullParserException
    //   854	864	1197	java/io/IOException
    //   854	864	1184	finally
    //   876	882	1204	org/xmlpull/v1/XmlPullParserException
    //   876	882	1197	java/io/IOException
    //   876	882	1184	finally
    //   907	918	1204	org/xmlpull/v1/XmlPullParserException
    //   907	918	1197	java/io/IOException
    //   907	918	1184	finally
    //   940	951	1204	org/xmlpull/v1/XmlPullParserException
    //   940	951	1197	java/io/IOException
    //   940	951	1184	finally
    //   978	987	1204	org/xmlpull/v1/XmlPullParserException
    //   978	987	1197	java/io/IOException
    //   978	987	1184	finally
    //   1019	1030	1204	org/xmlpull/v1/XmlPullParserException
    //   1019	1030	1197	java/io/IOException
    //   1019	1030	1184	finally
    //   1067	1078	1204	org/xmlpull/v1/XmlPullParserException
    //   1067	1078	1197	java/io/IOException
    //   1067	1078	1184	finally
    //   1090	1101	1204	org/xmlpull/v1/XmlPullParserException
    //   1090	1101	1197	java/io/IOException
    //   1090	1101	1184	finally
    //   1126	1135	1204	org/xmlpull/v1/XmlPullParserException
    //   1126	1135	1197	java/io/IOException
    //   1126	1135	1184	finally
    //   1174	1178	1357	java/io/IOException
    //   1229	1238	1184	finally
    //   1247	1257	1184	finally
    //   1275	1279	1362	java/io/IOException
    //   1289	1299	1184	finally
    //   1317	1321	1367	java/io/IOException
    //   1340	1344	1372	java/io/IOException
  }
  
  private void readAndDeleteLegacyAccountInfoLocked() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mContext : Landroid/content/Context;
    //   4: ldc_w 'syncmanager.db'
    //   7: invokevirtual getDatabasePath : (Ljava/lang/String;)Ljava/io/File;
    //   10: astore_1
    //   11: aload_1
    //   12: invokevirtual exists : ()Z
    //   15: ifne -> 19
    //   18: return
    //   19: aload_1
    //   20: invokevirtual getPath : ()Ljava/lang/String;
    //   23: astore_2
    //   24: aload_2
    //   25: aconst_null
    //   26: iconst_1
    //   27: invokestatic openDatabase : (Ljava/lang/String;Landroid/database/sqlite/SQLiteDatabase$CursorFactory;I)Landroid/database/sqlite/SQLiteDatabase;
    //   30: astore_3
    //   31: goto -> 37
    //   34: astore_1
    //   35: aconst_null
    //   36: astore_3
    //   37: aload_3
    //   38: ifnull -> 1050
    //   41: aload_3
    //   42: invokevirtual getVersion : ()I
    //   45: bipush #11
    //   47: if_icmplt -> 56
    //   50: iconst_1
    //   51: istore #4
    //   53: goto -> 59
    //   56: iconst_0
    //   57: istore #4
    //   59: ldc 'SyncManager'
    //   61: ldc_w 'Reading legacy sync accounts db'
    //   64: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   67: pop
    //   68: new android/database/sqlite/SQLiteQueryBuilder
    //   71: dup
    //   72: invokespecial <init> : ()V
    //   75: astore #5
    //   77: aload #5
    //   79: ldc_w 'stats, status'
    //   82: invokevirtual setTables : (Ljava/lang/String;)V
    //   85: new java/util/HashMap
    //   88: dup
    //   89: invokespecial <init> : ()V
    //   92: astore #6
    //   94: aload #6
    //   96: ldc_w '_id'
    //   99: ldc_w 'status._id as _id'
    //   102: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   105: pop
    //   106: ldc_w 'account'
    //   109: astore #7
    //   111: aload #6
    //   113: ldc_w 'account'
    //   116: ldc_w 'stats.account as account'
    //   119: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   122: pop
    //   123: ldc_w 'account_type'
    //   126: astore #8
    //   128: iload #4
    //   130: ifeq -> 145
    //   133: aload #6
    //   135: ldc_w 'account_type'
    //   138: ldc_w 'stats.account_type as account_type'
    //   141: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   144: pop
    //   145: ldc_w 'authority'
    //   148: astore #9
    //   150: aload #6
    //   152: ldc_w 'authority'
    //   155: ldc_w 'stats.authority as authority'
    //   158: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   161: pop
    //   162: ldc_w 'totalElapsedTime'
    //   165: astore_1
    //   166: aload #6
    //   168: ldc_w 'totalElapsedTime'
    //   171: ldc_w 'totalElapsedTime'
    //   174: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   177: pop
    //   178: aload #6
    //   180: ldc_w 'numSyncs'
    //   183: ldc_w 'numSyncs'
    //   186: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   189: pop
    //   190: aload #6
    //   192: ldc_w 'numSourceLocal'
    //   195: ldc_w 'numSourceLocal'
    //   198: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   201: pop
    //   202: aload #6
    //   204: ldc_w 'numSourcePoll'
    //   207: ldc_w 'numSourcePoll'
    //   210: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   213: pop
    //   214: aload #6
    //   216: ldc_w 'numSourceServer'
    //   219: ldc_w 'numSourceServer'
    //   222: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   225: pop
    //   226: aload #6
    //   228: ldc_w 'numSourceUser'
    //   231: ldc_w 'numSourceUser'
    //   234: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   237: pop
    //   238: aload #6
    //   240: ldc_w 'lastSuccessSource'
    //   243: ldc_w 'lastSuccessSource'
    //   246: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   249: pop
    //   250: aload #6
    //   252: ldc_w 'lastSuccessTime'
    //   255: ldc_w 'lastSuccessTime'
    //   258: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   261: pop
    //   262: aload #6
    //   264: ldc_w 'lastFailureSource'
    //   267: ldc_w 'lastFailureSource'
    //   270: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   273: pop
    //   274: ldc_w 'lastFailureSource'
    //   277: astore #10
    //   279: aload #6
    //   281: ldc_w 'lastFailureTime'
    //   284: ldc_w 'lastFailureTime'
    //   287: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   290: pop
    //   291: ldc_w 'lastFailureTime'
    //   294: astore #11
    //   296: aload #6
    //   298: ldc_w 'lastFailureMesg'
    //   301: ldc_w 'lastFailureMesg'
    //   304: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   307: pop
    //   308: ldc_w 'lastFailureMesg'
    //   311: astore #12
    //   313: ldc_w 'pending'
    //   316: astore #13
    //   318: aload #6
    //   320: ldc_w 'pending'
    //   323: ldc_w 'pending'
    //   326: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   329: pop
    //   330: aload #5
    //   332: aload #6
    //   334: invokevirtual setProjectionMap : (Ljava/util/Map;)V
    //   337: aload #5
    //   339: ldc_w 'stats._id = status.stats_id'
    //   342: invokevirtual appendWhere : (Ljava/lang/CharSequence;)V
    //   345: ldc_w 'numSourceUser'
    //   348: astore #6
    //   350: ldc_w 'numSourceServer'
    //   353: astore #14
    //   355: ldc_w 'numSourcePoll'
    //   358: astore #15
    //   360: ldc_w 'numSourceLocal'
    //   363: astore #16
    //   365: aload #5
    //   367: aload_3
    //   368: aconst_null
    //   369: aconst_null
    //   370: aconst_null
    //   371: aconst_null
    //   372: aconst_null
    //   373: aconst_null
    //   374: invokevirtual query : (Landroid/database/sqlite/SQLiteDatabase;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   377: astore #17
    //   379: aload #17
    //   381: invokeinterface moveToNext : ()Z
    //   386: ifeq -> 781
    //   389: aload #17
    //   391: aload #17
    //   393: aload #7
    //   395: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   400: invokeinterface getString : (I)Ljava/lang/String;
    //   405: astore #18
    //   407: iload #4
    //   409: ifeq -> 433
    //   412: aload #17
    //   414: aload #17
    //   416: aload #8
    //   418: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   423: invokeinterface getString : (I)Ljava/lang/String;
    //   428: astore #5
    //   430: goto -> 436
    //   433: aconst_null
    //   434: astore #5
    //   436: aload #5
    //   438: astore #19
    //   440: aload #5
    //   442: ifnonnull -> 450
    //   445: ldc_w 'com.google'
    //   448: astore #19
    //   450: aload #17
    //   452: aload #17
    //   454: aload #9
    //   456: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   461: invokeinterface getString : (I)Ljava/lang/String;
    //   466: astore #5
    //   468: new android/accounts/Account
    //   471: dup
    //   472: aload #18
    //   474: aload #19
    //   476: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
    //   479: astore #19
    //   481: aload_1
    //   482: astore #18
    //   484: aload_0
    //   485: aload #19
    //   487: iconst_0
    //   488: aload #5
    //   490: iconst_m1
    //   491: iconst_0
    //   492: invokespecial getOrCreateAuthorityLocked : (Landroid/accounts/Account;ILjava/lang/String;IZ)Lcom/lody/virtual/server/content/SyncStorageEngine$AuthorityInfo;
    //   495: astore #20
    //   497: aload #20
    //   499: ifnull -> 778
    //   502: aload_0
    //   503: getfield mSyncStatus : Landroid/util/SparseArray;
    //   506: invokevirtual size : ()I
    //   509: istore #21
    //   511: aconst_null
    //   512: astore #5
    //   514: iload #21
    //   516: ifle -> 563
    //   519: iinc #21, -1
    //   522: aload_0
    //   523: getfield mSyncStatus : Landroid/util/SparseArray;
    //   526: iload #21
    //   528: invokevirtual valueAt : (I)Ljava/lang/Object;
    //   531: checkcast android/content/SyncStatusInfo
    //   534: astore #19
    //   536: aload #19
    //   538: astore #5
    //   540: aload #19
    //   542: getfield authorityId : I
    //   545: aload #20
    //   547: getfield ident : I
    //   550: if_icmpne -> 514
    //   553: iconst_1
    //   554: istore #21
    //   556: aload #19
    //   558: astore #5
    //   560: goto -> 566
    //   563: iconst_0
    //   564: istore #21
    //   566: iload #21
    //   568: ifne -> 599
    //   571: new android/content/SyncStatusInfo
    //   574: dup
    //   575: aload #20
    //   577: getfield ident : I
    //   580: invokespecial <init> : (I)V
    //   583: astore #5
    //   585: aload_0
    //   586: getfield mSyncStatus : Landroid/util/SparseArray;
    //   589: aload #20
    //   591: getfield ident : I
    //   594: aload #5
    //   596: invokevirtual put : (ILjava/lang/Object;)V
    //   599: aload #5
    //   601: aload #17
    //   603: aload #18
    //   605: invokestatic getLongColumn : (Landroid/database/Cursor;Ljava/lang/String;)J
    //   608: putfield totalElapsedTime : J
    //   611: aload #5
    //   613: aload #17
    //   615: ldc_w 'numSyncs'
    //   618: invokestatic getIntColumn : (Landroid/database/Cursor;Ljava/lang/String;)I
    //   621: putfield numSyncs : I
    //   624: aload #5
    //   626: aload #17
    //   628: aload #16
    //   630: invokestatic getIntColumn : (Landroid/database/Cursor;Ljava/lang/String;)I
    //   633: putfield numSourceLocal : I
    //   636: aload #5
    //   638: aload #17
    //   640: aload #15
    //   642: invokestatic getIntColumn : (Landroid/database/Cursor;Ljava/lang/String;)I
    //   645: putfield numSourcePoll : I
    //   648: aload #5
    //   650: aload #17
    //   652: aload #14
    //   654: invokestatic getIntColumn : (Landroid/database/Cursor;Ljava/lang/String;)I
    //   657: putfield numSourceServer : I
    //   660: aload #5
    //   662: aload #17
    //   664: aload #6
    //   666: invokestatic getIntColumn : (Landroid/database/Cursor;Ljava/lang/String;)I
    //   669: putfield numSourceUser : I
    //   672: aload #5
    //   674: iconst_0
    //   675: putfield numSourcePeriodic : I
    //   678: aload #5
    //   680: aload #17
    //   682: ldc_w 'lastSuccessSource'
    //   685: invokestatic getIntColumn : (Landroid/database/Cursor;Ljava/lang/String;)I
    //   688: putfield lastSuccessSource : I
    //   691: aload #5
    //   693: aload #17
    //   695: ldc_w 'lastSuccessTime'
    //   698: invokestatic getLongColumn : (Landroid/database/Cursor;Ljava/lang/String;)J
    //   701: putfield lastSuccessTime : J
    //   704: aload #5
    //   706: aload #17
    //   708: aload #10
    //   710: invokestatic getIntColumn : (Landroid/database/Cursor;Ljava/lang/String;)I
    //   713: putfield lastFailureSource : I
    //   716: aload #5
    //   718: aload #17
    //   720: aload #11
    //   722: invokestatic getLongColumn : (Landroid/database/Cursor;Ljava/lang/String;)J
    //   725: putfield lastFailureTime : J
    //   728: aload #5
    //   730: aload #17
    //   732: aload #17
    //   734: aload #12
    //   736: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   741: invokeinterface getString : (I)Ljava/lang/String;
    //   746: putfield lastFailureMesg : Ljava/lang/String;
    //   749: aload #17
    //   751: aload #13
    //   753: invokestatic getIntColumn : (Landroid/database/Cursor;Ljava/lang/String;)I
    //   756: ifeq -> 765
    //   759: iconst_1
    //   760: istore #22
    //   762: goto -> 768
    //   765: iconst_0
    //   766: istore #22
    //   768: aload #5
    //   770: iload #22
    //   772: putfield pending : Z
    //   775: goto -> 778
    //   778: goto -> 379
    //   781: aload #17
    //   783: invokeinterface close : ()V
    //   788: new android/database/sqlite/SQLiteQueryBuilder
    //   791: dup
    //   792: invokespecial <init> : ()V
    //   795: astore_1
    //   796: aload_1
    //   797: ldc_w 'settings'
    //   800: invokevirtual setTables : (Ljava/lang/String;)V
    //   803: aload_1
    //   804: aload_3
    //   805: aconst_null
    //   806: aconst_null
    //   807: aconst_null
    //   808: aconst_null
    //   809: aconst_null
    //   810: aconst_null
    //   811: invokevirtual query : (Landroid/database/sqlite/SQLiteDatabase;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   814: astore_1
    //   815: aload_1
    //   816: invokeinterface moveToNext : ()Z
    //   821: ifeq -> 1028
    //   824: aload_1
    //   825: aload_1
    //   826: ldc_w 'name'
    //   829: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   834: invokeinterface getString : (I)Ljava/lang/String;
    //   839: astore #12
    //   841: aload_1
    //   842: aload_1
    //   843: ldc_w 'value'
    //   846: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   851: invokeinterface getString : (I)Ljava/lang/String;
    //   856: astore #5
    //   858: aload #12
    //   860: ifnonnull -> 866
    //   863: goto -> 815
    //   866: aload #12
    //   868: ldc_w 'listen_for_tickles'
    //   871: invokevirtual equals : (Ljava/lang/Object;)Z
    //   874: ifeq -> 912
    //   877: aload #5
    //   879: ifnull -> 899
    //   882: aload #5
    //   884: invokestatic parseBoolean : (Ljava/lang/String;)Z
    //   887: ifeq -> 893
    //   890: goto -> 899
    //   893: iconst_0
    //   894: istore #22
    //   896: goto -> 902
    //   899: iconst_1
    //   900: istore #22
    //   902: aload_0
    //   903: iload #22
    //   905: iconst_0
    //   906: invokevirtual setMasterSyncAutomatically : (ZI)V
    //   909: goto -> 1025
    //   912: aload #12
    //   914: ldc_w 'sync_provider_'
    //   917: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   920: ifeq -> 1025
    //   923: aload #12
    //   925: bipush #14
    //   927: aload #12
    //   929: invokevirtual length : ()I
    //   932: invokevirtual substring : (II)Ljava/lang/String;
    //   935: astore #6
    //   937: aload_0
    //   938: getfield mAuthorities : Landroid/util/SparseArray;
    //   941: invokevirtual size : ()I
    //   944: istore #4
    //   946: iload #4
    //   948: ifle -> 1025
    //   951: iinc #4, -1
    //   954: aload_0
    //   955: getfield mAuthorities : Landroid/util/SparseArray;
    //   958: iload #4
    //   960: invokevirtual valueAt : (I)Ljava/lang/Object;
    //   963: checkcast com/lody/virtual/server/content/SyncStorageEngine$AuthorityInfo
    //   966: astore #12
    //   968: aload #12
    //   970: getfield authority : Ljava/lang/String;
    //   973: aload #6
    //   975: invokevirtual equals : (Ljava/lang/Object;)Z
    //   978: ifeq -> 1022
    //   981: aload #5
    //   983: ifnull -> 1003
    //   986: aload #5
    //   988: invokestatic parseBoolean : (Ljava/lang/String;)Z
    //   991: ifeq -> 997
    //   994: goto -> 1003
    //   997: iconst_0
    //   998: istore #22
    //   1000: goto -> 1006
    //   1003: iconst_1
    //   1004: istore #22
    //   1006: aload #12
    //   1008: iload #22
    //   1010: putfield enabled : Z
    //   1013: aload #12
    //   1015: iconst_1
    //   1016: putfield syncable : I
    //   1019: goto -> 946
    //   1022: goto -> 946
    //   1025: goto -> 815
    //   1028: aload_1
    //   1029: invokeinterface close : ()V
    //   1034: aload_3
    //   1035: invokevirtual close : ()V
    //   1038: new java/io/File
    //   1041: dup
    //   1042: aload_2
    //   1043: invokespecial <init> : (Ljava/lang/String;)V
    //   1046: invokevirtual delete : ()Z
    //   1049: pop
    //   1050: return
    // Exception table:
    //   from	to	target	type
    //   24	31	34	android/database/sqlite/SQLiteException
  }
  
  private void readPendingOperationsLocked() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mPendingFile : Lcom/lody/virtual/helper/utils/AtomicFile;
    //   4: invokevirtual getBaseFile : ()Ljava/io/File;
    //   7: invokevirtual exists : ()Z
    //   10: ifne -> 23
    //   13: ldc 'SyncManagerFile'
    //   15: ldc_w 'No pending operation file.'
    //   18: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   21: pop
    //   22: return
    //   23: aconst_null
    //   24: astore_1
    //   25: aconst_null
    //   26: astore_2
    //   27: aconst_null
    //   28: astore_3
    //   29: aload_0
    //   30: getfield mPendingFile : Lcom/lody/virtual/helper/utils/AtomicFile;
    //   33: invokevirtual openRead : ()Ljava/io/FileInputStream;
    //   36: astore #4
    //   38: invokestatic newPullParser : ()Lorg/xmlpull/v1/XmlPullParser;
    //   41: astore_3
    //   42: aload_3
    //   43: aload #4
    //   45: aconst_null
    //   46: invokeinterface setInput : (Ljava/io/InputStream;Ljava/lang/String;)V
    //   51: aload_3
    //   52: invokeinterface getEventType : ()I
    //   57: istore #5
    //   59: iload #5
    //   61: iconst_2
    //   62: if_icmpeq -> 82
    //   65: iload #5
    //   67: iconst_1
    //   68: if_icmpeq -> 82
    //   71: aload_3
    //   72: invokeinterface next : ()I
    //   77: istore #5
    //   79: goto -> 59
    //   82: iload #5
    //   84: iconst_1
    //   85: if_icmpne -> 99
    //   88: aload #4
    //   90: ifnull -> 98
    //   93: aload #4
    //   95: invokevirtual close : ()V
    //   98: return
    //   99: aload_3
    //   100: invokeinterface getName : ()Ljava/lang/String;
    //   105: pop
    //   106: iload #5
    //   108: iconst_2
    //   109: if_icmpne -> 610
    //   112: aload_3
    //   113: invokeinterface getName : ()Ljava/lang/String;
    //   118: astore #6
    //   120: aload_3
    //   121: invokeinterface getDepth : ()I
    //   126: iconst_1
    //   127: if_icmpne -> 587
    //   130: ldc_w 'op'
    //   133: aload #6
    //   135: invokevirtual equals : (Ljava/lang/Object;)Z
    //   138: ifeq -> 587
    //   141: aload_3
    //   142: aconst_null
    //   143: ldc 'version'
    //   145: invokeinterface getAttributeValue : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   150: astore_1
    //   151: aload_1
    //   152: ifnull -> 534
    //   155: aload_1
    //   156: invokestatic parseInt : (Ljava/lang/String;)I
    //   159: iconst_3
    //   160: if_icmpne -> 534
    //   163: aload_3
    //   164: aconst_null
    //   165: ldc 'authority_id'
    //   167: invokeinterface getAttributeValue : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   172: invokestatic valueOf : (Ljava/lang/String;)Ljava/lang/Integer;
    //   175: invokevirtual intValue : ()I
    //   178: istore #7
    //   180: aload_3
    //   181: aconst_null
    //   182: ldc 'expedited'
    //   184: invokeinterface getAttributeValue : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   189: invokestatic valueOf : (Ljava/lang/String;)Ljava/lang/Boolean;
    //   192: invokevirtual booleanValue : ()Z
    //   195: istore #8
    //   197: aload_3
    //   198: aconst_null
    //   199: ldc 'source'
    //   201: invokeinterface getAttributeValue : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   206: invokestatic valueOf : (Ljava/lang/String;)Ljava/lang/Integer;
    //   209: invokevirtual intValue : ()I
    //   212: istore #5
    //   214: aload_3
    //   215: aconst_null
    //   216: ldc 'reason'
    //   218: invokeinterface getAttributeValue : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   223: invokestatic valueOf : (Ljava/lang/String;)Ljava/lang/Integer;
    //   226: invokevirtual intValue : ()I
    //   229: istore #9
    //   231: aload_0
    //   232: getfield mAuthorities : Landroid/util/SparseArray;
    //   235: iload #7
    //   237: invokevirtual get : (I)Ljava/lang/Object;
    //   240: checkcast com/lody/virtual/server/content/SyncStorageEngine$AuthorityInfo
    //   243: astore_2
    //   244: new java/lang/StringBuilder
    //   247: astore #6
    //   249: aload #6
    //   251: invokespecial <init> : ()V
    //   254: aload #6
    //   256: iload #7
    //   258: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   261: pop
    //   262: aload #6
    //   264: ldc_w ' '
    //   267: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   270: pop
    //   271: aload #6
    //   273: iload #8
    //   275: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   278: pop
    //   279: aload #6
    //   281: ldc_w ' '
    //   284: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   287: pop
    //   288: aload #6
    //   290: iload #5
    //   292: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   295: pop
    //   296: aload #6
    //   298: ldc_w ' '
    //   301: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   304: pop
    //   305: aload #6
    //   307: iload #9
    //   309: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   312: pop
    //   313: ldc 'SyncManagerFile'
    //   315: aload #6
    //   317: invokevirtual toString : ()Ljava/lang/String;
    //   320: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   323: pop
    //   324: aload_2
    //   325: ifnull -> 484
    //   328: new com/lody/virtual/server/content/SyncStorageEngine$PendingOperation
    //   331: astore #6
    //   333: aload_2
    //   334: getfield account : Landroid/accounts/Account;
    //   337: astore_1
    //   338: aload_2
    //   339: getfield userId : I
    //   342: istore #7
    //   344: aload_2
    //   345: getfield authority : Ljava/lang/String;
    //   348: astore #10
    //   350: new android/os/Bundle
    //   353: astore_2
    //   354: aload_2
    //   355: invokespecial <init> : ()V
    //   358: aload #6
    //   360: aload_1
    //   361: iload #7
    //   363: iload #9
    //   365: iload #5
    //   367: aload #10
    //   369: aload_2
    //   370: iload #8
    //   372: invokespecial <init> : (Landroid/accounts/Account;IIILjava/lang/String;Landroid/os/Bundle;Z)V
    //   375: aload #6
    //   377: aconst_null
    //   378: putfield flatExtras : [B
    //   381: aload_0
    //   382: getfield mPendingOperations : Ljava/util/ArrayList;
    //   385: aload #6
    //   387: invokevirtual add : (Ljava/lang/Object;)Z
    //   390: pop
    //   391: new java/lang/StringBuilder
    //   394: astore_1
    //   395: aload_1
    //   396: invokespecial <init> : ()V
    //   399: aload_1
    //   400: ldc_w 'Adding pending op: '
    //   403: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   406: pop
    //   407: aload_1
    //   408: aload #6
    //   410: getfield authority : Ljava/lang/String;
    //   413: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   416: pop
    //   417: aload_1
    //   418: ldc_w ' src='
    //   421: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   424: pop
    //   425: aload_1
    //   426: aload #6
    //   428: getfield syncSource : I
    //   431: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   434: pop
    //   435: aload_1
    //   436: ldc_w ' reason='
    //   439: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   442: pop
    //   443: aload_1
    //   444: aload #6
    //   446: getfield reason : I
    //   449: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   452: pop
    //   453: aload_1
    //   454: ldc_w ' expedited='
    //   457: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   460: pop
    //   461: aload_1
    //   462: aload #6
    //   464: getfield expedited : Z
    //   467: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   470: pop
    //   471: ldc 'SyncManagerFile'
    //   473: aload_1
    //   474: invokevirtual toString : ()Ljava/lang/String;
    //   477: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   480: pop
    //   481: goto -> 610
    //   484: new java/lang/StringBuilder
    //   487: astore #6
    //   489: aload #6
    //   491: invokespecial <init> : ()V
    //   494: aload #6
    //   496: ldc_w 'No authority found for '
    //   499: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   502: pop
    //   503: aload #6
    //   505: iload #7
    //   507: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   510: pop
    //   511: aload #6
    //   513: ldc_w ', skipping'
    //   516: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   519: pop
    //   520: ldc 'SyncManagerFile'
    //   522: aload #6
    //   524: invokevirtual toString : ()Ljava/lang/String;
    //   527: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   530: pop
    //   531: goto -> 610
    //   534: new java/lang/StringBuilder
    //   537: astore #6
    //   539: aload #6
    //   541: invokespecial <init> : ()V
    //   544: aload #6
    //   546: ldc_w 'Unknown pending operation version '
    //   549: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   552: pop
    //   553: aload #6
    //   555: aload_1
    //   556: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   559: pop
    //   560: ldc 'SyncManager'
    //   562: aload #6
    //   564: invokevirtual toString : ()Ljava/lang/String;
    //   567: invokestatic w : (Ljava/lang/String;Ljava/lang/String;)I
    //   570: pop
    //   571: new java/io/IOException
    //   574: astore #6
    //   576: aload #6
    //   578: ldc_w 'Unknown version.'
    //   581: invokespecial <init> : (Ljava/lang/String;)V
    //   584: aload #6
    //   586: athrow
    //   587: aload_3
    //   588: invokeinterface getDepth : ()I
    //   593: pop
    //   594: goto -> 610
    //   597: astore #6
    //   599: ldc 'SyncManager'
    //   601: ldc_w 'Invalid data in xml file.'
    //   604: aload #6
    //   606: invokestatic d : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   609: pop
    //   610: aload_3
    //   611: invokeinterface next : ()I
    //   616: istore #9
    //   618: iload #9
    //   620: istore #5
    //   622: iload #9
    //   624: iconst_1
    //   625: if_icmpne -> 106
    //   628: aload #4
    //   630: ifnull -> 727
    //   633: aload #4
    //   635: invokevirtual close : ()V
    //   638: goto -> 727
    //   641: astore #6
    //   643: aload #4
    //   645: astore_3
    //   646: aload #6
    //   648: astore #4
    //   650: goto -> 728
    //   653: astore #6
    //   655: goto -> 673
    //   658: astore #6
    //   660: goto -> 705
    //   663: astore #4
    //   665: goto -> 728
    //   668: astore #6
    //   670: aload_1
    //   671: astore #4
    //   673: aload #4
    //   675: astore_3
    //   676: ldc 'SyncManagerFile'
    //   678: ldc_w 'Error parsing pending ops xml.'
    //   681: aload #6
    //   683: invokestatic w : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   686: pop
    //   687: aload #4
    //   689: ifnull -> 727
    //   692: aload #4
    //   694: invokevirtual close : ()V
    //   697: goto -> 727
    //   700: astore #6
    //   702: aload_2
    //   703: astore #4
    //   705: aload #4
    //   707: astore_3
    //   708: ldc 'SyncManagerFile'
    //   710: ldc_w 'Error reading pending data.'
    //   713: aload #6
    //   715: invokestatic w : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   718: pop
    //   719: aload #4
    //   721: ifnull -> 727
    //   724: goto -> 692
    //   727: return
    //   728: aload_3
    //   729: ifnull -> 736
    //   732: aload_3
    //   733: invokevirtual close : ()V
    //   736: aload #4
    //   738: athrow
    //   739: astore_3
    //   740: goto -> 98
    //   743: astore_3
    //   744: goto -> 727
    //   747: astore_3
    //   748: goto -> 736
    // Exception table:
    //   from	to	target	type
    //   29	38	700	java/io/IOException
    //   29	38	668	org/xmlpull/v1/XmlPullParserException
    //   29	38	663	finally
    //   38	59	658	java/io/IOException
    //   38	59	653	org/xmlpull/v1/XmlPullParserException
    //   38	59	641	finally
    //   71	79	658	java/io/IOException
    //   71	79	653	org/xmlpull/v1/XmlPullParserException
    //   71	79	641	finally
    //   93	98	739	java/io/IOException
    //   99	106	658	java/io/IOException
    //   99	106	653	org/xmlpull/v1/XmlPullParserException
    //   99	106	641	finally
    //   112	151	597	java/lang/NumberFormatException
    //   112	151	658	java/io/IOException
    //   112	151	653	org/xmlpull/v1/XmlPullParserException
    //   112	151	641	finally
    //   155	324	597	java/lang/NumberFormatException
    //   155	324	658	java/io/IOException
    //   155	324	653	org/xmlpull/v1/XmlPullParserException
    //   155	324	641	finally
    //   328	481	597	java/lang/NumberFormatException
    //   328	481	658	java/io/IOException
    //   328	481	653	org/xmlpull/v1/XmlPullParserException
    //   328	481	641	finally
    //   484	531	597	java/lang/NumberFormatException
    //   484	531	658	java/io/IOException
    //   484	531	653	org/xmlpull/v1/XmlPullParserException
    //   484	531	641	finally
    //   534	587	597	java/lang/NumberFormatException
    //   534	587	658	java/io/IOException
    //   534	587	653	org/xmlpull/v1/XmlPullParserException
    //   534	587	641	finally
    //   587	594	597	java/lang/NumberFormatException
    //   587	594	658	java/io/IOException
    //   587	594	653	org/xmlpull/v1/XmlPullParserException
    //   587	594	641	finally
    //   599	610	658	java/io/IOException
    //   599	610	653	org/xmlpull/v1/XmlPullParserException
    //   599	610	641	finally
    //   610	618	658	java/io/IOException
    //   610	618	653	org/xmlpull/v1/XmlPullParserException
    //   610	618	641	finally
    //   633	638	743	java/io/IOException
    //   676	687	663	finally
    //   692	697	743	java/io/IOException
    //   708	719	663	finally
    //   732	736	747	java/io/IOException
  }
  
  private void readStatisticsLocked() {
    try {
      byte[] arrayOfByte = this.mStatisticsFile.readFully();
      Parcel parcel = Parcel.obtain();
      int i = arrayOfByte.length;
      byte b = 0;
      parcel.unmarshall(arrayOfByte, 0, i);
      parcel.setDataPosition(0);
      while (true) {
        int j = parcel.readInt();
        if (j != 0) {
          if (j == 101 || j == 100) {
            int k = parcel.readInt();
            i = k;
            if (j == 100)
              i = k - 2009 + 14245; 
            DayStats dayStats = new DayStats();
            this(i);
            dayStats.successCount = parcel.readInt();
            dayStats.successTime = parcel.readLong();
            dayStats.failureCount = parcel.readInt();
            dayStats.failureTime = parcel.readLong();
            if (b < this.mDayStats.length) {
              this.mDayStats[b] = dayStats;
              b++;
            } 
            continue;
          } 
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("Unknown stats token: ");
          stringBuilder.append(j);
          Log.w("SyncManager", stringBuilder.toString());
        } 
        break;
      } 
    } catch (IOException iOException) {
      Log.i("SyncManager", "No initial statistics");
    } 
  }
  
  private void readStatusLocked() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Reading ");
    stringBuilder.append(this.mStatusFile.getBaseFile());
    Log.v("SyncManager", stringBuilder.toString());
    try {
      byte[] arrayOfByte = this.mStatusFile.readFully();
      Parcel parcel = Parcel.obtain();
      parcel.unmarshall(arrayOfByte, 0, arrayOfByte.length);
      parcel.setDataPosition(0);
      while (true) {
        int i = parcel.readInt();
        if (i != 0) {
          if (i == 100) {
            SyncStatusInfo syncStatusInfo = new SyncStatusInfo();
            this(parcel);
            if (this.mAuthorities.indexOfKey(syncStatusInfo.authorityId) >= 0) {
              syncStatusInfo.pending = false;
              StringBuilder stringBuilder2 = new StringBuilder();
              this();
              stringBuilder2.append("Adding status for id ");
              stringBuilder2.append(syncStatusInfo.authorityId);
              Log.v("SyncManager", stringBuilder2.toString());
              this.mSyncStatus.put(syncStatusInfo.authorityId, syncStatusInfo);
            } 
            continue;
          } 
          StringBuilder stringBuilder1 = new StringBuilder();
          this();
          stringBuilder1.append("Unknown status token: ");
          stringBuilder1.append(i);
          Log.w("SyncManager", stringBuilder1.toString());
        } 
        break;
      } 
    } catch (IOException iOException) {
      Log.i("SyncManager", "No initial status");
    } 
  }
  
  private void removeAuthorityLocked(Account paramAccount, int paramInt, String paramString, boolean paramBoolean) {
    AccountInfo accountInfo = this.mAccounts.get(new AccountAndUser(paramAccount, paramInt));
    if (accountInfo != null) {
      AuthorityInfo authorityInfo = accountInfo.authorities.remove(paramString);
      if (authorityInfo != null) {
        this.mAuthorities.remove(authorityInfo.ident);
        if (paramBoolean)
          writeAccountInfoLocked(); 
      } 
    } 
  }
  
  private void reportChange(int paramInt) {
    synchronized (this.mAuthorities) {
      int i = this.mChangeListeners.beginBroadcast();
      ArrayList<ISyncStatusObserver> arrayList;
      for (arrayList = null; i > 0; arrayList = arrayList1) {
        if ((((Integer)this.mChangeListeners.getBroadcastCookie(--i)).intValue() & paramInt) == 0)
          continue; 
        ArrayList<ISyncStatusObserver> arrayList1 = arrayList;
        if (arrayList == null) {
          arrayList1 = new ArrayList();
          this(i);
        } 
        arrayList1.add((ISyncStatusObserver)this.mChangeListeners.getBroadcastItem(i));
      } 
      this.mChangeListeners.finishBroadcast();
      if (arrayList != null) {
        i = arrayList.size();
        while (i > 0) {
          i--;
          try {
            ((ISyncStatusObserver)arrayList.get(i)).onStatusChanged(paramInt);
          } catch (RemoteException remoteException) {}
        } 
      } 
      return;
    } 
  }
  
  private void requestSync(Account paramAccount, int paramInt1, int paramInt2, String paramString, Bundle paramBundle) {
    if (Process.myUid() == 1000) {
      OnSyncRequestListener onSyncRequestListener = this.mSyncRequestListener;
      if (onSyncRequestListener != null) {
        onSyncRequestListener.onSyncRequest(paramAccount, paramInt1, paramInt2, paramString, paramBundle);
        return;
      } 
    } 
    ContentResolver.requestSync(paramAccount, paramString, paramBundle);
  }
  
  private static Bundle unflattenBundle(byte[] paramArrayOfbyte) {
    Parcel parcel = Parcel.obtain();
    try {
      parcel.unmarshall(paramArrayOfbyte, 0, paramArrayOfbyte.length);
      parcel.setDataPosition(0);
      Bundle bundle = parcel.readBundle();
    } catch (RuntimeException runtimeException) {
      Bundle bundle = new Bundle();
    } finally {}
    parcel.recycle();
    return (Bundle)paramArrayOfbyte;
  }
  
  private void updateOrRemovePeriodicSync(PeriodicSync paramPeriodicSync, int paramInt, boolean paramBoolean) {
    synchronized (this.mAuthorities) {
      if (paramPeriodicSync.period <= 0L && paramBoolean) {
        StringBuilder stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("period < 0, should never happen in updateOrRemovePeriodicSync: add-");
        stringBuilder.append(paramBoolean);
        Log.e("SyncManager", stringBuilder.toString());
      } 
      if (paramPeriodicSync.extras == null) {
        StringBuilder stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("null extras, should never happen in updateOrRemovePeriodicSync: add-");
        stringBuilder.append(paramBoolean);
        Log.e("SyncManager", stringBuilder.toString());
      } 
      try {
        AuthorityInfo authorityInfo = getOrCreateAuthorityLocked(paramPeriodicSync.account, paramInt, paramPeriodicSync.authority, -1, false);
        byte b = 0;
        boolean bool = false;
        if (paramBoolean) {
          int i = authorityInfo.periodicSyncs.size();
          b = 0;
          while (true) {
            paramInt = bool;
            if (b < i) {
              PeriodicSync periodicSync = authorityInfo.periodicSyncs.get(b);
              if (PeriodicSync.syncExtrasEquals(paramPeriodicSync.extras, periodicSync.extras)) {
                if (paramPeriodicSync.period == periodicSync.period) {
                  long l1 = PeriodicSync.flexTime.get(paramPeriodicSync);
                  long l2 = PeriodicSync.flexTime.get(periodicSync);
                  if (l1 == l2)
                    return; 
                } 
                authorityInfo.periodicSyncs.set(b, PeriodicSync.clone(paramPeriodicSync));
                paramInt = 1;
                break;
              } 
              b++;
              continue;
            } 
            break;
          } 
          if (paramInt == 0) {
            authorityInfo.periodicSyncs.add(PeriodicSync.clone(paramPeriodicSync));
            getOrCreateSyncStatusLocked(authorityInfo.ident).setPeriodicSyncTime(authorityInfo.periodicSyncs.size() - 1, 0L);
          } 
        } else {
          SyncStatusInfo syncStatusInfo = (SyncStatusInfo)this.mSyncStatus.get(authorityInfo.ident);
          Iterator<PeriodicSync> iterator = authorityInfo.periodicSyncs.iterator();
          for (paramInt = 0; iterator.hasNext(); paramInt++) {
            if (PeriodicSync.syncExtrasEquals(((PeriodicSync)iterator.next()).extras, paramPeriodicSync.extras)) {
              iterator.remove();
              if (syncStatusInfo != null) {
                syncStatusInfo.removePeriodicSyncTime(paramInt);
              } else {
                Log.e("SyncManager", "Tried removing sync status on remove periodic sync butdid not find it.");
              } 
              b = 1;
              continue;
            } 
          } 
          if (b == 0)
            return; 
        } 
        writeAccountInfoLocked();
        return;
      } finally {
        writeAccountInfoLocked();
        writeStatusLocked();
      } 
    } 
  }
  
  private void writeAccountInfoLocked() {
    FileOutputStream fileOutputStream;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Writing new ");
    stringBuilder.append(this.mAccountInfoFile.getBaseFile());
    String str2 = stringBuilder.toString();
    String str1 = "SyncManager";
    Log.v("SyncManager", str2);
    str2 = null;
    try {
      FileOutputStream fileOutputStream1 = this.mAccountInfoFile.startWrite();
      str2 = str1;
      try {
        FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
        str2 = str1;
        this();
        str2 = str1;
        fastXmlSerializer.setOutput(fileOutputStream1, "utf-8");
        str2 = str1;
        fastXmlSerializer.startDocument(null, Boolean.valueOf(true));
        str2 = str1;
        fastXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        str2 = str1;
        fastXmlSerializer.startTag(null, "accounts");
        str2 = str1;
        fastXmlSerializer.attribute(null, "version", Integer.toString(2));
        str2 = str1;
        fastXmlSerializer.attribute(null, "nextAuthorityId", Integer.toString(this.mNextAuthorityId));
        str2 = str1;
        fastXmlSerializer.attribute(null, "offsetInSeconds", Integer.toString(this.mSyncRandomOffset));
        str2 = str1;
        int i = this.mMasterSyncAutomatically.size();
        byte b;
        for (b = 0; b < i; b++) {
          str2 = str1;
          int j = this.mMasterSyncAutomatically.keyAt(b);
          str2 = str1;
          Boolean bool = (Boolean)this.mMasterSyncAutomatically.valueAt(b);
          str2 = str1;
          fastXmlSerializer.startTag(null, "listenForTickles");
          str2 = str1;
          fastXmlSerializer.attribute(null, "user", Integer.toString(j));
          str2 = str1;
          fastXmlSerializer.attribute(null, "enabled", Boolean.toString(bool.booleanValue()));
          str2 = str1;
          fastXmlSerializer.endTag(null, "listenForTickles");
        } 
        str2 = str1;
        i = this.mAuthorities.size();
        b = 0;
        label89: while (true) {
          if (b < i) {
            str2 = str1;
            AuthorityInfo authorityInfo = (AuthorityInfo)this.mAuthorities.valueAt(b);
            str2 = str1;
            fastXmlSerializer.startTag(null, "authority");
            str2 = str1;
            fastXmlSerializer.attribute(null, "id", Integer.toString(authorityInfo.ident));
            str2 = str1;
            fastXmlSerializer.attribute(null, "user", Integer.toString(authorityInfo.userId));
            str2 = str1;
            fastXmlSerializer.attribute(null, "enabled", Boolean.toString(authorityInfo.enabled));
            str2 = str1;
            if (authorityInfo.service == null) {
              str2 = str1;
              fastXmlSerializer.attribute(null, "account", authorityInfo.account.name);
              str2 = str1;
              fastXmlSerializer.attribute(null, "type", authorityInfo.account.type);
              str2 = str1;
              fastXmlSerializer.attribute(null, "authority", authorityInfo.authority);
            } else {
              str2 = str1;
              fastXmlSerializer.attribute(null, "package", authorityInfo.service.getPackageName());
              str2 = str1;
              fastXmlSerializer.attribute(null, "class", authorityInfo.service.getClassName());
            } 
            str2 = str1;
            int j = authorityInfo.syncable;
            if (j < 0) {
              str2 = str1;
              fastXmlSerializer.attribute(null, "syncable", "unknown");
            } else {
              boolean bool;
              str2 = str1;
              if (authorityInfo.syncable != 0) {
                bool = true;
              } else {
                bool = false;
              } 
              str2 = str1;
              fastXmlSerializer.attribute(null, "syncable", Boolean.toString(bool));
            } 
            str2 = str1;
            Iterator<PeriodicSync> iterator = authorityInfo.periodicSyncs.iterator();
            while (true) {
              str2 = str1;
              if (iterator.hasNext()) {
                str2 = str1;
                PeriodicSync periodicSync = iterator.next();
                str2 = str1;
                fastXmlSerializer.startTag(null, "periodicSync");
                str2 = str1;
                str1 = str2;
                try {
                  fastXmlSerializer.attribute(null, "period", Long.toString(periodicSync.period));
                  str1 = str2;
                  fastXmlSerializer.attribute(null, "flex", Long.toString(PeriodicSync.flexTime.get(periodicSync)));
                  str1 = str2;
                  extrasToXml((XmlSerializer)fastXmlSerializer, periodicSync.extras);
                  str1 = str2;
                  fastXmlSerializer.endTag(null, "periodicSync");
                  str1 = str2;
                  continue;
                } catch (IOException iOException1) {
                  break;
                } 
              } 
              str2 = str1;
              str1 = str2;
              fastXmlSerializer.endTag(null, "authority");
              b++;
              str1 = str2;
              continue label89;
            } 
            // Byte code: goto -> 812
          } 
          str2 = str1;
          str1 = str2;
          fastXmlSerializer.endTag(null, "accounts");
          str1 = str2;
          fastXmlSerializer.endDocument();
          str1 = str2;
          this.mAccountInfoFile.finishWrite(fileOutputStream1);
          break;
        } 
      } catch (IOException null) {
        str1 = str2;
        IOException iOException1 = iOException;
        iOException = iOException1;
        fileOutputStream = fileOutputStream1;
      } 
    } catch (IOException iOException) {
      str1 = "SyncManager";
    } 
    Log.w(str1, "Error writing accounts", iOException);
    if (fileOutputStream != null)
      this.mAccountInfoFile.failWrite(fileOutputStream); 
  }
  
  private void writePendingOperationLocked(PendingOperation paramPendingOperation, XmlSerializer paramXmlSerializer) throws IOException {
    paramXmlSerializer.startTag(null, "op");
    paramXmlSerializer.attribute(null, "version", Integer.toString(3));
    paramXmlSerializer.attribute(null, "authority_id", Integer.toString(paramPendingOperation.authorityId));
    paramXmlSerializer.attribute(null, "source", Integer.toString(paramPendingOperation.syncSource));
    paramXmlSerializer.attribute(null, "expedited", Boolean.toString(paramPendingOperation.expedited));
    paramXmlSerializer.attribute(null, "reason", Integer.toString(paramPendingOperation.reason));
    extrasToXml(paramXmlSerializer, paramPendingOperation.extras);
    paramXmlSerializer.endTag(null, "op");
  }
  
  private void writePendingOperationsLocked() {
    int i = this.mPendingOperations.size();
    FileOutputStream fileOutputStream = null;
    if (i == 0) {
      FileOutputStream fileOutputStream1 = fileOutputStream;
      try {
        StringBuilder stringBuilder = new StringBuilder();
        fileOutputStream1 = fileOutputStream;
        this();
        fileOutputStream1 = fileOutputStream;
        stringBuilder.append("Truncating ");
        fileOutputStream1 = fileOutputStream;
        stringBuilder.append(this.mPendingFile.getBaseFile());
        fileOutputStream1 = fileOutputStream;
        Log.v("SyncManagerFile", stringBuilder.toString());
        fileOutputStream1 = fileOutputStream;
        this.mPendingFile.truncate();
        return;
      } catch (IOException iOException) {
        Log.w("SyncManager", "Error writing pending operations", iOException);
        if (fileOutputStream1 != null)
          this.mPendingFile.failWrite(fileOutputStream1); 
      } 
    } else {
      IOException iOException1 = iOException;
      StringBuilder stringBuilder = new StringBuilder();
      iOException1 = iOException;
      this();
      iOException1 = iOException;
      stringBuilder.append("Writing new ");
      iOException1 = iOException;
      stringBuilder.append(this.mPendingFile.getBaseFile());
      iOException1 = iOException;
      Log.v("SyncManagerFile", stringBuilder.toString());
      iOException1 = iOException;
      FileOutputStream fileOutputStream1 = this.mPendingFile.startWrite();
      FileOutputStream fileOutputStream2 = fileOutputStream1;
      FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
      fileOutputStream2 = fileOutputStream1;
      this();
      fileOutputStream2 = fileOutputStream1;
      fastXmlSerializer.setOutput(fileOutputStream1, "utf-8");
      for (byte b = 0; b < i; b++) {
        fileOutputStream2 = fileOutputStream1;
        writePendingOperationLocked(this.mPendingOperations.get(b), (XmlSerializer)fastXmlSerializer);
      } 
      fileOutputStream2 = fileOutputStream1;
      fastXmlSerializer.endDocument();
      fileOutputStream2 = fileOutputStream1;
      this.mPendingFile.finishWrite(fileOutputStream1);
    } 
  }
  
  private void writeStatisticsLocked() {
    FileOutputStream fileOutputStream;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Writing new ");
    stringBuilder.append(this.mStatisticsFile.getBaseFile());
    Log.v("SyncManager", stringBuilder.toString());
    removeMessages(2);
    stringBuilder = null;
    try {
      FileOutputStream fileOutputStream1 = this.mStatisticsFile.startWrite();
      fileOutputStream = fileOutputStream1;
      Parcel parcel = Parcel.obtain();
      fileOutputStream = fileOutputStream1;
      int i = this.mDayStats.length;
      for (byte b = 0; b < i; b++) {
        fileOutputStream = fileOutputStream1;
        DayStats dayStats = this.mDayStats[b];
        if (dayStats == null)
          break; 
        fileOutputStream = fileOutputStream1;
        parcel.writeInt(101);
        fileOutputStream = fileOutputStream1;
        parcel.writeInt(dayStats.day);
        fileOutputStream = fileOutputStream1;
        parcel.writeInt(dayStats.successCount);
        fileOutputStream = fileOutputStream1;
        parcel.writeLong(dayStats.successTime);
        fileOutputStream = fileOutputStream1;
        parcel.writeInt(dayStats.failureCount);
        fileOutputStream = fileOutputStream1;
        parcel.writeLong(dayStats.failureTime);
      } 
      fileOutputStream = fileOutputStream1;
      parcel.writeInt(0);
      fileOutputStream = fileOutputStream1;
      fileOutputStream1.write(parcel.marshall());
      fileOutputStream = fileOutputStream1;
      parcel.recycle();
      fileOutputStream = fileOutputStream1;
      this.mStatisticsFile.finishWrite(fileOutputStream1);
    } catch (IOException iOException) {
      Log.w("SyncManager", "Error writing stats", iOException);
      if (fileOutputStream != null)
        this.mStatisticsFile.failWrite(fileOutputStream); 
    } 
  }
  
  private void writeStatusLocked() {
    FileOutputStream fileOutputStream;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Writing new ");
    stringBuilder.append(this.mStatusFile.getBaseFile());
    Log.v("SyncManager", stringBuilder.toString());
    removeMessages(1);
    stringBuilder = null;
    try {
      FileOutputStream fileOutputStream1 = this.mStatusFile.startWrite();
      fileOutputStream = fileOutputStream1;
      Parcel parcel = Parcel.obtain();
      fileOutputStream = fileOutputStream1;
      int i = this.mSyncStatus.size();
      for (byte b = 0; b < i; b++) {
        fileOutputStream = fileOutputStream1;
        SyncStatusInfo syncStatusInfo = (SyncStatusInfo)this.mSyncStatus.valueAt(b);
        fileOutputStream = fileOutputStream1;
        parcel.writeInt(100);
        fileOutputStream = fileOutputStream1;
        syncStatusInfo.writeToParcel(parcel, 0);
      } 
      fileOutputStream = fileOutputStream1;
      parcel.writeInt(0);
      fileOutputStream = fileOutputStream1;
      fileOutputStream1.write(parcel.marshall());
      fileOutputStream = fileOutputStream1;
      parcel.recycle();
      fileOutputStream = fileOutputStream1;
      this.mStatusFile.finishWrite(fileOutputStream1);
    } catch (IOException iOException) {
      Log.w("SyncManager", "Error writing status", iOException);
      if (fileOutputStream != null)
        this.mStatusFile.failWrite(fileOutputStream); 
    } 
  }
  
  public VSyncInfo addActiveSync(SyncManager.ActiveSyncContext paramActiveSyncContext) {
    synchronized (this.mAuthorities) {
      AuthorityInfo authorityInfo = getOrCreateAuthorityLocked(paramActiveSyncContext.mSyncOperation.account, paramActiveSyncContext.mSyncOperation.userId, paramActiveSyncContext.mSyncOperation.authority, -1, true);
      VSyncInfo vSyncInfo = new VSyncInfo();
      this(authorityInfo.ident, authorityInfo.account, authorityInfo.authority, paramActiveSyncContext.mStartTime);
      getCurrentSyncs(authorityInfo.userId).add(vSyncInfo);
      reportActiveChange();
      return vSyncInfo;
    } 
  }
  
  public void addPeriodicSync(PeriodicSync paramPeriodicSync, int paramInt) {
    updateOrRemovePeriodicSync(paramPeriodicSync, paramInt, true);
  }
  
  public void addStatusChangeListener(int paramInt, ISyncStatusObserver paramISyncStatusObserver) {
    synchronized (this.mAuthorities) {
      this.mChangeListeners.register((IInterface)paramISyncStatusObserver, Integer.valueOf(paramInt));
      return;
    } 
  }
  
  public void clearAllBackoffsLocked(SyncQueue paramSyncQueue) {
    synchronized (this.mAuthorities) {
      Iterator<AccountInfo> iterator = this.mAccounts.values().iterator();
      boolean bool = false;
      label21: while (iterator.hasNext()) {
        AccountInfo accountInfo = iterator.next();
        Iterator<AuthorityInfo> iterator1 = accountInfo.authorities.values().iterator();
        boolean bool1 = bool;
        while (true) {
          bool = bool1;
          if (iterator1.hasNext()) {
            AuthorityInfo authorityInfo = iterator1.next();
            if (authorityInfo.backoffTime != -1L || authorityInfo.backoffDelay != -1L) {
              authorityInfo.backoffTime = -1L;
              authorityInfo.backoffDelay = -1L;
              paramSyncQueue.onBackoffChanged(accountInfo.accountAndUser.account, accountInfo.accountAndUser.userId, authorityInfo.authority, 0L);
              bool1 = true;
            } 
            continue;
          } 
          continue label21;
        } 
      } 
      if (bool)
        reportChange(1); 
      return;
    } 
  }
  
  public void clearAndReadState() {
    synchronized (this.mAuthorities) {
      this.mAuthorities.clear();
      this.mAccounts.clear();
      this.mServices.clear();
      this.mPendingOperations.clear();
      this.mSyncStatus.clear();
      this.mSyncHistory.clear();
      readAccountInfoLocked();
      readStatusLocked();
      readPendingOperationsLocked();
      readStatisticsLocked();
      readAndDeleteLegacyAccountInfoLocked();
      writeAccountInfoLocked();
      writeStatusLocked();
      writePendingOperationsLocked();
      writeStatisticsLocked();
      return;
    } 
  }
  
  public boolean deleteFromPending(PendingOperation paramPendingOperation) {
    synchronized (this.mAuthorities) {
      boolean bool = this.mPendingOperations.remove(paramPendingOperation);
      boolean bool1 = true;
      if (bool) {
        if (this.mPendingOperations.size() == 0 || this.mNumPendingFinished >= 4) {
          writePendingOperationsLocked();
          this.mNumPendingFinished = 0;
        } else {
          this.mNumPendingFinished++;
        } 
        AuthorityInfo authorityInfo = getAuthorityLocked(paramPendingOperation.account, paramPendingOperation.userId, paramPendingOperation.authority, "deleteFromPending");
        bool = bool1;
        if (authorityInfo != null) {
          int i = this.mPendingOperations.size();
          byte b = 0;
          while (true) {
            if (b < i) {
              PendingOperation pendingOperation = this.mPendingOperations.get(b);
              if (pendingOperation.account.equals(paramPendingOperation.account) && pendingOperation.authority.equals(paramPendingOperation.authority) && pendingOperation.userId == paramPendingOperation.userId) {
                b = 1;
                break;
              } 
              b++;
              continue;
            } 
            b = 0;
            break;
          } 
          bool = bool1;
          if (b == 0) {
            (getOrCreateSyncStatusLocked(authorityInfo.ident)).pending = false;
            bool = bool1;
          } 
        } 
      } else {
        bool = false;
      } 
      reportChange(2);
      return bool;
    } 
  }
  
  public void doDatabaseCleanup(Account[] paramArrayOfAccount, int paramInt) {
    synchronized (this.mAuthorities) {
      SparseArray sparseArray = new SparseArray();
      this();
      Iterator<AccountInfo> iterator = this.mAccounts.values().iterator();
      while (iterator.hasNext()) {
        AccountInfo accountInfo = iterator.next();
        if (!ArrayUtils.contains((Object[])paramArrayOfAccount, accountInfo.accountAndUser.account) && accountInfo.accountAndUser.userId == paramInt) {
          for (AuthorityInfo authorityInfo : accountInfo.authorities.values())
            sparseArray.put(authorityInfo.ident, authorityInfo); 
          iterator.remove();
        } 
      } 
      int i = sparseArray.size();
      if (i > 0) {
        label41: while (i > 0) {
          int j = i - 1;
          int k = sparseArray.keyAt(j);
          this.mAuthorities.remove(k);
          paramInt = this.mSyncStatus.size();
          while (paramInt > 0) {
            i = paramInt - 1;
            paramInt = i;
            if (this.mSyncStatus.keyAt(i) == k) {
              this.mSyncStatus.remove(this.mSyncStatus.keyAt(i));
              paramInt = i;
            } 
          } 
          paramInt = this.mSyncHistory.size();
          while (true) {
            i = j;
            if (paramInt > 0) {
              i = paramInt - 1;
              paramInt = i;
              if (((SyncHistoryItem)this.mSyncHistory.get(i)).authorityId == k) {
                this.mSyncHistory.remove(i);
                paramInt = i;
              } 
              continue;
            } 
            continue label41;
          } 
        } 
        writeAccountInfoLocked();
        writeStatusLocked();
        writePendingOperationsLocked();
        writeStatisticsLocked();
      } 
      return;
    } 
  }
  
  public void dumpPendingOperations(StringBuilder paramStringBuilder) {
    paramStringBuilder.append("Pending Ops: ");
    paramStringBuilder.append(this.mPendingOperations.size());
    paramStringBuilder.append(" operation(s)\n");
    for (PendingOperation pendingOperation : this.mPendingOperations) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("(");
      stringBuilder.append(pendingOperation.account);
      paramStringBuilder.append(stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append(", u");
      stringBuilder.append(pendingOperation.userId);
      paramStringBuilder.append(stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append(", ");
      stringBuilder.append(pendingOperation.authority);
      paramStringBuilder.append(stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append(", ");
      stringBuilder.append(pendingOperation.extras);
      paramStringBuilder.append(stringBuilder.toString());
      paramStringBuilder.append(")\n");
    } 
  }
  
  public AuthorityInfo getAuthority(int paramInt) {
    synchronized (this.mAuthorities) {
      return (AuthorityInfo)this.mAuthorities.get(paramInt);
    } 
  }
  
  public Pair<Long, Long> getBackoff(Account paramAccount, int paramInt, String paramString) {
    synchronized (this.mAuthorities) {
      AuthorityInfo authorityInfo = getAuthorityLocked(paramAccount, paramInt, paramString, "getBackoff");
      if (authorityInfo == null || authorityInfo.backoffTime < 0L)
        return null; 
      return Pair.create(Long.valueOf(authorityInfo.backoffTime), Long.valueOf(authorityInfo.backoffDelay));
    } 
  }
  
  public ArrayList<Pair<AuthorityInfo, SyncStatusInfo>> getCopyOfAllAuthoritiesWithSyncStatus() {
    synchronized (this.mAuthorities) {
      ArrayList<Pair<AuthorityInfo, SyncStatusInfo>> arrayList = new ArrayList();
      this(this.mAuthorities.size());
      for (byte b = 0; b < this.mAuthorities.size(); b++)
        arrayList.add(createCopyPairOfAuthorityWithSyncStatusLocked((AuthorityInfo)this.mAuthorities.valueAt(b))); 
      return arrayList;
    } 
  }
  
  public Pair<AuthorityInfo, SyncStatusInfo> getCopyOfAuthorityWithSyncStatus(Account paramAccount, int paramInt, String paramString) {
    synchronized (this.mAuthorities) {
      return createCopyPairOfAuthorityWithSyncStatusLocked(getOrCreateAuthorityLocked(paramAccount, paramInt, paramString, -1, true));
    } 
  }
  
  public List<VSyncInfo> getCurrentSyncsCopy(int paramInt) {
    synchronized (this.mAuthorities) {
      List<VSyncInfo> list = getCurrentSyncsLocked(paramInt);
      ArrayList<VSyncInfo> arrayList = new ArrayList();
      this();
      for (VSyncInfo vSyncInfo2 : list) {
        VSyncInfo vSyncInfo1 = new VSyncInfo();
        this(vSyncInfo2);
        arrayList.add(vSyncInfo1);
      } 
      return arrayList;
    } 
  }
  
  public DayStats[] getDayStatistics() {
    synchronized (this.mAuthorities) {
      int i = this.mDayStats.length;
      DayStats[] arrayOfDayStats = new DayStats[i];
      System.arraycopy(this.mDayStats, 0, arrayOfDayStats, 0, i);
      return arrayOfDayStats;
    } 
  }
  
  public long getDelayUntilTime(Account paramAccount, int paramInt, String paramString) {
    synchronized (this.mAuthorities) {
      AuthorityInfo authorityInfo = getAuthorityLocked(paramAccount, paramInt, paramString, "getDelayUntil");
      if (authorityInfo == null)
        return 0L; 
      return authorityInfo.delayUntil;
    } 
  }
  
  public int getIsSyncable(Account paramAccount, int paramInt, String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mAuthorities : Landroid/util/SparseArray;
    //   4: astore #4
    //   6: aload #4
    //   8: monitorenter
    //   9: aload_1
    //   10: ifnull -> 43
    //   13: aload_0
    //   14: aload_1
    //   15: iload_2
    //   16: aload_3
    //   17: ldc_w 'getIsSyncable'
    //   20: invokespecial getAuthorityLocked : (Landroid/accounts/Account;ILjava/lang/String;Ljava/lang/String;)Lcom/lody/virtual/server/content/SyncStorageEngine$AuthorityInfo;
    //   23: astore_1
    //   24: aload_1
    //   25: ifnonnull -> 33
    //   28: aload #4
    //   30: monitorexit
    //   31: iconst_m1
    //   32: ireturn
    //   33: aload_1
    //   34: getfield syncable : I
    //   37: istore_2
    //   38: aload #4
    //   40: monitorexit
    //   41: iload_2
    //   42: ireturn
    //   43: aload_0
    //   44: getfield mAuthorities : Landroid/util/SparseArray;
    //   47: invokevirtual size : ()I
    //   50: istore_2
    //   51: iload_2
    //   52: ifle -> 91
    //   55: iinc #2, -1
    //   58: aload_0
    //   59: getfield mAuthorities : Landroid/util/SparseArray;
    //   62: iload_2
    //   63: invokevirtual valueAt : (I)Ljava/lang/Object;
    //   66: checkcast com/lody/virtual/server/content/SyncStorageEngine$AuthorityInfo
    //   69: astore_1
    //   70: aload_1
    //   71: getfield authority : Ljava/lang/String;
    //   74: aload_3
    //   75: invokevirtual equals : (Ljava/lang/Object;)Z
    //   78: ifeq -> 51
    //   81: aload_1
    //   82: getfield syncable : I
    //   85: istore_2
    //   86: aload #4
    //   88: monitorexit
    //   89: iload_2
    //   90: ireturn
    //   91: aload #4
    //   93: monitorexit
    //   94: iconst_m1
    //   95: ireturn
    //   96: astore_1
    //   97: aload #4
    //   99: monitorexit
    //   100: aload_1
    //   101: athrow
    // Exception table:
    //   from	to	target	type
    //   13	24	96	finally
    //   28	31	96	finally
    //   33	41	96	finally
    //   43	51	96	finally
    //   58	70	96	finally
    //   70	89	96	finally
    //   91	94	96	finally
    //   97	100	96	finally
  }
  
  public boolean getMasterSyncAutomatically(int paramInt) {
    synchronized (this.mAuthorities) {
      boolean bool1;
      Boolean bool = (Boolean)this.mMasterSyncAutomatically.get(paramInt);
      if (bool == null) {
        bool1 = this.mDefaultMasterSyncAutomatically;
      } else {
        bool1 = bool.booleanValue();
      } 
      return bool1;
    } 
  }
  
  public int getPendingOperationCount() {
    synchronized (this.mAuthorities) {
      return this.mPendingOperations.size();
    } 
  }
  
  public ArrayList<PendingOperation> getPendingOperations() {
    synchronized (this.mAuthorities) {
      ArrayList<PendingOperation> arrayList = new ArrayList();
      this((Collection)this.mPendingOperations);
      return arrayList;
    } 
  }
  
  public List<PeriodicSync> getPeriodicSyncs(Account paramAccount, int paramInt, String paramString) {
    ArrayList<PeriodicSync> arrayList = new ArrayList();
    synchronized (this.mAuthorities) {
      AuthorityInfo authorityInfo = getAuthorityLocked(paramAccount, paramInt, paramString, "getPeriodicSyncs");
      if (authorityInfo != null) {
        Iterator<PeriodicSync> iterator = authorityInfo.periodicSyncs.iterator();
        while (iterator.hasNext())
          arrayList.add(PeriodicSync.clone(iterator.next())); 
      } 
      return arrayList;
    } 
  }
  
  public SyncStatusInfo getStatusByAccountAndAuthority(Account paramAccount, int paramInt, String paramString) {
    if (paramAccount == null || paramString == null)
      return null; 
    synchronized (this.mAuthorities) {
      int i = this.mSyncStatus.size();
      for (byte b = 0; b < i; b++) {
        SyncStatusInfo syncStatusInfo = (SyncStatusInfo)this.mSyncStatus.valueAt(b);
        AuthorityInfo authorityInfo = (AuthorityInfo)this.mAuthorities.get(syncStatusInfo.authorityId);
        if (authorityInfo != null && authorityInfo.authority.equals(paramString) && authorityInfo.userId == paramInt && paramAccount.equals(authorityInfo.account))
          return syncStatusInfo; 
      } 
      return null;
    } 
  }
  
  public boolean getSyncAutomatically(Account paramAccount, int paramInt, String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mAuthorities : Landroid/util/SparseArray;
    //   4: astore #4
    //   6: aload #4
    //   8: monitorenter
    //   9: iconst_1
    //   10: istore #5
    //   12: aload_1
    //   13: ifnull -> 50
    //   16: aload_0
    //   17: aload_1
    //   18: iload_2
    //   19: aload_3
    //   20: ldc_w 'getSyncAutomatically'
    //   23: invokespecial getAuthorityLocked : (Landroid/accounts/Account;ILjava/lang/String;Ljava/lang/String;)Lcom/lody/virtual/server/content/SyncStorageEngine$AuthorityInfo;
    //   26: astore_1
    //   27: aload_1
    //   28: ifnull -> 41
    //   31: aload_1
    //   32: getfield enabled : Z
    //   35: ifeq -> 41
    //   38: goto -> 44
    //   41: iconst_0
    //   42: istore #5
    //   44: aload #4
    //   46: monitorexit
    //   47: iload #5
    //   49: ireturn
    //   50: aload_0
    //   51: getfield mAuthorities : Landroid/util/SparseArray;
    //   54: invokevirtual size : ()I
    //   57: istore #6
    //   59: iload #6
    //   61: ifle -> 126
    //   64: iload #6
    //   66: iconst_1
    //   67: isub
    //   68: istore #7
    //   70: aload_0
    //   71: getfield mAuthorities : Landroid/util/SparseArray;
    //   74: iload #7
    //   76: invokevirtual valueAt : (I)Ljava/lang/Object;
    //   79: checkcast com/lody/virtual/server/content/SyncStorageEngine$AuthorityInfo
    //   82: astore_1
    //   83: iload #7
    //   85: istore #6
    //   87: aload_1
    //   88: getfield authority : Ljava/lang/String;
    //   91: aload_3
    //   92: invokevirtual equals : (Ljava/lang/Object;)Z
    //   95: ifeq -> 59
    //   98: iload #7
    //   100: istore #6
    //   102: aload_1
    //   103: getfield userId : I
    //   106: iload_2
    //   107: if_icmpne -> 59
    //   110: iload #7
    //   112: istore #6
    //   114: aload_1
    //   115: getfield enabled : Z
    //   118: ifeq -> 59
    //   121: aload #4
    //   123: monitorexit
    //   124: iconst_1
    //   125: ireturn
    //   126: aload #4
    //   128: monitorexit
    //   129: iconst_0
    //   130: ireturn
    //   131: astore_1
    //   132: aload #4
    //   134: monitorexit
    //   135: aload_1
    //   136: athrow
    // Exception table:
    //   from	to	target	type
    //   16	27	131	finally
    //   31	38	131	finally
    //   44	47	131	finally
    //   50	59	131	finally
    //   70	83	131	finally
    //   87	98	131	finally
    //   102	110	131	finally
    //   114	124	131	finally
    //   126	129	131	finally
    //   132	135	131	finally
  }
  
  public ArrayList<SyncHistoryItem> getSyncHistory() {
    synchronized (this.mAuthorities) {
      int i = this.mSyncHistory.size();
      ArrayList<SyncHistoryItem> arrayList = new ArrayList();
      this(i);
      for (byte b = 0; b < i; b++)
        arrayList.add(this.mSyncHistory.get(b)); 
      return arrayList;
    } 
  }
  
  public int getSyncRandomOffset() {
    return this.mSyncRandomOffset;
  }
  
  public ArrayList<SyncStatusInfo> getSyncStatus() {
    synchronized (this.mAuthorities) {
      int i = this.mSyncStatus.size();
      ArrayList<SyncStatusInfo> arrayList = new ArrayList();
      this(i);
      for (byte b = 0; b < i; b++)
        arrayList.add((SyncStatusInfo)this.mSyncStatus.valueAt(b)); 
      return arrayList;
    } 
  }
  
  public void handleMessage(Message paramMessage) {
    if (paramMessage.what == 1) {
      synchronized (this.mAuthorities) {
        writeStatusLocked();
      } 
    } else if (paramMessage.what == 2) {
      synchronized (this.mAuthorities) {
        writeStatisticsLocked();
      } 
    } 
  }
  
  public PendingOperation insertIntoPending(PendingOperation paramPendingOperation) {
    synchronized (this.mAuthorities) {
      AuthorityInfo authorityInfo = getOrCreateAuthorityLocked(paramPendingOperation.account, paramPendingOperation.userId, paramPendingOperation.authority, -1, true);
      if (authorityInfo == null)
        return null; 
      PendingOperation pendingOperation = new PendingOperation();
      this(paramPendingOperation);
      pendingOperation.authorityId = authorityInfo.ident;
      this.mPendingOperations.add(pendingOperation);
      appendPendingOperationLocked(pendingOperation);
      (getOrCreateSyncStatusLocked(authorityInfo.ident)).pending = true;
      reportChange(2);
      return pendingOperation;
    } 
  }
  
  public long insertStartSyncEvent(Account paramAccount, int paramInt1, int paramInt2, String paramString, long paramLong, int paramInt3, boolean paramBoolean, Bundle paramBundle) {
    synchronized (this.mAuthorities) {
      AuthorityInfo authorityInfo = getAuthorityLocked(paramAccount, paramInt1, paramString, "insertStartSyncEvent");
      if (authorityInfo == null)
        return -1L; 
      SyncHistoryItem syncHistoryItem = new SyncHistoryItem();
      this();
      syncHistoryItem.initialization = paramBoolean;
      syncHistoryItem.authorityId = authorityInfo.ident;
      paramInt1 = this.mNextHistoryId;
      this.mNextHistoryId = paramInt1 + 1;
      syncHistoryItem.historyId = paramInt1;
      if (this.mNextHistoryId < 0)
        this.mNextHistoryId = 0; 
      syncHistoryItem.eventTime = paramLong;
      syncHistoryItem.source = paramInt3;
      syncHistoryItem.reason = paramInt2;
      syncHistoryItem.extras = paramBundle;
      syncHistoryItem.event = 0;
      this.mSyncHistory.add(0, syncHistoryItem);
      while (this.mSyncHistory.size() > 100)
        this.mSyncHistory.remove(this.mSyncHistory.size() - 1); 
      paramLong = syncHistoryItem.historyId;
      reportChange(8);
      return paramLong;
    } 
  }
  
  public boolean isSyncActive(Account paramAccount, int paramInt, String paramString) {
    synchronized (this.mAuthorities) {
      Iterator<VSyncInfo> iterator = getCurrentSyncs(paramInt).iterator();
      while (iterator.hasNext()) {
        AuthorityInfo authorityInfo = getAuthority(((VSyncInfo)iterator.next()).authorityId);
        if (authorityInfo != null && authorityInfo.account.equals(paramAccount) && authorityInfo.authority.equals(paramString) && authorityInfo.userId == paramInt)
          return true; 
      } 
      return false;
    } 
  }
  
  public boolean isSyncPending(Account paramAccount, int paramInt, String paramString) {
    synchronized (this.mAuthorities) {
      int i = this.mSyncStatus.size();
      for (byte b = 0; b < i; b++) {
        SyncStatusInfo syncStatusInfo = (SyncStatusInfo)this.mSyncStatus.valueAt(b);
        AuthorityInfo authorityInfo = (AuthorityInfo)this.mAuthorities.get(syncStatusInfo.authorityId);
        if (authorityInfo != null && paramInt == authorityInfo.userId && (paramAccount == null || authorityInfo.account.equals(paramAccount)) && authorityInfo.authority.equals(paramString) && syncStatusInfo.pending)
          return true; 
      } 
      return false;
    } 
  }
  
  public void removeActiveSync(VSyncInfo paramVSyncInfo, int paramInt) {
    synchronized (this.mAuthorities) {
      getCurrentSyncs(paramInt).remove(paramVSyncInfo);
      reportActiveChange();
      return;
    } 
  }
  
  public void removeAuthority(Account paramAccount, int paramInt, String paramString) {
    synchronized (this.mAuthorities) {
      removeAuthorityLocked(paramAccount, paramInt, paramString, true);
      return;
    } 
  }
  
  public void removePeriodicSync(PeriodicSync paramPeriodicSync, int paramInt) {
    updateOrRemovePeriodicSync(paramPeriodicSync, paramInt, false);
  }
  
  public void removeStatusChangeListener(ISyncStatusObserver paramISyncStatusObserver) {
    synchronized (this.mAuthorities) {
      this.mChangeListeners.unregister((IInterface)paramISyncStatusObserver);
      return;
    } 
  }
  
  public void reportActiveChange() {
    reportChange(4);
  }
  
  public void setBackoff(Account paramAccount, int paramInt, String paramString, long paramLong1, long paramLong2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mAuthorities : Landroid/util/SparseArray;
    //   4: astore #8
    //   6: aload #8
    //   8: monitorenter
    //   9: aload_1
    //   10: ifnull -> 72
    //   13: aload_3
    //   14: ifnonnull -> 20
    //   17: goto -> 72
    //   20: aload_0
    //   21: aload_1
    //   22: iload_2
    //   23: aload_3
    //   24: iconst_m1
    //   25: iconst_1
    //   26: invokespecial getOrCreateAuthorityLocked : (Landroid/accounts/Account;ILjava/lang/String;IZ)Lcom/lody/virtual/server/content/SyncStorageEngine$AuthorityInfo;
    //   29: astore_1
    //   30: aload_1
    //   31: getfield backoffTime : J
    //   34: lload #4
    //   36: lcmp
    //   37: ifne -> 54
    //   40: aload_1
    //   41: getfield backoffDelay : J
    //   44: lload #6
    //   46: lcmp
    //   47: ifne -> 54
    //   50: aload #8
    //   52: monitorexit
    //   53: return
    //   54: aload_1
    //   55: lload #4
    //   57: putfield backoffTime : J
    //   60: aload_1
    //   61: lload #6
    //   63: putfield backoffDelay : J
    //   66: iconst_1
    //   67: istore #9
    //   69: goto -> 255
    //   72: aload_0
    //   73: getfield mAccounts : Ljava/util/HashMap;
    //   76: invokevirtual values : ()Ljava/util/Collection;
    //   79: invokeinterface iterator : ()Ljava/util/Iterator;
    //   84: astore #10
    //   86: iconst_0
    //   87: istore #11
    //   89: iload #11
    //   91: istore #9
    //   93: aload #10
    //   95: invokeinterface hasNext : ()Z
    //   100: ifeq -> 255
    //   103: aload #10
    //   105: invokeinterface next : ()Ljava/lang/Object;
    //   110: checkcast com/lody/virtual/server/content/SyncStorageEngine$AccountInfo
    //   113: astore #12
    //   115: aload_1
    //   116: ifnull -> 149
    //   119: aload_1
    //   120: aload #12
    //   122: getfield accountAndUser : Lcom/lody/virtual/server/accounts/AccountAndUser;
    //   125: getfield account : Landroid/accounts/Account;
    //   128: invokevirtual equals : (Ljava/lang/Object;)Z
    //   131: ifne -> 149
    //   134: iload_2
    //   135: aload #12
    //   137: getfield accountAndUser : Lcom/lody/virtual/server/accounts/AccountAndUser;
    //   140: getfield userId : I
    //   143: if_icmpeq -> 149
    //   146: goto -> 89
    //   149: aload #12
    //   151: getfield authorities : Ljava/util/HashMap;
    //   154: invokevirtual values : ()Ljava/util/Collection;
    //   157: invokeinterface iterator : ()Ljava/util/Iterator;
    //   162: astore #13
    //   164: iload #11
    //   166: istore #9
    //   168: iload #9
    //   170: istore #11
    //   172: aload #13
    //   174: invokeinterface hasNext : ()Z
    //   179: ifeq -> 89
    //   182: aload #13
    //   184: invokeinterface next : ()Ljava/lang/Object;
    //   189: checkcast com/lody/virtual/server/content/SyncStorageEngine$AuthorityInfo
    //   192: astore #12
    //   194: aload_3
    //   195: ifnull -> 213
    //   198: aload_3
    //   199: aload #12
    //   201: getfield authority : Ljava/lang/String;
    //   204: invokevirtual equals : (Ljava/lang/Object;)Z
    //   207: ifne -> 213
    //   210: goto -> 168
    //   213: aload #12
    //   215: getfield backoffTime : J
    //   218: lload #4
    //   220: lcmp
    //   221: ifne -> 235
    //   224: aload #12
    //   226: getfield backoffDelay : J
    //   229: lload #6
    //   231: lcmp
    //   232: ifeq -> 168
    //   235: aload #12
    //   237: lload #4
    //   239: putfield backoffTime : J
    //   242: aload #12
    //   244: lload #6
    //   246: putfield backoffDelay : J
    //   249: iconst_1
    //   250: istore #9
    //   252: goto -> 168
    //   255: aload #8
    //   257: monitorexit
    //   258: iload #9
    //   260: ifeq -> 268
    //   263: aload_0
    //   264: iconst_1
    //   265: invokespecial reportChange : (I)V
    //   268: return
    //   269: astore_1
    //   270: aload #8
    //   272: monitorexit
    //   273: aload_1
    //   274: athrow
    // Exception table:
    //   from	to	target	type
    //   20	53	269	finally
    //   54	66	269	finally
    //   72	86	269	finally
    //   93	115	269	finally
    //   119	146	269	finally
    //   149	164	269	finally
    //   172	194	269	finally
    //   198	210	269	finally
    //   213	235	269	finally
    //   235	249	269	finally
    //   255	258	269	finally
    //   270	273	269	finally
  }
  
  public void setDelayUntilTime(Account paramAccount, int paramInt, String paramString, long paramLong) {
    synchronized (this.mAuthorities) {
      AuthorityInfo authorityInfo = getOrCreateAuthorityLocked(paramAccount, paramInt, paramString, -1, true);
      if (authorityInfo.delayUntil == paramLong)
        return; 
      authorityInfo.delayUntil = paramLong;
      reportChange(1);
      return;
    } 
  }
  
  public void setIsSyncable(Account paramAccount, int paramInt1, String paramString, int paramInt2) {
    int i;
    if (paramInt2 > 1) {
      i = 1;
    } else {
      i = paramInt2;
      if (paramInt2 < -1)
        i = -1; 
    } 
    synchronized (this.mAuthorities) {
      AuthorityInfo authorityInfo = getOrCreateAuthorityLocked(paramAccount, paramInt1, paramString, -1, false);
      if (authorityInfo.syncable == i)
        return; 
      authorityInfo.syncable = i;
      writeAccountInfoLocked();
      if (i > 0)
        requestSync(paramAccount, paramInt1, -5, paramString, new Bundle()); 
      reportChange(1);
      return;
    } 
  }
  
  public void setMasterSyncAutomatically(boolean paramBoolean, int paramInt) {
    synchronized (this.mAuthorities) {
      Boolean bool = (Boolean)this.mMasterSyncAutomatically.get(paramInt);
      if (bool != null && bool.booleanValue() == paramBoolean)
        return; 
      this.mMasterSyncAutomatically.put(paramInt, Boolean.valueOf(paramBoolean));
      writeAccountInfoLocked();
      if (paramBoolean)
        requestSync((Account)null, paramInt, -7, (String)null, new Bundle()); 
      reportChange(1);
      return;
    } 
  }
  
  protected void setOnSyncRequestListener(OnSyncRequestListener paramOnSyncRequestListener) {
    if (this.mSyncRequestListener == null)
      this.mSyncRequestListener = paramOnSyncRequestListener; 
  }
  
  public void setPeriodicSyncTime(int paramInt, PeriodicSync paramPeriodicSync, long paramLong) {
    synchronized (this.mAuthorities) {
      boolean bool2;
      AuthorityInfo authorityInfo = (AuthorityInfo)this.mAuthorities.get(paramInt);
      boolean bool1 = false;
      byte b = 0;
      while (true) {
        bool2 = bool1;
        if (b < authorityInfo.periodicSyncs.size()) {
          if (paramPeriodicSync.equals(authorityInfo.periodicSyncs.get(b))) {
            ((SyncStatusInfo)this.mSyncStatus.get(paramInt)).setPeriodicSyncTime(b, paramLong);
            bool2 = true;
            break;
          } 
          b++;
          continue;
        } 
        break;
      } 
      if (!bool2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ignoring setPeriodicSyncTime request for a sync that does not exist. Authority: ");
        stringBuilder.append(authorityInfo.authority);
        Log.w("SyncManager", stringBuilder.toString());
      } 
      return;
    } 
  }
  
  public void setSyncAutomatically(Account paramAccount, int paramInt, String paramString, boolean paramBoolean) {
    synchronized (this.mAuthorities) {
      AuthorityInfo authorityInfo = getOrCreateAuthorityLocked(paramAccount, paramInt, paramString, -1, false);
      if (authorityInfo.enabled == paramBoolean)
        return; 
      authorityInfo.enabled = paramBoolean;
      writeAccountInfoLocked();
      if (paramBoolean)
        requestSync(paramAccount, paramInt, -6, paramString, new Bundle()); 
      reportChange(1);
      return;
    } 
  }
  
  public void stopSyncEvent(long paramLong1, long paramLong2, String paramString, long paramLong3, long paramLong4) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mAuthorities : Landroid/util/SparseArray;
    //   4: astore #10
    //   6: aload #10
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield mSyncHistory : Ljava/util/ArrayList;
    //   13: invokevirtual size : ()I
    //   16: istore #11
    //   18: iload #11
    //   20: ifle -> 54
    //   23: iinc #11, -1
    //   26: aload_0
    //   27: getfield mSyncHistory : Ljava/util/ArrayList;
    //   30: iload #11
    //   32: invokevirtual get : (I)Ljava/lang/Object;
    //   35: checkcast com/lody/virtual/server/content/SyncStorageEngine$SyncHistoryItem
    //   38: astore #12
    //   40: aload #12
    //   42: getfield historyId : I
    //   45: i2l
    //   46: lload_1
    //   47: lcmp
    //   48: ifne -> 18
    //   51: goto -> 57
    //   54: aconst_null
    //   55: astore #12
    //   57: aload #12
    //   59: ifnonnull -> 103
    //   62: new java/lang/StringBuilder
    //   65: astore #5
    //   67: aload #5
    //   69: invokespecial <init> : ()V
    //   72: aload #5
    //   74: ldc_w 'stopSyncEvent: no history for id '
    //   77: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: pop
    //   81: aload #5
    //   83: lload_1
    //   84: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   87: pop
    //   88: ldc 'SyncManager'
    //   90: aload #5
    //   92: invokevirtual toString : ()Ljava/lang/String;
    //   95: invokestatic w : (Ljava/lang/String;Ljava/lang/String;)I
    //   98: pop
    //   99: aload #10
    //   101: monitorexit
    //   102: return
    //   103: aload #12
    //   105: lload_3
    //   106: putfield elapsedTime : J
    //   109: aload #12
    //   111: iconst_1
    //   112: putfield event : I
    //   115: aload #12
    //   117: aload #5
    //   119: putfield mesg : Ljava/lang/String;
    //   122: aload #12
    //   124: lload #6
    //   126: putfield downstreamActivity : J
    //   129: aload #12
    //   131: lload #8
    //   133: putfield upstreamActivity : J
    //   136: aload_0
    //   137: aload #12
    //   139: getfield authorityId : I
    //   142: invokespecial getOrCreateSyncStatusLocked : (I)Landroid/content/SyncStatusInfo;
    //   145: astore #13
    //   147: aload #13
    //   149: aload #13
    //   151: getfield numSyncs : I
    //   154: iconst_1
    //   155: iadd
    //   156: putfield numSyncs : I
    //   159: aload #13
    //   161: aload #13
    //   163: getfield totalElapsedTime : J
    //   166: lload_3
    //   167: ladd
    //   168: putfield totalElapsedTime : J
    //   171: aload #12
    //   173: getfield source : I
    //   176: istore #11
    //   178: iload #11
    //   180: ifeq -> 270
    //   183: iload #11
    //   185: iconst_1
    //   186: if_icmpeq -> 255
    //   189: iload #11
    //   191: iconst_2
    //   192: if_icmpeq -> 240
    //   195: iload #11
    //   197: iconst_3
    //   198: if_icmpeq -> 225
    //   201: iload #11
    //   203: iconst_4
    //   204: if_icmpeq -> 210
    //   207: goto -> 282
    //   210: aload #13
    //   212: aload #13
    //   214: getfield numSourcePeriodic : I
    //   217: iconst_1
    //   218: iadd
    //   219: putfield numSourcePeriodic : I
    //   222: goto -> 282
    //   225: aload #13
    //   227: aload #13
    //   229: getfield numSourceUser : I
    //   232: iconst_1
    //   233: iadd
    //   234: putfield numSourceUser : I
    //   237: goto -> 282
    //   240: aload #13
    //   242: aload #13
    //   244: getfield numSourcePoll : I
    //   247: iconst_1
    //   248: iadd
    //   249: putfield numSourcePoll : I
    //   252: goto -> 282
    //   255: aload #13
    //   257: aload #13
    //   259: getfield numSourceLocal : I
    //   262: iconst_1
    //   263: iadd
    //   264: putfield numSourceLocal : I
    //   267: goto -> 282
    //   270: aload #13
    //   272: aload #13
    //   274: getfield numSourceServer : I
    //   277: iconst_1
    //   278: iadd
    //   279: putfield numSourceServer : I
    //   282: aload_0
    //   283: invokespecial getCurrentDayLocked : ()I
    //   286: istore #14
    //   288: aload_0
    //   289: getfield mDayStats : [Lcom/lody/virtual/server/content/SyncStorageEngine$DayStats;
    //   292: astore #15
    //   294: iconst_0
    //   295: istore #11
    //   297: aload #15
    //   299: iconst_0
    //   300: aaload
    //   301: ifnonnull -> 331
    //   304: aload_0
    //   305: getfield mDayStats : [Lcom/lody/virtual/server/content/SyncStorageEngine$DayStats;
    //   308: astore #16
    //   310: new com/lody/virtual/server/content/SyncStorageEngine$DayStats
    //   313: astore #15
    //   315: aload #15
    //   317: iload #14
    //   319: invokespecial <init> : (I)V
    //   322: aload #16
    //   324: iconst_0
    //   325: aload #15
    //   327: aastore
    //   328: goto -> 394
    //   331: iload #14
    //   333: aload_0
    //   334: getfield mDayStats : [Lcom/lody/virtual/server/content/SyncStorageEngine$DayStats;
    //   337: iconst_0
    //   338: aaload
    //   339: getfield day : I
    //   342: if_icmpeq -> 386
    //   345: aload_0
    //   346: getfield mDayStats : [Lcom/lody/virtual/server/content/SyncStorageEngine$DayStats;
    //   349: iconst_0
    //   350: aload_0
    //   351: getfield mDayStats : [Lcom/lody/virtual/server/content/SyncStorageEngine$DayStats;
    //   354: iconst_1
    //   355: aload_0
    //   356: getfield mDayStats : [Lcom/lody/virtual/server/content/SyncStorageEngine$DayStats;
    //   359: arraylength
    //   360: iconst_1
    //   361: isub
    //   362: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   365: aload_0
    //   366: getfield mDayStats : [Lcom/lody/virtual/server/content/SyncStorageEngine$DayStats;
    //   369: iconst_0
    //   370: new com/lody/virtual/server/content/SyncStorageEngine$DayStats
    //   373: dup
    //   374: iload #14
    //   376: invokespecial <init> : (I)V
    //   379: aastore
    //   380: iconst_1
    //   381: istore #14
    //   383: goto -> 397
    //   386: aload_0
    //   387: getfield mDayStats : [Lcom/lody/virtual/server/content/SyncStorageEngine$DayStats;
    //   390: iconst_0
    //   391: aaload
    //   392: astore #15
    //   394: iconst_0
    //   395: istore #14
    //   397: aload_0
    //   398: getfield mDayStats : [Lcom/lody/virtual/server/content/SyncStorageEngine$DayStats;
    //   401: iconst_0
    //   402: aaload
    //   403: astore #15
    //   405: aload #12
    //   407: getfield eventTime : J
    //   410: lload_3
    //   411: ladd
    //   412: lstore_1
    //   413: ldc 'success'
    //   415: aload #5
    //   417: invokevirtual equals : (Ljava/lang/Object;)Z
    //   420: ifeq -> 513
    //   423: aload #13
    //   425: getfield lastSuccessTime : J
    //   428: lconst_0
    //   429: lcmp
    //   430: ifeq -> 443
    //   433: aload #13
    //   435: getfield lastFailureTime : J
    //   438: lconst_0
    //   439: lcmp
    //   440: ifeq -> 446
    //   443: iconst_1
    //   444: istore #11
    //   446: aload #13
    //   448: lload_1
    //   449: putfield lastSuccessTime : J
    //   452: aload #13
    //   454: aload #12
    //   456: getfield source : I
    //   459: putfield lastSuccessSource : I
    //   462: aload #13
    //   464: lconst_0
    //   465: putfield lastFailureTime : J
    //   468: aload #13
    //   470: iconst_m1
    //   471: putfield lastFailureSource : I
    //   474: aload #13
    //   476: aconst_null
    //   477: putfield lastFailureMesg : Ljava/lang/String;
    //   480: aload #13
    //   482: lconst_0
    //   483: putfield initialFailureTime : J
    //   486: aload #15
    //   488: aload #15
    //   490: getfield successCount : I
    //   493: iconst_1
    //   494: iadd
    //   495: putfield successCount : I
    //   498: aload #15
    //   500: aload #15
    //   502: getfield successTime : J
    //   505: lload_3
    //   506: ladd
    //   507: putfield successTime : J
    //   510: goto -> 611
    //   513: ldc 'canceled'
    //   515: aload #5
    //   517: invokevirtual equals : (Ljava/lang/Object;)Z
    //   520: ifne -> 608
    //   523: aload #13
    //   525: getfield lastFailureTime : J
    //   528: lconst_0
    //   529: lcmp
    //   530: ifne -> 539
    //   533: iconst_1
    //   534: istore #11
    //   536: goto -> 542
    //   539: iconst_0
    //   540: istore #11
    //   542: aload #13
    //   544: lload_1
    //   545: putfield lastFailureTime : J
    //   548: aload #13
    //   550: aload #12
    //   552: getfield source : I
    //   555: putfield lastFailureSource : I
    //   558: aload #13
    //   560: aload #5
    //   562: putfield lastFailureMesg : Ljava/lang/String;
    //   565: aload #13
    //   567: getfield initialFailureTime : J
    //   570: lconst_0
    //   571: lcmp
    //   572: ifne -> 581
    //   575: aload #13
    //   577: lload_1
    //   578: putfield initialFailureTime : J
    //   581: aload #15
    //   583: aload #15
    //   585: getfield failureCount : I
    //   588: iconst_1
    //   589: iadd
    //   590: putfield failureCount : I
    //   593: aload #15
    //   595: aload #15
    //   597: getfield failureTime : J
    //   600: lload_3
    //   601: ladd
    //   602: putfield failureTime : J
    //   605: goto -> 611
    //   608: iconst_0
    //   609: istore #11
    //   611: iload #11
    //   613: ifeq -> 623
    //   616: aload_0
    //   617: invokespecial writeStatusLocked : ()V
    //   620: goto -> 644
    //   623: aload_0
    //   624: iconst_1
    //   625: invokevirtual hasMessages : (I)Z
    //   628: ifne -> 644
    //   631: aload_0
    //   632: aload_0
    //   633: iconst_1
    //   634: invokevirtual obtainMessage : (I)Landroid/os/Message;
    //   637: ldc2_w 600000
    //   640: invokevirtual sendMessageDelayed : (Landroid/os/Message;J)Z
    //   643: pop
    //   644: iload #14
    //   646: ifeq -> 656
    //   649: aload_0
    //   650: invokespecial writeStatisticsLocked : ()V
    //   653: goto -> 677
    //   656: aload_0
    //   657: iconst_2
    //   658: invokevirtual hasMessages : (I)Z
    //   661: ifne -> 677
    //   664: aload_0
    //   665: aload_0
    //   666: iconst_2
    //   667: invokevirtual obtainMessage : (I)Landroid/os/Message;
    //   670: ldc2_w 1800000
    //   673: invokevirtual sendMessageDelayed : (Landroid/os/Message;J)Z
    //   676: pop
    //   677: aload #10
    //   679: monitorexit
    //   680: aload_0
    //   681: bipush #8
    //   683: invokespecial reportChange : (I)V
    //   686: return
    //   687: astore #5
    //   689: aload #10
    //   691: monitorexit
    //   692: aload #5
    //   694: athrow
    // Exception table:
    //   from	to	target	type
    //   9	18	687	finally
    //   26	40	687	finally
    //   40	51	687	finally
    //   62	102	687	finally
    //   103	178	687	finally
    //   210	222	687	finally
    //   225	237	687	finally
    //   240	252	687	finally
    //   255	267	687	finally
    //   270	282	687	finally
    //   282	294	687	finally
    //   304	322	687	finally
    //   331	380	687	finally
    //   386	394	687	finally
    //   397	433	687	finally
    //   433	443	687	finally
    //   446	510	687	finally
    //   513	533	687	finally
    //   542	581	687	finally
    //   581	605	687	finally
    //   616	620	687	finally
    //   623	644	687	finally
    //   649	653	687	finally
    //   656	677	687	finally
    //   677	680	687	finally
    //   689	692	687	finally
  }
  
  public void writeAllState() {
    synchronized (this.mAuthorities) {
      if (this.mNumPendingFinished > 0)
        writePendingOperationsLocked(); 
      writeStatusLocked();
      writeStatisticsLocked();
      return;
    } 
  }
  
  static {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    sAuthorityRenames = (HashMap)hashMap;
    hashMap.put("contacts", "com.android.contacts");
    sAuthorityRenames.put("calendar", "com.android.calendar");
  }
  
  static class AccountInfo {
    final AccountAndUser accountAndUser;
    
    final HashMap<String, SyncStorageEngine.AuthorityInfo> authorities = new HashMap<String, SyncStorageEngine.AuthorityInfo>();
    
    AccountInfo(AccountAndUser param1AccountAndUser) {
      this.accountAndUser = param1AccountAndUser;
    }
  }
  
  public static class AuthorityInfo {
    final Account account;
    
    final String authority;
    
    long backoffDelay;
    
    long backoffTime;
    
    long delayUntil;
    
    boolean enabled;
    
    final int ident;
    
    final ArrayList<PeriodicSync> periodicSyncs;
    
    final ComponentName service;
    
    int syncable;
    
    final int userId;
    
    AuthorityInfo(Account param1Account, int param1Int1, String param1String, int param1Int2) {
      this.account = param1Account;
      this.userId = param1Int1;
      this.authority = param1String;
      this.service = null;
      this.ident = param1Int2;
      this.enabled = false;
      this.syncable = -1;
      this.backoffTime = -1L;
      this.backoffDelay = -1L;
      this.periodicSyncs = new ArrayList<PeriodicSync>();
      PeriodicSync periodicSync = new PeriodicSync(param1Account, param1String, new Bundle(), 86400L);
      long l = SyncStorageEngine.calculateDefaultFlexTime(86400L);
      PeriodicSync.flexTime.set(periodicSync, l);
      this.periodicSyncs.add(periodicSync);
    }
    
    AuthorityInfo(ComponentName param1ComponentName, int param1Int1, int param1Int2) {
      this.account = null;
      this.userId = param1Int1;
      this.authority = null;
      this.service = param1ComponentName;
      this.ident = param1Int2;
      this.enabled = true;
      this.syncable = -1;
      this.backoffTime = -1L;
      this.backoffDelay = -1L;
      this.periodicSyncs = new ArrayList<PeriodicSync>();
      PeriodicSync periodicSync = new PeriodicSync(this.account, this.authority, new Bundle(), 86400L);
      PeriodicSync.flexTime.set(periodicSync, SyncStorageEngine.calculateDefaultFlexTime(86400L));
      this.periodicSyncs.add(periodicSync);
    }
    
    AuthorityInfo(AuthorityInfo param1AuthorityInfo) {
      this.account = param1AuthorityInfo.account;
      this.userId = param1AuthorityInfo.userId;
      this.authority = param1AuthorityInfo.authority;
      this.service = param1AuthorityInfo.service;
      this.ident = param1AuthorityInfo.ident;
      this.enabled = param1AuthorityInfo.enabled;
      this.syncable = param1AuthorityInfo.syncable;
      this.backoffTime = param1AuthorityInfo.backoffTime;
      this.backoffDelay = param1AuthorityInfo.backoffDelay;
      this.delayUntil = param1AuthorityInfo.delayUntil;
      this.periodicSyncs = new ArrayList<PeriodicSync>();
      for (PeriodicSync periodicSync : param1AuthorityInfo.periodicSyncs)
        this.periodicSyncs.add(PeriodicSync.clone(periodicSync)); 
    }
  }
  
  public static class DayStats {
    public final int day;
    
    public int failureCount;
    
    public long failureTime;
    
    public int successCount;
    
    public long successTime;
    
    public DayStats(int param1Int) {
      this.day = param1Int;
    }
  }
  
  public static interface OnSyncRequestListener {
    void onSyncRequest(Account param1Account, int param1Int1, int param1Int2, String param1String, Bundle param1Bundle);
  }
  
  public static class PendingOperation {
    final Account account;
    
    final String authority;
    
    int authorityId;
    
    final boolean expedited;
    
    final Bundle extras;
    
    byte[] flatExtras;
    
    final int reason;
    
    final ComponentName serviceName;
    
    final int syncSource;
    
    final int userId;
    
    PendingOperation(Account param1Account, int param1Int1, int param1Int2, int param1Int3, String param1String, Bundle param1Bundle, boolean param1Boolean) {
      this.account = param1Account;
      this.userId = param1Int1;
      this.syncSource = param1Int3;
      this.reason = param1Int2;
      this.authority = param1String;
      Bundle bundle = param1Bundle;
      if (param1Bundle != null)
        bundle = new Bundle(param1Bundle); 
      this.extras = bundle;
      this.expedited = param1Boolean;
      this.authorityId = -1;
      this.serviceName = null;
    }
    
    PendingOperation(PendingOperation param1PendingOperation) {
      this.account = param1PendingOperation.account;
      this.userId = param1PendingOperation.userId;
      this.reason = param1PendingOperation.reason;
      this.syncSource = param1PendingOperation.syncSource;
      this.authority = param1PendingOperation.authority;
      this.extras = param1PendingOperation.extras;
      this.authorityId = param1PendingOperation.authorityId;
      this.expedited = param1PendingOperation.expedited;
      this.serviceName = param1PendingOperation.serviceName;
    }
  }
  
  public static class SyncHistoryItem {
    int authorityId;
    
    long downstreamActivity;
    
    long elapsedTime;
    
    int event;
    
    long eventTime;
    
    Bundle extras;
    
    int historyId;
    
    boolean initialization;
    
    String mesg;
    
    int reason;
    
    int source;
    
    long upstreamActivity;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\content\SyncStorageEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */