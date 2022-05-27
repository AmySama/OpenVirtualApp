package com.android.dx.dex.file;

import com.android.dx.rop.cst.CstMemberRef;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;

public abstract class MemberIdItem extends IdItem {
  private final CstMemberRef cst;
  
  public MemberIdItem(CstMemberRef paramCstMemberRef) {
    super(paramCstMemberRef.getDefiningClass());
    this.cst = paramCstMemberRef;
  }
  
  public void addContents(DexFile paramDexFile) {
    super.addContents(paramDexFile);
    paramDexFile.getStringIds().intern(getRef().getNat().getName());
  }
  
  public final CstMemberRef getRef() {
    return this.cst;
  }
  
  protected abstract int getTypoidIdx(DexFile paramDexFile);
  
  protected abstract String getTypoidName();
  
  public int writeSize() {
    return 8;
  }
  
  public final void writeTo(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    TypeIdsSection typeIdsSection = paramDexFile.getTypeIds();
    StringIdsSection stringIdsSection = paramDexFile.getStringIds();
    CstNat cstNat = this.cst.getNat();
    int i = typeIdsSection.indexOf(getDefiningClass());
    int j = stringIdsSection.indexOf(cstNat.getName());
    int k = getTypoidIdx(paramDexFile);
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(indexString());
      stringBuilder.append(' ');
      stringBuilder.append(this.cst.toHuman());
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  class_idx: ");
      stringBuilder.append(Hex.u2(i));
      paramAnnotatedOutput.annotate(2, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append(getTypoidName());
      stringBuilder.append(':');
      paramAnnotatedOutput.annotate(2, String.format("  %-10s %s", new Object[] { stringBuilder.toString(), Hex.u2(k) }));
      stringBuilder = new StringBuilder();
      stringBuilder.append("  name_idx:  ");
      stringBuilder.append(Hex.u4(j));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
    } 
    paramAnnotatedOutput.writeShort(i);
    paramAnnotatedOutput.writeShort(k);
    paramAnnotatedOutput.writeInt(j);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\MemberIdItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */