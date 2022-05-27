package com.android.dx.io.instructions;

public final class ShortArrayCodeOutput extends BaseCodeCursor implements CodeOutput {
  private final short[] array;
  
  public ShortArrayCodeOutput(int paramInt) {
    if (paramInt >= 0) {
      this.array = new short[paramInt];
      return;
    } 
    throw new IllegalArgumentException("maxSize < 0");
  }
  
  public short[] getArray() {
    int i = cursor();
    short[] arrayOfShort1 = this.array;
    if (i == arrayOfShort1.length)
      return arrayOfShort1; 
    short[] arrayOfShort2 = new short[i];
    System.arraycopy(arrayOfShort1, 0, arrayOfShort2, 0, i);
    return arrayOfShort2;
  }
  
  public void write(short paramShort) {
    this.array[cursor()] = (short)paramShort;
    advance(1);
  }
  
  public void write(short paramShort1, short paramShort2) {
    write(paramShort1);
    write(paramShort2);
  }
  
  public void write(short paramShort1, short paramShort2, short paramShort3) {
    write(paramShort1);
    write(paramShort2);
    write(paramShort3);
  }
  
  public void write(short paramShort1, short paramShort2, short paramShort3, short paramShort4) {
    write(paramShort1);
    write(paramShort2);
    write(paramShort3);
    write(paramShort4);
  }
  
  public void write(short paramShort1, short paramShort2, short paramShort3, short paramShort4, short paramShort5) {
    write(paramShort1);
    write(paramShort2);
    write(paramShort3);
    write(paramShort4);
    write(paramShort5);
  }
  
  public void write(byte[] paramArrayOfbyte) {
    int i = paramArrayOfbyte.length;
    byte b = 0;
    boolean bool = true;
    int j = 0;
    while (b < i) {
      byte b1 = paramArrayOfbyte[b];
      if (bool) {
        j = b1 & 0xFF;
        bool = false;
      } else {
        j = b1 << 8 | j;
        write((short)j);
        bool = true;
      } 
      b++;
    } 
    if (!bool)
      write((short)j); 
  }
  
  public void write(int[] paramArrayOfint) {
    int i = paramArrayOfint.length;
    for (byte b = 0; b < i; b++)
      writeInt(paramArrayOfint[b]); 
  }
  
  public void write(long[] paramArrayOflong) {
    int i = paramArrayOflong.length;
    for (byte b = 0; b < i; b++)
      writeLong(paramArrayOflong[b]); 
  }
  
  public void write(short[] paramArrayOfshort) {
    int i = paramArrayOfshort.length;
    for (byte b = 0; b < i; b++)
      write(paramArrayOfshort[b]); 
  }
  
  public void writeInt(int paramInt) {
    write((short)paramInt);
    write((short)(paramInt >> 16));
  }
  
  public void writeLong(long paramLong) {
    write((short)(int)paramLong);
    write((short)(int)(paramLong >> 16L));
    write((short)(int)(paramLong >> 32L));
    write((short)(int)(paramLong >> 48L));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\io\instructions\ShortArrayCodeOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */