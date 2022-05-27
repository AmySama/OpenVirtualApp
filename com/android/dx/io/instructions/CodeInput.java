package com.android.dx.io.instructions;

import java.io.EOFException;

public interface CodeInput extends CodeCursor {
  boolean hasMore();
  
  int read() throws EOFException;
  
  int readInt() throws EOFException;
  
  long readLong() throws EOFException;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\io\instructions\CodeInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */