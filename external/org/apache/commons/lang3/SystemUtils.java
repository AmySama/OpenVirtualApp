package external.org.apache.commons.lang3;

import java.io.File;
import java.io.PrintStream;

public class SystemUtils {
  public static final String AWT_TOOLKIT;
  
  public static final String FILE_ENCODING;
  
  public static final String FILE_SEPARATOR;
  
  public static final boolean IS_JAVA_1_1;
  
  public static final boolean IS_JAVA_1_2;
  
  public static final boolean IS_JAVA_1_3;
  
  public static final boolean IS_JAVA_1_4;
  
  public static final boolean IS_JAVA_1_5;
  
  public static final boolean IS_JAVA_1_6;
  
  public static final boolean IS_JAVA_1_7;
  
  public static final boolean IS_OS_AIX;
  
  public static final boolean IS_OS_FREE_BSD;
  
  public static final boolean IS_OS_HP_UX;
  
  public static final boolean IS_OS_IRIX;
  
  public static final boolean IS_OS_LINUX;
  
  public static final boolean IS_OS_MAC;
  
  public static final boolean IS_OS_MAC_OSX;
  
  public static final boolean IS_OS_NET_BSD;
  
  public static final boolean IS_OS_OPEN_BSD;
  
  public static final boolean IS_OS_OS2;
  
  public static final boolean IS_OS_SOLARIS;
  
  public static final boolean IS_OS_SUN_OS;
  
  public static final boolean IS_OS_UNIX;
  
  public static final boolean IS_OS_WINDOWS;
  
  public static final boolean IS_OS_WINDOWS_2000;
  
  public static final boolean IS_OS_WINDOWS_2003;
  
  public static final boolean IS_OS_WINDOWS_2008;
  
  public static final boolean IS_OS_WINDOWS_7;
  
  public static final boolean IS_OS_WINDOWS_95;
  
  public static final boolean IS_OS_WINDOWS_98;
  
  public static final boolean IS_OS_WINDOWS_ME;
  
  public static final boolean IS_OS_WINDOWS_NT;
  
  public static final boolean IS_OS_WINDOWS_VISTA;
  
  public static final boolean IS_OS_WINDOWS_XP;
  
  public static final String JAVA_AWT_FONTS;
  
  public static final String JAVA_AWT_GRAPHICSENV;
  
  public static final String JAVA_AWT_HEADLESS;
  
  public static final String JAVA_AWT_PRINTERJOB;
  
  public static final String JAVA_CLASS_PATH;
  
  public static final String JAVA_CLASS_VERSION;
  
  public static final String JAVA_COMPILER;
  
  public static final String JAVA_ENDORSED_DIRS;
  
  public static final String JAVA_EXT_DIRS;
  
  public static final String JAVA_HOME;
  
  private static final String JAVA_HOME_KEY = "java.home";
  
  public static final String JAVA_IO_TMPDIR;
  
  private static final String JAVA_IO_TMPDIR_KEY = "java.io.tmpdir";
  
  public static final String JAVA_LIBRARY_PATH;
  
  public static final String JAVA_RUNTIME_NAME;
  
  public static final String JAVA_RUNTIME_VERSION;
  
  public static final String JAVA_SPECIFICATION_NAME;
  
  public static final String JAVA_SPECIFICATION_VENDOR;
  
  public static final String JAVA_SPECIFICATION_VERSION;
  
  private static final JavaVersion JAVA_SPECIFICATION_VERSION_AS_ENUM;
  
  public static final String JAVA_UTIL_PREFS_PREFERENCES_FACTORY;
  
  public static final String JAVA_VENDOR;
  
  public static final String JAVA_VENDOR_URL;
  
  public static final String JAVA_VERSION;
  
  public static final String JAVA_VM_INFO;
  
  public static final String JAVA_VM_NAME;
  
  public static final String JAVA_VM_SPECIFICATION_NAME;
  
  public static final String JAVA_VM_SPECIFICATION_VENDOR;
  
  public static final String JAVA_VM_SPECIFICATION_VERSION;
  
  public static final String JAVA_VM_VENDOR;
  
  public static final String JAVA_VM_VERSION;
  
  public static final String LINE_SEPARATOR;
  
  public static final String OS_ARCH;
  
  public static final String OS_NAME;
  
  private static final String OS_NAME_WINDOWS_PREFIX = "Windows";
  
  public static final String OS_VERSION;
  
  public static final String PATH_SEPARATOR;
  
  public static final String USER_COUNTRY;
  
  public static final String USER_DIR;
  
  private static final String USER_DIR_KEY = "user.dir";
  
  public static final String USER_HOME;
  
  private static final String USER_HOME_KEY = "user.home";
  
  public static final String USER_LANGUAGE;
  
  public static final String USER_NAME;
  
  public static final String USER_TIMEZONE;
  
  static {
    // Byte code:
    //   0: ldc 'awt.toolkit'
    //   2: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   5: putstatic external/org/apache/commons/lang3/SystemUtils.AWT_TOOLKIT : Ljava/lang/String;
    //   8: ldc 'file.encoding'
    //   10: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   13: putstatic external/org/apache/commons/lang3/SystemUtils.FILE_ENCODING : Ljava/lang/String;
    //   16: ldc 'file.separator'
    //   18: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   21: putstatic external/org/apache/commons/lang3/SystemUtils.FILE_SEPARATOR : Ljava/lang/String;
    //   24: ldc 'java.awt.fonts'
    //   26: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   29: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_AWT_FONTS : Ljava/lang/String;
    //   32: ldc 'java.awt.graphicsenv'
    //   34: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   37: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_AWT_GRAPHICSENV : Ljava/lang/String;
    //   40: ldc 'java.awt.headless'
    //   42: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   45: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_AWT_HEADLESS : Ljava/lang/String;
    //   48: ldc 'java.awt.printerjob'
    //   50: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   53: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_AWT_PRINTERJOB : Ljava/lang/String;
    //   56: ldc 'java.class.path'
    //   58: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   61: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_CLASS_PATH : Ljava/lang/String;
    //   64: ldc 'java.class.version'
    //   66: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   69: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_CLASS_VERSION : Ljava/lang/String;
    //   72: ldc 'java.compiler'
    //   74: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   77: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_COMPILER : Ljava/lang/String;
    //   80: ldc 'java.endorsed.dirs'
    //   82: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   85: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_ENDORSED_DIRS : Ljava/lang/String;
    //   88: ldc 'java.ext.dirs'
    //   90: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   93: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_EXT_DIRS : Ljava/lang/String;
    //   96: ldc 'java.home'
    //   98: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   101: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_HOME : Ljava/lang/String;
    //   104: ldc 'java.io.tmpdir'
    //   106: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   109: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_IO_TMPDIR : Ljava/lang/String;
    //   112: ldc 'java.library.path'
    //   114: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   117: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_LIBRARY_PATH : Ljava/lang/String;
    //   120: ldc 'java.runtime.name'
    //   122: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   125: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_RUNTIME_NAME : Ljava/lang/String;
    //   128: ldc 'java.runtime.version'
    //   130: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   133: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_RUNTIME_VERSION : Ljava/lang/String;
    //   136: ldc 'java.specification.name'
    //   138: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   141: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_SPECIFICATION_NAME : Ljava/lang/String;
    //   144: ldc 'java.specification.vendor'
    //   146: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   149: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_SPECIFICATION_VENDOR : Ljava/lang/String;
    //   152: ldc 'java.specification.version'
    //   154: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   157: astore_0
    //   158: aload_0
    //   159: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_SPECIFICATION_VERSION : Ljava/lang/String;
    //   162: aload_0
    //   163: invokestatic get : (Ljava/lang/String;)Lexternal/org/apache/commons/lang3/JavaVersion;
    //   166: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_SPECIFICATION_VERSION_AS_ENUM : Lexternal/org/apache/commons/lang3/JavaVersion;
    //   169: ldc 'java.util.prefs.PreferencesFactory'
    //   171: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   174: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_UTIL_PREFS_PREFERENCES_FACTORY : Ljava/lang/String;
    //   177: ldc 'java.vendor'
    //   179: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   182: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_VENDOR : Ljava/lang/String;
    //   185: ldc 'java.vendor.url'
    //   187: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   190: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_VENDOR_URL : Ljava/lang/String;
    //   193: ldc 'java.version'
    //   195: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   198: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_VERSION : Ljava/lang/String;
    //   201: ldc 'java.vm.info'
    //   203: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   206: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_VM_INFO : Ljava/lang/String;
    //   209: ldc 'java.vm.name'
    //   211: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   214: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_VM_NAME : Ljava/lang/String;
    //   217: ldc 'java.vm.specification.name'
    //   219: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   222: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_VM_SPECIFICATION_NAME : Ljava/lang/String;
    //   225: ldc 'java.vm.specification.vendor'
    //   227: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   230: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_VM_SPECIFICATION_VENDOR : Ljava/lang/String;
    //   233: ldc 'java.vm.specification.version'
    //   235: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   238: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_VM_SPECIFICATION_VERSION : Ljava/lang/String;
    //   241: ldc 'java.vm.vendor'
    //   243: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   246: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_VM_VENDOR : Ljava/lang/String;
    //   249: ldc 'java.vm.version'
    //   251: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   254: putstatic external/org/apache/commons/lang3/SystemUtils.JAVA_VM_VERSION : Ljava/lang/String;
    //   257: ldc 'line.separator'
    //   259: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   262: putstatic external/org/apache/commons/lang3/SystemUtils.LINE_SEPARATOR : Ljava/lang/String;
    //   265: ldc 'os.arch'
    //   267: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   270: putstatic external/org/apache/commons/lang3/SystemUtils.OS_ARCH : Ljava/lang/String;
    //   273: ldc 'os.name'
    //   275: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   278: putstatic external/org/apache/commons/lang3/SystemUtils.OS_NAME : Ljava/lang/String;
    //   281: ldc 'os.version'
    //   283: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   286: putstatic external/org/apache/commons/lang3/SystemUtils.OS_VERSION : Ljava/lang/String;
    //   289: ldc 'path.separator'
    //   291: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   294: putstatic external/org/apache/commons/lang3/SystemUtils.PATH_SEPARATOR : Ljava/lang/String;
    //   297: ldc 'user.country'
    //   299: astore_0
    //   300: ldc 'user.country'
    //   302: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   305: ifnonnull -> 311
    //   308: ldc 'user.region'
    //   310: astore_0
    //   311: aload_0
    //   312: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   315: putstatic external/org/apache/commons/lang3/SystemUtils.USER_COUNTRY : Ljava/lang/String;
    //   318: ldc 'user.dir'
    //   320: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   323: putstatic external/org/apache/commons/lang3/SystemUtils.USER_DIR : Ljava/lang/String;
    //   326: ldc 'user.home'
    //   328: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   331: putstatic external/org/apache/commons/lang3/SystemUtils.USER_HOME : Ljava/lang/String;
    //   334: ldc_w 'user.language'
    //   337: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   340: putstatic external/org/apache/commons/lang3/SystemUtils.USER_LANGUAGE : Ljava/lang/String;
    //   343: ldc_w 'user.name'
    //   346: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   349: putstatic external/org/apache/commons/lang3/SystemUtils.USER_NAME : Ljava/lang/String;
    //   352: ldc_w 'user.timezone'
    //   355: invokestatic getSystemProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   358: putstatic external/org/apache/commons/lang3/SystemUtils.USER_TIMEZONE : Ljava/lang/String;
    //   361: ldc_w '1.1'
    //   364: invokestatic getJavaVersionMatches : (Ljava/lang/String;)Z
    //   367: putstatic external/org/apache/commons/lang3/SystemUtils.IS_JAVA_1_1 : Z
    //   370: ldc_w '1.2'
    //   373: invokestatic getJavaVersionMatches : (Ljava/lang/String;)Z
    //   376: putstatic external/org/apache/commons/lang3/SystemUtils.IS_JAVA_1_2 : Z
    //   379: ldc_w '1.3'
    //   382: invokestatic getJavaVersionMatches : (Ljava/lang/String;)Z
    //   385: putstatic external/org/apache/commons/lang3/SystemUtils.IS_JAVA_1_3 : Z
    //   388: ldc_w '1.4'
    //   391: invokestatic getJavaVersionMatches : (Ljava/lang/String;)Z
    //   394: putstatic external/org/apache/commons/lang3/SystemUtils.IS_JAVA_1_4 : Z
    //   397: ldc_w '1.5'
    //   400: invokestatic getJavaVersionMatches : (Ljava/lang/String;)Z
    //   403: putstatic external/org/apache/commons/lang3/SystemUtils.IS_JAVA_1_5 : Z
    //   406: ldc_w '1.6'
    //   409: invokestatic getJavaVersionMatches : (Ljava/lang/String;)Z
    //   412: putstatic external/org/apache/commons/lang3/SystemUtils.IS_JAVA_1_6 : Z
    //   415: ldc_w '1.7'
    //   418: invokestatic getJavaVersionMatches : (Ljava/lang/String;)Z
    //   421: putstatic external/org/apache/commons/lang3/SystemUtils.IS_JAVA_1_7 : Z
    //   424: ldc_w 'AIX'
    //   427: invokestatic getOSMatchesName : (Ljava/lang/String;)Z
    //   430: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_AIX : Z
    //   433: ldc_w 'HP-UX'
    //   436: invokestatic getOSMatchesName : (Ljava/lang/String;)Z
    //   439: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_HP_UX : Z
    //   442: ldc_w 'Irix'
    //   445: invokestatic getOSMatchesName : (Ljava/lang/String;)Z
    //   448: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_IRIX : Z
    //   451: ldc_w 'Linux'
    //   454: invokestatic getOSMatchesName : (Ljava/lang/String;)Z
    //   457: istore_1
    //   458: iconst_0
    //   459: istore_2
    //   460: iload_1
    //   461: ifne -> 481
    //   464: ldc_w 'LINUX'
    //   467: invokestatic getOSMatchesName : (Ljava/lang/String;)Z
    //   470: ifeq -> 476
    //   473: goto -> 481
    //   476: iconst_0
    //   477: istore_1
    //   478: goto -> 483
    //   481: iconst_1
    //   482: istore_1
    //   483: iload_1
    //   484: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_LINUX : Z
    //   487: ldc_w 'Mac'
    //   490: invokestatic getOSMatchesName : (Ljava/lang/String;)Z
    //   493: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_MAC : Z
    //   496: ldc_w 'Mac OS X'
    //   499: invokestatic getOSMatchesName : (Ljava/lang/String;)Z
    //   502: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_MAC_OSX : Z
    //   505: ldc_w 'FreeBSD'
    //   508: invokestatic getOSMatchesName : (Ljava/lang/String;)Z
    //   511: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_FREE_BSD : Z
    //   514: ldc_w 'OpenBSD'
    //   517: invokestatic getOSMatchesName : (Ljava/lang/String;)Z
    //   520: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_OPEN_BSD : Z
    //   523: ldc_w 'NetBSD'
    //   526: invokestatic getOSMatchesName : (Ljava/lang/String;)Z
    //   529: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_NET_BSD : Z
    //   532: ldc_w 'OS/2'
    //   535: invokestatic getOSMatchesName : (Ljava/lang/String;)Z
    //   538: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_OS2 : Z
    //   541: ldc_w 'Solaris'
    //   544: invokestatic getOSMatchesName : (Ljava/lang/String;)Z
    //   547: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_SOLARIS : Z
    //   550: ldc_w 'SunOS'
    //   553: invokestatic getOSMatchesName : (Ljava/lang/String;)Z
    //   556: istore_1
    //   557: iload_1
    //   558: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_SUN_OS : Z
    //   561: getstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_AIX : Z
    //   564: ifne -> 621
    //   567: getstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_HP_UX : Z
    //   570: ifne -> 621
    //   573: getstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_IRIX : Z
    //   576: ifne -> 621
    //   579: getstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_LINUX : Z
    //   582: ifne -> 621
    //   585: getstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_MAC_OSX : Z
    //   588: ifne -> 621
    //   591: getstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_SOLARIS : Z
    //   594: ifne -> 621
    //   597: iload_1
    //   598: ifne -> 621
    //   601: getstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_FREE_BSD : Z
    //   604: ifne -> 621
    //   607: getstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_OPEN_BSD : Z
    //   610: ifne -> 621
    //   613: iload_2
    //   614: istore_1
    //   615: getstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_NET_BSD : Z
    //   618: ifeq -> 623
    //   621: iconst_1
    //   622: istore_1
    //   623: iload_1
    //   624: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_UNIX : Z
    //   627: ldc 'Windows'
    //   629: invokestatic getOSMatchesName : (Ljava/lang/String;)Z
    //   632: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_WINDOWS : Z
    //   635: ldc 'Windows'
    //   637: ldc_w '5.0'
    //   640: invokestatic getOSMatches : (Ljava/lang/String;Ljava/lang/String;)Z
    //   643: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_WINDOWS_2000 : Z
    //   646: ldc 'Windows'
    //   648: ldc_w '5.2'
    //   651: invokestatic getOSMatches : (Ljava/lang/String;Ljava/lang/String;)Z
    //   654: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_WINDOWS_2003 : Z
    //   657: ldc_w 'Windows Server 2008'
    //   660: ldc_w '6.1'
    //   663: invokestatic getOSMatches : (Ljava/lang/String;Ljava/lang/String;)Z
    //   666: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_WINDOWS_2008 : Z
    //   669: ldc_w 'Windows 9'
    //   672: ldc_w '4.0'
    //   675: invokestatic getOSMatches : (Ljava/lang/String;Ljava/lang/String;)Z
    //   678: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_WINDOWS_95 : Z
    //   681: ldc_w 'Windows 9'
    //   684: ldc_w '4.1'
    //   687: invokestatic getOSMatches : (Ljava/lang/String;Ljava/lang/String;)Z
    //   690: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_WINDOWS_98 : Z
    //   693: ldc 'Windows'
    //   695: ldc_w '4.9'
    //   698: invokestatic getOSMatches : (Ljava/lang/String;Ljava/lang/String;)Z
    //   701: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_WINDOWS_ME : Z
    //   704: ldc_w 'Windows NT'
    //   707: invokestatic getOSMatchesName : (Ljava/lang/String;)Z
    //   710: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_WINDOWS_NT : Z
    //   713: ldc 'Windows'
    //   715: ldc_w '5.1'
    //   718: invokestatic getOSMatches : (Ljava/lang/String;Ljava/lang/String;)Z
    //   721: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_WINDOWS_XP : Z
    //   724: ldc 'Windows'
    //   726: ldc_w '6.0'
    //   729: invokestatic getOSMatches : (Ljava/lang/String;Ljava/lang/String;)Z
    //   732: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_WINDOWS_VISTA : Z
    //   735: ldc 'Windows'
    //   737: ldc_w '6.1'
    //   740: invokestatic getOSMatches : (Ljava/lang/String;Ljava/lang/String;)Z
    //   743: putstatic external/org/apache/commons/lang3/SystemUtils.IS_OS_WINDOWS_7 : Z
    //   746: return
  }
  
  public static File getJavaHome() {
    return new File(System.getProperty("java.home"));
  }
  
  public static File getJavaIoTmpDir() {
    return new File(System.getProperty("java.io.tmpdir"));
  }
  
  private static boolean getJavaVersionMatches(String paramString) {
    return isJavaVersionMatch(JAVA_SPECIFICATION_VERSION, paramString);
  }
  
  private static boolean getOSMatches(String paramString1, String paramString2) {
    return isOSMatch(OS_NAME, OS_VERSION, paramString1, paramString2);
  }
  
  private static boolean getOSMatchesName(String paramString) {
    return isOSNameMatch(OS_NAME, paramString);
  }
  
  private static String getSystemProperty(String paramString) {
    try {
      return System.getProperty(paramString);
    } catch (SecurityException securityException) {
      PrintStream printStream = System.err;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Caught a SecurityException reading the system property '");
      stringBuilder.append(paramString);
      stringBuilder.append("'; the SystemUtils property value will default to null.");
      printStream.println(stringBuilder.toString());
      return null;
    } 
  }
  
  public static File getUserDir() {
    return new File(System.getProperty("user.dir"));
  }
  
  public static File getUserHome() {
    return new File(System.getProperty("user.home"));
  }
  
  public static boolean isJavaAwtHeadless() {
    boolean bool;
    String str = JAVA_AWT_HEADLESS;
    if (str != null) {
      bool = str.equals(Boolean.TRUE.toString());
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isJavaVersionAtLeast(JavaVersion paramJavaVersion) {
    return JAVA_SPECIFICATION_VERSION_AS_ENUM.atLeast(paramJavaVersion);
  }
  
  static boolean isJavaVersionMatch(String paramString1, String paramString2) {
    return (paramString1 == null) ? false : paramString1.startsWith(paramString2);
  }
  
  static boolean isOSMatch(String paramString1, String paramString2, String paramString3, String paramString4) {
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (paramString1 != null)
      if (paramString2 == null) {
        bool2 = bool1;
      } else {
        bool2 = bool1;
        if (paramString1.startsWith(paramString3)) {
          bool2 = bool1;
          if (paramString2.startsWith(paramString4))
            bool2 = true; 
        } 
      }  
    return bool2;
  }
  
  static boolean isOSNameMatch(String paramString1, String paramString2) {
    return (paramString1 == null) ? false : paramString1.startsWith(paramString2);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\SystemUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */