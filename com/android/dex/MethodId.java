package com.android.dex;

import com.android.dex.util.Unsigned;

public final class MethodId implements Comparable<MethodId> {
  private final int declaringClassIndex;
  
  private final Dex dex;
  
  private final int nameIndex;
  
  private final int protoIndex;
  
  public MethodId(Dex paramDex, int paramInt1, int paramInt2, int paramInt3) {
    this.dex = paramDex;
    this.declaringClassIndex = paramInt1;
    this.protoIndex = paramInt2;
    this.nameIndex = paramInt3;
  }
  
  public int compareTo(MethodId paramMethodId) {
    int i = this.declaringClassIndex;
    int j = paramMethodId.declaringClassIndex;
    if (i != j)
      return Unsigned.compare(i, j); 
    j = this.nameIndex;
    i = paramMethodId.nameIndex;
    return (j != i) ? Unsigned.compare(j, i) : Unsigned.compare(this.protoIndex, paramMethodId.protoIndex);
  }
  
  public int getDeclaringClassIndex() {
    return this.declaringClassIndex;
  }
  
  public int getNameIndex() {
    return this.nameIndex;
  }
  
  public int getProtoIndex() {
    return this.protoIndex;
  }
  
  public String toString() {
    if (this.dex == null) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(this.declaringClassIndex);
      stringBuilder1.append(" ");
      stringBuilder1.append(this.protoIndex);
      stringBuilder1.append(" ");
      stringBuilder1.append(this.nameIndex);
      return stringBuilder1.toString();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.dex.typeNames().get(this.declaringClassIndex));
    stringBuilder.append(".");
    stringBuilder.append(this.dex.strings().get(this.nameIndex));
    Dex dex = this.dex;
    stringBuilder.append(dex.readTypeList(((ProtoId)dex.protoIds().get(this.protoIndex)).getParametersOffset()));
    return stringBuilder.toString();
  }
  
  public void writeTo(Dex.Section paramSection) {
    paramSection.writeUnsignedShort(this.declaringClassIndex);
    paramSection.writeUnsignedShort(this.protoIndex);
    paramSection.writeInt(this.nameIndex);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dex\MethodId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */