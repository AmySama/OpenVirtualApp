package com.android.dx.dex.file;

import com.android.dx.rop.cst.Constant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class HeaderSection extends UniformItemSection {
  private final List<HeaderItem> list;
  
  public HeaderSection(DexFile paramDexFile) {
    super(null, paramDexFile, 4);
    HeaderItem headerItem = new HeaderItem();
    headerItem.setIndex(0);
    this.list = Collections.singletonList(headerItem);
  }
  
  public IndexedItem get(Constant paramConstant) {
    return null;
  }
  
  public Collection<? extends Item> items() {
    return (Collection)this.list;
  }
  
  protected void orderItems() {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\HeaderSection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */