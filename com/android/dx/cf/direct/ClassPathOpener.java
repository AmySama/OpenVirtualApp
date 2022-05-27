package com.android.dx.cf.direct;

import com.android.dex.util.FileUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassPathOpener {
  public static final FileNameFilter acceptAll = new FileNameFilter() {
      public boolean accept(String param1String) {
        return true;
      }
    };
  
  private final Consumer consumer;
  
  private FileNameFilter filter;
  
  private final String pathname;
  
  private final boolean sort;
  
  public ClassPathOpener(String paramString, boolean paramBoolean, Consumer paramConsumer) {
    this(paramString, paramBoolean, acceptAll, paramConsumer);
  }
  
  public ClassPathOpener(String paramString, boolean paramBoolean, FileNameFilter paramFileNameFilter, Consumer paramConsumer) {
    this.pathname = paramString;
    this.sort = paramBoolean;
    this.consumer = paramConsumer;
    this.filter = paramFileNameFilter;
  }
  
  private static int compareClassNames(String paramString1, String paramString2) {
    paramString1 = paramString1.replace('$', '0');
    paramString2 = paramString2.replace('$', '0');
    return paramString1.replace("package-info", "").compareTo(paramString2.replace("package-info", ""));
  }
  
  private boolean processArchive(File paramFile) throws IOException {
    ZipFile zipFile = new ZipFile(paramFile);
    ArrayList<? extends ZipEntry> arrayList = Collections.list(zipFile.entries());
    if (this.sort)
      Collections.sort(arrayList, new Comparator<ZipEntry>() {
            public int compare(ZipEntry param1ZipEntry1, ZipEntry param1ZipEntry2) {
              return ClassPathOpener.compareClassNames(param1ZipEntry1.getName(), param1ZipEntry2.getName());
            }
          }); 
    this.consumer.onProcessArchiveStart(paramFile);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(40000);
    byte[] arrayOfByte = new byte[20000];
    Iterator<? extends ZipEntry> iterator = arrayList.iterator();
    boolean bool = false;
    while (iterator.hasNext()) {
      ZipEntry zipEntry = iterator.next();
      boolean bool1 = zipEntry.isDirectory();
      String str = zipEntry.getName();
      if (this.filter.accept(str)) {
        byte[] arrayOfByte1;
        if (!bool1) {
          InputStream inputStream = zipFile.getInputStream(zipEntry);
          byteArrayOutputStream.reset();
          while (true) {
            int i = inputStream.read(arrayOfByte);
            if (i != -1) {
              byteArrayOutputStream.write(arrayOfByte, 0, i);
              continue;
            } 
            inputStream.close();
            arrayOfByte1 = byteArrayOutputStream.toByteArray();
            break;
          } 
        } else {
          arrayOfByte1 = new byte[0];
        } 
        bool |= this.consumer.processFileBytes(str, zipEntry.getTime(), arrayOfByte1);
      } 
    } 
    zipFile.close();
    return bool;
  }
  
  private boolean processDirectory(File paramFile, boolean paramBoolean) {
    File file = paramFile;
    if (paramBoolean)
      file = new File(paramFile, "."); 
    File[] arrayOfFile = file.listFiles();
    int i = arrayOfFile.length;
    if (this.sort)
      Arrays.sort(arrayOfFile, new Comparator<File>() {
            public int compare(File param1File1, File param1File2) {
              return ClassPathOpener.compareClassNames(param1File1.getName(), param1File2.getName());
            }
          }); 
    byte b = 0;
    paramBoolean = false;
    while (b < i) {
      paramBoolean |= processOne(arrayOfFile[b], false);
      b++;
    } 
    return paramBoolean;
  }
  
  private boolean processOne(File paramFile, boolean paramBoolean) {
    try {
      if (paramFile.isDirectory())
        return processDirectory(paramFile, paramBoolean); 
      String str = paramFile.getPath();
      if (str.endsWith(".zip") || str.endsWith(".jar") || str.endsWith(".apk"))
        return processArchive(paramFile); 
      if (this.filter.accept(str)) {
        byte[] arrayOfByte = FileUtils.readFile(paramFile);
        return this.consumer.processFileBytes(str, paramFile.lastModified(), arrayOfByte);
      } 
      return false;
    } catch (Exception exception) {
      this.consumer.onException(exception);
      return false;
    } 
  }
  
  public boolean process() {
    return processOne(new File(this.pathname), true);
  }
  
  public static interface Consumer {
    void onException(Exception param1Exception);
    
    void onProcessArchiveStart(File param1File);
    
    boolean processFileBytes(String param1String, long param1Long, byte[] param1ArrayOfbyte);
  }
  
  public static interface FileNameFilter {
    boolean accept(String param1String);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\direct\ClassPathOpener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */