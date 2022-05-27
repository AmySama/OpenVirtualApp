package com.lody.virtual.os;

import android.content.Context;
import android.os.Build;
import android.util.Base64;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.stub.StubManifest;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.EncodeUtils;
import com.lody.virtual.helper.utils.FileUtils;
import java.io.File;
import java.io.IOException;

public class VEnvironment {
  private static final File DATA_APP_DIRECTORY;
  
  private static final File DATA_APP_DIRECTORY_EXT;
  
  private static final File DATA_APP_SYSTEM_DIRECTORY;
  
  private static final File DATA_DIRECTORY;
  
  private static final File DATA_DIRECTORY_EXT;
  
  private static final File DATA_USER_DE_DIRECTORY;
  
  private static final File DATA_USER_DE_DIRECTORY_EXT;
  
  private static final File DATA_USER_DIRECTORY;
  
  private static final File DATA_USER_DIRECTORY_EXT;
  
  private static final File FRAMEWORK_DIRECTORY;
  
  private static final File FRAMEWORK_DIRECTORY_EXT;
  
  private static final File ROOT;
  
  private static final File ROOT_EXT;
  
  private static final String TAG = VEnvironment.class.getSimpleName();
  
  static {
    try {
      file1 = new File();
      this((getContext().getApplicationInfo()).dataDir);
      file1 = file1.getCanonicalFile().getParentFile();
    } catch (IOException iOException) {
      file1 = (new File((getContext().getApplicationInfo()).dataDir)).getParentFile();
    } 
    File file2 = buildPath(new File(file1, StubManifest.PACKAGE_NAME), new String[] { "virtual" });
    ROOT = file2;
    FRAMEWORK_DIRECTORY = buildPath(file2, new String[] { "framework" });
    file2 = buildPath(ROOT, new String[] { "data" });
    DATA_DIRECTORY = file2;
    DATA_USER_DIRECTORY = buildPath(file2, new String[] { "user" });
    DATA_USER_DE_DIRECTORY = buildPath(DATA_DIRECTORY, new String[] { "user_de" });
    file2 = buildPath(DATA_DIRECTORY, new String[] { "app" });
    DATA_APP_DIRECTORY = file2;
    DATA_APP_SYSTEM_DIRECTORY = buildPath(file2, new String[] { "system" });
    File file1 = buildPath(new File(file1, StubManifest.EXT_PACKAGE_NAME), new String[] { "virtual" });
    ROOT_EXT = file1;
    file1 = buildPath(file1, new String[] { "data" });
    DATA_DIRECTORY_EXT = file1;
    DATA_USER_DIRECTORY_EXT = buildPath(file1, new String[] { "user" });
    DATA_APP_DIRECTORY_EXT = buildPath(DATA_DIRECTORY_EXT, new String[] { "app" });
    DATA_USER_DE_DIRECTORY_EXT = buildPath(DATA_DIRECTORY_EXT, new String[] { "user_de" });
    FRAMEWORK_DIRECTORY_EXT = buildPath(ROOT_EXT, new String[] { "framework" });
  }
  
  public static File buildPath(File paramFile, String... paramVarArgs) {
    int i = paramVarArgs.length;
    for (byte b = 0; b < i; b++) {
      String str = paramVarArgs[b];
      if (paramFile == null) {
        paramFile = new File(str);
      } else {
        paramFile = new File(paramFile, str);
      } 
    } 
    return paramFile;
  }
  
  public static void chmodPackageDictionary(File paramFile) {
    try {
      if (Build.VERSION.SDK_INT >= 21) {
        if (FileUtils.isSymlink(paramFile))
          return; 
        FileUtils.chmod(paramFile.getParentFile().getAbsolutePath(), 493);
        FileUtils.chmod(paramFile.getAbsolutePath(), 493);
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public static File getAccountConfigFile() {
    return new File(DATA_APP_SYSTEM_DIRECTORY, "account-list.ini");
  }
  
  public static File getAccountVisibilityConfigFile() {
    return new File(DATA_APP_SYSTEM_DIRECTORY, "account-visibility-list.ini");
  }
  
  public static File getBakUidListFile() {
    return new File(DATA_APP_SYSTEM_DIRECTORY, "uid-list.ini.bak");
  }
  
  private static Context getContext() {
    return VirtualCore.get().getContext();
  }
  
  public static File getDataAppDirectory() {
    return DATA_APP_DIRECTORY;
  }
  
  public static File getDataAppDirectoryExt() {
    return DATA_APP_DIRECTORY_EXT;
  }
  
  public static File getDataAppLibDirectory(String paramString) {
    return buildPath(getDataAppPackageDirectory(paramString), new String[] { "lib" });
  }
  
  public static File getDataAppLibDirectoryExt(String paramString) {
    return buildPath(getDataAppPackageDirectoryExt(paramString), new String[] { "lib" });
  }
  
  public static File getDataAppPackageDirectory(String paramString) {
    return buildPath(DATA_APP_DIRECTORY, new String[] { paramString });
  }
  
  public static File getDataAppPackageDirectoryExt(String paramString) {
    return buildPath(DATA_APP_DIRECTORY_EXT, new String[] { paramString });
  }
  
  public static File getDataDirectory() {
    return DATA_DIRECTORY;
  }
  
  public static File getDataSystemDirectory(int paramInt) {
    return buildPath(getDataUserDirectory(paramInt), new String[] { "system" });
  }
  
  public static File getDataSystemDirectoryExt(int paramInt) {
    return buildPath(getDataUserDirectoryExt(paramInt), new String[] { "system" });
  }
  
  public static File getDataUserDeDirectory() {
    return DATA_USER_DE_DIRECTORY;
  }
  
  public static File getDataUserDeDirectory(int paramInt) {
    return buildPath(DATA_USER_DE_DIRECTORY, new String[] { String.valueOf(paramInt) });
  }
  
  public static File getDataUserDirectory() {
    return DATA_USER_DIRECTORY;
  }
  
  public static File getDataUserDirectory(int paramInt) {
    return buildPath(DATA_USER_DIRECTORY, new String[] { String.valueOf(paramInt) });
  }
  
  public static File getDataUserDirectoryExt() {
    return DATA_USER_DIRECTORY_EXT;
  }
  
  public static File getDataUserDirectoryExt(int paramInt) {
    return buildPath(DATA_USER_DIRECTORY_EXT, new String[] { String.valueOf(paramInt) });
  }
  
  public static File getDataUserPackageDirectory(int paramInt, String paramString) {
    return buildPath(getDataUserDirectory(paramInt), new String[] { paramString });
  }
  
  public static File getDataUserPackageDirectoryExt(int paramInt, String paramString) {
    return buildPath(getDataUserDirectoryExt(paramInt), new String[] { paramString });
  }
  
  public static File getDeDataUserDirectoryExt() {
    return DATA_USER_DE_DIRECTORY_EXT;
  }
  
  public static File getDeDataUserDirectoryExt(int paramInt) {
    return buildPath(DATA_USER_DE_DIRECTORY_EXT, new String[] { String.valueOf(paramInt) });
  }
  
  public static File getDeDataUserPackageDirectory(int paramInt, String paramString) {
    return buildPath(getDataUserDeDirectory(paramInt), new String[] { paramString });
  }
  
  public static File getDeDataUserPackageDirectoryExt(int paramInt, String paramString) {
    return buildPath(getDeDataUserDirectoryExt(paramInt), new String[] { paramString });
  }
  
  public static File getDeDataUserPackageDirectoryExtRoot(int paramInt) {
    return buildPath(getDeDataUserDirectoryExt(paramInt), new String[0]);
  }
  
  public static File getDeDataUserPackageDirectoryRoot(int paramInt) {
    return buildPath(getDataUserDeDirectory(paramInt), new String[0]);
  }
  
  public static File getDeviceInfoFile() {
    return new File(DATA_APP_SYSTEM_DIRECTORY, "device-config.ini");
  }
  
  public static File getFrameworkDirectory() {
    return FRAMEWORK_DIRECTORY;
  }
  
  public static File getFrameworkDirectory(String paramString) {
    return buildPath(FRAMEWORK_DIRECTORY, new String[] { paramString });
  }
  
  public static File getFrameworkDirectoryExt() {
    return FRAMEWORK_DIRECTORY_EXT;
  }
  
  public static File getFrameworkFile(String paramString) {
    return new File(getFrameworkDirectory(paramString), "extracted.jar");
  }
  
  public static File getJobConfigFile() {
    return new File(DATA_APP_SYSTEM_DIRECTORY, "job-list.ini");
  }
  
  public static File getNativeCacheDir(boolean paramBoolean) {
    File file;
    if (paramBoolean) {
      file = ROOT_EXT;
    } else {
      file = ROOT;
    } 
    return buildPath(file, new String[] { ".native" });
  }
  
  public static File getOatDirectory(String paramString) {
    return buildPath(getDataAppPackageDirectory(paramString), new String[] { "oat" });
  }
  
  public static File getOatDirectoryExt(String paramString) {
    return buildPath(getDataAppPackageDirectoryExt(paramString), new String[] { "oat" });
  }
  
  public static File getOatFile(String paramString1, String paramString2) {
    return buildPath(getOatDirectory(paramString1), new String[] { paramString2, "base.odex" });
  }
  
  public static File getOatFileExt(String paramString1, String paramString2) {
    return buildPath(getOatDirectoryExt(paramString1), new String[] { paramString2, "base.odex" });
  }
  
  public static File getOptimizedFrameworkFile(String paramString) {
    return new File(getFrameworkDirectory(paramString), "classes.dex");
  }
  
  public static File getPackageCacheFile(String paramString) {
    return new File(getDataAppPackageDirectory(paramString), "package.ini");
  }
  
  public static File getPackageFile(String paramString) {
    return new File(getDataAppPackageDirectory(paramString), EncodeUtils.decodeBase64("YmFzZS5hcGs="));
  }
  
  public static File getPackageFileExt(String paramString) {
    return new File(getDataAppPackageDirectoryExt(paramString), EncodeUtils.decodeBase64("YmFzZS5hcGs="));
  }
  
  public static String getPackageFileStub(String paramString) {
    return BuildCompat.isOreo() ? String.format(EncodeUtils.decodeBase64("L2RhdGEvYXBwLyVzLSVzL2Jhc2UuYXBr"), new Object[] { paramString, Base64.encodeToString(paramString.getBytes(), 10) }) : String.format(EncodeUtils.decodeBase64("L2RhdGEvYXBwLyVzLTEvYmFzZS5hcGs="), new Object[] { paramString });
  }
  
  public static File getPackageInstallerStageDir() {
    return buildPath(DATA_APP_SYSTEM_DIRECTORY, new String[] { ".session_dir" });
  }
  
  public static File getPackageListFile() {
    return new File(DATA_APP_SYSTEM_DIRECTORY, "packages.ini");
  }
  
  public static File getRoot() {
    return ROOT;
  }
  
  public static File getRootExt() {
    return ROOT_EXT;
  }
  
  public static File getSignatureFile(String paramString) {
    return new File(getDataAppPackageDirectory(paramString), "signature.ini");
  }
  
  public static String getSplitFileName(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("split_");
    stringBuilder.append(paramString);
    stringBuilder.append(".apk");
    return stringBuilder.toString();
  }
  
  public static File getSplitPackageFile(String paramString1, String paramString2) {
    return new File(getDataAppPackageDirectory(paramString1), getSplitFileName(paramString2));
  }
  
  public static File getSplitPackageFileExt(String paramString1, String paramString2) {
    return new File(getDataAppPackageDirectoryExt(paramString1), getSplitFileName(paramString2));
  }
  
  public static File getSyncDirectory() {
    return buildPath(DATA_APP_SYSTEM_DIRECTORY, new String[] { "sync" });
  }
  
  public static File getUidListFile() {
    return new File(DATA_APP_SYSTEM_DIRECTORY, "uid-list.ini");
  }
  
  public static File getUserAppLibDirectory(int paramInt, String paramString) {
    return new File(getDataUserPackageDirectory(paramInt, paramString), "lib");
  }
  
  public static File getUserAppLibDirectoryExt(int paramInt, String paramString) {
    return new File(getDataUserPackageDirectoryExt(paramInt, paramString), "lib");
  }
  
  public static File getVSConfigFile() {
    return new File(DATA_APP_SYSTEM_DIRECTORY, "vss.ini");
  }
  
  public static File getVirtualLocationFile() {
    return new File(DATA_APP_SYSTEM_DIRECTORY, "virtual-loc.ini");
  }
  
  public static File getWifiMacFile(int paramInt, boolean paramBoolean) {
    return paramBoolean ? new File(getDataSystemDirectoryExt(paramInt), "wifiMacAddress") : new File(getDataSystemDirectory(paramInt), "wifiMacAddress");
  }
  
  public static void systemReady() {
    FileUtils.ensureDirCreate(DATA_DIRECTORY);
    FileUtils.ensureDirCreate(DATA_APP_DIRECTORY);
    FileUtils.ensureDirCreate(DATA_APP_SYSTEM_DIRECTORY);
    FileUtils.ensureDirCreate(DATA_USER_DIRECTORY);
    FileUtils.ensureDirCreate(DATA_USER_DE_DIRECTORY);
    FileUtils.ensureDirCreate(FRAMEWORK_DIRECTORY);
    FileUtils.chmod(ROOT.getAbsolutePath(), 493);
    FileUtils.chmod(DATA_DIRECTORY.getAbsolutePath(), 493);
    FileUtils.chmod(DATA_APP_DIRECTORY.getAbsolutePath(), 493);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\os\VEnvironment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */