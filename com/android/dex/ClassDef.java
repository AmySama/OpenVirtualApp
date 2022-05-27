package com.android.dex;

public final class ClassDef {
  public static final int NO_INDEX = -1;
  
  private final int accessFlags;
  
  private final int annotationsOffset;
  
  private final Dex buffer;
  
  private final int classDataOffset;
  
  private final int interfacesOffset;
  
  private final int offset;
  
  private final int sourceFileIndex;
  
  private final int staticValuesOffset;
  
  private final int supertypeIndex;
  
  private final int typeIndex;
  
  public ClassDef(Dex paramDex, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9) {
    this.buffer = paramDex;
    this.offset = paramInt1;
    this.typeIndex = paramInt2;
    this.accessFlags = paramInt3;
    this.supertypeIndex = paramInt4;
    this.interfacesOffset = paramInt5;
    this.sourceFileIndex = paramInt6;
    this.annotationsOffset = paramInt7;
    this.classDataOffset = paramInt8;
    this.staticValuesOffset = paramInt9;
  }
  
  public int getAccessFlags() {
    return this.accessFlags;
  }
  
  public int getAnnotationsOffset() {
    return this.annotationsOffset;
  }
  
  public int getClassDataOffset() {
    return this.classDataOffset;
  }
  
  public short[] getInterfaces() {
    return this.buffer.readTypeList(this.interfacesOffset).getTypes();
  }
  
  public int getInterfacesOffset() {
    return this.interfacesOffset;
  }
  
  public int getOffset() {
    return this.offset;
  }
  
  public int getSourceFileIndex() {
    return this.sourceFileIndex;
  }
  
  public int getStaticValuesOffset() {
    return this.staticValuesOffset;
  }
  
  public int getSupertypeIndex() {
    return this.supertypeIndex;
  }
  
  public int getTypeIndex() {
    return this.typeIndex;
  }
  
  public String toString() {
    if (this.buffer == null) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(this.typeIndex);
      stringBuilder1.append(" ");
      stringBuilder1.append(this.supertypeIndex);
      return stringBuilder1.toString();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.buffer.typeNames().get(this.typeIndex));
    if (this.supertypeIndex != -1) {
      stringBuilder.append(" extends ");
      stringBuilder.append(this.buffer.typeNames().get(this.supertypeIndex));
    } 
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dex\ClassDef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */