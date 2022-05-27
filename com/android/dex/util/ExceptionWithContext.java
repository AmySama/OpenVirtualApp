package com.android.dex.util;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ExceptionWithContext extends RuntimeException {
  private StringBuffer context;
  
  public ExceptionWithContext(String paramString) {
    this(paramString, null);
  }
  
  public ExceptionWithContext(String paramString, Throwable paramThrowable) {
    super(paramString, paramThrowable);
    if (paramThrowable instanceof ExceptionWithContext) {
      String str = ((ExceptionWithContext)paramThrowable).context.toString();
      StringBuffer stringBuffer = new StringBuffer(str.length() + 200);
      this.context = stringBuffer;
      stringBuffer.append(str);
    } else {
      this.context = new StringBuffer(200);
    } 
  }
  
  public ExceptionWithContext(Throwable paramThrowable) {
    this(null, paramThrowable);
  }
  
  public static ExceptionWithContext withContext(Throwable paramThrowable, String paramString) {
    if (paramThrowable instanceof ExceptionWithContext) {
      paramThrowable = paramThrowable;
    } else {
      paramThrowable = new ExceptionWithContext(paramThrowable);
    } 
    paramThrowable.addContext(paramString);
    return (ExceptionWithContext)paramThrowable;
  }
  
  public void addContext(String paramString) {
    if (paramString != null) {
      this.context.append(paramString);
      if (!paramString.endsWith("\n"))
        this.context.append('\n'); 
      return;
    } 
    throw new NullPointerException("str == null");
  }
  
  public String getContext() {
    return this.context.toString();
  }
  
  public void printContext(PrintStream paramPrintStream) {
    paramPrintStream.println(getMessage());
    paramPrintStream.print(this.context);
  }
  
  public void printContext(PrintWriter paramPrintWriter) {
    paramPrintWriter.println(getMessage());
    paramPrintWriter.print(this.context);
  }
  
  public void printStackTrace(PrintStream paramPrintStream) {
    super.printStackTrace(paramPrintStream);
    paramPrintStream.println(this.context);
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter) {
    super.printStackTrace(paramPrintWriter);
    paramPrintWriter.println(this.context);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\de\\util\ExceptionWithContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */