package com.swift.sandhook.utils;

import android.os.Build;
import android.system.Os;
import android.text.TextUtils;
import com.swift.sandhook.HookLog;
import com.swift.sandhook.SandHookConfig;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
  public static final boolean IS_USING_PROTECTED_STORAGE;
  
  static {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 24) {
      bool = true;
    } else {
      bool = false;
    } 
    IS_USING_PROTECTED_STORAGE = bool;
  }
  
  public static void chmod(String paramString, int paramInt) throws Exception {
    String str1;
    if (SandHookConfig.SDK_INT >= 21)
      try {
        Os.chmod(paramString, paramInt);
        return;
      } catch (Exception exception) {} 
    if ((new File(paramString)).isDirectory()) {
      str1 = "chmod  -R ";
    } else {
      str1 = "chmod ";
    } 
    String str2 = String.format("%o", new Object[] { Integer.valueOf(paramInt) });
    Runtime runtime = Runtime.getRuntime();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str1);
    stringBuilder.append(str2);
    stringBuilder.append(" ");
    stringBuilder.append(paramString);
    runtime.exec(stringBuilder.toString()).waitFor();
  }
  
  public static void delete(File paramFile) throws IOException {
    for (File file : paramFile.listFiles()) {
      if (file.isDirectory()) {
        delete(file);
      } else if (!file.delete()) {
        throw new IOException();
      } 
    } 
    if (paramFile.delete())
      return; 
    throw new IOException();
  }
  
  public static String getDataPathPrefix() {
    String str;
    if (IS_USING_PROTECTED_STORAGE) {
      str = "/data/user_de/0/";
    } else {
      str = "/data/data/";
    } 
    return str;
  }
  
  public static String getPackageName(String paramString) {
    if (TextUtils.isEmpty(paramString)) {
      HookLog.e("getPackageName using empty dataDir");
      return "";
    } 
    int i = paramString.lastIndexOf("/");
    return (i < 0) ? paramString : paramString.substring(i + 1);
  }
  
  public static String readLine(File paramFile) {
    try {
      BufferedReader bufferedReader = new BufferedReader();
      FileReader fileReader = new FileReader();
      this(paramFile);
      this(fileReader);
    } finally {
      paramFile = null;
    } 
  }
  
  public static void writeLine(File paramFile, String paramString) {
    try {
      paramFile.createNewFile();
    } catch (IOException iOException) {}
    try {
      BufferedWriter bufferedWriter;
      FileWriter fileWriter;
    } finally {
      Exception exception = null;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("error writing line to file ");
      stringBuilder.append(paramFile);
      stringBuilder.append(": ");
      stringBuilder.append(exception.getMessage());
    } 
  }
  
  public static interface FileMode {
    public static final int MODE_755 = 493;
    
    public static final int MODE_IRGRP = 32;
    
    public static final int MODE_IROTH = 4;
    
    public static final int MODE_IRUSR = 256;
    
    public static final int MODE_ISGID = 1024;
    
    public static final int MODE_ISUID = 2048;
    
    public static final int MODE_ISVTX = 512;
    
    public static final int MODE_IWGRP = 16;
    
    public static final int MODE_IWOTH = 2;
    
    public static final int MODE_IWUSR = 128;
    
    public static final int MODE_IXGRP = 8;
    
    public static final int MODE_IXOTH = 1;
    
    public static final int MODE_IXUSR = 64;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhoo\\utils\FileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */