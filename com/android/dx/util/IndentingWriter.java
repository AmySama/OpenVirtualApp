package com.android.dx.util;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public final class IndentingWriter extends FilterWriter {
  private boolean collectingIndent;
  
  private int column;
  
  private int indent;
  
  private final int maxIndent;
  
  private final String prefix;
  
  private final int width;
  
  public IndentingWriter(Writer paramWriter, int paramInt) {
    this(paramWriter, paramInt, "");
  }
  
  public IndentingWriter(Writer paramWriter, int paramInt, String paramString) {
    super(paramWriter);
    if (paramWriter != null) {
      if (paramInt >= 0) {
        if (paramString != null) {
          int i;
          if (paramInt != 0) {
            i = paramInt;
          } else {
            i = Integer.MAX_VALUE;
          } 
          this.width = i;
          this.maxIndent = paramInt >> 1;
          String str = paramString;
          if (paramString.length() == 0)
            str = null; 
          this.prefix = str;
          bol();
          return;
        } 
        throw new NullPointerException("prefix == null");
      } 
      throw new IllegalArgumentException("width < 0");
    } 
    throw new NullPointerException("out == null");
  }
  
  private void bol() {
    boolean bool;
    this.column = 0;
    if (this.maxIndent != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.collectingIndent = bool;
    this.indent = 0;
  }
  
  public void write(int paramInt) throws IOException {
    synchronized (this.lock) {
      boolean bool = this.collectingIndent;
      byte b = 0;
      if (bool)
        if (paramInt == 32) {
          int i = this.indent + 1;
          this.indent = i;
          if (i >= this.maxIndent) {
            this.indent = this.maxIndent;
            this.collectingIndent = false;
          } 
        } else {
          this.collectingIndent = false;
        }  
      if (this.column == this.width && paramInt != 10) {
        this.out.write(10);
        this.column = 0;
      } 
      if (this.column == 0) {
        if (this.prefix != null)
          this.out.write(this.prefix); 
        if (!this.collectingIndent) {
          while (b < this.indent) {
            this.out.write(32);
            b++;
          } 
          this.column = this.indent;
        } 
      } 
      this.out.write(paramInt);
      if (paramInt == 10) {
        bol();
      } else {
        this.column++;
      } 
      return;
    } 
  }
  
  public void write(String paramString, int paramInt1, int paramInt2) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: getfield lock : Ljava/lang/Object;
    //   4: astore #4
    //   6: aload #4
    //   8: monitorenter
    //   9: iload_3
    //   10: ifle -> 31
    //   13: aload_0
    //   14: aload_1
    //   15: iload_2
    //   16: invokevirtual charAt : (I)C
    //   19: invokevirtual write : (I)V
    //   22: iinc #2, 1
    //   25: iinc #3, -1
    //   28: goto -> 9
    //   31: aload #4
    //   33: monitorexit
    //   34: return
    //   35: astore_1
    //   36: aload #4
    //   38: monitorexit
    //   39: aload_1
    //   40: athrow
    // Exception table:
    //   from	to	target	type
    //   13	22	35	finally
    //   31	34	35	finally
    //   36	39	35	finally
  }
  
  public void write(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: getfield lock : Ljava/lang/Object;
    //   4: astore #4
    //   6: aload #4
    //   8: monitorenter
    //   9: iload_3
    //   10: ifle -> 29
    //   13: aload_0
    //   14: aload_1
    //   15: iload_2
    //   16: caload
    //   17: invokevirtual write : (I)V
    //   20: iinc #2, 1
    //   23: iinc #3, -1
    //   26: goto -> 9
    //   29: aload #4
    //   31: monitorexit
    //   32: return
    //   33: astore_1
    //   34: aload #4
    //   36: monitorexit
    //   37: aload_1
    //   38: athrow
    // Exception table:
    //   from	to	target	type
    //   13	20	33	finally
    //   29	32	33	finally
    //   34	37	33	finally
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\d\\util\IndentingWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */