package com.android.multidex;

import com.android.dx.cf.attrib.AttRuntimeVisibleAnnotations;
import com.android.dx.cf.direct.DirectClassFile;
import com.android.dx.cf.iface.Attribute;
import com.android.dx.cf.iface.FieldList;
import com.android.dx.cf.iface.HasAttribute;
import com.android.dx.cf.iface.MethodList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipFile;

public class MainDexListBuilder {
  private static final String CLASS_EXTENSION = ".class";
  
  private static final String DISABLE_ANNOTATION_RESOLUTION_WORKAROUND = "--disable-annotation-resolution-workaround";
  
  private static final String EOL = System.getProperty("line.separator");
  
  private static final int STATUS_ERROR = 1;
  
  private static final String USAGE_MESSAGE;
  
  private Set<String> filesToKeep;
  
  static {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Usage:");
    stringBuilder.append(EOL);
    stringBuilder.append(EOL);
    stringBuilder.append("Short version: Don't use this.");
    stringBuilder.append(EOL);
    stringBuilder.append(EOL);
    stringBuilder.append("Slightly longer version: This tool is used by mainDexClasses script to build");
    stringBuilder.append(EOL);
    stringBuilder.append("the main dex list.");
    stringBuilder.append(EOL);
    USAGE_MESSAGE = stringBuilder.toString();
  }
  
  public MainDexListBuilder(boolean paramBoolean, String paramString1, String paramString2) throws IOException {
    StringBuilder stringBuilder2;
    this.filesToKeep = new HashSet<String>();
    StringBuilder stringBuilder1 = null;
    try {
      ZipFile zipFile = new ZipFile();
    } catch (IOException iOException1) {
      IOException iOException2 = new IOException();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("\"");
      stringBuilder.append(paramString1);
      stringBuilder.append("\" can not be read as a zip archive. (");
      stringBuilder.append(iOException1.getMessage());
      stringBuilder.append(")");
      this(stringBuilder.toString(), iOException1);
      throw iOException2;
    } finally {
      paramString2 = null;
      paramString1 = null;
    } 
    try {
      stringBuilder2.close();
    } catch (IOException iOException) {}
    if (paramString1 != null)
      for (ClassPathElement classPathElement : ((Path)paramString1).elements) {
        try {
          classPathElement.close();
        } catch (IOException iOException) {}
      }  
    throw paramString2;
  }
  
  private boolean hasRuntimeVisibleAnnotation(HasAttribute paramHasAttribute) {
    boolean bool;
    Attribute attribute = paramHasAttribute.getAttributes().findFirst("RuntimeVisibleAnnotations");
    if (attribute != null && ((AttRuntimeVisibleAnnotations)attribute).getAnnotations().size() > 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private void keepAnnotated(Path paramPath) throws FileNotFoundException {
    Iterator<ClassPathElement> iterator = paramPath.getElements().iterator();
    while (iterator.hasNext()) {
      label29: for (String str : ((ClassPathElement)iterator.next()).list()) {
        if (str.endsWith(".class")) {
          DirectClassFile directClassFile = paramPath.getClass(str);
          if (hasRuntimeVisibleAnnotation((HasAttribute)directClassFile)) {
            this.filesToKeep.add(str);
            continue;
          } 
          MethodList methodList = directClassFile.getMethods();
          boolean bool = false;
          byte b;
          for (b = 0; b < methodList.size(); b++) {
            if (hasRuntimeVisibleAnnotation((HasAttribute)methodList.get(b))) {
              this.filesToKeep.add(str);
              continue label29;
            } 
          } 
          FieldList fieldList = directClassFile.getFields();
          for (b = bool; b < fieldList.size(); b++) {
            if (hasRuntimeVisibleAnnotation((HasAttribute)fieldList.get(b))) {
              this.filesToKeep.add(str);
              break;
            } 
          } 
        } 
      } 
    } 
  }
  
  public static void main(String[] paramArrayOfString) {
    byte b = 0;
    boolean bool = true;
    while (b < paramArrayOfString.length - 2) {
      if (paramArrayOfString[b].equals("--disable-annotation-resolution-workaround")) {
        bool = false;
      } else {
        PrintStream printStream = System.err;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid option ");
        stringBuilder.append(paramArrayOfString[b]);
        printStream.println(stringBuilder.toString());
        printUsage();
        System.exit(1);
      } 
      b++;
    } 
    if (paramArrayOfString.length - b != 2) {
      printUsage();
      System.exit(1);
    } 
    try {
      MainDexListBuilder mainDexListBuilder = new MainDexListBuilder();
      this(bool, paramArrayOfString[b], paramArrayOfString[b + 1]);
      printList(mainDexListBuilder.getMainDexList());
      return;
    } catch (IOException iOException) {
      PrintStream printStream = System.err;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("A fatal error occured: ");
      stringBuilder.append(iOException.getMessage());
      printStream.println(stringBuilder.toString());
      System.exit(1);
      return;
    } 
  }
  
  private static void printList(Set<String> paramSet) {
    for (String str : paramSet)
      System.out.println(str); 
  }
  
  private static void printUsage() {
    System.err.print(USAGE_MESSAGE);
  }
  
  public Set<String> getMainDexList() {
    return this.filesToKeep;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\multidex\MainDexListBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */