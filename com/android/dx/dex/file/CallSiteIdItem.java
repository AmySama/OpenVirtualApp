package com.android.dx.dex.file;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstCallSite;
import com.android.dx.rop.cst.CstCallSiteRef;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;

public final class CallSiteIdItem extends IndexedItem implements Comparable {
  private static final int ITEM_SIZE = 4;
  
  CallSiteItem data;
  
  final CstCallSiteRef invokeDynamicRef;
  
  public CallSiteIdItem(CstCallSiteRef paramCstCallSiteRef) {
    this.invokeDynamicRef = paramCstCallSiteRef;
    this.data = null;
  }
  
  public void addContents(DexFile paramDexFile) {
    CstCallSite cstCallSite = this.invokeDynamicRef.getCallSite();
    CallSiteIdsSection callSiteIdsSection = paramDexFile.getCallSiteIds();
    CallSiteItem callSiteItem1 = callSiteIdsSection.getCallSiteItem(cstCallSite);
    CallSiteItem callSiteItem2 = callSiteItem1;
    if (callSiteItem1 == null) {
      MixedItemSection mixedItemSection = paramDexFile.getByteData();
      callSiteItem2 = new CallSiteItem(cstCallSite);
      mixedItemSection.add(callSiteItem2);
      callSiteIdsSection.addCallSiteItem(cstCallSite, callSiteItem2);
    } 
    this.data = callSiteItem2;
  }
  
  public int compareTo(Object paramObject) {
    paramObject = paramObject;
    return this.invokeDynamicRef.compareTo((Constant)((CallSiteIdItem)paramObject).invokeDynamicRef);
  }
  
  public ItemType itemType() {
    return ItemType.TYPE_CALL_SITE_ID_ITEM;
  }
  
  public int writeSize() {
    return 4;
  }
  
  public void writeTo(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    int i = this.data.getAbsoluteOffset();
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(indexString());
      stringBuilder.append(' ');
      stringBuilder.append(this.invokeDynamicRef.toString());
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("call_site_off: ");
      stringBuilder.append(Hex.u4(i));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
    } 
    paramAnnotatedOutput.writeInt(i);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\CallSiteIdItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */