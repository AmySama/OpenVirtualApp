package com.swift.sandhook.xposedcompat.utils;

import android.os.Build;
import android.text.TextUtils;
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
      DexLog.e("getPackageName using empty dataDir");
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
      paramString = null;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("error writing line to file ");
      stringBuilder.append(paramFile);
      stringBuilder.append(": ");
      stringBuilder.append(paramString.getMessage());
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\xposedcompa\\utils\FileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */