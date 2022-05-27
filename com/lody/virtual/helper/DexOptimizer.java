package com.lody.virtual.helper;

import android.os.Build;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.helper.utils.FileUtils;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DexOptimizer {
  public static void dex2oat(String paramString1, String paramString2) throws IOException {
    StringBuilder stringBuilder;
    if (Build.VERSION.SDK_INT < 30) {
      FileUtils.ensureDirCreate((new File(paramString2)).getParentFile());
      ArrayList<String> arrayList = new ArrayList();
      arrayList.add("dex2oat");
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append("--dex-file=");
      stringBuilder2.append(paramString1);
      arrayList.add(stringBuilder2.toString());
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("--oat-file=");
      stringBuilder1.append(paramString2);
      arrayList.add(stringBuilder1.toString());
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("--instruction-set=");
      stringBuilder1.append(VirtualRuntime.getCurrentInstructionSet());
      arrayList.add(stringBuilder1.toString());
      if (Build.VERSION.SDK_INT > 25) {
        arrayList.add("--compiler-filter=quicken");
      } else {
        arrayList.add("--compiler-filter=interpret-only");
      } 
      ProcessBuilder processBuilder = new ProcessBuilder(arrayList);
      processBuilder.redirectErrorStream(true);
      Process process = processBuilder.start();
      StreamConsumer.consumeInputStream(process.getInputStream());
      StreamConsumer.consumeInputStream(process.getErrorStream());
      try {
        int i = process.waitFor();
        if (i != 0) {
          IOException iOException = new IOException();
          stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("dex2oat works unsuccessfully, exit code: ");
          stringBuilder.append(i);
          this(stringBuilder.toString());
          throw iOException;
        } 
      } catch (InterruptedException interruptedException) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("dex2oat is interrupted, msg: ");
        stringBuilder.append(interruptedException.getMessage());
        throw new IOException(stringBuilder.toString(), interruptedException);
      } 
    } else {
      DexFile.loadDex((String)stringBuilder, (String)interruptedException, 0).close();
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\DexOptimizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */