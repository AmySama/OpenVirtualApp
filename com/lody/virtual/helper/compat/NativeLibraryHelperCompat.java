package com.lody.virtual.helper.compat;

import android.content.pm.ApplicationInfo;
import android.util.Log;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.VLog;
import java.io.File;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import mirror.com.android.internal.content.NativeLibraryHelper;

public class NativeLibraryHelperCompat {
  private static String TAG = NativeLibraryHelperCompat.class.getSimpleName();
  
  private Object handle;
  
  public NativeLibraryHelperCompat(File paramFile) {}
  
  public static boolean contain32bitAbi(Set<String> paramSet) {
    Iterator<String> iterator = paramSet.iterator();
    while (iterator.hasNext()) {
      if (is32bitAbi(iterator.next()))
        return true; 
    } 
    return false;
  }
  
  public static boolean contain64bitAbi(Set<String> paramSet) {
    Iterator<String> iterator = paramSet.iterator();
    while (iterator.hasNext()) {
      if (is64bitAbi(iterator.next()))
        return true; 
    } 
    return false;
  }
  
  public static String findSupportedAbi(String[] paramArrayOfString, Set<String> paramSet) {
    int i = paramArrayOfString.length;
    for (byte b = 0; b < i; b++) {
      String str = paramArrayOfString[b];
      if (paramSet.contains(str))
        return str; 
    } 
    return null;
  }
  
  public static String getAbiFromElf(File paramFile) {
    // Byte code:
    //   0: bipush #20
    //   2: newarray byte
    //   4: astore_1
    //   5: aconst_null
    //   6: astore_2
    //   7: new java/io/FileInputStream
    //   10: astore_3
    //   11: aload_3
    //   12: aload_0
    //   13: invokespecial <init> : (Ljava/io/File;)V
    //   16: aload_3
    //   17: astore_0
    //   18: aload_3
    //   19: aload_1
    //   20: invokevirtual read : ([B)I
    //   23: bipush #20
    //   25: if_icmpne -> 181
    //   28: aload_1
    //   29: iconst_5
    //   30: baload
    //   31: iconst_1
    //   32: if_icmpne -> 44
    //   35: aload_1
    //   36: bipush #18
    //   38: baload
    //   39: istore #4
    //   41: goto -> 50
    //   44: aload_1
    //   45: bipush #19
    //   47: baload
    //   48: istore #4
    //   50: iload #4
    //   52: bipush #-73
    //   54: if_icmpeq -> 160
    //   57: iload #4
    //   59: iconst_3
    //   60: if_icmpeq -> 139
    //   63: iload #4
    //   65: bipush #40
    //   67: if_icmpeq -> 118
    //   70: iload #4
    //   72: bipush #62
    //   74: if_icmpeq -> 97
    //   77: aload_3
    //   78: invokevirtual close : ()V
    //   81: goto -> 95
    //   84: astore_0
    //   85: getstatic com/lody/virtual/helper/compat/NativeLibraryHelperCompat.TAG : Ljava/lang/String;
    //   88: ldc 'getAbiFromElf close error'
    //   90: aload_0
    //   91: invokestatic e : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   94: pop
    //   95: aconst_null
    //   96: areturn
    //   97: aload_3
    //   98: invokevirtual close : ()V
    //   101: goto -> 115
    //   104: astore_0
    //   105: getstatic com/lody/virtual/helper/compat/NativeLibraryHelperCompat.TAG : Ljava/lang/String;
    //   108: ldc 'getAbiFromElf close error'
    //   110: aload_0
    //   111: invokestatic e : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   114: pop
    //   115: ldc 'x86_64'
    //   117: areturn
    //   118: aload_3
    //   119: invokevirtual close : ()V
    //   122: goto -> 136
    //   125: astore_0
    //   126: getstatic com/lody/virtual/helper/compat/NativeLibraryHelperCompat.TAG : Ljava/lang/String;
    //   129: ldc 'getAbiFromElf close error'
    //   131: aload_0
    //   132: invokestatic e : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   135: pop
    //   136: ldc 'armeabi-v7a'
    //   138: areturn
    //   139: aload_3
    //   140: invokevirtual close : ()V
    //   143: goto -> 157
    //   146: astore_0
    //   147: getstatic com/lody/virtual/helper/compat/NativeLibraryHelperCompat.TAG : Ljava/lang/String;
    //   150: ldc 'getAbiFromElf close error'
    //   152: aload_0
    //   153: invokestatic e : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   156: pop
    //   157: ldc 'x86'
    //   159: areturn
    //   160: aload_3
    //   161: invokevirtual close : ()V
    //   164: goto -> 178
    //   167: astore_0
    //   168: getstatic com/lody/virtual/helper/compat/NativeLibraryHelperCompat.TAG : Ljava/lang/String;
    //   171: ldc 'getAbiFromElf close error'
    //   173: aload_0
    //   174: invokestatic e : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   177: pop
    //   178: ldc 'arm64-v8a'
    //   180: areturn
    //   181: aload_3
    //   182: invokevirtual close : ()V
    //   185: goto -> 199
    //   188: astore_0
    //   189: getstatic com/lody/virtual/helper/compat/NativeLibraryHelperCompat.TAG : Ljava/lang/String;
    //   192: ldc 'getAbiFromElf close error'
    //   194: aload_0
    //   195: invokestatic e : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   198: pop
    //   199: aconst_null
    //   200: areturn
    //   201: astore_2
    //   202: goto -> 214
    //   205: astore_0
    //   206: aload_2
    //   207: astore_3
    //   208: goto -> 257
    //   211: astore_2
    //   212: aconst_null
    //   213: astore_3
    //   214: aload_3
    //   215: astore_0
    //   216: getstatic com/lody/virtual/helper/compat/NativeLibraryHelperCompat.TAG : Ljava/lang/String;
    //   219: ldc 'getAbiFromElf error'
    //   221: aload_2
    //   222: invokestatic e : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   225: pop
    //   226: aload_3
    //   227: ifnull -> 248
    //   230: aload_3
    //   231: invokevirtual close : ()V
    //   234: goto -> 248
    //   237: astore_0
    //   238: getstatic com/lody/virtual/helper/compat/NativeLibraryHelperCompat.TAG : Ljava/lang/String;
    //   241: ldc 'getAbiFromElf close error'
    //   243: aload_0
    //   244: invokestatic e : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   247: pop
    //   248: aconst_null
    //   249: areturn
    //   250: astore_3
    //   251: aload_0
    //   252: astore_2
    //   253: aload_3
    //   254: astore_0
    //   255: aload_2
    //   256: astore_3
    //   257: aload_3
    //   258: ifnull -> 279
    //   261: aload_3
    //   262: invokevirtual close : ()V
    //   265: goto -> 279
    //   268: astore_3
    //   269: getstatic com/lody/virtual/helper/compat/NativeLibraryHelperCompat.TAG : Ljava/lang/String;
    //   272: ldc 'getAbiFromElf close error'
    //   274: aload_3
    //   275: invokestatic e : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   278: pop
    //   279: aload_0
    //   280: athrow
    // Exception table:
    //   from	to	target	type
    //   7	16	211	java/lang/Exception
    //   7	16	205	finally
    //   18	28	201	java/lang/Exception
    //   18	28	250	finally
    //   77	81	84	java/io/IOException
    //   97	101	104	java/io/IOException
    //   118	122	125	java/io/IOException
    //   139	143	146	java/io/IOException
    //   160	164	167	java/io/IOException
    //   181	185	188	java/io/IOException
    //   216	226	250	finally
    //   230	234	237	java/io/IOException
    //   261	265	268	java/io/IOException
  }
  
  public static SoLib getInstalledSo(ApplicationInfo paramApplicationInfo, boolean paramBoolean) {
    File file1;
    String str;
    if (paramApplicationInfo == null)
      return null; 
    File file2 = new File(paramApplicationInfo.nativeLibraryDir);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("applicationInfo.nativeLibraryDir");
    stringBuilder.append(paramApplicationInfo.nativeLibraryDir);
    Log.e("isPackage64bit", stringBuilder.toString());
    File[] arrayOfFile = file2.listFiles();
    if (paramBoolean) {
      String str2 = paramApplicationInfo.nativeLibraryDir;
      String str1 = str2;
      if (!str2.endsWith("64")) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str2);
        stringBuilder1.append("64");
        str1 = stringBuilder1.toString();
      } 
      File file = new File(str1);
      file1 = file2;
      File[] arrayOfFile1 = arrayOfFile;
      if (file.exists()) {
        arrayOfFile1 = file.listFiles();
        file1 = file;
      } 
      if (file.exists() && ArrayUtils.isEmpty((Object[])arrayOfFile1))
        return new SoLib("arm64-v8a", file1.getAbsolutePath()); 
      if (!ArrayUtils.isEmpty((Object[])arrayOfFile1)) {
        str = getAbiFromElf(arrayOfFile1[0]);
        if (str != null)
          return new SoLib(str, file1.getAbsolutePath()); 
      } 
    } else if (!ArrayUtils.isEmpty((Object[])arrayOfFile)) {
      String str1 = getAbiFromElf(arrayOfFile[0]);
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("abi");
      stringBuilder1.append(str1);
      Log.e("isPackage64bit", stringBuilder1.toString());
      if ((((ApplicationInfo)file1).flags & 0x1) == 0 && is32bitAbi(str1))
        return new SoLib(str1, str.getAbsolutePath()); 
    } 
    return null;
  }
  
  public static Set<String> getPackageAbiList(String paramString) {
    try {
      ZipFile zipFile = new ZipFile();
      this(paramString);
      Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
      HashSet<String> hashSet = new HashSet();
      this();
      while (enumeration.hasMoreElements()) {
        ZipEntry zipEntry = enumeration.nextElement();
        String str = zipEntry.getName();
        if (!str.contains("../") && str.startsWith("lib/") && !zipEntry.isDirectory() && str.endsWith(".so")) {
          String str1 = str.substring(str.indexOf("/") + 1, str.lastIndexOf("/"));
          hashSet.add(str1);
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("supportedAbi");
          stringBuilder.append(str1);
          Log.e("isPackage64bit", stringBuilder.toString());
        } 
      } 
      return hashSet;
    } catch (Exception exception) {
      exception.printStackTrace();
      return Collections.emptySet();
    } 
  }
  
  public static boolean is32bitAbi(String paramString) {
    return ("armeabi".equals(paramString) || "armeabi-v7a".equals(paramString) || "mips".equals(paramString) || "x86".equals(paramString));
  }
  
  public static boolean is64bitAbi(String paramString) {
    return ("arm64-v8a".equals(paramString) || "x86_64".equals(paramString) || "mips64".equals(paramString));
  }
  
  public static boolean isHandledAbi(String paramString) {
    return (is32bitAbi(paramString) || is64bitAbi(paramString));
  }
  
  public int copyNativeBinaries(File paramFile, String paramString) {
    try {
      return ((Integer)NativeLibraryHelper.copyNativeBinaries.callWithException(new Object[] { this.handle, paramFile, paramString })).intValue();
    } finally {
      paramFile = null;
      VLog.e(TAG, "failed to copyNativeBinaries.");
      paramFile.printStackTrace();
    } 
  }
  
  public static class SoLib {
    public String ABI;
    
    public String path;
    
    public SoLib() {}
    
    public SoLib(String param1String1, String param1String2) {
      this.ABI = param1String1;
      this.path = param1String2;
    }
    
    public boolean is64Bit() {
      boolean bool;
      String str = this.ABI;
      if (str != null && NativeLibraryHelperCompat.is64bitAbi(str)) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\NativeLibraryHelperCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */