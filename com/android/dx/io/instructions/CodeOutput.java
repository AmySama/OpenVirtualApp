package com.android.dx.io.instructions;

public interface CodeOutput extends CodeCursor {
  void write(short paramShort);
  
  void write(short paramShort1, short paramShort2);
  
  void write(short paramShort1, short paramShort2, short paramShort3);
  
  void write(short paramShort1, short paramShort2, short paramShort3, short paramShort4);
  
  void write(short paramShort1, short paramShort2, short paramShort3, short paramShort4, short paramShort5);
  
  void write(byte[] paramArrayOfbyte);
  
  void write(int[] paramArrayOfint);
  
  void write(long[] paramArrayOflong);
  
  void write(short[] paramArrayOfshort);
  
  void writeInt(int paramInt);
  
  void writeLong(long paramLong);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\io\instructions\CodeOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */