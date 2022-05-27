package com.android.dx.util;

import com.android.dex.util.ByteOutput;

public interface Output extends ByteOutput {
  void alignTo(int paramInt);
  
  void assertCursor(int paramInt);
  
  int getCursor();
  
  void write(ByteArray paramByteArray);
  
  void write(byte[] paramArrayOfbyte);
  
  void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  void writeByte(int paramInt);
  
  void writeInt(int paramInt);
  
  void writeLong(long paramLong);
  
  void writeShort(int paramInt);
  
  int writeSleb128(int paramInt);
  
  int writeUleb128(int paramInt);
  
  void writeZeroes(int paramInt);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\d\\util\Output.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */