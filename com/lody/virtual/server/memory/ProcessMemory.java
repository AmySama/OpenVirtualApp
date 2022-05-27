package com.lody.virtual.server.memory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Locale;

public class ProcessMemory {
  private RandomAccessFile memFile;
  
  private int pid;
  
  public ProcessMemory(int paramInt) throws IOException {
    this.pid = paramInt;
    this.memFile = new RandomAccessFile(String.format(Locale.ENGLISH, "/proc/%d/mem", new Object[] { Integer.valueOf(paramInt) }), "rw");
  }
  
  public void close() throws IOException {
    this.memFile.close();
  }
  
  public int read(long paramLong, byte[] paramArrayOfbyte, int paramInt) throws IOException {
    this.memFile.seek(paramLong);
    return this.memFile.read(paramArrayOfbyte, 0, paramInt);
  }
  
  public void write(long paramLong, byte[] paramArrayOfbyte) throws IOException {
    this.memFile.seek(paramLong);
    this.memFile.write(paramArrayOfbyte);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\memory\ProcessMemory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */