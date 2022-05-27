package com.lody.virtual.helper.dedex;

import com.lody.virtual.helper.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.zip.Adler32;

public class Dex {
  public final int dataEnd;
  
  public final int dexPosition;
  
  public final Header header;
  
  private final DataReader mReader;
  
  public Dex(DataReader paramDataReader) throws IOException {
    int i;
    this.dexPosition = paramDataReader.position();
    this.mReader = paramDataReader;
    Header header = new Header(paramDataReader);
    this.header = header;
    if (header.isCompactDex) {
      i = this.header.data_off_ + this.header.data_size_;
    } else {
      i = this.header.file_size_;
    } 
    this.dataEnd = i;
  }
  
  private void calcChecksum(byte[] paramArrayOfbyte) {
    Adler32 adler32 = new Adler32();
    adler32.update(paramArrayOfbyte, 12, paramArrayOfbyte.length - 12);
    int i = (int)adler32.getValue();
    if (this.header.checksum_ != i) {
      paramArrayOfbyte[8] = (byte)(byte)i;
      paramArrayOfbyte[9] = (byte)(byte)(i >> 8);
      paramArrayOfbyte[10] = (byte)(byte)(i >> 16);
      paramArrayOfbyte[11] = (byte)(byte)(i >> 24);
    } 
  }
  
  private void calcSignature(byte[] paramArrayOfbyte) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
      messageDigest.reset();
      messageDigest.update(paramArrayOfbyte, 32, paramArrayOfbyte.length - 32);
      messageDigest.digest(paramArrayOfbyte, 12, 20);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public byte[] getBytes() {
    byte[] arrayOfByte = new byte[this.dataEnd];
    this.mReader.position(this.dexPosition);
    this.mReader.readBytes(arrayOfByte);
    return arrayOfByte;
  }
  
  public byte[] getFixedBytes() {
    byte[] arrayOfByte = getBytes();
    calcSignature(arrayOfByte);
    calcChecksum(arrayOfByte);
    return arrayOfByte;
  }
  
  public void writeTo(File paramFile) throws IOException {
    FileUtils.writeToFile(getFixedBytes(), paramFile);
  }
  
  public static class Header {
    static final String MAGIC_COMPACT_DEX = "cdex";
    
    static final String MAGIC_DEX = "dex";
    
    final int checksum_;
    
    final int class_defs_off_;
    
    final int class_defs_size_;
    
    public final int data_off_;
    
    final int data_size_;
    
    final int endian_tag_;
    
    final int field_ids_off_;
    
    final int field_ids_size_;
    
    public final int file_size_;
    
    public final int header_size_;
    
    final boolean isCompactDex;
    
    final int link_off_;
    
    final int link_size_;
    
    final String magic;
    
    final char[] magic_;
    
    final int map_off_;
    
    final int method_ids_off_;
    
    final int method_ids_size_;
    
    final int proto_ids_off_;
    
    final int proto_ids_size_;
    
    final byte[] signature_;
    
    final int string_ids_off_;
    
    final int string_ids_size_;
    
    final int type_ids_off_;
    
    final int type_ids_size_;
    
    final String version;
    
    final char[] version_;
    
    public Header(DataReader param1DataReader) throws IOException {
      char[] arrayOfChar = new char[4];
      this.magic_ = arrayOfChar;
      this.version_ = new char[4];
      this.signature_ = new byte[20];
      param1DataReader.readBytes(arrayOfChar);
      String str = (new String(this.magic_)).trim();
      this.magic = str;
      this.isCompactDex = str.equals("cdex");
      if (this.magic.equals("dex") || this.isCompactDex) {
        param1DataReader.readBytes(this.version_);
        str = (new String(this.version_)).trim();
        this.version = str;
        if (this.isCompactDex || str.compareTo("035") >= 0) {
          this.checksum_ = param1DataReader.readInt();
          param1DataReader.readBytes(this.signature_);
          this.file_size_ = param1DataReader.readInt();
          this.header_size_ = param1DataReader.readInt();
          this.endian_tag_ = param1DataReader.readInt();
          this.link_size_ = param1DataReader.readInt();
          this.link_off_ = param1DataReader.readInt();
          this.map_off_ = param1DataReader.readInt();
          this.string_ids_size_ = param1DataReader.readInt();
          this.string_ids_off_ = param1DataReader.readInt();
          this.type_ids_size_ = param1DataReader.readInt();
          this.type_ids_off_ = param1DataReader.readInt();
          this.proto_ids_size_ = param1DataReader.readInt();
          this.proto_ids_off_ = param1DataReader.readInt();
          this.field_ids_size_ = param1DataReader.readInt();
          this.field_ids_off_ = param1DataReader.readInt();
          this.method_ids_size_ = param1DataReader.readInt();
          this.method_ids_off_ = param1DataReader.readInt();
          this.class_defs_size_ = param1DataReader.readInt();
          this.class_defs_off_ = param1DataReader.readInt();
          this.data_size_ = param1DataReader.readInt();
          this.data_off_ = param1DataReader.readInt();
          return;
        } 
        throw new IOException(String.format("Invalid dex version '%s'", new Object[] { this.version }));
      } 
      throw new IOException(String.format("Invalid dex magic '%s'", new Object[] { this.magic }));
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\dedex\Dex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */