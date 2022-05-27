package com.android.dx.dex.file;

import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;

public final class TypeIdItem extends IdItem {
  public TypeIdItem(CstType paramCstType) {
    super(paramCstType);
  }
  
  public void addContents(DexFile paramDexFile) {
    paramDexFile.getStringIds().intern(getDefiningClass().getDescriptor());
  }
  
  public ItemType itemType() {
    return ItemType.TYPE_TYPE_ID_ITEM;
  }
  
  public int writeSize() {
    return 4;
  }
  
  public void writeTo(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    CstString cstString = getDefiningClass().getDescriptor();
    int i = paramDexFile.getStringIds().indexOf(cstString);
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(indexString());
      stringBuilder.append(' ');
      stringBuilder.append(cstString.toHuman());
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  descriptor_idx: ");
      stringBuilder.append(Hex.u4(i));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
    } 
    paramAnnotatedOutput.writeInt(i);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\TypeIdItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */