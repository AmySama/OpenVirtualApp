package com.android.dx.dex.file;

import com.android.dx.rop.cst.CstFieldRef;
import com.android.dx.rop.cst.CstMemberRef;

public final class FieldIdItem extends MemberIdItem {
  public FieldIdItem(CstFieldRef paramCstFieldRef) {
    super((CstMemberRef)paramCstFieldRef);
  }
  
  public void addContents(DexFile paramDexFile) {
    super.addContents(paramDexFile);
    paramDexFile.getTypeIds().intern(getFieldRef().getType());
  }
  
  public CstFieldRef getFieldRef() {
    return (CstFieldRef)getRef();
  }
  
  protected int getTypoidIdx(DexFile paramDexFile) {
    return paramDexFile.getTypeIds().indexOf(getFieldRef().getType());
  }
  
  protected String getTypoidName() {
    return "type_idx";
  }
  
  public ItemType itemType() {
    return ItemType.TYPE_FIELD_ID_ITEM;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\FieldIdItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */