package com.android.dex;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public final class TableOfContents {
  public final Section annotationSetRefLists = new Section(4098);
  
  public final Section annotationSets = new Section(4099);
  
  public final Section annotations = new Section(8196);
  
  public final Section annotationsDirectories;
  
  public int apiLevel;
  
  public final Section callSiteIds = new Section(7);
  
  public int checksum;
  
  public final Section classDatas = new Section(8192);
  
  public final Section classDefs = new Section(6);
  
  public final Section codes = new Section(8193);
  
  public int dataOff;
  
  public int dataSize;
  
  public final Section debugInfos = new Section(8195);
  
  public final Section encodedArrays = new Section(8197);
  
  public final Section fieldIds = new Section(4);
  
  public int fileSize;
  
  public final Section header = new Section(0);
  
  public int linkOff;
  
  public int linkSize;
  
  public final Section mapList = new Section(4096);
  
  public final Section methodHandles = new Section(8);
  
  public final Section methodIds = new Section(5);
  
  public final Section protoIds = new Section(3);
  
  public final Section[] sections;
  
  public byte[] signature;
  
  public final Section stringDatas = new Section(8194);
  
  public final Section stringIds = new Section(1);
  
  public final Section typeIds = new Section(2);
  
  public final Section typeLists = new Section(4097);
  
  public TableOfContents() {
    Section section = new Section(8198);
    this.annotationsDirectories = section;
    this.sections = new Section[] { 
        this.header, this.stringIds, this.typeIds, this.protoIds, this.fieldIds, this.methodIds, this.classDefs, this.mapList, this.callSiteIds, this.methodHandles, 
        this.typeLists, this.annotationSetRefLists, this.annotationSets, this.classDatas, this.codes, this.stringDatas, this.debugInfos, this.annotations, this.encodedArrays, section };
    this.signature = new byte[20];
  }
  
  private Section getSection(short paramShort) {
    for (Section section : this.sections) {
      if (section.type == paramShort)
        return section; 
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No such map item: ");
    stringBuilder.append(paramShort);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  private void readHeader(Dex.Section paramSection) throws UnsupportedEncodingException {
    byte[] arrayOfByte = paramSection.readByteArray(8);
    if (DexFormat.isSupportedDexMagic(arrayOfByte)) {
      this.apiLevel = DexFormat.magicToApi(arrayOfByte);
      this.checksum = paramSection.readInt();
      this.signature = paramSection.readByteArray(20);
      this.fileSize = paramSection.readInt();
      int i = paramSection.readInt();
      if (i == 112) {
        i = paramSection.readInt();
        if (i == 305419896) {
          this.linkSize = paramSection.readInt();
          this.linkOff = paramSection.readInt();
          this.mapList.off = paramSection.readInt();
          if (this.mapList.off != 0) {
            this.stringIds.size = paramSection.readInt();
            this.stringIds.off = paramSection.readInt();
            this.typeIds.size = paramSection.readInt();
            this.typeIds.off = paramSection.readInt();
            this.protoIds.size = paramSection.readInt();
            this.protoIds.off = paramSection.readInt();
            this.fieldIds.size = paramSection.readInt();
            this.fieldIds.off = paramSection.readInt();
            this.methodIds.size = paramSection.readInt();
            this.methodIds.off = paramSection.readInt();
            this.classDefs.size = paramSection.readInt();
            this.classDefs.off = paramSection.readInt();
            this.dataSize = paramSection.readInt();
            this.dataOff = paramSection.readInt();
            return;
          } 
          throw new DexException("Cannot merge dex files that do not contain a map");
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Unexpected endian tag: 0x");
        stringBuilder1.append(Integer.toHexString(i));
        throw new DexException(stringBuilder1.toString());
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unexpected header: 0x");
      stringBuilder.append(Integer.toHexString(i));
      throw new DexException(stringBuilder.toString());
    } 
    throw new DexException(String.format("Unexpected magic: [0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x]", new Object[] { Byte.valueOf(arrayOfByte[0]), Byte.valueOf(arrayOfByte[1]), Byte.valueOf(arrayOfByte[2]), Byte.valueOf(arrayOfByte[3]), Byte.valueOf(arrayOfByte[4]), Byte.valueOf(arrayOfByte[5]), Byte.valueOf(arrayOfByte[6]), Byte.valueOf(arrayOfByte[7]) }));
  }
  
  private void readMap(Dex.Section paramSection) throws IOException {
    int i = paramSection.readInt();
    Section section = null;
    byte b = 0;
    while (b < i) {
      short s = paramSection.readShort();
      paramSection.readShort();
      Section section1 = getSection(s);
      int j = paramSection.readInt();
      int k = paramSection.readInt();
      if ((section1.size == 0 || section1.size == j) && (section1.off == -1 || section1.off == k)) {
        section1.size = j;
        section1.off = k;
        if (section == null || section.off <= section1.off) {
          b++;
          section = section1;
          continue;
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Map is unsorted at ");
        stringBuilder1.append(section);
        stringBuilder1.append(", ");
        stringBuilder1.append(section1);
        throw new DexException(stringBuilder1.toString());
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unexpected map value for 0x");
      stringBuilder.append(Integer.toHexString(s));
      throw new DexException(stringBuilder.toString());
    } 
    Arrays.sort((Object[])this.sections);
  }
  
  public void computeSizesFromOffsets() {
    int i = this.dataOff + this.dataSize;
    for (int j = this.sections.length - 1; j >= 0; j--) {
      Section section = this.sections[j];
      if (section.off != -1)
        if (section.off <= i) {
          section.byteCount = i - section.off;
          i = section.off;
        } else {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Map is unsorted at ");
          stringBuilder.append(section);
          throw new DexException(stringBuilder.toString());
        }  
    } 
  }
  
  public void readFrom(Dex paramDex) throws IOException {
    readHeader(paramDex.open(0));
    readMap(paramDex.open(this.mapList.off));
    computeSizesFromOffsets();
  }
  
  public void writeHeader(Dex.Section paramSection, int paramInt) throws IOException {
    paramSection.write(DexFormat.apiToMagic(paramInt).getBytes("UTF-8"));
    paramSection.writeInt(this.checksum);
    paramSection.write(this.signature);
    paramSection.writeInt(this.fileSize);
    paramSection.writeInt(112);
    paramSection.writeInt(305419896);
    paramSection.writeInt(this.linkSize);
    paramSection.writeInt(this.linkOff);
    paramSection.writeInt(this.mapList.off);
    paramSection.writeInt(this.stringIds.size);
    paramSection.writeInt(this.stringIds.off);
    paramSection.writeInt(this.typeIds.size);
    paramSection.writeInt(this.typeIds.off);
    paramSection.writeInt(this.protoIds.size);
    paramSection.writeInt(this.protoIds.off);
    paramSection.writeInt(this.fieldIds.size);
    paramSection.writeInt(this.fieldIds.off);
    paramSection.writeInt(this.methodIds.size);
    paramSection.writeInt(this.methodIds.off);
    paramSection.writeInt(this.classDefs.size);
    paramSection.writeInt(this.classDefs.off);
    paramSection.writeInt(this.dataSize);
    paramSection.writeInt(this.dataOff);
  }
  
  public void writeMap(Dex.Section paramSection) throws IOException {
    Section[] arrayOfSection = this.sections;
    int i = arrayOfSection.length;
    null = 0;
    for (null = 0; null < i; null = j) {
      int j = null;
      if (arrayOfSection[null].exists())
        j = null + 1; 
      null++;
    } 
    paramSection.writeInt(null);
    for (Section section : this.sections) {
      if (section.exists()) {
        paramSection.writeShort(section.type);
        paramSection.writeShort((short)0);
        paramSection.writeInt(section.size);
        paramSection.writeInt(section.off);
      } 
    } 
  }
  
  public static class Section implements Comparable<Section> {
    public int byteCount = 0;
    
    public int off = -1;
    
    public int size = 0;
    
    public final short type;
    
    public Section(int param1Int) {
      this.type = (short)(short)param1Int;
    }
    
    public int compareTo(Section param1Section) {
      int i = this.off;
      int j = param1Section.off;
      if (i != j) {
        if (i < j) {
          i = -1;
        } else {
          i = 1;
        } 
        return i;
      } 
      return 0;
    }
    
    public boolean exists() {
      boolean bool;
      if (this.size > 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public String toString() {
      return String.format("Section[type=%#x,off=%#x,size=%#x]", new Object[] { Short.valueOf(this.type), Integer.valueOf(this.off), Integer.valueOf(this.size) });
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dex\TableOfContents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */