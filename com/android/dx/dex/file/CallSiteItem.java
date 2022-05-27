package com.android.dx.dex.file;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstArray;
import com.android.dx.rop.cst.CstCallSite;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.ByteArrayAnnotatedOutput;

public final class CallSiteItem extends OffsettedItem {
  private byte[] encodedForm;
  
  private final CstCallSite value;
  
  public CallSiteItem(CstCallSite paramCstCallSite) {
    super(1, writeSize(paramCstCallSite));
    this.value = paramCstCallSite;
  }
  
  private static int writeSize(CstCallSite paramCstCallSite) {
    return -1;
  }
  
  public void addContents(DexFile paramDexFile) {
    ValueEncoder.addContents(paramDexFile, (Constant)this.value);
  }
  
  public ItemType itemType() {
    return ItemType.TYPE_ENCODED_ARRAY_ITEM;
  }
  
  protected void place0(Section paramSection, int paramInt) {
    ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = new ByteArrayAnnotatedOutput();
    (new ValueEncoder(paramSection.getFile(), (AnnotatedOutput)byteArrayAnnotatedOutput)).writeArray((CstArray)this.value, true);
    byte[] arrayOfByte = byteArrayAnnotatedOutput.toByteArray();
    this.encodedForm = arrayOfByte;
    setWriteSize(arrayOfByte.length);
  }
  
  public String toHuman() {
    return this.value.toHuman();
  }
  
  public String toString() {
    return this.value.toString();
  }
  
  protected void writeTo0(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(offsetString());
      stringBuilder.append(" call site");
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
      (new ValueEncoder(paramDexFile, paramAnnotatedOutput)).writeArray((CstArray)this.value, true);
    } else {
      paramAnnotatedOutput.write(this.encodedForm);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\CallSiteItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */