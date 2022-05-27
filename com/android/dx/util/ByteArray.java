package com.android.dx.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public final class ByteArray {
  private final byte[] bytes;
  
  private final int size;
  
  private final int start;
  
  public ByteArray(byte[] paramArrayOfbyte) {
    this(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public ByteArray(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (paramArrayOfbyte != null) {
      if (paramInt1 >= 0) {
        if (paramInt2 >= paramInt1) {
          if (paramInt2 <= paramArrayOfbyte.length) {
            this.bytes = paramArrayOfbyte;
            this.start = paramInt1;
            this.size = paramInt2 - paramInt1;
            return;
          } 
          throw new IllegalArgumentException("end > bytes.length");
        } 
        throw new IllegalArgumentException("end < start");
      } 
      throw new IllegalArgumentException("start < 0");
    } 
    throw new NullPointerException("bytes == null");
  }
  
  private void checkOffsets(int paramInt1, int paramInt2) {
    if (paramInt1 >= 0 && paramInt2 >= paramInt1 && paramInt2 <= this.size)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("bad range: ");
    stringBuilder.append(paramInt1);
    stringBuilder.append("..");
    stringBuilder.append(paramInt2);
    stringBuilder.append("; actual size ");
    stringBuilder.append(this.size);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  private int getByte0(int paramInt) {
    return this.bytes[this.start + paramInt];
  }
  
  private int getUnsignedByte0(int paramInt) {
    return this.bytes[this.start + paramInt] & 0xFF;
  }
  
  public int getByte(int paramInt) {
    checkOffsets(paramInt, paramInt + 1);
    return getByte0(paramInt);
  }
  
  public void getBytes(byte[] paramArrayOfbyte, int paramInt) {
    int i = paramArrayOfbyte.length;
    int j = this.size;
    if (i - paramInt >= j) {
      System.arraycopy(this.bytes, this.start, paramArrayOfbyte, paramInt, j);
      return;
    } 
    throw new IndexOutOfBoundsException("(out.length - offset) < size()");
  }
  
  public int getInt(int paramInt) {
    checkOffsets(paramInt, paramInt + 4);
    int i = getByte0(paramInt);
    int j = getUnsignedByte0(paramInt + 1);
    int k = getUnsignedByte0(paramInt + 2);
    return getUnsignedByte0(paramInt + 3) | i << 24 | j << 16 | k << 8;
  }
  
  public long getLong(int paramInt) {
    checkOffsets(paramInt, paramInt + 8);
    int i = getByte0(paramInt);
    int j = getUnsignedByte0(paramInt + 1);
    int k = getUnsignedByte0(paramInt + 2);
    int m = getUnsignedByte0(paramInt + 3);
    int n = getByte0(paramInt + 4);
    int i1 = getUnsignedByte0(paramInt + 5);
    int i2 = getUnsignedByte0(paramInt + 6);
    return (getUnsignedByte0(paramInt + 7) | n << 24 | i1 << 16 | i2 << 8) & 0xFFFFFFFFL | (i << 24 | j << 16 | k << 8 | m) << 32L;
  }
  
  public int getShort(int paramInt) {
    checkOffsets(paramInt, paramInt + 2);
    int i = getByte0(paramInt);
    return getUnsignedByte0(paramInt + 1) | i << 8;
  }
  
  public int getUnsignedByte(int paramInt) {
    checkOffsets(paramInt, paramInt + 1);
    return getUnsignedByte0(paramInt);
  }
  
  public int getUnsignedShort(int paramInt) {
    checkOffsets(paramInt, paramInt + 2);
    int i = getUnsignedByte0(paramInt);
    return getUnsignedByte0(paramInt + 1) | i << 8;
  }
  
  public MyDataInputStream makeDataInputStream() {
    return new MyDataInputStream(makeInputStream());
  }
  
  public MyInputStream makeInputStream() {
    return new MyInputStream();
  }
  
  public int size() {
    return this.size;
  }
  
  public ByteArray slice(int paramInt1, int paramInt2) {
    checkOffsets(paramInt1, paramInt2);
    return new ByteArray(Arrays.copyOfRange(this.bytes, paramInt1, paramInt2));
  }
  
  public int underlyingOffset(int paramInt) {
    return this.start + paramInt;
  }
  
  public static interface GetCursor {
    int getCursor();
  }
  
  public static class MyDataInputStream extends DataInputStream {
    private final ByteArray.MyInputStream wrapped;
    
    public MyDataInputStream(ByteArray.MyInputStream param1MyInputStream) {
      super(param1MyInputStream);
      this.wrapped = param1MyInputStream;
    }
  }
  
  public class MyInputStream extends InputStream {
    private int cursor = 0;
    
    private int mark = 0;
    
    public int available() {
      return ByteArray.this.size - this.cursor;
    }
    
    public void mark(int param1Int) {
      this.mark = this.cursor;
    }
    
    public boolean markSupported() {
      return true;
    }
    
    public int read() throws IOException {
      if (this.cursor >= ByteArray.this.size)
        return -1; 
      int i = ByteArray.this.getUnsignedByte0(this.cursor);
      this.cursor++;
      return i;
    }
    
    public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
      int i = param1Int2;
      if (param1Int1 + param1Int2 > param1ArrayOfbyte.length)
        i = param1ArrayOfbyte.length - param1Int1; 
      int j = ByteArray.this.size - this.cursor;
      param1Int2 = i;
      if (i > j)
        param1Int2 = j; 
      System.arraycopy(ByteArray.this.bytes, this.cursor + ByteArray.this.start, param1ArrayOfbyte, param1Int1, param1Int2);
      this.cursor += param1Int2;
      return param1Int2;
    }
    
    public void reset() {
      this.cursor = this.mark;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\d\\util\ByteArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */