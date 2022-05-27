package com.lody.virtual.server.pm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageParser;
import android.net.Uri;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.stub.StubManifest;
import com.lody.virtual.helper.PackageCleaner;
import com.lody.virtual.helper.collection.IntArray;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.helper.utils.Singleton;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.os.VUserInfo;
import com.lody.virtual.os.VUserManager;
import com.lody.virtual.remote.InstalledAppInfo;
import com.lody.virtual.remote.VAppInstallerParams;
import com.lody.virtual.remote.VAppInstallerResult;
import com.lody.virtual.server.accounts.VAccountManagerService;
import com.lody.virtual.server.am.UidSystem;
import com.lody.virtual.server.am.VActivityManagerService;
import com.lody.virtual.server.extension.VExtPackageAccessor;
import com.lody.virtual.server.interfaces.IAppManager;
import com.lody.virtual.server.interfaces.IPackageObserver;
import com.lody.virtual.server.notification.VNotificationManagerService;
import com.lody.virtual.server.pm.parser.PackageParserEx;
import com.lody.virtual.server.pm.parser.VPackage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VAppManagerService extends IAppManager.Stub {
  private static final String TAG = VAppManagerService.class.getSimpleName();
  
  private static final Singleton<VAppManagerService> sService = new Singleton<VAppManagerService>() {
      protected VAppManagerService create() {
        return new VAppManagerService();
      }
    };
  
  private final String ANDROID_TEST_BASE = "android.test.base";
  
  private final String ANDROID_TEST_RUNNER = "android.test.runner";
  
  private final String ORG_APACHE_HTTP_LEGACY = "org.apache.http.legacy";
  
  private BroadcastReceiver appEventReceiver = new BroadcastReceiver() {
      public void onReceive(Context param1Context, Intent param1Intent) {
        if (VAppManagerService.this.mScanning)
          return; 
        BroadcastReceiver.PendingResult pendingResult = goAsync();
        String str1 = param1Intent.getAction();
        if (str1 == null)
          return; 
        Uri uri = param1Intent.getData();
        if (uri == null)
          return; 
        String str2 = uri.getSchemeSpecificPart();
        if (str2 == null)
          return; 
        if (str2.equals(StubManifest.EXT_PACKAGE_NAME))
          VExtPackageAccessor.syncPackages(); 
        PackageSetting packageSetting = PackageCacheManager.getSetting(str2);
        if (packageSetting != null && packageSetting.dynamic) {
          VAppInstallerResult vAppInstallerResult;
          VAppManagerService vAppManagerService;
          StringBuilder stringBuilder;
          VActivityManagerService.get().killAppByPkg(str2, -1);
          if (str1.equals("android.intent.action.PACKAGE_REPLACED")) {
            ApplicationInfo applicationInfo;
            str1 = null;
            try {
              ApplicationInfo applicationInfo1 = VirtualCore.getPM().getApplicationInfo(str2, 0);
              applicationInfo = applicationInfo1;
            } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
              nameNotFoundException.printStackTrace();
            } 
            if (applicationInfo == null)
              return; 
            VAppInstallerParams vAppInstallerParams = new VAppInstallerParams(2, 1);
            vAppManagerService = VAppManagerService.this;
            stringBuilder = new StringBuilder();
            stringBuilder.append("package:");
            stringBuilder.append(str2);
            vAppInstallerResult = vAppManagerService.installPackageInternal(Uri.parse(stringBuilder.toString()), vAppInstallerParams);
            VLog.e(VAppManagerService.TAG, "Update package %s status: %d", new Object[] { vAppInstallerResult.packageName, Integer.valueOf(vAppInstallerResult.status) });
          } else if (vAppInstallerResult.equals("android.intent.action.PACKAGE_REMOVED") && vAppManagerService.getBooleanExtra("android.intent.extra.DATA_REMOVED", false)) {
            VLog.e(VAppManagerService.TAG, "Removing package %s", new Object[] { ((PackageSetting)stringBuilder).packageName });
            VAppManagerService.this.uninstallPackageFully((PackageSetting)stringBuilder, true);
          } 
          pendingResult.finish();
        } 
      }
    };
  
  private final PackagePersistenceLayer mPersistenceLayer = new PackagePersistenceLayer(this);
  
  private RemoteCallbackList<IPackageObserver> mRemoteCallbackList = new RemoteCallbackList();
  
  private volatile boolean mScanning;
  
  private final SystemConfig mSystemConfig = new SystemConfig();
  
  private final UidSystem mUidSystem = new UidSystem();
  
  private void cleanUpResidualFiles(PackageSetting paramPackageSetting) {
    VLog.e(TAG, "cleanup residual files for : %s", new Object[] { paramPackageSetting.packageName });
    uninstallPackageFully(paramPackageSetting, false);
  }
  
  private void extractApacheFrameworksForPie() {
    File file = VEnvironment.getOptimizedFrameworkFile("org.apache.http.legacy.boot");
    if (!file.exists())
      try {
        FileUtils.copyFileFromAssets(VirtualCore.get().getContext(), "org.apache.http.legacy.boot", file);
      } catch (IOException iOException) {
        iOException.printStackTrace();
      }  
  }
  
  public static VAppManagerService get() {
    return (VAppManagerService)sService.get();
  }
  
  private VAppInstallerResult installPackageInternal(Uri paramUri, VAppInstallerParams paramVAppInstallerParams) {
    // Byte code:
    //   0: invokestatic currentTimeMillis : ()J
    //   3: lstore_3
    //   4: aload_2
    //   5: invokevirtual getInstallFlags : ()I
    //   8: istore #5
    //   10: aload_1
    //   11: ifnull -> 2471
    //   14: aload_1
    //   15: invokevirtual getScheme : ()Ljava/lang/String;
    //   18: ifnonnull -> 24
    //   21: goto -> 2471
    //   24: aload_1
    //   25: invokevirtual getScheme : ()Ljava/lang/String;
    //   28: astore #6
    //   30: aload #6
    //   32: ldc 'package'
    //   34: invokevirtual equals : (Ljava/lang/Object;)Z
    //   37: ifne -> 55
    //   40: aload #6
    //   42: ldc 'file'
    //   44: invokevirtual equals : (Ljava/lang/Object;)Z
    //   47: ifne -> 55
    //   50: iconst_4
    //   51: invokestatic create : (I)Lcom/lody/virtual/remote/VAppInstallerResult;
    //   54: areturn
    //   55: aload #6
    //   57: ldc 'package'
    //   59: invokevirtual equals : (Ljava/lang/Object;)Z
    //   62: ifeq -> 77
    //   65: aload_1
    //   66: invokevirtual getSchemeSpecificPart : ()Ljava/lang/String;
    //   69: ifnonnull -> 77
    //   72: iconst_4
    //   73: invokestatic create : (I)Lcom/lody/virtual/remote/VAppInstallerResult;
    //   76: areturn
    //   77: aload #6
    //   79: ldc 'file'
    //   81: invokevirtual equals : (Ljava/lang/Object;)Z
    //   84: ifeq -> 99
    //   87: aload_1
    //   88: invokevirtual getPath : ()Ljava/lang/String;
    //   91: ifnonnull -> 99
    //   94: iconst_4
    //   95: invokestatic create : (I)Lcom/lody/virtual/remote/VAppInstallerResult;
    //   98: areturn
    //   99: aload_1
    //   100: invokevirtual getScheme : ()Ljava/lang/String;
    //   103: ldc 'package'
    //   105: invokevirtual equals : (Ljava/lang/Object;)Z
    //   108: ifeq -> 173
    //   111: aload_1
    //   112: invokevirtual getSchemeSpecificPart : ()Ljava/lang/String;
    //   115: astore #6
    //   117: invokestatic get : ()Lcom/lody/virtual/client/env/HostPackageManager;
    //   120: aload #6
    //   122: sipush #1024
    //   125: invokevirtual getApplicationInfo : (Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;
    //   128: astore_1
    //   129: goto -> 139
    //   132: astore_1
    //   133: aload_1
    //   134: invokevirtual printStackTrace : ()V
    //   137: aconst_null
    //   138: astore_1
    //   139: aload_1
    //   140: ifnonnull -> 151
    //   143: aload #6
    //   145: bipush #7
    //   147: invokestatic create : (Ljava/lang/String;I)Lcom/lody/virtual/remote/VAppInstallerResult;
    //   150: areturn
    //   151: new java/io/File
    //   154: dup
    //   155: aload_1
    //   156: getfield publicSourceDir : Ljava/lang/String;
    //   159: invokespecial <init> : (Ljava/lang/String;)V
    //   162: astore #7
    //   164: aload_1
    //   165: astore #6
    //   167: aload #7
    //   169: astore_1
    //   170: goto -> 188
    //   173: new java/io/File
    //   176: dup
    //   177: aload_1
    //   178: invokevirtual getPath : ()Ljava/lang/String;
    //   181: invokespecial <init> : (Ljava/lang/String;)V
    //   184: astore_1
    //   185: aconst_null
    //   186: astore #6
    //   188: aload_1
    //   189: invokevirtual exists : ()Z
    //   192: ifeq -> 2466
    //   195: aload_1
    //   196: invokevirtual isFile : ()Z
    //   199: ifne -> 205
    //   202: goto -> 2466
    //   205: aload_1
    //   206: iconst_0
    //   207: invokestatic parseApkLite : (Ljava/io/File;I)Landroid/content/pm/PackageParser$ApkLite;
    //   210: astore #7
    //   212: aload #7
    //   214: getfield splitName : Ljava/lang/String;
    //   217: ifnull -> 229
    //   220: aload_0
    //   221: aload_1
    //   222: aload #7
    //   224: aload_2
    //   225: invokespecial installSplitPackageInternal : (Ljava/io/File;Landroid/content/pm/PackageParser$ApkLite;Lcom/lody/virtual/remote/VAppInstallerParams;)Lcom/lody/virtual/remote/VAppInstallerResult;
    //   228: areturn
    //   229: aload_1
    //   230: invokestatic parsePackage : (Ljava/io/File;)Lcom/lody/virtual/server/pm/parser/VPackage;
    //   233: astore #8
    //   235: goto -> 248
    //   238: astore #9
    //   240: aload #9
    //   242: invokevirtual printStackTrace : ()V
    //   245: aconst_null
    //   246: astore #8
    //   248: aload #8
    //   250: ifnull -> 2454
    //   253: aload #8
    //   255: getfield packageName : Ljava/lang/String;
    //   258: ifnonnull -> 264
    //   261: goto -> 2454
    //   264: aload #8
    //   266: getfield packageName : Ljava/lang/String;
    //   269: invokestatic get : (Ljava/lang/String;)Lcom/lody/virtual/server/pm/parser/VPackage;
    //   272: astore #10
    //   274: aload #10
    //   276: ifnull -> 352
    //   279: iload #5
    //   281: iconst_4
    //   282: iand
    //   283: ifeq -> 296
    //   286: aload #8
    //   288: getfield packageName : Ljava/lang/String;
    //   291: iconst_3
    //   292: invokestatic create : (Ljava/lang/String;I)Lcom/lody/virtual/remote/VAppInstallerResult;
    //   295: areturn
    //   296: iload #5
    //   298: iconst_2
    //   299: iand
    //   300: ifne -> 326
    //   303: aload #10
    //   305: getfield mVersionCode : I
    //   308: aload #8
    //   310: getfield mVersionCode : I
    //   313: if_icmplt -> 326
    //   316: aload #8
    //   318: getfield packageName : Ljava/lang/String;
    //   321: iconst_5
    //   322: invokestatic create : (Ljava/lang/String;I)Lcom/lody/virtual/remote/VAppInstallerResult;
    //   325: areturn
    //   326: iload #5
    //   328: bipush #8
    //   330: iand
    //   331: ifne -> 346
    //   334: invokestatic get : ()Lcom/lody/virtual/server/am/VActivityManagerService;
    //   337: aload #8
    //   339: getfield packageName : Ljava/lang/String;
    //   342: iconst_m1
    //   343: invokevirtual killAppByPkg : (Ljava/lang/String;I)V
    //   346: iconst_2
    //   347: istore #11
    //   349: goto -> 355
    //   352: iconst_0
    //   353: istore #11
    //   355: new com/lody/virtual/remote/VAppInstallerResult
    //   358: dup
    //   359: invokespecial <init> : ()V
    //   362: astore #12
    //   364: aload #12
    //   366: aload #8
    //   368: getfield packageName : Ljava/lang/String;
    //   371: putfield packageName : Ljava/lang/String;
    //   374: aload #12
    //   376: iload #11
    //   378: putfield flags : I
    //   381: aload #8
    //   383: getfield packageName : Ljava/lang/String;
    //   386: invokestatic getDataAppPackageDirectory : (Ljava/lang/String;)Ljava/io/File;
    //   389: astore #9
    //   391: aload #9
    //   393: invokestatic ensureDirCreate : (Ljava/io/File;)Z
    //   396: ifne -> 444
    //   399: getstatic com/lody/virtual/server/pm/VAppManagerService.TAG : Ljava/lang/String;
    //   402: astore_1
    //   403: new java/lang/StringBuilder
    //   406: dup
    //   407: invokespecial <init> : ()V
    //   410: astore_2
    //   411: aload_2
    //   412: ldc_w 'failed to create app dir: '
    //   415: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   418: pop
    //   419: aload_2
    //   420: aload #9
    //   422: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   425: pop
    //   426: aload_1
    //   427: aload_2
    //   428: invokevirtual toString : ()Ljava/lang/String;
    //   431: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   434: aload #12
    //   436: bipush #6
    //   438: putfield flags : I
    //   441: aload #12
    //   443: areturn
    //   444: aload_2
    //   445: invokevirtual getCpuAbiOverride : ()Ljava/lang/String;
    //   448: astore #13
    //   450: new java/util/ArrayList
    //   453: dup
    //   454: invokespecial <init> : ()V
    //   457: astore #14
    //   459: aload #13
    //   461: ifnull -> 546
    //   464: aload #8
    //   466: getfield packageName : Ljava/lang/String;
    //   469: invokestatic getDataAppLibDirectory : (Ljava/lang/String;)Ljava/io/File;
    //   472: astore #7
    //   474: new java/io/File
    //   477: dup
    //   478: aload #7
    //   480: aload #13
    //   482: invokestatic getInstructionSet : (Ljava/lang/String;)Ljava/lang/String;
    //   485: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   488: astore #15
    //   490: aload #10
    //   492: astore #16
    //   494: aconst_null
    //   495: astore #17
    //   497: aconst_null
    //   498: astore #18
    //   500: aload_1
    //   501: astore #9
    //   503: aload #6
    //   505: astore #10
    //   507: aload #18
    //   509: astore_1
    //   510: aload #7
    //   512: astore #6
    //   514: aload #17
    //   516: astore #7
    //   518: iconst_1
    //   519: istore #19
    //   521: aload #7
    //   523: astore #17
    //   525: aload #6
    //   527: astore #20
    //   529: aload_1
    //   530: astore #18
    //   532: aload #15
    //   534: astore #7
    //   536: aload #10
    //   538: astore_1
    //   539: aload #9
    //   541: astore #6
    //   543: goto -> 1484
    //   546: aload #6
    //   548: ifnull -> 692
    //   551: getstatic mirror/android/content/pm/ApplicationInfoL.primaryCpuAbi : Lmirror/RefObject;
    //   554: aload #6
    //   556: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   559: checkcast java/lang/String
    //   562: astore #13
    //   564: getstatic mirror/android/content/pm/ApplicationInfoL.secondaryCpuAbi : Lmirror/RefObject;
    //   567: aload #6
    //   569: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   572: checkcast java/lang/String
    //   575: astore #18
    //   577: aload #6
    //   579: getfield nativeLibraryDir : Ljava/lang/String;
    //   582: astore #7
    //   584: aload #7
    //   586: ifnull -> 603
    //   589: new java/io/File
    //   592: dup
    //   593: aload #7
    //   595: invokespecial <init> : (Ljava/lang/String;)V
    //   598: astore #7
    //   600: goto -> 606
    //   603: aconst_null
    //   604: astore #7
    //   606: getstatic mirror/android/content/pm/ApplicationInfoL.secondaryNativeLibraryDir : Lmirror/RefObject;
    //   609: aload #6
    //   611: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   614: checkcast java/lang/String
    //   617: astore #9
    //   619: aload #9
    //   621: ifnull -> 638
    //   624: new java/io/File
    //   627: dup
    //   628: aload #9
    //   630: invokespecial <init> : (Ljava/lang/String;)V
    //   633: astore #9
    //   635: goto -> 641
    //   638: aconst_null
    //   639: astore #9
    //   641: new java/io/File
    //   644: dup
    //   645: getstatic mirror/android/content/pm/ApplicationInfoL.nativeLibraryRootDir : Lmirror/RefObject;
    //   648: aload #6
    //   650: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   653: checkcast java/lang/String
    //   656: invokespecial <init> : (Ljava/lang/String;)V
    //   659: astore #20
    //   661: getstatic mirror/android/content/pm/ApplicationInfoL.nativeLibraryRootRequiresIsa : Lmirror/RefBoolean;
    //   664: aload #6
    //   666: invokevirtual get : (Ljava/lang/Object;)Z
    //   669: istore #19
    //   671: aload #10
    //   673: astore #16
    //   675: aload_1
    //   676: astore #10
    //   678: aload #9
    //   680: astore #17
    //   682: aload #6
    //   684: astore_1
    //   685: aload #10
    //   687: astore #6
    //   689: goto -> 1484
    //   692: getstatic android/os/Build$VERSION.SDK_INT : I
    //   695: bipush #24
    //   697: if_icmplt -> 717
    //   700: aload #7
    //   702: getfield use32bitAbi : Z
    //   705: istore #19
    //   707: goto -> 720
    //   710: astore #7
    //   712: aload #7
    //   714: invokevirtual printStackTrace : ()V
    //   717: iconst_0
    //   718: istore #19
    //   720: aload_1
    //   721: invokevirtual getPath : ()Ljava/lang/String;
    //   724: invokestatic getPackageAbiList : (Ljava/lang/String;)Ljava/util/Set;
    //   727: astore #7
    //   729: getstatic android/os/Build.SUPPORTED_64_BIT_ABIS : [Ljava/lang/String;
    //   732: aload #7
    //   734: invokestatic findSupportedAbi : ([Ljava/lang/String;Ljava/util/Set;)Ljava/lang/String;
    //   737: astore #16
    //   739: getstatic android/os/Build.SUPPORTED_32_BIT_ABIS : [Ljava/lang/String;
    //   742: aload #7
    //   744: invokestatic findSupportedAbi : ([Ljava/lang/String;Ljava/util/Set;)Ljava/lang/String;
    //   747: astore #9
    //   749: getstatic android/os/Build.SUPPORTED_ABIS : [Ljava/lang/String;
    //   752: iconst_0
    //   753: aaload
    //   754: astore #7
    //   756: invokestatic get : ()Lcom/lody/virtual/client/core/VirtualCore;
    //   759: invokevirtual isExtPackageInstalled : ()Z
    //   762: ifne -> 773
    //   765: ldc_w 'armeabi-v7a'
    //   768: astore #7
    //   770: iconst_1
    //   771: istore #19
    //   773: aload #9
    //   775: ifnull -> 799
    //   778: iload #19
    //   780: ifne -> 788
    //   783: aload #16
    //   785: ifnonnull -> 799
    //   788: aload #9
    //   790: astore #7
    //   792: aload #16
    //   794: astore #9
    //   796: goto -> 824
    //   799: aload #16
    //   801: ifnull -> 821
    //   804: iload #19
    //   806: ifeq -> 814
    //   809: aload #9
    //   811: ifnonnull -> 821
    //   814: aload #16
    //   816: astore #7
    //   818: goto -> 824
    //   821: aconst_null
    //   822: astore #9
    //   824: new java/util/HashSet
    //   827: dup
    //   828: invokespecial <init> : ()V
    //   831: astore #15
    //   833: aload #8
    //   835: getfield usesLibraries : Ljava/util/ArrayList;
    //   838: ifnonnull -> 853
    //   841: aload #8
    //   843: new java/util/ArrayList
    //   846: dup
    //   847: invokespecial <init> : ()V
    //   850: putfield usesLibraries : Ljava/util/ArrayList;
    //   853: aload #8
    //   855: getfield usesOptionalLibraries : Ljava/util/ArrayList;
    //   858: ifnonnull -> 873
    //   861: aload #8
    //   863: new java/util/ArrayList
    //   866: dup
    //   867: invokespecial <init> : ()V
    //   870: putfield usesOptionalLibraries : Ljava/util/ArrayList;
    //   873: aload #8
    //   875: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   878: getfield targetSdkVersion : I
    //   881: bipush #23
    //   883: if_icmpge -> 923
    //   886: aload #8
    //   888: getfield usesLibraries : Ljava/util/ArrayList;
    //   891: ldc 'org.apache.http.legacy'
    //   893: invokevirtual contains : (Ljava/lang/Object;)Z
    //   896: ifne -> 923
    //   899: aload #8
    //   901: getfield usesOptionalLibraries : Ljava/util/ArrayList;
    //   904: ldc 'org.apache.http.legacy'
    //   906: invokevirtual contains : (Ljava/lang/Object;)Z
    //   909: ifne -> 923
    //   912: aload #8
    //   914: getfield usesLibraries : Ljava/util/ArrayList;
    //   917: ldc 'org.apache.http.legacy'
    //   919: invokevirtual add : (Ljava/lang/Object;)Z
    //   922: pop
    //   923: aload #8
    //   925: getfield usesLibraries : Ljava/util/ArrayList;
    //   928: ldc 'android.test.runner'
    //   930: invokevirtual contains : (Ljava/lang/Object;)Z
    //   933: ifne -> 958
    //   936: aload #8
    //   938: getfield usesOptionalLibraries : Ljava/util/ArrayList;
    //   941: ldc 'android.test.runner'
    //   943: invokevirtual contains : (Ljava/lang/Object;)Z
    //   946: ifeq -> 952
    //   949: goto -> 958
    //   952: iconst_0
    //   953: istore #21
    //   955: goto -> 961
    //   958: iconst_1
    //   959: istore #21
    //   961: iload #21
    //   963: ifne -> 985
    //   966: invokestatic isR : ()Z
    //   969: ifeq -> 1022
    //   972: aload #8
    //   974: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   977: getfield targetSdkVersion : I
    //   980: bipush #30
    //   982: if_icmpge -> 1022
    //   985: aload #8
    //   987: getfield usesLibraries : Ljava/util/ArrayList;
    //   990: ldc 'android.test.base'
    //   992: invokevirtual contains : (Ljava/lang/Object;)Z
    //   995: ifne -> 1022
    //   998: aload #8
    //   1000: getfield usesOptionalLibraries : Ljava/util/ArrayList;
    //   1003: ldc 'android.test.base'
    //   1005: invokevirtual contains : (Ljava/lang/Object;)Z
    //   1008: ifne -> 1022
    //   1011: aload #8
    //   1013: getfield usesLibraries : Ljava/util/ArrayList;
    //   1016: ldc 'android.test.base'
    //   1018: invokevirtual add : (Ljava/lang/Object;)Z
    //   1021: pop
    //   1022: aload #8
    //   1024: getfield usesOptionalLibraries : Ljava/util/ArrayList;
    //   1027: invokevirtual iterator : ()Ljava/util/Iterator;
    //   1030: astore #16
    //   1032: aload #16
    //   1034: invokeinterface hasNext : ()Z
    //   1039: istore #19
    //   1041: iload #19
    //   1043: ifeq -> 1186
    //   1046: aload #16
    //   1048: invokeinterface next : ()Ljava/lang/Object;
    //   1053: checkcast java/lang/String
    //   1056: astore #13
    //   1058: aload_0
    //   1059: getfield mSystemConfig : Lcom/lody/virtual/server/pm/SystemConfig;
    //   1062: aload #13
    //   1064: invokevirtual getSharedLibrary : (Ljava/lang/String;)Lcom/lody/virtual/server/pm/SystemConfig$SharedLibraryEntry;
    //   1067: astore #17
    //   1069: aload #17
    //   1071: ifnonnull -> 1118
    //   1074: getstatic com/lody/virtual/server/pm/VAppManagerService.TAG : Ljava/lang/String;
    //   1077: astore #17
    //   1079: new java/lang/StringBuilder
    //   1082: dup
    //   1083: invokespecial <init> : ()V
    //   1086: astore #18
    //   1088: aload #18
    //   1090: ldc_w 'skip optional shared library: '
    //   1093: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1096: pop
    //   1097: aload #18
    //   1099: aload #13
    //   1101: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1104: pop
    //   1105: aload #17
    //   1107: aload #18
    //   1109: invokevirtual toString : ()Ljava/lang/String;
    //   1112: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1115: goto -> 1032
    //   1118: aload #15
    //   1120: aload #17
    //   1122: getfield path : Ljava/lang/String;
    //   1125: invokeinterface add : (Ljava/lang/Object;)Z
    //   1130: pop
    //   1131: invokestatic isS : ()Z
    //   1134: ifeq -> 1183
    //   1137: aload #14
    //   1139: new android/content/pm/SharedLibraryInfo
    //   1142: dup
    //   1143: aload #17
    //   1145: getfield path : Ljava/lang/String;
    //   1148: aconst_null
    //   1149: aconst_null
    //   1150: aload #17
    //   1152: getfield name : Ljava/lang/String;
    //   1155: ldc2_w -1
    //   1158: iconst_0
    //   1159: new android/content/pm/VersionedPackage
    //   1162: dup
    //   1163: ldc_w 'android'
    //   1166: lconst_0
    //   1167: invokespecial <init> : (Ljava/lang/String;J)V
    //   1170: aconst_null
    //   1171: aconst_null
    //   1172: iconst_0
    //   1173: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;JILandroid/content/pm/VersionedPackage;Ljava/util/List;Ljava/util/List;Z)V
    //   1176: invokevirtual add : (Ljava/lang/Object;)Z
    //   1179: pop
    //   1180: goto -> 1183
    //   1183: goto -> 1032
    //   1186: aload #6
    //   1188: astore #17
    //   1190: aload_1
    //   1191: astore #18
    //   1193: aload #8
    //   1195: getfield usesLibraries : Ljava/util/ArrayList;
    //   1198: invokevirtual iterator : ()Ljava/util/Iterator;
    //   1201: astore_1
    //   1202: aload_1
    //   1203: invokeinterface hasNext : ()Z
    //   1208: ifeq -> 1350
    //   1211: aload_1
    //   1212: invokeinterface next : ()Ljava/lang/Object;
    //   1217: checkcast java/lang/String
    //   1220: astore #6
    //   1222: aload_0
    //   1223: getfield mSystemConfig : Lcom/lody/virtual/server/pm/SystemConfig;
    //   1226: aload #6
    //   1228: invokevirtual getSharedLibrary : (Ljava/lang/String;)Lcom/lody/virtual/server/pm/SystemConfig$SharedLibraryEntry;
    //   1231: astore #16
    //   1233: aload #16
    //   1235: ifnonnull -> 1282
    //   1238: getstatic com/lody/virtual/server/pm/VAppManagerService.TAG : Ljava/lang/String;
    //   1241: astore #16
    //   1243: new java/lang/StringBuilder
    //   1246: dup
    //   1247: invokespecial <init> : ()V
    //   1250: astore #13
    //   1252: aload #13
    //   1254: ldc_w 'skip required shared library: '
    //   1257: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1260: pop
    //   1261: aload #13
    //   1263: aload #6
    //   1265: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1268: pop
    //   1269: aload #16
    //   1271: aload #13
    //   1273: invokevirtual toString : ()Ljava/lang/String;
    //   1276: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1279: goto -> 1202
    //   1282: aload #15
    //   1284: aload #16
    //   1286: getfield path : Ljava/lang/String;
    //   1289: invokeinterface add : (Ljava/lang/Object;)Z
    //   1294: pop
    //   1295: invokestatic isS : ()Z
    //   1298: ifeq -> 1347
    //   1301: aload #14
    //   1303: new android/content/pm/SharedLibraryInfo
    //   1306: dup
    //   1307: aload #16
    //   1309: getfield path : Ljava/lang/String;
    //   1312: aconst_null
    //   1313: aconst_null
    //   1314: aload #16
    //   1316: getfield name : Ljava/lang/String;
    //   1319: ldc2_w -1
    //   1322: iconst_0
    //   1323: new android/content/pm/VersionedPackage
    //   1326: dup
    //   1327: ldc_w 'android'
    //   1330: lconst_0
    //   1331: invokespecial <init> : (Ljava/lang/String;J)V
    //   1334: aconst_null
    //   1335: aconst_null
    //   1336: iconst_0
    //   1337: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;JILandroid/content/pm/VersionedPackage;Ljava/util/List;Ljava/util/List;Z)V
    //   1340: invokevirtual add : (Ljava/lang/Object;)Z
    //   1343: pop
    //   1344: goto -> 1347
    //   1347: goto -> 1202
    //   1350: aload #15
    //   1352: invokeinterface isEmpty : ()Z
    //   1357: ifne -> 1382
    //   1360: aload #8
    //   1362: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1365: aload #15
    //   1367: iconst_0
    //   1368: anewarray java/lang/String
    //   1371: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   1376: checkcast [Ljava/lang/String;
    //   1379: putfield sharedLibraryFiles : [Ljava/lang/String;
    //   1382: aload #8
    //   1384: getfield packageName : Ljava/lang/String;
    //   1387: invokestatic getDataAppLibDirectory : (Ljava/lang/String;)Ljava/io/File;
    //   1390: astore #6
    //   1392: new java/io/File
    //   1395: dup
    //   1396: aload #6
    //   1398: aload #7
    //   1400: invokestatic getInstructionSet : (Ljava/lang/String;)Ljava/lang/String;
    //   1403: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   1406: astore #15
    //   1408: aload #9
    //   1410: ifnull -> 1455
    //   1413: new java/io/File
    //   1416: dup
    //   1417: aload #6
    //   1419: aload #9
    //   1421: invokestatic getInstructionSet : (Ljava/lang/String;)Ljava/lang/String;
    //   1424: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   1427: astore #16
    //   1429: aload #9
    //   1431: astore_1
    //   1432: aload #7
    //   1434: astore #13
    //   1436: aload #16
    //   1438: astore #7
    //   1440: aload #10
    //   1442: astore #16
    //   1444: aload #17
    //   1446: astore #10
    //   1448: aload #18
    //   1450: astore #9
    //   1452: goto -> 518
    //   1455: aload #9
    //   1457: astore_1
    //   1458: aconst_null
    //   1459: astore #9
    //   1461: aload #7
    //   1463: astore #13
    //   1465: aload #9
    //   1467: astore #7
    //   1469: aload #10
    //   1471: astore #16
    //   1473: aload #17
    //   1475: astore #10
    //   1477: aload #18
    //   1479: astore #9
    //   1481: goto -> 518
    //   1484: getstatic mirror/android/content/pm/ApplicationInfoL.primaryCpuAbi : Lmirror/RefObject;
    //   1487: aload #8
    //   1489: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1492: aload #13
    //   1494: invokevirtual set : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   1497: pop
    //   1498: getstatic mirror/android/content/pm/ApplicationInfoL.secondaryCpuAbi : Lmirror/RefObject;
    //   1501: aload #8
    //   1503: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1506: aload #18
    //   1508: invokevirtual set : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   1511: pop
    //   1512: aload #20
    //   1514: ifnull -> 1534
    //   1517: getstatic mirror/android/content/pm/ApplicationInfoL.nativeLibraryRootDir : Lmirror/RefObject;
    //   1520: aload #8
    //   1522: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1525: aload #20
    //   1527: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   1530: invokevirtual set : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   1533: pop
    //   1534: getstatic mirror/android/content/pm/ApplicationInfoL.nativeLibraryRootRequiresIsa : Lmirror/RefBoolean;
    //   1537: aload #8
    //   1539: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1542: iload #19
    //   1544: invokevirtual set : (Ljava/lang/Object;Z)V
    //   1547: aload #7
    //   1549: ifnull -> 1565
    //   1552: aload #8
    //   1554: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1557: aload #7
    //   1559: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   1562: putfield nativeLibraryDir : Ljava/lang/String;
    //   1565: aload #17
    //   1567: ifnull -> 1587
    //   1570: getstatic mirror/android/content/pm/ApplicationInfoL.secondaryNativeLibraryDir : Lmirror/RefObject;
    //   1573: aload #8
    //   1575: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1578: aload #17
    //   1580: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   1583: invokevirtual set : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   1586: pop
    //   1587: aload_1
    //   1588: ifnull -> 1710
    //   1591: aload #8
    //   1593: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1596: astore #10
    //   1598: aload_1
    //   1599: astore #9
    //   1601: aload #10
    //   1603: aload #9
    //   1605: getfield publicSourceDir : Ljava/lang/String;
    //   1608: putfield publicSourceDir : Ljava/lang/String;
    //   1611: aload #8
    //   1613: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1616: aload #9
    //   1618: getfield sourceDir : Ljava/lang/String;
    //   1621: putfield sourceDir : Ljava/lang/String;
    //   1624: aload #8
    //   1626: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1629: aload #9
    //   1631: getfield sharedLibraryFiles : [Ljava/lang/String;
    //   1634: putfield sharedLibraryFiles : [Ljava/lang/String;
    //   1637: getstatic android/os/Build$VERSION.SDK_INT : I
    //   1640: bipush #26
    //   1642: if_icmplt -> 1658
    //   1645: aload #8
    //   1647: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1650: aload #9
    //   1652: getfield splitNames : [Ljava/lang/String;
    //   1655: putfield splitNames : [Ljava/lang/String;
    //   1658: aload #8
    //   1660: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1663: aload #9
    //   1665: getfield splitSourceDirs : [Ljava/lang/String;
    //   1668: putfield splitSourceDirs : [Ljava/lang/String;
    //   1671: aload #8
    //   1673: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1676: aload #9
    //   1678: getfield splitPublicSourceDirs : [Ljava/lang/String;
    //   1681: putfield splitPublicSourceDirs : [Ljava/lang/String;
    //   1684: getstatic mirror/android/content/pm/ApplicationInfoP.sharedLibraryInfos : Lmirror/RefObject;
    //   1687: ifnull -> 1798
    //   1690: aload #9
    //   1692: invokestatic sharedLibraryInfos : (Landroid/content/pm/ApplicationInfo;)Ljava/util/List;
    //   1695: astore #9
    //   1697: aload #8
    //   1699: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1702: aload #9
    //   1704: invokestatic sharedLibraryInfos : (Landroid/content/pm/ApplicationInfo;Ljava/util/List;)V
    //   1707: goto -> 1798
    //   1710: invokestatic getConfig : ()Lcom/lody/virtual/client/core/SettingConfig;
    //   1713: astore #9
    //   1715: aload #9
    //   1717: invokevirtual isEnableIORedirect : ()Z
    //   1720: ifeq -> 1749
    //   1723: aload #9
    //   1725: aload #8
    //   1727: getfield packageName : Ljava/lang/String;
    //   1730: invokevirtual isUseRealApkPath : (Ljava/lang/String;)Z
    //   1733: ifeq -> 1749
    //   1736: aload #8
    //   1738: getfield packageName : Ljava/lang/String;
    //   1741: invokestatic getPackageFileStub : (Ljava/lang/String;)Ljava/lang/String;
    //   1744: astore #9
    //   1746: goto -> 1762
    //   1749: aload #8
    //   1751: getfield packageName : Ljava/lang/String;
    //   1754: invokestatic getPackageFile : (Ljava/lang/String;)Ljava/io/File;
    //   1757: invokevirtual getPath : ()Ljava/lang/String;
    //   1760: astore #9
    //   1762: aload #8
    //   1764: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1767: aload #9
    //   1769: putfield publicSourceDir : Ljava/lang/String;
    //   1772: aload #8
    //   1774: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1777: aload #9
    //   1779: putfield sourceDir : Ljava/lang/String;
    //   1782: getstatic mirror/android/content/pm/ApplicationInfoP.sharedLibraryInfos : Lmirror/RefObject;
    //   1785: ifnull -> 1798
    //   1788: aload #8
    //   1790: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1793: aload #14
    //   1795: invokestatic sharedLibraryInfos : (Landroid/content/pm/ApplicationInfo;Ljava/util/List;)V
    //   1798: aload_1
    //   1799: astore #9
    //   1801: aload #8
    //   1803: getfield packageName : Ljava/lang/String;
    //   1806: invokestatic getDataAppPackageDirectory : (Ljava/lang/String;)Ljava/io/File;
    //   1809: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   1812: astore_1
    //   1813: getstatic mirror/android/content/pm/ApplicationInfoL.scanSourceDir : Lmirror/RefObject;
    //   1816: aload #8
    //   1818: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1821: aload_1
    //   1822: invokevirtual set : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   1825: pop
    //   1826: getstatic mirror/android/content/pm/ApplicationInfoL.scanPublicSourceDir : Lmirror/RefObject;
    //   1829: aload #8
    //   1831: getfield applicationInfo : Landroid/content/pm/ApplicationInfo;
    //   1834: aload_1
    //   1835: invokevirtual set : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   1838: pop
    //   1839: new com/lody/virtual/helper/compat/NativeLibraryHelperCompat
    //   1842: dup
    //   1843: aload #6
    //   1845: invokespecial <init> : (Ljava/io/File;)V
    //   1848: astore_1
    //   1849: aload #9
    //   1851: ifnonnull -> 2028
    //   1854: aload #20
    //   1856: invokestatic ensureDirCreate : (Ljava/io/File;)Z
    //   1859: ifne -> 1903
    //   1862: getstatic com/lody/virtual/server/pm/VAppManagerService.TAG : Ljava/lang/String;
    //   1865: astore #15
    //   1867: new java/lang/StringBuilder
    //   1870: dup
    //   1871: invokespecial <init> : ()V
    //   1874: astore #10
    //   1876: aload #10
    //   1878: ldc_w 'failed to create native lib root dir: '
    //   1881: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1884: pop
    //   1885: aload #10
    //   1887: aload #20
    //   1889: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1892: pop
    //   1893: aload #15
    //   1895: aload #10
    //   1897: invokevirtual toString : ()Ljava/lang/String;
    //   1900: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1903: aload #7
    //   1905: invokestatic ensureDirCreate : (Ljava/io/File;)Z
    //   1908: ifeq -> 1923
    //   1911: aload_1
    //   1912: aload #7
    //   1914: aload #13
    //   1916: invokevirtual copyNativeBinaries : (Ljava/io/File;Ljava/lang/String;)I
    //   1919: pop
    //   1920: goto -> 1964
    //   1923: getstatic com/lody/virtual/server/pm/VAppManagerService.TAG : Ljava/lang/String;
    //   1926: astore #15
    //   1928: new java/lang/StringBuilder
    //   1931: dup
    //   1932: invokespecial <init> : ()V
    //   1935: astore #10
    //   1937: aload #10
    //   1939: ldc_w 'failed to create native lib dir: '
    //   1942: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1945: pop
    //   1946: aload #10
    //   1948: aload #7
    //   1950: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1953: pop
    //   1954: aload #15
    //   1956: aload #10
    //   1958: invokevirtual toString : ()Ljava/lang/String;
    //   1961: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1964: aload #18
    //   1966: ifnull -> 2028
    //   1969: aload #17
    //   1971: invokestatic ensureDirCreate : (Ljava/io/File;)Z
    //   1974: ifeq -> 1989
    //   1977: aload_1
    //   1978: aload #17
    //   1980: aload #18
    //   1982: invokevirtual copyNativeBinaries : (Ljava/io/File;Ljava/lang/String;)I
    //   1985: pop
    //   1986: goto -> 2028
    //   1989: getstatic com/lody/virtual/server/pm/VAppManagerService.TAG : Ljava/lang/String;
    //   1992: astore_1
    //   1993: new java/lang/StringBuilder
    //   1996: dup
    //   1997: invokespecial <init> : ()V
    //   2000: astore #7
    //   2002: aload #7
    //   2004: ldc_w 'failed to create secondary native lib dir: '
    //   2007: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2010: pop
    //   2011: aload #7
    //   2013: aload #17
    //   2015: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2018: pop
    //   2019: aload_1
    //   2020: aload #7
    //   2022: invokevirtual toString : ()Ljava/lang/String;
    //   2025: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   2028: aload #16
    //   2030: ifnull -> 2045
    //   2033: aload #16
    //   2035: getfield mExtras : Ljava/lang/Object;
    //   2038: checkcast com/lody/virtual/server/pm/PackageSetting
    //   2041: astore_1
    //   2042: goto -> 2053
    //   2045: new com/lody/virtual/server/pm/PackageSetting
    //   2048: dup
    //   2049: invokespecial <init> : ()V
    //   2052: astore_1
    //   2053: aload_1
    //   2054: aload #13
    //   2056: putfield primaryCpuAbi : Ljava/lang/String;
    //   2059: aload_1
    //   2060: aload #18
    //   2062: putfield secondaryCpuAbi : Ljava/lang/String;
    //   2065: aload_1
    //   2066: aload_1
    //   2067: getfield primaryCpuAbi : Ljava/lang/String;
    //   2070: invokestatic is64bitAbi : (Ljava/lang/String;)Z
    //   2073: putfield is64bitPackage : Z
    //   2076: aload #9
    //   2078: ifnonnull -> 2193
    //   2081: aload #8
    //   2083: getfield packageName : Ljava/lang/String;
    //   2086: invokestatic getPackageFile : (Ljava/lang/String;)Ljava/io/File;
    //   2089: astore #7
    //   2091: aload #6
    //   2093: aload #7
    //   2095: invokestatic copyFile : (Ljava/io/File;Ljava/io/File;)V
    //   2098: iconst_1
    //   2099: istore #21
    //   2101: goto -> 2155
    //   2104: astore #16
    //   2106: getstatic com/lody/virtual/server/pm/VAppManagerService.TAG : Ljava/lang/String;
    //   2109: astore #10
    //   2111: new java/lang/StringBuilder
    //   2114: dup
    //   2115: invokespecial <init> : ()V
    //   2118: astore #6
    //   2120: aload #6
    //   2122: ldc_w 'failed to copy file: '
    //   2125: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2128: pop
    //   2129: aload #6
    //   2131: aload #7
    //   2133: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2136: pop
    //   2137: aload #10
    //   2139: aload #6
    //   2141: invokevirtual toString : ()Ljava/lang/String;
    //   2144: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   2147: aload #16
    //   2149: invokevirtual printStackTrace : ()V
    //   2152: iconst_0
    //   2153: istore #21
    //   2155: iload #21
    //   2157: ifne -> 2181
    //   2160: aload #8
    //   2162: getfield packageName : Ljava/lang/String;
    //   2165: invokestatic getDataAppPackageDirectory : (Ljava/lang/String;)Ljava/io/File;
    //   2168: invokestatic deleteDir : (Ljava/io/File;)V
    //   2171: aload #12
    //   2173: bipush #6
    //   2175: putfield status : I
    //   2178: aload #12
    //   2180: areturn
    //   2181: aload #7
    //   2183: invokestatic chmodPackageDictionary : (Ljava/io/File;)V
    //   2186: aload #7
    //   2188: astore #6
    //   2190: goto -> 2193
    //   2193: aload #9
    //   2195: ifnull -> 2204
    //   2198: iconst_1
    //   2199: istore #19
    //   2201: goto -> 2207
    //   2204: iconst_0
    //   2205: istore #19
    //   2207: aload_1
    //   2208: iload #19
    //   2210: putfield dynamic : Z
    //   2213: aload_1
    //   2214: aload #8
    //   2216: getfield packageName : Ljava/lang/String;
    //   2219: putfield packageName : Ljava/lang/String;
    //   2222: aload_1
    //   2223: aload_0
    //   2224: getfield mUidSystem : Lcom/lody/virtual/server/am/UidSystem;
    //   2227: aload #8
    //   2229: invokevirtual getOrCreateUid : (Lcom/lody/virtual/server/pm/parser/VPackage;)I
    //   2232: invokestatic getAppId : (I)I
    //   2235: putfield appId : I
    //   2238: iload #11
    //   2240: iconst_2
    //   2241: iand
    //   2242: ifeq -> 2253
    //   2245: aload_1
    //   2246: lload_3
    //   2247: putfield lastUpdateTime : J
    //   2250: goto -> 2323
    //   2253: aload_1
    //   2254: lload_3
    //   2255: putfield firstInstallTime : J
    //   2258: aload_1
    //   2259: lload_3
    //   2260: putfield lastUpdateTime : J
    //   2263: invokestatic get : ()Lcom/lody/virtual/server/pm/VUserManagerService;
    //   2266: invokevirtual getUserIds : ()[I
    //   2269: astore #7
    //   2271: aload #7
    //   2273: arraylength
    //   2274: istore #21
    //   2276: iconst_0
    //   2277: istore #11
    //   2279: iload #11
    //   2281: iload #21
    //   2283: if_icmpge -> 2323
    //   2286: aload #7
    //   2288: iload #11
    //   2290: iaload
    //   2291: istore #22
    //   2293: iload #22
    //   2295: ifne -> 2304
    //   2298: iconst_1
    //   2299: istore #19
    //   2301: goto -> 2307
    //   2304: iconst_0
    //   2305: istore #19
    //   2307: aload_1
    //   2308: iload #22
    //   2310: iconst_0
    //   2311: iconst_0
    //   2312: iload #19
    //   2314: invokevirtual setUserState : (IZZZ)V
    //   2317: iinc #11, 1
    //   2320: goto -> 2279
    //   2323: aload #8
    //   2325: invokestatic savePackageCache : (Lcom/lody/virtual/server/pm/parser/VPackage;)V
    //   2328: aload #8
    //   2330: aload_1
    //   2331: invokestatic put : (Lcom/lody/virtual/server/pm/parser/VPackage;Lcom/lody/virtual/server/pm/PackageSetting;)V
    //   2334: aload_0
    //   2335: getfield mScanning : Z
    //   2338: ifne -> 2348
    //   2341: aload_0
    //   2342: getfield mPersistenceLayer : Lcom/lody/virtual/server/pm/PackagePersistenceLayer;
    //   2345: invokevirtual save : ()V
    //   2348: aload #9
    //   2350: ifnonnull -> 2415
    //   2353: aload_1
    //   2354: invokevirtual isRunInExtProcess : ()Z
    //   2357: ifne -> 2415
    //   2360: invokestatic getCurrentInstructionSet : ()Ljava/lang/String;
    //   2363: astore #7
    //   2365: aload_1
    //   2366: getfield packageName : Ljava/lang/String;
    //   2369: aload #7
    //   2371: invokestatic getOatFile : (Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;
    //   2374: astore #7
    //   2376: aload #7
    //   2378: invokevirtual exists : ()Z
    //   2381: ifeq -> 2394
    //   2384: aload_2
    //   2385: invokevirtual getInstallFlags : ()I
    //   2388: bipush #16
    //   2390: iand
    //   2391: ifne -> 2415
    //   2394: aload #6
    //   2396: invokevirtual getPath : ()Ljava/lang/String;
    //   2399: aload #7
    //   2401: invokevirtual getPath : ()Ljava/lang/String;
    //   2404: invokestatic dex2oat : (Ljava/lang/String;Ljava/lang/String;)V
    //   2407: goto -> 2415
    //   2410: astore_2
    //   2411: aload_2
    //   2412: invokevirtual printStackTrace : ()V
    //   2415: invokestatic get : ()Lcom/lody/virtual/server/accounts/VAccountManagerService;
    //   2418: aconst_null
    //   2419: invokevirtual refreshAuthenticatorCache : (Ljava/lang/String;)V
    //   2422: aload_0
    //   2423: getfield mScanning : Z
    //   2426: ifne -> 2432
    //   2429: invokestatic syncPackages : ()V
    //   2432: iload #5
    //   2434: iconst_1
    //   2435: iand
    //   2436: ifeq -> 2445
    //   2439: aload_0
    //   2440: aload_1
    //   2441: iconst_m1
    //   2442: invokespecial notifyAppInstalled : (Lcom/lody/virtual/server/pm/PackageSetting;I)V
    //   2445: aload #12
    //   2447: iconst_0
    //   2448: putfield status : I
    //   2451: aload #12
    //   2453: areturn
    //   2454: bipush #7
    //   2456: invokestatic create : (I)Lcom/lody/virtual/remote/VAppInstallerResult;
    //   2459: areturn
    //   2460: astore_1
    //   2461: iconst_4
    //   2462: invokestatic create : (I)Lcom/lody/virtual/remote/VAppInstallerResult;
    //   2465: areturn
    //   2466: iconst_4
    //   2467: invokestatic create : (I)Lcom/lody/virtual/remote/VAppInstallerResult;
    //   2470: areturn
    //   2471: iconst_4
    //   2472: invokestatic create : (I)Lcom/lody/virtual/remote/VAppInstallerResult;
    //   2475: areturn
    // Exception table:
    //   from	to	target	type
    //   117	129	132	android/content/pm/PackageManager$NameNotFoundException
    //   205	212	2460	android/content/pm/PackageParser$PackageParserException
    //   229	235	238	finally
    //   700	707	710	finally
    //   2091	2098	2104	java/io/IOException
    //   2394	2407	2410	finally
  }
  
  private VAppInstallerResult installSplitPackageInternal(File paramFile, PackageParser.ApkLite paramApkLite, VAppInstallerParams paramVAppInstallerParams) {
    // Byte code:
    //   0: aload_2
    //   1: getfield packageName : Ljava/lang/String;
    //   4: invokestatic get : (Ljava/lang/String;)Lcom/lody/virtual/server/pm/parser/VPackage;
    //   7: astore #4
    //   9: aload #4
    //   11: ifnonnull -> 24
    //   14: aload_2
    //   15: getfield packageName : Ljava/lang/String;
    //   18: bipush #8
    //   20: invokestatic create : (Ljava/lang/String;I)Lcom/lody/virtual/remote/VAppInstallerResult;
    //   23: areturn
    //   24: aload #4
    //   26: getfield mExtras : Ljava/lang/Object;
    //   29: checkcast com/lody/virtual/server/pm/PackageSetting
    //   32: astore #5
    //   34: aload #4
    //   36: getfield splitNames : Ljava/util/ArrayList;
    //   39: ifnonnull -> 57
    //   42: aload #4
    //   44: new java/util/ArrayList
    //   47: dup
    //   48: invokespecial <init> : ()V
    //   51: putfield splitNames : Ljava/util/ArrayList;
    //   54: goto -> 109
    //   57: aload #4
    //   59: getfield splitNames : Ljava/util/ArrayList;
    //   62: aload_2
    //   63: getfield splitName : Ljava/lang/String;
    //   66: invokevirtual contains : (Ljava/lang/Object;)Z
    //   69: ifeq -> 109
    //   72: aload_3
    //   73: invokevirtual getInstallFlags : ()I
    //   76: iconst_2
    //   77: iand
    //   78: ifne -> 90
    //   81: aload_2
    //   82: getfield packageName : Ljava/lang/String;
    //   85: iconst_5
    //   86: invokestatic create : (Ljava/lang/String;I)Lcom/lody/virtual/remote/VAppInstallerResult;
    //   89: areturn
    //   90: aload #4
    //   92: getfield splitNames : Ljava/util/ArrayList;
    //   95: aload_2
    //   96: getfield splitName : Ljava/lang/String;
    //   99: invokevirtual remove : (Ljava/lang/Object;)Z
    //   102: pop
    //   103: iconst_3
    //   104: istore #6
    //   106: goto -> 112
    //   109: iconst_1
    //   110: istore #6
    //   112: aload_3
    //   113: invokevirtual getInstallFlags : ()I
    //   116: bipush #8
    //   118: iand
    //   119: ifne -> 133
    //   122: invokestatic get : ()Lcom/lody/virtual/server/am/VActivityManagerService;
    //   125: aload_2
    //   126: getfield packageName : Ljava/lang/String;
    //   129: iconst_m1
    //   130: invokevirtual killAppByPkg : (Ljava/lang/String;I)V
    //   133: aload #4
    //   135: getfield splitNames : Ljava/util/ArrayList;
    //   138: aload_2
    //   139: getfield splitName : Ljava/lang/String;
    //   142: invokevirtual add : (Ljava/lang/Object;)Z
    //   145: pop
    //   146: aload_2
    //   147: getfield packageName : Ljava/lang/String;
    //   150: aload_2
    //   151: getfield splitName : Ljava/lang/String;
    //   154: invokestatic getSplitPackageFile : (Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;
    //   157: astore_3
    //   158: aload_1
    //   159: aload_3
    //   160: invokestatic copyFile : (Ljava/io/File;Ljava/io/File;)V
    //   163: goto -> 171
    //   166: astore_3
    //   167: aload_3
    //   168: invokevirtual printStackTrace : ()V
    //   171: aload_1
    //   172: invokevirtual getPath : ()Ljava/lang/String;
    //   175: invokestatic getPackageAbiList : (Ljava/lang/String;)Ljava/util/Set;
    //   178: iconst_0
    //   179: anewarray java/lang/String
    //   182: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   187: checkcast [Ljava/lang/String;
    //   190: astore_3
    //   191: aload_3
    //   192: arraylength
    //   193: ifle -> 304
    //   196: aload_3
    //   197: iconst_0
    //   198: aaload
    //   199: astore #7
    //   201: new java/io/File
    //   204: dup
    //   205: aload #4
    //   207: getfield packageName : Ljava/lang/String;
    //   210: invokestatic getDataAppLibDirectory : (Ljava/lang/String;)Ljava/io/File;
    //   213: aload #7
    //   215: invokestatic getInstructionSet : (Ljava/lang/String;)Ljava/lang/String;
    //   218: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   221: astore_3
    //   222: aload #5
    //   224: aload #7
    //   226: putfield primaryCpuAbi : Ljava/lang/String;
    //   229: aload #5
    //   231: aload #7
    //   233: invokestatic is64bitAbi : (Ljava/lang/String;)Z
    //   236: putfield is64bitPackage : Z
    //   239: new com/lody/virtual/helper/compat/NativeLibraryHelperCompat
    //   242: dup
    //   243: aload_1
    //   244: invokespecial <init> : (Ljava/io/File;)V
    //   247: astore_1
    //   248: aload_3
    //   249: invokestatic ensureDirCreate : (Ljava/io/File;)Z
    //   252: ifeq -> 266
    //   255: aload_1
    //   256: aload_3
    //   257: aload #7
    //   259: invokevirtual copyNativeBinaries : (Ljava/io/File;Ljava/lang/String;)I
    //   262: pop
    //   263: goto -> 304
    //   266: getstatic com/lody/virtual/server/pm/VAppManagerService.TAG : Ljava/lang/String;
    //   269: astore_1
    //   270: new java/lang/StringBuilder
    //   273: dup
    //   274: invokespecial <init> : ()V
    //   277: astore #5
    //   279: aload #5
    //   281: ldc_w 'failed to create native lib dir: '
    //   284: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   287: pop
    //   288: aload #5
    //   290: aload_3
    //   291: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   294: pop
    //   295: aload_1
    //   296: aload #5
    //   298: invokevirtual toString : ()Ljava/lang/String;
    //   301: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   304: aload #4
    //   306: invokestatic savePackageCache : (Lcom/lody/virtual/server/pm/parser/VPackage;)V
    //   309: aload_0
    //   310: getfield mScanning : Z
    //   313: ifne -> 319
    //   316: invokestatic syncPackages : ()V
    //   319: new com/lody/virtual/remote/VAppInstallerResult
    //   322: dup
    //   323: aload_2
    //   324: getfield packageName : Ljava/lang/String;
    //   327: iconst_0
    //   328: iload #6
    //   330: invokespecial <init> : (Ljava/lang/String;II)V
    //   333: areturn
    // Exception table:
    //   from	to	target	type
    //   158	163	166	java/io/IOException
  }
  
  private boolean loadPackageInnerLocked(PackageSetting paramPackageSetting) {
    boolean bool = paramPackageSetting.dynamic;
    if (bool && !VirtualCore.get().isOutsideInstalled(paramPackageSetting.packageName))
      return false; 
    File file = VEnvironment.getPackageCacheFile(paramPackageSetting.packageName);
    VPackage vPackage = null;
    try {
      VPackage vPackage1 = PackageParserEx.readPackageCache(paramPackageSetting.packageName);
    } finally {
      Exception exception = null;
    } 
    if (vPackage == null || vPackage.packageName == null)
      return false; 
    VEnvironment.chmodPackageDictionary(file);
    PackageCacheManager.put(vPackage, paramPackageSetting);
    if (bool)
      try {
        boolean bool1;
        PackageInfo packageInfo = VirtualCore.get().getHostPackageManager().getPackageInfo(paramPackageSetting.packageName, 0);
        if (vPackage.mVersionCode != packageInfo.versionCode) {
          bool1 = true;
        } else {
          bool1 = false;
        } 
        File file1 = new File();
        this(vPackage.applicationInfo.publicSourceDir);
        bool = file1.exists();
        if (bool1 || (bool ^ true) != 0) {
          String str = TAG;
          StringBuilder stringBuilder2 = new StringBuilder();
          this();
          stringBuilder2.append("app (");
          stringBuilder2.append(paramPackageSetting.packageName);
          stringBuilder2.append(") has changed version, update it.");
          VLog.d(str, stringBuilder2.toString(), new Object[0]);
          VAppInstallerParams vAppInstallerParams = new VAppInstallerParams();
          this(10, 1);
          StringBuilder stringBuilder1 = new StringBuilder();
          this();
          stringBuilder1.append("package:");
          stringBuilder1.append(paramPackageSetting.packageName);
          installPackageInternal(Uri.parse(stringBuilder1.toString()), vAppInstallerParams);
        } 
      } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
        nameNotFoundException.printStackTrace();
        return false;
      }  
    return true;
  }
  
  private void notifyAppInstalled(PackageSetting paramPackageSetting, int paramInt) {
    String str = paramPackageSetting.packageName;
    int i = this.mRemoteCallbackList.beginBroadcast();
    while (true) {
      int j = i - 1;
      if (i > 0) {
        if (paramInt == -1) {
          try {
            ((IPackageObserver)this.mRemoteCallbackList.getBroadcastItem(j)).onPackageInstalled(str);
            ((IPackageObserver)this.mRemoteCallbackList.getBroadcastItem(j)).onPackageInstalledAsUser(0, str);
          } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
          } 
        } else {
          ((IPackageObserver)this.mRemoteCallbackList.getBroadcastItem(j)).onPackageInstalledAsUser(paramInt, str);
        } 
        i = j;
        continue;
      } 
      sendInstalledBroadcast(str, new VUserHandle(paramInt));
      this.mRemoteCallbackList.finishBroadcast();
      return;
    } 
  }
  
  private void notifyAppUninstalled(PackageSetting paramPackageSetting, int paramInt) {
    String str = paramPackageSetting.packageName;
    int i = this.mRemoteCallbackList.beginBroadcast();
    while (true) {
      int j = i - 1;
      if (i > 0) {
        if (paramInt == -1) {
          try {
            ((IPackageObserver)this.mRemoteCallbackList.getBroadcastItem(j)).onPackageUninstalled(str);
            ((IPackageObserver)this.mRemoteCallbackList.getBroadcastItem(j)).onPackageUninstalledAsUser(0, str);
          } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
          } 
        } else {
          ((IPackageObserver)this.mRemoteCallbackList.getBroadcastItem(j)).onPackageUninstalledAsUser(paramInt, str);
        } 
        i = j;
        continue;
      } 
      sendUninstalledBroadcast(str, new VUserHandle(paramInt));
      this.mRemoteCallbackList.finishBroadcast();
      return;
    } 
  }
  
  private void sendInstalledBroadcast(String paramString, VUserHandle paramVUserHandle) {
    Intent intent = new Intent("android.intent.action.PACKAGE_ADDED");
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("package:");
    stringBuilder.append(paramString);
    intent.setData(Uri.parse(stringBuilder.toString()));
    VActivityManagerService.get().sendBroadcastAsUser(intent, paramVUserHandle);
  }
  
  private void sendUninstalledBroadcast(String paramString, VUserHandle paramVUserHandle) {
    Intent intent = new Intent("android.intent.action.PACKAGE_REMOVED");
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("package:");
    stringBuilder.append(paramString);
    intent.setData(Uri.parse(stringBuilder.toString()));
    VActivityManagerService.get().sendBroadcastAsUser(intent, paramVUserHandle);
  }
  
  private void startup() {
    this.mSystemConfig.load();
    this.mUidSystem.initUidList();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
    intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
    intentFilter.addDataScheme("package");
    VirtualCore.get().getContext().registerReceiver(this.appEventReceiver, intentFilter);
  }
  
  public static void systemReady() {
    VEnvironment.systemReady();
    if (BuildCompat.isPie() && !BuildCompat.isQ())
      get().extractApacheFrameworksForPie(); 
    get().startup();
  }
  
  private void uninstallPackageFully(PackageSetting paramPackageSetting, boolean paramBoolean) {
    String str = paramPackageSetting.packageName;
    VActivityManagerService.get().killAppByPkg(str, -1);
    PackageCacheManager.remove(str);
    FileUtils.deleteDir(VEnvironment.getDataAppPackageDirectory(str));
    FileUtils.deleteDir(VEnvironment.getOatDirectory(str));
    PackageCleaner.cleanAllUserPackage(VEnvironment.getDataUserDirectory(), str);
    for (VUserInfo vUserInfo : VUserManager.get().getUsers()) {
      VNotificationManagerService.get().cancelAllNotification(paramPackageSetting.packageName, vUserInfo.id);
      ComponentStateManager.user(vUserInfo.id).clear(str);
    } 
    if (paramBoolean)
      notifyAppUninstalled(paramPackageSetting, -1); 
    if (!this.mScanning)
      VExtPackageAccessor.syncPackages(); 
    VAccountManagerService.get().refreshAuthenticatorCache(null);
  }
  
  public boolean cleanPackageData(String paramString, int paramInt) {
    PackageSetting packageSetting = PackageCacheManager.getSetting(paramString);
    if (packageSetting == null)
      return false; 
    VActivityManagerService.get().killAppByPkg(paramString, paramInt);
    VNotificationManagerService.get().cancelAllNotification(packageSetting.packageName, paramInt);
    FileUtils.deleteDir(VEnvironment.getDataUserPackageDirectory(paramInt, paramString));
    FileUtils.deleteDir(VEnvironment.getDeDataUserPackageDirectory(paramInt, paramString));
    String str = packageSetting.packageName;
    VExtPackageAccessor.cleanPackageData(new int[] { paramInt }, str);
    ComponentStateManager.user(paramInt).clear(paramString);
    return true;
  }
  
  public int getInstalledAppCount() {
    return PackageCacheManager.size();
  }
  
  public InstalledAppInfo getInstalledAppInfo(String paramString, int paramInt) {
    // Byte code:
    //   0: ldc com/lody/virtual/server/pm/PackageCacheManager
    //   2: monitorenter
    //   3: aload_1
    //   4: ifnull -> 26
    //   7: aload_1
    //   8: invokestatic getSetting : (Ljava/lang/String;)Lcom/lody/virtual/server/pm/PackageSetting;
    //   11: astore_1
    //   12: aload_1
    //   13: ifnull -> 26
    //   16: aload_1
    //   17: invokevirtual getAppInfo : ()Lcom/lody/virtual/remote/InstalledAppInfo;
    //   20: astore_1
    //   21: ldc com/lody/virtual/server/pm/PackageCacheManager
    //   23: monitorexit
    //   24: aload_1
    //   25: areturn
    //   26: ldc com/lody/virtual/server/pm/PackageCacheManager
    //   28: monitorexit
    //   29: aconst_null
    //   30: areturn
    //   31: astore_1
    //   32: ldc com/lody/virtual/server/pm/PackageCacheManager
    //   34: monitorexit
    //   35: aload_1
    //   36: athrow
    // Exception table:
    //   from	to	target	type
    //   7	12	31	finally
    //   16	24	31	finally
    //   26	29	31	finally
    //   32	35	31	finally
  }
  
  public List<InstalledAppInfo> getInstalledApps(int paramInt) {
    null = new ArrayList(getInstalledAppCount());
    synchronized (PackageCacheManager.PACKAGE_CACHE) {
      Iterator iterator = PackageCacheManager.PACKAGE_CACHE.values().iterator();
      while (iterator.hasNext())
        null.add(((PackageSetting)((VPackage)iterator.next()).mExtras).getAppInfo()); 
      return null;
    } 
  }
  
  public List<InstalledAppInfo> getInstalledAppsAsUser(int paramInt1, int paramInt2) {
    ArrayList<InstalledAppInfo> arrayList = new ArrayList(getInstalledAppCount());
    synchronized (PackageCacheManager.PACKAGE_CACHE) {
      Iterator iterator = PackageCacheManager.PACKAGE_CACHE.values().iterator();
      while (iterator.hasNext()) {
        PackageSetting packageSetting = (PackageSetting)((VPackage)iterator.next()).mExtras;
        boolean bool1 = packageSetting.isInstalled(paramInt1);
        boolean bool2 = bool1;
        if ((paramInt2 & 0x1) == 0) {
          bool2 = bool1;
          if (packageSetting.isHidden(paramInt1))
            bool2 = false; 
        } 
        if (bool2)
          arrayList.add(packageSetting.getAppInfo()); 
      } 
      return arrayList;
    } 
  }
  
  public List<String> getInstalledSplitNames(String paramString) {
    // Byte code:
    //   0: ldc com/lody/virtual/server/pm/PackageCacheManager
    //   2: monitorenter
    //   3: aload_1
    //   4: ifnull -> 33
    //   7: aload_1
    //   8: invokestatic get : (Ljava/lang/String;)Lcom/lody/virtual/server/pm/parser/VPackage;
    //   11: astore_1
    //   12: aload_1
    //   13: ifnull -> 33
    //   16: aload_1
    //   17: getfield splitNames : Ljava/util/ArrayList;
    //   20: ifnull -> 33
    //   23: aload_1
    //   24: getfield splitNames : Ljava/util/ArrayList;
    //   27: astore_1
    //   28: ldc com/lody/virtual/server/pm/PackageCacheManager
    //   30: monitorexit
    //   31: aload_1
    //   32: areturn
    //   33: invokestatic emptyList : ()Ljava/util/List;
    //   36: astore_1
    //   37: ldc com/lody/virtual/server/pm/PackageCacheManager
    //   39: monitorexit
    //   40: aload_1
    //   41: areturn
    //   42: astore_1
    //   43: ldc com/lody/virtual/server/pm/PackageCacheManager
    //   45: monitorexit
    //   46: aload_1
    //   47: athrow
    // Exception table:
    //   from	to	target	type
    //   7	12	42	finally
    //   16	31	42	finally
    //   33	40	42	finally
    //   43	46	42	finally
  }
  
  public int[] getPackageInstalledUsers(String paramString) {
    PackageSetting packageSetting = PackageCacheManager.getSetting(paramString);
    byte b = 0;
    if (packageSetting != null) {
      IntArray intArray = new IntArray(5);
      int[] arrayOfInt = VUserManagerService.get().getUserIds();
      int i = arrayOfInt.length;
      while (b < i) {
        int j = arrayOfInt[b];
        if ((packageSetting.readUserState(j)).installed)
          intArray.add(j); 
        b++;
      } 
      return intArray.getAll();
    } 
    return new int[0];
  }
  
  public int getUidForSharedUser(String paramString) {
    return (paramString == null) ? -1 : this.mUidSystem.getUid(paramString);
  }
  
  public VAppInstallerResult installPackage(Uri paramUri, VAppInstallerParams paramVAppInstallerParams) {
    RuntimeException runtimeException;
    /* monitor enter ThisExpression{ObjectType{com/lody/virtual/server/pm/VAppManagerService}} */
    try {
      VAppInstallerResult vAppInstallerResult;
    } finally {
      paramVAppInstallerParams = null;
      paramVAppInstallerParams.printStackTrace();
      runtimeException = new RuntimeException();
      this((Throwable)paramVAppInstallerParams);
    } 
    /* monitor exit ThisExpression{ObjectType{com/lody/virtual/server/pm/VAppManagerService}} */
    throw runtimeException;
  }
  
  public boolean installPackageAsUser(int paramInt, String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic get : ()Lcom/lody/virtual/server/pm/VUserManagerService;
    //   5: iload_1
    //   6: invokevirtual exists : (I)Z
    //   9: ifeq -> 59
    //   12: aload_2
    //   13: invokestatic getSetting : (Ljava/lang/String;)Lcom/lody/virtual/server/pm/PackageSetting;
    //   16: astore_2
    //   17: aload_2
    //   18: ifnull -> 59
    //   21: aload_2
    //   22: iload_1
    //   23: invokevirtual isInstalled : (I)Z
    //   26: ifne -> 55
    //   29: aload_2
    //   30: iload_1
    //   31: iconst_1
    //   32: invokevirtual setInstalled : (IZ)V
    //   35: invokestatic syncPackages : ()V
    //   38: aload_0
    //   39: aload_2
    //   40: iload_1
    //   41: invokespecial notifyAppInstalled : (Lcom/lody/virtual/server/pm/PackageSetting;I)V
    //   44: aload_0
    //   45: getfield mPersistenceLayer : Lcom/lody/virtual/server/pm/PackagePersistenceLayer;
    //   48: invokevirtual save : ()V
    //   51: aload_0
    //   52: monitorexit
    //   53: iconst_1
    //   54: ireturn
    //   55: aload_0
    //   56: monitorexit
    //   57: iconst_1
    //   58: ireturn
    //   59: aload_0
    //   60: monitorexit
    //   61: iconst_0
    //   62: ireturn
    //   63: astore_2
    //   64: aload_0
    //   65: monitorexit
    //   66: aload_2
    //   67: athrow
    // Exception table:
    //   from	to	target	type
    //   2	17	63	finally
    //   21	51	63	finally
  }
  
  public boolean isAppInstalled(String paramString) {
    boolean bool;
    if (paramString != null && PackageCacheManager.contain(paramString)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isAppInstalledAsUser(int paramInt, String paramString) {
    if (paramString == null || !VUserManagerService.get().exists(paramInt))
      return false; 
    PackageSetting packageSetting = PackageCacheManager.getSetting(paramString);
    return (packageSetting == null) ? false : packageSetting.isInstalled(paramInt);
  }
  
  public boolean isPackage64bit(String paramString) {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull -> 6
    //   4: iconst_0
    //   5: ireturn
    //   6: new java/io/File
    //   9: dup
    //   10: aload_1
    //   11: invokespecial <init> : (Ljava/lang/String;)V
    //   14: astore_2
    //   15: aload_2
    //   16: invokevirtual exists : ()Z
    //   19: ifeq -> 277
    //   22: aload_2
    //   23: invokevirtual isFile : ()Z
    //   26: ifne -> 32
    //   29: goto -> 277
    //   32: aconst_null
    //   33: astore_1
    //   34: aload_2
    //   35: invokestatic parsePackage : (Ljava/io/File;)Lcom/lody/virtual/server/pm/parser/VPackage;
    //   38: astore_3
    //   39: aload_3
    //   40: astore_1
    //   41: goto -> 49
    //   44: astore_3
    //   45: aload_3
    //   46: invokevirtual printStackTrace : ()V
    //   49: aload_1
    //   50: ifnull -> 277
    //   53: aload_1
    //   54: getfield packageName : Ljava/lang/String;
    //   57: ifnonnull -> 63
    //   60: goto -> 277
    //   63: ldc_w 'showmepkg'
    //   66: aload_1
    //   67: getfield packageName : Ljava/lang/String;
    //   70: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   73: pop
    //   74: aload_1
    //   75: getfield packageName : Ljava/lang/String;
    //   78: ldc_w 'com.milecn.milevideo'
    //   81: invokevirtual equals : (Ljava/lang/Object;)Z
    //   84: ifeq -> 89
    //   87: iconst_1
    //   88: ireturn
    //   89: aload_2
    //   90: invokevirtual getPath : ()Ljava/lang/String;
    //   93: invokestatic getPackageAbiList : (Ljava/lang/String;)Ljava/util/Set;
    //   96: astore_3
    //   97: aload_3
    //   98: invokeinterface isEmpty : ()Z
    //   103: ifeq -> 191
    //   106: invokestatic getPM : ()Landroid/content/pm/PackageManager;
    //   109: aload_1
    //   110: getfield packageName : Ljava/lang/String;
    //   113: iconst_0
    //   114: invokevirtual getApplicationInfo : (Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;
    //   117: iconst_0
    //   118: invokestatic getInstalledSo : (Landroid/content/pm/ApplicationInfo;Z)Lcom/lody/virtual/helper/compat/NativeLibraryHelperCompat$SoLib;
    //   121: astore_1
    //   122: aload_1
    //   123: ifnull -> 168
    //   126: aload_1
    //   127: invokevirtual is64Bit : ()Z
    //   130: istore #4
    //   132: iload #4
    //   134: iconst_1
    //   135: ixor
    //   136: istore #5
    //   138: iload #5
    //   140: iconst_1
    //   141: ixor
    //   142: istore #4
    //   144: iconst_1
    //   145: istore #6
    //   147: goto -> 177
    //   150: astore_1
    //   151: getstatic com/lody/virtual/server/pm/VAppManagerService.TAG : Ljava/lang/String;
    //   154: ldc_w 'get Installed so error!'
    //   157: iconst_1
    //   158: anewarray java/lang/Object
    //   161: dup
    //   162: iconst_0
    //   163: aload_1
    //   164: aastore
    //   165: invokestatic e : (Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   168: iconst_0
    //   169: istore #5
    //   171: iconst_0
    //   172: istore #4
    //   174: iconst_0
    //   175: istore #6
    //   177: iload #6
    //   179: ifne -> 213
    //   182: iconst_1
    //   183: istore #5
    //   185: iconst_1
    //   186: istore #4
    //   188: goto -> 213
    //   191: aload_3
    //   192: invokestatic contain64bitAbi : (Ljava/util/Set;)Z
    //   195: istore #4
    //   197: aload_3
    //   198: invokestatic contain32bitAbi : (Ljava/util/Set;)Z
    //   201: ifeq -> 210
    //   204: iconst_1
    //   205: istore #5
    //   207: goto -> 213
    //   210: iconst_0
    //   211: istore #5
    //   213: iload #5
    //   215: ifeq -> 233
    //   218: iload #4
    //   220: ifne -> 233
    //   223: ldc_w 'isPackage64bit'
    //   226: ldc_w 'support 32 only'
    //   229: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   232: pop
    //   233: iload #5
    //   235: ifne -> 255
    //   238: iload #4
    //   240: ifeq -> 255
    //   243: ldc_w 'isPackage64bit'
    //   246: ldc_w 'support 64 only'
    //   249: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   252: pop
    //   253: iconst_1
    //   254: ireturn
    //   255: iload #5
    //   257: ifeq -> 277
    //   260: iload #4
    //   262: ifeq -> 277
    //   265: ldc_w 'isPackage64bit'
    //   268: ldc_w 'support 64 and 32 both'
    //   271: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   274: pop
    //   275: iconst_1
    //   276: ireturn
    //   277: iconst_0
    //   278: ireturn
    // Exception table:
    //   from	to	target	type
    //   34	39	44	finally
    //   106	122	150	finally
    //   126	132	150	finally
  }
  
  public boolean isPackageLaunched(int paramInt, String paramString) {
    boolean bool;
    PackageSetting packageSetting = PackageCacheManager.getSetting(paramString);
    if (packageSetting != null && packageSetting.isLaunched(paramInt)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isRunInExtProcess(String paramString) {
    boolean bool;
    PackageSetting packageSetting = PackageCacheManager.getSetting(paramString);
    if (packageSetting != null && packageSetting.isRunInExtProcess()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  boolean loadPackage(PackageSetting paramPackageSetting) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: invokespecial loadPackageInnerLocked : (Lcom/lody/virtual/server/pm/PackageSetting;)Z
    //   7: ifne -> 19
    //   10: aload_0
    //   11: aload_1
    //   12: invokespecial cleanUpResidualFiles : (Lcom/lody/virtual/server/pm/PackageSetting;)V
    //   15: aload_0
    //   16: monitorexit
    //   17: iconst_0
    //   18: ireturn
    //   19: aload_0
    //   20: monitorexit
    //   21: iconst_1
    //   22: ireturn
    //   23: astore_1
    //   24: aload_0
    //   25: monitorexit
    //   26: aload_1
    //   27: athrow
    // Exception table:
    //   from	to	target	type
    //   2	15	23	finally
  }
  
  public void onUserCreated(VUserInfo paramVUserInfo) {
    FileUtils.ensureDirCreate(VEnvironment.getDataUserDirectory(paramVUserInfo.id));
  }
  
  public void registerObserver(IPackageObserver paramIPackageObserver) {
    try {
      this.mRemoteCallbackList.register((IInterface)paramIPackageObserver);
    } finally {
      paramIPackageObserver = null;
    } 
  }
  
  void restoreFactoryState() {
    VLog.w(TAG, "Warning: Restore the factory state...", new Object[0]);
    FileUtils.deleteDir(VEnvironment.getRoot());
    VEnvironment.systemReady();
  }
  
  public void savePersistenceData() {
    this.mPersistenceLayer.save();
  }
  
  public void scanApps() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mScanning : Z
    //   4: ifeq -> 8
    //   7: return
    //   8: aload_0
    //   9: monitorenter
    //   10: aload_0
    //   11: iconst_1
    //   12: putfield mScanning : Z
    //   15: aload_0
    //   16: getfield mPersistenceLayer : Lcom/lody/virtual/server/pm/PackagePersistenceLayer;
    //   19: invokevirtual read : ()V
    //   22: aload_0
    //   23: getfield mPersistenceLayer : Lcom/lody/virtual/server/pm/PackagePersistenceLayer;
    //   26: getfield changed : Z
    //   29: ifeq -> 60
    //   32: aload_0
    //   33: getfield mPersistenceLayer : Lcom/lody/virtual/server/pm/PackagePersistenceLayer;
    //   36: iconst_0
    //   37: putfield changed : Z
    //   40: aload_0
    //   41: getfield mPersistenceLayer : Lcom/lody/virtual/server/pm/PackagePersistenceLayer;
    //   44: invokevirtual save : ()V
    //   47: getstatic com/lody/virtual/server/pm/VAppManagerService.TAG : Ljava/lang/String;
    //   50: ldc_w 'Package PersistenceLayer updated.'
    //   53: iconst_0
    //   54: anewarray java/lang/Object
    //   57: invokestatic w : (Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   60: invokestatic get : ()Lcom/lody/virtual/server/pm/VUserManagerService;
    //   63: iconst_1
    //   64: invokevirtual getUsers : (Z)Ljava/util/List;
    //   67: astore_1
    //   68: invokestatic getPreInstallPackages : ()Ljava/util/Set;
    //   71: invokeinterface iterator : ()Ljava/util/Iterator;
    //   76: astore_2
    //   77: aload_2
    //   78: invokeinterface hasNext : ()Z
    //   83: ifeq -> 238
    //   86: aload_2
    //   87: invokeinterface next : ()Ljava/lang/Object;
    //   92: checkcast java/lang/String
    //   95: astore_3
    //   96: invokestatic get : ()Lcom/lody/virtual/client/core/VirtualCore;
    //   99: invokevirtual getHostPackageManager : ()Lcom/lody/virtual/client/env/HostPackageManager;
    //   102: aload_3
    //   103: iconst_0
    //   104: invokevirtual getApplicationInfo : (Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;
    //   107: pop
    //   108: aload_1
    //   109: invokeinterface iterator : ()Ljava/util/Iterator;
    //   114: astore #4
    //   116: aload #4
    //   118: invokeinterface hasNext : ()Z
    //   123: ifeq -> 77
    //   126: aload #4
    //   128: invokeinterface next : ()Ljava/lang/Object;
    //   133: checkcast com/lody/virtual/os/VUserInfo
    //   136: astore #5
    //   138: aload_0
    //   139: aload_3
    //   140: invokevirtual isAppInstalled : (Ljava/lang/String;)Z
    //   143: ifne -> 211
    //   146: aload #5
    //   148: getfield id : I
    //   151: ifne -> 211
    //   154: new com/lody/virtual/remote/VAppInstallerParams
    //   157: astore #6
    //   159: aload #6
    //   161: bipush #10
    //   163: iconst_1
    //   164: invokespecial <init> : (II)V
    //   167: new java/lang/StringBuilder
    //   170: astore #5
    //   172: aload #5
    //   174: invokespecial <init> : ()V
    //   177: aload #5
    //   179: ldc_w 'package:'
    //   182: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   185: pop
    //   186: aload #5
    //   188: aload_3
    //   189: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: pop
    //   193: aload_0
    //   194: aload #5
    //   196: invokevirtual toString : ()Ljava/lang/String;
    //   199: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
    //   202: aload #6
    //   204: invokespecial installPackageInternal : (Landroid/net/Uri;Lcom/lody/virtual/remote/VAppInstallerParams;)Lcom/lody/virtual/remote/VAppInstallerResult;
    //   207: pop
    //   208: goto -> 116
    //   211: aload_0
    //   212: aload #5
    //   214: getfield id : I
    //   217: aload_3
    //   218: invokevirtual isAppInstalledAsUser : (ILjava/lang/String;)Z
    //   221: ifne -> 116
    //   224: aload_0
    //   225: aload #5
    //   227: getfield id : I
    //   230: aload_3
    //   231: invokevirtual installPackageAsUser : (ILjava/lang/String;)Z
    //   234: pop
    //   235: goto -> 116
    //   238: invokestatic get : ()Lcom/lody/virtual/server/accounts/VAccountManagerService;
    //   241: aconst_null
    //   242: invokevirtual refreshAuthenticatorCache : (Ljava/lang/String;)V
    //   245: aload_0
    //   246: iconst_0
    //   247: putfield mScanning : Z
    //   250: aload_0
    //   251: monitorexit
    //   252: return
    //   253: astore_2
    //   254: aload_0
    //   255: monitorexit
    //   256: aload_2
    //   257: athrow
    //   258: astore #4
    //   260: goto -> 77
    // Exception table:
    //   from	to	target	type
    //   10	60	253	finally
    //   60	77	253	finally
    //   77	96	253	finally
    //   96	108	258	android/content/pm/PackageManager$NameNotFoundException
    //   96	108	253	finally
    //   108	116	253	finally
    //   116	208	253	finally
    //   211	235	253	finally
    //   238	252	253	finally
    //   254	256	253	finally
  }
  
  public void setPackageHidden(int paramInt, String paramString, boolean paramBoolean) {
    PackageSetting packageSetting = PackageCacheManager.getSetting(paramString);
    if (packageSetting != null && VUserManagerService.get().exists(paramInt)) {
      packageSetting.setHidden(paramInt, paramBoolean);
      this.mPersistenceLayer.save();
    } 
  }
  
  public boolean uninstallPackage(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokestatic getSetting : (Ljava/lang/String;)Lcom/lody/virtual/server/pm/PackageSetting;
    //   6: astore_1
    //   7: aload_1
    //   8: ifnull -> 21
    //   11: aload_0
    //   12: aload_1
    //   13: iconst_1
    //   14: invokespecial uninstallPackageFully : (Lcom/lody/virtual/server/pm/PackageSetting;Z)V
    //   17: aload_0
    //   18: monitorexit
    //   19: iconst_1
    //   20: ireturn
    //   21: aload_0
    //   22: monitorexit
    //   23: iconst_0
    //   24: ireturn
    //   25: astore_1
    //   26: aload_0
    //   27: monitorexit
    //   28: aload_1
    //   29: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	25	finally
    //   11	17	25	finally
  }
  
  public boolean uninstallPackageAsUser(String paramString, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic get : ()Lcom/lody/virtual/server/pm/VUserManagerService;
    //   5: iload_2
    //   6: invokevirtual exists : (I)Z
    //   9: istore_3
    //   10: iload_3
    //   11: ifne -> 18
    //   14: aload_0
    //   15: monitorexit
    //   16: iconst_0
    //   17: ireturn
    //   18: aload_1
    //   19: invokestatic getSetting : (Ljava/lang/String;)Lcom/lody/virtual/server/pm/PackageSetting;
    //   22: astore #4
    //   24: aload #4
    //   26: ifnull -> 100
    //   29: aload_0
    //   30: aload_1
    //   31: invokevirtual getPackageInstalledUsers : (Ljava/lang/String;)[I
    //   34: astore #5
    //   36: aload #5
    //   38: iload_2
    //   39: invokestatic contains : ([II)Z
    //   42: istore_3
    //   43: iload_3
    //   44: ifne -> 51
    //   47: aload_0
    //   48: monitorexit
    //   49: iconst_0
    //   50: ireturn
    //   51: aload #5
    //   53: arraylength
    //   54: iconst_1
    //   55: if_icmpgt -> 68
    //   58: aload_0
    //   59: aload #4
    //   61: iconst_1
    //   62: invokespecial uninstallPackageFully : (Lcom/lody/virtual/server/pm/PackageSetting;Z)V
    //   65: goto -> 96
    //   68: aload_0
    //   69: aload_1
    //   70: iload_2
    //   71: invokevirtual cleanPackageData : (Ljava/lang/String;I)Z
    //   74: pop
    //   75: aload #4
    //   77: iload_2
    //   78: iconst_0
    //   79: invokevirtual setInstalled : (IZ)V
    //   82: aload_0
    //   83: getfield mPersistenceLayer : Lcom/lody/virtual/server/pm/PackagePersistenceLayer;
    //   86: invokevirtual save : ()V
    //   89: aload_0
    //   90: aload #4
    //   92: iload_2
    //   93: invokespecial notifyAppUninstalled : (Lcom/lody/virtual/server/pm/PackageSetting;I)V
    //   96: aload_0
    //   97: monitorexit
    //   98: iconst_1
    //   99: ireturn
    //   100: aload_0
    //   101: monitorexit
    //   102: iconst_0
    //   103: ireturn
    //   104: astore_1
    //   105: aload_0
    //   106: monitorexit
    //   107: aload_1
    //   108: athrow
    // Exception table:
    //   from	to	target	type
    //   2	10	104	finally
    //   18	24	104	finally
    //   29	43	104	finally
    //   51	65	104	finally
    //   68	96	104	finally
  }
  
  public void unregisterObserver(IPackageObserver paramIPackageObserver) {
    try {
      this.mRemoteCallbackList.unregister((IInterface)paramIPackageObserver);
    } finally {
      paramIPackageObserver = null;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\VAppManagerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */