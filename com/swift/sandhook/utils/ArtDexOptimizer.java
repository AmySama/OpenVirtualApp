package com.swift.sandhook.utils;

import android.os.Build;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.SandHookConfig;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ArtDexOptimizer {
  public static void dexoatAndDisableInline(String paramString1, String paramString2) throws IOException {
    String str;
    File file = new File(paramString2);
    if (!file.exists())
      file.getParentFile().mkdirs(); 
    ArrayList<String> arrayList = new ArrayList();
    arrayList.add("dex2oat");
    if (SandHookConfig.SDK_INT >= 24) {
      arrayList.add("--runtime-arg");
      arrayList.add("-classpath");
      arrayList.add("--runtime-arg");
      arrayList.add("&");
    } 
    StringBuilder stringBuilder3 = new StringBuilder();
    stringBuilder3.append("--dex-file=");
    stringBuilder3.append(paramString1);
    arrayList.add(stringBuilder3.toString());
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("--oat-file=");
    stringBuilder1.append(paramString2);
    arrayList.add(stringBuilder1.toString());
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("--instruction-set=");
    if (SandHook.is64Bit()) {
      str = "arm64";
    } else {
      str = "arm";
    } 
    stringBuilder2.append(str);
    arrayList.add(stringBuilder2.toString());
    arrayList.add("--compiler-filter=everything");
    if (SandHookConfig.SDK_INT >= 22 && SandHookConfig.SDK_INT < 29)
      arrayList.add("--compile-pic"); 
    if (SandHookConfig.SDK_INT > 25) {
      arrayList.add("--inline-max-code-units=0");
    } else if (Build.VERSION.SDK_INT >= 23) {
      arrayList.add("--inline-depth-limit=0");
    } 
    ProcessBuilder processBuilder = new ProcessBuilder(arrayList);
    processBuilder.redirectErrorStream(true);
    Process process = processBuilder.start();
    StreamConsumer.consumeInputStream(process.getInputStream());
    StreamConsumer.consumeInputStream(process.getErrorStream());
    try {
      int i = process.waitFor();
      if (i == 0)
        return; 
      IOException iOException = new IOException();
      stringBuilder2 = new StringBuilder();
      this();
      stringBuilder2.append("dex2oat works unsuccessfully, exit code: ");
      stringBuilder2.append(i);
      this(stringBuilder2.toString());
      throw iOException;
    } catch (InterruptedException interruptedException) {
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append("dex2oat is interrupted, msg: ");
      stringBuilder2.append(interruptedException.getMessage());
      throw new IOException(stringBuilder2.toString(), interruptedException);
    } 
  }
  
  private static class StreamConsumer {
    static final Executor STREAM_CONSUMER = Executors.newSingleThreadExecutor();
    
    static void consumeInputStream(final InputStream is) {
      STREAM_CONSUMER.execute(new Runnable() {
            public void run() {
              if (is == null)
                return; 
              null = new byte[256];
              try {
                while (true) {
                  int i = is.read(null);
                  if (i > 0)
                    continue; 
                  break;
                } 
              } catch (IOException iOException) {
              
              } finally {
                try {
                  is.close();
                } catch (Exception exception) {}
              } 
              try {
                is.close();
              } catch (Exception exception) {}
            }
          });
    }
  }
  
  class null implements Runnable {
    public void run() {
      if (is == null)
        return; 
      null = new byte[256];
      try {
        while (true) {
          int i = is.read(null);
          if (i > 0)
            continue; 
          break;
        } 
      } catch (IOException iOException) {
      
      } finally {
        try {
          is.close();
        } catch (Exception exception) {}
      } 
      try {
        is.close();
      } catch (Exception exception) {}
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhoo\\utils\ArtDexOptimizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */