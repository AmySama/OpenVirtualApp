package com.android.multidex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

class FolderPathElement implements ClassPathElement {
  private final File baseFolder;
  
  public FolderPathElement(File paramFile) {
    this.baseFolder = paramFile;
  }
  
  private void collect(File paramFile, String paramString, ArrayList<String> paramArrayList) {
    for (File paramFile : paramFile.listFiles()) {
      if (paramFile.isDirectory()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paramString);
        stringBuilder.append('/');
        stringBuilder.append(paramFile.getName());
        collect(paramFile, stringBuilder.toString(), paramArrayList);
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paramString);
        stringBuilder.append('/');
        stringBuilder.append(paramFile.getName());
        paramArrayList.add(stringBuilder.toString());
      } 
    } 
  }
  
  public void close() {}
  
  public Iterable<String> list() {
    ArrayList<String> arrayList = new ArrayList();
    collect(this.baseFolder, "", arrayList);
    return arrayList;
  }
  
  public InputStream open(String paramString) throws FileNotFoundException {
    return new FileInputStream(new File(this.baseFolder, paramString.replace('/', File.separatorChar)));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\multidex\FolderPathElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */