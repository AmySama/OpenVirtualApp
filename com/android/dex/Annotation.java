package com.android.dex;

public final class Annotation implements Comparable<Annotation> {
  private final Dex dex;
  
  private final EncodedValue encodedAnnotation;
  
  private final byte visibility;
  
  public Annotation(Dex paramDex, byte paramByte, EncodedValue paramEncodedValue) {
    this.dex = paramDex;
    this.visibility = (byte)paramByte;
    this.encodedAnnotation = paramEncodedValue;
  }
  
  public int compareTo(Annotation paramAnnotation) {
    return this.encodedAnnotation.compareTo(paramAnnotation.encodedAnnotation);
  }
  
  public EncodedValueReader getReader() {
    return new EncodedValueReader(this.encodedAnnotation, 29);
  }
  
  public int getTypeIndex() {
    EncodedValueReader encodedValueReader = getReader();
    encodedValueReader.readAnnotation();
    return encodedValueReader.getAnnotationType();
  }
  
  public byte getVisibility() {
    return this.visibility;
  }
  
  public String toString() {
    String str;
    if (this.dex == null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.visibility);
      stringBuilder.append(" ");
      stringBuilder.append(getTypeIndex());
      str = stringBuilder.toString();
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.visibility);
      stringBuilder.append(" ");
      stringBuilder.append(this.dex.typeNames().get(getTypeIndex()));
      str = stringBuilder.toString();
    } 
    return str;
  }
  
  public void writeTo(Dex.Section paramSection) {
    paramSection.writeByte(this.visibility);
    this.encodedAnnotation.writeTo(paramSection);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dex\Annotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */