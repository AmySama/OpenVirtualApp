package com.android.dx.command.dump;

import com.android.dx.cf.code.ConcreteMethod;
import com.android.dx.cf.iface.Member;
import com.android.dx.cf.iface.ParseObserver;
import com.android.dx.dex.DexOptions;
import com.android.dx.util.ByteArray;
import com.android.dx.util.Hex;
import com.android.dx.util.IndentingWriter;
import com.android.dx.util.TwoColumnOutput;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;

public abstract class BaseDumper implements ParseObserver {
  protected Args args;
  
  private final byte[] bytes;
  
  protected final DexOptions dexOptions;
  
  private final String filePath;
  
  private final int hexCols;
  
  private int indent;
  
  private final PrintStream out;
  
  private final boolean rawBytes;
  
  private int readBytes;
  
  private String separator;
  
  private final boolean strictParse;
  
  private final int width;
  
  public BaseDumper(byte[] paramArrayOfbyte, PrintStream paramPrintStream, String paramString, Args paramArgs) {
    String str;
    int i;
    this.bytes = paramArrayOfbyte;
    this.rawBytes = paramArgs.rawBytes;
    this.out = paramPrintStream;
    if (paramArgs.width <= 0) {
      i = 79;
    } else {
      i = paramArgs.width;
    } 
    this.width = i;
    this.filePath = paramString;
    this.strictParse = paramArgs.strictParse;
    this.indent = 0;
    if (this.rawBytes) {
      str = "|";
    } else {
      str = "";
    } 
    this.separator = str;
    this.readBytes = 0;
    this.args = paramArgs;
    this.dexOptions = new DexOptions();
    int j = (this.width - 5) / 15 + 1 & 0xFFFFFFFE;
    if (j < 6) {
      i = 6;
    } else {
      i = j;
      if (j > 10)
        i = 10; 
    } 
    this.hexCols = i;
  }
  
  static int computeParamWidth(ConcreteMethod paramConcreteMethod, boolean paramBoolean) {
    return paramConcreteMethod.getEffectiveDescriptor().getParameterTypes().getWordCount();
  }
  
  public void changeIndent(int paramInt) {
    String str;
    this.indent += paramInt;
    if (this.rawBytes) {
      str = "|";
    } else {
      str = "";
    } 
    this.separator = str;
    for (paramInt = 0; paramInt < this.indent; paramInt++) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.separator);
      stringBuilder.append("  ");
      this.separator = stringBuilder.toString();
    } 
  }
  
  public void endParsingMember(ByteArray paramByteArray, int paramInt, String paramString1, String paramString2, Member paramMember) {}
  
  protected final byte[] getBytes() {
    return this.bytes;
  }
  
  protected final String getFilePath() {
    return this.filePath;
  }
  
  protected final boolean getRawBytes() {
    return this.rawBytes;
  }
  
  protected final int getReadBytes() {
    return this.readBytes;
  }
  
  protected final boolean getStrictParse() {
    return this.strictParse;
  }
  
  protected final int getWidth1() {
    if (this.rawBytes) {
      int i = this.hexCols;
      return i * 2 + 5 + i / 2;
    } 
    return 0;
  }
  
  protected final int getWidth2() {
    byte b;
    if (this.rawBytes) {
      b = getWidth1() + 1;
    } else {
      b = 0;
    } 
    return this.width - b - this.indent * 2;
  }
  
  protected final String hexDump(int paramInt1, int paramInt2) {
    return Hex.dump(this.bytes, paramInt1, paramInt2, paramInt1, this.hexCols, 4);
  }
  
  public void parsed(ByteArray paramByteArray, int paramInt1, int paramInt2, String paramString) {
    String str;
    paramInt1 = paramByteArray.underlyingOffset(paramInt1);
    if (getRawBytes()) {
      str = hexDump(paramInt1, paramInt2);
    } else {
      str = "";
    } 
    print(twoColumns(str, paramString));
    this.readBytes += paramInt2;
  }
  
  protected final void print(String paramString) {
    this.out.print(paramString);
  }
  
  protected final void println(String paramString) {
    this.out.println(paramString);
  }
  
  public void startParsingMember(ByteArray paramByteArray, int paramInt, String paramString1, String paramString2) {}
  
  protected final String twoColumns(String paramString1, String paramString2) {
    int i = getWidth1();
    int j = getWidth2();
    if (i == 0)
      try {
        i = paramString2.length();
        StringWriter stringWriter = new StringWriter();
        this(i * 2);
        IndentingWriter indentingWriter = new IndentingWriter();
        this(stringWriter, j, this.separator);
        indentingWriter.write(paramString2);
        if (i == 0 || paramString2.charAt(i - 1) != '\n')
          indentingWriter.write(10); 
        indentingWriter.flush();
        return stringWriter.toString();
      } catch (IOException iOException) {
        throw new RuntimeException(iOException);
      }  
    return TwoColumnOutput.toString((String)iOException, i, this.separator, paramString2, j);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\command\dump\BaseDumper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */