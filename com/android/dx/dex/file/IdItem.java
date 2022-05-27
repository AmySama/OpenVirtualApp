package com.android.dx.dex.file;

import com.android.dx.rop.cst.CstType;

public abstract class IdItem extends IndexedItem {
  private final CstType type;
  
  public IdItem(CstType paramCstType) {
    if (paramCstType != null) {
      this.type = paramCstType;
      return;
    } 
    throw new NullPointerException("type == null");
  }
  
  public void addContents(DexFile paramDexFile) {
    paramDexFile.getTypeIds().intern(this.type);
  }
  
  public final CstType getDefiningClass() {
    return this.type;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\IdItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */