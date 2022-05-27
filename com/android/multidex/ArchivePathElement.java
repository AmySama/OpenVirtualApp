package com.android.multidex;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class ArchivePathElement implements ClassPathElement {
  private final ZipFile archive;
  
  public ArchivePathElement(ZipFile paramZipFile) {
    this.archive = paramZipFile;
  }
  
  public void close() throws IOException {
    this.archive.close();
  }
  
  public Iterable<String> list() {
    return new Iterable<String>() {
        public Iterator<String> iterator() {
          return new Iterator<String>() {
              Enumeration<? extends ZipEntry> delegate = ArchivePathElement.this.archive.entries();
              
              ZipEntry next = null;
              
              public boolean hasNext() {
                boolean bool;
                while (this.next == null && this.delegate.hasMoreElements()) {
                  ZipEntry zipEntry = this.delegate.nextElement();
                  this.next = zipEntry;
                  if (zipEntry.isDirectory())
                    this.next = null; 
                } 
                if (this.next != null) {
                  bool = true;
                } else {
                  bool = false;
                } 
                return bool;
              }
              
              public String next() {
                if (hasNext()) {
                  String str = this.next.getName();
                  this.next = null;
                  return str;
                } 
                throw new NoSuchElementException();
              }
              
              public void remove() {
                throw new UnsupportedOperationException();
              }
            };
        }
      };
  }
  
  public InputStream open(String paramString) throws IOException {
    ZipEntry zipEntry = this.archive.getEntry(paramString);
    if (zipEntry != null) {
      if (!zipEntry.isDirectory())
        return this.archive.getInputStream(zipEntry); 
      throw new DirectoryEntryException();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("File \"");
    stringBuilder.append(paramString);
    stringBuilder.append("\" not found");
    throw new FileNotFoundException(stringBuilder.toString());
  }
  
  static class DirectoryEntryException extends IOException {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\multidex\ArchivePathElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */