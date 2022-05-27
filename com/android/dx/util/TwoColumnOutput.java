package com.android.dx.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

public final class TwoColumnOutput {
  private final StringBuffer leftBuf;
  
  private final IndentingWriter leftColumn;
  
  private final int leftWidth;
  
  private final Writer out;
  
  private final StringBuffer rightBuf;
  
  private final IndentingWriter rightColumn;
  
  public TwoColumnOutput(OutputStream paramOutputStream, int paramInt1, int paramInt2, String paramString) {
    this(new OutputStreamWriter(paramOutputStream), paramInt1, paramInt2, paramString);
  }
  
  public TwoColumnOutput(Writer paramWriter, int paramInt1, int paramInt2, String paramString) {
    if (paramWriter != null) {
      if (paramInt1 >= 1) {
        if (paramInt2 >= 1) {
          if (paramString != null) {
            StringWriter stringWriter1 = new StringWriter(1000);
            StringWriter stringWriter2 = new StringWriter(1000);
            this.out = paramWriter;
            this.leftWidth = paramInt1;
            this.leftBuf = stringWriter1.getBuffer();
            this.rightBuf = stringWriter2.getBuffer();
            this.leftColumn = new IndentingWriter(stringWriter1, paramInt1);
            this.rightColumn = new IndentingWriter(stringWriter2, paramInt2, paramString);
            return;
          } 
          throw new NullPointerException("spacer == null");
        } 
        throw new IllegalArgumentException("rightWidth < 1");
      } 
      throw new IllegalArgumentException("leftWidth < 1");
    } 
    throw new NullPointerException("out == null");
  }
  
  private static void appendNewlineIfNecessary(StringBuffer paramStringBuffer, Writer paramWriter) throws IOException {
    int i = paramStringBuffer.length();
    if (i != 0 && paramStringBuffer.charAt(i - 1) != '\n')
      paramWriter.write(10); 
  }
  
  private void flushLeft() throws IOException {
    appendNewlineIfNecessary(this.leftBuf, this.leftColumn);
    while (this.leftBuf.length() != 0) {
      this.rightColumn.write(10);
      outputFullLines();
    } 
  }
  
  private void flushRight() throws IOException {
    appendNewlineIfNecessary(this.rightBuf, this.rightColumn);
    while (this.rightBuf.length() != 0) {
      this.leftColumn.write(10);
      outputFullLines();
    } 
  }
  
  private void outputFullLines() throws IOException {
    while (true) {
      int i = this.leftBuf.indexOf("\n");
      if (i < 0)
        return; 
      int j = this.rightBuf.indexOf("\n");
      if (j < 0)
        return; 
      if (i != 0)
        this.out.write(this.leftBuf.substring(0, i)); 
      if (j != 0) {
        writeSpaces(this.out, this.leftWidth - i);
        this.out.write(this.rightBuf.substring(0, j));
      } 
      this.out.write(10);
      this.leftBuf.delete(0, i + 1);
      this.rightBuf.delete(0, j + 1);
    } 
  }
  
  public static String toString(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2) {
    StringWriter stringWriter = new StringWriter((paramString1.length() + paramString3.length()) * 3);
    TwoColumnOutput twoColumnOutput = new TwoColumnOutput(stringWriter, paramInt1, paramInt2, paramString2);
    try {
      twoColumnOutput.getLeft().write(paramString1);
      twoColumnOutput.getRight().write(paramString3);
      twoColumnOutput.flush();
      return stringWriter.toString();
    } catch (IOException iOException) {
      throw new RuntimeException("shouldn't happen", iOException);
    } 
  }
  
  private static void writeSpaces(Writer paramWriter, int paramInt) throws IOException {
    while (paramInt > 0) {
      paramWriter.write(32);
      paramInt--;
    } 
  }
  
  public void flush() {
    try {
      appendNewlineIfNecessary(this.leftBuf, this.leftColumn);
      appendNewlineIfNecessary(this.rightBuf, this.rightColumn);
      outputFullLines();
      flushLeft();
      flushRight();
      return;
    } catch (IOException iOException) {
      throw new RuntimeException(iOException);
    } 
  }
  
  public Writer getLeft() {
    return this.leftColumn;
  }
  
  public Writer getRight() {
    return this.rightColumn;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\d\\util\TwoColumnOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */