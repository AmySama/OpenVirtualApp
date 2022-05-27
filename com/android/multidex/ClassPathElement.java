package com.android.multidex;

import java.io.IOException;
import java.io.InputStream;

interface ClassPathElement {
  public static final char SEPARATOR_CHAR = '/';
  
  void close() throws IOException;
  
  Iterable<String> list();
  
  InputStream open(String paramString) throws IOException;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\multidex\ClassPathElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */