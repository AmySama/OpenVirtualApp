package com.android.dx.dex.file;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstArray;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.ByteArrayAnnotatedOutput;

public final class EncodedArrayItem extends OffsettedItem {
  private static final int ALIGNMENT = 1;
  
  private final CstArray array;
  
  private byte[] encodedForm;
  
  public EncodedArrayItem(CstArray paramCstArray) {
    super(1, -1);
    if (paramCstArray != null) {
      this.array = paramCstArray;
      this.encodedForm = null;
      return;
    } 
    throw new NullPointerException("array == null");
  }
  
  public void addContents(DexFile paramDexFile) {
    ValueEncoder.addContents(paramDexFile, (Constant)this.array);
  }
  
  protected int compareTo0(OffsettedItem paramOffsettedItem) {
    paramOffsettedItem = paramOffsettedItem;
    return this.array.compareTo((Constant)((EncodedArrayItem)paramOffsettedItem).array);
  }
  
  public int hashCode() {
    return this.array.hashCode();
  }
  
  public ItemType itemType() {
    return ItemType.TYPE_ENCODED_ARRAY_ITEM;
  }
  
  protected void place0(Section paramSection, int paramInt) {
    ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = new ByteArrayAnnotatedOutput();
    (new ValueEncoder(paramSection.getFile(), (AnnotatedOutput)byteArrayAnnotatedOutput)).writeArray(this.array, false);
    byte[] arrayOfByte = byteArrayAnnotatedOutput.toByteArray();
    this.encodedForm = arrayOfByte;
    setWriteSize(arrayOfByte.length);
  }
  
  public String toHuman() {
    return this.array.toHuman();
  }
  
  protected void writeTo0(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(offsetString());
      stringBuilder.append(" encoded array");
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
      (new ValueEncoder(paramDexFile, paramAnnotatedOutput)).writeArray(this.array, true);
    } else {
      paramAnnotatedOutput.write(this.encodedForm);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\EncodedArrayItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */