package com.lody.virtual.helper.dedex;

import java.io.IOException;

public class Vdex {
  private static final int VERSION_OREO_006 = 6;
  
  private static final int VERSION_OREO_MR1_010 = 10;
  
  private static final int VERSION_P_018 = 18;
  
  public final int dexBegin;
  
  public final QuickenDex[] dexFiles;
  
  public final Header header;
  
  public final int[] quickeningTableOffsets;
  
  public Vdex(DataReader paramDataReader) throws Exception {
    int[] arrayOfInt;
    this.header = new Header(paramDataReader);
    int i = paramDataReader.position();
    this.dexBegin = i;
    paramDataReader.position(i);
    if (this.header.versionNears(18)) {
      arrayOfInt = new int[this.header.number_of_dex_files_];
    } else {
      arrayOfInt = null;
    } 
    this.quickeningTableOffsets = arrayOfInt;
    this.dexFiles = new QuickenDex[this.header.number_of_dex_files_];
    for (i = 0; i < this.header.number_of_dex_files_; i++) {
      arrayOfInt = this.quickeningTableOffsets;
      if (arrayOfInt != null)
        arrayOfInt[i] = paramDataReader.readInt(); 
      QuickenDex quickenDex = new QuickenDex(paramDataReader);
      this.dexFiles[i] = quickenDex;
      paramDataReader.position(quickenDex.dexPosition + quickenDex.header.file_size_);
    } 
  }
  
  public static class Header {
    final int dex_shared_data_size_;
    
    final int dex_size_;
    
    final char[] magic_;
    
    public final int number_of_dex_files_;
    
    final int quickening_info_size_;
    
    final int[] vdexCheckSums;
    
    final int verifier_deps_size_;
    
    public final int version;
    
    final char[] version_;
    
    public Header(DataReader param1DataReader) throws IOException {
      int[] arrayOfInt;
      char[] arrayOfChar = new char[4];
      this.magic_ = arrayOfChar;
      this.version_ = new char[4];
      param1DataReader.readBytes(arrayOfChar);
      String str = new String(this.magic_);
      if ("vdex".equals(str)) {
        param1DataReader.readBytes(this.version_);
        this.version = DataReader.toInt(new String(this.version_));
        this.number_of_dex_files_ = param1DataReader.readInt();
        this.dex_size_ = param1DataReader.readInt();
        boolean bool = versionNears(18);
        byte b1 = 0;
        if (bool) {
          b2 = param1DataReader.readInt();
        } else {
          b2 = 0;
        } 
        this.dex_shared_data_size_ = b2;
        this.verifier_deps_size_ = param1DataReader.readInt();
        this.quickening_info_size_ = param1DataReader.readInt();
        this.vdexCheckSums = new int[this.number_of_dex_files_];
        byte b2 = b1;
        while (true) {
          arrayOfInt = this.vdexCheckSums;
          if (b2 < arrayOfInt.length) {
            arrayOfInt[b2] = param1DataReader.readInt();
            b2++;
            continue;
          } 
          break;
        } 
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Invalid dex magic '");
      stringBuilder.append((String)arrayOfInt);
      stringBuilder.append("'");
      throw new IOException(stringBuilder.toString());
    }
    
    public boolean versionNears(int param1Int) {
      param1Int = Math.abs(this.version - param1Int);
      boolean bool = true;
      if (param1Int > 1)
        bool = false; 
      return bool;
    }
  }
  
  public static class QuickenDex extends Dex {
    QuickenDex(DataReader param1DataReader) throws IOException {
      super(param1DataReader);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\dedex\Vdex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */