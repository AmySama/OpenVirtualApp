package com.android.dx.util;

import java.io.PrintWriter;
import java.io.Writer;

public final class Writers {
  public static PrintWriter printWriterFor(Writer paramWriter) {
    return (paramWriter instanceof PrintWriter) ? (PrintWriter)paramWriter : new PrintWriter(paramWriter);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\d\\util\Writers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */