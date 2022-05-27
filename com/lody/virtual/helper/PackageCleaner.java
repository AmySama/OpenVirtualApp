package com.lody.virtual.helper;

import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.os.VUserInfo;
import com.lody.virtual.os.VUserManager;
import com.lody.virtual.remote.InstalledAppInfo;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class PackageCleaner {
  public static void cleanAllUserPackage(File paramFile, String paramString) {
    File[] arrayOfFile = paramFile.listFiles();
    if (arrayOfFile == null)
      return; 
    int i = arrayOfFile.length;
    for (byte b = 0; b < i; b++) {
      File file = new File(arrayOfFile[b], paramString);
      if (file.exists())
        FileUtils.deleteDir(file); 
    } 
  }
  
  public static void cleanUninstalledPackages() {
    VirtualCore virtualCore = VirtualCore.get();
    byte b = 0;
    List list = virtualCore.getInstalledApps(0);
    HashSet<String> hashSet = new HashSet(list.size());
    Iterator iterator = list.iterator();
    while (iterator.hasNext())
      hashSet.add(((InstalledAppInfo)iterator.next()).packageName); 
    File[] arrayOfFile = VEnvironment.getDataAppDirectoryExt().listFiles();
    if (arrayOfFile != null) {
      int i = arrayOfFile.length;
      while (b < i) {
        File file = arrayOfFile[b];
        String str = file.getName();
        if (!str.equals("system") && !hashSet.contains(str)) {
          cleanAllUserPackage(VEnvironment.getDataUserDirectoryExt(), str);
          cleanAllUserPackage(VEnvironment.getDeDataUserDirectoryExt(), str);
          FileUtils.deleteDir(file);
        } 
        b++;
      } 
    } 
  }
  
  public static void cleanUsers(File paramFile) {
    List list = VUserManager.get().getUsers();
    HashSet<Integer> hashSet = new HashSet(list.size());
    Iterator iterator = list.iterator();
    while (iterator.hasNext())
      hashSet.add(Integer.valueOf(((VUserInfo)iterator.next()).id)); 
    File[] arrayOfFile = paramFile.listFiles();
    if (arrayOfFile == null)
      return; 
    int i = arrayOfFile.length;
    for (byte b = 0; b < i; b++) {
      boolean bool;
      File file = arrayOfFile[b];
      try {
        bool = hashSet.contains(Integer.valueOf(Integer.parseInt(file.getName())));
      } catch (NumberFormatException numberFormatException) {
        numberFormatException.printStackTrace();
        bool = false;
      } 
      if (!bool)
        FileUtils.deleteDir(file); 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\PackageCleaner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */