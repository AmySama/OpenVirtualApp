package android.support.multidex;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

final class MultiDexExtractor implements Closeable {
  private static final int BUFFER_SIZE = 16384;
  
  private static final String DEX_PREFIX = "classes";
  
  static final String DEX_SUFFIX = ".dex";
  
  private static final String EXTRACTED_NAME_EXT = ".classes";
  
  static final String EXTRACTED_SUFFIX = ".zip";
  
  private static final String KEY_CRC = "crc";
  
  private static final String KEY_DEX_CRC = "dex.crc.";
  
  private static final String KEY_DEX_NUMBER = "dex.number";
  
  private static final String KEY_DEX_TIME = "dex.time.";
  
  private static final String KEY_TIME_STAMP = "timestamp";
  
  private static final String LOCK_FILENAME = "MultiDex.lock";
  
  private static final int MAX_EXTRACT_ATTEMPTS = 3;
  
  private static final long NO_VALUE = -1L;
  
  private static final String PREFS_FILE = "multidex.version";
  
  private static final String TAG = "MultiDex";
  
  private final FileLock cacheLock;
  
  private final File dexDir;
  
  private final FileChannel lockChannel;
  
  private final RandomAccessFile lockRaf;
  
  private final File sourceApk;
  
  private final long sourceCrc;
  
  MultiDexExtractor(File paramFile1, File paramFile2) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("MultiDexExtractor(");
    stringBuilder.append(paramFile1.getPath());
    stringBuilder.append(", ");
    stringBuilder.append(paramFile2.getPath());
    stringBuilder.append(")");
    Log.i("MultiDex", stringBuilder.toString());
    this.sourceApk = paramFile1;
    this.dexDir = paramFile2;
    this.sourceCrc = getZipCrc(paramFile1);
    paramFile1 = new File(paramFile2, "MultiDex.lock");
    RandomAccessFile randomAccessFile = new RandomAccessFile(paramFile1, "rw");
    this.lockRaf = randomAccessFile;
    try {
      this.lockChannel = randomAccessFile.getChannel();
      try {
        StringBuilder stringBuilder1 = new StringBuilder();
        this();
        stringBuilder1.append("Blocking on lock ");
        stringBuilder1.append(paramFile1.getPath());
        Log.i("MultiDex", stringBuilder1.toString());
        this.cacheLock = this.lockChannel.lock();
        stringBuilder1 = new StringBuilder();
        this();
        stringBuilder1.append(paramFile1.getPath());
        stringBuilder1.append(" locked");
        Log.i("MultiDex", stringBuilder1.toString());
        return;
      } catch (IOException iOException) {
      
      } catch (RuntimeException runtimeException) {
      
      } catch (Error null) {}
      closeQuietly(this.lockChannel);
      throw error;
    } catch (IOException iOException) {
    
    } catch (RuntimeException runtimeException) {
    
    } catch (Error error) {}
    closeQuietly(this.lockRaf);
    throw error;
  }
  
  private void clearDexDir() {
    File[] arrayOfFile = this.dexDir.listFiles(new FileFilter() {
          public boolean accept(File param1File) {
            return param1File.getName().equals("MultiDex.lock") ^ true;
          }
        });
    if (arrayOfFile == null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Failed to list secondary dex dir content (");
      stringBuilder.append(this.dexDir.getPath());
      stringBuilder.append(").");
      Log.w("MultiDex", stringBuilder.toString());
      return;
    } 
    int i = arrayOfFile.length;
    for (byte b = 0; b < i; b++) {
      File file = arrayOfFile[b];
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Trying to delete old file ");
      stringBuilder.append(file.getPath());
      stringBuilder.append(" of size ");
      stringBuilder.append(file.length());
      Log.i("MultiDex", stringBuilder.toString());
      if (!file.delete()) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to delete old file ");
        stringBuilder.append(file.getPath());
        Log.w("MultiDex", stringBuilder.toString());
      } else {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Deleted old file ");
        stringBuilder.append(file.getPath());
        Log.i("MultiDex", stringBuilder.toString());
      } 
    } 
  }
  
  private static void closeQuietly(Closeable paramCloseable) {
    try {
      paramCloseable.close();
    } catch (IOException iOException) {
      Log.w("MultiDex", "Failed to close resource", iOException);
    } 
  }
  
  private static void extract(ZipFile paramZipFile, ZipEntry paramZipEntry, File paramFile, String paramString) throws IOException, FileNotFoundException {
    InputStream inputStream = paramZipFile.getInputStream(paramZipEntry);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("tmp-");
    stringBuilder.append(paramString);
    File file = File.createTempFile(stringBuilder.toString(), ".zip", paramFile.getParentFile());
    stringBuilder = new StringBuilder();
    stringBuilder.append("Extracting ");
    stringBuilder.append(file.getPath());
    Log.i("MultiDex", stringBuilder.toString());
    try {
      StringBuilder stringBuilder1;
      ZipOutputStream zipOutputStream = new ZipOutputStream();
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream();
      FileOutputStream fileOutputStream = new FileOutputStream();
      this(file);
      this(fileOutputStream);
    } finally {
      closeQuietly(inputStream);
      file.delete();
    } 
  }
  
  private static SharedPreferences getMultiDexPreferences(Context paramContext) {
    byte b;
    if (Build.VERSION.SDK_INT < 11) {
      b = 0;
    } else {
      b = 4;
    } 
    return paramContext.getSharedPreferences("multidex.version", b);
  }
  
  private static long getTimeStamp(File paramFile) {
    long l1 = paramFile.lastModified();
    long l2 = l1;
    if (l1 == -1L)
      l2 = l1 - 1L; 
    return l2;
  }
  
  private static long getZipCrc(File paramFile) throws IOException {
    long l1 = ZipUtil.getZipCrc(paramFile);
    long l2 = l1;
    if (l1 == -1L)
      l2 = l1 - 1L; 
    return l2;
  }
  
  private static boolean isModified(Context paramContext, File paramFile, long paramLong, String paramString) {
    SharedPreferences sharedPreferences = getMultiDexPreferences(paramContext);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("timestamp");
    if (sharedPreferences.getLong(stringBuilder.toString(), -1L) == getTimeStamp(paramFile)) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString);
      stringBuilder1.append("crc");
      return (sharedPreferences.getLong(stringBuilder1.toString(), -1L) != paramLong);
    } 
    return true;
  }
  
  private List<ExtractedDex> loadExistingExtractions(Context paramContext, String paramString) throws IOException {
    Log.i("MultiDex", "loading existing secondary dex files");
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(this.sourceApk.getName());
    stringBuilder1.append(".classes");
    String str = stringBuilder1.toString();
    SharedPreferences sharedPreferences = getMultiDexPreferences(paramContext);
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString);
    stringBuilder2.append("dex.number");
    int i = sharedPreferences.getInt(stringBuilder2.toString(), 1);
    ArrayList<ExtractedDex> arrayList = new ArrayList(i - 1);
    byte b = 2;
    while (b <= i) {
      StringBuilder stringBuilder4 = new StringBuilder();
      stringBuilder4.append(str);
      stringBuilder4.append(b);
      stringBuilder4.append(".zip");
      String str1 = stringBuilder4.toString();
      ExtractedDex extractedDex = new ExtractedDex(this.dexDir, str1);
      if (extractedDex.isFile()) {
        extractedDex.crc = getZipCrc(extractedDex);
        StringBuilder stringBuilder6 = new StringBuilder();
        stringBuilder6.append(paramString);
        stringBuilder6.append("dex.crc.");
        stringBuilder6.append(b);
        long l1 = sharedPreferences.getLong(stringBuilder6.toString(), -1L);
        stringBuilder6 = new StringBuilder();
        stringBuilder6.append(paramString);
        stringBuilder6.append("dex.time.");
        stringBuilder6.append(b);
        long l2 = sharedPreferences.getLong(stringBuilder6.toString(), -1L);
        long l3 = extractedDex.lastModified();
        if (l2 == l3 && l1 == extractedDex.crc) {
          arrayList.add(extractedDex);
          b++;
          continue;
        } 
        StringBuilder stringBuilder5 = new StringBuilder();
        stringBuilder5.append("Invalid extracted dex: ");
        stringBuilder5.append(extractedDex);
        stringBuilder5.append(" (key \"");
        stringBuilder5.append(paramString);
        stringBuilder5.append("\"), expected modification time: ");
        stringBuilder5.append(l2);
        stringBuilder5.append(", modification time: ");
        stringBuilder5.append(l3);
        stringBuilder5.append(", expected crc: ");
        stringBuilder5.append(l1);
        stringBuilder5.append(", file crc: ");
        stringBuilder5.append(extractedDex.crc);
        throw new IOException(stringBuilder5.toString());
      } 
      StringBuilder stringBuilder3 = new StringBuilder();
      stringBuilder3.append("Missing extracted secondary dex file '");
      stringBuilder3.append(extractedDex.getPath());
      stringBuilder3.append("'");
      throw new IOException(stringBuilder3.toString());
    } 
    return arrayList;
  }
  
  private List<ExtractedDex> performExtractions() throws IOException {
    null = new StringBuilder();
    null.append(this.sourceApk.getName());
    null.append(".classes");
    String str = null.toString();
    clearDexDir();
    ArrayList<ExtractedDex> arrayList = new ArrayList();
    ZipFile zipFile = new ZipFile(this.sourceApk);
    try {
      null = new StringBuilder();
      this();
      null.append("classes");
      null.append(2);
      null.append(".dex");
      ZipEntry zipEntry = zipFile.getEntry(null.toString());
      byte b = 2;
      while (zipEntry != null) {
        StringBuilder stringBuilder2 = new StringBuilder();
        this();
        stringBuilder2.append(str);
        stringBuilder2.append(b);
        stringBuilder2.append(".zip");
        String str1 = stringBuilder2.toString();
        ExtractedDex extractedDex = new ExtractedDex();
        this(this.dexDir, str1);
        arrayList.add(extractedDex);
        StringBuilder stringBuilder1 = new StringBuilder();
        this();
        stringBuilder1.append("Extraction is needed for file ");
        stringBuilder1.append(extractedDex);
        Log.i("MultiDex", stringBuilder1.toString());
        byte b1 = 0;
        boolean bool = false;
        while (b1 < 3 && !bool) {
          String str2;
          extract(zipFile, zipEntry, extractedDex, str);
          try {
            extractedDex.crc = getZipCrc(extractedDex);
            bool = true;
          } catch (IOException iOException1) {
            stringBuilder1 = new StringBuilder();
            this();
            stringBuilder1.append("Failed to read crc from ");
            stringBuilder1.append(extractedDex.getAbsolutePath());
            Log.w("MultiDex", stringBuilder1.toString(), iOException1);
            bool = false;
          } 
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("Extraction ");
          if (bool) {
            str2 = "succeeded";
          } else {
            str2 = "failed";
          } 
          stringBuilder.append(str2);
          stringBuilder.append(" '");
          stringBuilder.append(extractedDex.getAbsolutePath());
          stringBuilder.append("': length ");
          stringBuilder.append(extractedDex.length());
          stringBuilder.append(" - crc: ");
          stringBuilder.append(extractedDex.crc);
          Log.i("MultiDex", stringBuilder.toString());
          if (!bool) {
            extractedDex.delete();
            if (extractedDex.exists()) {
              StringBuilder stringBuilder3 = new StringBuilder();
              this();
              stringBuilder3.append("Failed to delete corrupted secondary dex '");
              stringBuilder3.append(extractedDex.getPath());
              stringBuilder3.append("'");
              Log.w("MultiDex", stringBuilder3.toString());
            } 
          } 
          b1++;
        } 
        if (bool) {
          b++;
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("classes");
          stringBuilder.append(b);
          stringBuilder.append(".dex");
          ZipEntry zipEntry1 = zipFile.getEntry(stringBuilder.toString());
          continue;
        } 
        IOException iOException = new IOException();
        stringBuilder1 = new StringBuilder();
        this();
        stringBuilder1.append("Could not create zip file ");
        stringBuilder1.append(extractedDex.getAbsolutePath());
        stringBuilder1.append(" for secondary dex (");
        stringBuilder1.append(b);
        stringBuilder1.append(")");
        this(stringBuilder1.toString());
        throw iOException;
      } 
      return arrayList;
    } finally {
      try {
        zipFile.close();
      } catch (IOException iOException) {
        Log.w("MultiDex", "Failed to close resource", iOException);
      } 
    } 
  }
  
  private static void putStoredApkInfo(Context paramContext, String paramString, long paramLong1, long paramLong2, List<ExtractedDex> paramList) {
    SharedPreferences.Editor editor = getMultiDexPreferences(paramContext).edit();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("timestamp");
    editor.putLong(stringBuilder.toString(), paramLong1);
    stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("crc");
    editor.putLong(stringBuilder.toString(), paramLong2);
    stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("dex.number");
    editor.putInt(stringBuilder.toString(), paramList.size() + 1);
    Iterator<ExtractedDex> iterator = paramList.iterator();
    for (byte b = 2; iterator.hasNext(); b++) {
      ExtractedDex extractedDex = iterator.next();
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString);
      stringBuilder1.append("dex.crc.");
      stringBuilder1.append(b);
      editor.putLong(stringBuilder1.toString(), extractedDex.crc);
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString);
      stringBuilder1.append("dex.time.");
      stringBuilder1.append(b);
      editor.putLong(stringBuilder1.toString(), extractedDex.lastModified());
    } 
    editor.commit();
  }
  
  public void close() throws IOException {
    this.cacheLock.release();
    this.lockChannel.close();
    this.lockRaf.close();
  }
  
  List<? extends File> load(Context paramContext, String paramString, boolean paramBoolean) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("MultiDexExtractor.load(");
    stringBuilder.append(this.sourceApk.getPath());
    stringBuilder.append(", ");
    stringBuilder.append(paramBoolean);
    stringBuilder.append(", ");
    stringBuilder.append(paramString);
    stringBuilder.append(")");
    Log.i("MultiDex", stringBuilder.toString());
    if (this.cacheLock.isValid()) {
      List<ExtractedDex> list;
      if (!paramBoolean && !isModified(paramContext, this.sourceApk, this.sourceCrc, paramString)) {
        try {
          List<ExtractedDex> list1 = loadExistingExtractions(paramContext, paramString);
          list = list1;
        } catch (IOException iOException) {
          Log.w("MultiDex", "Failed to reload existing extracted secondary dex files, falling back to fresh extraction", iOException);
          List<ExtractedDex> list1 = performExtractions();
          putStoredApkInfo((Context)list, paramString, getTimeStamp(this.sourceApk), this.sourceCrc, list1);
          list = list1;
        } 
      } else {
        if (paramBoolean) {
          Log.i("MultiDex", "Forced extraction must be performed.");
        } else {
          Log.i("MultiDex", "Detected that extraction must be performed.");
        } 
        List<ExtractedDex> list1 = performExtractions();
        putStoredApkInfo((Context)list, paramString, getTimeStamp(this.sourceApk), this.sourceCrc, list1);
        list = list1;
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("load found ");
      stringBuilder1.append(list.size());
      stringBuilder1.append(" secondary dex files");
      Log.i("MultiDex", stringBuilder1.toString());
      return (List)list;
    } 
    throw new IllegalStateException("MultiDexExtractor was closed");
  }
  
  private static class ExtractedDex extends File {
    public long crc = -1L;
    
    public ExtractedDex(File param1File, String param1String) {
      super(param1File, param1String);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\multidex\MultiDexExtractor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */