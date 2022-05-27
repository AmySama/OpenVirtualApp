package com.lody.virtual.server.pm;

import android.app.IStopUserCallback;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.util.SparseArray;
import com.lody.virtual.R;
import com.lody.virtual.client.env.SpecialComponentList;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.AtomicFile;
import com.lody.virtual.helper.utils.FastXmlSerializer;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.os.VUserInfo;
import com.lody.virtual.os.VUserManager;
import com.lody.virtual.server.am.VActivityManagerService;
import com.lody.virtual.server.interfaces.IUserManager;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class VUserManagerService extends IUserManager.Stub {
  private static final String ATTR_CREATION_TIME = "created";
  
  private static final String ATTR_FLAGS = "flags";
  
  private static final String ATTR_ICON_PATH = "icon";
  
  private static final String ATTR_ID = "id";
  
  private static final String ATTR_LAST_LOGGED_IN_TIME = "lastLoggedIn";
  
  private static final String ATTR_NEXT_SERIAL_NO = "nextSerialNumber";
  
  private static final String ATTR_PARTIAL = "partial";
  
  private static final String ATTR_SERIAL_NO = "serialNumber";
  
  private static final String ATTR_USER_VERSION = "version";
  
  private static final boolean DBG = false;
  
  private static final long EPOCH_PLUS_30_YEARS = 946080000000L;
  
  private static final String LOG_TAG = "VUserManagerService";
  
  private static final int MIN_USER_ID = 1;
  
  private static final String TAG_NAME = "name";
  
  private static final String TAG_USER = "user";
  
  private static final String TAG_USERS = "users";
  
  private static final String USER_INFO_DIR;
  
  private static final String USER_LIST_FILENAME = "userlist.xml";
  
  private static final String USER_PHOTO_FILENAME = "photo.png";
  
  private static final int USER_VERSION = 1;
  
  private static VUserManagerService sInstance;
  
  private final File mBaseUserPath;
  
  private final Context mContext;
  
  private boolean mGuestEnabled;
  
  private final Object mInstallLock;
  
  private int mNextSerialNumber;
  
  private int mNextUserId;
  
  private final Object mPackagesLock;
  
  private final VPackageManagerService mPm;
  
  private HashSet<Integer> mRemovingUserIds;
  
  private int[] mUserIds;
  
  private final File mUserListFile;
  
  private int mUserVersion;
  
  private SparseArray<VUserInfo> mUsers;
  
  private final File mUsersDir;
  
  static {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("system");
    stringBuilder.append(File.separator);
    stringBuilder.append("users");
    USER_INFO_DIR = stringBuilder.toString();
  }
  
  VUserManagerService(Context paramContext, VPackageManagerService paramVPackageManagerService, Object paramObject1, Object paramObject2) {
    this(paramContext, paramVPackageManagerService, paramObject1, paramObject2, VEnvironment.getDataDirectory(), new File(VEnvironment.getDataDirectory(), "user"));
  }
  
  private VUserManagerService(Context paramContext, VPackageManagerService paramVPackageManagerService, Object paramObject1, Object paramObject2, File paramFile1, File paramFile2) {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial <init> : ()V
    //   4: aload_0
    //   5: new android/util/SparseArray
    //   8: dup
    //   9: invokespecial <init> : ()V
    //   12: putfield mUsers : Landroid/util/SparseArray;
    //   15: aload_0
    //   16: new java/util/HashSet
    //   19: dup
    //   20: invokespecial <init> : ()V
    //   23: putfield mRemovingUserIds : Ljava/util/HashSet;
    //   26: aload_0
    //   27: iconst_1
    //   28: putfield mNextUserId : I
    //   31: aload_0
    //   32: iconst_0
    //   33: putfield mUserVersion : I
    //   36: aload_0
    //   37: aload_1
    //   38: putfield mContext : Landroid/content/Context;
    //   41: aload_0
    //   42: aload_2
    //   43: putfield mPm : Lcom/lody/virtual/server/pm/VPackageManagerService;
    //   46: aload_0
    //   47: aload_3
    //   48: putfield mInstallLock : Ljava/lang/Object;
    //   51: aload_0
    //   52: aload #4
    //   54: putfield mPackagesLock : Ljava/lang/Object;
    //   57: aload_3
    //   58: monitorenter
    //   59: aload_0
    //   60: getfield mPackagesLock : Ljava/lang/Object;
    //   63: astore_1
    //   64: aload_1
    //   65: monitorenter
    //   66: new java/io/File
    //   69: astore_2
    //   70: aload_2
    //   71: aload #5
    //   73: getstatic com/lody/virtual/server/pm/VUserManagerService.USER_INFO_DIR : Ljava/lang/String;
    //   76: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   79: aload_0
    //   80: aload_2
    //   81: putfield mUsersDir : Ljava/io/File;
    //   84: aload_2
    //   85: invokevirtual mkdirs : ()Z
    //   88: pop
    //   89: new java/io/File
    //   92: astore_2
    //   93: aload_2
    //   94: aload_0
    //   95: getfield mUsersDir : Ljava/io/File;
    //   98: ldc '0'
    //   100: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   103: aload_2
    //   104: invokevirtual mkdirs : ()Z
    //   107: pop
    //   108: aload_0
    //   109: aload #6
    //   111: putfield mBaseUserPath : Ljava/io/File;
    //   114: new java/io/File
    //   117: astore_2
    //   118: aload_2
    //   119: aload_0
    //   120: getfield mUsersDir : Ljava/io/File;
    //   123: ldc 'userlist.xml'
    //   125: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   128: aload_0
    //   129: aload_2
    //   130: putfield mUserListFile : Ljava/io/File;
    //   133: aload_0
    //   134: invokespecial readUserListLocked : ()V
    //   137: new java/util/ArrayList
    //   140: astore_2
    //   141: aload_2
    //   142: invokespecial <init> : ()V
    //   145: iconst_0
    //   146: istore #7
    //   148: iload #7
    //   150: aload_0
    //   151: getfield mUsers : Landroid/util/SparseArray;
    //   154: invokevirtual size : ()I
    //   157: if_icmpge -> 200
    //   160: aload_0
    //   161: getfield mUsers : Landroid/util/SparseArray;
    //   164: iload #7
    //   166: invokevirtual valueAt : (I)Ljava/lang/Object;
    //   169: checkcast com/lody/virtual/os/VUserInfo
    //   172: astore #4
    //   174: aload #4
    //   176: getfield partial : Z
    //   179: ifeq -> 194
    //   182: iload #7
    //   184: ifeq -> 194
    //   187: aload_2
    //   188: aload #4
    //   190: invokevirtual add : (Ljava/lang/Object;)Z
    //   193: pop
    //   194: iinc #7, 1
    //   197: goto -> 148
    //   200: iconst_0
    //   201: istore #7
    //   203: iload #7
    //   205: aload_2
    //   206: invokevirtual size : ()I
    //   209: if_icmpge -> 305
    //   212: aload_2
    //   213: iload #7
    //   215: invokevirtual get : (I)Ljava/lang/Object;
    //   218: checkcast com/lody/virtual/os/VUserInfo
    //   221: astore #4
    //   223: new java/lang/StringBuilder
    //   226: astore #5
    //   228: aload #5
    //   230: invokespecial <init> : ()V
    //   233: aload #5
    //   235: ldc 'Removing partially created user #'
    //   237: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   240: pop
    //   241: aload #5
    //   243: iload #7
    //   245: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   248: pop
    //   249: aload #5
    //   251: ldc ' (name='
    //   253: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   256: pop
    //   257: aload #5
    //   259: aload #4
    //   261: getfield name : Ljava/lang/String;
    //   264: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   267: pop
    //   268: aload #5
    //   270: ldc ')'
    //   272: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   275: pop
    //   276: ldc 'VUserManagerService'
    //   278: aload #5
    //   280: invokevirtual toString : ()Ljava/lang/String;
    //   283: iconst_0
    //   284: anewarray java/lang/Object
    //   287: invokestatic w : (Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   290: aload_0
    //   291: aload #4
    //   293: getfield id : I
    //   296: invokespecial removeUserStateLocked : (I)V
    //   299: iinc #7, 1
    //   302: goto -> 203
    //   305: aload_0
    //   306: putstatic com/lody/virtual/server/pm/VUserManagerService.sInstance : Lcom/lody/virtual/server/pm/VUserManagerService;
    //   309: aload_1
    //   310: monitorexit
    //   311: aload_3
    //   312: monitorexit
    //   313: return
    //   314: astore_2
    //   315: aload_1
    //   316: monitorexit
    //   317: aload_2
    //   318: athrow
    //   319: astore_1
    //   320: aload_3
    //   321: monitorexit
    //   322: aload_1
    //   323: athrow
    // Exception table:
    //   from	to	target	type
    //   59	66	319	finally
    //   66	145	314	finally
    //   148	182	314	finally
    //   187	194	314	finally
    //   203	299	314	finally
    //   305	311	314	finally
    //   311	313	319	finally
    //   315	317	314	finally
    //   317	319	319	finally
    //   320	322	319	finally
  }
  
  private void fallbackToSingleUserLocked() {
    VUserInfo vUserInfo = new VUserInfo(0, this.mContext.getResources().getString(R.string.owner_name), null, 19);
    this.mUsers.put(0, vUserInfo);
    this.mNextSerialNumber = 1;
    updateUserIdsLocked();
    writeUserListLocked();
    writeUserLocked(vUserInfo);
  }
  
  public static VUserManagerService get() {
    // Byte code:
    //   0: ldc com/lody/virtual/server/pm/VUserManagerService
    //   2: monitorenter
    //   3: getstatic com/lody/virtual/server/pm/VUserManagerService.sInstance : Lcom/lody/virtual/server/pm/VUserManagerService;
    //   6: astore_0
    //   7: ldc com/lody/virtual/server/pm/VUserManagerService
    //   9: monitorexit
    //   10: aload_0
    //   11: areturn
    //   12: astore_0
    //   13: ldc com/lody/virtual/server/pm/VUserManagerService
    //   15: monitorexit
    //   16: aload_0
    //   17: athrow
    // Exception table:
    //   from	to	target	type
    //   3	10	12	finally
    //   13	16	12	finally
  }
  
  private int getNextAvailableIdLocked() {
    synchronized (this.mPackagesLock) {
      int i;
      for (i = this.mNextUserId; i < Integer.MAX_VALUE && (this.mUsers.indexOfKey(i) >= 0 || this.mRemovingUserIds.contains(Integer.valueOf(i))); i++);
      this.mNextUserId = i + 1;
      return i;
    } 
  }
  
  private VUserInfo getUserInfoLocked(int paramInt) {
    StringBuilder stringBuilder;
    VUserInfo vUserInfo = (VUserInfo)this.mUsers.get(paramInt);
    if (vUserInfo != null && vUserInfo.partial && !this.mRemovingUserIds.contains(Integer.valueOf(paramInt))) {
      stringBuilder = new StringBuilder();
      stringBuilder.append("getUserInfo: unknown user #");
      stringBuilder.append(paramInt);
      VLog.w("VUserManagerService", stringBuilder.toString(), new Object[0]);
      return null;
    } 
    return (VUserInfo)stringBuilder;
  }
  
  private boolean isUserLimitReachedLocked() {
    boolean bool;
    if (this.mUsers.size() >= VUserManager.getMaxSupportedUsers()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private int readIntAttribute(XmlPullParser paramXmlPullParser, String paramString, int paramInt) {
    String str = paramXmlPullParser.getAttributeValue(null, paramString);
    if (str == null)
      return paramInt; 
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException numberFormatException) {
      return paramInt;
    } 
  }
  
  private long readLongAttribute(XmlPullParser paramXmlPullParser, String paramString, long paramLong) {
    String str = paramXmlPullParser.getAttributeValue(null, paramString);
    if (str == null)
      return paramLong; 
    try {
      return Long.parseLong(str);
    } catch (NumberFormatException numberFormatException) {
      return paramLong;
    } 
  }
  
  private VUserInfo readUser(int paramInt) {
    IOException iOException;
    XmlPullParser xmlPullParser = null;
    try {
      AtomicFile atomicFile = new AtomicFile();
      File file1 = new File();
      File file2 = this.mUsersDir;
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append(Integer.toString(paramInt));
      stringBuilder.append(".xml");
      this(file2, stringBuilder.toString());
      this(file1);
      FileInputStream fileInputStream = atomicFile.openRead();
    } catch (IOException iOException1) {
    
    } catch (XmlPullParserException xmlPullParserException) {
      xmlPullParserException = null;
    } finally {
      if (iOException != null)
        try {
          iOException.close();
        } catch (IOException iOException1) {} 
    } 
    if (SYNTHETIC_LOCAL_VARIABLE_4 != null) {
      try {
        SYNTHETIC_LOCAL_VARIABLE_4.close();
      } catch (IOException iOException1) {}
      return null;
    } 
    return null;
  }
  
  private void readUserList() {
    synchronized (this.mPackagesLock) {
      readUserListLocked();
      return;
    } 
  }
  
  private void readUserListLocked() {
    // Byte code:
    //   0: aload_0
    //   1: iconst_0
    //   2: putfield mGuestEnabled : Z
    //   5: aload_0
    //   6: getfield mUserListFile : Ljava/io/File;
    //   9: invokevirtual exists : ()Z
    //   12: ifne -> 20
    //   15: aload_0
    //   16: invokespecial fallbackToSingleUserLocked : ()V
    //   19: return
    //   20: new com/lody/virtual/helper/utils/AtomicFile
    //   23: dup
    //   24: aload_0
    //   25: getfield mUserListFile : Ljava/io/File;
    //   28: invokespecial <init> : (Ljava/io/File;)V
    //   31: astore_1
    //   32: aconst_null
    //   33: astore_2
    //   34: aconst_null
    //   35: astore_3
    //   36: aconst_null
    //   37: astore #4
    //   39: aload_1
    //   40: invokevirtual openRead : ()Ljava/io/FileInputStream;
    //   43: astore_1
    //   44: invokestatic newPullParser : ()Lorg/xmlpull/v1/XmlPullParser;
    //   47: astore #4
    //   49: aload #4
    //   51: aload_1
    //   52: aconst_null
    //   53: invokeinterface setInput : (Ljava/io/InputStream;Ljava/lang/String;)V
    //   58: aload #4
    //   60: invokeinterface next : ()I
    //   65: istore #5
    //   67: iload #5
    //   69: iconst_2
    //   70: if_icmpeq -> 82
    //   73: iload #5
    //   75: iconst_1
    //   76: if_icmpeq -> 82
    //   79: goto -> 58
    //   82: iload #5
    //   84: iconst_2
    //   85: if_icmpeq -> 119
    //   88: ldc 'VUserManagerService'
    //   90: ldc_w 'Unable to read user list'
    //   93: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   96: aload_0
    //   97: invokespecial fallbackToSingleUserLocked : ()V
    //   100: aload_1
    //   101: ifnull -> 118
    //   104: aload_1
    //   105: invokevirtual close : ()V
    //   108: goto -> 118
    //   111: astore #4
    //   113: aload #4
    //   115: invokevirtual printStackTrace : ()V
    //   118: return
    //   119: aload_0
    //   120: iconst_m1
    //   121: putfield mNextSerialNumber : I
    //   124: aload #4
    //   126: invokeinterface getName : ()Ljava/lang/String;
    //   131: ldc 'users'
    //   133: invokevirtual equals : (Ljava/lang/Object;)Z
    //   136: ifeq -> 185
    //   139: aload #4
    //   141: aconst_null
    //   142: ldc 'nextSerialNumber'
    //   144: invokeinterface getAttributeValue : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   149: astore_2
    //   150: aload_2
    //   151: ifnull -> 162
    //   154: aload_0
    //   155: aload_2
    //   156: invokestatic parseInt : (Ljava/lang/String;)I
    //   159: putfield mNextSerialNumber : I
    //   162: aload #4
    //   164: aconst_null
    //   165: ldc 'version'
    //   167: invokeinterface getAttributeValue : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   172: astore_2
    //   173: aload_2
    //   174: ifnull -> 185
    //   177: aload_0
    //   178: aload_2
    //   179: invokestatic parseInt : (Ljava/lang/String;)I
    //   182: putfield mUserVersion : I
    //   185: aload #4
    //   187: invokeinterface next : ()I
    //   192: istore #5
    //   194: iload #5
    //   196: iconst_1
    //   197: if_icmpeq -> 298
    //   200: iload #5
    //   202: iconst_2
    //   203: if_icmpne -> 185
    //   206: aload #4
    //   208: invokeinterface getName : ()Ljava/lang/String;
    //   213: ldc 'user'
    //   215: invokevirtual equals : (Ljava/lang/Object;)Z
    //   218: ifeq -> 185
    //   221: aload_0
    //   222: aload #4
    //   224: aconst_null
    //   225: ldc 'id'
    //   227: invokeinterface getAttributeValue : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   232: invokestatic parseInt : (Ljava/lang/String;)I
    //   235: invokespecial readUser : (I)Lcom/lody/virtual/os/VUserInfo;
    //   238: astore_2
    //   239: aload_2
    //   240: ifnull -> 185
    //   243: aload_0
    //   244: getfield mUsers : Landroid/util/SparseArray;
    //   247: aload_2
    //   248: getfield id : I
    //   251: aload_2
    //   252: invokevirtual put : (ILjava/lang/Object;)V
    //   255: aload_2
    //   256: invokevirtual isGuest : ()Z
    //   259: ifeq -> 267
    //   262: aload_0
    //   263: iconst_1
    //   264: putfield mGuestEnabled : Z
    //   267: aload_0
    //   268: getfield mNextSerialNumber : I
    //   271: iflt -> 285
    //   274: aload_0
    //   275: getfield mNextSerialNumber : I
    //   278: aload_2
    //   279: getfield id : I
    //   282: if_icmpgt -> 185
    //   285: aload_0
    //   286: aload_2
    //   287: getfield id : I
    //   290: iconst_1
    //   291: iadd
    //   292: putfield mNextSerialNumber : I
    //   295: goto -> 185
    //   298: aload_0
    //   299: invokespecial updateUserIdsLocked : ()V
    //   302: aload_0
    //   303: invokespecial upgradeIfNecessary : ()V
    //   306: aload_1
    //   307: ifnull -> 381
    //   310: aload_1
    //   311: invokevirtual close : ()V
    //   314: goto -> 381
    //   317: astore_2
    //   318: goto -> 382
    //   321: astore #4
    //   323: goto -> 338
    //   326: astore #4
    //   328: goto -> 356
    //   331: astore_2
    //   332: aload #4
    //   334: astore_1
    //   335: goto -> 382
    //   338: aload_1
    //   339: astore #4
    //   341: aload_0
    //   342: invokespecial fallbackToSingleUserLocked : ()V
    //   345: aload_1
    //   346: ifnull -> 381
    //   349: aload_1
    //   350: invokevirtual close : ()V
    //   353: goto -> 381
    //   356: aload_1
    //   357: astore #4
    //   359: aload_0
    //   360: invokespecial fallbackToSingleUserLocked : ()V
    //   363: aload_1
    //   364: ifnull -> 381
    //   367: aload_1
    //   368: invokevirtual close : ()V
    //   371: goto -> 381
    //   374: astore #4
    //   376: aload #4
    //   378: invokevirtual printStackTrace : ()V
    //   381: return
    //   382: aload_1
    //   383: ifnull -> 400
    //   386: aload_1
    //   387: invokevirtual close : ()V
    //   390: goto -> 400
    //   393: astore #4
    //   395: aload #4
    //   397: invokevirtual printStackTrace : ()V
    //   400: aload_2
    //   401: athrow
    //   402: astore #4
    //   404: aload_3
    //   405: astore_1
    //   406: goto -> 356
    //   409: astore #4
    //   411: aload_2
    //   412: astore_1
    //   413: goto -> 338
    // Exception table:
    //   from	to	target	type
    //   39	44	402	java/io/IOException
    //   39	44	409	org/xmlpull/v1/XmlPullParserException
    //   39	44	331	finally
    //   44	58	326	java/io/IOException
    //   44	58	321	org/xmlpull/v1/XmlPullParserException
    //   44	58	317	finally
    //   58	67	326	java/io/IOException
    //   58	67	321	org/xmlpull/v1/XmlPullParserException
    //   58	67	317	finally
    //   88	100	326	java/io/IOException
    //   88	100	321	org/xmlpull/v1/XmlPullParserException
    //   88	100	317	finally
    //   104	108	111	java/io/IOException
    //   119	150	326	java/io/IOException
    //   119	150	321	org/xmlpull/v1/XmlPullParserException
    //   119	150	317	finally
    //   154	162	326	java/io/IOException
    //   154	162	321	org/xmlpull/v1/XmlPullParserException
    //   154	162	317	finally
    //   162	173	326	java/io/IOException
    //   162	173	321	org/xmlpull/v1/XmlPullParserException
    //   162	173	317	finally
    //   177	185	326	java/io/IOException
    //   177	185	321	org/xmlpull/v1/XmlPullParserException
    //   177	185	317	finally
    //   185	194	326	java/io/IOException
    //   185	194	321	org/xmlpull/v1/XmlPullParserException
    //   185	194	317	finally
    //   206	239	326	java/io/IOException
    //   206	239	321	org/xmlpull/v1/XmlPullParserException
    //   206	239	317	finally
    //   243	267	326	java/io/IOException
    //   243	267	321	org/xmlpull/v1/XmlPullParserException
    //   243	267	317	finally
    //   267	285	326	java/io/IOException
    //   267	285	321	org/xmlpull/v1/XmlPullParserException
    //   267	285	317	finally
    //   285	295	326	java/io/IOException
    //   285	295	321	org/xmlpull/v1/XmlPullParserException
    //   285	295	317	finally
    //   298	306	326	java/io/IOException
    //   298	306	321	org/xmlpull/v1/XmlPullParserException
    //   298	306	317	finally
    //   310	314	374	java/io/IOException
    //   341	345	331	finally
    //   349	353	374	java/io/IOException
    //   359	363	331	finally
    //   367	371	374	java/io/IOException
    //   386	390	393	java/io/IOException
  }
  
  private void removeDirectoryRecursive(File paramFile) {
    if (paramFile.isDirectory()) {
      String[] arrayOfString = paramFile.list();
      int i = arrayOfString.length;
      for (byte b = 0; b < i; b++)
        removeDirectoryRecursive(new File(paramFile, arrayOfString[b])); 
    } 
    paramFile.delete();
  }
  
  private void removeUserStateLocked(int paramInt) {
    this.mPm.cleanUpUser(paramInt);
    this.mUsers.remove(paramInt);
    this.mRemovingUserIds.remove(Integer.valueOf(paramInt));
    File file = this.mUsersDir;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramInt);
    stringBuilder.append(".xml");
    (new AtomicFile(new File(file, stringBuilder.toString()))).delete();
    writeUserListLocked();
    updateUserIdsLocked();
    removeDirectoryRecursive(VEnvironment.getDataUserDirectory(paramInt));
  }
  
  private void sendUserInfoChangedBroadcast(int paramInt) {
    Intent intent = new Intent("virtual.android.intent.action.USER_CHANGED");
    intent.putExtra("android.intent.extra.user_handle", paramInt);
    intent.addFlags(1073741824);
    VActivityManagerService.get().sendBroadcastAsUser(intent, new VUserHandle(paramInt));
  }
  
  private void updateUserIdsLocked() {
    boolean bool = false;
    byte b = 0;
    int i;
    for (i = 0; b < this.mUsers.size(); i = j) {
      int j = i;
      if (!((VUserInfo)this.mUsers.valueAt(b)).partial)
        j = i + 1; 
      b++;
    } 
    int[] arrayOfInt = new int[i];
    i = 0;
    b = bool;
    while (b < this.mUsers.size()) {
      int j = i;
      if (!((VUserInfo)this.mUsers.valueAt(b)).partial) {
        arrayOfInt[i] = this.mUsers.keyAt(b);
        j = i + 1;
      } 
      b++;
      i = j;
    } 
    this.mUserIds = arrayOfInt;
  }
  
  private void upgradeIfNecessary() {
    int i = this.mUserVersion;
    int j = i;
    if (i < 1) {
      VUserInfo vUserInfo = (VUserInfo)this.mUsers.get(0);
      if ("Primary".equals(vUserInfo.name)) {
        vUserInfo.name = "Admin";
        writeUserLocked(vUserInfo);
      } 
      j = 1;
    } 
    if (j < 1) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("User version ");
      stringBuilder.append(this.mUserVersion);
      stringBuilder.append(" didn't upgrade as expected to ");
      stringBuilder.append(1);
      VLog.w("VUserManagerService", stringBuilder.toString(), new Object[0]);
    } else {
      this.mUserVersion = j;
      writeUserListLocked();
    } 
  }
  
  private void writeBitmapLocked(VUserInfo paramVUserInfo, Bitmap paramBitmap) {
    try {
      File file1 = new File();
      this(this.mUsersDir, Integer.toString(paramVUserInfo.id));
      File file2 = new File();
      this(file1, "photo.png");
      if (!file1.exists())
        file1.mkdir(); 
      Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;
      FileOutputStream fileOutputStream = new FileOutputStream();
      this(file2);
      if (paramBitmap.compress(compressFormat, 100, fileOutputStream))
        paramVUserInfo.iconPath = file2.getAbsolutePath(); 
      try {
        fileOutputStream.close();
      } catch (IOException iOException) {}
    } catch (FileNotFoundException fileNotFoundException) {
      VLog.w("VUserManagerService", "Error setting photo for user ", new Object[] { fileNotFoundException });
    } 
  }
  
  private void writeUserListLocked() {
    AtomicFile atomicFile = new AtomicFile(this.mUserListFile);
    FastXmlSerializer fastXmlSerializer = null;
    try {
      FileOutputStream fileOutputStream = atomicFile.startWrite();
      try {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream();
        this(fileOutputStream);
        fastXmlSerializer = new FastXmlSerializer();
        this();
        fastXmlSerializer.setOutput(bufferedOutputStream, "utf-8");
        fastXmlSerializer.startDocument(null, Boolean.valueOf(true));
        fastXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        fastXmlSerializer.startTag(null, "users");
        fastXmlSerializer.attribute(null, "nextSerialNumber", Integer.toString(this.mNextSerialNumber));
        fastXmlSerializer.attribute(null, "version", Integer.toString(this.mUserVersion));
        for (byte b = 0; b < this.mUsers.size(); b++) {
          VUserInfo vUserInfo = (VUserInfo)this.mUsers.valueAt(b);
          fastXmlSerializer.startTag(null, "user");
          fastXmlSerializer.attribute(null, "id", Integer.toString(vUserInfo.id));
          fastXmlSerializer.endTag(null, "user");
        } 
        fastXmlSerializer.endTag(null, "users");
        fastXmlSerializer.endDocument();
        atomicFile.finishWrite(fileOutputStream);
      } catch (Exception exception1) {
        atomicFile.failWrite(fileOutputStream);
        VLog.e("VUserManagerService", "Error writing user list");
      } 
      return;
    } catch (Exception exception2) {
      exception2 = exception1;
    } 
    atomicFile.failWrite((FileOutputStream)exception2);
    VLog.e("VUserManagerService", "Error writing user list");
  }
  
  private void writeUserLocked(VUserInfo paramVUserInfo) {
    FastXmlSerializer fastXmlSerializer1;
    File file = this.mUsersDir;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(paramVUserInfo.id);
    stringBuilder1.append(".xml");
    AtomicFile atomicFile = new AtomicFile(new File(file, stringBuilder1.toString()));
    FastXmlSerializer fastXmlSerializer2 = null;
    try {
      FileOutputStream fileOutputStream = atomicFile.startWrite();
      try {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream();
        this(fileOutputStream);
        fastXmlSerializer2 = new FastXmlSerializer();
        this();
        fastXmlSerializer2.setOutput(bufferedOutputStream, "utf-8");
        fastXmlSerializer2.startDocument(null, Boolean.valueOf(true));
        fastXmlSerializer2.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        fastXmlSerializer2.startTag(null, "user");
        fastXmlSerializer2.attribute(null, "id", Integer.toString(paramVUserInfo.id));
        fastXmlSerializer2.attribute(null, "serialNumber", Integer.toString(paramVUserInfo.serialNumber));
        fastXmlSerializer2.attribute(null, "flags", Integer.toString(paramVUserInfo.flags));
        fastXmlSerializer2.attribute(null, "created", Long.toString(paramVUserInfo.creationTime));
        fastXmlSerializer2.attribute(null, "lastLoggedIn", Long.toString(paramVUserInfo.lastLoggedInTime));
        if (paramVUserInfo.iconPath != null)
          fastXmlSerializer2.attribute(null, "icon", paramVUserInfo.iconPath); 
        if (paramVUserInfo.partial)
          fastXmlSerializer2.attribute(null, "partial", "true"); 
        fastXmlSerializer2.startTag(null, "name");
        fastXmlSerializer2.text(paramVUserInfo.name);
        fastXmlSerializer2.endTag(null, "name");
        fastXmlSerializer2.endTag(null, "user");
        fastXmlSerializer2.endDocument();
        atomicFile.finishWrite(fileOutputStream);
      } catch (Exception null) {}
    } catch (Exception exception) {
      fastXmlSerializer1 = fastXmlSerializer2;
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("Error writing user info ");
    stringBuilder2.append(paramVUserInfo.id);
    stringBuilder2.append("\n");
    stringBuilder2.append(exception);
    VLog.e("VUserManagerService", stringBuilder2.toString());
    atomicFile.failWrite((FileOutputStream)fastXmlSerializer1);
  }
  
  public VUserInfo createUser(String paramString, int paramInt) {
    long l = Binder.clearCallingIdentity();
    try {
    
    } finally {
      Binder.restoreCallingIdentity(l);
    } 
  }
  
  public boolean exists(int paramInt) {
    synchronized (this.mPackagesLock) {
      return ArrayUtils.contains(this.mUserIds, paramInt);
    } 
  }
  
  void finishRemoveUser(int paramInt) {
    long l = Binder.clearCallingIdentity();
    try {
      Intent intent = new Intent();
      this("virtual.android.intent.action.USER_REMOVED");
      intent.putExtra("android.intent.extra.user_handle", paramInt);
      VActivityManagerService vActivityManagerService = VActivityManagerService.get();
      VUserHandle vUserHandle = VUserHandle.ALL;
      BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
          public void onReceive(Context param1Context, Intent param1Intent) {
            (new Thread() {
                public void run() {
                  synchronized (VUserManagerService.this.mInstallLock) {
                    synchronized (VUserManagerService.this.mPackagesLock) {
                      VUserManagerService.this.removeUserStateLocked(userHandle);
                      return;
                    } 
                  } 
                }
              }).start();
          }
        };
      super(this, paramInt);
      vActivityManagerService.sendOrderedBroadcastAsUser(intent, vUserHandle, null, broadcastReceiver, null, -1, null, null);
      return;
    } finally {
      Binder.restoreCallingIdentity(l);
    } 
  }
  
  public int getUserHandle(int paramInt) {
    synchronized (this.mPackagesLock) {
      for (int i : this.mUserIds) {
        if ((getUserInfoLocked(i)).serialNumber == paramInt)
          return i; 
      } 
      return -1;
    } 
  }
  
  public Bitmap getUserIcon(int paramInt) {
    synchronized (this.mPackagesLock) {
      StringBuilder stringBuilder;
      VUserInfo vUserInfo = (VUserInfo)this.mUsers.get(paramInt);
      if (vUserInfo == null || vUserInfo.partial) {
        stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("getUserIcon: unknown user #");
        stringBuilder.append(paramInt);
        VLog.w("VUserManagerService", stringBuilder.toString(), new Object[0]);
        return null;
      } 
      if (((VUserInfo)stringBuilder).iconPath == null)
        return null; 
      return BitmapFactory.decodeFile(((VUserInfo)stringBuilder).iconPath);
    } 
  }
  
  public int[] getUserIds() {
    synchronized (this.mPackagesLock) {
      return this.mUserIds;
    } 
  }
  
  int[] getUserIdsLPr() {
    return this.mUserIds;
  }
  
  public VUserInfo getUserInfo(int paramInt) {
    synchronized (this.mPackagesLock) {
      return getUserInfoLocked(paramInt);
    } 
  }
  
  public int getUserSerialNumber(int paramInt) {
    synchronized (this.mPackagesLock) {
      if (!exists(paramInt))
        return -1; 
      paramInt = (getUserInfoLocked(paramInt)).serialNumber;
      return paramInt;
    } 
  }
  
  public List<VUserInfo> getUsers(boolean paramBoolean) {
    synchronized (this.mPackagesLock) {
      ArrayList<VUserInfo> arrayList = new ArrayList();
      this(this.mUsers.size());
      for (byte b = 0; b < this.mUsers.size(); b++) {
        VUserInfo vUserInfo = (VUserInfo)this.mUsers.valueAt(b);
        if (!vUserInfo.partial && (!paramBoolean || !this.mRemovingUserIds.contains(Integer.valueOf(vUserInfo.id))))
          arrayList.add(vUserInfo); 
      } 
      return arrayList;
    } 
  }
  
  public boolean isGuestEnabled() {
    synchronized (this.mPackagesLock) {
      return this.mGuestEnabled;
    } 
  }
  
  public void makeInitialized(int paramInt) {
    synchronized (this.mPackagesLock) {
      VUserInfo vUserInfo = (VUserInfo)this.mUsers.get(paramInt);
      if (vUserInfo == null || vUserInfo.partial) {
        StringBuilder stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("makeInitialized: unknown user #");
        stringBuilder.append(paramInt);
        VLog.w("VUserManagerService", stringBuilder.toString(), new Object[0]);
      } 
      if ((vUserInfo.flags & 0x10) == 0) {
        vUserInfo.flags |= 0x10;
        writeUserLocked(vUserInfo);
      } 
      return;
    } 
  }
  
  public boolean removeUser(int paramInt) {
    synchronized (this.mPackagesLock) {
      VUserInfo vUserInfo = (VUserInfo)this.mUsers.get(paramInt);
      boolean bool = false;
      if (paramInt == 0 || vUserInfo == null)
        return false; 
      this.mRemovingUserIds.add(Integer.valueOf(paramInt));
      vUserInfo.partial = true;
      writeUserLocked(vUserInfo);
      if (VActivityManagerService.get().stopUser(paramInt, new IStopUserCallback.Stub() {
            public void userStopAborted(int param1Int) {}
            
            public void userStopped(int param1Int) {
              VUserManagerService.this.finishRemoveUser(param1Int);
            }
          }) == 0)
        bool = true; 
      return bool;
    } 
  }
  
  public void setGuestEnabled(boolean paramBoolean) {
    synchronized (this.mPackagesLock) {
      if (this.mGuestEnabled != paramBoolean) {
        this.mGuestEnabled = paramBoolean;
        for (byte b = 0; b < this.mUsers.size(); b++) {
          VUserInfo vUserInfo = (VUserInfo)this.mUsers.valueAt(b);
          if (!vUserInfo.partial && vUserInfo.isGuest()) {
            if (!paramBoolean)
              removeUser(vUserInfo.id); 
            return;
          } 
        } 
        if (paramBoolean)
          createUser("Guest", 4); 
      } 
      return;
    } 
  }
  
  public void setUserIcon(int paramInt, Bitmap paramBitmap) {
    synchronized (this.mPackagesLock) {
      StringBuilder stringBuilder;
      VUserInfo vUserInfo = (VUserInfo)this.mUsers.get(paramInt);
      if (vUserInfo == null || vUserInfo.partial) {
        stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("setUserIcon: unknown user #");
        stringBuilder.append(paramInt);
        VLog.w("VUserManagerService", stringBuilder.toString(), new Object[0]);
        return;
      } 
      writeBitmapLocked(vUserInfo, (Bitmap)stringBuilder);
      writeUserLocked(vUserInfo);
      sendUserInfoChangedBroadcast(paramInt);
      return;
    } 
  }
  
  public void setUserName(int paramInt, String paramString) {
    synchronized (this.mPackagesLock) {
      StringBuilder stringBuilder;
      VUserInfo vUserInfo = (VUserInfo)this.mUsers.get(paramInt);
      boolean bool1 = false;
      if (vUserInfo == null || vUserInfo.partial) {
        stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("setUserName: unknown user #");
        stringBuilder.append(paramInt);
        VLog.w("VUserManagerService", stringBuilder.toString(), new Object[0]);
        return;
      } 
      boolean bool2 = bool1;
      if (stringBuilder != null) {
        bool2 = bool1;
        if (!stringBuilder.equals(vUserInfo.name)) {
          vUserInfo.name = (String)stringBuilder;
          writeUserLocked(vUserInfo);
          bool2 = true;
        } 
      } 
      if (bool2)
        sendUserInfoChangedBroadcast(paramInt); 
      return;
    } 
  }
  
  public void userForeground(int paramInt) {
    synchronized (this.mPackagesLock) {
      StringBuilder stringBuilder;
      VUserInfo vUserInfo = (VUserInfo)this.mUsers.get(paramInt);
      long l = System.currentTimeMillis();
      if (vUserInfo == null || vUserInfo.partial) {
        stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("userForeground: unknown user #");
        stringBuilder.append(paramInt);
        VLog.w("VUserManagerService", stringBuilder.toString(), new Object[0]);
        return;
      } 
      if (l > 946080000000L) {
        ((VUserInfo)stringBuilder).lastLoggedInTime = l;
        writeUserLocked((VUserInfo)stringBuilder);
      } 
      return;
    } 
  }
  
  public void wipeUser(int paramInt) {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\VUserManagerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */