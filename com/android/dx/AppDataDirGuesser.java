package com.android.dx;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

class AppDataDirGuesser {
  public static final int PER_USER_RANGE = 100000;
  
  private String getPathFromThisClassLoader(ClassLoader paramClassLoader, Class<?> paramClass) {
    try {
      Field field = paramClass.getDeclaredField("path");
      field.setAccessible(true);
      return (String)field.get(paramClassLoader);
    } catch (NoSuchFieldException|IllegalAccessException|ClassCastException noSuchFieldException) {
      return processClassLoaderString(paramClassLoader.toString());
    } 
  }
  
  private File getWriteableDirectory(String paramString) {
    File file = new File(paramString);
    if (!isWriteableDirectory(file))
      file = null; 
    return file;
  }
  
  private ClassLoader guessSuitableClassLoader() {
    return AppDataDirGuesser.class.getClassLoader();
  }
  
  static String processClassLoaderString(String paramString) {
    return paramString.contains("DexPathList") ? processClassLoaderString43OrLater(paramString) : processClassLoaderString42OrEarlier(paramString);
  }
  
  private static String processClassLoaderString42OrEarlier(String paramString) {
    int i = paramString.lastIndexOf('[');
    if (i != -1)
      paramString = paramString.substring(i + 1); 
    i = paramString.indexOf(']');
    if (i != -1)
      paramString = paramString.substring(0, i); 
    return paramString;
  }
  
  private static String processClassLoaderString43OrLater(String paramString) {
    int i = paramString.indexOf("DexPathList") + 11;
    String str = paramString;
    if (paramString.length() > i + 4) {
      String str1 = paramString.substring(i);
      i = str1.indexOf(']');
      boolean bool = false;
      str = paramString;
      if (str1.charAt(0) == '[') {
        str = paramString;
        if (str1.charAt(1) == '[') {
          str = paramString;
          if (i >= 0) {
            String[] arrayOfString = str1.substring(2, i).split(",");
            for (i = 0; i < arrayOfString.length; i++) {
              int k = arrayOfString[i].indexOf('"');
              int m = arrayOfString[i].lastIndexOf('"');
              if (k > 0 && k < m)
                arrayOfString[i] = arrayOfString[i].substring(k + 1, m); 
            } 
            StringBuilder stringBuilder = new StringBuilder();
            int j = arrayOfString.length;
            for (i = bool; i < j; i++) {
              paramString = arrayOfString[i];
              if (stringBuilder.length() > 0)
                stringBuilder.append(':'); 
              stringBuilder.append(paramString);
            } 
            str = stringBuilder.toString();
          } 
        } 
      } 
    } 
    return str;
  }
  
  static String[] splitPathList(String paramString) {
    String str = paramString;
    if (paramString.startsWith("dexPath=")) {
      int i = paramString.indexOf(',');
      if (i == -1) {
        str = paramString.substring(8);
      } else {
        str = paramString.substring(8, i);
      } 
    } 
    return str.split(":");
  }
  
  boolean fileOrDirExists(File paramFile) {
    return paramFile.exists();
  }
  
  Integer getProcessUid() {
    try {
      return (Integer)Class.forName("android.os.Process").getMethod("myUid", new Class[0]).invoke(null, new Object[0]);
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public File guess() {
    try {
      ClassLoader classLoader = guessSuitableClassLoader();
      Class<?> clazz = Class.forName("dalvik.system.PathClassLoader");
      clazz.cast(classLoader);
      File[] arrayOfFile = guessPath(getPathFromThisClassLoader(classLoader, clazz));
      if (arrayOfFile.length > 0)
        return arrayOfFile[0]; 
    } catch (ClassCastException|ClassNotFoundException classCastException) {}
    return null;
  }
  
  File[] guessPath(String paramString) {
    ArrayList<File> arrayList = new ArrayList();
    for (String paramString : splitPathList(paramString)) {
      if (paramString.startsWith("/data/app/")) {
        int i = paramString.lastIndexOf(".apk");
        if (i == paramString.length() - 4) {
          int j = paramString.indexOf("-");
          if (j != -1)
            i = j; 
          String str = paramString.substring(10, i);
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("/data/data/");
          stringBuilder.append(str);
          File file2 = getWriteableDirectory(stringBuilder.toString());
          File file1 = file2;
          if (file2 == null)
            file1 = guessUserDataDirectory(str); 
          if (file1 != null) {
            file1 = new File(file1, "cache");
            if ((fileOrDirExists(file1) || file1.mkdir()) && isWriteableDirectory(file1))
              arrayList.add(file1); 
          } 
        } 
      } 
    } 
    return arrayList.<File>toArray(new File[arrayList.size()]);
  }
  
  File guessUserDataDirectory(String paramString) {
    Integer integer = getProcessUid();
    return (integer == null) ? null : getWriteableDirectory(String.format("/data/user/%d/%s", new Object[] { Integer.valueOf(integer.intValue() / 100000), paramString }));
  }
  
  boolean isWriteableDirectory(File paramFile) {
    boolean bool;
    if (paramFile.isDirectory() && paramFile.canWrite()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\AppDataDirGuesser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */