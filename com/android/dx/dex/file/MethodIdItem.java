package com.android.dx.dex.file;

import com.android.dx.rop.cst.CstBaseMethodRef;
import com.android.dx.rop.cst.CstMemberRef;

public final class MethodIdItem extends MemberIdItem {
  public MethodIdItem(CstBaseMethodRef paramCstBaseMethodRef) {
    super((CstMemberRef)paramCstBaseMethodRef);
  }
  
  public void addContents(DexFile paramDexFile) {
    super.addContents(paramDexFile);
    paramDexFile.getProtoIds().intern(getMethodRef().getPrototype());
  }
  
  public CstBaseMethodRef getMethodRef() {
    return (CstBaseMethodRef)getRef();
  }
  
  protected int getTypoidIdx(DexFile paramDexFile) {
    return paramDexFile.getProtoIds().indexOf(getMethodRef().getPrototype());
  }
  
  protected String getTypoidName() {
    return "proto_idx";
  }
  
  public ItemType itemType() {
    return ItemType.TYPE_METHOD_ID_ITEM;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\MethodIdItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */