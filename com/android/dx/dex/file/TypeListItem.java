package com.android.dx.dex.file;

import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;

public final class TypeListItem extends OffsettedItem {
  private static final int ALIGNMENT = 4;
  
  private static final int ELEMENT_SIZE = 2;
  
  private static final int HEADER_SIZE = 4;
  
  private final TypeList list;
  
  public TypeListItem(TypeList paramTypeList) {
    super(4, paramTypeList.size() * 2 + 4);
    this.list = paramTypeList;
  }
  
  public void addContents(DexFile paramDexFile) {
    TypeIdsSection typeIdsSection = paramDexFile.getTypeIds();
    int i = this.list.size();
    for (byte b = 0; b < i; b++)
      typeIdsSection.intern(this.list.getType(b)); 
  }
  
  protected int compareTo0(OffsettedItem paramOffsettedItem) {
    return StdTypeList.compareContents(this.list, ((TypeListItem)paramOffsettedItem).list);
  }
  
  public TypeList getList() {
    return this.list;
  }
  
  public int hashCode() {
    return StdTypeList.hashContents(this.list);
  }
  
  public ItemType itemType() {
    return ItemType.TYPE_TYPE_LIST;
  }
  
  public String toHuman() {
    throw new RuntimeException("unsupported");
  }
  
  protected void writeTo0(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    TypeIdsSection typeIdsSection = paramDexFile.getTypeIds();
    int i = this.list.size();
    boolean bool = paramAnnotatedOutput.annotates();
    byte b1 = 0;
    if (bool) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(offsetString());
      stringBuilder.append(" type_list");
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  size: ");
      stringBuilder.append(Hex.u4(i));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      for (byte b = 0; b < i; b++) {
        Type type = this.list.getType(b);
        int j = typeIdsSection.indexOf(type);
        stringBuilder = new StringBuilder();
        stringBuilder.append("  ");
        stringBuilder.append(Hex.u2(j));
        stringBuilder.append(" // ");
        stringBuilder.append(type.toHuman());
        paramAnnotatedOutput.annotate(2, stringBuilder.toString());
      } 
    } 
    paramAnnotatedOutput.writeInt(i);
    for (byte b2 = b1; b2 < i; b2++)
      paramAnnotatedOutput.writeShort(typeIdsSection.indexOf(this.list.getType(b2))); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\TypeListItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */