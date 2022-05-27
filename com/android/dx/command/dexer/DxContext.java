package com.android.dx.command.dexer;

import com.android.dx.dex.cf.CodeStatistics;
import com.android.dx.dex.cf.OptimizerOptions;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class DxContext {
  public final CodeStatistics codeStatistics = new CodeStatistics();
  
  public final PrintStream err;
  
  final PrintStream noop = new PrintStream(new OutputStream() {
        public void write(int param1Int) throws IOException {}
      });
  
  public final OptimizerOptions optimizerOptions = new OptimizerOptions();
  
  public final PrintStream out;
  
  public DxContext() {
    this(System.out, System.err);
  }
  
  public DxContext(OutputStream paramOutputStream1, OutputStream paramOutputStream2) {
    this.out = new PrintStream(paramOutputStream1);
    this.err = new PrintStream(paramOutputStream2);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\command\dexer\DxContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */