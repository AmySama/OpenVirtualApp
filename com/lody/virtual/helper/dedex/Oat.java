package com.lody.virtual.helper.dedex;

import com.lody.virtual.helper.utils.FileUtils;
import java.io.File;
import java.io.IOException;

public class Oat {
  public static final String SECTION_RODATA = ".rodata";
  
  public final Dex[] dexFiles;
  
  public final Header header;
  
  public final OatDexFile[] oatDexFiles;
  
  public final long oatPosition;
  
  public final File srcFile;
  
  public Oat(DataReader paramDataReader) throws Exception {
    long l = paramDataReader.position();
    this.oatPosition = l;
    if (l == 4096L) {
      this.srcFile = paramDataReader.getFile();
      Header header = new Header(paramDataReader);
      this.header = header;
      this.oatDexFiles = new OatDexFile[header.dex_file_count_];
      this.dexFiles = new Dex[this.header.dex_file_count_];
      for (byte b = 0; b < this.oatDexFiles.length; b++) {
        Dex dex;
        OatDexFile oatDexFile = new OatDexFile(paramDataReader, this.header.artVersion);
        this.oatDexFiles[b] = oatDexFile;
        l = paramDataReader.position();
        if (oatDexFile.dex_file_pointer_ != null) {
          DataReader dataReader = new DataReader(oatDexFile.dex_file_pointer_);
          paramDataReader.addAssociatedReader(dataReader);
          dataReader.seek(oatDexFile.dex_file_offset_);
          dex = new Dex(dataReader);
        } else {
          paramDataReader.seek(this.oatPosition + ((OatDexFile)dex).dex_file_offset_);
          dex = new Dex(paramDataReader);
        } 
        this.dexFiles[b] = dex;
        if (this.header.artVersion < Version.N_70.oat) {
          paramDataReader.seek(l + (dex.header.class_defs_size_ * 4));
          if (paramDataReader.previewInt() > 255)
            paramDataReader.readInt(); 
        } else {
          paramDataReader.seek(l);
        } 
      } 
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Strange oat position ");
    stringBuilder.append(this.oatPosition);
    throw new IOException(stringBuilder.toString());
  }
  
  public int getArtVersion() {
    return this.header.artVersion;
  }
  
  public static class Header {
    final int adler32_checksum_;
    
    int artVersion;
    
    final int dex_file_count_;
    
    final int executable_offset_;
    
    final int image_file_location_oat_checksum_;
    
    final int image_file_location_oat_data_begin_;
    
    final int image_patch_delta_;
    
    final int instruction_set_;
    
    final int instruction_set_features_;
    
    final int interpreter_to_compiled_code_bridge_offset_;
    
    final int interpreter_to_interpreter_bridge_offset_;
    
    final int jni_dlsym_lookup_offset_;
    
    final char[] key_value_store_;
    
    final int key_value_store_size_;
    
    final char[] magic_;
    
    int portable_imt_conflict_trampoline_offset_;
    
    int portable_resolution_trampoline_offset_;
    
    int portable_to_interpreter_bridge_offset_;
    
    final int quick_generic_jni_trampoline_offset_;
    
    final int quick_imt_conflict_trampoline_offset_;
    
    final int quick_resolution_trampoline_offset_;
    
    final int quick_to_interpreter_bridge_offset_;
    
    final char[] version_;
    
    public Header(DataReader param1DataReader) throws IOException {
      char[] arrayOfChar = new char[4];
      this.magic_ = arrayOfChar;
      this.version_ = new char[4];
      param1DataReader.readBytes(arrayOfChar);
      arrayOfChar = this.magic_;
      if (arrayOfChar[0] == 'o' && arrayOfChar[1] == 'a' && arrayOfChar[2] == 't') {
        param1DataReader.readBytes(this.version_);
        this.artVersion = DataReader.toInt(new String(this.version_));
        this.adler32_checksum_ = param1DataReader.readInt();
        this.instruction_set_ = param1DataReader.readInt();
        this.instruction_set_features_ = param1DataReader.readInt();
        this.dex_file_count_ = param1DataReader.readInt();
        this.executable_offset_ = param1DataReader.readInt();
        this.interpreter_to_interpreter_bridge_offset_ = param1DataReader.readInt();
        this.interpreter_to_compiled_code_bridge_offset_ = param1DataReader.readInt();
        this.jni_dlsym_lookup_offset_ = param1DataReader.readInt();
        if (this.artVersion < 52) {
          this.portable_imt_conflict_trampoline_offset_ = param1DataReader.readInt();
          this.portable_resolution_trampoline_offset_ = param1DataReader.readInt();
          this.portable_to_interpreter_bridge_offset_ = param1DataReader.readInt();
        } 
        this.quick_generic_jni_trampoline_offset_ = param1DataReader.readInt();
        this.quick_imt_conflict_trampoline_offset_ = param1DataReader.readInt();
        this.quick_resolution_trampoline_offset_ = param1DataReader.readInt();
        this.quick_to_interpreter_bridge_offset_ = param1DataReader.readInt();
        this.image_patch_delta_ = param1DataReader.readInt();
        this.image_file_location_oat_checksum_ = param1DataReader.readInt();
        this.image_file_location_oat_data_begin_ = param1DataReader.readInt();
        int i = param1DataReader.readInt();
        this.key_value_store_size_ = i;
        arrayOfChar = new char[i];
        this.key_value_store_ = arrayOfChar;
        param1DataReader.readBytes(arrayOfChar);
        return;
      } 
      throw new IOException(String.format("Invalid art magic %c%c%c", new Object[] { Character.valueOf(this.magic_[0]), Character.valueOf(this.magic_[1]), Character.valueOf(this.magic_[2]) }));
    }
  }
  
  public static final class InstructionSet {
    public static final int kArm = 1;
    
    public static final int kArm64 = 2;
    
    public static final int kMips = 6;
    
    public static final int kMips64 = 7;
    
    public static final int kNone = 0;
    
    public static final int kThumb2 = 3;
    
    public static final int kX86 = 4;
    
    public static final int kX86_64 = 5;
  }
  
  public static class OatDexFile {
    int class_offsets_offset_;
    
    final int dex_file_location_checksum_;
    
    public final byte[] dex_file_location_data_;
    
    public final int dex_file_location_size_;
    
    final int dex_file_offset_;
    
    File dex_file_pointer_;
    
    int lookup_table_offset_;
    
    public OatDexFile(DataReader param1DataReader, int param1Int) throws IOException {
      StringBuilder stringBuilder;
      int i = param1DataReader.readInt();
      this.dex_file_location_size_ = i;
      byte[] arrayOfByte = new byte[i];
      this.dex_file_location_data_ = arrayOfByte;
      param1DataReader.readBytes(arrayOfByte);
      this.dex_file_location_checksum_ = param1DataReader.readInt();
      this.dex_file_offset_ = param1DataReader.readInt();
      File file = FileUtils.changeExt(param1DataReader.getFile(), "vdex");
      if (file.exists()) {
        this.dex_file_pointer_ = file;
      } else if (this.dex_file_offset_ == 28) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("dex_file_offset_=");
        stringBuilder.append(this.dex_file_offset_);
        stringBuilder.append(", does ");
        stringBuilder.append(file.getName());
        stringBuilder.append(" miss?");
        throw new IOException(stringBuilder.toString());
      } 
      if (param1Int >= Oat.Version.N_70.oat) {
        this.class_offsets_offset_ = stringBuilder.readInt();
        this.lookup_table_offset_ = stringBuilder.readInt();
      } 
    }
    
    public String getLocation() {
      return new String(this.dex_file_location_data_);
    }
  }
  
  public enum Version {
    L_50(21, 39),
    L_MR1_51(22, 45),
    M_60(23, 64),
    N_70(24, 79),
    N_MR1_71(25, 88),
    O_80(26, 124),
    O_MR1_81(26, 124);
    
    public final int api;
    
    public final int oat;
    
    static {
      Version version = new Version("O_MR1_81", 6, 27, 131);
      O_MR1_81 = version;
      $VALUES = new Version[] { L_50, L_MR1_51, M_60, N_70, N_MR1_71, O_80, version };
    }
    
    Version(int param1Int1, int param1Int2) {
      this.api = param1Int1;
      this.oat = param1Int2;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\dedex\Oat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */