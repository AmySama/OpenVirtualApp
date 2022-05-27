package com.lody.virtual.server.pm;

import android.util.Xml;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.helper.utils.VLog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class SystemConfig {
  private static final String TAG = "SystemConfig";
  
  private final Map<String, SharedLibraryEntry> mSharedLibraries = new HashMap<String, SharedLibraryEntry>();
  
  private void readPermissionsFromXml(File paramFile) {
    try {
      FileReader fileReader = new FileReader(paramFile);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Reading permissions from ");
      stringBuilder.append(paramFile);
      VLog.i("SystemConfig", stringBuilder.toString(), new Object[0]);
      XmlPullParser xmlPullParser = Xml.newPullParser();
      try {
        int i;
        xmlPullParser.setInput(fileReader);
        while (true) {
          i = xmlPullParser.next();
          if (i != 2 && i != 1)
            continue; 
          break;
        } 
      } catch (XmlPullParserException|java.io.IOException xmlPullParserException) {
      
      } finally {
        FileUtils.closeQuietly(fileReader);
      } 
      FileUtils.closeQuietly(fileReader);
      return;
    } catch (FileNotFoundException fileNotFoundException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Couldn't find or open permissions file ");
      stringBuilder.append(paramFile);
      VLog.w("SystemConfig", stringBuilder.toString(), new Object[0]);
      return;
    } 
  }
  
  public SharedLibraryEntry getSharedLibrary(String paramString) {
    return this.mSharedLibraries.get(paramString);
  }
  
  public void load() {
    long l1 = System.currentTimeMillis();
    readSharedLibraries(new File("/etc/permissions/"));
    long l2 = System.currentTimeMillis();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("load cost time: ");
    stringBuilder.append(l2 - l1);
    stringBuilder.append("ms.");
    VLog.e("SystemConfig", stringBuilder.toString());
  }
  
  public void readSharedLibraries(File paramFile) {
    File[] arrayOfFile = paramFile.listFiles();
    if (arrayOfFile == null)
      return; 
    int i = arrayOfFile.length;
    for (byte b = 0; b < i; b++) {
      paramFile = arrayOfFile[b];
      if (paramFile.isFile() && paramFile.getName().endsWith(".xml"))
        readPermissionsFromXml(paramFile); 
    } 
  }
  
  public static class SharedLibraryEntry {
    public String[] dependencies;
    
    public String name;
    
    public String path;
    
    public SharedLibraryEntry(String param1String1, String param1String2, String[] param1ArrayOfString) {
      this.name = param1String1;
      this.path = param1String2;
      this.dependencies = param1ArrayOfString;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\SystemConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */