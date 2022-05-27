package com.lody.virtual.helper.dedex;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;

public class DataReader implements Closeable {
  private ArrayList<DataReader> mAssociatedReaders;
  
  private final File mFile;
  
  private final MappedByteBuffer mMappedBuffer;
  
  private final RandomAccessFile mRaf;
  
  public DataReader(File paramFile) throws Exception {
    this.mFile = paramFile;
    RandomAccessFile randomAccessFile = new RandomAccessFile(this.mFile, "r");
    this.mRaf = randomAccessFile;
    MappedByteBuffer mappedByteBuffer = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, paramFile.length());
    this.mMappedBuffer = mappedByteBuffer;
    mappedByteBuffer.rewind();
    setLittleEndian(true);
  }
  
  public DataReader(String paramString) throws Exception {
    this(new File(paramString));
  }
  
  public static int toInt(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual length : ()I
    //   4: istore_1
    //   5: iload_1
    //   6: newarray char
    //   8: astore_2
    //   9: iconst_0
    //   10: istore_3
    //   11: iconst_0
    //   12: istore #4
    //   14: iconst_0
    //   15: istore #5
    //   17: iload #4
    //   19: iload_1
    //   20: if_icmpge -> 79
    //   23: aload_0
    //   24: iload #4
    //   26: invokevirtual charAt : (I)C
    //   29: istore #6
    //   31: iload #6
    //   33: bipush #48
    //   35: if_icmplt -> 45
    //   38: iload #6
    //   40: bipush #57
    //   42: if_icmple -> 56
    //   45: iload #5
    //   47: istore #7
    //   49: iload #6
    //   51: bipush #45
    //   53: if_icmpne -> 69
    //   56: aload_2
    //   57: iload #5
    //   59: iload #6
    //   61: i2c
    //   62: castore
    //   63: iload #5
    //   65: iconst_1
    //   66: iadd
    //   67: istore #7
    //   69: iinc #4, 1
    //   72: iload #7
    //   74: istore #5
    //   76: goto -> 17
    //   79: iload #5
    //   81: ifne -> 90
    //   84: iload_3
    //   85: istore #4
    //   87: goto -> 106
    //   90: new java/lang/String
    //   93: dup
    //   94: aload_2
    //   95: iconst_0
    //   96: iload #5
    //   98: invokespecial <init> : ([CII)V
    //   101: invokestatic parseInt : (Ljava/lang/String;)I
    //   104: istore #4
    //   106: iload #4
    //   108: ireturn
  }
  
  public void addAssociatedReader(DataReader paramDataReader) {
    if (this.mAssociatedReaders == null)
      this.mAssociatedReaders = new ArrayList<DataReader>(); 
    this.mAssociatedReaders.add(paramDataReader);
  }
  
  public void close() {
    try {
      this.mRaf.close();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    ArrayList<DataReader> arrayList = this.mAssociatedReaders;
    if (arrayList != null) {
      Iterator<DataReader> iterator = arrayList.iterator();
      while (iterator.hasNext())
        ((DataReader)iterator.next()).close(); 
    } 
  }
  
  public FileChannel getChannel() {
    return this.mRaf.getChannel();
  }
  
  public File getFile() {
    return this.mFile;
  }
  
  public int position() {
    return this.mMappedBuffer.position();
  }
  
  public void position(int paramInt) {
    this.mMappedBuffer.position(paramInt);
  }
  
  public int previewInt() {
    this.mMappedBuffer.mark();
    int i = readInt();
    this.mMappedBuffer.reset();
    return i;
  }
  
  public int readByte() {
    return this.mMappedBuffer.get() & 0xFF;
  }
  
  public void readBytes(byte[] paramArrayOfbyte) {
    this.mMappedBuffer.get(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public void readBytes(char[] paramArrayOfchar) {
    byte[] arrayOfByte = new byte[paramArrayOfchar.length];
    readBytes(arrayOfByte);
    for (byte b = 0; b < paramArrayOfchar.length; b++)
      paramArrayOfchar[b] = (char)(char)arrayOfByte[b]; 
  }
  
  public int readInt() {
    return this.mMappedBuffer.getInt();
  }
  
  public final long readLong() {
    return this.mMappedBuffer.getLong();
  }
  
  public short readShort() {
    return this.mMappedBuffer.getShort();
  }
  
  public int readUleb128() {
    int i = readByte();
    int j = i;
    if (i > 127) {
      int k = readByte();
      i = i & 0x7F | (k & 0x7F) << 7;
      j = i;
      if (k > 127) {
        k = readByte();
        i |= (k & 0x7F) << 14;
        j = i;
        if (k > 127) {
          k = readByte();
          i |= (k & 0x7F) << 21;
          j = i;
          if (k > 127)
            j = i | readByte() << 28; 
        } 
      } 
    } 
    return j;
  }
  
  public void seek(long paramLong) {
    position((int)paramLong);
  }
  
  public void setLittleEndian(boolean paramBoolean) {
    ByteOrder byteOrder;
    MappedByteBuffer mappedByteBuffer = this.mMappedBuffer;
    if (paramBoolean) {
      byteOrder = ByteOrder.LITTLE_ENDIAN;
    } else {
      byteOrder = ByteOrder.BIG_ENDIAN;
    } 
    mappedByteBuffer.order(byteOrder);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\dedex\DataReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */