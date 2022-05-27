package com.lody.virtual.helper.dedex;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

public class Elf implements Closeable {
  static final int EI_CLASS = 4;
  
  static final int EI_DATA = 5;
  
  static final int EI_NIDENT = 16;
  
  static final char[] ELF_MAGIC = new char[] { '', 'E', 'L', 'F' };
  
  static final int PF_MASKOS = 267386880;
  
  static final int PF_MASKPROC = -268435456;
  
  static final int PF_R = 4;
  
  static final int PF_W = 2;
  
  static final int PF_X = 1;
  
  static final int PT_DYNAMIC = 2;
  
  static final int PT_INTERP = 3;
  
  static final int PT_LOAD = 1;
  
  static final int PT_NOTE = 4;
  
  static final int PT_NULL = 0;
  
  static final int PT_PHDR = 6;
  
  static final int PT_SHLIB = 5;
  
  static final int PT_TLS = 7;
  
  public static final String SHN_DYNAMIC = ".dynamic";
  
  public static final String SHN_DYNSTR = ".dynstr";
  
  public static final String SHN_DYNSYM = ".dynsym";
  
  public static final String SHN_HASH = ".hash";
  
  public static final String SHN_RODATA = ".rodata";
  
  public static final String SHN_SHSTRTAB = ".shstrtab";
  
  public static final String SHN_TEXT = ".text";
  
  static final int SHN_UNDEF = 0;
  
  static final int SHT_DYNAMIC = 6;
  
  static final int SHT_DYNSYM = 11;
  
  static final int SHT_HASH = 5;
  
  static final int SHT_PROGBITS = 1;
  
  static final int SHT_RELA = 4;
  
  static final int SHT_STRTAB = 3;
  
  static final int SHT_SYMTAB = 2;
  
  final char[] e_ident;
  
  public final boolean is64bit;
  
  byte[] mDynStringTable;
  
  Elf_Sym[] mDynamicSymbols;
  
  private final Ehdr mHeader;
  
  Elf_Phdr[] mProgHeaders;
  
  boolean mReadFull;
  
  private final DataReader mReader;
  
  private final Elf_Shdr[] mSectionHeaders;
  
  private byte[] mStringTable;
  
  public Elf(File paramFile) throws Exception {
    Ehdr ehdr;
    this.e_ident = new char[16];
    DataReader dataReader = new DataReader(paramFile);
    this.mReader = dataReader;
    dataReader.readBytes(this.e_ident);
    if (checkMagic()) {
      boolean bool;
      dataReader.setLittleEndian(isLittleEndian());
      char c = getFileClass();
      short s = 0;
      if (c == '\002') {
        bool = true;
      } else {
        bool = false;
      } 
      this.is64bit = bool;
      if (bool) {
        Elf64_Ehdr elf64_Ehdr = new Elf64_Ehdr();
        elf64_Ehdr.e_type = dataReader.readShort();
        elf64_Ehdr.e_machine = dataReader.readShort();
        elf64_Ehdr.e_version = dataReader.readInt();
        elf64_Ehdr.e_entry = dataReader.readLong();
        elf64_Ehdr.e_phoff = dataReader.readLong();
        elf64_Ehdr.e_shoff = dataReader.readLong();
        this.mHeader = elf64_Ehdr;
      } else {
        Elf32_Ehdr elf32_Ehdr = new Elf32_Ehdr();
        elf32_Ehdr.e_type = dataReader.readShort();
        elf32_Ehdr.e_machine = dataReader.readShort();
        elf32_Ehdr.e_version = dataReader.readInt();
        elf32_Ehdr.e_entry = dataReader.readInt();
        elf32_Ehdr.e_phoff = dataReader.readInt();
        elf32_Ehdr.e_shoff = dataReader.readInt();
        this.mHeader = elf32_Ehdr;
      } 
      ehdr = this.mHeader;
      ehdr.e_flags = dataReader.readInt();
      ehdr.e_ehsize = dataReader.readShort();
      ehdr.e_phentsize = dataReader.readShort();
      ehdr.e_phnum = dataReader.readShort();
      ehdr.e_shentsize = dataReader.readShort();
      ehdr.e_shnum = dataReader.readShort();
      ehdr.e_shstrndx = dataReader.readShort();
      this.mSectionHeaders = new Elf_Shdr[ehdr.e_shnum];
      while (s < ehdr.e_shnum) {
        dataReader.seek(ehdr.getSectionOffset() + (ehdr.e_shentsize * s));
        if (this.is64bit) {
          Elf64_Shdr elf64_Shdr = new Elf64_Shdr();
          elf64_Shdr.sh_name = dataReader.readInt();
          elf64_Shdr.sh_type = dataReader.readInt();
          elf64_Shdr.sh_flags = dataReader.readLong();
          elf64_Shdr.sh_addr = dataReader.readLong();
          elf64_Shdr.sh_offset = dataReader.readLong();
          elf64_Shdr.sh_size = dataReader.readLong();
          elf64_Shdr.sh_link = dataReader.readInt();
          elf64_Shdr.sh_info = dataReader.readInt();
          elf64_Shdr.sh_addralign = dataReader.readLong();
          elf64_Shdr.sh_entsize = dataReader.readLong();
          this.mSectionHeaders[s] = elf64_Shdr;
        } else {
          Elf32_Shdr elf32_Shdr = new Elf32_Shdr();
          elf32_Shdr.sh_name = dataReader.readInt();
          elf32_Shdr.sh_type = dataReader.readInt();
          elf32_Shdr.sh_flags = dataReader.readInt();
          elf32_Shdr.sh_addr = dataReader.readInt();
          elf32_Shdr.sh_offset = dataReader.readInt();
          elf32_Shdr.sh_size = dataReader.readInt();
          elf32_Shdr.sh_link = dataReader.readInt();
          elf32_Shdr.sh_info = dataReader.readInt();
          elf32_Shdr.sh_addralign = dataReader.readInt();
          elf32_Shdr.sh_entsize = dataReader.readInt();
          this.mSectionHeaders[s] = elf32_Shdr;
        } 
        s++;
      } 
      if (ehdr.e_shstrndx > -1) {
        s = ehdr.e_shstrndx;
        Elf_Shdr[] arrayOfElf_Shdr = this.mSectionHeaders;
        if (s < arrayOfElf_Shdr.length) {
          Elf_Shdr elf_Shdr = arrayOfElf_Shdr[ehdr.e_shstrndx];
          if (elf_Shdr.sh_type == 3) {
            this.mStringTable = new byte[elf_Shdr.getSize()];
            dataReader.seek(elf_Shdr.getOffset());
            dataReader.readBytes(this.mStringTable);
            if (this.mReadFull) {
              readSymbolTables();
              readProgramHeaders();
            } 
            return;
          } 
          StringBuilder stringBuilder2 = new StringBuilder();
          stringBuilder2.append("Wrong string section e_shstrndx=");
          stringBuilder2.append(ehdr.e_shstrndx);
          throw new IOException(stringBuilder2.toString());
        } 
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Invalid e_shstrndx=");
      stringBuilder1.append(ehdr.e_shstrndx);
      throw new IOException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Invalid elf magic: ");
    stringBuilder.append(ehdr);
    throw new IOException(stringBuilder.toString());
  }
  
  public Elf(String paramString) throws Exception {
    this(new File(paramString));
  }
  
  public Elf(String paramString, boolean paramBoolean) throws Exception {
    this(paramString);
    if (paramBoolean)
      this.mReader.close(); 
  }
  
  private void readProgramHeaders() {
    Ehdr ehdr = this.mHeader;
    DataReader dataReader = this.mReader;
    this.mProgHeaders = new Elf_Phdr[ehdr.e_phnum];
    for (byte b = 0; b < ehdr.e_phnum; b++) {
      dataReader.seek(ehdr.getProgramOffset() + (ehdr.e_phentsize * b));
      if (this.is64bit) {
        Elf64_Phdr elf64_Phdr = new Elf64_Phdr();
        elf64_Phdr.p_type = dataReader.readInt();
        elf64_Phdr.p_offset = dataReader.readInt();
        elf64_Phdr.p_vaddr = dataReader.readLong();
        elf64_Phdr.p_paddr = dataReader.readLong();
        elf64_Phdr.p_filesz = dataReader.readLong();
        elf64_Phdr.p_memsz = dataReader.readLong();
        elf64_Phdr.p_flags = dataReader.readLong();
        elf64_Phdr.p_align = dataReader.readLong();
        this.mProgHeaders[b] = elf64_Phdr;
      } else {
        Elf32_Phdr elf32_Phdr = new Elf32_Phdr();
        elf32_Phdr.p_type = dataReader.readInt();
        elf32_Phdr.p_offset = dataReader.readInt();
        elf32_Phdr.p_vaddr = dataReader.readInt();
        elf32_Phdr.p_paddr = dataReader.readInt();
        elf32_Phdr.p_filesz = dataReader.readInt();
        elf32_Phdr.p_memsz = dataReader.readInt();
        elf32_Phdr.p_flags = dataReader.readInt();
        elf32_Phdr.p_align = dataReader.readInt();
        this.mProgHeaders[b] = elf32_Phdr;
      } 
    } 
  }
  
  private void readSymbolTables() {
    DataReader dataReader = this.mReader;
    Elf_Shdr elf_Shdr = getSection(".dynsym");
    if (elf_Shdr != null) {
      dataReader.seek(elf_Shdr.getOffset());
      int i = elf_Shdr.getSize();
      if (this.is64bit) {
        b = 24;
      } else {
        b = 16;
      } 
      i /= b;
      this.mDynamicSymbols = new Elf_Sym[i];
      char[] arrayOfChar = new char[1];
      for (byte b = 0; b < i; b++) {
        if (this.is64bit) {
          Elf64_Sym elf64_Sym = new Elf64_Sym();
          elf64_Sym.st_name = dataReader.readInt();
          dataReader.readBytes(arrayOfChar);
          elf64_Sym.st_info = (char)arrayOfChar[0];
          dataReader.readBytes(arrayOfChar);
          elf64_Sym.st_other = (char)arrayOfChar[0];
          elf64_Sym.st_value = dataReader.readLong();
          elf64_Sym.st_size = dataReader.readLong();
          elf64_Sym.st_shndx = dataReader.readShort();
          this.mDynamicSymbols[b] = elf64_Sym;
        } else {
          Elf32_Sym elf32_Sym = new Elf32_Sym();
          elf32_Sym.st_name = dataReader.readInt();
          elf32_Sym.st_value = dataReader.readInt();
          elf32_Sym.st_size = dataReader.readInt();
          dataReader.readBytes(arrayOfChar);
          elf32_Sym.st_info = (char)arrayOfChar[0];
          dataReader.readBytes(arrayOfChar);
          elf32_Sym.st_other = (char)arrayOfChar[0];
          elf32_Sym.st_shndx = dataReader.readShort();
          this.mDynamicSymbols[b] = elf32_Sym;
        } 
      } 
      Elf_Shdr elf_Shdr1 = this.mSectionHeaders[elf_Shdr.sh_link];
      dataReader.seek(elf_Shdr1.getOffset());
      byte[] arrayOfByte = new byte[elf_Shdr1.getSize()];
      this.mDynStringTable = arrayOfByte;
      dataReader.readBytes(arrayOfByte);
    } 
  }
  
  final boolean checkMagic() {
    char[] arrayOfChar1 = this.e_ident;
    boolean bool1 = false;
    char c = arrayOfChar1[0];
    char[] arrayOfChar2 = ELF_MAGIC;
    boolean bool2 = bool1;
    if (c == arrayOfChar2[0]) {
      bool2 = bool1;
      if (arrayOfChar1[1] == arrayOfChar2[1]) {
        bool2 = bool1;
        if (arrayOfChar1[2] == arrayOfChar2[2]) {
          bool2 = bool1;
          if (arrayOfChar1[3] == arrayOfChar2[3])
            bool2 = true; 
        } 
      } 
    } 
    return bool2;
  }
  
  public void close() {
    this.mReader.close();
  }
  
  final char getDataEncoding() {
    return this.e_ident[5];
  }
  
  public final String getDynString(int paramInt) {
    if (paramInt == 0)
      return "SHN_UNDEF"; 
    int i;
    for (i = paramInt; this.mDynStringTable[i] != 0; i++);
    return new String(this.mDynStringTable, paramInt, i - paramInt);
  }
  
  final char getFileClass() {
    return this.e_ident[4];
  }
  
  public Ehdr getHeader() {
    return this.mHeader;
  }
  
  public DataReader getReader() {
    return this.mReader;
  }
  
  public final Elf_Shdr getSection(String paramString) {
    for (Elf_Shdr elf_Shdr : this.mSectionHeaders) {
      if (paramString.equals(getString(elf_Shdr.sh_name)))
        return elf_Shdr; 
    } 
    return null;
  }
  
  public Elf_Shdr[] getSectionHeaders() {
    return this.mSectionHeaders;
  }
  
  public final String getString(int paramInt) {
    if (paramInt == 0)
      return "SHN_UNDEF"; 
    int i;
    for (i = paramInt; this.mStringTable[i] != 0; i++);
    return new String(this.mStringTable, paramInt, i - paramInt);
  }
  
  public final Elf_Sym getSymbolTable(String paramString) {
    Elf_Sym[] arrayOfElf_Sym = this.mDynamicSymbols;
    if (arrayOfElf_Sym != null) {
      int i = arrayOfElf_Sym.length;
      for (byte b = 0; b < i; b++) {
        Elf_Sym elf_Sym = arrayOfElf_Sym[b];
        if (paramString.equals(getDynString(elf_Sym.st_name)))
          return elf_Sym; 
      } 
    } 
    return null;
  }
  
  public final boolean isLittleEndian() {
    char c = getDataEncoding();
    boolean bool = true;
    if (c != '\001')
      bool = false; 
    return bool;
  }
  
  public static abstract class Ehdr {
    short e_ehsize;
    
    int e_flags;
    
    short e_machine;
    
    short e_phentsize;
    
    short e_phnum;
    
    short e_shentsize;
    
    short e_shnum;
    
    short e_shstrndx;
    
    short e_type;
    
    int e_version;
    
    abstract long getProgramOffset();
    
    abstract long getSectionOffset();
  }
  
  static class Elf32_Ehdr extends Ehdr {
    int e_entry;
    
    int e_phoff;
    
    int e_shoff;
    
    long getProgramOffset() {
      return this.e_phoff;
    }
    
    long getSectionOffset() {
      return this.e_shoff;
    }
  }
  
  static class Elf32_Phdr extends Elf_Phdr {
    int p_align;
    
    int p_filesz;
    
    int p_flags;
    
    int p_memsz;
    
    int p_paddr;
    
    int p_vaddr;
    
    public long getFlags() {
      return this.p_flags;
    }
  }
  
  static class Elf32_Shdr extends Elf_Shdr {
    int sh_addr;
    
    int sh_addralign;
    
    int sh_entsize;
    
    int sh_flags;
    
    int sh_offset;
    
    int sh_size;
    
    public long getOffset() {
      return this.sh_offset;
    }
    
    public int getSize() {
      return this.sh_size;
    }
  }
  
  static class Elf32_Sym extends Elf_Sym {
    int st_size;
    
    int st_value;
    
    long getSize() {
      return this.st_size;
    }
  }
  
  static class Elf64_Ehdr extends Ehdr {
    long e_entry;
    
    long e_phoff;
    
    long e_shoff;
    
    long getProgramOffset() {
      return this.e_phoff;
    }
    
    long getSectionOffset() {
      return this.e_shoff;
    }
  }
  
  static class Elf64_Phdr extends Elf_Phdr {
    long p_align;
    
    long p_filesz;
    
    long p_flags;
    
    long p_memsz;
    
    long p_paddr;
    
    long p_vaddr;
    
    public long getFlags() {
      return this.p_flags;
    }
  }
  
  static class Elf64_Shdr extends Elf_Shdr {
    long sh_addr;
    
    long sh_addralign;
    
    long sh_entsize;
    
    long sh_flags;
    
    long sh_offset;
    
    long sh_size;
    
    public long getOffset() {
      return this.sh_offset;
    }
    
    public int getSize() {
      return (int)this.sh_size;
    }
  }
  
  static class Elf64_Sym extends Elf_Sym {
    long st_size;
    
    long st_value;
    
    long getSize() {
      return this.st_size;
    }
  }
  
  static abstract class Elf_Phdr {
    int p_offset;
    
    int p_type;
    
    String flagsString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("(");
      long l = getFlags();
      String str1 = "_";
      if ((l & 0x4L) != 0L) {
        str2 = "R";
      } else {
        str2 = "_";
      } 
      stringBuilder.append(str2);
      if ((getFlags() & 0x2L) != 0L) {
        str2 = "W";
      } else {
        str2 = "_";
      } 
      stringBuilder.append(str2);
      String str2 = str1;
      if ((getFlags() & 0x1L) != 0L)
        str2 = "X"; 
      stringBuilder.append(str2);
      stringBuilder.append(")");
      return stringBuilder.toString();
    }
    
    abstract long getFlags();
    
    String programType() {
      switch (this.p_type) {
        default:
          return "Unknown Section";
        case 6:
          return "Program Header";
        case 5:
          return "PT_SHLIB";
        case 4:
          return "Note";
        case 3:
          return "Interpreter Path";
        case 2:
          return "Dynamic Segment";
        case 1:
          return "Loadable Segment";
        case 0:
          break;
      } 
      return "NULL";
    }
  }
  
  public static abstract class Elf_Shdr {
    int sh_info;
    
    int sh_link;
    
    int sh_name;
    
    int sh_type;
    
    public abstract long getOffset();
    
    public abstract int getSize();
  }
  
  public static abstract class Elf_Sym {
    char st_info;
    
    int st_name;
    
    char st_other;
    
    short st_shndx;
    
    char getBinding() {
      return (char)(this.st_info >> 4);
    }
    
    public long getOffset(Elf param1Elf) {
      for (byte b = 0; b < param1Elf.mSectionHeaders.length; b++) {
        if (this.st_shndx == b)
          return param1Elf.mSectionHeaders[b].getOffset(); 
      } 
      return -1L;
    }
    
    abstract long getSize();
    
    char getType() {
      return (char)(this.st_info & 0xF);
    }
    
    void setBinding(char param1Char) {
      setBindingAndType(param1Char, getType());
    }
    
    void setBindingAndType(char param1Char1, char param1Char2) {
      this.st_info = (char)(char)((param1Char1 << 4) + (param1Char2 & 0xF));
    }
    
    void setType(char param1Char) {
      setBindingAndType(getBinding(), param1Char);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\dedex\Elf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */