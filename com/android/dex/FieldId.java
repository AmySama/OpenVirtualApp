package com.android.dex;

import com.android.dex.util.Unsigned;

public final class FieldId implements Comparable<FieldId> {
  private final int declaringClassIndex;
  
  private final Dex dex;
  
  private final int nameIndex;
  
  private final int typeIndex;
  
  public FieldId(Dex paramDex, int paramInt1, int paramInt2, int paramInt3) {
    this.dex = paramDex;
    this.declaringClassIndex = paramInt1;
    this.typeIndex = paramInt2;
    this.nameIndex = paramInt3;
  }
  
  public int compareTo(FieldId paramFieldId) {
    int i = this.declaringClassIndex;
    int j = paramFieldId.declaringClassIndex;
    if (i != j)
      return Unsigned.compare(i, j); 
    i = this.nameIndex;
    j = paramFieldId.nameIndex;
    return (i != j) ? Unsigned.compare(i, j) : Unsigned.compare(this.typeIndex, paramFieldId.typeIndex);
  }
  
  public int getDeclaringClassIndex() {
    return this.declaringClassIndex;
  }
  
  public int getNameIndex() {
    return this.nameIndex;
  }
  
  public int getTypeIndex() {
    return this.typeIndex;
  }
  
  public String toString() {
    if (this.dex == null) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(this.declaringClassIndex);
      stringBuilder1.append(" ");
      stringBuilder1.append(this.typeIndex);
      stringBuilder1.append(" ");
      stringBuilder1.append(this.nameIndex);
      return stringBuilder1.toString();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.dex.typeNames().get(this.typeIndex));
    stringBuilder.append(".");
    stringBuilder.append(this.dex.strings().get(this.nameIndex));
    return stringBuilder.toString();
  }
  
  public void writeTo(Dex.Section paramSection) {
    paramSection.writeUnsignedShort(this.declaringClassIndex);
    paramSection.writeUnsignedShort(this.typeIndex);
    paramSection.writeInt(this.nameIndex);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dex\FieldId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */