package com.lody.virtual.helper.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import org.xmlpull.v1.XmlSerializer;

public class FastXmlSerializer implements XmlSerializer {
  private static final int BUFFER_LEN = 8192;
  
  private static final String[] ESCAPE_TABLE = new String[] { 
      null, null, null, null, null, null, null, null, null, null, 
      null, null, null, null, null, null, null, null, null, null, 
      null, null, null, null, null, null, null, null, null, null, 
      null, null, null, null, "&quot;", null, null, null, "&amp;", null, 
      null, null, null, null, null, null, null, null, null, null, 
      null, null, null, null, null, null, null, null, null, null, 
      "&lt;", null, "&gt;", null };
  
  private static String sSpace = "                                                              ";
  
  private ByteBuffer mBytes = ByteBuffer.allocate(8192);
  
  private CharsetEncoder mCharset;
  
  private boolean mInTag;
  
  private boolean mIndent = false;
  
  private boolean mLineStart = true;
  
  private int mNesting = 0;
  
  private OutputStream mOutputStream;
  
  private int mPos;
  
  private final char[] mText = new char[8192];
  
  private Writer mWriter;
  
  private void append(char paramChar) throws IOException {
    int i = this.mPos;
    int j = i;
    if (i >= 8191) {
      flush();
      j = this.mPos;
    } 
    this.mText[j] = (char)paramChar;
    this.mPos = j + 1;
  }
  
  private void append(String paramString) throws IOException {
    append(paramString, 0, paramString.length());
  }
  
  private void append(String paramString, int paramInt1, int paramInt2) throws IOException {
    if (paramInt2 > 8192) {
      int k = paramInt2 + paramInt1;
      while (paramInt1 < k) {
        int m = paramInt1 + 8192;
        if (m < k) {
          paramInt2 = 8192;
        } else {
          paramInt2 = k - paramInt1;
        } 
        append(paramString, paramInt1, paramInt2);
        paramInt1 = m;
      } 
      return;
    } 
    int i = this.mPos;
    int j = i;
    if (i + paramInt2 > 8192) {
      flush();
      j = this.mPos;
    } 
    paramString.getChars(paramInt1, paramInt1 + paramInt2, this.mText, j);
    this.mPos = j + paramInt2;
  }
  
  private void append(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
    if (paramInt2 > 8192) {
      int k = paramInt2 + paramInt1;
      while (paramInt1 < k) {
        int m = paramInt1 + 8192;
        if (m < k) {
          paramInt2 = 8192;
        } else {
          paramInt2 = k - paramInt1;
        } 
        append(paramArrayOfchar, paramInt1, paramInt2);
        paramInt1 = m;
      } 
      return;
    } 
    int i = this.mPos;
    int j = i;
    if (i + paramInt2 > 8192) {
      flush();
      j = this.mPos;
    } 
    System.arraycopy(paramArrayOfchar, paramInt1, this.mText, j, paramInt2);
    this.mPos = j + paramInt2;
  }
  
  private void appendIndent(int paramInt) throws IOException {
    int i = paramInt * 4;
    paramInt = i;
    if (i > sSpace.length())
      paramInt = sSpace.length(); 
    append(sSpace, 0, paramInt);
  }
  
  private void escapeAndAppendString(String paramString) throws IOException {
    int i = paramString.length();
    String[] arrayOfString = ESCAPE_TABLE;
    char c = (char)arrayOfString.length;
    byte b = 0;
    int j = 0;
    while (b < i) {
      char c1 = paramString.charAt(b);
      if (c1 < c) {
        String str = arrayOfString[c1];
        if (str != null) {
          if (j < b)
            append(paramString, j, b - j); 
          j = b + 1;
          append(str);
        } 
      } 
      b++;
    } 
    if (j < b)
      append(paramString, j, b - j); 
  }
  
  private void escapeAndAppendString(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
    String[] arrayOfString = ESCAPE_TABLE;
    char c = (char)arrayOfString.length;
    int i = paramInt1;
    int j;
    for (j = paramInt1; j < paramInt2 + paramInt1; j++) {
      char c1 = paramArrayOfchar[j];
      if (c1 < c) {
        String str = arrayOfString[c1];
        if (str != null) {
          if (i < j)
            append(paramArrayOfchar, i, j - i); 
          i = j + 1;
          append(str);
        } 
      } 
    } 
    if (i < j)
      append(paramArrayOfchar, i, j - i); 
  }
  
  private void flushBytes() throws IOException {
    int i = this.mBytes.position();
    if (i > 0) {
      this.mBytes.flip();
      this.mOutputStream.write(this.mBytes.array(), 0, i);
      this.mBytes.clear();
    } 
  }
  
  public XmlSerializer attribute(String paramString1, String paramString2, String paramString3) throws IOException, IllegalArgumentException, IllegalStateException {
    append(' ');
    if (paramString1 != null) {
      append(paramString1);
      append(':');
    } 
    append(paramString2);
    append("=\"");
    escapeAndAppendString(paramString3);
    append('"');
    this.mLineStart = false;
    return this;
  }
  
  public void cdsect(String paramString) throws IOException, IllegalArgumentException, IllegalStateException {
    throw new UnsupportedOperationException();
  }
  
  public void comment(String paramString) throws IOException, IllegalArgumentException, IllegalStateException {
    throw new UnsupportedOperationException();
  }
  
  public void docdecl(String paramString) throws IOException, IllegalArgumentException, IllegalStateException {
    throw new UnsupportedOperationException();
  }
  
  public void endDocument() throws IOException, IllegalArgumentException, IllegalStateException {
    flush();
  }
  
  public XmlSerializer endTag(String paramString1, String paramString2) throws IOException, IllegalArgumentException, IllegalStateException {
    int i = this.mNesting - 1;
    this.mNesting = i;
    if (this.mInTag) {
      append(" />\n");
    } else {
      if (this.mIndent && this.mLineStart)
        appendIndent(i); 
      append("</");
      if (paramString1 != null) {
        append(paramString1);
        append(':');
      } 
      append(paramString2);
      append(">\n");
    } 
    this.mLineStart = true;
    this.mInTag = false;
    return this;
  }
  
  public void entityRef(String paramString) throws IOException, IllegalArgumentException, IllegalStateException {
    throw new UnsupportedOperationException();
  }
  
  public void flush() throws IOException {
    int i = this.mPos;
    if (i > 0)
      if (this.mOutputStream != null) {
        CharBuffer charBuffer = CharBuffer.wrap(this.mText, 0, i);
        CoderResult coderResult = this.mCharset.encode(charBuffer, this.mBytes, true);
        while (true) {
          if (!coderResult.isError()) {
            if (coderResult.isOverflow()) {
              flushBytes();
              coderResult = this.mCharset.encode(charBuffer, this.mBytes, true);
              continue;
            } 
            flushBytes();
            this.mOutputStream.flush();
          } else {
            throw new IOException(coderResult.toString());
          } 
          this.mPos = 0;
        } 
      } else {
        this.mWriter.write(this.mText, 0, i);
        this.mWriter.flush();
        this.mPos = 0;
      }  
  }
  
  public int getDepth() {
    throw new UnsupportedOperationException();
  }
  
  public boolean getFeature(String paramString) {
    throw new UnsupportedOperationException();
  }
  
  public String getName() {
    throw new UnsupportedOperationException();
  }
  
  public String getNamespace() {
    throw new UnsupportedOperationException();
  }
  
  public String getPrefix(String paramString, boolean paramBoolean) throws IllegalArgumentException {
    throw new UnsupportedOperationException();
  }
  
  public Object getProperty(String paramString) {
    throw new UnsupportedOperationException();
  }
  
  public void ignorableWhitespace(String paramString) throws IOException, IllegalArgumentException, IllegalStateException {
    throw new UnsupportedOperationException();
  }
  
  public void processingInstruction(String paramString) throws IOException, IllegalArgumentException, IllegalStateException {
    throw new UnsupportedOperationException();
  }
  
  public void setFeature(String paramString, boolean paramBoolean) throws IllegalArgumentException, IllegalStateException {
    if (paramString.equals("http://xmlpull.org/v1/doc/features.html#indent-output")) {
      this.mIndent = true;
      return;
    } 
    throw new UnsupportedOperationException();
  }
  
  public void setOutput(OutputStream paramOutputStream, String paramString) throws IOException, IllegalArgumentException, IllegalStateException {
    if (paramOutputStream != null)
      try {
        this.mCharset = Charset.forName(paramString).newEncoder();
        this.mOutputStream = paramOutputStream;
        return;
      } catch (IllegalCharsetNameException illegalCharsetNameException) {
        throw (UnsupportedEncodingException)(new UnsupportedEncodingException(paramString)).initCause(illegalCharsetNameException);
      } catch (UnsupportedCharsetException unsupportedCharsetException) {
        throw (UnsupportedEncodingException)(new UnsupportedEncodingException(paramString)).initCause(unsupportedCharsetException);
      }  
    throw new IllegalArgumentException();
  }
  
  public void setOutput(Writer paramWriter) throws IOException, IllegalArgumentException, IllegalStateException {
    this.mWriter = paramWriter;
  }
  
  public void setPrefix(String paramString1, String paramString2) throws IOException, IllegalArgumentException, IllegalStateException {
    throw new UnsupportedOperationException();
  }
  
  public void setProperty(String paramString, Object paramObject) throws IllegalArgumentException, IllegalStateException {
    throw new UnsupportedOperationException();
  }
  
  public void startDocument(String paramString, Boolean paramBoolean) throws IOException, IllegalArgumentException, IllegalStateException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<?xml version='1.0' encoding='utf-8' standalone='");
    if (paramBoolean.booleanValue()) {
      paramString = "yes";
    } else {
      paramString = "no";
    } 
    stringBuilder.append(paramString);
    stringBuilder.append("' ?>\n");
    append(stringBuilder.toString());
    this.mLineStart = true;
  }
  
  public XmlSerializer startTag(String paramString1, String paramString2) throws IOException, IllegalArgumentException, IllegalStateException {
    if (this.mInTag)
      append(">\n"); 
    if (this.mIndent)
      appendIndent(this.mNesting); 
    this.mNesting++;
    append('<');
    if (paramString1 != null) {
      append(paramString1);
      append(':');
    } 
    append(paramString2);
    this.mInTag = true;
    this.mLineStart = false;
    return this;
  }
  
  public XmlSerializer text(String paramString) throws IOException, IllegalArgumentException, IllegalStateException {
    boolean bool = this.mInTag;
    boolean bool1 = false;
    if (bool) {
      append(">");
      this.mInTag = false;
    } 
    escapeAndAppendString(paramString);
    if (this.mIndent) {
      bool = bool1;
      if (paramString.length() > 0) {
        bool = bool1;
        if (paramString.charAt(paramString.length() - 1) == '\n')
          bool = true; 
      } 
      this.mLineStart = bool;
    } 
    return this;
  }
  
  public XmlSerializer text(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException, IllegalArgumentException, IllegalStateException {
    boolean bool = this.mInTag;
    boolean bool1 = false;
    if (bool) {
      append(">");
      this.mInTag = false;
    } 
    escapeAndAppendString(paramArrayOfchar, paramInt1, paramInt2);
    if (this.mIndent) {
      if (paramArrayOfchar[paramInt1 + paramInt2 - 1] == '\n')
        bool1 = true; 
      this.mLineStart = bool1;
    } 
    return this;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\FastXmlSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */